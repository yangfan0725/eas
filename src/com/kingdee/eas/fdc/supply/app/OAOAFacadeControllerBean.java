package com.kingdee.eas.fdc.supply.app;

import org.apache.axis.client.Call;
import org.apache.axis.client.Service;
import org.apache.axis.utils.XMLUtils;
import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.ejb.*;
import javax.xml.namespace.QName;
import javax.xml.rpc.ServiceException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.rmi.RemoteException;
import java.sql.Array;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.kingdee.bos.*;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.util.backport.Collections;
import com.kingdee.bos.workflow.metas.AssignFactory;
import com.kingdee.bos.metadata.IMetaDataPK;
import com.kingdee.bos.metadata.rule.RuleExecutor;
import com.kingdee.bos.metadata.MetaDataPK;
//import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.framework.ejb.AbstractEntityControllerBean;
import com.kingdee.bos.framework.ejb.AbstractBizControllerBean;
//import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.IObjectCollection;
import com.kingdee.bos.service.ServiceContext;
import com.kingdee.bos.service.IServiceContext;

import com.kingdee.bos.dao.IObjectCollection;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.contract.BankNumFactory;
import com.kingdee.jdbc.rowset.IRowSet;


public class OAOAFacadeControllerBean extends AbstractOAOAFacadeControllerBean
{
	  private static Logger logger =
	        Logger.getLogger("com.kingdee.eas.fdc.supply.app.OAOAFacadeControllerBean");
	  
