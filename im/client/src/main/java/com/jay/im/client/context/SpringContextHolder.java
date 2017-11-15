package com.jay.im.client.context;

import java.util.Map;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Repository;

/**
 * @Type SpringContextHolder.java
 * @Desc 
 * @author jay
 * @date Jul 26, 2016 2:43:40 PM
 * @version 
 */
@Repository
public class SpringContextHolder implements ApplicationContextAware {

    private static ApplicationContext context;

    @Override
    public void setApplicationContext(ApplicationContext ctx) throws BeansException {
        SpringContextHolder.context = ctx;
    }

    public static ApplicationContext getApplicationContext() {
        return context;
    }

    @SuppressWarnings("unchecked")
    public static <T> T getBean(String name) {
        try{
            return (T) context.getBean(name);
        }catch (Exception e){
            return null;
        }
    }

    public static <T> Map<String, T> getBeansOfType(Class<T> type) {
        Map<String, T> springBeans = context.getBeansOfType(type);
        return springBeans;
    }

    public static String[] getBeanNamesForType(Class<?> type) {
        String[] springBeanNames = {};
        springBeanNames = context.getBeanNamesForType(type);
        return springBeanNames;
    }

    public static <T> T getSingleton(Class<T> type){
        Map<String, T> springBeans = context.getBeansOfType(type);
        for(Map.Entry<String, T> entry:  springBeans.entrySet()){
            return entry.getValue();
        }
        return null;
    }
}
