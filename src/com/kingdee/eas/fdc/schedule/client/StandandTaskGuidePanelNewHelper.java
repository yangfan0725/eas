/**
 * 
 */
package com.kingdee.eas.fdc.schedule.client;

import java.awt.Color;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent;
import com.kingdee.bos.ctrl.kdf.table.util.KDTableUtil;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.eas.base.attachment.AttachmentInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.basedata.util.FDCAttachmentUtil;
import com.kingdee.eas.fdc.basedata.util.FDCCPDMUtil;
import com.kingdee.eas.fdc.basedata.util.FDCImageAttUtil;
import com.kingdee.eas.fdc.schedule.FDCScheduleTaskInfo;
import com.kingdee.eas.fdc.schedule.GuideTypeEnum;
import com.kingdee.eas.fdc.schedule.StandardTaskGuideCollection;
import com.kingdee.eas.fdc.schedule.StandardTaskGuideEntryInfo;
import com.kingdee.eas.fdc.schedule.StandardTaskGuideFactory;
import com.kingdee.eas.fdc.schedule.framework.ext.KDTask;

/**
 * @(#)							
 * ��Ȩ��		�����������������޹�˾��Ȩ���� ������
 * 
 * @author �ź���
 * @version EAS7.0
 * @createDate 2011-8-20
 * @see
 */

public class StandandTaskGuidePanelNewHelper implements ITaskPanelHelper {

	private FDCScheduleTaskPropertiesNewUI taskProperties;

	private FDCScheduleTaskInfo fdcScheduleTask;

	private FDCImageAttUtil imageAttUtil;

	private final KDTask selectTask;

	public StandandTaskGuidePanelNewHelper(FDCScheduleTaskPropertiesNewUI taskProperties) {
		this.taskProperties = taskProperties;
		selectTask = taskProperties.getSelectedTask();
	}

	/**
	 * @description
	 * @author �ź���
	 * @createDate 2011-8-20
	 * @version EAS7.0
	 * @see
	 */

	public void commit() {

	}

	/**
	 * @description
	 * @author �ź���
	 * @createDate 2011-8-20
	 * @version EAS7.0
	 * @see
	 */

	public void load() throws EASBizException, BOSException {
		fdcScheduleTask = (FDCScheduleTaskInfo) selectTask.getScheduleTaskInfo();
		taskProperties.kDTableTaskGuideA.checkParsed();
		taskProperties.kDTableTaskGuideB.checkParsed();
		taskProperties.kDTableTaskGuideA.getStyleAttributes().setLocked(true);
		taskProperties.kDTableTaskGuideB.getStyleAttributes().setLocked(true);
		loadFieldOfEntry();
		loadFieldOfImage();

		taskProperties.kDTableTaskGuideA.addKDTMouseListener(new com.kingdee.bos.ctrl.kdf.table.event.KDTMouseListener() {
			public void tableClicked(com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e) {
				try {
					kDTableTaskGuide_tableClicked(e, taskProperties.kDTableTaskGuideA);
				} catch (Exception exc) {
					taskProperties.handUIException(exc);
				} finally {
				}
			}
		});

		taskProperties.kDTableTaskGuideB.addKDTMouseListener(new com.kingdee.bos.ctrl.kdf.table.event.KDTMouseListener() {
			public void tableClicked(com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e) {
				try {
					kDTableTaskGuide_tableClicked(e, taskProperties.kDTableTaskGuideB);
				} catch (Exception exc) {
					taskProperties.handUIException(exc);
				} finally {
				}
			}
		});
	}

	/**
	 * @description ��ʵ��ӻ�ȡ���ݸ������
	 * @author �ź���
	 * @createDate 2011-8-9
	 * @return
	 * @version EAS7.0
	 * @see
	 */
	private void loadFieldOfEntry() {
		taskProperties.kDTableTaskGuideA.removeRows();
		taskProperties.kDTableTaskGuideB.removeRows();
		if (fdcScheduleTask.getStandardTask() == null) {
			return;
		}
		EntityViewInfo view = getSelector();
		try {
			StandardTaskGuideCollection standardTaskGuides = StandardTaskGuideFactory.getRemoteInstance().getStandardTaskGuideCollection(view);
			if (standardTaskGuides != null && standardTaskGuides.get(0) != null) {
				for (int i = 0; i < standardTaskGuides.get(0).getEntrys().size(); i++) {
					StandardTaskGuideEntryInfo entryInfo = standardTaskGuides.get(0).getEntrys().get(i);
					IRow row = null;
					if (GuideTypeEnum.systemFlow.equals(entryInfo.getGuideType())) {// ����ָʾ���������
						row = taskProperties.kDTableTaskGuideA.addRow();
					} else {
						row = taskProperties.kDTableTaskGuideB.addRow();
					}
					copyProperies(entryInfo, row);
				}
			}
		} catch (BOSException e) {
			e.printStackTrace();
		}
	}

