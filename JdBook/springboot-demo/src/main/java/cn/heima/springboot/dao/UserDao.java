package cn.heima.springboot.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import cn.heima.springboot.pojo.User;

public interface UserDao extends JpaRepository<User, Long> {

}
