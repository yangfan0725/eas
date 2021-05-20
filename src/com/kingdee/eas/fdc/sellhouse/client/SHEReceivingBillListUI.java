/**
 * output package name
 */
package com.kingdee.eas.fdc.sellhouse.client;

import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import javax.swing.JOptionPane;
import javax.swing.tree.TreeNode;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectManager;
import com.kingdee.bos.ctrl.kdf.table.util.KDTableUtil;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.dao.query.BizEnumValueDTO;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemCollection;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.IUIFactory;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.eas.base.commonquery.IQuerySolutionFacade;
import com.kingdee.eas.base.commonquery.QuerySolutionFacadeFactory;
import com.kingdee.eas.base.commonquery.QuerySolutionInfo;
import com.kingdee.eas.base.commonquery.client.Util;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basecrm.FDCReceivingBillCollection;
import com.kingdee.eas.fdc.basecrm.FDCReceivingBillFactory;
import com.kingdee.eas.fdc.basecrm.FDCReceivingBillInfo;
import com.kingdee.eas.fdc.basecrm.IFDCReceivingBill;
import com.kingdee.eas.fdc.basecrm.RevBillStatusEnum;
import com.kingdee.eas.fdc.basecrm.RevBillTypeEnum;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.basedata.MoneySysTypeEnum;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.sellhouse.BuildingInfo;
import com.kingdee.eas.fdc.sellhouse.BuildingUnitInfo;
import com.kingdee.eas.fdc.sellhouse.MoneyTypeEnum;
import com.kingdee.eas.fdc.tenancy.client.UpdateSubjectUI;
import com.kingdee.eas.fi.cas.IReceivingBill;
import com.kingdee.eas.fi.cas.ReceivingBillCollection;
import com.kingdee.eas.fi.cas.ReceivingBillFactory;
import com.kingdee.eas.fi.cas.ReceivingBillInfo;
import com.kingdee.eas.fi.cas.client.CasReceivingBillListUI;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.MsgBox;

/**
 * output class name
 */
public class SHEReceivingBillListUI extends AbstractSHEReceivingBillListUI
{
    /**
	 * 
	 */
	private static final long serialVersionUID = -3093948863553679391L;
	private static final Logger logger = CoreUIObject.getLogger(SHEReceivingBillListUI.class);
	
    /**
     * output class constructor
     */
    public SHEReceivingBillListUI() throws Exception
    {
        super();
    }

	public void onLoad() throws Exception {
		super.onLoad();

		this.actionAddNew.setEnabled(false);
		this.actionDelVoucher.setVisible(true);
		this.actionTraceDown.setVisible(true);
		this.actionUpdateSubject.setVisible(true);
		this.actionUpdateSubject.setEnabled(true);
		
		if(!SysContext.getSysContext().getCurrentSaleUnit().isIsBizUnit()){
			this.actionEdit.setEnabled(false);
			this.actionRemove.setEnabled(false);
		}
		
		tblMain.getSelectManager().setSelectMode(KDTSelectManager.MULTIPLE_ROW_SELECT);
		this.treeMain.setSelectionRow(0);
		this.actionCreateBill.setVisible(true);
		this.actionCreateBill.setEnabled(true);
		this.actionRelatePaymentBill.setEnabled(true);
	}

	protected String getHandleClazzName() {
		return "com.kingdee.eas.fdc.sellhouse.app.SheRevHandle";
	}

	protected MoneySysTypeEnum getSystemType() {
		return MoneySysTypeEnum.SalehouseSys;
	}
		
	protected String getEditUIName() {
		return SHEReceivingBillEditUI.class.getName();
	}

