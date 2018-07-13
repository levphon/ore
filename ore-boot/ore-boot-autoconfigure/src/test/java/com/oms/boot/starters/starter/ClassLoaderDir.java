package com.oms.boot.starters.starter;

import java.util.Map;

/**
 * 类加载器各自搜索目录
 * 
 * @author taomk
 */
public class ClassLoaderDir
{
    
    /*
     * 1、Bootstrap Loader（启动类加载器）：加载System.getProperty("sun.boot.class.path")所指定的路径或jar。 2、Extended
     * Loader（标准扩展类加载器ExtClassLoader）：加载System.getProperty("java.ext.dirs")所指定 的路径或jar。在使用Java运行程序时，也可以指定其搜索路径，例如：java
     * -Djava.ext.dirs=d:\projects\testproj\classes HelloWorld 3、AppClass
     * Loader（系统类加载器AppClassLoader）：加载System.getProperty("java.class.path")所指定的路径或jar。
     * 在使用Java运行程序时，也可以加上-cp来覆盖原有的Classpath设置，例如： java -cp ./lavasoft/classes HelloWorld
     * ExtClassLoader和AppClassLoader在JVM启动后，会在JVM中保存一份，并且在程序运行中无法改变其搜索路径。如果想在运行时从其 他搜索路径加载类，就要产生新的类加载器。
     * 
     */
    public static void main(String[] args)
    {
        for (Map.Entry<Object, Object> entry : System.getProperties().entrySet())
        {
            System.out.println(entry.getKey() + "\t" + entry.getValue());
        }
    }
    
}