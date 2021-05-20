/**
 * output package name
 */
package com.kingdee.eas.fdc.invite.client;

import java.awt.event.ActionEvent;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.table.ICell;
import com.kingdee.bos.ctrl.kdf.table.IColumn;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.swing.KDTabbedPane;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.invite.AssessSetting;
import com.kingdee.eas.fdc.invite.DescriptionEnum;
import com.kingdee.eas.fdc.invite.HeadColumnInfo;
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
import com.kingdee.eas.fdc.invite.NewQuotingPriceCollection;
import com.kingdee.eas.fdc.invite.NewQuotingPriceFactory;
import com.kingdee.eas.fdc.invite.NewQuotingPriceInfo;
import com.kingdee.eas.fdc.invite.QuoteStandEnum;
import com.kingdee.eas.fdc.invite.QuotingPriceTypeEnum;

public class QuotingPriceOverRangeUI extends AbstractQuotingPriceOverRangeUI {
	/**
	 *
	 */
	private static final long serialVersionUID = -3526375754504501541L;

	private static final Logger logger = CoreUIObject.getLogger(QuotingPriceOverRangeUI.class);

	List tables = new ArrayList(10);

	KDTabbedPane tablePane = kDTabbedPane1;

	private NewListingInfo listingInfo;

	private NewQuotingPriceInfo priceContentInfo;

	private AssessSetting setting;

	private static BigDecimal zero = FDCHelper.ZERO;

	NewQuotingPriceCollection quotingPrices = null;

	Map sheetsMap = new HashMap(100);

	Map standPriceMap;// ��׼�ۼ���,��entryIDΪkey,amountΪvalue

	Map unitMap;// ��λ

	public QuotingPriceOverRangeUI() throws Exception {
		super();
	}

	public void onLoad() throws Exception {
		super.onLoad();
		init();
		super.printAction.setEnabled(true);
		super.prePrintAction.setEnabled(true);
		super.kDWorkButton2.setEnabled(true);
	}

	private void init() throws Exception {
		tablePane.removeAll();
		tables.clear();
		sheetsMap.clear();
		// standPriceMap.clear();
		// unitMap.clear();
		initPrivateData();
		initSheet();
		initOverRangeData();
		// this.repaint();
		InviteHelper.setAotuHeight(tablePane);
	}

	/**
	 * ���ܣ���ʼ������
	 *
	 * @throws EASBizException
	 * @throws BOSException
	 * @throws SQLException
	 */

	public void initPrivateData() throws EASBizException, BOSException, SQLException {
		String quotingPriceContentId = (String) getUIContext().get("quotingPriceContentId");
		SelectorItemCollection selectorItemCollection = new SelectorItemCollection();
		selectorItemCollection.add(new SelectorItemInfo("*"));
		selectorItemCollection.add(new SelectorItemInfo("supplier.*"));
		priceContentInfo = NewQuotingPriceFactory.getRemoteInstance().getNewQuotingPriceInfo(new ObjectUuidPK(quotingPriceContentId), selectorItemCollection);

		String inviteListingId = (String) getUIContext().get("inviteListingId");
		SelectorItemCollection sic = new SelectorItemCollection();
		sic.add(new SelectorItemInfo("*"));
		sic.add(new SelectorItemInfo("pages.*"));
		sic.add(new SelectorItemInfo("pages.pageHead.*"));

		listingInfo = NewListingFactory.getRemoteInstance().getNewListingInfo(new ObjectUuidPK(inviteListingId), sic);
		setting = new AssessSetting(listingInfo);
		quotingPrices = getQuotingPrices();
		standPriceMap = getEntryCollection(setting.getStand());
		// unitMap = getUnitCollection();
	}

	/**
	 * ��ȡ���嵥������Ͷ��
	 *
	 * @return
	 * @throws BOSException
	 */
	private NewQuotingPriceCollection getQuotingPrices() throws BOSException {
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		view.setFilter(filter);
		view.getSelector().add("*");
		view.getSelector().add("supplier.*");
		String inviteListingId = (String) this.getUIContext().get("inviteListingId");
		filter.getFilterItems().add(new FilterItemInfo("listing.id", inviteListingId));
		filter.getFilterItems().add(new FilterItemInfo("hasEffected", Boolean.TRUE));
		return NewQuotingPriceFactory.getRemoteInstance().getNewQuotingPriceCollection(view);
	}

