package com.lemon.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lemon.common.ApiListVO;
import com.lemon.common.ApiRunResult;
import com.lemon.common.ApiVO;
import com.lemon.mapper.ApiMapper;
import com.lemon.pojo.Api;
import com.lemon.pojo.ApiRequestParam;
import com.lemon.service.ApiService;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author samlin
 * @since 2020-02-18
 */
@Service
public class ApiServiceImpl extends ServiceImpl<ApiMapper, Api> implements ApiService {
	@Autowired
	ApiMapper apiMapper;

	public List<ApiListVO> showApiListByProject(Integer projectId) {
		return apiMapper.showApiListByProject(projectId);
	}

	public List<ApiListVO> showApiListByApiClassification(Integer apiClassificationId) {
		return apiMapper.showApiListByApiClassification(apiClassificationId);
	}

	public ApiVO findApiViewVO(Integer apiId) {
		return apiMapper.findApiViewVO(apiId);
	}

	@Override
	public ApiRunResult run(ApiVO apiRunVo) {
		// 远程调用
		RestTemplate restTemplate = new RestTemplate();
		String url = apiRunVo.getHost() + apiRunVo.getUrl();
		String method = apiRunVo.getMethod();
		List<ApiRequestParam> apiRequestParams = apiRunVo.getRequestParams();
		LinkedMultiValueMap<String, String> headers = new LinkedMultiValueMap<String, String>();
		LinkedMultiValueMap<String, String> bodyparams = new LinkedMultiValueMap<String, String>();
		String paramStr = "?";
		String jsonValue = "";
		for (ApiRequestParam apiRequestParam : apiRequestParams) {
			if (apiRequestParam.getType() == 1) {
				paramStr += apiRequestParam.getName() + "=" + apiRequestParam.getValue() + "&";
			} else if (apiRequestParam.getType() == 2) {
				bodyparams.add(apiRequestParam.getName(), apiRequestParam.getValue());
			} else if (apiRequestParam.getType() == 3) {
				headers.add(apiRequestParam.getName(), apiRequestParam.getValue());
			} else if (apiRequestParam.getType() == 4) {
				jsonValue = apiRequestParam.getValue();
			}

		}
		if (!"?".equals(paramStr)) {
			paramStr = paramStr.substring(0, paramStr.lastIndexOf("&"));
		}

		HttpEntity httpEntity = null;
		ResponseEntity response = null;
		ApiRunResult apiRunResult = new ApiRunResult();

		try {
			if ("get".equalsIgnoreCase(method)) {
				httpEntity = new HttpEntity(headers);
				response = restTemplate.exchange(url + paramStr, HttpMethod.GET, httpEntity, String.class);
			} else if ("post".equalsIgnoreCase(method)) {
				if (jsonValue == null || jsonValue == "") {
					httpEntity = new HttpEntity(bodyparams, headers);
					response = restTemplate.exchange(url, HttpMethod.POST, httpEntity, String.class);
				} else {
					httpEntity = new HttpEntity(jsonValue, headers);
					response = restTemplate.exchange(url, HttpMethod.POST, httpEntity, String.class);
					
				}
			}
			apiRunResult.setStatusCode(response.getStatusCodeValue() + "");
			HttpHeaders headsResult = response.getHeaders();
			apiRunResult.setHeaders(JSON.toJSONString(headsResult));
			apiRunResult.setBody(response.getBody().toString());
			return apiRunResult;
		} catch (HttpStatusCodeException e) {
			// 处理异常情况
			apiRunResult.setStatusCode(e.getRawStatusCode() + "");
			apiRunResult.setHeaders(JSON.toJSONString(e.getResponseHeaders()));
			apiRunResult.setBody(e.getResponseBodyAsString());
		}
		return null;
	};

}
