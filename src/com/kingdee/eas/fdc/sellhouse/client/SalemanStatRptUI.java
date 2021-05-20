/**
 * output package name
 */
package com.kingdee.eas.fdc.sellhouse.client;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeModel;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.data.event.RequestRowSetEvent;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTDataRequestManager;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectManager;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent;
import com.kingdee.bos.ctrl.kdf.util.style.Styles.HorizontalAlignment;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemCollection;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.eas.base.commonquery.client.CommonQueryDialog;
import com.kingdee.eas.base.permission.UserCollection;
import com.kingdee.eas.base.permission.UserFactory;
import com.kingdee.eas.base.permission.UserInfo;
import com.kingdee.eas.basedata.org.OrgStructureInfo;
import com.kingdee.eas.basedata.org.SaleOrgUnitInfo;
import com.kingdee.eas.basedata.person.PersonCollection;
import com.kingdee.eas.basedata.person.PersonFactory;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.fdc.basedata.FDCCustomerParams;
import com.kingdee.eas.fdc.basedata.FDCDateHelper;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.basedata.client.FDCClientHelper;
import com.kingdee.eas.fdc.sellhouse.MoneyTypeEnum;
import com.kingdee.eas.fdc.sellhouse.SalemanStatRptFacadeFactory;
import com.kingdee.eas.fdc.tenancy.MarketingUnitInfo;
import com.kingdee.eas.fdc.tenancy.MarketingUnitSellProjectCollection;
import com.kingdee.eas.fdc.tenancy.MarketingUnitSellProjectFactory;
import com.kingdee.eas.fdc.tenancy.client.TenancyClientHelper;
import com.kingdee.eas.fm.common.FMConstants;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.framework.config.TablePreferencesHelper;
import com.kingdee.eas.util.client.MsgBox;
import com.kingdee.jdbc.rowset.IRowSet;

/**
 * output class name
 */
public class SalemanStatRptUI extends AbstractSalemanStatRptUI {
	private static final Logger logger = CoreUIObject
			.getLogger(SellStatRptUI.class);

	Map rowMap = new HashMap();   //Ӫ����Ԫid+�û�id -->  row���� 
	Set muIds = new HashSet();
	Map muMap = new HashMap();	  //Ӫ����Ԫ��������Ŀ��ӳ��   Ӫ����Ԫid --> ��Ŀid
	
	
	private SalemanStatRptFilterUI filterUI = null;

	private CommonQueryDialog commonQueryDialog = null;

	/**
	 * output class constructor
	 */
	public SalemanStatRptUI() throws Exception {
		super();
	}

	protected CommonQueryDialog initCommonQueryDialog() {
		if (commonQueryDialog != null) {
			return commonQueryDialog;
		}
		commonQueryDialog = super.initCommonQueryDialog();
		commonQueryDialog.setWidth(400);
		commonQueryDialog.addUserPanel(this.getFilterUI());
		commonQueryDialog.setShowSorter(false);
		commonQueryDialog.setShowFilter(false);
		return commonQueryDialog;
	}
	public void handUIException(Throwable exc) {
		if(exc instanceof BOSException&&exc.getMessage().startsWith("Can't found propertyUnit:")){
			logger.error(exc);
		}
		else
			super.handUIException(exc);
	}

	protected String[] getLocateNames() {
		String[] locateNames = new String[1];
		locateNames[0] = "salesman";
		return locateNames;
	}

	private SalemanStatRptFilterUI getFilterUI() {
		if (this.filterUI == null) {
			try {
				this.filterUI = new SalemanStatRptFilterUI(this,this.actionOnLoad);
				//this.filterUI.onLoad();
			} catch (Exception e) {
				e.printStackTrace();
				abort(e);
			}
		}
		return this.filterUI;
	}
	
	/**
	 * output storeFields method
	 */
	public void storeFields() {
		super.storeFields();
	}