	/**
	 * ���ܣ���ʼ��ҳǩͷ
	 *
	 * @throws EASBizException
	 * @throws BOSException
	 * @throws SQLException
	 */
	public void initSheet() throws BOSException, SQLException {
		NewListingPageCollection pages = listingInfo.getPages();
		for (int i = 0; i < pages.size(); i++) {

			NewListingPageInfo sheetInfo = pages.get(i);
			if (!sheetInfo.getPageHead().getName().equals(FDCExcelImportExportUtil.sheetName_ProjectDescription)
					&& !sheetInfo.getPageHead().getName().equals(FDCExcelImportExportUtil.sheetName_ProjectGather)) {
				// ȡ�ø�Ҷǩ����Ŀ��׼�ۼ���
				KDTable table = new KDTable();
				tables.add(table);
				table.setName(sheetInfo.getPageHead().getName());
				final int row = 0;
				int col = 0;

				table.addHeadRow();
				table.addColumn().setKey("1");
				table.getHeadRow(row).getCell(col++).setValue("���");
				table.addColumn().setKey("2");
				table.getColumn("2").getStyleAttributes().setHided(true);
				table.getHeadRow(row).getCell(col++).setValue("��Ŀ����");
				IColumn c = table.addColumn();
				c.setKey("bt");
				c.getStyleAttributes().setWrapText(true);
				table.getHeadRow(row).getCell(col++).setValue("��Ŀ����");
				table.addColumn().setKey("3");
				table.getHeadRow(row).getCell(col++).setValue("��λ");
				table.addColumn().setKey("4");
				table.getHeadRow(row).getCell(col++).setValue("������");// ������С��
				table.addColumn().setKey("0");
				table.getHeadRow(row).getCell(col++).setValue("�쳣����");
				table.addColumn().setKey("5");
				table.getHeadRow(row).getCell(col++).setValue("��׼���ۣ�Ԫ��");
				table.addColumn().setKey("6");
				table.getHeadRow(row).getCell(col++).setValue("Ͷ�굥�ۣ�Ԫ��");
				table.addColumn().setKey("7");
				table.getHeadRow(row).getCell(col++).setValue("������Χ");
				table.addColumn().setKey("8");
				table.getHeadRow(row).getCell(col++).setValue("������Ԫ��");
				table.addColumn().setKey("9");
				table.getHeadRow(row).getCell(col++).setValue("100%��׼");

				table.checkParsed();
				sheetsMap.put(table.getName(), table);
				tablePane.add(table, table.getName() + "(" + priceContentInfo.getSupplier().getName() + ")");
				table.getStyleAttributes().setLocked(true);
				FDCHelper.formatTableNumber(table, new int[] { 4, 6, 7, 8, 9 });

				// for (int j = 0; j < table.getColumnCount(); j++) {
				// table.getColumn(j).setWidth(120);
				//
				// }
			}
		}

	}

