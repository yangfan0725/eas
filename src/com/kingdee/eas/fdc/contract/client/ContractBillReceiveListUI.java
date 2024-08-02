/**
 * output package name
 */
package com.kingdee.eas.fdc.contract.client;

import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.sql.RowSet;
import javax.swing.Action;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultTreeModel;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.metadata.IMetaDataPK;
import com.kingdee.bos.metadata.MetaDataPK;
import com.kingdee.bos.metadata.data.SortType;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemCollection;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SorterItemCollection;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.metadata.resource.BizEnumValueInfo;
import com.kingdee.bos.ui.face.BOSUIContext;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.IUIFactory;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.ItemAction;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.bos.ctrl.kdf.table.ICell;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectManager;
import com.kingdee.bos.ctrl.kdf.table.KDTStyleConstants;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.event.KDTDataFillListener;
import com.kingdee.bos.ctrl.kdf.table.event.KDTDataRequestEvent;
import com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent;
import com.kingdee.bos.ctrl.swing.KDFileChooser;
import com.kingdee.bos.ctrl.swing.KDTree;
import com.kingdee.bos.ctrl.swing.KDTreeView;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectStringPK;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.dao.query.IQueryExecutor;
import com.kingdee.bos.framework.cache.ActionCache;
import com.kingdee.eas.base.attachment.client.AttachmentUIContextInfo;
import com.kingdee.eas.base.attachment.common.AttachmentClientManager;
import com.kingdee.eas.base.attachment.common.AttachmentManagerFactory;
import com.kingdee.eas.base.attachment.util.FileGetter;
import com.kingdee.eas.base.attachment.util.Resrcs;
import com.kingdee.eas.base.attachment.util.StringUtil4File;
import com.kingdee.eas.base.multiapprove.MultiApproveInfo;
import com.kingdee.eas.base.multiapprove.client.MultiApproveUtil;
import com.kingdee.eas.base.param.ParamControlFactory;
import com.kingdee.eas.base.permission.PermissionFactory;
import com.kingdee.eas.basedata.org.FullOrgUnitFactory;
import com.kingdee.eas.basedata.org.FullOrgUnitInfo;
import com.kingdee.eas.basedata.org.OrgStructureInfo;
import com.kingdee.eas.basedata.org.OrgType;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basedata.ContractTypeFactory;
import com.kingdee.eas.fdc.basedata.ContractTypeInfo;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.fdc.basedata.FDCBillInfo;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCConstants;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.basedata.client.FDCClientHelper;
import com.kingdee.eas.fdc.basedata.client.FDCClientUtils;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.basedata.client.ProjectTreeBuilder;
import com.kingdee.eas.fdc.contract.ContractBillCollection;
import com.kingdee.eas.fdc.contract.ContractBillFactory;
import com.kingdee.eas.fdc.contract.ContractBillReceiveFactory;
import com.kingdee.eas.fdc.contract.ContractBillReceiveInfo;
import com.kingdee.eas.fdc.contract.ContractContentCollection;
import com.kingdee.eas.fdc.contract.ContractContentFactory;
import com.kingdee.eas.fdc.contract.ContractContentInfo;
import com.kingdee.eas.fdc.contract.ContractPropertyEnum;
import com.kingdee.eas.fdc.contract.ContractUtil;
import com.kingdee.eas.fdc.contract.FDCUtils;
import com.kingdee.eas.fdc.contract.ForWriteMarkHelper;
import com.kingdee.eas.fdc.contract.IContractBill;
import com.kingdee.eas.fdc.contract.IContractBillReceive;
import com.kingdee.eas.fdc.contract.app.OaUtil;
import com.kingdee.eas.fdc.contract.programming.ProgrammingContractInfo;
import com.kingdee.eas.fdc.contract.programming.client.ContractBillLinkProgContEditUI;
import com.kingdee.eas.fdc.sellhouse.SHEAttachBillEntryInfo;
import com.kingdee.eas.fdc.sellhouse.client.SHEAttachBillEditUI;
import com.kingdee.eas.framework.*;
import com.kingdee.eas.framework.batchHandler.RequestContext;
import com.kingdee.eas.framework.client.tree.DefaultLNTreeNodeCtrl;
import com.kingdee.eas.framework.client.tree.ILNTreeNodeCtrl;
import com.kingdee.eas.framework.client.tree.ITreeBuilder;
import com.kingdee.eas.framework.client.tree.KDTreeNode;
import com.kingdee.eas.framework.client.tree.TreeBuilderFactory;
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
public class ContractBillReceiveListUI extends AbstractContractBillReceiveListUI
{
    private static final Logger logger = CoreUIObject.getLogger(ContractBillReceiveListUI.class);
    
    /**
     * output class constructor
     */
    public ContractBillReceiveListUI() throws Exception
    {
        super();
    }


	private int viewType = 0;//��ͬ�༭��Ϣ
	private Map contentMap = new HashMap();
	private Map attachMap = new HashMap();
	// �ٴι鵵ʱ���º�ͬ���
	private boolean isUpdateConNo = false;
	// ��ͬ������������ϴ�����
    private boolean canUploadForAudited = false;
	/**
	 * ��������ˣ��������ʱ��
	 */
	Map auditMap = new HashMap();
	//���ز�ҵ��ϵͳ���Ĺ����Ƿ����������ʼ����۹���
	boolean isUseWriteMark=FDCUtils.getDefaultFDCParamByKey(null,null, FDCConstants.FDC_PARAM_WRITEMARK);
	//�����ͬ����ѡ���ύ״̬������ͬ
	private boolean isSupply = false;
	public void onLoad() throws Exception {
//		if(isUseWriteMark){
//			this.btnAddContent.setEnabled(false);
//			this.menuItemAddContent.setEnabled(false);
//		}else{
//			this.btnAddContent.setEnabled(true);
//			this.menuItemAddContent.setEnabled(true);
//		}
		 this.btnAudit.setIcon(EASResource.getIcon("imgTbtn_audit"));
        this.btnUnAudit.setIcon(EASResource.getIcon("imgTbtn_unaudit"));
		this.tblMain.getDataRequestManager().addDataFillListener(new KDTDataFillListener(){

			public void afterDataFill(KDTDataRequestEvent e) {
				boolean isMainOrder = false;
				SorterItemCollection sortItems = mainQuery.getSorter();
				if(sortItems!=null && sortItems.size()>0){
		        	for(Iterator it=sortItems.iterator();it.hasNext();){
		        		SorterItemInfo sortItem = (SorterItemInfo)it.next();
		        		if(sortItem.getPropertyName().equals("mainContractNumber")){
		        			isMainOrder = true;
		        			break;
		        		}
		        	}
		        }
				if(attachMap==null){
					attachMap=new HashMap();
				}
				if(auditMap==null){
					auditMap=new HashMap();
				}
				if(contentMap==null){
					contentMap=new HashMap();
				}
				String preNumber = null;
				for (int i = e.getFirstRow(); i <= e.getLastRow(); i++) {
					IRow row = tblMain.getRow(i);
					if(row.getCell("amount").getValue()==null){
						row.getCell("amount").setValue(FDCHelper.ZERO);
					}
					if(row.getCell("originalAmount").getValue()==null){
						row.getCell("originalAmount").setValue(FDCHelper.ZERO);
					}
					String idkey = row.getCell("id").getValue().toString();
					if(contentMap.containsKey(idkey)){
						row.getCell("content").setValue(Boolean.TRUE);
					}
					else{
						row.getCell("content").setValue(Boolean.FALSE);
					}
					if(attachMap.containsKey(idkey)){
						row.getCell("attachment").setValue(Boolean.TRUE);
					}
					else{
						row.getCell("attachment").setValue(Boolean.FALSE);
					}
					if(auditMap.containsKey(idkey)){
						MultiApproveInfo info = (MultiApproveInfo)auditMap.get(idkey);
						//����ж༶��������˱���ͻᵼ����������˺��������ʱ�����ݲ���ȷ,BUG,by Cassiel_peng
//						if(row.getCell("auditor").getValue()==null){
//							row.getCell("auditor").setValue(info.getCreator().getName());
//							row.getCell("auditTime").setValue(info.getCreateTime());
//						}
					}
					if(isMainOrder){
//							 node = new CellTreeNode();
						String number = "";
						if(row.getCell("mainContractNumber")!=null){
							if (row.getCell("mainContractNumber").getValue() != null) {
								number = row.getCell("mainContractNumber").getValue().toString();
							}
							if (!number.equals(preNumber)) {
								row.setTreeLevel(0);
							} else  if (!"".equals(number)){//
								row.setTreeLevel(1);
								tblMain.getTreeColumn().setDepth(2);
								row.getCell("number").setValue("   " + row.getCell("number").getValue());
							}
							preNumber = number;
							
							//tblMain.getColumn("mainContractNumber").getStyleAttributes().setHided(true);
						}
					}
				}
			}
		});
				
		super.onLoad();
//		actionStore.putValue(Action.SMALL_ICON, EASResource
//				.getIcon("imgTbtn_archive"));
//		actionAntiStore.putValue(Action.SMALL_ICON, EASResource
//				.getIcon("imgTbtn_undistribute"));
		
//		this.btnStore.setIcon(EASResource.getIcon("imgTbtn_archive"));
/*		kDSplitPane2.add(new KDLabel(""), "bottom");
//		this.kDSplitPane2.setDividerLocation(getHeight());
		kDSplitPane2.setDividerLocation(1.0);
		this.kDSplitPane2.setDividerSize(0);
		kDScrollPane2.validate();*/
//		kDSplitPane2.add(contContrList, "bottom");
//		kDSplitPane2.setDividerLocation(1.0);
//		actionConMove.setEnabled(true);
		actionQuery.setVisible(true);
		actionQuery.setEnabled(true);
//		actionProjectAttachment.setVisible(false);
//		actionProjectAttachment.setEnabled(false);
		
//		this.btnEditPerson.setIcon(EASResource.getIcon("imgTbtn_rename"));
//		this.btnEditAuditDate.setIcon(EASResource.getIcon("imgTbtn_rename"));
//		this.btnEditContractType.setIcon(EASResource.getIcon("imgTbtn_rename"));
//		this.btnUnProgram.setIcon(EASResource.getIcon("imgTbtn_associatecreate"));
		if(this.getUIContext().get("filter")!=null){
			this.toolBar.setVisible(false);
//			this.pnlLeftTree.setVisible(false);
			mainQuery=new EntityViewInfo();
			mainQuery.setFilter((FilterInfo) this.getUIContext().get("filter"));
			execQuery();
		}
		this.actionExportData.setVisible(false);
		this.tblContractBill.getSelectManager().setSelectMode(KDTSelectManager.ROW_SELECT);
		this.tblContractBill.setEnabled(false);
		FDCHelper.formatTableNumber(this.tblContractBill, "amount");
		
		tHelper=null;
		
		this.actionContract.setVisible(false);
	}
	
