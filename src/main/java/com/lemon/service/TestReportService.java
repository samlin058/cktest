package com.lemon.service;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lemon.common.ReportVO;
import com.lemon.pojo.TestReport;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author samlin
 * @since 2020-02-18
 */
public interface TestReportService extends IService<TestReport> {
	public List<TestReport> run(Integer suiteId);

	public TestReport findByCaseId(Integer caseId);

	public ReportVO getReport(Integer suiteId); 
}
