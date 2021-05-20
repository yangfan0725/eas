package com.kingdee.eas.fdc.invite.client.offline.ui;

import java.awt.Color;
import java.util.Vector;

import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import com.kingdee.bos.ctrl.kdf.table.ICell;
import com.kingdee.bos.ctrl.kdf.table.IColumn;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTDefaultCellEditor;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.event.KDTEditAdapter;
import com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent;
import com.kingdee.bos.ctrl.kdf.util.style.StyleAttributes;
import com.kingdee.bos.ctrl.kdf.util.style.Styles.HorizontalAlignment;
import com.kingdee.bos.ctrl.swing.KDFormattedTextField;
import com.kingdee.eas.fdc.invite.client.offline.Constants;
import com.kingdee.eas.fdc.invite.client.offline.util.ObjectUtil;
import com.kingdee.eas.fdc.invite.client.offline.util.TableSettingHelper;

public class TableSetting extends AbstractTableSetting {

	// �Ƿ����ù�������
	private boolean hasSetFreeColumn = false;

	// �Ƿ��������ö��뷽ʽ
	private static final boolean alignEnable = false;

	public TableSetting(JFrame frame, KDTable table) {
		super(frame, table);
		initTable();
	}

	private void initTable() {
		final KDTable table = super.table;
		IRow row = table.addHeadRow();
		IColumn column = null;
		column = table.addColumn();
		column.setKey("����");
		column.getStyleAttributes().setLocked(true);
		row.getCell("����").setValue("����");

		column = table.addColumn();
		column.setKey("�п��");
		row.getCell("�п��").setValue("�п��");

		KDFormattedTextField tableWidth = new KDFormattedTextField(KDFormattedTextField.INTEGER);
		// tableWidth.setMinimumValue(new Comparable())
		tableWidth.setMinimumValue(new Integer(1));
		tableWidth.setMaximumValue(new Integer(1000));

		column.setEditor(new KDTDefaultCellEditor(tableWidth));

		if (alignEnable) {
			column = table.addColumn();
			column.setKey("���뷽ʽ");
			row.getCell("���뷽ʽ").setValue("���뷽ʽ");
			Vector values = new Vector();
			values.addElement("��");
			values.addElement("��");
			values.addElement("��");
			JComboBox c = new JComboBox(values);
			column.setEditor(new KDTDefaultCellEditor(c));
		}

		table.addColumn().setKey("����");
		row.getCell("����").setValue("����");
		table.addColumn().setKey("�ж���");
		row.getCell("�ж���").setValue("�ж���");

		table.getColumn(0).setWidth(120);
		int cloumnCount = alignEnable ? 5 : 4;
		for (int i = 1; i < cloumnCount; i++) {
			table.getColumn(i).setWidth(60);
		}

		table.checkParsed();
		KDTable settingTable = super.settingTable;
		int freezeCol = settingTable.getViewManager().getFreezeCol();
		for (int i = 0; i < settingTable.getColumnCount(); i++) {
			row = table.addRow();
			// ����
			row.getCell(0).setValue(settingTable.getHeadRow(settingTable.getHeadRowCount() - 1).getCell(i).getValue());
			row.setUserObject(new Integer(i));
			if (alignEnable)
				if ("��Ŀ����".equals(row.getCell(0).getValue()) || "��λ".equals(row.getCell(0).getValue())) {
					row.getCell(2).getStyleAttributes().setBackground(Color.lightGray);
					row.getCell(2).getStyleAttributes().setLocked(true);
				}

			int width = settingTable.getColumn(i).getKDTColumn().getWidth();
			if (width <= 0)
				width = settingTable.getColumn(i).getWidth();
			row.getCell("�п��").setValue(new Integer(width));
			if (alignEnable)
				row.getCell("���뷽ʽ").setValue(getAlignName(settingTable.getColumn(i).getStyleAttributes().getHorizontalAlign()));
			row.getCell("����").setValue(new Boolean(settingTable.getColumn(i).getStyleAttributes().isHided()));
			row.getCell("�ж���").setValue(new Boolean(freezeCol > 0 && i < freezeCol));
		}

		height.setValue(new Integer(settingTable.getRow(0).getHeight()));

		if (alignEnable && (Constants.Total_Table.equals(settingTable.getName()) || !Constants.Desc_Table.equals(settingTable.getName()))) {
			table.getColumn(4).getStyleAttributes().setLocked(true);
		} else {
			// ��׼�������һ��
			final int lastColumn = alignEnable ? 4 : 3;
			table.getCell(table.getRowCount() - 1, lastColumn).getStyleAttributes().setLocked(true);
			table.addKDTEditListener(new KDTEditAdapter() {
				public void editValueChanged(KDTEditEvent e) {
					if (e.getColIndex() != lastColumn)
						return;
					hasSetFreeColumn = true;
					final int rowIndex = e.getRowIndex();
					if (!((Boolean) table.getCell(rowIndex, lastColumn).getValue()).booleanValue()) {
						JOptionPane.showMessageDialog(null, "����֮ǰ��������Ҳ�ᱻ����", "��������ʾ", JOptionPane.OK_OPTION);
					}
					IRow row = table.getRow(rowIndex);
					ICell cell = row.getCell(lastColumn);
					if (((Boolean) cell.getValue()).booleanValue()) {
						for (int i = rowIndex + 1; i < table.getRowCount(); i++) {
							table.getRow(i).getCell(lastColumn).setValue(Boolean.FALSE);
						}
					} else {
						for (int i = 0; i < rowIndex; i++) {
							table.getRow(i).getCell(lastColumn).setValue(Boolean.TRUE);
						}
					}

				}
			});
		}

	}

