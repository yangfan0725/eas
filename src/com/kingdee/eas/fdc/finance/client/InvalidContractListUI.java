/**
 * output package name
 */
package com.kingdee.eas.fdc.finance.client;

import java.awt.event.ActionEvent;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.table.util.KDTableUtil;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.ItemAction;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.basedata.assistant.PeriodInfo;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basedata.CostSplitStateEnum;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.PaymentTypeInfo;
import com.kingdee.eas.fdc.basedata.client.FDCClientHelper;
import com.kingdee.eas.fdc.basedata.client.FDCSplitClientHelper;
import com.kingdee.eas.fdc.contract.ConChangeNoCostSplitCollection;
import com.kingdee.eas.fdc.contract.ConChangeNoCostSplitFactory;
import com.kingdee.eas.fdc.contract.ConChangeNoCostSplitInfo;
import com.kingdee.eas.fdc.contract.ConChangeSplitCollection;
import com.kingdee.eas.fdc.contract.ConChangeSplitFactory;
import com.kingdee.eas.fdc.contract.ConChangeSplitInfo;
import com.kingdee.eas.fdc.contract.ConNoCostSplitFactory;
import com.kingdee.eas.fdc.contract.ContractBillFactory;
import com.kingdee.eas.fdc.contract.ContractBillInfo;
import com.kingdee.eas.fdc.contract.ContractChangeBillCollection;
import com.kingdee.eas.fdc.contract.ContractChangeBillFactory;
import com.kingdee.eas.fdc.contract.ContractChangeBillInfo;
import com.kingdee.eas.fdc.contract.ContractCostSplitFactory;
import com.kingdee.eas.fdc.contract.FDCUtils;
import com.kingdee.eas.fdc.contract.SettNoCostSplitFactory;
import com.kingdee.eas.fdc.contract.SettlementCostSplitFactory;
import com.kingdee.eas.fdc.contract.client.ContractBillEditUI;
import com.kingdee.eas.fdc.contract.client.ContractClientUtils;
import com.kingdee.eas.fdc.finance.PaymentNoCostSplitFactory;
import com.kingdee.eas.fdc.finance.PaymentSplitFactory;
import com.kingdee.eas.fdc.finance.TraceOldSplitVoucherFacadeFactory;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.MsgBox;

/**
 * output class name
 */
public class InvalidContractListUI extends AbstractInvalidContractListUI {
	private static final Logger logger = CoreUIObject
			.getLogger(InvalidContractListUI.class);

	/**
	 * output class constructor
	 */
	public InvalidContractListUI() throws Exception {
		super();
	}

	/**
	 * output storeFields method
	 */
	public void storeFields() {
		super.storeFields();
	}

