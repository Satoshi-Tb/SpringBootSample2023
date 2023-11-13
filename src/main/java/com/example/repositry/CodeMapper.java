package com.example.repositry;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.example.domain.user.model.MCode;

@Mapper
public interface CodeMapper {
	public List<MCode> findByCategory(String category);
}
