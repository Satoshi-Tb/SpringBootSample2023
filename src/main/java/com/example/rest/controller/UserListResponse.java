package com.example.rest.controller;

import java.util.List;

import com.example.domain.user.model.MUser;

import lombok.Data;

@Data
public class UserListResponse {
	private List<MUser> data;
	private String code;
	private String message;
	private int totalCount;
}
