package com.ljx.spring6.resources;

import org.springframework.core.io.UrlResource;

import java.net.MalformedURLException;

//演示urlResources访问网络资源
public class UrlResources {

    public static void main(String[] args) {
        //http前缀
//        loadUrlResource("http://www.baidu.com");

        //file前缀
        loadUrlResource("file:test.txt");
    }

    //访问前缀http,file
    public static void loadUrlResource(String path){
        //创建resource实现类对象
        try {
            UrlResource url = new UrlResource(path);
            //获取资源信息
            System.out.println(url.getFilename());
            System.out.println(url.getURI());
            System.out.println(url.getDescription());
            System.out.println(url.getInputStream().read());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
}
