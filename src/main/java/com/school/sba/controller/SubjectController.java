package com.school.sba.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.school.sba.request_dto.SubjectRequest;
import com.school.sba.response_dto.AcademicProgramResponse;
import com.school.sba.response_dto.SubjectResponse;
import com.school.sba.service.SubjectService;
import com.school.sba.util.ResponseStructure;

@RestController
public class SubjectController {
	@Autowired
	private SubjectService subjectService;

	@PostMapping("/academic-programs/{programId}/subjects")
	@PreAuthorize("hasAuthority('ADMIN')")
	public ResponseEntity<ResponseStructure<AcademicProgramResponse>> addSubjectsToAcademicProgram(@RequestBody SubjectRequest subjectRequest, @PathVariable int programId)
	{
		return subjectService.addSubjectsToAcademicProgram(subjectRequest, programId);
	}
	
	@PutMapping("/academic-programs/{programId}")
	@PreAuthorize("hasAuthority('ADMIN')")
	public ResponseEntity<ResponseStructure<AcademicProgramResponse>> updateSubjectsToAcademicProgram(@RequestBody SubjectRequest subjectRequest, @PathVariable int programId)
	{
		return subjectService.updateSubjectsToAcademicProgram(subjectRequest,programId);
	}
	
	@GetMapping("/subjects")
	public ResponseEntity<ResponseStructure<List<SubjectResponse>>> findAllSubjects()
	{
		return subjectService.findAllSubjects();
	}
	
	@PutMapping("/subjects/{subjectId}/users/{userId}")
	@PreAuthorize("hasAuthority('ADMIN')")
	public ResponseEntity<ResponseStructure<SubjectResponse>> addSubjectToTeacher(@PathVariable int subjectId, @PathVariable int userId)
	{
		return subjectService.addSubjectToTeacher(subjectId,userId);
	}
}

