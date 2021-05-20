/**
 * output package name
 */
package com.kingdee.eas.fdc.invite.client;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import javax.swing.JComponent;
import javax.swing.SwingUtilities;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox;
import com.kingdee.bos.ctrl.kdf.servertable.KDTStyleConstants;
import com.kingdee.bos.ctrl.kdf.table.BasicView;
import com.kingdee.bos.ctrl.kdf.table.ICell;
import com.kingdee.bos.ctrl.kdf.table.IColumn;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTDefaultCellEditor;
import com.kingdee.bos.ctrl.kdf.table.KDTMergeManager;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent;
import com.kingdee.bos.ctrl.kdf.table.util.KDTableUtil;
import com.kingdee.bos.ctrl.kdf.util.style.Styles.HorizontalAlignment;
import com.kingdee.bos.ctrl.swing.KDComboBox;
import com.kingdee.bos.ctrl.swing.KDDatePicker;
import com.kingdee.bos.ctrl.swing.KDFormattedTextField;
import com.kingdee.bos.ctrl.swing.KDTextField;
import com.kingdee.bos.ctrl.swing.KDWorkButton;
import com.kingdee.bos.ctrl.swing.StringUtils;
import com.kingdee.bos.ctrl.swing.event.DataChangeEvent;
import com.kingdee.bos.ctrl.swing.event.DataChangeListener;
import com.kingdee.bos.ctrl.swing.event.SelectorEvent;
import com.kingdee.bos.ctrl.swing.event.SelectorListener;
import com.kingdee.bos.ctrl.swing.util.CtrlCommonConstant;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.IMetaDataPK;
import com.kingdee.bos.metadata.MetaDataPK;
import com.kingdee.bos.metadata.data.SortType;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.metadata.entity.SorterItemCollection;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.base.attachment.AttachmentInfo;
import com.kingdee.eas.base.attachment.BoAttchAssoCollection;
import com.kingdee.eas.base.attachment.BoAttchAssoFactory;
import com.kingdee.eas.base.attachment.BoAttchAssoInfo;
import com.kingdee.eas.base.attachment.common.AttachmentClientManager;
import com.kingdee.eas.base.attachment.common.AttachmentManagerFactory;
import com.kingdee.eas.base.param.ParamControlFactory;
import com.kingdee.eas.basedata.org.FullOrgUnitFactory;
import com.kingdee.eas.basedata.org.FullOrgUnitInfo;
import com.kingdee.eas.basedata.org.OrgConstants;
import com.kingdee.eas.basedata.org.OrgUnitInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.cp.bc.BizCollUtil;
import com.kingdee.eas.custom.purchasebaseinfomation.PricingApproachFactory;
import com.kingdee.eas.custom.purchasebaseinfomation.PurchaseAuthorFactory;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.basedata.client.FDCClientUtils;
import com.kingdee.eas.fdc.basedata.client.FDCClientVerifyHelper;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.basedata.client.FDCTableHelper;
import com.kingdee.eas.fdc.basedata.util.KDDetailedArea;
import com.kingdee.eas.fdc.basedata.util.KDDetailedAreaDialog;
import com.kingdee.eas.fdc.contract.programming.ProgrammingContractCollection;
import com.kingdee.eas.fdc.contract.programming.ProgrammingContractInfo;
import com.kingdee.eas.fdc.contract.programming.ProgrammingFactory;
import com.kingdee.eas.fdc.invite.IPInviteListTypeEntryInfo;
import com.kingdee.eas.fdc.invite.InviteMonthPlanCollection;
import com.kingdee.eas.fdc.invite.InviteMonthPlanEntrysCollection;
import com.kingdee.eas.fdc.invite.InviteMonthPlanEntrysFactory;
import com.kingdee.eas.fdc.invite.InviteMonthPlanEntrysInfo;
import com.kingdee.eas.fdc.invite.InviteMonthPlanFactory;
import com.kingdee.eas.fdc.invite.InviteProjectEntriesInfo;
import com.kingdee.eas.fdc.invite.InviteProjectFactory;
import com.kingdee.eas.fdc.invite.InviteProjectInfo;
import com.kingdee.eas.fdc.invite.InvitePurchaseModeEnum;
import com.kingdee.eas.fdc.invite.InvitePurchaseModeInfo;
import com.kingdee.eas.fdc.invite.InviteSupplierEntryInfo;
import com.kingdee.eas.fdc.invite.InviteTypeFactory;
import com.kingdee.eas.fdc.invite.InviteTypeInfo;
import com.kingdee.eas.fdc.invite.PricingModeEnum;
import com.kingdee.eas.fdc.invite.ResultEnum;
import com.kingdee.eas.fdc.invite.supplier.DefaultStatusEnum;
import com.kingdee.eas.fdc.invite.supplier.GradeSetUpFactory;
import com.kingdee.eas.fdc.invite.supplier.IsGradeEnum;
import com.kingdee.eas.fdc.invite.supplier.QuaLevelFactory;
import com.kingdee.eas.fdc.invite.supplier.SupplierLinkPersonCollection;
import com.kingdee.eas.fdc.invite.supplier.SupplierLinkPersonFactory;
import com.kingdee.eas.fdc.invite.supplier.SupplierReviewGatherCollection;
import com.kingdee.eas.fdc.invite.supplier.SupplierReviewGatherFactory;
import com.kingdee.eas.fdc.invite.supplier.SupplierStateEnum;
import com.kingdee.eas.fdc.invite.supplier.SupplierStockInfo;
import com.kingdee.eas.fdc.invite.supplier.client.NoPerSupplierStockEditUI;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.MsgBox;
import com.kingdee.jdbc.rowset.IRowSet;

/**
 * �б����� �༭����
 */
public class InviteProjectEditUI extends AbstractInviteProjectEditUI
{
    private static final Logger logger = CoreUIObject.getLogger(InviteProjectEditUI.class);
    protected OrgUnitInfo currentOrg = SysContext.getSysContext().getCurrentCostUnit();
    
    public InviteProjectEditUI() throws Exception
    {
        super();
    }
    protected IObjectValue createNewData() {
		InviteProjectInfo info = new InviteProjectInfo();
//		info.setInviteForm(InviteFormEnum.INVITETYPE);
		info.setPaperIsComplete(true);
		info.setScalingRules(true);
		info.setPriceMode(PricingModeEnum.FIXEDPRICEANDUNITPRICE);
		info.setOrgUnit((FullOrgUnitInfo) getUIContext().get("org"));
		info.setInviteType((InviteTypeInfo)(getUIContext().get("type")));
		info.setPublishInvitation(Boolean.FALSE);
		info.setPublishWinInfo(Boolean.FALSE);
		
		try {
//			info.setPurchaseAuthority(PurchaseAuthorFactory.getRemoteInstance().getPurchaseAuthorCollection().get(0));
			info.setPricingApproach(PricingApproachFactory.getRemoteInstance().getPricingApproachCollection().get(0));
		} catch (BOSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return info;
	}
    
    public SelectorItemCollection getSelectors() {
    	SelectorItemCollection sel=super.getSelectors();
    	sel.add("orgUnit.*");
    	sel.add("CU.*");
    	sel.add("state");
    	sel.add("paperIsComplete");
    	sel.add("scalingRules");
    	sel.add("entries.*");
    	sel.add("*");
    	sel.add("respPerson.*");
    	sel.add("respDept.*");
		return sel;
	}
    protected boolean checkCanOperate() {
		boolean flag = false;
		if (editData.getOrgUnit() == null) {
			flag = false;
		}
		String orgId = editData.getOrgUnit() .getId().toString();
		if (currentOrg.getId().toString().equals(orgId)) {
			flag = true;
		} else {
			flag = false;
		}
		return flag;
	}
	protected void initControl(){
		
		this.prmtRespDept.setRequired(true);
		this.prmtRespPerson.setRequired(true);
		
		this.menuTable1.setVisible(false);
		this.btnAddLine.setVisible(false);
		this.btnInsertLine.setVisible(false);
		this.btnRemoveLine.setVisible(false);
		this.actionCreateFrom.setVisible(false);
		this.actionTraceDown.setVisible(false);
		this.actionTraceUp.setVisible(false);
		this.actionCopy.setVisible(false);
		this.actionCopyFrom.setVisible(false);
		
		this.chkMenuItemSubmitAndAddNew.setVisible(false);
		this.chkMenuItemSubmitAndAddNew.setSelected(false);
		this.chkMenuItemSubmitAndPrint.setVisible(false);
		this.chkMenuItemSubmitAndPrint.setSelected(false);
		this.actionPrint.setVisible(true);
		this.actionPrintPreview.setVisible(true);
		this.actionPrint.setEnabled(true);
		this.actionPrintPreview.setEnabled(true);
		
		EntityViewInfo view=new EntityViewInfo();
		FilterInfo filter=new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("state",FDCBillStateEnum.AUDITTED_VALUE));
		filter.getFilterItems().add(new FilterItemInfo("orgUnit.id",editData.getOrgUnit().getId().toString()));
		view.setFilter(filter);
		this.prmtTender.setEntityViewInfo(view);
		
		String	param1="false";
		try {
			param1 = ParamControlFactory.getRemoteInstance().getParamValue(new ObjectUuidPK(editData.getOrgUnit().getId()), "YF_TENDER");
		} catch (EASBizException e) {
			e.printStackTrace();
		} catch (BOSException e) {
			e.printStackTrace();
		}
		if("true".equals(param1)){
			this.prmtTender.setRequired(true);
		}
		
		KDWorkButton btnAddRowinfo = new KDWorkButton();
		KDWorkButton btnInsertRowinfo = new KDWorkButton();
		KDWorkButton btnDeleteRowinfo = new KDWorkButton();
		
		this.actionAddLine.putValue("SmallIcon", EASResource.getIcon("imgTbtn_addline"));
		btnAddRowinfo = (KDWorkButton)prjContainer.add(this.actionAddLine);
		btnAddRowinfo.setText("������Ŀ");
		btnAddRowinfo.setSize(new Dimension(140, 19));

		this.actionInsertLine.putValue("SmallIcon", EASResource.getIcon("imgTbtn_insert"));
		btnInsertRowinfo = (KDWorkButton) prjContainer.add(this.actionInsertLine);
		btnInsertRowinfo.setText("������Ŀ");
		btnInsertRowinfo.setSize(new Dimension(140, 19));

		this.actionRemoveLine.putValue("SmallIcon", EASResource.getIcon("imgTbtn_deleteline"));
		btnDeleteRowinfo = (KDWorkButton) prjContainer.add(this.actionRemoveLine);
		btnDeleteRowinfo.setText("ɾ����Ŀ");
		btnDeleteRowinfo.setSize(new Dimension(140, 19));
		
		KDWorkButton btnAddPlan = new KDWorkButton();
		KDWorkButton btnInsertPlan = new KDWorkButton();
		KDWorkButton btnDeletePlan = new KDWorkButton();
		
		this.actionAddPlan.putValue("SmallIcon", EASResource.getIcon("imgTbtn_addline"));
		btnAddPlan = (KDWorkButton)prjContainer.add(this.actionAddPlan);
		btnAddPlan.setText("�����ƻ�");
		btnAddPlan.setSize(new Dimension(140, 19));

		this.actionInsertPlan.putValue("SmallIcon", EASResource.getIcon("imgTbtn_insert"));
		btnInsertPlan = (KDWorkButton) prjContainer.add(this.actionInsertPlan);
		btnInsertPlan.setText("����ƻ�");
		btnInsertPlan.setSize(new Dimension(140, 19));

		this.actionRemovePlan.putValue("SmallIcon", EASResource.getIcon("imgTbtn_deleteline"));
		btnDeletePlan = (KDWorkButton) prjContainer.add(this.actionRemovePlan);
		btnDeletePlan.setText("ɾ���ƻ�");
		btnDeletePlan.setSize(new Dimension(140, 19));
		
		if(this.prmtPurchaseMode.getValue() == null ) {
			actionAddLine.setEnabled(false);
			actionInsertLine.setEnabled(false);
			actionRemoveLine.setEnabled(false);
			actionAddPlan.setEnabled(false);
			actionInsertPlan.setEnabled(false);
			actionRemovePlan.setEnabled(false);
		}else{
			InvitePurchaseModeInfo modeInfo = (InvitePurchaseModeInfo) this.prmtPurchaseMode.getValue();
			if(modeInfo.getType()==InvitePurchaseModeEnum.SINGLE) {
				 this.actionAddLine.setEnabled(false);
				 this.actionInsertLine.setEnabled(false);
				 this.actionRemoveLine.setEnabled(false);
				 this.actionAddPlan.setEnabled(true);
				 this.actionRemovePlan.setEnabled(true);
				 this.actionInsertPlan.setEnabled(true);
			}else if(modeInfo.getType()==InvitePurchaseModeEnum.GROUP) {
				 this.actionAddLine.setEnabled(true);
				 this.actionInsertLine.setEnabled(true);
				 this.actionRemoveLine.setEnabled(true);
				 this.actionAddPlan.setEnabled(true);
				 this.actionRemovePlan.setEnabled(true);
				 this.actionInsertPlan.setEnabled(true);
			}else if(modeInfo.getType()==InvitePurchaseModeEnum.STRATEGY) {
				 this.actionAddLine.setEnabled(false);
				 this.actionRemoveLine.setEnabled(false);
				 this.actionInsertLine.setEnabled(false);
				 this.actionAddPlan.setEnabled(false);
				 this.actionRemovePlan.setEnabled(false);
				 this.actionInsertPlan.setEnabled(false);
			}
		}
		if(!checkCanOperate()){
			this.actionAddNew.setEnabled(false);
			this.actionEdit.setEnabled(false);
			this.actionRemove.setEnabled(false);
			this.actionAudit.setEnabled(false);
			this.actionUnAudit.setEnabled(false);
		}
	}
    public void onLoad() throws Exception {
    	initTable();
    	this.prjTable.checkParsed();
    	initEditor();
    	super.onLoad();
    	initControl();
    	fillAttachmentList();
    	
    /*	if(this.getOprtState() == OprtState.ADDNEW ) {
    		IRow row = prjTable.addRow();
    		InviteProjectEntriesInfo entry = new InviteProjectEntriesInfo();
    		row.setUserObject(entry);
    	}*/
    	
    	contInviteSupplier.setTitle("��Χ��Ӧ��");
    	
    	this.txtPlanAmount.setEnabled(false);
    	this.txtPlanAmount.setPrecision(2);
    	
    	EntityViewInfo view=new EntityViewInfo();
    	FilterInfo filter=new FilterInfo();
    	filter.getFilterItems().add(new FilterItemInfo("isEnabled",Boolean.TRUE));
    	view.setFilter(filter);
    	
    	this.prmtInviteForm.setEntityViewInfo(view);
    	
    	 this.kdtSupplierEntry.addKDTMouseListener(new com.kingdee.bos.ctrl.kdf.table.event.KDTMouseListener() {
    		 public void tableClicked(com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e) {
                 try {
                	 kdtEntry_tableClicked(e);
                 } catch (Exception exc) {
                     handUIException(exc);
                 } finally {
                 }
             }
         });
    	
    }
	protected void attachListeners() {
		addDataChangeListener(this.prmtPurchaseMode);
	}
	protected void detachListeners() {
		 removeDataChangeListener(this.prmtPurchaseMode);
	}
	protected ICoreBase getBizInterface() throws Exception {
		return InviteProjectFactory.getRemoteInstance();
	}
	protected KDTable getDetailTable() {
		return null;
	}
	protected KDTextField getNumberCtrl() {
		return this.txtNumber;
	}
	protected void verifyInputForSave() throws Exception {
		if(getNumberCtrl().isEnabled()) {
			FDCClientVerifyHelper.verifyEmpty(this, getNumberCtrl());
		}
		InvitePurchaseModeInfo modeInfo = (InvitePurchaseModeInfo) this.prmtPurchaseMode.getValue();
		if(modeInfo.getType()!=InvitePurchaseModeEnum.STRATEGY&&prjTable.getRowCount()==0){
			MsgBox.showWarning("��¼����Ϊ�գ�");
			SysUtil.abort();
		}
		checkPrjTableRequired();
		super.verifyInputForSave();
//		FDCClientVerifyHelper.verifyEmpty(this, this.prmtProject);
	}
	
