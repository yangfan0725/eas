/**
 * output package name
 */
package com.kingdee.eas.fdc.finance.client;

import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.kingdee.bos.ctrl.kdf.table.event.KDTSelectEvent;
import com.kingdee.bos.dao.AbstractObjectValue;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.query.IQueryExecutor;
import com.kingdee.bos.metadata.IMetaDataPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemCollection;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SorterItemCollection;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.eas.base.commonquery.client.CommonQueryDialog;
import com.kingdee.eas.base.commonquery.client.CustomerQueryPanel;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basedata.CurProjectFactory;
import com.kingdee.eas.fdc.basedata.FDCBillInfo;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.client.FDCClientHelper;
import com.kingdee.eas.fdc.basedata.client.FDCClientUtils;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.contract.ContractBillFactory;
import com.kingdee.eas.fdc.contract.ContractFacadeFactory;
import com.kingdee.eas.fdc.contract.IContractBill;
import com.kingdee.eas.fdc.contract.client.ContractClientUtils;
import com.kingdee.eas.fi.cas.BillStatusEnum;
import com.kingdee.eas.fi.cas.IPaymentBill;
import com.kingdee.eas.fi.cas.PaymentBillCollection;
import com.kingdee.eas.fi.cas.PaymentBillFactory;
import com.kingdee.eas.fi.cas.PaymentBillInfo;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.MsgBox;
import com.kingdee.jdbc.rowset.IRowSet;

/**
 * output class name
 */
public class PaymentFullListUI extends AbstractPaymentFullListUI {
	public static final String resourcePath = "com.kingdee.eas.fdc.contract.client.ContractFullResource";

	private CustomerQueryPanel filterUI = null;

	private CommonQueryDialog commonQueryDialog = null;
	
	protected EntityViewInfo view;

	private Map proLongNameMap = new HashMap();

	/**
	 * output class constructor
	 */
	public PaymentFullListUI() throws Exception {
		super();
	}

	/**
	 * output storeFields method
	 */
	public void storeFields() {
		super.storeFields();
	}

	protected String getEditUIName() {
		return PaymentBillEditUI.class.getName();
	}

	protected void execQuery() {
		super.execQuery();
		
		FDCClientUtils.fmtFootNumber(this.tblMain, new String[]{"localAmt","amount"});
		FDCClientHelper.initTable(tblMain);
	}

	protected ICoreBase getBizInterface() throws Exception {
		return PaymentBillFactory.getRemoteInstance();
	}

	
	protected CommonQueryDialog initCommonQueryDialog() {
		if (commonQueryDialog != null) {
			return commonQueryDialog;
		}
		commonQueryDialog = super.initCommonQueryDialog();
		commonQueryDialog.setWidth(400);
		commonQueryDialog.addUserPanel(this.getFilterUI());
		return commonQueryDialog;
	}

	private CustomerQueryPanel getFilterUI() {
		if (this.filterUI == null) {
			try {
				this.filterUI = new PaymentFullFilterUI(this,
						this.actionOnLoad);
			} catch (Exception e) {
				e.printStackTrace();
				abort(e);
			}
		}
		return this.filterUI;
	}

	/**
	 * 
	 * ��ʼ��Ĭ�Ϲ�������
	 * 
	 * @return ��������ˣ������˳�ʼ�����������뷵��true;Ĭ�Ϸ���false;
	 */
	protected boolean initDefaultFilter() {
		return true;
	}

	protected String getKeyFieldName() {
		return super.getKeyFieldName();
	}

