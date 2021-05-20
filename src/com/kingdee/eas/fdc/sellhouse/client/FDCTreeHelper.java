package com.kingdee.eas.fdc.sellhouse.client;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreeNode;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.swing.KDTree;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.dao.IObjectCollection;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.dao.query.IQueryExecutor;
import com.kingdee.bos.dao.query.QueryExecutorFactory;
import com.kingdee.bos.metadata.IMetaDataPK;
import com.kingdee.bos.metadata.MetaDataPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.ItemAction;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.base.permission.UserInfo;
import com.kingdee.eas.basedata.org.FullOrgUnitInfo;
import com.kingdee.eas.basedata.org.IOrgStructure;
import com.kingdee.eas.basedata.org.NewOrgUtils;
import com.kingdee.eas.basedata.org.OUPartFIInfo;
import com.kingdee.eas.basedata.org.OrgConstants;
import com.kingdee.eas.basedata.org.OrgStructureFactory;
import com.kingdee.eas.basedata.org.OrgStructureInfo;
import com.kingdee.eas.basedata.org.OrgStructureInfo2;
import com.kingdee.eas.basedata.org.OrgTreeInfo;
import com.kingdee.eas.basedata.org.OrgUnitInfo;
import com.kingdee.eas.basedata.org.OrgViewType;
import com.kingdee.eas.basedata.org.SaleOrgUnitInfo;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.fdc.basecrm.CRMHelper;
import com.kingdee.eas.fdc.basecrm.client.CRMTreeHelper;
import com.kingdee.eas.fdc.basedata.FDCBillInfo;
import com.kingdee.eas.fdc.basedata.FDCDateHelper;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.basedata.FDCSQLFacadeFactory;
import com.kingdee.eas.fdc.basedata.MoneySysTypeEnum;
import com.kingdee.eas.fdc.sellhouse.ArchivesTypeEnum;
import com.kingdee.eas.fdc.sellhouse.BaseDataPropertyEnum;
import com.kingdee.eas.fdc.sellhouse.BuildingCollection;
import com.kingdee.eas.fdc.sellhouse.BuildingFactory;
import com.kingdee.eas.fdc.sellhouse.BuildingInfo;
import com.kingdee.eas.fdc.sellhouse.BuildingUnitCollection;
import com.kingdee.eas.fdc.sellhouse.BuildingUnitFactory;
import com.kingdee.eas.fdc.sellhouse.BuildingUnitInfo;
import com.kingdee.eas.fdc.sellhouse.FDCReceiveBillFactory;
import com.kingdee.eas.fdc.sellhouse.PlanisphereCollection;
import com.kingdee.eas.fdc.sellhouse.PlanisphereFactory;
import com.kingdee.eas.fdc.sellhouse.PlanisphereInfo;
import com.kingdee.eas.fdc.sellhouse.PlanisphereTypeEnum;
import com.kingdee.eas.fdc.sellhouse.ProjectArchivesEntryCollection;
import com.kingdee.eas.fdc.sellhouse.ProjectArchivesEntryFactory;
import com.kingdee.eas.fdc.sellhouse.ProjectArchivesEntryInfo;
import com.kingdee.eas.fdc.sellhouse.RoomDisplaySetting;
import com.kingdee.eas.fdc.sellhouse.SHEManageHelper;
import com.kingdee.eas.fdc.sellhouse.SHEProjectFactory;
import com.kingdee.eas.fdc.sellhouse.SHEProjectInfo;
import com.kingdee.eas.fdc.sellhouse.SellOrderCollection;
import com.kingdee.eas.fdc.sellhouse.SellOrderFactory;
import com.kingdee.eas.fdc.sellhouse.SellOrderInfo;
import com.kingdee.eas.fdc.sellhouse.SellProjectCollection;
import com.kingdee.eas.fdc.sellhouse.SellProjectFactory;
import com.kingdee.eas.fdc.sellhouse.SellProjectInfo;
import com.kingdee.eas.fdc.sellhouse.ShareOrgUnitCollection;
import com.kingdee.eas.fdc.sellhouse.ShareOrgUnitFactory;
import com.kingdee.eas.fdc.sellhouse.SubareaCollection;
import com.kingdee.eas.fdc.sellhouse.SubareaFactory;
import com.kingdee.eas.fdc.sellhouse.SubareaInfo;
import com.kingdee.eas.fdc.tenancy.MarketUnitControlCollection;
import com.kingdee.eas.fdc.tenancy.MarketUnitControlFactory;
import com.kingdee.eas.fdc.tenancy.MarketUnitControlInfo;
import com.kingdee.eas.fdc.tenancy.MarketingUnitFactory;
import com.kingdee.eas.fdc.tenancy.MarketingUnitInfo;
import com.kingdee.eas.fdc.tenancy.MarketingUnitMemberCollection;
import com.kingdee.eas.fdc.tenancy.MarketingUnitMemberFactory;
import com.kingdee.eas.fdc.tenancy.MarketingUnitMemberInfo;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.eas.framework.DataBaseInfo;
import com.kingdee.eas.framework.ObjectBaseInfo;
import com.kingdee.eas.framework.client.tree.DefaultLNTreeNodeCtrl;
import com.kingdee.eas.framework.client.tree.ITreeBuilder;
import com.kingdee.eas.framework.client.tree.KDTreeNode;
import com.kingdee.eas.framework.client.tree.TreeBuilderFactory;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.MsgBox;
import com.kingdee.jdbc.rowset.IRowSet;

/**
 * @author jeegan_wang ���ز� ������������
 */

public class FDCTreeHelper {
	private static final Logger log = Logger.getLogger(FDCTreeHelper.class);

	public static final String SHEPROJECT = "sheProject";
	public static final String SELLPROJECT = "sellProject";
	// ѡ������������
	public static RoomDisplaySetting set = null;

	/**
	 * ��õ�ǰ��������֯��Ԫ
	 * 
	 * @return
	 */
	public static SaleOrgUnitInfo getCurrentSaleOrg() {
		SaleOrgUnitInfo saleUnit = SysContext.getSysContext()
				.getCurrentSaleUnit();
		if (saleUnit == null) {
			MsgBox.showInfo("��ǰ��֯������������,���ܽ���!");
			SysUtil.abort();
		}
		return saleUnit;
	}

	/**
	 * ������Ŀ���������νṹ
	 * 
	 * @param actionOnLoad
	 * @param includeUser
	 * @return
	 * @throws Exception
	 * @author liang_ren969
	 */
	public static TreeModel getSHEProjectTree(ItemAction actionOnLoad,
			String type) throws Exception {
		TreeModel tree = getSaleOrgTree(actionOnLoad);
		DefaultMutableTreeNode node = (DefaultMutableTreeNode) tree.getRoot();
		Map orgIdMap = getAllObjectIdMap(node, "OrgStructure");
		String orgIdsStr = getStringFromSet(orgIdMap.keySet());
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(
				new FilterItemInfo("orgUnit.id", orgIdsStr, CompareType.INNER));
		ITreeBuilder treeBuilder = null;

		if (type.equals(SHEPROJECT)) {
			treeBuilder = TreeBuilderFactory
					.createTreeBuilder(new DefaultLNTreeNodeCtrl(
							SHEProjectFactory.getRemoteInstance()),
							Integer.MAX_VALUE, 5, filter);
		} else if (type.equals(SELLPROJECT)) {
			treeBuilder = TreeBuilderFactory
					.createTreeBuilder(new DefaultLNTreeNodeCtrl(
							SellProjectFactory.getRemoteInstance()),
							Integer.MAX_VALUE, 5, filter);
		}

		KDTree kdTree = treeBuilder.buildTree(null);

		KDTreeNode kdNode = (KDTreeNode) kdTree.getModel().getRoot();
		for (int i = 0; i < kdNode.getChildCount(); i++) { // ����֯���������¥��Ŀ�ڵ�
			KDTreeNode thisNode = (KDTreeNode) kdNode.getChildAt(i);
			KDTreeNode newNode = (KDTreeNode) thisNode.clone();
			cloneTree(newNode, thisNode);
			// newNode.setCustomIcon(EASResource.getIcon(
			// "imgTbtn_assistantaccount"));
			if (newNode.getUserObject() != null) {
				if (newNode.getUserObject() instanceof SHEProjectInfo) {
					SHEProjectInfo muInfo = (SHEProjectInfo) newNode
							.getUserObject();
					if (muInfo.getOrgUnit() != null) {
						DefaultMutableTreeNode saleOrgNode = (DefaultMutableTreeNode) orgIdMap
								.get(muInfo.getOrgUnit().getId().toString());
						if (saleOrgNode != null)
							saleOrgNode.add(newNode);
					}
				} else {
					SellProjectInfo muInfo = (SellProjectInfo) newNode
							.getUserObject();
					if (muInfo.getOrgUnit() != null) {
						DefaultMutableTreeNode saleOrgNode = (DefaultMutableTreeNode) orgIdMap
								.get(muInfo.getOrgUnit().getId().toString());
						if (saleOrgNode != null)
							saleOrgNode.add(newNode);
					}
				}
			}
			// if(!newNode.isLeaf())
			// updateSonsNodeCustomIconForSHEProject(newNode);
		}
		return tree;
	}

	public static TreeModel getSaleOrgTree(ItemAction actionOnLoad,
			OrgUnitInfo orgInfo) throws Exception {
		MetaDataPK dataPK = null;
		if (actionOnLoad != null) {
			String actoinName = actionOnLoad.getClass().getName();
			if (actoinName.indexOf("$") >= 0) {
				actoinName = actoinName.substring(actoinName.indexOf("$") + 1);
			}
			dataPK = new MetaDataPK(actoinName);
		}
		TreeModel tree = NewOrgUtils.getTreeModel(OrgViewType.SALE, "", orgInfo
				.getId().toString(), null, dataPK);
		return tree;
	}

	/**
	 * ����CU�޹ص�������֯��-ȫ����
	 * 
	 * @param actionOnLoad
	 * @return
	 * @throws Exception
	 */
	public static DefaultTreeModel getSaleOrgTreeForAll(
			ItemAction actionOnLoad, String orgViewID, String rootUnitID,
			boolean isContainSealUp, boolean isBizUnit) throws Exception {

		IRowSet rows = buildViewRowSetForAll(orgViewID, rootUnitID,
				isContainSealUp, isBizUnit);
		DefaultKingdeeTreeNode root = buildTreeByRowSet(rows);
		DefaultTreeModel treeModel = new DefaultTreeModel(root);
		/*
		 * if (isQueryBySortCode) {
		 * NewOrgViewHelper.reBuildBySortCode(treeModel, selectInfo); }
		 */
		return treeModel;
	}

	public static DefaultKingdeeTreeNode buildTreeByRowSet(IRowSet rowset)
			throws Exception {
		if (rowset == null) {
			return null;
		}

		if (rowset.size() == 0) {
			return new DefaultKingdeeTreeNode();
		}

		DefaultKingdeeTreeNode rootTreeNode = null;
		HashMap nodeMap = new HashMap();

		try {
			while (rowset.next()) {
				OrgStructureInfo nodeInfo = createOrgStructureInfo(rowset,
						false);
				if (rootTreeNode == null) {
					rootTreeNode = new DefaultKingdeeTreeNode(nodeInfo);
					nodeMap.put(nodeInfo.getId().toString(), rootTreeNode);
					// rootTreeNode.setCustomIcon(EASResource.getIcon(
					// "imgTree_businessgroup"));
				} else {
					DefaultKingdeeTreeNode subTreeNode = new DefaultKingdeeTreeNode(
							nodeInfo);
					// temperarily used: multi root cus, need removed
					if (nodeInfo.getParent() != null
							&& nodeInfo.getParent().getId() != null) {
						String parentID = nodeInfo.getParent().getId()
								.toString();
						if (nodeMap.containsKey(parentID)) {
							((DefaultKingdeeTreeNode) nodeMap.get(parentID))
									.add(subTreeNode);
						}
					}

					nodeMap.put(nodeInfo.getId().toString(), subTreeNode);
					// subTreeNode.setCustomIcon(EASResource.getIcon(
					// "imgTree_company"));
				}
			}
		} catch (SQLException e) {
			throw new Exception(e);
		}

		return rootTreeNode;
	}

	private static OrgStructureInfo createOrgStructureInfo(IRowSet row,
			boolean isRevised) throws SQLException {
		OrgStructureInfo structInfo = null;
		if (isRevised) {
			structInfo = new OrgStructureInfo2();
		} else {
			structInfo = new OrgStructureInfo();
		}

		structInfo.setId(BOSUuid.read(row.getString("id")));
		structInfo.setLongNumber(row.getString("longNumber"));
		structInfo.setSortCode(row.getString("sortCode"));
		structInfo.setLevel(row.getInt("level"));
		structInfo.setIsLeaf(row.getBoolean("isLeaf"));
		structInfo.setIsValid(row.getBoolean("isValid"));

		FullOrgUnitInfo fullUnitInfo = createFullOrgUnitInfo(row);
		fullUnitInfo.setLongNumber(row.getString("longNumber"));
		fullUnitInfo.put("treeType", new Integer(row.getInt("tree.type")));
		String treeNumber = row.getString("tree.number");
		if (treeNumber != null) {
			fullUnitInfo.put("treeNumber", treeNumber);
		}

		structInfo.setUnit(fullUnitInfo);

		OrgStructureInfo parentStructInfo = null;
		if (row.getString("parent.id") != null
				&& !row.getString("parent.id").equals("")) {
			parentStructInfo = new OrgStructureInfo();
			parentStructInfo.setId(BOSUuid.read(row.getString("parent.id")));
		}
		structInfo.setParent(parentStructInfo);

		// orgUnitInfo.put("struParentId",row.getString("parent.id"));

		switch (row.getInt("tree.type")) {
		case OrgViewType.ADMIN_VALUE:
			break;
		case OrgViewType.COMPANY_VALUE:
			break;
		case OrgViewType.SALE_VALUE:
			break;
		case OrgViewType.PURCHASE_VALUE:
			break;
		case OrgViewType.STORAGE_VALUE:
			break;
		case OrgViewType.COSTCENTER_VALUE:
			break;
		case OrgViewType.PROFITCENTER_VALUE:
			break;
		case OrgViewType.UNIONDEBT_VALUE:
			break;
		default:
		}

		OrgTreeInfo treeInfo = new OrgTreeInfo();
		treeInfo.setId(BOSUuid.read(row.getString("tree.id")));
		structInfo.setTree(treeInfo);

		return structInfo;
	}

