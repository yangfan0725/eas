/**
 * output package name
 */
package com.kingdee.eas.fdc.tenancy.client;

import java.awt.Color;
import java.awt.Dialog;
import java.awt.Frame;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.SwingUtilities;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.tree.TreeNode;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.table.ICell;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTMergeManager;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectManager;
import com.kingdee.bos.ctrl.kdf.table.event.KDTDataRequestEvent;
import com.kingdee.bos.ctrl.kdf.util.style.Styles.HorizontalAlignment;
import com.kingdee.bos.ctrl.swing.KDMenuItem;
import com.kingdee.bos.ctrl.swing.KDWorkButton;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.dao.query.IQueryExecutor;
import com.kingdee.bos.metadata.IMetaDataPK;
import com.kingdee.bos.metadata.MetaDataPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.base.attachment.common.AttachmentClientManager;
import com.kingdee.eas.base.attachment.common.AttachmentManagerFactory;
import com.kingdee.eas.base.commonquery.QuerySolutionInfo;
import com.kingdee.eas.base.commonquery.UserPreferenceData;
import com.kingdee.eas.base.permission.client.longtime.ILongTimeTask;
import com.kingdee.eas.base.uiframe.client.UIFactoryHelper;
import com.kingdee.eas.basedata.org.SaleOrgUnitInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basecrm.FDCReceivingBillCollection;
import com.kingdee.eas.fdc.basecrm.FDCReceivingBillFactory;
import com.kingdee.eas.fdc.basecrm.RevBillTypeEnum;
import com.kingdee.eas.fdc.basecrm.RevBizTypeEnum;
import com.kingdee.eas.fdc.basecrm.client.FDCReceivingBillEditUI;
import com.kingdee.eas.fdc.basedata.FDCConstants;
import com.kingdee.eas.fdc.basedata.FDCDateHelper;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.MoneySysTypeEnum;
import com.kingdee.eas.fdc.basedata.client.FDCClientUtils;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.contract.FDCUtils;
import com.kingdee.eas.fdc.contract.client.ContractClientUtils;
import com.kingdee.eas.fdc.sellhouse.BaseTransactionInfo;
import com.kingdee.eas.fdc.sellhouse.SellProjectInfo;
import com.kingdee.eas.fdc.sellhouse.TransactionStateEnum;
import com.kingdee.eas.fdc.sellhouse.client.PrePurchaseManageEditUI;
import com.kingdee.eas.fdc.sellhouse.client.PurchaseManageEditUI;
import com.kingdee.eas.fdc.sellhouse.client.SHEHelper;
import com.kingdee.eas.fdc.sellhouse.client.SignManageEditUI;
import com.kingdee.eas.fdc.tenancy.DepositDealBillFactory;
import com.kingdee.eas.fdc.tenancy.HandleStateEnum;
import com.kingdee.eas.fdc.tenancy.OtherBillFactory;
import com.kingdee.eas.fdc.tenancy.QuitTenancyFactory;
import com.kingdee.eas.fdc.tenancy.RentStartTypeEnum;
import com.kingdee.eas.fdc.tenancy.TenancyBillFactory;
import com.kingdee.eas.fdc.tenancy.TenancyBillInfo;
import com.kingdee.eas.fdc.tenancy.TenancyBillStateEnum;
import com.kingdee.eas.fdc.tenancy.TenancyContractTypeEnum;
import com.kingdee.eas.fdc.tenancy.TenancyHelper;
import com.kingdee.eas.fdc.tenancy.TenancyRoomEntryCollection;
import com.kingdee.eas.fdc.tenancy.TenancyRoomEntryInfo;
import com.kingdee.eas.ma.budget.client.LongTimeDialog;
import com.kingdee.eas.tools.datatask.DatataskMode;
import com.kingdee.eas.tools.datatask.DatataskParameter;
import com.kingdee.eas.tools.datatask.client.DatataskCaller;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.MsgBox;
import com.kingdee.util.StringUtils;

public class TenancyBillListUI extends AbstractTenancyBillListUI
{
    private static final Logger logger = CoreUIObject.getLogger(TenancyBillListUI.class);
    
	public void actionImport_actionPerformed(ActionEvent e) throws Exception {
		String strSolutionName ="eas.fdc.tenancy.TenancyBill";
		DatataskCaller task = new DatataskCaller();
		task.setParentComponent(this);
		DatataskParameter param = new DatataskParameter();
		String solutionName = strSolutionName;
		param.solutionName = solutionName;
		ArrayList paramList = new ArrayList();
		paramList.add(param);
		task.invoke(paramList, DatataskMode.UPDATE, true);
	}
	public void actionImportSql_actionPerformed(ActionEvent e) throws Exception {
		super.actionImportSql_actionPerformed(e);
		if(TenancyImport.tenancyUpdata()){
			MsgBox.showInfo("�޸������ͬ�����ѳɹ�");
		}else{
			MsgBox.showInfo("û��Ҫ�޸��ĵ����ͬ");
		}
		super.actionRefresh_actionPerformed(e);
	}

	public TenancyBillListUI() throws Exception
    {
        super();
    }
    
