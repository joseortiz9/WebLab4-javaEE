package ru.itmo.lab4.payload;

import java.io.Serializable;

public class ObjWithMsgResponse<T extends Serializable> {
    private String msg;
    private T object;

    public ObjWithMsgResponse() {}

    public ObjWithMsgResponse(String msg, T object) {
        this.msg = msg;
        this.object = object;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getObject() {
        return object;
    }

    public void setObject(T object) {
        this.object = object;
    }
}
