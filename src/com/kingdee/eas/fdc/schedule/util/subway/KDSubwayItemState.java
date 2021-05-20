package com.kingdee.eas.fdc.schedule.util.subway;

import java.awt.Color;

/**
 * �ڵ�״̬<br>
 * ÿ������ͼ�ڵ㶼���Լ���״̬�������Դ�ȷ���ڵ����ɫ<br>
 * Ĭ����4����ɫ�����飺<br>
 * ���-��ɫ��������-��ɫ��δ���-��ɫ��δ��ʼ-��ɫ��<br>
 * ����ͼ������ʱ�򣬻����״̬��������˵����壬����ָʾÿ���ڵ��״̬
 * 
 * @author emanon
 * 
 */
public class KDSubwayItemState {

	// Ĭ��������ɫ�����̡����ơ����
	public static Color DONE = Color.GREEN;
	public static Color DOING = Color.ORANGE;
	public static Color UNDO = Color.RED;
	public static Color TODO = Color.WHITE;

	public KDSubwayItemState(String name, Color color) {
		this.statusName = name;
		this.statusColor = color;
	}

	/**
	 * �ڵ�״̬�����ƣ���ʾ�ڵ���ͼ���·�˵������
	 */
	private String statusName;

	/**
	 * �ڵ�״̬����ɫ�����ڵ���ͼ�������Լ��ڵ���ɫ
	 */
	private Color statusColor;

	public String getStatusName() {
		return statusName;
	}

	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}

	public Color getStatusColor() {
		return statusColor;
	}

	public void setStatusColor(Color statusColor) {
		this.statusColor = statusColor;
	}

}
