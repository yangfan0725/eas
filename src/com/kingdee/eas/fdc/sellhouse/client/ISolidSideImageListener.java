package com.kingdee.eas.fdc.sellhouse.client;

import java.awt.Color;

import javax.swing.JButton;

/**
 *  ��������ͼ������Ԫ�񱻵��� <br>
 *  ����ͼ������ʱ���ý�����Ӧ����ͼ��Ԫ�񵥻��¼�����ʵ�ִ˽ӿ�
 * @author jian_wen
 *
 */
public interface ISolidSideImageListener {
	/**
	 *  ��Ӧ����
	 * @param obj
	 */
	void actionResponse(Object obj) throws Exception;
	/**
	 *  ˫���¼�
	 * @param obj
	 * 
	 */
	void actionBothClicked(Object obj) throws Exception;
	/**
	 *  ���ñ�������ť����ɫ
	 * @param btn
	 * @param c
	 * @throws Exception
	 */
	void setColor(JButton btn,Color c) throws Exception;
}
