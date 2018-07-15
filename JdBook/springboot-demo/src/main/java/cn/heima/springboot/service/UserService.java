package cn.heima.springboot.service;

import java.util.List;

import cn.heima.springboot.pojo.User;

public interface UserService {
	List<User> findAll();
}
