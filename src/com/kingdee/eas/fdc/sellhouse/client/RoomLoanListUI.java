/**
 * output package name
 */
package com.kingdee.eas.fdc.sellhouse.client;

import java.awt.event.ActionEvent;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.swing.tree.TreeNode;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectManager;
import com.kingdee.bos.ctrl.kdf.table.KDTSortManager;
import com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent;
import com.kingdee.bos.ctrl.kdf.table.event.KDTSelectEvent;
import com.kingdee.bos.ctrl.kdf.table.util.KDTableUtil;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.dao.query.IQueryExecutor;
import com.kingdee.bos.metadata.IMetaDataPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.metadata.resource.BizEnumValueInfo;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.ItemAction;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.base.codingrule.CodingRuleException;
import com.kingdee.eas.base.codingrule.CodingRuleManagerFactory;
import com.kingdee.eas.base.codingrule.ICodingRuleManager;
import com.kingdee.eas.base.codingrule.client.CodingRuleIntermilNOBox;
import com.kingdee.eas.basedata.assistant.BankFactory;
import com.kingdee.eas.basedata.assistant.BankInfo;
import com.kingdee.eas.basedata.org.OrgConstants;
import com.kingdee.eas.basedata.org.OrgStructureInfo;
import com.kingdee.eas.basedata.org.SaleOrgUnitInfo;
import com.kingdee.eas.basedata.org.client.OrgInnerUtils;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basecrm.client.FDCSysContext;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.basedata.MoneySysTypeEnum;
import com.kingdee.eas.fdc.basedata.client.FDCClientHelper;
import com.kingdee.eas.fdc.basedata.client.FDCClientUtils;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.sellhouse.AFMmmTypeEnum;
import com.kingdee.eas.fdc.sellhouse.AFMortgagedApproachEntryCollection;
import com.kingdee.eas.fdc.sellhouse.AFMortgagedApproachEntryInfo;
import com.kingdee.eas.fdc.sellhouse.AFMortgagedDataEntryCollection;
import com.kingdee.eas.fdc.sellhouse.AFMortgagedDataEntryInfo;
import com.kingdee.eas.fdc.sellhouse.AFMortgagedFactory;
import com.kingdee.eas.fdc.sellhouse.AFMortgagedInfo;
import com.kingdee.eas.fdc.sellhouse.AFMortgagedStateEnum;
import com.kingdee.eas.fdc.sellhouse.BizListEntryCollection;
import com.kingdee.eas.fdc.sellhouse.BizListEntryInfo;
import com.kingdee.eas.fdc.sellhouse.BizNewFlowEnum;
import com.kingdee.eas.fdc.sellhouse.BuildingInfo;
import com.kingdee.eas.fdc.sellhouse.BuildingUnitInfo;
import com.kingdee.eas.fdc.sellhouse.IAFMortgaged;
import com.kingdee.eas.fdc.sellhouse.IRoom;
import com.kingdee.eas.fdc.sellhouse.IRoomLoan;
import com.kingdee.eas.fdc.sellhouse.MoneyDefineFactory;
import com.kingdee.eas.fdc.sellhouse.MoneyDefineInfo;
import com.kingdee.eas.fdc.sellhouse.MoneyTypeEnum;
import com.kingdee.eas.fdc.sellhouse.PurPayListEntryCollection;
import com.kingdee.eas.fdc.sellhouse.PurPayListEntryInfo;
import com.kingdee.eas.fdc.sellhouse.PurchaseManageFactory;
import com.kingdee.eas.fdc.sellhouse.PurchaseManageInfo;
import com.kingdee.eas.fdc.sellhouse.RoomACCFundStateEnum;
import com.kingdee.eas.fdc.sellhouse.RoomDisplaySetting;
import com.kingdee.eas.fdc.sellhouse.RoomFactory;
import com.kingdee.eas.fdc.sellhouse.RoomInfo;
import com.kingdee.eas.fdc.sellhouse.RoomLoanAFMEntrysCollection;
import com.kingdee.eas.fdc.sellhouse.RoomLoanAFMEntrysFactory;
import com.kingdee.eas.fdc.sellhouse.RoomLoanAFMEntrysInfo;
import com.kingdee.eas.fdc.sellhouse.RoomLoanCollection;
import com.kingdee.eas.fdc.sellhouse.RoomLoanFactory;
import com.kingdee.eas.fdc.sellhouse.RoomLoanInfo;
import com.kingdee.eas.fdc.sellhouse.RoomLoanStateEnum;
import com.kingdee.eas.fdc.sellhouse.RoomSellStateEnum;
import com.kingdee.eas.fdc.sellhouse.SHEManageHelper;
import com.kingdee.eas.fdc.sellhouse.SHEPayTypeFactory;
import com.kingdee.eas.fdc.sellhouse.SHEPayTypeInfo;
import com.kingdee.eas.fdc.sellhouse.SellProjectInfo;
import com.kingdee.eas.fdc.sellhouse.SignManageFactory;
import com.kingdee.eas.fdc.sellhouse.SignManageInfo;
import com.kingdee.eas.fdc.sellhouse.SignPayListEntryCollection;
import com.kingdee.eas.fdc.sellhouse.SignPayListEntryInfo;
import com.kingdee.eas.fdc.sellhouse.SubareaInfo;
import com.kingdee.eas.fdc.sellhouse.TransactionCollection;
import com.kingdee.eas.fdc.sellhouse.TransactionFactory;
import com.kingdee.eas.fdc.sellhouse.TransactionInfo;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.framework.ITreeBase;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.MsgBox;
import com.kingdee.jdbc.rowset.IRowSet;
import com.kingdee.util.DateTimeUtils;

/**
 * output class name
 */
public class RoomLoanListUI extends AbstractRoomLoanListUI {
	private static final Logger logger = CoreUIObject
			.getLogger(RoomLoanListUI.class);

	private boolean isAddNew = true;

	private int sortType = KDTSortManager.SORT_ASCEND;
	
	/**
	 * ������ϸid���ϣ��������зſ�
	 */
	private Set payEntryIdSet = new HashSet();

	protected static final BOSUuid splitBillNullID = BOSUuid.create("null");

	SaleOrgUnitInfo saleOrg = SHEHelper.getCurrentSaleOrg();

	RoomDisplaySetting roomSetting = new RoomDisplaySetting();
	
//	private List loanList = new ArrayList();
//	private List accList = new ArrayList();
	private Map customerMap = new HashMap();
	
	/**
	 * ����Ϊ����
	 */
//	private final int moneyTypeOfLoan = 0;
	
	/**
	 * ����Ϊ������
	 */
//	private final int moneyTypeOfAfm = 1;
	
	/**
	 * �������Ϊ���Һ͹�����
	 */
//	private final int moneyTypeOfLoanAndAfm = 2;
	
	/**
	 * ���ڱ��淿���Ӧ�Ŀ�����󣬱����ظ���ѯ
	 */
//	private HashMap moneyDefineMap = new HashMap();
	
	/**
	 * �����������ҵ���ʱ�������Ϲ�������ǩԼ��
	 */
//	private HashMap billObjectMap = new HashMap();

	public RoomLoanListUI() throws Exception {
		super();
	}

	public void storeFields() {
		super.storeFields();
	}

	protected void tblMain_tableSelectChanged(KDTSelectEvent e)
			throws Exception {
		actionView.setEnabled(true);
	}

	/**
	 * ����ѡ������ڵ㣬���Ӳ�ѯ����
	 * @return
	 */
	public FilterInfo newFilterInfo() {
		DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeMain.getLastSelectedPathComponent();
		if (node == null) {
			return null;
		}
		FilterInfo filter = new FilterInfo();
		if (node.getUserObject() instanceof Integer) { // ������
			Integer unit = (Integer) node.getUserObject();
			BuildingInfo building = (BuildingInfo) ((DefaultKingdeeTreeNode) node.getParent()).getUserObject();
			String buildingId = building.getId().toString();
			filter.getFilterItems().add(new FilterItemInfo("room.building.id", buildingId));
			filter.getFilterItems().add(new FilterItemInfo("room.unit", unit));

		} else if (node.getUserObject() instanceof BuildingUnitInfo) {// ��Ԫ
			BuildingUnitInfo buildUnit = (BuildingUnitInfo) node.getUserObject();
			BuildingInfo building = (BuildingInfo) ((DefaultKingdeeTreeNode) node.getParent()).getUserObject();
			String buildingId = building.getId().toString();
			filter.getFilterItems().add(new FilterItemInfo("room.building.id", buildingId));
			filter.getFilterItems().add(new FilterItemInfo("room.buildUnit.id", buildUnit.getId().toString()));

		} else if (node.getUserObject() instanceof BuildingInfo) {// ¥��
			BuildingInfo building = (BuildingInfo) node.getUserObject();
			String buildingId = building.getId().toString();
			filter.getFilterItems().add(new FilterItemInfo("room.building.id", buildingId));
		} else if (node.getUserObject() instanceof SubareaInfo) { // ����
			SubareaInfo subarea = (SubareaInfo) node.getUserObject();
			filter.getFilterItems().add(new FilterItemInfo("room.building.subarea.id", subarea.getId().toString()));
		} else if (node.getUserObject() instanceof SellProjectInfo) { // ������Ŀ
			SellProjectInfo sellProject = (SellProjectInfo) node.getUserObject();
			filter.getFilterItems().add(new FilterItemInfo("room.building.sellProject.id", sellProject.getId().toString()));
		} else if (node.getUserObject() instanceof OrgStructureInfo) {
			Map sellProMap = FDCTreeHelper.getAllObjectIdMap(node, "SellProject");
			Iterator iter = sellProMap.keySet().iterator();
			Set sellProIdSet = new HashSet();
			while (iter.hasNext())
				sellProIdSet.add(iter.next());
			if (sellProIdSet.size() > 0)
				filter.getFilterItems().add(new FilterItemInfo("room.building.sellProject.id", sellProIdSet, CompareType.INCLUDE));
			else
				filter.getFilterItems().add(new FilterItemInfo("room.building.sellProject.id", null));
		}
		
		return filter;
	}

	public void actionLocate_actionPerformed(ActionEvent e) throws Exception {
		super.actionLocate_actionPerformed(e);
	}

	protected String[] getLocateNames() {
		String[] s = new String[] { "forecast" };
		return s;
	}

	protected void treeMain_valueChanged(javax.swing.event.TreeSelectionEvent e)
			throws Exception {
		DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeMain.getLastSelectedPathComponent();
		if (node == null || node.getUserObject() instanceof OrgStructureInfo) {
			this.tblMain.removeRows();
			
			this.actionLoanProcess.setEnabled(false);
			
			return;
		}
		
		if (FDCSysContext.getInstance().checkIsSHEOrg()){
			this.actionLoanProcess.setEnabled(true);
		}
		
		this.execQuery();
		
		actionView.setEnabled(true);
	}

	protected String getEditUIName() {
		return RoomLoanEditUI.class.getName();
	}

