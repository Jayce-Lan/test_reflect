package com.learn.reflect;

import com.learn.domain.Person;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class ReflectDemo4 {
    public static void main(String[] args) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Class<Person> personClass = Person.class;

        System.out.println(personClass);    //class com.learn.domain.Person
        System.out.println(personClass.getName());  //com.learn.domain.Person

        Method[] methods = personClass.getMethods();
        for (Method method : methods) {
            System.out.println(method); //这里还会打印从Object类继承的一些方法
            System.out.println(method.getName());   //获取方法名
        }

        System.out.println("===============================================");

        //这里如果为有参方法，那么需要传递多个参数
        Method method = personClass.getMethod("eat");
        System.out.println(method);

        //有参方法的获取
        Method _method = personClass.getMethod("setName", String.class);
        System.out.println(_method);

        System.out.println("===============================================");

        Method[] methods1 = personClass.getDeclaredMethods();
        for (Method method1 : methods1) {
            System.out.println(method1);
        }

        System.out.println("===============================================");

        //执行有参方法
        Person person = new Person();
        Method setName = personClass.getMethod("setName", String.class);
        //需要执行方法时，如果方法为有参方法，invoke的参数必须传入参数
        setName.invoke(person, "张三");

        //执行无参方法
        Method getName = personClass.getMethod("getName");
        Object name = getName.invoke(person);

        System.out.println(name);
        System.out.println(person.toString());
    }
}
