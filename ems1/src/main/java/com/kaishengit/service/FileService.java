package com.kaishengit.service;

import java.io.InputStream;

/**
 * Created by liu on 2017/2/17.
 */
public interface FileService {
    String uploadFile(String originalFilename, String contentType, InputStream inputStream);
}
