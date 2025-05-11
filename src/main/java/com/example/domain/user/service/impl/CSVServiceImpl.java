package com.example.domain.user.service.impl;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.example.domain.user.model.MUser;
import com.example.domain.user.service.CSVService;
import com.example.domain.user.service.CSVValidationResult;
import com.example.repositry.UserMapper;

@Service
public class CSVServiceImpl implements CSVService{
    private static final Logger logger = LoggerFactory.getLogger(CSVService.class);

	@Autowired
	private UserMapper mapper;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
    
    private static final int CSV_ITEM_COUNT = 8;    

    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");
    private static final Set<String> VALID_DEPARTMENT_IDS = Set.of("1", "2", "3", "9");
    private static final Set<String> VALID_GENDERS = Set.of("1", "2", "3");

    /**
     * CSVファイルを処理する
     * 
     * @param file アップロードされたCSVファイル
     * @return 処理が成功したかどうか
     */
    public boolean processCSVFile(MultipartFile file) {
        try {
            List<CSVValidationResult> validationResults = validateAndParseCSV(file);
            
            // 検証エラーがあるかチェック
            List<CSVValidationResult> errors = validationResults.stream()
                .filter(result -> !result.isValid())
                .collect(Collectors.toList());
            
            if (!errors.isEmpty()) {
                // エラーがある場合はログに出力
                for (CSVValidationResult error : errors) {
                    logger.error("行 {}: 検証エラー: {}", error.getLineNumber(), 
                            String.join(", ", error.getErrors()));
                }
                logger.error("CSVファイルに {} 件の検証エラーがあります", errors.size());
            }
            
            // 検証エラーなしユーザー
            List<MUser> validUser = validationResults.stream()
                .filter(result -> result.isValid())
                .map(result -> result.getUser())
                .collect(Collectors.toList());
            
            // 検証成功のデータを登録
            for (MUser user : validUser) {
                processUser(user);
            }
            
            logger.info("処理完了: {} 件のユーザーレコードが正常に処理されました", validUser.size());
            return errors.size() == 0 ? true : false;
        } catch (Exception e) {
            logger.error("CSVファイルの処理中にエラーが発生しました", e);
            return false;
        }
    }
    
