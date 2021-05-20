package com.kingdee.eas.fdc.sellhouse.client;

import java.awt.Color;
import java.awt.Dialog;
import java.awt.Frame;
import java.awt.Point;
import java.awt.font.TextAttribute;
import java.io.File;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.AttributedString;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.Enumeration;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

import javax.swing.JFileChooser;
import javax.swing.JTextField;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreeNode;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.ctrl.common.LanguageManager;
import com.kingdee.bos.ctrl.common.variant.Variant;
import com.kingdee.bos.ctrl.excel.io.kds.KDSBookToBook;
import com.kingdee.bos.ctrl.excel.model.struct.Sheet;
import com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox;
import com.kingdee.bos.ctrl.extendcontrols.KDCommonPromptDialog;
import com.kingdee.bos.ctrl.kdf.kds.KDSBook;
import com.kingdee.bos.ctrl.kdf.kds.KDSSheet;
import com.kingdee.bos.ctrl.kdf.read.POIXlsReader;
import com.kingdee.bos.ctrl.kdf.servertable.KDTStyleConstants;
import com.kingdee.bos.ctrl.kdf.table.ICell;
import com.kingdee.bos.ctrl.kdf.table.IColumn;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectManager;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent;
import com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent;
import com.kingdee.bos.ctrl.kdf.table.event.KDTMouseListener;
import com.kingdee.bos.ctrl.kdf.util.style.Styles.HorizontalAlignment;
import com.kingdee.bos.ctrl.swing.KDFileChooser;
import com.kingdee.bos.ctrl.swing.KDFormattedTextField;
import com.kingdee.bos.ctrl.swing.KDScrollPane;
import com.kingdee.bos.ctrl.swing.KDTextField;
import com.kingdee.bos.ctrl.swing.KDTree;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.ctrl.swing.util.CtrlCommonConstant;
import com.kingdee.bos.ctrl.swing.util.SimpleFileFilter;
import com.kingdee.bos.dao.IObjectCollection;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.framework.cache.CacheServiceFactory;
import com.kingdee.bos.metadata.IMetaDataLoader;
import com.kingdee.bos.metadata.MetaDataLoaderFactory;
import com.kingdee.bos.metadata.MetaDataPK;
import com.kingdee.bos.metadata.data.SortType;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.metadata.entity.SorterItemCollection;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.ItemAction;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.base.codingrule.CodingRuleException;
import com.kingdee.eas.base.codingrule.CodingRuleManagerFactory;
import com.kingdee.eas.base.codingrule.ICodingRuleManager;
import com.kingdee.eas.base.permission.UserInfo;
import com.kingdee.eas.basedata.org.CompanyOrgUnitInfo;
import com.kingdee.eas.basedata.org.CtrlUnitInfo;
import com.kingdee.eas.basedata.org.FullOrgUnitCollection;
import com.kingdee.eas.basedata.org.FullOrgUnitFactory;
import com.kingdee.eas.basedata.org.FullOrgUnitInfo;
import com.kingdee.eas.basedata.org.NewOrgUtils;
import com.kingdee.eas.basedata.org.OrgStructureInfo;
import com.kingdee.eas.basedata.org.OrgUnitInfo;
import com.kingdee.eas.basedata.org.OrgViewType;
import com.kingdee.eas.basedata.org.SaleOrgUnitInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basecrm.CRMHelper;
import com.kingdee.eas.fdc.basecrm.FDCReceivingBillCollection;
import com.kingdee.eas.fdc.basecrm.FDCReceivingBillEntryCollection;
import com.kingdee.eas.fdc.basecrm.FDCReceivingBillEntryInfo;
import com.kingdee.eas.fdc.basecrm.FDCReceivingBillInfo;
import com.kingdee.eas.fdc.basecrm.RevBillTypeEnum;
import com.kingdee.eas.fdc.basecrm.SHERevBillCollection;
import com.kingdee.eas.fdc.basecrm.SHERevBillEntry;
import com.kingdee.eas.fdc.basecrm.SHERevBillEntryCollection;
import com.kingdee.eas.fdc.basecrm.SHERevBillEntryInfo;
import com.kingdee.eas.fdc.basecrm.SHERevBillFactory;
import com.kingdee.eas.fdc.basecrm.SHERevBillInfo;
import com.kingdee.eas.fdc.basecrm.SHERevMapCollection;
import com.kingdee.eas.fdc.basecrm.SHERevMapFactory;
import com.kingdee.eas.fdc.basecrm.TransferSourceEntryCollection;
import com.kingdee.eas.fdc.basecrm.TransferSourceEntryInfo;
import com.kingdee.eas.fdc.basecrm.client.CRMClientHelper;
import com.kingdee.eas.fdc.basecrm.client.CRMTreeHelper;
import com.kingdee.eas.fdc.basecrm.client.SHERevBillEditUI;
import com.kingdee.eas.fdc.basecrm.client.SHERevBillListUI;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCDateHelper;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.basedata.FDCSQLFacadeFactory;
import com.kingdee.eas.fdc.basedata.MoneySysTypeEnum;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.sellhouse.*;
import com.kingdee.eas.fdc.sellhouse.RoomDisplaySetting;
import com.kingdee.eas.fdc.tenancy.MarketUnitPlatCollection;
import com.kingdee.eas.fdc.tenancy.MarketUnitPlatFactory;
import com.kingdee.eas.fdc.tenancy.MarketUnitPlatInfo;
import com.kingdee.eas.fdc.tenancy.MarketingUnitInfo;
import com.kingdee.eas.fdc.tenancy.MarketingUnitMemberCollection;
import com.kingdee.eas.fdc.tenancy.MarketingUnitMemberFactory;
import com.kingdee.eas.fdc.tenancy.MarketingUnitMemberInfo;
import com.kingdee.eas.fdc.tenancy.MarketingUnitSellProjectCollection;
import com.kingdee.eas.fdc.tenancy.MarketingUnitSellProjectFactory;
import com.kingdee.eas.fdc.tenancy.MarketingUnitSellProjectInfo;
import com.kingdee.eas.fdc.tenancy.TenancyBillInfo;
import com.kingdee.eas.fdc.tenancy.TenancyRoomPayListEntryCollection;
import com.kingdee.eas.fdc.tenancy.TenancyRoomPayListEntryFactory;
import com.kingdee.eas.fdc.tenancy.TenancyRoomPayListEntryInfo;
import com.kingdee.eas.fdc.tenancy.TenancyStateEnum;
import com.kingdee.eas.fi.cas.ReceivingBillFactory;
import com.kingdee.eas.fi.cas.ReceivingBillInfo;
import com.kingdee.eas.fi.newrpt.client.designer.io.WizzardIO;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.eas.framework.DataBaseInfo;
import com.kingdee.eas.framework.client.tree.KDTreeNode;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.ExceptionHandler;
import com.kingdee.eas.util.client.MsgBox;
import com.kingdee.jdbc.rowset.IRowSet;
import com.kingdee.util.StringUtils;
import com.kingdee.util.UuidException;

public class SHEHelper {
	private static final Logger log = Logger.getLogger(SHEHelper.class);

	public final static String STATUS_ADDNEW = "ADDNEW";
	private static final String SHARE = "share";
	private static final String ADAPTER = "adapter";
	private static final String CANCELSHARE = "cancelShare";
	private static final String UPDATE = "update";
	public static boolean isConvert =false;
	//�Ƿ��巿��
	public static boolean isDes =false;
//	//������¥��������ʾ������Ϲ��տ�״̬�Ƿ���ɫ  by zgy 2010-12-15
	//ѡ����������
	private static boolean isOpenedChooseRoom = false;
	
	
//	static RoomDisplaySetting set = new RoomDisplaySetting(new String[]{RoomDisplaySetting.CHOOSE_ROOM_SET_MAP});
	private final static String SELLAREATYPE="�����������";

	
	//�������⣬ѡ������ɫ���� by tim_gao
	//ר��Ϊѡ����д��
	//����idMap
	
	private static Map roomIdMap =new HashMap();
	
    public static Map getRoomIdMap() {
		return roomIdMap;
	}
	public static void setRoomIdMap(Map roomIdMap) {
		SHEHelper.roomIdMap = roomIdMap;
	}
	
	public static void getAllChooseRoomIdSet() throws BOSException, EASBizException{
		
		IChooseRoom ichooseRoom = ChooseRoomFactory.getRemoteInstance();

		EntityViewInfo view = new EntityViewInfo();
		view.getSelector().add("room.sellState");
		view.getSelector().add("room.*");
		view.getSelector().add("id");
		view.getSelector().add("room.id");
		view.getSelector().add("chooseRoomStateEnum");
		view.getSelector().add("name");
		view.getSelector().add("number");
		ChooseRoomCollection chooseRoomCol = (ChooseRoomCollection)ichooseRoom.getChooseRoomCollection(view);
		
		
		for (Iterator it = chooseRoomCol.iterator();it.hasNext();){
			ChooseRoomInfo chooseRoomInfo = (ChooseRoomInfo) it.next();
//			boolean flag =false;
			if(!ChooseRoomStateEnum.CancelChoose.equals(chooseRoomInfo.getChooseRoomStateEnum())){
//				flag = ichooseRoom.isValid(chooseRoomInfo.getId().toString());
				if(null!=chooseRoomInfo.getRoom()){
					roomIdMap.put(chooseRoomInfo.getRoom().getId(),Boolean.TRUE);
				}
			}
		}
//		for(int i =0; i<chooseRoomCol.size();i++){
//			ChooseRoomInfo chooseRoomInfo = (ChooseRoomInfo)chooseRoomCol.get(i);
////			boolean flag =false;
//			if(!ChooseRoomStateEnum.CancelChoose.equals(chooseRoomInfo.getChooseRoomStateEnum())){
////				flag = ichooseRoom.isValid(chooseRoomInfo.getId().toString());
//				if(null!=chooseRoomInfo.getRoom()){
//					roomIdMap.put(chooseRoomInfo.getRoom().getId(),Boolean.TRUE);
//				}
//			}
			
			
		
//		}
		isOpenedChooseRoom = true;
	}
	//�ͷ�ѡ��id by tim_gao
	public static void releaseChooseRoomIdSet() throws BOSException, EASBizException{
		roomIdMap.clear();
		isOpenedChooseRoom = false;
	}
	
	/**
	 * @descrition ��ȡ¥���·��䶨���Ƿ�ת��Ϊ��ĸͬʱ��ֵ��SHEHelper�ľ�̬���� isConvert
	 * @author tim_gao
	 * @param BuildingInfo ¥��
	 * @throws BOSException 
	 * @throws EASBizException 
	 */
	
	public static boolean getIsCovertByRoomdes(BuildingInfo building) throws EASBizException, BOSException{
		//�Ƿ�ת���ַ�
		if(building!=null){
			FilterInfo filterIsConvert = new FilterInfo();
			filterIsConvert.getFilterItems().add(new FilterItemInfo("isConvertToChar",Boolean.TRUE));
			
			filterIsConvert.getFilterItems().add(new FilterItemInfo("building",building.getId().toString()));
			if(RoomDesFactory.getRemoteInstance().exists(filterIsConvert)){
				isConvert = true;
			}else{
				isConvert = false;
			}
		}
		
		return isConvert;
	}

	
	public static TreeModel getTree(String type, ItemAction actionOnLoad) throws Exception {
		return getTree(type, actionOnLoad, null);
	}

	public static TreeModel getTree(String type, ItemAction actionOnLoad, MoneySysTypeEnum sysTypeParam) throws Exception {
		SaleOrgUnitInfo saleOrg = getCurrentSaleOrg();
		MetaDataPK dataPK = null;
		if (actionOnLoad != null) {
			String actoinName = actionOnLoad.getClass().getName();
			if (actoinName.indexOf("$") >= 0) {
				actoinName = actoinName.substring(actoinName.indexOf("$") + 1);
			}
			dataPK = new MetaDataPK(actoinName);
		}
		TreeModel tree = NewOrgUtils.getTreeModel(OrgViewType.SALE, "", saleOrg.getId().toString(), null, dataPK); // ������֯��

		if (type.equals("saleOrg"))
			return tree;

		if (type.equals("subarea") || type.equals("building") || type.equals("unit")) {
			fillTreeNode(tree, type, sysTypeParam);
		}
		return tree;
	}
	public static TreeModel getTree(String type, ItemAction actionOnLoad, MoneySysTypeEnum sysTypeParam,String[] sellProjectId) throws Exception {
		SaleOrgUnitInfo saleOrg = getCurrentSaleOrg();
		MetaDataPK dataPK = null;
		if (actionOnLoad != null) {
			String actoinName = actionOnLoad.getClass().getName();
			if (actoinName.indexOf("$") >= 0) {
				actoinName = actoinName.substring(actoinName.indexOf("$") + 1);
			}
			dataPK = new MetaDataPK(actoinName);
		}
		TreeModel tree = NewOrgUtils.getTreeModel(OrgViewType.SALE, "", saleOrg.getId().toString(), null, dataPK); // ������֯��

		if (type.equals("saleOrg"))
			return tree;

		if (type.equals("subarea") || type.equals("building") || type.equals("unit")) {
			fillTreeNode(tree, type, sysTypeParam,sellProjectId);
		}
		return tree;
	}
	
	/**
	 * ͨ���ض�����Ŀ��������
	 * @param tree
	 * @param type
	 * @param sysTypeParam
	 * @param sellProject
	 * @throws BOSException
	 * @throws SQLException
	 */
	private static void fillTreeNode(TreeModel tree, String type, MoneySysTypeEnum sysTypeParam,String[] sellProjectId) throws BOSException, SQLException 
	{


		DefaultMutableTreeNode rootNode = (DefaultMutableTreeNode) tree.getRoot();
		Map orgIdMap = FDCTreeHelper.getAllObjectIdMap(rootNode, "OrgStructure");
		if (orgIdMap == null || orgIdMap.size() == 0)
			return;

		EntityViewInfo view = new EntityViewInfo();
		view.getSorter().add(new SorterItemInfo("number"));
		FilterInfo filter = new FilterInfo();
		try {
			String proIdSql = CommerceHelper.getPermitProjectIdSql(SysContext.getSysContext().getCurrentUserInfo());
			
			if(sellProjectId != null && sellProjectId.length > 0)
			{
				String temp = "";
				for(int i = 0; i < sellProjectId.length; i ++)
				{
					temp = temp + sellProjectId[i]+"',";
				}
				temp = "'"+temp;
				temp = temp.substring(0, temp.length()-1);
				
				proIdSql += " and FSellProjectID in( "+ temp+ " )";
			}
			
			filter.getFilterItems().add(new FilterItemInfo("id", proIdSql, CompareType.INNER));
		} catch (EASBizException e) {
			e.printStackTrace();
			SysUtil.abort();
		}
		if (sysTypeParam != null) {
			if (sysTypeParam.equals(MoneySysTypeEnum.SalehouseSys)) {
				filter.getFilterItems().add(new FilterItemInfo("isForSHE", Boolean.TRUE));
			} else if (sysTypeParam.equals(MoneySysTypeEnum.TenancySys)) {
				filter.getFilterItems().add(new FilterItemInfo("isForTen", Boolean.TRUE));
			} else if (sysTypeParam.equals(MoneySysTypeEnum.ManageSys)) {
				filter.getFilterItems().add(new FilterItemInfo("isForPPM", Boolean.TRUE));
			}
		}
		view.setFilter(filter);
		view.getSelector().add("*");
		view.getSelector().add("buildings.id");
		view.getSelector().add("buildings.name");
		view.getSelector().add("buildings.number");
		view.getSelector().add("buildings.unitCount");
		view.getSelector().add("buildings.codingType");
		view.getSelector().add("buildings.buildingHeight");
		view.getSelector().add("buildings.joinInDate");
		view.getSelector().add("buildings.completeDate");
		view.getSelector().add("buildings.floorCount");
		view.getSelector().add("buildings.buildingTerraArea");
		view.getSelector().add("buildings.conditionType");
		view.getSelector().add("buildings.conditionStandard");
		view.getSelector().add("buildings.administrativeNumber");
		view.getSelector().add("buildings.sellProject.id");
		view.getSelector().add("buildings.buildingProperty.id");
		view.getSelector().add("buildings.constructPart.id");
		view.getSelector().add("buildings.buildingStructure.id");
		view.getSelector().add("buildings.productType.id");
		view.getSelector().add("buildings.subarea.id");
		view.getSelector().add("orgUnitShareList.orgUnit.id"); // �����������֯��������Ŀ

		if (type.equals("subarea") || type.equals("building") || type.equals("unit")) {
			view.getSelector().add("subarea.*");
			if (type.equals("building") || type.equals("unit")) {
				view.getSelector().add("buildings.subarea.*");
				if (type.equals("unit"))
					view.getSelector().add("buildings.units.*"); // ��Ԫ��Ϊ�˷�¼��ʽ��
																	// ���Ե�Ԫ��ʱҪ�����
			}
		}

		List allSellProList = new ArrayList();
		SellProjectCollection sellProjects = SellProjectFactory.getRemoteInstance().getSellProjectCollection(view);
		for (int i = 0; i < sellProjects.size(); i++) {
			allSellProList.add(sellProjects.get(i));
		}

		for (int i = 0; i < sellProjects.size(); i++) { // �����������֯�����
			SellProjectInfo sellProjectInfo = (SellProjectInfo) sellProjects.get(i);
			if (sellProjectInfo.getOrgUnitShareList().size() > 0) {
				for (int j = 0; j < sellProjectInfo.getOrgUnitShareList().size(); j++) {
					SellProjectInfo addNewSellProInfo = (SellProjectInfo) sellProjectInfo.clone();
					addNewSellProInfo.setOrgUnit(sellProjectInfo.getOrgUnitShareList().get(j).getOrgUnit());
					allSellProList.add(addNewSellProInfo);
				}
			}
		}

		for (int i = 0; i < allSellProList.size(); i++) {
			SellProjectInfo sellProjectInfo = (SellProjectInfo) allSellProList.get(i);
			KDTreeNode projectNode = new KDTreeNode(sellProjectInfo.getName());
			projectNode.setUserObject(sellProjectInfo);
			projectNode.setCustomIcon(EASResource.getIcon("imgTbtn_assistantaccount"));

			if (type.equals("subarea") || type.equals("building") || type.equals("unit")) {
				SubareaCollection subareas = sellProjectInfo.getSubarea();
				CRMHelper.sortCollection(subareas, "number",true);
				BuildingCollection buildings = sellProjectInfo.getBuildings();
				CRMHelper.sortCollection(buildings, "number",true);

				if (type.equals("building") || type.equals("unit")) { // ������Ŀ�¿���ֱ����¥��
					for (int k = 0; k < buildings.size(); k++) {
						BuildingInfo building = buildings.get(k);
						SubareaInfo buildingSubArea = building.getSubarea();
						if (buildingSubArea == null) { // ��§���ϲ����ڷ�����ʱ��ֱ�����Ӹ�§��
							KDTreeNode buildingNode = new KDTreeNode(building.getName());
							buildingNode.setCustomIcon(EASResource.getIcon("imgTbtn_copytotier"));
							buildingNode.setUserObject(building);
							if (type.equals("unit"))
								addBuildingUnit(building, buildingNode);
							projectNode.add(buildingNode);
						}
					}
				}

				for (int j = 0; j < subareas.size(); j++) { // ��ӷ����ڵ�
					SubareaInfo subarea = subareas.get(j);
					KDTreeNode subareaNode = new KDTreeNode(subarea.getName());
					subareaNode.setUserObject(subarea);
					subareaNode.setCustomIcon(EASResource.getIcon("imgTbtn_showparent"));
					if (type.equals("building") || type.equals("unit")) {
						for (int k = 0; k < buildings.size(); k++) {
							BuildingInfo building = buildings.get(k);
							SubareaInfo buildingSubArea = building.getSubarea();
							if (buildingSubArea != null) {
								String buildingAreaId = buildingSubArea.getId().toString();
								String subAreaId = subarea.getId().toString();
								if (buildingAreaId.equals(subAreaId)) { // ���§���ڵ�
									KDTreeNode buildingNode = new KDTreeNode(building.getName());
									buildingNode.setUserObject(building);
									buildingNode.setCustomIcon(EASResource.getIcon("imgTbtn_copytotier"));

									if (type.equals("unit")) { // ��ӵ�Ԫ�ڵ�
										addBuildingUnit(building, buildingNode);
									}
									subareaNode.add(buildingNode);
								}
							}
						}
					}
					projectNode.add(subareaNode);
				}
			}

			DefaultMutableTreeNode orgTreeNode = (DefaultMutableTreeNode) orgIdMap.get(sellProjectInfo.getOrgUnit().getId().toString());
			if (orgTreeNode != null)
				orgTreeNode.add(projectNode);
		}
	
	}

	/**
	 * (��ָ�����ڵ��е�Ҷ�ڵ� �� �����ӽ�) Ϊ�����������֯����������¥������Ԫ���ڵ�
	 * 
	 * @param node
	 *            ��֯�ṹ�ڵ� ������Ϊ�����������֯
	 * @param type
	 *            �� ����subarea §��building ��Ԫunit
	 * @param sysTypeParam
	 *            ӵ����¥���Եġ� ӵ���������Եġ�ӵ����ҵ���Եģ�Ϊ�ջ�����ʱ����ȫ��
	 * @throws BOSException
	 * @throws SQLException
	 */
	private static void fillTreeNode(TreeModel tree, String type, MoneySysTypeEnum sysTypeParam) throws BOSException, SQLException {

		DefaultMutableTreeNode rootNode = (DefaultMutableTreeNode) tree.getRoot();
		Map orgIdMap = FDCTreeHelper.getAllObjectIdMap(rootNode, "OrgStructure");
		if (orgIdMap == null || orgIdMap.size() == 0)
			return;

		EntityViewInfo view = new EntityViewInfo();
		view.getSorter().add(new SorterItemInfo("number"));
		FilterInfo filter = new FilterInfo();
		try {
			String proIdSql = CommerceHelper.getPermitProjectIdSql(SysContext.getSysContext().getCurrentUserInfo());
			filter.getFilterItems().add(new FilterItemInfo("id", proIdSql, CompareType.INNER));
		} catch (EASBizException e) {
			e.printStackTrace();
			SysUtil.abort();
		}
		if (sysTypeParam != null) {
			if (sysTypeParam.equals(MoneySysTypeEnum.SalehouseSys)) {
				filter.getFilterItems().add(new FilterItemInfo("isForSHE", Boolean.TRUE));
			} else if (sysTypeParam.equals(MoneySysTypeEnum.TenancySys)) {
				filter.getFilterItems().add(new FilterItemInfo("isForTen", Boolean.TRUE));
			} else if (sysTypeParam.equals(MoneySysTypeEnum.ManageSys)) {
				filter.getFilterItems().add(new FilterItemInfo("isForPPM", Boolean.TRUE));
			}
		}
		view.setFilter(filter);
		view.getSelector().add("*");
		view.getSelector().add("buildings.id");
		view.getSelector().add("buildings.name");
		view.getSelector().add("buildings.number");
		view.getSelector().add("buildings.unitCount");
		view.getSelector().add("buildings.codingType");
		view.getSelector().add("buildings.buildingHeight");
		view.getSelector().add("buildings.joinInDate");
		view.getSelector().add("buildings.completeDate");
		view.getSelector().add("buildings.floorCount");
		view.getSelector().add("buildings.buildingTerraArea");
		view.getSelector().add("buildings.conditionType");
		view.getSelector().add("buildings.conditionStandard");
		view.getSelector().add("buildings.administrativeNumber");
		view.getSelector().add("buildings.sellProject.id");
		view.getSelector().add("buildings.buildingProperty.id");
		view.getSelector().add("buildings.constructPart.id");
		view.getSelector().add("buildings.buildingStructure.id");
		view.getSelector().add("buildings.productType.id");
		view.getSelector().add("buildings.subarea.id");
		view.getSelector().add("orgUnitShareList.orgUnit.id"); // �����������֯��������Ŀ

		if (type.equals("subarea") || type.equals("building") || type.equals("unit")) {
			view.getSelector().add("subarea.*");
			if (type.equals("building") || type.equals("unit")) {
				view.getSelector().add("buildings.subarea.*");
				if (type.equals("unit"))
					view.getSelector().add("buildings.units.*"); // ��Ԫ��Ϊ�˷�¼��ʽ��
																	// ���Ե�Ԫ��ʱҪ�����
			}
		}

		List allSellProList = new ArrayList();
		SellProjectCollection sellProjects = SellProjectFactory.getRemoteInstance().getSellProjectCollection(view);
		for (int i = 0; i < sellProjects.size(); i++) {
			allSellProList.add(sellProjects.get(i));
		}

		for (int i = 0; i < sellProjects.size(); i++) { // �����������֯�����
			SellProjectInfo sellProjectInfo = (SellProjectInfo) sellProjects.get(i);
			if (sellProjectInfo.getOrgUnitShareList().size() > 0) {
				for (int j = 0; j < sellProjectInfo.getOrgUnitShareList().size(); j++) {
					SellProjectInfo addNewSellProInfo = (SellProjectInfo) sellProjectInfo.clone();
					addNewSellProInfo.setOrgUnit(sellProjectInfo.getOrgUnitShareList().get(j).getOrgUnit());
					allSellProList.add(addNewSellProInfo);
				}
			}
		}

		for (int i = 0; i < allSellProList.size(); i++) {
			SellProjectInfo sellProjectInfo = (SellProjectInfo) allSellProList.get(i);
			KDTreeNode projectNode = new KDTreeNode(sellProjectInfo.getName());
			projectNode.setUserObject(sellProjectInfo);
			projectNode.setCustomIcon(EASResource.getIcon("imgTbtn_assistantaccount"));

			if (type.equals("subarea") || type.equals("building") || type.equals("unit")) {
				SubareaCollection subareas = sellProjectInfo.getSubarea();
				CRMHelper.sortCollection(subareas, "number",true);
				BuildingCollection buildings = sellProjectInfo.getBuildings();
				CRMHelper.sortCollection(buildings, "number",true);

				if (type.equals("building") || type.equals("unit")) { // ������Ŀ�¿���ֱ����¥��
					for (int k = 0; k < buildings.size(); k++) {
						BuildingInfo building = buildings.get(k);
						SubareaInfo buildingSubArea = building.getSubarea();
						if (buildingSubArea == null) { // ��§���ϲ����ڷ�����ʱ��ֱ�����Ӹ�§��
							KDTreeNode buildingNode = new KDTreeNode(building.getName());
							buildingNode.setCustomIcon(EASResource.getIcon("imgTbtn_copytotier"));
							buildingNode.setUserObject(building);
							if (type.equals("unit"))
								addBuildingUnit(building, buildingNode);
							projectNode.add(buildingNode);
						}
					}
				}

				for (int j = 0; j < subareas.size(); j++) { // ��ӷ����ڵ�
					SubareaInfo subarea = subareas.get(j);
					KDTreeNode subareaNode = new KDTreeNode(subarea.getName());
					subareaNode.setUserObject(subarea);
					subareaNode.setCustomIcon(EASResource.getIcon("imgTbtn_showparent"));
					if (type.equals("building") || type.equals("unit")) {
						for (int k = 0; k < buildings.size(); k++) {
							BuildingInfo building = buildings.get(k);
							SubareaInfo buildingSubArea = building.getSubarea();
							if (buildingSubArea != null) {
								String buildingAreaId = buildingSubArea.getId().toString();
								String subAreaId = subarea.getId().toString();
								if (buildingAreaId.equals(subAreaId)) { // ���§���ڵ�
									KDTreeNode buildingNode = new KDTreeNode(building.getName());
									buildingNode.setUserObject(building);
									buildingNode.setCustomIcon(EASResource.getIcon("imgTbtn_copytotier"));

									if (type.equals("unit")) { // ��ӵ�Ԫ�ڵ�
										addBuildingUnit(building, buildingNode);
									}
									subareaNode.add(buildingNode);
								}
							}
						}
					}
					projectNode.add(subareaNode);
				}
			}

			DefaultMutableTreeNode orgTreeNode = (DefaultMutableTreeNode) orgIdMap.get(sellProjectInfo.getOrgUnit().getId().toString());
			if (orgTreeNode != null)
				orgTreeNode.add(projectNode);
		}
	}

	private static void addBuildingUnit(BuildingInfo building, DefaultKingdeeTreeNode buildingNode) {
		if (building.getCodingType().equals(CodingTypeEnum.UnitFloorNum)) {
			if (building.getUnits().size() != building.getUnitCount()) {
				// MsgBox.showInfo("¥��"+building.getName()+",����"+building.
				// getNumber()+"�ĵ�Ԫ���ͷ�¼�еĲ��ȣ����飡");
				// ���������������ȵ�Ԫ�����ȶ������ɾ��
				try {
					building = dealForTheOldUnitDate(building);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

			// ��Ԫ��¼��seq����
			if (building.getUnitCount() > 1) {
				CRMHelper.sortCollection(building.getUnits(), "seq",true);
			}

			// ���뵥Ԫ
			for (int index = 0; index < building.getUnitCount(); index++) {
				BuildingUnitInfo unitInfo = building.getUnits().get(index);
				KDTreeNode unitNode = new KDTreeNode(unitInfo.getName());
				unitNode.setCustomIcon(EASResource.getIcon("imgTree_folder_leaf"));
				unitNode.setUserObject(unitInfo);
				buildingNode.add(unitNode);
			}
		}
	}

	// ���������������ȵ�Ԫ�����ȶ������ɾ��
	private static BuildingInfo dealForTheOldUnitDate(BuildingInfo building) throws Exception {
		if (building.getUnitCount() > 0 && building.getUnits().size() == 0) {
			CoreBaseCollection coreColl = new CoreBaseCollection();
			for (int i = 1; i <= building.getUnitCount(); i++) {
				BuildingUnitInfo unitInfo = new BuildingUnitInfo();
				unitInfo.setSeq(i);
				unitInfo.setNumber(String.valueOf(100 + i));
				unitInfo.setName(i + "��Ԫ");
				unitInfo.setDescription("�����������Զ�������ɵ�");
				unitInfo.setBuilding(building);
				coreColl.add(unitInfo);
			}
			BuildingUnitFactory.getRemoteInstance().addnew(coreColl);

			building = BuildingFactory.getRemoteInstance().getBuildingInfo("select *,units.* where id='" + building.getId().toString() + "'");
			for (int i = 0; i < building.getUnits().size(); i++) {
				BuildingUnitInfo unitInfo = building.getUnits().get(i);

				FDCSQLBuilder builder = new FDCSQLBuilder();
				builder.appendSql("update T_SHE_Room set FBuildUnitID = '" + unitInfo.getId().toString() + "' where FBuildingID ='" + building.getId().toString() + "' and FUnit=" + unitInfo.getSeq());
				builder.execute();
			}

			CacheServiceFactory.getInstance().discardType(new BuildingUnitInfo().getBOSType());
		}
		return building;
	}

	/**
	 * ����������֯�� --
	 * 
	 * @param actionOnLoad
	 * @return
	 * @throws Exception
	 */
	public static TreeModel getSaleOrgTree(ItemAction actionOnLoad) throws Exception {
		return FDCTreeHelper.getSaleOrgTree(actionOnLoad);
	}

	/**
	 * @deprecated ����������Ŀ�� ���ͽṹ ������֯ - ������Ŀ
	 * @param actionOnLoad
	 * @return
	 * @throws Exception
	 */
	public static TreeModel getSellProjectTree(ItemAction actionOnLoad) throws Exception {
		return FDCTreeHelper.getSellProjectTreeForSHE(actionOnLoad, null);
	}

	/**
	 * @param sysTypeParam
	 *            ӵ����¥���Եġ�ӵ���������Եġ�ӵ����ҵ���Եģ�
	 */
	public static TreeModel getSellProjectTree(ItemAction actionOnLoad, MoneySysTypeEnum sysTypeParam) throws Exception {
		return FDCTreeHelper.getSellProjectTreeForSHE(actionOnLoad,MoneySysTypeEnum.SalehouseSys);
	}

	/**
	 * @deprecated �������̵��̴��� ���ͽṹ ������֯ - ������Ŀ - �̴�
	 * @param actionOnLoad
	 * @return
	 * @throws Exception
	 */
	public static TreeModel getSellOrderTree(ItemAction actionOnLoad) throws Exception {
		return FDCTreeHelper.getSaleOrgTree(actionOnLoad);
	}

	public static TreeModel getSellOrderTree(ItemAction actionOnLoad, MoneySysTypeEnum sysTypeParam) throws Exception {
		return FDCTreeHelper.getSellOrderTree(actionOnLoad, sysTypeParam);
	}

	/**
	 * @deprecated ���������� ���ͽṹ ������֯ - ������Ŀ - ����
	 * @param actionOnLoad
	 * @return
	 * @throws Exception
	 */
	public static TreeModel getSubareaTree(ItemAction actionOnLoad) throws Exception {
		return FDCTreeHelper.getSubareaTree(actionOnLoad, null);
	}

	public static TreeModel getSubareaTree(ItemAction actionOnLoad, MoneySysTypeEnum sysTypeParam) throws Exception {
		return FDCTreeHelper.getSubareaTree(actionOnLoad, sysTypeParam);
	}

	/**
	 * @deprecated ����¥���� ���ͽṹ ������֯ - ������Ŀ - ���� - ¥��
	 * @param actionOnLoad
	 * @return
	 * @throws Exception
	 */
	public static TreeModel getBuildingTree(ItemAction actionOnLoad) throws Exception {
		return FDCTreeHelper.getBuildingTreeForSHE(actionOnLoad, null);
	}

	/**
	 * ����¥���� ���ͽṹ ������֯ - ������Ŀ - ���� - ¥��
	 * 
	 * @param actionOnLoad
	 * @return
	 * @throws Exception
	 */
	public static TreeModel getBuildingTree(ItemAction actionOnLoad, MoneySysTypeEnum sysTypeParam) throws Exception {
		return FDCTreeHelper.getBuildingTreeForSHE(actionOnLoad, sysTypeParam);
	}

	
	public static TreeModel getBuildingTree(ItemAction actionOnLoad, MoneySysTypeEnum sysTypeParam, boolean showShare) throws Exception
	{
		return FDCTreeHelper.getBuildingTreeForSHE(actionOnLoad, sysTypeParam);
	}
	/**
	 * @deprecated ������Ԫ�� ���ͽṹ ������֯ - ������Ŀ - ���� - ¥�� - ��Ԫ
	 * @param actionOnLoad
	 * @return
	 * @throws Exception
	 */
	public static TreeModel getUnitTree(ItemAction actionOnLoad) throws Exception {
		return FDCTreeHelper.getUnitTreeForSHE(actionOnLoad, null);
	}

	public static TreeModel getUnitTree(ItemAction actionOnLoad, MoneySysTypeEnum sysTypeParam) throws Exception {
		return FDCTreeHelper.getUnitTreeForSHE(actionOnLoad, sysTypeParam);
	}
	
	//Add by zhiyuan_tang 2010/08/28
	/**
	 * ���������� ���ͽṹ ������֯ - ������Ŀ - ���� - ¥�� - ��Ԫ - ����
	 * @param actionOnLoad
	 * @return
	 * @throws Exception
	 */
	public static TreeModel getRoomTree(ItemAction actionOnLoad) throws Exception {
		return FDCTreeHelper.getRoomTree(actionOnLoad, null);
	}

	/**
	 * ���������� ���ͽṹ ������֯ - ������Ŀ - ���� - ¥�� - ��Ԫ - ����
	 * @param actionOnLoad
	 * @param sysTypeParam   ӵ����¥���Եġ�ӵ���������Եġ�ӵ����ҵ���Եģ� ��Ϊ��  ����ȫ������
	 * @return				 ������
	 * @throws Exception
	 */
	public static TreeModel getRoomTree(ItemAction actionOnLoad, MoneySysTypeEnum sysTypeParam) throws Exception {
		return FDCTreeHelper.getRoomTree(actionOnLoad, sysTypeParam);
	}
	
	public static TreeModel getUnitTree(ItemAction actionOnLoad, MoneySysTypeEnum sysTypeParam,String [] sellProjectId) throws Exception {
		//TreeModel tree = getTree("unit", actionOnLoad, sysTypeParam,sellProjectId);  //��Ŀ����
		TreeModel tree = FDCTreeHelper.getUnitTree(actionOnLoad, sysTypeParam);
		String sellProIds = "";
		if(sellProjectId != null && sellProjectId.length>0 ) {
			for(int i=0;i<sellProjectId.length;i++) sellProIds += ","+sellProjectId[i];
		}
		
		Map sellProIdMap = FDCTreeHelper.getAllObjectIdMap((DefaultMutableTreeNode)tree.getRoot(),"SellProject");
		Iterator iter = sellProIdMap.keySet().iterator();
		while(iter.hasNext()) {
			DefaultKingdeeTreeNode thisNode = (DefaultKingdeeTreeNode)sellProIdMap.get(iter.next());
			SellProjectInfo sellProInfo = (SellProjectInfo)thisNode.getUserObject();
			if(sellProInfo!=null) {
				if(sellProIds.indexOf(sellProInfo.getId().toString())<0)
					thisNode.removeFromParent();
			}
		}
		
		return tree;
	}

	public static TreeModel getUnitTree(ItemAction actionOnLoad, MoneySysTypeEnum sysTypeParam, boolean showShare) throws Exception
	{
		TreeModel tree = FDCTreeHelper.getUnitTree(actionOnLoad, sysTypeParam, showShare);
		return tree;
	}
	/**
	 * @deprecated ����ƽ��ͼ�� ���ͽṹ ������֯ - (��Ŀͼ) - ������Ŀ - (¥���ֲ�ͼ) - ���� - ¥�� -
	 *             (¥��ƽ��ͼ,���ֵ�Ԫ) - ��Ԫ - (¥��ƽ��ͼ,���ֵ�Ԫ)
	 * @param actionOnLoad
	 * @return
	 * @throws Exception
	 */
	public static TreeModel getPlanisphere(ItemAction actionOnLoad) throws Exception {
		return getPlanisphere(actionOnLoad, null);
	}

	public static TreeModel getPlanisphere(ItemAction actionOnLoad, MoneySysTypeEnum sysTypeParam) throws Exception {
		TreeModel tree = getUnitTree(actionOnLoad, sysTypeParam);
		DefaultMutableTreeNode treeNode = (DefaultMutableTreeNode) tree.getRoot();

		Map orgIdMap = FDCTreeHelper.getAllObjectIdMap(treeNode, "OrgStructure");
		Map sellProIdMap = FDCTreeHelper.getAllObjectIdMap(treeNode, "SellProject");
		Map buildingIdMap = FDCTreeHelper.getAllObjectIdMap(treeNode, "Building");
		Map buildingUnitIdMap = FDCTreeHelper.getAllObjectIdMap(treeNode, "BuildingUnit");
		PlanisphereCollection phColl = PlanisphereFactory.getRemoteInstance().getPlanisphereCollection(
				" where orgUnit.id in ("+FDCTreeHelper.getStringFromSet(orgIdMap.keySet())+") " +
						"and (sellProject.id is null or sellProject.id in ("+FDCTreeHelper.getStringFromSet(sellProIdMap.keySet())+")) ");
		for(int i=0;i<phColl.size();i++) {
			PlanisphereInfo phInfo = phColl.get(i);
			if(phInfo.getPtype()==null) continue; 
			if(phInfo.getPtype().equals(PlanisphereTypeEnum.PicSellProject)) {
				if(phInfo.getSellProject()==null) {
					DefaultKingdeeTreeNode orgNode = (DefaultKingdeeTreeNode)orgIdMap.get(phInfo.getOrgUnit().getId().toString());
					if(orgNode!=null) 	addPlanisphereNode(orgNode,phInfo);
				}				
			}else if(phInfo.getPtype().equals(PlanisphereTypeEnum.PicBuildDist)){
				if(phInfo.getSellProject()!=null && phInfo.getBuilding()==null){
					DefaultKingdeeTreeNode sellProNode = (DefaultKingdeeTreeNode)sellProIdMap.get(phInfo.getSellProject().getId().toString());
					if(sellProNode!=null) 	addPlanisphereNode(sellProNode,phInfo);
				}
			}else if(phInfo.getPtype().equals(PlanisphereTypeEnum.PicBuildPlane)){
				if(phInfo.getBuilding()!=null) {						
						if(phInfo.isIsShowUnit()){
							Iterator iter = buildingUnitIdMap.keySet().iterator();
							while(iter.hasNext()) {
								DefaultKingdeeTreeNode unitNode = (DefaultKingdeeTreeNode)buildingUnitIdMap.get(iter.next());
								if(unitNode!=null) {
									BuildingUnitInfo unitInfo = (BuildingUnitInfo)unitNode.getUserObject();
									if(unitInfo.getBuilding().getId().toString().equals(phInfo.getBuilding().getId().toString()) && unitInfo.getSeq()==phInfo.getUnit()) {
										addPlanisphereNode(unitNode,phInfo);	
									}
								}
							}							
						}else{
							DefaultKingdeeTreeNode buildNode = (DefaultKingdeeTreeNode)buildingIdMap.get(phInfo.getBuilding().getId().toString());
							if(buildNode!=null) {
								BuildingInfo buildInfo = (BuildingInfo)buildNode.getUserObject();
								if(buildInfo.getUnitCount()==0 || (buildInfo.getUnitCount()>0 && !phInfo.isIsShowUnit())){
									addPlanisphereNode(buildNode,phInfo);
								}
							}
						}
					
				}
			}
		}
		return tree;
	}

	private static void addPlanisphereNode(DefaultMutableTreeNode parentNode, PlanisphereInfo phInfo) {
		KDTreeNode membNode = new KDTreeNode(phInfo.getName());
		membNode.setCustomIcon(EASResource.getIcon("imgTbtn_showpicture"));
		membNode.setUserObject(phInfo);
		parentNode.insert(membNode, 0);
	}
	
	
	/**
	 * @description  �����������Ӷ���¥ѡ���������ж�����ɾ��ѡ����ʾ�ڵ�
	 * @author tim_gao
	 * @param actionOnLoad
	 * @param sysTypeParam
	 * @return
	 * @throws Exception
	 */
	public static TreeModel getPlanisphereForChooseRoomParam(ItemAction actionOnLoad, MoneySysTypeEnum sysTypeParam,RoomDisplaySetting set) throws Exception {
		TreeModel tree = FDCTreeHelper.getUnitTreeForSHE(actionOnLoad, sysTypeParam);
//		TreeModel tree = FDCTreeHelper.getBuildingTreeForSHE(actionOnLoad, sysTypeParam);
		DefaultMutableTreeNode treeNode = (DefaultMutableTreeNode) tree.getRoot();
		//�������֯�����޻���˵! by tim_gao
//		FDCTreeHelper.set=set;
		Map orgIdMap = FDCTreeHelper.getAllObjectIdMap(treeNode, "OrgStructure");
		Map sellProIdMap = FDCTreeHelper.getAllObjectIdMap(treeNode, "SellProject");
		Map buildingIdMap = FDCTreeHelper.getAllObjectIdMap(treeNode, "Building");
		Map buildingUnitIdMap = FDCTreeHelper.getAllObjectIdMap(treeNode, "BuildingUnit");
		PlanisphereCollection phColl = PlanisphereFactory.getRemoteInstance().getPlanisphereCollection(
				" where orgUnit.id in ("+FDCTreeHelper.getStringFromSet(orgIdMap.keySet())+") " +
						"and (sellProject.id is null or sellProject.id in ("+FDCTreeHelper.getStringFromSet(sellProIdMap.keySet())+")) ");
		for(int i=0;i<phColl.size();i++) {
			PlanisphereInfo phInfo = phColl.get(i);
			if(phInfo.getPtype()==null) continue; 
			if(phInfo.getPtype().equals(PlanisphereTypeEnum.PicSellProject)) {
				if(phInfo.getSellProject()==null) {
					DefaultKingdeeTreeNode orgNode = (DefaultKingdeeTreeNode)orgIdMap.get(phInfo.getOrgUnit().getId().toString());
					if(orgNode!=null) 	addPlanisphereNode(orgNode,phInfo);
				}				
			}else if(phInfo.getPtype().equals(PlanisphereTypeEnum.PicBuildDist)){
				if(phInfo.getSellProject()!=null && phInfo.getBuilding()==null){
					DefaultKingdeeTreeNode sellProNode = (DefaultKingdeeTreeNode)sellProIdMap.get(phInfo.getSellProject().getId().toString());
					if(sellProNode!=null) 	addPlanisphereNode(sellProNode,phInfo);
				}
			}else if(phInfo.getPtype().equals(PlanisphereTypeEnum.PicBuildPlane)){
				if(phInfo.getBuilding()!=null) {						
						if(phInfo.isIsShowUnit()){
							Iterator iter = buildingUnitIdMap.keySet().iterator();
							while(iter.hasNext()) {
								DefaultKingdeeTreeNode unitNode = (DefaultKingdeeTreeNode)buildingUnitIdMap.get(iter.next());
								if(unitNode!=null) {
									BuildingUnitInfo unitInfo = (BuildingUnitInfo)unitNode.getUserObject();
									if(unitInfo.getBuilding().getId().toString().equals(phInfo.getBuilding().getId().toString()) && unitInfo.getSeq()==phInfo.getUnit()) {
										addPlanisphereNode(unitNode,phInfo);	
									}
								}
							}							
						}else{
							DefaultKingdeeTreeNode buildNode = (DefaultKingdeeTreeNode)buildingIdMap.get(phInfo.getBuilding().getId().toString());
							if(buildNode!=null) {
								BuildingInfo buildInfo = (BuildingInfo)buildNode.getUserObject();
								if(buildInfo.getUnitCount()==0 || (buildInfo.getUnitCount()>0 && !phInfo.isIsShowUnit())){
									addPlanisphereNode(buildNode,phInfo);
								}
							}
						}
					
				}
			}
		}
		//�л�ʱ���ͷ�  by tim_gao 2011-07-09
//		FDCTreeHelper.set =null;
		return tree;
	}
	/**
	 * ��������ȡ¥���������޿��ƽ���ʹ�ã�������¥����ܶ࣬����ֻ��ʾ������¥��Ч��ͼ��ƽ��ͼ��¥��ڵ㡣
	 * @param actionOnLoad
	 * @param sysTypeParam
	 * @return
	 * @throws Exception
	 */
	public static TreeModel getFloorTree(ItemAction actionOnLoad, MoneySysTypeEnum sysTypeParam) throws Exception {
		//�õ���Ԫ��
		TreeModel tree = getUnitTree(actionOnLoad, sysTypeParam);
		DefaultMutableTreeNode treeNode = (DefaultMutableTreeNode) tree.getRoot();

		Map orgIdMap = FDCTreeHelper.getAllObjectIdMap(treeNode, "OrgStructure");
		Map sellProIdMap = FDCTreeHelper.getAllObjectIdMap(treeNode, "SellProject");
		Map buildingIdMap = FDCTreeHelper.getAllObjectIdMap(treeNode, "Building");
		Map buildingUnitIdMap = FDCTreeHelper.getAllObjectIdMap(treeNode, "BuildingUnit");
		//ʹ��SortedMap��¥��ڵ��ǰ�¥������ġ�
		SortedMap floorMap = new TreeMap();
		Map orgUnitMap = new HashMap();
		Map sellProjectMap = new HashMap();
		EffectImageAndPlanisphereInfo info = null;
		
		//��ȡƽ��ʾ��ͼ�ڵ�
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		HashSet orgIdSet = new HashSet(orgIdMap.keySet());
		filter.getFilterItems().add(new FilterItemInfo("orgUnit.id", orgIdSet, CompareType.INCLUDE));
		filter.getFilterItems().add(new FilterItemInfo("sellProject.id", null));
		HashSet projectIdSet = new HashSet(sellProIdMap.keySet());
		filter.getFilterItems().add(new FilterItemInfo("sellProject.id", projectIdSet, CompareType.INCLUDE));
		filter.setMaskString("#0 and ( #1 or #2 ) ");
		view.setFilter(filter);
		SelectorItemCollection sic = new SelectorItemCollection();
		sic.add("*");
		sic.add("orgUnit.*");
		sic.add("sellProject.*");
		sic.add("building.*");
		view.setSelector(sic);
		PlanisphereCollection phColl = PlanisphereFactory.getRemoteInstance().getPlanisphereCollection(view);
		for(int i=0;i<phColl.size();i++) {
			PlanisphereInfo phInfo = phColl.get(i);
			if(phInfo.getPtype() == null) continue; 
			if(phInfo.getPtype().equals(PlanisphereTypeEnum.PicSellProject)) {
				//����Ŀ��λͼ���͵Ľڵ㱣����orgUnitMap��
				if(phInfo.getSellProject()==null) {
					info = new EffectImageAndPlanisphereInfo();
					info.setName(phInfo.getOrgUnit().getName());
					info.setOrgUnit(phInfo.getOrgUnit());
					info.setPlanisphere(phInfo);
					orgUnitMap.put(phInfo.getOrgUnit().getId().toString(), info);
				}
				
			} else if (phInfo.getPtype().equals(PlanisphereTypeEnum.PicBuildDist)) {
				//¥���ֲ�ͼ���͵Ľڵ㱣����sellProjectMap��
				if(phInfo.getSellProject() != null && phInfo.getBuilding() == null){
					info = new EffectImageAndPlanisphereInfo();
					info.setName(phInfo.getSellProject().getName());
					info.setOrgUnit(phInfo.getOrgUnit());
					info.setSellProject(phInfo.getSellProject());
					info.setPlanisphere(phInfo);
					sellProjectMap.put(phInfo.getSellProject().getId().toString(), info);
				}
				
			} else if (phInfo.getPtype().equals(PlanisphereTypeEnum.PicBuildPlane)) {
				
				//¥��ƽ��ͼ���͵Ľڵ㱣����floorMap��
				//¥��ڵ�Ĺ�����ʽ��֮ǰ��ͬ�ˣ�
				if(phInfo.getBuilding() != null) {
					info = new EffectImageAndPlanisphereInfo();
					info.setName(phInfo.getFloor() + "��");
					info.setOrgUnit(phInfo.getOrgUnit());
					info.setSellProject(phInfo.getSellProject());
					info.setBuilding(phInfo.getBuilding());
					info.setPlanisphere(phInfo);
					if(phInfo.isIsShowUnit()){
						floorMap.put(phInfo.getBuilding().getId().toString() + phInfo.getUnit() + phInfo.getFloor(), info);
					} else {
						floorMap.put(phInfo.getBuilding().getId().toString() + phInfo.getFloor(), info);
					}				
				}
			}
		}
		
		//��ȡЧ��ͼ�ڵ�
		sic = new SelectorItemCollection();
		sic.add("*");
		sic.add("orgUnit.*");
		sic.add("sellProject.*");
		sic.add("building.*");
		sic.add("buildingUnit.*");
		sic.add("buildingFloor.*");
		sic.add("elementEntry.*");
		view.setSelector(sic);
		EffectImageCollection effectColl = EffectImageFactory.getRemoteInstance().getEffectImageCollection(view);
		for(int i=0; i<effectColl.size(); i++) {
			EffectImageInfo effectInfo = effectColl.get(i);
			if(effectInfo.getType() == null) continue; 
			if(effectInfo.getType().equals(EffectImageEnum.PIC_SELLPROJECT)) {
				
				//����Ŀ��λͼ���͵Ľڵ㱣����orgUnitMap��
				if(effectInfo.getSellProject()==null) {
					//����Ѿ���ƽ��ʾ��ͼ�����д������ˣ���ֻ�����EffectImage���ɣ���������Ҫ����
					info = (EffectImageAndPlanisphereInfo) orgUnitMap.get(effectInfo.getOrgUnit().getId().toString());
					if (info == null) {
						info = new EffectImageAndPlanisphereInfo();
						info.setName(effectInfo.getOrgUnit().getName());
						info.setOrgUnit(effectInfo.getOrgUnit());
					}
					info.setEffectImage(effectInfo);
					orgUnitMap.put(effectInfo.getOrgUnit().getId().toString(), info);
				}
				
			} else if (effectInfo.getType().equals(EffectImageEnum.PIC_BUILDING)) {
				
				//¥���ֲ�ͼ���͵Ľڵ㱣����sellProjectMap��
				if(effectInfo.getSellProject() != null && effectInfo.getBuilding() == null){
					//����Ѿ���ƽ��ʾ��ͼ�����д������ˣ���ֻ�����EffectImage���ɣ���������Ҫ����
					info = (EffectImageAndPlanisphereInfo) sellProjectMap.get(effectInfo.getSellProject().getId().toString());
					if (info == null) {
						info = new EffectImageAndPlanisphereInfo();
						info.setName(effectInfo.getSellProject().getName());
						info.setOrgUnit(effectInfo.getOrgUnit());
						info.setSellProject(effectInfo.getSellProject());
					}
					info.setEffectImage(effectInfo);
					sellProjectMap.put(effectInfo.getSellProject().getId().toString(), info);
				}
				
			} else if (effectInfo.getType().equals(EffectImageEnum.PIC_BUILDINGFLOOR)) {
				
				//¥��ƽ��ͼ���͵Ľڵ㱣����floorMap��
				if(effectInfo.getBuilding() != null) {
					//����Ѿ���ƽ��ʾ��ͼ�����д������ˣ���ֻ�����EffectImage���ɣ���������Ҫ����
					if (effectInfo.isIsShowUnit()) {
						info = (EffectImageAndPlanisphereInfo) floorMap.get(effectInfo.getBuilding().getId().toString() + effectInfo.getBuildingUnit().getSeq() + effectInfo.getBuildingFloor().getFloor());
					} else {
						info = (EffectImageAndPlanisphereInfo) floorMap.get(effectInfo.getBuilding().getId().toString() + effectInfo.getBuildingFloor().getFloor());
					}
					if (info == null) {
						info = new EffectImageAndPlanisphereInfo();
						info.setName(effectInfo.getBuildingFloor().getFloor() + "��");
						info.setOrgUnit(effectInfo.getOrgUnit());
						info.setSellProject(effectInfo.getSellProject());
						info.setBuilding(effectInfo.getBuilding());
					}
					info.setEffectImage(effectInfo);
					if (effectInfo.isIsShowUnit()) {
						floorMap.put(effectInfo.getBuilding().getId().toString() + effectInfo.getBuildingUnit().getSeq() + effectInfo.getBuildingFloor().getFloor(), info);
					} else {
						floorMap.put(effectInfo.getBuilding().getId().toString() + effectInfo.getBuildingFloor().getFloor(), info);
					}
				}
			}
		}
		
		//���������ȡ�ĸ���ƽ��ʾ��ͼ��Ч��ͼ�����ݣ��������ڵ���ӵ�����ȥ
		//���ӽڵ�
		EffectImageInfo effectImage = null;
		PlanisphereInfo plainsphere = null;
		//��֯�ڵ�
		if (!orgUnitMap.isEmpty()) {
			for (Iterator it = orgUnitMap.keySet().iterator(); it.hasNext();) {
				
				info = (EffectImageAndPlanisphereInfo) orgUnitMap.get((String)it.next());
				if (info.getSellProject() == null) {
					DefaultKingdeeTreeNode orgNode = (DefaultKingdeeTreeNode)orgIdMap.get(info.getOrgUnit().getId().toString());
					if(orgNode != null) {
						addFloorNode(orgNode, info);
					}
//					if (info.getPlanisphereInfo() != null) {
//						
//						plainsphere = info.getPlanisphereInfo();
//						if(plainsphere.getSellProject() == null) {
//							
//							DefaultKingdeeTreeNode orgNode = (DefaultKingdeeTreeNode)orgIdMap.get(plainsphere.getOrgUnit().getId().toString());
//							if(orgNode != null) {
//								addFloorNode(orgNode, info);
//							}
//						}
//						
//					} else if (info.getEffectImageInfo() != null) {
//						
//						effectImage = info.getEffectImageInfo();
//						if(effectImage.getSellProject() == null) {
//							
//							DefaultKingdeeTreeNode orgNode = (DefaultKingdeeTreeNode)orgIdMap.get(effectImage.getOrgUnit().getId().toString());
//							if(orgNode != null) {
//								addFloorNode(orgNode, info);
//							}
//						}
//					}
				}
			}
		}
		//��Ŀ�ڵ�
		if (!sellProjectMap.isEmpty()) {
			
			for (Iterator it = sellProjectMap.keySet().iterator(); it.hasNext();){
				
				info = (EffectImageAndPlanisphereInfo) sellProjectMap.get((String)it.next());
				if(info.getSellProject() != null && info.getBuilding() == null){
					
					DefaultKingdeeTreeNode sellProNode = (DefaultKingdeeTreeNode)sellProIdMap.get(info.getSellProject().getId().toString());
					if(sellProNode != null) {
						addFloorNode(sellProNode, info);
					}
//					if (info.getPlanisphereInfo() != null) {
//						
//						plainsphere = info.getPlanisphereInfo();
//						if(plainsphere.getSellProject() != null && plainsphere.getBuilding() == null){
//							
//							DefaultKingdeeTreeNode sellProNode = (DefaultKingdeeTreeNode)sellProIdMap.get(plainsphere.getSellProject().getId().toString());
//							if(sellProNode != null) {
//								addFloorNode(sellProNode, info);
//							}
//						}
//						
//					} else if (info.getEffectImageInfo() != null) {
//						
//						effectImage = info.getEffectImageInfo();
//						if(effectImage.getSellProject() != null && effectImage.getBuilding() == null){
//							
//							DefaultKingdeeTreeNode sellProNode = (DefaultKingdeeTreeNode)sellProIdMap.get(effectImage.getSellProject().getId().toString());
//							if(sellProNode != null) {
//								addFloorNode(sellProNode, info);
//							}
//						}
//					}
				}
			}
		}
		//¥��ڵ�
		if (!floorMap.isEmpty()) {
			for (Iterator it = floorMap.keySet().iterator(); it.hasNext();){
				info = (EffectImageAndPlanisphereInfo) floorMap.get((String)it.next());
				
				if(info.getBuilding() != null) {
					
					if (info.getPlanisphereInfo() != null) {
						
						plainsphere = info.getPlanisphereInfo();
						if(plainsphere.isIsShowUnit()){
							Iterator iter = buildingUnitIdMap.keySet().iterator();
							while(iter.hasNext()) {
								DefaultKingdeeTreeNode unitNode = (DefaultKingdeeTreeNode)buildingUnitIdMap.get(iter.next());
								if(unitNode!=null) {
									BuildingUnitInfo unitInfo = (BuildingUnitInfo)unitNode.getUserObject();
									if(unitInfo.getBuilding().getId().toString().equals(plainsphere.getBuilding().getId().toString()) && unitInfo.getSeq()==plainsphere.getUnit()) {
										addFloorNode(unitNode, info);	
									}
								}
							}							
						}else{
							DefaultKingdeeTreeNode buildNode = (DefaultKingdeeTreeNode)buildingIdMap.get(plainsphere.getBuilding().getId().toString());
							if(buildNode!=null) {
								BuildingInfo buildInfo = (BuildingInfo)buildNode.getUserObject();
								if(buildInfo.getUnitCount()==0 || (buildInfo.getUnitCount()>0 && !plainsphere.isIsShowUnit())){
									addFloorNode(buildNode, info);
								}
							}
						}
						
					} else if (info.getEffectImageInfo() != null) {
						
						effectImage = info.getEffectImageInfo();
						if(effectImage.isIsShowUnit()){
							Iterator iter = buildingUnitIdMap.keySet().iterator();
							while(iter.hasNext()) {
								DefaultKingdeeTreeNode unitNode = (DefaultKingdeeTreeNode)buildingUnitIdMap.get(iter.next());
								if(unitNode!=null) {
									BuildingUnitInfo unitInfo = (BuildingUnitInfo)unitNode.getUserObject();
									if(unitInfo.getBuilding().getId().toString().equals(effectImage.getBuilding().getId().toString()) 
											&& unitInfo.getId().toString().equals(effectImage.getBuildingUnit().getId().toString())) {
										addFloorNode(unitNode, info);	
									}
								}
							}							
						}else{
							DefaultKingdeeTreeNode buildNode = (DefaultKingdeeTreeNode)buildingIdMap.get(effectImage.getBuilding().getId().toString());
							if(buildNode!=null) {
								BuildingInfo buildInfo = (BuildingInfo)buildNode.getUserObject();
								if(buildInfo.getUnitCount()==0 || (buildInfo.getUnitCount()>0 && !effectImage.isIsShowUnit())){
									addFloorNode(buildNode, info);
								}
							}
						}
						
					}
				}
			}
		}
		
		return tree;
	}
	
	
	private static void addFloorNode(DefaultMutableTreeNode parentNode, EffectImageAndPlanisphereInfo info) {
		KDTreeNode membNode = new KDTreeNode(info.getName());
		membNode.setCustomIcon(EASResource.getIcon("imgTbtn_showpicture"));
		membNode.setUserObject(info);
		parentNode.insert(membNode, 0);
	}


	/**
	 * ������ѡ�ڵ����ñ�ͷ�ﵥԪ������� colIndex ĳһ¥����Ӧ���к� buildingMinNum ¥��id�뷿�����С��ŵ�ӳ��
	 * buildingMaxNum ¥��id�뷿��������ŵ�ӳ�� ���ص�ǰ�ڵ���������������
	 */
	private static int setTableHeadByNode(KDTable table, DefaultMutableTreeNode node, int colIndex, Map buildingMinNum, Map buildingMaxNum) {
		int childRowIndex = colIndex;

		for (int i = 0; i < node.getChildCount(); i++) {
			DefaultKingdeeTreeNode child = (DefaultKingdeeTreeNode) node.getChildAt(i);
			childRowIndex += setTableHeadByNode(table, child, childRowIndex, buildingMinNum, buildingMaxNum);
		}

		int currHeadRowNum = -1;
		Object object = node.getUserObject();
		if (object instanceof Integer) // ���滻Ϊ��Ԫ���� -- ��ʱ����
			currHeadRowNum = getHeadRowNumByName(table, "��Ԫ");
		else if (object instanceof BuildingUnitInfo)
			currHeadRowNum = getHeadRowNumByName(table, "��Ԫ");
		else if (object instanceof BuildingInfo)
			currHeadRowNum = getHeadRowNumByName(table, "¥��");
		else if (object instanceof SubareaInfo)
			currHeadRowNum = getHeadRowNumByName(table, "����");
		else if (object instanceof SellProjectInfo)
			currHeadRowNum = getHeadRowNumByName(table, "��Ŀ");
		else if (object instanceof OrgStructureInfo) {
			// ��ǰ��֯��headCell�����ǡ���֯�أ�������֯������֯��...?
			String osName = "��֯";
			boolean haveUnit = false;
			if (getHeadRowNumByName(table, "��Ԫ") > 0)
				haveUnit = true;
			
			if (haveUnit) {
				if (node.getDepth() - 4 > 0)
					osName += (node.getDepth() - 4);
			} else {
				if (node.getDepth() - 3 > 0)
					osName += (node.getDepth() - 3);
			}
			currHeadRowNum = getHeadRowNumByName(table, osName);
		}

		if (node.isLeaf()) { // ���Ҷ�ڵ�Ϊ¥����Ԫʱ������У�������������������
			String key = null;
			if (object instanceof BuildingInfo) {
				key = ((BuildingInfo) object).getId().toString() + 0;
			} else if (object instanceof Integer) { // ���滻Ϊ��Ԫ���� -- ��ʱ����
				int unit = ((Integer) object).intValue();
				DefaultMutableTreeNode parent = (DefaultMutableTreeNode) node.getParent();
				BuildingInfo building = (BuildingInfo) parent.getUserObject();
				key = building.getId().toString() + unit;
			} else if (object instanceof BuildingUnitInfo) {
				int unit = ((BuildingUnitInfo) object).getSeq();
				DefaultMutableTreeNode parent = (DefaultMutableTreeNode) node.getParent();
				BuildingInfo building = (BuildingInfo) parent.getUserObject();
				key = building.getId().toString() + unit;
			} else {
				return 0;
			}
			Integer minNum = (Integer) buildingMinNum.get(key);
			Integer maxNum = (Integer) buildingMaxNum.get(key);
			if (minNum == null || maxNum == null) {
				return 0;
			}

			for (int i = minNum.intValue(); i <= maxNum.intValue(); i++) { // ��ͷ������кűض�Ϊ
																			// '���'��
				table.getHeadRow(table.getHeadRowCount() - 1).getCell(i + colIndex - minNum.intValue()).setValue(i + "��");
			}

			if (currHeadRowNum >= 0 && currHeadRowNum < table.getHeadRowCount()) {
				
					table.getHeadRow(currHeadRowNum).getCell(colIndex).setValue(object);
					//����ʾ��Ԫʱ��ͷ����ʾ��Ԫ by tim_gao
					if(object instanceof BuildingUnitInfo){
						if(((BuildingUnitInfo) object).isHaveUnit()){
							table.getHeadRow(currHeadRowNum).getCell(colIndex).setValue(null);
						}
					}
			
				table.getHeadMergeManager().mergeBlock(currHeadRowNum, colIndex, currHeadRowNum, colIndex + maxNum.intValue() - minNum.intValue());

				if (object instanceof BuildingInfo) { // ¥�����޵�Ԫ,��head���е�Ԫ�е����;
														// ¥�����޷�������head���з����е����
					int unitHeadRow = getHeadRowNumByName(table, "��Ԫ");
					if (unitHeadRow >= 0)
						table.getHeadMergeManager().mergeBlock(unitHeadRow, colIndex, unitHeadRow, colIndex + maxNum.intValue() - minNum.intValue());
					if (!(node.getParent() instanceof SubareaInfo)) {
						int subAreaHeadRow = getHeadRowNumByName(table, "����");
						if (subAreaHeadRow >= 0)
							table.getHeadMergeManager().mergeBlock(subAreaHeadRow, colIndex, subAreaHeadRow, colIndex + maxNum.intValue() - minNum.intValue());
					}
				}
			}

			return maxNum.intValue() - minNum.intValue() + 1;
		} else {
			if (currHeadRowNum >= 0) {
				for (int i = 0; i < childRowIndex - colIndex; i++) {
					table.getHeadRow(currHeadRowNum).getCell(colIndex + i).setValue(object);
					//����ʾ��Ԫʱ��ͷ����ʾ��Ԫ by tim_gao
					if(object instanceof BuildingUnitInfo){
						if(((BuildingUnitInfo) object).isHaveUnit()){
							table.getHeadRow(currHeadRowNum).getCell(colIndex).setValue(null);
						}
					}
					
				}
				table.getHeadMergeManager().mergeBlock(currHeadRowNum, colIndex, currHeadRowNum, childRowIndex - 1);
			}
			return childRowIndex - colIndex;
		}

	}

	// ֻ����allNameString�е����ƣ�
	private static int getHeadRowNumByName(KDTable table, String cellName) {
		int headRowNumber = -1;
		String allNameString = "���,��Ԫ,¥��,����,��Ŀ,��֯,��֯1,��֯2,��֯3,��֯4,��֯5,��֯6,";
		if (cellName == null || allNameString.indexOf(cellName) < 0)
			return headRowNumber;

		for (int i = 0; i < table.getHeadRowCount(); i++) {
			IRow headRow = table.getHeadRow(i);
			Object cellObject = headRow.getCell(0).getValue();
			if (cellObject != null && cellObject instanceof String) {
				String cellString = (String) cellObject;
				if (cellString.equals(cellName))
					return i;
			}
		}
		return headRowNumber;
	}

	/**
	 *����ƽ��ͼԪ��,����ƽ��ͼ
	 */
	public static void showPlanisphereTable(KDTable table, PlanisphereInfo phInfo, IDisplayRule setting) throws BOSException {
		if (table == null)
			return;
		TableDrawManager thisPicTable = new TableDrawManager();
		table.getIndexColumn().getStyleAttributes().setHided(false);
		thisPicTable.setTable(table);
		if (phInfo == null)
			thisPicTable.initTable(0, 0, 0, 0, TableDrawManager.VIEW_MODEL);
		else
			thisPicTable.initTable(phInfo.getCellVertiCount(), phInfo.getCellHorizCount(), phInfo.getCellHeigth(), phInfo.getCellWidth(), TableDrawManager.VIEW_MODEL);

		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("planisphere.id", phInfo.getId().toString()));
		view.setFilter(filter);
		view.getSelector().add(new SelectorItemInfo("*"));
		view.getSelector().add(new SelectorItemInfo("roomEntry.building.sellProject"));
		if (setting.getSysType().equals(MoneySysTypeEnum.SalehouseSys)) {
			view.getSelector().add(new SelectorItemInfo("roomEntry.sellState"));
			view.getSelector().add(new SelectorItemInfo("roomEntry.isForSHE"));
		} else if (setting.getSysType().equals(MoneySysTypeEnum.TenancySys)) {
			view.getSelector().add(new SelectorItemInfo("roomEntry.tenancyState"));
			view.getSelector().add(new SelectorItemInfo("roomEntry.isForTen"));
		}
		PlanisphereElementEntryCollection elementColl = PlanisphereElementEntryFactory.getRemoteInstance().getPlanisphereElementEntryCollection(view);

		if (phInfo.getPtype().equals(PlanisphereTypeEnum.PicBuildPlane)) {
			/*
			 * ���ڷ����Ԫ�أ���ɫ�Ǹ����޻���¥ϵͳ���Լ�����״̬�йأ���ˣ����ڴ˴����ѷ���Ԫ�ص���ɫȫ������һ�� �ѷ��������õ���Ԫ����
			 */
			for (int i = 0; i < elementColl.size(); i++) {
				PlanisphereElementEntryInfo phElementInfo = elementColl.get(i);
				if (phElementInfo.getType().equals(PlanisphereElementEnum.room)) { // ��������
					RoomInfo roomInfo = phElementInfo.getRoomEntry();
					List plist = CommerceHelper.ByteArrayToListObject(phElementInfo.getOutLineLocationData());
					for (int j = 0; j < plist.size(); j++) { // �ѷ������
																// д��÷������������������е�Ԫ����
						Point point = (Point) plist.get(j);
						ICell cell = table.getCell(point.x, point.y);
						if (cell != null)
							cell.setUserObject(roomInfo); // setting
					}

					phElementInfo.setNameColor(setting.getFrontColor().getRGB());
					String roomStatus = null;
					if (MoneySysTypeEnum.SalehouseSys.equals(setting.getSysType())) {
						if (roomInfo.isIsForSHE() == false)
							roomStatus = "noSellhouse";
						else
							roomStatus = (roomInfo.getSellState() == null ? null : roomInfo.getSellState().getValue());
					} else if (MoneySysTypeEnum.TenancySys.equals(setting.getSysType())) {
						if (roomInfo.isIsForTen() == false)
							roomStatus = "noTenancy";
						else
							roomStatus = (roomInfo.getTenancyState() == null ? null : roomInfo.getTenancyState().getValue());
					}
					int bkColor = setting.getCellBackgroundColor(roomStatus).getRGB();

					phElementInfo.setOutLineBackColor(bkColor);
					phElementInfo.setNameBackColor(bkColor);
				}
			}
		} else if (phInfo.getPtype().equals(PlanisphereTypeEnum.PicBuildDist)) { /* ¥���ֲ�ͼ */
			for (int i = 0; i < elementColl.size(); i++) {
				PlanisphereElementEntryInfo phElementInfo = elementColl.get(i);
				if (phElementInfo.getType().equals(PlanisphereElementEnum.building)) {
					BuildingInfo buildInfo = phElementInfo.getBuildingEntry();
					List plist = CommerceHelper.ByteArrayToListObject(phElementInfo.getOutLineLocationData());
					for (int j = 0; j < plist.size(); j++) { // ��¥������
																// д��÷������������������е�Ԫ����
						Point point = (Point) plist.get(j);
						ICell cell = table.getCell(point.x, point.y);
						if (cell != null)
							cell.setUserObject(buildInfo);
					}
				}
			}
		} else if (phInfo.getPtype().equals(PlanisphereTypeEnum.PicSellProject)) { /* ��Ŀͼ */
			for (int i = 0; i < elementColl.size(); i++) {
				PlanisphereElementEntryInfo phElementInfo = elementColl.get(i);
				if (phElementInfo.getType().equals(PlanisphereElementEnum.sellProject)) {
					SellProjectInfo proInfo = phElementInfo.getSellProjectEntry();
					List plist = CommerceHelper.ByteArrayToListObject(phElementInfo.getOutLineLocationData());
					for (int j = 0; j < plist.size(); j++) { // ����Ŀ����
																// д��÷������������������е�Ԫ����
						Point point = (Point) plist.get(j);
						ICell cell = table.getCell(point.x, point.y);
						if (cell != null)
							cell.setUserObject(proInfo);
					}
				}
			}
		}

		phInfo.getElementEntry().clear();
		phInfo.getElementEntry().addCollection(elementColl);
		thisPicTable.showElementCollection(elementColl);
	}

	// ����������ڷ������±���ʱ����ã���һ��panel������������ϵͳΪ�գ���Ӱ�����ۿ��ƺ����޿���
	//����ͼ����ʾ����ķ�ʽ ֻ�����ڵ�Ԫ���ġ������¥��������¥�����е�Ԫ�ķ������޷���ʾ   by jeegan
	public static void fillRoomListTableByNode(KDTable table, DefaultMutableTreeNode root, MoneySysTypeEnum moneySysTypeEnum, IDisplayRule setting, KDScrollPane panel) throws BOSException{
		fillRoomTableByNode(table, root, moneySysTypeEnum, null, null, setting, panel, false);
	}

	public static void fillRoomTableByNode(KDTable table, DefaultMutableTreeNode root, MoneySysTypeEnum moneySysTypeEnum, IDisplayRule setting) throws BOSException{
		fillRoomTableByNode(table, root, moneySysTypeEnum, null, null, setting, null, false);
	}

	public static void fillRoomTableByNode(KDTable table, DefaultMutableTreeNode root, MoneySysTypeEnum moneySysTypeEnum, RoomCollection roomCollRestrict, IDisplayRule setting) throws BOSException {
		fillRoomTableByNode(table, root, moneySysTypeEnum, roomCollRestrict, null, setting, null, false);
	}

	public static void fillRoomTableByNode(KDTable table, DefaultMutableTreeNode root, MoneySysTypeEnum moneySysTypeEnum, RoomCollection roomCollRestrict, SellProjectInfo sellProjectRestrict,
			IDisplayRule setting, KDScrollPane panel) throws BOSException{
		fillRoomTableByNode(table, root, moneySysTypeEnum, roomCollRestrict, sellProjectRestrict, setting, null, false);
	}

	public static void fillVirtualRoomTableByNode(KDTable table, DefaultMutableTreeNode root, MoneySysTypeEnum moneySysTypeEnum, RoomCollection roomCollRestrict, IDisplayRule setting,
			boolean isVirtual) throws BOSException{
		fillRoomTableByNode(table, root, moneySysTypeEnum, roomCollRestrict, null, setting, null, true);
	}

	/**
	 * ��ָ��������ӷ��� ��ѡ��ڵ㡢ϵͳ���͡��Լ����䷶Χ���� ������
	 * 
	 * @param table
	 * @param root
	 *            ������֯���Ľڵ� ��������Ŀ ���� §����Ԫ
	 * @param moneySysTypeEnum
	 *            ϵͳ����
	 * @param roomCollRestrict
	 *            �Է��䷶Χ��������
	 * @param sellProjectRestrict
	 *            �Է������ڵ�������Ŀ�������ƣ�ֻ����ʾ��������Ŀ�µķ���
	 * @param isVirtual
	 *            �Ƿ��Ǽٵ�����ͼ��
	 * @throws EASBizException 
	 */
	public static void fillRoomTableByNode(KDTable table, DefaultMutableTreeNode root, MoneySysTypeEnum moneySysTypeEnum, RoomCollection roomCollRestrict, SellProjectInfo sellProjectRestrict,
			IDisplayRule setting, KDScrollPane panel, boolean isVirtual) throws BOSException{
		if (root.getDepth() > 10) {
			MsgBox.showInfo("̫�����֯��,���ܲ�ѯ!");
		}

		table.getStyleAttributes().setLocked(true);
		table.getStyleAttributes().setHorizontalAlign(HorizontalAlignment.CENTER);
//		table.getIndexColumn().getStyleAttributes().setHided(true);
		table.getStyleAttributes().setWrapText(true);
		table.setHorizonGridLineVisible(true);
		table.setVerticalGridLineVisible(true);
		table.setVerticalHeadGridLineVisible(true);
		table.setHorizonHeadGridLineVisible(true);
		table.removeColumns();
		table.removeHeadRows();
		table.removeRows();

		//by tim_gao
		try {
			setUnitSonNodeUpNode(root);
		} catch (Exception e2) {
			// TODO Auto-generated catch block
			log.warn("����ʾ��Ԫ�ڵ����ʧ�ܣ�");
			FDCMsgBox.showWarning("����ʾ��Ԫ�ڵ����ʧ�ܣ�");
		}
		
		
		String roomFilterSql = "";
		if (sellProjectRestrict != null) { // �Է������ڵ�������Ŀ�������ƣ�ֻ����ʾ��������Ŀ�µķ���
			if(sellProjectRestrict.getLongNumber()==null)
				roomFilterSql += "and SellProject.fid = '" + sellProjectRestrict.getId().toString() + "' ";
			else		//�µ���¥Ҫ֧������Ŀ
				roomFilterSql += " and (SellProject.fnumber = '" + sellProjectRestrict.getNumber() +"'  or  SellProject.flongNumber like '" + sellProjectRestrict.getLongNumber() + "!%') ";
		}
		if (roomCollRestrict != null && roomCollRestrict.size() > 0) { // ����Է��䷶Χ��������
																		// ,
																		// ֻ��ʾָ���ķ���
			String restirctRoomIdStr = "";
			for (int i = 0; i < roomCollRestrict.size(); i++)
				restirctRoomIdStr += ",'" + roomCollRestrict.get(i).getId().toString() + "'";
			restirctRoomIdStr = restirctRoomIdStr.replaceFirst(",", "");
			roomFilterSql = "and Room.fid in (" + restirctRoomIdStr + ") ";
		}

		// ѡ��Ľڵ�仯ʱ
		Object objectNode = root.getUserObject();
		if (objectNode instanceof BuildingUnitInfo) { // ��Ԫ����
			BuildingUnitInfo buildUnit = (BuildingUnitInfo) objectNode;
			roomFilterSql += "and BuildUnit.fid = '" + buildUnit.getId().toString() + "'";
		} else if (objectNode instanceof BuildingInfo) { // ¥��
			BuildingInfo building = (BuildingInfo) objectNode;
			roomFilterSql += "and Build.fid = '" + building.getId().toString() + "'";
		} else if (objectNode instanceof SubareaInfo) { // ����
			SubareaInfo subAreaInfo = (SubareaInfo) objectNode;
			roomFilterSql += "and SubArea.fid = '" + subAreaInfo.getId().toString() + "'";
		} else if (objectNode instanceof SellProjectInfo) {
			SellProjectInfo sellProInfo = (SellProjectInfo) objectNode;
			roomFilterSql += "and SellProject.fid = '" + sellProInfo.getId().toString() + "'";
		} else {// ��Ȳ������е�������Ŀ
			String sellProIdFilterStr = "'null'";
			Enumeration enumeration = root.breadthFirstEnumeration();
			while (enumeration.hasMoreElements()) {
				DefaultMutableTreeNode element = (DefaultMutableTreeNode) enumeration.nextElement();
				if (element.getUserObject() instanceof SellProjectInfo) {
					SellProjectInfo tempSellProInfo = (SellProjectInfo) element.getUserObject();
					sellProIdFilterStr += ",'" + tempSellProInfo.getId().toString() + "'";
				}
			}
			roomFilterSql += "and SellProject.fid in (" + sellProIdFilterStr + ")";
		}

//		if (moneySysTypeEnum != null) {
//			if (moneySysTypeEnum.equals(MoneySysTypeEnum.SalehouseSys))
//				roomFilterSql += " and Room.fisForSHE=1";
//			else if (moneySysTypeEnum.equals(MoneySysTypeEnum.TenancySys))
//				roomFilterSql += " and Room.fisForTen=1";
//			else if (moneySysTypeEnum.equals(MoneySysTypeEnum.ManageSys))
//				roomFilterSql += " and Room.fisForPPM=1";
//		}

		RoomCollection rooms = new RoomCollection();
		FDCSQLBuilder builder = new FDCSQLBuilder();
		//���ӷ����տ�״̬�ֶ������by zgy 2010-12-17
		builder.appendSql("select Room.fSub,Room.fid,Room.fname_l2,Room.fnumber,Room.fdisplayName,Room.funit,Room.ffloor,Room.fserialNumber,Room.fendSerialNumber,Room.FISMortagaged,"
				+ "Room.fsellState,Room.fisForSHE,Room.fisForTen,Room.fisForPPM,Room.ftenancyState,Room.fbuildingId,Build.fsellProjectId,Room.FReceiptTypeState,Build.fname_l2 buildname from t_she_room Room  ");
		builder.appendSql("inner join t_she_building Build on Room.fbuildingId = Build.fid ");
		builder.appendSql("inner join t_she_sellProject SellProject on Build.fsellProjectId = SellProject.fid ");
		builder.appendSql("left outer join t_she_buildingUnit BuildUnit on Room.fbuildUnitId = BuildUnit.fid ");
		builder.appendSql("left outer join t_she_subarea SubArea on Build.fsubareaId = SubArea.fid ");
		if (!roomFilterSql.equals("")) {
			builder.appendSql("where " + roomFilterSql.replaceFirst("and", ""));
		}

		Set chooseRoomBuilds = new HashSet();
		if(isOpenedChooseRoom){
			EntityViewInfo shefv = new EntityViewInfo();
			FilterInfo shefF = new FilterInfo();
			shefF.getFilterItems().add(new FilterItemInfo("isEnabled", Boolean.TRUE));
			shefv.setFilter(shefF);
			shefv.getSelector().add("building.id");
			
			SHEFunctionChooseRoomSetCollection tcoll = SHEFunctionChooseRoomSetFactory.getRemoteInstance().getSHEFunctionChooseRoomSetCollection(shefv);
			for(int i=0;i<tcoll.size(); i++){
				chooseRoomBuilds.add(tcoll.get(i).getBuilding().getId().toString());
			}
		}
		
		try {
			IRowSet rs = builder.executeQuery();
			while (rs.next()) {
				RoomInfo newInfo = new RoomInfo();
				String fid = rs.getString("fid");
				String fname = rs.getString("fname_l2");
				String fnumber = rs.getString("fnumber");
				String fdisplayName = rs.getString("fdisplayName");
				newInfo.setDisplayName(fdisplayName);
				newInfo.setId(BOSUuid.read(fid));
				newInfo.setName(fname);
				newInfo.setNumber(fnumber);
				int funit = rs.getInt("funit");
				newInfo.setUnit(funit);
				int ffloor = rs.getInt("ffloor");
				newInfo.setFloor(ffloor);
				int fserialNumber = rs.getInt("fserialNumber");
				newInfo.setSerialNumber(fserialNumber);
				int fendSerialNumber = rs.getInt("fendSerialNumber");
				newInfo.setEndSerialNumber(fendSerialNumber);
				newInfo.setIsMortagaged(rs.getBoolean("FISMortagaged"));
				String fsellState = rs.getString("fsellState");
				if (fsellState != null) {
					if (fsellState.equals("Onshow"))
						fsellState = "OnShow"; // ���ƺ�ֵ��һ�������
					newInfo.setSellState((RoomSellStateEnum) RoomSellStateEnum.getEnumMap().get(fsellState));
				}
				String ReceiptTypeState = rs.getString("FReceiptTypeState");
				if(ReceiptTypeState!=null){
					newInfo.setReceiptTypeState((ReceiptTypeStateEnum)ReceiptTypeStateEnum.getEnumMap().get(ReceiptTypeState));	
				}
				newInfo.setIsForSHE(rs.getBoolean("fisForSHE"));
				newInfo.setIsForTen(rs.getBoolean("fisForTen"));
				newInfo.setIsForPPM(rs.getBoolean("fisForPPM"));
				String ftenancyState = rs.getString("ftenancyState");
				if (ftenancyState != null) {
					if (ftenancyState.equals("UNTenancy"))
						ftenancyState = "unTenancy"; // ���ƺ�ֵ��һ�������
					else if (ftenancyState.equals("WaitTenancy"))
						ftenancyState = "waitTenancy";
					else if (ftenancyState.equals("KeepTenancy"))
						ftenancyState = "keepTenancy";
					else if (ftenancyState.equals("NewTenancy"))
						ftenancyState = "newTenancy";
					else if (ftenancyState.equals("ContinueTenancy"))
						ftenancyState = "continueTenancy";
					else if (ftenancyState.equals("EnlargeTenancy"))
						ftenancyState = "enlargeTenancy";
					else if (ftenancyState.equals("SincerObligate"))
						ftenancyState = "sincerObligate";
					newInfo.setTenancyState((TenancyStateEnum) TenancyStateEnum.getEnumMap().get(ftenancyState));
				}
				String fbuildingId = rs.getString("fbuildingId");
				if (fbuildingId != null) {
					BuildingInfo tempBuild = new BuildingInfo();
					tempBuild.setId(BOSUuid.read(fbuildingId));
					String fsellProjectId = rs.getString("fsellProjectId");
					tempBuild.setName(rs.getString("buildname"));
					if (fsellProjectId != null) {
						SellProjectInfo tempSellPro = new SellProjectInfo();
						tempSellPro.setId(BOSUuid.read(fsellProjectId));
						tempBuild.setSellProject(tempSellPro);
					}
					newInfo.setBuilding(tempBuild);
				}
				
				//ѡ����ʱ��Ϊ����ѡ�����õķ��䲻��ʾ����
				if(isOpenedChooseRoom && !chooseRoomBuilds.contains(fbuildingId)){
					continue;
				}
				
				newInfo.setSub(rs.getString("fSub"));
				rooms.add(newInfo);
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
			SysUtil.abort();
		}

		if (rooms.size() > 0 && moneySysTypeEnum == null) {
			/*
			 * //��ʾ������������Ŀ��Դ����һ����������Ŀ����ȡ���һ��� ---- �Σ���ʱ���������ȥ--����ô����Ӧ��զ��
			 *
			 * SellProjectResourceEnum projectResenum =
			 * rooms.get(0).getBuilding().getSellProject().getProjectResource();
			 * RoomDisplaySetViewUI.insertUIToScrollPanel(panel,projectResenum);
			 */
			try {
				BuildingInfo buildInfo = BuildingFactory.getRemoteInstance().getBuildingInfo("select sellProject.ProjectResource where id='" + rooms.get(0).getBuilding().getId().toString() + "'");
				RoomDisplaySetViewUI.insertUIToScrollPanel(panel, buildInfo.getSellProject().getProjectResource());
			} catch (EASBizException e) {
				e.printStackTrace();
				SysUtil.abort();
			}
		}
		if (rooms.size() > 0) {
			Map buildingMaxFloor = new HashMap(); // ¥��id�����¥���ӳ��
			Map buildingMinFloor = new HashMap();
			Map buildingMaxNum = new HashMap(); // ¥��id�ͷ����������кŵ�ӳ��
			Map buildingMinNum = new HashMap();
			for (int i = 0; i < rooms.size(); i++) {
				RoomInfo room = rooms.get(i);
				// int unitNum =
				// room.getBuildUnit()==null?0:room.getBuildUnit().getSeq();
				int unitNum = room.getUnit();
				String key = room.getBuilding().getId().toString() + unitNum;
				Integer maxFloor = (Integer) buildingMaxFloor.get(key);
				Integer minFloor = (Integer) buildingMinFloor.get(key);
				Integer maxNum = (Integer) buildingMaxNum.get(key);
				Integer minNum = (Integer) buildingMinNum.get(key);
				if (minFloor == null || room.getFloor() < minFloor.intValue()) {
					buildingMinFloor.put(key, new Integer(room.getFloor()));
				}
				if (maxFloor == null || room.getFloor() > maxFloor.intValue()) {
					buildingMaxFloor.put(key, new Integer(room.getFloor()));
				}
				if (minNum == null || room.getSerialNumber() < minNum.intValue()) {
					buildingMinNum.put(key, new Integer(room.getSerialNumber()));
				}
				if (maxNum == null || room.getEndSerialNumber() > maxNum.intValue()) {
					buildingMaxNum.put(key, new Integer(room.getEndSerialNumber()));
				}
			}
			IColumn column = table.addColumn();
			column.setKey("floor");
			column.setWidth(40);
			Enumeration enumeration = root.depthFirstEnumeration();
			boolean haveUnit = false;
			while (enumeration.hasMoreElements()) {
				DefaultMutableTreeNode element = (DefaultMutableTreeNode) enumeration.nextElement();
				Object object = element.getUserObject();
				String key = null;
				if (object instanceof Integer) { // ���滻Ϊ��Ԫ���� --- ��ʱ����
					int unit = ((Integer) object).intValue();
					DefaultMutableTreeNode parent = (DefaultMutableTreeNode) element.getParent();
					BuildingInfo building = (BuildingInfo) parent.getUserObject();
					key = building.getId().toString() + unit;
					haveUnit = true;
				} else if (object instanceof BuildingUnitInfo) {
					int unit = ((BuildingUnitInfo) object).getSeq();
					DefaultMutableTreeNode parent = (DefaultMutableTreeNode) element.getParent();
					BuildingInfo building = (BuildingInfo) parent.getUserObject();
					key = building.getId().toString() + unit;
					haveUnit = true;
					
				} else if (object instanceof BuildingInfo) {
					BuildingInfo building = (BuildingInfo) object;
					key = building.getId().toString() + 0;
				}
				Integer minNum = (Integer) buildingMinNum.get(key);
				Integer maxNum = (Integer) buildingMaxNum.get(key);
				if (minNum == null || maxNum == null) {
					continue;
				}
				for (int i = minNum.intValue(); i <= maxNum.intValue(); i++) { // ��������
					column = table.addColumn();
					column.setKey(key + i);
					column.setWidth(setting.getRoomWidth());
				}
			}
			int minRow = 1;
			int maxRow = 0;
			Collection collection = buildingMaxFloor.values();
			for (Iterator iter = collection.iterator(); iter.hasNext();) {
				Integer num = (Integer) iter.next();
				if (num.intValue() > maxRow)
					maxRow = num.intValue();
			}
			// ���϶���С¥�㣬���� ����¥���֧�֣� �㲻�� ���� Ϊʲô�����ַ��� ȥ�����¥�㣬���Ǽ�Ȼ������ô�ã���Ҳ�������ȥ����С¥��
			// by laiquan_luo
			Collection tempColl = buildingMinFloor.values();
			for (Iterator iter = tempColl.iterator(); iter.hasNext();) {
				Integer num = (Integer) iter.next();
				if (num.intValue() < minRow)
					minRow = num.intValue();
			}

			for (int i = minRow; i <= maxRow; i++) {

				IRow row = table.addRow();
				row.setUserObject(new Integer(i));
				row.setHeight(40);
//				row.setHeight(setting.getRoomHeight());

				int currFloor = maxRow - i + minRow;
				row.getCell("floor").setValue(new Integer(currFloor) + "��");
				if(currFloor==0) row.getStyleAttributes().setHided(true);	//0������
			}
			/**
			 * ��ѯ������ ����״̬Ϊ���ۣ����ж�Ӧ���Ϲ�����
			 * Ԥ�����롢Ԥ�����ˡ��Ϲ����롢�Ϲ������С��Ϲ�������5��״̬���Ϲ���
			 */
			
//			FDCSQLBuilder purChBuilder = new FDCSQLBuilder();
//			purChBuilder.appendSql(" select Room.fid,Room.fname_l2,Room.fnumber,Room.fdisplayName,Room.funit,Room.ffloor,Room.fserialNumber,Room.fendSerialNumber, " +
//					           " Room.fsellState,Room.fisForSHE,Room.fisForTen,Room.fisForPPM,Room.ftenancyState,Room.fbuildingId,Build.fsellProjectId ");
//			purChBuilder.appendSql(" from  t_she_purchase purchase left join t_she_room Room  on purchase.froomid = room.fid ");
//			purChBuilder.appendSql(" inner join t_she_building Build on Room.fbuildingId = Build.fid  ");
//			purChBuilder.appendSql(" inner join t_she_sellProject SellProject on Build.fsellProjectId = SellProject.fid  ");
//			purChBuilder.appendSql(" left outer join t_she_buildingUnit BuildUnit on Room.fbuildUnitId = BuildUnit.fid  ");
//			purChBuilder.appendSql(" left outer join t_she_subarea SubArea on Build.fsubareaId = SubArea.fid  ");
//			purChBuilder.appendSql(" where purchase.FPurchaseState in ('PrePurchaseApply','PrePurchaseCheck','PurchaseApply','PurchaseAuditing','PurchaseAudit') ");
//			purChBuilder.appendSql(" and Room.fsellState not in('SincerPurchase') ");
//			
//			Set roomPurSet = new HashSet();
//			if (!roomFilterSql.equals("")) {
//				purChBuilder.appendSql(roomFilterSql);
//			}
//			IRowSet rsPur = purChBuilder.executeQuery();
//			try {
//				while (rsPur.next()) {
//					roomPurSet.add(rsPur.getString("fid"));
//				}
//			} catch (SQLException e) {
//				e.printStackTrace();
//				SysUtil.abort();
//			}

			//���Ƕ��巿��ʱΪfalse
			isDes = false;
			
			try {
				fillRoomTable(table, rooms, setting, moneySysTypeEnum, panel, isVirtual,new HashSet());
			} catch (EASBizException e) {
				// TODO Auto-generated catch block
				log.warn("����ͼ�����ʧ��"+e.getMessage());
			}
			//�����Ż� xin_wang 2011.03.31
			String text[] = null;
			
			if (haveUnit) {
				text = new String[] { "���", "��Ԫ", "¥��", "����", "��Ŀ", "��֯", "��֯1", "��֯2", "��֯3", "��֯4", "��֯5", "��֯6" }; // ���ڶ༶��֯�����������ֿ�
			} else {
				text = new String[] { "���", "¥��", "����", "��Ŀ", "��֯", "��֯1", "��֯2", "��֯3", "��֯4", "��֯5", "��֯6" };
			}
			for (int i = 0; i < root.getDepth() + 1; i++) { // ������ͷ
				IRow row = table.addHeadRow(0);
				row.getCell(0).setValue(text[i]);
			}

			int count = 1;
			if (0==root.getChildCount()) {
				String key = null;
				if (objectNode instanceof BuildingInfo) {
					key = ((BuildingInfo) objectNode).getId().toString() + 0;
				} else if (objectNode instanceof Integer) { // ���滻Ϊ��Ԫ���� -- ��ʱ����
					int unit = ((Integer) objectNode).intValue();
					DefaultMutableTreeNode parent = (DefaultMutableTreeNode) root.getParent();
					BuildingInfo building = (BuildingInfo) parent.getUserObject();
					key = building.getId().toString() + unit;
				} else if (objectNode instanceof BuildingUnitInfo) {
					int unit = ((BuildingUnitInfo) objectNode).getSeq();
					DefaultMutableTreeNode parent = (DefaultMutableTreeNode) root.getParent();
					BuildingInfo building = (BuildingInfo) parent.getUserObject();
					key = building.getId().toString() + unit;
				} else {
					return;
				}
				Integer minNum = (Integer) buildingMinNum.get(key);
				Integer maxNum = (Integer) buildingMaxNum.get(key);
				if (minNum == null || maxNum == null) {
					return;
				}
				for (int i = minNum.intValue(); i <= maxNum.intValue(); i++) {
					table.getHeadRow(0).getCell(i + 1 - minNum.intValue()).setValue(i + "��");
				}
			} else {
				for (int i = 0; i < root.getChildCount(); i++) {
					count += setTableHeadByNode(table, (DefaultMutableTreeNode) root.getChildAt(i), count, buildingMinNum, buildingMaxNum);
				}
			}
		}
		//��ɾ������ʾ�Ľڵ�
		delNodeFromTree(root);
	}
	
	


	/**
	 * ��䷿���,��������ϵͳ�ͷ���״̬���÷�����ʾ����ɫ�������..
	 * @throws BOSException 
	 * @throws EASBizException 
	 */
	private static void fillRoomTable(KDTable table, RoomCollection rooms, IDisplayRule setting, MoneySysTypeEnum moneySysTypeEnum, KDScrollPane panel11, boolean isVirtual,Set roomPurSet) throws BOSException, EASBizException {
		// �������������Ӧ�ı�������
		Map roomAttachMap = new HashMap();
		FDCSQLBuilder builder = new FDCSQLBuilder();
		builder.appendSql("select roomEntry.fheadId,room.fnumber from t_she_RoomAttachmentEntry roomEntry inner join t_she_room Room on roomEntry.froomId = Room.fid ");
		try {
			IRowSet rs = builder.executeQuery();
			while (rs.next()) {
				String froomId = rs.getString("fheadId");
				String fnumber = rs.getString("fnumber");
				if (roomAttachMap.containsKey(froomId)) {
					String numberStr = (String) roomAttachMap.get(froomId);
					roomAttachMap.put(froomId, numberStr + "," + fnumber);
				} else {
					roomAttachMap.put(froomId, fnumber);
				}
			}
		} catch (BOSException e1) {
			e1.printStackTrace();
			SysUtil.abort();
		} catch (SQLException e) {
			e.printStackTrace();
			SysUtil.abort();
		}

		//�鴦��������ѡ����¥��
		Set chooseRoomBuilds = new HashSet();
		if(isOpenedChooseRoom){
			EntityViewInfo shefv = new EntityViewInfo();
			FilterInfo shefF = new FilterInfo();
			shefF.getFilterItems().add(new FilterItemInfo("isEnabled", Boolean.TRUE));
			shefv.setFilter(shefF);
			shefv.getSelector().add("building.id");
			
			SHEFunctionChooseRoomSetCollection tcoll = SHEFunctionChooseRoomSetFactory.getRemoteInstance().getSHEFunctionChooseRoomSetCollection(shefv);
			for(int i=0;i<tcoll.size(); i++){
				chooseRoomBuilds.add(tcoll.get(i).getBuilding().getId().toString());
			}
		}
		
		Integer minRow = (Integer) table.getRow(0).getUserObject();
		//�����ж�¥����ID
		String buildIdStr = null;
		//����ͼ����ѡ����������ʱ���ں�
		//by tim_gao 
		boolean isShowMerge = true;
		for (int i = 0; i < rooms.size(); i++) {
			RoomInfo room = rooms.get(i);
			String buildingId = room.getBuilding().getId().toString();
			// int unitNum =
			// room.getBuildUnit()==null?0:room.getBuildUnit().getSeq();
			int unitNum = room.getUnit();
			String key = buildingId + unitNum;
			for (int j = room.getSerialNumber(); j <= room.getEndSerialNumber(); j++) {
				ICell cell = table.getCell(table.getRowCount() + minRow.intValue() - 1 - room.getFloor(), key + j);
				if (cell == null)
					continue;
				cell.getStyleAttributes().setFont(setting.getFont());
				cell.getStyleAttributes().setFontColor(setting.getFrontColor());
				String text = "";
				String displayField = setting.getDisplayField();
				Object object = room.getDisplayName();
//				Object object = room.get(displayField);
//				if (displayField.equals("houseProperty")) {
//					object = room.getHouseProperty();
//				}
				if (object != null) {
					if (object instanceof BigDecimal) {
						text += ((BigDecimal) object).setScale(2, BigDecimal.ROUND_HALF_UP);
					} else {
						text += object;
					}
				}
				if (cell.getUserObject() != null) {
					RoomInfo roomte = (RoomInfo) cell.getUserObject();
					// log.info("���巿���ص�,�������:" + text+" ; "+ key +
					// ";floor="+room.getFloor() + ";fUnit="+room.getUnit() );
					String unit ="";
					if(null!=room.getBuildUnit()){
						unit = room.getBuildUnit().getName()+"-";
					}
					MsgBox.showError("���巿��λ���ص�:"+unit+"��"+room.getFloor()+"��"+"-���"+room.getSerialNumber()+""+"-�������:" + text+"\n"
							+"���޸ķ��䶨����Ϣ��");
					SysUtil.abort();
				}

				if (setting.getAttachDisType() == 0 || setting.getAttachDisType() == 1) {
					if (room.getId() != null) { // ���Ǵ�������ʱ���������Ϊ��
						String attachNumbers = (String) roomAttachMap.get(room.getId().toString());
						if (attachNumbers != null) {
							if (setting.getAttachDisType() == 0)
								text += "\n��" + attachNumbers.split(",").length + "������";
							else
								text += "\n��:" + attachNumbers;
						}
					}
				} else if (setting.getAttachDisType() == 2) {

				}
				if(TenancyStateEnum.keepTenancy.equals(room.getTenancyState())){
					cell.getStyleAttributes().setStrikeThrough(true);
				}
				if (isVirtual) {
					/** �ָ��ݾ���ķ���״̬�Ͷ�Ӧ���Ϲ�����Ϣ��ȷ��������ʾ���ۻ������ۡ�
					 */
					RoomDisplaySetting roomSetting = (RoomDisplaySetting)setting;
					cell.getStyleAttributes().setBackground(getRoomColorByState(room,roomSetting));
					
				} else {
					if(isDes){
						//��Ϊ�������������⣬�ܻ�����������ԣ�����ǿ��ȥ��
						room.setIsForTen(false);
						room.setIsForPPM(false);
						
						cell.getStyleAttributes().setBackground(getRoomColorBySysForDes(room, moneySysTypeEnum, setting));
					}else{
						cell.getStyleAttributes().setBackground(getRoomColorBySys(room, moneySysTypeEnum, setting));
					}
					
				}
				// by tim_gao 2011 -07-17 
				//û������״̬��������
				cell.setValue(text);
				if(false==room.isIsForPPM()&&false==room.isIsForSHE()&&false==room.isIsForTen()){
					cell.setValue(null);
				}
				cell.setUserObject(room);
			}
			
			
			//by tim_gao ����ѡ�����������Ƿ���ʾ���¥���ĺϲ�
			//��Ϊѡ�������ǿ���ȥ����¥���ģ����Ƿ����ﻹ���еģ�
			//���ǻ���ݷ�����кϲ�����¥�����Ѿ�û�У�ͼ���ϾͲ���ʾ
			//����ȴҪ�ϲ��ͻᱨ��
			if(isOpenedChooseRoom){
				if(null==buildIdStr||!buildIdStr.equals(room.getBuilding().getId().toString())){
					buildIdStr = room.getBuilding().getId().toString();
//					if(null!=set){
					
					if(!chooseRoomBuilds.contains(buildIdStr)){
						isShowMerge =false;
					}
//					}else{
//						isShowMerge =false;
//					}
				}
					
			}
			
			if(isShowMerge){
			
				if (room.getSerialNumber() != room.getEndSerialNumber()) {
					int columnOff = table.getColumnIndex(key + room.getSerialNumber());
					try {
						table.getMergeManager().mergeBlock(table.getRowCount() + minRow.intValue() - 1 - room.getFloor(), columnOff, table.getRowCount() + minRow.intValue() - 1 - room.getFloor(),
								columnOff + room.getEndSerialNumber() - room.getSerialNumber());
					} catch (Exception e) {
						// TODO Auto-generated catch block
						FDCMsgBox.showError("¥��: "+room.getBuilding().getName()+"�·������ :"+room.getName()+".\n�����������޸Ļ�ɾ���˷�����Ϣ��");
						
					}
				}
				if(room.getSub()!=null&&room.getSub().indexOf(",")>0){
					String[] sub=room.getSub().split(",");
					int columnOff = table.getColumnIndex(key + room.getSerialNumber());
					boolean isRow=true;
					int merger=sub.length;
					if (!sub[0].split(";")[0].equals(sub[1].split(";")[0])){
						isRow=false;
					}
					if(!isRow){
						if(room.getFloor()<0){
							table.getMergeManager().mergeBlock(table.getRowCount() + minRow.intValue() - 1 - room.getFloor()-merger, columnOff, table.getRowCount() + minRow.intValue() - 1 - room.getFloor(),columnOff);
						}else{
							table.getMergeManager().mergeBlock(table.getRowCount() + minRow.intValue() - 1 - room.getFloor()-merger+1, columnOff, table.getRowCount() + minRow.intValue() - 1 - room.getFloor(),columnOff);
						}
					}
				}
			}
			
		}
		
		/**
		 * 6������״̬Ϊ���ۣ��ж�Ӧ���Ϲ�������Ϊ����ֻ�������µ���Ч�Ϲ����������ж�Ӧ�Ϲ�������ʾΪ���ۡ�
		   ���Ǹ��ݿͻ������󣬴���һ�����������Ԥ�����롢Ԥ�����ˡ��Ϲ����롢�Ϲ������С��Ϲ�������5��״̬���Ϲ���û���տ��ʱ�򣬶�Ӧ����״̬û�ı��ʱ�򲻻���Ϲ�����������Ӧ�����ϡ�
		   ����������Ҫ������ڵ���������5��״̬���Ϲ���������Щ�Ϲ�����Ӧ�ķ���״̬��ʾΪ���ۣ�
		   ͬһ������ֻҪ��������5��״̬���Ϲ����������κ�һ�ֶ���ʾ�����ۡ�
		 */
		if(isVirtual){
			for (int i = 0; i < rooms.size(); i++) {
				RoomInfo room = rooms.get(i);
				if(roomPurSet.contains(String.valueOf(room.getId()))){
					String buildingId = room.getBuilding().getId().toString();
					// int unitNum =
					// room.getBuildUnit()==null?0:room.getBuildUnit().getSeq();
					int unitNum = room.getUnit();
					String key = buildingId + unitNum;
					for (int j = room.getSerialNumber(); j <= room.getEndSerialNumber(); j++) {
						ICell cell = table.getCell(table.getRowCount() + minRow.intValue() - 1 - room.getFloor(), key + j);
						if (cell == null)
							continue;
						cell.getStyleAttributes().setBackground(Color.ORANGE);
					}
				}
			}		
		}
		for (int i = 0; i < table.getRowCount(); i++) {
			for (int j = 0; j < table.getColumnCount(); j++) {
				ICell cell = table.getRow(i).getCell(j);
				if (cell.getUserObject() == null) {
					if (j == 0) {
						
						cell.getStyleAttributes().setBackground(new Color(184, 204, 221));
					} else {
						cell.getStyleAttributes().setBackground(Color.GRAY);
					}
				}
			}

		}
	}
	/**
	 * �ָ��ݾ���ķ���״̬�Ͷ�Ӧ���Ϲ�����Ϣ��ȷ��������ʾ���ۻ������ۡ�
	 *  1��	����״̬δ���̣���ʾΪ���ۡ�
		2��	����״̬Ϊ��������ʾΪ���ۡ�
		3��	����״̬Ϊ�����Ϲ�����ʾΪ���ۡ�
		4��	����״̬ΪԤ�����Ϲ���ǩԼ����ʾΪ���ۡ�
		5��	����״̬Ϊ���ۣ�û�ж�Ӧ���Ϲ�������ʾΪ���ۡ�
		6��	����״̬Ϊ���ۣ��ж�Ӧ���Ϲ�������Ϊ����ֻ�������µ���Ч�Ϲ����������ж�Ӧ�Ϲ�������ʾΪ���ۡ�
		   ���Ǹ��ݿͻ������󣬴���һ�����������Ԥ�����롢Ԥ�����ˡ��Ϲ����롢�Ϲ������С��Ϲ�������5��״̬���Ϲ���û���տ��ʱ�򣬶�Ӧ����״̬û�ı��ʱ�򲻻���Ϲ�����������Ӧ�����ϡ�
		   ����������Ҫ������ڵ���������5��״̬���Ϲ���������Щ�Ϲ�����Ӧ�ķ���״̬��ʾΪ���ۣ�
		   ͬһ������ֻҪ��������5��״̬���Ϲ����������κ�һ�ֶ���ʾ�����ۡ�
			by zgy 2010-12-16   
			Ŀǰ�Ϲ�����������״̬�����仯���ܲ������ƣ���¥�����Ϲ�ҵ����ʵ���տ�Ϊ׼��������״̬�����տ����
			�ֶ�����¥�̱���״̬�ĵ�������Ҫ�������£�
			 "�Ϲ�ҵ����ʵ���տ�Ϊ׼"��ѡ������£����ۡ�δ�۷�����ж���֮ǰһ�£������޸ģ�
			 "�Ϲ�ҵ����ʵ���տ�Ϊ׼"δ��ѡ�����ۡ�δ�۷����״̬�ж���Ҫ������
			���ۣ�����״̬Ϊ"Ԥ�����Ϲ���ǩԼ��δ���̡�����"��
			���ۣ�����״̬Ϊ"����,�����Ϲ�"��
			�޸������µ�ҵ����ƣ�����ԭ�п��ơ�
	 * @param room
	 * @param roomSetting
	 * @return
	 */
	public static Color getRoomColorByState(RoomInfo room, RoomDisplaySetting roomSetting) {
		Color retColor = Color.WHITE;
		//��ȡ�������Ŀ,��ʼ������״̬��ɫ��������¥���ò����Ϲ�ҵ����ʵ���տ�Ϊ׼�Ƿ�ѡ��
		//����ѡ����£���ԭҵ���߼����䣬�����ڣ��Ϲ��տ�״̬����Ϊ�Ϲ�״̬ by zgy 2010-12-15
		if(room.getBuilding()!=null&& room.getBuilding().getSellProject()!=null){
			String id = room.getBuilding().getSellProject().getId().toString();
			RoomDisplaySetting settingDisplay = roomSetting;
			HashMap functionSetMap = (HashMap)settingDisplay.getFunctionSetMap();
			FunctionSetting funcSet = (FunctionSetting)functionSetMap.get(id);
			if(funcSet!=null&&(!funcSet.getIsActGathering().booleanValue())){
				if (room.getSellState().equals(RoomSellStateEnum.OnShow)||room.getSellState().equals(RoomSellStateEnum.SincerPurchase)) {
					retColor = roomSetting.getBaseRoomSetting().getOnShowColor();
				} else {
					retColor = roomSetting.getBaseRoomSetting().getSignColor();
				}
			}else{
				//ԭҵ�����
//				System.out.println("room state:"+room.getName()+"==="+room.getSellState());
				if ((RoomSellStateEnum.Init).equals(room.getSellState())) {
					retColor = roomSetting.getBaseRoomSetting().getSignColor();
				} else if ((RoomSellStateEnum.KeepDown).equals(room.getSellState())) {
					retColor = roomSetting.getBaseRoomSetting().getSignColor();
				} else if ((RoomSellStateEnum.SincerPurchase).equals(room.getSellState())) {
					retColor = roomSetting.getBaseRoomSetting().getOnShowColor();
				} else if ((RoomSellStateEnum.PrePurchase).equals(room.getSellState())) {
					retColor = roomSetting.getBaseRoomSetting().getSignColor();
				} else if ((RoomSellStateEnum.Purchase).equals(room.getSellState())) {
					retColor = roomSetting.getBaseRoomSetting().getSignColor();
				} else if ((RoomSellStateEnum.Sign).equals(room.getSellState())) {
					retColor = roomSetting.getBaseRoomSetting().getSignColor();
				}else if ((RoomSellStateEnum.OnShow).equals(room.getSellState())) {
					retColor = roomSetting.getBaseRoomSetting().getOnShowColor();
				} else {
					retColor = roomSetting.getBaseRoomSetting().getSignColor();
				}
				//ԭҵ�����
			}
		}
		

		return retColor;
	}

	
	/**
	 * ��������ϵͳ�Ͳ��������Լ�����״̬��÷������ɫ
	 * 
	 * @throws BOSException 
	 * @throws EASBizException 
	 * <p>���޸� by tim_gao 2010-06-11
	 * <p>������ѡ������µ���ɫ��״̬�ж� 
	 */
	
	
	public static Color getRoomColorBySysForDes(RoomInfo room, MoneySysTypeEnum sysType, IDisplayRule setting) throws BOSException, EASBizException {
		Color retColor = Color.WHITE;
		//ѡ���� setting
		
		if (room == null)
			return retColor;
//		if (MoneySysTypeEnum.TenancySys.equals(sysType)) {
//			if (room.isIsForTen() == false) {
//				retColor = setting.getCellBackgroundColor("noTenancy");
//			} else {
//				// ���ݷ��� ���� ��״̬����䲻ͬ����ɫ
//				TenancyStateEnum tenancyStatEnum = room.getTenancyState();
//				String disKey = tenancyStatEnum == null ? null : tenancyStatEnum.getValue();
//				retColor = setting.getCellBackgroundColor(disKey);
//			}
//		} else 
			if (MoneySysTypeEnum.SalehouseSys.equals(sysType)) {
			if (room.isIsForSHE() == false) {
				retColor = setting.getCellBackgroundColor("noSellhouse");
				
			} else {
				RoomSellStateEnum state = room.getSellState();
				String disKey = state == null ? null : state.getValue();
				//��ȡ�������Ŀ,��ʼ������״̬��ɫ��������¥���ò����Ϲ�ҵ����ʵ���տ�Ϊ׼�Ƿ�ѡ��
				//����ѡ����£���ԭҵ���߼����䣬�������Ϲ��տ�״̬����Ϊ�Ϲ�״̬ by zgy 2010-12-15
				//������ѡ����£���ѯ�����Ƿ��տ�״̬Ϊ�Ƿ������һ����
				if(room.getBuilding()==null){
					SelectorItemCollection sels = new SelectorItemCollection();
					sels.add("building.*");
					sels.add("ReceiptTypeState");
					sels.add("SellState");
					sels.add("building.sellProject.*");
					try {
						room = RoomFactory.getRemoteInstance().getRoomInfo(new ObjectUuidPK(room.getId().toString()),sels);
					} catch (EASBizException e) {
						e.printStackTrace();
					} catch (BOSException e) {
						e.printStackTrace();
					}
				}
				
				retColor = setting.getCellBackgroundColor(disKey);
				
			}
		} else if (sysType == null) {
			if (room.isIsForSHE() && room.isIsForTen() == false) {
				retColor = Color.CYAN;
			}
			 else {
				
				retColor = Color.LIGHT_GRAY;
				
			}
		}
		if(isDes&&null!=room.getId()){
			retColor = Color.YELLOW;
		}
		return retColor;
	}
	
	public static Color getRoomColorBySys(RoomInfo room, MoneySysTypeEnum sysType, IDisplayRule setting) throws BOSException, EASBizException {
		ChooseRoomDisplaySetting chooseRoomSetting = new ChooseRoomDisplaySetting();
		return getRoomColorBySys(room, sysType, setting, chooseRoomSetting);
	}
	/**
	 * ��������ϵͳ�Ͳ��������Լ�����״̬��÷������ɫ
	 * 
	 * @throws BOSException 
	 * @throws EASBizException 
	 * <p>���޸� by tim_gao 2010-06-11
	 * <p>������ѡ������µ���ɫ��״̬�ж� 
	 */
	
	
	public static Color getRoomColorBySys(RoomInfo room, MoneySysTypeEnum sysType, IDisplayRule setting,ChooseRoomDisplaySetting chooseRoomSetting) throws BOSException, EASBizException {
		Color retColor = Color.WHITE;
		//ѡ���� setting
		//	ChooseRoomDisplaySetting chooseRoomSetting = new ChooseRoomDisplaySetting();
		if (room == null)
			return retColor;
		if (MoneySysTypeEnum.TenancySys.equals(sysType)) {
			if (room.isIsForTen() == false) {
				retColor = setting.getCellBackgroundColor("noTenancy");
			} else {
				// ���ݷ��� ���� ��״̬����䲻ͬ����ɫ
				TenancyStateEnum tenancyStatEnum = room.getTenancyState();
				String disKey = tenancyStatEnum == null ? null : tenancyStatEnum.getValue();
				retColor = setting.getCellBackgroundColor(disKey);
			}
		} else if (MoneySysTypeEnum.SalehouseSys.equals(sysType)) {
			if (room.isIsForSHE() == false) {
				retColor = setting.getCellBackgroundColor("noSellhouse");
				//ѡ�������
				if(isOpenedChooseRoom){
					retColor =chooseRoomSetting.getChooseRoomSelled();
				}
			} else {
				RoomSellStateEnum state = room.getSellState();
				String disKey = state == null ? null : state.getValue();
				//��ȡ�������Ŀ,��ʼ������״̬��ɫ��������¥���ò����Ϲ�ҵ����ʵ���տ�Ϊ׼�Ƿ�ѡ��
				//����ѡ����£���ԭҵ���߼����䣬�������Ϲ��տ�״̬����Ϊ�Ϲ�״̬ by zgy 2010-12-15
				//������ѡ����£���ѯ�����Ƿ��տ�״̬Ϊ�Ƿ������һ����
				if(room.getBuilding()==null){
					SelectorItemCollection sels = new SelectorItemCollection();
					sels.add("building.*");
					sels.add("ReceiptTypeState");
					sels.add("SellState");
					sels.add("building.sellProject.*");
					try {
						room = RoomFactory.getRemoteInstance().getRoomInfo(new ObjectUuidPK(room.getId().toString()),sels);
					} catch (EASBizException e) {
						e.printStackTrace();
					} catch (BOSException e) {
						e.printStackTrace();
					}
				}
				if(RoomSellStateEnum.Purchase.equals(room.getSellState())){
					if(room.getBuilding()!=null&&room.getBuilding().getSellProject()!=null){
						String id = room.getBuilding().getSellProject().getId().toString();
						RoomDisplaySetting settingDisplay = (RoomDisplaySetting)setting;
						HashMap functionSetMap = (HashMap)settingDisplay.getFunctionSetMap();
						FunctionSetting funcSet = (FunctionSetting)functionSetMap.get(id);
						if(funcSet!=null){
							if(!funcSet.getIsActGathering().booleanValue()){
								if(room.getReceiptTypeState()!=null&&room.getReceiptTypeState().equals(ReceiptTypeStateEnum.FirstRe)){
									disKey = ReceiptTypeStateEnum.FIRSTRE_VALUE ;
								}
							}else{
								if(room.getReceiptTypeState()!=null&&room.getReceiptTypeState().equals(ReceiptTypeStateEnum.FirstRe)){
									disKey = RoomSellStateEnum.PURCHASE_VALUE ;
								}
							}
						}
					}
				}
				if(disKey.equals(RoomSellStateEnum.KEEPDOWN_VALUE)){
					if(RoomKeepDownBillFactory.getRemoteInstance().exists("select id from where room.id='"+room.getId().toString()+"' and reason='"+RoomKeepDownReasonEnum.CONTROL_VALUE+"' and bizState!='"+RoomKeepDownBizEnum.CANCELKEEPDOWN_VALUE+"'")){
						retColor=setting.getCellBackgroundColor("CONTROL");
					}else{
						retColor = setting.getCellBackgroundColor(disKey);
					}
				}else{
					retColor = setting.getCellBackgroundColor(disKey);
				}
				
				//by tim_gao �Ǵ��ۺ�ѡ���ķ���Ҫ���Ƿ����ѡ�����Լ��Ƿ�ʧЧ�ж�
				//�������е����Գ������д�������������Ҫ�ĵط��������ˣ��Ѿ���Map��ID�����ˣ���֪����ʲô������
				if(isOpenedChooseRoom){
					
				if(disKey!=null && (disKey.equals(RoomSellStateEnum.ONSHOW_VALUE)||disKey.equals(RoomSellStateEnum.SINCERPURCHASE_VALUE))){
					
						if(null!=roomIdMap.get(room.getId())){
								Boolean flag = (Boolean)roomIdMap.get(room.getId());
							if(flag.booleanValue()){
								disKey = ChooseRoomStateEnum.AFFIRM_VALUE;
							}else{
								disKey = ChooseRoomStateEnum.UNCONFIRMED_VALUE;
							}
						}
							
					}
					retColor = chooseRoomSetting.getCellBackgroundColor(disKey);
				}
			}
		} else if (sysType == null) {
			if (room.isIsForSHE() && room.isIsForTen() == false) {
				retColor = Color.CYAN;
			} else if (room.isIsForTen() && room.isIsForSHE() == false) {
				retColor = Color.ORANGE;
			} else if (room.isIsForSHE() && room.isIsForTen()) {
				retColor = Color.PINK;
			} else {
				
				retColor = Color.LIGHT_GRAY;
				
			}
		}
		
		return retColor;
	}

	public static void fillRoomTableByCol(KDTable table, RoomCollection rooms, IDisplayRule setting) throws BOSException, EASBizException {
		table.getStyleAttributes().setLocked(true);
		table.getStyleAttributes().setHorizontalAlign(HorizontalAlignment.CENTER);
		table.getIndexColumn().getStyleAttributes().setHided(true);
		table.removeColumns();
		table.removeHeadRows();
		table.removeRows();
		int minRow = 9999;
		int maxRow = -1;
		int minCol = 9999;
		int maxCol = -1;
		String key = null;
		for (int i = 0; i < rooms.size(); i++) {
			RoomInfo room = rooms.get(i);
			String buildingId = room.getBuilding().getId().toString();
			// int unitNum =
			// room.getBuildUnit()==null?0:room.getBuildUnit().getSeq();
			int unitNum = room.getUnit();
			if (key == null) {
				key = buildingId + unitNum;
			}
			if (room.getFloor() < minRow) {
				minRow = room.getFloor();
			}
			if (room.getFloor() > maxRow) {
				maxRow = room.getFloor();
			}
			if (room.getSerialNumber() < minCol) {
				minCol = room.getSerialNumber();
			}
			if (room.getEndSerialNumber() > maxCol) {
				maxCol = room.getEndSerialNumber();
			}
		}
		if (minRow <= maxRow && minCol <= maxCol) {
			IColumn column = table.addColumn();
			column.setWidth(30);
			column.setKey(0 + "");
			for (int i = minCol; i <= maxCol; i++) {
				column = table.addColumn();
				column.setWidth(setting.getRoomWidth());
				column.getStyleAttributes().setWrapText(true);
				column.setKey(key + i);
			}
			IRow headRow = table.addHeadRow();
			headRow.getCell(0 + "").setValue("¥��");
			for (int i = minCol; i <= maxCol; i++) {
				headRow.getCell(key + i).setValue(i + "��");
			}
			for (int i = minRow; i <= maxRow; i++) {
				IRow row = table.addRow();
				row.setUserObject(new Integer(i));
				row.setHeight(setting.getRoomHeight());
				row.getCell(0 + "").setValue(new Integer(maxRow - i + minRow) + "��");
			}
		}
		fillRoomTable(table, rooms, setting, null, null, false,null);
	}

	public static RoomInfo createBaseRoom(RoomDesInfo roomDes) {
		RoomInfo room = new RoomInfo();
		room.setBuilding(roomDes.getBuilding());
		room.setBuildingArea(roomDes.getBuildingArea());
		room.setSaleArea(roomDes.getBuildingArea());
		room.setApportionArea(roomDes.getApportionArea());
		room.setBalconyArea(roomDes.getBalconyArea());
		room.setRoomArea(roomDes.getRoomArea());
		
		room.setRoomModel(roomDes.getRoomModel());
		room.setFloorHeight(roomDes.getFloorHeight());
		room.setBuildingProperty(roomDes.getBuildingProperty());
		
	
		 
		room.setSellState(RoomSellStateEnum.Init);
		room.setRoomJoinState(RoomJoinStateEnum.NOTJOIN);
		room.setRoomBookState(RoomBookStateEnum.NOTBOOKED);
		room.setRoomCompensateState(RoomCompensateStateEnum.NOCOMPENSATE);
		room.setRoomLoanState(RoomLoanStateEnum.NOTLOANED);
		room.setRoomACCFundState(RoomACCFundStateEnum.NOTFUND);
		
		
		
	
	
		
		room.setHouseProperty(HousePropertyEnum.NoAttachment);
		
		room.setBuilding(roomDes.getBuilding());
		room.setBuildingArea(roomDes.getBuildingArea());
		room.setSaleArea(roomDes.getBuildingArea());
		room.setApportionArea(roomDes.getApportionArea());
		room.setBalconyArea(roomDes.getBalconyArea());
		room.setRoomArea(roomDes.getRoomArea());
		room.setApportionArea(roomDes.getApportionArea());
		room.setBalconyArea(roomDes.getBalconyArea());
		room.setFloorHeight(roomDes.getFloorHeight());
		room.setRoomModel(roomDes.getRoomModel());
		room.setBuildingProperty(roomDes.getBuildingProperty());
		room.setActualBuildingArea(roomDes.getActualBuildingArea());
		room.setActualRoomArea(roomDes.getActualRoomArea());
		room.setDirection(roomDes.getDirection());
		room.setProductType(roomDes.getProductType());
		room.setSight(roomDes.getSight());
		room.setIsForSHE(roomDes.isIsForSHE());
		room.setIsForTen(roomDes.isIsForTen());
		room.setIsForPPM(roomDes.isIsForPPM());
		room.setNoise(roomDes.getNoise());
		room.setDecoraStd(roomDes.getDecoraStd());
		room.setEyeSignht(roomDes.getEyeSignht());
		room.setPosseStd(roomDes.getPosseStd());
		room.setRoomUsage(roomDes.getRoomUsage());
		room.setFlatArea(roomDes.getFlatArea());
		room.setCarbarnArea(roomDes.getCarbarnArea());
		room.setPlanBuildingArea(roomDes.getPlanBuildingArea());
		room.setPlanRoomArea(roomDes.getPlanRoomArea());
		room.setUseArea(roomDes.getUserArea());
		room.setGuardenArea(roomDes.getGuardenArea());
		room.setBuilStruct(roomDes.getBuildStruct());
		//ҵ�����ڲ�ȷ�������� bizDate
		room.setNewDecorastd(roomDes.getNewDecorastd());
		room.setNewEyeSight(roomDes.getNewEyeSight());
		room.setNewNoise(roomDes.getNewNoise());
		room.setNewPossStd(roomDes.getNewPossstd());
		room.setNewProduceRemark(roomDes.getNewProduceRemark());
		room.setNewRoomUsage(roomDes.getNewRoomUsage());
		room.setNewSight(roomDes.getNewSight());
		
		room.setIsPlan(false);
		room.setIsPre(false);
		room.setIsActualAreaAudited(false);
		//�����ӷ�������������Ҫ��״̬
		room.setIsActAudited(false);
		room.setIsAreaAudited(false);
		room.setIsPlanAudited(false);
		room.setIsPreAudited(false);
		room.setIsChangeSellArea(false);
		
		room.setPlanChangeState(RoomPlanChangeStateEnum.NOCHANCE);
		room.setPreChangeState(RoomPreChangeStateEnum.NOCHANGE);
		room.setActChangeState(RoomActChangeStateEnum.NOCHANGE);
		
		//���ӷ����������״̬ by renliang at 2011-8-18
		room.setSellAreaType(SellAreaTypeEnum.PLANNING);
		return room;
	}

	private static Map getFloorAlias(BuildingInfo building) {
		Map map = new HashMap();
		if (building == null || building.getId() == null)
			return map;

		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		view.setFilter(filter);

		filter.getFilterItems().add(new FilterItemInfo("building.id", building.getId().toString()));

		BuildingFloorEntryCollection coll = null;
		try {
			coll = BuildingFloorEntryFactory.getRemoteInstance().getBuildingFloorEntryCollection(view);
		} catch (BOSException e) {
			ExceptionHandler.handle(e);
		}
		for (int i = 0; i < coll.size(); i++) {
			map.put(new Integer(coll.get(i).getFloor()), coll.get(i));
		}
		return map;
	}

	/**
	 * ����ĳ��¥���µķ������� ���ɷ��伯��
	 * 
	 * @param buildingId
	 * @return
	 * @throws BOSException
	 */
	public static RoomCollection getRoomsByDes(String buildingId) throws BOSException {
		EntityViewInfo view = new EntityViewInfo();
		view.getSelector().add("*");
		view.getSelector().add("buildingProperty.*");
		view.getSelector().add("roomModel.*");
		view.getSelector().add("direction.*");
		view.getSelector().add("sight.*");
		view.getSelector().add("building.name");
		view.getSelector().add("building.number");
		view.getSelector().add("building.codingType");
		view.getSelector().add("productType.*");
		view.getSelector().add("building.sellProject.projectResource");
		view.getSelector().add("noise.*");
		view.getSelector().add("decoraStd.*");
		view.getSelector().add("eyeSignht.*");
		view.getSelector().add("posseStd.*");
		view.getSelector().add("roomUsage.*");
		FilterInfo filter = new FilterInfo();
		view.setFilter(filter);
		filter.getFilterItems().add(new FilterItemInfo("building.id", buildingId));
		RoomDesCollection dess = RoomDesFactory.getRemoteInstance().getRoomDesCollection(view);
		RoomCollection rooms = new RoomCollection();
		Timestamp currentTime = new Timestamp(new Date().getTime());

		try {
			// currentTime = FDCCommonServerHelper.getServerTimeStamp();
			currentTime = FDCSQLFacadeFactory.getRemoteInstance().getServerTime();// Ǩ��֧��Ҫ
																					// ��
																					// �����������ǻ�ȡ������ʱ��
		} catch (BOSException e) {
			ExceptionHandler.handle(e);
			SysUtil.abort();
		}
		UserInfo currentUser = SysContext.getSysContext().getCurrentUserInfo();
		CtrlUnitInfo currentCU = SysContext.getSysContext().getCurrentCtrlUnit();

		BuildingInfo buildInfo = null;
		try {
			buildInfo = BuildingFactory.getRemoteInstance().getBuildingInfo(
					"select name,unitCount,units.*,sellProject.name,subarea.name where id='" + buildingId + "'");
		} catch (EASBizException e) {
			e.printStackTrace();
		}
		//��ʼ��װ�����name�ֶ� ��������Ŀ-����-¥��-��Ԫ-���š�
		StringBuffer longName = new StringBuffer(); 
		longName.append(buildInfo.getSellProject().getName());
		if(buildInfo.getSubarea()!=null) {
			longName.append("-" + buildInfo.getSubarea().getName());
		}
		if(buildInfo.getName()!=null){
			longName.append( "-" + buildInfo.getName());
		}
		
		
		
		for (int i = 0; i < dess.size(); i++) {
			RoomDesInfo des = dess.get(i);

			Map map = getFloorAlias(des.getBuilding());

			CodingTypeEnum codingType = des.getBuilding().getCodingType();
			BuildingInfo building = des.getBuilding();
			RoomNumPrefixEnum numPrefix = des.getNumPrefixType();
			if (des.getUnitBegin() == 0) {
				// ����¥��Ϊ 0 ���������⴦��
				if (/* des.getFloorEnd() == 0 */false) {
					for (int num = des.getSerialNumberBegin(); num <= des.getSerialNumberEnd(); num++) {
						RoomInfo room = createBaseRoom(des);
						room.setFloor(des.getFloorBegin());
						room.setUnit(0);
						room.setSerialNumber(num);
						room.setEndSerialNumber(num);
						String strNum = getSerialNumberStr(num, des.isIsConvertToChar());
						room.setCreateTime(currentTime);
						room.setLastUpdateTime(currentTime);
						room.setCreator(currentUser);
						room.setLastUpdateUser(currentUser);
						room.setCU(currentCU);
//						room.setNumber(getRoomNumIncludeBuildPrefix(strNum, building, numPrefix));
//						room.setRoomPropNo(room.getNumber());
//						room.setDisplayName(room.getNumber());
						//2011.04.25�޸�  ʹname��number��Ϊ�����룬displaynameΪ�̱���
						room.setDisplayName(getRoomNumIncludeBuildPrefix(strNum, building, numPrefix));
						room.setRoomPropNo(room.getDisplayName());
						room.setName(longName+"-"+room.getDisplayName());
						room.setNumber(room.getName());
						
						rooms.add(room);
					}
				} else {
					for (int floor = des.getFloorBegin(); floor <= des.getFloorEnd(); floor++) {
						// ����� 0 �㣬������ȥ�������ɷ���
						if (floor == 0)
							continue;

						for (int num = des.getSerialNumberBegin(); num <= des.getSerialNumberEnd(); num++) {
							RoomInfo room = createBaseRoom(des);
							room.setFloor(floor);
							room.setUnit(0);
							room.setSerialNumber(num);
							room.setEndSerialNumber(num);
							String strNum = getSerialNumberStr(num, des.isIsConvertToChar());
							if (CodingTypeEnum.Num.equals(codingType)) {
								//2011.04.25�޸�  ʹname��number��Ϊ�����룬displaynameΪ�̱���
//								room.setNumber(getRoomNumIncludeBuildPrefix(strNum, building, numPrefix));
								room.setDisplayName(getRoomNumIncludeBuildPrefix(strNum, building, numPrefix));
							} else {

								// ��¥��������ɷ������
								if (map.get(new Integer(floor)) != null) {
									BuildingFloorEntryInfo info = (BuildingFloorEntryInfo) map.get(new Integer(floor));
									//2011.04.25�޸�  ʹname��number��Ϊ�����룬displaynameΪ�̱���
//									room.setNumber(getRoomNumIncludeBuildPrefix(info.getFloorAlias() + "" + strNum, building, numPrefix));
									room.setDisplayName(getRoomNumIncludeBuildPrefix(info.getFloorAlias() + "" + strNum, building, numPrefix));
									room.setBuildingFloor(info);
								} else {
									//2011.04.25�޸�  ʹname��number��Ϊ�����룬displaynameΪ�̱���
//									room.setNumber(getRoomNumIncludeBuildPrefix(floor + "" + strNum, building, numPrefix));
									room.setDisplayName(getRoomNumIncludeBuildPrefix(floor + "" + strNum, building, numPrefix));
								}
							}
							room.setCreateTime(currentTime);
							room.setLastUpdateTime(currentTime);
							room.setCreator(currentUser);
							room.setLastUpdateUser(currentUser);
							room.setCU(currentCU);
							room.setRoomPropNo(room.getDisplayName());
//							room.setDisplayName(room.getNumber());
							room.setName(longName+"-"+room.getDisplayName().toString());
							room.setNumber(room.getName());
							
							rooms.add(room);
						}
					}
				}
			} else {
				for (int unit = des.getUnitBegin(); unit <= des.getUnitEnd(); unit++) {
					for (int floor = des.getFloorBegin(); floor <= des.getFloorEnd(); floor++) {
						// ����� 0 �㣬������ȥ�������ɷ���
						if (floor == 0)
							continue;

						for (int num = des.getSerialNumberBegin(); num <= des.getSerialNumberEnd(); num++) {
							RoomInfo room = createBaseRoom(des);
							BuildingUnitInfo unitInfo = getUnitInfoByBuildingUnitColl(buildInfo.getUnits(), unit);
							if (unitInfo != null) {
								room.setBuildUnit(unitInfo);
								room.setUnit(unitInfo.getSeq());
							}
							room.setUnit(unit);
							room.setFloor(floor);
							room.setSerialNumber(num);
							room.setEndSerialNumber(num);
							String strNum = getSerialNumberStr(num, des.isIsConvertToChar());

							// ��¥��������ɷ������
							if (map.get(new Integer(floor)) != null) {
								BuildingFloorEntryInfo info = (BuildingFloorEntryInfo) map.get(new Integer(floor));
								//2011.04.25�޸�  ʹname��number��Ϊ�����룬displaynameΪ�̱���
//								room.setNumber(getRoomNumIncludeBuildPrefix(unit + "-" + info.getFloorAlias() + "" + strNum, building, numPrefix));
								room.setDisplayName(getRoomNumIncludeBuildPrefix(unit + "-" + info.getFloorAlias() + "" + strNum, building, numPrefix));
								room.setBuildingFloor(info);
							} else {
								//2011.04.25�޸�  ʹname��number��Ϊ�����룬displaynameΪ�̱���
//								room.setNumber(getRoomNumIncludeBuildPrefix(unit + "-" + floor + "" + strNum, building, numPrefix));
								room.setDisplayName(getRoomNumIncludeBuildPrefix(unit + "-" + floor + "" + strNum, building, numPrefix));
							}
							room.setCreateTime(currentTime);
							room.setLastUpdateTime(currentTime);
							room.setCreator(currentUser);
							room.setLastUpdateUser(currentUser);
							room.setCU(currentCU);
							room.setRoomPropNo(room.getDisplayName());
							if(unitInfo!=null)
								room.setName(longName+"-"+unitInfo.getName()+"-"+room.getDisplayName());
							else
								room.setName(longName+"-"+room.getDisplayName());
							room.setNumber(room.getName());
							rooms.add(room);
						}
					}
				}
			}
		}
		return rooms;
	}
	
	public static SelectorItemCollection getRoomSelector(SelectorItemCollection sele ){
		SelectorItemCollection sel = sele;
		
		sel.add("building.*");
//		sel.add("building.name");
//		sel.add("building.number");
//		sel.add("building.codingType");
//		sel.add("building.sellProject.*");
//		sel.add("building.sellProject.name");
//		sel.add("building.sellProject.number");
////		view.getSelector().add("productType.*");
//		sel.add("building.sellProject.projectResource");
//		view.getSelector().add("noise.*");
//		view.getSelector().add("decoraStd.*");
//		view.getSelector().add("eyeSignht.*");
//		view.getSelector().add("posseStd.*");
//		view.getSelector().add("roomUsage.*");
//		view.getSelector().add("unit.*");
//		view.getSelector().add("unit");
		sel.add("name");
		sel.add("number");
//		sel.add("unitBegin");
//		sel.add("unitEnd");
		sel.add("floor");
		sel.add("unit");
//		sel.add("floorEnd");
//		sel.add("natrueFloorBegin");
//		sel.add("natrueFloorEnd");
		sel.add("serialNumber");
		sel.add("endSerialNumber");
		sel.add("buildingArea");
		sel.add("roomArea");
		sel.add("apportionArea");
		sel.add("displayName");
		
		sel.add("floorHeight");
		sel.add("roomModel.*");
		sel.add("buildingProperty.*");
		sel.add("houseProperty");
		sel.add("isConvertToChar");
		sel.add("actualBuildingArea");
		sel.add("actualRoomArea");
		sel.add("direction.*");
		sel.add("sight.*");
//		sel.add("numPrefixType");
		sel.add("productType.*");
		sel.add("isForSHE");
		sel.add("isForTen");
		sel.add("isForPPM");
		sel.add("noise.*");
		sel.add("decoraStd.*");
		sel.add("eyeSignht.*");
		sel.add("posseStd.*");
		sel.add("roomUsage.*");
		sel.add("flatArea");
		sel.add("carbarnArea");
		sel.add("planRoomArea");
		sel.add("planBuildingArea");
		sel.add("useArea");
		sel.add("guardenArea");
		sel.add("builStruct.*");
		sel.add("bizDate");
		sel.add("newNoise.*");
		sel.add("newSight.*");
		sel.add("newEyeSight.*");
		sel.add("newDecorastd.*");
		sel.add("newPossstd.*");
		sel.add("newRoomUsage.*");
		sel.add("balconyArea");
		sel.add("newProduceRemark.*");
		sel.add("buildUnit.*");
		sel.add("roomCertifiName");
		
		return sel;
	}

	/**
	 * ����ĳ��¥���µķ������� ���ɷ��伯��
	 * 
	 * @param buildingId
	 * @return
	 * @throws BOSException
	 */
	public static RoomCollection getRoomsByDesForCreate(String buildingId) throws BOSException {
		EntityViewInfo view = new EntityViewInfo();
//		view.getSelector().add("*");
//		view.getSelector().add("buildingProperty.*");
//		view.getSelector().add("roomModel.*");
//		view.getSelector().add("direction.*");
//		view.getSelector().add("sight.*");
		view.getSelector().add("building.*");
		view.getSelector().add("building.name");
		view.getSelector().add("building.number");
		view.getSelector().add("building.codingType");
		view.getSelector().add("building.sellProject.id");
		view.getSelector().add("building.sellProject.name");
		view.getSelector().add("building.sellProject.number");
//		view.getSelector().add("productType.*");
		view.getSelector().add("building.sellProject.projectResource");
//		view.getSelector().add("noise.*");
//		view.getSelector().add("decoraStd.*");
//		view.getSelector().add("eyeSignht.*");
//		view.getSelector().add("posseStd.*");
//		view.getSelector().add("roomUsage.*");
//		view.getSelector().add("unit.*");
//		view.getSelector().add("unit");
		
		view.getSelector().add("unitBegin");
		view.getSelector().add("unitEnd");
		view.getSelector().add("floorBegin");
		view.getSelector().add("floorEnd");
		view.getSelector().add("natrueFloorBegin");
		view.getSelector().add("natrueFloorEnd");
		view.getSelector().add("serialNumberBegin");
		view.getSelector().add("serialNumberEnd");
		view.getSelector().add("buildingArea");
		view.getSelector().add("roomArea");
		view.getSelector().add("apportionArea");
		view.getSelector().add("balconyArea");
		view.getSelector().add("floorHeight");
		view.getSelector().add("roomModel.*");
		view.getSelector().add("buildingProperty.*");
		view.getSelector().add("houseProperty");
		view.getSelector().add("isConvertToChar");
		view.getSelector().add("actualBuildingArea");
		view.getSelector().add("actualRoomArea");
		view.getSelector().add("direction.*");
		view.getSelector().add("sight.*");
		view.getSelector().add("numPrefixType");
		view.getSelector().add("productType.*");
		view.getSelector().add("isForSHE");
		view.getSelector().add("isForTen");
		view.getSelector().add("isForPPM");
		view.getSelector().add("noise.*");
		view.getSelector().add("decoraStd.*");
		view.getSelector().add("eyeSignht.*");
		view.getSelector().add("posseStd.*");
		view.getSelector().add("roomUsage.*");
		view.getSelector().add("flatArea");
		view.getSelector().add("carbarnArea");
		view.getSelector().add("planRoomArea");
		view.getSelector().add("planBuildingArea");
		view.getSelector().add("userArea");
		view.getSelector().add("guardenArea");
		view.getSelector().add("buildStruct.*");
		view.getSelector().add("bizDate");
		view.getSelector().add("newNoise.*");
		view.getSelector().add("newSight.*");
		view.getSelector().add("newEyeSight.*");
		view.getSelector().add("newDecorastd.*");
		view.getSelector().add("newPossstd.*");
		view.getSelector().add("newRoomUsage.*");
		view.getSelector().add("newProduceRemark.*");
		view.getSelector().add("unit.*");
		//����ȡ������¼��Ϣ
//		view.getSelector().add("building.units.*");
		
		FilterInfo filter = new FilterInfo();
		view.setFilter(filter);
		filter.getFilterItems().add(new FilterItemInfo("building.id", buildingId));
		RoomDesCollection dess = RoomDesFactory.getRemoteInstance().getRoomDesCollection(view);
		RoomCollection rooms = new RoomCollection();
		Timestamp currentTime = new Timestamp(new Date().getTime());

		try {
			// currentTime = FDCCommonServerHelper.getServerTimeStamp();
			currentTime = FDCSQLFacadeFactory.getRemoteInstance().getServerTime();// Ǩ��֧��Ҫ
																					// ��
																					// �����������ǻ�ȡ������ʱ��
		} catch (BOSException e) {
			ExceptionHandler.handle(e);
			SysUtil.abort();
		}
		UserInfo currentUser = SysContext.getSysContext().getCurrentUserInfo();
		CtrlUnitInfo currentCU = SysContext.getSysContext().getCurrentCtrlUnit();

		BuildingInfo buildInfo = null;
		try {
			buildInfo = BuildingFactory.getRemoteInstance().getBuildingInfo(
					"select name,unitCount,units.*,sellProject.name,subarea.name,floorHeight,productType.*,buildingStructure.*,buildingProperty.* where id='" + buildingId + "'");
		} catch (EASBizException e) {
			e.printStackTrace();
		}
		//��ʼ��װ�����name�ֶ� ��������Ŀ-����-¥��-��Ԫ-���š�
		StringBuffer longName = new StringBuffer(); 
		longName.append(buildInfo.getSellProject().getName());
		if(buildInfo.getSubarea()!=null) {
			longName.append("-" + buildInfo.getSubarea().getName());
		}
		if(buildInfo.getName()!=null){
			longName.append( "-" + buildInfo.getName());
		}
		
		
		
		for (int i = 0; i < dess.size(); i++) {
			RoomDesInfo des = dess.get(i);

			Map map = getFloorAlias(des.getBuilding());

			CodingTypeEnum codingType = des.getBuilding().getCodingType();
			BuildingInfo building = des.getBuilding();
			RoomNumPrefixEnum numPrefix = des.getNumPrefixType();
			//����������û�е�Ԫ
//			if (des.getUnitBegin() == 0) {
//				// ����¥��Ϊ 0 ���������⴦��
//				if (/* des.getFloorEnd() == 0 */false) {
//					for (int num = des.getSerialNumberBegin(); num <= des.getSerialNumberEnd(); num++) {
//						RoomInfo room = createBaseRoom(des);
//						room.setFloor(des.getFloorBegin());
//						room.setUnit(0);
//						room.setSerialNumber(num);
//						room.setEndSerialNumber(num);
//						String strNum = getSerialNumberStr(num, des.isIsConvertToChar());
//						room.setCreateTime(currentTime);
//						room.setLastUpdateTime(currentTime);
//						room.setCreator(currentUser);
//						room.setLastUpdateUser(currentUser);
//						room.setCU(currentCU);
////						room.setNumber(getRoomNumIncludeBuildPrefix(strNum, building, numPrefix));
////						room.setRoomPropNo(room.getNumber());
////						room.setDisplayName(room.getNumber());
//						//2011.04.25�޸�  ʹname��number��Ϊ�����룬displaynameΪ�̱���
//						room.setDisplayName(getRoomNumIncludeBuildPrefix(strNum, building, numPrefix));
//						room.setRoomPropNo(room.getDisplayName());
//						room.setName(longName+"-"+room.getDisplayName());
//						room.setNumber(room.getName());
//						
//						rooms.add(room);
//					}
//				} 
//				else {
//					for (int floor = des.getFloorBegin(); floor <= des.getFloorEnd(); floor++) {
//						// ����� 0 �㣬������ȥ�������ɷ���
//						if (floor == 0)
//							continue;
//
//						for (int num = des.getSerialNumberBegin(); num <= des.getSerialNumberEnd(); num++) {
//							RoomInfo room = createBaseRoom(des);
//							room.setFloor(floor);
//							room.setUnit(0);
//							room.setSerialNumber(num);
//							room.setEndSerialNumber(num);
//							String strNum = getSerialNumberStr(num, des.isIsConvertToChar());
//							if (CodingTypeEnum.Num.equals(codingType)) {
//								//2011.04.25�޸�  ʹname��number��Ϊ�����룬displaynameΪ�̱���
////								room.setNumber(getRoomNumIncludeBuildPrefix(strNum, building, numPrefix));
//								room.setDisplayName(getRoomNumIncludeBuildPrefix(strNum, building, numPrefix));
//							} else {
//
//								// ��¥��������ɷ������
//								if (map.get(new Integer(floor)) != null) {
//									BuildingFloorEntryInfo info = (BuildingFloorEntryInfo) map.get(new Integer(floor));
//									//2011.04.25�޸�  ʹname��number��Ϊ�����룬displaynameΪ�̱���
////									room.setNumber(getRoomNumIncludeBuildPrefix(info.getFloorAlias() + "" + strNum, building, numPrefix));
//									room.setDisplayName(getRoomNumIncludeBuildPrefix(info.getFloorAlias() + "" + strNum, building, numPrefix));
//									room.setBuildingFloor(info);
//								} else {
//									//2011.04.25�޸�  ʹname��number��Ϊ�����룬displaynameΪ�̱���
////									room.setNumber(getRoomNumIncludeBuildPrefix(floor + "" + strNum, building, numPrefix));
//									room.setDisplayName(getRoomNumIncludeBuildPrefix(floor + "" + strNum, building, numPrefix));
//								}
//							}
//							room.setCreateTime(currentTime);
//							room.setLastUpdateTime(currentTime);
//							room.setCreator(currentUser);
//							room.setLastUpdateUser(currentUser);
//							room.setCU(currentCU);
//							room.setRoomPropNo(room.getDisplayName());
////							room.setDisplayName(room.getNumber());
//							room.setName(longName+"-"+room.getDisplayName().toString());
//							room.setNumber(room.getName());
//							
//							rooms.add(room);
//						}
//					}
//				}
//			}
//			else {
			if(null!=des.getUnit()){
				for (int unit = des.getUnitBegin(); unit <= des.getUnitEnd(); unit++) {
					for (int floor = des.getFloorBegin(); floor <= des.getFloorEnd(); floor++) {
						// ����� 0 �㣬������ȥ�������ɷ���
						if (floor == 0){
							continue;
						}
						for (int num = des.getSerialNumberBegin(); num <= des.getSerialNumberEnd(); num++) {
							RoomInfo room = createBaseRoom(des);
							BuildingUnitInfo unitInfo = getUnitInfoByBuildingUnitColl(buildInfo.getUnits(), unit);
							if (unitInfo != null) {
								room.setBuildUnit(unitInfo);
								room.setUnit(unitInfo.getSeq());
							}
							room.setUnit(unit);
							room.setFloor(floor);
							room.setSerialNumber(num);
							room.setEndSerialNumber(num);
							String strNum = getSerialNumberStr(num, des.isIsConvertToChar());

							// ��¥��������ɷ������
							if (map.get(new Integer(floor)) != null) {
								BuildingFloorEntryInfo info = (BuildingFloorEntryInfo) map.get(new Integer(floor));
								//2011.04.25�޸�  ʹname��number��Ϊ�����룬displaynameΪ�̱���
//								room.setNumber(getRoomNumIncludeBuildPrefix(unit + "-" + info.getFloorAlias() + "" + strNum, building, numPrefix));
								room.setDisplayName(getRoomNumIncludeBuildPrefix(info.getFloorAlias() + "" + strNum, building, numPrefix));
								room.setBuildingFloor(info);
							} else {
								//2011.04.25�޸�  ʹname��number��Ϊ�����룬displaynameΪ�̱���
								room.setNumber(getRoomNumIncludeBuildPrefix(unit + "-" + floor + "" + strNum, building, numPrefix));
								room.setDisplayName(getRoomNumIncludeBuildPrefix(floor + "" + strNum, building, numPrefix));
							}

//		    				String floors = String.valueOf(room.getFloor());
//		    				room.setFloor(room.getFloor()+1);
//							room.setUnit(0);
//						
//							NumberFormat nf = NumberFormat.getInstance();
//					        //�����Ƿ�ʹ�÷���
//					        nf.setGroupingUsed(false);
//					        //�����������λ��
//					        int len = 2;
//					       if(String.valueOf(room.getSerialNumber()).length()>2){
//					    	   len = String.valueOf(room.getSerialNumber()).length();
//					       }
//					        nf.setMaximumIntegerDigits(len);
//					        //������С����λ��    
//					        nf.setMinimumIntegerDigits(len);
//							String colum = nf.format(i);
//							room.setDisplayName(floors+colum);
//							//����¥��������
//							room.setBuildingProperty(buildInfo.getBuildingProperty());
//							room.setBuilStruct(buildInfo.getBuildingStructure());
//							room.setProductType(buildInfo.getProductType());
							room.setFloorHeight(buildInfo.getFloorHeight());
							room.setCreateTime(currentTime);
							room.setLastUpdateTime(currentTime);
							room.setCreator(currentUser);
							room.setLastUpdateUser(currentUser);
							room.setCU(currentCU);
							room.setRoomPropNo(room.getDisplayName());
							if(unitInfo!=null && unitInfo.getName()!=null)
								room.setName(longName+"-"+unitInfo.getName()+"-"+room.getDisplayName());
							else
								room.setName(longName+"-"+room.getDisplayName());
//							room.setName(floors+colum);
//							room.setNumber(floors+colum);
							room.setNumber(room.getName());
							rooms.add(room);
						}
					}
				}
			}
		else{
			FDCMsgBox.showWarning("��Ԫ��Ϣ�������󵼣�");
			SysUtil.abort();
		}
		}
		//}
		return rooms;
	}
	
	/** ���ݵ�Ԫ��ţ��ӵ�Ԫ��¼�л�õ�Ԫ��Ϣ */
	public static BuildingUnitInfo getUnitInfoByBuildingUnitColl(BuildingUnitCollection unitColl, int unitNum) {
		if (unitColl == null || unitColl.size() == 0)
			return null;
		for (int i = 0; i < unitColl.size(); i++) {
			if (unitColl.get(i).getSeq() == unitNum)
				return unitColl.get(i);

		}
		return null;
	}

	/**
	 * ��ü���¥��ǰ׺��ķ������
	 * 
	 * @param strNum
	 *            ����¥��ǰ׺ǰ�ķ������
	 * @param building
	 *            ¥��
	 * @param numPrefixType
	 *            ¥������ǰ׺����֯��ʽ
	 * @return ����¥��ǰ׺��ķ������
	 * */
	private static String getRoomNumIncludeBuildPrefix(String strNum, BuildingInfo building, RoomNumPrefixEnum numPrefixType) {
		String res = "";
		if (RoomNumPrefixEnum.buildNumPrefix.equals(numPrefixType)) {
			String buildNum = building.getNumber();
			if (buildNum != null) {
				res = buildNum + "-" + strNum;
			} else {
				log.warn("roomDes's buildingNum is null.check it carefully.");
			}
		} else if (RoomNumPrefixEnum.buildNamePrefix.equals(numPrefixType)) {
			String buildName = building.getName();
			if (buildName != null) {
				res = buildName + "-" + strNum;
			} else {
				log.warn("roomDes's buildingName is null.check it carefully.");
			}
		} else {
			res = strNum;
		}
		return res;
	}
	/**
	 * @author tim_gao
	 * @param num
	 * @param isConvertToChar
	 * @return
	 * <p>��ĸֻ��26��������26ʱ�������
	 */
	public static String getSerialNumberStr(int num, boolean isConvertToChar) {
		String strNum = "";
		if (num < 27 && num > 0) {
			if (isConvertToChar) {
				char a = 'A';
				a += (num - 1);
				strNum += a;
			} else {
				strNum = num + "";
				if (num < 10) {
					strNum = "0" + strNum;
				}
			}
		} else if (num == 0) {
			strNum = "0";
		}

		else if (num > -27 && num < 0) {
			num = num * -1;
			if (isConvertToChar) {
				char a = 'A';
				a += (num - 1);
				strNum += "-" + a;
			} else {
				strNum += num;
				if (num <10) {
					strNum = "-0" + strNum;
				}
			}
		} else if (num > 27 || num < -27) {
			strNum += num;
		}
		return strNum;
	}

	// public static RoomSignContractInfo getRoomSignContract(RoomInfo room) {
	// if (!room.getSellState().equals(RoomSellStateEnum.PrePurchase)
	// && !room.getSellState().equals(RoomSellStateEnum.Purchase)
	// && !room.getSellState().equals(RoomSellStateEnum.Sign)) {
	// return null;
	// }
	// if (room.getHouseProperty().equals(HousePropertyEnum.Attachment)) {
	// return null;
	// }
	// EntityViewInfo view = new EntityViewInfo();
	// FilterInfo filter = new FilterInfo();
	// filter.getFilterItems().add(
	// new FilterItemInfo("room.id", room.getId().toString()));
	// filter.getFilterItems().add(
	// new FilterItemInfo("isBlankOut", Boolean.FALSE));
	// view.setFilter(filter);
	// RoomSignContractCollection signs = null;
	// try {
	// signs = RoomSignContractFactory.getRemoteInstance()
	// .getRoomSignContractCollection(view);
	// } catch (BOSException e) {
	// e.printStackTrace();
	// }
	// if (signs.size() > 1) {
	// MsgBox.showInfo("�����ЧǩԼ��ͬ,ϵͳ����!");
	// SysUtil.abort();
	// }
	// if (signs.size() == 0) {
	// if (room.getSellState().equals(RoomSellStateEnum.Sign)) {
	// MsgBox.showInfo("û����ЧǩԼ��ͬ,ϵͳ����!");
	// SysUtil.abort();
	// }
	// return null;
	// }
	// return signs.get(0);
	// }
	public static PrePurchaseManageInfo getRoomPrePurchaseManage(RoomInfo room) {
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("room.id", room.getId().toString()));
		filter.getFilterItems().add(new FilterItemInfo("isValid",Boolean.FALSE));
		view.setFilter(filter);
		view.getSelector().add("id");
		view.getSelector().add("bizDate");
		PrePurchaseManageCollection prePurchaseManages = null;
		try {
			prePurchaseManages = PrePurchaseManageFactory.getRemoteInstance().getPrePurchaseManageCollection(view);
		} catch (BOSException e) {
			ExceptionHandler.handle(e);
			SysUtil.abort();
		}
		if (prePurchaseManages.size() > 1) {
			MsgBox.showInfo("�����ЧԤ����,ϵͳ����!");
			SysUtil.abort();
		}
		if (prePurchaseManages.size() == 0) {
			return null;
		}
		return prePurchaseManages.get(0);
	}
	
	public static PurchaseManageInfo getRoomPurchaseManage(RoomInfo room) {
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("room.id", room.getId().toString()));
		filter.getFilterItems().add(new FilterItemInfo("isValid",Boolean.FALSE));
		view.setFilter(filter);
		view.getSelector().add("id");
		view.getSelector().add("planSignDate");
		view.getSelector().add("bizDate");
		PurchaseManageCollection purchaseManages = null;
		try {
			purchaseManages = PurchaseManageFactory.getRemoteInstance().getPurchaseManageCollection(view);
		} catch (BOSException e) {
			ExceptionHandler.handle(e);
			SysUtil.abort();
		}
		if (purchaseManages.size() > 1) {
			MsgBox.showInfo("�����Ч�Ϲ���,ϵͳ����!");
			SysUtil.abort();
		}
		if (purchaseManages.size() == 0) {
			return null;
		}
		return purchaseManages.get(0);
	}
	
	public static SignManageInfo getRoomSignManage(RoomInfo room) {
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("room.id", room.getId().toString()));
		filter.getFilterItems().add(new FilterItemInfo("isValid",Boolean.FALSE));
		view.setFilter(filter);
		view.getSelector().add("id");
		view.getSelector().add("bizDate");
		SignManageCollection signManages = null;
		try {
			signManages = SignManageFactory.getRemoteInstance().getSignManageCollection(view);
		} catch (BOSException e) {
			ExceptionHandler.handle(e);
			SysUtil.abort();
		}
		if (signManages.size() > 1) {
			MsgBox.showInfo("�����ЧǩԼ��,ϵͳ����!");
			SysUtil.abort();
		}
		if (signManages.size() == 0) {
			return null;
		}
		return signManages.get(0);
	}
	
	public static TranBusinessOverViewInfo getTranBusinessOverView(String tranBusinessOverViewId) throws EASBizException {
		SelectorItemCollection sic = new SelectorItemCollection();
		sic.add("*");
		sic.add("head");
		sic.add("moneyDefine.name");
		sic.add("moneyDefine.number");
		sic.add("moneyDefine.moneyType");
		TranBusinessOverViewInfo tranBus = null;
		try {
			tranBus = TranBusinessOverViewFactory.getRemoteInstance().getTranBusinessOverViewInfo(new ObjectUuidPK(tranBusinessOverViewId), sic);
		} catch (BOSException e) {
			ExceptionHandler.handle(e);
			SysUtil.abort();
		}
		
		if (tranBus == null) {
			return null;
		}
		return tranBus;
	}

	public static RoomJoinInfo getRoomJoinIn(RoomInfo room) {
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("room.id", room.getId().toString()));
		filter.getFilterItems().add(new FilterItemInfo("joinState",new Integer(AFMortgagedStateEnum.UNTRANSACT_VALUE)));
		filter.getFilterItems().add(new FilterItemInfo("joinState",new Integer(AFMortgagedStateEnum.TRANSACTING_VALUE)));
		filter.getFilterItems().add(new FilterItemInfo("joinState",new Integer(AFMortgagedStateEnum.TRANSACTED_VALUE)));
		filter.setMaskString("#0 and ( #1 or #2 or #3 )");
		
		view.setFilter(filter);
		view.getSelector().add("id");
		view.getSelector().add("joinDate");
		view.getSelector().add("joinEndDate");
		RoomJoinCollection joins = null;
		try {
			joins = RoomJoinFactory.getRemoteInstance().getRoomJoinCollection(view);
		} catch (BOSException e) {
			ExceptionHandler.handle(e);
			SysUtil.abort();
		}
		if (joins.size() > 1) {
			MsgBox.showInfo("������,ϵͳ����!");
			SysUtil.abort();
		}
		if (joins.size() == 0) {
			return null;
		}
		return joins.get(0);
	}

	public static RoomAreaCompensateInfo getRoomAreaCompensation(RoomInfo room) {
		if (room.getRoomCompensateState() == null || room.getRoomCompensateState().equals(RoomCompensateStateEnum.NOCOMPENSATE)) {
			return null;
		}
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("room.id", room.getId().toString()));
		view.setFilter(filter);
		view.getSelector().add("compensateDate");
		view.getSelector().add("compensateState");
		view.getSelector().add("compensateAmount");

		RoomAreaCompensateCollection roomAreaCompens = null;
		try {
			roomAreaCompens = RoomAreaCompensateFactory.getRemoteInstance().getRoomAreaCompensateCollection(view);
		} catch (BOSException e) {
			ExceptionHandler.handle(e);
			SysUtil.abort();
		}
		if (roomAreaCompens.size() > 1) {
			MsgBox.showInfo("�������,ϵͳ����!");
			SysUtil.abort();
		}
		if (roomAreaCompens.size() == 0) {
			return null;
		}
		return roomAreaCompens.get(0);
	}

	public static RoomLoanInfo getRoomLoan(RoomInfo room) {
		if (room.getRoomLoanState() == null || room.getRoomLoanState().equals(RoomLoanStateEnum.NOTLOANED) && room.getRoomACCFundState().equals(RoomACCFundStateEnum.NOTFUND)) {
			return null;
		}
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("room.id", room.getId().toString()));
		filter.getFilterItems().add(new FilterItemInfo("mmType.moneyType", MoneyTypeEnum.LoanAmount));
		filter.getFilterItems().add(new FilterItemInfo("aFMortgagedState",new Integer(AFMortgagedStateEnum.UNTRANSACT_VALUE)));
		filter.getFilterItems().add(new FilterItemInfo("aFMortgagedState",new Integer(AFMortgagedStateEnum.TRANSACTING_VALUE)));
		filter.getFilterItems().add(new FilterItemInfo("aFMortgagedState",new Integer(AFMortgagedStateEnum.TRANSACTED_VALUE)));
		filter.getFilterItems().add(new FilterItemInfo("aFMortgagedState",new Integer(AFMortgagedStateEnum.BANKFUND_VALUE)));
		filter.setMaskString("#0 and #1 and( #2 or #3 or #4 or #5)");
		view.setFilter(filter);
		view.getSelector().add("fundProcessDate");
		view.getSelector().add("processLoanDate");
		view.getSelector().add("mmType");
		RoomLoanCollection joins = null;
		try {
			joins = RoomLoanFactory.getRemoteInstance().getRoomLoanCollection(view);
		} catch (BOSException e) {
			ExceptionHandler.handle(e);
			SysUtil.abort();
		}
		if (joins.size() == 0) {
			return null;
		}
		return joins.get(0);
	}
	
	public static RoomLoanInfo getRoomAccFund(RoomInfo room) {
		if (room.getRoomLoanState() == null || room.getRoomLoanState().equals(RoomLoanStateEnum.NOTLOANED) && room.getRoomACCFundState().equals(RoomACCFundStateEnum.NOTFUND)) {
			return null;
		}
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("room.id", room.getId().toString()));
		filter.getFilterItems().add(new FilterItemInfo("mmType.moneyType", MoneyTypeEnum.AccFundAmount));
		filter.getFilterItems().add(new FilterItemInfo("aFMortgagedState",new Integer(AFMortgagedStateEnum.UNTRANSACT_VALUE)));
		filter.getFilterItems().add(new FilterItemInfo("aFMortgagedState",new Integer(AFMortgagedStateEnum.TRANSACTING_VALUE)));
		filter.getFilterItems().add(new FilterItemInfo("aFMortgagedState",new Integer(AFMortgagedStateEnum.TRANSACTED_VALUE)));
		filter.getFilterItems().add(new FilterItemInfo("aFMortgagedState",new Integer(AFMortgagedStateEnum.BANKFUND_VALUE)));
		filter.setMaskString("#0 and #1 and( #2 or #3 or #4 or #5)");
		view.setFilter(filter);
		view.getSelector().add("fundProcessDate");
		view.getSelector().add("processLoanDate");
		view.getSelector().add("mmType");
		RoomLoanCollection joins = null;
		try {
			joins = RoomLoanFactory.getRemoteInstance().getRoomLoanCollection(view);
		} catch (BOSException e) {
			ExceptionHandler.handle(e);
			SysUtil.abort();
		}
		if (joins.size() == 0) {
			return null;
		}
		return joins.get(0);
	}


	public static RoomPropertyBookInfo getRoomPropertyBook(RoomInfo room) {
		if (room.getRoomBookState() == null || room.getRoomBookState().equals(RoomBookStateEnum.NOTBOOKED)) {
			return null;
		}
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("room.id", room.getId().toString()));
		filter.getFilterItems().add(new FilterItemInfo("propState",new Integer(AFMortgagedStateEnum.UNTRANSACT_VALUE)));
		filter.getFilterItems().add(new FilterItemInfo("propState",new Integer(AFMortgagedStateEnum.TRANSACTING_VALUE)));
		filter.getFilterItems().add(new FilterItemInfo("propState",new Integer(AFMortgagedStateEnum.TRANSACTED_VALUE)));
		filter.setMaskString("#0 and ( #1 or #2 or #3 )");
	
		view.setFilter(filter);
		view.getSelector().add("id");
		view.getSelector().add("transactDate");
		RoomPropertyBookCollection joins = null;
		try {
			joins = RoomPropertyBookFactory.getRemoteInstance().getRoomPropertyBookCollection(view);
		} catch (BOSException e) {
			ExceptionHandler.handle(e);
			SysUtil.abort();
		}
		if (joins.size() > 1) {
			MsgBox.showInfo("�����Ȩ�Ǽ�,ϵͳ����!");
			SysUtil.abort();
		}
		if (joins.size() == 0) {
			return null;
		}
		return joins.get(0);
	}
	
	public static SHERevBillInfo getRoomSHERevBill(RoomInfo room,BusinessTypeNameEnum bizFlow) {
		if (room== null || "".equals(bizFlow)) {
			return null;
		}
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("room.id", room.getId().toString()));
		filter.getFilterItems().add(new FilterItemInfo("entrys.moneyDefine.moneyType",bizFlow));
		view.setFilter(filter);
		view.getSelector().add("id");
		SHERevBillCollection revBills = null;
		try {
			revBills = SHERevBillFactory.getRemoteInstance().getSHERevBillCollection(view);
		} catch (BOSException e) {
			ExceptionHandler.handle(e);
			SysUtil.abort();
		}
		if (revBills.size() == 0) {
			return null;
		}
		return revBills.get(revBills.size()-1);
	}

	/**
	 * ��õ�ǰ��������֯��Ԫ
	 * 
	 * @return
	 */
	public static SaleOrgUnitInfo getCurrentSaleOrg() {
		SaleOrgUnitInfo saleUnit = SysContext.getSysContext().getCurrentSaleUnit();
		if (saleUnit == null) {
			MsgBox.showInfo("��ǰ��֯������������,���ܽ���!");
			SysUtil.abort();
		}
		return saleUnit;
	}

	private static void fillIdSetNode(Set idSet, DefaultKingdeeTreeNode node) {
		if (node.isLeaf()) {
			OrgStructureInfo org = (OrgStructureInfo) node.getUserObject();
			idSet.add(org.getUnit().getId().toString());
		} else {
			for (int i = 0; i < node.getChildCount(); i++) {
				fillIdSetNode(idSet, (DefaultKingdeeTreeNode) node.getChildAt(i));
			}
		}

	}

	/**
	 * ��õ�ǰ��������֯�µ��ӹ�˾Id����(��ʵ�幫˾�¶�����)
	 * 
	 * @param actionOnLoad
	 * @return
	 */
	public static Set getLeafCompanyIdSet(ItemAction actionOnLoad) {
		TreeModel orgTree = null;
		Set idSet = new HashSet();
		try {
			orgTree = getSaleOrgTree(actionOnLoad);
		} catch (Exception e) {
			ExceptionHandler.handle(e);
			SysUtil.abort();
		}
		if (orgTree == null)
			return idSet;
		DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) orgTree.getRoot();

		fillIdSetNode(idSet, node);
		return idSet;
	}

	/**
	 * ͨ��excel�ļ�����е������� ǰ���Ǵ�����ͬ��ָ���е�ֵ
	 * 
	 * @param fileName
	 *            excel�ļ�
	 * @param table
	 * @param headRowCount
	 *            ��ͷ������
	 * @param keyColumnCount
	 *            ָ����Ϊ�Ƚϵ��е�λ��
	 * @return
	 * @throws Exception
	 */
	public static int importExcelToTable(String fileName, KDTable table, int headRowCount, int keyColumnCount) throws Exception {
		if (headRowCount > 1) {
			MsgBox.showError("����ͷû��ʵ��,��Ը��д����д!");
			SysUtil.abort();
		}
		KDSBook kdsbook = null;
		try {

			kdsbook = POIXlsReader.parse2(fileName);
		} catch (Exception e) {
			e.printStackTrace();
			MsgBox.showError("��EXCEL����,EXCEl��ʽ��ƥ��!");
			SysUtil.abort();
		}
		if (kdsbook == null) {
			SysUtil.abort();
		}
		Sheet excelSheet = KDSBookToBook.traslate(kdsbook).getSheet(0);
		// KDSSheet excelSheet =kdsbook.getSheet(new Integer(0));
		Map colNameMap = new HashMap();
		int maxRow = excelSheet.getMaxRowIndex();
		int maxColumn = excelSheet.getMaxColIndex();
		// int maxRow = excelSheet.getRowCount();
		// int maxColumn = excelSheet.getColumnCount();

		for (int col = 0; col <= maxColumn; col++) {
			String excelColName = excelSheet.getCell(0, col, true).getText();
			colNameMap.put(excelColName, new Integer(col));
		}
		int successCount = 0;
		for (int rowIndex = 1; rowIndex <= maxRow; rowIndex++) {
			String key = "";
			int totalKeyCount = 0;
			int count = 0;
			for (int i = 0; i < table.getColumnCount(); i++) {
				if (count >= keyColumnCount) {
					break;
				}
				totalKeyCount++;
				if (table.getColumn(i).getStyleAttributes().isHided()) {
					continue;
				}
				String colName = (String) table.getHeadRow(0).getCell(i).getValue();
				Integer colInt = (Integer) colNameMap.get(colName);
				if (colInt == null) {
					MsgBox.showError("��ͷ�ṹ��һ��!����ϵĹؼ���:" + colName + "��EXCEL��û�г���!");
					SysUtil.abort();
				}
				String text = excelSheet.getCell(rowIndex, colInt.intValue(), true).getText();
				if (!StringUtils.isEmpty(text)) {
					key += text;
				}
				count++;
			}

			IRow row = getRowByRoomKey(table, key, keyColumnCount);
			if (row == null) {
				continue;
			}
			successCount++;
			for (int i = totalKeyCount; i < table.getColumnCount(); i++) {
				ICell tblCell = row.getCell(i);
				if (tblCell == null || table.getColumn(tblCell.getColumnIndex()).getStyleAttributes().isLocked())//modify by xiaominWang
					continue;
				String colName = (String) table.getHeadRow(0).getCell(i).getValue();
				Integer colInt = (Integer) colNameMap.get(colName);
				if (colInt == null) {
					continue;
				}
				Variant cellRawVal = excelSheet.getCell(rowIndex, colInt.intValue(), true).getValue();
				if (Variant.isNull(cellRawVal)) {
					continue;
				}
				if (cellRawVal.isNumeric()) {
					BigDecimal colValue = cellRawVal.toBigDecimal();
					tblCell.setValue(colValue);
				} else if (cellRawVal.isString()) {
					String colValue = cellRawVal.toString();
					tblCell.setValue(colValue);
				}
				// Object cellRawVal = excelSheet.getCell(rowIndex,
				// colInt.intValue(), true).getValue();
				// if (cellRawVal==null||cellRawVal=="") {
				// continue;
				// }
				// tblCell.setValue(FDCHelper.toBigDecimal(cellRawVal));
			}
		}
		return successCount;
	}

	// ר���ڶ��۵��۸���¥������Ϊ0��ͷ���������ݵ�����
	public static int importExcelToTable2(String fileName, KDTable table, int headRowCount, int keyColumnCount) throws Exception {
		if (headRowCount > 1) {
			MsgBox.showError("����ͷû��ʵ��,��Ը��д����д!");
			SysUtil.abort();
		}
		KDSBook kdsbook = null;
		try {

			kdsbook = POIXlsReader.parse2(fileName);
		} catch (Exception e) {
			e.printStackTrace();
			MsgBox.showError("��EXCEL����,EXCEl��ʽ��ƥ��!");
			SysUtil.abort();
		}
		if (kdsbook == null) {
			SysUtil.abort();
		}
		// Sheet excelSheet = KDSBookToBook.traslate(kdsbook).getSheet(0);
		KDSSheet excelSheet = kdsbook.getSheet(new Integer(0));
		Map colNameMap = new HashMap();
		// int maxRow = excelSheet.getMaxRowIndex();
		// int maxColumn = excelSheet.getMaxColIndex();
		int maxRow = excelSheet.getRowCount();
		int maxColumn = excelSheet.getColumnCount();

		for (int col = 0; col <= maxColumn; col++) {
			String excelColName = excelSheet.getCell(0, col, true).getText();
			colNameMap.put(excelColName, new Integer(col));
		}
		int successCount = 0;
		for (int rowIndex = 1; rowIndex <= maxRow; rowIndex++) {
			String key = "";
			int totalKeyCount = 0;
			int count = 0;
			for (int i = 0; i < table.getColumnCount(); i++) {
				if (count >= keyColumnCount) {
					break;
				}
				totalKeyCount++;
				if (table.getColumn(i).getStyleAttributes().isHided()) {
					continue;
				}
				String colName = (String) table.getHeadRow(0).getCell(i).getValue();
				Integer colInt = (Integer) colNameMap.get(colName);
				if (colInt == null) {
					MsgBox.showError("��ͷ�ṹ��һ��!����ϵĹؼ���:" + colName + "��EXCEL��û�г���!");
					SysUtil.abort();
				}
				String text = excelSheet.getCell(rowIndex, colInt.intValue(), true).getText();
				if (!StringUtils.isEmpty(text)) {
					key += text;
				}
				count++;
			}

			IRow row = getRowByRoomKey(table, key, keyColumnCount);
			if (row == null) {
				continue;
			}
			successCount++;
			for (int i = totalKeyCount; i < table.getColumnCount(); i++) {
				ICell tblCell = row.getCell(i);
				if (tblCell == null || tblCell.getStyleAttributes().isLocked())
					continue;
				String colName = (String) table.getHeadRow(0).getCell(i).getValue();
				Integer colInt = (Integer) colNameMap.get(colName);
				if (colInt == null) {
					continue;
				}
				// Variant cellRawVal = excelSheet.getCell(rowIndex,
				// colInt.intValue(), true).getValue();
				// if (Variant.isNull(cellRawVal)) {
				// continue;
				// }
				// if (cellRawVal.isNumeric()) {
				// BigDecimal colValue = cellRawVal.toBigDecimal();
				// tblCell.setValue(colValue);
				// } else if (cellRawVal.isString()) {
				// String colValue = cellRawVal.toString();
				// tblCell.setValue(colValue);
				// }
				Object cellRawVal = excelSheet.getCell(rowIndex, colInt.intValue(), true).getValue();
				if (cellRawVal == null || cellRawVal == "") {
					continue;
				}
				tblCell.setValue(FDCHelper.toBigDecimal(cellRawVal));
			}
		}
		return successCount;
	}
	
	// ר���ڶ��۵��۸���¥������Ϊ0��ͷ���������ݵ�����
	public static int importExcelToTableForRoomArea(String fileName, KDTable table, int headRowCount, int keyColumnCount) throws Exception {
		if (headRowCount > 1) {
			MsgBox.showError("����ͷû��ʵ��,��Ը��д����д!");
			SysUtil.abort();
		}
		KDSBook kdsbook = null;
		try {

			kdsbook = POIXlsReader.parse2(fileName);
		} catch (Exception e) {
			e.printStackTrace();
			MsgBox.showError("��EXCEL����,EXCEl��ʽ��ƥ��!");
			SysUtil.abort();
		}
		if (kdsbook == null) {
			SysUtil.abort();
		}
		
		KDSSheet excelSheet = kdsbook.getSheet(new Integer(0));
		Map colNameMap = new HashMap();
		int maxRow = excelSheet.getRowCount();
		int maxColumn = excelSheet.getColumnCount();

		for (int col = 0; col <= maxColumn; col++) {
			String excelColName = excelSheet.getCell(0, col, true).getText();
			colNameMap.put(excelColName, new Integer(col));
		}
		
		boolean isGo = true;
		for (int rowIndex = 1; rowIndex <= maxRow; rowIndex++) {
			String key = "";
			int totalKeyCount = 0;
			int count = 0;
			for (int i = 0; i < table.getColumnCount(); i++) {
				if (count >= keyColumnCount) {
					break;
				}
				totalKeyCount++;
				if (table.getColumn(i).getStyleAttributes().isHided()) {
					continue;
				}
				String colName = (String) table.getHeadRow(0).getCell(i).getValue();
				Integer colInt = (Integer) colNameMap.get(colName);
				if (colInt == null) {
					MsgBox.showError("��ͷ�ṹ��һ��!����ϵĹؼ���:" + colName + "��EXCEL��û�г���!");
					SysUtil.abort();
				}
				String text = excelSheet.getCell(rowIndex, colInt.intValue(), true).getText();
				if (!StringUtils.isEmpty(text)) {
					key += text;
				}
				count++;
			}

			IRow row = getRowByRoomKey(table, key, keyColumnCount);
			if (row == null) {
				continue;
			}
			
		
			for (int i = totalKeyCount; i < table.getColumnCount(); i++) {
				ICell tblCell = row.getCell(i);
				if (tblCell == null || tblCell.getStyleAttributes().isLocked())
					continue;
				String colName = (String) table.getHeadRow(0).getCell(i).getValue();
				if(!SELLAREATYPE.equals(colName)){
					continue;
				}
				Integer colInt = (Integer) colNameMap.get(colName);
				if (colInt == null) {
					continue;
				}
				Object cellRawVal = excelSheet.getCell(rowIndex, colInt.intValue(), true).getValue();
				if (cellRawVal == null || cellRawVal == "") {
					isGo = false;
					break;
				}else{
					if(!cellRawVal.toString().equals(SellAreaTypeEnum.ACTUAL.getAlias().toString())
					   &&!cellRawVal.toString().equals(SellAreaTypeEnum.PLANNING.getAlias().toString())
					   &&!cellRawVal.toString().equals(SellAreaTypeEnum.PRESELL.getAlias().toString())
					   ){
						isGo = false;
						break;
					}
				}
				
			}
		}
		
		if(!isGo){
			MsgBox.showError("EXCEL������������Ͳ���ȷ,����!");
			SysUtil.abort();
		}
		
		
		int successCount = 0;
		for (int rowIndex = 1; rowIndex <= maxRow; rowIndex++) {
			String key = "";
			int totalKeyCount = 0;
			int count = 0;
			for (int i = 0; i < table.getColumnCount(); i++) {
				if (count >= keyColumnCount) {
					break;
				}
				totalKeyCount++;
				if (table.getColumn(i).getStyleAttributes().isHided()) {
					continue;
				}
				String colName = (String) table.getHeadRow(0).getCell(i).getValue();
				Integer colInt = (Integer) colNameMap.get(colName);
				if (colInt == null) {
					MsgBox.showError("��ͷ�ṹ��һ��!����ϵĹؼ���:" + colName + "��EXCEL��û�г���!");
					SysUtil.abort();
				}
				String text = excelSheet.getCell(rowIndex, colInt.intValue(), true).getText();
				if (!StringUtils.isEmpty(text)) {
					key += text;
				}
				count++;
			}

			IRow row = getRowByRoomKey(table, key, keyColumnCount);
			if (row == null) {
				continue;
			}
			successCount++;
			for (int i = totalKeyCount; i < table.getColumnCount(); i++) {
				ICell tblCell = row.getCell(i);
				if (tblCell == null || tblCell.getStyleAttributes().isLocked())
					continue;
				String colName = (String) table.getHeadRow(0).getCell(i).getValue();
				Integer colInt = (Integer) colNameMap.get(colName);
				if (colInt == null) {
					continue;
				}
				Object cellRawVal = excelSheet.getCell(rowIndex, colInt.intValue(), true).getValue();
				if (cellRawVal == null || cellRawVal == "") {
					continue;
				}
				if(SELLAREATYPE.equals(colName) && cellRawVal instanceof String){
					tblCell.setValue(String.valueOf(cellRawVal));
				}else{
					tblCell.setValue(FDCHelper.toBigDecimal(cellRawVal));
				}
				
			}
		}
		return successCount;
	}

	private static IRow getRowByRoomKey(KDTable table, String key, int keyColumnCount) {
		for (int i = 0; i < table.getRowCount(); i++) {
			IRow row = table.getRow(i);
			String aKey = "";
			int count = 0;
			for (int j = 0; j < table.getColumnCount(); j++) {
				if (count >= keyColumnCount) {
					break;
				}
				if (table.getColumn(j).getStyleAttributes().isHided()) {
					continue;
				}
				Object value = row.getCell(j).getValue();
				if (value != null) {
					aKey += value.toString();
				}
				count++;
			}
			if (aKey.equals(key)) {
				return row;
			}
		}
		return null;
	}

	/**
	 * ����ѡ��excel�ļ��ĶԻ���
	 * 
	 * @param ui
	 * @return ����ѡ����ļ���ַ
	 */
	public static String showExcelSelectDlg(CoreUIObject ui) {
		KDFileChooser chsFile = new KDFileChooser();
		String XLS = "xls";
		String Key_File = "Key_File";
		SimpleFileFilter Filter_Excel = new SimpleFileFilter(XLS, "MS Excel" + LanguageManager.getLangMessage(Key_File, WizzardIO.class, "����ʧ��"));
		chsFile.addChoosableFileFilter(Filter_Excel);
		int ret = chsFile.showOpenDialog(ui);
		if (ret != JFileChooser.APPROVE_OPTION)
			SysUtil.abort();

		File file = chsFile.getSelectedFile();
		String fileName = file.getAbsolutePath();
		return fileName;
	}

	/**
	 * @deprecated  ���keyֵ��ͬ��������ᵼ�����ݶ�ʧ��������ʹ�� ����CRMHelper.sortCollection(elsePayList, "seq", true);
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

	/**
	 * ����ȡ��Ľ��,��decimal%unit
	 */
	public static BigDecimal remainder(BigDecimal decimal, BigDecimal unit) {
		BigDecimal res = decimal.divide(unit, 0, BigDecimal.ROUND_DOWN);
		return decimal.subtract(unit.multiply(res));
	}

	/**
	 * ȡ��������ǰʱ�䣬���쳣ʱ�����ر��ص�ǰʱ��
	 * */
	public static Timestamp getCurrentTime() {
		Timestamp currentTime = new Timestamp(new Date().getTime());
		try {
			currentTime = FDCSQLFacadeFactory.getRemoteInstance().getServerTime();
		} catch (BOSException e) {
			ExceptionHandler.handle(e);
			SysUtil.abort();
		}
		return currentTime;
	}

	/**
	 * ��ʼ��KDFormattedTextField����ػ�������
	 * */
	public static void setTextFormat(KDFormattedTextField textField) {
		textField.setRemoveingZeroInDispaly(false);
		textField.setRemoveingZeroInEdit(false);
		textField.setPrecision(2);
		textField.setHorizontalAlignment(JTextField.RIGHT);
		textField.setSupportedEmpty(true);
	}

	/**
	 * ��÷���Ĳ����
	 * 
	 * @param room
	 *            �����㲹��ķ���
	 * @return �����.����ΪNull����ʵ�����δ��������·���Null
	 *         ������ᱻ�����ˣ���ʵ�⸴�˺��䵥�۱���ա�������㲹���Ҳ����null
	 * */
	public static BigDecimal getCompensateAmount(RoomInfo room) {
		if (room == null || !room.isIsActualAreaAudited()) {
			return null;
		}
		boolean isCalByRoomArea = room.isIsCalByRoomArea();
		BigDecimal areaCompensateAmount = null;
		if (isCalByRoomArea) {
			BigDecimal actualRoomArea = room.getActualRoomArea();
			if (actualRoomArea != null && room.getRoomPrice() != null && room.getRoomArea() != null) {
				areaCompensateAmount = room.getRoomPrice().multiply(actualRoomArea.subtract(room.getRoomArea()));
			}
		} else {
			BigDecimal actualBuildingArea = room.getActualBuildingArea();
			if (actualBuildingArea != null && room.getBuildPrice() != null && room.getBuildingArea() != null) {
				areaCompensateAmount = room.getBuildPrice().multiply(actualBuildingArea.subtract(room.getBuildingArea()));
			}
		}
		return areaCompensateAmount;
	}

	/**
	 * ��ָ���������ʹ�ñ������ ������ڱ�����������ָ���������;�����������ʾ,�����������ı���
	 * 
	 * @param editData
	 * @param oprtState
	 * @param txtNumber
	 */
	public static void setNumberEnabled(CoreBaseInfo editData, String oprtState, KDTextField txtNumber) {
		if (editData == null)
			return;

		OrgUnitInfo crrOu = SysContext.getSysContext().getCurrentSaleUnit(); // ��ǰ������֯
																				// ;
		if (crrOu == null)
			crrOu = SysContext.getSysContext().getCurrentOrgUnit(); // ��ǰ��֯

		try {
			ICodingRuleManager iCodingRuleManager = CodingRuleManagerFactory.getRemoteInstance();
			if (iCodingRuleManager.isExist(editData, crrOu.getId().toString())) {
				if (txtNumber.isVisible() && txtNumber.isEnabled() && txtNumber.isEditable()) {
					txtNumber.setAccessAuthority(CtrlCommonConstant.AUTHORITY_COMMON);
					txtNumber.setEditable(false);
				}

				if (oprtState.equals(STATUS_ADDNEW) && iCodingRuleManager.isAddView(editData, crrOu.getId().toString())) {
					txtNumber.setText(iCodingRuleManager.getNumber(editData, crrOu.getId().toString()));
					if (editData instanceof DataBaseInfo) {
						DataBaseInfo dataBase = (DataBaseInfo) editData;
						dataBase.setNumber(txtNumber.getText());
					}
				}
			}
		} catch (Exception e) {
			ExceptionHandler.handle(e);
			SysUtil.abort();
		}
	}

	private static String getNumberFromCodeRule(CoreBaseInfo editData) throws CodingRuleException, EASBizException, BOSException {
		ICodingRuleManager iCodingRuleManager = CodingRuleManagerFactory.getRemoteInstance();
		String sysNumber = null;
		String companyID = iCodingRuleManager.getCurrentAppOUID(editData);
		if (iCodingRuleManager.isExist(editData, companyID)) {
			// û�����öϺ�֧�ֹ��ܣ���ʱ��ȡ�˱����������ı���
			sysNumber = iCodingRuleManager.getNumber(editData, companyID);
		}
		return sysNumber;
	}

	/**
	 * �Ƿ���Ҫ���ɵ��ݱ��
	 * 
	 * @return
	 * @throws CodingRuleException
	 * @throws EASBizException
	 * @throws BOSException
	 *             2006-11-24 wangyb
	 */
	private static boolean isRequireGenNewNumber(CoreBaseInfo editData, String oprtState, KDTextField txtNumber) throws CodingRuleException, EASBizException, BOSException {
		if (!oprtState.equals(STATUS_ADDNEW) || !StringUtils.isEmpty(txtNumber.getText()) || !isAutoNumber(editData) || !isAddView(editData)) {
			return false;
		}
		return true;
	}

	/**
	 * �Ƿ������Զ����ɱ���ı������
	 * 
	 * @return Ҫ�Զ�����ʱ����(true)���򷵻�(false)
	 * @throws CodingRuleException
	 * @throws EASBizException
	 * @throws BOSException
	 *             2006-2-22 wangyb
	 */
	private static boolean isAutoNumber(CoreBaseInfo editData) throws CodingRuleException, EASBizException, BOSException {
		ICodingRuleManager iCodingRuleManager = CodingRuleManagerFactory.getRemoteInstance();
		OrgUnitInfo crrOu = SysContext.getSysContext().getCurrentSaleUnit();
		if (crrOu == null)
			crrOu = SysContext.getSysContext().getCurrentOrgUnit();
		if (iCodingRuleManager.isExist(editData, crrOu.getId().toString())) {
			// ����������������Ϊ�Զ����ɱ���
			return true;
		}
		return false;
	}

	/**
	 * 
	 * �������ж��Ƿ���������ʾ
	 * 
	 * @return boolean
	 * @throws BOSException
	 * @throws EASBizException
	 * @throws CodingRuleException
	 * @author:daij ����ʱ�䣺2005-8-22
	 *              <p>
	 */
	private static boolean isAddView(CoreBaseInfo editData) throws CodingRuleException, EASBizException, BOSException {
		OrgUnitInfo crrOu = SysContext.getSysContext().getCurrentSaleUnit();
		if (crrOu == null)
			crrOu = SysContext.getSysContext().getCurrentOrgUnit();
		ICodingRuleManager iCodingRuleManager = CodingRuleManagerFactory.getRemoteInstance();
		return iCodingRuleManager.isAddView(editData, crrOu.getId().toString());
	}

	public static RoomCollection getRooms(BuildingInfo info) throws BOSException {
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		view.setFilter(filter);
		view.getSelector().add("*");
		view.getSelector().add("building.sellProject.projectResource");
		filter.getFilterItems().add(new FilterItemInfo("building.id", info.getId().toString()));
		RoomCollection rooms = RoomFactory.getRemoteInstance().getRoomCollection(view);
		return rooms;
	}

	public static RoomAreaCompensateInfo getRoomAreaCompensationInReport(RoomInfo room) {
		if (room.getRoomCompensateState() == null || room.getRoomCompensateState().equals(RoomCompensateStateEnum.NOCOMPENSATE)) {
			return null;
		}
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("room.id", room.getId().toString()));
		view.setFilter(filter);
		RoomAreaCompensateCollection roomAreaCompens = null;
		try {
			roomAreaCompens = RoomAreaCompensateFactory.getRemoteInstance().getRoomAreaCompensateCollection(view);
		} catch (BOSException e) {
			ExceptionHandler.handle(e);
			SysUtil.abort();
		}
		if (roomAreaCompens.size() > 1) {
			log.error("�������,ϵͳ����!");
		}
		if (roomAreaCompens.size() == 0) {
			return null;
			// MsgBox.showInfo("û�в���,ϵͳ����!");
			// SysUtil.abort();
		}
		return roomAreaCompens.get(0);
	}

	public static RoomJoinInfo getRoomJoinInInReport(RoomInfo room) {
		if (room.getRoomJoinState() == null || room.getRoomJoinState().equals(RoomJoinStateEnum.NOTJOIN)) {
			return null;
		}
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("room.id", room.getId().toString()));
		view.setFilter(filter);
		RoomJoinCollection joins = null;
		try {
			joins = RoomJoinFactory.getRemoteInstance().getRoomJoinCollection(view);
		} catch (BOSException e) {
			ExceptionHandler.handle(e);
			SysUtil.abort();
		}
		if (joins.size() > 1) {
			log.error("������,ϵͳ����!");
		}
		if (joins.size() == 0) {
			return null;
			// MsgBox.showInfo("û�����,ϵͳ����!");
			// SysUtil.abort();
		}
		return joins.get(0);
	}

	// kuangbiao add���ڈ��
	public static RoomLoanInfo getRoomLoanInReport(RoomInfo room) {
		if (room.getRoomLoanState() == null || room.getRoomLoanState().equals(RoomLoanStateEnum.NOTLOANED) && room.getRoomACCFundState().equals(RoomACCFundStateEnum.NOTFUND)) {
			return null;
		}
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("room.id", room.getId().toString()));
		view.setFilter(filter);
		RoomLoanCollection joins = null;
		try {
			joins = RoomLoanFactory.getRemoteInstance().getRoomLoanCollection(view);
		} catch (BOSException e) {
			ExceptionHandler.handle(e);
			SysUtil.abort();
		}
		if (joins.size() > 1) {
			log.error("�������,ϵͳ����!");
		}
		if (joins.size() == 0) {
			return null;
			// MsgBox.showInfo("û�а���,ϵͳ����!");
			// SysUtil.abort();
		}
		return joins.get(0);
	}

	public static RoomPropertyBookInfo getRoomPropertyBookInReport(RoomInfo room) {
		if (room.getRoomBookState() == null || room.getRoomBookState().equals(RoomBookStateEnum.NOTBOOKED)) {
			return null;
		}
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("room.id", room.getId().toString()));
		view.setFilter(filter);
		RoomPropertyBookCollection joins = null;
		try {
			joins = RoomPropertyBookFactory.getRemoteInstance().getRoomPropertyBookCollection(view);
		} catch (BOSException e) {
			ExceptionHandler.handle(e);
			SysUtil.abort();
		}
		if (joins.size() > 1) {
			log.error("�����Ȩ�Ǽ�,ϵͳ����!");
		}
		if (joins.size() == 0) {
			return null;
			// MsgBox.showInfo("û�в�Ȩ�Ǽ�,ϵͳ����!");
			// SysUtil.abort();
		}
		return joins.get(0);
	}

	/**
	 * ��¥��������table���湹��һ����
	 * 
	 * @author laiquan_luo
	 * @param action
	 * @param tblMain
	 * @return
	 * @throws Exception
	 */
	public static HashMap createTreeByBuilding(ItemAction action, KDTable tblMain, MoneySysTypeEnum sysTypeParam) throws Exception {
		HashMap map = new HashMap();
		tblMain.removeRows();
		TreeModel buildingTree = null;

		buildingTree = FDCTreeHelper.getBuildingTree(action, sysTypeParam);

		DefaultKingdeeTreeNode root = (DefaultKingdeeTreeNode) buildingTree.getRoot();
		tblMain.getTreeColumn().setDepth(root.getDepth() + 1);
		return fillNode(tblMain, (DefaultMutableTreeNode) root, map);
	}
	/**
	 * ��¥��������table���湹��һ����
	 * @param action
	 * @param tblMain
	 * @param sysTypeParam
	 * @param isSaleUnit
	 * @return
	 * @throws Exception
	 */
	public static HashMap createTreeByBuilding(ItemAction action, KDTable tblMain, MoneySysTypeEnum sysTypeParam,boolean isSaleUnit) throws Exception {
		HashMap map = new HashMap();
		tblMain.removeRows();
		TreeModel buildingTree = null;

		buildingTree = FDCTreeHelper.getBuildingTree(action, sysTypeParam,isSaleUnit);

		DefaultKingdeeTreeNode root = (DefaultKingdeeTreeNode) buildingTree.getRoot();
		tblMain.getTreeColumn().setDepth(root.getDepth() + 1);
		return fillNode(tblMain, (DefaultMutableTreeNode) root, map);
	}
	/**
	 * ����Ŀ���ƹ�����Ŀ��
	 * 
	 * @author laiquan_luo
	 * @param action
	 * @param tblMain
	 * @return
	 * @throws Exception
	 */
	public static HashMap createTreeByProject(ItemAction action, KDTable tblMain, MoneySysTypeEnum sysTypeParam) throws Exception {
		HashMap map = new HashMap();
		tblMain.removeRows();
		TreeModel projectTree = null;

		projectTree = FDCTreeHelper.getSellProjectTree(action, sysTypeParam);

		DefaultKingdeeTreeNode root = (DefaultKingdeeTreeNode) projectTree.getRoot();
		tblMain.getTreeColumn().setDepth(root.getDepth() + 1);
		return fillNodeByProject(tblMain, (DefaultMutableTreeNode) root, map);
	}

	/**
	 * ��Ӫ��Ȩ���޹ص���Ŀ��
	 */
	public static HashMap createTreeByBaseProject(ItemAction action, KDTable tblMain, MoneySysTypeEnum sysTypeParam) throws Exception {
		HashMap map = new HashMap();
		tblMain.removeRows();
		TreeModel projectTree = null;

		projectTree = FDCTreeHelper.getSellProjectBaseTree(action, sysTypeParam);
		DefaultKingdeeTreeNode root = (DefaultKingdeeTreeNode) projectTree.getRoot();
		tblMain.getTreeColumn().setDepth(root.getDepth() + 1);
		return fillNodeByProject(tblMain, (DefaultMutableTreeNode) root, map);
	}

	private static HashMap fillNode(KDTable table, DefaultMutableTreeNode node, HashMap map) {
		IRow row = table.addRow();
		row.setTreeLevel(node.getLevel());
		String space = "";
		for (int i = 0; i < node.getLevel(); i++) {
			space += " ";
		}
		if (node.getUserObject() instanceof BuildingInfo) {
			BuildingInfo building = (BuildingInfo) node.getUserObject();
			row.getCell("name").setValue(space + building.getName());
			row.setUserObject(node.getUserObject());
			map.put(building.getId().toString(), row);
		} else {
			row.getStyleAttributes().setBackground(new Color(0xF0EDD9));
			row.getCell("name").setValue(space + node);
		}
		if (!node.isLeaf()) {
			for (int i = 0; i < node.getChildCount(); i++) {
				fillNode(table, (DefaultMutableTreeNode) node.getChildAt(i), map);
			}
		}
		return map;
	}

	private static HashMap fillNodeByProject(KDTable table, DefaultMutableTreeNode node, HashMap map) {
		IRow row = table.addRow();
		row.setTreeLevel(node.getLevel());
		String space = "";
		for (int i = 0; i < node.getLevel(); i++) {
			space += " ";
		}
		if (node.getUserObject() instanceof SellProjectInfo) {
			SellProjectInfo sellProject = (SellProjectInfo) node.getUserObject();
			row.getCell("name").setValue(space + sellProject.getName());
			row.setUserObject(node.getUserObject());
			map.put(sellProject.getId().toString(), row);
		} else {
			row.getStyleAttributes().setBackground(new Color(0xF0EDD9));
			row.getStyleAttributes().setLocked(true);
			row.getCell("name").setValue(space + node);
		}
		if (!node.isLeaf()) {
			for (int i = 0; i < node.getChildCount(); i++) {
				fillNodeByProject(table, (DefaultMutableTreeNode) node.getChildAt(i), map);
			}
		}
		return map;
	}

	/**
	 * �鿴�������ܷ�ɾ�������޸ģ���Ҫ�Ǵ�ҵ����ȥ���� ���ǵ���ǰ�����ݣ��տ������û��seq�����ŵģ��ж����������ݶ��ǲ����޸ĵ�
	 * ����һ��lenght Ϊ2 �����飬�����false �򷵻� new String{"false","ԭ��"};
	 * 
	 * @param billId
	 * @return
	 * @author laiquan_luo
	 * @throws BOSException
	 * @throws EASBizException
	 */
	public static String[] isReceiveBillCanRemoveOrEdit(String billId) throws BOSException, EASBizException {
		SelectorItemCollection selColl = new SelectorItemCollection();
		selColl.add("fdcReceiveBill.*");
		selColl.add("fdcReceiveBill.moneyDefine.*");
		selColl.add("fdcReceiveBill.purchase.*");
		selColl.add("fdcReceiveBill.tenancyContract");
		selColl.add("fdcReceiveBill.purchase.payListEntry.*");
		selColl.add("fdcReceiveBill.room.*");
		selColl.add("fdcReceiveBillEntry.*");
		selColl.add("fdcReceiveBillEntry.moneyDefine.*");
		ReceivingBillInfo receivingBillInfo = ReceivingBillFactory.getRemoteInstance().getReceivingBillInfo(new ObjectUuidPK(BOSUuid.read(billId)), selColl);

		if (MoneySysTypeEnum.SalehouseSys.equals(receivingBillInfo.getFdcReceiveBill().getMoneySysType())) {
			return isReceiveBillCanRemoveOrEditForSHE(receivingBillInfo);
		} else if (MoneySysTypeEnum.TenancySys.equals(receivingBillInfo.getFdcReceiveBill().getMoneySysType())) {
			// return isReceiveBillCanRemoveOrEditForTEN(receivingBillInfo);
			return new String[] { "true", null };
		} else if (MoneySysTypeEnum.ManageSys.equals(receivingBillInfo.getFdcReceiveBill().getMoneySysType())) {
			return new String[] { "true", null };
		} else {
			return new String[] { "false", "�õ��ݲ��ܽ���ɾ�����޸Ĳ�����" };
		}
	}

	/**
	 * �ж������տ�Ƿ���ɾ���޸�
	 * 
	 * @param receivingBillInfo
	 * @return
	 * @throws BOSException
	 */
	private static String[] isReceiveBillCanRemoveOrEditForTEN(ReceivingBillInfo receivingBillInfo) throws BOSException {
		FDCReceiveBillInfo fdcReceiveBillInfo = receivingBillInfo.getFdcReceiveBill();
		// �˿�����޸�
		if (ReceiveBillTypeEnum.refundment.equals(fdcReceiveBillInfo.getBillTypeEnum())) {
			return new String[] { "true", null };
		}

		TenancyBillInfo tenancyBillInfo = fdcReceiveBillInfo.getTenancyContract();
		RoomInfo room = fdcReceiveBillInfo.getRoom();
		// ��ǰ���տ���ݵ�seq��Ϊ 0 �ģ��ж���ǰ�Ķ�����ɾ��
		int seq = fdcReceiveBillInfo.getSeq();

		FilterInfo filter = new FilterInfo();
		EntityViewInfo view = new EntityViewInfo();
		view.setFilter(filter);

		filter.getFilterItems().add(new FilterItemInfo("tenRoom.room", room.getId().toString()));
		filter.getFilterItems().add(new FilterItemInfo("tenRoom.tenancy", tenancyBillInfo.getId().toString()));

		TenancyRoomPayListEntryCollection tenancyRoomPayListEntryColl = TenancyRoomPayListEntryFactory.getRemoteInstance().getTenancyRoomPayListEntryCollection(view);

		int hasSeq = hasReceiveSeqForTEN(tenancyRoomPayListEntryColl);
		// ������˿������ȥ�жϸ�����ϸ�տ�˵ڼ�����
		if (!ReceiveBillTypeEnum.refundment.equals(fdcReceiveBillInfo.getBillTypeEnum())) {
			// �����ǰ�տ���Ѿ��տ�seqС������ɾ���޸�
			if (seq < hasSeq) {
				return new String[] { "false", "�õ����к������տ�ݣ����ܽ���ɾ���Ĳ�����", "�õ����к������տ�ݣ������޸Ľ�" };
			} else {
				// ��ʱû�д���һЩ��������
				return new String[] { "true", null };
			}
		} else {
			return new String[] { "true", null };
		}
	}

	/**
	 * ���տ��¼�л�ȡ�Ϲ���������ϸ�� ��� seq
	 * 
	 * @param entry
	 * @return
	 * @throws BOSException
	 */
	private static int getBigSeqFromFdcreceivebillEntry(FDCReceiveBillEntryCollection entry) throws BOSException {
		int returnValue = 0;

		Set payListSet = new HashSet();
		for (int i = 0; i < entry.size(); i++) {
			payListSet.add(entry.get(i).getPayListId());
		}

		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		view.setFilter(filter);

		if (payListSet != null && payListSet.size() > 0) {
			filter.getFilterItems().add(new FilterItemInfo("id", payListSet, CompareType.INCLUDE));
		} else {
			filter.getFilterItems().add(new FilterItemInfo("id", "noexist"));
		}
		PurchasePayListEntryCollection purchasePayListEntryColl = PurchasePayListEntryFactory.getRemoteInstance().getPurchasePayListEntryCollection(view);

		int temp = 0;
		for (int i = 0; i < purchasePayListEntryColl.size(); i++) {
			temp = purchasePayListEntryColl.get(i).getSeq();

			if (returnValue < temp)
				returnValue = temp;
		}
		return returnValue;
	}

	private static boolean isContainPlanList(FDCReceiveBillEntryCollection entry) throws BOSException, EASBizException {
		boolean debug = false;

		Set payListSet = new HashSet();
		for (int i = 0; i < entry.size(); i++) {
			payListSet.add(entry.get(i).getPayListId());
		}

		FilterInfo filter = new FilterInfo();

		if (payListSet != null && payListSet.size() > 0) {
			filter.getFilterItems().add(new FilterItemInfo("id", payListSet, CompareType.INCLUDE));
		} else {
			filter.getFilterItems().add(new FilterItemInfo("id", "noexist"));
		}
		debug = PurchasePayListEntryFactory.getRemoteInstance().exists(filter);
		return debug;
	}

	private static boolean isContainPreAmount(FDCReceiveBillEntryCollection entry) {
		boolean debug = false;

		for (int i = 0; i < entry.size(); i++) {
			if (MoneyTypeEnum.PreconcertMoney.equals(entry.get(i).getMoneyDefine().getMoneyType())) {
				debug = true;
				break;
			}
		}

		return debug;
	}

	/**
	 * ��¥ϵͳ���Ƿ���ɾ���޸�����
	 * 
	 * @param receivingBillInfo
	 * @return
	 * @throws BOSException
	 * @author laiquan_luo
	 * @throws EASBizException
	 */
	private static String[] isReceiveBillCanRemoveOrEditForSHE(ReceivingBillInfo receivingBillInfo) throws BOSException, EASBizException {
		FDCReceiveBillInfo fdcReceiveBillInfo = receivingBillInfo.getFdcReceiveBill();
		// ��������͵��տ�������޸ġ���ʱ�������� TODO
		if (GatheringEnum.SinPurchase.equals(fdcReceiveBillInfo.getGathering())) {
			return new String[] { "true", null };
		}

		FDCReceiveBillEntryCollection entry = receivingBillInfo.getFdcReceiveBillEntry();
		PurchaseInfo purchase = fdcReceiveBillInfo.getPurchase();
		// �˿�������޸�
		if (ReceiveBillTypeEnum.refundment.equals(fdcReceiveBillInfo.getBillTypeEnum())) {
			return new String[] { "true", null };
		}

		ReceiveBillTypeEnum revType = fdcReceiveBillInfo.getBillTypeEnum();

		// �ڴ˸���֮ǰ�Ĵ�����û�� billtypeenum �ֶεģ�֮ǰ���տ
		if (revType == null) {
			return new String[] { "false", "�õ��ݲ��ܽ����޸�/ɾ���Ĳ�����", "�õ��ݲ����޸Ľ�" };
		}

		// ����ת����������޸ĺ�ɾ��
		if (ReceiveBillTypeEnum.settlement.equals(revType) || ReceiveBillTypeEnum.transfer.equals(revType)) {
			return new String[] { "false", "�����տ�ܽ��иò�����", "�����տ�ܽ��иò�����", "ûʲô��" };
		}

		// ����һ��û�д����Ϲ�������Щ����
		FilterInfo filter = new FilterInfo();
		EntityViewInfo view = new EntityViewInfo();
		view.setFilter(filter);

		SorterItemCollection sorColl = new SorterItemCollection();
		sorColl.add(new SorterItemInfo("seq"));

		view.setSorter(sorColl);

		filter.getFilterItems().add(new FilterItemInfo("head", purchase.getId().toString()));
		view.getSelector().add("*");
		view.getSelector().add("payListEntry.*");

		PurchasePayListEntryCollection purchasePayListEntryColl = PurchasePayListEntryFactory.getRemoteInstance().getPurchasePayListEntryCollection(view);

		List planMoneyColl = new ArrayList();
		for (int i = 0; i < purchasePayListEntryColl.size(); i++) {
			PurchasePayListEntryInfo info = purchasePayListEntryColl.get(i);
			if (info.getMoneyDefine() != null && info.getMoneyDefine().getId() != null) {
				planMoneyColl.add(info.getMoneyDefine().getId().toString());
			}
		}

		RoomInfo room = fdcReceiveBillInfo.getRoom();
		RoomSellStateEnum state = room.getSellState();

		// ��ǰ���տ���ݵ�seq��Ϊ 0 �ģ��ж���ǰ�Ķ�����ɾ��
		int seq = getBigSeqFromFdcreceivebillEntry(entry);

		int hasSeq = hasReceiveSeqForSHE(purchasePayListEntryColl);

		if (isExistValidQuitBill(purchase, room)) {
			return new String[] { "false", "���տ�������Ч״̬�µ��˷��������ܽ����޸�/ɾ��������", "���տ�������Ч״̬�µ��˷����������޸Ľ�" };
		}

		if (isContainPreAmount(entry)) {
			if (PurchaseStateEnum.PurchaseAudit.equals(purchase.getPurchaseState())) {
				return new String[] { "false", "���տ���Ӧ���Ϲ����Ѿ����������ܽ����޸�/ɾ��������", "���տ���Ӧ���Ϲ����Ѿ������������޸Ľ�" };
			}
		}

		if (PurchaseStateEnum.PurchaseChange.equals(purchase.getPurchaseState())) {
			return new String[] { "false", "���տ���Ӧ���Ϲ������ڱ��״̬�����ܽ����޸�/ɾ��������", "���տ���Ӧ���Ϲ������ڱ��״̬�������޸Ľ�" };
		}
		// �����ǰ�տ���Ѿ��տ�seqС������ɾ���޸�
		else if (seq < hasSeq) {
			// ����ж�ֻ��ԣ��ƻ��Կ���
			if (isContainPlanList(entry)) {
				return new String[] { "false", "�õ����к������տ�ݣ����ܽ����޸�/ɾ��������", "�õ����к������տ��,�����޸Ľ�" };
			}

			else {
				return new String[] { "true", null };
			}
		} else {
			if (isContainPreAmount(entry)) {
				if (PurchaseStateEnum.PurchaseAudit.equals(purchase.getPurchaseState())) {
					return new String[] { "false", "���տ���Ӧ���Ϲ����Ѿ����������ܽ����޸�/ɾ��������", "���տ���Ӧ���Ϲ����Ѿ������������޸Ľ�" };
				}
			} else if (RoomSellStateEnum.Sign.equals(state)) {
				if (isFirstPlanMoney(entry, purchase)) {
					return new String[] { "false", "���տ����ǩԼ��״̬����Ӧ�ĵ�һ�ʿ���ܽ����޸�/ɾ��������", "���տ����ǩԼ��״̬����Ӧ�ĵ�һ�ʿ�����޸Ľ�" };
				}
			}

			return new String[] { "true", null };
		}
	}

	private static boolean isExistValidQuitBill(PurchaseInfo purchase, RoomInfo room) throws EASBizException, BOSException {
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		view.setFilter(filter);

		filter.getFilterItems().add(new FilterItemInfo("purchase.id", purchase.getId().toString()));
		filter.getFilterItems().add(new FilterItemInfo("room.id", room.getId().toString()));
		filter.getFilterItems().add(new FilterItemInfo("state", FDCBillStateEnum.SAVED_VALUE, CompareType.NOTEQUALS));
		filter.getFilterItems().add(new FilterItemInfo("state", FDCBillStateEnum.INVALID_VALUE, CompareType.NOTEQUALS));

		boolean debug = false;

		debug = QuitRoomFactory.getRemoteInstance().exists(filter);

		return debug;
	}

	private static boolean isFirstPlanMoney(FDCReceiveBillEntryCollection entry, PurchaseInfo purchase) throws BOSException {
		boolean debug = false;

		if (entry.size() < 1)
			return false;

		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		view.setFilter(filter);

		filter.getFilterItems().add(new FilterItemInfo("head", purchase.getId().toString()));
		view.getSelector().add("id");

		SorterItemCollection sorColl = new SorterItemCollection();
		sorColl.add(new SorterItemInfo("seq"));

		view.setSorter(sorColl);

		PurchasePayListEntryCollection payColl = PurchasePayListEntryFactory.getRemoteInstance().getPurchasePayListEntryCollection(view);

		if (payColl.get(0) == null) {
			throw new BOSException("���ִ�����鿴�������־��");
		}

		for (int i = 0; i < entry.size(); i++) {
			String payListId = entry.get(i).getPayListId().toString();
			if (payListId.equalsIgnoreCase(payColl.get(0).getId().toString())) {
				debug = true;
				break;
			} else {
				debug = false;
			}
		}

		return debug;
	}

	/**
	 * �Ƿ���ǩԼ��
	 * 
	 * @param purchase
	 * @param room
	 * @return
	 * @throws BOSException
	 * @author laiquan_luo
	 */
	private static boolean isHaveRoomSignContract(PurchaseInfo purchase, RoomInfo room) throws BOSException {
		FilterInfo filter = new FilterInfo();
		EntityViewInfo view = new EntityViewInfo();
		view.setFilter(filter);

		filter.getFilterItems().add(new FilterItemInfo("room", room.getId().toString()));
		filter.getFilterItems().add(new FilterItemInfo("purchase", purchase.getId().toString()));
		filter.getFilterItems().add(new FilterItemInfo("isBlankOut", Boolean.FALSE));

		RoomSignContractCollection roomSignColl = RoomSignContractFactory.getRemoteInstance().getRoomSignContractCollection(view);
		if (roomSignColl.size() > 0) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * ��¥ϵͳ ��øø�����ϸ�У��Ѹ����ˣ����˵ڼ���
	 * 
	 * @param purchasePayListEntryColl
	 * @return
	 * @author laiquan_luo
	 */
	private static int hasReceiveSeqForSHE(PurchasePayListEntryCollection purchasePayListEntryColl) {
		int tempSeq = 0;
		for (int i = 0; i < purchasePayListEntryColl.size(); i++) {
			PurchasePayListEntryInfo purchasePayListEntryInfo = purchasePayListEntryColl.get(i);
			BigDecimal actAmount = purchasePayListEntryInfo.getActPayAmount();
			if (actAmount == null)
				actAmount = FDCHelper.ZERO;
			if (actAmount.compareTo(FDCHelper.ZERO) > 0) {
				if (purchasePayListEntryInfo.getSeq() > tempSeq) {
					tempSeq = purchasePayListEntryInfo.getSeq();
				}
			}
		}
		return tempSeq;
	}

	/**
	 * ����ϵͳ ��øø�����ϸ�У��Ѹ����ˣ����˵ڼ���
	 * 
	 * @param purchasePayListEntryColl
	 * @return
	 * @author laiquan_luo
	 */
	private static int hasReceiveSeqForTEN(TenancyRoomPayListEntryCollection tenancyRoomPayListEntryColl) {
		int tempSeq = 0;
		for (int i = 0; i < tenancyRoomPayListEntryColl.size(); i++) {
			TenancyRoomPayListEntryInfo tenancyRoomPayListEntryInfo = tenancyRoomPayListEntryColl.get(i);
			if (tenancyRoomPayListEntryInfo.getActPayDate() != null) {
				if (tenancyRoomPayListEntryInfo.getSeq() > tempSeq) {
					tempSeq = tenancyRoomPayListEntryInfo.getSeq();
				}
			}
		}
		return tempSeq;
	}

	// ���ҵ�ǰ��Ա�ڵ�ǰ��֯�µ����ڵ�Ӫ����Ԫ
	public static Set getmarketingUnitID1111(UserInfo userInfo, SaleOrgUnitInfo saleOrg) throws BOSException {
		Set markUnitIDSet = new HashSet();
		EntityViewInfo view1 = new EntityViewInfo();
		FilterInfo filter1 = new FilterInfo();
		filter1.getFilterItems().add(new FilterItemInfo("dutyPerson.id", userInfo.getId().toString()));
		filter1.getFilterItems().add(new FilterItemInfo("head.orgUnit.id", saleOrg.getId().toString()));
		view1.setFilter(filter1);
		view1.getSelector().add("id");
		view1.getSelector().add("name");
		view1.getSelector().add("isUpdateMember");
		view1.getSelector().add("isOperation");
		view1.getSelector().add("head.id");
		view1.getSelector().add("id");
		view1.getSelector().add("head.longNumber");
		view1.getSelector().add("head.dutyPerson.id");
		view1.getSelector().add("head.dutyPerson.name");

		filter1 = new FilterInfo();
		filter1.getFilterItems().add(new FilterItemInfo("member.id", userInfo.getId().toString()));
		filter1.getFilterItems().add(new FilterItemInfo("head.orgUnit.id", saleOrg.getId().toString()));
		view1.setFilter(filter1);
		view1.getSelector().add("id");
		view1.getSelector().add("name");
		view1.getSelector().add("isUpdateMember");
		view1.getSelector().add("isOperation");
		view1.getSelector().add("head.id");
		view1.getSelector().add("head.longNumber");
		view1.getSelector().add("head");
		view1.getSelector().add("head.dutyPerson.id");
		view1.getSelector().add("head.dutyPerson.name");
		MarketingUnitMemberCollection markMemberUnit = MarketingUnitMemberFactory.getRemoteInstance().getMarketingUnitMemberCollection(view1);
		if (markMemberUnit.size() > 0) {
			for (int i = 0; i < markMemberUnit.size(); i++) {
				markUnitIDSet.add(markMemberUnit.get(i).getHead().getId().toString());
			}
		}
		return markUnitIDSet;
	}

	// Ĭ��ȡ���ķ�ʽ
	public static final ToIntegerTypeEnum DEFAULT_TO_INTEGER_TYPE = ToIntegerTypeEnum.Round;
	public static final DigitEnum DEFAULT_DIGIT = DigitEnum.EntryDigit;

	/**
	 * ����ֵ����ȡ�������󷵻�
	 * 
	 * @param srcAmount
	 *            ԭʼ���
	 * @param isToInteger
	 *            �Ƿ�ȡ��
	 * @param toIntegerType
	 *            ȡ����ʽ
	 * @param digit
	 *            ȡ��λ��
	 * @return ȡ����Ľ��
	 * */
	public static BigDecimal getAmountAfterToInteger(BigDecimal srcAmount, boolean isToInteger, ToIntegerTypeEnum toIntegerType, DigitEnum digit) {
		return SHEComHelper.getAmountAfterToInteger(srcAmount, isToInteger, toIntegerType, digit);
	}

	/**
	 * �����ۿ۷����ۿ۵�������Ϣ
	 * 
	 * @param col
	 *            ���۷�¼�ļ���,��IAgioEntry�ļ���
	 * @param specialAgio
	 *            �����ۿ�
	 * @param isToInteger
	 *            �Ƿ��Զ�ȡ��
	 * @param toIntegerType
	 *            ȡ����ʽ
	 * @param digit
	 *            ȡ��λ��
	 * @param �������д�����Ϣ���ַ���
	 * */
	public static String getAgioDes(AgioEntryCollection col, BigDecimal specialAgio, boolean isToInteger, ToIntegerTypeEnum toIntegerType, DigitEnum digit) {
		StringBuffer agioDes = new StringBuffer();

		if (col != null) {
			CRMHelper.sortCollection(col, "seq", true);
			for (int i = 0; i < col.size(); i++) {
				if (i != 0) {
					agioDes.append("��");
				}
				AgioEntryInfo entry = col.get(i);
				AgioBillInfo aAgio = entry.getAgio();

				agioDes.append(aAgio.getName());
				if (aAgio.getCalType().equals(AgioCalTypeEnum.Dazhe)) {
					agioDes.append("����" + entry.getPro().setScale(2, BigDecimal.ROUND_HALF_UP) + "% ");
				} else if (aAgio.getCalType().equals(AgioCalTypeEnum.JianDian)) {
					agioDes.append("����" + entry.getPro().setScale(2, BigDecimal.ROUND_HALF_UP) + "% ");
				} else if (aAgio.getCalType().equals(AgioCalTypeEnum.ZongJia)) {
					agioDes.append("�ܼ��Ż�" + entry.getAmount().setScale(2, BigDecimal.ROUND_HALF_UP));
				} else if (aAgio.getCalType().equals(AgioCalTypeEnum.DanJia)) {
					agioDes.append("�����Ż�" + entry.getAmount().setScale(2, BigDecimal.ROUND_HALF_UP));
				}

				if (i == col.size() - 1) {
					agioDes.append("��");
				}
			}
		}

		// �����ۿ�,ֻ����ʾ,������Ч�Լ��� ---by zhicheng_jin
		if (specialAgio != null && specialAgio.compareTo(new BigDecimal("100")) != 0) {
			BigDecimal teishu = specialAgio;
			agioDes.append("�����ۿ� " + teishu.setScale(2, BigDecimal.ROUND_HALF_UP) + "%��");
		}

		if (isToInteger) {
			agioDes.append(digit.getAlias()).append(toIntegerType.getAlias()).append("�Զ�ȡ����");
		}

		return agioDes.toString();
	}

	/**
	 * �����ۿ۷����ۿ۵�������Ϣ
	 * 
	 * @param col
	 *            ���۷�¼�ļ���,��IAgioEntry�ļ���
	 * @param specialAgio
	 *            �����ۿ�
	 * @param isToInteger
	 *            �Ƿ��Զ�ȡ��
	 * @param isBasePrice
	 * 			  �Ƿ�׼�����
	 * @param toIntegerType
	 *            ȡ����ʽ
	 * @param digit
	 *            ȡ��λ��
	 * @param �������д�����Ϣ���ַ���
	 * */
	public static String getAgioDes(AgioEntryCollection col, SpecialAgioEnum specialAgioType, BigDecimal specialAgio,String payTypeName, boolean isToInteger,
			boolean isBasePrice, ToIntegerTypeEnum toIntegerType, DigitEnum digit) {
		StringBuffer agioDes = new StringBuffer();
		
		if (payTypeName != null && SpecialAgioEnum.DaZhe.equals(specialAgioType)) {
			if (specialAgio != null && specialAgio.compareTo(new BigDecimal("100")) != 0) {
				BigDecimal teishu = specialAgio;
				agioDes.append(payTypeName+"�ۿ� " + teishu.setScale(2, BigDecimal.ROUND_HALF_UP) + "%;");
			}
		}
		if (col != null) {
			CRMHelper.sortCollection(col, "seq", true);
			for (int i = 0; i < col.size(); i++) {
				if (i != 0) {
					agioDes.append("��");
				}
				AgioEntryInfo entry = (AgioEntryInfo)col.get(i);
				AgioBillInfo aAgio = entry.getAgio();
				if(aAgio!=null){
					agioDes.append(aAgio.getName());
					if (aAgio.getCalType().equals(AgioCalTypeEnum.Dazhe)) {
						agioDes.append("����" + entry.getPro().setScale(2, BigDecimal.ROUND_HALF_UP) + "% ");
					} else if (aAgio.getCalType().equals(AgioCalTypeEnum.JianDian)) {
						agioDes.append("����" + entry.getPro().setScale(2, BigDecimal.ROUND_HALF_UP) + "% ");
					} else if (aAgio.getCalType().equals(AgioCalTypeEnum.ZongJia)) {
						agioDes.append("�ܼ��Ż�" + entry.getAmount().setScale(2, BigDecimal.ROUND_HALF_UP));
					} else if (aAgio.getCalType().equals(AgioCalTypeEnum.DanJia)) {
						agioDes.append("�����Ż�" + entry.getAmount().setScale(2, BigDecimal.ROUND_HALF_UP));
					}
				}else{
					agioDes.append("�ܼ��Ż�" + entry.getAmount().setScale(2, BigDecimal.ROUND_HALF_UP));
				}
			

				if (i == col.size() - 1) {
					agioDes.append("��");
				}
			}
		}
//		if(isBasePrice) {
//			agioDes.append("ֱ�ӵ׼�����  ");
//		}
		

		// �����ۿ�,ֻ����ʾ,������Ч�Լ��� ---by zhicheng_jin
//		if (SpecialAgioEnum.DaZhe.equals(specialAgioType)) {
//			if (specialAgio != null && specialAgio.compareTo(new BigDecimal("100")) != 0) {
//				BigDecimal teishu = specialAgio;
//				agioDes.append("�����ۿ� " + teishu.setScale(2, BigDecimal.ROUND_HALF_UP) + "%��");
//			}
//		} else {
//			if (specialAgio != null && specialAgio.compareTo(new BigDecimal(0)) != 0) {
//				BigDecimal teishu = specialAgio;
//				String str = new String();
//				agioDes.append("�����ۿ� " + str + teishu.setScale(2, BigDecimal.ROUND_HALF_UP) + "��");
//			}
//		}

		if (isToInteger) {
			agioDes.append(digit.getAlias()).append(toIntegerType.getAlias()).append("�Զ�ȡ����");
		}

		return agioDes.toString();
	}

	public static BigDecimal setScale(BigDecimal value) {
		if (value == null)
			return null;
		if (value.compareTo(FDCHelper.ZERO) == 0)
			return FDCHelper.ZERO;
		return value.setScale(2, BigDecimal.ROUND_HALF_UP);
	}

	public static final String TEN_THOUSHAND_DIGIT = "��λ";
	public static final String THOUSHAND_DIGIT = "ǧλ";

	/**
	 * �����û����з��ز��ͻ���ѡ��
	 * 
	 * @throws EASBizException
	 * */
	public static FDCCustomerInfo selectCustomer(IUIWindow win, UserInfo user, SaleOrgUnitInfo saleOrg) throws BOSException, EASBizException {
		KDCommonPromptDialog dlg = null;
		if(win instanceof Frame) {
			dlg = new KDCommonPromptDialog((Frame)win);
		}else if(win instanceof Dialog){
			dlg = new KDCommonPromptDialog((Dialog)win);
		}else{
			dlg = new KDCommonPromptDialog();
		}
		IMetaDataLoader loader = MetaDataLoaderFactory.getRemoteMetaDataLoader();
		dlg.setQueryInfo(loader.getQuery(new MetaDataPK("com.kingdee.eas.fdc.sellhouse.app.CustomerQuery")));

		dlg.setEntityViewInfo(CommerceHelper.getPermitCustomerView(null,user));
		dlg.show();
		Object[] object = (Object[]) dlg.getData();
		if (object != null && object.length > 0) {
			FDCCustomerInfo customer = (FDCCustomerInfo) object[0];
			return customer;
		}
		return null;
	}

	/**
	 * �ж��Ϲ����ĸ�����ϸ�����Ƿ��Ѿ��и����
	 * 
	 * @author laiquan_luo
	 * @param purchase
	 * @return
	 */
	public static boolean isHaveGatheringInPurchasePayList(PurchaseInfo purchase) {
		PurchasePayListEntryCollection entrys = purchase.getPayListEntry();
		boolean isHaveActRev = false;
		for (int i = 0; i < entrys.size(); i++) {
			PurchasePayListEntryInfo entry = entrys.get(i);
			BigDecimal actAmount = entry.getActPayAmount();
			if (actAmount != null && actAmount.compareTo(FDCHelper.ZERO) > 0) {
				isHaveActRev = true;
				break;
			}
		}
		return isHaveActRev;
	}

	/**
	 * �ж�ĳ���Ϲ����Ƿ���ȡ���ض���һЩ����
	 * 
	 * @param moneySet
	 * @param purchaseId
	 * @return
	 * @throws BOSException
	 * @throws EASBizException
	 */
	public static boolean isHaveGatheringBySpecialMoney(Set moneySet, String purchaseId) throws EASBizException, BOSException {
		boolean debug = false;
		FilterInfo filter = new FilterInfo();

		filter.getFilterItems().add(new FilterItemInfo("receivingBill.fdcReceiveBill.purchase.id", purchaseId));
		filter.getFilterItems().add(new FilterItemInfo("moneyDefine.moneyType", moneySet, CompareType.INCLUDE));

		debug = FDCReceiveBillEntryFactory.getRemoteInstance().exists(filter);
		return debug;
	}

	/**
	 * ��ǰ��֯�£���Ϊָ���ͻ������Ĺ��ʵĸ����ˣ���ӵ��ҵ�����Ȩ��
	 */
	public static boolean hasOperatorPermission(String customerId) throws EASBizException, BOSException {
		if (customerId == null)
			return false;
		UserInfo currUserInfo = SysContext.getSysContext().getCurrentUserInfo();
		SaleOrgUnitInfo saleOrg = SysContext.getSysContext().getCurrentSaleUnit();
		FDCCustomerInfo customerInfo = FDCCustomerFactory.getRemoteInstance().getFDCCustomerInfo("select salesman.id where id='" + customerId + "'");
		MarketUnitPlatCollection muPlatColl = MarketUnitPlatFactory.getRemoteInstance().getMarketUnitPlatCollection(
				"select dutyPerson.id,marketUnit.id " + "where orgUnit.id='" + saleOrg.getId().toString() + "' and dutyPerson.id='" + currUserInfo.getId().toString() + "' and member.id='"
						+ customerInfo.getSalesman().getId().toString() + "' ");
		for (int i = 0; i < muPlatColl.size(); i++) {
			MarketUnitPlatInfo muPlatInfo = muPlatColl.get(i);
			boolean existFlag = MarketingUnitMemberFactory.getRemoteInstance().exists(
					"where head.id='" + muPlatInfo.getMarketUnit().getId().toString() + "' " + "and isDuty=1 and isOperation=1 and member.id='" + muPlatInfo.getDutyPerson().getId().toString() + "'");
			if (existFlag)
				return true;
		}
		return false;
	}

	public static RoomInfo queryRoomInfo(String roomID) throws EASBizException, BOSException, UuidException {
		SelectorItemCollection sels = new SelectorItemCollection();
		sels.add("*");
		sels.add("roomModel.name");
		sels.add("roomModel.number");
		sels.add("roomModel.imgData");
		sels.add("roomModel.roomModelType.name");
		sels.add("direction.name");
		sels.add("sight.name");
		sels.add("decoraStd.name");
		sels.add("buildingProperty.name");
		sels.add("building.id");
		sels.add("building.number");
		sels.add("building.name");
		sels.add("building.subarea.name");
		sels.add("building.sellProject.id");
		sels.add("building.sellProject.name");
		sels.add("building.sellProject.number");
		sels.add("building.sellProject.isForPPM");
		sels.add("building.productType.id");
		sels.add("building.joinInDate");
		sels.add("buildUnit.name");
		sels.add("buildUnit.number");
		sels.add("buildUnit.seq");
		sels.add("sellOrder.name");
		sels.add("sellOrder.number");
		sels.add("lastPurchase.id");
		// -----ǩԼʱҪ��֤���Ϲ���״̬-by zhicheng_jin 081118
		sels.add("lastPurchase.purchaseState");
		sels.add("lastPurchase.purchaseDate");
		sels.add("lastPurchase.contractTotalAmount");
		sels.add("lastPurchase.auditor.name");
		sels.add("lastPurchase.payType.id");
		sels.add("lastPurchase.payListEntry.*");
		sels.add("lastSignContract.signDate");
		sels.add("direction.name");
		sels.add("direction.number");
		sels.add("sight.name");
		sels.add("sight.number");
		sels.add("productType.number");
		sels.add("productType.name");
		sels.add("attachmentEntry.room.number");
		sels.add("lastKeepDownBill.bizDate");
		sels.add("lastKeepDownBill.description");
		sels.add("lastKeepDownBill.handler.name");
		sels.add("lastTenancy.sellProject.id");
		sels.add("name");
		
		sels.add("buildingArea");
		sels.add("actualBuildingArea");
		sels.add("actualRoomArea");
		sels.add("roomArea");
		sels.add("buildingFloor.*");
		sels.add("roomModel.roomModelType.*");
		RoomInfo roomInfo = RoomFactory.getRemoteInstance().getRoomInfo(new ObjectUuidPK(BOSUuid.read(roomID)), sels);
		return roomInfo;
	}

	/**
	 * @deprecated ��ʹ�ô�revEditUIClazz�����ĺ���
	 * */
	public static void fillActPayTable(KDTable table, FDCReceivingBillCollection revs) {
		fillActPayTable(table, revs, null);
	}
	
	/**
	 * ���տ��¼չʾ��table�� Ŀǰ�����ر���Ϲ�����ʵ��ҳǩ����
	 * */
	public static void fillActPayTable(KDTable table, FDCReceivingBillCollection revs, Class revEditUIClazz) {
		if (table == null) {
			return;
		}
		if(revEditUIClazz == null) revEditUIClazz = SHEReceiveBillEditUI.class;
		initActPayTable(table, revEditUIClazz);
		for (int i = 0; i < revs.size(); i++) {
			FDCReceivingBillInfo rev = revs.get(i);
			FDCReceivingBillEntryCollection fdcRevEntrys = rev.getEntries();
			for (int j = 0; j < fdcRevEntrys.size(); j++) {
				FDCReceivingBillEntryInfo fdcRevEntry = fdcRevEntrys.get(j);

				IRow row = table.addRow();
				row.setUserObject(fdcRevEntry);

				row.getCell("id").setValue(rev.getId());
				row.getCell("date").setValue(rev.getBizDate());
				row.getCell("currency").setValue(rev.getCurrency());
				row.getCell("customer").setValue(rev.getCustomer());
				row.getCell("receiver").setValue(rev.getCreator()==null?"":rev.getCreator().getName());
				row.getCell("moneyName").setValue(fdcRevEntry.getMoneyDefine());
				row.getCell("amount").setValue(fdcRevEntry.getRevAmount());
				row.getCell("revBillType").setValue(rev.getRevBillType());
				
				TransferSourceEntryCollection trasSorColl = fdcRevEntry.getSourceEntries();
				for(int k=0;k<trasSorColl.size();k++) {
					TransferSourceEntryInfo trasSorInfo = trasSorColl.get(k);
					IRow trasRow = table.addRow();
					trasRow.setUserObject(fdcRevEntry);
					trasRow.getCell("id").setValue(rev.getId());
					trasRow.getCell("date").setValue(rev.getBizDate());
					trasRow.getCell("currency").setValue(rev.getCurrency());
					trasRow.getCell("customer").setValue(rev.getCustomer());
					trasRow.getCell("receiver").setValue(rev.getCreator()==null?"":rev.getCreator().getName());
					trasRow.getCell("moneyName").setValue(trasSorInfo.getFromMoneyDefine());
					trasRow.getCell("amount").setValue(trasSorInfo.getAmount().negate());
					trasRow.getCell("revBillType").setValue(rev.getRevBillType());
				}				
			}			
		}
	}

	private static void initActPayTable(final KDTable table, final Class revEditUIClazz) {
		table.getStyleAttributes().setLocked(true);
		table.removeColumns();
		table.getSelectManager().setSelectMode(KDTSelectManager.ROW_SELECT);
		IColumn column = table.addColumn();
		column.setKey("id");
		column.getStyleAttributes().setHided(true);
		column = table.addColumn();
		column.setKey("date");
		String formatString = "yyyy-MM-dd";
		column.getStyleAttributes().setNumberFormat(formatString);
		column = table.addColumn();
		column.setKey("revBillType");
		column = table.addColumn();
		column.setKey("moneyName");
		column = table.addColumn();
		column.setKey("amount");
		column.getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
		column.getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(2));
		column = table.addColumn();
		column.setKey("customer");
		column = table.addColumn();
		column.setKey("currency");
		column = table.addColumn();
		column.setKey("receiver");

		// column = table.addColumn();
		// column.setKey("salesman");
		IRow headRow = table.addHeadRow();
		headRow.getCell("id").setValue("ID");
		headRow.getCell("date").setValue("�տ�����");
		headRow.getCell("revBillType").setValue("��������");
		headRow.getCell("moneyName").setValue("��������");
		headRow.getCell("amount").setValue("���");
		headRow.getCell("currency").setValue("�ұ�");
		headRow.getCell("customer").setValue("�ͻ�");
		headRow.getCell("receiver").setValue("�Ƶ���");
		// headRow.getCell("salesman").setValue("���۹���");
		
		table.getColumn("id").setGroup(true);
		table.getColumn("date").setGroup(true);
		table.getColumn("revBillType").setGroup(true);
		table.getColumn("currency").setGroup(true);
		table.getColumn("customer").setGroup(true);
		table.getColumn("receiver").setGroup(true);
		table.getGroupManager().setGroup(true);
		
		table.addKDTMouseListener(new KDTMouseListener() {
			public void tableClicked(KDTMouseEvent e) {
				try {
					if (e.getButton() == 1 && e.getClickCount() == 2) {
						int rowIndex = e.getRowIndex();
						if (rowIndex == -1) {
							return;
						}
						IRow row = table.getRow(rowIndex);
						FDCReceivingBillEntryInfo fdcRevEntry = (FDCReceivingBillEntryInfo) row.getUserObject();
						UIContext uiContext = new UIContext(this);
						uiContext.put("ID", fdcRevEntry.getHead().getId());
						IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.MODEL).create(revEditUIClazz.getName(), uiContext, null, "VIEW");
						uiWindow.show();
					}
				} catch (Exception exc) {
					ExceptionHandler.handle(exc);
				} finally {
				}
			}
		});
	}

	public static OrgStructureInfo getOrgUnit(DefaultMutableTreeNode node) {
		Object obj = node.getUserObject();
		if (obj instanceof OrgStructureInfo) {
			return (OrgStructureInfo) obj;
		}

		if (node.getParent() != null) {
			return getOrgUnit((DefaultMutableTreeNode) node.getParent());
		} else {
			return null;
		}
	}

	/**
	 * ��ȡֱ���ϼ�Ӫ����Ԫ
	 * 
	 * @param node
	 * @return
	 */
	public static MarketingUnitInfo getMarketingUnit(DefaultMutableTreeNode node) {
		Object obj = node.getUserObject();
		if (obj instanceof MarketingUnitInfo) {
			return (MarketingUnitInfo) obj;
		}

		if (node.getParent() != null) {
			return getMarketingUnit((DefaultMutableTreeNode) node.getParent());
		} else {
			return null;
		}
	}

	public static SellProjectInfo getSellProject(DefaultMutableTreeNode node) {
		Object obj = node.getUserObject();
		if (obj instanceof SellProjectInfo) {
			return (SellProjectInfo) obj;
		}

		if (node.getParent() != null) {
			return getSellProject((DefaultMutableTreeNode) node.getParent());
		} else {
			return null;
		}
	}

	// �ҵ���ǰ�ڵ����и��ڵ���Ӫ����Ԫ�Ľڵ�ID
	public static Set getMarketingUnit(DefaultMutableTreeNode node, Set set) {
		Object obj = node.getUserObject();
		if (obj instanceof MarketingUnitInfo == false) {
			return set;
		}
		if (node.getParent() != null && ((DefaultMutableTreeNode) node.getParent()).getUserObject() instanceof MarketingUnitInfo) {
			MarketingUnitInfo markInfo = (MarketingUnitInfo) ((DefaultMutableTreeNode) node.getParent()).getUserObject();
			set.add(markInfo.getId().toString());
			return getMarketingUnit((DefaultMutableTreeNode) node.getParent(), set);
		}
		return set;
	}

	// �ҳ��㵱ǰ�ڵ��ӽڵ���Ӫ����Ԫ�Ľڵ�ID
	public static Set getChildMarketUnit(DefaultMutableTreeNode node, Set set) {
		Object obj = node.getUserObject();
		if (obj instanceof MarketingUnitInfo == false) {
			return set;
		}
		if (node.getChildCount() > 0 && obj instanceof MarketingUnitInfo) {
			MarketingUnitInfo markInfo = (MarketingUnitInfo) obj;
			set.add(markInfo.getId().toString());
		}
		for (int i = 0; i < node.getChildCount(); i++) {
			TreeNode node2 = node.getChildAt(i);
			getChildMarketUnit((DefaultMutableTreeNode) node2, set);
		}
		return set;
	}

	/*
	 * �ܹ������Ŀ��Ӫ����Ԫ�ļ���
	 */
	public static Set getPPMMarket(String projectID) {
		Set set = new HashSet();
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("sellProject.id", projectID));
		filter.getFilterItems().add(new FilterItemInfo("head.isWuYeFunction", Boolean.TRUE));
		view.setFilter(filter);
		try {
			MarketingUnitSellProjectCollection marketColl = MarketingUnitSellProjectFactory.getRemoteInstance().getMarketingUnitSellProjectCollection(view);
			if (marketColl.size() > 0) {
				for (int i = 0; i < marketColl.size(); i++) {
					MarketingUnitSellProjectInfo marketInfo = marketColl.get(i);
					set.add(marketInfo.getHead().getId().toString());
				}
			}
		} catch (BOSException e) {
			e.printStackTrace();
		}
		return set;
	}

	// Ӫ����Ԫ�¾�����ҵְ�ܵĳ�Ա
	public static Set getPPMUser(Set ppmMarketSet) {
		Set set = new HashSet();
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("head.id", ppmMarketSet, CompareType.INCLUDE));
		filter.getFilterItems().add(new FilterItemInfo("isWuYeFunction", Boolean.TRUE));
		view.setFilter(filter);
		try {
			MarketingUnitMemberCollection marketMemberColl = MarketingUnitMemberFactory.getRemoteInstance().getMarketingUnitMemberCollection(view);
			if (marketMemberColl.size() > 0) {
				for (int i = 0; i < marketMemberColl.size(); i++) {
					MarketingUnitMemberInfo marketMemberInfo = marketMemberColl.get(i);
					set.add(marketMemberInfo.getMember().getId().toString());
				}
			}
		} catch (BOSException e) {
			e.printStackTrace();
		}
		return set;
	}

	public static Set getPPMOrgUnit(String projectID) {
		Set set = new HashSet();
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("sellProject.id", projectID));
		filter.getFilterItems().add(new FilterItemInfo("head.isWuYeFunction", Boolean.TRUE));
		view.setFilter(filter);
		view.getSelector().add("*");
		view.getSelector().add("head.orgUnit.*");
		try {
			MarketingUnitSellProjectCollection marketColl = MarketingUnitSellProjectFactory.getRemoteInstance().getMarketingUnitSellProjectCollection(view);
			if (marketColl.size() > 0) {
				for (int i = 0; i < marketColl.size(); i++) {
					MarketingUnitSellProjectInfo marketInfo = marketColl.get(i);
					set.add(marketInfo.getHead().getOrgUnit().getId().toString());
				}
			}
		} catch (BOSException e) {
			e.printStackTrace();
		}
		return set;
	}

	// ȥ�������й��������ͬ���ҹ����������ͬһ�˵ļ�¼
	public static Set differentSet(List tblShareList, List salmanList, List marketUnitList, List orgUnitList) {
		Set newSet = new HashSet();
		// ����ͻ�ԭ��û�й�������۹��ʣ���ô�Ͱ�������ֱ�����
		if (salmanList.size() == 0 && marketUnitList.size() == 0 && orgUnitList.size() == 0) {
			for (int i = 0; i < tblShareList.size(); i++) {
				ShareSellerInfo shareSellerInfo2 = (ShareSellerInfo) tblShareList.get(i);
				newSet.add(shareSellerInfo2);
			}
			for (int j = 0; j < marketUnitList.size(); j++) {
				ShareSellerInfo shareSellerInfo = (ShareSellerInfo) marketUnitList.get(j);
				newSet.add(shareSellerInfo);
			}
			for (int j = 0; j < orgUnitList.size(); j++) {
				ShareSellerInfo shareSellerInfo = (ShareSellerInfo) orgUnitList.get(j);
				newSet.add(shareSellerInfo);
			}
		} else {
			for (int i = 0; i < tblShareList.size(); i++) {
				ShareSellerInfo shareSellerInfo = (ShareSellerInfo) tblShareList.get(i);
				if (ShareModelEnum.sharePerson.equals(shareSellerInfo.getShareModel())) {
					UserInfo userInfo = shareSellerInfo.getSeller();
					String userID = userInfo.getId().toString();
					// �������ж�����Ϊ���Ƴ�ԭ����������۹��ʣ����û���ˡ���ֱ�ӿ��԰������ļӵ�set��ȥ��
					if (salmanList.size() > 0) {
						for (int j = 0; j < salmanList.size(); j++) {
							ShareSellerInfo shareSellerInfo2 = (ShareSellerInfo) salmanList.get(j);
							UserInfo userInfo2 = shareSellerInfo2.getSeller();
							String userID2 = userInfo2.getId().toString();
							// �������ͬ��ȥ����ֵͬ������ֵ�ӵ�set�У������ظ�ֵ
							if (userID.equals(userID2)) {
								UserInfo operationPerson = shareSellerInfo.getOperationPerson();
								UserInfo operationPerson2 = shareSellerInfo2.getOperationPerson();
								// ͬһ����������۹��ʵĹ�������˲�����һ���������һ������ü�¼�Ͱ�ǰ�����������
								// Ҳ����˵����м�����ͬʱ��ĳ�ͻ��������ͬһ���˵�ʱ�������Ŀ��Եģ�
								// ��������˶Ըÿͻ�ӵ�е�Ȩ��
								// ȡ���Ȩ�ޣ�����Ĺ����ŶӺ���֯��һ����
								if (operationPerson.getId().toString().equals(operationPerson2.getId().toString())) {
									newSet.add(shareSellerInfo);
									salmanList.remove(shareSellerInfo2);
									break;
								} else {
									newSet.add(shareSellerInfo);
								}
							} else {
								newSet.add(shareSellerInfo);
							}
						}
					} else {
						newSet.add(shareSellerInfo);
					}
				} else if (ShareModelEnum.shareMarket.equals(shareSellerInfo.getShareModel())) {
					MarketingUnitInfo marketInfo = shareSellerInfo.getMarketingUnit();
					if (marketUnitList.size() > 0) {
						for (int k = 0; k < marketUnitList.size(); k++) {
							ShareSellerInfo shareSellerInfo4 = (ShareSellerInfo) marketUnitList.get(k);
							MarketingUnitInfo markInfo2 = shareSellerInfo4.getMarketingUnit();
							if (marketInfo.getId().toString().equals(markInfo2.getId().toString())) {
								UserInfo operationPerson = shareSellerInfo.getOperationPerson();
								UserInfo operationPerson2 = shareSellerInfo4.getOperationPerson();
								if (operationPerson.getId().toString().equals(operationPerson2.getId().toString())) {
									newSet.add(shareSellerInfo);
									marketUnitList.remove(shareSellerInfo4);
									break;
								} else {
									newSet.add(shareSellerInfo);
								}
							} else {
								newSet.add(shareSellerInfo);
							}
						}
					} else {
						newSet.add(shareSellerInfo);
					}
				} else if (ShareModelEnum.shareOrgUnit.equals(shareSellerInfo.getShareModel())) {
					FullOrgUnitInfo orgUnitInfo = shareSellerInfo.getOrgUnit();
					if (orgUnitList.size() > 0) {
						for (int m = 0; m < orgUnitList.size(); m++) {
							ShareSellerInfo shareSellerInfo5 = (ShareSellerInfo) orgUnitList.get(m);
							FullOrgUnitInfo orgUnitInfo2 = shareSellerInfo5.getOrgUnit();
							if (orgUnitInfo.getId().toString().endsWith(orgUnitInfo2.getId().toString())) {
								UserInfo operationPerson = shareSellerInfo.getOperationPerson();
								UserInfo operationPerson2 = shareSellerInfo5.getOperationPerson();
								if (operationPerson.getId().toString().equals(operationPerson2.getId().toString())) {
									newSet.add(shareSellerInfo);
									orgUnitList.remove(shareSellerInfo5);
									break;
								} else {
									newSet.add(shareSellerInfo);
								}
							} else {
								newSet.add(shareSellerInfo);
							}
						}
					} else {
						newSet.add(shareSellerInfo);
					}
				}
			}
			// ����Ƚ�����ԭ���Ĺ������۹���list�л���ֵ����ô��ʣ�µ�ֵҲ�ӵ�set�С���ʱ��Ľ������
			// ȥ������ֵͬ�������˲�ֵͬ�Ľ��
			if (salmanList.size() > 0) {
				for (int m = 0; m < salmanList.size(); m++) {
					ShareSellerInfo shareSellerInfo2 = (ShareSellerInfo) salmanList.get(m);
					newSet.add(shareSellerInfo2);
				}
			}
			if (marketUnitList.size() > 0) {
				for (int m = 0; m < marketUnitList.size(); m++) {
					ShareSellerInfo shareSellerInfo2 = (ShareSellerInfo) marketUnitList.get(m);
					newSet.add(shareSellerInfo2);
				}
			}
			if (orgUnitList.size() > 0) {
				for (int m = 0; m < orgUnitList.size(); m++) {
					ShareSellerInfo shareSellerInfo2 = (ShareSellerInfo) orgUnitList.get(m);
					newSet.add(shareSellerInfo2);
				}
			}
		}
		return newSet;
	}

	/***
	 * ���ݴ����������Ŀ��ţ��������ƣ��������ͼ��뼯�Źܿص������õĵ���(number)���ˡ� ��������һ��Ϊ�������ϵ����򵥼ۡ���Դ���۵�
	 * ��������һ��Ϊ���øû������ϵı�������ʵ�塣 ��Ҫ��Ӽ��Źܿص������õ��йع��ˡ�
	 * */

	public static FilterInfo setEnactmentScopeFilter(String sellProjectId, String archivesName, String archivesType, FilterInfo filter) {
		ProjectArchivesEntryCollection paCol = null;
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filterInfo = new FilterInfo();
		SelectorItemCollection sel = new SelectorItemCollection();
		sel.add("*");
		sel.add("archivesEntrys.*");
		if (sellProjectId != null) {
			filterInfo.getFilterItems().add(new FilterItemInfo("sellProject.id", sellProjectId));
		}
		if (archivesName != null) {
			filterInfo.getFilterItems().add(new FilterItemInfo("archivesName", archivesName));
		}
		if (archivesType != null) {
			filterInfo.getFilterItems().add(new FilterItemInfo("archivesType", archivesType));
		}

		view.setSelector(sel);
		view.setFilter(filterInfo);
		try {
			paCol = ProjectArchivesEntryFactory.getRemoteInstance().getProjectArchivesEntryCollection(view);
		} catch (BOSException e) {
			e.printStackTrace();
		}
		for (int i = 0; i < paCol.size(); i++) {
			ProjectArchivesEntryInfo info = paCol.get(i);
			if (info != null) {
				if (info.getScopeType().equals(ScopeTypeEnum.EnactmentScope)) {
					ArchivesEntryCollection aeCol = info.getArchivesEntrys();
					Set idSet = new HashSet();
					idSet.add("null");
					for (int j = 0; j < aeCol.size(); j++) {
						ArchivesEntryInfo arInfo = aeCol.get(j);
						if (arInfo != null) {
							idSet.add(arInfo.getDescription().toString());
						}
					}
					if (idSet.size() > 1) {
						filter.getFilterItems().add(new FilterItemInfo("id", idSet, CompareType.INCLUDE));
					}
				}
			}
		}

		return filter;
	}

	/***
	 * ���ݴ����������Ŀ��ţ��������Ƽ��뼯�Źܿص������õĵ���(number)���ˡ� ��������һ��Ϊ�������ϵ����򵥼ۡ���Դ���۵�
	 * ��������һ��Ϊ���øû������ϵı�������ʵ�塣 ��Ҫ��Ӽ��Źܿص������õ��йع��ˡ�
	 * */

	public static FilterInfo setEnactmentScopeFilter(String sellProjectId, String archivesName, FilterInfo filter) {
		ProjectArchivesEntryCollection paCol = null;
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filterInfo = new FilterInfo();
		SelectorItemCollection sel = new SelectorItemCollection();
		sel.add("*");
		sel.add("archivesEntrys.*");
		if (sellProjectId != null) {
			filterInfo.getFilterItems().add(new FilterItemInfo("sellProject.id", sellProjectId));
		}
		if (archivesName != null) {
			filterInfo.getFilterItems().add(new FilterItemInfo("archivesName", archivesName));
		}

		view.setSelector(sel);
		view.setFilter(filterInfo);
		try {
			paCol = ProjectArchivesEntryFactory.getRemoteInstance().getProjectArchivesEntryCollection(view);
		} catch (BOSException e) {
			e.printStackTrace();
		}
		for (int i = 0; i < paCol.size(); i++) {
			ProjectArchivesEntryInfo info = paCol.get(i);
			if (info != null) {
				if (info.getScopeType().equals(ScopeTypeEnum.EnactmentScope)) {
					ArchivesEntryCollection aeCol = info.getArchivesEntrys();
					Set idSet = new HashSet();
					idSet.add("null");
					for (int j = 0; j < aeCol.size(); j++) {
						ArchivesEntryInfo arInfo = aeCol.get(j);
						if (arInfo != null) {
							idSet.add(arInfo.getDescription().toString());
						}
					}
					if (idSet.size() > 1) {
						filter.getFilterItems().add(new FilterItemInfo("id", idSet, CompareType.INCLUDE));
					}
				}
			}
		}

		return filter;
	}

	/***
	 * ���ݴ����������Ŀ��ţ��������ƣ��������͵õ����Źܿص������õĵ���(number)���˵�View�� ��������һ��Ϊ�������ϵ����򵥼ۡ���Դ���۵�
	 * ��������һ��Ϊ���øû������ϵı�������ʵ�塣 ��Ҫ��Ӽ��Źܿص������õ��йع��ˡ�
	 * */

	public static EntityViewInfo getEnactmentScopeView(String sellProjectId, String archivesName, String archivesType) {
		ProjectArchivesEntryCollection paCol = null;
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filterInfo = new FilterInfo();
		SelectorItemCollection sel = new SelectorItemCollection();
		sel.add("*");
		sel.add("archivesEntrys.*");
		if (sellProjectId != null) {
			filterInfo.getFilterItems().add(new FilterItemInfo("sellProject.id", sellProjectId));
		}
		if (archivesName != null) {
			filterInfo.getFilterItems().add(new FilterItemInfo("archivesName", archivesName));
		}
		if (archivesType != null) {
			filterInfo.getFilterItems().add(new FilterItemInfo("archivesType", archivesType));
		}

		view.setSelector(sel);
		view.setFilter(filterInfo);
		try {
			paCol = ProjectArchivesEntryFactory.getRemoteInstance().getProjectArchivesEntryCollection(view);
		} catch (BOSException e) {
			e.printStackTrace();
		}

		EntityViewInfo view1 = new EntityViewInfo();
		FilterInfo filterInfo1 = new FilterInfo();
		for (int i = 0; i < paCol.size(); i++) {
			ProjectArchivesEntryInfo info = paCol.get(i);
			if (info != null) {
				if (info.getScopeType().equals(ScopeTypeEnum.EnactmentScope)) {
					ArchivesEntryCollection aeCol = info.getArchivesEntrys();
					Set idSet = new HashSet();
					idSet.add("null");
					for (int j = 0; j < aeCol.size(); j++) {
						ArchivesEntryInfo arInfo = aeCol.get(j);
						if (arInfo != null) {
							idSet.add(arInfo.getDescription().toString());
						}
					}
					if (idSet.size() > 1) {
						filterInfo1.getFilterItems().add(new FilterItemInfo("id", idSet, CompareType.INCLUDE));
					}
				}
			}
		}
		view1.setFilter(filterInfo1);
		return view1;
	}

	/***
	 * ���ݴ����������Ŀ��ţ��������Ƶõ����Źܿص������õĵ���(number)���˵�View�� ��������һ��Ϊ�������ϵ����򵥼ۡ���Դ���۵�
	 * ��������һ��Ϊ���øû������ϵı�������ʵ�塣 ��Ҫ��Ӽ��Źܿص������õ��йع��ˡ�
	 * */

	public static EntityViewInfo getEnactmentScopeView(String sellProjectId, String archivesName) {
		ProjectArchivesEntryCollection paCol = null;
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filterInfo = new FilterInfo();
		SelectorItemCollection sel = new SelectorItemCollection();
		sel.add("*");
		sel.add("archivesEntrys.*");
		if (sellProjectId != null) {
			filterInfo.getFilterItems().add(new FilterItemInfo("sellProject.id", sellProjectId));
		}
		if (archivesName != null) {
			filterInfo.getFilterItems().add(new FilterItemInfo("archivesName", archivesName));
		}

		view.setSelector(sel);
		view.setFilter(filterInfo);
		try {
			paCol = ProjectArchivesEntryFactory.getRemoteInstance().getProjectArchivesEntryCollection(view);
		} catch (BOSException e) {
			e.printStackTrace();
		}

		EntityViewInfo view1 = new EntityViewInfo();
		FilterInfo filterInfo1 = new FilterInfo();
		for (int i = 0; i < paCol.size(); i++) {
			ProjectArchivesEntryInfo info = paCol.get(i);
			if (info != null) {
				if (info.getScopeType().equals(ScopeTypeEnum.EnactmentScope)) {
					ArchivesEntryCollection aeCol = info.getArchivesEntrys();
					Set idSet = new HashSet();
					idSet.add("null");
					for (int j = 0; j < aeCol.size(); j++) {
						ArchivesEntryInfo arInfo = aeCol.get(j);
						if (arInfo != null) {
							idSet.add(arInfo.getDescription().toString());
						}
					}
					if (idSet.size() > 1) {
						filterInfo1.getFilterItems().add(new FilterItemInfo("id", idSet, CompareType.INCLUDE));
					}
				}
			}
		}
		view1.setFilter(filterInfo1);
		return view1;
	}

	/***
	 * ���ݴ����F7�ؼ���������Ŀ��ţ��������Ƶõ����Źܿص������õĵ���(number)���˵�View��
	 * ��Ҫ��Ӹ�F7�ؼ����Źܿص������õ��йع��ˡ�����ÿؼ�֮ǰ������EntiryviewInfo����ϲ���View��filter
	 * ���û�����Զ���Ӹÿؼ���EntiryviewInfo��Filter��
	 * */

	public static void SetGroupFilters(KDBizPromptBox prmtBox, String sellProjectId, String archivesName, String archivesType) {
		ProjectArchivesEntryCollection paCol = null;
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filterInfo = new FilterInfo();
		SelectorItemCollection sel = new SelectorItemCollection();
		sel.add("*");
		sel.add("archivesEntrys.*");
		if (sellProjectId != null) {
			filterInfo.getFilterItems().add(new FilterItemInfo("sellProject.id", sellProjectId));
		}
		if (archivesName != null) {
			filterInfo.getFilterItems().add(new FilterItemInfo("archivesName", archivesName));
		}
		if (archivesType != null) {
			filterInfo.getFilterItems().add(new FilterItemInfo("archivesType", archivesType));
		}
		view.setSelector(sel);
		view.setFilter(filterInfo);
		try {
			paCol = ProjectArchivesEntryFactory.getRemoteInstance().getProjectArchivesEntryCollection(view);
		} catch (BOSException e) {
			e.printStackTrace();
		}
		FilterInfo filterInfo1 = new FilterInfo();
		for (int i = 0; i < paCol.size(); i++) {
			ProjectArchivesEntryInfo info = paCol.get(i);
			if (info != null) {
				if (info.getScopeType().equals(ScopeTypeEnum.EnactmentScope)) {
					ArchivesEntryCollection aeCol = info.getArchivesEntrys();
					Set idSet = new HashSet();
					idSet.add("null");
					for (int j = 0; j < aeCol.size(); j++) {
						ArchivesEntryInfo arInfo = aeCol.get(j);
						if (arInfo != null) {
							idSet.add(arInfo.getDescription().toString());
						}
					}
					if (idSet.size() > 1) {
						filterInfo1.getFilterItems().add(new FilterItemInfo("id", idSet, CompareType.INCLUDE));
					}
				}
			}
		}
		if (prmtBox.getEntityViewInfo() != null) {
			try {
				prmtBox.getEntityViewInfo().getFilter().mergeFilter(filterInfo1, "And");
			} catch (BOSException e) {
				e.printStackTrace();
			}
		} else {
			EntityViewInfo view1 = new EntityViewInfo();
			view1.setFilter(filterInfo1);
			prmtBox.setEntityViewInfo(view1);
		}
	}

	/**
	 * У��ҵ�������������ڼ��Ƿ����½�.����Ѿ��½�,��ʾ����ʾ,���жϺ�������
	 * 
	 * @param bizDate
	 *            ҵ������
	 * @param balanceEndDate
	 *            ������Ŀ�Ľ����ֹ����
	 * */
	public static void verifyBalance(Date bizDate, Date balanceEndDate) {
		if (bizDate != null && balanceEndDate != null) {
			bizDate = FDCDateHelper.getDayBegin(bizDate);
			if (!bizDate.after(balanceEndDate)) {
				MsgBox.showInfo("��ҵ�������������ڼ��Ѿ����㣬���ܲ�����");
				SysUtil.abort();
			}
		}
	}

	/**
	 * ��ǰһ�����һ��
	 * 
	 * @return
	 */
	public static java.util.Date getNextDay(Date date) {
		Calendar cal = new GregorianCalendar();
		cal.clear();
		cal.setTime(date);
		cal.set(Calendar.DATE, cal.get(Calendar.DATE) + 1);
		return cal.getTime();
	}

	/**
	 * ��ǰ���ڵ�ǰһ�졣
	 * 
	 * @return
	 */
	public static java.util.Date getPreDay(Date date) {
		Calendar cal = new GregorianCalendar();
		cal.clear();
		cal.setTime(date);
		cal.set(Calendar.DATE, cal.get(Calendar.DATE) - 1);
		return cal.getTime();
	}

	/**
	 * ��ǰһ�����һ��
	 * 
	 * @return
	 */
	public static java.util.Date getNextMonth(Date date) {
		Calendar cal = new GregorianCalendar();
		cal.clear();
		cal.setTime(date);
		cal.set(Calendar.MONTH, cal.get(Calendar.MONTH) + 1);
		return cal.getTime();
	}

	/**
	 * ��ǰ���ڴ����ǰһ�졣
	 * 
	 * @return
	 */
	public static java.util.Date getNextMonthPreDate(Date date) {
		Calendar cal = new GregorianCalendar();
		cal.clear();
		cal.setTime(date);
		cal.set(Calendar.MONTH, cal.get(Calendar.MONTH) + 1);
		cal.set(Calendar.DATE, cal.get(Calendar.DATE) - 1);
		return cal.getTime();
	}

	/**
	 * ��ǰһ����賿ʱ�䡣
	 * 
	 * @return
	 */
	public static java.util.Date getDayStart(Date date) {
		Calendar cal = new GregorianCalendar();
		cal.clear();
		cal.setTime(date);
		cal.clear(Calendar.HOUR_OF_DAY);
		cal.clear(Calendar.MINUTE);
		cal.clear(Calendar.SECOND);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		return cal.getTime();
	}

	/**
	 * ��ǰһ�����ҹʱ�䡣
	 * 
	 * @return
	 */
	public static java.util.Date getDayEnd(Date date) {
		Calendar cal = new GregorianCalendar();
		cal.clear();
		cal.setTime(date);
		cal.clear(Calendar.HOUR_OF_DAY);
		cal.clear(Calendar.MINUTE);
		cal.clear(Calendar.SECOND);
		cal.set(Calendar.HOUR_OF_DAY, 23);
		cal.set(Calendar.MINUTE, 59);
		cal.set(Calendar.SECOND, 59);
		cal.set(Calendar.MILLISECOND, 59);
		return cal.getTime();
	}

	/**
	 * jeegan��BT,�����˸�����������:��int��ʾ����(���������2009-09-25,��Ӧ��intֵΪ20090925)
	 * �ú������ڽ�����int��ʾ������ת��ΪDate����,���ת�������쳣�򷵻�null
	 * */
	public static Date toDate(int num) {
		String str = Integer.toString(num);
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
		Date res = null;
		try {
			res = format.parse(str);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return res;
	}

	/**
	 * ��������ȡ��̨����
	 * 
	 * @author:xmcy ����ʱ�䣺2006-11-21
	 */
	public static Timestamp getServerDate() throws Exception {
		return FDCSQLFacadeFactory.getRemoteInstance().getServerTime();

	}
	
	//У���Ѱ�����ɵİ��ҵ��Ƿ�����޸�ɾ��
	public static boolean isCanEdit(Set purIDSet,String projectID,MoneyTypeEnum monType,RoomDisplaySetting roomSetting) throws BOSException, SQLException
	{
		boolean boo = false;
		FunctionSetting proSet = (FunctionSetting)roomSetting.getFunctionSetMap().get((projectID));
		boolean isLoanReceiving = proSet==null?false:((Boolean)proSet.getIsLoanReceiving()).booleanValue();
		//����ǰ�����ɲ����տ�Ļ����ж��Ƿ��Ѿ��տ����������޸�
		if(isLoanReceiving)
		{
			FDCSQLBuilder builder = new FDCSQLBuilder();
			builder.appendSql(" select count(*) count from T_SHE_Purchase purchase ");
			builder.appendSql(" left join T_SHE_PurchasePayListEntry payList on payList.fHeadID = purchase.fid ");
			builder.appendSql(" left join T_SHE_moneyDefine moneyDefine on payList.fMoneyDefineID = moneyDefine.fid where ");
			builder.appendParam("purchase.fid", purIDSet.toArray());
			builder.appendSql(" and isnull(payList.FActRevAmount,0)>0 ");
//			MoneyTypeEnum monType = (MoneyTypeEnum)monTypeList.get(0);
			if(MoneyTypeEnum.LoanAmount.equals(monType))
			{
				builder.appendSql(" and moneyDefine.FMoneyType='LoanAmount' ");
			}else if(MoneyTypeEnum.AccFundAmount.equals(monType))
			{
				builder.appendSql(" and moneyDefine.FMoneyType='AccFundAmount' ");
			}
			IRowSet countSet = builder.executeQuery();
			while (countSet.next()) {
				int count = countSet.getInt("count");
				if(count>0)
				{
					boo = true;
				}					
			}
		}else
		{
			boo = false;
		}
		
		return boo;
	}

	public static Date getServerDate2() {
		try {
			Timestamp serTime = getServerDate();
			return new Date(serTime.getTime());
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	// ͬһ��Ӫ����Ա��ͬһ����Ŀ��ֻ��һ��ְ�ܣ�Ҳ����ͬһ������ͬһ����Ŀ��ÿһ��ְ�ܶ�ֻ����һ��Ӫ����Ԫ��
	public static MarketingUnitInfo getMarketingUnit(UserInfo salesMan, SellProjectInfo project) throws BOSException {
		Set marketIDSet = new HashSet();
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		view.getSelector().add("head.*");
		filter.getFilterItems().add(new FilterItemInfo("member.id", salesMan.getId().toString()));
		// filter.getFilterItems()
		// .add(new FilterItemInfo("sellProject.sellProject.id",
		// project.getId().toString()));
		filter.getFilterItems().add(new FilterItemInfo("isSellFunction", Boolean.TRUE));
		// �����������õģ�������û��ְ����Ӫ����Ԫд��
		// �ϸ��������Ϲ�����֮ǰ����û����ְ��ְԱ
		// filter.getFilterItems()
		// .add(new
		// FilterItemInfo("accessionDate",bizDate,CompareType.LESS_EQUALS));
		// filter.getFilterItems()
		// .add(new FilterItemInfo("dimissionDate",null));
		// filter.getFilterItems().
		// add(new
		// FilterItemInfo("dimissionDate",bizDate,CompareType.GREATER_EQUALS));
		// filter.setMaskString("#0 and #1 and #2 and (#3 or #4)");
		view.setFilter(filter);

		MarketingUnitMemberCollection marketMemberColl = MarketingUnitMemberFactory.getRemoteInstance().getMarketingUnitMemberCollection(view);
		for (int i = 0; i < marketMemberColl.size(); i++) {
			MarketingUnitInfo marketInfo = marketMemberColl.get(i).getHead();
			marketIDSet.add(marketInfo.getId().toString());
		}

		EntityViewInfo view2 = new EntityViewInfo();
		FilterInfo filter2 = new FilterInfo();
		view2.getSelector().add("head.*");
		view2.getSelector().add("head.member.*");
		filter2.getFilterItems().add(new FilterItemInfo("head.id", marketIDSet, CompareType.INCLUDE));
		filter2.getFilterItems().add(new FilterItemInfo("sellProject.id", project.getId().toString()));
		view2.setFilter(filter2);
		MarketingUnitSellProjectCollection marketProjectColl = MarketingUnitSellProjectFactory.getRemoteInstance().getMarketingUnitSellProjectCollection(view2);
		// MarketingUnitCollection
		// marketColl=MarketingUnitFactory.getLocalInstance
		// (ctx).getMarketingUnitCollection(view);
		MarketingUnitInfo marketInfo = null;
		if (marketProjectColl.size() != 0) {
			marketInfo = new MarketingUnitInfo();
			marketInfo = marketProjectColl.get(0).getHead();
		}
		return marketInfo;
	}
	
	
	/**
	 *  ��ǰ��֯�Ƿ��ǲ���ʵ����֯��������򷵻���֯��FullOrgUnit�����򷵻�null
	 *  @author tim_gao
	 *  @description ����ҵ����PPMMEterHelper���������ģ���Ϊ��������¥���ã���ҵhelper
	 *  @date 2011-4-11
	 * @return
	 */
	public static  FullOrgUnitInfo checkCurrentBizFIOrg(){
		 CompanyOrgUnitInfo fiUnit = SysContext.getSysContext().getCurrentFIUnit();
		 OrgUnitInfo currOrgUnit = SysContext.getSysContext().getCurrentOrgUnit();
		 if(currOrgUnit  != null && fiUnit.isIsBizUnit() 
				 && fiUnit.getId().equals(currOrgUnit.getId())){
			  return fiUnit.castToFullOrgUnitInfo();
		 }
		 return  null;
	}
	
	public static void initF7(KDBizPromptBox box, String queryInfo, FilterInfo filterInfo) {
		EntityViewInfo evi = new EntityViewInfo();
		evi.setFilter(filterInfo);
		box.setQueryInfo(queryInfo);
		box.setEntityViewInfo(evi);
		box.setEditable(true);
		box.setDisplayFormat("$name$");
		box.setEditFormat("$number$");
		box.setCommitFormat("$number$");

	}
	
	/**
	 * �ַ����Ƿ������������
	 */
	public static boolean isInclude(String str,List list)
	{
		boolean result = false;
		for(int i=0;i<list.size();i++)
		{
			if(list.get(i)!=null && str!=null)
			{
				if(str.equals(list.get(i).toString()))
				{
					result = true;
					return result;
				}else
				{
					result = false;
				}
			}			
		}
		return result;
	}
	
	
	
	

	/**
	 * @description  ���䶨����д
	 * @author tim_gao
	 * @param table
	 * @param roomC
	 * @param setting
	 * @param unitCol
	 * @param root
	 * @throws BOSException
	 * @throws EASBizException
	 */
	public static void fillRoomTableByUnitCol(final KDTable table, RoomCollection roomC, IDisplayRule setting,BuildingUnitCollection unitCol,DefaultMutableTreeNode root) throws BOSException, EASBizException {
//		table.getStyleAttributes().setLocked(true);
//		table.getStyleAttributes().setHorizontalAlign(HorizontalAlignment.CENTER);
//		table.getIndexColumn().getStyleAttributes().setHided(true);
//		table.removeColumns();
//		table.removeHeadRows();
//		table.removeRows();
//		int minRow = 9999;
//		int maxRow = -1;
//		int minCol = 9999;
//		int maxCol = -1;
//		String key = null;
//			for (int i = 0; i < rooms.size(); i++) {
//				RoomInfo room = rooms.get(i);
//				String buildingId = room.getBuilding().getId().toString();
//				// int unitNum =
//				// room.getBuildUnit()==null?0:room.getBuildUnit().getSeq();
//				int unitNum = room.getUnit();
//				if (key == null) {
//					key = buildingId + unitNum;
//				}
//				if (room.getFloor() < minRow) {
//					minRow = room.getFloor();
//				}
//				if (room.getFloor() > maxRow) {
//					maxRow = room.getFloor();
//				}
//				if (room.getSerialNumber() < minCol) {
//					minCol = room.getSerialNumber();
//				}
//				if (room.getEndSerialNumber() > maxCol) {
//					maxCol = room.getEndSerialNumber();
//				}
//			}
//			if (minRow <= maxRow && minCol <= maxCol) {
//				IColumn column = table.addColumn();
//				column.setWidth(30);
//				column.setKey(0 + "");
//				for (int i = minCol; i <= maxCol; i++) {
//					column = table.addColumn();
//					column.setWidth(setting.getRoomWidth());
//					column.getStyleAttributes().setWrapText(true);
//					column.setKey(key + i);
//				}
//				IRow headRow = table.addHeadRow();
//				headRow.getCell(0 + "").setValue("¥��");
//				for (int i = minCol; i <= maxCol; i++) {
//					headRow.getCell(key + i).setValue(i + "��");
//				}
//				for (int i = minRow; i <= maxRow; i++) {
//					IRow row = table.addRow();
//					row.setUserObject(new Integer(i));
//					row.setHeight(setting.getRoomHeight());
//					row.getCell(0 + "").setValue(new Integer(maxRow - i + minRow) + "��");
//				}
//			}
//			fillRoomTable(table, rooms, setting, null, null, false,null);
//			
		
		if (root.getDepth() > 10) {
			MsgBox.showInfo("̫�����֯��,���ܲ�ѯ!");
		}
		//by tim_gao
		
		
		table.getStyleAttributes().setLocked(true);
		table.getStyleAttributes().setHorizontalAlign(HorizontalAlignment.CENTER);
		table.getIndexColumn().getStyleAttributes().setHided(true);
		table.getStyleAttributes().setWrapText(true);
		table.setHorizonGridLineVisible(true);
		table.setVerticalGridLineVisible(true);
		table.setVerticalHeadGridLineVisible(true);
		table.setHorizonHeadGridLineVisible(true);
		table.removeColumns();
		table.removeHeadRows();
		table.removeRows();

		String roomFilterSql = "";
		
			roomFilterSql += "and SellProject.fid = '" ;
		// ֻ��ʾָ���ķ���
			String restirctRoomIdStr = "";
			
				restirctRoomIdStr += ",'" +  "'";
			restirctRoomIdStr = restirctRoomIdStr.replaceFirst(",", "");
			roomFilterSql = "and Room.fid in (" + restirctRoomIdStr + ") ";
	

		// ѡ��Ľڵ�仯ʱ
		Object objectNode = root.getUserObject();
		if (objectNode instanceof BuildingUnitInfo) { // ��Ԫ����
			BuildingUnitInfo buildUnit = (BuildingUnitInfo) objectNode;
			roomFilterSql += "and BuildUnit.fid = '" + buildUnit.getId().toString() + "'";
		} else if (objectNode instanceof BuildingInfo) { // ¥��
			BuildingInfo building = (BuildingInfo) objectNode;
			roomFilterSql += "and Build.fid = '" + building.getId().toString() + "'";
		} else if (objectNode instanceof SubareaInfo) { // ����
			SubareaInfo subAreaInfo = (SubareaInfo) objectNode;
			roomFilterSql += "and SubArea.fid = '" + subAreaInfo.getId().toString() + "'";
		} else if (objectNode instanceof SellProjectInfo) {
			SellProjectInfo sellProInfo = (SellProjectInfo) objectNode;
			roomFilterSql += "and SellProject.fid = '" + sellProInfo.getId().toString() + "'";
		} else {// ��Ȳ������е�������Ŀ
			String sellProIdFilterStr = "'null'";
			Enumeration enumeration = root.breadthFirstEnumeration();
			while (enumeration.hasMoreElements()) {
				DefaultMutableTreeNode element = (DefaultMutableTreeNode) enumeration.nextElement();
				if (element.getUserObject() instanceof SellProjectInfo) {
					SellProjectInfo tempSellProInfo = (SellProjectInfo) element.getUserObject();
					sellProIdFilterStr += ",'" + tempSellProInfo.getId().toString() + "'";
				}
			}
			roomFilterSql += "and SellProject.fid in (" + sellProIdFilterStr + ")";
		}

//		if (moneySysTypeEnum != null) {
//			if (moneySysTypeEnum.equals(MoneySysTypeEnum.SalehouseSys))
//				roomFilterSql += " and Room.fisForSHE=1";
//			else if (moneySysTypeEnum.equals(MoneySysTypeEnum.TenancySys))
//				roomFilterSql += " and Room.fisForTen=1";
//			else if (moneySysTypeEnum.equals(MoneySysTypeEnum.ManageSys))
//				roomFilterSql += " and Room.fisForPPM=1";
//		}

		RoomCollection rooms = roomC;
		
	
		
		if (rooms.size() > 0) {
			Map buildingMaxFloor = new HashMap(); // ¥��id�����¥���ӳ��
			Map buildingMinFloor = new HashMap();
			Map buildingMaxNum = new HashMap(); // ¥��id�ͷ����������кŵ�ӳ��
			Map buildingMinNum = new HashMap();
			for (int i = 0; i < rooms.size(); i++) {
				RoomInfo room = rooms.get(i); 
				// int unitNum =
				// room.getBuildUnit()==null?0:room.getBuildUnit().getSeq();
				int unitNum = room.getUnit();
				String key = room.getBuilding().getId().toString() + unitNum;
				Integer maxFloor = (Integer) buildingMaxFloor.get(key);
				Integer minFloor = (Integer) buildingMinFloor.get(key);
				Integer maxNum = (Integer) buildingMaxNum.get(key);
				Integer minNum = (Integer) buildingMinNum.get(key);
				if (minFloor == null || room.getFloor() < minFloor.intValue()) {
					buildingMinFloor.put(key, new Integer(room.getFloor()));
				}
				if (maxFloor == null || room.getFloor() > maxFloor.intValue()) {
					buildingMaxFloor.put(key, new Integer(room.getFloor()));
				}
				if (minNum == null || room.getSerialNumber() < minNum.intValue()) {
					buildingMinNum.put(key, new Integer(room.getSerialNumber()));
				}
				if (maxNum == null || room.getEndSerialNumber() > maxNum.intValue()) {
					buildingMaxNum.put(key, new Integer(room.getEndSerialNumber()));
				}
			}
			IColumn column = table.addColumn();
			column.setKey("floor");
			column.setWidth(30);
			Enumeration enumeration = root.depthFirstEnumeration();
			boolean haveUnit = false;
			while (enumeration.hasMoreElements()) {
				DefaultMutableTreeNode element = (DefaultMutableTreeNode) enumeration.nextElement();
				Object object = element.getUserObject();
				String key = null;
				if (object instanceof Integer) { // ���滻Ϊ��Ԫ���� --- ��ʱ����
					int unit = ((Integer) object).intValue();
					DefaultMutableTreeNode parent = (DefaultMutableTreeNode) element.getParent();
					BuildingInfo building = (BuildingInfo) parent.getUserObject();
					key = building.getId().toString() + unit;
					haveUnit = true;
				} else if (object instanceof BuildingUnitInfo) {
					int unit = ((BuildingUnitInfo) object).getSeq();
					DefaultMutableTreeNode parent = (DefaultMutableTreeNode) element.getParent();
					BuildingInfo building = (BuildingInfo) parent.getUserObject();
					key = building.getId().toString() + unit;
					haveUnit = true;
				
				} else if (object instanceof BuildingInfo) {
					BuildingInfo building = (BuildingInfo) object;
					key = building.getId().toString() + 0;
				}
				Integer minNum = (Integer) buildingMinNum.get(key);
				Integer maxNum = (Integer) buildingMaxNum.get(key);
				if (minNum == null || maxNum == null) {
					continue;
				}
				for (int i = minNum.intValue(); i <= maxNum.intValue(); i++) { // ��������
					column = table.addColumn();
					column.setKey(key + i);
					column.setWidth(setting.getRoomWidth());
				}
			}
			int minRow = 1;
			int maxRow = 0;
			Collection collection = buildingMaxFloor.values();
			for (Iterator iter = collection.iterator(); iter.hasNext();) {
				Integer num = (Integer) iter.next();
				if (num.intValue() > maxRow)
					maxRow = num.intValue();
			}
			// ���϶���С¥�㣬���� ����¥���֧�֣� �㲻�� ���� Ϊʲô�����ַ��� ȥ�����¥�㣬���Ǽ�Ȼ������ô�ã���Ҳ�������ȥ����С¥��
			// by laiquan_luo
			Collection tempColl = buildingMinFloor.values();
			for (Iterator iter = tempColl.iterator(); iter.hasNext();) {
				Integer num = (Integer) iter.next();
				if (num.intValue() < minRow)
					minRow = num.intValue();
			}
			
//			//���ӶԱ�Ԫ�ļ���д������Ļ���ÿ�ζ����1��
//			addtableListener(table);

			for (int i = minRow; i <= maxRow; i++) {

				IRow row = table.addRow();
				row.setUserObject(new Integer(i));

				row.setHeight(setting.getRoomHeight());

				int currFloor = maxRow - i + minRow;
				row.getCell("floor").setValue(new Integer(currFloor));//¥��
				if(currFloor==0) row.getStyleAttributes().setHided(true);	//0������
			}
			//���巿��ʱΪtrue
			isDes = true;
			
			try {
				fillRoomTable(table, rooms, setting, null, null, false,null);
			} catch (EASBizException e) {
				// TODO Auto-generated catch block
				log.warn("����ͼ�����ʧ��"+e.getMessage());
			}
			//�滻��һ����ɫ
			
			table.getColumn("floor").getStyleAttributes().setLocked(false);
			table.getColumn("floor").getStyleAttributes().setBackground(Color.orange);
			
//			�����Ż� xin_wang 2011.03.31
			String text[] = null;
			if (haveUnit) {
				text = new String[] { "���", "��Ԫ", "¥��", "����", "��Ŀ", "��֯", "��֯1", "��֯2", "��֯3", "��֯4", "��֯5", "��֯6" }; // ���ڶ༶��֯�����������ֿ�
			} else {
				text = new String[] { "���", "¥��", "����", "��Ŀ", "��֯", "��֯1", "��֯2", "��֯3", "��֯4", "��֯5", "��֯6" };
			}
			for (int i = 0; i < root.getDepth() + 1; i++) { // ������ͷ
				IRow row = table.addHeadRow(0);
				row.getCell(0).setValue(text[i]);
			}

			int count = 1;
			if (root.getChildCount() == 0) {
				String key = null;
				if (objectNode instanceof BuildingInfo) {
					key = ((BuildingInfo) objectNode).getId().toString() + 0;
				} else if (objectNode instanceof Integer) { // ���滻Ϊ��Ԫ���� -- ��ʱ����
					int unit = ((Integer) objectNode).intValue();
					DefaultMutableTreeNode parent = (DefaultMutableTreeNode) root.getParent();
					BuildingInfo building = (BuildingInfo) parent.getUserObject();
					key = building.getId().toString() + unit;
				} else if (objectNode instanceof BuildingUnitInfo) {
					int unit = ((BuildingUnitInfo) objectNode).getSeq();
					DefaultMutableTreeNode parent = (DefaultMutableTreeNode) root.getParent();
					BuildingInfo building = (BuildingInfo) parent.getUserObject();
					key = building.getId().toString() + unit;
				} else {
					return;
				}
				Integer minNum = (Integer) buildingMinNum.get(key);
				Integer maxNum = (Integer) buildingMaxNum.get(key);
				if (minNum == null || maxNum == null) {
					return;
				}
				for (int i = minNum.intValue(); i <= maxNum.intValue(); i++) {
					table.getHeadRow(0).getCell(i + 1 - minNum.intValue()).setValue(new Integer(i));//���
					if(isConvert){
						table.getHeadRow(0).getCell(i + 1 - minNum.intValue()).setValue(getSerialNumberStr(i, true));//���
					}
					table.getHeadRow(0).getCell(i + 1 - minNum.intValue()).getStyleAttributes().setBackground(Color.orange);
				}
			} else {
				for (int i = 0; i < root.getChildCount(); i++) {
					count += setTableHeadByNodeForCreat(table, (DefaultMutableTreeNode) root.getChildAt(i), count, buildingMinNum, buildingMaxNum);
				}
			}
		}
//		//��ɾ������ʾ�Ľڵ�
//		delNodeFromTree(root);
	}
	
	
	

	
	public static Map delNode = null;
	/**
	 * @description ������ɾ���ڵ�ķ���
	 * @author tim_gao
	 * @param root
	 */
	public static void delNodeFromTree(DefaultMutableTreeNode root){
		if(null==delNode){
			return;
		}
		Iterator it = delNode.values().iterator();
		while(it.hasNext()){
			DefaultMutableTreeNode delNode = (DefaultMutableTreeNode) it.next();
			if(!(root.getUserObject() instanceof BuildingInfo)&&!(root.getUserObject() instanceof BuildingUnitInfo)){
				DefaultMutableTreeNode buildingNode = (DefaultMutableTreeNode) delNode.getParent();
				buildingNode.remove(delNode);
				
			}else if (root.getUserObject() instanceof BuildingInfo){
				root.remove(delNode);
			}
		}
		delNode = new HashMap();
	}
	/**
	 * @description ����ͼ����γ�����Ϊ�����е�Ԫ���������ڵ�Ԫ�в���ʾ����ʾ�����֣�����Ϊ����ʾ����ͼ���ڴ������ڵ�ʱ
	 * 				��¥���ϵĵ�Ԫ�ٹ���ȥ�����ڳ���ɿ�����fillRoomTableByUnitCol��������node�ڵ㹹��ͼ��
	 * 				������ڳ��������delNodeFromTree�����ڵ��ϲ���ʾ�Ľڵ�ɾ��
	 * @author tim_gao
	 * @param root
	 * @throws Exception
	 */
	public static DefaultMutableTreeNode setUnitSonNodeUpNode(DefaultMutableTreeNode root) throws Exception{
		//ƴװnode���޵�Ԫ��ʾ�Ľڵ�
		delNode = new HashMap();
		if (root.getDepth() > 10) {
			MsgBox.showInfo("̫�����֯��,���ܲ�ѯ!");
		}
//		
//		if((root.getUserObject() instanceof BuildingUnitInfo)){
//			return root;
//		}else{//�����A
//			//��ƴװһ��������
//			KDTree treeMain = new KDTree();
//			treeMain.setModel(FDCTreeHelper.getUnitTreeForSHE(actionOnLoad,MoneySysTypeEnum.SalehouseSys));//����
//			
//			Map buildingNodes = FDCTreeHelper.getAllObjectIdMap((TreeNode)treeMain.getModel().getRoot(),"Building");
//			if((root.getUserObject() instanceof BuildingInfo)){//¥���ڵ�
//				String id = ((BuildingInfo)root.getUserObject()).getId().toString();
//				
//				return root;
//			} else {//�����ڵ�
//				
//			Iterator it =  buildingNodes.keySet().iterator();
//			if(it.hasNext()){
//				String buildingId = (String) it.next();
//				
//			}
//				
//				return root;
//			} 
//		} 
//	
		Map buildingNodes = FDCTreeHelper.getAllObjectIdMap((TreeNode)root,"Building");
//		String id = ((BuildingInfo)root.getUserObject()).getId().toString();
//		root = (DefaultKingdeeTreeNode)buildingNodes.get(id);
		SellProjectInfo sellPro = null;
		if(null!=((DefaultKingdeeTreeNode)root).getUserObject()){//ѭ���õ���Ŀ����
			Object tempNode = ((DefaultKingdeeTreeNode)CRMTreeHelper.
					getSellProjectByNode((DefaultKingdeeTreeNode)root)).getUserObject();
			if(tempNode instanceof SellProjectInfo){
				sellPro = (SellProjectInfo) tempNode;
			}
		}
		Collection buildingColl =  buildingNodes.keySet();//�е�ѭ�������Ż�
		Set buildingSet = new HashSet();
		Iterator itBuild = buildingColl.iterator();
		while(itBuild.hasNext()){
			String buildingId = (String) itBuild.next();
			
			buildingSet.add(buildingId);
		}
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("building.id",buildingSet,CompareType.INCLUDE));
		filter.getFilterItems().add(new FilterItemInfo("haveUnit",Boolean.TRUE));
		if(null!=sellPro){
			filter.getFilterItems().add(new FilterItemInfo("building.sellProject.id",sellPro.getId()));
		}
		view.setFilter(filter);
		BuildingUnitCollection buildUnitColl = BuildingUnitFactory.getRemoteInstance().getBuildingUnitCollection(view);
		CRMHelper.sortCollection(buildUnitColl, "seq", true);
		
		Iterator it = buildingSet.iterator();
		while(it.hasNext()){
			String buildingId = (String) it.next();
		
		for (int j = 0; j < buildUnitColl.size(); j++) {
			BuildingUnitInfo buidUnitInfo = buildUnitColl.get(j);
			//ȷ���Ƿ���뵥Ԫ�ڵ�
			boolean isAdd = true;
			
				DefaultKingdeeTreeNode unitNode = new DefaultKingdeeTreeNode(buidUnitInfo);
//				sonNode.setCustomIcon(EASResource.getIcon("imgTree_folder_leaf"));	
				
				DefaultKingdeeTreeNode buildNode = (DefaultKingdeeTreeNode)buildingNodes.get(buildingId);
				
					Enumeration enumeration = buildNode.children();
					while (enumeration.hasMoreElements()) {//������鵥Ԫ�ٲ�������
						DefaultMutableTreeNode element = (DefaultMutableTreeNode) enumeration.nextElement();
						Object object = element.getUserObject();
						if(object instanceof BuildingUnitInfo){
							if(buidUnitInfo.getId().equals(((BuildingUnitInfo)object).getId())){
								isAdd = false;
							}
						}
					}
					if(isAdd){
						if(buildNode!=null&&null!=buidUnitInfo.getBuilding()) {//���ϵ�Ԫ
							
							if(buidUnitInfo.getBuilding().getId().toString().equals(buildingId)
									){//��Ϊ�Ǿ�̬��������ҪУ��ڵ��Ƿ��Ѿ�������
							
								buildNode.add(unitNode);
							
								delNode.put(buidUnitInfo.getId().toString(), unitNode);
							}
							
						}
					}
			
			}
		}
		
	
	 	
		
		return root;
	}
	
	/**
	 * @Description ��ӽ���ļ���
	 * @author tim_gao
	 * @param KDtable ��������  boolean �Ƿ�ת��Ϊ�ַ�
	 * 
	 */
	
	public static void addtableListener(final KDTable table){
		//���ӶԱ�Ԫ�ļ���
		table.addKDTEditListener(new com.kingdee.bos.ctrl.kdf.table.event.KDTEditAdapter() {
            public void editStopped(com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent e) {
            	 try {
					 rowAndColumTextField_DataChange(e,table);
	             } catch (Exception exc) {
	             	FDCMsgBox.showError("�����ŸĶ�����");
	             	log.warn("there is worng about the cellEditor of room !");
	             	SysUtil.abort();
	             } finally {
	             }
			}
        });
		
		 
	}
	
	
	
	/**
	 * @description ��д��setTableHeadByNode ��Ϊ��Ӱ�죬���ر�ͼ��չʾ
	 * @author tim_gao
	 * ������ѡ�ڵ����ñ�ͷ�ﵥԪ������� colIndex ĳһ¥����Ӧ���к� buildingMinNum ¥��id�뷿�����С��ŵ�ӳ��
	 * buildingMaxNum ¥��id�뷿��������ŵ�ӳ�� ���ص�ǰ�ڵ���������������
	 */
	private static int setTableHeadByNodeForCreat(final KDTable table, DefaultMutableTreeNode node, int colIndex, Map buildingMinNum, Map buildingMaxNum) {
		int childRowIndex = colIndex;

		for (int i = 0; i < node.getChildCount(); i++) {
			DefaultKingdeeTreeNode child = (DefaultKingdeeTreeNode) node.getChildAt(i);
			childRowIndex += setTableHeadByNode(table, child, childRowIndex, buildingMinNum, buildingMaxNum);
		}

		int currHeadRowNum = -1;
		Object object = node.getUserObject();
		if (object instanceof Integer) // ���滻Ϊ��Ԫ���� -- ��ʱ����
			currHeadRowNum = getHeadRowNumByName(table, "��Ԫ");
		else if (object instanceof BuildingUnitInfo)
			currHeadRowNum = getHeadRowNumByName(table, "��Ԫ");
		else if (object instanceof BuildingInfo)
			currHeadRowNum = getHeadRowNumByName(table, "¥��");
		else if (object instanceof SubareaInfo)
			currHeadRowNum = getHeadRowNumByName(table, "����");
		else if (object instanceof SellProjectInfo)
			currHeadRowNum = getHeadRowNumByName(table, "��Ŀ");
		else if (object instanceof OrgStructureInfo) {
			// ��ǰ��֯��headCell�����ǡ���֯�أ�������֯������֯��...?
			String osName = "��֯";
			boolean haveUnit = false;
			if (getHeadRowNumByName(table, "��Ԫ") > 0)
				haveUnit = true;
			if (haveUnit) {
				if (node.getDepth() - 4 > 0)
					osName += (node.getDepth() - 4);
			} else {
				if (node.getDepth() - 3 > 0)
					osName += (node.getDepth() - 3);
			}
			currHeadRowNum = getHeadRowNumByName(table, osName);
		}

		if (node.isLeaf()) { // ���Ҷ�ڵ�Ϊ¥����Ԫʱ������У�������������������
			String key = null;
			if (object instanceof BuildingInfo) {
				key = ((BuildingInfo) object).getId().toString() + 0;
			} else if (object instanceof Integer) { // ���滻Ϊ��Ԫ���� -- ��ʱ����
				int unit = ((Integer) object).intValue();
				DefaultMutableTreeNode parent = (DefaultMutableTreeNode) node.getParent();
				BuildingInfo building = (BuildingInfo) parent.getUserObject();
				key = building.getId().toString() + unit;
			} else if (object instanceof BuildingUnitInfo) {
				int unit = ((BuildingUnitInfo) object).getSeq();
				DefaultMutableTreeNode parent = (DefaultMutableTreeNode) node.getParent();
				BuildingInfo building = (BuildingInfo) parent.getUserObject();
				key = building.getId().toString() + unit;
			} else {
				return 0;
			}
			Integer minNum = (Integer) buildingMinNum.get(key);
			Integer maxNum = (Integer) buildingMaxNum.get(key);
			if (minNum == null || maxNum == null) {
				return 0;
			}

			for (int i = minNum.intValue(); i <= maxNum.intValue(); i++) { // ��ͷ������кűض�Ϊ
																			// '���'��
				final int cellNum=i + colIndex - minNum.intValue();
				ICell cell = table.getHeadRow(table.getHeadRowCount() - 1).getCell(cellNum);
				
				cell.setValue( setFillZeroInTxt(i,maxNum.intValue()));//���
				if(isConvert){
					cell.setValue( getSerialNumberStr(i, true));//���
				}
				
				cell.getStyleAttributes().setLocked(false);
				cell.getStyleAttributes().setBackground(Color.orange);
			}

			if (currHeadRowNum >= 0 && currHeadRowNum < table.getHeadRowCount()) {
				table.getHeadRow(currHeadRowNum).getCell(colIndex).setValue(object);
				//����ʾ��Ԫʱ��ͷ����ʾ��Ԫ by tim_gao
				if(object instanceof BuildingUnitInfo){
					if(((BuildingUnitInfo) object).isHaveUnit()){
						table.getHeadRow(currHeadRowNum).getCell(colIndex).setValue(null);
					}
				}
				table.getHeadMergeManager().mergeBlock(currHeadRowNum, colIndex, currHeadRowNum, colIndex + maxNum.intValue() - minNum.intValue());

				if (object instanceof BuildingInfo) { // ¥�����޵�Ԫ,��head���е�Ԫ�е����;
														// ¥�����޷�������head���з����е����
					int unitHeadRow = getHeadRowNumByName(table, "��Ԫ");
					if (unitHeadRow >= 0)
						table.getHeadMergeManager().mergeBlock(unitHeadRow, colIndex, unitHeadRow, colIndex + maxNum.intValue() - minNum.intValue());
					if (!(node.getParent() instanceof SubareaInfo)) {
						int subAreaHeadRow = getHeadRowNumByName(table, "����");
						if (subAreaHeadRow >= 0)
							table.getHeadMergeManager().mergeBlock(subAreaHeadRow, colIndex, subAreaHeadRow, colIndex + maxNum.intValue() - minNum.intValue());
					}
				}
			}

			return maxNum.intValue() - minNum.intValue() + 1;
		} else {
			if (currHeadRowNum >= 0) {
				for (int i = 0; i < childRowIndex - colIndex; i++) {
					table.getHeadRow(currHeadRowNum).getCell(colIndex + i).setValue(object);
					//����ʾ��Ԫʱ��ͷ����ʾ��Ԫ by tim_gao
					if(object instanceof BuildingUnitInfo){
						if(((BuildingUnitInfo) object).isHaveUnit()){
							table.getHeadRow(currHeadRowNum).getCell(colIndex).setValue(null);
						}
					}
				}
				table.getHeadMergeManager().mergeBlock(currHeadRowNum, colIndex, currHeadRowNum, childRowIndex - 1);
			}
			return childRowIndex - colIndex;
		}

	}

	
	/**
     * ����������ֵ�ļ���
	 * @return 
	 * @author tim_gao 
	 *  @param e �����¼�����
	 *   @param table Ҫ�ı�ı�
	 *    @param ICell ��Ԫ��
     */
    public static  void rowAndColumTextField_DataChange(KDTEditEvent e,KDTable table) throws Exception{
    			ICell activeCell = null;
    			//��֪����ô�жϣ��Ǳ�ͷ�����Ǳ�񣬾���ֵ�жϰ�
    			//��Ϊ��ͷֵ����
    			ICell checkCell = null;
    			
    			
    				String strColum =null;//���ұ仯
    				String strRow =null;//���±仯
    				String comRoom = null;
    				if(e.getType()!=KDTStyleConstants.HEAD_ROW){
    					checkCell = (ICell) table.getRow(e.getRowIndex()).getCell(e.getColIndex());
    				}
    				
    				if(0!=e.getColIndex()&&e.getType()!=KDTStyleConstants.HEAD_ROW&&false==checkCell.getStyleAttributes().isLocked()){//������ǵ�һ�п��Ա༭�Ļ���˵�����Ҽ��༭
    					//����ֵ�󷵻ز��ܱ༭״̬
    					checkCell.getStyleAttributes().setLocked(true);
    					if(Color.WHITE.equals(checkCell.getStyleAttributes().getBackground())){
    						RoomInfo checkRoom = (RoomInfo) checkCell.getUserObject();
    						if(null!=checkCell.getUserObject()){
    							
    							if(null!=checkRoom.getId()){
    								checkCell.getStyleAttributes().setBackground(Color.YELLOW);
    							}else{
    								checkCell.getStyleAttributes().setBackground(Color.CYAN);
    							}
    						}else{
    							checkCell.getStyleAttributes().setBackground(Color.GRAY);
    						}
    					}
    					return;
    				}else{
    				if(0==e.getColIndex()){//�����Ϊ0����Ϊ����ǰ��Ĳ�����
    					if(null!= table.getRow(e.getRowIndex()).getCell(e.getColIndex()).getValue()){
    	    				activeCell = (ICell) table.getRow(e.getRowIndex()).getCell(e.getColIndex());
    	    			}else{
    	    				return;
    	    			}
//    					activeCell = (ICell) table.getCell(e.getRowIndex(), e.getColIndex());
//    					activeCell.getValue().toString().indexOf("��");
    					strRow = activeCell.getValue().toString();
    					for(int i= 1 ; i < table.getColumnCount() ; i++ ){
    						ICell cellColum = table.getHeadRow(table.getHeadRowCount() - 1).getCell(i);//ÿ�б�ͷ
    						ICell cellValue = table.getCell(e.getRowIndex(), i); //����Ҫ�����������
    						if(Color.cyan.equals(cellValue.getStyleAttributes().getBackground())||Color.YELLOW.equals(cellValue.getStyleAttributes().getBackground())){
    							strColum = cellColum.getValue().toString();
        						
        						comRoom = strRow.toString()+strColum;
//        						if(strRow.intValue()>0){
//        							if(comRoom.length()<3){
//        								comRoom = strRow+"0"+strColum;
//        							}
//        						}else if(strRow.intValue()<0){
//        							if(comRoom.length()<4){
//        								comRoom = strRow+"0"+strColum;
//        							}
//        						}
        						
        						cellValue.setValue(comRoom);
    						}
    						
    					}
    				}else {//����״̬�¶��Ǻ�����

    					if(e.getType()==KDTStyleConstants.HEAD_ROW||e.getColIndex()==0){
    					if(null!= table.getHeadRow(e.getRowIndex()).getCell(e.getColIndex()).getValue()){
    	    				activeCell = (ICell) table.getHeadRow(e.getRowIndex()).getCell(e.getColIndex());
    	    			}else{
    	    				SysUtil.abort();
    	    			}
//    					activeCell = (ICell) table.getHeadRow(table.getHeadRowCount()-1).getCell(e.getColIndex());
    					strColum = activeCell.getValue().toString();
    					for(int i= 0 ; i < table.getRowCount(); i++ ){
    						ICell cellRow = table.getCell(i,0); //ÿ�б�ͷ
    						ICell cellValue = table.getCell(i, e.getColIndex());//ÿ��Ҫ�������
    						if(Color.cyan.equals( cellValue.getStyleAttributes().getBackground())||Color.YELLOW.equals( cellValue.getStyleAttributes().getBackground())){
    						strRow = cellRow.getValue().toString();
    						comRoom = strRow .toString()+ strColum;
    						
//    						if(strRow.intValue()>0){
//    							if(comRoom.length()<3){
//    								comRoom = strRow+"0"+strColum;
//    							}
//    						}else if(strRow.intValue()<0){
//    							if(comRoom.length()<4){
//    								comRoom = strRow+"0"+strColum;
//    							}
//    						}
    						cellValue.setValue(comRoom);
    					}
    				}
    				}
    				}
    			}
    		
//    			if(0==cell.getColumnIndex()){//����ֵ�ı仯
//        			//�ȵõ��еĸı�ֵ
//        			String strRow =null;
//        			if(null!=cell.getValue()){
//        				
//        				strRow = cell.getValue().toString().substring(0, cell.getValue().toString().indexOf("��"));
//        			}
//        			for(int i =0 ; i < table.getColumnCount() ; i++){
//        				ICell columnCell = table.getHeadRow(table.getHeadRowCount() - 1).getCell( i);
//        				String strRoomName = null;
//            			
//            			String strColumn =null;
//            			//ƴװ������
//            			if(null!=columnCell.getValue()){
//            				
//            				strColumn = columnCell.getValue().toString().substring(0, strColumn.indexOf("��"));
//            			}
//            			
//            			strRoomName =strRow+ strColumn;
//            			table.getRow(cell.getRowIndex()).getCell(i).setValue(strRoomName);
//        			}
//        			
//        			
//            	}else {//����ֵ�ı仯
//            			//�ȵõ��еĸı�ֵ
//            			String strColumn =null;
//            			if(null!=cell.getValue()){
//            				
//            				strColumn = cell.getValue().toString().substring(0, cell.getValue().toString().indexOf("��"));
//            			}
//            		for(int i =0 ; i < table.getRowCount() ; i++){
//            			ICell rowCell = table.getRow(i).getCell("floor");
//            			String strRoomName = null;
//            			String strRow =null;
//            			
//            			//ƴװ������
//            			
//            			
//            			if(null!=rowCell.getValue()){
//            				
//            				strRow = rowCell.getValue().toString().substring(0, rowCell.getValue().toString().indexOf("��"));
//            			}
//            			strRoomName =strRow+ strColumn;
//            			table.getRow(i).getCell(cell.getColumnIndex()).setValue(strRoomName);
//            		}
//            	}
   } 
    /**
	 * @description �ַ���ǰ�油0������maxTxt �ֶγ��ȣ�����txt���ȣ���СΪ2
	 * @author tim_gao
	 * @date 2011-07-16
	 * @param txt ��������
	 * @param maxTxt �Ա������ֶγ���
	 * @return
	 */
	public static String setFillZeroInTxt(int i,int maxTxt){
		   //�����������λ��
		int m = i;
		if(i<0){
			m=i*-1;
		}
	      int len = 2;
	  	NumberFormat nf = NumberFormat.getInstance();
	      //�����Ƿ�ʹ�÷���
	      nf.setGroupingUsed(false);
	      //�Ա�
		if((maxTxt+"").trim().length()>2){
			
			  	   len = (maxTxt+"").trim().length();
			     
		}
		//�������txt�Ĵ���2
		if((m+"").trim().length()>2){
			len=(m+"").trim().length();
		}
			nf.setMaximumIntegerDigits(len);
			//������С����λ��    
			nf.setMinimumIntegerDigits(len);
		
		return nf.format(i);
	}
	
	
	  /**
	 * @description �õ����е�������ĿΪ��������
	 * @author tim_gao
	 * @date 2011-07-16
	 * @return
	 */
	public static Map getAllSellProjectForRoom(Context ctx) throws BOSException{
		Map allselpro = new HashMap();
		SelectorItemCollection selector = new SelectorItemCollection();
		selector.add("parent");
		selector.add("id");
		selector.add("name");
		selector.add("number");
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("isForSHE",Boolean.TRUE));
		view.setFilter(filter);
		view.setSelector(selector);
		SellProjectCollection sellProCol = null;
	if(null!=ctx){
		sellProCol = SellProjectFactory.getLocalInstance(ctx).getSellProjectCollection(view);
	}else{
		sellProCol = SellProjectFactory.getRemoteInstance().getSellProjectCollection(view);
	}
			for(Iterator it = sellProCol.iterator();it.hasNext();){
				SellProjectInfo sellPro = (SellProjectInfo) it.next();
				
				allselpro.put(sellPro.getId().toString(), sellPro);
			}
			return allselpro;
		
	}
	  /**
	 * @description �õ�����Ŀ
	 * @author tim_gao
	 * @date 2011-07-16
	 * @return
	 */
	public static SellProjectInfo getRootSellProject(SellProjectInfo sellProj,Map allSellPro) throws BOSException{
		if(allSellPro.get( sellProj.getId().toString()) instanceof SellProjectInfo && null!=allSellPro.get(sellProj.getId().toString())
				&&null!=((SellProjectInfo)allSellPro.get(sellProj.getId().toString())).getParent()){
			getRootSellProject(((SellProjectInfo)allSellPro.get(sellProj.getId().toString())).getParent(),allSellPro);
		}
		return sellProj;
	
	}
	
	public static void toLoanBill(RoomInfo room,RoomLoanInfo roomLoan,boolean isEdit) throws BOSException, EASBizException{
		
		SelectorItemCollection sels =new SelectorItemCollection();
		sels.add("*");
		sels.add("building.sellProject.*");
		sels.add("roomModel.*");
		RoomInfo roomInfo=RoomFactory.getRemoteInstance().getRoomInfo(new ObjectUuidPK(room.getId().toString()),sels);
		UIContext uiContext = new UIContext();
		uiContext.put("ID", roomLoan.getId());
		uiContext.put("roomId", room.getId());
		uiContext.put("sellProjectId",roomInfo.getBuilding().getSellProject().getId());
		uiContext.put("mmTypeId", roomLoan.getMmType().getId());
		uiContext.put("done", "done");
		if(isEdit){
			IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.MODEL).create(RoomLoanEditUI.class.getName(), uiContext, null, OprtState.EDIT);
			uiWindow.show();
		}else{
			IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.MODEL).create(RoomLoanEditUI.class.getName(), uiContext, null, "FINDVIEW");
			uiWindow.show();
		}
	}
	
	public static void toJoinBill(RoomInfo room,RoomJoinInfo joinIn,boolean isEdit) throws BOSException, EASBizException{
		
		SelectorItemCollection sels =new SelectorItemCollection();
		sels.add("*");
		sels.add("building.sellProject.*");
		sels.add("roomModel.*");
		RoomInfo roomInfo=RoomFactory.getRemoteInstance().getRoomInfo(new ObjectUuidPK(room.getId().toString()),sels);
		UIContext uiContext = new UIContext();
		uiContext.put("ID", joinIn.getId());
		uiContext.put("roomId", room.getId());
		uiContext.put("sellProject.id",roomInfo.getBuilding().getSellProject().getId());
		if(isEdit){
			IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.MODEL).create(RoomJoinEditUI.class.getName(), uiContext, null, OprtState.EDIT);
			uiWindow.show();
		}else{
			IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.MODEL).create(RoomJoinEditUI.class.getName(), uiContext, null, "FINDVIEW");
			uiWindow.show();
		}
	}
	
	public static void toPropertyBill(RoomInfo room,RoomPropertyBookInfo propertyBook,boolean isEdit) throws BOSException, EASBizException{
		
		SelectorItemCollection sels =new SelectorItemCollection();
		sels.add("*");
		sels.add("building.sellProject.*");
		sels.add("roomModel.*");
		RoomInfo roomInfo=RoomFactory.getRemoteInstance().getRoomInfo(new ObjectUuidPK(room.getId().toString()),sels);
		UIContext uiContext = new UIContext();
		uiContext.put("ID", propertyBook.getId());
		uiContext.put("roomId", room.getId());
		uiContext.put("sellProjectId",roomInfo.getBuilding().getSellProject().getId());
		if(isEdit){
			IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.MODEL).create(RoomPropertyBookEditUI.class.getName(), uiContext, null, OprtState.EDIT);
			uiWindow.show();
		}else{
			IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.MODEL).create(RoomPropertyBookEditUI.class.getName(), uiContext, null, "FINDVIEW");
			uiWindow.show();
		}
	}
	public static String getRoomModelTypeName(String roomModelId) throws BOSException, SQLException {
		FDCSQLBuilder builder = new FDCSQLBuilder();
		builder.appendSql(" select roomType.fname_l2 roomTypeName from T_SHE_RoomModel roomMode ");
		builder.appendSql(" left join T_SHE_RoomModelType roomType on roomType.fid = roomMode.FRoomModelTypeID where");
		builder.appendParam("roomMode.fid", roomModelId);
		IRowSet countSet = builder.executeQuery();
		String roomModeTypeName = null;
		while (countSet.next()) {
			roomModeTypeName = countSet.getString("roomTypeName");
			break;
		}
		return roomModeTypeName;
	}
	//isFinish=true��ʾ����տfalseû���տ�Ҫ���� 
	public static void openRevBill(Object owner,String tranBusinessOverViewId,boolean isFinish) throws Exception{
		if(tranBusinessOverViewId==null) return;
		if(isFinish){
			Set sheRevSet = new HashSet();
			
			EntityViewInfo view = new EntityViewInfo();
			FilterInfo filter = new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("payListEntryId",tranBusinessOverViewId));
			view.setFilter(filter);
			view.getSelector().add("revBillEntryId.parent.id");
			SHERevMapCollection col=SHERevMapFactory.getRemoteInstance().getSHERevMapCollection(view);
			
			for(int i=0;i<col.size();i++){
				sheRevSet.add(col.get(i).getRevBillEntryId().getParent().getId().toString());
			}
			UIContext uiContext = new UIContext(owner); 
			if(sheRevSet.size()==1){
				CRMClientHelper.openTheGatherRevBillWindow(owner, col.get(0).getRevBillEntryId().getParent().getId().toString(), null, null ,null,null);
			}else{
				uiContext.put("IDSET", sheRevSet);
				IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.MODEL).create(SHERevBillListUI.class.getName(), uiContext, null, OprtState.VIEW);
				uiWindow.show();
			}
		}else{
			SelectorItemCollection sic = new SelectorItemCollection();
			sic.add("*");
			sic.add("head.*");
			sic.add("moneyDefine.name");
			sic.add("moneyDefine.number");
			sic.add("moneyDefine.moneyType");
			TranBusinessOverViewInfo tranBusinessOverViewInfo = null;
			try {
				tranBusinessOverViewInfo = TranBusinessOverViewFactory.getRemoteInstance().getTranBusinessOverViewInfo(new ObjectUuidPK(tranBusinessOverViewId), sic);
			} catch (BOSException e) {
				ExceptionHandler.handle(e);
				SysUtil.abort();
			}
			
			TransactionInfo transactionInfo = null;
			RoomSellStateEnum roomSellState = null;
			Object[] custObjs= null;
			BaseTransactionInfo info = null;
			if(tranBusinessOverViewInfo != null){
				transactionInfo = tranBusinessOverViewInfo.getHead();
				if(transactionInfo == null || transactionInfo.getId() == null){
					FDCMsgBox.showWarning("�������߲����ڣ�");
					SysUtil.abort();
				}
				roomSellState = transactionInfo.getCurrentLink();
				String sqlSelectStr = "select id,number,transactionID,bizDate,sellProject.*  ";
		    	String sqlWhereStr = " where id='"+transactionInfo.getBillId()+"'";
				if(RoomSellStateEnum.PrePurchase.equals(roomSellState)){
					info = PrePurchaseManageFactory.getRemoteInstance().getPrePurchaseManageInfo(sqlSelectStr +",prePurchaseCustomerEntry.customer.name,prePurchaseCustomerEntry.customer.number "+ sqlWhereStr);
		    		PrePurchaseManageInfo prePurchaseManageInfo = (PrePurchaseManageInfo)info; 
		    		custObjs= new Object[prePurchaseManageInfo.getPrePurchaseCustomerEntry().size()];
		    		for(int i=0;i<prePurchaseManageInfo.getPrePurchaseCustomerEntry().size();i++){
		    			custObjs[i]=prePurchaseManageInfo.getPrePurchaseCustomerEntry().get(i).getCustomer();
		    		}
				}else if(RoomSellStateEnum.Purchase.equals(roomSellState)){
					info = PurchaseManageFactory.getRemoteInstance().getPurchaseManageInfo(sqlSelectStr +",purCustomerEntry.customer.name,purCustomerEntry.customer.number "+ sqlWhereStr);
		    		PurchaseManageInfo purchaseManageInfo = (PurchaseManageInfo)info; 
		    		custObjs= new Object[purchaseManageInfo.getPurCustomerEntry().size()];
		    		for(int i=0;i<purchaseManageInfo.getPurCustomerEntry().size();i++){
		    			custObjs[i]=purchaseManageInfo.getPurCustomerEntry().get(i).getCustomer();
		    		}
				}else if(RoomSellStateEnum.Sign.equals(roomSellState)){
					info =SignManageFactory.getRemoteInstance().getSignManageInfo(sqlSelectStr +",signCustomerEntry.customer.name,signCustomerEntry.customer.number "+ sqlWhereStr);
		    		SignManageInfo signManageInfo = (SignManageInfo)info; 
		    		custObjs= new Object[signManageInfo.getSignCustomerEntry().size()];
		    		for(int i=0;i<signManageInfo.getSignCustomerEntry().size();i++){
		    			custObjs[i]=signManageInfo.getSignCustomerEntry().get(i).getCustomer();
		    		}
		    		//�������Ϊ��ֵ����Ӧ�ô��˿����
		    		if(tranBusinessOverViewInfo.getAppAmount().compareTo(new BigDecimal("0"))<0){
		    			SHERevBillEntryCollection revEntryColl = new SHERevBillEntryCollection();
		    			SHERevBillEntryInfo revEntryInfo = new SHERevBillEntryInfo();
		    			revEntryInfo.setMoneyDefine(tranBusinessOverViewInfo.getMoneyDefine());
		    			revEntryInfo.setRevAmount(tranBusinessOverViewInfo.getAppAmount());
		    			revEntryInfo.put("TranBusinessEntryIdStr", tranBusinessOverViewInfo.getId().toString());
		    			revEntryColl.add(revEntryInfo);
		    			CRMClientHelper.openTheRevBillWindow(owner, null, info.getSellProject(), RevBillTypeEnum.refundment, signManageInfo, custObjs, revEntryColl);
		    			return;
		    		}
				}else if(RoomSellStateEnum.ChangeName.equals(roomSellState) || RoomSellStateEnum.priceChange.equals(roomSellState)
						|| RoomSellStateEnum.changeRoom.equals(roomSellState)){
					if(RoomSellStateEnum.PrePurchase.equals(transactionInfo.getPreLink())){
						info = PrePurchaseManageFactory.getRemoteInstance().getPrePurchaseManageInfo(sqlSelectStr +",prePurchaseCustomerEntry.customer.name,prePurchaseCustomerEntry.customer.number "+ sqlWhereStr);
			    		PrePurchaseManageInfo prePurchaseManageInfo = (PrePurchaseManageInfo)info; 
			    		custObjs= new Object[prePurchaseManageInfo.getPrePurchaseCustomerEntry().size()];
			    		for(int i=0;i<prePurchaseManageInfo.getPrePurchaseCustomerEntry().size();i++){
			    			custObjs[i]=prePurchaseManageInfo.getPrePurchaseCustomerEntry().get(i).getCustomer();
			    		}
					}else if(RoomSellStateEnum.Purchase.equals(transactionInfo.getPreLink())){
						info = PurchaseManageFactory.getRemoteInstance().getPurchaseManageInfo(sqlSelectStr +",purCustomerEntry.customer.name,purCustomerEntry.customer.number "+ sqlWhereStr);
			    		PurchaseManageInfo purchaseManageInfo = (PurchaseManageInfo)info; 
			    		custObjs= new Object[purchaseManageInfo.getPurCustomerEntry().size()];
			    		for(int i=0;i<purchaseManageInfo.getPurCustomerEntry().size();i++){
			    			custObjs[i]=purchaseManageInfo.getPurCustomerEntry().get(i).getCustomer();
			    		}
					}else if(RoomSellStateEnum.Sign.equals(transactionInfo.getPreLink())){
						info =SignManageFactory.getRemoteInstance().getSignManageInfo(sqlSelectStr +",signCustomerEntry.customer.name,signCustomerEntry.customer.number "+ sqlWhereStr);
			    		SignManageInfo signManageInfo = (SignManageInfo)info; 
			    		custObjs= new Object[signManageInfo.getSignCustomerEntry().size()];
			    		for(int i=0;i<signManageInfo.getSignCustomerEntry().size();i++){
			    			custObjs[i]=signManageInfo.getSignCustomerEntry().get(i).getCustomer();
			    		}
					}
				}
				Set transEntryIdsSet = new HashSet();
				transEntryIdsSet.add(tranBusinessOverViewInfo.getId().toString());
				CRMClientHelper.openTheGatherRevBillWindow(owner, null, info.getSellProject(), info ,custObjs,transEntryIdsSet);
			}
		}
	}
	
	/**
	 * @description �õ���ǰ��Ŀ�µ�������Ŀ
	 * @author tim_gao
	 * @throws BOSException 
	 * @throws EASBizException 
	 */
	public static Set getAllSellProjectIds(SellProjectInfo sellPro,Set set) throws BOSException, EASBizException{
	
		Set allSetIds = set ;
		if(allSetIds == null){
		
			allSetIds = new HashSet();
			
		}
			allSetIds.add(sellPro.getId().toString());
		
		if(sellPro.getParent() instanceof SellProjectInfo){
			SellProjectInfo sellProParent = SellProjectFactory.getRemoteInstance().getSellProjectInfo(	new ObjectUuidPK(sellPro.getParent().getId()));
			allSetIds = getAllSellProjectIds(sellProParent, allSetIds);
		}
		return allSetIds;
		
	}
	
	/**
	 * @description ��ѯ��Ŀ�»���
	 * @author tim_gao
	 * @throws BOSException 
	 */
	public static EntityViewInfo getModelBySellProjectView(SellProjectInfo sellPro) throws BOSException{
		EntityViewInfo viewroomm = new EntityViewInfo ();
		FilterInfo filterroomm = new FilterInfo();
		if(sellPro==null){
			filterroomm.getFilterItems().add(new FilterItemInfo("id","'null'"));
			viewroomm.setFilter(filterroomm);
			return viewroomm;
		}
		filterroomm.getFilterItems().add(new FilterItemInfo("sellProject.id",sellPro.getId().toString()));
		viewroomm.setFilter(filterroomm);
//		ShareRoomModelsCollection sheRoomModels = ShareRoomModelsFactory.getRemoteInstance().getShareRoomModelsCollection(viewroomm);
//		Set allIds =new HashSet();
//		for(Iterator it = sheRoomModels.iterator();it.hasNext();){
//			ShareRoomModelsInfo shareRoomModel = (ShareRoomModelsInfo) it.next();
//			allIds.add(shareRoomModel.getRoomModel().getId().toString());
//		}
//		EntityViewInfo viewRoomModel = new EntityViewInfo();
//		FilterInfo filterRoomModel = new FilterInfo();
//		if(allIds!=null){
//			if(allIds.size()>0){
//				
//				filterRoomModel.getFilterItems().add(new FilterItemInfo("id",allIds,CompareType.INCLUDE));
//				viewRoomModel.setFilter(filterRoomModel);
//				return viewRoomModel;
//			}
//			
//		}
//		filterRoomModel.getFilterItems().add(new FilterItemInfo("id",null));
//		viewRoomModel.setFilter(filterRoomModel);
		return viewroomm;
	}
	protected static String getListSepartor(RoomListSeparatorEnum roomListSepa){
		String listSeperator = " ";
		if(null!=roomListSepa){
			if(RoomListSeparatorEnum.LittleRail.equals(roomListSepa)){
				listSeperator = "-";
			}else if(RoomListSeparatorEnum.LeftSeparator.equals(roomListSepa)){
				listSeperator = "/";
			}else if(RoomListSeparatorEnum.RightSeparator.equals(roomListSepa)){
				listSeperator = "\\";
			}
		}
		return listSeperator;
	}
	protected static String getSellProjIteratorName(String sellProStr,String sellProId,Map allSellPro,String listSeparaotr) throws BOSException{
		sellProStr =((SellProjectInfo)allSellPro.get(sellProId)).getName()+listSeparaotr+sellProStr;
		if(allSellPro.get(sellProId) instanceof SellProjectInfo && null!=allSellPro.get(sellProId)&&null!=((SellProjectInfo)allSellPro.get(sellProId)).getParent()){
		
		getSellProjIteratorName(sellProStr,((SellProjectInfo)allSellPro.get(sellProId)).getParent().getId().toString(),allSellPro,listSeparaotr);
		}
		return sellProStr;	
	}
	
		protected static String getSellProjIteratorNumber(String sellProStr,String sellProId,Map allSellPro,String listSeparaotr) throws BOSException{
			sellProStr = ((SellProjectInfo)allSellPro.get(sellProId)).getNumber()+listSeparaotr+sellProStr;
			if(allSellPro.get(sellProId) instanceof SellProjectInfo && null!=allSellPro.get(sellProId)&&null!=((SellProjectInfo)allSellPro.get(sellProId)).getParent()){
			getSellProjIteratorNumber(sellProStr,((SellProjectInfo)allSellPro.get(sellProId)).getParent().getId().toString(),allSellPro,listSeparaotr);
		}
		return sellProStr;	
	}
		public static int getMaxSeq(BuildingInfo build , BuildingUnitInfo unit) throws BOSException{
			int maxSeq = 0 ;
			EntityViewInfo view = new EntityViewInfo();
			
			FilterInfo filter = new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("building.id",build.getId().toString()));
			if(null!=unit){
				filter.getFilterItems().add(new FilterItemInfo("buildUnit.id",unit.getId().toString()));
			}
		
			view.setFilter(filter);
			RoomCollection roomCol = RoomFactory.getRemoteInstance().getRoomCollection(view);
			if(null!=roomCol&&roomCol.size()>0){
				for(Iterator it = roomCol.iterator(); it.hasNext();){
					RoomInfo room = (RoomInfo) it.next();
					if(maxSeq<room.getSerialNumber()){
						maxSeq = room.getSerialNumber();
					}
				}
				return maxSeq;
			}else{
				
				return 0;
			}
		
		
		}
		protected static RoomInfo getRoomSourcePrincipleName(String txtName,RoomInfo room,String sellProStrName,String sellProNumberStr,String listSeperator,int max){
			
			
			
			if (room.getBuildUnit() != null) {
				room.setUnit(room.getBuildUnit().getSeq());
			}
		
			int maxSeq = max;
			
			//Ҫдһ���õ���ǰ��Ԫ��������
			
//			maxSeq = room.getSerialNumber();
			
//			for(int i = 0 ; i<roomCol.size() ; i++){
//				RoomInfo roomComp = roomCol.get(i);
//				if(room.getBuildUnit().getId().equals(roomComp.getBuildUnit().getId())){
//					if(roomComp.getSerialNumber()>maxSeq){
//						maxSeq = roomComp.getSerialNumber();
//					}
//				}
//			}
			String txtNumber = null;
			if(maxSeq>2){
				 txtNumber =room.getFloor()
				+SHEHelper.setFillZeroInTxt(room.getSerialNumber(), maxSeq);
			}else{
				 txtNumber =room.getFloor()
					+SHEHelper.setFillZeroInTxt(room.getSerialNumber(), 2);
			}
			
		
//			if(null==txtName||("").equals(txtNumber)){
//				
//				 if(this.isConvert==true){
//						txtName =room.getFloor() + SHEHelper.getSerialNumberStr(room.getSerialNumber(), true);
//					}else{
//						 txtName=txtNumber;
//					}
//			}
			
//			room.setName(sellProStrName + room.getBuilding().getName() +listSeperator+room.getBuildUnit().getName()+ listSeperator + txtName);
//			room.setNumber(sellProNumberStr + room.getBuilding().getNumber() + listSeperator + room.getBuildUnit().getSeq()+ listSeperator+txtNumber);
			room.setName(sellProStrName + room.getBuilding().getName() +listSeperator+
					(room.getBuildUnit().isHaveUnit()==false ? room.getBuildUnit().getName()+ listSeperator:"") + txtName);
			room.setNumber(sellProNumberStr + room.getBuilding().getNumber() + listSeperator + 
					(room.getBuildUnit().isHaveUnit()==false ? room.getBuildUnit().getSeq()+ listSeperator:"")+txtNumber);
			room.setDisplayName(txtName);
			room.setRoomPropNo(room.getDisplayName());
//			String floors = String.valueOf(room.getFloor());
//			room.setFloor(room.getFloor()+1);
//			room.setUnit(0);
	//	
//			NumberFormat nf = NumberFormat.getInstance();
//	        //�����Ƿ�ʹ�÷���
//	        nf.setGroupingUsed(false);
//	        //�����������λ��
//	        int len = 2;
//	       if(String.valueOf(room.getSerialNumber()).length()>2){
//	    	   len = String.valueOf(room.getSerialNumber()).length();
//	       }
//	        nf.setMaximumIntegerDigits(len);
//	        //������С����λ��    
//	        nf.setMinimumIntegerDigits(len);
//			String colum = nf.format(i);
//			room.setDisplayName(floors+colum);
			
			return room;
		}
		/**
		 * ƴװ�������� name ,number displayname
		 * @param room
		 * @return
		 * @throws BOSException
		 * @author tim_gao
		 */
		public static RoomInfo getLongCodeRoom(RoomInfo room) throws BOSException{
			String sellProName ="";
			String sellProNumber ="";
			String listSepa =getListSepartor(room.getListSeparatorState());
			sellProName=room.getBuilding().getSellProject().getName()+" ";
			sellProNumber=room.getBuilding().getSellProject().getNumber()+" ";
//			sellProName = getSellProjIteratorName(sellProName,room.getBuilding().getSellProject().getId().toString(),getAllSellProjectForRoom(null),listSepa);
//			sellProNumber = getSellProjIteratorNumber(sellProNumber,room.getBuilding().getSellProject().getId().toString(),getAllSellProjectForRoom(null),listSepa);
			return getRoomSourcePrincipleName(room.getDisplayName(),room,sellProName,sellProNumber,listSepa,getMaxSeq(room.getBuilding(),room.getBuildUnit()));
			
		}
		/**
		 * ƴװnumber
		 * @param room
		 * @return
		 * @throws BOSException
		 * @author tim_gao
		 */
		public static RoomInfo getAssembleNumber(RoomInfo room) throws BOSException{
			String sellProNumber ="";
			String listSepa =getListSepartor(room.getListSeparatorState());
			sellProNumber = getSellProjIteratorNumber(sellProNumber,room.getBuilding().getSellProject().getId().toString(),getAllSellProjectForRoom(null),listSepa);
			
			if (room.getBuildUnit() != null) {
				room.setUnit(room.getBuildUnit().getSeq());
			}
		
			int maxSeq = getMaxSeq(room.getBuilding(),room.getBuildUnit());
			
			String txtNumber = null;
			if(maxSeq>2){
				 txtNumber =room.getFloor()
				+SHEHelper.setFillZeroInTxt(room.getSerialNumber(), maxSeq);
			}else{
				 txtNumber =room.getFloor()
					+SHEHelper.setFillZeroInTxt(room.getSerialNumber(), 2);
			}
			room.setNumber(sellProNumber + room.getBuilding().getNumber() + listSepa + (room.getBuildUnit()!=null?room.getBuildUnit().getSeq()+ listSepa:"")+txtNumber);
			return room;
		}
		
		public static SelectorItemCollection getRoomDesSelector(){
			SelectorItemCollection selector = new SelectorItemCollection();
	    	selector.add("id");
	    	selector.add("unitBegin");
	    	selector.add("unitEnd");
	    	selector.add("floorBegin");
	    	selector.add("floorEnd");
	    	selector.add("natrueFloorBegin");
	    	selector.add("natrueFloorEnd");
	    	selector.add("serialNumberBegin");
	    	selector.add("serialNumberEnd");
			selector.add("buildingArea");
			selector.add("roomArea");
			selector.add("apportionArea");
			selector.add("balconyArea");
			selector.add("floorHeight");
			selector.add("roomModel.*");
			selector.add("direction.*");
			selector.add("buildingProperty.*");
			selector.add("houseProperty");
			selector.add("isConvertToChar");
			selector.add("actualBuildingArea");
			selector.add("actualRoomArea");
			selector.add("sight.*");
			selector.add("numPrefixType");
			selector.add("productType.*");
			selector.add("isForSHE");
			selector.add("isForTen");
			selector.add("isForPPM");
			selector.add("noise.*");
			selector.add("decoraStd.*");
			selector.add("eyeSignht.*");
			selector.add("posseStd.*");
			selector.add("roomUsage.*");
			selector.add("flatArea");
			selector.add("carbarnArea");
			selector.add("planRoomArea");
			selector.add("planBuildingArea");
			selector.add("userArea");
			selector.add("guardenArea");
			selector.add("buildStruct.*");
			selector.add("bizDate");
			selector.add("newNoise.*");
			selector.add("newSight.*");
			selector.add("newEyeSight.*");
			selector.add("newDecorastd.*");
			selector.add("newPossstd.*");
			selector.add("newRoomUsage.*");
			selector.add("newProduceRemark.*");
	    	selector.add("unit.*");
	    	
	    	return selector;
		}
		/**
		 * @description ���ɵ�Ԫ��,�õ�����ʾ�ĵ�Ԫ
		 * @author tim_gao
		 * @param build����
		 * @param haveUnit(false ��ʾ true ����ʾ)���� ��Ϊ true ʱ���������ʾ�ĵ�Ԫ���еĻ��Ͳ�������
		 * @return
		 * @throws BOSException
		 * @throws EASBizException 
		 */
		public static BuildingUnitInfo getShowUnitWithRoomDes(BuildingInfo build) throws BOSException, EASBizException {
		// �õ�Ŀǰ¥�������е�Ԫ

		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		SorterItemInfo sort = new SorterItemInfo("seq");
		sort.setSortType(SortType.ASCEND);
		filter.getFilterItems().add(new FilterItemInfo("building.id", build.getId().toString()));
		view.getSorter().add(sort);
		view.setFilter(filter);
		BuildingUnitCollection buildUnitCol = BuildingUnitFactory.getRemoteInstance().getBuildingUnitCollection(view);
		BuildingUnitInfo maxUnit = new BuildingUnitInfo();

		BuildingUnitInfo getUnit = new BuildingUnitInfo();

		if (null == buildUnitCol || buildUnitCol.size() <= 0) {// ֱ�����ɵ�Ԫ�����ɶ�����Ϣ
			getUnit.setSeq(1);
			getUnit.setName("1��Ԫ");
			getUnit.setHaveUnit(true);
			getUnit.setBuilding(build);
			IObjectPK id = BuildingUnitFactory.getRemoteInstance().submit(getUnit);
			getUnit.setId(BOSUuid.read(id.toString()));
			RoomDesInfo roomDes = new RoomDesInfo();
			roomDes.setUnit(getUnit);
			roomDes.setBuilding(build);
			roomDes.setSerialNumberBegin(1);
			roomDes.setSerialNumberEnd(1);
			roomDes.setFloorBegin(1);
			roomDes.setFloorEnd(1);
			roomDes.setProductType(build.getProductType() != null ? build.getProductType() : null);
			roomDes.setBuildingProperty(build.getBuildingProperty() != null ? build.getBuildingProperty() : null);
			RoomDesFactory.getRemoteInstance().submit(roomDes);
			return getUnit;
		} else {// ֧���е�ԪʱҲ�з���ʾ��
			
//				maxUnit = buildUnitCol.get(buildUnitCol.size()-1);
			
			//����е�	
			if(null!=buildUnitCol&&buildUnitCol.size()==1){
				BuildingUnitInfo unit = (BuildingUnitInfo) buildUnitCol.get(0);
				if(true==unit.isHaveUnit()){
					return unit;
				}
			}
				return null;
		}
	}
		
		/**
		 * ��֯����,�¼��ɿ����ϼ���֯������
		 * �����֯Ϊ�յ���ЩԤ������
		 * add by youzhen,2011-09-14
		 * ���̻��������ϣ��ͻ��̻�ʹ��
		 */
		public static FilterInfo getDefaultFilterForTree() {
			FilterInfo filter = new FilterInfo();
			String longNumber = SysContext.getSysContext().getCurrentOrgUnit()
					.getLongNumber();
			String numbers[] = longNumber.split("!");// ���ϼ���������

			FilterInfo numFilter = new FilterInfo();
			EntityViewInfo view = new EntityViewInfo();
			SelectorItemCollection sic = new SelectorItemCollection();
			sic.add("id");
			Set set = new HashSet();
			try {
				for (int i = 0; i < numbers.length; i++) {// �����ϼ��ı���
					FilterInfo parentFilter = new FilterInfo();
					parentFilter.getFilterItems().add(
							new FilterItemInfo("number", numbers[i]));
					parentFilter.mergeFilter(parentFilter, "OR");
					numFilter.mergeFilter(parentFilter, "OR");
				}
				view.setFilter(numFilter);
				FullOrgUnitCollection FullOrgUnitColl = FullOrgUnitFactory
						.getRemoteInstance().getFullOrgUnitCollection(view);
				if (FullOrgUnitColl != null && FullOrgUnitColl.size() > 0) {
					for (int i = 0; i < FullOrgUnitColl.size(); i++) {
						String id = FullOrgUnitColl.get(i).getId().toString();
						set.add(id);
					}
				}
				filter.getFilterItems().add(
						new FilterItemInfo("orgUnit.id", set, CompareType.INCLUDE));

			} catch (BOSException e) {
				e.printStackTrace();
			}
			return filter;
		}
		
	 /**
	 * �жϵ�ǰ��¼���Ƿ��ǵ�ǰ��֯��Ӫ���ܿ���Ա
	 * 
	 * @return
	 */
	public static boolean isControl() {
		boolean result = false;
		UserInfo user = SysContext.getSysContext().getCurrentUserInfo();
		SaleOrgUnitInfo unit = SysContext.getSysContext().getCurrentSaleUnit();
		try {
			FilterInfo filter  = new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("controler.id",user.getId().toString(),CompareType.EQUALS));
			filter.getFilterItems().add(new FilterItemInfo("orgUnit.id",unit.getId().toString(),CompareType.EQUALS));
			if(MarketUnitControlFactory.getRemoteInstance().exists(filter)){
				result = true;
			}
		} catch (EASBizException e) {
			log.error(e.getMessage() + "�ڹܿ���Ա���޷��鵽���˵���Ϣ!");
		} catch (BOSException e) {
			log.error(e.getMessage() + "�ڹܿ���Ա���޷��鵽���˵���Ϣ!");
		}
		return result;
	}
	
	/**
	 * ���ݵ�ǰ��Ŀ�ڵ��ȡ����Ŀ�ڵ�
	 * @param node
	 * @return
	 */
	public static DefaultKingdeeTreeNode getParentNode(DefaultKingdeeTreeNode node) {
		if (node == null) {
			return null;
		}
		if (node.getParent() != null) {
			DefaultKingdeeTreeNode treeNode = (DefaultKingdeeTreeNode) node.getParent();
			if (treeNode.getUserObject() instanceof SellProjectInfo) {
				return getParentNode(treeNode);
			} else {
				return node;
			}
		}
		return null;
	}

}

