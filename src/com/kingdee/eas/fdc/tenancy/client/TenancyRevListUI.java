/**
 * output package name
 */
package com.kingdee.eas.fdc.tenancy.client;

import java.awt.Color;
import java.awt.Dialog;
import java.awt.Frame;
import java.awt.Window;
import java.awt.event.*;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

import javax.swing.JButton;
import javax.swing.SwingUtilities;
import javax.swing.event.ChangeEvent;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.tree.TreeNode;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.metadata.IMetaDataPK;
import com.kingdee.bos.metadata.MetaDataPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.metadata.entity.SorterItemCollection;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.bos.metadata.query.QueryFieldInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.metadata.resource.BizEnumValueInfo;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.IUIFactory;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.bos.ctrl.kdf.data.impl.ICrossPrintDataProvider;
import com.kingdee.bos.ctrl.kdf.servertable.KDTStyleConstants;
import com.kingdee.bos.ctrl.kdf.table.ICell;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTDataRequestManager;
import com.kingdee.bos.ctrl.kdf.table.KDTIndexColumn;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectManager;
import com.kingdee.bos.ctrl.kdf.table.event.KDTDataRequestEvent;
import com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent;
import com.kingdee.bos.ctrl.kdf.table.event.KDTSelectEvent;
import com.kingdee.bos.ctrl.kdf.table.foot.KDTFootManager;
import com.kingdee.bos.ctrl.kdf.table.util.KDTableUtil;
import com.kingdee.bos.ctrl.kdf.util.style.Styles.HorizontalAlignment;
import com.kingdee.bos.ctrl.report.forapp.kdnote.client.KDNoteHelper;
import com.kingdee.bos.ctrl.swing.KDDialog;
import com.kingdee.bos.ctrl.swing.KDMenuItem;
import com.kingdee.bos.ctrl.swing.KDWorkButton;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.dao.query.BizEnumValueDTO;
import com.kingdee.bos.dao.query.IQueryExecutor;
import com.kingdee.bos.framework.DynamicObjectFactory;
import com.kingdee.eas.base.codingrule.CodingRuleManagerFactory;
import com.kingdee.eas.base.codingrule.ICodingRuleManager;
import com.kingdee.eas.basedata.org.OrgUnitInfo;
import com.kingdee.eas.basedata.org.SaleOrgUnitInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basecrm.FDCReceivingBillCollection;
import com.kingdee.eas.fdc.basecrm.FDCReceivingBillEntryCollection;
import com.kingdee.eas.fdc.basecrm.FDCReceivingBillEntryInfo;
import com.kingdee.eas.fdc.basecrm.FDCReceivingBillFactory;
import com.kingdee.eas.fdc.basecrm.FDCReceivingBillInfo;
import com.kingdee.eas.fdc.basecrm.IFDCReceivingBill;
import com.kingdee.eas.fdc.basecrm.RevBillStatusEnum;
import com.kingdee.eas.fdc.basecrm.RevBillTypeEnum;
import com.kingdee.eas.fdc.basecrm.RevBizTypeEnum;
import com.kingdee.eas.fdc.basecrm.client.CRMClientHelper;
import com.kingdee.eas.fdc.basecrm.client.FDCReceivingBillEditUI;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.MoneySysTypeEnum;
import com.kingdee.eas.fdc.basedata.client.FDCClientUtils;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.merch.common.KDTableHelper;
import com.kingdee.eas.fdc.sellhouse.MoneyDefineFactory;
import com.kingdee.eas.fdc.sellhouse.MoneyDefineInfo;
import com.kingdee.eas.fdc.sellhouse.PurchaseManageInfo;
import com.kingdee.eas.fdc.sellhouse.SellProjectInfo;
import com.kingdee.eas.fdc.sellhouse.SignManageFactory;
import com.kingdee.eas.fdc.sellhouse.SignManageInfo;
import com.kingdee.eas.fdc.sellhouse.client.FDCTreeHelper;
import com.kingdee.eas.fdc.sellhouse.client.PurchaseManageEditUI;
import com.kingdee.eas.fdc.sellhouse.client.SHEHelper;
import com.kingdee.eas.fdc.tenancy.HandleStateEnum;
import com.kingdee.eas.fdc.tenancy.IInvoiceBill;
import com.kingdee.eas.fdc.tenancy.QuitTenancyFactory;
import com.kingdee.eas.fdc.tenancy.RentStartTypeEnum;
import com.kingdee.eas.fdc.tenancy.TenancyBillCollection;
import com.kingdee.eas.fdc.tenancy.TenancyBillFactory;
import com.kingdee.eas.fdc.tenancy.TenancyBillInfo;
import com.kingdee.eas.fdc.tenancy.TenancyBillStateEnum;
import com.kingdee.eas.fdc.tenancy.TenancyContractTypeEnum;
import com.kingdee.eas.fdc.tenancy.TenancyDisPlaySetting;
import com.kingdee.eas.fdc.tenancy.TenancyHelper;
import com.kingdee.eas.fdc.tenancy.TenancyRoomEntryCollection;
import com.kingdee.eas.fdc.tenancy.TenancyRoomEntryInfo;
import com.kingdee.eas.fi.cas.IReceivingBill;
import com.kingdee.eas.fi.cas.PaymentBillCollection;
import com.kingdee.eas.fi.cas.PaymentBillFactory;
import com.kingdee.eas.fi.cas.ReceivingBillCollection;
import com.kingdee.eas.fi.cas.ReceivingBillFactory;
import com.kingdee.eas.fi.cas.client.CasPaymentBillUI;
import com.kingdee.eas.fi.cas.client.CasReceivingBillUI;
import com.kingdee.eas.fi.cas.client.PaymentBillUI;
import com.kingdee.eas.fi.cas.client.ReceivingBillUI;
import com.kingdee.eas.framework.*;
import com.kingdee.eas.framework.client.FindDialog;
import com.kingdee.eas.framework.client.FindListEvent;
import com.kingdee.eas.framework.client.FrameWorkClientUtils;
import com.kingdee.eas.framework.client.IFindListListener;
import com.kingdee.eas.framework.client.ListFind;
import com.kingdee.eas.framework.client.ListUiHelper;
import com.kingdee.eas.framework.util.StringUtility;
import com.kingdee.eas.tools.datatask.DatataskMode;
import com.kingdee.eas.tools.datatask.DatataskParameter;
import com.kingdee.eas.tools.datatask.client.DatataskCaller;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.MsgBox;
import com.kingdee.jdbc.rowset.IRowSet;
import com.kingdee.util.StringUtils;

