/**
 * output package name
 */
package com.kingdee.eas.fdc.invite.client;

/**
 * @author Ф쭱�
 */
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JMenuItem;
import javax.swing.SwingUtilities;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.table.ICell;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTMenuManager;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.swing.KDPopupMenu;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.invite.DescriptionEnum;
import com.kingdee.eas.fdc.invite.NewListingEntryCollection;
import com.kingdee.eas.fdc.invite.NewListingEntryFactory;
import com.kingdee.eas.fdc.invite.NewListingEntryInfo;
import com.kingdee.eas.fdc.invite.NewListingFactory;
import com.kingdee.eas.fdc.invite.NewListingInfo;
import com.kingdee.eas.fdc.invite.NewListingPageCollection;
import com.kingdee.eas.fdc.invite.NewListingPageInfo;
import com.kingdee.eas.fdc.invite.NewListingValueCollection;
import com.kingdee.eas.fdc.invite.NewListingValueFactory;
import com.kingdee.eas.fdc.invite.NewListingValueInfo;
import com.kingdee.eas.fdc.invite.NewQuotingPriceFactory;
import com.kingdee.eas.fdc.invite.NewQuotingPriceInfo;
import com.kingdee.eas.framework.config.AbstractTablePreferencesHelper;

public class QuotingPriceModifyAnalyseUI extends AbstractQuotingPriceModifyAnalyseUI {
	private static final Logger logger = CoreUIObject.getLogger(QuotingPriceModifyAnalyseUI.class);

	private static BigDecimal zero = FDCHelper.ZERO;

	List tables = null;

	private NewListingInfo listingInfo;

	private NewQuotingPriceInfo priceContentInfo;

	public QuotingPriceModifyAnalyseUI() throws Exception {
		super();
	}


	public void onLoad() throws Exception {
		// set to false, in order to disable table right click Menu
		setQueryPreference(false);
		super.onLoad();
		init();
		// btnRefresh.setVisible(false);
		// btnRefresh.getAction().setEnabled(false);
		btnPrePrint.getAction().setEnabled(true);
		btnPrePrint.setEnabled(true);
		btnPrint.getAction().setEnabled(true);
		btnPrint.setEnabled(true);
	}

	private void init() throws Exception {
		this.tabbedPane.removeAll();
		initPrivateData();
		initSheet();
		InviteHelper.setAotuHeight(this.tabbedPane);
	}

	/**
	 * ���ܣ���ʼ������
	 *
	 * @throws EASBizException
	 * @throws BOSException
	 */

	public void initPrivateData() throws EASBizException, BOSException {
		String inviteListingId = (String) getUIContext().get("inviteListingId");
		SelectorItemCollection sic = new SelectorItemCollection();
		sic.add(new SelectorItemInfo("*"));
		sic.add(new SelectorItemInfo("pages.*"));
		sic.add(new SelectorItemInfo("pages.pageHead.*"));
		listingInfo = NewListingFactory.getRemoteInstance().getNewListingInfo(new ObjectUuidPK(inviteListingId), sic);

		String quotingPriceContentId = (String) getUIContext().get("quotingPriceContentId");

		SelectorItemCollection selectorItemCollection = new SelectorItemCollection();
		selectorItemCollection.add(new SelectorItemInfo("*"));
		selectorItemCollection.add(new SelectorItemInfo("supplier.*"));
		priceContentInfo = NewQuotingPriceFactory.getRemoteInstance().getNewQuotingPriceInfo(new ObjectUuidPK(quotingPriceContentId), selectorItemCollection);
	}

	/**
	 * ���ܣ���ʼ��ʼ��ҳǩ
	 *
	 * @throws EASBizException
	 * @throws BOSException
	 */
	private void initSheet() throws BOSException {
		NewListingPageCollection pages = listingInfo.getPages();
		tables = new ArrayList(pages.size());
		for (int i = 0; i < pages.size(); i++) {
			NewListingPageInfo pageInfo = pages.get(i);
			KDTable table = new KDTable();
			tables.add(i, table);
			table.setName(pageInfo.getPageHead().getName());

			initSheetHead(table, pageInfo.getPageHead().getName());
			initSheetBody(table, pageInfo);
			tabbedPane.add(table);
			table.getStyleAttributes().setLocked(true);
		}
	}

