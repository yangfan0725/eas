/**
 * output package name
 */
package com.kingdee.eas.fdc.sellhouse.client;

import java.awt.event.*;
import java.util.List;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.eas.basedata.org.OrgConstants;
import com.kingdee.eas.basedata.org.SaleOrgUnitInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.client.FDCClientHelper;
import com.kingdee.eas.fdc.sellhouse.FDCCustomerFactory;
import com.kingdee.eas.fdc.sellhouse.RoomFactory;
import com.kingdee.eas.fdc.sellhouse.RoomFormFactory;
import com.kingdee.eas.framework.*;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.MsgBox;

/**
 * output class name
 */
public class RoomFormListUI extends AbstractRoomFormListUI
{
    private static final Logger logger = CoreUIObject.getLogger(RoomFormListUI.class);
    SaleOrgUnitInfo saleOrg = SHEHelper.getCurrentSaleOrg();
    /**
     * output class constructor
     */
    public RoomFormListUI() throws Exception
    {
        super();
    }
    public void onLoad() throws Exception {
		this.actionQuery.setVisible(false);
		
		removeBizMenu();
		
		super.onLoad();
		FDCClientHelper.addSqlMenu(this, this.menuEdit);
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
    
    public void actionRemove_actionPerformed(ActionEvent e) throws Exception {
		checkSelected();
		RoomFormListUI.checkHasRelatedByFDCCustomer("roomForm.id", getSelectedIdValues());
		super.actionRemove_actionPerformed(e);
	}
    
    private static boolean hasRelatedByFDCCustomer(String key, List selectedIds) throws EASBizException, BOSException{
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo(key, FDCHelper.getSetByList(selectedIds),CompareType.INCLUDE));
		return RoomFactory.getRemoteInstance().exists(filter);
	}
    
    public static void checkHasRelatedByFDCCustomer(String key, List selectedIds) throws EASBizException, BOSException{
		if(hasRelatedByFDCCustomer(key, selectedIds)){
			MsgBox.showInfo("�Ѿ����������ò���ɾ��");
			SysUtil.abort();
		}
	}

    /**
     * output storeFields method
     */
    public void storeFields()
    {
        super.storeFields();
    }

	protected String getEditUIName() {
		return RoomFormEditUI.class.getName();
	}

	protected String getEditUIModal() {
		return UIFactoryName.MODEL;
	}
	
	protected ICoreBase getBizInterface() throws Exception {
		return RoomFormFactory.getRemoteInstance();
	}
	
	
}