	private static FullOrgUnitInfo createFullOrgUnitInfo(IRowSet row)
			throws SQLException {
		FullOrgUnitInfo fullUnitInfo = new FullOrgUnitInfo();

		fullUnitInfo.setId(BOSUuid.read(row.getString("unit.id")));
		fullUnitInfo.setNumber(row.getString("unit.number"));
		fullUnitInfo.setName(row.getString("unit.name"));
		fullUnitInfo.setIsGrouping(row.getBoolean("unit.isGrouping"));
		fullUnitInfo.setIsFreeze(row.getBoolean("unit.isFreeze"));
		fullUnitInfo.setInvalidDate(row.getDate("unit.invalidDate"));
		fullUnitInfo.setEffectDate(row.getDate("unit.effectDate"));

		fullUnitInfo.setLongNumber(row.getString("longNumber"));

		fullUnitInfo.setIsAdminOrgUnit(row.getBoolean("unit.isAdminOrgUnit"));
		fullUnitInfo.setIsCompanyOrgUnit(row
				.getBoolean("unit.isCompanyOrgUnit"));
		fullUnitInfo.setIsSaleOrgUnit(row.getBoolean("unit.isSaleOrgUnit"));
		fullUnitInfo.setIsPurchaseOrgUnit(row
				.getBoolean("unit.isPurchaseOrgUnit"));
		fullUnitInfo.setIsStorageOrgUnit(row
				.getBoolean("unit.isStorageOrgUnit"));
		fullUnitInfo.setIsProfitOrgUnit(row.getBoolean("unit.isProfitOrgUnit"));
		fullUnitInfo.setIsCostOrgUnit(row.getBoolean("unit.isCostOrgUnit"));
		fullUnitInfo.setIsUnion(row.getBoolean("unit.isUnion"));
		fullUnitInfo.setIsCU(row.getBoolean("unit.isCU"));
		fullUnitInfo.setIsHROrgUnit(row.getBoolean("unit.isHROrgUnit"));
		// ������֯
		fullUnitInfo.setIsTransportOrgUnit(row
				.getBoolean("unit.isTransportOrgUnit"));
		// �ʼ���֯
		fullUnitInfo.setIsQualityOrgUnit(row
				.getBoolean("unit.isQualityOrgUnit"));

		// �������⣿
		OUPartFIInfo fiInfo = new OUPartFIInfo();

		fullUnitInfo.setPartFI(fiInfo);

		// �Ƿ񸱱���֯
		fullUnitInfo.setIsAssistantOrg(row.getBoolean("unit.isAssistantOrg"));

		return fullUnitInfo;
	}

	private static IRowSet buildViewRowSetForAll(String orgViewID,
			String rootUnitID, boolean isContainSealUp, boolean isBizUnit)
			throws Exception {

		boolean isUnion = isUnionView(orgViewID);

		String rootLongNumber = "";
		IOrgStructure iStruct = OrgStructureFactory.getRemoteInstance();
		String oql = "select longNumber where unit.id = '" + rootUnitID
				+ "' and tree.id = '" + orgViewID + "' order by longNumber asc";

		if (iStruct.exists(oql)) {
			rootLongNumber = iStruct.getOrgStructureCollection(oql).get(0)
					.getLongNumber();
		} else {
			rootLongNumber = "###@$@###";
		}

		EntityViewInfo viewInfo = new EntityViewInfo();
		FilterInfo filter1 = new FilterInfo();
		filter1.getFilterItems().add(
				new FilterItemInfo("tree.id", orgViewID, CompareType.EQUALS));
		filter1.getFilterItems().add(
				new FilterItemInfo("isValid", new Integer(1),
						CompareType.EQUALS));

		if (rootUnitID.equals(OrgConstants.DEF_CU_ID)) {
			// �Ǻϲ���Ԫ����û���趨rootUnitID������²���Ҫ����
			filter1.setMaskString("#0 and #1");
		} else {
			filter1.getFilterItems().add(
					new FilterItemInfo("longNumber", rootLongNumber,
							CompareType.EQUALS));
			filter1.getFilterItems().add(
					new FilterItemInfo("longNumber", rootLongNumber + "!%",
							CompareType.LIKE));
			filter1.setMaskString("#0 and #1 and (#2 or #3)");
		}

		if (isContainSealUp == false) {
			if (!orgViewID.equals(OrgConstants.CU_VIEW_ID)
					&& !orgViewID.equals(OrgConstants.RESPON_VIEW_ID)
					&& !isUnion) {
				FilterInfo filter2 = new FilterInfo();
				filter2.getFilterItems().add(
						new FilterItemInfo(getSealUpColumnName(orgViewID),
								new Integer(0), CompareType.EQUALS));
				filter1.mergeFilter(filter2, "and");
			} else if (orgViewID.equals(OrgConstants.RESPON_VIEW_ID)) {
				FilterInfo filter2 = new FilterInfo();
				filter2.getFilterItems().add(
						new FilterItemInfo("unit.partFI.isSealUp", new Integer(
								0), CompareType.EQUALS));
				filter2.getFilterItems().add(
						new FilterItemInfo("unit.partCostCenter.isSealUp",
								new Integer(0), CompareType.EQUALS));
				filter2.getFilterItems().add(
						new FilterItemInfo("unit.partProfitCenter.isSealUp",
								new Integer(0), CompareType.EQUALS));
				filter2.setMaskString("#0 or #1 or #2");
				filter1.mergeFilter(filter2, "and");
			} else if (orgViewID.equals(OrgConstants.CU_VIEW_ID)) {
				FilterInfo filter2 = new FilterInfo();
				filter2.getFilterItems().add(
						new FilterItemInfo("unit.isOUSealUp", new Integer(0),
								CompareType.EQUALS));
				filter1.mergeFilter(filter2, "and");
			}
		}

		if (isBizUnit) {
			FilterInfo filter2 = new FilterInfo();
			filter2.getFilterItems().add(
					new FilterItemInfo("unit.partSale.isBizUnit", Boolean.TRUE,
							CompareType.EQUALS));
			filter1.mergeFilter(filter2, "and");
		}

		viewInfo.setFilter(filter1);
		IMetaDataPK queryPK = new MetaDataPK(
				"com.kingdee.eas.basedata.org.app.NewBuildTreeQuery");
		IQueryExecutor queryExcutor = QueryExecutorFactory
				.getRemoteInstance(queryPK);
		queryExcutor.setObjectView(viewInfo);

		return queryExcutor.executeQuery();
	}

	private static String getSealUpColumnName(String treeID) {
		String columnName = "";
		if (treeID.equals(OrgConstants.ADMIN_VIEW_ID)) {
			columnName = "unit.partAdmin.isSealUp";
		} else if (treeID.equals(OrgConstants.FI_VIEW_ID)) {
			columnName = "unit.partFI.isSealUp";
		} else if (treeID.equals(OrgConstants.PURCHASE_VIEW_ID)) {
			columnName = "unit.partPurchase.isSealUp";
		} else if (treeID.equals(OrgConstants.SALE_VIEW_ID)) {
			columnName = "unit.partSale.isSealUp";
		} else if (treeID.equals(OrgConstants.STORAGE_VIEW_ID)) {
			columnName = "unit.partStorage.isSealUp";
		} else if (treeID.equals(OrgConstants.COST_VIEW_ID)) {
			columnName = "unit.partCostCenter.isSealUp";
		} else if (treeID.equals(OrgConstants.PROFIT_VIEW_ID)) {
			columnName = "unit.partProfitCenter.isSealUp";
		} else if (treeID.equals(OrgConstants.HRO_VIEW_ID)) {
			columnName = "unit.partHR.isSealUp";
		} else if (treeID.equals(OrgConstants.TRANSPORT_VIEW_ID)) {
			columnName = "unit.partTransport.isSealUp";
		} else if (treeID.equals(OrgConstants.QUALITY_VIEW_ID)) {
			columnName = "unit.partQuality.isSealUp";
		}

		return columnName;
	}

	private static boolean isUnionView(String orgViewID) {
		return !orgViewID.equals(OrgConstants.ADMIN_VIEW_ID)
				&& !orgViewID.equals(OrgConstants.FI_VIEW_ID)
				&& !orgViewID.equals(OrgConstants.PURCHASE_VIEW_ID)
				&& !orgViewID.equals(OrgConstants.SALE_VIEW_ID)
				&& !orgViewID.equals(OrgConstants.STORAGE_VIEW_ID)
				&& !orgViewID.equals(OrgConstants.COST_VIEW_ID)
				&& !orgViewID.equals(OrgConstants.PROFIT_VIEW_ID)
				&& !orgViewID.equals(OrgConstants.HRO_VIEW_ID)
				&& !orgViewID.equals(OrgConstants.CU_VIEW_ID)
				&& !orgViewID.equals(OrgConstants.RESPON_VIEW_ID)
				&& !orgViewID.equals(OrgConstants.TRANSPORT_VIEW_ID);
	}

	/**
	 * ����������֯�� --
	 * 
	 * @param actionOnLoad
	 * @return TreeModel
	 * @throws Exception
	 */
	public static TreeModel getSaleOrgTree(ItemAction actionOnLoad)
			throws Exception {
		SaleOrgUnitInfo saleOrg = getCurrentSaleOrg();
		return getSaleOrgTree(actionOnLoad, saleOrg);
	}

	/**
	 * ����������Ŀ�� -- ����Ŀ����Ӫ��Ȩ���޹أ�����һЩ��������Ϣ���ã�������¥������ ���ͽṹ ������֯ - ������Ŀ
	 * 
	 * @param sysTypeParam
	 *            ӵ����¥���Եġ�ӵ���������Եġ�ӵ����ҵ���Եģ� ��Ϊ�� ����ȫ������
	 */
	public static TreeModel getSellProjectBaseTree(ItemAction actionOnLoad,
			MoneySysTypeEnum sysTypeParam) throws Exception {
		return buildTreeByMuSellProNodes(actionOnLoad, sysTypeParam,
				"SellProject", false);
	}

	/**
	 * ����������Ŀ�� -- ����Ŀ����Ӫ��Ȩ���й� ���ͽṹ ������֯ - ������Ŀ
	 * 
	 * @param sysTypeParam
	 *            ӵ����¥���Եġ�ӵ���������Եġ�ӵ����ҵ���Եģ� ��Ϊ�� ����ȫ������
	 */
	public static TreeModel getSellProjectTree(ItemAction actionOnLoad,
			MoneySysTypeEnum sysTypeParam) throws Exception {
		return buildTreeByMuSellProNodes(actionOnLoad, sysTypeParam,
				"SellProject", true);
	}

	public static TreeModel getSellProjectTreeIsNotSellhouse(
			ItemAction actionOnLoad, MoneySysTypeEnum sysTypeParam,boolean isShowUnit,boolean isControler)
			throws Exception {
		return buildTreeSellProNodes(actionOnLoad, sysTypeParam, "SellProject",
				isShowUnit,isControler);
	}
	
	public static TreeModel getSellProjectTreeIsNotSellhouseForChooseRoom(
			ItemAction actionOnLoad, MoneySysTypeEnum sysTypeParam,boolean isShowUnit,boolean isControler)
			throws Exception {
		return buildTreeSellProNodesForChooseRoom(actionOnLoad, sysTypeParam, "SellProject",
				isShowUnit,isControler);
	}

	public static TreeModel getSellProjectForSHESellProject(
			ItemAction actionOnLoad, MoneySysTypeEnum sysTypeParam)
			throws Exception {

		// ����¥��֯ ����Ŀ�Ĵ�����֯��
		OrgUnitInfo saleOrgInfo = SysContext.getSysContext().getCurrentOrgUnit();
		TreeModel tree = getSaleOrgTree(actionOnLoad, saleOrgInfo); // ����֯���������Ŀ�ڵ�
		DefaultMutableTreeNode rootNode = (DefaultMutableTreeNode) tree
				.getRoot();

		Map orgIdMap = getAllObjectIdMap(rootNode, "OrgStructure");
		Map spIdToNode = new HashMap(); // ��Ŀid��Ӧ���Ľڵ�
		String orgIdsSql = "select fid from t_org_sale where fid = '"+saleOrgInfo.getId()+"'" +
		" or flongNumber like '"+saleOrgInfo.getLongNumber()+"!%' "; 
		/**
		* ��ǰ��֯�����¼���Ŀ
		*/
		
		SellProjectCollection sellProjectColl = SellProjectFactory
		.getRemoteInstance().getSellProjectCollection(
		"select * where isForShe = 1 and orgUnit.id in ("+orgIdsSql+") order by level,number");
		/**
		* �����֯�������ǰ��֯����Ŀ
		*/
		ShareOrgUnitCollection shareSpColl = ShareOrgUnitFactory.getRemoteInstance()
		.getShareOrgUnitCollection("select head.*,orgUnit.id where orgUnit.id in ("+orgIdsSql+")");
		
		for (int i = 0; i < shareSpColl.size(); i++) {
			SellProjectInfo spInfo = shareSpColl.get(i).getHead();
			spInfo.setOrgUnit(shareSpColl.get(i).getOrgUnit());
			sellProjectColl.add(spInfo);
		}

		for (int i = 0; i < sellProjectColl.size(); i++) {
			SellProjectInfo thisSpInfo = sellProjectColl.get(i);
			DefaultKingdeeTreeNode sonNode = new DefaultKingdeeTreeNode(
					thisSpInfo);
			sonNode.setCustomIcon(EASResource
					.getIcon("imgTbtn_assistantaccount"));
			if (spIdToNode.get(thisSpInfo.getId().toString()) != null)
				continue;

			if (thisSpInfo.getOrgUnit()!=null && thisSpInfo.getLevel() == 1) {
				DefaultMutableTreeNode orgNode = (DefaultMutableTreeNode) orgIdMap
						.get(thisSpInfo.getOrgUnit().getId().toString());
				if (orgNode != null) {
					if (spIdToNode.get(thisSpInfo.getId().toString()) == null) {
						orgNode.add(sonNode);
						spIdToNode.put(thisSpInfo.getId().toString(), sonNode);
					}
				}
			} else {
				SellProjectInfo parentSpInfo = sellProjectColl.get(i)
						.getParent();
				if (parentSpInfo == null)
					continue;
				DefaultKingdeeTreeNode parentNode = (DefaultKingdeeTreeNode) spIdToNode
						.get(parentSpInfo.getId().toString());
				if (parentNode != null) {
					if (spIdToNode.get(thisSpInfo.getId().toString()) == null) {
						parentNode.add(sonNode);
						spIdToNode.put(thisSpInfo.getId().toString(), sonNode);
					}
				}
			}
		}
		return tree;
	}

	/**
	 * ��¥ר�� ����¥���� ���ͽṹ ������֯ - ������Ŀ - ���� - ¥��
	 */
	public static TreeModel getBuildingTreeForSHE(ItemAction actionOnLoad,
			MoneySysTypeEnum sysTypeParam) throws Exception {
		return CRMTreeHelper.getBuildingTree(actionOnLoad, false);
	}

	/**
	 * ��¥ר�� ������Ԫ�� ���ͽṹ ������֯ - ������Ŀ - ���� - ¥�� - ��Ԫ
	 */
	public static TreeModel getUnitTreeForSHE(ItemAction actionOnLoad,
			MoneySysTypeEnum sysTypeParam) throws Exception {
		return CRMTreeHelper.getBuildingTree(actionOnLoad, true);
	}

	/**
	 * 
	 * ��¥ר��ʹ�� ����������Ŀ�� -- ����Ŀ����Ӫ��Ȩ���й� ���ͽṹ ������֯ - ������Ŀ
	 * 
	 * @param sysTypeParam
	 *            ӵ����¥���Եġ�ӵ���������Եġ�ӵ����ҵ���Եģ� ��Ϊ�� ����ȫ������
	 * @author eric_wang
	 * @since 2011.06.30
	 */
	public static TreeModel getSellProjectTreeForSHE(ItemAction actionOnLoad,
			MoneySysTypeEnum sysTypeParam) throws Exception {
		return CRMTreeHelper.getSellProjectTree(actionOnLoad);
	}

