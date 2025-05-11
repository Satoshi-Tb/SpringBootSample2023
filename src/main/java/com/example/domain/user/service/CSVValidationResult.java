package com.example.domain.user.service;

import java.util.ArrayList;
import java.util.List;

import com.example.domain.user.model.MUser;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CSVValidationResult {
    private boolean valid = true;
    private List<String> errors = new ArrayList<>();
    private int lineNumber;
    private MUser user;

    public void addError(String error) {
        this.valid = false;
        this.errors.add(error);
    }
}