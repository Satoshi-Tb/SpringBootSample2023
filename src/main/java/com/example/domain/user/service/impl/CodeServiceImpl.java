package com.example.domain.user.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.domain.user.model.MCode;
import com.example.domain.user.service.CodeService;
import com.example.repositry.CodeMapper;

@Service
public class CodeServiceImpl implements CodeService {

	@Autowired
	private CodeMapper mapper;
	
	@Override
	public List<MCode> getCategoryCode(String category) {
		return mapper.findByCategory(category);
	}
}
