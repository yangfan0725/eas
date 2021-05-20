/**
 * output package name
 */
package com.kingdee.eas.fdc.schedule.client;

import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;

import org.apache.log4j.Logger;

import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.dao.query.IQueryExecutor;
import com.kingdee.bos.metadata.IMetaDataPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.eas.basedata.org.FullOrgUnitInfo;
import com.kingdee.eas.basedata.org.OrgConstants;
import com.kingdee.eas.basedata.org.OrgStructureInfo;
import com.kingdee.eas.basedata.org.OrgUnitInfo;
import com.kingdee.eas.basedata.org.client.OrgViewUtils;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.fdc.basedata.client.FDCClientUtils;
import com.kingdee.eas.fdc.basedata.client.ProjectTreeBuilder;
import com.kingdee.eas.framework.ICoreBase;

/**
 * output class name
 */
public abstract class ScheduleTreeListBaseUI extends AbstractScheduleTreeListBaseUI
{
    private static final Logger logger = CoreUIObject.getLogger(ScheduleTreeListBaseUI.class);
    
    /**
     * output class constructor
     */
    public ScheduleTreeListBaseUI() throws Exception
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
     * output tblMain_tableClicked method
     */
    protected void tblMain_tableClicked(com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e) throws Exception
    {
        super.tblMain_tableClicked(e);
    }

