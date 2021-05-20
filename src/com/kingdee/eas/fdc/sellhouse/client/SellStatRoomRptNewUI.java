/**
 * output package name
 */
package com.kingdee.eas.fdc.sellhouse.client;

import java.awt.event.ActionEvent;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.swing.event.TreeSelectionEvent;
import javax.swing.tree.TreeNode;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTDataRequestManager;
import com.kingdee.bos.ctrl.kdf.table.KDTGroupManager;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectManager;
import com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent;
import com.kingdee.bos.ctrl.kdf.table.event.KDTSelectEvent;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.UIException;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.eas.base.commonquery.client.CommonQueryDialog;
import com.kingdee.eas.base.commonquery.client.CustomerQueryPanel;
import com.kingdee.eas.basedata.org.FullOrgUnitInfo;
import com.kingdee.eas.basedata.org.OrgStructureInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basedata.FDCCustomerParams;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.MoneySysTypeEnum;
import com.kingdee.eas.fdc.basedata.client.FDCClientHelper;
import com.kingdee.eas.fdc.sellhouse.HousePropertyEnum;
import com.kingdee.eas.fdc.sellhouse.PurchaseFactory;
import com.kingdee.eas.fdc.sellhouse.PurchaseInfo;
import com.kingdee.eas.fdc.sellhouse.PurchaseRoomAttachmentEntryCollection;
import com.kingdee.eas.fdc.sellhouse.PurchaseRoomAttachmentEntryInfo;
import com.kingdee.eas.fdc.sellhouse.RoomBookStateEnum;
import com.kingdee.eas.fdc.sellhouse.RoomCompensateStateEnum;
import com.kingdee.eas.fdc.sellhouse.RoomJoinStateEnum;
import com.kingdee.eas.fdc.sellhouse.RoomLoanStateEnum;
import com.kingdee.eas.fdc.sellhouse.SellProjectInfo;
import com.kingdee.eas.fdc.sellhouse.SellStatRoomRptNewFacadeFactory;
import com.kingdee.eas.fdc.sellhouse.SellTypeEnum;
import com.kingdee.eas.framework.ITreeBase;

/**
 * output class name
 */
public class SellStatRoomRptNewUI extends AbstractSellStatRoomRptNewUI
{
    private static final Logger logger = CoreUIObject.getLogger(SellStatRoomRptNewUI.class);
    
    private CommonQueryDialog commonQueryDialog = null;
    private SellStatRoomRptFilterUI userFilter = null;
    private Map rowMap = new HashMap();
    
    //��һ�μ���
	private boolean isFirstLoad = true;
	
    public SellStatRoomRptNewUI() throws Exception
    {
        super();
    }
    public void storeFields()
    {
        super.storeFields();
    }
    
    protected void treeMain_valueChanged(TreeSelectionEvent e) throws Exception
	{
		if(!isFirstLoad)
			this.execQuery();
	}

	protected void checkTableParsed()
	{
		tblMain.checkParsed();
	}
	
	public void onShow() throws Exception {
//		super.onShow();
	}
	public void handUIException(Throwable exc) {
		if(exc instanceof BOSException&&exc.getMessage().startsWith("Can't found propertyUnit:")){
			logger.error(exc);
		}
		else
			super.handUIException(exc);
	}
	public void onLoad() throws Exception
	{
		this.tblMain.checkParsed();
		
		FDCClientHelper.addSqlMenu(this, this.menuFile);
		super.onLoad();
		tblMain.getSelectManager().setSelectMode(KDTSelectManager.MULTIPLE_CELL_SELECT);
		
		isFirstLoad = false;
//		initTable();
		this.actionLocate.setVisible(false);
		this.menuEdit.setVisible(false);
		this.menuBiz.setVisible(false);	
		
		/////����Ǵ�����ͳ�Ʊ���
		Object o = this.getUIContext().get("isFirstLoad");
		if(o!=null){
			this.treeView.setVisible(false);
			execQuery();
		}
		
	}
	
