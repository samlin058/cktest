package com.lemon.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONPath;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lemon.common.CaseEditVO;
import com.lemon.common.ReportVO;
import com.lemon.mapper.TestReportMapper;
import com.lemon.pojo.ApiRequestParam;
import com.lemon.pojo.TestReport;
import com.lemon.pojo.TestRule;
import com.lemon.pojo.User;
import com.lemon.service.TestReportService;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author samlin
 * @since 2020-02-18
 */
@Service
public class TestReportServiceImpl extends ServiceImpl<TestReportMapper, TestReport> implements TestReportService {

	@Autowired
	TestReportMapper testReportMapper;

	public List<TestReport> run(Integer suiteId) {
		List<TestReport> reportList = new ArrayList<TestReport>();
		// 1、先根据suiteId查询
		List<CaseEditVO> list = testReportMapper.findCaseEditVosUnderSuite(suiteId);
		for (CaseEditVO caseEditVO : list) {
			// 执行远程调用
			TestReport testReport = runAndGetReport(caseEditVO);
			reportList.add(testReport);
		}
		testReportMapper.deleteReportBySuiteId(suiteId);
		this.saveBatch(reportList);
		return reportList;
	};

	TestReport runAndGetReport(CaseEditVO caseEditVO) {
		TestReport testReport = new TestReport();
		RestTemplate restTemplate = new RestTemplate();
		String url = caseEditVO.getHost() + caseEditVO.getUrl();
		String method = caseEditVO.getMethod();
		List<ApiRequestParam> apiRequestParams = caseEditVO.getRequestParams();
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
					testReport.setRequestBody(JSON.toJSONString(bodyparams));
					response = restTemplate.exchange(url, HttpMethod.POST, httpEntity, String.class);

				}
				testReport.setCaseId(caseEditVO.getId());
				testReport.setRequestUrl(url);
				testReport.setRequestHeaders(JSON.toJSONString(headers));
				testReport.setResponseHeaders(JSON.toJSONString(response.getHeaders()));
				testReport.setResponseBody(response.getBody().toString());
				testReport.setPassFlag(assertByTestRule(response.getBody().toString(), caseEditVO.getTestRules()));
			}

			return null;

		} catch (HttpStatusCodeException e) {
			e.printStackTrace();
		}
		return testReport;
	};

	String assertByTestRule(String responseBody, List<TestRule> testRules) {
		boolean flag = true;
		for (TestRule testRule : testRules) {
			// 取出json要验算的字段值
			Object value = JSONPath.read(responseBody, testRule.getExpression());
			String actul = (String) value;
			String op = testRule.getOperator();
			// 根据不同的验算符进行验算
			if ("=".equals(op)) {
				if (!actul.equals(testRule.getExpected())) {
					flag = false;
				}
			} else {
				if (!actul.contains(testRule.getExpected())) {
					flag = false;
				}
			}
		}

		if (flag == false) {
			return "不通过";
		}

		return "通过";
	}

	@Override
	public TestReport findByCaseId(Integer caseId) {

		return testReportMapper.findByCaseId(caseId);
	}

	@Override
	public ReportVO getReport(Integer suiteId) {
		ReportVO report = testReportMapper.getReport(suiteId);
		User user = (User) SecurityUtils.getSubject().getPrincipal();
		report.setUsername(user.getUsername());
		report.setCreateReportTime(new Date());
		return report;
	};
}
