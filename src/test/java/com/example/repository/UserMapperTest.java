package com.example.repository;

import static org.assertj.core.api.Assertions.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.test.context.jdbc.Sql;

import com.example.domain.user.model.MUser;
import com.example.domain.user.service.UserListCriteria;
import com.example.repositry.UserMapper;


// MyBatisTestを指定すると、自動的にTransactionalが有効になる
// テストが終わったら自動的にRollbackされるのでクリーンアップは不要
@MybatisTest
@Sql({
	"classpath:sql/dept_common.sql",       // 共通テスト用SQL。resources配下のパスを指定する場合は、classpath文字を指定
	"UserMapperTest_insert_m_user.sql"   // 個別テスト用SQL。クラスパス上に作成すれば、相対パス指定可能
})
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE) 
@DisplayName("UserMapperTest")
class UserMapperTest {

    @Autowired
    private UserMapper userMapper;
	
	@BeforeEach
	void setUp() throws Exception {
	}

	@Test
	@DisplayName("findeOne: 指定したIDのユーザーが取得できること")
	void test_findOne() {
		var user = userMapper.findOne("user1@co.jp");
		
		assertThat(user).isNotNull();
		assertThat(user.getUserName()).isEqualTo("ユーザー1");
		assertThat(user.getAge()).isEqualTo(21);
		assertThat(user.getBirthday()).isEqualTo("2000-01-01");
	}
	
	@Test
	@DisplayName("findManyByPagination: 指定した条件のユーザーが取得できること（全件取得）")
	void test_findManyByPagination() {
		var condition = new UserListCriteria();
		
		// 全件取得（先頭から100件）
		condition.setOffset(0);
		condition.setSize(100);
		
		var users = userMapper.findManyByPagination(condition);
		
		assertThat(users).isNotNull();
		assertThat(users.size()).isEqualTo(21);
	}
	
	@Test
	@DisplayName("insertOne: 1件ユーザーを追加できること")
	void test_insertOne() throws ParseException {
		var df = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		
		var user = new MUser();
		user.setUserId("user9999@co.jp");
		user.setPassword("$2a$10$c.4qDGSyfiB/1sfgcDDusuU1vmi4a6gpTjWhxeG7vZ5XZ/YbGVYpG");
		user.setUserName("testuser 9999");
		user.setBirthday(df.parse("2000/04/05 00:00:00"));
		user.setGender(1);
		user.setAge(20);

		int count = userMapper.insertOne(user);
		
		assertThat(count).isEqualTo(1);
		
		var result = userMapper.findOne("user9999@co.jp");
		
		assertThat(result).isNotNull();
		assertThat(result.getUserName()).isEqualTo("testuser 9999");
		assertThat(result.getAge()).isEqualTo(20);
		assertThat(result.getBirthday()).isEqualTo("2000-04-05");
	}
	
}
