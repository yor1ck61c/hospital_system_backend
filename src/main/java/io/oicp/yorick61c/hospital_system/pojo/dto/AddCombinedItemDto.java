package io.oicp.yorick61c.hospital_system.pojo.dto;

import lombok.Data;

@Data
public class AddCombinedItemDto {
    private String itemName;
    private String numerator;
    private String denominator;
    private String definition;
    private String description;
    private String meaning;
}
