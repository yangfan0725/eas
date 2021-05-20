/**
 * output package name
 */
package com.kingdee.eas.fdc.tenancy.client;

import java.awt.Dialog;
import java.awt.Frame;
import java.awt.Window;
import java.awt.event.*;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import javax.swing.JDialog;
import javax.swing.SwingUtilities;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.tree.TreeNode;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.metadata.IMetaDataPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SorterItemCollection;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.bos.ctrl.kdf.servertable.KDTStyleConstants;
import com.kingdee.bos.ctrl.kdf.table.ICell;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectManager;
import com.kingdee.bos.ctrl.kdf.table.event.KDTDataRequestEvent;
import com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent;
import com.kingdee.bos.ctrl.kdf.table.event.KDTSelectEvent;
import com.kingdee.bos.ctrl.kdf.table.util.KDTableUtil;
import com.kingdee.bos.ctrl.swing.KDDialog;
import com.kingdee.bos.ctrl.swing.KDWorkButton;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.dao.query.IQueryExecutor;
import com.kingdee.eas.base.permission.client.longtime.ILongTimeTask;
import com.kingdee.eas.base.uiframe.client.UIFactoryHelper;
import com.kingdee.eas.basedata.org.SaleOrgUnitInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basecrm.client.CRMClientHelper;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.MoneySysTypeEnum;
import com.kingdee.eas.fdc.basedata.client.FDCClientUtils;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.merch.common.KDTableHelper;
import com.kingdee.eas.fdc.sellhouse.SellProjectInfo;
import com.kingdee.eas.fdc.sellhouse.client.SHEHelper;
import com.kingdee.eas.fdc.tenancy.IDepositDealBill;
import com.kingdee.eas.fdc.tenancy.DepositDealBillFactory;
import com.kingdee.eas.fdc.tenancy.DepositDealBillInfo;
import com.kingdee.eas.fdc.tenancy.IOtherBill;
import com.kingdee.eas.fdc.tenancy.TenancyBillCollection;
import com.kingdee.eas.fdc.tenancy.TenancyBillFactory;
import com.kingdee.eas.fdc.tenancy.TenancyBillInfo;
import com.kingdee.eas.fdc.tenancy.TenancyBillStateEnum;
import com.kingdee.eas.framework.*;
import com.kingdee.eas.framework.batchHandler.UtilRequest;
import com.kingdee.eas.framework.client.FindDialog;
import com.kingdee.eas.framework.client.FindListEvent;
import com.kingdee.eas.framework.client.FrameWorkClientUtils;
import com.kingdee.eas.framework.client.IFindListListener;
import com.kingdee.eas.framework.client.ListFind;
import com.kingdee.eas.framework.client.ListUiHelper;
import com.kingdee.eas.framework.util.StringUtility;
import com.kingdee.eas.ma.budget.client.LongTimeDialog;
import com.kingdee.eas.tools.datatask.DatataskParameter;
import com.kingdee.eas.tools.datatask.client.DatataskCaller;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.MsgBox;

/**
 * output class name
 */
public class DepositDealBillListUI extends AbstractDepositDealBillListUI
{
    private static final Logger logger = CoreUIObject.getLogger(DepositDealBillListUI.class);
    public DepositDealBillListUI() throws Exception
    {
        super();
    }
    protected boolean isNeedfetchInitData()throws Exception{
    	return false;
    }
    public void onLoad() throws Exception {

		super.onLoad();
		tblMain.getSelectManager().setSelectMode(
				KDTSelectManager.MULTIPLE_ROW_SELECT);
		this.actionTraceDown.setVisible(false);
		this.actionAdjust.setVisible(false);
		this.treeMain.setSelectionRow(0);
		
		this.actionQuery.setVisible(false);
		this.kdtTenancy.setEnabled(false);
		this.kdtTenancy.getSelectManager().setSelectMode(KDTSelectManager.ROW_SELECT);
		
		this.actionBatchReceiving.setVisible(false);
		this.actionReceive.setVisible(false);
		this.actionVoucher.setVisible(false);
		this.actionDelVoucher.setVisible(false);
		this.actionCanceReceive.setVisible(false);
		this.actionReceipt.setVisible(false);
		this.actionRetakeReceipt.setVisible(false);
		this.actionCreateInvoice.setVisible(false);
		this.actionClearInvoice.setVisible(false);
		
		this.menuItemUpdateSubject.setVisible(false);
		
		String[] fields=new String[this.kdtTenancy.getColumnCount()];
		for(int i=0;i<this.kdtTenancy.getColumnCount();i++){
			fields[i]=this.kdtTenancy.getColumnKey(i);
		}
		KDTableHelper.setSortedColumn(this.kdtTenancy,fields);
		this.actionAttachment.setVisible(true);
    }
    protected void afterTableFillData(KDTDataRequestEvent e) {
		super.afterTableFillData(e);
		CRMClientHelper.getFootRow(tblMain, new String[]{"entry.amount"});
	}

