/**
 * 
 */
package com.kingdee.eas.fdc.aimcost.client;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.EventListener;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.swing.SwingUtilities;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeModel;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTDefaultCellEditor;
import com.kingdee.bos.ctrl.kdf.table.KDTMergeManager;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.event.KDTEditListener;
import com.kingdee.bos.ctrl.kdf.table.event.KDTMouseListener;
import com.kingdee.bos.ctrl.kdf.util.style.Styles.HorizontalAlignment;
import com.kingdee.bos.ctrl.swing.KDComboBox;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.fdc.aimcost.MeasureDefaultSplitTypeSetCollection;
import com.kingdee.eas.fdc.aimcost.MeasureDefaultSplitTypeSetFactory;
import com.kingdee.eas.fdc.aimcost.MeasureDefaultSplitTypeSetInfo;
import com.kingdee.eas.fdc.aimcost.MeasureEntryCollection;
import com.kingdee.eas.fdc.aimcost.MeasureEntryInfo;
import com.kingdee.eas.fdc.aimcost.MeasureIncomeEntryCollection;
import com.kingdee.eas.fdc.aimcost.MeasureIncomeEntryInfo;
import com.kingdee.eas.fdc.aimcost.PlanIndexEntryCollection;
import com.kingdee.eas.fdc.aimcost.PlanIndexEntryInfo;
import com.kingdee.eas.fdc.aimcost.PlanIndexInfo;
import com.kingdee.eas.fdc.aimcost.PlanIndexTypeEnum;
import com.kingdee.eas.fdc.aimcost.client.AimMeasureCostEditUI.Item;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.IncomeAccountInfo;

/**
 * ������� �Ĳ�����ܱ�:����Ҫ Ԥ�������ܶ������ƷԤ�������ܶ�ϼ� 
 *
 */
public class MeasureIncomeCollectTable {

	private KDTable table = null;
	private MeasureIncomeEditUI measureIncomeEditUI = null;
	private PlanIndexInfo planIndexInfo = null;
	private PlanIndexEntryCollection entrys = null;

	public MeasureIncomeCollectTable(MeasureIncomeEditUI measureIncomeEditUI) throws EASBizException, BOSException {
		this.measureIncomeEditUI = measureIncomeEditUI;
		//isBuildPartLogic=measureIncomeEditUI.isBuildPartLogic();
		// ȡ���ݷ�ʽ�ĳɴӽ���ȡ��
		// planIndexInfo=loadData(headId);
		// entrys=getEntrys(planIndexInfo);
		this.table = new KDTable();
		table.getStyleAttributes().setLocked(true);
		// initTable();
		// fillTable();
		// setUnionData();
//		table.addKDTEditListener(new com.kingdee.bos.ctrl.kdf.table.event.KDTEditAdapter() {
//			public void editStopped(com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent e) {
//				tableEditStopped(e);
//			}
//		});
	}

	private PlanIndexEntryCollection getEntrys(PlanIndexInfo planIndexInfo) {
		//allSellArea=PlanIndexTable.getAllSellArea(planIndexInfo);
		//allBuildArea=PlanIndexTable.getAllBuildArea(planIndexInfo);
		PlanIndexEntryCollection entrys = new PlanIndexEntryCollection();
		for (int i = 0; i < planIndexInfo.getEntrys().size(); i++) {
			PlanIndexEntryInfo entry = planIndexInfo.getEntrys().get(i);
			if (entry.isIsProduct()) {
				entrys.add(entry);
			}
		}
		return entrys;
	}
//	private boolean isBuildPartLogic = false;
//	private boolean isBuildPartLogic(){
//		return isBuildPartLogic;
//	}
//	private BigDecimal allBuildArea=FDCHelper.ZERO;
//	private BigDecimal getAllBuildArea(){
//		return allBuildArea;
//	}
//	
//	private BigDecimal allSellArea=FDCHelper.ZERO;
//	private BigDecimal getAllSellArea(){
//		return allSellArea;
//	}
//	
//	private BigDecimal getSumArea(String splitType){
//		BigDecimal sum=FDCHelper.ZERO;
//		for (int i = 0; i < entrys.size(); i++) {
//			if (entrys.get(i).isIsSplit()) {
//				sum = sum.add(FDCHelper.toBigDecimal(entrys.get(i).getBigDecimal(splitType)));
//			}
//		}
//		return sum;
//	}
	public KDTable getTable() {
		return table;
	}

