package com.example.rest.controller;

import java.util.Date;

import lombok.Data;

@Data
public class UserRequest {
	private String id;
    private String userId;
    private String password;
    private String userName;
    private Date birthday;
    private Integer age;
    private Integer gender;
    private String profile;
    private Integer departmentId;
    private String updateMode;
}
