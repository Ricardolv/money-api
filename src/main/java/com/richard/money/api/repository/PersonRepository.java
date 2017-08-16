package com.richard.money.api.repository;

import com.richard.money.api.model.Person;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonRepository  extends JpaRepository<Person, Long> {

    Page<Person> findByNameContaining(String name, Pageable pageable);

}