	private void initTable() {
		if(entrys==null) return;
		this.table.removeColumns();
		int entrysSize = entrys.size();
		//int colums = entrysSize * 3 + 6;
		int colums = entrysSize * 1 + 3;
		// this.table=new KDTable(colums,2,0);
		this.table.createBlankTable(colums, 2, 0);
		table.getColumn(0).setKey("acctNumber");
		table.getColumn(1).setKey("acctName");
		int base = 2;//��Ŀ������һ�У���������
//		for (int i = 0; i < entrysSize; i++) {
//			table.getColumn(base + i).setKey("buildAvg" + i);
//			FDCHelper.formatTableNumber(table, "buildAvg" + i);
//		}
//		table.getColumn(base + entrysSize).setKey("buildAvg");
//		FDCHelper.formatTableNumber(table, "buildAvg");
//		
//		base = base + entrysSize + 1;
//		for (int i = 0; i < entrysSize; i++) {
//			table.getColumn(base + i).setKey("avg" + i);
//			FDCHelper.formatTableNumber(table, "avg" + i);
//		}
//		table.getColumn(base + entrysSize).setKey("avg");
//		FDCHelper.formatTableNumber(table, "avg");
//
//		base = base + entrysSize + 1;
		for (int i = 0; i < entrysSize; i++) {
			table.getColumn(base + i).setKey("split" + i);
			FDCHelper.formatTableNumber(table, "split" + i);
		}
		table.getColumn(base + entrysSize).setKey("total");
		FDCHelper.formatTableNumber(table, "total");
		//table.getColumn(colums - 1).setKey("splitType");

		// title
		IRow headRow = table.getHeadRow(0);
		headRow.getStyleAttributes().setHorizontalAlign(HorizontalAlignment.CENTER);
		// headRow.getStyleAttributes().setBold(true);
		headRow.getCell("acctNumber").setValue("��Ŀ����");
		headRow.getCell("acctName").setValue("��Ŀ����");
		//headRow.getCell("buildAvg").setValue("���������λ�ɱ�");
		//headRow.getCell("avg").setValue("���������λ�ɱ�");
		headRow.getCell("total").setValue("Ԥ�������ܶ�");
		//headRow.getCell("splitType").setValue("���䷽ʽ");
		headRow = table.getHeadRow(1);
		headRow.getStyleAttributes().setHorizontalAlign(HorizontalAlignment.CENTER);
		// headRow.getStyleAttributes().setBold(true);
		for (int i = 0; i < entrysSize; i++) {
			String name = entrys.get(i).getProduct().getName();
			//headRow.getCell("buildAvg" + i).setValue(name);
			//headRow.getCell("avg" + i).setValue(name);
			headRow.getCell("split" + i).setValue(name);
			headRow.getCell("split" + i).setUserObject(entrys.get(i).getProduct().getId().toString());
		}
		//headRow.getCell("buildAvg").setValue("ƽ��");
		//headRow.getCell("avg").setValue("ƽ��");
		headRow.getCell("total").setValue("�ϼ�");
		// merge
		KDTMergeManager mm = table.getHeadMergeManager();
		// mm.setMergeMode(KDTMergeManager.FREE_MERGE);
		
		// ��Ŀ����
		mm.mergeBlock(0, 0, 1, 0);
		// ��Ŀ����
		mm.mergeBlock(0, 1, 1, 1);
		
		// Ԥ�������ܶ�
		mm.mergeBlock(0, 2, 0, 2 + entrysSize);
		
		// ���䷽ʽ
		//mm.mergeBlock(0, colums - 1, 1, colums - 1);
		// ���������λ�ɱ�
		//mm.mergeBlock(0, 2, 0, 2 + entrysSize);
		// ���������λ�ɱ�(��1����Ϊ���������λ�ɱ�����ƽ���У��ܳɱ���2��ͬ by hpw)
		//mm.mergeBlock(0, 2 + entrysSize + 1, 0, 2 + entrysSize * 2 + 1);
		// �ܳɱ�
		//mm.mergeBlock(0, 2 + entrysSize * 2 + 2, 0, colums - 2);
		// table.getColumn("splitType").setEditor(measureCostEditUI.getIndexEditor(CostAccountTypeEnum.MAIN,
		// null));
		// ����
		table.getViewManager().setFreezeView(-1, 2);
	}

