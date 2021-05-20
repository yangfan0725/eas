package com.kingdee.eas.fdc.market.client;

import java.awt.Component;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Set;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.swing.KDComboBox;
import com.kingdee.bos.ctrl.swing.KDDatePicker;
import com.kingdee.bos.ctrl.swing.KDFormattedTextField;
import com.kingdee.bos.ctrl.swing.KDTextField;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.eas.basedata.master.cssp.CustomerFactory;
import com.kingdee.eas.basedata.master.cssp.CustomerInfo;
import com.kingdee.eas.basedata.person.PersonFactory;
import com.kingdee.eas.basedata.person.PersonInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.MsgBox;

/**
 * ¼��У�������
 * 
 * @author zhoubin
 * 
 */
public class VerifyInputUtil {

	private static Logger logger = Logger.getLogger(VerifyInputUtil.class);

	/**
	 * У������Ϊ�գ���У���ı���F7
	 * 
	 * @param ui
	 *            UI��name���ɴ�null����this��
	 * @param comp
	 *            �ؼ���name���磺txtNumber
	 * @param name
	 *            Ҫ��ʾΪ�յ���Ϣ���硰���롱
	 */
	public static void verifyNull(CoreUIObject ui, Component comp, String name) {
		if (comp == null)
			return;

		if (comp instanceof KDTextField) {
			KDTextField ctrl = (KDTextField) comp;
			String text = ctrl.getText();
			if (text == null || "".equals(text.trim())) {
				abort(ui, comp, name + "����Ϊ�գ�");
			}
		} else if (comp instanceof KDBizPromptBox) {
			KDBizPromptBox ctrl = (KDBizPromptBox) comp;
			Object obj = ctrl.getData();
			if (obj != null) {
				if (obj instanceof Object[]) {
					Object array[] = (Object[]) obj;
					if (array.length < 1 || array[0] == null) {
						abort(ui, comp, name + "����Ϊ�գ�");
					}
				}
			} else {
				abort(ui, comp, name + "����Ϊ�գ�");
			}
		} else if (comp instanceof KDFormattedTextField) {
			KDFormattedTextField ctrl = (KDFormattedTextField) comp;
			if (ctrl.getBigDecimalValue() == null) {
				abort(ui, comp, name + "����Ϊ�գ�");
			}
		} else if (comp instanceof KDDatePicker) {
			KDDatePicker ctrl = (KDDatePicker) comp;
			if (ctrl.getValue() == null) {
				abort(ui, comp, name + "����Ϊ�գ�");
			}
		} else if (comp instanceof KDComboBox) {
			KDComboBox ctrl = (KDComboBox) comp;
			if (ctrl.getSelectedItem() == null) {
				abort(ui, comp, name + "����Ϊ�գ�");
			}
		}
	}

	/**
	 * У������һ����Ԫ�������Ƿ�Ϊ��
	 * 
	 * @param ui
	 *            UI��name���ɴ�null����this��
	 * @param tblMain
	 *            ���name
	 * @param rowIndex
	 *            �к�
	 * @param colName
	 *            ������
	 */
	public static void verifyKDTCellNull(CoreUIObject ui, KDTable tblMain,
			int rowIndex, String colName) {
		if (tblMain == null)
			return;

		if (tblMain.getRowCount() < 1) {
			abort(ui, tblMain, "�������У�");
		} else {
			if (tblMain.getCell(rowIndex, colName).getValue() == null) {
				tblMain.getSelectManager().select(rowIndex,
						tblMain.getColumnIndex(colName), rowIndex,
						tblMain.getColumnIndex(colName));
				abort(ui, null, tblMain.getHead().getRow(0).getCell(
						tblMain.getColumnIndex(colName)).getValue()
						+ "����Ϊ�գ�");
			}
		}
	}

	/**
	 * У������һ����Ԫ�������Ƿ�Ϊ��
	 * 
	 * @param ui
	 *            UI��name���ɴ�null����this��
	 * @param tblMain
	 *            ���name
	 * @param colName
	 *            ������
	 */
	public static void verifyKDTColumnNull(CoreUIObject ui, KDTable tblMain,
			String colName) {
		if (tblMain == null)
			return;

		if (tblMain.getRowCount() < 1) {
			abort(ui, tblMain, "�������У�");
		} else {
			for (int i = 0, size = tblMain.getRowCount(); i < size; i++) {
				if (tblMain.getCell(i, colName).getValue() == null) {
					tblMain.getSelectManager().select(i,
							tblMain.getColumnIndex(colName), i,
							tblMain.getColumnIndex(colName));
					abort(ui, null, tblMain.getHead().getRow(0).getCell(
							tblMain.getColumnIndex(colName)).getValue()
							+ "����Ϊ�գ�");
				}
			}
		}
	}


	/**
	 * ��ʾ��Ϣ���жϣ�comp��ý���
	 * 
	 * @param ui
	 *            UI��name���ɴ�null����this��
	 * @param comp
	 *            �ؼ���name���磺txtNumber
	 * @param name
	 *            Ҫ��ʾΪ�յ���Ϣ���硰���롱
	 */
	public static void abort(CoreUIObject ui, Component comp, String errorString) {
		MsgBox.showError(ui, errorString);
		if (comp != null)
			comp.requestFocus();
		SysUtil.abort();
	}
}