/**
 * output class name
 */
public class TenancyRevListUI extends AbstractTenancyRevListUI
{
    private static final Logger logger = CoreUIObject.getLogger(TenancyRevListUI.class);
    TenancyDisPlaySetting setting = new TenancyDisPlaySetting();
    public TenancyRevListUI() throws Exception
    {
        super();
    }
    public void onLoad() throws Exception {
		this.actionBatchReceiving.setVisible(true);

		super.onLoad();
		this.actionBatchReceiving.setEnabled(false);// ֻ�е���¼��֯Ϊʵ��������֯����ѡ��������Ŀ�ڵ�ʱ
													// �ſɵ����������
		actionTDPrint.setVisible(true);
		actionTDPrint.setEnabled(true);
		actionTDPrintPreview.setVisible(true);
		actionTDPrintPreview.setEnabled(true);
		tblMain.getSelectManager().setSelectMode(
				KDTSelectManager.MULTIPLE_ROW_SELECT);

		this.actionTraceDown.setVisible(true);
		this.actionTraceDown.setEnabled(true);
		// this.actionDelVoucher.setVisible(true);
		// this.actionUnAudit.setEnabled(true);
		// this.actionCanceReceive.setEnabled(true);
		// this.actionUpdateSubject.setEnabled(true);
		this.actionAuditResult.setVisible(false);
		this.actionWorkFlowG.setVisible(false);

		this.actionAdjust.setVisible(false);
		this.treeMain.setSelectionRow(0);
//		this.btnReceipt.setVisible(false);
//		this.btnClearInvoice.setVisible(false);
//		this.btnRetakeReceipt.setVisible(false);
//		this.btnCreateInvoice.setVisible(false);
		
		this.actionQuery.setVisible(false);
		this.kdtTenancy.setEnabled(false);
		this.kdtTenancy.getSelectManager().setSelectMode(KDTSelectManager.ROW_SELECT);
		
		this.btnCreateBill.setIcon(EASResource.getIcon("imgTbtn_notice"));	
		
		this.actionBatchReceiving.setVisible(false);
		this.actionReceive.setVisible(false);
		this.actionUpdateSubject.setVisible(false);
		this.actionVoucher.setVisible(false);
		this.actionDelVoucher.setVisible(false);
		this.actionTDPrint.setVisible(false);
		this.actionTDPrintPreview.setVisible(false);
		this.actionCreateBill.setVisible(false);
		this.actionCanceReceive.setVisible(false);
		
		this.actionReceipt.setVisible(false);
		this.actionRetakeReceipt.setVisible(false);
		this.actionCreateInvoice.setVisible(false);
		this.actionClearInvoice.setVisible(false);
		
		CRMClientHelper.changeTableNumberFormat(tblMain, new String[]{"entries.revAmount"});
		
		this.tblMain.getColumn("isCreateBill").getStyleAttributes().setHided(true);
		
		this.btnRefundment.setIcon(EASResource.getIcon("imgTbtn_refuse"));
		
		String[] fields=new String[this.kdtTenancy.getColumnCount()];
		for(int i=0;i<this.kdtTenancy.getColumnCount();i++){
			fields[i]=this.kdtTenancy.getColumnKey(i);
		}
		KDTableHelper.setSortedColumn(this.kdtTenancy,fields);
		
		this.tblMain.getDataRequestManager().setDataRequestMode(KDTDataRequestManager.REAL_MODE);
	}
    protected void afterTableFillData(KDTDataRequestEvent e) {
		super.afterTableFillData(e);
		CRMClientHelper.getFootRow(tblMain, new String[]{"entries.revAmount"});
	}
	public void actionTraceDown_actionPerformed(ActionEvent e) throws Exception {
		checkSelected();
		int rowIndex = this.tblMain.getSelectManager().getActiveRowIndex();
		IRow row = this.tblMain.getRow(rowIndex);
		String id = (String) row.getCell(this.getKeyFieldName()).getValue();
		ReceivingBillCollection col=ReceivingBillFactory.getRemoteInstance().getReceivingBillCollection("select id from where sourceBillId='"+id+"'");
		if(col.size()>0){
			UIContext uiContext = new UIContext(this);
			uiContext.put("ID", col.get(0).getId().toString());
	        IUIFactory uiFactory = UIFactory.createUIFactory(UIFactoryName.MODEL);
	        IUIWindow uiWindow = uiFactory.create(CasReceivingBillUI.class.getName(), uiContext,null,OprtState.VIEW);
	        uiWindow.show();
	        return;
		}
		PaymentBillCollection paycol=PaymentBillFactory.getRemoteInstance().getPaymentBillCollection("select id from where sourceBillId='"+id+"'");
		if(paycol.size()>0){
			UIContext uiContext = new UIContext(this);
			uiContext.put("ID",  paycol.get(0).getId().toString());
	        IUIFactory uiFactory = UIFactory.createUIFactory(UIFactoryName.MODEL);
	        IUIWindow uiWindow = uiFactory.create(CasPaymentBillUI.class.getName(), uiContext,null,OprtState.VIEW);
	        uiWindow.show();
	        return;
		}
		FDCMsgBox.showInfo(this,"Ŀ�굥��Ϊ�գ�");
	}

