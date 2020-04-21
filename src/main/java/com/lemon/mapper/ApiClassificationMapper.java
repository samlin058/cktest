package com.lemon.mapper;

import com.lemon.common.ApiClassificationVO;
import com.lemon.pojo.ApiClassification;

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
public interface ApiClassificationMapper extends BaseMapper<ApiClassification> {

	// 两表 延迟加载 先查询分类信息（List<Api>），按需加载（既此时查另一张表）
	@Select("select * from api_classification where project_id=#{projectId}")
	@Results({ @Result(column = "id", property = "id"),
			@Result(column = "id", property = "apis", many = @Many(select = "com.lemon.mapper.ApiMapper.findApi")) })
	public List<ApiClassificationVO> getWithApi(Integer projectId);

}
