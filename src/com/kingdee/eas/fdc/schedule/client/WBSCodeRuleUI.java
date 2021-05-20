/**
 * output package name
 */
package com.kingdee.eas.fdc.schedule.client;

import java.awt.event.ActionEvent;

import org.apache.log4j.Logger;

import com.kingdee.bos.ctrl.extendcontrols.KDBizMultiLangBox;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTDefaultCellEditor;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectBlock;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectManager;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent;
import com.kingdee.bos.ctrl.swing.KDFormattedTextField;
import com.kingdee.bos.ctrl.swing.KDTextField;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.util.IUIActionPostman;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.basedata.org.OrgConstants;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.fdc.basedata.FDCDataBaseInfo;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.basedata.client.FDCBaseDataClientCtrler;
import com.kingdee.eas.fdc.basedata.client.FDCBaseDataClientUtils;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.schedule.WBSCodeRuleEntryInfo;
import com.kingdee.eas.fdc.schedule.WBSCodeRuleFactory;
import com.kingdee.eas.fdc.schedule.WBSCodeRuleInfo;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.framework.client.FrameWorkClientUtils;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.MsgBox;
import com.kingdee.jdbc.rowset.IRowSet;

/**
 * output class name
 */
public class WBSCodeRuleUI extends AbstractWBSCodeRuleUI {
	private static final Logger logger = CoreUIObject
			.getLogger(WBSCodeRuleUI.class);

	public WBSCodeRuleUI() throws Exception {
		super();
	}

	protected FDCDataBaseInfo getEditData() {
		return editData;
	}

	protected KDBizMultiLangBox getNameCtrl() {
		return kDBizMultiLangBox1;
	}

	protected KDTextField getNumberCtrl() {
		return kDTextField1;
	}

	protected IObjectValue createNewData() {
		WBSCodeRuleInfo wbsCodeRuleInfo = new WBSCodeRuleInfo();
		wbsCodeRuleInfo.setName(SysContext.getSysContext().getCurrentFIUnit()
				.getName()+ "WBS�������");
		wbsCodeRuleInfo.setNumber(SysContext.getSysContext().getCurrentFIUnit()
				.getNumber()+ "WBSCodingRule");
		return wbsCodeRuleInfo;
	}

	protected ICoreBase getBizInterface() throws Exception {
		return WBSCodeRuleFactory.getRemoteInstance();
	}

	protected void inOnload() throws Exception {
		// ����Ƿ��ⲿ����ʽ����ֵ����
		if (getUIContext().get(UIContext.INIT_DATAOBJECT) != null
				&& getUIContext().get(UIContext.INIT_DATAOBJECT) instanceof IObjectValue) {
			setDataObject((IObjectValue) getUIContext().get(
					UIContext.INIT_DATAOBJECT));
		} else {
			// �ɴ��ݹ�����ID��ȡֵ����
			if (getUIContext().get(UIContext.ID) == null) {
				String s = EASResource.getString(FrameWorkClientUtils.strResource
								+ "Msg_IDIsNull");
				MsgBox.showError(s);
				SysUtil.abort();
			}
			IObjectPK pk = new ObjectUuidPK(BOSUuid.read(getUIContext().get(
					UIContext.ID).toString()));
			setDataObject(getValue(pk));
		}
		loadFields();
		// Ϊ��BOTP�ļ��ɣ��ṩ��BillEditUI����ʹ��
		Object id = getUIContext().get(UIContext.ID);
		if (id != null) {
			prepareData(id.toString());
		}
	}

	
	/**
	 * Ŀǰ��֧��һ��������������������Ҫ�޸Ĵ��࣬Ŀǰ����ʽ��
	 * 1.��ѯ�Ƿ������ݣ��޵����Ǽ��ţ���Ϊ����������ΪView
	 * 2.�����ݵ���δ���ã����Ǽ�������Ϊ�޸ģ�����ΪView
	 * 3.���������Ѿ����ã���ΪView
	 * 
	 **/
	protected void loadData() throws Exception {
		kdtEntry.checkParsed();
		FDCSQLBuilder builder = new FDCSQLBuilder();
		builder.appendSql("select fid,fcontrolunitid,fisenabled from t_sch_WBSCodeRule");
		IRowSet rowSet = builder.executeQuery();
		if (rowSet.size() < 1){
			if(isGroup()){
				setOprtState(OprtState.ADDNEW);
			}
		}else {
			String tempId = null;
			int isEnabled = 0;
			while (rowSet.next()) {
				tempId = rowSet.getString("fid");
				isEnabled = rowSet.getInt("fisenabled");
			}
			getUIContext().put(UIContext.ID, tempId);
			
			if (isEnabled == 0 && isGroup()) {
				setOprtState(OprtState.EDIT);
			} else {
				setOprtState(OprtState.VIEW);
			}
		}
		if (STATUS_ADDNEW.equals(getOprtState())) {
			// ������������
			if (getUIContext().get(UIContext.INIT_DATAOBJECT) != null
					&& getUIContext().get(UIContext.INIT_DATAOBJECT) instanceof IObjectValue) {
				IObjectValue objValue = (IObjectValue) getUIContext().get(
						UIContext.INIT_DATAOBJECT);
				applyDefaultValue(objValue);
				setDataObject(objValue);
			} else {
				// ��Ϊ���ε���setDataOjbect���������ش���Ӱ������(��Ҫ��Agent��ȸ��ƺ�ʱ)��
				// �˴�������汾�������С�2006-10-20
				this.editData = (WBSCodeRuleInfo) createNewData();
				applyDefaultValue(this.editData);
				getUILifeCycleHandler().fireOnCreateNewData(this, editData);
				setDataObject(this.editData);
			}
			loadFields();
		} else { // ��������
			inOnload();
		}
		initOldData(this.editData);
		showMessageForStatus();
	}

