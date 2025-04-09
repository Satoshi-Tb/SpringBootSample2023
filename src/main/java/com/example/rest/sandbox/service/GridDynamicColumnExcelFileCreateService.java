package com.example.rest.sandbox.service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import com.example.rest.sandbox.model.GridDynamicColumnExcelDataModel.DetailItem;
import com.example.rest.sandbox.model.GridDynamicColumnExcelDataModel.RowData;



@Service
public class GridDynamicColumnExcelFileCreateService {
	
	private static int convertPxToColumnWidth(Integer pxWidth) {
	    if (pxWidth == null) {
	        return -1;
	    }
	    
	    double charWidth = pxWidth / 7.0;
	    int columnWidth = (int)(charWidth * 256);
	    
	    // 最小幅と最大幅の制限
	    int minWidth = 5 * 256;   // 最小5文字分
	    int maxWidth = 100 * 256; // 最大100文字分
	    
	    return Math.min(Math.max(columnWidth, minWidth), maxWidth);
	}
	
    public ByteArrayInputStream createExcelData() throws IOException {
        try (Workbook workbook = new XSSFWorkbook()) {
        	
            Sheet sheet = workbook.createSheet("UserList");

            // テストデータ取得（本来、DBから取得）
        	var data = getRowData("1");
        
        	
            // 文字列フォーマット用のCellStyleを作成
            CellStyle textStyle = workbook.createCellStyle();
            DataFormat format = workbook.createDataFormat();
            textStyle.setDataFormat(format.getFormat("@"));  // "@" は文字列フォーマット
        	
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
    			    	
                        // 列幅の設定
                        Integer columnWidth = item.columnWidth();
                        if (columnWidth != null) {
                            sheet.setColumnWidth(2 + colOffset, convertPxToColumnWidth(columnWidth));
                        }
                        
    				    colOffset++;
    			    }
        		}
        		
			    Row row = sheet.createRow(rowCnt);
			    row.createCell(0).setCellValue(r.id());
			    row.createCell(1).setCellValue(r.category());
			    row.createCell(2).setCellValue(r.item());

			    int colOffset = 1;
			    for (DetailItem item : r.detailItems()) {
	                Cell cell = row.createCell(2 + colOffset);
	                cell.setCellValue(item.value());
	                cell.setCellStyle(textStyle); // 文字列形式
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
		return List.of(
				new RowData("1", "野菜", "キャベツ", List.of(
						new DetailItem("101", "freeItem1",  "11", null),
						new DetailItem("102", "freeItem2", "1玉", 100),
						new DetailItem("103", "freeItem3", "300", 100),
						new DetailItem("104", "freeItem4", "2", 50)
				)),
				new RowData("2", "野菜", "ほうれん草", List.of(
						new DetailItem("201", "freeItem1", "12", null),
						new DetailItem("202", "freeItem2", "1束", 100),
						new DetailItem("203", "freeItem3", "200", 100),
						new DetailItem("204", "freeItem4", "1", 50)
				)),
				new RowData("3", "野菜", "ブロッコリー", List.of(
						new DetailItem("301", "freeItem1", "13", null),
						new DetailItem("302", "freeItem2", "1個", 100),
						new DetailItem("303", "freeItem3", "300", 100),
						new DetailItem("304", "freeItem4", "1", 50)
				))
			);
	}

}
