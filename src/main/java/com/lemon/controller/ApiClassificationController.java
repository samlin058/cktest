package com.lemon.controller;

import java.util.List;

import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lemon.common.ApiClassificationVO;
import com.lemon.common.Result;
import com.lemon.pojo.ApiClassification;
import com.lemon.pojo.Suite;
import com.lemon.pojo.User;
import com.lemon.service.ApiClassificationService;
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
@RequestMapping("/apiClassification")
public class ApiClassificationController {
	@Autowired
	ApiClassificationService apiClassificationService;
	@Autowired
	SuiteService suiteService;

	@GetMapping("/toIndex")
	public Result getWithApi(Integer projectId, Integer tab) {
		Result result = null;
		if (tab == 1) {
			List<ApiClassificationVO> list = apiClassificationService.getWithApi(projectId);
			result = new Result("1", list, "查询分类同时也延迟加载api");
		} else if (tab == 2) {
			List<Suite> list2 = suiteService.findSuiteAndReleadtedCasesBy(projectId);
			result = new Result("1", list2);
		}
		return result;
	}

	// 根据projectId，单表查询分类信息
	@GetMapping("/findAll")
	public Result findAll(Integer projectId) {
		QueryWrapper queryWrapper = new QueryWrapper();
		queryWrapper.eq("project_id", projectId);
		List<ApiClassification> list = apiClassificationService.list(queryWrapper);
		return new Result("1", list);
	}
	
	//根据projectid，添加接口分类
	@PutMapping("{projectId}")
	public Result add(@PathVariable Integer projectId, ApiClassification apiClassification) {
		apiClassification.setProjectId(projectId);
		User user = (User) SecurityUtils.getSubject().getPrincipal();
		apiClassification.setCreateUser(user.getId());
		apiClassificationService.save(apiClassification);
		return new Result("1", "添加接口分类成功");
	}

	@GetMapping("/add2")
	public Result add2(@RequestBody String jsonString) {
		ApiClassification apiClassification = JSON.parseObject(jsonString, ApiClassification.class);
		apiClassificationService.save(apiClassification);
		return new Result("1", "新增分类成功");
	}
}
