package com.mcy.layui.custom;

import java.util.HashMap;

import org.springframework.data.domain.Page;

public class AjaxResult {
    private Boolean success;
    private String msg;

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public AjaxResult(String msg) {
        super();
        this.success = true;
        this.msg = msg;
    }

    public AjaxResult(Boolean success, String msg) {
        super();
        this.success = success;
        this.msg = msg;
    }

    public AjaxResult(boolean success) {
        this.success = success;
    }

    @SuppressWarnings("rawtypes")
    public static HashMap<String, Object> bulidPageResult(Page page) {
        HashMap<String, Object> result = new HashMap<>();
        result.put("total", page.getTotalElements());
        result.put("rows", page.getContent());
        return result;
    }
}
