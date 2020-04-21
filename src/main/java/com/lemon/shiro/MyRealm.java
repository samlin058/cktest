 package com.lemon.shiro;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lemon.pojo.User;
import com.lemon.service.UserService;


public class MyRealm extends AuthorizingRealm{
   
	@Autowired
	UserService userService;
	
	//授权（权限管理）
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	//身份认证
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		String username= token.getPrincipal().toString();
		
		QueryWrapper<User> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("username", username);
		User dbUser= userService.getOne(queryWrapper);
		if (dbUser != null) {
			return new SimpleAuthenticationInfo(dbUser, dbUser.getPassword(), getName());
		}
		return null;
	}

}
