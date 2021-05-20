package com.kingdee.eas.fdc.market.client;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.swing.JToggleButton;
import javax.swing.text.JTextComponent;

import com.kingdee.eas.fdc.market.QuestionPaperAnswerEntryCollection;
import com.kingdee.eas.fdc.market.QuestionPaperAnswerEntryInfo;

public class XCellsAndSubjectAndAnswer {

	private Map/* <String,ButtonAndText> */detailMap = new HashMap/* <String,ButtonAndText> */();

	public void put(String optionId, JToggleButton buttonComp, JTextComponent textComp) {
		detailMap.put(optionId, new XButtonAndText(optionId, buttonComp, textComp));
	}
	
	public void put(String optionId, XButtonAndText bt) {
		detailMap.put(optionId, bt);
	}

	public XButtonAndText remove(String optionId) {
		return (XButtonAndText) detailMap.remove(optionId);
	}

	/**
	 * �������õ�������<br>
	 * ѭ������ setPartableAnswer(QuestionPaperAnswerEntryInfo answerEntryInfo) ����
	 */
	public void setAnswer(QuestionPaperAnswerEntryCollection answerEntryCollection) {
		for (int i = 0; i < answerEntryCollection.size(); i++) {
			setAnswer(answerEntryCollection.get(i));
		}
	}

	/**
	 * �������õ�����<br>
	 * 1������ QuestionPaperAnswerEntryInfo.option �ҵ��ؼ���� ButtonAndText��
	 * ���ButtonAndText.buttonComp != NULL,��ô�����øÿؼ�Ϊ����ѡ����<br>
	 * 2����1����� ButtonAndText.textComp != NULL &&
	 * answerEntryInfo.answerEntryInfo.getInputStr() != NULL , ��ô�ͽ�
	 * answerEntryInfo.answerEntryInfo.getInputStr() ���õ�
	 * ButtonAndText.textComp�ϡ�<br>
	 */
	public void setAnswer(QuestionPaperAnswerEntryInfo answerEntryInfo) {
		String optionId = answerEntryInfo.getOption();
		XButtonAndText bt = (XButtonAndText) detailMap.get(optionId);
		if (bt != null) {
			if (bt.buttonComp != null) {
				// ��ѡ��ؼ�����Ϊ��ѡ
				bt.buttonComp.setSelected(true);
			}
			if (bt.textComp != null && answerEntryInfo.getInputStr() != null) {
				// ��������ַ������õ�����ؼ���
				bt.textComp.setText(answerEntryInfo.getInputStr());
			}
		}
	}

	public QuestionPaperAnswerEntryCollection getAnswer() {
		QuestionPaperAnswerEntryCollection answerEntryCollection = new QuestionPaperAnswerEntryCollection();
		Iterator btnAndTxtIterator = detailMap.values().iterator();
		while (btnAndTxtIterator.hasNext()) {
			XButtonAndText btnAndTxt = (XButtonAndText) btnAndTxtIterator.next();
			if (haveValueButtonAndText(btnAndTxt)) {
				QuestionPaperAnswerEntryInfo answerEntryInfo = new QuestionPaperAnswerEntryInfo();
				answerEntryInfo.setOption(btnAndTxt.optionId);
				if (btnAndTxt.textComp != null) {
					answerEntryInfo.setInputStr(btnAndTxt.textComp.getText());
				}
				answerEntryCollection.add(answerEntryInfo);
			}

		}
		return answerEntryCollection;
	}

	public static boolean haveValueButtonAndText(XButtonAndText bt) {
		boolean have = false;
		if (bt != null) {
			if (bt.buttonComp != null && bt.buttonComp.isSelected()) {
				have = true;
			} else {
				if (bt.textComp != null && bt.textComp.getText() != null && bt.textComp.getText().length() > 0) {
					have = true;
				}
			}
		}
		return have;
	}
	

}
