package com.example.controller;

import org.springframework.stereotype.Controller;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class LogoutController {

	//SpringSecurityでログアウト処理を実施しているため、本コントローラは不要
//	//ログイン画面にリダイレクト
//	@PostMapping("/logout")
//	public String postLogout() {
//		log.info("ログアウト");
//		return "redirect:/login";
//	}
}