	 @Override
		protected void _pushMessageTOOA(Context ctx)
				throws BOSException, SQLException, RemoteException, MalformedURLException, ServiceException {
//			appName,modelName,modelId,subject,link,type,key,param1,param2,targets,createTime
			FDCSQLBuilder builder=new FDCSQLBuilder();
			builder.appendSql("select FASSIGNID,FSUBJECT_L2,FPERSONUSERNAME_L1,FBIZOBJID,FPERSONUSERID,FCREATEDTIME from t_wfr_assign ");
			IRowSet rs=builder.executeQuery();
			List list=new ArrayList();
			ResultSetMetaData md=rs.getMetaData();//获取键名
			int columnCount=md.getColumnCount();//获取行数
			while(rs.next()){
					String modelId=rs.getString("FASSIGNID");
					String subject=rs.getString("FSUBJECT_L2");
					String FPERSONUSERNAME_L1=rs.getString("FPERSONUSERNAME_L1");
					String FBIZOBJID=rs.getString("FBIZOBJID");
					String FPERSONUSERID=rs.getString("FPERSONUSERID");
					Timestamp FCREATEDTIME=rs.getTimestamp("FCREATEDTIME");
					String createTime=FCREATEDTIME.toString();
					System.out.println(createTime);
//					获取modelName根据FBIZOBJID
					FDCSQLBuilder builder1=new FDCSQLBuilder();
					builder1.appendSql(" select FName from T_CON_ContractBill where FID=FBIZOBJID ");
					IRowSet rs1=builder1.executeQuery();
					String modelName=rs1.getString("FNAME");
//					获取用户编码 targets
					FDCSQLBuilder builder2=new FDCSQLBuilder();
					builder2.appendSql(" select FNUMBER from T_PM_User where FID=FPERSONUSERID");
					IRowSet rs2=builder2.executeQuery();
					String targets=rs2.getString("FNUMBER");				
									
					
//				 创建代办
					Service service = new Service();
					Call call = (Call) service.createCall();
					
					call.setTargetEndpointAddress(new java.net.URL("http://172.17.4.211:8080/sys/webservice/sysNotifyTodoWebService"));
					call.setOperationName("sendERPTodo");
					call.addParameter("arg0",org.apache.axis.encoding.XMLType.XSD_STRING,javax.xml.rpc.ParameterMode.IN);
				//	call.addParameter("arg1",org.apache.axis.encoding.XMLType.XSD_STRING,javax.xml.rpc.ParameterMode.IN);
					call.setReturnType(org.apache.axis.encoding.XMLType.XSD_STRING);
					call.setUseSOAPAction(true);
					JSONObject data = new JSONObject();

			        data.put("appName", "ERP");
			        data.put("modelName", modelName);
			        data.put("modelId", modelId);
			        data.put("subject", subject);
			        data.put("link", "");
			        data.put("type", 1);
			        data.put("key","" );
			        data.put("targets",targets); //00641 
			        data.put("createTime",createTime);
			    
					String result = (String) call.invoke(new Object[] {data.toString() });
					System.out.println(result);
				
			}
		}
	 
//代办置为已办成功
	 public void a() throws RemoteException, ServiceException, MalformedURLException{
			Service service = new Service();
			Call call = (Call) service.createCall();
		call.setTargetEndpointAddress(new java.net.URL("http://172.17.4.41:8080/sys/webservice/sysNotifyTodoWebService"));
		call.setOperationName("setERPTodoDone");
		call.addParameter("arg0",org.apache.axis.encoding.XMLType.XSD_STRING,javax.xml.rpc.ParameterMode.IN);
//		call.addParameter("arg1",org.apache.axis.encoding.XMLType.XSD_STRING,javax.xml.rpc.ParameterMode.IN);
		call.setReturnType(org.apache.axis.encoding.XMLType.XSD_STRING);
		call.setUseSOAPAction(true);
		JSONObject data = new JSONObject();

	    data.put("appName", "ydsp");
	    data.put("modelName", "test");
	    data.put("modelId", "testModelId"); 
	   
	    data.put("optType", 1);
	    data.put("key", "bb");
	   
	    data.put("targets","qwe"); //测试用00541

		String result = (String) call.invoke(new Object[] {data.toString() });
		System.out.println(result+"===============================================");
		}
	 
//	 创建代办成功
	 public void b() throws RemoteException, ServiceException, MalformedURLException, SQLException, BOSException {
		 FDCSQLBuilder builder=new FDCSQLBuilder();
			builder.appendSql("select FASSIGNID,FSUBJECT_L2,FPERSONUSERNAME_L1,FBIZOBJID,FPERSONUSERID,FCREATEDTIME from t_wfr_assign ");
			IRowSet rs=builder.executeQuery();
			List list=new ArrayList();
			ResultSetMetaData md=rs.getMetaData();//获取键名
			int columnCount=md.getColumnCount();//获取行数
			while(rs.next()){
					String modelId=rs.getString("FASSIGNID");
					String subject=rs.getString("FSUBJECT_L2");
					String FPERSONUSERNAME_L1=rs.getString("FPERSONUSERNAME_L1");
					String FBIZOBJID=rs.getString("FBIZOBJID");
					String FPERSONUSERID=rs.getString("FPERSONUSERID");
					Timestamp FCREATEDTIME=rs.getTimestamp("FCREATEDTIME");
					String createTime=FCREATEDTIME.toString();
					System.out.println(createTime);
//					获取modelName根据FBIZOBJID
					FDCSQLBuilder builder1=new FDCSQLBuilder();
					builder1.appendSql(" select FName from T_CON_ContractBill where FID=FBIZOBJID ");
					IRowSet rs1=builder1.executeQuery();
					String modelName=rs1.getString("FNAME");
//					获取用户编码 targets
					FDCSQLBuilder builder2=new FDCSQLBuilder();
					builder2.appendSql(" select FNUMBER from T_PM_User where FID=FPERSONUSERID");
					IRowSet rs2=builder2.executeQuery();
					String targets=rs2.getString("FNUMBER");				
									
					
//				 创建代办
					Service service = new Service();
					Call call = (Call) service.createCall();
					
					call.setTargetEndpointAddress(new java.net.URL("http://172.17.4.41:8080/sys/webservice/sysNotifyTodoWebService"));
					call.setOperationName("sendERPTodo");
					call.addParameter("arg0",org.apache.axis.encoding.XMLType.XSD_STRING,javax.xml.rpc.ParameterMode.IN);
				//	call.addParameter("arg1",org.apache.axis.encoding.XMLType.XSD_STRING,javax.xml.rpc.ParameterMode.IN);
					call.setReturnType(org.apache.axis.encoding.XMLType.XSD_STRING);
					call.setUseSOAPAction(true);
					JSONObject data = new JSONObject();

			        data.put("appName", "ERP");
			        data.put("modelName", modelName);
			        data.put("modelId", modelId);
			        data.put("subject", subject);
			        data.put("link", "");
			        data.put("type", 1);
			        data.put("key","" );
			        data.put("targets",targets); //00641 
			        data.put("createTime",createTime);
			    
					String result = (String) call.invoke(new Object[] {data.toString() });
					System.out.println(result);
				
			}
}

}