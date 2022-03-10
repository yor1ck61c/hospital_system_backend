package io.oicp.yorick61c.hospital_system.pojo.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("value")
public class ValueDto {
    @TableId(type = IdType.AUTO)
    private Integer valueId;
    private String itemName;
    private String hospitalName;
    private String month;
    private Integer value;
    private Integer year;
    private Integer january;
    private Integer february;
    private Integer march;
    private Integer april;
    private Integer may;
    private Integer june;
    private Integer july;
    private Integer august;
    private Integer september;
    private Integer october;
    private Integer november;
    private Integer december;
}
