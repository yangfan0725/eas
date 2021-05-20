package com.kingdee.eas.fdc.finance.client;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import javax.swing.event.ChangeEvent;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.extendcontrols.BizDataFormat;
import com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox;
import com.kingdee.bos.ctrl.kdf.table.ICell;
import com.kingdee.bos.ctrl.kdf.table.IColumn;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTDefaultCellEditor;
import com.kingdee.bos.ctrl.kdf.table.KDTMergeManager;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent;
import com.kingdee.bos.ctrl.kdf.table.util.KDTableUtil;
import com.kingdee.bos.ctrl.kdf.util.editor.ICellEditor;
import com.kingdee.bos.ctrl.kdf.util.render.ObjectValueRender;
import com.kingdee.bos.ctrl.kdf.util.style.Styles.HorizontalAlignment;
import com.kingdee.bos.ctrl.swing.KDTextField;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.basedata.master.account.AccountViewCollection;
import com.kingdee.eas.basedata.master.account.AccountViewInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basedata.CostAccountCollection;
import com.kingdee.eas.fdc.basedata.CostAccountInfo;
import com.kingdee.eas.fdc.basedata.CostSplitStateEnum;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.client.CostSplitAcctUI;
import com.kingdee.eas.fdc.basedata.client.FDCClientHelper;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.basedata.client.NoCostSplitAcctUI;
import com.kingdee.eas.fdc.basedata.util.KDDetailedArea;
import com.kingdee.eas.fdc.contract.ConNoCostSplitCollection;
import com.kingdee.eas.fdc.contract.ConNoCostSplitEntryCollection;
import com.kingdee.eas.fdc.contract.ConNoCostSplitEntryInfo;
import com.kingdee.eas.fdc.contract.ConNoCostSplitFactory;
import com.kingdee.eas.fdc.contract.ConNoCostSplitInfo;
import com.kingdee.eas.fdc.contract.ContractBillInfo;
import com.kingdee.eas.fdc.contract.ContractCostSplitCollection;
import com.kingdee.eas.fdc.contract.ContractCostSplitEntryCollection;
import com.kingdee.eas.fdc.contract.ContractCostSplitEntryInfo;
import com.kingdee.eas.fdc.contract.ContractCostSplitFactory;
import com.kingdee.eas.fdc.contract.ContractCostSplitInfo;
import com.kingdee.eas.fdc.finance.FDCProDepConPayContractEntryCollection;
import com.kingdee.eas.fdc.finance.FDCProDepConPayContractEntryInfo;
import com.kingdee.eas.fdc.finance.FDCProDepConPayPlanContractCollection;
import com.kingdee.eas.fdc.finance.FDCProDepConPayPlanContractInfo;
import com.kingdee.eas.fdc.finance.FDCProDepConPayPlanFactory;
import com.kingdee.eas.fdc.finance.FDCProDepConPayPlanInfo;
import com.kingdee.eas.fdc.finance.FDCProDepConPayPlanNoContractCollection;
import com.kingdee.eas.fdc.finance.FDCProDepConPayPlanNoContractEntryCollection;
import com.kingdee.eas.fdc.finance.FDCProDepConPayPlanNoContractEntryInfo;
import com.kingdee.eas.fdc.finance.FDCProDepConPayPlanNoContractInfo;
import com.kingdee.eas.fdc.finance.FDCProDepConPayPlanUnsetEntryCollection;
import com.kingdee.eas.fdc.finance.FDCProDepConPayPlanUnsetEntryInfo;
import com.kingdee.eas.fdc.finance.FDCProDepConPayPlanUnsettledCollection;
import com.kingdee.eas.fdc.finance.FDCProDepConPayPlanUnsettledInfo;
import com.kingdee.eas.fdc.finance.FDCProDepSplitFactory;
import com.kingdee.eas.fdc.finance.FDCProDepSplitHasConEntryCollection;
import com.kingdee.eas.fdc.finance.FDCProDepSplitHasConEntryInfo;
import com.kingdee.eas.fdc.finance.FDCProDepSplitHasConInfo;
import com.kingdee.eas.fdc.finance.FDCProDepSplitInfo;
import com.kingdee.eas.fdc.finance.FDCProDepSplitNoConEntryCollection;
import com.kingdee.eas.fdc.finance.FDCProDepSplitNoConEntryInfo;
import com.kingdee.eas.fdc.finance.FDCProDepSplitNoConInfo;
import com.kingdee.eas.fdc.finance.FDCProDepSplitUnConEntryCollection;
import com.kingdee.eas.fdc.finance.FDCProDepSplitUnConEntryInfo;
import com.kingdee.eas.fdc.finance.FDCProDepSplitUnConInfo;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.framework.TreeBaseInfo;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.MsgBox;

public class FDCProDepSplitEditUI extends AbstractFDCProDepSplitEditUI {

	// ��̬����ʼ���
	private static int START_CON = 10;
	private static int START_UNC = 10;
	private static int START_NOC = 9;

	// ��Ŀѡ��UI
	private IUIWindow acctUI = null;

	public FDCProDepSplitEditUI() throws Exception {
		super();
	}

	public void loadFields() {
		super.loadFields();
		clearTable();
		initTableColumn();
		// �������ʱ����¼�ӹ����ƻ�ȡ��
		if (STATUS_ADDNEW.equals(getOprtState())) {
			// �����ƻ�
			String planID = (String) getUIContext().get("planID");
			FDCProDepConPayPlanInfo plan = null;
			try {
				plan = FDCProDepConPayPlanFactory.getRemoteInstance()
						.getFDCProDepConPayPlanInfo(new ObjectUuidPK(planID),
								getPlanSic());
			} catch (EASBizException e) {
				handUIException(e);
			} catch (BOSException e) {
				handUIException(e);
			}
			fillPlan(plan);
		}
		// �ӵ�ǰ���ݱ���ķ�¼ȡ��
		else {
			fillTable();
		}
		initAccountFormat(null);
	}

	/**
	 * �����������״̬<br>
	 * 1��ɾ��������<br>
	 * 2��ɾ���Զ�����<br>
	 * ��ǩ���ʹ�ǩ���ֱ���
	 */
	protected void clearTable() {
		tblHas.checkParsed();
		tblUn.checkParsed();
		tblNo.checkParsed();
		tblHas.removeRows();
		for (int i = tblHas.getColumnCount() - 1; i >= START_CON; i--) {
			tblHas.removeColumn(i);
		}
		tblUn.removeRows();
		for (int i = tblUn.getColumnCount() - 1; i >= START_UNC; i--) {
			tblUn.removeColumn(i);
		}
		tblNo.removeRows();
		for (int i = tblNo.getColumnCount() - 1; i >= START_NOC; i--) {
			tblNo.removeColumn(i);
		}
	}

	/**
	 * ������ͷ<br>
	 * ѭ�����ڣ�ÿ��������ǩ����ͬ���4�У���ǩ����ͬ���2��
	 * 
	 * @param year
	 *            ��
	 * @param month
	 *            ��
	 * @param cycle
	 *            ����
	 */
	protected void initTableColumn() {
		FDCProDepConPayPlanInfo plan = editData.getFdcProDep();
		if (plan != null) {
			int year = plan.getYear();
			int month = plan.getMonth();
			int cycle = plan.getPayPlanCycle().getCycle().getValue();

			// ��ǩ����ͷ���ں�
			IRow hHead0 = tblHas.getHeadRow(0);
			IRow hHead1 = tblHas.getHeadRow(1);
			KDTMergeManager hMeg = tblHas.getHeadMergeManager();
			// ��ǩ����ͷ���ں�
			IRow uHead0 = tblUn.getHeadRow(0);
			IRow uHead1 = tblUn.getHeadRow(1);
			KDTMergeManager uMeg = tblUn.getHeadMergeManager();
			// �޺�ͬ��ͷ���ں�
			IRow nHead0 = tblNo.getHeadRow(0);
			IRow nHead1 = tblNo.getHeadRow(1);
			KDTMergeManager nMeg = tblNo.getHeadMergeManager();
			for (int i = 0; i < cycle; i++) {
				if (month > 12) {
					year += 1;
					month = 1;
				}
				String monthStr;
				if (month < 10) {
					monthStr = year + "��0" + month + "��";
				} else {
					monthStr = year + "��" + month + "��";
				}
				String KeyHead = "MONTH" + year + "" + month;

				// ��ǩ����ͬҳǩ����У�ÿ��3�У������ں�
				IColumn col = tblHas.addColumn();
				int index = col.getColumnIndex();
				col.setKey(KeyHead + "id");
				hHead0.getCell(index).setValue(monthStr);
				hHead1.getCell(index).setValue("id");
				col.getStyleAttributes().setHided(true);
				col.setRequired(true);

				col = tblHas.addColumn();
				index = col.getColumnIndex();
				col.setKey(KeyHead + "plan");
				hHead1.getCell(index).setValue("�ƻ����");
				col.getStyleAttributes().setNumberFormat("#,##0.00");
				col.setEditor(getCellEditor("amount"));
				col.getStyleAttributes().setLocked(true);
				col.getStyleAttributes().setHorizontalAlign(
						HorizontalAlignment.RIGHT);

				col = tblHas.addColumn();
				index = col.getColumnIndex();
				col.setKey(KeyHead + "spt");
				hHead1.getCell(index).setValue("��ֽ��");
				col.getStyleAttributes().setNumberFormat("#,##0.00");
				col.setEditor(getCellEditor("amount"));
				col.setRequired(true);
				col.getStyleAttributes().setHorizontalAlign(
						HorizontalAlignment.RIGHT);

				hMeg.mergeBlock(0, START_CON + (i * 3), 0, START_CON + (i * 3)
						+ 2);

				// ��ǩ����ͬҳǩ����У�ÿ��3�У������ں�
				col = tblUn.addColumn();
				index = col.getColumnIndex();
				col.setKey(KeyHead + "id");
				uHead0.getCell(index).setValue(monthStr);
				uHead1.getCell(index).setValue("id");
				col.getStyleAttributes().setHided(true);
				col.setRequired(true);

				col = tblUn.addColumn();
				index = col.getColumnIndex();
				col.setKey(KeyHead + "plan");
				uHead1.getCell(index).setValue("�ƻ����");
				col.getStyleAttributes().setNumberFormat("#,##0.00");
				col.setEditor(getCellEditor("amount"));
				col.getStyleAttributes().setLocked(true);
				col.getStyleAttributes().setHorizontalAlign(
						HorizontalAlignment.RIGHT);

				col = tblUn.addColumn();
				index = col.getColumnIndex();
				col.setKey(KeyHead + "spt");
				uHead1.getCell(index).setValue("��ֽ��");
				col.getStyleAttributes().setNumberFormat("#,##0.00");
				col.setEditor(getCellEditor("amount"));
				col.setRequired(true);
				col.getStyleAttributes().setHorizontalAlign(
						HorizontalAlignment.RIGHT);

				uMeg.mergeBlock(0, START_UNC + (i * 3), 0, START_UNC + (i * 3)
						+ 2);

				// �޺�ͬҳǩ����У�ÿ��3�У������ں�
				col = tblNo.addColumn();
				index = col.getColumnIndex();
				col.setKey(KeyHead + "id");
				nHead0.getCell(index).setValue(monthStr);
				nHead1.getCell(index).setValue("id");
				col.getStyleAttributes().setHided(true);
				col.setRequired(true);

				col = tblNo.addColumn();
				index = col.getColumnIndex();
				col.setKey(KeyHead + "plan");
				nHead1.getCell(index).setValue("�ƻ����");
				col.getStyleAttributes().setNumberFormat("#,##0.00");
				col.setEditor(getCellEditor("amount"));
				col.getStyleAttributes().setLocked(true);
				col.getStyleAttributes().setHorizontalAlign(
						HorizontalAlignment.RIGHT);

				col = tblNo.addColumn();
				index = col.getColumnIndex();
				col.setKey(KeyHead + "spt");
				nHead1.getCell(index).setValue("��ֽ��");
				col.getStyleAttributes().setNumberFormat("#,##0.00");
				col.setEditor(getCellEditor("amount"));
				col.setRequired(true);
				col.getStyleAttributes().setHorizontalAlign(
						HorizontalAlignment.RIGHT);

				nMeg.mergeBlock(0, START_NOC + (i * 3), 0, START_NOC + (i * 3)
						+ 2);

				month++;
			}
		}
	}

