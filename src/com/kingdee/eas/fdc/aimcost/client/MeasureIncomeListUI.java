/**
 * output package name
 */
package com.kingdee.eas.fdc.aimcost.client;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.event.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.swing.Action;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreeNode;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.metadata.resource.BizEnumValueInfo;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.ItemAction;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectManager;
import com.kingdee.bos.ctrl.kdf.table.event.KDTDataFillListener;
import com.kingdee.bos.ctrl.kdf.table.event.KDTDataRequestEvent;
import com.kingdee.bos.ctrl.kdf.table.util.KDTableUtil;
import com.kingdee.bos.ctrl.swing.KDButton;
import com.kingdee.bos.ctrl.swing.KDDialog;
import com.kingdee.bos.ctrl.swing.KDPanel;
import com.kingdee.bos.ctrl.swing.KDTree;
import com.kingdee.bos.ctrl.swing.KDTreeView;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.ctrl.swing.util.CtrlSwingUtilities;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectStringPK;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.eas.base.log.LogUtil;
import com.kingdee.eas.base.param.ParamControlFactory;
import com.kingdee.eas.basedata.org.CompanyOrgUnitInfo;
import com.kingdee.eas.basedata.org.CostCenterOrgUnitInfo;
import com.kingdee.eas.basedata.org.FullOrgUnitFactory;
import com.kingdee.eas.basedata.org.FullOrgUnitInfo;
import com.kingdee.eas.basedata.org.OrgStructureInfo;
import com.kingdee.eas.basedata.org.client.OrgViewUtils;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.aimcost.AimCostCollection;
import com.kingdee.eas.fdc.aimcost.AimCostFactory;
import com.kingdee.eas.fdc.aimcost.AimCostInfo;
import com.kingdee.eas.fdc.aimcost.MeasureCostFactory;
import com.kingdee.eas.fdc.aimcost.MeasureCostInfo;
import com.kingdee.eas.fdc.aimcost.MeasureIncomeFactory;
import com.kingdee.eas.fdc.aimcost.MeasureIncomeInfo;
import com.kingdee.eas.fdc.aimcost.PlanIndexCollection;
import com.kingdee.eas.fdc.aimcost.PlanIndexEntryInfo;
import com.kingdee.eas.fdc.aimcost.PlanIndexFactory;
import com.kingdee.eas.fdc.aimcost.PlanIndexInfo;
import com.kingdee.eas.fdc.basedata.ApportionTypeInfo;
import com.kingdee.eas.fdc.basedata.CurProjProductEntriesCollection;
import com.kingdee.eas.fdc.basedata.CurProjectFactory;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCConstants;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.basedata.MeasureStageInfo;
import com.kingdee.eas.fdc.basedata.ProductTypeCollection;
import com.kingdee.eas.fdc.basedata.ProductTypeInfo;
import com.kingdee.eas.fdc.basedata.ProjectWithCostCenterOUCollection;
import com.kingdee.eas.fdc.basedata.ProjectWithCostCenterOUFactory;
import com.kingdee.eas.fdc.basedata.ProjectWithCostCenterOUInfo;
import com.kingdee.eas.fdc.basedata.client.FDCClientHelper;
import com.kingdee.eas.fdc.basedata.client.FDCClientUtils;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.basedata.client.FDCUIWeightWorker;
import com.kingdee.eas.fdc.basedata.client.IFDCWork;
import com.kingdee.eas.fdc.basedata.client.ProjectIndexDataUtils;
import com.kingdee.eas.fdc.basedata.client.ProjectTreeBuilder;
import com.kingdee.eas.fdc.contract.FDCUtils;
import com.kingdee.eas.fdc.contract.client.ContractClientUtils;
import com.kingdee.eas.fdc.finance.ProjectPeriodStatusFactory;
import com.kingdee.eas.framework.*;
import com.kingdee.eas.framework.config.TablePreferencesHelper;
import com.kingdee.eas.framework.util.UIConfigUtility;
import com.kingdee.eas.tools.datatask.DatataskMode;
import com.kingdee.eas.tools.datatask.DatataskParameter;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.MsgBox;
import com.kingdee.jdbc.rowset.IRowSet;
import com.kingdee.util.UuidException;

