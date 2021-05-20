package com.kingdee.eas.fdc.contract.app;

import org.apache.axis.client.Call;
import org.apache.axis.client.Service;
import org.apache.log4j.Logger;
import javax.ejb.*;
import javax.xml.rpc.ServiceException;
import java.text.ParseException;

import java.net.MalformedURLException;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.kingdee.bos.*;
import com.kingdee.bos.util.BOSObjectType;
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
import com.kingdee.cbos.process.vm.internal.t.a.Int;
import com.kingdee.eas.fdc.basedata.FDCConstants;
import com.kingdee.eas.fdc.basedata.FDCDateHelper;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.jdbc.rowset.IRowSet;


public class OAOASendToDoFacadeControllerBean extends AbstractOAOASendToDoFacadeControllerBean
{
    private static Logger logger =
        Logger.getLogger("com.kingdee.eas.fdc.contract.app.OAOASendToDoFacadeControllerBean");

	@Override
	protected void _sendToDo(Context ctx) throws BOSException {

		// TODO Auto-generated method stub
		super._sendToDo(ctx);
		
		FDCSQLBuilder builder=new FDCSQLBuilder(ctx); 
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		builder.appendSql("/*dialect*/ select FASSIGNID,FSUBJECT_L2,FPERSONUSERNAME_L1,FPROCDEFID,FPROCINSTID,FPERSONUSERID," +
				"FPROCDEFNAME_L2,FPERSONUSERID,FCREATEDTIME,sendtimes from t_wfr_assign where fstate in (1,32) and sended is null");
		
		IRowSet rs=builder.executeQuery();
		String modelId=null;
		String subject=null;
		String FPERSONUSERNAME_L1=null;
		String modelName=null;
		String targets=null;
		String param1=null;
		String param2=null;
		String dateString=null;
		String fPersonId=null;
		String docCreator=null;
		String url=null;
		String link=null;
		String errorMessage=null;

//		记录已发送条数
		int send=0;
//		记录发送成功条数
		int success=0;
//		记录发送失败条数
		int failed=0;
//		一共待传条数
		int waitToSend=0;
		List<String> list=null;
		try {
			while(rs.next()){
				send=send+1;
//				数据传输次数
				int sendtimes=0;
				if(rs.getString("sendtimes")!=null){
					sendtimes=rs.getInt("sendtimes");
				}
				if(rs.getString("FASSIGNID")!=null){
					 modelId=rs.getString("FASSIGNID");
				}
				if(rs.getString("FSUBJECT_L2")!=null){
					subject=rs.getString("FSUBJECT_L2");
				}
				if(rs.getString("FPROCINSTID")!=null){
					docCreator="";
					param1=rs.getString("FPROCINSTID");
					builder.clear();
					builder.appendSql("select tpu.fnumber as num from t_pm_user tpu left join T_WFR_ProcInst twp on tpu.fid=twp.FINITIATORID where twp.FPROCINSTID='"+param1+"'");
				
					IRowSet rs2=builder.executeQuery();
							while(rs2.next()){
								docCreator=rs2.getString("num").toLowerCase();
							}
				}
				if(rs.getString("FPERSONUSERNAME_L1")!=null){
					FPERSONUSERNAME_L1=rs.getString("FPERSONUSERNAME_L1");
				}
				if(rs.getString("FPROCDEFNAME_L2")!=null){
					param2=rs.getString("FPROCDEFNAME_L2");
				}else{
					param2="";
				}
				if(rs.getTimestamp("FCREATEDTIME")!=null){
					Timestamp FCREATEDTIME=rs.getTimestamp("FCREATEDTIME");
					long createTime1=FCREATEDTIME.getTime();
					Date date2 = new Date(createTime1);
					dateString=dateFormat.format(date2);
				}
				if(rs.getString("FPERSONUSERID")!=null){
					String FPERSONUSERID=rs.getString("FPERSONUSERID");
//				获取用户编码 targets
					builder.clear();
					builder.appendSql("/*dialect*/ select fnumber as num from T_PM_User where FID='"+FPERSONUSERID+"' ");
//					System.out.println(builder.getTestSql().toString());
					IRowSet rs2=builder.executeQuery();
					while(rs2.next()){
						targets=rs2.getString("num").toLowerCase();
					}
				}
				builder.clear();
				builder.appendSql("select furl from OAURL where type ='wsdl'");
				IRowSet rsu=builder.executeQuery();
				while(rsu.next()){
					url=rsu.getString("furl");
				}
				builder.clear();
				builder.appendSql("select furl from OAURL where type ='login'");
				IRowSet rsl=builder.executeQuery();
				while(rsl.next()){
					link=rsl.getString("furl");
				}
//				 创建代办
				Service service = new Service();
				Call call = (Call) service.createCall();
				
				call.setTargetEndpointAddress(new java.net.URL(url));
				call.setOperationName("sendERPTodo");
				call.addParameter("arg0",org.apache.axis.encoding.XMLType.XSD_STRING,javax.xml.rpc.ParameterMode.IN);		
				call.setReturnType(org.apache.axis.encoding.XMLType.XSD_STRING);
				call.setUseSOAPAction(true);
				JSONObject data = new JSONObject();
				if(ctx.getAIS().equals("easdb")){  //easdb
					 data.put("appName", "DCEAS");
					 data.put("modelName", "金蝶地产");
				}else{
			        data.put("appName", "WYEAS");
			        data.put("modelName", "金蝶物业");
				}
//		        data.put("modelName", "金蝶地产");
//		        data.put("appName", "DCEAS");
		        data.put("param1", param1);
		        data.put("param2", param2);
		        data.put("modelId", modelId);
		        data.put("subject", subject);
		        data.put("link", link);
		        data.put("type", 1);
		        data.put("key","" );
		        data.put("docCreator",docCreator);
		        data.put("targets",targets); //00641 
		        data.put("createTime",dateString);
				String dataJson = data.toString().replace("'", "");
				
				if(sendtimes<5){
				try{
				String result = (String) call.invoke(new Object[] {data.toString() });
//	        	System.out.println(data.toString());
	        	JSONObject jsonObject= (JSONObject) JSON.parse(result);
	        	 int code=Integer.parseInt(jsonObject.get("returnState").toString());
	        	 errorMessage = jsonObject.get("message").toString().replace("'", "");	
	        	 if(code==2){       //means success then updte sended=1, sendtimes donot update
	        		 success=success+1;
	        		    builder.clear();
						builder.appendSql(" update t_wfr_assign set sended = 1 where FASSIGNID='"+modelId+"' ");
						builder.executeUpdate();
	        	 }else{// when failed update set sendtimes+1,sended donot update
	        		 sendtimes=sendtimes+1;
	        		 failed=failed+1;
	        		 builder.clear();
						builder.appendSql(" update t_wfr_assign set sendTimes = '"+sendtimes+"' where FASSIGNID='"+modelId+"' ");
						builder.executeUpdate();
						builder.clear();
			        	builder.appendSql("/*dialect*/ INSERT INTO OAOADATASEND O (O.FASSIGNID,O.FSTATE,O.TARGETS,O.subject,O.code,O.json,O.message)" +
			        			" values('"+modelId+"',"+00+",'"+targets+"','"+subject+"','"+code+"','"+dataJson+"','"+errorMessage+"')");
			        	builder.execute();
	        	 }
				}catch(Exception e){
					    e.printStackTrace();
					 	builder.clear();
			        	builder.appendSql("/*dialect*/ INSERT INTO OAOADATASEND O (O.FASSIGNID,O.FSTATE,O.TARGETS,O.subject,O.code,O.json,O.message)" +
			        			" values('"+modelId+"',"+00+",'"+targets+"','"+subject+"','"+110+"','"+dataJson+"','"+e.getMessage()+"')");
			        	builder.execute();
			        	failed=failed+1;
				}
//				 sleep for 2seconds
				 try {
		                Thread.currentThread().sleep(2000);
		            } catch (InterruptedException e) {
		                e.printStackTrace();
		            }
				}
			}
			System.out.println("一共发送数量："+send);
			System.out.println("成功数量："+success);
		} catch (Exception e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		
	}    
}