	private void checkPrjTableRequired() throws BOSException, EASBizException {
		for( int i=0; i<prjTable.getRowCount(); i++ ) {
			if(prjTable.getCell(i, "name").getValue()==null  ) {
				MsgBox.showWarning("��" + (i+1) + "�й�����Ŀ����Ϊ��");
				SysUtil.abort();
			}
			this.checkPlan=false;
			CurProjectInfo project=(CurProjectInfo) prjTable.getCell(i, "name").getValue();
			FilterInfo filter = new FilterInfo();
			
			InviteMonthPlanCollection col=InviteMonthPlanFactory.getRemoteInstance().getInviteMonthPlanCollection("select id from where project.id='"+project.getId().toString()+"' and state='4AUDITTED' order by audittime desc");
			if(col.size()<=0) continue;
			EntityViewInfo view = new EntityViewInfo();
			SelectorItemCollection sic = new SelectorItemCollection();
			sic.add("*");
			sic.add("parent.*");
			filter.getFilterItems().add(new FilterItemInfo("parent.id",col.get(0).getId()));
			 
			view.setFilter(filter);
			view.setSelector(sic);
			 
			InviteMonthPlanEntrysCollection coll = InviteMonthPlanEntrysFactory.getRemoteInstance().getInviteMonthPlanEntrysCollection(view);
			ProgrammingContractCollection validPCColl=null;
			try {
				 validPCColl = InviteProjectFactory.getRemoteInstance().getValidProgrammingContract(coll);
				 if(validPCColl.size() <= 0) {
					 filter = new FilterInfo();
					 filter.getFilterItems().add(new FilterItemInfo("project.id",project.getId()));
					 filter.getFilterItems().add(new FilterItemInfo("state",FDCBillStateEnum.AUDITTED_VALUE));
					 if(ProgrammingFactory.getRemoteInstance().exists(filter)){
						 this.checkPlan = true;
					 }
				 }else {
					 this.checkPlan = true;
				 }
			 }catch(EASBizException e1) {
				 this.checkPlan = true;
			 }
			 if(this.checkPlan&&prjTable.getCell(i, "programmingName").getValue()==null) {
				MsgBox.showWarning("��" + (i+1) + "�вɹ��ƻ�����Ϊ��");
				SysUtil.abort();
			}
		}
	}
	
	protected void verifyInput(ActionEvent e) throws Exception {
		FDCClientVerifyHelper.verifyEmpty(this, this.prmtInviteType);
		FDCClientVerifyHelper.verifyEmpty(this, this.prmtInviteForm);
		FDCClientVerifyHelper.verifyEmpty(this, this.prmtPurchaseMode);
		FDCClientVerifyHelper.verifyEmpty(this, this.prmtScalingRule);
		FDCClientVerifyHelper.verifyEmpty(this, this.combProcurementType);
		FDCClientVerifyHelper.verifyEmpty(this, this.combAuth);
		if(this.prmtTender.isRequired()){
			FDCClientVerifyHelper.verifyEmpty(this, this.prmtTender);
		}
		super.verifyInput(e);
	}
	
	protected void verifyInputForSubmint() throws Exception {
		verifyInputForSave();
		if(this.kdtSupplierEntry.getRowCount()==0){
			FDCMsgBox.showWarning(this,"����Ӧ�̲���Ϊ�գ�");
			SysUtil.abort();
		}
		for(int i=0; i<this.kdtSupplierEntry.getRowCount(); i++) {
			if(this.kdtSupplierEntry.getCell(i, "supplier").getValue()==null) {
				FDCMsgBox.showWarning(this,"��Ӧ�̲���Ϊ�գ�");
				this.kdtSupplierEntry.getEditManager().editCellAt(i, this.kdtSupplierEntry.getColumnIndex("supplier"));
				SysUtil.abort();
			}
//			if(this.kdtSupplierEntry.getCell(i, "isAccept").getValue()==null) {
//				FDCMsgBox.showWarning(this,"�Ƿ���Χ����Ϊ�գ�");
//				this.kdtSupplierEntry.getEditManager().editCellAt(i, this.kdtSupplierEntry.getColumnIndex("isAccept"));
//				SysUtil.abort();
//			}
			if(this.kdtSupplierEntry.getCell(i, "coState").getValue()==null) {
				FDCMsgBox.showWarning(this,"����״̬����Ϊ�գ�");
				this.kdtSupplierEntry.getEditManager().editCellAt(i, this.kdtSupplierEntry.getColumnIndex("coState"));
				SysUtil.abort();
			}
			if(this.kdtSupplierEntry.getCell(i, "manager").getValue()==null) {
				FDCMsgBox.showWarning(this,"��Ŀ������Ϊ�գ�");
				this.kdtSupplierEntry.getEditManager().editCellAt(i, this.kdtSupplierEntry.getColumnIndex("manager"));
				SysUtil.abort();
			}
		}
	}
	
	private void replaceLongNumberStr( boolean dotToeExclamation) {
		for(int i=0; i<prjTable.getRowCount(); i++ ) {
			String longNumber = (String) prjTable.getCell(i, "number").getValue();
			if(longNumber != null ) {
				if(dotToeExclamation) {
					prjTable.getCell(i, "number").setValue(longNumber.replaceAll(".", "!"));
				}else {
					prjTable.getCell(i, "number").setValue(longNumber.replaceAll("!", "."));
				}
			}
		}
	}
	
