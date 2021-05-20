/**
 * output package name
 */
package com.kingdee.eas.fdc.sellhouse.client;

import java.awt.event.*;
import org.apache.log4j.Logger;

import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.eas.basedata.org.OrgConstants;
import com.kingdee.eas.basedata.org.SaleOrgUnitInfo;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.sellhouse.EyeSignhtFactory;
import com.kingdee.eas.fdc.sellhouse.PossessionStandardFactory;
import com.kingdee.eas.fdc.sellhouse.RoomDesFactory;
import com.kingdee.eas.fdc.sellhouse.RoomFactory;
import com.kingdee.eas.framework.*;
import com.kingdee.eas.framework.util.UIConfigUtility;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.MsgBox;

/**
 * output class name
 */
public class PossessionStandardListUI extends AbstractPossessionStandardListUI {
	private static final Logger logger = CoreUIObject.getLogger(PossessionStandardListUI.class);

	/**
	 * output class constructor
	 */
	public PossessionStandardListUI() throws Exception {
		super();
	}

	/**
	 * output storeFields method
	 */
	public void storeFields() {
		super.storeFields();
	}

	public void onLoad() throws Exception {
		super.onLoad();
		SaleOrgUnitInfo saleOrg = SHEHelper.getCurrentSaleOrg();
		if (!saleOrg.getId().toString().equals(OrgConstants.DEF_CU_ID)) {
			this.actionAddNew.setEnabled(false);
			this.actionEdit.setEnabled(false);
			this.actionRemove.setEnabled(false);
		}
	}
	protected String getEditUIModal() {
		String openModel = UIConfigUtility.getOpenModel();
		if (openModel != null) {
			return openModel;
		}
		return UIFactoryName.MODEL;
	}

	/**
	 * output tblMain_tableClicked method
	 */
	protected void tblMain_tableClicked(com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e) throws Exception {
		super.tblMain_tableClicked(e);
	}

	/**
	 * output actionAddNew_actionPerformed
	 */
	public void actionAddNew_actionPerformed(ActionEvent e) throws Exception {
		super.actionAddNew_actionPerformed(e);
	}

	/**
	 * output actionView_actionPerformed
	 */
	public void actionView_actionPerformed(ActionEvent e) throws Exception {
		this.checkSelected();
		super.actionView_actionPerformed(e);
	}

	/**
	 * output actionEdit_actionPerformed
	 */
	public void actionEdit_actionPerformed(ActionEvent e) throws Exception {
		this.checkSelected();
		super.actionEdit_actionPerformed(e);
	}

	/**
	 * output actionRemove_actionPerformed
	 */
	public void actionRemove_actionPerformed(ActionEvent e) throws Exception {
		this.checkSelected();
		String id = this.getSelectedKeyValue();
		if (id != null) {
			FilterInfo filter = new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("posseStd.id", id));
			if (RoomDesFactory.getRemoteInstance().exists(filter)) {
				MsgBox.showInfo("已被房间定义引用，不能删除！");
				SysUtil.abort();
			}
			if (RoomFactory.getRemoteInstance().exists(filter)) {
				MsgBox.showInfo("已被房间引用，不能删除！");
				SysUtil.abort();
			}
			super.actionRemove_actionPerformed(e);
		}
	}

	/**
	 * output actionRefresh_actionPerformed
	 */
	public void actionRefresh_actionPerformed(ActionEvent e) throws Exception {
		super.actionRefresh_actionPerformed(e);
	}

	protected ICoreBase getBizInterface() throws Exception {

		return PossessionStandardFactory.getRemoteInstance();
	}

	protected String getEditUIName() {
		return PossessionStandardEditUI.class.getName();
	}

	

}