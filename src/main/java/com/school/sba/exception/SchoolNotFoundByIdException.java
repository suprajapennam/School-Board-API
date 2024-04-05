package com.school.sba.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class SchoolNotFoundByIdException extends RuntimeException {

	private String message;
}
