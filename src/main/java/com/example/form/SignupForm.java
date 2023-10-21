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
	
	// groups属性にて、バリデーション順番を指定
	
	@NotBlank(groups = ValidGroup1.class)
	@Email(groups = ValidGroup2.class)
	private String userId;
	
	@NotBlank(groups = ValidGroup1.class)
	@Length(min = 4, max = 100, groups = ValidGroup2.class)
	@Pattern(regexp = "^[a-zA-Z0-9]+$")
	private String password;
	
	@NotBlank(groups = ValidGroup1.class)
	private String userName;
	
	// pattern属性に設定したフォーマット文字列を、Date型にバインドする
	@DateTimeFormat(pattern = "yyyy/MM/dd")
	@NotNull(groups = ValidGroup1.class)
	private Date birthday;
	
	@Min(value= 20, groups = ValidGroup2.class)
	@Max(value = 200, groups = ValidGroup2.class)
	private Integer age;
	
	@NotNull(groups = ValidGroup1.class)
	private Integer gender;
}
