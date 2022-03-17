package io.oicp.yorick61c.hospital_system.pojo.dto;


import lombok.Data;

@Data
public class TempTableDto {
    private Integer userId;
    private String hospitalName;
    private Integer otherHospitalUserId;
    private String otherHospitalName;
}
