/**
 * output package name
 */
package com.kingdee.eas.fdc.invite.client;

import java.awt.event.ActionEvent;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectManager;
import com.kingdee.bos.ctrl.kdf.table.util.KDTableUtil;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.metadata.resource.BizEnumValueInfo;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.basedata.org.OrgConstants;
import com.kingdee.eas.basedata.org.OrgUnitInfo;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.client.FDCClientUtils;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.contract.client.ContractClientUtils;
import com.kingdee.eas.fdc.invite.AppraiseTemplateFactory;
import com.kingdee.eas.fdc.invite.AppraiseTemplateInfo;
import com.kingdee.eas.fdc.invite.AppraiseTempletTypeFactory;
import com.kingdee.eas.fdc.invite.AppraiseTempletTypeInfo;
import com.kingdee.eas.fdc.invite.InviteProjectFactory;
import com.kingdee.eas.fdc.invite.InviteProjectInfo;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.framework.ITreeBase;
import com.kingdee.eas.framework.TreeBaseInfo;
import com.kingdee.eas.framework.client.FrameWorkClientUtils;
import com.kingdee.eas.framework.client.ListUiHelper;
import com.kingdee.eas.framework.client.tree.KDTreeNode;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.MsgBox;

/**
 * output class name
 */
public class AppraiseTemplateListUI extends AbstractAppraiseTemplateListUI
{
	private static final Logger logger = CoreUIObject.getLogger(AppraiseTemplateListUI.class);
	private OrgUnitInfo currentOrg = SysContext.getSysContext().getCurrentOrgUnit();

	private final String COL_ID = "id";
	private final String COL_STATE = "state";
	
	private boolean isSetTemplate = false;

	//�Ƿ������ʾ��һ,��һ
	private final static String GroupOperate="GroupOperate";

	/**
	 * output class constructor
	 */
	public AppraiseTemplateListUI() throws Exception
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
	 * output tblMain_tableSelectChanged method
	 */
	protected void tblMain_tableSelectChanged(com.kingdee.bos.ctrl.kdf.table.event.KDTSelectEvent e) throws Exception
	{
		super.tblMain_tableSelectChanged(e);

		reSetAuditBtnState();
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
		super.treeMain_valueChanged(e);
		
		int rowIndex = tblMain.getSelectManager().getActiveRowIndex();

		if(rowIndex < 0)
		{
			this.actionAudit.setEnabled(false);
			this.actionUnAudit.setEnabled(false);
			
			return ;
		}
	}
	public void actionCancel_actionPerformed(ActionEvent e) throws Exception{
		checkSelectedOrg();
		super.actionCancel_actionPerformed(e);
	}
	public void actionCancelCancel_actionPerformed(ActionEvent e) throws Exception{
		checkSelectedOrg();
		super.actionCancelCancel_actionPerformed(e);
	}

	public void actionEdit_actionPerformed(ActionEvent e) throws Exception {
		checkSelectedOrg();

		super.actionEdit_actionPerformed(e);

	}

	public void actionRemove_actionPerformed(ActionEvent e) throws Exception {
		checkSelectedOrg();
		super.actionRemove_actionPerformed(e);
	}

	public void checkSelectedOrg() {
		if (tblMain.getRowCount() == 0
				|| tblMain.getSelectManager().size() == 0) {
			MsgBox.showWarning(this, EASResource
					.getString(FrameWorkClientUtils.strResource
							+ "Msg_MustSelected"));
			SysUtil.abort();
		}
		String keyFiledName = "orgUnit.id";
		int[] selectRows = KDTableUtil.getSelectedRows(this.tblMain);
		String orgUnitId = ListUiHelper.getSelectedKeyValue(selectRows,
				this.tblMain, keyFiledName);
		if (currentOrg.getId().toString().equals(orgUnitId))
			return;
		else {
			FDCMsgBox.showWarning(this, "�ǵ�¼��֯���ݣ����ܽ��д˲���");
			SysUtil.abort();
		}
	}
	