	/**
	 * �����ѱ���ļƻ���ַ�¼�������
	 */
	protected void fillTable() {
		Color lockColor = new Color(0xE8E8E3);
		// ���غ�ͬ��ַ�¼
		for (int i = 0; i < editData.getHasCons().size(); i++) {
			FDCProDepSplitHasConInfo hasCon = editData.getHasCons().get(i);
			IRow row = tblHas.addRow();
			row.getCell("id").setValue(hasCon.getId().toString());
			row.getCell("planID").setValue(hasCon.getFdcProDepEntry());
			if (hasCon.isIsSplitRow()) {
				row.getCell("isSplitRow").setValue(Boolean.TRUE);
				row.getCell("curProject").setValue(hasCon.getProject());
				if (hasCon.isIsSplitCost()) {
					row.getCell("accountNum").setValue(hasCon.getCostAccount());
					row.getCell("accountName").setValue(
							hasCon.getCostAccount().getName());
					row.getCell("isSplitCost").setValue(Boolean.TRUE);
				} else {
					row.getCell("accountNum").setValue(hasCon.getAccountView());
					row.getCell("accountName").setValue(
							hasCon.getAccountView().getName());
					row.getCell("isSplitCost").setValue(Boolean.FALSE);
				}
				FDCProDepSplitHasConEntryCollection details = hasCon
						.getDetails();
				for (int j = 0; j < details.size(); j++) {
					FDCProDepSplitHasConEntryInfo detail = details.get(j);
					Calendar cal = Calendar.getInstance();
					cal.setTime(detail.getMonth());
					String KeyHead = "MONTH" + cal.get(Calendar.YEAR) + ""
							+ (cal.get(Calendar.MONTH) + 1);
					row.getCell(KeyHead + "id").setValue(
							detail.getId().toString());
					row.getCell(KeyHead + "id").setUserObject(
							detail.getPlanDetails());
					row.getCell(KeyHead + "spt").setValue(detail.getSptPay());
				}
			} else {
				row.getStyleAttributes().setLocked(true);
				row.getStyleAttributes().setBackground(lockColor);
				row.getCell("isSplitRow").setValue(Boolean.FALSE);
				row.getCell("conNum").setValue(hasCon.getContract());
				row.getCell("conName").setValue(hasCon.getContractName());
				row.getCell("conAmount").setValue(hasCon.getContractPrice());
				FDCProDepSplitHasConEntryCollection details = hasCon
						.getDetails();
				for (int j = 0; j < details.size(); j++) {
					FDCProDepSplitHasConEntryInfo detail = details.get(j);
					Calendar cal = Calendar.getInstance();
					cal.setTime(detail.getMonth());
					String KeyHead = "MONTH" + cal.get(Calendar.YEAR) + ""
							+ (cal.get(Calendar.MONTH) + 1);
					row.getCell(KeyHead + "id").setValue(
							detail.getId().toString());
					row.getCell(KeyHead + "id").setUserObject(
							detail.getPlanDetails());
					row.getCell(KeyHead + "plan").setValue(detail.getSptPay());
				}
			}
		}
		// ���ش�ǩ����ͬ��ַ�¼
		for (int i = 0; i < editData.getUnCons().size(); i++) {
			FDCProDepSplitUnConInfo unCon = editData.getUnCons().get(i);
			IRow row = tblUn.addRow();
			row.getCell("id").setValue(unCon.getId().toString());
			row.getCell("planID").setValue(unCon.getFdcProDepEntry());
			if (unCon.isIsSplitRow()) {
				row.getCell("isSplitRow").setValue(Boolean.TRUE);
				row.getCell("curProject").setValue(unCon.getProject());
				if (unCon.isIsSplitCost()) {
					row.getCell("accountNum").setValue(unCon.getCostAccount());
					row.getCell("accountName").setValue(
							unCon.getCostAccount().getName());
					row.getCell("isSplitCost").setValue(Boolean.TRUE);
				} else {
					row.getCell("accountNum").setValue(unCon.getAccountView());
					row.getCell("accountName").setValue(
							unCon.getAccountView().getName());
					row.getCell("isSplitCost").setValue(Boolean.FALSE);
				}
				FDCProDepSplitUnConEntryCollection details = unCon.getDetails();
				for (int j = 0; j < details.size(); j++) {
					FDCProDepSplitUnConEntryInfo detail = details.get(j);
					Calendar cal = Calendar.getInstance();
					cal.setTime(detail.getMonth());
					String KeyHead = "MONTH" + cal.get(Calendar.YEAR) + ""
							+ (cal.get(Calendar.MONTH) + 1);
					row.getCell(KeyHead + "id").setValue(
							detail.getId().toString());
					row.getCell(KeyHead + "id").setUserObject(
							detail.getPlanDetails());
					row.getCell(KeyHead + "spt").setValue(detail.getSptPay());
				}
			} else {
				row.getStyleAttributes().setLocked(true);
				row.getStyleAttributes().setBackground(lockColor);
				row.getCell("isSplitRow").setValue(Boolean.FALSE);
				row.getCell("conNum").setValue(unCon.getUnConNumber());
				row.getCell("conName").setValue(unCon.getUnConName());
				row.getCell("conAmount").setValue(unCon.getPlanAmount());
				FDCProDepSplitUnConEntryCollection details = unCon.getDetails();
				for (int j = 0; j < details.size(); j++) {
					FDCProDepSplitUnConEntryInfo detail = details.get(j);
					Calendar cal = Calendar.getInstance();
					cal.setTime(detail.getMonth());
					String KeyHead = "MONTH" + cal.get(Calendar.YEAR) + ""
							+ (cal.get(Calendar.MONTH) + 1);
					row.getCell(KeyHead + "id").setValue(
							detail.getId().toString());
					row.getCell(KeyHead + "id").setUserObject(
							detail.getPlanDetails());
					row.getCell(KeyHead + "plan").setValue(detail.getSptPay());
				}
			}
		}
		// �����޺�ͬ��ַ�¼
		for (int i = 0; i < editData.getNoCons().size(); i++) {
			FDCProDepSplitNoConInfo unCon = editData.getNoCons().get(i);
			IRow row = tblNo.addRow();
			row.getCell("id").setValue(unCon.getId().toString());
			row.getCell("planID").setValue(unCon.getFdcProDepEntry());
			if (unCon.isIsSplitRow()) {
				row.getCell("isSplitRow").setValue(Boolean.TRUE);
				row.getCell("curProject").setValue(unCon.getProject());
				if (unCon.isIsSplitCost()) {
					row.getCell("accountNum").setValue(unCon.getCostAccount());
					row.getCell("accountName").setValue(
							unCon.getCostAccount().getName());
					row.getCell("isSplitCost").setValue(Boolean.TRUE);
				} else {
					row.getCell("accountNum").setValue(unCon.getAccountView());
					row.getCell("accountName").setValue(
							unCon.getAccountView().getName());
					row.getCell("isSplitCost").setValue(Boolean.FALSE);
				}
				FDCProDepSplitNoConEntryCollection details = unCon.getDetails();
				for (int j = 0; j < details.size(); j++) {
					FDCProDepSplitNoConEntryInfo detail = details.get(j);
					Calendar cal = Calendar.getInstance();
					cal.setTime(detail.getMonth());
					String KeyHead = "MONTH" + cal.get(Calendar.YEAR) + ""
							+ (cal.get(Calendar.MONTH) + 1);
					row.getCell(KeyHead + "id").setValue(
							detail.getId().toString());
					row.getCell(KeyHead + "id").setUserObject(
							detail.getPlanDetails());
					row.getCell(KeyHead + "spt").setValue(detail.getSptPay());
				}
			} else {
				row.getStyleAttributes().setLocked(true);
				row.getStyleAttributes().setBackground(lockColor);
				row.getCell("isSplitRow").setValue(Boolean.FALSE);
				row.getCell("payMatters").setValue(unCon.getPayMatters());
				row.getCell("payMattersName").setValue(
						unCon.getPayMattersName());
				FDCProDepSplitNoConEntryCollection details = unCon.getDetails();
				for (int j = 0; j < details.size(); j++) {
					FDCProDepSplitNoConEntryInfo detail = details.get(j);
					Calendar cal = Calendar.getInstance();
					cal.setTime(detail.getMonth());
					String KeyHead = "MONTH" + cal.get(Calendar.YEAR) + ""
							+ (cal.get(Calendar.MONTH) + 1);
					row.getCell(KeyHead + "id").setValue(
							detail.getId().toString());
					row.getCell(KeyHead + "id").setUserObject(
							detail.getPlanDetails());
					row.getCell(KeyHead + "plan").setValue(detail.getSptPay());
				}
			}
		}
		setUnionData();
	}

