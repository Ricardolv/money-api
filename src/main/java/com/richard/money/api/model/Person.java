package com.richard.money.api.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Data
@EqualsAndHashCode(of = "code")
@Entity
@Table(name = "person")
@DynamicUpdate
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long code;

    @NotNull
    private String name;

    @Embedded
    private Anddress anddress;

    @NotNull
    private Boolean active;

}
