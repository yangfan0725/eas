/**
 * output package name
 */
package com.kingdee.eas.fdc.basedata.client;

import java.awt.event.ActionEvent;
import java.util.HashSet;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.table.ICell;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectBlock;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectManager;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.basedata.org.OrgConstants;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basedata.ILandDeveloper;
import com.kingdee.eas.fdc.basedata.LandDeveloperFactory;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.framework.client.FrameWorkClientUtils;
import com.kingdee.eas.framework.config.TablePreferencesHelper;
import com.kingdee.eas.framework.util.UIConfigUtility;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.MsgBox;

/**
 * 描述:甲方(开发商)叙事簿界面
 * 
 * @author jackwang date:2006-7-7
 * @version EAS5.1
 */
public class LandDeveloperListUI extends AbstractLandDeveloperListUI {
	private static final Logger logger = CoreUIObject.getLogger(LandDeveloperListUI.class);

	/**
	 * output class constructor
	 */
	public LandDeveloperListUI() throws Exception {
		super();
	}

	/**
	 * 
	 */
	public void onLoad() throws Exception {
		super.onLoad();
		//this.btnEnabled.setIcon(EASResource.getIcon("imgTbtn_staruse"));
		//this.btnDisEnabled.setIcon(EASResource.getIcon("imgTbtn_forbid"));
//		tblMain.getSelectManager().setSelectMode(KDTSelectManager.MULTIPLE_ROW_SELECT);
//		if(OrgConstants.DEF_CU_ID.equals(SysContext.getSysContext().getCurrentCtrlUnit().getId().toString())){
		if(SysContext.getSysContext().getCurrentOrgUnit()!=null
				&&SysContext.getSysContext().getCurrentOrgUnit().isIsCompanyOrgUnit()
				&&SysContext.getSysContext().getCurrentFIUnit().isIsBizUnit()){
			this.btnAddNew.setEnabled(true);
			this.btnEdit.setEnabled(true);
			this.btnRemove.setEnabled(true);
			//this.btnEnabled.setVisible(true);
			//this.btnDisEnabled.setVisible(true);
			this.menuItemAddNew.setEnabled(true);
			this.menuItemEdit.setEnabled(true);
			this.menuItemRemove.setEnabled(true);
//			this.menuItemCancel.setv(true)
		}else{
			this.btnAddNew.setEnabled(false);
			this.btnEdit.setEnabled(false);
			this.btnRemove.setEnabled(false);
			//this.btnEnabled.setVisible(false);
			//this.btnDisEnabled.setVisible(false);
			this.menuItemAddNew.setEnabled(false);
			this.menuItemEdit.setEnabled(false);
			this.menuItemRemove.setEnabled(false);
		}
		this.tblMain.getSelectManager().setSelectMode(KDTSelectManager.ROW_SELECT);
//		由于有查询方案，故不手动设置保存方案。
//		//设置可以保存当前样式
//		tHelper = new TablePreferencesHelper(this);
	}

	/**
	 * output storeFields method
	 */
	public void storeFields() {
		super.storeFields();
	}

	/**
	 * output tblMain_tableClicked method
	 */
	protected void tblMain_tableClicked(com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e) throws Exception {
		super.tblMain_tableClicked(e);
	}

	
	/**
	 * output menuItemImportData_actionPerformed method
	 */
	protected void menuItemImportData_actionPerformed(java.awt.event.ActionEvent e) throws Exception {
		super.menuItemImportData_actionPerformed(e);
	}

	/**
	 * output actionPageSetup_actionPerformed
	 */
	public void actionPageSetup_actionPerformed(ActionEvent e) throws Exception {
		super.actionPageSetup_actionPerformed(e);
	}

	/**
	 * output actionExitCurrent_actionPerformed
//	 */
	public void actionExitCurrent_actionPerformed(ActionEvent e) throws Exception {
		super.actionExitCurrent_actionPerformed(e);
	}

	/**
	 * output actionHelp_actionPerformed
	 */
	public void actionHelp_actionPerformed(ActionEvent e) throws Exception {
		super.actionHelp_actionPerformed(e);
	}

	/**
	 * output actionAbout_actionPerformed
	 */
	public void actionAbout_actionPerformed(ActionEvent e) throws Exception {
		super.actionAbout_actionPerformed(e);
	}

	/**
	 * output actionOnLoad_actionPerformed
	 */
	public void actionOnLoad_actionPerformed(ActionEvent e) throws Exception {
		super.actionOnLoad_actionPerformed(e);
	}

	/**
	 * output actionSendMessage_actionPerformed
	 */
	public void actionSendMessage_actionPerformed(ActionEvent e) throws Exception {
		super.actionSendMessage_actionPerformed(e);
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
		super.actionView_actionPerformed(e);
	}

	/**
	 * output actionEdit_actionPerformed
	 */
	public void actionEdit_actionPerformed(ActionEvent e) throws Exception {
		super.actionEdit_actionPerformed(e);
	}


	/**
	 * output actionRefresh_actionPerformed
	 */
	public void actionRefresh_actionPerformed(ActionEvent e) throws Exception {
		super.actionRefresh_actionPerformed(e);
	}

