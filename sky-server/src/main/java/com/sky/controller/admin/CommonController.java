package com.sky.controller.admin;

import com.sky.result.Result;
import com.sky.utils.AliOssUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * 通用接口
 */
@RestController
@Slf4j
@RequestMapping("/admin/common")
public class CommonController {
    @Autowired
    private AliOssUtil aliOssUtil;

    @PostMapping("/upload")
    public Result upload(MultipartFile file){
        log.info("文件上传：{}",file.getOriginalFilename());
        //获取原始文件名
        String originalFilename = file.getOriginalFilename();
        try {
            String url = aliOssUtil.upload(file.getBytes(), originalFilename);
            return Result.success(url);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
