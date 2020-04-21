package com.lemon.mapper;

import com.lemon.pojo.ApiRequestParam;

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
public interface ApiRequestParamMapper extends BaseMapper<ApiRequestParam> {
	@Select("select * from api_request_param where api_id = #{apiId}")
	public List<ApiRequestParam> findAll(Integer apiId);

	@Select("SELECT DISTINCT t2.*,t1.api_request_param_value value,t1.id valueId FROM case_param_value t1 JOIN api_request_param t2 ON t1.api_request_param_id = t2.id WHERE t1.case_id = #{caseId};")
   public List<ApiRequestParam> findByCaseId(Integer caseId );
}
