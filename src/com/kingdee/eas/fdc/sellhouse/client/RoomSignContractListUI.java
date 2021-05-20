/**
 * output package name
 */
package com.kingdee.eas.fdc.sellhouse.client;

import java.awt.event.ActionEvent;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.EventListener;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JComponent;
import javax.swing.SwingUtilities;
import javax.swing.event.ChangeListener;
import javax.swing.tree.TreeNode;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.data.event.RequestRowSetEvent;
import com.kingdee.bos.ctrl.kdf.data.impl.BOSQueryDelegate;
import com.kingdee.bos.ctrl.kdf.table.ICell;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.event.KDTDataFillListener;
import com.kingdee.bos.ctrl.kdf.table.event.KDTDataRequestEvent;
import com.kingdee.bos.ctrl.kdf.table.util.KDTableUtil;
import com.kingdee.bos.ctrl.report.forapp.kdnote.client.KDNoteHelper;
import com.kingdee.bos.ctrl.swing.KDSpinner;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.dao.query.IQueryExecutor;
import com.kingdee.bos.metadata.IMetaDataPK;
import com.kingdee.bos.metadata.MetaDataPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.metadata.resource.BizEnumValueInfo;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.IUIFactory;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.UIException;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.bos.workflow.ProcessInstInfo;
import com.kingdee.bos.workflow.service.ormrpc.EnactmentServiceFactory;
import com.kingdee.bos.workflow.service.ormrpc.IEnactmentService;
import com.kingdee.eas.base.commonquery.client.CommonQueryDialog;
import com.kingdee.eas.basedata.org.OrgStructureInfo;
import com.kingdee.eas.basedata.org.SaleOrgUnitInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.basedata.MoneySysTypeEnum;
import com.kingdee.eas.fdc.basedata.client.FDCClientHelper;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.sellhouse.BuildingInfo;
import com.kingdee.eas.fdc.sellhouse.BuildingUnitInfo;
import com.kingdee.eas.fdc.sellhouse.CodingTypeEnum;
import com.kingdee.eas.fdc.sellhouse.FDCReceiveBillFactory;
import com.kingdee.eas.fdc.sellhouse.PurchaseElsePayListEntryInfo;
import com.kingdee.eas.fdc.sellhouse.PurchaseFactory;
import com.kingdee.eas.fdc.sellhouse.PurchaseInfo;
import com.kingdee.eas.fdc.sellhouse.PurchasePayListEntryInfo;
import com.kingdee.eas.fdc.sellhouse.PurchaseStateEnum;
import com.kingdee.eas.fdc.sellhouse.RoomAreaCompensateFactory;
import com.kingdee.eas.fdc.sellhouse.RoomFactory;
import com.kingdee.eas.fdc.sellhouse.RoomInfo;
import com.kingdee.eas.fdc.sellhouse.RoomSellStateEnum;
import com.kingdee.eas.fdc.sellhouse.RoomSignContractFactory;
import com.kingdee.eas.fdc.sellhouse.RoomSignContractInfo;
import com.kingdee.eas.fdc.sellhouse.SellProjectInfo;
import com.kingdee.eas.fdc.sellhouse.SincerityPurchaseFactory;
import com.kingdee.eas.fdc.sellhouse.SincerityPurchaseInfo;
import com.kingdee.eas.fdc.sellhouse.SincerityPurchaseStateEnum;
import com.kingdee.eas.fdc.sellhouse.SubareaInfo;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.framework.ITreeBase;
import com.kingdee.eas.framework.util.CommonDataProvider;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.MsgBox;
import com.kingdee.jdbc.rowset.IRowSet;

/**
 * output class name
 */
public class RoomSignContractListUI extends AbstractRoomSignContractListUI {
	private static final Logger logger = CoreUIObject.getLogger(RoomSignContractListUI.class);

	SaleOrgUnitInfo saleOrg = SHEHelper.getCurrentSaleOrg();

	private Map map = new HashMap();
	private Map tableListenersMap = new HashMap();
	
	private RoomSignContractFilterUI filterUI = null;
	private CommonQueryDialog commonQueryDialog = null;
	
	private RoomSignContractFilterUI getFilterUI() {
		if (this.filterUI == null) {
			try {
				this.filterUI = new RoomSignContractFilterUI();
				this.filterUI.onLoad();
			} catch (Exception e) {
				e.printStackTrace();
				abort(e);
			}
		}
		return this.filterUI;
	}
	
	protected CommonQueryDialog initCommonQueryDialog() {
		if (commonQueryDialog != null) {
			return commonQueryDialog;
		}
		commonQueryDialog = super.initCommonQueryDialog();
		commonQueryDialog.setWidth(400);
		commonQueryDialog.addUserPanel(this.getFilterUI());
		commonQueryDialog.setShowSorter(true);
		commonQueryDialog.setShowFilter(true);
		return commonQueryDialog;
	}

	/**
	 * output class constructor
	 */
	public RoomSignContractListUI() throws Exception {
		super();
	}

	/**
	 * output storeFields method
	 */
	public void storeFields() {
		super.storeFields();
	}

	/**
	 * output tblMain_tableClicked method
	 */
	protected void tblMain_tableClicked(com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e) throws Exception {
		super.tblMain_tableClicked(e);
	}

	protected void tblMain_doRequestRowSet(RequestRowSetEvent e) {
		super.tblMain_doRequestRowSet(e);
	}

	/**
	 * output tblMain_tableSelectChanged method
	 */
	protected void tblMain_tableSelectChanged(com.kingdee.bos.ctrl.kdf.table.event.KDTSelectEvent e) throws Exception {
	}

	/**
	 * output menuItemImportData_actionPerformed method
	 */
	protected void menuItemImportData_actionPerformed(java.awt.event.ActionEvent e) throws Exception {
		super.menuItemImportData_actionPerformed(e);
	}

	protected void initTree() throws Exception {
		this.treeMain.setModel(SHEHelper.getUnitTree(this.actionOnLoad, MoneySysTypeEnum.SalehouseSys,true));
		this.treeMain.expandAllNodes(true, (TreeNode) this.treeMain.getModel().getRoot());
	}

	protected void afterTableFillData(KDTDataRequestEvent e) {
		super.afterTableFillData(e);
		
		int sta = e.getFirstCol();
		int end = e.getLastRow();
		
		for(int i=sta; i<=end; i++){
			IRow row = this.tblMain.getRow(i);
			if(row == null){
				continue;
			}
			
			String id = (String) row.getCell("id").getValue();
			row.getCell("paymentInfo").setValue(map.get(id));
		}
		
	}

