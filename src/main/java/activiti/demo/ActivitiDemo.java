package activiti.demo;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngineConfiguration;
import org.junit.Test;
import sun.applet.Main;

import java.lang.reflect.MalformedParameterizedTypeException;

/**
 * Hello world!
 *
 */
public class ActivitiDemo {

	 /**
     * 生成25张Activiti表
     */
    @Test
    public void testCreateTable() {
        // 引擎配置
        ProcessEngineConfiguration pec=ProcessEngineConfiguration.createStandaloneProcessEngineConfiguration();
        pec.setJdbcDriver("com.mysql.jdbc.Driver");
        pec.setJdbcUrl("jdbc:mysql://localhost:3306/db_activiti");
        pec.setJdbcUsername("root");
        pec.setJdbcPassword("root");

        /**
         * false 不能自动创建表
         * create-drop 先删除表再创建表
         * true 自动创建和更新表  
         */
        pec.setDatabaseSchemaUpdate(ProcessEngineConfiguration.DB_SCHEMA_UPDATE_TRUE);
         
        // 获取流程引擎对象
        ProcessEngine processEngine=pec.buildProcessEngine();
    }
    
    /** 
     * 使用xml配置 简化 
     */  
    @Test  
    public void testCreateTableWithXml(){  
        // 引擎配置  
         ProcessEngineConfiguration pec=ProcessEngineConfiguration             .createProcessEngineConfigurationFromResource("activiti.cfg.xml");

        // 获取流程引擎对象
        ProcessEngine processEngine=pec.buildProcessEngine();  
    }


}
