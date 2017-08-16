package com.richard.money.api.service;

import com.richard.money.api.model.Person;
import com.richard.money.api.repository.PersonRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PersonService {

    @Autowired
    private PersonRepository personRepository;

    public Person save(Person person) {
        return personRepository.save(person);
    }

    public List<Person> findAll() {
        return personRepository.findAll();
    }

    public Person findOne(Long code) {
        return searchPersonByCode(code);
    }

    public void delete(Long code) {
        personRepository.delete(code);
    }

    public Person update(Long code, Person person) {
        Person personSave = searchPersonByCode(code);
        BeanUtils.copyProperties(person, personSave, "code");
        personRepository.save(personSave);
        return personSave;
    }

    public void updatePropertyActive(Long code, Boolean active) {
        Person person = searchPersonByCode(code);
        person.setActive(active);
        personRepository.save(person);
    }

    private Person searchPersonByCode(Long code) {
        Person personSave = personRepository.findOne(code);

        if (null == personSave) {
            throw new EmptyResultDataAccessException(1);
        }
        return personSave;
    }

    public Page<Person> findByNameContaining(String name, Pageable pageable) {
        return personRepository.findByNameContaining(name, pageable);
    }
}