	 // ��ȡ�����������ɰ���Ҫ����
    protected FilterInfo getTreeFilter()
    {
    	FilterInfo filter = new FilterInfo();
    	if(getUIContext().containsKey("isSelectTemplate")&&getUIContext().get("isSelectTemplate") instanceof Boolean&&((Boolean)getUIContext().get("isSelectTemplate")).booleanValue()){
			filter.appendFilterItem("state", FDCBillStateEnum.AUDITTED_VALUE);
		}
    	try {
			Set upOrgIds = FDCHelper.getUpCompanyOrgIds(null, this.currentOrg.getId().toString());
			if(upOrgIds!=null){
				upOrgIds.add(this.currentOrg.getId().toString());
				filter.getFilterItems().add(new FilterItemInfo("orgUnit.id", upOrgIds,CompareType.INCLUDE));
			}
		} catch (Exception e) {
			this.handleException(e);
		}
    	
        return filter;
    }
    
	/**
	 * output actionAddNew_actionPerformed
	 */
	public void actionAddNew_actionPerformed(ActionEvent e) throws Exception
	{
		if(getTypeSelectedTreeNode().getUserObject() instanceof AppraiseTempletTypeInfo&&((AppraiseTempletTypeInfo)getTypeSelectedTreeNode().getUserObject()).isIsLeaf())
		{
			super.actionAddNew_actionPerformed(e);
		}else{
			FDCMsgBox.showWarning(this,"��ѡ����ϸ�ڵ���������");
		}
	}

	public void actionView_actionPerformed(ActionEvent e) throws Exception {
		super.actionView_actionPerformed(e);
	}

	protected String getGroupEditUIName() {
		return AppraiseTemplateTypeEditUI.class.getName();
	}
	protected String getEditUIName() {
		return AppraiseTemplateEditUI.class.getName();
	}
	protected String getQueryFieldName() {
		String fieldName = "templateType.id";
		return fieldName;
	}

	protected IObjectPK getSelectedTreeKeyValue() {
		AppraiseTemplateInfo appTmpInfo = null;
		IObjectPK detail = new ObjectUuidPK(getSelectedKeyValue());
		if (detail == null) {
			return null;
		}
		try {
			appTmpInfo = (AppraiseTemplateInfo) getBizInterface().getValue(detail);
		} catch (Exception e) {
			handUIException(e);
		}
		if (appTmpInfo.getTemplateType() == null
				|| appTmpInfo.getTemplateType().getId() == null) {
			this.treeMain.setSelectionNode((DefaultKingdeeTreeNode) this.treeMain.getModel().getRoot());
			abort();
		}
		return new ObjectUuidPK(appTmpInfo.getTemplateType().getId());
	}

