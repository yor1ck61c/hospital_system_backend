package io.oicp.yorick61c.hospital_system.controller;


import io.oicp.yorick61c.hospital_system.pojo.Center;
import io.oicp.yorick61c.hospital_system.pojo.Result;
import io.oicp.yorick61c.hospital_system.pojo.TempList;
import io.oicp.yorick61c.hospital_system.pojo.User;
import io.oicp.yorick61c.hospital_system.pojo.dto.TempTableDto;
import io.oicp.yorick61c.hospital_system.service.CenterService;
import io.oicp.yorick61c.hospital_system.utils.JsonUtil;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/center")
public class CenterController {

    @Resource
    private CenterService centerService;

    @PostMapping("/add")
    public String addCenter(@RequestBody Center center) {
        int res = centerService.addCenter(center);
        if(res == 0) {
            return getReturnJsonString(20001, "新增失败！", null);
        }
        return getReturnJsonString(20000, "新增成功！", null);
    }

    @PostMapping("/grand")
    public String saveTempGrand(@RequestBody TempList tempGrand) {
        int res = centerService.saveTempGrand(tempGrand);
        if(res == 0) {
            return getReturnJsonString(20001, "授予临时权限失败！", null);
        }
        return getReturnJsonString(20000, "授予临时权限成功！", null);
    }

    @PostMapping("/update")
    public String updateCenter(@RequestBody Center center) {
        int res = centerService.updateCenter(center);
        if(res == 0) {
            return getReturnJsonString(20001, "更新失败！", null);
        }
        return getReturnJsonString(20000, "更新成功！", null);
    }

    @GetMapping("/list")
    public String getCenterTableData() {
        List<Center> centerList = centerService.getCenterList();
        return getReturnJsonString(20000, "查询成功！", centerList);
    }

    @DeleteMapping("/delete")
    public String deleteCenter(@RequestBody Center center){
        int res = centerService.deleteCenter(center);
        if(res == 0) {
            return getReturnJsonString(20001, "删除失败！", null);
        }
        return getReturnJsonString(20000, "删除成功！", null);
    }

    @DeleteMapping("/delete_temp")
    public String deleteTempAuth(@RequestBody TempTableDto dto){
        int res = centerService.deleteTempAuth(dto);
        if(res == 0) {
            return getReturnJsonString(20001, "删除失败！", null);
        }
        return getReturnJsonString(20000, "删除成功！", null);
    }

    @PostMapping("/list_by_id")
    public String generateHospitalTableData(@RequestBody Integer centerId) {
        List<User> users = centerService.getHospitalTableDataByCenterName(centerId);
        return getReturnJsonString(20000, "查询成功", users);
    }

    @PostMapping("/temp_list")
    public String getTempListTableData(@RequestBody Integer userId) {
        List<TempTableDto> tempListTableData = centerService.getTempListTableData(userId);
        return getReturnJsonString(20000, "查询成功", tempListTableData);
    }

    @PostMapping("/vice_center")
    public String getViceCenterInfoById(@RequestBody Integer userId) {
        Center center = centerService.getViceCenterById(userId);
        return getReturnJsonString(20000, "查询成功", center);
    }

    public String getReturnJsonString(int code, String msg, Object data){
        Result result = new Result();
        result.setCode(code);
        result.setMsg(msg);
        result.setData(data);
        return JsonUtil.obj2String(result);
    }
}
