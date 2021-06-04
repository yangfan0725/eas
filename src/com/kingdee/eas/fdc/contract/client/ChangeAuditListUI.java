/**
 * output package name
 */
package com.kingdee.eas.fdc.contract.client;

import java.awt.Dialog;
import java.awt.Frame;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.swing.Action;
import javax.swing.SwingUtilities;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent;
import com.kingdee.bos.ctrl.kdf.table.event.KDTSelectEvent;
import com.kingdee.bos.ctrl.swing.KDWorkButton;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemCollection;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.metadata.resource.BizEnumValueInfo;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.bos.workflow.AssignmentInfo;
import com.kingdee.bos.workflow.service.ormrpc.EnactmentServiceFactory;
import com.kingdee.bos.workflow.service.ormrpc.IEnactmentService;
import com.kingdee.eas.base.commonquery.client.CommonQueryDialog;
import com.kingdee.eas.base.commonquery.client.CustomerQueryPanel;
import com.kingdee.eas.base.permission.client.longtime.ILongTimeTask;
import com.kingdee.eas.base.uiframe.client.UIFactoryHelper;
import com.kingdee.eas.basedata.org.FullOrgUnitInfo;
import com.kingdee.eas.basedata.org.OrgStructureInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCConstants;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.basedata.IFDCBill;
import com.kingdee.eas.fdc.basedata.client.FDCClientHelper;
import com.kingdee.eas.fdc.basedata.client.FDCClientUtils;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.basedata.client.FDCSplitClientHelper;
import com.kingdee.eas.fdc.contract.ChangeAuditBillCollection;
import com.kingdee.eas.fdc.contract.ChangeAuditBillFactory;
import com.kingdee.eas.fdc.contract.ChangeAuditBillInfo;
import com.kingdee.eas.fdc.contract.ChangeAuditUtil;
import com.kingdee.eas.fdc.contract.ChangeBillStateEnum;
import com.kingdee.eas.fdc.contract.ChangeSupplierEntryCollection;
import com.kingdee.eas.fdc.contract.ChangeSupplierEntryFactory;
import com.kingdee.eas.fdc.contract.ChangeSupplierEntryInfo;
import com.kingdee.eas.fdc.contract.ContractBillFactory;
import com.kingdee.eas.fdc.contract.ContractBillInfo;
import com.kingdee.eas.fdc.contract.ContractChangeBillInfo;
import com.kingdee.eas.fdc.contract.CopySupplierEntryCollection;
import com.kingdee.eas.fdc.contract.CopySupplierEntryFactory;
import com.kingdee.eas.fdc.contract.FDCUtils;
import com.kingdee.eas.fdc.contract.IChangeAuditBill;
import com.kingdee.eas.fdc.contract.SupplierContentEntryCollection;
import com.kingdee.eas.fdc.contract.SupplierContentEntryFactory;
import com.kingdee.eas.fdc.contract.app.OaUtil;
import com.kingdee.eas.fdc.tenancy.OtherBillInfo;
import com.kingdee.eas.fdc.tenancy.client.OtherBillEditUI;
import com.kingdee.eas.fdc.tenancy.client.TenancyImport;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.eas.framework.CoreBillBaseInfo;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.ma.budget.client.LongTimeDialog;
import com.kingdee.eas.tools.datatask.DatataskMode;
import com.kingdee.eas.tools.datatask.DatataskParameter;
import com.kingdee.eas.tools.datatask.client.DatataskCaller;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.MsgBox;
import com.kingdee.jdbc.rowset.IRowSet;
import com.kingdee.util.StringUtils;

/**
 * �����������ʱ��
 */
public class ChangeAuditListUI extends AbstractChangeAuditListUI
{
    private static final Logger logger = CoreUIObject.getLogger(ChangeAuditListUI.class);

	private ChangeAuditListFilterUI filterUI = null;

	private CommonQueryDialog commonQueryDialog = null;
    /**
     * output class constructor
     */
    public ChangeAuditListUI() throws Exception
    {
        super();
        actionRemove.setBindWorkFlow(false);
        actionAheadDisPatch.setBindWorkFlow(false);
    }
    

    /**
     * ��д�˿��ٶ�λ�е��ֶ�����by renliang at 2010-5-12
     */
   protected String[] getLocateNames() {
		

	        String[] locateNames = new String[5];
	        locateNames[0] = "number";
	        locateNames[1] = "name";
	        locateNames[2] = "changeReason.name";
	        locateNames[3] = "auditType.name";
	        locateNames[4] = "specialtyType.name";
	        return locateNames;
	    
	}

