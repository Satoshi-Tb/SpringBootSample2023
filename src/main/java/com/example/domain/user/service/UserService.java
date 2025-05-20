package com.example.domain.user.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.example.domain.user.model.CustomMUser;
import com.example.domain.user.model.FilterItem;
import com.example.domain.user.model.MUser;

public interface UserService {
	
	/** ユーザー登録 */
	@Transactional
	public void signup(MUser user);
	
	/** ユーザー取得 */
	public List<MUser> getUsers(MUser user);
	
	/** ユーザー取得(ページネーション) */
	public List<MUser> getUsersByPagination(UserListCriteria condition);
	
	/** ユーザー取得(ページネーション)(総件数) */
	public int getUsersByPaginationTotalCount(UserListCriteria condition);
	
	/** ユーザーフィルタ取得 */
	public List<FilterItem> getUsersFilter(String filterName, UserListCriteria condition);
	
	/** ユーザー取得（1件） */
	public MUser getUserOne(String userId);
	
	/** 次のユーザーID取得 */
	public String getNextUserId(String userId);
	
	/** 次のユーザーID取得 */
	public String getBeforeUserId(String userId);
	
	/** ユーザー更新（1件） */
	@Transactional
	public void updateUserOne(String userId, String password, String userName);
	
	/** ユーザー更新（1件） */
	@Transactional
	public void updateUser(CustomMUser user);
	
	/** ユーザー削除（1件） */
	@Transactional
	public void deleteUserOne(String userId);
	
	/** ユーザー削除（複数件） */
	@Transactional
	public void deleteUsers(List<String> userIdList);

	/** ユーザー更新（1件）引数違いテスト */
	@Transactional
	public void updateUserTest(String userId, String userName);

	/** ユーザー更新（1件）引数違いテスト */
	@Transactional
	public void updateUserTest(String userId, String userName, Integer gender);

}
