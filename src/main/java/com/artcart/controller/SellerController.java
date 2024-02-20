package com.artcart.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/seller")
@PreAuthorize("hasAuthority('ROLE_SELLER')")
public class SellerController {

    @GetMapping
    public String test(){
        return "test of seller";
    }
}
