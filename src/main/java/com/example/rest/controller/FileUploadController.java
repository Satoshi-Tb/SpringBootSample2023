package com.example.rest.controller;

import java.util.HashMap;
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
    public ResponseEntity<?> uploadCSVFile(@RequestParam("file") MultipartFile file) {
        Map<String, Object> response = new HashMap<>();

        // ファイルが空かチェック
        if (file.isEmpty()) {
            response.put("status", "error");
            response.put("message", "ファイルが空です");
            return ResponseEntity.badRequest().body(response);
        }

        // CSVファイルかチェック
        if (!file.getContentType().equals("text/csv") && 
            !file.getOriginalFilename().endsWith(".csv")) {
            response.put("status", "error");
            response.put("message", "CSVファイル形式のみアップロード可能です");
            return ResponseEntity.badRequest().body(response);
        }

        try {
            // CSVファイルを処理するサービスを呼び出し
            boolean result = csvService.processCSVFile(file);
            
            if (result) {
                response.put("status", "success");
                response.put("message", "ファイルが正常にアップロードされました");
                return ResponseEntity.ok(response);
            } else {
                response.put("status", "error");
                response.put("message", "ファイルの処理中にエラーが発生しました");
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
            }
        } catch (Exception e) {
            response.put("status", "error");
            response.put("message", "エラー: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
}