package com.example.rest.controller;

import java.util.List;

import com.example.domain.user.model.MCode;

import lombok.Data;

@Data
public class CategoryCodeListResponse  {
	private List<MCode> data;
	private String code;
	private String message;
}