	protected void initWorkButton() {
		super.initWorkButton();
		this.actionAddNew.setVisible(false);
		this.actionEdit.setVisible(false);
		this.actionAddNew.setEnabled(false);
		this.actionEdit.setEnabled(false);
		this.actionRemove.setEnabled(false);
		this.actionView.setEnabled(false);
		this.actionAddNew.setVisible(false);
		this.actionEdit.setVisible(false);
		this.actionRemove.setVisible(false);
		this.actionView.setVisible(false);
		this.menuEdit.setVisible(false);
		this.actionLocate.setVisible(false);
	}
    
    protected void initTree() throws Exception
	{
		this.treeMain.setModel(SHEHelper.getUnitTree(this.actionOnLoad,MoneySysTypeEnum.SalehouseSys,
				SysContext.getSysContext().getCurrentSaleUnit().isIsBizUnit()));
		
		this.treeMain.expandAllNodes(true, (TreeNode) this.treeMain.getModel().getRoot());
	}

	protected CommonQueryDialog initCommonQueryDialog()
	{
		if (commonQueryDialog != null)
		{
			return commonQueryDialog;
		}
		commonQueryDialog = super.initCommonQueryDialog();
		try
		{
			commonQueryDialog.addUserPanel(this.getFilterUI());
		} catch (Exception e)
		{
			super.handUIException(e);
		}
		commonQueryDialog.setShowSorter(false);
		commonQueryDialog.setShowFilter(false);
		return commonQueryDialog;
	}

	private CustomerQueryPanel getFilterUI() throws Exception
	{
		if (this.userFilter == null)
		{
			this.userFilter = new SellStatRoomRptFilterUI(this, this.actionOnLoad);
		}
		return this.userFilter;
	}
    
    public void actionRefresh_actionPerformed(ActionEvent e) throws Exception {
		execQuery();
	}
    
//    private void initTable()
//    {
//    	String formatString = "yyyy-MM-dd";
//		this.tblMain.getColumn("toPrePurchaseDate").getStyleAttributes().setNumberFormat(formatString);
//		this.tblMain.getColumn("toPurchaseDate").getStyleAttributes().setNumberFormat(formatString);
////		this.tblMain.getColumn("toPrePurchaseDate").getStyleAttributes().setHided(true);
//    }
    
