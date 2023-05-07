package com.pmt.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

@Service
public class MapValidationExceptionService {
	
	
	public ResponseEntity<Object> handleMapValidationExceptionService(BindingResult result){
		if(result.hasErrors()) {
			Map<String, String> errorMap=new HashMap<>();
			for(FieldError f:result.getFieldErrors()) {
				errorMap.put(f.getField(),f.getDefaultMessage());
			}
			
			return new ResponseEntity<Object>(errorMap,HttpStatus.BAD_REQUEST);
		}
		return null;
	}
}
