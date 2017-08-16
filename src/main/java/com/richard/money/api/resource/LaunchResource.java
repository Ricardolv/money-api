package com.richard.money.api.resource;


import com.richard.money.api.event.ResourceCreateEvent;
import com.richard.money.api.exceptionHandler.MoneyApiExceptionHandler;
import com.richard.money.api.model.Launch;
import com.richard.money.api.repository.filter.LaunchFilter;
import com.richard.money.api.repository.projection.ResumeLaunch;
import com.richard.money.api.service.LaunchService;
import com.richard.money.api.service.exception.PersonNonexistentOrInactiveException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
    @PreAuthorize("hasAuthority('ROLE_SEARCH_LAUNCH') and #oauth2.hasScope('read')")
    public Page<Launch> search(LaunchFilter launchFilter, Pageable pageable) {
        return this.launchService.filter(launchFilter, pageable);
    }

    @GetMapping(params = "resume")
    @PreAuthorize("hasAuthority('ROLE_SEARCH_LAUNCH') and #oauth2.hasScope('read')")
    public Page<ResumeLaunch> resume(LaunchFilter launchFilter, Pageable pageable) {
        return this.launchService.resume(launchFilter, pageable);
    }

    @GetMapping("/{code}")
    @PreAuthorize("hasAuthority('ROLE_SEARCH_LAUNCH') and #oauth2.hasScope('read')")
    public ResponseEntity<Launch> searchLaunchByCode(@PathVariable Long code) {
        Launch launch = launchService.findOne(code);
        return launch != null ? ResponseEntity.ok(launch) : ResponseEntity.notFound().build();
    }

    @PostMapping
    @PreAuthorize("hasAuthority('ROLE_REGISTER_LAUNCH') and #oauth2.hasScope('write')")
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

    @DeleteMapping("/{code}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasAuthority('ROLE_REMOVE_LAUNCH') and #oauth2.hasScope('write')")
    public void remover(@PathVariable Long code) {
        launchService.delete(code);
    }


    @PutMapping("/{code}")
    @PreAuthorize("hasAuthority('ROLE_REGISTER_LAUNCH')")
    public ResponseEntity<Launch> update(@PathVariable Long code, @Valid @RequestBody Launch launch) {
        try {
            Launch launchSave = launchService.update(code, launch);
            return ResponseEntity.ok(launchSave);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