    protected void execQuery()
	{   	
		tblMain.removeRows();
		tblMain.getDataRequestManager().setDataRequestMode(KDTDataRequestManager.REAL_MODE);
		
		try {
			//��ȡ����Ĺ��˲�����
			Map paramMap = getRptParamMap();
			//��ȡ������������
			Map rptDataMap = SellStatRoomRptNewFacadeFactory.getRemoteInstance().getSellStatRoomData(paramMap);
			//���ݻ�ȡ�����������
			fillRptDataToTable(rptDataMap);			
		} catch (Exception e1) {
			this.handleException(e1);
		}
		
		this.tblMain.getColumn(1).setGroup(true);
		this.tblMain.getGroupManager().setTotalize(true);
		this.tblMain.getColumn(1).setStat(true);
		
		IRow row0 = (IRow)tblMain.getGroupManager().getStatRowTemplate(-1);
		row0.getStyleAttributes().setBackground(CommerceHelper.BK_COLOR_MUST);
		row0.getCell(0).setValue("�ϼ�:");
		row0.getCell(14).setExpressions(KDTGroupManager.STAT_AVERAGE);
		row0.getCell(15).setExpressions(KDTGroupManager.STAT_AVERAGE);
		row0.getCell(17).setExpressions(KDTGroupManager.STAT_SUM);
		row0.getCell(18).setExpressions(KDTGroupManager.STAT_AVERAGE);
		row0.getCell(20).setExpressions(KDTGroupManager.STAT_AVERAGE);
		row0.getCell(21).setExpressions(KDTGroupManager.STAT_SUM);
		row0.getCell(22).setExpressions(KDTGroupManager.STAT_AVERAGE);
		row0.getCell(23).setExpressions(KDTGroupManager.STAT_AVERAGE);
		row0.getCell(24).setExpressions(KDTGroupManager.STAT_SUM);
		row0.getCell(26).setExpressions(KDTGroupManager.STAT_SUM);
		
		for(int i=28;i<36;i++)row0.getCell(i).setExpressions(KDTGroupManager.STAT_SUM);
		for(int i=47;i<53;i++)row0.getCell(i).setExpressions(KDTGroupManager.STAT_SUM);
		
		row0 = (IRow)tblMain.getGroupManager().getStatRowTemplate(1);
		row0.getStyleAttributes().setBackground(CommerceHelper.BK_COLOR_MUST);
		row0.getCell(2).setValue("¥��С�ƣ�");
	
		row0.getCell(14).setExpressions(KDTGroupManager.STAT_AVERAGE);
		row0.getCell(15).setExpressions(KDTGroupManager.STAT_AVERAGE);
		row0.getCell(17).setExpressions(KDTGroupManager.STAT_SUM);
		row0.getCell(18).setExpressions(KDTGroupManager.STAT_AVERAGE);
		row0.getCell(20).setExpressions(KDTGroupManager.STAT_AVERAGE);
		row0.getCell(21).setExpressions(KDTGroupManager.STAT_SUM);
		row0.getCell(22).setExpressions(KDTGroupManager.STAT_AVERAGE);
		row0.getCell(23).setExpressions(KDTGroupManager.STAT_AVERAGE);
		row0.getCell(24).setExpressions(KDTGroupManager.STAT_SUM);
		row0.getCell(26).setExpressions(KDTGroupManager.STAT_SUM);
		for(int i=28;i<36;i++)row0.getCell(i).setExpressions(KDTGroupManager.STAT_SUM);
		for(int i=47;i<53;i++)row0.getCell(i).setExpressions(KDTGroupManager.STAT_SUM);
	
		//���µ������ֲ�ˢ��
		tblMain.reLayoutAndPaint();
		this.tblMain.getGroupManager().group();
	}
    
    protected void fillRptDataToTable(Map rptDataMap)
	{
    	Map baseMap = (Map)rptDataMap.get("baseData");
    	if(baseMap==null) return;
    	List baseList = (List)baseMap.get("baseList"); 
    	if(baseList!=null)
    	{
    		fillTable(baseList);
    	}   	    	  	
    	Map statlistMap = (Map)rptDataMap.get("statList");
    	List statList = (List)statlistMap.get("statelistMap");
    	if(statList!=null)
    	{
    		fillStateTable(statList);
    	}
    	
    	Map compentMap = (Map)rptDataMap.get("compent");
    	if(compentMap==null) return;
    	List compentList = (List)compentMap.get("compentList");
    	if(compentList!=null)
    	{
    		fillCompentTable(compentList);
    	}
    	Map huikuanMap = (Map)rptDataMap.get("huiKuan"); 
    	List huikuanList = (List)huikuanMap.get("huikuanMap");
    	if(huikuanList!=null)
    	{
    		fillHuiKuanTable(huikuanList);
    	}  
    	
	} 
    
    //�����
    protected void fillCompentTable(List compentList)
	{
    	for(int i=0;i<compentList.size();i++)
    	{
    		Object[] obj = (Object[])compentList.get(i);
    		String roomID = (String)obj[0];
    		if(rowMap.get(roomID)!=null)
    		{
    			IRow row = (IRow)rowMap.get(roomID);
    			row.getCell("areaCompensateAmount").setValue(obj[1]);
    		}
    	}
	}

