package com.lemon.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lemon.common.ApiVO;
import com.lemon.common.CaseEditVO;
import com.lemon.common.CaseListVO;
import com.lemon.common.Result;
import com.lemon.pojo.Cases;
import com.lemon.service.CaseParamValueService;
import com.lemon.service.CasesService;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author samlin
 * @since 2020-02-18
 */
@RestController
@RequestMapping("/cases")
public class CasesController {

	@Autowired
	CasesService casesService;
	@Autowired
	CaseParamValueService caseParamValueService;

	@GetMapping("/showCasesUnderProject")
	public Result showCasesUnderProject(Integer projectId) {
		List<CaseListVO> list = casesService.showCasesUnderProject(projectId);
		return new Result("1", list);
	}

	@GetMapping("/findCasesUnderSuite")
	public Result findCasesUnderSuite(Integer suiteId) {
		List<CaseListVO> list = casesService.findCasesUnderSuite(suiteId);
		return new Result("1", list);
	}

	@PostMapping("/add")
	public Result add(Cases caseVo, ApiVO apiRunVO) {
		// 添加到cases
		casesService.add(caseVo, apiRunVO);
		return new Result("1", "添加到用例集成功");
	}

	@GetMapping("/toCaseEdit")
	public Result toCaseEdit(String caseId) {
		CaseEditVO caseEditVO = casesService.findCaseEditVO(caseId);
		return new Result("1", caseEditVO);
	}
	
	@PutMapping("/update")
	public Result updateCase(CaseEditVO caseEditVO) {
		casesService.update(caseEditVO);
		return new Result("1", "更新用例成功");
	}
	

}
