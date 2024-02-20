package com.artcart.repository;

import com.artcart.model.SingInAndSingUp;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SingInAndSingUpRepo extends JpaRepository<SingInAndSingUp,Integer> {

    SingInAndSingUp findByEmail(String email);
}