	protected ITreeBase getTreeInterface() throws Exception {
		return AppraiseTempletTypeFactory.getRemoteInstance();
	}
	protected void prepareUIContext(UIContext uiContext, ActionEvent e) {
		super.prepareUIContext(uiContext, e);

		if(getTypeSelectedTreeNode()!=null){
			uiContext.put("APPTEMPLATETYPE", getTypeSelectedTreeNode().getUserObject());
		}
		
		boolean canAdd = false;
		
		OrgUnitInfo currentOrg = SysContext.getSysContext().getCurrentOrgUnit();
		String keyFiledName = "orgUnit.id";
		int[] selectRows = KDTableUtil.getSelectedRows(this.tblMain);
		String orgUnitId = ListUiHelper.getSelectedKeyValue(selectRows,
				this.tblMain, keyFiledName);
		if (currentOrg.getId().toString().equals(orgUnitId)){
			canAdd = true;
		}
        uiContext.put("CAN_ADD", Boolean.valueOf(canAdd));
        uiContext.put("isSelectTemplate", Boolean.valueOf(isSetTemplate));
       
	}
	protected String getRootName() {
		String root = "����ģ������";
		return root;
	}
	public void onLoad() throws Exception {

		if(!currentOrg.isIsCompanyOrgUnit()){
			FDCMsgBox.showInfo("�ǲ�����֯���ܽ���!");
			abort();
		}
		this.tblMain.checkParsed();

		super.onLoad();

		boolean canAdd = false;

		OrgUnitInfo currentOrg = SysContext.getSysContext().getCurrentOrgUnit();
		if(currentOrg.getId().toString().equals(OrgConstants.DEF_CU_ID)){
			canAdd = true;
		}
//		this.actionAddNew.setEnabled(canAdd);
//		this.actionRemove.setEnabled(canAdd);
//		this.actionEdit.setEnabled(canAdd);
//		this.actionCancel.setEnabled(canAdd);
//		this.actionCancelCancel.setEnabled(canAdd);
//		this.actionCancel.setVisible(canAdd);
//		this.actionCancelCancel.setVisible(canAdd);

		this.actionGroupAddNew.setEnabled(canAdd);
		this.actionGroupEdit.setEnabled(canAdd);
		this.actionGroupMoveTree.setEnabled(canAdd);
		this.actionGroupRemove.setEnabled(canAdd);

//		this.actionAudit.setEnabled(canAdd);
//		this.actionUnAudit.setEnabled(canAdd);

		this.tblMain.getColumn("id").getStyleAttributes().setHided(true);

		FDCHelper.formatTableDate(tblMain, "creator.createTime");
		Map uiContext = this.getUIContext();

		

		this.tblMain.getColumn(this.COL_ID).getStyleAttributes().setHided(true);
		tblMain.getSelectManager().setSelectMode(KDTSelectManager.ROW_SELECT);

		if(tblMain.getRowCount() > 0)
		{
			getMainTable().getSelectManager().select(0, 0);
		}

		//���ݵ���״̬���ð�ť��ʾ״̬
//		reSetAuditBtnState();

		this.actionCancel.setEnabled(false);
		this.actionCancel.setVisible(false);
		this.actionCancelCancel.setEnabled(false);
		this.actionCancelCancel.setVisible(false);


		this.actionMoveTree.setEnabled(false);
		this.actionMoveTree.setVisible(false);

		this.actionGroupMoveTree.setEnabled(false);
		this.actionGroupMoveTree.setVisible(false);

		this.chkIncludeChild.setEnabled(false);
		this.chkIncludeChild.setVisible(false);

		//this.pnlMain.setDividerLocation(300);
		pnlMain.setDividerLocation(300);
		pnlMain.setResizeWeight(0.2);
		if(uiContext.containsKey("isSelectTemplate")&&uiContext.get("isSelectTemplate") instanceof Boolean&&((Boolean)uiContext.get("isSelectTemplate")).booleanValue()){
			this.btnOK.setVisible(true);
			this.btnClose.setVisible(true);
			
			this.actionAddNew.setVisible(false);
			this.actionView.setVisible(false);
			this.actionEdit.setVisible(false);
			this.actionRemove.setVisible(false);
			
			this.actionRefresh.setVisible(false);
			this.actionQuery.setVisible(false);
			this.actionLocate.setVisible(false);
			this.actionPrint.setVisible(false);
			
			this.actionPrintPreview.setVisible(false);
			this.actionAudit.setVisible(false);
			this.actionUnAudit.setVisible(false);
			
			this.actionGroupAddNew.setVisible(false);
			this.actionGroupEdit.setVisible(false);
			this.actionGroupRemove.setVisible(false);
			this.actionGroupView.setVisible(false);
			
			this.isSetTemplate = true ;
			
		}else{
			this.btnOK.setVisible(false);
			this.btnClose.setVisible(false);
			
			this.actionAddNew.setVisible(true);
			this.actionView.setVisible(true);
			this.actionEdit.setVisible(true);
			this.actionRemove.setVisible(true);
			
			this.actionRefresh.setVisible(true);
			this.actionQuery.setVisible(false);
			this.actionLocate.setVisible(true);
			this.actionPrint.setVisible(true);
			
			this.actionPrintPreview.setVisible(true);
			this.actionAudit.setVisible(true);
			this.actionUnAudit.setVisible(true);
			
			this.actionGroupAddNew.setVisible(true);
			this.actionGroupEdit.setVisible(true);
			this.actionGroupRemove.setVisible(true);
			this.actionGroupView.setVisible(true);
			
			this.isSetTemplate = false ;
		}
		
		this.menuItemGroupView.setIcon(EASResource.getIcon("imgTree_view"));
		this.menuItemGroupEdit.setIcon(EASResource.getIcon("imgTree_edit"));
		this.menuItemGroupRemove.setIcon(EASResource.getIcon("imgTree_delete"));
	
	}
	protected DefaultKingdeeTreeNode getTypeSelectedTreeNode() {
		return (DefaultKingdeeTreeNode) this.treeMain
		.getLastSelectedPathComponent();
	}

