package com.scm.service;

import org.springframework.web.multipart.MultipartFile;

public interface ImageService {
       String uploadImage(MultipartFile contactImg, String fileName);
       String getUrlFromPublicId(String publicId);
}