	protected void setColGroups() {
	}

	protected IQueryExecutor getQueryExecutor(IMetaDataPK queryPK,EntityViewInfo viewInfo) {
		FilterInfo filter = new FilterInfo();
		int rowIndex = this.kdtTenancy.getSelectManager().getActiveRowIndex();
		String id="'null'";
		if(rowIndex>=0){
			IRow row = this.kdtTenancy.getRow(rowIndex);
			id = (String) row.getCell("id").getValue();
		}
		filter.getFilterItems().add(new FilterItemInfo("tenancyBill.id",id));
		try {
			viewInfo = (EntityViewInfo) this.mainQuery.clone();
			if (viewInfo.getFilter() != null)
			{
				viewInfo.getFilter().mergeFilter(filter, "and");
			} else
			{
				viewInfo.setFilter(filter);
			}
		} catch (BOSException e) {
			this.handleException(e);
			this.abort();
		}
		return super.getQueryExecutor(queryPK, viewInfo);
	}

	protected void kdtTenancy_tableClicked(KDTMouseEvent e) throws Exception {
		if (e.getType() == KDTStyleConstants.BODY_ROW && e.getButton() == MouseEvent.BUTTON1 && e.getClickCount() == 2) {
			IRow row = this.kdtTenancy.getRow(e.getRowIndex());
			UIContext uiContext = new UIContext(this);
			uiContext.put("ID", row.getCell("id").getValue().toString());
			IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.MODEL).create(TenancyBillEditUI.class.getName(), uiContext, null, OprtState.VIEW);
			uiWindow.show();
		}
	}
	protected void kdtTenancy_tableSelectChanged(KDTSelectEvent e)
			throws Exception {
		this.refresh(null);
	}
	// ����ϵͳ�տ�������ͬһ��Ŀ��ͬ¥���෿��,�������������Ҫ������Ŀ��
	protected void initTree() throws Exception {
		this.treeMain.setModel(SHEHelper.getSellProjectTree(this.actionOnLoad,MoneySysTypeEnum.TenancySys));
		this.treeMain.expandAllNodes(true, (TreeNode) this.treeMain.getModel().getRoot());
	}

	public void setUITitle(String title) {
		super.setUITitle("Ѻ�������뵥��ʱ��");
	}

	protected void prepareUIContext(UIContext uiContext, ActionEvent e) {
		DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeMain
				.getLastSelectedPathComponent();
		if (node == null) {
			return;
		}
		if (node.getUserObject() instanceof SellProjectInfo) {
			SellProjectInfo sellProject = (SellProjectInfo) node
					.getUserObject();
			uiContext.put("sellProject", sellProject);
		}
		int rowIndex = this.kdtTenancy.getSelectManager().getActiveRowIndex();
		if(e.getActionCommand().equals("com.kingdee.eas.framework.client.AbstractListUI$ActionAddNew")){
			if(rowIndex>=0){
				IRow row = this.kdtTenancy.getRow(rowIndex);
				String id = (String) row.getCell("id").getValue();
		    	SelectorItemCollection sels = new SelectorItemCollection();
				sels.add("*");
				sels.add("sellProject.*");
				sels.add("orgUnit.*");
				sels.add("tenancyAdviser.*");
				
				sels.add("tenancyRoomList.*");
				sels.add("tenancyRoomList.room.floor");
				sels.add("tenancyRoomList.room.isForPPM");
				sels.add("tenancyRoomList.room.number");

				sels.add("tenancyRoomList.room.building.name");
				sels.add("tenancyRoomList.room.building.number");
				sels.add("tenancyRoomList.room.roomModel.name");
				sels.add("tenancyRoomList.room.roomModel.number");
				sels.add("tenancyRoomList.room.buildingProperty.name");
				sels.add("tenancyRoomList.room.direction.number");
				sels.add("tenancyRoomList.room.direction.name");
				sels.add("tenancyRoomList.room.buildingProperty.number");
				sels.add("tenancyRoomList.room.building.sellProject.name");
				sels.add("tenancyRoomList.room.building.sellProject.number");
				sels.add("tenancyRoomList.roomPayList.*");
				sels.add("tenancyRoomList.roomPayList.currency.name");
				sels.add("tenancyRoomList.roomPayList.currency.number");

				sels.add("tenancyRoomList.roomPayList.moneyDefine.name");
				sels.add("tenancyRoomList.roomPayList.moneyDefine.number");
				sels.add("tenancyRoomList.roomPayList.moneyDefine.moneyType");
				sels.add("tenancyRoomList.roomPayList.moneyDefine.sysType");
				sels.add("tenancyRoomList.roomPayList.moneyDefine.isEnabled");

				sels.add("tenancyRoomList.dealAmounts.*");
				sels.add("tenancyRoomList.dealAmounts.moneyDefine.name");
				sels.add("tenancyRoomList.dealAmounts.moneyDefine.number");
				sels.add("tenancyRoomList.dealAmounts.moneyDefine.moneyType");
				sels.add("tenancyRoomList.dealAmounts.moneyDefine.sysType");
				sels.add("tenancyRoomList.dealAmounts.moneyDefine.isEnabled");
				try {
					TenancyBillInfo  tenBill = TenancyBillFactory.getRemoteInstance().getTenancyBillInfo(new ObjectUuidPK(id),sels);
					uiContext.put("tenancy", tenBill);
				} catch (EASBizException e1) {
					e1.printStackTrace();
				} catch (BOSException e1) {
					e1.printStackTrace();
				}
			}else{
				FDCMsgBox.showWarning(this,"��ѡ���У�");
				this.abort();
			}
		}
		super.prepareUIContext(uiContext, e);
	}

	protected String getEditUIName() {
		return DepositDealBillEditUI.class.getName();
	}
	protected MoneySysTypeEnum getSystemType() {
		return MoneySysTypeEnum.TenancySys;
	}
	public ArrayList getSelectedValues(String fieldName) {
		ArrayList list = new ArrayList();
		int[] selectRows = KDTableUtil.getSelectedRows(getBillListTable());
		for (int i = 0; i < selectRows.length; i++) {
			if (selectRows[i] == -1)
				selectRows[i] = 0;
			ICell cell = getBillListTable().getRow(selectRows[i]).getCell(
					fieldName);
			if (cell == null) {
				MsgBox.showError(EASResource
						.getString(FrameWorkClientUtils.strResource
								+ "Error_KeyField_Fail"));
				SysUtil.abort();
			}
			if (cell.getValue() != null) {
				String id = cell.getValue().toString();
				if (!list.contains(id))
					list.add(id);
			}

		}
		return list;
	}
	protected void treeMain_valueChanged(TreeSelectionEvent e) throws Exception {
		super.treeMain_valueChanged(e);
		DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeMain
				.getLastSelectedPathComponent();
		if (node == null) {
			return;
		}
		SaleOrgUnitInfo saleOrg = SHEHelper.getCurrentSaleOrg();
		if (!saleOrg.isIsBizUnit()) {
			this.actionAddNew.setEnabled(false);
			this.actionEdit.setEnabled(false);
			this.actionRemove.setEnabled(false);
			this.actionAudit.setEnabled(false);
			this.actionUnAudit.setEnabled(false);
		} else {
			this.actionAddNew.setEnabled(false);
			this.actionEdit.setEnabled(true);
			this.actionRemove.setEnabled(true);
			this.actionAudit.setEnabled(true);
			this.actionUnAudit.setEnabled(true);
			if (node.getUserObject() instanceof SellProjectInfo) {
				this.actionAddNew.setEnabled(true);
				this.actionView.setEnabled(true);
			}
		}
		getTenancyBillList();
	}
	private void getTenancyBillList(){
		DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeMain.getLastSelectedPathComponent();
		EntityViewInfo vi = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("tenancyState",TenancyBillStateEnum.AUDITED_VALUE));
		filter.getFilterItems().add(new FilterItemInfo("tenancyState",TenancyBillStateEnum.EXECUTING_VALUE));
		filter.getFilterItems().add(new FilterItemInfo("tenancyState",TenancyBillStateEnum.CONTINUETENANCYING_VALUE));
		if(this.cbIsAll.isSelected()){
			filter.getFilterItems().add(new FilterItemInfo("tenancyState",TenancyBillStateEnum.EXPIRATION_VALUE));
		}
		if (node != null  &&  node.getUserObject() instanceof SellProjectInfo) {
			SellProjectInfo pro = (SellProjectInfo) node.getUserObject();
			filter.getFilterItems().add(new FilterItemInfo("sellProject.id", pro.getId().toString()));
		} else {
			filter.getFilterItems().add(new FilterItemInfo("id", null));
		}
		if(this.cbIsAll.isSelected()){
			filter.setMaskString("(#0 or #1 or #2 or #3) and #4");
		}else{
			filter.setMaskString("(#0 or #1 or #2) and #3");
		}
		vi.setFilter(filter);
		SorterItemCollection sort=new SorterItemCollection();
		sort.add(new SorterItemInfo("name"));
		vi.setSorter(sort);
		SelectorItemCollection sels =new SelectorItemCollection();
		sels.add("*");
		sels.add("oldTenancyBill.*");
		sels.add("tenancyAdviser.*");
		sels.add("creator.*");
		sels.add("agency.*");
		vi.setSelector(sels);
		try {
			TenancyBillCollection tencol = TenancyBillFactory.getRemoteInstance().getTenancyBillCollection(vi);
			this.kdtTenancy.removeRows();
			this.tblMain.removeRows();
			for(int i=0;i<tencol.size();i++){
				IRow row=this.kdtTenancy.addRow();
				TenancyBillInfo info=tencol.get(i);
				row.getCell("id").setValue(info.getId().toString());
				row.getCell("tenancyState").setValue(info.getTenancyState().getAlias());
				row.getCell("moreRoomsType").setValue(info.getMoreRoomsType().getAlias());
				row.getCell("auditDate").setValue(info.getAuditTime());
				row.getCell("number").setValue(info.getNumber());
				row.getCell("tenancyName").setValue(info.getTenancyName());
				row.getCell("tenRoomsDes").setValue(info.getTenRoomsDes());
				row.getCell("tenAttachesDes").setValue(info.getTenAttachesDes());
				row.getCell("tenCustomerDes").setValue(info.getTenCustomerDes());
				row.getCell("tenancyType").setValue(info.getTenancyType());
				if(info.getOldTenancyBill()!=null)row.getCell("oldTenancyBill.tenancyName").setValue(info.getOldTenancyBill().getTenancyName());
				row.getCell("startDate").setValue(info.getStartDate());
				row.getCell("leaseCount").setValue(info.getLeaseCount());
				row.getCell("endDate").setValue(info.getEndDate());
				row.getCell("leaseTime").setValue(info.getLeaseTime());
				row.getCell("flagAtTerm").setValue(info.getFlagAtTerm());
				if(info.getTenancyAdviser()!=null)row.getCell("tenancyAdviser.name").setValue(info.getTenancyAdviser().getName());
				if(info.getAgency()!=null)row.getCell("agency.name").setValue(info.getAgency().getName());
				row.getCell("dealTotalRent").setValue(info.getDealTotalRent());
//				row.getCell("roomsRentType").setValue(info.getRentStartType());
				row.getCell("standardTotalRent").setValue(info.getStandardTotalRent());
				row.getCell("depositAmount").setValue(info.getDepositAmount());
				row.getCell("firstPayRent").setValue(info.getFirstPayRent());
				row.getCell("deliveryRoomDate").setValue(info.getDeliveryRoomDate());
				row.getCell("description").setValue(info.getDescription());
				row.getCell("specialClause").setValue(info.getSpecialClause());
				if(info.getCreator()!=null)row.getCell("creator.name").setValue(info.getCreator().getName());
				row.getCell("createTime").setValue(info.getCreateTime());
				row.getCell("state").setValue(info.getState());
				row.getCell("startDate").setValue(info.getStartDate());
				row.getCell("tenBillRoomState").setValue(info.getTenBillRoomState());
			}
		} catch (BOSException ee) {
			ee.printStackTrace();
		}
	}
	 public FDCBillStateEnum getBizState(String id) throws EASBizException, BOSException, Exception{
	    	if(id==null) return null;
	    	SelectorItemCollection sels =new SelectorItemCollection();
	    	sels.add("state");
	    	return ((DepositDealBillInfo)getBizInterface().getValue(new ObjectUuidPK(id),sels)).getState();
	    }
	protected void checkBeforeEditOrRemove(String warning,String id) throws EASBizException, BOSException, Exception {
    	//����Ƿ��ڹ�������
		FDCClientUtils.checkBillInWorkflow(this, id);
		
		FDCBillStateEnum state = getBizState(id);
		
		if (!FDCBillStateEnum.SUBMITTED.equals(state)&&!FDCBillStateEnum.SAVED.equals(state)) {
			if(warning.equals("cantEdit")){
				FDCMsgBox.showWarning("���ݲ��Ǳ�������ύ״̬�����ܽ����޸Ĳ�����");
				SysUtil.abort();
			}else{
				FDCMsgBox.showWarning("���ݲ��Ǳ�������ύ״̬�����ܽ���ɾ��������");
				SysUtil.abort();
			}
		}
	}
	public void actionEdit_actionPerformed(ActionEvent e) throws Exception {
		checkSelected();
		int rowIndex = this.tblMain.getSelectManager().getActiveRowIndex();
		IRow row = this.tblMain.getRow(rowIndex);
		String id = (String) row.getCell(this.getKeyFieldName()).getValue();
		
		checkBeforeEditOrRemove("cantEdit",id);
		
		IUIWindow uiWindow = showEditUI(e);
		uiWindow.show();
		if(isDoRefresh(uiWindow))
		                {
			if(UtilRequest.isPrepare("ActionRefresh", this))
				prepareRefresh(null).callHandler();
			setLocatePre(false);
			refresh(e);
			setPreSelecteRow();
			setLocatePre(true);
		}
	}
	private IUIWindow showEditUI(ActionEvent e)throws Exception{
		checkSelected();
		checkObjectExists();
		UIContext uiContext = new UIContext(this);
		uiContext.put("ID", getSelectedKeyValue());
		prepareUIContext(uiContext, e);
		IUIWindow uiWindow = null;
		if(SwingUtilities.getWindowAncestor(this) != null && (SwingUtilities.getWindowAncestor(this) instanceof JDialog))
			uiWindow = UIFactory.createUIFactory("com.kingdee.eas.base.uiframe.client.UIModelDialogFactory").create(getEditUIName(), uiContext, null, OprtState.EDIT);
		else
			uiWindow = UIFactory.createUIFactory(getEditUIModal()).create(getEditUIName(), uiContext, null, OprtState.EDIT);
		return uiWindow;
    }
	public void actionRemove_actionPerformed(ActionEvent e) throws Exception {
		checkSelected();
		ArrayList id = getSelectedIdValues();
		for(int i = 0; i < id.size(); i++){
			checkBeforeEditOrRemove("cantRemove",id.get(i).toString());
		}
		if(confirmRemove()){
			for(int i = 0; i < id.size(); i++){
				((IDepositDealBill)getBizInterface()).delete(new ObjectUuidPK(id.get(i).toString()));
			}
			FDCClientUtils.showOprtOK(this);
			this.refresh(null);
		}
	}
	protected ICoreBase getBizInterface() throws Exception {
		return DepositDealBillFactory.getRemoteInstance();
    }
	public void actionAudit_actionPerformed(ActionEvent e) throws Exception {
		checkSelected();
		ArrayList id = getSelectedIdValues();
		for(int i = 0; i < id.size(); i++){
			FDCClientUtils.checkBillInWorkflow(this, id.get(i).toString());
	    	
			if (!FDCBillStateEnum.SUBMITTED.equals(getBizState(id.get(i).toString()))) {
				FDCMsgBox.showWarning("���ݲ����ύ״̬�����ܽ�������������");
				return;
			}
			((IDepositDealBill)getBizInterface()).audit(BOSUuid.read(id.get(i).toString()));
		}
		FDCClientUtils.showOprtOK(this);
		this.refresh(null);
	}
	/**
	 * ������
	 */
	public void actionUnAudit_actionPerformed(ActionEvent e) throws Exception {
		checkSelected();
		ArrayList id = getSelectedIdValues();
		for(int i = 0; i < id.size(); i++){
			FDCClientUtils.checkBillInWorkflow(this, id.get(i).toString());
	    	
			if (!FDCBillStateEnum.AUDITTED.equals(getBizState(id.get(i).toString()))) {
				FDCMsgBox.showWarning("���ݲ�������״̬�����ܽ��з�����������");
				return;
			}
			((IDepositDealBill)getBizInterface()).unAudit(BOSUuid.read(id.get(i).toString()));
		}
		FDCClientUtils.showOprtOK(this);
		this.refresh(null);
	}
	protected void cbIsAll_actionPerformed(ActionEvent e) throws Exception {
		getTenancyBillList();
	}
	private boolean hasQyeryPK = false;
    boolean isFirstFind = true;
    private int searcheRowCount=0;
    private  String searchText=null;
    private  boolean isMatch=false;
    private  FindListEvent preFindListEvent=null;
    private  String  propertyName=null;
    //��λ��
    private FindDialog findDialog = null;
    private static String locateFirst = "Msg_LocateFirst";
    private static String locateLast = "Msg_LocateLast";
    //��ӵȴ���ʾ��
    KDDialog kddialog=null;
    protected String[] keyFieldNames;
    //��¼id
    protected String   subKeyFieldName;
    //��ʼ��������
    ListUiHelper listUiHelper=null;
    protected String[] getLocateNames()
    {
    	String[] locateNames = new String[3];
        locateNames[0] = "number";
        locateNames[1] = "tenRoomsDes";
        locateNames[2] = "tenCustomerDes";
        return locateNames;
    }
    public void actionLocate_actionPerformed(ActionEvent e) throws Exception
    {
	 	/*if(kdtTenancy.getSelectManager().get()==null){
	 		MsgBox.showWarning("����ѡ��һ��");
	 		return;
	 	}*/
	 
        searcheRowCount=0;
        searchText=null;
        preFindListEvent=null;
        Window win = SwingUtilities.getWindowAncestor(this);
        List FindPropertyName = new ArrayList();
        for (int i = 0; i < getLocateNames().length; i++)
        {
            if (kdtTenancy.getColumn(getLocateNames()[i]) != null)
            {
            	//����ʱ����Ӧ����kdtTenancy.getHeadRow(0)�������ڴ˴�Ӧ�ó���֮��Ӧ����kdtTenancy.getHeadRow(1)��
                ListFind cEnum = new ListFind(getLocateNames()[i], kdtTenancy.getHeadRow(0).getCell(getLocateNames()[i]).getValue().toString());
                FindPropertyName.add(cEnum);
            }
        }

        if (FindPropertyName.size() == 0)
        {
            return;
        }

            if (win instanceof Frame)
            {
                findDialog = new FindDialog((Frame) win, "", FindPropertyName, true);
            }
            else
            {
                findDialog = new FindDialog((Dialog) win, "", FindPropertyName, true);
            }
            findDialog.addFindListListener(new IFindListListener()
            {
                public void FindNext(FindListEvent e)
                {
                    locate(e);
                }

                /**
                 * FindClose
                 *
                 * @param e
                 *            FindListEvent
                 */
                public void FindClose(FindListEvent e)
                {
                    findDialog.dispose();
                    findDialog = null;
                    searcheRowCount=0;
                    searchText=null;
                    isMatch=false;
                }
            }

            );
        findDialog.setLocation(600, 100);
        findDialog.show();
    }

