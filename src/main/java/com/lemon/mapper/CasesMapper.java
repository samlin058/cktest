package com.lemon.mapper;

import com.lemon.common.CaseEditVO;
import com.lemon.common.CaseListVO;
import com.lemon.pojo.Cases;

import java.util.List;

import org.apache.ibatis.annotations.One;
import org.apache.ibatis.annotations.Many;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author samlin
 * @since 2020-02-18
 */
public interface CasesMapper extends BaseMapper<Cases> {
	@Select("SELECT * FROM cases where suite_id=#{suiteId}")
	public List<Cases> findCasesBySuiteId(Integer suiteId);

	@Select("SELECT DISTINCT t1.*, t6.id apiId, t6.url apiUrl FROM cases t1 JOIN suite t2 ON t1.suite_id = t2.id JOIN project t3 ON t2.project_id = t3.id JOIN case_param_value t4 ON t1.id = t4.case_id JOIN api_request_param t5 ON t4.api_request_param_id = t5.id JOIN api t6 ON t5.api_id = t6.id WHERE t3.id = #{projectId};")
	@Results({ 
	    @Result(column = "id", property = "id"),
	    @Result(column= "id" ,property="testReport",one = @One(select="com.lemon.mapper.TestReportMapper.findByCaseId"))
	    })
	List<CaseListVO> showCasesUnderProject(Integer projectId);

	@Select("SELECT DISTINCT t1.*, t6.id apiId, t6.url apiUrl FROM cases t1 JOIN suite t2 ON t1.suite_id = t2.id JOIN case_param_value t4 ON t1.id = t4.case_id JOIN api_request_param t5 ON t4.api_request_param_id = t5.id JOIN api t6 ON t5.api_id = t6.id WHERE t1.suite_id = #{suiteId};")
	@Results({ 
	    @Result(column = "id", property = "id"),
	    @Result(column= "id" ,property="testReport",one = @One(select="com.lemon.mapper.TestReportMapper.findByCaseId"))
	    })
	List<CaseListVO> findCasesUnderSuite(Integer suiteId);

	@Select("SELECT DISTINCT t1.*, t4.id apiId, t4.url ,t4.method FROM cases t1 JOIN case_param_value t2 ON t2.case_id = t1.id JOIN api_request_param t3 ON t2.api_request_param_id = t3.id JOIN api t4 ON t3.api_id = t4.id WHERE t1.id = #{caseId};")
	@Results({ 
		    @Result(column = "id", property = "id"),
			@Result(column = "id", property = "requestParams", many = @Many(select = "com.lemon.mapper.ApiRequestParamMapper.findByCaseId")),
		    @Result(column = "id", property = "testRules", many =@Many(select="com.lemon.mapper.TestRuleMapper.findByCases"))
		    })
	CaseEditVO findCaseEditVO(String caseId);

}
