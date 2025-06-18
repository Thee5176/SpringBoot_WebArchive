package com.thee5176.webarchive.dto;

import com.thee5176.webarchive.validation.Website;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public record LinkDTO(
	@NotEmpty(message = "サイト名:入力してください")
	@Size(min=2, max=15, message="サイト名:2-15文字を抑えてください")
	String name,
	
	@NotEmpty(message = "リンク:URLを入力してください")
	@Website
	String url,
	
	@Size(min=2, max=50, message="詳細:2-50文字を抑えてください")
	String description,
	
	@Min( value = 1L, message = "タッグ:選択してください")
	Long tagId
) {
	
}
