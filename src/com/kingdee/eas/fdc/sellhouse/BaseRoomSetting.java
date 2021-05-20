package com.kingdee.eas.fdc.sellhouse;

import java.awt.Color;
import java.awt.Font;
import java.io.Serializable;

public class BaseRoomSetting implements Serializable {
	
	private int attachDisType = 0;
	private Font font = new Font("����", 5, 10);	
	private Color frontColor = Color.BLACK;		//��ʼ������ɫ  - ��
	private int roomHeight = 45;
	private int roomWidth = 40;
	private String displayField = "number";
	
	
	private Color initColor = Color.LIGHT_GRAY;  //��ʼ���� δ����-�� 

	private Color onShowColor = Color.WHITE;	 //��ʼ���� ���� - ��

	private Color keepDownColor = Color.GREEN;   //��ʼ���� ���� - ��
	
	private Color controlColor = Color.BLUE;   //��ʼ���� ���� - ��
	
	private Color prePurColor = Color.YELLOW;    //��ʼ����  Ԥ�� - ��

	private Color purColor = Color.ORANGE;		 //��ʼ����  �Ϲ� - ��ɫ

	private Color signColor = Color.RED;		//��ʼ����  ǩԼ - ��ɫ
	
	private Color noSellhouseColor = Color.BLACK;	//û����¥���� - ���
	
	//�����Ϲ��տ���ɫĬ��gy 2010-12-10  ��ӥ�ƻ�������������
	private Color sinReColor = Color.PINK;      //��ʼ����  �Ϲ��տ� ��  
	
	public Color getSinReColor() {
		return sinReColor;
	}

	public void setSinReColor(Color sinReColor) {
		this.sinReColor = sinReColor;
	}

	private Color sincerPurColor = new Color(255,153,204);	

	private boolean isAuditDate = false; //����ҵ��������ʱ��Ϊ׼	
	
	private int lockTime =0; //��������ʱ��
	
	private Color chooseColor = new Color(128,0,128);	//ѡ����ɫ -  r=128,g=0,b=128
	
	public int getAttachDisType() {
		return attachDisType;
	}

	public void setAttachDisType(int attachDisType) {
		this.attachDisType = attachDisType;
	}

	public Font getFont() {
		return font;
	}

	public void setFont(Font font) {
		this.font = font;
	}

	public Color getFrontColor() {
		return frontColor;
	}

	public void setFrontColor(Color frontColor) {
		this.frontColor = frontColor;
	}

	public int getRoomHeight() {
		return roomHeight;
	}

	public void setRoomHeight(int roomHeight) {
		this.roomHeight = roomHeight;
	}

	public int getRoomWidth() {
		return roomWidth;
	}

	public void setRoomWidth(int roomWidth) {
		this.roomWidth = roomWidth;
	}

	public String getDisplayField() {
		return displayField;
	}

	public void setDisplayField(String displayField) {
		this.displayField = displayField;
	}



	public Color getInitColor() {
		return initColor;
	}

	public void setInitColor(Color initColor) {
		this.initColor = initColor;
	}

	public Color getOnShowColor() {
		return onShowColor;
	}

	public void setOnShowColor(Color onShowColor) {
		this.onShowColor = onShowColor;
	}

	public Color getKeepDownColor() {
		return keepDownColor;
	}

	public void setKeepDownColor(Color keepDownColor) {
		this.keepDownColor = keepDownColor;
	}
	public Color getControlColor() {
		return controlColor;
	}

	public void setControlColor(Color controlColor) {
		this.controlColor = controlColor;
	}
	public Color getPrePurColor() {
		return prePurColor;
	}

	public void setPrePurColor(Color prePurColor) {
		this.prePurColor = prePurColor;
	}

	public Color getPurColor() {
		return purColor;
	}

	public void setPurColor(Color purColor) {
		this.purColor = purColor;
	}

	public Color getSignColor() {
		return signColor;
	}

	public void setSignColor(Color signColor) {
		this.signColor = signColor;
	}

	public Color getNoSellhouseColor() {
		return noSellhouseColor;
	}

	public void setNoSellhouseColor(Color noSellhouseColor) {
		this.noSellhouseColor = noSellhouseColor;
	}
	
	public Color getSincerPurColor() {
		return sincerPurColor;
	}

	public void setSincerPurColor(Color sincerPurColor) {
		this.sincerPurColor = sincerPurColor;
	}

	public boolean isAuditDate() {
		return isAuditDate;
	}

	public void setAuditDate(boolean isAuditDate) {
		this.isAuditDate = isAuditDate;
	}
	
	public int getLockTime() {
		return lockTime;
	}

	public void setLockTime(int lockTime) {
		this.lockTime = lockTime;
	}
	
	public Color getChooseColor() {
		return chooseColor;
	}

	public void setChooseColor(Color chooseColor) {
		this.chooseColor = chooseColor;
	}
}
