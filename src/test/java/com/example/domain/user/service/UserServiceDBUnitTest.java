package com.example.domain.user.service;

import static org.assertj.core.api.Assertions.*;

import java.io.File;
import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.dbunit.DatabaseUnitException;
import org.dbunit.database.DatabaseConnection;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.csv.CsvDataSet;
import org.dbunit.operation.DatabaseOperation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest(webEnvironment = WebEnvironment.NONE)
@ExtendWith(SpringExtension.class)
@Transactional
class UserServiceDBUnitTest {

    @Autowired
    private UserService service;

    @Autowired
    private DataSource dataSource;

    @BeforeEach
    void setUp() throws DatabaseUnitException, SQLException {
        try (Connection conn = dataSource.getConnection()) {
            DatabaseConnection dbConn = new DatabaseConnection(conn);

            // CSV フォルダからデータを取得
            IDataSet dataSet = new CsvDataSet(new File("src/test/resources/csv/"));

            // 既存のデータをクリアし、新しいデータを挿入
            DatabaseOperation.CLEAN_INSERT.execute(dbConn, dataSet);
        }
    }

    @Test
    void test_getUserOne_1() {
        var user = service.getUserOne("user1@co.jp");

        assertThat(user).isNotNull();
        assertThat(user.getUserName()).isEqualTo("ユーザー1");
        assertThat(user.getAge()).isEqualTo(21);
        assertThat(user.getBirthday()).isEqualTo("2000-01-01");
    }

}
