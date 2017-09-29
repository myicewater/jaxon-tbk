package com.jaxon.util.spring;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public final class SpringBeanUtil  implements ApplicationContextAware
{
    
    private static ApplicationContext context;
    
    

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException
    {
        this.context = applicationContext;
    }
    
    public static Object getBean(String name){
        Object bean = context.getBean(name);
        return bean;
    }

}
