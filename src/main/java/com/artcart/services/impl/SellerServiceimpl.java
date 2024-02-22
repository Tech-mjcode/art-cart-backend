package com.artcart.services.impl;

import com.artcart.model.Seller;
import com.artcart.repository.SellerRepo;
import com.artcart.response.SellerDto;
import com.artcart.services.SellerService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class SellerServiceimpl implements SellerService {

    private SellerRepo sellerRepo;
    private ModelMapper modelMapper;
    public SellerServiceimpl(SellerRepo sellerRepo , ModelMapper modelMapper){
        this.sellerRepo = sellerRepo;
        this.modelMapper = modelMapper;
    }
    @Override
    public SellerDto create(SellerDto sellerDto) {
        Seller seller = modelMapper.map(sellerDto, Seller.class);
        Seller byEmail = sellerRepo.findByEmail(sellerDto.getEmail());
        //update data
        byEmail.setName(seller.getName());
        byEmail.setAadhaarImage(seller.getAadhaarImage());
        byEmail.setProfileImage(seller.getProfileImage());
        byEmail.setPhoneNumber(seller.getPhoneNumber());
        byEmail.setAadhaarNo(seller.getAadhaarNo());
        Seller save = sellerRepo.save(byEmail);
        return modelMapper.map(save,SellerDto.class);
    }

    @Override
    public SellerDto getSellerByEmail(String email) {
        Seller byEmail = sellerRepo.findByEmail(email);
        return modelMapper.map(byEmail,SellerDto.class);
    }
}
