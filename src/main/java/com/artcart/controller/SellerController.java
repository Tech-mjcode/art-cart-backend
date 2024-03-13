package com.artcart.controller;

import com.artcart.config.JwtTokenProvider;
import com.artcart.exception.CloudinaryImageUploadException;
import com.artcart.exception.UserNotFound;
import com.artcart.model.Seller;
import com.artcart.repository.SellerRepo;
import com.artcart.response.SellerDto;
import com.artcart.services.CloudinaryImageUpload;
import com.artcart.services.SellerService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@RestController
@RequestMapping("/api/seller")
@PreAuthorize("hasAuthority('ROLE_SELLER')")
public class SellerController {

    private SellerService sellerService;
    private JwtTokenProvider jwtTokenProvider;
    private SellerRepo sellerRepo;
    private CloudinaryImageUpload cloudinaryImageUpload;
    private ObjectMapper objectMapper;

    private Logger logger = LoggerFactory.getLogger(SellerController.class);
    public SellerController(SellerService sellerService , JwtTokenProvider jwtTokenProvider,SellerRepo sellerRepo,
    CloudinaryImageUpload cloudinaryImageUpload,ObjectMapper objectMapper){
        this.sellerService =sellerService;
        this.jwtTokenProvider = jwtTokenProvider;
        this.sellerRepo = sellerRepo;
        this.cloudinaryImageUpload = cloudinaryImageUpload;
        this.objectMapper = objectMapper;
    }

    @GetMapping
    public ResponseEntity<SellerDto> getSellerHandler(@RequestHeader("Authorization") String jwtToken) throws Exception{
        String emailFromToken = jwtTokenProvider.getEmailFromToken(jwtToken);
        SellerDto sellerByEmail = sellerService.getSellerByEmail(emailFromToken);
        return new ResponseEntity<>(sellerByEmail,HttpStatus.OK);
    }

    @PostMapping("/save")
    public ResponseEntity<SellerDto> createHandler(@RequestParam("aadhaarImage")MultipartFile aadhaarImage,
                                                   @RequestParam("profileImage") MultipartFile profileImage,
                                                   @RequestParam("data") String data,
                                                   @RequestHeader("Authorization") String jwtToken) throws Exception{

        //getting seller email from jwt token
        String emailFromToken = jwtTokenProvider.getEmailFromToken(jwtToken);
        //getting seller object from above email
        Seller byEmail = sellerRepo.findByEmail(emailFromToken);
        if(byEmail == null)
            throw new UserNotFound("user not found");
        try{
            Map<Object, Object> cloudAadhaarImage = cloudinaryImageUpload.imageUpload(aadhaarImage);
            Map<Object, Object> cloudProfileImage = cloudinaryImageUpload.imageUpload(profileImage);

            SellerDto sellerDto = objectMapper.readValue(data, SellerDto.class);
            sellerDto.setId(byEmail.getId());
            sellerDto.setEmail(byEmail.getEmail());
            sellerDto.setAadhaarImage(cloudAadhaarImage.get("url").toString());
            sellerDto.setProfileImage(cloudProfileImage.get("url").toString());
            SellerDto sellerDto1 = sellerService.create(sellerDto);
            return new ResponseEntity<>(sellerDto1, HttpStatus.CREATED);
        }catch (Exception e){
            throw new CloudinaryImageUploadException("file uploaded failed");
        }
    }

    @GetMapping("/test")
    public String home (){
        return  "home";
    }
}
