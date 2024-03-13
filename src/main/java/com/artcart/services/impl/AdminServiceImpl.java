package com.artcart.services.impl;

import com.artcart.model.Seller;
import com.artcart.repository.SellerRepo;
import com.artcart.response.SellerDto;
import com.artcart.services.AdminServices;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AdminServiceImpl implements AdminServices {


    private ModelMapper modelMapper;
    private SellerRepo sellerRepo;
    public AdminServiceImpl(ModelMapper modelMapper, SellerRepo sellerRepo){
        this.modelMapper = modelMapper;
        this.sellerRepo = sellerRepo;
    }
    @Override
    public void approveSeller(SellerDto sellerDto) {
        Seller seller = modelMapper.map(sellerDto, Seller.class);
        seller.setApproved(true);
        sellerRepo.save(seller);
    }

    @Override
    public void addCategories() {

    }

    @Override
    public List<SellerDto> getAllUnApprovedSeller() {
        List<Seller> byApproved = sellerRepo.findByApproved(false);
        List<SellerDto> sellerDtos = new ArrayList<>();
        byApproved.stream().map((item)->{
           return  sellerDtos.add(modelMapper.map(item, SellerDto.class));
        }).collect(Collectors.toList());

        return  sellerDtos;
    }
}
