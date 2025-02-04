package com.example.rest.sandbox.model;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.domain.sandbox.griddynamiccolumn.ColumnDefinition;
import com.example.domain.sandbox.griddynamiccolumn.Option;
import com.example.rest.sandbox.model.GridDynamicColumnModel.DetailItem;
import com.example.rest.sandbox.model.GridDynamicColumnModel.RowData;
import com.example.rest.sandbox.model.GridDynamicColumnModel.RowDataV3;

@Service
public class GridDynamicColumnService {
	public List<ColumnDefinition>getColumnDefinitions(String id) {
		var result = List.of(
				new ColumnDefinition("freeItem1", "itemId", "商品ID",  "1", null),
				new ColumnDefinition("freeItem2", "unit", "単位", "1", null),
				new ColumnDefinition("freeItem3", "price", "価格", "1", null),
				new ColumnDefinition("freeItem4", "vegetableType", "野菜の種類", "2", List.of(
						new Option("vegetableTypeOption", "1", "緑黄色野菜"),
						new Option("vegetableTypeOption", "2", "淡色野菜")
				))
		);
		
		return result;
	}
	
	public List<RowData> getRowData(String id) {
		var result = List.of(
				new RowData("1", "野菜", "キャベツ", List.of(
						new DetailItem("101", "freeItem1", "itemId", "11"),
						new DetailItem("102", "freeItem2", "unit", "1玉"),
						new DetailItem("103", "freeItem3", "price", "300"),
						new DetailItem("104", "freeItem4", "vegetableType", "2")
				)),
				new RowData("2", "野菜", "ほうれん草", List.of(
						new DetailItem("201", "freeItem1", "itemId", "12"),
						new DetailItem("202", "freeItem2", "unit", "1束"),
						new DetailItem("203", "freeItem3", "price", "200"),
						new DetailItem("204", "freeItem4", "vegetableType", "1")
				)),
				new RowData("3", "野菜", "ブロッコリー", List.of(
						new DetailItem("301", "freeItem1", "itemId", "13"),
						new DetailItem("302", "freeItem2", "unit", "1個"),
						new DetailItem("303", "freeItem3", "price", "300"),
						new DetailItem("304", "freeItem4", "vegetableType", "1")
				))
			);
		return result;
	}
	
	public List<RowDataV3> getRowDataV3(String id) {
		var result = List.of(
				new RowDataV3("1", "野菜", "キャベツ", List.of(
						new DetailItem("101", "freeItem1", "itemId", "11"),
						new DetailItem("102", "freeItem2", "unit", "1玉"),
						new DetailItem("103", "freeItem3", "price", "300"),
						new DetailItem("104", "freeItem4", "vegetableType", "2")
				)),
				new RowDataV3("2", "野菜", "ほうれん草", List.of(
						new DetailItem("201", "freeItem1", "itemId", "12"),
						new DetailItem("202", "freeItem2", "unit", "1束"),
						new DetailItem("203", "freeItem3", "price", "200"),
						new DetailItem("204", "freeItem4", "vegetableType", "1")
				)),
				new RowDataV3("3", "野菜", "ブロッコリー", List.of(
						new DetailItem("301", "freeItem1", "itemId", "13"),
						new DetailItem("302", "freeItem2", "unit", "1個"),
						new DetailItem("303", "freeItem3", "price", "300"),
						new DetailItem("304", "freeItem4", "vegetableType", "1")
				))
			);
		return result;
	}
}
