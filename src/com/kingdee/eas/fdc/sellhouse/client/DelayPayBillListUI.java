/**
 * output package name
 */
package com.kingdee.eas.fdc.sellhouse.client;

import java.awt.event.*;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.swing.event.TreeSelectionEvent;
import javax.swing.tree.TreeNode;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.metadata.IMetaDataPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.IUIFactory;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.bos.ctrl.extendcontrols.IDataFormat;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectManager;
import com.kingdee.bos.ctrl.kdf.util.render.ObjectValueRender;
import com.kingdee.bos.ctrl.swing.KDWorkButton;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.dao.query.IQueryExecutor;
import com.kingdee.eas.base.permission.UserInfo;
import com.kingdee.eas.basedata.org.OrgStructureInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basecrm.client.FDCSysContext;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.MoneySysTypeEnum;
import com.kingdee.eas.fdc.basedata.client.FDCClientUtils;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.sellhouse.BuildingInfo;
import com.kingdee.eas.fdc.sellhouse.BuildingUnitInfo;
import com.kingdee.eas.fdc.sellhouse.IDelayPayBill;
import com.kingdee.eas.fdc.sellhouse.SHEManageHelper;
import com.kingdee.eas.fdc.sellhouse.SellProjectInfo;
import com.kingdee.eas.fdc.sellhouse.DelayPayBillCollection;
import com.kingdee.eas.fdc.sellhouse.DelayPayBillEntryFactory;
import com.kingdee.eas.fdc.sellhouse.DelayPayBillEntryInfo;
import com.kingdee.eas.fdc.sellhouse.DelayPayBillFactory;
import com.kingdee.eas.fdc.sellhouse.DelayPayBillInfo;
import com.kingdee.eas.framework.*;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.EASResource;

/**
 * output class name
 */
public class DelayPayBillListUI extends AbstractDelayPayBillListUI
{
    private static final Logger logger = CoreUIObject.getLogger(DelayPayBillListUI.class);
    protected SellProjectInfo sellProject = null;
    protected BuildingInfo building = null;
    protected BuildingUnitInfo buildUnit = null;
    protected boolean isSaleHouseOrg= FDCSysContext.getInstance().getOrgMap().containsKey(SysContext.getSysContext().getCurrentOrgUnit().getId().toString());
    protected UserInfo currentUserInfo = SysContext.getSysContext().getCurrentUserInfo();
    protected boolean isControl=SHEManageHelper.isControl(null, currentUserInfo);
    private static final String CANTEDIT = "cantEdit";
    protected Map permit=new HashMap();
    /**
     * output class constructor
     */
    public DelayPayBillListUI() throws Exception
    {
        super();
    }
    public void onLoad() throws Exception {
		super.onLoad();
		initTree();
		initControl();
	}
    
    protected void initTree() throws Exception
	{
    	this.kDTree.setModel(FDCTreeHelper.getUnitTreeForSHE(this.actionOnLoad,MoneySysTypeEnum.SalehouseSys));
    	this.kDTree.expandAllNodes(true, (TreeNode) this.kDTree.getModel().getRoot());
    }
    protected void initControl(){
        this.btnAudit.setIcon(EASResource.getIcon("imgTbtn_audit"));
        this.btnUnAudit.setIcon(EASResource.getIcon("imgTbtn_unaudit"));
    	this.tblMain.getSelectManager().setSelectMode(KDTSelectManager.MULTIPLE_ROW_SELECT);
    	this.kDTree.setSelectionRow(0);
    	this.actionTraceUp.setVisible(false);//�ϲ�
    	this.actionTraceDown.setVisible(false);//�²�
    	this.actionCreateTo.setVisible(false);//��������
    	this.actionAttachment.setVisible(false);
    }
	protected void kDTree_valueChanged(TreeSelectionEvent e) throws Exception {
		super.kDTree_valueChanged(e);
		DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) kDTree.getLastSelectedPathComponent();
		if (node == null) {
			return;
		}
		sellProject=null;
		building=null;
		buildUnit=null;
		if (node.getUserObject() instanceof SellProjectInfo){
			//��Ŀ
			if(node.getUserObject() != null){
				sellProject=(SellProjectInfo) node.getUserObject();
			}			
		}else if (node.getUserObject() instanceof BuildingInfo){ 
			// ¥��
			if(node.getUserObject() != null){
				building=(BuildingInfo)node.getUserObject();
				sellProject = building.getSellProject();
			}
		}else if (node.getUserObject() instanceof BuildingUnitInfo){ 
			// ��Ԫ
			if(node.getUserObject() != null){
				buildUnit=(BuildingUnitInfo)node.getUserObject();
				building=buildUnit.getBuilding();
				sellProject = buildUnit.getBuilding().getSellProject();
			}
		}
		
