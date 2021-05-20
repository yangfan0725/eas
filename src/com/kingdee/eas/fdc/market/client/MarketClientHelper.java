package com.kingdee.eas.fdc.market.client;

import java.util.HashMap;

import javax.swing.tree.DefaultTreeModel;

import com.kingdee.bos.ctrl.swing.KDTree;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.eas.basedata.org.SaleOrgUnitInfo;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.fdc.market.CycleEnum;
import com.kingdee.eas.fdc.market.MarketTypeCollection;
import com.kingdee.eas.fdc.market.MarketTypeFactory;
import com.kingdee.eas.fdc.market.MarketTypeInfo;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.MsgBox;

/**
 * 
 * ����:���ɻ������
 * 
 * @author hjx date:2009-04-17
 *         <p>
 * @version EAS6.0
 */

public class MarketClientHelper {
	
	private MarketClientHelper() {
	}
	
	public final static String STATUS_ADDNEW = "ADDNEW";
	/**
	 * ���2�����ڼ���·�����
	 * */
	
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
	 * ������
	 * @throws Exception
	 */
	public static void getMarketTypeTree(KDTree treeMain) throws Exception
	{
		DefaultKingdeeTreeNode rootNode =createTreeNode(true);
		DefaultTreeModel model = new DefaultTreeModel(rootNode);
		treeMain.setModel(model);
		treeMain.expandAllNodes(false, rootNode);
		if (rootNode.getChildCount() > 0)
			treeMain.setSelectionNode((DefaultKingdeeTreeNode) rootNode.getChildAt(0));
		else
			treeMain.setSelectionNode(rootNode);
		treeMain.setShowsRootHandles(true);
		
	}

	
	public static DefaultKingdeeTreeNode createTreeNode(boolean includeEmployeeType) throws Exception
	{
		//���ڵ�
		 DefaultKingdeeTreeNode rootTreeNode = new DefaultKingdeeTreeNode("�����"),subTreeNode = null,midTreeNode=null;
		 HashMap nodeMap = new HashMap();
		
		MarketTypeCollection marketUnitColl = MarketTypeFactory.getRemoteInstance().getMarketTypeCollection(" Order by Level");
		MarketTypeCollection leafUnits = new MarketTypeCollection();
		//������ʹ�������һ���ڵ�
		for(int i=0;i<marketUnitColl.size();i++)
		{
			MarketTypeInfo unit = marketUnitColl.get(i);
			if(unit.getParent() != null){
//				subTreeNode = new DefaultKingdeeTreeNode(marketUnitColl.get(i));
//				nodeMap.put(marketUnitColl.get(i).getId().toString(),subTreeNode);
				leafUnits.add(unit);
			}
			else{
				subTreeNode = new DefaultKingdeeTreeNode(marketUnitColl.get(i));
				nodeMap.put(marketUnitColl.get(i).getId().toString(),subTreeNode);
				rootTreeNode.add(subTreeNode);
			}
			
		}
		 
		 
		//������ʹ���������߶����������нڵ�
        for(int i=0;i<leafUnits.size();i++)
        {
        	MarketTypeInfo marketInfo = leafUnits.get(i);
        	subTreeNode = new DefaultKingdeeTreeNode(marketInfo);
        	
        	//���ҵȼ������Ļ���ͣ����뵽���ӽڵ�
        	if (!marketInfo.isIsLeaf()) {
        		subTreeNode = new DefaultKingdeeTreeNode(leafUnits.get(i));
				nodeMap.put(leafUnits.get(i).getId().toString(),subTreeNode);
				
        	}
        	if(marketInfo.getId().toString() != null && nodeMap.containsKey(marketInfo.getParent().getId().toString()))
        	{
        		((DefaultKingdeeTreeNode) nodeMap.get(marketInfo.getParent().getId().toString())).add(subTreeNode);
        	}
        }       
        
        nodeMap = null;
        return rootTreeNode;
	}
	
	/**
	 * ��������������
	 * @throws Exception
	 */
	public static void getCycleTree(KDTree treeMain) throws Exception
	{
		DefaultKingdeeTreeNode rootNode =createCycleNode(true);
		DefaultTreeModel model = new DefaultTreeModel(rootNode);
		treeMain.setModel(model);
		treeMain.expandAllNodes(true, rootNode);
		if (rootNode.getChildCount() > 0)
			treeMain.setSelectionNode((DefaultKingdeeTreeNode) rootNode.getChildAt(0));
		else
			treeMain.setSelectionNode(rootNode);
		treeMain.setShowsRootHandles(true);
		
	}
	public static DefaultKingdeeTreeNode createCycleNode(boolean includeEmployeeType) throws Exception
	{
		//���ڵ�
		DefaultKingdeeTreeNode rootTreeNode = new DefaultKingdeeTreeNode("��������"),subTreeNode = null;
		for(int i=0;i<CycleEnum.getEnumList().size();i++)
        {
			subTreeNode = new DefaultKingdeeTreeNode(CycleEnum.getEnumList().get(i));
			rootTreeNode.add(subTreeNode);
        }
		 
		return rootTreeNode;
		
	}
	
}