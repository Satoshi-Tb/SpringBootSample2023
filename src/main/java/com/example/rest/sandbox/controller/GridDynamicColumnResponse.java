package com.example.rest.sandbox.controller;

import java.util.List;

import com.example.domain.sandbox.griddynamiccolumn.ColumnDefinition;
import com.example.rest.sandbox.model.GridDynamicColumnModel.RowData;

import lombok.Data;

@Data
public class GridDynamicColumnResponse {
	private List<ColumnDefinition> colDefData;
	private List<RowData> rowData;
}
