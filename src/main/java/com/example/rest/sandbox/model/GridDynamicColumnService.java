package com.example.rest.sandbox.model;

import java.util.ArrayList;
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
		if (id.equals("1")) {
			return List.of(
				new ColumnDefinition("freeItem1", "itemId", "商品ID",  "1", null),
				new ColumnDefinition("freeItem2", "unit", "単位", "1", null),
				new ColumnDefinition("freeItem3", "price", "価格", "1", null),
				new ColumnDefinition("freeItem4", "vegetableType", "野菜の種類", "2", List.of(
						new Option("vegetableTypeOption", "1", "緑黄色野菜"),
						new Option("vegetableTypeOption", "2", "淡色野菜")
				))
			);
		}
		
		return List.of(
				new ColumnDefinition("freeItem1", "title", "タイトル", "1", null),
				new ColumnDefinition("freeItem2", "author", "著者", "1", null),
				new ColumnDefinition("freeItem3", "genre", "ジャンル",  "2", List.of(
						new Option("genreOption", "1", "文学"),
						new Option("genreOption", "2", "政治/経済"),
						new Option("genreOption", "3", "コンピュータ"),
						new Option("genreOption", "4", "料理"),
						new Option("genreOption", "5", "その他")
						)),
				new ColumnDefinition("freeItem4", "price", "価格", "1", null),
				new ColumnDefinition("freeItem5", "hasEbookFormat", "電子書籍の有無", "3", List.of(
						new Option("hasEbookFormatOption", "0", "なし"),
						new Option("hasEbookFormatOption", "1", "あり")
				))
			);
	}
	
	public List<RowData> getRowData(String id) {
		if (id.equals("1")) {
			return List.of(
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
		}
		
		return List.of(
				new RowData("1", "書籍", "小説", List.of(
						new DetailItem("111", "freeItem1", "title", "吾輩は猫である"),
						new DetailItem("112", "freeItem2", "authro", "夏目漱石"),
						new DetailItem("113", "freeItem3", "genre", "1"),
						new DetailItem("114", "freeItem4", "price", "700"),
						new DetailItem("115", "freeItem5", "hasEbookFormat", "1")
				)),
				new RowData("2", "書籍", "技術書", List.of(
						new DetailItem("211", "freeItem1", "title", "詳解Java言語"),
						new DetailItem("212", "freeItem2", "authro", "JVM"),
						new DetailItem("213", "freeItem3", "genre", "3"),
						new DetailItem("214", "freeItem4", "price", "3800"),
						new DetailItem("215", "freeItem5", "hasEbookFormat", "0")
						))
			);
	}
	
	public List<RowDataV3> getRowDataV3(String id) {
		var base = getRowData(id);
		
		var list = new ArrayList<RowDataV3>();
		for (RowData src : base) {
			list.add(new RowDataV3(src.id(), src.category(), src.item(), src.detailItems()));
		}
		return list;
	}
}
