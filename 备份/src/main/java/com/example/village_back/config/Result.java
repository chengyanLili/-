package com.example.village_back.config;

public class Result<T> {
    private String code;//用code和错误信息一一对应，前台就知道返回的结果是什么意思
    private String msg;//后台可以把错误信息返回前台，比如用户名或者密码错误，错误信息在msg里面，前台可以直接弹出msg
    private T data; //定义一个T类型，T是一个泛型，表示任何一个的数据类型都可以被result包含
    //需要查不同的表，返回的对象是不一样的，比如查班级和查用户是两个不同的实体，两个实体都可以用result包装

    public String getCode() { return code; }

    public void setCode(String code) { this.code = code; }

    public String getMsg() { return msg; }

    public void setMsg(String msg) { this.msg = msg; }

    public T getData() { return data; }

    public void setData(T data) { this.data = data; }

    public Result() {
    }

    public Result(T data) { this.data = data; }

    //定义了返回的常用的一些方法
    //success自动地写上0和成功
    public static Result success() {
        Result result = new Result<>();
        //预置的两条信息，不用每次都写0和成功
        result.setCode("0");
        result.setMsg("成功");
        return result;
    }

    public static <T> Result<T> success(T data) {
        Result<T> result = new Result<>(data);
        result.setCode("0");
        result.setMsg("成功");
        return result;
    }

    public static Result error( String msg) {
        Result result = new Result();
//        result.setCode(code);
        result.setMsg(msg);
        return result;
    }
}