	public void actionTrace_actionPerformed(ActionEvent e) throws Exception {
		checkSelected();
		List idList = ContractClientUtils.getSelectedIdValues(getMainTable(),
				getKeyFieldName());
		int size = idList.size();
		/***
		 * �˷���У�飬forѭ���ж�ε�����remote����exists����
		 * TODO ��Ҫ�Ż�
		 */
		for (int i = 0; i < size; i++) {
			FilterInfo filter = new FilterInfo();
			filter.getFilterItems().add(
					new FilterItemInfo("contractBill", idList.get(i)));
			filter.getFilterItems().add(
					new FilterItemInfo("splitState",
							CostSplitStateEnum.ALLSPLIT_VALUE));
			filter.getFilterItems().add(
					new FilterItemInfo("state", FDCBillStateEnum.INVALID_VALUE,
							CompareType.NOTEQUALS));
			SelectorItemCollection selector = new SelectorItemCollection();
			selector.add("id");
			selector.add("isCoseSplit");
			selector.add("number");
			selector.add("curProject");
			ContractBillInfo info = ContractBillFactory.getRemoteInstance()
					.getContractBillInfo(
							new ObjectUuidPK(BOSUuid.read(idList.get(i)
									.toString())));
			if (info.isIsCoseSplit()) {
				/***
				 * ����ɱ���ͬ�����Ѿ����
				 */
				if (!ContractCostSplitFactory.getRemoteInstance()
						.exists(filter)) {
					MsgBox.showError(this, "��ͬ���Ϊ:" + info.getNumber()
							+ "�ĺ�ͬδ��֣�");
					SysUtil.abort();
				}
				FilterInfo filterPay = new FilterInfo();
				filterPay.getFilterItems().add(
						new FilterItemInfo("paymentBill.contractBillId", idList
								.get(i)));
				filterPay.getFilterItems().add(
						new FilterItemInfo("paymentBill.fdcPayType.payType.id",
								PaymentTypeInfo.settlementID));
				/*****
				 * ���ڽ�������������
				 */
				if (PaymentSplitFactory.getRemoteInstance().exists(filterPay)) {
					FilterInfo filterSett = new FilterInfo();
					filterSett.getFilterItems().add(
							new FilterItemInfo(
									"settlementBill.contractBill.id", idList
											.get(i)));
					filterSett.getFilterItems().add(
							new FilterItemInfo("state",
									FDCBillStateEnum.INVALID_VALUE,
									CompareType.NOTEQUALS));
					if (!SettlementCostSplitFactory.getRemoteInstance().exists(
							filterSett)) {
						MsgBox.showError(this, "���ڴ�����Ľ�����ͬ���Ϊ:"
								+ info.getNumber() + "�ĺ�ͬ��Ӧ���㵥δ��֣�");
						SysUtil.abort();
					}
				}
				/**
				 * У��˺�ͬ���Ƿ���ڵ�ǰ����ɱ��ڼ����ǰ�ڼ��δ��ֵı��ָ������������ƾ֤����
				 */
				PeriodInfo period = FDCUtils.getCurrentPeriod(null, info.getCurProject().getId().toString(), true);
				
				EntityViewInfo viewChange = new EntityViewInfo();
				FilterInfo filterChange = new FilterInfo();
				viewChange.setFilter(filterChange);
				filterChange.getFilterItems().add(
						new FilterItemInfo("contractBill", idList.get(i)));
				filterChange.getFilterItems().add(new FilterItemInfo("period.number",new Integer(period.getNumber()),CompareType.LESS_EQUALS));
				viewChange.getSelector().add("id");
				viewChange.getSelector().add("number");
				ContractChangeBillCollection change = ContractChangeBillFactory
						.getRemoteInstance().getContractChangeBillCollection(
								viewChange);
				if (change != null) {
					viewChange = new EntityViewInfo();
					filterChange = new FilterInfo();
					viewChange.setFilter(filterChange);
					viewChange.getSelector().add("contractChange");
					filterChange.getFilterItems().add(
							new FilterItemInfo("contractChange.contractBill",
									idList.get(i)));
					ConChangeSplitCollection changeSplit = ConChangeSplitFactory
							.getRemoteInstance().getConChangeSplitCollection(
									viewChange);
					StringBuffer sb = new StringBuffer();
					Set idSet = new HashSet();
					if (changeSplit != null) {
						for (Iterator iter = changeSplit.iterator(); iter
								.hasNext();) {
							ConChangeSplitInfo split = (ConChangeSplitInfo) iter
									.next();
							idSet.add(split.getContractChange().getId().toString());
						}
					}
					for (Iterator iter = change.iterator(); iter.hasNext();) {
						ContractChangeBillInfo bill = (ContractChangeBillInfo) iter
								.next();
						if (!idSet.contains(bill.getId().toString())) {
							sb.append(bill.getNumber() + ",");
						}
					}
					if (sb.length() > 0) {
						MsgBox.showError(this, "��ͬ����ͬ���룺" + info.getNumber()
								+ "�����ڵ�ǰ�ڼ估��ǰ�ڼ��δ��ֵı����������룺"
								+ sb.substring(0, sb.length() - 1)
								+ "�������Ƚ��б����֡�");
						SysUtil.abort();
					}
				}
				
				
			} else {
				/***
				 * �ǳɱ���ͬ������
				 */
				
				if (!ConNoCostSplitFactory.getRemoteInstance().exists(filter)) {
					MsgBox.showError(this, "��ͬ���Ϊ:" + info.getNumber()
							+ "�ĺ�ͬδ��֣�");
					SysUtil.abort();
				}
				FilterInfo filterPay = new FilterInfo();
				filterPay.getFilterItems().add(
						new FilterItemInfo("paymentBill.contractBillId", idList
								.get(i)));
				filterPay.getFilterItems().add(
						new FilterItemInfo("paymentBill.fdcPayType.payType.id",
								PaymentTypeInfo.settlementID));
				/***
				 * �ǳɱ��ĺ�ͬ�����н�������������
				 */
				if (PaymentNoCostSplitFactory.getRemoteInstance().exists(
						filterPay)) {
					FilterInfo filterSett = new FilterInfo();
					filterSett.getFilterItems().add(
							new FilterItemInfo(
									"settlementBill.contractBill.id", idList
											.get(i)));
					filterSett.getFilterItems().add(
							new FilterItemInfo("state",
									FDCBillStateEnum.INVALID_VALUE,
									CompareType.NOTEQUALS));
					if (!SettNoCostSplitFactory.getRemoteInstance().exists(
							filterSett)) {
						MsgBox.showError(this, "���ڴ�����Ľ�����ͬ���Ϊ:"
								+ info.getNumber() + "�ĺ�ͬ��Ӧ���㵥δ��֣�");
						SysUtil.abort();
					}
				}
				
				/**
				 * У��˺�ͬ���Ƿ���ڵ�ǰ����ɱ��ڼ����ǰ�ڼ��δ��ֵı��ָ������������ƾ֤����
				 */
				PeriodInfo period = FDCUtils.getCurrentPeriod(null, info.getCurProject().getId().toString(), false);
				
				EntityViewInfo viewChange = new EntityViewInfo();
				FilterInfo filterChange = new FilterInfo();
				viewChange.setFilter(filterChange);
				filterChange.getFilterItems().add(
						new FilterItemInfo("contractBill", idList.get(i)));
				filterChange.getFilterItems().add(new FilterItemInfo("period.number",new Integer(period.getNumber()),CompareType.LESS_EQUALS));
				viewChange.getSelector().add("id");
				viewChange.getSelector().add("number");
				ContractChangeBillCollection change = ContractChangeBillFactory
						.getRemoteInstance().getContractChangeBillCollection(
								viewChange);
				if (change != null) {
					viewChange = new EntityViewInfo();
					filterChange = new FilterInfo();
					viewChange.setFilter(filterChange);
					viewChange.getSelector().add("contractChange");
					filterChange.getFilterItems().add(
							new FilterItemInfo("contractChange.contractBill",
									idList.get(i)));
					ConChangeNoCostSplitCollection changeSplit = ConChangeNoCostSplitFactory
							.getRemoteInstance().getConChangeNoCostSplitCollection(
									viewChange);
					StringBuffer sb = new StringBuffer();
					Set idSet = new HashSet();
					if (changeSplit != null) {
						for (Iterator iter = changeSplit.iterator(); iter
								.hasNext();) {
							ConChangeNoCostSplitInfo split = (ConChangeNoCostSplitInfo) iter
									.next();
							idSet.add(split.getContractChange().getId().toString());
						}
					}
					for (Iterator iter = change.iterator(); iter.hasNext();) {
						ContractChangeBillInfo bill = (ContractChangeBillInfo) iter
								.next();
						if (!idSet.contains(bill.getId().toString())) {
							sb.append(bill.getNumber() + ",");
						}
					}
					if (sb.length() > 0) {
						MsgBox.showError(this, "��ͬ����ͬ���룺" + info.getNumber()
								+ "�����ڵ�ǰ�ڼ估��ǰ�ڼ��δ��ֵı����������룺"
								+ sb.substring(0, sb.length() - 1)
								+ "�������Ƚ��б����֡�");
						SysUtil.abort();
					}
				}
				
			}
		}
		
		/***
		 * У��ͨ�������÷���������
		 */
		TraceOldSplitVoucherFacadeFactory.getRemoteInstance().traceContracts(
				idList);
		MsgBox.showInfo(this, "�����ɹ���");
		refresh(e);
	}

