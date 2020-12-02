package com.learn.domain;

public class Person {
    private String name;
    private int age;

    public String msg;
    public int _no;
    public int score;

    public Person() {
    }

    public Person(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void eat() {

    }

    private void see() {

    }

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", msg='" + msg + '\'' +
                ", _no=" + _no +
                ", score=" + score +
                '}';
    }
}
