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
		ContractBillInfo info=(ContractBillInfo) this.prmtContract.getValue();
		contractBillReceiveInfo.setContractBill(info);
		destroyWindow();
	}
	ContractBillReceiveInfo contractBillReceiveInfo;
	@Override
	public void onLoad() throws Exception {
		// TODO Auto-generated method stub
		super.onLoad();
		Map uiContext = this.getUIContext();
		contractBillReceiveInfo = (ContractBillReceiveInfo) uiContext.get("contractBillReceiveInfo");
		
		this.prmtContract.setValue(contractBillReceiveInfo.getContractBill());
		EntityViewInfo view=new EntityViewInfo();
		FilterInfo filter=new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("state",FDCBillStateEnum.AUDITTED_VALUE));
		filter.getFilterItems().add(new FilterItemInfo("curProject.id",contractBillReceiveInfo.getCurProject().getId()));
		view.setFilter(filter);
		this.prmtContract.setEntityViewInfo(view);
		
		this.actionConfirm.setEnabled(true);
		this.actionCancel.setEnabled(true);
	}

}