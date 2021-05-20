/**
 * output package name
 */
package com.kingdee.eas.fdc.sellhouse.client;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.swing.event.TreeSelectionEvent;
import javax.swing.tree.TreeNode;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.table.IColumn;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTDataRequestManager;
import com.kingdee.bos.ctrl.kdf.table.KDTGroupManager;
import com.kingdee.bos.ctrl.kdf.table.KDTMergeManager;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectManager;
import com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent;
import com.kingdee.bos.ctrl.kdf.table.event.KDTSelectEvent;
import com.kingdee.bos.ctrl.kdf.util.style.Styles.HorizontalAlignment;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.ItemAction;
import com.kingdee.eas.base.commonquery.client.CommonQueryDialog;
import com.kingdee.eas.basedata.org.SaleOrgUnitInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.fdc.basecrm.RevBillTypeEnum;
import com.kingdee.eas.fdc.basedata.FDCCustomerParams;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.MoneySysTypeEnum;
import com.kingdee.eas.fdc.basedata.client.FDCClientHelper;
import com.kingdee.eas.fdc.sellhouse.BuildingInfo;
import com.kingdee.eas.fdc.sellhouse.BuildingUnitInfo;
import com.kingdee.eas.fdc.sellhouse.GatheringDetailFacadeFactory;
import com.kingdee.eas.fdc.sellhouse.MoneyDefineCollection;
import com.kingdee.eas.fdc.sellhouse.MoneyDefineFactory;
import com.kingdee.eas.fdc.sellhouse.MoneyDefineInfo;
import com.kingdee.eas.fdc.sellhouse.MoneyTypeEnum;
import com.kingdee.eas.framework.ITreeBase;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.MsgBox;
import com.kingdee.jdbc.rowset.IRowSet;
import com.kingdee.eas.fdc.sellhouse.SellProjectInfo;

/**
 * output class name
 */
public class GatheringDetailListUI extends AbstractGatheringDetailListUI
{
	//��ͬ���� ��8������
	private static final String ContractMoneyTypeStr = "'"+MoneyTypeEnum.PRECONCERTMONEY_VALUE +"','"+ MoneyTypeEnum.EARNESTMONEY_VALUE 
							+"','" + MoneyTypeEnum.HOUSEAMOUNT_VALUE +"','" + MoneyTypeEnum.FISRTAMOUNT_VALUE +"','"+ MoneyTypeEnum.COMPENSATEAMOUNT_VALUE 
							+"'";//,'"+ MoneyTypeEnum.REFUNDMENT_VALUE+"'";  �������˿� zhicheng_jin 091225
	
    private static final String KEY_SHOUKUAN_TOTAL = "shoukuanTotal";	//��ʵ�գ������տ�ϼ�

	private static final String KEY_ALL_TOTAL = "allTotal";    //���п���ϼ�

	private static final String KEY_ELSE_TOTAL = "elseTotal";	//ʵ�������ϼ�

	private static final String KEY_TAX_TOTAL = "taxTotal";

	private static final String KEY_SHOUXU_TOTAL = "shouxuTotal";

	private static final String KEY_DAISHOU_TOTAL = "daishouTotal";

	private static final String KEY_PLAN_TOTAL = "planTotal";

	/** �����ת�� */
	private static final String KEY_EARN_A = "asdf23224";
	
	/** ��ͬ����ϼ� */
	private static final String KEY_OUT_PLAN_TOTAL = "outPlanTotal";
	
	private static final Logger logger = CoreUIObject.getLogger(GatheringDetailListUI.class);
    
    private ItemAction[] hiddenAction = new ItemAction[]{this.actionView ,this.actionAddNew,this.actionEdit,this.actionRemove,this.actionLocate,this.actionEdit};

    private Map moneyDefineMap = new HashMap();
    
    SaleOrgUnitInfo saleOrg = SHEHelper.getCurrentSaleOrg();
    
    //���㷽ʽ��ϸ
    private final String SETTLE_TYPE = "settlementType";
    private final String BANK_AMT = "bankAmount";
    private final String BANK_ACCOUNT = "bankAccount";
    private final String BANK_ACCT_NUM = "bankAccountNumber";
    
    private  Color preTax = new Color(0xD2E3CA);
	private  Color aftTax = new Color(0xE3EFDE);
	
	
	private final String TOTAL_COL_TOTAL_EDNSTR ="_totalAmount";
	private final String TOTAL_COL_REVAMOUNT_EDNSTR ="_revAmount";
	
	private final String TOTAL_COL_SETTLE_EDNSTR ="_settleAmount";
	private final String TOTAL_COL_USEAMOUNT_EDNSTR ="_useAmount";
    
	
	private Set allBuildingIds = null; // ������¥��id
	private Set allSellProjectIds =null;//��Ŀ����
	
	//private static final BigDecimal ZERO=new BigDecimal("0.00");
	
    public GatheringDetailListUI() throws Exception
    {
        super();
    }
    
    public void handUIException(Throwable exc) {
		if(exc instanceof BOSException&&exc.getMessage().startsWith("Can't found propertyUnit:")){
			logger.error(exc);
		}
		else
			super.handUIException(exc);
	}
    
	protected boolean initDefaultFilter() {
		return true;
	}
	
	private CommonQueryDialog commonQueryDialog = null;
	protected CommonQueryDialog initCommonQueryDialog() {
		if (commonQueryDialog != null) {
			return commonQueryDialog;
		}
		commonQueryDialog = super.initCommonQueryDialog();
		commonQueryDialog.setWidth(400);
		commonQueryDialog.addUserPanel(this.getFilterUI());
		commonQueryDialog.setShowSorter(false);
		commonQueryDialog.setShowFilter(false);
		return commonQueryDialog;
	}

	private GatheringDetailFilterUI filterUI = null;
	private GatheringDetailFilterUI getFilterUI() {
		if (this.filterUI == null) {
			try {
				this.filterUI = new GatheringDetailFilterUI();
				this.filterUI.onLoad();
			} catch (Exception e) {
				e.printStackTrace();
				abort(e);
			}
		}
		return this.filterUI;
	}
    
    public void onLoad() throws Exception
    {
    	super.onLoad();
    	this.initTable();
    	this.menuEdit.setVisible(false);
    	this.tblMain.getDataRequestManager().setDataRequestMode(KDTDataRequestManager.REAL_MODE);
		
		FDCClientHelper.setActionVisible(this.hiddenAction,false);
    }

    public void storeFields()
    {
        super.storeFields();
    }
    protected String getKeyFieldName()
    {
    	return "room";
    }
    
    private void initTable() throws BOSException
    {
    	this.tblMain.checkParsed();
    	moneyDefineMap.clear();
    	    	
		tblMain.getStyleAttributes().setLocked(true);
		tblMain.getSelectManager().setSelectMode(KDTSelectManager.ROW_SELECT);
		
		for(int i = tblMain.getColumnCount()-1; i > 5; --i)
		{
			tblMain.removeColumn(i);
		}
		if(this.tblMain.getRowCount()>0) 
			this.tblMain.removeRows();
	
		intTableHead();
		try {
			this.getAllData();
		} catch (EASBizException e) {
			
		}
		
	//	tblMain.getHeadMergeManager().mergeBlock(0, 0, 3, tblMain.getColumnCount()-1, KDTMergeManager.FREE_MERGE);
    	/*for (int i = 6; i < this.tblMain.getColumnCount() - 1; i++) {
			IColumn column = this.tblMain.getColumn(i);
			
			if(column.getKey().equals(BANK_ACCOUNT) || column.getKey().equals(BANK_ACCT_NUM))
			{
				continue;
			}
			column.getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
			column.getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(2));
		}*/
    	
    	//tblMain.getHeadMergeManager().mergeBlock(0, 0, 2, tblMain.getColumnCount()-1, KDTMergeManager.FREE_MERGE);
    	//tblMain.getHeadMergeManager().mergeBlock(1, 0, 3, tblMain.getColumnCount()-1, KDTMergeManager.FREE_MERGE);
    }

	protected ITreeBase getTreeInterface() throws Exception
	{
		return null;
	}

	protected void initTree() throws Exception 
	{
		boolean isbizunit = SysContext.getSysContext().getCurrentSaleUnit().isIsBizUnit();
		this.treeMain.setModel(SHEHelper.getUnitTree(this.actionOnLoad, MoneySysTypeEnum.SalehouseSys,isbizunit));
		this.treeMain.expandAllNodes(true, (TreeNode) this.treeMain.getModel().getRoot());
	}

	protected void treeMain_valueChanged(TreeSelectionEvent e) throws Exception
	{
		//��ʼ�����
		initTable();
		
		//�������
		//getAllData();
	}
	
	
	protected Map getRptParamMap() throws Exception {

		Map paramMap = new HashMap();
		

		return paramMap;
	}
	
