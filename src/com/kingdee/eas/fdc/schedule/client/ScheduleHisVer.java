/**
 * 
 */
package com.kingdee.eas.fdc.schedule.client;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;
import java.util.Date;

import javax.swing.ToolTipManager;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.swing.KDMenu;
import com.kingdee.bos.ctrl.swing.KDMenuItem;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.metadata.data.SortType;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SorterItemCollection;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.schedule.FDCScheduleCollection;
import com.kingdee.eas.fdc.schedule.FDCScheduleFactory;
import com.kingdee.eas.fdc.schedule.FDCScheduleInfo;
import com.kingdee.eas.fdc.schedule.FDCScheduleTaskCollection;
import com.kingdee.eas.fdc.schedule.FDCScheduleTaskInfo;
import com.kingdee.eas.fdc.schedule.ScheduleHelper;
import com.kingdee.eas.util.client.EASResource;

/**
 * @(#)							
 * ��Ȩ��		�����������������޹�˾��Ȩ���� ������ �鿴��ʷ�汾
 * 
 * @author �ź���
 * @version EAS7.0
 * @createDate 2011-9-23
 * @see
 */

public class ScheduleHisVer {

	private static ScheduleHisVer instance;
	private FDCScheduleInfo schedule;

	public FDCScheduleInfo getSchedule() {
		return schedule;
	}

	public void setSchedule(FDCScheduleInfo schedule) {
		this.schedule = schedule;
	}

	private ScheduleHisVer() {
	}

	public static ScheduleHisVer getInstance() {
		return instance == null ? new ScheduleHisVer() : instance;
	}

	/**
	 * ����editDataֵ�����¼���鿴��ʷ�汾�а�ť
	 * 
	 * @param objectValue
	 * @param coreUIObject
	 * @throws BOSException
	 */
	public void loadHistoryVer(IObjectValue objectValue,
			CoreUIObject coreUIObject) throws BOSException {
		FDCScheduleInfo editData = (FDCScheduleInfo) objectValue;
		FDCScheduleBaseEditUI editUI = (FDCScheduleBaseEditUI) coreUIObject;
		if (editData != null && editData.getProject() != null) {
			EntityViewInfo view = getView(editData);
			editUI.btnHisVerion.removeAllAssistButton();
			FDCScheduleCollection historyScheudles = FDCScheduleFactory.getRemoteInstance().getFDCScheduleCollection(view);
			loadHisMenu(editUI, historyScheudles);
			editUI.menuViewHis.updateUI();
			editUI.btnHisVerion.updateUI();
		}
	}

	/**
	 * ��ʷ�汾�鿴��ť����
	 * <p>
	 * ���г����5���汾<br>
	 * ������а汾���򰴵���ʱ����ݷ���
	 * 
	 * @author emanon
	 * @param editUI
	 * @param historyScheudles
	 */
	private void loadHisMenu(FDCScheduleBaseEditUI editUI,
			FDCScheduleCollection historyScheudles) {
		if (historyScheudles == null || historyScheudles.size() < 1) {
			return;
		}
		int size = historyScheudles.size();
		// ��ʼ5��
		int first5 = 5 > size ? size : 5;
		for (int i = 0; i < first5; i++) {
			FDCScheduleInfo historyScheudle = historyScheudles.get(i);
			KDMenuItem menu = buildHisMenu(editUI, historyScheudle);
			editUI.menuViewHis.add(menu);
			editUI.btnHisVerion.addAssistMenuItem(menu);
		}
		// ������������
		Calendar cal = Calendar.getInstance();
		int curYear = 10000;
		KDMenu menu = null;
		for (int i = first5; i < historyScheudles.size(); i++) {
			FDCScheduleInfo historyScheudle = historyScheudles.get(i);
			Date versionDate = historyScheudle.getVersionDate();
			cal.setTime(versionDate);
			int year = cal.get(Calendar.YEAR);
			if (year < curYear || menu == null) {
				menu = new KDMenu(String.valueOf(year));
				editUI.btnHisVerion.addAssistMenuItem(menu);
				curYear = year;
			}
			KDMenuItem menuItem = buildHisMenu(editUI, historyScheudle);
			menu.add(menuItem);
		}
	}

	/**
	 * ����һ����ʷ�汾�Ĳ˵�����ť
	 * <p>
	 * ����setToolTipText��������ΪKDMenuItem�и÷�������Ч<br>
	 * ����ͼ�꼰���������ʱ����������ʷ�汾
	 * 
	 * @author emanon
	 * @param editUI
	 * @param historyScheudle
	 * @return
	 */
	private KDMenuItem buildHisMenu(FDCScheduleBaseEditUI editUI,
			FDCScheduleInfo historyScheudle) {
		KDMenuItem menu = new KDMenuItem(historyScheudle.getVersionName()) {
			public void setToolTipText(String text) {
				String oldText = getToolTipText();
				putClientProperty(TOOL_TIP_TEXT_KEY, text);
				ToolTipManager toolTipManager = ToolTipManager.sharedInstance();
				if (text != null) {
					if (oldText == null) {
						toolTipManager.registerComponent(this);
					}
				} else {
					toolTipManager.unregisterComponent(this);
				}
			}
		};
		menu.setIcon(EASResource.getIcon("imgTbtn_historyedition"));
		menu.setUserObject(historyScheudle);
		initToolTipText(historyScheudle, menu);
		initListener(editUI, menu);
		return menu;
	}

