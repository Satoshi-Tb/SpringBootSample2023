package com.example.controller;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.domain.user.service.UserService;
import com.example.form.UserDetailForm;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/user")
public class UserDetailController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private ModelMapper modelMapper;
	
	/** ユーザー詳細画面を表示 */
	// ユーザー名がメアド形式のため、userIdではuser@xxx.co.jpが取得できない。正規表現として:.+追加することで対応
	@GetMapping("/detail/{userId:.+}")
	public String getUserOne(UserDetailForm form, Model model, @PathVariable("userId") String userId) {

		// ユーザーを1件取得
		var user = userService.getUserOne(userId);
		user.setPassword(null);
		
		// MUserをFormに変換
		form = modelMapper.map(user, UserDetailForm.class);

		model.addAttribute("userDetailForm", form);
		
		// ユーザー詳細画面を表示
		return "user/detail";
	}
	
	/** ユーザー更新処理 */
	// params属性は、type=submitのbuttonタグのname属性と同じ値を指定する。こうすることで、同一のformタグ内のボタンであっても、
	// コントローラで受け取るメソッドを変更することができる。
	// value属性にはURLを指定する。
	@PostMapping(value = "/detail", params = "update")
	public String updateUser(Model model, UserDetailForm form) {
		
		userService.updateUserOne(form.getUserId(), form.getPassword(), form.getUserName());

		// ユーザー一覧画面にリダイレクト
		return "redirect:/user/list";
	}
	
	/** ユーザー削除処理 */
	@PostMapping(value = "/detail", params = "delete")
	public String deleteUser(Model model, UserDetailForm form) {
		
		userService.deleteUserOne(form.getUserId());
		
		// ユーザー一覧画面にリダイレクト
		return "redirect:/user/list";
	}
}
