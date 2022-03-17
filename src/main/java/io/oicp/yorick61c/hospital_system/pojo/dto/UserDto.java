package io.oicp.yorick61c.hospital_system.pojo.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("user")
public class UserDto {

    //设置id字段自增
    @TableId(type = IdType.AUTO)
    private Integer id;
    private Integer centerId;
    private String username;
    private String password;
    private String role;
    private String avatar;
    private String introduction;
    private String hospitalName;
    private String hospitalType;
    private String hospitalLevel;
    private String centerType;


}
