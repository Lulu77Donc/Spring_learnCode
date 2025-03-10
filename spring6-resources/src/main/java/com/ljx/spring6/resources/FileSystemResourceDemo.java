package com.ljx.spring6.resources;

import org.springframework.core.io.FileSystemResource;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class FileSystemResourceDemo {

    public static void main(String[] args) {
        //相对路径，根目录下文件
        loadFileResource("test.txt");
    }

    public static void loadFileResource(String path){
        //创建对象
        FileSystemResource resource = new FileSystemResource(path);
        System.out.println(resource.getFilename());
        System.out.println(resource.getDescription());
        try {
            BufferedReader reader =
                    new BufferedReader(new InputStreamReader(resource.getInputStream()));
            String line;
            byte[] b = new byte[1024];
            while((line=reader.readLine())!=null){
                System.out.println(line);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
