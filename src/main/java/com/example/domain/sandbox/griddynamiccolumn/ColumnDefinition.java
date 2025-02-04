package com.example.domain.sandbox.griddynamiccolumn;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ColumnDefinition {
	private String gridFieldName;
	private String fieldName;
	private String label;
	private String inputType;
	private List<Option> options;
}
