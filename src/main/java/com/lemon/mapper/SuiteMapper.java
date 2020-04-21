package com.lemon.mapper;

import com.lemon.pojo.Suite;

import java.util.List;

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
public interface SuiteMapper extends BaseMapper<Suite> {
	
	@Select("SELECT * from suite where project_id=#{projectId};")
	@Results({ @Result(column = "id", property = "id"),
			@Result(column = "id", property = "cases", many = @Many(select = "com.lemon.mapper.CasesMapper.findCasesBySuiteId")) })
	List<Suite> findSuiteAndReleadtedCasesBy(Integer projectId);

}
