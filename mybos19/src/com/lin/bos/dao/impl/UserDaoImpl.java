package com.lin.bos.dao.impl;
import java.util.List;

import org.hibernate.Query;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import com.lin.bos.dao.IUserDao;
import com.lin.bos.dao.base.impl.BaseDaoImpl;
import com.lin.bos.domain.User;

@Repository
//@Scope("prototype")
public class UserDaoImpl extends BaseDaoImpl<User> implements IUserDao{
	/**
	 * 根据用户名和密码查询用户
	 */
	public User findByUsernameAndPassword(String username, String password) {
		
		Query query = this.getSession().createQuery("from User u where u.username = :loginName and u.password = :loginPwd");
		query.setParameter("loginName", username).setParameter(
				"loginPwd", password);
		User list = (User) query.uniqueResult();
		System.out.println(password);
		System.out.println(list);
		return list;
	}
}
