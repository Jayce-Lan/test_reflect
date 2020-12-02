package com.learn.domain;

public class Student {
    private int age;
    private String name;
    private int _no;

    @Override
    public String toString() {
        return "Student{" +
                "age=" + age +
                ", name='" + name + '\'' +
                ", _no=" + _no +
                '}';
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int get_no() {
        return _no;
    }

    public void set_no(int _no) {
        this._no = _no;
    }

    public Student() {
    }

    public Student(int age, String name, int _no) {
        this.age = age;
        this.name = name;
        this._no = _no;
    }

    public void sleep() {
        System.out.println("学生睡觉");
    }
}
