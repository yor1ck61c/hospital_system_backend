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

    @Test
    public void getUpperCase() {
        String str = "hello";
        System.out.println(str.toUpperCase());
    }


}
