/**
 * output package name
 */
package com.kingdee.eas.fdc.finance.client;

import java.awt.event.ActionEvent;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.swing.Action;
import javax.swing.event.ChangeEvent;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.table.CellTreeNode;
import com.kingdee.bos.ctrl.kdf.table.IColumn;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTMergeManager;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectBlock;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectManager;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.event.BeforeActionEvent;
import com.kingdee.bos.ctrl.kdf.table.event.BeforeActionListener;
import com.kingdee.bos.ctrl.kdf.table.event.KDTActiveCellEvent;
import com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent;
import com.kingdee.bos.ctrl.swing.KDTextField;
import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.bos.util.backport.Collections;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basedata.CellBinder;
import com.kingdee.eas.fdc.basedata.CostAccountCollection;
import com.kingdee.eas.fdc.basedata.CostAccountInfo;
import com.kingdee.eas.fdc.basedata.CostSplitStateEnum;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.basedata.client.CostSplitAcctUI;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.contract.ContractCostSplitEntryCollection;
import com.kingdee.eas.fdc.contract.ContractCostSplitEntryInfo;
import com.kingdee.eas.fdc.contract.PayReqUtils;
import com.kingdee.eas.fdc.finance.DepConPayPlanSplitBillFactory;
import com.kingdee.eas.fdc.finance.DepConPayPlanSplitBillInfo;
import com.kingdee.eas.fdc.finance.DepConPayPlanSplitEntryFactory;
import com.kingdee.eas.fdc.finance.DepConPayPlanSplitEntryInfo;
import com.kingdee.eas.fdc.finance.DepConPayPlanSplitItemFactory;
import com.kingdee.eas.fdc.finance.DepConPayPlanSplitItemInfo;
import com.kingdee.eas.fdc.finance.FDCDepConPayPlanBillFactory;
import com.kingdee.eas.fdc.finance.FDCDepConPayPlanBillInfo;
import com.kingdee.eas.fdc.finance.FDCDepConPayPlanEntryCollection;
import com.kingdee.eas.fdc.finance.FDCDepConPayPlanEntryFactory;
import com.kingdee.eas.fdc.finance.FDCDepConPayPlanEntryInfo;
import com.kingdee.eas.fdc.finance.FDCDepConPayPlanItemCollection;
import com.kingdee.eas.fdc.finance.FDCDepConPayPlanItemInfo;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.MsgBox;
import com.kingdee.jdbc.rowset.IRowSet;
import com.kingdee.util.UuidException;

/**
 * output class name
 */
public class DepConPayPlanSplitEditUI extends AbstractDepConPayPlanSplitEditUI {
	private static final Logger logger = CoreUIObject.getLogger(DepConPayPlanSplitEditUI.class);
	private int MAX_LEVEL = 0;
	private boolean isModified = false;
	public DepConPayPlanSplitEditUI() throws Exception {
		super();
	}
	public boolean isModify() {
		if(isModified){
			return true;
		}else{
			return super.isModify();
		}
	}
	public void actionRemoveLine_actionPerformed(ActionEvent e) throws Exception {
		if (getDetailTable() == this.tblContract) {
			MsgBox.showWarning("��ǩ����ͬ����ƻ���ֲ���ɾ����¼��");
			SysUtil.abort();
		} else {
			int actRowIdx = tblUnsettledCon.getSelectManager().getActiveRowIndex();
			IRow row = this.tblUnsettledCon.getRow(actRowIdx);
			DepConPayPlanSplitEntryInfo splitEntry = (DepConPayPlanSplitEntryInfo) row.getUserObject();
			if (splitEntry != null && !splitEntry.isIsLeaf()) {
				MsgBox.showWarning("����ѡ����в��ʺ�ɾ��������");
				SysUtil.abort();
			}
//			this.tblUnsettledCon.removeRow(actRowIdx);
		}
		super.actionRemoveLine_actionPerformed(e);
		
		BigDecimal total = null;
		for(int i = 0;i<this.tblUnsettledCon.getRowCount();i++){
			IRow row = tblUnsettledCon.getRow(i);
			DepConPayPlanSplitEntryInfo splitEntry = (DepConPayPlanSplitEntryInfo) row.getUserObject();
			int level = splitEntry.getLevel();
			if (!splitEntry.isIsLeaf()) {// ��Ҷ�ڵ�
				if (splitEntry.getId() == null) {// �����ַ�¼IDΪ��
					String planEntryId = splitEntry.getPayPlanEntry().getId().toString();
					FDCDepConPayPlanEntryInfo planEntry = findPlanEntry(planEntryId);
					FDCDepConPayPlanItemCollection itemColls = planEntry.getItems();
					List list = new ArrayList();
					for (Iterator iter = itemColls.iterator(); iter.hasNext();) {
						FDCDepConPayPlanItemInfo info = (FDCDepConPayPlanItemInfo) iter.next();
						list.add(info);
					}
					Collections.sort(list, FDCDepConPayPlanUtil.getSortComparator());
					for (Iterator iter = list.iterator(); iter.hasNext();) {
						FDCDepConPayPlanItemInfo item = (FDCDepConPayPlanItemInfo) iter.next();
						//���������
						IRow tempRow = null;
						for (int j = 0; j < this.tblUnsettledCon.getRowCount(); j++) {
							IRow _row = tblUnsettledCon.getRow(j);
							DepConPayPlanSplitEntryInfo splitEntryInfo = (DepConPayPlanSplitEntryInfo) _row.getUserObject();
							String planId = splitEntryInfo.getPayPlanEntry().getId().toString();
							if (planId.equals(planEntryId) && splitEntryInfo.isIsLeaf()) {
								total = FDCHelper.add(total, _row.getCell(getSplitAmtKey(item.getMonth())).getValue());
							} else if (planId.equals(planEntryId) && !splitEntryInfo.isIsLeaf()) {
								tempRow = _row;
							}
						}
						tempRow.getCell(getSplitAmtKey(item.getMonth())).setValue(total);
					}
				} else {
					String planEntryId = splitEntry.getPayPlanEntry().getId().toString();
					EntityViewInfo view = new EntityViewInfo();
					SelectorItemCollection selector = new SelectorItemCollection();
					selector.add("*");
					FilterInfo filter = new FilterInfo();
					filter.getFilterItems().add(new FilterItemInfo("entry.id", splitEntry.getId().toString()));
					view.setFilter(filter);
					view.setSelector(selector);
					CoreBaseCollection itemsColl = null;
					itemsColl = (CoreBaseCollection) DepConPayPlanSplitItemFactory.getRemoteInstance().getCollection(view);
					if (itemsColl == null || itemsColl.size() == 0) {
						return;
					}
					List list = new ArrayList();
					for (Iterator iter = itemsColl.iterator(); iter.hasNext();) {
						DepConPayPlanSplitItemInfo info = (DepConPayPlanSplitItemInfo) iter.next();
						list.add(info);
					}
					Collections.sort(list, FDCDepConPayPlanUtil.getSortComparator());
					for (Iterator iter = list.iterator(); iter.hasNext();) {
						DepConPayPlanSplitItemInfo item = (DepConPayPlanSplitItemInfo) iter.next();
						IRow tempRow = null;
						for (int j = 0; j < this.tblUnsettledCon.getRowCount(); j++) {
							IRow _row = tblUnsettledCon.getRow(j);
							DepConPayPlanSplitEntryInfo splitEntryInfo = (DepConPayPlanSplitEntryInfo) _row.getUserObject();
							String planId = splitEntryInfo.getPayPlanEntry().getId().toString();
							if (planId.equals(planEntryId) && splitEntryInfo.isIsLeaf()) {
								total = FDCHelper.add(total, _row.getCell(getSplitAmtKey(item.getMonth())).getValue());
							} else if (planId.equals(planEntryId) && !splitEntryInfo.isIsLeaf()) {
								tempRow = _row;
							}
						}
						tempRow.getCell(getSplitAmtKey(item.getMonth())).setValue(total);
					}
				}
			}
		}
	}

	public void actionEdit_actionPerformed(ActionEvent e) throws Exception {
		if (this.editData != null && this.editData.getId() != null) {
			// δ��ֵĵ��ݲ�������,���У���ڱ����ֵ�ʱ��Ϳ��Կ��Ƶõ��ˡ���Ϊֻ����ȫ��ֵĵ��ݲ����Ա���
			FDCSQLBuilder builder = new FDCSQLBuilder();
			builder.appendSql("select FState from  T_FNC_DepConPayPlanSplitBill where FID = ?  ");
			builder.addParam(this.editData.getId().toString());
			IRowSet rowSet = builder.executeQuery();
			if (rowSet != null && rowSet.next()) {
				String state = rowSet.getString("FState");
				if (state != null && FDCBillStateEnum.AUDITTED_VALUE.equals(state)) {
					MsgBox.showWarning("�������������������²�֣�");
					SysUtil.abort();
				}
			}
		}

		this.actionSplit.setEnabled(true);
		
		super.actionEdit_actionPerformed(e);
	}

	protected void attachListeners() {
		// TODO Auto-generated method stub

	}

	protected void detachListeners() {
		// TODO Auto-generated method stub

	}

	public void actionSave_actionPerformed(ActionEvent e) throws Exception {

		super.actionSave_actionPerformed(e);
	}

