package com.kingdee.eas.basedata.org.client;

import java.awt.event.ActionEvent;
import java.rmi.RemoteException;
import java.sql.SQLException;

import javax.xml.namespace.QName;
import javax.xml.rpc.ServiceException;

import org.apache.axis.client.Call;
import org.apache.axis.client.Service;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.basedata.org.CompanyOrgUnitCollection;
import com.kingdee.eas.basedata.org.CompanyOrgUnitFactory;
import com.kingdee.eas.basedata.org.CompanyOrgUnitInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fi.cas.PaymentBillFactory;
import com.kingdee.eas.util.client.MsgBox;
import com.kingdee.jdbc.rowset.IRowSet;
import com.kingdee.util.NumericExceptionSubItem;

public class OUEditUICTEx extends OUEditUI{

	public OUEditUICTEx() throws Exception {
		super();
	}
	public void actionSubmit_actionPerformed(ActionEvent e) throws Exception {
		super.actionSubmit_actionPerformed(e);
		if(this.editData.getId()!=null){
			CompanyOrgUnitCollection col = CompanyOrgUnitFactory.getRemoteInstance().getCompanyOrgUnitCollection("select * from where id='"+this.editData.getId()+"'");
			if(col.size()>0&&col.get(0).isIsBizUnit()){
				try {
					FDCSQLBuilder builder=new FDCSQLBuilder();
					builder.appendSql("select furl from t_zjsc");
					IRowSet rs=builder.executeQuery();
					String url=null;
			        while(rs.next()){
			        	url=rs.getString("furl");
			        }
				    if(url!=null){
						 JSONArray arr=new JSONArray();
						
						 JSONObject json=new JSONObject();
						 json.put("CORP_CODE", col.get(0).getNumber());
						 json.put("CORP_NAME", col.get(0).getName());
						 
						 json.put("DATA_SOURCE", SysContext.getSysContext().getDCNumber());
						 
						 arr.add(json);
						 
						 Service s=new Service();
				         Call call=(Call)s.createCall();
				         call.setOperationName(new QName("http://server.webservice.sdkg.erp.hibernate.byttersoft.com","erpCorpJsonData"));
				         call.setTargetEndpointAddress(url+"/erp/services/sdkgCorpWebService?wsdl");
				         call.setReturnQName(new QName("http://server.webservice.sdkg.erp.hibernate.byttersoft.com","erpCorpJsonDataResponse"));
				         call.setTimeout(Integer.valueOf(1000*600000*60));
				         call.setMaintainSession(true);
				         String result=(String)call.invoke(new Object[]{arr.toString()} );
				         
				         JSONArray rso = JSONArray.fromObject(result);
				         
						 if(rso.getJSONObject(0).get("STATUS").equals("1")){
							 MsgBox.showWarning(this,rso.getJSONObject(0).getString("MESSAGE"));
						 }
				    }
				}catch (BOSException e1) {
					e1.printStackTrace();
				}  catch (SQLException e1) {
					e1.printStackTrace();
				} catch (ServiceException e1) {
					e1.printStackTrace();
				} catch (RemoteException e1) {
					e1.printStackTrace();
				}
			}
		}
	}

}
