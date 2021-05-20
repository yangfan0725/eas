/**
 * output package name
 */
package com.kingdee.eas.fdc.tenancy.client;

import java.awt.event.ActionEvent;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.swing.KDTextField;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.eas.base.codingrule.CodingRuleException;
import com.kingdee.eas.base.codingrule.CodingRuleManagerFactory;
import com.kingdee.eas.base.codingrule.ICodingRuleManager;
import com.kingdee.eas.base.codingrule.client.CodingRuleIntermilNOBox;
import com.kingdee.eas.base.permission.UserInfo;
import com.kingdee.eas.basedata.org.OrgUnitInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.client.FDCClientHelper;
import com.kingdee.eas.fdc.basedata.client.FDCClientUtils;
import com.kingdee.eas.fdc.basedata.client.FDCClientVerifyHelper;
import com.kingdee.eas.fdc.sellhouse.SellProjectInfo;
import com.kingdee.eas.fdc.sellhouse.client.SHEHelper;
import com.kingdee.eas.fdc.tenancy.AgencyContractFactory;
import com.kingdee.eas.fdc.tenancy.AgencyContractInfo;
import com.kingdee.eas.fdc.tenancy.AgencyInfo;
import com.kingdee.eas.fdc.tenancy.AppPayDateTypeEnum;
import com.kingdee.eas.fdc.tenancy.CommisionStandardEnum;
import com.kingdee.eas.fdc.tenancy.ContractStateEnum;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.util.client.MsgBox;

/**
 * output class name
 */
public class AgencyContractEditUI extends AbstractAgencyContractEditUI
{
    private static final Logger log = CoreUIObject.getLogger(AgencyContractEditUI.class);
    
    /**
     * output class constructor
     */
    public AgencyContractEditUI() throws Exception
    {
        super();
    }
    
    public void onLoad() throws Exception {
    	super.onLoad();
    	
//    	ItemAction[] actions = new ItemAction[]{actionCopy, action};
//    	FDCClientHelper.setActionVisible(actions, false);
    	
    	this.f7Auditor.setEnabled(false);
    	this.pkAuditTime.setEnabled(false);
    	this.f7Handler.setEnabled(false);
    	this.pkStartUsingDate.setEnabled(false);
    	this.f7Stopper.setEnabled(false);
    	this.pkStopUsingDate.setEnabled(false);
    	this.actionAuditResult.setVisible(false);
    	
    	//�����,ֻʹ���ύ��ť
//    	this.actionSave.setVisible(false);
    	
    	this.storeFields();
		initOldData(this.editData);
		//��ͬ״̬Ϊ����������ʱ��Ҫ���Ƶİ�ť״̬
		
    }
    
    protected void verifyInput(ActionEvent e) throws Exception {
    	super.verifyInput(e);
    	
    	if(this.f7Agency.getData() == null){
    		MsgBox.showWarning(this, "�н��������Ϊ�գ�");
    		this.abort();
    	}
    	
    	if(this.f7Signer.getData() == null){
    		MsgBox.showWarning(this, "ǩ���˲���Ϊ�գ�");
    		this.abort();
    	}
    	
    	if(this.txtValue.getBigDecimalValue() == null){
    		MsgBox.showWarning(this, "���(�ٷֱ�)����Ϊ�գ�");
    		this.abort();
    	}
    	
    	//add by warship at 2010/09/15
    	BigDecimal amount = (BigDecimal)this.txtValue.getBigDecimalValue();
    	if(this.comboCommisionStandard.getSelectedIndex()== 0 && amount.compareTo(FDCHelper.ONE_HUNDRED) > 0){
    		MsgBox.showWarning(this, "���(�ٷֱ�)���ܴ���100��");
    		this.abort();
    	}
    	
    	if(this.comboCommisionStandard.getSelectedIndex()== 0 && amount.compareTo(FDCHelper.ZERO) < 0){
    		MsgBox.showWarning(this, "���(�ٷֱ�)����С��0��");
    		this.abort();
    	}
    	
    	if(this.comboCommisionStandard.getSelectedIndex()== 1 && amount.compareTo(FDCHelper.ZERO) < 0){
    		MsgBox.showWarning(this, "���(�ٷֱ�)����С��0��");
    		this.abort();
    	}
    	
    }
    
