/**
 * output package name
 */
package com.kingdee.eas.fdc.invite.client;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.io.File;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.swing.JFileChooser;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.common.LanguageManager;
import com.kingdee.bos.ctrl.common.variant.Variant;
import com.kingdee.bos.ctrl.excel.io.kds.KDSBookToBook;
import com.kingdee.bos.ctrl.excel.model.struct.Book;
import com.kingdee.bos.ctrl.excel.model.struct.Cell;
import com.kingdee.bos.ctrl.excel.model.struct.Row;
import com.kingdee.bos.ctrl.excel.model.struct.Sheet;
import com.kingdee.bos.ctrl.kdf.kds.KDSBook;
import com.kingdee.bos.ctrl.kdf.kds.KDSSheet;
import com.kingdee.bos.ctrl.kdf.table.ICell;
import com.kingdee.bos.ctrl.kdf.table.IColumn;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectManager;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.KDTableHelper;
import com.kingdee.bos.ctrl.kdf.util.style.Styles.HorizontalAlignment;
import com.kingdee.bos.ctrl.swing.KDFileChooser;
import com.kingdee.bos.ctrl.swing.event.DataChangeEvent;
import com.kingdee.bos.ctrl.swing.util.SimpleFileFilter;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.base.codingrule.CodingRuleManagerFactory;
import com.kingdee.eas.base.codingrule.ICodingRuleManager;
import com.kingdee.eas.basedata.org.CompanyOrgUnitCollection;
import com.kingdee.eas.basedata.org.CompanyOrgUnitFactory;
import com.kingdee.eas.basedata.org.CompanyOrgUnitInfo;
import com.kingdee.eas.basedata.org.CostCenterOrgUnitInfo;
import com.kingdee.eas.basedata.org.ICompanyOrgUnit;
import com.kingdee.eas.basedata.org.NewOrgViewHelper;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCNumberConstants;
import com.kingdee.eas.fdc.basedata.client.FDCClientHelper;
import com.kingdee.eas.fdc.invite.ColumnTypeEnum;
import com.kingdee.eas.fdc.invite.DescriptionEnum;
import com.kingdee.eas.fdc.invite.HeadColumnCollection;
import com.kingdee.eas.fdc.invite.HeadColumnFactory;
import com.kingdee.eas.fdc.invite.HeadColumnInfo;
import com.kingdee.eas.fdc.invite.HeadTypeInfo;
import com.kingdee.eas.fdc.invite.ListingItemCollection;
import com.kingdee.eas.fdc.invite.ListingItemFactory;
import com.kingdee.eas.fdc.invite.ListingItemInfo;
import com.kingdee.eas.fdc.invite.RefPriceCollection;
import com.kingdee.eas.fdc.invite.RefPriceEntryCollection;
import com.kingdee.eas.fdc.invite.RefPriceEntryInfo;
import com.kingdee.eas.fdc.invite.RefPriceFactory;
import com.kingdee.eas.fdc.invite.RefPriceInfo;
import com.kingdee.eas.fi.newrpt.client.designer.io.WizzardIO;
import com.kingdee.eas.util.client.MsgBox;
import com.kingdee.util.StringUtils;

/**
 * output class name
 */
public class ImportExcelPriceUI extends AbstractImportExcelPriceUI {
	private static final Logger logger = CoreUIObject
			.getLogger(ImportExcelPriceUI.class);

	private static final Color ERR_COLOR = new Color(224, 100, 0);

	private HeadTypeInfo headType = null;

	private KDSBook book = null;

	private Map dbItems = new HashMap();
	
	private Map colObjMap = new HashMap();
	
	private boolean isCodingRuleAddView = false;

	/**
	 * output class constructor
	 */
	public ImportExcelPriceUI() throws Exception {
		super();
	}

	public void onLoad() throws Exception {
		this.f7HeadType.setDisplayFormat("$name$");
		this.f7HeadType.setEditFormat("$number$");
		this.f7HeadType.setCommitFormat("$number$");
		this.txtFileName.setEnabled(false);
		super.onLoad();
		if (this.getUIContext().get("headType") != null) {
			this.f7HeadType.setValue(this.getUIContext().get("headType"));
		} else {
			btnChoose.setEnabled(false);
		}
		this.kdtPrice.getStyleAttributes().setWrapText(true);
	}

	/**
	 * output storeFields method
	 */
	public void storeFields() {
		super.storeFields();
	}

