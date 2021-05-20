/**
 * output package name
 */
package com.kingdee.eas.fdc.tenancy.client;

import java.awt.event.ActionEvent;
import java.math.BigDecimal;

import javax.swing.tree.TreeNode;

import org.apache.log4j.Logger;

import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.dao.query.IQueryExecutor;
import com.kingdee.bos.metadata.IMetaDataPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.resource.BizEnumValueInfo;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.bos.workflow.ProcessInstInfo;
import com.kingdee.bos.workflow.service.ormrpc.EnactmentServiceFactory;
import com.kingdee.bos.workflow.service.ormrpc.IEnactmentService;
import com.kingdee.eas.basedata.org.SaleOrgUnitInfo;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.MoneySysTypeEnum;
import com.kingdee.eas.fdc.basedata.client.FDCClientHelper;
import com.kingdee.eas.fdc.basedata.client.FDCClientUtils;
import com.kingdee.eas.fdc.sellhouse.RoomInfo;
import com.kingdee.eas.fdc.sellhouse.SellProjectInfo;
import com.kingdee.eas.fdc.sellhouse.client.SHEHelper;
import com.kingdee.eas.fdc.tenancy.BuildigRentEntrysCollection;
import com.kingdee.eas.fdc.tenancy.BuildigRentEntrysInfo;
import com.kingdee.eas.fdc.tenancy.BuildingRoomEntrysCollection;
import com.kingdee.eas.fdc.tenancy.BuildingRoomEntrysInfo;
import com.kingdee.eas.fdc.tenancy.TenancyRentBillFactory;
import com.kingdee.eas.fdc.tenancy.TenancyRentBillInfo;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.util.client.MsgBox;

/**
 * output class name
 */