	protected void getRowSetBeforeFillTable(IRowSet rowSet) {
		try {
			fillPaymentInfo(rowSet);
		} catch (BOSException e) {
			this.handleException(e);
		} catch (SQLException e) {
			this.handleException(e);
		}
	}
	
	private void removeTableFillListener(JComponent com) {
		EventListener[] listeners = null;
		if (com instanceof KDSpinner) {
			listeners = com.getListeners(ChangeListener.class);
			for (int i = 0; i < listeners.length; i++) {
				((KDTable) com).removeKDTDataFillListener((KDTDataFillListener) listeners[i]);
			}
		}

		if (listeners != null && listeners.length > 0) {
			tableListenersMap.put(com, listeners);
		}
	}

	private void addTableFillListener(JComponent com) {
		EventListener[] listeners = (EventListener[]) tableListenersMap.get(com);
		if (listeners != null && listeners.length > 0) {
			if (com instanceof KDSpinner) {
				for (int i = 0; i < listeners.length; i++) {
					((KDTable) com).addKDTDataFillListener((KDTDataFillListener) listeners[i]);
				}
			}
		}
	}

	/**
	 * ��ǩ output actionWebMark_actionPerformed method
	 */
	public void actionWebMark_actionPerformed(ActionEvent e) throws Exception {
		checkSelected();
		UIContext uiContext = new UIContext(this);
		String sOprt = OprtState.ADDNEW;
		uiContext.put(UIContext.ID, null);
		uiContext.put(UIContext.OWNER, this);
		String sellProjId = "";
		try {
			// ��Ŀ
			DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeMain.getLastSelectedPathComponent();
			if (node.getUserObject() instanceof BuildingUnitInfo) {
				BuildingInfo building = (BuildingInfo) ((DefaultKingdeeTreeNode) node.getParent()).getUserObject();
				SellProjectInfo sellProjInfo = building.getSellProject();
				sellProjId = sellProjInfo.getId().toString();
			} else if (node.getUserObject() instanceof BuildingInfo) {
				BuildingInfo building = (BuildingInfo) node.getUserObject();
				if (!building.getCodingType().equals(CodingTypeEnum.UnitFloorNum)) {
					// buildingId = building.getId().toString();
					SellProjectInfo sellProjInfo = building.getSellProject();
					sellProjId = sellProjInfo.getId().toString();
				}
			}
			// ����
			ICell cell = tblMain.getRow(tblMain.getSelectManager().getActiveRowIndex()).getCell("room.number");
			String roomNumber = cell.getValue().toString();

			// String roomID = this.tblMain.getCell(
			// tblMain.getSelectManager().getActiveRowIndex(), "id")
			// .toString();
			if (sellProjId.trim().equals("")) {
				FDCMsgBox.showInfo("��ĿȡֵΪ�գ�");
				return;
			}
			if (roomNumber.trim().equals("")) {
				FDCMsgBox.showInfo("����ȡֵΪ�գ�");
				return;
			}
			uiContext.put("sellProjID", sellProjId);
			uiContext.put("roomNumber", roomNumber);
//			IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.NEWTAB).create(WebMarkPutInEditUI.class.getName(), uiContext, null, sOprt);
			uiWindow.show();
		} catch (Exception ex) {
			ex = null;
		}
	}

	protected void treeMain_valueChanged(javax.swing.event.TreeSelectionEvent e) throws Exception {
		DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeMain.getLastSelectedPathComponent();
		if (node == null) {
			return;
		}
		this.actionRevSearch.setEnabled(true);
		if (node.getUserObject() instanceof OrgStructureInfo) {
			this.actionRevSearch.setEnabled(false);
		}		
		this.execQuery();
	}

	private String getRoomId(IRowSet rowSet) throws SQLException{
		String temp = "";
		StringBuffer sb = new StringBuffer();
		try {
			while(rowSet.next()){
				sb.append("'");
				sb.append(rowSet.getString("id"));
				sb.append("'");
				sb.append(",");
			}
		} catch (SQLException e) {
			logger.error("get room id has error!"+e.getMessage());
		}
		
		if(sb.length()>0){
			temp = "("+sb.toString().substring(0, sb.toString().length()-1)+")";
		}
		rowSet.beforeFirst();
		return temp;
	}
	
