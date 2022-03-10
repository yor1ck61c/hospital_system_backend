package io.oicp.yorick61c.hospital_system.controller;

import io.oicp.yorick61c.hospital_system.pojo.BioFeatureItem;
import io.oicp.yorick61c.hospital_system.pojo.Result;
import io.oicp.yorick61c.hospital_system.pojo.Value;
import io.oicp.yorick61c.hospital_system.pojo.dto.ValueDto;
import io.oicp.yorick61c.hospital_system.service.BioFeatureService;
import io.oicp.yorick61c.hospital_system.utils.JsonUtil;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/bio_feature")
public class BioFeatureController {

    @Resource
    private BioFeatureService bioFeatureService;

    @GetMapping("/name_list")
    public String getBioFeatureNameList(){
        List<BioFeatureItem> bioFeatureNameList = bioFeatureService.getBioFeatureNameList();

        return getReturnJsonString(20000, "查询成功", bioFeatureNameList);
    }

    @PatchMapping("/save")
    public String saveBioFeature(@RequestBody Value value){
        int res = bioFeatureService.saveBioFeature(value);
        if(res == 1)
            return getReturnJsonString(20000, "保存成功!", value);
        else return getReturnJsonString(50001, "保存失败!", null);
    }

    public String getReturnJsonString(int code, String msg, Object data){
        Result result = new Result();
        result.setCode(code);
        result.setMsg(msg);
        result.setData(data);
        return JsonUtil.obj2String(result);
    }
}
