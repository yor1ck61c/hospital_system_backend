package io.oicp.yorick61c.hospital_system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.oicp.yorick61c.hospital_system.mapper.BioFeatureMapper;
import io.oicp.yorick61c.hospital_system.mapper.CBFIMappingMapper;
import io.oicp.yorick61c.hospital_system.mapper.CombinedBioFeatureMapper;
import io.oicp.yorick61c.hospital_system.mapper.ValueMapper;
import io.oicp.yorick61c.hospital_system.pojo.BioFeatureItem;
import io.oicp.yorick61c.hospital_system.pojo.CBFIMapping;
import io.oicp.yorick61c.hospital_system.pojo.CombinedBioFeatureItem;
import io.oicp.yorick61c.hospital_system.pojo.Value;
import io.oicp.yorick61c.hospital_system.pojo.dto.CBFIDto;
import io.oicp.yorick61c.hospital_system.pojo.vo.ValueVo;
import io.oicp.yorick61c.hospital_system.service.BioFeatureService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class BioFeatureServiceImpl implements BioFeatureService {

    @Resource
    private BioFeatureMapper bioFeatureMapper;

    @Resource
    private CombinedBioFeatureMapper combinedBioFeatureMapper;

    @Resource
    private ValueMapper valueMapper;

    @Resource
    private CBFIMappingMapper cbfiMappingMapper;

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

    @Override
    public List<CombinedBioFeatureItem> getCombinedBioFeatureNameList() {
        return combinedBioFeatureMapper.selectList(new QueryWrapper<CombinedBioFeatureItem>().select("item_name"));
    }

    @Override
    public Map<String, Object> getEchartsValue(CBFIDto cbfiDto) {
        CBFIMapping CBFI = cbfiMappingMapper.selectOne(new QueryWrapper<CBFIMapping>().eq("item_name", cbfiDto.getCombinedBioFeatureName()));
        assert CBFI != null;
        Map<String, Object> getNumeratorHashMap = new HashMap<>();
        Map<String, Object> getDenominatorHashMap = new HashMap<>();
        // 查询条件：item_name, hospital_name, year 返回一条value记录。
        // numerator: 分子
        getNumeratorHashMap.put("item_name", CBFI.getNumerator());
        getNumeratorHashMap.put("hospital_name", cbfiDto.getHospitalName());
        getNumeratorHashMap.put("year", cbfiDto.getYear());
        // denominator: 分母
        getDenominatorHashMap.put("item_name", CBFI.getDenominator());
        getDenominatorHashMap.put("hospital_name", cbfiDto.getHospitalName());
        getDenominatorHashMap.put("year", cbfiDto.getYear());

        // 获取两条value记录
        Value value = valueMapper.selectOne(new QueryWrapper<Value>().allEq(getNumeratorHashMap));
        Value value2 = valueMapper.selectOne(new QueryWrapper<Value>().allEq(getDenominatorHashMap));

        ValueVo nValue = new ValueVo();
        ValueVo dValue = new ValueVo();
        BeanUtils.copyProperties(value, nValue);
        BeanUtils.copyProperties(value2, dValue);
        // 返回给前端：1.生命特征类指标名  2.分子12个月份对应的值 3.分母12个月份对应的值
        Map<String, Object> returnMap = new HashMap<>();
        returnMap.put("item_name", cbfiDto.getCombinedBioFeatureName());
        returnMap.put("numerator", nValue);
        returnMap.put("denominator", dValue);
        return returnMap;

    }
}
