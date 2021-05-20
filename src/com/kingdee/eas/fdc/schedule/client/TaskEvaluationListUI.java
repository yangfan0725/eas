/**
 * output package name
 */
package com.kingdee.eas.fdc.schedule.client;

import java.awt.event.ActionEvent;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.swing.event.ChangeEvent;

import org.apache.log4j.Logger;

import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectManager;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent;
import com.kingdee.bos.ctrl.kdf.table.util.KDTableUtil;
import com.kingdee.bos.ctrl.kdf.util.style.Styles.HorizontalAlignment;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.IUIFactory;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.eas.base.commonquery.client.CommonQueryDialog;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basedata.FDCDataBaseInfo;
import com.kingdee.eas.fdc.schedule.ITaskEvaluation;
import com.kingdee.eas.fdc.schedule.TaskEvaluationFactory;
import com.kingdee.eas.fdc.schedule.TaskEvaluationInfo;
import com.kingdee.eas.fdc.schedule.TaskEvaluationTypeEnum;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.framework.client.ListUiHelper;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.MsgBox;

/**
 * 
 * @(#)						
 * ��Ȩ��		�����������������޹�˾��Ȩ���� ������ ���������������±�
 * 
 * @author �ܺ���
 * @version EAS7.0
 * @createDate 2011-08-08
 * @see
 */
public class TaskEvaluationListUI extends AbstractTaskEvaluationListUI {
	/**
	 * serialVersionNumber
	 */
	private static final long serialVersionUID = -3403951811098512421L;

	private static final Logger logger = CoreUIObject.getLogger(TaskEvaluationListUI.class);

	/*
	 * ͨ�ò�ѯ����
	 */
	private CommonQueryDialog commonQueryDialog = null;
	private KDTable table = tblMain;
	private int selectIndex;

	/**
	 * Info ����
	 */
	protected FDCDataBaseInfo getBaseDataInfo() {
		return new TaskEvaluationInfo();
	}

	/**
	 * ��ȡԶ�̽ӿ�
	 */
	protected ICoreBase getBizInterface() throws Exception {
		return TaskEvaluationFactory.getRemoteInstance();
	}

	/**
	 * ��ȡEditUI����
	 */
	protected String getEditUIName() {
		return TaskEvaluationEditUI.class.getName();
	}

	/**
	 * 
	 * 
	 * ������ ��ʼ��ListUI���水ť
	 * 
	 * @author �ܺ���
	 * @version EAS7.0
	 * @createDate 2011-8-8
	 * 
	 */
	protected void initWorkButton() {

		/**
		 * ToolBarPanel
		 */
		this.btnQuery.setVisible(false);
		this.btnLocate.setVisible(false);
		this.btnAttachment.setVisible(false);
		this.btnPageSetup.setVisible(false);
		this.btnPrint.setVisible(false);
		this.btnPrintPreview.setVisible(false);
		this.btnQueryScheme.setVisible(false);

		/**
		 * MenuBar
		 */
		this.MenuService.setVisible(false);
		this.menuBiz.setVisible(false);
		this.menuTool.setVisible(false);
		this.menuItemQuery.setVisible(false);

		super.initWorkButton();
	}

	private void canOprate() {
		if (!("00000000-0000-0000-0000-000000000000CCE7AED4".equals(SysContext.getSysContext().getCurrentOrgUnit().getId().toString()))) {
			this.btnAddNew.setEnabled(false);
			this.menuItemAddNew.setEnabled(false);
			this.btnRemove.setEnabled(false);
			this.menuItemRemove.setEnabled(false);
			this.btnEdit.setEnabled(false);
			this.menuItemEdit.setEnabled(false);
		}
	}