	private void fillTable() {
		TreeModel incomeAcctTree = measureIncomeEditUI.getIncomeAcctTree();
		table.removeRows();
		table.setUserObject(null);
		DefaultKingdeeTreeNode root = (DefaultKingdeeTreeNode) incomeAcctTree.getRoot();
		Enumeration childrens = root.depthFirstEnumeration();
		int maxLevel = 0;
		while (childrens.hasMoreElements()) {
			DefaultMutableTreeNode node = (DefaultMutableTreeNode) childrens.nextElement();
			if (node.getUserObject() != null && node.getLevel() > maxLevel) {
				maxLevel = node.getLevel();
			}
		}
		table.getTreeColumn().setDepth(maxLevel + 1);
		for (int i = 0; i < root.getChildCount(); i++) {
			DefaultMutableTreeNode child = (DefaultMutableTreeNode) root.getChildAt(i);
			fillNode(child);
		}

//		String oprtState = measureIncomeEditUI.getOprtState();
//		if (oprtState!=null&&!oprtState.equals(OprtState.ADDNEW) && !oprtState.equals(OprtState.EDIT)) {
//			for (int i = 0; i < table.getRowCount(); i++) {
//				if (table.getCell(i, "splitType").getEditor() != null && table.getCell(i, "splitType").getEditor().getComponent() != null) {
//					table.getCell(i, "splitType").getEditor().getComponent().setEnabled(false);
//				}
//			}
//		}
	}

	private void fillNode(DefaultMutableTreeNode node) {
		IncomeAccountInfo incomeAcct = (IncomeAccountInfo) node.getUserObject();
		if (incomeAcct == null) {
//			MsgBox.showError("�ɱ���Ŀ�ļ���̫��!");
			return;
		}
		IRow row = table.addRow();
		row.setTreeLevel(node.getLevel() - 1);
		// row.getStyleAttributes().setLocked(true);
		// row.getStyleAttributes().setBackground(new Color(0xF0EDD9));
		row.setUserObject(incomeAcct);
		String longNumber = incomeAcct.getLongNumber();
		longNumber = longNumber.replace('!', '.');
		row.getCell("acctNumber").setValue(longNumber);
		row.getCell("acctName").setValue(incomeAcct.getName());
		if (node.isLeaf()) {
			loadRow(row);
		} else {
			for (int i = 0; i < node.getChildCount(); i++) {
				this.fillNode((DefaultMutableTreeNode) node.getChildAt(i));
			}
		}
	}

	private void loadRow(IRow row) {
		Object obj = row.getUserObject();
		if (!(obj instanceof IncomeAccountInfo)) {
			return;
		}
		IncomeAccountInfo incomeAcct = (IncomeAccountInfo) obj;

		String key = incomeAcct.getId().toString();
		Map measureIncomeMap = measureIncomeEditUI.getMeasureIncomeMap();
		
		// Ԥ�������ܶ�_X��Ʒ��x��Ʒ_Ԥ�������ܶ�; 
		// Ԥ�������ܶ�_�ϼƣ�X��Ʒ_Ԥ�������ܶ� �ϼ�;
		BigDecimal allTotal = FDCHelper.ZERO;
		for (int i = 0; i < entrys.size(); i++) {
			PlanIndexEntryInfo entry = entrys.get(i);

			String productKey = key + entry.getProduct().getId().toString();
			MeasureIncomeEntryCollection coll = (MeasureIncomeEntryCollection) measureIncomeMap.get(productKey);
			if (coll != null && coll.size() > 0) {
				BigDecimal total = FDCHelper.ZERO;
				for (int j = 0; j < coll.size(); j++) {
					MeasureIncomeEntryInfo info = coll.get(j);
					if (info == null)
						continue;
					total = total.add(FDCHelper.toBigDecimal(info.getAmount()));
				}
				allTotal = allTotal.add(total);
				row.getCell("split" + i).setValue(total);
			}

			if (entrys.get(i).getType() != null && entrys.get(i).getType() == PlanIndexTypeEnum.parking) {
				// ͣ������������
				continue;
			}

		}
		if (allTotal.compareTo(FDCHelper.ZERO) == 0)
			return;
		row.getCell("total").setValue(allTotal);
		

	}

