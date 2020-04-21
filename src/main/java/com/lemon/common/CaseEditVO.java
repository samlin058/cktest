package com.lemon.common;

import java.util.ArrayList;
import java.util.List;

import com.lemon.pojo.ApiRequestParam;
import com.lemon.pojo.Cases;
import com.lemon.pojo.TestRule;

import lombok.Data;

@Data
public class CaseEditVO  extends Cases{
//     private Integer id;
//     private String name;
     private String method;
     private String url;
     private String apiId;
     private String host;
     private List<ApiRequestParam> requestParams = new ArrayList<ApiRequestParam>(); 
     
     private List<TestRule> testRules = new ArrayList<TestRule>();
}