	protected ICoreBase getRemoteInterface() throws BOSException {
		return ChangeAuditBillFactory.getRemoteInstance();
	}
	//������������ɵ�ָ��Ѿ�����ֹ�������ɾ��������   by Cassiel_peng
	public void actionRemove_actionPerformed(ActionEvent e) throws Exception {
		SelectorItemCollection itemCollection=new SelectorItemCollection();
		itemCollection.add("suppEntry");
		itemCollection.add("suppEntry.contractChange.contractBill.isCoseSplit");
		itemCollection.add("changeState");
		checkSelected();
		ArrayList id = getSelectedIdValues();
		for(int i = 0; i < id.size(); i++){
			FDCClientUtils.checkBillInWorkflow(this, id.get(i).toString());
			ChangeAuditBillInfo	info=ChangeAuditBillFactory.getRemoteInstance().getChangeAuditBillInfo(new ObjectUuidPK(getSelectedKeyValue()),itemCollection);
			if(info!=null){
				if (!info.getChangeState().equals(ChangeBillStateEnum.Saved) && !info.getChangeState().equals(ChangeBillStateEnum.Submit)) {
					MsgBox.showWarning(this, "����ǰѡ��ĵ��ݵ�״̬���ʺ�ɾ��������");
					abort();
				}
    			boolean isCostSplit =false;
    			for(int j=0;j<info.getSuppEntry().size();j++){
    				ContractChangeBillInfo entryInfo = info.getSuppEntry().get(j).getContractChange();
    				if(entryInfo!=null&&entryInfo.getContractBill()!=null&&entryInfo.getContractBill().isIsCoseSplit()){
    					isCostSplit = FDCSplitClientHelper.isBillSplited(entryInfo.getId().toString(), "t_con_conchangesplit", "FContractChangeID");
    					if(isCostSplit){
    						break;//ֻҪ��һ��ָ�����ֹ�����Ҫ��ʾ
    					}
    				}
    				if(entryInfo!=null&&entryInfo.getContractBill()!=null&&!entryInfo.getContractBill().isIsCoseSplit()){//�ǳɱ�����
    					isCostSplit=FDCSplitClientHelper.isBillSplited(entryInfo.getId().toString(), "T_CON_ConChangeNoCostSplit", "FContractChangeID");
    					if(isCostSplit){
    						break;//ֻҪ��һ��ָ�����ֹ�����Ҫ��ʾ
    					}
    				}
    			}//�����ǳɱ����ֻ��Ƿǳɱ����֣�ֻҪ��ָ�����ֹ��͵ø��ͻ���ʾ
    			if(isCostSplit){
    				MsgBox.showWarning("�˱�����������ɵ�ָ��Ѿ���֣�����ɾ����");
    				SysUtil.abort();
    			}
    		}
		}
		super.actionRemove_actionPerformed(e);
	}
	
	protected void audit(List ids) throws Exception {
		IChangeAuditBill bill = (IChangeAuditBill) getBizInterface();		
    	if(ids!=null){
    		bill.audit(ids);
    	}
	}

	protected void unAudit(List ids) throws Exception {
		
//		for (int i = 0; i < ids.size(); i++) {
//			String settId=(String) ids.get(i);
//			FilterInfo filterSett = new FilterInfo();
//			filterSett.getFilterItems().add(
//					new FilterItemInfo("contractChange.id", settId));
//			filterSett.getFilterItems().add(
//					new FilterItemInfo("state", FDCBillStateEnum.INVALID_VALUE,
//							CompareType.NOTEQUALS));
//			boolean hasSettleSplit = false;
//			if (ConChangeSplitFactory.getRemoteInstance().exists(filterSett)
//					|| ConChangeNoCostSplitFactory.getRemoteInstance()
//					.exists(filterSett)) {
//				hasSettleSplit = true;
//			}
//			if (hasSettleSplit) {
//				MsgBox.showWarning("������Ѿ����,���ܷ�����!");
//				SysUtil.abort();
//				return;
//			}
//		}
		
		IChangeAuditBill bill = (IChangeAuditBill) getBizInterface();		
    	if(ids!=null){
    		bill.unAudit(ids);
    	}
	}
	
	/**
	 * output getEditUIName method
	 */
	protected String getEditUIName() {
		return com.kingdee.eas.fdc.contract.client.ChangeAuditEditUI.class
				.getName();
	}