	protected void btnClose_actionPerformed(ActionEvent e) throws Exception {
		// TODO �Զ����ɷ������
		this.destroyWindow();
	}

	protected void btnOK_actionPerformed(ActionEvent e) throws Exception {
		this.checkSelected();
		if(this.getUIContext().containsKey("inviteProjectId")){
			InviteProjectInfo invProjectInfo = new InviteProjectInfo();
			invProjectInfo.setId(BOSUuid.read((String)this.getUIContext().get("inviteProjectId")));
			String appraiseTemplateId = this.getSelectedKeyValue();
			AppraiseTemplateInfo tempInfo = new AppraiseTemplateInfo();
			tempInfo.setId(BOSUuid.read(appraiseTemplateId));
			invProjectInfo.setAppraiseTemplate(tempInfo);
			SelectorItemCollection selectors = new SelectorItemCollection();
			selectors.add("appraiseTemplate");
			InviteProjectFactory.getRemoteInstance().updatePartial(invProjectInfo,selectors);
		}
		this.destroyWindow();
	}

	protected String getEditUIModal() {
		return UIFactoryName.MODEL;
	}

	protected ICoreBase getBizInterface() throws Exception {
		return AppraiseTemplateFactory.getRemoteInstance();
	}

	public void actionAudit_actionPerformed(ActionEvent e) throws Exception {
		checkSelectedOrg();

		if(!checkTemplateState(getStateForAudit()))
		{
			FDCMsgBox.showWarning(this,"�Ǳ���״̬��������");
			abort();
		}
		List idList =ContractClientUtils.getSelectedIdValues(this.getMainTable(), getKeyFieldName());
		boolean hasMutex = false;
		try{
			FDCClientUtils.requestDataObjectLock(this, idList, "Audit");
			if(idList.size() > 0 && idList.get(0) != null)
			{
				BOSUuid billId = BOSUuid.read(idList.get(0).toString());
				AppraiseTemplateFactory.getRemoteInstance().audit(billId);
			}
		}
		catch (Throwable e1) {
			this.handUIException(e1);
			hasMutex = FDCClientUtils.hasMutexed(e1);
		}
		finally
		{
			if (!hasMutex) {
				try {
					FDCClientUtils.releaseDataObjectLock(this, idList);
				} catch (Throwable e1) {
					this.handUIException(e1);
				}
			}	
		}
		showOprtOKMsgAndRefresh();
		
		this.actionRefresh_actionPerformed(e);
	}

	public void actionUnAudit_actionPerformed(ActionEvent e) throws Exception 
	{	
		checkSelectedOrg();

		if(!checkTemplateState(getStateForUnAudit()))
		{
			FDCMsgBox.showWarning(this,"������״̬���ܷ�����");
			abort();
		}
		
		//���ü��ʵ��
		List idList =ContractClientUtils.getSelectedIdValues(this.getMainTable(), getKeyFieldName());
		Set idSet = new HashSet();
		for(int i = 0; i < idList.size(); ++i)
		{
			idSet.add(idList.get(i).toString());
		}
		
		FilterInfo checkFilter = new FilterInfo();
		checkFilter.getFilterItems().add(new FilterItemInfo("appraiseTemplate.id", idSet, CompareType.INCLUDE));
		if(InviteProjectFactory.getRemoteInstance().exists(checkFilter))
		{
			FDCMsgBox.showWarning(this,"������ģ���ѱ����ã����ܷ�����");
			abort();
		}
		
		boolean hasMutex = false;
		try{
			FDCClientUtils.requestDataObjectLock(this, idList, "UnAudit");
			if(idList.size() > 0 && idList.get(0) != null)
			{
				BOSUuid billId = BOSUuid.read(idList.get(0).toString());
				AppraiseTemplateFactory.getRemoteInstance().unAudit(billId);
			}
		}
		catch (Throwable e1) {
			this.handUIException(e1);
			hasMutex = FDCClientUtils.hasMutexed(e1);
		}
		finally
		{
			if (!hasMutex) {
				try {
					FDCClientUtils.releaseDataObjectLock(this, idList);
				} catch (Throwable e1) {
					this.handUIException(e1);
				}
			}	
		}
		showOprtOKMsgAndRefresh();
		
		this.actionRefresh_actionPerformed(e);
	}
	protected String getStateForAudit() {
		return FDCBillStateEnum.SUBMITTED_VALUE;
	}
	protected String getStateForUnAudit() {
		return FDCBillStateEnum.AUDITTED_VALUE;
	}
	protected void showOprtOKMsgAndRefresh() throws Exception {
		FDCClientUtils.showOprtOK(this);
		refreshList();
	}

