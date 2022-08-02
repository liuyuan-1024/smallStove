package com.bug.framework.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.bug.framework.constant.SystemConstant;
import com.bug.framework.models.Result;
import com.bug.framework.models.ResultBuilder;
import com.bug.framework.models.ResultEnum;
import com.bug.framework.service.UploadService;
import com.bug.framework.utils.PathUtils;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.util.Auth;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author: BugOS-ly
 * @Date: 2022/7/15 21:19
 * @Description: 七牛云上传文件业务逻辑实现类
 */
@Service
public class UploadServiceImpl implements UploadService {

    // 七牛云域名
    @Value("${oss.domainName}")
    String domainName;

    //...生成上传凭证
    @Value("${oss.accessKey}")
    String accessKey;
    @Value("${oss.secretKey}")
    String secretKey;
    @Value("${oss.bucket}")
    String bucket;

    /**
     * 上传文件到七牛云
     *
     * @param imageFile 待上传文件, 默认不指定key的情况下，以文件内容的hash值作为文件名
     * @param key       文件在七牛云中的key(唯一)
     * @return 文件的外链连接
     */
    @Override
    public String uploadOss(MultipartFile imageFile, String key) {
        //构造一个带指定 Region 对象的配置类
        Configuration cfg = new Configuration();
        UploadManager uploadManager = new UploadManager(cfg);

        Auth auth = Auth.create(accessKey, secretKey);
        String upToken = auth.uploadToken(bucket);

        try {
            Response response = uploadManager.put(imageFile.getInputStream(), key, upToken, null, null);
            //解析上传成功的结果
            JSONObject parse = JSON.parseObject(response.bodyString());
            return new StringBuilder().append(domainName).append(parse.get("key")).toString();
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public Result<String> uploadImage(MultipartFile image) {
        String filename = image.getOriginalFilename();
        // 判断文件是否为空
        if (ObjectUtils.isEmpty(filename)) {
            return new ResultBuilder().error(ResultEnum.UPLOAD_FAIL, "上传图片为空,请重新上传!");
        }
        // 判断文件类型和大小
        if (!filename.endsWith(SystemConstant.JPG)) {
            return new ResultBuilder().error(ResultEnum.UPLOAD_FAIL, "图片格式不正确, 应是jpg,jpeg,png!且单张大小不超过2MB");
        }

        // 允许上传
        String url = uploadOss(image, PathUtils.generateFilePath(filename));
        return new ResultBuilder().success(ResultEnum.UPLOAD_SUCCESS, url);
    }

    @Override
    public Result<?> uploadImages(MultipartFile[] images) {
        List<Object> urls = new ArrayList<>();

        for (MultipartFile image : images) {
            Result<?> result = this.uploadImage(image);
            if (!String.valueOf(result.getCode()).startsWith("200")) {
                return result;
            }
            urls.add(result.getData());
        }

        return new ResultBuilder().success(ResultEnum.UPLOAD_SUCCESS, urls);
    }
}
