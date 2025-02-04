package com.example.rest.sandbox.model;

import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

public class GridDynamicColumnModel {

	public record RowData(String id, String category, String item, List<DetailItem> detailItems) {};
	
	@JsonSerialize(using = RowDataV2Serializer.class)
	public record RowDataV2(String id, String category, String item, Map<String, Object> detailItems) {};
	public record DetailItem(String id, String gridFieldName, String fieldName, String value) {};
	
	@JsonSerialize(using = RowDataV3Serializer.class)
	public record RowDataV3(String id, String category, String item, List<DetailItem> detailItems) {};
	public record DetailItemSub(String id, String fieldName, String value) {};

}