	/**
	 * ��ȡ��Ŀ�Ļ�׼�ۼ���
	 *
	 * @param page
	 * @param columnId
	 * @param stand
	 * @return
	 * @throws BOSException
	 * @throws SQLException
	 */
	private Map getEntryCollection(QuoteStandEnum stand) throws BOSException, SQLException {
		// String sql = "";
		// // ��ѯ�ۺϵ���С��
		Map map = new HashMap(100);

		// ����ͱ��
		if (setting.getStand() == QuoteStandEnum.BidderMinPrice) {
			for (int i = 0; i < listingInfo.getPages().size(); i++) {
				EntityViewInfo view = new EntityViewInfo();
				FilterInfo filter = new FilterInfo();
				SelectorItemCollection selector = view.getSelector();
				view.setFilter(filter);

				NewListingPageInfo page = listingInfo.getPages().get(i);
				filter.getFilterItems().add(new FilterItemInfo("head.id", page.getId().toString()));
				filter.getFilterItems().add(new FilterItemInfo("isLeaf", Boolean.TRUE));
				selector.add("id");
				view.getSorter().add(new SorterItemInfo("seq"));
				NewListingEntryCollection entryCollection = NewListingEntryFactory.getRemoteInstance().getNewListingEntryCollection(view);

				for (int j = 0; j < entryCollection.size(); j++) {
					NewListingEntryInfo entry = entryCollection.get(j);

					view = new EntityViewInfo();
					filter = new FilterInfo();
					selector = view.getSelector();
					view.setFilter(filter);
					filter.getFilterItems().add(new FilterItemInfo("entry.id", entry.getId().toString()));
					filter.appendFilterItem("type", QuotingPriceTypeEnum.modify);
					filter.appendFilterItem("column.headColumn.property", DescriptionEnum.TOTALPRICESUM_VALUE);
					selector.add("*");
					NewListingValueCollection valueCollection = NewListingValueFactory.getRemoteInstance().getNewListingValueCollection(view);
					BigDecimal temp = null;
					for (int k = 0; k < valueCollection.size(); k++) {
						NewListingValueInfo value = valueCollection.get(k);
						if (value.getAmount() != null) {
							if (temp == null || temp.floatValue() > value.getAmount().floatValue())
								temp = value.getAmount();
						}

					}
					map.put(entry.getId().toString(), temp != null ? temp : FDCHelper._ONE);
				}
			}

			// Ͷ�걨��ƽ��ֵ
		} else if (setting.getStand() == QuoteStandEnum.QuotingPriceAvg) {
			for (int i = 0; i < listingInfo.getPages().size(); i++) {
				EntityViewInfo view = new EntityViewInfo();
				FilterInfo filter = new FilterInfo();
				SelectorItemCollection selector = view.getSelector();
				view.setFilter(filter);

				NewListingPageInfo page = listingInfo.getPages().get(i);
				filter.getFilterItems().add(new FilterItemInfo("head.id", page.getId().toString()));
				filter.getFilterItems().add(new FilterItemInfo("isLeaf", Boolean.TRUE));
				selector.add("id");
				selector.add("itemName");
				view.getSorter().add(new SorterItemInfo("seq"));
				NewListingEntryCollection entryCollection = NewListingEntryFactory.getRemoteInstance().getNewListingEntryCollection(view);

				for (int j = 0; j < entryCollection.size(); j++) {
					NewListingEntryInfo entry = entryCollection.get(j);

					view = new EntityViewInfo();
					filter = new FilterInfo();
					selector = view.getSelector();
					view.setFilter(filter);

					filter.getFilterItems().add(new FilterItemInfo("entry.id", entry.getId().toString()));
					filter.appendFilterItem("type", QuotingPriceTypeEnum.MODIFY_VALUE);
					filter.appendFilterItem("column.headColumn.property", DescriptionEnum.TOTALPRICESUM_VALUE);

					Set quotingSet=new HashSet();
					for (int k = 0; k < this.quotingPrices.size(); k++) {
						quotingSet.add(this.quotingPrices.get(k).getId().toString());
					}
					filter.getFilterItems().add(
							new FilterItemInfo("quotingPrice.id", quotingSet,CompareType.INCLUDE));

					selector.add("*");
					NewListingValueCollection valueCollection = NewListingValueFactory.getRemoteInstance().getNewListingValueCollection(view);
					BigDecimal sum = FDCHelper.ZERO;
					int count = 0;
					for (int k = 0; k < valueCollection.size(); k++) {
						NewListingValueInfo value = valueCollection.get(k);
						// if (value.getAmount() != null) {
						if (value.getAmount() == null)
							value.setAmount(FDCHelper.ZERO);
						sum = sum.add(value.getAmount());
						count++;
						// }

					}
					if (count != 0)
						map.put(entry.getId().toString(), sum.divide(new BigDecimal("" + count), BigDecimal.ROUND_HALF_UP));
					else
						map.put(entry.getId().toString(), FDCHelper._ONE);
				}
			}

			// �ο���
		} else if (setting.getStand() == QuoteStandEnum.ConsultPrice) {

			EntityViewInfo view = new EntityViewInfo();
			FilterInfo filter = new FilterInfo();
			SelectorItemCollection selector = view.getSelector();
			view.setFilter(filter);

			filter.getFilterItems().add(new FilterItemInfo("head.head.id", listingInfo.getId().toString()));
			filter.getFilterItems().add(new FilterItemInfo("isLeaf", Boolean.TRUE));
			// filter.appendFilterItem("type", null);
			selector.add("id");
			view.getSorter().add(new SorterItemInfo("seq"));
			NewListingEntryCollection entryCollection = NewListingEntryFactory.getRemoteInstance().getNewListingEntryCollection(view);

			for (int j = 0; j < entryCollection.size(); j++) {
				NewListingEntryInfo entry = entryCollection.get(j);

				view = new EntityViewInfo();
				filter = new FilterInfo();
				selector = view.getSelector();
				view.setFilter(filter);

				filter.getFilterItems().add(new FilterItemInfo("entry.id", entry.getId().toString()));
				filter.getFilterItems().add(new FilterItemInfo("quotingPrice", null));
				filter.appendFilterItem("column.headColumn.property", DescriptionEnum.TOTALPRICESUM_VALUE);
				selector.add("id");
				selector.add("amount");
				NewListingValueCollection valueCollection = NewListingValueFactory.getRemoteInstance().getNewListingValueCollection(view);
				for (int k = 0; k < valueCollection.size(); k++) {
					NewListingValueInfo value = valueCollection.get(k);
					map.put(entry.getId().toString(), value.getAmount() != null ? value.getAmount() : FDCHelper._ONE);
				}

			}
			// }

			// ����ܱ��۵�Ͷ����
		} else if (setting.getStand() == QuoteStandEnum.LowestTotalPrice) {
			NewQuotingPriceInfo temp = null;
			for (int xx = 0; xx < quotingPrices.size(); xx++) {
				NewQuotingPriceInfo q = quotingPrices.get(xx);
				if (q.getTotoalPrice() != null) {
					if (temp == null || q.getTotoalPrice().floatValue() < temp.getTotoalPrice().floatValue()) {
						temp = q;
					}
				}
			}
			if (temp == null)
				return new HashMap();

			EntityViewInfo view = new EntityViewInfo();
			FilterInfo filter = new FilterInfo();
			SelectorItemCollection selector = view.getSelector();
			view.setFilter(filter);
			view = new EntityViewInfo();
			filter = new FilterInfo();
			selector = view.getSelector();
			view.setFilter(filter);
			filter.getFilterItems().add(new FilterItemInfo("quotingPrice.listing.id", listingInfo.getId().toString()));
			filter.getFilterItems().add(new FilterItemInfo("quotingPrice.supplier.id", temp.getSupplier().getId().toString()));
			filter.getFilterItems().add(new FilterItemInfo("entry.isLeaf", Boolean.TRUE));
			filter.appendFilterItem("type", QuotingPriceTypeEnum.modify);
			filter.appendFilterItem("column.headColumn.property", DescriptionEnum.TOTALPRICESUM_VALUE);
			selector.add("id");
			selector.add("entry.id");
			selector.add("amount");
			NewListingValueCollection valueCollection = NewListingValueFactory.getRemoteInstance().getNewListingValueCollection(view);
			for (int k = 0; k < valueCollection.size(); k++) {
				NewListingValueInfo value = valueCollection.get(k);
				map.put(value.getEntry().getId().toString(), value.getAmount() != null ? value.getAmount() : FDCHelper._ONE);
			}
		}

		return map;
	}