	public void onLoad() throws Exception {
		if((!SysContext.getSysContext().getCurrentOrgUnit().isIsCompanyOrgUnit())&&(SysContext.getSysContext().getCurrentOrgUnit().isIsCostOrgUnit()))   	
    	{
    		MsgBox.showWarning(this, PaymentBillClientUtils.getRes("CostUnit"));
    		SysUtil.abort();
    	}
		
		FDCClientHelper.addSqlMenu(this, this.menuEdit);
		this.proLongNameMap = FDCHelper.createTreeDataMap(CurProjectFactory
				.getRemoteInstance(), "name", "\\");
		
		if (dataObjects != null && dataObjects.size() > 0) {
			java.util.Iterator keysIterater = dataObjects.keySet().iterator();

			while (keysIterater.hasNext()) {
				String key = (String) keysIterater.next();
				setDataObject((AbstractObjectValue) dataObjects.get(key));
			}
			
			setIsNeedDefaultFilter(false);
			this.actionQuery.setEnabled(false);
			this.actionQuery.setVisible(false);
		}
		
		super.onLoad();
		this.actionAddNew.setEnabled(false);
		//this.actionEdit.setEnabled(false); //update by renliang
		this.actionRemove.setEnabled(false);
		this.actionLocate.setEnabled(false);
		this.actionCreateTo.setEnabled(false);
//		this.actionTraceDown.setEnabled(false);
		this.actionTraceUp.setEnabled(false);
		this.actionCopyTo.setEnabled(false);
		this.actionVoucher.setEnabled(false);
		this.actionDelVoucher.setEnabled(false);
		this.actionAuditResult.setEnabled(false);
		this.actionMultiapprove.setEnabled(false);
		this.actionNextPerson.setEnabled(false);
		
		//new add by renliang
		this.actionAudit.setEnabled(true);
		this.actionPay.setEnabled(true);
		
		this.actionAddNew.setVisible(false);
		//this.actionEdit.setVisible(false); //update by renliang
		this.actionRemove.setVisible(false);
		this.actionLocate.setVisible(false);
		this.actionCreateTo.setVisible(false);
//		this.actionTraceDown.setVisible(false);
		this.actionTraceUp.setVisible(false);
		this.actionCopyTo.setVisible(false);
//		this.actionVoucher.setVisible(false);
//		this.actionDelVoucher.setVisible(false);
		this.actionAuditResult.setVisible(false);
		this.actionMultiapprove.setVisible(false);
		this.actionNextPerson.setVisible(false);
		//this.tblMain.getColumn("curProject.id").getStyleAttributes().setHided(true);
		//this.tblMain.getColumn("contractId").getStyleAttributes().setHided(true);
		this.tblMain.getColumn("company.name").getStyleAttributes().setHided(true);
		
		//new add by renliang
		this.actionPay.setVisible(true);
		this.actionAudit.setVisible(true);
		this.menuEdit.setVisible(true);
		this.menuEdit.setEnabled(true);
		
		FDCClientHelper.initTable(tblMain);
		setActionVoucher();
	}
	
	public void afterActionPerformed(ActionEvent e) {
		super.afterActionPerformed(e);
		this.actionVoucher.setVisible(true);
		this.actionDelVoucher.setVisible(true);
	}

	protected IQueryExecutor getQueryExecutor(IMetaDataPK queryPK,EntityViewInfo viewInfo) {

		viewInfo.setTopCount(2000);
		SorterItemCollection sorter = viewInfo.getSorter();
		if(viewInfo.getSorter().size()==0){
			sorter.add(new SorterItemInfo("payDate"));
		}
		
		if(view !=null){
			viewInfo.setFilter(view.getFilter());
		}

		return super.getQueryExecutor(queryPK,viewInfo);
	}
	
	public void onGetRowSet(IRowSet rowSet) {
		try {
			rowSet.beforeFirst();			
			Map idMap  = new HashMap();
			Map numberMap  = new HashMap();
			while (rowSet.next()) {
				String contractId = rowSet.getString("contractId");
				numberMap.put(contractId,contractId);

				String id  = ContractClientUtils.getUpdateAmtByAmtWithoutCost(rowSet);
				if(id!=null){
					idMap.put(id,id);
				}
			}
			Map amountMap  = ((IContractBill)ContractBillFactory.getRemoteInstance()).getAmtByAmtWithoutCost(idMap);
			
			rowSet.beforeFirst();
			while (rowSet.next()) {
				ContractClientUtils.updateAmtByAmtWithoutCost( rowSet, amountMap);
			}	
			
			//��������
//			Map map = ContractFacadeFactory.getRemoteInstance().getContractNumberAndNameMap(numberMap);
			
			rowSet.beforeFirst();
			while (rowSet.next()) {
				String displayName="";
				String id=rowSet.getString("curProject.id");
				String orgName=rowSet.getString("company.name");
				if(orgName!=null){
					displayName+=orgName;
				}
				String proName = (String) this.proLongNameMap.get(id);
				if(proName!=null){
					displayName+="\\"+proName;
				}
				rowSet.updateString("curProject.name",displayName);
				//��ͬ�źͺ�ͬ���Ʋ���ÿ�����£���Query�д��� by zhiyuan_tang 2011-01-06
//				String contractId=rowSet.getString("contractId");
//
//				FDCBillInfo info = (FDCBillInfo)map.get(contractId);
//				if(info != null){
//					rowSet.updateString("contractNumber",info.getNumber());
//					rowSet.updateString("contractName",info.getName());
//				}
				
			}
			rowSet.beforeFirst();
		}  catch (Exception e) {
			e.printStackTrace();
		}
		super.onGetRowSet(rowSet);
	}

