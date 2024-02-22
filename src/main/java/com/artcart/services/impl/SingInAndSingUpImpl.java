package com.artcart.services.impl;


import com.artcart.model.Seller;
import com.artcart.model.SingInAndSingUp;
import com.artcart.repository.SellerRepo;
import com.artcart.repository.SingInAndSingUpRepo;
import com.artcart.request.SignUpRequest;
import com.artcart.request.SignInRequest;
import com.artcart.services.CustomUserService;
import com.artcart.services.SingInAndSingUpService;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class SingInAndSingUpImpl implements SingInAndSingUpService {

    private SingInAndSingUpRepo singInAndSingUpRepo;
    private PasswordEncoder passwordEncoder;
    private CustomUserService customUserService;

    private SellerRepo sellerRepo;

    public SingInAndSingUpImpl (SingInAndSingUpRepo singInAndSingUpRepo,PasswordEncoder passwordEncoder ,
                                CustomUserService customUserService,SellerRepo sellerRepo){
        this.singInAndSingUpRepo = singInAndSingUpRepo;
        this.passwordEncoder = passwordEncoder;
        this.customUserService = customUserService;
        this.sellerRepo = sellerRepo;
    }
    @Override
    public SingInAndSingUp signUp(SignUpRequest signUpRequest) {
        SingInAndSingUp byEmail = singInAndSingUpRepo.findByEmail(signUpRequest.getEmail());
        if(byEmail != null){
            return null;
        }
        SingInAndSingUp singInAndSingUp = new SingInAndSingUp();
        singInAndSingUp.setEmail(signUpRequest.getEmail());
        singInAndSingUp.setPassword(passwordEncoder.encode(signUpRequest.getPassword()));
        singInAndSingUp.setRole("ROLE_"+signUpRequest.getRole().toUpperCase());

        if(singInAndSingUp.getRole().compareTo("ROLE_SELLER")==0){
            Seller seller = new Seller();
            seller.setEmail(singInAndSingUp.getEmail());
            seller.setRegDate(LocalDateTime.now());
            sellerRepo.save(seller);
        }

        return singInAndSingUpRepo.save(singInAndSingUp);

    }

    @Override
    public SingInAndSingUp signIn(SignInRequest singInRequest) {
        return null;
    }

    public Authentication authenticate(String username, String password){
        UserDetails userDetails = customUserService.loadUserByUsername(username);
        if(userDetails == null){
            throw new BadCredentialsException("invalid username");
        }
        if(!passwordEncoder.matches(password,userDetails.getPassword())){
            throw new BadCredentialsException("invalid password");
        }
        return new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
    }
}
