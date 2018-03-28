package com.fh.ecology;

import java.io.IOException;
import java.rmi.RemoteException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.JdbcTemplate;

import com.fh.util.DateUtil;
import com.fh.util.PageData;

import cn.com.weaver.services.webservices.WorkflowServicePortType;
import cn.com.weaver.services.webservices.WorkflowServicePortTypeProxy;
import oracle.sql.Datum;
import weaver.workflow.webservices.WorkflowBaseInfo;
import weaver.workflow.webservices.WorkflowDetailTableInfo;
import weaver.workflow.webservices.WorkflowMainTableInfo;
import weaver.workflow.webservices.WorkflowRequestInfo;
import weaver.workflow.webservices.WorkflowRequestTableField;
import weaver.workflow.webservices.WorkflowRequestTableRecord;

public class ConFirmAutoCreate {

    WorkflowServicePortTypeProxy proxy = new WorkflowServicePortTypeProxy();

    private final static String ZJLXX = "2501"; // 总经理信箱

    WorkflowServicePortType service = proxy.getWorkflowServicePortType();

    private final static String JXSTSYJY = "2521"; // 经销商投诉与建议

    public static void main(String[] args) {
        try {
            Map<String, String> map = new HashMap<String, String>();
            map.put("ID", "121");
            map.put("jxs", "5561");
            map.put("ndyj", "测试");
            map.put("tpfj", "http://www.16boke.com/imagepro/upload/image/20161125/1480081414365043607.png,http://www.16boke.com/imagepro/upload/image/20161125/1480081389490036952.png");
            new ConFirmAutoCreate().CreateWorkflow(ZJLXX, map);
            // new ConFirmAutoCreate().CreateWorkflow( map);
        } catch (RemoteException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public static void uploudWorkFlow(Map<String, String> pd) {
        try {
            if (ZJLXX.equals(pd.get("workFlowId"))) {
                new ConFirmAutoCreate().CreateWorkflow(ZJLXX, pd);
                // 经销商投诉与建议
                return;
            }
            new ConFirmAutoCreate().CreateWorkflow(pd);
        } catch (RemoteException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void CreateWorkflow(String workFlowId, Map<String, String> map) throws IOException, RemoteException {
        try {
            Date now = new Date();

            WorkflowBaseInfo workflowBaseInfo = new WorkflowBaseInfo();
            workflowBaseInfo.setWorkflowId(workFlowId);// 流程workflowid
            WorkflowRequestInfo workflowRequestInfo = new WorkflowRequestInfo();

            workflowRequestInfo.setCreatorId(map.get("ID"));// 创建人ID
            workflowRequestInfo.setWorkflowBaseInfo(workflowBaseInfo);
            workflowRequestInfo.setRequestLevel("2");// 0 正常，1重要，2紧急
            workflowRequestInfo.setRequestName("总经理信箱"+DateUtil.getTime());
            WorkflowMainTableInfo workflowMainTableInfo = new WorkflowMainTableInfo();// 主表字段
            WorkflowRequestTableRecord[] requestRecords = new WorkflowRequestTableRecord[1];
            requestRecords[0] = new WorkflowRequestTableRecord();
            WorkflowRequestTableField[] workflowRequestTableFields = new WorkflowRequestTableField[6];
            for (int i = 0; i < 6; i++) {
                workflowRequestTableFields[i] = new WorkflowRequestTableField();
                workflowRequestTableFields[i].setEdit(new java.lang.Boolean(true));
                workflowRequestTableFields[i].setView(new java.lang.Boolean(true));
            }
            workflowRequestTableFields[0].setFieldName("creater");// 申请人
            workflowRequestTableFields[1].setFieldName("creatDate");// 申请日期
            workflowRequestTableFields[2].setFieldName("creatime");// 申请时间
            workflowRequestTableFields[3].setFieldName("jxs");// 经销商
            workflowRequestTableFields[4].setFieldName("ndyj");// 反馈意见
            workflowRequestTableFields[5].setFieldName("fjjl");// 反馈图片
            workflowRequestTableFields[0].setFieldValue(map.get("creater"));// 申请人
            workflowRequestTableFields[1].setFieldValue(DateUtil.getDay());// 申请日期
            workflowRequestTableFields[2].setFieldValue(DateUtil.format(now, "HH:mm"));// 申请时间
            workflowRequestTableFields[3].setFieldValue(map.get("jxs"));// 经销商
            workflowRequestTableFields[4].setFieldValue(map.get("ndyj"));// 反馈意见
            workflowRequestTableFields[5].setFieldValue(map.get("tpfj"));// 反馈图片

            requestRecords[0].setWorkflowRequestTableFields(workflowRequestTableFields);

            workflowMainTableInfo.setRequestRecords(requestRecords);
            workflowRequestInfo.setWorkflowMainTableInfo(workflowMainTableInfo);
            String requestId = service.doCreateWorkflowRequest(workflowRequestInfo, Integer.parseInt(map.get("ID")));

            System.out.println("requestId:" + requestId);

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void CreateWorkflow(Map<String, String> map) throws IOException, RemoteException {
        try {
            Date now = new Date();

            WorkflowBaseInfo workflowBaseInfo = new WorkflowBaseInfo();
            workflowBaseInfo.setWorkflowId(JXSTSYJY);// 流程workflowid
            WorkflowRequestInfo workflowRequestInfo = new WorkflowRequestInfo();

            workflowRequestInfo.setCreatorId(map.get("ID"));// 创建人ID
            workflowRequestInfo.setWorkflowBaseInfo(workflowBaseInfo);
            workflowRequestInfo.setRequestLevel("2");// 0 正常，1重要，2紧急
            workflowRequestInfo.setRequestName("经销商投诉与建议"+DateUtil.getTime());
            WorkflowMainTableInfo workflowMainTableInfo = new WorkflowMainTableInfo();// 主表字段
            WorkflowRequestTableRecord[] requestRecords = new WorkflowRequestTableRecord[1];
            requestRecords[0] = new WorkflowRequestTableRecord();
            WorkflowRequestTableField[] workflowRequestTableFields = new WorkflowRequestTableField[6];
            for (int i = 0; i < 6; i++) {
                workflowRequestTableFields[i] = new WorkflowRequestTableField();
                workflowRequestTableFields[i].setEdit(new java.lang.Boolean(true));
                workflowRequestTableFields[i].setView(new java.lang.Boolean(true));
            }
            workflowRequestTableFields[0].setFieldName("creater");// 申请人
            workflowRequestTableFields[1].setFieldName("creatDate");// 申请日期
            workflowRequestTableFields[2].setFieldName("creatime");// 申请时间
            workflowRequestTableFields[3].setFieldName("jxs");// 经销商
            workflowRequestTableFields[4].setFieldName("tsjy");// 反馈意见
            workflowRequestTableFields[5].setFieldName("tpfj");// 反馈图片
            workflowRequestTableFields[0].setFieldValue(map.get("creater"));// 申请人
            workflowRequestTableFields[1].setFieldValue(DateUtil.getDay());// 申请日期
            workflowRequestTableFields[2].setFieldValue(DateUtil.format(now, "HH:mm"));// 申请时间
            workflowRequestTableFields[3].setFieldValue(map.get("jxs"));// 经销商
            workflowRequestTableFields[4].setFieldValue(map.get("ndyj"));// 反馈意见
            workflowRequestTableFields[5].setFieldValue(map.get("tpfj"));// 反馈图片

            requestRecords[0].setWorkflowRequestTableFields(workflowRequestTableFields);

            workflowMainTableInfo.setRequestRecords(requestRecords);
            workflowRequestInfo.setWorkflowMainTableInfo(workflowMainTableInfo);
            String requestId = service.doCreateWorkflowRequest(workflowRequestInfo, Integer.parseInt(map.get("ID")));

            System.out.println("requestId:" + requestId);

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static String null2String(Object object) {
        if (object == null) {
            return "";
        } else {
            return object.toString();
        }
    }

}
