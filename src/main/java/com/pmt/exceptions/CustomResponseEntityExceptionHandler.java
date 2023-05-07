package com.pmt.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class CustomResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {
	
	@ExceptionHandler(ProjectIdException.class)
	public ResponseEntity<Object> handleResponseEntityException(ProjectIdException ex,WebRequest request){
		ProjectIdExceptionResponse exceptionResponse =new ProjectIdExceptionResponse(ex.getMessage());
	return new ResponseEntity<Object>(exceptionResponse,HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(ProjectNotFoundException.class)
	public ResponseEntity<Object> handleProjectNotFound(ProjectNotFoundException ex){
		ProjectIdExceptionResponse exceptionResponse=new ProjectIdExceptionResponse(ex.getMessage());
		return new ResponseEntity<Object>(exceptionResponse,HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(UsernameAlreadyExistException.class)
	public ResponseEntity<Object> handleUsernameAlreadyExistException(UsernameAlreadyExistException ex,WebRequest request){
		UsernameAlreadyExistExceptionResponse alreadyExistExceptionResponse=new UsernameAlreadyExistExceptionResponse(ex.getMessage());
		return new ResponseEntity<Object>(alreadyExistExceptionResponse,HttpStatus.BAD_REQUEST);
	}
}
