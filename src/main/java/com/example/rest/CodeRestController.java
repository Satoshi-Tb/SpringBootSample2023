package com.example.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.domain.user.service.CodeService;

import jakarta.validation.constraints.NotNull;

@RestController
@RequestMapping("/api/code")
public class CodeRestController {
	
	@Autowired
	private CodeService service;
	
	@GetMapping("/category/{category}")
	public ResponseEntity<CategoryCodeListResponse> getUserList(@PathVariable @NotNull String category) {	
		var result = service.getCategoryCode(category);
		
		var response = new CategoryCodeListResponse();
		response.setData(result);
		response.setCode("0000");
		response.setMessage("");
		
		
		return new ResponseEntity<CategoryCodeListResponse>(response,  HttpStatus.OK);
	}
}
