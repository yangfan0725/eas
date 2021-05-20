/**
 * output package name
 */
package com.kingdee.eas.fdc.sellhouse.client;

import java.awt.event.ActionEvent;

import org.apache.log4j.Logger;

import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.event.KDTSelectEvent;
import com.kingdee.bos.ctrl.kdf.table.util.KDTableUtil;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.eas.basedata.org.OrgConstants;
import com.kingdee.eas.basedata.org.SaleOrgUnitInfo;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basedata.FDCDataBaseInfo;
import com.kingdee.eas.fdc.basedata.client.FDCClientHelper;
import com.kingdee.eas.fdc.sellhouse.BuildingPropertyFactory;
import com.kingdee.eas.fdc.sellhouse.BuildingPropertyInfo;
import com.kingdee.eas.fdc.sellhouse.RoomFactory;
import com.kingdee.eas.fdc.sellhouse.SellProjectFactory;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.util.client.MsgBox;

/**
 * output class name
 * modify by qinyouzhen,��¥�Ż� V7.0.3
 * @date 20110613
 */
public class BuildingPropertyListUI extends AbstractBuildingPropertyListUI {
	private static final Logger logger = CoreUIObject
			.getLogger(BuildingPropertyListUI.class);
	SaleOrgUnitInfo saleOrg = SHEHelper.getCurrentSaleOrg();
	/**
	 * output class constructor
	 */
	public BuildingPropertyListUI() throws Exception {
		super();
	}

	public void onLoad() throws Exception {
		super.onLoad();
		FDCClientHelper.addSqlMenu(this, this.menuEdit);
		if (!saleOrg.getId().toString().equals(OrgConstants.DEF_CU_ID)) {
			actionAddNew.setEnabled(false);
			actionEdit.setEnabled(false);
			actionRemove.setEnabled(false);
		}
	}

	protected String getEditUIModal() {
		return UIFactoryName.MODEL;
	}

	 /**
	 * ����ÿһ�ʼ�¼�Ƿ����,�Ӷ����ð�ť�Ƿ����
	 */
	protected void tblMain_tableSelectChanged(KDTSelectEvent e)
			throws Exception {
		IRow row = KDTableUtil.getSelectedRow(tblMain);
		if (row != null) {
			Boolean isEnabled = (Boolean) row.getCell("isEnabled").getValue();

			if (!saleOrg.getId().toString().equals(OrgConstants.DEF_CU_ID)) {
				actionAddNew.setEnabled(false);
				actionEdit.setEnabled(false);
				actionRemove.setEnabled(false);
				actionCancel.setEnabled(false);
				actionCancelCancel.setEnabled(false);
			} else {
				if (isEnabled.booleanValue()) {// ���������,���ð�ť����,�޸İ�ť������
					actionCancel.setEnabled(true);
					actionCancelCancel.setEnabled(false);
				} else {
					actionCancel.setEnabled(false);
					actionCancelCancel.setEnabled(true);
				}
			}
		}
	}

//	public void actionRemove_actionPerformed(ActionEvent e) throws Exception {
//		String id = this.getSelectedKeyValue();
//		if (id != null) {
//			FilterInfo filter = new FilterInfo();
//			filter.getFilterItems().add(
//					new FilterItemInfo("shareRoomModels.id", id));
//			if (SellProjectFactory.getRemoteInstance().exists(filter)) {
//				MsgBox.showInfo("�Ѿ�����Ŀ����,����ɾ��!");
//				return;
//			}
//			
//			filter = new FilterInfo();
//			filter.getFilterItems().add(
//					new FilterItemInfo("roomModel.id", id));
//			if (RoomFactory.getRemoteInstance().exists(filter)) {
//				MsgBox.showInfo("�Ѿ�����������,����ɾ��!");
//				return;
//			}
//		}
//		super.actionRemove_actionPerformed(e);
////		checkSelected();
////		if (confirmRemove()) {
////			Remove();
////			refresh(e);
////		}
//	}
	
	protected FDCDataBaseInfo getBaseDataInfo(){
		return new BuildingPropertyInfo();
	}
	
	protected String getEditUIName() {
		return BuildingPropertyEditUI.class.getName();
	}

	protected ICoreBase getBizInterface() throws Exception {
		return BuildingPropertyFactory.getRemoteInstance();
	}

}