	protected void getAllData() throws EASBizException, BOSException
	{
		this.tblMain.getDataRequestManager().setDataRequestMode(KDTDataRequestManager.REAL_MODE);
		
		this.tblMain.removeRows(false);
		
		allBuildingIds = new HashSet();
		allSellProjectIds = new HashSet();
		
		int unitNumber = 0; // ��Ԫ���ˣ������޵�Ԫ����
		TreeNode buildingNode = (TreeNode) this.treeMain.getLastSelectedPathComponent();
		// allBuildingIds = "null";
		if (buildingNode != null) {
			DefaultKingdeeTreeNode thisNode = (DefaultKingdeeTreeNode) buildingNode;
			if (thisNode.getUserObject() != null) {
				if (thisNode.getUserObject() instanceof BuildingInfo) {
					BuildingInfo building = (BuildingInfo) thisNode
					.getUserObject();
					// allBuildingIds += "," + building.getId().toString();
					allBuildingIds.add(building.getId().toString());
				} else if (thisNode.getUserObject() instanceof Integer) { // ������
					DefaultKingdeeTreeNode parentNode = (DefaultKingdeeTreeNode) thisNode
					.getParent();
					BuildingInfo parentBuilding = (BuildingInfo) parentNode
					.getUserObject();
					allBuildingIds.add(parentBuilding.getId().toString());
					unitNumber = ((Integer) thisNode.getUserObject())
					.intValue();
				} else if (thisNode.getUserObject() instanceof BuildingUnitInfo) { //
					DefaultKingdeeTreeNode parentNode = (DefaultKingdeeTreeNode) thisNode
					.getParent();
					BuildingInfo parentBuilding = (BuildingInfo) parentNode
					.getUserObject();
					allBuildingIds.add(parentBuilding.getId().toString());
					unitNumber = ((BuildingUnitInfo) thisNode.getUserObject()).getSeq();
				} else {
					this.getAllBuildingIds(buildingNode);
					this.getAllSellProject(buildingNode);
				}
			}
		} else {
			boolean isbizunit = SysContext.getSysContext().getCurrentSaleUnit().isIsBizUnit();
			try {
				this.treeMain.setModel(SHEHelper.getUnitTree(this.actionOnLoad,MoneySysTypeEnum.SalehouseSys,isbizunit));
				this.treeMain.expandAllNodes(true, (TreeNode) this.treeMain.getModel().getRoot());
				this.getAllBuildingIds((TreeNode) this.treeMain.getModel().getRoot());
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		if (allBuildingIds.size() == 0) {
			return;
		}
		
		/**
		 * �ӹ��˽��������ڼӵ� bizDate �Ĺ�����,ע����ʱ����Ĵ���
		 */
		Map filterMap = CommerceHelper.convertFilterItemCollToMap(this.mainQuery.getFilter());
		Date BeginQueryDate = filterMap.get("BeginQueryDate")==null?null:new Date(((Timestamp)filterMap.get("BeginQueryDate")).getTime());
		Date EndQueryDate = filterMap.get("EndQueryDate")==null?null:new Date(((Timestamp)filterMap.get("EndQueryDate")).getTime());
		/*if(BeginQueryDate!=null)
			filter.getFilterItems().add(new FilterItemInfo("bizDate", BeginQueryDate, CompareType.GREATER_EQUALS));
		if(EndQueryDate!=null)
			filter.getFilterItems().add(new FilterItemInfo("bizDate", EndQueryDate, CompareType.LESS));
*/		
		
		Set moneydefinetypeset = getSelectItemArray();
	
		Map param = new HashMap();
		param.put("dateFrom", BeginQueryDate);
		param.put("dateTo", EndQueryDate);
		param.put("includeHistoey", Boolean.valueOf(this.getFilterUI().kdhistory.isSelected()));
		param.put("moneydefine", moneydefinetypeset);
		
		String buildingIds = FDCTreeHelper.getStringFromSet(allBuildingIds);
		String sellProjectIds = FDCTreeHelper.getStringFromSet(allSellProjectIds);
		
		param.put("allBuildingIds", buildingIds);
		param.put("unitNumber", new Integer(unitNumber));
		
		param.put("allSellProjectIds", sellProjectIds);
		
		Map maps = GatheringDetailFacadeFactory.getRemoteInstance().getAllData(param);
		

		/**
		 * ������Щ�տ����
		 * �ṹΪ Map<CustomerInfo, Map<RoomInfo, Set<ReceivingBillInfo>>>
		 */
		//�̶���
		//<Map customId,Map<roomid,Map>>
		Map holdmap = new HashMap();
		IRowSet rows = (IRowSet)maps.get("map");
		IRowSet rows2 = (IRowSet)maps.get("map2");
		if(rows==null ||rows.size()<1){
			hideNullType();
			return;
		}
		
		String customerID="";
		String roomID="";

		//Map<customerId, Map<roomId,  Map<MoneyType,Map<MoneyDefine,Map<settleType,Map<bankAccout,Data>> >>>>;
		Map customMap = new HashMap();
		
		//
		updateCustomMap(rows,customMap,holdmap);
		//ת��
		updateCustomMap(rows2,customMap,holdmap);
		
		
		//Set tempupdateTotalMoneyType =new HashSet();
		Set updateTotalMoneyType =new HashSet();
		Map tempcustom =null;
		Map temp =null;
		if(customMap!=null){
			Iterator  customit = customMap.keySet().iterator();
			int start =0;
			int end =0;
			
			//int maxRow =0;
			
			
			while (customit.hasNext()){  //�ͻ�
				
				//updateTotalMoneyType =new HashSet();
				
				String customId = (String)customit.next();
				Map roomMap = (Map)customMap.get(customId);
					if(roomMap!=null){
						Iterator  roomit = roomMap.keySet().iterator();
						while (roomit.hasNext()){  //����
							
							start = end = this.tblMain.getRowCount();
							
							String  roomsId = (String)roomit.next();
							Map moneyType = (Map)roomMap.get(roomsId);
							tempcustom = (Map)holdmap.get(customId);
						    temp = (Map)tempcustom.get(roomsId);
						  //Map<index,Object(settleTye,bankNumber)>
							//Map indexMap = new HashMap();   /// <��ǰ��������к�,<���㷽ʽ-�տ������˺�>>
							
								if(moneyType!=null){
									Iterator  moneyTypeit = moneyType.keySet().iterator();
									while (moneyTypeit.hasNext()){  //�������
										
										String moneyTypes = (String)moneyTypeit.next();
										Map moneyDefineMap = (Map)moneyType.get(moneyTypes);
										
										updateTotalMoneyType.add(moneyTypes);
										
										if(moneyDefineMap!=null){
											int count=0;
											Iterator  defineit = moneyDefineMap.keySet().iterator();
											while (defineit.hasNext()){  //����
												count++;
												String defineID = (String)defineit.next();
												
												int itemstart = start ;
												
												
												Map itemMap = (Map)moneyDefineMap.get(defineID);
												if(itemMap!=null){
													Iterator  itemit = itemMap.keySet().iterator();
													
													while (itemit.hasNext()){  //��
														
														String itemId = (String)itemit.next();
														
														
														
														//unused code
														//��ǰ�ͻ� ��ǰ��� �в��� ��ͬ�Ľ��㷽ʽ+�˻�
														//û���򽫸� ���㷽ʽ+�˻�����map 
														//���ظ� ���㷽ʽ+�˻� �� ��ǰ�к�
														//int rowindex = this.getASameRow(indexMap, (RowMoneyDefineObj)itemMap.get(itemId));
														//
														
														//RowMoneyDefineObj row = (RowMoneyDefineObj)moneyDefineMap.get(defineit.next());
														IRow row= this.tblMain.getRow(itemstart);
														if(row==null){
															//if(maxRow<=start+rowindex){
																row = this.tblMain.addRow();
																fillHeadData(row,temp);
																end++;
															//	maxRow = end;
														//	}
														
														}
														//������� ������������ 
														fillBodyData(row,moneyTypes,defineID,(RowMoneyDefineObj)itemMap.get(itemId));
														
														//������Ӧ�ĺϼ���
														updateMoneyTypeTotal(row,moneyTypes,defineID,(RowMoneyDefineObj)itemMap.get(itemId));
														itemstart++;
													}
												}
											}
											
										}
									}
									
								}
						//�ϼƷ���
								
						//updateCustomTotal(start,this.tblMain.getRowCount(),updateTotalMoneyType);
						//updateAllTotalCol(start,this.tblMain.getRowCount());
						}
/*						if(start ==2) {
							tempupdateTotalMoneyType = updateTotalMoneyType;
							break;
						}
*/						
					}
			//С��
			//IRow row= this.tblMain.addRow();
			//row.setTreeLevel(1);
			//row.getStyleAttributes().setBackground(FDCTableHelper.totalColor);
			//row.getCell(0).setValue("С��:");
			//limitTotal(start,end,row);
					
			}
		}
		
		//��Ԫ��Ϊ��ʱ ����
		/*boolean havUnit =false;
		for(int i=0;i<this.tblMain.getRowCount();i++){
			IRow row = this.tblMain.getRow(i);
			if(row!=null && row.getCell("unit")!=null){
				if(row.getCell("unit").getValue()!=null){
					havUnit =true;	
					break;
				}
			}
		}
		if(!havUnit){
			this.tblMain.getColumn("unit").getStyleAttributes().setHided(true);
		}*/
		/////
		
		///����  ����ȫ�յ� �������
		hideNullType();
		
		int count =0;
		if(this.tblMain.getColumnCount()>255){
			for(int i=0;i<this.tblMain.getColumnCount();i++){
				if(!this.tblMain.getColumn(i).getStyleAttributes().isHided()){
					count ++;
				}
			}
		}
		
		if(count>255){
			if(this.tblMain.getRowCount()>0) 
				this.tblMain.removeRows();
			MsgBox.showError("���������г�����������ѡ��������ͳ�Ʒ�Χ");
			SysUtil.abort();
		}
		
		//����ʵ�տ��� ,ʵ������ ,�տ�ϼ� ��
		updateAllTotalCol(0,this.tblMain.getRowCount(),false);
		
		/**
		 * ����С��/�ܼ���
		 */
		
		//���÷��������
		this.tblMain.getGroupManager().reInitialize();
		
		this.tblMain.getColumn(0).setGroup(true);
		this.tblMain.getColumn(1).setGroup(true);
		this.tblMain.getColumn(2).setGroup(true);
		this.tblMain.getColumn(3).setGroup(true);
		this.tblMain.getColumn(4).setGroup(true);
		this.tblMain.getColumn(5).setGroup(true);
		this.tblMain.getGroupManager().setTotalize(true);
		this.tblMain.getColumn(0).setStat(true);
		
		IRow row0 = (IRow)tblMain.getGroupManager().getStatRowTemplate(-1);
		row0.getStyleAttributes().setBackground(CommerceHelper.BK_COLOR_MUST);
		row0.getCell(0).setValue("�ܼ�:");
		for(int i=6;i<this.tblMain.getColumnCount();i++)
		{
			if(tblMain.getColumn(i).getStyleAttributes().isHided()){
				continue;
			}
			
			if(tblMain.getColumnKey(i).endsWith("1") || tblMain.getColumnKey(i).endsWith("3") || tblMain.getColumnKey(i).endsWith("4")){
				row0.getCell(i).setValue("");
				continue;
			}
			if(!(tblMain.getColumnKey(i).equals(SETTLE_TYPE) || tblMain.getColumnKey(i).equals(BANK_ACCOUNT) || tblMain.getColumnKey(i).equals(BANK_ACCT_NUM)))
			{
				row0.getCell(i).setExpressions(KDTGroupManager.STAT_SUM);
			}
		}
		
		row0 = (IRow)tblMain.getGroupManager().getStatRowTemplate(0);
		row0.getStyleAttributes().setBackground(CommerceHelper.BK_COLOR_MUST);
		row0.getCell(0).setValue("С��:");
		for(int i=6;i<this.tblMain.getColumnCount();i++)
		{
			if(tblMain.getColumn(i).getStyleAttributes().isHided() && 
					(!tblMain.getColumnKey(i).endsWith(TOTAL_COL_TOTAL_EDNSTR)&& !tblMain.getColumnKey(i).endsWith(TOTAL_COL_REVAMOUNT_EDNSTR)
							&&  !tblMain.getColumnKey(i).endsWith(TOTAL_COL_SETTLE_EDNSTR)&&  !tblMain.getColumnKey(i).endsWith(TOTAL_COL_USEAMOUNT_EDNSTR)
							)){
				continue;
			}
			if(tblMain.getColumnKey(i).endsWith("1") || tblMain.getColumnKey(i).endsWith("3") || tblMain.getColumnKey(i).endsWith("4")){
				row0.getCell(i).setValue("");
				continue;
			}
			if(!(tblMain.getColumnKey(i).equals(SETTLE_TYPE) || tblMain.getColumnKey(i).equals(BANK_ACCOUNT) || tblMain.getColumnKey(i).equals(BANK_ACCT_NUM)))
			{
				row0.getCell(i).setExpressions(KDTGroupManager.STAT_SUM);
			}
		}
		//���÷���������� �ȷ����������� ����ˢ�� 
		this.tblMain.getGroupManager().group();
		
		// ������
		tblMain.getGroupManager().toTreeColumn();
		
		int start=0 ,end=0;
		boolean currentBolck =false;
		String oldroom =null;
		IRow row  = null;
		for(int i=0;i<this.tblMain.getRowCount();i++){
			row = this.tblMain.getRow(i);
			
			/*//����ɫ��� �ϼ���  //�� һ���ͻ��Ľ�β
			if( CommerceHelper.BK_COLOR_MUST.equals(row.getStyleAttributes().getBackground())){
				end = row.getRowIndex();
				currentBolck =false;
				updateCustomTotal(start,end,updateTotalMoneyType);
				updateAllTotalCol(start,end);
				continue;
			}
			
			//�¿ͻ���ʼ��
			if(!currentBolck){
				start = row.getRowIndex();
				currentBolck =true;
			}*/
			String room = (String)row.getCell("room").getValue();
			if(room!=null && !"".equals(room) && oldroom!=null  && room.equals(oldroom) ){
				currentBolck =true;
				continue;
			}else{
				if(currentBolck){
					end =  row.getRowIndex();
					oldroom =null;
					currentBolck =false;
				}else{
					start = row.getRowIndex();	
					oldroom = room;
					currentBolck =true;
					continue;
				}
			}
			if(!currentBolck){
				updateCustomTotal(start,end,updateTotalMoneyType);
				updateAllTotalCol(start,end);
			}
			
			//
			if(room!=null && !"".equals(room)){
				start = row.getRowIndex();
				oldroom =room;
			}
			
		}
		
		
		//���µ������ֲ�ˢ��
		tblMain.reLayoutAndPaint();
		
	}
	
	
	private void updateCustomMap(IRowSet rows,Map customMap,Map holdmap){
		
		String customerID="";
		String roomID="";
		if(rows!=null){
			try {
				rows.beforeFirst();
				while(rows.next())
				{
					//row= this.tblMain.addRow();]
					
					customerID = rows.getString("customerID");  //tempcustomerID is not null  //check it
					roomID = rows.getString("roomID");
					//����
					Map  roomListMap = (Map)customMap.get(customerID);
					
					if(roomListMap==null){   ///�ͻ�--����Map
						roomListMap =new HashMap();
						customMap.put(customerID, roomListMap);
					}
					
					Map  roomMap = (Map)roomListMap.get(roomID);
					
					if(roomMap==null){ ////�ͻ�--����---
						roomMap = new HashMap();
						roomListMap.put(roomID, roomMap);
					}
					
					Map  roomholdmap = (Map)holdmap.get(customerID);
					if(roomholdmap==null){
						roomholdmap = new HashMap();
						holdmap.put(customerID, roomholdmap);
					}
				
					Map roomDisp = (Map)roomholdmap.get(roomID);
					
					if(roomDisp==null){
						roomDisp = new HashMap();
						roomDisp.put("customer", rows.getString("customerName"));
						roomDisp.put("sellProject", rows.getString("sellProjectName"));
						roomDisp.put("area", rows.getString("subAreaName"));
						roomDisp.put("building", rows.getString("buidingName"));
						roomDisp.put("unit", rows.getString("buildUnitName"));
						roomDisp.put("room", rows.getString("roomNumber"));
						//roomDisp.put("roomID", rows.getString("roomID"));
						
						roomholdmap.put(roomID, roomDisp);
					}
					
					String moneytype = rows.getString("MoneyType");
					String moneydefine = rows.getString("moneyDefineId");
					
					Map moneyTypeMap = new HashMap();
					Map moneyDefineMap = new HashMap();
					//�������
					if(moneytype!=null){
						Map tempmoneyTypeMap = (Map)roomMap.get(moneytype);
						if(tempmoneyTypeMap!=null){
							moneyTypeMap = tempmoneyTypeMap;
						}else{
							roomMap.put(moneytype, moneyTypeMap);
						}
					}else{  //
						continue;
					}
					
					//��������
					Map tempmoneyDefineMap = (Map)moneyTypeMap.get(moneydefine);
					if(tempmoneyDefineMap!=null){
						moneyDefineMap = tempmoneyDefineMap;
					}else{
						moneyTypeMap.put(moneydefine, moneyDefineMap);
					}
					
					readRowMap(moneyDefineMap,rows);

				}
				
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * ����  �����տ�ϼ�, ʵ�������տ�ϼ�,�տ�ϼ� ��
	 */
	private void updateAllTotalCol(int start,int end){
		updateAllTotalCol(start,end,true);
	}
	private void updateAllTotalCol(int start,int end,boolean merge){
		int shoukuanendCol = this.tblMain.getColumnIndex(KEY_SHOUKUAN_TOTAL);
		int otherendCol = this.tblMain.getColumnIndex(KEY_ELSE_TOTAL);
		int endCol = this.tblMain.getColumnIndex(KEY_ALL_TOTAL);
		
		
		BigDecimal preMoney = FDCHelper.ZERO;
		
		BigDecimal preconcertMoney = FDCHelper.ZERO;
		BigDecimal earnestMoney = FDCHelper.ZERO;
		BigDecimal fisrtAmount = FDCHelper.ZERO;
		BigDecimal houseAmount = FDCHelper.ZERO;
		BigDecimal loanAmount = FDCHelper.ZERO;
		BigDecimal accFundAmount = FDCHelper.ZERO;
		BigDecimal compensateAmount = FDCHelper.ZERO;
		
		BigDecimal fitmentAmount = FDCHelper.ZERO;
		BigDecimal replaceFee = FDCHelper.ZERO;
		BigDecimal commissionCharge = FDCHelper.ZERO;
		BigDecimal lateFee = FDCHelper.ZERO;
		BigDecimal elseAmount = FDCHelper.ZERO;
				
		IRow row = null;
		BigDecimal shoukutotal = FDCHelper.ZERO;
		BigDecimal othertotal = FDCHelper.ZERO;
		BigDecimal total = FDCHelper.ZERO;
		
		//int totalRow = this.tblMain.getRowCount();
		for(int rowindex=start;rowindex<end;rowindex++){
			
			row = this.tblMain.getRow(rowindex);
			preMoney = getCellValue(row,MoneyTypeEnum.PreMoney);
			
			if(shoukuanendCol>0 && !this.tblMain.getColumn(shoukuanendCol).getStyleAttributes().isHided()){ //�����տ�ϼ�
				 preconcertMoney = getCellValue(row,MoneyTypeEnum.PreconcertMoney);
				 earnestMoney = getCellValue(row,MoneyTypeEnum.EarnestMoney);
				 fisrtAmount = getCellValue(row,MoneyTypeEnum.FisrtAmount);
				 houseAmount = getCellValue(row,MoneyTypeEnum.HouseAmount);
				 loanAmount = getCellValue(row,MoneyTypeEnum.LoanAmount);
				 accFundAmount = getCellValue(row,MoneyTypeEnum.AccFundAmount);
				 compensateAmount = getCellValue(row,MoneyTypeEnum.CompensateAmount);
				 //�����տ�ϼ�
				 shoukutotal = preconcertMoney.add(earnestMoney)
						.add(fisrtAmount).add(houseAmount).add(loanAmount).add(accFundAmount).add(compensateAmount);
				 row.getCell(shoukuanendCol).setValue(shoukutotal);
			}
			
			if(otherendCol>0 && !this.tblMain.getColumn(otherendCol).getStyleAttributes().isHided()){ //ʵ�������տ�ϼ�
					fitmentAmount  = getCellValue(row,MoneyTypeEnum.FitmentAmount);
					replaceFee = getCellValue(row,MoneyTypeEnum.ReplaceFee);
					commissionCharge = getCellValue(row,MoneyTypeEnum.CommissionCharge);
					lateFee = getCellValue(row,MoneyTypeEnum.LateFee);
					elseAmount = getCellValue(row,MoneyTypeEnum.ElseAmount);
					//�����տ�ϼ�
					othertotal = fitmentAmount.add(replaceFee).add(commissionCharge)
						.add(lateFee).add(elseAmount);
					row.getCell(otherendCol).setValue(othertotal);
			}
			
			if(endCol>0 && !this.tblMain.getColumn(endCol).getStyleAttributes().isHided()){ //�տ�ϼ�
				total = preMoney.add(shoukutotal).add(othertotal);
				row.getCell(endCol).setValue(total);
			}
		}
		if(merge){
			
		if(start+1>=end){
			return;
		}
			tblMain.getMergeManager().mergeBlock(start, shoukuanendCol, end-1, 
					shoukuanendCol,KDTMergeManager.SPECIFY_MERGE );
			tblMain.getMergeManager().mergeBlock(start, otherendCol, end-1, 
					otherendCol,KDTMergeManager.SPECIFY_MERGE );
			tblMain.getMergeManager().mergeBlock(start, endCol, end-1, 
					endCol,KDTMergeManager.SPECIFY_MERGE );
		}
		
	}
	
	private BigDecimal getCellValue(IRow row,String key){
		if(row!=null && row.getCell(key)!=null){
			if(row.getCell(key).getValue()!=null && !"".equals(row.getCell(key).getValue())){
				Object o = row.getCell(key).getValue();
				if(!(o instanceof BigDecimal)){
					return new BigDecimal(o.toString());
				}
				return (BigDecimal)o;
			}
		}
		
		return FDCHelper.ZERO;
	}
	
	private BigDecimal getCellValue(IRow row,MoneyTypeEnum type,String endStr){
		String key = type.getValue()+endStr;
		return getCellValue(row,key);
	}
	private BigDecimal getCellValue(IRow row,MoneyTypeEnum type){
		String key = type.getValue()+"_useAmount";
		return getCellValue(row,key);
	}
	
	private void updateCustomTotal(int start ,int end,Set updateMoneyTypeSet)
	{
		if(updateMoneyTypeSet!=null && updateMoneyTypeSet.size()>0)
		{
			Iterator  it = updateMoneyTypeSet.iterator();
			String moneyType =null;
			String colPre = null;
			String endStr =null;
			
			BigDecimal totalAmount =FDCHelper.ZERO;
			BigDecimal settleAmount =FDCHelper.ZERO;
			BigDecimal useAmount =FDCHelper.ZERO;
			
			while(it.hasNext()){
				 moneyType = (String)it.next();
				 colPre = moneyType+"_";
				 endStr ="totalAmount";
				 totalAmount =FDCHelper.ZERO;
				 settleAmount =FDCHelper.ZERO;
				 useAmount =FDCHelper.ZERO;
				if(MoneyTypeEnum.PREMONEY_VALUE.equals(moneyType)){
					endStr = "revAmount";
				} 
				
				if(this.tblMain.getColumn(colPre+endStr)==null ){
					continue;
				}
				
				
				for(int i=start ;i<end;i++){
					//if(this.tblMain.getCell(i, colPre+"settleAmount")!=null){
					Object o = this.tblMain.getCell(i,colPre + "settleAmount").getValue();
					if(o!=null){
						if(!(o instanceof BigDecimal)){
							this.tblMain.getCell(i,colPre + "settleAmount").setValue(new BigDecimal(o.toString()));
						}
						settleAmount =settleAmount.add(this.tblMain.getCell(i,
								colPre + "settleAmount").getValue() == null ? FDCHelper.ZERO
								: (BigDecimal) this.tblMain.getCell(i,colPre + "settleAmount").getValue());
					}
					
					o = this.tblMain.getCell(i,colPre + endStr).getValue();
					if(o!=null){
						if(!(o instanceof BigDecimal)){
								this.tblMain.getCell(i,colPre + endStr).setValue(new BigDecimal(o.toString()));
						}
						totalAmount =totalAmount.add(this.tblMain.getCell(i,
								colPre + endStr).getValue() == null ? FDCHelper.ZERO
								: (BigDecimal) this.tblMain.getCell(i,colPre + endStr).getValue());
						
					}
					this.tblMain.getCell(i,colPre + "settleAmount").setValue(FDCHelper.ZERO);
					this.tblMain.getCell(i,colPre + endStr).setValue(FDCHelper.ZERO);
					this.tblMain.getCell(i,colPre + "useAmount").setValue(FDCHelper.ZERO);
				}
				useAmount = totalAmount.subtract(settleAmount);
				
				/*for(int i=start ;i<end;i++){
					tblMain.getCell(i, colPre+endStr).setValue(totalAmount);
					tblMain.getCell(i, colPre+"settleAmount").setValue(settleAmount);
					tblMain.getCell(i, colPre+"useAmount").setValue(useAmount);
				}*/
				
				tblMain.getCell(start, colPre+endStr).setValue(totalAmount);
				tblMain.getCell(start, colPre+"settleAmount").setValue(settleAmount);
				tblMain.getCell(start, colPre+"useAmount").setValue(useAmount);
				
				if(start+1>=end){
					continue;
				}
				//tblMain.getMergeManager().setDataMode(KDTMergeManager.DATA_KEEPFIRST);
				if(!this.tblMain.getColumn(colPre+endStr).getStyleAttributes().isHided()){
					//tblMain.getMergeManager().getMergeBlockSet();
					tblMain.getMergeManager().mergeBlock(start, this.tblMain.getColumnIndex(colPre+endStr), end-1, 
							this.tblMain.getColumnIndex(colPre+endStr),KDTMergeManager.SPECIFY_MERGE );
					tblMain.getMergeManager().mergeBlock(start, this.tblMain.getColumnIndex(colPre+"settleAmount"), end-1, 
							this.tblMain.getColumnIndex(colPre+"settleAmount"),KDTMergeManager.SPECIFY_MERGE );
					tblMain.getMergeManager().mergeBlock(start, this.tblMain.getColumnIndex(colPre+"useAmount"), end-1, 
							this.tblMain.getColumnIndex(colPre+"useAmount"),KDTMergeManager.SPECIFY_MERGE );
					System.out.print(" ");
				}
				
			}
			
			
			
		}
		
		
	}
	
	private void updateMoneyTypeTotal(IRow row,String moneyType,String moneydefineId,RowMoneyDefineObj obj){
		
	
		String colPre = moneyType+"_";
		String endStr ="totalAmount";
		
		if(MoneyTypeEnum.PREMONEY_VALUE.equals(moneyType)){
			endStr = "revAmount";
		}
		BigDecimal rowsettletotal = FDCHelper.ZERO;
		BigDecimal rowamttotal = FDCHelper.ZERO;
		
		if(this.tblMain.getColumn(colPre+"settleAmount")!=null){  //ת��
			
			Object o = row.getCell(colPre+"settleAmount").getValue();
			if(o!=null){
				try{
					rowsettletotal = (BigDecimal)o;
					rowsettletotal = rowsettletotal.add(obj.getSettleOutAmount()==null?FDCHelper.ZERO:obj.getSettleOutAmount());
				}catch (Exception e) {
					
				}
			}else if(obj.getSettleOutAmount()!=null){
				rowsettletotal = obj.getSettleOutAmount();
			}
			row.getCell(colPre+"settleAmount").setValue(rowsettletotal);
		}
		
		
		if(this.tblMain.getColumn(colPre+endStr)!=null){  //�տ���
			
			Object o = row.getCell(colPre+endStr).getValue();
			if(o!=null){
				try{
					rowamttotal = (BigDecimal)o;
					rowamttotal = rowamttotal.add(obj.getAmt()==null?FDCHelper.ZERO:obj.getAmt());
				}catch (Exception e) {
					
				}
			}else if(obj.getAmt()!=null){
				rowamttotal = obj.getAmt();
			}
			row.getCell(colPre+endStr).setValue(rowamttotal);
		}
		
		if(row.getCell(colPre+"settleAmount")!=null || row.getCell(colPre+endStr)!=null){
			row.getCell(colPre+"useAmount").setValue(rowamttotal.subtract(rowsettletotal));
		}

	}
	
	
	
	/**
	 * 
	 * @param indexMap
	 * @param settlementType   ���㷽ʽ
	 * @param bankNumber	   �����˻�
	 * @return
	 */
	private int getASameRow(Map indexMap,RowMoneyDefineObj obj){
		
		String settlementType = obj.getSettlementType();
		String bankNumber  = obj.getBankNumber();
		
		int count=0;
		boolean exist = false;
		if(indexMap!=null){
			
			Iterator  index = indexMap.keySet().iterator();
			while (index.hasNext()){  //
				
				String indexID = (String)index.next();
				
				
				SettleTypeBank settleTypeBank = (SettleTypeBank)indexMap.get(indexID); //
				if(settleTypeBank!=null){
					if(settleTypeBank.equals(new SettleTypeBank(settlementType,bankNumber))){
						exist = true;
						return Integer.parseInt(indexID);					//
					}
				}
				count++;
			}
			if(!exist){
				indexMap.put(indexMap.size()+"", new SettleTypeBank(settlementType,bankNumber));
				return count;
			}
		}
		
		
		indexMap = new HashMap();
		indexMap.put(0+"", new SettleTypeBank(settlementType,bankNumber));
		
		return 0;
	}
	
	
	protected void limitTotal(int start,int end,IRow row)throws BOSException{
		if(start<end){
			FDCCustomerParams para = new FDCCustomerParams(this.getFilterUI().getCustomerParams());
			//Ԥ�տ�
			if(this.getFilterUI().isPreMoney(para)){
				//preMoney();
				limitTotal(start,end,MoneyTypeEnum.PreMoney,row);
			}
			////Ԥ�ս� 
			if(this.getFilterUI().isPreconcertMoney(para)){
				limitTotal(start,end,MoneyTypeEnum.PreconcertMoney,row);
				//createAMoneyDefineTypeHead(MoneyTypeEnum.PreconcertMoney);
			}
			//����
			if(this.getFilterUI().ispurchaseAmount(para)){
				limitTotal(start,end,MoneyTypeEnum.EarnestMoney,row);
				//createAMoneyDefineTypeHead(MoneyTypeEnum.EarnestMoney);
			}
			
			//����
			if(this.getFilterUI().isFisrtAmount(para)){
				limitTotal(start,end,MoneyTypeEnum.FisrtAmount,row);
				//createAMoneyDefineTypeHead(MoneyTypeEnum.FisrtAmount);
			}
			//¥��
			if(this.getFilterUI().isHouseAmount(para)){
				limitTotal(start,end,MoneyTypeEnum.HouseAmount,row);
				//createAMoneyDefineTypeHead(MoneyTypeEnum.HouseAmount);
			}
			//���ҿ�
			if(this.getFilterUI().isLoanAmount(para)){
				limitTotal(start,end,MoneyTypeEnum.LoanAmount,row);
				//createAMoneyDefineTypeHead(MoneyTypeEnum.LoanAmount);
			}
			//������
			if(this.getFilterUI().isAccFundAmount(para)){
				limitTotal(start,end,MoneyTypeEnum.AccFundAmount,row);
				//createAMoneyDefineTypeHead(MoneyTypeEnum.AccFundAmount);
			}
			//�����
			if(this.getFilterUI().isCompensateAmount(para)){
				limitTotal(start,end,MoneyTypeEnum.CompensateAmount,row);
				//createAMoneyDefineTypeHead(MoneyTypeEnum.CompensateAmount);
			}
			//װ�޿�
			if(this.getFilterUI().isFitmentAmount(para)){
				limitTotal(start,end,MoneyTypeEnum.FitmentAmount,row);
				//createAMoneyDefineTypeHead(MoneyTypeEnum.FitmentAmount);
			}
			//���շ���
			if(this.getFilterUI().isReplaceFee(para)){
				limitTotal(start,end,MoneyTypeEnum.ReplaceFee,row);
				//createAMoneyDefineTypeHead(MoneyTypeEnum.ReplaceFee);
			}
			//������
			if(this.getFilterUI().isCommissionCharge(para)){
				limitTotal(start,end,MoneyTypeEnum.CommissionCharge,row);
				//createAMoneyDefineTypeHead(MoneyTypeEnum.CommissionCharge);
			}
			//���ɽ�
			if(this.getFilterUI().isLateFee(para)){
				limitTotal(start,end,MoneyTypeEnum.LateFee,row);
				//createAMoneyDefineTypeHead(MoneyTypeEnum.LateFee);
			}
			//����
			if(this.getFilterUI().isElseAmount(para)){
				limitTotal(start,end,MoneyTypeEnum.ElseAmount,row);
				//createAMoneyDefineTypeHead(MoneyTypeEnum.ElseAmount);
			}
			
			
			for(int i=start;i<end;i++){
				this.tblMain.getCell(i, "");
			}
		}
	}
	
	
	protected void limitTotal(int start,int end,MoneyTypeEnum moneytype,IRow row)throws  BOSException{
		MoneyDefineCollection coll =  getATypeMoneyDefineCollection(moneytype);
		//��������
    	for(int i = 0; i < coll.size(); i ++)	{
			MoneyDefineInfo info = coll.get(i);
			//����
			String idpre =moneytype.getValue()+"_"+info.getId().toString()+"_";
			row.getCell(idpre+"0").setValue(totalColumn(start,end,idpre+"0"));
			row.getCell(idpre+"2").setValue(totalColumn(start,end,idpre+"2"));

		}
    	if(coll.size()>1){
	    	//�ϼ���
    		if(MoneyTypeEnum.PreMoney.equals(moneytype)){
    			row.getCell(moneytype.getValue()+"_revAmount").setValue(totalColumn(start,end,moneytype.getValue()+"_revAmount"));
    		}else{
    			row.getCell(moneytype.getValue()+"_totalAmount").setValue(totalColumn(start,end,moneytype.getValue()+"_totalAmount"));
    		}
    		
    		row.getCell(moneytype.getValue()+"_settleAmount").setValue(totalColumn(start,end,moneytype.getValue()+"_settleAmount"));
    		row.getCell(moneytype.getValue()+"_useAmount").setValue(totalColumn(start,end,moneytype.getValue()+"_useAmount"));
    		
    	}
		
	}
	
	protected BigDecimal totalColumn(int start,int end,String colIndex)throws  BOSException{
		BigDecimal total = null;
		if(start<end && this.tblMain.getColumn(colIndex)!=null ){
			total =FDCHelper.ZERO;
			
			for(int i=start;i<end;i++){
				BigDecimal val = (BigDecimal)this.tblMain.getCell(i, colIndex).getValue();
				if(val!=null){
					total = total.add(val);
				}
				
			}
		}
		if(FDCHelper.ZERO.equals(total)){
			return null;
		}
		return total;
	}
	
	public void fillHeadData(IRow row ,Map head){
		
		row.getCell("customer").setValue(head.get("customer"));
		row.getCell("sellProject").setValue(head.get("sellProject"));
		row.getCell("area").setValue(head.get("area"));
		row.getCell("building").setValue(head.get("building"));;
		row.getCell("unit").setValue(head.get("unit"));
		row.getCell("room").setValue(head.get("room"));
		
	}
	
	public void fillBodyData(IRow row ,String moneyType,String moneydefineId,RowMoneyDefineObj obj){
		
		String colPre = moneyType+"_"+moneydefineId+"_";
		
		row.getCell(colPre+"0").setValue(obj.getAmt());
		row.getCell(colPre+"1").setValue(obj.getSettlementType());
		row.getCell(colPre+"2").setValue(obj.getSettlementAmount());
		row.getCell(colPre+"3").setValue(obj.getBankName());
		row.getCell(colPre+"4").setValue(obj.getBankNumber());
		
		if(MoneyTypeEnum.PREMONEY_VALUE.equals(moneyType)){
			row.getCell(colPre+"5").setValue(obj.getSettleOutAmount());
			if(obj.getAmt()!=null){
				if(obj.getSettleOutAmount()==null){
					row.getCell(colPre+"6").setValue(obj.getAmt());
				}else{
					row.getCell(colPre+"6").setValue(obj.getAmt().subtract(obj.getSettleOutAmount()));
				}
				///�����˿�Ϊ0�����
				if(row.getCell(colPre+"6").getValue()!=null){
					 BigDecimal col6Value = (BigDecimal)row.getCell(colPre+"6").getValue();
					 if(col6Value.compareTo(new BigDecimal("0"))<0){
						 row.getCell(colPre+"6").setValue(null);
					 }
				}
			}
		}
		
		
	}
	
	
	protected void tblMain_tableClicked(KDTMouseEvent e) throws Exception
	{
		
	}
	
	public void actionRefresh_actionPerformed(ActionEvent e) throws Exception {
		this.execQuery();
	}

	protected void tblMain_tableSelectChanged(KDTSelectEvent e)
			throws Exception
	{

	}
	
	protected void execQuery() {
		
		try {
			initTable();
		} catch (BOSException e) {
			e.printStackTrace();
		}
	}
	
	
	
	/**
	 * 
	 * @param type  �������
	 * @return   �Ƿ������˸����
	 */
	private boolean hideATypeNullCol(MoneyTypeEnum type){
		//�Զ�����û��ʹ�õ��Ŀ������ơ�
		int beginIndex = this.tblMain.getColumnIndex("room");
		int endIndex = this.tblMain.getColumnCount();
		
		int typeStart=beginIndex;  //������
		int defineStart=beginIndex+1; //�����������

		int moneyTypeNum =0;  //�ɼ�����������Ŀ

		String moneyDefineOldId =null;	 //
		String moneyDefineCurrId =null;	 //������µ�ǰ ��������
				
		boolean isUsed = false;
		//start		
		for(int i = beginIndex; i < endIndex; i++)  /// 
		{
					if(this.tblMain.getColumnKey(i).startsWith(type.getValue())){ //��Ҫ�������� �е����
						defineStart = typeStart = i; 
							 break;
					}
		}	
		int typeEnd = this.tblMain.getColumnIndex(type.getValue()+"_useAmount");
		
		
		
		for(int i = typeStart; i < typeEnd; i++)  /// 
		{
			//�õ���������ID
			String [] temp = this.tblMain.getColumnKey(i).split("_");
			if(temp!=null && temp.length==3){
				moneyDefineCurrId =  temp[1];
			}else{ //�ϼ���
				if(!isUsed){  //��ǰһ�������Ƿ�������
					for(int j =defineStart;j<i;j++){	//���ظĿ�������
						tblMain.getColumn(j).getStyleAttributes().setHided(true);
					}
				}else{
					moneyTypeNum++;  //�ɼ� ����������
				}
				break;
			}
			
			//�µĿ�������
			if(!moneyDefineCurrId.equals(moneyDefineOldId)){
				
				moneyDefineOldId = moneyDefineCurrId;
				//hideNullMoneyDefine(defineStart,i-1,false,type);
				if(!isUsed){
					for(int j =defineStart;j<i;j++){	//������һ����������
 						tblMain.getColumn(j).getStyleAttributes().setHided(true);
					}
				}else{
					moneyTypeNum++;  //�ɼ� ����������
				}
				defineStart  = i;  //
				isUsed =false;
				
			}else if(isUsed){  //��ǰ��������������
				
				 continue;
			}
			
			//������������
			for(int rowIndex = 0; rowIndex < tblMain.getRowCount(); ++rowIndex)		//��
			{
				if(this.tblMain.getCell(rowIndex, i).getValue()!=null && !"".equals(this.tblMain.getCell(rowIndex, i).getValue()))
				{
					isUsed = true;
					break;
				}
			}
			
		}
		if(moneyTypeNum<=1){
			if(typeEnd>0){
				if(MoneyTypeEnum.PreMoney.equals(type)){
					tblMain.getColumn(type.getValue()+"_revAmount").getStyleAttributes().setHided(true);
				}else{
					tblMain.getColumn(type.getValue()+"_totalAmount").getStyleAttributes().setHided(true);
				}
				tblMain.getColumn(type.getValue()+"_settleAmount").getStyleAttributes().setHided(true);
				tblMain.getColumn(type.getValue()+"_useAmount").getStyleAttributes().setHided(true);
			}
			
			if(moneyTypeNum<1){
				return true;
			}
		}
		return false;

	}
	
	
	
	//���ز���Ҫ�Ŀ������ ���ϼ���
	protected void hideNullType(){
		FDCCustomerParams para = new FDCCustomerParams(this.getFilterUI().getCustomerParams());
		int start = this.tblMain.getColumnIndex("room")+1;
		boolean havReceAmount = false;
		boolean havtotalcol = false;
		
		//Ԥ�տ�
		if(this.getFilterUI().isPreMoney(para)){
			boolean tempval = hideATypeNullCol(MoneyTypeEnum.PreMoney);
			if(!tempval){
				havtotalcol = true;
			}
		}
		
		
		////Ԥ�ս� 
		if(this.getFilterUI().isPreconcertMoney(para)){
			hideATypeNullCol(MoneyTypeEnum.PreconcertMoney);
		}
		//����
		if(this.getFilterUI().ispurchaseAmount(para)){
			hideATypeNullCol(MoneyTypeEnum.EarnestMoney);
		}
		//����
		if(this.getFilterUI().isFisrtAmount(para)){
			hideATypeNullCol(MoneyTypeEnum.FisrtAmount);
		}
		//¥��
		if(this.getFilterUI().isHouseAmount(para)){
			hideATypeNullCol(MoneyTypeEnum.HouseAmount);
		}
		//���ҿ�
		if(this.getFilterUI().isLoanAmount(para)){
			hideATypeNullCol(MoneyTypeEnum.LoanAmount);
		}
		//������
		if(this.getFilterUI().isAccFundAmount(para)){
			hideATypeNullCol(MoneyTypeEnum.AccFundAmount);
		}
		//�����
		if(this.getFilterUI().isCompensateAmount(para)){
			hideATypeNullCol(MoneyTypeEnum.CompensateAmount);
		}
		
		int end = this.tblMain.getColumnIndex(KEY_SHOUKUAN_TOTAL);
		
		if(end<0){
			end = start;
		}else	if(end>start){  //�����տ�ϼ�
			for(int i=start;i<end;i++){
				if(!this.tblMain.getColumn(i).getStyleAttributes().isHided()){
					havReceAmount =true;
					havtotalcol =true;
					break;
				}
			}
			if(!havReceAmount){
				this.tblMain.getColumn(KEY_SHOUKUAN_TOTAL).getStyleAttributes().setHided(true);
			}
		}
		havReceAmount =false;
		start= end+1;
	
		
		//װ�޿�
		if(this.getFilterUI().isFitmentAmount(para)){
			hideATypeNullCol(MoneyTypeEnum.FitmentAmount);
		}
		//���շ���
		if(this.getFilterUI().isReplaceFee(para)){
			hideATypeNullCol(MoneyTypeEnum.ReplaceFee);
		}
		//������
		if(this.getFilterUI().isCommissionCharge(para)){
			hideATypeNullCol(MoneyTypeEnum.CommissionCharge);
		}
		//���ɽ�
		if(this.getFilterUI().isLateFee(para)){
			hideATypeNullCol(MoneyTypeEnum.LateFee);
		}
		//����
		if(this.getFilterUI().isElseAmount(para)){
			hideATypeNullCol(MoneyTypeEnum.ElseAmount);
		}
		
		end = this.tblMain.getColumnIndex(KEY_ELSE_TOTAL);
		if(end<0){
			end = start;
		}else if(end>start){  //�����տ�ϼ�
			for(int i=start;i<end;i++){
				if(!this.tblMain.getColumn(i).getStyleAttributes().isHided()){
					havReceAmount =true;
					havtotalcol =true;
					break;
				}
			}
			if(!havReceAmount){
				this.tblMain.getColumn(KEY_ELSE_TOTAL).getStyleAttributes().setHided(true);
			}
		}
		
		
		if(!havtotalcol ){
			this.tblMain.getColumn(KEY_ALL_TOTAL).getStyleAttributes().setHided(true);
		}
	}
	
	
	//������Ҫ���˵Ŀ������
	protected Set getSelectItemArray(){
		Set set = new HashSet();
		
		Map filterMap = CommerceHelper.convertFilterItemCollToMap(this.mainQuery.getFilter());
		
		FDCCustomerParams para = new FDCCustomerParams(this.getFilterUI().getCustomerParams());
		
		boolean isDefault=false; 
		//Ĭ�Ϸ�������
		if(para.get("dateFrom")==null && para.get("dateTo")==null && para.get("yearFrom")==null && para.get("yearTo")==null){
			isDefault =true;
		}
		
		
		//Ԥ�տ�
		if(isDefault){
			if(filterMap.containsKey("preMoney")){
				if(((Integer)filterMap.get("preMoney")).intValue()==1){
					set.add(MoneyTypeEnum.PREMONEY_VALUE);
				}
			}
		}else if(this.getFilterUI().isPreMoney(para)){
			set.add(MoneyTypeEnum.PREMONEY_VALUE);
		}
		
		////Ԥ�ս� 
		if(isDefault){
			if(filterMap.containsKey("preconcertMoney")){
				if(((Integer)filterMap.get("preconcertMoney")).intValue()==1){
					set.add(MoneyTypeEnum.PRECONCERTMONEY_VALUE);
				}
			}
		}else if(this.getFilterUI().isPreconcertMoney(para)){
			set.add(MoneyTypeEnum.PRECONCERTMONEY_VALUE);
		}
		
		//����
		if(isDefault){
			if(filterMap.containsKey("purchaseAmount")){
				if(((Integer)filterMap.get("purchaseAmount")).intValue()==1){
					set.add(MoneyTypeEnum.EARNESTMONEY_VALUE);
				}
			}
		}else if(this.getFilterUI().ispurchaseAmount(para)){
			set.add(MoneyTypeEnum.EARNESTMONEY_VALUE);
		}
		
		//����
		if(isDefault){
			if(filterMap.containsKey("fisrtAmount")){
				if(((Integer)filterMap.get("fisrtAmount")).intValue()==1){
					set.add(MoneyTypeEnum.FISRTAMOUNT_VALUE);
				}
			}
		}else  if(this.getFilterUI().isFisrtAmount(para)){
			set.add(MoneyTypeEnum.FISRTAMOUNT_VALUE);
		}
		//¥��
		if(isDefault){
			if(filterMap.containsKey("houseAmount")){
				if(((Integer)filterMap.get("houseAmount")).intValue()==1){
					set.add(MoneyTypeEnum.HOUSEAMOUNT_VALUE);
				}
			}
		}else  if(this.getFilterUI().isHouseAmount(para)){
			set.add(MoneyTypeEnum.HOUSEAMOUNT_VALUE);
		}
		//���ҿ�
		if(isDefault){
			if(filterMap.containsKey("loanAmount")){
				if(((Integer)filterMap.get("loanAmount")).intValue()==1){
					set.add(MoneyTypeEnum.LOANAMOUNT_VALUE);
				}
			}
		}else if(this.getFilterUI().isLoanAmount(para)){
			set.add(MoneyTypeEnum.LOANAMOUNT_VALUE);
		}
		//������
		if(isDefault){
			if(filterMap.containsKey("accFund")){
				if(((Integer)filterMap.get("accFund")).intValue()==1){
					set.add(MoneyTypeEnum.ACCFUNDAMOUNT_VALUE);
				}
			}
		}else if(this.getFilterUI().isAccFundAmount(para)){
			set.add(MoneyTypeEnum.ACCFUNDAMOUNT_VALUE);
		}
		//�����
		if(isDefault){
			if(filterMap.containsKey("compensateAmount")){
				if(((Integer)filterMap.get("compensateAmount")).intValue()==1){
					set.add(MoneyTypeEnum.COMPENSATEAMOUNT_VALUE);
				}
			}
		}else if(this.getFilterUI().isCompensateAmount(para)){
			set.add(MoneyTypeEnum.COMPENSATEAMOUNT_VALUE);
		}
		//װ�޿�
		if(isDefault){
			if(filterMap.containsKey("fitmentAmount")){
				if(((Integer)filterMap.get("fitmentAmount")).intValue()==1){
					set.add(MoneyTypeEnum.FITMENTAMOUNT_VALUE);
				}
			}
		}else  if(this.getFilterUI().isFitmentAmount(para)){
			set.add(MoneyTypeEnum.FITMENTAMOUNT_VALUE);
		}
		//���շ���
		if(isDefault){
			if(filterMap.containsKey("replaceFee")){
				if(((Integer)filterMap.get("replaceFee")).intValue()==1){
					set.add(MoneyTypeEnum.REPLACEFEE_VALUE);
				}
			}
		}else if(this.getFilterUI().isReplaceFee(para)){
			set.add(MoneyTypeEnum.REPLACEFEE_VALUE);
		}
		//������
		if(isDefault){
			if(filterMap.containsKey("commissionCharge")){
				if(((Integer)filterMap.get("commissionCharge")).intValue()==1){
					set.add(MoneyTypeEnum.COMMISSIONCHARGE_VALUE);
				}
			}
		}else if(this.getFilterUI().isCommissionCharge(para)){
			set.add(MoneyTypeEnum.COMMISSIONCHARGE_VALUE);
		}
		//���ɽ�
		if(isDefault){
			if(filterMap.containsKey("lateFee")){
				if(((Integer)filterMap.get("lateFee")).intValue()==1){
					set.add(MoneyTypeEnum.LATEFEE_VALUE);
				}
			}
		}else if(this.getFilterUI().isLateFee(para)){
			set.add(MoneyTypeEnum.LATEFEE_VALUE);
		}
		//����
		if(isDefault){
			if(filterMap.containsKey("elseAmount")){
				if(((Integer)filterMap.get("elseAmount")).intValue()==1){
					set.add(MoneyTypeEnum.ELSEAMOUNT_VALUE);
				}
			}
		}else if(this.getFilterUI().isElseAmount(para)){
			set.add(MoneyTypeEnum.ELSEAMOUNT_VALUE);
		}
		
		return set; 
	}
	
	protected void intTableHead()throws BOSException{
		
		Map filterMap = CommerceHelper.convertFilterItemCollToMap(this.mainQuery.getFilter());
		
		FDCCustomerParams para = new FDCCustomerParams(this.getFilterUI().getCustomerParams());
		
		boolean isDefault=false; 
		//Ĭ�Ϸ�������
		if(para.get("dateFrom")==null && para.get("dateTo")==null && para.get("yearFrom")==null && para.get("yearTo")==null){
			isDefault =true;
		}
		
		
		int begin = this.tblMain.getColumnIndex("room");
		
		//Ԥ�տ�
		if((isDefault && filterMap.containsKey("preMoney") && ((Integer)filterMap.get("preMoney")).intValue()==1) ||
				this.getFilterUI().isPreMoney(para)){
			preMoney();
		}
		
		int start = this.tblMain.getColumnCount();
		////Ԥ�ս� 
		if((isDefault && filterMap.containsKey("preconcertMoney") && ((Integer)filterMap.get("preconcertMoney")).intValue()==1) ||
				this.getFilterUI().isPreconcertMoney(para)){
			createAMoneyDefineTypeHead(MoneyTypeEnum.PreconcertMoney);
		}
		//����
		if((isDefault && filterMap.containsKey("purchaseAmount") && ((Integer)filterMap.get("purchaseAmount")).intValue()==1) ||
				this.getFilterUI().ispurchaseAmount(para)){
			createAMoneyDefineTypeHead(MoneyTypeEnum.EarnestMoney);
		}
		
		//����
		if((isDefault && filterMap.containsKey("fisrtAmount") && ((Integer)filterMap.get("fisrtAmount")).intValue()==1) ||
				this.getFilterUI().isFisrtAmount(para)){
			createAMoneyDefineTypeHead(MoneyTypeEnum.FisrtAmount);
		}
		//¥��
		if((isDefault && filterMap.containsKey("houseAmount") && ((Integer)filterMap.get("houseAmount")).intValue()==1) ||
				this.getFilterUI().isHouseAmount(para)){
			createAMoneyDefineTypeHead(MoneyTypeEnum.HouseAmount);
		}
		//���ҿ�
		if((isDefault && filterMap.containsKey("loanAmount") && ((Integer)filterMap.get("loanAmount")).intValue()==1) ||
				this.getFilterUI().isLoanAmount(para)){
			createAMoneyDefineTypeHead(MoneyTypeEnum.LoanAmount);
		}
		//������
		if((isDefault && filterMap.containsKey("accFund") && ((Integer)filterMap.get("accFund")).intValue()==1) ||
				this.getFilterUI().isAccFundAmount(para)){
			createAMoneyDefineTypeHead(MoneyTypeEnum.AccFundAmount);
		}
		//�����
		if((isDefault && filterMap.containsKey("compensateAmount") && ((Integer)filterMap.get("compensateAmount")).intValue()==1) ||
				this.getFilterUI().isCompensateAmount(para)){
			createAMoneyDefineTypeHead(MoneyTypeEnum.CompensateAmount);
		}
		int end = this.tblMain.getColumnCount();
		
		IRow row2 = this.tblMain.getHeadRow(2);
		IRow row1 = this.tblMain.getHeadRow(1);
    	IRow row0 = this.tblMain.getHeadRow(0);
		if(end>start){  //�����տ�ϼ�
	    	createTotalCol(row0,row1,row2,KEY_SHOUKUAN_TOTAL,"","�����տ�ϼ�");
			end++;
			tblMain.getHeadMergeManager().mergeBlock(1, end-1, 2, end-1,KDTMergeManager.FREE_MERGE);
		}
		tblMain.getHeadMergeManager().mergeBlock(0, start, 0, end-1,KDTMergeManager.SPECIFY_MERGE);
		
		start= end;
		//װ�޿�
		if((isDefault && filterMap.containsKey("fitmentAmount") && ((Integer)filterMap.get("fitmentAmount")).intValue()==1) ||
				this.getFilterUI().isFitmentAmount(para)){
			createAMoneyDefineTypeHead(MoneyTypeEnum.FitmentAmount);
		}
		//���շ���
		if((isDefault && filterMap.containsKey("replaceFee") && ((Integer)filterMap.get("replaceFee")).intValue()==1) ||
				this.getFilterUI().isReplaceFee(para)){
			createAMoneyDefineTypeHead(MoneyTypeEnum.ReplaceFee);
		}
		//������
		if((isDefault && filterMap.containsKey("commissionCharge") && ((Integer)filterMap.get("commissionCharge")).intValue()==1) ||
				this.getFilterUI().isCommissionCharge(para)){
			createAMoneyDefineTypeHead(MoneyTypeEnum.CommissionCharge);
		}
		//���ɽ�
		if((isDefault && filterMap.containsKey("lateFee") && ((Integer)filterMap.get("lateFee")).intValue()==1) ||
				this.getFilterUI().isLateFee(para)){
			createAMoneyDefineTypeHead(MoneyTypeEnum.LateFee);
		}
		//����
		if((isDefault && filterMap.containsKey("elseAmount") && ((Integer)filterMap.get("elseAmount")).intValue()==1) ||
				this.getFilterUI().isElseAmount(para)){
			createAMoneyDefineTypeHead(MoneyTypeEnum.ElseAmount);
		}
		
		end = this.tblMain.getColumnCount();
		if(end>start){//�����տ�ϼ�
	    	createTotalCol(row0,row1,row2,KEY_ELSE_TOTAL,"","�����տ�ϼ�");
			end++;
			tblMain.getHeadMergeManager().mergeBlock(1, end-1, 2, end-1,KDTMergeManager.FREE_MERGE);
		}
		tblMain.getHeadMergeManager().mergeBlock(0, start, 0, end-1,KDTMergeManager.SPECIFY_MERGE);
		
		if(end>begin ||this.getFilterUI().isPreMoney(para)){
			createTotalCol(row0,row1,row2,KEY_ALL_TOTAL,"�տ�ϼ�","�տ�ϼ�");
			end++;
			tblMain.getHeadMergeManager().mergeBlock(0, end-1, 2, end-1,KDTMergeManager.FREE_MERGE);
		}
		
		for(int i=6;i<this.tblMain.getColumnCount();i++)
		{
			if(tblMain.getColumnKey(i).endsWith("1") || tblMain.getColumnKey(i).endsWith("3") || tblMain.getColumnKey(i).endsWith("4")){
				continue;
			}
				tblMain.getColumn(i).getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
				tblMain.getColumn(i).getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(2));
			//}
		}
	}
	
	/**
	 * ͨ�ÿ�������ͷ
	 * @param enum
	 * @throws BOSException
	 * 
	 * �� ID ͳһ�������� ��������ö��ֵ + "_"+��������Id +"_"+ ���б��� 
	 * ��  MoneyTypeEnum.PREMONEY_VALUE+"_"+info.getId().toString()+"_"+j;
	 */
	protected void createAMoneyDefineTypeHead(MoneyTypeEnum typeenum)throws BOSException{
		MoneyDefineCollection coll = getATypeMoneyDefineCollection(typeenum);
		
		IRow row2 = this.tblMain.getHeadRow(2);
		IRow row1 = this.tblMain.getHeadRow(1);
    	IRow row0 = this.tblMain.getHeadRow(0);
    	
    	int start = tblMain.getColumnCount();
    	
    	String titleDisplayname = getTitleNameBy(typeenum);
    	//��������
    	for(int i = 0; i < coll.size(); i ++)	{
			MoneyDefineInfo info = coll.get(i);
			//����
	    	for(int j=0;j<5;j++){
	    		IColumn col = this.tblMain.addColumn();
	    		String id =typeenum.getValue()+"_"+info.getId().toString()+"_"+j;
		    	col.setKey(id);
		    	createCol(row0,row1,id,titleDisplayname,info.getName());
	    	}
	    	getSimpleNameList(row2,typeenum.getValue()+"_"+info.getId().toString()+"_");
		}
    	//if(coll.size()>1){
    	if(coll.size()>0){
	    	//�ϼ���
    		createPublicTotalCol(row0,row1,row2,typeenum.getValue(),titleDisplayname,typeenum.getAlias());
    	}
    	int end =  tblMain.getColumnCount();
    	if(end>start){
    		tblMain.getHeadMergeManager().mergeBlock(0, start, 0, end-1,KDTMergeManager.FREE_MERGE);
    		if(coll.size()>1){
        		tblMain.getHeadMergeManager().mergeBlock(1, start, 1, end-4,KDTMergeManager.FREE_MERGE);
        		tblMain.getHeadMergeManager().mergeBlock(1, end-3, 2, end-1,KDTMergeManager.FREE_MERGE);
    		}else{
        		tblMain.getHeadMergeManager().mergeBlock(1, start, 1, end-1,KDTMergeManager.FREE_MERGE);
        		tblMain.getHeadMergeManager().mergeBlock(1, end-3, 2, end-1,KDTMergeManager.FREE_MERGE);
        		//
        		tblMain.getColumn(typeenum.getValue()+"_totalAmount").getStyleAttributes().setHided(true);
        		tblMain.getColumn(typeenum.getValue()+"_settleAmount").getStyleAttributes().setHided(true);
        		tblMain.getColumn(typeenum.getValue()+"_useAmount").getStyleAttributes().setHided(true);
    		}
    	}
	}
	
	
	private String getTitleNameBy(MoneyTypeEnum typeenum){
		
		if(MoneyTypeEnum.FitmentAmount.equals(typeenum) 
				||MoneyTypeEnum.ReplaceFee.equals(typeenum)
				||MoneyTypeEnum.CommissionCharge.equals(typeenum)
				||MoneyTypeEnum.LateFee.equals(typeenum)
				||MoneyTypeEnum.ElseAmount.equals(typeenum)
		){
			return "ʵ������";
		}
			
			return "ʵ�շ���";
	}
	
	
	/**
	 * ͨ�úϼ��б�ͷ ,������Ԥ�տ�
	 * @param row0   
	 * @param row1
	 * @param row2
	 * @param headId_Pre    �������� ֵ ,�� MoneyTypeEnum.PRECONCERTMONEY_VALUE
	 * @param headName 		�������� ����, ��Ԥ����
	 * 
	 * ͳһ��������  ��������ö��ֵ + "_"+�̶�ֵ
	 */
	protected void createPublicTotalCol(IRow row0,IRow row1,IRow row2,String headId_Pre,String headName,String mergeName){
		createTotalCol(row0,row1,row2,headId_Pre+"_totalAmount",headName,mergeName+"�ϼ�");
		createTotalCol(row0,row1,row2,headId_Pre+"_settleAmount",headName,"��ת"+mergeName);
		createTotalCol(row0,row1,row2,headId_Pre+"_useAmount",headName,mergeName+"�������");
	}
	

	//////////////////13�� ���� 
	//Ԥ�տ�
	protected void preMoney()throws BOSException{
		MoneyDefineCollection coll = getATypeMoneyDefineCollection(MoneyTypeEnum.PreMoney);
		
		IRow row2 = this.tblMain.getHeadRow(2);
		IRow row1 = this.tblMain.getHeadRow(1);
    	IRow row0 = this.tblMain.getHeadRow(0);
    	
    	int start = tblMain.getColumnCount();
    	//��������
    	for(int i = 0; i < coll.size(); i ++)	{
			MoneyDefineInfo info = coll.get(i);
			//����
	    	for(int j=0;j<7;j++){
	    		IColumn col = this.tblMain.addColumn();
	    		String id =MoneyTypeEnum.PREMONEY_VALUE+"_"+info.getId().toString()+"_"+j;
		    	col.setKey(id);
		    	createCol(row0,row1,id,"Ԥ�տ�",info.getName());
	    	}
	    	getPreMoneyNameList(row2,MoneyTypeEnum.PREMONEY_VALUE+"_"+info.getId().toString()+"_");
		}
    	//if(coll.size()>1){  
    	if(coll.size()>0){  
	    	//�ϼ���
	    	createTotalCol(row0,row1,row2,MoneyTypeEnum.PREMONEY_VALUE+"_revAmount","Ԥ�տ�","Ԥ�տ��տ��ܶ�");
	    	createTotalCol(row0,row1,row2,MoneyTypeEnum.PREMONEY_VALUE+"_settleAmount","Ԥ�տ�","Ԥ�տ��ת�ܶ�");
	    	createTotalCol(row0,row1,row2,MoneyTypeEnum.PREMONEY_VALUE+"_useAmount","Ԥ�տ�","Ԥ�տ�������");
	    	
    	}
    	int end =  tblMain.getColumnCount();
    	if(end>start){
    		tblMain.getHeadMergeManager().mergeBlock(0, start, 0, end-1,KDTMergeManager.FREE_MERGE);
    		if(coll.size()>1){
        		tblMain.getHeadMergeManager().mergeBlock(1, start, 1, end-4,KDTMergeManager.FREE_MERGE);
        		tblMain.getHeadMergeManager().mergeBlock(1, end-3, 2, end-1,KDTMergeManager.FREE_MERGE);
    		}else{
        		tblMain.getHeadMergeManager().mergeBlock(1, start, 1, end-1,KDTMergeManager.FREE_MERGE);
        		//
        		tblMain.getColumn(MoneyTypeEnum.PREMONEY_VALUE+"_revAmount").getStyleAttributes().setHided(true);
        		tblMain.getColumn(MoneyTypeEnum.PREMONEY_VALUE+"_settleAmount").getStyleAttributes().setHided(true);
        		tblMain.getColumn(MoneyTypeEnum.PREMONEY_VALUE+"_useAmount").getStyleAttributes().setHided(true);
    		}
    	}
	}
	
	
	/**
	 * ��ȡ�Կ������͵Ŀ������Ƽ���
	 * @param typeEnum
	 * @return
	 * @throws BOSException
	 */
	protected MoneyDefineCollection getATypeMoneyDefineCollection(MoneyTypeEnum typeEnum)throws BOSException{
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		view.setFilter(filter);
		
		view.setSelector(getMoneyDefineSelector());
		
		filter.getFilterItems().add(new FilterItemInfo("sysType", MoneySysTypeEnum.SALEHOUSESYS_VALUE));
		filter.getFilterItems().add(new FilterItemInfo("moneyType", typeEnum));		
		MoneyDefineCollection moneyColl = MoneyDefineFactory.getRemoteInstance().getMoneyDefineCollection(view);
		return moneyColl;
	}
	
	
	protected SelectorItemCollection getMoneyDefineSelector(){
		SelectorItemCollection si = new SelectorItemCollection();
			si.add(new SelectorItemInfo("id"));
			si.add(new SelectorItemInfo("name"));
		return si;
	}
	
	
	/**
	 * Ԥ�տ�   �� ĳ���������Ƶ�����
	 * @param row  
	 * @param preId  ��Ԫ�� Id ǰ׺
	 */
	protected void getPreMoneyNameList(IRow row,String preId){
		
		row.getCell(preId+"0").setValue("�տ���");
		
		appendSettleCell(row,preId,"1","2");
		appendBankCell(row,preId,"3","4");
		row.getCell(preId+5).setValue("��ת���");
		row.getCell(preId+6).setValue("��ת���");
		//return ;
	}
	
	/**
	 * һ��  ����   ĳ���������Ƶ�����
	 * @param row
	 * @param preId ��Ԫ�� Id ǰ׺
	 */
	protected void getSimpleNameList(IRow row,String preId){
		
		row.getCell(preId+"0").setValue("�տ���");
		
		appendSettleCell(row,preId,"1","2");
		appendBankCell(row,preId,"3","4");

	}
	
	//����һ��ͨ��,ǰ����. 
	protected void createCol(IRow row0,IRow row1, String id,String type,String name){
		row0.getCell(id).setValue(type);
		row1.getCell(id).setValue(name);
	}
	
	/**
	 * ����һ�кϼ���
	 * @param row0
	 * @param row1
	 * @param row2
	 * @param id
	 * @param type
	 * @param name
	 */
	protected void createTotalCol(IRow row0,IRow row1,IRow row2,String id,String type,String name){
		IColumn col = this.tblMain.addColumn();
		col.setKey(id);
		row0.getCell(id).setValue(type);
		row1.getCell(id).setValue(name);
		row2.getCell(id).setValue(name);
	}
	
	/**
	 * 
	 * @param row
	 * @param preId
	 * @param endstr1   ������������µ�һ�� �����˻�/�˺�
	 * @param endstr2   
	 */
	protected void appendBankCell(IRow row,String preId,String endstr1,String endstr2){
		row.getCell(preId+endstr1).setValue("�տ������˻�");
		row.getCell(preId+endstr2).setValue("�տ������˺�");
		
		//return row;
	}
	
	/**
	 * 
	 * @param row
	 * @param preId
	 * @param endstr1   ������������µ�һ�����
	 * @param endstr2   
	 */
	protected void appendSettleCell(IRow row,String preId,String endstr1,String endstr2){
		row.getCell(preId+endstr1).setValue("���㷽ʽ");
		row.getCell(preId+endstr2).setValue("������");
		
	//	return row;
	}

	/**
	 * �洢 ���㷽ʽ+�����˻�
	 * �����Ƚ���
	 * @author jinqiu_tan
	 *
	 */
	private class SettleTypeBank{
		private String settlementType;
		private String bankNumber;
		
		public SettleTypeBank(String settlementType,String bankNumber) {
			this.settlementType =settlementType;
			this.bankNumber= bankNumber;
		}
		
		public boolean equals(SettleTypeBank obj) {
			if(obj==null){
				return false;
			}
			
			boolean bankboo =( this.bankNumber==null && obj.getBankNumber()==null );
			boolean settboo =( this.settlementType==null && obj.getSettlementType()==null );
			
			if(bankboo && settboo){
				return true;
			}
			
			boolean bankboolean =false;
			boolean settboolean =false;
			if(this.bankNumber!=null){
				bankboolean = this.bankNumber.equals(obj.getBankNumber());
			}
			if(this.settlementType!=null){
				settboolean = this.settlementType.equals(obj.getSettlementType());
			}
			return bankboolean && settboolean;
		}

		public String getSettlementType() {
			return settlementType;
		}

		public String getBankNumber() {
			return bankNumber;
		}

	}
	
/**
 * 
 * @author jinqiu_tan
 *
 */
	private class RowMoneyDefineObj
	{
		private BigDecimal amt;
		private String settlementType;
		private BigDecimal settlementAmount;
		private String bankNumber;
		private String bankName;
		
		private BigDecimal settleOutAmount;
		
		private RevBillTypeEnum billType;
		
		public RowMoneyDefineObj(BigDecimal amt,String settlementType,BigDecimal settlementAmount,String bankNumber,String bankName,RevBillTypeEnum billType){
			this.amt =amt;
			this.settlementType =settlementType;
			this.settlementAmount =settlementAmount;
			this.bankNumber =bankNumber;
			this.bankName =bankName; 
			this.billType =billType; 
		}
		
		public RowMoneyDefineObj(BigDecimal amt, String settlementType,
				BigDecimal settlementAmount, String bankNumber,
				String bankName,BigDecimal settleOutAmount, RevBillTypeEnum billType) {
			this.amt =amt;
			this.settlementType =settlementType;
			this.settlementAmount =settlementAmount;
			this.bankNumber =bankNumber;
			this.bankName =bankName; 
			this.billType =billType; 
			this.settleOutAmount = 	settleOutAmount;
		}
		
		
		
		public BigDecimal getSettleOutAmount() {
			return settleOutAmount;
		}

		public void setSettleOutAmount(BigDecimal settleOutAmount) {
			this.settleOutAmount = settleOutAmount;
		}

		public RevBillTypeEnum getBillType() {
			return billType;
		}

		public void setBillType(RevBillTypeEnum billType) {
			this.billType = billType;
		}

		public BigDecimal getAmt() {
			return amt;
		}
		public void setAmt(BigDecimal amt) {
			this.amt = amt;
		}
		public String getSettlementType() {
			return settlementType;
		}
		public void setSettlementType(String settlementType) {
			this.settlementType = settlementType;
		}
		public BigDecimal getSettlementAmount() {
			return settlementAmount;
		}
		public void setSettlementAmount(BigDecimal settlementAmount) {
			this.settlementAmount = settlementAmount;
		}
		public String getBankNumber() {
			return bankNumber;
		}
		public void setBankNumber(String bankNumber) {
			this.bankNumber = bankNumber;
		}
		public String getBankName() {
			return bankName;
		}
		public void setBankName(String bankName) {
			this.bankName = bankName;
		}
		public void addAmt(BigDecimal amt) {
			if(amt==null){
				return ;
			}
			
			if(this.amt==null){
				amt =FDCHelper.ZERO;
			}
			this.amt.add(amt);
		}
		public void subAmt(BigDecimal amt) {
			if(amt==null){
				return ;
			}
			if(this.amt==null){
				amt =FDCHelper.ZERO;
			}
			this.amt.subtract(amt);
		}
		
		public boolean checkRowMoneyDefineObj(String settlementType,String bankNumber){
			if((this.settlementType!=null &&settlementType!=null && this.settlementType.equals(settlementType)) || (this.settlementType==null && settlementType==null)){
				
				if((this.bankNumber!=null &&bankNumber!=null && this.bankNumber.equals(bankNumber)) || (this.bankNumber==null && bankNumber==null)){
					return true;
				}
			}
			return false;
		}
		
		private void init()
		{
			if(amt==null){
				amt=FDCHelper.ZERO;
			}
			if(settlementAmount==null){
				settlementAmount=FDCHelper.ZERO;
			}
			if(settleOutAmount==null){
				settleOutAmount=FDCHelper.ZERO;
			}
		}
		
		public void updateRowMoneyDefineObj(BigDecimal amt ,BigDecimal settlementAmount ,RevBillTypeEnum billType){
			init();
			if(RevBillTypeEnum.gathering.equals(billType)){
				this.amt = this.amt.add(amt);
				this.settlementAmount = this.settlementAmount.add(settlementAmount);  ///�տ�ϼƵ�����ϼ�
			}else if(RevBillTypeEnum.refundment.equals(billType)){
				this.amt = this.amt.add(amt);
				this.settlementAmount = this.settlementAmount.add(settlementAmount);  ///�տ�ϼƵ�����ϼ�
			}else if(RevBillTypeEnum.transfer.equals(billType)){
				this.amt =  this.amt.add(amt);
				this.settlementAmount = this.settlementAmount.add(settlementAmount);  ///�տ�ϼƵ�����ϼ�
			}
			//
			
		}
		
		public void updatesettleOutAmount(BigDecimal settleOutAmount  ,RevBillTypeEnum billType){
			init();
			// if(RevBillTypeEnum.transfer.equals(billType)){
				this.settleOutAmount =  this.settleOutAmount.add(settleOutAmount);
				//	}
		}
	}
	
	//,String settlementTypeId,String bankNumberId,String
	private void readRowMap(Map map,IRowSet rows )throws SQLException {
		if(map!=null){
			
			boolean exist = false;
			String settle = rows.getString("settlementTypeID"); //
			String bank = rows.getString("RevAccountBankID");
			
			String settlementTypeName = rows.getString("settlementTypeName");  //���㷽ʽ
			String revBillType = rows.getString("revBillType");  //�տ����
			String accountbankName = rows.getString("accountbankName"); //�����˻�
			String accountbankNumber = rows.getString("accountbankNumber"); //�����˺�
			BigDecimal revAmount = rows.getBigDecimal("revAmount"); //�տ���
			
			String transfer = rows.getString("transferID"); //�տ���   ת��ID 
			
			
			if(revBillType==null){
				return;
			}
			int size = map.size();
			Iterator it= map.keySet().iterator();
			while(it.hasNext()){
				String  index = (String)it.next();
				RowMoneyDefineObj obj = (RowMoneyDefineObj)map.get(index);
				if(obj.checkRowMoneyDefineObj(settlementTypeName, accountbankNumber)){ 
					if(transfer!=null && !"".equals(transfer) // && RevBillTypeEnum.transfer.equals(RevBillTypeEnum.getEnum(revBillType))
							){  ///ת��, ת��
						obj.updatesettleOutAmount(revAmount, RevBillTypeEnum.getEnum(revBillType));
					}else{ 
						obj.updateRowMoneyDefineObj(revAmount, revAmount, RevBillTypeEnum.getEnum(revBillType));
					}
					exist = true;
					break;
				}
			}
			if(!exist){
				if (transfer != null&& !"".equals(transfer)
						//&& RevBillTypeEnum.transfer.equals(RevBillTypeEnum.getEnum(revBillType))
						) { // /ת��, ת��
					map.put(size + "", new RowMoneyDefineObj(FDCHelper.ZERO,settlementTypeName, FDCHelper.ZERO, accountbankNumber,
							accountbankName,revAmount, RevBillTypeEnum.getEnum(revBillType)));
				}else{
					map.put(size + "", new RowMoneyDefineObj(revAmount,settlementTypeName, revAmount, accountbankNumber,
							accountbankName, RevBillTypeEnum.getEnum(revBillType)));
				}
				size++;
			}
			
		}
	}
	
	
	
	/**
	 * ��ѯ���е�¥��id
	 * 
	 * @param treeNode
	 */
	private void getAllBuildingIds(TreeNode treeNode) {
		if(allBuildingIds==null){
			allBuildingIds = new HashSet();
		}
		DefaultKingdeeTreeNode thisNode = (DefaultKingdeeTreeNode) treeNode;
		if (thisNode.getUserObject() instanceof BuildingInfo) {
			BuildingInfo building = (BuildingInfo) thisNode.getUserObject();
			allBuildingIds.add(building.getId().toString());
			// allBuildingIds += "," + building.getId().toString();
		}

		if (!treeNode.isLeaf()) {
			int childCount = treeNode.getChildCount();
			while (childCount > 0) {
				getAllBuildingIds(treeNode.getChildAt(childCount - 1));
				childCount--;
			}
		}
	}
	
	private void getAllSellProject(TreeNode treeNode) {
//		allSellProjectIds = null;
		if(allSellProjectIds==null){
			allSellProjectIds = new HashSet();
		}
		DefaultKingdeeTreeNode thisNode = (DefaultKingdeeTreeNode) treeNode;
		if (thisNode.getUserObject() instanceof SellProjectInfo) {
			SellProjectInfo sellproject = (SellProjectInfo) thisNode.getUserObject();
			allSellProjectIds.add(sellproject.getId().toString());
			// allBuildingIds += "," + building.getId().toString();
		}

		if (!treeNode.isLeaf()) {
			int childCount = treeNode.getChildCount();
			while (childCount > 0) {
				getAllSellProject(treeNode.getChildAt(childCount - 1));
				childCount--;
			}
		}
	}
	
	
}