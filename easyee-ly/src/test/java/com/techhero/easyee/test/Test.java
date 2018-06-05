package com.techhero.easyee.test;

import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;


@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration 
@ContextConfiguration(locations = { "classpath:spring/ApplicationContext.xml"})
public class Test {
    
    @org.junit.Test
    public void testMethod() {
        
    }
}
  
