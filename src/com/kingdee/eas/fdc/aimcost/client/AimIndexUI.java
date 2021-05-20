/**
 * output package name
 */
package com.kingdee.eas.fdc.aimcost.client;

import java.awt.event.*;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ctrl.kdf.table.ICell;
import com.kingdee.bos.ctrl.kdf.table.IColumn;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectManager;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.fdc.basecrm.CRMHelper;
import com.kingdee.eas.fdc.basedata.ApportionTypeCollection;
import com.kingdee.eas.fdc.basedata.ApportionTypeFactory;
import com.kingdee.eas.fdc.basedata.ApportionTypeInfo;
import com.kingdee.eas.fdc.basedata.CurProjProductEntries;
import com.kingdee.eas.fdc.basedata.CurProjProductEntriesCollection;
import com.kingdee.eas.fdc.basedata.CurProjProductEntriesFactory;
import com.kingdee.eas.fdc.basedata.CurProjProductEntriesInfo;
import com.kingdee.eas.fdc.basedata.FDCConstants;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.ProjectIndexDataCollection;
import com.kingdee.eas.fdc.basedata.ProjectIndexDataEntryCollection;
import com.kingdee.eas.fdc.basedata.ProjectIndexDataEntryInfo;
import com.kingdee.eas.fdc.basedata.ProjectIndexDataFactory;
import com.kingdee.eas.fdc.basedata.ProjectIndexDataInfo;
import com.kingdee.eas.fdc.basedata.ProjectStageEnum;
import com.kingdee.eas.fdc.contract.FDCUtils;
import com.kingdee.eas.framework.*;

/**
 * output class name
 */
public class AimIndexUI extends AbstractAimIndexUI
{
    private static final Logger logger = CoreUIObject.getLogger(AimIndexUI.class);
    private String projectId = null;
    private ProjectStageEnum stage= null;
    private boolean  isEdit = false; //��ui�Ĳ���״̬
    private String parentID = null;
    public AimIndexUI() throws Exception
    {
        super();
    }
    
    
    public void onLoad() throws Exception {
    	this.projectId = (String)this.getUIContext().get("projectId");
    	this.stage = (ProjectStageEnum)this.getUIContext().get("stage");
    	this.isEdit = ((Boolean)this.getUIContext().get("isEdit"))!= null?((Boolean)this.getUIContext().get("isEdit")).booleanValue():false;
    	this.parentID = (String)this.getUIContext().get("parentID");
    	initUITable();
    	//׼������
    	prepareData();
    	super.onLoad();
    	loadIndexValue();
    	
    }
    private Map editDataMap= new HashMap(); 
    ProjectIndexDataCollection projectIndexDataCollection = null;
    public ProjectIndexDataCollection getProjectIndexDataCollection() {
		return projectIndexDataCollection;
	}


	private void prepareData() throws BOSException {
		if(isEdit){ //ȡ���µ�ָ��ֵ
			EntityViewInfo view = new EntityViewInfo();
			FilterInfo filter = new FilterInfo();
			filter.appendFilterItem("projOrOrgID",projectId );
			filter.appendFilterItem("projectStage",ProjectStageEnum.AIMCOST);
			filter.appendFilterItem("isLatestVer",Boolean.TRUE);
			filter.getFilterItems().add(new FilterItemInfo("aimMeasureID",null));
			SelectorItemCollection sel = new SelectorItemCollection();
			sel.add("*");
			sel.add("productType.id");
			sel.add("entries.apportionType.*");
			sel.add("entries.*");
			view.setFilter(filter);
			projectIndexDataCollection  = ProjectIndexDataFactory.getRemoteInstance().getProjectIndexDataCollection(view);
			//����Ʒ���ͷֳɼ���
			getEditDataMapByProduceType(projectIndexDataCollection);
			//��װ�Ա�������
		} else { //ȡ����ֵ
			EntityViewInfo view = new EntityViewInfo();
			FilterInfo filter = new FilterInfo();
			filter.appendFilterItem("aimMeasureID", parentID);
			view.setFilter(filter);
			ProjectIndexDataCollection coll = ProjectIndexDataFactory.getRemoteInstance().getProjectIndexDataCollection(view);
			getEditDataMapByProduceType(coll);
		}
	}