	/**
	 * ͳ�ƺϼ�ֵ
	 */
	protected void setUnionData() {
		// ��ͬ���
		int hasCount = tblHas.getRowCount();
		for (int i = 0; i < hasCount; i++) {
			IRow totalRow = tblHas.getRow(i);
			Boolean isSplitRow = (Boolean) totalRow.getCell("isSplitRow")
					.getValue();
			if (!isSplitRow.booleanValue()) {
				for (int k = START_CON + 2; k < tblHas.getColumnCount(); k += 3) {
					totalRow.getCell(k).setValue(null);
				}
				for (int j = i + 1; j < hasCount; j++) {
					IRow splitRow = tblHas.getRow(j);
					isSplitRow = (Boolean) splitRow.getCell("isSplitRow")
							.getValue();
					if (!isSplitRow.booleanValue()) {
						break;
					} else {
						for (int k = START_CON + 2; k < tblHas.getColumnCount(); k += 3) {
							BigDecimal sum = (BigDecimal) totalRow.getCell(k)
									.getValue();
							sum = sum == null ? FDCHelper.ZERO : sum;
							BigDecimal cur = (BigDecimal) splitRow.getCell(k)
									.getValue();
							cur = cur == null ? FDCHelper.ZERO : cur;
							sum = sum.add(cur);
							totalRow.getCell(k).setValue(sum);
						}
					}
				}
			}
		}
		// ��ǩ����ͬ���
		int unCount = tblUn.getRowCount();
		for (int i = 0; i < unCount; i++) {
			IRow totalRow = tblUn.getRow(i);
			Boolean isSplitRow = (Boolean) totalRow.getCell("isSplitRow")
					.getValue();
			if (!isSplitRow.booleanValue()) {
				for (int k = START_UNC + 2; k < tblHas.getColumnCount(); k += 3) {
					totalRow.getCell(k).setValue(null);
				}
				for (int j = i + 1; j < unCount; j++) {
					IRow splitRow = tblUn.getRow(j);
					isSplitRow = (Boolean) splitRow.getCell("isSplitRow")
							.getValue();
					if (!isSplitRow.booleanValue()) {
						break;
					} else {
						for (int k = START_UNC + 2; k < tblUn.getColumnCount(); k += 3) {
							BigDecimal sum = (BigDecimal) totalRow.getCell(k)
									.getValue();
							sum = sum == null ? FDCHelper.ZERO : sum;
							BigDecimal cur = (BigDecimal) splitRow.getCell(k)
									.getValue();
							cur = cur == null ? FDCHelper.ZERO : cur;
							sum = sum.add(cur);
							totalRow.getCell(k).setValue(sum);
						}
					}
				}
			}
		}
		// �޺�ͬ���
		int noCount = tblNo.getRowCount();
		for (int i = 0; i < noCount; i++) {
			IRow totalRow = tblNo.getRow(i);
			Boolean isSplitRow = (Boolean) totalRow.getCell("isSplitRow")
					.getValue();
			if (!isSplitRow.booleanValue()) {
				for (int k = START_NOC + 2; k < tblHas.getColumnCount(); k += 3) {
					totalRow.getCell(k).setValue(null);
				}
				for (int j = i + 1; j < noCount; j++) {
					IRow splitRow = tblNo.getRow(j);
					isSplitRow = (Boolean) splitRow.getCell("isSplitRow")
							.getValue();
					if (!isSplitRow.booleanValue()) {
						break;
					} else {
						for (int k = START_NOC + 2; k < tblNo.getColumnCount(); k += 3) {
							BigDecimal sum = (BigDecimal) totalRow.getCell(k)
									.getValue();
							sum = sum == null ? FDCHelper.ZERO : sum;
							BigDecimal cur = (BigDecimal) splitRow.getCell(k)
									.getValue();
							cur = cur == null ? FDCHelper.ZERO : cur;
							sum = sum.add(cur);
							totalRow.getCell(k).setValue(sum);
						}
					}
				}
			}
		}
	}

	public void storeFields() {
		super.storeFields();
		editData.getHasCons().clear();
		editData.getUnCons().clear();
		editData.getNoCons().clear();
		// store��ǩ��ͬ
		for (int i = 0; i < tblHas.getRowCount(); i++) {
			IRow row = tblHas.getRow(i);
			FDCProDepSplitHasConInfo sptEntry = new FDCProDepSplitHasConInfo();
			sptEntry.setHead(editData);
			String id = (String) row.getCell("id").getValue();
			if (id != null) {
				sptEntry.setId(BOSUuid.read(id));
			}
			Boolean isSplitRow = (Boolean) row.getCell("isSplitRow").getValue();
			// ��ͬ�У������������Ϣ
			if (!isSplitRow.booleanValue()) {
				FDCProDepConPayPlanContractInfo plan = (FDCProDepConPayPlanContractInfo) row
						.getCell("planID").getValue();
				sptEntry.setFdcProDepEntry(plan);
				sptEntry.setIsSplitRow(false);
				ContractBillInfo con = (ContractBillInfo) row.getCell("conNum")
						.getValue();
				sptEntry.setContract(con);
				String conName = (String) row.getCell("conName").getValue();
				sptEntry.setContractName(conName);
				BigDecimal conPrice = (BigDecimal) row.getCell("conAmount")
						.getValue();
				sptEntry.setContractPrice(conPrice);

				for (int j = START_CON; j < tblHas.getColumnCount(); j += 3) {
					FDCProDepSplitHasConEntryInfo detail = new FDCProDepSplitHasConEntryInfo();
					String dtlID = (String) row.getCell(j).getValue();
					if (dtlID != null) {
						detail.setId(BOSUuid.read(dtlID));
					}
					FDCProDepConPayContractEntryInfo conEntry = (FDCProDepConPayContractEntryInfo) row
							.getCell(j).getUserObject();
					detail.setPlanDetails(conEntry);
					BigDecimal sptPay = (BigDecimal) row.getCell(j + 1)
							.getValue();
					sptPay = sptPay == null ? FDCHelper.ZERO : sptPay;
					detail.setSptPay(sptPay);

					String key = tblHas.getColumn(j).getKey();
					String year = key.substring(5, 9);
					String month = key.substring(9, key.length() - 2);
					Calendar cal = Calendar.getInstance();
					cal.set(Calendar.YEAR, new Integer(year).intValue());
					cal.set(Calendar.MONTH, new Integer(month).intValue() - 1);
					cal.set(Calendar.DAY_OF_MONTH, 15);
					detail.setMonth(cal.getTime());

					sptEntry.getDetails().add(detail);
				}
			}
			// ����У������ֺ�ͬ����Ϣ
			else {
				FDCProDepConPayPlanContractInfo plan = (FDCProDepConPayPlanContractInfo) row
						.getCell("planID").getValue();
				sptEntry.setFdcProDepEntry(plan);
				CurProjectInfo project = (CurProjectInfo) row.getCell(
						"curProject").getValue();
				sptEntry.setProject(project);
				sptEntry.setIsSplitRow(true);
				Object acc = row.getCell("accountNum").getValue();
				if (acc instanceof CostAccountInfo) {
					sptEntry.setCostAccount((CostAccountInfo) acc);
					sptEntry.setIsSplitCost(true);
				} else if (acc instanceof AccountViewInfo) {
					sptEntry.setAccountView((AccountViewInfo) acc);
					sptEntry.setIsSplitCost(false);
				}
				for (int j = START_CON; j < tblHas.getColumnCount(); j += 3) {
					FDCProDepSplitHasConEntryInfo detail = new FDCProDepSplitHasConEntryInfo();
					String dtlID = (String) row.getCell(j).getValue();
					if (dtlID != null) {
						detail.setId(BOSUuid.read(dtlID));
					}
					FDCProDepConPayContractEntryInfo conEntry = (FDCProDepConPayContractEntryInfo) row
							.getCell(j).getUserObject();
					detail.setPlanDetails(conEntry);
					BigDecimal sptPay = (BigDecimal) row.getCell(j + 2)
							.getValue();
					sptPay = sptPay == null ? FDCHelper.ZERO : sptPay;
					detail.setSptPay(sptPay);

					String key = tblHas.getColumn(j).getKey();
					String year = key.substring(5, 9);
					String month = key.substring(9, key.length() - 2);
					Calendar cal = Calendar.getInstance();
					cal.set(Calendar.YEAR, new Integer(year).intValue());
					cal.set(Calendar.MONTH, new Integer(month).intValue() - 1);
					cal.set(Calendar.DAY_OF_MONTH, 15);
					detail.setMonth(cal.getTime());

					sptEntry.getDetails().add(detail);
				}
			}
			editData.getHasCons().add(sptEntry);
		}
		// store��ǩ��ͬ
		for (int i = 0; i < tblUn.getRowCount(); i++) {
			IRow row = tblUn.getRow(i);
			FDCProDepSplitUnConInfo sptEntry = new FDCProDepSplitUnConInfo();
			sptEntry.setHead(editData);
			String id = (String) row.getCell("id").getValue();
			if (id != null) {
				sptEntry.setId(BOSUuid.read(id));
			}
			Boolean isSplitRow = (Boolean) row.getCell("isSplitRow").getValue();
			// ��ͬ�У������������Ϣ
			if (!isSplitRow.booleanValue()) {
				FDCProDepConPayPlanUnsettledInfo plan = (FDCProDepConPayPlanUnsettledInfo) row
						.getCell("planID").getValue();
				sptEntry.setFdcProDepEntry(plan);
				sptEntry.setIsSplitRow(false);
				String con = (String) row.getCell("conNum").getValue();
				sptEntry.setUnConNumber(con);
				String conName = (String) row.getCell("conName").getValue();
				sptEntry.setUnConName(conName);
				BigDecimal planAmount = (BigDecimal) row.getCell("conAmount")
						.getValue();
				sptEntry.setPlanAmount(planAmount);
				for (int j = START_UNC; j < tblUn.getColumnCount(); j += 3) {
					FDCProDepSplitUnConEntryInfo detail = new FDCProDepSplitUnConEntryInfo();
					String dtlID = (String) row.getCell(j).getValue();
					if (dtlID != null) {
						detail.setId(BOSUuid.read(dtlID));
					}
					FDCProDepConPayPlanUnsetEntryInfo conEntry = (FDCProDepConPayPlanUnsetEntryInfo) row
							.getCell(j).getUserObject();
					detail.setPlanDetails(conEntry);
					BigDecimal sptPay = (BigDecimal) row.getCell(j + 1)
							.getValue();
					sptPay = sptPay == null ? FDCHelper.ZERO : sptPay;
					detail.setSptPay(sptPay);

					String key = tblUn.getColumn(j).getKey();
					String year = key.substring(5, 9);
					String month = key.substring(9, key.length() - 2);
					Calendar cal = Calendar.getInstance();
					cal.set(Calendar.YEAR, new Integer(year).intValue());
					cal.set(Calendar.MONTH, new Integer(month).intValue() - 1);
					cal.set(Calendar.DAY_OF_MONTH, 15);
					detail.setMonth(cal.getTime());

					sptEntry.getDetails().add(detail);
				}
			}
			// ����У������ֺ�ͬ����Ϣ
			else {
				FDCProDepConPayPlanUnsettledInfo plan = (FDCProDepConPayPlanUnsettledInfo) row
						.getCell("planID").getValue();
				sptEntry.setFdcProDepEntry(plan);
				CurProjectInfo project = (CurProjectInfo) row.getCell(
						"curProject").getValue();
				sptEntry.setProject(project);
				Object acc = row.getCell("accountNum").getValue();
				sptEntry.setIsSplitRow(true);
				if (acc instanceof CostAccountInfo) {
					sptEntry.setCostAccount((CostAccountInfo) acc);
					sptEntry.setIsSplitCost(true);
				} else if (acc instanceof AccountViewInfo) {
					sptEntry.setAccountView((AccountViewInfo) acc);
					sptEntry.setIsSplitCost(false);
				}
				for (int j = START_UNC; j < tblUn.getColumnCount(); j += 3) {
					FDCProDepSplitUnConEntryInfo detail = new FDCProDepSplitUnConEntryInfo();
					String dtlID = (String) row.getCell(j).getValue();
					if (dtlID != null) {
						detail.setId(BOSUuid.read(dtlID));
					}
					FDCProDepConPayPlanUnsetEntryInfo conEntry = (FDCProDepConPayPlanUnsetEntryInfo) row
							.getCell(j).getUserObject();
					detail.setPlanDetails(conEntry);
					BigDecimal sptPay = (BigDecimal) row.getCell(j + 2)
							.getValue();
					sptPay = sptPay == null ? FDCHelper.ZERO : sptPay;
					detail.setSptPay(sptPay);

					String key = tblUn.getColumn(j).getKey();
					String year = key.substring(5, 9);
					String month = key.substring(9, key.length() - 2);
					Calendar cal = Calendar.getInstance();
					cal.set(Calendar.YEAR, new Integer(year).intValue());
					cal.set(Calendar.MONTH, new Integer(month).intValue() - 1);
					cal.set(Calendar.DAY_OF_MONTH, 15);
					detail.setMonth(cal.getTime());

					sptEntry.getDetails().add(detail);
				}
			}
			editData.getUnCons().add(sptEntry);
		}

		// store�޺�ͬ
		for (int i = 0; i < tblNo.getRowCount(); i++) {
			IRow row = tblNo.getRow(i);
			FDCProDepSplitNoConInfo sptEntry = new FDCProDepSplitNoConInfo();
			sptEntry.setHead(editData);
			String id = (String) row.getCell("id").getValue();
			if (id != null) {
				sptEntry.setId(BOSUuid.read(id));
			}
			Boolean isSplitRow = (Boolean) row.getCell("isSplitRow").getValue();
			// ��ͬ�У������������Ϣ
			if (!isSplitRow.booleanValue()) {
				FDCProDepConPayPlanNoContractInfo plan = (FDCProDepConPayPlanNoContractInfo) row
						.getCell("planID").getValue();
				sptEntry.setFdcProDepEntry(plan);
				sptEntry.setIsSplitRow(false);
				String payMatters = (String) row.getCell("payMatters")
						.getValue();
				sptEntry.setPayMatters(payMatters);
				String payMattersName = (String) row.getCell("payMattersName")
						.getValue();
				sptEntry.setPayMattersName(payMattersName);
				for (int j = START_NOC; j < tblNo.getColumnCount(); j += 3) {
					FDCProDepSplitNoConEntryInfo detail = new FDCProDepSplitNoConEntryInfo();
					String dtlID = (String) row.getCell(j).getValue();
					if (dtlID != null) {
						detail.setId(BOSUuid.read(dtlID));
					}
					FDCProDepConPayPlanNoContractEntryInfo conEntry = (FDCProDepConPayPlanNoContractEntryInfo) row
							.getCell(j).getUserObject();
					detail.setPlanDetails(conEntry);
					BigDecimal sptPay = (BigDecimal) row.getCell(j + 1)
							.getValue();
					sptPay = sptPay == null ? FDCHelper.ZERO : sptPay;
					detail.setSptPay(sptPay);

					String key = tblNo.getColumn(j).getKey();
					String year = key.substring(5, 9);
					String month = key.substring(9, key.length() - 2);
					Calendar cal = Calendar.getInstance();
					cal.set(Calendar.YEAR, new Integer(year).intValue());
					cal.set(Calendar.MONTH, new Integer(month).intValue() - 1);
					cal.set(Calendar.DAY_OF_MONTH, 15);
					detail.setMonth(cal.getTime());

					sptEntry.getDetails().add(detail);
				}
			}
			// ����У������ֺ�ͬ����Ϣ
			else {
				sptEntry.setIsSplitRow(true);
				FDCProDepConPayPlanNoContractInfo plan = (FDCProDepConPayPlanNoContractInfo) row
						.getCell("planID").getValue();
				sptEntry.setFdcProDepEntry(plan);
				CurProjectInfo project = (CurProjectInfo) row.getCell(
						"curProject").getValue();
				sptEntry.setProject(project);
				Object acc = row.getCell("accountNum").getValue();
				if (acc instanceof CostAccountInfo) {
					sptEntry.setCostAccount((CostAccountInfo) acc);
					sptEntry.setIsSplitCost(true);
				} else if (acc instanceof AccountViewInfo) {
					sptEntry.setAccountView((AccountViewInfo) acc);
					sptEntry.setIsSplitCost(false);
				}
				for (int j = START_NOC; j < tblNo.getColumnCount(); j += 3) {
					FDCProDepSplitNoConEntryInfo detail = new FDCProDepSplitNoConEntryInfo();
					String dtlID = (String) row.getCell(j).getValue();
					if (dtlID != null) {
						detail.setId(BOSUuid.read(dtlID));
					}
					FDCProDepConPayPlanNoContractEntryInfo conEntry = (FDCProDepConPayPlanNoContractEntryInfo) row
							.getCell(j).getUserObject();
					detail.setPlanDetails(conEntry);
					BigDecimal sptPay = (BigDecimal) row.getCell(j + 2)
							.getValue();
					sptPay = sptPay == null ? FDCHelper.ZERO : sptPay;
					detail.setSptPay(sptPay);

					String key = tblNo.getColumn(j).getKey();
					String year = key.substring(5, 9);
					String month = key.substring(9, key.length() - 2);
					Calendar cal = Calendar.getInstance();
					cal.set(Calendar.YEAR, new Integer(year).intValue());
					cal.set(Calendar.MONTH, new Integer(month).intValue() - 1);
					cal.set(Calendar.DAY_OF_MONTH, 15);
					detail.setMonth(cal.getTime());

					sptEntry.getDetails().add(detail);
				}
			}
			editData.getNoCons().add(sptEntry);
		}
	}

