package com.ssafy.enjoytrip.service;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.ssafy.enjoytrip.dao.UserDao;
import com.ssafy.enjoytrip.dto.UserDto;

@Service
public class UserServiceImpl implements UserService {
	
	private UserDao userDao;

	public UserServiceImpl(UserDao userDao) {
		super();
		this.userDao = userDao;
	}

	@Override
	public UserDto login(Map<String,String> userDto) throws Exception {
		return userDao.login(userDto);
	}

	@Override
	public UserDto userInfo(String userId) throws Exception {
		return userDao.userInfo(userId);
	}

	@Override
	public void saveRefreshToken(String userId, String refreshToken) throws Exception {
		Map<String, String> map = new HashMap<String, String>();
		map.put("userId",userId);
		map.put("token", refreshToken);
		userDao.saveRefreshToken(map);
	}

	@Override
	public Object getRefreshToken(String userId) throws Exception {
		return userDao.getRefreshToken(userId);
	}

	@Override
	public void deleteRefreshToken(String userId) throws Exception {
		Map<String, String> map = new HashMap<String, String>();
		map.put("userId", userId);
		map.put("token", null);
		userDao.deleteRefreshToken(map);
	}
	@Override
	public int joinUser(UserDto memberDto) throws SQLException {
		memberDto.setUserUuid(UUID.randomUUID().toString());
		return userDao.joinUser(memberDto);
	}

	@Override
	public int updateUser(UserDto memberDto) throws SQLException {
		// TODO Auto-generated method stub
		return userDao.updateUser(memberDto);
	}

	@Override
	public int deleteUser(String userUuid) throws SQLException {
		// TODO Auto-generated method stub
		return userDao.deleteUser(userUuid);
	}

}
