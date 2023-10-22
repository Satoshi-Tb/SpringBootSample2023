package com.example.controller;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.example.domain.user.model.MUser;
import com.example.domain.user.service.UserService;
import com.example.form.UserListForm;

@Controller
@RequestMapping("/user")
public class UserListController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private ModelMapper modelMapper;
	
	/** ユーザー一覧画面を表示 */
	@RequestMapping(path = "/list", method = { RequestMethod.GET, RequestMethod.POST })
	public String getUserList(Model model, @ModelAttribute UserListForm form) {
		// formをMUserクラスに変換
		MUser user = modelMapper.map(form, MUser.class);
		
		// ユーザー一覧取得
		var userList = userService.getUsers(user);
		
		// Modelに登録
		model.addAttribute("userList", userList);
		
		// ユーザー一覧を表示
		return "user/list";
	}
	
}