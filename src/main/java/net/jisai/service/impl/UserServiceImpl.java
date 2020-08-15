package net.jisai.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import net.jisai.mapper.UserMapper;
import net.jisai.pojo.User;
import net.jisai.service.UserService;

@Service
@Transactional
public class UserServiceImpl implements UserService {
	@Resource
	private UserMapper usermapper;
	@Override
	public int addUser(User user) {
		// TODO Auto-generated method stub
		return usermapper.insertUser(user);
	}
	@Override
	public User login(User user) {
		// TODO Auto-generated method stub
		return usermapper.selcetUserByNameAndPassword(user);
	}
}
