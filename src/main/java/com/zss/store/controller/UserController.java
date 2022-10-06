package com.zss.store.controller;

import com.mysql.cj.x.protobuf.Mysqlx;
import com.zss.store.entity.User;
import com.zss.store.service.IUserService;
import com.zss.store.service.ex.InsertException;
import com.zss.store.service.ex.UsernameDuplicateException;
import com.zss.store.util.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

/**
 * @author ：zss
 * @description：TODO
 * @date ：2022/9/28 11:51
 */
/*处理用户相关请求的控制器类*/
@RestController
@RequestMapping("users")
public class UserController extends BaseController {
    @Autowired
    private IUserService userService;

    @RequestMapping("reg")
    public JsonResult<Void> reg(User user){
        //创建返回值
        JsonResult<Void> result = new JsonResult<>();
        //调用业务对象执行注册
        userService.reg(user);
        //返回
        return new JsonResult<>(OK);
    }

    @RequestMapping("login")
    public JsonResult<User> login(String username, String password, HttpSession session){
        //调用业务对象的方法执行登录，并获取返回值
        User data = userService.login(username, password);

        //登录成功后，将uid和username存入到HttpSession中
        session.setAttribute("uid",data.getUid());
        session.setAttribute("username",data.getUsername());
        //将以上返回值和状态码OK封装到响应结果中并返回
        return new JsonResult<>(OK,data);
    }

    @RequestMapping("change_password")
    public JsonResult<Void> changePassword(String oldPassword,String newPassword,HttpSession session){
        //调用session.getAttribute("")获取uid和username
        Integer uid = getUidFromSession(session);
        String username = getUsernameFromSession(session);
        //调用业务对象执行修改密码
        userService.changePassword(uid,username,oldPassword,newPassword);
        //返回成功
        return new JsonResult<>(OK);
    }
}