public class TenancyRentBillListUI extends AbstractTenancyRentBillListUI
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 2602893629149299728L;
	private static final Logger logger = CoreUIObject.getLogger(TenancyRentBillListUI.class);
	//��֯��Ԫ
	SaleOrgUnitInfo saleOrg = SHEHelper.getCurrentSaleOrg();

	public TenancyRentBillListUI() throws Exception
	{
		super();
	}

	protected String getEditUIModal() {
		return UIFactoryName.NEWTAB;
	}

	protected String getEditUIName() {
		return TenancyRentBillEditUI.class.getName();
	}

	public void onLoad() throws Exception {
		FDCClientHelper.addSqlMenu(this, this.menuEdit);
		super.onLoad();
		initTree();
		this.treeMain.setSelectionRow(0);
		this.menuItemCreateTo.setVisible(false);
		this.actionCreateTo.setVisible(false);
		this.menuItemCopyTo.setVisible(false);
		this.menuItemTraceUp.setVisible(false);
		this.menuItemTraceDown.setVisible(false);
		this.actionTraceUp.setVisible(false);
		this.actionTraceDown.setVisible(false);
		this.btnUnAudit.setVisible(false);
		this.btnExecute.setEnabled(true);
		this.menuItemExecut.setEnabled(true);

		this.MenuItemAttachment.setVisible(false);
		this.actionAttachment.setVisible(false);
		this.actionAuditResult.setVisible(false);
		//by tim_gao 
		this.btnAudit.setVisible(true);
		this.btnAudit.setEnabled(true);
		this.actionAudit.setEnabled(true);
		this.btnUnAudit.setVisible(true);
		this.btnUnAudit.setEnabled(true);
		this.actionUnAudit.setEnabled(true);

	}

	protected void initTree() throws Exception {
		this.treeMain.setModel(SHEHelper.getSellProjectTree(this.actionOnLoad, MoneySysTypeEnum.TenancySys));
		this.treeMain.expandAllNodes(true, (TreeNode) this.treeMain.getModel().getRoot());
	}

	protected void prepareUIContext(UIContext uiContext, ActionEvent e) {
		DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeMain.getLastSelectedPathComponent();
		if (node == null) {
			return;
		}
		if (node.getUserObject() instanceof String) {
			MsgBox.showInfo("��ѡ�����������Ŀ��");
			this.abort();
		}
		if (node.getUserObject() instanceof SellProjectInfo) {
			SellProjectInfo sellProject = (SellProjectInfo) node.getUserObject();
			uiContext.put("sellProject", sellProject);
		}
		super.prepareUIContext(uiContext, e);
	}

	protected ICoreBase getBizInterface() throws Exception {
		return TenancyRentBillFactory.getRemoteInstance();
	}

	protected String getLongNumberFieldName() {
		return "number";
	}

	protected void treeMain_valueChanged(javax.swing.event.TreeSelectionEvent e) throws Exception {
		
		DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeMain
		.getLastSelectedPathComponent();
		if (node == null) {
			return;
		}

		if (node.getUserObject() instanceof SellProjectInfo  &&  this.saleOrg.isIsBizUnit()) {
			this.actionAddNew.setEnabled(true);
		} else {
			this.actionAddNew.setEnabled(false);
		}
		this.execQuery();
	}

	/**
	 *��ѯ��������
	 * */
	protected IQueryExecutor getQueryExecutor(IMetaDataPK queryPK, EntityViewInfo viewInfo) {
		try {
			DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeMain.getLastSelectedPathComponent();
			viewInfo = (EntityViewInfo) this.mainQuery.clone();

			FilterInfo filter = new FilterInfo();
			if (node.getUserObject() instanceof SellProjectInfo) {
				SellProjectInfo pro = (SellProjectInfo) node.getUserObject();
				filter.getFilterItems().add(new FilterItemInfo("project.id", pro.getId().toString()));
			} else {
				filter.getFilterItems().add(new FilterItemInfo("id", null));
			}

			if (viewInfo.getFilter() != null) {
				viewInfo.getFilter().mergeFilter(filter, "and");
			} else {
				viewInfo.setFilter(filter);
			}
		} catch (Exception e) {
			this.handleException(e);
		}
		
		return super.getQueryExecutor(queryPK, viewInfo);
	}

	/**
	 * ���ⵥɾ���¼�
	 * */
	public void actionRemove_actionPerformed(ActionEvent e) throws Exception {
		this.checkSelected();
		int rowIndex = this.tblMain.getSelectManager().getActiveRowIndex();
		IRow row = this.tblMain.getRow(rowIndex);
		BizEnumValueInfo state = (BizEnumValueInfo) row.getCell("state")
		.getValue();
		Boolean flag = (Boolean)row.getCell("isExecuted").getValue();
		
		if (flag.booleanValue()) {
			MsgBox.showInfo("���ⵥ�Ѿ�ִ��!");
			return;
		}
		if (row.getCell("state") != null&&(FDCBillStateEnum.SUBMITTED_VALUE.equals(state.getValue())||
				FDCBillStateEnum.SAVED_VALUE.equals(state.getValue())||
				FDCBillStateEnum.INVALID_VALUE.equals(state.getValue()))){
			
			FDCClientUtils.checkBillInWorkflow(this, getSelectedKeyValue());//��鵥���Ƿ��ڹ�������
			super.actionRemove_actionPerformed(e);
		
		}else{
			MsgBox.showInfo("δ��˻������ϵĶ��ⵥ�ſ�ɾ��!");
			return;
		}
	}

	/**
	 * ���ⵥ����¼�
	 * */
	public void actionAudit_actionPerformed(ActionEvent e) throws Exception {
		super.actionAudit_actionPerformed(e);
		int rowIndex = this.tblMain.getSelectManager().getActiveRowIndex();
		if (rowIndex == -1) {
			MsgBox.showInfo("��ѡ����!");
			return;
		}
		IRow row = this.tblMain.getRow(rowIndex);
		BizEnumValueInfo state = (BizEnumValueInfo) row.getCell("state")
		.getValue();
		if (FDCBillStateEnum.SAVED_VALUE.equals(state.getValue())) {
			MsgBox.showInfo("���ⵥû���ύ!");
			return;
		}
		if (FDCBillStateEnum.AUDITTED_VALUE.equals(state.getValue())) {
			MsgBox.showInfo("���ⵥ�Ѿ����!");
			return;
		}
		if (FDCBillStateEnum.INVALID_VALUE.equals(state.getValue())) {
			MsgBox.showInfo("���ⵥ�Ѿ�����!");
			return;
		}
		String id = (String) row.getCell(this.getKeyFieldName()).getValue();
		TenancyRentBillFactory.getRemoteInstance().audit(BOSUuid.read(id));
		this.refresh(null);
		// by tim_gao 
		verifyAuditState();
	}

	/**
	 * ���ⵥ������¼�
	 * */
	public void actionUnAudit_actionPerformed(ActionEvent e) throws Exception {
	 super.actionUnAudit_actionPerformed(e);
	 int rowIndex = this.tblMain.getSelectManager().getActiveRowIndex();
		if (rowIndex == -1) {
			MsgBox.showInfo("��ѡ����!");
			return;
		}
		IRow row = this.tblMain.getRow(rowIndex);
		BizEnumValueInfo state = (BizEnumValueInfo) row.getCell("state")
		.getValue();
		Boolean flag = (Boolean)row.getCell("isExecuted")
		.getValue();
		if (!FDCBillStateEnum.AUDITTED_VALUE.equals(state.getValue())) {
			MsgBox.showInfo("���ⵥ�������״̬!");
			return;
		}
		if (FDCBillStateEnum.INVALID_VALUE.equals(state.getValue())) {
			MsgBox.showInfo("���ⵥ�Ѿ�����!");
			return;
		}
		if (flag.booleanValue()) {
			MsgBox.showInfo("���ⵥ�Ѿ�ִ��!");
			return;
		}
		String id = (String) row.getCell(this.getKeyFieldName()).getValue();
		TenancyRentBillFactory.getRemoteInstance().unAudit(BOSUuid.read(id));
		this.refresh(null);
		verifyAuditState();
	}

	/**
	 * ���ⵥִ���¼�
	 * */
	public void actionExecute_actionPerformed(ActionEvent e) throws Exception {
		super.actionExecute_actionPerformed(e);
		int rowIndex = this.tblMain.getSelectManager().getActiveRowIndex();
		if (rowIndex == -1) {
			MsgBox.showInfo("��ѡ����!");
			return;
		}
		IRow row = this.tblMain.getRow(rowIndex);

		Boolean isExecuted = (Boolean) row.getCell("isExecuted").getValue();
		if (isExecuted.booleanValue()) {
			MsgBox.showInfo("���ⵥ�Ѿ�ִ��!");
			return;
		}
		String auditorName = (String) row.getCell("auditor.name").getValue();
		if (auditorName == null) {
			MsgBox.showInfo("���ⵥû�����!");
			return;
		}
		BizEnumValueInfo tenancyState = (BizEnumValueInfo) row.getCell(
		"state").getValue();
		if(FDCBillStateEnum.INVALID_VALUE.equals(tenancyState.getValue()))
		{
			MsgBox.showInfo("���ⵥ�Ѿ�����!");
			return;
		}
		if (MsgBox.showConfirm2New(this, "�Ƿ�ִ��?") == MsgBox.YES) {
			String id = (String) row.getCell(this.getKeyFieldName()).getValue();	
			TenancyRentBillInfo tenBill = TenancyClientHelper.getBuildingRentEntrys(id);
			//����¥����¼
			BuildigRentEntrysCollection buildingRentEntrysColl = tenBill.getBuildingEntrys();
			//���ڼ�¼�붨��ʱ���������ͬ�ķ������ƣ�������ʾ
			StringBuffer stringName = new StringBuffer();
			if(buildingRentEntrysColl.size()>0)
			{
				for(int i=0;i<buildingRentEntrysColl.size();i++)
				{
					BuildigRentEntrysInfo buildingRentInfo = buildingRentEntrysColl.get(i);
					//¥�������¼
					BuildingRoomEntrysCollection entrys = TenancyClientHelper.getBuildingRoomEntrys(buildingRentInfo);
					for (int j = 0; j < entrys.size(); j++) {

						BuildingRoomEntrysInfo roomEntrysInfo = entrys.get(j);
						RoomInfo room = roomEntrysInfo.getRooms();
						BigDecimal actualBuildingArea = FDCHelper.ZERO;
						BigDecimal actualRoomArea = FDCHelper.ZERO;
						if(room.isIsActualAreaAudited())
						{
							actualBuildingArea =  room.getActualBuildingArea();
							actualRoomArea =  room.getActualRoomArea();
							if(actualBuildingArea==null)
							{
								actualBuildingArea = FDCHelper.ZERO;
							}
							if(actualRoomArea== null)
							{
								actualRoomArea = FDCHelper.ZERO;
							}
						}else
						{
							actualBuildingArea = room.getBuildingArea();
							actualRoomArea = room.getRoomArea();
						}
						BigDecimal buildingArea = roomEntrysInfo.getBuildingArea();
						BigDecimal roomArea = roomEntrysInfo.getRoomArea();
						/**
						 * �����ִ��ʱ�򷿼�����Ͷ���ʱ�����������ͬ��˵�������ڶ�������з���ǰ���˻�����ʵ�⸴�˹�����ô
						 * ��������ı�ķ�����Ҫ���������ۣ�ִ��ʱҲ����Ѽ۸�д�뷿����
						 * */
						if(roomArea.compareTo(actualRoomArea)!=0 || buildingArea.compareTo(actualBuildingArea)!=0)
						{
							if(stringName==null ||stringName.length()==0)
							{
								stringName.append(room.getName());
							}else
							{
								stringName.append(","+room.getName());
							}			
						}
					}
				}
			}else
			{
				BuildingRoomEntrysCollection buildRoomEntryColl = tenBill.getRoomEntrys();
				for(int i=0;i<buildRoomEntryColl.size();i++)
				{
					BuildingRoomEntrysInfo roomEntrysInfo = buildRoomEntryColl.get(i);
					RoomInfo room = roomEntrysInfo.getRooms();
					BigDecimal actualBuildingArea = FDCHelper.ZERO;
					BigDecimal actualRoomArea = FDCHelper.ZERO;
					if(room.isIsActualAreaAudited())
					{
						actualBuildingArea =  room.getActualBuildingArea();
						actualRoomArea =  room.getActualRoomArea();
						if(actualBuildingArea==null)
						{
							actualBuildingArea = FDCHelper.ZERO;
						}
						if(actualRoomArea== null)
						{
							actualRoomArea = FDCHelper.ZERO;
						}
					}else
					{
						actualBuildingArea = room.getBuildingArea();
						actualRoomArea = room.getRoomArea();
					}
					BigDecimal buildingArea = roomEntrysInfo.getBuildingArea();
					BigDecimal roomArea = roomEntrysInfo.getRoomArea();
					if(buildingArea.compareTo(actualBuildingArea)!=0 || roomArea.compareTo(actualRoomArea)!=0)
					{
						if(stringName==null ||stringName.length()==0)
						{
							stringName.append(room.getName());
						}else
						{
							stringName.append(","+room.getName());
						}	
					}
				}
			}
			if(stringName.length()>0)
			{
				MsgBox.showInfo("����"+stringName.toString()+"���������仯,���Զ�����!��Ҫ���¶���!");
			}
			if (TenancyRentBillFactory.getRemoteInstance().execute(id)) {
				MsgBox.showInfo("ִ�гɹ�!");
				this.refresh(null);
			}

		}
	}

	/**
	 * ���ⵥ�����¼�
	 * */
	public void actionBlankOut_actionPerformed(ActionEvent e) throws Exception {
		int rowIndex = this.tblMain.getSelectManager().getActiveRowIndex();
		if (rowIndex == -1) {
			MsgBox.showInfo("��ѡ����!");
			return;
		}
		IRow row = this.tblMain.getRow(rowIndex);
		if(((Boolean)row.getCell("isExecuted").getValue()).booleanValue())
		{
			MsgBox.showInfo("���ⵥ��ִ�У���������!");
			return;
		}
		BizEnumValueInfo tenancyState = (BizEnumValueInfo) row.getCell(
		"state").getValue();
		if(FDCBillStateEnum.INVALID_VALUE.equals(tenancyState.getValue()))
		{
			MsgBox.showInfo("���ⵥ�Ѿ�����!");
			return;
		}
		String id = (String) row.getCell(this.getKeyFieldName()).getValue();
		if (MsgBox.showConfirm2New(this, "���Ϻ󲻿ɻָ�����ȷ���Ƿ�����?") == MsgBox.YES) {
			//����ʱ��Ҫ��״̬д�뵥���У�����ֹͣ��ǰ�������еĹ�����
			TenancyRentBillInfo tenancyInfo = TenancyRentBillFactory.getRemoteInstance()
			.getTenancyRentBillInfo(new ObjectUuidPK(BOSUuid.read(id)));
			tenancyInfo.setState(FDCBillStateEnum.INVALID);
			SelectorItemCollection sels = new SelectorItemCollection();
			sels.add("state");
			TenancyRentBillFactory.getRemoteInstance().updatePartial(tenancyInfo, sels);

			ProcessInstInfo instInfo = null;
			ProcessInstInfo[] procInsts = null;
			IEnactmentService service = EnactmentServiceFactory
			.createRemoteEnactService();
			procInsts = service.getProcessInstanceByHoldedObjectId(tenancyInfo.getId()
					.toString());
			for (int j = 0; j < procInsts.length; j++) {
				if ("open.running".equals(procInsts[j].getState())) {
					instInfo = procInsts[j];
					service.abortProcessInst(instInfo.getProcInstId());
				}
			}
			this.refresh(null);
		}
	}

	public void actionEdit_actionPerformed(ActionEvent arg0) throws Exception {
		int rowIndex = this.tblMain.getSelectManager().getActiveRowIndex();
		if (rowIndex == -1) {
			MsgBox.showInfo("��ѡ����!");
			return;
		}
		IRow row = this.tblMain.getRow(rowIndex);
		String auditorName = (String) row.getCell("auditor.name").getValue();
		if (auditorName != null) {
			MsgBox.showInfo("���ⵥ�Ѿ�����,�����޸�!");
			return;
		}
		FDCClientUtils.checkBillInWorkflow(this, getSelectedKeyValue());//��鵥���Ƿ��ڹ�������
		super.actionEdit_actionPerformed(arg0);
	}
	/**
	 * @author tim_gao
	 * @description ��˷�����ж�
	 * @date 2010-12-17
	 */
	protected void verifyAuditState(){
		int rowindex = this.tblMain.getSelectManager().getActiveRowIndex();
		
		if(rowindex <0){
			this.btnAudit.setVisible(true);
			this.actionAudit.setEnabled(true);
			this.actionAudit.setVisible(true);
			this.btnUnAudit.setVisible(true);
			this.actionUnAudit.setEnabled(true);
			this.actionUnAudit.setVisible(true);
		}else{
			IRow row =  this.tblMain.getRow(rowindex);
			BizEnumValueInfo state = (BizEnumValueInfo) row.getCell("state")
			.getValue();
			if(FDCBillStateEnum.AUDITTED_VALUE.equals(state.getValue())){
				this.btnAudit.setEnabled(false);
				this.actionAudit.setEnabled(false);
				
				this.btnUnAudit.setEnabled(true);
				this.actionUnAudit.setEnabled(true);
				
			}else if (FDCBillStateEnum.SUBMITTED_VALUE.equals(state.getValue())||FDCBillStateEnum.SAVED_VALUE.equals(state.getValue())){
				this.btnAudit.setEnabled(true);
				this.actionAudit.setEnabled(true);
				
				this.btnUnAudit.setEnabled(false);
				this.actionUnAudit.setEnabled(false);
			
			}	
			
		}
		
	
	}
	
	public void tblMain_tableClicked(com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e) throws Exception {
	      super.tblMain_tableClicked(e);
		//write your code here
	  	
			//by tim_gao ���״̬�ж�
			verifyAuditState();
	}
}