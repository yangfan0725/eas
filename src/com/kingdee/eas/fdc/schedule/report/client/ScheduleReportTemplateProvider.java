/*
 * @(#)ScheduleReportTemplateProvider.java
 *
 * �����������������޹�˾��Ȩ���� 
 */
package com.kingdee.eas.fdc.schedule.report.client;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.kingdee.bos.ctrl.kdf.data.datasource.BOSQueryDataSource;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.client.FDCBillDataProvider;
import com.kingdee.eas.fdc.schedule.framework.client.ScheduleChartProvider;
import com.kingdee.jdbc.rowset.IRowSet;
import com.kingdee.jdbc.rowset.impl.ColInfo;
import com.kingdee.jdbc.rowset.impl.DynamicRowSet;
import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageEncoder;

/**
 * ����:���ȱ����״�ģ��
 * @author adward_huang  date:2012-9-29
 * @version EAS6.1
 */
public class ScheduleReportTemplateProvider extends FDCBillDataProvider {

	private static final Logger logger = CoreUIObject.getLogger(ScheduleChartProvider.class);

	private BufferedImage img = null;
	private List<List<String>> list;
	private static final String PROJECT_NAME = "projectName";
	private static final String PLANNED_COMP = "plannedComp";
	private static final String TIMED_COMP = "timedComp";
	private static final String FIF_IN_COMP = "fifInComp";
	private static final String FIF_OUT_COMP = "fifOutComp";
	private static final String UN_COMP = "unComp";
	private static final String CONFIRM_COMP = "confirmComp";
	private static final String TIMED_COMP_RATE = "timedCompRate";
	private static final String DELAYED_COMP_RATE = "delayedCompRate";

	private static final int TABLE_COLUMN = 9;

	// ͼ�β�ѯ����ID
	private static final String SCHEDULE_REPORT_IMAGE_TEMPLATE = "ScheduleReportDataImageQuery";

	// ����ѯ����ID
	//	private static final String SCHEDULE_REPORT_TABLE_TEMPLATE = "ScheduleReportDataTableQuery";

	public ScheduleReportTemplateProvider(List<List<String>> list, BufferedImage bufferedImage) {
		super(null, null);
		this.list = list;
		this.img = bufferedImage;
	}

	/**
	* �½�һ�����ݼ������ô����ͼƬ������byte[]ͼƬ�ֶβ����ظ��״�
	*/
	public IRowSet getOtherSubRowSet(BOSQueryDataSource ds) {
		try {
			String id = ds.getID();
			DynamicRowSet row /*= insertImageRow()*/;
			// �����ͼ�β�ѯ���򷵻�ͼ��
			if (id.equals(SCHEDULE_REPORT_IMAGE_TEMPLATE)) {
				row = insertImageRow();
			} else {//���ر������
				row = insertTableRows();
			}
			return row;
		} catch (SQLException e) {
			logger.error(e);
		} catch (IOException e) {
			logger.error(e);
		}
		return null;
	}

	/**
	 * ����������ͼ���״�ģ��
	 * @return
	 * @throws SQLException
	 * @throws IOException
	 * @Author��adward_huang
	 * @CreateTime��2012-10-17
	 */
	private DynamicRowSet insertImageRow() throws SQLException, IOException {
		DynamicRowSet rs = new DynamicRowSet(1);
		ColInfo ci = new ColInfo();
		ci = new ColInfo();
		ci.colType = Types.BLOB;
		ci.columnName = "img";
		ci.nullable = 1;
		rs.setColInfo(1, ci);

		rs.beforeFirst();

		rs.moveToInsertRow();
		if (!FDCHelper.isEmpty(img)) {
			ByteArrayOutputStream os = new ByteArrayOutputStream();
			JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(os);
			encoder.encode(img);
			rs.updateObject("img", os.toByteArray());
		}
		rs.insertRow();
		rs.beforeFirst();
		return rs;
	}

	/**
	 * ���������ر���״�ģ��
	 * @return
	 * @throws SQLException
	 * @throws IOException
	 * @Author��adward_huang
	 * @CreateTime��2012-10-17
	 */
	private DynamicRowSet insertTableRows() throws SQLException, IOException {
		DynamicRowSet rs = new DynamicRowSet(TABLE_COLUMN);//TABLE_COLUMN
		List<String> tableNameList = getTableNameList();
		if (list != null && !list.isEmpty()) {
			List<String> row = null;
			for (int i = 0; i < list.size(); i++) {
				ColInfo ci = null;
				row = list.get(i);
				for (int j = 0; j < row.size(); j++) {
					ci = new ColInfo();
					ci.colType = Types.VARCHAR;
					ci.columnName = tableNameList.get(j);
					ci.nullable = 1;
					rs.setColInfo(j + 1, ci);
					rs.moveToInsertRow();
					if (!FDCHelper.isEmpty(row.get(j))) {
						rs.updateObject(tableNameList.get(j), row.get(j));
					}
				}
				rs.insertRow();
			}
		}

		rs.beforeFirst();
		return rs;
	}

	/**
	 * ��������ȡ������Ƽ���
	 * @return
	 * @Author��adward_huang
	 * @CreateTime��2012-10-17
	 */
	private List<String> getTableNameList() {
		List<String> list = new ArrayList<String>();
		list.add(PROJECT_NAME);
		list.add(PLANNED_COMP);
		list.add(TIMED_COMP);
		list.add(FIF_IN_COMP);
		list.add(FIF_OUT_COMP);
		list.add(UN_COMP);
		list.add(CONFIRM_COMP);
		list.add(TIMED_COMP_RATE);
		list.add(DELAYED_COMP_RATE);
		return list;
	}

}
