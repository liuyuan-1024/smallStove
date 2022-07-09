package com.bug.api.controller.common;

import com.bug.framework.models.Result;
import com.bug.framework.service.UploadService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;

@Api(tags = "上传接口")
@RestController
public class UploadController {

    @Resource
    private UploadService uploadService;

    @ApiOperation(value = "用户头像", notes = "用户上传头像")
    @PostMapping("/upload-avatar")
    public Result<?> uploadAvatar(MultipartFile avatar) {
        return uploadService.uploadImage(avatar);
    }

    @ApiOperation(value = "上传图片", notes = "可一次上传多张图片")
    @PostMapping("/upload-images")
    public Result<?> uploadImages(MultipartFile[] images) {
        return uploadService.uploadImages(images);
    }
}
