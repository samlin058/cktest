package com.lemon.controller;

import java.util.List;

import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lemon.common.Result;
import com.lemon.pojo.Suite;
import com.lemon.pojo.User;
import com.lemon.service.SuiteService;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author samlin
 * @since 2020-02-18
 */
@RestController
@RequestMapping("/suite")
public class SuiteController {

	@Autowired
	SuiteService suiteService;

	@GetMapping("/listAll")
	public Result findAll(Integer projectId) {
		QueryWrapper queryWrapper = new QueryWrapper();
		queryWrapper.eq("project_id", projectId);
		List<Suite> list = suiteService.list(queryWrapper);
		return new Result("1", list);
	}

	// 根据projectId添加测试套件分类
	@PutMapping("{projectId}")
	public Result add(@PathVariable Integer projectId, Suite suite) {
		suite.setProjectId(projectId);
		User user = (User) SecurityUtils.getSubject().getPrincipal();
		suite.setCreateUser(user.getId());
		suiteService.save(suite);
		return new Result("1", "添加套件分类成功");
	}
}