protected void locateForNotIdList(FindListEvent e)
{
int currentRow = 0;
if (this.kdtTenancy.getSelectManager().get() != null)
{
    currentRow = this.kdtTenancy.getSelectManager().get().getBeginRow();
}
int i = 0;
    if (e.getFindDeration() == FindListEvent.Down_Find)
    {
        i = currentRow + 1;
    }
    else
    {
        i = currentRow;
    }

IRow row = kdtTenancy.getRow(i);
showMsgNoIdList(row,e);
while (row != null)
{
	row = kdtTenancy.getRow(i);
    // ��������ںϵ��ݣ��򲻴���
    if (i!=-1&&i!=kdtTenancy.getRowCount()&&row.getCell(e.getPropertyName()).getValue() == null)
    {
    	if (e.getFindDeration() == FindListEvent.Down_Find)
    		i++;
    	else
    		i--;
        continue;
    }
    int curSelectIndex=this.kdtTenancy.getSelectManager().getActiveRowIndex();
    if (curSelectIndex==i)
    {
    	if (e.getFindDeration() == FindListEvent.Down_Find)
    		i++;
    	else
    		i--;
    	 continue;
    }
    row = kdtTenancy.getRow(i);
    if (row==null)
    {
    	showMsgNoIdList(row,e);
    	break;
    }
    ICell cell=kdtTenancy.getRow(i).getCell(e.getPropertyName());
    Object cellValue = cell==null?null:cell.getValue();
    String Search =cellValue==null?null:cellValue.toString().replace('!', '.');
    
    if (Search != null
            && StringUtility.isMatch(Search, e.getSearch(), e.isIsMatch(), Pattern.CASE_INSENSITIVE))
    {
        isFirstFind = false;
        searcheRowCount++;
        this.kdtTenancy.getSelectManager().select(i, 0, i, kdtTenancy.getColumnCount());
        //kdtTenancy.getRow(i).getStyleAttributes().setBackground(new Color(223,239,245));
        kdtTenancy.getLayoutManager().scrollRowToShow(i);
        break;
    }
    if (e.getFindDeration() == FindListEvent.Down_Find){
    	i++;
    }
	else{
		i--;
	}
}

}
/**
* �Ҳ���ƥ���¼ʱ������ʾ
*/
private void showMsgNoIdList(IRow row,FindListEvent e)
{
if (row == null)
{
	String hint="";
	if (e.getFindDeration() == FindListEvent.Down_Find)
	{
		if (searcheRowCount==0)
		{
		  hint=locateLast;
		}else
		{
		  hint="Msg_LocateFirst_end";
		}
	}else
	{
		if (searcheRowCount==0)
		{
			hint=locateFirst;
		}else
		{
			hint="Msg_LocateFirst_end";
		}
	}
	String msg=EASResource.getString(FrameWorkClientUtils.strResource + hint);
	if (searcheRowCount!=0)
	{
		Object[] objs = new Object[]{ new Integer(searcheRowCount)};
		msg = MessageFormat.format(msg, objs);
	}
    MsgBox.showInfo(this, msg);
    this.findDialog.show();
}
}


