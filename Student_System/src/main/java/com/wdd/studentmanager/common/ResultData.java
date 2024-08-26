package com.wdd.studentmanager.common;

import com.wdd.studentmanager.domain.*;
import lombok.Data;

import java.util.List;

@Data
public class ResultData {

    private Integer code;

    private String msg;

    private long count;

    private Object data;

    private List<S_student> list;

    private List<S_course> list_course;

    private List<S_dormit> list_dormit;

    private List<S_leave> list_leave;

    private List<Admin_confirm> list_admin_confirm;

    private boolean success;
    public static ResultData success(Object data) {
        return resultData(data);
    }

    public static ResultData success(List<S_student> list){
        ResultData resultData =new ResultData();
        resultData.setList(list);
        resultData.setCode(200);
        resultData.setMsg("获取成功");
        resultData.setSuccess(true);
        return resultData;
    }

    public static ResultData success_leave(List<S_leave> list){
        ResultData resultData =new ResultData();
        resultData.setList_leave(list);
        resultData.setCode(200);
        resultData.setMsg("获取成功");
        resultData.setSuccess(true);
        return resultData;
    }
    public static ResultData success_dormit(List<S_dormit> list){
        ResultData resultData =new ResultData();
        resultData.setList_dormit(list);
        resultData.setCode(200);
        resultData.setMsg("获取成功");
        resultData.setSuccess(true);
        return resultData;
    }

    public static ResultData success_course(List<S_course> list){
        ResultData resultData =new ResultData();
        resultData.setList_course(list);
        resultData.setCode(200);
        resultData.setMsg("获取成功");
        resultData.setSuccess(true);
        return resultData;
    }

    public static ResultData success_admin_confirm(List<Admin_confirm> list){
        ResultData resultData =new ResultData();
        resultData.setList_admin_confirm(list);
        resultData.setCode(200);
        resultData.setMsg("获取成功");
        resultData.setSuccess(true);
        return resultData;
    }

    public static ResultData success(long count, Object data) {
        return resultData(200, "消息返回成功", count, data);
    }
    public static ResultData success(String msg, long count, Object data) {
        return resultData(200, msg, count, data);
    }
    public static ResultData success(Integer code, long count, Object data) {
        return resultData(code, "", count, data);
    }

    public static ResultData fail(String msg) {
        return resultData(500, msg, 0, null);
    }

    private static ResultData resultData(Object data) {
        ResultData resultData = new ResultData();
        resultData.setCode(200);
        resultData.setMsg("消息返回成功");
        resultData.setData(data);
        resultData.setSuccess(true);
        return resultData;
    }

    private static ResultData resultData(Integer code, String msg, long count, Object data) {
        ResultData resultData = new ResultData();
        resultData.setCode(code);
        resultData.setMsg(msg);
        resultData.setCount(count);
        resultData.setData(data);
        return resultData;
    }
}