	private void fillPaymentInfo(IRowSet rowSet) throws BOSException, SQLException {
		
		String roomIds = getRoomId(rowSet);
		
		FDCSQLBuilder builder = new FDCSQLBuilder();
		builder.appendSql(" select ROOMSIGNCONTRACT.fid as id, ");		
		
		builder.appendSql(" sum(case when FisEarnestInHouseAmount=1   ");
		builder.appendSql("          and (moneyDefine.FMoneyType='FisrtAmount' or moneyDefine.FMoneyType='HouseAmount'   ");
		builder.appendSql("               or moneyDefine.FMoneyType='PurchaseAmount') then payListEntry.FAppAmount        ");
		builder.appendSql("          when FisEarnestInHouseAmount=0                                                      ");
		builder.appendSql("          and (moneyDefine.FMoneyType='FisrtAmount' or moneyDefine.FMoneyType='HouseAmount')  ");
		builder.appendSql("                                                           then payListEntry.FAppAmount        ");
		builder.appendSql("          else 0 end) as apAmount,		                                                     ");
		builder.appendSql(" sum(case when FisEarnestInHouseAmount=1                                                      ");
		builder.appendSql("          and (moneyDefine.FMoneyType='FisrtAmount' or moneyDefine.FMoneyType='HouseAmount'   ");
		builder.appendSql("               or moneyDefine.FMoneyType='PurchaseAmount') then payListEntry.FActrevAmount    ");
		builder.appendSql("          when FisEarnestInHouseAmount=0                                                      ");
		builder.appendSql("          and (moneyDefine.FMoneyType='FisrtAmount' or moneyDefine.FMoneyType='HouseAmount')  ");
		builder.appendSql("                                                           then payListEntry.FActrevAmount    ");
		builder.appendSql("          else 0 end) as actPayAmount                                                         ");
		
		builder.appendSql(" FROM T_SHE_RoomSignContract  ROOMSIGNCONTRACT  ");
		builder.appendSql(" LEFT OUTER JOIN T_SHE_Purchase   PURCHASE ");
		builder.appendSql("  ON  ROOMSIGNCONTRACT.FPurchaseID =  PURCHASE.FID  ");
		builder.appendSql(" left join T_SHE_PurchasePayListEntry   payListEntry  ");
		builder.appendSql(" on payListEntry.FHeadID=PURCHASE.FID  ");
		builder.appendSql(" left join T_SHE_MoneyDefine moneyDefine on  ");
		builder.appendSql(" moneyDefine.fid=payListEntry.FMoneyDefineID   ");
		builder.appendSql(" LEFT OUTER JOIN T_SHE_Room ROOM ");
		builder.appendSql(" ON ROOMSIGNCONTRACT.FRoomID = ROOM.FID ");
		builder.appendSql(" INNER JOIN T_SHE_Building BUILDING ");
		builder.appendSql(" ON ROOM.FBuildingID = BUILDING.FID ");
		builder.appendSql(" LEFT OUTER JOIN T_SHE_BuildingUnit BUILDUNIT ");
		builder.appendSql(" ON ROOM.FBuildUnitID = BUILDUNIT.FID ");
		builder.appendSql(" INNER JOIN T_SHE_SellProject SELLPROJECT ");
		builder.appendSql(" ON BUILDING.FSellProjectID = SELLPROJECT.FID ");
		builder.appendSql(" LEFT OUTER JOIN T_SHE_Subarea SUBAREA ");
		builder.appendSql(" ON BUILDING.FSubareaID = SUBAREA.FID ");
		builder.appendSql(" where  1=1 ");
//		builder.appendSql(" moneyDefine.FMoneyType in ('FisrtAmount','HouseAmount','PurchaseAmount') ");
		DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeMain.getLastSelectedPathComponent();
		if(node!=null){
			if (node.getUserObject() instanceof BuildingUnitInfo) {
				BuildingUnitInfo buildUnit = (BuildingUnitInfo) node.getUserObject();
				builder.appendSql(" and buildUnit.fid='" + buildUnit.getId() + "'  ");
			} else if (node.getUserObject() instanceof BuildingInfo) {
				BuildingInfo building = (BuildingInfo) node.getUserObject();
				builder.appendSql(" and building.fid='" + building.getId() + "'  ");
			} else if (node.getUserObject() instanceof SellProjectInfo) {
				SellProjectInfo sellProInfo = (SellProjectInfo) node.getUserObject();
				builder.appendSql(" and sellProject.fid='" + sellProInfo.getId() + "'  ");
			} else if (node.getUserObject() instanceof SubareaInfo) {
				SubareaInfo subAreaInfo = (SubareaInfo) node.getUserObject();
				builder.appendSql(" and subarea.fid='" + subAreaInfo.getId() + "'  ");
			} else {
				builder.appendSql(" and ROOMSIGNCONTRACT.fid is not null ");
			}	
		}
		if(roomIds != null  &&  roomIds.length() != 0){
			builder.appendSql(" and ROOMSIGNCONTRACT.fid in ").appendSql(roomIds);
		}
		builder.appendSql(" group by ROOMSIGNCONTRACT.fid ");
		IRowSet termQuitSellSet = builder.executeQuery();
		map.clear();
		while(termQuitSellSet.next()){
			String roomSignContractId = termQuitSellSet.getString("id");
			BigDecimal apAmount = FDCHelper.toBigDecimal(termQuitSellSet.getString("apAmount"));
			BigDecimal payAmount = FDCHelper.toBigDecimal(termQuitSellSet.getString("actPayAmount"));		
			if (payAmount.compareTo(apAmount) >= 0&&payAmount.compareTo(FDCHelper.ZERO)>0) {
				map.put(roomSignContractId, Boolean.TRUE);
			} else {
				map.put(roomSignContractId, Boolean.FALSE);
			}
		}
		
		

	}

	private void getAllRow() {
//		rowMap = new HashMap();
//		for (int i = 0; i < this.tblMain.getRowCount(); i++) {
//			IRow row = this.tblMain.getRow(i);
//			if (row != null) {
//				this.rowMap.put(row.getCell("id").getValue().toString(), row);
//			}
//
//		}

	}

	private FilterInfo getRoomFilter() {
		DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeMain.getLastSelectedPathComponent();
		FilterInfo roomFilter = new FilterInfo();
		if (node.getUserObject() instanceof BuildingUnitInfo) {
			BuildingUnitInfo buildUnit = (BuildingUnitInfo) node.getUserObject();
			roomFilter.getFilterItems().add(new FilterItemInfo("buildUnit.id", buildUnit.getId()));
		} else if (node.getUserObject() instanceof BuildingInfo) {
			BuildingInfo building = (BuildingInfo) node.getUserObject();
			roomFilter.getFilterItems().add(new FilterItemInfo("building.id", building.getId()));
		} else if (node.getUserObject() instanceof SellProjectInfo) {
			SellProjectInfo sellProInfo = (SellProjectInfo) node.getUserObject();
			roomFilter.getFilterItems().add(new FilterItemInfo("sellProject.id", sellProInfo.getId()));
		} else if (node.getUserObject() instanceof SubareaInfo) {
			SubareaInfo subAreaInfo = (SubareaInfo) node.getUserObject();
			roomFilter.getFilterItems().add(new FilterItemInfo("subarea.id", subAreaInfo.getId()));
		} else {
//			�����۲��Ľڵ��£�Ҫ����ʾ���еģ��򲻼ӹ�������
//			roomFilter.getFilterItems().add(new FilterItemInfo("id", null));
		}
		return roomFilter;
	}

	protected IQueryExecutor getQueryExecutor(IMetaDataPK queryPK, EntityViewInfo viewInfo) {
		/*try {
			FilterInfo roomFilter = getRoomFilter();
			viewInfo = (EntityViewInfo) this.mainQuery.clone();
			if (viewInfo.getFilter() != null) {
				viewInfo.getFilter().mergeFilter(roomFilter, "and");
			} else {
				viewInfo.setFilter(roomFilter);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return super.getQueryExecutor(queryPK, viewInfo);*/
		DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeMain.getLastSelectedPathComponent();
		if(node==null) node = (DefaultKingdeeTreeNode)treeMain.getModel().getRoot();
		
		String allSpIdStr = FDCTreeHelper.getStringFromSet(FDCTreeHelper.getAllObjectIdMap(node, "SellProject").keySet());
		if(allSpIdStr.trim().length()==0)
			allSpIdStr = "'nullnull'"; 
		
		try	{
			FilterInfo filter = new FilterInfo();
			if(node!=null){
				if (node.getUserObject() instanceof SellProjectInfo)		{
					SellProjectInfo spInfo = (SellProjectInfo)node.getUserObject();
					filter.getFilterItems().add(new FilterItemInfo("sellProject.id", spInfo.getId().toString()));
				}else if (node.getUserObject() instanceof SubareaInfo)		{
					SubareaInfo subInfo = (SubareaInfo) node.getUserObject();
					filter.getFilterItems().add(new FilterItemInfo("subarea.id", subInfo.getId().toString()));
				}else if (node.getUserObject() instanceof BuildingInfo)		{
					BuildingInfo bdInfo = (BuildingInfo)node.getUserObject();
					filter.getFilterItems().add(new FilterItemInfo("building.id", bdInfo.getId().toString()));
				}else if (node.getUserObject() instanceof BuildingUnitInfo)		{
					//BuildingInfo bdInfo = (BuildingInfo)((DefaultKingdeeTreeNode)node.getParent()).getUserObject();
					BuildingUnitInfo buInfo = (BuildingUnitInfo)node.getUserObject();
					/*filter.getFilterItems().add(new FilterItemInfo("building.id", bdInfo.getId().toString()));
					filter.getFilterItems().add(new FilterItemInfo("room.unit", new Integer(buInfo.getSeq())));
					�����ѽ���ΪbuildUnit����ֶ� ������Ĺ�������Ҳ�ĵ� xiaoao_liu*/
					filter.getFilterItems().add(new FilterItemInfo("room.buildUnit.id", buInfo.getId().toString()));
				}
			}
			if(filter.getFilterItems().size()==0)
				filter.getFilterItems().add(new FilterItemInfo("sellProject.id", allSpIdStr ,CompareType.INNER));
			
			
			viewInfo = (EntityViewInfo) this.mainQuery.clone();
			if (viewInfo.getFilter() != null)
			{
				viewInfo.getFilter().mergeFilter(filter, "and");
			} else
			{
				viewInfo.setFilter(filter);
			}
		} catch (Exception e)
		{
			handleException(e);
		}
		return super.getQueryExecutor(queryPK, viewInfo);
	}

