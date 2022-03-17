package io.oicp.yorick61c.hospital_system.service;

import io.oicp.yorick61c.hospital_system.pojo.BioFeatureItem;
import io.oicp.yorick61c.hospital_system.pojo.CBFIMapping;
import io.oicp.yorick61c.hospital_system.pojo.CombinedBioFeatureItem;
import io.oicp.yorick61c.hospital_system.pojo.Value;
import io.oicp.yorick61c.hospital_system.pojo.dto.AddCombinedItemDto;
import io.oicp.yorick61c.hospital_system.pojo.dto.CBFIDto;

import java.util.List;
import java.util.Map;

public interface BioFeatureService {
    List<BioFeatureItem> getBioFeatureNameList();

    int saveBioFeatureValue(Value value);

    List<CombinedBioFeatureItem> getCombinedBioFeatureNameList();

    Map<String, Object> getEchartsValue(CBFIDto cbfiDto);

    List<BioFeatureItem> getBioFeatureList();

    int saveItem(BioFeatureItem item);

    int deleteBFI(BioFeatureItem item);

    List<CBFIMapping> getCBFIList();

    int deleteCBFI(CBFIMapping item);

    int saveCombinedItem(AddCombinedItemDto dto);

    List<Value> getValueListByHospitalName(String hospitalName);

    int deleteValue(Value value);
}
