package com.yb.geek.homework.week01;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;

/**
 * @author yangbiao
 * @date 2021/3/19
 */
@Slf4j
public class MyClassLoader extends ClassLoader {

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        try {
            InputStream in = getResourceAsStream("Hello.xlass");
            byte[] bytes = new byte[in.available()];
            byte[] data = new byte[bytes.length];
            in.read(bytes, 0, bytes.length);
            for (int i = 0; i < bytes.length; i++) {
                data[i] = (byte) (255 - bytes[i]);
            }
            return defineClass(name, data, 0, data.length);
        } catch (IOException e) {
            log.debug(e.getMessage(),e);
            throw new ClassNotFoundException();
        }
    }

    public static void main(String[] args) throws Exception {
        MyClassLoader myClassLoader = new MyClassLoader();
        Class<?> aClass = myClassLoader.findClass("Hello");
        Method hello = aClass.getMethod("hello", null);
        hello.invoke(aClass.newInstance(), null);
    }

}
