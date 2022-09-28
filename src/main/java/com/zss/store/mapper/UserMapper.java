package com.zss.store.mapper;

import com.zss.store.entity.User;
import org.apache.ibatis.annotations.Param;

/*处理用户数据操作的持久层接口*/
public interface UserMapper {

    /**
     * 插入用户数据
     * @param user 用户数据
     * @return 受影响的行数
     */
    Integer insert(User user);

    /**
     * 根据用户名查询用户数据
     * @param username 用户名
     * @return 匹配的用户数据，如果没有匹配的数据，则返回null
     */
    User findByUsername(@Param("username") String username);
}