    protected void treeMain_valueChanged(TreeSelectionEvent e) throws Exception {
		DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeMain.getLastSelectedPathComponent();
		if (node == null) {
			return;
		}
		SaleOrgUnitInfo saleOrg = SHEHelper.getCurrentSaleOrg();
		if(!saleOrg.isIsBizUnit())
		{
			this.actionAddNew.setEnabled(false);
			this.actionEdit.setEnabled(false);
			this.actionRemove.setEnabled(false);
			this.actionAudit.setEnabled(false);
			this.actionUnAudit.setEnabled(false);
			this.actionHandleTenancy.setEnabled(false);
			this.actionReceiveBill.setEnabled(false);
			this.actionRefundment.setEnabled(false);
			this.actionRepairStartDate.setEnabled(false);
			this.btnSpecial.setEnabled(false);
		}else
		{
			this.actionAddNew.setEnabled(false);
			this.actionEdit.setEnabled(true);
			this.actionRemove.setEnabled(true);
			this.actionAudit.setEnabled(true);
			this.actionUnAudit.setEnabled(true);
			this.actionHandleTenancy.setEnabled(true);
			this.actionReceiveBill.setEnabled(true);
			this.actionRefundment.setEnabled(true);
			this.actionRepairStartDate.setEnabled(true);
			this.btnSpecial.setEnabled(true);
			if(node.getUserObject() instanceof SellProjectInfo)
			{
				this.actionAddNew.setEnabled(true);				
			}
		}
		this.execQuery();
	}
	protected IQueryExecutor getQueryExecutor(IMetaDataPK queryPK, EntityViewInfo viewInfo) {
		try {
			DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeMain.getLastSelectedPathComponent();
			viewInfo = (EntityViewInfo) this.mainQuery.clone();

			FilterInfo filter = new FilterInfo();
			if (node != null  &&  node.getUserObject() instanceof SellProjectInfo) {
				SellProjectInfo pro = (SellProjectInfo) node.getUserObject();
				filter.getFilterItems().add(new FilterItemInfo("sellProject.id", pro.getId().toString()));
			} else {
				filter.getFilterItems().add(new FilterItemInfo("id", null));
			}

			if (viewInfo.getFilter() != null) {
				viewInfo.getFilter().mergeFilter(filter, "and");
			} else {
				viewInfo.setFilter(filter);
			}
		} catch (Exception e) {
			handleException(e);
		}

		return super.getQueryExecutor(queryPK, viewInfo);
	}
    
	protected void initTree() throws Exception {
		this.treeMain.setModel(SHEHelper.getSellProjectTree(this.actionOnLoad,MoneySysTypeEnum.TenancySys));
		this.treeMain.expandAllNodes(true, (TreeNode) this.treeMain.getModel().getRoot());
		
		this.tblMain.getSelectManager().setSelectMode(KDTSelectManager.MULTIPLE_ROW_SELECT);
		
		setColumnNumberFormat("leaseCount");
		this.tblMain.getColumn("leaseTime").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
		setColumnNumberFormat("dealTotalRent");
		setColumnNumberFormat("standardTotalRent");
		
		setColumnNumberFormat("depositAmount");
		setColumnNumberFormat("firstPayRent");
		
//		this.tblMain.getColumn("tenancyRoomList.dayPrice").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
//		this.tblMain.getColumn("tenancyRoomList.dayPrice").getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(2));
//		this.tblMain.getColumn("tenancyRoomList.actDayprice").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
//		this.tblMain.getColumn("tenancyRoomList.actDayprice").getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(2));
	}
    
	protected void prepareUIContext(UIContext uiContext, ActionEvent e) {
		super.prepareUIContext(uiContext, e);
		uiContext.put(UIContext.ID, getSelectedKeyValue());
		DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeMain.getLastSelectedPathComponent();
		if (node.getUserObject() instanceof SellProjectInfo) {
			SellProjectInfo sellProject = (SellProjectInfo) node.getUserObject();
			uiContext.put("sellProject", sellProject);
		}
	}
	
