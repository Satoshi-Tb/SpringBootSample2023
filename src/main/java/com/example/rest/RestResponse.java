package com.example.rest;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RestResponse<T> {

	/** リターンコード */
	private String code;
	
	/**
	 * エラーマップ
	 * key: フィールド名
	 * value: エラーメッセージ
	 * */
	private Map<String, String> errors;
	
	/**
	 * 
	 * */
	private T data;
	
	public static <S> ResponseEntity<RestResponse<S>>  createResponse(String code,Map<String, String> errors, HttpStatus status, S response) {
		return new ResponseEntity<RestResponse<S>>(new RestResponse<S>(code, errors, response), status);
	}
}