	/**
	 * output actionPrint_actionPerformed
	 */
	public void actionPrint_actionPerformed(ActionEvent e) throws Exception {
		super.actionPrint_actionPerformed(e);
	}

	/**
	 * output actionPrintPreview_actionPerformed
	 */
	public void actionPrintPreview_actionPerformed(ActionEvent e) throws Exception {
		super.actionPrintPreview_actionPerformed(e);
	}

	/**
	 * output actionLocate_actionPerformed
	 */
	public void actionLocate_actionPerformed(ActionEvent e) throws Exception {
		super.actionLocate_actionPerformed(e);
	}

	/**
	 * output actionQuery_actionPerformed
	 */
	public void actionQuery_actionPerformed(ActionEvent e) throws Exception {
		super.actionQuery_actionPerformed(e);
	}

	/**
	 * output actionImportData_actionPerformed
	 */
	public void actionImportData_actionPerformed(ActionEvent e) throws Exception {
		super.actionImportData_actionPerformed(e);
	}

	/**
	 * output actionAttachment_actionPerformed
	 */
	public void actionAttachment_actionPerformed(ActionEvent e) throws Exception {
		super.actionAttachment_actionPerformed(e);
	}

	/**
	 * output actionExportData_actionPerformed
	 */
	public void actionExportData_actionPerformed(ActionEvent e) throws Exception {
		super.actionExportData_actionPerformed(e);
	}

	/**
	 * output actionToExcel_actionPerformed
	 */
	public void actionToExcel_actionPerformed(ActionEvent e) throws Exception {
		super.actionToExcel_actionPerformed(e);
	}

	/**
	 * output actionStartWorkFlow_actionPerformed
	 */
	public void actionStartWorkFlow_actionPerformed(ActionEvent e) throws Exception {
		super.actionStartWorkFlow_actionPerformed(e);
	}

	/**
	 * output actionPublishReport_actionPerformed
	 */
	public void actionPublishReport_actionPerformed(ActionEvent e) throws Exception {
		super.actionPublishReport_actionPerformed(e);
	}

	protected String getEditUIName() {
		// TODO 自动生成方法存根
		return LandDeveloperEditUI.class.getName();
	}

	protected ICoreBase getBizInterface() throws Exception {
		// TODO 自动生成方法存根
		return LandDeveloperFactory.getRemoteInstance();
	}

	/**
	 * output actionEnabled_actionPerformed method
	 */
	public void actionEnabled_actionPerformed(ActionEvent e) throws Exception {
		actionRefresh_actionPerformed(e);
		IRow row = checkSelected(e);
		if (row == null)
			return;
		String id = row.getCell("id").getValue().toString().trim();
		ILandDeveloper iLandDeveloper = LandDeveloperFactory.getRemoteInstance();
		if(iLandDeveloper.enabled(new ObjectUuidPK(id))){
			setMessageText(EASResource.getString(FDCBaseDataClientUtils.FDCBASEDATA_RESOURCE, "Enabled_OK"));
			showMessage();
		}		
		
	}
	
	
	//启用功能实现
	 public void actionCancelCancel_actionPerformed(ActionEvent e) throws Exception
	 {
	        IRow row = checkSelected(e);
			if (row == null)
				return;
			String id = row.getCell("id").getValue().toString().trim();
			ILandDeveloper iLandDeveloper = LandDeveloperFactory.getRemoteInstance();
			if(iLandDeveloper.enabled(new ObjectUuidPK(id))){
				setMessageText(EASResource.getString(FDCBaseDataClientUtils.FDCBASEDATA_RESOURCE, "Enabled_OK"));
				showMessage();
			}	
			
			actionRefresh_actionPerformed(e);
	 }
	 
	 //禁用功能实现
	 public void actionCancel_actionPerformed(ActionEvent e) throws Exception
	 {
	    	IRow row = checkSelected(e);
			if (row == null)
				return;
			String id = row.getCell("id").getValue().toString().trim();
			ILandDeveloper iLandDeveloper = LandDeveloperFactory.getRemoteInstance();
			if(iLandDeveloper.disEnabled(new ObjectUuidPK(id))){
				setMessageText(EASResource.getString(FDCBaseDataClientUtils.FDCBASEDATA_RESOURCE, "DisEnabled_OK"));
				showMessage();
			}	
			
			actionRefresh_actionPerformed(e);
	 }

	

	/**
	 * output actionDisEnabled_actionPerformed method
	 */
	public void actionDisEnabled_actionPerformed(ActionEvent e) throws Exception {
		actionRefresh_actionPerformed(e);
		
		IRow row = checkSelected(e);
		if (row == null)
			return;
		String id = row.getCell("id").getValue().toString().trim();
		ILandDeveloper iLandDeveloper = LandDeveloperFactory.getRemoteInstance();
		if(iLandDeveloper.disEnabled(new ObjectUuidPK(id))){
			setMessageText(EASResource.getString(FDCBaseDataClientUtils.FDCBASEDATA_RESOURCE, "DisEnabled_OK"));
			showMessage();
		}		
		
	}

