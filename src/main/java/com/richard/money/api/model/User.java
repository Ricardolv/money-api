package com.richard.money.api.model;


import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.util.List;

@Data
@EqualsAndHashCode(of = "code")
@Entity
@Table(name = "user")
public class User {

    @Id
    private Long code;

    private String name;
    private String email;
    private String password;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_permission", joinColumns = @JoinColumn(name = "code_user")
            , inverseJoinColumns = @JoinColumn(name = "code_permission"))
    private List<Permission> permissions;

}

