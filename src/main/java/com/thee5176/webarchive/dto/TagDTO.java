package com.thee5176.webarchive.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public record TagDTO(
	@NotEmpty
	@Size(min=2, max=15, message="タッグ名:2-15文字を抑えてください")
	String name,
	@NotEmpty
	@Size(min=2,max=50, message="詳細:2-15文字を抑えてください")
	String description
	) {
	
}
