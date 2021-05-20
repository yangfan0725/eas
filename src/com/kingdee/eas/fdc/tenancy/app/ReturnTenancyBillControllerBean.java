package com.kingdee.eas.fdc.tenancy.app;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.tenancy.ReturnTenancyBillInfo;
import com.kingdee.eas.fdc.tenancy.ReturnTenancyContractCollection;
import com.kingdee.eas.fdc.tenancy.ReturnTenancyContractInfo;
import com.kingdee.eas.fdc.tenancy.ReturnTenancyEntryCollection;
import com.kingdee.eas.fdc.tenancy.ReturnTenancyEntryInfo;
import com.kingdee.eas.fdc.tenancy.ReturnTenancyRentEntryCollection;
import com.kingdee.eas.fdc.tenancy.ReturnTenancyRentEntryInfo;
import com.kingdee.jdbc.rowset.IRowSet;

public class ReturnTenancyBillControllerBean extends
		AbstractReturnTenancyBillControllerBean {
	private static Logger logger = Logger
			.getLogger("com.kingdee.eas.fdc.tenancy.app.ReturnTenancyBillControllerBean");

	@Override
	protected IObjectPK _save(Context ctx, IObjectValue model)
			throws BOSException, EASBizException {
		ReturnTenancyBillInfo returnTenancyBill = calcReturnRate(ctx,model);
		return super._save(ctx, returnTenancyBill);
	}

	@Override
	protected IObjectPK _submit(Context ctx, IObjectValue model)
			throws BOSException, EASBizException {
		ReturnTenancyBillInfo returnTenancyBill = calcReturnRate(ctx,model);
		return super._submit(ctx, returnTenancyBill);
	}
	
	private ReturnTenancyBillInfo calcReturnRate(Context ctx,IObjectValue model){
		ReturnTenancyBillInfo returnTenancyBill = (ReturnTenancyBillInfo) model;
		BigDecimal dealAmt = FDCHelper.ZERO;
		FDCSQLBuilder builder  = new FDCSQLBuilder(ctx);
		builder.appendSql("select sg.fdealtotalamount dealAmt,sg.fcustomernames custName from t_she_signmanage sg where froomid = ? ");
		builder.addParam(returnTenancyBill.getRoom().getId().toString());
		IRowSet rs;
		try {
			rs = builder.executeQuery();
			while(rs.next()){
				dealAmt = rs.getBigDecimal(1);
			}
		} catch (BOSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		

		ReturnTenancyEntryCollection entrys = returnTenancyBill.getEntry();
		ReturnTenancyEntryCollection newEntrys = new ReturnTenancyEntryCollection();
		StringBuffer str = new StringBuffer();
		Integer key = null;
		ReturnTenancyEntryInfo entry = null;
		BigDecimal amt = FDCHelper.ZERO;
		BigDecimal rent = FDCHelper.ZERO;
		BigDecimal rate = FDCHelper.ZERO;
		for (int k = 0; k < entrys.size(); k++) {
			entry = entrys.get(k);
			amt = entry.getAmtOfReturn()==null?FDCHelper.ZERO:entry.getAmtOfReturn();
			key = entry.getYear();
			if(dealAmt.compareTo(FDCHelper.ZERO)!= 0){
				rate = FDCHelper.divide(amt,FDCHelper.divide(dealAmt,0.95,4,BigDecimal.ROUND_HALF_UP),4,BigDecimal.ROUND_HALF_UP);
				entry.setRateOfReturn(rate);
			}
			newEntrys.add(entry);
			BigDecimal percent = FDCHelper.multiply(rate, new BigDecimal("100.00"));
			BigDecimal dpercent = FDCHelper.divide(percent,FDCHelper.ONE,2,BigDecimal.ROUND_HALF_UP);;
			str.append(key+"Äê:"+dpercent+"%;");
		}
		
		returnTenancyBill.getEntry().clear();
		returnTenancyBill.getEntry().addCollection(newEntrys);
		returnTenancyBill.setStrOfReturnRate(str.toString());
		return returnTenancyBill;
	}
	
}