	/**
	 * �������ֽ���еĸ�ʽ:�Ҷ���,2λС��
	 * */
    private void setColumnNumberFormat(String colKey) {
    	this.tblMain.getColumn(colKey).getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
		this.tblMain.getColumn(colKey).getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(2));
	}

   
	public void onLoad() throws Exception {
		this.menuSpecial.setIcon(EASResource.getIcon("imgTbtn_disassemble"));
		this.kDWorkButton1.setIcon(EASResource.getIcon("imgTbtn_input"));//����ģ�嵼������
		this.kDWorkButton2.setIcon(EASResource.getIcon("imgTbtn_emend"));//�޸ĵ�������
		KDMenuItem menuItem1 = new KDMenuItem();
		menuItem1.setAction(this.actionContinueTenancy);
		menuItem1.setText("��������");
		menuItem1.setIcon(EASResource.getIcon("imgTbtn_releasebymdanduser"));
		this.btnSpecial.addAssistMenuItem(menuItem1);

//		KDMenuItem menuItem4 = new KDMenuItem();
//		menuItem4.setAction(this.actionRejiggerTenancy);
//		menuItem4.setText("��������");
//		menuItem4.setIcon(EASResource.getIcon("imgTbtn_distributeuser"));
//		this.btnSpecial.addAssistMenuItem(menuItem4);
//
//		KDMenuItem menuItem2 = new KDMenuItem();
//		menuItem2.setAction(this.actionChangeName);
//		menuItem2.setText("ת������");
//		menuItem2.setIcon(EASResource.getIcon("imgTbtn_persondistribute"));
//		this.btnSpecial.addAssistMenuItem(menuItem2);
		
//		KDMenuItem menuItem5 = new KDMenuItem();
//		menuItem5.setAction(this.actionPriceChange);
//		menuItem5.setText("�۸���");
//		menuItem5.setIcon(EASResource.getIcon("imgTbtn_assistantlistaccount"));
//		this.btnSpecial.addAssistMenuItem(menuItem5);

		KDMenuItem menuItem3 = new KDMenuItem();
		menuItem3.setAction(this.actionQuitTenancy);
		menuItem3.setText("��������");
		menuItem3.setIcon(EASResource.getIcon("imgTbtn_logoutuser"));
		this.btnSpecial.addAssistMenuItem(menuItem3);
    	super.onLoad();
//    	tblMain.getGroupManager().setGroup(true);
//		tblMain.getMergeManager().setMergeMode(KDTMergeManager.FREE_ROW_MERGE);
//		tblMain.getMergeManager().setViewMode(KDTMergeManager.VIEW_AS_INDENTATION);
//		tblMain.getColumn("tenancyType").setMergeable(false);
//		tblMain.getColumn("tenancyState").setMergeable(false);
//		tblMain.getColumn("tenAttachesDes").setMergeable(false);
//		tblMain.getColumn("flagAtTerm").setMergeable(false);
//		tblMain.getColumn("tenancyAdviser.name").setMergeable(false);
//		
//		tblMain.getColumn("tenancyRoomList.dayprice").setMergeable(false);
//		tblMain.getColumn("tenancyRoomList.actDayprice").setMergeable(false);
    	initTree();
    	this.treeMain.setSelectionRow(0);
    	
//    	this.actionAttachment.setVisible(false);
    	this.actionAttachment.setVisible(true);
    	this.actionCreateTo.setVisible(false);
    	this.actionCopyTo.setVisible(false);
    	this.actionTraceUp.setVisible(false);
    	this.actionTraceDown.setVisible(false);
    	this.actionAuditResult.setVisible(true);
    	this.actionWorkFlowG.setVisible(true);   	
    	
    	this.actionUnAudit.setVisible(true);
    	this.actionFlagAtTerm.setVisible(false);
    	this.actionUnAudit.setEnabled(true);
    	this.actionCarryForward.setVisible(false);
    	this.actionBlankOut.setVisible(false);
    	this.actionRepairStartDate.setEnabled(true);
    	this.actionPrintBill.setVisible(true);
    	this.actionPrintBill.setEnabled(true);
    	this.actionPrintPreviewBill.setVisible(true);
    	this.actionPrintPreviewBill.setEnabled(true);
    	 
    	this.tblMain.getColumn("firstPayRent").getStyleAttributes().setHided(true);
    	this.tblMain.getColumn("leaseCount").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
		this.tblMain.getColumn("leaseCount").getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(0));
    	JButton tblPrintBill = this.toolBar.add(this.actionPrintBill);
		tblPrintBill.setIcon(this.btnPrint.getIcon());
		JButton tblPrintPreviewBill = this.toolBar.add(this.actionPrintPreviewBill);
		tblPrintPreviewBill.setIcon(this.btnPrintPreview.getIcon());
		this.actionImport.setVisible(true);
		this.actionImportSql.setVisible(true);
		
		
		this.actionPrintBill.setVisible(false);
		this.actionPrintPreviewBill.setVisible(false);
		this.actionReceiveBill.setVisible(false);
		this.actionRefundment.setVisible(false);
		this.actionRepairStartDate.setVisible(false);
		
		KDWorkButton btnMultiSubmit=new KDWorkButton();
		btnMultiSubmit.setText("�����ύ");
		btnMultiSubmit.setIcon(EASResource.getIcon("imgTbtn_submit"));
		this.toolBar.add(btnMultiSubmit);
		btnMultiSubmit.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
	                beforeActionPerformed(e);
	                try {
	                	btnMultiSubmit_actionPerformed(e);
	                } catch (Exception exc) {
	                    handUIException(exc);
	                } finally {
	                    afterActionPerformed(e);
	                }
	            }
	        });
	}
	public void btnMultiSubmit_actionPerformed(ActionEvent e) {
		checkSelected();
		   Window win = SwingUtilities.getWindowAncestor(this);
	       LongTimeDialog dialog = null;
	       if(win instanceof Frame)
	           dialog = new LongTimeDialog((Frame)win);
	       else
	       if(win instanceof Dialog)
	           dialog = new LongTimeDialog((Dialog)win);
	       
	       dialog.setLongTimeTask(new ILongTimeTask() {
	           public Object exec()
	               throws Exception
	           {
	        	   TenancyImport.tenancyUpdata();
	        	   ArrayList id = getSelectedIdValues();
	        	   for(int i = 0; i < id.size(); i++){
        			   UIContext uiContext = new UIContext(this);
        			   uiContext.put("ID", id.get(i).toString());
        			   TenancyBillEditUI ui=(TenancyBillEditUI) UIFactoryHelper.initUIObject(TenancyBillEditUI.class.getName(), uiContext, null,OprtState.EDIT);
        			   TenancyBillStateEnum state = ((TenancyBillInfo)ui.getEditData()).getTenancyState();
        			   FDCClientUtils.checkBillInWorkflow(ui, ui.getEditData().getId().toString());
        				
        			   if(state==null||!(TenancyBillStateEnum.Saved.equals(state)||TenancyBillStateEnum.Submitted.equals(state))){
        					MsgBox.showWarning("���ݲ��Ǳ�������ύ״̬�����ܽ����ύ������");
        					SysUtil.abort();
        			   }
        			   ui.loadFields();
        			   ui.updatePayListInfo();
        			   ui.storeFields();
        			   ui.verifyInput(null);
        			   ui.runSubmit();
        			   ui.destroyWindow();
        		   }
	               return new Boolean(true);
	           }
	           public void afterExec(Object result)
	               throws Exception
	           {
	        	   FDCMsgBox.showWarning("�����ɹ���");
	           }
	       });
       dialog.show();
       try {
		this.refreshList();
	} catch (Exception e1) {
		e1.printStackTrace();
	}
   }
    public void actionPrintBill_actionPerformed(ActionEvent e) throws Exception {
    	ArrayList idList = new ArrayList();
    	this.checkSelected();
    	String id = this.getSelectedKeyValue();
		if (id != null && !StringUtils.isEmpty(id)) {
			idList.add(id);
		}
		if (idList == null || idList.size() == 0 || getTDQueryPK() == null
				|| getTDFileName() == null) {
			MsgBox.showWarning(this, EASResource.getString(
					"com.kingdee.eas.fdc.basedata.client.FdcResource",
					"cantPrint"));
			return;
		}
		TenancyBillDataProvider data = new TenancyBillDataProvider(id, getTDQueryPK());
		com.kingdee.bos.ctrl.report.forapp.kdnote.client.KDNoteHelper appHlp = new com.kingdee.bos.ctrl.report.forapp.kdnote.client.KDNoteHelper();
		appHlp.print(getTDFileName(), data, javax.swing.SwingUtilities
				.getWindowAncestor(this));
	}

	public void actionPrintPreviewBill_actionPerformed(ActionEvent e)
			throws Exception {
		ArrayList idList = new ArrayList();
		String id = this.getSelectedKeyValue();
		if (id != null && !StringUtils.isEmpty(id)) {
			idList.add(id);
		}
		if (idList == null || idList.size() == 0 || getTDQueryPK() == null
				|| getTDFileName() == null) {
			MsgBox.showWarning(this, EASResource.getString(
					"com.kingdee.eas.fdc.basedata.client.FdcResource",
					"cantPrint"));
			return;

		}
		TenancyBillDataProvider data = new TenancyBillDataProvider(id, getTDQueryPK());
		com.kingdee.bos.ctrl.report.forapp.kdnote.client.KDNoteHelper appHlp = new com.kingdee.bos.ctrl.report.forapp.kdnote.client.KDNoteHelper();
		appHlp.printPreview(getTDFileName(), data, javax.swing.SwingUtilities
				.getWindowAncestor(this));
	}
	protected String getTDFileName() {
		return "/bim/fdc/tenancy/TenancyBill";
	}

	protected IMetaDataPK getTDQueryPK() {
		return new MetaDataPK(
				"com.kingdee.eas.fdc.tenancy.app.TenancyBillPrintQuery");
	}

	/** ���� */
    public void actionAudit_actionPerformed(ActionEvent e) throws Exception {
    	checkSelected();
		ArrayList idList = getSelectedIdValues();
		for(int i = 0; i < idList.size(); i++){
			String id = idList.get(i).toString();
	    	TenancyBillInfo tenBill = TenancyBillFactory.getRemoteInstance().getTenancyBillInfo(new ObjectUuidPK(id));
	    	TenancyBillStateEnum tenState = tenBill.getTenancyState();
	    	if(!TenancyBillStateEnum.Submitted.equals(tenState)){
	    		MsgBox.showInfo(this, "ֻ�����ύ�ĺ�ͬ����������");
	    		this.abort();
	    	}
	    	TenancyBillFactory.getRemoteInstance().audit(BOSUuid.read(id));
		}
		FDCClientUtils.showOprtOK(this);
    	this.refresh(null);
    }
    
    /** ������TODO */
    public void actionUnAudit_actionPerformed(ActionEvent e) throws Exception {
    	checkSelected();
		ArrayList idList = getSelectedIdValues();
		for(int i = 0; i < idList.size(); i++){
			String id = idList.get(i).toString();
			TenancyBillInfo tenBill = TenancyBillFactory.getRemoteInstance().getTenancyBillInfo(new ObjectUuidPK(id));
			TenancyContractTypeEnum tenType = tenBill.getTenancyType();

			if (OtherBillFactory.getRemoteInstance().exists("select id from where tenancyBill.id='"+id+"'")) {
				MsgBox.showInfo(this, "����������ͬ����ֹ������������");
				this.abort();
			}
			if (DepositDealBillFactory.getRemoteInstance().exists("select id from where tenancyBill.id='"+id+"'")) {
				MsgBox.showInfo(this, "����Ѻ�������뵥����ֹ������������");
				this.abort();
			}
			if (!tenType.equals(TenancyContractTypeEnum.NewTenancy)) {
				MsgBox.showInfo(this, "ֻ�������ͬ������������");
				this.abort();
			}
			
			if(!TenancyBillStateEnum.Audited.equals(tenBill.getTenancyState())){
				MsgBox.showInfo(this, "ֻ������״̬�ĺ�ͬ������������");
				this.abort();
			}
			boolean isjiaojie = false;
			TenancyRoomEntryCollection list = tenBill.getTenancyRoomList();
			for (int j = 0; j < list.size(); j++) {
				TenancyRoomEntryInfo entry = list.get(j);
				if (!entry.getHandleState().equals(HandleStateEnum.NoHandleRoom)) {
					isjiaojie = true;
					break;
				}
			}
			FilterInfo info = new FilterInfo();
			info.appendFilterItem("tenancyObj.id", tenBill.getId());
			EntityViewInfo view = new EntityViewInfo();
			view.setFilter(info);
			FDCReceivingBillCollection c = FDCReceivingBillFactory.getRemoteInstance().getFDCReceivingBillCollection(view);
			if (isjiaojie = false || c.size() == 0) {
				TenancyBillFactory.getRemoteInstance().antiAudit(tenBill.getId());
			} else {
				MsgBox.showInfo(this, "ֻ��δ���ӷ��䡢δ�տ�������ͬ������������");
				this.abort();
			}
		}
		FDCClientUtils.showOprtOK(this);
		this.refresh(null);
	}
    
    /** ���� */
    public void actionContinueTenancy_actionPerformed(ActionEvent e) throws Exception {
    	super.actionContinueTenancy_actionPerformed(e);
		String id = getSelectedTenancyId();
    	commonVerify(id);
    	commonVerify2(id);
		showTenancyBillEditUI(id, TenancyContractTypeEnum.ContinueTenancy);
    }
    
    /**
     * �����޺�ͬ����
     * @param oldTenBillId ԭ��ͬID
     * @param tenancyType ��ͬ����
     * @throws BOSException 
     * @throws EASBizException 
     * */
    private void showTenancyBillEditUI(String oldTenBillId, TenancyContractTypeEnum tenancyType) throws EASBizException, BOSException {
    	UIContext uiContext = new UIContext(this);
		uiContext.put(TenancyBillConstant.KEY_OLD_TENANCY_BILL_ID, oldTenBillId);
		uiContext.put(TenancyBillConstant.KEY_TENANCY_CONTRACT_TYPE, tenancyType);

		// ����UI������ʾ
		IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.NEWTAB).create(TenancyBillEditUI.class.getName(), uiContext, null, OprtState.ADDNEW);
		uiWindow.show();
	}

    /**
     * �ж�ԭ��ͬ�Ƿ�Ϊ����Ѻ������,����ִ�л���ִ����
     * �������,�Ի�����ʾ,���жϲ���
     * */
	private void commonVerify(String tenId) throws EASBizException, BOSException {
		TenancyBillInfo tenBill = TenancyBillFactory.getRemoteInstance().getTenancyBillInfo(new ObjectUuidPK(tenId));
    	TenancyBillStateEnum tenState = tenBill.getTenancyState();
    	/* TODO �޸�Ϊִ���еĺ�ͬ��������,��Ҫ����ȷ�ϸ÷�ʽ
		if(!TenancyBillStateEnum.DepositReved.equals(tenState)  &&
				!TenancyBillStateEnum.PartExecuted.equals(tenState)  &&
				!TenancyBillStateEnum.Executing.equals(tenState)){
    		MsgBox.showInfo(this, "ֻ������Ѻ�����⣬����ִ�л�ִ���еĺ�ͬ����ִ�иò�����");
    		*/
		if(!TenancyBillStateEnum.Executing.equals(tenState)){
        		MsgBox.showInfo(this, "ֻ��ִ���еĺ�ͬ����ִ�иò�����");
    		this.abort();
    	}
	}
	
	/**
     * �жϸú�ͬ�Ƿ���ڷǱ���״̬�����ⵥ,�Ǳ���״̬�ĸ�������������ĺ�ͬ
     * �������,�Ի�����ʾ���жϲ���
     * */
	private void commonVerify2(String tenId) throws EASBizException, BOSException {
		if (TenancyHelper.existQuitTenBillByTenBill(QuitTenancyFactory.getRemoteInstance(), tenId, null)) {
			MsgBox.showInfo(this, "ԭ��ͬ�Ѿ������ⵥ��");
			this.abort();
		}

		//�ⲽ�������ڿ��Բ�Ҫ�ˣ���Ϊ�����ͬ�ύ��ԭ��ͬΪ������״̬��
		/*
		String targetTenId = TenancyHelper.getTargetTenIdBySrcTenancyId(tenId);
		if (targetTenId != null) {
			MsgBox.showInfo(this, "ԭ��ͬ�Ѿ��������ת����");
			this.abort();
		}
		*/
	}
    
    /** ���ѡ���е����޺�ͬ��ID,���û��ѡ���и�����ʾ,���жϲ��� */
    private String getSelectedTenancyId(){
		return getSelectedId();
    }
    
    //TODO 
    private TenancyBillStateEnum getSelectedBillState(){
    	IRow row = getSelectedRow();
    	
    	return (TenancyBillStateEnum) row.getCell("tenancyState").getValue();
    }
    
    /** ���� */
    public void actionRejiggerTenancy_actionPerformed(ActionEvent e) throws Exception {
    	super.actionRejiggerTenancy_actionPerformed(e);
    	String id = getSelectedTenancyId();
    	commonVerify(id);
    	commonVerify2(id);
		showTenancyBillEditUI(id, TenancyContractTypeEnum.RejiggerTenancy);
    }
    
    /** ת�� */
    public void actionChangeName_actionPerformed(ActionEvent e) throws Exception {
    	super.actionChangeName_actionPerformed(e);
		String id = getSelectedTenancyId();
    	commonVerify(id);
    	commonVerify2(id);
		showTenancyBillEditUI(id, TenancyContractTypeEnum.ChangeName);
    }
    
    /** �۸��� */
    public void actionPriceChange_actionPerformed(ActionEvent e)throws Exception {
//    	super.actionPriceChange_actionPerformed(e);
//    	String id = getSelectedTenancyId();
////    	commonVerify(id);
//		UIContext uiContext = new UIContext(this);
////		uiContext.put("ID", id);
//		uiContext.put(TenancyBillConstant.KEY_OLD_TENANCY_BILL_ID, id);
//		uiContext.put(TenancyBillConstant.KEY_TENANCY_CONTRACT_TYPE, TenancyContractTypeEnum.PriceChange);
//
//		// ����UI������ʾ
//		IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.NEWTAB).create(TenancyBillEditUI.class.getName(), uiContext, null, OprtState.ADDNEW);
//		uiWindow.show();
    }
    
    /** ���� */
    public void actionBlankOut_actionPerformed(ActionEvent e) throws Exception {
    	super.actionBlankOut_actionPerformed(e);
    	String id = getSelectedTenancyId();
    	TenancyBillInfo tenBill = TenancyBillFactory.getRemoteInstance().getTenancyBillInfo(new ObjectUuidPK(id));
    	TenancyBillStateEnum tenState = tenBill.getTenancyState();
    	if(!TenancyBillStateEnum.Saved.equals(tenState) && !TenancyBillStateEnum.Submitted.equals(tenState) && 
				!TenancyBillStateEnum.Auditing.equals(tenState) && !TenancyBillStateEnum.Audited.equals(tenState) /*&& 
				!TenancyBillStateEnum.DepositReved.equals(tenState)*/){
    		MsgBox.showInfo("�ѱ���,���ύ,������,������,���������Ѻ��״̬�ĺ�ͬ�ſ�������");
			abort();
		}
    	
    	TenancyBillFactory.getRemoteInstance().blankOut(new ObjectUuidPK(id));
    	this.refresh(null);
    }
    
    /** �������� */
    public void actionQuitTenancy_actionPerformed(ActionEvent e) throws Exception {
    	super.actionQuitTenancy_actionPerformed(e);
    	String id = getSelectedTenancyId();
    	
    	TenancyBillInfo tenBill = TenancyBillFactory.getRemoteInstance().getTenancyBillInfo(new ObjectUuidPK(id));
    	TenancyBillStateEnum tenState = tenBill.getTenancyState();
    	if(!TenancyBillStateEnum.Executing.equals(tenState)
    			&& !TenancyBillStateEnum.PartExecuted.equals(tenState)
    			&& !TenancyBillStateEnum.Audited.equals(tenState)){
			MsgBox.showInfo("ֻ��������������ִ�л�ִ���еĺ�ͬ�������⣡");
			abort();
		}
    	if(tenBill.getRentStartType().equals(RentStartTypeEnum.DynamicStartDate) && tenBill.getStartDate()==null)
    	{
    		MsgBox.showInfo("��̬������ʼ���ڵĺ�ͬ��ʼ���ڻ�δ��¼�������������!");
    		this.abort();
    	}
    	this.commonVerify2(id);
    	
    	UIContext uiContext = new UIContext(this);
		uiContext.put(QuitTenancyEditUI.KEY_TENANCY_ID, id);
		uiContext.put("sellProject", tenBill.getSellProject());
		//����UI������ʾ
		IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.MODEL)
				.create(QuitTenancyEditUI.class.getName(), uiContext, null, OprtState.ADDNEW);
		uiWindow.show();
    }
    
    /** ���佻�� */
    public void actionHandleTenancy_actionPerformed(ActionEvent e) throws Exception {
    	super.actionHandleTenancy_actionPerformed(e);
    	String id = getSelectedTenancyId();
    	TenancyBillInfo tenBill = TenancyBillFactory.getRemoteInstance().getTenancyBillInfo(new ObjectUuidPK(id));
    	TenancyBillStateEnum tenState = tenBill.getTenancyState();
    	if(TenancyBillStateEnum.Saved.equals(tenState) || TenancyBillStateEnum.Submitted.equals(tenState) || TenancyBillStateEnum.Auditing.equals(tenState))
    	{
    		MsgBox.showInfo("��ͬ��δ��������,��������");
    		this.abort();
    	}else if(TenancyBillStateEnum.Expiration.equals(tenState))
    	{
    		MsgBox.showInfo("��ͬ�Ѿ���ֹ���ܽ��н�������!");
    		this.abort();
    	}else if(TenancyBillStateEnum.Executing.equals(tenState))
    	{
    		MsgBox.showInfo("ִ���еĺ�ͬ���ܽ��н�������!");
    		this.abort();
    	}else if(TenancyBillStateEnum.BlankOut.equals(tenState))
    	{
    		MsgBox.showInfo("��ͬ������!");
    		this.abort();
    	}  	
    	UIContext uiContext = new UIContext(this);
		uiContext.put("tenancyBillId", id);
		
		//����UI������ʾ
		IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.MODEL)
				.create(HandleRoomTenancyEditUI.class.getName(), uiContext, null, OprtState.ADDNEW);
		uiWindow.show();
		
		this.refresh(null);
    }
    
    /** �տ� */
    public void actionReceiveBill_actionPerformed(ActionEvent e) throws Exception {
    	super.actionReceiveBill_actionPerformed(e);
    	String id = getSelectedId();
    	SelectorItemCollection sels = new SelectorItemCollection();
		sels.add("*");
		sels.add("sellProject.*");
    	TenancyBillInfo tenBill = TenancyBillFactory.getRemoteInstance().getTenancyBillInfo(new ObjectUuidPK(id),sels);
    	TenancyBillStateEnum tenState = tenBill.getTenancyState();
    	if(TenancyBillStateEnum.Saved.equals(tenState) || TenancyBillStateEnum.Submitted.equals(tenState) || 
    			TenancyBillStateEnum.Auditing.equals(tenState)){
    		MsgBox.showInfo("������ĺ�ͬ�����տ");
			abort();
    	}
//    	if(TenancyBillStateEnum.ContinueTenancying.equals(tenState) || TenancyBillStateEnum.RejiggerTenancying.equals(tenState)
//    			||TenancyBillStateEnum.ChangeNaming.equals(tenState) || TenancyBillStateEnum.TenancyChanging.equals(tenState)
//    			||TenancyBillStateEnum.QuitTenancying.equals(tenState) || TenancyBillStateEnum.Expiration.equals(tenState))
//    	{
//    		MsgBox.showInfo(tenState+"�ĺ�ͬ�����տ�");
//    		abort();
//    	}
    	
    	UIContext uiContext = new UIContext(this);
    	uiContext.put(FDCReceivingBillEditUI.KEY_REV_BIZ_TYPE, RevBizTypeEnum.tenancy);
    	uiContext.put(FDCReceivingBillEditUI.KEY_REV_BILL_TYPE, RevBillTypeEnum.gathering);
    	uiContext.put(FDCReceivingBillEditUI.KEY_SELL_PROJECT, tenBill.getSellProject());
    	uiContext.put(FDCReceivingBillEditUI.KEY_TENANCY_BILL, tenBill);
    	uiContext.put(FDCReceivingBillEditUI.KEY_IS_LOCK_BILL_TYPE, Boolean.TRUE);
    	IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.NEWTAB)
				.create(TENReceivingBillEditUI.class.getName(), uiContext, null,
						"ADDNEW");
		uiWindow.show();
		
		this.refresh(null);
    }
    
    /** �˿� */
    public void actionRefundment_actionPerformed(ActionEvent e) throws Exception {
  
    	String id = getSelectedTenancyId();
    	SelectorItemCollection sels = new SelectorItemCollection();
		sels.add("*");
		sels.add("sellProject.*");
    	TenancyBillInfo tenBill = TenancyBillFactory.getRemoteInstance().getTenancyBillInfo(new ObjectUuidPK(id), sels);
//    	TenancyBillStateEnum tenState = tenBill.getTenancyState();
//    	if(!TenancyBillStateEnum.Expiration.equals(tenState) && !TenancyBillStateEnum.BlankOut.equals(tenState)){
//    		MsgBox.showInfo("ֻ����ֹ������״̬�ĵĺ�ͬ�����˿");
//			abort();
//    	}
    	
    	UIContext uiContext = new UIContext(this);
    	uiContext.put(FDCReceivingBillEditUI.KEY_REV_BIZ_TYPE, RevBizTypeEnum.tenancy);
    	uiContext.put(FDCReceivingBillEditUI.KEY_REV_BILL_TYPE, RevBillTypeEnum.refundment);
    	uiContext.put(FDCReceivingBillEditUI.KEY_SELL_PROJECT, tenBill.getSellProject());
    	uiContext.put(FDCReceivingBillEditUI.KEY_TENANCY_BILL, tenBill);
    	uiContext.put(FDCReceivingBillEditUI.KEY_IS_LOCK_BILL_TYPE, Boolean.TRUE);
    	IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.NEWTAB)
				.create(TENReceivingBillEditUI.class.getName(), uiContext, null,
						"ADDNEW");
		uiWindow.show();
		
		this.refresh(null);
    }
    
    /** Ѻ���ת */
    public void actionCarryForward_actionPerformed(ActionEvent e) throws Exception {
    	super.actionCarryForward_actionPerformed(e);
    	String id = getSelectedTenancyId();
    	
    	//������������ͬ������������,��ԭ��ͬ���ܽ���Ѻ���ת����
    	String targetTenId = TenancyHelper.getTargetTenIdBySrcTenancyId(id);
    	
    	if(targetTenId == null){
    		MsgBox.showInfo("�ú�ͬû�н��и���������,���ܽ�ת��");
			abort();
    	}
    	
    	TenancyBillInfo tenBill = TenancyBillFactory.getRemoteInstance().getTenancyBillInfo(new ObjectUuidPK(targetTenId));
    	TenancyBillStateEnum tenState = tenBill.getTenancyState();
    	if(TenancyBillStateEnum.Saved.equals(tenState) || TenancyBillStateEnum.Submitted.equals(tenState) ||
    			TenancyBillStateEnum.Auditing.equals(tenState)){
    		MsgBox.showInfo("��������߸����ĺ�ͬ��û������,���ܽ�ת��");
			abort();
    	}
    	
		UIContext uiContext = new UIContext(this);
		uiContext.put(CarryForwardBillEditUI.KEY_SRC_TENANCY_ID, id);
		
		IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.MODEL)
			.create(CarryForwardBillEditUI.class.getName(), uiContext, null, OprtState.ADDNEW);
		uiWindow.show();
		
		this.refresh(null);
    }
    
    /** ���ڱ�� */
    public void actionFlagAtTerm_actionPerformed(ActionEvent e) throws Exception {
    	super.actionFlagAtTerm_actionPerformed(e);
    }
    
    public void actionRemove_actionPerformed(ActionEvent e) throws Exception {
    	String id = getSelectedTenancyId();
    	TenancyBillInfo tenBill = TenancyBillFactory.getRemoteInstance().getTenancyBillInfo(new ObjectUuidPK(id));
    	TenancyBillStateEnum tenState = tenBill.getTenancyState();
    	if(!TenancyBillStateEnum.Saved.equals(tenState) && !TenancyBillStateEnum.Submitted.equals(tenState)
    			&& !TenancyBillStateEnum.BlankOut.equals(tenState)){
    		MsgBox.showInfo("ֻ�б���,�ύ������״̬�ĵĺ�ͬ����ɾ����");
			abort();
    	}
    	super.actionRemove_actionPerformed(e);
    }
    
    public void actionEdit_actionPerformed(ActionEvent e) throws Exception {
    	String id = getSelectedId();
    	TenancyBillInfo tenBill = TenancyBillFactory.getRemoteInstance().getTenancyBillInfo(new ObjectUuidPK(id));
    	TenancyBillStateEnum tenState = tenBill.getTenancyState();
    	if(!TenancyBillStateEnum.Saved.equals(tenState) && !TenancyBillStateEnum.Submitted.equals(tenState)){
    		MsgBox.showInfo("������ύ״̬�ĵĺ�ͬ�����޸ģ�");
			abort();
    	}
    	super.actionEdit_actionPerformed(e);
    }
    
    public void actionRepairStartDate_actionPerformed(ActionEvent e)
    		throws Exception {
    	super.actionRepairStartDate_actionPerformed(e);
    	int rowIndex = this.tblMain.getSelectManager().getActiveRowIndex();
		if (rowIndex == -1)
		{
			MsgBox.showInfo("��ѡ����!");
			this.abort();
		}
		IRow row = this.tblMain.getRow(rowIndex);
		Date startDate = (Date)row.getCell("startDate").getValue();
		if(startDate!=null)
		{
			MsgBox.showInfo("ֻ�����޿�ʼ����Ϊ�յĺ�ͬ���ܲ�¼");
			this.abort();
		}
		String tenancyBillID = this.getSelectedKeyValue();
		SelectorItemCollection sels = new SelectorItemCollection();
		sels.add("*");
		//sels.add("startDateLimit");
		TenancyBillInfo tenBill = TenancyBillFactory.getRemoteInstance()
		.getTenancyBillInfo(new ObjectUuidPK(BOSUuid.read(tenancyBillID)),
				sels);
		RepairStartDateUI.showUI(this, tenBill);
    }
    
    protected com.kingdee.eas.framework.ICoreBase getBizInterface() throws Exception
    {
        return TenancyBillFactory.getRemoteInstance();
    }
    
    protected String getKeyFieldName() {
    	return super.getKeyFieldName();
    }
    
    
	@Override
	public String getEntriesName() {
		// TODO Auto-generated method stub
		return null;
	}
	protected String getEditUIName() {
    	return TenancyBillEditUI.class.getName();
    }
    
	protected boolean isIgnoreCUFilter() {
		return true;
	}
	protected void afterTableFillData(KDTDataRequestEvent e) {
	   super.afterTableFillData(e);
	   for(int i = 0 ; i < tblMain.getRowCount();i++){
		   IRow row = tblMain.getRow(i);
		   Object state = row.getCell("tenancyState").getValue();
		   if(state!=null&&state.toString().equals("ִ����")){
			   row.getStyleAttributes().setBackground(new java.awt.Color(190,255,190));
		   }
	   }
   }
	protected void fetchInitData() throws Exception {

		super.fetchInitData();
		Map paramMap = FDCUtils.getDefaultFDCParam(null, SysContext.getSysContext().getCurrentOrgUnit().getId().toString());
		if(paramMap.get(FDCConstants.FDC_PARAM_UPLOADAUDITEDBILL)!=null){
			canUploadForAudited = Boolean.valueOf(paramMap.get(FDCConstants.FDC_PARAM_UPLOADAUDITEDBILL).toString()).booleanValue();
		}
	}
	private boolean canUploadForAudited = false;
	 public void actionAttachment_actionPerformed(ActionEvent e) throws Exception
    {
//	    	super.actionAttachment_actionPerformed(e);
		 checkSelected();
    	boolean isEdit=false;
    	AttachmentClientManager acm = AttachmentManagerFactory.getClientManager();
    	String boID = this.getSelectedKeyValue();
    	if (boID == null)
    	{
    		return;
    	}
    	if(getBillStatePropertyName()!=null){
//    		int rowIdx=getBillListTable().getSelectManager().getActiveRowIndex();
//    		ICell cell =getBillListTable().getCell(rowIdx, getBillStatePropertyName());
//    		Object obj=cell.getValue();
//    		isEdit=ContractClientUtils.canUploadAttaForAudited(obj, canUploadForAudited);
    		isEdit=canUploadForAudited;
    	}
    	acm.showAttachmentListUIByBoID(boID,this,isEdit);
    	this.refreshList();
    }
}