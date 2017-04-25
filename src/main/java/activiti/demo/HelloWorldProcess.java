package activiti.demo;

import java.util.List;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.junit.Test;

public class HelloWorldProcess {

	/**
	 * 获取默认的流程实例，自动读取activiti.cfg.xml文件
	 * */
	private ProcessEngine processEngine=ProcessEngines.getDefaultProcessEngine();  

	/**
	 * 部署流程定义
	 * */
	@Test
	public void deploy(){
		//获取部署对象
		Deployment deployment = processEngine.getRepositoryService() //获取部署Service
					.createDeployment() //创建部署
					.addClasspathResource("bpmn/HelloWord.bpmn") //从classPath中加载，一次只能加载一个
					.addClasspathResource("bpmn/HelloWord.png") //从classPath中加载，一次只能加载一个
					.name("helloworld流程") //给流程命名
					.deploy();  //完成部署
		System.out.println("流程部署ID："+deployment.getId());
		System.out.println("流程部署名称："+deployment.getName());
	}
	
	/**
	 * 开始流程
	 * */
	@Test
	public void startProcess(){
		ProcessInstance pi = processEngine.getRuntimeService() //获取运行Service
					 .startProcessInstanceByKey("myProcess"); // 数据库中流程定义表(act_re_prcdef)的KEY字段值；key对应对应 流程图里的process id的名字，使用Key值 启动，默认是按照最新版本的流程定义启动的    
		System.out.println("流程实例ID：" + pi.getId());
		System.out.println("流程定义ID：" + pi.getProcessDefinitionId());
	}
	
	/**
	 * 查看任务
	 * */
	@Test
	public void findTask(){
		//查询并返回任务
		List<Task> list = processEngine.getTaskService() //获取任务相关Service
					 .createTaskQuery() // 创建任务查询
					 .taskAssignee("dxgong") //指定某个人
					 .list();
		for (Task task : list) {
			System.out.println("任务ID:"+task.getId());  
	        System.out.println("任务名称："+task.getName());  
	        System.out.println("任务创建时间："+task.getCreateTime());  
	        System.out.println("任务委派人："+task.getAssignee());  
	        System.out.println("流程实例ID:"+task.getProcessInstanceId()); 
		}
	}
	
	/**
	 * 完成任务
	 * */
	public void completeTask () {
		processEngine.getTaskService().complete("2504"); //指定完成的任务ID
	}
}
