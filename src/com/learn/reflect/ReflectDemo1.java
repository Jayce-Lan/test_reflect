package com.learn.reflect;

import com.learn.domain.Person;

//获取Class对象的三种方式
public class ReflectDemo1 {
    public static void main(String[] args) throws ClassNotFoundException {
        //Class.forName("报名.类名");获取Class对象
        Class clazz = Class.forName("com.learn.domain.Person");
        System.out.println(clazz); //class com.learn.domain.Person

        print();

        //类名.class获取Class对象
        Class<Person> personClass = Person.class;
        System.out.println(personClass);    //class com.learn.domain.Person

        print();

        //对象.getClass()获取Class对象
        Person person = new Person();
        Class clazz2 = person.getClass();
        System.out.println(clazz2); //class com.learn.domain.Person

        print();

        //同一个字节码文件（*.class）再一次运行中只会被加载一次
        System.out.println(clazz == personClass);   //true
        System.out.println(clazz == clazz2);    //true

    }

    public static void print() {
        System.out.println("===========================================");
    }
}
