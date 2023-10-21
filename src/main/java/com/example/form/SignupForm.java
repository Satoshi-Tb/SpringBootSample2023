package com.example.form;

import java.util.Date;

import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class SignupForm {
	// NotNull/NotEmpty/NotBlankの違い
	// アノテーション   | null | 空文字 | 空白
	//-------------------------------------
	//@NotNull      | NG  | OK      | OK
	//@NotEmpty   | NG  | NG     | OK
	//@NotBlank    | NG  | NG     | NG
	
	@NotBlank
	@Email
	private String userId;
	
	@NotBlank
	@Length(min = 4, max = 100)
	@Pattern(regexp = "^[a-zA-Z0-9]+$")
	private String password;
	
	@NotBlank
	private String userName;
	
	// pattern属性に設定したフォーマット文字列を、Date型にバインドする
	@DateTimeFormat(pattern = "yyyy/MM/dd")
	@NotNull
	private Date birthday;
	
	@Min(20)
	@Max(200)
	private Integer age;
	
	@NotNull
	private Integer gender;
}
