package com.zss.store.service.impl;

import com.zss.store.entity.User;
import com.zss.store.mapper.UserMapper;
import com.zss.store.service.IUserService;
import com.zss.store.service.ex.InsertException;
import com.zss.store.service.ex.PasswordNotMatchException;
import com.zss.store.service.ex.UserNotFoundException;
import com.zss.store.service.ex.UsernameDuplicateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.util.Date;
import java.util.UUID;

/**
 * @author ：zss
 * @description：TODO
 * @date ：2022/9/28 10:55
 */
@Service
public class UserServiceImpl implements IUserService {
    @Autowired
    private UserMapper userMapper;

    @Override
    public void reg(User user) {
        //根据参数user对象获取注册的用户名
        String username = user.getUsername();
        //调用持久层的User findByUsername(String username)方法，根据用户名查询用户数据
        User result = userMapper.findByUsername(username);
        //判断查询结构是否不为null
        if (result != null){
            //是：表示用户名已被占用，则抛出UsernameDuplicateException异常
            throw new UsernameDuplicateException("用户数据已存在");
        }
        //创建当前时间对象
        Date date = new Date();
        //补全数据：加密后的密码
        String salt = UUID.randomUUID().toString().toUpperCase();
        String md5Password = getMd5Password(user.getPassword(), salt);
        user.setPassword(md5Password);
        //补全数据：盐值
        user.setSalt(salt);
        //补全数据：isDelete(0)
        user.setIsDelete(0);
        //补全数据：四项日志属性
        user.setCreatedUser(username);
        user.setCreatedTime(date);
        user.setModifiedUser(username);
        user.setModifiedTime(date);

        //表示用户名没有被占用，则运行注册
        //调用持久层Integer insert(User user)方法，执行注册并获取返回值(受影响的行数)
        Integer rows = userMapper.insert(user);
        //判断受影响的行数是否不为1
        if (rows != 1){
            //是：插入数据时出现某种错误，则抛出InsertException异常
            throw new InsertException("用户数据已存在");
        }
    }

    /**
     * 执行密码加密
     * @param password 原始密码
     * @param salt 盐值
     * @return 加密后的密文
     */
    private String getMd5Password(String password,String salt){
        /**
         * 加密规则
         * 1、无视原始密码的强度
         * 2、使用UUID作为盐值，在原始密码的左右两次拼接
         * 3、循环加密3次
         */
        for (int i = 0; i < 3; i++) {
            password = DigestUtils.md5DigestAsHex((salt + password + salt).getBytes()).toUpperCase();
        }
        return password;
    }

    @Override
    public User login(String username, String password) {
        //调用userMapper的findByUsername()方法，根据参数username查询用户数据
        User result = userMapper.findByUsername(username);
        //判断查询结果是否为null
        if (result == null){
            //是：抛出UserNotFoundException异常
            throw new UserNotFoundException("用户数据不存在");
        }

        //判断查询结果中的isDelete是否为1
        if (result.getIsDelete() == 1){
            //是：抛出UserNotFoundException
            throw new UserNotFoundException("用户数据不存在");
        }

        //从查询结果中获取盐值
        String salt = result.getSalt();
        //调用getMd5Password()方法，将参数password和salt结合起来进行加密
        String md5Password = getMd5Password(password, salt);
        //判断查询结果中的密码，与以上加密得到的密码是否不一致
        if (!result.getPassword().equals(md5Password)){
            //是：抛出PasswordNotMatchException异常
            throw new PasswordNotMatchException("密码验证失败");
        }

        //创建新的User对象
        User user = new User();
        //将查询结果中的uid、username、avatar封装到新的user对象中
        user.setUid(result.getUid());
        user.setUsername(result.getUsername());
        user.setAvatar(result.getAvatar());
        //返回新的user对象
        return user;
    }
}