	protected void updateButtonStatus() {
		// TODO Auto-generated method stub
		super.updateButtonStatus();
		
//		�ſ��������������ɾ��
		actionAddNew.setEnabled(true);
		actionEdit.setEnabled(true);
		actionRemove.setEnabled(true);
		actionAddNew.setVisible(true);
		actionEdit.setVisible(true);
		actionRemove.setVisible(true);
		menuEdit.setVisible(true);
//		actionRespite.setVisible(true);
//		actionCancelRespite.setVisible(true);

	}
	
	public void refreshListForOrder() throws Exception
    {
        SorterItemCollection sortItems = this.mainQuery.getSorter();
        if(sortItems!=null && sortItems.size()>0){
        	for(Iterator it=sortItems.iterator();it.hasNext();){
        		SorterItemInfo sortItem = (SorterItemInfo)it.next();
        		if(sortItem.getPropertyName().equals("mainContractNumber")){
        			SorterItemInfo cloneSort = (SorterItemInfo)sortItem.clone();
        			SorterItemInfo newSort = new SorterItemInfo();
        			newSort.setPropertyName("contractPropert");
        			newSort.setSortType(SortType.ASCEND);
        			this.mainQuery.getSorter().clear();
        			this.mainQuery.getSorter().add(cloneSort);
        			this.mainQuery.getSorter().add(newSort);
        			break;
        		}
        	}
        }
        super.refreshListForOrder();
    }

	/**
	 * output tblMain_tableSelectChanged method
	 */
protected void tblMain_tableSelectChanged(
			com.kingdee.bos.ctrl.kdf.table.event.KDTSelectEvent e)
			throws Exception {
		super.tblMain_tableSelectChanged(e);	
		IRow row;
		if (this.tblMain.getSelectManager().getActiveRowIndex()!= -1) {
			row = this.tblMain.getRow(this.tblMain.getSelectManager().getActiveRowIndex());
			if((tblMain.getSelectManager().getBlocks().size() > 1) 
				||(e.getSelectBlock().getBottom() - e.getSelectBlock().getTop() >0)){
//				actionRespite.setEnabled(true);
//				actionCancelRespite.setEnabled(true);
			}else{
//				if(Boolean.TRUE.equals(row.getCell("isRespite").getValue())){
//					actionRespite.setEnabled(false);
//					actionCancelRespite.setEnabled(true);
//				}else{
//					actionRespite.setEnabled(true);
//					actionCancelRespite.setEnabled(false);
//				}
			}
			if(row.getCell("state").getValue().equals("�ϼ�")) return;
			if (row.getCell("state") != null) {		
//				if(FDCBillStateEnum.AUDITTED_VALUE.equalsIgnoreCase(((BizEnumValueInfo)row.getCell("state").getValue()).getValue().toString())){
//					if((e.getSelectBlock().getBottom() - e.getSelectBlock().getTop() ==0)&&
//							(tblMain.getSelectManager().getBlocks().size()== 1)){
//						actionCancelRespite.setEnabled(true);
//						actionRespite.setEnabled(false);
//					}
//					
//					if(!((Boolean)row.getCell("isArchived").getValue()).booleanValue()){
//						actionStore.setEnabled(true);
//						actionAntiStore.setEnabled(false);
//					}else{
//						actionAntiStore.setEnabled(true);
//						actionStore.setEnabled(false);
//					}
//				}else{
//					actionStore.setEnabled(false);
//					actionAntiStore.setEnabled(false);
//				}
			}
			if(row.getCell("id").getValue()!=null){
				this.tblContractBill.removeRows();
				String id=row.getCell("id").getValue().toString();
				ContractBillCollection col=ContractBillFactory.getRemoteInstance().getContractBillCollection("select *,contractType.*,partB.*,partC.*,respPerson.*,respDept.*,landDeveloper.* from where contractBillReceive.id='"+id+"'");
				for(int i=0;i<col.size();i++){
					IRow cbRow=this.tblContractBill.addRow();
					cbRow.getCell("id").setValue(col.get(i).getId().toString());
					cbRow.getCell("contractType").setValue(col.get(i).getContractType().getName());
					cbRow.getCell("number").setValue(col.get(i).getNumber());
					cbRow.getCell("contractName").setValue(col.get(i).getName());
					cbRow.getCell("amount").setValue(col.get(i).getAmount());
					cbRow.getCell("partB").setValue(col.get(i).getPartB().getName());
					cbRow.getCell("signDate").setValue(col.get(i).getBookedDate());
					cbRow.getCell("landDeveloper").setValue(col.get(i).getLandDeveloper().getName());
					if(col.get(i).getPartC()!=null)cbRow.getCell("partC").setValue(col.get(i).getPartC().getName());
					cbRow.getCell("contractPropert").setValue(col.get(i).getContractPropert().getAlias());
					cbRow.getCell("respPerson").setValue(col.get(i).getRespPerson().getName());
					cbRow.getCell("respDept").setValue(col.get(i).getRespDept().getName());
				}
			}
		}
	}

