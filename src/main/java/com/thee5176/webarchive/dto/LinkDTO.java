package com.thee5176.webarchive.dto;

public record LinkDTO(
	// Controller -> View
	String name,
	String url,
	String description,
	long tagId
) {
	
}
