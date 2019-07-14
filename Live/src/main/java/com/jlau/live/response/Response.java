package com.jlau.live.response;

/**
 * Created by cxr1205628673 on 2019/7/1.
 */
public class Response<T> {
    private String success;
    private String message;
    private T data;

    public Response(String success, String message, T data) {
        this.success = success;
        this.message = message;
        this.data = data;
    }

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
