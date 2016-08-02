package com.mrlzy.shiro.plugin.spring;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.support.ManagedMap;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.support.ClassPathXmlApplicationContext;


public class SpringBeanLoaderAware implements ApplicationContextAware {
	private static Log log = LogFactory.getLog(SpringBeanLoaderAware.class);
	private static ApplicationContext applicationContext=null;

	
	private static  synchronized void initApplicationContext()  {
		try {
			 if(applicationContext!=null)return;
			 applicationContext = new ClassPathXmlApplicationContext(new String[]{"config/spring.xml"});
			 log.info("ApplicationContext is init with SpringBeanLoader"+applicationContext);

		} catch (Exception e) {
			System.setProperty("APPLICATIONCONTEXT_SUCCESS", "0");
			e.printStackTrace();
			log.error("Spring init failed\n"+e.getMessage());
			System.exit(0);
		}
	}

	
	public static ApplicationContext getApplicationContext() {
		if(applicationContext==null )initApplicationContext();
		return applicationContext;
	}
	
	
	public  static  boolean   isApplicationContextLoad(){
		return applicationContext!=null;
	}

	
	public static Object getSpringBean(String pBeanId) {
		Object springBean = null;
		try {
			springBean = getApplicationContext().getBean(pBeanId);
		} catch (NoSuchBeanDefinitionException e) {
			log.error(e.getMessage());
		}
		return springBean;
	}
	
	

	public void setApplicationContext(ApplicationContext acx)
			throws BeansException {
		if(applicationContext==null){
			log.info("ApplicationContext is init with DispatcherServlet");
			applicationContext=acx;	
			 
		}else{
			String message="The ApplicationContext  initialized 2! you can't use  getSpringBean in static,"
					+ "such as[ private static Object obj=SpringBeanLoader.getSpringBean(XXX) ] ";
			try {
				throw new Exception(message);
			} catch (Exception e) {
				log.error(e.getMessage());
			}
			System.exit(0);
		}
	}

}
