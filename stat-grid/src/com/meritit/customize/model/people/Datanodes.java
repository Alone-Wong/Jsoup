package com.meritit.customize.model.people;

import java.util.List;

public class Datanodes {

    private String code;
    private Data data;
    private List<Wds> wds;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public List<Wds> getWds() {
        return wds;
    }

    public void setWds(List<Wds> wds) {
        this.wds = wds;
    }

}