	public void refresh() {
		planIndexInfo = measureIncomeEditUI.getPlanIndexTable().getPlanIndexInfo();
		entrys = getEntrys(planIndexInfo);
		measureIncomeEditUI.refreshMeasureIncomeMap();
		initTable();
		fillTable();
		setUnionData();
	}

	/**
	 * refresh all date of six table and product table
	 */

	private KDTDefaultCellEditor editor = null;

	KDTDefaultCellEditor getSplitTypeEditor() {
		if (editor != null)
			return editor;
		Object[] items = Item.SPLITITEMS;
		KDComboBox box = new KDComboBox(items);

		editor = new KDTDefaultCellEditor(box);
		return editor;
	}

	private Item getSelectItem(String key) {
		if (editor == null)
			return null;
		KDComboBox box = (KDComboBox) editor.getComponent();
		for (int i = 0; i < box.getItemCount(); i++) {
			Item item = (Item) box.getItemAt(i);

			if (key.equals(item.key)) {
				return item;
			}
		}
		return null;
	}

	/**
	 * ���ø���Ŀ������
	 * 
	 * @param table
	 * @param amountColumns
	 */
	public void setUnionData() {
		// if(true) return;
		// String[] cols = new String[] { "price", "sumPrice", "sellPart" };
		String[] cols = new String[entrys.size() * 1 + 1]; 
		for (int i = 0; i < entrys.size(); i++) {
			cols[i] = "split" + i;
			//cols[i] = "buildAvg" + i;
			//cols[entrys.size() + 1 + i] = "avg" + i;
			//cols[entrys.size()*2 + 2 + i] = "split" + i;
		}
		cols[entrys.size()] = "total";
		//cols[entrys.size()] = "buildAvg";
		//cols[entrys.size() * 2 + 1] = "avg";
		//cols[entrys.size() * 3 + 2] = "total";
		// cols=new String[]{"total"};
		for (int i = 0; i < table.getRowCount(); i++) {
			IRow row = table.getRow(i);
			if (row.getUserObject() instanceof IncomeAccountInfo) {
				IncomeAccountInfo acct = (IncomeAccountInfo) row.getUserObject();
				if (acct.isIsLeaf())
					continue;
				// ���û�����
				int level = row.getTreeLevel();
				List aimRowList = new ArrayList();
				for (int j = i + 1; j < table.getRowCount(); j++) {
					IRow rowAfter = table.getRow(j);
					if (rowAfter.getTreeLevel() <= level) {
						break;
					}
					if (rowAfter.getUserObject() instanceof IncomeAccountInfo) {
						acct = (IncomeAccountInfo) rowAfter.getUserObject();
						if (acct.isIsLeaf()) {
							aimRowList.add(rowAfter);
						}
					}

				}
				for (int j = 0; j < cols.length; j++) {
					BigDecimal sum = null;
					for (int rowIndex = 0; rowIndex < aimRowList.size(); rowIndex++) {
						IRow rowAdd = (IRow) aimRowList.get(rowIndex);
						Object value = rowAdd.getCell(cols[j]).getValue();
						if (value != null) {
							sum = FDCHelper.toBigDecimal(sum, 2).add(FDCHelper.toBigDecimal(value, 2));
						}
					}
					row.getCell(cols[j]).setValue(sum);
				}
			}
		}
		
		SwingUtilities.invokeLater(new Runnable(){
			public void run() {
				try {
					AimCostClientHelper.setTotalCostRow(getTable(),getColumns());
				} catch (Exception e) {
					measureIncomeEditUI.handUIException(e);
				}
			}
		});
		//afterSetUnionData();
	}
	
