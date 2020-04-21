package com.lemon.common;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.lemon.pojo.TestReport;

import lombok.Data;

@Data
public class ReportVO {
	private Integer id;
	private String name; // 套件名

	private String username;// 测试人
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
	private Date createReportTime;// 生成时间
 
	private int totalCase;// 总用例数
	private int successes;// 成功通过数
	private int failures;// 失败数

	private List<CaseListVO> caseList;

	public int getTotalCase() {
		return caseList.size();
	}

	public int getSuccesses() {
		int count1 = 0;
		int count2 = 0;
		for (CaseListVO caseListVO : caseList) {
			TestReport report = caseListVO.getTestReport();
			if (report != null) {
				if (report.getPassFlag().equals("通过")) {
					count1++;
				} else {
					count2++;
				}
			}
		}
		this.successes = count1;
		this.failures = count2;
		return successes;
	}
	
	public int getFailures() {
		return failures;
		
	}
}