	public boolean isModify() {
		return false;
	}

	public void onLoad() throws Exception {
		super.onLoad();
		initUI();
	}

	protected void initUI() {
		btnSptAccountView.setIcon(EASResource.getIcon("imgTbtn_edittotier"));

		ObjectValueRender orNum = new ObjectValueRender();
		orNum.setFormat(new BizDataFormat("$number$"));
		ObjectValueRender orName = new ObjectValueRender();
		orNum.setFormat(new BizDataFormat("$name$"));
		ObjectValueRender orLongNum = new ObjectValueRender();
		orLongNum.setFormat(new BizDataFormat("$codingNumber$"));

		tblHas.getColumn("conNum").setRenderer(orNum);
		tblHas.getColumn("conNum").setEditor(getCellEditor("contract"));
		tblHas.getColumn("curProject").setRenderer(orName);
		tblHas.getColumn("curProject").setEditor(getCellEditor("project"));
		tblUn.getColumn("curProject").setRenderer(orName);
		tblUn.getColumn("curProject").setEditor(getCellEditor("project"));
		tblNo.getColumn("curProject").setRenderer(orName);
		tblNo.getColumn("curProject").setEditor(getCellEditor("project"));
	}

	/**
	 * ���ÿ�Ŀչ��<br>
	 * ��Ϊͬһ����Ԫ������ǳɱ���Ŀ���ƿ�Ŀ����Ҫ��������
	 * 
	 * @param row
	 *            Ϊ��ʱ��������3��ҳǩ�ĸ�ʽ����������ָ���и�ʽ
	 */
	protected void initAccountFormat(IRow row) {
		ObjectValueRender orNum = new ObjectValueRender();
		orNum.setFormat(new BizDataFormat("$number$"));
		ObjectValueRender orLongNum = new ObjectValueRender();
		orLongNum.setFormat(new BizDataFormat("$codingNumber$"));

		if (row != null) {
			ICell cell = row.getCell("accountNum");
			if (cell == null) {
				return;
			}
			if (cell.getValue() instanceof CostAccountInfo) {
				cell.setRenderer(orLongNum);
				cell.setEditor(getCellEditor("costAccount"));
			} else if (cell.getValue() instanceof AccountViewInfo) {
				cell.setRenderer(orNum);
				cell.setEditor(getCellEditor("accountView"));
			}
		} else {
			for (int i = 0; i < tblHas.getRowCount(); i++) {
				ICell cell = tblHas.getCell(i, "accountNum");
				if (cell.getValue() instanceof CostAccountInfo) {
					cell.setRenderer(orLongNum);
					cell.setEditor(getCellEditor("costAccount"));
				} else if (cell.getValue() instanceof AccountViewInfo) {
					cell.setRenderer(orNum);
					cell.setEditor(getCellEditor("accountView"));
				}
			}
			for (int i = 0; i < tblUn.getRowCount(); i++) {
				ICell cell = tblUn.getCell(i, "accountNum");
				if (cell.getValue() instanceof CostAccountInfo) {
					cell.setRenderer(orLongNum);
					cell.setEditor(getCellEditor("costAccount"));
				} else if (cell.getValue() instanceof AccountViewInfo) {
					cell.setRenderer(orNum);
					cell.setEditor(getCellEditor("accountView"));
				}
			}
			for (int i = 0; i < tblNo.getRowCount(); i++) {
				ICell cell = tblNo.getCell(i, "accountNum");
				if (cell.getValue() instanceof CostAccountInfo) {
					cell.setRenderer(orLongNum);
					cell.setEditor(getCellEditor("costAccount"));
				} else if (cell.getValue() instanceof AccountViewInfo) {
					cell.setRenderer(orNum);
					cell.setEditor(getCellEditor("accountView"));
				}
			}
		}
	}

	/**
	 * ����onShow�������ô˷���������һ����������򣬶��˵��ݲ���Ҫ���򣬹�����
	 */
	protected void initListener() {
		// super.initListener();
	}

	public void actionEdit_actionPerformed(ActionEvent e) throws Exception {
		super.actionEdit_actionPerformed(e);
		tpCon_stateChanged(null);
	}

