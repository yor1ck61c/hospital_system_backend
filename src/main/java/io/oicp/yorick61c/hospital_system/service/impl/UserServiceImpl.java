package io.oicp.yorick61c.hospital_system.service.impl;

import io.oicp.yorick61c.hospital_system.mapper.UserMapper;
import io.oicp.yorick61c.hospital_system.pojo.User;
import io.oicp.yorick61c.hospital_system.service.UserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class UserServiceImpl implements UserService {

    @Resource
    private UserMapper userMapper;

    @Override
    public User login(User user) {
        // 根据id或用户名或密码查找用户并返回用户对象。
        return userMapper.findUser(user);
    }

    @Override
    public User findUserByUsername(String username) {
        return userMapper.findUserByUsername(username);
    }

    @Override
    public int saveUser(User user) {
        return userMapper.insert(user);
    }
}
