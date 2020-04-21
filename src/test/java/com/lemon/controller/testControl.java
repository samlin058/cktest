package com.lemon.controller;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.UnsupportedEncodingException;

import org.hamcrest.Matcher;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.alibaba.fastjson.JSONPath;

@SpringBootTest // 启动对单元测试的支持
@RunWith(SpringRunner.class) // 使用哪个单元测试
@AutoConfigureMockMvc
public class testControl {

	@Autowired
	private MockMvc mockMvc;

	String sessionId;

	// 用户登录
	@Before
	@Test
	public void userLogin() throws UnsupportedEncodingException, Exception {
		String resultJson = mockMvc
				.perform(MockMvcRequestBuilders.post("/user/login")
						.param("username", "111@qq.com")
						.param("password","123456"))
				.andDo(print())
				.andExpect(status().isOk())
				.andReturn().getResponse().getContentAsString();
		sessionId =(String) JSONPath.read(resultJson, "$.message");
	}

	//用户验重
	@Test
	public void test() throws Exception {
          mockMvc.perform(MockMvcRequestBuilders.get("/user/find")
          .header("Authorization",sessionId)
          .param("username","111@qq.com"))
          .andDo(print()).andExpect(MockMvcResultMatchers.jsonPath("$.message", Matchers.is("账号已存在")))
          .andReturn();
	}
	
	//参数为json 新增project
	@Test
	public void testProjectAdd() throws Exception {
          mockMvc.perform(MockMvcRequestBuilders.get("/project/add2")
          .header("Authorization",sessionId)
          .contentType(MediaType.APPLICATION_JSON)
          .content("{\"name\":\"ck\"},\"host\":\"http://admin.ck.org\"}")
          )
          .andDo(print()).andExpect(MockMvcResultMatchers.jsonPath("$.message", Matchers.is("1")))
          .andReturn();
	}
}
