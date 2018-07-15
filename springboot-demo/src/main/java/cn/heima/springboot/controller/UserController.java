package cn.heima.springboot.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cn.heima.springboot.pojo.User;
import cn.heima.springboot.service.UserService;

@RestController
@RequestMapping("user")
public class UserController {

	@Autowired
	private UserService userService;

	@RequestMapping("list")
	public List<User> queryUserAll() {
		List<User> list = userService.findAll();
		return list;
	}
}
