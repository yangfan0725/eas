package com.kingdee.eas.fdc.schedule.framework.util;

import java.awt.Color;
import java.awt.Component;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;

/**
 * ÿһ�е�һ����Ԫ�����ı�ͷ
 * <p>
 * ģ���ͷ��ʽ����
 * 
 * @author emanon
 * 
 */
public class ColumnHeaderRenderer extends JLabel implements TableCellRenderer {

	/**
	 * ����һ����ͷ��Ԫ��
	 * 
	 * @param table
	 */
	public ColumnHeaderRenderer(JTable table) {
		JTableHeader header = table.getTableHeader();
		// ��͸��
		setOpaque(true);
		// ���뷽ʽ
		setHorizontalAlignment(CENTER);
		setVerticalAlignment(CENTER);
		// �߿�ģ���ͷ�������ұ߲�����ɸ���Ч��
		setBorder(BorderFactory.createMatteBorder(1, 1, 1, 0, Color.WHITE));
		// ��ɫ
		setForeground(header.getForeground());
		setBackground(header.getBackground());
		// ����
		setFont(header.getFont());
	}

	/**
	 * ���ñ�ͷ����
	 */
	public Component getTableCellRendererComponent(JTable table, Object value,
			boolean isSelected, boolean hasFocus, int row, int column) {
		setText((value == null) ? "" : value.toString());
		return this;
	}

}