	protected void tblUnsettledCon_editStopped(KDTEditEvent e) throws Exception {
		BigDecimal total = null;

		int rowIndex = e.getRowIndex();
		IRow currentRow = this.tblUnsettledCon.getRow(rowIndex);
		DepConPayPlanSplitEntryInfo splitEntry = (DepConPayPlanSplitEntryInfo) currentRow.getUserObject();
		String payPlanId = splitEntry.getPayPlanEntry().getId().toString();

		int colIndex = e.getColIndex();
		IColumn column = tblUnsettledCon.getColumn(colIndex);
		String key = column.getKey();
		if (key.indexOf("month_split_amt") != -1 && isMonthColumn(colIndex)) {
			// ��payPlanEntryID+levelΪ�жϱ�׼
			IRow tempRow = null;
			for (int i = 0; i < this.tblUnsettledCon.getRowCount(); i++) {
				IRow row = tblUnsettledCon.getRow(i);
				DepConPayPlanSplitEntryInfo splitEntryInfo = (DepConPayPlanSplitEntryInfo) row.getUserObject();
				String planId = splitEntryInfo.getPayPlanEntry().getId().toString();
				if (planId.equals(payPlanId) && splitEntryInfo.isIsLeaf()) {
					total = FDCHelper.add(total, row.getCell(colIndex).getValue());
				} else if (planId.equals(payPlanId) && !splitEntryInfo.isIsLeaf()) {
					tempRow = row;
				}
			}
			tempRow.getCell(colIndex).setValue(total);
		}
	}

	protected void setBeforeAction() {
		tblContract.setBeforeAction(new BeforeActionListener() {
			public void beforeAction(BeforeActionEvent e) {
				if (BeforeActionEvent.ACTION_DELETE == e.getType()) {
					for (int i = 0; i < tblContract.getSelectManager().size(); i++) {
						KDTSelectBlock block = tblContract.getSelectManager().get(i);
						for (int rowIndex = block.getBeginRow(); rowIndex <= block.getEndRow(); rowIndex++) {
							for (int colIndex = block.getBeginCol(); colIndex <= block.getEndCol(); colIndex++) {
								IColumn column = tblContract.getColumn(colIndex);
								String key = column.getKey();
								int curIndex = 10;
								if (key.indexOf("month_split_amt") != -1 && isMonthColumn(colIndex)) {
									curIndex = tblContract.getColumnIndex(key);
								}
								if (colIndex < curIndex + 1) {
									e.setCancel(true);
									continue;
								}
								KDTEditEvent event = new KDTEditEvent(tblContract, null, null, rowIndex, colIndex, true, 1);
								try {
									tblContract_editStopped(event);
								} catch (Exception e1) {
									handUIException(e1);
								}
							}
						}
					}
				} else if (false) {
					// ��������
				}
			}
		});
		tblUnsettledCon.setBeforeAction(new BeforeActionListener() {
			public void beforeAction(BeforeActionEvent e) {
				if (BeforeActionEvent.ACTION_DELETE == e.getType()) {
					for (int i = 0; i < tblUnsettledCon.getSelectManager().size(); i++) {
						KDTSelectBlock block = tblUnsettledCon.getSelectManager().get(i);
						for (int rowIndex = block.getBeginRow(); rowIndex <= block.getEndRow(); rowIndex++) {
							for (int colIndex = block.getBeginCol(); colIndex <= block.getEndCol(); colIndex++) {
								IColumn column = tblContract.getColumn(colIndex);
								String key = column.getKey();
								int curIndex = 10;
								if (key.indexOf("month_split_amt") != -1 && isMonthColumn(colIndex)) {
									curIndex = tblContract.getColumnIndex(key);
								}
								if (colIndex < curIndex + 1) {
									e.setCancel(true);
									continue;
								}
								KDTEditEvent event = new KDTEditEvent(tblUnsettledCon, null, null, rowIndex, colIndex, true, 1);
								try {
									tblUnsettledCon_editStopped(event);
								} catch (Exception e1) {
									handUIException(e1);
								}
							}
						}
					}
				} else if (false) {
					// ��������
				}
			}
		});
	}

	protected void tblContract_editStopped(KDTEditEvent e) throws Exception {
		BigDecimal total = null;

		int rowIndex = e.getRowIndex();
		IRow currentRow = this.tblContract.getRow(rowIndex);
		DepConPayPlanSplitEntryInfo splitEntry = (DepConPayPlanSplitEntryInfo) currentRow.getUserObject();
		String payPlanId = splitEntry.getPayPlanEntry().getId().toString();

		int colIndex = e.getColIndex();
		IColumn column = tblContract.getColumn(colIndex);
		String key = column.getKey();
		if (key.indexOf("month_split_amt") != -1 && isMonthColumn(colIndex)) {
			// ��payPlanEntryID+levelΪ�жϱ�׼
			IRow tempRow = null;
			for (int i = 0; i < this.tblContract.getRowCount(); i++) {
				IRow row = tblContract.getRow(i);
				DepConPayPlanSplitEntryInfo splitEntryInfo = (DepConPayPlanSplitEntryInfo) row.getUserObject();
				String planId = splitEntryInfo.getPayPlanEntry().getId().toString();
				if (planId.equals(payPlanId) && splitEntryInfo.isIsLeaf()) {
					total = FDCHelper.add(total, row.getCell(colIndex).getValue());
				} else if (planId.equals(payPlanId) && !splitEntryInfo.isIsLeaf()) {
					tempRow = row;
				}
			}
			tempRow.getCell(colIndex).setValue(total);
		}
	}

	/**
	 * �˷����������θ�ȷ�Ϲ��������˵�������ˣ�����ing......
	 */
	protected void tblUnsettledCon_activeCellChanged(KDTActiveCellEvent e) throws Exception {
		// final int colIndex = e.getColumnIndex();
		// final int rowIndex = e.getRowIndex();
		// /**
		// * ��ӹ�����ĿF7
		// */
		// final KDBizPromptBox bizPrjBox = new KDBizPromptBox();
		// if (colIndex == this.tblUnsettledCon.getColumn("project.name")
		// .getColumnIndex()) {
		// bizPrjBox.addDataChangeListener(new DataChangeListener() {
		// public void dataChanged(DataChangeEvent eventObj) {
		// if (eventObj != null && eventObj.getNewValue() != null) {
		// }
		// }
		// });
		//
		// ObjectValueRender ovr = new ObjectValueRender();
		// ovr.setFormat(new BizDataFormat("$name$"));
		// tblUnsettledCon.getCell(rowIndex, "project.name").setRenderer(ovr);
		//
		// bizPrjBox.setDisplayFormat("$name$");
		// bizPrjBox.setEditFormat("$number$");
		// bizPrjBox.setCommitFormat("$number$");
		// bizPrjBox.setRequired(true);
		// bizPrjBox
		// .setQueryInfo("com.kingdee.eas.fdc.basedata.app.F7ProjectQuery");
		// KDTDefaultCellEditor prjEditor = new KDTDefaultCellEditor(bizPrjBox);
		// this.tblUnsettledCon.getColumn("project.name").setEditor(prjEditor);
		//
		// // �ı���ʾ��ʽ
		// final IFormatter formProject = bizPrjBox.getDisplayFormatter();
		// IFormatter formatProject = new IFormatter() {
		// public void applyPattern(String pattern) {
		// formProject.applyPattern(pattern);
		// }
		//
		// public String valueToString(Object o) {
		// if (formProject != null
		// && formProject.valueToString(o) != null) {
		// return formProject.valueToString(o).replaceAll("!",
		// "\\.");
		// }
		// return null;
		// }
		// };
		// bizPrjBox.setDisplayFormatter(formatProject);
		// bizPrjBox.setEnabledMultiSelection(false);
		// // ����F7�Ĺ�������
		// EntityViewInfo view = new EntityViewInfo();
		// FilterInfo filter = new FilterInfo();
		// filter.getFilterItems().add(
		// new FilterItemInfo("fullOrgUnit.id", SysContext
		// .getSysContext().getCurrentFIUnit().getId()));
		// // ����ѡȡ��ϸ������Ŀ
		// filter.getFilterItems().add(
		// new FilterItemInfo("isLeaf", Boolean.TRUE));
		// view.setFilter(filter);
		// bizPrjBox.setEntityViewInfo(view);
		//
		// bizPrjBox.addDataChangeListener(new DataChangeListener() {
		// public void dataChanged(DataChangeEvent eventObj) {
		// if (eventObj.getOldValue() != null
		// && eventObj.getNewValue() != eventObj.getOldValue()) {
		// // ����ѡ��ֵ����ͬ�Ļ���ͬһ�еĳɱ���Ŀ��Ԫ���ֵ�ÿ�
		// tblUnsettledCon.getCell(rowIndex, "costAccount.id")
		// .setValue(null);
		// tblUnsettledCon.getCell(rowIndex, "costAccount.number")
		// .setValue(null);
		// tblUnsettledCon.getCell(rowIndex, "costAccount.name")
		// .setValue(null);
		// }
		// }
		// });
		// }
		// if (colIndex == this.tblUnsettledCon.getColumn("costAccount.number")
		// .getColumnIndex()) {
		// /**
		// * ��ӳɱ���ĿF7
		// */
		// final KDBizPromptBox bizCostAccountBox = new KDBizPromptBox();
		// bizCostAccountBox.addDataChangeListener(new DataChangeListener() {
		// public void dataChanged(DataChangeEvent eventObj) {
		// if (eventObj.getNewValue() != null
		// && eventObj.getNewValue() instanceof CostAccountInfo) {
		// tblUnsettledCon.getCell(rowIndex, "costAccount.id")
		// .setValue(
		// ((CostAccountInfo) eventObj
		// .getNewValue()).getId());
		// tblUnsettledCon.getCell(rowIndex, "costAccount.name")
		// .setValue(
		// ((CostAccountInfo) eventObj
		// .getNewValue()).getName());
		// } else if (eventObj.getNewValue() != null
		// && eventObj.getNewValue() instanceof String) {
		//
		// }
		// }
		// });
		//
		// ObjectValueRender ovr = new ObjectValueRender();
		// ovr.setFormat(new BizDataFormat("$longNumber$"));
		// tblUnsettledCon.getCell(rowIndex, "costAccount.number")
		// .setRenderer(ovr);
		//
		// bizCostAccountBox.setDisplayFormat("$number$");
		// bizCostAccountBox.setEditFormat("$number$");
		// bizCostAccountBox.setCommitFormat("$number$");
		// bizCostAccountBox.setRequired(true);
		// bizCostAccountBox
		// .setQueryInfo("com.kingdee.eas.fdc.basedata.app.F7CostAccountQuery");
		// KDTDefaultCellEditor costAccountEditor = new KDTDefaultCellEditor(
		// bizCostAccountBox);
		// this.tblUnsettledCon.getColumn("costAccount.number").setEditor(
		// costAccountEditor);
		// // �ı���ʾ��ʽ
		// final IFormatter formatCostAccountF7 = bizCostAccountBox
		// .getDisplayFormatter();
		// IFormatter formatCostAccount = new IFormatter() {
		// public void applyPattern(String pattern) {
		// formatCostAccountF7.applyPattern(pattern);
		// }
		//
		// public String valueToString(Object o) {
		// if (formatCostAccountF7 != null
		// && formatCostAccountF7.valueToString(o) != null) {
		// return formatCostAccountF7.valueToString(o).replaceAll(
		// "!", "\\.");
		// }
		// return null;
		// }
		// };
		//
		// if (bizCostAccountBox != null) {
		// bizCostAccountBox.addSelectorListener(new SelectorListener() {
		// public void willShow(SelectorEvent e) {
		// try {
		// Object tempProject = tblUnsettledCon.getCell(
		// rowIndex, "project.name").getValue();
		// if (tempProject != null && !"".equals(tempProject)) {
		// if (!(tempProject instanceof String)) {
		// bizCostAccountBox.getQueryAgent()
		// .resetRuntimeEntityView();
		// if (((CurProjectInfo) tempProject).getId()
		// .toString() == null) {
		// bizCostAccountBox.setValue(null);
		// return;
		// } else {
		// EntityViewInfo view = new EntityViewInfo();
		// FilterInfo filter = new FilterInfo();
		// filter
		// .getFilterItems()
		// .add(
		// new FilterItemInfo(
		// "curProject.id",
		// ((CurProjectInfo) tempProject)
		// .getId()
		// .toString()));
		// filter
		// .getFilterItems()
		// .add(
		// new FilterItemInfo(
		// "curProject.longNumber",
		// ((CurProjectInfo) tempProject)
		// .getId()
		// .toString()));
		// filter.setMaskString(" #0 or #1 ");
		// view.setFilter(filter);
		// bizCostAccountBox.getQueryAgent()
		// .setEntityViewInfo(view);
		// }
		// } else {
		// bizCostAccountBox.getQueryAgent()
		// .resetRuntimeEntityView();
		// if ((String) tempProject == null) {
		// bizCostAccountBox.setValue(null);
		// return;
		// } else {
		// EntityViewInfo view = new EntityViewInfo();
		// FilterInfo filter = new FilterInfo();
		// filter.getFilterItems().add(
		// new FilterItemInfo(
		// "curProject.id",
		// (String) tempProject));
		// filter
		// .getFilterItems()
		// .add(
		// new FilterItemInfo(
		// "curProject.longNumber",
		// (String) tempProject));
		// filter.setMaskString(" #0 or #1 ");
		// view.setFilter(filter);
		// bizCostAccountBox.getQueryAgent()
		// .setEntityViewInfo(view);
		// }
		// }
		// } else {
		// // �Ѿ������������Ϊɶ��Ҫ����F7�����أ�����������������
		// FDCMsgBox.showError("����ѡ�񹤳���Ŀ");
		// SysUtil.abort();
		// return;
		// }
		// } catch (Exception e1) {
		// e1.printStackTrace();
		// }
		// }
		// });
		// bizCostAccountBox.setDisplayFormatter(formatCostAccount);
		// bizCostAccountBox.setEnabledMultiSelection(false);
		// }
		// }
		//	
	}

