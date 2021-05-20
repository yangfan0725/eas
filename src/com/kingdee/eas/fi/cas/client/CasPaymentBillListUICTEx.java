package com.kingdee.eas.fi.cas.client;

import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.net.URLEncoder;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.xml.namespace.QName;
import javax.xml.rpc.ServiceException;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.axis.client.Call;
import org.apache.axis.client.Service;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.swing.KDLayout;
import com.kingdee.bos.ctrl.swing.KDWorkButton;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.ui.face.IUIFactory;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.contract.BankNumCollection;
import com.kingdee.eas.fdc.contract.BankNumFactory;
import com.kingdee.eas.fdc.contract.PayReqUtils;
import com.kingdee.eas.fdc.contract.app.OaUtil;
import com.kingdee.eas.fdc.contract.client.ContractWithoutTextEditUI;
import com.kingdee.eas.fdc.contract.client.PayRequestBillEditUI;
import com.kingdee.eas.fi.cas.BillStatusEnum;
import com.kingdee.eas.fi.cas.PaymentBillFactory;
import com.kingdee.eas.fi.cas.PaymentBillInfo;
import com.kingdee.eas.ma.budget.BgControlFacadeFactory;
import com.kingdee.eas.ma.budget.IBgControlFacade;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.jdbc.rowset.IRowSet;
import com.kingdee.util.NumericExceptionSubItem;

public class CasPaymentBillListUICTEx extends CasPaymentBillListUI{
	public CasPaymentBillListUICTEx() throws Exception {
		super();
	}

