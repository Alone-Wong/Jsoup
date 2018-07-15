package com.meritit.customize.model.people;

import java.util.List;

public class Wdnodes {

    private List<Nodes> nodes;
    private String wdcode;
    private String wdname;
    public void setNodes(List<Nodes> nodes) {
         this.nodes = nodes;
     }
     public List<Nodes> getNodes() {
         return nodes;
     }

    public void setWdcode(String wdcode) {
         this.wdcode = wdcode;
     }
     public String getWdcode() {
         return wdcode;
     }

    public void setWdname(String wdname) {
         this.wdname = wdname;
     }
     public String getWdname() {
         return wdname;
     }

}