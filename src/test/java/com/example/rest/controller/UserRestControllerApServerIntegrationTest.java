package com.example.rest.controller;

import static org.assertj.core.api.Assertions.*;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.function.Supplier;
import java.util.stream.Stream;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
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
	@DisplayName("get /api/user/get/list-pager: 検索条件:なし、1ページ当たり:5件")
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
		
		// 検証対象から、特定プロパティを除外して比較する
		for (int i = 0; i < users.size(); i++) {
			assertThat(users.get(i))
			.usingRecursiveComparison()  // 全プロパティを再帰的に比較
			.ignoringFields("insUserId", "insDate", "updUserId", "updDate")  // 特定プロパティを除外して比較
			.isEqualTo(expected.getData().getUserList().get(i));
		}
	}
    
	
    /*
     * パラメータ化テストのサンプル。専用のパラメータメソッドを準備するため、Nestedテストケースとして実装。
     * */
    @Nested
    @DisplayName("get /api/user/get/list-pager: ユーザー一覧検索ページネーションテスト")
    class GetUserByPaginationTest {
    	
        // テストパラメータを格納するためのクラス
        static class TestCase {
            private final String displayName;
            private final String jsonResourcePath;
            private final Supplier<UserListCriteria> criteriaSupplier;

            public TestCase(String displayName, String jsonResourcePath, Supplier<UserListCriteria> criteriaSupplier) {
                this.displayName = displayName;
                this.jsonResourcePath = jsonResourcePath;
                this.criteriaSupplier = criteriaSupplier;
            }

            // getters
            public String getDisplayName() { return displayName; }
            public String getJsonResourcePath() { return jsonResourcePath; }
            
            // 条件を取得するメソッド
            public UserListCriteria getCondition() { 
                return criteriaSupplier.get();
            }
            
            @Override
            public String toString() {
                return displayName;
            }
        }

        // テストケースのデータを提供するメソッド
        static Stream<TestCase> userPaginationTestCases() {
            return Stream.of(
                new TestCase(
                    "検索条件:なし、1ページ当たり:5件、1ページ目",
                    "com/example/rest/controller/UserRestControllerTest.json",
                    () -> {
                        var cond = new UserListCriteria();
                        cond.setPage(0);
                        cond.setSize(5);
                        return cond;
                    }
                ),
                new TestCase(
                        "検索条件:なし、1ページ当たり:5件、2ページ目",
                        "com/example/rest/controller/UserRestControllerTest_2.json",
                        () -> {
                            var cond = new UserListCriteria();
                            cond.setPage(1);
                            cond.setSize(5);
                            return cond;
                        }
                    ),
                new TestCase(
                    "検索条件:ユーザー名に\"ユーザー\"を含む、性別：男性、1ページ当たり:5件、1ページ目",
                    "com/example/rest/controller/UserRestControllerTest_3.json",
                    () -> {
                        var cond = new UserListCriteria();
                        cond.setUserName("ユーザー");
                        cond.setGender(1);
                        cond.setPage(0);
                        cond.setSize(5);
                        return cond;
                    }
                )
                // 必要に応じてさらにテストケースを追加
            );
        }
    	
        @Tag("SQL")
        @DisplayName("検索条件バリエーションテスト")
        @ParameterizedTest(name = "インデックス : {index}, 引数 : {0}")
        @MethodSource("userPaginationTestCases")
    	void test_getUserByPagination_parameterize_method(TestCase testCase) throws IOException {
        	/*
        	 * テスト準備
        	 */
        	// 期待値データ(JSON)の読み込み
            ClassPathResource resource = new ClassPathResource(testCase.getJsonResourcePath());
            
            String jsonContent;
            try (InputStream inputStream = resource.getInputStream()) {
                jsonContent = StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8);
            }
            // JSON -> Object変換
            var expected = objectMapper.readValue(jsonContent, new TypeReference<RestResponse<UserListPaginationResponse>>() {});
  		
            // ヘッダーの設定
            var headers = new HttpHeaders();
            headers.set("Content-Type", "application/json");

            // リクエストエンティティの作成
            var requestEntity = new HttpEntity<>(testCase.getCondition(), headers);
            
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
    		
    		// 検証対象から、特定プロパティを除外して比較する
    		for (int i = 0; i < users.size(); i++) {
    			assertThat(users.get(i))
    			.usingRecursiveComparison()  // 全プロパティを再帰的に比較
    			.ignoringFields("insUserId", "insDate", "updUserId", "updDate")  // 特定プロパティを除外して比較
    			.isEqualTo(expected.getData().getUserList().get(i));
    		}
    	}
    	
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
