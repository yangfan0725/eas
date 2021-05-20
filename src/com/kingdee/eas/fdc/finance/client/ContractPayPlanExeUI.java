/**
 * output package name
 */
package com.kingdee.eas.fdc.finance.client;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.swing.event.TreeSelectionEvent;

import org.apache.log4j.Logger;

import com.kingdee.bos.ctrl.kdf.expr.Variant;
import com.kingdee.bos.ctrl.kdf.table.ICell;
import com.kingdee.bos.ctrl.kdf.table.IColumn;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTDataRequestManager;
import com.kingdee.bos.ctrl.kdf.table.KDTIndexColumn;
import com.kingdee.bos.ctrl.kdf.table.KDTMergeManager;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectManager;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent;
import com.kingdee.bos.ctrl.kdf.table.event.KDTSelectEvent;
import com.kingdee.bos.ctrl.kdf.table.foot.KDTFootManager;
import com.kingdee.bos.ctrl.kdf.util.style.Styles.HorizontalAlignment;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.eas.base.commonquery.IQuerySolutionFacade;
import com.kingdee.eas.base.commonquery.QuerySolutionFacadeFactory;
import com.kingdee.eas.base.commonquery.QuerySolutionInfo;
import com.kingdee.eas.base.commonquery.client.CommonQueryDialog;
import com.kingdee.eas.base.commonquery.client.CustomerParams;
import com.kingdee.eas.base.commonquery.client.CustomerQueryPanel;
import com.kingdee.eas.basedata.org.OrgF7InnerUtils;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.fdc.basedata.FDCConstants;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.contract.ContractBillCollection;
import com.kingdee.eas.fdc.contract.ContractBillInfo;
import com.kingdee.eas.fdc.finance.PaymentFacadeFactory;
import com.kingdee.eas.framework.client.FrameWorkClientUtils;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.MsgBox;

/**
 * output class name
 */
public class ContractPayPlanExeUI extends AbstractContractPayPlanExeUI {
	private static final Logger logger = CoreUIObject
			.getLogger(ContractPayPlanExeUI.class);

	private static final int FIX_COL_LEN = 11;

	protected ContractPayPlanExeFilterUI filterUI;
	protected CommonQueryDialog dialog;
	// ����ͳ����
	private Map totalMap = new HashMap();

	/**
	 * output class constructor
	 */
	public ContractPayPlanExeUI() throws Exception {
		super();
	}

	private CustomerQueryPanel getFilterUI() {
		if (this.filterUI == null) {
			try {
				this.filterUI = new ContractPayPlanExeFilterUI(this,
						this.actionOnLoad);
			} catch (Exception e) {
				e.printStackTrace();
				abort(e);
			}
		}
		return this.filterUI;
	}

	public CustomerParams getCustomerParams() {
		return ((ContractPayPlanExeFilterUI) getFilterUI()).getCustomerParams();
	}


	public void onLoad() throws Exception {
		super.onLoad();
		initMyIcon();
	}

	protected void initTable() {
		tblMain.checkParsed();
		FDCHelper.formatTableNumber(tblMain, "contract.oriAmount");
		FDCHelper.formatTableNumber(tblMain, "contract.amount");
		FDCHelper.formatTableNumber(tblMain, "contract.lastPrice");// contract.
		FDCHelper.formatTableNumber(tblMain, "notPaid");
		FDCHelper.formatTableNumber(tblMain, "allPaidRate");
	}

	private void initMyIcon() {
		this.actionAddNew.setVisible(false);
		this.actionEdit.setVisible(false);
		this.actionLocate.setVisible(false);
		this.actionRemove.setVisible(false);
		this.actionView.setVisible(false);
		this.menuEdit.setVisible(false);
	}

	protected KDTable getPrintTable() {
		return this.tblMain;
	}

	protected Variant getPrintVariant(String varName) {
		return null;
	}

	public void query(Object condition) {
		tblMain.removeRows();
	}

