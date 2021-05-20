/**
 * output package name
 */
package com.kingdee.eas.fdc.invite.client;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.zip.ZipEntry;
import java.util.zip.ZipException;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

import javax.swing.JFileChooser;
import javax.swing.SwingUtilities;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.common.variant.Variant;
import com.kingdee.bos.ctrl.excel.model.struct.Cell;
import com.kingdee.bos.ctrl.excel.model.struct.Sheet;
import com.kingdee.bos.ctrl.extendcontrols.BizDataFormat;
import com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox;
import com.kingdee.bos.ctrl.kdf.table.ICell;
import com.kingdee.bos.ctrl.kdf.table.IColumn;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTDefaultCellEditor;
import com.kingdee.bos.ctrl.kdf.table.KDTMergeManager;
import com.kingdee.bos.ctrl.kdf.table.KDTStyleConstants;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.event.KDTEditAdapter;
import com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent;
import com.kingdee.bos.ctrl.kdf.util.editor.ICellEditor;
import com.kingdee.bos.ctrl.kdf.util.style.Styles.HorizontalAlignment;
import com.kingdee.bos.ctrl.swing.KDFileChooser;
import com.kingdee.bos.ctrl.swing.KDFormattedTextField;
import com.kingdee.bos.ctrl.swing.KDTabbedPane;
import com.kingdee.bos.ctrl.swing.KDTextField;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.framework.cache.CacheServiceFactory;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemCollection;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.base.permission.PermissionFactory;
import com.kingdee.eas.basedata.master.cssp.SupplierInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCNumberHelper;
import com.kingdee.eas.fdc.basedata.client.FDCClientUtils;
import com.kingdee.eas.fdc.basedata.client.FDCClientVerifyHelper;
import com.kingdee.eas.fdc.invite.ColumnTypeEnum;
import com.kingdee.eas.fdc.invite.DescriptionEnum;
import com.kingdee.eas.fdc.invite.FDCInviteException;
import com.kingdee.eas.fdc.invite.HeadColumnInfo;
import com.kingdee.eas.fdc.invite.ListingItemInfo;
import com.kingdee.eas.fdc.invite.ListingPageSumEntryInfo;
import com.kingdee.eas.fdc.invite.NewListingColumnCollection;
import com.kingdee.eas.fdc.invite.NewListingColumnFactory;
import com.kingdee.eas.fdc.invite.NewListingColumnInfo;
import com.kingdee.eas.fdc.invite.NewListingEntryCollection;
import com.kingdee.eas.fdc.invite.NewListingEntryFactory;
import com.kingdee.eas.fdc.invite.NewListingEntryInfo;
import com.kingdee.eas.fdc.invite.NewListingFactory;
import com.kingdee.eas.fdc.invite.NewListingInfo;
import com.kingdee.eas.fdc.invite.NewListingPageCollection;
import com.kingdee.eas.fdc.invite.NewListingPageFactory;
import com.kingdee.eas.fdc.invite.NewListingPageInfo;
import com.kingdee.eas.fdc.invite.NewListingValueCollection;
import com.kingdee.eas.fdc.invite.NewListingValueFactory;
import com.kingdee.eas.fdc.invite.NewListingValueInfo;
import com.kingdee.eas.fdc.invite.NewQuotingPriceFactory;
import com.kingdee.eas.fdc.invite.NewQuotingPriceInfo;
import com.kingdee.eas.fdc.invite.PageHeadInfo;
import com.kingdee.eas.fdc.invite.QuotingPriceStatusEnum;
import com.kingdee.eas.fdc.invite.QuotingPriceTypeEnum;
import com.kingdee.eas.fdc.invite.SupplierQualifyEntryInfo;
import com.kingdee.eas.fdc.migrate.StringUtils;
import com.kingdee.eas.fm.common.FMConstants;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.framework.client.AbstractEditUI;
import com.kingdee.eas.framework.client.CoreUI;
import com.kingdee.eas.util.client.MsgBox;
import com.kingdee.util.DateTimeUtils;

/**
 * ���۵��� �༭����
 */
public class NewQuotingPriceEditUI extends AbstractNewQuotingPriceEditUI {
	private static final Color LEAF_COLOR = new Color(0xFF, 0xEA, 0x67);

	private static final Color NON_LEAF_COLOR = new Color(0xF0EDD9);

	private static final Color REQUIREDCOLOR = new Color(0, true);

	private static final Logger logger = CoreUIObject
			.getLogger(NewQuotingPriceEditUI.class);


	// begin xuisan 
	Map refPriceBakMap = new HashMap();
	HashMap listingColumnObjectMap = new HashMap();
	// end xuisan 
	
	
	NewListingInfo listing = null;

	/**
	 * �������ͣ�����Ϊ��ԭʼ���ۡ��������ۡ���ʷ����
	 */
	private QuotingPriceTypeEnum priceTypeEnum = null;

	boolean isModify = false;
	
	private static boolean isRate = false;

	Map rowMap = new HashMap();
	Map columnMap = new HashMap();
	public boolean isModify() {
		return isModify;
	}

	/**
	 * output class constructor
	 */
	public NewQuotingPriceEditUI() throws Exception {
		super();
	}

	public void onShow() throws Exception {
		super.onShow();
		this.f7Bidder.requestFocus();
	}

	/**
	 * output storeFields method
	 */
	public void storeFields() {
		super.storeFields();
		if(this.f7Bidder.getValue()!=null&&this.f7Bidder.getValue() instanceof SupplierQualifyEntryInfo)
		{
			SupplierQualifyEntryInfo entryInfo = (SupplierQualifyEntryInfo)this.f7Bidder.getValue();
//			this.editData.setSupplier(entryInfo.getSupplier());
		}
		if(this.f7Bidder.getValue()!=null&&this.f7Bidder.getValue() instanceof SupplierInfo)
		{
			SupplierInfo sInfo = (SupplierInfo)this.f7Bidder.getValue();
			this.editData.setSupplier(sInfo);
		}
		QuotingPriceStatusEnum status = null;
		if (priceTypeEnum != null) {
			NewQuotingPriceInfo dbQuoting = null;
			try {
				dbQuoting = NewQuotingPriceFactory.getRemoteInstance()
						.getNewQuotingPriceInfo(
								new ObjectUuidPK(this.editData.getId()));
				status = dbQuoting.getStatus();
			} catch (EASBizException e) {
				// TODO �Զ����� catch ��
				e.printStackTrace();
			} catch (BOSException e) {
				// TODO �Զ����� catch ��
				e.printStackTrace();
			}
		}
		status = (status == null) ? QuotingPriceStatusEnum.NoImportPrice
				: status;

		if (status.equals(QuotingPriceStatusEnum.NoImportPrice)
				|| priceTypeEnum.equals(QuotingPriceTypeEnum.modify)) {
			if (tabbedPnlTable.getTabCount() > 2) {
				KDTable tbl = (KDTable) tabbedPnlTable.getComponentAt(1);
				BigDecimal value = (BigDecimal) tbl.getCell(
						tbl.getRowCount() - 1, "amount").getValue();
				this.editData.setTotoalPrice(value);
			}
		}
		this.initOldData(this.editData);
	}

	protected IObjectValue createNewData() {
		NewQuotingPriceInfo quoting = new NewQuotingPriceInfo();
		quoting.setId(BOSUuid.create(quoting.getBOSType()));
		String inviteListingId = (String) getUIContext().get("inviteListingId");
		try {
			quoting.setListing(NewListingFactory.getRemoteInstance()
					.getNewListingInfo(new ObjectUuidPK(inviteListingId)));
		} catch (EASBizException e) {
			// TODO �Զ����� catch ��
			e.printStackTrace();
		} catch (BOSException e) {
			// TODO �Զ����� catch ��
			e.printStackTrace();
		}
		quoting.setStatus(QuotingPriceStatusEnum.NoImportPrice);
		quoting.setTotoalPrice(new BigDecimal("0"));
		return quoting;
	}

	protected ICoreBase getBizInterface() throws Exception {
		return NewQuotingPriceFactory.getRemoteInstance();
	}

	public boolean destroyWindow() {
		boolean b = super.destroyWindow();
		// �˳�ʱ���ˢ�� ListUI�ĺ������ã�
		if (this.isSaved() && getUIContext().get(UIContext.OWNER) != null) {
			QuotingPriceListUI listUI = (QuotingPriceListUI) getUIContext()
					.get(UIContext.OWNER);
			listUI.refreshQuotingPriceListByInviteListingRow();
		}
		return b;
	}

	public void loadFields() {
		super.loadFields();
		this.f7Bidder.setValue(this.editData.getSupplier());
		if (this.editData.getSupplier() != null) {
			this.txtBidderNumber.setText(this.editData.getSupplier()
					.getNumber());
		}
		// �޸�ʱ���ܸ�Ͷ����
		if (AbstractEditUI.STATUS_EDIT.equals(getOprtState())) {
			f7Bidder.setEnabled(false);
		}
		this.storeFields();
	}

	public void onLoad() throws Exception {
		if (this.getOprtState().equals("EDIT1")) {
			this.setOprtState("EDIT");
		}
		if (this.getOprtState().equals("VIEW1")) {
			this.setOprtState("VIEW");
		}
		super.onLoad();
		String inviteListingId = (String) getUIContext().get("inviteListingId");
		listing = getInviteListing(inviteListingId);
		if(listing.getInviteProject()!=null){
			SelectorItemCollection sic = new SelectorItemCollection();
			sic.add("*");
			sic.add("supplier.name");
			sic.add("supplier.number");
			sic.add("supplier.id");
			sic.add("parent.*");
			EntityViewInfo view = new EntityViewInfo();
			FilterInfo filter = new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("parent.inviteProject.id",listing.getInviteProject().getId().toString()));
			view.setFilter(filter);
			this.f7Bidder.setEntityViewInfo(view);
			this.f7Bidder.setSelectorCollection(sic);
		}
		else{
			//FDCMsgBox.showError(this,"���嵥û��ָ���б�����");
			FDCClientUtils.initSupplierF7(this, this.f7Bidder);
		}
		this.f7Bidder.addChangeListener(new SupplierF7CommitListener(
				this.txtBidderNumber));
		
		if (getUIContext().get("quotingPriceType") != null) {
			priceTypeEnum = (QuotingPriceTypeEnum) getUIContext().get(
					"quotingPriceType");
		}
		this.actionExportErrorInfo.setEnabled(false);
		this.menuImportPrice.setIcon(this.btnImportQuotingExcel.getIcon());
		this.exportMenuItem.setIcon(this.exportButton.getIcon());
		if (priceTypeEnum != null) {
			this.windowTitle = this.windowTitle + "["
					+ priceTypeEnum.getAlias() + "]";
			this.btnImportQuotingExcel.setText("����" + priceTypeEnum.getAlias());
			this.menuImportPrice.setText("����" + priceTypeEnum.getAlias());
		}else{
			this.actionImportQuotingExcel.setVisible(false);
			this.actionExportQuotingExcel.setVisible(false);
			this.menuBiz.setVisible(false);
		}
		this.actionExportErrorInfo.setVisible(false);
		this.actionSave.setVisible(false);
		// enable=false���ܽ��ÿ�ݼ�
		actionSave.setEnabled(false);
		actionAddNew.setEnabled(false);
		actionRemove.setEnabled(false);

