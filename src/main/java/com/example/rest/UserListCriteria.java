package com.example.rest;

import lombok.Data;

@Data
public class UserListCriteria {
    private String userId;
    private String userName;
    private Integer offset;
    private Integer page;
    private Integer size;
}
