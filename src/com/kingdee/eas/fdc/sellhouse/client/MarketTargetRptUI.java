/**
 * output package name
 */
package com.kingdee.eas.fdc.sellhouse.client;

import java.awt.Color;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeModel;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.table.ICell;
import com.kingdee.bos.ctrl.kdf.table.IColumn;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTDataRequestManager;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectManager;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent;
import com.kingdee.bos.ctrl.kdf.util.style.Styles.HorizontalAlignment;
import com.kingdee.bos.dao.query.SQLExecutorFactory;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.eas.base.commonquery.client.CommonQueryDialog;
import com.kingdee.eas.base.permission.util.DateUtils;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCCustomerParams;
import com.kingdee.eas.fdc.basedata.FDCDateHelper;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.basedata.MoneySysTypeEnum;
import com.kingdee.eas.fdc.sellhouse.BuildingInfo;
import com.kingdee.eas.fdc.sellhouse.PurchaseChangeCollection;
import com.kingdee.eas.fdc.sellhouse.PurchaseChangeFactory;
import com.kingdee.eas.fdc.sellhouse.PurchaseChangeInfo;
import com.kingdee.eas.fdc.sellhouse.QuitRoomCollection;
import com.kingdee.eas.fdc.sellhouse.QuitRoomFactory;
import com.kingdee.eas.fdc.sellhouse.QuitRoomInfo;
import com.kingdee.eas.fdc.sellhouse.ReceptionTypeCollection;
import com.kingdee.eas.fdc.sellhouse.ReceptionTypeFactory;
import com.kingdee.eas.fdc.sellhouse.ReceptionTypeInfo;
import com.kingdee.eas.fdc.sellhouse.RoomDisplaySetting;
import com.kingdee.eas.fdc.sellhouse.SellProjectInfo;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.jdbc.rowset.IRowSet;
import com.kingdee.util.DateTimeUtils;

/**
 * output class name
 */
public class MarketTargetRptUI extends AbstractMarketTargetRptUI
{
	private static final Logger logger = CoreUIObject.getLogger(MarketTargetRptUI.class);

	Map sellProCountMap = null;//������Ŀid  ��Ӧ   Row

	private MarketTargetRptFilterUI filterUI = null;

	private CommonQueryDialog commonQueryDialog = null;
	
	RoomDisplaySetting setting = new RoomDisplaySetting();
	boolean isAudit =false;
	Map receptTypeMap = null;   //�Ӵ���ʽ

	Map receptTypeColumnNameMap = null;   //�Ӵ���ʽ ��Ӧ ����
	Map receptTypeColumnNameMap2 = null;   //����Ӵ���ʽ ��Ӧ ����
	Map receptTypeColumnNameMap3 = null;   //����Ӵ���ʽ�ɽ����� ��Ӧ ����

	Date beginDate = null;
	Date endDate = null;

	public MarketTargetRptUI() throws Exception
	{	
		super();
		isAudit = setting.getBaseRoomSetting().isAuditDate();
	}

	public void onLoad() throws Exception {		
		super.onLoad();
		this.actionAddNew.setVisible(false);
		this.actionEdit.setVisible(false);		
		this.actionRemove.setVisible(false);
		this.menuEdit.setVisible(false);
		this.btnView.setVisible(false);
		this.actionView.setVisible(false);
		this.tblMain.getStyleAttributes().setLocked(true);
		this.tblMain.getSelectManager().setSelectMode(KDTSelectManager.ROW_SELECT);
	}

	public void storeFields()
	{
		super.storeFields();
	}

	protected boolean initDefaultFilter() {
		return true;
	}
	public void handUIException(Throwable exc) {
		if(exc instanceof BOSException&&exc.getMessage().startsWith("Can't found propertyUnit:")){
			logger.error(exc);
		}
		else
			super.handUIException(exc);
	}
	private MarketTargetRptFilterUI getFilterUI() {
		if (this.filterUI == null) {
			try {
				this.filterUI = new MarketTargetRptFilterUI(this, this.actionOnLoad);
				this.filterUI.onLoad();
			} catch (Exception e) {
				this.handleException(e);
				abort(e);
			}
		}
		return this.filterUI;
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
		//commonQueryDialog.setUiObject(null);
		return commonQueryDialog;
	}

	/**
	 * ���ݽӴ���ʽ������ͷ����  recepTypeColumnMap   sellProjectName trackCountSum1 newCustomerCountSum1
	 * @throws BOSException 
	 */
	private void initTableHead() throws BOSException {
		getReceptTypeMap();
		IRow headRow0 = this.tblMain.addHeadRow();
		IRow headRow1 = this.tblMain.addHeadRow();	
		IColumn columnSellProjectId = this.tblMain.addColumn();
		columnSellProjectId.setKey("id");
		headRow0.getCell("id").setValue("ID");
		headRow1.getCell("id").setValue("ID");
		this.tblMain.getHeadMergeManager().mergeBlock(0,columnSellProjectId.getColumnIndex(), 1, columnSellProjectId.getColumnIndex());
		columnSellProjectId.getStyleAttributes().setHided(true);
		IColumn columnSellProject = this.tblMain.addColumn();
		columnSellProject.setKey("sellProjectName");
		headRow0.getCell("sellProjectName").setValue("����");
		headRow1.getCell("sellProjectName").setValue("����");
		this.tblMain.getHeadMergeManager().mergeBlock(0,columnSellProject.getColumnIndex(), 1, columnSellProject.getColumnIndex());
		if(receptTypeMap!=null)  {      //������ͷ	
			Set keySet = receptTypeMap.keySet();
			Iterator iter = keySet.iterator();
			int i = 1;
			while(iter.hasNext()) {		
				String key = (String)iter.next();
				receptTypeColumnNameMap.put(key,"trackCountSum"+i);
				IColumn columnTrack = this.tblMain.addColumn();
				columnTrack.setKey("trackCountSum"+i);			
				headRow0.getCell("trackCountSum"+i).setValue("�ۼƽӴ���ʽ");
				headRow1.getCell("trackCountSum"+i).setValue(receptTypeMap.get(key));
				this.tblMain.getColumn("trackCountSum"+i).getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
				i++;
			}
			this.tblMain.getHeadMergeManager().mergeBlock(0,this.tblMain.getColumnIndex("trackCountSum1"),0,this.tblMain.getColumnIndex("trackCountSum"+(i-1)));
		}
		if(receptTypeMap!=null)  {      //��������Ӵ���ʽ��ͷ	
			Set keySet = receptTypeMap.keySet();
			Iterator iter = keySet.iterator();
			int i = 1;
			while(iter.hasNext()) {		
				String key = (String)iter.next();
				receptTypeColumnNameMap2.put(key,"tremtrackCountSum"+i);

				IColumn columnTrack = this.tblMain.addColumn();
				columnTrack.setKey("tremtrackCountSum"+i);
				headRow1.getCell("tremtrackCountSum"+i).setValue(receptTypeMap.get(key));
				this.tblMain.getColumn("tremtrackCountSum"+i).getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);			
				i++;
			}
			this.tblMain.getHeadMergeManager().mergeBlock(0,this.tblMain.getColumnIndex("tremtrackCountSum1"),0,this.tblMain.getColumnIndex("tremtrackCountSum"+(i-1)));
		}
		IColumn columnSumContractCount = this.tblMain.addColumn();
		columnSumContractCount.setKey("sumContractCount");
		headRow0.getCell("sumContractCount").setValue("�ۼ�Ԥ����ͬ��");
		headRow1.getCell("sumContractCount").setValue("�ۼ�Ԥ����ͬ��");
		this.tblMain.getColumn("sumContractCount").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
		this.tblMain.getHeadMergeManager().mergeBlock(0,columnSumContractCount.getColumnIndex(), 1, columnSumContractCount.getColumnIndex());

