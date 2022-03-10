package io.oicp.yorick61c.hospital_system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.oicp.yorick61c.hospital_system.pojo.Value;

public interface ValueMapper extends BaseMapper<Value> {

    int myUpdate(Value value);
}