	/**
	 * @param str
	 * @return ���ض��뷽ʽ
	 */
	private HorizontalAlignment getAlign(String str) {
		if (str.equals("��"))
			return HorizontalAlignment.LEFT;
		if (str.equals("��"))
			return HorizontalAlignment.CENTER;
		if (str.equals("��"))
			return HorizontalAlignment.RIGHT;
		return HorizontalAlignment.LEFT;
	}

	private String getAlignName(HorizontalAlignment align) {
		if (HorizontalAlignment.LEFT.equals(align))
			return "��";
		if (HorizontalAlignment.CENTER.equals(align))
			return "��";
		if (HorizontalAlignment.RIGHT.equals(align))
			return "��";
		return "��";
	}

	public void setDefault() {
		TableSettingHelper.setDefaultFormat(settingTable);
		//
		height.setValue(new Integer(TableSettingHelper.defaultHeight));
		for (int i = 0; i < settingTable.getColumnCount(); i++) {
			IRow row = table.getRow(i);
			int width = settingTable.getColumn(i).getKDTColumn().getWidth();
			if (width <= 0)
				width = settingTable.getColumn(i).getWidth();
			row.getCell("�п��").setValue(new Integer(width));
			if (alignEnable)
				row.getCell("���뷽ʽ").setValue(settingTable.getColumn(i).getStyleAttributes().getHorizontalAlign());
			row.getCell("����").setValue(Boolean.FALSE);
			row.getCell("�ж���").setValue(Boolean.FALSE);
		}
		if (hasSetFreeColumn) {
			TableSettingHelper.setFreeze(settingTable);
			hasSetFreeColumn = false;
		}
	}

	public void OK() {
		KDTable table = super.table;
		int freezeCol = -1;
		final int rowCount = table.getRowCount();
		for (int i = 0; i < rowCount; i++) {
			IRow row = table.getRow(i);
			int col = ((Integer) row.getUserObject()).intValue();
			IColumn column = super.settingTable.getColumn(col);
			column.setWidth(ObjectUtil.convertToInt(row.getCell("�п��").getValue(), 100));
			StyleAttributes sa = super.settingTable.getColumn(col).getStyleAttributes();
			if (alignEnable)
				sa.setHorizontalAlign(getAlign(row.getCell("���뷽ʽ").getValue().toString()));
			sa.setHided(((Boolean) row.getCell("����").getValue()).booleanValue());
			if (((Boolean) row.getCell("�ж���").getValue()).booleanValue()) {
				freezeCol = col + 1;
			}
			column.setStyleAttributes(sa);

		}
		if (heightChanged)
			for (int i = 0; i < super.settingTable.getRowCount(); i++) {
				super.settingTable.getRow(i).setHeight(ObjectUtil.convertToInt(height.getText(), 20));
			}
		if (!Constants.Total_Table.equals(settingTable.getName()) && !Constants.Desc_Table.equals(settingTable.getName())) {
			super.settingTable.getViewManager().freeze(-1, freezeCol);
			if (hasSetFreeColumn) {
				TableSettingHelper.setFreeze(settingTable);
				hasSetFreeColumn = false;
			}
		}
		this.dispose();
	}

	public void cancel() {
		this.dispose();
	}

}
