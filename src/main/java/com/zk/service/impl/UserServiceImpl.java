package com.zk.service.impl;

import com.zk.entity.User;
import com.zk.service.UserService;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by zk on 18/7/10.
 *
 * 加入缓存的作用:不用自己写redis的配置类.通过注解缓存自动帮我们对redis数据库进行操作.
 *
 长度： @CachePut(value = "user", key = "#user.id",condition = "#user.username.length() < 10") 只缓存用户名长度少于10的数据
 大小： @Cacheable(value = "user", key = "#id",condition = "#id < 10") 只缓存ID小于10的数据
 组合： @Cacheable(value="user",key="#user.username.concat(##user.password)")
 提前操作： @CacheEvict(value="user",allEntries=true,beforeInvocation=true) 加上beforeInvocation=true后，不管内部是否报错，缓存都将被清除，默认情况为false

 @Cacheable(根据方法的请求参数对其结果进行缓存)
 key： 缓存的 key，可以为空，如果指定要按照 SpEL 表达式编写，如果不指定，则缺省按照方法的所有参数进行组合（如：@Cacheable(value="user",key="#userName")）
 value： 缓存的名称，必须指定至少一个（如：@Cacheable(value="user") 或者 @Cacheable(value={"user1","use2"})）
 condition： 缓存的条件，可以为空，使用 SpEL 编写，返回 true 或者 false，只有为 true 才进行缓存（如：@Cacheable(value = "user", key = "#id",condition = "#id < 10")）

 @CachePut(根据方法的请求参数对其结果进行缓存，和 @Cacheable 不同的是，它每次都会触发真实方法的调用)
 key： 同上
 value： 同上
 condition： 同上

 @CachEvict(根据条件对缓存进行清空)
 key： 同上
 value： 同上
 condition： 同上
 allEntries： 是否清空所有缓存内容，缺省为 false，如果指定为 true，则方法调用后将立即清空所有缓存（如：@CacheEvict(value = "user", key = "#id", allEntries = true)）
 beforeInvocation： 是否在方法执行前就清空，缺省为 false，如果指定为 true，则在方法还没有执行的时候就清空缓存，缺省情况下，如果方法执行抛出异常，则不会清空缓存（如：@CacheEvict(value = "user", key = "#id", beforeInvocation = true)）
 */
@Service
public class UserServiceImpl implements UserService{

    private static final Map<Long, User> DATABASES = new HashMap<>();

    static {
        DATABASES.put(1L, new User(1L, "u1", "p1"));
        DATABASES.put(2L, new User(2L, "u2", "p2"));
        DATABASES.put(3L, new User(3L, "u3", "p3"));
    }

    @CachePut(value = "user", key = "#user.id", condition = "#user.username.length() < 10")
    @Override
    public User saveOrUpdate(User user) {
        DATABASES.put(user.getId(), user);
        System.out.println("进入saveOrUpdate方法");
        return user;
    }



    @Cacheable(value = "user", key = "#id")
    @Override
    public User get(Long id) {
        System.out.println("进入 get 方法");
        return DATABASES.get(id);
    }

    @CacheEvict(value = "user", key = "#id", allEntries = true, beforeInvocation = true)
    @Override
    public void delete(Long id) {
        DATABASES.remove(id);
        System.out.println("进入 delete 方法");
    }
}
