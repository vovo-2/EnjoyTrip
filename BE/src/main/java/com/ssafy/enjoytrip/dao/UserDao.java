package com.ssafy.enjoytrip.dao;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.enjoytrip.dto.UserDto;


@Mapper
public interface UserDao {

	int joinUser(UserDto memberDto) throws SQLException;
	int updateUser(UserDto memberDto) throws SQLException;
	int deleteUser(String userUuid) throws SQLException;

	UserDto login(Map<String,String> UserDto) throws Exception;
	UserDto userInfo(String userId) throws Exception;
	void saveRefreshToken(Map<String,String> userDto)throws Exception;
	Object getRefreshToken(String userId) throws Exception;
	void deleteRefreshToken(Map<String,String> userDto) throws Exception;
}
