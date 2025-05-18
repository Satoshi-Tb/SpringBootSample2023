package com.example.rest.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.domain.user.service.CSVService;


@RestController
@RequestMapping("/api")
public class FileUploadController {

	@Autowired
    private CSVService csvService;

    @PostMapping("/upload-csv")
    public ResponseEntity<RestResponse<Object>> uploadCSVFile(@RequestParam("file") MultipartFile file) {
        // ファイルが空かチェック
        if (file.isEmpty()) {
            return RestResponse.createErrorResponse("9000", Map.of("message", "ファイルが空です"), HttpStatus.BAD_REQUEST);
        }

        // CSVファイルかチェック
        if (!file.getContentType().equals("text/csv") && 
            !file.getOriginalFilename().endsWith(".csv")) {
            return RestResponse.createErrorResponse("9000", Map.of("message", "CSVファイル形式のみアップロード可能です"), HttpStatus.BAD_REQUEST);
        }

        try {
            // CSVファイルを処理するサービスを呼び出し
            boolean result = csvService.processCSVFile(file);
            
            if (result) {
                return RestResponse.createSuccessResponse();
            } else {
                return RestResponse.createErrorResponse("9000", Map.of("message", "ファイルの処理中にエラーが発生しました"), HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } catch (Exception e) {
            return RestResponse.createErrorResponse("9000", Map.of("message", "エラー: " + e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}