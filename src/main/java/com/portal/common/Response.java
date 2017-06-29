package com.portal.common;

import java.io.Serializable;

/**
 * 前端接口回报
 *
 */
public class Response implements Serializable {

	private static final long serialVersionUID = 1L;

	private Meta meta;
	private Object datas;

	public Response success() {
		this.meta = new Meta(RespResultEnum.SUCCESS);
		return this;
	}

	public Response success(Object datas) {
		this.meta = new Meta(RespResultEnum.SUCCESS);
		this.datas = datas;
		return this;
	}

	public Response failure() {
		this.meta = new Meta(RespResultEnum.SYSTEM_ERR);
		return this;
	}

	public Response failure(RespResultEnum respResultEnum) {
		this.meta = new Meta(respResultEnum);
		return this;
	}

	public Meta getMeta() {
		return meta;
	}
	
	public Object getDatas() {
		return datas;
	}

	public class Meta {

		private String code;
		private String message;

		public Meta(RespResultEnum respResultEnum) {
			this.code = respResultEnum.getRespCode();
			this.message = respResultEnum.getRespDesc();
		}

		public Meta(String code, String message) {
			this.code = code;
			this.message = message;
		}

		public String getCode() {
			return code;
		}

		public String getMessage() {
			return message;
		}
	}
}
