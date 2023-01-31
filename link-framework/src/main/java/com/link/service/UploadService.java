package com.link.service;

import com.link.domain.Result;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author Link
 * @Description
 * @date 2023-01-29 10:40
 */
public interface UploadService {
    Result uploadImg(MultipartFile img);
}
