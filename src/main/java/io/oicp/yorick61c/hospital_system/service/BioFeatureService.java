package io.oicp.yorick61c.hospital_system.service;

import io.oicp.yorick61c.hospital_system.pojo.*;
import io.oicp.yorick61c.hospital_system.pojo.dto.AddCombinedItemDto;
import io.oicp.yorick61c.hospital_system.pojo.dto.CBFIDto;

import java.util.List;
import java.util.Map;

public interface BioFeatureService {
    List<BioFeatureItem> getBioFeatureNameList();

    List<CombinedBioFeatureItem> getCombinedBioFeatureNameList();

    Map<String, Object> getEchartsValue(CBFIDto cbfiDto);

    List<BioFeatureItem> getBioFeatureList();

    int saveItem(BioFeatureItem item);

    int deleteBFI(BioFeatureItem item);

    List<CBFIMapping> getCBFIList();

    int deleteCBFI(CBFIMapping item);

    int saveCombinedItem(AddCombinedItemDto dto);

    List<Value> getValueListByHospitalName(String hospitalName);

    int deleteValueCache(ValueCache value);

    List<User> getOtherHospitalDataById(Integer userId);

    int saveCacheValue(ValueCache value);

    List<ValueCache> getValueCacheListByHospitalName(String hospitalName);

    int commitValueCache(ValueCache cache);

    int updateValue(Value value);

    int deleteValue(Value value);

    Map<String, Object> findSingleItemValue(Value value);
}
