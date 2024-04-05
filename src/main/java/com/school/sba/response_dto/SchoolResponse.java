package com.school.sba.response_dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SchoolResponse {

	private int schoolId;
	private String schoolName;
	private long contactNo;
	private String emailId;
	private String address;
}
