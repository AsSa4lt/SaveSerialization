package com.gmail.st1tchqwerty;

import java.lang.reflect.InvocationTargetException;

public class Main {
    public static void main(String[] args) throws IllegalAccessException, InstantiationException, NoSuchFieldException, InvocationTargetException, NoSuchMethodException {
        TestClass test1=new TestClass("Loh",72, "male");
        String res=Serializer.serialize(test1);
        System.out.println(res);
        test1=Serializer.deSerialize(TestClass.class, res);
        System.out.println("Deserialized:"+test1.getName()+","+test1.getAge()+","+test1.isGender());

    }
}
