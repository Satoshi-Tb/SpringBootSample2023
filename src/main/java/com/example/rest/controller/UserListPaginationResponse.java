package com.example.rest.controller;

import java.util.List;

import com.example.domain.user.model.MUser;

import lombok.Data;

@Data
public class UserListPaginationResponse {
	private List<MUser> userList;
	private int resultNum;
}
