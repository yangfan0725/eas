package com.kingdee.eas.fdc.invite.client;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.common.util.StringUtil;
import com.kingdee.bos.ctrl.common.variant.SyntaxErrorException;
import com.kingdee.bos.ctrl.common.variant.Variant;
import com.kingdee.bos.ctrl.excel.model.struct.Sheet;
import com.kingdee.bos.ctrl.kdf.kds.KDSBook;
import com.kingdee.bos.ctrl.kdf.read.POIXlsReader;
import com.kingdee.bos.ctrl.kdf.table.ICell;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.KDTableHelper;
import com.kingdee.bos.ctrl.swing.KDTabbedPane;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.UIException;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.basedata.org.CompanyOrgUnitCollection;
import com.kingdee.eas.basedata.org.CompanyOrgUnitFactory;
import com.kingdee.eas.basedata.org.CompanyOrgUnitInfo;
import com.kingdee.eas.basedata.org.ICompanyOrgUnit;
import com.kingdee.eas.basedata.org.NewOrgViewHelper;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCNumberHelper;
import com.kingdee.eas.fdc.basedata.client.FDCPrintHelper;
import com.kingdee.eas.fdc.basedata.client.FDCTableHelper;
import com.kingdee.eas.fdc.invite.ColumnTypeEnum;
import com.kingdee.eas.fdc.invite.NewListTempletEntryFactory;
import com.kingdee.eas.fdc.invite.NewListTempletValueInfo;
import com.kingdee.eas.fdc.invite.NewListingEntryFactory;
import com.kingdee.eas.fdc.invite.NewListingValueFactory;
import com.kingdee.eas.fdc.invite.NewListingValueInfo;
import com.kingdee.eas.fdc.invite.RefPriceEntryInfo;
import com.kingdee.eas.fdc.invite.RefPriceFactory;
import com.kingdee.eas.framework.client.CoreUI;
import com.kingdee.eas.util.client.MsgBox;
import com.kingdee.util.DateTimeUtils;

public class InviteHelper {

	private static final Logger logger = Logger
			.getLogger("com.kingdee.eas.fdc.invite.client.InviteHelper");

	// ���KDTable������excel

	// ָ���Ƿ���Ҫ��ӡͷ
	public static void exportToExcel(Collection tables, boolean withHead)
			throws Exception {
		FDCTableHelper.exportToExcel(tables, withHead);
	}

	public static void exportToExcel(Collection tables) throws Exception {
		FDCTableHelper.exportToExcel(tables, true);
	}

	// ����KDTable������excel
	public static void exportToExcel(KDTable table) throws Exception {
		FDCTableHelper.exportToExcel(table, true);
	}

	/**
	 * ���ô�ӡ����
	 * 
	 * @param table
	 * @param title
	 * @param titleFontsize
	 *            ���������С
	 */
	public static void setPrintTitle(KDTable table, String title,
			int titleFontsize) {
		FDCPrintHelper.setPrintTitle(table, title, titleFontsize);
	}

	public static void setPrintTitle(KDTable table) {
		setPrintTitle(table, table.getName(), 0);
	}

	/**
	 * ��Objectת����BigDecimal,Ĭ��Ϊ��
	 * 
	 * @param obj
	 * @return
	 */
	public static BigDecimal convertToBigDecimal(Object obj) {
		return FDCNumberHelper.toBigDecimal(obj);
	}

