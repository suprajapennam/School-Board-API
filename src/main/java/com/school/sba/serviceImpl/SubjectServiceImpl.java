package com.school.sba.serviceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.school.sba.entity.AcademicProgram;
import com.school.sba.entity.Subject;
import com.school.sba.entity.User;
import com.school.sba.enums.UserRole;
import com.school.sba.exception.AcademicProgramNotFoundByIdException;
import com.school.sba.exception.InvalidUserRoleException;
import com.school.sba.exception.SubjectNotFoundByIdException;
import com.school.sba.exception.UserNotFoundByIdException;
import com.school.sba.repository.AcademicProgramRepository;
import com.school.sba.repository.SubjectRepository;
import com.school.sba.repository.UserRepository;
import com.school.sba.request_dto.SubjectRequest;
import com.school.sba.response_dto.AcademicProgramResponse;
import com.school.sba.response_dto.SubjectResponse;
import com.school.sba.service.SubjectService;
import com.school.sba.util.ResponseEntityProxy;
import com.school.sba.util.ResponseStructure;

@Service
public class SubjectServiceImpl implements SubjectService {
	@Autowired
	private SubjectRepository subjectRepository;
	@Autowired
	private AcademicProgramRepository academicProgramRepository;
	@Autowired
	private AcademicProgramServiceImpl academicProgramServiceImpl;
	@Autowired
	private UserRepository userRepository;

	//Mapper Method
	private SubjectResponse mapToSubjectResponse(Subject subject)
	{
		return SubjectResponse.builder()
				.subjectId(subject.getSubjectId())
				.subjectName(subject.getSubjectName().toUpperCase())
				.build();
	}

	@Override
	public ResponseEntity<ResponseStructure<AcademicProgramResponse>> addSubjectsToAcademicProgram(SubjectRequest subjectRequest,int programId) 
	{
		return academicProgramRepository.findById(programId).map(academicProgram -> {
			List<Subject> subjects = new ArrayList<Subject>(); 

			subjectRequest.getSubjectNames().forEach(subjectName -> {

				Subject subject = subjectRepository.findBySubjectName(subjectName.toLowerCase()).map(s -> s).orElseGet(() -> {
					Subject s = new Subject();
					s.setSubjectName(subjectName.toLowerCase());
					subjectRepository.save(s);
					return s;
				});	

				subjects.add(subject);

			});
			academicProgram.setSubjects(subjects);
			academicProgramRepository.save(academicProgram);
			return ResponseEntityProxy.getResponseEntity(HttpStatus.CREATED, "Subjects added to the Academic Program", academicProgramServiceImpl.mapToAcademicProgramResponse(academicProgram));

		}).orElseThrow(() -> new AcademicProgramNotFoundByIdException("Invalid Program Id"));

	}

	@Override
	public ResponseEntity<ResponseStructure<AcademicProgramResponse>> updateSubjectsToAcademicProgram(SubjectRequest subjectRequest,int programId)
	{
		AcademicProgram academicProgram = academicProgramRepository.findById(programId)
				.orElseThrow(() -> new AcademicProgramNotFoundByIdException("Invalid program Id"));

		Set<String> existingSubjectNames = academicProgram.getSubjects().stream()
				.map(subject -> subject.getSubjectName()).collect(Collectors.toSet());


		for(String subjectName : subjectRequest.getSubjectNames())
		{
			if(!existingSubjectNames.contains(subjectName.toLowerCase()))
			{
				Subject subject = subjectRepository.findBySubjectName(subjectName.toLowerCase())
						.orElseGet(() -> {
							Subject s = new Subject();
							s.setSubjectName(subjectName.toLowerCase());
							return subjectRepository.save(s);
						});
				academicProgram.getSubjects().add(subject);
			}
		}

		academicProgram.getSubjects().removeIf(subject ->subjectRequest.getSubjectNames().stream()
				.noneMatch(subjectName -> subjectName.toLowerCase().equalsIgnoreCase(subject.getSubjectName())));

		academicProgram = academicProgramRepository.save(academicProgram);
		return ResponseEntityProxy.getResponseEntity(HttpStatus.OK, "Subject List of Academic Program updated successfully.", academicProgramServiceImpl.mapToAcademicProgramResponse(academicProgram));

	}

	@Override
	public ResponseEntity<ResponseStructure<List<SubjectResponse>>> findAllSubjects()
	{
		List<Subject> subjects = subjectRepository.findAll();
		List<SubjectResponse> subjectResponses = subjects.stream().map(this::mapToSubjectResponse).collect(Collectors.toList());
		return ResponseEntityProxy.getResponseEntity(HttpStatus.FOUND, "All the subjects found successfully.", subjectResponses);
	}

	@Override
	public ResponseEntity<ResponseStructure<SubjectResponse>> addSubjectToTeacher(int subjectId, int userId) 
	{
		User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundByIdException("Invalid User Id"));

		if(user.getUserRole().equals(UserRole.TEACHER))
		{

			return subjectRepository.findById(subjectId).map(subject -> {

				user.setSubject(subject);
				userRepository.save(user);

				return ResponseEntityProxy.getResponseEntity(HttpStatus.OK, "Subject added to the Teacher successfully.", mapToSubjectResponse(subject));


			}).orElseThrow(() -> new SubjectNotFoundByIdException("Invalid Subject Id"));
		}
		else {
			throw new InvalidUserRoleException("Subjects should be assigned to Teachers only");
		}

	}
}