	private boolean checkTemplateState(String state)
	{
		boolean flag = false ;
		int rowIndex = tblMain.getSelectManager().getActiveRowIndex() ;
		IRow row = tblMain.getRow(rowIndex);

		if(row.getCell(COL_STATE).getValue() != null)
		{
			if(state.equalsIgnoreCase(((BizEnumValueInfo)row.getCell(COL_STATE).getValue()).getValue().toString()))
			{
				flag = true ;
			}
			else
			{
				flag = false ;
			}
		}

		return flag ;
	}

	private void reSetAuditBtnState()
	{
		boolean canAdd = false;

		OrgUnitInfo currentOrg = SysContext.getSysContext().getCurrentOrgUnit();
		if(currentOrg.getId().toString().equals(OrgConstants.DEF_CU_ID)){
			canAdd = true;
		}
		
		if(!canAdd)
		{
			return ;
		}
		int rowIndex = tblMain.getSelectManager().getActiveRowIndex();

		if(rowIndex < 0)
		{
			return ;
		}
			
		IRow row = tblMain.getRow(rowIndex);

		//�жϵ�ǰ״̬�����°�ť״̬
		if(FDCBillStateEnum.SAVED_VALUE.equalsIgnoreCase(((BizEnumValueInfo)row.getCell(COL_STATE).getValue()).getValue().toString()))
		{
			this.actionEdit.setEnabled(true);
			this.actionView.setEnabled(true);
			this.actionRemove.setEnabled(true);

			this.actionAudit.setEnabled(false);
			this.actionUnAudit.setEnabled(false);
		}
		else if(FDCBillStateEnum.SUBMITTED_VALUE.equalsIgnoreCase(((BizEnumValueInfo)row.getCell(COL_STATE).getValue()).getValue().toString()))
		{
			this.actionEdit.setEnabled(true);
			this.actionView.setEnabled(true);
			this.actionRemove.setEnabled(true);

			this.actionAudit.setEnabled(true);
			this.actionUnAudit.setEnabled(false);
		}
		else if(FDCBillStateEnum.AUDITTED_VALUE.equalsIgnoreCase(((BizEnumValueInfo)row.getCell(COL_STATE).getValue()).getValue().toString()))
		{
			this.actionEdit.setEnabled(false);
			this.actionView.setEnabled(true);
			this.actionRemove.setEnabled(false);

			this.actionAudit.setEnabled(false);
			this.actionUnAudit.setEnabled(true);
		}
	}

	public void actionGroupAddNew_actionPerformed(ActionEvent e)
	throws Exception {

		if(getTypeSelectedTreeNode().getUserObject() instanceof AppraiseTempletTypeInfo){
    		if(((AppraiseTempletTypeInfo)getTypeSelectedTreeNode().getUserObject()).isIsLeaf()){
    			FilterInfo filter = new FilterInfo();
        		filter.getFilterItems().add(new FilterItemInfo("templateType.id",((AppraiseTempletTypeInfo)getTypeSelectedTreeNode().getUserObject()).getId().toString()));
        		if(getBizInterface().exists(filter)){
        			FDCMsgBox.showWarning(this,"��ϸ�����Ѿ������ݣ����ܽ��д˲���");
        			abort();
        		}
    		}
    	}
		
		checkTreeNodeSelected(e);

		UIContext uiContext = new UIContext(this);
		uiContext.put(UIContext.ID, null);
		uiContext.put(GroupOperate,"false");

		if(getTypeSelectedTreeNode()!=null){
			uiContext.put("APPTEMPLATETYPE", getTypeSelectedTreeNode().getUserObject());
		}

		// �����ඨ��Ҫ���ݵ�EditUI����չ������
		prepareGroupUIContext(uiContext, e);

		IUIWindow uiWindow = UIFactory.createUIFactory(getGroupEditUIModal()).create(getGroupEditUIName(), uiContext,
				null, OprtState.ADDNEW);
		uiWindow.show();

		// ����༭������ģʽ���ڷ�ʽ�򿪣���ˢ�½���
		setActionEvent(e);
		// if (getGroupEditUIModal().equals(UIFactoryName.MODEL))
		if (isDoRefresh(uiWindow,getGroupEditUIModal()))
		{
			refresh(e);
		}
	}
	
