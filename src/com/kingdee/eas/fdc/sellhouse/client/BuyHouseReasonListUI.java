/**
 * output package name
 */
package com.kingdee.eas.fdc.sellhouse.client;

import java.awt.event.ActionEvent;

import org.apache.log4j.Logger;

import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.eas.fdc.basedata.FDCDataBaseInfo;
import com.kingdee.eas.fdc.basedata.client.FDCClientHelper;
import com.kingdee.eas.fdc.sellhouse.BuyHouseReasonFactory;
import com.kingdee.eas.fdc.sellhouse.BuyHouseReasonInfo;
import com.kingdee.eas.framework.ICoreBase;

/**
 * output class name
 */
public class BuyHouseReasonListUI extends AbstractBuyHouseReasonListUI
{
    private static final Logger logger = CoreUIObject.getLogger(BuyHouseReasonListUI.class);
    
    /**
     * output class constructor
     */
    public BuyHouseReasonListUI() throws Exception
    {
        super();
    }
    public void onLoad() throws Exception {
		this.actionQuery.setVisible(false);
		
		removeBizMenu();
		
		super.onLoad();
		FDCClientHelper.addSqlMenu(this, this.menuEdit);
	}
    
    private void removeBizMenu() {
		this.menuBar.remove(menuBiz);
		this.toolBar.remove(btnCancelCancel);
        this.toolBar.remove(btnCancel);
	}
	protected FDCDataBaseInfo getBaseDataInfo() {
		return new BuyHouseReasonInfo();
	}
	protected String getEditUIName() {
		return BuyHouseReasonEditUI.class.getName();
	}
	protected ICoreBase getBizInterface() throws Exception {
		return BuyHouseReasonFactory.getRemoteInstance();
	}
	public void actionRemove_actionPerformed(ActionEvent e) throws Exception {
		checkSelected();
//		FDCCustomerHelper.checkHasRelatedByFDCCustomer("buyHouseReason.id", getSelectedIdValues());
		super.actionRemove_actionPerformed(e);
	}
}