    //�Ϲ���״̬��ǩԼ����
    protected void fillStateTable(List statList)
	{
    	for(int i=0;i<statList.size();i++)
    	{
    		Object[] obj = (Object[])statList.get(i);
    		String roomID = (String)obj[0];
    		String purID = (String)obj[3];   
//    		String str  = purID+roomID;
    		if(rowMap.get(roomID)!=null)
    		{
    			IRow row = (IRow)rowMap.get(roomID);
    			row.getCell("id").setValue(roomID);
    			row.getCell("sellState").setValue(obj[1]);
    			row.getCell("toSignDate").setValue(obj[2]);
//    			if(rowMap.get(str)!=null)
//    			{
    				SelectorItemCollection sel = new SelectorItemCollection();
//    				sel.add("attachmentEntries.*");
//    				sel.add("attachmentEntries.attachmentEntry.*");
    				sel.add("attachmentEntries.attachmentEntry.room.id");
    				try {
						PurchaseInfo purchase = PurchaseFactory.getRemoteInstance().getPurchaseInfo(new ObjectUuidPK(purID), sel);
						PurchaseRoomAttachmentEntryCollection purAttColl = purchase.getAttachmentEntries();
						if(purAttColl.size()>0)
						{
							for(int k=0;k<purAttColl.size();k++)
							{
								PurchaseRoomAttachmentEntryInfo a = purAttColl.get(k);
								String roomID2 = a.getAttachmentEntry().getRoom().getId().toString();
								String str  = purID+roomID2;
								if(rowMap.get(str)!=null)
								{
									IRow row2 = (IRow)rowMap.get(str);
					        		row2.getCell("sellState").setValue(obj[1]);
					        		row2.getCell("toSignDate").setValue(obj[2]);
								}								
							}
							
						}
					} catch (EASBizException e) {
						e.printStackTrace();
					} catch (BOSException e) {
						e.printStackTrace();
					}  				
//    			}
    		}
    	}
	}
    //��ͬ�ؿ����Ӧ�ջؿ�ͺ�ͬǷ��
    protected void fillHuiKuanTable(List huikuanList)
	{
    	for(int i=0;i<huikuanList.size();i++)
    	{
    		Object[] obj = (Object[])huikuanList.get(i);
    		String roomID = (String)obj[0];
    		if(rowMap.get(roomID)!=null)
    		{
    			IRow row = (IRow)rowMap.get(roomID);
    			row.getCell("contactComeBackCount").setValue(obj[1]);
    			row.getCell("otherShouldBackCount").setValue(obj[2]);
//    			row.getCell("areaCompensateAmount").setValue(obj[3]);
    			BigDecimal contractAmount = new BigDecimal(0);
    			if(row.getCell("contractTotalAmount").getValue()!=null)
    			{
    				contractAmount = (BigDecimal)row.getCell("contractTotalAmount").getValue();
    			}
    			BigDecimal appCompentAmount = new BigDecimal(0);
    			BigDecimal compentAmount = new BigDecimal(0);
    			if(row.getCell("appCompentAmount").getValue()!=null)
    			{
    				appCompentAmount = (BigDecimal)row.getCell("appCompentAmount").getValue();
    			}
    			if(row.getCell("areaCompensateAmount").getValue()!=null)
    			{
    				compentAmount = (BigDecimal)row.getCell("areaCompensateAmount").getValue();
    			}
    			BigDecimal compentAmountQiankuan = appCompentAmount.subtract(compentAmount);
    			if(obj[1]!=null)
    			{
    				BigDecimal contactComeBackCount = (BigDecimal)obj[1];
    				row.getCell("arrearageCount").setValue(contractAmount.subtract(contactComeBackCount).add(compentAmountQiankuan).divide(new BigDecimal(1),2,BigDecimal.ROUND_HALF_UP));
    			}else
    			{
    				BigDecimal contactComeBackCount = new BigDecimal(0);
    				row.getCell("arrearageCount").setValue(contractAmount.subtract(contactComeBackCount).add(compentAmountQiankuan).divide(new BigDecimal(1),2,BigDecimal.ROUND_HALF_UP));
    			}
    		}
    	}
	}
    
