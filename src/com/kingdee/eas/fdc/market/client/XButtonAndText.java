package com.kingdee.eas.fdc.market.client;

import javax.swing.JToggleButton;
import javax.swing.text.JTextComponent;
/**
 * ѡ��ؼ�������ؼ������
 */
public class XButtonAndText {

	public String optionId;

	public JToggleButton buttonComp;

	public JTextComponent textComp;

	public XButtonAndText(String optionId, JToggleButton buttonComp, JTextComponent textComp) {
		this.buttonComp = buttonComp;
		this.textComp = textComp;
		this.optionId = optionId;
	}
}
