package com.kingdee.eas.fdc.schedule.framework.util;

import java.util.List;

public interface TableBuilder {
	/**
	 * �������
	 * 
	 * @param header1
	 *            ����
	 * @param header2
	 *            ����
	 * @param datas
	 *            ����
	 * @return DocTable
	 */
	DocTable createTable(List header1, List header2, List datas);
}
