package activiti.demo;

import java.io.InputStream;
import java.util.List;
import java.util.zip.ZipInputStream;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.junit.Test;

/**
 * 
 * 
 * */

public class HelloWorldByZip {
	

    /** 
     * 获取默认的流程引擎实例 会自动读取activiti.cfg.xml文件  
     */  
    private ProcessEngine processEngine=ProcessEngines.getDefaultProcessEngine(); 
    
    @Test
    public void deployWithZip(){
    	InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream("bpmn.zip"); //获取指定文件资源流
    	ZipInputStream zip = new ZipInputStream(inputStream); //实例化zip输入流对象
    	
    	//获取部署对象
    	Deployment deploy = processEngine.getRepositoryService() //获取部署Service
    				.createDeployment()  //创建部署
    				.name("zip中helloworld流程") //命名流程
    				.addZipInputStream(zip) //添加zip的输入流
    				.deploy(); //部署
    	System.out.println("流程部署ID：" + deploy.getId()); 
    	System.out.println("流程部署NAME:" + deploy.getName());
    }			
    
    /**
     * 查询流程定义
     * */
    @Test
    public void findProcessDefinition () {
    	List<ProcessDefinition> list = processEngine.getRepositoryService() //获取部署Serivice
    				 .createProcessDefinitionQuery() //创建一个流程定义的查询
                     /**指定查询条件,where条件*/    
//                   .deploymentId(deploymentId)//使用部署对象ID查询    
//                   .processDefinitionId(processDefinitionId)//使用流程定义ID查询    
//                   .processDefinitionKey(processDefinitionKey)//使用流程定义的key查询    
//                   .processDefinitionNameLike(processDefinitionNameLike)//使用流程定义的名称模糊查询    
                         
                     /**排序*/    
                     .orderByProcessDefinitionVersion().asc()//按照版本的升序排列    
//                   .orderByProcessDefinitionName().desc()//按照流程定义的名称降序排列    
                         
                     /**返回的结果集*/    
                     .list();//返回一个集合列表，封装流程定义    
//                   .singleResult();//返回惟一结果集    
//                   .count();//返回结果集数量    
//                   .listPage(firstResult, maxResults);//分页查询    

    	if(list!=null && list.size()>0){    
            for(ProcessDefinition pd:list){    
                System.out.println("流程定义ID:"+pd.getId());//流程定义的key+版本+随机生成数    
                System.out.println("流程定义的名称:"+pd.getName());//对应helloworld.bpmn文件中的name属性值    
                System.out.println("流程定义的key:"+pd.getKey());//对应helloworld.bpmn文件中的id属性值    
                System.out.println("流程定义的版本:"+pd.getVersion());//当流程定义的key值相同的相同下，版本升级，默认1    
                System.out.println("资源名称bpmn文件:"+pd.getResourceName());    
                System.out.println("资源名称png文件:"+pd.getDiagramResourceName());    
                System.out.println("部署对象ID："+pd.getDeploymentId());    
                System.out.println("#########################################################");    
            }    
        }       
    }
    
    /**
     * 删除流程定义
     * */
    @Test
    public void deleteDefinition () {
        //使用部署ID，完成删除  
        String deploymentId = "1";  
        /** 
         * 不带级联的删除 
         *    只能删除没有启动的流程，如果流程启动，就会抛出异常 
         */  
//      processEngine.getRepositoryService()//  
//                      .deleteDeployment(deploymentId);  
          
        /** 
         * 级联删除 
         *    不管流程是否启动，都能可以删除 
         */  
        processEngine.getRepositoryService()//  
                        .deleteDeployment(deploymentId, true);  
        System.out.println("删除成功！");  
    }
    
    
    
    
}
