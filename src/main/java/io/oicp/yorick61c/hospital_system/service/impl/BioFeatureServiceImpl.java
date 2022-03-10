package io.oicp.yorick61c.hospital_system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.oicp.yorick61c.hospital_system.mapper.BioFeatureMapper;
import io.oicp.yorick61c.hospital_system.mapper.ValueMapper;
import io.oicp.yorick61c.hospital_system.pojo.BioFeatureItem;
import io.oicp.yorick61c.hospital_system.pojo.Value;
import io.oicp.yorick61c.hospital_system.pojo.dto.ValueDto;
import io.oicp.yorick61c.hospital_system.service.BioFeatureService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;

@Service
public class BioFeatureServiceImpl implements BioFeatureService {

    @Resource
    private BioFeatureMapper bioFeatureMapper;

    @Resource
    private ValueMapper valueMapper;

    @Override
    public List<BioFeatureItem> getBioFeatureNameList() {
        return bioFeatureMapper.selectList(new QueryWrapper<BioFeatureItem>().select("item_name"));
    }

    @Override
    public int saveBioFeature(Value value) {
        // 查询后端看之前有没有存过
        HashMap<String, Object> queryMap = new HashMap<>();
        queryMap.put("item_name", value.getItemName());
        queryMap.put("hospital_name", value.getHospitalName());
        queryMap.put("year", value.getYear());
        Value resValue = valueMapper.selectOne(new QueryWrapper<Value>().allEq(queryMap));

        int res;
        // 第一次存
        if (resValue == null){
            res = valueMapper.insert(value);
            // 之前已经存过
        } else {
            value.setValueId(resValue.getValueId());
            res = valueMapper.myUpdate(value);
        }
        return res;
    }
}
