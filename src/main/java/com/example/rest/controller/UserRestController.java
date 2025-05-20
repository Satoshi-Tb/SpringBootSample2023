package com.example.rest.controller;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.domain.user.model.CustomMUser;
import com.example.domain.user.model.FilterItem;
import com.example.domain.user.model.MUser;
import com.example.domain.user.service.UserListCriteria;
import com.example.domain.user.service.UserService;
import com.example.form.UserListForm;

/**
 * RestControllerアノテーションをクラスに付けると、そのクラス内のメソッドの戻り値が、HTTPレスポンスボディとなる
 * */
@RestController
@RequestMapping("/api/user")
public class UserRestController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@GetMapping("/get/list")
	public ResponseEntity<UserListResponse> getUserList(UserListForm form) {	
		var user = modelMapper.map(form, MUser.class);
		var userList = userService.getUsers(user);
		
		var response = new UserListResponse();
		response.setTotalCount(userList.size());
		response.setData(userList);
		response.setCode("0000");
		response.setMessage("");
		
		
		return new ResponseEntity<UserListResponse>(response,  HttpStatus.OK);
	}
	
	
	// TODO WIP
	@GetMapping("/get/filter/{filterName}")
	public ResponseEntity<RestResponse<List<FilterItem>>> getUserListFilter(@PathVariable String filterName, @ModelAttribute UserListCriteria condition) {	
		condition.setOffset(condition.getPage() * condition.getSize());

		return RestResponse.createSuccessResponse(userService.getUsersFilter(filterName, condition));
	}
	
	
	@PostMapping("/get/list-pager")
	public ResponseEntity<RestResponse<UserListPaginationResponse>> getUserListByPaginationPost(@RequestBody UserListCriteria condition) {	
		condition.setOffset(condition.getPage() * condition.getSize());
		return getUserListByPagination(condition);
	}
	
	@GetMapping("/get/list-pager")
	public ResponseEntity<RestResponse<UserListPaginationResponse>> getUserListByPaginationGet(@ModelAttribute  UserListCriteria condition) {	
		condition.setOffset(condition.getPage() * condition.getSize());
		return getUserListByPagination(condition);
	}
	
	private ResponseEntity<RestResponse<UserListPaginationResponse>> getUserListByPagination(UserListCriteria condition) {
		// １ページあたりのデータ
		var userList = userService.getUsersByPagination(condition);
		// 検索条件に対する総件数
		int totalCount = userService.getUsersByPaginationTotalCount(condition);
		
		var response = new UserListPaginationResponse();
		response.setResultNum(totalCount);
		response.setUserList(userList);

		return RestResponse.createSuccessResponse(response);
	}
	
	// ユーザー名がメアド形式のため、userIdではuser@xxx.co.jpが取得できない。正規表現として:.+追加することで対応
	@GetMapping("/detail/{userId:.+}")
	public ResponseEntity<RestResponse<UserResponse>> getUserOne(@PathVariable("userId") String userId) {

		// ユーザーを1件取得
		MUser user = userService.getUserOne(userId);
		String nextUserId = userService.getNextUserId(userId);
		String beforeUserId = userService.getBeforeUserId(userId);

		UserResponse response = new UserResponse();
		// データなし：エラー
		response.setUser(user);
		response.setNextUserId(nextUserId);
		response.setBeforeUserId(beforeUserId);
		
		return RestResponse.createSuccessResponse(response);
	}
	
	@PostMapping("/signup")
	public ResponseEntity<RestResponse<MUser>> postSignup(@RequestBody @Validated UserRequest req) {
		MUser user = modelMapper.map(req, MUser.class);
		
		userService.signup(user);
		
		// 結果：OK
		return RestResponse.createSuccessResponse(user);
	}
	
	@PutMapping("/update")  // Putメソッドにマップ
	public ResponseEntity<RestResponse<MUser>> updateUser(@RequestBody UserRequest req) {
		CustomMUser user = modelMapper.map(req, CustomMUser.class);
		
		// ユーザーを更新
		userService.updateUser(user);
		
		// ユーザーを1件取得
		var resp = userService.getUserOne(user.getUserId());
		
		return RestResponse.createSuccessResponse(resp);
	}
	
	@DeleteMapping("/delete")  // Deleteメソッドにマップ
	public ResponseEntity<RestResponse<Object>> deleteUser(@RequestBody @Validated UserDeleteRequest req) {
		userService.deleteUsers(req.getUserIdList());
		
		// 結果：OK
		return RestResponse.createSuccessResponse();
		
		// エラーテスト用のレスポンス
		//return RestResponse.createErrorResponse("9999", null, HttpStatus.BAD_REQUEST);
	}
	
	// 引数違いのメソッド呼び出しテスト。MyBatisのマッパーで利用している引数欠落の場合、NGになる。
	@PutMapping("/update-test1")  // Putメソッドにマップ
	public ResponseEntity<RestResponse<MUser>> updateUserTest1(@RequestBody UserRequest req) {
		// ユーザーを更新
		userService.updateUserTest(req.getUserId(), req.getUserName());
		
		// ユーザーを1件取得
		var resp = userService.getUserOne(req.getUserId());
		
		return RestResponse.createSuccessResponse(resp);
	}

	@PutMapping("/update-test2")  // Putメソッドにマップ
	public ResponseEntity<RestResponse<MUser>> updateUserTest2(@RequestBody UserRequest req) {
		// ユーザーを更新
		userService.updateUserTest(req.getUserId(), req.getUserName(), req.getGender());
		
		// ユーザーを1件取得
		var resp = userService.getUserOne(req.getUserId());
		
		return RestResponse.createSuccessResponse(resp);
	}
}
