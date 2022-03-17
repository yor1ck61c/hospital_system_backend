package io.oicp.yorick61c.hospital_system;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.oicp.yorick61c.hospital_system.mapper.BioFeatureMapper;
import io.oicp.yorick61c.hospital_system.mapper.ValueMapper;
import io.oicp.yorick61c.hospital_system.pojo.Value;
import io.oicp.yorick61c.hospital_system.pojo.dto.CBFIDto;
import io.oicp.yorick61c.hospital_system.service.BioFeatureService;
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
        Value value = new Value();
        value.setItemName("术中");
        value.setHospitalName("沧州市中心医院");
        value.setFebruary(26);
        value.setYear(2000);
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
        System.out.println(res);
    }
}