	public void actionQuery_actionPerformed(ActionEvent e) throws Exception {
		IQuerySolutionFacade iQuery = QuerySolutionFacadeFactory
				.getRemoteInstance();
		String queryName = (getQueryInfo(this.mainQueryPK)).getFullName();

		if (!isPerformDefaultQuery(iQuery, queryName)) {

			// �Ƿ���ʾͨ�ù��˿�
			if (!isFirstDefaultQuery()) {

				if (dialog == null) {
					dialog = initCommonQueryDialog();
					dialog.setMaxReturnCountVisible(false);
				}

				if (dialog.show()) {
					// Map map = filterUI.getCustomCondition();
					fillTable();
				} else {
					tHelper.setDialog(dialog);
					SysUtil.abort();
				}
			}
		} else {

			String uiName = getQueryUiName();
			String name = getQueryName();
			if (uiName == null) {
				uiName = this.getClass().getName();
			}
			if (name == null) {
				name = queryName;
			}
			QuerySolutionInfo querySolution = iQuery.getDefaultSolution(uiName,
					name);

			String customerParams = ((ContractPayPlanExeFilterUI) getFilterUI())
					.getQueryPanelInfo(querySolution,
							getFilterUI().getClass().getName())
					.getCustomerParams();
			CustomerParams cp = new CustomerParams();
			this.getFilterUI().setCustomerParams(cp);
		}
	}

	protected void fillTable() throws Exception {
		fillTable(null);
	}

	/**
	 *  �����
	 */
	protected void fillTable(Map map) throws Exception {
		// ʵģʽ
		tblMain.getDataRequestManager().setDataRequestMode(KDTDataRequestManager.REAL_MODE);
		tblMain.getSelectManager().setSelectMode(KDTSelectManager.MULTIPLE_ROW_SELECT);
		tblMain.removeRows();
		totalMap.clear();
		int cols = tblMain.getColumnCount();
		for (int i = cols - 1; i > FIX_COL_LEN; i--) {
			tblMain.removeColumn(i);
		}

		// ��ȡ����
		CustomerParams param = getCustomerParams();
		int dateType = param.getInt(ContractPayPlanExeFilterUI.DATE_TYPE);
		int yearFrom = param.getInt(ContractPayPlanExeFilterUI.YEAR_FROM);
		int yearTo = param.getInt(ContractPayPlanExeFilterUI.YEAR_TO);
		int monthFrom = param.getInt(ContractPayPlanExeFilterUI.MONTH_FROM);
		int monthTo = param.getInt(ContractPayPlanExeFilterUI.MONTH_TO);

		// ����������
		cols = calcols(dateType, yearFrom, monthFrom, yearTo, monthTo);
		//��������д��������ڱ�������е������е�˳��ᵼ�¶�̬��ӵ����¼���ȡ�����󣬲��һ��ɵ����ж�  by Cassiel_peng
//		int colInex = tblMain.getColumnIndex("contract.lastPrice");
		int colInex =11;
		if (cols > 0) {
			KDTMergeManager mm = tblMain.getHeadMergeManager();
			// ���ӱ����
			for (int i = 0; i < cols; i++) {

				int key = colInex + i * 4 + 1;
				IColumn col1 = tblMain.addColumn(key);
				col1.setKey("payPlan" + key);
				tblMain.getHeadRow(1).getCell(key).setValue("�ƻ���");
				FDCHelper.formatTableNumber(tblMain, "payPlan" + key);

				IColumn col2 = tblMain.addColumn(key + 1);
				col2.setKey("payPlan" + (key + 1));
				tblMain.getHeadRow(1).getCell(key + 1).setValue("ʵ����");
				FDCHelper.formatTableNumber(tblMain, "payPlan" + (key + 1));

				IColumn col3 = tblMain.addColumn(key + 2);
				col3.setKey("payPlan" + (key + 2));
				tblMain.getHeadRow(1).getCell(key + 2).setValue("����");
				tblMain.getColumn("payPlan" + (key + 2)).getStyleAttributes()
						.setNumberFormat("##.##%");

				IColumn col4 = tblMain.addColumn(key + 3);
				col4.setKey("payPlan" + (key + 3));
				tblMain.getHeadRow(1).getCell(key + 3).setValue("���");
				FDCHelper.formatTableNumber(tblMain, "payPlan" + (key + 3));

				mm.mergeBlock(0, key, 0, key + 3, KDTMergeManager.SPECIFY_MERGE);
				tblMain.getHeadRow(0).getCell(key).setValue(calcolName(dateType, yearFrom, monthFrom, i));
			}

			fetchData(param);
		}
		appendFootRow();
	}