//�Ƿ�ʹ����ģʽԤȡ,�ڶ�λ��ʱ����ҪԤȡ
private boolean bPreFetch = true;

public boolean isHasQyeryPK()
{
    return hasQyeryPK;
}

// ��λList���λ�á�
protected void locate(FindListEvent e)
{
boolean searchResult = false;
int currentRow = 0;
// ������
int RowCount = 0;

// �������ģʽ����
// if (this.isHasQyeryPK() && !this.isLessData()) {
int curSelectIndex=this.kdtTenancy.getSelectManager().getActiveRowIndex();
if (curSelectIndex<0&&(this.kdtTenancy.getRowCount()>0||this.kdtTenancy.getRowCount3()>0))
{
	this.kdtTenancy.getSelectManager().select(0,0);
	curSelectIndex=0;
}
if (curSelectIndex<0||this.kdtTenancy.getRowCount()==0)
{
	MsgBox.showInfo(this, EASResource.getString(FrameWorkClientUtils.strResource + locateLast));
	return;
}
if ((preFindListEvent!=null&&e.getFindDeration()!=preFindListEvent.getFindDeration())
	||(searchText!=null&&!searchText.equals(e.getSearch()))
	||(propertyName!=null&&!e.getPropertyName().equals(propertyName))
	||isMatch!=e.isIsMatch())
{
	searcheRowCount=0;
	Object cellValue= null;
	try {
		bPreFetch = false;
		cellValue = kdtTenancy.getRow(curSelectIndex).getCell(e.getPropertyName()).getValue();
	}
	finally {
		bPreFetch = true;
	}
  String selectText = cellValue==null?null:cellValue.toString();
  propertyName=e.getPropertyName();
  if (selectText != null&&StringUtility.isMatch(selectText, e.getSearch(), e.isIsMatch(), Pattern.CASE_INSENSITIVE))
  {
      searcheRowCount=searcheRowCount+1;
  }
}
isMatch=e.isIsMatch();
preFindListEvent=e;
searchText=e.getSearch();
propertyName=e.getPropertyName();
if (this.isHasQyeryPK())
{
    RowCount = kdtTenancy.getBody().size();
}
else
{
    locateForNotIdList(e);
}

if (this.kdtTenancy.getSelectManager().get() != null)
{
    currentRow = this.kdtTenancy.getSelectManager().get().getBeginRow();
}
int i = 0;
if (e.getFindDeration() == FindListEvent.Down_Find)
{
   i = currentRow + 1;
}
else
{
    i = currentRow;
}


// ���û�����򲻽��д���
if (RowCount == 0)
{
    return;
}

if (e.getFindDeration() == FindListEvent.Down_Find)
{
    if (i == RowCount)
    {
    	if (searcheRowCount==0)
    	{
          MsgBox.showInfo(this, EASResource.getString(FrameWorkClientUtils.strResource + locateLast));
    	}
        else
        {
    		String msg=EASResource.getString(FrameWorkClientUtils.strResource + "Msg_LocateLast_end");
    		Object[] objs = new Object[]{ new Integer(searcheRowCount)};
    		msg = MessageFormat.format(msg, objs);
    		MsgBox.showInfo(this, msg);
    		//searcheRowCount=0;
        }
        this.findDialog.show();
    }
    else
    {
        for (; i < RowCount; i++)
        {
        	IRow row = null;
        	try {
        		bPreFetch = false;
        		row = kdtTenancy.getRow2(i);
        	}
        	finally {
        		bPreFetch = true;
        	}
        	ICell cell=(null == row ) ? null : row.getCell(e.getPropertyName());
            Object o = cell==null?null:cell.getValue();
            String Search = o == null ? null : o.toString();
            // ��������ںϵ��ݣ��򲻴���
            if (Search == null)
            {
                if (i == RowCount - 1)
                {
                    MsgBox.showInfo(this, EASResource.getString(FrameWorkClientUtils.strResource + locateLast));
                    this.findDialog.show();
                }
                continue;
            }

            if (Search != null
                    && StringUtility.isMatch(Search, e.getSearch(), e.isIsMatch(), Pattern.CASE_INSENSITIVE))
            {
                searchResult = true;
                isFirstFind = false;
                searcheRowCount++;
                this.kdtTenancy.getSelectManager().select(i, 0);
                kdtTenancy.getLayoutManager().scrollRowToShow(i);
                break;
            }
            // û���ҵ��Ͳ���ѡ�С�
            if (i == RowCount - 1)
            {
            	if (searcheRowCount==0)
            	{
            		MsgBox.showInfo(this, EASResource.getString(FrameWorkClientUtils.strResource + locateLast));
            	}else
            	{
            		String msg=EASResource.getString(FrameWorkClientUtils.strResource + "Msg_LocateLast_end");
            		Object[] objs = new Object[]{ new Integer(searcheRowCount)};
            		msg = MessageFormat.format(msg, objs);
            		MsgBox.showInfo(this, msg);
            		//searcheRowCount=0;
            	}
                this.findDialog.show();
            }
        }
    }
}
else
{
    if (i == 0)
    {
    	if (searcheRowCount==0)
    	{
    		MsgBox.showInfo(this, EASResource.getString(FrameWorkClientUtils.strResource + locateFirst));
    	}else
    	{
    		String msg=EASResource.getString(FrameWorkClientUtils.strResource + "Msg_LocateFirst_end");
    		Object[] objs = new Object[]{ new Integer(searcheRowCount)};
    		msg = MessageFormat.format(msg, objs);
    		MsgBox.showInfo(this, msg);
    		//searcheRowCount=0;
    	}
        this.findDialog.show();
    }

    for (; i > 0; i--)
    {
    	ICell cell=(null == kdtTenancy.getRow2(i - 1)) ? null : kdtTenancy.getRow2(i - 1).getCell(e.getPropertyName());
    	Object o = cell==null?null:cell.getValue();
        String Search = o == null ? null : o.toString();
        // ��������ںϵ��ݣ��򲻴���
        if (Search == null)
        {
            if (i == 1)
            {
                MsgBox.showInfo(this, EASResource.getString(FrameWorkClientUtils.strResource + locateFirst));
                this.findDialog.show();
            }
            continue;
        }


        if (Search != null
                && StringUtility.isMatch(Search, e.getSearch(), e.isIsMatch(), Pattern.CASE_INSENSITIVE))
        {
            searchResult = true;
            isFirstFind = false;
            searcheRowCount++;
            this.kdtTenancy.getSelectManager().select(i - 1, 0);
            kdtTenancy.getLayoutManager().scrollRowToShow(i - 1);
            break;
        }
        // û���ҵ��Ͳ���ѡ�С�
        if (i == 1)
        {
        	if (searcheRowCount==0)
        	{
        		MsgBox.showInfo(this, EASResource.getString(FrameWorkClientUtils.strResource + locateFirst));
        	}else
        	{
        		String msg=EASResource.getString(FrameWorkClientUtils.strResource + "Msg_LocateFirst_end");
        		Object[] objs = new Object[]{ new Integer(searcheRowCount)};
        		msg = MessageFormat.format(msg, objs);
        		MsgBox.showInfo(this, msg);
        		//searcheRowCount=0;
        	}

            this.findDialog.show();
        }
    }
}
}
}