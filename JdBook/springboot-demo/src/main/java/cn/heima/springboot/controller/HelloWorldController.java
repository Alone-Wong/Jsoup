package cn.heima.springboot.controller;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloWorldController {
/*
	@Value("${name}")
	private String name;
	
	@Value("${url}")
	private String url;
	
	@Resource
	private Environment env;
	
	
	@RequestMapping("/index111")
	public String getIndex(){
		System.out.println(env.getProperty("name"));
		System.out.println(env.getProperty("url"));
		return "hello world!nnn";
		
	}*/
}