		if (node.getUserObject() instanceof OrgStructureInfo){
			this.actionAddNew.setEnabled(false);
		}else if(node.getUserObject() instanceof SellProjectInfo){
			if(SHEManageHelper.isSellProjectHasChild((SellProjectInfo) node.getUserObject())){
				this.actionAddNew.setEnabled(false);
			}else if(isSaleHouseOrg){
				this.actionAddNew.setEnabled(true);
			}
		}else{
			if(isSaleHouseOrg){
				this.actionAddNew.setEnabled(true);
			}
		}
		this.refresh(null);
	}
	protected IQueryExecutor getQueryExecutor(IMetaDataPK queryPK, EntityViewInfo viewInfo) {
		
		DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) kDTree.getLastSelectedPathComponent();
		try	{
			FilterInfo filter = new FilterInfo();
			if(node!=null){
				if (node.getUserObject() instanceof SellProjectInfo){
					//��Ŀ
					filter.getFilterItems().add(new FilterItemInfo("sellProject.id", SHEManageHelper.getAllSellProjectCollection(null,sellProject),CompareType.INCLUDE));	
				}else if (node.getUserObject() instanceof BuildingInfo){ 
					// ¥��
					filter.getFilterItems().add(new FilterItemInfo("sellProject.id", sellProject.getId().toString()));
					filter.getFilterItems().add(new FilterItemInfo("building.id", building.getId().toString()));
				}else if (node.getUserObject() instanceof BuildingUnitInfo){ 
					// ��Ԫ
					filter.getFilterItems().add(new FilterItemInfo("sellProject.id", sellProject.getId().toString()));
					filter.getFilterItems().add(new FilterItemInfo("buildUnit.id", buildUnit.getId().toString()));
					filter.getFilterItems().add(new FilterItemInfo("building.id", building.getId().toString()));
				} else if (node.getUserObject() instanceof OrgStructureInfo){
					//��֯
					filter.getFilterItems().add(new FilterItemInfo("sellProject.id", "'null'",CompareType.INNER));
				}
				if (!(node.getUserObject() instanceof OrgStructureInfo)&&!isControl){
					filter.getFilterItems().add(new FilterItemInfo("creator.id", SHEManageHelper.getPermitSaleManSet(sellProject,permit),CompareType.INCLUDE));
				}
			}
			viewInfo = (EntityViewInfo) this.mainQuery.clone();
			if (viewInfo.getFilter() != null)
			{
				viewInfo.getFilter().mergeFilter(filter, "and");
			} else
			{
				viewInfo.setFilter(filter);
			}
			
		}catch (Exception e)
		{
			handleException(e);
		}
		return super.getQueryExecutor(queryPK, viewInfo);
	}
	protected void refresh(ActionEvent e) throws Exception
	{
		this.tblMain.removeRows();
	}
    protected void prepareUIContext(UIContext uiContext, ActionEvent e)
	{
		super.prepareUIContext(uiContext, e);
		uiContext.put(UIContext.ID, getSelectedKeyValue());
		uiContext.put("sellProject", sellProject);
		uiContext.put("building", building);
		uiContext.put("buildUnit", buildUnit);
	}
    
	/* (non-Javadoc)
	 * @see com.kingdee.eas.fdc.sellhouse.client.AbstractDelayPayBillListUI#auditAction_actionPerformed(java.awt.event.ActionEvent)
	 */
	public void auditAction_actionPerformed(ActionEvent e) throws Exception {
		checkSelected();
		ArrayList id = getSelectedIdValues();
		for(int i = 0; i < id.size(); i++){
			FDCClientUtils.checkBillInWorkflow(this, id.get(i).toString());
	    	
			if (!FDCBillStateEnum.SUBMITTED.equals(getstate(id.get(i).toString()))) {
				FDCMsgBox.showWarning("���ݲ����ύ״̬�����ܽ�������������");
				return;
			}
			DelayPayBillFactory.getRemoteInstance().audit(BOSUuid.read(id.get(i).toString()));
		}
		FDCClientUtils.showOprtOK(this);
		this.refresh(null);
	}
	
    public FDCBillStateEnum getstate(String id) throws EASBizException, BOSException, Exception{
    	if(id==null) return null;
    	SelectorItemCollection sels =new SelectorItemCollection();
    	sels.add("state");
    	return ((DelayPayBillInfo)getBizInterface().getValue(new ObjectUuidPK(id),sels)).getState();
    }

	/* (non-Javadoc)
	 * @see com.kingdee.eas.fdc.sellhouse.client.AbstractDelayPayBillListUI#unAuditAction_actionPerformed(java.awt.event.ActionEvent)
	 */
	public void unAuditAction_actionPerformed(ActionEvent e) throws Exception {
		checkSelected();
		ArrayList id = getSelectedIdValues();
		for(int i = 0; i < id.size(); i++){
			FDCClientUtils.checkBillInWorkflow(this, id.get(i).toString());
	    	
			if (!FDCBillStateEnum.AUDITTED.equals(getstate(id.get(i).toString()))) {
				FDCMsgBox.showWarning("���ݲ�������״̬�����ܽ��з�����������");
				return;
			}
			DelayPayBillFactory.getRemoteInstance().unAudit(BOSUuid.read(id.get(i).toString()));
		}
		FDCClientUtils.showOprtOK(this);
		this.refresh(null);
	}

	/* (non-Javadoc)
	 * @see com.kingdee.eas.fdc.sellhouse.client.AbstractDelayPayBillListUI#actionRemove_actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionRemove_actionPerformed(ActionEvent e) throws Exception {
		checkBeforeEditOrRemove("CANREMOVE");
		super.actionRemove_actionPerformed(e);
	}

	/* (non-Javadoc)
	 * @see com.kingdee.eas.framework.client.ListUI#actionEdit_actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionEdit_actionPerformed(ActionEvent e) throws Exception {
		checkBeforeEditOrRemove(CANTEDIT);
		super.actionEdit_actionPerformed(e);
	}
    protected void checkBeforeEditOrRemove(String warning) throws Exception{
    	checkSelected();
    	int rowIndex = this.tblMain.getSelectManager().getActiveRowIndex();
		IRow row = this.tblMain.getRow(rowIndex);
    	String id = (String) row.getCell(this.getKeyFieldName()).getValue();
    	//����Ƿ��ڹ�������
		FDCClientUtils.checkBillInWorkflow(this, id);
		
		FDCBillStateEnum state = getstate(id);
		
		if (state != null
				&& (state == FDCBillStateEnum.AUDITTING || state == FDCBillStateEnum.AUDITTED )) {
			if(warning.equals(CANTEDIT)){
				FDCMsgBox.showWarning("�õ��ݲ��Ǳ�������ύ״̬�����ܽ����޸Ĳ�����");
				SysUtil.abort();
			}else{
				FDCMsgBox.showWarning("�õ��ݲ��Ǳ�������ύ״̬�����ܽ���ɾ��������");
				SysUtil.abort();
			}
		}
	}

	/**
     * output storeFields method
     */
    public void storeFields()
    {
        super.storeFields();
    }

    /**
     * output getBizInterface method
     */
    protected com.kingdee.eas.framework.ICoreBase getBizInterface() throws Exception
    {
        return com.kingdee.eas.fdc.sellhouse.DelayPayBillFactory.getRemoteInstance();
    }

    /**
     * output createNewData method
     */
    protected com.kingdee.bos.dao.IObjectValue createNewData()
    {
        com.kingdee.eas.fdc.sellhouse.DelayPayBillInfo objectValue = new com.kingdee.eas.fdc.sellhouse.DelayPayBillInfo();
		
        return objectValue;
    }
    protected boolean isIgnoreCUFilter() {
		return true;
	}
	public boolean isIgnoreRowCount() {
		return false;
	}
	protected String getEditUIModal()
	{
		return UIFactoryName.NEWTAB;
	}
	 protected String getEditUIName()
	    {
	        return com.kingdee.eas.fdc.sellhouse.client.DelayPayBillEditUI1.class.getName();
	    }
}