/**
 * output package name
 */
package com.kingdee.eas.fdc.sellhouse.client;

import java.awt.event.ActionEvent;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.swing.tree.TreeNode;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.table.ICell;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTableHelper;
import com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent;
import com.kingdee.bos.ctrl.kdf.table.event.KDTSelectEvent;
import com.kingdee.bos.ctrl.kdf.table.util.KDTableUtil;
import com.kingdee.bos.ctrl.swing.KDMenuItem;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.dao.query.IQueryExecutor;
import com.kingdee.bos.metadata.IMetaDataPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemCollection;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.ItemAction;
import com.kingdee.bos.ui.face.UIException;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.bos.workflow.ProcessInstInfo;
import com.kingdee.bos.workflow.monitor.client.BasicWorkFlowMonitorPanel;
import com.kingdee.bos.workflow.monitor.client.ProcessRunningListUI;
import com.kingdee.bos.workflow.service.ormrpc.EnactmentServiceFactory;
import com.kingdee.bos.workflow.service.ormrpc.IEnactmentService;
import com.kingdee.eas.base.multiapprove.IMultiApprove;
import com.kingdee.eas.base.multiapprove.MultiApproveFactory;
import com.kingdee.eas.base.multiapprove.client.MultiApproveUtil;
import com.kingdee.eas.base.uiframe.client.UIModelDialogFactory;
import com.kingdee.eas.basedata.org.SaleOrgUnitInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basecrm.RevBillTypeEnum;
import com.kingdee.eas.fdc.basecrm.RevBizTypeEnum;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.MoneySysTypeEnum;
import com.kingdee.eas.fdc.basedata.client.FDCClientHelper;
import com.kingdee.eas.fdc.basedata.client.FDCClientUtils;
import com.kingdee.eas.fdc.sellhouse.AreaCompensateRevListFactory;
import com.kingdee.eas.fdc.sellhouse.BuildingCompensateFactory;
import com.kingdee.eas.fdc.sellhouse.BuildingCompensateInfo;
import com.kingdee.eas.fdc.sellhouse.BuildingInfo;
import com.kingdee.eas.fdc.sellhouse.BuildingUnitInfo;
import com.kingdee.eas.fdc.sellhouse.CodingTypeEnum;
import com.kingdee.eas.fdc.sellhouse.IBuildingCompensate;
import com.kingdee.eas.fdc.sellhouse.IRoomAreaCompensate;
import com.kingdee.eas.fdc.sellhouse.PurchaseCollection;
import com.kingdee.eas.fdc.sellhouse.PurchaseFactory;
import com.kingdee.eas.fdc.sellhouse.PurchaseStateEnum;
import com.kingdee.eas.fdc.sellhouse.RoomAreaCompensate;
import com.kingdee.eas.fdc.sellhouse.RoomAreaCompensateCollection;
import com.kingdee.eas.fdc.sellhouse.RoomAreaCompensateFactory;
import com.kingdee.eas.fdc.sellhouse.RoomAreaCompensateInfo;
import com.kingdee.eas.fdc.sellhouse.RoomCollection;
import com.kingdee.eas.fdc.sellhouse.RoomCompensateStateEnum;
import com.kingdee.eas.fdc.sellhouse.RoomFactory;
import com.kingdee.eas.fdc.sellhouse.RoomInfo;
import com.kingdee.eas.fdc.sellhouse.RoomSellStateEnum;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.framework.ITreeBase;
import com.kingdee.eas.framework.client.FrameWorkClientUtils;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.MsgBox;
import com.kingdee.jdbc.rowset.IRowSet;
import com.kingdee.util.NumericExceptionSubItem;
import com.kingdee.util.StringUtils;
import com.kingdee.util.UuidException;

/**
 * output class name
 */
public class RoomAreaCompensateListUI extends AbstractRoomAreaCompensateListUI
{
	private static final Logger logger = CoreUIObject
			.getLogger(RoomAreaCompensateListUI.class);

	protected static final BOSUuid splitBillNullID = BOSUuid.create("null");

	SaleOrgUnitInfo saleOrg = SHEHelper.getCurrentSaleOrg();

	/**
	 * output class constructor
	 */
	public RoomAreaCompensateListUI() throws Exception
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

	protected ITreeBase getTreeInterface() throws Exception
	{
		return null;
	}

	protected String getEditUIName()
	{
		return RoomAreaCompensateEditUI.class.getName();
	}

	protected void treeMain_valueChanged(javax.swing.event.TreeSelectionEvent e)
			throws Exception
	{
		execQuery();
	}

	protected void initTree() throws Exception
	{
		this.treeMain.setModel(SHEHelper.getUnitTree(this.actionOnLoad,MoneySysTypeEnum.SalehouseSys));
		this.treeMain.expandAllNodes(true, (TreeNode) this.treeMain.getModel().getRoot());
	}
	
	private void hideToolButton()
	{
		this.actionTraceDown.setVisible(false);
		this.actionTraceUp.setVisible(false);
		this.actionAttachment.setVisible(false);
		
		this.actionCreateTo.setVisible(false);
		
		this.actionCopyTo.setVisible(false);
		this.actionAuditResult.setVisible(false);
		//this.actionAuditResult.setVisible(true);
	}

	
	
