package com.lemon.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lemon.common.ApiClassificationVO;
import com.lemon.mapper.ApiClassificationMapper;
import com.lemon.pojo.ApiClassification;
import com.lemon.service.ApiClassificationService;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author samlin
 * @since 2020-02-18
 */
@Service
public class ApiClassificationServiceImpl extends ServiceImpl<ApiClassificationMapper, ApiClassification> implements ApiClassificationService {
    
	@Autowired
	ApiClassificationMapper apiClassificationMapper;
	 
	@Override
	public List<ApiClassificationVO> getWithApi(Integer projectId) {
		// TODO Auto-generated method stub
		return apiClassificationMapper.getWithApi(projectId);
	}

}
