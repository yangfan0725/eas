/**
 * output package name
 */
package com.kingdee.eas.fdc.schedule.client;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.KeyStroke;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeModel;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.table.ICell;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTDataRequestManager;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectBlock;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectManager;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent;
import com.kingdee.bos.ctrl.kdf.table.event.KDTSelectEvent;
import com.kingdee.bos.ctrl.kdf.table.util.KDTableUtil;
import com.kingdee.bos.ctrl.kdf.util.style.Styles.HorizontalAlignment;
import com.kingdee.bos.ctrl.swing.KDContainer;
import com.kingdee.bos.ctrl.swing.KDLayout;
import com.kingdee.bos.ctrl.swing.KDTreeView;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.framework.cache.ActionCache;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemCollection;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.ItemAction;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.base.attachment.common.AttachmentClientManager;
import com.kingdee.eas.base.attachment.common.AttachmentManagerFactory;
import com.kingdee.eas.base.permission.PermissionFactory;
import com.kingdee.eas.base.permission.UserInfo;
import com.kingdee.eas.basedata.org.FullOrgUnitFactory;
import com.kingdee.eas.basedata.org.FullOrgUnitInfo;
import com.kingdee.eas.basedata.org.OrgStructureInfo;
import com.kingdee.eas.basedata.org.OrgType;
import com.kingdee.eas.basedata.org.client.OrgViewUtils;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.fdc.basedata.FDCConstants;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.basedata.client.FDCClientHelper;
import com.kingdee.eas.fdc.basedata.client.FDCClientUtils;
import com.kingdee.eas.fdc.basedata.client.FDCColorConstants;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.basedata.client.ProjectTreeBuilder;
import com.kingdee.eas.fdc.contract.FDCUtils;
import com.kingdee.eas.fdc.schedule.FDCScheduleInfo;
import com.kingdee.eas.fdc.schedule.FDCScheduleTaskCollection;
import com.kingdee.eas.fdc.schedule.FDCScheduleTaskFactory;
import com.kingdee.eas.fdc.schedule.FDCScheduleTaskInfo;
import com.kingdee.eas.fdc.schedule.FDCScheduleTaskSynchronize;
import com.kingdee.eas.fdc.schedule.FDCWBSFactory;
import com.kingdee.eas.fdc.schedule.FDCWBSInfo;
import com.kingdee.eas.fdc.schedule.ScheduleCalendarHelper;
import com.kingdee.eas.fdc.schedule.ScheduleFacadeFactory;
import com.kingdee.eas.fdc.schedule.TaskLoadEntryCollection;
import com.kingdee.eas.fdc.schedule.TaskLoadEntryFactory;
import com.kingdee.eas.fdc.schedule.TaskLoadEntryInfo;
import com.kingdee.eas.fdc.schedule.framework.ScheduleStateEnum;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.framework.client.FrameWorkClientUtils;
import com.kingdee.eas.framework.client.tree.KDTreeNode;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.MsgBox;
import com.kingdee.jdbc.rowset.IRowSet;

/**
 * ��������������
 */
public class TaskLoadUI extends AbstractTaskLoadUI
{
    private static final Logger logger = CoreUIObject
			.getLogger(TaskLoadUI.class);

    private Map taskMap = new HashMap();
    private Map addMap = new HashMap();
	protected FullOrgUnitInfo orgUnit = null;

	// ��ȡ��Ȩ�޵���֯
	protected Set authorizedOrgs = null;

	private JButton btnConfirm = null;

	private JButton btnCancel = null;
	
	private JButton btnAdd = null;
	private JButton btnDelete = null;
	// �Ƿ�ȷ�Ϲ�����
	private boolean isConfirm = false;
	
	private boolean isModified = false;
    /**
     * output class constructor
     */
    public TaskLoadUI() throws Exception
    {
        super();
    }

    /**
     * output storeFields method
     */
    public void storeFields()
    {
        super.storeFields();
    }

    /**
	 * output tblMain_tableSelectChanged method
	 */
	protected void tblMain_tableSelectChanged(
			KDTSelectEvent e)
			throws Exception {
		if(!isHasBillTable()) {
			super.tblMain_tableSelectChanged(e);
			return;
		}
		if(e.getSelectBlock()==null) return;
		getBillListTable().removeRows(false);
		
		EntityViewInfo view = genBillQueryView(e);
		if(!displayBillByScheduleTask(e, view) ){
			displayBillByScheduleTask(view);
		}
		if(getBillListTable()!=null&&getBillListTable().getRowCount()>0){
			getBillListTable().getSelectManager().select(0,0);
		}
		super.tblMain_tableSelectChanged(e);
	}
	
	/**
     * 
     * ����������ѡ��ĺ�ͬ��ʾ�����б�
     * @param e
     * @throws BOSException
     * @author:liupd
     * ����ʱ�䣺2006-8-2 <p>
     */
	protected void displayBillByScheduleTask(EntityViewInfo view) throws BOSException {
//		getFirstTable().setRefresh(false);
		getFirstTable().removeRows();
//		getSecondTable().setRefresh(false);
		getSecondTable().removeRows();
		FDCWBSInfo wbsInfo = getFDCWBSInfo();
    	if(wbsInfo==null){
    		return;
    	}
    	FDCScheduleTaskInfo schTaskInfo = (FDCScheduleTaskInfo)taskMap.get(wbsInfo.getId().toString());
    	if(schTaskInfo!=null&&schTaskInfo.getSchedule()!=null&&ScheduleStateEnum.CLOSED.equals(schTaskInfo.getSchedule().getState())){
    		if(isConfirm){
    			btnConfirm.setEnabled(false);
    		}else{
    			btnAdd.setEnabled(false);
    			btnDelete.setEnabled(false);
    			btnSave.setEnabled(false);
    		}
    	}else{
    		if(isConfirm){
    			btnConfirm.setEnabled(true);
    		}else{
    			btnAdd.setEnabled(true);
    			btnDelete.setEnabled(true);
    			btnSave.setEnabled(true);
    		}
    	}
    	
		view = getTaskLoadEntryView(wbsInfo);
		TaskLoadEntryCollection taskLoadEntrys = TaskLoadEntryFactory.getRemoteInstance().getTaskLoadEntryCollection(view);
		
		for(Iterator iter=taskLoadEntrys.iterator();iter.hasNext();){
			TaskLoadEntryInfo entryInfo = (TaskLoadEntryInfo)iter.next();
			IRow row = null;
			if(entryInfo.isIsConfirm()){
				row = getFirstTable().addRow();
			}else{
				row = getSecondTable().addRow();
			}
			setRowValue(row, entryInfo);
//			if(isConfirm){
//				row = getSecondTable().addRow();
//				setRowValue(row, entryInfo);
//			}
		}
	}
	
	protected boolean  displayBillByScheduleTask(KDTSelectEvent e,EntityViewInfo view) throws BOSException {
		return false;
	}
	
