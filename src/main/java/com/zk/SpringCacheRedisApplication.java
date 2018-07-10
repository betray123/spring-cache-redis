package com.zk;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@EnableCaching  //能够使用缓存相关的注解
@SpringBootApplication
public class SpringCacheRedisApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringCacheRedisApplication.class, args);
	}
}