		this.actionSubmit.setVisible(true);
		
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				loadPages(listing);
			}
		});
		this.btnImportQuotingExcel.getAction().setEnabled(
				OprtState.EDIT.equals(oprtState)
						|| OprtState.ADDNEW.equals(oprtState));
		this.exportMenuItem.getAction().setEnabled(
				OprtState.EDIT.equals(oprtState)
						|| OprtState.ADDNEW.equals(oprtState));
		
		// ���ص�������
		this.actionExportQuotingExcel.setVisible(false);
		
		// ��Ҷ�ӽڵ�ϼ����¼���
		for (int i = 0; i < this.tabbedPnlTable.getTabCount(); i++) {
			KDTable table = (KDTable) this.tabbedPnlTable.getComponentAt(i);
			//���ڵ���
			ArrayList isLeafListNo = new ArrayList();
			for (int k = 1; k < table.getRowCount(); k++) {
				BigDecimal sum = FMConstants.ZERO;
				IRow row = table.getRow(k);
				BigDecimal rowLevel = FDCHelper.toBigDecimal(row.getCell(0).getValue());
				NewListingEntryInfo info = new NewListingEntryInfo();
//				if (info != null && !info.isIsLeaf()) {
				if(!checkLeafRow(table, k)){
					row.getCell(4).setValue(null);
					row.getCell(5).setValue(null);
//					row.getCell(6).setValue(null);
					info.setIsLeaf(false);
					
					isLeafListNo.add(String.valueOf(k));
					
					for (int m = k + 1; m < table.getRowCount(); m++) {
						IRow rowNext = table.getRow(m);
						NewListingEntryInfo nextInfo = (NewListingEntryInfo) rowNext.getUserObject();
//						if (nextInfo != null && nextInfo.isIsLeaf()) {
						if(checkLeafRow(table, m)){
							BigDecimal rowLevelNext = FDCHelper.toBigDecimal(rowNext.getCell(0).getValue());
							BigDecimal value = FDCHelper.toBigDecimal(rowNext.getCell(7).getValue());
							if(rowLevelNext.floatValue() > rowLevel.floatValue()){
								sum = sum.add(FDCHelper.toBigDecimal(value));
							}
						}
					}
//					row.getCell(7).setValue(sum);
				}else{
					info.setIsLeaf(true);
				}
				row.setUserObject(info);
			}
		}
	}
	
	public static boolean checkLeafRow(KDTable table, int rowIndex) {
		if (rowIndex + 1 == table.getRowCount()) {
			return true;
		}
		BigDecimal rowLevel = FDCHelper.toBigDecimal(table.getRow(rowIndex).getCell(0).getValue());
		BigDecimal nextLevel = FDCHelper.toBigDecimal(table.getRow(rowIndex + 1).getCell(0).getValue());
		if (nextLevel.floatValue() <= rowLevel.floatValue()) {
			return true;
		}
		return false;
	}

	/**
	 * ��ȡ�б��嵥
	 * @param inviteListingId �б��嵥ID����QuotingPriceListUI.javaͨ��UIContent������
	 * @return �б��嵥
	 * @throws BOSException
	 */
	public static NewListingInfo getInviteListing(String inviteListingId) throws BOSException {
		
		NewListingInfo listing = new NewListingInfo();
		SelectorItemCollection sicInviteProject = new SelectorItemCollection();
		sicInviteProject.add(new SelectorItemInfo("*"));
		sicInviteProject.add(new SelectorItemInfo("inviteProject.isRate"));
		try {
			listing = NewListingFactory.getRemoteInstance().getNewListingInfo(new ObjectUuidPK(BOSUuid.read(inviteListingId)),sicInviteProject);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(listing.getInviteProject() != null){
			isRate = listing.getInviteProject().isIsRate();
		}
				
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		view.setFilter(filter);
		filter.getFilterItems().add(
				new FilterItemInfo("head.id", listing.getId().toString()));
		SelectorItemCollection sic = view.getSelector();
		sic.add("*");
		sic.add("pageHead.*");
		sic.add("headType.*");
		sic.add("columns.*");
		sic.add("entrys.*");
		view.getSorter().add(new SorterItemInfo("seq"));
		NewListingPageCollection sheets = NewListingPageFactory
				.getRemoteInstance().getNewListingPageCollection(view);
		listing.getPages().clear();
		listing.getPages().addCollection(sheets);
		for (int i = 0; i < listing.getPages().size(); i++) {
			NewListingPageInfo sheet = listing.getPages().get(i);
			view = new EntityViewInfo();
			filter = new FilterInfo();
			view.setFilter(filter);
			filter.getFilterItems().add(
					new FilterItemInfo("page.id", sheet.getId().toString()));
			sic = view.getSelector();
			sic.add("*");
			sic.add("headColumn.*");
			sic.add("headColumn.parent.*");
			view.getSorter().add(new SorterItemInfo("seq"));
			NewListingColumnCollection columns = null;
			columns = NewListingColumnFactory.getRemoteInstance()
					.getNewListingColumnCollection(view);
			sheet.getColumns().clear();
			sheet.getColumns().addCollection(columns);

			view = new EntityViewInfo();
			filter = new FilterInfo();
			view.setFilter(filter);
			view.getSelector().add("*");
			view.getSelector().add("item.*");
			view.getSelector().add("values.*");
			//ҳǩ��ͷ���Ͷ���isDefinedһ��Ҫselect��
			//���򼴱�sheet����ֵ����newListingEntry.getHead().getHeadType().isIsDefined()����ȡ����ֵ
			view.getSelector().add("head.headType.isDefined"); 
			filter.getFilterItems().add(new FilterItemInfo("head.id", sheet.getId().toString()));
			view.getSorter().add(new SorterItemInfo("seq"));
			NewListingEntryCollection entrys = null;
			entrys = NewListingEntryFactory.getRemoteInstance().getNewListingEntryCollection(view);
			sheet.getEntrys().clear();
			sheet.getEntrys().addCollection(entrys);
		}
		return listing;
	}

	/**
	 * ����������ҳǩ��ҳǩ����
	 * @param newListingInfo �б��嵥
	 */
	private void loadPages(NewListingInfo newListingInfo) {
		tabbedPnlTable.removeAll();
		for (int page_i = 0; page_i < newListingInfo.getPages().size(); page_i++) {
			NewListingPageInfo page = (NewListingPageInfo) newListingInfo.getPages().get(page_i);
			PageHeadInfo pageHead = page.getPageHead();
			KDTable table = new KDTable();
			table.setName(pageHead.getName());
			initTable(table, page);
			String id = null;
			if (this.editData.getId() != null) {
				id = this.editData.getId().toString();
			}
			loadTableData(table, page, id,priceTypeEnum);
			setUnionData(table,tabbedPnlTable);
			
			hideSupplierDefinedRow(page, table);
			
			setTableLock(table, true);
			this.tabbedPnlTable.add(table, pageHead.getName());
		}
		initTotalPageTable(tabbedPnlTable,listing);
		initPageDesTable(tabbedPnlTable);
		InviteHelper.setAotuHeight(this.tabbedPnlTable);
		tHelper.setCanMoveColumn(false);
	}

	/**
	 * 
	 * ���������طǱ��ι�Ӧ���Զ����ĳЩ�У����ڷǱ��ι�Ӧ�̵����ݱ���Ҫ���أ�
	 * ���򱾹�Ӧ���ܿ���������Ӧ�̵ı�����Ϣ�Ƿǳ����ص�����
	 * @Author��owen_wen
	 * @param page ҳǩ
	 * @param table ���
	 * @CreateTime��2011-11-1
	 */
	private void hideSupplierDefinedRow(NewListingPageInfo page, KDTable table) {
		if (page != null) {
			int projectAmtIndex = 0;
			int projectAmtSum = 0;	
			int totalPriceIndex = 0;
			int totalPriceSum = 0;
			int amountIndex = 0;
			
			if("������".equals(table.getColumn(5).getKey())){
				projectAmtIndex = 5;
				projectAmtSum = 5;
			}else{
				projectAmtIndex = 5;
				for(int j = projectAmtIndex; j < table.getColumnCount(); j++){
					if("С��".equals(table.getColumn(j).getKey())){
						projectAmtSum = j;
						break;
					}
				}
			}
			
			if("�ۺϵ���".equals(table.getColumn(projectAmtSum + 1).getKey())){
				totalPriceIndex = projectAmtSum + 1;
				totalPriceSum = projectAmtSum + 1;
			}else{
				totalPriceIndex = projectAmtSum + 1;
				for(int j = totalPriceIndex; j < table.getColumnCount(); j++){
					if("С��".equals(table.getColumn(j).getKey())){
						totalPriceSum = j;
						break;
					}
				}
			}
			
			if("���".equals(table.getColumn(totalPriceSum + 1).getKey())){
				amountIndex = totalPriceSum + 1;
			}else{
				amountIndex = totalPriceSum + 1;
				for(int j = amountIndex; j < table.getColumnCount(); j++){
					if("С��".equals(table.getColumn(j).getKey())){
						break;
					}
				}
			}
			
			for (int i = 0; i < page.getEntrys().size(); i++) {
				NewListingEntryInfo pageEntryInfo = page.getEntrys().get(i);
				// �ǹ�Ӧ���Զ��壬����Ҫ������Ŀ��
				if(pageEntryInfo.getHead() != null && pageEntryInfo.getHead().getHeadType() != null){
					if(!pageEntryInfo.getHead().getHeadType().isIsDefined()){
						continue;
					}
				}
				NewListingValueInfo amountSumNewListingValueInfo = pageEntryInfo.getValues().get(totalPriceSum - 3);
				String columnnIdProjectAmt = null;
				if(amountSumNewListingValueInfo != null && amountSumNewListingValueInfo.getColumn() != null && amountSumNewListingValueInfo.getColumn().getId() != null){
					columnnIdProjectAmt = amountSumNewListingValueInfo.getColumn().getId().toString();
				}
				try {
					if(columnnIdProjectAmt != null){
						if (getUITitle().toString().contains("����")) {
							// ���Ǳ��ε��������� �� ����Ҫ����
							if (!NewListingValueFactory.getRemoteInstance().exists(
									"where entry.id = '" + pageEntryInfo.getString("id") + "'" + " and quotingPrice.id = '" + editData.getString("id") + "'" + "and type = 1")) {
								if (table.getRow(i + 1) != null) {
									 table.getRow(i + 1).getStyleAttributes().setHided(true);
								}
							}
						} else { // ������Ǳ��ε�ԭʼ���ۣ�������
							if (!NewListingValueFactory.getRemoteInstance().exists(
									"where entry.id = '" + pageEntryInfo.getString("id") + "'" + " and quotingPrice.id = '" + editData.getString("id") + "'" + "and type = 0")) {
								if (table.getRow(i + 1) != null) {
									table.getRow(i + 1).getStyleAttributes().setHided(true);
								}
							}
						}
					}
				} catch (EASBizException e) {
					e.printStackTrace();
				} catch (BOSException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public static void initPageDesTable(final KDTabbedPane tabbedPnlTable) {
		KDTable table = new KDTable();
		table.setActiveCellStatus(KDTStyleConstants.ACTIVE_CELL_EDIT);
		table.setName("�б�˵��");
		IRow row = table.addHeadRow();
		table.addColumns(2);
		table.getColumn(0).setKey("name");
		table.getColumn(1).setWidth(300);
		table.getColumn(0).getStyleAttributes().setLocked(true);
		table.getColumn(1).setKey("des");
		table.getColumn(1).setWidth(400);
		table.getColumn(1).getStyleAttributes().setWrapText(true);
		table.getColumn(1).getStyleAttributes().setLocked(true);
		KDTextField txtField = new KDTextField();
		txtField.setMaxLength(200);
		ICellEditor editor = new KDTDefaultCellEditor(txtField);
		table.getColumn("des").setEditor(editor);
		row.getCell(0).setValue("ҳǩ����");
		row.getCell(1).setValue("����");

		int pageCount = tabbedPnlTable.getTabCount();
		table.addRows(pageCount - 1);
		for (int i = 0; i < pageCount - 1; i++) {
			KDTable aTable = (KDTable) tabbedPnlTable.getComponentAt(i + 1);
			NewListingPageInfo page = (NewListingPageInfo) aTable
					.getUserObject();
			table.getCell(i, "name").setValue(page.getPageHead().getName());
			table.getCell(i, "des").setValue(page.getDescription());
		}
		tabbedPnlTable.add(table, 0);
		updateTotalPageData(tabbedPnlTable);
	}

	public static void initTotalPageTable(final KDTabbedPane tabbedPnlTable,NewListingInfo listing) {
		KDTable table = new KDTable();
		table.getStyleAttributes().setWrapText(true);
		table.setActiveCellStatus(KDTStyleConstants.ACTIVE_CELL_EDIT);
		table.setName("���ۻ��ܱ�");
		table.addHeadRows(2);
		IRow row = table.getHeadRow(1);
		table.addColumns(3);
		table.getColumn(0).setKey("number");
		table.getColumn(0).getStyleAttributes().setLocked(true);
		table.getColumn(1).setKey("name");
		table.getColumn(1).getStyleAttributes().setLocked(true);
		table.getColumn(2).setKey("amount");
		row.getCell(0).setValue("���");
		row.getCell(1).setValue("��Ŀ");
		row.getCell(2).setValue("���");
		table.getHeadRow(0).getCell(0).setValue("���ۻ��ܱ�");
		table.getHeadMergeManager().mergeBlock(0, 0, 0, 2);
		int pageCount = tabbedPnlTable.getTabCount();
		table.addRows(pageCount + 4);
		for (int i = 0; i < pageCount; i++) {
			table.getCell(i, "number").setValue(new Integer(i + 1));
			table.getCell(i, "name").setValue(tabbedPnlTable.getTitleAt(i));
			table.getCell(i, "amount").getStyleAttributes().setLocked(true);
		}
		table.getCell(pageCount, "name").setValue("С��");
		table.getCell(pageCount, "amount").getStyleAttributes().setLocked(true);
		table.getCell(pageCount + 1, "name").setValue("˰��(%)");
		table.getCell(pageCount + 1, "amount").setValue(listing.getTax());
		table.getCell(pageCount + 1, "amount").getStyleAttributes().setLocked(
				true);
		table.getCell(pageCount + 2, "name").setValue("˰��");
		table.getCell(pageCount + 2, "amount").getStyleAttributes().setLocked(
				true);
		table.getCell(pageCount + 3, "name").setValue("�����");
		table.getCell(pageCount + 3, "amount").getStyleAttributes().setLocked(
				true);
		KDFormattedTextField formattedTextField = new KDFormattedTextField(
				KDFormattedTextField.BIGDECIMAL_TYPE);
		formattedTextField.setPrecision(2);
		formattedTextField.setRemoveingZeroInDispaly(false);
		formattedTextField.setRemoveingZeroInEdit(false);
		formattedTextField.setNegatived(false);
		formattedTextField.setMaximumValue(FDCHelper.MAX_VALUE);
		formattedTextField.setMinimumValue(FDCHelper.MIN_VALUE);
		ICellEditor numberEditor = new KDTDefaultCellEditor(formattedTextField);
		table.getColumn(2).setEditor(numberEditor);
		FDCHelper.formatTableNumber(table, "amount");
		tabbedPnlTable.add(table, 0);
		updateTotalPageData(tabbedPnlTable);
	}

	/**
	 * ��ʼ����񣬰������кͱ�����
	 */
	private void initTable(final KDTable table, NewListingPageInfo page) {
		table.getStyleAttributes().setWrapText(true);
		table.setActiveCellStatus(KDTStyleConstants.ACTIVE_CELL_EDIT);
		tHelper.setCanSetTable(true);
		table.setUserObject(page);
		setTableEvent(table);
		setTableColumn(table, page);
		setTableRow(table, page, false);
		table.getTreeColumn().setDepth(getMaxLevel(table));
	}

	private static void updateTotalPageData(final KDTabbedPane tabbedPnlTable) {
		if (tabbedPnlTable.getTabCount() <= 1) {
			return;
		}
		KDTable table = (KDTable) tabbedPnlTable.getComponentAt(1);
		if (!table.getName().equals("���ۻ��ܱ�")) {
			return;
		}
		int pageCount = tabbedPnlTable.getTabCount() - 2;
		BigDecimal sum = FDCHelper.ZERO;
		for (int i = 2; i < pageCount + 2; i++) {
			KDTable page = (KDTable) tabbedPnlTable.getComponentAt(i);
			for (int j = 3; j < page.getColumnCount(); j++) {
				IColumn col = page.getColumn(j);
				NewListingColumnInfo colInfo = (NewListingColumnInfo) col.getUserObject();
				if (colInfo != null) {
					DescriptionEnum property = colInfo.getHeadColumn().getProperty();
					if (property.equals(DescriptionEnum.AmountSum)) {
						BigDecimal value = (BigDecimal) page.getCell(0, j).getValue();
						table.getCell(i - 2, 2).setValue(value);
						if (value != null) {
							sum = sum.add(value);
						}
						break;
					}
				}
			}
		}
		table.getCell(pageCount, "amount").setValue(sum);
		BigDecimal scale = FDCHelper.ZERO;
		if (table.getCell(pageCount + 1, "amount").getValue() != null) {
			scale = (BigDecimal) table.getCell(pageCount + 1, "amount")
					.getValue();
		}
		sum = sum.setScale(2, BigDecimal.ROUND_HALF_UP);
		BigDecimal amount = sum.multiply(scale).divide(FDCHelper.ONE_HUNDRED,
				BigDecimal.ROUND_HALF_UP);
		table.getCell(pageCount + 2, "amount").setValue(amount);
		table.getCell(pageCount + 3, "amount").setValue(sum.add(amount));
	}

	private static void setTableScript(KDTable table) {
		int rowCount = table.getRowCount();
		int amountIndex = 0;
		int amountSum = 0;
		int projectAmtIndex = 0;
		int projectAmtSum = 0;
		int totalPriceIndex = 0;
		int totalPriceSum = 0;
		for (int i = 3, colCount = table.getColumnCount(); i < colCount; i++) {
			IColumn col = table.getColumn(i);
			if (col.getUserObject() != null
					&& col.getUserObject() instanceof NewListingColumnInfo) {
				NewListingColumnInfo column = (NewListingColumnInfo) col
						.getUserObject();
				DescriptionEnum property = column.getHeadColumn().getProperty();
				if (property.equals(DescriptionEnum.ProjectAmt)) {
					if (projectAmtIndex == 0) {
						projectAmtIndex = i;
					}
				} else if (property.equals(DescriptionEnum.ProjectAmtSum)) {
					projectAmtSum = i;
					if (projectAmtIndex == 0) {
						projectAmtIndex = i;
					}
				} else if (property.equals(DescriptionEnum.TotalPrice)) {
					if (totalPriceIndex == 0) {
						totalPriceIndex = i;
					}
				} else if (property.equals(DescriptionEnum.TotalPriceSum)) {
					totalPriceSum = i;
					if (totalPriceIndex == 0) {
						totalPriceIndex = i;
					}
				} else if (property.equals(DescriptionEnum.Amount)) {
					if (amountIndex == 0) {
						amountIndex = i;
					}
				} else if (property.equals(DescriptionEnum.AmountSum)) {
					amountSum = i;
					if (amountIndex == 0) {
						amountIndex = i;
					}
				}
			}
		}
		for (int i = 1; i < rowCount; i++) {
			if (isLeafRow(table, i)) {
				if (projectAmtIndex < projectAmtSum) {
					TableScriptHelper.setTableRowSumFormalu(table.getCell(i,
							projectAmtSum), projectAmtSum - projectAmtIndex);
				}
				if (totalPriceIndex < totalPriceSum) {
					if (table.getCell(i, "isCompose").getValue() != null) {
						boolean isCompose = ((Boolean) table.getCell(i,
								"isCompose").getValue()).booleanValue();
						if (isCompose) {
							TableScriptHelper.setTableRowSumFormalu(table
									.getCell(i, totalPriceSum), totalPriceSum
									- totalPriceIndex);
						}
					}
				}
				for (int j = 0; j <= projectAmtSum - projectAmtIndex; j++) {
					TableScriptHelper.setTableMultiplyFormalu(table.getCell(i,
							amountIndex + j), table.getCell(i, projectAmtIndex
							+ j), table.getCell(i, totalPriceSum));
				}
			}
		}
	}

	public static void setTableLock(final KDTable table, boolean isLock) {
		// if(true)return;
		int priceCount = 0;
		for (int i = 3, colCount = table.getColumnCount(); i < colCount; i++) {
			IColumn col = table.getColumn(i);
			NewListingColumnInfo colInfo = (NewListingColumnInfo) col
					.getUserObject();
			DescriptionEnum property = colInfo.getHeadColumn().getProperty();
			if (property.equals(DescriptionEnum.TotalPrice)) {
				priceCount++;
			}
		}

		for (int i = 1; i < table.getRowCount(); i++) {
			IRow row = table.getRow(i);
			NewListingEntryInfo entry = (NewListingEntryInfo) row
					.getUserObject();
			for (int j = 0; j < table.getColumnCount(); j++) {
				IColumn column = table.getColumn(j);
				ICell cell = row.getCell(j);
				cell.getStyleAttributes().setLocked(true);
				NewListingColumnInfo colInfo = (NewListingColumnInfo) column
						.getUserObject();
				if (colInfo == null) {
					continue;
				}
				DescriptionEnum property = colInfo.getHeadColumn()
						.getProperty();
				if (property.equals(DescriptionEnum.TotalPrice)) {
					if (entry.isIsCompose()) {
						cell.getStyleAttributes().setLocked(isLock);
					} else {
						cell.getStyleAttributes()
								.setBackground(Color.lightGray);
					}
				} else if (property.equals(DescriptionEnum.TotalPriceSum)) {
					if (!entry.isIsCompose() || priceCount == 0) {
						cell.getStyleAttributes().setLocked(isLock);
					}
				} else if (colInfo.isIsQuoting()) {
					cell.getStyleAttributes().setLocked(isLock);
				}
			}
		}
		for (int i = 1; i < table.getRowCount(); i++) {
			if (!isLeafRow(table, i)) {
				table.getRow(i).getCell(2).setValue(null);
				table.getRow(i).getStyleAttributes().setBackground(
						NON_LEAF_COLOR);
			}
		}
	}

	private void setTableEvent(final KDTable table) {
		table.addKDTEditListener(new KDTEditAdapter() {
			// �༭������
			public void editStopped(KDTEditEvent e) {
				// IRow row = table.getRow(e.getRowIndex());
				IColumn col = table.getColumn(e.getColIndex());
				if (col.getUserObject() != null
						&& col.getUserObject() instanceof NewListingColumnInfo) {
					NewListingColumnInfo colInfo = (NewListingColumnInfo) col
							.getUserObject();
					DescriptionEnum property = colInfo.getHeadColumn()
							.getProperty();
					if (property.equals(DescriptionEnum.TotalPrice)
							|| property.equals(DescriptionEnum.TotalPriceSum)) {
						setUnionData(table,tabbedPnlTable);
					}
				}
			}
		});
	}

	public static void setTableColumn(final KDTable table, NewListingPageInfo page) {
		NewListingColumnCollection collColumn = page.getColumns();
		int sizeColumn = collColumn.size();
		if (sizeColumn > 0) {
			table.addHeadRows(2);
			IRow row = table.getHeadRow(1);
			IRow parentRow = table.getHeadRow(0);
			IColumn col = table.addColumn(0);
			col.setKey("level");
			col.setWidth(30);
			row.getCell("level").setValue("����");
			col.getStyleAttributes().setLocked(true);
			IColumn colIsKey = table.addColumn(1);
			colIsKey.setKey("isKey");
			colIsKey.setWidth(60);
			row.getCell("isKey").setValue("�ؼ���Ŀ");
			colIsKey.getStyleAttributes().setLocked(true);
			IColumn colIsCompose = table.addColumn(2);
			colIsCompose.setKey("isCompose");
			colIsCompose.setWidth(60);
			row.getCell("isCompose").setValue("���۹���");
			colIsCompose.getStyleAttributes().setLocked(true);
			if(page != null && page.getHeadType() != null){
				if(page.getHeadType().isIsDefined()){
					table.getColumn(1).getStyleAttributes().setHided(true);
					table.getColumn(2).getStyleAttributes().setHided(true);
				}
			}
			int count = 0;
			KDTMergeManager mm = table.getHeadMergeManager();
			mm.mergeBlock(0, 0, 1, 0);
			mm.mergeBlock(0, 1, 1, 1);
			mm.mergeBlock(0, 2, 1, 2);
			int baseCount = 3;
			for (int j = 0; j < sizeColumn; j++) {
				NewListingColumnInfo listColInfo = collColumn.get(j);
				HeadColumnInfo infoColumn = listColInfo.getHeadColumn();
				IColumn column = table.addColumn();
				column.setKey(infoColumn.getName());
				column.setUserObject(listColInfo);
				row.getCell(j + baseCount).setValue(infoColumn.getName());
				if (infoColumn.getColumnType().equals(ColumnTypeEnum.Amount)) {
					KDFormattedTextField formattedTextField = new KDFormattedTextField(
							KDFormattedTextField.BIGDECIMAL_TYPE);
					
					formattedTextField.setRemoveingZeroInDispaly(false);
					formattedTextField.setRemoveingZeroInEdit(false);
					formattedTextField.setNegatived(false);
					formattedTextField.setSupportedEmpty(true);
					formattedTextField.setMaximumValue(FDCHelper.MAX_VALUE);
					formattedTextField.setMinimumValue(FDCHelper.MIN_VALUE);
					formattedTextField
							.setHorizontalAlignment(KDFormattedTextField.RIGHT);
					ICellEditor numberEditor = new KDTDefaultCellEditor(
							formattedTextField);
					if(isRate && infoColumn.isIsQuoting()){
						formattedTextField.setPrecision(4);
						column.getStyleAttributes().setNumberFormat(
								"#,##0.0000;-#,##0.0000");
					}else{
						formattedTextField.setPrecision(2);
						column.getStyleAttributes().setNumberFormat(
								"#,##0.00;-#,##0.00");
					}
					column.setEditor(numberEditor);
					
					column.getStyleAttributes().setHorizontalAlign(
							HorizontalAlignment.RIGHT);
					// FDCHelper.formatTableNumber(table, infoColumn.getName());
				} else if (infoColumn.getColumnType().equals(
						ColumnTypeEnum.Date)) {
					//����һ�����ڿؼ� 2008-05-28 ����
					com.kingdee.bos.ctrl.swing.KDDatePicker pkauditDate = new com.kingdee.bos.ctrl.swing.KDDatePicker();
					ICellEditor dateEditor = new KDTDefaultCellEditor(pkauditDate);
					column.setEditor(dateEditor);
					column.getStyleAttributes().setNumberFormat(
							"yyyy-m-d");
				} else if (infoColumn.getColumnType().equals(
						ColumnTypeEnum.String)) {
					column.getStyleAttributes().setWrapText(true);
				}
				if ((infoColumn.getParent() != null)) {
					parentRow.getCell(j + baseCount).setValue(
							infoColumn.getParent().getName());
					if (j < sizeColumn - 1) {
						HeadColumnInfo infoNext = collColumn.get(j + 1)
								.getHeadColumn();
						if (infoNext.getParent() != null
								&& infoColumn.getParent().equals(
										infoNext.getParent())) {
							count++;
						} else {
							if (count == 0) {
								mm.mergeBlock(0, j + baseCount, 1, j
										+ baseCount,
										KDTMergeManager.SPECIFY_MERGE);
							} else {
								mm
										.mergeBlock(0, j - count + baseCount,
												0, j + baseCount,
												KDTMergeManager.DATA_UNIFY);
								count = 0;
							}
						}
					} else {
						mm.mergeBlock(0, j - count + baseCount, 0, j
								+ baseCount, KDTMergeManager.DATA_UNIFY);
						count = 0;
					}
				} else {
					mm.mergeBlock(0, j + baseCount, 1, j + baseCount);
				}
			}
		}
		table.getColumn("��Ŀ����").setWidth(400);
	}
	static class myF7 extends KDBizPromptBox{
		protected Object stringToValue(String t) {
			Object obj = super.stringToValue(t);
			if (obj instanceof IObjectValue) {
				return obj;
			} else {
				return t;
			}
		}
	}

	/**
	 * 
	 * ���������ñ���У�ֻ��������е���Ŀ���ƣ����ۺϵ��ۣ���������δ�������ݡ�
	 * @param isExporting �Ƿ����⵼������
	 * @Author��owen_wen
	 * @CreateTime��2011-10-27
	 */
	public static void setTableRow(final KDTable table, NewListingPageInfo page, boolean isExporting) {
		NewListingEntryCollection collEntry = page.getEntrys();
		NewListingEntryInfo infoEntry = null;
		int sizeEntry = collEntry.size();
		IRow totalRow = table.addRow();
		totalRow.getCell(3).setValue("�ϼ�");
		totalRow.getStyleAttributes().setBackground(Color.lightGray);
		totalRow.getStyleAttributes().setLocked(true);
		// ����ǹ�Ӧ���Զ���ҳǩ�����⵼��ʱ������Ҫ��������
		if (page.getHeadType().isIsDefined() && isExporting) {
			return;
		}
		for (int k = 0; k < sizeEntry; k++) {
			IRow dataRow = table.addRow();
			infoEntry = collEntry.get(k);
			dataRow.setUserObject(infoEntry);
			if (infoEntry.getItem() != null) {
				dataRow.getCell("��Ŀ����").setValue(infoEntry.getItem());
			} else {
				dataRow.getCell("��Ŀ����").setValue(infoEntry.getItemName());
			}
			KDBizPromptBox f7 = new myF7();
			f7.setDisplayFormat("$name$");
			f7.setCommitFormat("$name$");
			f7.setEditFormat("$name$");
			f7.setQueryInfo("com.kingdee.eas.fdc.invite.app.F7ListingItemQuery");
			EntityViewInfo evInfo = new EntityViewInfo();
			FilterInfo filter = new FilterInfo();
			FilterItemCollection filterItems = filter.getFilterItems();
			filterItems.add(new FilterItemInfo("headType.id", page.getHeadType().getId().toString(), CompareType.EQUALS));
			evInfo.setFilter(filter);
			f7.setEntityViewInfo(evInfo);
			f7.setEditable(true);
			CellTextRenderImpl avr = new CellTextRenderImpl();
			avr.getText(new BizDataFormat("$name$"));
			dataRow.getCell("��Ŀ����").setEditor(new KDTDefaultCellEditor(f7));
			dataRow.getCell("��Ŀ����").setRenderer(avr);
			dataRow.getCell("level").setValue(new Integer(infoEntry.getLevel()));
			dataRow.getCell("isKey").setValue(Boolean.valueOf(infoEntry.isIsKey()));
			dataRow.getCell("isCompose").setValue(Boolean.valueOf(infoEntry.isIsCompose()));
			dataRow.setTreeLevel(infoEntry.getLevel());
		}
	}

	public static int getMaxLevel(KDTable table) {
		int levelmax = 0;
		for (int i = 1; i < table.getRowCount(); i++) {
			int thisLevel = Integer.parseInt(table.getCell(i, "level").getValue().toString());
			if (thisLevel > levelmax) {
				levelmax = thisLevel;
			}
		}
		return levelmax;
	}

	class SupplierF7CommitListener implements ChangeListener {
		KDTextField kDTextNumber;

		public SupplierF7CommitListener(KDTextField kDTextNumber) {
			this.kDTextNumber = kDTextNumber;
		}

		public void stateChanged(ChangeEvent arg0) {
			KDBizPromptBox box = (KDBizPromptBox) arg0.getSource();
			if (box.getData() != null) {
				if(box.getData() instanceof SupplierQualifyEntryInfo){
					SupplierQualifyEntryInfo info = (SupplierQualifyEntryInfo) box.getData();
					kDTextNumber.setText(info.getSupplier().getNumber());
				}else if(box.getData() instanceof SupplierInfo){
					SupplierInfo info = (SupplierInfo) box.getData();
					kDTextNumber.setText(info.getName());
				}
				
			} else {
				kDTextNumber.setText(null);
			}
		}

	}

	/**
	 * �����������е������ݣ���������Ŀ���ƣ��������ۺϵ��ۣ�����������
	 */
	public static void loadTableData(KDTable table, NewListingPageInfo page,
			String quotingId,QuotingPriceTypeEnum priceTypeEnum) {
		Map values = new HashMap();
		NewListingValueCollection values1 = null;
		EntityViewInfo view1 = new EntityViewInfo();
		FilterInfo filter1 = new FilterInfo();
		filter1.appendFilterItem("entry.head", page.getId().toString()); // ��ҳǩpage�µ�NewListingValue
		filter1.appendFilterItem("column.isQuoting", Boolean.FALSE); // �Ǳ�����
		view1.setFilter(filter1);
		view1.getSelector().add(new SelectorItemInfo("*"));
		view1.getSelector().add(new SelectorItemInfo("entry.*"));
		view1.getSelector().add(new SelectorItemInfo("column.*"));

		NewListingValueCollection values2 = null;
		EntityViewInfo view2 = new EntityViewInfo();
		FilterInfo filter2 = new FilterInfo();
		filter2.appendFilterItem("entry.head", page.getId().toString());
		filter2.appendFilterItem("quotingPrice.id", quotingId); // ������id���˵�NewListingValue
		if (QuotingPriceTypeEnum.modify.equals(priceTypeEnum)) {// ���޸Ļ�ԭʼ���۹���
			filter2.appendFilterItem("type", QuotingPriceTypeEnum.modify);
		} else if (QuotingPriceTypeEnum.original.equals(priceTypeEnum)) {
			filter2.appendFilterItem("type", QuotingPriceTypeEnum.original);
		}
		view2.setFilter(filter2);
		view2.getSelector().add(new SelectorItemInfo("*"));
		view2.getSelector().add(new SelectorItemInfo("entry.*"));
		view2.getSelector().add(new SelectorItemInfo("column.*"));
		try {
			values1 = NewListingValueFactory.getRemoteInstance().getNewListingValueCollection(view1);
			values2 = NewListingValueFactory.getRemoteInstance().getNewListingValueCollection(view2);
		} catch (BOSException e) {
			e.printStackTrace();
		}
		if(values1==null||values2==null)
			return;
		for (int i = 0; i < values1.size(); i++) {
			NewListingValueInfo valueInfo = values1.get(i);
			String entryId = valueInfo.getEntry().getId().toString();
			String colId = valueInfo.getColumn().getId().toString();
			String key = entryId + colId;
			values.put(key, valueInfo);
		}
		for (int i = 0; i < values2.size(); i++) {
			NewListingValueInfo valueInfo = values2.get(i);
			String entryId = valueInfo.getEntry().getId().toString();
			String colId = valueInfo.getColumn().getId().toString();
			String key = entryId + colId;
			values.put(key, valueInfo);
		}
		for (int colIndex = 0; colIndex < table.getColumnCount(); colIndex++) {
			IColumn column = table.getColumn(colIndex);
			NewListingColumnInfo colInfo = (NewListingColumnInfo) column.getUserObject();
			if (colInfo != null) {
				ColumnTypeEnum colType = colInfo.getHeadColumn().getColumnType();
				for (int rowIndex = 1; rowIndex < table.getRowCount(); rowIndex++) {
					IRow row = table.getRow(rowIndex);
					NewListingEntryInfo entry = (NewListingEntryInfo) row.getUserObject();
					NewListingValueInfo value = (NewListingValueInfo) values.get(entry.getId().toString() + colInfo.getId().toString());
					if (!colInfo.getHeadColumn().getName().equals("��Ŀ����")) {
						InviteHelper.loadListingValue(row.getCell(colIndex), value, colType);
					}
				}
			}
		}
	}

	/**
	 * ��������:��״̬����ʾ��Ϣ
	 */
	protected void showBarInfo(CoreUI ui,String info) {
		ui.setMessageText(info);
		setNextMessageText(info);
		ui.setShowMessagePolicy(SHOW_MESSAGE_DEFAULT);
		setIsShowTextOnly(false);
		ui.showMessage();
	}

	/**
	 * �������������Ϸ�������䵽ҳ��
	 */
	public void importSheetToNewSheet(Sheet sheet, KDTable table) {
		int maxRow = sheet.getMaxRowIndex();
		int maxColumn = sheet.getMaxColIndex();
		for (int col = 2; col <= maxColumn; col++) {
			IColumn column = table.getColumn(col);
			Object colUserObj = column.getUserObject();
			if (colUserObj != null
					&& colUserObj instanceof NewListingColumnInfo) {
				NewListingColumnInfo colInfo = (NewListingColumnInfo) colUserObj;
				if (!colInfo.isIsQuoting()) {
					continue;
				}
				ColumnTypeEnum colType = colInfo.getHeadColumn()
						.getColumnType();
				for (int row = 2; row <= maxRow; row++) {
					Cell excelCell = sheet.getCell(row, col, true);
					Variant rawVal = excelCell.getValue();
					if (Variant.isNull(rawVal)) {
						continue;
					}
					InviteHelper.loadExcelVal(table.getCell(row - 2, col), rawVal, colType);
				}
			}
		}
	}

	protected void verifyInput(ActionEvent e) throws Exception {
		super.verifyInput(e);
		FDCClientVerifyHelper.verifyRequire(this);
		if(editData.getSupplier()==null)
			return;
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("supplier", editData.getSupplier().getId().toString()));
		filter.getFilterItems().add(new FilterItemInfo("listing", editData.getListing().getId().toString()));
		if (editData.getId() != null) {
			filter.getFilterItems().add(new FilterItemInfo("id", editData.getId().toString(), CompareType.NOTEQUALS));
		}
		if (NewQuotingPriceFactory.getRemoteInstance().exists(filter)) {
			throw new FDCInviteException(FDCInviteException.SUPPLIER_IS_OVER);
		}
	}

	public SelectorItemCollection getSelectors() {
		SelectorItemCollection sic = super.getSelectors();
		sic.add("*");
		sic.add("supplier.*");
		return sic;
	}

	/**
	 * ���ø���Ŀ������
	 *
	 * @param table
	 * @param amountColumns
	 */
	public static void setUnionData(KDTable table,final KDTabbedPane tabbedPnlTable) {
		setTableScript(table);
		List colList = new ArrayList();
		for (int i = 3; i < table.getColumnCount(); i++) {
			IColumn column = table.getColumn(i);
			if (column.getUserObject() != null
					&& column.getUserObject() instanceof NewListingColumnInfo) {
				NewListingColumnInfo col = (NewListingColumnInfo) column
						.getUserObject();
				if (col != null
						&& (col.getHeadColumn().getProperty()
								.equals(DescriptionEnum.AmountSum))) {
					colList.add(new Integer(i));
				}
			}
		}
		for (int i = 1; i < table.getRowCount(); i++) {
			IRow row = table.getRow(i);
			// ���û�����
			int level = row.getTreeLevel();
			int maxLevel = getMaxLevel(table);
			if (level == maxLevel) {
				continue;
			}
			List aimRowList = new ArrayList();
			for (int j = i + 1; j < table.getRowCount(); j++) {
				IRow rowAfter = table.getRow(j);
				if (rowAfter.getTreeLevel() <= level) {
					break;
				}
				if (isLeafRow(table, j))
					aimRowList.add(rowAfter);

			}
			if (aimRowList.size() > 0) {
				for (int j = 0; j < colList.size(); j++) {
					Integer colNum = (Integer) colList.get(j);
					int colIndex = colNum.intValue();
					BigDecimal sum = null;
					for (int rowIndex = 0; rowIndex < aimRowList.size(); rowIndex++) {
						IRow rowAdd = (IRow) aimRowList.get(rowIndex);
						Object value = rowAdd.getCell(colIndex).getValue();
						if (value != null) {
							if (sum == null) {
								sum = FMConstants.ZERO;
							}
							sum = sum.add(FDCHelper.toBigDecimal(value));
						}
					}
					row.getCell(colIndex).setValue(sum);
				}
			}

		}
		setTableTotal(table);
		table.getScriptManager().runAll();
		updateTotalPageData(tabbedPnlTable);
		// setTableLock(table, true);

	}

	private static void setTableTotal(KDTable table) {
		int amountSumCol = -1;
		for (int i = 3; i < table.getColumnCount(); i++) {
			IColumn column = table.getColumn(i);
			NewListingColumnInfo col = (NewListingColumnInfo) column
					.getUserObject();
			if (col != null
					&& (col.getHeadColumn().getProperty()
							.equals(DescriptionEnum.AmountSum))) {
				amountSumCol = i;
				break;
			}
		}
		if (amountSumCol == -1) {
			return;
		}
		IRow row = (IRow) table.getRow(0);
		List aimRowList = new ArrayList();
		for (int j = 1; j < table.getRowCount(); j++) {
			IRow rowAfter = table.getRow(j);
//			NewListingEntryInfo info = (NewListingEntryInfo) table.getRow(j).getUserObject();
			if (isLeafRow(table, j))
//			if(info.isIsLeaf())
				aimRowList.add(rowAfter);
		}
		if (aimRowList.size() > 0) {
			BigDecimal sum = FMConstants.ZERO;
			for (int rowIndex = 0; rowIndex < aimRowList.size(); rowIndex++) {
				IRow rowAdd = (IRow) aimRowList.get(rowIndex);
				Object value = rowAdd.getCell(amountSumCol).getValue();
				sum = sum.add(FDCHelper.toBigDecimal(value));
			}
			row.getCell(amountSumCol).setValue(sum);
		}
		
		
	}

	/**
	 * 
	 * �������ж��Ƿ���Ҷ���С�<br>
	 * 2011-10-27 �޸ģ�֮ǰ��row.getTreeLevel()��ʱ��ȡ����ֵ������table.getCell(rowIndex, "level").getValue()
	 */
	public static boolean isLeafRow(KDTable table, int rowIndex) {
		if (rowIndex + 1 == table.getRowCount()) {
			return true;
		}
		int rowLevel = 0;
		if (table.getCell(rowIndex, "level").getValue() != null) {
			rowLevel = ((Integer) table.getCell(rowIndex, "level").getValue()).intValue();
		}
		int nextLevel = ((Integer) table.getCell(rowIndex + 1, "level").getValue()).intValue();
		if (nextLevel <= rowLevel) {
			return true;
		}
		return false;
	}

	public void verifyData() {
		boolean isErr = false;
		for (int i = 2; i < this.tabbedPnlTable.getTabCount(); i++) {
			KDTable table = (KDTable) this.tabbedPnlTable.getComponentAt(i);
			// У��ͬ���Ƽ۸��Ƿ���ͬ
			Map nameMap = new HashMap();
			for (int j = 1; j < table.getRowCount(); j++) {
				IRow row = table.getRow(j);
				ICell nameCell = row.getCell("��Ŀ����");
				String name = null;
				if (nameCell.getValue() instanceof String) {
					name = (String) nameCell.getValue();
				} else {
					if (nameCell.getValue() != null) {
						name = ((ListingItemInfo) nameCell.getValue())
								.getName();
					}
				}
				Color errColor = LEAF_COLOR;
				int colCount = table.getColumnCount();
				if (nameMap.containsKey(name)) {
					IRow fRow = (IRow) nameMap.get(name);
					for (int k = 0; k < colCount; k++) {
						NewListingColumnInfo colInfo = (NewListingColumnInfo) table.getColumn(k).getUserObject();
						ICell cell = row.getCell(k);
						if (!cell.getStyleAttributes().isLocked()) {
							cell.getStyleAttributes().setBackground(REQUIREDCOLOR);
							if (colInfo.getHeadColumn().getColumnType().equals(ColumnTypeEnum.Amount)) {
								BigDecimal fValue = (BigDecimal) fRow.getCell(k).getValue();
								if (fValue == null) {
									fValue = FDCHelper.ZERO;
								}
								BigDecimal value = (BigDecimal) row.getCell(k).getValue();
								if (value == null) {
									value = FDCHelper.ZERO;
								}
								if (value.compareTo(fValue) != 0) {
									cell.getStyleAttributes().setBackground(errColor);
									isErr = true;
								}
							}
						}
					}

				} else {
					nameMap.put(name, row);
				}
			}
		}
		if (isErr) {
			MsgBox.showInfo("������Ŀ������ͬ���б��۲�ͬ!");
			this.abort();
		}
	}

	public void actionSubmit_actionPerformed(ActionEvent e) throws Exception {
		this.verifyData();
		super.actionSubmit_actionPerformed(e);
		
//		storeNewListingValues();
		storeNewListingValuesByMySelf();
		isModify = false;
		CacheServiceFactory.getInstance().discardType(new NewListingPageInfo().getBOSType());
		CacheServiceFactory.getInstance().discardType(new NewListingColumnInfo().getBOSType());
		CacheServiceFactory.getInstance().discardType(new NewListingEntryInfo().getBOSType());
		CacheServiceFactory.getInstance().discardType(new NewListingValueInfo().getBOSType());
		CacheServiceFactory.getInstance().discardType(new ListingPageSumEntryInfo().getBOSType());
	}

	/**
	 * 
	 * ���������浼����嵥���ݡ�����ʵ���������������ʷ���ݣ��ٱ��档
	 * @throws Exception
	 * @Author��owen_wen
	 * @CreateTime��2011-11-3
	 */
	private void storeNewListingValuesByMySelf() throws Exception {
		if (this.editData.getStatus().equals(QuotingPriceStatusEnum.NoImportPrice)) {
			isModify = false;
			return;
		}		
		
		// ����newListingValues�������������
		// 1) �Թ�Ӧ�̶��ԣ�����Ҫ����newListingEntry��
		// 2) ���ڼ׷�������Ҫ����newListingEntry��ֻ��Ҫ����newListingValues
		for (int i = 2; i < this.tabbedPnlTable.getTabCount(); i++) {
			KDTable table = (KDTable) tabbedPnlTable.getComponentAt(i);
			NewListingPageInfo page = (NewListingPageInfo) table.getUserObject();
			// ����������ȴ���Ӧ���Զ���ҳǩ
			if (page.getHeadType().isIsDefined()) {
				clearSupplierNewsListingEntries();
				// ��ΪclearSupplierNewsListingEntries����ɾ���˲���Entry����������ȡ��һ��
				SelectorItemCollection sic = new SelectorItemCollection();
				sic.add(new SelectorItemInfo("headType.isDefined"));
				page = NewListingPageFactory.getRemoteInstance().getNewListingPageInfo(new ObjectUuidPK(page.getId()), sic);
				page.setDescription((String) ((KDTable) tabbedPnlTable.getComponentAt(0)).getCell(i - 2, "des").getValue());

				for (int rowIndex = 1; rowIndex < table.getRowCount(); rowIndex++) {
					IRow row = table.getRow(rowIndex);
					if (!(row.getUserObject() instanceof NewListingEntryInfo))
						return;

					NewListingEntryInfo rowEntryInfo = (NewListingEntryInfo) row.getUserObject();
					NewListingEntryInfo newEntryInfo = new NewListingEntryInfo();
					newEntryInfo.setHead(page);
					newEntryInfo.setId(rowEntryInfo.getId());
					newEntryInfo.setLevel(Integer.parseInt(row.getCell("level").getValue().toString()));
					if (row.getCell("��Ŀ����").getValue() instanceof ListingItemInfo) {
						ListingItemInfo item = (ListingItemInfo) row.getCell("��Ŀ����").getValue();
						newEntryInfo.setItem(item);
						newEntryInfo.setItemName(item.getName());
						newEntryInfo.setItemNumber(item.getNumber());
					} else {
						if (row.getCell("��Ŀ����").getValue() != null) {
							newEntryInfo.setItemName(row.getCell("��Ŀ����").getValue().toString());
							newEntryInfo.setItem(null);
						}
					}

					newEntryInfo.setSeq(rowIndex); // ���������Ӧ�̣���ô����seq���񲻶� �ˣ�
					newEntryInfo.setIsLeaf(rowEntryInfo.isIsLeaf());
					newEntryInfo.setIsCanZeroProAmount(rowEntryInfo.isIsCanZeroProAmount());

					if (row.getCell("isKey").getValue() != null) { // �ؼ���Ŀ
						newEntryInfo.setIsKey(((Boolean) row.getCell("isKey").getValue()).booleanValue());
					} else {
						newEntryInfo.setIsKey(false);
					}

					if (row.getCell("isCompose").getValue() != null) { // ���۹���
						newEntryInfo.setIsCompose(((Boolean) row.getCell("isCompose").getValue()).booleanValue());
					} else {
						newEntryInfo.setIsCompose(false);
					}
					
					NewListingEntryInfo cloneNewListingEntryInfo = (NewListingEntryInfo) newEntryInfo.clone();//��һ�䲻�ܷŵ�����ȥ����¡֮ǰ����ȷ�����ķ�¼�ǿյ�
					NewListingValueCollection valueColl = getRowValues(row, table, newEntryInfo, page);
					newEntryInfo.getValues().addCollection(valueColl);
					page.getEntrys().add(newEntryInfo);
					
					// ����ԭʼ����ʱ��Ҫ��¡һ����Ϊ��������
					if (QuotingPriceTypeEnum.original.equals(priceTypeEnum)) { 
						cloneNewListingEntryInfo.setBoolean("isCloned", true); //�Ӹ���¡��־
						NewListingValueCollection valueColl2 = getRowValues(row, table, cloneNewListingEntryInfo, page);
						cloneNewListingEntryInfo.getValues().addCollection(valueColl2);
						page.getEntrys().add(cloneNewListingEntryInfo);
					}
				}
				
				/* print all data, use for debug 
				System.out.println("page.getName()" + page.getDescription() + "; page.getId()=" + page.getId());
				for (int k = 0; k < page.getEntrys().size(); k++) {
					System.out.println("��Ŀ���ƣ�" + page.getEntrys().get(k).getItemName());
					for (int m = 0; m < page.getEntrys().get(k).getValues().size(); m++) {
						System.out.print("\t �嵥���ݣ����=" + page.getEntrys().get(k).getValues().get(m).getAmount() + "\t�ı���Ϣ��" + page.getEntrys().get(k).getValues().get(m).getText() + "\t���ڣ�"
								+ page.getEntrys().get(k).getValues().get(m).getDateValue());
						System.out.print(" page.id = " + page.getEntrys().get(k).getValues().get(m).getEntry().getHead().getId());
						System.out.println("\t type = " + page.getEntrys().get(k).getValues().get(m).getType());
					}
				}
				*/		
				
				NewListingPageFactory.getRemoteInstance().save(page);
			} else {// ��Ӧ��ҳǩ������ϣ��ִ���׷������ҳǩ
				clearPartANewsListingValues(page);
				// ��ΪclearPartANewsListingValues����ɾ���˲���newListingValue����������ȡ��һ��
				page = NewListingPageFactory.getRemoteInstance().getNewListingPageInfo(new ObjectUuidPK(page.getId()));

				for (int rowIndex = 1; rowIndex < table.getRowCount(); rowIndex++) {
					IRow row = table.getRow(rowIndex);
					if (!isLeafRow(table, rowIndex)) // �������ĩ���ڵ㣬��û��values��
						continue;

					for (int j = 0; j < page.getEntrys().size(); j++) {
						NewListingEntryInfo entryInfo = page.getEntrys().get(j);
						// �����Ŀ���ơ���λ����������ͬ������Ϊ����ȵ�Entry
						if (row.getCell("��Ŀ����").getValue().toString().equals(entryInfo.getItemName())) {
							if (entryInfo.getValues().get(1).getText() != null // ��λ��ͬ
									&& entryInfo.getValues().get(1).getText().equals(row.getCell("��λ").getValue().toString())) {
								if (entryInfo.getValues().get(2).getAmount() != null&&row.getCell("������").getValue()!=null // �ۺϵ�����ͬ
										&&entryInfo.getValues().get(2).getAmount().compareTo((BigDecimal)row.getCell("������").getValue())== 0) {
									entryInfo.getValues().addCollection(getRowValues(row, table, entryInfo, page));
									break; // ����entryInfo�����еıȽϽ�������ʼ��һ��
								}
							}
						}
					}
				}
				NewListingPageFactory.getRemoteInstance().save(page);
			}
		}
	}

	/**
	 * ����������׷�ҳǩĳ��Ӧ�̵��嵥���ݣ������ǣ�����ID�����۵Ĺ�Ӧ�̡��������͡�����ҳǩ��ͬ
	 * @Author��owen_wen
	 * @CreateTime��2011-11-6
	 */
	private void clearPartANewsListingValues(NewListingPageInfo page) throws EASBizException, BOSException {
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("quotingPrice.id", editData.getString("id")));
		filter.getFilterItems().add(new FilterItemInfo("quotingPrice.supplier.id", editData.getSupplier().getId().toString()));
		filter.getFilterItems().add(new FilterItemInfo("type", new Integer(priceTypeEnum.equals(QuotingPriceTypeEnum.modify) ? 1 : 0)));
		filter.getFilterItems().add(new FilterItemInfo("entry.head.id", page.getId().toString()));
		NewListingValueFactory.getRemoteInstance().delete(filter);
	}

	/** 
	 * ���ݱ���ID����Ӧ��ID����������иñ����µ��嵥���ݣ�NewListingValue�����׷���Ӧ��NewListingEntry����Ҫ�����
	 * @Author��owen_wen
	 * @CreateTime��2011-11-6
	 */
	private void clearSupplierNewsListingEntries() throws BOSException, EASBizException {
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("quotingPrice.id", editData.getString("id")));
		filter.getFilterItems().add(new FilterItemInfo("quotingPrice.supplier.id", editData.getSupplier().getId().toString()));
		filter.getFilterItems().add(new FilterItemInfo("type", new Integer(priceTypeEnum.equals(QuotingPriceTypeEnum.modify) ? 1 : 0)));
		EntityViewInfo view = new EntityViewInfo();
		view.setFilter(filter);
		NewListingValueCollection newListingValues = NewListingValueFactory.getRemoteInstance().getNewListingValueCollection(view);
		Set newListingEntryIdSet = new HashSet();
		for (int i = 0; i < newListingValues.size(); i++) {
			newListingEntryIdSet.add(newListingValues.get(i).getEntry().getId().toString());
		}

		if (!newListingEntryIdSet.isEmpty()) {
			filter.getFilterItems().clear();
			filter.getFilterItems().add(new FilterItemInfo("id", newListingEntryIdSet, CompareType.INCLUDE));
			filter.getFilterItems().add(new FilterItemInfo("head.headType.isDefined", new Integer(1))); // �ǹ�Ӧ�̶���ҳǩ����Ҫ���
			NewListingEntryFactory.getRemoteInstance().delete(filter); // ɾ����ǰ��Ӧ�̶�Ӧ���嵥��¼������Ҳɾ�����嵥����
		}
	}
	
	/**
	 * �������д�Ĵ��룬�߼����ڸ��ӣ�����д�ˣ���������Ҫ��ɾ�� owen_wen 2011-11-6
	 * @deprecated 
	 * @see {@link NewQuotingPriceEditUI} �е� storeNewListingValuesByMySelf()
	 */
	private void storeNewListingValues() throws Exception {
		List pageId = new ArrayList();
		// begin ---- xuisan
		KDTable desTable = (KDTable) this.tabbedPnlTable.getComponentAt(0);
		
		if (this.editData.getStatus().equals(QuotingPriceStatusEnum.NoImportPrice)) {
			isModify = false;
			return;
		}
		
		boolean isChange = false;
		if(this.getUITitle().toString().contains("����")){
			 isChange = true;
		}
		
		CoreBaseCollection baseCollection = new CoreBaseCollection();
		for (int i = 0; i < this.tabbedPnlTable.getTabCount(); i++) {
			KDTable table = (KDTable) tabbedPnlTable.getComponentAt(i);			
			NewListingPageInfo page = (NewListingPageInfo) table.getUserObject();
			if (page != null) {
				
				int projectAmtIndex = 0; // ������
				int projectAmtSum = 0; // ������С��
				int totalPriceIndex = 0; // �ۺϵ���
				int totalPriceSum = 0; 
				int amountIndex = 0;
				int amountSum = 0;
				
				if("������".equals(table.getColumn(5).getKey())){
					projectAmtIndex = 5;
					projectAmtSum = 5;
				}else{
					projectAmtIndex = 5;
					for(int j = projectAmtIndex; j < table.getColumnCount(); j++){
						if("С��".equals(table.getColumn(j).getKey())){
							projectAmtSum = j;
							break;
						}
					}
				}

				if("�ۺϵ���".equals(table.getColumn(projectAmtSum + 1).getKey())){
					totalPriceIndex = projectAmtSum + 1;
					totalPriceSum = projectAmtSum + 1;
				}else{
					totalPriceIndex = projectAmtSum + 1;
					for(int j = totalPriceIndex; j < table.getColumnCount(); j++){
						if("С��".equals(table.getColumn(j).getKey())){
							totalPriceSum = j;
							break;
						}
					}
				}
				
				if("���".equals(table.getColumn(totalPriceSum + 1).getKey())){
					amountIndex = totalPriceSum + 1;
					amountSum = totalPriceSum + 1;
				}else{
					amountIndex = totalPriceSum + 1;
					for(int j = amountIndex; j < table.getColumnCount(); j++){
						if("С��".equals(table.getColumn(j).getKey())){
							amountSum = j;
							break;
						}
					}
				}
				
				page.setDescription((String) desTable.getCell(i - 2, "des").getValue());
				page.getEntrys().clear();
				for (int j = 1; j < table.getRowCount(); j++) {
					IRow row = table.getRow(j);
					if (row.getUserObject() instanceof NewListingEntryInfo) {
						NewListingEntryInfo entryInfo = new NewListingEntryInfo();
						NewListingEntryInfo tempInfo = (NewListingEntryInfo) row.getUserObject();
						entryInfo.getValues().clear();
						entryInfo.setHead(page);
						entryInfo.setId(tempInfo.getId());
						entryInfo.setLevel(Integer.parseInt(row.getCell("level").getValue().toString()));
						if (row.getCell("��Ŀ����").getValue() instanceof ListingItemInfo) {
							ListingItemInfo item = (ListingItemInfo) row.getCell("��Ŀ����").getValue();
							entryInfo.setItem(item);
							entryInfo.setItemName(item.getName());
							entryInfo.setItemNumber(item.getNumber());
						} else {
							if (row.getCell("��Ŀ����").getValue() != null) {
								entryInfo.setItemName(row.getCell("��Ŀ����").getValue().toString());
								entryInfo.setItem(null);
							}
						}
						
						entryInfo.setSeq(j);
						entryInfo.setIsLeaf(tempInfo.isIsLeaf());
						entryInfo.setIsCanZeroProAmount(tempInfo.isIsCanZeroProAmount());
						if (row.getCell("isKey").getValue() != null)
							entryInfo.setIsKey(((Boolean) row.getCell("isKey").getValue()).booleanValue());
						else
							entryInfo.setIsKey(false);
						if (row.getCell("isCompose").getValue() != null)
							entryInfo.setIsCompose(((Boolean) row.getCell("isCompose").getValue()).booleanValue());
						else
							entryInfo.setIsCompose(false);
						NewListingValueCollection valueColl = getRowValues(row, table, entryInfo, page);
						entryInfo.getValues().addCollection(valueColl);
//						entryInfo.setIsLeaf(this.isLeafRow(table, j));
						page.getEntrys().add(entryInfo);
					}
				}
				NewListingPageInfo oldPage = NewListingPageFactory.getRemoteInstance().getNewListingPageInfo(
						"where id = '" + page.getId() + "'");
				
				for(int s = 0 ; s < page.getEntrys().size() ; s ++ )
				{					
					boolean isEqual = false;
					NewListingEntryInfo newEntryInfo = page.getEntrys().get(s);
					if(newEntryInfo.getHead() != null && newEntryInfo.getHead().getHeadType() != null){
						// ����Ǽ׷�ҳǩ�����б귽��������page.getEntrys()�ǲ���Ҫ�ı�ģ����б��嵥�����ﶨ������о��Ƕ����У�
						if(!newEntryInfo.getHead().getHeadType().isIsDefined()){
							continue;
						}
					}
					
					pageId.add(page.getString("id"));

					NewListingValueInfo newListingValueUnit = newEntryInfo.getValues().get(1);
					NewListingValueInfo newListingValueProjectAmt = newEntryInfo.getValues().get(projectAmtSum - 3);
					String columnnIdUnit = newListingValueUnit.getColumn().getId().toString();
					String unit = StringUtils.cnulls(newListingValueUnit.get("text"));
//					String unitId = newListingValueUnit.getEntry().getId().toString();
					String columnnIdProjectAmt = newListingValueProjectAmt.getColumn().getId().toString();
					BigDecimal projectAmt = FDCNumberHelper.toBigDecimal(newListingValueProjectAmt.get("amount"));
//					String projectAmtId = newListingValueProjectAmt.getEntry().getId().toString();
					
					for(int k = 0 ; k < oldPage.getEntrys().size() ; k ++ )
					{
						NewListingEntryInfo oldEntryInfo = oldPage.getEntrys().get(k);
						
						if(newEntryInfo.getItemName() != null && oldEntryInfo.getItemName() != null 
								&& newEntryInfo.getItemName().equals(oldEntryInfo.getItemName()))
						{							
							EntityViewInfo view = new EntityViewInfo();
							FilterInfo filter = new FilterInfo();
							filter.getFilterItems().add(new FilterItemInfo("entry.id", oldEntryInfo.getString("id") ,CompareType.EQUALS));
							filter.getFilterItems().add(new FilterItemInfo("column.id", columnnIdUnit ,CompareType.EQUALS));
							view.setFilter(filter);
							NewListingValueCollection oldNewListingValue = NewListingValueFactory.getRemoteInstance().getNewListingValueCollection(view);

							if(oldNewListingValue != null && oldNewListingValue.size() > 0){
								for(int unitIndex = 0 ; unitIndex < oldNewListingValue.size(); unitIndex++){
									NewListingValueInfo oldNewListingValueInfo = oldNewListingValue.get(unitIndex);
									if(unit.equals(oldNewListingValueInfo.getText())){
										view = new EntityViewInfo();
										filter = new FilterInfo();
										filter.getFilterItems().add(new FilterItemInfo("entry.id", oldEntryInfo.getString("id") ,CompareType.EQUALS));
										filter.getFilterItems().add(new FilterItemInfo("column.id", columnnIdProjectAmt ,CompareType.EQUALS));
										view.setFilter(filter);
										NewListingValueCollection oldNewListingValueProjectAmt = NewListingValueFactory.getRemoteInstance().getNewListingValueCollection(view);
										if(oldNewListingValueProjectAmt != null && oldNewListingValueProjectAmt.size() > 0){
											for(int projectIndex = 0 ; projectIndex < oldNewListingValue.size(); projectIndex++){
												NewListingValueInfo oldProjectAmt = oldNewListingValueProjectAmt.get(projectIndex);
												if(projectAmt.floatValue() == FDCNumberHelper.toBigDecimal(oldProjectAmt.getAmount()).floatValue()){
													isEqual = true;
												}
											}
										}
									}
								}
							}
						}
					}
					if(!isEqual){
//						if(isChange){
						for(int index = amountSum ; index >= totalPriceIndex ; index--){
							newEntryInfo.getValues().get(index - 3).setAmount(null);//Constants.zero
						}
						oldPage.getEntrys().add(newEntryInfo);
					}
//					else{
//						oldPage.getEntrys().set(s, newEntryInfo);
//					}
				}
				
				// ����Ǽ׷�ҳǩ�����б귽��������page.getEntrys()�ǲ���Ҫ�ı�ģ���˲���Ҫ����
				if (page.getHeadType().isIsDefined()) { 
					NewListingPageFactory.getRemoteInstance().submit(oldPage);
				}
				
				if(NewListingEntryFactory.getRemoteInstance().exists("where head.id = '" + oldPage.getString("id") + "'"))
				{
					NewListingEntryCollection newListingEntryCollection = 
						NewListingEntryFactory.getRemoteInstance().getNewListingEntryCollection("where head.id = '" + oldPage.getString("id") + "'");

					for(int s = 0 ; s < newListingEntryCollection.size() ; s ++ )
					{
						if(isChange)
						{
							NewListingValueFactory.getRemoteInstance().delete(
									"where entry.id = '" + newListingEntryCollection.get(s).getString("id") + "' " +
									" and quotingPrice.id = '" + editData.getString("id") + "' and type = 1");
						}
						else
						{
							NewListingValueFactory.getRemoteInstance().delete(
								"where entry.id = '" + newListingEntryCollection.get(s).getString("id") + "' " +
								" and quotingPrice.id = '" + editData.getString("id") + "' and type = 0");
						}
					}
					
					for(int j = 0; j < table.getRowCount(); j++){
						IRow row = table.getRow(j);
						if (row.getUserObject() instanceof NewListingEntryInfo) {
							NewListingEntryInfo entryInfo = (NewListingEntryInfo) row.getUserObject();
							if (row.getCell("��Ŀ����").getValue() != null) {
								for (int a = 0; a < newListingEntryCollection.size(); a++) {
									if (newListingEntryCollection.get(a).getItemName().equals(row.getCell("��Ŀ����").getValue())) {
										if ((newListingEntryCollection.get(a).getValues().get(1).get("text") == null && row.getCell("��λ").getValue() == null)
												|| (newListingEntryCollection.get(a).getValues().get(1).get("text") != null && newListingEntryCollection.get(a).getValues().get(1).get("text")
														.toString().equals(row.getCell("��λ").getValue()))) {
											Object amount = newListingEntryCollection.get(a).getValues().get(projectAmtSum - 3).get("amount");
											float famount = FDCNumberHelper.toBigDecimal(newListingEntryCollection.get(a).getValues().get(projectAmtSum - 3).get("amount")).floatValue();
											float famount2 = FDCNumberHelper.toBigDecimal(row.getCell(projectAmtSum).getValue()).floatValue();
											if (amount != null && famount == famount2) {
												entryInfo = newListingEntryCollection.get(a);
											}
										}
									}
								}
							}
							int colCount = table.getColumnCount();
							for (int k = 0; k < colCount; k++) {
								IColumn column = table.getColumn(k);
								if (column.getUserObject() instanceof NewListingColumnInfo) {
									NewListingColumnInfo columnInfo = (NewListingColumnInfo) column.getUserObject();
									DescriptionEnum property = columnInfo.getHeadColumn().getProperty();
									if (property.equals(DescriptionEnum.TotalPrice) || property.equals(DescriptionEnum.TotalPriceSum) || property.equals(DescriptionEnum.Personal)
											|| property.equals(DescriptionEnum.AmountSum)) {
										Object temp = row.getCell(k).getValue();
										if (temp == null) {
											continue;
										}
										
										NewListingValueInfo valueInfo = new NewListingValueInfo();
										valueInfo.setColumn(columnInfo);
										valueInfo.setEntry(entryInfo);
										valueInfo.setQuotingPrice(editData);
										ColumnTypeEnum colType = columnInfo.getHeadColumn().getColumnType();
										InviteHelper.storeListingValue(valueInfo, temp, colType);
										if (colType.equals(ColumnTypeEnum.Amount)) {
											valueInfo.setAmount(FDCHelper.toBigDecimal(temp));
										} else if (colType.equals(ColumnTypeEnum.Date)) {
											valueInfo.setDateValue((Date) temp);
										} else {
											valueInfo.setText(temp.toString());
										}
										
										if (isChange) {
											valueInfo.setType(QuotingPriceTypeEnum.modify);
											baseCollection.add(valueInfo);
										} else {
											valueInfo.setType(QuotingPriceTypeEnum.original);
											baseCollection.add(valueInfo);
											NewListingValueInfo newInfo = (NewListingValueInfo) valueInfo.clone();
											newInfo.setType(QuotingPriceTypeEnum.modify);
											baseCollection.add(newInfo);
										}
									}
								}
							}
						}
					}
				}
			}
		}
		
		NewListingValueFactory.getRemoteInstance().submit(baseCollection); 		
		//  end ---- xuisan 
	}
	
	//begin ----xuisan

	/**
	 * ���ÿһ�е��嵥���ݣ�������ʱʹ�á�
	 */
	private NewListingValueCollection getRowValues(IRow row, KDTable table, NewListingEntryInfo entryInfo, NewListingPageInfo page) {
		NewListingValueCollection valueColl = new NewListingValueCollection();
		NewListingValueInfo valueInfo = null;
		NewListingColumnInfo columnInfo = null;
		for (int i = 3; i < table.getColumnCount(); i++) {
			valueInfo = new NewListingValueInfo();
			IColumn column = table.getColumn(i);
			if (column.getUserObject() instanceof NewListingColumnInfo) {
				columnInfo = (NewListingColumnInfo) column.getUserObject();
			} else {
				columnInfo = (NewListingColumnInfo) listingColumnObjectMap.get(table.getName() + column.getKey());
			}
			valueInfo.setColumn(columnInfo);
			valueInfo.setEntry(entryInfo);
			ICell cell = row.getCell(i);
			if (cell.getValue() != null) {
				if (column.getKey() == "��Ŀ����") {
					if (cell.getValue() instanceof ListingItemInfo) {
						ListingItemInfo item = (ListingItemInfo) cell.getValue();
						valueInfo.setText(item.getName());
					} else {
						valueInfo.setText(cell.getValue().toString());
					}
				} else {
					ColumnTypeEnum colType = columnInfo.getHeadColumn().getColumnType();
					InviteHelper.storeListingValue(valueInfo, cell.getValue(), colType);
				}
			}
			if (columnInfo.isIsQuoting()) {
				NewListingValueInfo oldValue = null;
				if (page.getHeadType().getId() != null && row.getCell("��Ŀ����").getValue() != null && columnInfo.getHeadColumn() != null)
					oldValue = (NewListingValueInfo) refPriceBakMap.get(page.getHeadType().getId().toString() + row.getCell("��Ŀ����").getValue().toString() + columnInfo.getHeadColumn().getName());
				if (oldValue != null) {
					valueInfo.setAmount(oldValue.getAmount());
					valueInfo.setText(oldValue.getText());
					valueInfo.setDateValue(oldValue.getDateValue());
				}
			}
			valueInfo.setType(priceTypeEnum);
			valueInfo.setQuotingPrice(editData);//�뱨�۹�������
			if (page.getHeadType().isIsDefined()) {//�Թ�Ӧ��ҳǩ�ķ�¼�н��и���
				if (entryInfo.getBoolean("isCloned")) { // ��Ҫcloneԭʼ���ۣ������entryInfo�ǿ�¡���entry
					valueInfo.setType(QuotingPriceTypeEnum.modify);
				}
			}			
			// ����Ǽ׷�ҳǩ����¡�ǳ��򵥣�ֱ�Ӹ���һ��valueInfo����
			else if (QuotingPriceTypeEnum.original.equals(priceTypeEnum)) {
				NewListingValueInfo valueInfoClone = (NewListingValueInfo) valueInfo.clone();
				valueInfoClone.setType(QuotingPriceTypeEnum.modify);
				valueColl.add(valueInfoClone);
			}
			valueColl.add(valueInfo);
		}
		return valueColl;
	}
	//end ----- xuisan 
	
	
	// �����뵼��

	private String importID;

	private static final char separator = '`';

	// ��Ҫ������ٵ������У����ڻ�ԭ
	private Map priceCols = new HashMap(5);

	/**
	 * �򿪺ͱ����ļ��Ի���
	 *
	 * @param title
	 * @param mode
	 * @return
	 */
	public static File fileDialog(String title,String listingName, int mode,CoreUI coreui) {
		JFileChooser fileChooser = new KDFileChooser() {
			public void approveSelection() {
				File file = this.getSelectedFile();
				if (this.getDialogType() == this.SAVE_DIALOG) {
					if (file != null && file.exists()) {
						if (MsgBox.showConfirm2(this, "�ļ��Ѵ��ڣ�ȷ��Ҫ������") == MsgBox.OK) {
							super.approveSelection();
						}
					} else {
						super.approveSelection();
					}
				} else if (this.getDialogType() == this.OPEN_DIALOG) {
					if (file == null || !file.exists()) {
						MsgBox.showError(this, "�ļ������ڣ�������ѡ���ļ�");
					} else {
						super.approveSelection();
					}
	
				}
			}
		};
		fileChooser.setSelectedFile(new File("c:\\"+listingName+".fdc"));
		fileChooser.setDialogTitle(title);
		fileChooser.setDialogType(mode);
		fileChooser.setFileFilter(new javax.swing.filechooser.FileFilter() {
			public boolean accept(File f) {
				return f.isDirectory() || f.getName().endsWith(".fdc");
			}

			public String getDescription() {
				return "���ز������ļ�(*.fdc)";
			}
		});
		if (fileChooser.showDialog(coreui, null) == JFileChooser.APPROVE_OPTION) {
			return fileChooser.getSelectedFile();
		}
		return null;
	}

	/*
	 * ����
	 */
	public void actionImportQuotingExcel_actionPerformed(ActionEvent e)
			throws Exception {
		
		//Ȩ�޼��
		IObjectPK orgPK = getOrgPK(this.actionImportQuotingExcel);
		PermissionFactory.getRemoteInstance().checkFunctionPermission(
				new ObjectUuidPK(SysContext.getSysContext()
						.getCurrentUserInfo().getId().toString()), orgPK,
		"inv_quotingPrice_ImportQuotingExcel");
		String importName = (String) getUIContext().get("inviteListingName");
		File file = fileDialog("���뱨��",importName, JFileChooser.OPEN_DIALOG,this);
		importQuoting(file);
	}

	/**
	 * set it public in order to be called in perf test codes
	 *
	 * @param file
	 * @throws ZipException
	 * @throws IOException
	 */
	public void importQuoting(File file) throws ZipException, IOException {
		if (file == null)
			return;
		ZipFile zipFile = null;
		try{
			zipFile = new ZipFile(file);
		}
		catch(Exception e){
			MsgBox.showError("�ļ���ʽ����!");
			return;
		}
		if (!verifyImport(zipFile)) {
			MsgBox.showError("�����ļ��뵱ǰ���۱�һ��!");
			return;
		}
		ZipEntry zipEntry = null;
		Enumeration enums = zipFile.entries();
		while (enums.hasMoreElements()) {
			zipEntry = (ZipEntry) enums.nextElement();
			if (zipEntry.getName().endsWith(".kdf")) {
				String comment = zipEntry.getComment();
				Object[] strCols = null;
				if (comment != null && comment.length() > 1) {		
					
					//�����ַ����У������е�֧�֣�����ԭ�е�ת��������Ҫ����
					String[] tempCols = comment.split(""+separator);
					ArrayList tempList = new ArrayList();
					for (int i = 0; i < tempCols.length - 1; i++) {
						String[] cols = tempCols[i].split(";");
						for (int j = 0; j < cols.length; j++) {
							if (!cols[j].equals(""))
								tempList.add(cols[j]);
						}
					}
					strCols = tempList.toArray();
					if (strCols == null || strCols.length < 1)
						continue;
					KDTable table = new KDTable();
					table.getIOManager().load(zipFile.getInputStream(zipEntry));
					int[] cols = new int[strCols.length];
					for (int i = 0; i < cols.length; i++) {
						cols[i] = Integer.parseInt((String)strCols[i]);
					}
					// �������
					if (table.getRowCount() > 0)
						fill(table, cols);
				}
			}
		}
		isModify = true;
		this.editData.setStatus(QuotingPriceStatusEnum.ImportedPrice);
		
		showBarInfo(this,"�ɹ����뱨��");
	}

	private boolean verifyImport(ZipFile zipFile) {
		importID = (String) getUIContext().get("inviteListingId");// (String)this.getUIContext().get(UIContext.ID);
		Enumeration enums = zipFile.entries();
		while (enums.hasMoreElements()) {
			ZipEntry zipEntry = (ZipEntry) enums.nextElement();
			if (zipEntry.getName().endsWith(".fdc")) {
				String comment = zipEntry.getComment();
				if (comment.indexOf('`') < 0)
					return false;
				return importID.equals(comment.substring(0, comment
						.indexOf(separator)));
			}
		}
		return false;
	}

	/**
	 * ������Ӧ��
	 *
	 * @param table
	 * @param columnList
	 *            ��Ҫ������
	 */
	private void fill(KDTable table, int[] columnList) {
		for (int i = 0; i < this.tabbedPnlTable.getTabCount(); i++) {

			KDTable myTable = (KDTable) this.tabbedPnlTable.getComponentAt(i);
			if (myTable.getUserObject() == null) {
				continue;
			}
			// ������֤
			if (!table.getName().equals(myTable.getName())) {
				continue;
			}

			logger.debug("table :" + table.getName());
			//begin ---- xuisan 
			myTable.removeRows(); 
			//end   ---- xuisan 
			for (int j = 0, count = table.getRowCount(); j < count; j++) {
				for (int colIndex = 0; colIndex < myTable.getColumnCount(); colIndex++) {
					IColumn col = myTable.getColumn(colIndex);
					NewListingColumnInfo colInfo = (NewListingColumnInfo) col
							.getUserObject();
					if (colInfo == null || !colInfo.isIsQuoting()) {
						continue;
					}
					Object cellVal = table.getCell(j, colIndex).getValue();
					// myTable.getCell(j, colIndex).setValue(cellVal);
					// ����ϸ���Լ�Ϊ0�ı������ݲ���ʾ
					NewListingEntryInfo entry  = new NewListingEntryInfo();
					if(myTable.getRow(j) == null){
						myTable.addRow(j, table.getRow(j));
						myTable.getRow(j).getStyleAttributes().setLocked(true);
					}else{
						entry = (NewListingEntryInfo) myTable.getRow(j).getUserObject();
					}
					if (entry != null && cellVal != null) {
						if (!entry.isIsLeaf()) {
//							myTable.getCell(j, colIndex).setValue(null);
						} else {
							//����  �ַ���  ��� �Ĵ��� 2008-05-28 ����
							if (cellVal instanceof String) {
								//�û����������һ�����֣����磺1234
								//���û�����Ĵ���Ϊ�����У���Ҫ�õ�getFormattedValue()
								//2008-05-30 ����
								if(table.getCell(j, colIndex).getStyleAttributes().getNumberFormat().equals("yyyy-m-d")){
									
										try {
											myTable.getCell(j, colIndex).setValue(DateTimeUtils.parseDate(table.getCell(j, colIndex).getFormattedValue().toString()));
										} catch (ParseException e) {
											// TODO �Զ����� catch ��
											e.printStackTrace();
											myTable.getCell(j, colIndex).setValue(null);
										}
									
								}
								else{
									myTable.getCell(j, colIndex).setValue(cellVal);
								}								
							} else if (cellVal instanceof Date) {
								myTable.getCell(j, colIndex).setValue(cellVal);
							}
							else{
								BigDecimal val = FDCHelper.toBigDecimal(cellVal);
								logger.debug("quotingVal:" + cellVal);
								if (val.signum() == 0)
									myTable.getCell(j, colIndex).setValue(null);
								else
									myTable.getCell(j, colIndex).setValue(val);
							}
							
						}
					}
					else if(entry != null && cellVal == null){
						myTable.getCell(j, colIndex).setValue(null);
					}else if(entry == null && cellVal != null){
//						myTable.setRow(j).setUserObject(table.getRow(j));
					}
				}
			}
			setTreeLevel(myTable);
			// ��ɵ���,������������
			setUnionData(myTable,tabbedPnlTable);
			
			// ��Ҷ�ӽڵ�ϼ����¼���
			//���ڵ���
			ArrayList isLeafListNo = new ArrayList();
			for (int k = 1; k < table.getRowCount(); k++) {
				BigDecimal sum = FMConstants.ZERO;
				IRow row = table.getRow(k);
				BigDecimal rowLevel = FDCHelper.toBigDecimal(row.getCell(0).getValue());
				NewListingEntryInfo info = new NewListingEntryInfo();
//				if (info != null && !info.isIsLeaf()) {
				if(!checkLeafRow(table, k)){
					row.getCell(4).setValue(null);
					row.getCell(5).setValue(null);
//					row.getCell(6).setValue(null);
					info.setIsLeaf(false);
					
					isLeafListNo.add(String.valueOf(k));
					
					for (int m = k + 1; m < table.getRowCount(); m++) {
						IRow rowNext = table.getRow(m);
						NewListingEntryInfo nextInfo = (NewListingEntryInfo) rowNext.getUserObject();
//						if (nextInfo != null && nextInfo.isIsLeaf()) {
						if(checkLeafRow(table, m)){
							BigDecimal rowLevelNext = FDCHelper.toBigDecimal(rowNext.getCell(0).getValue());
							BigDecimal value = FDCHelper.toBigDecimal(rowNext.getCell(7).getValue());
							if(rowLevelNext.floatValue() > rowLevel.floatValue()){
								sum = sum.add(FDCHelper.toBigDecimal(value));
							}
						}
					}
//					row.getCell(7).setValue(sum);
				}else{
					info.setIsLeaf(true);
				}
				row.setUserObject(info);
			}
		}
	}

	/*
	 * ����
	 */
	public void actionExportQuotingExcel_actionPerformed(ActionEvent e)
			throws Exception {
		if (this.tabbedPnlTable.getTabCount() <= 2) {
			MsgBox.showWarning("û�пɵ���������");
			return;
		}
		importID = (String) getUIContext().get("inviteListingId");
		String importName = (String) getUIContext().get("inviteListingName");
		File zipFile = null;
		if ((zipFile = fileDialog("����",importName, JFileChooser.SAVE_DIALOG,this)) == null)
			return;
		if (!zipFile.getName().endsWith(".fdc"))
			zipFile = new File(zipFile.getAbsolutePath() + ".fdc");

		
		exportQuoting(zipFile,tabbedPnlTable,userObjectList,priceCols,importID);
		showBarInfo(this,"�ɹ���������");
	}

	/**
	 * set it public in order to be called in perf test codes
	 *
	 * @param zipFile
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public static void exportQuoting(File zipFile,final KDTabbedPane tabbedPnlTable,List userObjectList,Map priceCols,
			String importID) throws FileNotFoundException,
			IOException {
		if (zipFile == null || tabbedPnlTable.getTabCount() <= 2) {
			logger.error("expFile: " + zipFile + ", tabCount:"
					+ tabbedPnlTable.getTabCount());
			return;
		}

		// ����KDTableΪkdf�ļ�
		File files[] = new File[tabbedPnlTable.getTabCount()];
		List comments = new ArrayList(10);
		
		
		comments.add(0, "");
		comments.add(1, "");
		// ������ܽ����
		String totalColumn = "";
		// ������Ҫ��ӡ����
		String withoutPrintCols = "";
		
		
		for (int i = 2; i < tabbedPnlTable.getTabCount(); i++) {
			KDTable table = (KDTable) tabbedPnlTable.getComponentAt(i);
			// if(table.getUserObject()==null){
			// continue;
			// }
			// ������
			setTableLock(table, false);
			// ������Ҷ�ӽڵ���
			lockNotLeafRow(table);
			// '`'֮ǰ�ַ�������Ҫ��д���۵��У�֮����Ҫ����Ŀ���ܵ���
			// ��Ҫ��д���۵���
			// ������ͱ�����
			String str = "";
			// �ַ����ͱ�����
			String str2 = "";
			// �������ͱ�����
			String str3 = "";
			for (int k = 3; k < table.getColumnCount(); k++) {
				IColumn column = table.getColumn(k);
				if (!(column.getUserObject() instanceof NewListingColumnInfo))
					continue;
				NewListingColumnInfo columnInfo = (NewListingColumnInfo) column
						.getUserObject();
				if (columnInfo.isIsQuoting()
						&& columnInfo.getHeadColumn().getColumnType().equals(
								ColumnTypeEnum.Amount)) {
					str += k + ";";
				} else if (columnInfo.isIsQuoting()
						&& columnInfo.getHeadColumn().getColumnType().equals(
								ColumnTypeEnum.String)) {
					str2 += k + ";";
				} else if (columnInfo.isIsQuoting()
						&& columnInfo.getHeadColumn().getColumnType().equals(
								ColumnTypeEnum.Date)) {
					str3 += k + ";";
				}
			}
			//    ����� ` �ַ����� ` ������ `
			str = str + separator + str2 + separator + str3 + separator;
			// ����Ŀ���ܵ���
			// ������ܽ����
			// ����Ҫ��ӡ����
			List noPrintColumn = new ArrayList(5);
			boolean found = false;

			// �ۺϵ���
			int totalPrice = 0;

			for (int i0 = 3; i0 < table.getColumnCount(); i0++) {
				IColumn column = table.getColumn(i0);
				if (column.getUserObject() instanceof NewListingColumnInfo) {
					NewListingColumnInfo col = (NewListingColumnInfo) column
							.getUserObject();
					if (col.getHeadColumn() == null)
						continue;
					DescriptionEnum property = col.getHeadColumn()
							.getProperty();
					if (property.equals(DescriptionEnum.AmountSum)) {
						found = true;
						str += i0;
						totalColumn += i0 + ";";
					} else if (property.equals(DescriptionEnum.ProjectAmt)) {
						noPrintColumn.add(i0 + ";");
					} else if (property.equals(DescriptionEnum.TotalPrice)) {
						totalPrice = i0;
					} else if (property.equals(DescriptionEnum.TotalPriceSum)) {
						totalPrice = i0;
					}
				}
			}

			table.setID(table.getID() + separator + totalPrice);

			if (!found) {
				totalColumn += "0;";
			}
			// ����� ` �ַ����� ` ������ ` �ۺϵ�����
			comments.add(i, str);
			if (noPrintColumn.size() > 0) {
				for (int j = 0; j < noPrintColumn.size(); j++) {
					withoutPrintCols = withoutPrintCols
							+ (String) noPrintColumn.get(j);
				}
				withoutPrintCols += "!";
			} else {
				withoutPrintCols += "0!";
			}
			files[i] = new File("table_" + i + ".kdf");
			filter(table,userObjectList,priceCols).getIOManager().save("table_" + i + ".kdf");
			// ��ԭ
			unfilter(table,userObjectList,priceCols);
			// ����
			setTableLock(table, true);

		}

		// ���ȼ����б�˵��Table
		final String kd0 = "kd0.fdc";
		files[0] = new File(kd0);
		KDTable table = (KDTable) tabbedPnlTable.getComponentAt(0);
		table.getIOManager().save(kd0);

		// ע��
		comments.set(0, importID + separator + totalColumn + separator
				+ withoutPrintCols);
		
		// ��Ӧ���Զ���ҳǩ
		String userDefined = "-1"+separator; // userDefinedĬ����-1��䣬�������߿ͻ��˵���MainFrame.importFile()
		for (int i = 2; i < tabbedPnlTable.getTabCount(); i++) {
			table = (KDTable) tabbedPnlTable.getComponentAt(i);
			NewListingPageInfo pageInfo=(NewListingPageInfo)table.getUserObject();
			if(pageInfo != null && pageInfo.getHeadType() != null && pageInfo.getHeadType().isIsDefined()){
				userDefined = userDefined + i + separator;
			}
		}
		comments.set(1, userDefined);

		// ���ۻ��ܱ�
		final String kd1 = "kd1.fdc";
		files[1] = new File(kd1);
		table = (KDTable) tabbedPnlTable.getComponentAt(1);

		final int rowCount = table.getRowCount();
		Object[] objs = new Object[rowCount];
		for (int i = 0; i < rowCount; i++) {
			if (i != rowCount - 3) {
				ICell cell = table.getCell(i, 2);
				objs[i] = cell.getValue();
				cell.setValue(FDCHelper.ZERO);
			}
		}
		table.getIOManager().save(kd1);
		for (int i = 0; i < rowCount; i++) {
			if (i != rowCount - 3) {
				ICell cell = table.getCell(i, 2);
				cell.setValue(objs[i]);
			}
		}

		// ѹ���ļ�
		ZipOutputStream out = new ZipOutputStream(new FileOutputStream(zipFile));
		for (int i = 0; i < files.length; i++) {
			ZipEntry zipEntry = new ZipEntry(files[i].getAbsolutePath());
			out.putNextEntry(zipEntry);
			zipEntry.setComment((String) comments.get(i));
			if(files[i].getAbsolutePath()==null)
				continue;
			FileInputStream in = new FileInputStream(files[i]);
			int b = 0;
			while ((b = in.read()) != -1) {
				out.write(b);
			}
			in.close();
			files[i].deleteOnExit();
		}
		out.close();
	}

	private List userObjectList = new ArrayList(25);

	/**
	 * KDTable(����)������ٵ���
	 *
	 * @param table
	 * @return
	 */
	public static KDTable filter(KDTable table,List userObjectList,Map priceCols) {
		for (int j = 3; j < table.getColumnCount(); j++) {
			List list = null;
			// ��ո���UserObject(cell���UserObjectδ���),��С�����ļ��ĳ���
			IColumn column = table.getColumn(j);
			Object userObj = column.getUserObject();
			userObjectList.add(userObj);
			// ������
			boolean isProjectAmt = false;
			// �ۺϵ���
			boolean isTotalPriceAmt = false;
			// �ۺϵ���С��
			boolean isTotalPriceSum = false;
			// �����Ǳ�����
			boolean isQuoting = false;
			// ����ǽ��С��
			boolean isAmountSum = false;
			//
			if (userObj instanceof NewListingColumnInfo) {
				NewListingColumnInfo columnInfo = (NewListingColumnInfo) userObj;
				if (DescriptionEnum.ProjectAmt.equals(columnInfo
						.getHeadColumn().getProperty())
						|| DescriptionEnum.ProjectAmtSum.equals(columnInfo
								.getHeadColumn().getProperty())) {
					isProjectAmt = true;
				} else if (columnInfo.isIsQuoting()
						&& DescriptionEnum.TotalPrice.equals(columnInfo
								.getHeadColumn().getProperty())) {
					isTotalPriceAmt = true;
				} else if (columnInfo.isIsQuoting()
						&& DescriptionEnum.TotalPriceSum.equals(columnInfo
								.getHeadColumn().getProperty())) {
					isTotalPriceSum = true;
				} else if (columnInfo.isIsQuoting()) {
					isQuoting = true;
				} else if (DescriptionEnum.AmountSum.equals(columnInfo
						.getHeadColumn().getProperty())) {
					isAmountSum = true;
				}
				if (isProjectAmt || isTotalPriceAmt || isQuoting
						|| isTotalPriceSum || isAmountSum)
					list = new ArrayList(45);
			}
			// ��Ŀ���ƶ�����ַ���
			for (int i = 1; i < table.getRowCount(); i++) {
				ICell cell = table.getRow(i).getCell("��Ŀ����");
				if (cell != null) {
					Object value = cell.getValue();
					if (value instanceof ListingItemInfo) {
						cell.setValue(((ListingItemInfo) value).getName());
					}
				}

				// ˳���޸ı�����ɫ
				if (!isLeafRow(table, i)) {
					table.getRow(i).getStyleAttributes().setBackground(
							NON_LEAF_COLOR);
					// continue;
				}
				cell = table.getCell(i, j);
				// ����
				if (isProjectAmt) {
					if (cell.getValue() == null) {
						cell.setValue("0");
						cell.getStyleAttributes().setFontColor(
								cell.getStyleAttributes().getBackground());
					}
				} else if (isTotalPriceAmt) {
					list.add(cell.getValue());
					if (cell.getStyleAttributes().isLocked())
						cell.getStyleAttributes().setFontColor(
								cell.getStyleAttributes().getBackground());
					cell.setValue("0");
				} else if (isTotalPriceSum) {
					list.add(cell.getValue());
					if (cell.getExpressions() == null)
						cell.setValue("0");
					if (isLeafRow(table, cell.getRowIndex()))
						cell.getStyleAttributes().setBackground(LEAF_COLOR);
				} else if (isQuoting) {
					list.add(cell.getValue());
					cell.setValue(null);
				} else if (isAmountSum) {
					if (i == 1 && table.getCell(0, j).getValue() != null)
						table.getCell(0, j).setValue(FDCHelper.ZERO);
					list.add(cell.getValue());
					if (cell.getExpressions() == null)
						cell.setValue("0");
				}

			}
			column.setUserObject(null);
			if (isQuoting || isTotalPriceAmt || isTotalPriceSum || isAmountSum)
				priceCols.put(new Integer(j), list);
		}

		return table;
	}

	/**
	 * �ѹ��˺��KDTable��ԭ,ע�⣺��δ��ԭ"��Ŀ����"����
	 *
	 * @param table
	 * @return
	 */
	private static void unfilter(KDTable table,List userObjectList,Map priceCols) {
		for (int j = 3; j < table.getColumnCount(); j++) {
			table.getColumn(j).setUserObject(userObjectList.get(j - 3));
			if (priceCols.containsKey(new Integer(j))) {
				List list = (List) priceCols.get(new Integer(j));
				for (int i = 1; i < table.getRowCount(); i++) {
					ICell cell = table.getCell(i, j);
					if (cell.getExpressions() != null)
						continue;
					cell.setValue(list.get(i - 1));
				}
			}
		}
		userObjectList.clear();
		priceCols.clear();

		int col = 0;
		for (int i = 3; i < table.getColumnCount(); i++) {
			IColumn column = table.getColumn(i);
			Object userObj = column.getUserObject();
			if (userObj instanceof NewListingColumnInfo) {
				NewListingColumnInfo columnInfo = (NewListingColumnInfo) userObj;
				if (columnInfo.isIsQuoting()
						&& DescriptionEnum.TotalPriceSum.equals(columnInfo
								.getHeadColumn().getProperty())) {
					col = i;
					break;
				}
			}

		}
		// ��ɫ��ԭ
		if (col > 0) {
			for (int i = 1; i < table.getRowCount(); i++) {
				if (isLeafRow(table, i))
					table.getCell(i, col).getStyleAttributes().setBackground(
							Color.WHITE);

			}
		}

	}

	/**
	 * �������ӿ�Ŀ��
	 *
	 * @param table
	 */
	public static void lockNotLeafRow(KDTable table) {
		for (int i = 0; i < table.getRowCount(); i++) {
			if (!isLeafRow(table, i))
				table.getRow(i).getStyleAttributes().setLocked(true);
		}
	}

	protected void afterSubmitAddNew() {
	}

	/**
	 * ���������ñ�����ģ�ͣ����μ����
	 * @param table
	 * @Author��owen_wen
	 * @CreateTime��2011-10-29
	 */
	private void setTreeLevel(KDTable table) {
		int maxLevel = 0;
		for (int i = 1; i < table.getRowCount(); i++) { // ��һ��Ϊ�ϼ��У�����Ҫ����
			int level = ((Integer) table.getRow(i).getCell("level").getValue()).intValue();
			table.getRow(i).setTreeLevel(level);
			 if (level > maxLevel) {
				maxLevel = level;
			}
		}
		table.getTreeColumn().setDepth(maxLevel);
	}
}
