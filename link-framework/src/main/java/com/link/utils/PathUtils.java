package com.link.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

/**
 * @author Link
 * @Description
 * @date 2023-01-29 11:21
 */
public class PathUtils {
    public static void main(String[] args) {
        String filePath = generateFilePath("aa.jpg");
        System.out.println(filePath);
    }

    public static String generateFilePath(String fileName) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd/");
        String datePath = sdf.format(new Date());

        String uuid = UUID.randomUUID().toString().replaceAll("-", "");
        int index = fileName.lastIndexOf(".");
        String fileType = fileName.substring(index);
        return new StringBuilder().append(datePath).append(uuid).append(fileType).toString();
    }
}