	protected void tblContract_tableClicked(com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e) throws Exception {

		KDTSelectBlock sb = tblContract.getSelectManager().get();
		if (sb != null) {
			if (e.getClickCount() == 2) { // ˫����ȷ��
			} else if (e.getClickCount() == 1) {
				int selRow = sb.getBeginRow();
				int selCol = sb.getBeginCol();
				if (selCol == tblContract.getColumnIndex("conNumber") && tblContract.getRow(selRow).getCell("conNumber").getValue() instanceof CellTreeNode) {
					((CellTreeNode) tblContract.getRow(selRow).getCell("conNumber").getValue()).doClick(tblContract, e);
					tblContract.validate();
				}
			}
		}
	}

	// ������ʶ�Ƿ��в�ֶ���
	private boolean hasSplitAction = false;

	public void loadFields() {
		super.loadFields();

		for (int i = 0; i < tblContract.getRowCount(); i++) {
		}

	}

	private IUIWindow acctUI = null;

	public void actionSplit_actionPerformed(ActionEvent e) throws Exception {
		if (getDetailTable() == this.tblUnsettledCon) {// ��ǩ����ͬ
			if (this.tblUnsettledCon.getRowCount() == 0) {
				MsgBox.showWarning("����ѡ���У�");
				SysUtil.abort();
			}
			int actRowIdx = tblUnsettledCon.getSelectManager().getActiveRowIndex();
			if (actRowIdx < 0) {
				FDCMsgBox.showWarning("��ѡ��Ҫ�������У�");
				SysUtil.abort();
			}
			IRow row = this.tblUnsettledCon.getRow(actRowIdx);
			if (row.getUserObject() != null && row.getCell("costAccount.id").getValue() != null) {
				MsgBox.showWarning("����ѡ����в��ʺϲ�ֲ�����������ѡ��");
				SysUtil.abort();
			}

			UIContext uiContext = new UIContext(this);
			Object o = getUIContext().get("node");
			if (o != null && o instanceof CurProjectInfo) {
				uiContext.put("curProject", (CurProjectInfo) o);
			}
			CostAccountCollection accts = null;
			// ѡ���Ŀ
			acctUI = UIFactory.createUIFactory(UIFactoryName.MODEL).create(com.kingdee.eas.fdc.basedata.client.CostSplitAcctUI.class.getName(), uiContext, null, null);
			acctUI.show();

			IUIWindow uiWin = acctUI;
			if (((CostSplitAcctUI) uiWin.getUIObject()).isOk()) {
				accts = ((CostSplitAcctUI) uiWin.getUIObject()).getData();
				for (int i = 0; i < accts.size(); i++) {
					CostAccountInfo costAccount = (CostAccountInfo) accts.get(i);
					if (!costAccount.getCurProject().isIsLeaf()) {
						MsgBox.showWarning("����ѡ����ϸ������Ŀ�µĳɱ���Ŀ��");
						SysUtil.abort();
					}
				}
			} else {
				return;
			}

			for (int i = accts.size() - 1; i < accts.size(); i--) {
				if (i < 0) {
					break;
				}
				IRow _row = this.tblUnsettledCon.addRow(actRowIdx + 1);

				// _row.setUserObject(row.getUserObject());

				DepConPayPlanSplitEntryInfo splitEntry = new DepConPayPlanSplitEntryInfo();
				splitEntry.setPayPlanEntry(((DepConPayPlanSplitEntryInfo) row.getUserObject()).getPayPlanEntry());
				splitEntry.setLevel(1);
				splitEntry.setIsLeaf(true);
				_row.setUserObject(splitEntry);

				CostAccountInfo costAccount = (CostAccountInfo) accts.get(i);
				_row.getCell("project.id").setValue(costAccount.getCurProject().getId().toString());
				_row.getCell("project.name").setValue(costAccount.getCurProject().getName().replace('_', '.'));
				_row.getCell("costAccount.id").setValue(costAccount.getId().toString());
				_row.getCell("costAccount.number").setValue(costAccount.getLongNumber().replace('!', '.'));
				_row.getCell("costAccount.name").setValue(costAccount.getName());
			}
		} else {// ��ǩ����ͬ
			if (this.tblContract.getRowCount() == 0) {
				MsgBox.showWarning("����ѡ���У�");
				SysUtil.abort();
			}
			int actRowIdx = tblContract.getSelectManager().getActiveRowIndex();
			if (actRowIdx < 0) {
				FDCMsgBox.showWarning("��ѡ��Ҫ�������У�");
				SysUtil.abort();
			}
			IRow row = this.tblContract.getRow(actRowIdx);
			// ���ѡ�е��Ǹ���ƻ��Ĳ���У������κζ���
			if (row.getUserObject() != null && (row.getCell("costAccount.id").getValue() != null)) {
				MsgBox.showWarning("����ѡ����в��ʺϲ�ֲ�����������ѡ��");
				SysUtil.abort();
			}

			if (this.tblContract.getRow(row.getRowIndex() + 1) != null && this.tblContract.getRow(row.getRowIndex() + 1).getCell("conName").getValue() == null) {
				MsgBox.showWarning("���ܷ��������ֿ�Ŀ��");
				SysUtil.abort();
			}

			hasSplitAction = true;

			// TODO ���쵥Ԫ����
			// ���쵥Ԫ����
			// ����ɱ���Ŀ��Ϣ
			importCostAccountOfConSplit(row);
			isModified=true;
		}
	}

	private Map dataMap = null;

	public Map getDataMap() {
		if (dataMap == null) {
			fetchData();
			if (dataMap == null) {
				dataMap = new HashMap();
			}
		}
		return dataMap;
	}

	private void clear() {
		if (dataMap != null) {
			dataMap.clear();
		}
		dataMap = null;
	}