	/**
	 * ������������ʼ���㲢�䳬����Χ����Ŀ������
	 *
	 * @throws BOSException
	 * @throws SQLException
	 */

	private void initOverRangeData() throws BOSException, SQLException {
		NewListingPageCollection sheets = listingInfo.getPages();
		BigDecimal high = setting.getAllowHigh();
		BigDecimal low = setting.getAllowLow();
		BigDecimal oHUNDRED = new BigDecimal("100");
		for (int i = 0; i < sheets.size(); i++) {
			NewListingPageInfo sheetInfo = sheets.get(i);
			if (!sheetInfo.getPageHead().getName().equals(FDCExcelImportExportUtil.sheetName_ProjectDescription)
					&& !sheetInfo.getPageHead().getName().equals(FDCExcelImportExportUtil.sheetName_ProjectGather)) {
				KDTable table = (KDTable) sheetsMap.get(sheetInfo.getPageHead().getName());
				// �Ƚ���ǰҳ������Ŀѡ�����
				EntityViewInfo viewInfo = new EntityViewInfo();
				FilterInfo filterInfo = new FilterInfo();
				filterInfo.appendFilterItem("head.id", sheetInfo.getId().toString());
				filterInfo.appendFilterItem("isLeaf", "1");
				viewInfo.setFilter(filterInfo);
				viewInfo.getSelector().add("*");
				viewInfo.getSelector().add("item.id");
				viewInfo.getSelector().add("item.number");
				viewInfo.getSorter().add(new SorterItemInfo("seq"));
				NewListingEntryCollection entryCollection = NewListingEntryFactory.getRemoteInstance().getNewListingEntryCollection(viewInfo);
				int seq = 1;
				for (int m = 0; m < entryCollection.size(); m++) {
					NewListingEntryInfo entryInfo = entryCollection.get(m);
					// �õ���׼��
					BigDecimal base = (BigDecimal) standPriceMap.get(entryInfo.getId().toString());
					Map map = getDataByEntry(entryInfo);
					// �õ���ǰ����
					BigDecimal value = (BigDecimal) map.get("Ͷ�굥��");
					// ��֤��׼�ۺ�Ͷ��۶���Ϊ��
					if (base == null){
						base = FDCHelper._ONE;
					}
					if (value == null){
						value = FDCHelper._ONE;
					}
//					 if (base == null || value == null || base.floatValue() <= 0)
//						continue;
					// /////��ʼ���
					if (base.floatValue() == 0
							|| value.multiply(oHUNDRED).divide(base, 2,
									BigDecimal.ROUND_HALF_UP).compareTo(high) == 1
							|| value.multiply(oHUNDRED).divide(base, 2,
									BigDecimal.ROUND_HALF_UP).compareTo(low) == -1) {
						int col = 0;
						int row = table.addRow().getRowIndex();
						ICell cell = table.getCell(row, col++);

						cell.setValue("" + seq++);// ���

						cell = table.getCell(row, col++);
						cell.setValue(entryInfo.getItem() != null ? entryInfo.getItem().getNumber() : entryInfo.getItemNumber());// ���

						cell = table.getCell(row, col++);
						cell.setValue(entryInfo.getItemName());// ����

						cell = table.getCell(row, col++);
						cell.setValue(map.get("��λ"));// ��λ

						cell = table.getCell(row, col++);
						cell.setValue(map.get("������"));// ������

						cell = table.getCell(row, col++);
						cell.setValue("�ۺϵ���");// �쳣����

						cell = table.getCell(row, col++);
						if(base.intValue() < 0)
							cell.setValue("û�л�׼��");
						else
							cell.setValue(base);// �ο���(��׼��)

						cell = table.getCell(row, col++);
						cell.setValue(value);// Ͷ���

						cell = table.getCell(row, col++);
						if (base.intValue() > 0) {
							TableScriptHelper.setTableDivideFormalu(cell, table.getCell(row, cell.getColumnIndex() - 1), table.getCell(row, cell
									.getColumnIndex() - 2));
							cell.getStyleAttributes().setNumberFormat("#,#0.00%;-#,#0.00%");
						}
						else if(base.intValue() == 0){
							cell.setValue("��׼��Ϊ��");
						}
						else {
							cell.setValue("û�л�׼��");
							// rangeSa.setHorizontalAlign(HorizontalAlignment.RIGHT);
						}
						cell = table.getCell(row, col++);
						// �������
						if (base.intValue() > 0) {
							TableScriptHelper
							.setTableSubFormalu(cell, table.getCell(row, cell.getColumnIndex() - 2), table.getCell(row, cell.getColumnIndex() - 3));
							cell.getStyleAttributes().setNumberFormat("#,#0.00;-#,#0.00");
						}
						else if(base.intValue() == 0){
							cell.setValue("��׼��Ϊ��");
						}
						else {
							cell.setValue("û�л�׼��");
							// rangeSa.setHorizontalAlign(HorizontalAlignment.RIGHT);
						}
						

						cell = table.getCell(row, col++);
						cell.setValue(setting.getStand().getAlias());// 100%��׼
					}
					// //////������
				}
//				table.addRow();
//				IRow row = table.addRow();
//				row.getCell(1).setValue("��������ܼ�");
//				BigDecimal sum = FDCHelper.ZERO;
//				int tableRowCount = table.getRowCount();
//				for (int j = 0; j < tableRowCount - 2; j++) {
//					sum = sum.add((BigDecimal) table.getRow(j).getCell(9).getValue());
//				}
//				row.getCell(9).setValue(sum);
//				table.getMergeManager().mergeBlock(tableRowCount - 2, 0, tableRowCount - 2, 10);
//				table.getMergeManager().mergeBlock(tableRowCount - 1, 0, tableRowCount - 1, 8);
				// table.getMergeManager().mergeBlock(tableRowCount - 1, 9,
				// tableRowCount - 1, 10);
			}
		}
	}

