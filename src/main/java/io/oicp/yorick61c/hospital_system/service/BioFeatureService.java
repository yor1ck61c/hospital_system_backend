package io.oicp.yorick61c.hospital_system.service;

import io.oicp.yorick61c.hospital_system.pojo.BioFeatureItem;
import io.oicp.yorick61c.hospital_system.pojo.Value;
import io.oicp.yorick61c.hospital_system.pojo.dto.ValueDto;
import org.springframework.stereotype.Service;

import java.util.List;

public interface BioFeatureService {
    List<BioFeatureItem> getBioFeatureNameList();

    int saveBioFeature(Value value);
}
