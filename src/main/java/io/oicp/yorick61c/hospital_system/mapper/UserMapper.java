package io.oicp.yorick61c.hospital_system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.oicp.yorick61c.hospital_system.pojo.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserMapper extends BaseMapper<User> {
    User findUser(User user);
    User findUserByUsername(String username);

    List<String> findHospitalNameList();
}