	public void loadFields() {
		detachListeners();
		super.loadFields();
		setSaveActionStatus();
		
		if(this.editData.isPaperIsComplete()){
			this.kDRBPCYes.setSelected(true);
		}else{
			this.kDRBPCNo.setSelected(true);
		}
		/*if(this.editData.isScalingRules()){
			this.kDRBSRYes.setSelected(true);
		}else{
			this.kDRBSRNo.setSelected(true);
		}*/
		try {
		for(int i=0; i<this.kdtSupplierEntry.getRowCount(); i++) {
			SupplierStockInfo supplier=(SupplierStockInfo) this.kdtSupplierEntry.getRow(i).getCell("supplier").getValue();
			if(supplier!=null){
				//��Լ�÷�...
				EntityViewInfo view = new EntityViewInfo();
				SelectorItemCollection sic = new SelectorItemCollection();
				sic.add("evaluationType.number");
				sic.add("supplier.id");
				sic.add("amount");
				sic.add("state");
				view.setSelector(sic);
				
				view.setTopCount(1);
				FilterInfo filter = new FilterInfo();
				filter.getFilterItems().add(new FilterItemInfo("supplier.id", supplier.getId().toString()));
				filter.getFilterItems().add(new FilterItemInfo("evaluationType.number", "003"));
				filter.getFilterItems().add(new FilterItemInfo("evaluationType.number", "004"));
				filter.getFilterItems().add(new FilterItemInfo("state", FDCBillStateEnum.AUDITTED_VALUE));
				filter.setMaskString("#0 and (#1 or #2) and #3");
				view.setFilter(filter);
				
				SorterItemCollection sorter = new SorterItemCollection();
				SorterItemInfo sorterInfo = new SorterItemInfo("bizDate");
				sorterInfo.setSortType(SortType.DESCEND);
				sorter.add(sorterInfo);
				view.setSorter(sorter);
				SupplierReviewGatherCollection coll = SupplierReviewGatherFactory.getRemoteInstance().getSupplierReviewGatherCollection(view);
				if(coll.size()>0) {
					this.kdtSupplierEntry.getRow(i).getCell("score1").setValue(coll.get(0).getAmount());
				}
				
				//����÷֡���
				filter = new FilterInfo();
				filter.getFilterItems().add(new FilterItemInfo("supplier.id", supplier.getId().toString()));
				filter.getFilterItems().add(new FilterItemInfo("evaluationType.number", "002"));
				filter.getFilterItems().add(new FilterItemInfo("state", FDCBillStateEnum.AUDITTED_VALUE));
				view.setFilter(filter);
				
					coll = SupplierReviewGatherFactory.getRemoteInstance().getSupplierReviewGatherCollection(view);
				
				if(coll.size()>0) {
					this.kdtSupplierEntry.getRow(i).getCell("score2").setValue(coll.get(0).getAmount());
				}
				
				}
			
			}
		} catch (BOSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		replaceLongNumberStr(false);
		attachListeners();
		setAuditButtonStatus(this.getOprtState());
		
		if(this.getOprtState() != OprtState.ADDNEW ) {
    		this.mergeBlock();
    	}
	}
	
	public void storeFields() {
		replaceLongNumberStr(true);
		if(this.kDRBPCYes.isSelected()){
			this.editData.setPaperIsComplete(true);
		}else{
			this.editData.setPaperIsComplete(false);
		}
		Set curProject=new HashSet();
		String curProjectName="";
		for(int i=0;i<this.prjTable.getRowCount();i++){
			CurProjectInfo info = (CurProjectInfo) prjTable.getCell(i, "name").getValue();
			if(info==null) continue;
			if(!curProject.contains(info.getId())){
				curProjectName=curProjectName+info.getName()+",";
				curProject.add(info.getId());
			}
		}
		if(curProjectName.indexOf(",")>0){
			curProjectName=curProjectName.substring(0, curProjectName.lastIndexOf(","));
		}else{
			curProjectName=null;
		}
		this.editData.setCurProjectName(curProjectName);
		/*if(this.kDRBSRYes.isSelected()){
			this.editData.setScalingRules(true);
		}else{
			this.editData.setScalingRules(false);
		}*/
		super.storeFields();
	}
	
	private void mergeBlock() {
		KDTMergeManager merge = prjTable.getMergeManager();
		int count = prjTable.getRowCount();
		if(count==0) {
			return;
		}
		
		CurProjectInfo info = (CurProjectInfo) prjTable.getCell(0, "name").getValue();
		int start=0;
		for(int i=1; i<count; i++ ) {
			CurProjectInfo info2 = (CurProjectInfo) prjTable.getCell(i, "name").getValue();
			if( info!=null && info2!=null && info.getId().toString().equals(info2.getId().toString()) ) {
				continue;
			}else {
				merge.mergeBlock(start, 1, i-1, 1);
				merge.mergeBlock(start, 2, i-1, 2);
				start=i;
				info = info2;
			}
		}
		
		merge.mergeBlock(start, 1, count-1, 1);
		merge.mergeBlock(start, 2, count-1, 2);
	}
	
	public void actionRemove_actionPerformed(ActionEvent e) throws Exception {
		super.actionRemove_actionPerformed(e);
		handleCodingRule();
	}
	public void actionEdit_actionPerformed(ActionEvent e) throws Exception {
		// TODO Auto-generated method stub
		super.actionEdit_actionPerformed(e);
		InvitePurchaseModeInfo modeInfo = (InvitePurchaseModeInfo) this.prmtPurchaseMode.getValue();
		 if(modeInfo==null) {
			 return;
		 }
		 if(modeInfo.getType()==InvitePurchaseModeEnum.SINGLE) {
			 this.actionAddLine.setEnabled(true);
			 this.actionInsertLine.setEnabled(true);
			 this.actionRemoveLine.setEnabled(true);
			 this.actionAddPlan.setEnabled(true);
			 this.actionRemovePlan.setEnabled(true);
			 this.actionInsertPlan.setEnabled(true);
		 }else if(modeInfo.getType()==InvitePurchaseModeEnum.GROUP) {
			 this.actionAddLine.setEnabled(true);
			 this.actionInsertLine.setEnabled(true);
			 this.actionRemoveLine.setEnabled(true);
			 this.actionAddPlan.setEnabled(true);
			 this.actionRemovePlan.setEnabled(true);
			 this.actionInsertPlan.setEnabled(true);
		 }else if(modeInfo.getType()==InvitePurchaseModeEnum.STRATEGY) {
			 this.actionAddLine.setEnabled(false);
			 this.actionRemoveLine.setEnabled(false);
			 this.actionInsertLine.setEnabled(false);
			 this.actionAddPlan.setEnabled(false);
			 this.actionRemovePlan.setEnabled(false);
			 this.actionInsertPlan.setEnabled(false);
		 }
	}
	public void actionSave_actionPerformed(ActionEvent e) throws Exception {
		super.actionSave_actionPerformed(e);
		InvitePurchaseModeInfo modeInfo = (InvitePurchaseModeInfo) this.prmtPurchaseMode.getValue();
		 if(modeInfo==null) {
			 return;
		 }
		 if(modeInfo.getType()==InvitePurchaseModeEnum.SINGLE) {
			 this.actionAddLine.setEnabled(true);
			 this.actionInsertLine.setEnabled(true);
			 this.actionRemoveLine.setEnabled(true);
			 this.actionAddPlan.setEnabled(true);
			 this.actionRemovePlan.setEnabled(true);
			 this.actionInsertPlan.setEnabled(true);
		 }else if(modeInfo.getType()==InvitePurchaseModeEnum.GROUP) {
			 this.actionAddLine.setEnabled(true);
			 this.actionInsertLine.setEnabled(true);
			 this.actionRemoveLine.setEnabled(true);
			 this.actionAddPlan.setEnabled(true);
			 this.actionRemovePlan.setEnabled(true);
			 this.actionInsertPlan.setEnabled(true);
		 }else if(modeInfo.getType()==InvitePurchaseModeEnum.STRATEGY) {
			 this.actionAddLine.setEnabled(false);
			 this.actionRemoveLine.setEnabled(false);
			 this.actionInsertLine.setEnabled(false);
			 this.actionAddPlan.setEnabled(false);
			 this.actionRemovePlan.setEnabled(false);
			 this.actionInsertPlan.setEnabled(false);
		 }
	}
	public void actionSubmit_actionPerformed(ActionEvent e) throws Exception {
		
		//У�������������β���
		if(prmtRespDept.getData() == null){
			FDCMsgBox.showWarning(this,"���β��Ų���Ϊ�գ�");
			SysUtil.abort();
		}
		
		if(prmtRespPerson.getData() == null){
			FDCMsgBox.showWarning(this,"�����˲���Ϊ�գ�");
			SysUtil.abort();
		}
		
		if(this.kdtInviteListEntry.getRowCount()==0){
			FDCMsgBox.showWarning(this,"�ɹ���ϸ��Ϣ����Ϊ�գ�");
			SysUtil.abort();
		}
		for(int i=0;i<this.kdtInviteListEntry.getRowCount();i++){
			if(this.kdtInviteListEntry.getCell(i, "inviteListType").getValue()==null) {
				FDCMsgBox.showWarning(this,"�ɹ���ϸ����Ϊ�գ�");
				this.kdtInviteListEntry.getEditManager().editCellAt(i, this.kdtInviteListEntry.getColumnIndex("inviteListType"));
				SysUtil.abort();
			}
			if(this.kdtInviteListEntry.getCell(i, "amount").getValue()==null) {
				FDCMsgBox.showWarning(this,"Ԥ�Ʋɹ�����Ϊ�գ�");
				this.kdtInviteListEntry.getEditManager().editCellAt(i, this.kdtInviteListEntry.getColumnIndex("amount"));
				SysUtil.abort();
			}
		}
		FDCClientVerifyHelper.verifyEmpty(this, this.txtPlanAmount);
		
		if(this.kdtSupplierEntry.getRowCount()==0){
			FDCMsgBox.showWarning(this,"��Χ��Ӧ�̲���Ϊ�գ�");
			SysUtil.abort();
		}
		for(int i=0; i<this.kdtSupplierEntry.getRowCount(); i++) {
			if(this.kdtSupplierEntry.getCell(i, "supplier").getValue()==null) {
				FDCMsgBox.showWarning(this,"��Ӧ�̲���Ϊ�գ�");
				this.kdtSupplierEntry.getEditManager().editCellAt(i, this.kdtSupplierEntry.getColumnIndex("supplier"));
				SysUtil.abort();
			}
//			if(this.kdtSupplierEntry.getCell(i, "isAccept").getValue()==null) {
//				FDCMsgBox.showWarning(this,"�Ƿ���Χ����Ϊ�գ�");
//				this.kdtSupplierEntry.getEditManager().editCellAt(i, this.kdtSupplierEntry.getColumnIndex("isAccept"));
//				SysUtil.abort();
//			}
			if(this.kdtSupplierEntry.getCell(i, "coState").getValue()==null) {
				FDCMsgBox.showWarning(this,"����״̬����Ϊ�գ�");
				this.kdtSupplierEntry.getEditManager().editCellAt(i, this.kdtSupplierEntry.getColumnIndex("coState"));
				SysUtil.abort();
			}
			if(this.kdtSupplierEntry.getCell(i, "manager").getValue()==null) {
				FDCMsgBox.showWarning(this,"��Ŀ������Ϊ�գ�");
				this.kdtSupplierEntry.getEditManager().editCellAt(i, this.kdtSupplierEntry.getColumnIndex("manager"));
				SysUtil.abort();
			}
		}
		
		
		
		super.actionSubmit_actionPerformed(e);
		this.setOprtState("VIEW");
		this.actionAudit.setVisible(true);
		this.actionAudit.setEnabled(true);
	}
	public void actionAudit_actionPerformed(ActionEvent e) throws Exception {
		super.actionAudit_actionPerformed(e);
		this.actionUnAudit.setVisible(true);
		this.actionUnAudit.setEnabled(true);
		this.actionAudit.setVisible(false);
		this.actionAudit.setEnabled(false);
	}
	public void actionUnAudit_actionPerformed(ActionEvent e) throws Exception {
		super.actionUnAudit_actionPerformed(e);
		this.actionUnAudit.setVisible(false);
		this.actionUnAudit.setEnabled(false);
		this.actionAudit.setVisible(true);
		this.actionAudit.setEnabled(true);
	}
	public void setOprtState(String oprtType) {
		super.setOprtState(oprtType);
		if (oprtType.equals(OprtState.VIEW)) {
			this.lockUIForViewStatus();
		} else {
			this.unLockUI();
		}
	}
	
	private void initEditor() throws Exception {
		FDCClientUtils.setPersonF7(prmtRespPerson, this,null);
		FDCClientUtils.setRespDeptF7(prmtRespDept, this);
		prjTable.checkParsed();
		KDBizPromptBox prmtCurProject = new KDBizPromptBox();
		prmtCurProject.setSelector(new F7ProjectSelector(this));
		prmtCurProject.setDisplayFormat("$name$");
		prmtCurProject.setEditFormat("$number$");
		prmtCurProject.setCommitFormat("$number$");
		prmtCurProject.setRequired(true);
		
		prmtCurProject.addSelectorListener(new SelectorListener() {
			public void willShow(SelectorEvent e) {
				KDBizPromptBox f7 = (KDBizPromptBox) e.getSource();
				f7.getQueryAgent().resetRuntimeEntityView();
				EntityViewInfo view = new EntityViewInfo();
				FilterInfo filter = new FilterInfo();
				
				filter.getFilterItems().add(new FilterItemInfo("fullOrgUnit.longNumber", editData.getOrgUnit().getLongNumber()+"%",CompareType.LIKE));
//				filter.getFilterItems().add(new FilterItemInfo("isLeaf", "1"));
				view.setFilter(filter);
				f7.setEntityViewInfo(view);
		}});
		prjTable.getColumn("name").setEditor(new KDTDefaultCellEditor(prmtCurProject));
		
		//�ɹ��ƻ�������
		KDBizPromptBox prmtPlan = new KDBizPromptBox();
		prmtPlan.setDisplayFormat("$name$");
		prmtPlan.setEditFormat("$number$");
		prmtPlan.setCommitFormat("$number$");
		prmtPlan.setRequired(true);
		prmtPlan.setQueryInfo("com.kingdee.eas.fdc.invite.app.F7PurchasePlanQuery");
		prmtPlan.setEnabledMultiSelection(true);
		prmtPlan.getQueryAgent().setEnabledMultiSelection(true);
		
		prmtPlan.addSelectorListener(new SelectorListener() {
			public void willShow(SelectorEvent e) {
				KDBizPromptBox f7 = (KDBizPromptBox) e.getSource();
				f7.getQueryAgent().resetRuntimeEntityView();
				EntityViewInfo view = new EntityViewInfo();
				FilterInfo filter = new FilterInfo();
				
				int rowIndex = prjTable.getSelectManager().getActiveRowIndex();
				
				CurProjectInfo prj = (CurProjectInfo) prjTable.getCell(rowIndex, "name").getValue();
				if(prj == null) {
					MsgBox.showConfirm2("����ѡ�񹤳���Ŀ");
					SysUtil.abort();
				}
				
				String prjID= prj.getId().toString();
				FDCSQLBuilder sqlBuilder = new FDCSQLBuilder();
				
				sqlBuilder.appendSql("select fid as parentId from T_INV_InviteMonthPlan where fprojectid='" +
						prjID + "' and fstate='4AUDITTED' order by FVersion desc");
				IRowSet rowSet = null;
				String parentId = "";
				try {
					rowSet = sqlBuilder.executeQuery();
					if(rowSet != null && rowSet.size() > 0) {
						while(rowSet.next()){
							parentId = rowSet.getString("parentId");
							break;
						}
					}
				} catch (BOSException e1) {
					handleException(e1);
				} catch (SQLException e2) {
					handleException(e2);
				}
				
				filter.getFilterItems().add(new FilterItemInfo("project.id", prjID));
				filter.getFilterItems().add(new FilterItemInfo("parent.id", parentId));
				view.setFilter(filter);
				f7.setEntityViewInfo(view);
		}});
		prjTable.getColumn("programmingName").setEditor(new KDTDefaultCellEditor(prmtPlan));
		
		String formatString = "yyyy-MM-dd";
		KDDatePicker issueDatePicker = new KDDatePicker();
		prjTable.getColumn("issueDate").setEditor(new KDTDefaultCellEditor(issueDatePicker));
		this.prjTable.getColumn("issueDate").getStyleAttributes().setNumberFormat(formatString);
		
		KDDatePicker startDatePicker = new KDDatePicker();
		prjTable.getColumn("startDate").setEditor(new KDTDefaultCellEditor(startDatePicker));
		this.prjTable.getColumn("startDate").getStyleAttributes().setNumberFormat(formatString);
		
		KDDetailedArea desc = new KDDetailedArea(250, 200);
		desc.setMaxLength(1000);
		prjTable.getColumn("desc").setEditor(new KDTDefaultCellEditor(desc));
	}
	
	/**
	 * ���ĳ��ѡ�����Ŀ�Ƿ����������д���
	 * @param project
	 * @param selectedIndex 
	 * @return
	 */
	private boolean checkProjectExist(CurProjectInfo project, int selectedIndex) {
		String projectId = project.getId().toString();
		for( int i =0; i<prjTable.getRowCount(); i++) {
			if(i==selectedIndex) {
				continue;
			}
			CurProjectInfo prj = (CurProjectInfo) prjTable.getCell(i,"name").getValue();
			if(prj==null) {
				continue;
			}else if(prj.getId().toString().equals(projectId)) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * �Ի�ȡ�ļƻ������ˣ�ȥ����ѡȡ�ļƻ���
	 */
	private void checkProgrammingContractCollection( ProgrammingContractCollection coll, CurProjectInfo project, int selectedIndex ) {
		int end = this.getProjectEndIndex(project, selectedIndex);
		int start = this.getProjectStartIndex(project, selectedIndex);
		
		Iterator<ProgrammingContractInfo> iter = coll.iterator();
		while(iter.hasNext()){
			ProgrammingContractInfo info = iter.next();
			for( int j=start; j<=end; j++ ) {
				Object id = prjTable.getCell(j, "programmingId").getValue();
				if( j==selectedIndex || id == null) {
					continue;
				}
				if(info.getId().toString().equals(id.toString())) {
					iter.remove();
					break;
				}
			}
		}
	}
	
    private IRow clearPlans( CurProjectInfo project, int selectedIndex ) {
    	int startIndex = this.getProjectStartIndex(project, selectedIndex);
    	int endIndex = this.getProjectEndIndex(project, selectedIndex);
    	if( endIndex>startIndex ) {
    		for(int i=endIndex; i>startIndex; i--) {
    			this.prjTable.removeRow(i);
    		}
    	}
    	return prjTable.getRow(startIndex);
    	
    }
    boolean checkPlan = false;
	protected void prjTable_editStopped( KDTEditEvent e ) throws Exception {
			int colIndex = e.getColIndex();
			IRow row = prjTable.getRow(e.getRowIndex());
			if (row == null ) {
				return;
			}
			String key = prjTable.getColumnKey(colIndex);
			if ("name".equals(key)) { //������Ŀ����
				
				CurProjectInfo project = (CurProjectInfo) row.getCell("name").getValue();
				if(project==null) {
					return;
				}else if(!project.isIsLeaf()) {
					row.getCell("name").setValue(null);
					return;
				}
				
				CurProjectInfo oldProject = (CurProjectInfo) e.getOldValue();
				if( oldProject != null && project.getId().equals(oldProject.getId())) {
					return;
				}
				
				if(this.checkProjectExist(project,row.getRowIndex()) ) {
					MsgBox.showConfirm2("��ѡ��Ŀ��"+project.getName() + "�Ѵ���" );
					row.getCell("name").setValue(null);
					return;
				}
				
				if( oldProject != null ) {//������ֵ���������Ŀ�����У�������ʼ��
					removeDataChangeListener(this.prmtPurchaseMode);
				    row.getCell("name").setValue(oldProject);	//������ֵ���Ա���ҿ�ʼ����λ��
				    row = this.clearPlans(oldProject, e.getRowIndex());
				    row.getCell("name").setValue(project);
				    addDataChangeListener(this.prmtPurchaseMode);
				    
				    int start = prjTable.getColumnIndex("name") + 1;
				    for( int i=start; i<prjTable.getColumnCount(); i++ ) {
				    	row.getCell(i).setValue(null);
				    }
				}
				
				row.getCell("number").setValue(project.getLongNumber().replaceAll("!", "."));
//				row.getCell("name").getStyleAttributes().setLocked(true);
				 for( int i=3; i<prjTable.getColumnCount(); i++ ) {
					 prjTable.getCell(0, i).getStyleAttributes().setLocked(false);
					 prjTable.getCell(0, i).getStyleAttributes().setBackground(Color.WHITE);
				 }
			}else if("programmingName".equals(key)) { //�ɹ��ƻ�����
				Object value = e.getValue();
				if( value instanceof InviteMonthPlanEntrysInfo) {//��ѡ
					InviteMonthPlanEntrysInfo info = (InviteMonthPlanEntrysInfo) value;
					InviteMonthPlanEntrysCollection coll = new InviteMonthPlanEntrysCollection();
					coll.add(info);
					
					ProgrammingContractCollection validPCColl = InviteProjectFactory.getRemoteInstance().getValidProgrammingContract(coll);
					CurProjectInfo project = (CurProjectInfo) row.getCell("name").getValue();
					this.checkProgrammingContractCollection(validPCColl, project, row.getRowIndex());
					if(validPCColl.size()>0) {
						row.getCell("programmingName").setValue(validPCColl.get(0));
						row.getCell("programmingId").setValue(validPCColl.get(0).getId());
					}
				}else if( value instanceof Object[] ) {//��ѡ
//					InviteMonthPlanEntrysInfo[] infos = (InviteMonthPlanEntrysInfo[]) value;
					Object[] valueArray = (Object[]) value;
					InviteMonthPlanEntrysCollection coll = new InviteMonthPlanEntrysCollection();
					for(int i=0; i<valueArray.length; i++) {
						coll.add((InviteMonthPlanEntrysInfo) valueArray[i]);
					}
					
					ProgrammingContractCollection validPCColl=null;
					try {
						validPCColl = InviteProjectFactory.getRemoteInstance().getValidProgrammingContract(coll);
					}catch(EASBizException e1) {
						row.getCell("programmingName").setValue(null);
						row.getCell("programmingId").setValue(null);
						MsgBox.showWarning(e1.getMessage());
						return;
					}
					
					
				
					
					
					 if(validPCColl.size()<coll.size() ) {
//						row.getCell("programmingName").setValue(null);
						MsgBox.showWarning("��ѡ�ƻ����б������б��������õļƻ���");
					}
					
					CurProjectInfo project = (CurProjectInfo) row.getCell("name").getValue();
					this.checkProgrammingContractCollection(validPCColl, project, row.getRowIndex());
					int selectedIndex = row.getRowIndex();
					CurProjectInfo prjInfo = (CurProjectInfo) row.getCell("name").getValue();
					for(int i=0; i<validPCColl.size(); i++) {
						if(i>0) {
							row = prjTable.addRow(selectedIndex);
							InviteProjectEntriesInfo entry = new InviteProjectEntriesInfo();
							row.setUserObject(entry);
						}
						
						row.getCell("number").setValue(prjInfo.getLongNumber().replaceAll("!", "."));
						row.getCell("name").setValue(prjInfo);
						row.getCell("programmingName").setValue(validPCColl.get(i));
						row.getCell("programmingId").setValue(validPCColl.get(i).getId());
					}
					
					if(validPCColl.size() == 0) {
						row.getCell("programmingName").setValue(e.getOldValue());
					}
				}
				
				KDTMergeManager merge = prjTable.getMergeManager();
				CurProjectInfo project = (CurProjectInfo) row.getCell("name").getValue();
				if(project == null) {
					return;
				}
				
				int end = this.getProjectEndIndex(project, row.getRowIndex());
				int start = this.getProjectStartIndex(project, row.getRowIndex());
				merge.mergeBlock(start, 1, end, 1);
				merge.mergeBlock(start, 2, end, 2);
			}
    }
	
	//���ӹ�����Ŀ
	public void actionAddLine_actionPerformed(ActionEvent e) throws Exception {
		IRow row = prjTable.addRow();
		InviteProjectEntriesInfo entry = new InviteProjectEntriesInfo();
		row.setUserObject(entry);
		row.getCell("name").getStyleAttributes().setLocked(false);
	}
	
	/**
	 * ������Ŀ��ʼ��λ�á�
	 * @param project
	 * @param selectedIndex
	 * @return
	 */
	private int getProjectStartIndex( CurProjectInfo project, int selectedIndex ) {
		for( int i=selectedIndex-1; i>=0; i-- ) {
			CurProjectInfo prj = (CurProjectInfo) prjTable.getCell(i, "name").getValue();
			if( prj==null || !prj.getId().equals(project.getId()) ) {
				return i+1;
			}
		}
		
		return 0;
	}
	
	private int getProjectEndIndex( CurProjectInfo project, int selectedIndex ) {
		for( int i=selectedIndex+1; i<prjTable.getBody().size(); i++ ) {
			CurProjectInfo prj = (CurProjectInfo) prjTable.getCell(i, "name").getValue();
			if( prj == null || !prj.getId().equals(project.getId()) ) {
				return i-1;
			}
		}
		
		
		return prjTable.getBody().size()-1;
	}
	
	//���빤����Ŀ
	public void actionInsertLine_actionPerformed(ActionEvent e) throws Exception {
		IRow row = null;
		if (this.prjTable.getSelectManager().size() > 0) {
			int top = prjTable.getSelectManager().getActiveRowIndex();
			if (isTableColumnSelected(this.prjTable)) {
				row = this.prjTable.addRow();
			}
			else {
				IRow topRow = this.prjTable.getRow(top);
				CurProjectInfo project = (CurProjectInfo) topRow.getCell("name").getValue();
				if(project==null || top==0) {
					row = this.prjTable.addRow(top);
				}else {
					int projectStartIndex = getProjectStartIndex(project, top);
					projectStartIndex = projectStartIndex==-1?0:projectStartIndex;
					row = this.prjTable.addRow(projectStartIndex);
				}
			}
		} else {
			row = this.prjTable.addRow();
		}
		InviteProjectEntriesInfo entry = new InviteProjectEntriesInfo();
		row.setUserObject(entry);
		row.getCell("name").getStyleAttributes().setLocked(false);
	}
	
	//ɾ��������Ŀ
	public void actionRemoveLine_actionPerformed(ActionEvent e) throws Exception {
		if (this.prjTable.getSelectManager().size() == 0 || isTableColumnSelected(this.prjTable)) {
				FDCMsgBox.showInfo(this, EASResource.getString("com.kingdee.eas.framework.FrameWorkResource.Msg_NoneEntry"));
				return;
		}
		 
		int top = this.prjTable.getSelectManager().getActiveRowIndex();
		
		CurProjectInfo prjInfo = (CurProjectInfo) this.prjTable.getCell(top, "name").getValue();
		if(prjInfo==null) {
			this.prjTable.removeRow(top);
			return;
		}
		
		String selectedPrjID = prjInfo.getId().toString();
		int end = this.getProjectEndIndex(prjInfo, top);
		int start = this.getProjectStartIndex(prjInfo, top);
		
		for( int i=end; i>=start; i--) {
			String prjID = ((CurProjectInfo) this.prjTable.getCell(i, "name").getValue()).getId().toString();
			
			if(selectedPrjID.equals(prjID)) {
				this.prjTable.getRow(i).setUserObject(null);
				this.prjTable.removeRow(i);
			}
		}
	 }
	 
	//���Ӽƻ�
	 public void actionAddPlan_actionPerformed(ActionEvent e) throws Exception {
		 if (this.prjTable.getSelectManager().size() == 0 || isTableColumnSelected(this.prjTable)) {
				FDCMsgBox.showInfo(this, "δѡ�з�¼");
				return;
		 }
		 
		 IRow row = FDCTableHelper.getSelectedRow(prjTable);
		 CurProjectInfo project = (CurProjectInfo) row.getCell("name").getValue();
		 if(project==null) {
			 MsgBox.showConfirm2("��ѡ����δ¼�빤����Ŀ��");
			 SysUtil.abort();
		 }
		 
		 int endIndex = getProjectEndIndex(project, row.getRowIndex());
		 IRow newRow = null;
		 if(endIndex==prjTable.getRowCount()) {
			 newRow = prjTable.addRow();
		 }else {
			 newRow = prjTable.addRow(endIndex+1);
		 }
		 
		 newRow.getCell("name").setValue(project);
		 newRow.getCell("number").setValue(project.getLongNumber().replaceAll("!", "."));
		 
		 InviteProjectEntriesInfo entry = new InviteProjectEntriesInfo();
		 newRow.setUserObject(entry);
	 }
	
	 //����ƻ�
	 public void actionInsertPlan_actionPerformed(ActionEvent e) throws Exception {
		 if (this.prjTable.getSelectManager().size() == 0 || isTableColumnSelected(this.prjTable)) {
				FDCMsgBox.showInfo(this, "δѡ�з�¼");
				return;
		 }
		 
		 IRow row = FDCTableHelper.getSelectedRow(prjTable);
		 CurProjectInfo project = (CurProjectInfo) row.getCell("name").getValue();
		 if(project==null) {
			 MsgBox.showConfirm2("��ѡ����δ¼�빤����Ŀ��");
			 SysUtil.abort();
		 }
		 
		 IRow newRow = prjTable.addRow(row.getRowIndex());
		 newRow.getCell("name").setValue(project);
		 newRow.getCell("number").setValue(project.getLongNumber().replaceAll("!", "."));
		 
		 InviteProjectEntriesInfo entry = new InviteProjectEntriesInfo();
		 newRow.setUserObject(entry);
	 }
	 
	 
	 //ɾ���ƻ�
	 public void actionRemovePlan_actionPerformed(ActionEvent e) throws Exception {
		 if( prjTable.getRowCount()<=1) {
				MsgBox.showConfirm2("������һ��������Ŀ");
				SysUtil.abort();
		 }
		 
		 if (this.prjTable.getSelectManager().size() == 0 || isTableColumnSelected(this.prjTable)) {
				FDCMsgBox.showInfo(this, EASResource.getString("com.kingdee.eas.framework.FrameWorkResource.Msg_NoneEntry"));
				return;
		 }
		 
		 int i = prjTable.getSelectManager().getActiveRowIndex();
		 this.prjTable.getRow(i).setUserObject(null);
		 this.prjTable.removeRow(i);
	 }
	 
	 protected void prmtPurchaseMode_dataChanged(DataChangeEvent e) throws Exception {
		 boolean isChanged = true;
		 isChanged = BizCollUtil.isF7ValueChanged(e);
         if(!isChanged){
        	 return;
         }

		 InvitePurchaseModeInfo modeInfo = (InvitePurchaseModeInfo) e.getNewValue();
		 boolean showAlarm = false;
		 if(modeInfo==null) {
			 return;
		 }
		 if(modeInfo.getType()==InvitePurchaseModeEnum.SINGLE) {
			 for(int i =0; i<prjTable.getRowCount(); i++ ) {
				 if( prjTable.getCell(i, "name").getValue() != null ) {
					 showAlarm = true;
					 break;
				 }
			 }
			 
			 if(showAlarm) {
				 int choice = MsgBox.showConfirm2("���Ϊ����ɹ���������й�����Ŀ");
				 if(choice == 0) {
					 this.editData.getEntries().clear();
				 }else {
					 removeDataChangeListener(this.prmtPurchaseMode);
					 prmtPurchaseMode.setValue(e.getOldValue());
					 addDataChangeListener(this.prmtPurchaseMode);
					 return;
				 } 
			 }
			 prjTable.removeRows();
//			 this.actionAddLine_actionPerformed(null);
//			 prjTable.addRow();
			 
			 this.actionAddLine.setEnabled(true);
			 this.actionInsertLine.setEnabled(true);
			 this.actionRemoveLine.setEnabled(true);
			 this.actionAddPlan.setEnabled(true);
			 this.actionRemovePlan.setEnabled(true);
			 this.actionInsertPlan.setEnabled(true);
			 
			
		 }else if(modeInfo.getType()==InvitePurchaseModeEnum.GROUP) {
			 this.actionAddLine.setEnabled(true);
			 this.actionInsertLine.setEnabled(true);
			 this.actionRemoveLine.setEnabled(true);
			 this.actionAddPlan.setEnabled(true);
			 this.actionRemovePlan.setEnabled(true);
			 this.actionInsertPlan.setEnabled(true);
			 
			 if(prjTable.getRowCount()<=0) {
//				 this.actionAddLine_actionPerformed(null);
			 }
			 
			 for( int i=0; i<prjTable.getRowCount(); i++ ) {
				 for( int j=3; j<prjTable.getColumnCount(); j++ ) {
					 prjTable.getCell(i, j).getStyleAttributes().setBackground(Color.WHITE);
					 prjTable.getCell(i, j).getStyleAttributes().setLocked(false);
				 }
			 }
		 }else if(modeInfo.getType()==InvitePurchaseModeEnum.STRATEGY) {
			 this.actionAddLine.setEnabled(false);
			 this.actionRemoveLine.setEnabled(false);
			 this.actionInsertLine.setEnabled(false);
			 this.actionAddPlan.setEnabled(false);
			 this.actionRemovePlan.setEnabled(false);
			 this.actionInsertPlan.setEnabled(false);
			 
			 for( int i=0; i<prjTable.getRowCount(); i++ ) {
				 if(prjTable.getCell(i, "name").getValue() != null ) {
					 showAlarm = true;
					 break;
				 }
			 }
			 
			 if(showAlarm) {
				 int choice = MsgBox.showConfirm2("���Ϊս�Բɹ���������й�����Ŀ");
				 if(choice == 0) {
					 this.editData.getEntries().clear();
				 }else {
					 removeDataChangeListener(this.prmtPurchaseMode);
					 prmtPurchaseMode.setValue(e.getOldValue());
					 addDataChangeListener(this.prmtPurchaseMode);
					 return;
				 } 
			 }	
			 prjTable.removeRows();
		 }
		 
		 if(modeInfo.getType()==InvitePurchaseModeEnum.GROUP){
			 KDBizPromptBox prmtCurProject = new KDBizPromptBox();
			 prmtCurProject.setSelector(new F7ProjectSelector(this,true));
			 prmtCurProject.setDisplayFormat("$name$");
			 prmtCurProject.setEditFormat("$number$");
			 prmtCurProject.setCommitFormat("$number$");
			 prmtCurProject.setRequired(true);
			 prjTable.getColumn("name").setEditor(new KDTDefaultCellEditor(prmtCurProject));
		 }else{
			 KDBizPromptBox prmtCurProject = new KDBizPromptBox();
			 prmtCurProject.setSelector(new F7ProjectSelector(this,false));
			 prmtCurProject.setDisplayFormat("$name$");
			 prmtCurProject.setEditFormat("$number$");
			 prmtCurProject.setCommitFormat("$number$");
			 prmtCurProject.setRequired(true);
			 prjTable.getColumn("name").setEditor(new KDTDefaultCellEditor(prmtCurProject));
		 }
	 }
	 
	 protected void setComponentEnable( boolean enabled) {
		 this.txtName.setAccessAuthority(CtrlCommonConstant.AUTHORITY_COMMON);
		 this.txtNumber.setAccessAuthority(CtrlCommonConstant.AUTHORITY_COMMON);
		 this.prmtInviteType.setAccessAuthority(CtrlCommonConstant.AUTHORITY_COMMON);
		 this.prmtInviteForm.setAccessAuthority(CtrlCommonConstant.AUTHORITY_COMMON);
		 this.prmtPurchaseMode.setAccessAuthority(CtrlCommonConstant.AUTHORITY_COMMON);
		 this.combPriceMode.setAccessAuthority(CtrlCommonConstant.AUTHORITY_COMMON);
		 this.prmtScalingRule.setAccessAuthority(CtrlCommonConstant.AUTHORITY_COMMON);
		 combProcurementType.setAccessAuthority(CtrlCommonConstant.AUTHORITY_COMMON);
		 combAuth.setAccessAuthority(CtrlCommonConstant.AUTHORITY_COMMON);
		
		 txtName.setEnabled(enabled);
		 txtNumber.setEnabled(false);
		 prmtInviteType.setEditable(false);
		 prmtInviteType.setEnabled(enabled);
		 prmtInviteForm.setEnabled(enabled);
		 prmtPurchaseMode.setEnabled(enabled);
		 combPriceMode.setEnabled(enabled);
		 combProcurementType.setEnabled(enabled);
		 combAuth.setEnabled(enabled);
		 prmtScalingRule.setEnabled(enabled);
		 kDRBPCYes.setEnabled(enabled);
		 kDRBPCNo.setEnabled(enabled);
		 prjTable.getStyleAttributes().setLocked(enabled);
		 
		 this.actionAddLine.setEnabled(enabled);
		 this.actionRemoveLine.setEnabled(enabled);
		 this.actionInsertLine.setEnabled(enabled);
		 this.actionAddPlan.setEnabled(enabled);
		 this.actionRemovePlan.setEnabled(enabled);
		 this.actionInsertPlan.setEnabled(enabled);
		 
	 }
	 public void actionPrint_actionPerformed(ActionEvent e) throws Exception {
			ArrayList idList = new ArrayList();
			if (editData != null && !StringUtils.isEmpty(editData.getString("id"))) {
				idList.add(editData.getString("id"));
			}
			if (idList == null || idList.size() == 0 || getTDQueryPK() == null
					|| getTDFileName() == null) {
				MsgBox.showWarning(this, FDCClientUtils.getRes("cantPrint"));
				return;
			}
			InviteProjectPrintProvider data = new InviteProjectPrintProvider(
					editData.getString("id"), getTDQueryPK());
			com.kingdee.bos.ctrl.report.forapp.kdnote.client.KDNoteHelper appHlp = new com.kingdee.bos.ctrl.report.forapp.kdnote.client.KDNoteHelper();
			appHlp.print(getTDFileName(), data, javax.swing.SwingUtilities
					.getWindowAncestor(this));
		}

		public void actionPrintPreview_actionPerformed(ActionEvent e)
				throws Exception {
			ArrayList idList = new ArrayList();
			if (editData != null && !StringUtils.isEmpty(editData.getString("id"))) {
				idList.add(editData.getString("id"));
			}
			if (idList == null || idList.size() == 0 || getTDQueryPK() == null
					|| getTDFileName() == null) {
				MsgBox.showWarning(this, FDCClientUtils.getRes("cantPrint"));
				return;
			}
			InviteProjectPrintProvider data = new InviteProjectPrintProvider(
					editData.getString("id"), getTDQueryPK());
			com.kingdee.bos.ctrl.report.forapp.kdnote.client.KDNoteHelper appHlp = new com.kingdee.bos.ctrl.report.forapp.kdnote.client.KDNoteHelper();
			appHlp.printPreview(getTDFileName(), data, javax.swing.SwingUtilities
					.getWindowAncestor(this));
		}
		protected String getTDFileName() {
			return "/bim/fdc/invite/InviteProject";
		}

		// ��Ӧ���״�Query
		protected IMetaDataPK getTDQueryPK() {
			return new MetaDataPK(
					"com.kingdee.eas.fdc.invite.app.InviteProjectPrintQuery");
		}
		public void actionSupplierALine_actionPerformed(ActionEvent arg0)
				throws Exception {
			IRow row = this.kdtSupplierEntry.addRow();
			InviteSupplierEntryInfo info = new InviteSupplierEntryInfo();
			if(info == null ) {
				return;
			}
			info.setId(BOSUuid.create(info.getBOSType()));
			row.setUserObject(info);
			row.getCell("isAccept").setValue(DefaultStatusEnum.YES);
		}
		public void actionSupplierILine_actionPerformed(ActionEvent arg0)
				throws Exception {
			IRow row = null;
			if (this.kdtSupplierEntry.getSelectManager().size() > 0) {
				int top = this.kdtSupplierEntry.getSelectManager().get().getTop();
				if (isTableColumnSelected(this.kdtSupplierEntry))
					row = this.kdtSupplierEntry.addRow();
				else
					row = this.kdtSupplierEntry.addRow(top);
			} else {
				row = this.kdtSupplierEntry.addRow();
			}
			InviteSupplierEntryInfo info = new InviteSupplierEntryInfo();
			info.setId(BOSUuid.create(info.getBOSType()));
			row.setUserObject(info);
			row.getCell("isAccept").setValue(DefaultStatusEnum.YES);
		}
		public boolean confirmRemove(Component comp) {
			return FDCMsgBox.isYes(FDCMsgBox.showConfirm2(comp, EASResource.getString("com.kingdee.eas.framework.FrameWorkResource.Confirm_Delete")));
		}
		public void actionSupplierRLine_actionPerformed(ActionEvent arg0)
				throws Exception {
			if (this.kdtSupplierEntry.getSelectManager().size() == 0 || isTableColumnSelected(this.kdtSupplierEntry)) {
				FDCMsgBox.showInfo(this, EASResource.getString("com.kingdee.eas.framework.FrameWorkResource.Msg_NoneEntry"));
				return;
			}
			if (confirmRemove(this)) {
				int top = this.kdtSupplierEntry.getSelectManager().get().getBeginRow();
				int bottom = this.kdtSupplierEntry.getSelectManager().get().getEndRow();
				for (int i = top; i <= bottom; i++) {
					if (this.kdtSupplierEntry.getRow(top) == null) {
						FDCMsgBox.showInfo(this, EASResource.getString("com.kingdee.eas.framework.FrameWorkResource.Msg_NoneEntry"));
						return;
					}
					this.kdtSupplierEntry.removeRow(top);
				}
			}
		}
		protected String getResource(String msg) {
			return EASResource.getString("com.kingdee.eas.fdc.invite.supplier.SupplierResource", msg);
		}
		protected void initTable() {
			KDWorkButton btnAddRowinfo = new KDWorkButton();
			KDWorkButton btnInsertRowinfo = new KDWorkButton();
			KDWorkButton btnDeleteRowinfo = new KDWorkButton();

			this.actionSupplierALine.putValue("SmallIcon", EASResource.getIcon("imgTbtn_addline"));
			btnAddRowinfo = (KDWorkButton) this.contInviteSupplier.add(this.actionSupplierALine);
			btnAddRowinfo.setText(getResource("addRow"));
			btnAddRowinfo.setSize(new Dimension(140, 19));

			this.actionSupplierILine.putValue("SmallIcon", EASResource.getIcon("imgTbtn_insert"));
			btnInsertRowinfo = (KDWorkButton) this.contInviteSupplier.add(this.actionSupplierILine);
			btnInsertRowinfo.setText(getResource("insertRow"));
			btnInsertRowinfo.setSize(new Dimension(140, 19));

			this.actionSupplierRLine.putValue("SmallIcon", EASResource.getIcon("imgTbtn_deleteline"));
			btnDeleteRowinfo = (KDWorkButton) this.contInviteSupplier.add(this.actionSupplierRLine);
			btnDeleteRowinfo.setText(getResource("deleteRow"));
			btnDeleteRowinfo.setSize(new Dimension(140, 19));

			KDTextField testField = new KDTextField();
			testField.setMaxLength(80);
			KDTDefaultCellEditor editorSize = new KDTDefaultCellEditor(testField);

//			KDTextField testField1 = new KDTextField();
//	    	testField1.setMaxLength(2000);
//			KDTDefaultCellEditor editorSize1 = new KDTDefaultCellEditor(testField1);
			
			KDDetailedArea desc = new KDDetailedArea(250, 200);
			desc.setMaxLength(50000);
			KDTDefaultCellEditor editorDesc=new KDTDefaultCellEditor(desc);
			
			this.kdtSupplierEntry.checkParsed();
			FDCTableHelper.disableAutoAddLine(this.kdtSupplierEntry);
			IColumn col = this.kdtSupplierEntry.addColumn(1);
			col.setKey("inviteType");
			col.setWidth(100);
			col.getStyleAttributes().setLocked(true);
			this.kdtSupplierEntry.getHeadRow(0).getCell("inviteType").setValue("�ɹ����");

			this.kdtSupplierEntry.getColumn("linkPerson").setEditor(editorSize);
		 	this.kdtSupplierEntry.getColumn("linkPhone").setEditor(editorSize);
		 	this.kdtSupplierEntry.getColumn("coState").setEditor(editorSize);
		 	
		 	KDComboBox combo = new KDComboBox();
	        for(int i = 0; i < IsGradeEnum.getEnumList().size(); i++){
	        	combo.addItem(IsGradeEnum.getEnumList().get(i));
	        }
	        KDTDefaultCellEditor comboEditor = new KDTDefaultCellEditor(combo);
	        this.kdtSupplierEntry.getColumn("supplierState").setEditor(comboEditor);
		 	this.kdtSupplierEntry.getColumn("supplierState").getStyleAttributes().setLocked(true);
		 	
		 	combo = new KDComboBox();
	        for(int i = 0; i < ResultEnum.getEnumList().size(); i++){
	        	combo.addItem(ResultEnum.getEnumList().get(i));
	        }
	        comboEditor = new KDTDefaultCellEditor(combo);
			this.kdtSupplierEntry.getColumn("result").setEditor(comboEditor);
			
		 	this.kdtSupplierEntry.getColumn("reason").setEditor(editorSize);
		 	
		 	combo = new KDComboBox();
	        for(int i = 0; i < DefaultStatusEnum.getEnumList().size(); i++){
	        	combo.addItem(DefaultStatusEnum.getEnumList().get(i));
	        }
	        comboEditor = new KDTDefaultCellEditor(combo);
		 	this.kdtSupplierEntry.getColumn("isAccept").setEditor(comboEditor);
		 		
			this.kdtSupplierEntry.getColumn("remark").setEditor(editorDesc);
			this.kdtSupplierEntry.getColumn("remark").getStyleAttributes().setWrapText(true);
			
			this.kdtSupplierEntry.getColumn("recommended").getStyleAttributes().setLocked(true);
			
			this.kdtSupplierEntry.getColumn("manager").getStyleAttributes().setLocked(false);
			/*if(getUIContext().containsKey("isFromWorkflow")) {
				kdtEntry.getColumn("isAccept").getStyleAttributes().setLocked(false);
			}*/
			
			this.kdtSupplierEntry.getColumn("isAccept").getStyleAttributes().setLocked(false);
			
			this.kdtSupplierEntry.getColumn("supplier").getStyleAttributes().setFontColor(Color.BLUE);
		
		
			KDBizPromptBox f7Box = new KDBizPromptBox(); 
			f7Box.setDisplayFormat("$name$");
			f7Box.setEditFormat("$number$");
			f7Box.setCommitFormat("$number$");
			f7Box.setQueryInfo("com.kingdee.eas.fdc.invite.supplier.app.NewSupplierStockQuery");
			
			EntityViewInfo view = new EntityViewInfo();
			FilterInfo filter = new FilterInfo();
			view.setFilter(filter);
			
			
				Set org=new HashSet();
				FullOrgUnitInfo curFullOrg = SysContext.getSysContext().getCurrentOrgUnit().castToFullOrgUnitInfo();
				org.add(OrgConstants.DEF_CU_ID);
				if(curFullOrg.isIsPurchaseOrgUnit()) {
					org.add(curFullOrg.getId().toString());
				}
				
				while(curFullOrg.getParent() !=null && !OrgConstants.DEF_CU_ID.equals(curFullOrg.getParent().getId().toString()) ) {
					FullOrgUnitInfo parent = curFullOrg.getParent();
					try {
						parent = FullOrgUnitFactory.getRemoteInstance().getFullOrgUnitInfo(new ObjectUuidPK(parent.getId()));
					} catch (EASBizException e) {
						e.printStackTrace();
					} catch (BOSException e) {
						e.printStackTrace();
					}
					
					if(parent.isIsPurchaseOrgUnit()) {
						org.add(parent.getId().toString());
						break;
					}
					curFullOrg = parent;
				}
				filter.getFilterItems().add(new FilterItemInfo("purchaseOrgUnit.id",org,CompareType.INCLUDE));
				
			
			filter.getFilterItems().add(new FilterItemInfo("isPass",Integer.valueOf(IsGradeEnum.ELIGIBILITY_VALUE)));
			filter.getFilterItems().add(new FilterItemInfo("isPass",0));
			filter.getFilterItems().add(new FilterItemInfo("isPass",null,CompareType.IS));
//			filter.getFilterItems().add(new FilterItemInfo("isPass",Integer.valueOf(IsGradeEnum.TEMP_VALUE)));
			filter.getFilterItems().add(new FilterItemInfo("state",Integer.valueOf(SupplierStateEnum.APPROVE_VALUE)));
			
			filter.setMaskString("#0 and (#1 or #2 or #3) and #4");
			
			f7Box.setEntityViewInfo(view);
			f7Box.addDataChangeListener(new DataChangeListener(){

				public void dataChanged(DataChangeEvent e) {
					if(e.getNewValue() != null && !e.getNewValue().equals(e.getOldValue())){
						
						SupplierStockInfo info = (SupplierStockInfo) e.getNewValue();
						int rowIndex = kdtSupplierEntry.getSelectManager().getActiveRowIndex();
						if(info.getInviteType()!=null){
							
							try {
								InviteTypeInfo i = InviteTypeFactory.getRemoteInstance().getInviteTypeInfo(new ObjectUuidPK(info.getInviteType().getId()));
								kdtSupplierEntry.getCell(rowIndex, "inviteType").setValue(i.getName());
							} catch (EASBizException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							} catch (BOSException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
						}
						
					}
					
				}});
			
			KDTDefaultCellEditor f7Editor = new KDTDefaultCellEditor(f7Box);
			
			this.kdtSupplierEntry.getColumn("supplier").setEditor(f7Editor);
			
			
			
			this.kdtInviteListEntry.checkParsed();
			
			KDWorkButton btnInviteAddRowinfo = new KDWorkButton();
			KDWorkButton btnInviteInsertRowinfo = new KDWorkButton();
			KDWorkButton btnInviteDeleteRowinfo = new KDWorkButton();

			this.actionInviteListEntryALine.putValue("SmallIcon", EASResource.getIcon("imgTbtn_addline"));
			btnInviteAddRowinfo = (KDWorkButton) this.contInviteListEntry.add(this.actionInviteListEntryALine);
			btnInviteAddRowinfo.setText("������");
			btnInviteAddRowinfo.setSize(new Dimension(140, 19));

			this.actionInviteListEntryILine.putValue("SmallIcon", EASResource.getIcon("imgTbtn_insert"));
			btnInviteInsertRowinfo = (KDWorkButton) contInviteListEntry.add(this.actionInviteListEntryILine);
			btnInviteInsertRowinfo.setText("������");
			btnInviteInsertRowinfo.setSize(new Dimension(140, 19));

			this.actionInviteListEntryRLine.putValue("SmallIcon", EASResource.getIcon("imgTbtn_deleteline"));
			btnInviteDeleteRowinfo = (KDWorkButton) contInviteListEntry.add(this.actionInviteListEntryRLine);
			btnInviteDeleteRowinfo.setText("ɾ����");
			btnInviteDeleteRowinfo.setSize(new Dimension(140, 19));
			
			
			KDFormattedTextField amount = new KDFormattedTextField();
			amount.setDataType(KDFormattedTextField.BIGDECIMAL_TYPE);
			amount.setDataVerifierType(KDFormattedTextField.NO_VERIFIER);
			amount.setNegatived(false);
			amount.setPrecision(2);
			KDTDefaultCellEditor amountEditor = new KDTDefaultCellEditor(amount);
			
			this.kdtInviteListEntry.getColumn("amount").setEditor(amountEditor);
			this.kdtInviteListEntry.getColumn("amount").getStyleAttributes().setNumberFormat("#,##0.00;-#,##0.00");
			this.kdtInviteListEntry.getColumn("amount").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.getAlignment("right"));
			this.kdtInviteListEntry.getColumn("amount").setRequired(true);
			
			this.kdtInviteListEntry.getColumn("price").setEditor(amountEditor);
			this.kdtInviteListEntry.getColumn("price").getStyleAttributes().setNumberFormat("#,##0.00;-#,##0.00");
			this.kdtInviteListEntry.getColumn("price").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.getAlignment("right"));
			
			this.kdtInviteListEntry.getColumn("num").setEditor(amountEditor);
			this.kdtInviteListEntry.getColumn("num").getStyleAttributes().setNumberFormat("#,##0.00;-#,##0.00");
			this.kdtInviteListEntry.getColumn("num").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.getAlignment("right"));
			
			f7Box = new KDBizPromptBox();
			f7Box.setQueryInfo("com.kingdee.eas.fdc.invite.supplier.app.InviteListTypeQuery");
			f7Box.setEnabledMultiSelection(false);
			f7Box.setEditFormat("$name$");
			f7Box.setDisplayFormat("$name$");
			f7Box.setCommitFormat("$name$");
			
			view = new EntityViewInfo();
			filter = new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("isEnabled",Boolean.TRUE));
			filter.getFilterItems().add(new FilterItemInfo("isLeaf",Boolean.TRUE));
			view.setFilter(filter);
			f7Box.setEntityViewInfo(view);
			f7Editor = new KDTDefaultCellEditor(f7Box);
			this.kdtInviteListEntry.getColumn("inviteListType").setEditor(f7Editor);
			this.kdtInviteListEntry.getColumn("inviteListType").setRequired(true);
		}
		protected void kdtEntry_tableClicked(com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e) throws Exception {
			//���ݴ�͸
			 if (e.getType() == KDTStyleConstants.BODY_ROW && e.getButton() == MouseEvent.BUTTON1 ){
					IRow row=KDTableUtil.getSelectedRow(kdtSupplierEntry);
					if(kdtSupplierEntry.getColumnKey(e.getColIndex()).equals("supplier")&& e.getClickCount() == 2) {
						if(row.getCell("supplier").getValue() instanceof SupplierStockInfo&&
								row.getCell("supplier").getValue()!=null) {
							setCursorOfWair();
							SupplierStockInfo supplier = (SupplierStockInfo) row.getCell("supplier").getValue();
							UIContext uiContext = new UIContext(this);
							uiContext.put(UIContext.ID, supplier.getId().toString());
							IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.NEWTAB).create(NoPerSupplierStockEditUI.class.getName(), uiContext, null,"VIEW");
							uiWindow.show();
							setCursorOfDefault();
						}
					}
					if(kdtSupplierEntry.getColumnKey(e.getColIndex()).equals("remark")){
						this.showDialog(this, kdtSupplierEntry, 250, 200, 50000);
					}
		      }
		 }
		public void showDialog(JComponent owner, KDTable table, int X, int Y, int len)
	    {
	        int rowIndex = table.getSelectManager().getActiveRowIndex();
	        int colIndex = table.getSelectManager().getActiveColumnIndex();
	        ICell cell = table.getCell(rowIndex, colIndex);
	        if(cell.getValue() == null)
	            return;
	        BasicView view = table.getViewManager().getView(5);
	        Point p = view.getLocationOnScreen();
	        Rectangle rect = view.getCellRectangle(rowIndex, colIndex);
	        java.awt.Window parent = SwingUtilities.getWindowAncestor(owner);
	        KDDetailedAreaDialog dialog;
	        if(parent instanceof Dialog)
	        {
	            dialog = new KDDetailedAreaDialog((Dialog)parent, X, Y, true);
	        }
	        else
	        if(parent instanceof Frame){
	            dialog = new KDDetailedAreaDialog((Frame)parent, X, Y, true);
	        }
	        else{
	            dialog = new KDDetailedAreaDialog(true);
	        }
	        String vals = cell.getValue().toString();
	        dialog.setData(vals);
	        dialog.setPRENTE_X(p.x + rect.x + rect.width);
	        dialog.setPRENTE_Y(p.y + rect.y);
	        dialog.setMaxLength(len);
	        dialog.setEditable(false);
	        dialog.show();
	    }
		protected void kdtInviteListEntry_editStopped(KDTEditEvent e)
				throws Exception {
			if("amount".equals(this.kdtInviteListEntry.getColumn(e.getColIndex()).getKey())){
				BigDecimal amount=FDCHelper.ZERO;
				for(int i=0;i<this.kdtInviteListEntry.getRowCount();i++){
					amount=FDCHelper.add(amount, this.kdtInviteListEntry.getRow(i).getCell("amount").getValue());
				}
				this.txtPlanAmount.setValue(amount);
			}
		}
		protected void kdtEntry_editStopped(KDTEditEvent e) throws Exception {
			if("supplier".equals(this.kdtSupplierEntry.getColumn(e.getColIndex()).getKey())){
				SupplierStockInfo supplier=(SupplierStockInfo) this.kdtSupplierEntry.getRow(e.getRowIndex()).getCell("supplier").getValue();
				if(supplier!=null){
					for(int i=0;i<this.kdtSupplierEntry.getRowCount();i++){
						if(i==e.getRowIndex()) continue;
						SupplierStockInfo sameSupplier=(SupplierStockInfo) this.kdtSupplierEntry.getRow(i).getCell("supplier").getValue();
						if(sameSupplier!=null&&supplier.getId().toString().equals(sameSupplier.getId().toString())){
							FDCMsgBox.showWarning(this,supplier.getName()+"�Ѵ��ڣ�");
							this.kdtSupplierEntry.getRow(e.getRowIndex()).getCell("supplier").setValue(null);
							this.kdtSupplierEntry.getRow(e.getRowIndex()).getCell("supplierState").setValue(null);
							this.kdtSupplierEntry.getRow(e.getRowIndex()).getCell("grade").setValue(null);
							this.kdtSupplierEntry.getRow(e.getRowIndex()).getCell("recommended").setValue(null);
							this.kdtSupplierEntry.getRow(e.getRowIndex()).getCell("linkPerson").setValue(null);
							this.kdtSupplierEntry.getRow(e.getRowIndex()).getCell("linkPhone").setValue(null);
							return;
						}
					}
					this.kdtSupplierEntry.getRow(e.getRowIndex()).getCell("supplierState").setValue(supplier.getIsPass());
					if(supplier.getGrade()!=null){
						this.kdtSupplierEntry.getRow(e.getRowIndex()).getCell("grade").setValue(GradeSetUpFactory.getRemoteInstance().getGradeSetUpInfo(new ObjectUuidPK(supplier.getGrade().getId())).getName());
					}
					if(supplier.getState()== SupplierStateEnum.APPROVE && supplier.getQuaLevel()!=null) {
						this.kdtSupplierEntry.getRow(e.getRowIndex()).getCell("quaLevel").setValue(QuaLevelFactory.getRemoteInstance().getQuaLevelInfo(new ObjectUuidPK(supplier.getQuaLevel().getId())).getName());
					}
					
					//��Լ�÷�...
					EntityViewInfo view = new EntityViewInfo();
					SelectorItemCollection sic = new SelectorItemCollection();
					sic.add("evaluationType.number");
					sic.add("supplier.id");
					sic.add("amount");
					view.setSelector(sic);
					
					view.setTopCount(1);
					FilterInfo filter = new FilterInfo();
					filter.getFilterItems().add(new FilterItemInfo("supplier.id", supplier.getId().toString()));
					filter.getFilterItems().add(new FilterItemInfo("evaluationType.number", "003"));
					filter.getFilterItems().add(new FilterItemInfo("evaluationType.number", "004"));
					filter.getFilterItems().add(new FilterItemInfo("state", FDCBillStateEnum.AUDITTED_VALUE));
					filter.setMaskString("#0 and (#1 or #2) and #3");
					view.setFilter(filter);
					
					SorterItemCollection sorter = new SorterItemCollection();
					SorterItemInfo sorterInfo = new SorterItemInfo("bizDate");
					sorterInfo.setSortType(SortType.DESCEND);
					sorter.add(sorterInfo);
					view.setSorter(sorter);
					SupplierReviewGatherCollection coll = SupplierReviewGatherFactory.getRemoteInstance().getSupplierReviewGatherCollection(view);
					if(coll.size()>0) {
						this.kdtSupplierEntry.getRow(e.getRowIndex()).getCell("score1").setValue(coll.get(0).getAmount());
					}
					
					//����÷֡���
					filter = new FilterInfo();
					filter.getFilterItems().add(new FilterItemInfo("supplier.id", supplier.getId().toString()));
					filter.getFilterItems().add(new FilterItemInfo("evaluationType.number", "002"));
					filter.getFilterItems().add(new FilterItemInfo("state", FDCBillStateEnum.AUDITTED_VALUE));
					view.setFilter(filter);
					coll = SupplierReviewGatherFactory.getRemoteInstance().getSupplierReviewGatherCollection(view);
					if(coll.size()>0) {
						this.kdtSupplierEntry.getRow(e.getRowIndex()).getCell("score2").setValue(coll.get(0).getAmount());
					}
					
					
					this.kdtSupplierEntry.getRow(e.getRowIndex()).getCell("recommended").setValue(supplier.getRecommended());
					view = new EntityViewInfo();
					filter = new FilterInfo();
					view.setFilter(filter);
					filter.getFilterItems().add(new FilterItemInfo("parent.id",supplier.getId().toString()));
					filter.getFilterItems().add(new FilterItemInfo("isDefault",Boolean.TRUE));
					
					SupplierLinkPersonCollection person=SupplierLinkPersonFactory.getRemoteInstance().getSupplierLinkPersonCollection(view);
					if(person.size()>0){
						this.kdtSupplierEntry.getRow(e.getRowIndex()).getCell("linkPerson").setValue(person.get(0).getPersonName());
						this.kdtSupplierEntry.getRow(e.getRowIndex()).getCell("linkPhone").setValue(person.get(0).getPhone());
					}
				}else{
					this.kdtSupplierEntry.getRow(e.getRowIndex()).getCell("linkPerson").setValue(null);
					this.kdtSupplierEntry.getRow(e.getRowIndex()).getCell("linkPhone").setValue(null);
					this.kdtSupplierEntry.getRow(e.getRowIndex()).getCell("supplierState").setValue(null);
					this.kdtSupplierEntry.getRow(e.getRowIndex()).getCell("grade").setValue(null);
					this.kdtSupplierEntry.getRow(e.getRowIndex()).getCell("recommended").setValue(null);
				}
			}else if("result".equals(this.kdtSupplierEntry.getColumn(e.getColIndex()).getKey())){
				ResultEnum result=(ResultEnum)this.kdtSupplierEntry.getRow(e.getRowIndex()).getCell("result").getValue();
				
				if(result!=null){
					if(result.equals(ResultEnum.NO)){
						this.kdtSupplierEntry.getRow(e.getRowIndex()).getCell("isAccept").setValue(DefaultStatusEnum.NO);
						this.kdtSupplierEntry.getRow(e.getRowIndex()).getCell("isAccept").getStyleAttributes().setLocked(true);
						
						this.kdtSupplierEntry.getRow(e.getRowIndex()).getCell("reason").getStyleAttributes().setLocked(false);
					}else{
						this.kdtSupplierEntry.getRow(e.getRowIndex()).getCell("isAccept").getStyleAttributes().setLocked(false);
						
						this.kdtSupplierEntry.getRow(e.getRowIndex()).getCell("reason").setValue(null);
						this.kdtSupplierEntry.getRow(e.getRowIndex()).getCell("reason").getStyleAttributes().setLocked(true);
					}
				}
			}
		}
		
	public void actionInviteListEntryALine_actionPerformed(ActionEvent e)
			throws Exception {
		IRow row = this.kdtInviteListEntry.addRow();
		IPInviteListTypeEntryInfo info = new IPInviteListTypeEntryInfo();
		info.setId(BOSUuid.create(info.getBOSType()));
		
		row.setUserObject(info);
	}
	public void actionInviteListEntryILine_actionPerformed(ActionEvent e)
			throws Exception {
		IRow row = null;
		if (this.kdtInviteListEntry.getSelectManager().size() > 0) {
			int top = this.kdtInviteListEntry.getSelectManager().get().getTop();
			if (isTableColumnSelected(this.kdtInviteListEntry)){
				actionInviteListEntryALine_actionPerformed(e);
			}else{
				IPInviteListTypeEntryInfo info = new IPInviteListTypeEntryInfo();
				info.setId(BOSUuid.create(info.getBOSType()));
				
				row = this.kdtInviteListEntry.addRow(top);
				row.setUserObject(info);
			}
		} else {
			actionInviteListEntryALine_actionPerformed(e);
		}
	}
	public void actionInviteListEntryRLine_actionPerformed(ActionEvent e)
			throws Exception {
		if (this.kdtInviteListEntry.getSelectManager().size() == 0 || isTableColumnSelected(this.kdtInviteListEntry)) {
			FDCMsgBox.showInfo(this, EASResource.getString("com.kingdee.eas.framework.FrameWorkResource.Msg_NoneEntry"));
			return;
		}
		if (confirmRemove(this)) {
			int top = this.kdtInviteListEntry.getSelectManager().get().getBeginRow();
			int bottom = this.kdtInviteListEntry.getSelectManager().get().getEndRow();
			for (int i = top; i <= bottom; i++) {
				if (this.kdtInviteListEntry.getRow(top) == null) {
					FDCMsgBox.showInfo(this, EASResource.getString("com.kingdee.eas.framework.FrameWorkResource.Msg_NoneEntry"));
					return;
				}
				this.kdtInviteListEntry.removeRow(top);
			}
		}
	}
	private boolean isHasAttachment = false;
	public void fillAttachmentList(){
		this.cmbAttachment.removeAllItems();
		String boId = null;
		if(this.editData.getId() == null){
			return;
		}else{
			boId = this.editData.getId().toString();
		}
		
		if(boId != null){
			SelectorItemCollection sic = new SelectorItemCollection();
			sic.add(new SelectorItemInfo("id"));
			sic.add(new SelectorItemInfo("attachment.id"));
			sic.add(new SelectorItemInfo("attachment.name"));
			
			FilterInfo filter = new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("boID",boId));
			
			EntityViewInfo evi = new EntityViewInfo();
			evi.setFilter(filter);
			evi.setSelector(sic);
			BoAttchAssoCollection cols = null;
			 try {
				cols =BoAttchAssoFactory.getRemoteInstance().getBoAttchAssoCollection(evi);
			} catch (BOSException e) {
				logger.debug(e.getMessage(), e.getCause());
				e.printStackTrace();
			}
			if(cols != null && cols.size()>0){
				for(Iterator it = cols.iterator();it.hasNext();){
					AttachmentInfo attachment = ((BoAttchAssoInfo)it.next()).getAttachment();
					this.cmbAttachment.addItem(attachment);
				}
				this.isHasAttachment = true;
			}else{
				this.isHasAttachment =false;
			}
		}
		this.btnViewAttachment.setEnabled(this.isHasAttachment);
	}

	public void actionViewAttachment_actionPerformed(ActionEvent e)
    		throws Exception {
    	super.actionViewAttachment_actionPerformed(e);
    	String attachId = null;
    	if(this.cmbAttachment.getSelectedItem() != null && this.cmbAttachment.getSelectedItem() instanceof AttachmentInfo){
    		attachId = ((AttachmentInfo)this.cmbAttachment.getSelectedItem()).getId().toString();
    		AttachmentClientManager acm = AttachmentManagerFactory.getClientManager();
    		acm.viewAttachment(attachId);
    	}
    }
    
    public void actionAttachment_actionPerformed(ActionEvent e)
    		throws Exception {
    	super.actionAttachment_actionPerformed(e);
    	fillAttachmentList();
    }
	protected void lockContainer(Container container) {
   	 if(lblAttachmentContainer.getName().equals(container.getName())){
         	return;
         }
         else{
         	super.lockContainer(container);
         }
   }
}