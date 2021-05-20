package com.kingdee.eas.fdc.schedule.util.subway;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JDialog;

public class Test extends JDialog {

	protected void setUp() {
		setSize(800, 200);
		setLayout(null);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);

		// ���Ƽ���
		String[] names = new String[] {
				"�ڵ�1111111111111111111111111111111111111111111111111111111",
				"�ڵ�22222222222222222222222222222222222222", "�ڵ�3", "�ڵ�4",
				"�ڵ�5", "�ڵ�6", "�ڵ�7", "�ڵ�8", "�ڵ�9" };
		// ״̬���ϣ��˴�ʹ��Ԥ���4����ɫ��Ҳ�����Լ�����
		KDSubwayItemState finish = new KDSubwayItemState("��ʱ���",
				KDSubwayItemState.DONE);
		KDSubwayItemState doing = new KDSubwayItemState("��ʱ���",
				KDSubwayItemState.DOING);
		KDSubwayItemState undo = new KDSubwayItemState("��ʱ��δ���",
				KDSubwayItemState.UNDO);
		KDSubwayItemState todo = new KDSubwayItemState("δ�ﵽ�������",
				KDSubwayItemState.TODO);
		KDSubwayItemState[] allStates = new KDSubwayItemState[] { finish,
				doing, undo, todo };
		// �ڵ�״̬����Ӧ����״̬���ϣ���֪״̬�ֱ�Ϊ��finish��finish��doing��finish��doing....
		int[] itemStates = new int[] { 0, 0, 1, 0, 1, 1, 2, 3, 3 };
		// �ڵ���ϸ��Ϣ���˴�Ϊһ��Map���飬���б��������ơ���ʼ���ڵ���Ϣ����չʾ��ʱ��ʹ��TestRenderer������html���
		// �ڽ�������ʽʹ�õ�ʱ�򣬴˴���������̱��ڵ��һЩ�ؼ���Ϣ
		Map[] values = new HashMap[9];
		for (int i = 0; i < 9; i++) {
			Map value = new HashMap();
			value.put("name", "aaa" + i);
			value.put("start", new SimpleDateFormat("yyyy-MM-dd")
					.format(new Date()));
			value.put("len", "30");
			value.put("admin", "�������");
			values[i] = value;
		}
		// ��������ͼ
//		KDSubwayMap map = new KDSubwayMap(names, allStates, itemStates, values,
//				100, true);
//		map.setRenderer(new TestRenderer());
//
//		map.setBounds(0, 0, 800, 200);
//		add(map);
	}

	public static void main(String[] args) {
		Test test = new Test();
		test.setUp();
		test.setVisible(true);
	}

}
