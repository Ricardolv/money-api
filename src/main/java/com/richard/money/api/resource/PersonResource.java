package com.richard.money.api.resource;

import com.richard.money.api.model.Person;
import com.richard.money.api.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/persons")
public class PersonResource {

    @Autowired
    private PersonService personService;

    @GetMapping
    public List<Person> listAll() {
        return personService.findAll();
    }

    @PostMapping
    public ResponseEntity<Person> create(@Valid @RequestBody Person person, HttpServletResponse response) {
        Person personSave = personService.save(person);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{code}")
                .buildAndExpand(personSave.getCode()).toUri();

        response.setHeader("Location", uri.toASCIIString());

        return ResponseEntity.created(uri).body(personSave);
    }

    @GetMapping("/{code}")
    public ResponseEntity<Person> searchCategoryByCode(@PathVariable Long code) {
        Person person = personService.findOne(code);
        return person != null ? ResponseEntity.ok(person) : ResponseEntity.notFound().build();
    }

}
