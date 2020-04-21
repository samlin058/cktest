package com.lemon.common;

import lombok.Data;

@Data
public class ApiRunResult {
	private String StatusCode;
	private String headers;
	private String body;
}
