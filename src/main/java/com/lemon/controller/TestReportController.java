package com.lemon.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lemon.common.ReportVO;
import com.lemon.common.Result;
import com.lemon.pojo.TestReport;
import com.lemon.service.TestReportService;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author samlin
 * @since 2020-02-18
 */
@RestController
@RequestMapping("/testReport")
public class TestReportController {

	@Autowired
	TestReportService testReportService;

	@RequestMapping("/run")
	public Result run(Integer suiteId) {
		List<TestReport> testReports = testReportService.run(suiteId);
		return new Result("1", testReports, "测试执行成功");
	}

	@GetMapping("/findCaseRunResult")
	public Result findCaseRunResult(Integer caseId) {
		TestReport testReport = testReportService.findByCaseId(caseId);
		return new Result("1", testReport);
	}
	
	@RequestMapping("/get")
	public Result get(Integer suiteId){
		ReportVO report =testReportService.getReport(suiteId);
		Result result = new Result("1", report);
		return result;
	}
}
