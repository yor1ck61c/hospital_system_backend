package io.oicp.yorick61c.hospital_system.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
public class DecimalValue {
    @TableId(type = IdType.AUTO)
    private Integer valueId;
    private String itemName;
    private String hospitalName;
    private Integer year;
    private Double january;
    private Double february;
    private Double march;
    private Double april;
    private Double may;
    private Double june;
    private Double july;
    private Double august;
    private Double september;
    private Double october;
    private Double november;
    private Double december;
    private Integer hospitalNum;
    private String countTime;
    private Integer noDataHospitalNum;
}
