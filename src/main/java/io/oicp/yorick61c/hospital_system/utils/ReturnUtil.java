package io.oicp.yorick61c.hospital_system.utils;

import io.oicp.yorick61c.hospital_system.pojo.Result;

public class ReturnUtil {
    public static String getReturnJsonString(int code, String msg, Object data){
        Result result = new Result();
        result.setCode(code);
        result.setMsg(msg);
        result.setData(data);
        return JsonUtil.obj2String(result);
    }
}
