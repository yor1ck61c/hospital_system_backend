package io.oicp.yorick61c.hospital_system;

import io.oicp.yorick61c.hospital_system.pojo.DecimalValue;
import io.oicp.yorick61c.hospital_system.pojo.Value;
import io.oicp.yorick61c.hospital_system.pojo.vo.CompareVO;
import io.oicp.yorick61c.hospital_system.pojo.vo.CountVO;
import io.oicp.yorick61c.hospital_system.pojo.vo.RankVO;
import io.oicp.yorick61c.hospital_system.service.DataAnalyzeService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.List;

@SpringBootTest
class HospitalSystemApplicationTests {

    @Resource
    private DataAnalyzeService dataAnalyzeService;

    @Test
    public void getUpperCase() {
        String str = "hello";
        System.out.println(str.toUpperCase());
   }

}
