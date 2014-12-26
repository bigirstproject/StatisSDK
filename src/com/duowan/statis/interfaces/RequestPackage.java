
package com.duowan.statis.interfaces;

import org.apache.http.HttpEntity;

public interface RequestPackage {

    /**
     * 获取GET方法参数
     * 
     * @return
     */
    public String getGetRequestParams();

    /**
     * 获取Post请求Entity<br/>
     * 根据不用的参数，需要将数据转换成对应HttpEntity子类
     * 
     * @return
     */
    public HttpEntity getPostRequestEntity();

    /**
     * 获取请求链接
     * 
     * @return
     */
    public String getUrl();

    /**
     * 请求类型
     * 
     * @return
     */
    public String getRequestType();

}