    //�Ϲ�����ϸ
    protected void fillTable(List baseList)
	{
    	for(int i=0;i<baseList.size();i++)
    	{
    		IRow row = this.tblMain.addRow();   		
    		Object[] obj = (Object[])baseList.get(i);
    		String roomID = (String)obj[0];
    		String purID = (String)obj[51];
    		if(HousePropertyEnum.ATTACHMENT_VALUE.equals((String)obj[40]))
    		{
    			String str = purID+roomID;
    			rowMap.put(str, row);
    		}else
    		{
    			rowMap.put(roomID, row);
    		}
    		
    		row.getCell("projectName").setValue(obj[1]);
    		row.getCell("buildingName").setValue(obj[2]);
    		row.getCell("unitName").setValue(obj[3]);
    		row.getCell("number").setValue(obj[4]);
    		row.getCell("roomNo").setValue(obj[5]);
//    		row.getCell("customerNames").setValue(obj[6]);
    		String customerNames = (String)obj[6];
    		if(customerNames.split(";").length!=1)
    		{
    			String customName = customerNames.substring(0, customerNames.lastIndexOf(";"));
        		row.getCell("customerNames").setValue(customName);
    		}else
    		{
    			row.getCell("customerNames").setValue(obj[6]);
    		}
    		
//    		String phones = (String)obj[7];
//    		
//    		String[] str = phones.split(",");
//    		StringBuffer aa = new StringBuffer();
//    		for(int m=0;m<str.length;m++)
//    		{
//    			String bb = str[m];
//    			if(bb.indexOf(";")==-1)
//    			{
//    				aa.append(bb);
//    			}else
//    			{
//    				String cc = bb.substring(bb.indexOf(";"),bb.length());
//    				aa.append(cc);
//    			}
//    		}
//    		String dd = aa.toString();
//    		String phone = dd.substring(0, dd.lastIndexOf(";"));
//    		row.getCell("customerPhones").setValue(phone);
    		//�绰���벻�淶,��������
    		row.getCell("customerPhones").setValue(obj[7]);
    		row.getCell("payType").setValue(obj[8]);
    		row.getCell("salesmanName").setValue(obj[9]);
    		row.getCell("toPrePurchaseDate").setValue(obj[10]);
    		row.getCell("toPurchaseDate").setValue(obj[11]);
//    		row.getCell("toSignDate").setValue(obj[12]);
    		row.getCell("baseBuildingPrice").setValue(obj[13]);
    		row.getCell("baseRoomPrice").setValue(obj[14]);
    		row.getCell("isBasePriceAudited").setValue(obj[15]);
    		row.getCell("buildPrice").setValue(obj[16]);
    		row.getCell("roomPrice").setValue(obj[17]);
    		row.getCell("standardTotalAmount").setValue(obj[18]);
    		      		
    		row.getCell("contractTotalAmount").setValue(obj[22]);
    		if(HousePropertyEnum.ATTACHMENT_VALUE.equals((String)obj[40]))
    		{
    			row.getCell("finalAgio").setValue(null);
    			row.getCell("dealTotalAmount").setValue(obj[22]); 
    			if(obj[25]!=null && obj[24]!=null)
        		{
        			BigDecimal dealTotalAmount = (BigDecimal)obj[22];
        			BigDecimal buildingArea = (BigDecimal)obj[24];       			
        			BigDecimal roomArea = (BigDecimal)obj[25];
        			row.getCell("dealBuildPrice").setValue(dealTotalAmount.divide(buildingArea, 2, BigDecimal.ROUND_HALF_UP));
        			row.getCell("dealRoomPrice").setValue(dealTotalAmount.divide(roomArea, 2, BigDecimal.ROUND_HALF_UP));
        		}  			
    		}else
    		{
    			row.getCell("dealBuildPrice").setValue(obj[19]);
        		row.getCell("dealRoomPrice").setValue(obj[20]);
        		row.getCell("dealTotalAmount").setValue(obj[21]); 
    			if(obj[18]!=null && obj[21]!=null)
        		{
    				//�����ۿۣ��ɽ��ܼ�/��׼�ܼ� ��׼�ܼ۰����������۲��Ԥ�۵Ĳ����Ҫ�ܣ�
        			BigDecimal standardTotalAmount = (BigDecimal)obj[18];
        			BigDecimal compent = (BigDecimal)obj[52];
        			compent = compent ==null?new BigDecimal(0):compent;
        			standardTotalAmount = standardTotalAmount.add(compent);
        			BigDecimal dealTotalAmount = (BigDecimal)obj[21];
        			/**
        			 * �˴���standardTotalAmount���û���ж��ڵĻ����������standardTotalAmount=0�Ļ�
        			 * ��divide����� update by renliang 2011-1-15
        			 */
        			if(standardTotalAmount==null || standardTotalAmount.compareTo(FDCHelper.ZERO)==0){
        				standardTotalAmount = FDCHelper.ONE;
        			}
        		
        			row.getCell("finalAgio").setValue(dealTotalAmount.multiply(new BigDecimal(100)).divide(standardTotalAmount, 2, BigDecimal.ROUND_HALF_UP));
        		}
    		}   		
    		row.getCell("sellType").setValue(SellTypeEnum.getEnum((String)obj[23]));
    		
    		row.getCell("isCalByRoomArea").setValue(obj[43]);
    		
    		row.getCell("buildingArea").setValue(obj[24]);
    		if(obj[13]!=null && obj[24]!=null)
    		{
    			BigDecimal baseBuildingPrice = (BigDecimal)obj[13];
    			BigDecimal buildingArea = (BigDecimal)obj[24];
    			row.getCell("basePrice").setValue(baseBuildingPrice.multiply(buildingArea).divide(new BigDecimal(1), 2, BigDecimal.ROUND_HALF_UP));
    		}
    		row.getCell("roomArea").setValue(obj[25]);
    		row.getCell("actualBuildingArea").setValue(obj[26]);
    		row.getCell("actualRoomArea").setValue(obj[27]);
    		row.getCell("buildingPropertyName").setValue(obj[28]);
    		row.getCell("productType").setValue(obj[29]);
    		row.getCell("directionName").setValue(obj[30]);
    		row.getCell("roomModelName").setValue(obj[31]);
    		row.getCell("roomModelType").setValue(obj[44]);
    		row.getCell("sightName").setValue(obj[32]);
    		row.getCell("apportionArea").setValue(obj[33]);
    		row.getCell("balconyArea").setValue(obj[34]);
    		row.getCell("guardenArea").setValue(obj[35]);
    		row.getCell("carbarnArea").setValue(obj[36]);
    		row.getCell("useArea").setValue(obj[37]);
    		row.getCell("flatArea").setValue(obj[38]);
    		row.getCell("serialNumber").setValue(obj[39]);
    		row.getCell("houseProperty").setValue(HousePropertyEnum.getEnum((String)obj[40]));
    		row.getCell("roomForm").setValue(obj[41]);
    		row.getCell("productDetial").setValue(obj[42]);
    		
//    		row.getCell("sellState").setValue(obj[45]);
    		row.getCell("roomJoinState").setValue(RoomJoinStateEnum.getEnum((String)obj[46]));
    		row.getCell("roomBookState").setValue(RoomBookStateEnum.getEnum((String)obj[47]));
    		row.getCell("roomLoanState").setValue(RoomLoanStateEnum.getEnum((String)obj[48]));
    		row.getCell("roomCompensateState").setValue(RoomCompensateStateEnum.getEnum((String)obj[49]));
//    		row.getCell("orderDate").setValue(obj[50]);
    		row.getCell("appCompentAmount").setValue(obj[52]);
    		
    		//new add subarea by renliang at 2011-1-12
    		row.getCell("proArea").setValue(obj[54]);
    		row.getCell("fitStd").setValue(obj[55]);
    	}
	}
    