		IColumn columnSumContractAmount = this.tblMain.addColumn();
		columnSumContractAmount.setKey("sumContractAmount");
		headRow0.getCell("sumContractAmount").setValue("�ۼ�Ԥ����ͬ�ܼ�");
		headRow1.getCell("sumContractAmount").setValue("�ۼ�Ԥ����ͬ�ܼ�");
		this.tblMain.getColumn("sumContractAmount").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
		this.tblMain.getHeadMergeManager().mergeBlock(0,columnSumContractAmount.getColumnIndex(), 1, columnSumContractAmount.getColumnIndex());

		IColumn columnContractCount = this.tblMain.addColumn();
		columnContractCount.setKey("contractCount");
		headRow1.getCell("contractCount").setValue("��ͬ��");
		this.tblMain.getColumn("contractCount").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);

		IColumn columnContractAmount = this.tblMain.addColumn();
		columnContractAmount.setKey("contractAmount");
		headRow1.getCell("contractAmount").setValue("��ͬ�ܼ�");
		this.tblMain.getColumn("contractAmount").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
		tblMain.getColumn("contractAmount").getStyleAttributes().setNumberFormat(
				FDCHelper.getNumberFtm(0));

		IColumn columnContractArea = this.tblMain.addColumn();
		columnContractArea.setKey("contractArea");
		headRow1.getCell("contractArea").setValue("��ͬ���");
		this.tblMain.getColumn("contractArea").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
		this.tblMain.getHeadMergeManager().mergeBlock(0,columnContractCount.getColumnIndex(), 0, columnContractArea.getColumnIndex());
		if(receptTypeMap!=null)  {      //��������Ӵ���ʽ��ͷ	
			Set keySet = receptTypeMap.keySet();
			Iterator iter = keySet.iterator();
			int i = 1;
			while(iter.hasNext()) {		
				String key = (String)iter.next();
				receptTypeColumnNameMap3.put(key,"tremTrackScale"+i);

				IColumn columnTrack = this.tblMain.addColumn();
				columnTrack.setKey("tremTrackScale"+i);
				headRow1.getCell("tremTrackScale"+i).setValue(receptTypeMap.get(key)+"%");
				this.tblMain.getColumn("tremTrackScale"+i).getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);		
				i++;
			}
			this.tblMain.getHeadMergeManager().mergeBlock(0,this.tblMain.getColumnIndex("tremTrackScale1"),0,this.tblMain.getColumnIndex("tremTrackScale"+(i-1)));
		}


	}

	protected void execQuery() {
		this.tblMain.getDataRequestManager().setDataRequestMode(KDTDataRequestManager.REAL_MODE);

		if(receptTypeMap==null) {
			this.tblMain.removeHeadRows();
			try {
				initTableHead();
			} catch (BOSException e) {
				this.handleException(e);
			}
		}	

		IRow headRow0 = this.tblMain.getHeadRow(0);
		beginDate = this.getBeginQueryDate();
		endDate = this.getEndQueryDate();
		DateFormat FORMAT_DAY = new SimpleDateFormat("yyyy-MM-dd");
		FDCCustomerParams para = new FDCCustomerParams(this.getFilterUI().getCustomerParams());
		int a = (receptTypeColumnNameMap2.keySet().size())+2;
		if (para.isNotNull("isShowAll") && para.getBoolean("isShowAll"))
		{	
			headRow0.getCell(a).setValue("�Ӵ���ʽ(ȫ����ʾ)");
			headRow0.getCell(2*a).setValue("�ϼ�(ȫ����ʾ)");
			headRow0.getCell(2*a+3).setValue("�Ӵ���ʽ�ɽ�����(ȫ����ʾ)");
		}else		{
			if(beginDate==null) beginDate = new Date(System.currentTimeMillis());
			if(endDate==null) endDate = new Date(System.currentTimeMillis());
			headRow0.getCell(a).setValue((FORMAT_DAY.format(beginDate)+"--"+FORMAT_DAY.format(endDate)+"�Ӵ���ʽ"));
			headRow0.getCell(2*a).setValue((FORMAT_DAY.format(beginDate)+"--"+FORMAT_DAY.format(endDate)+"�ϼ�"));
			headRow0.getCell(2*a+3).setValue(FORMAT_DAY.format(beginDate)+"--"+FORMAT_DAY.format(endDate)+"�Ӵ���ʽ�ɽ�����");
		}

		if(tblMain.getRowCount() == 0){
			try {
				fillProjectAndBuilding();
			} catch (EASBizException e) {
				handleException(e);
			} catch (BOSException e) {
				handleException(e);
			} catch (SQLException e) {
				handleException(e);
			}
		}

		if(tblMain.getRowCount() == 0){
			return;
		}

		if(sellProCountMap==null || sellProCountMap.size() == 0)  return;

		//		������������
		//clearAllDate();

		//�����ۼƽӴ���ʽ
		String sellProIds = "";
		Set idSet = sellProCountMap.keySet();
		Iterator iter = idSet.iterator();
		while(iter.hasNext()) {
			sellProIds += "'" + iter.next() + "',";			
		}
		if(!sellProIds.equals(""))  sellProIds = sellProIds.substring(0,sellProIds.length()-1);
		if(!sellProIds.equals("")) 
		{
			FDCSQLBuilder builder = new FDCSQLBuilder(); 
			builder.appendSql("select FSellProjectId,FReceptionTypeID,count(*) as num from T_SHE_TrackRecord ");
			builder.appendSql(" where FSellProjectId in ("+sellProIds+") ");
			builder.appendSql(" group by FSellProjectId,FReceptionTypeID ");
			try {
				IRowSet sellSet = null;
				sellSet = SQLExecutorFactory.getRemoteInstance(builder.getSql())
				.executeSQL();
				while (sellSet.next()) {
					String projectId = sellSet.getString("FSellProjectId");
					String recepTypeId = sellSet.getString("FReceptionTypeID");
					int trackNum = sellSet.getInt("num");					
					IRow row = (IRow)sellProCountMap.get(projectId);
					row.getCell((String)receptTypeColumnNameMap.get(recepTypeId)).setValue(String.valueOf(trackNum));
				}	
			} catch (Exception e) {
				this.handleException(e);
			} 
		}	
		try {
			//�����ۼƺ�ͬ�����ۼƺ�ͬ�ܼ�
			FDCSQLBuilder builder3 = new FDCSQLBuilder();
			builder3.appendSql("select sellProject.Fid as sellProjectID,count(*) as contractNum,sum(purchase.FContractTotalAmount) contractAmount from T_SHE_room as room");
			builder3.appendSql(" LEFT OUTER JOIN  T_SHE_Building AS building ON room.FBuildingID = building.FID");
			builder3.appendSql(" LEFT OUTER JOIN  T_SHE_SellProject AS sellProject ON building.FSellProjectID = sellProject.FID");
			builder3.appendSql(" LEFT OUTER JOIN  T_SHE_Purchase AS purchase ON room.FLastPurchaseID = purchase.FID");
			builder3.appendSql(" where (fSellState = 'PrePurchase') and sellProject.Fid  in ("+sellProIds+")");
			builder3.appendSql(" AND room.FIsForSHE=1 group by sellProject.Fid");
			IRowSet set = null;
			set = SQLExecutorFactory.getRemoteInstance(builder3.getSql())
			.executeSQL();
			while(set.next())
			{
				int contractNum = set.getInt("contractNum");
				BigDecimal contractAmount = set.getBigDecimal("contractAmount");
				String projectId = set.getString("sellProjectID");
				IRow row = (IRow)sellProCountMap.get(projectId);
				row.getCell("sumContractCount").setValue(new Integer(contractNum));
				row.getCell("sumContractAmount").setValue(contractAmount);
				row.getCell("sumContractAmount").getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(2));
			}
		} catch (Exception e1) {
			this.handleException(e1);
		}

		//�����ͬ��������ͬ�ܼۣ���ͬ���
		boolean isPreIntoSale = this.isPreIntoSale();
		Map map = new HashMap();
		FDCSQLBuilder builder3 = new FDCSQLBuilder();
		builder3.appendSql("select sellProject.Fid as sellProjectID,count(*) as contractNum,sum(purchase.FContractTotalAmount) contractAmount,sum(room.FBuildingArea) as buildingArea from T_SHE_room as room");
		builder3.appendSql(" LEFT OUTER JOIN  T_SHE_Building AS building ON room.FBuildingID = building.FID");
		builder3.appendSql(" LEFT OUTER JOIN  T_SHE_SellProject AS sellProject ON building.FSellProjectID = sellProject.FID ");
		builder3.appendSql(" LEFT OUTER JOIN  T_SHE_Purchase AS purchase ON room.FLastPurchaseID = purchase.FID");
		builder3.appendSql(" where room.FIsForSHE=1 AND sellProject.Fid in ("+sellProIds+")");
		if(isPreIntoSale)
		{
			builder3.appendSql(" and (fSellState = 'PrePurchase' or fSellState = 'Purchase' or fSellState = 'Sign') ");
			this.appendFilterSql(builder3, "room.FToSaleDate");
		}else
		{
			builder3.appendSql(" and (fSellState = 'Purchase' or fSellState = 'Sign') ");
			this.appendFilterSql(builder3, "room.FToPurchaseDate");
		}
		//builder3.appendSql(" and purchase.fid is not null ");
		builder3.appendSql(" group by sellProject.Fid");
		try {
			IRowSet termContractSet = builder3.executeQuery();
			while (termContractSet.next()) {
				int contractNum = termContractSet.getInt("contractNum");
				BigDecimal contractAmount = termContractSet.getBigDecimal("contractAmount");
				BigDecimal buildingArea = termContractSet.getBigDecimal("buildingArea");
				String projectId = termContractSet.getString("sellProjectID");
				IRow row = (IRow)sellProCountMap.get(projectId);
				row.getCell("contractCount").setValue(new Integer(contractNum));
				row.getCell("contractAmount").setValue(contractAmount);
				row.getCell("contractArea").setValue(buildingArea);	
				row.getCell("contractAmount").getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(2));
				row.getCell("contractArea").getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(2));
				map.put(projectId,new Integer(contractNum));
			}	
		} catch (Exception e) {
			this.handleException(e);
		}


		try {
			handleSpecialBiz(isPreIntoSale);
		} catch (BOSException e1) {
			this.handleException(e1);
		} catch (SQLException e1) {
			this.handleException(e1);
		}

		FDCSQLBuilder builder2 = new FDCSQLBuilder();    //��ѯ������Ŀ��,���Ӵ���ʽ�ĸ�������
		builder2.appendSql("select FSellProjectId,FReceptionTypeID,count(*) as num from T_SHE_TrackRecord as trackRecord ");
		builder2.appendSql(" where FSellProjectId in ("+sellProIds+") ");
		this.appendFilterSql(builder2, "FEventDate");
		builder2.appendSql(" group by FSellProjectId,FReceptionTypeID ");
		try {
			IRowSet termSellSet = builder2.executeQuery();
			while (termSellSet.next()) {
				String projectId = termSellSet.getString("FSellProjectId");
				String recepTypeId = termSellSet.getString("FReceptionTypeID");
				int trackNum = termSellSet.getInt("num");					
				IRow row = (IRow)sellProCountMap.get(projectId);
				row.getCell((String)receptTypeColumnNameMap2.get(recepTypeId)).setValue(String.valueOf(trackNum));
				Integer contractNum = (Integer)map.get(projectId);
				float num =0;
				if(contractNum!=null && contractNum.intValue()>0)
				{
					num =contractNum.floatValue()/(float)trackNum *100;
				}
				Float num2 = num==0?null:new Float(num);
				//����Ӵ���ʽ�ɽ�����
				row.getCell((String)receptTypeColumnNameMap3.get(recepTypeId)).setValue(num2);
				row.getCell((String)receptTypeColumnNameMap3.get(recepTypeId)).getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(2));
			}	
		} catch (Exception e) {
			this.handleException(e);
		}

		setUnionData();
	}

	private void fillProjectAndBuilding() throws EASBizException, BOSException, SQLException{
		tblMain.removeRows(false);
		TreeModel sellProTree = null;
		try {
			//sellProTree = FDCTreeHelper.getSellProjectTree(this.actionOnLoad,MoneySysTypeEnum.SalehouseSys);
			sellProTree = SHEHelper.getSellProjectTree(this.actionOnLoad,MoneySysTypeEnum.SalehouseSys);
		} catch (Exception e) {
			this.handleException(e);
		}
		DefaultMutableTreeNode sellProRoot = (DefaultMutableTreeNode) sellProTree.getRoot();
		sellProCountMap = new HashMap();
		this.tblMain.getTreeColumn().setDepth(sellProRoot.getDepth() + 1);
		fillNode(this.tblMain, sellProRoot);

	}

	private void fillNode(KDTable table, DefaultMutableTreeNode node)	throws BOSException, SQLException, EASBizException {
		IRow row = table.addRow();
		row.setTreeLevel(node.getLevel());
		String space = "";
		for (int i = 0; i < node.getLevel(); i++) {
			space += " ";
		}
		if (node.getUserObject() instanceof SellProjectInfo) {
			SellProjectInfo sellPro = (SellProjectInfo) node.getUserObject();
			row.getCell("sellProjectName").setValue(space + sellPro.getName());
			row.setUserObject(node.getUserObject());
			this.sellProCountMap.put(sellPro.getId().toString(), row);			
		} else {
			row.getStyleAttributes().setBackground(new Color(0xF0EDD9));
			row.getCell("sellProjectName").setValue(space + node);
		}
		if (!node.isLeaf()) {
			for (int i = 0; i < node.getChildCount(); i++) {
				this.fillNode(table, (DefaultMutableTreeNode) node.getChildAt(i));
			}
		}
	}

	//��ȡ�Ӵ���ʽ��id��name��ӳ���ϵ
	private void getReceptTypeMap() throws BOSException {
		if(receptTypeMap!=null) return;
		ReceptionTypeCollection recepTypes  = ReceptionTypeFactory.getRemoteInstance().getReceptionTypeCollection();
		if(recepTypes!=null) {
			receptTypeMap = new HashMap();
			receptTypeColumnNameMap = new HashMap();
			receptTypeColumnNameMap2= new HashMap();
			receptTypeColumnNameMap3 = new HashMap();
			for(int i=0;i<recepTypes.size();i++) {
				ReceptionTypeInfo receptType = recepTypes.get(i);
				receptTypeMap.put(receptType.getId().toString(),receptType.getName());
			}
		}
	}

	private void clearAllDate() {
		int colCount = this.tblMain.getColumnCount();
		int track1Num = this.tblMain.getColumnIndex("trackCountSum1");
		for(int i=0;i<this.tblMain.getRowCount();i++)  {
			IRow row = this.tblMain.getRow(i);
			for(int j=track1Num;j<colCount;j++) {
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
				if(row!=null)  {
					//�ۼ�Ԥ����ͬ��
					int sumContractCount = 0;
					//�ۼ�Ԥ����ͬ�ܼ�
					BigDecimal sumContractAmount= FDCHelper.ZERO;
					//�����ͬ��
					int contractCount = 0;
					//�����ͬ�ܼ�
					BigDecimal contractAmount= FDCHelper.ZERO;
					//�����ͬ���
					BigDecimal contractArea= FDCHelper.ZERO;
					for(int j=0;j<rowList.size();j++)
					{
						IRow addRow = (IRow)rowList.get(j);
						Integer tempSumContractCount = (Integer)addRow.getCell("sumContractCount").getValue();
						if(tempSumContractCount!=null)
						{
							sumContractCount+=tempSumContractCount.intValue();//�ۼƺ�ͬ������
						}
						BigDecimal tempSumContractAmount = (BigDecimal)addRow.getCell("sumContractAmount").getValue();
						if(tempSumContractAmount!=null)
						{
							sumContractAmount = sumContractAmount.add(tempSumContractAmount);//�ۼƺ�ͬ�ܼۻ���
						}
						Integer tempContractCount = (Integer)addRow.getCell("contractCount").getValue();
						if(tempContractCount!=null)
						{
							contractCount+=tempContractCount.intValue();//�����ͬ������
						}
						BigDecimal tempContractAmount = (BigDecimal)addRow.getCell("contractAmount").getValue();
						if(tempContractAmount!=null)
						{
							contractAmount = contractAmount.add(tempContractAmount);//�����ͬ�ܼۻ���
						}
						BigDecimal tempContractArea = (BigDecimal)addRow.getCell("contractArea").getValue();
						if(tempContractArea!=null)
						{
							contractArea = contractArea.add(tempContractArea);//�����ͬ�������
						}

					}
					if(sumContractCount!=0)
					{
						row.getCell("sumContractCount").setValue(new Integer(sumContractCount));

					}if(sumContractAmount!=null)
					{
						row.getCell("sumContractAmount").setValue(sumContractAmount);
						row.getCell("sumContractAmount").getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(2));
					}
					if(contractCount!=0)
					{
						row.getCell("contractCount").setValue(new Integer(contractCount));

					}if(contractAmount!=null)
					{
						row.getCell("contractAmount").setValue(contractAmount);
						row.getCell("contractAmount").getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(2));
					}if(contractArea!=null)
					{
						row.getCell("contractArea").setValue(contractArea);
						row.getCell("contractArea").getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(2));
					}
					//					�ۼƽӴ���ʽ��̬�кϼ�
					this.setSumTrackNum(receptTypeColumnNameMap,row,rowList);
					//					����Ӵ���ʽ��̬�кϼ�
					this.setSumTrackNum(receptTypeColumnNameMap2,row,rowList);
					//					����Ӵ���ʽ�ɽ�����
					this.setSumTrackScale(receptTypeColumnNameMap3,receptTypeColumnNameMap2,row,rowList,contractCount);

				}		
			}
		}
	}

	private void setSumTrackNum(Map map,IRow row,List rowList)
	{
		Set tempSet2 = map.keySet();
		Iterator tempIter2 = tempSet2.iterator();
		while(tempIter2.hasNext()) {
			String trackColName = (String)map.get(tempIter2.next());
			int colunIndex = row.getCell(trackColName).getColumnIndex();
			int sumTrackNum = 0;
			for(int ii=0;ii<rowList.size();ii++) {
				IRow addRow = (IRow)rowList.get(ii);
				String cellTrackValue = (String)addRow.getCell(colunIndex).getValue();
				if(cellTrackValue!=null) 
					sumTrackNum += Integer.parseInt(cellTrackValue);
			}
			if(sumTrackNum!=0)  
				row.getCell(colunIndex).setValue(String.valueOf(sumTrackNum));
		}
	}

	private void setSumTrackScale(Map map,Map map2,IRow row ,List rowList,int contractCount)
	{
		Set tempSet3 = map.keySet();
		Iterator tempIter3 = tempSet3.iterator();
		while(tempIter3.hasNext()) {
			String receptionTypeId = (String) tempIter3.next();
			String trackColName = (String)map.get(receptionTypeId);																	
			int colunIndex = row.getCell(trackColName).getColumnIndex();//��ýӴ���ʽ�ɽ�������λ��

			//receptTypeColumnNameMap2��receptTypeColumnNameMap3�洢ʱkeyֵ��ͬ���ҳ���ͬ��value
			String tera = (String) map2.get(receptionTypeId);

			int sumTrackNum = 0;
			for(int ii=0;ii<rowList.size();ii++) {
				IRow addRow = (IRow)rowList.get(ii);
				//�ҳ�����Ӵ���ʽ�ۼ�
				String value = (String)addRow.getCell(tera).getValue();
				if(value!=null) 
					sumTrackNum += Integer.parseInt(value);
			}
			float num =0;
			if(sumTrackNum!=0)
				num = contractCount/(float)sumTrackNum *100;
			row.getCell(colunIndex).setValue(new Float(num));
			row.getCell(colunIndex).getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(2));
		}
	}

	private Date getBeginQueryDate() {
		FDCCustomerParams para = new FDCCustomerParams(this.getFilterUI().getCustomerParams());
		return this.getFilterUI().getBeginQueryDate(para);
	}

	private Date getEndQueryDate() {
		FDCCustomerParams para = new FDCCustomerParams(this.getFilterUI().getCustomerParams());
		return this.getFilterUI().getEndQueryDate(para);
	}	

	private boolean getQueryIsShowAll() {
		FDCCustomerParams para = new FDCCustomerParams(this.getFilterUI().getCustomerParams());
		if (para.isNotNull("isShowAll") && para.getBoolean("isShowAll")) 
			return true;

		return false;		
	}

	public void appendFilterSql(FDCSQLBuilder builder, String proName) {
		FDCCustomerParams para = new FDCCustomerParams(this.getFilterUI()
				.getCustomerParams());
		if (para.isNotNull("isShowAll") && !para.getBoolean("isShowAll")) {

			Date beginDate = this.getBeginQueryDate();
			if (beginDate != null) {
				builder.appendSql(" and " + proName + " >= ? ");
				builder.addParam(FDCDateHelper.getSqlDate(beginDate));
			}
			Date endDate = this.getEndQueryDate();
			if (endDate != null) {
				builder.appendSql(" and " + proName + " < ? ");
				builder.addParam(FDCDateHelper.getNextDay(endDate));
			}
		}

	}

	/**
	 * �ڴ�֮ǰ�Ѿ�ͳ���˸��������µ���������.���ﴦ������ҵ��,��Ҫ�Ǵ����˷��ͱ��
	 * ����:����Room 1��1���Ϲ�100,2�ű��Ϊ80,3���˷�.��ô��3������۶�Ӧ�÷ֱ�Ϊ100,-20,-80.���Ҫ��ѯ1�ŵ����۶�,ǰ���ͳ����ȡ�÷����������������,��0(�������˷�).
	 * �������´���
	 * 1.�����˷�,��ѯ��Room�����������˷���,���˷����������Ϲ������Ϲ�����Ϊ��ѯ�����ڵ�,���Ϲ����ϵ����۽��ӵ�֮ǰ��ͳ�ƽ����. (0+80)
	 * 2.�����˷�,��ѯ��Room�����������˷���,���˷������ڲ�ѯ�����ڵ�,��֮ǰ��ͳ�ƽ�������Ϲ����ϵ����۽�� (�˷�������3��,���ڲ�ѯ������)
	 * 3.���ڱ��,��ѯ��Room���������ı����,�������������Ϲ������Ϲ������ڲ�ѯ�����ڵ�,�����������Ĳ��ӵ�֮ǰ��ͳ�ƽ���� (80 + 20)
	 * 4.���ڱ��,��ѯ��Room���������ı����,���������ڲ�ѯ�����ڵ�,��֮ǰ��ͳ�ƽ������������ϵĲ�� (���������2��,���ٲ�ѯ������)
	 * 5.���ڻ���,��ѯ��Room���������Ļ�����,�任����������ԭ�Ϲ������Ϲ�����Ϊ��ѯ�����ڵ�,���Ϲ����ϵĺ�ͬ�ܼۼӵ�֮ǰ��ͳ�ƽ����
	 * 6.���ڻ���,��ѯ��Room���������Ļ�����,�任�������������Ϲ������Ϲ�����Ϊ��ѯ�����ڵ�,��֮ǰ��ͳ�ƽ���������Ϲ����ϵĺ�ͬ�ܼ�
	 *���������Ϲ����;��Ϲ����Ľ���ֵ
	 *
	 * 7.���ڻ�������Ҳ���԰��������˷�һ�����߼�������
	 * */
	private void handleSpecialBiz(boolean isPreIntoSale) throws BOSException, SQLException {
		FDCSQLBuilder builder = new FDCSQLBuilder();
		builder
		.appendSql("select s.FID FSellProjectID,count(*) contractNum,sum(c.FBuildingArea) buildingArea,sum(c.FBuildingArea*c.FBaseBuildingPrice) termSumBaseAmount," 
				+"sum(b.FStandardTotalAmount) termSumAmount,"
				+"sum(b.FDealAmount) termDealTotalAmount,sum(b.FContractTotalAmount) contractAmount,sum(c.FAreaCompensateAmount) termAreaCompensateAmount " 
				+"from T_SHE_QuitRoom a " 
				+"left outer join T_SHE_Purchase b on a.FPurchaseID=b.FID " 
				+"left outer join T_SHE_Room c on b.FRoomID=c.FID " 
				+"left outer join T_SHE_Building d on c.FBuildingID=d.FID " 
				+"left outer join T_SHE_SellProject s on d.FSellProjectID=s.FID " 
				+"where c.FIsForSHE=1 AND a.FState='4AUDITTED' ");
		if (isPreIntoSale) {
			//				builder.appendSql(" and (fSellState = 'PrePurchase' or fSellState = 'Purchase' or fSellState = 'Sign')");
		} else {//Ԥ������������ͳ�ƵĻ�,Ԥ���˷��ļ�¼Ҳ��Ӧͳ������
			builder.appendSql(" and b.FToPurchaseDate is not null ");
		}
		if(isPreIntoSale){
			this.appendFilterSql(builder, "b.FToSaleDate");
		}else{
			this.appendFilterSql(builder, "b.FToPurchaseDate");
		}
		builder.appendSql(" group by s.FID ");
		IRowSet termQuitSellSet = builder.executeQuery();
		while (termQuitSellSet.next()) {
			//String buildingId = termQuitSellSet.getString("FBuildingId");
			String sellProjectId = termQuitSellSet.getString("FSellProjectID");
			IRow row = (IRow) this.sellProCountMap.get(sellProjectId);
			if (row == null) {
				continue;
			}
			addToCell(row, "contractCount", termQuitSellSet.getInt("contractNum"));
			addToCell(row, "contractArea", termQuitSellSet.getBigDecimal("buildingArea"));
			addToCell(row, "contractAmount", termQuitSellSet.getBigDecimal("contractAmount"));
			//addToCell(row, "termSumAmount", termQuitSellSet.getBigDecimal("termSumAmount"));
			//addToCell(row, "termDealTotalAmount", termQuitSellSet.getBigDecimal("termDealTotalAmount"));
			//addToCell(row, "termContractTotalAmount", termQuitSellSet.getBigDecimal("termContractTotalAmount"));
			//addToCell(row, "termAreaCompensateAmount", termQuitSellSet.getBigDecimal("termAreaCompensateAmount"));

			//reSetSaleAmount(row);//����仯�˺�ͬ�ܼ�,�����ܼ�ҲҪ��Ӧ�仯
		}

		builder.clear();
		builder
		.appendSql("select s.FID FSellProjectID,count(*) contractNum,sum(c.FBuildingArea) buildingArea,sum(c.FBuildingArea*c.FBaseBuildingPrice) termSumBaseAmount," 
				+"sum(b.FStandardTotalAmount) termSumAmount,"
				+"sum(b.FDealAmount) termDealTotalAmount,sum(b.FContractTotalAmount) contractAmount,sum(c.FAreaCompensateAmount) termAreaCompensateAmount " 
				+"from T_SHE_QuitRoom a " 
				+"left outer join T_SHE_Purchase b on a.FPurchaseID=b.FID " 
				+"left outer join T_SHE_Room c on b.FRoomID=c.FID " 
				+"left outer join T_SHE_Building d on c.FBuildingID=d.FID " 
				+"left outer join T_SHE_SellProject s on d.FSellProjectID=s.FID " 
				+"where c.FIsForSHE=1 AND a.FState='4AUDITTED' ");
		if (isPreIntoSale) {
			//				builder.appendSql(" and (fSellState = 'PrePurchase' or fSellState = 'Purchase' or fSellState = 'Sign')");
		} else {//Ԥ������������ͳ�ƵĻ�,Ԥ���˷��ļ�¼Ҳ��Ӧͳ������
			builder.appendSql(" and b.FToPurchaseDate is not null ");
		}
		String str = "";
		if(isAudit)
		{
			str = "a.FAuditTime";
		}else
		{
			str = "a.FQuitDate";
		}
		this.appendFilterSql(builder, str);
		builder.appendSql(" group by s.FID ");
		IRowSet termQuitSet = builder.executeQuery();
		while (termQuitSet.next()) {
			//String buildingId = termQuitSet.getString("FBuildingId");
			String sellProjectId = termQuitSet.getString("FSellProjectID");
			IRow row = (IRow) this.sellProCountMap.get(sellProjectId);
			if (row == null) {
				continue;
			}
			reduceFromCell(row, "contractCount", termQuitSet.getInt("contractNum"));
			reduceFromCell(row, "contractArea", termQuitSet.getBigDecimal("buildingArea"));
			reduceFromCell(row, "contractAmount", termQuitSet.getBigDecimal("contractAmount"));
			//reduceFromCell(row, "termSumAmount", termQuitSet.getBigDecimal("termSumAmount"));
			//reduceFromCell(row, "termDealTotalAmount", termQuitSet.getBigDecimal("termDealTotalAmount"));
			//reduceFromCell(row, "termContractTotalAmount", termQuitSet.getBigDecimal("termContractTotalAmount"));
			//reduceFromCell(row, "termAreaCompensateAmount", termQuitSet.getBigDecimal("termAreaCompensateAmount"));

			//reSetSaleAmount(row);
		}

		builder.clear();
		builder
		.appendSql("select s.FID FSellProjectID,sum(a.FOldDealAmount-a.FNewDealAmount) termDealTotalAmount," 
				+"sum(a.FOldContractAmount-a.FNewContractAmount) contractAmount from T_SHE_PurchaseChange a " 
				+"left outer join T_SHE_Purchase b on a.FPurchaseID=b.FID " 
				+"left outer join T_SHE_Room c on b.FRoomID=c.FID " 
				+"left outer join T_SHE_Building d on c.FBuildingID=d.FID "
				+"left outer join T_SHE_SellProject s on d.FSellProjectID=s.FID");
		builder.appendSql(" where c.FIsForSHE=1 AND a.FState='4AUDITTED' ");
		if (isPreIntoSale) {
			//				builder.appendSql(" and (fSellState = 'PrePurchase' or fSellState = 'Purchase' or fSellState = 'Sign')");
		} else {//Ԥ������������ͳ�ƵĻ�,Ԥ������ļ�¼Ҳ��Ӧͳ������
			builder.appendSql(" and b.FToPurchaseDate is not null ");
		}
		if(isPreIntoSale){
			this.appendFilterSql(builder, "b.FToSaleDate");
		}else{
			this.appendFilterSql(builder, "b.FToPurchaseDate");
		}
		builder.appendSql(" group by s.FID ");
		IRowSet termChangeSellSet = builder.executeQuery();
		while (termChangeSellSet.next()) {
			//String buildingId = termChangeSellSet.getString("FBuildingId");
			String sellProjectId = termChangeSellSet.getString("FSellProjectID");
			IRow row = (IRow) this.sellProCountMap.get(sellProjectId);
			if (row == null) {
				continue;
			}
			addToCell(row, "contractAmount", termChangeSellSet.getBigDecimal("contractAmount"));
			//addToCell(row, "termContractTotalAmount", termChangeSellSet.getBigDecimal("termContractTotalAmount"));

			//reSetSaleAmount(row);
		}

		builder.clear();
		builder
		.appendSql("select s.FID FSellProjectID,sum(a.FOldDealAmount-a.FNewDealAmount) termDealTotalAmount," 
				+"sum(a.FOldContractAmount-a.FNewContractAmount) contractAmount from T_SHE_PurchaseChange a " 
				+"left outer join T_SHE_Purchase b on a.FPurchaseID=b.FID " 
				+"left outer join T_SHE_Room c on b.FRoomID=c.FID "
				+"left outer join T_SHE_Building d on c.FBuildingID=d.FID "
				+"left outer join T_SHE_SellProject s on d.FSellProjectID=s.FID");
		builder.appendSql(" where c.FIsForSHE=1 AND a.FState='4AUDITTED' ");
		if (isPreIntoSale) {
			//				builder.appendSql(" and (fSellState = 'PrePurchase' or fSellState = 'Purchase' or fSellState = 'Sign')");
		} else {//Ԥ������������ͳ�ƵĻ�,Ԥ������ļ�¼Ҳ��Ӧͳ������
			builder.appendSql(" and b.FToPurchaseDate is not null ");
		}
		String str2 = "";
		if(isAudit)
		{
			str2 = "a.FAuditTime";
		}else
		{
			str2 = "a.FChangeDate";
		}
		this.appendFilterSql(builder, str2);
		builder.appendSql(" group by s.FID ");
		IRowSet termChangeSet = builder.executeQuery();
		while (termChangeSet.next()) {
			//String buildingId = termChangeSet.getString("FBuildingId");
			String sellProjectId = termChangeSet.getString("FSellProjectID");
			IRow row = (IRow) this.sellProCountMap.get(sellProjectId);
			if (row == null) {
				continue;
			}
			reduceFromCell(row, "contractAmount", termChangeSet.getBigDecimal("contractAmount"));
			//reduceFromCell(row, "termContractTotalAmount", termChangeSet.getBigDecimal("termContractTotalAmount"));

			//reSetSaleAmount(row);
		}

		builder.clear();
		builder
		.appendSql("select s.FID FSellProjectID,count(*) contractNum,sum(c.FBuildingArea) buildingArea, "
				+"sum(b.FContractTotalAmount) contractAmount from T_SHE_ChangeRoom a "
				+"left outer join T_SHE_Purchase b on a.FOldPurchaseID=b.FID " 
				+"left outer join T_SHE_Room c on b.FRoomID=c.FID "
				+"left outer join T_SHE_Building d on c.FBuildingID=d.FID "
				+"left outer join T_SHE_SellProject s on d.FSellProjectID=s.FID");
		builder.appendSql(" where c.FIsForSHE=1 AND a.FState='4AUDITTED' ");
		if (isPreIntoSale) {
			//	builder.appendSql(" and (fSellState = 'PrePurchase' or fSellState = 'Purchase' or fSellState = 'Sign')");
		} else {//Ԥ������������ͳ�ƵĻ�,Ԥ���˷��ļ�¼Ҳ��Ӧͳ������
			builder.appendSql(" and b.FToPurchaseDate is not null ");
		}
		if(isPreIntoSale){
			this.appendFilterSql(builder, "b.FToSaleDate");
		}else{
			this.appendFilterSql(builder, "b.FToPurchaseDate");
		}
		builder.appendSql(" group by s.FID ");
		IRowSet changeRoomSellSet = builder.executeQuery();
		while (changeRoomSellSet.next()) {
			String sellProjectId = changeRoomSellSet.getString("FSellProjectID");
			IRow row = (IRow) this.sellProCountMap.get(sellProjectId);
			if (row == null) {
				continue;
			}
			addToCell(row, "contractCount", changeRoomSellSet.getInt("contractNum"));
			addToCell(row, "contractArea", changeRoomSellSet.getBigDecimal("buildingArea"));
			addToCell(row, "contractAmount", changeRoomSellSet.getBigDecimal("contractAmount"));
		}
		
		builder.clear();
		builder
		.appendSql("select s.FID FSellProjectID,count(*) contractNum,sum(c.FBuildingArea) buildingArea, "
				+"sum(b.FContractTotalAmount) contractAmount from T_SHE_ChangeRoom a "
				+"left outer join T_SHE_Purchase b on a.FOldPurchaseID=b.FID " 
				+"left outer join T_SHE_Room c on b.FRoomID=c.FID "
				+"left outer join T_SHE_Building d on c.FBuildingID=d.FID "
				+"left outer join T_SHE_SellProject s on d.FSellProjectID=s.FID");
		builder.appendSql(" where c.FIsForSHE=1 AND a.FState='4AUDITTED' ");
		if (isPreIntoSale) {
			//				builder.appendSql(" and (fSellState = 'PrePurchase' or fSellState = 'Purchase' or fSellState = 'Sign')");
		} else {//Ԥ������������ͳ�ƵĻ�,Ԥ���˷��ļ�¼Ҳ��Ӧͳ������
			builder.appendSql(" and b.FToPurchaseDate is not null ");
		}
		String str3 = "";
		if(isAudit)
		{
			str3 = "a.FAuditTime";
		}else
		{
			str3 = "a.FChangeDate";
		}
		this.appendFilterSql(builder, str3);
		this.appendFilterSql(builder, "a.FChangeDate");
		builder.appendSql(" group by s.FID ");
		IRowSet termChangeRoomSet = builder.executeQuery();
		while (termChangeRoomSet.next()) {
			String sellProjectId = termChangeRoomSet.getString("FSellProjectID");
			IRow row = (IRow) this.sellProCountMap.get(sellProjectId);
			if (row == null) {
				continue;
			}
			reduceFromCell(row, "contractCount", termChangeRoomSet.getInt("contractNum"));
			reduceFromCell(row, "contractArea", termChangeRoomSet.getBigDecimal("buildingArea"));
			reduceFromCell(row, "contractAmount", termChangeRoomSet.getBigDecimal("contractAmount"));
		}
		
		//�������ַ�ʽ�ѻ��������˷�һ��������
//		builder.clear();
//		builder
//		.appendSql("select s.FID FSellProjectID,count(*) contractNum,sum(c.FBuildingArea) buildingArea, "
//				+"sum(b.FContractTotalAmount) contractAmount from T_SHE_ChangeRoom a "
//				+"left outer join T_SHE_Purchase b on a.FOldPurchaseID=b.FID " 
//				+"left outer join T_SHE_Room c on b.FRoomID=c.FID "
//				+"left outer join T_SHE_Building d on c.FBuildingID=d.FID "
//				+"left outer join T_SHE_SellProject s on d.FSellProjectID=s.FID");
//		builder.appendSql(" where c.FIsForSHE=1 AND a.FState='4AUDITTED' ");
//		if (isPreIntoSale) {
//			//			builder.appendSql(" and (fSellState = 'PrePurchase' or fSellState = 'Purchase' or fSellState = 'Sign')");
//		} else {//Ԥ������������ͳ�ƵĻ�,Ԥ������ļ�¼Ҳ��Ӧͳ������
//			builder.appendSql(" and b.FToPurchaseDate is not null ");
//		}
//		this.appendFilterSql(builder, "a.FChangeDate");
//		builder.appendSql(" group by s.FID ");
//		Map amountMap = new HashMap();
//		Map countMap = new HashMap();
//		Map buildAreaMap = new HashMap();
//		IRowSet changeOldRoomSet = builder.executeQuery();
//		while (changeOldRoomSet.next()) {
//			String sellProjectId = changeOldRoomSet.getString("FSellProjectID");
//			IRow row = (IRow) this.sellProCountMap.get(sellProjectId);
//			if (row == null) {
//				continue;
//			}
//			addToCell(row, "contractAmount", changeOldRoomSet.getBigDecimal("contractAmount"));
//			addToCell(row, "contractCount", changeOldRoomSet.getInt("contractNum"));
//			addToCell(row, "contractArea",  changeOldRoomSet.getBigDecimal("buildingArea"));
//			BigDecimal contractAmount =FDCHelper.ZERO;
//			if(changeOldRoomSet.getBigDecimal("contractAmount") ==null || changeOldRoomSet.getBigDecimal("contractAmount").compareTo(FDCHelper.ZERO)==0)
//			{
//				amountMap.put(sellProjectId, contractAmount);
//			}else
//			{
//				amountMap.put(sellProjectId, changeOldRoomSet.getBigDecimal("contractAmount"));
//			}
//			if( changeOldRoomSet.getBigDecimal("buildingArea")==null ||  changeOldRoomSet.getBigDecimal("buildingArea").compareTo(FDCHelper.ZERO)==0)
//			{
//				buildAreaMap.put(sellProjectId, FDCHelper.ZERO);
//			}else
//			{
//				buildAreaMap.put(sellProjectId, changeOldRoomSet.getBigDecimal("buildingArea"));
//			}
//			countMap.put(sellProjectId, new Integer(changeOldRoomSet.getInt("contractNum")));
//		}
//		
//		builder.clear();
//		builder
//		.appendSql("select s.FID FSellProjectID,count(*) contractNum,sum(c.FBuildingArea) buildingArea, " 
//				+"sum(b.FContractTotalAmount) contractAmount from T_SHE_ChangeRoom a "
//				+"left outer join T_SHE_Purchase b on a.FNewPurchaseID=b.FID " 
//				+"left outer join T_SHE_Room c on b.FRoomID=c.FID "
//				+"left outer join T_SHE_Building d on c.FBuildingID=d.FID "
//				+"left outer join T_SHE_SellProject s on d.FSellProjectID=s.FID");
//		builder.appendSql(" where c.FIsForSHE=1 AND a.FState='4AUDITTED' ");
//		if (isPreIntoSale) {
//			//			builder.appendSql(" and (fSellState = 'PrePurchase' or fSellState = 'Purchase' or fSellState = 'Sign')");
//		} else {//Ԥ������������ͳ�ƵĻ�,Ԥ������ļ�¼Ҳ��Ӧͳ������
//			//builder.appendSql(" and b.FToPurchaseDate is not null ");
//		}
//		this.appendFilterSql(builder, "a.FChangeDate");
//		builder.appendSql(" group by s.FID ");
//		IRowSet changeNewRoomSet = builder.executeQuery();
//		while (changeNewRoomSet.next()) {
//			//String buildingId = termChangeSet.getString("FBuildingId");
//			String sellProjectId = changeNewRoomSet.getString("FSellProjectID");
//			IRow row = (IRow) this.sellProCountMap.get(sellProjectId);
//			if (row == null) {
//				continue;
//			}			
//			reduceFromCell(row, "contractAmount", changeNewRoomSet.getBigDecimal("contractAmount"));
//			reduceFromCell(row, "contractCount", changeNewRoomSet.getInt("contractNum"));
//			reduceFromCell(row, "contractArea",  changeNewRoomSet.getBigDecimal("buildingArea"));
//			//������2���Ϲ����ϵĽ�����������Ĳ�
//			BigDecimal newAmount = FDCHelper.ZERO;
//			BigDecimal newBuildingArea = FDCHelper.ZERO;
//			int newCount = 0;
//			BigDecimal amount = (BigDecimal)amountMap.get(sellProjectId);				
//			//���ԭ��ͬ������0�Ͳ���Ҫ�������ߵĲ�ۣ���˵��ֻ���º�ͬ�ڲ�ѯʱ�䷶Χ�ڣ������Ѿ����˴���
//			if(amount ==null ||amount.compareTo(FDCHelper.ZERO)==0)
//			{
//				amount = FDCHelper.ZERO;
//			}else
//			{
//				int count = ((Integer)countMap.get(sellProjectId)).intValue();
//				BigDecimal builingArea = (BigDecimal)buildAreaMap.get(sellProjectId);
//				newAmount = changeNewRoomSet.getBigDecimal("contractAmount");
//				addToCell(row, "contractAmount", newAmount.subtract(amount));
//				
//				newCount = (new Integer(changeNewRoomSet.getInt("contractNum"))).intValue();
//				addToCell(row, "contractCount", newCount-count);
//				
//				newBuildingArea = changeNewRoomSet.getBigDecimal("buildingArea");
//				addToCell(row, "contractArea", newBuildingArea.subtract(builingArea));
//			}
//		}
	}

	/**
	 * ��addedValue�ӵ�ԭ�ȵ�Ԫ���ֵ��,������ֵ�����ڵ�Ԫ����
	 * */
	private void addToCell(IRow row, String colName, int addedValue) {
		if(addedValue == 0){
			return;
		}
		ICell cell = row.getCell(colName);
		if(cell == null) return ;
		int srcValue = 0;
		if(cell.getValue() != null) srcValue = ((Integer)cell.getValue()).intValue();
		cell.setValue(new Integer(srcValue + addedValue));
	}

	private void addToCell(IRow row, String colName, BigDecimal addedValue) {
		if(addedValue == null  ||   addedValue.compareTo(FDCHelper.ZERO) == 0){
			return;
		}
		ICell cell = row.getCell(colName);
		if(cell == null) return ;
		BigDecimal srcValue = (BigDecimal) cell.getValue();
		if(srcValue == null) srcValue = FDCHelper.ZERO;
		cell.setValue(srcValue.add(addedValue));
	}

	private void reduceFromCell(IRow row, String colName, int reducedValue) {
		if(reducedValue == 0){
			return;
		}
		ICell cell = row.getCell(colName);
		if(cell == null) return ;
		int srcValue = 0;
		if(cell.getValue() != null) srcValue = ((Integer)cell.getValue()).intValue();
		cell.setValue(new Integer(srcValue - reducedValue));
	}

	private void reduceFromCell(IRow row, String colName, BigDecimal reducedValue) {
		if(reducedValue == null  ||   reducedValue.compareTo(FDCHelper.ZERO) == 0){
			return;
		}
		ICell cell = row.getCell(colName);
		if(cell == null) return ;
		BigDecimal srcValue = (BigDecimal) cell.getValue();
		if(srcValue == null) srcValue = FDCHelper.ZERO;
		cell.setValue(srcValue.subtract(reducedValue));
	}

	private boolean isPreIntoSale(){
		FDCCustomerParams para = new FDCCustomerParams(this.getFilterUI().getCustomerParams());
		return this.getFilterUI().isPreIntoSale(para);
	}

	protected void tblMain_tableClicked(KDTMouseEvent e) throws Exception {
		if (e.getButton() == 1 && e.getClickCount() == 2) {
			return;
		}
		super.tblMain_tableClicked(e);
	}

	protected String getEditUIName() {
		return null;
	}

	protected ICoreBase getBizInterface() throws Exception {
		return null;
	}

}