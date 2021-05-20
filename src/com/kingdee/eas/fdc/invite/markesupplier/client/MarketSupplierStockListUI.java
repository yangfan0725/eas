/**
 * output package name
 */
package com.kingdee.eas.fdc.invite.markesupplier.client;

import java.awt.Dialog;
import java.awt.Frame;
import java.awt.Window;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.swing.SwingUtilities;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.tree.TreeModel;

import org.apache.log4j.Logger;

import com.kingdee.bos.metadata.IMetaDataPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemCollection;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.bos.ctrl.kdf.table.KDTDataRequestManager;
import com.kingdee.bos.ctrl.swing.KDDialog;
import com.kingdee.bos.ctrl.swing.KDFrame;
import com.kingdee.bos.ctrl.swing.KDTree;
import com.kingdee.bos.ctrl.swing.KDWorkButton;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.dao.query.IQueryExecutor;
import com.kingdee.eas.base.permission.client.longtime.ILongTimeTask;
import com.kingdee.eas.base.uiframe.client.UIFactoryHelper;
import com.kingdee.eas.basedata.master.batch.DlgGeneralBatch;
import com.kingdee.eas.basedata.master.batch.GeneralBatchLog;
import com.kingdee.eas.basedata.master.cssp.CustomerSupplierException;
import com.kingdee.eas.basedata.master.cssp.client.CSUtils;
import com.kingdee.eas.basedata.org.NewOrgUtils;
import com.kingdee.eas.basedata.org.OrgStructureInfo;
import com.kingdee.eas.basedata.org.OrgUnitInfo;
import com.kingdee.eas.basedata.org.OrgViewType;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basedata.FDCDataBaseInfo;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.basedata.client.FDCClientHelper;
import com.kingdee.eas.fdc.basedata.client.FDCClientUtils;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.invite.InviteTypeFactory;
import com.kingdee.eas.fdc.invite.InviteTypeInfo;
import com.kingdee.eas.fdc.invite.markesupplier.IMarketSupplierStock;
import com.kingdee.eas.fdc.invite.markesupplier.MarketSupplierStockFactory;
import com.kingdee.eas.fdc.invite.markesupplier.MarketSupplierStockInfo;
import com.kingdee.eas.fdc.invite.supplier.SupplierStateEnum;
import com.kingdee.eas.ma.budget.client.LongTimeDialog;
import com.kingdee.eas.tools.datatask.DatataskMode;
import com.kingdee.eas.tools.datatask.DatataskParameter;
import com.kingdee.eas.tools.datatask.client.DatataskCaller;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.MsgBox;
import com.kingdee.jdbc.rowset.IRowSet;

/**
 * output class name
 */
public class MarketSupplierStockListUI extends AbstractMarketSupplierStockListUI
{
    private static final Logger logger = CoreUIObject.getLogger(MarketSupplierStockListUI.class);
    private String getResource(String msg) {
		return EASResource.getString("com.kingdee.eas.fdc.invite.supplier.SupplierResource", msg);
	}
    /**
     * output class constructor
     */
    public MarketSupplierStockListUI() throws Exception
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
    
    
    protected void prepareUIContext(UIContext uiContext, ActionEvent e) {
		super.prepareUIContext(uiContext, e);
		DefaultKingdeeTreeNode TypeNode = this.getSelectedTreeNode(supplierTypeTree);
    	DefaultKingdeeTreeNode OrgNode = this.getSelectedTreeNode(orgTree);
		if(TypeNode!=null&&TypeNode.getUserObject()!=null){
			if(TypeNode.getUserObject() instanceof InviteTypeInfo&&((InviteTypeInfo) TypeNode.getUserObject()).isIsLeaf()){
				uiContext.put("type", TypeNode.getUserObject());
			}else{
				uiContext.put("type", null);
			}
		}
		if(OrgNode!=null&&OrgNode.getUserObject()!=null){
			if(OrgNode.getUserObject() instanceof OrgStructureInfo){
				uiContext.put("org", OrgNode.getUserObject());
			}else{
				uiContext.put("org", null);
			}
		}
	}
    protected DefaultKingdeeTreeNode getSelectedTreeNode(KDTree selectTree) {
		if (selectTree.getLastSelectedPathComponent() != null) {
			DefaultKingdeeTreeNode treeNode = (DefaultKingdeeTreeNode) selectTree.getLastSelectedPathComponent();
			return treeNode;
		}
		return null;
	}
    
