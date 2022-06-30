package io.oicp.yorick61c.hospital_system.controller;


import io.oicp.yorick61c.hospital_system.pojo.*;
import io.oicp.yorick61c.hospital_system.pojo.dto.TotalValueDTO;
import io.oicp.yorick61c.hospital_system.pojo.vo.CompareVO;
import io.oicp.yorick61c.hospital_system.pojo.vo.CountVO;
import io.oicp.yorick61c.hospital_system.pojo.vo.RankVO;
import io.oicp.yorick61c.hospital_system.service.DataAnalyzeService;
import io.oicp.yorick61c.hospital_system.utils.ReturnUtil;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/data_analyze")
public class DataAnalyzeController {

    @Resource
    private DataAnalyzeService dataAnalyzeService;

    // 统计单个数据
    @PostMapping("/count_single")
    public String countSingleData(@RequestBody CountVO countVO) {
        Map<String, Object> returnMap = dataAnalyzeService.countSingle(countVO);
        return ReturnUtil.getReturnJsonString(20000, "查询成功", returnMap);
    }

    // 统计组合数据
    @PostMapping("/count_combined")
    public String countCombinedData(@RequestBody CountVO countVO) {
        Map<String, Object> returnMap = dataAnalyzeService.countCombined(countVO);
        return ReturnUtil.getReturnJsonString(20000, "查询成功", returnMap);
    }


    // 比较单个数据
    @PostMapping("/compare_single")
    public String compareSingle(@RequestBody CompareVO compareVO) {
        Map<String, Object> map = dataAnalyzeService.compareSingle(compareVO);
        return ReturnUtil.getReturnJsonString(20000, "查询成功", map);
    }

    // 比较组合数据
    @PostMapping("/compare_combined")
    public String compareCombined(@RequestBody CompareVO compareVO) {
        System.out.println(compareVO);
        Map<String, Object> map = dataAnalyzeService.compareCombined(compareVO);
        return ReturnUtil.getReturnJsonString(20000, "查询成功", map);
    }

    // 对单个数据排行
    @PostMapping("/rank_single")
    public String rankSingle(@RequestBody RankVO rankVO) {
        List<Value> valueList = dataAnalyzeService.rankSingle(rankVO);
        return ReturnUtil.getReturnJsonString(20000, "查询成功", valueList);
    }

    // 对组合数据排行
    @PostMapping("/rank_combined")
    public String rankCombined(@RequestBody RankVO rankVO) {
        List<DecimalValue> decimalValues = dataAnalyzeService.rankCombined(rankVO);
        return ReturnUtil.getReturnJsonString(20000, "查询成功", decimalValues);
    }

    // 获取全部组合指标类型信息
    @GetMapping("/get_combined_type")
    public String getCombinedType() {
        List<Type> typeList = dataAnalyzeService.getCombinedType();
        return ReturnUtil.getReturnJsonString(20000, "查询成功", typeList);
    }

    // 根据组合指标类型id获取组合指标名称列表
    @PostMapping("/get_combined_item_by_type_id")
    public String getCombinedItemByTypeId(@RequestBody Integer typeId) {
        List<CBFIMapping> itemList = dataAnalyzeService.getCombinedItemByTypeId(typeId);
        return ReturnUtil.getReturnJsonString(20000, "查询成功", itemList);
    }

    // 根据类型获取比较对象信息
    @PostMapping("/get_combined_item_compare_obj")
    public String getCombinedItemCompareObj(@RequestBody String type) {
        // 去掉type中的双引号
        type = type.substring(1, type.length() - 1);
        Map<Integer, String> returnMap = dataAnalyzeService.getCombinedItemCompareObjByType(type);
        return ReturnUtil.getReturnJsonString(20000, "查询成功", returnMap);
    }
}
