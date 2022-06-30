package io.oicp.yorick61c.hospital_system.controller;

import io.oicp.yorick61c.hospital_system.pojo.Result;
import io.oicp.yorick61c.hospital_system.pojo.User;
import io.oicp.yorick61c.hospital_system.pojo.dto.UserDto;
import io.oicp.yorick61c.hospital_system.service.UserService;
import io.oicp.yorick61c.hospital_system.utils.JsonUtil;
import io.oicp.yorick61c.hospital_system.utils.JwtUtil;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    private UserService userService;

    @PostMapping("/login")
    public String login(@RequestBody User user, HttpSession session){
        User userByUsername = userService.findUserByUsername(user.getUsername());
        Result result = new Result();
        if (userByUsername == null) {
            result.setCode(30000);
            result.setMsg("该用户不存在");
        } else {
            if (!userByUsername.getPassword().equals(user.getPassword())){
                //密码不正确
                result.setCode(30001);
                result.setMsg("密码错误");
                return JsonUtil.obj2String(result);
            }
            String token = JwtUtil.generateToken(user.getUsername());
            result.setCode(20000);
            result.setMsg("登录成功");
            result.setData(token);
        }
        return JsonUtil.obj2String(result);
    }

    @GetMapping("/info")    // 此处token与前端Cookie中SetToken的键名保持一致。
    public String getUserInfo(@CookieValue("token") String token) {
        // 根据token获取user对象全部信息,token是根据用户名加密的
        User user = userService.findUserByUsername(JwtUtil.parse(token).getSubject());
        // 将对象信息转为json字符串返回
        Result result = new Result();
        assert user != null;
        result.setCode(20000);
        result.setMsg("成功拉取用户信息");
        result.setData(user);
        return JsonUtil.obj2String(result);
    }

    @PostMapping("/logout")
    public String logout() {
        Result result = new Result();
        result.setCode(20000);
        result.setMsg("登出成功，欢迎再次使用");
        return JsonUtil.obj2String(result);
    }

    @PutMapping("/register")
    public String register(@RequestBody UserDto user) {
        Result result = new Result();
        // 先判断用户名是否重复
        User userByUsername = userService.findUserByUsername(user.getUsername());
        if (userByUsername != null){
            // 不为空说明该用户名已被注册
            result.setCode(30001);
            result.setMsg("该用户名已被注册");
        } else {
            // user为空说明该用户名还没被注册过，可以注册
            int insertResult = userService.saveUser(user);
            if (insertResult == 1){
                result.setCode(20000);
                result.setMsg("注册成功");
            } else {
                result.setCode(30000);
                result.setMsg("注册失败");
            }
        }
        result.setData(user);
        return JsonUtil.obj2String(result);
    }

    @GetMapping("/list")
    public String getAccountInfoList() {
        List<User> allUser = userService.findAllUser();
        return getReturnJsonString(20000, "查询成功", allUser);
    }

    @GetMapping("/hospital_list")
    public String generateHospitalNameList() {
        List<String> hospitalNameList = userService.getHospitalNameList();
        return getReturnJsonString(20000, "查询成功", hospitalNameList);
    }

    @PutMapping("/update")
    public String updateUser(@RequestBody User user) {
        String res = userService.updateUser(user);
        if(res.equals("修改成功")) {
            return getReturnJsonString(20000, res, null);
        }
        return getReturnJsonString(20001, res, null);
    }

    @DeleteMapping("/delete")
    public String deleteUserById(@RequestBody Integer userId) {
        int res = userService.deleteUserById(userId);
        if(res == 0) {
            return getReturnJsonString(20001, "删除失败！", null);
        }
        return getReturnJsonString(20000, "删除成功！", null);
    }

    public String getReturnJsonString(int code, String msg, Object data){
        Result result = new Result();
        result.setCode(code);
        result.setMsg(msg);
        result.setData(data);
        return JsonUtil.obj2String(result);
    }
}
