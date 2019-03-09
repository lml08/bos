package com.lin.bos.dao;

import com.lin.bos.dao.base.IBaseDao;
import com.lin.bos.domain.User;

public interface IUserDao extends IBaseDao<User>{
	public User findByUsernameAndPassword(String username, String password);
}
