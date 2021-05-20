/**
 * output package name
 */
package com.kingdee.eas.fdc.sellhouse.client;

import java.awt.event.ActionEvent;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Map;

import org.apache.log4j.Logger;

import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.swing.KDTextField;
import com.kingdee.bos.ctrl.swing.event.DataChangeEvent;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.eas.base.permission.UserInfo;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.fdc.basecrm.client.FDCSysContext;
import com.kingdee.eas.fdc.basedata.MoneySysTypeEnum;
import com.kingdee.eas.fdc.basedata.client.FDCClientVerifyHelper;
import com.kingdee.eas.fdc.sellhouse.MortagageControlFactory;
import com.kingdee.eas.fdc.sellhouse.MortagageControlInfo;
import com.kingdee.eas.fdc.sellhouse.MortagageEnum;
import com.kingdee.eas.fdc.sellhouse.RoomInfo;
import com.kingdee.eas.fdc.sellhouse.RoomSellStateEnum;
import com.kingdee.eas.fdc.sellhouse.SellProjectInfo;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.MsgBox;

/**
 * @auther qinyouzhen,��¥�Ż� V 7.0.3��
 * @date 2011-06-15 ��Ѻ���Ʊ༭
 */
public class MortagageControlEditUI extends AbstractMortagageControlEditUI {
	private static final Logger logger = CoreUIObject
			.getLogger(MortagageControlEditUI.class);
	private Map map = FDCSysContext.getInstance().getOrgMap();

	/**
	 * output class constructor
	 */
	public MortagageControlEditUI() throws Exception {
		super();
	}

	public void onLoad() throws Exception {
		super.onLoad();
		SellProjectInfo sellproject = (SellProjectInfo) this.getUIContext()
				.get("sellProjectInfo");
		NewFDCRoomPromptDialog dialog = new NewFDCRoomPromptDialog(
				Boolean.FALSE, null, null, MoneySysTypeEnum.SalehouseSys, null,
				sellproject);

		prmtRoomEntry.setSelector(dialog);
		btnAuditResult.setVisible(false);
		if (!map.containsKey(SysContext.getSysContext().getCurrentOrgUnit()
				.getId().toString())) {
			this.actionAddNew.setEnabled(false);
			this.actionEdit.setEnabled(false);
			actionRemove.setEnabled(false);
		} else {
			if (STATUS_ADDNEW.equals(getOprtState())) {
				actionEdit.setEnabled(false);
				actionRemove.setEnabled(false);
			} else if (STATUS_EDIT.equals(getOprtState())) {
				actionEdit.setEnabled(false);
				refreshButton();
			} else if (STATUS_VIEW.equals(getOprtState())) {
				actionSubmit.setEnabled(false);
				refreshButton();
			}
		}
		btnAudit.setVisible(false);
		btnUnAudit.setVisible(false);
		btnAntiMortagage.setVisible(false);
	}

	public void storeFields() {
		if (this.prmtRoomEntry.getValue() != null) {
			if (this.prmtRoomEntry.getValue() instanceof RoomInfo) {
				this.editData.setRoom((RoomInfo) this.prmtRoomEntry.getValue());
			} else {
				Object[] object = (Object[]) this.prmtRoomEntry.getValue();
				if (object != null && object.length > 0) {
					this.editData.setRoom((RoomInfo) object[0]);
				}
			}
		}
		super.storeFields();
	}

	public void loadFields() {
		super.loadFields();
		prmtRoomEntry.setValue(this.editData.getRoom());

	}

	/**
	 * ��ѯ��Room
	 */
	public SelectorItemCollection getSelectors() {
		SelectorItemCollection sels = new SelectorItemCollection();
		sels.add("id");
		sels.add("number");
		sels.add("sellProject");
		// sels.add("roomEntry.room.name"); //ȥ�����ԣ�����ʹ�÷�¼
		sels.add("bizDate");
		sels.add("auditTime");
		sels.add("isSell");
		sels.add("description");
		sels.add("creator.id");
		sels.add("creator.name");
		sels.add("auditor.id");
		sels.add("auditor.name");
		sels.add("mortagageState");
		sels.add("antiMortagagor");
		sels.add("antiMortagageDate");
		sels.add("handler.id");
		sels.add("handler.name");
		sels.add("room.id");// ��������ԣ�����ʹ�÷�¼
		sels.add("room.name");
		sels.add("CU.*");
		return sels;
	}