	public void actionDelLine_actionPerformed(ActionEvent e) throws Exception {
		if (tpCon.getSelectedIndex() == 0) {
			MsgBox.showWarning(this, "ֻ��ɾ����ǩ����ͬ�Ĳ����!");
		} else {
			int actRowIdx = getDetailTable().getSelectManager()
					.getActiveRowIndex();
			if (actRowIdx < 0) {
				FDCMsgBox.showWarning("����ѡ����!");
				SysUtil.abort();
			}
			IRow row = getDetailTable().getRow(actRowIdx);
			Boolean isSptRow = (Boolean) row.getCell("isSplitRow").getValue();
			if (isSptRow != null && !isSptRow.booleanValue()) {
				MsgBox.showWarning("����ɾ���ƻ���!");
				SysUtil.abort();
			}
			getDetailTable().removeRow(actRowIdx);
		}
	}

	public void actionSptCostAccount_actionPerformed(ActionEvent e)
			throws Exception {
		checkCanSpt(true);

		UIContext uiContext = new UIContext(this);
		uiContext.put("curProject", editData.getCurProject());
		CostAccountCollection accts = null;
		// ѡ���Ŀ
		acctUI = UIFactory.createUIFactory(UIFactoryName.MODEL).create(
				com.kingdee.eas.fdc.basedata.client.CostSplitAcctUI.class
						.getName(), uiContext, null, null);
		acctUI.show();

		// У���Ƿ���ϸ������Ŀ�µĳɱ���Ŀ
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

		// ȡ�õ�ǰ�Ѳ�ֳɱ���Ŀ�����ظ�ѡ��ʱ����
		int actRowIdx = getDetailTable().getSelectManager().getActiveRowIndex();
		IRow curRow = getDetailTable().getRow(actRowIdx);
		IObjectValue plan = (IObjectValue) curRow.getCell("planID").getValue();
		Set had = new HashSet();
		for (int j = actRowIdx + 1; j < getDetailTable().getRowCount(); j++) {
			IRow checkRow = getDetailTable().getRow(j);
			Boolean isSptRow = (Boolean) checkRow.getCell("isSplitRow")
					.getValue();
			if (!isSptRow.booleanValue()) {
				break;
			} else {
				CurProjectInfo chkPrj = (CurProjectInfo) checkRow.getCell(
						"curProject").getValue();
				TreeBaseInfo chkAcc = (TreeBaseInfo) checkRow.getCell(
						"accountNum").getValue();
				if (chkPrj != null && chkAcc != null) {
					had.add(chkPrj.getId().toString()
							+ chkAcc.getId().toString());
				}
			}
		}

		for (int i = accts.size() - 1; i < accts.size(); i--) {
			if (i < 0) {
				break;
			}
			CostAccountInfo costAccount = (CostAccountInfo) accts.get(i);
			if (costAccount != null) {
				CurProjectInfo prj = costAccount.getCurProject();
				if (!had.contains(prj.getId().toString()
						+ costAccount.getId().toString())) {
					IRow newRow = getDetailTable().addRow(actRowIdx + 1);
					newRow.getCell("planID").setValue(plan);
					newRow.getCell("isSplitRow").setValue(Boolean.TRUE);
					newRow.getCell("isSplitCost").setValue(Boolean.TRUE);
					newRow.getCell("curProject").setValue(prj);
					newRow.getCell("accountNum").setValue(costAccount);
					newRow.getCell("accountName").setValue(
							costAccount.getName());
					initAccountFormat(newRow);
				}
			}
		}
	}

	public void actionSptAccountView_actionPerformed(ActionEvent e)
			throws Exception {
		checkCanSpt(false);

		UIContext uiContext = new UIContext(this);
		uiContext.put("curProject", editData.getCurProject());
		acctUI = UIFactory.createUIFactory(UIFactoryName.MODEL).create(
				com.kingdee.eas.fdc.basedata.client.NoCostSplitAcctUI.class
						.getName(), uiContext, null, null);
		acctUI.show();
		IUIWindow uiWin = acctUI;
		AccountViewCollection accountViewCollection = null;
		CurProjectInfo curProjectInfo = null;
		if (((NoCostSplitAcctUI) uiWin.getUIObject()).isOk()) {
			HashMap map = ((NoCostSplitAcctUI) uiWin.getUIObject()).getData();
			accountViewCollection = (AccountViewCollection) map
					.get("accountViewCollection");
			curProjectInfo = (CurProjectInfo) map.get("curProject");
			// У���Ƿ���ϸ������Ŀ�µĳɱ���Ŀ
			if (curProjectInfo == null || !curProjectInfo.isIsLeaf()) {
				MsgBox.showWarning("����ѡ����ϸ������Ŀ��");
				SysUtil.abort();
			}
		} else {
			return;
		}

		// ȡ�õ�ǰ�Ѳ�ֻ�ƿ�Ŀ�����ظ�ѡ��ʱ����
		int actRowIdx = getDetailTable().getSelectManager().getActiveRowIndex();
		IRow curRow = getDetailTable().getRow(actRowIdx);
		IObjectValue plan = (IObjectValue) curRow.getCell("planID").getValue();
		Set had = new HashSet();
		for (int j = actRowIdx + 1; j < getDetailTable().getRowCount(); j++) {
			IRow checkRow = getDetailTable().getRow(j);
			Boolean isSptRow = (Boolean) checkRow.getCell("isSplitRow")
					.getValue();
			if (!isSptRow.booleanValue()) {
				break;
			} else {
				CurProjectInfo chkPrj = (CurProjectInfo) checkRow.getCell(
						"curProject").getValue();
				TreeBaseInfo chkAcc = (TreeBaseInfo) checkRow.getCell(
						"accountNum").getValue();
				if (chkPrj != null && chkAcc != null) {
					had.add(chkPrj.getId().toString()
							+ chkAcc.getId().toString());
				}
			}
		}

		for (int i = accountViewCollection.size() - 1; i < accountViewCollection
				.size(); i--) {
			if (i < 0) {
				break;
			}
			AccountViewInfo accountView = (AccountViewInfo) accountViewCollection
					.get(i);
			if (accountView != null
					&& !had.contains(curProjectInfo.getId().toString()
							+ accountView.getId().toString())) {
				IRow newRow = getDetailTable().addRow(actRowIdx + 1);
				newRow.getCell("planID").setValue(plan);
				newRow.getCell("isSplitRow").setValue(Boolean.TRUE);
				newRow.getCell("isSplitCost").setValue(Boolean.TRUE);
				newRow.getCell("curProject").setValue(curProjectInfo);
				newRow.getCell("accountNum").setValue(accountView);
				newRow.getCell("accountName").setValue(accountView.getName());
				initAccountFormat(newRow);
			}
		}
	}

	/**
	 * ����Ƿ�ɲ��
	 */
	protected void checkCanSpt(boolean isSptCost) {
		IRow row = KDTableUtil.getSelectedRow(getDetailTable());
		if (row == null) {
			FDCMsgBox.showWarning(this, "����ѡ���У�");
			SysUtil.abort();
		} else {
			// ��ѡ�ж�Ӧ�ƻ����ܲ��
			Boolean isSptRow = (Boolean) row.getCell("isSplitRow").getValue();
			if (isSptRow.booleanValue()) {
				FDCMsgBox.showWarning(this, "��ѡ�мƻ��н��в�֣�");
				SysUtil.abort();
			} else {
				int actRowIdx = getDetailTable().getSelectManager()
						.getActiveRowIndex();
				IRow curRow = getDetailTable().getRow(actRowIdx);
				IObjectValue plan = (IObjectValue) curRow.getCell("planID")
						.getValue();
				// ��鵱ǰ�Ƿ��Ѳ��
				for (int j = actRowIdx + 1; j < getDetailTable().getRowCount(); j++) {
					IRow checkRow = getDetailTable().getRow(j);
					isSptRow = (Boolean) checkRow.getCell("isSplitRow")
							.getValue();
					if (!isSptRow.booleanValue()) {
						break;
					} else {
						// ����Ѳ���ɱ����������ɱ�����ʾ���Ѳ�����Ҳһ��
						Object chkAcc = checkRow.getCell("accountNum")
								.getValue();
						if (isSptCost ? chkAcc instanceof CostAccountInfo
								: chkAcc instanceof AccountViewInfo) {
							break;
						}
						// �����ǰ��Ĳ�һ��������ʾ�Ƿ����ԭ����
						else if (FDCMsgBox.OK == FDCMsgBox.showConfirm2(this,
								"����ͬʱ��ֵ��ɱ���Ŀ�ͻ�ƿ�Ŀ�����ȷ���������ԭ�в�֣�")) {
							// ȷ����ɾ�������Ѳ�ֵ�
							for (int k = getDetailTable().getRowCount() - 1; k > actRowIdx; k--) {
								IRow delRow = getDetailTable().getRow(k);
								IObjectValue delPlan = (IObjectValue) delRow
										.getCell("planID").getValue();
								if (delPlan.getString("id").equals(
										plan.getString("id"))) {
									getDetailTable().removeRow(k);
								}
							}
							break;
						}
						// ȡ������ֹ
						else {
							abort();
						}
					}
				}
			}
		}
	}

	protected void tpCon_stateChanged(ChangeEvent e) throws Exception {
		int index = tpCon.getSelectedIndex();
		if ((index == 1 || index == 2)
				&& (STATUS_ADDNEW.equals(getOprtState()) || STATUS_EDIT
						.equals(getOprtState()))) {
			actionSptCostAccount.setEnabled(true);
			actionSptAccountView.setEnabled(true);
			actionDelLine.setEnabled(true);
		} else {
			actionSptCostAccount.setEnabled(false);
			actionSptAccountView.setEnabled(false);
			actionDelLine.setEnabled(false);
		}
	}

	/**
	 * �����ֺϼ�
	 */
	protected void tblHas_editStopped(KDTEditEvent e) throws Exception {
		setUnionData();
	}

	/**
	 * �����ֺϼ�
	 */
	protected void tblUn_editStopped(KDTEditEvent e) throws Exception {
		setUnionData();
	}

	/**
	 * �����ֺϼ�
	 */
	protected void tblNo_editStopped(KDTEditEvent e) throws Exception {
		setUnionData();
	}

	protected void attachListeners() {
	}

	protected void detachListeners() {
	}

	protected ICoreBase getBizInterface() throws Exception {
		return FDCProDepSplitFactory.getRemoteInstance();
	}

	protected KDTable getDetailTable() {
		if (tpCon.getSelectedIndex() == 0) {
			return tblHas;
		} else if (tpCon.getSelectedIndex() == 1) {
			return tblUn;
		} else if (tpCon.getSelectedIndex() == 2) {
			return tblNo;
		}
		return null;
	}

	protected KDTextField getNumberCtrl() {
		// TODO Auto-generated method stub
		return null;
	}

