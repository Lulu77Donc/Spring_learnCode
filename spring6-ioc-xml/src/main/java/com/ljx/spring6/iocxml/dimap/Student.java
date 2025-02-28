package com.ljx.spring6.iocxml.dimap;

import java.util.Map;

public class Student {
    private Map<String,Teacher>teacherMap;

    private String sid;
    private String sname;

    public Map<String, Teacher> getTeacherMap() {
        return teacherMap;
    }

    public void setTeacherMap(Map<String, Teacher> teacherMap) {
        this.teacherMap = teacherMap;
    }

    public void run(){
        System.out.println("学生编号：" + sid + "学生名称" + sname);
        System.out.println(teacherMap);
    }

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public String getSname() {
        return sname;
    }

    public void setSname(String sname) {
        this.sname = sname;
    }
}
