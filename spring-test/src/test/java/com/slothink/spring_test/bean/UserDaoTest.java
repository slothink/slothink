package com.slothink.spring_test.bean;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;

import static org.junit.Assert.assertEquals;

/**
 * Created with IntelliJ IDEA.
 * User: slothink
 * Date: 13. 8. 7
 * Time: 오후 5:16
 * To change this template use File | Settings | File Templates.
 */
public class UserDaoTest {

    @Test
    public void runByAnnotationConfigApplictionContext() {
        ApplicationContext context = new AnnotationConfigApplicationContext(BeanFactory.class);
        UserDao userDao = (UserDao)context.getBean("userDao");

        assertEquals("haha", userDao.say());
    }

    @Test
    public void runByGenericPathApplicationContext() {
        ApplicationContext context = new GenericXmlApplicationContext("applicationContext.xml");
        UserDao userDao = (UserDao)context.getBean("userDao");
        assertEquals("haha", userDao.say());
    }

    @Test
    public void runByClassPathApplicationContext() {
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml", UserDao.class);
        UserDao userDao = (UserDao)context.getBean("userDao");
        assertEquals("haha", userDao.say());
    }
}
