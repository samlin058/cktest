package com.lemon.service;

import com.lemon.common.ApiVO;
import com.lemon.common.CaseEditVO;
import com.lemon.common.CaseListVO;
import com.lemon.pojo.Cases;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author samlin
 * @since 2020-02-18
 */
public interface CasesService extends IService<Cases> {

	public void add(Cases caseVo, ApiVO apiRunVo);

	public List<CaseListVO> showCasesUnderProject(Integer projectId);
	
	public List<CaseListVO> findCasesUnderSuite(Integer suiteId);
	
	CaseEditVO findCaseEditVO(String caseId);

	public void update(CaseEditVO caseEditVO);
}