	public void refresh() {
		try {
			actionRefresh_actionPerformed(null);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * ������ �����������ã���������¼�
	 * 
	 * @author �ܺ���
	 * @version EAS7.0
	 * @createDate 2011-8-8
	 * 
	 */
	public void onLoad() throws Exception {
		super.onLoad();
		fillDataInTable();
		canOprate();
	}

	/**
	 * 
	 * ������ �����������ã�������ؽ����¼�
	 * 
	 * @author �ܺ���
	 * @version EAS7.0
	 * @createDate 2011-8-8
	 * 
	 */
	public void onShow() throws Exception {
		super.onShow();
		loadSetBoutton();

	}

	/**
	 * 
	 * ������ �����������ã����ؽ�����ť����
	 * 
	 * @author �ܺ���
	 * @version EAS7.0
	 * @createDate 2011-8-8
	 * 
	 */
	public void loadSetBoutton() {
		this.btnCancel.setVisible(false);
		this.btnCancelCancel.setVisible(false);
		
	}

	/**
	 * 
	 * ������ �����������ã������������͵�EditUI
	 * 
	 * @author �ܺ���
	 * @version EAS7.0
	 * @createDate 2011-8-8
	 * 
	 */
	protected void prepareUIContext(UIContext ctxt, ActionEvent e) {
		int index = this.taskEvaluationPane.getSelectedIndex();
		if (index == 0) {
			ctxt.put("pane", TaskEvaluationTypeEnum.SCHEDULE);
		} else {

			ctxt.put("pane", TaskEvaluationTypeEnum.QUALITY);
		}
		super.prepareUIContext(ctxt, e);
	}

	/**
	 * 
	 * ������ �����������ã�add after fillData
	 * 
	 * @author �ܺ���
	 * @version EAS7.0
	 * @createDate 2011-8-8
	 * 
	 */
	protected void execQuery() {
		super.execQuery();
		try {
			fillDataInTable();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * @description �����������ã���ʼ��������
	 * @author �ܺ���
	 * @createDate 2011-08-08
	 * @param
	 * @return
	 * 
	 * @version EAS7.0
	 * @throws
	 * @throws Exception
	 */
	private void fillDataInTable() throws Exception {
		int index = this.taskEvaluationPane.getSelectedIndex();
		String enumType = "";
		/*
		 * ��������ҳǩ
		 */
		if (index == 0 || index == -1) {
			tblMain = table;
			enumType = TaskEvaluationTypeEnum.SCHEDULE.getValue().toString();

			/*
			 * ����Զ�̷���,���ݲ�ѯ
			 */
			Set set = TaskEvaluationFactory.getRemoteInstance().getDataBase(enumType);
			/*
			 * ����
			 */
			 this.tblMain.checkParsed(true);
			this.tblMain.removeRows();

			Iterator it = set.iterator();
			while (it.hasNext()) {
				Map map = (Map) it.next();

				IRow ir = this.tblMain.addRow();

				/*
				 * �󶨱�����
				 */
				ir.getCell("evaluationResult").setValue(map.get("Eresult"));
				ir.getCell("number").setValue(map.get("Enumber"));
				try {
					if (null != map.get("Epass") && map.get("Epass").toString().trim().equals("1")) {
						ir.getCell("isNotPass").setValue("��");
					} else {
						ir.getCell("isNotPass").setValue("��");
					}
				} catch (Exception e) {
					logger.info(e.getMessage());
				}

				ir.getCell("description").setValue(map.get("Edescription"));
				ir.getCell("id").setValue(map.get("Eid"));
				ir.getCell("CU.ID").setValue(map.get("ECUID"));

				/*
				 * ������Ԫ��
				 */
				// this.setColumnLock();
			}

			/*
			 * �������־���
			 */
			this.tblMain.getColumn("number").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
			/*
			 * ��ס
			 */
			// this.tblMain.getHeadStyleAttributes().setLocked(true);
			/*
			 * ѡ�������з�ʽ
			 */
			this.tblMain.getSelectManager().setSelectMode(KDTSelectManager.MULTIPLE_ROW_SELECT);
		}
		/*
		 * ��������ҳǩ
		 */
		else {
			enumType = TaskEvaluationTypeEnum.QUALITY.getValue().toString();

			/*
			 * ����Զ�̷���,���ݲ�ѯ
			 */
			Set set = TaskEvaluationFactory.getRemoteInstance().getDataBase(enumType);
			/*
			 * ����
			 */
			this.tblMass.checkParsed();
			this.tblMass.removeRows();

			Iterator it = set.iterator();
			while (it.hasNext()) {
				Map map = (Map) it.next();

				IRow rw = this.tblMass.addRow();
				/*
				 * �󶨱�����
				 */
				rw.getCell("evaluationResult").setValue(map.get("Eresult"));
				rw.getCell("number").setValue(map.get("Enumber"));
				rw.getCell("description").setValue(map.get("Edescription"));
				rw.getCell("id").setValue(map.get("Eid"));

				/*
				 * ������Ԫ��
				 */
				this.setColumnLock();

			}
			/*
			 * �������־���
			 */
			this.tblMass.getColumn("number").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
			/*
			 * ��ס
			 */
			this.tblMass.getHeadStyleAttributes().setLocked(true);
			/*
			 * ѡ��������
			 */
			this.tblMass.getSelectManager().setSelectMode(KDTSelectManager.MULTIPLE_ROW_SELECT);
		}
		this.getCurPane();

	}

	/**
	 * @description �����������ã�Lock Table<�������۱�>
	 * @author �ܺ���
	 * @createDate 2011-08-10
	 * @param
	 * @return
	 * 
	 * @version EAS7.0
	 */
	private void setColumnLock() {
		/*
		 * ������Ԫ�񲻿ɱ༭
		 */
		this.tblMass.getColumn("evaluationResult").getStyleAttributes().setLocked(true);
		this.tblMass.getColumn("number").getStyleAttributes().setLocked(true);
		this.tblMass.getColumn("description").getStyleAttributes().setLocked(true);
		this.tblMass.getColumn("id").getStyleAttributes().setLocked(true);

	}

	/**
	 * @description �����������ã����캯��
	 * @author �ܺ���
	 * @createDate 2011-08-08
	 * @param
	 * @return
	 * 
	 * @version EAS7.0
	 */
	public TaskEvaluationListUI() throws Exception {
		super();
		/**
		 * �޸���������
		 */
		selectIndex = -1;
	}

	/**
	 * @description �����������ã�add after fillData
	 * @author �ܺ���
	 * @createDate 2011-08-10
	 * @param
	 * @return
	 * 
	 * @version EAS7.0
	 */
	protected void taskEvaluationPane_stateChanged(ChangeEvent e) throws Exception {
		super.taskEvaluationPane_stateChanged(e);
		this.fillDataInTable();
		this.getCurPane();
	}

	/**
	 * output storeFields method
	 */
	public void storeFields() {
		super.storeFields();
	}

	/**
	 * output menuItemImportData_actionPerformed method
	 */
	protected void menuItemImportData_actionPerformed(java.awt.event.ActionEvent e) throws Exception {
		logger.getName();
		super.menuItemImportData_actionPerformed(e);
	}

	/**
	 * @description �����������ã���������Table˫������¼�
	 * @author �ܺ���
	 * @createDate 2011-08-10
	 * @param
	 * @return
	 * 
	 * @version EAS7.0
	 */
	protected void tblMain_tableClicked(KDTMouseEvent e) throws Exception {

		/*
		 * ȡ��ѡ����ID
		 */
		int tblMainIndex = this.tblMain.getSelectManager().getActiveRowIndex();
		// if(e.getClickCount() == 1){
		// this.tblMain.getRow(tblMain.getSelectManager().getActiveRowIndex()).
		// setCollapse(true);
		// }
		if (tblMainIndex != -1 && e.getClickCount() == 2) {

			Object infoId = this.tblMain.getRow(this.tblMain.getSelectManager().getActiveRowIndex()).getCell("id").getValue();
			UIContext uiContext = new UIContext(this);
			if (null == infoId) {
				abort();
			}

			/*
			 * ��ѡ���¼��ID����
			 */
			uiContext.put(UIContext.ID, infoId);
			uiContext.put("pane", TaskEvaluationTypeEnum.SCHEDULE);
			uiContext.put("Owner", this);
			IUIFactory uiFactory = UIFactory.createUIFactory(UIFactoryName.MODEL);
			IUIWindow uiWindow = null;
			uiWindow = uiFactory.create(TaskEvaluationEditUI.class.getName(), uiContext, null, OprtState.VIEW);
			uiWindow.show();
		}

	}

	/**
	 * @description �����������ã���������Table˫������¼�
	 * @author �ܺ���
	 * @createDate 2011-08-10
	 * @param
	 * @return
	 * 
	 * @version EAS7.0
	 */
	protected void tblMass_tableClicked(KDTMouseEvent e) throws Exception {

		/*
		 * ȡ��ѡ����ID
		 */
		int tblMassIndex = this.tblMass.getSelectManager().getActiveRowIndex();
		// if(e.getClickCount() == 1){
		// this.tblMass.getRow(tblMass.getSelectManager().getActiveRowIndex()).
		// setCollapse(true);
		// }
		if (tblMassIndex != -1 && e.getClickCount() == 2) {

			Object infoId = this.tblMass.getRow(this.tblMass.getSelectManager().getActiveRowIndex()).getCell("id").getValue();
			UIContext uiContext = new UIContext(this);
			if (null == infoId) {
				abort();
			}
			/*
			 * ��ѡ���¼��ID����
			 */
			uiContext.put(UIContext.ID, infoId);
			uiContext.put("pane", TaskEvaluationTypeEnum.QUALITY);
			uiContext.put("owner", this);
			IUIFactory uiFactory = UIFactory.createUIFactory(UIFactoryName.MODEL);
			IUIWindow uiWindow = null;
			uiWindow = uiFactory.create(TaskEvaluationEditUI.class.getName(), uiContext, null, OprtState.VIEW);
			uiWindow.show();
		}
	}

	/**
	 * 
	 * @description �õ�ͨ�ò�ѯ
	 * @author �ܺ���
	 * @createDate 2011-08-17
	 * @param
	 * @return CommonQueryDialog
	 * 
	 * @version EAS7.0
	 */
	protected CommonQueryDialog initCommonQueryDialog() {
		if (null != commonQueryDialog) {
			return commonQueryDialog;
		}
		commonQueryDialog = super.initCommonQueryDialog();
		return commonQueryDialog;
	}

	// protected IQueryExecutor getQueryExecutor(IMetaDataPK pk,
	// EntityViewInfo evinfo) {
	//    	
	// FilterInfo filter = new FilterInfo();
	// FilterInfo stateFilter = null;
	// evinfo.clear();
	// /*
	// * �����ͨ�ò�ѯ��������˺�������������
	// */
	// if(initCommonQueryDialog() !=null){
	// if(commonQueryDialog.getCommonFilter() != null &&
	// commonQueryDialog.getCommonFilter().getFilterItems().size()!=0){
	// FilterInfo filterInfo = commonQueryDialog.getCommonFilter();
	// FilterInfo cuFilterInfo = new FilterInfo();
	// cuFilterInfo.getFilterItems().add(new
	// FilterItemInfo("CU.id",SysContext.getSysContext
	// ().getCurrentCtrlUnit().getId().toString()));
	// stateFilter = getStateFilter();
	// try {
	// if(stateFilter!=null){
	// filterInfo.mergeFilter(stateFilter, "AND");
	// }
	// filterInfo.mergeFilter(cuFilterInfo, "AND");
	// } catch (BOSException e) {
	// handUIException(e);
	// }
	// viewInfo.setFilter(filterInfo);
	// return super.getQueryExecutor(pk, evinfo);
	// }
	// }
	// return super.getQueryExecutor(pk, evinfo);
	// }

	/**
	 * output actionPageSetup_actionPerformed
	 */
	public void actionPageSetup_actionPerformed(ActionEvent e) throws Exception {
		super.actionPageSetup_actionPerformed(e);
	}

	/**
	 * output actionExitCurrent_actionPerformed
	 */
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
	 * output actionCalculator_actionPerformed
	 */
	public void actionCalculator_actionPerformed(ActionEvent e) throws Exception {
		super.actionCalculator_actionPerformed(e);
	}

	/**
	 * output actionExport_actionPerformed
	 */
	public void actionExport_actionPerformed(ActionEvent e) throws Exception {
		super.actionExport_actionPerformed(e);
	}

	/**
	 * output actionExportSelected_actionPerformed
	 */
	public void actionExportSelected_actionPerformed(ActionEvent e) throws Exception {
		super.actionExportSelected_actionPerformed(e);
	}

	/**
	 * output actionRegProduct_actionPerformed
	 */
	public void actionRegProduct_actionPerformed(ActionEvent e) throws Exception {
		super.actionRegProduct_actionPerformed(e);
	}

	/**
	 * output actionPersonalSite_actionPerformed
	 */
	public void actionPersonalSite_actionPerformed(ActionEvent e) throws Exception {
		super.actionPersonalSite_actionPerformed(e);
	}

	/**
	 * output actionProcductVal_actionPerformed
	 */
	public void actionProcductVal_actionPerformed(ActionEvent e) throws Exception {
		super.actionProcductVal_actionPerformed(e);
	}

	/**
	 * output actionExportSave_actionPerformed
	 */
	public void actionExportSave_actionPerformed(ActionEvent e) throws Exception {
		super.actionExportSave_actionPerformed(e);
	}

	/**
	 * output actionExportSelectedSave_actionPerformed
	 */
	public void actionExportSelectedSave_actionPerformed(ActionEvent e) throws Exception {
		super.actionExportSelectedSave_actionPerformed(e);
	}

	/**
	 * output actionKnowStore_actionPerformed
	 */
	public void actionKnowStore_actionPerformed(ActionEvent e) throws Exception {
		super.actionKnowStore_actionPerformed(e);
	}

	/**
	 * output actionAnswer_actionPerformed
	 */
	public void actionAnswer_actionPerformed(ActionEvent e) throws Exception {
		super.actionAnswer_actionPerformed(e);
	}

	/**
	 * output actionRemoteAssist_actionPerformed
	 */
	public void actionRemoteAssist_actionPerformed(ActionEvent e) throws Exception {
		super.actionRemoteAssist_actionPerformed(e);
	}

	/**
	 * output actionPopupCopy_actionPerformed
	 */
	public void actionPopupCopy_actionPerformed(ActionEvent e) throws Exception {
		super.actionPopupCopy_actionPerformed(e);
	}

	/**
	 * output actionHTMLForMail_actionPerformed
	 */
	public void actionHTMLForMail_actionPerformed(ActionEvent e) throws Exception {
		super.actionHTMLForMail_actionPerformed(e);
	}

	/**
	 * output actionExcelForMail_actionPerformed
	 */
	public void actionExcelForMail_actionPerformed(ActionEvent e) throws Exception {
		super.actionExcelForMail_actionPerformed(e);
	}

	/**
	 * output actionHTMLForRpt_actionPerformed
	 */
	public void actionHTMLForRpt_actionPerformed(ActionEvent e) throws Exception {
		super.actionHTMLForRpt_actionPerformed(e);
	}

	/**
	 * output actionExcelForRpt_actionPerformed
	 */
	public void actionExcelForRpt_actionPerformed(ActionEvent e) throws Exception {
		super.actionExcelForRpt_actionPerformed(e);
	}

	/**
	 * output actionLinkForRpt_actionPerformed
	 */
	public void actionLinkForRpt_actionPerformed(ActionEvent e) throws Exception {
		super.actionLinkForRpt_actionPerformed(e);
	}

	/**
	 * output actionPopupPaste_actionPerformed
	 */
	public void actionPopupPaste_actionPerformed(ActionEvent e) throws Exception {
		super.actionPopupPaste_actionPerformed(e);
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
	 * @description �����������ã���д���෽������tblMass�������޸�
	 * @author �ܺ���
	 * @createDate 2011-08-10
	 * @param activeRowIndex
	 * @return boolean
	 * 
	 * @version EAS7.0
	 */
	protected boolean isSystemDefaultData(int activeRowIndex) {
		return false;
	}

	/**
	 * @description �����������ã���д���෽������tblMass�������޸�<�Ƿ�ѡ��������>
	 * @author �ܺ���
	 * @createDate 2011-08-10
	 * @param
	 * @return
	 * 
	 * @version EAS7.0
	 */
	public void checkSelected() {
		int index = this.taskEvaluationPane.getSelectedIndex();
		if (index == 0) {
			super.checkSelected();
		} else {
			if (tblMass.getRowCount() == 0 || tblMass.getSelectManager().size() == 0) {
				MsgBox.showWarning(this, EASResource.getString("com.kingdee.eas.framework.FrameWorkResource.Msg_MustSelected"));
				SysUtil.abort();
			}
		}
	}

	/**
	 * @description �����������ã���д���෽������tblMass�������޸�
	 * @author �ܺ���
	 * @createDate 2011-08-10
	 * @param
	 * @return String
	 * 
	 * @version EAS7.0
	 */
	protected String getSelectedKeyValue() {
		int index = this.taskEvaluationPane.getSelectedIndex();
		if (index == 0) {
			return super.getSelectedKeyValue();
		} else {
			String keyFiledName = subKeyFieldName != null ? subKeyFieldName : getKeyFieldName();
			int selectRows[] = KDTableUtil.getSelectedRows(tblMass);
			selectIndex = -1;
			if (selectRows.length > 0) {
				selectIndex = selectRows[0];
			}
			return ListUiHelper.getSelectedKeyValue(selectRows, tblMass, keyFiledName);
		}

	}

	/**
	 * @description �����������ã������޸��¼�
	 * @author �ܺ���
	 * @createDate 2011-08-10
	 * @param
	 * @return String
	 * 
	 * @version EAS7.0
	 */
	public void actionEdit_actionPerformed(ActionEvent e) throws Exception {
		int index = this.taskEvaluationPane.getSelectedIndex();
		if (index == 1) {
			tblMain = tblMass;
		} else {
			tblMain = table;
		}
		// �Ƿ�ѡ����
		checkSelected();

		super.actionEdit_actionPerformed(e);

	}

	/**
	 * @description ����ɾ������
	 * @author �ܺ���
	 * @createDate 2011-08-09
	 * @param
	 * @return
	 * 
	 * @version EAS7.0
	 */
	public void actionRemove_actionPerformed(ActionEvent e) throws Exception {
		checkSelected();
		int index = this.taskEvaluationPane.getSelectedIndex();
		if (index == 1) {
			tblMain = tblMass;
		} else {
			tblMain = table;
		}
		/*
		 * �õ�ѡ����ID
		 */
		List idList = getSelectedIdValues();
		int yes = MsgBox.showConfirm2("�Ƿ�ȷ��ɾ��?");
		if (yes > 0) {
			SysUtil.abort();
		} else {
			ITaskEvaluation instance = TaskEvaluationFactory.getRemoteInstance();
			/*
			 * ����ɾ��
			 */
			for (int i = 0; i < idList.size(); i++) {
				boolean falg = instance.quoteDelete(idList.get(i).toString());
				if (falg) {
					MsgBox.showError("�ü�¼�ѱ����ã�������ɾ��!");
					SysUtil.abort();
				} else {
					instance.delete(new ObjectUuidPK(idList.get(i).toString()));
				}
			}
		}
		refresh(e);
	}

	/**
	 * @description ȡ��ǰҳǩtable
	 * @author �ܺ���
	 * @createDate 2011-08-08
	 * @param
	 * @return
	 * 
	 * @version EAS7.0
	 */
	public void getCurPane() {
		int index = this.taskEvaluationPane.getSelectedIndex();
		if (index == 1) {
			tblMain = tblMass;
		} else {
			tblMain = table;
		}
		// Ĭ��ѡ���һ��
		this.tblMain.getSelectManager().select(0, 0);
	}

	/**
	 * output actionRefresh_actionPerformed
	 */
	public void actionRefresh_actionPerformed(ActionEvent e) throws Exception {
		super.actionRefresh_actionPerformed(e);
		getCurPane();

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
	 * @description ��λ����
	 * @author �ܺ���
	 * @createDate 2011-08-16
	 * @param
	 * @return
	 * @version EAS7.0
	 * 
	 */
	public void actionLocate_actionPerformed(ActionEvent e) throws Exception {
		int index = this.taskEvaluationPane.getSelectedIndex();
		if (index == 1) {
			tblMain = tblMass;
		} else {
			tblMain = table;
		}
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

	/**
	 * output actionCancel_actionPerformed
	 */
	public void actionCancel_actionPerformed(ActionEvent e) throws Exception {
		super.actionCancel_actionPerformed(e);
	}

	/**
	 * output actionCancelCancel_actionPerformed
	 */
	public void actionCancelCancel_actionPerformed(ActionEvent e) throws Exception {
		super.actionCancelCancel_actionPerformed(e);
	}

	/**
	 * output actionQueryScheme_actionPerformed
	 */
	public void actionQueryScheme_actionPerformed(ActionEvent e) throws Exception {
		super.actionQueryScheme_actionPerformed(e);
	}

}