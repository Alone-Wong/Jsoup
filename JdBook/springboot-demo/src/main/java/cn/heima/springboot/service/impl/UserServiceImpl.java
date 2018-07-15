package cn.heima.springboot.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.heima.springboot.dao.UserDao;
import cn.heima.springboot.pojo.User;
import cn.heima.springboot.service.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserDao userDao;

	@Override
	public List<User> findAll() {
		List<User> list = userDao.findAll();
		return list;
	}

}