	private boolean isGroup() {
		return OrgConstants.DEF_CU_ID.equals(SysContext.getSysContext().getCurrentCtrlUnit().
				getId().toString());
	}


	
	protected void initWorkButton() {
		super.initWorkButton();
		actionAddNew.setEnabled(false);
		actionAddNew.setVisible(false);
		actionEdit.setEnabled(false);
		actionEdit.setVisible(false);
		actionRemove.setEnabled(false);
		actionRemove.setVisible(false);
		actionAddLine.setEnabled(true);
		actionInsertLine.setEnabled(true);
		actionRemoveLine.setEnabled(true);
		menuView.setEnabled(false);
		menuView.setVisible(false);
		actionCopy.setVisible(false);
//		actionCancel.setEnabled(false);
//		actionCancelCancel.setEnabled(false);
//		actionAddLine.setEnabled(false);
//		actionRemoveLine.setEnabled(false);
//		actionInsertLine.setEnabled(false);
	}
//	�����
	public void actionAddLine_actionPerformed(ActionEvent e) throws Exception {
		if (FDCSCHClientHelper.isSystemDefaultData(editData)) {
			MsgBox.showError(EASResource.getString(FDCBaseDataClientUtils.FDCBASEDATA_RESOURCE, "operate_SysData"));
			return;
		}
		getCtrler().checkPermission(FDCBaseDataClientCtrler.ACTION_ADDNEW);
		if(editData.isIsEnabled() && isGroup()){
			FDCMsgBox.showWarning("��������Ѿ����ã�������������");
			SysUtil.abort();
		}
		if (kdtEntry == null)	return;
		WBSCodeRuleEntryInfo detailData = new WBSCodeRuleEntryInfo();
		IRow row = kdtEntry.addRow();
		getUILifeCycleHandler().fireOnAddNewLine(kdtEntry, detailData);
		dataBinder.loadLineFields(kdtEntry, row, detailData);
	}
//	������
	public void actionInsertLine_actionPerformed(ActionEvent e)	throws Exception {
		if (FDCSCHClientHelper.isSystemDefaultData(editData)) {
			MsgBox.showError(EASResource.getString(FDCBaseDataClientUtils.FDCBASEDATA_RESOURCE, "operate_SysData"));
			return;
		}
		if(kdtEntry.getSelectManager().getActiveRowIndex() == 0){
			FDCMsgBox.showWarning("������Ĭ�ϱ������ǰ������");
			SysUtil.abort();
		}
		getCtrler().checkPermission(FDCBaseDataClientCtrler.ACTION_MODIFY);
		if(editData.isIsEnabled()){
			FDCMsgBox.showWarning("��������Ѿ����ã�������������");
			SysUtil.abort();
		}
		WBSCodeRuleEntryInfo detailData = new WBSCodeRuleEntryInfo();
		IRow row = null;
		if (kdtEntry.getSelectManager().size() > 0) {
			int top = kdtEntry.getSelectManager().get().getTop();
			if (isTableColumnSelected(kdtEntry)) {
				row = kdtEntry.addRow();
			} else {
				row = kdtEntry.addRow(top);
			}
		} else {
			row = kdtEntry.addRow();
		}
		getUILifeCycleHandler().fireOnAddNewLine(kdtEntry, detailData);
		dataBinder.loadLineFields(kdtEntry, row, detailData);
	}
//	ɾ����
	public void actionRemoveLine_actionPerformed(ActionEvent e)	throws Exception {
		if (FDCSCHClientHelper.isSystemDefaultData(editData)) {
			MsgBox.showError(EASResource.getString(FDCBaseDataClientUtils.FDCBASEDATA_RESOURCE, "operate_SysData"));
			return;
		}

		getCtrler().checkPermission(FDCBaseDataClientCtrler.ACTION_DELETE);
		if(editData.isIsEnabled()){
			FDCMsgBox.showWarning("��������Ѿ����ã�������ɾ����");
			SysUtil.abort();
		}
		int activeRowIndex = kdtEntry.getSelectManager().getActiveRowIndex();
		if (activeRowIndex < 0) {
			FDCMsgBox.showWarning("����ѡ���У�");
			SysUtil.abort();
		}
		if(kdtEntry.getCell(activeRowIndex, "id").getValue()!= null 
				&& kdtEntry.getCell(activeRowIndex, "id").getValue().toString().equals(WBSCodeRuleEntryInfo.DEFAULT_ID)){
			FDCMsgBox.showWarning("����ɾ��Ĭ�ϱ������");
			SysUtil.abort();
		}
		kdtEntry.removeRow(activeRowIndex);
	}
//	���ò���
	public void actionCancel_actionPerformed(ActionEvent e) throws Exception {
		FDCMsgBox.showError("WBS����������ú�������� ��");
		SysUtil.abort();
		super.actionCancel_actionPerformed(e);
		setOprtState(OprtState.EDIT);
	}
//	���ò���
	public void actionCancelCancel_actionPerformed(ActionEvent e)
			throws Exception {
		super.actionCancelCancel_actionPerformed(e);
		setOprtState(OprtState.VIEW);
	}
//	�����ύ����
	public void actionSubmit_actionPerformed(ActionEvent e) throws Exception {
		if (FDCSCHClientHelper.isSystemDefaultData(editData)) {
			MsgBox.showError(EASResource.getString(FDCBaseDataClientUtils.FDCBASEDATA_RESOURCE, "operate_SysData"));
			return;
		}
		getCtrler().checkPermission(FDCBaseDataClientCtrler.ACTION_MODIFY);
		 if(getActionFromActionEvent(e) == null && !(checkClickTime()))
	        {
	            return;
	        }
	        //Ϊ��BOTP�ļ��ɣ��ṩ��BillEditUI����ʹ��
	        IObjectPK pk = null;
	        doBeforeSubmit(e);
	        IUIActionPostman post = prepareSubmit();
	        if(post != null) {
	        	post.callHandler();
	        }
	        pk = runSubmit();
	        doAfterSubmit(pk);
		/*if(editData.isIsEnabled()){
			btnCancelCancel.setVisible(false);
			actionCancelCancel.setEnabled(false);
			btnCancel.setVisible(true);
			actionCancel.setEnabled(true);
		}else{
			btnCancelCancel.setVisible(true);
			actionCancelCancel.setEnabled(true);
			btnCancel.setVisible(false);
			actionCancel.setEnabled(false);
		}*/
	}
	
