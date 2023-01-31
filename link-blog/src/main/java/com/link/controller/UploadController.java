package com.link.controller;

import com.link.domain.Result;
import com.link.service.UploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author Link
 * @Description
 * @date 2023-01-29 10:31
 */
@RestController
public class UploadController {

    @Autowired
    private UploadService uploadService;

    @PostMapping("/upload")
    public Result uploadImg(@RequestBody MultipartFile img) {
        return uploadService.uploadImg(img);
    }
}
