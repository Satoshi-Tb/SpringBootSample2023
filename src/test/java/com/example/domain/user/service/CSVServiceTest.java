package com.example.domain.user.service;

import static org.assertj.core.api.Assertions.*;

import java.io.IOException;
import java.io.InputStream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.transaction.annotation.Transactional;

import com.example.domain.model.mbg.MUserMbgExample;
import com.example.repositry.mbg.MUserMbgMapper;

@SpringBootTest(webEnvironment = WebEnvironment.NONE)
@Transactional // テスト終了後に自動的にロールバックされる
@DisplayName("CSVServiceのテスト")
public class CSVServiceTest {

	// テスト対象のサービス
	@Autowired
	private CSVService service;
	
	@Autowired
	private MUserMbgMapper muserMpper;
	
	@BeforeEach
	void setUp() throws Exception {
	}

	@Test
	@DisplayName("CSV処理-正常-CSVService_sample1")
	void testProcessCSVFile() throws Exception {
		var csvFile = createMockCsvFile("sampleCsv1.csv", "com/example/domain/user/service/CSVService_sample1.csv");
        // テスト実行
        boolean result = service.processCSVFile(csvFile);
        
        // 結果検証
        assertThat(result).isTrue();
        
        var condition = new MUserMbgExample();
        condition.setOrderByClause("user_id");
        var results = muserMpper.selectByExample(condition);
        
        assertThat(results.size()).isEqualTo(2);
        var user1 = results.get(0);
        assertThat(user1.getUserId()).isEqualTo("csv_user1@co.jp");
        var user2 = results.get(1);
        assertThat(user2.getUserId()).isEqualTo("csv_user2@co.jp");
	}

	
	@Test
	@DisplayName("CSVService_異常_空ファイル")
	void testProcessCSVFileEmptyFileError() throws Exception {
		var csvFile = createMockCsvFile("sampleCsv1.csv", "com/example/domain/user/service/CSVService_error_empty.csv");
        // テスト実行
        boolean result = service.processCSVFile(csvFile);
        
        // 結果検証
        assertThat(result).isFalse();
	}

	
	
	@Test
	@DisplayName("CSVService_異常_明細エラーパタンテスト")
	void testProcessCSVFileDetailErrorPattern() throws Exception {
		var csvFile = createMockCsvFile("sampleCsv1.csv", "com/example/domain/user/service/CSVService_error_pattern.csv");
        // テスト実行
        boolean result = service.processCSVFile(csvFile);
        
        // 結果検証
        assertThat(result).isFalse();
	}

	
    // 外部CSVファイルの読み込み
	// resourcesフォルダ以下のパスを指定
	private static MockMultipartFile createMockCsvFile(String mockCsvFileName, String resourcePath) throws IOException {
        ClassPathResource resource = new ClassPathResource(resourcePath);
        InputStream inputStream = resource.getInputStream();
        
        return  new MockMultipartFile(
                "file",
                mockCsvFileName,
                "text/csv",
                inputStream
        );
	}
	
}
