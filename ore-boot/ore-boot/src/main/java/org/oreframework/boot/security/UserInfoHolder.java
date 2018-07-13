package org.oreframework.boot.security;

import java.util.Map;

import org.jasig.cas.client.util.AssertionHolder;
import org.oreframework.boot.security.cas.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;

public class UserInfoHolder {
	
	@Autowired
	private UserCache userCache;
	 	
	public User getUserInfo(String jsessionid) {
		if (null == jsessionid || "".equals(jsessionid)) {
			return new User();
		} else {
			return userCache.gutUserInfo(jsessionid);
		}
	}


	public User putUserInfo(User user) {
		return userCache.putUserInfo(user);
	}

	@Deprecated
	public static UserInfo getUserInfo() {
		UserInfo userInfo = null;
		try {
			userInfo = (UserInfo) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		} catch (Exception e) {
			return null;
		}

		return userInfo;

	}

	@Deprecated
	public static User getUser() {
		User user = new User();
		try {
			Map<String, Object> userInfoMap = (Map<String, Object>) AssertionHolder.getAssertion().getPrincipal()
					.getAttributes();
			String name = AssertionHolder.getAssertion().getPrincipal().getName();
			user.setRealname(name);
			user.setId(Integer.parseInt(userInfoMap.get("id").toString()));
		} catch (Exception e) {
			return null;
		}
		return user;
	}

}
