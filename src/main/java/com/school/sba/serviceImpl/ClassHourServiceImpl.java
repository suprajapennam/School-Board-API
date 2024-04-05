package com.school.sba.serviceImpl;

import java.time.LocalDateTime;
import java.time.LocalTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.school.sba.entity.ClassHour;
import com.school.sba.entity.Schedule;
import com.school.sba.entity.School;
import com.school.sba.enums.ClassStatus;
import com.school.sba.exception.AcademicProgramNotFoundByIdException;
import com.school.sba.exception.ScheduleNotFoundBySchoolIdException;
import com.school.sba.repository.AcademicProgramRepository;
import com.school.sba.repository.ClassHourRepository;
import com.school.sba.service.ClassHourService;
import com.school.sba.util.ResponseEntityProxy;
import com.school.sba.util.ResponseStructure;

@Service
public class ClassHourServiceImpl implements ClassHourService {
	
	@Autowired
	private AcademicProgramRepository academicProgramRepository;
	@Autowired
	private ClassHourRepository classHourRepository;

	private boolean isBreakTime(LocalDateTime beginsAt, LocalDateTime endsAt, Schedule schedule)
	{
		LocalTime breakTimeStart = schedule.getBreakTime();
		
		return ((breakTimeStart.isAfter(beginsAt.toLocalTime()) && breakTimeStart.isBefore(endsAt.toLocalTime())) || breakTimeStart.equals(beginsAt.toLocalTime()));
	}
	
	private boolean isLunchTime(LocalDateTime beginsAt, LocalDateTime endsAt , Schedule schedule)
	{
		LocalTime lunchTimeStart = schedule.getLunchTime();
		
		return ((lunchTimeStart.isAfter(beginsAt.toLocalTime()) && lunchTimeStart.isBefore(endsAt.toLocalTime())) || lunchTimeStart.equals(beginsAt.toLocalTime()));
    }
	
	@Override
	public ResponseEntity<ResponseStructure<String>> generateClassHourForAcademicProgram(int programId) 
	{
		return academicProgramRepository.findById(programId)
		.map(academicProgarm -> {
			School school = academicProgarm.getSchool();
			Schedule schedule = school.getSchedule();
			if(schedule!=null)
			{
				int classHourPerDay = schedule.getClassHoursPerDay();
				int classHourLength = (int) schedule.getClassHourLengthInMinutes().toMinutes();
				
				LocalDateTime currentTime = LocalDateTime.now().with(schedule.getOpensAt());
				
				LocalDateTime lunchTimeStart = LocalDateTime.now().with(schedule.getLunchTime());
				LocalDateTime lunchTimeEnd = lunchTimeStart.plusMinutes(schedule.getLunchLengthInMinutes().toMinutes());
				LocalDateTime breakTimeStart = LocalDateTime.now().with(schedule.getBreakTime());
				LocalDateTime breakTimeEnd = breakTimeStart.plusMinutes(schedule.getBreakLengthInMinutes().toMinutes());
				
				for(int day = 1 ; day <= 6 ; day++)
				{
					for(int hour = 1 ; hour <= classHourPerDay+2 ; hour++)
					{
						ClassHour classHour = new ClassHour();
						LocalDateTime beginsAt = currentTime;
						LocalDateTime endsAt = beginsAt.plusMinutes(classHourLength);
						
						if(!isLunchTime(beginsAt, endsAt, schedule))
						{
							if(!isBreakTime(beginsAt, endsAt, schedule))
							{
								classHour.setBeginsAt(beginsAt);
								classHour.setEndsAt(endsAt);
								classHour.setClassStatus(ClassStatus.NOT_SCHEDULED);
								
								currentTime = endsAt;
							}
							else
							{
								classHour.setBeginsAt(breakTimeStart);
								classHour.setEndsAt(breakTimeEnd);
								classHour.setClassStatus(ClassStatus.BREAK_TIME);
								currentTime = breakTimeEnd;
							}
						}
						else
						{
							classHour.setBeginsAt(lunchTimeStart);
							classHour.setEndsAt(lunchTimeEnd);
							classHour.setClassStatus(ClassStatus.LUNCH_TIME);
							currentTime = lunchTimeEnd;
						}
						classHour.setAcademicProgram(academicProgarm);
						classHourRepository.save(classHour);
					}
					currentTime = currentTime.plusDays(1).with(schedule.getOpensAt());
				}
	
			}
			else
				throw new ScheduleNotFoundBySchoolIdException("The school does not contain any schedule, please provide a schedule to the school");
			
			return ResponseEntityProxy.getResponseEntity(HttpStatus.CREATED, "ClassHour generated successfully for the academic progarm","Class Hour generated for the current week successfully");
		})
		.orElseThrow(() -> new AcademicProgramNotFoundByIdException("Invalid Program Id"));
	}

}