	private IRow checkSelected(ActionEvent e) {
		IRow row = null;
		if (this.tblMain.getSelectManager().getActiveRowIndex() == -1) {
			// 请先指定一条记录
			MsgBox.showWarning("请先指定一条记录!");
		} else {
			row = this.tblMain.getRow(this.tblMain.getSelectManager().getActiveRowIndex());
		}
		return row;
	}

	/**
	 * output tblMain_tableSelectChanged method
	 */
	protected void tblMain_tableSelectChanged(com.kingdee.bos.ctrl.kdf.table.event.KDTSelectEvent e) throws Exception {
		if (this.tblMain.getSelectManager().getActiveRowIndex() != -1) {
			// boolean status = false;
			if (this.tblMain.getRow(this.tblMain.getSelectManager().getActiveRowIndex()).getCell("isEnabled") != null) {
				boolean status = ((Boolean) this.tblMain.getRow(this.tblMain.getSelectManager().getActiveRowIndex()).getCell("isEnabled").getValue()).booleanValue();
				
				// 随着每一行规则的isEnabled的值改变，两个WBT的状态也改变
				
				changeWBTEnabeld(status);
				
			}
			// if
			// (this.tblMain.getRow(this.tblMain.getSelectManager().getActiveRowIndex()).getCell("cu.id")
			// != null) {
			// boolean isEnabled =
			// this.tblMain.getRow(this.tblMain.getSelectManager().getActiveRowIndex()).getCell("cu.id").getValue().toString().equals(
			// SysContext.getSysContext().getCurrentCtrlUnit().getId().toString());
			// changeEditEnabeld(isEnabled);
			// } else {
			// disabledEdit();
			// }
		} /*else {
			disabledWBT();

		}*/
	}

	/**
	 * 随着每一行规则的isEnabled的值改变，两个btn的状态也改变
	 * 
	 * @param isEnabled
	 *            boolean
	 */
	private void changeWBTEnabeld(boolean isEnabled) {
		//this.btnEnabled.setEnabled(!isEnabled);
		this.actionCancel.setEnabled(isEnabled);
		this.actionCancelCancel.setEnabled(!isEnabled);
		//this.btnDisEnabled.setEnabled(isEnabled);

	}

	/**
	 * 把启用/禁止按钮disabled
	 */
	/*private void disabledWBT() {
		this.btnEnabled.setEnabled(false);
		this.btnDisEnabled.setEnabled(false);

	}*/
    protected boolean isIgnoreCUFilter()
    {
        return true;
    }
	/**
	 * output actionRemove_actionPerformed
	 */
	public void actionRemove_actionPerformed(ActionEvent e) throws Exception {
		super.actionRemove_actionPerformed(e);
	}
    protected String getEditUIModal()
    {
        // return UIFactoryName.MODEL;
        // return UIFactoryName.NEWWIN;
        // 2006-4-29 胡博要求加入根据配置项来读取UI打开方式。
        String openModel = UIConfigUtility.getOpenModel();
        if (openModel != null)
        {
            return openModel;
        }
        else
        {
            return UIFactoryName.MODEL;
        }
    }
    
    protected void beforeExcutQuery(EntityViewInfo ev) {
    	super.beforeExcutQuery(ev);
    	//对非集团进行CU过滤
    	if(!OrgConstants.DEF_CU_ID.equals(SysContext.getSysContext().getCurrentOrgUnit().getId().toString())){
/*    		if(ev.getFilter()!=null){
    			FilterInfo filter=ev.getFilter();
    			for(int i=0;i<filter.getFilterItems().size();i++){
    				if(filter.getFilterItems().get(i).getPropertyName().equalsIgnoreCase("CU.id")){
    					filter.getFilterItems().removeObject(i);
    				}
    			}
    			if(filter.getFilterItems().isEmpty()) ev.setFilter(new FilterInfo());
    		}*/
    		if(ev.getFilter()==null){
    			FilterInfo filter=new FilterInfo();
    			ev.setFilter(filter);
    		}
    		HashSet set=new HashSet();
    		//2009-1-19 kelvin_yang 经与时小鸿、侯艳讨论，甲方不应显示集团CU，否则会导致在建立合同时，F7出来的数据与序事簿不一致。
//    		set.add(OrgConstants.SYS_CU_ID);
    		//因甲方基础资料级别S4会产生过滤条件，故mergeFilter产生一个交集
    		set.add(SysContext.getSysContext().getCurrentCtrlUnit().getId().toString());
    		FilterInfo filter = new FilterInfo();
    		filter.getFilterItems().add(new FilterItemInfo("CU.id",set,CompareType.INCLUDE));
//    		ev.getFilter().getFilterItems().add(new FilterItemInfo("CU.id",set,CompareType.INCLUDE));
    		if(ev.getFilter() != null){
    			try {
					ev.getFilter().mergeFilter(filter,"and");
				} catch (BOSException e) {
					this.handUIException(e);
				}
    		}
    	}
    }
    
}