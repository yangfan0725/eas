/**
 * output package name
 */
package com.kingdee.eas.fdc.sellhouse.client;

import java.awt.Color;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeModel;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.table.IColumn;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTDataRequestManager;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectManager;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent;
import com.kingdee.bos.ctrl.kdf.util.style.Styles.HorizontalAlignment;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.eas.base.commonquery.client.CommonQueryDialog;
import com.kingdee.eas.basedata.assistant.PeriodFactory;
import com.kingdee.eas.basedata.assistant.PeriodInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.basedata.FDCDateHelper;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.MoneySysTypeEnum;
import com.kingdee.eas.fdc.sellhouse.BuildingInfo;
import com.kingdee.eas.fdc.sellhouse.DayMainTableInfo;
import com.kingdee.eas.fdc.sellhouse.DayPurchaseBldCollection;
import com.kingdee.eas.fdc.sellhouse.DayPurchaseBldFactory;
import com.kingdee.eas.fdc.sellhouse.DayPurchaseBldInfo;
import com.kingdee.eas.fm.common.FMConstants;
import com.kingdee.eas.framework.ICoreBase;

/**
 * output class name
 */
public class HistorySaleDirectionStatUI extends AbstractHistorySaleDirectionStatUI
{
    private static final Logger logger = CoreUIObject.getLogger(HistorySaleDirectionStatUI.class);
    
    private HistorySaleDirectionFilterUI filterUI = null;
    private Map buildingMap = null;

    public HistorySaleDirectionStatUI() throws Exception
    {
        super();
    }
    
    protected boolean initDefaultFilter() {
		return true;
	}

	private HistorySaleDirectionFilterUI getFilterUI() {
		if (this.filterUI == null) {
			try {
				this.filterUI = new HistorySaleDirectionFilterUI();
				this.filterUI.onLoad();
			} catch (Exception e) {
				handleException(e);
				abort(e);
			}
		}
		return this.filterUI;
	}
	
	protected CommonQueryDialog initCommonQueryDialog() {
		CommonQueryDialog commonQueryDialog = super.initCommonQueryDialog();
		commonQueryDialog.setWidth(400);
		commonQueryDialog.addUserPanel(getFilterUI());
		commonQueryDialog.setShowSorter(false);
		commonQueryDialog.setShowFilter(false);
		return commonQueryDialog;
	}
	
	public void onLoad() throws Exception {		
		super.onLoad();
		
		this.actionAddNew.setVisible(false);
		this.actionEdit.setVisible(false);
		this.actionView.setVisible(false);
		this.actionRemove.setVisible(false);
		this.menuEdit.setVisible(false);
		
		this.tblMain.getStyleAttributes().setLocked(true);
		this.tblMain.getSelectManager().setSelectMode(KDTSelectManager.ROW_SELECT);
	}
	
	public void onShow() throws Exception {
		super.onShow();
		
     	int indexNum = this.tblMain.getColumn("name").getColumnIndex();     	
     	this.tblMain.getViewManager().setFreezeView(-1, indexNum+1);
	}

