/**
 * output package name
 */
package com.kingdee.eas.fdc.sellhouse.client;

import java.awt.Color;
import java.awt.event.*;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeModel;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.metadata.IMetaDataPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ctrl.kdf.table.IColumn;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTDataRequestManager;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectManager;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent;
import com.kingdee.bos.ctrl.kdf.util.style.Styles.HorizontalAlignment;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.query.IQueryExecutor;
import com.kingdee.eas.base.commonquery.client.CommonQueryDialog;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.basedata.FDCCustomerParams;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.market.client.WarningTestUI;
import com.kingdee.eas.fdc.sellhouse.BuildingInfo;
import com.kingdee.eas.fdc.sellhouse.ReceptionTypeCollection;
import com.kingdee.eas.fdc.sellhouse.ReceptionTypeFactory;
import com.kingdee.eas.fdc.sellhouse.ReceptionTypeInfo;
import com.kingdee.eas.fdc.sellhouse.SellProjectInfo;
import com.kingdee.eas.fdc.sellhouse.TrackRecordCollection;
import com.kingdee.eas.fdc.sellhouse.TrackRecordFactory;
import com.kingdee.eas.fdc.sellhouse.TrackRecordInfo;
import com.kingdee.eas.fm.common.FMConstants;
import com.kingdee.eas.framework.*;
import com.kingdee.jdbc.rowset.IRowSet;

/**
 * �ͻ�����ͳ�Ʊ�
 */
public class TrackRecordStatUI extends AbstractTrackRecordStatUI
{
    private static final Logger logger = CoreUIObject.getLogger(TrackRecordStatUI.class);
    
	private TrackRecordStatFilterUI filterUI = null;

	private CommonQueryDialog commonQueryDialog = null;
    
	Map receptTypeMap = null;   //�Ӵ���ʽ
	
	Map receptTypeColumnNameMap = null;   //�Ӵ���ʽ ��Ӧ ����
	
	Map sellProCountMap = null;    //������Ŀid  ��Ӧ   Row
	
	Date beginDate = null;
	Date endDate = null;


    public TrackRecordStatUI() throws Exception
    {
        super();
    }

	protected String getEditUIName() {
		return TrackRecordListUI.class.getName();
	}

	protected ICoreBase getBizInterface() throws Exception {
		return null;
	}

	//��ȡ�Ӵ���ʽ��id��name��ӳ���ϵ
	private void getReceptTypeMap() {
		if(receptTypeMap!=null) return;
		
		ReceptionTypeCollection recepTypes = null; 
		try {
			recepTypes = ReceptionTypeFactory.getRemoteInstance().getReceptionTypeCollection();
		} catch (BOSException e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		}
			
		if(recepTypes!=null) {
			receptTypeMap = new HashMap();
			receptTypeColumnNameMap = new HashMap();
			for(int i=0;i<recepTypes.size();i++) {
				ReceptionTypeInfo receptType = recepTypes.get(i);
				receptTypeMap.put(receptType.getId().toString(),receptType.getName());
			}
		}
	}
	
	

	
	/**
	 * ���ݽӴ���ʽ������ͷ����  recepTypeColumnMap   sellProjectName trackCountSum1 newCustomerCountSum1
	 */
	private void initTableHead() {
		getReceptTypeMap();

		if(receptTypeMap!=null)  {      //������ͷ
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
			Set keySet = receptTypeMap.keySet();
			Iterator iter = keySet.iterator();
			int i = 1;
			while(iter.hasNext()) {		
				String key = (String)iter.next();
				receptTypeColumnNameMap.put(key,"trackCountSum"+i);
				
				IColumn columnTrack = this.tblMain.addColumn();
				columnTrack.setKey("trackCountSum"+i);			
				headRow0.getCell("trackCountSum"+i).setValue(receptTypeMap.get(key));
				headRow1.getCell("trackCountSum"+i).setValue("�����ܴ���");
				this.tblMain.getColumn("trackCountSum"+i).getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
				IColumn columnCust = this.tblMain.addColumn();
				columnCust.setKey("newCustomerCountSum"+i);
				headRow0.getCell("newCustomerCountSum"+i).setValue(receptTypeMap.get(key));
				headRow1.getCell("newCustomerCountSum"+i).setValue("�¿ͻ���");
				this.tblMain.getColumn("newCustomerCountSum"+i).getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
				this.tblMain.getHeadMergeManager().mergeBlock(0,columnTrack.getColumnIndex(), 0, columnCust.getColumnIndex());				
				i++;
			}
		}
	}
	

