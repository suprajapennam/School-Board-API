package com.school.sba.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class SubjectNotFoundInAcademicProgramException extends RuntimeException{

	private String message;
}