	/**
	 * output getBizInterface method
	 */
	protected com.kingdee.eas.framework.ICoreBase getBizInterface()
			throws Exception {
		return com.kingdee.eas.fdc.contract.ChangeAuditBillFactory
				.getRemoteInstance();
	}

	/**
	 * output createNewData method
	 */
	protected com.kingdee.bos.dao.IObjectValue createNewData() {
		com.kingdee.eas.fdc.contract.ChangeAuditBillInfo objectValue = new com.kingdee.eas.fdc.contract.ChangeAuditBillInfo();
		return objectValue;
	}
	/**
	 * �������������
	 */
	protected void freezeTableColumn() {
		/*FDCHelper.formatTableNumber(getMainTable(), "amount");	
		// ��ͬ����
		int name_col_index = getMainTable().getColumn("billName")
				.getColumnIndex();
		getMainTable().getViewManager().setFreezeView(-1, name_col_index+1);*/
	}

	public void onShow() throws Exception{
		super.onShow();
		actionAudit.setEnabled(true);
		actionUnAudit.setEnabled(true);
//		actionWorkFlowG.setVisible(false);
//		menuWorkFlow.setVisible(false);
		actionAheadDisPatch.putValue(Action.SMALL_ICON, EASResource.getIcon("imgTbtn_emend"));
		actionAheadDisPatch.setEnabled(true);
		actionDisPatch.setVisible(false);
		actionQuery.setVisible(true);
	}

	protected void checkBillState(String state, String res) throws Exception {
		List idList = ContractClientUtils.getSelectedIdValues(getMainTable(),
				getKeyFieldName());

		Set idSet = ContractClientUtils.listToSet(idList);

		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(
				new FilterItemInfo("id", idSet, CompareType.INCLUDE));
		view.setFilter(filter);
		view.getSelector().add("id");
		view.getSelector().add("changeState");
		CoreBaseCollection coll = getRemoteInterface().getCollection(view);

		for (Iterator iter = coll.iterator(); iter.hasNext();) {
			CoreBaseInfo element = (CoreBaseInfo) iter.next();

			// ��鵥���Ƿ��ڹ�������
			FDCClientUtils
					.checkBillInWorkflow(this, element.getId().toString());

			if (!element.getString(getBillStatePropertyName()).equals(state)) {
				MsgBox.showWarning(this, ContractClientUtils.getRes(res));
				abort();
			}

		}
	}
	
	protected String getBillStatePropertyName() {
		return "changeState";
	}
	
	protected String getStateForAudit() {
		return ChangeBillStateEnum.SUBMIT_VALUE;
	}
	
	protected String getStateForUnAudit() {
		return ChangeBillStateEnum.AUDIT_VALUE;
	}

	protected String[] getBillStateForEditOrRemove() {
		return new String[] { ChangeBillStateEnum.SAVED_VALUE,
				ChangeBillStateEnum.SUBMIT_VALUE,ChangeBillStateEnum.REGISTER_VALUE };
	}

	public void actionDisPatch_actionPerformed(ActionEvent e) throws Exception {
		checkSelected();
		super.actionDisPatch_actionPerformed(e);
		List selectedIdValues = ContractClientUtils.getSelectedIdValues(
				getMainTable(), getKeyFieldName());
		IChangeAuditBill bill = (IChangeAuditBill) getBizInterface();		
    	if(selectedIdValues!=null){
    		bill.disPatch(FDCHelper.list2Set(selectedIdValues));
    		showOprtOKMsgAndRefresh();
    	}
	}
	
	/**
	 * �������������Ӧ�ĺ�ͬ����Ƿ��Ѿ����������Ҹ��ݲ����Ƿ����ã�������Ϣ��ʾ��
	 * @param idList
	 * @throws BOSException
	 * @throws EASBizException
	 * @author owen_wen 2010-12-10
	 */
	private void checkContractSplitIsAuditAndShowMsg(List idList) throws BOSException, EASBizException{
		for (int i = 0; i < idList.size(); i++){
			EntityViewInfo view = new EntityViewInfo();
			FilterInfo filter  = new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("parent.id", idList.get(i)));
			view.setFilter(filter);
			view.getSelector().add(new SelectorItemInfo("contractBill.id"));
			view.getSelector().add(new SelectorItemInfo("contractBill.isCoseSplit"));
			
			ChangeSupplierEntryCollection cse = ChangeSupplierEntryFactory.getRemoteInstance().getChangeSupplierEntryCollection(view);
			
			for (int j = 0; j < cse.size(); j++){
				boolean conSplitIsAudited = FDCSplitClientHelper.checkContractSplitIsAudited(cse.get(j).getContractBill().getId().toString(), 
						cse.get(j).getContractBill().isIsCoseSplit());
				if (conSplitIsAudited) // ��ͬ���δ����
					FDCSplitClientHelper.getParamValueAndShowMsg("conSplitNotAudit", this);
			}
		}
	}
	
