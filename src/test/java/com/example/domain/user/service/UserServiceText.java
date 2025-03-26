package com.example.domain.user.service;

import static org.assertj.core.api.Assertions.*;

import java.text.ParseException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;

//ServiceのDIを有効にするため、SpringBootTestを指定する。WebEnvironment.NONEを指定することで、Webまわりのコンフィグレーションを無効にする
@SpringBootTest(webEnvironment = WebEnvironment.NONE)
@Sql({
	"classpath:sql/dept_common.sql",       // 共通テスト用SQL。resources配下のパスを指定する場合は、classpath文字を指定
	"UserServiceTest_insert_m_user.sql"   // 個別テスト用SQL。クラスパス上に作成すれば、相対パス指定可能
})
@Transactional
@DisplayName("UserServiceとUserMapperのインテグレーションテスト")
class UserServiceText {

	@Autowired
	private UserService service;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@BeforeEach
	void setUp() throws Exception {
	}

	@Test
	@DisplayName("getUserOne: 指定したIDのユーザーが取得できること")
	void test_getUserOne_1() {
		var user = service.getUserOne("user1@co.jp");
		
		assertThat(user).isNotNull();
		assertThat(user.getUserName()).isEqualTo("ユーザー1");
		assertThat(user.getAge()).isEqualTo(21);
		assertThat(user.getBirthday()).isEqualTo("2000-01-01");
	}
	
	@Test
	@DisplayName("getUserOne: 存在しないユーザーの場合、Nullが取得できること")
	void test_getUserOne_2() {
		var user = service.getUserOne("xxxxxx");
		
		assertThat(user).isNull();
	}

	@Test
	@DisplayName("insertOne: 1件ユーザーを追加できること")
	void test_insertOne() throws ParseException {
		service.updateUserOne("user1@co.jp", "passwordX", "ユーザー名変更");

		var result = service.getUserOne("user1@co.jp");
		
		assertThat(result).isNotNull();
		//パスワード暗号化
		String expectedPasswd = passwordEncoder.encode("passwordX");
		assertThat(result.getUserName()).isEqualTo("ユーザー名変更");
		// assertThat(result.getPassword()).isEqualTo(expectedPasswd);  // エンコード済パスワード
	}
	
}