    /**
     * CSVをバリデーションしてMUserオブジェクトのリストに変換
     */
    public List<CSVValidationResult> validateAndParseCSV(MultipartFile file) throws Exception {
        List<CSVValidationResult> results = new ArrayList<>();
        
        try (BufferedReader fileReader = new BufferedReader(
                new InputStreamReader(file.getInputStream(), StandardCharsets.UTF_8));
             CSVParser csvParser = new CSVParser(fileReader,
                 CSVFormat.DEFAULT.builder()
                     .setHeader()                          // 最初の行をヘッダーとして使用
                     .setSkipHeaderRecord(true)  // ヘッダー行をデータとして処理しない
                     .setIgnoreHeaderCase(true)  // ヘッダー名の大文字小文字を区別しない
                     .setTrim(true)                        // フィールドの前後の空白を削除
                     .build())) {
            
            int lineNumber = 1; // ヘッダー行の次から開始
            
            // ヘッダーを取得
            Map<String, Integer> headerMap = csvParser.getHeaderMap();
          
            if (headerMap.size() != CSV_ITEM_COUNT) {
                CSVValidationResult validationResult = new CSVValidationResult();
                validationResult.setLineNumber(0);
                validationResult.addError("項目数が正しくありません");
                results.add(validationResult);
                return results;
            }
            
            for (CSVRecord csvRecord : csvParser) {
                lineNumber++;
                CSVValidationResult validationResult = new CSVValidationResult();
                validationResult.setLineNumber(lineNumber);
                
                MUser user = new MUser();
                
                // user_id の検証とセット
                String userId = csvRecord.get("user_id");
                if (StringUtils.isEmpty(userId)) {
                    validationResult.addError("user_id は必須です");
                } else {
                    user.setUserId(userId);
                }
                
                // password の検証とセット
                String password = csvRecord.get("password");
                if (StringUtils.isEmpty(password)) {
                    validationResult.addError("password は必須です");
                } else {
                	// パスワードを暗号化してセット
            		user.setPassword(passwordEncoder.encode(password));
                }
                                
                // user_name の検証とセット
                String userName = csvRecord.get("user_name");
                if (StringUtils.isEmpty(userName)) {
                    validationResult.addError("user_name は必須です");
                } else {
                    user.setUserName(userName);
                }
                
                // birthday の検証とセット
                String birthdayStr = csvRecord.get("birthday");
                if (StringUtils.isEmpty(birthdayStr)) {
                    validationResult.addError("birthday は必須です");
                } else {
                    try {
                        Date birthday = DATE_FORMAT.parse(birthdayStr);
                        user.setBirthday(birthday);
                    } catch (ParseException e) {
                        validationResult.addError(String.format("birthday は yyyy-MM-dd 形式で入力してください。入力値{%s}", birthdayStr));
                    }
                }
                
                // age の検証とセット
                String ageStr = csvRecord.get("age");
                if (StringUtils.isEmpty(ageStr)) {
                    validationResult.addError("age は必須です");
                } else {
                    try {
                    	// MEMO: Exceptionが出る場合、isDigitで判定したほうが速い。が、出る可能性が低ければparseIntでよい
                        int age = Integer.parseInt(ageStr);
                        if (age <= 0) {
                            validationResult.addError(String.format("age は正の数値でなければなりません。入力値{%s}", ageStr));
                        } else {
                            user.setAge(age);
                        }
                    } catch (NumberFormatException e) {
                        validationResult.addError(String.format("age は数値でなければなりません。入力値{%s}", ageStr));
                    }
                }
                
                // gender の検証とセット
                String genderStr = csvRecord.get("gender");
                if (StringUtils.isEmpty(genderStr)) {
                    validationResult.addError("gender は必須です");
                } else {
                    if (!VALID_GENDERS.contains(genderStr)) {
                        validationResult.addError(String.format("gender は 1, 2, 3 のいずれかでなければなりません。入力値{%s}", genderStr));
                    } else {
                        user.setGender(Integer.parseInt(genderStr));
                    }
                }
                
                // profile はチェックなし
                user.setProfile(csvRecord.get("profile"));
                
                // department_id の検証とセット
                String deptIdStr = csvRecord.get("department_id");
                if (StringUtils.isEmpty(deptIdStr)) {
                    validationResult.addError("department_id は必須です");
                } else {
					if (!VALID_DEPARTMENT_IDS.contains(deptIdStr)) {
					    validationResult.addError(String.format("department_id は 1, 2, 3, 9 のいずれかでなければなりません。入力値{%s}", deptIdStr));
					} else {
					    user.setDepartmentId(Integer.parseInt(deptIdStr));
					}
                }

                // 検証結果にユーザーオブジェクトをセット
                validationResult.setUser(user);
                results.add(validationResult);
            }
        }
        
        return results;
    }
    
    /**
     * ユーザー情報を処理する（DB保存などの実際の処理）
     */
    @Transactional(rollbackFor = Exception.class)
    private void processUser(MUser user) {
        logger.info("データ登録: {}", user);
		mapper.insertOne(user);	
    }
    
    /**
     * CSVファイルをリストとして取得する（代替メソッド）
     * 
     * @param file CSVファイル
     * @return 行のリスト（各行は列値のマップ）
     */
    public List<Map<String, String>> getCSVDataAsList(MultipartFile file) throws Exception {
        List<Map<String, String>> result = new ArrayList<>();
        
        try (BufferedReader fileReader = new BufferedReader(
                new InputStreamReader(file.getInputStream(), StandardCharsets.UTF_8));
             CSVParser csvParser = new CSVParser(fileReader,
                 CSVFormat.DEFAULT.builder()
                     .setHeader()
                     .setSkipHeaderRecord(true)
                     .setIgnoreHeaderCase(true)
                     .setTrim(true)
                     .build())) {
            
            for (CSVRecord csvRecord : csvParser) {
                result.add(csvRecord.toMap());
            }
        }
        
        return result;
    }
}
