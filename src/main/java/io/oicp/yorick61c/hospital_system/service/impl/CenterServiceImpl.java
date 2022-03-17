package io.oicp.yorick61c.hospital_system.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.oicp.yorick61c.hospital_system.mapper.CenterMapper;
import io.oicp.yorick61c.hospital_system.mapper.CenterUserMappingMapper;
import io.oicp.yorick61c.hospital_system.mapper.TempListMapper;
import io.oicp.yorick61c.hospital_system.mapper.UserMapper;
import io.oicp.yorick61c.hospital_system.pojo.Center;
import io.oicp.yorick61c.hospital_system.pojo.CenterUserMapping;
import io.oicp.yorick61c.hospital_system.pojo.TempList;
import io.oicp.yorick61c.hospital_system.pojo.User;
import io.oicp.yorick61c.hospital_system.pojo.dto.TempTableDto;
import io.oicp.yorick61c.hospital_system.service.CenterService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CenterServiceImpl implements CenterService {

    @Resource
    private CenterMapper centerMapper;

    @Resource
    private UserMapper userMapper;

    @Resource
    private TempListMapper tempListMapper;

    @Resource
    private CenterUserMappingMapper centerUserMappingMapper;

    @Override
    public int addCenter(Center center) {
        return centerMapper.insert(center);
    }

    @Override
    public List<Center> getCenterList() {
        return centerMapper.selectList(new QueryWrapper<>());
    }

    @Override
    public int deleteCenter(Center center) {
        return centerMapper.deleteById(center.getCenterId());
    }

    @Override
    public int updateCenter(Center center) {
        return centerMapper.updateById(center);
    }

    @Override
    public List<User> getHospitalTableDataByCenterName(Integer centerId) {
        List<CenterUserMapping> mappings = centerUserMappingMapper.selectList(new QueryWrapper<CenterUserMapping>().eq("center_id", centerId));
        List<User> users = new ArrayList<>();
        for (CenterUserMapping mapping : mappings) {
            users.add(userMapper.selectById(mapping.getUserId()));
        }
        return users;
    }

    @Override
    public int saveTempGrand(TempList tempGrand) {
        return tempListMapper.insert(tempGrand);
    }

    @Override
    public List<TempTableDto> getTempListTableData(Integer userId) {
        List<TempList> tempLists = tempListMapper.selectList(new QueryWrapper<TempList>().eq("user_id", userId));
        List<TempTableDto> tempTableData = new ArrayList<>();
        User originUser = userMapper.selectById(userId);
        User user;
        for (TempList t: tempLists) {
            user = userMapper.selectById(t.getOtherHospitalUserId());
            TempTableDto tempTableDto = new TempTableDto();
            tempTableDto.setUserId(userId);
            tempTableDto.setHospitalName(originUser.getHospitalName());
            tempTableDto.setOtherHospitalName(user.getHospitalName());
            tempTableDto.setOtherHospitalUserId(user.getId());
            tempTableData.add(tempTableDto);
        }
        return tempTableData;
    }

    @Override
    public int deleteTempAuth(TempTableDto dto) {
        Map<String, Object> deleteMap = new HashMap<>();
        deleteMap.put("user_id", dto.getUserId());
        deleteMap.put("other_hospital_user_id", dto.getOtherHospitalUserId());
        return tempListMapper.deleteByMap(deleteMap);
    }

}
