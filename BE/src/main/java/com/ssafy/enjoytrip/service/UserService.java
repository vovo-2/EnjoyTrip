package com.ssafy.enjoytrip.service;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.ssafy.enjoytrip.dto.UserDto;

public interface UserService {
	int joinUser(UserDto userDto) throws SQLException;
	int updateUser(UserDto userDto) throws SQLException;
	int deleteUser(String userUuid) throws SQLException;

	UserDto login(Map<String,String> UserDto) throws Exception;
	UserDto userInfo(String userId) throws Exception;
	void saveRefreshToken(String userId, String refreshToken) throws Exception;
	Object getRefreshToken(String userId) throws Exception;
	void deleteRefreshToken(String userId) throws Exception;
}