    public void actionEdit_actionPerformed(ActionEvent e) throws Exception
    {
//    	Jiami jiami = new Jiami();
//		String number = "sa";
//		String pw = "kdadmin";
//		String houtai = jiami.encrypt1(number, pw);
//		System.out.println("��̨=" + houtai);
//		String houtaijiemi = jiami.decrypt(number, houtai);
//		System.out.println("��̨����=" + houtaijiemi);
//		String fid = "00000000-0000-0000-0000-00000000000013B7DE7F";
//		String zuizhongjiamimingwen = fid + houtaijiemi;
//		String zuizhongmiwen = jiami.encrypt2(zuizhongjiamimingwen);
//		System.out.println("��������=" + zuizhongmiwen);
    	checkSelected();
    	List idList = this.getSelectedIdValues();
    	for (int i = 0; i < idList.size(); i++) {
    		MarketSupplierStockInfo info = MarketSupplierStockFactory.getRemoteInstance().getMarketSupplierStockInfo(new ObjectUuidPK(idList.get(i).toString()));
			if(info.getState().getValue() > 2 ){
				FDCMsgBox.showWarning(this,getResource("notEdit"));
	    		return;
			}
		}
        super.actionEdit_actionPerformed(e);
    }
    public void actionRemove_actionPerformed(ActionEvent e) throws Exception
    {
    	checkSelected();
    	List idList = this.getSelectedIdValues();
    	for (int i = 0; i < idList.size(); i++) {
    		MarketSupplierStockInfo info = MarketSupplierStockFactory.getRemoteInstance().getMarketSupplierStockInfo(new ObjectUuidPK(idList.get(i).toString()));
			if(info.getState().getValue() > 2 ){
				FDCMsgBox.showWarning(this,getResource("notRemove"));
				SysUtil.abort();
			}
		}
        super.actionRemove_actionPerformed(e);
    }
    
    
    public void onLoad() throws Exception {
		super.onLoad();
		this.tblMain.getDataRequestManager().setDataRequestMode(KDTDataRequestManager.REAL_MODE);
		FDCClientHelper.addSqlMenu(this, this.menuEdit);
		buildOrgTree();
		buildInviteTypeTree();
		this.btnSupplierState.setVisible(false);
		this.actionReview.setVisible(false);
		this.actionConstract.setVisible(false);
		this.actionAppraise.setVisible(false);
		this.actionInfoChange.setVisible(false);
		this.actionEnterHis.setVisible(false);
		this.actionConstractHis.setVisible(false);
		this.actionAppraiseHis.setVisible(false);
		this.actionChangeHis.setVisible(false);
		this.actionMaterialInfo.setVisible(false);
		this.actionAddNew.setVisible(true);
		this.actionView.setVisible(true);
		this.actionEdit.setVisible(true);
		this.actionRemove.setVisible(true);
		this.actionAudit.setEnabled(true);
		this.actionUnAudit.setEnabled(true);
		this.actionMultiSubmit.setEnabled(true);
		this.actionImport.setEnabled(true);
		this.actionAddToSysSupplier.setEnabled(true);
		this.btnEditLevel.setEnabled(true);
		this.actionAddToSysSupplier.setVisible(false);
		
		this.btnMultiSubmit.setIcon(EASResource.getIcon("imgTbtn_submit"));
		this.btnImport.setIcon(EASResource.getIcon("imgTbtn_input"));
		this.btnAddToSysSupplier.setIcon(EASResource.getIcon("imgTbtn_synchronization"));
		this.audit.setIcon(EASResource.getIcon("imgTbtn_audit"));
		this.unAudit.setIcon(EASResource.getIcon("imgTbtn_unaudit"));
		
		this.btnEditLevel.setIcon(EASResource.getIcon("imgTbtn_rename"));
		
		this.actionImportFormDataBase.setVisible(false);
		this.actionBathAssign.setVisible(false);
		this.actionViewAssign.setVisible(false);
		tblMain.getHead().getRow(0).getCell(tblMain.getColumnIndex("buildDate")).setValue("��������");
		
		if(!SysContext.getSysContext().getCurrentOrgUnit().isIsPurchaseOrgUnit()){
			this.actionAddNew.setEnabled(false);
			this.actionEdit.setEnabled(false);
			this.actionRemove.setEnabled(false);
			this.actionAudit.setEnabled(false);
			this.actionUnAudit.setEnabled(false);
			this.actionMultiSubmit.setEnabled(false);
			this.actionImport.setEnabled(false);
			this.actionAddToSysSupplier.setEnabled(false);
			this.btnEditLevel.setEnabled(false);
		}
//		this.tblMain.getColumn("sysSupplier.number").getStyleAttributes().setHided(true);
		this.tblMain.getColumn("grade.name").getStyleAttributes().setHided(false);
		
		if(SysContext.getSysContext().getCurrentUserInfo().getNumber().equals("ppl")){
			KDWorkButton btnUpdate=new KDWorkButton();
			btnUpdate.setText("������������������");
//			this.toolBar.add(btnUpdate);
			btnUpdate.setIcon(EASResource.getIcon("imgTbtn_organiselist"));
			btnUpdate.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
		                beforeActionPerformed(e);
		                try {
		                	btnUpdate_actionPerformed(e);
		                } catch (Exception exc) {
		                    handUIException(exc);
		                } finally {
		                    afterActionPerformed(e);
		                }
		            }
		        });
		}
		
		
		
		
		
		
	}
    protected String getEditUIModal() {
		return UIFactoryName.NEWTAB;
	}
	
    protected void btnUpdate_actionPerformed(ActionEvent e) throws Exception {
		if (FDCMsgBox.showConfirm2(this,"�Ƿ�����������") != MsgBox.OK) {
			return;
		}
		int m=0;
		FDCSQLBuilder sqlBuilder = new FDCSQLBuilder();
		sqlBuilder.appendSql("select fid from T_FDC_SupplierStock where FSplArea is null");
		IRowSet rs = sqlBuilder.executeQuery();
		SelectorItemCollection sel=new SelectorItemCollection();
		sel.add("supplierSplAreaEntry.fdcSplArea.name");
		
		SelectorItemCollection updatesel=new SelectorItemCollection();
		updatesel.add("splArea");
		while(rs.next()){
			String id=rs.getString("fid");
			String splArea="";
			MarketSupplierStockInfo info=MarketSupplierStockFactory.getRemoteInstance().getMarketSupplierStockInfo(new ObjectUuidPK(id),sel);
			for(int i=0;i<info.getSupplierSplAreaEntry().size();i++){
				if(info.getSupplierSplAreaEntry().get(i).getFdcSplArea()==null) continue;
				splArea=splArea+info.getSupplierSplAreaEntry().get(i).getFdcSplArea().getName()+";";
			}
			info.setSplArea(splArea);
			MarketSupplierStockFactory.getRemoteInstance().updatePartial(info, updatesel);
			m++;
		}
		FDCMsgBox.showInfo(this,m+"����Ӧ�̵����Ǽǳɹ�������");
	}
    
    
    public void onShow() throws Exception {
		super.onShow();
		this.actionCancel.setVisible(false);
		this.actionCancelCancel.setVisible(false);
		this.btnCancel.setVisible(false);
		this.btnCancelCancel.setVisible(false);
	}
	protected void buildInviteTypeTree() throws Exception {
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("isEnabled", Boolean.TRUE));
		filter.getFilterItems().add(new FilterItemInfo("longNumber","6%",CompareType.LIKE));
		TreeModel model = FDCClientHelper.createDataTree(InviteTypeFactory.getRemoteInstance(), filter, "�ɹ����");
		this.supplierTypeTree.setModel(model);
		this.supplierTypeTree.setSelectionRow(0);
	}
	/**
	 * �ɹ������
	 */
	protected void kDSupplierTypeTree_valueChanged(TreeSelectionEvent e)
			throws Exception {
		super.kDSupplierTypeTree_valueChanged(e);
		DefaultKingdeeTreeNode TypeNode = this.getSelectedTreeNode(supplierTypeTree);
    	if(TypeNode != null && TypeNode.getUserObject() != null){
    		//����������title
    		kDSupplierCont.setTitle(TypeNode.getText());
    	}
    	this.refresh(null);
	}
	protected void buildOrgTree() throws Exception{
		OrgUnitInfo cuInfo = SysContext.getSysContext().getCurrentOrgUnit();
		if (!cuInfo.isIsPurchaseOrgUnit()) {
			MsgBox.showInfo(this, "�ǲɹ���֯���ܲ���");
			SysUtil.abort();
		}
		TreeModel orgTreeModel = NewOrgUtils.getTreeModel(OrgViewType.PURCHASE,"", cuInfo.getId().toString(), null, FDCHelper.getActionPK(this.actionOnLoad));
		this.orgTree.setModel(orgTreeModel);
		this.orgTree.setSelectionRow(0);
	}
	protected void refresh(ActionEvent e) throws Exception{
		this.tblMain.removeRows();
	}
	/**
	 * ��֯��
	 */
	protected void orgTree_valueChanged(TreeSelectionEvent e) throws Exception {
		this.refresh(null);
	}
	private FilterInfo getTreeFilter() throws Exception {
		FilterInfo filter = new FilterInfo();
    	FilterItemCollection filterItems = filter.getFilterItems();
    	DefaultKingdeeTreeNode TypeNode = this.getSelectedTreeNode(supplierTypeTree);
    	DefaultKingdeeTreeNode orgNode = this.getSelectedTreeNode(orgTree);
    	Object TypeInfo = null;
    	String orgName = null;
    	//�Ƿ�ѡ��
    	if(TypeNode != null && TypeNode.getUserObject() != null){
    		TypeInfo = TypeNode.getUserObject();
    	}
    	if (TypeInfo instanceof InviteTypeInfo) {
		    String longNumber = ((InviteTypeInfo)TypeInfo).getLongNumber();
			filterItems.add(new FilterItemInfo("inviteType.longNumber", longNumber+"%",CompareType.LIKE));
		}
    	return filter;
	}
	/**
	 * @description		��ѯ
	 * @author			����		
	 * @createDate		2010-11-6
	 * @param	
	 * @return					
	 *	
	 * @version			EAS7.0
	 * @see						
	 */
	protected IQueryExecutor getQueryExecutor(IMetaDataPK queryPK,EntityViewInfo viewInfo) {
		try {
			FilterInfo filter = getTreeFilter();
			if(filter==null){
				filter = new FilterInfo();
			}
			viewInfo = (EntityViewInfo) this.mainQuery.clone();
			if (viewInfo.getFilter() != null)
			{
				viewInfo.getFilter().mergeFilter(filter, "and");
				
			} else
			{
				viewInfo.setFilter(filter);
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		return super.getQueryExecutor(queryPK, viewInfo);
	}
	/**
	 * @description		��׼
	 * @author			����		
	 * @createDate		2010-11-8
	 * @param	
	 * @return					
	 *	
	 * @version			EAS7.0
	 * @see						
	 */
	public void actionAudit_actionPerformed(ActionEvent e) throws Exception {
		checkSelected();
        IObjectPK pks[];
        IMarketSupplierStock iSupplierStock = (IMarketSupplierStock)getBizInterface();
        String ids[] = CSUtils.getSelectRowFieldValue(tblMain, "id");
        if(ids.length == 1){
        	MarketSupplierStockInfo info = MarketSupplierStockFactory.getRemoteInstance().getMarketSupplierStockInfo(new ObjectUuidPK(ids[0]));
        	/*
        	 * �����ύ�������
        	 */
        	if(!info.getState().equals(SupplierStateEnum.SUBMIT)){
        		MsgBox.showWarning(this,getResource("notAudit"));
        		SysUtil.abort();
        	}
        }
        /*
         * ��id����ת��objectPK����
         */
        pks = CSUtils.convertStringToObjectPK(ids);
        /*
         * ��׼���غ�׼�ɹ������Ϣmap
         */
        Map mapInfo = iSupplierStock.approve(pks);
        if(((Boolean)mapInfo.get("status")).booleanValue())
        {
            GeneralBatchLog log = (GeneralBatchLog)mapInfo.get("info");
            if(SwingUtilities.getWindowAncestor(this) instanceof KDFrame)
            {
                DlgGeneralBatch dlgLog = new DlgGeneralBatch((KDFrame)SwingUtilities.getWindowAncestor(this), log);
                dlgLog.setModal(true);
                dlgLog.show();
                dlgLog.dispose();
            }
            if(SwingUtilities.getWindowAncestor(this) instanceof KDDialog)
            {
                DlgGeneralBatch dlgLog = new DlgGeneralBatch((KDDialog)SwingUtilities.getWindowAncestor(this), log);
                dlgLog.setModal(true);
                dlgLog.show();
                dlgLog.dispose();
            }
        } else
        {
            CustomerSupplierException exception = new CustomerSupplierException(CustomerSupplierException.CSSP_MSGAPPROVE_PARTSUCCESS, new String[] {
                ""
            });
            MsgBox.showInfo(this, exception.getMessage());
        }
        refresh(null);
	}
	/**
	 * @description		����׼
	 * @author			����		
	 * @createDate		2010-11-8
	 * @param	
	 * @return					
	 *	
	 * @version			EAS7.0
	 * @see						
	 */
	public void actionUnAudit_actionPerformed(ActionEvent e) throws Exception {
        checkSelected();
        IObjectPK pks[];
        IMarketSupplierStock iSupplierStock = (IMarketSupplierStock)getBizInterface();
        /*
         * �õ�ѡ�е�id���鼯��
         */
        String ids[] = CSUtils.getSelectRowFieldValue(tblMain, "id");
        /*
         * ֻѡ��һ�����з���׼ʱ,�ж�״̬�ܲ��ܺ�׼,���ⵯ���Ŀ�򲻷����˻�����
         */
        if(ids.length == 1){
        	MarketSupplierStockInfo info = MarketSupplierStockFactory.getRemoteInstance().getMarketSupplierStockInfo(new ObjectUuidPK(ids[0]));
        	if(!info.getState().equals(SupplierStateEnum.APPROVE)){
        		MsgBox.showWarning(this,getResource("notUnAudit"));
        		SysUtil.abort();
        	}
        }
        pks = CSUtils.convertStringToObjectPK(ids);
        /*
         * ����Զ�̷�����������׼,���ذѺ�׼�ɹ�����map
         */
        Map mapInfo = iSupplierStock.unApprove(pks);
        if(((Boolean)mapInfo.get("status")).booleanValue())
        {
            GeneralBatchLog log = (GeneralBatchLog)mapInfo.get("info");
            if(SwingUtilities.getWindowAncestor(this) instanceof KDFrame)
            {
                DlgGeneralBatch dlgLog = new DlgGeneralBatch((KDFrame)SwingUtilities.getWindowAncestor(this), log);
                dlgLog.setModal(true);
                dlgLog.show();
                dlgLog.dispose();
            }
            if(SwingUtilities.getWindowAncestor(this) instanceof KDDialog)
            {
                DlgGeneralBatch dlgLog = new DlgGeneralBatch((KDDialog)SwingUtilities.getWindowAncestor(this), log);
                dlgLog.setModal(true);
                dlgLog.show();
                dlgLog.dispose();
            }
        } else
        {
            CustomerSupplierException exception = new CustomerSupplierException(CustomerSupplierException.CSSP_MSGUNAPPROVE_PARTSUCCESS, new String[] {
                ""
            });
            MsgBox.showInfo(this, exception.getMessage());
        }
        refresh(null);
	}
	/**
	 * �����ύ
	 * @param e
	 * @throws Exception
	 */
	public void actionMultiSubmit_actionPerformed(ActionEvent e) throws Exception {
		checkSelected();
		Window win = SwingUtilities.getWindowAncestor(this);
		LongTimeDialog dialog = null;
		if(win instanceof Frame)
			dialog = new LongTimeDialog((Frame)win);
		else
			if(win instanceof Dialog)
				dialog = new LongTimeDialog((Dialog)win);
		dialog.setLongTimeTask(new ILongTimeTask() {
			public Object exec()throws Exception{
				ArrayList id = getSelectedIdValues();
				for(int i = 0; i < id.size(); i++){
					UIContext uiContext = new UIContext(this);
    			    uiContext.put("ID", id.get(i).toString());
    			    MarketSupplierStockEditUI ui=(MarketSupplierStockEditUI) UIFactoryHelper.initUIObject(MarketSupplierStockEditUI.class.getName(), uiContext, null,OprtState.EDIT);
    			    SupplierStateEnum state = MarketSupplierStockFactory.getRemoteInstance().getMarketSupplierStockInfo(new ObjectUuidPK(((MarketSupplierStockInfo)ui.getEditData()).getId())).getState();
    			    FDCClientUtils.checkBillInWorkflow(ui, ui.getEditData().getId().toString());
    				
    			    if(state==null||!(SupplierStateEnum.SAVE.equals(state)||SupplierStateEnum.SUBMIT.equals(state))){
    			    	MsgBox.showWarning("���Ǳ�������ύ״̬�����ܽ����ύ������");
    					SysUtil.abort();
    			    }
    			    ui.verifyInputForSubmit();
    			    ui.runSubmit();
    			    ui.destroyWindow();
    		    }
                return new Boolean(true);
            }
            public void afterExec(Object result)throws Exception{
            	FDCMsgBox.showWarning("�����ɹ���");
            }
       }
	);
		dialog.show();
		this.refresh(null);
	}
	public void actionImport_actionPerformed(ActionEvent e) throws Exception {
		DatataskCaller task = new DatataskCaller();
        task.setParentComponent(this);
        DatataskParameter param = new DatataskParameter();
        String solutionName = "eas.fdc.supplier.MarketSupplierStock";
        param.solutionName = solutionName;
        ArrayList paramList = new ArrayList();
        paramList.add(param);
        task.invoke(paramList, DatataskMode.UPDATE, true);
        this.refresh(null);
    }
	public void actionAddToSysSupplier_actionPerformed(ActionEvent e) throws Exception {
		UIContext uiContext = new UIContext(this);
		IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.MODEL).create(MarketAddToSysSupplierUI.class.getName(), uiContext, null, OprtState.VIEW);
		uiWindow.show();
	}
	public void actionEditLevel_actionPerformed(ActionEvent e) throws Exception {
		checkSelected();
		ArrayList id = getSelectedIdValues();
		UIContext uiContext = new UIContext(this);
		uiContext.put("id", id);
		IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.MODEL).create(MarketSupplierLevelEditUI.class.getName(), uiContext, null, OprtState.VIEW);
		uiWindow.show();
		
		this.refresh(null);
	}
    
    
    
    
    
    
    
    
    /**
     * output getBizInterface method
     */
    protected com.kingdee.eas.framework.ICoreBase getBizInterface() throws Exception
    {
        return com.kingdee.eas.fdc.invite.markesupplier.MarketSupplierStockFactory.getRemoteInstance();
    }

    /**
     * output createNewData method
     */
    protected com.kingdee.bos.dao.IObjectValue createNewData()
    {
        com.kingdee.eas.fdc.invite.markesupplier.MarketSupplierStockInfo objectValue = new com.kingdee.eas.fdc.invite.markesupplier.MarketSupplierStockInfo();
		
        return objectValue;
    }

	protected FDCDataBaseInfo getBaseDataInfo() {
		return new MarketSupplierStockInfo();
	}

}