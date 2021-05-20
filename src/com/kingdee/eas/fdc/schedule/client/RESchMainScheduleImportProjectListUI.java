/**
 * output package name
 */
package com.kingdee.eas.fdc.schedule.client;

import java.awt.event.ActionEvent;
import java.io.File;
import java.util.List;
import java.util.Map;

import javax.swing.filechooser.FileFilter;

import org.apache.log4j.Logger;

import com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTDefaultCellEditor;
import com.kingdee.bos.ctrl.swing.KDFileChooser;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.basedata.org.FullOrgUnitInfo;
import com.kingdee.eas.basedata.person.PersonInfo;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.fdc.basedata.client.FDCClientUtils;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.schedule.FDCScheduleTaskCollection;
import com.kingdee.eas.fdc.schedule.FDCScheduleTaskFactory;
import com.kingdee.eas.fdc.schedule.FDCScheduleTaskInfo;
import com.kingdee.eas.fdc.schedule.client.AbstractREScheduleImportPlanTemplateListUI.actionClose;
import com.kingdee.eas.framework.ICoreBase;

/**
 * 
 * @(#)						
 * ��Ȩ��		�����������������޹�˾��Ȩ����		 	
 * ������		�ƻ����������б�༭����
 *		
 * @author		��С��
 * @version		EAS7.0		
 * @createDate	2011-08-04 
 * @see
 */