	public void onLoad() throws Exception
	{
		this.hideToolButton();
		
		FDCClientHelper.addSqlMenu(this, this.menuEdit);
		// ����Ϊfalse,���ִ��tHelper = new
		// TableListPreferencesHelper(this);���±�����ò��ܱ�����ʽ
		actionQuery.setEnabled(false);
		super.onLoad();
		actionQuery.setEnabled(true);
		FDCClientHelper.setActionEnable(new ItemAction[]{ actionExecute }, true);
		FDCClientHelper.setActionEnable(new ItemAction[]{ actionAddNew, actionCancel, actionCancelCancel }, false);
		if (!saleOrg.isIsBizUnit())		{
			FDCClientHelper.setActionEnableAndNotSetVisible(new ItemAction[]
			{ actionEdit, actionRemove, actionBatchCompensate, actionBatchEdit,
					actionSingleScheme, actionExecute, actionSubmitWorkFlow },		false);
		}
		kDMenu1.setIcon(EASResource.getIcon("imgTbtn_edit"));
		
		KDMenuItem item = null;

		item = new KDMenuItem();
		item.setAction(this.actionEdit);
		item.setText("�ֹ�����");
		item.setAccelerator(null);
		item.setIcon(EASResource.getIcon("imgTbtn_edit"));
		this.btnEdit.addAssistMenuItem(item);

		item = new KDMenuItem();
		item.setAction(this.actionSingleScheme);
		item.setText("��������");
		item.setAccelerator(null);
		item.setIcon(EASResource.getIcon("imgTbtn_edit"));
		this.btnEdit.addAssistMenuItem(item);

		item = new KDMenuItem();
		item.setAction(this.actionBatchEdit);
		item.setText("�����ֹ�����");
		item.setAccelerator(null);
		item.setIcon(EASResource.getIcon("imgTbtn_editbatch"));
		this.btnBatchCompensate.addAssistMenuItem(item);		
		
		item = new KDMenuItem();
		item.setAction(this.actionBatchCompensate);
		item.setText("������������");
		item.setAccelerator(null);
		item.setIcon(EASResource.getIcon("imgTbtn_editbatch"));
		this.btnBatchCompensate.addAssistMenuItem(item);

		initTree();
		
		this.treeMain.setSelectionRow(0);
		actionUnAudit.setEnabled(true);
		
	}

	protected void tblMain_tableClicked(KDTMouseEvent e) throws Exception {
		if(e.getButton()==1 && e.getClickCount()==2)  {
			if (e.getType() != 1)
				return;
			
			String selectIdStr = getSelectedKeyValue(); 
			if(selectIdStr!=null)
				openTheUIWindow(this, getEditUIName(), selectIdStr, OprtState.VIEW,"noscheme");
			else
				openTheUIWindow(this, getEditUIName(), null, OprtState.ADDNEW,"noscheme");
			this.tblMain.refresh();
		}
	}
	
	
	
	//�ֹ�����
	public void actionEdit_actionPerformed(ActionEvent e) throws Exception
	{
		checkSelected();
		checkSelectArea();
		
		String selectIdStr = getSelectedKeyValue(); 
		if(selectIdStr==null) {
			String roomIdStr = getSelectedRoomID();
			if(roomIdStr!=null) {
				RoomAreaCompensateCollection comColl =  RoomAreaCompensateFactory.getRemoteInstance()
						.getRoomAreaCompensateCollection("where room.id = '"+roomIdStr+"' order by createTime desc");
				if(comColl.size()>0) {
					selectIdStr = comColl.get(0).getId().toString();
					IRow selectRow = KDTableUtil.getSelectedRow(this.tblMain);
					if(selectRow!=null)
						selectRow.getCell("roomAreaCompensate.id").setValue(selectIdStr);
				}
			}			
		}
		openTheUIWindow(this, getEditUIName(), selectIdStr, OprtState.EDIT,"noscheme");
		this.tblMain.refresh();
	}

	
	//��������
	public void actionSingleScheme_actionPerformed(ActionEvent e)
			throws Exception
	{
		checkSelected();
		checkSelectArea();
		
		String selectIdStr = getSelectedKeyValue(); 
		if(selectIdStr==null) {
			String roomIdStr = getSelectedRoomID();
			if(roomIdStr!=null) {
				RoomAreaCompensateCollection comColl =  RoomAreaCompensateFactory.getRemoteInstance()
						.getRoomAreaCompensateCollection("where room.id = '"+roomIdStr+"' order by createTime desc");
				if(comColl.size()>0) {
					selectIdStr = comColl.get(0).getId().toString();
					IRow selectRow = KDTableUtil.getSelectedRow(this.tblMain);
					if(selectRow!=null)
						selectRow.getCell("roomAreaCompensate.id").setValue(selectIdStr);
				}
			}			
		}
		openTheUIWindow(this, getEditUIName(), selectIdStr, OprtState.EDIT,"scheme");
		this.tblMain.refresh();
	}

