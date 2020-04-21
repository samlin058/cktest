package com.lemon.mapper;

import com.lemon.common.CaseEditVO;
import com.lemon.common.ReportVO;
import com.lemon.pojo.TestReport;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Many;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author samlin
 * @since 2020-02-18
 */
public interface TestReportMapper extends BaseMapper<TestReport> {
    
	@Select("SELECT DISTINCT t1.*, t6.id apiId, t6.method, t6.url url, t3. HOST FROM cases t1 JOIN suite t2 ON t1.suite_id = t2.id JOIN project t3 ON t2.project_id = t3.id JOIN case_param_value t4 ON t1.id = t4.case_id JOIN api_request_param t5 ON t4.api_request_param_id = t5.id JOIN api t6 ON t5.api_id = t6.id WHERE t2.id = #{suiteId}")
	@Results({ 
	    @Result(column = "id", property = "id"),
		@Result(column = "id", property = "requestParams", many = @Many(select = "com.lemon.mapper.ApiRequestParamMapper.findByCaseId")),
	    @Result(column = "id", property = "testRules", many =@Many(select="com.lemon.mapper.TestRuleMapper.findByCases"))
	    })
	public List<CaseEditVO> findCaseEditVosUnderSuite(Integer suiteId); 
	
	@Delete("DELETE FROM test_report where case_id in (SELECT case_id from suite where id =#{suiteId});")
	public void deleteReportBySuiteId(Integer suiteId);
	
	
	@Select("select * from test_report where case_id = #{case_id}")
	public TestReport findByCaseId(Integer caseId);
	
	@Select("select * from test_report where case_id = #{case_id}")
	@Results({
		@Result(column = "id", property = "id"),
		@Result(column = "id", property="caseList" ,many =@Many(select="com.lemon.mapper.CasesMapper.findCasesUnderSuite")),
	})
	public ReportVO getReport(Integer suiteId);
}
