/**
 * output package name
 */
package com.kingdee.eas.fdc.schedule.client;

import java.awt.event.ActionEvent;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.log4j.Logger;

import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTDataRequestManager;
import com.kingdee.bos.ctrl.kdf.table.KDTDefaultCellEditor;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectManager;
import com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent;
import com.kingdee.bos.ctrl.kdf.table.util.KDTableUtil;
import com.kingdee.bos.ctrl.swing.KDDatePicker;
import com.kingdee.bos.ctrl.swing.KDTextField;
import com.kingdee.bos.ctrl.swing.util.CtrlCommonConstant;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.base.permission.UserInfo;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.schedule.BizTypeFactory;
import com.kingdee.eas.fdc.schedule.BizTypeInfo;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.MsgBox;

/**
 * ҵ�����ͻ�������<br>
 * ��ҳ�������ʱ���ͱ༭����Ĺ��ܣ������ڴ���ɾ�Ĳ�����<br>
 * �ⶼ�ݳ����BT��������
 * 
 * @author emanon
 */
public class BizTypeListUI extends AbstractBizTypeListUI {
	private static final Logger logger = CoreUIObject
			.getLogger(BizTypeListUI.class);

	// ���ڱ������иı��˵��У����ڱ���ʱʹ�ã��������Բ���һ�α���ȫ����
	Map changedRow = null;
	// һ���б�־������־Ϊnewʱ����������û���棬��ֱ��ɾ��
	private static final String NEW = "new";

	/**
	 * output class constructor
	 */
	public BizTypeListUI() throws Exception {
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
		initTable();
		changedRow = new HashMap();
		actionSave.setEnabled(true);
		btnSave.setIcon(EASResource.getIcon("imgTbtn_save"));
		menuItemSave.setIcon(EASResource.getIcon("imgTbtn_save"));
	}

	/**
	 * ��ʼ�����<br>
	 * 1���������б���棬�����Ƚ��������ÿɶ�дȨ��<br>
	 * 2������ʵģʽȡ���͵�Ԫ��ѡ��ģʽ<br>
	 * 3�����õ�Ԫ��༭��
	 */
	private void initTable() {
		tblMain.getStyleAttributes().setLocked(false);
		tblMain.setAccessAuthority(CtrlCommonConstant.AUTHORITY_COMMON);
		tblMain.getDataRequestManager().setDataRequestMode(
				KDTDataRequestManager.REAL_MODE);
		tblMain.getSelectManager().setSelectMode(
				KDTSelectManager.MULTIPLE_CELL_SELECT);

		KDTextField txtNum = new KDTextField();
		txtNum.setMaxLength(40);
		txtNum.setRequired(true);
		tblMain.getColumn("number").setEditor(new KDTDefaultCellEditor(txtNum));
		tblMain.getColumn("number").setRequired(true);

		KDTextField txtName = new KDTextField();
		txtName.setMaxLength(80);
		txtName.setRequired(true);
		tblMain.getColumn("name").setEditor(new KDTDefaultCellEditor(txtName));
		tblMain.getColumn("name").setRequired(true);

		KDTextField txtDesc = new KDTextField();
		txtDesc.setMaxLength(200);
		tblMain.getColumn("description").setEditor(
				new KDTDefaultCellEditor(txtDesc));

		KDDatePicker pkCreateTime = new KDDatePicker();
		tblMain.getColumn("createTime").setEditor(
				new KDTDefaultCellEditor(pkCreateTime));
	}

	/**
	 * ����info��row
	 * 
	 * @param row
	 * @param info
	 */
	protected void loadRow(IRow row, BizTypeInfo info) {
		if (row == null || info == null) {
			return;
		}
		if (info.getId() != null) {
			row.getCell("id").setValue(info.getId().toString());
		}
		row.getCell("number").setValue(info.getNumber());
		row.getCell("name").setValue(info.getName());
		row.getCell("description").setValue(info.getDescription());
		if (info.getCreator() != null) {
			row.getCell("creator.id").setValue(
					info.getCreator().getId().toString());
		}
		row.getCell("createTime").setValue(info.getCreateTime());
	}

	/**
	 * ����row��info
	 * 
	 * @param row
	 * @param info
	 */
	protected BizTypeInfo storeRow(IRow row) {
		if (row == null) {
			return null;
		}
		String id = (String) row.getCell("id").getValue();
		String number = (String) row.getCell("number").getValue();
		String name = (String) row.getCell("name").getValue();
		String desc = (String) row.getCell("description").getValue();
		String creator = (String) row.getCell("creator.id").getValue();
		Timestamp createTime = (Timestamp) row.getCell("createTime").getValue();

		if (FDCHelper.isEmpty(number) && FDCHelper.isEmpty(name)) {
			return null;
		} else if (FDCHelper.isEmpty(number)) {
			MsgBox.showWarning(this, "��" + (row.getRowIndex() + 1) + "���벻��Ϊ��");
			SysUtil.abort();
		} else if (FDCHelper.isEmpty(name)) {
			MsgBox.showWarning(this, "��" + (row.getRowIndex() + 1) + "���Ʋ���Ϊ��");
			SysUtil.abort();
		}

		BizTypeInfo info = new BizTypeInfo();
		if (!FDCHelper.isEmpty(id)) {
			info.setId(BOSUuid.read(id));
		}
		info.setNumber(number);
		info.setName(name);
		info.setDescription(desc);
		info.setCreateTime(createTime);
		if (!FDCHelper.isEmpty(creator)) {
			UserInfo user = new UserInfo();
			user.setId(BOSUuid.read(creator));
			info.setCreator(user);
		}
		return info;
	}

