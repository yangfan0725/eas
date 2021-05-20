package com.kingdee.eas.fdc.market.client;

import java.awt.Color;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreeNode;

import com.kingdee.bos.ctrl.kdf.table.ICell;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTMergeManager;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.foot.KDTFootManager;
import com.kingdee.bos.ctrl.kdf.util.style.Styles.HorizontalAlignment;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.metadata.MetaDataPK;
import com.kingdee.bos.ui.face.ItemAction;
import com.kingdee.eas.base.permission.UserInfo;
import com.kingdee.eas.basedata.org.NewOrgUtils;
import com.kingdee.eas.basedata.org.OrgConstants;
import com.kingdee.eas.basedata.org.OrgStructureInfo;
import com.kingdee.eas.basedata.org.OrgUnitInfo;
import com.kingdee.eas.basedata.org.OrgViewType;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.fdc.aimcost.PlanIndexTypeEnum;
import com.kingdee.eas.fdc.basecrm.FDCOrgStructureCollection;
import com.kingdee.eas.fdc.basecrm.FDCOrgStructureFactory;
import com.kingdee.eas.fdc.basecrm.FDCOrgStructureInfo;
import com.kingdee.eas.fdc.basecrm.client.FDCSysContext;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.ProductTypeInfo;
import com.kingdee.eas.fdc.market.AreaSetInfo;
import com.kingdee.eas.fdc.sellhouse.MarketingUnitFactory;
import com.kingdee.eas.fdc.sellhouse.SellProjectCollection;
import com.kingdee.eas.fdc.sellhouse.SellProjectFactory;
import com.kingdee.eas.fdc.sellhouse.SellProjectInfo;
import com.kingdee.eas.fdc.sellhouse.client.CommerceHelper;
import com.kingdee.eas.util.client.EASResource;

public class MarketHelpForSec {

	private static String strDataFormat = "#,##0.00;-#,##0.00";
	
