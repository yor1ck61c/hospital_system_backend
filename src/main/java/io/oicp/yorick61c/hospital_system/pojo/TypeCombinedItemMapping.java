package io.oicp.yorick61c.hospital_system.pojo;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("type_combined_item_mapping")
public class TypeCombinedItemMapping {

    @TableId(type = IdType.AUTO)
    private Integer mappingId;
    private Integer combinedItemId;
    private Integer typeId;
}
