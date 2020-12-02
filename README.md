# 反射
> 将类的各个组成部分封装为其它对象，这就是反射机制

![Java执行过程 ](.\img\Java执行过程 .jpg)

## 反射的好处
- 可以在程序的运行过程中操作对象
- 可以解耦，提高程序的可扩展性



## 获取Class类的对象
- `Class.forName("全类名");`	 将字节码文件加载进内存，返回Class对象（该方式适合文件未加载进内存时使用）
  - 该方式多**用于配置文件**，类名定义在配置文件中，读取文件加载类
- `类名.class` 通过类名.class获取Class类的对象（该方式适用于文件已经加载进入内存）
  - 该方式多**用于参数的传递**
- `对象.getClass();` getClass()方法封装在了Object对象中（该方式适用于对象已经被创建，也就是被new出来后）
  - **多用于对象的获取字节码的方式**

> 实例

```java
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
```



同一个字节码文件（*.class）再一次运行中只会被加载一次，无论如何获取的class文件都是一样的



## 使用Class对象

> 获取功能

- 获取成员变量
- 获取构造方法
- 获取成员方法
- 获取类名



### 获取成员变量

- `Filed[] getFields()`	获取**所有public修饰的成员变量**
- `Filed getField(String name) `   获取**指定名称的public修饰的成员变量**

> 获取到成员变量后一般会进行获取值和操作值这两个操作

get(Object o)：获取对象的值

set(Object o, value)：设置对象的值

> 实例

```java
package com.learn.reflect;

import com.learn.domain.Person;

import java.io.File;
import java.lang.reflect.Field;

public class ReflectDemo2 {
    public static void main(String[] args) throws Exception {
        //获取Person的Class对象
        Class<Person> clazz = Person.class;

        //获取该类下的所有public修饰的成员变量
        Field[] fields = clazz.getFields();

        for (Field field : fields) {
            System.out.println(field);
        }

        //获取指定名称的public修饰的成员变量，该方法可能会抛出异常
        Field field = clazz.getField("msg");
        Field field1 = clazz.getField("name");  //Exception in thread "main" java.lang.NoSuchFieldException: name
        System.out.println(field);  //public java.lang.String com.learn.domain.Person.msg

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
    }
}
```



- `Filed[] getDeclaredFields()` 	获取所有的成员变量，不考虑修饰符
- `Filed getDeclaredField(String name)`  获取单个成员变量，不考虑修饰符

#### 暴力反射setAccessible(true)

> 由于变量本身是私有，正常情况下是无权访问的，但是我们可以使用暴力反射修改/访问被私有化的成员变量

```java
package com.learn.reflect;

import com.learn.domain.Person;
import java.lang.reflect.Field;

public class ReflectDemo2 {
    public static void main(String[] args) throws Exception {
        //获取Person的Class对象
        Class<Person> clazz = Person.class;

        //获取所有的成员变量，不考虑修饰符
        Field[] declaredFields = clazz.getDeclaredFields();
        for (Field field1 : declaredFields) {
            System.out.println(field1);
        }

        Field _name = clazz.getDeclaredField("name");
        //如果直接访问私有值是不允许被访问的
//        System.out.println(_name.get(p));   //出现异常

        //忽略访问修饰符安全检查，一旦设置即可无视访问权限
        _name.setAccessible(true);  //暴力反射
        _name.set(p, "Tony");
        System.out.println(_name.get(p));
    }
}
```



### 获取构造方法

- `Constructor<?>[] getConstructors()`  	获取public修饰的所有构造方法
- `Constructor<?> getConstructor(Class<>...parameterTypes)`   获取public修饰的指定构造方法

> 通过构造器创建对象

`newInstance()`方法可以通过构造器创建对象

```java
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
```



- `Constructor<?>[] getDeclaredConstructors()`
- `Constructor<?> getDeclaredConstructor(Class<>...parameterTypes)`



> 如果使用其它修饰符的构造器，那么在进行set的时候，也可以使用`setAccessible(true)`暴力反射



### 获取成员方法

- `Method[] getMethods()`	获取所有public修饰符的成员方法
- `Method getMethod(String name, Class<?>... parameterTypes) ` 获取指定的public修饰符的成员方法

- `Method[] getDeclaredMethods()`   获取所有成员方法
- `Method getDeclaredMethod(String name, Class<?>... parameterTypes)`   获取指定的成员方法

```java
package com.learn.reflect;

import com.learn.domain.Person;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class ReflectDemo4 {
    public static void main(String[] args) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Class<Person> personClass = Person.class;

        Method[] methods = personClass.getMethods();
        for (Method method : methods) {
            System.out.println(method);	////这里还会打印从Object类继承的一些方法
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
    }
}
```



#### 执行方法

> 获取到方法后，如果需要执行，则需要`invoke()`方法

```java
package com.learn.reflect;

import com.learn.domain.Person;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class ReflectDemo4 {
    public static void main(String[] args) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
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
```

#### 获取方法名

> 使用getName()可以获取方法名

```java
Class<Person> personClass = Person.class;

Method[] methods = personClass.getMethods();
for (Method method : methods) {
    System.out.println(method.getName());
}
```



### 获取类名

- `String getName()`

使用该方法打印的是全类名

```java
public class ReflectDemo4 {
    public static void main(String[] args) throws Exception {
        Class<Person> personClass = Person.class;

        System.out.println(personClass);    //class com.learn.domain.Person
        System.out.println(personClass.getName());  //com.learn.domain.Person
    }
}
```



## 反射实际案例

### 案例需求

#### 需求

- 写一个“框架”，可以帮我们创建任意类的对象，并且执行任意方法
- 不能改变该类的任意代码，可以创建任意类的方法

#### 实现需求

- 配置文件
- 反射

#### 步骤

- 将需要创建的对象的全类名和需要执行的方法定义在配置文件中
- 在程序中加载、读取配置文件
- 使用反射技术来加载类文件，使其进入内存
- 创建对象
- 执行方法

> 框架类

```java
package com.learn.reflect;


import java.io.InputStream;
import java.lang.reflect.Method;
import java.util.Properties;

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
```

> 配置文件

```properties
className=com.learn.domain.Student
methodName=sleep
```



一旦使用了配置文件，就触发到了反射机制