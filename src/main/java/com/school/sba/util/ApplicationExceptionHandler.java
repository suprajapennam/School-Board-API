package com.school.sba.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.school.sba.exception.AcademicProgramNotFoundByIdException;
import com.school.sba.exception.AdminAlreadyExistsException;
import com.school.sba.exception.InvalidAcademicProgramAssignmentToTeacherException;
import com.school.sba.exception.InvalidProgramTypeException;
import com.school.sba.exception.InvalidUserRoleException;
import com.school.sba.exception.ScheduleExceededException;
import com.school.sba.exception.ScheduleNotFoundByIdException;
import com.school.sba.exception.ScheduleNotFoundBySchoolIdException;
import com.school.sba.exception.SchoolExceededException;
import com.school.sba.exception.SchoolNotFoundByIdException;
import com.school.sba.exception.SchoolNotFoundException;
import com.school.sba.exception.SubjectNotFoundByIdException;
import com.school.sba.exception.SubjectNotFoundInAcademicProgramException;
import com.school.sba.exception.UnauthorizedException;
import com.school.sba.exception.UniqueConstraintViolationException;
import com.school.sba.exception.UserNotFoundByIdException;
import com.school.sba.exception.SubjectNotAssignedToTeacherException;

@RestControllerAdvice
public class ApplicationExceptionHandler extends ResponseEntityExceptionHandler{