	/**
	 * ���ܣ���ʼ��ʼ��ҳǩͷ
	 *
	 * @param table
	 * @param sheetInfo
	 * @throws BOSException
	 */
	private void initSheetHead(KDTable table, String name) throws BOSException {
		final int row = 0;
		int col = 0;

		table.addHeadRow();
		table.addColumn().setKey("k");
		table.getHeadRow(row).getCell(col++).setValue("���");
		table.addColumn().setKey("2");
		table.getColumn("2").getStyleAttributes().setHided(true);
		table.getHeadRow(row).getCell(col++).setValue("��Ŀ����");
		table.addColumn().getStyleAttributes().setWrapText(true);
		table.getHeadRow(row).getCell(col++).setValue("��Ŀ����");
		table.addColumn().setKey("4");
		table.getHeadRow(row).getCell(col++).setValue("��λ");
		table.addColumn().setKey("7");
		table.getHeadRow(row).getCell(col++).setValue("�ܹ�����");
		table.addColumn().setKey("8");
		table.getHeadRow(row).getCell(col++).setValue("ԭʼ���ۣ�Ԫ��");
		table.addColumn().setKey("9");
		table.getHeadRow(row).getCell(col++).setValue("�������ۣ�Ԫ��");
		table.addColumn().setKey("`");
		table.getHeadRow(row).getCell(col++).setValue("��ۣ�Ԫ��");
		table.addColumn().setKey("0");
		table.getHeadRow(row).getCell(col++).setValue("������Ԫ��");
		table.addColumn().setKey(".");
		table.getHeadRow(row).getCell(col++).setValue("����ԭ��");

		table.checkParsed();

		FDCHelper.formatTableNumber(table, new int[] { 4, 5, 6, 7, 8 });

		table.setName(name + "(" + priceContentInfo.getSupplier().getName() + ")");

		for (int i = 0; i < table.getColumnCount(); i++) {
			table.getColumn(i).setWidth(120);

		}
	}

