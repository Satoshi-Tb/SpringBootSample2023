package com.example.rest.controller;

import static org.assertj.core.api.Assertions.*;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.util.StreamUtils;

import com.example.domain.user.model.MUser;
import com.example.domain.user.service.UserListCriteria;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql("UserRestControllerTest_insert_m_user.sql")
// トランザクション制御できないので、テストの後でデータのクリア実施必要
@Sql(value = "UserRestControllerTest_clear.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
@DisplayName("UserRestControllerテスト（APサーバーインテグレーションテスト）")
class UserRestControllerApServerIntegrationTest {
	private static final SimpleDateFormat SDF = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
	
    @Autowired
    TestRestTemplate testRestTemplate;
	
    @Autowired
    private ObjectMapper objectMapper;
    
	@BeforeEach
	void setUp() throws Exception {
	}

    @Tag("SQL")
	@Test
	@DisplayName("get /api/user/detail/{userId}: 指定したIDのuserが取得できること")
	void test_getUserById() {
		var responseEntity = testRestTemplate.exchange(
				"/api/user/detail/{userId}",
				HttpMethod.GET, 
				null, 
				new ParameterizedTypeReference<RestResponse<UserResponse>>() {}, 
				"user1@co.jp"
		);
		Assertions.assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
		RestResponse<UserResponse> resp = responseEntity.getBody();
		assertThat(resp).isNotNull();
		
		var data = resp.getData();
		var user = data.getUser();

		assertThat(user).isNotNull();
		assertThat(user.getUserName()).isEqualTo("ユーザー1");
		assertThat(user.getAge()).isEqualTo(21);
		assertThat(user.getBirthday()).isEqualTo("2000-01-01");       
	}

    @Tag("SQL")
	@Test
	@DisplayName("get /api/user/detail/{userId}: 指定したIDのuserが取得できないこと")
	void test_getUser_not_found() {
		var responseEntity = testRestTemplate.exchange(
				"/api/user/detail/{userId}",
				HttpMethod.GET, 
				null, 
				new ParameterizedTypeReference<RestResponse<UserResponse>>() {}, 
				"xxxx"
		);
		Assertions.assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
		RestResponse<UserResponse> resp = responseEntity.getBody();
		assertThat(resp).isNotNull();
		
		var data = resp.getData();
		var user = data.getUser();

		assertThat(user).isNull(); 
	}
	
    @Tag("SQL")
	@Test
	@DisplayName("get /api/user/detail/{userId}: 指定したIDのuserが取得できること")
	void test_detail() {
		var responseEntity = testRestTemplate.exchange(
				"/api/user/detail/{userId}",
				HttpMethod.GET, 
				null, 
				new ParameterizedTypeReference<RestResponse<UserResponse>>() {}, 
				"user1@co.jp"
		);
		Assertions.assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
		RestResponse<UserResponse> resp = responseEntity.getBody();
		assertThat(resp).isNotNull();
		
		var data = resp.getData();
		var user = data.getUser();

		assertThat(user).isNotNull();
		assertThat(user.getUserName()).isEqualTo("ユーザー1");
		assertThat(user.getAge()).isEqualTo(21);
		assertThat(user.getBirthday()).isEqualTo("2000-01-01");       
	}
	
    @Tag("SQL")
	@Test
	@DisplayName("get /api/user/get/list-pager: ")
	void test_getUserByPagination() throws IOException {
    	/*
    	 * テスト準備
    	 */
    	// 期待値データ(JSON)の読み込み
    	// クラスパスが同じであれば、相対パス読込も可能かも？
        String resourcePath = "com/example/rest/controller/UserRestControllerTest.json";
        ClassPathResource resource = new ClassPathResource(resourcePath);
        
        String jsonContent;
        try (InputStream inputStream = resource.getInputStream()) {
            jsonContent = StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8);
        }
        // JSON -> Object変換
        var expected = objectMapper.readValue(jsonContent, new TypeReference<RestResponse<UserListPaginationResponse>>() {});

        // リクエストボディの作成（検索条件）
        var condition = new UserListCriteria();
        condition.setPage(0);
        condition.setSize(5);
		
        // ヘッダーの設定
        var headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");

        // リクエストエンティティの作成
        var requestEntity = new HttpEntity<UserListCriteria>(condition, headers);
        
    	/*
    	 * テスト実施
    	 */
		var responseEntity = testRestTemplate.exchange(
				"/api/user/get/list-pager",
				HttpMethod.POST, 
				requestEntity, 
				new ParameterizedTypeReference<RestResponse<UserListPaginationResponse>>() {}
		);
		
    	/*
    	 * 結果確認
    	 */
		Assertions.assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
		RestResponse<UserListPaginationResponse> resp = responseEntity.getBody();
		assertThat(resp).isNotNull();
		
		var data = resp.getData();
		var users = data.getUserList();
		
		assertThat(users.size()).isEqualTo(5);
		
		// TODO 検証対象から、特定列（insDate、updDateなど）を除外必要
		assertThat(users).isEqualTo(expected.getData().getUserList());
		
//		assertThat(user).isNotNull();
//		assertThat(user.getUserName()).isEqualTo("ユーザー1");
//		assertThat(user.getAge()).isEqualTo(21);
//		assertThat(user.getBirthday()).isEqualTo("2000-01-01");       
	}
    
    @Tag("SQL")
	@Test
	@DisplayName("put /api/user/update: 指定したIDのuserを更新できること")
	void test_update() throws Exception {
        // リクエストボディの作成
        var requestBody = new UserRequest();
        requestBody.setUserId("user1@co.jp");
        requestBody.setPassword("newPassword123");
        requestBody.setUserName("山田太郎");
		
        // ヘッダーの設定
        var headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");

        // リクエストエンティティの作成
        var requestEntity = new HttpEntity<UserRequest>(requestBody, headers);
        
        // エンドポイント実行
		var responseEntity = testRestTemplate.exchange(
			"/api/user/update",
			HttpMethod.PUT, 
			requestEntity, 
			new ParameterizedTypeReference<RestResponse<MUser>>() {}
		);
		
		// 実行結果の確認
		assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
		
		var user = responseEntity.getBody().getData();
		assertThat(user).isNotNull();
		assertThat(user.getUserName()).isEqualTo("山田太郎");
	}
}
