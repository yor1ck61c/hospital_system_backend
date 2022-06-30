package io.oicp.yorick61c.hospital_system.pojo.vo;

import lombok.Data;

@Data
public class CompareVO {
    private String itemName;
    private String compareObjTypeA;
    private Integer compareObjA;
    private Integer yearA;
    private String hospitalLevelA;
    private String hospitalTypeA;
    private String compareObjTypeB;
    private Integer compareObjB;
    private Integer yearB;
    private String hospitalLevelB;
    private String hospitalTypeB;

}
