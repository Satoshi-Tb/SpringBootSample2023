package com.example.domain.user.service;

import static org.assertj.core.api.Assertions.*;

import java.io.File;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import javax.sql.DataSource;

import org.dbunit.DatabaseUnitException;
import org.dbunit.database.DatabaseConnection;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.csv.CsvDataSet;
import org.dbunit.operation.DatabaseOperation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.transaction.annotation.Transactional;

import com.example.domain.user.model.MUser;
import com.example.domain.user.model.Salary;

/**
 * Service＆Mapperのテスト
 * テストデータはCSVから読み込む
 * トランザクションは自動でロールバック
 * */
@SpringBootTest(webEnvironment = WebEnvironment.NONE)
@Transactional
@DisplayName("UserServiceとUserMapperのインテグレーションテスト(DBUnit利用)")
class UserServiceDBUnitTest {

	private static final SimpleDateFormat SDF = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
	
	// CSVファイルのパス（フォルダ）
	private static final String INIT_DATA_PATH = "src/test/resources/csv/init";
	private static final String EXPECT_DATA_PATH = "src/test/resources/csv/expect";
	
    @Autowired
    private UserService service;

    @Autowired
    private DataSource dataSource;

    @BeforeEach
    void setUp() throws DatabaseUnitException, SQLException {
    	// テストケースごとに、テストデータ登録が実施される
        try (Connection conn = dataSource.getConnection()) {
            DatabaseConnection dbConn = new DatabaseConnection(conn);

            // CSV フォルダからデータを取得
            // 「テーブル名」.csvでファイルを用意
            // 読込順はtable-ordering.txtで指定
            IDataSet dataSet = new CsvDataSet(new File(INIT_DATA_PATH));

            // 既存のデータをクリアし、新しいデータを挿入（delete-insert）
            DatabaseOperation.CLEAN_INSERT.execute(dbConn, dataSet);
        }
    }

    @Test
	@DisplayName("getUserOne: 指定したIDのユーザーが取得できること")
    void test_getUserOne_1() throws ParseException {
        var user = service.getUserOne("user1@co.jp");

        assertThat(user).isNotNull();
        assertThat(user.getUserName()).isEqualTo("ユーザー1");
        assertThat(user.getAge()).isEqualTo(21);
        assertThat(user.getBirthday()).isEqualTo("2000-01-01");
        
        // オブジェクトによる比較
        // 比較項目が漏れにくい一方で、差異が出た場合に、問題個所が分かりにくい
        var expected = new MUser();
        expected.setId("user1@co.jp");
        expected.setUserId("user1@co.jp");
        expected.setPassword("$2a$10$c.4qDGSyfiB/1sfgcDDusuU1vmi4a6gpTjWhxeG7vZ5XZ/YbGVYpG");
        expected.setUserName("ユーザー1");
        expected.setAge(21);
        expected.setGender(2);
        expected.setProfile("ユーザーです");
        expected.setBirthday(SDF.parse("2000/01/01 00:00:00"));
        expected.setSalaryList(new ArrayList<Salary>());
        assertThat(user).isEqualTo(expected);
    }

}
