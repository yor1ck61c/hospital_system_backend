package io.oicp.yorick61c.hospital_system.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("cbfi_bfi_mapping")
public class CBFIMapping {

    @TableId(type = IdType.AUTO)
    private Integer cbfiBfiMappingId;
    private String itemName;
    private String numerator;
    private String denominator;
    private Integer ratio;
    private String code;
}
