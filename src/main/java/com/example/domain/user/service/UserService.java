package com.example.domain.user.service;

import java.util.List;

import com.example.domain.user.model.CustomMUser;
import com.example.domain.user.model.MUser;

public interface UserService {
	
	/** ユーザー登録 */
	public void signup(MUser user);
	
	/** ユーザー取得 */
	public List<MUser> getUsers(MUser user);
	
	/** ユーザー取得(ページネーション) */
	public List<MUser> getUsersByPagination(UserListCriteria condition);
	
	/** ユーザー取得(ページネーション)(総件数) */
	public int getUsersByPaginationTotalCount(UserListCriteria condition);
	
	/** ユーザー取得（1件） */
	public MUser getUserOne(String userId);
	
	/** 次のユーザーID取得 */
	public String getNextUserId(String userId);
	
	/** 次のユーザーID取得 */
	public String getBeforeUserId(String userId);
	
	/** ユーザー更新（1件） */
	public void updateUserOne(String userId, String password, String userName);
	
	/** ユーザー更新（1件） */
	public void updateUser(CustomMUser user);
	
	/** ユーザー削除（1件） */
	public void deleteUserOne(String userId);
	
	/** ユーザー削除（複数件） */
	public void deleteUsers(List<String> userIdList);
}
