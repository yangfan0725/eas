/**
 * output package name
 */
package com.kingdee.eas.fdc.schedule.report.client;

import org.apache.log4j.Logger;

import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.eas.fdc.schedule.FDCScheduleConstant;

/**
 * ����:���ȱ�����̱��ƻ�����ʽ���
 * @author adward_huang  date:2012-10-17
 * @version EAS6.1
 */
public class ScheduleMilestonePlanRateUI extends AbstractScheduleMilestonePlanRateUI {

	private static final long serialVersionUID = -4511959671280402940L;

	private static final Logger logger = CoreUIObject.getLogger(ScheduleMilestonePlanRateUI.class);

	private static final String CHART_TITLE = "��̱��ƻ������";

	/**
	 * output class constructor
	 */
	public ScheduleMilestonePlanRateUI() throws Exception {
		super();
	}

	/**
	 * �������ṩ����Ľӿڣ������ͼ�εı���
	 * @return
	 * @Author��adward_huang
	 * @CreateTime��2012-9-12
	 */
	protected String getChartTitle() {

		return getProjectName() + CHART_TITLE;
	}

	/**
	 * �������ṩ����Ľӿڣ������ͳ��ָ������
	 * @return
	 * @Author��adward_huang
	 * @CreateTime��2012-9-12
	 */
	protected String getScheduleType() {

		return FDCScheduleConstant.REPORT_CHART_SCHEDULE_TYPE_MILESTONE;
	}

}