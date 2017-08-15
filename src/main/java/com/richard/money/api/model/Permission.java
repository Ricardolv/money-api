package com.richard.money.api.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@EqualsAndHashCode(of = "code")
@Entity
@Table(name = "permission")
public class Permission {

    @Id
    private Long code;
    private String description;
}
