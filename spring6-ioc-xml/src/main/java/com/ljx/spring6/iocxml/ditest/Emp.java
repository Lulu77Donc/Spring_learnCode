package com.ljx.spring6.iocxml.ditest;

import java.util.Arrays;

public class Emp {

    //对象类型属性：员工属于某个部门
    private Dept dept;

    private String ename;
    private Integer age;
    private String[] loves;

    public String[] getLoves() {
        return loves;
    }

    public void setLoves(String[] loves) {
        this.loves = loves;
    }

    public Dept getDept() {
        return dept;
    }

    public void setDept(Dept dept) {
        this.dept = dept;
    }

    public String getEname() {
        return ename;
    }

    public void setEname(String ename) {
        this.ename = ename;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public void work(){
        System.out.println(ename+"emp work..."+age);
        dept.info();
        System.out.println(Arrays.toString(loves));
    }
}