	/**
	 * 
	 * ��¥ר��ʹ�� ����ƽ��ͼ�� ���ͽṹ ������֯ - (��Ŀͼ) - ������Ŀ - (¥���ֲ�ͼ) - ¥�� - (¥��ƽ��ͼ,���ֵ�Ԫ) - ��Ԫ
	 * - (¥��ƽ��ͼ,���ֵ�Ԫ)
	 * 
	 * @param sysTypeParam
	 *            ӵ����¥���Եġ�ӵ���������Եġ�ӵ����ҵ���Եģ� ��Ϊ�� ����ȫ������
	 * @author eric_wang
	 * @since 2011.06.30
	 */
	public static TreeModel getPlanisphereForSHE(ItemAction actionOnLoad,
			MoneySysTypeEnum sysTypeParam) throws Exception {
		TreeModel tree = getUnitTreeForSHE(actionOnLoad, sysTypeParam);
		DefaultMutableTreeNode treeNode = (DefaultMutableTreeNode) tree
				.getRoot();

		Map orgIdMap = FDCTreeHelper
				.getAllObjectIdMap(treeNode, "OrgStructure");
		Map sellProIdMap = FDCTreeHelper.getAllObjectIdMap(treeNode,
				"SellProject");
		Map buildingIdMap = FDCTreeHelper.getAllObjectIdMap(treeNode,
				"Building");
		Map buildingUnitIdMap = FDCTreeHelper.getAllObjectIdMap(treeNode,
				"BuildingUnit");
		PlanisphereCollection phColl = PlanisphereFactory
				.getRemoteInstance()
				.getPlanisphereCollection(
						" where orgUnit.id in ("
								+ FDCTreeHelper.getStringFromSet(orgIdMap
										.keySet())
								+ ") "
								+ "and (sellProject.id is null or sellProject.id in ("
								+ FDCTreeHelper.getStringFromSet(sellProIdMap
										.keySet()) + ")) ");
		for (int i = 0; i < phColl.size(); i++) {
			PlanisphereInfo phInfo = phColl.get(i);
			if (phInfo.getPtype() == null)
				continue;
			if (phInfo.getPtype().equals(PlanisphereTypeEnum.PicSellProject)) {
				if (phInfo.getSellProject() == null) {
					DefaultKingdeeTreeNode orgNode = (DefaultKingdeeTreeNode) orgIdMap
							.get(phInfo.getOrgUnit().getId().toString());
					if (orgNode != null)
						addPlanisphereNode(orgNode, phInfo);
				}
			} else if (phInfo.getPtype().equals(
					PlanisphereTypeEnum.PicBuildDist)) {
				if (phInfo.getSellProject() != null
						&& phInfo.getBuilding() == null) {
					DefaultKingdeeTreeNode sellProNode = (DefaultKingdeeTreeNode) sellProIdMap
							.get(phInfo.getSellProject().getId().toString());
					if (sellProNode != null)
						addPlanisphereNode(sellProNode, phInfo);
				}
			} else if (phInfo.getPtype().equals(
					PlanisphereTypeEnum.PicBuildPlane)) {
				if (phInfo.getBuilding() != null) {
					if (phInfo.isIsShowUnit()) {
						Iterator iter = buildingUnitIdMap.keySet().iterator();
						while (iter.hasNext()) {
							DefaultKingdeeTreeNode unitNode = (DefaultKingdeeTreeNode) buildingUnitIdMap
									.get(iter.next());
							if (unitNode != null) {
								BuildingUnitInfo unitInfo = (BuildingUnitInfo) unitNode
										.getUserObject();
								if (unitInfo.getBuilding().getId().toString()
										.equals(
												phInfo.getBuilding().getId()
														.toString())
										&& unitInfo.getSeq() == phInfo
												.getUnit()) {
									addPlanisphereNode(unitNode, phInfo);
								}
							}
						}
					} else {
						DefaultKingdeeTreeNode buildNode = (DefaultKingdeeTreeNode) buildingIdMap
								.get(phInfo.getBuilding().getId().toString());
						if (buildNode != null) {
							BuildingInfo buildInfo = (BuildingInfo) buildNode
									.getUserObject();
							if (buildInfo.getUnitCount() == 0
									|| (buildInfo.getUnitCount() > 0 && !phInfo
											.isIsShowUnit())) {
								addPlanisphereNode(buildNode, phInfo);
							}
						}
					}

				}
			}
		}
		return tree;
	}

	private static TreeModel buildTreeByMuSellProNodes(ItemAction actionOnLoad,
			MoneySysTypeEnum sysTypeParam, String detailNodeType,
			boolean isMuPerm) throws Exception {
		// ----------------------------------
		// �������տ������������ھ����ݣ��������������������ܽ���ϵͳ����
		// �жϹ������£�������¥���տ�����ݣ������տû���������������ݣ�����ʾ�����Ƚ�������
		if (MoneySysTypeEnum.SalehouseSys.equals(sysTypeParam)
				&& FDCReceiveBillFactory.getRemoteInstance().exists(
						"where moneySysType='SalehouseSys'")) {
			// TODO ��������ĵ��˰�t_tmp_bot_skdgz_bat_s��ɾ����ô��.
			final String sql = "select * from KSQL_USERTABLES where KSQL_TABNAME='t_tmp_bot_skdgz_bat_s'";
			IRowSet rs = FDCSQLFacadeFactory.getRemoteInstance().executeQuery(
					sql, null);
			if (!rs.next()) {
				// ����ʾ�������ݲ��жϰ�
				MsgBox.showInfo("���Ƚ�����¥�տ������������ϸ��鿴����˵����");
				// IUIWindow uiWindow =
				// UIFactory.createUIFactory(UIFactoryName.NEWTAB
				// ).create(RevUpUI.class.getName(), );
				// uiWindow.show();
				// throw new Exception("���Ƚ�����¥�տ����������");
			}
		}
		// -----------------------------------

		TreeModel tree = getSaleOrgTree(actionOnLoad); // ����֯���������Ŀ�ڵ�
		DefaultMutableTreeNode node = (DefaultMutableTreeNode) tree.getRoot();
		Map orgIdMap = getAllObjectIdMap(node, "OrgStructure");
		ArrayList muSellProTreeNodes = MarketingUnitFactory.getRemoteInstance()
				.getMuSellProTreeNodes(detailNodeType, isMuPerm);
		for (int i = 0; i < muSellProTreeNodes.size(); i++) {
			DefaultKingdeeTreeNode spNode = (DefaultKingdeeTreeNode) muSellProTreeNodes
					.get(i);
			SellProjectInfo spInfo = (SellProjectInfo) spNode.getUserObject();
			if (sysTypeParam == null
					|| (sysTypeParam.equals(MoneySysTypeEnum.SalehouseSys) && spInfo
							.isIsForSHE())
					|| (sysTypeParam.equals(MoneySysTypeEnum.TenancySys) && spInfo
							.isIsForTen())
					|| (sysTypeParam.equals(MoneySysTypeEnum.ManageSys) && spInfo
							.isIsForPPM())) {
				DefaultMutableTreeNode orgTreeNode = (DefaultMutableTreeNode) orgIdMap
						.get(spInfo.getOrgUnit().getId().toString());
				if (orgTreeNode != null)
					orgTreeNode.add(spNode);
			}
		}

		// ˢ�½ڵ�ͼ��
		setCustomerIcon(node);
		return tree;
	}

	/**
	 * �ǹܿ���Ա����¥��֯��Ա���ܿ�����������Ŀ-¥��
	 * @param actionOnLoad
	 * @param sysTypeParam
	 * @param detailNodeType
	 * @param isMuPerm
	 * @return
	 * @throws Exception
	 */
	private static TreeModel buildTreeSellProNodes(ItemAction actionOnLoad,
			MoneySysTypeEnum sysTypeParam, String detailNodeType,
			boolean isShowUnit,boolean isControler) throws Exception {
		TreeModel retTree = null; 
		
		retTree = getSellProjectForNoControlNoSell(actionOnLoad,isControler);
		
		if(!isShowUnit){
			return retTree;
		}
		
		DefaultMutableTreeNode rootNode = (DefaultMutableTreeNode)retTree.getRoot();	
		Map spIdMap = getAllObjectIdMap(rootNode,"SellProject");
		if(spIdMap.size()==0) return retTree;
		BuildingCollection buildColl = BuildingFactory.getRemoteInstance().getBuildingCollection(
					"select *"+ (isShowUnit?",units.*":"") +" where sellProject.id in ("+ getStringFromSet(spIdMap.keySet()) +") ");
		Map buildMap = new HashMap();
		CRMHelper.sortCollection(buildColl, "number", true);
		for (int i = 0; i < buildColl.size(); i++) {
			BuildingInfo buildInfo = buildColl.get(i);
			DefaultKingdeeTreeNode sonNode = new DefaultKingdeeTreeNode(buildInfo);
			sonNode.setCustomIcon(EASResource.getIcon("imgTbtn_copytotier"));		
			DefaultKingdeeTreeNode spNode = (DefaultKingdeeTreeNode)spIdMap.get(buildInfo.getSellProject().getId().toString());
			if(spNode!=null) {
				buildInfo.setSellProject((SellProjectInfo)spNode.getUserObject());
				spNode.add(sonNode);
				buildMap.put(buildInfo.getId().toString(), sonNode);
			}
		}
		
		if(!isShowUnit) return retTree;
		
		for (int i = 0; i < buildColl.size(); i++) {
			BuildingUnitCollection unitColl = buildColl.get(i).getUnits();
			CRMHelper.sortCollection(unitColl, "seq", true);
			for (int j = 0; j < unitColl.size(); j++) {
				BuildingUnitInfo buidUnitInfo = unitColl.get(j);
				if(!buidUnitInfo.isHaveUnit()){//(false��ʾ��true����ʾ)
					buidUnitInfo.setBuilding(buildColl.get(i));
					DefaultKingdeeTreeNode sonNode = new DefaultKingdeeTreeNode(buidUnitInfo);
					sonNode.setCustomIcon(EASResource.getIcon("imgTree_folder_leaf"));	
					DefaultKingdeeTreeNode buildNode = (DefaultKingdeeTreeNode)buildMap.get(buildColl.get(i).getId().toString());
					if(buildNode!=null) {
						buildNode.add(sonNode);
					}
				}
			
			}
		}
		return retTree;
	}
	
	private static TreeModel buildTreeSellProNodesForChooseRoom(ItemAction actionOnLoad,
			MoneySysTypeEnum sysTypeParam, String detailNodeType,
			boolean isShowUnit,boolean isControler) throws Exception {
		TreeModel retTree = null; 
		retTree = getSellProjectForNoControlNoSell(actionOnLoad,isControler);
		
		if(!isShowUnit){
			return retTree;
		}
		
		DefaultMutableTreeNode rootNode = (DefaultMutableTreeNode)retTree.getRoot();	
		Map spIdMap = getAllObjectIdMap(rootNode,"SellProject");
		if(spIdMap.size()==0) return retTree;
		BuildingCollection buildColl = BuildingFactory.getRemoteInstance().getBuildingCollection(
					"select *"+ (isShowUnit?",units.*":"") +" where sellProject.id in ("+ getStringFromSet(spIdMap.keySet()) +") ");
		Map buildMap = new HashMap();
		CRMHelper.sortCollection(buildColl, "number", true);
		for (int i = 0; i < buildColl.size(); i++) {
			BuildingInfo buildInfo = buildColl.get(i);
			DefaultKingdeeTreeNode sonNode = new DefaultKingdeeTreeNode(buildInfo);
			sonNode.setCustomIcon(EASResource.getIcon("imgTbtn_copytotier"));		
			DefaultKingdeeTreeNode spNode = (DefaultKingdeeTreeNode)spIdMap.get(buildInfo.getSellProject().getId().toString());
			if(spNode!=null) {
				buildInfo.setSellProject((SellProjectInfo)spNode.getUserObject());
				spNode.add(sonNode);
				buildMap.put(buildInfo.getId().toString(), sonNode);
			}
		}
		
		return retTree;
	}


	/**
	 * �ǹܿ���Ա����¥��֯��Ա���ܿ�����������Ŀ
	 * @param actionOnLoad
	 * @return
	 * @throws Exception
	 * @throws BOSException
	 */
	private static TreeModel getSellProjectForNoControlNoSell(
			ItemAction actionOnLoad,boolean isControler) throws Exception, BOSException {
		
		
		if(!isControler){
			FullOrgUnitInfo orgUnitInfo = SysContext.getSysContext()
					.getCurrentOrgUnit().castToFullOrgUnitInfo();
			if(orgUnitInfo!=null){
				DefaultKingdeeTreeNode rootTreeNode = new DefaultKingdeeTreeNode(
						orgUnitInfo);
				return new DefaultTreeModel(rootTreeNode);
			}
		}
		
		
		SaleOrgUnitInfo saleOrgInfo = SysContext.getSysContext()
				.getCurrentSaleUnit();
		TreeModel tree = getSaleOrgTree(actionOnLoad, saleOrgInfo); // ����֯���������Ŀ�ڵ�
		DefaultMutableTreeNode rootNode = (DefaultMutableTreeNode) tree
				.getRoot();
		SHEManageHelper.deleteNonCompanyNode(rootNode,rootNode,true);

		Map orgIdMap = getAllObjectIdMap(rootNode, "OrgStructure");
		Map spIdToNode = new HashMap(); // ��Ŀid��Ӧ���Ľڵ�
		SellProjectCollection sellProjectColl = SellProjectFactory
				.getRemoteInstance()
				.getSellProjectCollection(
						"select *,companyOrgUnit.id,companyOrgUnit.name,companyOrgUnit.number where isForSHE=1 order by level,number");
		for (int i = 0; i < sellProjectColl.size(); i++) {
			SellProjectInfo thisSpInfo = sellProjectColl.get(i);
			DefaultKingdeeTreeNode sonNode = new DefaultKingdeeTreeNode(
					thisSpInfo);
			sonNode.setCustomIcon(EASResource
					.getIcon("imgTbtn_assistantaccount"));
			if (spIdToNode.get(thisSpInfo.getId().toString()) != null)
				continue;

			if (thisSpInfo.getLevel() == 1) {
				if (thisSpInfo.getCompanyOrgUnit() == null) {
					continue;
				} else {
					if (orgIdMap.get(thisSpInfo.getCompanyOrgUnit().getId()
							.toString()) == null) {
						continue;
					}
				}

				DefaultMutableTreeNode orgNode = (DefaultMutableTreeNode) orgIdMap
						.get(thisSpInfo.getCompanyOrgUnit().getId().toString());
				if (orgNode != null) {
					if (spIdToNode.get(thisSpInfo.getId().toString()) == null) {
						orgNode.add(sonNode);
						spIdToNode.put(thisSpInfo.getId().toString(), sonNode);
					}
				}
			} else {
				SellProjectInfo parentSpInfo = sellProjectColl.get(i)
						.getParent();
				if (parentSpInfo == null) {
					continue;
				}
				DefaultKingdeeTreeNode parentNode = (DefaultKingdeeTreeNode) spIdToNode
						.get(parentSpInfo.getId().toString());
				if (parentNode != null) {
					if (spIdToNode.get(thisSpInfo.getId().toString()) == null) {
						parentNode.add(sonNode);
						spIdToNode.put(thisSpInfo.getId().toString(), sonNode);
					}
				}
			}
		}
		return tree;
	}

	

