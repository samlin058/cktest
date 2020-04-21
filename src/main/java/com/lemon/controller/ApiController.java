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
import com.lemon.common.ApiListVO;
import com.lemon.common.ApiRunResult;
import com.lemon.common.ApiVO;
import com.lemon.common.Result;
import com.lemon.handler.StringUtil;
import com.lemon.pojo.Api;
import com.lemon.pojo.ApiClassification;
import com.lemon.pojo.ApiRequestParam;
import com.lemon.pojo.User;
import com.lemon.service.ApiRequestParamService;
import com.lemon.service.ApiService;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author samlin
 * @since 2020-02-18
 */
@RestController
@RequestMapping("/api")
public class ApiController {
	@Autowired
	ApiService apiService;

	@Autowired
	ApiRequestParamService apiRequestParamService;
	
	//根据apiClassificationId，添加接口
		@PutMapping("{apiClassificationId}")
		public Result add(@PathVariable Integer apiClassificationId, Api api) {
			api.setApiClassificationId(apiClassificationId);
			User user = (User) SecurityUtils.getSubject().getPrincipal();
			api.setCreateUser(user.getId());
			apiService.save(api);
			return new Result("1", "添加接口成功");
		}

	@GetMapping("/showApiUnderProject")
	public Result showApiListByProject(Integer projectId) {
		List<ApiListVO> list = apiService.showApiListByProject(projectId);
		Result result = new Result("1", list, "接口信息");
		return result;
	}

	@GetMapping("/showApiUnderApiClassification")
	public Result showApiUnderApiClassification(Integer apiClassificationId) {
		List<ApiListVO> list = apiService.showApiListByApiClassification(apiClassificationId);
		Result result = new Result("1", list, "接口信息");
		return result;
	}

	@GetMapping("/toApiView")
	public Result findApiView(Integer apiId) {
		ApiVO api = apiService.findApiViewVO(apiId);
		return new Result("1", api, "接口信息");
	}

	@PutMapping("/edit")
	public Result toApiEdit(ApiVO apiEdit) {
		// 1、直接根据apiId进行更新api
		apiService.updateById(apiEdit);
		// 2、删掉原来的apiRequestParam
		QueryWrapper queryWrapper = new QueryWrapper();
		queryWrapper.eq("api_id", apiEdit.getId());
		apiRequestParamService.remove(queryWrapper);

		// 3、insert apiRequestParams
		// 将子集添加到apiRequestParam
		List<ApiRequestParam> requestParamsList = apiEdit.getQueryParams();
		List<ApiRequestParam> BodyParamsList = apiEdit.getQueryParams();
		List<ApiRequestParam> HeaderParamsList = apiEdit.getQueryParams();
		List<ApiRequestParam> BodyRawParamsList = apiEdit.getQueryParams();

		apiEdit.getRequestParams().addAll(apiEdit.getQueryParams());
		apiEdit.getRequestParams().addAll(apiEdit.getBodyParams());
		apiEdit.getRequestParams().addAll(apiEdit.getHeaderParams());
		apiEdit.getRequestParams().addAll(apiEdit.getBodyRawParams());
		List<ApiRequestParam> apiRequestParams = apiEdit.getRequestParams();
		// 遍历删掉requestParamsList中属性为null的对象
		for (ApiRequestParam apiRequestParam : apiRequestParams) {
			try {
				if (StringUtil.isAllFieldNull(apiRequestParam)) {
					requestParamsList.remove(apiRequestParam);
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		apiRequestParamService.saveBatch(apiRequestParams);

		Result result = new Result("1", "更新成功");
		return result;
	}

	@RequestMapping("/run")
	public Result run(ApiVO apiRunVo) {
		ApiRunResult apiRunResult = apiService.run(apiRunVo);
		return new Result("1", apiRunResult, "请求成功");
	}

}
