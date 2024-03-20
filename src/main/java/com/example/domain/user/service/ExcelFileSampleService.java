package com.example.domain.user.service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import com.example.domain.user.model.MUser;

@Service
public class ExcelFileSampleService {
    public ByteArrayInputStream generateExcelFile() throws IOException {
        try (Workbook workbook = new XSSFWorkbook()) {
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
}
