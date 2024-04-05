package com.school.sba.serviceImpl;

import java.time.Duration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.school.sba.entity.Schedule;
import com.school.sba.exception.ScheduleExceededException;
import com.school.sba.exception.ScheduleNotFoundByIdException;
import com.school.sba.exception.ScheduleNotFoundBySchoolIdException;
import com.school.sba.exception.SchoolNotFoundByIdException;
import com.school.sba.repository.ScheduleRepository;
import com.school.sba.repository.SchoolRepository;
import com.school.sba.request_dto.ScheduleRequest;
import com.school.sba.response_dto.ScheduleResponse;
import com.school.sba.service.ScheduleService;
import com.school.sba.util.ResponseEntityProxy;
import com.school.sba.util.ResponseStructure;

import jakarta.validation.Valid;

@Service
public class ScheduleServiceImpl implements ScheduleService{
	@Autowired
	private ScheduleRepository scheduleRepository;
	@Autowired
	private SchoolRepository schoolRepository;
	
	//Mapper Methods
	private Schedule mapToSchedule(@Valid ScheduleRequest scheduleRequest)
	{
		return Schedule.builder()
				.opensAt(scheduleRequest.getOpensAt())
				.closesAt(scheduleRequest.getClosesAt())
				.classHoursPerDay(scheduleRequest.getClassHoursPerDay())
				.classHourLengthInMinutes(Duration.ofMinutes(scheduleRequest.getClassHourLengthInMinutes()))
				.breakTime(scheduleRequest.getBreakTime())
				.breakLengthInMinutes(Duration.ofMinutes(scheduleRequest.getBreakLengthInMinutes()))
				.lunchTime(scheduleRequest.getLunchTime())
				.lunchLengthInMinutes(Duration.ofMinutes(scheduleRequest.getLunchLengthInMinutes()))
				.build();
	}
	
	private ScheduleResponse mapToScheduleResponse(Schedule schedule)
	{
		return ScheduleResponse.builder()
				.scheduleId(schedule.getScheduleId())
				.opensAt(schedule.getOpensAt())
				.closesAt(schedule.getClosesAt())
				.classHoursPerDay(schedule.getClassHoursPerDay())
				.classHourLengthInMinutes((int)schedule.getClassHourLengthInMinutes().toMinutes())
				.breakTime(schedule.getBreakTime())
				.breakLengthInMinutes((int)schedule.getBreakLengthInMinutes().toMinutes())
				.lunchTime(schedule.getLunchTime())
				.lunchLengthInMinutes((int) schedule.getLunchLengthInMinutes().toMinutes())
				.build();
	}
	
	@Override
	public ResponseEntity<ResponseStructure<ScheduleResponse>> saveSchedule(@Valid ScheduleRequest scheduleRequest, int schoolId) {
	    return schoolRepository.findById(schoolId)
	            .map(school -> {
	            		if(school.getSchedule() == null)
		            	{
	            			Schedule schedule = scheduleRepository.save(mapToSchedule(scheduleRequest));
		            		school.setSchedule(schedule);
		            	    schoolRepository.save(school);
		            	    return ResponseEntityProxy.getResponseEntity(HttpStatus.CREATED, "Scheduled created for School", mapToScheduleResponse(schedule));
		            	}
	            		else
	            		{
	            			throw new ScheduleExceededException("You can't have more than one schedule for a school");
	            		}      
	            })
	            .orElseThrow(() -> new SchoolNotFoundByIdException("Invalid School Id"));
	}

	@Override
	public ResponseEntity<ResponseStructure<ScheduleResponse>> findScheduleBySchoolId(int schoolId) 
	{
		return schoolRepository.findById(schoolId)
		.map(school -> {
			Schedule schedule = school.getSchedule();
			if(schedule != null)
			{
				return ResponseEntityProxy.getResponseEntity(HttpStatus.FOUND, "Schedule found successfully", mapToScheduleResponse(schedule));
			}
			else
			{
				throw new ScheduleNotFoundBySchoolIdException("Schedule is not present for the given school Id");
			}
			
		}).orElseThrow(() -> new SchoolNotFoundByIdException("Invalid School Id"));
	}

	@Override
	public ResponseEntity<ResponseStructure<ScheduleResponse>> updateSchedule(@Valid ScheduleRequest scheduleRequest,int scheduleId) 
	{
		return scheduleRepository.findById(scheduleId)
		.map(s ->{
			Schedule updatedSchedule = mapToSchedule(scheduleRequest);
			updatedSchedule.setScheduleId(s.getScheduleId());
			Schedule schedule = scheduleRepository.save(updatedSchedule);
			return ResponseEntityProxy.getResponseEntity(HttpStatus.FOUND, "Schedule updated successfully", mapToScheduleResponse(schedule));
			
		}).orElseThrow(() -> new ScheduleNotFoundByIdException("Invalid Schedule Id"));
	}

}
