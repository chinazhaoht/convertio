package shop.convertio.http;

import java.util.HashMap;
import java.util.Map;

public class RespResult {
    private int code;
    private boolean isSuccess;
    private String msg;
    private Object data;

    public int getCode() {
        return code;
    }

    public RespResult setCode(int code) {
        this.code = code;
        return this;
    }

    public boolean isSuccess() {
        return isSuccess;
    }

    public RespResult setSuccess(boolean success) {
        isSuccess = success;
        return this;
    }

    public String getMsg() {
        return msg;
    }

    public RespResult setMsg(String msg) {
        this.msg = msg;
        return this;
    }

    public Object getData() {
        return data;
    }

    public RespResult setData(Object data) {
        this.data = data;
        return this;
    }

    public static RespResult build(){
        return new RespResult();
    }
}