	/**
	 * load excel cell value to the kdtable's cell. the cellRawVal should be
	 * checked before u call the method
	 * 
	 * @param cell
	 * @param cellRawVal
	 * @param colType
	 */
	public static void loadExcelVal(ICell cell, Variant cellRawVal,
			ColumnTypeEnum colType) {
		String value = cellRawVal.toString().trim();
		if (colType.equals(ColumnTypeEnum.Amount)) {
			cell.setValue(FDCNumberHelper.toBigDecimal(value));
		} else if (colType.equals(ColumnTypeEnum.Date)) {
			Date dateVal = null;
			if (cellRawVal.isDate()) {
				if (cellRawVal.getVt() == Variant.VT_CALENDAR) {
					dateVal = ((Calendar) cellRawVal.getValue()).getTime();
				} else {
					dateVal = (Date) cellRawVal.getValue();
				}
			} else {
				try {
					//�����˶�����ת��Ϊ���ڵ�һ���ж�
					//��������֣�ת��Ϊ���ڵ�ʱ��Ӧ��-1������Excel�����ֵ��Ӧ
					if(cellRawVal.isNumber()){
						Calendar cal= cellRawVal.toCalendar();
						cal.add(Calendar.DATE,-1);
						dateVal = cal.getTime();
					}
					else
						dateVal = cellRawVal.toDate();
					logger.debug("Variant.toDate :" + dateVal);
				} catch (SyntaxErrorException e) {
					// excel val may not be date val
					logger.error("cannot parsed as Date", e);
				}
			}
			cell.setValue(dateVal);
			// cell.getStyleAttributes().setNumberFormat("yyyy-m-d");
		} else {
			cell.setValue(value);
		}
		logger.debug("setCellVal:" + cell.getValue());
	}

	/**
	 * <p>
	 * the cellRawVal must be checked before u call the method
	 * </p>
	 * <p>
	 * ����colType����ֵ��com.kingdee.bos.ctrl.excel.model.struct.Book�Ὣ001���ַ����ݽ���Ϊ
	 * BigDecimal���͵����ݡ���Variant.getValue()����1��������KDSBook�ķ��ص�����kdsVal��
	 * ��Book����������Variant����Ϊ����������ڵ��������ݣ���kdsVal��excel���ڲ�����
	 * 
	 * @param cell
	 * @param cellRawVal
	 * @param kdsVal
	 * @param colType
	 */
	public static void loadExcelVal(ICell cell, Variant cellRawVal,
			Object kdsVal, ColumnTypeEnum colType) {
		String value = cellRawVal.toString().trim();
		if (colType.equals(ColumnTypeEnum.Amount)) {
			cell.setValue(FDCNumberHelper.toBigDecimal(value));
		} else if (colType.equals(ColumnTypeEnum.Date)) {
			Date dateVal = null;
			if (cellRawVal.isDate()) {
				if (cellRawVal.getVt() == Variant.VT_CALENDAR) {
					dateVal = ((Calendar) cellRawVal.getValue()).getTime();
				} else {
					dateVal = (Date) cellRawVal.getValue();
				}
			} else {				
				try {
					//�����˶�����ת��Ϊ���ڵ�һ���ж�
					//��������֣�ת��Ϊ���ڵ�ʱ��Ӧ��-1������Excel�����ֵ��Ӧ
					if(cellRawVal.isNumber()){
						//��̬��EXCEL���⣬excel��1900��2��29�վ�Ȼ�Ǵ��ڵģ�������΢��
						BigDecimal temp=cellRawVal.toBigDecimal();
						if(temp.intValue()>=60){
							Calendar cal= cellRawVal.toCalendar();
							//cal.add(Calendar.DATE,-1);
							dateVal = cal.getTime();
						}
						else if(temp.intValue()<60&&temp.intValue()>0){
							Calendar cal= cellRawVal.toCalendar();
							cal.add(Calendar.DATE,1);
							dateVal = cal.getTime();
						}
					}
					else
						dateVal = cellRawVal.toDate();
					logger.debug("Variant.toDate :" + dateVal);
				} catch (SyntaxErrorException e) {
					// excel val may not be date val
					logger.error("cannot parsed as Date", e);
				}
			}
			cell.setValue(dateVal);
			// cell.getStyleAttributes().setNumberFormat("yyyy-m-d");
		} else {
			if(kdsVal instanceof Calendar)
			{
				SimpleDateFormat formatter = new SimpleDateFormat ("yyyy-M-d");
				Calendar cal = (Calendar)kdsVal;		
				cell.setValue(formatter.format(cal.getTime()));
			}
			else
				cell.setValue(String.valueOf(kdsVal));
		}
		logger.debug("setCellVal:" + cell.getValue());
	}

