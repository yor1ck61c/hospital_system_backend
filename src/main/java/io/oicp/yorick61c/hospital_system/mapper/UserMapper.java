package io.oicp.yorick61c.hospital_system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.oicp.yorick61c.hospital_system.pojo.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper extends BaseMapper<User> {
    User findUser(User user);
    User findUserByUsername(String username);
}