	protected void verifyInput(ActionEvent e) throws Exception {
		// У����ǩ����ͬ
		for (int i = START_CON; i < tblHas.getColumnCount(); i += 3) {
			// ��ǰ��ͬ��
			String conName = null;
			// ��ǰ�·�
			String month = (String) tblHas.getHeadRow(0).getCell(i).getValue();
			// �ƻ����
			BigDecimal sumAmt = FDCHelper.ZERO;
			// ��ֽ��ϼ�
			BigDecimal sptAmt = FDCHelper.ZERO;
			// ��ǰУ��ļƻ�
			String planID = "";
			// �Ƚϴ�С
			int sub = 0;
			for (int j = 0; j < tblHas.getRowCount(); j++) {
				IRow row = tblHas.getRow(j);
				Boolean isSplitRow = (Boolean) row.getCell("isSplitRow")
						.getValue();
				IObjectValue plan = (IObjectValue) row.getCell("planID")
						.getValue();
				String curPlanID = plan.getString("id");
				// �Ƿ���һ���ƻ�У��ͨ�����ҿ�ʼУ����һ���ƻ�
				boolean isNewRow = sub == 0 && !planID.equals(curPlanID);
				// �����ǰ�еļƻ�����һ�мƻ���ͬ�����ۼӲ��
				if (planID.equals(curPlanID) || isNewRow) {
					if (isNewRow) {
						// �¼ƻ�Ҫ��ʼ���ϼ�ֵ
						sumAmt = FDCHelper.ZERO;
						sptAmt = FDCHelper.ZERO;
					}
					// �ǲ�������ۼӲ��
					if (isSplitRow.booleanValue()) {
						BigDecimal curSpt = (BigDecimal) row.getCell(i + 2)
								.getValue();
						curSpt = curSpt == null ? FDCHelper.ZERO : curSpt;
						sptAmt = sptAmt.add(curSpt);
					}
					// �����Ǽƻ�ֵ
					else {
						conName = (String) row.getCell("conName").getValue();
						planID = ((IObjectValue) row.getCell("planID")
								.getValue()).getString("id");
						sumAmt = (BigDecimal) row.getCell(i + 1).getValue();
						sumAmt = sumAmt == null ? FDCHelper.ZERO : sumAmt;
					}
					sub = sumAmt.compareTo(sptAmt);
					if (j == tblHas.getRowCount() - 1 && sub != 0) {
						FDCMsgBox.showWarning(this, "��ǩ����ͬ����ƻ� ��" + conName
								+ "�� " + month + " �ļƻ�������ֽ����������Ƿ���ȫ��֡�");
						abort();
					}
				} else {
					FDCMsgBox.showWarning(this, "��ǩ����ͬ����ƻ� ��" + conName + "�� "
							+ month + " �ļƻ�������ֽ����������Ƿ���ȫ��֡�");
					abort();
				}
			}
		}
		// У���ǩ����ͬ
		for (int i = START_UNC; i < tblUn.getColumnCount(); i += 3) {
			// ��ǰ��ͬ��
			String conName = null;
			// ��ǰ�·�
			String month = (String) tblUn.getHeadRow(0).getCell(i).getValue();
			// �ƻ����
			BigDecimal sumAmt = FDCHelper.ZERO;
			// ��ֽ��ϼ�
			BigDecimal sptAmt = FDCHelper.ZERO;
			// ��ǰУ��ļƻ�
			String planID = "";
			// �Ƚϴ�С
			int sub = 0;
			for (int j = 0; j < tblUn.getRowCount(); j++) {
				IRow row = tblUn.getRow(j);
				Boolean isSplitRow = (Boolean) row.getCell("isSplitRow")
						.getValue();
				IObjectValue plan = (IObjectValue) row.getCell("planID")
						.getValue();
				String curPlanID = plan.getString("id");
				// �Ƿ���һ���ƻ�У��ͨ�����ҿ�ʼУ����һ���ƻ�
				boolean isNewRow = sub == 0 && !planID.equals(curPlanID);
				// �����ǰ�еļƻ�����һ�мƻ���ͬ�����ۼӲ��
				if (planID.equals(curPlanID) || isNewRow) {
					if (isNewRow) {
						// �¼ƻ�Ҫ��ʼ���ϼ�ֵ
						sumAmt = FDCHelper.ZERO;
						sptAmt = FDCHelper.ZERO;
					}
					// �ǲ�������ۼӲ��
					if (isSplitRow.booleanValue()) {
						BigDecimal curSpt = (BigDecimal) row.getCell(i + 2)
								.getValue();
						curSpt = curSpt == null ? FDCHelper.ZERO : curSpt;
						sptAmt = sptAmt.add(curSpt);
					}
					// �����Ǽƻ�ֵ
					else {
						conName = (String) row.getCell("conName").getValue();
						planID = ((IObjectValue) row.getCell("planID")
								.getValue()).getString("id");
						sumAmt = (BigDecimal) row.getCell(i + 1).getValue();
						sumAmt = sumAmt == null ? FDCHelper.ZERO : sumAmt;
					}
					sub = sumAmt.compareTo(sptAmt);
					if (j == tblUn.getRowCount() - 1 && sub != 0) {
						FDCMsgBox.showWarning(this, "��ǩ����ͬ����ƻ� ��" + conName
								+ "�� " + month + " �ļƻ�������ֽ����������Ƿ���ȫ��֡�");
						abort();
					}
				} else {
					FDCMsgBox.showWarning(this, "��ǩ����ͬ����ƻ� ��" + conName + "�� "
							+ month + " �ļƻ�������ֽ����������Ƿ���ȫ��֡�");
					abort();
				}
			}
		}
		// У���޺�ͬ
		for (int i = START_NOC; i < tblNo.getColumnCount(); i += 3) {
			// ��ǰ��ͬ��
			String conName = null;
			// ��ǰ�·�
			String month = (String) tblNo.getHeadRow(0).getCell(i).getValue();
			// �ƻ����
			BigDecimal sumAmt = FDCHelper.ZERO;
			// ��ֽ��ϼ�
			BigDecimal sptAmt = FDCHelper.ZERO;
			// ��ǰУ��ļƻ�
			String planID = "";
			// �Ƚϴ�С
			int sub = 0;
			for (int j = 0; j < tblNo.getRowCount(); j++) {
				IRow row = tblNo.getRow(j);
				Boolean isSplitRow = (Boolean) row.getCell("isSplitRow")
						.getValue();
				IObjectValue plan = (IObjectValue) row.getCell("planID")
						.getValue();
				String curPlanID = plan.getString("id");
				// �Ƿ���һ���ƻ�У��ͨ�����ҿ�ʼУ����һ���ƻ�
				boolean isNewRow = sub == 0 && !planID.equals(curPlanID);
				// �����ǰ�еļƻ�����һ�мƻ���ͬ�����ۼӲ��
				if (planID.equals(curPlanID) || isNewRow) {
					if (isNewRow) {
						// �¼ƻ�Ҫ��ʼ���ϼ�ֵ
						sumAmt = FDCHelper.ZERO;
						sptAmt = FDCHelper.ZERO;
					}
					// �ǲ�������ۼӲ��
					if (isSplitRow.booleanValue()) {
						BigDecimal curSpt = (BigDecimal) row.getCell(i + 2)
								.getValue();
						curSpt = curSpt == null ? FDCHelper.ZERO : curSpt;
						sptAmt = sptAmt.add(curSpt);
					}
					// �����Ǽƻ�ֵ
					else {
						conName = (String) row.getCell("payMattersName")
								.getValue();
						planID = ((IObjectValue) row.getCell("planID")
								.getValue()).getString("id");
						sumAmt = (BigDecimal) row.getCell(i + 1).getValue();
						sumAmt = sumAmt == null ? FDCHelper.ZERO : sumAmt;
					}
					sub = sumAmt.compareTo(sptAmt);
					if (j == tblNo.getRowCount() - 1 && sub != 0) {
						FDCMsgBox.showWarning(this, "�޺�ͬ����ƻ� ��" + conName
								+ "�� " + month + " �ļƻ�������ֽ����������Ƿ���ȫ��֡�");
						abort();
					}
				} else {
					FDCMsgBox.showWarning(this, "�޺�ͬ����ƻ� ��" + conName + "�� "
							+ month + " �ļƻ�������ֽ����������Ƿ���ȫ��֡�");
					abort();
				}
			}
		}
	}

	/**
	 * ����ʱ����ֹ����ƻ�<br>
	 * * ȡһ�������ƻ���¼<br>
	 * * ���Ϊ��ǩ����ͬ������뵽ҳǩһ<br>
	 * * * ������ͬ������ݽ��в��<br>
	 * * ���Ϊ��ǩ����ͬ���޺�ͬ������뵽ҳǩ������<br>
	 * 4����ͬ����userObjectΪ0���������userObjectΪ1
	 * 
	 * @param plan
	 */
	protected IObjectValue createNewData() {
		FDCProDepSplitInfo objectValue = new FDCProDepSplitInfo();
		objectValue.setSplitUser(SysContext.getSysContext()
				.getCurrentUserInfo());

		String planID = (String) getUIContext().get("planID");
		if (!FDCHelper.isEmpty(planID)) {
			try {
				FDCProDepConPayPlanInfo plan = FDCProDepConPayPlanFactory
						.getRemoteInstance().getFDCProDepConPayPlanInfo(
								new ObjectUuidPK(planID), getPlanSic());
				if (plan != null) {
					objectValue.setNumber(plan.getNumber());
					objectValue.setName(plan.getName());
					objectValue.setCurProject(plan.getCurProject());
					objectValue.setFdcProDep(plan);
					objectValue.setSplitState(CostSplitStateEnum.ALLSPLIT);
					FDCProDepConPayPlanContractCollection hasCol = plan
							.getHasContract();
					if (hasCol != null && hasCol.size() > 0) {
						for (int i = 0; i < hasCol.size(); i++) {
							FDCProDepConPayPlanContractInfo hasInfo = hasCol
									.get(i);
							FDCProDepSplitHasConInfo hasSpt = new FDCProDepSplitHasConInfo();
							hasSpt.setHead(objectValue);
							hasSpt.setFdcProDepEntry(hasInfo);
							hasSpt.setContract(hasInfo.getContract());
							hasSpt.setContractName(hasInfo.getContractName());
							hasSpt.setContractPrice(hasInfo.getContractPrice());

						}
					}
				}
			} catch (EASBizException e) {
				e.printStackTrace();
			} catch (BOSException e) {
				e.printStackTrace();
			}
		}
		return objectValue;
	}

