/**
 * output package name
 */
package com.kingdee.eas.fdc.invite.supplier.client;

import java.awt.event.ActionEvent;

import org.apache.log4j.Logger;

import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.invite.supplier.SupplierChangeConfirmFactory;
import com.kingdee.eas.fdc.invite.supplier.SupplierChangeConfirmInfo;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.util.SysUtil;

/**
 * output class name
 */
public class SupplierChangeConfirmListUI extends
		AbstractSupplierChangeConfirmListUI {
	private static final Logger logger = CoreUIObject
			.getLogger(SupplierChangeConfirmListUI.class);

	/**
	 * output class constructor
	 */
	public SupplierChangeConfirmListUI() throws Exception {
		super();
	}
	
	@Override
	public void actionEdit_actionPerformed(ActionEvent e) throws Exception {
		// TODO Auto-generated method stub
		SupplierChangeConfirmInfo info = SupplierChangeConfirmFactory.getRemoteInstance().getSupplierChangeConfirmInfo(new ObjectUuidPK(getSelectedID()));
		if(!info.getState().equals(FDCBillStateEnum.SAVED)&&!info.getState().equals(FDCBillStateEnum.SUBMITTED)){
			FDCMsgBox.showError("当前状态单据不适合修改.");
			SysUtil.abort();
		}
		super.actionEdit_actionPerformed(e);
	}
	
	@Override
	public void actionRemove_actionPerformed(ActionEvent e) throws Exception {
		// TODO Auto-generated method stub
		SupplierChangeConfirmInfo info = SupplierChangeConfirmFactory.getRemoteInstance().getSupplierChangeConfirmInfo(new ObjectUuidPK(getSelectedID()));
		if(!info.getState().equals(FDCBillStateEnum.SAVED)&&!info.getState().equals(FDCBillStateEnum.SUBMITTED)){
			FDCMsgBox.showError("当前状态单据不适合删除.");
			SysUtil.abort();
		}
		super.actionRemove_actionPerformed(e);
	}

	@Override
	public void onLoad() throws Exception {
		// TODO Auto-generated method stub
		super.onLoad();
		this.actionAddNew.setVisible(false);
	}

	@Override
	protected ICoreBase getBizInterface() throws Exception {
		// TODO Auto-generated method stub
		return SupplierChangeConfirmFactory.getRemoteInstance();
	}

	@Override
	protected String getEditUIName() {
		// TODO Auto-generated method stub
		return SupplierChangeConfirmEditUI.class.getName();
	}
	@Override
	protected String getEditUIModal() {
		// TODO Auto-generated method stub
		return UIFactoryName.NEWTAB;
	}
	
	private BOSUuid getSelectedID(){
		checkSelected();
		int rowIndex = this.tblMain.getSelectManager().getActiveRowIndex();
		String id = this.tblMain.getCell(rowIndex, "id").getValue()+"";
		return BOSUuid.read(id);
	}
	
	@Override
	public void actionAudit_actionPerformed(ActionEvent e) throws Exception {
		// TODO Auto-generated method stub
		super.actionAudit_actionPerformed(e);
		SupplierChangeConfirmFactory.getRemoteInstance().audit(getSelectedID());
		FDCMsgBox.showInfo("操作成功");
		this.tblMain.refresh();
		
	}
	
	@Override
	public void actionUnAudit_actionPerformed(ActionEvent e) throws Exception {
		// TODO Auto-generated method stub
		super.actionUnAudit_actionPerformed(e);
		SupplierChangeConfirmFactory.getRemoteInstance().unAudit(getSelectedID());
		FDCMsgBox.showInfo("操作成功");
		this.tblMain.refresh();
	}

}