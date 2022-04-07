package io.oicp.yorick61c.hospital_system.service;

import io.oicp.yorick61c.hospital_system.pojo.User;
import io.oicp.yorick61c.hospital_system.pojo.dto.UserDto;

import java.util.List;

public interface UserService {

    User login(User user);

    User findUserByUsername(String username);

    int saveUser(UserDto user);

    List<User> findAllUser();

    List<String> getHospitalNameList();

    int updateUser(User user);

    int deleteUserById(Integer userId);
}
