package com.example.rest;

import com.example.domain.user.model.MUser;

import lombok.Data;

@Data
public class UserResponse {
	private MUser user;
	private String code;
	private String message;
}