	/**
	 * ������ʾ��Ϣ
	 * <p>
	 * �ƻ��汾�ĵ�����Ϣ������ط������������˴�����ʾ��Ϣ����Ϣ��ʾ����<br>
	 * ����˵���ϳ�ʱ������td��ȹ̶�180,
	 * 
	 * @author emanon
	 * @param historyScheudle
	 * @param menu
	 */
	private void initToolTipText(FDCScheduleInfo historyScheudle,
			KDMenuItem menu) {
		StringBuffer toolTipText = new StringBuffer();
		toolTipText.append("<html><table>");
		// ��������
		toolTipText
				.append("<tr><td><font size=3>�������ڣ�</font></td><td><font size=3>");
		toolTipText.append(historyScheudle.getVersionDate());
		toolTipText.append("</font></td></tr>");
		// ����ԭ��
		toolTipText
				.append("<tr><td><font size=3>����ԭ��</font></td><td><font size=3>");
		if (historyScheudle.getAdjustReason() == null) {
			toolTipText.append("��ʼ�汾");
		} else {
			toolTipText.append(historyScheudle.getAdjustReason().getName());
		}
		toolTipText.append("</font></td></tr>");
		// ����˵��
		toolTipText
				.append("<tr><td valign = 'top'><font size=3>����˵����</font></td>");
		String desc = historyScheudle.getDescription();
		if (FDCHelper.isEmpty(desc)) {
			toolTipText.append("<td><font size=3>��");
		} else {
			// ����һ�����Ⱦͻ���
			if (desc.length() > 14) {
				toolTipText.append("<td width=180><font size=3>");
			} else {
				toolTipText.append("<td><font size=3>");
			}
			toolTipText.append(desc);
		}
		toolTipText.append("</font></td></tr>");
		toolTipText.append("</table></html>");
		menu.setToolTipText(toolTipText.toString());
	}

	/**
	 * ��Ӽ��������ʱ����������ʷ�汾
	 * 
	 * @param editUI
	 * @param menu
	 */
	private void initListener(final FDCScheduleBaseEditUI editUI, final KDMenuItem menu) {
		menu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					FDCScheduleInfo info = (FDCScheduleInfo) menu.getUserObject();
					EntityViewInfo view = ScheduleHelper.getScheduleView(info.getId().toString());
					FDCScheduleCollection scheduleCollection = FDCScheduleFactory.getRemoteInstance().getFDCScheduleCollection(view);
					if (scheduleCollection != null && scheduleCollection.size() > 0) {
						FDCScheduleInfo newSchedule = scheduleCollection.get(0);
						handleTasks(newSchedule);
						FDCScheduleInfo oldSchedule = null;
						if (newSchedule.getVersion() > 1f) {
							oldSchedule = (FDCScheduleInfo) FDCScheduleFactory.getRemoteInstance().getSchedule4Compare(null, newSchedule.getId().toString());
							if (oldSchedule != null && oldSchedule.getTaskEntrys() != null && oldSchedule.getTaskEntrys().size() > 0) {
								FDCScheduleTaskCollection taskEntrys = newSchedule.getTaskEntrys();
								FDCScheduleTaskCollection oldTasks = oldSchedule.getTaskEntrys();
								for (int i = 0; i < taskEntrys.size(); i++) {
									String id = taskEntrys.get(i).getSrcID();
									FDCScheduleTaskInfo task = newSchedule.getTaskEntrys().get(i);
									for (int j = 0; j < oldTasks.size(); j++) {
										FDCScheduleTaskInfo oldTask = oldTasks.get(j);
										if (oldTask.getSrcID().toString().equals(id)) {
											task.put("myOldStartDate", oldTask.getStart());
											task.put("myOldEndDate", oldTask.getEnd());
											task.put("myOldCheckDate", oldTask.getCheckDate());
										}
									}
								}
							}
						}
						setSchedule(newSchedule);
						editUI.txtVersion.setText(newSchedule.getVersionName());
						editUI.loadData2Gantt(newSchedule);
						loadHistoryVer(newSchedule, editUI);
					}
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}

		});
	}

	private EntityViewInfo getView(final FDCScheduleInfo editData) {
		EntityViewInfo view = new EntityViewInfo();
		SelectorItemCollection selectors = view.getSelector();
		selectors.add("id");
		selectors.add("project.id");
		selectors.add("version");
		selectors.add("versionName");
		selectors.add("versionDate");
		selectors.add("adjustReason.id");
		selectors.add("adjustReason.name");
		selectors.add("description");

		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(
				new FilterItemInfo("project.id", editData.getProject().getId()
						.toString()));
		if (editData.getId() != null) {
			filter.getFilterItems().add(
					new FilterItemInfo("id", editData.getId().toString(),
							CompareType.NOTEQUALS));
		}
		if (editData.getProjectSpecial() != null) {
			filter.getFilterItems().add(
					new FilterItemInfo("projectSpecial.id", editData
							.getProjectSpecial().getId().toString()));
		} else {
			filter.getFilterItems().add(
					new FilterItemInfo("projectSpecial.id", null,
							CompareType.EQUALS));
		}
		SorterItemCollection sorter = new SorterItemCollection();
		// ��ԭ���İ��汾�����У���Ϊ������ʱ�����У����ڰ������
		SorterItemInfo dateSort = new SorterItemInfo("versionDate");
		dateSort.setSortType(SortType.DESCEND);
		sorter.add(dateSort);
		SorterItemInfo verSort = new SorterItemInfo("version");
		verSort.setSortType(SortType.DESCEND);
		sorter.add(verSort);
		view.setFilter(filter);
		view.setSorter(sorter);
		return view;
	}
	
	private void handleTasks(FDCScheduleInfo schedule) throws BOSException {
		// FDCScheduleTaskCollection newTasks = new FDCScheduleTaskCollection();
		// FDCScheduleTaskCollection taskEntrys = schedule.getTaskEntrys();
		// newTasks = ScheduleTaskHelper.getRelationReverseTasks(newTasks,
		// taskEntrys);
		// schedule.getTaskEntrys().addCollection(newTasks);
	}
	
	

}