	public void onLoad() throws Exception {
		this.actionWebMark.setEnabled(true);
		this.actionPullDown.setEnabled(true);
		this.actionBlankOut.setVisible(true);
		this.actionBlankOut.setEnabled(true);
		FDCClientHelper.addSqlMenu(this, this.menuEdit);
		super.onLoad();
		if (!saleOrg.isIsBizUnit()) {
			this.actionAddNew.setEnabled(false);
			this.actionEdit.setEnabled(false);
			this.actionRemove.setEnabled(false);
		}

		this.actionCreateTo.setVisible(false);
		this.actionTraceDown.setVisible(false);
		this.actionTraceUp.setVisible(false);
		this.actionAuditResult.setVisible(false);
		this.actionVoucher.setVisible(true);
		this.actionDelVoucher.setVisible(true);
		this.actionVoucher.setEnabled(true);
		this.actionDelVoucher.setEnabled(true);

		//���˵���������ť PT431654
		this.actionRevSearch.setVisible(false);
		initTree();
		this.treeMain.setSelectionRow(0);
		this.actionVoucher.setVisible(false);
		this.actionDelVoucher.setVisible(false);

	}

	protected void prepareUIContext(UIContext uiContext, ActionEvent e) {
		super.prepareUIContext(uiContext, e);
		uiContext.put(UIContext.ID, getSelectedKeyValue());
		DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeMain.getLastSelectedPathComponent();
		if (node.getUserObject() instanceof BuildingUnitInfo) {
			BuildingUnitInfo buildUnit = (BuildingUnitInfo) node.getUserObject();
			uiContext.put("buildUnit", buildUnit);
			BuildingInfo building = (BuildingInfo) ((DefaultKingdeeTreeNode) node.getParent()).getUserObject();
			uiContext.put("building", building);
		} else if (node.getUserObject() instanceof BuildingInfo) {
			BuildingInfo building = (BuildingInfo) node.getUserObject();
			if (!building.getCodingType().equals(CodingTypeEnum.UnitFloorNum)) {
				uiContext.put("building", building);
				uiContext.put("unit", new Integer(0));
			}
		}
	}

	protected String getEditUIName() {
		return RoomSignContractEditUI.class.getName();
	}

	protected ICoreBase getBizInterface() throws Exception {
		return RoomSignContractFactory.getRemoteInstance();
	}

	protected String getEditUIModal() {
		return UIFactoryName.NEWTAB;
	}

	protected ITreeBase getTreeInterface() throws Exception {
		return null;
	}

	protected void refresh(ActionEvent e) throws Exception {
		this.tblMain.removeRows();
		treeMain_valueChanged(null);
	}

