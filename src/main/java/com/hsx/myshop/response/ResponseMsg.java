package com.hsx.myshop.response;

public enum ResponseMsg {

    SUCCESS("成功"),
    FAILURE("失败");
    private final String value;

    // 自定义构造,虽然没有写private,但是默认就是private
    ResponseMsg(String v) {
        value = v;
    }


    // 重写方法
    @Override
    public String toString() {
        return value;
    }


}