	//�����ֹ�
	public void actionBatchEdit_actionPerformed(ActionEvent e) throws Exception
	{
		checkSelected();
		checkSelectArea();
		List idList = (List)getSelectedRoomIDs();
		UIContext uiContext = new UIContext(this);
		uiContext.put("roomIds", idList);
		uiContext.put("type", "noscheme");
		// ����UI������ʾ
		IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.MODEL)
					.create(RoomAreaCompensateBatchEditUI.class.getName(),uiContext, null, "ADDNEW");
		uiWindow.show();
		this.tblMain.refresh();
	}

	//��������
	public void actionBatchCompensate_actionPerformed(ActionEvent e)
			throws Exception
	{
		checkSelected();
		checkSelectArea();
		List idList = (List)getSelectedRoomIDs();
		UIContext uiContext = new UIContext(this);
		uiContext.put("roomIds", idList);
		uiContext.put("type", "scheme");
		// ����UI������ʾ
		IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.MODEL)
				.create(RoomAreaCompensateBatchEditUI.class.getName(),	uiContext, null, "ADDNEW");
		uiWindow.show();
		this.tblMain.refresh();
	}
	
	protected String getEditUIModal()
	{
		return UIFactoryName.MODEL;
	}

	protected ICoreBase getBizInterface() throws Exception
	{
		return RoomAreaCompensateFactory.getRemoteInstance();
	}


	protected String getKeyFieldName() {
		return "roomAreaCompensate.id";
	}
	
	//�����id
	protected String getSelectedRoomID()	{
		IRow selectRow = KDTableUtil.getSelectedRow(this.tblMain);
		if(selectRow==null) return null;
		
		return (String)selectRow.getCell("id").getValue();	
	}
	
	protected List getSelectedRoomIDs()	{
		List roomIds = new ArrayList();
		int selectRows[] = KDTableUtil.getSelectedRows(this.tblMain);
		for(int i=0;i<selectRows.length;i++) {
			roomIds.add(this.tblMain.getRow(selectRows[i]).getCell("id").getValue());
		}
		return roomIds;	
	}	

	protected void checkSelectArea() throws EASBizException, BOSException,
			Exception
	{
		int[] selectRows = KDTableUtil.getSelectedRows(this.tblMain);
		for (int i = 0, size = selectRows.length; i < size; i++)		{
			String idStr = (String)this.tblMain.getRow(selectRows[i]).getCell("roomAreaCompensate.id").getValue();
			if (idStr != null)			{
				RoomAreaCompensateInfo info = (RoomAreaCompensateInfo) getBizInterface().getValue(new ObjectUuidPK(idStr));
				if (info.getCompensateState() != null)				{
					if(!info.getCompensateState().equals(RoomCompensateStateEnum.COMSUBMIT) && !info.getCompensateState().equals(RoomCompensateStateEnum.COMAUDITTING)) {
						MsgBox.showInfo(this, "�ü�¼����"+RoomCompensateStateEnum.COMSUBMIT.getAlias()+"��"+RoomCompensateStateEnum.COMAUDITTING.getAlias()+"�����ܽ��д˲�����");
						SysUtil.abort();
					}
				}
			}
		}
		// �������ȷ��Ҫ�õ������ID
		List idList = (List)getSelectedRoomIDs();
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("id", FDCHelper.list2Set(idList),CompareType.INCLUDE));
		view.setFilter(filter);
		RoomCollection roomColl = RoomFactory.getRemoteInstance().getRoomCollection(view);
		for (int i=0;i<roomColl.size();i++)	{
			RoomInfo roomInfo = roomColl.get(i);
			if (roomInfo.isIsCalByRoomArea()) {
				if (roomInfo.getActualRoomArea() == null
						|| FDCHelper.toBigDecimal(roomInfo.getActualRoomArea()).compareTo(FDCHelper.ZERO) == 0)				{
					MsgBox.showInfo(this, "����ʵ���������Ϊ0�ļ�¼�����ڷ������¼����¼��ʵ�����������");
					SysUtil.abort();
				}
				if (!roomInfo.isIsActualAreaAudited())		{
					MsgBox.showInfo(this, "����ʵ�����δ���˵ļ�¼����Է��� "+ roomInfo.getNumber() + "����ʵ��������ˣ�");
					SysUtil.abort();
				}
			} else	{
				if (roomInfo.getActualBuildingArea() == null
						|| FDCHelper.toBigDecimal(roomInfo.getActualBuildingArea()).compareTo(FDCHelper.ZERO) == 0)				{
					MsgBox.showInfo(this, "����ʵ�⽨�����Ϊ0�ļ�¼�����ڷ������¼����¼��ʵ�⽨�������");
					SysUtil.abort();
				}
				if (!roomInfo.isIsActualAreaAudited())	{
					MsgBox.showInfo(this, "����ʵ�����δ���˵ļ�¼����Է��� "+ roomInfo.getNumber() + "����ʵ��������ˣ�");
					SysUtil.abort();
				}
			}
		}
	}

	protected void refresh(ActionEvent e) throws Exception
	{
		treeMain_valueChanged(null);
	}

	public void actionView_actionPerformed(ActionEvent e) throws Exception
	{
		String selectIdStr = getSelectedKeyValue(); 
		if(selectIdStr!=null)
			openTheUIWindow(this, getEditUIName(), selectIdStr, OprtState.VIEW,"noscheme");
		else
			openTheUIWindow(this, getEditUIName(), null, OprtState.ADDNEW,"noscheme");
	}


	public void actionRemove_actionPerformed(ActionEvent e) throws Exception
	{		
		IRow selectRow = KDTableUtil.getSelectedRow(this.tblMain);
		if(selectRow==null) return;
		
		String idStr = this.getSelectedKeyValue();
		Object roomIdStr = (String)selectRow.getCell("id").getValue();
		if (idStr != null)	{
			RoomAreaCompensateInfo info = (RoomAreaCompensateInfo) getBizInterface().getValue(new ObjectUuidPK(idStr.toString()));
			if (info.getCompensateState() != null
						&& (!info.getCompensateState().equals(RoomCompensateStateEnum.COMSUBMIT)))			{
				MsgBox.showInfo(this, "�����ϲ����ļ�¼����"+RoomCompensateStateEnum.COMSUBMIT.getAlias()+"�������ܽ��д˲�����");
				return;
			}
		} else	{
			FilterInfo filter = new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("room.id", roomIdStr.toString()));
			if (!getBizInterface().exists(filter))		{
				MsgBox.showInfo(this, "δ������ļ�¼,���ܽ��д˲�����");
				SysUtil.abort();
			}
		}
		
		if (confirmRemove()) {
			getBizInterface().delete(new ObjectUuidPK(idStr));
			FDCClientUtils.showOprtOK(this);
			refreshList();
		}
	}


	
	public void actionAudit_actionPerformed(ActionEvent e) throws Exception {
		this.checkSelected();
		this.checkTableParsed();
		if(KDTableUtil.getSelectedRowCount(this.tblMain) == 1){
			this.checkSelectArea();
			String idStr = this.getSelectedKeyValue();
			if(idStr == null)
			{
				MsgBox.showInfo(this, "�ü�¼����"+RoomCompensateStateEnum.COMSUBMIT.getAlias()+"��"+RoomCompensateStateEnum.COMAUDITTING.getAlias()+"�����ܽ��д˲�����");
				this.abort();
			}
			IRoomAreaCompensate iRoomComp = RoomAreaCompensateFactory.getRemoteInstance();
			RoomAreaCompensateInfo roomCompen = iRoomComp.getRoomAreaCompensateInfo(new ObjectUuidPK(BOSUuid.read(idStr)));
			if(roomCompen.getCompensateState()!=null && (roomCompen.getCompensateState().equals(RoomCompensateStateEnum.COMSUBMIT) || roomCompen.getCompensateState().equals(RoomCompensateStateEnum.COMAUDITTING))){
				RoomAreaCompensateFactory.getRemoteInstance().audit(BOSUuid.read(idStr));
				MsgBox.showInfo("�����ɹ���");
				this.tblMain.refresh();
				//this.tblMain.removeAll();
			}else{
				MsgBox.showInfo(this, "�ü�¼����"+RoomCompensateStateEnum.COMSUBMIT.getAlias()+"��"+RoomCompensateStateEnum.COMAUDITTING.getAlias()+"�����ܽ��д˲�����");
			}
		}else{
			int[] rows = KDTableUtil.getSelectedRows(tblMain);		
			IRoomAreaCompensate iRoomComp = RoomAreaCompensateFactory.getRemoteInstance();
			StringBuffer secc = new StringBuffer();
			for (int i = 0; i < rows.length; i++) {
				IRow row = tblMain.getRow(rows[i]);
				String id = (String) row.getCell(getKeyFieldName()).getValue();
				if (id != null) {
					RoomAreaCompensateInfo roomCompen = iRoomComp.getRoomAreaCompensateInfo(new ObjectUuidPK(BOSUuid.read(id)));
					if (roomCompen.getCompensateState() != null	&& (roomCompen.getCompensateState().equals(
									RoomCompensateStateEnum.COMSUBMIT) || roomCompen.getCompensateState().equals(RoomCompensateStateEnum.COMAUDITTING))) {
						RoomAreaCompensateFactory.getRemoteInstance().audit(BOSUuid.read(id));
						secc.append("��").append(row.getRowIndex()+1).append("�м�¼�����ɹ�\n");
					}else{
						secc.append("��").append(row.getRowIndex()+1).append("�м�¼����������δ�����ɹ�\n");
					}
				}else{
					secc.append("��").append(row.getRowIndex()+1).append("�м�¼����������δ�����ɹ�\n");
				}
			}
			MsgBox.showDetailAndOK(null,"��������������鿴��ϸ��Ϣ��" , secc.toString(), 1);		
			}
		refresh(null);

		
	}

	public void actionUnAudit_actionPerformed(ActionEvent e) throws Exception {
		checkSelected();
		String idStr = this.getSelectedKeyValue();
		if(idStr == null)
		{
			MsgBox.showWarning(this,"���δ�������Ѹ����������з�������");
			this.abort();
		}
		if(KDTableUtil.getSelectedRowCount(this.tblMain) > 1)
			{
				MsgBox.showInfo("��ѡ�������ݽ��з�������");
				this.abort();
			}
		ArrayList list = getSelectedIdValues();
		StringBuffer msg = new StringBuffer();
		if(list.size() != 0 ){
			if(list.size()==1){
				int actRowIdx = tblMain.getSelectManager().getActiveRowIndex();
				String compensateState= tblMain.getCell(actRowIdx, "roomAreaCompensate.compensateState").getValue().toString();
				if(!RoomCompensateStateEnum.COMAUDITTED.toString().equals(compensateState)){
					MsgBox.showWarning(this,"���δ�������Ѹ����������з�������");
					this.abort();
				}
			}
//			}else{
//				for(int i=0;i<list.size();i++){
//					String billId = (String) list.get(i);
//					RoomAreaCompensateInfo roomCom = RoomAreaCompensateFactory
//						.getRemoteInstance().getRoomAreaCompensateInfo(
//								"select compensateState,compensateAmount,room.* where id = '"
//										+ billId.toString() + "'");
//					if (!RoomCompensateStateEnum.COMAUDITTED.equals(roomCom.getCompensateState())) {
//						msg.append("��").append(i+1).append("�м�¼����������δ�������ɹ�\n");
//					}else{
//						msg.append("��").append(i+1).append("�м�¼�������ɹ�\n");
//					}
//				}
//			}
			RoomAreaCompensateFactory.getRemoteInstance().unAudit(list);	
//			MsgBox.showDetailAndOK(null,"���������������ȷ���鿴��ϸ��Ϣ��" , msg.toString(), 1);
			this.tblMain.refresh();
		} 
		
//		String id= tblMain.getCell(actRowIdx, "roomAreaCompensate.id").getValue().toString();
		
		
	}
	public void actionSubmitRoomWorkFlow_actionPerformed(ActionEvent e) throws Exception {
		super.actionSubmitRoomWorkFlow_actionPerformed(e);
		int rowIndex = this.tblMain.getSelectManager().getActiveRowIndex();
		if (rowIndex == -1) {
			MsgBox.showInfo("��ѡ����!");
			return;
		}
		
		String idStr = this.getSelectedKeyValue();
		IRoomAreaCompensate iRoomComp = RoomAreaCompensateFactory.getRemoteInstance();
		RoomAreaCompensateInfo roomCompen = iRoomComp.getRoomAreaCompensateInfo(new ObjectUuidPK(BOSUuid.read(idStr)));
		if(roomCompen.getCompensateState().equals(RoomCompensateStateEnum.NOCOMPENSATE)){
			RoomAreaCompensateFactory.getRemoteInstance().submit(roomCompen);
		}else{
			MsgBox.showInfo("�ü�¼����'"+RoomCompensateStateEnum.NOCOMPENSATE.getAlias()+"'״̬!");
		}

		
	}

	public void actionSubmitWorkFlow_actionPerformed(ActionEvent e)
			throws Exception
	{
		DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeMain
				.getLastSelectedPathComponent();
		if (node == null)
		{
			MsgBox.showInfo("��ѡ����Ӧ��¥����");
			return;
		}
		String msg = "����¥���Ĳ�������빤����������������ȷ���Ƿ��¥�������еķ��䲹��Ѿ�������ϣ��Ƿ�ȷ���ύ��";
		if (MsgBox.showConfirm2New(this, msg) == MsgBox.YES)
		{
			String buildingId = null;
			if (node.getUserObject() instanceof Integer)  //������
			{
				BuildingInfo building = (BuildingInfo) ((DefaultKingdeeTreeNode) node
						.getParent()).getUserObject();
				buildingId = building.getId().toString();
			}else if (node.getUserObject() instanceof BuildingUnitInfo)
			{
				BuildingInfo building = (BuildingInfo) ((DefaultKingdeeTreeNode) node
						.getParent()).getUserObject();
				buildingId = building.getId().toString();
			} else if (node.getUserObject() instanceof BuildingInfo)
			{
				BuildingInfo building = (BuildingInfo) node.getUserObject();
				if (!building.getCodingType().equals(
						CodingTypeEnum.UnitFloorNum))
				{
					buildingId = building.getId().toString();
				}
			}
			
			//��鵱ǰ¥���µĲ���Ƿ������ύ�򲹲�����״̬�ĵ���
			IRoomAreaCompensate iRoomComp = RoomAreaCompensateFactory.getRemoteInstance();
			FilterInfo roomfilter = new FilterInfo();
			roomfilter.setMaskString("#0 and (#1 or #2)");
			roomfilter.getFilterItems().add(new FilterItemInfo("room.building.id",buildingId));
			roomfilter.getFilterItems().add(new FilterItemInfo("compensateState",RoomCompensateStateEnum.COMAUDITTING));
			roomfilter.getFilterItems().add(new FilterItemInfo("compensateState",RoomCompensateStateEnum.COMSUBMIT));
			if(!iRoomComp.exists(roomfilter)) {
				MsgBox.showInfo("��¥���в�����"+RoomCompensateStateEnum.COMSUBMIT.getAlias()+"��"+RoomCompensateStateEnum.COMAUDITTING.getAlias()+"�ķ���!");
				return;
			}
			
			
			FilterInfo filter = new FilterInfo();
			filter.getFilterItems().add(
					new FilterItemInfo("building.id", buildingId));
			IBuildingCompensate ibuilding = BuildingCompensateFactory
					.getRemoteInstance();
			if (ibuilding.exists(filter))
			{
				EntityViewInfo view = new EntityViewInfo();
				view.setFilter(filter);
				BuildingCompensateInfo buildingCompensate = ibuilding.getBuildingCompensateCollection(view).get(0);
				String buildingCompensateId = null;
				if (buildingCompensate != null) {
					buildingCompensateId = buildingCompensate.getId().toString();
				}
				if (!(FDCBillStateEnum.SUBMITTED.equals(buildingCompensate.getState()))) {
					FDCClientUtils.checkBillInWorkflow(this, buildingCompensateId);
				}
			}
			if (buildingId != null)
			{
				RoomAreaCompensateFactory.getRemoteInstance().submitToWorkFlow(
						buildingId);
			}
		}
	}

	/**
	 * ��ʵ���տ��action
	 */
	public void actionExecute_actionPerformed(ActionEvent e) throws Exception
	{
		checkSelected();
		if(KDTableUtil.getSelectedRowCount(this.tblMain) > 1)
		{
			MsgBox.showWarning("��ѡ�������ݽ����տ������");
			return;
		}
		IRow row = KDTableUtil.getSelectedRow(this.tblMain);
		if(row == null)
			return;
		
		String idStr = (String)row.getCell(this.getKeyFieldName()).getValue();
		if(idStr==null || idStr.trim().length()==0) {
			MsgBox.showWarning("ֻ��"+RoomCompensateStateEnum.COMAUDITTED.getAlias()+"�ĵ��ݣ����ܽ����տ������");
			return;
		}
		
		RoomAreaCompensateInfo areaComInfo = RoomAreaCompensateFactory.getRemoteInstance()
				.getRoomAreaCompensateInfo("select id,name,number,compensateState,state,compensateAmount " +
						",room.id,room.building.sellProject.id,room.building.sellProject.name" +
						",room.lastPurchase.id,room.lastPurchase.name,room.lastPurchase.number  " +
						"where id = '"+idStr+"'"); 
		if(areaComInfo.getCompensateState()==null 
				|| RoomCompensateStateEnum.COMSUBMIT.equals(areaComInfo.getCompensateState())
				|| RoomCompensateStateEnum.COMAUDITTING.equals(areaComInfo.getCompensateState()) )	{
			MsgBox.showWarning("ֻ��"+RoomCompensateStateEnum.COMAUDITTED.getAlias()+"��ĵ��ݣ����ܽ����տ������");
			return;
		}

		BigDecimal compensateAmount = areaComInfo.getCompensateAmount();
		if(compensateAmount==null) compensateAmount = FDCHelper.ZERO;
		if(compensateAmount.compareTo(FDCHelper.ZERO)==0)	return;
		
		UIContext uiContext = new UIContext(this);
		uiContext.put(SHEReceivingBillEditUI.KEY_SELL_PROJECT, areaComInfo.getRoom().getBuilding().getSellProject());
		if(compensateAmount.compareTo(FDCHelper.ZERO)>0)	{
			uiContext.put(SHEReceivingBillEditUI.KEY_REV_BILL_TYPE, RevBillTypeEnum.gathering);
		}else if(compensateAmount.compareTo(FDCHelper.ZERO)<0) {
			uiContext.put(SHEReceivingBillEditUI.KEY_REV_BILL_TYPE, RevBillTypeEnum.refundment);
			uiContext.put(SHEReceivingBillEditUI.KEY_IS_LOCK_BILL_TYPE, new Boolean(true));
		}
		
		uiContext.put(SHEReceivingBillEditUI.KEY_REV_BIZ_TYPE, RevBizTypeEnum.areaCompensate);
		uiContext.put(SHEReceivingBillEditUI.KEY_SELL_AreaCompensate, areaComInfo);
		IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.NEWTAB)
				.create(SHEReceivingBillEditUI.class.getName(), uiContext, null,"ADDNEW");
		uiWindow.show();		
		
		this.refresh(null);
	}


	protected void getRowSetBeforeFillTable(IRowSet rowSet)
	{
		super.getRowSetBeforeFillTable(rowSet);
		String compensateState = null;
		try
		{
			rowSet.beforeFirst();
			while (rowSet.next())
			{
				compensateState = rowSet.getString("roomAreaCompensate.compensateState");
				if (compensateState == null)				{
					rowSet.updateString("roomAreaCompensate.compensateState",	RoomCompensateStateEnum.NOCOMPENSATE.toString());
				}
			}
		} catch (SQLException e)
		{
			handleException(e);
		} catch (UuidException e)
		{
			handleException(e);
		}
	}

	protected boolean isIgnoreCUFilter()
	{
		return true;
	}

	protected FilterInfo getTreeFilter() throws Exception
	{
		DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeMain
				.getLastSelectedPathComponent();
		if (node == null)
		{
			return null;
		}
		Set set = new HashSet(2);
		set.add(null);
		set.add(FDCHelper.ZERO);
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("purchase.areaCompensateAmount", null));
		filter.getFilterItems().add(new FilterItemInfo("purchase.areaCompensateAmount",	FDCHelper.ZERO));
		if (node.getUserObject() instanceof BuildingUnitInfo)		{
			BuildingUnitInfo buildUnit = (BuildingUnitInfo) node.getUserObject();
			BuildingInfo building = (BuildingInfo) ((DefaultKingdeeTreeNode) node
					.getParent()).getUserObject();
			String buildingId = building.getId().toString();
			filter.getFilterItems().add(
					new FilterItemInfo("building.id", buildingId));
			filter.getFilterItems().add(new FilterItemInfo("buildUnit.id", buildUnit.getId().toString()));
			if (saleOrg.isIsBizUnit())
				FDCClientHelper.setActionEnableAndNotSetVisible(
						new ItemAction[]
						{ actionEdit, actionBatchCompensate,
								actionSingleScheme, actionBatchEdit,
								actionExecute, actionSubmitWorkFlow,
								actionRemove }, true);
			filter.setMaskString("(#0 or #1) and #2 and #3");
		} else if (node.getUserObject() instanceof BuildingInfo)		{
			BuildingInfo building = (BuildingInfo) node.getUserObject();
			if (!building.getCodingType().equals(CodingTypeEnum.UnitFloorNum))
			{
				String buildingId = building.getId().toString();
				filter.getFilterItems().add(
						new FilterItemInfo("building.id", buildingId));
				if (saleOrg.isIsBizUnit())
					FDCClientHelper.setActionEnableAndNotSetVisible(
							new ItemAction[]
							{ actionEdit, actionBatchCompensate,
									actionSingleScheme, actionBatchEdit,
									actionExecute, actionSubmitWorkFlow,
									actionRemove }, true);
				filter.setMaskString("(#0 or #1) and #2");
			} else			{
				filter.getFilterItems().add(new FilterItemInfo("id", null));
				FDCClientHelper.setActionEnableAndNotSetVisible(
						new ItemAction[]
						{ actionEdit, actionBatchCompensate,
								actionSingleScheme, actionBatchEdit,
								actionExecute, actionSubmitWorkFlow,
								actionRemove }, false);
				filter.setMaskString("(#0 or #1) and #2");
			}
		} else		{
			filter.getFilterItems().add(new FilterItemInfo("id", null));
			FDCClientHelper.setActionEnableAndNotSetVisible(new ItemAction[]
			{ actionEdit, actionBatchCompensate, actionSingleScheme,
					actionBatchEdit, actionExecute, actionSubmitWorkFlow,
					actionRemove }, false);
			filter.setMaskString("(#0 or #1) and #2");
		}
		return filter;
	}

	protected IQueryExecutor getQueryExecutor(IMetaDataPK queryPK,
			EntityViewInfo viewInfo)
	{
		viewInfo = (EntityViewInfo) mainQuery.clone();
		if(viewInfo.getFilter() != null)
		{
		FilterItemCollection itemColl = viewInfo.getFilter().getFilterItems();
		if(itemColl != null)
		{
			for(int i = 0; i < itemColl.size(); i ++)
			{
				FilterItemInfo info = itemColl.get(i);
				if(info != null && "roomAreaCompensate.compensateState".equalsIgnoreCase(info.getPropertyName()) && "NOCOMPENSATE".equalsIgnoreCase((String)info.getCompareValue()))
				{
					info.setCompareValue(null);
				}
			}
		}
		}
		
		// �ϲ���ѯ����
		try
		{
			FilterInfo filter = getTreeFilter();
			if (viewInfo.getFilter() != null)
			{
				viewInfo.getFilter().mergeFilter(filter, "and");
			} else			{
				viewInfo.setFilter(filter);
			}
		} catch (Exception e)		{
			e.printStackTrace();
		}

		return super.getQueryExecutor(queryPK, viewInfo);
	}

	public void afterActionPerformed(ActionEvent e)
	{
		super.afterActionPerformed(e);
		if (e.getSource() == btnEdit || e.getSource() == menuItemEdit
				|| e.getSource() == btnBatchCompensate
				|| e.getSource() == menuItemBatchComp
				|| e.getSource() == menuItemSingleSheme
				|| e.getSource() == menuItemBatchEdit)
		{
			try
			{
				refresh(e);
			} catch (Exception e1)
			{
				logger.error(e1);
			}
		}
	}

	public void actionExport_actionPerformed(ActionEvent e) throws Exception
	{
		handlePermissionForItemAction(actionExport);
		super.actionExport_actionPerformed(e);
	}

	public void actionExportData_actionPerformed(ActionEvent e)
			throws Exception
	{
		handlePermissionForItemAction(actionExportData);
		super.actionExportData_actionPerformed(e);
	}

	public void actionExportSelected_actionPerformed(ActionEvent e)
			throws Exception
	{
		handlePermissionForItemAction(actionExportSelected);
		super.actionExportSelected_actionPerformed(e);

	}

	public void actionToExcel_actionPerformed(ActionEvent e) throws Exception
	{
		handlePermissionForItemAction(actionToExcel);
		super.actionToExcel_actionPerformed(e);
	}
	
	/**
	 * ��д����ͼ�鿴
	 * @author laiquan_luo
	 */
	public void actionViewDoProccess_actionPerformed(ActionEvent e)
			throws Exception
	{
		DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeMain
				.getLastSelectedPathComponent();
		if (node == null)
		{
			MsgBox.showInfo("��ѡ����Ӧ��¥����");
			return;
		}

		String buildingId = null;

		if (node.getUserObject() instanceof Integer)   //������
		{
			BuildingInfo building = (BuildingInfo) ((DefaultKingdeeTreeNode) node
					.getParent()).getUserObject();
			buildingId = building.getId().toString();
		}else if (node.getUserObject() instanceof BuildingUnitInfo)   
		{
			BuildingInfo building = (BuildingInfo) ((DefaultKingdeeTreeNode) node
					.getParent()).getUserObject();
			buildingId = building.getId().toString();
		} else if (node.getUserObject() instanceof BuildingInfo)
		{
			BuildingInfo building = (BuildingInfo) node.getUserObject();
			if (!building.getCodingType().equals(CodingTypeEnum.UnitFloorNum))
			{
				buildingId = building.getId().toString();
			}
		}

		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(
				new FilterItemInfo("building.id", buildingId));
		IBuildingCompensate ibuilding = BuildingCompensateFactory
				.getRemoteInstance();

		String buildingCompensateId = null;
		if (ibuilding.exists(filter))
		{
			EntityViewInfo view = new EntityViewInfo();
			view.setFilter(filter);
			buildingCompensateId = ibuilding.getBuildingCompensateCollection(
					view).get(0).getId().toString();
		}
		
		
		
		String id = buildingCompensateId;
		
		
		if(id == null)
		{
			MsgBox.showInfo("��ǰû�ж�Ӧ�����̣�");
			abort();
		}
		
		IEnactmentService service = EnactmentServiceFactory
				.createRemoteEnactService();
		ProcessInstInfo processInstInfo = null;
		ProcessInstInfo[] procInsts = service
				.getProcessInstanceByHoldedObjectId(id);
		for (int i = 0, n = procInsts.length; i < n; i++)
		{
			if (procInsts[i].getState().startsWith("open"))
			{
				processInstInfo = procInsts[i];
			}
		}
		if (processInstInfo == null)
		{
			//���û������ʱ���̣��ж��Ƿ��п���չ�ֵ�����ͼ��չ��
			procInsts = service.getAllProcessInstancesByBizobjId(id);
			if (procInsts == null || procInsts.length <= 0)
				MsgBox.showInfo(this, EASResource
						.getString(FrameWorkClientUtils.strResource
								+ "Msg_WFHasNotInstance"));
			else if (procInsts.length == 1)
			{
				showWorkflowDiagram(procInsts[0]);
			} else
			{
				UIContext uiContext = new UIContext(this);
				uiContext.put("procInsts", procInsts);
				String className = ProcessRunningListUI.class.getName();
				IUIWindow uiWindow = UIFactory.createUIFactory(
						UIFactoryName.MODEL).create(className, uiContext);
				uiWindow.show();
			}
		} else
		{
			showWorkflowDiagram(processInstInfo);
		}
	}

	private void showWorkflowDiagram(ProcessInstInfo processInstInfo)
			throws Exception
	{
		UIContext uiContext = new UIContext(this);
		uiContext.put("id", processInstInfo.getProcInstId());
		uiContext.put("processInstInfo", processInstInfo);
		String className = BasicWorkFlowMonitorPanel.class.getName();
		IUIWindow uiWindow = UIFactory.createUIFactory(getEditUIModal())
				.create(className, uiContext);
		uiWindow.show();
	}
	
	public void actionAuditResult_actionPerformed(ActionEvent e)
			throws Exception
	{
		DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeMain
				.getLastSelectedPathComponent();
		if (node == null)
		{
			MsgBox.showInfo("��ѡ����Ӧ��¥����");
			return;
		}

		String buildingId = null;

		if (node.getUserObject() instanceof Integer)  //������
		{
			BuildingInfo building = (BuildingInfo) ((DefaultKingdeeTreeNode) node
					.getParent()).getUserObject();
			buildingId = building.getId().toString();
		}else if (node.getUserObject() instanceof BuildingUnitInfo)
		{
			BuildingInfo building = (BuildingInfo) ((DefaultKingdeeTreeNode) node
					.getParent()).getUserObject();
			buildingId = building.getId().toString();
		} else if (node.getUserObject() instanceof BuildingInfo)
		{
			BuildingInfo building = (BuildingInfo) node.getUserObject();
			if (!building.getCodingType().equals(CodingTypeEnum.UnitFloorNum))
			{
				buildingId = building.getId().toString();
			}
		}

		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(
				new FilterItemInfo("building.id", buildingId));
		IBuildingCompensate ibuilding = BuildingCompensateFactory
				.getRemoteInstance();

		String buildingCompensateId = null;
		if (ibuilding.exists(filter))
		{
			EntityViewInfo view = new EntityViewInfo();
			view.setFilter(filter);
			buildingCompensateId = ibuilding.getBuildingCompensateCollection(
					view).get(0).getId().toString();
		}

		String id = buildingCompensateId;

		if (id == null)
		{
			MsgBox.showInfo("��ǰû�ж�Ӧ�����̣�");
			abort();
		}
		if (!StringUtils.isEmpty(id))
		{
			MultiApproveUtil.showApproveHis(BOSUuid.read(id),
					UIModelDialogFactory.class.getName(), this);
		}
	}
	
	public void onShow() throws Exception
	{
		this.actionAuditResult.setEnabled(true);
		this.actionAttachment.setVisible(false);
		super.onShow();
	}
	
	
	/**
	 * ��ModelDialogģʽu��ָ����ui  ���idΪ���������������,������oprtState��ʽ��
	 */
	private void openTheUIWindow(Object owner,String uiClassName,String id,String oprtState,String type) {		
		UIContext uiContext = new UIContext(owner); 	
		uiContext.put("type", type);
		uiContext.put("roomId", getSelectedRoomID());
		
		if(oprtState==null) oprtState = OprtState.VIEW;
		if(id==null || id.trim().equals("")) {
			oprtState = OprtState.ADDNEW;
		}else{
			uiContext.put(UIContext.ID, id);
		}
		try {
			IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.MODEL).create(uiClassName, uiContext, null, oprtState);
			uiWindow.show();
		} catch (UIException e) {
			e.printStackTrace();
			SysUtil.abort();
		}
	}
	
	
	

}