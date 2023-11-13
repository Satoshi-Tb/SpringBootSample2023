package com.example.domain.user.service;

import java.util.List;

import com.example.domain.user.model.MUser;
import com.example.rest.UserListCriteria;

public interface UserService {
	
	/** ユーザー登録 */
	public void signup(MUser user);
	
	/** ユーザー取得 */
	public List<MUser> getUsers(MUser user);
	
	/** ユーザー取得(ページネーション) */
	public List<MUser> getUsersByPagination(UserListCriteria condition);
	
	/** ユーザー取得（1件） */
	public MUser getUserOne(String userId);
	
	/** ユーザー更新（1件） */
	public void updateUserOne(String userId, String password, String userName);
	
	/** ユーザー削除（1件） */
	public void deleteUserOne(String userId);
}