	/**
	 *  ���㶯̬����
	 * @param dateType
	 * @param yearFrom
	 * @param monthFrom
	 * @param yearTo
	 * @param monthTo
	 * @return
	 */
	private int calcols(int dateType, int yearFrom, int monthFrom, int yearTo, int monthTo) {
		int cols = 0;
		if (dateType == 1) {
			if (yearFrom == yearTo) {
				cols = monthTo - monthFrom + 1;
			} else if (yearFrom < yearTo) {
				cols = 12 - monthFrom + 1;
				for (int i = yearFrom + 1; i < yearTo; i++) {
					cols += 12;
				}
				cols += monthTo;
			}
		} else if (dateType == 2) {
			if (yearFrom == yearTo) {
				cols = monthTo - monthFrom + 1;
			} else if (yearFrom < yearTo) {
				cols = 4 - monthFrom + 1;
				for (int i = yearFrom + 1; i < yearTo; i++) {
					cols += 4;
				}
				cols += monthTo;
			}
		} else if (dateType == 3) {
			cols = yearTo - yearFrom + 1;
		}

		return cols > 0 ? cols : 0;
	}

	/**
	 * ���ݹ�����������������
	 * @param dateType ��������
	 * @param yearFrom ��ʼ���
	 * @param monthFrom ��ʼ�·�
	 * @param i �е��ۼӻ��������磺����������ۼ��£����������ơ�
	 * @return
	 */
	private String calcolName(int dateType, int yearFrom, int monthFrom, int i) {
		String colName = null;
		if (dateType == 1) {
			if (monthFrom + i <= 12) {
				colName = (yearFrom) + "��" + (monthFrom + i) + "��";
			} else {
				colName = (yearFrom + (monthFrom + i) / 12) + "��"	+ (monthFrom + i - 12) % 12 + "��";
			}
		} else if (dateType == 2) {
			if (monthFrom + i <= 4) {
				colName = (yearFrom) + "��" + (monthFrom + i) + "��";
			} else {
				colName = (yearFrom + (monthFrom + i) / 4) + "��" + (monthFrom + i - 4) % 4 + "��";
			}
		} else if (dateType == 3) {
			colName = (yearFrom + i) + "��";
		}

		return colName;
	}

	protected void fetchData(CustomerParams param) throws Exception {

		Map p = new HashMap();
		// ��ȡ����
		int dateType = param.getInt(ContractPayPlanExeFilterUI.DATE_TYPE);
		int yearFrom = param.getInt(ContractPayPlanExeFilterUI.YEAR_FROM);
		int yearTo = param.getInt(ContractPayPlanExeFilterUI.YEAR_TO);
		int monthFrom = param.getInt(ContractPayPlanExeFilterUI.MONTH_FROM);
		int monthTo = param.getInt(ContractPayPlanExeFilterUI.MONTH_TO);
		String currencyId = (String) param
				.getCustomerParam(ContractPayPlanExeFilterUI.CURRENCY_ID);

		p.put(ContractPayPlanExeFilterUI.DATE_TYPE, new Integer(param.getInt(ContractPayPlanExeFilterUI.DATE_TYPE)));
		p.put(ContractPayPlanExeFilterUI.YEAR_FROM, new Integer(param.getInt(ContractPayPlanExeFilterUI.YEAR_FROM)));
		p.put(ContractPayPlanExeFilterUI.YEAR_TO, new Integer(param.getInt(ContractPayPlanExeFilterUI.YEAR_TO)));
		p.put(ContractPayPlanExeFilterUI.MONTH_FROM, new Integer(param.getInt(ContractPayPlanExeFilterUI.MONTH_FROM)));
		p.put(ContractPayPlanExeFilterUI.MONTH_TO, new Integer(param.getInt(ContractPayPlanExeFilterUI.MONTH_TO)));
		p.put(ContractPayPlanExeFilterUI.CURRENCY_ID, currencyId);
		p.put("projectIds", set);
		CurProjectInfo project = null;

		Map result = PaymentFacadeFactory.getRemoteInstance().fetchPayPlanData(p);

		// ��ͬ
		ContractBillCollection contractBills = (ContractBillCollection) result.get("ContractBillCollection");
		if (contractBills == null) {
			return;
		}
		Map conExecuteData = (Map) result.get("fetchExecution");
		Map payDataMap = (Map) result.get("payData");
		fillContracts(project, contractBills, conExecuteData, payDataMap, dateType, yearFrom, monthFrom);
		fillOtherDatas(payDataMap);
	}

