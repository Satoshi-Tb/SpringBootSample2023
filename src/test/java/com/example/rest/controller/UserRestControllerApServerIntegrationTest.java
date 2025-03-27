package com.example.rest.controller;

import static org.assertj.core.api.Assertions.*;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.jdbc.Sql;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql("UserRestControllerTest_insert_m_user.sql")
// トランザクション制御できないので、テストの後でデータのクリア実施必要
@Sql(value = "UserRestControllerTest_clear.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
@DisplayName("UserRestControllerテスト（APサーバーインテグレーションテスト）")
class UserRestControllerApServerIntegrationTest {
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
}