	private EntityViewInfo getSelector() {
		EntityViewInfo view = new EntityViewInfo();
		view.setFilter(new FilterInfo());
		view.getFilter().getFilterItems().add(new FilterItemInfo("id", fdcScheduleTask.getStandardTask().getId().toString()));
		view.getSelector().add("id");
		view.getSelector().add("name");
		view.getSelector().add("number");
		view.getSelector().add("entrys.*");
		return view;
	}

	/**
	 * @description ��ʵ���ȡ������������
	 * @author �ź���
	 * @createDate 2011-8-9
	 * @return
	 * @version EAS7.0
	 * @see
	 */
	private void copyProperies(StandardTaskGuideEntryInfo entryInfo, IRow row) {
		row.getCell("docName").setValue(entryInfo.getDocName());
		row.getCell("docName").getStyleAttributes().setFontColor(Color.blue);
		row.getCell("id").setValue(entryInfo.getId().toString());
		row.getCell("docID").setValue(entryInfo.getDocID());
		row.getCell("guideType").setValue(entryInfo.getGuideType().getValue());
		row.getCell("isCPFile").setValue((new Boolean(entryInfo.isIsCPFile())));
		if (entryInfo.isIsCPFile()) {
			row.getCell("docPath").setValue("Эͬ�ĵ�");
		} else {
			row.getCell("docPath").setValue("ϵͳ����ƽ̨�ĵ�");
		}

	}

	/**
	 * @description ����ҳ��ͼƬ
	 * @author �ź���
	 * @createDate 2011-8-9
	 * @return
	 * @version EAS7.0
	 * @throws EASBizException
	 * @see
	 */
	private void loadFieldOfImage() throws EASBizException {
		taskProperties.kDPanel1TaskGuide.removeAll();
		try {
			if (//imageAttUtil == null && 
					fdcScheduleTask.getStandardTask() != null) {
				imageAttUtil = new FDCImageAttUtil(taskProperties, fdcScheduleTask.getStandardTask().getId().toString(), null);
				List imageList = imageAttUtil.getImgAtts();
				if (!imageList.isEmpty()) {
					imageAttUtil.setIMGByAttID(((AttachmentInfo) imageList.get(0)).getId().toString(), taskProperties.kDPanel1TaskGuide);
				}
			}
		} catch (BOSException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		taskProperties.kDPanel1TaskGuide.updateUI();
	}

	/**
	 * 
	 * @description ˫��table�ĵڶ�����Ԫ��,�ж��ļ�"�Ƿ�Эͬ",�����ĵ�
	 * @author �ź���
	 * @createDate 2011-8-11
	 * @param e
	 *            ����¼�
	 * @param table
	 * @throws EASBizException
	 * @throws BOSException
	 * @throws IOException
	 * @throws SQLException void
	 * @version EAS7.0
	 * @see
	 */
	private void kDTableTaskGuide_tableClicked(KDTMouseEvent e, KDTable table) throws EASBizException, BOSException, IOException, SQLException {
		if (e.getClickCount() == 2 && e.getColIndex() == 2) {
			IRow row = KDTableUtil.getSelectedRow(table);// ��Ҫ�ж��Ƿ��Ǹ���ID
			Boolean isCPFile = (Boolean) row.getCell("isCPFile").getValue();
			if (isCPFile.booleanValue()) {
				File[] files = FDCCPDMUtil.downLoadFileFromCP(getCaseCellData(row, "docID"), getCaseCellData(row, "docName"));
				FDCAttachmentUtil.openFileInWindows(files[0].getPath());
			} else {
				FDCAttachmentUtil.openAtt(getCaseCellData(row, "docID"));
			}
		}
	}

	/**
	 * @description ��row�л�ȡCellֵ��ת������
	 * @author �ź���
	 * @createDate 2011-8-9
	 * @return String
	 * @version EAS7.0
	 * @see
	 */
	private String getCaseCellData(IRow row, String cellName) {
		return row.getCell(cellName).getValue().toString();
	}

	/**
	 * @description
	 * @author �ź���
	 * @createDate 2011-8-20
	 * @version EAS7.0
	 * @see
	 */

	public void setEditUIStatus() {
		// TODO Auto-generated method stub

	}

	/**
	 * @description
	 * @author �ź���
	 * @createDate 2011-8-20
	 * @version EAS7.0
	 * @see
	 */

	public void setExecutingUIStatus() {
		// TODO Auto-generated method stub

	}

	/**
	 * @description
	 * @author �ź���
	 * @createDate 2011-8-20
	 * @version EAS7.0
	 * @see
	 */

	public void setViewUIStatus() {
		// TODO Auto-generated method stub

	}

}