/**
 * output class name
 */
public class MeasureIncomeListUI extends AbstractMeasureIncomeListUI
{
    private static final Logger logger = CoreUIObject.getLogger(MeasureIncomeListUI.class);    

    private KDDialog diag=null;
	
	private static String orgID = null ;
	
	public void onLoad() throws Exception {
		if(!isIncomeJoinCost()){
			FDCMsgBox.showError(this, "���ɱ���������������Ƿ����á�����δ���ã��ù��ܲ���ʹ�ã�");
			SysUtil.abort();
		}
		
		tblMain.addKDTDataFillListener(new KDTDataFillListener(){
			public void afterDataFill(KDTDataRequestEvent e) {
				tblMain_afterDataFile(e);
				
			}
		});
		tblMain.getStyleAttributes().setLocked(true);
		tblMain.getSelectManager().setSelectMode(
				KDTSelectManager.MULTIPLE_ROW_SELECT);
		this.initTree();
		super.onLoad();
		this.treeMain.setSelectionRow(0);
		this.actionCreateTo.setVisible(false);
		this.actionViewDoProccess.setVisible(false);
		this.actionTraceDown.setVisible(false);
		this.actionTraceUp.setVisible(false);
		this.actionWorkFlowG.setVisible(false);
		this.actionCopyTo.setVisible(false);
		this.actionAuditResult.setVisible(false);
		this.menuBiz.setVisible(true);
		this.menuWorkFlow.setVisible(false);
		this.actionExportToAim.setEnabled(false);
		this.actionQuery.setVisible(false);
		this.actionLocate.setVisible(false);
		
		this.actionAddNew.setEnabled(false);
		this.actionAddNew.setVisible(false);
		this.actionRemove.setEnabled(false);
		this.actionRemove.setVisible(false);
		this.actionExportIndex.setEnabled(false);
		this.actionExportIndex.setVisible(false);
		this.actionExportToAim.setEnabled(false);
		this.actionExportToAim.setVisible(false);
		this.actionVersion.setEnabled(false);
		this.actionVersion.setVisible(false);
		
		String formatString = "yyyy-MM-dd";
		tblMain.getColumn("createTime").getStyleAttributes().setNumberFormat(
				formatString);
		tblMain.getColumn("description").getStyleAttributes().setHided(true);
		FDCClientHelper.addSqlMenu(this, this.menuEdit);
		tHelper = new TablePreferencesHelper(this);
	}

	protected void tblMain_afterDataFile(KDTDataRequestEvent e) {
		// ����ֻ�ܷ��ʵ�ǰҳ���У�ǧ��Ҫ������¼������table��������
		  int r1 = e.getFirstRow();
		  int r2 = e.getLastRow();
		  for (int i = r1; i <= r2; i++)
		  {
			  IRow row = tblMain.getRow(i);
			  if(row.getCell("sourceBillId").getValue()!=null){
				  //�޶��汾
				  row.getStyleAttributes().setBackground(new Color(0xFFEA67).brighter().brighter());
			  }
			  if(row.getCell("isLastVersion").getValue().equals(Boolean.TRUE)){
				  row.getStyleAttributes().setBackground(new Color(0xFFEA67));
			  }
		  }

		
	}

	protected void initTree() throws Exception {
		ProjectTreeBuilder treeBuilder = new ProjectTreeBuilder();
		treeBuilder.build(this, this.treeMain, this.actionOnLoad);
		this.treeMain.expandAllNodes(true,
				(TreeNode) ((TreeModel) this.treeMain.getModel()).getRoot());
	}
	