	public static void loadListingValue(ICell cell, NewListingValueInfo value,
			ColumnTypeEnum colType) {
		if (value == null || cell == null)
			return;

		if (colType.equals(ColumnTypeEnum.Amount)) {
			cell.setValue(value.getAmount());
		} else if (colType.equals(ColumnTypeEnum.Date)) {
			cell.setValue(value.getDateValue());
			cell.getStyleAttributes().setNumberFormat(FDCHelper.KDTABLE_DATE_FMT);
		} else {
			cell.setValue(value.getText());
		}
	}

	public static void loadTempletValue(ICell cell,
			NewListTempletValueInfo value, ColumnTypeEnum colType) {
		if (value == null || cell == null)
			return;

		if (colType.equals(ColumnTypeEnum.Amount)) {
			cell.setValue(value.getAmount());
		} else if (colType.equals(ColumnTypeEnum.Date)) {
			cell.setValue(value.getDateValue());
			cell.getStyleAttributes().setNumberFormat(FDCHelper.KDTABLE_DATE_FMT);
		} else {
			cell.setValue(value.getText());
		}
	}

	public static void loadRefPriceEntry(ICell cell, RefPriceEntryInfo entry,
			ColumnTypeEnum colType) {
		if (entry == null || cell == null)
			return;

		if (colType.equals(ColumnTypeEnum.Amount)) {
			cell.setValue(entry.getValue());
		} else if (colType.equals(ColumnTypeEnum.Date)) {
			cell.setValue(entry.getDateValue());
			cell.getStyleAttributes().setNumberFormat(FDCHelper.KDTABLE_DATE_FMT);
		} else {
			cell.setValue(entry.getText());
		}
	}

	public static void storeListingValue(NewListingValueInfo valueInfo,
			Object temp, ColumnTypeEnum colType) {
		if (colType.equals(ColumnTypeEnum.Amount)) {
			valueInfo.setAmount(FDCNumberHelper.toBigDecimal(temp));
		} else if (colType.equals(ColumnTypeEnum.Date)) {
			if(temp instanceof Date)
				valueInfo.setDateValue((Date) temp);
			//temp������String ���͵�һ������ֵ��������ҪparstDate���޸����ڵ������
			else if(temp instanceof String){
				try {
					valueInfo.setDateValue(DateTimeUtils.parseDate(temp.toString()));
				} catch (ParseException e) {
					e.printStackTrace();
				}
			}
		} else {
			valueInfo.setText(temp.toString());
		}
	}

	/***************************************************************************
	 * �ж�Excel��ʽ��ȷ��
	 * 
	 * @param coreui
	 *            UI����ֱ�Ӵ�this
	 * @param fileName
	 *            �ļ�����·��
	 * @return ��ȷ����KDSBook,���򷵻�NULL
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public static KDSBook excelReaderParse(CoreUI coreui, String fileName) {
		try {
			return POIXlsReader.parse2(fileName);
		} catch (Exception e) {
			logger.error("POIXls cannot parse the excel:" + fileName, e);
			MsgBox.showError(coreui, "excel�ļ���ʽ����ȷ�����ý����񵼳���excel��д����");
			return null;
		}
	}

	/***************************************************************************
	 * �жϱ�ͷ�Ƿ�һ�£�����һ�µ���Ϣд��list��������
	 * 
	 * @param table
	 * @param excelSheet
	 * @return
	 * @throws UIException
	 */
	public static List validateImportExcelHeadRow(KDTable table,
			Sheet excelSheet) {

		List importErrorInfoList = new ArrayList();
		for (int col = 0; col < table.getColumnCount(); col++) {
			if (table.getHeadRow(1).getCell(col).getValue() != null) {
				String columnName1 = table.getHeadRow(0).getCell(col)
						.getValue().toString();
				String columnName2 = table.getHeadRow(1).getCell(col)
						.getValue().toString();
				String excelColName = excelSheet.getCell(1, col, true)
						.getText();
				if (StringUtil.isEmptyString(excelColName)) {
					excelColName = excelSheet.getCell(0, col, true).getText();
				}
				if (!columnName1.equals(excelColName)
						&& !columnName2.equals(excelColName)) {
					HashMap errMap = new HashMap();
					errMap.put("seq", (1 + 1) + "");
					errMap.put("errInfo", "��ͷ�ṹ��һ��(EAS��ͷ[����:" + (col + 1)
							+ ";����:" + columnName1 + "],Excel��ͷ[����:"
							+ excelColName + "])!");
					importErrorInfoList.add(errMap);
				}
			}
		}
		if (excelSheet.getMaxColIndex() != table.getColumnCount()-1) {
			HashMap errMap = new HashMap();
			errMap.put("seq", (1 + 1) + "");
			errMap.put("errInfo", "��ͷ�ṹ��һ��,Excel��ͷ���ж������");
			importErrorInfoList.add(errMap);
		}
		return importErrorInfoList;
	}

