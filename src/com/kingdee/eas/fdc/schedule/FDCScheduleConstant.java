/*
 * @(#)FDCScheduleConstant.java
 *
 * �����������������޹�˾��Ȩ���� 
 */
package com.kingdee.eas.fdc.schedule;

public class FDCScheduleConstant {
	
	/**�������*/
	public final static String PROJECTIMAGE = "JM2iYBxoReukgy90hZJbW2LF6cA=";
	/**�׶��Գɹ�*/
	public final static String ACHIEVEMANAGER = "Rz+dS7ECSfqM4kEJqGawYWLF6cA=";
	/**��������*/
	public final static String QUALITYCHECKPOINT = "8PR0EhHFTaiDGdtCmQ19SGLF6cA=";
	
	/** ��ʱ��� */
	public final static int ONTIME = 0;
	/** ��ʱ��� */
	public final static int DELAY = 1;
	/** ��ȷ�� */
	public final static int CONFIRMED = 2;
	/** ��ʱδ��� */
	public final static int DELAYANDNOTCOMPLETE = 3;
	/** ���� */
	public final static int UNDEF = 4;
	
	private FDCScheduleConstant(){
		
	}
	/*
	 * ����ΪǨ����Ա������������õ�������֯��ѡ�񹤾ߡ����Ǵ������ϣ�����ɱ��󣬺��������Ʋ⻹���õ�������ȫ��Ǩ�ƹ�����
	 */

	// ������ĿID
	public static final String REPORT_CHART_ID = "id";
	
	// ������Ŀ����
	public static final String REPORT_CHART_PROJECT_ID = "projectID";

	// ������Ŀ����
	public static final String REPORT_CHART_PROJECT_NAME = "projectName";

	// �ƻ������Ŀ��
	public static final String REPORT_CHART_PLANNED_COMP = "plannedComp";

	// ��ʱ�����Ŀ��
	public static final String REPORT_CHART_TIMMED_COMP = "timedComp";

	// �ӳ�15���������Ŀ��
	public static final String REPORT_CHART_FIF_IN_COMP = "fifteenInComp";

	// �ӳ�15���������Ŀ��
	public static final String REPORT_CHART_FIF_OUT_COMP = "fifteenOutComp";

	// δ�����Ŀ��
	public static final String REPORT_CHART_UNCOMP = "unComp";

	// ��ȷ����Ŀ��
	public static final String REPORT_CHART_CONFIRMED_COMP = "confirmedComp";

	// ��ʱ�����Ŀ������
	public static final String REPORT_CHART_TIMED_COMP_RATE = "timedCompRate";

	// �ӳ������Ŀ������
	public static final String REPORT_CHART_DELAYED_COMP_RATE = "delayedCompRate";
	
	// ��ʼ����
	public static final String REPORT_CHART_START_DATE = "startDate";
	
	// ��������
	public static final String REPORT_CHART_END_DATE = "endDate";
	
	// ���ȿ������ͣ�����Ϊ���˽ڵ㡢����ƻ���ר��ƻ�����̱���
	public static final String REPORT_CHART_SCHEDULE_TYPE_CHECK = "check";
	
	public static final String REPORT_CHART_SCHEDULE_TYPE_MAIN_PRJ = "mainPrj";
	
	public static final String REPORT_CHART_SCHEDULE_TYPE_PRO_PRJ = "proPrj";
	
	public static final String REPORT_CHART_SCHEDULE_TYPE_MILESTONE = "milestone";
	
	// ר������
	public static final String REPORT_CHART_SCHEDULE_ALL_EXPERT_PLAN_ID = "allExpertPlan";
	
	public static final String REPORT_CHART_SCHEDULE_ALL_EXPERT_PLAN_NAME = "����";
	
	// ͼ���к�������ʾ��keyֵ
	public static final String CHART_X_AXIS_NAME = "x_axis_name";

	// ѡ�����֯�͹�����Ŀ
	public static final String CHART_SELECT_ORG_PRO = "select_org_pro";
	
	// ��������֯�͹�����Ŀ
	public static final String CHART_SELECT_SORTED_ORG = "sorted_select_org_pro";
	
	// ��������֯�͹�����Ŀ�������
	public static final String CHART_SELECT_SORTED_BY_NUMBWE_ORG = "sorted_select_number_org_pro";
	
	// �����ĸ���֯�͹�����Ŀ
	public static final String CHART_SELECT_SORTED_PARENT_ORG = "sorted_select_parent_org_pro";
	
	// ѡ�нڵ�Ĺ��������ڵ�����
	public static final String CHART_SELECT_PROJECT_NAME = "chart_select_project_name";

	// ����ID
	public static final String PROJECT_TREE_NODE_GROUP_ID = "00000000-0000-0000-0000-00000000000232B85C74";

	// ��Լ���ID
	public static final String PROJECT_TREE_NODE_XUHUI_GROUP_ID = "+D9V2+gnSWOoycJ2ikueqTK4XHQ=";

	// ��֯Ϊ����ʱ����֯ID
	public static final String PROJECT_TREE_NODE_GROUP_CURRENT_ORG_ID = "00000000-0000-0000-0000-000000000000CCE7AED4";
	public static final String PROJECT_TREE_NODE_GROUP_CURRENT_ORG_NAME = "����";
	public static final String PROJECT_TREE_NODE_GROUP_CURRENT_ORG_NUMBER = "G";

	// ��֯Ϊ��Լ���ʱ����֯ID
	public static final String PROJECT_TREE_NODE_XUHUI_GROUP_CURRENT_ORG_ID = "xpNSW8jbR52f4Zw1HZXhCsznrtQ=";
	
	// �������ڵ���ʶ����
	public static final String REPORT_TREE_SEPARATOR = "!";
	
	// ��֯���Ʒָ���
	public static final String ORG_PROJECT_NAME_SEPARATOR = "/";

	// ��������    �����кͲ����С�ɾ���в���
	public static final int ACTION_TYPE_ADD = 1;
	public static final int ACTION_TYPE_INSERT = 2;
	public static final int ACTION_TYPE_DELETE = 3;
	public static final int ACTION_TYPE_UP = 4;
	public static final int ACTION_TYPE_NEXT = 5;

}
