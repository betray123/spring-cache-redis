package com.zk;

import com.zk.entity.User;
import com.zk.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringCacheRedisApplicationTests {

	@Autowired
	private UserService userService;

	@Test
	public void contextLoads() {
		final User user = userService.saveOrUpdate(new User(5L, "u5", "p5"));
		System.out.println("[saveOrUpdate] - [{}]" + user);
		final User user1 = userService.get(5L);
		System.out.println("[get] - [{}]"+user1);
		userService.delete(5L);
	}

}
