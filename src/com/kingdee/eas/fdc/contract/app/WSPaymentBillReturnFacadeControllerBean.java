package com.kingdee.eas.fdc.contract.app;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import javax.ejb.*;
import java.rmi.RemoteException;
import java.util.HashSet;
import java.util.Set;

import com.kingdee.bos.*;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.metadata.IMetaDataPK;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.rule.RuleExecutor;
import com.kingdee.bos.metadata.MetaDataPK;
//import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.framework.ejb.AbstractEntityControllerBean;
import com.kingdee.bos.framework.ejb.AbstractBizControllerBean;
//import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.IObjectCollection;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.service.ServiceContext;
import com.kingdee.bos.service.IServiceContext;

import java.lang.String;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fi.cas.BillStatusEnum;
import com.kingdee.eas.fi.cas.CashPamentBillUtil;
import com.kingdee.eas.fi.cas.PaymentBillCollection;
import com.kingdee.eas.fi.cas.PaymentBillFactory;
import com.kingdee.eas.fi.cas.PaymentBillInfo;
import com.kingdee.eas.fi.cas.SourceTypeEnum;

public class WSPaymentBillReturnFacadeControllerBean extends AbstractWSPaymentBillReturnFacadeControllerBean
{
    private static Logger logger =
        Logger.getLogger("com.kingdee.eas.fdc.contract.app.WSPaymentBillReturnFacadeControllerBean");

	public String _synPaymentBillReturn(Context ctx, String str)
			throws BOSException, EASBizException {
		JSONObject obj = JSONObject.fromObject(str);
		
		String SERIAL_NO_ERP=obj.getString("SERIAL_NO_ERP");
		String VOUCHER_STAT=obj.getString("VOUCHER_STAT");
		String BFS_BILL_NO=obj.getString("BFS_BILL_NO");
		
		JSONObject rs = new JSONObject();
		
		PaymentBillCollection col=PaymentBillFactory.getLocalInstance(ctx).getPaymentBillCollection("select * from where sourceFunction='"+SERIAL_NO_ERP+"'");
		if(col.size()>0){
			PaymentBillInfo pay=col.get(0);
			if(VOUCHER_STAT.equals("0")){
//				Set id=new HashSet();
//				id.add(pay.getId().toString());
				try {
//					if(pay.getBillStatus().equals(BillStatusEnum.AUDITED)){
//						PaymentBillFactory.getLocalInstance(ctx).payForBook(id,true);
//					}
					pay.setBankReturnInfo("资金系统付款成功");
	
					SelectorItemCollection selector = new SelectorItemCollection();
					selector.add("bankReturnInfo");
					
					PaymentBillFactory.getLocalInstance(ctx).updatePartial(pay, selector);
					
				} catch (Exception e) {
					e.printStackTrace();
					rs.put("BFS_BILL_NO", BFS_BILL_NO);
					rs.put("SERIAL_NO_ERP", SERIAL_NO_ERP);
					rs.put("STATUS", "1");
					rs.put("MESSAGE", e.getMessage());
					return rs.toString();
				}
			}else{
				try {
					pay.setIsCommittoBe(false);
	
					String srcId=pay.getSourceFunction().split("-")[0]+"-"+(Integer.parseInt(pay.getSourceFunction().split("-")[1])+1);
					
					pay.setSourceFunction(srcId);
					
					SelectorItemCollection selector = new SelectorItemCollection();
					selector.add("isCommittoBe");
					selector.add("sourceFunction");
					
					PaymentBillFactory.getLocalInstance(ctx).updatePartial(pay, selector);
	
//					PaymentBillInfo paymentBill = PaymentBillFactory.getLocalInstance(ctx).getPaymentBillInfo(new ObjectUuidPK(pay.getId()));
//					if(paymentBill.getSourceType().equals(SourceTypeEnum.FDC)&&BillStatusEnum.SUBMIT.equals(paymentBill.getBillStatus()) && !paymentBill.isIsNeedPay())
//						CashPamentBillUtil._cancelPay4FDC(ctx, paymentBill);
				} catch (Exception e) {
					e.printStackTrace();
					rs.put("BFS_BILL_NO", BFS_BILL_NO);
					rs.put("SERIAL_NO_ERP", SERIAL_NO_ERP);
					rs.put("STATUS", "1");
					rs.put("MESSAGE", e.getMessage());
					return rs.toString();
				}
			}
		}
//		else{
//			rs.put("BFS_BILL_NO", BFS_BILL_NO);
//			rs.put("SERIAL_NO_ERP", SERIAL_NO_ERP);
//			rs.put("STATUS", "1");
//			rs.put("MESSAGE", "付款单不存在！");
//			return rs.toString();
//		}
		rs.put("BFS_BILL_NO", BFS_BILL_NO);
		rs.put("SERIAL_NO_ERP", SERIAL_NO_ERP);
		rs.put("STATUS", "2");
		return rs.toString();
	}
    
    
}