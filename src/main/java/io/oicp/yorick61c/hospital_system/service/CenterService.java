package io.oicp.yorick61c.hospital_system.service;

import io.oicp.yorick61c.hospital_system.pojo.Center;
import io.oicp.yorick61c.hospital_system.pojo.TempList;
import io.oicp.yorick61c.hospital_system.pojo.User;
import io.oicp.yorick61c.hospital_system.pojo.dto.HospitalInfoDto;
import io.oicp.yorick61c.hospital_system.pojo.dto.TempTableDto;

import java.util.List;

public interface CenterService {
    int addCenter(Center center);

    List<Center> getCenterList();

    int deleteCenter(Center center);

    int updateCenter(Center center);

    List<User> getHospitalTableDataByCenterName(Integer centerId);

    int saveTempGrand(TempList tempGrand);

    List<TempTableDto> getTempListTableData(Integer userId);

    int deleteTempAuth(TempTableDto dto);

    Center getViceCenterById(Integer userId);

    List<HospitalInfoDto> getHospitalListByCenterId(Integer centerId);
}