    /**
     * output menuItemImportData_actionPerformed method
     */
    protected void menuItemImportData_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
        super.menuItemImportData_actionPerformed(e);
    }

    /**
     * output treeMain_valueChanged method
     */
    protected void treeMain_valueChanged(javax.swing.event.TreeSelectionEvent e) throws Exception
    {
    	DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeMain.getLastSelectedPathComponent();
    	if(node!=null){
    		this.refresh(null);
    	}
    }

    public void onLoad() throws Exception {
    	buildTree();
    	super.onLoad();
    	treeMain.expandRow(0);
    	treeMain.setSelectionRow(0);
    }
    protected void initWorkButton() {
    	super.initWorkButton();
    	this.actionQuery.setVisible(false);
    }
	protected void buildTree() throws Exception {
		OrgUnitInfo currentOrgUnit = SysContext.getSysContext().getCurrentOrgUnit();
		try{
			//�ò�����֯��������֤�¼���֯�ܹ�������������Ŀ
			SysContext.getSysContext().setCurrentOrgUnit(SysContext.getSysContext().getCurrentFIUnit());
			ProjectTreeBuilder projectTreeBuilder = new ProjectTreeBuilder();

			projectTreeBuilder.build(this, treeMain, actionOnLoad);
		} finally {
			SysContext.getSysContext().setCurrentOrgUnit(currentOrgUnit);
		}
		treeMain.setShowsRootHandles(true);
	}
	
	/**
	 * �õ���ǰѡ��Ķ��󹤳���Ŀ,��֯ID,��Null
	 * @return ��ǰѡ��Ķ��󹤳���Ŀ,��֯ID,��Null
	 */
	protected Object getSelectObj() {
		DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeMain.getLastSelectedPathComponent();
		if (node == null || node.getUserObject() == null || OrgViewUtils.isTreeNodeDisable(node)) {
			return null;
		}
		if (node.getUserObject() instanceof CurProjectInfo) {
			CurProjectInfo projectInfo = (CurProjectInfo) node.getUserObject();
			return projectInfo;
		} else if (node.getUserObject() instanceof OrgStructureInfo) {
			OrgStructureInfo oui = (OrgStructureInfo) node.getUserObject();
			if (oui.getUnit() == null) {
				return null;
			}
			FullOrgUnitInfo info = oui.getUnit();
			return info;
		}
		return null;
	}
	
	/**
	 * ѡ��Ĺ�����Ŀ
	 * @return
	 */
	protected CurProjectInfo getSelectProject() {
		DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeMain.getLastSelectedPathComponent();
		if (node == null || node.getUserObject() == null || OrgViewUtils.isTreeNodeDisable(node)) {
			return null;
		}
		if (node.getUserObject() instanceof CurProjectInfo) {
			CurProjectInfo projectInfo = (CurProjectInfo) node.getUserObject();
			return projectInfo;
		} else if (node.getUserObject() instanceof OrgStructureInfo) {
			return null;
		}
		return null;
	}

    /* 
     * ���˵�ʱ����϶���Ĺ�������
     * @see com.kingdee.eas.framework.client.ListUI#getQueryExecutor(com.kingdee.bos.metadata.IMetaDataPK, com.kingdee.bos.metadata.entity.EntityViewInfo)
     */
    protected IQueryExecutor getQueryExecutor(IMetaDataPK queryPK, EntityViewInfo viewInfo) {
    	viewInfo=(EntityViewInfo)viewInfo.clone();
    	try{
    		if(viewInfo.getFilter()!=null){
    			viewInfo.getFilter().mergeFilter(getMainFilter(), "and");
    		}else{
    			viewInfo.setFilter(getMainFilter());
    		}
    	}catch(Exception e){
    		this.handUIException(e);
    	}
    	IQueryExecutor exec = super.getQueryExecutor(queryPK, viewInfo);
    	exec.option().isIgnoreOrder=false;
		return exec;
    }
    
	protected void showOprtOKMsgAndRefresh() throws Exception {
		FDCClientUtils.showOprtOK(this);
		refreshList();
	}
	
    protected abstract ICoreBase getBizInterface() throws Exception ;

	protected abstract String getEditUIName();
	
	/**
	 * �����������������Query֮��Ĺ�������,�ṩĬ�ϵİ�������Ŀ�����˵Ĺ��ܣ�Ҫ��Query�����й�����Ŀ���ֶΣ���ʵ��getProjectFieldName ��������
	 * @return
	 */
	protected FilterInfo getMainFilter(){
    	DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeMain.getLastSelectedPathComponent();
    	Set prjIdSet=getProjectLeafsOfNode(node);
    	FilterInfo filter = new FilterInfo();
    	filter.getFilterItems().add(new FilterItemInfo(getProjectFieldName(),prjIdSet,CompareType.INCLUDE));
		
    	return filter;
	}
	
	/**
	 * query����Ĺ�����ĿID�ֶΣ���getMainFilter����ʹ�ã��������Ҫ���ֶεĻ�����дgetMainFilter����
	 * @return �磺"project.id"
	 */
	protected abstract String getProjectFieldName();

	/**
     * ��ȡѡ��ڵ�������¼���ϸ������Ŀ
     * @return
     */
    protected Set getProjectLeafsOfNode(DefaultKingdeeTreeNode node){
    	Set idSet=new HashSet();
    	if(node!=null){
    		if(node.getUserObject() instanceof CurProjectInfo){
    			CurProjectInfo prj = (CurProjectInfo)node.getUserObject();
    			if(prj.isIsLeaf()){
    				idSet.add(prj.getId().toString());
    				return idSet;
    			}
    		}
    		//ֱ�ӱ�����ȥ���ڵ���Ա�֤һ����
    		Enumeration childrens = node.depthFirstEnumeration();
    		while(childrens.hasMoreElements()){
    			DefaultKingdeeTreeNode childNode = (DefaultKingdeeTreeNode)childrens.nextElement();
    			if(childNode.getUserObject() instanceof CurProjectInfo){
    				CurProjectInfo prj=(CurProjectInfo)childNode.getUserObject();
    				if(prj.isIsLeaf()){
    					idSet.add(prj.getId().toString());
    				}
    			}
    		}
    	}
    	if(idSet.size()==0){
    		//���ѡ��Ľڵ���û���κ���ϸ�Ĺ�����Ŀ�ڵ������Ӹ��ڵ㱣֤���˲������κ�����
    		idSet.add(OrgConstants.DEF_CU_ID);
    	}
    	return idSet;
    }
    
    protected String getEditUIModal() {
    	return UIFactoryName.NEWTAB;
    }
}