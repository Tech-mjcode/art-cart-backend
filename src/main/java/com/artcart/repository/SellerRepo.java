package com.artcart.repository;

import com.artcart.model.Seller;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SellerRepo extends JpaRepository<Seller,Integer> {

    Seller findByEmail(String email);
}
