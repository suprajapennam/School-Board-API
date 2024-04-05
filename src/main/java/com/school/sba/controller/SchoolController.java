package com.school.sba.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.school.sba.request_dto.SchoolRequest;
import com.school.sba.response_dto.SchoolResponse;
import com.school.sba.service.SchoolService;
import com.school.sba.util.ResponseStructure;

import jakarta.validation.Valid;

@RestController
public class SchoolController {
	@Autowired
	private SchoolService schoolService;
	
	@PostMapping("/schools")
	@PreAuthorize("hasAuthority('ADMIN')")
	public ResponseEntity<ResponseStructure<SchoolResponse>> saveSchool(@RequestBody @Valid SchoolRequest schoolRequest)
	{
		return schoolService.saveSchool(schoolRequest);
		
	}
	
}
