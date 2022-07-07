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
import javax.swing.event.ChangeEvent;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SorterItemCollection;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ctrl.extendcontrols.ext.IFilterInfoProducer;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.fdc.basedata.FDCCommonServerHelper;
import com.kingdee.eas.fdc.basedata.MoneySysTypeEnum;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.sellhouse.MoneyTypeEnum;
import com.kingdee.eas.fdc.sellhouse.SellProjectCollection;
import com.kingdee.eas.fdc.sellhouse.SellProjectFactory;
import com.kingdee.eas.fdc.sellhouse.SellProjectInfo;
import com.kingdee.eas.fdc.sellhouse.client.CommerceHelper;
import com.kingdee.eas.fdc.sellhouse.client.FDCRoomPromptDialog;
import com.kingdee.eas.fdc.tenancy.TenancyBillStateEnum;
import com.kingdee.eas.framework.*;
import com.kingdee.eas.framework.report.util.RptParams;

/**
 * output class name
 */
public class DepositAmountReportFilterUI extends AbstractDepositAmountReportFilterUI
{
    private static final Logger logger = CoreUIObject.getLogger(DepositAmountReportFilterUI.class);
    
    public DepositAmountReportFilterUI() throws Exception
    {
        super();
    }
    public void onLoad() throws Exception {
		super.onLoad();
		this.prmtRoom.setValue(null);
		this.prmtCustomer.setValue(null);
		this.prmtMoneyDefine.setValue(null);
		this.chkExecuting.setSelected(true);
		
//		FDCRoomPromptDialog dialog=new FDCRoomPromptDialog(Boolean.TRUE, null, null,
//				MoneySysTypeEnum.TenancySys, null);
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
		filter.getFilterItems().add(new FilterItemInfo("moneyType", MoneyTypeEnum.DEPOSITAMOUNT_VALUE));
		filter.getFilterItems().add(new FilterItemInfo("name", "%×÷·Ï%",CompareType.NOTLIKE));
		view.setFilter(filter);
		SorterItemCollection sort=new SorterItemCollection();
		sort.add(new SorterItemInfo("number"));
		view.setSorter(sort);
		this.prmtMoneyDefine.setEntityViewInfo(view);
	
    }
	public RptParams getCustomCondition() {
		 RptParams pp = new RptParams();
         if(this.prmtRoom.getValue()!=null){
    		 pp.setObject("room", this.prmtRoom.getValue());
         }else{
        	 pp.setObject("room", null);
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
		 pp.setBoolean("isAll", chkAll.isSelected());
		 pp.setBoolean("isExecuting", chkExecuting.isSelected());
		 pp.setBoolean("isQuitTenancy", chkQuitTenancy.isSelected());
		 pp.setBoolean("isExpiration", chkExpiration.isSelected());
		 
		 pp.setBoolean("isSubAll", chsAll.isSelected());
		 pp.setBoolean("isSubZero", chsZero.isSelected());
		 pp.setBoolean("isSubNotZero", chsNotZero.isSelected());
		 return pp;
	}
	public void onInit(RptParams params) throws Exception {
		
	}
	public void setCustomCondition(RptParams params) {
		this.prmtRoom.setValue(params.getObject("room"));
		this.prmtCustomer.setValue(params.getObject("customer"));
		this.prmtMoneyDefine.setValue(params.getObject("moneyDefine"));
		boolean selected = params.getBoolean("isAll");
		this.chkAll.setSelected(selected);
		
		selected = params.getBoolean("isExecuting");
		this.chkExecuting.setSelected(selected);
		
		selected = params.getBoolean("isQuitTenancy");
		this.chkQuitTenancy.setSelected(selected);
		
		selected = params.getBoolean("isExpiration");
		this.chkExpiration.setSelected(selected);
		
		boolean subSelected = params.getBoolean("isSubAll");
		this.chsAll.setSelected(subSelected);
		
		subSelected = params.getBoolean("isSubZero");
		this.chsZero.setSelected(subSelected);
		
		subSelected = params.getBoolean("isSubNotZero");
		this.chsNotZero.setSelected(subSelected);
	}
	protected void chkAll_stateChanged(ChangeEvent e) throws Exception {
		// TODO Auto-generated method stub
		super.chkAll_stateChanged(e);
		if(chkAll.isSelected()){
			this.chkExecuting.setSelected(true);
			this.chkExpiration.setSelected(true);
			this.chkQuitTenancy.setSelected(true);
		}
		if(!chkAll.isSelected()){
			this.chkExecuting.setSelected(true);
			this.chkExpiration.setSelected(false);
			this.chkQuitTenancy.setSelected(false);
		}
	}
	protected void chkQuitTenancy_stateChanged(ChangeEvent e) throws Exception {
		// TODO Auto-generated method stub
		super.chkQuitTenancy_stateChanged(e);
		if(!chkQuitTenancy.isSelected()){
			if(chkAll.isSelected()){
				chkAll.setSelected(false);
			}
			if(!chkExecuting.isSelected()&&!chkExpiration.isSelected()){
				this.chkExecuting.setSelected(true);
			}
		}
		
		if(chkQuitTenancy.isSelected()){
			if(chkExecuting.isSelected()&&chkExpiration.isSelected()){
				this.chkAll.setSelected(true);
			}
		}
	}
	
	@Override
	protected void chkExecuting_stateChanged(ChangeEvent e) throws Exception {
		// TODO Auto-generated method stub
		super.chkExecuting_stateChanged(e);
		if(!chkExecuting.isSelected()){
			if(chkAll.isSelected()){
				chkAll.setSelected(false);
			}
			if(!chkQuitTenancy.isSelected()&&!chkExpiration.isSelected()){
				this.chkExecuting.setSelected(true);
			}
		}
		if(chkExecuting.isSelected()){
			if(chkQuitTenancy.isSelected()&&chkExpiration.isSelected()){
				this.chkAll.setSelected(true);
			}
		}
	}
	
	@Override
	protected void chkExpiration_stateChanged(ChangeEvent e) throws Exception {
		// TODO Auto-generated method stub
		super.chkExpiration_stateChanged(e);
		if(!chkExpiration.isSelected()){
			if(chkAll.isSelected()){
				chkAll.setSelected(false);
			}
			if(!chkExecuting.isSelected()&&!chkQuitTenancy.isSelected()){
				this.chkExecuting.setSelected(true);
			}
		}
		if(chkExpiration.isSelected()){
			if(chkExecuting.isSelected()&&chkQuitTenancy.isSelected()){
				this.chkAll.setSelected(true);
			}
		}
	}
	boolean isUpdate=true;
	@Override
	protected void chsAll_stateChanged(ChangeEvent e) throws Exception {
		// TODO Auto-generated method stub
		super.chsAll_stateChanged(e);
		if(chsAll.isSelected()){
			this.chsZero.setSelected(true);
			this.chsNotZero.setSelected(true);
		}
		if(isUpdate&&!chsAll.isSelected()){
			this.chsZero.setSelected(false);
			this.chsNotZero.setSelected(true);
		}
	}
	@Override
	protected void chsNotZero_stateChanged(ChangeEvent e) throws Exception {
		// TODO Auto-generated method stub
		super.chsNotZero_stateChanged(e);
		if(chsZero.isSelected()&&chsNotZero.isSelected()){
			this.chsAll.setSelected(true);
		}else if(!chsZero.isSelected()&&!chsNotZero.isSelected()){
			this.chsAll.setSelected(false);
		}else{
			isUpdate=false;
			this.chsAll.setSelected(false);
			isUpdate=true;
		}
		
	}
	@Override
	protected void chsZero_stateChanged(ChangeEvent e) throws Exception {
		// TODO Auto-generated method stub
		super.chsZero_stateChanged(e);
		if(chsZero.isSelected()&&chsNotZero.isSelected()){
			this.chsAll.setSelected(true);
		}else if(!chsZero.isSelected()&&!chsNotZero.isSelected()){
			this.chsAll.setSelected(false);
		}else{
			isUpdate=false;
			this.chsAll.setSelected(false);
			isUpdate=true;
		}
	}
	
}