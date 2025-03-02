package com.ljx.reflect;

import org.junit.jupiter.api.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class TestCar {

    //1、获取class对象多种方式
    @Test
    public void test01() throws Exception {
        //1 类名.class
        Class clazz1 = Car.class;

        //2 对象.getClass()
        Class clazz2 = new Car().getClass();

        //3 Class.forName("全路径")
        Class clazz3 = Class.forName("com.ljx.reflect.Car");

        //实例化
        Car car = (Car)clazz3.getDeclaredConstructor().newInstance();
        System.out.println(car);

    }

    //2、获取构造方法
    @Test
    public void test02() throws Exception{
        Class clazz = Car.class;
        //获取所有的构造
        //getConstructors()获取所有public的构造方法
        //getDeclaredConstructors()获取所有构造方法
        //Constructor[] constructors = clazz.getConstructors();
        Constructor[] constructors = clazz.getDeclaredConstructors();
        for (Constructor c: constructors) {
            System.out.println("方法名称："+c.getName()+"参数个数："+c.getParameterCount());
        }

        //指定有参构造创建对象
        //获取具体构造
        //1 构造public
        Constructor c1 =
                clazz.getConstructor(String.class, int.class, String.class);
        Car car1 = (Car) c1.newInstance("夏利", 10, "红色");
        System.out.println(car1);

        //2 构造private
        Constructor c2 =
                clazz.getDeclaredConstructor(String.class, int.class, String.class);
        c2.setAccessible(true);//允许访问私有构造
        Car car2 = (Car) c2.newInstance("捷达", 15, "白色");
        System.out.println(car2);
    }


    //3、获取属性
    @Test
    public void test03() throws Exception{
        Class clazz = Car.class;
        Car car = (Car)clazz.getDeclaredConstructor().newInstance();
        //获取所有public属性
        //Field[] fields = clazz.getFields();
        //获取所有属性，包括私有属性
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            if(field.getName().equals("name")){
                //设置允许访问
                field.setAccessible(true);
                field.set(car,"五菱宏光");
            }
            System.out.println(field.getName());
            System.out.println(car);
        }
    }

    //4、获取方法
    @Test
    public void test04() throws Exception {
        Class clazz = Car.class;
        Car car = (Car) clazz.getDeclaredConstructor(String.class,int.class, String.class).newInstance("奔驰",10,"黑色");
        //1 public方法
        Method[] methods = clazz.getMethods();
        for (Method method : methods) {
            //System.out.println(method.getName());
            //执行方法
            if(method.getName().equals("toString")){
                String invoke = (String) method.invoke(car);
                System.out.println("toString执行了："+invoke);
            }
        }

        //2 private方法
        Method[] methosdAll = clazz.getDeclaredMethods();
        for (Method m : methosdAll) {
            //执行方法
            if(m.getName().equals("run")){
                m.setAccessible(true);
                m.invoke(car);
            }
        }

    }
}
