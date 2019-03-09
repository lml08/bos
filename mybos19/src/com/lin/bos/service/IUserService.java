package com.lin.bos.service;

import com.lin.bos.domain.User;

public interface IUserService {

	User login(User model);

	void editPassword(String password, String id);


}