    protected Map getRptParamMap() throws Exception
	{
		//��ȡ��ѡ�Ľڵ�
		DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeMain.getLastSelectedPathComponent();
		Set childNodeProjectIdSet = new HashSet();
		if(node == null || node.getUserObject() == null)
		{
			node = new DefaultKingdeeTreeNode();
			node.setUserObject(SysContext.getSysContext().getCurrentOrgUnit().castToFullOrgUnitInfo());
		}
		if(node.getUserObject() instanceof OrgStructureInfo)
		{
			OrgStructureInfo orgStrInfo = (OrgStructureInfo)node.getUserObject();
			node.setUserObject(orgStrInfo.getUnit());
		}
		if(node.getUserObject() instanceof FullOrgUnitInfo){
			//�ݹ������֯�ڵ��µ���Ŀ�ڵ�,��ȡ��Ŀid����
			Map idMap = FDCTreeHelper.getAllObjectIdMap(node, "SellProject");
			if(idMap!=null && idMap.size()>0)
			{
				childNodeProjectIdSet = new HashSet(idMap.keySet());
			}
		}
		
		//������ѡ�Ľڵ��ȡ��ص�����
		 /**
	     * paramMap
	     * ������
	     * selectNode        = ��ѡ���Ľڵ㣬���û��ѡ������Ϊ��ǰ��֯
	     * IncludeAttachment = ��������������Ϊ���ǰ�����Ϊ��ʱ������
	     * BuildArea         = ��ʾ������ͣ�Ϊ���ǽ��������Ϊ��ʱΪ�������
	     * PreArea           = ��ʾ������ͣ�Ϊ����Ԥ�������Ϊ��ʱΪʵ�����
	     * BeginDate         = ��ʼ����
	     * EndDate           = ��������
	     * IncludeOrder      = ����Ԥ��ҵ�� Ϊ���ǰ�����Ϊ��ʱ������
	     * IsAuditDate       = ���Ǹ�ʱ��Ϊ��׼��Ϊ��ʱ����������Ϊ��׼��Ϊ��ʱ�Ѷ�Ӧ������Ϊ��׼��
	     */
		Map paramMap = new HashMap();
		
		///����Ǵ�����ͳ�Ʊ���
		Object o = this.getUIContext().get("FromSellStatTable");
		if(o!=null && (o instanceof Map)){ //ȡ����ͳ�Ʊ���˲���map
			paramMap = ((Map)o);
		}else{
			paramMap.put("selectNode", node);
			
			paramMap.put("IncludeAttachment", Boolean.valueOf(isIncludeAttachment()));
	//		paramMap.put("BuildArea", Boolean.valueOf(isBuildArea()));
	//		paramMap.put("PreArea", Boolean.valueOf(isPreArea()));
			paramMap.put("isShowAll", Boolean.valueOf(isShowAll()));
			paramMap.put("BeginDate", getBeginQueryDate());
			paramMap.put("EndDate", getEndQueryDate());
			paramMap.put("IncludeOrder", Boolean.valueOf(isIncludePreOrder()));
			paramMap.put("childNodeProjectIdSet", childNodeProjectIdSet);
			
		}
		paramMap.put("userInfo",SysContext.getSysContext().getCurrentUserInfo());
		
		return paramMap;
	}
    
/////////////////////////////////////////////////////////////////////////////////////////////////////
	//��ȡ���˽���Ĳ���
	/**
	 * ��������
	 * @return
	 * @throws Exception
	 */
	private Date getEndQueryDate() throws Exception
	{
		FDCCustomerParams para = new FDCCustomerParams(this.getFilterUI().getCustomerParams());
		return ((SellStatRoomRptFilterUI) this.getFilterUI()).getEndQueryDate(para);
	}
	/**
	 * ��ʼ����
	 * @return
	 * @throws Exception
	 */
	private Date getBeginQueryDate() throws Exception
	{
		FDCCustomerParams para = new FDCCustomerParams(this.getFilterUI().getCustomerParams());
		return ((SellStatRoomRptFilterUI) this.getFilterUI()).getBeginQueryDate(para);
	}
	
