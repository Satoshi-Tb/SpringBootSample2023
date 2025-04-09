package com.example.rest.sandbox.model;

import java.util.List;

public class GridDynamicColumnExcelDataModel {

	public record RowData(String id, String category, String item, List<DetailItem> detailItems) {};
	
	public record DetailItem(String id, String fieldName, String value, Integer columnWidth) {};
}