	/**
	 * ��ֹ����ƻ�<br>
	 * 1����ձ��<br>
	 * 2��������ͷ<br>
	 * 3��ȡһ�������ƻ���¼<br>
	 * * ���Ϊ��ǩ����ͬ������뵽ҳǩһ<br>
	 * * * ������ͬ������ݽ��в��<br>
	 * * ���Ϊ��ǩ����ͬ���޺�ͬ������뵽ҳǩ������<br>
	 * 4���ƻ�����isSplitRowΪfalse���������isSplitRowΪtrue
	 * 
	 * @param plan
	 */
	protected void fillPlan(FDCProDepConPayPlanInfo plan) {
		if (plan == null) {
			return;
		}
		Color lockColor = new Color(0xE8E8E3);
		// ��ǩ����ͬ
		FDCProDepConPayPlanContractCollection conCol = plan.getHasContract();
		IRow row = null;
		for (int i = 0; i < conCol.size(); i++) {
			FDCProDepConPayPlanContractInfo planCon = conCol.get(i);
			row = tblHas.addRow();
			row.getStyleAttributes().setLocked(true);
			row.getStyleAttributes().setBackground(lockColor);
			row.getCell("isSplitRow").setValue(Boolean.FALSE);
			row.getCell("planID").setValue(planCon);
			row.getCell("conNum").setValue(planCon.getContract());
			row.getCell("conName").setValue(planCon.getContractName());
			row.getCell("conAmount").setValue(planCon.getContractPrice());
			FDCProDepConPayContractEntryCollection details = planCon
					.getEntrys();
			if (details != null) {
				for (int j = 0; j < details.size(); j++) {
					FDCProDepConPayContractEntryInfo detail = details.get(j);
					Calendar cal = Calendar.getInstance();
					cal.setTime(detail.getMonth());
					String keyHead = "MONTH" + cal.get(Calendar.YEAR) + ""
							+ (cal.get(Calendar.MONTH) + 1);
					BigDecimal pay = detail.getOfficialPay();
					row.getCell(keyHead + "plan").setValue(pay);
				}
			}
			splitNewPlan(planCon, row);
		}

		// ��ǩ����ͬ
		FDCProDepConPayPlanUnsettledCollection unCol = plan.getUnsettledCon();
		for (int i = 0; i < unCol.size(); i++) {
			FDCProDepConPayPlanUnsettledInfo planUnC = unCol.get(i);
			row = tblUn.addRow();
			row.getStyleAttributes().setLocked(true);
			row.getStyleAttributes().setBackground(lockColor);
			row.getCell("isSplitRow").setValue(Boolean.FALSE);
			row.getCell("planID").setValue(planUnC);
			row.getCell("conNum").setValue(planUnC.getUnConNumber());
			row.getCell("conName").setValue(planUnC.getUnConName());
			row.getCell("conAmount").setValue(planUnC.getPlanAmount());
			FDCProDepConPayPlanUnsetEntryCollection details = planUnC
					.getEntrys();
			if (details != null) {
				for (int j = 0; j < details.size(); j++) {
					FDCProDepConPayPlanUnsetEntryInfo detail = details.get(j);
					Calendar cal = Calendar.getInstance();
					cal.setTime(detail.getMonth());
					String keyHead = "MONTH" + cal.get(Calendar.YEAR) + ""
							+ (cal.get(Calendar.MONTH) + 1);
					BigDecimal pay = detail.getOfficialPay();
					row.getCell(keyHead + "plan").setValue(pay);
				}
			}
		}

		// �޺�ͬ
		FDCProDepConPayPlanNoContractCollection noCol = plan.getNoContract();
		for (int i = 0; i < noCol.size(); i++) {
			FDCProDepConPayPlanNoContractInfo planNoC = noCol.get(i);
			row = tblNo.addRow();
			row.getStyleAttributes().setLocked(true);
			row.getStyleAttributes().setBackground(lockColor);
			row.getCell("isSplitRow").setValue(Boolean.FALSE);
			row.getCell("planID").setValue(planNoC);
			row.getCell("payMatters").setValue(planNoC.getPayMatters());
			row.getCell("payMattersName").setValue(planNoC.getPayMattersName());
			FDCProDepConPayPlanNoContractEntryCollection details = planNoC
					.getEntrys();
			if (details != null) {
				for (int j = 0; j < details.size(); j++) {
					FDCProDepConPayPlanNoContractEntryInfo detail = details
							.get(j);
					Calendar cal = Calendar.getInstance();
					cal.setTime(detail.getMonth());
					String keyHead = "MONTH" + cal.get(Calendar.YEAR) + ""
							+ (cal.get(Calendar.MONTH) + 1);
					BigDecimal pay = detail.getOfficialPay();
					row.getCell(keyHead + "plan").setValue(pay);
				}
			}
		}
	}

	/**
	 * ����¼ƻ�<br>
	 * �����ͬ���붯̬�ɱ�����ȡ�ɱ��������<br>
	 * ����ȡ��ƿ�Ŀ�������
	 */
	protected void splitNewPlan(FDCProDepConPayPlanContractInfo planCon,
			IRow baseRow) {
		if (planCon == null) {
			return;
		}
		if (planCon.getContract().isIsCoseSplit()) {
			splitCost(planCon, baseRow);
		} else {
			splitNoCost(planCon, baseRow);
		}
	}

	protected void splitNoCost(FDCProDepConPayPlanContractInfo planCon,
			IRow baseRow) {

		ContractBillInfo contract = planCon.getContract();
		ConNoCostSplitInfo conSptInfo = (ConNoCostSplitInfo) getConSptInfo(
				contract.getId().toString(), false);
		if (conSptInfo == null) {
			return;
		}
		ConNoCostSplitEntryCollection conSptEntrys = conSptInfo.getEntrys();
		if (conSptEntrys != null && conSptEntrys.size() > 0) {
			for (int i = 0; i < conSptEntrys.size(); i++) {
				ConNoCostSplitEntryInfo conSptEntry = conSptEntrys.get(i);
				if (conSptEntry.getApportionValue() == null) {
					continue;
				}
				AccountViewInfo account = conSptEntry.getAccountView();
				IRow row = tblHas.addRow();
				row.getCell("isSplitRow").setValue(Boolean.TRUE);
				row.getCell("isSplitCost").setValue(Boolean.TRUE);
				if (account != null && contract.getCurProject() != null) {
					row.getCell("planID").setValue(planCon);
					row.getCell("curProject").setValue(
							contract.getCurProject().getName());
					// String longNum = account.getLongNumber();
					// if (longNum != null) {
					// longNum = longNum.replaceAll("!", ".");
					// }
					row.getCell("accountNum").setValue(account);
					row.getCell("accountName").setValue(account.getName());
					if (conSptEntry.getApportionValue() != null) {
						for (int j = START_CON; j < tblHas.getColumnCount(); j += 3) {
							BigDecimal value = (BigDecimal) baseRow.getCell(
									j + 1).getValue();
							value = value == null ? FDCHelper.ZERO : value;
							value = value.multiply(
									conSptEntry.getApportionValue()).divide(
									FDCHelper.ONE_HUNDRED, 10,
									BigDecimal.ROUND_HALF_UP);
							value = value.setScale(2, BigDecimal.ROUND_HALF_UP);
							row.getCell(j + 2).setValue(value);
						}
					}
				}
			}
		}
	}

	protected void splitCost(FDCProDepConPayPlanContractInfo planCon,
			IRow baseRow) {
		ContractCostSplitInfo conSptInfo = (ContractCostSplitInfo) getConSptInfo(
				planCon.getContract().getId().toString(), true);
		if (conSptInfo == null) {
			return;
		}
		ContractCostSplitEntryCollection conSptEntrys = conSptInfo.getEntrys();
		if (conSptEntrys != null && conSptEntrys.size() > 0) {
			for (int i = 0; i < conSptEntrys.size(); i++) {
				ContractCostSplitEntryInfo conSptEntry = conSptEntrys.get(i);
				if (conSptEntry.getSplitScale() == null) {
					continue;
				}
				CostAccountInfo account = conSptEntry.getCostAccount();
				IRow row = tblHas.addRow();
				row.getCell("isSplitRow").setValue(Boolean.TRUE);
				row.getCell("isSplitCost").setValue(Boolean.TRUE);
				if (account != null && account.getCurProject() != null) {
					row.getCell("planID").setValue(planCon);
					row.getCell("curProject").setValue(account.getCurProject());
					// String longNum = account.getLongNumber();
					// if (longNum != null) {
					// longNum = longNum.replaceAll("!", ".");
					// }
					row.getCell("accountNum").setValue(account);
					row.getCell("accountName").setValue(account.getName());
					if (conSptEntry.getSplitScale() != null) {
						for (int j = START_CON; j < tblHas.getColumnCount(); j += 3) {
							BigDecimal value = (BigDecimal) baseRow.getCell(
									j + 1).getValue();
							value = value == null ? FDCHelper.ZERO : value;
							value = value.multiply(conSptEntry.getSplitScale())
									.divide(FDCHelper.ONE_HUNDRED, 10,
											BigDecimal.ROUND_HALF_UP);
							value = value.setScale(2, BigDecimal.ROUND_HALF_UP);
							row.getCell(j + 2).setValue(value);
						}
					}
				}
			}
		}
	}

	/**
	 * ���ݺ�ͬidȡ�ú�ͬ�����Ϣ
	 * 
	 * @param conNum
	 * @return
	 */
	protected IObjectValue getConSptInfo(String conID, boolean isCost) {
		IObjectValue conSptInfo = null;
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(
				new FilterItemInfo("contractBill.id", conID));
		filter.getFilterItems().add(
				new FilterItemInfo("isInvalid", Boolean.TRUE,
						CompareType.NOTEQUALS));
		view.setFilter(filter);
		view.setTopCount(1);
		try {
			if (isCost) {
				view.setSelector(getConCostSptSic());
				ContractCostSplitCollection conCol = ContractCostSplitFactory
						.getRemoteInstance().getContractCostSplitCollection(
								view);
				if (conCol != null && conCol.size() > 0) {
					conSptInfo = conCol.get(0);
				}
			} else {
				view.setSelector(getConNoCostSptSic());
				ConNoCostSplitCollection conCol = ConNoCostSplitFactory
						.getRemoteInstance().getConNoCostSplitCollection(view);
				if (conCol != null && conCol.size() > 0) {
					conSptInfo = conCol.get(0);
				}
			}
		} catch (BOSException e) {
			handUIException(e);
		}
		return conSptInfo;
	}

	/**
	 * ��ͬ�ɱ������Ϣ
	 * 
	 * @return
	 */
	protected SelectorItemCollection getConCostSptSic() {
		SelectorItemCollection sic = new SelectorItemCollection();
		sic.add("id");
		sic.add("splitState");
		sic.add("isInvalid");
		sic.add("entrys.id");
		sic.add("entrys.amount");
		sic.add("entrys.splitScale");
		sic.add("entrys.amount");
		sic.add("entrys.costAccount.id");
		sic.add("entrys.costAccount.number");
		sic.add("entrys.costAccount.longNumber");
		sic.add("entrys.costAccount.name");
		sic.add("entrys.costAccount.curProject.id");
		sic.add("entrys.costAccount.curProject.name");
		return sic;
	}