	protected void initTree() throws Exception {
		this.treeMain.setModel(FDCTreeHelper.getSellProjectTreeForSHE(this.actionOnLoad,MoneySysTypeEnum.SalehouseSys));
		this.treeMain.setShowsRootHandles(true);
		this.treeMain.expandAllNodes(true, (TreeNode) this.treeMain.getModel().getRoot());
	}

	public void onLoad() throws Exception {
		super.onLoad();
		
		tblMain.getSelectManager().setSelectMode(KDTSelectManager.MULTIPLE_ROW_SELECT);
		SimpleKDTSortManager.setTableSortable(tblMain);
		
		this.btnBankLoan.setIcon(EASResource.getIcon("imgTbtn_gathering"));
		this.btnLoanProcess.setIcon(EASResource.getIcon("imgTbtn_autocollate"));
		this.btnUpdateLoanAmount.setIcon(EASResource.getIcon("imgTbtn_handworkcollate"));
		
		FDCClientHelper.setActionEnable(new ItemAction[] { actionAddNew }, false);
		if (!FDCSysContext.getInstance().checkIsSHEOrg()) {
			FDCClientHelper.setActionEnableAndNotSetVisible(
					new ItemAction[] { actionEdit, actionBatchLoan, actionBatchReceiveBill, actionLoanProcess,
							actionStopTransact, actionCalling, actionBankLoan, actionUpdateLoanAmount }, false);
		} else {
			FDCClientHelper.setActionEnableAndNotSetVisible(
					new ItemAction[] { actionEdit, actionBatchLoan, actionBatchReceiveBill, actionLoanProcess,
							actionStopTransact, actionCalling, actionBankLoan, actionUpdateLoanAmount }, true);
		}
		
		actionView.setEnabled(true);
		btnBatchLoan.setVisible(false);
		actionBatchLoan.setVisible(false);
		actionLocate.setVisible(false);
		
		this.actionRemove.setVisible(true);
		this.btnRemove.setEnabled(true);
		this.actionBatchReceiveBill.setVisible(false);
		
		
	}

	public void checkBatchAFM(IRow row) throws BOSException, EASBizException {
		String roomID = null;
		roomID = row.getCell("id").getValue().toString();
		if (row != null && roomID != null) {
			IRoom ir = RoomFactory.getRemoteInstance();
			SelectorItemCollection roomSic = new SelectorItemCollection();
			roomSic.add("id");
			roomSic.add("name");
			roomSic.add("number");
			roomSic.add("displayName");
			roomSic.add("building.id");
			roomSic.add("building.name");
			roomSic.add("building.number");
			roomSic.add("building.sellProject.id");
			roomSic.add("building.sellProject.name");
			roomSic.add("building.sellProject.number");
			RoomInfo room = ir.getRoomInfo(new ObjectUuidPK(roomID), roomSic);
			SellProjectInfo spInfo = room.getBuilding().getSellProject();
			EntityViewInfo view = new EntityViewInfo();
			FilterInfo filter = new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("project", spInfo.getId()));
			filter.getFilterItems().add(new FilterItemInfo("isEnabled", "1"));
			String biz = row.getCell("mmTypeId").getValue().toString();
			MoneyDefineInfo moneyInfo = MoneyDefineFactory.getRemoteInstance().getMoneyDefineInfo(new ObjectUuidPK(biz));
			if (moneyInfo.getMoneyType().getValue().equals(AFMmmTypeEnum.LOANAMOUNT_VALUE)) {
				filter.getFilterItems().add(new FilterItemInfo("afmType", AFMmmTypeEnum.LOANAMOUNT_VALUE));
			} else {
				filter.getFilterItems().add(new FilterItemInfo("afmType", AFMmmTypeEnum.ACCFUNDAMOUNT_VALUE));
			}
			SelectorItemCollection sic = new SelectorItemCollection();
			sic.add("id");
			sic.add("name");
			sic.add("number");
			sic.add("isEnabled");
			sic.add("ApproachEntrys.id");
			sic.add("ApproachEntrys.name");
			sic.add("ApproachEntrys.number");
			sic.add("ApproachEntrys.remark");
			sic.add("DataEntrys.id");
			sic.add("DataEntrys.name");
			sic.add("DataEntrys.number");
			sic.add("DataEntrys.remark");
			view.setFilter(filter);
			view.setSelector(sic);
			IAFMortgaged iafm = AFMortgagedFactory.getRemoteInstance();
			AFMortgagedInfo afmInfo = iafm.getAFMortgagedCollection(view).get(0);
			if (afmInfo == null) {
				MsgBox.showInfo(this, "����Ŀδ���ù�����/���ҷ������޷��޸ģ�");
				this.abort();
			}
		}
	}

	public boolean checkAFM() {
		return true;
	}

	public void checkBatchLoanEdit(IRow row) {
		BizEnumValueInfo state = null;
		if (row.getCell("loanState").getValue() != null) {
			state = (BizEnumValueInfo) row.getCell("loanState").getValue();
			if (!state.getName().equals(AFMortgagedStateEnum.UNTRANSACT.getName())
					&& !state.getName().equals(AFMortgagedStateEnum.TRANSACTING.getName())
					&& !state.getName().equals(AFMortgagedStateEnum.TRANSACTED.getName())) {
				MsgBox.showInfo(this, "��ѡ����ǰ��������У��޷������޸ģ���");
				abort();
			}
			if (state.getName().equals(AFMortgagedStateEnum.STOPTRANSACT.getName())) {
				MsgBox.showInfo(this, "��ѡ�����ֹ���ֹ�����޷������޸ģ���");
				abort();
			}
			if (state.getName().equals(AFMortgagedStateEnum.CHANGECALL.getName())) {
				MsgBox.showInfo(this, "��ѡ������ת�У��޷������޸ģ�");
				abort();
			}
			if (state.getName().equals(AFMortgagedStateEnum.CANCELROOM.getName())) {
				MsgBox.showInfo(this, "��ѡ�����˷���ֹ���޷������޸ģ�");
				abort();
			}
			if (state.getName().equals(AFMortgagedStateEnum.CHANGEROOM.getName())) {
				MsgBox.showInfo(this, "��ѡ���任����ֹ���޷������޸ģ�");
				abort();
			}
		}
	}

	public boolean checkEditAndRemove(String str) throws BOSException,
			SQLException {
		IRow row = null;
		BizEnumValueInfo state = null;
		row = KDTableUtil.getSelectedRow(tblMain);
		if (row.getCell("loanState").getValue() != null) {
			state = (BizEnumValueInfo) row.getCell("loanState").getValue();
		}
		/*if (str.equals("�޸�")) {
			Set purIDSet = new HashSet();
			String purID = ((BOSUuid) row.getCell("unpurchaseID").getValue()).toString();
			purIDSet.add(purID);
			SellProjectInfo project = (SellProjectInfo) row.getCell("unItem").getValue();
			if (SHEHelper.isCanEdit(purIDSet, project.getId().toString(), 
					(MoneyTypeEnum) row.getCell("unmmType").getValue(), roomSetting)) {
				MsgBox.showInfo("��ѡ�ķ����Ѿ���ȡ" + (MoneyTypeEnum) row.getCell("unmmType").getValue() + "����" + str + "!");
				this.abort();
			}
		}*/
		if (state != null) {
			if (state.getName().equals(AFMortgagedStateEnum.STOPTRANSACT.getName())) {
				MsgBox.showInfo(this, "�ֹ���ֹ�����޷�" + str + "��");
				return false;
			}
			if (state.getName().equals(AFMortgagedStateEnum.CHANGECALL.getName())) {
				MsgBox.showInfo(this, "��ת�У��޷�" + str + "��");
				return false;
			}
			if (state.getName().equals(AFMortgagedStateEnum.CANCELROOM.getName())) {
				MsgBox.showInfo(this, "�˷���ֹ���޷�" + str + "��");
				return false;
			}
			if (state.getName().equals(AFMortgagedStateEnum.CHANGEROOM.getName())) {
				MsgBox.showInfo(this, "������ֹ���޷�" + str + "��");
				return false;
			}
			if (state.getName().equals(AFMortgagedStateEnum.BANKFUND.getName())) {
				MsgBox.showInfo(this, "���зſ�޷�" + str + "��");
				return false;
			}
		}
		return true;
	}

	public Set getLoanRoomsID() {
		Set list = new HashSet();
		for (int i = 0; i < tblMain.getRowCount(); i++) {
			if ("���ҿ�".equals(tblMain.getRow(i).getCell("afmType").getValue())) {
				list.add(tblMain.getRow(i).getCell("id").getValue());
			}
		}
		return list;
	}

	public Set getFundRoomsID() {
		Set list = new HashSet();
		for (int i = 0; i < tblMain.getRowCount(); i++) {
			if ("������".equals(tblMain.getRow(i).getCell("afmType").getValue())) {
				list.add(tblMain.getRow(i).getCell("id").getValue());
			}
		}
		return list;
	}

	public List getBatchLoanList(int[] rowsID) throws Exception {
		List list = new ArrayList();
		for (int i = 0; i < rowsID.length; i++) {
			if (tblMain.getRow(rowsID[i]).getCell("id").getValue() == null)
				continue;
			String roomLoanId = tblMain.getRow(rowsID[i]).getCell("id").getValue().toString();
			IRoomLoan roomLoan = (IRoomLoan) getBizInterface();
			RoomLoanInfo roomLoanInfo = roomLoan
					.getRoomLoanInfo(new ObjectUuidPK(roomLoanId));
			list.add(roomLoanInfo);
		}
		return list;
	}

	private void batchLoan(List idList) throws Exception {
		UIContext uiContext = new UIContext(this);
		uiContext.put("roomLoanIdList", idList);
		IRow row = null;
		uiContext.put("state", "done");
		
		int[] rows = KDTableUtil.getSelectedRows(tblMain);
		row = tblMain.getRow(rows[0]);
		uiContext.put("sellProjectId", row.getCell("sellProjectId").getValue().toString());
		if(row.getCell("afmId").getValue() != null){
			uiContext.put("afmId", row.getCell("afmId").getValue().toString());
		}
		uiContext.put("mmTypeId", row.getCell("mmTypeId").getValue().toString());
		
		List roomloans = getBatchLoanList(rows);
		RoomLoanInfo roomloan = (RoomLoanInfo) roomloans.get(0);
		if (roomloan.isIsEditData()) {
			uiContext.put("isEditData", Boolean.TRUE);
		}
		List roomLoanIds = new ArrayList();
		roomLoanIds.add(row.getCell("id").getValue());
		List steps = new ArrayList();
		steps.add(row.getCell("curProcess").getValue());
		
		checkBatchLoanEdit(row);
		
		for (int i = 1; i < rows.length; i++) {
			checkBatchLoanEdit(tblMain.getRow(rows[i]));
			if (!row.getCell("sellProjectId").getValue().toString()
					.equals(tblMain.getRow(rows[i]).getCell("sellProjectId").getValue().toString())) {
				MsgBox.showInfo("��ѡ�ķ��䲻����ͬһ����Ŀ��");
				abort();
			}
			if(!row.getCell("afmType").getValue().toString().equals(tblMain.getRow(rows[i]).getCell("afmType").getValue().toString())){
				MsgBox.showInfo("��ѡ�ķ��䲻��ͬһ�����ҿ��");
				abort();
			}
			roomLoanIds.add(tblMain.getRow(rows[i]).getCell("id").getValue());
			steps.add(tblMain.getRow(rows[i]).getCell("curProcess").getValue());
		}

		uiContext.put("sellProject", row.getCell("sellProjectId").getValue().toString());
		uiContext.put("afmType", row.getCell("afmType").getValue().toString());
		uiContext.put("roomLoan.id", row.getCell("id").getValue());
		uiContext.put("roomLoanIds", roomLoanIds);
		uiContext.put("roomId", row.getCell("roomId").getValue());
		uiContext.put("steps", steps);

		IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.MODEL)
			.create(RoomLoanBatchEditUI.class.getName(), uiContext, null, "ADDNEW");
		uiWindow.show();
	}

	private List getselectedKeyList() {
		List list = new ArrayList();
		int[] rows = KDTableUtil.getSelectedRows(tblMain);
		for (int i = 0; i < rows.length; i++) {
			IRow row = tblMain.getRow(rows[i]);
			list.add(row.getCell("id").getValue().toString());
		}
		return list;
	}

	public void actionEdit_actionPerformed(ActionEvent e) throws Exception {
		checkSelected();
		List idList = (List) getselectedKeyList();
		if (idList.size() > 1) {
			batchLoan(idList);
		} else if (checkAFM()) {
			if (checkEditAndRemove("�޸�")) {
				isAddNew = false;
				super.actionEdit_actionPerformed(e);
			}
		}
	}

	protected String getEditUIModal() {
		return UIFactoryName.NEWTAB;
	}

	protected ICoreBase getBizInterface() throws Exception {
		return RoomLoanFactory.getRemoteInstance();
	}

	protected void prepareUIContext(UIContext uiContext, ActionEvent e) {
		ItemAction act = getActionFromActionEvent(e);
		if (act.equals(actionAddNew) || act.equals(actionEdit)
				|| (act.equals(actionView))) {
			IRow row = null;
			row = KDTableUtil.getSelectedRow(tblMain);
			uiContext.put("ID", row.getCell("id").getValue());
			uiContext.put("roomId", row.getCell("roomId").getValue());
			uiContext.put("sellProjectId", row.getCell("sellProjectId").getValue());
			uiContext.put("mmTypeId", row.getCell("mmTypeId").getValue());
			uiContext.put("done", "done");
		}
	}

	protected ITreeBase getTreeInterface() throws Exception {
		return null;
	}

	public void actionBatchLoan_actionPerformed(ActionEvent e) throws Exception {
		checkSelected();
		boolean isAllow = checkSelect();
		if (isAllow) {
			if (MsgBox.showConfirm2New(this,
					"����ѡ��¼�д����Ѱ����ҵļ�¼����������������������ά������Щ�ֶΣ��Ƿ������") == MsgBox.NO) {
				return;
			}
		}
		isAddNew = true;
		List idList = (List) getSelectedIdValues();
		UIContext uiContext = new UIContext(this);
		uiContext.put("idList", idList);
		// ����UI������ʾ
		IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.NEWTAB)
				.create(RoomLoanBatchEditUI.class.getName(), uiContext, null, "ADDNEW");
		uiWindow.show();
	}

	protected boolean checkSelect() throws EASBizException, BOSException,
			Exception {
		int[] selectRows = KDTableUtil.getSelectedRows(this.tblMain);
		boolean isAllow = false;
		for (int i = 0, size = selectRows.length; i < size; i++) {
			int rowIndex = selectRows[i];
			IRow row = tblMain.getRow(rowIndex);
			if (row == null) {
				MsgBox.showInfo(this, "û��ѡ���У�");
				SysUtil.abort();
			}
			Object valueCom = row.getCell("loanState").getValue();
			if (valueCom != null && valueCom.toString().equals(RoomLoanStateEnum.LOANED.toString())) {
				isAllow = true;
				break;
			}
		}
		return isAllow;
	}

