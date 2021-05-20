package com.kingdee.eas.fdc.schedule.framework.util;

import java.awt.BorderLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JFrame;

import org.apache.log4j.Logger;

public class DocExample extends JFrame {
	Logger logger = Logger.getLogger(DocExample.class.getName());
	DocTable table;

	public DocExample() {
		DocTableBuilder builer = new DocTableBuilder();
		// �ϱ�ͷ
		List header1 = new ArrayList();
		Map m = new HashMap();
		m.put("id", "COL_ID_0");
		m.put("name", "��Ŀ������");
		header1.add(m);
		m = new HashMap();
		m.put("id", "COL_ID_1");
		m.put("name", "�з�");
		header1.add(m);
		m = new HashMap();
		m.put("id", "COL_ID_2");
		m.put("name", "Ӫ��");
		header1.add(m);
		m = new HashMap();
		m.put("id", "COL_ID_3");
		m.put("name", "���");
		header1.add(m);
		m = new HashMap();
		m.put("id", "COL_ID_4");
		m.put("name", "����");
		header1.add(m);
		// ���ͷ
		List header2 = new ArrayList();
		m = new HashMap();
		m.put("id", "ROW_ID_0");
		m.put("name", "���ط����׶�");
		header2.add(m);
		m = new HashMap();
		m.put("id", "ROW_ID_1");
		m.put("name", "��Ŀ�����׶�");
		header2.add(m);
		m = new HashMap();
		m.put("id", "ROW_ID_2");
		m.put("name", "������ƽ׶�");
		header2.add(m);
		m = new HashMap();
		m.put("id", "ROW_ID_3");
		m.put("name", "ʵʩ�׶�");
		header2.add(m);
		// �������
		List data = new ArrayList();
		for (int i = 0; i < 5; i++) {
			m = new HashMap();
			m.put("id", "id_" + i);
			m.put("name", "����" + i + ".doc");
			m.put("rowId", "ROW_ID_" + 0);
			m.put("colId", "COL_ID_" + i);
			m.put(Config.STATUS_KEY, Config.STATUS_NOTSUBMIT);
			data.add(m);
		}
		for (int i = 0; i < 3; i++) {
			m = new HashMap();
			m.put("id", "id_" + i);
			m.put("name", "����" + i + ".doc");
			m.put("rowId", "ROW_ID_" + 1);
			m.put("colId", "COL_ID_" + i);
			data.add(m);
			m.put("status", "00");
		}
		for (int i = 0; i < 2; i++) {
			m = new HashMap();
			m.put("id", "id_" + i);
			m.put("name", "����" + i + ".doc");
			m.put("rowId", "ROW_ID_" + 2);
			m.put("colId", "COL_ID_" + i);
			data.add(m);
		}
		for (int i = 0; i < 5; i++) {
			m = new HashMap();
			m.put("id", "id_" + i);
			m.put("name", "����" + i + ".doc");
			m.put("rowId", "ROW_ID_" + 3);
			m.put("colId", "COL_ID_" + i);
			data.add(m);
			m.put("status", "02");
		}
		for (int i = 0; i < 5; i++) {
			m = new HashMap();
			m.put("id", "id_" + i);
			m.put("name", "��������������������������������������������������������" + i + ".doc");
			m.put("rowId", "ROW_ID_" + 3);
			m.put("colId", "COL_ID_" + i);
			m.put("status", "03");
			data.add(m);
		}
		// �������
		table = builer.createTable(header1, header2, data);
		// ���������
		table.addMouseListener(new MouseListener() {

			public void mouseClicked(MouseEvent e) {
				actionClicked(e);
			}

			public void mouseEntered(MouseEvent e) {
			}

			public void mouseExited(MouseEvent e) {
			}

			public void mousePressed(MouseEvent e) {
			}

			public void mouseReleased(MouseEvent e) {
			}
		});
		// �и�
		// table.setRowHeight(30);
		// ��ӿؼ�������
		getContentPane().add(table.getContainer(), BorderLayout.CENTER);
	}

	/**
	 * ������¼����ڴ˿��ж�˫������ȡ�õ���ĵ�Ԫ��ֵ
	 * 
	 * @param e
	 */
	protected void actionClicked(MouseEvent e) {
		if (e.getClickCount() == 1) {
			logger.info("time:" + e.getWhen());
			logger.info("Ҽ");
		} else {
			logger.info("time:" + e.getWhen());
			logger.info("��");
			logger.info(table.getSelectedItem().getText());
		}
	}

	public static void main(String[] args) {
		DocExample frame = new DocExample();

		frame.setSize(600, 400);
		frame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
		frame.setVisible(true);
	}
}
