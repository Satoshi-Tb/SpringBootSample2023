package com.example.repositry;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.example.domain.user.model.Department;

@Mapper
public interface DeptMapper {
	public List<Department> findAll();
}
