package com.example.domain.user.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class CustomMUser extends MUser {

	private String updateMode;
}
