package com.example.rest;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UserListCriteria {
    private String userId;
    private String userName;
    
    
    private Integer offset;
    
    @NotNull
    private Integer page;
    
    @NotNull
    private Integer size;
}
