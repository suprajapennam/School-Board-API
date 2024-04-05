package com.school.sba.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.school.sba.request_dto.SubjectRequest;
import com.school.sba.response_dto.AcademicProgramResponse;
import com.school.sba.response_dto.SubjectResponse;
import com.school.sba.util.ResponseStructure;

public interface SubjectService {

	ResponseEntity<ResponseStructure<AcademicProgramResponse>> addSubjectsToAcademicProgram(SubjectRequest subjectRequest, int programId);

	ResponseEntity<ResponseStructure<AcademicProgramResponse>> updateSubjectsToAcademicProgram(SubjectRequest subjectRequest,int programId);

	ResponseEntity<ResponseStructure<List<SubjectResponse>>> findAllSubjects();

	ResponseEntity<ResponseStructure<SubjectResponse>> addSubjectToTeacher(int subjectId, int userId);

}
