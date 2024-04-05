package com.school.sba.service;

import org.springframework.http.ResponseEntity;

import com.school.sba.request_dto.SchoolRequest;
import com.school.sba.response_dto.SchoolResponse;
import com.school.sba.util.ResponseStructure;

import jakarta.validation.Valid;

public interface SchoolService {

	ResponseEntity<ResponseStructure<SchoolResponse>> saveSchool(@Valid SchoolRequest schoolRequest);

}
