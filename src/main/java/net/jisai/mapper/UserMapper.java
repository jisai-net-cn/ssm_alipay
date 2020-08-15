package net.jisai.mapper;

import net.jisai.pojo.User;

public interface UserMapper {
	/**
	 * 向数据库中插入新用户
	 * @param user
	 * @return 返回影响行数
	 */
	int insertUser(User user);
	/**
	 * 根据用户名和密码查询用户
	 * @param user
	 * @return
	 */
	User selcetUserByNameAndPassword(User user);
}
