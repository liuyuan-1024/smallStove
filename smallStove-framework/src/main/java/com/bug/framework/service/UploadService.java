package com.bug.framework.service;

import com.bug.framework.models.Result;
import org.springframework.web.multipart.MultipartFile;


public interface UploadService {

    String uploadOss(MultipartFile imageFile, String key);

    Result<?> uploadImage(MultipartFile image);

    Result<?> uploadImages(MultipartFile[] images);
}
