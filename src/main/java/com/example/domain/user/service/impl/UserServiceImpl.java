package com.example.domain.user.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.domain.user.model.CustomMUser;
import com.example.domain.user.model.FilterItem;
import com.example.domain.user.model.MUser;
import com.example.domain.user.service.UserListCriteria;
import com.example.domain.user.service.UserService;
import com.example.repositry.UserMapper;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserMapper mapper;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	/** ユーザー登録 */
	@Override
	public void signup(MUser user) {
		user.setDepartmentId(1);
		user.setRole("ROLE_GENERAL");
		
		//パスワード暗号化
		String rawPasswd = user.getPassword();
		user.setPassword(passwordEncoder.encode(rawPasswd));
		mapper.insertOne(user);	
	}

	/** ユーザー取得 */
	@Override
	public List<MUser> getUsers(MUser user) {
		return mapper.findMany(user);
	}

	/** ユーザー取得 */
	@Override
	public List<MUser> getUsersByPagination(UserListCriteria condition) {		
		return mapper.findManyByPagination(condition);
	}
	
	@Override
	public int getUsersByPaginationTotalCount(UserListCriteria condition) {
		return mapper.findManyByPaginationTotalCount(condition);
	}
	
	/** ユーザー取得（１件） */
	@Override
	public MUser getUserOne(String userId) {
		return mapper.findOne(userId);
	}
	
	/** フィルタ結果取得 */
	@Override
	public List<FilterItem> getUsersFilter(String filterName, UserListCriteria condition) {
		//TODO フィルタサービスの口は1つにまとめられるとしても、SQLの共通化は難しそうかも
        switch (filterName) {
	        case "gender":
	    		return mapper.getFilterByGender(condition);
	        case "departmentId":
	    		return mapper.getFilterByDepartmentId(condition);
	        default:
	        	return new ArrayList<FilterItem>();
        }
	}

	/** ユーザー更新（１件） */
	// @Transactionalのアノテーションが付いたメソッド内で例外が発生すると自動でロールバックされる。
	// ロールバック対象は、デフォルトではスローされた例外が RuntimeException のインスタンスまたはサブクラスである場合です。(Error インスタンスもデフォルトでロールバックします)。
	// publicのメソッドにアノテーションを付与した場合のみ、ロールバックが有効になる。
	// アノテーションを付けたメソッド以降の処理が同一トランザクション管理範囲となる。
	@Transactional
	@Override
	public void updateUserOne(String userId, String password, String userName) {
		//パスワード暗号化
		mapper.updateOne(userId, passwordEncoder.encode(password), userName);
	}
	
	@Transactional
	@Override
	public void updateUser(CustomMUser user) {
		//パスワード暗号化
		String newPasswd = passwordEncoder.encode(user.getPassword());
		user.setPassword(newPasswd);

		mapper.updateByUser(user);
	}
	
	/** ユーザー削除（１件） */
	@Transactional
	@Override
	public void deleteUserOne(String userId) {
		mapper.deleteOne(userId);
	}

	/** ユーザー削除（複数件） */
	@Transactional
	@Override
	public void deleteUsers(List<String> userIdList) {
		// mapper.deleteUsersByInClause(userIdList);
		mapper.deleteUsersByUnionClause(userIdList);
	}

	@Override
	public String getNextUserId(String userId) {
		return mapper.findNextUserId(userId);
	}

	@Override
	public String getBeforeUserId(String userId) {
		return mapper.findBeforeUserId(userId);
	}
	@Override
	public void updateUserTest(String userId, String userName) {
		mapper.updateByUserSelectiveTest(userId, userName);
	};

	/** ユーザー更新（1件）引数違いテスト */
	@Override
	public void updateUserTest(String userId, String userName, Integer gender) {
		mapper.updateByUserSelectiveTest(userId, userName, gender);
	}
	
}
