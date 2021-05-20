/**
 * output package name
 */
package com.kingdee.eas.fdc.finance.client;

import java.awt.event.ActionEvent;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.ItemAction;
import com.kingdee.eas.basedata.org.CostCenterOrgUnitInfo;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCConstants;
import com.kingdee.eas.fdc.basedata.client.FDCClientHelper;
import com.kingdee.eas.fdc.basedata.client.FDCClientUtils;
import com.kingdee.eas.fdc.contract.FDCUtils;
import com.kingdee.eas.fdc.finance.ContractPayPlanFactory;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.framework.ICoreBillBase;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.MsgBox;

/**
 * output class name
 */
public class ContractPayPlanListUI extends AbstractContractPayPlanListUI {
	private static final Logger logger = CoreUIObject
			.getLogger(ContractPayPlanListUI.class);

	//���ӱ����ж��Ƿ�Ϊ�޸ĺ�ͬ�ı༭
	private static boolean beingEdit = false;
	public static boolean beingEdit(){
		return beingEdit;
	}
	
	public ContractPayPlanListUI() throws Exception {
		super();
	}
	
	public void actionEditPayPlan_actionPerformed(ActionEvent e)
			throws Exception {
		if (getSelectContractId() == null) {
			MsgBox.showWarning(this, "û��ѡ�к�ͬ���޷��޸ĺ�ͬ����ƻ�");
			SysUtil.abort();
		}
		beingEdit=true;
		boolean hasMutex = false;
		List list = new ArrayList();
		String id = this.getSelectContractId();
		list.add(id + "PayPlan");
		try {
			FDCClientUtils.requestDataObjectLock(this, list, "edit");
			ContractPayPlanEditUI.showEditUI(this, getSelectContractId(),
					"EDIT");
			this.displayBillByContract(null);
		} catch (Throwable e1) {
			this.handUIException(e1);
			hasMutex = FDCClientUtils.hasMutexed(e1);
		} finally {
			if (!hasMutex) {
				try {
					FDCClientUtils.releaseDataObjectLock(this, list);
				} catch (Throwable e1) {
					this.handUIException(e1);
				}
			}
		}
	}

	protected void initWorkButton() {
		super.initWorkButton();
		this.btnEditPayPlan.setIcon(EASResource.getIcon("imgTbtn_edit"));
		this.menuItemEditPayPlan.setIcon(EASResource.getIcon("imgTbtn_edit"));
		this.actionTraceDown.setEnabled(false);
		this.actionTraceUp.setEnabled(false);
		
		this.actionView.setEnabled(false);
	}
	
	public void actionView_actionPerformed(ActionEvent e) throws Exception
	{
		//super.actionView_actionPerformed(e);
	}

	protected void refresh(ActionEvent e) throws Exception {
		super.refresh(e);
		beingEdit = false;
	}
	
	/**
	 * output getEditUIName method
	 */
	protected String getEditUIName() {
		return ContractPayPlanEditUI.class.getName();
	}

	protected ICoreBase getBizInterface() throws Exception {

		return ContractPayPlanFactory.getRemoteInstance();
	}

	/**
	 * 
	 * ��������ȡ��ǰ���ݵ�Table���������ʵ�֣�
	 * 
	 * @return
	 * @author:liupd ����ʱ�䣺2006-8-2
	 *               <p>
	 */
	protected KDTable getBillListTable() {
		return this.tblPayPlan;
	}

	/**
	 * output storeFields method
	 */
	public void storeFields() {
		super.storeFields();
	}

	protected boolean isHasBillTable() {
		return true;
	}

	protected void displayBillByContract(EntityViewInfo view)
			throws BOSException {
		String contractId = getSelectContractId();
		if(contractId==null){
			return;
		}
		try {
			ContractPayPlanHandler.fillTable(contractId, getBillListTable());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		this.tblPayPlan.getStyleAttributes().setLocked(true);
	}

	private String getSelectContractId() {
		int rowIndex = this.getMainTable().getSelectManager()
				.getActiveRowIndex();
		if(rowIndex==-1){
			return null;
		}
		String contractId = (String) getMainTable().getCell(rowIndex,
				getKeyFieldName()).getValue();
		return contractId;
	}
	protected String getKeyFieldName()
	{
		return "id";
	}
	protected void tblBill_tableClicked(KDTMouseEvent e) throws Exception
	{
		
	}
    //���ݶ���仯ʱ��ˢ�½���״̬
    protected void setActionState(){

    }

	public void onLoad() throws Exception {
		super.onLoad();
		
		FDCClientHelper.setActionEnable(new ItemAction[] {actionAttachment, actionPrint, actionPrintPreview,
				actionWorkFlowG, actionAudit, actionUnAudit, actionAddNew, actionEdit,	actionAuditResult,
				actionRemove,actionView,actionCancel,actionCancelCancel }, false);
		this.menuBiz.setVisible(false);
		this.menuWorkFlow.setVisible(false);
		CostCenterOrgUnitInfo currentOrg = SysContext.getSysContext().getCurrentCostUnit();
		boolean isDep = FDCUtils.getDefaultFDCParamByKey(null, null, FDCConstants.FDC_PARAM_DEPCONPAYPLAN);
		if (currentOrg == null /*|| !currentOrg.isIsBizUnit() */ ||isDep) {
			actionEditPayPlan.setEnabled(false);
		}
		actionQuery.setEnabled(true);
		actionQuery.setVisible(true);
	}

	protected String[] getLocateNames() {
		return new String[] {"number", "contractName", "contractType.name", "partB.name", "signDate"};
	}
	
	protected ICoreBillBase getRemoteInterface() throws BOSException {
		// TODO �Զ����ɷ������
		return null;
	}

	protected void audit(List ids) throws Exception {
		// TODO �Զ����ɷ������
		
	}

	protected void unAudit(List ids) throws Exception {
		// TODO �Զ����ɷ������
		
	}
	/**
	 * Ҫ��ʾ�ĺ�ͬ��״̬����,���ڹ��˺�ͬ
	 * @return
	 */
	protected Set getContractBillStateSet() {
		Set set=new HashSet();
		set.add(FDCBillStateEnum.AUDITTED_VALUE);
		set.add(FDCBillStateEnum.SAVED_VALUE);
		set.add(FDCBillStateEnum.SUBMITTED_VALUE);
		return set;
	}
	protected boolean isFootVisible() {
		return false;
	}

	protected void fetchInitData() throws Exception {
//		Map param = new HashMap();
//		Map initData = ((IFDCBill)fdcbill.getRemoteInstance()).fetchInitData(param);
//		
//		//��õ�ǰ��֯
//		orgUnit = (FullOrgUnitInfo)initData.get(FDCConstants.FDC_INIT_ORGUNIT);
		
	}
	
	protected SelectorItemCollection genBillQuerySelector() {
		return null;
	}
	//Ĭ�ϸ������ɼ�
    protected boolean isShowAttachmentAction()
    {
        return false;
    }
}