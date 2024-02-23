package com.artcart.services.impl;

import com.artcart.services.CloudinaryImageUpload;
import com.cloudinary.Cloudinary;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@Service
public class CloudinaryImageUploadImpl implements CloudinaryImageUpload {

    private Cloudinary cloudinary;

    public CloudinaryImageUploadImpl(Cloudinary cloudinary){
        this.cloudinary = cloudinary;
    }
    @Override
    public Map<Object, Object> imageUpload(MultipartFile multipartFile) throws Exception{

        Map<Object,Object> uploadImage = this.cloudinary.uploader().upload(multipartFile.getBytes(), Map.of());
        return uploadImage;
    }
}