	/**
	 *  ���ִ�����
	 */
	private void fillData(Map data, IRow row, String contractId, int dateType, int yearFrom, int monthFrom) {

		Map conMap = (Map) data.get(contractId);
		if (conMap != null) {
			Set keySet = conMap.keySet();
			Iterator it = keySet.iterator();
			while (it.hasNext()) {
				String key = (String) it.next();
				Map amtMap = (Map) conMap.get(key);
				BigDecimal FPayAmount = (BigDecimal) amtMap.get("FPayAmount");
				BigDecimal FPayedAmt = (BigDecimal) amtMap.get("FPayedAmt");

				// �����keyֵ�ǵڼ���
				int col = (calCol(dateType, key, yearFrom, monthFrom) - 1) * 4 + 1;

				row.getCell(col + FIX_COL_LEN).setValue(FPayAmount);
				row.getCell(col + FIX_COL_LEN + 1).setValue(FPayedAmt);
				if (FPayAmount.compareTo(FDCConstants.ZERO) != 0) {
					BigDecimal temp = FDCHelper.divide(FPayedAmt, FPayAmount, 4, BigDecimal.ROUND_HALF_UP);
					row.getCell(col + FIX_COL_LEN + 2).setValue(temp.multiply(FDCHelper.ONE_HUNDRED).toString() + "%");
					if (temp.compareTo(FDCHelper.ZERO) == -1) {
						row.getCell(col + FIX_COL_LEN + 2).getStyleAttributes().setFontColor(Color.RED);
					}
					row.getCell(col + FIX_COL_LEN + 2).getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
				}
				BigDecimal res = FPayAmount.subtract(FPayedAmt);
				row.getCell(col + FIX_COL_LEN + 3).setValue(res);
				if (res.compareTo(FDCHelper.ZERO) == -1) {
					row.getCell(col + FIX_COL_LEN + 3).getStyleAttributes().setFontColor(Color.RED);
				}
			}
		}
	}

	/**
	 *  �����keyֵ�ǵڼ���
	 * @param dateType
	 * @param key
	 * @param yearFrom
	 * @param monthFrom
	 * @return
	 */
	private int calCol(int dateType, String key, int yearFrom, int monthFrom) {
		int col = 0;
		String dateKey = null;
		int monthAdd = 0;
		int yearAdd = 0;
		do {
			if (dateType == 1) {
				monthAdd = col % 12;
				yearAdd = col / 12;
				if (monthFrom + monthAdd > 12) {
					yearFrom++;
					dateKey = "" + (yearFrom + yearAdd)	+ (monthFrom + monthAdd - 12);
				} else {
					dateKey = "" + (yearFrom + yearAdd)	+ (monthFrom + monthAdd);
				}
			} else if (dateType == 2) {
				monthAdd = col % 4;
				yearAdd = col / 4;
				if (monthFrom + monthAdd > 4) {
					yearFrom++;
					dateKey = "" + (yearFrom + yearAdd)	+ (monthFrom + monthAdd - 12);
				} else {
					dateKey = "" + (yearFrom + yearAdd)	+ (monthFrom + monthAdd);
				}
			} else if (dateType == 3) {
				dateKey = "" + (yearFrom + col);
			}

			col++;
		} while (!key.equals(dateKey));

		return col;
	}

	/**
	 * Ϊ�����������ˢ�¹��� haiti_yang 2007-03-19
	 */
	public void checkSelected() {
		if (tblMain.getBody().size() == 0
				|| tblMain.getSelectManager().size() == 0) {
			MsgBox.showWarning(this, EASResource.getString(FrameWorkClientUtils.strResource + "Msg_MustSelected"));
			SysUtil.abort();
		}
	}
	