	protected final boolean isTableColumnSelected(KDTable table) {
		if (table.getSelectManager().size() > 0) {
			KDTSelectBlock block = table.getSelectManager().get();

			if ((block.getMode() == KDTSelectManager.COLUMN_SELECT)
					|| (block.getMode() == KDTSelectManager.TABLE_SELECT)) {
				return true;
			}
		}
		return false;
	}

	protected void verifyInput(ActionEvent e) throws Exception {
	}

	protected void kdtEntry_editStopped(KDTEditEvent e) throws Exception {
		if (e.getColIndex() == kdtEntry.getColumnIndex("level"))
			checkLevel(e);
		else if (e.getColIndex() == kdtEntry.getColumnIndex("length"))
			checkLength(e);
	}

	private void checkLength(KDTEditEvent e) {
		if (e.getValue() != null
				&& Integer.parseInt(e.getValue().toString()) > 5) {
			FDCMsgBox.showWarning("���볤�Ȳ��ܴ���5��");
			kdtEntry.getCell(e.getRowIndex(), e.getColIndex()).setValue(e.getOldValue());
			SysUtil.abort();
		} else if (e.getValue() != null
				&& Integer.parseInt(e.getValue().toString()) <= 0) {
			FDCMsgBox.showWarning("���볤�ȱ������0��");
			kdtEntry.getCell(e.getRowIndex(), e.getColIndex()).setValue(e.getOldValue());
			SysUtil.abort();
		}
	}

