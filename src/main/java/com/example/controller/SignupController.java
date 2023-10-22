package com.example.controller;

import java.util.Locale;
import java.util.Map;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.application.service.UserApplicationService;
import com.example.domain.user.model.MUser;
import com.example.domain.user.service.UserService;
import com.example.form.GroupOrder;
import com.example.form.SignupForm;

import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("/user")
@Slf4j
public class SignupController {
	// @Slf4jはLombokのアノテーション。これをクラスに付けるとlogというstatic変数が用意される。その変数のメソッドを使うことで簡単にログ出力ができる。
	
	@Autowired
	private UserApplicationService userApplicationService;

	@Autowired
	private UserService userService;
	
	@Autowired
	private ModelMapper modelMapper;
	
	//ユーザー登録画面を表示
	@GetMapping("/signup")
	public String getSignup(Model model, Locale locale, @ModelAttribute SignupForm form) {
		// @ModelAttribute 自動でModelにインスタンスを登録してくれる。クラス名の先頭を小文字にした文字列をキーに登録してくれる。
		// model.addAttribute("signupForm", form)のイメージ
		Map<String, Integer> genderMap = userApplicationService.getGenderMap(locale);
		model.addAttribute("genderMap", genderMap);
		
		// サインアップ画面に遷移
		return "user/signup";
	}
	
	//ユーザー登録処理
	@PostMapping("/signup")
	public String postSignup(Model model, Locale locale,
			@ModelAttribute @Validated(GroupOrder.class) SignupForm form, 
			BindingResult bindingResult) {
		
		//入力チェック結果
		if (bindingResult.hasErrors()) {
			//NG: ユーザー登録画面に戻す
			return getSignup(model, locale, form);
		}
		
		log.info(form.toString());
		
		//formをMUserクラスに変換。
		//フォームのプロパティと、モデルのプロパティを一致させておくのがポイント。
		//全てを完全一致させる必要は無い。モデルに転送させたいプロパティを一致させておくこと。
		//
		//formクラスをサービスに渡さないようにする。理由は以下の通り
		//・画面に変更があっても、サービスの修正が不要になる。
		//・他の画面からもサービスを再利用できるようになる。
		MUser user = modelMapper.map(form, MUser.class);
		
		//ユーザー登録
		userService.signup(user);
		
		//ログイン画面にリダイレクト
		return "redirect:/login";
	}
	
}
