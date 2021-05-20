/**
 * output package name
 */
package com.kingdee.eas.fdc.schedule.client;

import java.awt.event.ActionEvent;

import org.apache.log4j.Logger;

import com.kingdee.bos.ctrl.kdf.table.ICell;
import com.kingdee.bos.ctrl.kdf.table.util.KDTableUtil;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.IUIFactory;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.fdc.basedata.FDCDataBaseInfo;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.schedule.ScheduleBizTypeFactory;
import com.kingdee.eas.fdc.schedule.ScheduleBizTypeInfo;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.EASResource;


/**
 * @(#)							
 * ��Ȩ��		�����������������޹�˾��Ȩ����		 	
 * ������		ҵ�����������б����	
 * @author		���
 * @version		EAS7.0		
 * @createDate	2011-8-1 
 * @see						
 */
public class ScheduleBizTypeListUI extends AbstractScheduleBizTypeListUI
{
    private static final Logger logger = CoreUIObject.getLogger(ScheduleBizTypeListUI.class);
    
    /**
     * output class constructor
     */
    public ScheduleBizTypeListUI() throws Exception
    {
        super();
    }

    /**
     * output storeFields method
     */
    public void storeFields()
    {
        super.storeFields();
    }

    /**
     * output tblMain_tableClicked method
     */
    protected void tblMain_tableClicked(com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e) throws Exception
    {
		// if (e.getButton() == 1 && e.getClickCount() == 1 && e.getColIndex()
		// == tblMain.getColumnIndex("number")) {
		// tblMain.showCellDetailInfo();
		// }
		//    	
        super.tblMain_tableClicked(e);
    }