//	����ʱ�������ݻ���
	public void actionAudit_actionPerformed(ActionEvent e) throws Exception {
		// ��ȡ�û�ѡ��Ŀ�
		List idList = ContractClientUtils.getSelectedIdValues(
				getBillListTable(), getKeyFieldName());
		
		// �����˲��� ����ͬ�������ͬ����Ĳ���Ƿ��Զ����ú�ͬ��ֵĳɱ���Ŀ����ֱ������� �ż����ж�
		if (FDCUtils.getBooleanValue4FDCParamByKey(null, SysContext.getSysContext().getCurrentOrgUnit().getId().toString(), FDCConstants.FDC_PARAM_IMPORTCONSPLIT))
			this.checkContractSplitIsAuditAndShowMsg(idList);
		
		boolean hasMutex = false;
		try {

			FDCClientUtils.requestDataObjectLock(this, idList, "Audit");

			super.actionAudit_actionPerformed(e);

		} catch (Throwable e1) {
			this.handUIException(e1);
			hasMutex = FDCClientUtils.hasMutexed(e1);
		} finally {
			if (!hasMutex) {
				try {
					FDCClientUtils.releaseDataObjectLock(this, idList);
				} catch (Throwable e1) {
					this.handUIException(e1);
				}
			}
		}
	}
	
    public void actionQuery_actionPerformed(ActionEvent e) throws Exception
    {
		getFilterUI();
       super.actionQuery_actionPerformed(e);
    }
	
	protected void checkBeforeRemove() throws Exception {
		super.checkBeforeRemove();
	}
	
	protected boolean confirmRemove(){
		 List idList = ContractClientUtils.getSelectedIdValues(getMainTable(),
				 getKeyFieldName());
		 Set idSet = ContractClientUtils.listToSet(idList);
		 
		 EntityViewInfo view = new EntityViewInfo();
		 FilterInfo filter = new FilterInfo();
		 filter.getFilterItems().add(
				 new FilterItemInfo("id", idSet, CompareType.INCLUDE));
		 view.setFilter(filter);
		 view.getSelector().add("id");
		 view.getSelector().add(getBillStatePropertyName());
		 CoreBaseCollection coll;
		 try {
			 coll = getRemoteInterface().getCollection(view);
			 
			 for (Iterator iter = coll.iterator(); iter.hasNext();) {
				 CoreBillBaseInfo element = (CoreBillBaseInfo) iter.next();
				 String billState = element.getString(getBillStatePropertyName());
				 if(billState.equals(ChangeBillStateEnum.SUBMIT_VALUE)){
					 int isYes = MsgBox.showConfirm2(this, ChangeAuditUtil.getRes("hasChange"));
					 if (MsgBox.isYes(isYes)) {	
						 return true;
					 }else{
						 return false;
					 }
				 }else{
					 return super.confirmRemove();
				 }
			 }
		 } catch (BOSException e) {
			 super.handUIException(e);
		 }
		 return super.confirmRemove();
	 }

	public void actionAheadDisPatch_actionPerformed(ActionEvent e) throws Exception {
		checkSelected();
		checkBeforAhead();
		List selectedIdValues = ContractClientUtils.getSelectedIdValues(
				getMainTable(), getKeyFieldName());
		IChangeAuditBill bill = (IChangeAuditBill) getBizInterface();
		Set idSet = ContractClientUtils.listToSet(selectedIdValues);
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(
				new FilterItemInfo("id", idSet, CompareType.INCLUDE));
		view.setFilter(filter);
		view.getSelector().add("id");
		view.getSelector().add("changeState");
		CoreBaseCollection coll = getRemoteInterface().getCollection(view);

		for (Iterator iter = coll.iterator(); iter.hasNext();) {
			CoreBaseInfo element = (CoreBaseInfo) iter.next();

			UIContext uiContext = new UIContext(this); 
			uiContext.put(UIContext.ID, element.getId());
			IUIWindow uiWin = UIFactory.createUIFactory(UIFactoryName.MODEL).	create(
					ChangeAuditAheadEditUI.class.getName(),	uiContext, null , null);       
			uiWin.show();
		}
		refreshList();
		super.actionAheadDisPatch_actionPerformed(e);
	}
	
	private void checkBeforAhead() throws Exception{
		List selectedIdValues = ContractClientUtils.getSelectedIdValues(
				getMainTable(), getKeyFieldName());
		IChangeAuditBill bill = (IChangeAuditBill) getBizInterface();
		Set idSet = ContractClientUtils.listToSet(selectedIdValues);
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(
				new FilterItemInfo("id", idSet, CompareType.INCLUDE));
		view.setFilter(filter);
		view.getSelector().add("id");
		view.getSelector().add("changeState");
		view.getSelector().add("entrys.*");
		view.getSelector().add("suppEntry.mainSupp.*");
		view.getSelector().add("suppEntry.contractBill.*");
		view.getSelector().add("suppEntry.*");
		view.getSelector().add("suppEntry.entry.*");
		view.getSelector().add("suppEntry.copySupp.*");
		view.getSelector().add("suppEntry.reckonor.id");
		ChangeAuditBillCollection coll = bill.getChangeAuditBillCollection(view);
		for (Iterator iter = coll.iterator(); iter.hasNext();) {
			ChangeAuditBillInfo element = (ChangeAuditBillInfo) iter.next();
			if(element.getChangeState().equals(ChangeBillStateEnum.Saved)
					||element.getChangeState().equals(ChangeBillStateEnum.Register)
					||element.getChangeState().equals(ChangeBillStateEnum.Submit)
					||element.getChangeState().equals(ChangeBillStateEnum.Auditting)){
				if(element.getEntrys().size()==0||element.getSuppEntry().size()==0){
					MsgBox.showWarning(this, ChangeAuditUtil.getRes("notAhead"));
					SysUtil.abort();
				}else{
					ChangeSupplierEntryCollection collSupp = element.getSuppEntry();
					int count = collSupp.size();
					for(int i=0;i<count;i++){
						ChangeSupplierEntryInfo info = collSupp.get(i);
						if(info.getMainSupp()==null){
							MsgBox.showWarning(this, ChangeAuditUtil.getRes("partAhead"));
							SysUtil.abort();
						}
						if(info.getContractBill()==null){
							MsgBox.showWarning(this, ChangeAuditUtil.getRes("partAhead"));
							SysUtil.abort();
						}
						if(info.getCostAmount()==null||info.getCostAmount().equals(FDCHelper.ZERO)){
							MsgBox.showWarning(this, ChangeAuditUtil.getRes("costAhead"));
							SysUtil.abort();
						}
						if(info.getReckonor()==null){
							MsgBox.showWarning(this, ChangeAuditUtil.getRes("costAhead"));
							SysUtil.abort();
						}
						if(info.getId()!=null){
							EntityViewInfo vi = new EntityViewInfo();
							FilterInfo fi = new FilterInfo();
							FilterItemCollection it = fi.getFilterItems();	
							if(info.getId()!=null)
								it.add(new FilterItemInfo("parent.id", info.getId().toString(),CompareType.EQUALS));
							vi.setFilter(fi);
							vi.getSelector().add("content.*");
							SupplierContentEntryCollection c = SupplierContentEntryFactory.getRemoteInstance().getSupplierContentEntryCollection(vi);
					        int entryNum = c.size();
					        if(entryNum==0){
								MsgBox.showWarning(this, ChangeAuditUtil.getRes("partAhead"));
								SysUtil.abort();
					        }
					        
					        EntityViewInfo vie = new EntityViewInfo();
							FilterInfo fil = new FilterInfo();
							FilterItemCollection itl = fil.getFilterItems();
							if(info.getId()!=null)
								itl.add(new FilterItemInfo("parent.id", info.getId().toString(),CompareType.EQUALS));
							vie.setFilter(fil);
							vie.getSelector().add("copySupp.*");
							CopySupplierEntryCollection collCopy = CopySupplierEntryFactory.getRemoteInstance().getCopySupplierEntryCollection(vie);							
					        int copyNum = collCopy.size();
					        if(copyNum==0){
								MsgBox.showWarning(this, ChangeAuditUtil.getRes("partAhead"));
								SysUtil.abort();
					        }
						}
					}
				}
			}
			else{
				MsgBox.showWarning(this, ChangeAuditUtil.getRes("notAhead"));
				SysUtil.abort();
			}
		}
	}

	public void onLoad() throws Exception {		
		super.onLoad();
		btnPassHistory.setIcon(EASResource.getIcon("imgTbtn_allotpopedom"));
		actionQuery.setEnabled(true);
		actionQuery.setVisible(true);
		boolean isDispatch = FDCUtils.getDefaultFDCParamByKey(null,null,FDCConstants.FDC_PARAM_ALLOWDISPATCH);
		if(!isDispatch){//����ģʽ������Ҫ�·�
			actionAheadDisPatch.setVisible(false);
			actionAheadDisPatch.setEnabled(false);
		}
		this.actionImportData.setVisible(true);
		this.actionExportData.setVisible(true);
		this.menuItemExportData.setText("�·���λ����");
		this.menuItemExportData.setIcon(EASResource.getIcon("imgTbtn_input"));
		
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
		
		this.tblMain.getColumn("jobType.name").getStyleAttributes().setHided(true);
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
        			   ChangeAuditEditUI ui=(ChangeAuditEditUI) UIFactoryHelper.initUIObject(ChangeAuditEditUI.class.getName(), uiContext, null,OprtState.EDIT);
        			   ChangeBillStateEnum state = ((ChangeAuditBillInfo)ui.getEditData()).getChangeState();
        			   FDCClientUtils.checkBillInWorkflow(ui, ui.getEditData().getId().toString());
        				
        			   if(state==null||!(ChangeBillStateEnum.Saved.equals(state)||ChangeBillStateEnum.Submit.equals(state))){
        					MsgBox.showWarning("���ݲ��Ǳ�������ύ״̬�����ܽ����ύ������");
        					SysUtil.abort();
        			   }
        			   ui.loadFields();
        			   
        			   BigDecimal amount = FDCHelper.ZERO;
        			   int count = ui.kdtSuppEntry.getRowCount();
        			   for(int j=0;j<count;j++){
        				   if(j%ui.suppRows==8){
        					   Object obj  = ui.kdtSuppEntry.getCell(j-1, "content").getValue();
        					   Object contentCA = ui.kdtSuppEntry.getCell(j, "content").getValue();
        					   BigDecimal newCostAmt = FDCHelper.toBigDecimal(obj).multiply(FDCHelper.toBigDecimal(contentCA));
        					   ui.kdtSuppEntry.getCell(j+1, "content").setValue(newCostAmt);
        					   
        					   amount = amount.add((FDCHelper.toBigDecimal(newCostAmt)));
        				   }
        			   }
        			   ui.editData.setTotalCost(amount);
        			   ui.storeFields();
        			   ui.verifyInputForSave();
        			   ui.verfySuppEntrys();
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
	protected void updateButtonStatus() {}	
	
	protected void tblMain_tableSelectChanged(KDTSelectEvent e)
			throws Exception {
		super.tblMain_tableSelectChanged(e);
		actionRemove.setEnabled(true);
		IRow row;
		if (this.tblMain.getSelectManager().getActiveRowIndex()!= -1) {
			if((tblMain.getSelectManager().getBlocks().size() > 1)
				||(e.getSelectBlock().getBottom() - e.getSelectBlock().getTop() > 0)){
				actionSetRespite.setEnabled(true);
				actionCancelRespite.setEnabled(true);
			}else{
				row = this.tblMain.getRow(this.tblMain.getSelectManager().getActiveRowIndex());
				if(Boolean.TRUE.equals(row.getCell("isRespite").getValue())){
					actionSetRespite.setEnabled(false);
					actionCancelRespite.setEnabled(true);
				}else{
					actionSetRespite.setEnabled(true);
					actionCancelRespite.setEnabled(false);
				}
				if(row.getCell("changeState")!=null && 
						ChangeBillStateEnum.AUDIT_VALUE.equalsIgnoreCase(
								((BizEnumValueInfo)row.getCell("changeState").getValue()).getValue().toString())
					&& tblMain.getSelectManager().getBlocks().size() == 1){
					actionSetRespite.setEnabled(false);
					actionCancelRespite.setEnabled(true);
				}
			}
		}
	}
	
	public void actionAddNew_actionPerformed(ActionEvent e) throws Exception {
		FDCClientUtils.checkSelectProj(this, getProjSelectedTreeNode());
		// FDCClientUtils.checkProjWithCostOrg(this, getProjSelectedTreeNode());
		super.actionAddNew_actionPerformed(e);
	}
	
	public void actionUnAudit_actionPerformed(ActionEvent e) throws Exception {
		checkSelected();
		FDCClientHelper.checkAuditor(getSelectedIdValues(), "T_CON_ChangeAuditBill");
		super.actionUnAudit_actionPerformed(e);
	}


	public void actionSetRespite_actionPerformed(ActionEvent e)
			throws Exception {
		checkSelected();
		List idList = ContractClientUtils.getSelectedUnAuditedId(getBillListTable(), getKeyFieldName(),false);
		if(idList.size()!=0&&idList.get(0) != null){
			((IFDCBill)getRemoteInterface()).setRespite(idList, true);
			MsgBox.showWarning("�����ɹ���������״̬�ĵ��ݲ��������ݻ�");
			refreshList();
		}
	}
	
	public void actionCancelRespite_actionPerformed(ActionEvent e)
			throws Exception {
		checkSelected();
		List idList = ContractClientUtils.getSelectedUnAuditedId(getBillListTable(), getKeyFieldName(),true);
		if(idList.get(0) != null){
			((IFDCBill)getRemoteInterface()).setRespite(idList, false);
			showOprtOKMsgAndRefresh();
		}
	}
	protected void execQuery() {
		super.execQuery();
//		this.getMainQuery().setFilter(new FilterInfo());
	}
	protected CommonQueryDialog initCommonQueryDialog() {
		if (commonQueryDialog == null) {
			commonQueryDialog = super.initCommonQueryDialog();
			commonQueryDialog.setWidth(400);
		}
		commonQueryDialog.addUserPanel(this.getFilterUI());
		return commonQueryDialog;
	}

	private CustomerQueryPanel getFilterUI() {
		if (this.filterUI == null) {
			try {
				this.filterUI = new ChangeAuditListFilterUI(this, this.actionOnLoad);
			} catch (Exception e) {
				e.printStackTrace();
				abort(e);
			}
		}
		filterUI.setAuthorizedOrgs(this.authorizedOrgs);
		filterUI.setCompany(null);
		filterUI.setProject(null);
		DefaultKingdeeTreeNode projectNode = this.getProjSelectedTreeNode();
		if (projectNode != null && projectNode.getUserObject() != null && projectNode.getUserObject() instanceof CoreBaseInfo) {
			CoreBaseInfo projTreeNodeInfo = (CoreBaseInfo) projectNode.getUserObject();
			if (projTreeNodeInfo instanceof OrgStructureInfo || projTreeNodeInfo instanceof FullOrgUnitInfo) {
				FullOrgUnitInfo company = null;
				if (projTreeNodeInfo instanceof OrgStructureInfo) {
					company = ((OrgStructureInfo) projTreeNodeInfo).getUnit();
				} else {
					company = (FullOrgUnitInfo) projTreeNodeInfo;
				}
				filterUI.setCompany(company);
			} else if (projTreeNodeInfo instanceof CurProjectInfo) {
				filterUI.setProject((CurProjectInfo) projTreeNodeInfo);
			}
		}
		return this.filterUI;
	}
	
	public void actionPassHistory_actionPerformed(ActionEvent e)
			throws Exception {
        checkSelected();
        int rowIndex = tblMain.getSelectManager().getActiveRowIndex();
        String sourceId = tblMain.getRow(rowIndex).getCell("id").getValue().toString();
//        IEnactmentService wfService = EnactmentServiceFactory.createRemoteEnactService();
//        AssignmentInfo info = wfService.getAssignmentById(sourceId);
//        if(StringUtils.isEmpty(info.getBizObjectIdArray()[0]))
//        {
//            MsgBox.showWarning(this, EASResource.getString("com.kingdee.eas.base.message.client.MessageCenter", "noBillOrDeleted"));
//            return;
//        }
        UIContext uiContext = new UIContext(this);
        uiContext.put("ID", sourceId);
        IUIWindow uiWindow = null;
        uiWindow = UIFactory.createUIFactory((com.kingdee.eas.base.uiframe.client.UIModelDialogFactory.class).getName()).create((com.kingdee.eas.base.multiapprove.client.PassHistoryUI.class).getName(), uiContext);
        uiWindow.show();
    }
	public void actionEdit_actionPerformed(ActionEvent e) throws Exception{
		this.checkSelected();
    	
		int rowIndex = this.tblMain.getSelectManager().getActiveRowIndex();
		IRow row = this.tblMain.getRow(rowIndex);
		String id = (String) row.getCell(this.getKeyFieldName()).getValue();
		ChangeAuditBillInfo info = ChangeAuditBillFactory.getRemoteInstance().getChangeAuditBillInfo(new ObjectUuidPK(BOSUuid.read(id)));

		if (!info.getChangeState().equals(ChangeBillStateEnum.Saved) && !info.getChangeState().equals(ChangeBillStateEnum.Submit)) {
			MsgBox.showWarning(this, "����ǰѡ��ĵ��ݵ�״̬���ʺ��޸Ĳ�����");
			abort();
		}
        super.actionEdit_actionPerformed(e);
    }
	public void actionImportData_actionPerformed(ActionEvent e) throws Exception {
		DatataskCaller task = new DatataskCaller();
        task.setParentComponent(this);
        ArrayList list = this.getImportParam();
        
        if (list != null)
        {
            task.invoke(list, DatataskMode.ImpMode,true,true);
        }
        actionRefresh_actionPerformed(e);
	}
	protected ArrayList getImportParam(){	
		Hashtable hs = new Hashtable();
		
		DatataskParameter param = new DatataskParameter();
		param.setContextParam(hs);//
        param.solutionName = getSolutionName();      
        param.alias = getDatataskAlias();
        ArrayList paramList = new ArrayList();
        paramList.add(param);
        return paramList;
    }
	
	protected String getSolutionName(){
    	return "eas.fdc.contract.ChangeAudit";
    }
    protected String getDatataskAlias(){
    	return "���������";
    }
    public void actionExportData_actionPerformed(ActionEvent e) throws Exception{
    	String strSolutionName ="eas.fdc.contract.ChangeAuditBillContract";
		DatataskCaller task = new DatataskCaller();
		task.setParentComponent(this);
		DatataskParameter param = new DatataskParameter();
		String solutionName = strSolutionName;
		param.solutionName = solutionName;
		ArrayList paramList = new ArrayList();
		paramList.add(param);
		task.invoke(paramList, DatataskMode.ImpMode, true,true);
        actionRefresh_actionPerformed(e);
    }
    public void actionWorkFlowG_actionPerformed(ActionEvent e) throws Exception {
		checkSelected();
		int rowIndex = this.tblMain.getSelectManager().getActiveRowIndex();
		IRow row = this.tblMain.getRow(rowIndex);
    	String id = (String) row.getCell("id").getValue();
    	ChangeAuditBillInfo info=ChangeAuditBillFactory.getRemoteInstance().getChangeAuditBillInfo(new ObjectUuidPK(id));
    	if(info.getSourceFunction()!=null){
    		FDCSQLBuilder builder=new FDCSQLBuilder();
			builder.appendSql("select fviewurl from t_oa");
			IRowSet rs=builder.executeQuery();
			String url=null;
			while(rs.next()){
				url=rs.getString("fviewurl");
			}
			if(url!=null){
				String mtLoginNum = OaUtil.encrypt(SysContext.getSysContext().getCurrentUserInfo().getNumber());
				String s2 = "&MtFdLoinName=";
				StringBuffer stringBuffer = new StringBuffer();
	            String oaid = URLEncoder.encode(info.getSourceFunction());
	            String link = String.valueOf(stringBuffer.append(url).append(oaid).append(s2).append(mtLoginNum));
				Runtime.getRuntime().exec("rundll32 url.dll,FileProtocolHandler "+link);  
			}
    	}else{
    		super.actionWorkFlowG_actionPerformed(e);
    	}
	}
    public void actionAuditResult_actionPerformed(ActionEvent e)
	throws Exception {
    	checkSelected();
		int rowIndex = this.tblMain.getSelectManager().getActiveRowIndex();
		IRow row = this.tblMain.getRow(rowIndex);
    	String id = (String) row.getCell("id").getValue();
    	ChangeAuditBillInfo info=ChangeAuditBillFactory.getRemoteInstance().getChangeAuditBillInfo(new ObjectUuidPK(id));
    	if(info.getSourceFunction()!=null){
    		FDCSQLBuilder builder=new FDCSQLBuilder();
			builder.appendSql("select fviewurl from t_oa");
			IRowSet rs=builder.executeQuery();
			String url=null;
			while(rs.next()){
				url=rs.getString("fviewurl");
			}
			if(url!=null){
				String mtLoginNum = OaUtil.encrypt(SysContext.getSysContext().getCurrentUserInfo().getNumber());
				String s2 = "&MtFdLoinName=";
				StringBuffer stringBuffer = new StringBuffer();
	            String oaid = URLEncoder.encode(info.getSourceFunction());
	            String link = String.valueOf(stringBuffer.append(url).append(oaid).append(s2).append(mtLoginNum));
				Runtime.getRuntime().exec("rundll32 url.dll,FileProtocolHandler "+link);  
			}
    	}else{
    		super.actionAuditResult_actionPerformed(e);
    	}
    }
}