	public static TreeModel getSellProjectTree(ItemAction actionOnLoad) throws Exception{
		FDCSysContext fdcSysContext = FDCSysContext.getInstance();
		OrgUnitInfo saleOrgInfo = SysContext.getSysContext().getCurrentOrgUnit();
		UserInfo currUserInfo = SysContext.getSysContext().getCurrentUserInfo();
		boolean includeSonSellPorject = true;
		//��½��֯����¥��֯
		if(fdcSysContext.getOrgMap().containsKey(saleOrgInfo.getId().toString())){
			OrgStructureInfo rootOrgStruct = new OrgStructureInfo();
			rootOrgStruct.setUnit(saleOrgInfo.castToFullOrgUnitInfo());
			rootOrgStruct.setDisplayName(saleOrgInfo.getName());
			DefaultKingdeeTreeNode rootNode = new DefaultKingdeeTreeNode(rootOrgStruct);
			
			String curOrgLongNumber = SysContext.getSysContext().getCurrentOrgUnit().getLongNumber();
			Map orgIdNodeMap = new HashMap();  //��֯id��Ӧ�����ڵ�
			Map orgLongNumberNodeMap = new HashMap();  //��֯�������Ӧ�����ڵ�
			orgIdNodeMap.put(saleOrgInfo.getId().toString(), rootNode);
			orgLongNumberNodeMap.put(curOrgLongNumber, rootNode);	
			FDCOrgStructureCollection fdcOrgColl = FDCOrgStructureFactory.getRemoteInstance()
				.getFDCOrgStructureCollection("select *,unit.name where tree.id = '"+OrgConstants.SALE_VIEW_ID+"'" +
						" and  longNumber like '"+curOrgLongNumber+"!%' and sellHouseStrut = 1 and IsHis = 0 and isValid = 1 order by longNumber ");
		
			for(int i=0;i<fdcOrgColl.size();i++){
				FDCOrgStructureInfo thisFDCOrgInfo= fdcOrgColl.get(i);
				if(orgIdNodeMap.get(thisFDCOrgInfo.getUnit().getId().toString())!=null) continue;
				
				OrgStructureInfo curOrgInfo = new OrgStructureInfo();
				curOrgInfo.setUnit(thisFDCOrgInfo.getUnit());
				curOrgInfo.setDisplayName(thisFDCOrgInfo.getUnit().getName());
				DefaultKingdeeTreeNode sonNode = new DefaultKingdeeTreeNode(curOrgInfo);
				
				DefaultKingdeeTreeNode parentNode = rootNode;	//Ѱ��Ҫ�ҵĸ���¥�ڵ㣬���ϼ������Ƿ���¥�ڵ㣬Ҫ����������
				String thisLongNumber = thisFDCOrgInfo.getLongNumber();
				while(thisLongNumber.indexOf("!")>0){
					String parentLongNumber = thisLongNumber.substring(0,thisLongNumber.lastIndexOf("!"));
					if(orgLongNumberNodeMap.get(parentLongNumber)!=null){
						parentNode = (DefaultKingdeeTreeNode)orgLongNumberNodeMap.get(parentLongNumber);
						break;
					}
					thisLongNumber = parentLongNumber;
				}				
				parentNode.add(sonNode);				
				orgIdNodeMap.put(thisFDCOrgInfo.getUnit().getId().toString(), sonNode);
				orgLongNumberNodeMap.put(thisFDCOrgInfo.getLongNumber(), sonNode);
			}	
			
			Set orgIdspIdSet = new HashSet(); //У����ͬ��֯�µ���ͬ��Ŀ
			Map spIdToNode = new HashMap();   //��Ŀid��Ӧ���Ľڵ�
			String permitSpIdStr = MarketingUnitFactory.getRemoteInstance().getPermitSellProjectIdSql(currUserInfo);
			
			SellProjectCollection sellProjectColl = SellProjectFactory.getRemoteInstance().getSellProjectCollection(
					"select * where id in ("+  permitSpIdStr  +") "+(includeSonSellPorject?"":" and level = 1")+" order by level,number"); 
			for (int i = 0; i < sellProjectColl.size(); i++) {
				SellProjectInfo thisSpInfo = sellProjectColl.get(i);
				//thisSpInfo.setOrgUnit(saleOrgInfo.castToFullOrgUnitInfo());
				DefaultKingdeeTreeNode sonNode = new DefaultKingdeeTreeNode(thisSpInfo);
				sonNode.setCustomIcon(EASResource.getIcon("imgTbtn_assistantaccount"));
				if(spIdToNode.get(thisSpInfo.getId().toString())!=null) continue;
				
				spIdToNode.put(thisSpInfo.getId().toString(), sonNode);
				if(thisSpInfo.getLevel()==1){
					DefaultKingdeeTreeNode parentNode = (DefaultKingdeeTreeNode)orgIdNodeMap.get(thisSpInfo.getOrgUnit().getId().toString()); //������֯
					if(parentNode!=null) {
						parentNode.add(sonNode);
						orgIdspIdSet.add(thisSpInfo.getId() + "," + thisSpInfo.getOrgUnit().getId());
					}else {
						rootNode.add(sonNode);
						orgIdspIdSet.add(thisSpInfo.getId() + "," + saleOrgInfo.getId());
					}
				}else{
					SellProjectInfo parentSpInfo = sellProjectColl.get(i).getParent();
					if(parentSpInfo==null) continue;
					DefaultKingdeeTreeNode parentNode = (DefaultKingdeeTreeNode)spIdToNode.get(parentSpInfo.getId().toString());
					if(parentNode!=null) 
						parentNode.add(sonNode);
					else
						rootNode.add(sonNode);
				}
			}			
			
			return new DefaultTreeModel(rootNode);
		}
		else{
			//����¥��֯           ����Ŀ�Ĵ�����֯��
			TreeModel tree = getSaleOrgTree(actionOnLoad,saleOrgInfo);  //����֯���������Ŀ�ڵ�			
			DefaultMutableTreeNode rootNode = (DefaultMutableTreeNode)tree.getRoot();
			
			deleteNonCompanyNode(rootNode,rootNode,true);
			
			Map orgIdMap = getAllObjectIdMap(rootNode,"OrgStructure");
			Map spIdToNode = new HashMap();   //��Ŀid��Ӧ���Ľڵ�
			SellProjectCollection sellProjectColl = SellProjectFactory.getRemoteInstance().getSellProjectCollection(
					"select * where id in ("+ MarketingUnitFactory.getRemoteInstance().getPermitSellProjectIdSql(currUserInfo)  +") " +
							" "+(includeSonSellPorject?"":" and level = 1")+" order by level,number"); 			
			for (int i = 0; i < sellProjectColl.size(); i++) {
				SellProjectInfo thisSpInfo = sellProjectColl.get(i);
				DefaultKingdeeTreeNode sonNode = new DefaultKingdeeTreeNode(thisSpInfo);
				sonNode.setCustomIcon(EASResource.getIcon("imgTbtn_assistantaccount"));	
				if(spIdToNode.get(thisSpInfo.getId().toString())!=null) continue;
				
				if(thisSpInfo.getLevel()==1){
					/*DefaultMutableTreeNode orgNode = (DefaultMutableTreeNode)orgIdMap.get(thisSpInfo.getOrgUnit().getId().toString());
					if(orgNode!=null) {
						if(spIdToNode.get(thisSpInfo.getId().toString())==null) {
							orgNode.add(sonNode);
							spIdToNode.put(thisSpInfo.getId().toString(), sonNode);
						}
					}*/
					if (thisSpInfo.getCompanyOrgUnit() == null) {
						continue;
					} else {
						if (orgIdMap.get(thisSpInfo.getCompanyOrgUnit().getId().toString()) == null) {
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
					
				}else{
					SellProjectInfo parentSpInfo = sellProjectColl.get(i).getParent();
					if(parentSpInfo==null) continue;
					DefaultKingdeeTreeNode parentNode = (DefaultKingdeeTreeNode)spIdToNode.get(parentSpInfo.getId().toString());
					if(parentNode!=null) {
						if(spIdToNode.get(thisSpInfo.getId().toString())==null) {
							parentNode.add(sonNode);
							spIdToNode.put(thisSpInfo.getId().toString(), sonNode);
						}
					}
				}				
			}
			return tree;
		}
	}
	
	public static TreeModel getSaleOrgTree(ItemAction actionOnLoad, OrgUnitInfo orgInfo)	throws Exception {
		MetaDataPK dataPK = null;
		if (actionOnLoad != null) {
			String actoinName = actionOnLoad.getClass().getName();
			if (actoinName.indexOf("$") >= 0) {
				actoinName = actoinName.substring(actoinName.indexOf("$") + 1);
			}
			dataPK = new MetaDataPK(actoinName);
		}
		TreeModel tree = NewOrgUtils.getTreeModel(OrgViewType.SALE, "", orgInfo.getId().toString(), null, dataPK);		
		return tree;
	}
	
	/**
	 * ɾ����֯�ṹ�еķǲ�����֯
	 * @param root
	 * @param treeNode
	 */
	private static void deleteNonCompanyNode(DefaultMutableTreeNode root,
			DefaultMutableTreeNode treeNode,boolean isEntryChild) {
		DefaultKingdeeTreeNode thisNode = (DefaultKingdeeTreeNode) treeNode;
		List indexList = new ArrayList();
		if (thisNode.getUserObject() != null
				&& thisNode.getUserObject() instanceof OrgStructureInfo) {
			OrgStructureInfo org = (OrgStructureInfo) thisNode.getUserObject();
			if (org != null && org.getUnit() != null
					&& !org.getUnit().isIsCompanyOrgUnit()) {
				indexList.add(root.getIndex(thisNode) + "");
			}
		}
		
		if (indexList.size() > 0) {
			int temp = 0;
			for (int i = 0; i < indexList.size(); i++) {
				temp = Integer.parseInt(indexList.get(i).toString());
				if(temp == -1){
					if(thisNode.getParent()!=null){
						deleteNonCompanyNode((DefaultMutableTreeNode)thisNode.getParent(),thisNode,false);
					}
				}else{
					root.remove(temp);
				}
			}
		}

		if(isEntryChild){
			int childCount = treeNode.getChildCount();
			while (childCount > 0) {
				deleteNonCompanyNode(root, (DefaultMutableTreeNode) treeNode
						.getChildAt(childCount - 1),true);
				childCount--;
			}
		}
	}
	
	/**
	 * ��ѯĳ�ڵ������е�ָ�������id�ͽڵ��ӳ��
	 * @param treeNode
	 * @param treeType  ��֯OrgStructure��������ĿSellProject
	 */
	
	public static Map getAllObjectIdMap(TreeNode treeNode,String treeType) {
		Map idMap = new HashMap();
		if(treeNode!=null) {
			String treeTypeStr = "OrgStructure��SellProject";
			if(treeType!=null && treeTypeStr.indexOf(treeType)>=0)
			{
				fillTreeNodeIdMap(idMap,treeNode,treeType);
			}
		}
		
		return idMap;
	}	
	
	private static void fillTreeNodeIdMap(Map idMap,TreeNode treeNode, String treeType){		
		DefaultKingdeeTreeNode thisNode = (DefaultKingdeeTreeNode)treeNode;
		
		if(treeType.equals("OrgStructure")) {  //�洢������֯��Ԫid
			if(thisNode.getUserObject() instanceof OrgStructureInfo){
				OrgStructureInfo objectInfo = (OrgStructureInfo)thisNode.getUserObject();
				idMap.put(objectInfo.getUnit().getId().toString(),thisNode);
			}
		}else if(treeType.equals("SellProject")) {
			if(thisNode.getUserObject() instanceof SellProjectInfo){
				 SellProjectInfo objectInfo = (SellProjectInfo)thisNode.getUserObject();
				 idMap.put(objectInfo.getId().toString(),thisNode);
			}
		}

		int childCount = treeNode.getChildCount();
		
		while(childCount>0) {				
			fillTreeNodeIdMap(idMap,treeNode.getChildAt(childCount-1),treeType);		
			childCount--;
		}	

	}
	
	/**
     * ��ָ��table�趨����ʽ�������Ҷ���
     * 
     * @param table ���
     * @param columnNames ��������
     */
    public static void changeTableNumberFormat(KDTable table, String[] columnNames) {
        for (int i = 0; i < columnNames.length; i++)
            changeTableNumberFormat(table, columnNames[i]);
    }
    
    /**
     * ��ָ��table�趨����ʽ�������Ҷ���
     * 
     * @param table ���
     * @param columnName ����
     */
    public static void changeTableNumberFormat(KDTable table, String columnName) {
    	if(table.getColumn(columnName)!=null){
    		table.getColumn(columnName).getStyleAttributes().setNumberFormat(strDataFormat);
            table.getColumn(columnName).getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT); // �Ҷ���
    	}
    }
	
	//TODO
	//���¿�ʼΪ��ֵ�ֽ���巽
	/**
	 * Ŀ���ֵԤ��  ���úϼ���
	 * ���ԶԶ�����ͣ�
	 * @param columnName����������
	 * **/
	public static void getAmtFootRow_Measure(KDTable tblMain,String[] columnName){
		IRow footRow = null;
        KDTFootManager footRowManager = tblMain.getFootManager();
        if(footRowManager == null)
        {
            String total = EASResource.getString("com.kingdee.eas.framework.FrameWorkResource.Msg_Total");
            footRowManager = new KDTFootManager(tblMain);
            footRowManager.addFootView();
            tblMain.setFootManager(footRowManager);
            footRow = footRowManager.addFootRow(0);
            footRow.getStyleAttributes().setHorizontalAlign(com.kingdee.bos.ctrl.kdf.util.style.Styles.HorizontalAlignment.getAlignment("right"));
            tblMain.getIndexColumn().setWidthAdjustMode((short)1);
            tblMain.getIndexColumn().setWidth(30);
            footRowManager.addIndexText(0, total);
        } else
        {
            footRow = footRowManager.getFootRow(0);
        }
        int columnCount = tblMain.getColumnCount();
        for(int c = 0; c < columnCount; c++)
        {
            String fieldName = tblMain.getColumn(c).getKey();
            for(int i = 0; i < columnName.length; i++)
            {
                String colName = (String)columnName[i];
                if(colName.equalsIgnoreCase(fieldName))
                {
                    ICell cell = footRow.getCell(c);
                    cell.getStyleAttributes().setNumberFormat("#,##0.00;-#,##0.00");
                    cell.getStyleAttributes().setHorizontalAlign(com.kingdee.bos.ctrl.kdf.util.style.Styles.HorizontalAlignment.getAlignment("right"));
                    cell.getStyleAttributes().setFontColor(java.awt.Color.BLACK);
                    cell.setValue(getColumnValueSum_Measure(tblMain,colName));//684
                }
            }
        }
        footRow.getStyleAttributes().setBackground(new java.awt.Color(246, 246, 191));
	}
	
	/**
     * ��ָ��table���������
     * 
     * @param table Ӫ��Ŀ���ֵ����
     * @param columnName �������
     */
    public static BigDecimal getColumnValueSum_Measure(KDTable table,String columnName) {
    	BigDecimal sum = new BigDecimal(0);
        for(int i=0;i<table.getRowCount();i++){
        	//С���в�����
        	if(table.getRow(i).getCell("newAreaRange").getValue() instanceof String){
        		if(table.getRow(i).getCell("newAreaRange").getValue().equals("С��")){
        			continue;
        		}
        	}
        	//�ϼ��в�����
        	if(table.getRow(i).getCell("productType").getValue() != null){
        		if(table.getRow(i).getCell("productType").getValue().equals("�ϼ�")){
        			continue;
        		}
        	}
        	if(table.getRow(i).getCell(columnName).getValue()!=null ){
        		if( table.getRow(i).getCell(columnName).getValue() instanceof BigDecimal)
            		sum = sum.add((BigDecimal)table.getRow(i).getCell(columnName).getValue());
            	else if(table.getRow(i).getCell(columnName).getValue() instanceof String){
            		String value = (String)table.getRow(i).getCell(columnName).getValue();
            		if(value.indexOf("��")==-1 && value.indexOf("[]")==-1){
            			value = value.replaceAll(",", "");
                		sum = sum.add(new BigDecimal(value));
            		}
            	}
            	else if(table.getRow(i).getCell(columnName).getValue() instanceof Integer){
            		String value = table.getRow(i).getCell(columnName).getValue().toString();
            		sum = sum.add(new BigDecimal(value));
            	}
        	}
        }
        return sum;
    }
    
  //Ŀ���ܻ�ֵ���úϼ����Լ��ں�
	public static void setTotalRow(KDTable table){
		int business = 0;
		int park = 0;
		int publicBuild = 0;
		int XJ_COUNT = 0;
		Map XJ_MAP = new HashMap();
		ProductTypeInfo productTypeInfo_old = new ProductTypeInfo();
		ProductTypeInfo productTypeInfo_new = new ProductTypeInfo();
		String house_old = PlanIndexTypeEnum.house.getValue();
		String house_new = "";
		boolean ishasValue = false;
		KDTMergeManager mm = table.getMergeManager();
		for(int i=0;i<table.getRowCount();i++){
			PlanIndexTypeEnum planIndexType = (PlanIndexTypeEnum)table.getCell(i, 0).getValue();
			house_new = planIndexType.getValue();
			if(PlanIndexTypeEnum.house.equals(planIndexType)){
				//סլ����С���У����ڸñ�Ϊ��̬���ɣ��������������ע�⡣
				productTypeInfo_new = (ProductTypeInfo)table.getRow(i).getCell(1).getValue();
				if(productTypeInfo_new != null){
					if(!productTypeInfo_new.equals(productTypeInfo_old)){
						ishasValue = true;
						productTypeInfo_old = productTypeInfo_new;
						XJ_MAP.put(String.valueOf(XJ_COUNT), String.valueOf(i));
						XJ_COUNT++;
					}
				}
			}
			if(!house_old.equals(house_new) && ishasValue){
				XJ_MAP.put(String.valueOf(XJ_COUNT), String.valueOf(i));
				ishasValue = false;
			}
			if(PlanIndexTypeEnum.business.equals(planIndexType)){
				if(business==0){
					business = i;
					IRow row = table.addRow(i);
//					row.setEditor(CellBinder.getCellNumberEdit());
					row.getStyleAttributes().setBackground(FDCHelper.KDTABLE_TOTAL_BG_COLOR);
					row.getStyleAttributes().setLocked(true);
					row.getCell(1).setValue("�ϼ�");
					row.getCell(0).setValue(PlanIndexTypeEnum.house);
					row.getStyleAttributes().setNumberFormat("#,##0.00;-#,##0.00");
					row.setEditor( CommerceHelper.getKDFormattedTextDecimalEditor());
				}
			}else if(PlanIndexTypeEnum.parking.equals(planIndexType)){
				if(park==0){
					park = i;
					IRow row = table.addRow(i);
					row.getStyleAttributes().setBackground(FDCHelper.KDTABLE_TOTAL_BG_COLOR);
					row.getStyleAttributes().setLocked(true);
					row.getCell(1).setValue("�ϼ�");
					row.getCell(0).setValue(PlanIndexTypeEnum.business);
					row.getStyleAttributes().setNumberFormat("#,##0.00;-#,##0.00");
					row.setEditor( CommerceHelper.getKDFormattedTextDecimalEditor());
				}
			}else if(PlanIndexTypeEnum.publicBuild.equals(planIndexType)){
				if(publicBuild==0){
					publicBuild=i;
					IRow row = table.addRow(i);
					row.getStyleAttributes().setBackground(FDCHelper.KDTABLE_TOTAL_BG_COLOR);
					row.getStyleAttributes().setLocked(true);
					row.getCell(1).setValue("�ϼ�");
					row.getCell(0).setValue(PlanIndexTypeEnum.parking);
					row.getStyleAttributes().setNumberFormat("#,##0.00;-#,##0.00");
					row.setEditor( CommerceHelper.getKDFormattedTextDecimalEditor());
				}
			}
			if(i==table.getRowCount()-1){
				IRow row = table.addRow(++i);
				row.getStyleAttributes().setBackground(FDCHelper.KDTABLE_TOTAL_BG_COLOR);
				row.getStyleAttributes().setLocked(true);
				row.getCell(1).setValue("�ϼ�");
				row.getCell(0).setValue(PlanIndexTypeEnum.publicBuild);
				row.getStyleAttributes().setNumberFormat("#,##0.00;-#,##0.00");
				row.setEditor( CommerceHelper.getKDFormattedTextDecimalEditor());
			}
		}
		
		//���
		int longNum = 0;
		for(int i=1;i<XJ_MAP.size();i++){
			int rowNum = Integer.parseInt(XJ_MAP.get(i+"").toString());
			rowNum +=longNum;
			IRow row = table.addRow(rowNum);
			row.getStyleAttributes().setBackground(FDCHelper.KDTABLE_TOTAL_BG_COLOR);
			row.getStyleAttributes().setLocked(true);
			row.getCell(2).setValue("С��");
			row.getCell(1).setValue((ProductTypeInfo)table.getRow(rowNum-1).getCell(1).getValue());
			row.getCell(0).setValue(PlanIndexTypeEnum.house);
			row.getStyleAttributes().setNumberFormat("#,##0.00;-#,##0.00");
			longNum++;
		}
		
		if(XJ_MAP.size()>0){
			mm.mergeBlock(0, 0, business+XJ_MAP.size()-1, 0, KDTMergeManager.FREE_MERGE);
			mm.mergeBlock(business+XJ_MAP.size(), 0, park+XJ_MAP.size()-1, 0, KDTMergeManager.FREE_MERGE);
			mm.mergeBlock(park+XJ_MAP.size(), 0, publicBuild+XJ_MAP.size()-1, 0, KDTMergeManager.FREE_MERGE);
			mm.mergeBlock(publicBuild+XJ_MAP.size(), 0, table.getRowCount()-1, 0, KDTMergeManager.FREE_MERGE);
			
			//�ںϵ�2�� wyh
			mm.mergeBlockEx(0, 1, business+XJ_MAP.size()-1, 1, KDTMergeManager.FREE_MERGE);
		}
		else{
			mm.mergeBlock(0, 0, business, 0, KDTMergeManager.FREE_MERGE);
			mm.mergeBlock(business+1, 0, park, 0, KDTMergeManager.FREE_MERGE);
			mm.mergeBlock(park+1, 0, publicBuild, 0, KDTMergeManager.FREE_MERGE);
			mm.mergeBlock(publicBuild+1, 0, table.getRowCount()-1, 0, KDTMergeManager.FREE_MERGE);
		}
	}
	
	/**
	 * ��ȷֽ����úϼ���
	 * ���ԶԶ�����ͣ�
	 * @param columnName����������
	 * 
	 * **/
	public static void getYearFootRow(KDTable tblMain,Set columnName){
		IRow footRow = null;
        KDTFootManager footRowManager = tblMain.getFootManager();
        if(footRowManager == null)
        {
            String total = EASResource.getString("com.kingdee.eas.framework.FrameWorkResource.Msg_Total");
            footRowManager = new KDTFootManager(tblMain);
            footRowManager.addFootView();
            tblMain.setFootManager(footRowManager);
            footRow = footRowManager.addFootRow(0);
            footRow.getStyleAttributes().setHorizontalAlign(com.kingdee.bos.ctrl.kdf.util.style.Styles.HorizontalAlignment.getAlignment("right"));
            tblMain.getIndexColumn().setWidthAdjustMode((short)1);
            tblMain.getIndexColumn().setWidth(30);
            footRowManager.addIndexText(0, total);
        } else
        {
            footRow = footRowManager.getFootRow(0);
        }
        int columnCount = tblMain.getColumnCount();
        for(int c = 0; c < columnCount; c++)
        {
        	if(columnName.contains(String.valueOf(c))){
        		
	            ICell cell = footRow.getCell(c);
	            cell.getStyleAttributes().setNumberFormat("#,##0.00;-#,##0.00");
	            cell.getStyleAttributes().setHorizontalAlign(HorizontalAlignment.getAlignment("right"));
	            cell.getStyleAttributes().setFontColor(java.awt.Color.BLACK);
	            if(c%4!=1){
	            	cell.setValue(getColumnValueSum_value(tblMain,c));
        	    }else{
        	    	cell.setValue(FDCHelper.divide(getColumnValueSum_value(tblMain,c+1),getColumnValueSum_value(tblMain,c-2) ));
        	    } 	
        	}
        }
        footRow.getStyleAttributes().setBackground(new java.awt.Color(246, 246, 191));
        footRow.getCell(6).getStyleAttributes().setFontColor(Color.RED);
	}
	
	/**
     * ��ָ��table���������
     * 
     * @param table ��ֵ��ȷֽ�
     * @param columnName �������
     */
    public static BigDecimal getColumnValueSum_value(KDTable table,int columnName) {
    	BigDecimal sum = new BigDecimal(0);
        for(int i=0;i<table.getRowCount();i++){
//        	if(table.getRow(i).getCell(1).getValue().equals("�ϼ�")){
//        		continue;
//        	}
//        	if(table.getRow(i).getCell(2).getValue() != null){
//        		if(table.getRow(i).getCell(2).getValue().equals("С��")){
//            		continue;
//            	}
//        	}
        	
        	if(table.getRow(i).getCell(1).getValue() instanceof String){
        		continue;
        	}
        	if(table.getRow(i).getCell(2).getValue() != null){
        		if(table.getRow(i).getCell(2).getValue() instanceof String){
            		continue;
            	}
        	}
        	
        	if(table.getRow(i).getCell(columnName)!=null && table.getRow(i).getCell(columnName).getValue()!=null 
        			&& table.getRow(i).getCell(columnName).getValue() instanceof BigDecimal)
        		sum = sum.add((BigDecimal)table.getRow(i).getCell(columnName).getValue());
        	else if(table.getRow(i).getCell(columnName)!=null && table.getRow(i).getCell(columnName).getValue()!=null 
        			&& table.getRow(i).getCell(columnName).getValue() instanceof String){
        		String value = (String)table.getRow(i).getCell(columnName).getValue();
        		if(value.indexOf(",")!=-1){
        			value = value.replaceAll(",", "");
            		if(value!=null && isNumeric(value)){
                		sum = sum.add(new BigDecimal(value));
            		}
        		}
        	}else if(table.getRow(i).getCell(columnName)!=null && table.getRow(i).getCell(columnName).getValue() instanceof Integer){
        		String value = table.getRow(i).getCell(columnName).getValue().toString();
        		if(value!=null){
        			sum = sum.add(new BigDecimal(value));
        		}
        	}
        }
        return sum;
    }
    
    /**
	 * �ж�һ���ַ���ȫ������
	 * */
	public static boolean isNumeric(String str)
	{
		Pattern pattern = Pattern.compile("[0-9]*");
		Matcher isNum = pattern.matcher(str);
		if( !isNum.matches() )
		{
			return false;
		}
		return true;
	}
	
	/**
	 * ���úϼ����Լ��ں�, Ŀ���ֵ��ȷֽ�falg Ϊtrue���¶ȷֽ�flagΪfalse �����ۼƻ�
	 * ������ȷֽ�
	 * */
	public static void setTotalRow2(KDTable table,boolean flag){
		
		boolean ishouse = false;
		boolean isbusiness = false;
		boolean ispark = false;
		boolean ispublicBuild =false;
		int house =-1;
		int lastHouse =-1;
		int business = -1;
		int lastBusiness =-1;
		int park = -1;
		int lastPark =-1;
		int publicBuild = -1;
		int lastPublicBuild =-1;
		
		int XJ_COUNT = 0;
		Map XJ_MAP = new HashMap();
		ProductTypeInfo productTypeInfo_old = new ProductTypeInfo();
		ProductTypeInfo productTypeInfo_new = new ProductTypeInfo();
		String house_old = PlanIndexTypeEnum.house.getValue();
		String house_new = "";
		boolean ishasValue = false;
		
		KDTMergeManager mm = table.getMergeManager();
		for(int i=0;i<table.getRowCount();i++){
			//��Ʒ����
			PlanIndexTypeEnum planIndexType = (PlanIndexTypeEnum)table.getCell(i, 0).getValue();
			house_new = planIndexType.getValue();
			if(PlanIndexTypeEnum.house.equals(planIndexType)){
				//סլ����С����
				if(table.getRow(i).getCell(1).getValue() instanceof ProductTypeInfo){
					productTypeInfo_new = (ProductTypeInfo)table.getRow(i).getCell(1).getValue();
				}
				if(productTypeInfo_new != null){
					if(!productTypeInfo_new.equals(productTypeInfo_old)){
						ishasValue = true;
						productTypeInfo_old = productTypeInfo_new;
						XJ_MAP.put(String.valueOf(XJ_COUNT), String.valueOf(i));
						XJ_COUNT++;
					}
				}
				if(!ishouse){
					ishouse = true;
					house = i;
				}
				lastHouse =i;
			}
			if(!house_old.equals(house_new) && ishasValue){
				XJ_MAP.put(String.valueOf(XJ_COUNT), String.valueOf(i));
				ishasValue = false;
			}
			if(PlanIndexTypeEnum.business.equals(planIndexType)){
				if(!isbusiness){
					isbusiness = true;
					business = i;
				}
				lastBusiness =i;
			}
			if(PlanIndexTypeEnum.parking.equals(planIndexType)){
				if(!ispark){
					ispark = true;
					park = i;
				}
				lastPark =i;
			}
			if(PlanIndexTypeEnum.publicBuild.equals(planIndexType)){
				if(!ispublicBuild){
					ispublicBuild = true;
					publicBuild = i;
				}
				lastPublicBuild = i;
			}
		}
		//���С��
		//���
		int longNum = 0;
		if(table.getRowCount()/4 == XJ_MAP.size()){
			int num = XJ_MAP.size();
			XJ_MAP.put(String.valueOf(num), String.valueOf(table.getRowCount()));
		}
		for(int i=1;i<XJ_MAP.size();i++){
			int rowNum = Integer.parseInt(XJ_MAP.get(i+"").toString());
			rowNum +=longNum;
			IRow row = table.addRow(rowNum);
			row.getStyleAttributes().setBackground(FDCHelper.KDTABLE_TOTAL_BG_COLOR);
			row.getStyleAttributes().setLocked(true);
			row.getCell(2).setValue("С��");
			row.getCell(1).setValue((ProductTypeInfo)table.getRow(rowNum-1).getCell(1).getValue());
			row.getCell(0).setValue(PlanIndexTypeEnum.house);
			row.getStyleAttributes().setNumberFormat("#,##0.00;-#,##0.00");
			longNum++;
		}
		if(ishouse){
			sumRow(table,lastHouse+longNum+1,flag);
			business++;
			park++;
			publicBuild++;
			lastBusiness++;
			lastPark++;
			lastPublicBuild++;
		}
		if(isbusiness){
			sumRow(table,lastBusiness+longNum+1,flag);
			park++;
			publicBuild++;
			lastPark++;
			lastPublicBuild++;
		}
		if(ispark){
			sumParkRow(table,lastPark+longNum+1,flag);
			publicBuild++;
			lastPublicBuild++;
		}
		if(ispublicBuild){
			sumRow(table,lastPublicBuild+longNum+1,flag);
		}
		//�ںϵ�2�� wyh
		mm.mergeBlock(house, 0, lastHouse+longNum+1, 0, KDTMergeManager.SPECIFY_MERGE);
		mm.mergeBlock(business+longNum, 0, lastBusiness+longNum+1, 0, KDTMergeManager.SPECIFY_MERGE);
		mm.mergeBlock(park+longNum, 0, lastPark+longNum+1, 0, KDTMergeManager.SPECIFY_MERGE);
		mm.mergeBlock(publicBuild+longNum, 0, lastPublicBuild+longNum+1, 0, KDTMergeManager.SPECIFY_MERGE);
		mm.mergeBlockEx(0, 1, lastHouse+longNum, 1, KDTMergeManager.FREE_MERGE);
	}
	
	/**
	 * ���úϼ����Լ��ں�, Ŀ���ֵ����ȷֽ�falg Ϊtrue���¶ȷֽ�flagΪfalse �����ۼƻ�
	 * ������ȷֽ� �����ͺϼ�
	 * */
	public static void sumRow(KDTable table,int i,boolean flag){
		IRow row = table.addRow(i);
		row.getStyleAttributes().setBackground(FDCHelper.KDTABLE_TOTAL_BG_COLOR);
		row.getStyleAttributes().setLocked(true);
		row.getCell(1).setValue("�ϼ�");
		row.getCell(0).setValue((PlanIndexTypeEnum)table.getRow(i-1).getCell(0).getValue());
		row.getStyleAttributes().setNumberFormat("#,##0.00;-#,##0.00");
		row.setEditor(CommerceHelper.getKDFormattedTextDecimalEditor());
		if(flag)
			reSetExpressions(table,row,i);
		else
			getReSetExpressions(table,row,i);	
	}
	
	/**
	 * 
	 * */
	private static void reSetExpressions(KDTable table,IRow row,int n){
		int m = n-1;
		//С�Ʊ�������
		HashMap XJ_MAP = new HashMap();//���С���к�
		int num = 0;
		
		//�����Ƿ��Ѿ����ںϼ�
		for(;m>=0;m--){
			if(table.getRow(m).getCell(1).getValue()instanceof String){
				break;
			}
		}
		
		for(int i=0;i<table.getRowCount()-1;i++){
			IRow row_xj=table.getRow(i);
			//��ȡС���к�
			if(row_xj.getCell(0).getValue()==PlanIndexTypeEnum.house && row_xj.getCell(2).getValue() != null){
				if(row_xj.getCell(2).getValue().equals("С��")){
					row_xj.getStyleAttributes().setBackground(FDCHelper.KDTABLE_TOTAL_BG_COLOR);
					XJ_MAP.put(String.valueOf(num),String.valueOf(i));
					num++;
				}
			}
		}
		
		//��סլС�Ƶ�С����ϼƼ��㹫ʽ
		if(PlanIndexTypeEnum.house.equals((PlanIndexTypeEnum)row.getCell(0).getValue()) && XJ_MAP.size()>0){
			//��С��������С�Ƽ���
			for(int i=0;i<XJ_MAP.size();i++){
				int rowNum = Integer.parseInt(XJ_MAP.get(String.valueOf(i)).toString());
				int old_rowNum = 1;
				if(i>0){
					old_rowNum = Integer.parseInt(XJ_MAP.get(String.valueOf(i-1)).toString()) + 2;
				}
				IRow houseRow = table.getRow(rowNum);
				for(int j = 0; j<table.getColumnCount();j++){
					if(j==0||j==1||j==2||j%4==1){//��ȥ��Ʒ���ɡ���Ʒ���͡�����Ρ����۾�����ĺϼ�
						continue;
					}
					String c ="";
					String exp = null;
					if(j<=25){
					   c=String.valueOf(((char)('A'+j)));
					}else{
					   c=(char)('A'+j/26-1)+String.valueOf(((char)('A'+j%26)));
					}
					exp="=sum("+c+(old_rowNum)+":"+c+(rowNum)+")";
					houseRow.getCell(j).setExpressions(exp);
					if(j%4==2){//С�Ƶ����۾���
						Number subTotalArea = (Number)houseRow.getCell(j-3).getValue();
						Number subTotalAmount = (Number)houseRow.getCell(j).getValue();
						BigDecimal price = FDCHelper.divide(subTotalAmount,subTotalArea );
						houseRow.getCell(j-1).setValue(price);
					}
				}
			}
			//��ʼ����ϼ�
			for(int j = 0; j<table.getColumnCount();j++){
				if(j==0||j==1||j==2||j%4==1){//��ȥ��Ʒ���ɡ���Ʒ���͡�����Ρ����۾�����ĺϼ�
					continue;
				}
				String c ="";
				String exp = "=";
				if(j<=25){
				   c=String.valueOf(((char)('A'+j)));
				}else{
				   c=(char)('A'+j/26-1)+String.valueOf(((char)('A'+j%26)));
				}
				for(int i=0;i<XJ_MAP.size();i++){
					exp = exp+c+(Integer.parseInt(XJ_MAP.get(String.valueOf(i)).toString())+1)+"+";
				}
				row.getCell(j).setExpressions(exp.substring(0, exp.length()-1));
				if(j%4==2){//С�Ƶ����۾���
					Number subTotalArea = (Number)row.getCell(j-3).getValue();
					Number subTotalAmount = (Number)row.getCell(j).getValue();
					BigDecimal price = FDCHelper.divide(subTotalAmount,subTotalArea );
					row.getCell(j-1).setValue(price);
				}
			}
		}
		else{
			for(int j = 0; j<table.getColumnCount();j++){
				if(j==0||j==1||j==2||j%4==1){
					continue;
				}
				String c ="";
				String exp = "";
				if(j<=25){
				   c=String.valueOf(((char)('A'+j)));
				}else{
				   c=(char)('A'+j/26-1)+String.valueOf(((char)('A'+j%26)));
				}
				exp="=sum("+c+(m+2)+":"+c+(n)+")";
				row.getCell(j).setExpressions(exp);
				if(j%4==2){
					Number subTotalArea = (Number)row.getCell(j-3).getValue();
					Number subTotalAmount = (Number)row.getCell(j).getValue();
					BigDecimal price = FDCHelper.divide(subTotalAmount,subTotalArea );
				    row.getCell(j-1).setValue(price);
				}
			}
		}
	}
	/**
	 * �¶ȼƻ����ñ�
	 *@table ������
	 *@row ĳ���Ʒ�ϼ���
	 *@n �����Ʒ������
	 * */
	private static void getReSetExpressions(KDTable table,IRow row,int n){
		int m = n-1;
		//�����Ʒ�ṹ��סլ
		if(row.getCell(0).getValue().equals(PlanIndexTypeEnum.house)){
			IRow houseRow;
			//С������
			HashMap XJ_MAP = new HashMap();
			int num = 0;
			for(int i=0;i<n;i++){
				IRow row_xj=table.getRow(i);
				//��ȡС���к�
				if(row_xj.getCell("areaRange").getValue() != null){
					if(row_xj.getCell("areaRange").getValue().equals("С��")){
						row.getStyleAttributes().setBackground(FDCHelper.KDTABLE_TOTAL_BG_COLOR);
						XJ_MAP.put(String.valueOf(num),String.valueOf(i));
						num++;
					}
				}
			}
			//С��ֵ����
			for(int i=0;i<XJ_MAP.size();i++){
				int rowNum = Integer.parseInt(XJ_MAP.get(String.valueOf(i)).toString());
				int old_rowNum = 1;
				if(i>0){
					old_rowNum = Integer.parseInt(XJ_MAP.get(String.valueOf(i-1)).toString()) + 2;
				}
				houseRow = table.getRow(rowNum);
				//13��14��15��16����ΪԤ�����������Ԥ������������Ԥ�����۾��ۡ�Ԥ�����۽��
				for(int j=3;j<table.getColumnCount()-1;j++){
					String exp=null;
					if(j>=13 && j<=17){
						if(j==15){
							exp="="+"(Q"+(rowNum+1)+"/"+"N"+(rowNum+1)+")";
							houseRow.getCell(j).setExpressions(exp);
							houseRow.getStyleAttributes().setNumberFormat("#,##0.00;-#,##0.00");
							continue;
						}
						else{
							String c = String.valueOf((char)('A'+j));
							exp="=sum("+c+(old_rowNum)+":"+c+(rowNum)+")";
							houseRow.getCell(j).setExpressions(exp);
							houseRow.getStyleAttributes().setNumberFormat("#,##0.00;-#,##0.00");
							continue;
						}
					}
					if(j < 13){
						if(j == 7 || j == 8){
							String c = String.valueOf((char)('A'+(j+2)));
							String c_1 = String.valueOf((char)('A'+(j-4)));
							exp="="+"("+c+(rowNum+1)+"/"+c_1+(rowNum+1)+")";
							houseRow.getCell(j).setExpressions(exp);
							houseRow.getStyleAttributes().setNumberFormat("#,##0.00;-#,##0.00");
							continue;
						}
						else{
							String c = String.valueOf((char)('A'+j));
							exp="=sum("+c+(old_rowNum)+":"+c+(rowNum)+")";
							houseRow.getCell(j).setExpressions(exp);
							houseRow.getStyleAttributes().setNumberFormat("#,##0.00;-#,##0.00");
							continue;
						}
					}
					if(j > 17 && j <= 25){
						if(j%10==2 || j%10==3){
							int z = j+2;
							int z_1 = j-4;
							String c = String.valueOf((char)('A'+z));
							String c_1 = String.valueOf((char)('A'+z_1));
							exp="="+"("+c+(rowNum+1)+"/"+c_1+(rowNum+1)+")";
							houseRow.getCell(j).setExpressions(exp);
							houseRow.getStyleAttributes().setNumberFormat("#,##0.00;-#,##0.00");
							continue;
						}
						else{
							String c = String.valueOf((char)('A'+j));
							exp="=sum("+c+(old_rowNum)+":"+c+(rowNum)+")";
							houseRow.getCell(j).setExpressions(exp);
							houseRow.getStyleAttributes().setNumberFormat("#,##0.00;-#,##0.00");
							continue;
						}
					}
					if(j>25){
						int z = j+2;
						int z_1 = j-4;
						if(j%10==2 || j%10==3){
							String c=(char)('A'+z/26-1)+String.valueOf(((char)('A'+z%26)));
							String c_1 = (char)('A'+z_1/26-1)+String.valueOf(((char)('A'+z_1%26)));
							exp="="+"("+c+(rowNum+1)+"/"+c_1+(rowNum+1)+")";
							houseRow.getCell(j).setExpressions(exp);
							houseRow.getStyleAttributes().setNumberFormat("#,##0.00;-#,##0.00");
							continue;
						}
						else{
							String c=(char)('A'+j/26-1)+String.valueOf(((char)('A'+j%26)));
							exp="=sum("+c+(old_rowNum)+":"+c+(rowNum)+")";
							houseRow.getCell(j).setExpressions(exp);
							houseRow.getStyleAttributes().setNumberFormat("#,##0.00;-#,##0.00");
							continue;
						}
					}
				}
			}
			houseRow = table.getRow(n);
			//��С�������ݺϼƼ���
			if(XJ_MAP.size() > 0){
				for(int j=3;j<table.getColumnCount()-1;j++){
					String c="";
					String c_1="";
					String exp="=";
					if(j<=25){
						if(j == 7 || j == 8){
							c = String.valueOf((char)('A'+(j+2)));
							c_1 = String.valueOf((char)('A'+(j-4)));
							exp=exp+"("+c+(n+1)+"/"+c_1+(n+1)+")";
							houseRow.getCell(j).setExpressions(exp);
							houseRow.getStyleAttributes().setNumberFormat("#,##0.00;-#,##0.00");
							continue;
						}
						if(j==15){
							exp=exp+"(Q"+(n+1)+"/"+"N"+(n+1)+")";
							houseRow.getCell(j).setExpressions(exp);
							houseRow.getStyleAttributes().setNumberFormat("#,##0.00;-#,##0.00");
							continue;
						}
						if(j==22 || j==23){
							int z = j+2;
							int z_1 = j-4;
							c = String.valueOf((char)('A'+z));
							c_1 = String.valueOf((char)('A'+z_1));
							exp=exp+"("+c+(n+1)+"/"+c_1+(n+1)+")";
							houseRow.getCell(j).setExpressions(exp);
							houseRow.getStyleAttributes().setNumberFormat("#,##0.00;-#,##0.00");
							continue;
						}
						c=String.valueOf(((char)('A'+j)));
						for(int i=0;i<XJ_MAP.size();i++){
							exp = exp+c+(Integer.parseInt(XJ_MAP.get(String.valueOf(i)).toString())+1)+"+";
						}
						houseRow.getCell(j).setExpressions(exp.substring(0, exp.length()-1));
						houseRow.getStyleAttributes().setNumberFormat("#,##0.00;-#,##0.00");
					}else{
						int z = j+2;
						int z_1 = j-4;
						if(j%10==2 || j%10==3){
							c = (char)('A'+z/26-1)+String.valueOf(((char)('A'+z%26)));
							c_1 = (char)('A'+z_1/26-1)+String.valueOf(((char)('A'+z_1%26)));
							exp = exp+"("+c+(n+1)+"/"+c_1+(n+1)+")";
							houseRow.getCell(j).setExpressions(exp);
							houseRow.getStyleAttributes().setNumberFormat("#,##0.00;-#,##0.00");
							continue;
						}else{
							c=(char)('A'+j/26-1)+String.valueOf(((char)('A'+j%26)));
							for(int i=0;i<XJ_MAP.size();i++){
								exp = exp+c+(Integer.parseInt(XJ_MAP.get(String.valueOf(i)).toString())+1)+"+";
							}
							houseRow.getCell(j).setExpressions(exp.substring(0, exp.length()-1));
							houseRow.getStyleAttributes().setNumberFormat("#,##0.00;-#,##0.00");
						}
					}
				}
			}
			else{
				for(int j=3;j<table.getColumnCount()-1;j++){
					String c = "";
					String c_1 = "";
					String exp="=";
					if(j<=25){
						if(j == 7 || j == 8){
							c = String.valueOf((char)('A'+(j+2)));
							c_1 = String.valueOf((char)('A'+(j-4)));
							exp=exp+"("+c+(n+1)+"/"+c_1+(n+1)+")";
							houseRow.getCell(j).setExpressions(exp);
							houseRow.getStyleAttributes().setNumberFormat("#,##0.00;-#,##0.00");
							continue;
						}
						if(j==15){
							exp=exp+"(Q"+(n+1)+"/"+"N"+(n+1)+")";
							houseRow.getCell(j).setExpressions(exp);
							houseRow.getStyleAttributes().setNumberFormat("#,##0.00;-#,##0.00");
							continue;
						}
						if(j==22 || j==23){
							int z = j+2;
							int z_1 = j-4;
							c = String.valueOf((char)('A'+z));
							c_1 = String.valueOf((char)('A'+z_1));
							exp=exp+"("+c+(n+1)+"/"+c_1+(n+1)+")";
							houseRow.getCell(j).setExpressions(exp);
							houseRow.getStyleAttributes().setNumberFormat("#,##0.00;-#,##0.00");
							continue;
						}
						c=String.valueOf(((char)('A'+j)));
						exp="=sum("+c+(1)+":"+c+(n)+")";
						houseRow.getCell(j).setExpressions(exp);
					}else{
						int z = j+2;
						int z_1 = j-4;
						if(j%10==2 || j%10==3){
							c = (char)('A'+z/26-1)+String.valueOf(((char)('A'+z%26)));
							c_1 = (char)('A'+z_1/26-1)+String.valueOf(((char)('A'+z_1%26)));
							exp = exp+"("+c+(n+1)+"/"+c_1+(n+1)+")";
							houseRow.getCell(j).setExpressions(exp);
							houseRow.getStyleAttributes().setNumberFormat("#,##0.00;-#,##0.00");
							continue;
						}else{
							c = (char)('A'+j/26-1)+String.valueOf(((char)('A'+j%26)));
							exp="=sum("+c+(1)+":"+c+(n)+")";
							houseRow.getCell(j).setExpressions(exp.substring(0, exp.length()-1));
							houseRow.getStyleAttributes().setNumberFormat("#,##0.00;-#,##0.00");
						}
					}
					
				}
			}
		}
		else{
			for(;m>=0;m--){
				if(table.getRow(m).getCell(1).getValue()instanceof String){
					break;
				}
			}
			for(int j = 3; j<table.getColumnCount();j++){
				String c ="";
				String c_1 ="";
				String exp = "";
				if(j<=25){
				   c=String.valueOf(((char)('A'+j)));
				   if(j == 7 || j == 8){
						c = String.valueOf((char)('A'+(j+2)));
						c_1 = String.valueOf((char)('A'+(j-4)));
						exp=exp+"("+c+(n+1)+"/"+c_1+(n+1)+")";
						row.getCell(j).setExpressions(exp);
						row.getStyleAttributes().setNumberFormat("#,##0.00;-#,##0.00");
						continue;
					}
					if(j==15){
						exp=exp+"(Q"+(n+1)+"/"+"N"+(n+1)+")";
						row.getCell(j).setExpressions(exp);
						row.getStyleAttributes().setNumberFormat("#,##0.00;-#,##0.00");
						continue;
					}
					if(j==22 || j==23){
						int z = j+2;
						int z_1 = j-4;
						c = String.valueOf((char)('A'+z));
						c_1 = String.valueOf((char)('A'+z_1));
						exp=exp+"("+c+(n+1)+"/"+c_1+(n+1)+")";
						row.getCell(j).setExpressions(exp);
						row.getStyleAttributes().setNumberFormat("#,##0.00;-#,##0.00");
						continue;
					}
					exp="=sum("+c+(m+2)+":"+c+(n)+")";
					row.getCell(j).setExpressions(exp);
					row.getStyleAttributes().setNumberFormat("#,##0.00;-#,##0.00");
				}
				else{
				   int z = j+2;
				   int z_1 = j-4;
				   if(j%10==2 || j%10==3){
					   c = (char)('A'+z/26-1)+String.valueOf(((char)('A'+z%26)));
					   c_1 = (char)('A'+z_1/26-1)+String.valueOf(((char)('A'+z_1%26)));
					   exp = exp+"("+c+(n+1)+"/"+c_1+(n+1)+")";
					   row.getCell(j).setExpressions(exp);
					   row.getStyleAttributes().setNumberFormat("#,##0.00;-#,##0.00");
					   continue;
				   }else{
					   c = (char)('A'+j/26-1)+String.valueOf(((char)('A'+j%26)));
					   exp="=sum("+c+(m+2)+":"+c+(n)+")";
					   row.getCell(j).setExpressions(exp);
					   row.getStyleAttributes().setNumberFormat("#,##0.00;-#,##0.00");
				   }
				}
			}
		}
	}
	
	/**
	 * ���úϼ����Լ��ں�, Ŀ���ֵ����ȷֽ�falg Ϊtrue���¶ȷֽ�flagΪfalse �����ۼƻ�
	 * */
	public static void sumParkRow(KDTable table,int i,boolean flag){
		IRow row = table.addRow(i);
		row.getStyleAttributes().setBackground(FDCHelper.KDTABLE_TOTAL_BG_COLOR);
		row.getStyleAttributes().setLocked(true);
		row.getCell(1).setValue("�ϼ�");
		row.getCell(0).setValue((PlanIndexTypeEnum)table.getRow(i-1).getCell(0).getValue());
		row.getStyleAttributes().setNumberFormat("#,##0.00;-#,##0.00");
		row.setEditor(CommerceHelper.getKDFormattedTextDecimalEditor());
		if(flag){
			int m = i-1;
			for(;m>=0;m--){
				if(table.getRow(m).getCell(1).getValue()instanceof String){
					break;
				}
			}
			for(int j = 0; j<table.getColumnCount();j++){
				if(j==0||j==1||j==2||j%4==1){
					continue;
				}
				String c ="";
				String exp = "";
				if(j<=25){
				   c=String.valueOf(((char)('A'+j)));
				}else{
				   c=(char)('A'+j/26-1)+String.valueOf(((char)('A'+j%26)));
				}
				exp="=sum("+c+(m+2)+":"+c+(i)+")";
				row.getCell(j).setExpressions(exp);
				if(j%4==2){
					Number subTotalArea = (Number)row.getCell(j-2).getValue();
					Number subTotalAmount = (Number)row.getCell(j).getValue();
					BigDecimal price = FDCHelper.divide(subTotalAmount,subTotalArea );
				    row.getCell(j-1).setValue(price);
				}
			}	
		}
		else{
			int m = i-1;
			for(;m>=0;m--){
				if(table.getRow(m).getCell(1).getValue()instanceof String){
					break;
				}
			}
			for(int j = 0; j<table.getColumnCount();j++){
				if(j==0||j==1||j==2||j%4==1){
					continue;
				}
				String c ="";
				String exp = "";
				if(j<=25){
				   c=String.valueOf(((char)('A'+j)));
				}else{
				   c=(char)('A'+j/26-1)+String.valueOf(((char)('A'+j%26)));
				}
				exp="=sum("+c+(m+2)+":"+c+(i)+")";
				row.getCell(j).setExpressions(exp);
				if(j%4==2){
					Number subTotalArea = (Number)row.getCell(j-2).getValue();
					Number subTotalAmount = (Number)row.getCell(j).getValue();
					BigDecimal price = FDCHelper.divide(subTotalAmount,subTotalArea );
				    row.getCell(j-1).setValue(price);
				}
			}	
		}
	}
}
