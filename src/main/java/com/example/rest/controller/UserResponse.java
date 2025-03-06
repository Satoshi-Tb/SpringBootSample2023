package com.example.rest.controller;

import com.example.domain.user.model.MUser;

import lombok.Data;

@Data
public class UserResponse {
	private MUser user;
	private String nextUserId;
	private String beforeUserId;
}