	public void actionRemove_actionPerformed(ActionEvent e) throws Exception {
		checkSelected();
		String id = this.getSelectedKeyValue();
		if (id != null) {
			SelectorItemCollection sels = new SelectorItemCollection();
			sels.add("*");
			sels.add("room.*");
			sels.add("purchase.id");
			sels.add("purchase.name");
			sels.add("purchase.number");
			RoomSignContractInfo sign = RoomSignContractFactory.getRemoteInstance().getRoomSignContractInfo(new ObjectUuidPK(BOSUuid.read(id)), sels);
			
			//�Ƿ����������
			if(RoomAreaCompensateFactory.getRemoteInstance().exists("where room.id='"+sign.getRoom().getId()+"'")){
				MsgBox.showInfo("��ǩԼ���ķ����Ѵ�������������������ɾ��������");
				return;				
			} 
			
			if (!FDCBillStateEnum.SAVED.equals(sign.getState())) {
				MsgBox.showInfo("����״̬��ǩԼ������ɾ��!");
				return;
			}
			if (confirmRemove()) {
//				 prepareRemove(null).callHandler();
				if(sign.isIsImmediacySign()){
					String purchaseId = sign.getPurchase().getId().toString();
					FilterInfo filter = new FilterInfo();
					filter.getFilterItems().add(
							new FilterItemInfo("purchase.id", purchaseId));
					if (!FDCReceiveBillFactory.getRemoteInstance().exists(filter))
					{
						SelectorItemCollection sic2=new SelectorItemCollection();
						sic2.add("*");
						sic2.add("sincerityPurchase.*");
						sic2.add("payListEntry.*");
						sic2.add("elsePayListEntry.*");
						sic2.add("room.*");
						PurchaseInfo purInfo = PurchaseFactory.getRemoteInstance()
							.getPurchaseInfo(new ObjectUuidPK(purchaseId),sic2);
						boolean hasRev = false;	//ֻҪ�չ�������տ�
						for(int i=0;i<purInfo.getPayListEntry().size();i++) {
							PurchasePayListEntryInfo payEntry = purInfo.getPayListEntry().get(i);
							if(payEntry.getActRevAmount().compareTo(new BigDecimal(0))>0) {
								hasRev = true;
								break;
							}
						}
						if(!hasRev) {
							for(int i=0;i<purInfo.getElsePayListEntry().size();i++) {
								PurchaseElsePayListEntryInfo elseEntry = purInfo.getElsePayListEntry().get(i);
								if(elseEntry.getActRevAmount().compareTo(new BigDecimal(0))>0) {
									hasRev = true;
									break;
								}
							}	
						}
						
						if (!hasRev && purInfo.getPrePurchaseDate()==null)		{
							RoomSellStateEnum stateEnum = RoomSellStateEnum.OnShow;
							//������ĳ����Ϲ���Ϊ�ź�״̬ 
							SincerityPurchaseInfo spur = null;
							if(purInfo.getSincerityPurchase() != null){
								spur = purInfo.getSincerityPurchase();
								spur.setSincerityState(SincerityPurchaseStateEnum.ArrangeNum);
								SelectorItemCollection sic  = new SelectorItemCollection();
								sic.add("sincerityState");
								SincerityPurchaseFactory.getRemoteInstance().updatePartial(spur, sic);
								if(purInfo.getSincerityPurchase().getRoom()!=null){
									stateEnum = RoomSellStateEnum.SincerPurchase;
								}else{
									stateEnum = RoomSellStateEnum.OnShow;
								}
							}
							
							RoomInfo roomInfo =  purInfo.getRoom();
							roomInfo.setSellState(stateEnum);
							roomInfo.setDealPrice(null);
							roomInfo.setDealTotalAmount(null);
							roomInfo.setSellAmount(null);
							roomInfo.setSaleArea(null);
							roomInfo.setToPurchaseDate(null);
							roomInfo.setToSaleDate(null);
							roomInfo.setToPrePurchaseDate(null);
							roomInfo.setToSignDate(null);
							roomInfo.setLastPurchase(null);
							roomInfo.setLastSignContract(null);
							roomInfo.setLastAreaCompensate(null);
							roomInfo.setAreaCompensateAmount(null);
							SelectorItemCollection roomUpdateSel = new SelectorItemCollection();					
							roomUpdateSel.add("sellState");
							roomUpdateSel.add("dealPrice");
							roomUpdateSel.add("dealTotalAmount");
							roomUpdateSel.add("sellAmount");
							roomUpdateSel.add("saleArea");
							roomUpdateSel.add("toPurchaseDate");
							roomUpdateSel.add("toSaleDate");
							roomUpdateSel.add("toPrePurchase");
							roomUpdateSel.add("toSignDate");
							roomUpdateSel.add("lastPurchase");
							roomUpdateSel.add("lastSignContract");
							roomUpdateSel.add("lastAreaCompensate");
							roomUpdateSel.add("areaCompensateAmount");
							RoomFactory.getRemoteInstance().updatePartial(roomInfo, roomUpdateSel);
							
							ProcessInstInfo instInfo = null;
							ProcessInstInfo[] procInsts = null;
							IEnactmentService service = EnactmentServiceFactory
									.createRemoteEnactService();
							procInsts = service.getProcessInstanceByHoldedObjectId(purInfo.getId()
									.toString());
							for (int j = 0; j < procInsts.length; j++)
							{
								if ("open.running".equals(procInsts[j].getState()))
								{
									instInfo = procInsts[j];
									service.abortProcessInst(instInfo.getProcInstId());
								}

							}
							FDCSQLBuilder sql=new FDCSQLBuilder();
							sql.appendSql("delete T_SHE_Purchase where fid = '"+purchaseId+"'");
							sql.execute();
						}
					}
				}
				Remove();
				refresh(e);
			}
		}
	}

	public void actionEdit_actionPerformed(ActionEvent e) throws Exception {
		checkSelected();
		String id = this.getSelectedKeyValue();
		RoomSignContractInfo info = RoomSignContractFactory.getRemoteInstance().getRoomSignContractInfo(new ObjectUuidPK(BOSUuid.read(id)));
		if (info.getState() != null && info.getState().equals(FDCBillStateEnum.INVALID)) {
			MsgBox.showInfo("ǩԼ���Ѿ��ϳ�,�����޸ģ�");
			this.abort();
		}
		IRow row = KDTableUtil.getSelectedRow(tblMain);
		if(FDCBillStateEnum.AUDITTED.toString().equals(row.getCell("state").getValue().toString())){
			MsgBox.showInfo("ǩԼ��������,�����޸ģ�");
			this.abort();
		}
		super.actionEdit_actionPerformed(e);
	}

	protected boolean isIgnoreCUFilter() {
		return true;
	}

	protected String getLongNumberFieldName() {
		return "number";
	}

	public void actionAudit_actionPerformed(ActionEvent e) throws Exception {
		int rowIndex = this.tblMain.getSelectManager().getActiveRowIndex();
		if (rowIndex == -1) {
			MsgBox.showInfo("��ѡ����!");
			return;
		}
		IRow row = this.tblMain.getRow(rowIndex);
		BizEnumValueInfo billState = (BizEnumValueInfo) row.getCell("state").getValue();

		if (!FDCBillStateEnum.SUBMITTED_VALUE.equalsIgnoreCase((String) billState.getValue())) {
			MsgBox.showInfo("�õ��ݲ����ύ״̬�����ܽ�������������");
			return;
		}

		super.actionAudit_actionPerformed(e);

		String id = (String) row.getCell(this.getKeyFieldName()).getValue();
		RoomSignContractFactory.getRemoteInstance().audit(BOSUuid.read(id));
		this.refresh(null);
	}

	public void actionUnAudit_actionPerformed(ActionEvent e) throws Exception {
		super.actionUnAudit_actionPerformed(e);
		int rowIndex = this.tblMain.getSelectManager().getActiveRowIndex();
		if (rowIndex == -1) {
			MsgBox.showInfo("��ѡ����!");
			return;
		}
		IRow row = this.tblMain.getRow(rowIndex);
		BizEnumValueInfo billState = (BizEnumValueInfo) row.getCell("state").getValue();

		if (!FDCBillStateEnum.AUDITTED_VALUE.equalsIgnoreCase((String) billState.getValue())) {
			MsgBox.showInfo("�õ��ݲ������״̬�����ܽ��з���������!");
			return;
		}

		String id = (String) row.getCell(this.getKeyFieldName()).getValue();
		if (MsgBox.showConfirm2New(this, "ȷ�϶Ըõ��ݽ��з���˲�����?") == MsgBox.YES) {

			RoomSignContractFactory.getRemoteInstance().unAudit(BOSUuid.read(id));
			this.refresh(null);
		}
	}