public class RESchMainScheduleImportProjectListUI extends AbstractRESchMainScheduleImportProjectListUI
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 6430071457465581329L;
	/** ��־�ļ� */
    private static final Logger logger = CoreUIObject.getLogger(RESchMainScheduleImportProjectListUI.class);
    
    private static String preProjectFileName = System.getProperty("user.home");
    
    /** ������ */
    private static String COL_PERSON = "person";
    
    /** ���β��� */
    private static String COL_DEPARTMENT = "department";
    
    /** ��� */
    private static String COL_SEQ = "seq";
    
    /** �쳣��Ϣ */
    private static String COL_INFO = "exceptionInfo";
    
    /** ������ */
    private static String COL_ID = "id";
    
    private KDFileChooser chooser;
    
    private boolean isExit = true;
    
    /**
     * ����project�ļ�
     */
    public void actionImportProject_actionPerformed(ActionEvent e) throws Exception {
//    	if(this.getUIContext().get("Owner") instanceof FDCScheduleBaseEditUI){
//    		FDCScheduleBaseEditUI fDCScheduleBaseEditUI = (FDCScheduleBaseEditUI)this.getUIContext().get("Owner");
//    		if(null == chooser){
//    			FDCMsgBox.showInfo("��ѡ���ļ���");
//    			return ;
//    		}
//    		if(this.tblMain.getRowCount() > 0){
//    			FDCMsgBox.showInfo("�����ظ����룡");
//    			return ;
//    		}
//    		
//    		FDCScheduleTaskCollection fDCScheduleTaskCollection = fDCScheduleBaseEditUI.editData.getTaskEntrys();
//    		List errorInfos = fDCScheduleBaseEditUI.importProject(this.chooser);
//    		/* ��õ�������� */
//    		for(int m = 0; m < fDCScheduleTaskCollection.size(); m ++){
//				FDCScheduleTaskInfo fDCScheduleTaskInfo = fDCScheduleTaskCollection.get(m);
//				Map isPersonNulls = (Map) errorInfos.get(0);
//	    		Map isDeptNulls = (Map) errorInfos.get(1);
//	    		Map isPersonRepeats = (Map) errorInfos.get(2);
//	    		Map isDeptRepeats = (Map) errorInfos.get(3);
//	    		Object isNullObj = isPersonNulls.get(fDCScheduleTaskInfo.getId().toString());
//	    		Object isDeptNullObj = isDeptNulls.get(fDCScheduleTaskInfo.getId().toString());
//	    		Object isPersonRepeatObj = isPersonRepeats.get(fDCScheduleTaskInfo.getId().toString());
//	    		Object isDeptRepeatObj = isDeptRepeats.get(fDCScheduleTaskInfo.getId().toString());
//	    		if(null == isNullObj && null == isDeptNullObj && null == isPersonRepeatObj && null == isDeptRepeatObj){
//	    			continue ;  
//	    		}
//	    		IRow row = this.tblMain.addRow();
//	    		row.getCell(COL_SEQ).setValue((m + 1) + "");
//	    		
//	    		String errorInfo = "��" + (m + 1) + "�С�" + fDCScheduleTaskInfo.getName() + "�ġ�";
//	    		
//	    		/* Ϊ�յ���� */
//	    		if(null != isNullObj || null != isDeptNullObj){
//	    			if(null != isNullObj){
//		    			errorInfo += isNullObj.toString().substring(0,isNullObj.toString().indexOf("___")) + ",";
//		    		}
//		    		if(null != isDeptNullObj){
//		    			errorInfo += isDeptNullObj.toString().substring(0,isDeptNullObj.toString().indexOf("___"));
//		    		}
//		    		errorInfo += "�޷��ҵ�ƥ���";
//	    		}
//	    		
//	    		/* �ظ������ */
//	    		if(null != isPersonRepeatObj || null != isDeptRepeatObj){
//	    			if(null != isNullObj){
//		    			errorInfo += isPersonRepeatObj.toString().substring(0,isPersonRepeatObj.toString().indexOf("___")) + ",";
//		    		}
//		    		if(null != isDeptRepeatObj){
//		    			errorInfo += isDeptRepeatObj.toString().substring(0,isDeptRepeatObj.toString().indexOf("___"));
//		    		}
//		    		errorInfo += "�ظ���";
//	    		}
//	    		
//	    		row.getCell(COL_INFO).setValue(errorInfo);
//	    		row.getCell(COL_ID).setValue(fDCScheduleTaskInfo.getId());
//			}
//    		
//    	}
    }
    
    /**
     * ����
     */
    public void actionSubmit_actionPerformed(ActionEvent e) throws Exception {
    	isExit = false;
    	
    	saveData();
    	
    	/* �رմ��� */
		destroyWindow();
    }
    
    /**
     * @discription  <��������>
     * @author       <Xiaolong Luo>
     * @createDate   <2011/08/30> <p>
     * @param        <��>
     * @return       <����ֵ����>
     * 
     * modifier      <��> <p>
     * modifyDate    <��> <p>
     * modifyInfo	 <��> <p>
     * @see          <��ص���>
     */
    public void saveData() throws Exception{
    	if(this.getUIContext().get("Owner") instanceof FDCScheduleBaseEditUI){
    		FDCScheduleBaseEditUI fDCScheduleBaseEditUI = (FDCScheduleBaseEditUI)this.getUIContext().get("Owner");
    		FDCScheduleTaskCollection fDCScheduleTaskCollection = new FDCScheduleTaskCollection();
    		for(int k = 0; k < this.tblMain.getRowCount(); k ++){
    			FDCScheduleTaskInfo fDCScheduleTaskInfo = new FDCScheduleTaskInfo(); 
    			IRow row = this.tblMain.getRow(k);
    			fDCScheduleTaskInfo.setId(BOSUuid.read(row.getCell(COL_ID).getValue().toString()));
    			fDCScheduleTaskInfo.setAdminPerson((PersonInfo)row.getCell(COL_PERSON).getValue());
    			fDCScheduleTaskInfo.setAdminDept((FullOrgUnitInfo)row.getCell(COL_DEPARTMENT).getValue());
    			fDCScheduleTaskCollection.add(fDCScheduleTaskInfo);
    		}
    		fDCScheduleBaseEditUI.saveDepAndPerson(fDCScheduleTaskCollection);
    	}
    }
    
    public boolean destroyWindow() {
    	if(isExit){
    		int result = FDCMsgBox.showConfirm2("�����Ѿ��޸��Ƿ񱣴棡");
    		if(result == 0){
    			try {
					saveData();
				} catch (Exception e) {
					logger.error(e.getMessage());
				}
    		}
    	}
    	return super.destroyWindow();
    }

    public void onLoad() throws Exception {
    	super.onLoad();
    	this.tblMain.checkParsed();

    	
    	/* �������� */
		KDBizPromptBox f7PrmpAdmin = new KDBizPromptBox();
		f7PrmpAdmin.setQueryInfo("com.kingdee.eas.fdc.schedule.app.F7PersonQuery");
		f7PrmpAdmin.setDisplayFormat("$name$");		
		f7PrmpAdmin.setCommitFormat("$number$");		
		f7PrmpAdmin.setEditFormat("$number$");
		f7PrmpAdmin.setEditable(false);
//		dataBinder.registerBinding("prmpAdmin", com.kingdee.eas.basedata.person.PersonInfo.class, f7PrmpAdmin, "data");
//		ScheduleClientHelper.setPersonF7(f7PrmpAdmin, this, SysContext.getSysContext().getCurrentCtrlUnit() != null ? SysContext.getSysContext().getCurrentCtrlUnit().getId().toString() : null);
		KDTDefaultCellEditor f7EditorPrmpAdmin = new KDTDefaultCellEditor(f7PrmpAdmin);
		
		this.tblMain.getColumn(COL_PERSON).setEditor(f7EditorPrmpAdmin);

		/* �����β��� */
		KDBizPromptBox f7PrmpDepartment = new KDBizPromptBox();
		f7PrmpDepartment.setName("prmpDepartment");
		f7PrmpDepartment.setQueryInfo("com.kingdee.eas.fdc.schedule.app.F7FullOrgUnitQuery");
		f7PrmpDepartment.setDisplayFormat("$name$");		
		f7PrmpDepartment.setCommitFormat("$number$");		
		f7PrmpDepartment.setEditFormat("$number$");
		f7PrmpDepartment.setEditable(false);
//		if (ScheduleClientHelper.chooseAllOrg()) {
//			ScheduleClientHelper.setRespDept(f7PrmpDepartment, this, null);
//		} else {
//			ScheduleClientHelper.setRespDept(f7PrmpDepartment, this, SysContext.getSysContext()
//					.getCurrentFIUnit() != null ? SysContext.getSysContext().getCurrentFIUnit().getId().toString() : null);
//		}
//		FDCClientUtils.setRespDeptF7(f7PrmpDepartment,this,null);
		KDTDefaultCellEditor f7EditorPrmpDepartment = new KDTDefaultCellEditor(f7PrmpDepartment);
		this.tblMain.getColumn(COL_DEPARTMENT).setEditor(f7EditorPrmpDepartment);
    	
    }
    
    public void onShow() throws Exception {
    	super.onShow();
    	
    	initTableStyle();
    	
    }
    
    /**
     * @discription  <��ʼ������ʾ>
     * @author       <Xiaolong Luo>
     * @createDate   <2011/08/23> <p>
     * @param        <��>
     * @return       <����ֵ����>
     * 
     * modifier      <��> <p>
     * modifyDate    <��> <p>
     * modifyInfo	 <��> <p>
     * @see          <��ص���>
     */
    public void initTableStyle(){
    	this.tblMain.getColumn(COL_SEQ).getStyleAttributes().setHided(false);
    	this.tblMain.getColumn(COL_SEQ).getStyleAttributes().setLocked(true);
    	
    	this.tblMain.getColumn(COL_SEQ).setWidth(50);
    	this.tblMain.getColumn(COL_INFO).setWidth(280);
    	this.tblMain.getColumn(COL_INFO).getStyleAttributes().setLocked(true);
    	this.tblMain.getColumn(COL_PERSON).setWidth(100);
    	this.tblMain.getColumn(COL_DEPARTMENT).setWidth(100);
    }
    
    protected void initWorkButton() {
    	super.initWorkButton();
    	this.btnSelect.setEnabled(true);
    	this.btnImport.setEnabled(true);
    }
    
    /**
     * ����ļ� 
     */
    public void actionBrowse_actionPerformed(ActionEvent e) throws Exception {
    	KDFileChooser chooser = new KDFileChooser(new File(preProjectFileName));
		chooser.setFileFilter(new FileFilter() {
			public boolean accept(File f) {
				return f.isDirectory() || f.getName().toLowerCase().endsWith(".mpp");
			}

			public String getDescription() {
				return "Project�ļ�(*.mpp)";
			}
		});
		if (chooser.showOpenDialog(this) == KDFileChooser.APPROVE_OPTION) {
			File file = chooser.getSelectedFile();
			this.txtPath.setText(file.getPath());
			this.chooser = chooser;
		}
	}

    /**
	 *����ʾ������
	 */
	public void initUIToolBarLayout() {
		this.toolBar.add(btnSubmit);
	}
	
	/**
     * output class constructor
     */
    public RESchMainScheduleImportProjectListUI() throws Exception
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
     * output actionEdit_actionPerformed
     */
    public void actionEdit_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionEdit_actionPerformed(e);
    }

    /**
     * output actionRemove_actionPerformed
     */
    public void actionRemove_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionRemove_actionPerformed(e);
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
     * output actionAttachment_actionPerformed
     */
    public void actionAttachment_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionAttachment_actionPerformed(e);
    }

    /**
     * output actionCancel_actionPerformed
     */
    public void actionCancel_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionCancel_actionPerformed(e);
    }

    /**o
     * output actionCancelCancel_actionPerformed
     */
    public void actionCancelCancel_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionCancelCancel_actionPerformed(e);
    }

    /**
     * output actionSave_actionPerformed
     */
    public void actionSave_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionSave_actionPerformed(e);
    }

    protected ICoreBase getBizInterface() throws Exception {
		return FDCScheduleTaskFactory.getRemoteInstance();
	}

	protected IObjectValue createNewData() {
		return new FDCScheduleTaskInfo();
	}

}