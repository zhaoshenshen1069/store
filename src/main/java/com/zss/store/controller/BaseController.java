package com.zss.store.controller;

import com.zss.store.service.ex.*;
import com.zss.store.util.JsonResult;
import org.apache.ibatis.annotations.Insert;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpSession;

/**
 * @author ：zss
 * @description：TODO
 * @date ：2022/9/28 16:17
 */
/*控制类的基类*/
public class BaseController {
    /*操作成功的状态码*/
    public static final int OK = 200;

    /*@ExceptionHandler用于统一处理抛出的异常*/
    @ExceptionHandler(ServiceException.class)
    public JsonResult<Void> handleException(Throwable e){
        JsonResult<Void> result = new JsonResult<>(e);
        if (e instanceof UsernameDuplicateException){
            result.setState(4000);
        } else if (e instanceof UserNotFoundException){
            result.setState(4001);
        } else if (e instanceof PasswordNotMatchException){
            result.setState(4002);
        } else if (e instanceof InsertException){
            result.setState(5000);
        } else if (e instanceof UpdateException){
            result.setState(5001);
        }
        return result;
    }

    /**
     * 从HttpSession对象中获取uid
     * @param session HttpSession对象
     * @return 当前登录的用户的id
     */
    protected final Integer getUidFromSession(HttpSession session){
        return Integer.valueOf(session.getAttribute("uid").toString());
    }

    /**
     * 从HttpSession对象中获取用户名
     * @param session HttpSession对象
     * @return 当前登录的用户名
     */
    protected final String getUsernameFromSession(HttpSession session){
        return session.getAttribute("username").toString();
    }
}
