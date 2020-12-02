package com.learn.reflect;

import com.learn.domain.Person;

import java.lang.reflect.Field;

//获取成员变量的四种方法
public class ReflectDemo2 {
    public static void main(String[] args) throws Exception {
        //获取Person的Class对象
        Class<Person> clazz = Person.class;

        print("获取成员变量");

        //获取该类下的所有public修饰的成员变量
        Field[] fields = clazz.getFields();

        for (Field field : fields) {
            System.out.println(field);
        }

        _print();

        //获取指定名称的public修饰的成员变量，该方法可能会抛出异常
        Field field = clazz.getField("msg");
//        Field field1 = clazz.getField("name");  //Exception in thread "main" java.lang.NoSuchFieldException: name
        System.out.println(field);  //public java.lang.String com.learn.domain.Person.msg

        print("操作成员变量");

        //获取到成员变量后进行操作
        //获取成员变量的值
        Person p = new Person();
        //get(Object o)
        Object value = field.get(p);
        System.out.println(value);  //null

        //设置对象变量的值
        //set(Object o, value)
        field.set(p, "学生");
        System.out.println(p.toString());

        print("获取成员变量的另一种方法");

        //获取所有的成员变量，不考虑修饰符
        Field[] declaredFields = clazz.getDeclaredFields();
        for (Field field1 : declaredFields) {
            System.out.println(field1);
        }

        _print();

        Field _name = clazz.getDeclaredField("name");
        //如果直接访问私有值是不允许被访问的
//        System.out.println(_name.get(p));   //出现异常

        //忽略访问修饰符安全检查，一旦设置即可无视访问权限
        _name.setAccessible(true);  //暴力反射
        _name.set(p, "Tony");
        System.out.println(_name.get(p));
    }

    public static void print(String msg) {
        System.out.println("=============" + msg + "===============");
    }

    public static void _print() {
        System.out.println("---------------------------------");
    }
}