	private static TreeModel buildTreeByMuSellProNodes(ItemAction actionOnLoad,
			OrgUnitInfo orgInfo, SellProjectInfo projectInfo,
			MoneySysTypeEnum sysTypeParam, String detailNodeType,
			boolean isMuPerm) throws Exception {
		TreeModel tree = getSaleOrgTree(actionOnLoad, orgInfo); // ����֯���������Ŀ�ڵ�
		DefaultMutableTreeNode node = (DefaultMutableTreeNode) tree.getRoot();
		Map orgIdMap = getAllObjectIdMap(node, "OrgStructure");

		ArrayList muSellProTreeNodes = MarketingUnitFactory.getRemoteInstance()
				.getMuSellProTreeNodes(detailNodeType, isMuPerm);
		for (int i = 0; i < muSellProTreeNodes.size(); i++) {
			DefaultKingdeeTreeNode spNode = (DefaultKingdeeTreeNode) muSellProTreeNodes
					.get(i);
			SellProjectInfo spInfo = (SellProjectInfo) spNode.getUserObject();
			if (sysTypeParam == null
					|| (sysTypeParam.equals(MoneySysTypeEnum.SalehouseSys) && spInfo
							.isIsForSHE())
					|| (sysTypeParam.equals(MoneySysTypeEnum.TenancySys) && spInfo
							.isIsForTen())
					|| (sysTypeParam.equals(MoneySysTypeEnum.ManageSys) && spInfo
							.isIsForPPM())) {
				if (projectInfo == null
						|| projectInfo.getId().toString().equals(
								spInfo.getId().toString())) {
					DefaultMutableTreeNode orgTreeNode = (DefaultMutableTreeNode) orgIdMap
							.get(spInfo.getOrgUnit().getId().toString());
					if (orgTreeNode != null)
						orgTreeNode.add(spNode);
				}
			}
		}

		// ˢ�½ڵ�ͼ��
		setCustomerIcon(node);
		return tree;
	}

	private static void setCustomerIcon(TreeNode treeNode) {
		DefaultKingdeeTreeNode thisNode = (DefaultKingdeeTreeNode) treeNode;

		if (thisNode.getUserObject() instanceof SellProjectInfo) {
			thisNode.setCustomIcon(EASResource
					.getIcon("imgTbtn_assistantaccount"));
		} else if (thisNode.getUserObject() instanceof SellOrderInfo) {
			thisNode.setCustomIcon(EASResource.getIcon("imgTbtn_sortorder"));
		} else if (thisNode.getUserObject() instanceof SubareaInfo) {
			thisNode.setCustomIcon(EASResource.getIcon("imgTbtn_showparent"));
		} else if (thisNode.getUserObject() instanceof BuildingInfo) {
			thisNode.setCustomIcon(EASResource.getIcon("imgTbtn_copytotier"));
		} else if (thisNode.getUserObject() instanceof BuildingUnitInfo) {
			thisNode.setCustomIcon(EASResource.getIcon("imgTree_folder_leaf"));
		}

		int childCount = treeNode.getChildCount();
		while (childCount > 0) {
			setCustomerIcon(treeNode.getChildAt(childCount - 1));
			childCount--;
		}

	}

