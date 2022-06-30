package io.oicp.yorick61c.hospital_system.pojo.vo;

import lombok.Data;

@Data
/*
* 根据医院等级获取数据综合
* */
public class CountVO {
    private String hospitalLevel;
    private String hospitalType;
    private Integer centerId;
    private String hospitalName;
    private String itemName;
    private Integer year;
    private Integer month;
}
