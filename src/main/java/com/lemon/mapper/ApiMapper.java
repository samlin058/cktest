package com.lemon.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Many;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lemon.common.ApiClassificationVO;
import com.lemon.common.ApiListVO;
import com.lemon.common.ApiVO;
import com.lemon.pojo.Api;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author samlin
 * @since 2020-02-18
 */
public interface ApiMapper extends BaseMapper<Api> {
	@Select("select * from api where api_classification_id = #{apiClassificationId} ")
	public List<Api> findApi(Integer apiClassificationId);

	@Select("SELECT t1.*, t2.NAME classificationName FROM api t1, api_classification t2 WHERE t1.api_classification_id = t2.id AND t2.project_id = #{projectId};")
	public List<ApiListVO> showApiListByProject(Integer projectId);

	@Select("SELECT t1.*,t2.name classificationName from api t1,api_classification t2 where t2.id=t1.api_classification_id and t2.id = #{apiClassificationId};")
	public List<ApiListVO> showApiListByApiClassification(Integer apiClassificationId);
	
	@Select("SELECT t1.*, t2.username createUsername FROM api t1, USER t2 WHERE t1.create_user = t2.id and t1.id=#{apiId};")
	@Results({
		@Result(column="id",property="id"),
		@Result(column="id",property="requestParams",many=@Many(select="com.lemon.mapper.ApiRequestParamMapper.findAll"))
	})
	public ApiVO findApiViewVO(Integer apiId);

}