	private void fillOtherDatas(Map map) {
		
	}
	
	/**
	 *  ����ͬ
	 */
	private void fillContracts(CurProjectInfo project,
			ContractBillCollection contractBills, Map conExecuteData,
			Map payDataMap, int dateType, int yearFrom, int monthFrom) {
		IRow row;
		String projectName = null;

		for (int i = 0; i < contractBills.size(); i++) {
			row = tblMain.addRow();
			ContractBillInfo bill = contractBills.get(i);
			projectName = bill.getCurProject().getDisplayName();
			projectName = projectName.replaceAll("_", "\\\\");
			row.getCell("curProject.name").setValue(projectName);
			// ��ͬId,��������
			String contractid=bill.getId().toString();
			row.getCell("contract.id").setValue(contractid);
			row.getCell("contractType.name").setValue(bill.getContractType().getName());
			row.getCell("currency.name").setValue(bill.getCurrency().getName());
			row.getCell("contract.number").setValue(bill.getNumber());
			row.getCell("contract.name").setValue(bill.getName());
			row.getCell("contract.oriAmount").setValue(bill.getOriginalAmount());
			// ͳ��ԭ�ҽ��
			if (bill.getOriginalAmount() != null) {
				if (totalMap.containsKey("contract.oriAmount")) {
					BigDecimal oriAmount = (BigDecimal) totalMap.get("contract.oriAmount");
					totalMap.put("contract.oriAmount", oriAmount.add(bill.getOriginalAmount()));
				} else {
					totalMap.put("contract.oriAmount", bill.getOriginalAmount());
				}
			}
			row.getCell("contract.amount").setValue(bill.getAmount());
			// ͳ�Ʊ�λ�ҽ��
			if (bill.getAmount() != null) {
				if (totalMap.containsKey("contract.amount")) {
					BigDecimal amount = (BigDecimal) totalMap.get("contract.amount");
					totalMap.put("contract.amount", amount.add(bill.getAmount()));
				} else {
					totalMap.put("contract.amount", bill.getAmount());
				}
			}
			row.getCell("contract.lastPrice").setValue(bill.getSettleAmt());
				BigDecimal acctPayAmount=FDCHelper.toBigDecimal(payDataMap.get(contractid));
				/*
				 * ����ƻ�ִ�б�����������<p>
				 * δ����:��ͬ�������-�ú�ͬ���ۼ��Ѹ���<p>
				 * �ۼƸ������:�ú�ͬ���ۼ��Ѹ���/��ͬ�������*100%<p>
				 * �ѽ���:��ͬ�Ƿ������ս���<p>
				 * by Cassiel_peng 2009-11-16
				 */
				//δ������
				BigDecimal notPaidAmt=FDCHelper.toBigDecimal(FDCHelper.subtract(bill.getSettleAmt(), acctPayAmount),2);
				row.getCell("notPaid").setValue(notPaidAmt);
				//�ۼƸ������
				row.getCell("allPaidRate").setValue(FDCHelper.toBigDecimal(FDCHelper.divide(FDCHelper.multiply(acctPayAmount, FDCHelper.ONE_HUNDRED), bill.getSettleAmt()),2));
				//�ѽ���
				row.getCell("hasSettled").setValue(bill.isHasSettled()==false?Boolean.FALSE:Boolean.TRUE);

			//ͳ���������
			if (bill.getSettleAmt() != null) {
				if (totalMap.containsKey("contract.lastPrice")) {
					BigDecimal lastPrice = (BigDecimal) totalMap.get("contract.lastPrice");
					totalMap.put("contract.lastPrice", lastPrice.add(bill.getSettleAmt()));
				} else {
					totalMap.put("contract.lastPrice", bill.getSettleAmt());
				}
			}
			//ͳ��δ������
			if (bill.getSettleAmt() != null) {
				if (totalMap.containsKey("notPaid")) {
					BigDecimal notPaid = (BigDecimal) totalMap.get("notPaid");
					totalMap.put("notPaid", notPaid.add(notPaidAmt));
				} else {
					totalMap.put("notPaid", notPaidAmt);
				}
			}
			
			if (conExecuteData != null) {
				fillData(conExecuteData, row, contractid, dateType, yearFrom, monthFrom);
			}
		}
	}