    /**
     * output menuItemImportData_actionPerformed method
     */
    protected void menuItemImportData_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
        super.menuItemImportData_actionPerformed(e);
    }

    /**
     * output actionPageSetup_actionPerformed
     */
    public void actionPageSetup_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionPageSetup_actionPerformed(e);
    }

    /**
     * output actionExitCurrent_actionPerformed
     */
    public void actionExitCurrent_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionExitCurrent_actionPerformed(e);
    }

    /**
     * output actionHelp_actionPerformed
     */
    public void actionHelp_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionHelp_actionPerformed(e);
    }

    /**
     * output actionAbout_actionPerformed
     */
    public void actionAbout_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionAbout_actionPerformed(e);
    }

    /**
     * output actionOnLoad_actionPerformed
     */
    public void actionOnLoad_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionOnLoad_actionPerformed(e);
    }

    /**
     * output actionSendMessage_actionPerformed
     */
    public void actionSendMessage_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionSendMessage_actionPerformed(e);
    }

    /**
     * output actionCalculator_actionPerformed
     */
    public void actionCalculator_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionCalculator_actionPerformed(e);
    }

    /**
     * output actionExport_actionPerformed
     */
    public void actionExport_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionExport_actionPerformed(e);
    }

    /**
     * output actionExportSelected_actionPerformed
     */
    public void actionExportSelected_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionExportSelected_actionPerformed(e);
    }

    /**
     * output actionRegProduct_actionPerformed
     */
    public void actionRegProduct_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionRegProduct_actionPerformed(e);
    }

    /**
     * output actionPersonalSite_actionPerformed
     */
    public void actionPersonalSite_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionPersonalSite_actionPerformed(e);
    }

    /**
     * output actionProcductVal_actionPerformed
     */
    public void actionProcductVal_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionProcductVal_actionPerformed(e);
    }

    /**
     * output actionExportSave_actionPerformed
     */
    public void actionExportSave_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionExportSave_actionPerformed(e);
    }

    /**
     * output actionExportSelectedSave_actionPerformed
     */
    public void actionExportSelectedSave_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionExportSelectedSave_actionPerformed(e);
    }

    /**
     * output actionKnowStore_actionPerformed
     */
    public void actionKnowStore_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionKnowStore_actionPerformed(e);
    }

    /**
     * output actionAnswer_actionPerformed
     */
    public void actionAnswer_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionAnswer_actionPerformed(e);
    }

    /**
     * output actionRemoteAssist_actionPerformed
     */
    public void actionRemoteAssist_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionRemoteAssist_actionPerformed(e);
    }

    /**
     * output actionPopupCopy_actionPerformed
     */
    public void actionPopupCopy_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionPopupCopy_actionPerformed(e);
    }

    /**
     * output actionHTMLForMail_actionPerformed
     */
    public void actionHTMLForMail_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionHTMLForMail_actionPerformed(e);
    }

    /**
     * output actionExcelForMail_actionPerformed
     */
    public void actionExcelForMail_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionExcelForMail_actionPerformed(e);
    }

    /**
     * output actionHTMLForRpt_actionPerformed
     */
    public void actionHTMLForRpt_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionHTMLForRpt_actionPerformed(e);
    }

    /**
     * output actionExcelForRpt_actionPerformed
     */
    public void actionExcelForRpt_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionExcelForRpt_actionPerformed(e);
    }

    /**
     * output actionLinkForRpt_actionPerformed
     */
    public void actionLinkForRpt_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionLinkForRpt_actionPerformed(e);
    }

    /**
     * output actionPopupPaste_actionPerformed
     */
    public void actionPopupPaste_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionPopupPaste_actionPerformed(e);
    }

    /**
     * output actionAddNew_actionPerformed
     */
    public void actionAddNew_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionAddNew_actionPerformed(e);
    }

    /**
     * output actionView_actionPerformed
     */
    public void actionView_actionPerformed(ActionEvent e) throws Exception
    {
		int m = tblMain.getSelectManager().getActiveRowIndex();
		if (m < 0) {
			IUIFactory uifactory = null;
			uifactory = UIFactory.createUIFactory("com.kingdee.eas.base.uiframe.client.UIModelDialogFactory");
			UIContext uicontext = new UIContext(this);
			String id = tblMain.getRow(0).getCell("id").getValue().toString();
			uicontext.put(UIContext.ID, id);
			IUIWindow uiwindow = null;
			uiwindow = uifactory.create(getEditUIName(), uicontext, null, OprtState.VIEW);
			uiwindow.show();
		} else {
			super.actionView_actionPerformed(e);
		}
        
    }

	/**
	 * @auth libing
	 */
    public void actionEdit_actionPerformed(ActionEvent e) throws Exception
    {
    	int activeRowIndex = this.tblMain.getSelectManager().getActiveRowIndex();
		if (activeRowIndex < 0) {
			FDCMsgBox.showInfo("��ѡ����Ҫ�޸ĵ�ҵ������");
			SysUtil.abort();
		}
		// ���������ϵͳԤ������
		ICell cell = tblMain.getCell(activeRowIndex, "isSysDef");
		Boolean flag = (Boolean) cell.getValue();
		// �ж��Ƿ���Ԥ������
		if (flag.booleanValue()) {
			FDCMsgBox.showInfo(getReasource("isSysDef"));
			SysUtil.abort();
		}
		// super.actionEdit_actionPerformed(e);
		// actionView_actionPerformed(e);
		IUIFactory uifactory = null;
		uifactory = UIFactory.createUIFactory("com.kingdee.eas.base.uiframe.client.UIModelDialogFactory");
		UIContext uicontext = new UIContext(this);
		String id = tblMain.getRow(activeRowIndex).getCell("id").getValue().toString();
		uicontext.put(UIContext.ID, id);
		IUIWindow uiwindow = null;
		uiwindow = uifactory.create(getEditUIName(), uicontext, null, OprtState.EDIT);
		uiwindow.show();
		
		
    }
    
    /**
     * output actionRemove_actionPerformed
     */
    public void actionRemove_actionPerformed(ActionEvent e) throws Exception
    {
    	
    	// ֧�ֶ���ɾ��
		int m[] = KDTableUtil.getSelectedRows(tblMain);
		if (m.length == 0) {
			FDCMsgBox.showInfo("��ѡ����Ҫɾ����ҵ������");
			SysUtil.abort();
		}
		IObjectPK strID[] = new IObjectPK[m.length];
		boolean isHasDefault = false;
		boolean isAllDefault = true;
		for (int i = 0; i < m.length; i++) {
			// �ж��Ƿ�ϵͳԤ������
			ICell cell = tblMain.getCell(m[i], "isSysDef");
			Boolean flag = (Boolean) cell.getValue();
			
			if (flag.booleanValue()) {
				isHasDefault = true;
			} else {
				isAllDefault = false;
			}
			strID[i] = new ObjectUuidPK(tblMain.getCell(m[i], "id").getValue().toString());
		}
		 // ���¼��д������жϱ�����
		if (isHasDefault) {
			if (isAllDefault) {
				FDCMsgBox.showInfo("ϵͳԤ���ҵ�����Ͳ���ɾ��");
				SysUtil.abort();
			} else {
				FDCMsgBox.showInfo("ɾ��������ϵͳԤ���ҵ�����ͣ�����ȡ��ϵͳԤ�����ݣ�������ɾ��");
				SysUtil.abort();
			}
		}
		// ѯ���Ƿ�ɾ��
		if (confirmRemove()) {
			getBizInterface().delete(strID);
		} else {
			return;
		}
    	// ɾ�������ˢ��
		actionRefresh_actionPerformed(e);
    	
// // ϵͳԤ�����ݲ�����ɾ��
		// int activeRowIndex =
		// this.tblMain.getSelectManager().getActiveRowIndex();
		// if (activeRowIndex < 0)
		// return;
		// ���������ϵͳԤ������
		// ICell cell = tblMain.getCell(activeRowIndex, "isSysDef");
		// Boolean flag = (Boolean) cell.getValue();
		// // �ж��Ƿ���Ԥ������
		// if (flag.booleanValue()) {
		// FDCMsgBox.showInfo();
		// SysUtil.abort();
		// }
		// ICell c1 = tblMain.getCell(activeRowIndex, "id");
		// String strID = (String) c1.getValue();
		// getBizInterface().delete(new ObjectUuidPK(strID));
		
    }

    /**
     * output actionRefresh_actionPerformed
     */
    public void actionRefresh_actionPerformed(ActionEvent e) throws Exception
    {
		// this.tblMain.getSelectManager().select(0, 0);
    	int m[] = KDTableUtil.getSelectedRows(tblMain);
		if (m.length > 0) {
			tblMain.getSelectManager().select(m[0], 0);
		}
		super.actionRefresh_actionPerformed(e);
    }

    /**
     * output actionPrint_actionPerformed
     */
    public void actionPrint_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionPrint_actionPerformed(e);
    }

    /**
     * output actionPrintPreview_actionPerformed
     */
    public void actionPrintPreview_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionPrintPreview_actionPerformed(e);
    }

    /**
     * output actionLocate_actionPerformed
     */
    public void actionLocate_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionLocate_actionPerformed(e);
    }

    /**
     * output actionQuery_actionPerformed
     */
    public void actionQuery_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionQuery_actionPerformed(e);
    }

    /**
     * output actionImportData_actionPerformed
     */
    public void actionImportData_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionImportData_actionPerformed(e);
    }

    /**
     * output actionAttachment_actionPerformed
     */
    public void actionAttachment_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionAttachment_actionPerformed(e);
    }

    /**
     * output actionExportData_actionPerformed
     */
    public void actionExportData_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionExportData_actionPerformed(e);
    }

    /**
     * output actionToExcel_actionPerformed
     */
    public void actionToExcel_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionToExcel_actionPerformed(e);
    }

    /**
     * output actionStartWorkFlow_actionPerformed
     */
    public void actionStartWorkFlow_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionStartWorkFlow_actionPerformed(e);
    }

    /**
     * output actionPublishReport_actionPerformed
     */
    public void actionPublishReport_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionPublishReport_actionPerformed(e);
    }

    /**
     * output actionCancel_actionPerformed
     */
    public void actionCancel_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionCancel_actionPerformed(e);
    }

    /**
     * output actionCancelCancel_actionPerformed
     */
    public void actionCancelCancel_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionCancelCancel_actionPerformed(e);
    }

    /**
     * output actionQueryScheme_actionPerformed
     */
    public void actionQueryScheme_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionQueryScheme_actionPerformed(e);
    }

	protected FDCDataBaseInfo getBaseDataInfo() {
		// TODO Auto-generated method stub
		// return new ScheduleBizTypeInfo();
		return new ScheduleBizTypeInfo();
	}


	protected ICoreBase getBizInterface() throws Exception {
		// TODO Auto-generated method stub
		return ScheduleBizTypeFactory.getRemoteInstance();
	}


	protected String getEditUIName() {
		// TODO Auto-generated method stub
		return ScheduleBizTypeEditUI.class.getName();
	}


	protected void initWorkButton() {
		// TODO Auto-generated method stub
		super.initWorkButton();
		// ��ѯ
		this.btnQuery.setVisible(false);
		this.btnQuery.setEnabled(false);
		this.menuItemQuery.setVisible(false);
		this.menuItemQuery.setEnabled(false);
		// ��λ
		this.btnLocate.setVisible(false);
		this.btnLocate.setEnabled(false);
		this.menuItemLocate.setVisible(false);
		this.menuItemLocate.setEnabled(false);
		// ��ӡ
		this.btnPrint.setVisible(false);
		this.btnPrint.setEnabled(false);
		this.menuItemPrint.setVisible(false);
		this.menuItemPrint.setEnabled(false);
		
		// ��ӡԤ��
		this.btnPrintPreview.setVisible(false);
		this.btnPrintPreview.setEnabled(false);
		this.menuItemPrintPreview.setVisible(false);
		this.menuItemPrintPreview.setEnabled(false);
		
		// ����
		this.btnCancelCancel.setVisible(false);
		this.btnCancelCancel.setEnabled(false);
		this.menuItemCancelCancel.setVisible(false);
		this.menuItemCancelCancel.setEnabled(false);
		// ����
		this.btnCancel.setVisible(false);
		this.btnCancel.setEnabled(false);
		this.menuItemCancel.setVisible(false);
		this.menuItemCancel.setEnabled(false);
		
		
		
	}
	
	public void onShow() throws Exception {
		// TODO Auto-generated method stub
		super.onShow();
		this.btnCancelCancel.setVisible(false);
		this.btnCancel.setVisible(false);
	}
	public void onLoad() throws Exception {
		super.onLoad();
		canOprate();
		this.windowTitle = "ҵ����������";
	}
	
	/**
	 * @description �жϵ�ǰ��֯�Ƿ��Ǽ��ţ�ֻ�м��Ų��ܽ�������ɾ����
	 * @author ����ΰ
	 * @createDate 2011-09-06
	 * @param
	 * @return
	 * 
	 * @version EAS6.0
	 * @see
	 */
	private void canOprate() {
		if (!("00000000-0000-0000-0000-000000000000CCE7AED4".equals(SysContext.getSysContext().getCurrentOrgUnit().getId().toString()))) {
			this.btnAddNew.setEnabled(false);
			this.menuItemAddNew.setEnabled(false);
			this.btnEdit.setEnabled(false);
			this.menuItemEdit.setEnabled(false);
			this.btnRemove.setEnabled(false);
			this.menuItemRemove.setEnabled(false);
			this.btnPrint.setEnabled(false);
			this.btnPrintPreview.setEnabled(false);
			this.menuItemPrint.setEnabled(false);
			this.menuItemPrintPreview.setEnabled(false);
		}
	}
	
	// �����Դ�ļ�·��
	private String getReasource(String msg) {
		return EASResource.getString("com.kingdee.eas.fdc.schedule.ScheduleResource", msg);

	}
}