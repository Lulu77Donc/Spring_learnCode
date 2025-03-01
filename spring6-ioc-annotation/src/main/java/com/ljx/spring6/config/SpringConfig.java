package com.ljx.spring6.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration //配置类
@ComponentScan("com.ljx.spring6") //开启组件扫描，等同于xml配置文件中的component-scan
public class SpringConfig {
}
