package com.example.domain.user.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.domain.user.model.Department;
import com.example.domain.user.service.DepartmentService;
import com.example.repositry.DeptMapper;

@Service
public class DepartmentServiceImpl implements DepartmentService {

	@Autowired
	private DeptMapper mapper;

	@Override
	public List<Department> getAllDepartments() {
		return mapper.findAll();
	}
}
