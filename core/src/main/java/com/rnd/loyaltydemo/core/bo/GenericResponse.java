package com.rnd.loyaltydemo.core.bo;

import javax.servlet.http.HttpServletResponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GenericResponse {
	@SerializedName("ErrorCode")
	@Expose
	private String errorCode;
	@SerializedName("Message")
	@Expose
	private String message;
	
	@SerializedName("statusCode")
	@Expose
	private int statusCode;
	
	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(final String errorCode) {
		this.errorCode = errorCode;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(final String message) {
		this.message = message;
	}

	public int getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(int statusCode) {
		if (statusCode == HttpServletResponse.SC_CREATED || statusCode == HttpServletResponse.SC_NO_CONTENT) {
			this.statusCode = HttpServletResponse.SC_OK;
		} else {
			this.statusCode = statusCode;
		}
	}
	
}