	public void onLoad() throws Exception {
		SaleOrgUnitInfo saleOrg = SHEHelper.getCurrentSaleOrg();
		
		FDCClientHelper.addSqlMenu(this, this.menuFile);
		super.onLoad();
		boolean issale=SysContext.getSysContext().getCurrentSaleUnit().isIsBizUnit();
		if(!issale){
    		MsgBox.showInfo("��ʵ����֯�����޸���ʾ����!");
			this.abort();
    	}
//		if(!issale){
////			this.actionRefresh.setEnabled(false);
//////			this.actionPrint.setEnabled(false);
//////			this.actionPrintPreview.setEnabled(false);
////			this.actionQuery.setEnabled(false);
////			this.actionLocate.setEnabled(false);
//		}
		this.actionAddNew.setEnabled(false);
		this.actionEdit.setEnabled(false);
		this.actionRemove.setEnabled(false);
		this.actionView.setEnabled(false);
		this.actionAddNew.setVisible(false);
		this.actionEdit.setVisible(false);
		this.actionRemove.setVisible(false);
		this.actionView.setVisible(false);
//		this.actionLocate.setVisible(false);
		this.menuEdit.setVisible(false);
		// this.actionLocate.setVisible(false);
		this.menuEdit.setVisible(false);
		initTable();
//		fillMarketingUnit();//���Ӫ����֯
//		fillSalesman();
		this.refresh(null);
		
		
//		���ÿ��Ա�����ʽ
		tHelper = new TablePreferencesHelper(this);
	}
	  private Date addDays(Date date)
	    {
	    	Calendar calendar = new GregorianCalendar();
	    	calendar.setTime(date);
	    	calendar.add(Calendar.DATE,1);
	    	return calendar.getTime();
	    }
	private void fillMarketingUnit() {
		tblMain.removeRows(false);
		Map dataMap=new HashMap();
		Date beginDate = this.getBeginQueryDate();
		Date endDate =this.getEndQueryDate();
		
		if (beginDate != null) {
			dataMap.put("beginDate", addDays(FDCDateHelper.getSqlDate(beginDate)));
		}
		if (endDate != null) {
			dataMap.put("endDate",FDCDateHelper.getSqlDate(endDate));
		}
		
		TreeModel marketingTree = null;
		try {
			marketingTree = FDCTreeHelper.getMarketTree(this.actionOnLoad,dataMap);
		} catch (Exception e) {
			this.handleException(e);
		}
		DefaultKingdeeTreeNode root = (DefaultKingdeeTreeNode) marketingTree.getRoot();
		tblMain.getTreeColumn().setDepth(root.getDepth() + 1);
		fillNode(tblMain, (DefaultMutableTreeNode) root);
		
		
		//��ѯ��Ӫ����Ԫ��Ӧ�� ������Ŀ����
		// SELECT *, head.name, eventType.name, receptionType.name, commerceChance.name WHERE commerceChance.id = 'hFUMqafgTAOAfCrwM31CdquX5Yo=' ORDER BY eventDate DESC
		if(muIds.size()>0) {
			String muIdStr = "";
			Iterator iter = muIds.iterator();
			while(iter.hasNext()) {
				muIdStr += ",'" + (String)iter.next() + "'";
			}
			muIdStr = muIdStr.substring(1);
			
			try{
				MarketingUnitSellProjectCollection muColl = MarketingUnitSellProjectFactory.getRemoteInstance()
					.getMarketingUnitSellProjectCollection("select head.id,sellProject.id where sellProject.id in ("+muIdStr+")");
				for(int i=0;i<muColl.size();i++) {
					String muIdString = muColl.get(i).getHead().getId().toString();
					String spIdString = muColl.get(i).getSellProject().getId().toString();					
					if(muMap.get(muIdString)!=null) {
						Set proSet = (Set)muMap.get(muIdString);
						proSet.add(spIdString);
						muMap.put(muIdString, proSet);
					}else{
						Set proSet = new HashSet();
						proSet.add(spIdString);
						muMap.put(muIdString, proSet);
					}
				}
			}catch(Exception e){
				e.printStackTrace();
				this.abort();
			}
			
		}
	}