	protected SelectorItemCollection getConNoCostSptSic() {
		SelectorItemCollection sic = new SelectorItemCollection();
		sic.add("id");
		sic.add("splitState");
		sic.add("isInvalid");
		sic.add("entrys.id");
		sic.add("entrys.amount");
		sic.add("entrys.splitScale");
		sic.add("entrys.amount");
		sic.add("entrys.costAccount.id");
		sic.add("entrys.costAccount.number");
		sic.add("entrys.costAccount.longNumber");
		sic.add("entrys.costAccount.name");
		sic.add("entrys.costAccount.curProject.id");
		sic.add("entrys.costAccount.curProject.name");
		return sic;
	}

	/**
	 * ����ּƻ�����¼��ѯ
	 * 
	 * @return
	 */
	protected SelectorItemCollection getPlanSic() {
		SelectorItemCollection sic = new SelectorItemCollection();
		sic.add("*");
		sic.add("curProject.id");
		sic.add("curProject.number");
		sic.add("curProject.name");
		sic.add("payPlanCycle.id");
		sic.add("payPlanCycle.cycle");
		sic.add("creator.id");
		sic.add("creator.number");
		sic.add("creator.name");
		sic.add("auditor.id");
		sic.add("auditor.number");
		sic.add("auditor.name");

		sic.add("hasContract.*");
		sic.add("hasContract.depPlan.id");
		sic.add("hasContract.department.id");
		sic.add("hasContract.department.number");
		sic.add("hasContract.department.name");
		sic.add("hasContract.contract.id");
		sic.add("hasContract.contract.number");
		sic.add("hasContract.contract.name");
		sic.add("hasContract.contract.isCoseSplit");
		sic.add("hasContract.entrys.*");
		sic.add("hasContract.entrys.moneyDefine.id");
		sic.add("hasContract.entrys.moneyDefine.number");
		sic.add("hasContract.entrys.moneyDefine.name");

		sic.add("unsettledCon.*");
		sic.add("unsettledCon.depPlan.id");
		sic.add("unsettledCon.department.id");
		sic.add("unsettledCon.department.number");
		sic.add("unsettledCon.department.name");
		sic.add("unsettledCon.entrys.*");
		sic.add("unsettledCon.entrys.moneyDefine.id");
		sic.add("unsettledCon.entrys.moneyDefine.number");
		sic.add("unsettledCon.entrys.moneyDefine.name");

		sic.add("noContract.*");
		sic.add("noContract.depPlan.id");
		sic.add("noContract.department.id");
		sic.add("noContract.department.number");
		sic.add("noContract.department.name");
		sic.add("noContract.entrys.*");
		sic.add("noContract.entrys.moneyDefine.id");
		sic.add("noContract.entrys.moneyDefine.number");
		sic.add("noContract.entrys.moneyDefine.name");

		return sic;
	}

	public SelectorItemCollection getSelectors() {
		SelectorItemCollection sic = new SelectorItemCollection();
		sic.add("id");
		sic.add("number");
		sic.add("name");
		sic.add("splitState");
		sic.add("state");

		sic.add("curProject.id");
		sic.add("curProject.number");
		sic.add("curProject.name");

		sic.add("auditor.id");
		sic.add("auditor.number");
		sic.add("auditor.name");
		sic.add("auditTime");

		sic.add("splitUser.id");
		sic.add("splitUser.number");
		sic.add("splitUser.name");
		sic.add("splitDate");

		sic.add("fdcProDep.id");
		sic.add("fdcProDep.number");
		sic.add("fdcProDep.name");
		sic.add("fdcProDep.payPlanCycle.id");
		sic.add("fdcProDep.payPlanCycle.cycle");
		sic.add("fdcProDep.year");
		sic.add("fdcProDep.month");

		sic.add("hasCons.id");
		sic.add("hasCons.fdcProDepEntry.id");
		sic.add("hasCons.fdcProDepEntry.contractNumber");
		sic.add("hasCons.fdcProDepEntry.contractName");
		sic.add("hasCons.fdcProDepEntry.contractPrice");
		sic.add("hasCons.contract.id");
		sic.add("hasCons.contract.number");
		sic.add("hasCons.contract.name");
		sic.add("hasCons.contractName");
		sic.add("hasCons.contractPrice");
		sic.add("hasCons.project.id");
		sic.add("hasCons.project.number");
		sic.add("hasCons.project.name");
		sic.add("hasCons.isSplitCost");
		sic.add("hasCons.isSplitRow");
		sic.add("hasCons.costAccount.id");
		sic.add("hasCons.costAccount.name");
		sic.add("hasCons.costAccount.codingNumber");
		sic.add("hasCons.costAccount.curProject.id");
		sic.add("hasCons.costAccount.curProject.name");
		sic.add("hasCons.accountView.id");
		sic.add("hasCons.accountView.number");
		sic.add("hasCons.accountView.name");
		sic.add("hasCons.accountView.longNumber");
		sic.add("hasCons.accountView.curProject.id");
		sic.add("hasCons.accountView.curProject.name");
		sic.add("hasCons.details.id");
		sic.add("hasCons.details.planDetails.id");
		sic.add("hasCons.details.planDetails.pay");
		sic.add("hasCons.details.planDetails.month");
		sic.add("hasCons.details.sptPay");
		sic.add("hasCons.details.month");

		sic.add("unCons.id");
		sic.add("unCons.fdcProDepEntry.id");
		// sic.add("unCons.fdcProDepEntry.contractNumber");
		// sic.add("unCons.fdcProDepEntry.contractName");
		// sic.add("unCons.fdcProDepEntry.contractPrice");
		sic.add("unCons.unConNumber");
		sic.add("unCons.unConName");
		sic.add("unCons.planAmount");
		sic.add("unCons.project.id");
		sic.add("unCons.project.number");
		sic.add("unCons.project.name");
		sic.add("unCons.isSplitCost");
		sic.add("unCons.isSplitRow");
		sic.add("unCons.costAccount.id");
		sic.add("unCons.costAccount.name");
		sic.add("unCons.costAccount.codingNumber");
		sic.add("unCons.costAccount.curProject.id");
		sic.add("unCons.costAccount.curProject.name");
		sic.add("unCons.accountView.id");
		sic.add("unCons.accountView.number");
		sic.add("unCons.accountView.name");
		sic.add("unCons.accountView.longNumber");
		sic.add("unCons.accountView.curProject.id");
		sic.add("unCons.accountView.curProject.name");
		sic.add("unCons.details.id");
		sic.add("unCons.details.planDetails.id");
		sic.add("unCons.details.planDetails.pay");
		sic.add("unCons.details.planDetails.month");
		sic.add("unCons.details.sptPay");
		sic.add("unCons.details.month");

		sic.add("noCons.id");
		sic.add("noCons.fdcProDepEntry.id");
		// sic.add("noCons.fdcProDepEntry.contractNumber");
		// sic.add("noCons.fdcProDepEntry.contractName");
		// sic.add("noCons.fdcProDepEntry.contractPrice");
		sic.add("noCons.payMatters");
		sic.add("noCons.payMattersName");
		sic.add("noCons.project.id");
		sic.add("noCons.project.number");
		sic.add("noCons.project.name");
		sic.add("noCons.isSplitCost");
		sic.add("noCons.isSplitRow");
		sic.add("noCons.costAccount.id");
		sic.add("noCons.costAccount.name");
		sic.add("noCons.costAccount.codingNumber");
		sic.add("noCons.costAccount.curProject.id");
		sic.add("noCons.costAccount.curProject.name");
		sic.add("noCons.accountView.id");
		sic.add("noCons.accountView.number");
		sic.add("noCons.accountView.name");
		sic.add("noCons.accountView.longNumber");
		sic.add("noCons.accountView.curProject.id");
		sic.add("noCons.accountView.curProject.name");
		sic.add("noCons.details.id");
		sic.add("noCons.details.planDetails.id");
		sic.add("noCons.details.planDetails.pay");
		sic.add("noCons.details.planDetails.month");
		sic.add("noCons.details.sptPay");
		sic.add("noCons.details.month");

		return sic;
	}

	protected ICellEditor getCellEditor(String type) {
		if (type == null) {
			return null;
		} else if ("amount".equals(type)) {
			return FDCClientHelper.getNumberCellEditor();
		} else if ("explain".equals(type)) {
			KDDetailedArea explain = new KDDetailedArea(250, 200);
			explain.setMaxLength(1000);
			return new KDTDefaultCellEditor(explain);
		} else if ("contract".equals(type)) {
			KDBizPromptBox prmtcontract = new KDBizPromptBox();
			prmtcontract.setDisplayFormat("$name$");
			prmtcontract.setEditFormat("$number$");
			prmtcontract.setCommitFormat("$number$");
			prmtcontract.setRequired(true);
			prmtcontract
					.setQueryInfo("com.kingdee.eas.fdc.contract.app.F7ContractBillQueryForPayPlanQuery");
			return new KDTDefaultCellEditor(prmtcontract);
		} else if ("project".equals(type)) {
			KDBizPromptBox prmtproject = new KDBizPromptBox();
			prmtproject.setDisplayFormat("$name$");
			prmtproject.setEditFormat("$number$");
			prmtproject.setCommitFormat("$number$");
			prmtproject.setRequired(true);
			prmtproject
					.setQueryInfo("com.kingdee.eas.fdc.basedata.app.CurProjectQuery");
			return new KDTDefaultCellEditor(prmtproject);
		} else if ("text".equals(type)) {
			KDTextField txt = new KDTextField();
			txt.setMaxLength(80);
			return new KDTDefaultCellEditor(txt);
		} else if ("costAccount".equals(type)) {
			KDBizPromptBox prmtCostAccount = new KDBizPromptBox();
			prmtCostAccount.setDisplayFormat("$number$");
			prmtCostAccount.setEditFormat("$number$");
			prmtCostAccount.setCommitFormat("$number$");
			prmtCostAccount.setRequired(true);
			prmtCostAccount
					.setQueryInfo("com.kingdee.eas.fdc.basedata.app.F7CostAccountQuery");
			return new KDTDefaultCellEditor(prmtCostAccount);
		} else if ("accountView".equals(type)) {
			KDBizPromptBox prmtAccountView = new KDBizPromptBox();
			prmtAccountView.setDisplayFormat("$number$");
			prmtAccountView.setEditFormat("$number$");
			prmtAccountView.setCommitFormat("$number$");
			prmtAccountView.setRequired(true);
			prmtAccountView
					.setQueryInfo("com.kingdee.eas.basedata.master.account.app.F7AccountViewQuery");
			return new KDTDefaultCellEditor(prmtAccountView);
		}
		return null;
	}

	protected void initDataStatus() {
		super.initDataStatus();

	}

}
