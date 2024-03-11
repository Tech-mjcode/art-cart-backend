package com.artcart.controller;


import com.artcart.exception.UserNotFound;
import com.artcart.model.Seller;
import com.artcart.repository.SellerRepo;
import com.artcart.response.SellerDto;
import com.artcart.services.AdminServices;
import com.artcart.services.SellerService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/admin")
@PreAuthorize("hasAuthority('ROLE_ADMIN')")
public class AdminController {


    private AdminServices adminServices;
    private SellerRepo sellerRepo;
    private SellerService sellerService;
    private ModelMapper modelMapper;
    public AdminController(AdminServices adminServices , SellerRepo sellerRepo , SellerService sellerService,ModelMapper modelMapper){
        this.adminServices = adminServices;
        this.sellerRepo = sellerRepo;
        this.sellerService = sellerService;
        this.modelMapper = modelMapper;
    }

    @GetMapping
    public String test(){
        return "test of admin";
    }


    @GetMapping("/all-unapproved-seller")
    public ResponseEntity<List<SellerDto>> handlerForUnapprovedSeller(@RequestHeader("Authorization") String token){
        List<SellerDto> allUnApprovedSeller = adminServices.getAllUnApprovedSeller();
        return new ResponseEntity<>(allUnApprovedSeller, HttpStatus.OK);
    }

    @PutMapping("/approve-seller/{sellerId}")
    public ResponseEntity<Map<String, String>> handlerForApprovedSingleSeller(@PathVariable Integer sellerId){
        Seller seller = sellerRepo.findById(sellerId).orElseThrow(() -> new UserNotFound("seller not found with id " + sellerId));
        seller.setApproved(true);
        sellerRepo.save(seller);
        Map<String,String> res = new HashMap<>();
        res.put("message","successfully approved");
        res.put("status",HttpStatus.OK.toString());
        return new ResponseEntity<>(res,HttpStatus.OK);
    }

    @GetMapping("/seller-details")
    public ResponseEntity<List<SellerDto>> getAllSellerDetails(){

        List<Seller> all = sellerRepo.findAll();
        List<SellerDto> res =  new ArrayList<>();
        all.stream().map(item->res.add(modelMapper.map(item, SellerDto.class))
        ).collect(Collectors.toList());
        return new ResponseEntity<>(res,HttpStatus.OK);
    }

}
