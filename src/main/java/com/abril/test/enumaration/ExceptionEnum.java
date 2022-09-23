package com.abril.test.enumaration;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ExceptionEnum {
	
	NO_CONTENT(1, "No Content"),
	NOT_FOUND(1, "Not Found");
	
	Integer id;
	String description;

}
