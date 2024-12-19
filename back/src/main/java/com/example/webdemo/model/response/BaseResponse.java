package com.example.webdemo.model.response;

/**
 * 作为返回值的基类
 */
public class BaseResponse<T> {

    /**
     * 返回代码，默认200
     */
    public int code = 200;

    /**
     * 错误信息
     */
    public String message;

    /**
     * 数据
     */
    public T data;

    @Override
    public String toString() {
        return "BaseResponse{" +
                "code=" + code +
                ", data=" + data +
                '}';
    }
}