	/**
	 * output btnChoose_actionPerformed method
	 */
	protected void btnChoose_actionPerformed(java.awt.event.ActionEvent e)
			throws Exception {
		if (f7HeadType.getValue() == null) {
			f7HeadType.requestFocus();
			MsgBox.showWarning(this, "����ѡ���ͷ����");
			return;
		}

		KDFileChooser chsFile = new KDFileChooser();
		String XLS = "xls";
		String Key_File = "Key_File";
		SimpleFileFilter Filter_Excel = new SimpleFileFilter(XLS, "MS Excel"
				+ LanguageManager.getLangMessage(Key_File, WizzardIO.class,
						"����ʧ��"));
		chsFile.addChoosableFileFilter(Filter_Excel);
		int ret = chsFile.showOpenDialog(this);
		if (ret != JFileChooser.APPROVE_OPTION) {
			return;
		}

		loadFile2UI(chsFile.getSelectedFile());
	}

	public void loadFile2UI(File file) throws Exception {
		if (file == null)
			return;

		String fileName = file.getAbsolutePath();
		book = InviteHelper.excelReaderParse(this, fileName);
		if (book != null) {
			txtFileName.setText(file.getPath());
			fillTable();
		}
	}

	public void fillTable() throws BOSException, EASBizException {
		this.kdtPrice.removeColumns();
		fillHead();
		fillRows();
		initUserConfig();
	}
	/***
	 * ���Ǵ˺�����֧���û���������ʽ��ÿ����ͷ������ͬ����ʽ
	 */
	public Object getTablePreferenceSchemaKey(){
		HeadTypeInfo headType = (HeadTypeInfo) this.f7HeadType.getValue();
		if(headType==null){
			return null;
		}
		else{
			// �Ա�ͷ���͵�longNumber��Ϊkey ����ÿ����ͷ���͵��Զ�����ʽ
			return headType.getLongNumber();
		}
		
    }
	/***
	 * 
	 * @return
	 * @throws BOSException
	 * @deprecated
	 */
	private CompanyOrgUnitCollection getSelComps() throws BOSException {
		if (getUIContext().get("selCompName") == null)
			return null;

		ICompanyOrgUnit iComp = CompanyOrgUnitFactory.getRemoteInstance();

		String selCompName = getUIContext().get("selCompName").toString();
		EntityViewInfo selOrgView = new EntityViewInfo();
		FilterInfo selOrgFilter = new FilterInfo();
		selOrgFilter.getFilterItems().add(
				new FilterItemInfo("name", selCompName));
		selOrgView.setFilter(selOrgFilter);
		return iComp.getCompanyOrgUnitCollection(selOrgView);
	}

	/***************************************************************************
	 * ������ݣ����Excel�ļ����ж��ҳǩ����ô�жϳ���һ��ҳǩ�������ҳǩ�Ƿ�������
	 * ��������ݣ���ô��ʾ�û��Ƿ��룬������ֻ�����һ��ҳǩ������ ���û�У�ֻ�����һ��ҳǩ������ �����ж�ҳǩ�����Ƿ���ѡ�����֯�Ƿ���ͬ
	 * 
	 * @throws BOSException
	 * @throws EASBizException
	 */
	public void fillRows() throws BOSException, EASBizException {
		if (book == null || headType == null) {
			return;
		}
		// ��ȡ��ǰ��֯
		CostCenterOrgUnitInfo currentCostOrg = SysContext.getSysContext()
				.getCurrentCostUnit();
		CompanyOrgUnitInfo currentOrg = CompanyOrgUnitFactory
				.getRemoteInstance().getCompanyOrgUnitInfo(
						new ObjectUuidPK(currentCostOrg.getId()));
		Book importBook = KDSBookToBook.traslate(book);
		int n = importBook.getSheetCount();
		if (n <= 0) {
			// һ��ҳǩ��û�У�Excel�ļ�������
			logger.error("excel file has no sheet.");
			MsgBox.showInfo("Excel�ļ���û��ҳǩ");
			return;
		} else {
			Sheet firstSheet = importBook.getSheet(0);
			if (firstSheet.hasData()) {
				boolean doImport = true;
				// �ж��ҳǩ����Ҫѭ���жϳ���һ��ҳǩ֮�������ҳǩ���Ƿ�������
				for (int i = 1; i < n; i++) {
					Sheet sheet = importBook.getSheet(i);
					if (sheet.hasData()) {
						if (MsgBox.showConfirm2New(this,
								"�������ݵ�ҳǩ����һ�������ε��뽫ֻ�����һ��ҳǩ�����ݣ�ȷ���Ƿ��룿") == MsgBox.NO) {
							doImport = false;
						}
						break;
					}
				}
				if (doImport) {
					// ���Ե���
					Map columns = getExcelInTableColumnList(firstSheet);
					if (!isExcelMatchTable(firstSheet, columns)) {
						logger
								.error("excel columns is not matches with tbl's.");
						MsgBox.showInfo("Excel�ļ��еı�ͷ��ѡ��ı�ͷ���Ͳ�ƥ��");
						return;
					}
					int beginRow = kdtPrice.getRowCount();
					fillRowWithSheetData(firstSheet, columns, currentOrg);
					checkImportData(currentOrg, columns, beginRow);
				}
			} else {
				logger.error("excel sheet 0 has no data.");
				MsgBox.showInfo("��һ��ҳǩ��û������!");
			}
		}
	}

