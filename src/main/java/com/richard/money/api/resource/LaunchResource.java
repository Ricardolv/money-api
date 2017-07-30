package com.richard.money.api.resource;


import com.richard.money.api.event.ResourceCreateEvent;
import com.richard.money.api.exceptionHandler.MoneyApiExceptionHandler;
import com.richard.money.api.model.Launch;
import com.richard.money.api.repository.filter.LaunchFilter;
import com.richard.money.api.service.LaunchService;
import com.richard.money.api.service.exception.PersonNonexistentOrInactiveException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/releases")
public class LaunchResource {

    @Autowired
    private LaunchService launchService;

    @Autowired
    private ApplicationEventPublisher publisher;

    @Autowired
    private MessageSource messageSource;

    @GetMapping
    public List<Launch> search(LaunchFilter launchFilter) {
        return this.launchService.filter(launchFilter);
    }

    @GetMapping("/{code}")
    public ResponseEntity<Launch> searchLaunchByCode(@PathVariable Long code) {
        Launch launch = launchService.findOne(code);
        return launch != null ? ResponseEntity.ok(launch) : ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<Launch> create(@Valid @RequestBody Launch launch, HttpServletResponse response) {
        Launch launchSave = launchService.save(launch);
        this.publisher.publishEvent(new ResourceCreateEvent(this, response, launchSave.getCode()));
        return ResponseEntity.status(HttpStatus.CREATED).body(launchSave);
    }

    @ExceptionHandler({ PersonNonexistentOrInactiveException.class })
    public ResponseEntity<Object> handlePersonNonexistentOrInactiveException(PersonNonexistentOrInactiveException ex) {
        String messageUser = messageSource.getMessage("person.nonexistent-or-inactive", null, LocaleContextHolder.getLocale());
        String messageDeveloper = ex.toString() ;
        List<MoneyApiExceptionHandler.Error> errors = Arrays.asList(new MoneyApiExceptionHandler.Error(messageUser, messageDeveloper));
        return ResponseEntity.badRequest().body(errors);
    }
}
