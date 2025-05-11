package com.example.domain.user.service;

import org.springframework.web.multipart.MultipartFile;

public interface CSVService {
    public boolean processCSVFile(MultipartFile file);
}