	/**
     * output class constructor
     */
    public MeasureIncomeListUI() throws Exception
    {
        super();
    }

	/**
	 * output storeFields method
	 */
	public void storeFields() {
		super.storeFields();
	}

	/**
	 * output tblMain_tableClicked method
	 */
	protected void tblMain_tableClicked(
			com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e)
			throws Exception {
		super.tblMain_tableClicked(e);
	}

	protected void treeMain_valueChanged(javax.swing.event.TreeSelectionEvent e)
			throws Exception {
		this.mainQuery.setFilter(this.getMainFilter());
		this.mainQuery.put("selector", getSelectors());//.getSelector().
/*		mainQuery.getSorter().clear();
		mainQuery.getSorter().add(new SorterItemInfo("mainVerNo"));
		mainQuery.getSorter().add(new SorterItemInfo("subVerNo"));*/
		this.tblMain.removeRows();		
		if (this.getSelectOrgId() == null) {
			this.actionAddNew.setEnabled(false);
			this.actionEdit.setEnabled(false);
			this.actionRemove.setEnabled(false);
			//�ǵ�����������ܽ���ͬ��
			this.actionImportData.setEnabled(false);
		} else {
			this.actionAddNew.setEnabled(true);
			this.actionEdit.setEnabled(true);
			this.actionRemove.setEnabled(true);
            //�ǵ�����������ܽ���ͬ��
			this.actionImportData.setEnabled(true);
		}
		
		if(tblMain.getRowCount()>0){
			tblMain.getSelectManager().select(0, 0);
		}
	}

	protected FilterInfo getMainFilter() throws Exception {
		String projectId = this.getSelectProjectId();
		FilterInfo filter = new FilterInfo();
		if(projectId!=null){
			FDCSQLBuilder builder=new FDCSQLBuilder();
			builder.appendSql("select b.fid as fid from T_FDC_CurProject a ,T_FDC_CurProject b where a.fid=? and charindex(a.flongnumber||'!',b.flongnumber)=1");
			builder.addParam(projectId);
			IRowSet rowSet=builder.executeQuery();
			Set set=new HashSet();
			while(rowSet.next()){
				set.add(rowSet.getString("fid"));
			}
			set.add(projectId);
			filter.getFilterItems()
				.add(new FilterItemInfo("project.id", set,CompareType.INCLUDE));
		}else{
			String orgId=getSelectOrgId();
			if(orgId!=null){
				filter.appendFilterItem("orgUnit.id", orgId);
			}else{
//				filter.appendFilterItem("orgUnit", orgId);
			}
		}
		filter.getFilterItems().add(
				new FilterItemInfo("isAimMeasure", Boolean.TRUE));
		if(SysContext.getSysContext().getCurrentCtrlUnit()!=null){
			//CU���� 
			String cuId = SysContext.getSysContext().getCurrentCtrlUnit().getId().toString();
			filter.getFilterItems().add(new FilterItemInfo("CU.id",cuId));
		}
		return filter;
	}

	protected String getSelectProjectId() {
		DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeMain
				.getLastSelectedPathComponent();
		if (node == null ||node.getUserObject() == null
				|| OrgViewUtils.isTreeNodeDisable(node)) {
			return null;
		}
		if (node.getUserObject() instanceof CurProjectInfo) {
			CurProjectInfo projectInfo = (CurProjectInfo) node.getUserObject();
			return projectInfo.getId().toString();
		}
		return null;
	}

