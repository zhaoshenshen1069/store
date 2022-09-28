package com.zss.store.controller;

import com.zss.store.service.ex.InsertException;
import com.zss.store.service.ex.ServiceException;
import com.zss.store.service.ex.UsernameDuplicateException;
import com.zss.store.util.JsonResult;
import org.apache.ibatis.annotations.Insert;
import org.springframework.web.bind.annotation.ExceptionHandler;

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
        } else if (e instanceof InsertException){
            result.setState(5000);
        }
        return result;
    }
}
