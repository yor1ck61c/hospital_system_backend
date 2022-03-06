package io.oicp.yorick61c.hospital_system.pojo;

import lombok.Data;

/*
* 格式化返回类，包含status(状态码), msg(提示信息), data(数据部分, 可有可无)
* */

@Data
public class Result {
    private Integer code;
    private String msg;
    private Object data;

}
