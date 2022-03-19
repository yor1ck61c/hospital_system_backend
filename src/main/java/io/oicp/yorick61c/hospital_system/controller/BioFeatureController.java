package io.oicp.yorick61c.hospital_system.controller;

import io.oicp.yorick61c.hospital_system.pojo.*;
import io.oicp.yorick61c.hospital_system.pojo.dto.AddCombinedItemDto;
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

    @GetMapping("/single_list")
    public String getBioFeatureList(){
        List<BioFeatureItem> bioFeatureList = bioFeatureService.getBioFeatureList();
        return getReturnJsonString(20000, "查询成功", bioFeatureList);
    }

    @GetMapping("/combined_list")
    public String getCombinedItemTableData() {
        List<CBFIMapping> cbfiMappings = bioFeatureService.getCBFIList();
        return getReturnJsonString(20000, "查询成功", cbfiMappings);
    }

    @GetMapping("/combined_bio_feature_name_list")
    public String getCombinedBioFeatureNameList(){
        List<CombinedBioFeatureItem> bioFeatureItems = bioFeatureService.getCombinedBioFeatureNameList();
        return getReturnJsonString(20000, "查询成功", bioFeatureItems);
    }

    @PatchMapping("/save")
    public String saveBioFeature(@RequestBody ValueCache value){
        int res = bioFeatureService.saveCacheValue(value);
        if(res == 1)
            return getReturnJsonString(20000, "保存成功!", value);
        else return getReturnJsonString(50001, "保存失败!", null);
    }

    @PostMapping("/combined_bio_feature_data")
    public String getCombinedBioFeatureData(@RequestBody CBFIDto cbfiDto) {
        Map<String, Object> echartsValue = bioFeatureService.getEchartsValue(cbfiDto);

        if (echartsValue == null || echartsValue.get("numerator") == null || echartsValue.get("denominator") == null){
            return getReturnJsonString(20001, "缺少数据，无法展示。", null);
        } else {
            return getReturnJsonString(20000, "查询成功！", echartsValue);
        }
    }

    @PostMapping("/insert")
    public String addItem(@RequestBody BioFeatureItem item) {
        int res = bioFeatureService.saveItem(item);
        if(res == 1)
            return getReturnJsonString(20000, "保存成功!", item);
        else return getReturnJsonString(50001, "保存失败!", null);
    }

    @PostMapping("/add_combined")
    public String addCombinedItem(@RequestBody AddCombinedItemDto dto){
        int res = bioFeatureService.saveCombinedItem(dto);
        if(res == 1)
            return getReturnJsonString(20000, "保存成功!", dto);
        else return getReturnJsonString(50001, "保存失败!", null);
    }

    @DeleteMapping("/delete_combined")
    public String deleteCombinedItem(@RequestBody CBFIMapping item) {
        int res = bioFeatureService.deleteCBFI(item);
        if(res == 1)
            return getReturnJsonString(20000, "删除成功!", item);
        else return getReturnJsonString(50001, "删除失败!", null);
    }

    @DeleteMapping("/delete")
    public String deleteItem(@RequestBody BioFeatureItem item) {
        int res = bioFeatureService.deleteBFI(item);
        if(res == 1)
            return getReturnJsonString(20000, "删除成功!", item);
        else return getReturnJsonString(50001, "删除失败!", null);
    }

    @PostMapping("/save_combined_cache")
    public String saveValueCacheByYear(@RequestBody ValueCache value) {
        int res = bioFeatureService.saveCacheValue(value);
        if(res == 1)
            return getReturnJsonString(20000, "保存成功!", value);
        else return getReturnJsonString(50001, "保存失败!", null);
    }

    @PostMapping("/value")
    public String getValueTableData(@RequestBody String hospitalName) {
        List<Value> valueList = bioFeatureService.getValueListByHospitalName(hospitalName);
        return getReturnJsonString(20000, "查询成功", valueList);
    }

    @PostMapping("/value_cache")
    public String getValueCacheTableData(@RequestBody String hospitalName) {
        List<ValueCache> valueList = bioFeatureService.getValueCacheListByHospitalName(hospitalName);
        return getReturnJsonString(20000, "查询成功", valueList);
    }

    @PostMapping("/other")
    public String getOtherHospitalData(@RequestBody Integer userId) {
        List<User> userList = bioFeatureService.getOtherHospitalDataById(userId);
        return getReturnJsonString(20000, "查询成功", userList);
    }

    @DeleteMapping("/value_cache")
    public String deleteValueCache(@RequestBody ValueCache value) {
        int res = bioFeatureService.deleteValueCache(value);
        if(res == 1)
            return getReturnJsonString(20000, "删除成功!", value);
        else return getReturnJsonString(50001, "删除失败!", null);
    }

    @PostMapping("/commit")
    public String commitValueCache(@RequestBody ValueCache cache){
        int res = bioFeatureService.commitValueCache(cache);
        if(res == 1)
            return getReturnJsonString(20000, "提交成功!", cache);
        else return getReturnJsonString(50001, "提交失败!", null);
    }

    @PostMapping("/update_value")
    public String updateValue(@RequestBody Value value) {
        int res = bioFeatureService.updateValue(value);
        if(res == 1)
            return getReturnJsonString(20000, "更新成功!", value);
        else return getReturnJsonString(50001, "更新失败!", null);
    }

    @DeleteMapping("/delete_value")
    public String deleteValue(@RequestBody Value value) {
        int res = bioFeatureService.deleteValue(value);
        if(res == 1)
            return getReturnJsonString(20000, "删除成功!", value);
        else return getReturnJsonString(50001, "删除失败!", null);
    }

    public String getReturnJsonString(int code, String msg, Object data){
        Result result = new Result();
        result.setCode(code);
        result.setMsg(msg);
        result.setData(data);
        return JsonUtil.obj2String(result);
    }


}