	protected ICoreBase getRemoteInterface() throws BOSException {
		return ContractBillFactory.getRemoteInstance();
	}

	protected ICoreBase getBizInterface() throws Exception {
		return ContractBillFactory.getRemoteInstance();
	}

	protected void audit(List ids) throws Exception {

	}

	protected void unAudit(List ids) throws Exception {

	}

	protected String getKeyFieldName() {
		return super.getKeyFieldName();
	}

	public void onLoad() throws Exception {
		if ((!SysContext.getSysContext().getCurrentOrgUnit()
				.isIsCompanyOrgUnit())
				&& (SysContext.getSysContext().getCurrentOrgUnit()
						.isIsCostOrgUnit())) {
			MsgBox.showWarning(this, PaymentBillClientUtils.getRes("CostUnit"));
			SysUtil.abort();
		}
		if(!FDCUtils.IsFinacial(null, SysContext.getSysContext().getCurrentFIUnit().getId().toString())){
			MsgBox.showWarning(this, "�˲�����֯δ���ò���ɱ�һ�廯��");
			SysUtil.abort();
		}
		super.onLoad();
		FDCClientHelper.setActionEnable(new ItemAction[] { actionAudit,
				actionUnAudit, actionWorkFlowG, actionAttachment,actionAddNew,actionEdit,actionRemove }, false);
		menuWorkFlow.setVisible(false);
		this.actionViewAdjust.setVisible(true);
		this.actionViewAdjust.setEnabled(true);
	}

	public void actionView_actionPerformed(ActionEvent e) throws Exception {
		checkSelected();
		int selectRows[] = KDTableUtil.getSelectedRows(getMainTable());
		if (selectRows.length > 1) {
			MsgBox.showWarning(this, FDCSplitClientHelper
					.getRes("multiRowSelected"));
			SysUtil.abort();
		} else if (selectRows.length == 1) {
			Object obj = getMainTable().getCell(selectRows[0], "id").getValue();
			if (obj != null) {
				String id = obj.toString();
				UIContext uiContext = new UIContext(this);
				uiContext.put(UIContext.ID, id);
				IUIWindow testUI = UIFactory.createUIFactory(
						UIFactoryName.NEWTAB).create(
						ContractBillEditUI.class.getName(), uiContext, null,
						"FINDVIEW");
				testUI.show();
			}
		}
	}
	
	protected void initTable() {
		
	}
	
	public void actionClearSplit_actionPerformed(ActionEvent e) throws Exception {
		UIContext uiContext = new UIContext(this);
		IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.MODEL)
				.create(ToInvalidContractUI.class.getName(), uiContext, null, "View");
		uiWindow.show();
		refresh(e);
	}
	public void actionViewAdjust_actionPerformed(ActionEvent e) throws Exception
    {
		UIContext uiContext = new UIContext(this);
		IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.NEWTAB)
				.create(FDCAdjustBillListUI.class.getName(), uiContext, null, "View");
		uiWindow.show();
		refresh(e);
    }
	
	protected String[] getLocateNames() {
		return new String[] {"number", "name", "contractType.name",  "partB.name", };
	}
}