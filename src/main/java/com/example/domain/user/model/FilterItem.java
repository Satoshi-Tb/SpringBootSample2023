package com.example.domain.user.model;

import lombok.Data;

@Data
public class FilterItem {
	private String filterValue;
	private String filterLabel;
	private Integer count;
}