	protected void tblMain_tableClicked(KDTMouseEvent e) throws Exception {
	}
	protected void tblMain_tableSelectChanged(KDTSelectEvent e)
			throws Exception {
	}
	
	private boolean isIncludeAttachment() throws Exception
	{
		FDCCustomerParams param = new FDCCustomerParams(this.getFilterUI().getCustomerParams());
		return ((SellStatRoomRptFilterUI) this.getFilterUI()).isIncludeAttach(param);
	}
	private boolean isIncludePreOrder() throws Exception
	{
		FDCCustomerParams param = new FDCCustomerParams(this.getFilterUI().getCustomerParams());
		return ((SellStatRoomRptFilterUI)this.getFilterUI()).isPreIntoSale(param);
	}
	private boolean isShowAll() throws Exception
	{
		FDCCustomerParams param = new FDCCustomerParams(this.getFilterUI().getCustomerParams());
		return ((SellStatRoomRptFilterUI)this.getFilterUI()).isShowAll(param);
	}
	
	//��list����û����id����������صķ���
	protected void setActionState() {

	}
	
	protected String getKeyFieldName()
	{
		return "id";
	}
	
	protected boolean isIgnoreCUFilter()
	{
		return true;
	}
	
	protected boolean initDefaultFilter() {
		/////����Ǵ�����ͳ�Ʊ��� �Ͳ��������˴���
		Object o = this.getUIContext().get("isFirstLoad");
		boolean showUI=true;
		if(o!=null && (o instanceof Boolean)){
			showUI = ((Boolean)o).booleanValue();
		}
		return showUI;
	}
	
