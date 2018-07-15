package com.meritit.customize.model.people;

import java.util.List;

public class Datanodes {

    private String code;
    private Data data;
    private List<Wds> wds;
    public void setCode(String code) {
         this.code = code;
     }
     public String getCode() {
         return code;
     }

    public void setData(Data data) {
         this.data = data;
     }
     public Data getData() {
         return data;
     }

    public void setWds(List<Wds> wds) {
         this.wds = wds;
     }
     public List<Wds> getWds() {
         return wds;
     }

}