	/**
	 * 
	 * ���������ɲ�ѯ���ݵ�EntityViewInfo
	 * @param e
	 * @return
	 * @author:liupd
	 * ����ʱ�䣺2006-8-2 <p>
	 */
	protected EntityViewInfo genBillQueryView(com.kingdee.bos.ctrl.kdf.table.event.KDTSelectEvent e) {
		KDTSelectBlock selectBlock = e.getSelectBlock();
    	int top = selectBlock.getTop();
    	if(getMainTable().getCell(top, getKeyFieldName())==null){
    		return null;
    	}
    	
    	String wbsId = (String)getMainTable().getCell(top, getKeyFieldName()).getValue();
    	String projectId = null;
    	if(getProjSelectedTreeNode()!=null&&getProjSelectedTreeNode().getUserObject() instanceof CurProjectInfo){
    		
    		projectId=((CurProjectInfo)getProjSelectedTreeNode().getUserObject()).getId().toString();
    	}
    	EntityViewInfo view = new EntityViewInfo();
    	FilterInfo filter = new FilterInfo();
    	filter.getFilterItems().add(new FilterItemInfo("wbs.id", wbsId));
//    	filter.getFilterItems().add(new FilterItemInfo("wbs.curProject.id", projectId));
    	view.setFilter(filter);
    	SelectorItemCollection selectors = genBillQuerySelector();
    	if(selectors != null && selectors.size() > 0) {
    		for (Iterator iter = selectors.iterator(); iter.hasNext();) {
				SelectorItemInfo element = (SelectorItemInfo) iter.next();
				view.getSelector().add(element);
			}
    	}
		return view;
	}
	
	private EntityViewInfo getTaskLoadEntryView(FDCWBSInfo wbsInfo){
		
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		
		filter.getFilterItems().add(new FilterItemInfo("wbs.id", wbsInfo.getId().toString()));
		view.setFilter(filter);
		view.getSelector().add("*");
		view.getSelector().add("wbs.number");
		view.getSelector().add("wbs.name");
		view.getSelector().add("creator.name");
		view.getSelector().add("creator.number");
		view.getSelector().add("confirmer.name");
		view.getSelector().add("confirmer.number");
		return view;
	}
	private String getMainTableKeyFieldName(){
		return "wbs.id";
	}
	protected String getKeyFieldName() {
		return "id";
	}
	
	protected  SelectorItemCollection genBillQuerySelector(){
		SelectorItemCollection selector = new SelectorItemCollection();
		
		return selector;
	}
	// ���ε�CU���˴���
	protected FilterInfo getDefaultFilterForTree() {
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(
				new FilterItemInfo("isEnabled", Boolean.TRUE));
		return filter;
	}
    /**
     * output tblMain_tableClicked method
     */
    protected void tblMain_tableClicked(com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e) throws Exception
    {
    	//TODO ��Ҫ�򿪽�������
//        super.tblMain_tableClicked(e);
    }