	/**
	 * ���excel�����ݺ�kdtable�������Ƿ��Ӧ
	 *
	 * @param tblCols
	 * @return
	 */
	private boolean isExcelMatchTable(Sheet sheet, Map tblCols) {
		if (tblCols == null) {
			logger.error("cannot extract valid column from table");
			return false;
		}

		boolean fMatch = true;
		int colCount = tblCols.size();
		for (int i = 0, n = sheet.getMaxColIndex(); i <= n; i++) {
			Variant cellRawVal = sheet.getCell(1, i, true).getValue();
			if (Variant.isNull(cellRawVal)) {
				cellRawVal = sheet.getCell(0, i, true).getValue();
			}
			String colName = Variant.isNull(cellRawVal) ? "" : cellRawVal
					.toString();
			if (!(colName.equals("����״̬") || colName.equals("��֯") || colName.equals("�ڲ�����"))
					&& kdtPrice.getColumn(colName) == null) {
				fMatch = false;
				logger.error("non table column:" + cellRawVal);
				break;
			}
		}
		// 2-ǰ���"����״̬","��֯"���У������Ƚ�ʱ������������
		return fMatch && colCount == kdtPrice.getColumnCount() - 3;
	}
	public boolean isCodingRuleAddView(){
		return this.isCodingRuleAddView;
	}
	private void setIsCodingRuleAddView() throws BOSException, EASBizException{
		ICodingRuleManager iCodingRuleManager;
		
		iCodingRuleManager = CodingRuleManagerFactory
		.getRemoteInstance();
		String currentOrgId = SysContext.getSysContext().getCurrentCostUnit().getId().toString();
		ListingItemInfo itemInfoCall = new ListingItemInfo();
		if(iCodingRuleManager.isExist(itemInfoCall, currentOrgId)){
			this.isCodingRuleAddView = iCodingRuleManager.isAddView(itemInfoCall, currentOrgId);
		}
		else{
			currentOrgId = SysContext.getSysContext().getCurrentOrgUnit().getId().toString();
			if(iCodingRuleManager.isExist(itemInfoCall, currentOrgId))
				this.isCodingRuleAddView = iCodingRuleManager.isAddView(itemInfoCall, currentOrgId);
		}
		
	}