	/**
	 * @deprecated
	 */
	public static TreeModel getSellProjectTree_Bak(ItemAction actionOnLoad,
			MoneySysTypeEnum sysTypeParam) throws Exception {
		TreeModel tree = getSaleOrgTree(actionOnLoad); // ����֯���������Ŀ�ڵ�

		DefaultMutableTreeNode node = (DefaultMutableTreeNode) tree.getRoot();
		Map orgIdMap = getAllObjectIdMap(node, "OrgStructure");
		String sysFilter = "";
		if (sysTypeParam != null) {
			if (sysTypeParam.equals(MoneySysTypeEnum.SalehouseSys))
				sysFilter += " and AAAA.fisForSHE=1";
			else if (sysTypeParam.equals(MoneySysTypeEnum.TenancySys))
				sysFilter += " and AAAA.fisForTen=1";
			else if (sysTypeParam.equals(MoneySysTypeEnum.ManageSys))
				sysFilter += " and AAAA.fisForPPM=1";
		}

		UserInfo currSaleMan = SysContext.getSysContext().getCurrentUserInfo();
		String sellProIdSql = MarketingUnitFactory.getRemoteInstance()
				.getPermitSellProjectIdSql(currSaleMan);
		try {
			FDCSQLBuilder builder = new FDCSQLBuilder(); // " " order by
															// sellPro.fnumber
															// asc
			builder.appendSql("select * from (");
			builder
					.appendSql("select fid,fname_l2,fnumber,forgUnitid,fisForSHE,fisForTen,fisForPPM from t_she_sellProject ");
			builder.appendSql(" union ");
			builder
					.appendSql("select sellPro.fid,sellPro.fname_l2,sellPro.fnumber,shareOrg.forgUnitid,sellPro.fisForSHE,sellPro.fisForTen,sellPro.fisForPPM from T_SHE_ShareOrgUnit shareOrg ");
			builder
					.appendSql("inner join t_she_sellProject sellPro on shareOrg.FHeadID = sellPro.fid ");
			builder.appendSql(") AAAA where AAAA.fid in (" + sellProIdSql
					+ ") " + sysFilter + " order by AAAA.fnumber asc ");

			IRowSet tableSet = builder.executeQuery();
			if (tableSet.size() == 0)
				return tree;
			while (tableSet.next()) {
				String FID = tableSet.getString("FID");
				String FName = tableSet.getString("fname_l2");
				String FNumber = tableSet.getString("fnumber");
				String ForgUnitId = tableSet.getString("forgUnitid");
				SellProjectInfo thisInfo = new SellProjectInfo();
				thisInfo.setId(BOSUuid.read(FID));
				thisInfo.setName(FName);
				thisInfo.setNumber(FNumber);
				FullOrgUnitInfo orgUnitInfo = new FullOrgUnitInfo();
				orgUnitInfo.setId(BOSUuid.read(ForgUnitId));
				thisInfo.setOrgUnit(orgUnitInfo);
				KDTreeNode newNode = getNewKDTreeNodeByObject(thisInfo, null);
				if (newNode != null) {
					DefaultMutableTreeNode orgTreeNode = (DefaultMutableTreeNode) orgIdMap
							.get(ForgUnitId);
					if (orgTreeNode != null)
						orgTreeNode.add(newNode);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return tree;
	}

	public static String getStringFromSet(Set srcSet) {
		String retStr = "";
		if (srcSet == null || srcSet.size() == 0)
			return retStr;
		Iterator iter = srcSet.iterator();
		while (iter.hasNext()) {
			Object obj = iter.next();
			if (obj instanceof String)
				retStr += ",'" + (String) obj + "'";
		}
		if (!retStr.equals(""))
			retStr = retStr.replaceFirst(",", "");
		return retStr;
	}

	/**
	 * ��ѯĳ�ڵ������е�ָ�������id�ͽڵ��ӳ��
	 * 
	 * @param treeNode
	 * @param treeType
	 *            ��֯OrgStructure��������ĿSellProject���̴�SellOrder������Subarea��¥��Building
	 *            ����ԪBuildingUnit��ƽ��ͼPlanisphere��Ӫ����ԪMarketingUnit��Ӫ������User
	 */

	public static Map getAllObjectIdMap(TreeNode treeNode, String treeType) {
		Map idMap = new HashMap();
		if (treeNode != null) {
			String treeTypeStr = "OrgStructure��SellProject��SellOrder��Subarea��Building��BuildingUnit��Planisphere��MarketingUnit��User��FDCOrgStructure,SHEProject";
			if (treeType != null && treeTypeStr.indexOf(treeType) >= 0)
				fillTreeNodeIdMap(idMap, treeNode, treeType);
		}

		return idMap;
	}

	/**
	 * ��ѯĳ�ڵ������и��ڵ㶨�����id�ͽڵ��ӳ��
	 * 
	 * @param treeNode
	 * @param treeType
	 *            ��֯OrgStructure��������ĿSellProject���̴�SellOrder������Subarea��¥��Building
	 *            ����ԪBuildingUnit��ƽ��ͼPlanisphere��Ӫ����ԪMarketingUnit��Ӫ������User
	 */
	public static Map getAllObjectIdMapForRoot(TreeNode treeNode,
			String treeType) {
		Map idMap = new HashMap();
		if (treeNode != null) {
			String treeTypeStr = "OrgStructure��SellProject��SellOrder��Subarea��Building��BuildingUnit��Planisphere��MarketingUnit��User��FDCOrgStructure,SHEProject";
			if (treeType != null && treeTypeStr.indexOf(treeType) >= 0)
				fillTreeNodeIdMapForRoot(idMap, treeNode, treeType);
		}

		return idMap;
	}

	private static void fillTreeNodeIdMap(Map idMap, TreeNode treeNode,
			String treeType) {
		DefaultKingdeeTreeNode thisNode = (DefaultKingdeeTreeNode) treeNode;

		if (treeType.equals("OrgStructure")) { // �洢������֯��Ԫid
			if (thisNode.getUserObject() instanceof OrgStructureInfo) {
				OrgStructureInfo objectInfo = (OrgStructureInfo) thisNode
						.getUserObject();
				idMap.put(objectInfo.getUnit().getId().toString(), thisNode);
			}
			// �����ӻ����֯id by add renliang by 2011-6-10
		} else if (treeType.equals("FDCOrgStructure")) {
			if (thisNode.getUserObject() instanceof OrgStructureInfo) {
				OrgStructureInfo objectInfo = (OrgStructureInfo) thisNode
						.getUserObject();
				idMap.put(objectInfo.getId().toString(), thisNode);
			}
			// �����ӻ����¥��Ŀid by add renliang by 2011-6-10
		} else if (treeType.equals("SHEProject")) {
			if (thisNode.getUserObject() instanceof SHEProjectInfo) {
				SHEProjectInfo objectInfo = (SHEProjectInfo) thisNode
						.getUserObject();
				idMap.put(objectInfo.getId().toString(), thisNode);
			}
		} else if (treeType.equals("SellProject")) {
			if (thisNode.getUserObject() instanceof SellProjectInfo) {
				SellProjectInfo objectInfo = (SellProjectInfo) thisNode
						.getUserObject();
				idMap.put(objectInfo.getId().toString(), thisNode);
			}
		} else if (treeType.equals("SellOrder")) {
			if (thisNode.getUserObject() instanceof SellOrderInfo) {
				SellOrderInfo objectInfo = (SellOrderInfo) thisNode
						.getUserObject();
				idMap.put(objectInfo.getId().toString(), thisNode);
			}
		} else if (treeType.equals("Subarea")) {
			if (thisNode.getUserObject() instanceof SubareaInfo) {
				SubareaInfo objectInfo = (SubareaInfo) thisNode.getUserObject();
				idMap.put(objectInfo.getId().toString(), thisNode);
			}
		} else if (treeType.equals("Building")) {
			// ����ѡ���Ĳ����ж� by tim_gao 2011-07-09
			// ���ݲ�������¥���ڵ�д��
			if (thisNode.getUserObject() instanceof BuildingInfo) {
				BuildingInfo objectInfo = (BuildingInfo) thisNode
						.getUserObject();
				/*if (null != set) {
					ChooseRoomSet chooseRoomSet = set
							.getChooseRoomSet(objectInfo.getId().toString());
					if (null != chooseRoomSet && chooseRoomSet.size() > 0) {
						if (null != chooseRoomSet.getValues().get(
								SHEParamConstant.T3_IS_ENABLE)) {
							// �õ�������map����Ҫ����getValues�õ������map���ܵõ�ֵ
							// ʱ�������Ч
							boolean isTimeSeg = false;
							// ��ʼʱ��Ϊ���жϣ�����ʱ��

							if (!((null == chooseRoomSet.getValues().get(
									SHEParamConstant.T3_BEGIN_DATE) && null == chooseRoomSet
									.getValues().get(
											SHEParamConstant.T3_END_DATE)) && (com.kingdee.util.Null.NULL
									.equals(chooseRoomSet.getValues().get(
											SHEParamConstant.T3_END_DATE)) || com.kingdee.util.Null.NULL
									.equals(chooseRoomSet.getValues().get(
											SHEParamConstant.T3_BEGIN_DATE))))) {
								// ��Ч��3�����
								if (chooseRoomSet.getValues().get(
										SHEParamConstant.T3_BEGIN_DATE) != null
										&& !chooseRoomSet
												.getValues()
												.get(
														SHEParamConstant.T3_BEGIN_DATE)
												.equals(
														com.kingdee.util.Null.NULL)
										&& SHEHelper
												.getCurrentTime()
												.after(
														(Date) chooseRoomSet
																.getValues()
																.get(
																		SHEParamConstant.T3_BEGIN_DATE))
										&& chooseRoomSet.getValues().get(
												SHEParamConstant.T3_END_DATE) != null
										&& !chooseRoomSet
												.getValues()
												.get(
														SHEParamConstant.T3_END_DATE)
												.equals(
														com.kingdee.util.Null.NULL)
										&& SHEHelper
												.getCurrentTime()
												.before(
														(Date) chooseRoomSet
																.getValues()
																.get(
																		SHEParamConstant.T3_END_DATE))) {
									isTimeSeg = true;
								}
								if (!(com.kingdee.util.Null.NULL
										.equals(chooseRoomSet.getValues().get(
												SHEParamConstant.T3_BEGIN_DATE)) || null == chooseRoomSet
										.getValues().get(
												SHEParamConstant.T3_BEGIN_DATE))) {
									if (SHEHelper
											.getCurrentTime()
											.after(
													(Date) chooseRoomSet
															.getValues()
															.get(
																	SHEParamConstant.T3_BEGIN_DATE))
											&& (com.kingdee.util.Null.NULL
													.equals(chooseRoomSet
															.getValues()
															.get(
																	SHEParamConstant.T3_END_DATE)) || null == chooseRoomSet
													.getValues()
													.get(
															SHEParamConstant.T3_END_DATE))) {
										isTimeSeg = true;
									}
								}

								if (!(com.kingdee.util.Null.NULL
										.equals(chooseRoomSet.getValues().get(
												SHEParamConstant.T3_END_DATE)) || null == chooseRoomSet
										.getValues().get(
												SHEParamConstant.T3_END_DATE))) {
									if (SHEHelper
											.getCurrentTime()
											.before(
													(Date) chooseRoomSet
															.getValues()
															.get(
																	SHEParamConstant.T3_END_DATE))
											&& (com.kingdee.util.Null.NULL
													.equals(chooseRoomSet
															.getValues()
															.get(
																	SHEParamConstant.T3_BEGIN_DATE)) || null == chooseRoomSet
													.getValues()
													.get(
															SHEParamConstant.T3_BEGIN_DATE))) {
										isTimeSeg = true;
									}
								}

							}

							//
							if (isTimeSeg) {
								if (false == ((Boolean) chooseRoomSet
										.getValues().get(
												SHEParamConstant.T3_IS_ENABLE))
										.booleanValue()) {
									((DefaultKingdeeTreeNode) thisNode
											.getParent()).remove(thisNode);
								} else {
									idMap.put(objectInfo.getId().toString(),
											thisNode);
								}
							} else {
								((DefaultKingdeeTreeNode) thisNode.getParent())
										.remove(thisNode);
							}
						} else if (null == chooseRoomSet.getValues().get(
								SHEParamConstant.T3_IS_ENABLE)) {
							((DefaultKingdeeTreeNode) thisNode.getParent())
									.remove(thisNode);
						}

					} else {
						((DefaultKingdeeTreeNode) thisNode.getParent())
								.remove(thisNode); // ����ѡ�������˵��ϵ�Ҳȥ����ʾ by tim_gao
													// 2011-07-11
					}
				}*/
				idMap.put(objectInfo.getId().toString(), thisNode);

			}
		} else if (treeType.equals("BuildingUnit")) {
			if (thisNode.getUserObject() instanceof BuildingUnitInfo) {
				BuildingUnitInfo objectInfo = (BuildingUnitInfo) thisNode
						.getUserObject();
				idMap.put(objectInfo.getId().toString(), thisNode);
			}
		} else if (treeType.equals("Planisphere")) {
			if (thisNode.getUserObject() instanceof PlanisphereInfo) {
				PlanisphereInfo objectInfo = (PlanisphereInfo) thisNode
						.getUserObject();
				idMap.put(objectInfo.getId().toString(), thisNode);
			}
		} else if (treeType.equals("MarketingUnit")) {
			if (thisNode.getUserObject() instanceof MarketingUnitInfo) {
				MarketingUnitInfo objectInfo = (MarketingUnitInfo) thisNode
						.getUserObject();
				idMap.put(objectInfo.getId().toString(), thisNode);
			}
		} else if (treeType.equals("User")) {
			if (thisNode.getUserObject() instanceof UserInfo) {
				UserInfo objectInfo = (UserInfo) thisNode.getUserObject();
				idMap.put(objectInfo.getId().toString(), thisNode);
			}
		}

		int childCount = treeNode.getChildCount();

		while (childCount > 0) {
			fillTreeNodeIdMap(idMap, treeNode.getChildAt(childCount - 1),
					treeType);
			childCount--;
		}

	}

	private static void fillTreeNodeIdMapForRoot(Map idMap, TreeNode treeNode,
			String treeType) {
		DefaultKingdeeTreeNode thisNode = (DefaultKingdeeTreeNode) treeNode;

		if (treeType.equals("OrgStructure")) { // �洢������֯��Ԫid
			if (thisNode.getUserObject() instanceof OrgStructureInfo) {
				OrgStructureInfo objectInfo = (OrgStructureInfo) thisNode
						.getUserObject();
				idMap.put(objectInfo.getUnit().getId().toString(), thisNode);
			}
			// �����ӻ����֯id by add renliang by 2011-6-10
		} else if (treeType.equals("FDCOrgStructure")) {
			if (thisNode.getUserObject() instanceof OrgStructureInfo) {
				OrgStructureInfo objectInfo = (OrgStructureInfo) thisNode
						.getUserObject();
				idMap.put(objectInfo.getId().toString(), thisNode);
			}
			// �����ӻ����¥��Ŀid by add renliang by 2011-6-10
		} else if (treeType.equals("SHEProject")) {
			if (thisNode.getUserObject() instanceof SHEProjectInfo) {
				SHEProjectInfo objectInfo = (SHEProjectInfo) thisNode
						.getUserObject();
				idMap.put(objectInfo.getId().toString(), thisNode);
			}
		} else if (treeType.equals("SellProject")) {
			if (thisNode.getUserObject() instanceof SellProjectInfo) {
				SellProjectInfo objectInfo = (SellProjectInfo) thisNode
						.getUserObject();
				idMap.put(objectInfo.getId().toString(), thisNode);
			}
		} else if (treeType.equals("SellOrder")) {
			if (thisNode.getUserObject() instanceof SellOrderInfo) {
				SellOrderInfo objectInfo = (SellOrderInfo) thisNode
						.getUserObject();
				idMap.put(objectInfo.getId().toString(), thisNode);
			}
		} else if (treeType.equals("Subarea")) {
			if (thisNode.getUserObject() instanceof SubareaInfo) {
				SubareaInfo objectInfo = (SubareaInfo) thisNode.getUserObject();
				idMap.put(objectInfo.getId().toString(), thisNode);
			}
		} else if (treeType.equals("Building")) {
			if (thisNode.getUserObject() instanceof BuildingInfo) {
				BuildingInfo objectInfo = (BuildingInfo) thisNode
						.getUserObject();
				idMap.put(objectInfo.getId().toString(), thisNode);
			}
		} else if (treeType.equals("BuildingUnit")) {
			if (thisNode.getUserObject() instanceof BuildingUnitInfo) {
				BuildingUnitInfo objectInfo = (BuildingUnitInfo) thisNode
						.getUserObject();
				idMap.put(objectInfo.getId().toString(), thisNode);
			}
		} else if (treeType.equals("Planisphere")) {
			if (thisNode.getUserObject() instanceof PlanisphereInfo) {
				PlanisphereInfo objectInfo = (PlanisphereInfo) thisNode
						.getUserObject();
				idMap.put(objectInfo.getId().toString(), thisNode);
			}
		} else if (treeType.equals("MarketingUnit")) {
			if (thisNode.getUserObject() instanceof MarketingUnitInfo) {
				MarketingUnitInfo objectInfo = (MarketingUnitInfo) thisNode
						.getUserObject();
				idMap.put(objectInfo.getId().toString(), thisNode);
			}
		} else if (treeType.equals("User")) {
			if (thisNode.getUserObject() instanceof UserInfo) {
				UserInfo objectInfo = (UserInfo) thisNode.getUserObject();
				idMap.put(objectInfo.getId().toString(), thisNode);
			}
		}
		TreeNode root = treeNode.getParent();
		if (root != null) {
			DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) root;
			if (node.getUserObject() instanceof SellProjectInfo) {
				fillTreeNodeIdMapForRoot(idMap, root, treeType);
			}
		}
	}

	/**
	 * ����һ���µ�TreeNode ������Ŀ���̴Ρ�������¥������Ԫ��ƽ��ͼ��Ӫ������ iconType ��Ϊ�� admin person
	 */
	public static KDTreeNode getNewKDTreeNodeByObject(
			ObjectBaseInfo nodeObject, String iconType) {
		KDTreeNode treeNode = null;
		if (nodeObject != null
				&& (nodeObject instanceof DataBaseInfo
						|| nodeObject instanceof FDCBillInfo || nodeObject instanceof UserInfo)) {
			if (nodeObject instanceof DataBaseInfo) {
				treeNode = new KDTreeNode(((DataBaseInfo) nodeObject).getName());
			} else if (nodeObject instanceof FDCBillInfo) {
				treeNode = new KDTreeNode(((FDCBillInfo) nodeObject).getName());
			} else if (nodeObject instanceof UserInfo) {
				treeNode = new KDTreeNode(((UserInfo) nodeObject).getName());
			}
			treeNode.setUserObject(nodeObject);
		} else {
			return null;
		}

		if (nodeObject instanceof SellProjectInfo) { // ������Ŀ
			treeNode.setCustomIcon(EASResource
					.getIcon("imgTbtn_assistantaccount"));
		} else if (nodeObject instanceof SellOrderInfo) { // �̴�
			treeNode.setCustomIcon(EASResource.getIcon("imgTbtn_sortorder"));
		} else if (nodeObject instanceof SubareaInfo) { // ����
			treeNode.setCustomIcon(EASResource.getIcon("imgTbtn_showparent"));
		} else if (nodeObject instanceof BuildingInfo) { // ¥��
			treeNode.setCustomIcon(EASResource.getIcon("imgTbtn_copytotier"));
		} else if (nodeObject instanceof BuildingUnitInfo) { // ��Ԫ
			treeNode.setCustomIcon(EASResource.getIcon("imgTree_folder_leaf"));
		} else if (nodeObject instanceof PlanisphereInfo) { // ƽ��ͼ
			treeNode.setCustomIcon(EASResource.getIcon("imgTbtn_showpicture"));
		} else if (nodeObject instanceof UserInfo) { // Ӫ������ �� �����˺ͳ�Ա
			if (iconType != null) {
				if (iconType.equals("admin"))
					treeNode.setCustomIcon(EASResource
							.getIcon("imgTbtn_conadministrator"));
				else if (iconType.equals("person"))
					treeNode.setCustomIcon(EASResource
							.getIcon("imgTbtn_personnonepos"));
			}
		}

		return treeNode;
	}

	/**
	 * �������̵��̴��� ���ͽṹ ������֯ - ������Ŀ - �̴�
	 */
	public static TreeModel getSellOrderTree(ItemAction actionOnLoad,
			MoneySysTypeEnum sysTypeParam) throws Exception {
		return buildTreeByMuSellProNodes(actionOnLoad, sysTypeParam,
				"SellOrder", true);
	}

	/** @deprecated */
	public static TreeModel getSellOrderTree_Bak(ItemAction actionOnLoad,
			MoneySysTypeEnum sysTypeParam) throws Exception {
		TreeModel tree = getSellProjectTree(actionOnLoad, sysTypeParam);

		DefaultMutableTreeNode node = (DefaultMutableTreeNode) tree.getRoot();
		Map sellProIdMap = getAllObjectIdMap(node, "SellProject"); // ������Ŀ��idӳ��
		if (sellProIdMap == null || sellProIdMap.size() == 0)
			return tree;

		SellOrderCollection sellOrders = SellOrderFactory.getRemoteInstance()
				.getSellOrderCollection(
						"select id,name,number,project.id where project.id in ("
								+ getStringFromSet(sellProIdMap.keySet())
								+ ") and isEnabled =1 order by number");
		if (sellOrders.size() == 0)
			return tree;

		for (int i = 0; i < sellOrders.size(); i++) {
			SellOrderInfo orderInfo = sellOrders.get(i);
			KDTreeNode newNode = getNewKDTreeNodeByObject(orderInfo, null);
			if (newNode != null) {
				DefaultMutableTreeNode sellProNode = (DefaultMutableTreeNode) sellProIdMap
						.get(orderInfo.getProject().getId().toString());
				if (sellProNode != null)
					sellProNode.add(newNode);
			}
		}

		return tree;
	}

	/**
	 * ���������� ���ͽṹ ������֯ - ������Ŀ - ����
	 */
	public static TreeModel getSubareaTree(ItemAction actionOnLoad,
			MoneySysTypeEnum sysTypeParam) throws Exception {
		return buildTreeByMuSellProNodes(actionOnLoad, sysTypeParam, "Subarea",
				true);
	}

	/** @deprecated */
	public static TreeModel getSubareaTree_Bak(ItemAction actionOnLoad,
			MoneySysTypeEnum sysTypeParam) throws Exception {
		TreeModel tree = getSellProjectTree(actionOnLoad, sysTypeParam);

		DefaultMutableTreeNode node = (DefaultMutableTreeNode) tree.getRoot();
		Map sellProIdMap = getAllObjectIdMap(node, "SellProject"); // ������Ŀ��idӳ��
		if (sellProIdMap == null || sellProIdMap.size() == 0)
			return tree;

		SubareaCollection subAreaColl = SubareaFactory.getRemoteInstance()
				.getSubareaCollection(
						"select id,name,number,sellProject.id where sellProject.id in ("
								+ getStringFromSet(sellProIdMap.keySet())
								+ ") order by number");
		if (subAreaColl.size() == 0)
			return tree;

		for (int i = 0; i < subAreaColl.size(); i++) {
			SubareaInfo thisInfo = subAreaColl.get(i);
			KDTreeNode newNode = getNewKDTreeNodeByObject(thisInfo, null);
			if (newNode != null) {
				DefaultMutableTreeNode sellProNode = (DefaultMutableTreeNode) sellProIdMap
						.get(thisInfo.getSellProject().getId().toString());
				if (sellProNode != null)
					sellProNode.add(newNode);
			}
		}

		return tree;
	}

	/**
	 * ����¥���� ���ͽṹ ������֯ - ������Ŀ - ���� - ¥��
	 */
	public static TreeModel getBuildingTree(ItemAction actionOnLoad,
			MoneySysTypeEnum sysTypeParam) throws Exception {
		return buildTreeByMuSellProNodes(actionOnLoad, sysTypeParam,
				"Building", true);
	}

	/** @deprecated */
	public static TreeModel getBuildingTree_Bak(ItemAction actionOnLoad,
			MoneySysTypeEnum sysTypeParam) throws Exception {
		TreeModel tree = getSubareaTree(actionOnLoad, sysTypeParam);

		DefaultMutableTreeNode node = (DefaultMutableTreeNode) tree.getRoot();
		Map sellProIdMap = getAllObjectIdMap(node, "SellProject"); // ������Ŀ��idӳ��
		if (sellProIdMap == null || sellProIdMap.size() == 0)
			return tree;
		Map subAreaIdMap = getAllObjectIdMap(node, "Subarea"); // ������idӳ��

		FDCSQLBuilder builder = new FDCSQLBuilder();
		builder
				.appendSql("select fid,fname_l2,fnumber,fsellProjectId,fsubareaId from t_she_Building where fsellProjectId in ("
						+ getStringFromSet(sellProIdMap.keySet())
						+ ") order by fnumber");
		IRowSet tableSet = builder.executeQuery();
		if (tableSet.size() == 0)
			return tree;
		while (tableSet.next()) {
			String FID = tableSet.getString("FID");
			String FName = tableSet.getString("fname_l2");
			String FNumber = tableSet.getString("fnumber");
			String FSellProjectId = tableSet.getString("fsellProjectId");
			BuildingInfo thisInfo = new BuildingInfo();
			thisInfo.setId(BOSUuid.read(FID));
			thisInfo.setName(FName);
			thisInfo.setNumber(FNumber);
			SellProjectInfo sellProInfo = new SellProjectInfo();
			sellProInfo.setId(BOSUuid.read(FSellProjectId));
			thisInfo.setSellProject(sellProInfo);
			String FSubareaId = tableSet.getString("fsubareaId");
			if (FSubareaId != null) {
				SubareaInfo subAreaInfo = new SubareaInfo();
				subAreaInfo.setId(BOSUuid.read(FSubareaId));
				thisInfo.setSubarea(subAreaInfo);
			}
			KDTreeNode newNode = getNewKDTreeNodeByObject(thisInfo, null);
			if (newNode != null) {
				if (thisInfo.getSubarea() == null) {
					DefaultMutableTreeNode sellProNode = (DefaultMutableTreeNode) sellProIdMap
							.get(thisInfo.getSellProject().getId().toString());
					if (sellProNode != null)
						sellProNode.add(newNode);
				} else {
					DefaultMutableTreeNode subAreaNode = (DefaultMutableTreeNode) subAreaIdMap
							.get(thisInfo.getSubarea().getId().toString());
					if (subAreaNode != null)
						subAreaNode.add(newNode);
				}
			}
		}

		return tree;
	}

	/**
	 * ��ָ����֯������֯�µ�§���� ��Ӫ��Ȩ���޹ص�§����
	 */
	public static TreeModel getBuildingTree(SaleOrgUnitInfo saleOrgInfo,
			MoneySysTypeEnum sysTypeParam) throws Exception {
		DefaultKingdeeTreeNode root = new DefaultKingdeeTreeNode(saleOrgInfo);
		TreeModel tree = new DefaultTreeModel(root);
		DefaultMutableTreeNode node = (DefaultMutableTreeNode) tree.getRoot();

		ArrayList muSellProTreeNodes = MarketingUnitFactory.getRemoteInstance()
				.getMuSellProTreeNodes("Building", false);
		for (int i = 0; i < muSellProTreeNodes.size(); i++) {
			DefaultKingdeeTreeNode spNode = (DefaultKingdeeTreeNode) muSellProTreeNodes
					.get(i);
			SellProjectInfo spInfo = (SellProjectInfo) spNode.getUserObject();
			if (sysTypeParam == null
					|| (sysTypeParam.equals(MoneySysTypeEnum.SalehouseSys) && spInfo
							.isIsForSHE())
					|| (sysTypeParam.equals(MoneySysTypeEnum.TenancySys) && spInfo
							.isIsForTen())
					|| (sysTypeParam.equals(MoneySysTypeEnum.ManageSys) && spInfo
							.isIsForPPM())) {
				node.add(spNode);
			}
		}

		// ˢ�½ڵ�ͼ��
		setCustomerIcon(node);
		return tree;
	}

	/**
	 * ������Ԫ�� ���ͽṹ ������֯ - ������Ŀ - ���� - ¥�� - ��Ԫ
	 */
	public static TreeModel getUnitTree(ItemAction actionOnLoad,
			MoneySysTypeEnum sysTypeParam) throws Exception {
		return buildTreeByMuSellProNodes(actionOnLoad, sysTypeParam,
				"BuildingUnit", true);
	}

	/**
	 * ����ѡ��֯Ϊ����������֯ʱֻ��ʾ��ǰ��֯�µ�������Ŀ������ʾ���������������Ŀ
	 * ����ѡ��֯Ϊʵ��������֯ʱ��ʾ��ǰ��֯�µ�������Ŀ�͹��������������Ŀ
	 * 
	 * @param actionOnLoad
	 * @param sysTypeParam
	 * @param showShare
	 * @return
	 * @throws Exception
	 */
	public static TreeModel getUnitTree(ItemAction actionOnLoad,
			MoneySysTypeEnum sysTypeParam, boolean showShare) throws Exception {
		return buildTreeByMuSellProNodes(actionOnLoad, sysTypeParam,
				"BuildingUnit", true, showShare);
	}

	public static TreeModel getBuildingTree(ItemAction actionOnLoad,
			MoneySysTypeEnum sysTypeParam, boolean showShare) throws Exception {
		return buildTreeByMuSellProNodes(actionOnLoad, sysTypeParam,
				"Building", true, showShare);
	}

	/**
	 * ����ѡ��֯Ϊ����������֯ʱֻ��ʾ��ǰ��֯�µ�������Ŀ������ʾ���������������Ŀ
	 * ����ѡ��֯Ϊʵ��������֯ʱ��ʾ��ǰ��֯�µ�������Ŀ�͹��������������Ŀ
	 * 
	 * @param actionOnLoad
	 * @param sysTypeParam
	 * @param detailNodeType
	 * @param isMuPerm
	 * @param showShare
	 * @return
	 * @throws Exception
	 */
	private static TreeModel buildTreeByMuSellProNodes(ItemAction actionOnLoad,
			MoneySysTypeEnum sysTypeParam, String detailNodeType,
			boolean isMuPerm, boolean showShare) throws Exception {
		TreeModel tree = getSaleOrgTree(actionOnLoad); // ����֯���������Ŀ�ڵ�
		DefaultMutableTreeNode node = (DefaultMutableTreeNode) tree.getRoot();
		Map orgIdMap = getAllObjectIdMap(node, "OrgStructure");

		ArrayList muSellProTreeNodes = MarketingUnitFactory.getRemoteInstance()
				.getMuSellProTreeNodes(detailNodeType, showShare);
		for (int i = 0; i < muSellProTreeNodes.size(); i++) {
			DefaultKingdeeTreeNode spNode = (DefaultKingdeeTreeNode) muSellProTreeNodes
					.get(i);
			SellProjectInfo spInfo = (SellProjectInfo) spNode.getUserObject();
			if (sysTypeParam == null
					|| (sysTypeParam.equals(MoneySysTypeEnum.SalehouseSys) && spInfo
							.isIsForSHE())
					|| (sysTypeParam.equals(MoneySysTypeEnum.TenancySys) && spInfo
							.isIsForTen())
					|| (sysTypeParam.equals(MoneySysTypeEnum.ManageSys) && spInfo
							.isIsForPPM())) {

				DefaultMutableTreeNode orgTreeNode = (DefaultMutableTreeNode) orgIdMap
						.get(spInfo.getOrgUnit().getId().toString());
				if (orgTreeNode != null)
					orgTreeNode.add(spNode);
			}
		}

		// ˢ�½ڵ�ͼ��
		setCustomerIcon(node);
		return tree;
	}

	/** @deprecated */
	public static TreeModel getUnitTree_Bak(ItemAction actionOnLoad,
			MoneySysTypeEnum sysTypeParam) throws Exception {
		TreeModel tree = getBuildingTree(actionOnLoad, sysTypeParam);

		DefaultMutableTreeNode node = (DefaultMutableTreeNode) tree.getRoot();
		Map buildIdMap = getAllObjectIdMap(node, "Building"); // ¥����idӳ��
		if (buildIdMap == null || buildIdMap.size() == 0)
			return tree;

		BuildingUnitCollection buildUnitColl = BuildingUnitFactory
				.getRemoteInstance().getBuildingUnitCollection(
						"select id,name,number,seq,building.id where building.id in ("
								+ getStringFromSet(buildIdMap.keySet())
								+ ") order by seq");
		if (buildUnitColl.size() == 0)
			return tree;

		for (int i = 0; i < buildUnitColl.size(); i++) {
			BuildingUnitInfo thisInfo = buildUnitColl.get(i);
			KDTreeNode newNode = getNewKDTreeNodeByObject(thisInfo, null);
			if (newNode != null) {
				DefaultMutableTreeNode buildNode = (DefaultMutableTreeNode) buildIdMap
						.get(thisInfo.getBuilding().getId().toString());
				if (buildNode != null)
					buildNode.add(newNode);
			}
		}

		return tree;
	}

	/**
	 * ����Ӫ����Ԫ�� -- δ��Ӫ��Ȩ������ ���ͽṹ ������֯ - Ӫ����Ԫ - (������+��Ա)
	 * 
	 * @includeUser �Ƿ����Ӫ����Ա
	 */
	public static TreeModel getMarketTree(ItemAction actionOnLoad,
			boolean includeUser) throws Exception {
		TreeModel tree = getSaleOrgTree(actionOnLoad);

		DefaultMutableTreeNode node = (DefaultMutableTreeNode) tree.getRoot();
		Map orgIdMap = getAllObjectIdMap(node, "OrgStructure");
		String orgIdsStr = getStringFromSet(orgIdMap.keySet());
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(
				new FilterItemInfo("orgUnit.id", orgIdsStr, CompareType.INNER));
		ITreeBuilder treeBuilder = TreeBuilderFactory.createTreeBuilder(
				new DefaultLNTreeNodeCtrl(MarketingUnitFactory
						.getRemoteInstance()), Integer.MAX_VALUE, 5, filter);
		KDTree kdTree = treeBuilder.buildTree(null);

		KDTreeNode kdNode = (KDTreeNode) kdTree.getModel().getRoot();
		for (int i = 0; i < kdNode.getChildCount(); i++) { // ����֯�������Ӫ����Ԫ�ڵ�
			KDTreeNode thisNode = (KDTreeNode) kdNode.getChildAt(i);
			KDTreeNode newNode = (KDTreeNode) thisNode.clone();
			cloneTree(newNode, thisNode);
			newNode.setCustomIcon(EASResource.getIcon("imgTbtn_BatchAdd"));
			MarketingUnitInfo muInfo = (MarketingUnitInfo) newNode
					.getUserObject();
			if (muInfo.getOrgUnit() != null) {
				DefaultMutableTreeNode saleOrgNode = (DefaultMutableTreeNode) orgIdMap
						.get(muInfo.getOrgUnit().getId().toString());
				if (saleOrgNode != null)
					saleOrgNode.add(newNode);
			}
			if (!newNode.isLeaf())
				updateSonsNodeCustomIcon(newNode);
		}

		if (includeUser) {
			// ����֯�Ϲҹܿ���Ա
			MarketUnitControlCollection muControlColl = MarketUnitControlFactory
					.getRemoteInstance()
					.getMarketUnitControlCollection(
							"select controler.id,controler.name,controler.number,orgUnit.id where orgUnit.id in("
									+ orgIdsStr + ") ");
			for (int i = 0; i < muControlColl.size(); i++) {
				MarketUnitControlInfo muControlInfo = muControlColl.get(i);
				UserInfo thisInfo = muControlInfo.getControler();
				KDTreeNode newNode = getNewKDTreeNodeByObject(thisInfo, "admin");
				if (newNode != null) {
					DefaultMutableTreeNode muNode = (DefaultMutableTreeNode) orgIdMap
							.get(muControlInfo.getOrgUnit().getId().toString());
					if (muNode != null)
						muNode.insert(newNode, 0);
				}
			}

			// ��Ӫ����Ԫ�ڵ��Ϲ�Ӫ������
			Map muIdMap = getAllObjectIdMap(node, "MarketingUnit");
			MarketingUnitMemberCollection muMember = MarketingUnitMemberFactory
					.getRemoteInstance()
					.getMarketingUnitMemberCollection(
							"select member.id,member.name,member.number,head.id,isDuty where head.orgUnit.id in ("
									+ orgIdsStr + ")");
			for (int i = 0; i < muMember.size(); i++) {
				MarketingUnitMemberInfo muMemberInfo = muMember.get(i);
				UserInfo thisInfo = muMemberInfo.getMember();
				KDTreeNode newNode = getNewKDTreeNodeByObject(thisInfo,
						muMemberInfo.isIsDuty() ? "admin" : "person");
				if (newNode != null) {
					DefaultMutableTreeNode muNode = (DefaultMutableTreeNode) muIdMap
							.get(muMemberInfo.getHead().getId().toString());
					if (muNode != null) {
						if (muMemberInfo.isIsDuty())
							muNode.insert(newNode, 0);
						else
							muNode.add(newNode);
					}
				}
			}
		}
		return tree;
	}

	/**
	 * ��Ӫ��Ȩ�����Ƶĵ�Ԫ�� --����Ӫ����Ա
	 */
	public static TreeModel getMarketTree(ItemAction actionOnLoad)
			throws Exception {
		TreeModel tree = getSaleOrgTree(actionOnLoad);

		DefaultMutableTreeNode node = (DefaultMutableTreeNode) tree.getRoot();
		Map orgIdMap = getAllObjectIdMap(node, "OrgStructure");
		String orgIdsStr = getStringFromSet(orgIdMap.keySet());

		Set controlOrgIdSet = new HashSet(); // �ܿص���֯id����,��������֯��id
		UserInfo currUser = SysContext.getSysContext().getCurrentUserInfo();
		String currUserIdStr = currUser.getId().toString();
		MarketUnitControlCollection orgControlColl = MarketUnitControlFactory
				.getRemoteInstance().getMarketUnitControlCollection(
						"select orgUnit.id,orgUnit.number,orgUnit.longNumber where orgUnit.id in("
								+ orgIdsStr + ") and controler.id = '"
								+ currUserIdStr + "' ");
		if (orgControlColl.size() > 0) { // �ڵ�ǰ��֯���ǹܿ���Ա������Ҫ�ҹܿ���Ա
											// (�����Լ��͹ܿ���֯�µ����йܿ���Ա)
			String whereSql = "";
			for (int i = 0; i < orgControlColl.size(); i++) {
				MarketUnitControlInfo muControlInfo = orgControlColl.get(i);
				whereSql += "or (orgUnit.number = '"
						+ muControlInfo.getOrgUnit().getNumber()
						+ "' and controler.id ='" + currUserIdStr + "')  ";
				whereSql += " or orgUnit.longNumber like '"
						+ muControlInfo.getOrgUnit().getLongNumber() + "!%' ";
			}
			// ����֯�Ϲҹܿ���Ա
			MarketUnitControlCollection muControlColl = MarketUnitControlFactory
					.getRemoteInstance().getMarketUnitControlCollection(
							"select controler.id,controler.name,controler.number,orgUnit.id where "
									+ whereSql.replaceFirst("or", ""));
			for (int i = 0; i < muControlColl.size(); i++) {
				MarketUnitControlInfo muControlInfo = muControlColl.get(i);
				String controlOrgId = muControlInfo.getOrgUnit().getId()
						.toString();

				Map sonOrgIdMap = getAllObjectIdMap((TreeNode) orgIdMap
						.get(controlOrgId), "OrgStructure");
				controlOrgIdSet.addAll(sonOrgIdMap.keySet());

				UserInfo thisInfo = muControlInfo.getControler();
				KDTreeNode newNode = getNewKDTreeNodeByObject(thisInfo, "admin");
				if (newNode != null) {
					DefaultMutableTreeNode muNode = (DefaultMutableTreeNode) orgIdMap
							.get(muControlInfo.getOrgUnit().getId().toString());
					if (muNode != null)
						muNode.insert(newNode, 0);
				}
			}
		}

		String includeOrgIdStr = ""; // �ų��ܿ���֯�������֯
		if (controlOrgIdSet.size() > 0) {
			Iterator iterTemp = orgIdMap.keySet().iterator();
			while (iterTemp.hasNext()) {
				String orgIdStr = (String) iterTemp.next();
				if (!controlOrgIdSet.contains(orgIdStr)) { //���ǹܿص���֯���Ҳ��ǹܿص���֯������֯
					includeOrgIdStr += ",'" + orgIdStr + "'";
				}
			}
			includeOrgIdStr = includeOrgIdStr.replaceFirst(",", "");

			MarketingUnitMemberCollection muUnitColl = MarketingUnitMemberFactory
					.getRemoteInstance().getMarketingUnitMemberCollection(
							"select head.id,isDuty where head.orgUnit.id in ("
									+ getStringFromSet(controlOrgIdSet) + ") ");
			if (muUnitColl.size() > 0) {
				Set muIdSet = new HashSet(); // ����Ӫ����Ԫ���뼯��
				for (int i = 0; i < muUnitColl.size(); i++) {
					MarketingUnitMemberInfo muUnitInfo = muUnitColl.get(i);
					muIdSet.add(muUnitInfo.getHead().getId().toString());
				}

				FilterInfo filter = new FilterInfo();
				filter.getFilterItems().add(
						new FilterItemInfo("id", muIdSet, CompareType.INCLUDE));
				ITreeBuilder treeBuilder = TreeBuilderFactory
						.createTreeBuilder(new DefaultLNTreeNodeCtrl(
								MarketingUnitFactory.getRemoteInstance()),
								Integer.MAX_VALUE, 3, filter);
				KDTree kdTree = treeBuilder.buildTree(null);

				KDTreeNode kdNode = (KDTreeNode) kdTree.getModel().getRoot();
				for (int i = 0; i < kdNode.getChildCount(); i++) { // ����֯�������Ӫ����Ԫ�ڵ�
					KDTreeNode thisNode = (KDTreeNode) kdNode.getChildAt(i);
					KDTreeNode newNode = (KDTreeNode) thisNode.clone();
					cloneTree(newNode, thisNode);
					newNode.setCustomIcon(EASResource
							.getIcon("imgTbtn_BatchAdd"));
					MarketingUnitInfo muInfo = (MarketingUnitInfo) newNode
							.getUserObject();
					if (muInfo.getOrgUnit() != null) {
						DefaultMutableTreeNode saleOrgNode = (DefaultMutableTreeNode) orgIdMap
								.get(muInfo.getOrgUnit().getId().toString());
						if (saleOrgNode != null)
							saleOrgNode.add(newNode);
					}
					if (!newNode.isLeaf())
						updateSonsNodeCustomIcon(newNode);
				}

				// ��Ӫ����Ԫ�ڵ��Ϲ�Ӫ������ ȫ����
				MarketingUnitMemberCollection muMember = MarketingUnitMemberFactory
						.getRemoteInstance().getMarketingUnitMemberCollection(
								"select member.id,member.name,member.number,head.id,isDuty where head.id in ("
										+ getStringFromSet(muIdSet) + ") ");
				Map muIdMap = getAllObjectIdMap(node, "MarketingUnit");
				for (int i = 0; i < muMember.size(); i++) {
					MarketingUnitMemberInfo muMemberInfo = muMember.get(i);
					UserInfo thisInfo = muMemberInfo.getMember();
					KDTreeNode newNode = getNewKDTreeNodeByObject(thisInfo,
							muMemberInfo.isIsDuty() ? "admin" : "person");
					if (newNode != null) {
						DefaultMutableTreeNode muNode = (DefaultMutableTreeNode) muIdMap
								.get(muMemberInfo.getHead().getId().toString());
						if (muNode != null) {
							if (muMemberInfo.isIsDuty())
								muNode.insert(newNode, 0);
							else
								muNode.add(newNode);
						}
					}
				}
			}

		} else {
			includeOrgIdStr = orgIdsStr;
		}

		if (!includeOrgIdStr.equals("")) {
			// ��ǰ��Ա�ĵ�ǰ��֯������Ӫ����ɫ �����ǵ�Ӧ���Ƿǹܿص���֯����֯��ΧҪ�ų��ܿص���֯�����¼���֯����
			MarketingUnitMemberCollection muUnitColl = MarketingUnitMemberFactory
					.getRemoteInstance().getMarketingUnitMemberCollection(
							"select head.longNumber,head.number,isDuty where member.id='"
									+ currUserIdStr
									+ "' and head.orgUnit.id in ("
									+ includeOrgIdStr + ") ");
			if (muUnitColl.size() > 0) {
				Set upMuNumbersSet = new HashSet(); // ����(�����Լ�)����������Ӫ����Ԫ���뼯��
				Set dutyMuLongNumberSet = new HashSet(); // ��Ϊ�����˵ĵ�Ԫ�����뼯��
				for (int i = 0; i < muUnitColl.size(); i++) {
					MarketingUnitMemberInfo muUnitInfo = muUnitColl.get(i);
					String longNumber = muUnitInfo.getHead().getLongNumber();
					String numbers[] = longNumber.split("!");
					for (int j = 0; j < numbers.length; j++)
						upMuNumbersSet.add(numbers[j]);
					if (muUnitInfo.isIsDuty()) { // ����Ϊ�����ˣ��¼���ԪҲ���Է���
						dutyMuLongNumberSet.add(muUnitInfo.getHead()
								.getLongNumber());
					}
				}

				FilterInfo filter = new FilterInfo();
				filter.getFilterItems().add(
						new FilterItemInfo("number", upMuNumbersSet,
								CompareType.INCLUDE));
				Iterator iter = dutyMuLongNumberSet.iterator();
				while (iter.hasNext()) {
					String longNumber = (String) iter.next();
					FilterInfo sonFilter = new FilterInfo();
					sonFilter.getFilterItems().add(
							new FilterItemInfo("longNumber", longNumber + "!%",
									CompareType.LIKE));
					filter.mergeFilter(sonFilter, "OR");
				}
				ITreeBuilder treeBuilder = TreeBuilderFactory
						.createTreeBuilder(new DefaultLNTreeNodeCtrl(
								MarketingUnitFactory.getRemoteInstance()),
								Integer.MAX_VALUE, 3, filter);
				KDTree kdTree = treeBuilder.buildTree(null);

				KDTreeNode kdNode = (KDTreeNode) kdTree.getModel().getRoot();
				for (int i = 0; i < kdNode.getChildCount(); i++) { // ����֯�������Ӫ����Ԫ�ڵ�
					KDTreeNode thisNode = (KDTreeNode) kdNode.getChildAt(i);
					KDTreeNode newNode = (KDTreeNode) thisNode.clone();
					cloneTree(newNode, thisNode);
					newNode.setCustomIcon(EASResource
							.getIcon("imgTbtn_BatchAdd"));
					MarketingUnitInfo muInfo = (MarketingUnitInfo) newNode
							.getUserObject();
					if (muInfo.getOrgUnit() != null) {
						DefaultMutableTreeNode saleOrgNode = (DefaultMutableTreeNode) orgIdMap
								.get(muInfo.getOrgUnit().getId().toString());
						if (saleOrgNode != null)
							saleOrgNode.add(newNode);
					}
					if (!newNode.isLeaf())
						updateSonsNodeCustomIcon(newNode);
				}

				// ��Ӫ����Ԫ�ڵ��Ϲ�Ӫ������ 1.��Ϊ��Ա�����˵��Լ���2��Ϊ�����˵ĳ�Ա���¼������˺ͳ�Ա
				String memWhereFilter = " member.id='" + currUserIdStr + "' "; // head
																				// .
																				// orgUnit
																				// .
																				// id
																				// in
																				// (
																				// "+orgIdsStr+"
																				// )
				Iterator dutyIter = dutyMuLongNumberSet.iterator();
				while (dutyIter.hasNext()) {
					String dutyLongNumber = (String) dutyIter.next();
					memWhereFilter += "  or (head.longNumber = '"
							+ dutyLongNumber
							+ "' and isDuty=0) or head.longNumber like '"
							+ dutyLongNumber + "!%'   ";
				}
				MarketingUnitMemberCollection muMember = MarketingUnitMemberFactory
						.getRemoteInstance().getMarketingUnitMemberCollection(
								"select member.id,member.name,member.number,head.id,isDuty where "
										+ memWhereFilter
										+ " order by isDuty desc");
				Map muIdMap = getAllObjectIdMap(node, "MarketingUnit");
				for (int i = 0; i < muMember.size(); i++) {
					MarketingUnitMemberInfo muMemberInfo = muMember.get(i);
					UserInfo thisInfo = muMemberInfo.getMember();
					KDTreeNode newNode = getNewKDTreeNodeByObject(thisInfo,
							muMemberInfo.isIsDuty() ? "admin" : "person");
					if (newNode != null) {
						DefaultMutableTreeNode muNode = (DefaultMutableTreeNode) muIdMap
								.get(muMemberInfo.getHead().getId().toString());
						if (muNode != null)
							muNode.add(newNode);
					}
				}
			}
		}
		return tree;
	}

	/**
	 * ��Ӫ��Ȩ�����Ƶĵ�Ԫ�� --����Ӫ����Ա
	 */
	public static TreeModel getMarketTree(ItemAction actionOnLoad, Map dataMap)
			throws Exception {
		Date beginDate = null;
		Date endDate = null;
		if (dataMap.containsKey("beginDate")) {
			beginDate = (Date) dataMap.get("beginDate");
		}
		if (dataMap.containsKey("endDate")) {
			endDate = (Date) dataMap.get("endDate");
		}

		TreeModel tree = getSaleOrgTree(actionOnLoad);

		DefaultMutableTreeNode node = (DefaultMutableTreeNode) tree.getRoot();
		Map orgIdMap = getAllObjectIdMap(node, "OrgStructure");
		String orgIdsStr = getStringFromSet(orgIdMap.keySet());

		Set controlOrgIdSet = new HashSet(); // �ܿص���֯id����,��������֯��id
		UserInfo currUser = SysContext.getSysContext().getCurrentUserInfo();
		String currUserIdStr = currUser.getId().toString();
		MarketUnitControlCollection orgControlColl = MarketUnitControlFactory
				.getRemoteInstance().getMarketUnitControlCollection(
						"select orgUnit.id,orgUnit.number,orgUnit.longNumber where orgUnit.id in("
								+ orgIdsStr + ") and controler.id = '"
								+ currUserIdStr + "' ");
		if (orgControlColl.size() > 0) { // �ڵ�ǰ��֯���ǹܿ���Ա������Ҫ�ҹܿ���Ա
											// (�����Լ��͹ܿ���֯�µ����йܿ���Ա)
			String whereSql = "";
			for (int i = 0; i < orgControlColl.size(); i++) {
				MarketUnitControlInfo muControlInfo = orgControlColl.get(i);
				whereSql += "or (orgUnit.number = '"
						+ muControlInfo.getOrgUnit().getNumber()
						+ "' and controler.id ='" + currUserIdStr + "')  ";
				whereSql += " or orgUnit.longNumber like '"
						+ muControlInfo.getOrgUnit().getLongNumber() + "!%' ";
			}
			// ����֯�Ϲҹܿ���Ա
			MarketUnitControlCollection muControlColl = MarketUnitControlFactory
					.getRemoteInstance().getMarketUnitControlCollection(
							"select controler.id,controler.name,controler.number,orgUnit.id where "
									+ whereSql.replaceFirst("or", ""));
			for (int i = 0; i < muControlColl.size(); i++) {
				MarketUnitControlInfo muControlInfo = muControlColl.get(i);
				String controlOrgId = muControlInfo.getOrgUnit().getId()
						.toString();

				Map sonOrgIdMap = getAllObjectIdMap((TreeNode) orgIdMap
						.get(controlOrgId), "OrgStructure");
				controlOrgIdSet.addAll(sonOrgIdMap.keySet());

				UserInfo thisInfo = muControlInfo.getControler();
				KDTreeNode newNode = getNewKDTreeNodeByObject(thisInfo, "admin");
				if (newNode != null) {
					DefaultMutableTreeNode muNode = (DefaultMutableTreeNode) orgIdMap
							.get(muControlInfo.getOrgUnit().getId().toString());
					if (muNode != null)
						muNode.insert(newNode, 0);
				}
			}
		}

		String includeOrgIdStr = ""; // �ų��ܿ���֯�������֯
		if (controlOrgIdSet.size() > 0) {
			Iterator iterTemp = orgIdMap.keySet().iterator();
			while (iterTemp.hasNext()) {
				String orgIdStr = (String) iterTemp.next();
				if (!controlOrgIdSet.contains(orgIdStr)) { //���ǹܿص���֯���Ҳ��ǹܿص���֯������֯
					includeOrgIdStr += ",'" + orgIdStr + "'";
				}
			}
			includeOrgIdStr = includeOrgIdStr.replaceFirst(",", "");

			MarketingUnitMemberCollection muUnitColl = MarketingUnitMemberFactory
					.getRemoteInstance().getMarketingUnitMemberCollection(
							"select head.id,isDuty where head.orgUnit.id in ("
									+ getStringFromSet(controlOrgIdSet)
									+ ") "
									+ appendFilterSql("accessionDate",
											beginDate, endDate));
			if (muUnitColl.size() > 0) {
				Set muIdSet = new HashSet(); // ����Ӫ����Ԫ���뼯��
				for (int i = 0; i < muUnitColl.size(); i++) {
					MarketingUnitMemberInfo muUnitInfo = muUnitColl.get(i);
					muIdSet.add(muUnitInfo.getHead().getId().toString());
				}

				FilterInfo filter = new FilterInfo();
				filter.getFilterItems().add(
						new FilterItemInfo("id", muIdSet, CompareType.INCLUDE));
				ITreeBuilder treeBuilder = TreeBuilderFactory
						.createTreeBuilder(new DefaultLNTreeNodeCtrl(
								MarketingUnitFactory.getRemoteInstance()),
								Integer.MAX_VALUE, 3, filter);
				KDTree kdTree = treeBuilder.buildTree(null);

				KDTreeNode kdNode = (KDTreeNode) kdTree.getModel().getRoot();
				for (int i = 0; i < kdNode.getChildCount(); i++) { // ����֯�������Ӫ����Ԫ�ڵ�
					KDTreeNode thisNode = (KDTreeNode) kdNode.getChildAt(i);
					KDTreeNode newNode = (KDTreeNode) thisNode.clone();
					cloneTree(newNode, thisNode);
					newNode.setCustomIcon(EASResource
							.getIcon("imgTbtn_BatchAdd"));
					MarketingUnitInfo muInfo = (MarketingUnitInfo) newNode
							.getUserObject();
					if (muInfo.getOrgUnit() != null) {
						DefaultMutableTreeNode saleOrgNode = (DefaultMutableTreeNode) orgIdMap
								.get(muInfo.getOrgUnit().getId().toString());
						if (saleOrgNode != null)
							saleOrgNode.add(newNode);
					}
					if (!newNode.isLeaf())
						updateSonsNodeCustomIcon(newNode);
				}

				// ��Ӫ����Ԫ�ڵ��Ϲ�Ӫ������ ȫ����
				MarketingUnitMemberCollection muMember = MarketingUnitMemberFactory
						.getRemoteInstance()
						.getMarketingUnitMemberCollection(
								"select member.id,member.name,member.number,head.id,isDuty ,accessionDate where head.id in ("
										+ getStringFromSet(muIdSet)
										+ ")"
										+ appendFilterSql("accessionDate",
												beginDate, endDate));
				Map muIdMap = getAllObjectIdMap(node, "MarketingUnit");
				for (int i = 0; i < muMember.size(); i++) {
					MarketingUnitMemberInfo muMemberInfo = muMember.get(i);
					UserInfo thisInfo = muMemberInfo.getMember();
					KDTreeNode newNode = getNewKDTreeNodeByObject(thisInfo,
							muMemberInfo.isIsDuty() ? "admin" : "person");
					if (newNode != null) {
						DefaultMutableTreeNode muNode = (DefaultMutableTreeNode) muIdMap
								.get(muMemberInfo.getHead().getId().toString());
						if (muNode != null) {
							if (muMemberInfo.isIsDuty())
								muNode.insert(newNode, 0);
							else
								muNode.add(newNode);
						}
					}
				}
			}

		} else {
			includeOrgIdStr = orgIdsStr;
		}

		if (!includeOrgIdStr.equals("")) {
			// ��ǰ��Ա�ĵ�ǰ��֯������Ӫ����ɫ �����ǵ�Ӧ���Ƿǹܿص���֯����֯��ΧҪ�ų��ܿص���֯�����¼���֯����
			MarketingUnitMemberCollection muUnitColl = MarketingUnitMemberFactory
					.getRemoteInstance().getMarketingUnitMemberCollection(
							"select head.longNumber,head.number,isDuty where member.id='"
									+ currUserIdStr
									+ "' and head.orgUnit.id in ("
									+ includeOrgIdStr + ") ");
			if (muUnitColl.size() > 0) {
				Set upMuNumbersSet = new HashSet(); // ����(�����Լ�)����������Ӫ����Ԫ���뼯��
				Set dutyMuLongNumberSet = new HashSet(); // ��Ϊ�����˵ĵ�Ԫ�����뼯��
				for (int i = 0; i < muUnitColl.size(); i++) {
					MarketingUnitMemberInfo muUnitInfo = muUnitColl.get(i);
					String longNumber = muUnitInfo.getHead().getLongNumber();
					String numbers[] = longNumber.split("!");
					for (int j = 0; j < numbers.length; j++)
						upMuNumbersSet.add(numbers[j]);
					if (muUnitInfo.isIsDuty()) { // ����Ϊ�����ˣ��¼���ԪҲ���Է���
						dutyMuLongNumberSet.add(muUnitInfo.getHead()
								.getLongNumber());
					}
				}

				FilterInfo filter = new FilterInfo();
				filter.getFilterItems().add(
						new FilterItemInfo("number", upMuNumbersSet,
								CompareType.INCLUDE));
				Iterator iter = dutyMuLongNumberSet.iterator();
				while (iter.hasNext()) {
					String longNumber = (String) iter.next();
					FilterInfo sonFilter = new FilterInfo();
					sonFilter.getFilterItems().add(
							new FilterItemInfo("longNumber", longNumber + "!%",
									CompareType.LIKE));
					filter.mergeFilter(sonFilter, "OR");
				}
				ITreeBuilder treeBuilder = TreeBuilderFactory
						.createTreeBuilder(new DefaultLNTreeNodeCtrl(
								MarketingUnitFactory.getRemoteInstance()),
								Integer.MAX_VALUE, 3, filter);
				KDTree kdTree = treeBuilder.buildTree(null);

				KDTreeNode kdNode = (KDTreeNode) kdTree.getModel().getRoot();
				for (int i = 0; i < kdNode.getChildCount(); i++) { // ����֯�������Ӫ����Ԫ�ڵ�
					KDTreeNode thisNode = (KDTreeNode) kdNode.getChildAt(i);
					KDTreeNode newNode = (KDTreeNode) thisNode.clone();
					cloneTree(newNode, thisNode);
					newNode.setCustomIcon(EASResource
							.getIcon("imgTbtn_BatchAdd"));
					MarketingUnitInfo muInfo = (MarketingUnitInfo) newNode
							.getUserObject();
					if (muInfo.getOrgUnit() != null) {
						DefaultMutableTreeNode saleOrgNode = (DefaultMutableTreeNode) orgIdMap
								.get(muInfo.getOrgUnit().getId().toString());
						if (saleOrgNode != null)
							saleOrgNode.add(newNode);
					}
					if (!newNode.isLeaf())
						updateSonsNodeCustomIcon(newNode);
				}

				// ��Ӫ����Ԫ�ڵ��Ϲ�Ӫ������ 1.��Ϊ��Ա�����˵��Լ���2��Ϊ�����˵ĳ�Ա���¼������˺ͳ�Ա
				String memWhereFilter = " member.id='" + currUserIdStr + "' "; // head
																				// .
																				// orgUnit
																				// .
																				// id
																				// in
																				// (
																				// "+orgIdsStr+"
																				// )
				Iterator dutyIter = dutyMuLongNumberSet.iterator();
				while (dutyIter.hasNext()) {
					String dutyLongNumber = (String) dutyIter.next();
					memWhereFilter += "  or (head.longNumber = '"
							+ dutyLongNumber
							+ "' and isDuty=0) or head.longNumber like '"
							+ dutyLongNumber + "!%'   ";
				}
				MarketingUnitMemberCollection muMember = MarketingUnitMemberFactory
						.getRemoteInstance()
						.getMarketingUnitMemberCollection(
								"select member.id,member.name,member.number,head.id,isDuty,accessionDate where ("
										+ memWhereFilter
										+ ")"
										+ appendFilterSql("accessionDate",
												beginDate, endDate)
										+ " order by isDuty desc");
				Map muIdMap = getAllObjectIdMap(node, "MarketingUnit");
				for (int i = 0; i < muMember.size(); i++) {
					MarketingUnitMemberInfo muMemberInfo = muMember.get(i);
					UserInfo thisInfo = muMemberInfo.getMember();
					KDTreeNode newNode = getNewKDTreeNodeByObject(thisInfo,
							muMemberInfo.isIsDuty() ? "admin" : "person");
					if (newNode != null) {
						DefaultMutableTreeNode muNode = (DefaultMutableTreeNode) muIdMap
								.get(muMemberInfo.getHead().getId().toString());
						if (muNode != null)
							muNode.add(newNode);
					}
				}
			}
		}
		return tree;
	}

	public static String appendFilterSql(String proName, Date beginDate,
			Date endDate) {
		StringBuffer sb = new StringBuffer();
		// if (beginDate != null) {
		// sb.append(" and " + proName + ">='");
		// sb.append(FDCDateHelper.getSqlDate(beginDate)).append("' ");
		// }else{//CRMHelper getServerDate
		// sb.append(" and " + proName + "<='");
		//sb.append(FDCDateHelper.getSqlDate(CRMHelper.getServerDate2())).append
		// ("' ");
		// }
		// if (endDate != null) {
		// sb.append(" and " + proName + "<'");
		// sb.append(FDCDateHelper.getSqlDate(endDate)).append("'");
		// }
		if (beginDate != null && endDate == null) {
			sb.append(" and " + proName + "<'");
			sb.append(FDCDateHelper.getSqlDate(beginDate)).append("' ");
		} else if (beginDate == null && endDate != null) {
			sb.append(" and " + proName + "<'");
			sb.append(FDCDateHelper.getSqlDate(endDate)).append("'");
		} else if (beginDate != null && endDate != null) {
			sb.append(" and " + proName + "<'");
			sb.append(FDCDateHelper.getSqlDate(endDate)).append("'");
		} else {
			sb.append(" and " + proName + "<='");
			sb.append(FDCDateHelper.getSqlDate(CRMHelper.getServerDate2()))
					.append("' ");
		}

		return sb.toString();
	}

	public static TreeModel getBaseDataTree(ItemAction actionOnLoad,
			MoneySysTypeEnum sysTypeParam) throws Exception {
		return getBaseDataOrCuManagerTree(actionOnLoad, sysTypeParam, false);
	}

	public static TreeModel getCUManagerTree(ItemAction actionOnLoad,
			MoneySysTypeEnum sysTypeParam) throws Exception {
		return getBaseDataOrCuManagerTree(actionOnLoad, sysTypeParam, true);
	}

	public static TreeModel getBaseDataOrCuManagerTree(ItemAction actionOnLoad,
			MoneySysTypeEnum sysTypeParam, boolean isCuManager)
			throws Exception {
		TreeModel tree = getSellProjectTree(actionOnLoad, sysTypeParam);

		DefaultMutableTreeNode node = (DefaultMutableTreeNode) tree.getRoot();
		Map sellProIdMap = getAllObjectIdMap(node, "SellProject"); // ������Ŀ��idӳ��
		if (sellProIdMap == null || sellProIdMap.size() == 0)
			return tree;

		Map roomArchIdMap = new HashMap();
		Map customerArchIdMap = new HashMap();
		Map commerArchIdMap = new HashMap();
		Iterator iter = sellProIdMap.keySet().iterator();
		while (iter.hasNext()) {
			String spIdStr = (String) iter.next();
			DefaultMutableTreeNode projectNode = (DefaultMutableTreeNode) sellProIdMap
					.get(spIdStr);
			KDTreeNode roomArchNode = new KDTreeNode("���䵵��");
			KDTreeNode customerArchNode = new KDTreeNode("�ͻ�����");
			KDTreeNode commerArchNode = new KDTreeNode("�̻�����");
			projectNode.add(roomArchNode);
			projectNode.add(customerArchNode);
			projectNode.add(commerArchNode);
			roomArchIdMap.put(spIdStr, roomArchNode);
			customerArchIdMap.put(spIdStr, customerArchNode);
			commerArchIdMap.put(spIdStr, commerArchNode);
		}

		// ��÷�����������Ŀ�����ļ��ϣ������������������
		ProjectArchivesEntryCollection projectArchEntrys = ProjectArchivesEntryFactory
				.getRemoteInstance().getProjectArchivesEntryCollection(
						"select id,name,number,sellProject.id where sellProject.id in ("
								+ getStringFromSet(sellProIdMap.keySet())
								+ ")  order by baseDataProperty");
		for (int j = 0; j < projectArchEntrys.size(); j++) {
			ProjectArchivesEntryInfo projectArchInfo = projectArchEntrys.get(j);
			projectArchInfo = ProjectArchivesEntryFactory.getRemoteInstance()
					.getProjectArchivesEntryInfo(
							new ObjectUuidPK(projectArchInfo.getId()));
			KDTreeNode projectArchivesNode = new KDTreeNode(projectArchInfo
					.getArchivesName());
			if (projectArchInfo.getBaseDataProperty() != null
					&& projectArchInfo.getBaseDataProperty().equals(
							BaseDataPropertyEnum.CUControl)) {
				projectArchivesNode.setCustomIcon(EASResource
						.getIcon("imgTree_folder_leaf"));
			} else {
				if (!isCuManager)
					projectArchivesNode.setCustomIcon(EASResource
							.getIcon("imgTree_bigicon_end"));
			}
			projectArchivesNode.setUserObject(projectArchInfo);
			String spIdStr = projectArchInfo.getSellProject().getId()
					.toString();
			if (ArchivesTypeEnum.RoomArch.equals(projectArchInfo
					.getArchivesType())) {
				if (!isCuManager
						|| projectArchInfo.getBaseDataProperty().equals(
								BaseDataPropertyEnum.CUControl)) {
					DefaultMutableTreeNode roomArchNode = (DefaultMutableTreeNode) roomArchIdMap
							.get(spIdStr);
					roomArchNode.add(projectArchivesNode);
				}
			} else if (ArchivesTypeEnum.CustomerArch.equals(projectArchInfo
					.getArchivesType())) {
				DefaultMutableTreeNode customerArchNode = (DefaultMutableTreeNode) customerArchIdMap
						.get(spIdStr);
				customerArchNode.add(projectArchivesNode);
			} else if (ArchivesTypeEnum.commerArch.equals(projectArchInfo
					.getArchivesType())) {
				DefaultMutableTreeNode commerArchNode = (DefaultMutableTreeNode) commerArchIdMap
						.get(spIdStr);
				commerArchNode.add(projectArchivesNode);
			}
		}

		return tree;
	}

	/**
	 * ��¡��ѡ���ڵ㣬�����ӽڵ�
	 */
	public static void cloneTree(DefaultKingdeeTreeNode newNode,
			DefaultKingdeeTreeNode oriNode) {
		for (int i = 0; i < oriNode.getChildCount(); i++) {
			DefaultKingdeeTreeNode oriChild = (DefaultKingdeeTreeNode) oriNode
					.getChildAt(i);
			DefaultKingdeeTreeNode child = (DefaultKingdeeTreeNode) oriChild
					.clone();
			newNode.add(child);
			cloneTree(child, oriChild);
		}
	}

	private static void updateSonsNodeCustomIcon(KDTreeNode thisNode) {
		for (int i = 0; i < thisNode.getChildCount(); i++) {
			KDTreeNode sonNode = (KDTreeNode) thisNode.getChildAt(i);
			sonNode.setCustomIcon(EASResource.getIcon("imgTbtn_BatchAdd"));
			if (!sonNode.isLeaf())
				updateSonsNodeCustomIcon(sonNode);
		}
	}

	/**
	 * ��ָ��������key����
	 * 
	 * @param coll
	 *            ����ļ���
	 * @param key
	 *            ָ���������������
	 */
	public static void sortCollection(IObjectCollection coll, String key) {
		Map map = new TreeMap();

		Iterator iterator = coll.iterator();
		while (iterator.hasNext()) {
			CoreBaseInfo info = (CoreBaseInfo) iterator.next();
			map.put(info.get(key), info.clone());
		}
		coll.clear();
		iterator = map.values().iterator();
		while (iterator.hasNext()) {
			CoreBaseInfo info = (CoreBaseInfo) iterator.next();
			coll.addObject(info);
		}
	}

	////////////////////////////////////////////////////////////////////////////
	// //////////////////////////
	// ����ָ��������֯��������Ŀ������
	/**
	 * ����¥���� ���ͽṹ ������֯ - ������Ŀ - ���� - ¥��
	 */
	public static TreeModel getBuildingTree(ItemAction actionOnLoad,
			OrgUnitInfo orgInfo, SellProjectInfo projectInfo,
			MoneySysTypeEnum sysTypeParam) throws Exception {
		return buildTreeByMuSellProNodes(actionOnLoad, orgInfo, projectInfo,
				sysTypeParam, "Building", true);
	}

	/**
	 * ���������� ���ͽṹ ������֯ - ������Ŀ - ����
	 */
	public static TreeModel getSubareaTree(ItemAction actionOnLoad,
			OrgUnitInfo orgInfo, SellProjectInfo projectInfo,
			MoneySysTypeEnum sysTypeParam) throws Exception {
		return buildTreeByMuSellProNodes(actionOnLoad, orgInfo, projectInfo,
				sysTypeParam, "Subarea", true);
	}

	/**
	 * ����������Ŀ�� -- ����Ŀ����Ӫ��Ȩ���й� ���ͽṹ ������֯ - ������Ŀ
	 * 
	 * @param sysTypeParam
	 *            ӵ����¥���Եġ�ӵ���������Եġ�ӵ����ҵ���Եģ� ��Ϊ�� ����ȫ������
	 */
	public static TreeModel getSellProjectTree(ItemAction actionOnLoad,
			OrgUnitInfo orgInfo, SellProjectInfo projectInfo,
			MoneySysTypeEnum sysTypeParam) throws Exception {
		return buildTreeByMuSellProNodes(actionOnLoad, orgInfo, projectInfo,
				sysTypeParam, "SellProject", true);
	}

	/**
	 * ���������� ���ͽṹ ������֯ - ������Ŀ - ���� - ¥�� - ��Ԫ - ����
	 * 
	 * @param sysTypeParam
	 *            ӵ����¥���Եġ�ӵ���������Եġ�ӵ����ҵ���Եģ� ��Ϊ�� ����ȫ������
	 * @author zhiyuan_tang <2010/08/28>
	 */
	public static TreeModel getRoomTree(ItemAction actionOnLoad,
			MoneySysTypeEnum sysTypeParam) throws Exception {
		return buildTreeByMuSellProNodes(actionOnLoad, sysTypeParam,
				"RoomUnit", true);
	}

	private static void addPlanisphereNode(DefaultMutableTreeNode parentNode,
			PlanisphereInfo phInfo) {
		KDTreeNode membNode = new KDTreeNode(phInfo.getName());
		membNode.setCustomIcon(EASResource.getIcon("imgTbtn_showpicture"));
		membNode.setUserObject(phInfo);
		parentNode.insert(membNode, 0);
	}
}
