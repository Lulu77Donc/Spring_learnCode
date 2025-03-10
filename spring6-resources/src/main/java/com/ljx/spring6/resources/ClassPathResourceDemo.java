package com.ljx.spring6.resources;

import org.springframework.core.io.ClassPathResource;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

//访问类路径下资源
public class ClassPathResourceDemo {

    public static void loadClassPathResource(String path){
        //创建对象
        ClassPathResource resource = new ClassPathResource(path);
        System.out.println(resource.getFilename());
        System.out.println(resource.getDescription());
        //获取文件内容
        try {
            BufferedReader reader =
                    new BufferedReader(new InputStreamReader(resource.getInputStream()));
//            InputStream in = resource.getInputStream();
            String line;
            byte[] b = new byte[1024];
            while ((line = reader.readLine())!=null){
                System.out.println(line);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        loadClassPathResource("test.txt");
    }
}
