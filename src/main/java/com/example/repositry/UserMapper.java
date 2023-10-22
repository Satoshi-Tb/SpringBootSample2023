package com.example.repositry;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.example.domain.user.model.MUser;

/** 
 * MyBatisでリポジトリを作成するためには、Javaのインタフェースに@Mapperアノテーションを指定する。
 * ORMのDAOクラスのようなもの
 *  */
@Mapper
public interface UserMapper {
	
	/** ユーザー登録 */
	public int insertOne(MUser user);
	
	/** ユーザー取得 */
	public List<MUser> findMany();
	
	/** ユーザー取得（1件） */
	public MUser findOne(String userId);

}