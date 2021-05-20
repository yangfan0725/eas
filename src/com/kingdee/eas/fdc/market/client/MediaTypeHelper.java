package com.kingdee.eas.fdc.market.client;

import java.util.HashMap;

import javax.swing.tree.DefaultTreeModel;

import com.kingdee.bos.ctrl.swing.KDTree;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.eas.base.forewarn.CompareType;
import com.kingdee.eas.basedata.org.SaleOrgUnitInfo;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.fdc.market.CycleEnum;
import com.kingdee.eas.fdc.market.MediaTypeCollection;
import com.kingdee.eas.fdc.market.MediaTypeFactory;
import com.kingdee.eas.fdc.market.MediaTypeInfo;
import com.kingdee.eas.framework.IFWEntityStruct;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.MsgBox;

public class MediaTypeHelper {
	private MediaTypeHelper() {
	}

	public final static String STATUS_ADDNEW = "ADDNEW";

	/**
	 * ���2�����ڼ���·�����
	 * */

	public static SaleOrgUnitInfo getCurrentSaleOrg() {
		SaleOrgUnitInfo saleUnit = SysContext.getSysContext().getCurrentSaleUnit();
		if (saleUnit == null) {
			MsgBox.showInfo("��ǰ��֯������������,���ܽ���!");
			SysUtil.abort();
		}
		return saleUnit;
	}

	/**
	 * ������
	 * 
	 * @throws Exception
	 */
	public static void getMediaTypeTree(KDTree treeMain) throws Exception {
		DefaultKingdeeTreeNode rootNode = createTreeNode(true);
		DefaultTreeModel model = new DefaultTreeModel(rootNode);
		treeMain.setModel(model);
		treeMain.expandAllNodes(false, rootNode);
		if (rootNode.getChildCount() > 0) {
			treeMain.setSelectionNode((DefaultKingdeeTreeNode) rootNode.getChildAt(0));
		}

		else {
			treeMain.setSelectionNode(rootNode);
		}
		treeMain.setShowsRootHandles(true);

	}

	public static DefaultKingdeeTreeNode createTreeNode(boolean includeEmployeeType) throws Exception {
		// ���ڵ�
		DefaultKingdeeTreeNode rootTreeNode = new DefaultKingdeeTreeNode("ý�����"), subTreeNode = null, midTreeNode = null;
		HashMap nodeMap = new HashMap();
		EntityViewInfo view = new EntityViewInfo();
		SelectorItemCollection sel = new SelectorItemCollection();
		sel.add("*");
		FilterInfo filter = new FilterInfo();
		String ctrlUnitId = SysContext.getSysContext().getCurrentCtrlUnit().getId().toString();
		if (ctrlUnitId != null && !"".equals(ctrlUnitId) ) {
			filter.getFilterItems().add(new FilterItemInfo(IFWEntityStruct.objectBase_CU, ctrlUnitId,com.kingdee.bos.metadata.query.util.CompareType.EQUALS));
		}
		filter.getFilterItems().add(
	                new FilterItemInfo(IFWEntityStruct.objectBase_CU, com.kingdee.eas.basedata.org.OrgConstants.SYS_CU_ID, com.kingdee.bos.metadata.query.util.CompareType.EQUALS));
        filter.getFilterItems().add(
                new FilterItemInfo(IFWEntityStruct.objectBase_CU, com.kingdee.eas.basedata.org.OrgConstants.DEF_CU_ID, com.kingdee.bos.metadata.query.util.CompareType.EQUALS));
        filter.setMaskString("#0 or #1 or #2");
		
		view.setSelector(sel);
		view.setFilter(filter);
		view.getSorter().add(new SorterItemInfo("level"));
		view.getSorter().add(new SorterItemInfo("name"));
		MediaTypeCollection mediaTypeCol = MediaTypeFactory.getRemoteInstance().getMediaTypeCollection(view);
		MediaTypeCollection leafUnits = new MediaTypeCollection();
		// ������ʹ�������һ���ڵ�
		for (int i = 0; i < mediaTypeCol.size(); i++) {
			MediaTypeInfo unit = mediaTypeCol.get(i);
			if (unit.getParent() != null) {
				leafUnits.add(unit);
			} else {
				subTreeNode = new DefaultKingdeeTreeNode(mediaTypeCol.get(i));
				nodeMap.put(mediaTypeCol.get(i).getId().toString(), subTreeNode);
				rootTreeNode.add(subTreeNode);
			}

		}
		// ������ʹ���������߶����������нڵ�
		for (int i = 0; i < leafUnits.size(); i++) {
			MediaTypeInfo mediaTypeInfo = leafUnits.get(i);
			subTreeNode = new DefaultKingdeeTreeNode(mediaTypeInfo);

			// ���ҵȼ������Ļ���ͣ����뵽���ӽڵ�
			if (!mediaTypeInfo.isIsLeaf()) {
				subTreeNode = new DefaultKingdeeTreeNode(leafUnits.get(i));
				nodeMap.put(leafUnits.get(i).getId().toString(), subTreeNode);

			}
			if (mediaTypeInfo.getId().toString() != null && nodeMap.containsKey(mediaTypeInfo.getParent().getId().toString())) {
				((DefaultKingdeeTreeNode) nodeMap.get(mediaTypeInfo.getParent().getId().toString())).add(subTreeNode);
			}
		}

		nodeMap = null;
		return rootTreeNode;
	}

	/**
	 * ��������������
	 * 
	 * @throws Exception
	 */
	public static void getCycleTree(KDTree treeMain) throws Exception {
		DefaultKingdeeTreeNode rootNode = createCycleNode(true);
		DefaultTreeModel model = new DefaultTreeModel(rootNode);
		treeMain.setModel(model);
		treeMain.expandAllNodes(true, rootNode);
		if (rootNode.getChildCount() > 0)
			treeMain.setSelectionNode((DefaultKingdeeTreeNode) rootNode.getChildAt(0));
		else
			treeMain.setSelectionNode(rootNode);
		treeMain.setShowsRootHandles(true);

	}

	public static DefaultKingdeeTreeNode createCycleNode(boolean includeEmployeeType) throws Exception {
		// ���ڵ�
		DefaultKingdeeTreeNode rootTreeNode = new DefaultKingdeeTreeNode("ý�����"), subTreeNode = null;
		for (int i = 0; i < CycleEnum.getEnumList().size(); i++) {
			subTreeNode = new DefaultKingdeeTreeNode(CycleEnum.getEnumList().get(i));
			rootTreeNode.add(subTreeNode);
		}

		return rootTreeNode;

	}
}
