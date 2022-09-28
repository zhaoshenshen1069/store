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
}