	protected String getEditUIModal() {
		return UIFactoryName.NEWTAB;
	}

	protected boolean isIgnoreCUFilter() {
		return true;
	}
	protected void beforeExcutQuery(EntityViewInfo ev)
	{
		if(getUIContext().get("MyFilter") instanceof FilterInfo){
			FilterInfo myFilter=(FilterInfo)getUIContext().get("MyFilter");
			ev.setFilter(myFilter);
//			isNeedDefaultFilter=false;
			actionQuery.setVisible(false);
			actionAttachment.setVisible(false);
			actionPrint.setVisible(false);
			actionPrintPreview.setVisible(false);
			actionWorkFlowG.setVisible(false);
			return;
		}
		super.beforeExcutQuery(ev);
	}
	
	private boolean isFirstLoad=true;
	public void actionQuery_actionPerformed(ActionEvent e) throws Exception {
		if(isFirstLoad&&getUIContext().get("MyFilter")!=null) {
			//ʹ��Ĭ�ϲ�ѯ
			isFirstLoad=false;
			return;
		}else{
//			view = null;
			super.actionQuery_actionPerformed(e);
		}
	}
    
    public void actionRemove_actionPerformed(ActionEvent e) throws Exception
    {
    	return;
    }
    
    protected boolean isShowAttachmentAction() {
    	return false;
    }
    
	/**
     * �����Ƿ���ʾ�ϼ���
     * 2005-03-09 haiti_yang
     */
    protected boolean isFootVisible()
    {
        return true;
    }
	
	static public void showUI(EntityViewInfo view, Map ctx)
	throws Exception {
		Map dataObjects = new HashMap();
		dataObjects.put("mainQuery", view);
		dataObjects.put("company", ctx.get("company"));
		
		IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.NEWTAB)
				.create(PaymentFullListUI.class.getName(), ctx, dataObjects,
						OprtState.VIEW);
		
