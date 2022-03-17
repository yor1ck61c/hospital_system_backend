package io.oicp.yorick61c.hospital_system.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("center_user_mapping")
public class CenterUserMapping {
    //设置id字段自增
    @TableId(type = IdType.AUTO)
    private Integer mappingId;
    private Integer userId;
    private Integer centerId;
}
