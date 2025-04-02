package com.example.domain.user.service;

import static org.assertj.core.api.Assertions.*;

import java.io.File;
import java.sql.Connection;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import javax.sql.DataSource;

import org.dbunit.Assertion;
import org.dbunit.DataSourceDatabaseTester;
import org.dbunit.IDatabaseTester;
import org.dbunit.database.DatabaseConnection;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.ITable;
import org.dbunit.dataset.csv.CsvDataSet;
import org.dbunit.dataset.filter.DefaultColumnFilter;
import org.dbunit.operation.DatabaseOperation;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionTemplate;

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

	@Autowired
	private PasswordEncoder passwordEncoder;
    
	@Autowired
	private PlatformTransactionManager transactionManager;
	
    private IDatabaseTester databaseTester;
    
    @BeforeEach
    void setUp() throws Exception {
        // DataSourceを使ってIDatabaseTesterを初期化
        databaseTester = new DataSourceDatabaseTester(dataSource);
        
        // 初期データセット（CSV）の準備
        IDataSet dataSet = new CsvDataSet(new File(INIT_DATA_PATH));
        
        // データベース操作の設定（初期データで入れ替え）
        databaseTester.setSetUpOperation(DatabaseOperation.CLEAN_INSERT);
        databaseTester.setDataSet(dataSet);
        
        // テスト終了時にデータ削除
        databaseTester.setTearDownOperation(DatabaseOperation.DELETE_ALL);
        
        // セットアップの実行
        databaseTester.onSetup();
    }
    
    @AfterEach
    public void tearDown() throws Exception {
        // テスト後のクリーンアップ
        databaseTester.onTearDown();
    }

    @Tag("DBUnit")
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
        expected.setRole("ROLE_GENERAL");
        assertThat(user).isEqualTo(expected);
    }

    @Tag("DBUnit")
    @Test
    @DisplayName("signup: ユーザー登録できること")
    void test_signUp_1() throws Exception {
    	var registerUser = new MUser();
    	registerUser.setId("userX@co.jp");
    	registerUser.setUserId("userX@co.jp");
    	registerUser.setPassword("password");
    	registerUser.setUserName("ユーザーX");
    	registerUser.setBirthday(SDF.parse("1988/12/03 00:00:00"));
    	registerUser.setAge(37);
    	registerUser.setGender(1);
    	registerUser.setProfile("ユーザーXのプロファイル");
    	
    	TransactionTemplate txTemplate = new TransactionTemplate(transactionManager);
    	txTemplate.execute(status -> {
    	    try {
    	        // ユーザー登録処理
    	        service.signup(registerUser);
    	        
    	        /*
    	         * チェック方法１：テーブル内容と期待値CSVを比較 
    	         */
    	        // 重要: 同じトランザクションのコネクションを使って検証する
    	        Connection conn = DataSourceUtils.getConnection(dataSource);
    	        DatabaseConnection dbConn = new DatabaseConnection(conn);
    	        
    	        // この方法ならトランザクション内の変更が見える
    	        IDataSet databaseDataSet = dbConn.createDataSet();
    	        ITable actualTable = databaseDataSet.getTable("m_user");
    	        
    	        // 検証対象外カラムを除外設定
    	        ITable filteredActualTable = DefaultColumnFilter.excludedColumnsTable(
    	                actualTable, new String[]{"password", "department_id", "role", "ins_date", "ins_user_id", "upd_date", "upd_user_id"});
    	        
    	        // 期待値のCSVデータセット
    	        IDataSet expectedDataSet = new CsvDataSet(new File(EXPECT_DATA_PATH));
    	        ITable expectedTable = expectedDataSet.getTable("m_user");

    	        // 検証対象外カラムを除外設定
    	        ITable filteredExpectedTable = DefaultColumnFilter.excludedColumnsTable(
    	                expectedTable, new String[]{"password", "department_id", "role", "ins_date", "ins_user_id", "upd_date", "upd_user_id"});
    	        
    	        // 比較・検証
    	        Assertion.assertEquals(filteredExpectedTable, filteredActualTable);
    	        
    	        /*
    	         * チェック方法２：更新結果をピックアップして、検証
    	         */
    	    	var expectedUser = new MUser();
    	    	BeanUtils.copyProperties(registerUser, expectedUser);
    	    	expectedUser.setDepartmentId(null);
    	    	expectedUser.setSalaryList(new ArrayList<Salary>());
    	    	
    	        var actual = service.getUserOne("userX@co.jp");
    	        assertThat(actual).isEqualTo(expectedUser);
    	        
    	        // 検証後にロールバック（オプション）
    	        status.setRollbackOnly();
    	        
    	        return null;
    	    } catch (Exception e) {
    	        throw new RuntimeException(e);
    	    }
    	});

    }
    
}
