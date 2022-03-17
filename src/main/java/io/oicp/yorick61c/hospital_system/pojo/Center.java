package io.oicp.yorick61c.hospital_system.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/*
* 格式化返回类，包含status(状态码), msg(提示信息), data(数据部分, 可有可无)
* */

@Data
@TableName("center")
public class Center {
    //设置id字段自增
    @TableId(type = IdType.AUTO)
    private Integer centerId;
    private String centerName;
    private Integer centerType;

}
