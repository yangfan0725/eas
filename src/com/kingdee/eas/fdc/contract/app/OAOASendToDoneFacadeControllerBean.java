package com.kingdee.eas.fdc.contract.app;

import org.apache.axis.client.Call;
import org.apache.axis.client.Service;
import org.apache.log4j.Logger;
import javax.ejb.*;
import javax.xml.rpc.ServiceException;

import java.net.MalformedURLException;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

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
import com.kingdee.eas.fdc.basedata.FDCConstants;
import com.kingdee.eas.fdc.basedata.FDCDateHelper;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.jdbc.rowset.IRowSet;


public class OAOASendToDoneFacadeControllerBean extends AbstractOAOASendToDoneFacadeControllerBean
{
    private static Logger logger =
        Logger.getLogger("com.kingdee.eas.fdc.contract.app.OAOASendToDoneFacadeControllerBean");

	@Override
	protected void _sengToDone(Context ctx) throws BOSException {
		// TODO Auto-generated method stub
		super._sengToDone(ctx);
		FDCSQLBuilder builder=new FDCSQLBuilder(ctx);
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//		记录已发送条数
		int send=0;
//		记录发送成功条数
		int success=0;
//		记录发送失败条数
		int failed=0;
//		一共待传条数 
		int waitToSend=0;
//		获取最新一次发送已办时间
		
		String modelId=null;
		String subject=null;
		String FPERSONUSERNAME_L1=null;
		String modelName=null;
		String targets=null;
		String endTime=null;
		String param1=null;
		String param2=null;
		String dateString=null;
		String docCreator=null;
		String url=null;
		
		builder.clear();
		builder.appendSql("/*dialect*/ select FASSIGNID,FSUBJECT_L2,FPERSONUSERNAME_L1,FPROCDEFNAME_L2,FPROCINSTID,FPROCDEFID,FPERSONUSERID," +
				"FCREATEDTIME,FLASTSTATETIME,FENDTIME,sendtimes from T_WFR_AssignDetail  where sended is null ");
		IRowSet rs=builder.executeQuery();
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
//				 System.out.println(modelId);
				}
				if(rs.getString("FPROCDEFNAME_L2")!=null){
					param2=rs.getString("FPROCDEFNAME_L2");
				}else{
					param2="";
				}
				if(rs.getString("FPROCDEFID")!=null){
					param1=rs.getString("FPROCDEFID");
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
				if(rs.getTimestamp("FENDTIME")!=null){
					Timestamp FCREATEDTIME=rs.getTimestamp("FENDTIME");
					long createTime=FCREATEDTIME.getTime();
					Date date = new Date(createTime);
					endTime=dateFormat.format(date);
				}
				if(rs.getString("FPERSONUSERID")!=null){
					String FPERSONUSERID=rs.getString("FPERSONUSERID");
//				获取用户编码 targets
					builder.clear();
					builder.appendSql("select fnumber as num from T_PM_User where FID='"+FPERSONUSERID+"' ");
//					System.out.println(builder.getTestSql().toString());
					IRowSet rs2=builder.executeQuery();
					while(rs2.next()){
//					System.out.println(rs2.getString("num"));
						targets=rs2.getString("num").toLowerCase();
					}
				}
				if(rs.getTimestamp("FLASTSTATETIME")!=null){
					Timestamp FCREATEDTIME=rs.getTimestamp("FLASTSTATETIME");
					long createTime1=FCREATEDTIME.getTime();
					Date date2 = new Date(createTime1);
					dateString=dateFormat.format(date2);
				}
				builder.clear();
				builder.appendSql("select furl from OAURL where type ='wsdl'");
				IRowSet rsu=builder.executeQuery();
				while(rsu.next()){
					url=rsu.getString("furl");
				}
				Service service = new Service();
				Call call = (Call) service.createCall();
				
				call.setTargetEndpointAddress(new java.net.URL(url));
				call.setOperationName("setERPTodoDone");
				call.addParameter("arg0",org.apache.axis.encoding.XMLType.XSD_STRING,javax.xml.rpc.ParameterMode.IN);
				call.setReturnType(org.apache.axis.encoding.XMLType.XSD_STRING);
				call.setUseSOAPAction(true);
				JSONObject data = new JSONObject();
				if(ctx.getAIS().equals("easdb")){ //easdb
					 data.put("appName", "DCEAS");
					 data.put("modelName", "金蝶地产");
				}else{
			        data.put("appName", "WYEAS");
			        data.put("modelName", "金蝶物业");
				}
				data.put("modelId", modelId);
		        data.put("optType", 1);
		        data.put("key","");
		        data.put("targets",targets); 
		        String dataJson = data.toString().replace("'", "");
		        
		        if(sendtimes<5){
		        try{
		        String result = (String) call.invoke(new Object[] {data.toString()});
//	        	System.out.println(data.toString());
	        	JSONObject jsonObject= (JSONObject) JSON.parse(result);
	        	 int code=Integer.parseInt(jsonObject.get("returnState").toString());
	        	 String errorMessage =null;
	        	 errorMessage = jsonObject.get("message").toString().replace("'", "");	
	        	 if(code==2){
//			        	if success --> update recordTime 
	        		    success=success+1;
			        	builder.clear();
						builder.appendSql(" update T_WFR_AssignDetail  set sended=1 where fassignid='"+modelId+"' ");
						builder.executeUpdate();
			        	
			        }else{
			        	sendtimes=sendtimes+1;
		        		 failed=failed+1;
		        		 builder.clear();
							builder.appendSql(" update t_wfr_assign set sendTimes = '"+sendtimes+"' where FASSIGNID='"+modelId+"' ");
							builder.executeUpdate();
							builder.clear();
				        	builder.appendSql("/*dialect*/ INSERT INTO OAOADATASEND O (O.FASSIGNID,O.FSTATE,O.TARGETS,O.subject,O.code,O.json,O.message)" +
				        			" values('"+modelId+"',"+11+",'"+targets+"','"+subject+"','"+code+"','"+dataJson+"','"+errorMessage+"')");
				        	builder.execute();
			        }
		        }catch(Exception e1){
		        	 e1.printStackTrace();
					 	builder.clear();
			        	builder.appendSql("/*dialect*/ INSERT INTO OAOADATASEND O (O.FASSIGNID,O.FSTATE,O.TARGETS,O.subject,O.code,O.json,O.message)" +
			        			" values('"+modelId+"',"+11+",'"+targets+"','"+subject+"','"+110+"','"+dataJson+"','"+e1.getMessage()+"')");
			        	builder.execute();
			        	failed=failed+1;
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