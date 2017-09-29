package com.jaxon.util;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.HashSet;
import java.util.Set;

import org.apache.log4j.Logger;

/**
 * 对象转换工具类
 * @category 用户不同对象但是有很多相同字段类型之间的转换，例如业务层返回的对象BO，转换成view层展示对象 VO
 * @author gag
 *
 */
public class ObjectTransformUtil
{
    private static final Logger log = Logger.getLogger(ObjectTransformUtil.class);

    /**
     * 获取set方法
     * @param <T>
     * @param field
     * @param cla
     * @return
     * @throws NoSuchMethodException
     * @throws SecurityException
     */
    
    public static <T> Method getSetMethod(Field field,Class<T> cla) {
        
        String methodName = null;
        int modifiers = field.getModifiers();
        if(field.getType() == boolean.class && field.getName().startsWith("is")){
            methodName = "set"+upFirstCharactor(field.getName().substring(2));
        }else{
            methodName = "set"+upFirstCharactor(field.getName());
        }
        Method method = null;
        try
        {
            method = cla.getDeclaredMethod(methodName, field.getType());
        } catch (NoSuchMethodException e)
        {
            log.warn("没有获取到 "+cla.getName()+" 的"+methodName +" 方法");
        } catch (SecurityException e)
        {
            log.warn("由于安全性，没有获取到 "+cla.getName()+" 的"+methodName +" 方法");
        }
        return method;
        
    }
    
    
    /**
     * 根据属性获取getter方法
     * @param field
     * @param cla
     * @return
     * @throws NoSuchMethodException
     * @throws SecurityException
     */
    public static <T> Method getGetMethod(Field field,Class<T> cla) {
        String methodName = null;
        if(field.getType() == boolean.class ){
            if(field.getName().startsWith("is")){
                methodName = field.getName();
            }else{
                methodName = "is"+upFirstCharactor(field.getName());
            }
            
        }else{
            methodName = "get"+upFirstCharactor(field.getName());
        }
        Method method = null;
        try
        {
            method = cla.getDeclaredMethod(methodName);
        } catch (NoSuchMethodException e)
        {
            //e.printStackTrace();
            log.warn("没有获取到 "+cla.getName()+" 的"+methodName +" 方法");
        } catch (SecurityException e)
        {
            //e.printStackTrace();
            log.warn("由于安全性，没有获取到 "+cla.getName()+" 的"+methodName +" 方法");
        }
        return method;
        
    }
    
    /**
     * 字符串第一个字母大写
     * @param str
     * @return
     */
    public static String upFirstCharactor(String str){
        if(str!=null && str!=""){
            str  = str.substring(0,1).toUpperCase()+str.substring(1);
        }
        return str;
    }
    
    /**
     * 从一个对象
     * @param sourceObject
     * @param targetClassType
     * @return
     */
    public static <T> T createOjectFromAnther(Object sourceObject,Class<T> targetClassType) {
        T target = null;
        try
        {
            target = targetClassType.newInstance();
        } catch (InstantiationException e)
        {
            e.printStackTrace();
        } catch (IllegalAccessException e)
        {
            e.printStackTrace();
        }

        Field[] fieldSource = sourceObject.getClass().getDeclaredFields();
        Set<String> fieldsNameSource = new HashSet<String>();
        for(Field f : fieldSource){
            fieldsNameSource.add(f.getName());
        }
        
        Field[] fieldTarget = targetClassType.getDeclaredFields();
        Set<String> fieldsNameTarget = new HashSet<String>();
        for(Field f : fieldTarget){
            fieldsNameTarget.add(f.getName());
        }
        
        for(String field: fieldsNameTarget){
            if(fieldsNameSource.contains(field)){//循环目标类属性，如果源类有这个属性则进行赋值
                
                Field f1 = null;
                Field f2 = null;
                try
                {
                    f1 = sourceObject.getClass().getDeclaredField(field);
                    f2 = targetClassType.getDeclaredField(field);
                } catch (NoSuchFieldException e)
                {
                    e.printStackTrace();
                } catch (SecurityException e)
                {
                    e.printStackTrace();
                }
                int modifiers = f1.getModifiers();
                if(f1.getType() == f2.getType() && !Modifier.isStatic(modifiers) && !Modifier.isFinal(modifiers)){//final 、static的属性不进行设值
                    Method getMethod = getGetMethod(f1, sourceObject.getClass());
                    Method setMethod = getSetMethod(f2, targetClassType);
                    
                    if(getMethod == null || setMethod == null)//没有的忽略掉，不强求
                        continue;
                    
                    Object result = null;
                    try
                    {
                        result = getMethod.invoke(sourceObject);
                        setMethod.invoke(target, result);
                    } catch (Exception e)
                    {
                        log.error(field +" 属性设值失败！");
                    }

                }
                
            }
        }
        return target;
    }
}