	private void checkImportData(CompanyOrgUnitInfo org, Map columns,
			int beginRow) throws BOSException, EASBizException {
		Map itemNameMap = getSameOrgSameNameItems(org);
		Map upOrgItemNameMap = getUpOrgSameNameItems(org);
		Map downOrgItemNameMap = getDownOrgSameNameItems(org);
//		Set columnSet = columns.keySet();
		Set columnSet = columns.entrySet();
		kdtPrice.getStyleAttributes().setWrapText(true);
		Map rowDupMap = new HashMap();
		String currentOrgId = SysContext.getSysContext().getCurrentCostUnit().getId().toString();
		ICodingRuleManager iCodingRuleManager = CodingRuleManagerFactory
		.getRemoteInstance();
		ListingItemInfo itemInfoCall = new ListingItemInfo();		
		String [] numbers = null;
		
		if(isCodingRuleAddView){
			numbers = iCodingRuleManager.getBatchNumber(itemInfoCall,currentOrgId,kdtPrice.getRowCount());
		}
		int iNumber = 0;
		for (int j = beginRow; j < kdtPrice.getRowCount(); j++) {
			IRow row = kdtPrice.getRow(j);
			List errList = new ArrayList();
			boolean isHave = false;
			// ���������ͬ�Ŀ�����,����������
			// ����һ������ �����ж�
			// 2008-05-29 ����
			boolean isExcelHave = false;// ��Excel���Ƿ������ͬ����Ŀ����
			String itemName = (String) row.getCell("��Ŀ����").getValue();
			//String itemNumber = (String) row.getCell("��Ŀ����").getValue();
			String unit = (String) row.getCell("��λ").getValue();
			if (StringUtils.isEmpty(unit)) {
				errList.add("��λΪ��!");
			}
			String key = itemName;
			if (StringUtils.isEmpty(itemName)) {
				errList.add("��Ŀ����Ϊ��!");
			} else {
				if (itemNameMap.containsKey(key)) {
					isHave = true;
					ListingItemInfo itemInfo = (ListingItemInfo) itemNameMap
							.get(itemName);
//					if (!dbInfo.getNumber().equals(itemNumber)) {
//						errList.add("�����ݿ��Ӧ��Ŀ���벻ƥ��!");
//					}
					String dbUnit = itemInfo.getUnit();
					if (dbUnit == null) {
						if (!StringUtils.isEmpty(unit)) {
							errList.add("�����ݿ��Ӧ��Ŀ��λ��ƥ��!");
						}
					} else if (!dbUnit.equals(unit)) {
						errList.add("�����ݿ��Ӧ��Ŀ��λ��ƥ��!");
					}

				} else {
					if (key.getBytes().length > 500){
						errList.add("��Ŀ���Ƴ��ȹ���,���500�ֽ�,ʵ��"+key.getBytes().length);
					}
					if (upOrgItemNameMap.containsKey(key)) {
						isHave = true;
						errList.add("�ϼ���֯��Ӧ��ͷ��������Ŀ�д���ͬ����Ŀ");
					}
					else if (downOrgItemNameMap.containsKey(key)) {
						isHave = true;
						errList.add("�¼���֯��Ӧ��ͷ��������Ŀ�д���ͬ����Ŀ");
					}
					else if (rowDupMap.containsKey(key)) {
						isExcelHave = true;// ��Excel�д�����ͬ����Ŀ����
						IRow dup = (IRow) rowDupMap.get(key);
//						String dupItemNumber = (String) dup.getCell("��Ŀ����")
//								.getValue();
						String dupUnit = (String) dup.getCell("��λ")
								.getValue();
//						if (!StringUtils.isEmpty(dupItemNumber)
//								&& !StringUtils.isEmpty(itemNumber)) {
//							if (!dupItemNumber.equals(itemNumber)) {
//								errList.add("�͵�" + (dup.getRowIndex() + 1)
//										+ "����ͬ��Ŀ���Ƶ����벻ƥ��!");
//							}
//						}
						if (dupUnit!=null&&!dupUnit.equals(unit)) {
							errList.add("�͵�" + (dup.getRowIndex() + 1)
									+ "����ͬ��Ŀ���Ƶ���λ��ƥ��!");
						}
						if (dupUnit!=null&&dupUnit.getBytes().length > 80)
							errList.add("\n����Ŀ��\"��λ\"�г��ȹ���,���80�ֽ�,ʵ��"
									+ dupUnit.getBytes().length + "�ֽ�");
					} else {
						rowDupMap.put(key, row);
					}
//					if (!StringUtils.isEmpty(itemNumber)) {
//						if (itemNumberMap.containsKey(itemNumber)) {
//							errList.add("��Ŀ�����Ѿ�����!");
//						}
//					}
				}
			}
			BigDecimal sum = FDCNumberConstants.ZERO;
			String sumColKey = null;
			boolean isNoPrice = true;
			boolean isHaveSubAmount = false;
			for (Iterator iter = columnSet.iterator(); iter.hasNext();) {
//				int index = ((Integer) iter.next()).intValue();
//				String column = (String) columns.get(new Integer(index));
				Map.Entry entry = (Map.Entry) iter.next();
				String column = (String) entry.getValue();
				
//				HeadColumnInfo columnInfo = (HeadColumnInfo) kdtPrice
//						.getColumn(column).getUserObject();
				HeadColumnInfo columnInfo = (HeadColumnInfo) colObjMap.get(column);
				if (columnInfo != null
						&& columnInfo.getProperty().equals(
								DescriptionEnum.TotalPrice)) {
					isHaveSubAmount = true;
					Object val = row.getCell(column).getValue();
					BigDecimal value = null;
					if(val instanceof BigDecimal){
						value = (BigDecimal) val;
					}
					else {
						try{
							if(val==null)
								value=null;
							else
								value = new BigDecimal((String)val);
						}
						catch(Exception e){
							value = FDCHelper.ZERO;
						}
						
					}
					
					if (value != null) {
						if(value.compareTo(FDCHelper.ZERO)<0)
							errList.add("\n����Ŀ�д��ڼ۸�С��0��ֵ");
						else{
							sum = sum.add(value);
							isNoPrice = false;
						}
						
					}
				} else if (columnInfo != null
						&& columnInfo.getProperty().equals(
								DescriptionEnum.TotalPriceSum)) {
					sumColKey = column;
				}
				if (columnInfo != null
						&& columnInfo.getProperty().equals(
								DescriptionEnum.Personal)) {
					if (row.getCell(column).getValue() != null) {
						isNoPrice = false;
						if (row.getCell(column).getValue().toString()
								.getBytes().length > 500)
							errList.add("\n����Ŀ��\""+column+"\"�г��ȹ���,���500�ֽ�,ʵ��"
									+ row.getCell(column).getValue().toString()
											.getBytes().length + "�ֽ�");
					}
				}
				if (columnInfo != null
						&& columnInfo.getName().equals("��ע")) {
					if (row.getCell(column).getValue() != null) {
						isNoPrice = false;
					}
				}
			}
			BigDecimal sumValue = FDCNumberConstants.ZERO;
			if (sumColKey != null) {
				Object sumVal = row.getCell(sumColKey).getValue();
				if(sumVal instanceof BigDecimal){
					sumValue = (BigDecimal)sumVal;
				}
				else{
					try{
						if(sumVal==null)
							sumValue = FDCHelper.ZERO;
						else
							sumValue = new BigDecimal(sumVal.toString());
					}
					catch(Exception e){
						sumValue = FDCHelper.ZERO;
					}
					
				}
				if(sumValue.compareTo(FDCHelper.ZERO)<0)
					errList.add("\n����Ŀ���ۺϵ����м۸�С��0");
				//sumValue = (BigDecimal) row.getCell(sumColKey).getValue();
			}
			if (sumValue == null) {
				if (itemNameMap.containsKey(key)) {
					boolean fNoNullTotalPriceSum = hasNoNullTotalPriceSum((ListingItemInfo) itemNameMap
							.get(key));
					if (!fNoNullTotalPriceSum)
						errList.add("�Ѵ����ۺϵ���Ϊ�յ����ݣ������ظ�����");
				}

				sumValue = FDCNumberConstants.ZERO;
			}
			if (isHaveSubAmount && sum.compareTo(sumValue) != 0) {
				logger.debug("sum:" + sum + ", sumVal:" + sumValue);
				errList.add("���С�Ʋ�������ϸ֮��!");
			}
			if (!isHaveSubAmount && sum.compareTo(sumValue) != 0) {
				isNoPrice = false;
			}
			ICell cellState = row.getCell("state");
			if (errList.size() > 0) {
				StringBuffer err = new StringBuffer("�޷�����:");
				for (int k = 0; k < errList.size(); k++) {
					err.append(errList.get(k)) ;
				}
				cellState.setValue(err.toString());
				row.getStyleAttributes().setBackground(ERR_COLOR);
				logger.error("row " + row.getRowIndex() + ", " + err);
			} else {
				// ���������ͬ�Ŀ�����,����������
				// 2008-05-29 ����
				
				if (isNoPrice) {
					cellState.setValue("ֻ������Ŀ");
				} else {
					cellState.setValue("������Ŀ�ͼ۸�");
				}
				ListingItemInfo item = new ListingItemInfo();
				item.setOrgUnit(org.castToFullOrgUnitInfo());
				item.setName(itemName);
				if(this.isCodingRuleAddView && numbers != null && iNumber<numbers.length){
					String tempNum = numbers[iNumber++];
					item.setNumber(tempNum);
					//row.getCell("�ڲ�����").setValue(tempNum);
				}
//					item.setNumber(itemNumber);
				item.setUnit(unit);
				item.setHeadType(this.headType);
				row.setUserObject(item);
				
			}
		KDTableHelper.autoFitRowHeight(this.kdtPrice,row.getRowIndex(),5);
		}
	}