	public void actionTraceUp_actionPerformed(ActionEvent e) throws Exception {
		checkSelected();
		int rowIndex = this.tblMain.getSelectManager().getActiveRowIndex();
		IRow row = this.tblMain.getRow(rowIndex);
		String id=row.getCell("id").getValue().toString();
		PaymentBillInfo info=PaymentBillFactory.getRemoteInstance().getPaymentBillInfo(new ObjectUuidPK(id));
		if(info.getContractBillId()!=null&&info.getFdcPayReqID()!=null){
			String className=null;
			if(PayReqUtils.isConWithoutTxt(info.getContractBillId())){
				id=info.getContractBillId();
				className=ContractWithoutTextEditUI.class.getName();
			}else{
				id=info.getFdcPayReqID();id=info.getFdcPayReqID();
				className=PayRequestBillEditUI.class.getName();
			}
			UIContext uiContext = new UIContext(this);
			uiContext.put("ID", id);
	        IUIFactory uiFactory = UIFactory.createUIFactory(UIFactoryName.MODEL);
	        IUIWindow uiWindow = uiFactory.create(className, uiContext,null,OprtState.VIEW);
	        uiWindow.show();
		}else{
			if(info.getSummary()!=null){
				String mtLoginNum = OaUtil.encrypt(SysContext.getSysContext().getCurrentUserInfo().getNumber());
				String s2 = "&MtFdLoinName=";
				StringBuffer stringBuffer = new StringBuffer();
				String oaid = info.getSummary();
	            String link = String.valueOf(stringBuffer.append(oaid).append(s2).append(mtLoginNum));
				
				Runtime.getRuntime().exec("rundll32 url.dll,FileProtocolHandler "+link);  
	    	}else{
	    		super.actionTraceUp_actionPerformed(e);
	    	}
		}
	}
	public void onLoad() throws Exception {
		super.onLoad();
		KDWorkButton btnReCal=new KDWorkButton();
		btnReCal.setText("传递资金系统付款");
		btnReCal.addActionListener(new java.awt.event.ActionListener() {
	            public void actionPerformed(java.awt.event.ActionEvent e) {
	                beforeActionPerformed(e);
	                try {
	                	btnReCal_actionPerformed(e);
	                } catch (Exception exc) {
	                    handUIException(exc);
	                } finally {
	                    afterActionPerformed(e);
	                }
	            }
	        });
		btnReCal.setIcon(EASResource.getIcon("imgTbtn_input"));
		
		this.toolBar.add(btnReCal);
		btnReCal.setBounds(new Rectangle(658, 36, 120, 19));
	}
	protected void btnReCal_actionPerformed(ActionEvent e) throws Exception {
		checkSelected();
		int rowIndex = this.tblMain.getSelectManager().getActiveRowIndex();
		IRow row = this.tblMain.getRow(rowIndex);
		String id = (String) row.getCell(this.getKeyFieldName()).getValue();
		try {
			FDCSQLBuilder builder=new FDCSQLBuilder();
			builder.appendSql("select furl from t_zjsc");
			IRowSet rs=builder.executeQuery();
			String url=null;
	        while(rs.next()){
	        	url=rs.getString("furl");
	        }
	        if(url!=null){
	        	SelectorItemCollection sel = new SelectorItemCollection();
				sel.add("*");
				sel.add("company.*");
				sel.add("payerAccountBank.*");
				sel.add("payerAccountBank.property.*");
				sel.add("payeeType.*");
				sel.add("feeType.*");
				sel.add("entries.*");
				sel.add("entries.expenseType.*");
				sel.add("entries.costCenter.*");
				PaymentBillInfo pay=PaymentBillFactory.getRemoteInstance().getPaymentBillInfo(new ObjectUuidPK(id), sel);
				
//				Set idSet=new HashSet();
//				idSet.add(pay.getId().toString());
//				PaymentBillFactory.getRemoteInstance().payForBook(idSet,true);
				
//				IBgControlFacade iBgControlFacade = BgControlFacadeFactory.getRemoteInstance();
//				pay.setPayDate(new Date());
//    			Map bgmap=iBgControlFacade.checkBudget(pay);
//    			if(!((Boolean)bgmap.get("isPass")).booleanValue()){
//    				FDCMsgBox.showWarning(this,bgmap.get("message").toString());
//    				return;
//    			}
				if(pay.isIsCommittoBe()){
					FDCMsgBox.showWarning(this,"已提交资金系统，不能传递！");
					return;
				}
				if(!pay.getBillStatus().equals(BillStatusEnum.PAYED)){
					FDCMsgBox.showWarning(this,"非已付款状态，不能传递！");
					return;
				}
				if(pay.getPayerAccountBank()==null){
					FDCMsgBox.showWarning(this,"付款账号不能为空！");
					return;
				}
				JSONObject json=new JSONObject();
				String srcId=null;
				if(pay.getSourceFunction()==null){
					srcId=pay.getId()+"-1";
					json.put("SERIAL_NO_ERP", srcId);
				}else{
					srcId=pay.getSourceFunction();
					json.put("SERIAL_NO_ERP", pay.getSourceFunction());
				}
//				else{
//					srcId=pay.getSourceFunction().split("-")[0]+"-"+(Integer.parseInt(pay.getSourceFunction().split("-")[1])+1);
//					json.put("SERIAL_NO_ERP", srcId);
//				}
				json.put("VOUCHER_NO_ERP", pay.getNumber());
				json.put("CORP_CODE", pay.getCompany().getNumber());
				if(pay.getPayerAccountBank()!=null){
					json.put("PAYER_ACC_NO", pay.getPayerAccountBank().getBankAccountNumber());
				}
				json.put("AMT", pay.getActPayAmt());
				
				json.put("PAYEE_CORP_CODE", pay.getPayeeNumber());
				json.put("PAYEE_ACC_NO", pay.getPayeeAccountBank());
				json.put("PAYEE_NAME", pay.getPayeeName());
				
				if(pay.getBankNumber()!=null&&!"".equals(pay.getBankNumber().trim())){
					json.put("PAYEE_CODE", pay.getBankNumber());
				}else{
					BankNumCollection bankNum=BankNumFactory.getRemoteInstance().getBankNumCollection("select number from where name='"+pay.getPayeeBank()+"'");
					if(bankNum.size()>0){
						json.put("PAYEE_CODE", bankNum.get(0).getNumber());
					}else{
						FDCMsgBox.showWarning(this,"收款银行："+pay.getPayeeBank()+" 维护错误，请维护正确的开户行全称名！");
						return;
					}
				}
				json.put("VOUCHER_TYPE", "34");
				
//				json.put("ABS", "EAS系统支付");
				if(pay.getFeeType()!=null){
					json.put("ABS", pay.getFeeType().getName());
				}else{
					json.put("ABS", "EAS系统支付");
				}
//				json.put("PURPOSE", pay.getUsage());
				
				json.put("DATA_SOURCE", SysContext.getSysContext().getDCNumber());
				
				if(pay.getPayeeName().length()>4){
					json.put("ISFORINDIVIDUAL", "0");
				}else{
					json.put("ISFORINDIVIDUAL", "1");
				}
				Service s=new Service();
				Call call=(Call)s.createCall();
				if(pay.getPayeeType()!=null&&pay.getPayeeType().getName().equals("公司")){
					 call.setOperationName(new QName("http://server.webservice.sdkg.erp.hibernate.byttersoft.com","erpAllocationJsonData	"));
					 call.setTargetEndpointAddress(url+"/erp/services/sdkgAllocationWebService?wsdl");
					 call.setReturnQName(new QName("http://server.webservice.sdkg.erp.hibernate.byttersoft.com","erpAllocationJsonDataResponse"));
					 
					 if(pay.getFeeType()!=null)
						 json.put("BIS_TYPE", pay.getFeeType().getNumber());
				}else{
					 call.setOperationName(new QName("http://server.webservice.sdkg.erp.hibernate.byttersoft.com","erpPaymentJsonData"));
					 call.setTargetEndpointAddress(url+"/erp/services/sdkgPaymentWebService?wsdl");
					 call.setReturnQName(new QName("http://server.webservice.sdkg.erp.hibernate.byttersoft.com","erpPaymentJsonDataResponse"));
				}
				call.setTimeout(Integer.valueOf(1000*600000*60));
		        call.setMaintainSession(true);
		        
		        
		        JSONArray arr=new JSONArray();
	        	arr.add(json);
	        	
		        String result=(String)call.invoke(new Object[]{arr.toString()} );
		         
		        JSONArray rso = JSONArray.fromObject(result);
				if(rso.getJSONObject(0).get("STATUS").equals("2")){
					SelectorItemCollection sic = new SelectorItemCollection();
					sic.add("sourceFunction");
					sic.add("isCommittoBe");
					pay.setSourceFunction(srcId);
					pay.setIsCommittoBe(true);
					PaymentBillFactory.getRemoteInstance().updatePartial(pay, sic);
					FDCMsgBox.showInfo(this,"传递成功！");
				}else{
					FDCMsgBox.showWarning(this,rso.getJSONObject(0).getString("MESSAGE"));
				}
	        }
		} catch (RemoteException e1) {
			FDCMsgBox.showWarning(this,e1.getMessage());
		} catch (SQLException e1) {
			e1.printStackTrace();
		} catch (ServiceException e1) {
			e1.printStackTrace();
		} catch (BOSException e1) {
			e1.printStackTrace();
		}
	}
	
}