		uiWindow.show();
	}

	public void setDataObject(IObjectValue dataObject) {
		if (dataObject instanceof EntityViewInfo) {
			EntityViewInfo ev = (EntityViewInfo) dataObject;
			FilterInfo filter = ev.getFilter();
			if (filter != null) {
				FilterItemCollection fic = filter.getFilterItems();
				if (fic.size() > 0) {
					view = new EntityViewInfo();
//					view.setTopCount(topCount);
					view.setFilter(filter);
					SorterItemCollection sorters = view.getSorter();
					sorters.addObjectCollection(ev.getSorter());
					// Ĭ����ƾ֤ID����
//					sorters.add(new SorterItemInfo(HEADVCHID)); //

					EntityViewInfo v = new EntityViewInfo();
					v.setFilter(view.getFilter());
					sorters = v.getSorter();
					sorters.addObjectCollection(view.getSorter());
					this.mainQuery = v;
				}
			}
		}else{
			super.setDataObject(dataObject);
		}
	}

	protected void initDapButtons() throws Exception {
		super.initDapButtons();
		//2009-1-6 ˫���򿪱༭����󣬽���ƾ֤��ذ�ť��
		this.actionVoucher.setVisible(false);
		this.actionVoucher.setEnabled(false);
		this.actionDelVoucher.setVisible(false);
		this.actionDelVoucher.setEnabled(false);
	}
	protected void tblMain_tableSelectChanged(KDTSelectEvent e)
			throws Exception {
		setActionVoucher();
		super.tblMain_tableSelectChanged(e);
	}
	private void setActionVoucher(){
		int activeIndex = tblMain.getSelectManager().getActiveRowIndex();
		if(activeIndex < 0) {
			actionVoucher.setEnabled(false);
        	actionDelVoucher.setEnabled(false);
			return;
		}
		Boolean isFIVouchered = (Boolean)tblMain.getRow(activeIndex).getCell("fiVouchered").getValue();
		if(Boolean.TRUE.equals(isFIVouchered)){
			actionDelVoucher.setEnabled(true);
			actionDelVoucher.setVisible(true);
			actionVoucher.setEnabled(false);
			actionVoucher.setVisible(true);
		}else{
			actionVoucher.setVisible(true);
			actionVoucher.setEnabled(true);
			actionDelVoucher.setEnabled(false);
			actionDelVoucher.setVisible(true);
		}
	}
	
	/**
	 * ��������������
	 * @author liang_ren969
	 * @date 2010-5-19
	 */
	public void actionAudit_actionPerformed(ActionEvent e) throws Exception {
		this.checkSelected();
		//�õ��û�ѡ���billID�б�
		List idList =ContractClientUtils.getSelectedIdValues(getMainTable(), getKeyFieldName());
		
		if(idList!=null && idList.size()>0)
		{
			//���ø�������е���������
			IPaymentBill iPayemntBill  = PaymentBillFactory.getRemoteInstance();
			//״̬
			EntityViewInfo view = new EntityViewInfo();
			view.getSelector().add("billStatus");
			FilterInfo filter = new FilterInfo();
			view.setFilter(filter);
			Set idSet = new HashSet(idList); 
			filter.getFilterItems().add(new FilterItemInfo("id",idSet,CompareType.INCLUDE));
			PaymentBillCollection bills = iPayemntBill.getPaymentBillCollection(view);
			for(Iterator iter=bills.iterator();iter.hasNext();){
				PaymentBillInfo bill = (PaymentBillInfo)iter.next();
				if(!BillStatusEnum.SUBMIT.equals(bill.getBillStatus())){
					FDCMsgBox.showWarning(this,"���ڲ����������ļ�¼��������ѡ��");
					this.abort();
				}
			}
			//���÷��ز��ӿ�
			iPayemntBill.audit4FDC(idList);
			
			//��ʾ��ʾ��ˢ��ҳ��
			showOprtOKMsgAndRefresh();
		}else
		{
			MsgBox.showWarning(this,"����ѡ���У�");
			SysUtil.abort();
		}
		
		
	}
	

	/**
	 * 
	 * ��������ʾ�����ɹ�
	 * @author:renliang
	 * @date 2010-5-19
	 */
	protected void showOprtOKMsgAndRefresh() throws Exception {
		FDCClientUtils.showOprtOK(this);
		refreshList();
	}
	
	/**
	 * �����������
	 * @author:renliang
	 * @date 2010-5-19
	 */
	public void actionPay_actionPerformed(ActionEvent e) throws Exception {
		this.checkSelected();
		//�õ��û�ѡ���billID�б�
		List idList =ContractClientUtils.getSelectedIdValues(getMainTable(), getKeyFieldName());
		
		if(idList!=null && idList.size()>0)
		{
			//���ø�������еĸ����
			IPaymentBill iPayemntBill  = PaymentBillFactory.getRemoteInstance();
			//״̬
			EntityViewInfo view = new EntityViewInfo();
			view.getSelector().add("billStatus");
			FilterInfo filter = new FilterInfo();
			view.setFilter(filter);
			Set idSet = new HashSet(idList); 
			filter.getFilterItems().add(new FilterItemInfo("id",idSet,CompareType.INCLUDE));
			PaymentBillCollection bills = iPayemntBill.getPaymentBillCollection(view);
			for(Iterator iter=bills.iterator();iter.hasNext();){
				PaymentBillInfo bill = (PaymentBillInfo)iter.next();
				if(!BillStatusEnum.AUDITED.equals(bill.getBillStatus())){
					FDCMsgBox.showWarning(this,"���ڲ����������ļ�¼��������ѡ��");
					this.abort();
				}
			}
			iPayemntBill.pay(idSet);
			
			//��ʾ��ʾ��ˢ��ҳ��
			showOprtOKMsgAndRefresh();
		}
		else
		{
			MsgBox.showWarning(this,"����ѡ���У�");
			SysUtil.abort();
		}
		
		
	}
	public boolean isNeedShowBOTPRule() {
		//��д������һ��ʱֱ������ �����ʱ����ѡ�������� 
		return true;
	}
}