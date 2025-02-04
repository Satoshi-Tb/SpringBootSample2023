package com.example.rest.sandbox.controller;

import java.util.List;

import com.example.domain.sandbox.griddynamiccolumn.ColumnDefinition;
import com.example.rest.sandbox.model.GridDynamicColumnModel.RowDataV2;

import lombok.Data;

@Data
public class GridDynamicColumnResponseV3 {
	private List<ColumnDefinition> colDefData;
	private List<RowDataV2> rowData;
}