	protected void pnlBig_stateChanged(ChangeEvent e) throws Exception {
		if (tempEntrysColl == null || tempEntrysColl.size() == 0) {
			return;
		}
		resetTableHead(tempEntrysColl);

		if (flag) {// ��ֵ�������
			for (Iterator iter = _tempSplitEntryList.iterator(); iter.hasNext();) {
				DepConPayPlanSplitEntryInfo splitEntry = (DepConPayPlanSplitEntryInfo) iter.next();
				if (!splitEntry.isIsLeaf() && splitEntry.getLevel() == 0) {// ����Ƿ���ϸ�Ļ���Ҫ����Ӧ����ƻ���¼�ĺ�ͬ�����Ϣ
					IRow row = this.tblUnsettledCon.addRow();
					row.getStyleAttributes().setLocked(true);
					row.setUserObject(splitEntry);
					String payPlanId = splitEntry.getPayPlanEntry().getId().toString();
					// ���ݸ�ID�ҵ�����ƻ���¼
					FDCDepConPayPlanEntryInfo planEntry = findPlanEntry(payPlanId);
					// ����ͬ�����Ϣ
					row.getCell("contractId").setValue(planEntry.getContractBillId());
					row.getCell("unsettledConNumber").setValue(planEntry.getContractNumber());
					row.getCell("unsettledConName").setValue(planEntry.getContractName());
					row.getCell("unsettledConestPrice").setValue(planEntry.getContractLastestPrice());
					// �����Ŀ�����Ϣ(�ƻ�ֵ)
					DepConPayPlanSplitEntryInfo splitEntry2 = (DepConPayPlanSplitEntryInfo) row.getUserObject();
					EntityViewInfo view = new EntityViewInfo();
					SelectorItemCollection selector = new SelectorItemCollection();
					selector.add("*");
					FilterInfo filter = new FilterInfo();
					filter.getFilterItems().add(new FilterItemInfo("entry.id", splitEntry2.getId().toString()));
					view.setFilter(filter);
					view.setSelector(selector);
					CoreBaseCollection itemsColl = (CoreBaseCollection) DepConPayPlanSplitItemFactory.getRemoteInstance().getCollection(view);
					if (itemsColl == null || itemsColl.size() == 0) {
						return;
					}
					List list = new ArrayList();
					for (Iterator _iter = itemsColl.iterator(); _iter.hasNext();) {
						DepConPayPlanSplitItemInfo info = (DepConPayPlanSplitItemInfo) _iter.next();
						list.add(info);
					}
					Collections.sort(list, FDCDepConPayPlanUtil.getSortComparator());
					for (Iterator _iter = list.iterator(); _iter.hasNext();) {
						DepConPayPlanSplitItemInfo item = (DepConPayPlanSplitItemInfo) _iter.next();
						row.getCell(getPlanAmtKey(item.getMonth())).setValue(item.getPlanAmt());
						row.getCell(getSplitAmtKey(item.getMonth())).setValue(item.getSplitAmt());
					}
				} else if (splitEntry.isIsLeaf()) {// �п�Ŀ�Ĳ�ַ�¼��
					IRow row = this.tblUnsettledCon.addRow();
					row.setUserObject(splitEntry);
					row.getCell("project.id").setValue(splitEntry.getCostAccount().getCurProject().getId());
					row.getCell("project.name").setValue(splitEntry.getCostAccount().getCurProject().getName().replace('_', '.'));
					row.getCell("costAccount.id").setValue(splitEntry.getCostAccount().getId());
					row.getCell("costAccount.number").setValue(splitEntry.getCostAccount().getLongNumber().replace('!', '.'));
					row.getCell("costAccount.name").setValue(splitEntry.getCostAccount().getName());
					// �����Ŀ�����Ϣ(�ƻ�ֵ)
					DepConPayPlanSplitEntryInfo splitEntry2 = (DepConPayPlanSplitEntryInfo) row.getUserObject();
					EntityViewInfo view = new EntityViewInfo();
					SelectorItemCollection selector = new SelectorItemCollection();
					selector.add("*");
					FilterInfo filter = new FilterInfo();
					filter.getFilterItems().add(new FilterItemInfo("entry.id", splitEntry2.getId().toString()));
					view.setFilter(filter);
					view.setSelector(selector);
					CoreBaseCollection itemsColl = (CoreBaseCollection) DepConPayPlanSplitItemFactory.getRemoteInstance().getCollection(view);
					if (itemsColl == null || itemsColl.size() == 0) {
						return;
					}
					List list = new ArrayList();
					for (Iterator _iter = itemsColl.iterator(); _iter.hasNext();) {
						DepConPayPlanSplitItemInfo info = (DepConPayPlanSplitItemInfo) _iter.next();
						list.add(info);
					}
					Collections.sort(list, FDCDepConPayPlanUtil.getSortComparator());
					for (Iterator _iter = list.iterator(); _iter.hasNext();) {
						DepConPayPlanSplitItemInfo item = (DepConPayPlanSplitItemInfo) _iter.next();
						row.getCell(getSplitAmtKey(item.getMonth())).setValue(item.getSplitAmt());
					}
				}
			}
			_tempSplitEntryList.clear();
		} else {
			if (tempSplitEntryList == null || tempSplitEntryList.size() == 0) {
				return;
			}

			for (Iterator iter = tempSplitEntryList.iterator(); iter.hasNext();) {
				FDCDepConPayPlanEntryInfo planEntry = (FDCDepConPayPlanEntryInfo) iter.next();
				IRow row = null;
				row = tblUnsettledCon.addRow();
				row.getStyleAttributes().setLocked(true);
				DepConPayPlanSplitEntryInfo splitEntry = new DepConPayPlanSplitEntryInfo();
				splitEntry.setPayPlanEntry(planEntry);
				splitEntry.setLevel(0);
				splitEntry.setIsLeaf(false);
				row.setUserObject(splitEntry);
				// ��ǩ��
				row.getCell("unsettledConNumber").setValue(planEntry.getContractNumber());
				row.getCell("unsettledConName").setValue(planEntry.getContractName());
				row.getCell("unsettledConestPrice").setValue(planEntry.getContractLastestPrice());
				// ������Ŀ
				// �ɱ���Ŀ

				AbstractObjectCollection items = (AbstractObjectCollection) planEntry.get("items");
				if (items == null || items.size() == 0) {
					return;
				}
				for (Iterator iterator = items.iterator(); iterator.hasNext();) {
					FDCDepConPayPlanItemInfo item = (FDCDepConPayPlanItemInfo) iterator.next();
					row.getCell(getPlanAmtKey(item.getMonth())).setValue(item.getAuditAmt());
				}
			}
			tempSplitEntryList.clear();
		}
	}

	private void fillItems(IRow row) {
		DepConPayPlanSplitEntryInfo splitEntry = (DepConPayPlanSplitEntryInfo) row.getUserObject();
		FDCDepConPayPlanEntryInfo planEntry = findPlanEntry(splitEntry.getPayPlanEntry().getId().toString());
		FDCDepConPayPlanItemCollection itemsColl = (FDCDepConPayPlanItemCollection) planEntry.get("items");
		if (itemsColl == null || itemsColl.size() == 0) {
			return;
		}
		List list = new ArrayList();
		for (Iterator iter = itemsColl.iterator(); iter.hasNext();) {
			FDCDepConPayPlanItemInfo info = (FDCDepConPayPlanItemInfo) iter.next();
			list.add(info);
		}
		Collections.sort(list, FDCDepConPayPlanUtil.getSortComparator());
		for (Iterator iter = list.iterator(); iter.hasNext();) {
			FDCDepConPayPlanItemInfo item = (FDCDepConPayPlanItemInfo) iter.next();
			row.getCell(getPlanAmtKey(item.getMonth())).setValue(item.getAuditAmt());
		}
	}

	private void loadRow(FDCDepConPayPlanEntryInfo planEntryInfo, IRow row) {
		if (getDetailTable() == this.tblContract) {// ��ǩ��
			row.getCell("contractId").setValue(planEntryInfo.getContractBillId());
			row.getCell("conNumber").setValue(planEntryInfo.getContractNumber());
			row.getCell("conName").setValue(planEntryInfo.getContractName());
			row.getCell("conPrice").setValue(planEntryInfo.getContractLastestPrice());
		}

	}

	private void fetchData() {
		Object o = getUIContext().get("payPlanID");
		if (o == null) {
			return;
		}
		String payPlanID = o.toString();
		Map param = new HashMap();
		param.put("payPlanID", payPlanID);
		try {
			dataMap = DepConPayPlanSplitBillFactory.getRemoteInstance().fetchData(param);
		} catch (EASBizException e) {
			e.printStackTrace();
		} catch (BOSException e) {
			e.printStackTrace();
		}
	}

	public String getPlanAmtKey(int month) {
		if (month > 12) {
			month = month % 12;
		}
		return "month_plan_amt" + "_" + month;
	}

	public String getSplitAmtKey(int month) {
		if (month > 12) {
			month = month % 12;
		}
		return "month_split_amt" + "_" + month;
	}

	// �ж��ǲ����·���
	private boolean isMonthColumn(int colIndex) {
		String key = getDetailTable().getColumnKey(colIndex);
		if (key != null && key.startsWith("month_")) {
			return true;
		}
		return false;
	}

	/**
	 * ���ݸ���ƻ���¼����Ŀ��Ŀ������ͷ
	 */
	private void resetTableHead(FDCDepConPayPlanEntryCollection entryColl) {

		if (getDetailTable().getRowCount() != 0) {
			return;
		}

		int startIndex = 7;
		for (int i = getDetailTable().getColumnCount() - 1; i >= 0; i--) {
			if (isMonthColumn(i)) {
				getDetailTable().removeColumn(i);
			}
		}

		if (getDetailTable() == this.tblContract) {
			startIndex = getDetailTable().getColumnIndex("costAccount.name") + 1;
		} else {
			startIndex = getDetailTable().getColumnIndex("costAccount.name") + 1;
		}

		IRow headRow1 = getDetailTable().getHeadRow(0);
		IRow headRow2 = getDetailTable().getHeadRow(1);
		if (entryColl != null) {
			FDCDepConPayPlanEntryInfo entry = (FDCDepConPayPlanEntryInfo) entryColl.get(0);
			FDCDepConPayPlanItemCollection itemColl = entry.getItems();
			List list = new ArrayList();
			for (Iterator iter = itemColl.iterator(); iter.hasNext();) {
				FDCDepConPayPlanItemInfo info = (FDCDepConPayPlanItemInfo) iter.next();
				list.add(info);
			}
			Collections.sort(list, FDCDepConPayPlanUtil.getSortComparator());
			for (Iterator iter = list.iterator(); iter.hasNext();) {
				FDCDepConPayPlanItemInfo item = (FDCDepConPayPlanItemInfo) iter.next();
				int year = item.getYear();
				int month = item.getMonth();
				// �ƻ����
				IColumn column = getDetailTable().addColumn(startIndex);
				column.setKey(getPlanAmtKey(month));
				column.setEditor(CellBinder.getCellNumberEdit());
				column.setMoveable(false);// �����ƶ�
				column.getStyleAttributes().setLocked(true);// �ƻ������޸�
				FDCHelper.formatTableNumber(getDetailTable(), column.getKey());
				// ��ֽ��
				column = getDetailTable().addColumn(startIndex + 1);
				column.setKey(getSplitAmtKey(month));
				column.setEditor(CellBinder.getCellNumberEdit());
				column.setMoveable(false);
				FDCHelper.formatTableNumber(getDetailTable(), column.getKey());
				// �б���
				// ����12
				// ��ͷ�������ں�
				String title = year + "��" + month + "��";
				headRow1.getCell(startIndex).setValue(title);
				headRow1.getCell(startIndex + 1).setValue(title);
				headRow2.getCell(startIndex).setValue("�ƻ����");
				headRow2.getCell(startIndex + 1).setValue(" ��ֽ��");
				startIndex += 2;
			}
			table.getHeadMergeManager().mergeBlock(0, 0, 1, table.getColumnCount() - 1, KDTMergeManager.FREE_MERGE);
		}
	}

