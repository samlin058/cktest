package com.lemon.common;

import lombok.Data;

@Data
public class Result {
	private String status;
	private Object data;
	private String message;

	public Result(String stauts, Object data) {
		super();
		this.status = stauts;
		this.data = data;
	}

	public Result(String stauts, String message) {
		super();
		this.status = stauts;
		this.message = message;
	}

	public Result(String stauts, Object data, String message) {
		super();
		this.status = stauts;
		this.data = data;
		this.message = message;
	}
}