	/**
	 * �����Ŀ�������Ƿ����ۺϵ���Ϊ��ֵ�����п�ֵ�����ݣ���ʾ�����ظ�����
	 *
	 * @param itemInfo
	 * @return
	 * @throws BOSException
	 */
	private boolean hasNoNullTotalPriceSum(ListingItemInfo itemInfo)
			throws BOSException {
		if (itemInfo == null)
			return true;

		String itemId = itemInfo.getId().toString();
		RefPriceCollection refPrices = RefPriceFactory.getRemoteInstance()
				.getRefPriceCollection(
						"select *, entrys.*, entrys.column.* where item.id= '"
								+ itemId + "'");
		if (refPrices == null || refPrices.size() == 0)
			return true;

		boolean fNoNullTotalPriceSum = true;
		boolean fRefNoNull = true;
		for (int iN = 0, n = refPrices.size(); iN < n; iN++) {
			fNoNullTotalPriceSum = fNoNullTotalPriceSum && fRefNoNull;
			if (!fNoNullTotalPriceSum) {
				break;
			}
			RefPriceInfo refInfo = refPrices.get(iN);
			fRefNoNull = true;
			RefPriceEntryCollection entries = refInfo.getEntrys();
			for (int jN = 0, eN = entries.size(); jN < eN; jN++) {
				RefPriceEntryInfo refEntryInfo = entries.get(jN);
				if (refEntryInfo.getColumn() != null
						&& refEntryInfo.getColumn().getProperty().equals(
								DescriptionEnum.TotalPriceSum)) {
					fRefNoNull = false;
					break;
				}
			}
		}
		return fNoNullTotalPriceSum;
	}