	private Map getDataByEntry(NewListingEntryInfo entryInfo) throws BOSException {
		Map map = new HashMap(10);
		EntityViewInfo viewInfo = new EntityViewInfo();
		FilterInfo filterInfo = new FilterInfo();
		filterInfo.appendFilterItem("entry", entryInfo.getId().toString());
		filterInfo.appendFilterItem("quotingPrice", priceContentInfo.getId().toString());
		filterInfo.appendFilterItem("type", QuotingPriceTypeEnum.modify);// ��������
		filterInfo.appendFilterItem("quotingPrice", null);
		filterInfo.appendFilterItem("type", null);// �ο�����
		// filterInfo.appendFilterItem("column.headColumn.property",
		// "4TotalPriceSum");// �ۺϵ���С��
		// filterInfo.appendFilterItem("column.headColumn.property",
		// "3TotalPrice"/* DescriptionEnum.TotalPrice */);//
		// �ۺϵ���
		filterInfo.setMaskString("(#0 and (#1 and #2 or #3 and #4))");
		viewInfo.setFilter(filterInfo);
		viewInfo.getSelector().add("*");
		viewInfo.getSelector().add("column.*");
		viewInfo.getSelector().add("column.headColumn.*");
		NewListingValueCollection valueCollection = NewListingValueFactory.getRemoteInstance().getNewListingValueCollection(viewInfo);
		for (int i = 0; i < valueCollection.size(); i++) {
			NewListingValueInfo value = valueCollection.get(i);
			HeadColumnInfo headColumn = value.getColumn().getHeadColumn();
			if (value.getType() == null && "��λ".equals(headColumn.getName())) {
				map.put("��λ", value.getText());
			} else if (value.getType() == null && DescriptionEnum.ProjectAmtSum.equals(value.getColumn().getHeadColumn().getProperty())) {
				map.put("������", value.getAmount());
			} else if (value.getType() != null && headColumn.getProperty().equals(DescriptionEnum.TotalPriceSum)) {
				map.put("Ͷ�굥��", value.getAmount() != null ? value.getAmount() : FDCHelper.ZERO);
			}
		}
		return map;
	}

