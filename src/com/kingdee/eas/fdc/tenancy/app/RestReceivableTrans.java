package com.kingdee.eas.fdc.tenancy.app;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Hashtable;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.basedata.FDCDateHelper;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.basedata.util.FDCTransmissionHelper;
import com.kingdee.eas.fdc.sellhouse.MoneyDefineFactory;
import com.kingdee.eas.fdc.sellhouse.MoneyDefineInfo;
import com.kingdee.eas.fdc.tenancy.IRestReceivable;
import com.kingdee.eas.fdc.tenancy.RestReceivableFactory;
import com.kingdee.eas.fdc.tenancy.RestReceivableInfo;
import com.kingdee.eas.fdc.tenancy.TenBillOtherPayInfo;
import com.kingdee.eas.fdc.tenancy.TenancyBillFactory;
import com.kingdee.eas.fdc.tenancy.TenancyBillInfo;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.tools.datatask.AbstractDataTransmission;
import com.kingdee.eas.tools.datatask.core.TaskExternalException;
import com.kingdee.jdbc.rowset.IRowSet;
import com.kingdee.util.StringUtils;

public class RestReceivableTrans extends AbstractDataTransmission {

	@Override
	protected ICoreBase getController(Context ctx)
			throws TaskExternalException {
		// TODO Auto-generated method stub
		IRestReceivable factory = null;
		try {
			factory = RestReceivableFactory.getLocalInstance(ctx);
		} catch (BOSException e) {
			// TODO Auto-generated catch block 
			e.printStackTrace();
		}
		return factory;
	}

	public CoreBaseInfo transmit(Hashtable dataMap, Context ctx)
			throws TaskExternalException {
		// TODO Auto-generated method stub
		//导入时校验合同是否有效
		RestReceivableInfo rri = new RestReceivableInfo();
		
		int existsCount = -1;
		String contractName = dataMap.get("FTenancyBill_tenancyName")+"";
		if(StringUtils.isEmpty(contractName)){
			FDCTransmissionHelper.isThrow("租赁合同名称不能为空.");
		}
	    FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
	    builder.appendSql("select count(*) cnt from t_ten_tenancybill where ftenancyname=? and ftenancystate='Executing'");
	    builder.addParam(contractName);
	    try {
			IRowSet rs = builder.executeQuery();
			while(rs.next()){
				existsCount = rs.getInt("cnt");
			}
		} catch (BOSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(existsCount < 1){
			FDCTransmissionHelper.isThrow("不存名称为"+contractName+"租赁合同.");
		}
		
		//导入校验客户是否是合同上的客户
		 existsCount = -1;
		 String custName = dataMap.get("FTenancyBill_tenCustomerDes")+"";
		 
		 if(StringUtils.isEmpty(contractName)){
			 FDCTransmissionHelper.isThrow("客户不能为空.");
		 }
		 
		 builder.clear();
		 builder.appendSql("select count(*) cnt from t_ten_tenancybill where ftencustomerdes=? and ftenancystate='Executing'");
		 builder.addParam(custName);
		 
		  try {
				IRowSet rs = builder.executeQuery();
				while(rs.next()){
					existsCount = rs.getInt("cnt");
				}
			} catch (BOSException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if(existsCount < 1){
				FDCTransmissionHelper.isThrow("不存名称为"+custName+"客户.");
			}
		
		 String contractCustName = null;	
		 builder.clear();
		 builder.appendSql("select ftencustomerdes from t_ten_tenancybill where ftenancyname=? and  ftenancystate='Executing'");
		 builder.addParam(contractName);
		 try {
				IRowSet rs = builder.executeQuery();
				while(rs.next()){
					contractCustName = rs.getString("ftencustomerdes");
				}
			} catch (BOSException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if(!custName.equals(contractCustName)){
				FDCTransmissionHelper.isThrow("客户名称与租赁客户名称不能应.");
			}	
			
		try {
			TenancyBillInfo tenancyBill = TenancyBillFactory.getLocalInstance(
					ctx).getTenancyBillInfo(
					" where tenancyname='" + contractName + "'");
			rri.setTenancyBill(tenancyBill);
			String billNumber = dataMap.get("FNumber")+"";
			 if(StringUtils.isEmpty(billNumber)){
				 FDCTransmissionHelper.isThrow("客户不能为空.");
			 }
			rri.setNumber(billNumber);
		} catch (EASBizException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (BOSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		TenBillOtherPayInfo pay = new TenBillOtherPayInfo();
		pay.setRestReceivable(rri);
		if(dataMap.get("FOtherPayList_appAmount") == null){
			FDCTransmissionHelper.isThrow("应收金额不能为空.");
		}
		BigDecimal  apAmount = FDCHelper.toBigDecimal(dataMap.get("FOtherPayList_appAmount"));
		pay.setAppAmount(apAmount);
		if(dataMap.get("FOtherPayList$moneyDefine_name_l2") == null){
			FDCTransmissionHelper.isThrow("款项类型不能为空.");
		}
		String moneyName = dataMap.get("FOtherPayList$moneyDefine_name_l2")+"";
	    MoneyDefineInfo md =null;
		try {
			md = MoneyDefineFactory.getLocalInstance(ctx).getMoneyDefineInfo("where name='"+moneyName+"'");
		} catch (EASBizException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (BOSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(md == null){
			FDCTransmissionHelper.isThrow("款项类型不正确.");
		}
		pay.setMoneyDefine(md);
		String strApDate = dataMap.get("FOtherPayList_appDate")+"";
		if(StringUtils.isEmpty(strApDate)){
			FDCTransmissionHelper.isThrow("应收日期不能为空.");
		}
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd");
		
		try {
			pay.setAppDate(sdf.parse(strApDate));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			FDCTransmissionHelper.isThrow("应收日期格式不正确.");
		}
		
		String strStartDate =dataMap.get("FOtherPayList_startDate")+"";
		if( !StringUtils.isEmpty(strStartDate)){
			try {
				pay.setStartDate(sdf.parse(strStartDate));
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				FDCTransmissionHelper.isThrow("开始日期格式不正确.");
			}
		}
		String strEndDate =dataMap.get("FOtherPayList_endDate")+"";
		if(!StringUtils.isEmpty(strEndDate)){
			try {
				pay.setEndDate(sdf.parse(strEndDate));
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				FDCTransmissionHelper.isThrow("结束日期格式不正确.");
			}
		}
		String strBizDate =dataMap.get("FBizDate")+"";
		if(!StringUtils.isEmpty(strBizDate)){
			try {
				rri.setBizDate(sdf.parse(strBizDate));
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				FDCTransmissionHelper.isThrow("业务日期格式不正确.");
			}
		}
		rri.getOtherPayList().add(pay);
		
		return rri;
	}
	
	@Override
	public void submit(CoreBaseInfo coreBaseInfo, Context ctx)
			throws TaskExternalException {
		// TODO Auto-generated method stub
		super.submit(coreBaseInfo, ctx);
	}
	

}