/*	protected String getKeyFieldName() {
		if (isAddNew)
			return super.getKeyFieldName();
		else
			return "roomLoan.id";
	}

	protected String getSelectedKeyValue() {
		String keyValue = "";
		IRow row = KDTableUtil.getSelectedRow(tblMain);
		if (row != null) {
			if (row.getCell("id").getValue() != null) {
				keyValue = row.getCell("id").getValue().toString();
			}
		}
		return keyValue;
	}*/

	/*protected String getSelectedCostBillID() {
		int selectIndex = -1;
		int[] selectRows = KDTableUtil.getSelectedRows(this.tblMain);
		if (selectRows.length > 0) {
			int rowIndex = selectRows[0];
			IRow row = tblMain.getRow(rowIndex);
			if (row == null) {
				return null;
			}

			selectIndex = rowIndex;
			ICell cell = row.getCell("id");

			if (cell == null) {
				MsgBox.showError(EASResource
						.getString(FrameWorkClientUtils.strResource
								+ "Error_KeyField_Fail"));
				SysUtil.abort();
			}

			Object keyValue = cell.getValue();

			if (keyValue != null) {
				return keyValue.toString();
			}
		}

		return null;
	}*/

	protected void refresh(ActionEvent e) throws Exception {
		treeMain_valueChanged(null);
	}

	public void actionView_actionPerformed(ActionEvent e) throws Exception {
		checkSelected();
		isAddNew = false;
		super.actionView_actionPerformed(e);
	}

	// �õ��Զ�����
	protected String getAutoCode(IObjectValue objValue) throws EASBizException,
			BOSException {
		String companyId = OrgInnerUtils.getCurCompany();
		ICodingRuleManager codeRuleMgr = null;

		codeRuleMgr = CodingRuleManagerFactory.getRemoteInstance();
		if (codeRuleMgr.isUseIntermitNumber(objValue, companyId)) {
			return codeRuleMgr.readNumber(objValue, companyId);
		} else {
			return codeRuleMgr.getNumber(objValue, companyId);
		}

	}

	// �Ƿ���ڱ������
	protected boolean isCodeRuleEnable(IObjectValue objValue)
			throws EASBizException, BOSException {
		String companyId = OrgInnerUtils.getCurCompany();
		ICodingRuleManager codeRuleMgr = null;
		codeRuleMgr = CodingRuleManagerFactory.getRemoteInstance();
		return codeRuleMgr.isExist(objValue, companyId);
	}
	
	public void actionLoanProcess_actionPerformed(ActionEvent e)
			throws Exception {
		// ����UI������ʾ
		int comfim = MsgBox.NO;
		SellProjectInfo sellProject  = null;
		
		DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeMain
				.getLastSelectedPathComponent();
		if (node != null) {
			if(node.getChildCount() > 0){
				FDCMsgBox.showWarning(this, "��ĩ����Ŀ�������ɰ��ҷ���!");
				this.abort();
			}
			if (node.getUserObject() instanceof SellProjectInfo) {
				sellProject = (SellProjectInfo) node
						.getUserObject();

			} else {
				FDCMsgBox.showWarning(this, "����ѡ����Ŀ!");
				this.abort();
			}
		} else {
			FDCMsgBox.showWarning(this, "����ѡ����Ŀ!");
			this.abort();
		}
		comfim = MsgBox.showConfirm2New(this, "�Ƿ�����"+sellProject.getName().toString()+"������ǩԼ����İ��ҷ����¼?");
		
		if (comfim == MsgBox.YES) {
			creataNewLoanRecoard(sellProject.getId().toString());
			refresh(null);
		}else{
//			UIContext uiContext = new UIContext(this);
//			uiContext.put("selectProject", sellProject);
//			IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.NEWTAB).create(RoomLoanProcessUI.class.getName(), 
//					uiContext, null, OprtState.VIEW);
//			uiWindow.show();
			return;
		}
	}

	private void creataNewLoanRecoard(String projectId) {
//		this.loanList.clear();
//		this.accList.clear();
		try{
//			IRowSet rs = RoomLoanFactory.getRemoteInstance().getRoomList(projectId);
//			while(rs.next()){
//				if (rs.getString("moneyType") != null) {
//					if(rs.getString("moneyType").equals("LoanAmount")){
//						if (rs.getString("roomId") != null) {
//							if(!this.loanList.contains(rs.getString("roomId") )){
//								this.loanList.add(rs.getString("roomId"));
//							}
//						}
//					}else if(rs.getString("moneyType").equals("AccFundAmount")){
//						if (rs.getString("roomId") != null) {
//							if(!this.accList.contains(rs.getString("roomId") )){
//								this.accList.add(rs.getString("roomId"));
//							}
//						}
//					}
//				}
//			}
//			logger.error("create loan servier: the loanList's size is :"+this.loanList.size());
//			if(this.loanList!=null && this.loanList.size()>0){
//				createLoanRecord(this.loanList);
//			}
//			logger.error("create loan servier: the accList's size is :"+this.accList.size());
//			if(this.accList!=null && this.accList.size()>0){
//				createAccRecord(this.accList);
//			}
			FDCSQLBuilder _builder = new FDCSQLBuilder();
			_builder.appendSql(" select md.fid mdId,md.fmoneyType mdType,sum(payEntry.fappAmount) amount,sign.fid signId,sign.froomId roomId,sign.floanBank loanBankId,sign.facfBank acfBankId,sign.fpayTypeId payTypeId from t_she_signManage sign ");
			_builder.appendSql(" left join T_SHE_SignPayListEntry payEntry on payEntry.fheadId=sign.fid left join t_she_moneyDefine md on payEntry.fmoneyDefineId=md.fid where sign.fbizState in ('SignApply','SignAudit') ");
			_builder.appendSql(" and sign.fsellProjectId='"+projectId+"' and md.fmoneyType in('LoanAmount','AccFundAmount') ");
			_builder.appendSql(" and not exists(select roomloan.fsignId,moneyType.fmoneyType from T_SHE_RoomLoan roomloan left join T_SHE_MoneyDefine moneyType on roomloan.FMmType = moneyType.fid where roomloan.fafmortgagedState in ('0', '1', '3', '8') and sign.fid=roomloan.fsignId and md.fmoneyType=moneyType.fmoneyType)");
			_builder.appendSql(" group by md.fid,md.fmoneyType,sign.fid,sign.froomId,sign.floanBank,sign.facfBank,sign.fpayTypeId ");
			IRowSet rowSet = _builder.executeQuery();
			while(rowSet.next()){
				String roomId=rowSet.getString("roomId");
				String mdId=rowSet.getString("mdId");
				String signId=rowSet.getString("signId");
				String loanBankId=rowSet.getString("loanBankId");
				String acfBankId=rowSet.getString("acfBankId");
				String payTypeId=rowSet.getString("payTypeId");
				RoomLoanInfo roomLoan = new RoomLoanInfo();
				
				RoomInfo room = new RoomInfo();
				room.setId(BOSUuid.read(roomId));
				roomLoan.setRoom(room);
				
				SignManageInfo sign=new SignManageInfo();
				sign.setId(BOSUuid.read(signId));
				
				SHEPayTypeInfo payType=new SHEPayTypeInfo();
				payType.setId(BOSUuid.read(payTypeId));
				sign.setPayType(payType);
				
				roomLoan.setSign(sign);
				
				String number ="";
				number = createNumber();
				if(number!=null && !"".equals(number)){
					roomLoan.setNumber(number);
				}else{
					FDCMsgBox.showInfo("�����ñ������");
					SysUtil.abort();
				}
				roomLoan.setAFMortgagedState(AFMortgagedStateEnum.UNTRANSACT);
				roomLoan.setCreator(SysContext.getSysContext().getCurrentUserInfo());
				roomLoan.setActualLoanAmt(rowSet.getBigDecimal("amount"));
				if(rowSet.getString("mdType").equals("LoanAmount")){
					if(loanBankId!=null){
						BankInfo bank=new BankInfo();
						bank.setId(BOSUuid.read(loanBankId));
						roomLoan.setLoanBank(bank);
					}
					
					MoneyDefineInfo md=new MoneyDefineInfo();
					md.setId(BOSUuid.read(mdId));
					md.setMoneyType(MoneyTypeEnum.LoanAmount);
					roomLoan.setMmType(md);
					
					//��ʼ��������Ϣ
					this.initLoanData(roomLoan);
					
					//����ҵ��������Ӧ�ķ���
					SHEManageHelper.updateTransactionOverView(null, roomLoan.getRoom(), SHEManageHelper.MORTGAGE,roomLoan.getPromiseDate(), null, false);
					RoomLoanFactory.getRemoteInstance().save(roomLoan);
				}else{
					if(acfBankId!=null){
						BankInfo bank=new BankInfo();
						bank.setId(BOSUuid.read(acfBankId));
						roomLoan.setLoanBank(bank);
					}
					
					MoneyDefineInfo md=new MoneyDefineInfo();
					md.setId(BOSUuid.read(mdId));
					md.setMoneyType(MoneyTypeEnum.AccFundAmount);
					roomLoan.setMmType(md);
					
					//��ʼ��������Ϣ
					this.initLoanData(roomLoan);
					
					SHEManageHelper.updateTransactionOverView(null, roomLoan.getRoom(), SHEManageHelper.ACCFUND, roomLoan.getPromiseDate(), null, false);
					RoomLoanFactory.getRemoteInstance().save(roomLoan);
				}
			}
			FDCMsgBox.showInfo("�����ɹ�!");
			return;
		}catch(Exception ex){
			logger.error(ex.getMessage()+"�������ɰ��ҷ��񵥾�ʧ��!");
		}
	}

	private TransactionInfo getTransactionInfo(String roomId) throws BOSException{
		EntityViewInfo view = new EntityViewInfo();
		view.getSelector().add("tranBusinessOverView.*");
		view.getSelector().add("tranBusinessOverView.moneyDefine.*");
		view.getSelector().add("currentLink");
		view.getSelector().add("billId");
		
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("room.id", roomId,CompareType.EQUALS));
		filter.getFilterItems().add(new FilterItemInfo("isValid", new Boolean(false)));
		filter.getFilterItems().add(new FilterItemInfo("currentLink", RoomSellStateEnum.SIGN_VALUE));
		view.setFilter(filter);
		TransactionCollection transactionCol = TransactionFactory.getRemoteInstance().getTransactionCollection(view);
		if(transactionCol!=null && !transactionCol.isEmpty()){
			return transactionCol.get(0);
		}
		else{
			return null;
		}
	}
	
	/**
	 * ��鷿���Ϲ���ǩԼ�Ŀ���ж��Ƿ���Ҫ�������Ű��ҵ���
	 * @param room
	 * @return 0 - ���ң�1 - ������ 2 - ����
	 * @throws BOSException 
	 * @throws EASBizException 
	 */
