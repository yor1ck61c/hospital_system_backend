package io.oicp.yorick61c.hospital_system.service;

import io.oicp.yorick61c.hospital_system.pojo.BioFeatureItem;
import io.oicp.yorick61c.hospital_system.pojo.CombinedBioFeatureItem;
import io.oicp.yorick61c.hospital_system.pojo.Value;
import io.oicp.yorick61c.hospital_system.pojo.dto.CBFIDto;

import java.util.List;
import java.util.Map;

public interface BioFeatureService {
    List<BioFeatureItem> getBioFeatureNameList();

    int saveBioFeature(Value value);

    List<CombinedBioFeatureItem> getCombinedBioFeatureNameList();

    Map<String, Object> getEchartsValue(CBFIDto cbfiDto);
}
