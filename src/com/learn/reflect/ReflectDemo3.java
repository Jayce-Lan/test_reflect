package com.learn.reflect;

import com.learn.domain.Person;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

//获取构造方法
public class ReflectDemo3 {
    public static void main(String[] args) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        Class clazz = Person.class;

        Constructor[] constructors = clazz.getConstructors();
        for (Constructor constructor : constructors) {
            System.out.println(constructor);
        }

        //获取无参构造方法
        Constructor constructor = clazz.getConstructor();
        System.out.println(constructor);

        //获取有参构造方法
        Constructor constructor1 = clazz.getConstructor(String.class, int.class);
        System.out.println(constructor1);

        //通过构造器创建对象
        Object obj = constructor1.newInstance("张三", 20);    //有可能会抛出异常
        System.out.println(obj.toString());

        //如果单纯的想创建一个无参的构造方法，可以使用Class.newInstance()创建
        Object obj2 = clazz.newInstance();
        System.out.println(obj2.toString());
    }
}
