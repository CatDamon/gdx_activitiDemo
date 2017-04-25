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
 * 请假流程例子--设置变量
 * */
public class ActivitiDemo3 {
	ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
	
	/**
	 * 部署流程定义(从InputStream)
	 * */
	@Test
	public void deploymentProcessDefinition_inputStream () {
		InputStream streambpmn = this.getClass().getResourceAsStream("/bpmn/LeaveOfProcess2.bpmn");
		InputStream streambpng = this.getClass().getResourceAsStream("/bpmn/LeaveOfProcess2.png");
		Deployment deploy = processEngine.getRepositoryService() //与流程定义和部署对象相关的Service
					 .createDeployment() //创建一个部署对象
					 .name("请假流程(设置变量)")
					 //使用资源文件的名称(要求，与资源文件的名称要一致)，和输入流完成部署
					 .addInputStream("LeaveOfProcess2.bpmn", streambpmn)
					 .addInputStream("LeaveOfProcess2.png", streambpng)
					 .deploy();
		System.out.println("部署ID："+ deploy.getId());
		System.out.println("部署名称："+ deploy.getName());
	}
	
	/**
	 * 启动流程实例
	 * */
	@Test
	public void startProcessInstance () {
		//流程定义的key
		String processDefinitionKey = "LeaveOfProcess2";
		ProcessInstance instance = processEngine.getRuntimeService() //与正在执行流程实例和执行对象相关的Service
					 .startProcessInstanceByKey(processDefinitionKey);
		System.out.println("流程实例ID："+ instance.getId());
		System.out.println("流程定义ID："+ instance.getProcessDefinitionId());
	}
	
	/**
	 * 设置流程变量
	 * */
	
}