	protected void setColGroups() {
		setColGroup("number");
		setColGroup("currency.name");
		setColGroup("amount");
		setColGroup("originalAmount");
		setColGroup("billStatus");
		setColGroup("revBillType");
		setColGroup("revBizType");
		setColGroup("customer.name");
		setColGroup("description");
		setColGroup("accountBank.name");
		setColGroup("revAccount.name");
		setColGroup("bank.name");
		setColGroup("settlementType.name");
		setColGroup("settlementNumber");
		setColGroup("creator.name");
		setColGroup("createTime");
		setColGroup("auditor.name");
		setColGroup("auditTime");
		setColGroup("isCreateBill");
	}

	protected IQueryExecutor getQueryExecutor(IMetaDataPK queryPK,
			EntityViewInfo viewInfo) {
		FilterInfo filter = new FilterInfo();
//		Set set = new HashSet();
//		set.add(RevBizTypeEnum.TENANCY_VALUE);
//		set.add(RevBizTypeEnum.OBLIGATE_VALUE);
//		filter.getFilterItems().add(
//				new FilterItemInfo("revBizType", set, CompareType.INCLUDE));
		// viewInfo = (EntityViewInfo) mainQuery.clone();
		// viewInfo.setFilter(filter);
		// if (viewInfo.getFilter() != null) {
		// try {
		// viewInfo.getFilter().mergeFilter(filter, "and");
		// } catch (BOSException e) {
		// this.handleException(e);
		// this.abort();
		// }
		// } else {
		// viewInfo.setFilter(filter);
		// }
//		IQueryExecutor queryExe = super.getQueryExecutor(queryPK, new EntityViewInfo());
		IQueryExecutor queryExe = super.getQueryExecutor(queryPK, viewInfo);
		EntityViewInfo view = queryExe.getObjectView();
		int rowIndex = this.kdtTenancy.getSelectManager().getActiveRowIndex();
		String id="'null'";
		if(rowIndex>=0){
			IRow row = this.kdtTenancy.getRow(rowIndex);
			id = (String) row.getCell("id").getValue();
		}
		filter.getFilterItems().add(new FilterItemInfo("tenancyObj.id",id));
		try {
			view.getFilter().mergeFilter(filter, "and");
		} catch (BOSException e) {
			this.handleException(e);
			this.abort();
		}
//		if (view.getFilter() != null) {
//			try {
//				view.getFilter().mergeFilter(filter, "and");
//			} catch (BOSException e) {
//				this.handleException(e);
//				this.abort();
//			}
//		} else {
////			view.setFilter(filter);
//			view.setFilter(new FilterInfo());
//		}
////		queryExe.setObjectView(view);
//		queryExe.setObjectView( new EntityViewInfo());
		
		return queryExe;
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
		super.setUITitle("�����տ��ʱ��");
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
				try {
					TenancyBillInfo  tenBill = TenancyBillFactory.getRemoteInstance().getTenancyBillInfo(new ObjectUuidPK(id),sels);
					uiContext.put(FDCReceivingBillEditUI.KEY_REV_BIZ_TYPE, RevBizTypeEnum.tenancy);
			    	uiContext.put(FDCReceivingBillEditUI.KEY_REV_BILL_TYPE, RevBillTypeEnum.gathering);
			    	uiContext.put(FDCReceivingBillEditUI.KEY_SELL_PROJECT, tenBill.getSellProject());
			    	uiContext.put(FDCReceivingBillEditUI.KEY_TENANCY_BILL, tenBill);
			    	uiContext.put(FDCReceivingBillEditUI.KEY_IS_LOCK_BILL_TYPE, Boolean.TRUE);
				} catch (EASBizException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (BOSException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}else{
				FDCMsgBox.showWarning(this,"��ѡ���У�");
				this.abort();
			}
		}
		super.prepareUIContext(uiContext, e);
	}

	public void actionReceive_actionPerformed(ActionEvent e) throws Exception {
		super.actionReceive_actionPerformed(e);
	}

	protected String getEditUIName() {
		return TENReceivingBillEditUI.class.getName();
	}

	protected MoneySysTypeEnum getSystemType() {
		return MoneySysTypeEnum.TenancySys;
	}

	/**
	 * �ж��տ�Ƿ������˱������ ���û��������ʹ�������տ�
	 */
	private void isCancelCodingRule() throws Exception {
		OrgUnitInfo crrOu = SysContext.getSysContext().getCurrentSaleUnit(); // ��ǰ������֯
																				// ;
		if (crrOu == null) {
			crrOu = SysContext.getSysContext().getCurrentOrgUnit(); // ��ǰ��֯
		}
		ICodingRuleManager iCodingRuleManager = CodingRuleManagerFactory
				.getRemoteInstance();
		FDCReceivingBillInfo info = new FDCReceivingBillInfo();
		// info.setId(BOSUuid.create("9412AC80"));
		if (!iCodingRuleManager.isExist(info, crrOu.getId().toString())) {
			MsgBox.showError("��û�����ñ�����򣬲��������տ");
			abort();
		}
		//if(!iCodingRuleManager.isUseIntermitNumber(info,crrOu.getId().toString
		// ())){
		// MsgBox.showError("û�����ò�����Ϻţ����������տ");
		// abort();
		// }
	}

