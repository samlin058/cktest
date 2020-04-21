package com.lemon.controller;

import java.util.List;

import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lemon.common.Result;
import com.lemon.pojo.Project;
import com.lemon.pojo.User;
import com.lemon.service.ProjectService;

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
@RequestMapping("/project")
public class ProjectController {

	@Autowired
	private ProjectService projectService;

	@ApiOperation(value = "查询项目", httpMethod = "GET")
	@GetMapping("/toList")
	protected Result toList(Integer userId) {
		Result result = null;
		QueryWrapper queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("create_user", userId);
		List list = projectService.list(queryWrapper);
		result = new Result("1", list, "项目列表");
		return result;
	}

	@PostMapping("/add")
	protected Result add(Project project) {
		Result result = null;
		User user = (User) SecurityUtils.getSubject().getPrincipal();
		project.setCreateUser(user.getId());
        projectService.save(project);
        result = new Result("1", "项目保存成功");
		return result;
	}

	@GetMapping("/{projectId}")
	public Result getById(@PathVariable("projectId") Integer projectId) {
		Result result = null;
		Project project = projectService.getById(projectId);
		result = new Result("1", project, "查询项目");
		return result;
	}
	
	@PutMapping("/{projectId}")
	public Result updateById(@PathVariable("projectId") Integer projectId,Project project) {
		Result result = null;
		project.setId(projectId);
		User user = (User) SecurityUtils.getSubject().getPrincipal();
		project.setCreateUser(user.getId());
		projectService.updateById(project);
		result = new Result("1", project, "更新项目");
		return result;
	}
    
	@DeleteMapping("/{projectId}")
	public Result deleteById(@PathVariable("projectId") Integer projectId,Project project) {
		Result result = null;
		project.setId(projectId);
		User user = (User) SecurityUtils.getSubject().getPrincipal();
		project.setCreateUser(user.getId());
		QueryWrapper queryWrapper = new QueryWrapper(project);
		projectService.remove(queryWrapper);
		result = new Result("1", project, "删除项目");
		return result;
	}
}