	protected void execQuery() {
		this.tblMain.getDataRequestManager().setDataRequestMode(KDTDataRequestManager.REAL_MODE);
		
     	int indexNum = this.tblMain.getColumn("name").getColumnIndex();     	
     	this.tblMain.getViewManager().setFreezeView(-1, indexNum+1);
		
		//����¥����
     	if(buildingMap==null || this.tblMain.getRowCount()==0) {
			this.tblMain.removeRows();
			initTableBuildingTree();
     	}
		
		this.tblMain.getColumn("durCratCount").setWidth(250);
		this.tblMain.getColumn("durCratCount").getStyleAttributes().setNumberFormat("%r-[ ]0.2n");

		//		��� �� ͳ����  
		int durColIndex = this.tblMain.getColumnIndex("durCratCount");
		int allColCount = this.tblMain.getColumnCount();
		for(int i=allColCount;i>=durColIndex+1;i--) {
			this.tblMain.removeColumn(i);
		}
		
		//������������
		clearAllDate();		
		
		if(buildingMap==null)  return;
	
		Map filterMap = CommerceHelper.convertFilterItemCollToMap(this.mainQuery.getFilter());
		
		String periodId = (String) filterMap.get("periodId");
		if(periodId == null){
			return;
		}
		
		PeriodInfo period = null;
		try {
			period = PeriodFactory.getRemoteInstance().getPeriodInfo(new ObjectUuidPK(periodId));
		} catch (EASBizException e1) {
			e1.printStackTrace();
		} catch (BOSException e1) {
			e1.printStackTrace();
		}
		if(period == null){
			return;
		}
		Date beginDate = period.getBeginDate();
		Date selectEndDate = period.getEndDate();
		Date endDate = this.getNextDayBeginTime(selectEndDate);  //��ѡ��Ľ���ʱ����һ��Ŀ�ʼʱ��
		
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		view.setFilter(filter);
		filter.getFilterItems().add(new FilterItemInfo("dayMain.saleBalance.period", period.getId().toString()));
		
		view.getSelector().add("*");
		view.getSelector().add("dayMain.dayNum");
		
		DayPurchaseBldCollection dayPurchaseBlds = null;
		try {
			dayPurchaseBlds = DayPurchaseBldFactory.getRemoteInstance().getDayPurchaseBldCollection(view);
		} catch (BOSException e) {
			handleException(e);
			this.abort();
		}
		
		Map data = new HashMap();
		Map totalAmountMap = new HashMap();
		for(int i=0; i<dayPurchaseBlds.size(); i++){
			DayPurchaseBldInfo dayPurchaseBld = dayPurchaseBlds.get(i);
			BuildingInfo building = dayPurchaseBld.getBuilding();
			if(building == null){
				continue;
			}
			String buildingId = building.getId().toString();
			//�����ǰ������¥����ѹ��û�����¥��,����ͳ�Ƹ�¥��������
			if(buildingMap.get(buildingId) == null){
				continue;
			}
			
			BigDecimal amount = FDCHelper.toBigDecimal(dayPurchaseBld.getAmount());
			
			DayMainTableInfo dayMain = dayPurchaseBld.getDayMain();
			if(dayMain == null){
				continue;
			}
			
			Date date = SHEHelper.toDate(dayMain.getDayNum());
			if(date == null){
				continue;
			}
			Map buildMap = (Map) data.get(buildingId);
			if(buildMap == null){
				buildMap = new HashMap();
				buildMap.put(date, amount);
				
				data.put(buildingId, buildMap);
			}else{
				buildMap.put(date, amount);
			}
			
			BigDecimal tolAmount = (BigDecimal) totalAmountMap.get(buildingId);
			if(tolAmount == null){
				totalAmountMap.put(buildingId, amount);
			}else{
				totalAmountMap.put(buildingId, tolAmount.add(amount));
			}
		}
		
		//��� �� ͳ���� 			
		Date tempBeginDate = FDCDateHelper.getDayBegin(beginDate);
		int dayColumnIndex = 1;
		while(tempBeginDate.before(endDate)) {
			IColumn addColumn = this.tblMain.addColumn();
			addColumn.setKey("dayCratCount"+dayColumnIndex);
			//������ʾ��ʽ
			addColumn.getStyleAttributes().setNumberFormat("%r-[ ]0.2n");
			addColumn.getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
			
			this.tblMain.getHeadRow(0).getCell("dayCratCount"+dayColumnIndex).setValue(FDCDateHelper.formatDate2(tempBeginDate));
			
			for(Iterator itor = buildingMap.keySet().iterator(); itor.hasNext(); ){
				String buildingId = (String) itor.next();
				Map buildMap = (Map) data.get(buildingId);
				IRow row = (IRow) buildingMap.get(buildingId);
				if(buildMap == null  ||  row == null){
					continue;
				}
				BigDecimal value = FDCHelper.toBigDecimal(buildMap.get(tempBeginDate));
				row.getCell("dayCratCount"+dayColumnIndex).setValue(value);
			}
			dayColumnIndex++;
			tempBeginDate = getNextDayBeginTime(tempBeginDate);  //ָ�����ڵ���һ��
		}

		// '�����ۼƺ�ͬ�ܼ� (***~*)' durCratCount  ��������Ϊ�գ������ֱ�ӷ���    --��������ҵ��,�˷��ͱ�� ���⴦�� 
		String durCratHeadName = "�����ۼƺ�ͬ�ܼ�\r\n"+"("+ FDCDateHelper.formatDate(beginDate)+"-"+ FDCDateHelper.formatDate(selectEndDate) +"�ϼ�)";
		this.tblMain.getHeadRow(0).getCell("durCratCount").setValue(durCratHeadName);
		for(Iterator itor = buildingMap.keySet().iterator(); itor.hasNext(); ){
			String buildingId = (String) itor.next();
			BigDecimal tolAmount = FDCHelper.toBigDecimal(totalAmountMap.get(buildingId));
			
			IRow row = (IRow) buildingMap.get(buildingId);
			if(row == null){
				continue;
			}
			row.getCell("durCratCount").setValue(tolAmount);
		}
		
		setUnionData();
	}
	
