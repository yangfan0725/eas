/**
 * output package name
 */
package com.kingdee.eas.fdc.contract.client;

import java.awt.event.ActionEvent;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.swing.KDTextField;
import com.kingdee.bos.ctrl.swing.StringUtils;
import com.kingdee.bos.ctrl.swing.event.DataChangeEvent;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectStringPK;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.IMetaDataPK;
import com.kingdee.bos.metadata.MetaDataPK;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.base.permission.UserInfo;
import com.kingdee.eas.basedata.assistant.CurrencyInfo;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCDateHelper;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.SourceTypeEnum;
import com.kingdee.eas.fdc.basedata.client.FDCClientHelper;
import com.kingdee.eas.fdc.basedata.client.FDCClientUtils;
import com.kingdee.eas.fdc.contract.ContractBillFactory;
import com.kingdee.eas.fdc.contract.ContractBillInfo;
import com.kingdee.eas.fdc.contract.GuerdonBillFactory;
import com.kingdee.eas.fdc.contract.GuerdonBillInfo;
import com.kingdee.eas.fdc.contract.PutOutTypeEnum;
import com.kingdee.eas.fi.gl.GlUtils;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.MsgBox;

/**
 * output class name
 */
public class GuerdonBillEditUI extends AbstractGuerdonBillEditUI {
	public static final String resourcePath = "com.kingdee.eas.fdc.contract.client.ComAndPriseResource";
	
	private DateFormat formatDay = new SimpleDateFormat("yyyy-MM-dd");

	/**
	 * output class constructor
	 */
	public GuerdonBillEditUI() throws Exception {
		super();
		jbInit() ;
	}

	private void jbInit() {	    
		titleMain = getUITitle();
	}
	
	public void onShow() throws Exception {
		super.onShow();
		this.comboPutOutType.requestFocus();
	}
	
	protected  void fetchInitData() throws Exception{
		
	}
	
	/**
	 * output storeFields method
	 */
	public void storeFields() {
		super.storeFields();
		GuerdonBillInfo info = (GuerdonBillInfo) this.editData;
		info.setPutOutType((PutOutTypeEnum) this.comboPutOutType
				.getSelectedItem());
		info.setNumber(this.txtNumber.getText());
		info.setGuerdonThings(this.txtGuerdonThings.getText());
		info.setGuerdonDes(this.txtGuerdonDes.getText());
		info.setAmount(this.txtAmount.getBigDecimalValue());
		info.setOriginalAmount(this.txtOriginalAmount.getBigDecimalValue());
		info.setExRate(this.txtExRate.getBigDecimalValue());
		info.setCurrency((CurrencyInfo) this.comboCurrency.getSelectedItem());
		info.setName(this.txtName.getText());
	}
	/**
     * 计算本币金额
     *
     */
    private void calLocalAmt() {
    	if(txtOriginalAmount.getBigDecimalValue() != null && txtExRate.getBigDecimalValue() != null) {
    		BigDecimal lAmount = txtOriginalAmount.getBigDecimalValue().multiply(txtExRate.getBigDecimalValue());
    		txtAmount.setNumberValue(lAmount);
    	}
    	else {
    		txtAmount.setNumberValue(null);
    	}
    }