	/**
	 * ���ܣ���ʼ��ʼ��ҳǩbody
	 *
	 * @param table
	 * @param sheetInfo
	 * @throws BOSException
	 */
	public void initSheetBody(KDTable table, NewListingPageInfo sheetInfo) throws BOSException {

		EntityViewInfo viewInfo = new EntityViewInfo();
		FilterInfo filterInfo = new FilterInfo();
		filterInfo.appendFilterItem("head", sheetInfo.getId().toString());
		filterInfo.appendFilterItem("isLeaf", "1");
		viewInfo.getSelector().add("item.*");
		viewInfo.getSelector().add("*");
		viewInfo.setFilter(filterInfo);
		NewListingEntryCollection entryCollection = NewListingEntryFactory.getRemoteInstance().getNewListingEntryCollection(viewInfo);
		Map myMap = null;
		if (entryCollection.size() > 0) {
			myMap = new HashMap(100);
			viewInfo = new EntityViewInfo();
			filterInfo = new FilterInfo();
			filterInfo.appendFilterItem("entry.head", sheetInfo.getId().toString());
			filterInfo.appendFilterItem("quotingPrice", priceContentInfo.getId().toString());
			filterInfo.appendFilterItem("quotingPrice", null);
			filterInfo.appendFilterItem("entry.isLeaf", "1");
			filterInfo.setMaskString("(#0 and (#1 or #2) and #3)");
//			filterInfo.setMaskString("(#0 and (#1 or #2))");
			
			viewInfo.setFilter(filterInfo);
			viewInfo.getSelector().add("*");
			viewInfo.getSelector().add("entry.id");
			viewInfo.getSelector().add("column.headColumn.*");
			NewListingValueCollection valueCollection = NewListingValueFactory.getRemoteInstance().getNewListingValueCollection(viewInfo);
			for (int i = 0; i < valueCollection.size(); i++) {
				NewListingValueInfo value = valueCollection.get(i);
				if (value.getColumn().getHeadColumn().getProperty().equals(DescriptionEnum.ProjectAmtSum)) {
					myMap.put("�ܹ�����|" + value.getEntry().getId(), value.getAmount());
				} else if (value.getColumn().getHeadColumn().getProperty().equals(DescriptionEnum.TotalPriceSum)) {
					if (value.getType() != null)
						myMap.put(value.getType().toString() + "|" + value.getEntry().getId(), value.getAmount());
				} else if ("��λ".equals(value.getColumn().getHeadColumn().getName())) {
					myMap.put("��λ|" + value.getEntry().getId(), value.getText());
				}
			}
		}
		// ��ʼһ��һ�е���
		int row = 0;// ���
		for (int i = 0; i < entryCollection.size(); i++) {
			NewListingEntryInfo entry = entryCollection.get(i);
			int j = 0;
			ICell cell = null;
			Object value = null;
			String key = entry.getId().toString();
			String modifyReason = null;
			BigDecimal srcPrice = (BigDecimal) myMap.get("ԭʼ����|" + key);
			BigDecimal modifyPrice = (BigDecimal) myMap.get("��������|" + key);
			if (srcPrice == null)
				srcPrice = zero;
			if (modifyPrice == null)
				modifyPrice = zero;
			if (srcPrice.floatValue() > modifyPrice.floatValue()) {
				modifyReason = "ԭʼ���۸�";
			} else if (srcPrice.floatValue() < modifyPrice.floatValue()) {
				modifyReason = "ԭʼ���۵�";
			} 
			else{
				continue;
			}
				// ���
			table.addRow();

			cell = table.getCell(row, j++);
			cell.setValue((row + 1) + "");
			// ��Ŀ����
			cell = table.getCell(row, j++);
			cell.setValue(entry.getItem() != null ? entry.getItem().getNumber() : entry.getItemNumber());
			// ��Ŀ����
			cell = table.getCell(row, j++);
			cell.setValue(entry.getItemName());
			// ��λ
			cell = table.getCell(row, j++);
			value = myMap.get("��λ|" + key);
			cell.setValue(value);
			// �ܹ�����
			cell = table.getCell(row, j++);
			value = myMap.get("�ܹ�����|" + key);
			cell.setValue(value);
			// ԭʼ����
			cell = table.getCell(row, j++);
			cell.setValue(srcPrice);
			// ��������
			cell = table.getCell(row, j++);
			cell.setValue(modifyPrice);
			// ���
			cell = table.getCell(row, j++);
			TableScriptHelper.setTableSubFormalu(cell, table.getCell(row, cell.getColumnIndex() - 1), table.getCell(row, cell.getColumnIndex() - 2));
			// cell.setExpressions("=G" + row + "-F" + row);
			// �������
			cell = table.getCell(row, j++);
			TableScriptHelper.setTableMultiplyFormalu(cell, table.getCell(row, cell.getColumnIndex() - 1), table.getCell(row, cell.getColumnIndex() - 4));
			// cell.setExpressions("=E" + row + "*H" + row );
			// ����ԭ��
			cell = table.getCell(row, j++);
			cell.setValue(modifyReason);
			row++;
		}

		table.addRow();
		IRow irow = table.addRow();
		irow.getCell(1).setValue("��������ܼ�");
		BigDecimal sum = FDCHelper.ZERO;
		int tableRowCount = table.getRowCount();
		for (int j = 0; j < tableRowCount - 2; j++) {
			sum = sum.add((BigDecimal) table.getRow(j).getCell(8).getValue());
		}
		irow.getCell(8).setValue(sum);
		table.getMergeManager().mergeBlock(tableRowCount - 2, 0, tableRowCount - 2, 9);
		table.getMergeManager().mergeBlock(tableRowCount - 1, 0, tableRowCount - 1, 7);
	}

	public void actionExportExcel_actionPerformed(ActionEvent e) throws Exception {
		InviteHelper.exportToExcel(tables, true);
	}

	public void actionPrint_actionPerformed(ActionEvent e) throws Exception {
		KDTable table = (KDTable) tabbedPane.getSelectedComponent();
		InviteHelper.setPrintTitle(table, "�ۺϵ���������ϸ��", 0);
		table.getPrintManager().print();
	}

	public void actionRefresh_actionPerformed(ActionEvent e) throws Exception {
		init();
	}

	public void actionPrePrint_actionPerformed(ActionEvent e) throws Exception {
		KDTable table = (KDTable) tabbedPane.getSelectedComponent();
		InviteHelper.setPrintTitle(table, "�ۺϵ���������ϸ��", 0);
		table.getPrintManager().printPreview();
	}

}
