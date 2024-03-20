package com.example.rest;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.domain.user.service.ExcelFileSampleService;

@RestController
@RequestMapping("/api/download/excel")
public class ExcelFileDownloadController {

	@Autowired
	private ExcelFileSampleService excelFileSampleService;
	
    @GetMapping("/sample")
    public ResponseEntity<byte[]> downloadExcelFile() throws IOException {
        ByteArrayInputStream in = excelFileSampleService.generateExcelFile();
        
        byte[] content = in.readAllBytes();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        String filename = "sample.xlsx";
        headers.setContentDispositionFormData(filename, filename);
        headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");

        return new ResponseEntity<>(content, headers, HttpStatus.OK);
    }
}