	/**
	 * ��ѡ�����ʵ�������֯
	 * @return
	 */
	protected String getSelectOrgId() {
		DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeMain
				.getLastSelectedPathComponent();
		if (node == null ||node.getUserObject() == null
				|| OrgViewUtils.isTreeNodeDisable(node)) {
			return null;
		}
		FullOrgUnitInfo org=null;
		if (node.getUserObject() instanceof OrgStructureInfo){
			final OrgStructureInfo info = (OrgStructureInfo)node.getUserObject();
			org = info.getUnit();

		}
		if(node.getUserObject() instanceof CurProjectInfo){
			CurProjectInfo curPrj=(CurProjectInfo)node.getUserObject();
			EntityViewInfo view=new EntityViewInfo();
			String number=curPrj.getLongNumber();
			
			view.getSelector().add("costCenterOU.id");
			view.getSelector().add("costCenterOU.CU.id");
//			view.getSelector().add("costCenterOU.partFI.unit.id");
			view.getSelector().add("curProject.longNumber");
			ProjectWithCostCenterOUCollection projectWithCostCenterOUCollection=null;
			try {
				projectWithCostCenterOUCollection = ProjectWithCostCenterOUFactory.getRemoteInstance().getProjectWithCostCenterOUCollection(view);
			} catch (BOSException e) {
				handUIException(e);
			}
			if(projectWithCostCenterOUCollection==null||projectWithCostCenterOUCollection.size()==0){
				return null;
			}
			ProjectWithCostCenterOUInfo pwcInfo=null;
			
			CurProjectInfo prj=null;
			CostCenterOrgUnitInfo costCenter=null;
			for(Iterator iter=projectWithCostCenterOUCollection.iterator();iter.hasNext();){
				pwcInfo=(ProjectWithCostCenterOUInfo)iter.next();
				final String longNumber = pwcInfo.getCurProject().getLongNumber();
				if(number.startsWith(longNumber)){
					if(prj==null){
						prj=pwcInfo.getCurProject();
						costCenter=pwcInfo.getCostCenterOU();
					}else{
						if(longNumber.startsWith(prj.getLongNumber())){
							prj=pwcInfo.getCurProject();
							costCenter=pwcInfo.getCostCenterOU();
						}
					}
				}
			}
			if(costCenter==null){
				MsgBox.showWarning(this, "��ǰ������Ŀû�����ɱ������빤����Ŀ�Ķ�Ӧ��ϵ���������ã�");
				SysUtil.abort();
			}
			org=costCenter.castToFullOrgUnitInfo();
		}
		
		
		SelectorItemCollection selector=new SelectorItemCollection();
		selector.add("id");
		selector.add("isCompanyOrgUnit");
		selector.add("partFI.isBizUnit");
		selector.add("parent.id");
		try{
			for(;org!=null&&org.getId()!=null;org=org.getParent()){
				org=FullOrgUnitFactory.getRemoteInstance().getFullOrgUnitInfo(new ObjectUuidPK(org.getId()), selector);
				if(org.isIsCompanyOrgUnit()){
					if(org.getPartFI().isIsBizUnit()){
						return org.getId().toString();
					}
				}
			};
		}catch (Exception e){
			return null;
		}
		return null;
	}
	protected ICoreBase getBizInterface() throws Exception {
		return MeasureIncomeFactory.getRemoteInstance();
	}

	protected String getEditUIName() {
		return MeasureIncomeEditUI.class.getName();
	}