	protected IObjectValue createNewData() {
		GuerdonBillInfo guerdon = new GuerdonBillInfo();
		String contractId = (String) this.getUIContext().get("contractBillId");
		if (contractId != null) {
			try {
				SelectorItemCollection sels = new SelectorItemCollection();
				sels.add(new SelectorItemInfo("*"));
				sels.add(new SelectorItemInfo("contract.codingNumber"));
				sels.add(new SelectorItemInfo("currency.*"));
				//2009-1-6 加入工程项目的查询字段
				sels.add(new SelectorItemInfo("curProject.id"));
				sels.add(new SelectorItemInfo("curProject.codingNumber"));
				ContractBillInfo contract = ContractBillFactory.getRemoteInstance().getContractBillInfo(
								new ObjectUuidPK(BOSUuid.read(contractId)),sels);
				//设置奖励单部分和合同相关属性
				guerdon.setContract(contract);
				//2009-1-6 保存时增加对工程项目的保存
				guerdon.setCurProject(contract.getCurProject());
				guerdon.setCurrency(contract.getCurrency());
				guerdon.setExRate(contract.getExRate());
				guerdon.setOrgUnit(contract.getOrgUnit());
			} catch (Exception e) {
				e.printStackTrace();
			} 
		}
		UserInfo user = SysContext.getSysContext().getCurrentUserInfo();
		guerdon.setCreator(user);
//		guerdon.setCreateTime(new Timestamp(new Date().getTime()));
		try {
			guerdon.setCreateTime(FDCDateHelper.getServerTimeStamp());
		} catch (BOSException e) {
			// TODO 自动生成 catch 块
			e.printStackTrace();
		}
		guerdon.setSourceType(SourceTypeEnum.ADDNEW);
		
		guerdon.setAmount(FDCHelper.ZERO);
		//支付类型
		guerdon.setPutOutType(PutOutTypeEnum.Cash);
		
		return guerdon;
	}

	protected ICoreBase getBizInterface() throws Exception {
		return GuerdonBillFactory.getRemoteInstance();
	}

	public void onLoad() throws Exception {
		FDCClientHelper.initComboCurrency(this.comboCurrency, true);
		super.onLoad();
		this.actionTraceDown.setVisible(false);
		this.actionTraceUp.setVisible(false);
		this.actionCreateFrom.setVisible(false);
		this.actionCreateTo.setVisible(false);
		this.actionAddLine.setVisible(false);
		this.actionRemoveLine.setVisible(false);
		this.actionInsertLine.setVisible(false);
//		this.actionAudit.setVisible(false);
//		this.actionUnAudit.setVisible(false);
		this.actionCopyFrom.setVisible(false);
		this.menuBiz.setVisible(false);
		this.menuTable1.setVisible(false);
		this.menuSubmitOption.setVisible(false);
		this.txtGuerdonDes.setMaxLength(200);
		this.txtGuerdonThings.setMaxLength(200);
		this.txtAmount.setNegatived(false);
		this.txtAmount.setRequired(true);
		this.txtCreateDate.setEnabled(false);
		this.txtCreator.setEnabled(false);
		this.comboCurrency.setEnabled(false);
		this.actionCopy.setVisible(false);
		this.actionCopyFrom.setVisible(false);
		this.actionMultiapprove.setVisible(false);
		this.actionNextPerson.setVisible(false);
		this.txtOriginalAmount.setRequired(true);
		this.actionPrint.setVisible(true);
		this.actionPrint.setEnabled(true);
		this.actionPrintPreview.setVisible(true);
		this.actionPrintPreview.setEnabled(true);
//		 actionAudit.setEnabled(false);
//		 actionUnAudit.setEnabled(false);
	}

	public void loadFields() {
		super.loadFields();
		
		GuerdonBillInfo info = (GuerdonBillInfo) this.editData;
		this.txtContractName.setText(info.getContract().getName());
		this.txtContractNumber.setText(info.getContract().getNumber());
		this.txtContractName.setEnabled(false);
		this.txtContractNumber.setEnabled(false);
		
		this.txtNumber.setText(info.getNumber());
		this.txtName.setText(info.getName());
		
		this.txtGuerdonDes.setText(info.getGuerdonDes());
		this.txtGuerdonThings.setText(info.getGuerdonThings());
		
		
		this.txtOriginalAmount.setValue(info.getOriginalAmount());
		this.txtExRate.setValue(info.getExRate());
		GlUtils.setSelectedItem(comboCurrency,info.getCurrency());
		this.txtAmount.setValue(info.getAmount());
		this.txtAmount.setEnabled(false);	
		GlUtils.setSelectedItem(comboCurrency,info.getCurrency());
				
		comboPutOutType.setSelectedItem(info.getPutOutType());
		
		if (info.getCreator() != null) {
			this.txtCreator.setText(info.getCreator().getName());
		}
		this.txtCreateDate.setText(formatDay.format(new Date(info
				.getCreateTime().getTime())));
		if (info.getAuditor() != null) {
			txtAuditor.setText(info.getAuditor().getName());
		}
		if(info.getAuditTime()!=null){
			this.txtAuditTime.setText(formatDay.format(new Date(info
				.getAuditTime().getTime())));
		}
		this.chkfiVouchered.setSelected(info.isFiVouchered());
		setSaveActionStatus();
		
		txtNumber.requestFocus();
		
		//2009-1-21 在loadFields加入设置审批、反审批按钮状态的方法，在通过上一、下一功能切换单据时，正确显示审批、反审批按钮。
		setAuditButtonStatus(this.getOprtState());
	}

