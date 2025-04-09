package com.example.rest.sandbox.service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import com.example.rest.sandbox.model.GridDynamicColumnModel.DetailItem;
import com.example.rest.sandbox.model.GridDynamicColumnModel.RowData;

@Service
public class GridDynamicColumnExcelFileCreateService {
	
    public ByteArrayInputStream createExcelData() throws IOException {
        try (Workbook workbook = new XSSFWorkbook()) {
        	
            Sheet sheet = workbook.createSheet("UserList");

            // テストデータ取得（本来、DBから取得）
        	var data = getRowData("1");
            
        	int rowCnt = 1;
        	
        	for (RowData r : data) {
        		if (rowCnt == 1) {
        			// 1行目の場合、ヘッダ作成
                    Row headerRow = sheet.createRow(0);
                    headerRow.createCell(0).setCellValue("ID");
                    headerRow.createCell(1).setCellValue("カテゴリ");
                    headerRow.createCell(2).setCellValue("アイテム");

                    
                    // 可変項目
    			    int colOffset = 1;
    			    for (DetailItem item : r.detailItems()) {
    			    	headerRow.createCell(2 + colOffset).setCellValue(item.fieldName());
    				    colOffset++;
    			    }
        		}
        		
			    Row row = sheet.createRow(rowCnt);
			    row.createCell(0).setCellValue(r.id());
			    row.createCell(1).setCellValue(r.category());
			    row.createCell(2).setCellValue(r.item());

			    int colOffset = 1;
			    for (DetailItem item : r.detailItems()) {
				    row.createCell(2 + colOffset).setCellValue(item.value());
				    colOffset++;
			    }
			    // 可変項目
			    rowCnt++;
        	}


            ByteArrayOutputStream out = new ByteArrayOutputStream();
            workbook.write(out);
            return new ByteArrayInputStream(out.toByteArray());
        }
    }
	
	private static List<RowData> getRowData(String id) {
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

}