	protected void prepareUIContext(UIContext uiContext, ActionEvent e) {
		ItemAction action = getActionFromActionEvent(e);
        if (action.equals(actionAddNew)||action.equals(this.actionImportData))
        {
        	uiContext.put("projectId", this.getSelectProjectId());
    		uiContext.put("orgId", getSelectOrgId());
    		
        }else{
        	checkSelected();
        	int index = tblMain.getSelectManager().getActiveRowIndex();
        	Object prj = tblMain.getCell(index, "project.id").getValue();
        	if(prj!=null){
        		uiContext.put("projectId", prj.toString());
        	}
        	Object orgUnitId = tblMain.getCell(index, "orgUnit.id").getValue();
    		
        	if(orgUnitId!=null){
        		uiContext.put("orgId", orgUnitId.toString());
        	}
        }
        
        if (action.equals(actionVersion))
        {
        	uiContext.put("isEditVersion", Boolean.TRUE);
    		
        }
        uiContext.put("isAimMeasure", Boolean.TRUE);
		super.prepareUIContext(uiContext, e);
	}
	public void onShow() throws Exception {
		super.onShow();
		/**
		 * ����:Ŀ��ɱ��޶���Ҫ֧�ֹ������ж༶��������Ҫ�ſ���ʾ��һЩ��ܵĶ༶��˹������İ�ť  by Cassiel_peng 2009-8-18
		 */
		btnMultiapprove.setVisible(true);
		btnMultiapprove.setEnabled(true);
		btnNextPerson.setVisible(true);
		btnNextPerson.setEnabled(true);
		btnAuditResult.setVisible(true);
		btnAuditResult.setEnabled(true);
		btnWorkFlowG.setVisible(true);
		btnWorkFlowG.setEnabled(true);
		menuWorkFlow.setVisible(true);
		menuWorkFlow.setEnabled(true);
	}
	protected void initWorkButton() {
		super.initWorkButton();
		
		menuItemAudit.setAction(actionAudit);
		actionExportToAim.putValue(Action.SMALL_ICON, EASResource.getIcon("imgTbtn_output"));
		actionAudit.putValue(Action.SMALL_ICON, FDCClientHelper.ICON_AUDIT);
		actionUnAudit.putValue(Action.SMALL_ICON, FDCClientHelper.ICON_UNAUDIT);
		
		menuItemAudit.setAccelerator(KeyStroke.getKeyStroke("ctrl U"));
		actionAudit.putValue(Action.MNEMONIC_KEY, new Integer('A'));
		menuItemAudit.setText(menuItemAudit.getText()+"(A)");
		actionUnAudit.putValue(Action.MNEMONIC_KEY, new Integer('U'));
		menuItemUnAudit.setText(menuItemUnAudit.getText()+"(U)");
		menuItemUnAudit.setAccelerator(KeyStroke.getKeyStroke("ctrl shift U"));
		
		actionVersion.putValue(Action.SMALL_ICON, EASResource.getIcon("imgTbtn_particular"));
		actionExportIndex.putValue(Action.SMALL_ICON, EASResource.getIcon("imgTbtn_output"));
		actionAudit.setEnabled(true);
		actionUnAudit.setEnabled(true);
		
		menuItemVersion.setMnemonic('V');
		menuItemVersion.setText(menuItemVersion.getText()+"(V)");
		menuItemVersion.setAccelerator(KeyStroke.getKeyStroke("ctrl R"));
		
		menuItemExportIndex.setMnemonic('E');
		menuItemExportIndex.setText(menuItemExportIndex.getText()+"(E)");
		menuItemExportIndex.setAccelerator(KeyStroke.getKeyStroke("ctrl shift E"));

		menuExportAim.setMnemonic('X');
		menuExportAim.setText(menuExportAim.getText()+"(X)");
		menuExportAim.setAccelerator(KeyStroke.getKeyStroke("ctrl shift X"));
		
		
	}	
	
	protected String getEditUIModal() {
        String openModel = UIConfigUtility.getOpenModel();
        if(openModel != null)
        {
            return openModel;
        }
        else
        {
            return UIFactoryName.NEWTAB;
        }
	}

	public void actionAudit_actionPerformed(ActionEvent e) throws Exception {
		checkSelected();
		List list = getSelectIds();
		if (list == null)
			return;
		/**
		 * ֮ǰ"Ŀ��ɱ�����༭����"��û���ṩ"�ύ"����,����"����"״̬�ĵ��ݿ�ֱ��"����".���ṩ"�ύ"���ܣ�
		 * �ͱ������Ҫ"�ύ"֮���������   by Cassiel_peng 2009-8-18
		 */
		checkBillState(new String[] { /*FDCBillStateEnum.SAVED_VALUE,*/
				FDCBillStateEnum.SUBMITTED_VALUE }, "selectRightRowForAudit");
		// �������ݻ���
		boolean hasMutex = false;
		try {

			FDCClientUtils.requestDataObjectLock(this, list, "Audit");

			MeasureIncomeFactory.getRemoteInstance().audit(list);
			FDCClientUtils.showOprtOK(this);
			refreshList();
		} catch (Throwable e1) {
			this.handUIException(e1);
			hasMutex = FDCClientUtils.hasMutexed(e1);
		} finally {
			if (!hasMutex) {
				try {
					FDCClientUtils.releaseDataObjectLock(this, list);
				} catch (Throwable e1) {
					this.handUIException(e1);
				}
			}
		}
	}
	
