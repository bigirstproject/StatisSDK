package com.duowan.statis.interfaces;

public interface ResponsePackage<T extends Object> {

	/**
	 * 设置返回的字符串
	 * 
	 * @param s
	 */
	public void setContext(byte[] data);

	/**
	 * 获取返回对象集合
	 * 
	 * @return
	 */
	public void getResponseData(T t);

}
