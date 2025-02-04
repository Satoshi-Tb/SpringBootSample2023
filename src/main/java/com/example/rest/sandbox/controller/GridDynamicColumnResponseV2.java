package com.example.rest.sandbox.controller;

import java.util.List;
import java.util.Map;

import com.example.domain.sandbox.griddynamiccolumn.ColumnDefinition;

import lombok.Data;

@Data
public class GridDynamicColumnResponseV2 {
	private List<ColumnDefinition> colDefData;
	private List<Map<String, Object>> rowData;
}
