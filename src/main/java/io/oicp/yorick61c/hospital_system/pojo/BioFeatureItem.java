package io.oicp.yorick61c.hospital_system.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("bio_feature_item")
public class BioFeatureItem {

    //设置id字段自增
    @TableId(type = IdType.AUTO)
    private Integer bioFeatureItemId;
    private String itemName;


}