	protected ITreeBase getTreeInterface() throws Exception {
		return null;
	}
	
	/**
	 * ����id��ʾ����
	 * @param isShowAll 
	 * 
	 * @param filter
	 * @throws UIException 
	 */
	public static void showUI(Object rptUI, Object node, Date beginDate, Date endDate, boolean isPreIntoSale, boolean isIncludeAttach, boolean isShowAll) throws UIException {
		UIContext uiContext = new UIContext(rptUI);
		Map paramMap = new HashMap();
		/*FilterInfo filter = new FilterInfo();
		
		if(!isShowAll) {
			String dateParam = "toPurchaseDate";
			if(isPreIntoSale) 
				dateParam = "toSaleDate";
			if(beginDate!=null)				
				filter.getFilterItems().add(new FilterItemInfo(dateParam,beginDate,CompareType.GREATER_EQUALS));
			if(endDate!=null)
				filter.getFilterItems().add(new FilterItemInfo(dateParam, FDCDateHelper.getNextDay(endDate),CompareType.LESS));
		}
		
		if(isIncludeAttach)
			filter.getFilterItems().add(new FilterItemInfo("houseProperty", HousePropertyEnum.ATTACHMENT_VALUE,CompareType.NOTEQUALS));
		
		Set sellTypes = new HashSet();
			sellTypes.add(RoomSellStateEnum.PURCHASE_VALUE);
			sellTypes.add(RoomSellStateEnum.SIGN_VALUE);
			if(isPreIntoSale){
				sellTypes.add(RoomSellStateEnum.PREPURCHASE_VALUE);
			}
		filter.getFilterItems().add(new FilterItemInfo("sellState", sellTypes,	CompareType.INCLUDE));	
		if(buildingId!=null)
			filter.getFilterItems().add(new FilterItemInfo("building.id", buildingId));
		*/
		
		paramMap.put("selectNode", node);
		
		paramMap.put("IncludeAttachment", Boolean.valueOf(isIncludeAttach));
		paramMap.put("isShowAll", Boolean.valueOf(isShowAll));
		paramMap.put("BeginDate", beginDate);
		paramMap.put("EndDate", endDate);
		paramMap.put("IncludeOrder", Boolean.valueOf(isPreIntoSale));
		paramMap.put("userInfo",SysContext.getSysContext().getCurrentUserInfo());
		
		
		uiContext.put("FromSellStatTable", paramMap);
		uiContext.put("isFirstLoad", Boolean.valueOf(false));
		
		// ����UI������ʾ
		IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.NEWTAB).create(SellStatRoomRptNewUI.class.getName(), uiContext, null,	"VIEW");
		uiWindow.show();
	}
}