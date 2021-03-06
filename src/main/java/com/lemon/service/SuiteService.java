package com.lemon.service;

import com.lemon.pojo.Suite;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author samlin
 * @since 2020-02-18
 */
public interface SuiteService extends IService<Suite> {
	List<Suite> findSuiteAndReleadtedCasesBy(Integer projectId);
}
