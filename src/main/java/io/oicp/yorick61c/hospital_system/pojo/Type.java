package io.oicp.yorick61c.hospital_system.pojo;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@TableName("type")
@Data
public class Type {
    @TableId(type = IdType.AUTO)
    private Integer typeId;
    private String typeName;
}
