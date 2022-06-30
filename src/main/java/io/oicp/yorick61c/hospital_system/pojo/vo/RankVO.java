package io.oicp.yorick61c.hospital_system.pojo.vo;

import lombok.Data;

@Data
/*
* 根据医院等级获取数据综合
* */
public class RankVO {
    private String hospitalLevel;
    private String hospitalType;
    private Integer centerId;
    private String hospitalName;
    private String itemName;
    private Integer year;
    private String month;
    // 排序次序，按升序还是降序
    private String order;
}