	public SelectorItemCollection getSelectors() {
		SelectorItemCollection selectors = super.getSelectors();
		selectors.add("number");
		selectors.add("name");
		selectors.add("state");
		selectors.add("amount");
		selectors.add("exRate");
		selectors.add("originalAmount");
		selectors.add("createTime");
		selectors.add("auditTime");
		selectors.add("guerdonDes");
		selectors.add("guerdonThings");
		selectors.add("putOutType");
		
		selectors.add("contract.number");
		selectors.add("contract.name");
		selectors.add("currency.number");
		selectors.add("currency.name");
		selectors.add("creator.name");
		selectors.add("auditor.name");
		
		selectors.add("orgUnit.name");
		selectors.add("orgUnit.number");
		selectors.add("fiVouchered");
		
		return selectors;
	}

	protected KDTextField getNumberCtrl() {
		return this.txtNumber;
	}

	 public void actionPrint_actionPerformed(ActionEvent e) throws Exception {
		ArrayList idList = new ArrayList();
    	if (editData != null && !StringUtils.isEmpty(editData.getString("id"))) {
    		idList.add(editData.getString("id"));
    	}
        if (idList == null || idList.size() == 0 || getTDQueryPK() == null || getTDFileName() == null){
			MsgBox.showWarning(this,FDCClientUtils.getRes("cantPrint"));
			return;
        }          
        GuerdonBillPrintProvider data = new GuerdonBillPrintProvider(editData.getString("id"),getTDQueryPK(),getATTCHQueryPK());
        com.kingdee.bos.ctrl.report.forapp.kdnote.client.KDNoteHelper appHlp = new com.kingdee.bos.ctrl.report.forapp.kdnote.client.KDNoteHelper();
        appHlp.print(getTDFileName(), data, javax.swing.SwingUtilities.getWindowAncestor(this));
	}

	public void actionPrintPreview_actionPerformed(ActionEvent e) throws Exception {
        ArrayList idList = new ArrayList();
        if (editData != null && !StringUtils.isEmpty(editData.getString("id"))) {
    		idList.add(editData.getString("id"));
    	}
        if (idList == null || idList.size() == 0 || getTDQueryPK() == null || getTDFileName() == null){
			MsgBox.showWarning(this,FDCClientUtils.getRes("cantPrint"));
			return;
        }
        GuerdonBillPrintProvider data = new GuerdonBillPrintProvider(editData.getString("id"),getTDQueryPK(),getATTCHQueryPK());
        com.kingdee.bos.ctrl.report.forapp.kdnote.client.KDNoteHelper appHlp = new com.kingdee.bos.ctrl.report.forapp.kdnote.client.KDNoteHelper();
        appHlp.printPreview(getTDFileName(), data, javax.swing.SwingUtilities.getWindowAncestor(this));
	}
	//获得扣款单套打对应的目录
	protected String getTDFileName() {
    	return "/bim/fdc/contract/GuerdonBill";
	}
	//对应的套打Query
	protected IMetaDataPK getTDQueryPK() {
    	return new MetaDataPK("com.kingdee.eas.fdc.contract.app.GuerdonBillForPrintQuery");
	}
	
	// 对应的套打Query
	protected IMetaDataPK getATTCHQueryPK() {
		return new MetaDataPK(
				"com.kingdee.eas.fdc.contract.app.AttchmentForPrintQuery");
	}
	