	private Set set = null;

	protected void treeMain_valueChanged(TreeSelectionEvent e) throws Exception {
		DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeMain.getLastSelectedPathComponent();
		if (node == null || node.getUserObject() == null
				|| OrgF7InnerUtils.isTreeNodeDisable(node)) {
			return;
		}
		set = new HashSet();
		int count = node.getLeafCount();
		if (count > 0 && !node.isLeaf()) {
			while (count > 0 && node.getNextNode() != null) {
				DefaultKingdeeTreeNode nextNode = (DefaultKingdeeTreeNode) node
						.getNextNode();
				if (nextNode.getUserObject() instanceof CurProjectInfo) {
					CurProjectInfo projectInfo = (CurProjectInfo) nextNode
							.getUserObject();
					set.add(((IObjectValue) projectInfo).get("id"));
					count--;
				}
				node = (DefaultKingdeeTreeNode) node.getNextNode();
			}
		} else if (node.isLeaf() && node.getUserObject()!=null && (node.getUserObject() instanceof CurProjectInfo)) {
			CurProjectInfo projectInfo = (CurProjectInfo) node.getUserObject();
			set.add(((IObjectValue) projectInfo).get("id"));
		}
		fillTable(null);
	}

	protected void tblMain_tableClicked(KDTMouseEvent e) throws Exception {
	}

	protected void tblMain_tableSelectChanged(KDTSelectEvent e)
			throws Exception {
	}

	protected String getKeyFieldName() {
		return "contract.id";
	}

	/**
	 *  �Ƿ���ʾ�ϼ���
	 */
	protected boolean isFootVisible() {
		return true;
	}

	/**
	 *  ���Ӻϼ���
	 */
	protected IRow appendFootRow() {
		if (!this.isFootVisible()) {
			return null;
		}
		try {
			List sumList = new ArrayList();
			sumList.add("contract.oriAmount");
			sumList.add("contract.amount");
			sumList.add("contract.lastPrice");
			sumList.add("notPaid");
			if (sumList.size() > 0) {
				IRow footRow = null;
				KDTFootManager footRowManager = tblMain.getFootManager();
				if (footRowManager == null) {
					String total = EASResource
							.getString(FrameWorkClientUtils.strResource
									+ "Msg_Total");
					footRowManager = new KDTFootManager(this.tblMain);
					footRowManager.addFootView();
					this.tblMain.setFootManager(footRowManager);
					footRow = footRowManager.addFootRow(0);
					footRow.getStyleAttributes().setHorizontalAlign(HorizontalAlignment.getAlignment("right"));
					this.tblMain.getIndexColumn().setWidthAdjustMode(KDTIndexColumn.WIDTH_MANUAL);
					this.tblMain.getIndexColumn().setWidth(30);
					footRowManager.addIndexText(0, total);
				} else {
					footRow = footRowManager.getFootRow(0);
				}
				int columnCount = this.tblMain.getColumnCount();
				for (int i = 0; i < columnCount; i++) {
					String colName = this.tblMain.getColumn(i).getKey();
					for (int j = 0; j < sumList.size(); j++) {
						String fieldName = (String) sumList.get(j);
						if (colName.equalsIgnoreCase(fieldName)) {
							ICell cell = footRow.getCell(i);
							cell.getStyleAttributes().setNumberFormat(FDCHelper.strDataFormat);
							cell.getStyleAttributes().setHorizontalAlign(HorizontalAlignment.getAlignment("right"));
							cell.getStyleAttributes().setFontColor(Color.BLACK);
							cell.setValue(totalMap.get(fieldName));
						}
					}
				}
				footRow.getStyleAttributes().setBackground(
						new Color(0xf6, 0xf6, 0xbf));
				return footRow;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	protected String getEditUIName() {
		return null;
	}

	protected CommonQueryDialog initCommonQueryDialog() {
		dialog = super.initCommonQueryDialog();
		dialog.setShowFilter(false);
		dialog.setShowSorter(false);
		dialog.addUserPanel(getFilterUI());
		return dialog;
	}
}