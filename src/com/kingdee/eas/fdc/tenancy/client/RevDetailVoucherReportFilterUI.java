/**
 * output package name
 */
package com.kingdee.eas.fdc.tenancy.client;

import java.awt.event.*;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.swing.SpinnerNumberModel;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.fdc.basedata.FDCCommonServerHelper;
import com.kingdee.eas.fdc.basedata.MoneySysTypeEnum;
import com.kingdee.eas.fdc.sellhouse.MoneyTypeEnum;
import com.kingdee.eas.fdc.sellhouse.client.CommerceHelper;
import com.kingdee.eas.fdc.sellhouse.client.FDCRoomPromptDialog;
import com.kingdee.eas.fdc.tenancy.TenancyBillStateEnum;
import com.kingdee.eas.framework.*;
import com.kingdee.eas.framework.report.util.RptParams;

/**
 * output class name
 */
public class RevDetailVoucherReportFilterUI extends AbstractRevDetailVoucherReportFilterUI
{
    private static final Logger logger = CoreUIObject.getLogger(RevDetailVoucherReportFilterUI.class);
    public RevDetailVoucherReportFilterUI() throws Exception
    {
        super();
    }
    public void onLoad() throws Exception {
		super.onLoad();
		this.prmtRoom.setValue(null);
		this.prmtTanancyBill.setValue(null);
		this.prmtCustomer.setValue(null);
		this.prmtMoneyDefine.setValue(null);
		
		Date now=new Date();
		try {
			now=FDCCommonServerHelper.getServerTimeStamp();
		} catch (BOSException e) {
			logger.error(e.getMessage());
		}
		
		Calendar cal = Calendar.getInstance();
		cal.setTime(now);
		int year=cal.get(Calendar.YEAR);
		int month=cal.get(Calendar.MONTH)+1;
		
		this.spYear.setModel(new SpinnerNumberModel(year,1,10000,1));
		this.spMonth.setModel(new SpinnerNumberModel(month,1,12,1));
		
		FDCRoomPromptDialog dialog=new FDCRoomPromptDialog(Boolean.TRUE, null, null,
				MoneySysTypeEnum.TenancySys, null,null);
		this.prmtRoom.setSelector(dialog);
		
		EntityViewInfo vi = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("tenancyState",TenancyBillStateEnum.AUDITED_VALUE));
		filter.getFilterItems().add(new FilterItemInfo("tenancyState",TenancyBillStateEnum.EXECUTING_VALUE));
		
		filter.getFilterItems().add(new FilterItemInfo("orgUnit.longNumber", SysContext.getSysContext().getCurrentOrgUnit().getLongNumber()+"%",CompareType.LIKE));
		filter.setMaskString("(#0 or #1) and #2");
		vi.setFilter(filter);
		this.prmtTanancyBill.setEntityViewInfo(vi);
		
		this.prmtCustomer.setEditable(false);
		this.prmtCustomer.setQueryInfo("com.kingdee.eas.fdc.sellhouse.app.CustomerAllQuery");
		this.prmtCustomer.setDisplayFormat("$name$");
		this.prmtCustomer.setEditFormat("$number$");
		this.prmtCustomer.setCommitFormat("$number$");
		this.prmtCustomer.setEnabledMultiSelection(true);
		this.prmtCustomer.setEntityViewInfo(CommerceHelper.getPermitCustomerView(null,SysContext.getSysContext().getCurrentUserInfo()));
	
		this.prmtMoneyDefine.setEditable(false);
		this.prmtMoneyDefine.setQueryInfo("com.kingdee.eas.fdc.sellhouse.app.MoneyDefineQuery");
		this.prmtMoneyDefine.setDisplayFormat("$name$");
		this.prmtMoneyDefine.setEditFormat("$number$");
		this.prmtMoneyDefine.setCommitFormat("$number$");
		this.prmtMoneyDefine.setEnabledMultiSelection(true);
		EntityViewInfo view = new EntityViewInfo();
		filter = new FilterInfo();
		Set noInNumber=new HashSet();
		noInNumber.add("03");
		noInNumber.add("04");
		noInNumber.add("08");
		noInNumber.add("10");
		noInNumber.add("12");
		noInNumber.add("13");
		noInNumber.add("14");
		noInNumber.add("15");
		noInNumber.add("16");
		filter.getFilterItems().add(new FilterItemInfo("sysType", MoneySysTypeEnum.TENANCYSYS_VALUE));
		filter.getFilterItems().add(new FilterItemInfo("number", noInNumber,CompareType.NOTINCLUDE));
		view.setFilter(filter);
		this.prmtMoneyDefine.setEntityViewInfo(view);
	
    }
    public boolean verify()
    {
        return true;
    }
	public RptParams getCustomCondition() {
		 RptParams pp = new RptParams();
         if(this.prmtRoom.getValue()!=null){
    		 pp.setObject("room", this.prmtRoom.getValue());
         }else{
        	 pp.setObject("room", null);
         }
         if(this.prmtTanancyBill.getValue()!=null){
    		 pp.setObject("tenancyBill", this.prmtTanancyBill.getValue());
         }else{
        	 pp.setObject("tenancyBill", null);
         }
         if(this.prmtCustomer.getValue()!=null){
    		 pp.setObject("customer", this.prmtCustomer.getValue());
         }else{
        	 pp.setObject("customer", null);
         }
         if(this.prmtMoneyDefine.getValue()!=null){
    		 pp.setObject("moneyDefine", this.prmtMoneyDefine.getValue());
         }else{
        	 pp.setObject("moneyDefine", null);
         }
         pp.setObject("year", this.spYear.getIntegerVlaue());
         pp.setObject("month", this.spMonth.getIntegerVlaue());
		 pp.setObject("isAll", this.cbIsAll.isSelected());
		 pp.setObject("isZero", this.cbIsZero.isSelected());
		 return pp;
	}
	public void onInit(RptParams params) throws Exception {
		
	}
	public void setCustomCondition(RptParams params) {
		this.prmtRoom.setValue(params.getObject("room"));
		this.prmtTanancyBill.setValue(params.getObject("tenancyBill"));
		this.cbIsAll.setSelected(params.getBoolean("isAll"));
		this.cbIsZero.setSelected(params.getBoolean("isZero"));
		this.prmtCustomer.setValue(params.getObject("customer"));
		this.prmtMoneyDefine.setValue(params.getObject("moneyDefine"));
		this.spYear.setValue(params.getObject("year"));
		this.spMonth.setValue(params.getObject("month"));
	}
	protected void cbIsAll_actionPerformed(ActionEvent e) throws Exception {
		EntityViewInfo vi = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("tenancyState",TenancyBillStateEnum.AUDITED_VALUE));
		filter.getFilterItems().add(new FilterItemInfo("tenancyState",TenancyBillStateEnum.EXECUTING_VALUE));
		if(this.cbIsAll.isSelected()){
			filter.getFilterItems().add(new FilterItemInfo("tenancyState",TenancyBillStateEnum.EXPIRATION_VALUE));
		}
		filter.getFilterItems().add(new FilterItemInfo("orgUnit.longNumber", SysContext.getSysContext().getCurrentOrgUnit().getLongNumber()+"%",CompareType.LIKE));
		if(this.cbIsAll.isSelected()){
			filter.setMaskString("(#0 or #1 or #2) and #3");
		}else{
			filter.setMaskString("(#0 or #1) and #2");
		}
		vi.setFilter(filter);
		this.prmtTanancyBill.setEntityViewInfo(vi);
	}
}