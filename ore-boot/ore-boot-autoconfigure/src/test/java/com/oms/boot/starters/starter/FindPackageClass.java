package com.oms.boot.starters.starter;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarInputStream;

public class FindPackageClass
{
    public static void main(String[] args)
    {
        // String ss="cn.yyzx.test.TestC";
        // try {
        // Thread a=(Thread) Class.forName(ss).newInstance();
        // a.start();
        // } catch (Exception e) {
        // e.printStackTrace();
        // }
        List<String> cls = getClassInPackage("com.oms.boot.starters.starter");
        for (String s : cls)
        {
            System.out.println(s);
        }
    }
    
    public static List<String> getClassInPackage(String pkgName)
    {
        List<String> ret = new ArrayList<String>();
        String rPath = pkgName.replace('.', '/') + "/";
        try
        {
            for (File classPath : CLASS_PATH_ARRAY)
            {
                if (!classPath.exists())
                    continue;
                if (classPath.isDirectory())
                {
                    File dir = new File(classPath, rPath);
                    if (!dir.exists())
                        continue;
                    for (File file : dir.listFiles())
                    {
                        if (file.isFile())
                        {
                            String clsName = file.getName();
                            clsName = pkgName + "." + clsName.substring(0, clsName.length() - 6);
                            ret.add(clsName);
                        }
                    }
                }
                else
                {
                    FileInputStream fis = new FileInputStream(classPath);
                    JarInputStream jis = new JarInputStream(fis, false);
                    JarEntry e = null;
                    while ((e = jis.getNextJarEntry()) != null)
                    {
                        String eName = e.getName();
                        if (eName.startsWith(rPath) && !eName.endsWith("/"))
                        {
                            ret.add(eName.replace('/', '.').substring(0, eName.length() - 6));
                        }
                        jis.closeEntry();
                    }
                    jis.close();
                }
            }
        }
        catch (Exception e)
        {
            throw new RuntimeException(e);
        }
        
        return ret;
    }
    
    private static String[] CLASS_PATH_PROP = {"java.class.path", "java.ext.dirs", "sun.boot.class.path"};
    
    private static List<File> CLASS_PATH_ARRAY = getClassPath();
    
    private static List<File> getClassPath()
    {
        List<File> ret = new ArrayList<File>();
        String delim = ":";
        if (System.getProperty("os.name").indexOf("Windows") != -1)
            delim = ";";
        System.out.println(System.getProperty("os.name"));
        for (String pro : CLASS_PATH_PROP)
        {
            String[] pathes = System.getProperty(pro).split(delim);
            for (String path : pathes)
            {
                System.out.println(path);
                ret.add(new File(path));
            }
        }
        return ret;
    }
}
