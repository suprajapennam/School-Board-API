package com.school.sba.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.school.sba.request_dto.ScheduleRequest;
import com.school.sba.response_dto.ScheduleResponse;
import com.school.sba.service.ScheduleService;
import com.school.sba.util.ResponseStructure;

import jakarta.validation.Valid;

@RestController
public class ScheduleController {

	@Autowired
	private ScheduleService scheduleService;
	
	@PostMapping("/schools/{schoolId}/schedules")
	@PreAuthorize("hasAuthority('ADMIN')")
	public ResponseEntity<ResponseStructure<ScheduleResponse>> saveSchedule(@RequestBody @Valid ScheduleRequest scheduleRequest, @PathVariable int schoolId)
	{
		return scheduleService.saveSchedule(scheduleRequest, schoolId);
		
	}
	
	@GetMapping("/schools/{schoolId}/schedules")
	public ResponseEntity<ResponseStructure<ScheduleResponse>> findScheduleBySchoolId(@PathVariable int schoolId)
	{
		return scheduleService.findScheduleBySchoolId(schoolId);
	}
	
	@PutMapping("/schedules/{scheduleId}")
	@PreAuthorize("hasAuthority('ADMIN')")
	public ResponseEntity<ResponseStructure<ScheduleResponse>> updateSchedule(@RequestBody @Valid ScheduleRequest scheduleRequest, @PathVariable int scheduleId)
	{
		return scheduleService.updateSchedule(scheduleRequest,scheduleId);
	}
	
}