    protected IObjectValue createNewData() {
    	AgencyContractInfo agencyContract = new AgencyContractInfo();
    	agencyContract.setSellProject((SellProjectInfo) this.getUIContext().get("sellProject"));
    	agencyContract.setAgency((AgencyInfo) this.getUIContext().get("agency"));
    	
    	agencyContract.setContractState(ContractStateEnum.SAVED);
    	
    	agencyContract.setCommisionStandard(CommisionStandardEnum.Percentum);
    	agencyContract.setAppPayDateType(AppPayDateTypeEnum.TenancyAuditedDay);
    	
    	UserInfo user = SysContext.getSysContext().getCurrentUserInfo();
    	agencyContract.setCreator(user);
    	agencyContract.setCreateTime(new Timestamp(new Date().getTime()));
    	
    	
    	agencyContract.setSigner(user);
    	agencyContract.setCU(SysContext.getSysContext().getCurrentCtrlUnit());
    	agencyContract.setOrgUnit(SysContext.getSysContext().getCurrentOrgUnit().castToFullOrgUnitInfo());
    	agencyContract.setBookedDate(new Date());
    	
    	return agencyContract;
    }
    
    
	protected ICoreBase getBizInterface() throws Exception {
		return AgencyContractFactory.getRemoteInstance();
	}

	protected KDTable getDetailTable() {
		return null;
	}

	protected void attachListeners() {
	}

	protected void detachListeners() {
	}

	protected KDTextField getNumberCtrl() {
		return this.txtNumber;
	}

	protected void checkIsOUSealUp() throws Exception{
		super.checkIsOUSealUp();
	}
    public void loadFields() {
    	super.loadFields();
    	ContractStateEnum cse = this.editData.getContractState();
		if(ContractStateEnum.Audited.equals(cse)||ContractStateEnum.InUsing.equals(cse)){
			this.actionEdit.setEnabled(false);
		}
    }
    public SelectorItemCollection getSelectors() {
    	// TODO Auto-generated method stub
    	SelectorItemCollection coll =super.getSelectors();
    	coll.add("contractState");
    	return coll;
    }
    
    /**
	 * ����������
	 * 
	 * @throws BOSException
	 * @throws CodingRuleException
	 * @throws EASBizException
	 */
	protected void handleCodingRule() throws BOSException, CodingRuleException, EASBizException {

		String currentOrgId = FDCClientHelper.getCurrentOrgId();
		ICodingRuleManager iCodingRuleManager = CodingRuleManagerFactory.getRemoteInstance();

		if (STATUS_ADDNEW.equals(this.oprtState) && iCodingRuleManager.isExist(editData, currentOrgId)) {
			// EditUI�ṩ�˷�������û�е��ã���onload����ã��Ը��ǳ�����loadfields����ĵ��ã��õ���û�д���Ϻ�ѡ��

			boolean isAddView = FDCClientHelper.isCodingRuleAddView(editData, currentOrgId);
			if (isAddView) {
				getNumberByCodingRule(editData, currentOrgId);
			} else {
				String number = null;

				if (iCodingRuleManager.isUseIntermitNumber(editData, currentOrgId)) { // �˴���orgId�벽��1
																						// ��
																						// ��orgIdʱһ�µ�
																						// ��
																						// �ж��û��Ƿ����öϺ�֧�ֹ���
					if (iCodingRuleManager.isUserSelect(editData, currentOrgId)) {
						// �����˶Ϻ�֧�ֹ���,ͬʱ�������û�ѡ��ϺŹ���
						// KDBizPromptBox pb = new KDBizPromptBox();
						CodingRuleIntermilNOBox intermilNOF7 = new CodingRuleIntermilNOBox(editData, currentOrgId, null, null);
						// pb.setSelector(intermilNOF7);
						//Ҫ�ж��Ƿ���ڶϺ�,���򵯳�,���򲻵�///////////////////////////////////
						// ///////
						Object object = null;
						if (iCodingRuleManager.isDHExist(editData, currentOrgId)) {
							intermilNOF7.show();
							object = intermilNOF7.getData();
						}
						if (object != null) {
							number = object.toString();
						}
					}
				}
				txtNumber.setText(number);
			}

			setNumberTextEnabled();
		}
	}

	/**
	 * getNumberByCodingRuleֻ�ṩ�˻�ȡ����Ĺ��ܣ�û���ṩ���õ��ؼ��Ĺ��ܣ�ʵ�ִ˷��������ݱ�������"�Ƿ�������ʾ"
	 * �������ñ��뵽�ؼ�
	 */
	protected void prepareNumber(IObjectValue caller, String number) {
		super.prepareNumber(caller, number);

		txtNumber.setText(number);

	}

	protected void setNumberTextEnabled() {

		if (txtNumber != null) {
			// CostCenterOrgUnitInfo currentCostUnit =
			// SysContext.getSysContext()
			// .getCurrentCostUnit();

			OrgUnitInfo currentCostUnit = SysContext.getSysContext().getCurrentOrgUnit();

			if (currentCostUnit != null) {
				boolean isAllowModify = FDCClientUtils.isAllowModifyNumber(editData, currentCostUnit.getId().toString());

				txtNumber.setEnabled(isAllowModify);
				txtNumber.setEditable(isAllowModify);
				txtNumber.setRequired(isAllowModify);
			}
		}
	}
}