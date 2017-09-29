package com.jaxon.entity.web;

import java.io.Serializable;
import java.util.Map;

/**
 *
 * @author Jaxon
 *
 */
public class ApiResult implements Serializable
{

    private static final long serialVersionUID = 1L;

    /**
     * 接口调用结果状态码：S、F三种状态分别对应 调用成功、调用失败
     *
     * 失败情况包括业务异常（比如调用某些依赖接口），系统异常等
     *
     *
     */
    private String status;

    /**
     * 响应请求信息
     */
    private String msg;

    /**
     * 接口返回数据，业务操作结果
     */
    private Map<String, Object> data;

    /**
     * 全属性构造方法
     * @param status
     * @param msg
     * @param data
     */
    public ApiResult(String status, String msg, Map<String, Object> data)
    {
        super();
        this.status = status;
        this.msg = msg;
        this.data = data;
    }

    /**
     * 接口操作成功是调用构造函数
     * @param data
     */
    public ApiResult(Map<String, Object> data)
    {
        super();
        this.status = "S";
        this.msg = "成功！";
        this.data = data;
    }

    public String getStatus()
    {
        return this.status;
    }

    public void setStatus(String status)
    {
        this.status = status;
    }

    public String getMsg()
    {
        return this.msg;
    }

    public void setMsg(String msg)
    {
        this.msg = msg;
    }

    public Map<String, Object> getData()
    {
        return this.data;
    }

    public void setData(Map<String, Object> data)
    {
        this.data = data;
    }

}
