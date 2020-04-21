package com.lemon.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lemon.common.ApiVO;
import com.lemon.common.CaseEditVO;
import com.lemon.common.CaseListVO;
import com.lemon.mapper.CasesMapper;
import com.lemon.pojo.ApiRequestParam;
import com.lemon.pojo.CaseParamValue;
import com.lemon.pojo.Cases;
import com.lemon.service.CaseParamValueService;
import com.lemon.service.CasesService;
import com.lemon.service.TestRuleService;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author samlin
 * @since 2020-02-18
 */
@Service
public class CasesServiceImpl extends ServiceImpl<CasesMapper, Cases> implements CasesService {
	
	@Autowired
	CaseParamValueService caseParamValueService;
	
	@Autowired
	CasesMapper casesMapper;
	
	@Autowired
	TestRuleService  testRuleService;
	
	public void add(Cases caseVo ,ApiVO apiRunVo) {
		//添加到cases
		this.save(caseVo);
		//批量添加到case_param_value
		List<ApiRequestParam> requestParamsList = apiRunVo.getRequestParams();
		List<CaseParamValue> caseParamValues =new ArrayList<CaseParamValue>();
		for (ApiRequestParam apiRequestParam : requestParamsList) {
			CaseParamValue caseParamValue= new CaseParamValue();
			caseParamValue.setCaseId(caseVo.getId());
			caseParamValue.setApiRequestParamId(apiRequestParam.getId());
			caseParamValue.setApiRequestParamValue(apiRequestParam.getValue());
			caseParamValues.add(caseParamValue);
		}
		caseParamValueService.saveBatch(caseParamValues);
	}
	
	public List<CaseListVO> showCasesUnderProject(Integer projectId){
	  return casesMapper.showCasesUnderProject(projectId);
	}
	
	public List<CaseListVO> findCasesUnderSuite(Integer suiteId){
		return casesMapper.findCasesUnderSuite(suiteId);
	}
	
	public CaseEditVO findCaseEditVO(String caseId){
		return casesMapper.findCaseEditVO(caseId);
	}

	@Override
	public void update(CaseEditVO caseEditVO) {
		//此时只能更新case表和case_param_value表 还需要更新test_rule表
		updateById(caseEditVO);
		List<ApiRequestParam> requestParams = caseEditVO.getRequestParams();
		List<CaseParamValue> list = new ArrayList<CaseParamValue>();
		for (ApiRequestParam apiRequestParam : requestParams) {
			CaseParamValue caseParamValue = new CaseParamValue();
			caseParamValue.setId(apiRequestParam.getValueId());
			caseParamValue.setApiRequestParamId(apiRequestParam.getId());
			caseParamValue.setApiRequestParamValue(apiRequestParam.getValue());
			caseParamValue.setCaseId(caseEditVO.getId());
			list.add(caseParamValue);
		}
		
		caseParamValueService.updateBatchById(list);
		//先删除test_rule,再insert
		QueryWrapper queryWrapper = new QueryWrapper();
		queryWrapper.eq("case_id", caseEditVO.getId());
		testRuleService.remove(queryWrapper);
		
		testRuleService.saveBatch(caseEditVO.getTestRules());
		
	};
	 
}
