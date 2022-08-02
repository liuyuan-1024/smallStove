package com.bug.framework.service;

import com.bug.framework.models.Result;
import org.springframework.web.multipart.MultipartFile;

/**
 * @Author: BugOS-ly
 * @Date: 2022/7/15 21:19
 * @Description: 七牛云上传文件业务逻辑接口
 */
public interface UploadService {

    String uploadOss(MultipartFile imageFile, String key);

    Result<?> uploadImage(MultipartFile image);

    Result<?> uploadImages(MultipartFile[] images);
}
