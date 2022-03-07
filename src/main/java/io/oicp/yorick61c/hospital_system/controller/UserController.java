package io.oicp.yorick61c.hospital_system.controller;

import io.oicp.yorick61c.hospital_system.pojo.Result;
import io.oicp.yorick61c.hospital_system.pojo.User;
import io.oicp.yorick61c.hospital_system.service.UserService;
import io.oicp.yorick61c.hospital_system.utils.JsonUtil;
import io.oicp.yorick61c.hospital_system.utils.JwtUtil;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

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
            session.setAttribute("username", user.getUsername());
            result.setCode(20000);
            result.setMsg("登录成功");
            result.setData(token);
        }
        return JsonUtil.obj2String(result);
    }

    @GetMapping("/info")
    public String getUserInfo(@CookieValue("token") String token) {
        //根据token获取user对象全部信息,token是根据用户名加密的
        User user = userService.findUserByUsername(JwtUtil.parse(token).getSubject());
        //将对象信息转为json字符串返回
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
    public String register(@RequestBody User user) {
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

}
