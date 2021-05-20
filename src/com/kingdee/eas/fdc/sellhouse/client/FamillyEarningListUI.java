/**
 * output package name
 */
package com.kingdee.eas.fdc.sellhouse.client;

import java.awt.event.ActionEvent;

import org.apache.log4j.Logger;

import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.eas.basedata.org.OrgConstants;
import com.kingdee.eas.basedata.org.SaleOrgUnitInfo;
import com.kingdee.eas.fdc.basedata.FDCDataBaseInfo;
import com.kingdee.eas.fdc.basedata.client.FDCClientHelper;
import com.kingdee.eas.fdc.sellhouse.FamillyEarningFactory;
import com.kingdee.eas.fdc.sellhouse.FamillyEarningInfo;
import com.kingdee.eas.framework.ICoreBase;

/**
 * output class name
 */
public class FamillyEarningListUI extends AbstractFamillyEarningListUI
{
    private static final Logger logger = CoreUIObject.getLogger(FamillyEarningListUI.class);
    
    /**
     * output class constructor
     */
    public FamillyEarningListUI() throws Exception
    {
        super();
    }
    public void onLoad() throws Exception {
		this.actionQuery.setVisible(false);
		
		removeBizMenu();
		
		super.onLoad();
		FDCClientHelper.addSqlMenu(this, this.menuEdit);
		
		SaleOrgUnitInfo saleOrg = SHEHelper.getCurrentSaleOrg();
		if (!saleOrg.getId().toString().equals(OrgConstants.DEF_CU_ID)) {
			this.actionAddNew.setEnabled(false);
			this.actionEdit.setEnabled(false);
			this.actionRemove.setEnabled(false);
		}	
	}
    
    private void removeBizMenu() {
		this.menuBar.remove(menuBiz);
		this.toolBar.remove(btnCancelCancel);
        this.toolBar.remove(btnCancel);
	}
	protected FDCDataBaseInfo getBaseDataInfo() {
		return new FamillyEarningInfo();
	}
	protected String getEditUIName() {
		return FamillyEarningEditUI.class.getName();
	}
	protected ICoreBase getBizInterface() throws Exception {
		return FamillyEarningFactory.getRemoteInstance();
	}
	public void actionRemove_actionPerformed(ActionEvent e) throws Exception {
		checkSelected();
		FDCCustomerHelper.checkHasRelatedByFDCCustomer("famillyEarning.id", getSelectedIdValues());
		super.actionRemove_actionPerformed(e);
	}
}