	public SelectorItemCollection getSelectors() {
        SelectorItemCollection sic = new SelectorItemCollection();
        sic.add(new SelectorItemInfo("sellProject.name"));
        sic.add(new SelectorItemInfo("currency.name"));
        sic.add(new SelectorItemInfo("number"));
        sic.add(new SelectorItemInfo("amount"));
        sic.add(new SelectorItemInfo("originalAmount"));
        sic.add(new SelectorItemInfo("receiptNumber"));
        sic.add(new SelectorItemInfo("invoiceNumber"));
        sic.add(new SelectorItemInfo("bizDate"));
        sic.add(new SelectorItemInfo("state"));
        sic.add(new SelectorItemInfo("createTime"));
        sic.add(new SelectorItemInfo("id"));
        sic.add(new SelectorItemInfo("moneyDefine.name"));
        sic.add(new SelectorItemInfo("settlementType.name"));
        sic.add(new SelectorItemInfo("moneyDefine.moneyType"));
        sic.add(new SelectorItemInfo("billStatus"));
        sic.add(new SelectorItemInfo("roomLongNumber"));
        sic.add(new SelectorItemInfo("room.name"));
//        sic.add(new SelectorItemInfo("room.number"));
        sic.add(new SelectorItemInfo("room.displayName"));
        sic.add(new SelectorItemInfo("room.id"));
        sic.add(new SelectorItemInfo("fiVouchered"));
        sic.add(new SelectorItemInfo("revBillType"));
        sic.add(new SelectorItemInfo("revBizType"));
        sic.add(new SelectorItemInfo("customer.name"));
        sic.add(new SelectorItemInfo("description"));
        sic.add(new SelectorItemInfo("entries.*"));
        sic.add(new SelectorItemInfo("entries.revAccount.*"));
        sic.add(new SelectorItemInfo("entries.oppAccount.*"));
        return sic;
	}
	
	protected void initTree() throws Exception {
		this.treeMain.setModel(FDCTreeHelper.getUnitTree(this.actionOnLoad, this.getSystemType()));
		this.treeMain.expandAllNodes(true, (TreeNode) this.treeMain.getModel().getRoot());
	}
	
	 public void actionUpdateSubject_actionPerformed(ActionEvent e)
		throws Exception {
	    checkSelected();
		checkUpdateSubject();
		ArrayList list=this.getSelectedIdValues();
		HashSet set=new HashSet(list);
		int[] indexs=KDTableUtil.getSelectedRows(tblMain);
		String revBillType=tblMain.getRow(indexs[0]).getCell("revBillType").getValue().toString();
		IUIFactory uiFactory = null;
		UIContext uiContext = new UIContext(this);
		uiContext.put("list", set);
		uiContext.put("revBillType", revBillType);// ��������
		uiContext.put("sysType", MoneySysTypeEnum.SalehouseSys);
		uiFactory = UIFactory.createUIFactory(UIFactoryName.NEWWIN);
		IUIWindow curDialog = uiFactory.create(UpdateSubjectUI.class.getName(),uiContext, null, OprtState.ADDNEW);
		curDialog.show();
	 }

      private void checkUpdateSubject(){
		int[] indexs=KDTableUtil.getSelectedRows(tblMain);
		int count = 0;
		if(indexs[0]!=-1)count = indexs[0];
		String revBillType=tblMain.getRow(count).getCell("revBillType").getValue().toString();
		for(int i=0;i<indexs.length;i++){
			IRow row=tblMain.getRow(indexs[i]);
			Boolean b=Boolean.valueOf(row.getCell("fiVouchered").getValue().toString());
			if(!revBillType.equals(row.getCell("revBillType").getValue().toString())){
				MsgBox.showWarning("ѡ��ĵ������Ͳ�һ�£����ܽ��п�Ŀά����");
				abort();
			}
			if(b.booleanValue()){
				MsgBox.showWarning("ѡ���а�����������ƾ֤�ĵ���,���ܽ��п�Ŀά����");
				abort();
			}
		}
}
	
	protected void prepareUIContext(UIContext uiContext, ActionEvent e) {
		uiContext.put(SHEReceivingBillEditUI.KEY_SELL_PROJECT, curSellProNode);
		super.prepareUIContext(uiContext, e);
	}
	
	protected void setColGroups() {
		super.setColGroups();
		
		setColGroup("building");
		setColGroup("buildUnit");
		setColGroup("room.number");
		setColGroup("room.roomNo");
		setColGroup("purchase.number");
		setColGroup("saleMan");
		setColGroup("creator");
		setColGroup("receiptState");
		setColGroup("invoiceAmount");
	}
	
	
	public void actionBatchReceiving_actionPerformed(ActionEvent e) throws Exception {
		DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode)treeMain.getLastSelectedPathComponent();
		if (node == null || !(node.getUserObject() instanceof BuildingInfo || node.getUserObject() instanceof BuildingUnitInfo)) {
			FDCMsgBox.showInfo("������ѡ��¥����Ԫ�ڵ㣡");
			return;
		}
		
