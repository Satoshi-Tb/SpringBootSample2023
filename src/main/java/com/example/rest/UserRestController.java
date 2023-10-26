package com.example.rest;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.domain.user.model.MUser;
import com.example.domain.user.service.UserService;
import com.example.form.GroupOrder;
import com.example.form.SignupForm;
import com.example.form.UserDetailForm;
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
	
	@Autowired
	private MessageSource messageSource;
	
	@GetMapping("/get/list")
	public ResponseEntity<UserListResponse> getUserList(UserListForm form) {	
		var user = modelMapper.map(form, MUser.class);
		var userList = userService.getUsers(user);
		
		var response = new UserListResponse();
		response.setUserList(userList);
		response.setCode("0000");
		response.setMessage("");
		
		
		return new ResponseEntity<UserListResponse>(response,  HttpStatus.OK);
	}
	
	// ユーザー名がメアド形式のため、userIdではuser@xxx.co.jpが取得できない。正規表現として:.+追加することで対応
	@GetMapping("/detail/{userId:.+}")
	public ResponseEntity<UserResponse> getUserOne(@PathVariable("userId") String userId) {

		// ユーザーを1件取得
		var user = userService.getUserOne(userId);
		// user.setPassword(null);
		var response = new UserResponse();
		response.setUser(user);
		response.setCode("0000");
		response.setMessage("");
		
		return new ResponseEntity<UserResponse>(response,  HttpStatus.OK);
	}
	
	@PostMapping("/signup")
	public RestResult postSignup(@Validated(GroupOrder.class) SignupForm form, BindingResult bindingResult, Locale locale) {
		if (bindingResult.hasErrors()) {
			Map<String, String> errors = new HashMap<>();
			
			// エラーメッセージ取得
			for (FieldError error : bindingResult.getFieldErrors()) {
				String message = messageSource.getMessage(error, locale);
				errors.put(error.getField(), message);
			}
			
			// 結果:NG
			return new RestResult(90, errors);
		}
		
		MUser user = modelMapper.map(form, MUser.class);
		
		userService.signup(user);
		
		// 結果：OK
		return new RestResult(0, null);
	}
	
	@PutMapping("/update")  // Putメソッドにマップ
	public int updateUser(UserDetailForm form) {
		// ユーザーを更新
		userService.updateUserOne(form.getUserId(), form.getPassword(), form.getUserName());
		
		// なぜ0を返す？
		return 0;
	}
	
	@DeleteMapping("/delete")  // Deleteメソッドにマップ
	public int deleteUse(UserDetailForm form) {
		userService.deleteUserOne(form.getUserId());
		
		return 0;
	}

}
