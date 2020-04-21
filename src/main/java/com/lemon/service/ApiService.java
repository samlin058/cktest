package com.lemon.service;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lemon.common.ApiListVO;
import com.lemon.common.ApiRunResult;
import com.lemon.common.ApiVO;
import com.lemon.pojo.Api;

/**
 * <p>
 *  服务类
 * </p>
 * @author samlin
 * @since 2020-02-18
 */
public interface ApiService extends IService<Api> {
	
	public List<ApiListVO> showApiListByProject(Integer projectId);
	
	public List<ApiListVO> showApiListByApiClassification(Integer apiClassificationId);
	
	public ApiVO findApiViewVO(Integer apiId);

	public ApiRunResult run(ApiVO apiRunVo);
}
