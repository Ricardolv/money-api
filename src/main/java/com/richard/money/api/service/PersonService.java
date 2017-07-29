package com.richard.money.api.service;

import com.richard.money.api.model.Person;
import com.richard.money.api.repository.PersonRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
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
        return personRepository.findOne(code);
    }

    public void delete(Long code) {
        personRepository.delete(code);
    }

    public Person update(Long code, Person person) {
        Person personSave = personRepository.findOne(code);

        if (null == personSave) {
            throw new EmptyResultDataAccessException(1);
        }

        BeanUtils.copyProperties(person, personSave, "code");
        personRepository.save(personSave);
        return personSave;
    }
}