	public void actionBlankOut_actionPerformed(ActionEvent e) throws Exception {
		int rowIndex = this.tblMain.getSelectManager().getActiveRowIndex();
		if (rowIndex == -1) {
			MsgBox.showInfo("��ѡ����!");
			return;
		}
		IRow row = this.tblMain.getRow(rowIndex);

		String id = (String) row.getCell(this.getKeyFieldName()).getValue();

		if (MsgBox.showConfirm2New(this, "���Ϻ󲻿ɻָ�����ȷ���Ƿ�����?") == MsgBox.YES) {

			RoomSignContractInfo roomSignContract = RoomSignContractFactory.getRemoteInstance().getRoomSignContractInfo(new ObjectUuidPK(BOSUuid.read(id)));

			if (!FDCBillStateEnum.SAVED.equals(roomSignContract.getState()) && !FDCBillStateEnum.SUBMITTED.equals(roomSignContract.getState())) {
				MsgBox.showInfo("ֻ�б�������ύ�ĵ��ݲſ��Խ������ϲ���!");
				return;
			}
			
			//�Ƿ����������
			if(RoomAreaCompensateFactory.getRemoteInstance().exists("where room.id='"+roomSignContract.getRoom().getId()+"'")){
				MsgBox.showInfo("��ǩԼ���ķ����Ѵ����������������������ϲ�����");
				return;				
			} 
			
			roomSignContract.setState(FDCBillStateEnum.INVALID);
			roomSignContract.setIsBlankOut(true);
			SelectorItemCollection sels = new SelectorItemCollection();
			sels.add("isBlankOut");
			sels.add("state");

			RoomSignContractFactory.getRemoteInstance().updatePartial(roomSignContract, sels);

			//�ѷ���״̬�ĳ��Ϲ�״̬
			sels = new SelectorItemCollection();
			sels.add("sellState");
			roomSignContract.getRoom().setSellState(RoomSellStateEnum.Purchase);
			RoomFactory.getRemoteInstance().updatePartial(roomSignContract.getRoom(), sels);			

			
			ProcessInstInfo instInfo = null;
			ProcessInstInfo[] procInsts = null;
			IEnactmentService service = EnactmentServiceFactory.createRemoteEnactService();
			procInsts = service.getProcessInstanceByHoldedObjectId(roomSignContract.getId().toString());
			for (int j = 0; j < procInsts.length; j++) {
				if ("open.running".equals(procInsts[j].getState())) {
					instInfo = procInsts[j];
					service.abortProcessInst(instInfo.getProcInstId());
				}

			}
			//�����ֱ��ǩԼ����ͬʱ�����Ϲ���
			if(roomSignContract.isIsImmediacySign()){
				blankOutPurchase(roomSignContract.getPurchase().getId().toString());
			}
			this.refresh(null);
		}
		super.actionBlankOut_actionPerformed(e);
	}
	
	/**
	 * ֱ��ǩԼ����ʱ��ͬʱ�����Ϲ���
	 * @param id
	 * @throws EASBizException
	 * @throws BOSException
	 */
	public void blankOutPurchase(String id) throws EASBizException, BOSException{
		SelectorItemCollection sic2=new SelectorItemCollection();
		sic2.add("*");
		sic2.add("sincerityPurchase.*");
		sic2.add("payListEntry.*");
		sic2.add("elsePayListEntry.*");
		sic2.add("room.*");
		PurchaseInfo purInfo = PurchaseFactory.getRemoteInstance()
			.getPurchaseInfo(new ObjectUuidPK(id),sic2);
		
		boolean hasRev = false;	//ֻҪ�չ�������տ�
		for(int i=0;i<purInfo.getPayListEntry().size();i++) {
			PurchasePayListEntryInfo payEntry = purInfo.getPayListEntry().get(i);
			if(payEntry.getActRevAmount().compareTo(new BigDecimal(0))>0) {
				hasRev = true;
				break;
			}
		}
		if(!hasRev) {
			for(int i=0;i<purInfo.getElsePayListEntry().size();i++) {
				PurchaseElsePayListEntryInfo elseEntry = purInfo.getElsePayListEntry().get(i);
				if(elseEntry.getActRevAmount().compareTo(new BigDecimal(0))>0) {
					hasRev = true;
					break;
				}
			}	
		}
		
		if (hasRev)		{
			return;
		}else{
			purInfo.setState(FDCBillStateEnum.INVALID);
			purInfo.setPurchaseState(PurchaseStateEnum.ManualBlankOut);
			SelectorItemCollection sels = new SelectorItemCollection();
			sels.add("state");
			sels.add("purchaseState");
			PurchaseFactory.getRemoteInstance().updatePartial(purInfo, sels);

			RoomSellStateEnum stateEnum = RoomSellStateEnum.OnShow;
			//������ĳ����Ϲ���Ϊ�ź�״̬ 
			SincerityPurchaseInfo spur = null;
			if(purInfo.getSincerityPurchase() != null){
				spur = purInfo.getSincerityPurchase();
				spur.setSincerityState(SincerityPurchaseStateEnum.ArrangeNum);
				SelectorItemCollection sic  = new SelectorItemCollection();
				sic.add("sincerityState");
				SincerityPurchaseFactory.getRemoteInstance().updatePartial(spur, sic);
				if(purInfo.getSincerityPurchase().getRoom()!=null){
					stateEnum = RoomSellStateEnum.SincerPurchase;
				}else{
					stateEnum = RoomSellStateEnum.OnShow;
				}
			}
//			if(purInfo.getPrePurchaseDate()!=null){
//				stateEnum = RoomSellStateEnum.PrePurchase;
//			}
			
			RoomInfo roomInfo =  purInfo.getRoom();
			roomInfo.setSellState(stateEnum);
			roomInfo.setDealPrice(null);
			roomInfo.setDealTotalAmount(null);
			roomInfo.setSellAmount(null);
			roomInfo.setSaleArea(null);
			roomInfo.setToPurchaseDate(null);
			roomInfo.setToSaleDate(null);
			roomInfo.setToPrePurchaseDate(null);
			roomInfo.setToSignDate(null);
			roomInfo.setLastPurchase(null);
			roomInfo.setLastSignContract(null);
			roomInfo.setLastAreaCompensate(null);
			roomInfo.setAreaCompensateAmount(null);
			SelectorItemCollection roomUpdateSel = new SelectorItemCollection();					
			roomUpdateSel.add("sellState");
			roomUpdateSel.add("dealPrice");
			roomUpdateSel.add("dealTotalAmount");
			roomUpdateSel.add("sellAmount");
			roomUpdateSel.add("saleArea");
			roomUpdateSel.add("toPurchaseDate");
			roomUpdateSel.add("toSaleDate");
			roomUpdateSel.add("toPrePurchase");
			roomUpdateSel.add("toSignDate");
			roomUpdateSel.add("lastPurchase");
			roomUpdateSel.add("lastSignContract");
			roomUpdateSel.add("lastAreaCompensate");
			roomUpdateSel.add("areaCompensateAmount");
			RoomFactory.getRemoteInstance().updatePartial(roomInfo, roomUpdateSel);
			
			ProcessInstInfo instInfo = null;
			ProcessInstInfo[] procInsts = null;
			IEnactmentService service = EnactmentServiceFactory
					.createRemoteEnactService();
			procInsts = service.getProcessInstanceByHoldedObjectId(purInfo.getId()
					.toString());
			for (int j = 0; j < procInsts.length; j++)
			{
				if ("open.running".equals(procInsts[j].getState()))
				{
					instInfo = procInsts[j];
					service.abortProcessInst(instInfo.getProcInstId());
				}

			}
		}
	}

