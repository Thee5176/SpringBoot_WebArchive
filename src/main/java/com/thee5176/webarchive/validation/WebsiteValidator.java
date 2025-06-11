package com.thee5176.webarchive.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class WebsiteValidator implements ConstraintValidator<Website,String> {

	@Override
	public void initialize(Website website) {}
	
	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		if (value == null) {
			return false;			
		}
//		return value.matches("^(https?://)?([\\\\w-]+\\\\.)+[\\\\w-]+(/[\\\\w- ./?%&=]*)?$");
		return value.matches("^https?:\\\\/\\\\/(?:www\\\\.)?[-a-zA-Z0-9@:%._\\\\+~#=]{1,256}\\\\.[a-zA-Z0-9()]{1,6}\\\\b(?:[-a-zA-Z0-9()@:%_\\\\+.~#?&\\\\/=]*)$");
	}

}