	public void actionUnAudit_actionPerformed(ActionEvent e) throws Exception {
		checkSelected();
		List list=getSelectIds();
		if(list==null)	return;
		checkBillState(new String[]{FDCBillStateEnum.AUDITTED_VALUE}, "selectRightRowForUnAudit");
		MeasureIncomeFactory.getRemoteInstance().unAudit(list);
		FDCClientUtils.showOprtOK(this);
		refreshList();

	}
	private List getSelectIds() {
		List list =new ArrayList();
		int[] selectRows = KDTableUtil.getSelectedRows(this.tblMain);
		for(int i=0;i<selectRows.length;i++){
			Object id = tblMain.getCell(selectRows[i],getKeyFieldName()).getValue();
			if(id==null) continue;
			list.add(id.toString());
		}
		return list;
	}
	
	public void actionVersion_actionPerformed(ActionEvent e) throws Exception {
		checkSelected();
		IRow selectRow=KDTableUtil.getSelectedRow(tblMain);
		
		final Object value = selectRow.getCell("state").getValue();
		if(value instanceof BizEnumValueInfo&&!((BizEnumValueInfo)value).getValue().equals(FDCBillStateEnum.AUDITTED_VALUE)){
			MsgBox.showError(this, "δ�������ݲ������޶�");
			return;
		}
		
		String measureId = this.getSelectedKeyValue();
		SelectorItemCollection sels = new SelectorItemCollection();
		sels.add("*");
		sels.add("measureStage.number");
		sels.add("measureStage.name");
		sels.add("incomeEntry.incomeAccount.longNumber");
		sels.add("incomeEntry.*");
		MeasureIncomeInfo measure = MeasureIncomeFactory.getRemoteInstance()
				.getMeasureIncomeInfo(new ObjectUuidPK(BOSUuid.read(measureId)),sels);
				
		if(true){
			String msg=AimCostClientHelper.getRes("addNew");
			MeasureStageInfo lastStageInfo = AimCostClientHelper.getLastMeasureStage(measure.getProject(),measure.isIsAimMeasure());
			MeasureStageInfo stageInfo = measure.getMeasureStage();
			if(lastStageInfo!=null&&FDCHelper.subtract(lastStageInfo.getNumber(), stageInfo.getNumber()).compareTo(FDCHelper.ZERO)==1){
				StringBuffer sb = new StringBuffer();
				sb.append("�Ѵ��� [ ").append(lastStageInfo.getNumber()).append("-").append(lastStageInfo.getName()).append(" ] ���հ汾��Ŀ��ɱ�����,����").append(msg).append(" [ ").append(stageInfo.getNumber())
						.append("-").append(stageInfo.getName()).append(" ] Ŀ��ɱ����㡣");
				FDCMsgBox.showWarning(sb.toString());
				SysUtil.abort();
			}
		}
		super.actionEdit_actionPerformed(e);
	}
	