	// ����
	public void actionOnRecord_actionPerformed(ActionEvent e) throws Exception {
		super.actionOnRecord_actionPerformed(e);
		this.checkSelected();

		int[] selectRows = KDTableUtil.getSelectedRows(this.tblMain);
		for (int i = 0; i < selectRows.length; i++) {
			IRow row = this.tblMain.getRow(selectRows[i]);
			BizEnumValueInfo contractState = (BizEnumValueInfo) row.getCell("state").getValue();
			if (contractState == null) {
				logger.warn("����������,����״̬ΪNull,���顣");
				continue;
			}

			Boolean isOnRecord = (Boolean) row.getCell("isOnRecord").getValue();
			if (isOnRecord != null && isOnRecord.booleanValue()) {
				MsgBox.showInfo(this, "��ͬ�Ѿ�����!");
				return;
			}

			if (!contractState.getValue().equals(FDCBillStateEnum.AUDITTED_VALUE)) {
				MsgBox.showInfo(this, "ֻ���������ĺ�ͬ�ſ��Ա���!");
				return;
			}
		}
	     	UIContext uiContext = new UIContext(this);
	        uiContext.put("selectedIDValues", this.getSelectedIdValues());
	        uiContext.put("lable","���Ƿ�ȷ��Ҫ������?");
	        uiContext.put("parentUI", this);
	        uiContext.put("setTitle", "����ʱ��¼��");
	        String className = DateUI.class.getName();
	        IUIFactory uiFactory = UIFactory.createUIFactory(UIFactoryName.MODEL);
	        IUIWindow uiWindow = uiFactory.create(className, uiContext);
	        uiWindow.show();
/*		if (MsgBox.showConfirm2New(this, "���Ƿ�ȷ��Ҫ������?") == MsgBox.YES) {
			RoomSignContractFactory.getRemoteInstance().onRecord(FDCHelper.idListToPKArray(this.getSelectedIdValues()));
			this.refresh(null);
		}*/
	}

	// ǩ��
	public void actionStamp_actionPerformed(ActionEvent e) throws Exception {
		super.actionStamp_actionPerformed(e);
		this.checkSelected();
		int[] selectRows = KDTableUtil.getSelectedRows(tblMain);
		for (int i = 0; i < selectRows.length; i++) {
			IRow row = this.tblMain.getRow(selectRows[i]);
			BizEnumValueInfo contractState = (BizEnumValueInfo) row.getCell("state").getValue();
			if (contractState == null) {
				logger.warn("����������,����״̬ΪNull,���顣");
				continue;
			}
			Boolean isStamp = (Boolean) row.getCell("isStamp").getValue();
			if (isStamp != null && isStamp.booleanValue()) {
				MsgBox.showInfo(this, "��ͬ�Ѿ�ǩ��!");
				return;
			}
			if (!contractState.getValue().equals(FDCBillStateEnum.AUDITTED_VALUE)) {
				MsgBox.showInfo(this, "ֻ�������ĺ�ͬ�ſ���ǩ��!");
				return;
			}
		}

        UIContext uiContext = new UIContext(this);
        uiContext.put("parentUI", this);
        uiContext.put("selectedIDValues", this.getSelectedIdValues());
        uiContext.put("lable","���Ƿ�ȷ��Ҫǩ����?");
        uiContext.put("setTitle", "ǩ��ʱ��¼��");
        String className = DateUI.class.getName();
        IUIFactory uiFactory = UIFactory.createUIFactory(UIFactoryName.MODEL);
        IUIWindow uiWindow = uiFactory.create(className, uiContext);
        uiWindow.show();
/*		if (MsgBox.showConfirm2New(this, "���Ƿ�ȷ��Ҫǩ����?") == MsgBox.YES) {
			RoomSignContractFactory.getRemoteInstance().stamp(FDCHelper.idListToPKArray(this.getSelectedIdValues()));
			this.refresh(null);
		}*/
   //     this.refreshList();
	}