	private ResponseEntity<Object> exceptionStructure(HttpStatus status, String message, Object rootcause)
	{
		return new ResponseEntity<Object>(
				Map.of("status",status.value(),
						"message",message,
						"rootcause",rootcause),status);
	}
	
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatusCode status, WebRequest request) 
	{
		List<ObjectError> allErrors = ex.getAllErrors();
		Map<String, String> errors = new HashMap<String, String>();
		
		allErrors.forEach(error ->{
							FieldError fieldError = (FieldError) error;
							errors.put(fieldError.getField(), fieldError.getDefaultMessage());
									});
		return exceptionStructure(HttpStatus.BAD_REQUEST, "Validations failed for some inputs, please check your fields properly", errors);
	}
	
	@ExceptionHandler(AdminAlreadyExistsException.class)
	public ResponseEntity<Object> handleAdminAlreadyExists(AdminAlreadyExistsException ex)
	{
		return exceptionStructure(HttpStatus.BAD_REQUEST, ex.getMessage(), "Admin already exist, cannot create more than one admin");
	}
	
	@ExceptionHandler(InvalidUserRoleException.class)
	public ResponseEntity<Object> handleInvalidUserRole(InvalidUserRoleException ex)
	{
		return exceptionStructure(HttpStatus.BAD_REQUEST, ex.getMessage(), "Invalid user Role");
	}
	
	@ExceptionHandler(UniqueConstraintViolationException.class)
	public ResponseEntity<Object> handleUniqueConstraintViolation(UniqueConstraintViolationException ex)
	{
		return exceptionStructure(HttpStatus.BAD_REQUEST, ex.getMessage(), "Uniqueness should me maintained ");
	}
	
	@ExceptionHandler(UserNotFoundByIdException.class)
	public ResponseEntity<Object> handleUserNotFoundById(UserNotFoundByIdException ex)
	{
		return exceptionStructure(HttpStatus.NOT_FOUND, ex.getMessage(), "User with given Id not found, please provide a valid User Id");
	}
	
	@ExceptionHandler
	public ResponseEntity<Object> handleUnauthorized(UnauthorizedException ex)
	{
		return exceptionStructure(HttpStatus.NOT_ACCEPTABLE, ex.getMessage(), "Unauthorized Person");
	}
	
	@ExceptionHandler(SchoolExceededException.class)
	public ResponseEntity<Object> handleSchoolExceeded(SchoolExceededException ex)
	{
		return exceptionStructure(HttpStatus.UNAVAILABLE_FOR_LEGAL_REASONS, ex.getMessage(), "You can have only one school record in the database");
	}
	
	@ExceptionHandler(SchoolNotFoundByIdException.class)
	public ResponseEntity<Object> handleSchoolNotFoundById(SchoolNotFoundByIdException ex)
	{
		return exceptionStructure(HttpStatus.NOT_FOUND, ex.getMessage(), "School with given Id not found, please provide a valid School Id");
	}
	
	@ExceptionHandler(ScheduleExceededException.class)
	public ResponseEntity<Object> handleScheduleExceeded(ScheduleExceededException ex)
	{
		return exceptionStructure(HttpStatus.IM_USED, ex.getMessage(), "Schedule already set for the school.");
	}
	
	@ExceptionHandler(ScheduleNotFoundBySchoolIdException.class)
	public ResponseEntity<Object> handleScheduleNotFoundBySchoolId(ScheduleNotFoundBySchoolIdException ex)
	{
		return exceptionStructure(HttpStatus.NOT_FOUND, ex.getMessage(), "No schedule is associated with the given school");
	}
	
	@ExceptionHandler(ScheduleNotFoundByIdException.class)
	public ResponseEntity<Object> handleScheduleNotFoundById(ScheduleNotFoundByIdException ex)
	{
		return exceptionStructure(HttpStatus.NOT_FOUND, ex.getMessage(), "Schedule with given Id not found, please provide a valid Schedule Id");
	}
	
	@ExceptionHandler(InvalidProgramTypeException.class)
	public ResponseEntity<Object> handleInvalidProgramType(InvalidProgramTypeException ex)
	{
		return exceptionStructure(HttpStatus.BAD_GATEWAY, ex.getMessage(), "Invalid Program Type, please provide the Program Type as PRIMARY, SECONDARY, HIGHERSECONDARY, UNDERGRADUATE, POSTGRADUATE, DIPLOMA or OTHERS");
	}
	
	@ExceptionHandler(AcademicProgramNotFoundByIdException.class)
	public ResponseEntity<Object> handleAcademicProgramNotFoundById(AcademicProgramNotFoundByIdException ex)
	{
		return exceptionStructure(HttpStatus.NOT_FOUND, ex.getMessage(), "Academic Program with given Id not found, please provide a valid Program Id");
	}
	
	@ExceptionHandler(SubjectNotFoundByIdException.class)
	public ResponseEntity<Object> handleSubjectNotFoundById(SubjectNotFoundByIdException ex)
	{
		return exceptionStructure(HttpStatus.NOT_FOUND, ex.getMessage(), "Subject with given Id not found, please provide a valid Subject Id");
	}
	
	@ExceptionHandler(UsernameNotFoundException.class)
	public ResponseEntity<Object> handleUsernameNotFound(UsernameNotFoundException ex)
	{
		return exceptionStructure(HttpStatus.NOT_FOUND, ex.getMessage(), "Username not found");
	}
	
	@ExceptionHandler(SchoolNotFoundException.class)
	public ResponseEntity<Object> handleSchoolNotFound(SchoolNotFoundException ex)
	{
		return exceptionStructure(HttpStatus.NOT_FOUND, ex.getMessage(), "School Not found");
	}
	
	@ExceptionHandler(SubjectNotFoundInAcademicProgramException.class)
	public ResponseEntity<Object> handleSubjectNotFoundInAcademicProgram(SubjectNotFoundInAcademicProgramException ex)
	{
		return exceptionStructure(HttpStatus.BAD_REQUEST, ex.getMessage(), "Subjects are not associated with the academic program");
	}
	
	@ExceptionHandler(InvalidAcademicProgramAssignmentToTeacherException.class)
	public ResponseEntity<Object> handleInvalidAcademicProgramAssignmentToTeacher(InvalidAcademicProgramAssignmentToTeacherException ex)
	{
		return exceptionStructure(HttpStatus.BAD_REQUEST, ex.getMessage(), "Invalid Academic Program Assigned to Teacher");
	}
	
	@ExceptionHandler(SubjectNotAssignedToTeacherException.class)
	public ResponseEntity<Object> handleSubjectNotAssignedToTeacher(SubjectNotAssignedToTeacherException ex)
	{
		return exceptionStructure(HttpStatus.BAD_REQUEST, ex.getMessage(), "No subject has been assigned to the teacher");
	}
}
