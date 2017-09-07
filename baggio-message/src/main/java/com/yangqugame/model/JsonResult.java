package com.yangqugame.model;

/**
 * Created by Administrator on 2017/8/30 0030.
 */
public class JsonResult<T> {

    private int code;
    private String message;
    private T data;

    public JsonResult() {}

    public JsonResult(int code) {
        this.code = code;
    }

    public JsonResult(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public JsonResult(int code, T data) {
        this.code = code;
        this.data = data;
    }

    public JsonResult(int code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public boolean isSuccess() {
        return code == 200;
    }
}