//	private int checkRoomMoneyType(String roomId) throws BOSException, EASBizException{
//		this.moneyDefineMap.clear();
//		int retMoneyType = -1;
//		
//		TransactionInfo transactionInfo = this.getTransactionInfo(roomId);
//		if(transactionInfo != null){
//			if(transactionInfo.getCurrentLink()!=null && (transactionInfo.getCurrentLink().equals(RoomSellStateEnum.Purchase)
//					|| transactionInfo.getCurrentLink().equals(RoomSellStateEnum.Sign))){  //�Ϲ���ǩԼ״̬
//				//�����Ϲ�����ǩԼ��
//				if(transactionInfo.getCurrentLink().equals(RoomSellStateEnum.Sign)){  //�ݴ�ǩԼ��
//					SignManageInfo signInfo = SignManageFactory.getRemoteInstance()
//						.getSignManageInfo(new ObjectUuidPK(transactionInfo.getBillId().toString()));
//					this.billObjectMap.put("sign", signInfo);
//					
//					SignPayListEntryCollection payListEntry = this.getSignPayEntry(transactionInfo.getBillId().toString());
//					if(payListEntry!=null && !payListEntry.isEmpty()){  //��鸶����ϸ���Ƿ�������һ򹫻������
//						for(int i=0; i<payListEntry.size(); i++){
//							SignPayListEntryInfo payListInfo = payListEntry.get(i);
//							if(payListInfo.getMoneyDefine()!=null) {
//								if(payListInfo.getMoneyDefine().getMoneyType().equals(MoneyTypeEnum.LoanAmount)){
//									moneyDefineMap.put(new Integer(this.moneyTypeOfLoan), payListInfo.getMoneyDefine());
//									if(retMoneyType == this.moneyTypeOfAfm){  //���а��ң����й�����
//										return this.moneyTypeOfLoanAndAfm;
//									}
//									else{  //����
//										retMoneyType = this.moneyTypeOfLoan;
//									}
//								}
//								else if(payListInfo.getMoneyDefine().getMoneyType().equals(MoneyTypeEnum.AccFundAmount)){
//									moneyDefineMap.put(new Integer(this.moneyTypeOfAfm), payListInfo.getMoneyDefine());
//									if(retMoneyType == this.moneyTypeOfLoan){  //���а��ң����й�����
//										return this.moneyTypeOfLoanAndAfm;
//									}
//									else{  //������
//										retMoneyType = this.moneyTypeOfAfm;
//									}
//								}
//							}
//						}
//					}
//				}
//				else if(transactionInfo.getCurrentLink().equals(RoomSellStateEnum.Purchase)){  //�ݴ��Ϲ���
//					PurchaseManageInfo purInfo = PurchaseManageFactory.getRemoteInstance()
//						.getPurchaseManageInfo(new ObjectUuidPK(transactionInfo.getBillId().toString()));
//					this.billObjectMap.put("purchase", purInfo);
//					PurPayListEntryCollection payListEntry = this.getPurchasePayEntry(transactionInfo.getBillId().toString());
//					if(payListEntry!=null && !payListEntry.isEmpty()){  //��鸶����ϸ���Ƿ�������һ򹫻������
//						for(int i=0; i<payListEntry.size(); i++){
//							PurPayListEntryInfo payListInfo = payListEntry.get(i);
//							if(payListInfo.getMoneyDefine()!=null) {
//								if(payListInfo.getMoneyDefine().getMoneyType().equals(MoneyTypeEnum.LoanAmount)){
//									moneyDefineMap.put(new Integer(this.moneyTypeOfLoan), payListInfo.getMoneyDefine());
//									if(retMoneyType == this.moneyTypeOfAfm){  //���а��ң����й�����
//										return this.moneyTypeOfLoanAndAfm;
//									}
//									else{  //����
//										retMoneyType = this.moneyTypeOfLoan;
//									}
//								}
//								else if(payListInfo.getMoneyDefine().getMoneyType().equals(MoneyTypeEnum.AccFundAmount)){
//									moneyDefineMap.put(new Integer(this.moneyTypeOfAfm), payListInfo.getMoneyDefine());
//									if(retMoneyType == this.moneyTypeOfLoan){  //���а��ң����й�����
//										return this.moneyTypeOfLoanAndAfm;
//									}
//									else{  //������
//										retMoneyType = this.moneyTypeOfAfm;
//									}
//								}
//							}
//						}
//					}
//				}
//			}
//		}
//		
//		return retMoneyType;
//	}
	
	/**
	 * �����Ϲ���id����ȡ������ϸ
	 * @param billId
	 * @return
	 * @throws BOSException 
	 * @throws EASBizException 
	 * @throws BOSException 
	 */
	private PurPayListEntryCollection getPurchasePayEntry(String billId) throws EASBizException, BOSException{
		PurPayListEntryCollection entryCol = null;
		
		SelectorItemCollection selCol = new SelectorItemCollection();
		selCol.add("purPayListEntry.moneyDefine.moneyType");
		
		PurchaseManageInfo purInfo = PurchaseManageFactory.getRemoteInstance()
			.getPurchaseManageInfo(new ObjectUuidPK(billId), selCol);
		if(purInfo.getId() != null){
			entryCol = purInfo.getPurPayListEntry();
		}
		
		return entryCol;
	}
	
	/**
	 * ����ǩԼ��id����ȡ������ϸ
	 * @param billId
	 * @return
	 * @throws BOSException 
	 * @throws EASBizException 
	 */
	private SignPayListEntryCollection getSignPayEntry(String billId) throws EASBizException, BOSException{
		SignPayListEntryCollection entryCol = null;
		
		SelectorItemCollection selCol = new SelectorItemCollection();
		selCol.add("signPayListEntry.moneyDefine.moneyType");
		
		SignManageInfo signInfo = SignManageFactory.getRemoteInstance().getSignManageInfo(new ObjectUuidPK(billId), selCol);
		if(signInfo != null){
			entryCol = signInfo.getSignPayListEntry();
		}
		
		return entryCol;
	}
	