	/**
	 * ȡ����֯��ͬ������Ŀ������Map�У�����Ŀ������key
	 *
	 * @param orgName
	 * @param org
	 * @return
	 * @throws BOSException
	 * @throws EASBizException 
	 */
	private Map getSameOrgSameNameItems(CompanyOrgUnitInfo org)
			throws BOSException, EASBizException {		
		ListingItemCollection items = ListingItemFactory.getRemoteInstance()
				.getCollectionForCurOrg(org.getId(),this.headType.getId());
		Map itemNameMap = new HashMap();
		for (int j = 0; j < items.size(); j++) {
			ListingItemInfo item = items.get(j);
			itemNameMap.put(item.getName(), item);
			// itemNumberMap.put(item.getNumber(), item);
			this.dbItems.put(org.getName() + item.getName(), item);
		}
		return itemNameMap;
	}
	/***
	 * ��ListingItemCollection����Ŀ������key������HashMap
	 * @param items
	 * @return
	 */
	public Map listingItemCollectionToHashMap(ListingItemCollection items){
		Map itemNameMap = new HashMap();
		for (int j = 0; j < items.size(); j++) {
			ListingItemInfo item = items.get(j);
			itemNameMap.put(item.getName(), item);
		}
		return itemNameMap;
	}

	/**
	 * ��ȡ�ϼ���֯�£���ͬ���Ƶ���Ŀ������map������Ŀ������key
	 *
	 * @param org
	 * @return
	 * @throws BOSException
	 * @throws EASBizException 
	 */
	private Map getUpOrgSameNameItems(CompanyOrgUnitInfo org)
			throws BOSException, EASBizException {		
		ListingItemCollection items = ListingItemFactory.getRemoteInstance()
				.getCollectionForUpOrg(org.getId(),this.headType.getId());
		return listingItemCollectionToHashMap(items);
	}
	/**
	 * ��ȡ�¼���֯�£���ͬ���Ƶ���Ŀ������map������Ŀ������key
	 *
	 * @param org
	 * @return
	 * @throws BOSException
	 * @throws EASBizException 
	 */
	private Map getDownOrgSameNameItems(CompanyOrgUnitInfo org)
			throws BOSException, EASBizException {		
		ListingItemCollection items = ListingItemFactory.getRemoteInstance()
				.getCollectionForDownOrg(org.getId(),this.headType.getId());
		return listingItemCollectionToHashMap(items);
	}
	
	private void fillRowWithSheetData(Sheet sheet, Map columns,
			CompanyOrgUnitInfo org) {
		Set columnSet = columns.keySet();
		int sheetInd = sheet.getSheetIndex();
		KDSSheet kdsSheet = book.getSheet(new Integer(sheetInd));
		for (int j = 2, n = sheet.getMaxRowIndex(); j <= n; j++) {
			IRow addRow = kdtPrice.addRow();
			addRow.getCell("org").setValue(org.getName());
			for (Iterator iter = columnSet.iterator(); iter.hasNext();) {
				int index = ((Integer) iter.next()).intValue();
				String column = (String) columns.get(new Integer(index));
				Cell excelCell = sheet.getCell(j, index, true);
				Variant cellRawVal = excelCell.getValue();
				if (Variant.isNull(cellRawVal)) {
					continue;
				}
//				HeadColumnInfo columnInfo = (HeadColumnInfo) kdtPrice
//						.getColumn(column).getUserObject();
				HeadColumnInfo columnInfo = (HeadColumnInfo)colObjMap.get(column);
				ColumnTypeEnum colType = (columnInfo == null) ? ColumnTypeEnum.String
						: columnInfo.getColumnType();
				Object kdsVal = kdsSheet.getCell(j, index, true).getValue();
				InviteHelper.loadExcelVal(addRow.getCell(column), cellRawVal,
						kdsVal, colType);
			}
		}
	}

	/**
	 * ��excel�е�����ӵ�map�У�������������ֵ��ֻ��ȡexcel����kdtable�д��ڵ���
	 *
	 * @param sheet
	 * @return
	 */
	private Map getExcelInTableColumnList(Sheet sheet) {
		Row row1 = sheet.getRow(0, false);
		if (row1 == null) {
			logger.error("no head row!!!");
			return null;
		}
		Map columns = new HashMap();
		for (int i = 0, n = sheet.getMaxColIndex(); i <= n; i++) {
			Variant cellRawVal = sheet.getCell(1, i, true).getValue();
			if (Variant.isNull(cellRawVal)) {
				cellRawVal = sheet.getCell(0, i, true).getValue();
			}
			if (!cellRawVal.isNull()
					&& kdtPrice.getColumn(cellRawVal.toString()) != null) {
				columns.put(new Integer(i), cellRawVal.toString());
			}
		}
		return columns;
	}

