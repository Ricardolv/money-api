package com.richard.money.api.security;

import com.richard.money.api.model.User;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public class UserSystem extends org.springframework.security.core.userdetails.User {

    @Getter
    private User user;

    public UserSystem(User user, Collection<? extends GrantedAuthority> authorities) {
        super(user.getEmail(), user.getPassword(), authorities);
        this.user = user;
    }
}
