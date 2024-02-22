package com.artcart.services;

import org.springframework.web.multipart.MultipartFile;

import java.util.Map;
import java.util.Objects;

public interface CloudinaryImageUpload {

    Map<Object ,Object> imageUpload(MultipartFile multipartFile) throws Exception;
}