	private void checkLevel(KDTEditEvent e) {
		int level = 0;
		if (kdtEntry.getCell(e.getRowIndex(), "level").getValue() != null)
			level = Integer.parseInt(kdtEntry.getCell(e.getRowIndex(), "level").getValue().toString());
		else return;
		if (level <= 0) {
			kdtEntry.getCell(e.getRowIndex(), "level").setValue(e.getOldValue());
			FDCMsgBox.showWarning("���α������0��");
			SysUtil.abort();
		}
		if (level != 0) {
			for (int i = 0; i < kdtEntry.getRowCount(); i++) {
				if (i == e.getRowIndex())
					continue;
				if (kdtEntry.getCell(i, "level").getValue() != null
						&& level == Integer.parseInt(kdtEntry.getCell(i,"level").getValue().toString())) {
					kdtEntry.getCell(e.getRowIndex(), "level").setValue(e.getOldValue());
					FDCMsgBox.showWarning("���" + (i+1) + "�еļ����ظ���");
					SysUtil.abort();
				}
			}
		}
	}

	private void initDetailTable() {
		KDFormattedTextField weightField = new KDFormattedTextField(
				KDFormattedTextField.INTEGER);
		weightField.setSupportedEmpty(true);
		KDTDefaultCellEditor editor = new KDTDefaultCellEditor(weightField);
		kdtEntry.getColumn("length").setEditor(editor);
		kdtEntry.getColumn("level").setEditor(editor);
		if(OprtState.VIEW.equals(getOprtState())){
			kdtEntry.getColumn("length").getStyleAttributes().setLocked(true);
			kdtEntry.getColumn("level").getStyleAttributes().setLocked(true);
		}else{
			kdtEntry.getColumn("length").getStyleAttributes().setLocked(false);
			kdtEntry.getColumn("level").getStyleAttributes().setLocked(false);
		}
	}

	public void onLoad() throws Exception {
		super.onLoad();
		initDetailTable();
		kdtEntry.getRow(0).getStyleAttributes().setLocked(true);
		actionCancel.setVisible(false);
		btnCancel.setVisible(false);
		menuItemCancel.setVisible(false);
	}

	protected void beforeStoreFields(ActionEvent e) throws Exception {
		for (int i = 1; i < kdtEntry.getRowCount(); i++) {
			if ((kdtEntry.getCell(i, "length").getValue() == null)
					|| (Integer.parseInt(kdtEntry.getCell(i, "length")
							.getValue().toString()) == 0)) {
				FDCMsgBox.showWarning("��" + (i+1) + "�еı��볤�Ȳ���Ϊ�ջ�Ϊ0��");
				SysUtil.abort();
			}
			if ((kdtEntry.getCell(i, "level").getValue() == null)
					|| (Integer.parseInt(kdtEntry.getCell(i, "level")
							.getValue().toString()) == 0)) {
				FDCMsgBox.showWarning("��" + (i+1) + "�еļ��β���Ϊ�ջ�Ϊ0��");
				SysUtil.abort();
			}
		}
		super.beforeStoreFields(e);
	}

	protected void doAfterSubmit(IObjectPK pk) throws Exception {
		super.doAfterSubmit(pk);
		setOprtState(OprtState.EDIT);
		
		inOnload();
		if(OprtState.EDIT.equals(getOprtState())){
			actionCancelCancel.setEnabled(true);
			btnCancelCancel.setEnabled(true);
		}
	}


	
	public SelectorItemCollection getSelectors() {
        SelectorItemCollection sic = new SelectorItemCollection();
        sic.add(new SelectorItemInfo("entrys.length"));
        sic.add(new SelectorItemInfo("entrys.level"));
        sic.add(new SelectorItemInfo("entrys.*"));
        sic.add(new SelectorItemInfo("name"));
        sic.add(new SelectorItemInfo("number"));
        sic.add(new SelectorItemInfo("isEnabled"));
        sic.add(new SelectorItemInfo("CU"));
        return sic;
	}
	
	public void onShow() throws Exception {
		super.onShow();
		btnCancel.setVisible(false);
		btnCancelCancel.setVisible(true);
	}
}