package com.ljx.bean;

import com.ljx.anno.Bean;

import java.io.File;
import java.net.URL;
import java.net.URLDecoder;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

public class AnnotationApplicationContext implements ApplicationContext{

    //创建map集合，放bean对象
    private static Map<Class,Object> beanFactory = new HashMap<>();
    private static String rootPath;

    //返回对象
    //从spring管理的bean中获取bean对象
    @Override
    public Object getBean(Class clazz) {
        return beanFactory.get(clazz);
    }

    //设置包扫描规则
    //当前包及其子包，哪个类有@Bean注解，把这个类通过反射实例化
    //创建有参数构造，传递包路径
    public AnnotationApplicationContext(String basePackage){
        //com.ljx

        try {
            //1 把.替换成\
            String packagePath = basePackage.replaceAll("\\.", "\\\\");//“//”=/ “////“=//=/

            //2 获取包决定路径
            //java中，包名实际对应的是类路径下目录结构 类路径包括项目编译后的 classes 目录
            //例如 D:/project/target/classes/
            //JAR 包内部
            //例如 some-lib.jar!/com/ljx/
            //运行时动态加载的路径

            //类路径 D:/project/target/classes/com/ljx/MyClass.class
            Enumeration<URL> urls
                    = Thread.currentThread().getContextClassLoader().getResources(packagePath);
            //返回可能值 file:/D:/project/target/classes/com/ljx/
            while (urls.hasMoreElements()){
                //hasMoreElements() 检查 是否还有未处理的资源，如果有则返回 true。
                URL url = urls.nextElement();//取出下一个 URL，即 packagePath 对应的 文件路径 或 JAR 内部路径。
                String filePath = URLDecoder.decode(url.getFile(), "utf-8");//转译

                //获取包前面路径部分，字符串截取
                //D:/project/target/classes/com/ljx/
                rootPath = filePath.substring(0, filePath.length() - packagePath.length());

                //包扫描
                loadBean(new File(filePath));

            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    //包扫描过程，实例化
    private static void loadBean(File file) throws Exception {
        //1 判断当前是否文件夹
        if(file.isDirectory()){
            //2 获取文件夹里面所有内容
            File[] childrenFiles = file.listFiles();

            //3 判断文件夹里面内容为空，直接返回
            if(childrenFiles == null || childrenFiles.length == 0){
                return;
            }

            //4 如果文件夹里面不为空，遍历文件夹所有内容
            for (File childrenFile : childrenFiles) {
                //4.1 遍历得到每个File对象，继续判断，如果还是文件夹，继续递归
                if(childrenFile.isDirectory()){
                    //递归
                    loadBean(childrenFile);
                }else {
                    //4.2 遍历得到File对象不是文件夹，是文件，
                    //4.3 得到包路径+类名称部分-字符串截取
                    String pathWithClass = childrenFile
                            .getAbsolutePath()
                            .substring(rootPath.length() - 1);//为排除路径最后有\，所以-1
                    //只获得包路径，比如com/ljx/service/UserServiceImpl.class

                    //4.4 判断当前文件类型是否.class
                    if(pathWithClass.contains(".class")){
                        //4.5 如果是.class类型，把路径\替换成. 把.class去掉
                        // com.ljx.service.UserServiceImpl
                        String allName = pathWithClass.replaceAll("\\\\", ".")
                                //.replace("\\",".")
                                .replace(".class", "");

                        //4.6 判断类上面是否有注解@Bean，如果有实例化过程
                        //4.6.1 获取类class对象
                        Class<?> clazz = Class.forName(allName);

                        //4.6.2 判断不是接口
                        if(!clazz.isInterface()){
                            //4.6.3 判断类上面是否有注解 @Bean
                            Bean annotation = clazz.getAnnotation(Bean.class);
                            if(annotation != null){
                                //4.6.4 实例化
                                Object instance = clazz.getConstructor().newInstance();
                                //4.7 对象实例化之后，放到map集合beanFactory
                                //4.7.1 判断当前类如果有接口，让接口class作为map的key
                                if(clazz.getInterfaces().length>0){
                                    beanFactory.put(clazz.getInterfaces()[0],instance);

                                }else {
                                    beanFactory.put(clazz,instance);

                                }
                            }
                        }
                    }
                }
            }
        }
    }

   /* public static void main(String[] args) {
        ApplicationContext context =
                new AnnotationApplicationContext("com.ljx");
        context.getBean();
    }*/
}
