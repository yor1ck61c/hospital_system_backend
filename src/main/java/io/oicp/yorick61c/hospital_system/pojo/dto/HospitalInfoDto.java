package io.oicp.yorick61c.hospital_system.pojo.dto;

import lombok.Data;

@Data
public class HospitalInfoDto {
    private Integer id;
    private String hospitalName;
    private String hospitalType;
    private String hospitalLevel;
}