	/**
	 * ��ʼ����ť
	 */
	protected void initDataStatus() {
		super.initDataStatus();
		actionWorkFlowG.setVisible(false);
		actionCreateFrom.setVisible(false);
		actionCreateTo.setVisible(false);
		actionMultiapprove.setVisible(false);
		actionNextPerson.setVisible(false);
		actionCalculator.setVisible(false);
		actionAttachment.setVisible(false);
		this.menuBiz.setVisible(false);
		this.menuTable1.setVisible(false);
		this.actionTraceDown.setVisible(false);
		this.actionTraceUp.setVisible(false);
		this.actionAddLine.setVisible(false);
		this.actionRemoveLine.setVisible(false);
		this.actionInsertLine.setVisible(false);
		this.actionCopy.setVisible(false);
		this.actionCancel.setVisible(false);
		this.actionCancelCancel.setVisible(false);
		this.actionCopyFrom.setVisible(false);
		this.MenuItemAttachment.setVisible(false);
		this.chkMenuItemSubmitAndAddNew.setSelected(true);
		this.menuSubmitOption.setVisible(false);
		btnFirst.setVisible(false);
		btnPre.setVisible(false);
		btnNext.setVisible(false);
		btnLast.setVisible(false);
	}

	private void refreshButton() {
		if (map.containsKey(SysContext.getSysContext().getCurrentOrgUnit()
				.getId().toString())) {
			if (editData.getMortagageState() != null) {
				if (editData.getMortagageState()
						.equals(MortagageEnum.SUBMITTED)) {
					btnSave.setEnabled(false);
				}
			}
		}
	}

	/**
	 * ѡȡRoom
	 * 
	 */
	protected void prmtRoomEntry_dataChanged(DataChangeEvent e)
			throws Exception {
		super.prmtRoomEntry_dataChanged(e);
		RoomInfo room = (RoomInfo) this.editData.getRoom();
		if (room != null && room.getSellState() != null) {
			if ((!room.getSellState().equals(RoomSellStateEnum.Init)
					&& !room.getSellState().equals(RoomSellStateEnum.OnShow)
					&& !room.getSellState().equals(RoomSellStateEnum.KeepDown) && !room
					.getSellState().equals(RoomSellStateEnum.SincerPurchase))) {
				MsgBox.showError("δ���̡����ۡ�������ԤԼ�ź�״̬����ſ��Ա���Ѻ");
				prmtRoomEntry.setValue(null);
				SysUtil.abort();
			} else if (room.isIsMortagaged()) {
				MsgBox.showError("�����ѱ���Ѻ��������ѡ��");
				prmtRoomEntry.setValue(null);
				SysUtil.abort();
			}
			editData.setRoom(room);
		}
	}

	/**
	 * ����
	 */
	protected IObjectValue createNewData() {
		MortagageControlInfo mortagageControl = new MortagageControlInfo();
		mortagageControl.setCreator((UserInfo) (SysContext.getSysContext()
				.getCurrentUserInfo()));
		mortagageControl.setHandler((UserInfo) (SysContext.getSysContext()
				.getCurrentUserInfo()));
		mortagageControl.setCreateTime(new Timestamp(new Date().getTime()));
		SellProjectInfo sellProjectInfo = (SellProjectInfo) this.getUIContext()
				.get("sellProjectInfo");
		mortagageControl.setSellProject(sellProjectInfo);
		mortagageControl.setCU(SysContext.getSysContext().getCurrentCtrlUnit());
		return mortagageControl;
	}