//	private void createAccRecord(List accList) throws Exception {
//		logger.error("entern createAccRecord function!");
//		if (accList != null && !accList.isEmpty()) { // ��������
//			String roomId = "";
//			for (int i = 0; i < accList.size(); i++) {
//				roomId = "";
//				roomId = accList.get(i).toString();
//				//this.getTransactionInfo(room); // ���������������
//				this.checkRoomMoneyType(roomId);
//				RoomLoanInfo roomLoan = new RoomLoanInfo();
//				RoomInfo room = new RoomInfo();
//				room.setId(BOSUuid.read(roomId));
//				roomLoan.setRoom(room);
//				// ���ݱ�����������ɱ���
//				String number = "";
//				number = createNumber();
//				if (number != null && !"".equals(number)) {
//					roomLoan.setNumber(number);
//				}
//				else{
//					FDCMsgBox.showInfo("�����ñ������");
//					SysUtil.abort();
//				}
//				roomLoan.setAFMortgagedState(AFMortgagedStateEnum.UNTRANSACT);
//				roomLoan.setMmType((MoneyDefineInfo) this.moneyDefineMap
//						.get(new Integer(this.moneyTypeOfAfm)));
//				roomLoan.setCreator(SysContext.getSysContext()
//						.getCurrentUserInfo());
//				if (this.billObjectMap.get("sign") != null) {
//					roomLoan.setSign((SignManageInfo) this.billObjectMap.get("sign"));
////					String id = ((SignManageInfo) this.billObjectMap.get("sign")).toString();  //add by shilei
////					SignManageInfo smi = SignManageFactory.getRemoteInstance().getSignManageInfo(new ObjectUuidPK(id));
////					if(smi.getAcfBank()!=null){
////						String loanbank = smi.getLoanBank().getId().toString();
////						BankInfo loanbankinfo = BankFactory.getRemoteInstance().getBankInfo(new ObjectUuidPK(loanbank));
////						roomLoan.setLoanBank(loanbankinfo);
////					}
//					roomLoan.setLoanBank(((SignManageInfo) this.billObjectMap.get("sign")).getAcfBank());
//				} else if (this.billObjectMap.get("purchase") != null) {
//					roomLoan
//							.setPurchase((PurchaseManageInfo) this.billObjectMap
//									.get("purchase"));
//				}
//
//				logger.error("��ʼ��������Ϣ:this.initLoanData");
//				// ��ʼ��������Ϣ
//				this.initLoanData(roomLoan);
//
//				logger.error("����ҵ��������Ӧ�ķ���:updateTransactionOverView");
//				// ����ҵ��������Ӧ�ķ���
//				SHEManageHelper.updateTransactionOverView(null, roomLoan
//						.getRoom(), SHEManageHelper.ACCFUND, roomLoan
//						.getPromiseDate(), null, false);
//
//				logger.error("���������𵥾ݿ�ʼ..........");
//				RoomLoanFactory.getRemoteInstance().save(roomLoan);
//				logger.error("���������𵥾ݾݽ���..........");
//
//			}
//
//		}
//	}
	
	/**
	 * �����Ϲ���ǩԼ���еĸ��������ʼ����������
	 * @param roomLoan
	 * @throws BOSException 
	 * @throws EASBizException 
	 */
	Map payListMap=new HashMap();
	private void initLoanData(RoomLoanInfo roomLoan) throws EASBizException, BOSException{
		if(roomLoan.getSign().getPayType()==null||roomLoan.getSign().getPayType().getId()==null){
			return;
		}
		String payTypeId = roomLoan.getSign().getPayType().getId().toString();
		SHEPayTypeInfo payType=null;
		if(payListMap.containsKey(payTypeId)){
			payType=(SHEPayTypeInfo) payListMap.get(payTypeId);
		}else{
			//��ȡ�����
			SelectorItemCollection selector = new SelectorItemCollection();
			selector.add(new SelectorItemInfo("isLoan"));
			selector.add(new SelectorItemInfo("loanBank.*"));
			selector.add(new SelectorItemInfo("acfBank.*"));
			selector.add(new SelectorItemInfo("loanScheme.*"));
			selector.add(new SelectorItemInfo("afScheme.*"));
			selector.add(new SelectorItemInfo("bizLists.*"));
			
			selector.add(new SelectorItemInfo("loanScheme.ApproachEntrys.*"));
			selector.add(new SelectorItemInfo("afScheme.DataEntrys.*"));
			
			payType = SHEPayTypeFactory.getRemoteInstance().getSHEPayTypeInfo(new ObjectUuidPK(payTypeId), selector);
		}

		if(payType.isIsLoan()){  //�����ң�����Ϣ���浽���ҵ���
			if(roomLoan.getMmType().getMoneyType().equals(MoneyTypeEnum.LoanAmount)){  //����
				roomLoan.setORSOMortgaged(payType.getLoanScheme());
				this.setLoanEntry(roomLoan, payType.getLoanScheme());
			}else{  //������
				roomLoan.setORSOMortgaged(payType.getAfScheme());
				this.setLoanEntry(roomLoan, payType.getAfScheme());
			}
		}
		//��������ҵ����ϸ�������ŵ�������
		if(payType.getBizLists()!=null && !payType.getBizLists().isEmpty()){
			for(int i=0; i<payType.getBizLists().size(); i++){
				BizListEntryInfo bizEntry = payType.getBizLists().get(i);
				if(bizEntry.getPayTypeBizFlow().equals(BizNewFlowEnum.LOAN)){
					Date promDate = this.getBizPromiseDate(payType.getBizLists(), bizEntry);
					roomLoan.setPromiseDate(promDate);
					break;
				}
			}
		}
	}
	
	/**
	 * ������ϸ������Ӧ�������
	 * @param bizEntryCol
	 * @param currentBizInfo
	 * @return
	 */
	private Date getBizPromiseDate(BizListEntryCollection bizEntryCol, 
			BizListEntryInfo currentBizInfo){
		if(currentBizInfo.getPayTypeBizTime() == null){
			return null;
		}
		else if(currentBizInfo.getPayTypeBizTime().equals("ָ������")){  //��������Ϊָ������
			return currentBizInfo.getAppDate();
		}
		else{
			for(int i=0; i<bizEntryCol.size(); i++){
				BizListEntryInfo bizInfo = bizEntryCol.get(i);
				if(bizInfo.getPayTypeBizFlow().getAlias().equals(currentBizInfo.getPayTypeBizTime())){
					Date tempDate = getBizPromiseDate(bizEntryCol, bizInfo);
					if(tempDate != null){
						//���ݼ���º����������
						int mon = currentBizInfo.getMonthLimit();
						int day = currentBizInfo.getDayLimit();
						return DateTimeUtils.addDuration(tempDate, 0, mon, day);
					}
				}
			}
			return null;
		}
	}
	
	
	/**
	 * ���ݸ�����еİ��ҷ��������ð��ҵ��ݵķ�¼
	 * @param roomLoan
	 * @param afmScheme
	 * @throws BOSException 
	 * @throws EASBizException 
	 */
	private void setLoanEntry(RoomLoanInfo roomLoan, AFMortgagedInfo afmScheme) throws EASBizException, BOSException{
		if(afmScheme == null){
			return;
		}
//		SelectorItemCollection selector = new SelectorItemCollection();
//		selector.add(new SelectorItemInfo("ApproachEntrys.*"));
//		selector.add(new SelectorItemInfo("DataEntrys.*"));
//		
//		afmScheme = AFMortgagedFactory.getRemoteInstance().getAFMortgagedInfo(new ObjectUuidPK(afmScheme.getId().toString()), selector);
		
		roomLoan.getAFMortgaged().clear();
		
		//���ý��̷�¼
		RoomLoanAFMEntrysCollection afmEntryCol = new RoomLoanAFMEntrysCollection();
		AFMortgagedApproachEntryCollection appCol = afmScheme.getApproachEntrys();
		if(appCol!=null && !appCol.isEmpty()){
			for(int i=0; i<appCol.size(); i++){
				RoomLoanAFMEntrysInfo roomLoanEntry = new RoomLoanAFMEntrysInfo();
				AFMortgagedApproachEntryInfo appEntry = appCol.get(i);
				
				roomLoanEntry.setApproach(appEntry.getName());
				roomLoanEntry.setPromiseFinishDate(getApproachPromiseDate(roomLoan, appCol, appEntry));
				roomLoanEntry.setIsFinish(false);
				roomLoanEntry.setRemark(appEntry.getRemark());
				roomLoanEntry.setIsFinishFlag(appEntry.isIsFinish());
				roomLoanEntry.setIsAOrB(true);
				
				afmEntryCol.add(roomLoanEntry);
			}
		}
		
		//�������Ϸ�¼
		AFMortgagedDataEntryCollection dataCol = afmScheme.getDataEntrys();
		if(dataCol!=null && !dataCol.isEmpty()){
			for(int i=0; i<dataCol.size(); i++){
				RoomLoanAFMEntrysInfo roomLoanEntry = new RoomLoanAFMEntrysInfo();
				AFMortgagedDataEntryInfo dataEntry = dataCol.get(i);
				
				roomLoanEntry.setApproach(dataEntry.getName());
				roomLoanEntry.setIsFinish(false);
				roomLoanEntry.setRemark(dataEntry.getRemark());
				roomLoanEntry.setIsAOrB(false);
				
				afmEntryCol.add(roomLoanEntry);
			}
		}
		
		if(!afmEntryCol.isEmpty()){
			roomLoan.getAFMortgaged().addCollection(afmEntryCol);
		}
	}
	
	private Date getApproachPromiseDate(RoomLoanInfo roomLoan, AFMortgagedApproachEntryCollection afmAppCol, 
			AFMortgagedApproachEntryInfo currentAppInfo){
		if(currentAppInfo.getReferenceTime() == null){
			return null;
		}
		else if(currentAppInfo.getReferenceTime().equals("ָ������")){  //��������Ϊָ������
			return currentAppInfo.getScheduledDate();
		}
		else if(currentAppInfo.getReferenceTime().equals("ǩԼ����")){  //��������ΪǩԼ����
			if(roomLoan.getSign()==null){
				return null;
			}
			else{
				return roomLoan.getSign().getBizDate();
			}
		}
		else{
			for(int i=0; i<afmAppCol.size(); i++){
				AFMortgagedApproachEntryInfo appInfo = afmAppCol.get(i);
				if(appInfo.getName().equals(currentAppInfo.getReferenceTime())){
					Date tempDate = getApproachPromiseDate(roomLoan, afmAppCol, appInfo);
					if(tempDate != null){
						//���ݼ���º����������
						int mon = currentAppInfo.getIntervalMonth();
						int day = currentAppInfo.getIntervalDays();
						return DateTimeUtils.addDuration(tempDate, 0, mon, day);
					}
				}
			}
			return null;
		}
	}
	

