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
import com.kingdee.bos.metadata.entity.SorterItemCollection;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.fdc.basedata.FDCCommonServerHelper;
import com.kingdee.eas.fdc.basedata.MoneySysTypeEnum;
import com.kingdee.eas.fdc.sellhouse.MoneyTypeEnum;
import com.kingdee.eas.fdc.sellhouse.SellProjectCollection;
import com.kingdee.eas.fdc.sellhouse.SellProjectFactory;
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
		
//		FDCRoomPromptDialog dialog=new FDCRoomPromptDialog(Boolean.TRUE, null, null,
//				MoneySysTypeEnum.TenancySys, null,null);
//		this.prmtRoom.setSelector(dialog);
		
		SellProjectCollection sp=SellProjectFactory.getRemoteInstance().getSellProjectCollection("select id from where orgUnit.id='"+SysContext.getSysContext().getCurrentOrgUnit().getId()+"'");
		Set spSet=new HashSet();
		for(int i=0;i<sp.size();i++){
			spSet.add(sp.get(i).getId().toString());
		}
		EntityViewInfo view=new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("isForTen",Boolean.TRUE));
		filter.getFilterItems().add(new FilterItemInfo("sellProject.id",spSet,CompareType.INCLUDE));
		view.setFilter(filter);
		this.prmtRoom.setQueryInfo("com.kingdee.eas.fdc.tenancy.app.F7RoomQuery");		
	    this.prmtRoom.setDisplayFormat("$name$");		
	    this.prmtRoom.setEditFormat("$name$");	
	    this.prmtRoom.setEntityViewInfo(view);
		
		view = new EntityViewInfo();
		filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("tenancyState",TenancyBillStateEnum.AUDITED_VALUE));
		filter.getFilterItems().add(new FilterItemInfo("tenancyState",TenancyBillStateEnum.EXECUTING_VALUE));
		
		filter.getFilterItems().add(new FilterItemInfo("orgUnit.longNumber", SysContext.getSysContext().getCurrentOrgUnit().getLongNumber()+"%",CompareType.LIKE));
		filter.setMaskString("(#0 or #1) and #2");
		view.setFilter(filter);
		this.prmtTanancyBill.setEntityViewInfo(view);
		
		this.prmtCustomer.setEditable(false);
		this.prmtCustomer.setQueryInfo("com.kingdee.eas.fdc.sellhouse.app.CustomerAllQuery");
		this.prmtCustomer.setDisplayFormat("$name$");
		this.prmtCustomer.setEditFormat("$number$");
		this.prmtCustomer.setCommitFormat("$number$");
		this.prmtCustomer.setEnabledMultiSelection(true);
		view=CommerceHelper.getPermitCustomerView(null,SysContext.getSysContext().getCurrentUserInfo());
		view.getFilter().getFilterItems().add(new FilterItemInfo("project.id",spSet,CompareType.INCLUDE));
		this.prmtCustomer.setEntityViewInfo(view);
		
		this.prmtMoneyDefine.setEditable(false);
		this.prmtMoneyDefine.setQueryInfo("com.kingdee.eas.fdc.sellhouse.app.MoneyDefineQuery");
		this.prmtMoneyDefine.setDisplayFormat("$name$");
		this.prmtMoneyDefine.setEditFormat("$number$");
		this.prmtMoneyDefine.setCommitFormat("$number$");
		this.prmtMoneyDefine.setEnabledMultiSelection(true);
		view = new EntityViewInfo();
		filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("sysType", MoneySysTypeEnum.TENANCYSYS_VALUE));
		filter.getFilterItems().add(new FilterItemInfo("name", "%����%",CompareType.NOTLIKE));
		filter.getFilterItems().add(new FilterItemInfo("moneyType", MoneyTypeEnum.RENTAMOUNT_VALUE));
		filter.getFilterItems().add(new FilterItemInfo("moneyType", MoneyTypeEnum.ELSEAMOUNT_VALUE));
		filter.getFilterItems().add(new FilterItemInfo("moneyType", MoneyTypeEnum.PERIODICITYAMOUNT_VALUE));
		view.setFilter(filter);
		
		filter.setMaskString("#0 and #1 and (#2 or #3 or #4)");
		view.setFilter(filter);
		SorterItemCollection sort=new SorterItemCollection();
		sort.add(new SorterItemInfo("number"));
		view.setSorter(sort);
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
		 pp.setObject("isNeedTotal", this.chkIsNeedTotal.isSelected());
         pp.setObject("isZero", this.cbIsZero.isSelected());
		 return pp;
	}
	public void onInit(RptParams params) throws Exception {
		
	}
	public void setCustomCondition(RptParams params) {
		this.prmtRoom.setValue(params.getObject("room"));
		this.prmtTanancyBill.setValue(params.getObject("tenancyBill"));
		this.cbIsAll.setSelected(params.getBoolean("isAll"));
		this.chkIsNeedTotal.setSelected(params.getBoolean("isNeedTotal"));
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