	/***************************************************************************
	 * ��֤������б����Ƿ���ֵ������и���������ʾ��������false�����򷵻�true
	 * 
	 * @param coreui
	 * @param importErrorInfoList
	 * @return
	 * @throws UIException
	 */
	public static boolean validateErrorInfoList(CoreUI coreui,
			List importErrorInfoList) throws UIException {
		if (importErrorInfoList.size() > 0) {
			UIContext uiContext = new UIContext(coreui);
			uiContext.put("errorInfoList", importErrorInfoList);
			uiContext.put("onlyDisplayDescription", "");
			IUIWindow window = UIFactory.createUIFactory(UIFactoryName.MODEL)
					.create(QuotingPriceImportErrorInfoUI.class.getName(),
							uiContext);
			window.show();
			return false;
		} else {
			return true;
		}
	}
	/***************************************************************************
	 * ��֤�嵥�Ƿ��Ѿ�¼��ο��ۣ����¼���˲ο��ۣ���ʾ�û��Ƿ����������񣬷���false
	 * 
	 * @param coreui
	 * @param billID
	 * @return
	 * @throws EASBizException
	 * @throws BOSException
	 */
	public static boolean validateNewListingHasRefPrice(CoreUI coreui,
			String billID) throws EASBizException, BOSException {
		String sql = "select * where entry.head.head='" + billID + "'" 
				+ " and column.isQuoting=1 "
				+ " and (amount is not null " + " or text is not null "
				+ " or datevalue is not null " + " )";
		if (NewListingValueFactory.getRemoteInstance().exists(sql)) {
			if (MsgBox.YES != MsgBox.showConfirm2(coreui,
					"���嵥�Ѿ�¼���˲ο��ۣ�����޸���Ŀ�������µ���ģ�棬��ɾ���ο��ۣ�ȷ��Ҫ�޸���")) {
				return false;
			}
		}
		return true;
	}
	public static boolean validateItemHasUsed(CoreUI coreui,String itemid) throws EASBizException, BOSException{
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(
				new FilterItemInfo("item.id", itemid));
		if (RefPriceFactory.getRemoteInstance().exists(filter))
			return true;
		
		if (NewListTempletEntryFactory.getRemoteInstance().exists(filter))
			return true;
		
		if (NewListingEntryFactory.getRemoteInstance().exists(filter))
			return true;
		
		return false;
	}
	
	/**
	 * ����Tableÿ���Զ��и�
	 *
	 */
	public static void setAotuHeight(KDTable table){
		if(table != null){
			for(int rowIndex=0;rowIndex<table.getRowCount();rowIndex++){
				KDTableHelper.autoFitRowHeight(table,rowIndex,5);
			}
		}
	}
	
