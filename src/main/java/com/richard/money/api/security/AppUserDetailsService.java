package com.richard.money.api.security;

import com.richard.money.api.model.User;
import com.richard.money.api.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class AppUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MessageSource messageSource;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        Optional<User> userOptional = userRepository.findByEmail(email);

        String messageUser = messageSource.getMessage("message.invalid_user", null, LocaleContextHolder.getLocale());
        User user = userOptional.orElseThrow(() -> new UsernameNotFoundException(messageUser));

        return new org.springframework.security.core.userdetails.User(email, user.getPassword(), getPermissions(user));
    }

    private Collection<? extends GrantedAuthority> getPermissions(User user) {
        Set<SimpleGrantedAuthority> authorities = new HashSet<>();
        user.getPermissions().forEach(p -> authorities.add(new SimpleGrantedAuthority(p.getDescription().toUpperCase())));
        return authorities;
    }
}