	public void actionGroupView_actionPerformed(ActionEvent e) throws Exception {
		checkTreeNodeSelected(e);

		boolean canAdd = false;
		
		OrgUnitInfo currentOrg = SysContext.getSysContext().getCurrentOrgUnit();
		if(currentOrg.getId().toString().equals(OrgConstants.DEF_CU_ID)){
			canAdd = true;
		}
		
        UIContext uiContext = new UIContext(this);
        uiContext.put(UIContext.ID, getSelectedNodeKeyValue());
        uiContext.put(GroupOperate,"false");

        uiContext.put("CAN_ADD", Boolean.valueOf(canAdd));
        // �����ඨ��Ҫ���ݵ�EditUI����չ������
        prepareGroupUIContext(uiContext, e);

        // ����UI������ʾ
        IUIWindow uiWindow = UIFactory.createUIFactory(getGroupEditUIModal()).create(getGroupEditUIName(), uiContext,
                null, OprtState.VIEW);
        uiWindow.show();
        // �鿴������ˢ��
        setActionEvent(e);
        if (isDoRefresh(uiWindow,getGroupEditUIModal()))
        {
            refresh(e);
        }
	}
	public void actionGroupRemove_actionPerformed(ActionEvent e)
			throws Exception {
		if(getTypeSelectedTreeNode().getUserObject() instanceof AppraiseTempletTypeInfo){
    		if(((AppraiseTempletTypeInfo)getTypeSelectedTreeNode().getUserObject()).isIsLeaf()){
    			FilterInfo filter = new FilterInfo();
        		filter.getFilterItems().add(new FilterItemInfo("templateType.id",((AppraiseTempletTypeInfo)getTypeSelectedTreeNode().getUserObject()).getId().toString()));
        		if(getBizInterface().exists(filter)){
        			FDCMsgBox.showWarning(this,"��ϸ����Ѿ������ݣ����ܽ��д˲���");
        			abort();
        		}
    		}
    		else{
    			FDCMsgBox.showWarning(this,"����ϸ�ڵ㣬����ִ�д˲���");
    			abort();
    		}
    	}
		
		super.actionGroupRemove_actionPerformed(e);
	}
	public void actionGroupEdit_actionPerformed(ActionEvent e) throws Exception {
		super.actionGroupEdit_actionPerformed(e);
	}
	public EntityViewInfo getMainQuery() {
		return mainQuery;
	}
	
	 protected void prepareGroupUIContext(UIContext uiContext, ActionEvent e) {
		super.prepareGroupUIContext(uiContext, e);
		   KDTreeNode treeNode = (KDTreeNode) treeMain
				.getLastSelectedPathComponent();

		
		   
		if (treeNode != null
				&& treeNode.getUserObject() instanceof TreeBaseInfo) {
			uiContext.put(UIContext.PARENTNODE, treeNode.getUserObject());
			   if (treeNode.getUserObject() instanceof AppraiseTempletTypeInfo) {
				logger.warn("UIcontext+"
						+ ((AppraiseTempletTypeInfo) treeNode.getUserObject())
								.getName());
			} else if (treeNode.getUserObject() instanceof String) {
				FDCMsgBox.showWarning(treeNode.getUserObject().toString());
				logger.warn("UIcontext+�ڵ�����");
			}
		} else {
			uiContext.put(UIContext.PARENTNODE, null);
			FDCMsgBox.showWarning(treeNode.getUserObject().toString());
			logger.warn("UIcontext+�ڵ�����");
		}
	}
}