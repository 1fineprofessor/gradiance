package com.uwec.gradiance.service;

import com.uwec.gradiance.dao.GeneralDao;
import com.uwec.gradiance.database.Users;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class GeneralDaoUserDetailsService implements UserDetailsService {
    private final GeneralDao dao;

    public GeneralDaoUserDetailsService(GeneralDao dao) {
        this.dao = dao;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        // Now searching by email, since username no longer exists
        Users u = dao.findUserByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + email));

        System.out.println(">>> Logging in as: " + email);
        System.out.println(">>> Role from DB: " + u.getRole());


        String roleName = u.getRole().name().toUpperCase();

        return User.builder()
                .username(u.getEmail())       // use email as username
                .password(u.getPassword())    // password_hash in DB maps to getPassword()
                .roles(roleName)              // pass single role string, no array needed
                .build();
    }
}
