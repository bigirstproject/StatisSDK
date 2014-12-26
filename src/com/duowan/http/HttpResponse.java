package com.duowan.http;

import com.duowan.statis.interfaces.IEntity;

public class HttpResponse implements IEntity {
	
	private static final long serialVersionUID = 1L;
	
	// 结果是否正确
	private boolean isOk;

	public boolean isOk() {
		return isOk;
	}

	public void setOk(boolean isOk) {
		this.isOk = isOk;
	}

}
