package com.school.sba.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.school.sba.request_dto.AcademicProgramRequest;
import com.school.sba.response_dto.AcademicProgramResponse;
import com.school.sba.util.ResponseStructure;

import jakarta.validation.Valid;

public interface AcademicProgramService {

	ResponseEntity<ResponseStructure<AcademicProgramResponse>> saveAcademicProgram(
			@Valid AcademicProgramRequest academicProgramRequest,int schoolId);

	ResponseEntity<ResponseStructure<List<AcademicProgramResponse>>> findAllAcademicProgram(int schoolId);

	ResponseEntity<ResponseStructure<AcademicProgramResponse>> assignAcademicProgramToUser(int programId, int userId);

}
