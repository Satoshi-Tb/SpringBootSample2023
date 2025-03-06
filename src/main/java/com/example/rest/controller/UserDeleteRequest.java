package com.example.rest.controller;

import java.util.List;

import lombok.Data;

@Data
public class UserDeleteRequest {
	private List<String> userIdList;
}