	/**
	 * ����������Ŀ�����������е�������Ŀ
	 */
	private void initTableSellPro(){
		try {
			TreeModel sellProTree = SHEHelper.getSellProjectTree(this.actionOnLoad);
			DefaultMutableTreeNode sellProRoot = (DefaultMutableTreeNode) sellProTree.getRoot();	
			sellProCountMap = new HashMap();			
			fillNode(this.tblMain,sellProRoot);
			this.tblMain.getTreeColumn().setDepth(sellProRoot.getDepth() + 1);
		} catch (Exception e) {
			e.printStackTrace();
		}
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
	
	
	public void onShow() throws Exception {
		super.onShow();
		
     	int indexNum = this.tblMain.getColumn("sellProjectName").getColumnIndex();     	
     	this.tblMain.getViewManager().setFreezeView(-1, indexNum+1);
	}
	
	
	public void onLoad() throws Exception {		
		super.onLoad();
		
		this.actionAddNew.setVisible(false);
		this.actionEdit.setVisible(false);
		this.actionView.setVisible(false);
		this.actionRemove.setVisible(false);
		this.actionLocate.setVisible(false);	
		
		this.tblMain.getStyleAttributes().setLocked(true);
		this.tblMain.getSelectManager().setSelectMode(KDTSelectManager.ROW_SELECT);
		
		
		WarningTestUI.addHideMenuItem(this, this.menuHelp);
	}


	protected boolean initDefaultFilter() {
		return true;
	}

	private TrackRecordStatFilterUI getFilterUI() {
		if (this.filterUI == null) {
			try {
				this.filterUI = new TrackRecordStatFilterUI(this, this.actionOnLoad);
				this.filterUI.onLoad();
			} catch (Exception e) {
				e.printStackTrace();
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
		//commonQueryDialog.setUiObject(null);  //���ر������
		return commonQueryDialog;
	}
	

	
	
	
	
	protected void execQuery() {
		
		this.tblMain.getDataRequestManager().setDataRequestMode(KDTDataRequestManager.REAL_MODE);
	
		if(receptTypeMap==null) {
			this.tblMain.removeHeadRows();
			initTableHead();
		}				
	
		if(sellProCountMap==null || this.tblMain.getRowCount()==0) {
			this.tblMain.removeRows();
			initTableSellPro();
		}
				
		if(sellProCountMap==null)  return;
		
     	int indexNum = this.tblMain.getColumn("sellProjectName").getColumnIndex();     	
     	this.tblMain.getViewManager().setFreezeView(-1, indexNum+1);
		
		
		//������������
		clearAllDate();
		
		String sellProIds = "";
		Set idSet = sellProCountMap.keySet();
		Iterator iter = idSet.iterator();
		while(iter.hasNext()) {
			sellProIds += "'" + iter.next() + "',";			
		}
		if(!sellProIds.equals(""))  sellProIds = sellProIds.substring(0,sellProIds.length()-1);
		
		
			
		//��ѯ���� �������		
		String  uiAndDateSql = this.mainQuery.toString();
		//WHERE (eventDate >= {TS '2009-01-01 00:00:00'}) AND (eventDate <= {TS '2009-12-31 00:00:00'}) AND (CU.id = 'D75OO4J6ShyPjEambGp0R8znrtQ=' OR CU.id = '11111111-1111-1111-1111-111111111111CCE7AED4' OR CU.id = '00000000-0000-0000-0000-000000000000CCE7AED4')  ��java.lang.String��
		getBeginAndEndDate(uiAndDateSql);    //��ÿ�ʼ�ͽ���ʱ��
		
		uiAndDateSql = uiAndDateSql.replaceAll("CU.id","FControlUnitID");
		uiAndDateSql = uiAndDateSql.replaceAll("eventDate","FEventDate");
		String sqlWhere = uiAndDateSql;
		if(!sellProIds.equals("")) 
			sqlWhere += " and  FSellProjectId in ("+sellProIds+") ";
		
		FDCSQLBuilder builder = new FDCSQLBuilder();    //��ѯ������Ŀ��,���Ӵ���ʽ�ĸ����ܴ���
		builder.appendSql("select FSellProjectId,FReceptionTypeID,count(*) as num from T_SHE_TrackRecord ");
		builder.appendSql(sqlWhere);
		builder.appendSql(" group by FSellProjectId,FReceptionTypeID ");

			try {
				IRowSet termSellSet = null;
				termSellSet = builder.executeQuery();
				while (termSellSet.next()) {
					String projectId = termSellSet.getString("FSellProjectId");
					String recepTypeId = termSellSet.getString("FReceptionTypeID");
					int trackNum = termSellSet.getInt("num");					
					IRow row = (IRow)sellProCountMap.get(projectId);
					row.getCell((String)receptTypeColumnNameMap.get(recepTypeId)).setValue(String.valueOf(trackNum));
				}	
			} catch (BOSException e) {
				logger.error(e.getMessage());
				e.printStackTrace();
			} catch (SQLException e) {
				logger.error(e.getMessage());
				e.printStackTrace();
			}
			
		
			//�¿ͻ���  ��������    1.�ͻ������ǣ� ȫ���о�ֻ��һ�θ�����¼�Ŀͻ�   2.����ôεĲ�ѯ��Χ  3.distinct(FHeadID) 
			builder =  new FDCSQLBuilder();  
			builder.appendSql("select FSellProjectId,FReceptionTypeID,count(distinct(FHeadID)) as num from T_SHE_TrackRecord ");
			builder.appendSql(sqlWhere);
			builder.appendSql(" and FHeadID in ( ");
			builder.appendSql("select fheadid from (select fheadid ,count(*) as usernum from T_SHE_TrackRecord ");
			if(!sellProIds.equals("")) 
				sqlWhere += " and  FSellProjectId in ("+sellProIds+") ";
			builder.appendSql(" group by fheadid) AA where usernum = 1");
			builder.appendSql(") group by FSellProjectId,FReceptionTypeID ");
			try {
				IRowSet termSellSet = null;
				termSellSet = builder.executeQuery();
				while (termSellSet.next()) {
					String projectId = termSellSet.getString("FSellProjectId");
					String recepTypeId = termSellSet.getString("FReceptionTypeID");
					int distUserNum = termSellSet.getInt("num");					
					IRow row = (IRow)sellProCountMap.get(projectId);
					int columnIndex = row.getCell((String)receptTypeColumnNameMap.get(recepTypeId)).getColumnIndex();
					row.getCell(columnIndex+1).setValue(String.valueOf(distUserNum));
				}	
			} catch (BOSException e) {
				logger.error(e.getMessage());
				e.printStackTrace();
			} catch (SQLException e) {
				logger.error(e.getMessage());
				e.printStackTrace();
			}
			
			
			
			
			//�����ܹ�˾��ͳ��  Ĭ��Ӧ���ǵ�0��
			IRow row = this.tblMain.getRow(0);		
			
			int rowCount = this.tblMain.getRowCount();
			if(row!=null)  {
				Set tempSet = receptTypeColumnNameMap.keySet();
				Iterator tempIter = tempSet.iterator();
				while(tempIter.hasNext()) {
					String trackColName = (String)receptTypeColumnNameMap.get(tempIter.next());
					int colunIndex = row.getCell(trackColName).getColumnIndex();
					int sumTrackNum = 0;
					int sumUserNum = 0;
					for(int i=1;i<rowCount;i++) {
						String cellTrackValue = (String)this.tblMain.getRow(i).getCell(colunIndex).getValue();
						if(cellTrackValue!=null) 
							sumTrackNum += Integer.parseInt(cellTrackValue);
						
						String cellUserValue = (String)this.tblMain.getRow(i).getCell(colunIndex+1).getValue();
						if(cellUserValue!=null) 
							sumUserNum += Integer.parseInt(cellUserValue);
					}
					if(sumTrackNum!=0)  
						row.getCell(colunIndex).setValue(String.valueOf(sumTrackNum));
					if(sumUserNum!=0)  
						row.getCell(colunIndex+1).setValue(String.valueOf(sumUserNum));		
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
					Set tempSet = receptTypeColumnNameMap.keySet();
					Iterator tempIter = tempSet.iterator();
					while(tempIter.hasNext()) {
						String trackColName = (String)receptTypeColumnNameMap.get(tempIter.next());
						int colunIndex = row.getCell(trackColName).getColumnIndex();
						int sumTrackNum = 0;
						int sumUserNum = 0;
						for(int ii=0;ii<rowList.size();ii++) {
							IRow addRow = (IRow)rowList.get(ii);
							String cellTrackValue = (String)addRow.getCell(colunIndex).getValue();
							if(cellTrackValue!=null) 
								sumTrackNum += Integer.parseInt(cellTrackValue);
							
							String cellUserValue = (String)addRow.getCell(colunIndex+1).getValue();
							if(cellUserValue!=null) 
								sumUserNum += Integer.parseInt(cellUserValue);
						}
						if(sumTrackNum!=0)  
							row.getCell(colunIndex).setValue(String.valueOf(sumTrackNum));
						if(sumUserNum!=0)  
							row.getCell(colunIndex+1).setValue(String.valueOf(sumUserNum));		
					}				
				}
				
				
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
	
	
	
	
	protected void tblMain_tableClicked(KDTMouseEvent e) throws Exception {
		//super.tblMain_tableClicked(e);		
		//���ݵ�ǰ��������Ŀ  �� ��ѯ��ʼʱ�� �� ����ʱ��
		if (e.getButton() == 1 && e.getClickCount() == 2) {
			int rowIndex = this.tblMain.getSelectManager().getActiveRowIndex();
			IRow row = this.tblMain.getRow(rowIndex);
			if (row != null) {
				SellProjectInfo proInfo = (SellProjectInfo) row.getUserObject();
				if (proInfo != null) {
					TrackRecordListUI.showUI(this, proInfo.getId().toString(),beginDate, endDate);
				}
			}
		}
		
	}
	
	
	
	private void getBeginAndEndDate(String dateStr) {
		Pattern p=Pattern.compile("\\d{4}\\-\\d{2}\\-\\d{2}\\s\\d{2}:\\d{2}:\\d{2}");  
		Matcher m=p.matcher(dateStr);  
		Date date1 = null;
		Date date2 = null;
		while(m.find()) {
			String tempStr = m.group();
			java.text.SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			try {
				if(date1==null)
					date1 =  df.parse(tempStr);
				else
					date2 = df.parse(tempStr);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}  
		
		if(date1!=null && date2!=null) {
			if(date1.after(date2)) {
				Date tempDate = date1;
				date1 = date2;
				date2 = tempDate;
			}			
		}
		
		beginDate = date1;
		endDate = date2;
	}
	
	public int getRowCountFromDB(){
		return -1;
	}

	
	

}