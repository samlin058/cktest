package com.lemon.mapper;

import com.lemon.pojo.TestRule;

import java.util.List;

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
public interface TestRuleMapper extends BaseMapper<TestRule> {
	@Select("select * from test_rule where case_id=#{casesId}")
	public List<TestRule> findByCases(String casesId);
}
