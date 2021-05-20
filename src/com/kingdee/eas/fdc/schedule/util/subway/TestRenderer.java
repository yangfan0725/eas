package com.kingdee.eas.fdc.schedule.util.subway;

import java.awt.Color;
import java.awt.Dimension;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JLabel;

public class TestRenderer extends JLabel implements KDSubwayItemRenderer {

	public JComponent getSubwayRendererCompt(KDSubwayItemButton btnItem, Object detail) {
		setPreferredSize(new Dimension(220, 220));
		setOpaque(true);
		Map values = (Map) detail;

		String name = (String) values.get("name");
		String start = (String) values.get("start");
		String end = (String) values.get("end");
		String len = (String) values.get("natureTimes");
		String dept = (String) values.get("adminDept");
		String person = (String) values.get("adminPerson");
		String actualStartDate = (String) values.get("actualStartDate");
		String actualEndDate = (String) values.get("actualEndDate");
		// String admin = (String) values.get("admin");
		String checkDate = (String) values.get("checkDate");
		String complete = (String) values.get("complete");
		int stats = ((Integer) values.get("status")).intValue();
		String status = "";
		if (stats == 0) {
			status = "��ʱ���";
		} else if (stats == 1) {
			status = "�ӳ����";
		} else if (stats == 2) {
			status = "δ�ﵽ�������";
		} else {
			status = "��ʱδ���";
		}
		// String show1 = "<html>&nbsp;&nbsp;�������ƣ�" + name
		// + "<br>&nbsp;&nbsp;�ƻ���ʼ���ڣ�" + start + "<br>&nbsp;&nbsp;��׼���ڣ�"
		// + len + "<br>&nbsp;&nbsp;�ƻ��������ڣ�" + end + "</html>";

		String show = "<html><body><font size=3><p>&nbsp;&nbsp;�������ƣ�" + name + "<br>&nbsp;&nbsp;�ƻ���ʼ���ڣ�" + start + "<br>&nbsp;&nbsp;�ƻ��������: " + end
				+ "<br>&nbsp;&nbsp;���ڣ�" + len + "<br>&nbsp;&nbsp;���β��ţ�" + dept +"<br>&nbsp;&nbsp;�����ˣ�" + person +"<br>&nbsp;&nbsp;ʵ�ʿ�ʼ���ڣ�" + actualStartDate + "<br>&nbsp;&nbsp;ʵ��������ڣ�" + actualEndDate
				+ "<br>&nbsp;&nbsp;�������ڣ�" + checkDate + "<br>&nbsp;&nbsp;��ɽ��ȣ�" + complete + "<br>&nbsp;&nbsp;״̬:" + status
				+ "</p></font></body></html>";
		setText(show);
		setBackground(new Color(221, 255, 221));
		setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200)));
		return this;
	}

}