	/**
	 * ����ÿ��KDTabbedPane�е�ÿ��table�Զ��и�
	 *
	 */
	public static void setAotuHeight(KDTabbedPane kDTabbedPane1){
		for(int tableIndex=0;tableIndex<kDTabbedPane1.getTabCount();tableIndex++){
			Object obj = (Object)kDTabbedPane1.getComponentAt(tableIndex);
			if(obj != null && obj instanceof KDTable){
				KDTable table = (KDTable)obj;
				for(int rowIndex=0;rowIndex<table.getRowCount();rowIndex++){
					KDTableHelper.autoFitRowHeight(table,rowIndex,5);
					
				}
			}
			
		}
	}
	
	/**
	 * ����ÿ��KDTabbedPane�е�ÿ��table�Զ��и�
	 *
	 */
	public static void setAutoHeadRowHeight(KDTabbedPane kDTabbedPane1){
		for(int tableIndex=0;tableIndex<kDTabbedPane1.getTabCount();tableIndex++){
			Object obj = (Object)kDTabbedPane1.getComponentAt(tableIndex);
			if(obj != null && obj instanceof KDTable){
				KDTable table = (KDTable)obj;
				for(int rowIndex=0;rowIndex<table.getHeadRowCount();rowIndex++){
					KDTableHelper.autoFitHeadRowHeight(table,rowIndex,5);
					
				}
			}
			
		}
	}
	/***
	 * ��ȡ�ϼ���ϵ��org
	 * @param ctx
	 * @param org
	 * @return
	 * @throws BOSException
	 */
	
	public static Set getUpOrgIdSet(BOSUuid currentOrgId) throws BOSException , EASBizException{
		CompanyOrgUnitInfo currentOrg = CompanyOrgUnitFactory.getRemoteInstance().getCompanyOrgUnitInfo(new ObjectUuidPK(currentOrgId));
		String[] ancestor = NewOrgViewHelper.getAncestor(currentOrg.getLongNumber());
		Set lNumSet = new HashSet();
		for (int i = 0; i < ancestor.length; i++) {
			lNumSet.add(ancestor[i]);
		}
		EntityViewInfo orgView = new EntityViewInfo();
		FilterInfo orgFilter = new FilterInfo();
		orgFilter.getFilterItems().add(
				new FilterItemInfo("longNumber", lNumSet, CompareType.INCLUDE));
		orgFilter.getFilterItems().add(
				new FilterItemInfo("id", currentOrg.getId(), CompareType.NOTEQUALS));
		orgView.setFilter(orgFilter);
		ICompanyOrgUnit iComp = CompanyOrgUnitFactory.getRemoteInstance();
		CompanyOrgUnitCollection ancestorComps = iComp
				.getCompanyOrgUnitCollection(orgView);
		Set orgIdSet = new HashSet(ancestorComps.size());
		for (int i = 0, n = ancestorComps.size(); i < n; i++) {
			orgIdSet.add(ancestorComps.get(i).getId().toString());
		}
		return orgIdSet;
	}
	/***
	 * ��ȡ�¼���ϵ��org
	 * @param ctx
	 * @param org
	 * @return
	 * @throws BOSException
	 */
	
	public static Set getDownOrgIdSet(BOSUuid currentOrgId) throws BOSException , EASBizException{
		CompanyOrgUnitInfo currentOrg = CompanyOrgUnitFactory.getRemoteInstance().getCompanyOrgUnitInfo(new ObjectUuidPK(currentOrgId));
		EntityViewInfo orgView = new EntityViewInfo();
		FilterInfo orgFilter = new FilterInfo();		
		orgFilter.getFilterItems().add(
				new FilterItemInfo("longNumber", currentOrg.getLongNumber() + "!%",
						CompareType.LIKE));		
		orgView.setFilter(orgFilter);
		ICompanyOrgUnit iComp = CompanyOrgUnitFactory.getRemoteInstance();
		CompanyOrgUnitCollection ancestorComps = iComp
				.getCompanyOrgUnitCollection(orgView);
		Set orgIdSet = new HashSet(ancestorComps.size());
		for (int i = 0, n = ancestorComps.size(); i < n; i++) {
			orgIdSet.add(ancestorComps.get(i).getId().toString());
		}
		return orgIdSet;
	}
}
