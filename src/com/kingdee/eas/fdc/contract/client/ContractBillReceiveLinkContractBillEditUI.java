/**
 * output package name
 */
package com.kingdee.eas.fdc.contract.client;

import java.awt.event.*;
import java.util.Map;

import org.apache.log4j.Logger;

import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.contract.ContractBillInfo;
import com.kingdee.eas.fdc.contract.ContractBillReceiveInfo;
import com.kingdee.eas.framework.*;

/**
 * output class name
 */
public class ContractBillReceiveLinkContractBillEditUI extends AbstractContractBillReceiveLinkContractBillEditUI
{
    private static final Logger logger = CoreUIObject.getLogger(ContractBillReceiveLinkContractBillEditUI.class);
    
    /**
     * output class constructor
     */
    public ContractBillReceiveLinkContractBillEditUI() throws Exception
    {
        super();
    }

	@Override
	public void actionCancel_actionPerformed(ActionEvent e) throws Exception {
		// TODO Auto-generated method stub
		super.actionCancel_actionPerformed(e);
    	destroyWindow();
	}

	@Override
	public void actionConfirm_actionPerformed(ActionEvent e) throws Exception {
		// TODO Auto-generated method stub
		super.actionConfirm_actionPerformed(e);
		getUIContext().put("ok", "ok");
		ContractBillReceiveInfo info=(ContractBillReceiveInfo) this.prmtContract.getValue();
		contractBillInfo.setContractBillReceive(info);
		destroyWindow();
	}
	ContractBillInfo contractBillInfo;
	@Override
	public void onLoad() throws Exception {
		// TODO Auto-generated method stub
		super.onLoad();
		Map uiContext = this.getUIContext();
		contractBillInfo = (ContractBillInfo) uiContext.get("contractBillInfo");
		
		this.prmtContract.setValue(contractBillInfo.getContractBillReceive());
		EntityViewInfo view=new EntityViewInfo();
		FilterInfo filter=new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("state",FDCBillStateEnum.AUDITTED_VALUE));
		filter.getFilterItems().add(new FilterItemInfo("curProject.id",contractBillInfo.getCurProject().getId()));
//		filter.getFilterItems().add(new FilterItemInfo("id","select fContractBillid from T_CON_ContractBillReceive where fContractBillid is not null and fid!='"+contractBillReceiveInfo.getId()+"'",CompareType.NOTINNER));
		view.setFilter(filter);
		this.prmtContract.setEntityViewInfo(view);
		
		this.actionConfirm.setEnabled(true);
		this.actionCancel.setEnabled(true);
	}

}