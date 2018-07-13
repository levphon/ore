package com.glsx.springboot.test.annotation;

import java.lang.reflect.Field;

public class AnnotationProcessor
{
    public static void getStudentInfo(Class<?> clazz)
    {
        if (clazz.isAnnotationPresent(Persion.class))
        {
            Persion annotation = (Persion)clazz.getAnnotation(Persion.class);
            System.out.println(annotation);
            System.out.println(annotation.age());
            System.out.println(annotation.name());
            String[] hobby = annotation.hobby();
            for (String str : hobby)
            {
                System.out.println(str);
            }
        }
        Field[] fields = clazz.getDeclaredFields();
        
        for (Field field : fields)
        {
            System.out.println("fieldName=" + field.toString());
            if (field.isAnnotationPresent(StudentGender.class))
            {
                StudentGender annotation = (StudentGender)field.getAnnotation(StudentGender.class);
                System.out.println(annotation);
                System.out.println(annotation.gender());
            }
        }
    }
    
    public static void main(String[] args)
    {
        // TODO Auto-generated method stub
        AnnotationProcessor.getStudentInfo(Student.class);
    }
    
}
