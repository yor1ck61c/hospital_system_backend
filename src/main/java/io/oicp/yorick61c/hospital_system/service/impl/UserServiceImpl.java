package io.oicp.yorick61c.hospital_system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.oicp.yorick61c.hospital_system.mapper.CenterUserMapper;
import io.oicp.yorick61c.hospital_system.mapper.UserMapper;
import io.oicp.yorick61c.hospital_system.pojo.CenterUserMapping;
import io.oicp.yorick61c.hospital_system.pojo.User;
import io.oicp.yorick61c.hospital_system.pojo.dto.UserDto;
import io.oicp.yorick61c.hospital_system.service.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Resource
    private UserMapper userMapper;

    @Resource
    private CenterUserMapper centerUserMapper;

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
    @Transactional
    public int saveUser(UserDto userDto) {
        User user = new User();
        BeanUtils.copyProperties(userDto, user);
        userMapper.insert(user);
        user = userMapper.selectOne(new QueryWrapper<User>().eq("username", userDto.getUsername()));
        CenterUserMapping centerUserMapping = new CenterUserMapping();
        centerUserMapping.setCenterId(userDto.getCenterId());
        centerUserMapping.setUserId(user.getId());
        return centerUserMapper.insert(centerUserMapping);
    }

    @Override
    public List<User> findAllUser() {
        return userMapper.selectList(new QueryWrapper<>());
    }

    @Override
    public List<String> getHospitalNameList() {
        return userMapper.findHospitalNameList();
    }

    @Override
    public String updateUser(User user) {
        // 先判断用户名是否重复
        User userByUsername = userMapper.findUserByUsername(user.getUsername());
        // 如果用户名重复且不是同一用户，则提示用户名已存在
        if (userByUsername != null && !userByUsername.getId().equals(user.getId())) {
            return "该用户名已被注册";
        }
        userMapper.updateById(user);
        return "修改成功";
    }

    @Override
    public int deleteUserById(Integer userId) {
        return userMapper.deleteById(userId);
    }
}