	private void fillNode(KDTable tblMain, DefaultMutableTreeNode node) {
		IRow row = tblMain.addRow();
		row.setTreeLevel(node.getLevel());
		String space = "";
		for (int i = 0; i < node.getLevel(); i++) {
			space += " ";
		}
		if (node.getUserObject() instanceof UserInfo) {
			UserInfo user = (UserInfo) node.getUserObject();
			row.getCell("user").setValue(space + user.getNumber());
			row.getCell("salesman").setValue(space + user.getName());
			row.setUserObject(user);
			//���Ҹ��ڵ��Ӫ����Ԫ
			DefaultMutableTreeNode parentNode = (DefaultMutableTreeNode)node.getParent();
			//�����Ƿ���MarketingUnitInfo����OrgStructureInfo  --jiadong
			if(parentNode.getUserObject() instanceof MarketingUnitInfo){
				MarketingUnitInfo muInfo = (MarketingUnitInfo)parentNode.getUserObject();
				this.rowMap.put(muInfo.getId().toString() + user.getId().toString(), row);
			}else if(parentNode.getUserObject() instanceof OrgStructureInfo){
				OrgStructureInfo muInfo = (OrgStructureInfo)parentNode.getUserObject();
				this.rowMap.put(muInfo.getId().toString() + user.getId().toString(), row);
			}

		} else {
			row.getStyleAttributes().setBackground(new Color(0xF0EDD9));
			row.getCell("user").setValue(space + node);
			row.getCell("salesman").setValue(space + node);
			row.setMergeable(true);
			tblMain.getMergeManager().mergeBlock(row.getRowIndex(), 0, row.getRowIndex(), 1);
		}
		
		//����ڵ���Ӫ����Ԫ  muIds
		if(node.getUserObject() instanceof MarketingUnitInfo) {
			MarketingUnitInfo muInfo = (MarketingUnitInfo)node.getUserObject();
			muIds.add(muInfo.getId().toString());
		}
		
		if (!node.isLeaf()) {
			for (int i = 0; i < node.getChildCount(); i++) {
				this.fillNode(tblMain, (DefaultMutableTreeNode) node
						.getChildAt(i));
			}
		}
	}

	

	protected void execQuery() {
		fillMarketingUnit();
		
		if(tblMain.getRowCount() == 0){
			return;
		}
		
		try {
			fillData();
			
		} catch (Exception e) {
			this.handleException(e);
		}
	}

	public void appendFilterSql(FDCSQLBuilder builder, String proName) {
		FDCCustomerParams para = new FDCCustomerParams(this.getFilterUI()
				.getCustomerParams());
		if (para.isNotNull("isShowAll") && !para.getBoolean("isShowAll")) {

			Date beginDate = this.getBeginQueryDate();
			if (beginDate != null) {
				builder.appendSql(" and " + proName + ">= ? ");
				builder.addParam(FDCDateHelper.getSqlDate(beginDate));
			}
			Date endDate = this.getEndQueryDate();
			if (endDate != null) {
				builder.appendSql(" and " + proName + "< ? ");
				builder.addParam(FDCDateHelper.getSqlDate(endDate));
			}
		}
	}
	/**
	 * ���ڹ��˷�Χ֮�ڵ�ʱ��
	 * @param builder
	 * @param proName
	 */
	public void appendNotFilterSql(FDCSQLBuilder builder, String proName) {
		FDCCustomerParams para = new FDCCustomerParams(this.getFilterUI()
				.getCustomerParams());
		if (para.isNotNull("isShowAll") && !para.getBoolean("isShowAll")) {

			Date beginDate = this.getBeginQueryDate();
			if (beginDate != null) {
				builder.appendSql(" and (" + proName + "<= ? )");
				builder.addParam(FDCDateHelper.getSqlDate(beginDate));
			}
			Date endDate = this.getEndQueryDate();
			if (endDate != null) {
				builder.appendSql(" or " + proName + "> ? ))");
				builder.addParam(FDCDateHelper.getSqlDate(endDate));
			}
		}
	}

