package com.alone.Test;

import java.lang.reflect.Field;

/**
 * Created by Black on 2018/2/9.
 */
public class test04 {
    public static void main(String[] args) throws Exception {
        Field declaredField = test03.class.getDeclaredField("password");
        declaredField.setAccessible(true);

        test03 t = new test03();
        Object password = declaredField.get(t);
        System.out.println(password.toString());
    }

}
