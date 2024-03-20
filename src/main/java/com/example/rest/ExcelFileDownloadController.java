package com.example.rest;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.domain.user.model.MUser;
import com.example.domain.user.service.ExcelFileDownloadService;
import com.example.domain.user.service.UserService;

@RestController
@RequestMapping("/api/download/excel")
public class ExcelFileDownloadController {

	@Autowired
	private ExcelFileDownloadService excelFileSampleService;
	
	@Autowired
	private UserService userService;
	
	private static HttpHeaders createHeaders(String filename) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.setContentDispositionFormData(filename, filename);
        headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");
        return headers;
	}
	
    @GetMapping("/sample")
    public ResponseEntity<byte[]> downloadExcelFile() throws IOException {
        ByteArrayInputStream in = excelFileSampleService.generateExcelFile();
        
        byte[] content = in.readAllBytes();

        HttpHeaders headers = createHeaders("sample.xlsx");

        return new ResponseEntity<>(content, headers, HttpStatus.OK);
    }
    
    @GetMapping("/userlist")
    public ResponseEntity<byte[]> downloadUserList() throws IOException {
		List<MUser> userList = userService.getUsers(null);
		
        ByteArrayInputStream in = excelFileSampleService.generateUserListExcel(userList);
        
        byte[] content = in.readAllBytes();

        HttpHeaders headers = createHeaders("userlist.xlsx");

        return new ResponseEntity<>(content, headers, HttpStatus.OK);
    }
}
