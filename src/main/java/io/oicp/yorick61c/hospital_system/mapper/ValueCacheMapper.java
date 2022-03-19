package io.oicp.yorick61c.hospital_system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.oicp.yorick61c.hospital_system.pojo.ValueCache;

public interface ValueCacheMapper extends BaseMapper<ValueCache> {
    int myUpdate(ValueCache value);
}
