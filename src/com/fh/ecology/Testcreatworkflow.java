package com.fh.ecology;


import java.rmi.RemoteException;



import cn.com.weaver.services.webservices.WorkflowServicePortType;
import cn.com.weaver.services.webservices.WorkflowServicePortTypeProxy;

import weaver.workflow.webservices.WorkflowBaseInfo;
import weaver.workflow.webservices.WorkflowMainTableInfo;
import weaver.workflow.webservices.WorkflowRequestInfo;
import weaver.workflow.webservices.WorkflowRequestTableField;
import weaver.workflow.webservices.WorkflowRequestTableRecord;

public class Testcreatworkflow {
	static WorkflowServicePortTypeProxy proxy = new WorkflowServicePortTypeProxy();
	static WorkflowServicePortType service = proxy.getWorkflowServicePortType();
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
        WorkflowRequestTableField[] wrti = new WorkflowRequestTableField[6]; //字段信息       
        wrti[0] = new WorkflowRequestTableField();        
        wrti[0].setFieldName("creater");//        
        wrti[0].setFieldValue("6815");// 
        wrti[0].setView(true);//字段是否可见        
        wrti[0].setEdit(true);//字段是否可编辑
        
        wrti[1] = new WorkflowRequestTableField();         
        wrti[1].setFieldName("creatDate");//        
        wrti[1].setFieldValue("2018-02-02");
        wrti[1].setView(true);//字段是否可见        
        wrti[1].setEdit(true);//字段是否可编辑
        
        wrti[2] = new WorkflowRequestTableField();         
        wrti[2].setFieldName("creatTime");//        
        wrti[2].setFieldValue("17:00");  
        wrti[2].setView(true);//字段是否可见        
        wrti[2].setEdit(true);//字段是否可编辑
        
        wrti[3] = new WorkflowRequestTableField();         
        wrti[3].setFieldName("jxs");//附件1        
        wrti[3].setFieldValue("1");//附件地址         
        wrti[3].setView(true);//字段是否可见        
        wrti[3].setEdit(true);//字段是否可编辑
        
        wrti[4] = new WorkflowRequestTableField();         
        wrti[4].setFieldName("ndyj");//审批领导        
        wrti[4].setFieldValue("测试数据");
        wrti[4].setView(true);//字段是否可见        
        wrti[4].setEdit(true);//字段是否可编辑
      
        wrti[5] = new WorkflowRequestTableField();         
        wrti[5].setFieldName("fjjl");//事由        
        wrti[5].setFieldValue("图片地址");
        wrti[5].setView(true);//字段是否可见        
        wrti[5].setEdit(true);//字段是否可编辑
          
        WorkflowRequestTableRecord[] wrtri = new WorkflowRequestTableRecord[1];//主字段只有一行数据       
        wrtri[0] = new WorkflowRequestTableRecord();        
        wrtri[0].setWorkflowRequestTableFields(wrti);           
        WorkflowMainTableInfo wmi = new WorkflowMainTableInfo();        
        wmi.setRequestRecords(wrtri);        
       
              
        WorkflowBaseInfo wbi = new WorkflowBaseInfo();        
        wbi.setWorkflowId("2501");//workflowid 流程接口演示流程2016==38        
        WorkflowRequestInfo wri = new WorkflowRequestInfo();//流程基本信息            
        wri.setCreatorId("6815");//创建人id        
        wri.setRequestLevel("2");//0 正常，1重要，2紧急        
        wri.setRequestName("测试数据");//流程标题        
        wri.setWorkflowMainTableInfo(wmi);//添加主字段数据         
        wri.setWorkflowBaseInfo(wbi);        
        String requestId = null;
		try {
			requestId = service.doCreateWorkflowRequest(wri,6815);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        System.out.println("requestid:"+requestId );
        }
		
		

	

}