	private Date getEndQueryDate() {
		FilterInfo filter = this.mainQuery.getFilter();
		if (filter != null) {
			FilterItemCollection items = filter.getFilterItems();
			for (int i = 0; i < items.size(); i++) {
				FilterItemInfo item = items.get(i);
				String expression = item.getCompareExpression();
				if (expression.equals("<")) {
					Date date = (Date) item.getCompareValue();
					return date;
				}
			}
		} else {	
			FDCCustomerParams para = new FDCCustomerParams(this.getFilterUI().getCustomerParams());
			return this.getFilterUI().getBeginQueryDate(para);
		}
		return null;
	}

	private Date getBeginQueryDate() {
		FilterInfo filter = this.mainQuery.getFilter();
		if (filter != null) {
			FilterItemCollection items = filter.getFilterItems();
			for (int i = 0; i < items.size(); i++) {
				FilterItemInfo item = items.get(i);
				String expression = item.getCompareExpression();
				if (expression.equals(">=")) {
					Date date = (Date) item.getCompareValue();
					return date;
				}
			}
		} else {
			FDCCustomerParams para = new FDCCustomerParams(this.getFilterUI()
					.getCustomerParams());
			return this.getFilterUI().getEndQueryDate(para);
		}
		return null;
	}

	private void fillData() throws BOSException, SQLException {
		for (int i = 0; i < this.tblMain.getRowCount(); i++) {
			for (int j = 2; j < this.tblMain.getColumnCount(); j++) {
				this.tblMain.getCell(i, j).setValue(null);
				this.tblMain.getCell(i,"newCustomer").setValue("0");
				this.tblMain.getCell(i,"receiveAmount").setValue("0");
			}
		}
		Map dateMap=new HashMap();
		dateMap.put("beginDate", this.getBeginQueryDate());
		dateMap.put("endDate", this.getEndQueryDate());
		
		
		try {
			//�õ��洢���ݵļ���
			Map dataMap = (Map)SalemanStatRptFacadeFactory.getRemoteInstance().getSalemanStatData(dateMap);
			// ͳ��ÿ���û������Ŀͻ�����
			IRowSet cusSet = (IRowSet)dataMap.get("cusCountSet");
			while (cusSet.next()) {
				String fsalesmanid = cusSet.getString("fsalesmanid");
				String muSellProId = cusSet.getString("FHeadID");
				IRow row = (IRow) this.rowMap.get(muSellProId+fsalesmanid);
				if (row == null) {
					continue;
				}
				row.getCell("newCustomer").setValue(new Integer(cusSet.getInt("cusCount")));//�½��ͻ���
			}
			//�Ϲ���+�Ϲ��begin��
			//1.ȡ����Ч���Ϲ���
			Map cusSetMap =(Map)dataMap.get("signSet");
			cusSet=(IRowSet)cusSetMap.get("allDate");
			if(cusSet!=null){
				while (cusSet.next()) {
					String fsalesmanid = cusSet.getString("fsalesmanid");
					String muSellProId = cusSet.getString("FHeadID");
					IRow row = (IRow) this.rowMap.get(muSellProId+fsalesmanid);
					if (row == null) {
						continue;
					}
//					addDecimalValueToCell(row,"purchaseCount",new BigDecimal(cusSet.getInt("totalCount")),"add" );//�Ϲ���
//					addDecimalValueToCell(row,"purchaseAmount",cusSet.getBigDecimal("contractAmount"),"add" );//�Ϲ���

					row.getCell("purchaseCount").setValue(new Integer(cusSet.getInt("totalCount")));
					row.getCell("purchaseAmount").setValue(cusSet.getBigDecimal("contractAmount"));//�Ϲ���
				}
			}
//			//2.��--����ҵ����Ϲ���
//			cusSet=(IRowSet)cusSetMap.get("addDate");
//			if(cusSet!=null){
//				while (cusSet.next()) {
//					String fsalesmanid = cusSet.getString("fsalesmanid");
//					String muSellProId = cusSet.getString("FHeadID");
//					IRow row = (IRow) this.rowMap.get(muSellProId+fsalesmanid);
//					if (row == null) {
//						continue;
//					}
//					addDecimalValueToCell(row,"purchaseCount",new BigDecimal(cusSet.getInt("totalCount")),"add" );//�Ϲ���
//					addDecimalValueToCell(row,"purchaseAmount",cusSet.getBigDecimal("contractAmount") ,"add");//�Ϲ���
////					addDecimalValueToCell(row,"purchaseCount",new );
//				}
//			}
//			
//			//3.��--����ҵ����Ϲ���
//			cusSet=(IRowSet)cusSetMap.get("muitDate");
//			if(cusSet!=null){
//				while (cusSet.next()) {
//					String fsalesmanid = cusSet.getString("fsalesmanid");
//					String muSellProId = cusSet.getString("FHeadID");
//					IRow row = (IRow) this.rowMap.get(muSellProId+fsalesmanid);
//					if (row == null) {
//						continue;
//					}
//					addDecimalValueToCell(row,"purchaseCount",new BigDecimal(-cusSet.getInt("totalCount")),"muit" );//�Ϲ���
//					addDecimalValueToCell(row,"purchaseAmount",cusSet.getBigDecimal("contractAmount"), "muit");//�Ϲ���
////					addDecimalValueToCell(row,"purchaseCount",new );
//				}
//			}

			cusSet =(IRowSet)dataMap.get("constractSet");
			while (cusSet.next()) {
				String fsalesmanid = cusSet.getString("fsalesmanid");
				String muSellProId = cusSet.getString("FHeadID");
				IRow row = (IRow) this.rowMap.get(muSellProId+fsalesmanid);
				if (row == null) {
					continue;
				}
				row.getCell("contractCount").setValue(
						new Integer(cusSet.getInt("totalCount")));//��ͬ��
				row.getCell("contractAmount").setValue(
						cusSet.getBigDecimal("contractAmount"));//��ͬ��
			}

			//�տ��
			Map termReceiveMap =(Map)dataMap.get("revSet");
			Iterator it=termReceiveMap.keySet().iterator();
			while(it.hasNext()){
				String ormanId=(String)it.next();
				IRow row = (IRow) this.rowMap.get(ormanId);
				if (row == null) {
					continue;
				}
				BigDecimal sumRevAmount = (BigDecimal)termReceiveMap.get(ormanId);//�տ��
				row.getCell("receiveAmount").setValue(sumRevAmount);
			}
			
		} catch (EASBizException e) {
			e.printStackTrace();
		}
		
		setUnionData();
	}
	private void addDecimalValueToCell(IRow row,String cellName,BigDecimal addValue,String tag) {
		if(addValue==null) return; 
		BigDecimal cellValue = new BigDecimal(0); 
		Object cellObject = row.getCell(cellName).getValue();
		if(cellObject instanceof Integer)
			cellValue = new BigDecimal(((Integer)cellObject).intValue());
		else if(cellObject instanceof BigDecimal)
			cellValue = (BigDecimal)cellObject;
		if("muit".equals(tag)){
			row.getCell(cellName).setValue(cellValue.multiply(addValue));
		}else{
			row.getCell(cellName).setValue(cellValue.add(addValue));
		}
	}
	protected boolean initDefaultFilter() {
		return false;
	}
	protected boolean isIgnoreCUFilter()
	{
		return true;
	}
	
