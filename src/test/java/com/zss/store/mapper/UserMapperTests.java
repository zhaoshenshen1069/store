package com.zss.store.mapper;

import com.zss.store.entity.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author ：zss
 * @description：TODO
 * @date ：2022/9/28 10:19
 */
/*@RunWith(SpringRunner.class)：注解是一个测试启动器，可以加载SpringBoot测试注解*/
@RunWith(SpringRunner.class)
@SpringBootTest
public class UserMapperTests {
    @Autowired
    private UserMapper userMapper;

    @Test
    public void findByUsername(){
        String username = "Bean";
        User result = userMapper.findByUsername(username);
        System.out.println(result);
    }
}