    /**
	 * 
	 * ��������鵥��״̬
	 * 
	 * @param states
	 *            ״̬
	 * @param res
	 *            ��ʾ��Ϣ��Դ����
	 * @throws BOSException
	 * @author:liupd ����ʱ�䣺2006-7-27
	 *               <p>
	 */
	protected void checkBillState(String[] states, String res) throws Exception {
		List idList = ContractClientUtils.getSelectedIdValues(tblMain, getKeyFieldName());

		Set idSet = ContractClientUtils.listToSet(idList);
		Set stateSet = FDCHelper.getSetByArray(states);

		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(
				new FilterItemInfo("id", idSet, CompareType.INCLUDE));
		view.setFilter(filter);
		view.getSelector().add("id");
		view.getSelector().add("state");
		CoreBaseCollection coll = getBizInterface().getCollection(view);

		for (Iterator iter = coll.iterator(); iter.hasNext();) {
			CoreBillBaseInfo element = (CoreBillBaseInfo) iter.next();
			
//			��鵥���Ƿ��ڹ�������
			FDCClientUtils.checkBillInWorkflow(this, element.getId().toString());
			
//			if (!element.getString(getBillStatePropertyName()).equals(states)) {
			if (!stateSet.contains(element.getString("state"))) {
				MsgBox.showWarning(this, ContractClientUtils.getRes(res));
				abort();
			}

		}
	}
	
	public void actionEdit_actionPerformed(ActionEvent e) throws Exception {
		checkSelected();
		IRow selectRow=KDTableUtil.getSelectedRow(tblMain);
		final Object value = selectRow.getCell("state").getValue();
		if(value instanceof BizEnumValueInfo&&((BizEnumValueInfo)value).getValue().equals(FDCBillStateEnum.AUDITTED_VALUE)){
			MsgBox.showError(this, "���������ݲ������޸�");
			return;
		}
		if(value instanceof BizEnumValueInfo&&((BizEnumValueInfo)value).getValue().equals(FDCBillStateEnum.AUDITTING_VALUE)){
			MsgBox.showWarning(this, "����ѡ��ĵ��ݵ�״̬���ʺ��޸Ĳ���");
			return;
		}
		
		if(isBeforeLastVersionMeasureStatge(this.getSelectedKeyValue())){
			MsgBox.showError(this, "��ӦĿ��ɱ���������հ汾����׶Σ��ڵ�ǰ��¼�Ĳ���׶�֮�󣬹ʲ������޸�!");			
			return;
		}
		
		super.actionEdit_actionPerformed(e);
	}

	public void actionRemove_actionPerformed(ActionEvent e) throws Exception {
		checkSelected();
		IRow selectRow=KDTableUtil.getSelectedRow(tblMain);
		final Object value = selectRow.getCell("state").getValue();
		if(value instanceof BizEnumValueInfo&&((BizEnumValueInfo)value).getValue().equals(FDCBillStateEnum.AUDITTED_VALUE)){
			MsgBox.showError(this, "���������ݲ�����ɾ��");
			return;
		}
		super.actionRemove_actionPerformed(e);
	}
	
	/*��ǰ�޸ļ�¼�Ĳ���׶�������ӦĿ��ɱ��������հ汾��Ӧ����׶�֮ǰ���������޸�
	 * �����հ汾֮ǰ����true
	 */
	private boolean isBeforeLastVersionMeasureStatge(String incomeId) throws EASBizException, BOSException, UuidException{
		//String measureId = this.getSelectedKeyValue();
		SelectorItemCollection sels = new SelectorItemCollection();
		sels.add("*");
		sels.add("measureStage.number");
		sels.add("measureStage.name");		
		MeasureIncomeInfo incomeInfo = MeasureIncomeFactory.getRemoteInstance()
				.getMeasureIncomeInfo(new ObjectUuidPK(BOSUuid.read(incomeId)),sels);
	    MeasureStageInfo lastVerStageInfo = AimCostClientHelper.getLastMeasureStage(incomeInfo.getProject(), incomeInfo.isIsAimMeasure());
	    MeasureStageInfo curStageInfo = incomeInfo.getMeasureStage();
	    if(lastVerStageInfo!=null && FDCHelper.subtract(curStageInfo.getNumber(),lastVerStageInfo.getNumber()).compareTo(FDCHelper.ZERO)==-1){
	    	return true;//�����հ汾֮ǰ(curStageInfo - lastVerStageInfo)<0
	    }else{
	    	return false;//���������հ汾���߲��������հ汾֮ǰ��������false
	    }	    
	}
	
