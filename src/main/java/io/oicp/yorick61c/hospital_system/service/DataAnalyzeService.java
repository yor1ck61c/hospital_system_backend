package io.oicp.yorick61c.hospital_system.service;

import io.oicp.yorick61c.hospital_system.pojo.*;
import io.oicp.yorick61c.hospital_system.pojo.dto.TotalValueDTO;
import io.oicp.yorick61c.hospital_system.pojo.vo.CompareVO;
import io.oicp.yorick61c.hospital_system.pojo.vo.CountVO;
import io.oicp.yorick61c.hospital_system.pojo.vo.RankVO;

import java.util.List;
import java.util.Map;

public interface DataAnalyzeService {

    Map<String, Object> countSingle(CountVO countVO);

    Map<String, Object> countCombined(CountVO countVO);

    Map<String, Object> compareSingle(CompareVO compareVO);

    Map<String, Object> compareCombined(CompareVO compareVO);

    List<Value> rankSingle(RankVO rankVO);

    List<DecimalValue> rankCombined(RankVO rankVO);

    List<Type> getCombinedType();

    List<CBFIMapping> getCombinedItemByTypeId(Integer typeId);

    Map<Integer, String> getCombinedItemCompareObjByType(String type);
}
