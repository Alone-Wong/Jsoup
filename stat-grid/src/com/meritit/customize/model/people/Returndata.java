package com.meritit.customize.model.people;

import java.util.List;

public class Returndata {

    private List<Datanodes> datanodes;
    private List<Wdnodes> wdnodes;
    public void setDatanodes(List<Datanodes> datanodes) {
         this.datanodes = datanodes;
     }
     public List<Datanodes> getDatanodes() {
         return datanodes;
     }

    public void setWdnodes(List<Wdnodes> wdnodes) {
         this.wdnodes = wdnodes;
     }
     public List<Wdnodes> getWdnodes() {
         return wdnodes;
     }

}