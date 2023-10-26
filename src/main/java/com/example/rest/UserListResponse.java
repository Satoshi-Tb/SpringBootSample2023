package com.example.rest;

import java.util.List;

import com.example.domain.user.model.MUser;

import lombok.Data;

@Data
public class UserListResponse {
	private List<MUser> userList;
	private String code;
	private String message;
}