	//
	private void afterSetUnionData(){
		//BigDecimal allSellArea = getAllSellArea();
		//BigDecimal allBuildArea = getAllBuildArea();
		for (int i = 0; i < table.getRowCount(); i++) {
			IRow row = table.getRow(i);
			if (row.getUserObject() instanceof IncomeAccountInfo) {
				BigDecimal allTotal = (BigDecimal)row.getCell("total").getValue();//�ܳɱ�_�ϼƣ������๫̯���ڼ�ѣ�
				if(allTotal==null){
					continue;
				}
//				if (FDCHelper.toBigDecimal(allSellArea).compareTo(FDCHelper.ZERO) != 0) {
//					BigDecimal avg = allTotal.divide(allSellArea, 2, BigDecimal.ROUND_HALF_UP);
//					row.getCell("avg").setValue(avg);
//				}
//				if (FDCHelper.toBigDecimal(allBuildArea).compareTo(FDCHelper.ZERO) != 0) {
//					BigDecimal avg = allTotal.divide(allBuildArea, 2, BigDecimal.ROUND_HALF_UP);
//					row.getCell("buildAvg").setValue(avg);
//				}
			}
		}
	}
	
//	private void tableEditStopped(com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent e) {
//		Object oldValue = e.getOldValue();
//		Object newValue = e.getValue();
//		if(oldValue==null&&newValue==null){
//			return;
//		}
//		if (oldValue != null && newValue != null && oldValue.equals(newValue)) {
//			return;
//		}
//		int rowIndex = e.getRowIndex();
//		int columnIndex = e.getColIndex();
//		IRow row = table.getRow(rowIndex);
//		if (table.getColumnKey(columnIndex).equals("splitType")) {
//			Object obj = row.getUserObject();
//			if (obj instanceof CostAccountInfo && ((CostAccountInfo) obj).getType() == CostAccountTypeEnum.SIX) {
//				// ȡ�÷�̯��׼����з�̯-������
//				splitSIX(row);
//				setUnionData();
//			}
//		}
//
//		if (table.getColumnKey(columnIndex).startsWith("split") 
//				&& !table.getColumnKey(columnIndex).equals("split")
//				&&!table.getColumnKey(columnIndex).equals("splitType")) {
//			// ����ָ����̯ʱ��¼��
//			Object obj = row.getCell("splitType").getValue();
//			if (obj instanceof Item && ((Item) obj).key.equals("man")) {
//				row.getCell(columnIndex).setValue(row.getCell(columnIndex).getValue());
//				int index = Integer.parseInt(table.getColumnKey(columnIndex).substring("split".length()));
//				BigDecimal sellArea = entrys.get(index).getSellArea();
//				BigDecimal buildArea = entrys.get(index).getBuildArea();
//				if (FDCHelper.toBigDecimal(sellArea).signum() != 0) {
//					row.getCell("avg" + index).setValue(FDCHelper.toBigDecimal(newValue).divide(sellArea, 2, BigDecimal.ROUND_HALF_UP));
//				}
//				if (FDCHelper.toBigDecimal(buildArea).signum() != 0) {
//					row.getCell("buildAvg" + index).setValue(FDCHelper.toBigDecimal(newValue).divide(buildArea, 2, BigDecimal.ROUND_HALF_UP));
//				}
//				setUnionData();
//			}
//		}
//		
//		measureIncomeEditUI.setDataChange(true);
//	}

//	public Map getSplitTypes() {
//		Map map = new HashMap();
//		for (int i = 0; i < table.getRowCount(); i++) {
//			IRow row = table.getRow(i);
//			Object value = row.getCell("splitType").getValue();
//			String splitType = null;
//			if (value instanceof Item) {
//				Item item = (Item) value;
//				splitType = item.key;
//			}
//			if (splitType == null)
//				continue;
//
//			Object obj = row.getUserObject();
//			if (obj instanceof CostAccountInfo) {
//
//				CostAccountInfo acct = (CostAccountInfo) obj;
//				if (acct.isIsLeaf() && acct.getType() == CostAccountTypeEnum.SIX) {
//					if (splitType.equals("man")) {
//						for (int k = 0; k < entrys.size(); k++) {
//							if (!entrys.get(k).isIsSplit()) {
//								continue;
//							}
//							// ���ڲ�Ʒ����ˣ�����ṹû���½�����������������ʱ���򵥴���
//							if (row.getCell("split" + k) == null)
//								break;
//							Object v = row.getCell("split" + k).getValue();
//							String productId = entrys.get(k).getProduct().getId().toString();
//							if (productId == null) {
//								break;
//							}
//
//							splitType = splitType + "[]" + productId + "|" + FDCHelper.toBigDecimal(v).toString();
//						}
//					}
//					map.put(acct.getId().toString(), splitType);
//				}
//			}
//		}
//		return map;
//	}

//	private void setDefaultSplitType(IRow row) {
//		Object obj = row.getUserObject();
//		if (obj instanceof CostAccountInfo && ((CostAccountInfo) obj).isIsLeaf()) {
//			Object key = getDefaultCostAcctMap().get(((CostAccountInfo) obj).getLongNumber());
//			if (key == null) {
//				row.getCell("splitType").setValue(null);
//			} else {
//				row.getCell("splitType").setValue(getSelectItem(key.toString()));
//			}
//		}
//	}

//	private HashMap defaultCostAcctMap = null;
//
//	private HashMap getDefaultCostAcctMap() {
//		if (defaultCostAcctMap == null) {
//			defaultCostAcctMap = new HashMap();
//			EntityViewInfo view = new EntityViewInfo();
//			view.getSelector().add("costAccount.longNumber");
//			view.getSelector().add("splitType");
//			try {
//				MeasureDefaultSplitTypeSetCollection c = MeasureDefaultSplitTypeSetFactory.getRemoteInstance().getMeasureDefaultSplitTypeSetCollection(view);
//				for (Iterator iter = c.iterator(); iter.hasNext();) {
//					MeasureDefaultSplitTypeSetInfo info = (MeasureDefaultSplitTypeSetInfo) iter.next();
//					defaultCostAcctMap.put(info.getCostAccount().getLongNumber(), info.getSplitType());
//
//				}
//			} catch (Exception e) {
//				measureIncomeEditUI.handUIException(e);
//			}
//		}
//
//		return defaultCostAcctMap;
//	}

//	private int getProductColumnIndex(String productId) {
//		IRow headRow = table.getHeadRow(0);
//		for (int i = 0; i < this.getTable().getColumnCount(); i++) {
//			if (headRow.getCell(i) != null && headRow.getCell(i).getValue() != null && headRow.getCell(i).getValue().equals(productId)) {
//				return i;
//			}
//		}
//		return -1;
//	}

