package com.example.rest.controller;

import static org.assertj.core.api.Assertions.*;

import java.text.SimpleDateFormat;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.jdbc.Sql;

import com.example.domain.user.model.MUser;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql("UserRestControllerTest_insert_m_user.sql")
// トランザクション制御できないので、テストの後でデータのクリア実施必要
@Sql(value = "UserRestControllerTest_clear.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
@DisplayName("UserRestControllerテスト（APサーバーインテグレーションテスト）")
class UserRestControllerApServerIntegrationTest {
	private static final SimpleDateFormat SDF = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
	
    @Autowired
    TestRestTemplate testRestTemplate;
	
	@BeforeEach
	void setUp() throws Exception {
	}

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
