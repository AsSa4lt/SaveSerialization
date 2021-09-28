package com.gmail.st1tchqwerty;

import java.io.BufferedReader;
import java.io.FileReader;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.security.InvalidParameterException;

public class Serializer {
    public static String serialize(Object obj) throws IllegalAccessException{
        Class<?>cls=obj.getClass();
        StringBuilder sr=new StringBuilder();
        Field[] fields= cls.getDeclaredFields();
        for(Field f:fields){
            if(!f.isAnnotationPresent(Save.class)){
                continue;
            }
            if(Modifier.isPrivate(f.getModifiers())){
                f.setAccessible(true);
            }
            sr.append(f.getName()+"=");
            if (f.getType()==int.class){
                sr.append(f.getInt(obj));
            } else if(f.getType()==String.class){
                sr.append((String)f.get(obj));
            }
            sr.append(";");
        }
        return sr.toString();
    }

    public static <T> T deSerialize(Class<T> cls, String s) throws InstantiationException, IllegalAccessException, NoSuchFieldException, NoSuchMethodException, InvocationTargetException {
        T res=(T)cls.newInstance();
        String[] pairs=s.split(";");
        for(String p:pairs){
            String[] nv=p.split(("="));
            if(nv.length!=2)
                throw new InvalidParameterException();

            String name=nv[0];
            String value=nv[1];
            Field f=cls.getDeclaredField(name);
            if(Modifier.isPrivate(f.getModifiers()))
                f.setAccessible(true);
            if(f.isAnnotationPresent(Save.class)){
                if(f.getType()==int.class) {
                    f.setInt(res, Integer.parseInt(value));
                }else if(f.getType()==String.class){
                    f.set(res,value);
                }
            }
        }
        return res;
    }
}
