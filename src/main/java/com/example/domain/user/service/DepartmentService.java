package com.example.domain.user.service;

import java.util.List;

import com.example.domain.user.model.Department;

public interface DepartmentService {
	/** ユーザー取得 */
	public List<Department> getAllDepartments();
}