	// ��ȡ
	public void actionPullDown_actionPerformed(ActionEvent e) throws Exception {
		super.actionPullDown_actionPerformed(e);
		this.checkSelected();
		int[] selectRows = KDTableUtil.getSelectedRows(tblMain);
		for (int i = 0; i < selectRows.length; i++) {
			IRow row = this.tblMain.getRow(selectRows[i]);
			BizEnumValueInfo contractState = (BizEnumValueInfo) row.getCell("state").getValue();
			if (contractState == null) {
				logger.warn("����������,����״̬ΪNull,���顣");
				continue;
			}
			Boolean isPullDown = (Boolean) row.getCell("isPullDown").getValue();
			if (isPullDown != null && isPullDown.booleanValue()) {
				MsgBox.showConfirm2(this, "��ͬ����ȡ!");
				return;
			}
			if (!contractState.getValue().equals(FDCBillStateEnum.AUDITTED_VALUE)) {
				MsgBox.showInfo(this, "ֻ�������ĺ�ͬ�ſ�����ȡ!");
				return;
			}
		}
		   UIContext uiContext = new UIContext(this);
	        uiContext.put("selectedIDValues", this.getSelectedIdValues());
	        uiContext.put("lable","���Ƿ�ȷ��Ҫ��ȡ��?");
	        uiContext.put("parentUI", this);
	        uiContext.put("setTitle", "��ȡʱ��¼��");
	        String className = DateUI.class.getName();
	        IUIFactory uiFactory = UIFactory.createUIFactory(UIFactoryName.MODEL);
	        IUIWindow uiWindow = uiFactory.create(className, uiContext);
	        uiWindow.show();
	/*	if (MsgBox.showConfirm2New(this, "���Ƿ�ȷ��Ҫ��ȡ��?") == MsgBox.YES) {
			RoomSignContractFactory.getRemoteInstance().pullDown(FDCHelper.idListToPKArray(this.getSelectedIdValues()));
			this.refresh(null);
		}*/
	}
	//ȡ������
	public void  actionUnOnRecord_actionPerformed(ActionEvent e) throws Exception
	{
		super.actionUnOnRecord_actionPerformed(e);
		this.checkSelected();
		int[] selectRows = KDTableUtil.getSelectedRows(tblMain);
		for (int i = 0; i < selectRows.length; i++) {
			IRow row = this.tblMain.getRow(selectRows[i]);
			BizEnumValueInfo contractState = (BizEnumValueInfo) row.getCell("state").getValue();
			Boolean isOnRecord = (Boolean)row.getCell("isOnRecord").getValue();
			if(contractState==null)
			{
				MsgBox.showInfo(this, "����������,����״̬ΪNull,���顣");
			}
			if (!contractState.getValue().equals(FDCBillStateEnum.AUDITTED_VALUE)) {
				MsgBox.showInfo(this, "ֻ�������ĺ�ͬ�ſ���ȡ������!");
				return;
			}
			if (isOnRecord != null && isOnRecord.booleanValue()) {
				if(MsgBox.showConfirm2(this, "���Ƿ�ȷ��Ҫȡ������?")== MsgBox.YES){
				RoomSignContractFactory.getRemoteInstance().unOnRecord(FDCHelper.idListToPKArray(this.getSelectedIdValues()));
				this.refresh(null);
				}
			}
			if(isOnRecord.booleanValue()==false)
			{
				MsgBox.showInfo(this, "ֻ���ѱ����ĺ�ͬ�ſ���ȡ����������");
				return;
			}
		}
	}
	//ȡ��ǩ��
	public void actionUnStamp_actionPerformed(ActionEvent e) throws Exception {
		// TODO Auto-generated method stub
		super.actionUnStamp_actionPerformed(e);
		this.checkSelected();
		int[] selectRows = KDTableUtil.getSelectedRows(tblMain);
		for (int i = 0; i < selectRows.length; i++) {
			IRow row = this.tblMain.getRow(selectRows[i]);
			BizEnumValueInfo contractState = (BizEnumValueInfo) row.getCell("state").getValue();
			Boolean isStamp = (Boolean)row.getCell("isStamp").getValue();
			if(contractState==null)
			{
				MsgBox.showInfo(this, "����������,����״̬ΪNull,���顣");
			}
			if (!contractState.getValue().equals(FDCBillStateEnum.AUDITTED_VALUE)) {
				MsgBox.showInfo(this, "ֻ�������ĺ�ͬ�ſ���ȡ��ǩ��!");
				return;
			}
			if(isStamp.booleanValue()==false)
			{
				MsgBox.showInfo(this, "ֻ����ǩ�µĺ�ͬ�ſ���ȡ��ǩ�²���");
				return;
			}
			if (isStamp != null && isStamp.booleanValue()) {
				if(MsgBox.showConfirm2(this, "���Ƿ�ȷ��Ҫȡ��ǩ��")==MsgBox.YES){
				RoomSignContractFactory.getRemoteInstance().unStamp(FDCHelper.idListToPKArray(this.getSelectedIdValues()));
				this.refresh(null);
				}
			}
		}
	}
	//ȡ����ȡ
	public void actionUnPullDown_actionPerformed(ActionEvent e) throws Exception {
			// TODO Auto-generated method stub
		super.actionUnPullDown_actionPerformed(e);
		this.checkSelected();
		int[] selectRows = KDTableUtil.getSelectedRows(tblMain);
		for (int i = 0; i < selectRows.length; i++) {
			IRow row = this.tblMain.getRow(selectRows[i]);
			BizEnumValueInfo contractState = (BizEnumValueInfo) row.getCell("state").getValue();
			Boolean isPullDown = (Boolean)row.getCell("isPullDown").getValue();
			if(contractState==null)
			{
				MsgBox.showInfo(this, "����������,����״̬ΪNull,���顣");
			}
			if (!contractState.getValue().equals(FDCBillStateEnum.AUDITTED_VALUE)) {
				MsgBox.showInfo(this, "ֻ�������ĺ�ͬ�ſ���ȡ����ȡ!");
				return;
			}
			if(isPullDown.booleanValue()==false)
			{
				MsgBox.showInfo(this, "ֻ������ȡ�ĺ�ͬ�ſ���ȡ����ȡ����");
				return;
			}
			if (isPullDown != null && isPullDown.booleanValue()) {
				if(MsgBox.showConfirm2(this, "���Ƿ�ȷ��Ҫȡ����ȡ��?")==MsgBox.YES){
				RoomSignContractFactory.getRemoteInstance().unPullDown(FDCHelper.idListToPKArray(this.getSelectedIdValues()));
				this.refresh(null);
				}
			}
		}
	}
	public void actionRevSearch_actionPerformed(ActionEvent e) throws Exception {
		FilterInfo roomFilter = getRoomFilter();

		UIContext uiContext = new UIContext(this);
		uiContext.put("RoomFilter", roomFilter);
		try {
			IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.MODEL).create(RoomSignGroupListUI.class.getName(), uiContext, null, OprtState.VIEW);
			uiWindow.show();
		} catch (UIException ee) {
			ee.printStackTrace();
			SysUtil.abort();
		}

	}
	
	
	public SelectorItemCollection getBOTPSelectors() {
		SelectorItemCollection supSelecor = super.getSelectors(); 
		supSelecor.add(new SelectorItemInfo("purchase.*"));
		
		return supSelecor;
	}

//	public void actionRoomSignPrint_actionPerformed(ActionEvent e)
//			throws Exception {
//		List idList = getSelectedIdValues();
//
//		if (idList == null || idList.size() == 0)
//			return;
//		BOSQueryDelegate data = new  CommonDataProvider(idList,getQuery());
//		KDNoteHelper appHlp = new KDNoteHelper();
//		appHlp.print(LOANINTBILL_FILENAME, data, SwingUtilities
//				.getWindowAncestor(this));
//		super.actionRoomSignPrint_actionPerformed(e);
//	}
//
//	public void actionRoomSignPrintView_actionPerformed(ActionEvent e)
//			throws Exception {
//		List idList = getSelectedIdValues();
//
//		if (idList == null || idList.size() == 0)
//			return;
//		BOSQueryDelegate data = new CommonDataProvider(idList,getQuery());
//		KDNoteHelper appHlp = new KDNoteHelper();
//		appHlp.printPreview(LOANINTBILL_FILENAME, data, SwingUtilities
//				.getWindowAncestor(this));
//		super.actionRoomSignPrintView_actionPerformed(e);
//	}
//	
//	private MetaDataPK getQuery(){
//		return new MetaDataPK	("com.kingdee.eas.fdc.material.app.MaterialConfirmBillQuery");
//	}
//	private static final String LOANINTBILL_FILENAME = "bim/fdc/MCB/";

}