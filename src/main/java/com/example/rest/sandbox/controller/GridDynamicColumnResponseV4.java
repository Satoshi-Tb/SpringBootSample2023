package com.example.rest.sandbox.controller;

import java.util.List;

import com.example.domain.sandbox.griddynamiccolumn.ColumnDefinition;
import com.example.rest.sandbox.model.GridDynamicColumnModel.RowDataV3;

import lombok.Data;

@Data
public class GridDynamicColumnResponseV4 {
	private List<ColumnDefinition> colDefData;
	private List<RowDataV3> rowData;
}
