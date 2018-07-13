package org.oreframework.boot.security;

import org.oreframework.boot.security.cas.User;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;

@CacheConfig(cacheNames = "users")
public class UserCache {

	@Cacheable(key = "#jsessionid")
	public User gutUserInfo(String jsessionid) {
		return new User();
	}

	@CachePut(key = "#user.jsessionid")
	public User putUserInfo(User user) {
		return user;
	}
}
