package com.school.sba.service;

import org.springframework.http.ResponseEntity;

import com.school.sba.request_dto.ScheduleRequest;
import com.school.sba.response_dto.ScheduleResponse;
import com.school.sba.util.ResponseStructure;

import jakarta.validation.Valid;

public interface ScheduleService {

	ResponseEntity<ResponseStructure<ScheduleResponse>> saveSchedule(@Valid ScheduleRequest scheduleRequest,int schoolId);

	ResponseEntity<ResponseStructure<ScheduleResponse>> findScheduleBySchoolId(int schoolId);

	ResponseEntity<ResponseStructure<ScheduleResponse>> updateSchedule(@Valid ScheduleRequest scheduleRequest,int scheduleId);


}
