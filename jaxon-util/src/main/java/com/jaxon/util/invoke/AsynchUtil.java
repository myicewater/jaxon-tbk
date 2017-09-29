package com.jaxon.util.invoke;

import com.jaxon.exception.BusinessException;
import com.jaxon.util.spring.SpringBeanUtil;
import org.apache.log4j.Logger;

import java.lang.reflect.Method;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * 异步调用方法工具类
 */
public abstract class AsynchUtil
{

    private static final Logger log = Logger.getLogger(AsynchUtil.class);
    
    private static final ExecutorService es = Executors.newCachedThreadPool();
    
    public  <T> T asynchDo(){
        
        Future<T> result = es.submit(new Callable<T>()
        {

            @Override
            public T call() throws Exception
            {
                T t = busiDo();
                return t;
            }
            
        });
        
        T t2 = null;
        try
        {
            t2 = result.get();
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
        }
        catch (ExecutionException e)
        {
            e.printStackTrace();
        }
        return t2;
    }
    
    public  abstract <T> T busiDo();
    


    /**
     * 
     * @param cla 执行类的class
     * @param beanName spring中的beanName
     * @param methodName 调用的方法名称
     * @param arg 调用参数
     * @return 执行异常则返回null
     * @throws BusinessException 
     */
    public static Object asynchDo(Class cla,String beanName,String methodName,Param ...arg) throws BusinessException {
        Param[] param =null;
        Class[] argType = null;
        Object[] paramValueT = null;
        if(arg != null){
            param = arg.clone();
            argType = new Class[arg.length];
            paramValueT = new Object[arg.length];
            for(int i=0;i<arg.length;i++){
                argType[i] = param[i].getParmaType();
                paramValueT[i] = param[i].getParamValue();
            }
        }
        
        
        Method declaredMethod2 = null;
        try
        {
            declaredMethod2 = cla.getDeclaredMethod(methodName, argType);
        }
        catch (NoSuchMethodException e)
        {
            log.error(beanName +"没有对应的方法："+methodName,e);
            throw new BusinessException(beanName +"没有对应的方法："+methodName);
        }
        catch (SecurityException e)
        {
            e.printStackTrace();
            log.error(e);
        }
        final Object bean = SpringBeanUtil.getBean(beanName);
        
        final Method declaredMethod = declaredMethod2;
        final Object[] paramValue = paramValueT;
        Future<Object> result = es.submit(new Callable<Object>()
        {
            @Override
            public Object call() throws Exception
            {
                Object o = declaredMethod.invoke(bean, paramValue);
                return o;
            }

        });
        try
        {
            return result.get();
        }
        catch (InterruptedException e)
        {
            log.error("调用"+beanName+"的"+methodName+"被中断",e);
            throw new BusinessException("调用"+beanName+"的"+methodName+"被中断");
        }
        catch (ExecutionException e)//当调用方法中抛出异常时会触发 执行异常
        {
            log.error("调用"+beanName+"的"+methodName+"执行异常",e);
            throw new BusinessException("调用"+beanName+"的"+methodName+"执行异常");
        }

    }

    /**
     * @param args
     */
    public static void main(String[] args)
    {
        String r =  new AsynchUtil()
        {
            @Override
            public  String busiDo()
            {
                System.out.println("This is the real do !");
                return "haha";
            }
        }.asynchDo();
        
        System.out.println("result:"+r);
    }
    
    public static class AsynchParam{
        public Class cla;
        public String beanName;
        public String beanMethod;
        public Param[] param;
        public AsynchParam(Class cla, String beanName, String beanMethod, Param[] param)
        {
            super();
            this.cla = cla;
            this.beanName = beanName;
            this.beanMethod = beanMethod;
            this.param = param;
        }
    }
    
    public static class Param{
        private Object paramValue;
        private Class parmaType;
        public Param(Object paramValue,Class pt){
            this.paramValue = paramValue;
            this.parmaType = pt;
        }
        public Object getParamValue()
        {
            return paramValue;
        }
        public void setParamValue(Object paramValue)
        {
            this.paramValue = paramValue;
        }
        public Class getParmaType()
        {
            return parmaType;
        }
        public void setParmaType(Class parmaType)
        {
            this.parmaType = parmaType;
        }
        
        
        
    }
    

}
