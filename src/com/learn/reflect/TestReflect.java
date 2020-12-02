package com.learn.reflect;


import java.io.InputStream;
import java.lang.reflect.Method;
import java.util.Properties;

/*
    @需求 写一个“框架”，可以帮我们创建任意类的对象，并且执行任意方法
        不能改变该类的任意代码，可以创建任意类的方法
    @实现需求
        1.配置文件
        2.反射
    @步骤：
        将需要创建的对象的全类名和需要执行的方法定义在配置文件中
        在程序中加载、读取配置文件
        使用反射技术来加载类文件，使其进入内存
        创建对象
        执行方法
    @TestReflect 框架类
 */
public class TestReflect {
    public static void main(String[] args) throws Exception {
        //加载配置文件
        Properties properties = new Properties();

        //加载配置文件，将加载的配置文件转换为一个集合
        ClassLoader classLoader = TestReflect.class.getClassLoader();   //获取类加载器
        InputStream inputStream = classLoader.getResourceAsStream("pro.properties"); //获取加载器资源路径下的字节流
        properties.load(inputStream);

        //获取配置文件中定义的数据
        String className = properties.getProperty("className");
        String methodName = properties.getProperty("methodName");

        //加载该类进入内存
        Class clazz = Class.forName(className);
        //创建对象
        Object obj = clazz.newInstance();
        //获取方法对象
        Method method = clazz.getMethod(methodName);
        //执行方法
        method.invoke(obj);
    }
}
