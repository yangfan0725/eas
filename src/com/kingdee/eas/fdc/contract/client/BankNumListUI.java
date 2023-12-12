/**
 * output package name
 */
package com.kingdee.eas.fdc.contract.client;

import java.awt.event.*;
import java.rmi.RemoteException;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.apache.log4j.Logger;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.eas.base.attachment.FtpConfigFactory;
import com.kingdee.eas.fdc.basedata.FDCDataBaseInfo;
import com.kingdee.eas.fdc.basedata.FDCDateHelper;
import com.kingdee.eas.fdc.basedata.client.ContractTypeEditUI;
import com.kingdee.eas.fdc.contract.BankNumFactory;
import com.kingdee.eas.fdc.contract.BankNumInfo;
import com.kingdee.eas.fdc.contract.ChangeWFTypeFactory;
import com.kingdee.eas.fdc.contract.ChangeWFTypeInfo;
import com.kingdee.eas.fdc.contract.MarketProjectFactory;
import com.kingdee.eas.fdc.contract.MarketProjectInfo;
import com.kingdee.eas.fdc.contract.OAContractFacadeFactory;
import com.kingdee.eas.fdc.contract.WSPaymentBillFacadeFactory;
import com.kingdee.eas.fdc.sellhouse.WSSellHouseFacadeFactory;
import com.kingdee.eas.fdc.tenancy.GetTenSqlResultFacade;
import com.kingdee.eas.fdc.tenancy.GetTenSqlResultFacadeFactory;
import com.kingdee.eas.framework.*;
import javax.xml.namespace.QName;
import javax.xml.rpc.ParameterMode;
import javax.xml.rpc.ServiceException;

import org.apache.axis.client.Call;
import org.apache.axis.client.Service;
import org.apache.axis.encoding.XMLType;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
/**
 * output class name
 */
public class BankNumListUI extends AbstractBankNumListUI
{
    private static final Logger logger = CoreUIObject.getLogger(BankNumListUI.class);
    
    /**
     * output class constructor
     */
    public BankNumListUI() throws Exception
    {
        super();
//        String str="{'result':'03','easid':'AZ2aL1tSRmOC2+f6bVORgQ1t0fQ=','type':'01'}";
//        String rs=OAContractFacadeFactory.getRemoteInstance().acceptHandle(str);
//        MarketProjectInfo info=MarketProjectFactory.getRemoteInstance().getMarketProjectInfo(new ObjectUuidPK("Hx3iBxzFQqSduDVCdlkiQoLoiOI="));
//        Calendar cal = new GregorianCalendar();
//		cal.setTime(FDCDateHelper.getNextMonth(info.getAuditTime()));
//		cal.set(5, 15);
//		cal.set(11, 0);
//		cal.set(12, 0);
//		cal.set(13, 0);
//		cal.set(14, 0);
//		int day=FDCDateHelper.getDiffDays(cal.getTime(), new Date());
//        Service s=new Service();
//        Call call=null;
//		try {
//			call = (Call)s.createCall();
//		} catch (ServiceException e1) {
//			e1.printStackTrace();
//		}
//		call.setOperationName("login");
//        call.setTargetEndpointAddress("http://172.17.4.63:6888/ormrpc/services/EASLogin?wsdl");
//        call.setReturnType(new QName("urn:client","WSContext"));
//        call.setReturnClass(WSContext.class);
//        call.setReturnQName(new QName("","loginReturn"));
//
//        //超时
//        call.setTimeout(Integer.valueOf(1000*600000*60));
//        call.setMaintainSession(true);
//        //登陆接口参数
//        try {
//             WSContext rs=(WSContext) call.invoke(new Object[]{"webservice", "webservice", "eas", "easdb", "L2", Integer.valueOf(2)});
//             System.out.println("session:"+rs.getSessionId());
//        } catch (RemoteException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//        //清理
//        call.clearOperation();
//        //调用业务接口
//        call.setOperationName("sysCustomer");    
//        call.setTargetEndpointAddress("http://172.17.4.69:6888/ormrpc/services/WSWSSellHouseFacade?wsdl");
//        call.setReturnQName(new QName("","sysCustomerReturn"));   //接口返回
//        call.setTimeout(Integer.valueOf(1000*600000*60));
//        call.setMaintainSession(true);
//        //接口参数
//        JSONArray arrrs = new JSONArray();
//        for(int i=0;i<51;i++){
//        	JSONObject json = new JSONObject();   //单据头信息
//            json.put("bank", "");
//        	json.put("bankAccount", "");
//        	json.put("birth", "");
//        	json.put("bookedAddress", "");
//        	json.put("certificateNumber", "");
//        	json.put("certificateType", "其它");
//        	json.put("creditCode", "");
//        	json.put("firstDate", "2020-04-01 18:55:34");
//        	json.put("latestDate", "2020-04-01 18:55:35");
//        	json.put("reportDate", "2020-01-16 12:21:19");
//        	json.put("legalPerson", "");
//        	json.put("level", "C");
//        	json.put("mailAddress", "内蒙古自治区乌海市乌达区人民路4号");
//        	json.put("name", "王新华");
//        	json.put("number", "MT-B-025DC11.01-011111932"+i);
//        	json.put("officeTel", "");
//        	json.put("oneQd", "来电录入");
//        	json.put("phone", "13851901775");
//        	json.put("qdDate", "2020-03-09 10:44:37");
//        	json.put("qdPerson", "刘仲仲");
//        	json.put("recommend", "");
//        	json.put("recommendDate", "");
//        	json.put("saleMan", "30074");
//        	json.put("sellProject", "B-025DC05.01");
//        	json.put("sex", "0");
//        	json.put("tel", "");
//        	json.put("twoQd", "来电录入");
//        	json.put("type", "0");
//        	json.put("id", "MT-B-025DC11.01-10000931"+i);
//
//        	arrrs.add(json);
//        }
//    	WSSellHouseFacadeFactory.getRemoteInstance().sysCustomer(arrrs.toString());
////        JSONArray arrrs = new JSONArray();
////        JSONObject json = new JSONObject();   //单据头信息
////      json.put("saleMan", "1");
////  	json.put("number", "1");
////  	json.put("valid", "0");
////  	arrrs.add(json);
//        WSSellHouseFacadeFactory.getRemoteInstance().sysCustomer(arrrs.toString());
//    	
//        try {
//			String result=(String)call.invoke(new Object[]{json.toString()} );
//			System.out.println(result);
//		} catch (RemoteException e) {
//			e.printStackTrace();
//		}//发送请求
    }
    protected String getEditUIName() {
		return BankNumEditUI.class.getName();
	}
    protected FDCDataBaseInfo getBaseDataInfo() {
		return new BankNumInfo();
	}
	protected ICoreBase getBizInterface() throws Exception {
		return BankNumFactory.getRemoteInstance();
	}
	protected boolean isIgnoreCUFilter() {
		return true;
	}
	protected void refresh(ActionEvent e) throws Exception {
		this.tblMain.removeRows();
	}
	protected boolean isSystemDefaultData(int activeRowIndex){
		return false;
    }

}