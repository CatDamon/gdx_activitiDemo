package activiti.demo;

import java.io.InputStream;
import java.util.List;
import java.util.zip.ZipInputStream;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.junit.Test;

/**
 * 请假流程例子
 * */
public class ActivitiDemo2 {
	ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();  
	
	/**
	 * 部署流程定义(zip)
	 * */
	@Test
	public void deploymentProcessDefinition () {
		//获取zip包的输入流
		InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream("bpmn/bpmn.zip");
		ZipInputStream zipInputStream = new ZipInputStream(inputStream);
		
		Deployment deploy = processEngine.getRepositoryService() //与流程定义和部署相关的Service
		             .createDeployment() //创建部署
		             .name("请假流程") //添加部署名称
		             .addZipInputStream(zipInputStream) //指定zip文件完成部署
		             .deploy(); //完成部署
		System.out.println("部署ID：" + deploy.getId());   //部署ID：17501
		System.out.println("部署名称：" + deploy.getName()); //部署名称：请假流程
		
	}
	
	/**
	 * 启动实例流程
	 * */
	@Test
	public void startProcess () {
		//定义流程的key
		String processDefinitionKey = "LeaveOfProcess";
		ProcessInstance instance = processEngine.getRuntimeService() //与正在执行的流程实例和执行对象相关的Service
					 .startProcessInstanceByKey(processDefinitionKey); //使用流程定义的key启动流程实例，key对应LeaveOfProcess.bpmn文件中id的属性值，使用key值启动，默认是按照最新版本的流程定义启动
		System.out.println("流程实例ID："+instance.getId());  // 流程实例ID：20001
		System.out.println("流程定义ID："+instance.getProcessDefinitionId());  //流程定义ID：LeaveOfProcess:1:17504

	}
	
	/**
	 * 查询个人任务
	 * 任务ID:20004
		任务名称:请假申请
		任务的创建时间:Tue Apr 25 10:18:32 CST 2017
		任务的办理人:anybody
		流程实例ID：20001
		执行对象ID:20001
		流程定义ID:LeaveOfProcess:1:17504
		########################################################
		任务ID:22504
		任务名称:请假申请
		任务的创建时间:Tue Apr 25 10:24:11 CST 2017
		任务的办理人:anybody
		流程实例ID：22501
		执行对象ID:22501
		流程定义ID:LeaveOfProcess:1:17504
		########################################################
		
		任务ID:25002
		任务名称:部门经理审核
		任务的创建时间:Tue Apr 25 11:31:09 CST 2017
		任务的办理人:部门经理
		流程实例ID：20001
		执行对象ID:20001
		流程定义ID:LeaveOfProcess:1:17504
		########################################################
		
		任务ID:27502
		任务名称:总经理审批
		任务的创建时间:Tue Apr 25 11:42:20 CST 2017
		任务的办理人:总经理
		流程实例ID：20001
		执行对象ID:20001
		流程定义ID:LeaveOfProcess:1:17504
		########################################################
	 * */
	@Test
	public void findMyPersonlTask () {
		//String assignee = "anybody"; //获取某节点操作人
		//String assignee = "部门经理";
		String assignee = "总经理";
		List<Task> list = processEngine.getTaskService() //与正在执行的任务相关的Service
					 .createTaskQuery() //创建任务查询对象
					 /**查询条件(where部分)*/
					 .taskAssignee(assignee)//指定个人任务查询，指定办理人  
//                   .taskCandidateUser(candidateUser)//组任务的办理人查询  
//                   .processDefinitionId(processDefinitionId)//使用流程定义ID查询  
//                   .processInstanceId(processInstanceId)//使用流程实例ID查询  
//                   .executionId(executionId)//使用执行对象ID查询  
                     /**排序*/  
                     .orderByTaskCreateTime().asc()//使用创建时间的升序排列  
                     /**返回结果集*/  
//                   .singleResult()//返回惟一结果集  
//                   .count()//返回结果集的数量  
//                   .listPage(firstResult, maxResults);//分页查询  
                     .list();//返回列表
		if(list!=null && list.size()>0){  
            for(Task task:list){  
                System.out.println("任务ID:"+task.getId());  
                System.out.println("任务名称:"+task.getName());  
                System.out.println("任务的创建时间:"+task.getCreateTime());  
                System.out.println("任务的办理人:"+task.getAssignee());  
                System.out.println("流程实例ID："+task.getProcessInstanceId());  
                System.out.println("执行对象ID:"+task.getExecutionId());  
                System.out.println("流程定义ID:"+task.getProcessDefinitionId());  
                System.out.println("########################################################");  
            }  
        } 
		
	}
	
	/**
	 * 完成办理任务
	 * */
	@Test
	public void completeMyPersonlTask () {
		//任务ID
		//String taskId = "20004";   //anybody
		//String taskId = "25002";   //部门经理
		String taskId = "27502";	//总经理
		processEngine.getTaskService()  //与正在执行任务管理相关的Service
					 .complete(taskId);
		System.out.println("完成任务，任务ID为：" + taskId);
	}
	
	/**
	 * 查询流程状态(判断正在执行，还是结束)
	 * */
	@Test
	public void isProcessEnd(){
		String processInstanceId = "20001"; 
		ProcessInstance pi = processEngine.getRuntimeService() //表示正在执行的流程实例和对象
					 .createProcessInstanceQuery() //创建流程实例查询
					 .processInstanceId(processInstanceId) //使用流程实例ID查询，即流程ID
					 .singleResult(); 
		 if(pi==null){  
		      System.out.println("流程已经结束");  
		 }  
		 else{  
		      System.out.println("流程没有结束");  
		 } 
		
	}
	
}