	public void actionAddNew_actionPerformed(ActionEvent e) throws Exception {
		super.actionAddNew_actionPerformed(e);
	}

	public void actionSave_actionPerformed(ActionEvent e) throws Exception {
		editData.setMortagageState(MortagageEnum.SAVED);
		super.actionSave_actionPerformed(e);
	}

	public void actionSubmit_actionPerformed(ActionEvent e) throws Exception {
		editData.setMortagageState(MortagageEnum.SUBMITTED);
		super.actionSubmit_actionPerformed(e);
	}

	public void actionEdit_actionPerformed(ActionEvent e) throws Exception {
		VerifyAuditOrUnAuditById("�޸�");
		super.actionEdit_actionPerformed(e);
		refreshButton();
	}

	public void actionRemove_actionPerformed(ActionEvent e) throws Exception {
		VerifyAuditOrUnAuditById("ɾ��");
		super.actionRemove_actionPerformed(e);
	}

	/**
	 * �����Ѻ
	 */
	public void actionAntiMortagage_actionPerformed(ActionEvent e)
			throws Exception {
		super.actionAntiMortagage_actionPerformed(e);
		boolean antiMortagageResult = MortagageControlFactory
				.getRemoteInstance().antiMortagage(editData);
		if (antiMortagageResult) {
			MsgBox.showInfo("�����Ѻ�ɹ�");
		} else {
			MsgBox.showInfo("����״̬������Ҫ��");
		}
	}

	/**
	 * ����
	 */
	public void actionAudit_actionPerformed(ActionEvent e) throws Exception {
		boolean auditResult = MortagageControlFactory.getRemoteInstance()
				.audit(editData);
		if (auditResult) {
			MsgBox.showInfo("���������ɹ�");
		} else {
			MsgBox.showInfo("����״̬������Ҫ��");
		}
	}

	/**
	 * ������
	 */
	public void actionUnAudit_actionPerformed(ActionEvent e) throws Exception {
		boolean unAuditResult = MortagageControlFactory.getRemoteInstance()
				.unAudit(editData);
		if (unAuditResult) {
			MsgBox.showInfo("���ݷ������ɹ�");
		} else {
			MsgBox.showInfo("����״̬������Ҫ��");
		}
	}

	/**
	 * �жϼ�¼�Ƿ�����������Ѻ,�޸ĺ�ɾ��ʱ����
	 * 
	 * @param words
	 * @param selectID
	 * @return
	 * @throws Exception
	 */
	public void VerifyAuditOrUnAuditById(String words) throws Exception {
		if (editData != null && !editData.getId().equals("")) {
			MortagageControlInfo info = MortagageControlFactory
					.getRemoteInstance().getMortagageControlInfo(
							"select id,mortagageState where id='"
									+ editData.getId() + "'");
			if (info.getMortagageState().equals(MortagageEnum.AUDITTED)) {
				MsgBox.showWarning("�������Ѿ�����������" + words + "!");
				this.abort();
			} else if (info.getMortagageState().equals(MortagageEnum.AUDITTING)) {
				MsgBox.showWarning("�������������У�����" + words + "!");
				this.abort();
			} else if (info.getMortagageState().equals(
					MortagageEnum.ANTIMORTAGAGE)) {
				MsgBox.showWarning("�������Ѿ������Ѻ������" + words + "!");
				this.abort();
			}
		}
	}

	protected void attachListeners() {

	}

	protected void detachListeners() {
	}

	protected ICoreBase getBizInterface() throws Exception {
		return MortagageControlFactory.getRemoteInstance();
	}

	protected KDTable getDetailTable() {
		return null;
	}

	protected KDTextField getNumberCtrl() {
		return this.txtNumber;
	}

	protected void verifyInput(ActionEvent e) throws Exception {
		super.verifyInput(e);
		FDCClientVerifyHelper.verifyEmpty(this, this.pkBizDate);
		FDCClientVerifyHelper.verifyEmpty(this, this.prmtRoomEntry);
	}
}