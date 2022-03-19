package io.oicp.yorick61c.hospital_system;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.oicp.yorick61c.hospital_system.mapper.BioFeatureMapper;
import io.oicp.yorick61c.hospital_system.mapper.ValueCacheMapper;
import io.oicp.yorick61c.hospital_system.mapper.ValueMapper;
import io.oicp.yorick61c.hospital_system.pojo.Value;
import io.oicp.yorick61c.hospital_system.pojo.ValueCache;
import io.oicp.yorick61c.hospital_system.pojo.dto.CBFIDto;
import io.oicp.yorick61c.hospital_system.service.BioFeatureService;
import io.oicp.yorick61c.hospital_system.utils.TimeUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.BeanUtils;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;

@SpringBootTest
class HospitalSystemApplicationTests {

    @Resource
    private BioFeatureMapper bioFeatureMapper;

    @Resource
    private BioFeatureService bioFeatureService;

    @Resource
    private ValueMapper valueMapper;

    @Resource
    private ValueCacheMapper valueCacheMapper;

    @Test
    void contextLoads() {
    }

    @Test
    void testGetNameList(){
        System.out.println(bioFeatureService.getBioFeatureNameList());
    }

    @Test
    void testCopyBean() {

        List<Value> valueList = valueMapper.selectList(new QueryWrapper<Value>().eq("hospital_name", "沧州市中心医院"));
        System.out.println(valueList);
    }

    @Test
    void testSave() {
        ValueCache cache = new ValueCache();
        cache.setItemName("PACU入室低体温患者数");
        cache.setHospitalName("沧州市人民医院");
        cache.setJune(27);
        cache.setYear(2021);
        // 查询后端看之前有没有存过
        HashMap<String, Object> queryMap = new HashMap<>();
        queryMap.put("item_name", cache.getItemName());
        queryMap.put("hospital_name", cache.getHospitalName());
        queryMap.put("year", cache.getYear());
        Value resValue = valueMapper.selectOne(new QueryWrapper<Value>().allEq(queryMap));

        int res;
        Value value = new Value();
        // 之前没存过
        if (resValue == null){
            BeanUtils.copyProperties(cache, value);
            valueMapper.insert(value);
            // 之前已经存过
        } else {
            value.setValueId(resValue.getValueId());
            if (resValue.getJanuary() == null && cache.getJanuary() != null){
                value.setJanuary(cache.getJanuary());
            }
            if (resValue.getFebruary() == null && cache.getFebruary() != null){
                value.setJanuary(cache.getFebruary());
            }
            if (resValue.getMarch() == null && cache.getMarch() != null){
                value.setJanuary(cache.getMarch());
            }
            if (resValue.getApril() == null && cache.getApril() != null){
                value.setJanuary(cache.getApril());
            }
            if (resValue.getMay() == null && cache.getMay() != null){
                value.setJanuary(cache.getMay());
            }
            if (resValue.getJune() == null && cache.getJune() != null){
                value.setJanuary(cache.getJune());
            }
            if (resValue.getJuly() == null && cache.getJuly() != null){
                value.setJanuary(cache.getJuly());
            }
            if (resValue.getAugust() == null && cache.getAugust() != null){
                value.setJanuary(cache.getAugust());
            }
            if (resValue.getSeptember() == null && cache.getSeptember() != null){
                value.setJanuary(cache.getSeptember());
            }
            if (resValue.getOctober() == null && cache.getOctober() != null){
                value.setJanuary(cache.getOctober());
            }
            if (resValue.getNovember() == null && cache.getNovember() != null){
                value.setJanuary(cache.getNovember());
            }
            if (resValue.getDecember() == null && cache.getDecember() != null){
                value.setJanuary(cache.getDecember());
            }
            valueCacheMapper.deleteById(cache.getValueId());
            value.setSaveTime(TimeUtil.getPresentFormatTimeString());
            System.out.println(value);
            res = valueMapper.myUpdate(value);
            System.out.println(res);
        }
    }
}
