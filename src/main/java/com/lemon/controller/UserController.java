package com.lemon.controller;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lemon.common.Result;
import com.lemon.pojo.User;
import com.lemon.service.UserService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author samlin
 * @since 2020-02-18
 */
@RestController
@RequestMapping("/user")
@Api("用户模块")
public class UserController {

	@Autowired
	private UserService userService;

	// 注册方法
	// @RequestMapping("/register")
	@PostMapping("/register")
	@ApiOperation(value = "注册方法", httpMethod = "POST")
	@CrossOrigin
	public Result register(User user) {
		// 调用业务层方法，插入DB，处理异常（统一）
		userService.save(user);
		Result result = new Result("1", "注册成功");
		return result;
		
	}
	// 账号验重
	@GetMapping("/find")
	@ApiOperation(value = "查询是否已注册", httpMethod = "GET")
	public Result find(String username) {
		Result result = null;
		// 调用业务层方法，查询DB非主键列，统一处理异 常
		QueryWrapper queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("username", username);
		User user = userService.getOne(queryWrapper);
		if (user == null) {
			result = new Result("1", "账号不存在");
		} else {
			result = new Result("0", "账号已存在");
		}
		return result;
	}

	// 用户登陆
	@PostMapping("/login")
	@ApiOperation(value = "登陆方法", httpMethod = "POST")
	public Result login(User user) {
		Result result = null;
		try {
			UsernamePasswordToken token = new UsernamePasswordToken(user.getUsername(), user.getPassword());
			// shiro
			Subject subject = SecurityUtils.getSubject();
			subject.login(token);
			User loginUser = (User) subject.getPrincipal();
			String sessionId = (String) SecurityUtils.getSubject().getSession().getId();
			result = new Result("1", loginUser.getId(), sessionId);
		} catch (AuthenticationException e) {
			if (e instanceof UnknownAccountException) {
				result = new Result("0", "无效的用户名");
			} else {
				result = new Result("0", "密码错误");
			}
			e.printStackTrace();
		}
		return result;
	}

	// 退出登录
	@GetMapping("/logout")
	@ApiOperation(value = "退出方法", httpMethod = "GET")
	public Result logout() {
		Result result = null;
		// 从shiro退出会话
		SecurityUtils.getSubject().logout();
		result = new Result("1", "账号未登录");
		return result;
	}

	// 无认证退出
	@GetMapping("/unauth")
	@ApiOperation(value = "未授权方法", httpMethod = "GET")
	public Result unauth() {
		return new Result("1", "账号未登录");
	}
}