	protected void checkTableParsed() {
		tblMain.checkParsed();
	}

	protected void tblMain_doRequestRowSet(RequestRowSetEvent e) {
	}

	private void initTable() {
		this.tblMain.getDataRequestManager().setDataRequestMode(
				KDTDataRequestManager.REAL_MODE);
		tblMain.getStyleAttributes().setLocked(true);
		tblMain.getSelectManager().setSelectMode(KDTSelectManager.ROW_SELECT);
		tblMain.getColumn("newCustomer").getStyleAttributes()
				.setHorizontalAlign(HorizontalAlignment.RIGHT);
		tblMain.getColumn("newCustomer").getStyleAttributes().setNumberFormat(
				FDCHelper.getNumberFtm(0));
		tblMain.getColumn("purchaseAmount").getStyleAttributes()
				.setHorizontalAlign(HorizontalAlignment.RIGHT);
		tblMain.getColumn("purchaseAmount").getStyleAttributes()
				.setNumberFormat(FDCHelper.getNumberFtm(2));
		tblMain.getColumn("purchaseCount").getStyleAttributes()
				.setHorizontalAlign(HorizontalAlignment.RIGHT);
		tblMain.getColumn("purchaseCount").getStyleAttributes()
				.setNumberFormat(FDCHelper.getNumberFtm(0));
		tblMain.getColumn("contractAmount").getStyleAttributes()
				.setHorizontalAlign(HorizontalAlignment.RIGHT);
		tblMain.getColumn("contractAmount").getStyleAttributes()
				.setNumberFormat(FDCHelper.getNumberFtm(2));
		tblMain.getColumn("contractCount").getStyleAttributes()
				.setHorizontalAlign(HorizontalAlignment.RIGHT);
		tblMain.getColumn("contractCount").getStyleAttributes()
				.setNumberFormat(FDCHelper.getNumberFtm(0));
		tblMain.getColumn("receiveAmount").getStyleAttributes()
				.setHorizontalAlign(HorizontalAlignment.RIGHT);
		tblMain.getColumn("receiveAmount").getStyleAttributes()
				.setNumberFormat(FDCHelper.getNumberFtm(2));
//		tblMain.getColumn("earnestAmount").getStyleAttributes()
//		.setHorizontalAlign(HorizontalAlignment.RIGHT);
//tblMain.getColumn("earnestAmount").getStyleAttributes()
//		.setNumberFormat(FDCHelper.getNumberFtm(2));
		
		for (int i = 1; i < 7; i++) {
			this.tblMain.getHeadMergeManager().mergeBlock(0, i, 1, i);
		}
	}