	private Date getNextDayBeginTime(Date date) {  //ָ��ʱ��һ�����һ�쿪ʼʱ��
		if(date==null) return new Date();
		
		Calendar cal = new GregorianCalendar();
		cal.setTime(date);
		cal.set(Calendar.DATE, cal.get(Calendar.DATE) + 1);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		return cal.getTime();
	}
	
	/**
	 * ����¥�������������е�������Ŀ
	 */
	private void initTableBuildingTree(){
		try {
			TreeModel thisTree = SHEHelper.getBuildingTree(this.actionOnLoad,MoneySysTypeEnum.SalehouseSys);
			DefaultMutableTreeNode thisRoot = (DefaultMutableTreeNode) thisTree.getRoot();	
			buildingMap = new HashMap();			
			fillNode(this.tblMain,thisRoot);
			this.tblMain.getTreeColumn().setDepth(thisRoot.getDepth() + 1);
		} catch (Exception e) {
			this.handleException(e);
			this.abort();
		}
	}
	
	private void fillNode(KDTable table, DefaultMutableTreeNode node)	throws BOSException, SQLException, EASBizException {
		IRow row = table.addRow();
		row.setTreeLevel(node.getLevel());
		String space = "";
		for (int i = 0; i < node.getLevel(); i++) {
			space += " ";
		}
		if (node.getUserObject() instanceof BuildingInfo) {
			BuildingInfo thisInfo = (BuildingInfo) node.getUserObject();
			row.getCell("name").setValue(space + thisInfo.getName());
			row.setUserObject(node.getUserObject());
			this.buildingMap.put(thisInfo.getId().toString(), row);			
		} else {
			row.getStyleAttributes().setBackground(new Color(0xF0EDD9));
			row.getCell("name").setValue(space + node);
		}
		if (!node.isLeaf()) {
			for (int i = 0; i < node.getChildCount(); i++) {
				this.fillNode(table, (DefaultMutableTreeNode) node.getChildAt(i));
			}
		}
	}
	
	private void clearAllDate() {
		int colCount = this.tblMain.getColumnCount();
		int firstColNum = this.tblMain.getColumnIndex("durCratCount");
		for(int i=0;i<this.tblMain.getRowCount();i++)  {
			IRow row = this.tblMain.getRow(i);
			for(int j=firstColNum;j<colCount;j++) {
				row.getCell(j).setValue(null);
			}
		}
	}
	
	/**
	 * ͳ�����и��ڵ�
	 */
	public void setUnionData() {
		for (int i = 0; i < tblMain.getRowCount(); i++) {
			IRow row = tblMain.getRow(i);            //��ǰ��
			if (row.getUserObject() == null) {
				// ���û�����
				int level = row.getTreeLevel();
				List rowList = new ArrayList();     //����Ҷ�ڵ���Ŀ����
				for (int j = i + 1; j < tblMain.getRowCount(); j++) {
					IRow rowAfter = tblMain.getRow(j);
					if (rowAfter.getTreeLevel() <= level) {
						break;
					}
					if (rowAfter.getUserObject() != null) {
						rowList.add(rowAfter);
					}
				}
				
				for (int j = 2; j < this.tblMain.getColumnCount(); j++) {
					BigDecimal aimCost = FMConstants.ZERO;
					for (int rowIndex = 0; rowIndex < rowList.size(); rowIndex++) {
						IRow rowAdd = (IRow) rowList.get(rowIndex);
						Object value = rowAdd.getCell(j).getValue();
						if (value != null) {
							if (value instanceof BigDecimal) {
								aimCost = aimCost.add((BigDecimal) value);
							} else if (value instanceof Integer) {
								aimCost = aimCost.add(new BigDecimal(((Integer) value).toString()));
							}
						}
					}
					if(!aimCost.equals(FMConstants.ZERO)) row.getCell(j).setValue(aimCost);
				}
			}
		}
	}
	
	protected void tblMain_tableClicked(KDTMouseEvent e) throws Exception {
	}
	
	protected ICoreBase getBizInterface() throws Exception {
		return null;
	}

	protected String getEditUIName() {
		return null;
	}
	
	/**
	 * ����Ҽ��������ж�����
	 */
	public void handUIException(Throwable exc) {
		if(exc instanceof BOSException&&exc.getMessage().startsWith("Can't found propertyUnit:")){
			logger.error(exc);
		}
		else
			super.handUIException(exc);
	}
}