	public void actionAddNew_actionPerformed(ActionEvent e) throws Exception {
		
		//检查合同是否已经结算
		GuerdonBillInfo info = (GuerdonBillInfo) this.editData;
		String contractId = info.getContract().getId().toString();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(
				new FilterItemInfo("id", contractId));
		//filter.getFilterItems().add(new FilterItemInfo("state", FDCBillStateEnum.AUDITTED));
		filter.getFilterItems().add(new FilterItemInfo("hasSettled", Boolean.TRUE));
		
		if (ContractBillFactory.getRemoteInstance().exists(filter)) {
			MsgBox.showError(this,"合同已经结算，不能继续增加违约金单据！");
			SysUtil.abort();
		}
		getUIContext().put("contractBillId",contractId);
		
		super.actionAddNew_actionPerformed(e);
		comboCurrency.setEnabled(false);
	}
	
	public void actionEdit_actionPerformed(ActionEvent e) throws Exception {
		super.actionEdit_actionPerformed(e);
		this.txtContractName.setEditable(false);
		this.txtContractNumber.setEditable(false);
		this.txtCreator.setEnabled(false);
		this.txtCreateDate.setEnabled(false);
		this.comboCurrency.setEnabled(false);
		this.txtAmount.setEnabled(false);
	}

	protected void verifyInput(ActionEvent e) throws Exception {
		super.verifyInput(e);
//		if (this.txtAmount.getBigDecimalValue() == null
//				|| this.txtAmount.getBigDecimalValue().compareTo(FDCHelper.ZERO) == 0) {
//			txtOriginalAmount.requestFocus(true);
////			MsgBox.showWarning(getResource("AmountNoNull"));
//			MsgBox.showWarning(this,"奖励本币金额不能为空或者0！");
//			SysUtil.abort();
//		}
	}

	public static String getResource(String resourceName) {
		return EASResource.getString(resourcePath, resourceName);
	}
	public void actionSubmit_actionPerformed(ActionEvent e) throws Exception {
		super.actionSubmit_actionPerformed(e);
		this.comboPutOutType.requestFocus();
	}
	
	/**
     * 覆盖抽象类处理编码规则的行为,统一在FDCBillEditUI.handCodingRule方法中处理
     */
    protected void setAutoNumberByOrg(String orgType) {
    	
    }
    
    public void checkBeforeAuditOrUnAudit(FDCBillStateEnum state, String warning) throws Exception {
    	super.checkBeforeAuditOrUnAudit(state, warning);
    	
    	if(state != null && state == FDCBillStateEnum.AUDITTED) {
    		String id = getSelectBOID();
    		SelectorItemCollection selector = new SelectorItemCollection();
    		selector.add("isGuerdoned");
    		boolean isIsGuerdoned = false;
    		try {
				GuerdonBillInfo guerdonBillInfo = GuerdonBillFactory.getRemoteInstance().getGuerdonBillInfo(new ObjectStringPK(id), selector);
				
				isIsGuerdoned = guerdonBillInfo.isIsGuerdoned();
			} catch (Exception e) {
				handUIException(e);
			} 
    		
			if(isIsGuerdoned) {
				MsgBox.showWarning(this, "该奖励单已经奖励，不能反审批");
			}
    	}
    }
    
    public void actionCopy_actionPerformed(ActionEvent e) throws Exception {
    	// TODO Auto-generated method stub
//    	super.actionCopy_actionPerformed(e);
    }

	protected void attachListeners() {
		// TODO 自动生成方法存根
		
	}

	protected void detachListeners() {
		// TODO 自动生成方法存根
		
	}
	
    protected boolean isUseMainMenuAsTitle(){
    	return false;
    }
    protected void txtOriginalAmount_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
    {
    	calLocalAmt();
    }
    
	/*
	 *  在FDCBillEdit内将其改成抽象方法,以强制要求子类去实现
	 * @see com.kingdee.eas.fdc.basedata.client.FDCBillEditUI#getDetailTable()
	 */
	protected KDTable getDetailTable() {
		return null;
	}

	protected void txtExRate_dataChanged(DataChangeEvent e) throws Exception {
		// TODO 自动生成方法存根
		calLocalAmt();
	}
}