	/**
	 * output actionAddNew_actionPerformed
	 */
	public void actionAddNew_actionPerformed(ActionEvent e) throws Exception {
		BizTypeInfo info = (BizTypeInfo) createNewData();
		IRow row = tblMain.addRow();
		// ���һ��new��־����ɾ����ʱ������������־�������ٵ����ݿ�ɾ��
		row.setUserObject(NEW);
		loadRow(row, info);
	}

	/**
	 * ��������޸�
	 */
	public void actionSave_actionPerformed(ActionEvent e) throws Exception {
		if (changedRow != null) {
			CoreBaseCollection col = new CoreBaseCollection();
			Iterator it = changedRow.entrySet().iterator();
			while (it.hasNext()) {
				IRow row = (IRow) ((Map.Entry) it.next()).getValue();
				BizTypeInfo info = storeRow(row);
				if (info != null) {
					col.add(info);
				}
			}
			getBizInterface().save(col);
		}
		changedRow = new HashMap();
		refresh(null);
	}

	/**
	 * ��û�������ֱ��ɾ��
	 */
	public void actionRemove_actionPerformed(ActionEvent e) throws Exception {
		IRow selectedRow = KDTableUtil.getSelectedRow(tblMain);
		if (FDCHelper.isEmpty(selectedRow.getUserObject())) {
			super.actionRemove_actionPerformed(e);
		} else if (NEW.equals(selectedRow.getUserObject())) {
			// ˳�㻹Ҫɾ��changeRow������У���Ȼ���ᱣ��
			changedRow.remove(selectedRow.getCell("id").getValue());
			tblMain.removeRow(selectedRow.getRowIndex());
		}
	}

	/**
	 * ˫���������鿴
	 */
	protected void tblMain_tableClicked(
			com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e)
			throws Exception {
		// super.tblMain_tableClicked(e);
	}

	/**
	 * �ı�һ�У������б��浽Set�����ڽ��汣��ʱֻ�����Ѹı���У���ֹ����io����
	 */
	protected void tblMain_editStopped(KDTEditEvent e) throws Exception {
		Object oldValue = e.getOldValue();
		Object value = e.getValue();
		oldValue = oldValue == null ? "" : oldValue;
		value = value == null ? "" : value;
		if (!oldValue.equals(value)) {
			int numIndex = tblMain.getColumnIndex("number");
			int nameIndex = tblMain.getColumnIndex("name");
			int eIndex = e.getColIndex();

			if (numIndex == eIndex || nameIndex == eIndex) {
				// У���Ƿ��ظ�
				int rowIndex = e.getRowIndex();
				for (int i = 0; i < tblMain.getRowCount(); i++) {
					if (i != rowIndex) {
						Object obj = tblMain.getCell(i, eIndex).getValue();
						if (value.equals(obj)) {
							if (numIndex == eIndex) {
								MsgBox.showWarning(this, "�����ظ�������������!");
							} else if (nameIndex == eIndex) {
								MsgBox.showWarning(this, "�����ظ�������������!");
							}
							tblMain.getCell(rowIndex, eIndex).setValue(null);
							return;
						}
					}
				}
			}
			String pk = tblMain.getRow(e.getRowIndex()).getCell("id")
					.getValue().toString();
			changedRow.put(pk, tblMain.getRow(e.getRowIndex()));
		}
	}

	/**
	 * output getBizInterface method
	 */
	protected ICoreBase getBizInterface() throws Exception {
		return BizTypeFactory.getRemoteInstance();
	}

	/**
	 * output createNewData method
	 */
	protected IObjectValue createNewData() {
		BizTypeInfo objectValue = new BizTypeInfo();
		objectValue.setId(BOSUuid.create(objectValue.getBOSType()));
		objectValue.setCreator(getUserInfo());
		return objectValue;
	}

	public SelectorItemCollection getSelectors() {
		SelectorItemCollection sic = new SelectorItemCollection();
		sic.add(new SelectorItemInfo("id"));
		sic.add(new SelectorItemInfo("name"));
		sic.add(new SelectorItemInfo("number"));
		sic.add(new SelectorItemInfo("description"));
		sic.add(new SelectorItemInfo("createTime"));
		sic.add(new SelectorItemInfo("creator.id"));
		sic.add(new SelectorItemInfo("creator.name"));
		return sic;
	}

}