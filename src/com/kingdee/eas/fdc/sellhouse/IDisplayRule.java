package com.kingdee.eas.fdc.sellhouse;

import java.awt.Color;
import java.awt.Font;

import com.kingdee.eas.fdc.basedata.MoneySysTypeEnum;


public interface IDisplayRule {
	//����ϵͳ����
	public MoneySysTypeEnum getSysType();
	
	//����������
	public Font getFont();

	//����������ɫ
	public Color getFrontColor();

	//������ʾ�ֶ� 
	public String getDisplayField();

	//�󶨷�����ʾ 
	public int getAttachDisType();
	
	//���ݷ��� ״̬������ɫ
	public Color getCellBackgroundColor(String key);

	//���䵥Ԫ����
	public int getRoomWidth();

	//���䵥Ԫ��߶�
	public int getRoomHeight();
	
}