	public void fillHead() throws EASBizException, BOSException {
		if (headType == null) {
			return;
		}
		kdtPrice.getSelectManager().setSelectMode(KDTSelectManager.ROW_SELECT);
		kdtPrice.getStyleAttributes().setLocked(true);
		IRow headRow0 = kdtPrice.addHeadRow();
		IRow headRow1 = kdtPrice.addHeadRow();
		IColumn column = kdtPrice.addColumn();
		column.setWidth(350);
		column.setKey("state");
		headRow0.getCell("state").setValue("����״̬");
		column = kdtPrice.addColumn();
		column.setKey("org");
		headRow0.getCell("org").setValue("��֯");
		column = kdtPrice.addColumn();
		column.setKey("�ڲ�����");
		headRow0.getCell("�ڲ�����").setValue("�ڲ�����");
		this.setIsCodingRuleAddView();
		//if(!this.isCodingRuleAddView){
			column.getStyleAttributes().setHided(true);
		//}
		column = kdtPrice.addColumn();
		column.setKey("��Ŀ����");
		headRow0.getCell("��Ŀ����").setValue("��Ŀ����");
		column = kdtPrice.addColumn();
		column.setKey("��λ");
		headRow0.getCell("��λ").setValue("��λ");
		kdtPrice.getHeadMergeManager().mergeBlock(0, 0, 1, 0);
		kdtPrice.getHeadMergeManager().mergeBlock(0, 1, 1, 1);
		kdtPrice.getHeadMergeManager().mergeBlock(0, 2, 1, 2);
		kdtPrice.getHeadMergeManager().mergeBlock(0, 3, 1, 3);
		kdtPrice.getHeadMergeManager().mergeBlock(0, 4, 1, 4);
		HeadColumnCollection cols = headType.getEntries();
		HeadColumnCollection refCols = new HeadColumnCollection();
		for (int i = 0; i < cols.size(); i++) {
			HeadColumnInfo info = cols.get(i);
			if (info.isIsLeaf()
					&& !info.getProperty().equals(DescriptionEnum.System)
					&& !info.getProperty().equals(DescriptionEnum.ProjectAmt)
					&& !info.getProperty()
							.equals(DescriptionEnum.ProjectAmtSum)
					&& !info.getProperty().equals(DescriptionEnum.Amount)
					&& !info.getProperty().equals(DescriptionEnum.AmountSum)) {
				column = this.kdtPrice.addColumn();
				//column.setUserObject(info);
				colObjMap.put(info.getName(),info);
				column.setKey(info.getName());
				if (info.getColumnType().equals(ColumnTypeEnum.Amount)) {
					column.getStyleAttributes().setHorizontalAlign(
							HorizontalAlignment.RIGHT);
					column.getStyleAttributes().setNumberFormat(
							FDCHelper.getNumberFtm(2));

				} else if (info.getColumnType().equals(ColumnTypeEnum.Date)) {
					column.getStyleAttributes().setNumberFormat("yyyy-MM-dd");
				}
				refCols.add(info);
			}
		}
		int count = 0;
		int baseCount = 5;
		for (int j = 0; j < refCols.size(); j++) {
			HeadColumnInfo infoColumn = refCols.get(j);
			headRow1.getCell(j + baseCount).setValue(infoColumn.getName());
			if (infoColumn.getParent() != null) {
				headRow0.getCell(j + baseCount).setValue(
						infoColumn.getParent().getName());
				if (j < refCols.size() - 1) {
					HeadColumnInfo infoNext = refCols.get(j + 1);
					if (infoNext.getParent() != null
							&& infoColumn.getParent().equals(
									infoNext.getParent())) {
						count++;
					} else {
						kdtPrice.getHeadMergeManager().mergeBlock(0,
								j - count + baseCount, 0, j + baseCount);
						count = 0;
					}
				} else {
					kdtPrice.getHeadMergeManager().mergeBlock(0,
							j - count + baseCount, 0, j + baseCount);
					count = 0;
				}
			} else {
				kdtPrice.getHeadMergeManager().mergeBlock(0, j + baseCount, 1,
						j + baseCount);
			}
		}
		tHelper.setCanMoveColumn(false);
	}

	protected void f7HeadType_dataChanged(DataChangeEvent e) throws Exception {
		super.f7HeadType_dataChanged(e);
		HeadTypeInfo newType = (HeadTypeInfo) this.f7HeadType.getValue();
		if (newType == null) {
			this.headType = null;
			this.book = null;
			this.txtFileName.setText(null);
			this.btnChoose.setEnabled(false);
			this.fillTable();
			return;
		}
		EntityViewInfo view = new EntityViewInfo();
		view.getSelector().add("*");
		view.getSelector().add("parent.*");
		view.getSorter().add(new SorterItemInfo("longNumber"));
		FilterInfo filter = new FilterInfo();
		view.setFilter(filter);
		filter.getFilterItems().add(
				new FilterItemInfo("headType.id", newType.getId().toString()));
		HeadColumnCollection entrys = HeadColumnFactory.getRemoteInstance()
				.getHeadColumnCollection(view);
		newType.getEntries().clear();
		newType.getEntries().addCollection(entrys);
		if (this.headType == null) {
			this.headType = newType;
			this.book = null;
			this.txtFileName.setText(null);
			this.btnChoose.setEnabled(true);
			this.fillTable();
		} else {
			if (!this.headType.getId().toString().equals(
					newType.getId().toString())) {
				this.headType = newType;
				this.book = null;
				this.txtFileName.setText(null);
				this.btnChoose.setEnabled(true);
				this.fillTable();
			}
		}
	}