	public void actionBatchReceiving_actionPerformed(ActionEvent e)
			throws Exception {
		isCancelCodingRule();
		DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeMain
				.getLastSelectedPathComponent();
		IUIFactory uiFactory = null;
		UIContext uiContext = new UIContext(this);
		uiContext.put("sellProject", node.getUserObject());
		uiFactory = UIFactory.createUIFactory(UIFactoryName.NEWWIN);
		IUIWindow curDialog = uiFactory.create(TENBatchReceivingUI.class
				.getName(), uiContext, null, OprtState.ADDNEW);
		curDialog.show();

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

	public void actionUpdateSubject_actionPerformed(ActionEvent e)
			throws Exception {
		checkSelected();
		checkUpdateSubject();
		ArrayList list = getSelectedValues("id");// ��ȡ����ѡ���е�ID �տ�Ͼ�Ȼ û�аѷ�¼ID
													// ������...ֻ�й�ȥ���²���ˡ�����
		HashSet set = new HashSet(list);
		int[] indexs = KDTableUtil.getSelectedRows(tblMain);
		int count = 0;
		if (indexs[0] != -1)
			count = indexs[0];
		String revBillType = tblMain.getRow(count).getCell("revBillType")
				.getValue().toString();
		IUIFactory uiFactory = null;
		UIContext uiContext = new UIContext(this);
		uiContext.put("list", set);
		uiContext.put("revBillType", revBillType);// ��������
		uiContext.put("sysType", getSystemType());
		uiFactory = UIFactory.createUIFactory(UIFactoryName.NEWWIN);
		IUIWindow curDialog = uiFactory.create(UpdateSubjectUI.class.getName(),
				uiContext, null, OprtState.ADDNEW);
		curDialog.show();
	}

	private void checkUpdateSubject() {
		int[] indexs = KDTableUtil.getSelectedRows(tblMain);
		int count = 0;
		if (indexs[0] != -1)
			count = indexs[0];
		if (tblMain.getRow(count).getCell("revBillType").getValue() == null) {
			MsgBox.showInfo("�տ������Ϊ�գ����ܽ��п�Ŀά��!");
			this.abort();
		} else {
			String revBillType = tblMain.getRow(count).getCell("revBillType")
					.getValue().toString();
			for (int i = 0; i < indexs.length; i++) {
				IRow row = tblMain.getRow(count);
				Boolean b = Boolean.valueOf(row.getCell("fiVouchered")
						.getValue().toString());
				if (!revBillType.equals(row.getCell("revBillType").getValue()
						.toString())) {
					MsgBox.showWarning("ѡ��ĵ������Ͳ�һ�£����ܽ��п�Ŀά����");
					abort();
				}
				if (b.booleanValue()) {
					MsgBox.showWarning("ѡ���а�����������ƾ֤�ĵ���,���ܽ��п�Ŀά����");
					abort();
				}
			}
		}
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
			this.actionBatchReceiving.setEnabled(false);
			this.actionUpdateSubject.setEnabled(false);
			this.actionVoucher.setEnabled(false);
			this.actionDelVoucher.setEnabled(false);
			this.actionReceive.setEnabled(false);
			this.actionCanceReceive.setEnabled(false);
		} else {
			this.actionAddNew.setEnabled(false);
			this.actionEdit.setEnabled(true);
			this.actionRemove.setEnabled(true);
			this.actionAudit.setEnabled(true);
			this.actionUnAudit.setEnabled(true);
			this.actionVoucher.setEnabled(true);
			this.actionDelVoucher.setEnabled(true);
			this.actionReceive.setEnabled(true);
			this.actionCanceReceive.setEnabled(true);
			this.actionBatchReceiving.setEnabled(false);
			this.actionUpdateSubject.setEnabled(false);
			if (node.getUserObject() instanceof SellProjectInfo) {
				this.actionAddNew.setEnabled(true);
				this.actionBatchReceiving.setEnabled(true);
				this.actionUpdateSubject.setEnabled(true);
				this.actionView.setEnabled(true);
			}
		}
		// if (node.getUserObject() instanceof SellProjectInfo) {
		// if (SHEHelper.getCurrentSaleOrg().isIsBizUnit()) {
		// this.actionBatchReceiving.setEnabled(true);
		// } else {
		// this.actionBatchReceiving.setEnabled(false);
		// }
		// } else {
		// this.actionBatchReceiving.setEnabled(false);
		// }
		//modify by warship at 2010/09/07 ������������
		//this.execQuery();
		//this.refresh(null);
		getTenancyBillList();
	}
	private void getTenancyBillList(){
		DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeMain.getLastSelectedPathComponent();
		EntityViewInfo vi = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
//		filter.getFilterItems().add(new FilterItemInfo("tenancyState",TenancyBillStateEnum.AUDITED_VALUE));
		filter.getFilterItems().add(new FilterItemInfo("tenancyState",TenancyBillStateEnum.EXECUTING_VALUE));
		filter.getFilterItems().add(new FilterItemInfo("tenancyState",TenancyBillStateEnum.CONTINUETENANCYING_VALUE));
		filter.getFilterItems().add(new FilterItemInfo("tenancyState",TenancyBillStateEnum.QuitTenancying));
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
	public void actionEdit_actionPerformed(ActionEvent e) throws Exception {
		int rowIndex = this.tblMain.getSelectManager().getActiveRowIndex();
		if (rowIndex == -1) {
			MsgBox.showInfo("��ѡ����!");
			return;
		}
		IRow row = this.tblMain.getRow(rowIndex);
		BizEnumValueInfo revBillTypeInfo = (BizEnumValueInfo) row.getCell(
				"revBillType").getValue();
		if (RevBillTypeEnum.TRANSFER_VALUE.equals(revBillTypeInfo.getValue())) {
			MsgBox.showInfo("ת��������޸�");
			this.abort();
		}

		String id = (String) row.getCell("id").getValue();
		if (id == null) {
			MsgBox.showInfo("��idΪ��!");
			return;
		}
		checkIsSupply(id);
		checkCreateBillStatus(id,"��ѡ�տ�������ɳ����ո�����������޸Ĳ�����");
		super.actionEdit_actionPerformed(e);
	}

	public void actionRemove_actionPerformed(ActionEvent e) throws Exception {
		checkSelected();
		SelectorItemCollection sels = new SelectorItemCollection();
		sels.add("id");
		sels.add("billStatus");
		ArrayList id = getSelectedIdValues();
		for(int i = 0; i < id.size(); i++){
			checkIsSupply(id.get(i).toString());
			checkCreateBillStatus(id.get(i).toString(),"��ѡ�տ�������ɳ����ո����������ɾ��������");
			
			FDCReceivingBillInfo rev = FDCReceivingBillFactory.getRemoteInstance().getFDCReceivingBillInfo(new ObjectUuidPK(BOSUuid.read(id.get(i).toString())),sels);
			if(!RevBillStatusEnum.SUBMIT.equals(rev.getBillStatus())){
				MsgBox.showInfo("�ύ״̬�ĵ��ݲ���ɾ����");
				this.abort();
			}
		}
		if(confirmRemove()){
			for(int i = 0; i < id.size(); i++){
				FDCReceivingBillFactory.getRemoteInstance().delete(BOSUuid.read(id.get(i).toString()), getHandleClazzName());
			}
			FDCClientUtils.showOprtOK(this);
			this.refresh(null);
		}
	}

	// �ж��Ƿ��Ǵ���
	private void checkIsSupply(String id) throws BOSException, EASBizException {
		SelectorItemCollection sels = new SelectorItemCollection();
		sels.add("id");
		sels.add("srcRevBill.id");
		FDCReceivingBillInfo rev = FDCReceivingBillFactory.getRemoteInstance()
				.getFDCReceivingBillInfo(new ObjectUuidPK(id), sels);
		if (rev.getSrcRevBill() != null) {
			MsgBox.showInfo(this, "���ڴ��յ��տ,�������޸Ļ�ɾ������ȥ�޸Ļ�ɾ��Դ���ݣ�");
			this.abort();
		}
	}

	protected String getHandleClazzName() {
		return "com.kingdee.eas.fdc.tenancy.app.TenRevHandle";
	}

	protected CoreBillBaseCollection getNewBillList() throws Exception {
		return super.getNewBillList();
	}

	/**
	 * �״�
	 */
	public void actionTDPrint_actionPerformed(ActionEvent e) throws Exception {
		List fdcIdList = this.getSelectedIdValues();
		for (int i = 0; i < fdcIdList.size(); i++) {
			String receiveID = (String) fdcIdList.get(i);
			TDprint(false, receiveID);
			refresh(e);
		}
	}

	/**
	 *�״�Ԥ��
	 */
	public void actionTDPrintPreview_actionPerformed(ActionEvent e)
			throws Exception {
		List fdcIdList = this.getSelectedIdValues();
		for (int i = 0; i < fdcIdList.size(); i++) {
			String receiveID = (String) fdcIdList.get(i);
			TDprint(true, receiveID);
			refresh(e);
		}
	}

	/**
	 * 
	 * @param ofNot
	 *            trueΪ�״�Ԥ�� falseΪ�״�
	 * @throws IOException
	 */
	public void TDprint(boolean ofNot, String receiveID) throws IOException {
		checkSelected();
		List idList = new ArrayList();
		idList.add(receiveID);
		if (idList == null || idList.size() == 0)
			return;
		if (setting.getIsMoneyDeifine()) {
			KDNoteHelper appHlp = new KDNoteHelper();

			Map moneyMap = setting.getMoneyMap();
			Set moneySet = moneyMap.keySet();
			ICrossPrintDataProvider[] datas = new ICrossPrintDataProvider[moneySet
					.size()];
			String[] templatePaths = new String[moneySet.size()];
			// �����տ��Ӧ���������������������ƥ����Ѱ�Ҷ�Ӧ��ģ��
			FDCReceivingBillCollection fdcc = null;
			try {
				Set set = new HashSet();
				for (int i = 0; i < idList.size(); i++) {
					set.add(idList.get(i));
				}
				EntityViewInfo view = new EntityViewInfo();
				FilterInfo filter = new FilterInfo();
				filter.getFilterItems().add(
						new FilterItemInfo("id", set, CompareType.INCLUDE));
				view.setFilter(filter);
				SelectorItemCollection sic = new SelectorItemCollection();
				sic.add("*");
				sic.add("entries.*");
				sic.add("entries.moneyDefine.id");
				sic.add("entries.moneyDefine.name");
				sic.add("entries.moneyDefine.number");
				view.setSelector(sic);
				IFDCReceivingBill ifdc = FDCReceivingBillFactory
						.getRemoteInstance();
				fdcc = ifdc.getFDCReceivingBillCollection(view);
			} catch (BOSException e) {
				e.printStackTrace();
			}
			List moneyNameList = new ArrayList();
			for (int j = 0; j < fdcc.size(); j++) {
				FDCReceivingBillEntryCollection fdce = fdcc.get(j).getEntries();
				for (int k = 0; k < fdce.size(); k++) {
					FDCReceivingBillEntryInfo fdceInfo = fdce.get(k);
					if (fdceInfo.getMoneyDefine() != null) {
						moneyNameList.add(fdceInfo.getMoneyDefine().getId()
								.toString());
					}
				}
			}

			// ������Ҫ�޸ģ��տ�״�Ԫ���ݱ����Ƕ�Ԫ���ݡ���Ϊ�����¼�ж����Ļ�ͷ�ͻ��ӡ�ظ�
			List moneySettingList = new ArrayList();
			Iterator iter = moneySet.iterator();
			int k = 0;
			while (iter.hasNext()) {
				MoneyDefineInfo moneyInfo = (MoneyDefineInfo) iter.next();
				moneySettingList.add(moneyInfo.getId().toString());
				if (moneyNameList.contains(moneyInfo.getId().toString())) {
					k++;
					datas[k] = new FDCCrossDuoCurrency(idList, getTDQueryPK(),
							moneyInfo.getId().toString(), "moneyDefine");
					templatePaths[k] = (String) moneyMap.get(moneyInfo);
				}
			}

			String moneySetID = equlasList(moneyNameList, moneySettingList);
			if (moneySetID.length() > 0) {
				MsgBox.showInfo("���� " + moneySetID
						+ "û���ҵ���Ӧ���״�ģ�棬�뵽����������ȥ���ÿ����Ӧģ��");
				this.abort();
			}
			int endLength = 0;
			for (int i = 0; i < datas.length; i++) {
				if (datas[i] != null) {
					endLength++;
				}
			}
			List newDatasList = new ArrayList();
			List newTemplatePathsList = new ArrayList();
			for (int i = 0; i < datas.length; i++) {
				if (datas[i] != null) {
					newDatasList.add(datas[i]);
					newTemplatePathsList.add(templatePaths[i]);
				}
			}

			ICrossPrintDataProvider[] newDatas = new ICrossPrintDataProvider[endLength];
			String[] newTemplatePaths = new String[endLength];
			for (int i = 0; i < newDatasList.size(); i++) {
				newDatas[i] = (ICrossPrintDataProvider) newDatasList.get(i);
				newTemplatePaths[i] = (String) newTemplatePathsList.get(i);
			}
			if (newTemplatePaths.length > 0 && newTemplatePaths[0] != null) {
				appHlp.crossPrint(newTemplatePaths, newDatas, ofNot,
						SwingUtilities.getWindowAncestor(this));
			} else {
				MsgBox.showWarning("�޴˿������͵�ģ�壬�޷������״�,�뵽����������ȥ���ÿ����Ӧģ�棡");
				SysUtil.abort();
			}
		} else {
			FDCCrossDuoCurrency data = new FDCCrossDuoCurrency(
					idList,
					new MetaDataPK(
							"com.kingdee.eas.fdc.tenancy.app.TENReceivingBillTDQuery"),
					"receivePrint");
			com.kingdee.bos.ctrl.report.forapp.kdnote.client.KDNoteHelper appHlp = new com.kingdee.bos.ctrl.report.forapp.kdnote.client.KDNoteHelper();
			// ���Ϊ�������״�Ԥ��
			if (ofNot) {
				appHlp.printPreview("/bim/fdc/tenancy/receivingBill/receiving",
						data, javax.swing.SwingUtilities
								.getWindowAncestor(this));
			} else {
				appHlp.print("/bim/fdc/tenancy/receivingBill/receiving", data,
						javax.swing.SwingUtilities.getWindowAncestor(this),
						false);
			}
		}
	}

	public String equlasList(List moneyList, List moneySettingList) {
		StringBuffer moneyNameID = new StringBuffer();
		Set set = new HashSet();
		for (int i = 0; i < moneyList.size(); i++) {
			boolean boo = false;
			String moneyID = (String) moneyList.get(i);
			for (int j = 0; j < moneySettingList.size(); j++) {
				String moneySettingId = (String) moneySettingList.get(j);
				if (moneyID.equals(moneySettingId)) {
					boo = true;
				}
			}
			if (!boo) {
				set.add(moneyID);
			}
		}
		for (Iterator iter = set.iterator(); iter.hasNext();) {
			String ID = (String) iter.next();
			try {
				if (moneyNameID.length() == 0) {

					String moneyName = ((MoneyDefineInfo) MoneyDefineFactory
							.getRemoteInstance().getMoneyDefineInfo(
									new ObjectUuidPK(ID))).getName();
					moneyNameID.append(moneyName);
				} else {
					moneyNameID.append(",");
					String moneyName = ((MoneyDefineInfo) MoneyDefineFactory
							.getRemoteInstance().getMoneyDefineInfo(
									new ObjectUuidPK(ID))).getName();
					moneyNameID.append(moneyName);
				}
			} catch (EASBizException e) {
				e.printStackTrace();
			} catch (BOSException e) {
				e.printStackTrace();
			}
		}
		return moneyNameID.toString();
	}

	protected String getTDFileName() {
		return "/bim/fdc/tenancy/";
	}

	protected IMetaDataPK getTDQueryPK() {
		return new MetaDataPK(
				"com.kingdee.eas.fdc.tenancy.app.TENReceivingBillTDQuery");
	}

	protected boolean isFootVisible() {
		return false;
	}

	protected IRow appendFootRow() {
		if (!this.isFootVisible()
		// ������ListUI��appendFootRow()�������� ����Ĭ�Ϸ���(�˽���û�й��˽���)���ж��Ƿ���ʾ�ϼ��� eric_wang
		// 2010.08.25
		// &&!isSolutionSumVisble()
		)
			return null;
		Object footVisible = this.getUIContext().get(
				IFWClientConst.TABLESUM_VISIBLE);
		if (footVisible != null
				&& !Boolean.valueOf(footVisible.toString()).booleanValue()) {
			return null;
		}
		try {
			List fieldSumList = this.getFieldSumList();

			if (fieldSumList.size() > 0) {
				// �����м���
				QueryFieldInfo fieldInfo[] = new QueryFieldInfo[fieldSumList
						.size()];
				System.arraycopy(fieldSumList.toArray(), 0, fieldInfo, 0,
						fieldSumList.size());
				IQueryExecutor iexec = getQueryExecutor(this.mainQueryPK,
						this.mainQuery);

				IRowSet singleRowSet = iexec.sum(fieldInfo);

				if (singleRowSet == null)
					return null;
				singleRowSet.next();
				// ���ɼ�����
				IRow footRow = null;
				KDTFootManager footRowManager = tblMain.getFootManager();
				if (footRowManager == null) {
					String total = EASResource
							.getString(FrameWorkClientUtils.strResource
									+ "Msg_Total");
					footRowManager = new KDTFootManager(this.tblMain);
					footRowManager.addFootView();
					this.tblMain.setFootManager(footRowManager);
					footRow = footRowManager.addFootRow(0);
					footRow.getStyleAttributes().setHorizontalAlign(
							HorizontalAlignment.getAlignment("right"));
					this.tblMain.getIndexColumn().setWidthAdjustMode(
							KDTIndexColumn.WIDTH_MANUAL);
					this.tblMain.getIndexColumn().setWidth(30);
					footRowManager.addIndexText(0, total);
				} else {
					footRow = footRowManager.getFootRow(0);
				}
//				String colFormat = "%{0.##########}f";
				String colFormat = "%{#,###.00}f";
				int columnCount = this.tblMain.getColumnCount();
				for (int c = 0; c < columnCount; c++) {
					String fieldName = this.tblMain.getColumn(c).getFieldName();
					for (int i = 0; i < fieldSumList.size(); i++) {
						QueryFieldInfo info = (QueryFieldInfo) fieldSumList
								.get(i);
						String name = info.getName();
						if (name.equalsIgnoreCase(fieldName)) {
							ICell cell = footRow.getCell(c);
							cell.getStyleAttributes()
									.setNumberFormat(colFormat);
							cell.getStyleAttributes().setHorizontalAlign(
									HorizontalAlignment.getAlignment("right"));
							cell.getStyleAttributes().setFontColor(Color.BLACK);

							if ("amount".equals(name)) {
								cell.setValue(singleRowSet
										.getBigDecimal("entries.revAmount"));
							} else {
								cell.setValue(singleRowSet.getBigDecimal(name));
							}
						}
					}
				}
				footRow.getStyleAttributes().setBackground(
						new Color(0xf6, 0xf6, 0xbf));
				return footRow;
			}
		} catch (Exception E) {
			E.printStackTrace();
			logger.error(E);
		}
		return null;
	}
	private void checkBillType(){
		int index=this.tblMain.getSelectManager().getActiveRowIndex();
		
		if(index==-1){
			return;
		}
		
		IRow row = this.tblMain.getRow(index);
		
		if(row ==null){
			return;
		}
		
		BizEnumValueDTO bizDTO=(BizEnumValueDTO)row.getCell("revBillType").getValue();
		
		if(bizDTO.getName().equals("transfer")){
			FDCMsgBox.showWarning(this, "ת�����͵��տ�������ɳ����ո��!");
			SysUtil.abort();
		}/*else if(bizDTO.getName().equals("adjust")){
			FDCMsgBox.showWarning(this, "�������͵��տ�������ɳ����ո��!");
			SysUtil.abort();
		}*/
	}
	public void actionCreateBill_actionPerformed(ActionEvent e) throws Exception {
		
		checkBillType();
		ArrayList idList = this.getSelectedIdValues();
		
		if(idList!=null && idList.size()>1){
			MsgBox.showWarning(this,"��ѡ�������ݽ��д˲�����");
			SysUtil.abort();
		}else{
				if(idList.size()==0 && tblMain.getSelectManager().getActiveRowIndex()==-1){
					idList.clear();
					IRow row = tblMain.getRow(0);
					if(row==null){
						return;
					}
					String id = row.getCell("id").getValue().toString();
					idList.add(id);
				}
				
				checkBillStatusForCreate(idList);
				isCreateVouchered(idList);
				boolean res = isCreateBill(idList);
				if(!res){
					
					boolean result = findBillFromCasPaymentBillbyId(idList);
					if(!result){
						MsgBox.showWarning(this,"�Ѿ����ɳ����ո�����������ظ����ɣ�");
						SysUtil.abort();
					}
				}
				try{
					FDCReceivingBillFactory.getRemoteInstance().createCashBill(idList, false);
					MsgBox.showWarning(this, "�����ո�����ɳɹ���");
					SysUtil.abort();
				}catch(Exception ex){
					if(ex instanceof BOSException&&ex.getMessage().startsWith("��Ŀ����ָ���ұ����")){
						MsgBox.showWarning(this, "��Ŀ����ָ���ұ���㣬��ѡ�������ұ�");
						SysUtil.abort();
					}else if(ex instanceof BOSException&&ex.getMessage().startsWith("���ɳ����ո��ʧ��")){
						MsgBox.showWarning(this, "���ɳ����ո��ʧ�ܣ�");
						SysUtil.abort();
					}
				}
		}
	}
	private void checkBillStatusForCreate(ArrayList idList){
		try {
			
			for (int i = 0; i < idList.size(); i++) {
				
				EntityViewInfo evi = new EntityViewInfo();
				FilterInfo filterInfo = new FilterInfo();
				filterInfo.getFilterItems().add(new FilterItemInfo("id", idList.get(i).toString(), CompareType.EQUALS));
				evi.setFilter(filterInfo);
				SelectorItemCollection coll = new SelectorItemCollection();
				coll.add(new SelectorItemInfo("id"));
				coll.add(new SelectorItemInfo("billStatus"));
				evi.setSelector(coll);
				IFDCReceivingBill fdcRece = FDCReceivingBillFactory.getRemoteInstance();
				FDCReceivingBillCollection collection = fdcRece.getFDCReceivingBillCollection(evi);
				if (collection != null && collection.size() > 0) {
					for (int j = 0; j < collection.size(); j++) {
						FDCReceivingBillInfo info = collection.get(j);
					
						if (info.getBillStatus().equals(RevBillStatusEnum.SAVE)) {
							MsgBox.showWarning(this, "��ѡ����״̬���ԣ����飡");
							SysUtil.abort();
							break;
						}else if (info.getBillStatus().equals(RevBillStatusEnum.SUBMIT)){
							MsgBox.showWarning(this, "��ѡ����״̬���ԣ����飡");
							SysUtil.abort();
							break;
						}else if (info.getBillStatus().equals(RevBillStatusEnum.AUDITING)){
							MsgBox.showWarning(this, "��ѡ����״̬���ԣ����飡");
							SysUtil.abort();
							break;
						}
					}
				}
			}
		} catch (BOSException e) {
			logger.error(e.getMessage()+"��ȡ���ز���¥�տ״̬ʧ��!");
		}
	}
	private void isCreateVouchered(ArrayList idList){
		boolean result = false;
		try {
				for (int i = 0; i < idList.size(); i++) {
					IFDCReceivingBill fdcRece = FDCReceivingBillFactory.getRemoteInstance();
					EntityViewInfo evi = new EntityViewInfo();
					FilterInfo filterInfo = new FilterInfo();
					filterInfo.getFilterItems().add(new FilterItemInfo("id", idList.get(i).toString(), CompareType.EQUALS));
					evi.setFilter(filterInfo);
					SelectorItemCollection coll = new SelectorItemCollection();
					coll.add(new SelectorItemInfo("id"));
					coll.add(new SelectorItemInfo("fiVouchered"));
					evi.setSelector(coll);
					FDCReceivingBillCollection collection = fdcRece.getFDCReceivingBillCollection(evi);
					if(collection!=null && collection.size()>0){
						FDCReceivingBillInfo info = collection.get(i);
						if(info.isFiVouchered()){
							result = true;
						}
					}
				}
			} catch (BOSException e) {
				logger.error(e.getMessage()+"��ȡ�Ƿ�������ƾ֤״̬ʧ��!");
			}
			
			if(result){
				MsgBox.showWarning(this, "������ƾ֤���������ɳ����ո����");
				SysUtil.abort();
			}
	}
	private boolean isCreateBill(ArrayList idList){
		
		boolean result = true;
	
		try {
				for (int i = 0; i < idList.size(); i++) {
					EntityViewInfo evi = new EntityViewInfo();
					FilterInfo filterInfo = new FilterInfo();
					filterInfo.getFilterItems().add(new FilterItemInfo("id", idList.get(i).toString(), CompareType.EQUALS));
					evi.setFilter(filterInfo);
					SelectorItemCollection coll = new SelectorItemCollection();
					coll.add(new SelectorItemInfo("id"));
					coll.add(new SelectorItemInfo("isCreateBill"));
					evi.setSelector(coll);
					IFDCReceivingBill fdcRece = FDCReceivingBillFactory.getRemoteInstance();
					FDCReceivingBillCollection collection = fdcRece.getFDCReceivingBillCollection(evi);
					if (collection != null && collection.size() > 0) {
						for (int j = 0; j < collection.size(); j++) {
							FDCReceivingBillInfo info = collection.get(j);
							if(info.isIsCreateBill()){
								result = false;
							}
						}
					}
				}
				
				
			} catch (BOSException e) {
				logger.error(e.getMessage()+"��ȡ���ز���¥�տ״̬ʧ��!");
			}
		return result;
	}
	private boolean findBillFromCasPaymentBillbyId(ArrayList idList){
		boolean result = true;
		try {
			
			for (int i = 0; i < idList.size(); i++) {
				IReceivingBill rece = ReceivingBillFactory.getRemoteInstance();
				EntityViewInfo evi = new EntityViewInfo();
				FilterInfo filterInfo = new FilterInfo();
				filterInfo.getFilterItems().add(new FilterItemInfo("sourceBillId", idList.get(0).toString(), CompareType.EQUALS));
				evi.setFilter(filterInfo);
				SelectorItemCollection coll = new SelectorItemCollection();
				coll.add(new SelectorItemInfo("sourceBillId"));
				coll.add(new SelectorItemInfo("billStatus"));
				evi.setSelector(coll);
				ReceivingBillCollection collection = rece.getReceivingBillCollection(evi);
				if(collection!=null && collection.size()>0){
					result = false;
				}
			}
			
		} catch (BOSException e) {
			e.printStackTrace();
		}
		
		return result;
	}
	private void checkCreateBillStatus(String id,String msg){
		try {
			if(ReceivingBillFactory.getRemoteInstance().exists("select id from where sourceBillId='"+id+"'")
					||PaymentBillFactory.getRemoteInstance().exists("select id from where sourceBillId='"+id+"'")){
				MsgBox.showWarning(this, msg);
				SysUtil.abort();
			}
			} catch (BOSException e) {
				logger.error(e.getMessage()+"��ȡ�Ƿ������ɳ����ո��״̬ʧ��!");
			} catch (EASBizException e) {
				logger.error(e.getMessage()+"��ȡ�Ƿ������ɳ����ո��״̬ʧ��!");
			}
			
	}
	protected void cbIsAll_actionPerformed(ActionEvent e) throws Exception {
		getTenancyBillList();
	}
	public void actionRefundment_actionPerformed(ActionEvent e) throws Exception {
		int rowIndex = this.kdtTenancy.getSelectManager().getActiveRowIndex();
		String id="'null'";
		if(rowIndex>=0){
			IRow row = this.kdtTenancy.getRow(rowIndex);
			id = (String) row.getCell("id").getValue();
		}else{
			FDCMsgBox.showWarning(this,"��ѡ�����޺�ͬ�����˿������");
			return;
		}
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