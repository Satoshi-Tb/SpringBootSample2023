package com.example.domain.user.service.impl;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Random;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import com.example.domain.user.model.MUser;
import com.example.domain.user.service.ExcelFileDownloadService;

@Service
public class ExcelFileDownloadServiceImple implements ExcelFileDownloadService {
	private static int MAX_CELL_CHAR_COUNT = 32767;
	
	
    public ByteArrayInputStream generateExcelFile() throws IOException {
        try (Workbook workbook = new SXSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("Sample Sheet");
            Row row = sheet.createRow(0);
            Cell cell = row.createCell(0);
            cell.setCellValue("Excelファイルサンプルです");

            ByteArrayOutputStream out = new ByteArrayOutputStream();
            workbook.write(out);
            return new ByteArrayInputStream(out.toByteArray());
        }
    }
    
    public ByteArrayInputStream generateUserListExcel(List<MUser> userList) throws IOException {
        try (Workbook workbook = new XSSFWorkbook()) {
        	
            Sheet sheet = workbook.createSheet("UserList");

            // ヘッダ行の作成
            Row headerRow = sheet.createRow(0);
            Cell headerCell1 = headerRow.createCell(0);
            headerCell1.setCellValue("ユーザー名");
            Cell headerCell2 = headerRow.createCell(1);
            headerCell2.setCellValue("誕生日");

            // 日付フォーマットの設定
            CreationHelper createHelper = workbook.getCreationHelper();
            CellStyle dateCellStyle = workbook.createCellStyle();
            dateCellStyle.setDataFormat(createHelper.createDataFormat().getFormat("yyyy-mm-dd"));

            // データ行の作成
            int rowNum = 1;
            for (MUser user : userList) {
                Row row = sheet.createRow(rowNum++);
                row.createCell(0).setCellValue(user.getUserName());
                Cell birthdayCell = row.createCell(1);
                birthdayCell.setCellValue(user.getBirthday());
                birthdayCell.setCellStyle(dateCellStyle);
            }

            ByteArrayOutputStream out = new ByteArrayOutputStream();
            workbook.write(out);
            return new ByteArrayInputStream(out.toByteArray());
        }
    }

	@Override
	public ByteArrayInputStream generateBigDataExcel() throws IOException {
		// 巨大データ作成サンプル
        // SXSSFWorkbookを利用する
        try (SXSSFWorkbook workbook = new SXSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("Large Sheet");

            Random random = new Random();

            for (int rowNum = 0; rowNum < 1000; rowNum++) {
                Row row = sheet.createRow(rowNum);
                for (int colNum = 0; colNum < 20; colNum++) {
                    Cell cell = row.createCell(colNum);

                    if (colNum < 0) {
                        // 3列目までは1000文字から10000文字のランダムな文字列を設定
                        int length = 1000 + random.nextInt(9000);
                        String value = generateRandomString(length);
                        cell.setCellValue(value);
                    } else if (colNum == 5) {
                        // 列最大文字数テスト
                        //String value = generateRandomString(MAX_CELL_CHAR_COUNT + 1);  // エラーになる
                    	String value = generateRandomString(MAX_CELL_CHAR_COUNT);
                        cell.setCellValue(value);
                    } else {
                        // それ以外の列は100文字以下の文字列を設定
                        String value = generateRandomString(100);
                        cell.setCellValue(value);
                    }
                }
            }

            ByteArrayOutputStream out = new ByteArrayOutputStream();
            workbook.write(out);
            return new ByteArrayInputStream(out.toByteArray());
        }
	}
	
    private static String generateRandomString(int length) {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        StringBuilder sb = new StringBuilder(length);
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            sb.append(characters.charAt(random.nextInt(characters.length())));
        }
        return sb.toString();
    }
}
