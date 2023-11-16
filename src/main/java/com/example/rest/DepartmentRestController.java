package com.example.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.domain.user.model.Department;
import com.example.domain.user.service.DepartmentService;

@RestController
@RequestMapping("/api/department")
public class DepartmentRestController {
	
	@Autowired
	private DepartmentService service;

	@GetMapping("/all")
	public ResponseEntity<RestResponse<List<Department>>> getAllDepartments() {
		var result = service.getAllDepartments();
		return RestResponse.createSuccessResponse(result);
		
	}
}
