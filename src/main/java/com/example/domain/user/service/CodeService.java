package com.example.domain.user.service;

import java.util.List;

import com.example.domain.user.model.MCode;

public interface CodeService {
	/** コード取得 */
	public List<MCode> getCategoryCode(String category);
}
