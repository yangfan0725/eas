package com.kingdee.eas.fdc.finance;

/**
 * �������ɱ�ȡ�������� 
 */
public class FDCCostRptUtils {
	public static Object[][][] getCostCloseItem(boolean isCost,String key){
		Object[][] item =null;
		if (isCost) {
			item = new Object[][] { { "��ͬ", "��ͬȫ����ȫ���", "con" },
					{ "��ͬ���", "���ȫ����ȫ���", "change" },
					{ "����", "����ȫ����ȫ���", "settle" },
					{ "����ʵ��Ͷ��", "����ʵ��Ͷ��ȫ����ȫ���", "workload" },
					{ "���ı���ͬ", "���ı���ͬȫ����ȫ����", "conWithout" } };
		} else {
			item = new Object[][] { { "���", "��ͬȫ����ȫ���", "conSplit" },
					{ "���ı���ͬ������", "���ı���ͬȫ����ȫ���", "noTextSplit" },
					{ "�������ͬ", "��������", "invalidCon" },
					{ "���������ı���ͬ", "��������", "invalidWithoutCon" } };
		}
		return null;
	}
}
