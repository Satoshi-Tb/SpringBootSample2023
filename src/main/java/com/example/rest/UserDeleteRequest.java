package com.example.rest;

import java.util.List;

import lombok.Data;

@Data
public class UserDeleteRequest {
	private List<String> userIdList;
}
