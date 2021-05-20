/*
 * @(#)EffectDegreePanel.java
 *
 * �����������������޹�˾��Ȩ���� 
 */
package com.kingdee.eas.fdc.schedule.client;

import java.awt.FlowLayout;
import java.awt.Rectangle;

import javax.swing.JComboBox;

import com.kingdee.bos.ctrl.swing.KDLabelContainer;

/**
 * Ӱ��̶�Panel
 * @author RD_owen_wen  date:2013-12-3
 * @version EAS6.1
 */
public class EffectDegreePanel extends javax.swing.JPanel {
	JComboBox cbEffect;

	public EffectDegreePanel() {
		cbEffect = new JComboBox(new String[] { "��������", "�ճ�����", "һ������", "�ؼ�����", "��̱�����" });
		setLayout(new FlowLayout(FlowLayout.LEFT));
		KDLabelContainer contEffect = new KDLabelContainer("Ӱ��̶ȣ�");
		contEffect.setBounds(new Rectangle(270, 19));//��׼�ߴ�
		contEffect.setBoundEditor(cbEffect);
		this.add(contEffect);
	}
	
	/**
	 * ��ȡӰ��̶�����comboBox
	 * @return Ӱ��̶�����comboBox
	 * @Author��RD_owen_wen
	 * @CreateTime��2013-12-3
	 */
	public JComboBox getCBEffect() {
		return cbEffect;
	}
}
