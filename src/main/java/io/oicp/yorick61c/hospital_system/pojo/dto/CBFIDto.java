package io.oicp.yorick61c.hospital_system.pojo.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
public class CBFIDto {

    private String combinedBioFeatureName;
    private String hospitalName;
    private Integer year;
}