	protected void btnYes_actionPerformed(ActionEvent e) throws Exception {
		if (headType == null) {
			MsgBox.showInfo("��ѡ���ͷ����");
			return;
		}
		HeadColumnInfo unitCol = null;
		for (int i = 0; i < this.headType.getEntries().size(); i++) {
			HeadColumnInfo col = this.headType.getEntries().get(i);
			if (col.getName().equals("��λ")) {
				unitCol = col;
				break;
			}
		}
		for (int i = 0; i < this.kdtPrice.getRowCount(); i++) {
			IRow row = this.kdtPrice.getRow(i);
			if (row.getUserObject() != null) {
				ListingItemInfo item = (ListingItemInfo) row.getUserObject();
				String key = item.getOrgUnit().getName() + item.getName();
				if (item.getId() == null) {
					if (!this.dbItems.containsKey(key)) {
						IObjectPK objectPK = ListingItemFactory
								.getRemoteInstance().submit(item);
						item.setId(BOSUuid.read(objectPK.toString()));
						this.dbItems.put(key, item);
					} else {
						// ��������ļ�����ͬ������Ŀ��ȡ�ѱ������Ŀ�Ա����Ӧ�Ĳο���
						item = (ListingItemInfo)dbItems.get(key);
					}
				}

				String cellStateVal = ((String) row.getCell("state").getValue());
				if (cellStateVal.equals("ֻ������Ŀ")) {
					continue;
				}
				if (cellStateVal.equals("û�м۸�,��Ŀ���ݿ��Ѿ�����")) {
					continue;
				}
				// �����������ͬ�Ŀ�ֵ,�ڶ���������
				// 2008-05-29 ����
				if (cellStateVal.equals("������ͬ����Ŀ������û�м۸񲻵���")) {
					continue;
				}
				RefPriceInfo refPrice = new RefPriceInfo();
				refPrice.setItem(item);
				refPrice.setDate(new Date());
				// ��5���ǲο������ݿ�ʼ��
				for (int j = 5; j < this.kdtPrice.getColumnCount(); j++) {
					Object value = row.getCell(j).getValue();
					if (value != null) {
//						HeadColumnInfo col = (HeadColumnInfo) this.kdtPrice
//								.getColumn(j).getUserObject();
						HeadColumnInfo col = (HeadColumnInfo)colObjMap.get(this.kdtPrice.getColumn(j).getKey());
						RefPriceEntryInfo entry = new RefPriceEntryInfo();
						entry.setColumn(col);
						if (col.getColumnType().equals(ColumnTypeEnum.Amount)) {
							if(value instanceof BigDecimal){
								entry.setValue((BigDecimal) value);
							}
							else{
								try{
									entry.setValue(new BigDecimal(value.toString()));
								}
								catch(Exception ex){
									entry.setValue( FDCHelper.ZERO);
								}
								
							}
						} else if (col.getColumnType().equals(
								ColumnTypeEnum.Date)) {
							entry.setDateValue((Date) value);
						} else {
							entry.setText((String) value);
						}
						refPrice.getEntrys().add(entry);
					}
				}
				RefPriceEntryInfo entry = new RefPriceEntryInfo();
				entry.setColumn(unitCol);
				entry.setText((String) row.getCell("��λ").getValue());
				refPrice.getEntrys().add(entry);
				RefPriceFactory.getRemoteInstance().submit(refPrice);
			}
		}
		this.destroyWindow();
	}
	
	/**
     * ������Ҫ����ı��
     */
    protected KDTable getTableForCommon()
    {
        return this.kdtPrice;
    }

	
	/**
     * output kDButton_actionPerformed method
     */
    protected void kDButton_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
    	if( headType == null){
    		MsgBox.showInfo(this,"����ѡ���ͷ���ͣ�");
    		return;
    	}
		KDTable  table = getTableForCommon();
		if(table != null){
			table.getIOManager().exportExcelToTempFile(false);			
		}
    }

	protected void btnNo_actionPerformed(ActionEvent e) throws Exception {
		super.btnNo_actionPerformed(e);
		this.destroyWindow();
	}
}