	public void actionExport_actionPerformed(ActionEvent e) throws Exception {
		InviteHelper.exportToExcel(tables, true);
	}

	public void prePrintAction_actionPerformed(ActionEvent e) throws Exception {
		KDTable table = (KDTable) tablePane.getSelectedComponent();
		InviteHelper.setPrintTitle(table, "������Χ�ۺϵ�����ϸ��", 0);
		table.getPrintManager().printPreview();
	}

	public void printAction_actionPerformed(ActionEvent e) throws Exception {
		KDTable table = (KDTable) tablePane.getSelectedComponent();
		InviteHelper.setPrintTitle(table, "������Χ�ۺϵ�����ϸ��", 0);
		table.getPrintManager().print();
	}

	public void refreshAction_actionPerformed(ActionEvent e) throws Exception {
		init();
	}

	// private void del() throws BOSException, EASBizException {
	//
	// NewListingValueCollection valueCollection =
	// NewListingValueFactory.getRemoteInstance().getNewListingValueCollection();
	// for (int i = 0; i < valueCollection.size(); i++) {
	// NewListingValueInfo value = valueCollection.get(i);
	// if (value.getQuotingPrice() == null)
	// continue;
	// EntityViewInfo view = new EntityViewInfo();
	// FilterInfo filter = new FilterInfo();
	// view.setFilter(filter);
	// filter.appendFilterItem("id", value.getQuotingPrice().getId());
	// NewQuotingPriceCollection qc =
	// NewQuotingPriceFactory.getRemoteInstance().getNewQuotingPriceCollection(view);
	// if (qc.size() == 0) {
	// String sql = "delete from T_INV_NewListingValue where fid='" +
	// value.getId() + "'";
	// ISQLExecutor executor = SQLExecutorFactory.getRemoteInstance(sql);
	// executor.executeSQL();
	// }
	// }
	// }
}