//	private void createLoanRecord(List loanList) throws EASBizException, BOSException {
//		logger.error("entern createLoanRecord function!");
//		if (loanList != null && !loanList.isEmpty()) { // ��������
//			String roomId = "";
//			for (int i = 0; i < loanList.size(); i++) {
//				roomId = "";
//				roomId = loanList.get(i).toString();
//				this.checkRoomMoneyType(roomId);
//				RoomLoanInfo roomLoan = new RoomLoanInfo();
//				RoomInfo room = new RoomInfo();
//				room.setId(BOSUuid.read(roomId));
//				roomLoan.setRoom(room);
//				//���ݱ�����������ɱ���
//				String number ="";
//				number = createNumber();
//				if(number!=null && !"".equals(number)){
//					roomLoan.setNumber(number);
//				}
//				else{
//					FDCMsgBox.showInfo("�����ñ������");
//					SysUtil.abort();
//				}
//				roomLoan.setAFMortgagedState(AFMortgagedStateEnum.UNTRANSACT);
//				roomLoan.setMmType((MoneyDefineInfo)this.moneyDefineMap.get(new Integer(this.moneyTypeOfLoan)));
//				roomLoan.setCreator(SysContext.getSysContext().getCurrentUserInfo());
//				if(this.billObjectMap.get("sign") != null){
//					roomLoan.setSign((SignManageInfo)this.billObjectMap.get("sign"));
////					String id = ((SignManageInfo)this.billObjectMap.get("sign")).getId().toString(); //add by shilei
////					SignManageInfo smi = SignManageFactory.getRemoteInstance().getSignManageInfo(new ObjectUuidPK(id));
////					if(smi.getLoanBank()!=null){
////						String Loanbank = smi.getLoanBank().getId().toString();
////						BankInfo loanBankInfo = BankFactory.getRemoteInstance().getBankInfo(new ObjectUuidPK(Loanbank)); 
////						roomLoan.setLoanBank(loanBankInfo);
////					}
//					roomLoan.setLoanBank(((SignManageInfo) this.billObjectMap.get("sign")).getLoanBank());
//				}
//				else if(this.billObjectMap.get("purchase") != null){
//					roomLoan.setPurchase((PurchaseManageInfo)this.billObjectMap.get("purchase"));
//				}
//				
//				logger.error("��ʼ��������Ϣ:this.initLoanData");
//				//��ʼ��������Ϣ
//				this.initLoanData(roomLoan);
//				
//				logger.error("����ҵ��������Ӧ�ķ���:updateTransactionOverView");
//				//����ҵ��������Ӧ�ķ���
//				SHEManageHelper.updateTransactionOverView(null, roomLoan.getRoom(), SHEManageHelper.MORTGAGE,
//						roomLoan.getPromiseDate(), null, false);
//				
//				logger.error("�������ҵ��ݿ�ʼ..........");
//				RoomLoanFactory.getRemoteInstance().save(roomLoan);
//				logger.error("�������ҵ��ݽ���..........");
//			}
//
//		}
//		
//	}
	
	private String createNumber() throws BOSException, CodingRuleException,
			EASBizException {
		String number = "";
		String currentOrgId = SysContext.getSysContext().getCurrentOrgUnit()
				.getId().toString();
		ICodingRuleManager iCodingRuleManager = CodingRuleManagerFactory
				.getRemoteInstance();
		// if (!"ADDNEW".equals(this.oprtState)) {
		// return number;
		// }
		boolean isExist = true;
		RoomLoanInfo objValue = new RoomLoanInfo();
		objValue.setCU(SysContext.getSysContext().getCurrentCtrlUnit());
		if (currentOrgId.length() == 0
				|| !iCodingRuleManager.isExist(objValue, currentOrgId)) {
			currentOrgId = FDCClientHelper.getCurrentOrgId();
			if (!iCodingRuleManager.isExist(objValue, currentOrgId)) {
				isExist = false;
			}
		}

		if (isExist) {
			boolean isAddView = FDCClientHelper.isCodingRuleAddView(objValue,
					currentOrgId);
			if (isAddView) {
				number = getRuleNumber(objValue, currentOrgId);
			} else {
				// String number = null;
				if (iCodingRuleManager.isUseIntermitNumber(objValue,
						currentOrgId)) {
					if (iCodingRuleManager.isUserSelect(objValue, currentOrgId)) {
						CodingRuleIntermilNOBox intermilNOF7 = new CodingRuleIntermilNOBox(
								objValue, currentOrgId, null, null);
						Object object = null;
						if (iCodingRuleManager
								.isDHExist(objValue, currentOrgId)) {
							intermilNOF7.show();
							object = intermilNOF7.getData();
						}
						if (object != null) {
							number = object.toString();
						}
					} else {
						// ֻ�����˶Ϻ�֧�ֹ��ܣ���ʱֻ�Ƕ�ȡ��ǰ���±��룬�����������ڱ���ʱ
						number = iCodingRuleManager.getNumber(objValue,
								currentOrgId);
					}
				} else {
					number = iCodingRuleManager.getNumber(objValue,
							currentOrgId);
				}
			}
		}
		return number;
	}

	private String getRuleNumber(IObjectValue caller, String orgId) {

		String number = "";

		try {
			ICodingRuleManager iCodingRuleManager = CodingRuleManagerFactory
					.getRemoteInstance();
			if (orgId == null || orgId.trim().length() == 0) {
				// ��ǰ�û�������֯������ʱ��ȱʡʵ�����ü��ŵ�
				orgId = OrgConstants.DEF_CU_ID;
			}
			if (iCodingRuleManager.isExist(caller, orgId)) {

				if (iCodingRuleManager.isUseIntermitNumber(caller, orgId)) { // �˴���orgId�벽��1
																				// ��
																				// ��orgIdʱһ�µ�
																				// ��
																				// �ж��û��Ƿ����öϺ�֧�ֹ���
					if (iCodingRuleManager.isUserSelect(caller, orgId)) {
						// �����˶Ϻ�֧�ֹ���,ͬʱ�������û�ѡ��ϺŹ���
						// KDBizPromptBox pb = new KDBizPromptBox();
						CodingRuleIntermilNOBox intermilNOF7 = new CodingRuleIntermilNOBox(
								caller, orgId, null, null);
						// pb.setSelector(intermilNOF7);
						//Ҫ�ж��Ƿ���ڶϺ�,���򵯳�,���򲻵�///////////////////////////////////
						// ///////
						Object object = null;
						if (iCodingRuleManager.isDHExist(caller, orgId)) {
							intermilNOF7.show();
							object = intermilNOF7.getData();
						}
						if (object != null) {
							number = object.toString();
						} else {
							// ���û��ʹ���û�ѡ����,ֱ��getNumber���ڱ���,Ϊʲô����read?
							// ����Ϊʹ���û�ѡ����Ҳ��get!
							number = iCodingRuleManager
									.getNumber(caller, orgId);
						}
					} else {
						// ֻ�����˶Ϻ�֧�ֹ��ܣ���ʱֻ�Ƕ�ȡ��ǰ���±��룬�����������ڱ���ʱ
						number = iCodingRuleManager.getNumber(caller, orgId);
					}
				} else {
					// û�����öϺ�֧�ֹ��ܣ���ʱ��ȡ�˱����������ı���
					number = iCodingRuleManager.getNumber(caller, orgId);
				}
			}

		} catch (Exception err) {
			// ��ȡ�����������ֿ����ֹ�������룡
			// handleCodingRuleError(err);
		}

		return number;
	}

	public void actionUpdateLoanAmount_actionPerformed(ActionEvent e)throws Exception {
		super.actionUpdateLoanAmount_actionPerformed(e);
		
		checkSelected();
		boolean isUpdate = false;
		int[] rowIndexArray = KDTableUtil.getSelectedRows(tblMain);
		
		for(int i=0; i<rowIndexArray.length; i++){
			IRow row = tblMain.getRow(rowIndexArray[i]);
			
			String id = row.getCell("id").getValue().toString();
			String mmTypeId = row.getCell("mmTypeId").getValue().toString();
			BigDecimal afmAmount = FDCHelper.ZERO;
			if(row.getCell("afmAmount").getValue() != null){
				afmAmount = new BigDecimal(row.getCell("afmAmount").getValue().toString());
			}
			
			RoomLoanInfo roomLoanInfo = RoomLoanFactory.getRemoteInstance().getRoomLoanInfo(new ObjectUuidPK(id));
			if(roomLoanInfo.getSign() != null){  //�ȿ�ǩԼ
				SelectorItemCollection selCol = new SelectorItemCollection();
				selCol.add(new SelectorItemInfo("signPayListEntry.*"));
				selCol.add(new SelectorItemInfo("signPayListEntry.moneyDefine.*"));
				
				SignManageInfo signInfo = SignManageFactory.getRemoteInstance()
					.getSignManageInfo(new ObjectUuidPK(roomLoanInfo.getSign().getId().toString()), selCol);
				if(signInfo.getSignPayListEntry() != null){
					for(int j=0; j<signInfo.getSignPayListEntry().size(); j++){
						SignPayListEntryInfo payListEntry = signInfo.getSignPayListEntry().get(j);
						if(payListEntry.getMoneyDefine()!=null && payListEntry.getMoneyDefine().getId().toString().equals(mmTypeId)
								&& payListEntry.getActRevAmount()!=null){
							//���ҽ�� = ʵ�ս�� - Ӧ�ս��
							BigDecimal actLoanAmount = payListEntry.getAppAmount().subtract(payListEntry.getActRevAmount());
							if(afmAmount.compareTo(actLoanAmount) != 0 ){
								row.getCell("afmAmount").setValue(payListEntry.getAppAmount());
								roomLoanInfo.setActualLoanAmt(payListEntry.getAppAmount());
								
								SelectorItemCollection selector = new SelectorItemCollection();
								selector.add("actualLoanAmt");
								RoomLoanFactory.getRemoteInstance().updatePartial(roomLoanInfo, selector);
								
								isUpdate = true;
							}
						}
					}
				}
			}
		}
//		else if(roomLoanInfo.getPurchase() != null){  //�ٿ��Ϲ�
//			SelectorItemCollection selCol = new SelectorItemCollection();
//			selCol.add(new SelectorItemInfo("purPayListEntry.*"));
//			selCol.add(new SelectorItemInfo("purPayListEntry.moneyDefine.*"));
//			
//			PurchaseManageInfo purInfo = PurchaseManageFactory.getRemoteInstance()
//				.getPurchaseManageInfo(new ObjectUuidPK(roomLoanInfo.getPurchase().getId().toString()), selCol);
//			if(purInfo.getPurPayListEntry() != null){
//				for(int i=0; i<purInfo.getPurPayListEntry().size(); i++){
//					PurPayListEntryInfo payListEntry = purInfo.getPurPayListEntry().get(i);
//					if(payListEntry.getMoneyDefine()!=null && payListEntry.getMoneyDefine().getId().toString().equals(mmTypeId)
//							&& payListEntry.getActRevAmount()!=null){
//						//���ҽ�� = ʵ�ս�� - Ӧ�ս��
//						BigDecimal actLoanAmount = payListEntry.getAppAmount().subtract(payListEntry.getActRevAmount());
//						if(afmAmount.compareTo(actLoanAmount) != 0 ){
//							row.getCell("afmAmount").setValue(payListEntry.getAppAmount());
//							roomLoanInfo.setActualLoanAmt(payListEntry.getAppAmount());
//							
//							SelectorItemCollection selector = new SelectorItemCollection();
//							selector.add("actualLoanAmt");
//							RoomLoanFactory.getRemoteInstance().updatePartial(roomLoanInfo, selector);
//							
//							isUpdate = true;
//						}
//					}
//				}
//			}
//		}
		if(isUpdate){
			FDCMsgBox.showInfo("���³ɹ�");
		}else{
			FDCMsgBox.showInfo("���ҽ��û�б仯������Ҫ����");
		}
	}
	
	public void actionBankLoan_actionPerformed(ActionEvent e) throws Exception {
		super.actionBankLoan_actionPerformed(e);
		checkSelected();
		
		payEntryIdSet.clear();
		
		UIContext uiContext = new UIContext(this);
		
		int[] rowIndexArray = KDTableUtil.getSelectedRows(tblMain);
		
		for(int i=0; i<rowIndexArray.length; i++){
			IRow curRow = tblMain.getRow(rowIndexArray[i]);
			
			//��鰴��״̬
			if (curRow.getCell("loanState").getValue() != null) {
				BizEnumValueInfo state = (BizEnumValueInfo) curRow.getCell("loanState").getValue();
				if (!state.getName().equals(AFMortgagedStateEnum.TRANSACTED.getName())) {
					FDCMsgBox.showWarning(this, "״̬Ϊ" + state.getAlias() + "�ĵ��ݲ��ܽ������зſ������");
					abort();
				}
				if(0 < i){  //��ǰһ�����ҿ������Ƚ�
					IRow preRow = tblMain.getRow(rowIndexArray[i-1]);
					if(curRow.getCell("afmType").getValue() != null && preRow.getCell("afmType").getValue() != null){
						BizEnumValueInfo curAfmType = (BizEnumValueInfo) curRow.getCell("afmType").getValue();
						BizEnumValueInfo preAfmType = (BizEnumValueInfo) preRow.getCell("afmType").getValue();
						if(!curAfmType.getName().equals(preAfmType.getName())){  //���һ��
							FDCMsgBox.showWarning(this, "���ҿ��һ���ĵ��ݲ��ܽ������зſ������");
							abort();
						}
					}
				}
			}
			String id = curRow.getCell("id").getValue().toString();
			
			RoomLoanAFMEntrysCollection col=RoomLoanAFMEntrysFactory.getRemoteInstance().getRoomLoanAFMEntrysCollection("select * from where isAOrB=1 and actualFinishDate is null and parent.id='"+id+"'");
			if(col.size()>0){
				FDCMsgBox.showWarning(this, "���Ұ����������ʵ��������ڱ�����д�������ܽ������зſ������");
				abort();
			}
			
			
			SelectorItemCollection sels = new SelectorItemCollection();
	    	sels.add("mmType");
	    	sels.add("sign.id");
	    	sels.add("room.id");
	    	sels.add("room.building.id");
	    	sels.add("room.building.sellProject.id");
	    	sels.add("room.building.sellProject.name");
	    	sels.add("room.building.sellProject.number");
			RoomLoanInfo roomLoanInfo = RoomLoanFactory.getRemoteInstance().getRoomLoanInfo(new ObjectUuidPK(id),sels);
			
			//��ȡ��������
			TransactionInfo transInfo = SHEManageHelper.getTransactionInfo(null, roomLoanInfo.getRoom());
			if(transInfo!=null && transInfo.getCurrentLink()!=null 
					&& transInfo.getCurrentLink().equals(RoomSellStateEnum.Sign)){  //��ǰ����ΪǩԼ
				this.getSignPayEntry(transInfo.getBillId().toString(),roomLoanInfo.getMmType());
				uiContext.put("sellProject", roomLoanInfo.getRoom().getBuilding().getSellProject());
				if(!this.customerMap.isEmpty()){
					uiContext.put("customerMap", this.customerMap);
				}
				
			}
			else {
				FDCMsgBox.showInfo("��ǩԼ���䲻�ܽ������зſ����");
				SysUtil.abort();
			}
		}
		
		uiContext.put("signEntrySet", payEntryIdSet);
		
		IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.MODEL).create(BankPaymentEditUI.class.getName(), 
				uiContext, null, OprtState.ADDNEW);
		uiWindow.show();
		this.refresh(null);
	}
	
	/**
	 * ��ȡǩԼ���ĸ�����ϸ
	 * @param billId
	 * @return
	 * @throws EASBizException
	 * @throws BOSException
	 */
	private void getSignPayEntry(String billId,MoneyDefineInfo moneyDefine) throws EASBizException, BOSException{
		SelectorItemCollection selCol = new SelectorItemCollection();
		selCol.add("signPayListEntry.moneyDefine.moneyType");
		selCol.add("signPayListEntry.moneyDefine.id");
		selCol.add("customerNames");
		selCol.add("signPayListEntry.actRevAmount");
		selCol.add("signPayListEntry.appAmount");
		
		SignManageInfo signInfo = SignManageFactory.getRemoteInstance().getSignManageInfo(new ObjectUuidPK(billId), selCol);
		String customerName = "";
		if(signInfo != null){
			if(signInfo.getCustomerNames()!=null){
				customerName = signInfo.getCustomerNames();
			}
			SignPayListEntryCollection signEntryColl = signInfo.getSignPayListEntry();
			for(int i=0;i<signEntryColl.size();i++)
			{
				SignPayListEntryInfo signPayInfo = signEntryColl.get(i);
				if(moneyDefine.getId().toString().equals(signPayInfo.getMoneyDefine().getId().toString()))
				{
					payEntryIdSet.add(signPayInfo.getId().toString());
					this.customerMap.put(signPayInfo.getId().toString(),customerName);
				}
			}
		}
	}
	
	/**
	 * �ֹ���ֹ����
	 */
	public void actionStopTransact_actionPerformed(ActionEvent e)
			throws Exception {
		IRow row = KDTableUtil.getSelectedRow(tblMain);
		if (row != null) {
			if(MsgBox.showConfirm2New(this, "�Ƿ�ȷ���ֹ���ֹ����") == MsgBox.YES){
				if (row.getCell("id").getValue() != null) {
					String id = row.getCell("id").getValue().toString();
					//��ȡ���Ҷ���
					SelectorItemCollection selector = new SelectorItemCollection();
					selector.add(new SelectorItemInfo("*"));
					selector.add(new SelectorItemInfo("mmType.*"));
					selector.add(new SelectorItemInfo("room.id"));
					selector.add(new SelectorItemInfo("promiseDate"));
					selector.add(new SelectorItemInfo("actualFinishDate"));
					RoomLoanInfo rlInfo = RoomLoanFactory.getRemoteInstance().getRoomLoanInfo(new ObjectUuidPK(id), selector);
					if (rlInfo != null) {
						//���°��ҵ���״̬
						if (rlInfo.getAFMortgagedState().equals(AFMortgagedStateEnum.UNTRANSACT)
								|| rlInfo.getAFMortgagedState().equals(AFMortgagedStateEnum.TRANSACTING)
								|| rlInfo.getAFMortgagedState().equals(AFMortgagedStateEnum.TRANSACTED)) {
							rlInfo.setAFMortgagedState(AFMortgagedStateEnum.STOPTRANSACT);
							RoomLoanFactory.getRemoteInstance().update(new ObjectUuidPK(id), rlInfo);
							
							//���·��䰴��״̬
							if(rlInfo.getMmType().getMoneyType() != null){  //�ж��ǰ��һ��ǹ����𵥾�
								if(rlInfo.getMmType().getMoneyType().equals(MoneyTypeEnum.LoanAmount)){
									rlInfo.getRoom().setRoomLoanState(RoomLoanStateEnum.NOTLOANED);
								}
								else{
									rlInfo.getRoom().setRoomACCFundState(RoomACCFundStateEnum.NOTFUND);
								}
							}
							else{
								rlInfo.getRoom().setRoomLoanState(RoomLoanStateEnum.NOTLOANED);
								rlInfo.getRoom().setRoomACCFundState(RoomACCFundStateEnum.NOTFUND);
							}
							SelectorItemCollection roomSelector = new SelectorItemCollection();
							roomSelector.add("roomLoanState");
							roomSelector.add("roomACCFundState");
							RoomFactory.getRemoteInstance().updatePartial(rlInfo.getRoom(), roomSelector);
							
							//����ҵ�������еĶ�Ӧ��ҵ��
							this.deleteTransactionBiz(rlInfo);
							
							MsgBox.showWarning(this, "�ֹ���ֹ��������ɹ���");
							refresh(null);
						} else {
							MsgBox.showWarning(this, "ֻ��״̬Ϊδ����Ͱ����еĵ��ݲſ��Խ����ֹ���ֹ���������");
						}
					}
				}
			}
		} else {
			MsgBox.showWarning(this, "����ѡ���У�");
		}
	}

	protected void checkBeforeRemove() throws EASBizException, BOSException,
			Exception {
		int[] selectRows = KDTableUtil.getSelectedRows(this.tblMain);

		for (int i = 0, size = selectRows.length; i < size; i++) {
			int rowIndex = selectRows[i];
			IRow row = tblMain.getRow(rowIndex);
			if (row == null) {
				MsgBox.showInfo(this, "û��ѡ���У�");
				SysUtil.abort();
			}
			Object valueCom = row.getCell("mmTypeId").getValue();
			Object valueRoom = row.getCell("id").getValue();
			if (valueCom == null) {
				FilterInfo filter = new FilterInfo();
				filter.getFilterItems().add(new FilterItemInfo("roomId", valueRoom.toString()));
				if (!getBizInterface().exists(filter)) {
					MsgBox.showInfo(this, "����δ�����ҵļ�¼�����ܽ��д˲�����");
					SysUtil.abort();
				}
			}
		}
	}

	public void actionRemove_actionPerformed(ActionEvent e) throws Exception {
		checkSelected();
//		Set purchaseSet = new HashSet();
//		Set moneyTypeSet = new HashSet();
//		int selectedRows[] = KDTableUtil.getSelectedRows(this.tblMain);
//		for (int i = 0; i < selectedRows.length; i++) {
//			IRow row = this.tblMain.getRow(selectedRows[i]);
////			String purchaseID = ((BOSUuid) row.getCell("id").getValue()).toString();
//			BizEnumValueInfo moneyType = (BizEnumValueInfo) row.getCell("afmType").getValue();
////			purchaseSet.add(purchaseID);
//			moneyTypeSet.add(moneyType);
//		}
//		if (moneyTypeSet.size() > 1) {
//			MsgBox.showInfo("��ѡ�ķ��䰴�ҿ��һ��");
//			this.abort();
//		}
//		if (purchaseSet.size() > 0) {
//			FDCSQLBuilder builder = new FDCSQLBuilder();
//			builder.appendSql(" select count(*) count from T_SHE_Purchase purchase ");
//			builder.appendSql(" left join T_SHE_PurchasePayListEntry payList on payList.fHeadID = purchase.fid ");
//			builder.appendSql(" left join T_SHE_moneyDefine moneyDefine on payList.fMoneyDefineID = moneyDefine.fid where ");
//			builder.appendParam("purchase.fid", purchaseSet.toArray());
//			builder.appendSql(" and isnull(payList.FActRevAmount,0)>0 ");
//			Iterator iter = moneyTypeSet.iterator();
//			MoneyTypeEnum monType = (MoneyTypeEnum) iter.next();
//			if (MoneyTypeEnum.LoanAmount.equals(monType)) {
//				builder.appendSql(" and moneyDefine.FMoneyType='LoanAmount' ");
//			} else if (MoneyTypeEnum.AccFundAmount.equals(monType)) {
//				builder.appendSql(" and moneyDefine.FMoneyType='AccFundAmount' ");
//			}
//			IRowSet countSet = builder.executeQuery();
//			while (countSet.next()) {
//				int count = countSet.getInt("count");
//				if (count > 0) {
//					MsgBox.showInfo("��ѡ�ķ����Ѿ���ȡ" + monType + "����ɾ��!");
//					this.abort();
//				}
//			}
//		}
//		if (checkEditAndRemove("ɾ��")) {
//			isAddNew = false;
			int[] rows = KDTableUtil.getSelectedRows(tblMain);
			IRoomLoan ir = RoomLoanFactory.getRemoteInstance();
			if (rows != null && rows.length > 0) {
				if (confirmRemove()) {
					IObjectPK[] pks = new ObjectUuidPK[rows.length];
					IRow row = null;
					for (int i = 0; i < rows.length; i++) {
						row = this.tblMain.getRow(rows[i]);
						pks[i] = new ObjectUuidPK(row.getCell("id").getValue().toString());
						//ɾ�������������е�ҵ��
//						this.deleteTransactionBiz(row.getCell("id").getValue().toString());
					}
					ir.delete(pks);
					MsgBox.showWarning("ɾ���ɹ���");
					refresh(null);
				}
			}
//		}
	}
	
	/**
	 * ɾ��ҵ�������ж�Ӧ�ķ���
	 * @param roomLoan
	 */
	private void deleteTransactionBiz(RoomLoanInfo roomLoan){
		try {
//			BusinessTypeNameEnum bizType = BusinessTypeNameEnum.MORTGAGE;  //Ĭ�ϰ���
			String bizType = SHEManageHelper.MORTGAGE;  //Ĭ�ϰ���
			if(roomLoan.getMmType().getMoneyType().equals(MoneyTypeEnum.AccFundAmount)){  //������
				bizType = SHEManageHelper.ACCFUND;
			}
			SHEManageHelper.updateTransactionOverView(null, roomLoan.getRoom(), bizType, roomLoan.getPromiseDate(),
				roomLoan.getActualFinishDate(), true);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	public static String getRes(String resName) {
		return EASResource.getString(
				"com.kingdee.eas.fdc.sellhouse.SellHouseResource", resName);
	}

	protected boolean isIgnoreCUFilter() {
		return true;
	}

	protected FilterInfo getTreeFilter() throws Exception {
		DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeMain
				.getLastSelectedPathComponent();
		if (node == null || node.getUserObject() instanceof OrgStructureInfo) {
			return null;
		}
		FilterInfo filter = new FilterInfo();
		if (node.getUserObject() instanceof Integer) { // ������
			Integer unit = (Integer) node.getUserObject();
			BuildingInfo building = (BuildingInfo) ((DefaultKingdeeTreeNode) node.getParent()).getUserObject();
			String buildingId = building.getId().toString();
			filter.getFilterItems().add(new FilterItemInfo("room.building.id", buildingId));
			filter.getFilterItems().add(new FilterItemInfo("room.unit", unit));
		} else if (node.getUserObject() instanceof BuildingUnitInfo) {// ��Ԫ
			BuildingUnitInfo buildUnit = (BuildingUnitInfo) node.getUserObject();
			BuildingInfo building = (BuildingInfo) ((DefaultKingdeeTreeNode) node.getParent()).getUserObject();
			String buildingId = building.getId().toString();
			filter.getFilterItems().add(new FilterItemInfo("room.building.id", buildingId));
			filter.getFilterItems().add(new FilterItemInfo("room.buildUnit.id", buildUnit.getId().toString()));
		} else if (node.getUserObject() instanceof BuildingInfo) {// ¥��
			BuildingInfo building = (BuildingInfo) node.getUserObject();
			String buildingId = building.getId().toString();
			filter.getFilterItems().add(new FilterItemInfo("room.building.id", buildingId));
		} else if (node.getUserObject() instanceof SubareaInfo) { // ����
			SubareaInfo subarea = (SubareaInfo) node.getUserObject();
			filter.getFilterItems().add(new FilterItemInfo("room.building.subarea.id", subarea.getId().toString()));
		} else if (node.getUserObject() instanceof SellProjectInfo) { // ������Ŀ
			SellProjectInfo sellProject = (SellProjectInfo) node.getUserObject();
			if(node.getChildCount() > 0){  //�༶��Ŀ
				filter.getFilterItems().add(new FilterItemInfo("room.building.sellProject.parent.id", sellProject.getId().toString()));
			}
			else{  //ĩ����Ŀ
				filter.getFilterItems().add(new FilterItemInfo("room.building.sellProject.id", sellProject.getId().toString()));
			}
		}
		
		return filter;
	}

	protected IQueryExecutor getQueryExecutor(IMetaDataPK queryPK, EntityViewInfo viewInfo) {
		viewInfo = (EntityViewInfo) mainQuery.clone();

		// �ϲ���ѯ����
		try {
			//����¥����ѯ����
			FilterInfo filter = getTreeFilter();
			if(filter != null){
				//�޶�����״̬��ֻ��ʾδ���������С�������ɺ����зſ�ֹ���ֹ����5��״̬������
				HashSet afmStateSet = new HashSet();
				afmStateSet.add(new Integer(AFMortgagedStateEnum.UNTRANSACT_VALUE));
				afmStateSet.add(new Integer(AFMortgagedStateEnum.TRANSACTING_VALUE));
				afmStateSet.add(new Integer(AFMortgagedStateEnum.TRANSACTED_VALUE));
				afmStateSet.add(new Integer(AFMortgagedStateEnum.STOPTRANSACT_VALUE));
				afmStateSet.add(new Integer(AFMortgagedStateEnum.BANKFUND_VALUE));
				filter.getFilterItems().add(new FilterItemInfo("aFMortgagedState", afmStateSet, CompareType.INCLUDE));
			}
			else{
				filter = new FilterInfo();
				filter.getFilterItems().add(new FilterItemInfo("id", "null"));
			}
			
			if (viewInfo.getFilter() != null) {
				viewInfo.getFilter().mergeFilter(filter, "and");
			} else {
				viewInfo.setFilter(filter);
			}
		} catch (Exception e) {
			super.handUIException(e);
		}

		return super.getQueryExecutor(queryPK, viewInfo);
	}

	public void afterActionPerformed(ActionEvent e) {
		super.afterActionPerformed(e);
		if (e.getSource() == btnEdit || e.getSource() == menuItemEdit
				|| e.getSource() == btnBatchLoan
				|| e.getSource() == menuItemBatchLoan) {
			try {
				refresh(e);
			} catch (Exception e1) {
				logger.error(e1);
			}
		}
	}

	public void actionExport_actionPerformed(ActionEvent e) throws Exception {
		handlePermissionForItemAction(actionExport);
		super.actionExport_actionPerformed(e);
	}

	public void actionExportData_actionPerformed(ActionEvent e)
			throws Exception {
		handlePermissionForItemAction(actionExportData);
		super.actionExportData_actionPerformed(e);
	}

	public void actionExportSelected_actionPerformed(ActionEvent e)
			throws Exception {
		handlePermissionForItemAction(actionExportSelected);
		super.actionExportSelected_actionPerformed(e);

	}

	public void actionToExcel_actionPerformed(ActionEvent e) throws Exception {
		handlePermissionForItemAction(actionToExcel);
		super.actionToExcel_actionPerformed(e);
	}

	/**
	 * ���������տ����
	 */
	public void actionBatchReceiveBill_actionPerformed(ActionEvent e)
			throws Exception {
		List idList = new ArrayList();
		boolean isSelectOk = true;
		boolean isChangeOK = true;
		boolean isChanging = true;
		int[] selRowNums = KDTableUtil.getSelectedRows(this.tblMain);
		if (selRowNums.length == 0)
			return;
		String idsStr = "'nullnull'";
		for (int i = 0; i < selRowNums.length; i++) {
			BOSUuid roomUid = (BOSUuid) this.tblMain.getRow(selRowNums[i])
					.getCell("id").getValue();
			String loanIdStr = (String) (roomUid == null ? "" : roomUid
					.toString());
			if (loanIdStr == null || loanIdStr.length() == 0) {
				isSelectOk = false;
				break;
			}
			idsStr += ",'" + loanIdStr + "'";
			BOSUuid unUid = (BOSUuid) this.tblMain.getRow(selRowNums[i]).getCell("roomId").getValue();
			String roomIdStr = (String) (unUid == null ? "" : unUid.toString());
			if (!idList.contains(roomIdStr))
				idList.add(roomIdStr);
		}

		Set mmTypeSet = new HashSet(); // ����������һ��
		Set sellProSet = new HashSet(); // ��������Ŀ����һ��
		RoomLoanCollection roomLoanColl = RoomLoanFactory
				.getRemoteInstance()
				.getRoomLoanCollection(
						"select aFMortgagedState,MmType.moneyType,room.building.sellProject.name where id in ("
								+ idsStr + ")");
		if (roomLoanColl.size() == 0)
			isSelectOk = false;
		for (int i = 0; i < roomLoanColl.size(); i++) {
			RoomLoanInfo thisLoanInfo = roomLoanColl.get(i);
			if (thisLoanInfo.getAFMortgagedState() == null
					|| (!thisLoanInfo.getAFMortgagedState().equals(
							AFMortgagedStateEnum.TRANSACTED))) {
				if (thisLoanInfo.getAFMortgagedState() == null
						|| thisLoanInfo.getAFMortgagedState().equals(
								AFMortgagedStateEnum.CHANGECALL)) {
					isChangeOK = false;
					break;
				} else if (thisLoanInfo.getAFMortgagedState() == null
						|| thisLoanInfo.getAFMortgagedState().equals(
								AFMortgagedStateEnum.STOPTRANSACT)) {
					isSelectOk = false;
					break;
				} else if (thisLoanInfo.getAFMortgagedState() == null
						|| thisLoanInfo.getAFMortgagedState().equals(
								AFMortgagedStateEnum.TRANSACTING)) {
					isChanging = false;
				} else {
					isSelectOk = false;
					break;
				}
			}
			MoneyDefineInfo monyDefInfo = thisLoanInfo.getMmType();
			if (monyDefInfo == null) {
				mmTypeSet.add(null);
			} else {
				mmTypeSet.add(monyDefInfo.getMoneyType());
			}
			if (thisLoanInfo.getRoom().getBuilding().getSellProject() == null)
				sellProSet.add(null);
			else
				sellProSet.add(thisLoanInfo.getRoom().getBuilding()
						.getSellProject());
		}

		if (!isChangeOK) {
			MsgBox.showWarning("��ѡ������ת�У����������տ");
			return;
		}

		if (!isSelectOk) {
			MsgBox.showWarning("��ѡ�������ֹ�������ֹ�����������տ");
			return;
		}

		if (!isChanging) {
			MsgBox.showWarning("��ѡ�����״̬Ϊ�����У����������տ");
			return;
		}

		if (mmTypeSet.size() != 1) {
			MsgBox.showWarning("��ѡ����Ŀ������һ��,����!");
			return;
		}
		if (sellProSet.size() != 1) {
			MsgBox.showWarning("��ѡ����ı�����ͬһ��Ŀ�µ�,����!");
			return;

		}

		UIContext uiContext = new UIContext(this);
		uiContext.put("roomIds", idList);
		uiContext.put("MoneyTypeEnum", (MoneyTypeEnum) mmTypeSet.iterator()
				.next());
		uiContext.put("sellProject", (SellProjectInfo) sellProSet.iterator()
				.next());
		// ����UI������ʾ
		IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.NEWTAB)
				.create(BatchReceiveBillEditUI.class.getName(), uiContext,
						null, "ADDNEW");
		uiWindow.show();
	}

	protected String getLongNumberFieldName() {
		return "number";
	}

	protected void tblMain_tableClicked(KDTMouseEvent e) throws Exception {
		super.tblMain_tableClicked(e);
	}
}