		UIContext uiContext = new UIContext(this);
		if(node.getUserObject() instanceof BuildingInfo) {
			BuildingInfo buildInfo = (BuildingInfo)node.getUserObject();
			uiContext.put("TreeNodeInfo", buildInfo);
			uiContext.put("sellProject", buildInfo.getSellProject());
		}else if(node.getUserObject() instanceof BuildingUnitInfo){
			BuildingUnitInfo buildUnitInfo = (BuildingUnitInfo)node.getUserObject();
			uiContext.put("TreeNodeInfo", buildUnitInfo);			
			uiContext.put("sellProject", ((BuildingInfo)((DefaultKingdeeTreeNode)node.getParent()).getUserObject()).getSellProject());
		}
		

		int retResult = FDCMsgBox.showConfirm2New(null,"��ѡ��Ҫ�����տ�Ŀ������                                        " +
				" "+MoneyTypeEnum.AccFundAmount.getAlias()+"���ǡ�                                                             " +
						""+MoneyTypeEnum.LoanAmount.getAlias()+"����");   //.showTableDetailAndOK(null, error, errorDetailMap, 3);
		if(retResult==JOptionPane.YES_OPTION) {
			uiContext.put("MoneyTypeEnum", MoneyTypeEnum.AccFundAmount);
			IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.MODEL).
			create(BatchReceiveBillEditUI.class.getName(), uiContext, null,OprtState.ADDNEW);
			uiWindow.show();
		}else if(retResult==JOptionPane.NO_OPTION){
			uiContext.put("MoneyTypeEnum", MoneyTypeEnum.LoanAmount);
			IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.MODEL).
			create(BatchReceiveBillEditUI.class.getName(), uiContext, null,OprtState.ADDNEW);
			uiWindow.show();	
		}
		

	}

	/**
	 * �����տ���ɳ���ϵͳ���տ
	 * 
	 */
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
						MsgBox.showWarning(this,"�Ѿ����ɳ����տ���������ظ����ɣ�");
						SysUtil.abort();
					}
				}
				try{
					FDCReceivingBillFactory.getRemoteInstance().createCashBill(idList, false);
					MsgBox.showWarning(this, "�����տ���ɳɹ���");
					SysUtil.abort();
				}catch(Exception ex){
					if(ex instanceof BOSException&&ex.getMessage().startsWith("��Ŀ����ָ���ұ����")){
						MsgBox.showWarning(this, "��Ŀ����ָ���ұ���㣬��ѡ�������ұ�");
						SysUtil.abort();
					}else if(ex instanceof BOSException&&ex.getMessage().startsWith("���ɳ����տʧ��")){
						MsgBox.showWarning(this, "���ɳ����տʧ�ܣ�");
						SysUtil.abort();
					}
				}
		}
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
			FDCMsgBox.showWarning(this, "ת�����͵��տ�������ɳ����տ!");
			SysUtil.abort();
		}/*else if(bizDTO.getName().equals("adjust")){
			FDCMsgBox.showWarning(this, "�������͵��տ�������ɳ����տ!");
			SysUtil.abort();
		}*/
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
	
	private void checkCreateBillStatus(ArrayList idList,String msg){

		try {
				for (int i = 0; i < idList.size(); i++) {
					IReceivingBill rece = ReceivingBillFactory.getRemoteInstance();
					EntityViewInfo evi = new EntityViewInfo();
					FilterInfo filterInfo = new FilterInfo();
					filterInfo.getFilterItems().add(new FilterItemInfo("sourceBillId", idList.get(i).toString(), CompareType.EQUALS));
					evi.setFilter(filterInfo);
					SelectorItemCollection coll = new SelectorItemCollection();
					coll.add(new SelectorItemInfo("sourceBillId"));
					coll.add(new SelectorItemInfo("billStatus"));
					evi.setSelector(coll);
					ReceivingBillCollection collection = rece.getReceivingBillCollection(evi);
					if(collection!=null && collection.size()>0){
						//result = true;
						MsgBox.showWarning(this, msg);
						SysUtil.abort();
					}
				}
			} catch (BOSException e) {
				logger.error(e.getMessage()+"��ȡ�Ƿ������ɳ����տ״̬ʧ��!");
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
				MsgBox.showWarning(this, "������ƾ֤���������ɳ����տ��");
				SysUtil.abort();
			}
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
	
	public void actionRemove_actionPerformed(ActionEvent e) throws Exception {
		
		ArrayList idList = this.getSelectedIdValues();
		checkCreateBillStatus(idList,"��ѡ�տ�����в��ֵ������ɳ����տ��������ɾ��������");
		super.actionRemove_actionPerformed(e);
	}
	
	public void actionEdit_actionPerformed(ActionEvent e) throws Exception {
		ArrayList idList = this.getSelectedIdValues();
		checkCreateBillStatus(idList,"��ѡ�տ�����в��ֵ������ɳ����տ���������޸Ĳ�����");
		super.actionEdit_actionPerformed(e);
	}
	
	//�����ɳ����տ�ļ�¼���ܽ���"������"��"ȡ���տ�"���� lww 12272010
	public void actionUnAudit_actionPerformed(ActionEvent e) throws Exception {
		ArrayList idList = this.getSelectedIdValues();
		checkCreateBillStatus(idList,"��ѡ�տ�����в��ֵ������ɳ����տ���������޸Ĳ�����");
		super.actionUnAudit_actionPerformed(e);
	}
	
	public void actionCanceReceive_actionPerformed(ActionEvent e)
			throws Exception {
		ArrayList idList = this.getSelectedIdValues();
		checkCreateBillStatus(idList,"��ѡ�տ�����в��ֵ������ɳ����տ���������޸Ĳ�����");
		super.actionCanceReceive_actionPerformed(e);
	}
	
	public void actionView_actionPerformed(ActionEvent e) throws Exception {
	
		super.actionView_actionPerformed(e);
	}
	protected boolean isVerifyAdjustBillEditable() {
		return false;
	}
	public void actionVoucher_actionPerformed(ActionEvent e) throws Exception {
		ArrayList idList = this.getSelectedIdValues();
		checkCreateBillStatus(idList,"��ѡ�տ�����в��ֵ������ɳ����տ��������ѡ�������ƾ֤���տ��");
		
		FDCSQLBuilder builder = new FDCSQLBuilder();
		builder.appendSql(" select top 1 bill.FID ");
		builder.appendSql(" from T_BDC_FDCRECEIVINGBILL bill ");
		builder.appendSql(" left join T_BDC_FDCReceivingBillEntry entry ");
		builder.appendSql(" on entry.FHeadID = bill.FID ");
		builder.appendSql(" where ");
		builder.appendParam("bill.FID", idList.toArray());
		builder.appendSql(" and (entry.FRevAccountID IS NULL or entry.FOppAccountID IS NULL) ");
		if(builder.isExist()){
			MsgBox.showWarning(this, "���ڷ�¼��ĿΪ�յļ�¼����������ƾ֤!");
			SysUtil.abort();
		}
		
		super.actionVoucher_actionPerformed(e);
	}

	public void actionClearInvoice_actionPerformed(ActionEvent e) throws Exception {
		try{
			super.actionClearInvoice_actionPerformed(e);
			
			SysUtil.abort();
		}catch(Exception ex){
			logger.error(ex.getMessage()+"��Ʊ���ʧ�ܣ�");
		}
		
	}
	
	/**
	 * �����տ ���� ���ɳ���ϵͳ���տ by lww 12102010
	 */
	public void actionBatchCreateBill_actionPerformed(ActionEvent e)
			throws Exception {
		ArrayList IdList = this.getSelectedIdValues();
		if (IdList == null || IdList.size() == 0) {
			FDCMsgBox.showWarning(this, "��ѡ��Ҫ���ɳ����տ�ļ�¼��");
			SysUtil.abort();
		}
		batchCreatePaymentBillCheck(getReceivingBillCollection(IdList), IdList);

		try {
			FDCReceivingBillFactory.getRemoteInstance().createCashBill(IdList,
					false);
			FDCMsgBox.showWarning(this, "�����տ���ɳɹ���");
			SysUtil.abort();
		} catch (Exception ex) {
			if (ex instanceof BOSException
					&& ex.getMessage().startsWith("��Ŀ����ָ���ұ����")) {
				FDCMsgBox.showWarning(this, "��Ŀ����ָ���ұ���㣬��ѡ�������ұ�");
				SysUtil.abort();
			} else if (ex instanceof BOSException
					&& ex.getMessage().startsWith("���ɳ����տʧ��")) {
				FDCMsgBox.showWarning(this, "���ɳ����տʧ�ܣ�");
				SysUtil.abort();
			}
			ex.printStackTrace();
		}
	}

	/**
	 * ������ѡ�е�ID����FDCReceivingBillCollection by lww 12102010
	 * @param IdList
	 * @return
	 */
	private FDCReceivingBillCollection getReceivingBillCollection (ArrayList IdList){
		Set IdSet = new HashSet(IdList);
		
		FilterItemInfo  IDFilter = new FilterItemInfo("id",IdSet,CompareType.INCLUDE);
		FilterInfo filter = new FilterInfo();
		FilterItemCollection filterItemCollection = filter.getFilterItems();
		filterItemCollection.add(IDFilter);
		
		SelectorItemCollection coll = new SelectorItemCollection();
		coll.add(new SelectorItemInfo("id"));
		coll.add(new SelectorItemInfo("revBillType"));
		coll.add(new SelectorItemInfo("billStatus"));
		coll.add(new SelectorItemInfo("fiVouchered"));
		coll.add(new SelectorItemInfo("isCreateBill"));
		
		EntityViewInfo view = new EntityViewInfo();
		view.setFilter(filter);
		view.setSelector(coll);
		
		try {
			IFDCReceivingBill fdcrece = FDCReceivingBillFactory.getRemoteInstance();
			FDCReceivingBillCollection collection = fdcrece.getFDCReceivingBillCollection(view);
			return collection;
		} catch (BOSException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * У�鵥�����͡�����״̬���Ƿ��Ѿ�����ƾ֤���Ƿ��Ѿ����ɳ����տ by lww 12102010
	 * @param collection
	 * @param IdList
	 */
	private void batchCreatePaymentBillCheck(FDCReceivingBillCollection collection,ArrayList IdList){
	    String warningMsg = "���ڲ��������ɳ����տ�ļ�¼��" +
	    		"������ѡ�տ��¼�ĵ���״̬���������͡��Ƿ������ɳ����տ���Ƿ�������ƾ֤!";
		if(collection!=null && collection.size()>0){
			for(int i=0; i<collection.size(); i++){
				FDCReceivingBillInfo info = collection.get(i);
				//�жϵ���������� ���� "�տ�"��"�˿�"��"����"�;���(������Ϊ"ת��"�;��棩
				if(info.getRevBillType().equals(RevBillTypeEnum.transfer)){
					FDCMsgBox.showWarning(this, warningMsg);
					SysUtil.abort();
				}
				//�жϵ���״̬��� ����"������"��"���տ�"�;���
				if (info.getBillStatus().equals(RevBillStatusEnum.SAVE)) {
					FDCMsgBox.showWarning(this, warningMsg);
					SysUtil.abort();
				}else if (info.getBillStatus().equals(RevBillStatusEnum.SUBMIT)){
					FDCMsgBox.showWarning(this, warningMsg);
					SysUtil.abort();
				}else if (info.getBillStatus().equals(RevBillStatusEnum.AUDITING)){
					FDCMsgBox.showWarning(this, warningMsg);
					SysUtil.abort();
				}
				//�жϵ����Ƿ��Ѿ�����ƾ֤
				if(info.isFiVouchered()){
					FDCMsgBox.showWarning(this, warningMsg);
					SysUtil.abort();
				}
				//�жϵ����Ƿ��Ѿ����ɳ����տ(���ж��Ƿ񴴽��˳��ɵ�������ֵ���ٲ�洢���ɵ��ı��Ƿ���������¼)
				if(info.isIsCreateBill()){
					boolean result = findBillFromCasPaymentBillbyId(IdList);
					if(!result){
						FDCMsgBox.showWarning(this, warningMsg);
						SysUtil.abort();
					}
				}
			}
		}
	}

	/**
	 * �����տ���������տ        by lww 12132010
	 */
	public void acitonRelatePaymentBill_actionPerformed(ActionEvent e) 
			throws Exception {
		ArrayList IdList = this.getSelectedIdValues();
		if (IdList == null || IdList.size() == 0) {
			FDCMsgBox.showWarning(this, "��ѡ���¼�У�");
			SysUtil.abort();
		}
		if (IdList != null && IdList.size() > 1) {
			FDCMsgBox.showWarning(this, "��ѡ�������ݽ��д˲�����");
			SysUtil.abort();
		}
		// ������ɵ������ˣ�isCreateBill()���ص���false
		boolean billStatus = isCreateBill(IdList);
		if (billStatus) {
			FDCMsgBox.showWarning(this, "��ѡ��û�������տ���ɵ���������ѡ��!");
			SysUtil.abort();
		} else {
			// ���ɵ������ˣ�����ɾ����
			boolean result = findBillFromCasPaymentBillbyId(IdList);
			if (result) {
				FDCMsgBox.showWarning(this, "��ѡ��û�������տ���ɵ���������ѡ��!");
				SysUtil.abort();
			}
		}
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(
				new FilterItemInfo("sourceBillId", IdList.get(0).toString(),
						CompareType.EQUALS));
		SelectorItemCollection coll = new SelectorItemCollection();
		coll.add(new SelectorItemInfo("id"));
		EntityViewInfo view = new EntityViewInfo();
		view.setFilter(filter);
		view.setSelector(coll);
		IReceivingBill rec = ReceivingBillFactory.getRemoteInstance();
		ReceivingBillCollection collection = rec
				.getReceivingBillCollection(view);
		String[] payMentBillIdList = new String[collection.size()];
		if (collection != null && collection.size() > 0) {
			for (int i = 0; i < collection.size(); i++) {
				ReceivingBillInfo info = collection.get(i);
				payMentBillIdList[i] = info.getId().toString();
			}
		}
		UIContext ctx = new UIContext(this);
		ctx.put(UIContext.IDLIST, payMentBillIdList);
		IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.NEWTAB)
				.create(CasReceivingBillListUI.class.getName(), ctx, null,
						OprtState.VIEW);

		// CasReceivingBillListUI ui = (CasReceivingBillListUI)
		// uiWindow.getUIObject();
		// ui.setIsNeedDefaultFilter(false);
		uiWindow.show();
	}
	
	/*
	 * ����ͼ�� lww(non-Javadoc)
	 * @see com.kingdee.eas.fdc.basedata.client.FDCBillListUI#initWorkButton()
	 */
	protected void initWorkButton() {
		super.initWorkButton();
		this.btnBatchCreateBill.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_newbatch"));
		this.btnRelatePaymentBill.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_associateentry"));
	}
	/**
     * �Ƿ�Ӧ���ڵ��ݲ�ѯ֮ǰ����ID����Ϊ�˱�֤����ͷ��ȷ���ںϡ� Ĭ�����ӡ�
     * ���в�Ϊ�յ�Ĭ�������ѯ������ʱ��retun false������ϵͳ�ܹ��������򷽰�����
     * xiaoao����liu
     * @return
     */
    protected boolean isOrderByIDForBill()
    {
    	IQuerySolutionFacade iQuery=null;
    	QuerySolutionInfo querySolution=null;
		try {
			iQuery = QuerySolutionFacadeFactory.getRemoteInstance();
		} catch (BOSException e1) {
			e1.printStackTrace();
		}
        String queryName = (getQueryInfo(this.mainQueryPK)).getFullName();
//        if (preference!=null)
//            return;
        String uiName=getQueryUiName();
        String name  =getQueryName();
        if (uiName==null)
        {
            uiName=this.getClass().getName();
        }
        if (name==null)
        {
            name=queryName;
        }
        if (iQuery!=null && name!=null)
        {
        	try {
				querySolution = iQuery.getDefaultSolution(uiName, name);
			} catch (EASBizException e) {
				e.printStackTrace();
			} catch (BOSException e) {
				e.printStackTrace();
			}
			EntityViewInfo ev =null;

	        if (querySolution!=null&&querySolution.getEntityViewInfo()!=null)
	        {
	            try {
					ev=Util.getInnerFilterInfo(querySolution);
				} catch (EASBizException e) {
					e.printStackTrace();
				} catch (BOSException e) {
					e.printStackTrace();
				}
	        }
	        if(ev!=null){
	        	return false;
	        }
        }
       
        return true;
    }
}