	private String getColumnProdcutId(String key) {
		IRow headRow = table.getHeadRow(1);
		if (headRow.getCell(key) != null && headRow.getCell(key).getUserObject() != null) {
			return (String) headRow.getCell(key).getUserObject();
		}
		return null;
	}

	/**
	 * ������๫̯��Ŀ
	 * 
	 * @param splitType
	 * @param row
	 */
//	private void splitSIX(IRow row) {
//		Object value = row.getCell("splitType").getValue();
//
//		if (!(value instanceof Item)) {
//			return;
//		}
//
//		String splitType = ((Item) value).key;
//
//		value = row.getCell("total").getValue();
//		BigDecimal total = FDCHelper.toBigDecimal(value);
//		if (total.signum() == 0||splitType==null) {
//			for (int i = 0; i < entrys.size(); i++) {
//				row.getCell("buildAvg"+i).setValue(null);
//				row.getCell("avg"+i).setValue(null);
//				row.getCell("split"+i).setValue(null);
//			}
//			row.getCell("buildAvg").setValue(null);
//			row.getCell("avg").setValue(null);
//			row.getCell("total").setValue(null);
//			return;
//		}
//		
//		boolean  isManSplit=isManSplit(splitType);
//
//
//		if(isManSplit){
//			manSplit(splitType, row);
//			for (int i = 0; i < entrys.size(); i++) {
//				row.getCell("buildAvg"+i).setValue(null);
//				row.getCell("avg" + i).setValue(null);
//				row.getCell("split" + i).setValue(null);
//			}
//		}else{
//			BigDecimal sum = getSumArea(splitType);
//			if (sum.signum()>0) {
//				BigDecimal tmp = FDCHelper.ZERO;
//				int lastIndex = 0;// ���һ���ֵ̯
//				for (int i = 0; i < entrys.size(); i++) {
//					if (!entrys.get(i).isIsSplit()) {
//						row.getCell("split" + i).setValue(null);
//						row.getCell("avg" + i).setValue(null);
//						row.getCell("buildAvg"+i).setValue(null);
//						if (i == entrys.size() - 1) {
//							// ���һ���̯�Ļ�,����̯�Ĳ�ֵ�鵽֮ǰ��̯�����һ��
//							BigDecimal dif = total.subtract(tmp);
//							if(lastIndex>0){
//								BigDecimal amount = FDCHelper.toBigDecimal(row.getCell(lastIndex).getValue());
//								amount = amount.add(dif);
//								row.getCell(lastIndex).setValue(amount);
//							}
//						}
//						continue;
//					}
//					BigDecimal splitAmt = FDCHelper.ZERO;
//					BigDecimal amt = FDCHelper.ZERO;
//					if (i == entrys.size() - 1) {
//						// ���һ����̯
//						splitAmt = total.subtract(tmp);
//					} else {
//						lastIndex=table.getColumnIndex("split"+i);
//						amt = entrys.get(i).getBigDecimal(splitType);
//						splitAmt = FDCHelper.toBigDecimal(amt).multiply(total).divide(sum, 2, BigDecimal.ROUND_HALF_UP);
//						tmp = tmp.add(splitAmt);
//					}
//					row.getCell("split" + i).setValue(splitAmt);
//
//					amt = FDCHelper.toBigDecimal(entrys.get(i).getSellArea());
//					if (amt.compareTo(FDCHelper.ZERO) == 0)
//						continue;
//					BigDecimal avgAmt = splitAmt.divide(amt, 2, BigDecimal.ROUND_HALF_UP);
//					row.getCell("avg" + i).setValue(avgAmt);
//					
//					amt = FDCHelper.toBigDecimal(entrys.get(i).getBuildArea());
//					if (amt.compareTo(FDCHelper.ZERO) == 0)
//						continue;
//					avgAmt = splitAmt.divide(amt, 2, BigDecimal.ROUND_HALF_UP);
//					row.getCell("buildAvg" + i).setValue(avgAmt);
//
//				}
//
//			} else {
//				for (int i = 0; i < entrys.size(); i++) {
//					row.getCell("buildAvg" + i).setValue(null);
//					row.getCell("avg" + i).setValue(null);
//					row.getCell("split" + i).setValue(null);
//				}
//			}
//			BigDecimal sumSellArea = getAllSellArea();
//			BigDecimal sumBuildArea = getAllBuildArea();
//			if (sumSellArea.signum() != 0) {
//				row.getCell("avg").setValue(total.divide(sumSellArea, 2, BigDecimal.ROUND_HALF_UP));
//			}
//			if (sumBuildArea.signum() != 0) {
//				row.getCell("buildAvg").setValue(total.divide(sumBuildArea, 2, BigDecimal.ROUND_HALF_UP));
//			}
//		}
//
//	}
	
//	private void manSplit(String splitType,IRow row){
//		String[] split = splitType.split("\\[\\]");
//		row.getCell("splitType").setValue(getSelectItem(split[0]));
//		if (split.length > 1) {
//			HashMap map = new HashMap();
//			for (int i = 1; i < split.length; i++) {
//				String s[] = split[i].split("\\|");
//
//				if (s.length == 2) {
//					map.put(s[0], s[1]);
//				}
//			}
//			for (int i = 0; i < entrys.size(); i++) {
//				BigDecimal splitAmt = FDCHelper.toBigDecimal(map.get(getColumnProdcutId("split" + i)));
//				row.getCell("split" + i).setValue(splitAmt);
//				row.getCell("split" + i).getStyleAttributes().setLocked(false);
//
//				BigDecimal amt = FDCHelper.toBigDecimal(entrys.get(i).getSellArea());
//				if (amt.compareTo(FDCHelper.ZERO) == 0)
//					continue;
//				BigDecimal avgAmt = splitAmt.divide(amt, 2, BigDecimal.ROUND_HALF_UP);
//				row.getCell("avg" + i).setValue(avgAmt);
//				
//				amt = FDCHelper.toBigDecimal(entrys.get(i).getBuildArea());
//				if (amt.compareTo(FDCHelper.ZERO) == 0)
//					continue;
//				avgAmt = splitAmt.divide(amt, 2, BigDecimal.ROUND_HALF_UP);
//				row.getCell("buildAvg" + i).setValue(avgAmt);
//			}
//		}
//
//		BigDecimal sumSellArea = FDCHelper.toBigDecimal(getAllSellArea());
//		BigDecimal sumBuildArea = FDCHelper.toBigDecimal(getAllBuildArea());
//		BigDecimal total = FDCHelper.toBigDecimal(row.getCell("total").getValue());
//		if (sumSellArea.compareTo(FDCHelper.ZERO) != 0) {
//			row.getCell("avg").setValue(total.divide(sumSellArea, 2, BigDecimal.ROUND_HALF_UP));
//		}
//		if (sumBuildArea.compareTo(FDCHelper.ZERO) != 0) {
//			row.getCell("buildAvg").setValue(total.divide(sumBuildArea, 2, BigDecimal.ROUND_HALF_UP));
//		}
//		
//		//��ʼ���ؼ���̯��ʽ�ı��ʱ��ͳһ����״̬
//		for (int i = 0; i < entrys.size(); i++) {
//			//�鿴״̬�����޸�
//			String oprtState = measureIncomeEditUI.getOprtState();
//			if (oprtState!=null&&!oprtState.equals(OprtState.ADDNEW) && !oprtState.equals(OprtState.EDIT)) {
//				row.getCell("split" + i).getStyleAttributes().setLocked(true);
//				continue;
//			}
//			if (!entrys.get(i).isIsSplit()) {
//				row.getCell("split" + i).getStyleAttributes().setLocked(true);
//			}else{
//				row.getCell("split" + i).getStyleAttributes().setLocked(false);
//			}
//		}
//	}
	
//	private boolean isManSplit(String splitType){
//		return splitType.startsWith("man");
//	}
	
	private String[] getColumns(){
		int size=1;
		if(entrys!=null&&entrys.size()>0){
			size=size+entrys.size();
		}
		String[] columns=new String[size];
		columns[0]="total";
		for(int i=1;i<size;i++){
			columns[i]="split"+(i-1);
		}
		
		return columns;
	}
	
	void clear(){
		EventListener[] listeners = this.table.getListeners(KDTEditListener.class);
		for(int i=0;i<listeners.length;i++){
			this.table.removeKDTEditListener((KDTEditListener)listeners[i]);
		}
		
		listeners = this.table.getListeners(KDTMouseListener.class);
		for(int i=0;i<listeners.length;i++){
			this.table.removeKDTMouseListener((KDTMouseListener)listeners[i]);
		}
		this.table.setBeforeAction(null);
		this.table.setAfterAction(null);
		this.table.removeAll();
		this.editor=null;
		this.table=null;
		this.measureIncomeEditUI=null;
		this.planIndexInfo = null;
		this.entrys = null;
	}
	
}
