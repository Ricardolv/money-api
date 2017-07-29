package com.richard.money.api.resource;

import com.richard.money.api.event.ResourceCreateEvent;
import com.richard.money.api.model.Person;
import com.richard.money.api.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/persons")
public class PersonResource {

    @Autowired
    private PersonService personService;

    @Autowired
    private ApplicationEventPublisher publisher;

    @GetMapping
    public List<Person> listAll() {
        return personService.findAll();
    }

    @PostMapping
    public ResponseEntity<Person> create(@Valid @RequestBody Person person, HttpServletResponse response) {
        Person personSave = personService.save(person);
        this.publisher.publishEvent(new ResourceCreateEvent(this, response, personSave.getCode()));
        return ResponseEntity.status(HttpStatus.CREATED).body(personSave);
    }

    @GetMapping("/{code}")
    public ResponseEntity<Person> searchCategoryByCode(@PathVariable Long code) {
        Person person = personService.findOne(code);
        return person != null ? ResponseEntity.ok(person) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{code}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remove(@PathVariable Long code) {
        personService.delete(code);
    }


}
