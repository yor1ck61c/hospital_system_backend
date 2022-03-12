package io.oicp.yorick61c.hospital_system.controller;

import io.oicp.yorick61c.hospital_system.pojo.BioFeatureItem;
import io.oicp.yorick61c.hospital_system.pojo.CombinedBioFeatureItem;
import io.oicp.yorick61c.hospital_system.pojo.Result;
import io.oicp.yorick61c.hospital_system.pojo.Value;
import io.oicp.yorick61c.hospital_system.pojo.dto.CBFIDto;
import io.oicp.yorick61c.hospital_system.service.BioFeatureService;
import io.oicp.yorick61c.hospital_system.utils.JsonUtil;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

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

    @GetMapping("/combined_bio_feature_name_list")
    public String getCombinedBioFeatureNameList(){
        List<CombinedBioFeatureItem> bioFeatureItems = bioFeatureService.getCombinedBioFeatureNameList();
        return getReturnJsonString(20000, "查询成功", bioFeatureItems);
    }

    @PatchMapping("/save")
    public String saveBioFeature(@RequestBody Value value){
        int res = bioFeatureService.saveBioFeature(value);
        if(res == 1)
            return getReturnJsonString(20000, "保存成功!", value);
        else return getReturnJsonString(50001, "保存失败!", null);
    }

    @PostMapping("/combined_bio_feature_data")
    public String getCombinedBioFeatureData(@RequestBody CBFIDto cbfiDto) {
        Map<String, Object> echartsValue = bioFeatureService.getEchartsValue(cbfiDto);

        if (echartsValue.get("numerator") == null || echartsValue.get("denominator") == null){
            return getReturnJsonString(20001, "缺少数据，无法展示。", null);
        } else {
            return getReturnJsonString(20000, "查询成功！", echartsValue);
        }
    }


    public String getReturnJsonString(int code, String msg, Object data){
        Result result = new Result();
        result.setCode(code);
        result.setMsg(msg);
        result.setData(data);
        return JsonUtil.obj2String(result);
    }
}
