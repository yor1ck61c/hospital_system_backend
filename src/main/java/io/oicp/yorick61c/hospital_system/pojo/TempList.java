package io.oicp.yorick61c.hospital_system.pojo;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("temp_list")
public class TempList {

    @TableId(type = IdType.AUTO)
    private Integer tempId;
    private Integer userId;
    private Integer otherHospitalUserId;
}
