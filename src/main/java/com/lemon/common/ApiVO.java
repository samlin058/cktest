package com.lemon.common;

import java.util.ArrayList;
import java.util.List;

import com.lemon.pojo.Api;
import com.lemon.pojo.ApiRequestParam;

import lombok.Data;
import lombok.val;

@Data
public class ApiVO  extends Api{
     
	private String createUsername;
	private String host;
	
	private List<ApiRequestParam> requestParams = new ArrayList<ApiRequestParam>();
	private List<ApiRequestParam> queryParams = new ArrayList<ApiRequestParam>();
	private List<ApiRequestParam> bodyParams = new ArrayList<ApiRequestParam>();
	private List<ApiRequestParam> headerParams = new ArrayList<ApiRequestParam>();
	private List<ApiRequestParam> bodyRawParams = new ArrayList<ApiRequestParam>();
}