	public void loadFields() {
		super.loadFields();
	}

	/**
	 * @deprecated
	 * */
	private void fillSalesman() throws BOSException, EASBizException,
			SQLException {
		this.tblMain.removeRows();
		PersonCollection persons = PersonFactory.getRemoteInstance()
				.getPersonCollection();
		Set idSet = new HashSet();
		for (int i = 0; i < persons.size(); i++) {
			idSet.add(persons.get(i).getId().toString());
		}
		EntityViewInfo view = new EntityViewInfo();
		view.getSelector().add("*");
		view.getSelector().add("person.*");
		FilterInfo filter = new FilterInfo();

		// ----����ְλ����------
		UserInfo currentUserInfo = SysContext.getSysContext()
				.getCurrentUserInfo();
		Set personIds = FDCCustomerHelper.getChildPersonIdsOfCurrentUser();
		if (personIds == null) {
			filter.getFilterItems()
					.add(
							new FilterItemInfo("id", currentUserInfo.getId()
									.toString()));
		} else {
			filter.getFilterItems().add(
					new FilterItemInfo("person.id", personIds,
							CompareType.INCLUDE));
		}
		
		// ------------------

		view.setFilter(filter);
		//filter.getFilterItems().add(new FilterItemInfo("type", UserType.OTHER));
		//filter.getFilterItems().add(new FilterItemInfo("agentUser",Boolean.FALSE));
		//filter.setMaskString("(#0 or #1) and #2");
		//filter.setMaskString("#0 and #1");
		UserCollection users = UserFactory.getRemoteInstance()
				.getUserCollection(view);
		
		//this.tblMain.checkParsed();
		for (int i = 0; i < users.size(); i++) {
			UserInfo user = users.get(i);
			IRow row = this.tblMain.addRow();
			// if (user.getPerson() != null) {
			// row.getCell("salesmans").setValue(user.getPerson().getName());
			// }
			row.getCell("user").setValue(user.getNumber());
			row.getCell("salesman").setValue(user.getName());
			row.setUserObject(user);
			this.rowMap.put(user.getId().toString(), row);
		}

	}

