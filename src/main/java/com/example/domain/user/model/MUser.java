package com.example.domain.user.model;

import java.util.Date;
import java.util.List;

import lombok.Data;

@Data
public class MUser {
	private String id;
    private String userId;
    private String password;
    private String userName;
    private Date birthday;
    private Integer age;
    private Integer gender;
    private String genderName;
    private String profile;
    private Integer departmentId;
    private String role;
    private Department department;
    private List<Salary> salaryList;
    private String insUserId;
    private Date insDate;
    private String updUserId;
    private Date updDate;
}