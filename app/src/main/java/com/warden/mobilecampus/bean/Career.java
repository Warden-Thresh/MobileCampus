package com.warden.mobilecampus.bean;

import java.util.List;

/**
 * Created by Warden on 2017/9/13.
 */

public class Career {

    /**
     * code : 1
     * msg :
     * data :
     */

    private int code;
    private String msg;
    private List<Recruitment> data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<Recruitment> getData() {
        return data;
    }

    public void setData(List<Recruitment> data) {
        this.data = data;
    }


}
