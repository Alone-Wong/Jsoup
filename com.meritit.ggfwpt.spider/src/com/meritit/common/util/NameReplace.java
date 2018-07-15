package com.meritit.common.util;
import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
 
/**
 * 重命名规则类
 * @author jack
 */
public class NameReplace{
    private Map<String,String> map;
 
 
    public NameReplace() {
        this.map = new HashMap<String, String>();
    }
 
    public Map<String, String> getMap() {
        return map;
    }
 
    // 添加新的替换规则(字符串替换)
    public NameReplace addRegulation(String oldStr , String newStr){
        this.map.put(oldStr, newStr);
        return this;
    }
 
}
 
/**
 * 重命名类
 * @author Jack
 */
class Rename {
 
    /**
     * 批量重命名
     * @param path
     * @param replacementChain
     */
    public static void multiRename(String path,NameReplace replacementChain){
        File file = new File(path);
        boolean isDirectory = file.isDirectory();
 
        /** 如果不是文件夹，就返回* */
        if(!isDirectory){
            System.out.println(path + "不是一个文件夹！");
            return;
        }
 
        String[] files = file.list();
        File f = null;
        String filename = "";
        String oldFileName = ""; //之前的名字
        /** 循环遍历所有文件* */
        for(String fileName : files){
            oldFileName = fileName;
            Map<String, String> map = replacementChain.getMap();
            for (Entry<String, String> entry : map.entrySet()) {
                fileName = fileName.replace(entry.getKey(), entry.getValue());
            }
 
            f = new File(path + "\\" + oldFileName); //输出地址和原路径保持一致
            f.renameTo(new File(path + "\\" +  fileName));
        }
        System.out.println("恭喜，批量重命名成功！");
    }
 
    public static void main(String[] args) {
        NameReplace replacementChain = new NameReplace();
        replacementChain.addRegulation("【我是一个好长好长的前缀哦】~~~~~", "").addRegulation("（我是萌萌的小尾巴）", "");
        Rename.multiRename("F:\\测试文件夹", replacementChain);
    }
}