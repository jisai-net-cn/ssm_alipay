package net.jisai.service;

import net.jisai.pojo.User;

public interface UserService {
	/**
	 * 新增用户
	 * @param user
	 * @return
	 */
	int addUser(User user);
	/**
	 * 根据用户名和密码查询用户
	 * @param user
	 * @return
	 */
	User login(User user);
}
