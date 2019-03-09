package com.lin.bos.service.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lin.bos.dao.IUserDao;
import com.lin.bos.domain.User;
import com.lin.bos.service.IUserService;
@Service
@Transactional
public class UserServiceImpl implements IUserService {
	@Autowired
	private IUserDao userDao;

	@Override
	public User login(User user) {
		String username = user.getUsername();
		String password = user.getPassword();
		return userDao.findByUsernameAndPassword(username, password);
	}

	@Override
	public void editPassword(String password, String id) {
		userDao.executeUpdate("editPassword", password,id);
	}
}