	private void getEditDataMapByProduceType(ProjectIndexDataCollection coll) {
		editDataMap.clear();
		for(int i = 0 ; i < coll.size() ; i++){
			ProjectIndexDataInfo info = coll.get(i);
			String productId  = null;
			if(info.getProductType()!= null){
				productId= info.getProductType().getId().toString();
				editDataMap.put(productId, info);
			} else {
				editDataMap.put("allProject", info);
			}
		}
		
	}
	private Map tableMap = new HashMap();
	private CurProjProductEntriesCollection curProColl = null;
    private void initUITable() throws BOSException, EASBizException {
		if( projectId== null || stage == null){
			return;
		}
		//ȡ�������Ŀ�ܵĲ�Ʒ����
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("curProject",projectId));
		view.setFilter(filter);
		SelectorItemCollection sel = new SelectorItemCollection();
		sel.add("*");
		sel.add("productType.*");
		view.setSelector(sel);
		curProColl = CurProjProductEntriesFactory.getRemoteInstance().getCurProjProductEntriesCollection(view);
		//���ݲ�Ʒ���ͻ���ҳǩ�ĸ���
		KDTable table = null;
		table = initIndexTable();
		tableMap.clear();
		tableMap.put("allProject", table);
		plAimIndex.add(table,"��������");
		for(int i = 0 ; i < curProColl.size() ; i++){
			CurProjProductEntriesInfo info = curProColl.get(i);
			String productName = info.getProductType().getName();
			table = initIndexTable();
			tableMap.put(info.getProductType().getId().toString(), table); 
			plAimIndex.add(table,productName);
		}
	}
    //-----����---------
    private static final String ID = "id";//ָ���ID
    private static final String NUMBER = "number";//ָ��ı���
    private static final String NAME = "name";//ָ�������
    private static final String INDEXVALUE = "indexValue";//ָ��ֵ
    private static final String UNIT = "unit";//��λ
    private static final String DES = "des";//˵��
    private static final String REMARK = "remark";//��ע
	private KDTable initIndexTable() throws EASBizException, BOSException {
		KDTable table = new KDTable(7,1,0);
		table.getSelectManager().setSelectMode(KDTSelectManager.ROW_SELECT);
		table.getStyleAttributes().setLocked(true);
		IRow row = table.getHeadRow(0);
	
		IColumn column = table.getColumn(0);
		column.setKey(ID);
		row.getCell(0).setValue("ID");
		column.getStyleAttributes().setHided(true);
		
		column = table.getColumn(1);
		column.setKey(NUMBER);
		row.getCell(1).setValue("ָ�����");
		
		column = table.getColumn(2);
		column.setKey(NAME);
		row.getCell(2).setValue("ָ������");
		
		column = table.getColumn(3);
		column.setKey(INDEXVALUE);
		row.getCell(3).setValue("ָ��ֵ");
		column.getStyleAttributes().setBackground(FDCHelper.KDTABLE_TOTAL_BG_COLOR);
		
		column = table.getColumn(4);
		column.setKey(UNIT);
		row.getCell(4).setValue("��λ");
		
		column = table.getColumn(5);
		column.setKey(DES);
		row.getCell(5).setValue("˵��");
		
		column = table.getColumn(6);
		column.setKey(REMARK);
		row.getCell(6).setValue("��ע");
		//���ر�׼ָ��
		loadTheStandardIndex(table);
		return table;
	}
	
	private Map rowMap = new HashMap();
	private void loadTheStandardIndex(KDTable table) throws EASBizException, BOSException {
		Set areaIndexSet=new HashSet();
		if(!isDisplayAreaIndex()){
			areaIndexSet.add(ApportionTypeInfo.buildAreaType);
			areaIndexSet.add(ApportionTypeInfo.sellAreaType);
			areaIndexSet.add(ApportionTypeInfo.placeAreaType);
		}
		areaIndexSet.add(ApportionTypeInfo.aimCostType);
		areaIndexSet.add(ApportionTypeInfo.appointType);
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("id",areaIndexSet,CompareType.NOTINCLUDE));
		filter.getFilterItems().add(new FilterItemInfo("isEnabled",Boolean.TRUE));
		view.setFilter(filter);
		SelectorItemCollection sel = new SelectorItemCollection();
		sel.add("*");
		sel.add("measureUnit.*");
		view.setSelector(sel);
		ApportionTypeCollection coll =  ApportionTypeFactory.getRemoteInstance().getApportionTypeCollection(view);
		CRMHelper.sortCollection(coll,NUMBER,true);
		for(int i = 0 ; i < coll.size(); i++){
			ApportionTypeInfo info = coll.get(i);
			IRow row = table.addRow();
			rowMap.put(info.getId().toString(), Integer.valueOf(row.getRowIndex()));
			row.setUserObject(info);
			row.getCell(ID).setValue(info.getId().toString());
			row.getCell(NUMBER).setValue(info.getNumber());
			row.getCell(NAME).setValue(info.getName());
			row.getCell(UNIT).setValue(info.getMeasureUnit()!=null?info.getMeasureUnit().getName():null);
			row.getCell(DES).setValue(info.getDescription());
			
		}
	}

	public void loadIndexValue() {
    	KDTable table = null;
    	if(curProColl != null){
    		for(int i = 0 ; i < curProColl.size(); i++){
    			 CurProjProductEntriesInfo info = curProColl.get(i);
    			 String productTypeId = info.getProductType().getId().toString();
				 table = (KDTable)tableMap.get(productTypeId);
				 ProjectIndexDataInfo projectIndexInfo  = (ProjectIndexDataInfo)editDataMap.get(productTypeId);
				 loadForIndex(table,projectIndexInfo);
    		}
    	}
    	
    	//������������
    	 table = (KDTable)tableMap.get("allProject");
    	 ProjectIndexDataInfo projectIndexInfo  = (ProjectIndexDataInfo)editDataMap.get("allProject");
		 loadForIndex(table,projectIndexInfo);
    }
	
	private void loadForIndex(KDTable table, ProjectIndexDataInfo projectIndexInfo){
		if(table ==null || projectIndexInfo ==null)return;
		 ProjectIndexDataEntryCollection projectEntryColl =  projectIndexInfo.getEntries();
		 IRow row =null;
		 for( int j = 0 ; j < projectEntryColl.size() ; j++){
			 ProjectIndexDataEntryInfo projectEntryInfo = projectEntryColl.get(j);
			 String apportionTypeId = projectEntryInfo.getApportionType().getId().toString();
			 if(rowMap.get(apportionTypeId) !=null){
				 row  = table.getRow(((Integer)rowMap.get(apportionTypeId)).intValue());
			 } else {
				continue;
			 }
			 row.getCell(INDEXVALUE).setValue(projectEntryInfo.getIndexValue());
			 row.getCell(REMARK).setValue(projectEntryInfo.getRemark());
		 }
	}
	
	protected boolean isDisplayAreaIndex() throws EASBizException, BOSException{
		//���������Ƿ���ʾ���ָ��
			boolean displayAreaIdx = true;
			HashMap param = FDCUtils.getDefaultFDCParam(null,null);
			if(param.get(FDCConstants.FDC_PARAM_PROJECTINDEX)!=null){
				displayAreaIdx = Boolean.valueOf(param.get(FDCConstants.FDC_PARAM_PROJECTINDEX).toString()).booleanValue();
			}
		return displayAreaIdx;
	}
}