    /**
     * output menuItemImportData_actionPerformed method
     */
    protected void menuItemImportData_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
        super.menuItemImportData_actionPerformed(e);
    }

    protected void refresh(ActionEvent e) throws Exception {
    	super.refresh(e);
    	treeSelectChange();
    }
    /**
     * output treeMain_valueChanged method
     */
    protected void treeMain_valueChanged(javax.swing.event.TreeSelectionEvent e) throws Exception
    {
        super.treeMain_valueChanged(e);
        treeSelectChange();
    }
    public DefaultKingdeeTreeNode getProjSelectedTreeNode() {
		return (DefaultKingdeeTreeNode) treeMain
				.getLastSelectedPathComponent();
	}

    protected void treeSelectChange() throws Exception {
    	
    	if(!isSelectLeafPrj()){
    		tblMain.removeRows();
    		tbl1.removeRows();
    		tbl2.removeRows();
    		return;
    	}
		
//		mainQuery.setFilter(getTreeSelectFilter(project));
//		execQuery();
		
		fillMainTable();
		

		if(isHasBillTable()) {
			getBillListTable().removeRows(false);
		}	
		
		if (getMainTable().getRowCount() > 0) {
			getMainTable().getSelectManager().select(0, 0);
			btnAddNew.setEnabled(true);
		}else if(isHasBillTable()){
			/*
			 * û�к�ͬʱ�����������ε��� sxhong
			 */
			btnAddNew.setEnabled(false);
		}
	}
    
    /**
     * 
     * �������Ƿ�ʹ�õ���Table������ͬTable���Ƿ���Table
     * @return
     * @author:liupd
     * ����ʱ�䣺2006-8-26 <p>
     */
    protected boolean isHasBillTable() {
    	return true;
    }

    
    /**
	 * ѡ�񹤳���Ŀ�ڵ�ͺ�ͬ���ͽڵ���ѡ���¼�
	 * @return
	 * @throws Exception
	 */
	protected FilterInfo getFDCWBSFilter(Object projectNode) throws Exception {
		FilterInfo filter = new FilterInfo();
		FilterItemCollection filterItems = filter.getFilterItems();
		if (projectNode != null && projectNode instanceof CoreBaseInfo) {

			CoreBaseInfo projTreeNodeInfo = (CoreBaseInfo) projectNode;
			BOSUuid id = null;
			if (projTreeNodeInfo instanceof CurProjectInfo
					&& ((CurProjectInfo) projTreeNodeInfo).isIsLeaf()) {
				id = projTreeNodeInfo.getId();
				filterItems.add(new FilterItemInfo("curProject.id", id));
			}
		}
		filterItems.add(new FilterItemInfo("id","select fwbsid from t_sch_fdcscheduletask",CompareType.INNER));
		return filter;
	}
	/**
	 * ѡ�񹤳���Ŀ�ڵ�ͺ�ͬ���ͽڵ���ѡ���¼�
	 * @return
	 * @throws Exception
	 */
	protected FilterInfo getTaskLoadEntryFilter(Object projectNode) throws Exception {
		FilterInfo filter = new FilterInfo();
		FilterItemCollection filterItems = filter.getFilterItems();
		if (projectNode != null && projectNode instanceof CoreBaseInfo) {

			CoreBaseInfo projTreeNodeInfo = (CoreBaseInfo) projectNode;
			String id = null;
			if (projTreeNodeInfo instanceof CurProjectInfo
					&& ((CurProjectInfo) projTreeNodeInfo).isIsLeaf()) {
				id = projTreeNodeInfo.getId().toString();
				filterItems.add(new FilterItemInfo("wbs.curProject.id", id));
				
			}
		}

		return filter;
	}
	
	private String selectKeyValue = null;
	private int selectIndex = -1;
	 
	/**
	 * ��ȡ��ǰѡ���е�����
	 * 
	 * @return ���ص�ǰѡ���е�����������ǰѡ����Ϊ�գ����ߵ�ǰѡ���е�������Ϊ�գ��򷵻�null
	 */
	protected String getSelectedKeyValue(KDTable table) {
		//String value = super.getSelectedKeyValue();
		
        int[] selectRows = KDTableUtil.getSelectedRows(this.tblMain);
        selectIndex=-1;
        if (selectRows.length > 0)
        {
        	selectIndex=selectRows[0];
        }
		
	    KDTSelectBlock selectBlock = table.getSelectManager().get();
	    selectKeyValue = null;
	
	    if (selectBlock != null) {
	        int rowIndex = selectBlock.getTop();
	        IRow row = table.getRow(rowIndex);
	        if (row == null) 
	        {
	            return null;
	        }
	        
	        ICell cell = row.getCell(getKeyFieldName());
	
	        if (cell == null) {
	            MsgBox.showError(EASResource
	                    .getString(FrameWorkClientUtils.strResource
	                            + "Error_KeyField_Fail"));
	            SysUtil.abort();
	        }
	
	        Object keyValue = cell.getValue();
	
	        if (keyValue != null) {
	        	selectKeyValue = keyValue.toString();
	        }
	    }   
	    	
	    return selectKeyValue;
	}
	
	protected String getSelectedKeyValue() {
		return getSelectedKeyValue(getBillListTable());
	}

	public KDTreeNode getSelectedTreeNode()
    {
        return null;
    }

    /**
     * �����������������������Ƿ�ѡ��
     * �Բ鿴���޸ġ�ɾ������������Ҫ���ѡ�н���Ƿ�Ϊ������
     */
    public void checkTreeNodeSelected(ActionEvent e)
    {
        //
    }
    
	/**
	 * 
	 * ��������ȡ��ǰ���ݵ�Table���������ʵ�֣�
	 * @return
	 * @author:liupd
	 * ����ʱ�䣺2006-8-2 <p>
	 */
	protected KDTable getBillListTable() {
		if(tbl1.isFocusOwner()){
			return this.tbl1;
		}else if(tbl2.isFocusOwner()){
			return this.tbl2;
		}
		return tbl1;
	}

	/**
	 * 
	 * ��������鵱ǰ���ݵ�Table�Ƿ�ѡ����
	 * @author:liupd
	 * @see com.kingdee.eas.framework.client.ListUI#checkSelected()
	 */
	public void checkSelected() {
		checkSelected(getBillListTable());
	}

	/**
	 * 
	 * ��������鵱ǰ���ݵ�Table�Ƿ�ѡ����
	 * @author:liupd
	 * @see com.kingdee.eas.framework.client.ListUI#checkSelected()
	 */
	public void checkSelected(KDTable table) {
		if (table.getRowCount()==0 || table.getSelectManager().size() == 0) {
	        MsgBox.showWarning(this, EASResource
	                .getString(FrameWorkClientUtils.strResource
	                        + "Msg_MustSelected"));
	        SysUtil.abort();
	    }
	}
    /**
     * output actionSave_actionPerformed
     */
    public void actionSave_actionPerformed(ActionEvent e) throws Exception
    {
    	if (this.tblMain.getSelectManager().getActiveRowIndex() == -1) {
			return;
		}
    	this.verify();
    	FDCWBSInfo wbsInfo = getFDCWBSInfo();
    	FilterInfo filter = new FilterInfo();
    	filter.getFilterItems().add(new FilterItemInfo("wbs.id", wbsInfo.getId().toString()));
    	TaskLoadEntryFactory.getRemoteInstance().delete(filter);
    	TaskLoadEntryCollection taskLoadEntrys = getTaskLoadEntrys();
    	CoreBaseCollection entrys = (CoreBaseCollection)taskLoadEntrys.cast(CoreBaseCollection.class);
    	TaskLoadEntryFactory.getRemoteInstance().addnew(entrys);
//    	if(isConfirm){
    		ScheduleFacadeFactory.getRemoteInstance().reCalLoadFromTaskLoad(wbsInfo.getId().toString());
//    	}
    	// ˢ��
    	refresh(null);
		isModified = false;
		FDCMsgBox.showInfo("�����ɹ���");
    }

    /**
     * output actionAddRow_actionPerformed
     */
    public void actionAddRow_actionPerformed(ActionEvent e) throws Exception
    {
    	if(this.panelDown.getSelectedIndex()!=1){
    		return;
    	}
    	KDTable table = getSecondTable();
    	
    	int rowIndex = table.getSelectManager().getActiveRowIndex();
		if (rowIndex == -1) {
			rowIndex = table.getRowCount() - 1;
		}
		IRow row = null;
		if (rowIndex != 0) {
			row = table.addRow(rowIndex + 1);
		}else{
			row = table.addRow();
		}
		TaskLoadEntryInfo entryInfo = createNewTaskLoadEntryInfo(row);
		setRowValue(row, entryInfo);
		
    }
    private void addRow() {
    	if(this.panelDown.getSelectedIndex()!=1){
    		return;
    	}
    	KDTable table = getSecondTable();
    	
    	int rowIndex = table.getSelectManager().getActiveRowIndex();
    	FDCWBSInfo wbsInfo = getFDCWBSInfo();
    	if(wbsInfo==null||!wbsInfo.isIsLeaf()){
    		FDCMsgBox.showWarning("��ѡ����ϸWBS!");
    		this.abort();
    	}
		if (rowIndex == -1) {
			rowIndex = table.getRowCount() - 1;
		}
		IRow row = null;
		if (rowIndex != 0) {
			row = table.addRow(rowIndex + 1);
		}else{
			row = table.addRow();
		}
		TaskLoadEntryInfo entryInfo = createNewTaskLoadEntryInfo(row);
		setRowValue(row, entryInfo);
    }

    private void deleteRow(){
    	if (this.panelDown.getSelectedIndex() != 1) {
			return;
		}
    	FDCWBSInfo wbsInfo = getFDCWBSInfo();
    	if(wbsInfo==null || !wbsInfo.isIsLeaf()){
    		FDCMsgBox.showWarning("��ѡ����ϸWBS!");
    		this.abort();
    	}
		KDTable table = getSecondTable();
    	if (getSecondTable().getSelectManager().getActiveRowIndex() == -1) {
    		return;
    	}
		int rowIndex = table.getSelectManager().getActiveRowIndex();
		table.removeRow(rowIndex);
    }
    
    /**
     * output actionDeleteRow_actionPerformed
     */
    public void actionDeleteRow_actionPerformed(ActionEvent e) throws Exception {
		if (this.panelDown.getSelectedIndex() != 1) {
			return;
		}
		KDTable table = getSecondTable();
    	if (getSecondTable().getSelectManager().getActiveRowIndex() == -1) {
    		return;
    	}
		int rowIndex = table.getSelectManager().getActiveRowIndex();
		table.removeRow(rowIndex);
		
	}
    
    public void actionEntryAttachment_actionPerformed(ActionEvent e)
    		throws Exception {
    	checkSelected();
    	AttachmentClientManager acm = AttachmentManagerFactory.getClientManager();
    	String boID = this.getSelectedKeyValue();
    	boolean isEdit = false;
    	Object value = getBillListTable().getRow(0).getCell("isConfirm").getValue();
    	if(value!=null){
    		isEdit = Boolean.valueOf(value.toString()).booleanValue();
    	}
    	acm.showAttachmentListUIByBoID(boID, this, true); // boID �� �븽�������� ҵ������ ID
    }
    /**
     * output tbl1_tableSelectChanged method
     */
    protected void tbl1_tableSelectChanged(com.kingdee.bos.ctrl.kdf.table.event.KDTSelectEvent e) throws Exception
    {
    }

    /**
     * output tbl1_tableClicked method
     */
    protected void tbl1_tableClicked(com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e) throws Exception
    {
    }
    
    protected void tbl2_editStopped(KDTEditEvent e) throws Exception {
    	super.tbl2_editStopped(e);
    	KDTable table = getSecondTable();
    	int rowIndex = e.getRowIndex();
    	int colIndex = e.getColIndex();
    	int mainIndex = tblMain.getSelectManager().getActiveRowIndex();
    	BigDecimal addLoad = (BigDecimal)tblMain.getRow(mainIndex).getCell("addLoad").getValue();
    	BigDecimal addPercent = (BigDecimal)tblMain.getRow(mainIndex).getCell("addPercent").getValue();
    	if(colIndex==tbl2.getColumnIndex("load")){
    		table.getCell(rowIndex, "addLoad").setValue(FDCHelper.add(addLoad, e.getValue()));
    	}else if(colIndex==tbl2.getColumnIndex("percent")){
    		table.getCell(rowIndex, "addPercent").setValue(FDCHelper.add(addPercent, e.getValue()));
    	}
    		
    	
    	
    	
    	isModified = true;
    }
    
    /**
     * output panelDown_stateChanged method
     */
    protected void panelDown_stateChanged(javax.swing.event.ChangeEvent e) throws Exception
    {
    	 String ctnName = this.panelDown.getSelectedComponent().getName();
    	 if("ctnName".equals(ctnName)){
    		 this.verify();
    		 btnSave.doClick();
    		 // tbl 
    	 }
    }

    /**
     * output panelDown_mouseClicked method
     */
    protected void panelDown_mouseClicked(java.awt.event.MouseEvent e) throws Exception
    {
    }

	protected ICoreBase getBizInterface() throws Exception {
		return TaskLoadEntryFactory.getRemoteInstance();
	}

    private void registerSynchronizeTaskKey(final CoreUIObject uiObject) {
		String actionName = "TaskSynchronizeSQLExecute";
		this.getActionMap().put(actionName, new javax.swing.AbstractAction() {
			public void actionPerformed(ActionEvent e) {
				int ret = FDCMsgBox.showConfirm2(uiObject,
						"����Ҫͬ�������е���ɰٷֱȺ����ʱ����Ϣ, ����Ҫ������ʱ�䣬ȷ�����ڿ�ʼ��");
				if (ret == FDCMsgBox.OK) {
					try {
						FDCScheduleTaskSynchronize.modifyByTaskLoad(null, null);
						FDCMsgBox.showInfo(uiObject, "��ϲ����ͬ��������ɡ� :)");
					} catch (BOSException e1) {
						uiObject.handUIException(e1);
					}
				}
			}
		});
		this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
				KeyStroke.getKeyStroke("ctrl shift F12"), actionName);
	}
    
	public void onLoad() throws Exception {
		boolean isStrict = FDCUtils.getDefaultFDCParamByKey(null, SysContext
				.getSysContext().getCurrentFIUnit().getId().toString(),
				FDCConstants.FDCSCH_PARAM_ISFILLBILLCONTROLSTRICT);
		boolean isBaseOnTask = FDCUtils.getDefaultFDCParamByKey(null, SysContext
				.getSysContext().getCurrentFIUnit().getId().toString(),
				FDCConstants.FDCSCH_PARAM_BASEONTASK);
		logger.info(SysContext.getSysContext().getCurrentFIUnit().getName()+isStrict);
		logger.info(SysContext.getSysContext().getCurrentFIUnit().getName()+isBaseOnTask);
//		if(isStrict){
//			FDCMsgBox.showWarning("�����á��Ƿ����á����ں�ͬ������������ϸ����ģʽ���������˹��ܲ�����");
//			this.abort();
//		}else if (isBaseOnTask){
//			FDCMsgBox.showWarning("�����á��Ƿ����á���������������������ϸ����ģʽ���������˹��ܲ�����");
//			this.abort();
//		}
		super.onLoad();
		this.menuView.setVisible(false);
		this.menuView.setEnabled(false);
		this.menuBiz.setVisible(false);
		this.menuBiz.setEnabled(false);
		this.menuWorkFlow.setVisible(false);
		this.menuWorkFlow.setEnabled(false);
		
		FDCClientHelper.setActionEnable(new ItemAction[] { actionAddNew,
				actionEdit, actionLocate, actionQuery,actionCopyTo,
				actionAttachment, actionPrint, actionPrintPreview,
				actionRemove, actionView, actionCancel, actionCancelCancel,
				actionTraceDown, actionTraceUp ,actionWorkFlowG,actionCreateTo,actionAuditResult}, false);
		
		orgUnit = FullOrgUnitFactory.getRemoteInstance().getFullOrgUnitInfo(
				new ObjectUuidPK(SysContext.getSysContext().getCurrentOrgUnit()
						.getId()));
		
		//������Ŀ���Ĺ���
		buildProjectTree();
		treeMain.setShowsRootHandles(true);
		treeSelectChange();
		
		//����KDtreeView
		KDTreeView treeView=new KDTreeView();
		treeView.setTree(treeMain);
		treeView.setShowButton(true);
		treeView.setTitle("������Ŀ");
        pnlMain.add(treeView, "left");
		treeView.setShowControlPanel(true);
		
		
		Object obj = getUIContext().get(UIContext.UICLASSPARAM);
		if(obj!=null&&obj.equals("isConfirm")){
			isConfirm = true;
		}
		actionAddRow.setEnabled(false);
		actionAddRow.setVisible(false);
		actionDeleteRow.setEnabled(false);
		actionDeleteRow.setVisible(false);
		this.menuEdit.setVisible(false);
		this.menuEdit.setEnabled(false);
		if(isConfirm){
			setUITitle("������ȷ��");
			
			initCtn(ctnSecond,true);
			initCtn(ctnFirst,false);
		}else{
			initCtn(ctnSecond);
		}
		panelDown.setSelectedIndex(1);
		tblMain.getDataRequestManager().setDataRequestMode(KDTDataRequestManager.REAL_MODE);
		tblMain.getSelectManager().setSelectMode(KDTSelectManager.ROW_SELECT);
		FDCHelper.formatTableNumber(tblMain, new String[]{"addPercent"});
		tblMain.getColumn("effectTimes").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
		tblMain.getColumn("natureTimes").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
		getSecondTable().getSelectManager().setSelectMode(KDTSelectManager.MULTIPLE_ROW_SELECT);
		initTable(tbl1);
		initTable(tbl2);
		registerSynchronizeTaskKey(this);
	}
	
	private void initTable(KDTable table){
		FDCHelper.formatTableNumber(table, new String[]{"load","percent","addLoad","addPercent"});

		// ��ʽ
		table.getColumn("percent").setEditor(FDCClientHelper.getNumberCellEditor());
		table.getColumn("load").setEditor(FDCClientHelper.getNumberCellEditor());
		
		table.getColumn("desc").setEditor(FDCClientHelper.getKDTextFieldCellEditor());
		table.getColumn("completeDate").setEditor(FDCClientHelper.getDateCellEditor());
		FDCHelper.formatTableDate(table, "completeDate");
		FDCHelper.formatTableDate(table, "confirmDate");
		
		// ��¼��
		table.getColumn("percent").setRequired(true);
		table.getColumn("load").setRequired(true);
		table.getColumn("completeDate").setRequired(true);
		table.getColumn("createDate").setRequired(true);
		table.getColumn("creator").setRequired(true);
		table.getColumn("isConfirm").setRequired(true);
		
		// ���ɱ༭
		table.getColumn("createDate").getStyleAttributes().setLocked(true);
		table.getColumn("creator").getStyleAttributes().setLocked(true);
		table.getColumn("isConfirm").getStyleAttributes().setLocked(true);
		table.getColumn("addPercent").getStyleAttributes().setLocked(true);
		table.getColumn("addLoad").getStyleAttributes().setLocked(true);
		if(!isConfirm){
			if("tbl1".equals(table.getName())){
				table.getColumn("isConfirm").getStyleAttributes().setHided(true);
				table.getColumn("confirmer").getStyleAttributes().setHided(true);
				table.getColumn("confirmDate").getStyleAttributes().setHided(true);
				table.getColumn("addPercent").getStyleAttributes().setHided(true);
				table.getColumn("addLoad").getStyleAttributes().setHided(true);	
				table.getColumn("desc").getStyleAttributes().setHided(true);
			}else{
				table.getColumn("confirmer").getStyleAttributes().setHided(true);
				table.getColumn("confirmDate").getStyleAttributes().setHided(true);
			}	
		}else{
			table.getStyleAttributes().setLocked(true);
		}
		if("tbl1".equals(table.getName())){
			table.getStyleAttributes().setLocked(true);
			table.getColumn("addLoad").getStyleAttributes().setHided(true);
			table.getColumn("addPercent").getStyleAttributes().setHided(true);
		}
	}
	
	protected void initWorkButton() {
		super.initWorkButton();
		this.btnSave.setIcon(EASResource.getIcon("imgTbtn_save"));
		this.btnAddRow.setIcon(EASResource.getIcon("imgTbtn_addline"));
		this.btnDeleteRow.setIcon(EASResource.getIcon("imgTbtn_deleteline"));
		this.btnEntryAttachment.setIcon(EASResource.getIcon("imgTbtn_affixmanage"));
		
		menuItemSave.setIcon(EASResource.getIcon("imgTbtn_save"));
		menuItemAddRow.setIcon(EASResource.getIcon("imgTbtn_addline"));
		menuItemDeleteRow.setIcon(EASResource.getIcon("imgTbtn_deleteline"));
		menuItemEntryAttachment.setIcon(EASResource.getIcon("imgTbtn_affixmanage"));
				
	}
	
	private KDTable getFirstTable(){
		return this.tbl1;
	}
	
	private KDTable getSecondTable(){
		return this.tbl2;
	}
	
	public void initCtn(KDContainer ctn){
			btnAdd = ctn.add(new ItemAction() {

				public void actionPerformed(ActionEvent arg0) {
					addRow();
				}

			});
			btnAdd.setIcon(EASResource.getIcon("imgTbtn_addline"));
			btnAdd.setSize(20, 50);
			btnAdd.setText("������");
			btnAdd.setPreferredSize(new Dimension(120, 20));
			btnAdd.setVisible(true);

			btnDelete = ctn.add(new ItemAction() {

				public void actionPerformed(ActionEvent arg0) {
					deleteRow();
				}
			});
			btnDelete.setIcon(EASResource.getIcon("imgTbtn_deleteline"));
			btnDelete.setSize(20, 50);
			btnDelete.setText("ɾ����");
			btnDelete.setPreferredSize(new Dimension(120, 20));
			btnDelete.setVisible(true);
	}
	
	public void initCtn(KDContainer ctn, boolean isUnConfirm){
		if (isUnConfirm) {
			btnConfirm = ctn.add(new ItemAction() {

				public void actionPerformed(ActionEvent arg0) {
					setConfirmStatus(getSecondTable(), true);
				}

			});
			btnConfirm.setIcon(EASResource.getIcon("imgTbtn_audit"));
			btnConfirm.setSize(20, 50);
			btnConfirm.setText("����ȷ��");
			btnConfirm.setPreferredSize(new Dimension(120, 20));
			btnConfirm.setVisible(true);

		} else {
			btnCancel = ctn.add(new ItemAction() {

				public void actionPerformed(ActionEvent arg0) {
					setConfirmStatus(getFirstTable(), false);
				}
			});
			btnCancel.setIcon(EASResource.getIcon("imgTbtn_unaudit"));
			btnCancel.setSize(20, 50);
			btnCancel.setText("������ȷ��");
			btnCancel.setPreferredSize(new Dimension(120, 20));
			btnCancel.setVisible(true);
		}
		
	}
	

	public void addLine(KDTable table) {

		if (table == null) {
			return;
		}
		IRow row = null;
		if (table.getSelectManager().size() > 0) {
			int top = table.getSelectManager().get().getTop();
			if (isTableColumnSelected(table)) {
				row = table.addRow();
			} else {
				row = table.addRow(top);
			}
		} else {
			row = table.addRow(0);
		}

	}

	public void removeLine(KDTable table){
		int index = table.getSelectManager().getActiveRowIndex();
		if(index == -1){
			index = index + 1;
		}
		
		Object value = table.getRow(index).getCell("conName").getValue();
		if(value == null){
			table.removeRow(index);
		}
		if(value != null && !value.toString().equals(FDCClientUtils.getRes("total"))){
			table.removeRow(index);
		}
	}
	
	protected final boolean isTableColumnSelected(KDTable table)
    {
        if (table.getSelectManager().size() > 0)
        {
            KDTSelectBlock block = table.getSelectManager().get();

            if ((block.getMode() == KDTSelectManager.COLUMN_SELECT)
                    || (block.getMode() == KDTSelectManager.TABLE_SELECT))
            {
                return true;
            }
        }

        return false;
    }
	
	private void buildProjectTree() throws Exception {

		ProjectTreeBuilder projectTreeBuilder = new ProjectTreeBuilder();

		SysContext.getSysContext().setCurrentOrgUnit(SysContext.getSysContext().getCurrentFIUnit());
		projectTreeBuilder.build(this, treeMain, actionOnLoad);
		authorizedOrgs = (Set)ActionCache.get("FDCBillListUIHandler.authorizedOrgs");
		if(authorizedOrgs==null){
			authorizedOrgs = new HashSet();
			Map orgs = PermissionFactory.getRemoteInstance().getAuthorizedOrgs(
					 new ObjectUuidPK(SysContext.getSysContext().getCurrentUserInfo().getId()),
			            OrgType.CostCenter, 
			            null,  null, null);
			if(orgs!=null){
				Set orgSet = orgs.keySet();
				Iterator it = orgSet.iterator();
				while(it.hasNext()){
					authorizedOrgs.add(it.next());
				}
			}		
		}
		
	}
	
	/**
	 * @author yong_zhou
	 * @return
	 */
	protected Set getSelectObjLeafIds() {
		Set idSet = new HashSet();
		DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) this.treeMain.getLastSelectedPathComponent();
		if (node == null || node.getUserObject() == null || OrgViewUtils.isTreeNodeDisable(node)) {
			return idSet;
		}
		getNodeIds(node,idSet);
		return idSet;
	}
	protected void getNodeIds(DefaultKingdeeTreeNode node,Set idSet){
		if (node.isLeaf()/* && node.getUserObject() instanceof CurProjectInfo*/){
//			CurProjectInfo projectInfo = (CurProjectInfo) node.getUserObject();
//			idSet.add(projectInfo.getId().toString());
			if(node.getUserObject() instanceof CurProjectInfo){
				CurProjectInfo projectInfo = (CurProjectInfo) node.getUserObject();
				idSet.add(projectInfo.getId().toString());
			}
			else if(node.getUserObject() instanceof FullOrgUnitInfo){
				FullOrgUnitInfo orgUnitInfo=(FullOrgUnitInfo)node.getUserObject();
				idSet.add(orgUnitInfo.getId().toString());
			}
			else if(node.getUserObject() instanceof OrgStructureInfo){
				OrgStructureInfo orgUnitInfo=(OrgStructureInfo)node.getUserObject();
				idSet.add(orgUnitInfo.getId().toString());
			}
		}else if(!node.isLeaf()){
			for(int i=0;i<node.getChildCount();i++){
				DefaultKingdeeTreeNode child = (DefaultKingdeeTreeNode)node.getChildAt(i);
				getNodeIds(child,idSet);
			}
		}
	}
	
	protected CurProjectInfo getSelectLeafProject() {
		
		DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeMain.getLastSelectedPathComponent();
		if (node == null || node.getUserObject() == null || OrgViewUtils.isTreeNodeDisable(node)) {
			return null;
		}
		if (node.getUserObject() instanceof CurProjectInfo) {
			CurProjectInfo projectInfo = (CurProjectInfo) node.getUserObject();
			return projectInfo.isIsLeaf() ? projectInfo : null;
		} else if (node.getUserObject() instanceof OrgStructureInfo) {
			return null;
		}
		return null;
	}

	/**
	 * 
	 * @return
	 */
	protected boolean isSelectLeafPrj(){
		Object obj=getSelectLeafProject();
		if(obj!=null && obj instanceof CurProjectInfo &&((CurProjectInfo)obj).isIsLeaf()){
			return true;
		}
		return false;
	}
	
	private void initTaskMap(FilterInfo filter) throws Exception{
		taskMap.clear();
		EntityViewInfo view = new EntityViewInfo();
		//FilterInfo mfilter = new FilterInfo();
		//mfilter.getFilterItems().add(new FilterItemInfo("schedule.isLatestVer", Boolean.TRUE,CompareType.EQUALS));
//		filter.mergeFilter(mfilter, "and");
//		Set state = new HashSet();
//		state.add(ScheduleStateEnum.EXETING);
//		state.add(ScheduleStateEnum.AUDITTED);
//		filter.getFilterItems().add(new FilterItemInfo("schedule.state", state, CompareType.INCLUDE));
		
		view.setFilter(filter);
		view.getSelector().add("wbs.id");
		view.getSelector().add("wbs.name");
		view.getSelector().add("wbs.level");
		view.getSelector().add("wbs.isLeaf");
		view.getSelector().add("wbs.longNumber");
		view.getSelector().add("effectTimes");
		view.getSelector().add("natureTimes");
		view.getSelector().add("actualStartDate");
		view.getSelector().add("start");
		view.getSelector().add("end");
		view.getSelector().add("adminDept.name");
		view.getSelector().add("respDept.name");
		view.getSelector().add("adminPerson.name");
		view.getSelector().add("id");
		view.getSelector().add("complete");
		view.getSelector().add("schedule.state");
		view.getSelector().add("schedule.calendar");
		view.getSelector().add("schedule.isLatestVer");
		view.getSorter().add(new SorterItemInfo("wbs.longNumber"));
		FDCScheduleTaskCollection schTasks = FDCScheduleTaskFactory.getRemoteInstance().getFDCScheduleTaskCollection(view);
		for(Iterator iter=schTasks.iterator();iter.hasNext();){
			FDCScheduleTaskInfo schTaskInfo = (FDCScheduleTaskInfo)iter.next();
			FDCWBSInfo wbsInfo = schTaskInfo.getWbs();
			FDCScheduleInfo schInfo = schTaskInfo.getSchedule();
			if(wbsInfo==null){
				continue;
			}
			String wbsID = wbsInfo.getId().toString();
			if(!schInfo.isIsLatestVer()) continue;
			schTaskInfo.setEffectTimes(ScheduleCalendarHelper.getEffectTimes(
					schTaskInfo.getStart(), schTaskInfo.getEnd(), schInfo
					.getCalendar()));
			taskMap.put(wbsID, schTaskInfo);
		}
	}
	private void initAddPercentMap() {
		addMap = new HashMap();
		Set prjIds = getSelectObjLeafIds();
		if(prjIds==null||prjIds.size()==0){
			return ;
		}
		FDCSQLBuilder builder = new FDCSQLBuilder();
		builder.appendSql("select wbs.fid,sum(tl.fpercent) fpercent,sum(tl.fload) fload from t_sch_taskloadentry tl ");
		builder.appendSql("inner join t_sch_fdcwbs wbs on wbs.fid=tl.fwbsid ");
		builder.appendSql("inner join t_fdc_curproject prj on prj.fid=wbs.fcurprojectid	");
		builder.appendSql("where ");
		builder.appendParam("prj.fid", prjIds.toArray());
		builder.appendSql(" and ");
		builder.appendParam("tl.fisconfirm", Boolean.valueOf(true));
		builder.appendSql("group by wbs.fid ");
		IRowSet rs;
		try {
			rs = builder.executeQuery();
			while(rs.next()){
				addMap.put("percent"+rs.getString("fid"), rs.getBigDecimal("fpercent")!=null?rs.getBigDecimal("fpercent"):FDCHelper.ZERO);
				addMap.put("load"+rs.getString("fid"), rs.getBigDecimal("fload")!=null?rs.getBigDecimal("fload"):FDCHelper.ZERO);
			}
		} catch (BOSException e) {
			handleException(e);
		} catch (SQLException e) {
			handleException(e);
		}
	}
	
	public void initUIContentLayout()
    {

        this.setBounds(new Rectangle(10, 10, 1013, 629));
        this.setLayout(new KDLayout());
        this.putClientProperty("OriginalBounds", new Rectangle(10, 10, 1013, 629));
        pnlMain.setBounds(new Rectangle(10, 10, 993, 609));
        this.add(pnlMain, new KDLayout.Constraints(10, 10, 993, 609, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        //pnlMain
        pnlMain.add(pnlLeftTree, "left");
        pnlMain.add(pnlRight, "right");
        //pnlLeftTree
        pnlLeftTree.setLayout(new KDLayout());
        //TODO ���ڸ���������KDLayout���֣���������һ������������������ĳ�ʼ��С��
        pnlLeftTree.putClientProperty("OriginalBounds", new Rectangle(0,0,240,609));        ctnTree.setBounds(new Rectangle(0, 0, 240, 609));
        pnlLeftTree.add(ctnTree, new KDLayout.Constraints(0, 0, 240, 609, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        //ctnTree
        ctnTree.getContentPane().setLayout(new KDLayout());
        ctnTree.getContentPane().putClientProperty("OriginalBounds", new Rectangle(0, 0, 240, 609));        treeMain.setBounds(new Rectangle(0, 0, 240, 608));
        ctnTree.getContentPane().add(treeMain, new KDLayout.Constraints(0, 0, 240, 608, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        //pnlRight
        pnlRight.setLayout(new KDLayout());
        //TODO ���ڸ���������KDLayout���֣���������һ������������������ĳ�ʼ��С��
        pnlRight.putClientProperty("OriginalBounds", new Rectangle(0,0,733,608));        kDSplitPane1.setBounds(new Rectangle(0, 1, 733, 608));
        pnlRight.add(kDSplitPane1, new KDLayout.Constraints(0, 1, 733, 608, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        //kDSplitPane1
        kDSplitPane1.add(ctnScheduleTask, "top");
        kDSplitPane1.add(panelDown, "bottom");
        //ctnScheduleTask
        ctnScheduleTask.getContentPane().setLayout(new KDLayout());
        //TODO ���ڸ���������KDLayout���֣���������һ������������������ĳ�ʼ��С��
        ctnScheduleTask.getContentPane().putClientProperty("OriginalBounds", new Rectangle(0,0,733,281));        tblMain.setBounds(new Rectangle(0, 0, 733, 281));
        ctnScheduleTask.getContentPane().add(tblMain, new KDLayout.Constraints(0, 0, 733, 281, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_BOTTOM_SCALE | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        //panelDown
        panelDown.add(ctnFirst, resHelper.getString("ctnFirst.constraints"));
        panelDown.add(ctnSecond, resHelper.getString("ctnSecond.constraints"));
        //ctnFirst
        ctnFirst.getContentPane().setLayout(new KDLayout());
        //TODO ���ڸ���������KDLayout���֣���������һ������������������ĳ�ʼ��С��
        ctnFirst.getContentPane().putClientProperty("OriginalBounds", new Rectangle(0,0,729,250));        tbl1.setBounds(new Rectangle(0, 0, 729, 250));
        ctnFirst.getContentPane().add(tbl1, new KDLayout.Constraints(0, 0, 729, 250, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        //ctnSecond
        ctnSecond.getContentPane().setLayout(new KDLayout());
        //TODO ���ڸ���������KDLayout���֣���������һ������������������ĳ�ʼ��С��
        ctnSecond.getContentPane().putClientProperty("OriginalBounds", new Rectangle(0,0,729,250));        tbl2.setBounds(new Rectangle(0, 0, 729, 250));
        ctnSecond.getContentPane().add(tbl2, new KDLayout.Constraints(0, 0, 729, 250, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
    }
	
	private void setRowValue(IRow row, TaskLoadEntryInfo entryInfo){
		if(entryInfo.getId()!=null){
			row.getCell("id").setValue(entryInfo.getId().toString());
		}
		row.getCell("percent").setValue(entryInfo.getPercent());
		row.getCell("load").setValue(entryInfo.getLoad());
		row.getCell("completeDate").setValue(entryInfo.getCompleteDate());
		row.getCell("creator").setUserObject(entryInfo.getCreator());
		if(entryInfo.getCreator()!=null){
			row.getCell("creator").setValue(entryInfo.getCreator().getName());
		}
		row.getCell("isConfirm").setValue(Boolean.valueOf(entryInfo.isIsConfirm()));
		row.getCell("createDate").setValue(entryInfo.getCreateDate());
		row.getCell("confirmer").setUserObject(entryInfo.getConfirmer());
		if(entryInfo.getConfirmer()!=null){
			row.getCell("confirmer").setValue(entryInfo.getConfirmer().getName());
		}
		row.getCell("confirmDate").setValue(entryInfo.getConfirmDate());
		row.getCell("desc").setValue(entryInfo.getDesc());
		int rowIndex = tblMain.getSelectManager().getActiveRowIndex();
		BigDecimal addLoad = (BigDecimal)tblMain.getCell(rowIndex, "addLoad").getValue();
		BigDecimal addPercent = (BigDecimal)tblMain.getCell(rowIndex, "addPercent").getValue();
		
		row.getCell("addLoad").setValue(FDCHelper.add(addLoad, entryInfo.getLoad()));
		row.getCell("addPercent").setValue(FDCHelper.add(addPercent, entryInfo.getPercent()));
		row.getCell("objectId").setValue(entryInfo.getObjectId());
		row.setUserObject(entryInfo);
	}
	
	private TaskLoadEntryInfo createNewTaskLoadEntryInfo(IRow row){
		TaskLoadEntryInfo entryInfo = new TaskLoadEntryInfo();
		// ����Ĭ��ֵ
		Date createDate = new Date();
		entryInfo.setWbs(getFDCWBSInfo());
		entryInfo.setCreateDate(createDate);
		entryInfo.setCompleteDate(createDate);//
		entryInfo.setCreator(SysContext.getSysContext().getCurrentUserInfo());
		entryInfo.setIsConfirm(false);
		
		return entryInfo;
	}
	
	private FDCWBSInfo getFDCWBSInfo(){
		if (this.tblMain.getSelectManager().getActiveRowIndex() == -1) {
			return null;
		}
		int rowIndex = tblMain.getSelectManager().getActiveRowIndex();
		IRow row = tblMain.getRow(rowIndex);
		FDCWBSInfo wbsInfo = (FDCWBSInfo)row.getCell("wbsNumber").getUserObject();
		return wbsInfo;
	}
	
	private TaskLoadEntryCollection getTaskLoadEntrys(){
		TaskLoadEntryCollection taskLoadEntrys = new TaskLoadEntryCollection();
		FDCWBSInfo wbsInfo = getFDCWBSInfo();//  
		getTableDatas(getSecondTable(),taskLoadEntrys, wbsInfo);
		//¼�뵥ʱ������������ȷ�ϵ�ʱ�ڶ����������������
//		if(isConfirm){
//			return taskLoadEntrys;
//		}
		getTableDatas(getFirstTable(),taskLoadEntrys, wbsInfo);
		return taskLoadEntrys;
	}

	private void getTableDatas(KDTable table, TaskLoadEntryCollection taskLoadEntrys,
			FDCWBSInfo wbsInfo) {
		for(int i=0;i<table.getRowCount();i++){
			IRow row = table.getRow(i);
			TaskLoadEntryInfo object = (TaskLoadEntryInfo)row.getUserObject();
			if(object!=null){
				TaskLoadEntryInfo entryInfo = (TaskLoadEntryInfo)object.clone();
				//wbs
				entryInfo.setWbs(wbsInfo);
				// �����깤�ٷֱ�
				entryInfo.setPercent((BigDecimal)row.getCell("percent").getValue());
				// �����깤������
				entryInfo.setLoad((BigDecimal)row.getCell("load").getValue());
				// �깤����
				entryInfo.setCompleteDate((Date)row.getCell("completeDate").getValue());
				// ¼����
				entryInfo.setCreator((UserInfo)row.getCell("creator").getUserObject());
				// ¼������
				entryInfo.setCreateDate((Date)row.getCell("createDate").getValue());
				// ȷ������
				entryInfo.setConfirmDate((Date)row.getCell("confirmDate").getValue());
				// ȷ����
				entryInfo.setConfirmer((UserInfo)row.getCell("confirmer").getUserObject());
				// ȷ��״̬
				entryInfo.setIsConfirm(((Boolean)row.getCell("isConfirm").getValue()).booleanValue());
				// ��ע
				entryInfo.setDesc((String)row.getCell("desc").getValue());
				// ����id
				entryInfo.setObjectId((String)row.getCell("objectId").getValue());
				taskLoadEntrys.add(entryInfo);
			}
		}
	}
	
	private void setConfirmStatus(KDTable table, boolean isConfirm){
		if(table.getSelectManager().size()==0){
			return;
		}
		for(int i=0;i<table.getSelectManager().size();i++){
			KDTSelectBlock block = table.getSelectManager().get(i);
			for (int rowIndex = block.getBeginRow(); rowIndex <= block.getEndRow(); rowIndex++)
			{
				IRow row = table.getRow(rowIndex);
				if(isConfirm){
					row.getCell("confirmer").setUserObject(SysContext.getSysContext().getCurrentUserInfo());
					row.getCell("confirmer").setValue(SysContext.getSysContext().getCurrentUserInfo().getName());
					row.getCell("confirmDate").setValue(new Date());
					row.getCell("isConfirm").setValue(Boolean.valueOf(isConfirm));
				}else{
					String objectId = (String)row.getCell("objectId").getValue();
					if(objectId!=null){
						FDCMsgBox.showWarning("�ù������ѱ��������뵥���������ܷ�ȷ�ϣ�");
						this.abort();
					}
					row.getCell("confirmer").setUserObject(null);
					row.getCell("confirmer").setValue(null);
					row.getCell("confirmDate").setValue(null);
					row.getCell("isConfirm").setValue(Boolean.FALSE);
				}
			}
		}
		isModified = true;
	}
	private void verify(){
		int rowIndex = tblMain.getSelectManager().getActiveRowIndex();
		BigDecimal allPercent = FDCHelper.toBigDecimal(tblMain.getCell(rowIndex, "addPercent").getValue());
		for(int i=0;i<getSecondTable().getRowCount();i++){
			IRow row = getSecondTable().getRow(i);
			BigDecimal percent = (BigDecimal)row.getCell("percent").getValue();
			if(FDCHelper.toBigDecimal(percent).compareTo(FDCHelper.ZERO)<=0){
				FDCMsgBox.showWarning("�� "+(i+1)+" ���깤�ٷֱȲ���Ϊ�ջ�С�ڵ�����!");
				this.abort();
			}
			Date completeDate = (Date)row.getCell("completeDate").getValue();
			if(completeDate==null){
				FDCMsgBox.showWarning("�� "+(i+1)+" �� �깤���� Ϊ��!");
				this.abort();
			}
			//������¼��:ʵ�����ʱ�䲻С��ʵ�ʿ�ʼʱ��
			FDCWBSInfo wbsInfo = getFDCWBSInfo();
			FDCScheduleTaskInfo schTaskInfo = (FDCScheduleTaskInfo)taskMap.get(wbsInfo.getId().toString());
			if(schTaskInfo!=null){
				if(schTaskInfo.getActualStartDate()!=null){
					if(((Date)row.getCell("completeDate").getValue()).before(schTaskInfo.getActualStartDate())){
						FDCMsgBox.showWarning("�� "+(i+1)+" �� �깤���� ����С��ʵ�ʿ�ʼ����!");
						this.abort();
					}
				}else if(schTaskInfo.getStart()!=null){
					if(((Date)row.getCell("completeDate").getValue()).before(schTaskInfo.getStart())){
						FDCMsgBox.showWarning("�� "+(i+1)+" �� �깤���� ����С��ʵ�ʿ�ʼ����!");
						this.abort();
					}
				}
			}
			allPercent = FDCHelper.add(allPercent,percent);
			if(allPercent.compareTo(FDCHelper.ONE_HUNDRED)==1){
				FDCMsgBox.showWarning("��ȷ�ϵ��깤�ٷֱ�+����ȷ�ϰٷֱȲ��ܴ���100");
				this.abort();
			}
			BigDecimal load = (BigDecimal)row.getCell("load").getValue();
			if(load == null || FDCHelper.toBigDecimal(load).compareTo(FDCHelper.ZERO) < 0){
				FDCMsgBox.showWarning("�� "+(i+1)+" ���깤����������Ϊ�ջ�С����!");
				this.abort();
			}
		}
	}
	public boolean destroyWindow() {
		if(isModified){
			int isOk = FDCMsgBox.showConfirm2("�������޸ģ��Ƿ񱣴沢�˳���");
			if (isOk == MsgBox.CANCEL) {
				return true;
			} else if (isOk == MsgBox.YES) {
				btnSave.doClick();
			}
		}
		return super.destroyWindow();
	}
	private void fillMainTable() throws Exception {
		tblMain.removeRows();
		tbl1.removeRows();
		tbl2.removeRows();
		DefaultKingdeeTreeNode projectNode  = getProjSelectedTreeNode();
		Object project  = null;
		if(projectNode!=null){
			project = projectNode.getUserObject();
		}
		initTaskMap(getTaskLoadEntryFilter(project));
		initAddPercentMap();
		
		TreeModel wbsTree = FDCClientHelper.createDataTree(
				FDCWBSFactory.getRemoteInstance(), getFDCWBSFilter(project));
		DefaultKingdeeTreeNode root = (DefaultKingdeeTreeNode) wbsTree
				.getRoot();
		int maxLevel=root.getDepth();
		this.tblMain.getTreeColumn().setDepth(maxLevel);
		// ʹ���µĽӿ�
		for (int i = 0; i < root.getChildCount(); i++) {
			fillNode((DefaultMutableTreeNode) root.getChildAt(i));
		}
		
//		setUnionData();
//		setUnionData();
	}
	private void fillNode(DefaultMutableTreeNode node) throws BOSException,
	SQLException, EASBizException {
		FDCWBSInfo wbsInfo = (FDCWBSInfo) node.getUserObject();
		if (wbsInfo == null) {
			return;
		}
		IRow row = tblMain.addRow();
		row.setTreeLevel(node.getLevel() - 1);
		row.getCell("wbsNumber").setUserObject(wbsInfo);
		row.getCell("wbsNumber").setValue(
				wbsInfo.getLongNumber().replace('!', '.'));
		row.getCell("wbsName").setValue(wbsInfo.getName());
		
		FDCScheduleTaskInfo schTaskInfo = (FDCScheduleTaskInfo)taskMap.get(wbsInfo.getId().toString());
		if(schTaskInfo==null){
			return;
		}
		row.getCell("start").setValue(schTaskInfo.getStart());
		row.getCell("end").setValue(schTaskInfo.getEnd());
		row.getCell("effectTimes").setValue(schTaskInfo.getEffectTimes());
		row.getCell("natureTimes").setValue(schTaskInfo.getNatureTimes());
		if(schTaskInfo.getAdminDept()!=null){
			row.getCell("adminDept").setValue(schTaskInfo.getAdminDept().getName());
		}
		if(schTaskInfo.getAdminPerson()!=null){
			row.getCell("adminPerson").setValue(schTaskInfo.getAdminPerson().getName());
		}
		String key = wbsInfo.getId().toString();
//		if(addMap.containsKey("percent"+key)){
//			BigDecimal addPercent = FDCHelper.toBigDecimal(addMap.get("percent"+key));
//			row.getCell("addPercent").setValue(addPercent);
		row.getCell("addPercent").setValue(schTaskInfo.getComplete());
//		}
//		if(addMap.containsKey("load"+key)){
			BigDecimal addLoad = FDCHelper.toBigDecimal(addMap.get("load"+key));
			row.getCell("addLoad").setValue(addLoad);
//		}
		if(wbsInfo.isIsLeaf()){
			row.setUserObject(wbsInfo);
			row.getStyleAttributes().setBackground(FDCColorConstants.totalColor);
		}
		if (!wbsInfo.isIsLeaf()) {
			row.getStyleAttributes().setBackground(new Color(0xF0EDD9));
		}
		for (int i = 0; i < node.getChildCount(); i++) {
			this.fillNode((DefaultMutableTreeNode) node.getChildAt(i));
		}
	}
	
	private void setUnionData() {
		KDTable table = this.tblMain;
		List zeroLeverRowList = new ArrayList();
		List columns = new ArrayList();
		// amountColumns.add("addLoad");
		columns.add("addPercent");
		for (int i = 0; i < table.getRowCount(); i++) {
			IRow row = table.getRow(i);
			// ����
			if (row.getTreeLevel() == 0) {
				zeroLeverRowList.add(row);
			}
			// ����ϸ��
			if (row.getUserObject() == null) {
				// ���û�����
				int level = row.getTreeLevel();
				List rowList = new ArrayList();
				// ����ϸ���¼���
				for (int j = i + 1; j < table.getRowCount(); j++) {
					IRow rowAfter = table.getRow(j);
					if(rowAfter.getTreeLevel()<=level){
						break;
					}
					if (rowAfter.getTreeLevel()-1 == level) {
						rowList.add(rowAfter);
					}
				}
				for (int k = 0; k < columns.size(); k++) {
					String colName = (String) columns.get(k);
					BigDecimal perTimes = FDCHelper.ZERO;
					BigDecimal amount = FDCHelper.ZERO;
					BigDecimal addTimes = FDCHelper.ZERO;
					for (int rowIndex = 0; rowIndex < rowList.size(); rowIndex++) {
						IRow rowAdd = (IRow) rowList.get(rowIndex);
						addTimes = FDCHelper.add(addTimes,rowAdd.getCell("effectTimes").getValue());
						amount = FDCHelper.add(amount,FDCHelper.multiply(rowAdd.getCell("addPercent").getValue(), rowAdd.getCell("effectTimes").getValue()));
					}
					perTimes = FDCHelper.divide(amount, addTimes, 2,
							BigDecimal.ROUND_HALF_UP);
					row.getCell(colName).setValue(perTimes);
				}
				
			}
		}
	}
}