	// ���ݺ�ͬ����ƻ���¼��ֵ�payPlanId�ҵ���ͬ����ƻ���¼
	private FDCDepConPayPlanEntryInfo findPlanEntry(String payPlanId) {
		FDCDepConPayPlanEntryInfo planEntry = null;
		try {
			planEntry = (FDCDepConPayPlanEntryInfo) FDCDepConPayPlanEntryFactory.getRemoteInstance().getValue(new ObjectUuidPK(BOSUuid.read(payPlanId)));
		} catch (EASBizException e) {
			e.printStackTrace();
		} catch (BOSException e) {
			e.printStackTrace();
		} catch (UuidException e) {
			e.printStackTrace();
		}
		return planEntry;
	}

	// ���ݺ�ͬ����ƻ���¼��id�ҵ���ͬ����ƻ���ַ�¼
	private CoreBaseCollection findSplitEntry(String planId) {
		EntityViewInfo view = new EntityViewInfo();
		SelectorItemCollection selector = new SelectorItemCollection();
		selector.add("*");
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("FPayPlanEntryID", planId));
		CoreBaseCollection splitEntryColl = null;
		try {
			splitEntryColl = DepConPayPlanSplitEntryFactory.getRemoteInstance().getCollection(view);
		} catch (BOSException e) {
			e.printStackTrace();
		}
		return splitEntryColl;
	}

	private CoreBaseCollection getSplitEntryColl(String splitBillId) {
		/*
		 * EntityViewInfo view = new EntityViewInfo(); SelectorItemCollection
		 * selector = new SelectorItemCollection();
		 * selector.add("costAccount.id"); selector.add("costAccount.number");
		 * selector.add("costAccount.longNumber");
		 * selector.add("costAccount.name");
		 * selector.add("costAccount.curProject.id");
		 * selector.add("costAccount.curProject.number");
		 * selector.add("costAccount.curProject.longNumber");
		 * selector.add("costAccount.curProject.name"); FilterInfo filter = new
		 * FilterInfo(); filter.getFilterItems().add(new
		 * FilterItemInfo("parent.id", splitBillId));
		 * view.setSelector(selector); CoreBaseCollection splitEntryColl = null;
		 * try { splitEntryColl =
		 * DepConPayPlanSplitEntryFactory.getRemoteInstance
		 * ().getCollection(view); } catch (BOSException e) {
		 * e.printStackTrace(); }
		 */
		// �������ַ�ʽ�鲻��������
		CoreBaseCollection splitEntryColl = new CoreBaseCollection();
		FDCSQLBuilder builder = new FDCSQLBuilder();
		builder
				.appendSql(" select planEntry.FID planEntryID,entry.FID entryId,entry.FLevel entryLevel,entry.FIsLeaf entryIsLeaf,entry.FIsUnsettledCon isUnsettledCon,acct.FID acctId,acct.FNumber acctNumber,acct.FLongNumber acctLongNumber,acct.FIsLeaf isleaf,acct.FName_l2 acctName, "
						+ "prj.FID prjId,prj.FName_l2 prjName,prj.FDisplayName_l2 displayName,prj.FNumber prjNumber,prj.FLongNumber prjLongNumber from T_FNC_DepConPayPlanSplitEntry entry  "
						+ "LEFT OUTER JOIN T_FNC_DepConPayPlanSplitBill head on head.FID = entry.FParentID "
						+ "LEFT OUTER JOIN T_FNC_FDCDepConPayPlanEntry planEntry on planEntry.FID = entry.FPayPlanEntryID  "
						+ "LEFT OUTER JOIN T_FDC_CostAccount acct on acct.FID = entry.FCostAccountID  "
						+ "LEFT OUTER JOIN  T_FDC_CurProject prj on prj.FID = acct.FCurProject "
						+ "where  head.FID =? " + " order by entry.FIsUnsettledCon,entry.FPayPlanEntryID,entry.flevel ");
		builder.addParam(splitBillId);
		IRowSet rowSet = null;
		try {
			rowSet = builder.executeQuery();
			while (rowSet != null && rowSet.next()) {
				// String tempAccountID = null;
				// TODO
				DepConPayPlanSplitEntryInfo splitEntry = new DepConPayPlanSplitEntryInfo();
				splitEntry.setId(BOSUuid.read(rowSet.getString("entryId")));
				splitEntry.setLevel(rowSet.getInt("entryLevel"));
				splitEntry.setIsLeaf(rowSet.getBoolean("entryIsLeaf"));
				splitEntry.setIsUnsettledCon(rowSet.getBoolean("isUnsettledCon"));
				FDCDepConPayPlanEntryInfo planEntry = new FDCDepConPayPlanEntryInfo();
				planEntry.setId(BOSUuid.read(rowSet.getString("planEntryID")));
				splitEntry.setPayPlanEntry(planEntry);
				if (rowSet.getString("acctId") != null) {
					CostAccountInfo acct = new CostAccountInfo();
					// tempAccountID = rowSet.getString("acctId");
					acct.setId(BOSUuid.read(rowSet.getString("acctId")));
					acct.setNumber(rowSet.getString("acctNumber"));
					acct.setLongNumber(rowSet.getString("acctLongNumber"));
					acct.setName(rowSet.getString("acctName"));
					acct.setIsLeaf(rowSet.getBoolean("isLeaf"));
					CurProjectInfo project = new CurProjectInfo();
					project.setId(BOSUuid.read(rowSet.getString("prjId")));
					project.setNumber(rowSet.getString("prjNumber"));
					project.setLongNumber(rowSet.getString("prjLongNumber"));
					project.setName(rowSet.getString("displayName"));
					splitEntry.setCostAccount(acct);
					acct.setCurProject(project);
				}
				splitEntryColl.add(splitEntry);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return splitEntryColl;
	}

	private List tempSplitEntryList = new ArrayList();// ��ʱ tblUnsettledCon
														// ���û���س��������Բ���������ݣ�
	// ������ʱ����tempEntrysColl�������
	private FDCDepConPayPlanEntryCollection tempEntrysColl = new FDCDepConPayPlanEntryCollection();
	private boolean flag = false;

	private void fillTable() {
		clear();
		fetchData();
		try {
			tblContract.setRefresh(false);
			tblContract.removeRows(false);
			tblUnsettledCon.setRefresh(false);
			tblUnsettledCon.removeRows(false);

			// ����ͷ
			FDCDepConPayPlanEntryCollection planEntryColl = (FDCDepConPayPlanEntryCollection) getDataMap().get("entrys");

			tempEntrysColl = planEntryColl;// ��ʱ tblUnsettledCon
			// ���û���س��������Բ���������ݣ�������ʱ����tempEntrysColl�������

			resetTableHead(planEntryColl);
			CoreBaseCollection splitEntryColl = null;
			if (this.editData != null && this.editData.getId() != null) {
				flag = true;
				splitEntryColl = (CoreBaseCollection) getSplitEntryColl(this.editData.getId().toString());
			}
			// �����ַ�¼���ڵĻ���ֱ����ֵ
			if (splitEntryColl != null && splitEntryColl.size() > 0) {
				fillSplitInfoWhenExist(splitEntryColl);
			} else {// �����ַ�¼�����ھ��������������޸ĵ���
				if (planEntryColl != null) {
					for (Iterator iterator = planEntryColl.iterator(); iterator.hasNext();) {
						FDCDepConPayPlanEntryInfo planEntry = (FDCDepConPayPlanEntryInfo) iterator.next();
						IRow row = null;
						if (planEntry.isIsUnsettledCon()) {// ��ǩ����ͬ
							tempSplitEntryList.add(planEntry);
						} else {// ��ǩ����ͬ
							row = tblContract.addRow();
							row.getStyleAttributes().setLocked(true);
							// ÿ����һ�о� new һ����¼
							DepConPayPlanSplitEntryInfo splitEntryInfo = new DepConPayPlanSplitEntryInfo();
							splitEntryInfo.setPayPlanEntry(planEntry);
							splitEntryInfo.setLevel(0);// ����Ŀǰ���ֻ���������Σ����������򵥴���
							splitEntryInfo.setIsLeaf(false);
							row.setUserObject(splitEntryInfo);
							loadRow(planEntry, row);
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			tblContract.setRefresh(true);
			tblContract.repaint();
			tblUnsettledCon.setRefresh(true);
			tblUnsettledCon.repaint();

			setBeforeAction();
		}
	}

	private List _tempSplitEntryList = new ArrayList();// ��ʱ tblUnsettledCon
	// ���û���س��������Բ���������ݣ�
	// ������ʱ����tempEntrysColl�������
	private FDCDepConPayPlanEntryCollection _tempEntrysColl = new FDCDepConPayPlanEntryCollection();

	private void fillSplitInfoWhenExist(CoreBaseCollection splitEntryColl) throws BOSException {
		// TODO ��Ҫ�жϵ����Ǵ�ǩ����ͬ������ǩ����ͬ
		for (Iterator iter1 = splitEntryColl.iterator(); iter1.hasNext();) {
			DepConPayPlanSplitEntryInfo splitEntry = (DepConPayPlanSplitEntryInfo) iter1.next();
			if (!splitEntry.isIsUnsettledCon()) {
				if (!splitEntry.isIsLeaf() && splitEntry.getLevel() == 0) {// ����Ƿ���ϸ�Ļ�
					// ��
					// ��Ҫ����Ӧ����ƻ���¼�ĺ�ͬ�����Ϣ
					IRow row = this.tblContract.addRow();
					row.getStyleAttributes().setLocked(true);
					row.setUserObject(splitEntry);
					String payPlanId = splitEntry.getPayPlanEntry().getId().toString();
					// ���ݸ�ID�ҵ�����ƻ���¼
					FDCDepConPayPlanEntryInfo planEntry = findPlanEntry(payPlanId);
					// ����ͬ�����Ϣ
					row.getCell("contractId").setValue(planEntry.getContractBillId());
					row.getCell("conNumber").setValue(planEntry.getContractNumber());
					row.getCell("conName").setValue(planEntry.getContractName());
					row.getCell("conPrice").setValue(planEntry.getContractLastestPrice());
					// row.getCell("").setValue(value)
					// �����Ŀ�����Ϣ(�ƻ�ֵ)
					DepConPayPlanSplitEntryInfo splitEntry2 = (DepConPayPlanSplitEntryInfo) row.getUserObject();
					EntityViewInfo view = new EntityViewInfo();
					SelectorItemCollection selector = new SelectorItemCollection();
					selector.add("*");
					FilterInfo filter = new FilterInfo();
					filter.getFilterItems().add(new FilterItemInfo("entry.id", splitEntry2.getId().toString()));
					view.setFilter(filter);
					view.setSelector(selector);
					CoreBaseCollection itemsColl = (CoreBaseCollection) DepConPayPlanSplitItemFactory.getRemoteInstance().getCollection(view);
					if (itemsColl == null || itemsColl.size() == 0) {
						return;
					}
					List list = new ArrayList();
					for (Iterator iter = itemsColl.iterator(); iter.hasNext();) {
						DepConPayPlanSplitItemInfo info = (DepConPayPlanSplitItemInfo) iter.next();
						list.add(info);
					}
					Collections.sort(list, FDCDepConPayPlanUtil.getSortComparator());
					for (Iterator iter = list.iterator(); iter.hasNext();) {
						DepConPayPlanSplitItemInfo item = (DepConPayPlanSplitItemInfo) iter.next();
						row.getCell(getPlanAmtKey(item.getMonth())).setValue(item.getPlanAmt());
						row.getCell(getSplitAmtKey(item.getMonth())).setValue(item.getSplitAmt());
					}
				} else if (splitEntry.isIsLeaf()) {// �п�Ŀ�Ĳ�ַ�¼��
					IRow row = this.tblContract.addRow();
					row.setUserObject(splitEntry);
					row.getCell("project.id").setValue(splitEntry.getCostAccount().getCurProject().getId());
					row.getCell("project.name").setValue(splitEntry.getCostAccount().getCurProject().getName().replace('_', '.'));
					row.getCell("costAccount.id").setValue(splitEntry.getCostAccount().getId());
					row.getCell("costAccount.number").setValue(splitEntry.getCostAccount().getLongNumber().replace('!', '.'));
					row.getCell("costAccount.name").setValue(splitEntry.getCostAccount().getName());
					// �����Ŀ�����Ϣ(�ƻ�ֵ)
					DepConPayPlanSplitEntryInfo splitEntry2 = (DepConPayPlanSplitEntryInfo) row.getUserObject();
					EntityViewInfo view = new EntityViewInfo();
					SelectorItemCollection selector = new SelectorItemCollection();
					selector.add("*");
					FilterInfo filter = new FilterInfo();
					filter.getFilterItems().add(new FilterItemInfo("entry.id", splitEntry2.getId().toString()));
					view.setFilter(filter);
					view.setSelector(selector);
					CoreBaseCollection itemsColl = (CoreBaseCollection) DepConPayPlanSplitItemFactory.getRemoteInstance().getCollection(view);
					if (itemsColl == null || itemsColl.size() == 0) {
						return;
					}
					List list = new ArrayList();
					for (Iterator iter = itemsColl.iterator(); iter.hasNext();) {
						DepConPayPlanSplitItemInfo info = (DepConPayPlanSplitItemInfo) iter.next();
						list.add(info);
					}
					Collections.sort(list, FDCDepConPayPlanUtil.getSortComparator());
					for (Iterator iter = list.iterator(); iter.hasNext();) {
						DepConPayPlanSplitItemInfo item = (DepConPayPlanSplitItemInfo) iter.next();
						row.getCell(getSplitAmtKey(item.getMonth())).setValue(item.getSplitAmt());
					}
				}
			} else if (splitEntry.isIsUnsettledCon()) {
				System.err.println("�����ȷ�");
				_tempSplitEntryList.add(splitEntry);
			}
		}
	}

	public SelectorItemCollection getSelectors() {
		SelectorItemCollection sic = super.getSelectors();
		return sic;
	}

	protected void initWorkButton() {
		// TODO Auto-generated method st ub
		super.initWorkButton();
	}

	public void onShow() throws Exception {
		// TODO Auto-generated method stub
		super.onShow();

		setOtherInfo();

		initTable();
		initOldData(this.editData);
		storeFields();
	}

	private void setOtherInfo() {
		this.pkCreateTime.setEnabled(false);
		this.prmtCreator.setEnabled(false);

		this.btnAddNew.setVisible(false);
		this.btnRemove.setVisible(false);
		this.btnEdit.setVisible(false);
		this.btnSubmit.setVisible(false);
		this.btnCopy.setVisible(false);
		this.btnPre.setVisible(false);
		this.btnFirst.setVisible(false);
		this.btnNext.setVisible(false);
		this.btnLast.setVisible(false);
		this.btnTraceDown.setVisible(false);
		this.btnTraceUp.setVisible(false);
		this.btnCreateFrom.setVisible(false);
		this.btnAttachment.setVisible(false);
		this.btnInsertLine.setVisible(false);
		this.btnAudit.setVisible(false);
		this.btnUnAudit.setVisible(false);
		this.btnAddLine.setVisible(false);
		this.btnInsertLine.setVisible(false);
		// this.btnRemoveLine.setVisible(false);
		this.btnWorkFlowG.setVisible(false);
		this.btnAuditResult.setVisible(false);
		this.btnMultiapprove.setVisible(false);
		this.btnNextPerson.setVisible(false);

		this.menuItemSubmit.setVisible(false);
		this.menuSubmitOption.setVisible(false);
		this.menuItemAddNew.setVisible(false);
		this.menuItemRemove.setVisible(false);
		this.menuItemEdit.setVisible(false);
		this.menuItemCopy.setVisible(false);
		this.menuView.setVisible(false);
		this.menuItemCreateFrom.setVisible(false);
		this.menuItemCreateTo.setVisible(false);
		this.MenuItemAttachment.setVisible(false);
		// this.menuTable1.setVisible(false);
		this.menuItemAddLine.setVisible(false);
		this.menuItemInsertLine.setVisible(false);
		this.menuItemCopyLine.setVisible(false);
		this.menuItemCopyFrom.setVisible(false);
		this.menuBiz.setVisible(false);
		this.menuWorkflow.setVisible(false);

		actionSplit.putValue(Action.SMALL_ICON, EASResource.getIcon("imgTbtn_split"));

		if (STATUS_VIEW.equals(getOprtState())) {
			this.actionSplit.setEnabled(false);
		}
	}

	public void onLoad() throws Exception {
		setOtherInfo();
		super.onLoad();
		fillTable();
		initOldData(this.editData);
		storeFields();
	}

	private void initTable() {
		this.tblContract.getSelectManager().setSelectMode(KDTSelectManager.ROW_SELECT);
		this.tblUnsettledCon.getSelectManager().setSelectMode(KDTSelectManager.ROW_SELECT);
		
		this.tblContract.getColumn("conNumber").getStyleAttributes().setLocked(true);
		this.tblContract.getColumn("conName").getStyleAttributes().setLocked(true);
		this.tblContract.getColumn("conPrice").getStyleAttributes().setLocked(true);
		this.tblContract.getColumn("project.name").getStyleAttributes().setLocked(true);
		this.tblContract.getColumn("costAccount.number").getStyleAttributes().setLocked(true);
		this.tblContract.getColumn("costAccount.name").getStyleAttributes().setLocked(true);
		this.tblContract.getColumn("conNumber").setMoveable(false);
		this.tblContract.getColumn("conName").setMoveable(false);
		this.tblContract.getColumn("conPrice").setMoveable(false);
		this.tblContract.getColumn("project.name").setMoveable(false);
		this.tblContract.getColumn("costAccount.number").setMoveable(false);
		this.tblContract.getColumn("costAccount.name").setMoveable(false);
		this.tblContract.getColumn("conNumber").setSortable(false);
		this.tblContract.getColumn("conName").setSortable(false);
		this.tblContract.getColumn("conPrice").setSortable(false);
		this.tblContract.getColumn("project.name").setSortable(false);
		this.tblContract.getColumn("costAccount.number").setSortable(false);
		this.tblContract.getColumn("costAccount.name").setSortable(false);
		FDCHelper.formatTableNumber(tblContract, "conPrice");
		FDCHelper.formatTableNumber(tblUnsettledCon, "unsettledConestPrice");

		this.tblUnsettledCon.getColumn("unsettledConNumber").getStyleAttributes().setLocked(true);
		this.tblUnsettledCon.getColumn("unsettledConName").getStyleAttributes().setLocked(true);
		this.tblUnsettledCon.getColumn("unsettledConestPrice").getStyleAttributes().setLocked(true);
		this.tblUnsettledCon.getColumn("project.name").getStyleAttributes().setLocked(true);
		this.tblUnsettledCon.getColumn("costAccount.number").getStyleAttributes().setLocked(true);
		this.tblUnsettledCon.getColumn("costAccount.name").getStyleAttributes().setLocked(true);

	}

	protected ICoreBase getBizInterface() throws Exception {
		return DepConPayPlanSplitBillFactory.getRemoteInstance();
	}

	private KDTable table = this.tblContract;

	protected KDTable getDetailTable() {
		if (this.pnlBig.getSelectedIndex() == 0) {
			table = this.tblContract;
		}
		if (this.pnlBig.getSelectedIndex() == 1) {
			table = this.tblUnsettledCon;
		}
		return table;
	}

	protected void verifyInputForSave() throws Exception {
		// TODO Auto-generated method stub

	}

	protected KDTextField getNumberCtrl() {
		// TODO Auto-generated method stub
		return null;
	}

	protected void beforeStoreFields(ActionEvent e) throws Exception {
		if (this.editData != null && this.editData.getId() != null) {
			// δ��ֵĵ��ݲ�������,���У���ڱ����ֵ�ʱ��Ϳ��Կ��Ƶõ��ˡ���Ϊֻ����ȫ��ֵĵ��ݲ����Ա���
			FDCSQLBuilder builder = new FDCSQLBuilder();
			builder.appendSql("select FState from  T_FNC_DepConPayPlanSplitBill where FID = ?  ");
			builder.addParam(this.editData.getId().toString());
			IRowSet rowSet = builder.executeQuery();
			if (rowSet != null && rowSet.next()) {
				String state = rowSet.getString("FState");
				if (state != null && FDCBillStateEnum.AUDITTED_VALUE.equals(state)) {
					MsgBox.showWarning("����������������ִ�иò�����");
					SysUtil.abort();
				}
			}
			FDCSQLBuilder _builder = new FDCSQLBuilder();
			_builder.appendSql("select FSplitPayPlanBill from  T_FNC_DepConPayPlanSplitBill where FID = ?  ");
			_builder.addParam(this.editData.getId().toString());
			IRowSet _rowSet = _builder.executeQuery();
			if (_rowSet != null && _rowSet.next()) {
				if("".equals(_rowSet.getString("FSplitPayPlanBill"))||_rowSet.getString("FSplitPayPlanBill")==null){
					MsgBox.showWarning("�õ�������ʱ�������Ѿ���ɾ�������˳��ý������½�������ٱ��棡 ");
					SysUtil.abort();
				}
			}
		}
		int rowCount = tblUnsettledCon.getRowCount();
		if (rowCount > 0) {
			for (int i = 0; i < rowCount; i++) {
				if (this.tblUnsettledCon.getCell(i, "project.name").getValue() != null && this.tblUnsettledCon.getCell(i, "costAccount.number").getValue() != null
						&& this.tblUnsettledCon.getCell(i, "costAccount.name").getValue() != null) {
					hasSplitAction = true;
				}
			}
		}
		if (STATUS_ADDNEW.equals(getOprtState())) {
			if (!hasSplitAction) {
				MsgBox.showWarning("û�н����κβ�ֶ��������������棡");
				SysUtil.abort();
			}
		}
		checkPrice();
		editData.setSplitState(CostSplitStateEnum.ALLSPLIT);// ���ò��״̬

		super.beforeStoreFields(e);
		storeRows();
	}

	protected void checkPrice() throws BOSException {
		for (int i = 0; i < tblContract.getRowCount(); i++) {
			IRow row = tblContract.getRow(i);
			DepConPayPlanSplitEntryInfo splitEntry = (DepConPayPlanSplitEntryInfo) row.getUserObject();
			if (!splitEntry.isIsLeaf()) {// ��Ҷ�ڵ�
				if (splitEntry.getId() == null) {// �����ַ�¼IDΪ��
					FDCDepConPayPlanEntryInfo planEntry = findPlanEntry(splitEntry.getPayPlanEntry().getId().toString());
					FDCDepConPayPlanItemCollection itemColls = planEntry.getItems();
					List list = new ArrayList();
					for (Iterator iter = itemColls.iterator(); iter.hasNext();) {
						FDCDepConPayPlanItemInfo info = (FDCDepConPayPlanItemInfo) iter.next();
						list.add(info);
					}
					Collections.sort(list, FDCDepConPayPlanUtil.getSortComparator());
					for (Iterator iter = list.iterator(); iter.hasNext();) {
						FDCDepConPayPlanItemInfo item = (FDCDepConPayPlanItemInfo) iter.next();
						BigDecimal planAmt = FDCHelper.toBigDecimal(row.getCell(getPlanAmtKey(item.getMonth())).getValue());
						BigDecimal splitAmt = FDCHelper.toBigDecimal(row.getCell(getSplitAmtKey(item.getMonth())).getValue());
						if (planAmt.compareTo(splitAmt) != 0) {
							MsgBox.showWarning("��ǩ����ͬ��" + (row.getRowIndex() + 1) + "��" + item.getYear() + "��" + item.getMonth() + "��" + "�Ĳ�ֽ��֮�Ͳ����ڼƻ�����������д��");
							SysUtil.abort();
						}
					}
				} else {
					EntityViewInfo view = new EntityViewInfo();
					SelectorItemCollection selector = new SelectorItemCollection();
					selector.add("*");
					FilterInfo filter = new FilterInfo();
					filter.getFilterItems().add(new FilterItemInfo("entry.id", splitEntry.getId().toString()));
					view.setFilter(filter);
					view.setSelector(selector);
					CoreBaseCollection itemsColl = null;
					itemsColl = (CoreBaseCollection) DepConPayPlanSplitItemFactory.getRemoteInstance().getCollection(view);
					if (itemsColl == null || itemsColl.size() == 0) {
						return;
					}
					List list = new ArrayList();
					for (Iterator iter = itemsColl.iterator(); iter.hasNext();) {
						DepConPayPlanSplitItemInfo info = (DepConPayPlanSplitItemInfo) iter.next();
						list.add(info);
					}
					Collections.sort(list, FDCDepConPayPlanUtil.getSortComparator());
					for (Iterator iter = list.iterator(); iter.hasNext();) {
						DepConPayPlanSplitItemInfo item = (DepConPayPlanSplitItemInfo) iter.next();
						BigDecimal planAmt = FDCHelper.toBigDecimal(row.getCell(getPlanAmtKey(item.getMonth())).getValue());
						BigDecimal splitAmt = FDCHelper.toBigDecimal(row.getCell(getSplitAmtKey(item.getMonth())).getValue());
						if (planAmt.compareTo(splitAmt) != 0) {
							MsgBox.showWarning("��ǩ����ͬ��" + (row.getRowIndex() + 1) + "��" + item.getYear() + "��" + item.getMonth() + "��" + "�Ĳ�ֽ��֮�Ͳ����ڼƻ�����������д��");
							SysUtil.abort();
						}
					}
				}
			}
		}
		for (int i = 0; i < tblUnsettledCon.getRowCount(); i++) {
			IRow row = tblUnsettledCon.getRow(i);
			DepConPayPlanSplitEntryInfo splitEntry = (DepConPayPlanSplitEntryInfo) row.getUserObject();
			String payPlanEntryId = splitEntry.getPayPlanEntry().getId().toString();

			boolean haha = false;
			if (!splitEntry.isIsLeaf()) {// ��Ҷ�ڵ�
				if (splitEntry.getId() == null) {// �����ַ�¼IDΪ��
					for (int j = 0; j < tblUnsettledCon.getRowCount(); j++) {
						IRow _row = tblUnsettledCon.getRow(j);
						DepConPayPlanSplitEntryInfo tempSplitEntry = (DepConPayPlanSplitEntryInfo) _row.getUserObject();
						String tempPlanEntryId = tempSplitEntry.getPayPlanEntry().getId().toString();
						if (_row.getRowIndex() != row.getRowIndex() && tempPlanEntryId.equals(payPlanEntryId)) {
							haha = true;
						}
					}
					if (!haha) {
						continue;
					}

					FDCDepConPayPlanEntryInfo planEntry = findPlanEntry(splitEntry.getPayPlanEntry().getId().toString());
					FDCDepConPayPlanItemCollection itemColls = planEntry.getItems();
					List list = new ArrayList();
					for (Iterator iter = itemColls.iterator(); iter.hasNext();) {
						FDCDepConPayPlanItemInfo info = (FDCDepConPayPlanItemInfo) iter.next();
						list.add(info);
					}
					Collections.sort(list, FDCDepConPayPlanUtil.getSortComparator());
					for (Iterator iter = list.iterator(); iter.hasNext();) {
						FDCDepConPayPlanItemInfo item = (FDCDepConPayPlanItemInfo) iter.next();
						BigDecimal planAmt = FDCHelper.toBigDecimal(row.getCell(getPlanAmtKey(item.getMonth())).getValue());
						BigDecimal splitAmt = FDCHelper.toBigDecimal(row.getCell(getSplitAmtKey(item.getMonth())).getValue());
						if (planAmt.compareTo(splitAmt) != 0) {
							MsgBox.showWarning("��ǩ����ͬ��" + (row.getRowIndex() + 1) + "��" + item.getYear() + "��" + item.getMonth() + "��" + "�Ĳ�ֽ��֮�Ͳ����ڼƻ�����������д��");
							SysUtil.abort();
						}
					}
				} else {
					EntityViewInfo view = new EntityViewInfo();
					SelectorItemCollection selector = new SelectorItemCollection();
					selector.add("*");
					FilterInfo filter = new FilterInfo();
					filter.getFilterItems().add(new FilterItemInfo("entry.id", splitEntry.getId().toString()));
					view.setFilter(filter);
					view.setSelector(selector);
					CoreBaseCollection itemsColl = null;
					itemsColl = (CoreBaseCollection) DepConPayPlanSplitItemFactory.getRemoteInstance().getCollection(view);
					if (itemsColl == null || itemsColl.size() == 0) {
						return;
					}
					List list = new ArrayList();
					for (Iterator iter = itemsColl.iterator(); iter.hasNext();) {
						DepConPayPlanSplitItemInfo info = (DepConPayPlanSplitItemInfo) iter.next();
						list.add(info);
					}
					Collections.sort(list, FDCDepConPayPlanUtil.getSortComparator());
					for (Iterator iter = list.iterator(); iter.hasNext();) {
						DepConPayPlanSplitItemInfo item = (DepConPayPlanSplitItemInfo) iter.next();
						BigDecimal planAmt = FDCHelper.toBigDecimal(row.getCell(getPlanAmtKey(item.getMonth())).getValue());
						BigDecimal splitAmt = FDCHelper.toBigDecimal(row.getCell(getSplitAmtKey(item.getMonth())).getValue());
						if (planAmt.compareTo(splitAmt) != 0) {
							MsgBox.showWarning("��ǩ����ͬ��" + (row.getRowIndex() + 1) + "��" + item.getYear() + "��" + item.getMonth() + "��" + "�Ĳ�ֽ��֮�Ͳ����ڼƻ�����������д��");
							SysUtil.abort();
						}
					}
				}
			}
		}
	}

	private void storeRows() {

		DepConPayPlanSplitBillInfo splitBill = this.editData;
		// �Ƚ����еķ�¼��գ�Ȼ���ȫ��ɨ�轫ÿһ�е�ֵ����ɷ�¼
		splitBill.getEntrys().clear();

		for (int i = 0; i < tblContract.getRowCount(); i++) {
			IRow row = tblContract.getRow(i);
			DepConPayPlanSplitEntryInfo entry = (DepConPayPlanSplitEntryInfo) storeContractRow(row);
			if (entry != null) {
				splitBill.getEntrys().add(entry);
			}
		}

		for (int i = 0; i < tblUnsettledCon.getRowCount(); i++) {
			IRow row = tblUnsettledCon.getRow(i);
			DepConPayPlanSplitEntryInfo entry = (DepConPayPlanSplitEntryInfo) storeUnsettedConRow(row);
			if (entry != null) {
				splitBill.getEntrys().add(entry);
			}
		}
		isModified = false;
	}

	private DepConPayPlanSplitEntryInfo storeUnsettedConRow(IRow row) {

		FDCDepConPayPlanEntryInfo planEntry = null;// ����ƻ���¼
		DepConPayPlanSplitEntryInfo splitEntry = null;// ����ƻ���ַ�¼
		FDCDepConPayPlanItemCollection payPlanItemColl = null;// ����ƻ������Ŀ

		if (row.getUserObject() != null && row.getUserObject() instanceof DepConPayPlanSplitEntryInfo) {

			splitEntry = (DepConPayPlanSplitEntryInfo) row.getUserObject();
			planEntry = findPlanEntry(splitEntry.getPayPlanEntry().getId().toString());

			payPlanItemColl = planEntry.getItems();

			splitEntry.setCostBillId(planEntry.getParent() != null ? planEntry.getParent().getId() : null);
			// �Ƚ���¼�ϵ���Ŀȫ����գ�Ȼ������ɨ���ٱ���
			splitEntry.getItems().clear();
			splitEntry.setParent(this.editData);
			splitEntry.setPayPlanEntry(planEntry);
			splitEntry.setIsUnsettledCon(true);
			// �ɱ���Ŀ
			CostAccountInfo costAccount = new CostAccountInfo();
			if (row.getCell("costAccount.id").getValue() != null) {
				costAccount.setId(BOSUuid.read(row.getCell("costAccount.id").getValue().toString()));
				costAccount.setName(row.getCell("costAccount.name").getValue().toString());
				costAccount.setNumber(row.getCell("costAccount.number").getValue().toString());
				splitEntry.setCostAccount(costAccount);
			}

			// ��Ŀ
			for (Iterator iter = payPlanItemColl.iterator(); iter.hasNext();) {
				FDCDepConPayPlanItemInfo tempItem = (FDCDepConPayPlanItemInfo) iter.next();

				DepConPayPlanSplitItemInfo item = new DepConPayPlanSplitItemInfo();
				item.setEntry(splitEntry);

				item.setMonth(tempItem.getMonth());
				item.setYear(tempItem.getYear());
				item.setPlanAmt((BigDecimal) row.getCell(getPlanAmtKey(tempItem.getMonth())).getValue());// �ƻ����
				item.setSplitAmt((BigDecimal) row.getCell(getSplitAmtKey(tempItem.getMonth())).getValue());// ��ֽ��

				splitEntry.getItems().add(item);
			}
		}
		return splitEntry;
	}

	private DepConPayPlanSplitEntryInfo storeContractRow(IRow row) {

		FDCDepConPayPlanEntryInfo planEntry = null;// ����ƻ���¼
		DepConPayPlanSplitEntryInfo splitEntry = null;// ����ƻ���ַ�¼
		FDCDepConPayPlanItemCollection payPlanItemColl = null;// ����ƻ������Ŀ

		if (row.getUserObject() != null && row.getUserObject() instanceof DepConPayPlanSplitEntryInfo) {

			splitEntry = (DepConPayPlanSplitEntryInfo) row.getUserObject();
			planEntry = findPlanEntry(splitEntry.getPayPlanEntry().getId().toString());

			payPlanItemColl = planEntry.getItems();

			splitEntry.setCostBillId(planEntry.getParent() != null ? planEntry.getParent().getId() : null);
			// �Ƚ���¼�ϵ���Ŀȫ����գ�Ȼ������ɨ���ٱ���
			splitEntry.getItems().clear();
			splitEntry.setParent(this.editData);
			splitEntry.setPayPlanEntry(planEntry);
			splitEntry.setIsUnsettledCon(false);
			// �ɱ���Ŀ
			CostAccountInfo costAccount = new CostAccountInfo();
			if (row.getCell("costAccount.id").getValue() != null) {
				costAccount.setId(BOSUuid.read(row.getCell("costAccount.id").getValue().toString()));
				costAccount.setName(row.getCell("costAccount.name").getValue().toString());
				costAccount.setNumber(row.getCell("costAccount.number").getValue().toString());
				splitEntry.setCostAccount(costAccount);// TODO �ɱ���ĿID����Ϊ��ô������
			}

			// ��Ŀ
			for (Iterator iter = payPlanItemColl.iterator(); iter.hasNext();) {
				FDCDepConPayPlanItemInfo tempItem = (FDCDepConPayPlanItemInfo) iter.next();

				DepConPayPlanSplitItemInfo item = new DepConPayPlanSplitItemInfo();
				item.setEntry(splitEntry);

				item.setMonth(tempItem.getMonth());
				item.setYear(tempItem.getYear());
				item.setPlanAmt((BigDecimal) row.getCell(getPlanAmtKey(tempItem.getMonth())).getValue());// �ƻ����
				item.setSplitAmt((BigDecimal) row.getCell(getSplitAmtKey(tempItem.getMonth())).getValue());// ��ֽ��

				splitEntry.getItems().add(item);
			}
		}
		return splitEntry;
	}

	protected IObjectValue createNewData() {
		DepConPayPlanSplitBillInfo objectValue = new DepConPayPlanSplitBillInfo();
		objectValue.setCreator((com.kingdee.eas.common.client.SysContext.getSysContext().getCurrentUserInfo()));
		objectValue.setCreateTime(new Timestamp(serverDate.getTime()));
		objectValue.setIsInvalid(false);
		Object o = getUIContext().get("payPlanID");
		if (o != null) {
			try {
				CoreBaseInfo payPlanBill = FDCDepConPayPlanBillFactory.getRemoteInstance().getValue(new ObjectUuidPK(BOSUuid.read(o.toString())));
				objectValue.setSplitPayPlanBill((FDCDepConPayPlanBillInfo) payPlanBill);
			} catch (EASBizException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (BOSException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (UuidException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		return objectValue;
	}

	protected IObjectValue createNewDetailData(KDTable table) {
		// TODO Auto-generated method stub
		return super.createNewDetailData(table);
	}

	// �����ͬ��ֵĿ�Ŀ��Ϣ
	private void importCostAccountOfConSplit(IRow row) {
		DepConPayPlanSplitEntryInfo splitEntry = (DepConPayPlanSplitEntryInfo) row.getUserObject();

		Map costAccountMap = (HashMap) getDataMap().get("costAccount");
		String entryId = splitEntry.getPayPlanEntry().getId().toString();
		String contractId = row.getCell("contractId").getValue() != null ? row.getCell("contractId").getValue().toString() : null;
		if (contractId == null) {
			return;
		}
		String key = entryId + "_" + contractId;
		ContractCostSplitEntryCollection conSplitBillColl = (ContractCostSplitEntryCollection) costAccountMap.get(key);
		for (Iterator iter = conSplitBillColl.iterator(); iter.hasNext();) {
			ContractCostSplitEntryInfo _entry = (ContractCostSplitEntryInfo) iter.next();
			IRow _row = this.tblContract.addRow(row.getRowIndex() + 1);

			DepConPayPlanSplitEntryInfo splitEntryInfo = new DepConPayPlanSplitEntryInfo();
			splitEntryInfo.setPayPlanEntry(splitEntry.getPayPlanEntry());
			splitEntryInfo.setLevel(1);
			splitEntryInfo.setIsLeaf(true);
			_row.setUserObject(splitEntryInfo);
			// _row.setUserObject(row.getUserObject());

			_row.getCell("project.id").setValue(_entry.getCostAccount().getCurProject().getId());
			_row.getCell("project.name").setValue(_entry.getCostAccount().getCurProject().getName().replace('_', '.'));
			_row.getCell("costAccount.id").setValue(_entry.getCostAccount().getId());
			_row.getCell("costAccount.number").setValue(_entry.getCostAccount().getLongNumber().replace('!', '.'));
			_row.getCell("costAccount.name").setValue(_entry.getCostAccount().getName());
		}
		fillItems(row);
	}
}