	public void actionImportData_actionPerformed(final ActionEvent e) throws Exception {
		List verNumList= getVerNumber();
		DatataskParameter param = new DatataskParameter();
		ArrayList paramList = new ArrayList();
    	paramList.add(param);
    	
    	orgID = getSelectOrgId() ;
    	
    	param.getContextParam().put("orgUnitId", getSelectOrgId());
    	param.getContextParam().put("ListUI", this);
    	param.getContextParam().put("VerNumber", verNumList);
    	param.getContextParam().put("ActionEvent", e);
    	param.getContextParam().put("title", "Ŀ����㵼��");
    	paramList.add(param);
    	ImportDataDlg dlg=new ImportDataDlg(FDCClientHelper.getFrameAncestor(this),paramList,DatataskMode.ImpMode);
    	dlg.show();
	}
	
	/**
	 * ͨ��Sql����ȡ��ǰ��֯�µ����а汾��
	 */
	private List getVerNumber() throws EASBizException, BOSException
	{
		String selectOrgId = getSelectOrgId() ;
		List verNumber= new ArrayList();
		boolean isAimCost = true ;
		
		FDCSQLBuilder builder=new FDCSQLBuilder();
		builder.appendSql("select REPLACE(FVersionNumber, '!', '.') as FVersionNumber from T_AIM_MeasureIncome where FOrgUnitID = ? ");
		builder.addParam(selectOrgId);
		builder.appendSql(" and FIsAimMeasure = ? ");
		builder.addParam(Boolean.valueOf(isAimCost)) ;
		try
		{
			IRowSet rowSet=builder.executeQuery();
			while(rowSet.next())
			{
				verNumber.add(rowSet.getString("FVersionNumber"));
			}
		} catch (BOSException e)
		{
			e.printStackTrace();
		} catch (SQLException e)
		{
			e.printStackTrace();
		}
		
		return verNumber ;
	}
	public static String getOrgId()
	{
		return orgID ;
	}
	
	public void actionAttachment_actionPerformed(ActionEvent e) throws Exception {
		checkSelected();
		IRow selectRow=KDTableUtil.getSelectedRow(tblMain);
		final String id = selectRow.getCell("id").getValue()+"";
		MeasureIncomeInfo info = MeasureIncomeFactory.getRemoteInstance().getMeasureIncomeInfo(new ObjectStringPK(id));
		IObjectPK pk = LogUtil.beginLog(null, "1",info.getBOSType(), null, "false", "AimMeasureIncome_Attachment" );
		super.actionAttachment_actionPerformed(e);
		LogUtil.afterLog(null, pk );
	}
	
	private HashMap params = null;
	private HashMap getParams() {
		if(params==null){
	        HashMap hmParamIn = new HashMap();	        
	        //�ɱ���������������Ƿ�����
	        hmParamIn.put(FDCConstants.FDC_PARAM_ISINCOMEJOINCOST, null);
	        try{
	        	HashMap hmAllParam = ParamControlFactory.getRemoteInstance().getParamHashMap(hmParamIn);
	        	params=hmAllParam;
	        }catch(Exception e){
	        	handUIException(e);
	        }
		}
		if(params==null){
			params=new HashMap();
		}
        return params;
	}
	/**
	 * �ɱ���������������Ƿ�����
	 * @return
	 */
	protected boolean isIncomeJoinCost() {
		Object theValue = getParams().get(FDCConstants.FDC_PARAM_ISINCOMEJOINCOST);
        if(theValue != null){
        	return Boolean.valueOf(theValue.toString()).booleanValue();
		}else{
			return false;
		}
	}

}