	@Override
	protected void tblContractBill_tableClicked(KDTMouseEvent e)
			throws Exception {
		if (e.getType() == KDTStyleConstants.BODY_ROW && e.getButton() == MouseEvent.BUTTON1 && e.getClickCount() == 2){
			String id=tblContractBill.getRow(e.getRowIndex()).getCell("id").getValue().toString();
			UIContext uiContext = new UIContext(this);
			uiContext.put(UIContext.OWNER, this);
			uiContext.put("ID", id);
			IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.NEWTAB).create(ContractBillEditUI.class.getName(), uiContext, null, OprtState.VIEW);
			uiWindow.show();
		}
	}

	public void actionStore_actionPerformed(ActionEvent e) throws Exception {
		checkSelected();
		HashMap hm = new UIContext(this);
		hm.put("isUpdateConNo", Boolean.valueOf(isUpdateConNo));
		IRow row;
		if (this.tblMain.getSelectManager().getActiveRowIndex()== -1) {
			//��ʾѡ��
		}else{
			row = this.tblMain.getRow(this.tblMain.getSelectManager().getActiveRowIndex());
			
			if(row.getCell("id")!=null){
				hm.put("id", row.getCell("id").getValue().toString());
			}
			if(row.getCell("number")!=null){
				hm.put("number", row.getCell("number").getValue().toString());
			}
			/*
			 * �ѵ�ǰѡ�еĹ�����Ŀ�ͺ�ͬ���ʹ���EditUI
			 */
//			BOSUuid projId = ((CurProjectInfo) getProjSelectedTreeNode().getUserObject()).getId();
//			BOSUuid typeId = null;
//			if (getTypeSelectedTreeNode() != null && getTypeSelectedTreeNode().isLeaf()) {
//
//				typeId = ((ContractTypeInfo) getTypeSelectedTreeNode().getUserObject()).getId();
//			}
//			hm.put("projectId", projId);
//			hm.put("contractTypeId", typeId);
			
		}	
		IUIFactory uiFactory = null;
		uiFactory = UIFactory.createUIFactory(UIFactoryName.MODEL);
		IUIWindow uiWindow = uiFactory.create("com.kingdee.eas.fdc.contract.client.ContractBillStoreUI", hm, null,null);
		uiWindow.show();
		actionRefresh_actionPerformed(e);	
	}
	
	//����ʱ�������ݻ���
	public void actionAudit_actionPerformed(ActionEvent e) throws Exception {
//		��ȡ�û�ѡ��Ŀ�
		List idList =ContractClientUtils.getSelectedIdValues(getBillListTable(), getKeyFieldName());
		boolean hasMutex = false;
		try{
			
			FDCClientUtils.requestDataObjectLock(this, idList, "Audit");
//			if(isRelatedTask()){
//				for(int i = 0 ;i<idList.size();i++){
//					String idKey = idList.get(i).toString();
//					ContractBillReceiveInfo contract = ContractBillReceiveFactory.getRemoteInstance().getContractBillReceiveInfo(new ObjectUuidPK(idKey));
//					ContractClientUtils.checkConRelatedTaskSubmit(contract);
//				}
//			}
			
			
			super.actionAudit_actionPerformed(e);
	
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
	}
	//��ͬ�ύ����ʱ�Ƿ������ƻ�������й��� 2010-08-09
	private boolean isRelatedTask(){
		boolean isRealtedTask = false;
		String cuID = SysContext.getSysContext().getCurrentCostUnit().getId().toString();
		try {
			isRealtedTask = FDCUtils.getDefaultFDCParamByKey(null, cuID, FDCConstants.FDC_PARAM_RELATEDTASK);
		} catch (EASBizException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (BOSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return isRealtedTask;
	}
	
	public void actionUnAudit_actionPerformed(ActionEvent e) throws Exception {
		FDCClientHelper.checkAuditor(getSelectedIdValues(), "t_con_ContractBill");
		
		//R110603-0148:������ڱ��������������������
	    if (ContractUtil.hasChangeAuditBill(null, getSelectedIdValues())) {
    		FDCMsgBox.showWarning(this, EASResource.getString("com.kingdee.eas.fdc.contract.client.ContractResource", "hasChangeAuditBill"));
			this.abort();
    	}
    	
    	//R110603-0148:������ڱ��ָ�������������
    	if (ContractUtil.hasContractChangeBill(null, getSelectedIdValues())) {
    		FDCMsgBox.showWarning(this, EASResource.getString("com.kingdee.eas.fdc.contract.client.ContractResource", "hasContractChangeBill"));
			this.abort();
    	}
    	
		if(isRelatedTask()){
			checkConRelatedTaskDelUnAudit();
		}
		super.actionUnAudit_actionPerformed(e);
	}
	
	public void actionRemove_actionPerformed(ActionEvent e) throws Exception {
		if(isRelatedTask()){
			checkConRelatedTaskDelUnAudit();
		}
		super.actionRemove_actionPerformed(e);
		// ά����ܺ�Լ�Ƿ������ֶ�
	}
	
	/**
	 * ����ͬ�Ƿ������ܺ�Լ
	 * 
	 * @return
	 * @throws Exception
	 */
	private String checkReaPre() throws Exception {
		String billId = null;
		String selectedKeyValue = getSelectedKeyValue();
		FDCSQLBuilder builder = new FDCSQLBuilder();
		SelectorItemCollection sic = new SelectorItemCollection();
		sic.add("*");
		sic.add("programmingContract.*");
		builder.appendSql("select fprogrammingContract from T_CON_CONTRACTBILL where 1=1 and ");
		builder.appendParam("fid", selectedKeyValue);
		IRowSet rowSet = builder.executeQuery();
		while (rowSet.next()) {
			if (rowSet.getString("fprogrammingContract") != null) {
				billId=rowSet.getString("fprogrammingContract");
			}
		}
		return billId;
	}

	
	private void checkConRelatedTaskDelUnAudit() throws BOSException, SQLException{
		List selectedIdValues = ContractClientUtils.getSelectedIdValues(
				getBillListTable(), getKeyFieldName());
		
		if(selectedIdValues!=null&&selectedIdValues.size()==0){
			return;
		}
		
		Set contractIds = FDCHelper.getSetByList(selectedIdValues);
		FDCSQLBuilder builder = new FDCSQLBuilder();
		builder.appendParam("select fid,count(fid) jishu from T_SCH_ContractAndTaskRel where FContractID",contractIds.toArray());
		builder.appendSql(" group by fid ");
		IRowSet rowSet = builder.executeQuery();
		Set ids = new HashSet();
		while(rowSet.next()){
			int count = rowSet.getInt("jishu");
			if(count >0){
				String id = rowSet.getString("fid");
				ids.add(id);
			}
		}
		boolean flag = false;
		if(ids.size()>0){
			builder.clear();
			builder.appendParam("select count(fid) jishu from T_SCH_ContractAndTaskRelEntry where FParentID  ",ids.toArray());
			IRowSet _rowSet = builder.executeQuery();
			while(_rowSet.next()){
				int _count = _rowSet.getInt("jishu");
				if(_count > 0){
					flag = true;
					break;
				}
			}
		}
		if(flag){
			FDCMsgBox.showInfo("��ͬ�ѱ��������������ִ�д˲�����");
			SysUtil.abort();
		}
		
	}
	protected String getStateForUnAudit() {
		return FDCBillStateEnum.AUDITTED_VALUE;
	}
	
	//���鵵
    public void actionAntiStore_actionPerformed(ActionEvent e) throws Exception
    {
    	checkSelected();
    	
    	checkBillState(new String[]{getStateForUnAudit()}, "selectRightRowForUnAudit");
    	
    	List selectedIdValues = ContractClientUtils.getSelectedIdValues(getBillListTable(), getKeyFieldName());
    	
		IContractBillReceive icb = ContractBillReceiveFactory.getRemoteInstance();
		boolean flag = false;
		flag = icb.contractBillAntiStore(selectedIdValues);
		if (flag) {				
			FDCClientUtils.showOprtOK(this);
			actionRefresh_actionPerformed(e);	
		}
    }

    /**
     * ֧�ֶ�λ���ܣ���λ�ֶ��У���ͬ��ţ���ͬ���ƣ���ͬ���ͣ�ǩԼʱ�䣬״̬��ǩԼ�ҷ�����ͬԭ�ҽ���λ�ҽ��
     * Modified by Owen_wen 2010-09-06
     */
	protected String[] getLocateNames()
    {
		return new String[] {IFWEntityStruct.dataBase_Number, IFWEntityStruct.dataBase_Name, "contractType.name", 
				"signDate", "state", "partB.name", "originalAmount", "amount",};
    }
	/**
	 * output getEditUIName method
	 */
	protected String getEditUIName() {
//		if(viewType==0){
			return com.kingdee.eas.fdc.contract.client.ContractBillReceiveEditUI.class.getName();
//		}else{
//			return getContractUIName() ;
//		}
	}
	
	protected String getContractUIName() {
		return com.kingdee.eas.fdc.contract.client.ContractFullInfoUI.class.getName();
	}

	/**
	 * output getBizInterface method
	 */
	protected com.kingdee.eas.framework.ICoreBase getBizInterface() throws Exception {
		return com.kingdee.eas.fdc.contract.ContractBillReceiveFactory.getRemoteInstance();
	}

	/**
	 * output createNewData method
	 */
	protected com.kingdee.bos.dao.IObjectValue createNewData() {
		com.kingdee.eas.fdc.contract.ContractBillReceiveInfo objectValue = new com.kingdee.eas.fdc.contract.ContractBillReceiveInfo();
		objectValue.setCurProject((CurProjectInfo) getProjSelectedTreeNode().getUserObject());
		if (getTypeSelectedTreeNode() != null && getTypeSelectedTreeNode().isLeaf()) {
			objectValue.setContractType((ContractTypeInfo) getTypeSelectedTreeNode().getUserObject());
		}
		return objectValue;
	}

	protected ICoreBillBase getRemoteInterface() throws BOSException {
		return (ICoreBillBase) ContractBillReceiveFactory.getRemoteInstance();
	}

	protected void audit(List ids) throws Exception {
		FDCSQLBuilder builder = new FDCSQLBuilder();
	    builder.appendSql("select bill.fcontractpropert from t_con_contractbillentry entry ");
	    builder.appendSql("inner join t_con_contractbill bill on  bill.fid=entry.fparentid ");
	    builder.appendSql("inner join t_con_contractbill main on main.fid=entry.fcontent and main.fstate <> '4AUDITTED' ");
	    builder.appendSql("where ");
	    builder.appendParam("bill.fid", FDCHelper.list2Set(ids).toArray());
	    builder.appendSql(" and bill.fcontractpropert='SUPPLY' ");
	    builder.appendSql(" and entry.fdetail='��Ӧ����ͬ����' ");
	    IRowSet rs = builder.executeQuery();
	    if(rs!=null&&rs.size()==1){
	    	rs.next();
	    	String prop = rs.getString("fcontractpropert");
	    	if("SUPPLY".equals(prop)){
	    		FDCMsgBox.showWarning(this,"����ѡ��������д�������ͬδ�����Ĳ����ͬ�����ܽ��д˲���!");
		    	this.abort();
	    	}
	    }
	        
		ContractBillReceiveFactory.getRemoteInstance().audit(ids);
	}
	
	/**
	 * ���Ϊ�ǵ������㲹���ͬ������������
	 * 
	 * @param billId
	 */
	private int unLongContract(List ids) {
		FDCSQLBuilder builder = new FDCSQLBuilder();
		builder.appendSql(" select count(1) from t_con_contractbillentry entry");
		builder.appendSql(" inner join t_con_contractbill con on con.fid = entry.fparentid   ");
		builder.appendSql(" inner join t_con_contractbillentry entry2 on entry2.fparentid = entry.fparentid ");
		builder.appendSql("  where entry.frowkey = 'am' and");
		builder.appendParam(" entry2.fcontent", FDCHelper.list2Set(ids).toArray());
		try {
			RowSet rowSet = builder.executeQuery();
			while (rowSet.next()) {
				return rowSet.getInt(1);
			}
		} catch (Exception e) {
			logger.error(e);
		}
		return 0;
	}

	protected void unAudit(List ids) throws Exception {
		// ���Ϊ�ǵ������㲹���ͬ������������
		int unLongContract = unLongContract(ids);
		if (unLongContract == 1) {
			FDCMsgBox.showWarning("��ͬ�ѹ��������ͬ���ܽ��д˲���");
			SysUtil.abort();
		}
		
		FDCSQLBuilder builder = new FDCSQLBuilder();
	    builder.appendSql("select * from t_con_contractbillentry entry ");
	    builder.appendSql("inner join t_con_contractbill bill on bill.fid=entry.fparentid ");
	    builder.appendSql("inner join t_con_contractbill main on  main.fid=entry.fcontent ");
	    builder.appendSql("where");
	    builder.appendParam("main.fid", FDCHelper.list2Set(ids).toArray());
	    if(isSupply){
	    	builder.appendSql(" and bill.fstate='4AUDITTED'");
	    }
	    IRowSet rs = builder.executeQuery();
	    if(rs!=null&&rs.size()>0){
			FDCMsgBox.showWarning(this, "����ѡ������ݴ��ڱ������ͬ���õĺ�ͬ�����ܽ��д˲���!");
			this.abort();
		}
	    
	    builder.clear();
	    builder.appendSql("select count(fid) from t_con_contractbill where ");
	    builder.appendParam("FMainContractID", FDCHelper.list2Set(ids).toArray());
	    rs = builder.executeQuery();
	    if(rs!= null && rs.size() > 0 && rs.next() && rs.getInt(1) > 0){
	    	FDCMsgBox.showWarning(this, "����ѡ������ݴ��ڱ�ս���Ӻ�ͬ���õĺ�ͬ�����ܽ��д˲���!");
			this.abort();
	    }
		ContractBillReceiveFactory.getRemoteInstance().unAudit(ids);
	}

	protected KDTable getBillListTable() {

		return getMainTable();
	}

	protected void initTable() {
		super.initTable();
		
		//freezeMainTableColumn();
		FDCHelper.formatTableNumber(getMainTable(), "srcAmount");
		FDCHelper.formatTableNumber(getMainTable(), "amount");
//		FDCHelper.formatTableNumber(getMainTable(), "exRate");
		getMainTable().getColumn("exRate").getStyleAttributes().setHided(true);
		//FDCHelper.formatTableNumber(getMainTable(), "localAmount");
		getMainTable().getSelectManager().setSelectMode(KDTSelectManager.MULTIPLE_ROW_SELECT);
	}

	protected ArrayList getExportParam() {
		
		DatataskParameter param = new DatataskParameter();
		param.solutionName = "eas.fdc.contract.fdccontractbill";
		param.alias = "��ͬ����";
		param.datataskMode = DatataskMode.ExpMode;
		//param.putContextParam("ids",FDCHelper.list2Set(idList));
		ArrayList paramList = new ArrayList();
		paramList.add(param);
		return paramList;
		
	}
	public void actionExportData_actionPerformed(ActionEvent e) throws Exception
    {
		checkSelected();
		List idList = ContractClientUtils.getSelectedIdValues(
				getBillListTable(), getKeyFieldName());
//		FilterInfo filter = this.mainQuery.getFilter();
//		if(filter != null){
//			boolean flag = false;
//			for(Iterator it=filter.getFilterItems().iterator();it.hasNext();){
//				FilterItemInfo info = (FilterItemInfo)it.next();
//				if(info.getPropertyName().equals("id")){
//					info.setCompareValue(FDCHelper.list2Set(idList));
//					info.setCompareType(CompareType.INCLUDE);
//					flag = true;
//					break;
//				}
//			}
//			if(flag==false){
//				filter.getFilterItems().add(new FilterItemInfo("id",FDCHelper.list2Set(idList),CompareType.INCLUDE));
//				filter.setMaskString("( "+filter.getMaskString()+" ) and #"+(filter.getFilterItems().size()-1));
//			}
//			
//		}
		ArrayList para = getExportParam();
        if (para == null || para.size() <= 0)
        {
            throw new FrameWorkException(FrameWorkException.EXPORTDATAPARANULL);// "��������ȷ������������"�˴���Ҫ�޸ģ�����EASBizException�ж�����쳣��Ϣ

        }

        Object tmp = para.get(0);
        if (tmp instanceof DatataskParameter)
        {
            DatataskParameter dp = (DatataskParameter) tmp;
            IMetaDataPK contractExportQueryPK = new MetaDataPK("com.kingdee.eas.fdc.contract.app", "ContractBillForExportQuery");
            EntityViewInfo view = new EntityViewInfo();
            //view.getSelector().add("*");
            FilterInfo filter = new FilterInfo();
            filter.getFilterItems().add(new FilterItemInfo("id",FDCHelper.list2Set(idList),CompareType.INCLUDE));
            view.setFilter(filter);
            dp.putContextParam("mainQueryPK", contractExportQueryPK);
            dp.putContextParam("mainQuery", view);
        }

        DatataskCaller dc = new DatataskCaller();
        dc.setParentComponent(this);
        dc.invoke(para, DatataskMode.ExpMode);
    }

	/**
	 * 
	 * ������Ϊ��ǰ���ݵ��������༭���鿴׼��Context
	 * @author:liupd
	 * @see com.kingdee.eas.framework.client.CoreBillListUI#prepareUIContext(com.kingdee.eas.common.client.UIContext, java.awt.event.ActionEvent)
	 */
	protected void prepareUIContext(UIContext uiContext, ActionEvent e) {
		super.prepareUIContext(uiContext, e);
		ItemAction act = getActionFromActionEvent(e);
		if (act.equals(actionAddNew)) {

			/*
			 * �ѵ�ǰѡ�еĹ�����Ŀ�ͺ�ͬ���ʹ���EditUI
			 */
			BOSUuid projId = ((CurProjectInfo) getProjSelectedTreeNode().getUserObject()).getId();
			BOSUuid typeId = null;
			if (getTypeSelectedTreeNode() != null && getTypeSelectedTreeNode().isLeaf()) {
				if(getTypeSelectedTreeNode().getUserObject() instanceof ContractTypeInfo){
					typeId = ((ContractTypeInfo) getTypeSelectedTreeNode().getUserObject()).getId();
				}
			}
			uiContext.put("projectId", projId);
			uiContext.put("contractTypeId", typeId);
		}
	}

	protected boolean isHasBillTable() {
		return false;
	}

	/**
	 * 
	 * ����������Ƿ��й�������
	 * @author:liupd
	 * ����ʱ�䣺2006-8-26 <p>
	 */
	protected void checkRef(String id) throws Exception {
		ContractClientUtils.checkContractBillRef(this, id);

	}

	/**
	 * ���̸���
	 */
	public void actionProjectAttachment_actionPerformed(ActionEvent e) throws Exception {
		//�����������ڵ�����̸�����ʱ��ʹûѡ����ֵѡ���˹���Ҳ��Ҫ����Ӹ��� fengYJ 2008-12-16
		//		checkSelected();
//		super.actionProjectAttachment_actionPerformed(e);

		AttachmentClientManager acm = AttachmentManagerFactory.getClientManager();
		String contractId = this.getSelectedKeyValue();
		if(contractId!=null){
			ContractBillReceiveInfo contract = ContractBillReceiveFactory.getRemoteInstance().getContractBillReceiveInfo(new ObjectUuidPK(BOSUuid.read(contractId)));
			CurProjectInfo curProject = contract.getCurProject();
			if (curProject == null) {
				return;
			}
			acm.showAttachmentListUIByBoID(curProject.getId().toString(), this);
			
		//update by david_yang 2011.03.29
		}else if(getProjSelectedTreeNode().getUserObject() instanceof CurProjectInfo){
			CurProjectInfo curProject = (CurProjectInfo) getProjSelectedTreeNode().getUserObject();
			if (curProject == null) {
				return;
			}
			acm.showAttachmentListUIByBoID(curProject.getId().toString(), this);
		}else{
			MsgBox.showWarning("��ѡ�񹤳���Ŀ������Ӹ�����");
		}
	}
	
    public void actionAttachment_actionPerformed(ActionEvent e) throws Exception
    {
//    	super.actionAttachment_actionPerformed(e);
//    	checkSelected(tblMain);
    	boolean isEdit=false;
    	AttachmentClientManager acm = AttachmentManagerFactory.getClientManager();
    	String boID = this.getSelectedKeyValue();
    	if (boID == null)
    	{
    		return;
    	}
    	/* 896*/        boolean flag = FDCClientUtils.isBillInWorkflow(boID);
    	/* 897*/        if(flag)
    	                {/* 898*/            ICoreBase coreBase = getBizInterface();
    	/* 899*/            if(null == coreBase)
    	                    {/* 900*/                return;
    	                    } else
    	                    {
    	/* 903*/                CoreBaseInfo editData = coreBase.getValue(new ObjectUuidPK(boID));
    	/* 904*/                AttachmentUIContextInfo info = new AttachmentUIContextInfo();
    	/* 905*/                info.setBoID(boID);
    	/* 906*/                MultiApproveUtil.showAttachmentManager(info, this, editData, String.valueOf("1"), false);

    	/* 908*/                return;
    	                    }
    	                }
    	if(getBillStatePropertyName()!=null){
    		int rowIdx=tblMain.getSelectManager().getActiveRowIndex();
    		ICell cell =tblMain.getCell(rowIdx, getBillStatePropertyName());
    		Object obj=cell.getValue();
    		isEdit=ContractClientUtils.canUploadAttaForAudited(obj, canUploadForAudited);
    	}
    	//add by david_yang PT043562 2011.03.29
    	 if(isEdit){
         	//��ͬ
 	        String orgId = SysContext.getSysContext().getCurrentOrgUnit().getId().toString();
 	        String userId = SysContext.getSysContext().getCurrentUserInfo().getId().toString();
 			if(this.getClass().getName().equals("com.kingdee.eas.fdc.contract.client.ContractBillReceiveListUI")){
 				String uiName = "com.kingdee.eas.fdc.contract.client.ContractBillReceiveEditUI";
 				boolean hasFunctionPermission = PermissionFactory.getRemoteInstance().hasFunctionPermission(
 	    				new ObjectUuidPK(userId),
 	    				new ObjectUuidPK(orgId),
 	    				new MetaDataPK(uiName),
 	    				new MetaDataPK("ActionAttamentCtrl") );
 				//���δ���ò�������Ȩ�޵��û����ܽ��и���ά��,����Ѿ������˲������Ƶ��˵��ڵ�ǰ�û�����Ȩ�޲��ܽ��� ά��
 	        	if(hasFunctionPermission){
 	        		boolean creatorCtrl=FDCUtils.getDefaultFDCParamByKey(null, null, FDCConstants.FDC_PARAM_CREATORATTACHMENT);
 	        		if(creatorCtrl){
 	        			//�Ƶ���Ҫ���ڵ�ǰ�û�����
 	        			FDCSQLBuilder builder=new FDCSQLBuilder();
 	        			builder.appendSql("select 1 from T_Con_ContractBillReceive where fid=? and fcreatorId=?");
 	        			builder.addParam(boID);
 	        			builder.addParam(userId);
 	        			if(!builder.isExist()){
 	        				isEdit=false;
 	        			}
 	        		}
 	        	}else{
 	        		isEdit=false;
 	        	}
 	        }
    	}
    	acm.showAttachmentListUIByBoID(boID,this,isEdit);
    	this.refreshList();
    }

	/**
	 * �鿴����
	 */
	public void actionViewContent_actionPerformed(ActionEvent arg0) throws Exception {
			this.checkSelected();
			ContractBillReceiveInfo billInfo=ContractBillReceiveFactory.getRemoteInstance().getContractBillReceiveInfo(new ObjectUuidPK(BOSUuid.read(getSelectedKeyValue())));
			if(isUseWriteMark&&billInfo!=null&&billInfo instanceof FDCBillInfo){//����ֵΪ�ǣ����һ���Ҫ��FDCBill.
				//��ʱ������Ĳ���״̬Ĭ�ϵ�ΪVIEW,������Ҫ�ܹ�ʹ������ʱ��������(���赥��״̬����)�ܹ���ӡ��޸ġ�ɾ�����ľͲ���ֱ�Ӵ���һ�� getOprtState()״̬ 
				ForWriteMarkHelper.isUseWriteMarkForListUI(billInfo,OprtState.EDIT,this);
			}else{
				ContractClientUtils.viewContent(this, this.getSelectedKeyValue());
			}
		}
	
	/**
	 * ��ִͬ����Ϣ
	 */
    public void actionViewContract_actionPerformed(ActionEvent e) throws Exception
    {
    	viewType=-1;
    	try {
			actionView_actionPerformed(e);
		} catch (Exception e1) {
			throw e1;
		}finally{
			viewType=0;
		}    	
    }
    //������������Ӻ�ͬ����  by cassiel_peng
    private boolean isAddContentAfterAudited() {
		boolean returnVal=false;
		try {
			returnVal=FDCUtils.getDefaultFDCParamByKey(null, null,FDCConstants.FDC_PARAM_ADDCONTENTAUDITED);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return returnVal;
	}
	/**
	 * �������
	 */
	public void actionAddContent_actionPerformed(ActionEvent e) throws Exception {
		this.checkSelected();
		String selectedKeyValue = this.getSelectedKeyValue();
		/*ContractBillReceiveInfo contract = new ContractBillReceiveInfo();
		String selectedKeyValue = this.getSelectedKeyValue();
		contract.setId(BOSUuid.read(selectedKeyValue));
		FilterInfo filter = new FilterInfo();
		filter.appendFilterItem("id", selectedKeyValue);
		filter.appendFilterItem("state", FDCBillStateEnum.CANCEL_VALUE);
		if(ContractBillReceiveFactory.getRemoteInstance().exists(filter)) {
			MsgBox.showWarning(this, "��ͬ����ֹ������������ģ�");
			SysUtil.abort();
		}*/
		//�������ĺ�ͬ�����������   by cassiel_peng 2009-11-06
		SelectorItemCollection selector=new SelectorItemCollection();
		selector.add("id");
		selector.add("state");
		ContractBillReceiveInfo contractBill=ContractBillReceiveFactory.getRemoteInstance().getContractBillReceiveInfo(new ObjectUuidPK(BOSUuid.read(selectedKeyValue)),selector);
		if(contractBill!=null&&contractBill.getState()!=null&&FDCBillStateEnum.CANCEL.equals(contractBill.getState())){
			MsgBox.showWarning(this, "��ͬ����ֹ������������ģ�");
			SysUtil.abort();
		}
		if(!isAddContentAfterAudited()){
			if(contractBill!=null&&contractBill.getState()!=null&&FDCBillStateEnum.AUDITTED.equals(contractBill.getState())){
				MsgBox.showWarning(this, "��ͬ������������������ģ�");
				SysUtil.abort();
			}
		}
		
		ContractContentCollection coll = ContractContentFactory.getRemoteInstance().getContractContentCollection("select id,filetype where contract='" + contractBill.getId().toString() + "'");
		if (coll != null && coll.size() > 0) {
			//���Σ���˵һ����ͬ������������ľ��õڶ������ǵ�һ�������Ǹ���by cassiel_peng
			/*if (MsgBox.showConfirm2(this, ContractClientUtils.getRes("hasContent")) == 2) {
				return;
			}*/
//			MsgBox.showWarning("�Ѿ�����һ�����ģ��ٴ��������Ĭ�ϵص�һ�����ģ�");
		}
		File file = this.chooseFileByDialog();
		if (file == null) {
			return;
		}
		
		String fullname = file.getName();
		String extName = StringUtil4File.getExtendedFileName(fullname);
		for(int i=0;i<coll.size();i++){
			if(fullname.equals(((ContractContentInfo)coll.get(i)).getFileType())){
				MsgBox.showWarning(fullname+" �Ѿ����ڣ���������ӣ�");
				return;
			}
		}
		if(!file.canRead()) {
			MsgBox.showWarning(this, ContractClientUtils.getRes("readFileError"));
			SysUtil.abort();
		}
		byte[] content = null;
		try {
			content = FileGetter.getBytesFromFile(file);
		} catch (IOException ex) {
			MsgBox.showWarning(this, ContractClientUtils.getRes("readFileError"));
			SysUtil.abort();
		}
		
		ContractContentInfo contentInfo = new ContractContentInfo();
		contentInfo.setVersion(new BigDecimal("1.0"));
//		contentInfo.setContract(contractBill);
		contentInfo.setFileType(fullname);
		contentInfo.setContentFile(content);
		ContractContentFactory.getRemoteInstance().addnew(contentInfo);
//		super.actionAddContent_actionPerformed(e);
	}
	public void actionProgram_actionPerformed(ActionEvent e) throws Exception {
//		checkSelected();
//		List idList =ContractClientUtils.getSelectedIdValues(getBillListTable(), getKeyFieldName());
//		if(idList.size()>1){
//			FDCMsgBox.showWarning("��ѡ��һ����¼���в�����");
//			SysUtil.abort();
//		}
//		String selectedKeyValue = getSelectedKeyValue();
////		if (FDCUtils.isRunningWorkflow(selectedKeyValue)) {
////			FDCMsgBox.showWarning("���ڹ����������У����Ժ����ԣ�");
////			SysUtil.abort();
////		}
//		SelectorItemCollection sic = new SelectorItemCollection();
//		sic.add("*");
//		sic.add("programmingContract.*");
//		sic.add("curProject.isWholeAgeStage");
//		ContractBillReceiveInfo ContractBillReceiveInfo = ContractBillReceiveFactory.getRemoteInstance().getContractBillReceiveInfo(new ObjectUuidPK(selectedKeyValue), sic);
//		if(ContractBillReceiveInfo.getCurProject().isIsWholeAgeStage()){
//			FDCMsgBox.showWarning("����п��ں�ͬ��Լ�滮��ֲ�����");
//			SysUtil.abort();
//		}
//		if(ContractBillReceiveInfo.getContractPropert().equals(ContractPropertyEnum.SUPPLY)){
//			FDCMsgBox.showWarning("��ѡ������ͬ���к�Լ�滮������");
//			SysUtil.abort();
//		}
//		boolean isRealToProgramming =FDCUtils.getDefaultFDCParamByKey(null,ContractBillReceiveInfo.getOrgUnit().getId().toString(), "FDC900_CON_OLDCONTRACTRTOPROGRAMMING");
//		if(isRealToProgramming){
//			if(!ContractBillReceiveInfo.getState().equals(FDCBillStateEnum.AUDITTED)){
//				FDCMsgBox.showWarning("��ͬδ���������ܽ��к�Լ�滮������");
//				SysUtil.abort();
//			}
//		}else{
//			FDCMsgBox.showWarning("�������ͬ������Լ�滮����δ���ã����ܽ��к�Լ�滮������");
//			SysUtil.abort();
//		}
//		
//		ProgrammingContractInfo pc = (ProgrammingContractInfo) ContractBillReceiveInfo.getProgrammingContract();
//		IUIWindow uiWindow = null;
//		UIContext uiContext = new UIContext(this);
//		uiContext.put("ContractBillReceiveInfo", ContractBillReceiveInfo);
//		uiContext.put("isWholeAgeStage", new Boolean(ContractBillReceiveInfo.getCurProject().isIsWholeAgeStage()));
//		uiWindow = UIFactory.createUIFactory(UIFactoryName.MODEL).create(ContractBillLinkProgContEditUI.class.getName(), uiContext, null,OprtState.EDIT);
//		uiWindow.show();
//		if (ContractBillReceiveInfo.getProgrammingContract() != null) {
//			Object object = uiWindow.getUIObject().getUIContext().get("cancel");
//			if (object != null) {
//				return;
//			} else {
//				String param = null;
//				SelectorItemCollection sict = new SelectorItemCollection();
//				sict.add("*");
//				sict.add("fullOrgUnit.*");
//				ObjectUuidPK pk = new ObjectUuidPK(ContractBillReceiveInfo.getOrgUnit().getId());
//				param = ParamControlFactory.getRemoteInstance().getParamValue(pk, "FDC228_ISSTRICTCONTROL");
//				if (!com.kingdee.util.StringUtils.isEmpty(param)) {
//					if(pc!=null&&pc.getId().toString().equals(ContractBillReceiveInfo.getProgrammingContract().getId().toString())){
//						return;
//					}
//					int i = Integer.parseInt(param);
//					switch (i) {
//					case 0:
//						// �ϸ����ʱ
//						if (ContractBillReceiveInfo.getProgrammingContract() != null) {
//							if (ContractBillReceiveInfo.getAmount().compareTo(FDCHelper.toBigDecimal(FDCHelper.toBigDecimal(ContractBillReceiveInfo.getProgrammingContract().getBalance()))) > 0) {
//								FDCMsgBox.showWarning(this, "��ͬǩԼ���������ĺ�Լ������");
//								SysUtil.abort();
//							}
//						} else {
//							FDCMsgBox.showWarning(this, "δ������ܺ�Լ��");
//							SysUtil.abort();
//						}
//						break;
//					case 1:
//						// ��ʾ����ʱ
//						if (ContractBillReceiveInfo.getProgrammingContract() != null) {
//							if (ContractBillReceiveInfo.getAmount().compareTo(FDCHelper.toBigDecimal(ContractBillReceiveInfo.getProgrammingContract().getBalance())) > 0) {
//								if (FDCMsgBox.showConfirm2(this, "��ͬǩԼ���������ĺ�Լ��������ȷ���Ƿ������") == FDCMsgBox.CANCEL) {
//									SysUtil.abort();
//								}
//							}
//						} else {
//							FDCMsgBox.showWarning(this, "δ������ܺ�Լ��");
//							SysUtil.abort();
//						}
//						break;
//					case 2:
//						// ������ʱ
//						if (ContractBillReceiveInfo.getProgrammingContract() != null) {
//							if (ContractBillReceiveInfo.getAmount().compareTo(
//									FDCHelper.toBigDecimal(ContractBillReceiveInfo.getProgrammingContract().getBalance())) > 0) {
//								if (FDCMsgBox.showConfirm2(this, "��ͬǩԼ���������ĺ�Լ��������ȷ���Ƿ������") == FDCMsgBox.CANCEL) {
//									SysUtil.abort();
//								}
//							}
//						}
//						break;
//					}
//					ContractBillReceiveFactory.getRemoteInstance().synReUpdateProgramming(ContractBillReceiveInfo.getId().toString(), pc);
//					SelectorItemCollection sicz = new SelectorItemCollection();
//					sicz.add("programmingContract");
//					ContractBillReceiveFactory.getRemoteInstance().updatePartial(ContractBillReceiveInfo, sicz);
//					ContractBillReceiveFactory.getRemoteInstance().synUpdateProgramming(ContractBillReceiveInfo.getId().toString(), ContractBillReceiveInfo.getProgrammingContract());
//					FDCMsgBox.showInfo("������ܺ�Լ�ɹ���");
//					this.refresh(null);
//				}
//			}
//		}
	}
	public void actionUnProgram_actionPerformed(ActionEvent e) throws Exception {
//		checkSelected();
//		List idList =ContractClientUtils.getSelectedIdValues(getBillListTable(), getKeyFieldName());
//		if(idList.size()>1){
//			FDCMsgBox.showWarning("��ѡ��һ����¼���в�����");
//			SysUtil.abort();
//		}
//		String selectedKeyValue = getSelectedKeyValue();
////		if (FDCUtils.isRunningWorkflow(selectedKeyValue)) {
////			FDCMsgBox.showWarning(this,"���ڹ����������У����Ժ����ԣ�");
////			SysUtil.abort();
////		}
//		SelectorItemCollection sic = new SelectorItemCollection();
//		sic.add("contractPropert");
//		sic.add("programmingContract.*");
//		sic.add("curProject.isWholeAgeStage");
//		ContractBillReceiveInfo ContractBillReceiveInfo = ContractBillReceiveFactory.getRemoteInstance().getContractBillReceiveInfo(new ObjectUuidPK(selectedKeyValue), sic);
//		if(ContractBillReceiveInfo.getCurProject().isIsWholeAgeStage()){
//			FDCMsgBox.showWarning("����п��ں�ͬ��Լ�滮���ɾ��������");
//			SysUtil.abort();
//		}
//		if(ContractBillReceiveInfo.getProgrammingContract()==null){
//			FDCMsgBox.showWarning(this,"��ѡ���������Լ�滮��¼����ȡ��������");
//			SysUtil.abort();
//		}
//		if(ContractBillReceiveInfo.getContractPropert().equals(ContractPropertyEnum.SUPPLY)){
//			FDCMsgBox.showWarning(this,"��ѡ������ͬ����ȡ��������");
//			SysUtil.abort();
//		}else{
//			ContractBillReceiveFactory.getRemoteInstance().synReUpdateProgramming(ContractBillReceiveInfo.getId().toString(), ContractBillReceiveInfo.getProgrammingContract());
//			SelectorItemCollection sel=new SelectorItemCollection();
//			sel.add("programmingContract");
//			ContractBillReceiveInfo.setProgrammingContract(null);
//			ContractBillReceiveFactory.getRemoteInstance().updatePartial(ContractBillReceiveInfo, sel);
//			FDCMsgBox.showInfo(this,"�����ɹ���");
//			this.refresh(null);
//		}
	}
	private File chooseFileByDialog() {
		File retFile = null;
		int retVal;
		KDFileChooser fc = new KDFileChooser(System.getProperty("user.home"));
		fc.setFileSelectionMode(KDFileChooser.FILES_ONLY);
		fc.setMultiSelectionEnabled(false);

		retVal = fc.showOpenDialog(this);
		if (retVal == KDFileChooser.CANCEL_OPTION)
			return retFile;
		retFile = fc.getSelectedFile();
		if (!retFile.exists()) {
			MsgBox.showInfo(Resrcs.getString("FileNotExisted"));
			return null;
		}
		if (retFile.length() > StringUtil4File.FILE_BYTES_LIMIT_SINGLE) {
			MsgBox.showInfo(Resrcs.getString("FileSizeNotAllowed"));
			return null;
		}
		return retFile;
	}

	protected void initWorkButton() {
		super.initWorkButton();
		/**
		 * �������������Ĳ˵�
		 */
		this.menuItemExportData.setVisible(true);
		this.menuItemExportData.setEnabled(true);
		
		this.menuItemImportData.setVisible(true);
		this.menuItemImportData.setEnabled(true);
		
//		this.btnAddContent.setIcon(EASResource.getIcon("imgTbtn_upenumnew"));
//		this.btnViewContent.setIcon(EASResource.getIcon("imgTbtn_seeperformance"));
//		this.btnProjectAttachment.setIcon(EASResource.getIcon("imgTbtn_createcredence"));
//		this.menuItemAddContent.setIcon(EASResource.getIcon("imgTbtn_upenumnew"));
//		this.menuItemViewContent.setIcon(EASResource.getIcon("imgTbtn_seeperformance"));
//		this.menuItemProjectAttachment.setIcon(EASResource.getIcon("imgTbtn_createcredence"));
//		this.btnConMove.setIcon(FDCClientHelper.ICON_MOVE);
//		this.menuItemConMove.setIcon(FDCClientHelper.ICON_MOVE);
//		
//		this.menuItemViewContract.setIcon(EASResource.getIcon("imgTbtn_execute"));
//		this.btnViewContract.setIcon(EASResource.getIcon("imgTbtn_execute"));
//		
//		actionRespite.setVisible(true);
//		actionRespite.setEnabled(true);
//		actionCancelRespite.setVisible(true);
//		actionCancelRespite.setEnabled(true);
	}

	protected FilterInfo getTreeSelectChangeFilter() {

		FilterInfo filter = new FilterInfo();
		FilterItemCollection filterItems = filter.getFilterItems();
		filterItems.add(new FilterItemInfo("contractType.isEnabled", Boolean.TRUE));
		filterItems.add(new FilterItemInfo("curProject.isEnabled", Boolean.TRUE));
		return filter;
	}

	/**
	 * output actionAddNew_actionPerformed
	 */
	public void actionAddNew_actionPerformed(ActionEvent e) throws Exception {
//		this.setOprtState("addnew");
//		int rowIndex = this.tblMain.getSelectManager().get().getBeginRow();
//		String id = this.tblMain.getCell(rowIndex,"id").getValue().toString().trim();
//		try {
//			pubFireVOChangeListener(id);
//		} catch (Throwable e1) {
//			// TODO �Զ����� catch ��
//			e1.printStackTrace();
//		}
		FDCClientUtils.checkSelectProj(this, getProjSelectedTreeNode());
		FDCClientUtils.checkProjWithCostOrg(this, getProjSelectedTreeNode());
		//�����������ж����˺�ͬ����,�������Ǳ���ѡ���ͬ���͵���ϸ�ڵ�
//		ContractClientUtils.checkCodingRuleForContract(this, getTypeSelectedTreeNode());

		DefaultKingdeeTreeNode node=getProjSelectedTreeNode();
		if (node.getUserObject() instanceof CurProjectInfo) {
			if(((CurProjectInfo)node.getUserObject()).isIsWholeAgeStage()){
				FDCMsgBox.showInfo(this, "��ѡ����ǿ��ں�ͬ��������ע�⣡");
			}
		}
		super.actionAddNew_actionPerformed(e);
	}

	
	/**
	 * ����
	 * @param ids
	 * @throws Exception
	 */
	protected void cancel(List ids) throws Exception {
		IObjectPK[] pkArray = FDCHelper.idListToPKArray(ids);
		ContractBillReceiveFactory.getRemoteInstance().cancel(pkArray);
	}

	/**
	 * ��Ч
	 * @param ids
	 * @throws Exception
	 */
	protected void cancelCancel(List ids) throws Exception {
		IObjectPK[] pkArray = FDCHelper.idListToPKArray(ids);

		ContractBillReceiveFactory.getRemoteInstance().cancelCancel(pkArray);
	}
	
	public void actionConMove_actionPerformed(ActionEvent e) throws Exception {
//		super.actionConMove_actionPerformed(e);
		
		checkSelected();
		checkBillState(new String[]{FDCBillStateEnum.AUDITTED_VALUE}, "selectRightRowForConMove");
		
		if(ContractMoveEditUI.showMe(getSelectedKeyValue(), this)) {
			refreshList();
		}
		
	}
	protected String[] getBillStateForEditOrRemove() {
    	return new String[]{FDCBillStateEnum.SAVED_VALUE, FDCBillStateEnum.SUBMITTED_VALUE};
    }
    /**
     * 
     * �������޸�ǰ���
     * @author:liupd
     * ����ʱ�䣺2006-8-26 <p>
     * @throws Exception 
     */
    protected void checkBeforeEdit() throws Exception {
	    checkSelected();
		
		//CoreBillBaseInfo billInfo = getRemoteInterface().getCoreBillBaseInfo(new ObjectUuidPK(getSelectedKeyValue(getBillListTable())));
		String selectedKeyValue = getSelectedKeyValue();
		SelectorItemCollection selector = new SelectorItemCollection();
		selector.add(getBillStatePropertyName());
		selector.add("splitState");
		ContractBillReceiveInfo ContractBillReceiveInfo = ContractBillReceiveFactory.getRemoteInstance().getContractBillReceiveInfo(new ObjectStringPK(selectedKeyValue), selector);		
	    
	    String billState = ContractBillReceiveInfo.getString(getBillStatePropertyName());
		String[] states = getBillStateForEditOrRemove();
		boolean pass = false;
		for (int i = 0; i < states.length; i++) {
			if(billState.equals(states[i])) {
				pass = true;
			}
		}
		if(!pass) {
			MsgBox.showWarning(this, ContractClientUtils.getRes("cantEdit"));
			SysUtil.abort();
		}
		
		//�ú�ͬ�Ѿ������˲�֣����ܽ����޸�
//		if(ContractBillReceiveInfo.getSplitState()!=null && !CostSplitStateEnum.NOSPLIT.equals(ContractBillReceiveInfo.getSplitState())){
//			MsgBox.showWarning(this, "�ú�ͬ�Ѿ������˲�֣����ܽ����޸�");
//			SysUtil.abort();
//		}
    }
    
	protected void checkBeforeCancel() throws Exception {
		super.checkBeforeCancel();
		
		String selectedKeyValue = getSelectedKeyValue();
		SelectorItemCollection selector = new SelectorItemCollection();
		selector.add("hasSettled");
		selector.add("state");
		ContractBillReceiveInfo ContractBillReceiveInfo = ContractBillReceiveFactory.getRemoteInstance().getContractBillReceiveInfo(new ObjectStringPK(selectedKeyValue), selector);
		if(ContractBillReceiveInfo.getState()==FDCBillStateEnum.SUBMITTED || ContractBillReceiveInfo.getState()==FDCBillStateEnum.AUDITTING){
			//R101224-250�ύ״̬�Ŀ���ֱ����ֹ
		}else{
			//����״̬��ֻ�н����˲ſ�����ֹ
			if(ContractBillReceiveInfo.getState()==FDCBillStateEnum.AUDITTED&&!ContractBillReceiveInfo.isHasSettled()) {
				MsgBox.showWarning(this, ContractClientUtils.getRes("hasNotSettled"));
				SysUtil.abort();
			}
		}
		
	}
	

	//���ӣ����Զ��ύ�������еĺ�ͬ������ֹ��״̬
	protected String[] getBillStateEnum() {
    	return new String[]{FDCBillStateEnum.AUDITTED_VALUE,FDCBillStateEnum.SUBMITTED_VALUE,FDCBillStateEnum.AUDITTING_VALUE};
	}
	

	// ������Լ�鿴����
	public void actionHeYue_actionPerformed(ActionEvent e) throws Exception {
		BOSUIContext uiCtx = new BOSUIContext();

		IUIWindow uiWindow = null;
		uiCtx.put(UIContext.ID, getSelectedKeyValue());
		// com.kingdee.eas.fdc.contract.client.HeYueDanEditUI
		uiWindow = UIFactory.createUIFactory(UIFactoryName.MODEL).create(HeYueDanEditUI.class.getName(), uiCtx, null, OprtState.VIEW);
		uiWindow.show();
//		super.actionHeYue_actionPerformed(e);
	}
	

	//���ò�ѯ��
	protected IQueryExecutor getQueryExecutor(IMetaDataPK queryPK, EntityViewInfo viewInfo) {
		
		IQueryExecutor queryExecutor = super.getQueryExecutor(queryPK, viewInfo);
		
		queryExecutor.option().isAutoIgnoreZero = false;
		
		return queryExecutor;
	}
	
	//ִ�в�ѯ
	protected void execQuery() {

		super.execQuery();
		//��ʽ�����Ľ�
		FDCClientUtils.fmtFootNumber(tblMain, new String[]{"originalAmount","amount"});
	}

	//�����Tableǰ��������
    public void onGetRowSet(IRowSet rowSet) {

    	super.onGetRowSet(rowSet);
		try {
			rowSet.beforeFirst();

			Set contractIds = new HashSet() ;
			while (rowSet.next()) {
				String id  = rowSet.getString("id");
				contractIds.add(id);					
			}
			Map retValue = ContractBillReceiveFactory.getRemoteInstance().getOtherInfo(contractIds);
	    	contentMap = (Map)retValue.get("contentMap");
	    	attachMap = (Map) retValue.get("attachMap");
//	    	auditMap = (Map) retValue.get("auditMap");
		}catch(Exception e){
			this.handUIException(e);
		}finally{
			try {
				rowSet.beforeFirst();
			} catch (SQLException e) {
				this.handUIException(e);
			}
		}
/*    	��getOtherInfo�ڽ���ͳһ���� by sxhong 2009-05-07 15:24:37
    	contentMap = (Map) ActionCache.get("ContractBillListUIHandler.contentMap");
    	attachMap = (Map) ActionCache.get("ContractBillListUIHandler.attachMap");
    	auditMap = (Map) ActionCache.get("ContractBillListUIHandler.auditMap");
    	
    	if(contentMap==null || attachMap==null){
    		contentMap = new HashMap();
    		attachMap = new HashMap();
    		
			try {
				rowSet.beforeFirst();
	
				Set contractIds = new HashSet() ;
				while (rowSet.next()) {
					String id  = rowSet.getString("id");
					contractIds.add(id);					
				}
				
				auditMap = FDCBillWFFacadeFactory.getRemoteInstance().getWFBillLastAuditorAndTime(contractIds);
				
				if(contractIds.size()>0){
					*//**
					 * ���������,���б��л�ȡ��ͬ��ID�����е�ID���Ǻ�ͬ�����ID��
					 * Ȼ��ͨ�����ù�����Ϣ��ʹ��Զ�̵��ã��鿴��ͬ���ĸ����������Ƿ��к�ͬ��ID.
					 * ����ú�ͬ�����ĸ����ͽ��б�ǣ����򲻱��
					 *//*
					EntityViewInfo viewContent = new EntityViewInfo();
					FilterInfo filterContent = new FilterInfo();
					viewContent.getSelector().clear();
					viewContent.getSelector().add("contract.id");
					filterContent.getFilterItems().add(new FilterItemInfo("contract.id", contractIds, CompareType.INCLUDE));
					viewContent.setFilter(filterContent);
					ContractContentCollection colContent = ContractContentFactory.getRemoteInstance().getContractContentCollection(viewContent);				
					for(int j = 0; j < colContent.size(); ++j){
						contentMap.put(colContent.get(j).getContract().getId().toString(),Boolean.TRUE);
					}						
					
					*//**
					 * ��丽����,���б��л�ȡ��ͬ��ID�����е�ID���Ǻ�ͬ�����ID��
					 * Ȼ��ͨ�����ù�����Ϣ��ʹ��Զ�̵��ã��鿴��ͬ���ĸ����������Ƿ��к�ͬ��ID.
					 * ����ú�ͬ��ҵ����ظ����ͽ��б�ǣ����򲻱��
					 *//*
					EntityViewInfo viewAttachment = new EntityViewInfo();
					FilterInfo filterAttachment = new FilterInfo();
					viewContent.getSelector().clear();
					viewAttachment.getSelector().add("boID");
					filterAttachment.getFilterItems().add(new FilterItemInfo("boID", contractIds, CompareType.INCLUDE));
					viewAttachment.setFilter(filterAttachment);
					BoAttchAssoCollection colAttachment = BoAttchAssoFactory.getRemoteInstance().getBoAttchAssoCollection(viewAttachment);
						
					for(int j = 0; j < colAttachment.size(); ++j){
						attachMap.put(colAttachment.get(j).getBoID().toString(),Boolean.TRUE);
					}			
					//���������afterDataFill
				}
				rowSet.beforeFirst();
			} catch (Exception e) {
				handUIException(e);
			}
    	}*/
    	
    }
    
	/**
     * �����Ƿ���ʾ�ϼ���
     * 2005-03-09 haiti_yang
     */
    protected boolean isFootVisible()
    {
        return true;
    }

	protected void fetchInitData() throws Exception {
		
		super.fetchInitData();
		// �ɱ����ļ�����
		Map paramMap = FDCUtils.getDefaultFDCParam(null, orgUnit.getId().toString());
		if(paramMap.get(FDCConstants.FDC_PARAM_UPDATECONTRACTNO)!=null){
			//�뼯��һ��ֵ
			isUpdateConNo = Boolean.valueOf(paramMap.get(FDCConstants.FDC_PARAM_UPDATECONTRACTNO).toString()).booleanValue();
		}
		if(paramMap.get(FDCConstants.FDC_PARAM_UPLOADAUDITEDBILL)!=null){
			canUploadForAudited = Boolean.valueOf(paramMap.get(FDCConstants.FDC_PARAM_UPLOADAUDITEDBILL).toString()).booleanValue();
		}
		if(paramMap.get(FDCConstants.FDC_PARAM_SELECTSUPPLY)!=null){
			isSupply = Boolean.valueOf(paramMap.get(FDCConstants.FDC_PARAM_SELECTSUPPLY).toString()).booleanValue();
		}
	}
	
	protected SelectorItemCollection genBillQuerySelector() {
		return null;
	}
	
	
	/**
	 * RPC���죬�κ�һ���¼�ֻ��һ��RPC
	 */
	
	public boolean isPrepareInit() {
    	return true;
    }
	
	public boolean isPrepareActionSubmit() {
    	return true;
    }
	
	public boolean isPrepareActionSave() {
    	return true;
    }
		
    /**
     * EditUI�ṩ�ĳ�ʼ��handler��������
     */
    protected RequestContext PrepareHandlerParam(RequestContext request)
    {
        return super.PrepareHandlerParam(request);
    }
	public void actionImportData_actionPerformed(ActionEvent e) throws Exception{
//		FDCClientUtils.checkSelectProj(this, getProjSelectedTreeNode());
//		FDCClientUtils.checkProjWithCostOrg(this, getProjSelectedTreeNode());
		DatataskCaller task = new DatataskCaller();
        task.setParentComponent(this);
        if (getImportParam() != null)
        {
            task.invoke(getImportParam(), DatataskMode.ImpMode,true,true);
        }
        actionRefresh_actionPerformed(e);
		
	}
	protected ArrayList getImportParam()
    {	
		
		DatataskParameter param = new DatataskParameter();
		Hashtable hs = new Hashtable();
		param.setContextParam(hs);//
        param.solutionName = getSolutionName();      
        param.alias = getDatataskAlias();
        ArrayList paramList = new ArrayList();
        paramList.add(param);
        return paramList;
    }
	protected String getSolutionName(){
    	//return "eas.fdc.contract.fdccontractbill";
		return "eas.fdc.contract.ContractBillImport";
    }
    
    protected String getDatataskAlias(){
    	return "��ͬ";
    } 
	public void actionEditPerson_actionPerformed(ActionEvent e) throws Exception {
		checkSelected();
		UIContext uiContext = new UIContext(this);
		uiContext.put("list", ContractClientUtils.getSelectedIdValues(getBillListTable(), getKeyFieldName()));
		IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.MODEL).create(ContractBillEditPersonUI.class.getName(), uiContext, null, OprtState.VIEW);
		uiWindow.show();
		
		this.refresh(null);
	}
	public void actionEditAuditDate_actionPerformed(ActionEvent e)throws Exception {
		checkSelected();
		UIContext uiContext = new UIContext(this);
		uiContext.put("list", ContractClientUtils.getSelectedIdValues(getBillListTable(), getKeyFieldName()));
		IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.MODEL).create(ContractBillEditAuditDateUI.class.getName(), uiContext, null, OprtState.VIEW);
		uiWindow.show();
		
		this.refresh(null);
	}
	public void actionEditContractType_actionPerformed(ActionEvent e)throws Exception {
		checkSelected();
		UIContext uiContext = new UIContext(this);
		uiContext.put("list", ContractClientUtils.getSelectedIdValues(getBillListTable(), getKeyFieldName()));
		IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.MODEL).create(ContractBillEditContractTypeUI.class.getName(), uiContext, null, OprtState.VIEW);
		uiWindow.show();
		
		this.refresh(null);
		
	}
	public void actionWorkFlowG_actionPerformed(ActionEvent e) throws Exception {
		checkSelected();
		int rowIndex = this.tblMain.getSelectManager().getActiveRowIndex();
		IRow row = this.tblMain.getRow(rowIndex);
    	String id = (String) row.getCell("id").getValue();
    	ContractBillReceiveInfo info=ContractBillReceiveFactory.getRemoteInstance().getContractBillReceiveInfo(new ObjectUuidPK(id));
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
    	ContractBillReceiveInfo info=ContractBillReceiveFactory.getRemoteInstance().getContractBillReceiveInfo(new ObjectUuidPK(id));
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
	
	protected FilterInfo getDefaultFilterForTree() {
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(
				new FilterItemInfo("isEnabled", Boolean.TRUE));
		filter.getFilterItems().add(
				new FilterItemInfo("isReceive", Boolean.TRUE));
		return filter;
	}

	@Override
	public void actionContract_actionPerformed(ActionEvent e) throws Exception {
		// TODO Auto-generated method stub
		super.actionContract_actionPerformed(e);
		checkSelected();
		List idList =ContractClientUtils.getSelectedIdValues(getBillListTable(), getKeyFieldName());
		if(idList.size()>1){
			FDCMsgBox.showWarning("��ѡ��һ����¼���в�����");
			SysUtil.abort();
		}
		String selectedKeyValue = getSelectedKeyValue();
//		if (FDCUtils.isRunningWorkflow(selectedKeyValue)) {
//			FDCMsgBox.showWarning("���ڹ����������У����Ժ����ԣ�");
//			SysUtil.abort();
//		}
		SelectorItemCollection sic = new SelectorItemCollection();
		sic.add("*");
		sic.add("curProject.*");
		sic.add("contractBill.*");
		ContractBillReceiveInfo contractBillReceiveInfo = ContractBillReceiveFactory.getRemoteInstance().getContractBillReceiveInfo(new ObjectUuidPK(selectedKeyValue), sic);
		
		IUIWindow uiWindow = null;
		UIContext uiContext = new UIContext(this);
		uiContext.put("contractBillReceiveInfo", contractBillReceiveInfo);
		uiWindow = UIFactory.createUIFactory(UIFactoryName.MODEL).create(ContractBillReceiveLinkContractBillEditUI.class.getName(), uiContext, null,OprtState.EDIT);
		uiWindow.show();
		Object object = uiWindow.getUIObject().getUIContext().get("ok");
		if(object!=null){
			SelectorItemCollection sicz = new SelectorItemCollection();
			sicz.add("contractBill");
			ContractBillReceiveFactory.getRemoteInstance().updatePartial(contractBillReceiveInfo, sicz);
			FDCMsgBox.showInfo("������ͬ�ɹ���");
			this.refresh(null);
		}
	}
	
}