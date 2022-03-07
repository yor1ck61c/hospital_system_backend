package io.oicp.yorick61c.hospital_system.service;

import io.oicp.yorick61c.hospital_system.pojo.User;

public interface UserService {

    User login(User user);

    User findUserByUsername(String username);

    int saveUser(User user);
}
