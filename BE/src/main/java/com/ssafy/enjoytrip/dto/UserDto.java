package com.ssafy.enjoytrip.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class UserDto {

	private String userId;
	private String userName;
	private String userPassword;
	private String emailId;
	private String emailDomain;
	private String joinDate;
	private String refreshToken;
	
	private String userUuid;
}
