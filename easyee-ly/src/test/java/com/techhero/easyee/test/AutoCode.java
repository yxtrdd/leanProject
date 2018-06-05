package com.techhero.easyee.test;

import cn.easyproject.easyee.auto.generator.EasyAutoModule;
import cn.easyproject.easyee.auto.generator.SMCodeGenerator;

/**
 * description: 自动化构造器 <br/>  
 * date: 2017年7月27日 上午10:43:11 <br/>  
 * author: gaojx  <br/> 
 * copyright: 北京志诚泰和信息技术有限公司
 */
public class AutoCode {
    public static void main(String[] args) {
        // EasyEE Auto

        // Generate all the classes under the entity package
      // String entityPackage = "com.techhero.kkxfd.dmgl.entity";
      //  new SMCodeGenerator().generator(SysDict.class);

        // Generates the specified class
        new SMCodeGenerator().generator(new EasyAutoModule[]{
                EasyAutoModule.CONTROLLER_CLASS, // Controller/Action 控制器类
                EasyAutoModule.CRITERIA_CLASS, // Criteria
                EasyAutoModule.MYBATIS_DAO_INTERFACE, // MyBatis DAO interface
                EasyAutoModule.MYBATIS_MAPPER_XML, // MyBatis mapper.xml
                EasyAutoModule.PAGE, // View page
                EasyAutoModule.SERVICE_INTERFACE, // Service interface
                EasyAutoModule.SERVICE_IMPLEMENTS // Server implements
        },Object.class);
       //new GenEntityTool("ZC_FJGYJRQ","com.techhero.kkxfd.fjgl.entity");
        System.out.println("Generator work done.");

    }

}
