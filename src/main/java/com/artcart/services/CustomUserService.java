package com.artcart.services;

import com.artcart.model.SingInAndSingUp;
import com.artcart.repository.SingInAndSingUpRepo;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class CustomUserService implements UserDetailsService {

    private SingInAndSingUpRepo singInAndSingUpRepo;
    public CustomUserService(SingInAndSingUpRepo singInAndSingUpRepo){
        this.singInAndSingUpRepo = singInAndSingUpRepo;
    }
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        SingInAndSingUp singInAndSingUp = singInAndSingUpRepo.findByEmail(username);
        if(singInAndSingUp == null)
            return null;
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(singInAndSingUp.getRole()));
        return new org.springframework.security.core.userdetails.User(singInAndSingUp.getEmail(),singInAndSingUp.getPassword(),authorities);
    }
}
