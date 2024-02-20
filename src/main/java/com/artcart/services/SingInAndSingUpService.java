package com.artcart.services;

import com.artcart.model.SingInAndSingUp;
import com.artcart.request.SignUpRequest;
import com.artcart.request.SignInRequest;

public interface SingInAndSingUpService {
    SingInAndSingUp signUp(SignUpRequest signUpRequest);
    SingInAndSingUp signIn(SignInRequest singInRequest);
}