	public void setUnionData() {
		for (int i = 0; i < tblMain.getRowCount(); i++) {
			IRow row = tblMain.getRow(i);
			if (row.getUserObject() == null) {
				// ���û�����
				int level = row.getTreeLevel();
				List rowList = new ArrayList();
				for (int j = i + 1; j < tblMain.getRowCount(); j++) {
					IRow rowAfter = tblMain.getRow(j);
					if (rowAfter.getTreeLevel() <= level) {
						break;
					}
					if (rowAfter.getUserObject() != null) {
						rowList.add(rowAfter);
					}
				}
				for (int j = 1; j < this.tblMain.getColumnCount(); j++) {
					BigDecimal aimCost = FMConstants.ZERO;
					for (int rowIndex = 0; rowIndex < rowList.size(); rowIndex++) {
						IRow rowAdd = (IRow) rowList.get(rowIndex);
						Object value = rowAdd.getCell(j).getValue();
						if (value != null) {
							if (value instanceof BigDecimal) {
								aimCost = aimCost.add((BigDecimal) value);
							} else if (value instanceof Integer) {
								aimCost = aimCost.add(new BigDecimal(
										((Integer) value).toString()));
							}
						}
					}
					row.getCell(j).setValue(aimCost);
				}
			}
		}
	}

	protected void setActionState() {

	}

	protected void tblMain_tableClicked(KDTMouseEvent e) throws Exception {
		// super.tblMain_tableClicked(e);
		// if (e.getButton() == 1 && e.getClickCount() == 2) {
		// int rowIndex = this.tblMain.getSelectManager().getActiveRowIndex();
		// IRow row = this.tblMain.getRow(rowIndex);
		// if (row != null) {
		// BuildingInfo building = (BuildingInfo) row.getUserObject();
		// if (building != null) {
		// SellStatRoomRptUI.showUI(this, building.getId().toString());
		// }
		// }
		// }
	}

	public void actionRefresh_actionPerformed(ActionEvent e) throws Exception {
		super.actionRefresh_actionPerformed(e);
	}

	protected String getEditUIName() {
		return null;
	}

	protected ICoreBase getBizInterface() throws Exception {
		return null;
	}

	public void actionExport_actionPerformed(ActionEvent e) throws Exception {
		handlePermissionForItemAction(actionExport);
		super.actionExport_actionPerformed(e);
	}

	public void actionExportData_actionPerformed(ActionEvent e)
			throws Exception {
		handlePermissionForItemAction(actionExportData);
		super.actionExportData_actionPerformed(e);
	}

	public void actionExportSelected_actionPerformed(ActionEvent e)
			throws Exception {
		handlePermissionForItemAction(actionExportSelected);
		super.actionExportSelected_actionPerformed(e);

	}

	public void actionToExcel_actionPerformed(ActionEvent e) throws Exception {
		handlePermissionForItemAction(actionToExcel);
		super.actionToExcel_actionPerformed(e);
	}

	public void actionPublishReport_actionPerformed(ActionEvent e)
			throws Exception {
		handlePermissionForItemAction(actionPublishReport);
		super.actionPublishReport_actionPerformed(e);
	}
}