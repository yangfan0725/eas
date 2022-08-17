/**
 * output package name
 */
package com.kingdee.eas.fdc.finance.client;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.EventListener;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.sql.RowSet;
import javax.swing.Action;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.KeyStroke;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.ctrl.extendcontrols.ExtendParser;
import com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox;
import com.kingdee.bos.ctrl.kdf.table.ICell;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTDefaultCellEditor;
import com.kingdee.bos.ctrl.kdf.table.KDTIndexColumn;
import com.kingdee.bos.ctrl.kdf.table.KDTMergeManager;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectBlock;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.event.BeforeActionEvent;
import com.kingdee.bos.ctrl.kdf.table.event.BeforeActionListener;
import com.kingdee.bos.ctrl.kdf.table.event.KDTEditAdapter;
import com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent;
import com.kingdee.bos.ctrl.kdf.util.style.StyleAttributes;
import com.kingdee.bos.ctrl.kdf.util.style.Styles.HorizontalAlignment;
import com.kingdee.bos.ctrl.swing.KDComboBox;
import com.kingdee.bos.ctrl.swing.KDFormattedTextField;
import com.kingdee.bos.ctrl.swing.KDFrame;
import com.kingdee.bos.ctrl.swing.KDLayout;
import com.kingdee.bos.ctrl.swing.KDPanel;
import com.kingdee.bos.ctrl.swing.KDTabbedPane;
import com.kingdee.bos.ctrl.swing.StringUtils;
import com.kingdee.bos.ctrl.swing.event.CommitEvent;
import com.kingdee.bos.ctrl.swing.event.DataChangeEvent;
import com.kingdee.bos.ctrl.swing.event.DataChangeListener;
import com.kingdee.bos.ctrl.swing.event.SelectorEvent;
import com.kingdee.bos.ctrl.swing.util.CtrlCommonConstant;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.IMetaDataPK;
import com.kingdee.bos.metadata.MetaDataPK;
import com.kingdee.bos.metadata.data.SortType;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemCollection;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.IUIFactory;
import com.kingdee.bos.ui.face.IUIObject;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.ItemAction;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.base.multiapprove.MultiApproveCollection;
import com.kingdee.eas.base.multiapprove.MultiApproveFactory;
import com.kingdee.eas.base.multiapprove.MultiApproveInfo;
import com.kingdee.eas.base.param.ParamControlFactory;
import com.kingdee.eas.base.param.util.ParamManager;
import com.kingdee.eas.base.uiframe.client.UIFactoryHelper;
import com.kingdee.eas.base.uiframe.client.UIModelDialog;
import com.kingdee.eas.base.uiframe.client.UIModelDialogFactory;
import com.kingdee.eas.basedata.assistant.AccountBankCollection;
import com.kingdee.eas.basedata.assistant.AccountBankFactory;
import com.kingdee.eas.basedata.assistant.AccountBankInfo;
import com.kingdee.eas.basedata.assistant.AccountType;
import com.kingdee.eas.basedata.assistant.BankFactory;
import com.kingdee.eas.basedata.assistant.BankInfo;
import com.kingdee.eas.basedata.assistant.CityCollection;
import com.kingdee.eas.basedata.assistant.CityFactory;
import com.kingdee.eas.basedata.assistant.CityInfo;
import com.kingdee.eas.basedata.assistant.CurrencyInfo;
import com.kingdee.eas.basedata.assistant.ExchangeRateInfo;
import com.kingdee.eas.basedata.assistant.ExchangeTableInfo;
import com.kingdee.eas.basedata.assistant.IBank;
import com.kingdee.eas.basedata.assistant.PeriodInfo;
import com.kingdee.eas.basedata.assistant.ProvinceInfo;
import com.kingdee.eas.basedata.assistant.SettlementTypeInfo;
import com.kingdee.eas.basedata.master.account.AccountViewInfo;
import com.kingdee.eas.basedata.master.account.client.AccountPromptBox;
import com.kingdee.eas.basedata.master.auxacct.AsstActTypeCollection;
import com.kingdee.eas.basedata.master.auxacct.AsstActTypeFactory;
import com.kingdee.eas.basedata.master.auxacct.AsstActTypeInfo;
import com.kingdee.eas.basedata.master.auxacct.IAsstActType;
import com.kingdee.eas.basedata.master.cssp.CustomerCompanyBankInfo;
import com.kingdee.eas.basedata.master.cssp.CustomerCompanyInfoInfo;
import com.kingdee.eas.basedata.master.cssp.CustomerFactory;
import com.kingdee.eas.basedata.master.cssp.CustomerInfo;
import com.kingdee.eas.basedata.master.cssp.SupplierCompanyBankInfo;
import com.kingdee.eas.basedata.master.cssp.SupplierFactory;
import com.kingdee.eas.basedata.master.cssp.SupplierInfo;
import com.kingdee.eas.basedata.org.CompanyOrgUnitInfo;
import com.kingdee.eas.basedata.org.FullOrgUnitInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCConstants;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCNumberHelper;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.basedata.PaymentTypeInfo;
import com.kingdee.eas.fdc.basedata.TypeInfo;
import com.kingdee.eas.fdc.basedata.client.FDCClientHelper;
import com.kingdee.eas.fdc.basedata.client.FDCClientUtils;
import com.kingdee.eas.fdc.basedata.client.FDCClientVerifyHelper;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.basedata.client.FDCSplitClientHelper;
import com.kingdee.eas.fdc.contract.CompensationOfPayReqBillCollection;
import com.kingdee.eas.fdc.contract.CompensationOfPayReqBillInfo;
import com.kingdee.eas.fdc.contract.ContractBillFactory;
import com.kingdee.eas.fdc.contract.ContractBillInfo;
import com.kingdee.eas.fdc.contract.ContractChangeBillCollection;
import com.kingdee.eas.fdc.contract.ContractChangeBillInfo;
import com.kingdee.eas.fdc.contract.ContractSettlementBillFactory;
import com.kingdee.eas.fdc.contract.ContractSettlementBillInfo;
import com.kingdee.eas.fdc.contract.ContractWithoutTextFactory;
import com.kingdee.eas.fdc.contract.ContractWithoutTextInfo;
import com.kingdee.eas.fdc.contract.DeductOfPayReqBillCollection;
import com.kingdee.eas.fdc.contract.DeductOfPayReqBillInfo;
import com.kingdee.eas.fdc.contract.FDCUtils;
import com.kingdee.eas.fdc.contract.GuerdonOfPayReqBillCollection;
import com.kingdee.eas.fdc.contract.GuerdonOfPayReqBillInfo;
import com.kingdee.eas.fdc.contract.PayReqPrjPayEntryInfo;
import com.kingdee.eas.fdc.contract.PayReqUtils;
import com.kingdee.eas.fdc.contract.PayRequestBillCollection;
import com.kingdee.eas.fdc.contract.PayRequestBillConfirmEntryCollection;
import com.kingdee.eas.fdc.contract.PayRequestBillConfirmEntryInfo;
import com.kingdee.eas.fdc.contract.PayRequestBillEntryCollection;
import com.kingdee.eas.fdc.contract.PayRequestBillEntryFactory;
import com.kingdee.eas.fdc.contract.PayRequestBillEntryInfo;
import com.kingdee.eas.fdc.contract.PayRequestBillFactory;
import com.kingdee.eas.fdc.contract.PayRequestBillInfo;
import com.kingdee.eas.fdc.contract.client.AbstractSplitInvokeStrategy;
import com.kingdee.eas.fdc.contract.client.BudgetViewUtils;
import com.kingdee.eas.fdc.contract.client.ContractClientUtils;
import com.kingdee.eas.fdc.contract.client.ContractWithoutTextEditUI;
import com.kingdee.eas.fdc.contract.client.PayRequestBillEditUI;
import com.kingdee.eas.fdc.contract.client.PaymentSplitInvokeStrategy;
import com.kingdee.eas.fdc.contract.client.SplitInvokeStrategyFactory;
import com.kingdee.eas.fdc.finance.FDCDepConPayPlanContractInfo;
import com.kingdee.eas.fdc.finance.FDCDepConPayPlanNoContractInfo;
import com.kingdee.eas.fdc.finance.FDCDepConPayPlanUnsettledConInfo;
import com.kingdee.eas.fdc.finance.FDCPaymentBillCollection;
import com.kingdee.eas.fdc.finance.FDCPaymentBillFactory;
import com.kingdee.eas.fdc.finance.FDCPaymentBillInfo;
import com.kingdee.eas.fdc.finance.FinanceException;
import com.kingdee.eas.fdc.finance.PaymentFacadeFactory;
import com.kingdee.eas.fdc.finance.PaymentPrjPayEntryInfo;
import com.kingdee.eas.fdc.finance.PaymentSplitFactory;
import com.kingdee.eas.fdc.finance.PaymentSplitHelper;
import com.kingdee.eas.fdc.sellhouse.client.PurchaseManageEditUI;
import com.kingdee.eas.fi.cas.BatchFetchParamFacadeFactory;
import com.kingdee.eas.fi.cas.BgCtrlPaymentBillHandler;
import com.kingdee.eas.fi.cas.BillStatusEnum;
import com.kingdee.eas.fi.cas.BizTypeEnum;
import com.kingdee.eas.fi.cas.CashDataTypeEnum;
import com.kingdee.eas.fi.cas.CashManagementFactory;
import com.kingdee.eas.fi.cas.CashSysParamConstants;
import com.kingdee.eas.fi.cas.ICashManagement;
import com.kingdee.eas.fi.cas.IPaymentBill;
import com.kingdee.eas.fi.cas.PaymentBillCollection;
import com.kingdee.eas.fi.cas.PaymentBillEntryInfo;
import com.kingdee.eas.fi.cas.PaymentBillFactory;
import com.kingdee.eas.fi.cas.PaymentBillInfo;
import com.kingdee.eas.fi.cas.RecPayException;
import com.kingdee.eas.fi.cas.RecPayHelper;
import com.kingdee.eas.fi.cas.SettlementStatusEnum;
import com.kingdee.eas.fi.cas.SourceTypeEnum;
import com.kingdee.eas.fi.cas.UrgentDegreeEnum;
import com.kingdee.eas.fi.cas.client.CasRecPayHandler;
import com.kingdee.eas.fi.gl.GlUtils;
import com.kingdee.eas.fi.gl.common.GLf7Utils;
import com.kingdee.eas.fm.be.BankInterfaceTypeEnum;
import com.kingdee.eas.fm.common.CompanyF7Factory;
import com.kingdee.eas.fm.common.ContextHelperFactory;
import com.kingdee.eas.fm.common.FMConstants;
import com.kingdee.eas.fm.common.FMException;
import com.kingdee.eas.fm.common.FMHelper;
import com.kingdee.eas.fm.common.FMSysDefinedEnum;
import com.kingdee.eas.fm.common.client.FMClientHelper;
import com.kingdee.eas.fm.common.client.FMClientVerifyHelper;
import com.kingdee.eas.fm.fpl.FPItemDirectionEnum;
import com.kingdee.eas.fm.fpl.FpItemInfo;
import com.kingdee.eas.fm.fs.InnerAccountInfo;
import com.kingdee.eas.fm.fs.SettBizTypeEnum;
import com.kingdee.eas.fm.fs.SettBizTypeInfo;
import com.kingdee.eas.fm.nt.ChequeInfo;
import com.kingdee.eas.fm.nt.ChequeStatusEnum;
import com.kingdee.eas.fm.nt.NTTypeGroupEnum;
import com.kingdee.eas.fm.nt.client.ChequeEditUI;
import com.kingdee.eas.framework.CoreBillBaseCollection;
import com.kingdee.eas.framework.DataBaseInfo;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.framework.ObjectBaseInfo;
import com.kingdee.eas.framework.client.FrameWorkClientUtils;
import com.kingdee.eas.framework.client.IBTPBillEdit;
import com.kingdee.eas.framework.client.ListUI;
import com.kingdee.eas.ma.budget.BgCtrlParamCollection;
import com.kingdee.eas.ma.budget.client.NewBgItemDialog;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.MsgBox;
import com.kingdee.jdbc.rowset.IRowSet;
import com.kingdee.util.DateTimeUtils;

/**
 * ���ز��ɱ�������༭���� version: 5.1.3 �����������������޹�˾��Ȩ����
 */

public class PaymentBillEditUI extends AbstractPaymentBillEditUI {

	private static final Logger logger = CoreUIObject
			.getLogger(PaymentBillEditUI.class);
	/**�Ƿ��һ�μ���,�������Ʊ��ļ�����ʾ*/
//	private boolean isFirstLoad = true;
	/**����������*/
//	private Window calcDiag = null; 

	private static final Color noEditColor = new Color(232, 232, 227);

	/**ȡ�õ�ǰ��˾*/
	private CompanyOrgUnitInfo currentCompany ;
	
	/**��ǰ��֯*/
	protected FullOrgUnitInfo orgUnitInfo;
	
	/**��ǰ��λ��*/
	private CurrencyInfo baseCurrency ;
	
	/**������Ŀ*/
	private CurProjectInfo curProject ;

	/**��ʼ����*/
	private Map initData ;
	
    /**��;�ֶ��ܿ�*/
    protected int usageLegth = 90;
    
    /**������Ŀ*/
	private AsstActTypeInfo supplierAssitType;

	private AsstActTypeInfo innerAccountActType;
	
	/** �ո�������� */
	private CasRecPayHandler handler = new CasRecPayHandler();

	/** ���ڰ�cell����ֵ������map keyΪ���Ե�info��������valueΪcell������ */
	private HashMap bindCellMap = new HashMap(20);

	// ���ı���ͬ��Ϊ����һ������θ����ˡ� Added by Owen_wen 2011-05-12 R110130-022
	// private boolean canModifyAmount = true;
	
	/**�������������޸Ľ��(���������ı���ͬ���ɵĸ��)*/
//	private boolean canModifyAmountAfterAuit = false;
	
	/** ����������޸ĸ����˻��븶���Ŀ */
	private boolean canModifyAccountAfterAuit = false;
	
	/** ����ύ������ʱ���Ƿ�У���ͬδ��ȫ��� */
	private boolean checkAllSplit = true;
	/** ����ύУ�鸶���Ƿ���ȫ��� */
	private boolean checkPaymentAllSplit = false;
	/**����ƾ֤ʱ�Ƿ�У����*/
	private boolean isPaymentSplit = true;
    /** ������⣺�����⣬�������޸ģ��鿴��ƾ֤����*/
    protected String titleMain, titleNew, titleModify, titleView, billIndex = "";
    
    private boolean isBaseCurrency = true;
	
    /**�׹�����,��Ϊtrueʱ�����ɼ׹�����ͬ�б�ҳǩ*/
    private boolean isPartA = FDCUtils.getDefaultFDCParamByKey(null, SysContext.getSysContext().getCurrentFIUnit().getId().toString(), FDCConstants.FDC_PARAM_CREATEPARTADEDUCT);
    
    private PaymentBillTableHelper paymentBillTableHelper = new PaymentBillTableHelper(this);
    
    /** �Ƿ�׹��ĺ�ͬ */
	private boolean isPartAMaterialCon = false;

	/** ��ͬ�ڹ��̿�ԭ�� */
	private BigDecimal amtInContractOri = FDCHelper.ZERO;

	/** �����ͬ�Ƿ��ѽ��� */
	private boolean hasSettled = false;

	private String contractId = null;
	
	/** ������ϸ�� */
	private PaymentBillDetailHelper detailHelper = null;
	/**����ģʽ*/
	private boolean isAdjustVourcherModel = false;
	/** ��ģʽһ�廯 */
	private boolean isSimpleFinancial = true;
	private boolean isSimpleFinancialExtend = true;
	private boolean isFinancial = false;
	/** ��һ�廯����Ʊ*/
	private boolean isSimpleInvoice = false;
	/** ���뵥���ȿ������Զ�Ϊ100% */
	private boolean isAutoComplete = false;
	/** ������ȷ�������븶�������Ƿ���� */
	private boolean isSeparate = false;
	
	/** �Ƿ���붯̬�ɱ� */
	private boolean isCostSplit = false;	
	
	/**
	 * output class constructor
	 */
	public PaymentBillEditUI() throws Exception {
		super();
		
		jbInit() ;
	}
	
    public String getStr(String name) {
        return getStr(FDCConstants.VoucherResource, name);
    }
    
    public String getStr(String resFile,String name) {
        return EASResource.getString(resFile, name);
    }

	private void jbInit() {
	    titleMain = getUITitle();
	    titleNew = getStr("titleNew");
	    titleModify = getStr("titleModify");
	    titleView = getStr("titleView");
	}
	
    public void setDataObject(IObjectValue dataObject) {
    	
    	PaymentBillInfo info = (PaymentBillInfo)dataObject;
		//Ӧ���ɵĸ���Ǳ����ڲ���������ĳ��ɵģ����Ƿ��ز��ĸ��������֮������ȫ�������޸�
    	if(info==null){
    		return ;
    	}
		BillStatusEnum billState = info.getBillStatus();
    	if (this.getBOTPViewStatus() == 1) {
    		if (billState==null){
    			info.setBillStatus(BillStatusEnum.SAVE);
    		}
    	}else{
			if (billState.equals(BillStatusEnum.PAYED)) {
				this.setOprtState(STATUS_VIEW);			
			}	
			
			if (billState.equals(BillStatusEnum.AUDITED)){
				if(!canModifyAccountAfterAuit){
					this.setOprtState(STATUS_VIEW);			
				}
			}
    	}
    	
    	if(this.getUIContext().get("ChequeReimBurse")!=null&&((Integer)this.getUIContext().get("ChequeReimBurse")).intValue()==1)
    	{
    		this.setOprtState(STATUS_EDIT);	
    	}
        super.setDataObject(dataObject);
    }

	/**
	 * ���ݸ���ĵ���״̬�趨����ִ�еĲ���
	 * 
	 * @author:zhonghui_luo 2006-10-13 14:22:04
	 * 
	 */
	public void setOprtStateByBillStatus() {
		actionViewBgBalance.setEnabled(true);
		// actionViewBgBalance.setVisible(false);
		
		setButtonStatus(this.getOprtState());
		
		setNumber();
		// btnInputCollect.setEnabled(canModifyAmount &&
		// !STATUS_VIEW.equals(this.oprtState));
		btnInputCollect.setEnabled(!STATUS_VIEW.equals(this.oprtState));
		
		if(prmtPayerAccountBank.getValue() != null && prmtPayerAccountBank.isEnabled()){
			prmtPayerAccount.setEnabled(false);
		}else{
			prmtPayerAccount.setEnabled(true);
		}
		this.comboPayeeType.setEnabled(false);
//		this.prmtPayee.setEnabled(false);
		
		this.prmtActFdcPayeeName.setEnabled(false);
	}

    protected void setButtonStatus(String oprtType){
    	this.pkBizDate.setAccessAuthority(CtrlCommonConstant.AUTHORITY_COMMON);
		this.pkBizDate.setEnabled(false);	
		if (STATUS_FINDVIEW.equals(this.oprtState)) {
			actionEdit.setEnabled(false);
			actionSave.setEnabled(false);
			actionSubmit.setEnabled(false);
			actionClosePayReq.setVisible(false);
			actionCancelClosePayReq.setVisible(false);
			actionAudit.setVisible(false);
			actionAntiAudit.setVisible(false);
			actionAudit.setEnabled(false);
			actionAntiAudit.setEnabled(false);
			
			actionPay.setVisible(false);
			actionRefresh.setVisible(false);
			actionCreateFrom.setVisible(false);
			actionTraceUp.setVisible(false);
			if(checkPaymentAllSplit){
				actionSplit.setVisible(true);
				actionSplit.setEnabled(true);
			}else{
				actionSplit.setVisible(false);
				actionSplit.setEnabled(false);
			}
			
		} else {
			actionPay.setVisible(false);
			if(checkPaymentAllSplit){
				actionSplit.setVisible(true);
				actionSplit.setEnabled(true);
			}else{
				actionSplit.setVisible(false);
				actionSplit.setEnabled(false);
			}
			if (editData != null && editData.getBillStatus() != null) {
				if (editData.getBillStatus() == BillStatusEnum.SUBMIT||editData.getBillStatus() == BillStatusEnum.APPROVED) {
					actionSave.setEnabled(false);
					actionAudit.setEnabled(true);
					actionAudit.setVisible(true);
					actionEdit.setEnabled(true);
					actionAntiAudit.setEnabled(false);
					actionAntiAudit.setVisible(false);
					// txtNumber.setEnabled(true);
				} else if (editData.getBillStatus() == BillStatusEnum.AUDITING) {
					actionEdit.setEnabled(false);
					actionAntiAudit.setEnabled(false);
					actionAntiAudit.setVisible(false);
					actionSave.setEnabled(false);
					actionSubmit.setEnabled(false);
					actionRemove.setEnabled(false);
				} else if (editData.getBillStatus() == BillStatusEnum.AUDITED) {
					actionEdit.setEnabled(false);
					actionAudit.setEnabled(false);
					actionAntiAudit.setEnabled(true);
					actionAudit.setVisible(false);
					actionAntiAudit.setVisible(true);
					actionSave.setEnabled(false);
					actionSubmit.setEnabled(false);
					actionRemove.setEnabled(false);
				} else if (editData.getBillStatus() == BillStatusEnum.PAYED) {
					actionEdit.setEnabled(false);
					actionSave.setEnabled(false);
					actionAudit.setVisible(false);
					actionAntiAudit.setVisible(false);
					actionAudit.setEnabled(false);
					actionAntiAudit.setEnabled(false);
					actionSubmit.setEnabled(false);
					actionRemove.setEnabled(false);
				} else {
					actionAntiAudit.setEnabled(false);
					actionAntiAudit.setVisible(false);
					actionEdit.setEnabled(true);
				}
			}
			if (STATUS_EDIT.equals(this.oprtState)
					|| STATUS_ADDNEW.equals(this.oprtState)) {
				// txtNumber.setEnabled(false);
				actionRefresh.setEnabled(true);
				actionEdit.setEnabled(false);
				if (editData.getBillStatus() == BillStatusEnum.AUDITED)
					actionPay.setEnabled(true);
				else
					actionPay.setEnabled(false);
			}
			if (STATUS_ADDNEW.equals(this.oprtState)) {
				actionRefresh.setEnabled(false);
				actionSave.setEnabled(true);
				actionPay.setEnabled(false);
			}
			if (STATUS_VIEW.equals(this.oprtState) && editData != null
					&& editData.getBillStatus() == BillStatusEnum.AUDITED) {
				actionPay.setEnabled(true);
				kdtEntries.getStyleAttributes().setLocked(true);
			}
			
			if (STATUS_VIEW.equals(this.oprtState)) {
				actionCreateFrom.setEnabled(false);
			}else{
//				actionAudit.setEnabled(false);
//				actionAntiAudit.setEnabled(false);
//				actionAudit.setVisible(false);
//				actionAntiAudit.setVisible(false);
			}
			
			if(!canModifyAccountAfterAuit){
				return ;
			}
			//�������,����������޸�����			
			PaymentBillInfo info = (PaymentBillInfo) getDataObject();
			if(info!=null){
				BillStatusEnum billStatus = BillStatusEnum.SAVE;
				if (info.getBillStatus() != null) {
					billStatus = info.getBillStatus();
				}
				
				if (billStatus.equals(BillStatusEnum.AUDITED) && getOprtState().equals(OprtState.EDIT)){
					boolean isVoucher = info.isFiVouchered();
					boolean isNoText = ((BOSUuid.read(editData.getContractBillId()).getType())
							.equals(new ContractWithoutTextInfo().getBOSType())) ;
					
					boolean canModifyBizDate = (billStatus.equals(BillStatusEnum.SAVE)
							|| billStatus.equals(BillStatusEnum.SUBMIT) 
							|| billStatus.equals(BillStatusEnum.AUDITED)
							|| billStatus.equals(BillStatusEnum.APPROVED))
							&& !isVoucher && !isNoText;
//					pkBizDate.setEnabled(canModifyBizDate);	
					if(prmtPayerAccountBank.getValue() != null){
						prmtPayerAccountBank.setEnabled(canModifyBizDate);						
						prmtPayerAccount.setEnabled(false);
					}else{
						prmtPayerAccountBank.setEnabled(false);
						prmtPayerAccount.setEnabled(canModifyBizDate);
					}
					btnInputCollect.setEnabled(canModifyBizDate);
					//2009-2-5 ���ò���FDC302_MODIFY��Ӧ�����տ�����Ҳ�����޸ģ������տ�����Ϊ�գ����Ѹ����Ŀ��Ϊ���п�Ŀʱ���޷��ύ��
					txtPayeeBank.setEnabled(canModifyBizDate);
					//kdtEntries.getStyleAttributes().setLocked(!canModifyBizDate);					
					//kdtEntries.setEnabled(canModifyBizDate);		
					
					int last = getDetailTable().getRowCount() - 1;
					if(last>0){
						// �������
						getDetailTable().getCell(last, 5).getStyleAttributes().setLocked(true);
						getDetailTable().getCell(last, 5).getStyleAttributes().setBackground(noEditColor);
						
						if(!canModifyBizDate){
							Set set = bindCellMap.keySet();
							for (Iterator iter = set.iterator(); iter.hasNext();) {
								Object obj = bindCellMap.get(iter.next());
								if (obj instanceof ICell) {
									ICell cell = (ICell) obj;
									if (!cell.getStyleAttributes().isLocked()) {
										cell.getStyleAttributes().setLocked(true);
									}
								}
							}
							//�׹�
							getDetailTable().getCell(last - 4, 4).getStyleAttributes().setLocked(true);
						}
					}
					
					actionSubmit.setEnabled(canModifyBizDate);
					
					comboFeeType.setEnabled(false);
					prmtuseDepartment.setEnabled(false);
					txtrecProvince.setEnabled(false);
					txtrecCity.setEnabled(false);

					difPlace.setEnabled(false);
//					txtPayeeBank.setEnabled(false);
					txtPayeeAccountBank.setEnabled(false);
					prmtCurrency.setEnabled(false);
					prmtSettleBizType.setEnabled(false);
					mergencyState.setEnabled(false);

					txtAccessoryAmt.setEnabled(false);
					txtSummary.setEnabled(false);
					txtUsage.setEnabled(false);
					prmtFpItem.setEnabled(false);
					prmtSettlementType.setEnabled(false);
					contDescription.setEnabled(false);
					f7SettleNumber.setEnabled(false);
					
				}
			}
		}
    }
	protected void f7SettleNumber_dataChanged(DataChangeEvent e)
			throws Exception {
	}

	/**
	 * 
	 * �޸ģ����ڿ���¼���ı�������ֱ�Ӱ�willCommit�й��ڹ��˵�ɾ��
	 * 
	 * @author:peng_peng1
	 * @see com.kingdee.eas.fi.cas.client.AbstractCasPaymentBillUI#f7SettleNumber_willCommit(com.kingdee.bos.ctrl.swing.event.CommitEvent)
	 */
	protected void f7SettleNumber_willCommit(CommitEvent e) throws Exception {
		super.f7SettleNumber_willCommit(e);
		ExtendParser parser = new ExtendParser(f7SettleNumber);
		f7SettleNumber.setCommitParser(parser);
		f7SettleNumber.setCommitFormat(null);

	}
	

	protected void f7SettleNumber_willShow(SelectorEvent e) throws Exception {
		super.f7SettleNumber_willShow(e);

		SettlementTypeInfo settType = (SettlementTypeInfo) prmtSettlementType
				.getData();
		if (settType != null) {
			if (settType.getNtType() != null) {//
				
				if (NTTypeGroupEnum.PaymentCheque.equals(settType.getNtType()
						.getSuperGroup())
					) {//R100130-010
					EntityViewInfo evi = new EntityViewInfo();
					evi.setFilter(getChequeFilter());
					
					f7SettleNumber.setEntityViewInfo(evi);
					f7SettleNumber.getQueryAgent().resetRuntimeEntityView();
					if(f7SettleNumber.isEnabledMultiSelection()){
						String tempSettle=null;
						if(f7SettleNumber.getData() instanceof String){
							tempSettle=(String)f7SettleNumber.getData();
							ChequeInfo cheque=new ChequeInfo();
							cheque.setNumber(tempSettle);
							f7SettleNumber.setData(new Object[]{cheque});
						}
						f7SettleNumber.setEnabledMultiSelection(false);
						if(f7SettleNumber.getData() instanceof ChequeInfo){
							f7SettleNumber.setData(tempSettle);
						}
					}
					return;
				}
			}

			// ���û��Ʊ�����ͣ����߲�ΪӦ��Ʊ�ݣ�����Ϊ��
			e.setCanceled(true);
			f7SettleNumber.setCommitFormat(null);
		} else {
			e.setCanceled(true);
			f7SettleNumber.setCommitFormat(null);
		}
	}
	private FilterInfo getChequeFilter() throws EASBizException, BOSException{
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(
				new FilterItemInfo("keepCompany.id", currentCompany.getId()
						.toString()));
		filter.getFilterItems().add(
				new FilterItemInfo("company.id", currentCompany.getId()
						.toString()));
		filter.getFilterItems().add(new FilterItemInfo("state", new Integer(ChequeStatusEnum.NEW_VALUE)));
		filter.setMaskString("(#0 OR #1) AND #2");
		if(prmtPayerAccountBank.getValue()!=null)
		{
			AccountBankInfo accountBankInfo = (AccountBankInfo) prmtPayerAccountBank
					.getValue();
			FilterInfo filterAccBank = new FilterInfo();
			filterAccBank.getFilterItems().add(
					new FilterItemInfo("bankAcct.id", accountBankInfo.getId()
							.toString()));
			
			if(accountBankInfo.getBank()==null){
				AccountBankInfo acbInfo=AccountBankFactory.getRemoteInstance().getAccountBankInfo(new ObjectUuidPK(accountBankInfo.getId()));
				accountBankInfo.setBank(acbInfo.getBank());
			}
			filterAccBank.getFilterItems().add(
					new FilterItemInfo("bank.id", accountBankInfo.getBank().getId()
							.toString()));
			filterAccBank.setMaskString("#0 OR #1");
			filter.mergeFilter(filterAccBank, "AND");
		}
		SettlementTypeInfo settType = (SettlementTypeInfo) prmtSettlementType.getData();
		if (settType != null&&settType.getNtType() != null) {
			FilterInfo filterNtType=new FilterInfo();
			filterNtType.getFilterItems().add(
					new FilterItemInfo("ntType.id", settType.getNtType().getId()
							.toString()));
			filter.mergeFilter(filterNtType, "AND");
		}
		return filter;
	}
    
//    private void lockDetailTable(){
//    	
//		if(!canModifyAccountAfterAuit){
//			return ;
//		}
//		
//		//�������,����������޸�����			
//		PaymentBillInfo info = (PaymentBillInfo) getDataObject();
//		if(info!=null){
//			BillStatusEnum billStatus = BillStatusEnum.SAVE;
//			if (info.getBillStatus() != null) {
//				billStatus = info.getBillStatus();
//			}
//			
//			if (billStatus.equals(BillStatusEnum.AUDITED) && getOprtState().equals(OprtState.EDIT)){
//				boolean isVoucher = info.isFiVouchered();
//				
//				boolean canModifyBizDate = (billStatus.equals(BillStatusEnum.SAVE)
//						|| billStatus.equals(BillStatusEnum.SUBMIT) 
//						|| billStatus.equals(BillStatusEnum.AUDITED)
//						|| billStatus.equals(BillStatusEnum.APPROVED))
//						&& !isVoucher;
//				getDetailTable().setEnabled(canModifyBizDate);
//				getDetailTable().setEditable(canModifyBizDate);
//				getDetailTable().getCell(4,4).getStyleAttributes().setLocked(!canModifyBizDate);
//				setTableCellColorAndEdit(!canModifyBizDate) ;
//				// ȥ������ƻ��ĸ�:
//				int lastRowIdx = kdtEntries.getRowCount() - 1;
//				for (int i = 4; i < kdtEntries.getColumnCount(); i++) {
//					StyleAttributes styleAttributes = kdtEntries.getCell(lastRowIdx - 1, i)
//							.getStyleAttributes();
//					styleAttributes.setLocked(true);
//					styleAttributes.setBackground(noEditColor);
//				}
//				kdtEntries.getCell(lastRowIdx - 1, 1).getStyleAttributes().setLocked(true);
//				kdtEntries.getCell(lastRowIdx - 1, 1).getStyleAttributes().setBackground(noEditColor);
//				
//				kdtEntries.getCell(9,4).getStyleAttributes().setLocked(true);
//				kdtEntries.getCell(lastRowIdx - 1,6).getStyleAttributes().setLocked(!canModifyBizDate);
//			}
//		}
//    }
    
	protected void setNumber() {
		PaymentBillInfo info = (PaymentBillInfo) getEditData();
		txtNumber.setEnabled(false);
		if (info==null || info.getBillStatus() == null) {
			return;
		}
		BillStatusEnum billStatus = info.getBillStatus();
		if (billStatus.equals(BillStatusEnum.SAVE)
				|| billStatus.equals(BillStatusEnum.SUBMIT)) {
			if (info.getCompany() == null) {
				info.setCompany(currentCompany);
			}
			FMClientHelper.initNumber(info, txtNumber, info.getCompany()
					.getId().toString());

			txtNumber.setRequired(txtNumber.isEnabled());
		}
	}
	/**
	 * ���ñ�λ�ҽ��,��Сд
	 * 
	 * @author:zhonghui_luo 2006-10-13 14:22:04
	 */
	private void setAmount() {
//		txtAmount.setPrecision(2);
		txtLocalAmt.setPrecision(2);
//		txtExchangeRate.setPrecision(2);
		txtAmount.setRemoveingZeroInDispaly(false);
		txtLocalAmt.setRemoveingZeroInDispaly(false);
		txtExchangeRate.setRemoveingZeroInDispaly(false);
		
		if(this.editData.getFdcPayReqID()!=null&&prmtCurrency.getValue()!=null){
			SelectorItemCollection sel=new SelectorItemCollection();
			sel.add("currency.id");
			sel.add("originalAmount");
			sel.add("amount");
			
			try {
				PayRequestBillInfo pay = PayRequestBillFactory.getRemoteInstance().getPayRequestBillInfo(new ObjectUuidPK(this.editData.getFdcPayReqID()),sel);
				KDTable table = getDetailTable();
				BigDecimal localamount =FDCHelper.ZERO;
				BigDecimal amount=FDCHelper.ZERO;
				BigDecimal exchangeRate = toBigDecimal(txtExchangeRate.getNumberValue());
				
				CurrencyInfo currency=(CurrencyInfo)prmtCurrency.getValue();
				if(!pay.getCurrency().getId().toString().equals(currency.getId().toString())){
					BigDecimal actPayAmount=FDCHelper.ZERO;
					BigDecimal actPayLocAmount=FDCHelper.ZERO;
					FDCSQLBuilder _builder = new FDCSQLBuilder();
					_builder.appendSql(" select sum(pb.famount) actPayAmount,sum(pb.flocalAmount) actPayLocAmount from t_cas_paymentbill pb");
					_builder.appendSql(" where pb.fFdcPayReqID='"+pay.getId().toString()+"'");
					if(editData.getId()!=null){
						_builder.appendSql(" and pb.fid!='"+editData.getId().toString()+"'");
					}
					final IRowSet rowSet = _builder.executeQuery();
					while (rowSet.next()) {
						if(rowSet.getBigDecimal("actPayAmount")!=null){
							actPayAmount=rowSet.getBigDecimal("actPayAmount");
						}
						if(rowSet.getBigDecimal("actPayLocAmount")!=null){
							actPayLocAmount=rowSet.getBigDecimal("actPayLocAmount");
						}
					}
					
					if(table.getCell(4, 4)!=null){
						table.getCell(4, 4).getStyleAttributes().setLocked(true);
						table.getCell(4, 4).setValue(pay.getOriginalAmount().subtract(actPayAmount));
					}
					localamount=pay.getAmount().subtract(actPayLocAmount);
					this.txtLocalAmt.setValue(localamount);
					this.editData.setLocalAmt(localamount);
					
					amount=localamount.divide(FDCHelper.toBigDecimal(exchangeRate),2,BigDecimal.ROUND_HALF_UP);	
					txtAmount.setValue(amount);
					this.editData.setAmount(amount);
				}else{
					if(table.getCell(4, 4)!=null){
						table.getCell(4, 4).getStyleAttributes().setLocked(false);
					}
					Object cell = bindCellMap.get(PaymentBillContants.CURPAID);
					if (cell instanceof ICell) {
						Object value = ((ICell) cell).getValue();
						if (value != null&&value instanceof BigDecimal) {
							amount=(BigDecimal) value;
							this.txtAmount.setValue(amount);
							this.editData.setAmount(amount);
							
							if (exchangeRate instanceof BigDecimal)
							{			
								localamount=amount.multiply(exchangeRate).setScale(2,BigDecimal.ROUND_HALF_UP);
								this.txtLocalAmt.setValue(localamount);
								this.editData.setLocalAmt(localamount);
							}
						}
					}else{
						if (exchangeRate instanceof BigDecimal)
						{			
							amount=this.txtAmount.getBigDecimalValue();
							localamount=amount.multiply(exchangeRate).setScale(2,BigDecimal.ROUND_HALF_UP);
							this.txtLocalAmt.setValue(localamount);
							this.editData.setLocalAmt(localamount);
						}
					}
				}
				if(localamount.compareTo(FDCConstants.ZERO)!=0){
					localamount = localamount.setScale(2,BigDecimal.ROUND_HALF_UP);
					//��д���Ϊ��λ�ҽ��
					String cap = FDCClientHelper.getChineseFormat(localamount,false);
					txtcapitalAmount.setText(cap);
					this.editData.setCapitalAmount(cap);
				}else{
					txtcapitalAmount.setText(null);
				}
			} catch (EASBizException e) {
				e.printStackTrace();
			} catch (BOSException e) {
				e.printStackTrace();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * ���Ǹ���ķ����Կ��ƹ�������ť����ʾ
	 * 
	 * @author:zhonghui_luo 2006-10-13 14:22:04
	 * 
	 */
	public void initUIToolBarLayout() {
		super.initUIToolBarLayout();
		actionCalculator.putValue(Action.SMALL_ICON, EASResource
				.getIcon("imgTbtn_counter"));
		actionClosePayReq.putValue(Action.SMALL_ICON, EASResource
				.getIcon("imgTbtn_close"));
		actionCancelClosePayReq.putValue(Action.SMALL_ICON, EASResource
				.getIcon("imgTbtn_fclose"));
		actionPay.putValue(Action.SMALL_ICON, EASResource
				.getIcon("imgTbtn_payment"));
		actionAudit.putValue(Action.SMALL_ICON, FDCClientHelper.ICON_AUDIT);
		actionAntiAudit.putValue(Action.SMALL_ICON, FDCClientHelper.ICON_UNAUDIT);
		actionRefresh.putValue(Action.SMALL_ICON, EASResource
				.getIcon("imgTbtn_refresh"));
		actionPaymentPlan.putValue(Action.SMALL_ICON, EASResource
				.getIcon("imgTbtn_showdata"));
		btnTaoPrint.setIcon(EASResource.getIcon("imgTbtn_print"));
		actionPaymentPlan.setVisible(false);
		actionCopy.setVisible(false);
		actionAuditResult.setVisible(false);
		actionTraceDown.setVisible(false);
		actionWorkFlowG.setVisible(false);
		actionCopyFrom.setVisible(false);
		actionAddLine.setVisible(false);
		actionInsertLine.setVisible(false);
		actionRemoveLine.setVisible(false);
		actionVoucher.setVisible(false);
		actionDelVoucher.setVisible(false);
		actionMultiapprove.setVisible(false);
		btnTaoPrint.setVisible(false);
		this.btnWFViewdoProccess.setVisible(false);
		this.btnWFViewSubmitProccess.setVisible(false);
	}
	
	//��ȡ��ʼ������
	protected  void fetchInitData() throws Exception{

		Map ctx = this.getUIContext();
		String contractBillId = (String) ctx.get("contractBillId");

		Map initparam = new HashMap();
		
		if (this.getBOTPViewStatus() == 1) {
			String srcId = (String)ctx.get("srcBillID");
			initparam.put("srcBillID",srcId);
		}else{
			initparam.put("ID",getUIContext().get("ID"));
			if(contractBillId!=null){
				initparam.put("contractBillId",contractBillId);
			}
		}
		if(editData!=null &&editData.getCreateTime()!=null){
			initparam.put("createTime",editData.getCreateTime());
			initparam.put("FdcPayReqID",editData.getFdcPayReqID());
		}
		initData = PaymentFacadeFactory.getRemoteInstance().fetchInitData(initparam);
		if(contractBillId==null){
			contractBillId = (String)initData.get("contractBillId");
		}
		if(initData==null){
			MsgBox.showWarning(this,"���Ƿ��ز�����");
			SysUtil.abort();
		}
		
		//��˾
		if (currentCompany == null) {
			currentCompany = (CompanyOrgUnitInfo)initData.get(FDCConstants.FDC_INIT_COMPANY);
		}
		if(orgUnitInfo==null){
			orgUnitInfo = (FullOrgUnitInfo) initData.get(FDCConstants.FDC_INIT_ORGUNIT);
		}else{
			orgUnitInfo =SysContext.getSysContext().getCurrentOrgUnit().castToFullOrgUnitInfo();
		}
		//��λ��
		if (baseCurrency == null) {
			baseCurrency = (CurrencyInfo)initData.get(FDCConstants.FDC_INIT_CURRENCY);
		}

		curProject = (CurProjectInfo) initData.get(FDCConstants.FDC_INIT_PROJECT);
		
//		//��ͬ����
//		if(initData.get(FDCConstants.FDC_INIT_CONTRACT) instanceof ContractBillInfo){
//			contractBill = (ContractBillInfo)initData.get(FDCConstants.FDC_INIT_CONTRACT);
//		}	else{
//			text = (ContractWithoutTextInfo)initData.get(FDCConstants.FDC_INIT_CONTRACT);
//		}
//		
//		info = (PayRequestBillInfo)initData.get("PayRequestBillInfo");
//		payReqCol =(PayRequestBillCollection) initData.get("PayRequestBillCollection");
//		payCol = (PaymentBillCollection)initData.get("PaymentBillCollection");		
		
		
		//�Ƿ�����Ԥ�����
		if(currentCompany!=null){
			HashMap param = FDCUtils.getDefaultCashOrGLParam(null,currentCompany.getId().toString());
			if(param.get("CS050")!=null){
				usageLegth = Integer.valueOf(param.get("CS050").toString()).intValue();
			}		
			
			if(param.get("CS034")!=null){
				initData.put("PARAM_CS034", param.get("CS034"));
			}
			
			if(param.get("CS036")!=null){
				initData.put("PARAM_CS036", param.get("CS036"));
			}
			
			//canModifyAccountAfterAuit
			if(param.get(FDCConstants.FDC_PARAM_MODIFYACOUNT)!=null){
				canModifyAccountAfterAuit =Boolean.valueOf(param.get(FDCConstants.FDC_PARAM_MODIFYACOUNT).toString()).booleanValue();
			}	
			/*isSimpleFinancial = FDCUtils.getDefaultFDCParamByKey(null,
					currentCompany.getId().toString(),
					FDCConstants.FDC_PARAM_SIMPLEFINACIAL);
			isAutoComplete = FDCUtils.getDefaultFDCParamByKey(null,
					currentCompany.getId().toString(),
					FDCConstants.FDC_PARAM_PAYPROGRESS);
			isPaymentSplit = FDCUtils.getDefaultFDCParamByKey(null,currentCompany.getId().toString(), FDCConstants.FDC_PARAM_CHECKPAYMENTSPLIT);
			isSeparate = FDCUtils.getDefaultFDCParamByKey(null, currentCompany
					.getId().toString(),
					FDCConstants.FDC_PARAM_SEPARATEFROMPAYMENT);*/	
			//������֯����(����ȡ��)
			HashMap fiParam = FDCUtils.getDefaultFDCParam(null, currentCompany.getId().toString());
			isFinancial = FDCUtils.getParamValue(fiParam, FDCConstants.FDC_PARAM_FINACIAL);
			isAdjustVourcherModel = FDCUtils.getParamValue(fiParam, FDCConstants.FDC_PARAM_ADJUSTVOURCHER);
			isSimpleFinancial = FDCUtils.getParamValue(fiParam, FDCConstants.FDC_PARAM_SIMPLEFINACIAL);
			isSimpleFinancialExtend = FDCUtils.getParamValue(fiParam, FDCConstants.FDC_PARAM_SIMPLEFINACIALEXTEND);
			isSimpleInvoice = FDCUtils.getParamValue(fiParam, FDCConstants.FDC_PARAM_SIMPLEINVOICE);
//			isAutoComplete = FDCUtils.getParamValue(fiParam, FDCConstants.FDC_PARAM_PAYPROGRESS);
			isPaymentSplit = FDCUtils.getParamValue(fiParam, FDCConstants.FDC_PARAM_CHECKPAYMENTSPLIT);
			isSeparate = FDCUtils.getParamValue(fiParam, FDCConstants.FDC_PARAM_SEPARATEFROMPAYMENT);	
		}
		
		//�ɱ����Ĳ���
		if(orgUnitInfo==null){//�ǹ�����Ŀ�����ѯ�Ƚ���ʱ��֯Ϊ��
			orgUnitInfo =SysContext.getSysContext().getCurrentCostUnit().castToFullOrgUnitInfo();
		}
		HashMap paramMap = FDCUtils.getDefaultFDCParam(null, orgUnitInfo.getId().toString());
		checkPaymentAllSplit = isPaymentSplit&&FDCUtils.getParamValue(paramMap, FDCConstants.FDC_PARAM_CHECKPAYMENTALLSPLIT);
		isAutoComplete = FDCUtils.getParamValue(paramMap, FDCConstants.FDC_PARAM_PAYPROGRESS);//Boolean.valueOf(paramMap.get(FDCConstants.FDC_PARAM_PAYPROGRESS).toString()).booleanValue();
		
		isCostSplit = FDCUtils.isCostSplit(null,contractBillId);
	}
	
	//������
	Map listenersMap = new HashMap();
    //���������ݺ���ϼ�����
    protected void attachListeners() {
    	
    	EventListener[] listeners = (EventListener[] )listenersMap.get(prmtuseDepartment);
		for(int i=0;i<listeners.length;i++){
			prmtuseDepartment.addDataChangeListener((DataChangeListener)listeners[i]);
		}
    	
    	listeners = (EventListener[] )listenersMap.get(prmtCurrency);
		for(int i=0;i<listeners.length;i++){
			prmtCurrency.addDataChangeListener((DataChangeListener)listeners[i]);
		}
		
    	listeners = (EventListener[] )listenersMap.get(prmtPayerAccountBank);
		for(int i=0;i<listeners.length;i++){
			prmtPayerAccountBank.addDataChangeListener((DataChangeListener)listeners[i]);
		}
		
		//txtrecCity
    	listeners = (EventListener[] )listenersMap.get(txtrecCity);
		for(int i=0;i<listeners.length;i++){
			txtrecCity.addDataChangeListener((DataChangeListener)listeners[i]);
		}
		
    	listeners = (EventListener[] )listenersMap.get(txtrecProvince);
		for(int i=0;i<listeners.length;i++){
			txtrecProvince.addDataChangeListener((DataChangeListener)listeners[i]);
		}
		
		listeners = (EventListener[] )listenersMap.get(comboPayeeType);
		for(int i=0;i<listeners.length;i++){
			((KDComboBox)comboPayeeType).addItemListener((ItemListener)listeners[i]);
		}
				
		listeners = (EventListener[] )listenersMap.get(txtInvoiceAmt);
		for(int i=0;i<listeners.length;i++){
			txtInvoiceAmt.addDataChangeListener((DataChangeListener)listeners[i]);
		}
    }
    
    //ע��������
    protected void detachListeners() {
    	
		EventListener[] listeners = prmtuseDepartment.getListeners(DataChangeListener.class);	
		for(int i=0;i<listeners.length;i++){
			prmtuseDepartment.removeDataChangeListener((DataChangeListener)listeners[i]);
		}		
		listenersMap.put(prmtuseDepartment,listeners );
    	
		listeners = prmtCurrency.getListeners(DataChangeListener.class);	
		for(int i=0;i<listeners.length;i++){
			prmtCurrency.removeDataChangeListener((DataChangeListener)listeners[i]);
		}		
		listenersMap.put(prmtCurrency,listeners );
		
		listeners = prmtPayerAccountBank.getListeners(DataChangeListener.class);	
		for(int i=0;i<listeners.length;i++){
			prmtPayerAccountBank.removeDataChangeListener((DataChangeListener)listeners[i]);
		}		
		listenersMap.put(prmtPayerAccountBank,listeners );
		
		listeners = txtrecProvince.getListeners(DataChangeListener.class);	
		for(int i=0;i<listeners.length;i++){
			txtrecProvince.removeDataChangeListener((DataChangeListener)listeners[i]);
		}		
		listenersMap.put(txtrecProvince,listeners );
		
		listeners = txtrecCity.getListeners(DataChangeListener.class);	
		for(int i=0;i<listeners.length;i++){
			txtrecCity.removeDataChangeListener((DataChangeListener)listeners[i]);
		}		
		listenersMap.put(txtrecCity,listeners );
		
		listeners = comboPayeeType.getListeners(ItemListener.class);	
		for(int i=0;i<listeners.length;i++){
			((KDComboBox)comboPayeeType).removeItemListener((ItemListener)listeners[i]);
		}
		listenersMap.put(comboPayeeType,listeners );
		
		listeners = txtInvoiceAmt.getListeners(DataChangeListener.class);	
		for(int i=0;i<listeners.length;i++){
			txtInvoiceAmt.removeDataChangeListener((DataChangeListener)listeners[i]);
		}
		listenersMap.put(txtInvoiceAmt,listeners );
    }

	/**
	 * output loadFields method
	 */
	public void loadFields() {
		
		//��ע��������
		detachListeners();		

		if(editData==null){
			SysUtil.abort();
		}
		
// ���ı���ͬ��Ϊ����һ������θ����ˡ� Added by Owen_wen 2011-05-12 R110130-022
		// ��������ı���ͬ����ô�����޸Ľ��
		// if (editData.getContractBillId() != null&&
		// PayReqUtils.isConWithoutTxt(editData.getContractBillId())) {
		// canModifyAmount = false;
		// }else{
		// canModifyAmount = true;
		// }
		// btnInputCollect.setEnabled(canModifyAmount);
		boolean isExist = false;
		if(initData.get("isExist")!=null){
			isExist=((Boolean)initData.get("isExist")).booleanValue();
		}
		PayRequestBillInfo info = (PayRequestBillInfo)initData.get("PayRequestBillInfo");
		// ����ǵ���ת��,�����ø���Ľ��
		// ������ = ��Ӧ�������뵥�Ľ�� - �˸�����뵥���з�¼���֮��		
		if (this.getBOTPViewStatus() == 1) {
			if(info==null){
				try {
					fetchInitData();
				} catch (Exception e) {
					e.printStackTrace();
					this.handleException(e);
				}
			}else{
				this.editData.setIsNeedPay(info.isIsPay());			
			}		
			
			BigDecimal amou = FDCHelper.ZERO; //ԭ��С��
			BigDecimal amouLocal = FDCHelper.ZERO;//����С��
			BigDecimal total =FDCHelper.ZERO; //����ԭ��
			BigDecimal totalLocal = FDCHelper.ZERO;//���б���
			BigDecimal paya = FDCHelper.ZERO; //new BigDecimal(0);
			BigDecimal payaTo = FDCHelper.ZERO; //new BigDecimal(0);
			BigDecimal incon = FDCHelper.ZERO; //new BigDecimal(0);
			BigDecimal inconTo = FDCHelper.ZERO; //new BigDecimal(0);
			BigDecimal add = FDCHelper.ZERO; //new BigDecimal(0);
			BigDecimal addTo = FDCHelper.ZERO; //new BigDecimal(0);
			//���뵥��¼ -- ������ ԭ��
			BigDecimal addLocal = FDCHelper.ZERO;
			BigDecimal addConInPrj = FDCHelper.ZERO;
			BigDecimal addAdvance = FDCHelper.ZERO;
			BigDecimal addLocAdvance = FDCHelper.ZERO;
			if (editData.getFdcPayReqID() != null) {
				
				if(info.getUrgentDegree()!=null&&UrgentDegreeEnum.URGENT_VALUE.endsWith(String.valueOf(info.getUrgentDegree().getValue()))){
					editData.put("isEmergency", new Integer(1));
				}else{
					editData.put("isEmergency", new Integer(0));
				}
				
				if (info.getCompletePrjAmt() != null){
					if(isExist){
						//�Ѵ��ڸ��
						txtcompletePrjAmt.setValue(FDCHelper.ZERO);
					}else{
						txtcompletePrjAmt.setValue(info.getCompletePrjAmt());
					}
				}
				if (info.getPaymentProportion() != null){
					txtpaymentProportion.setValue(info.getPaymentProportion());
				}
				if (info.getProjectPriceInContractOri() != null){
					editData.setAddProjectAmt(info.getProjectPriceInContractOri());
				}
				//���û���ֶδ�����:�տ������͡����ơ�ժҪ����ע��������
				if(supplierAssitType!=null){
					editData.setPayeeType(supplierAssitType);
				}
				
				//
				if(info.getSupplier()!=null){
					editData.setPayeeID(info.getSupplier().getId().toString());
					editData.setPayeeNumber(info.getSupplier().getNumber());
					editData.setPayeeName(info.getSupplier().getName());
				}
				editData.setActFdcPayeeName(info.getRealSupplier());
				
				editData.setDescription(info.getDescription());
				editData.setSummary(info.getMoneyDesc());
				
				editData.setAccessoryAmt(info.getAttachment());
				
				//��������
				editData.setFdcPayType(info.getPaymentType());
				
				//�¼ӣ����ֶ�
//				editData.setUsage(info.getUsage());
				editData.setIsDifferPlace(info.getIsDifferPlace());

				total = info.getOriginalAmount();
				totalLocal = info.getAmount();
				payaTo = info.getPayPartAMatlAmt();
				inconTo = info.getProjectPriceInContract();
				addTo = info.getAddProjectAmt();
				
				if (this.editData.getFdcPayReqID() != null) {
					PayRequestBillEntryCollection payReqEntry = info.getEntrys();
					if(payReqEntry!=null){
						Iterator itor;
						for (itor = payReqEntry.iterator(); itor.hasNext();) {
							PayRequestBillEntryInfo entry = (PayRequestBillEntryInfo) itor.next();
							if (entry.getOriginalAmount() != null)
								amou = amou.add(entry.getOriginalAmount());
							if (entry.getAmount() != null){
//								amou = amou.add(entry.getAmount());
								amouLocal = amouLocal.add(entry.getAmount());
							}
							if(entry.getAddProjectAmt() != null)
								addLocal = addLocal.add(entry.getAddProjectAmt());
							if(entry.getProjectPriceInContract() != null)
								addConInPrj = addConInPrj.add(entry.getProjectPriceInContract());
							if (entry.getPayPartAMatlAmt() != null)
								paya = paya.add(entry.getPayPartAMatlAmt());
							if (entry.getProjectPriceInContract() != null)
								incon = incon.add(entry.getProjectPriceInContract());
							if (entry.getAddProjectAmt() != null)
								add = add.add(entry.getAddProjectAmt());
							if (entry.getAdvance() != null) {
								addAdvance = addAdvance.add(entry.getAdvance());
							}
							if (entry.getLocAdvance() != null) {
								addLocAdvance = addLocAdvance.add(entry
										.getLocAdvance());
							}
						}	
					}
					
				}				
				
				if(info!=null){
					pkbookedDate.setValue(info.getBookedDate());
					cbPeriod.setValue(info.getPeriod());
					editData.setBizDate(info.getBookedDate());
				}
			}
			editData.setAddProjectAmt(FDCHelper.subtract(info.getProjectPriceInContractOri(),addLocal));
			editData.setAmount(FDCHelper.subtract(total, amou));
//			editData.setLocalAmt(FDCHelper.subtract(totalLocal, amouLocal));
			editData.setProjectPriceInContract(FDCHelper.subtract(info.getProjectPriceInContract(),addConInPrj));
			this.txtAmount.setValue(FDCHelper.subtract(total, amou));
//			this.txtLocalAmt.setValue(FDCHelper.subtract(totalLocal, amouLocal));
			// ((ICell)bindCellMap.get(PaymentBillContants.PROJECTPRICEINCONTRACT)).setValue(editData.getAmount());
			if(this.isAdvance()){
				PaymentPrjPayEntryInfo prjPayEntry = editData.getPrjPayEntry();
				if(prjPayEntry==null){
					prjPayEntry = new PaymentPrjPayEntryInfo();
					editData.setPrjPayEntry(prjPayEntry);
				}
				if(info.getPrjPayEntry()!=null){
					editData.getPrjPayEntry().setAdvance(FDCHelper.subtract(info.getPrjPayEntry().getAdvance(), addAdvance));
					editData.getPrjPayEntry().setLocAdvance(FDCHelper.subtract(info.getPrjPayEntry().getLocAdvance(), addLocAdvance));
					editData.getPrjPayEntry().setLstAdvanceAllPaid(info.getPrjPayEntry().getLstAdvanceAllPaid());
					editData.getPrjPayEntry().setLstAdvanceAllReq(info.getPrjPayEntry().getAdvanceAllReq());
				}
			}
			if (payaTo != null)
				editData.setPayPartAMatlAmt(payaTo.subtract(paya));
			
			// ���ڷ�����Ӧ�ÿۼ������ڷ���ָ��ǰ���븶����
			// if (inconTo != null)
			// editData.setProjectPriceInContract(inconTo.subtract(incon));
//			if (addTo != null)
//				editData.setAddProjectAmt(addTo.subtract(add));
			
		} else if (editData.getFdcPayReqID() != null) {
//			editData.setAddProjectAmt(info.getProjectPriceInContractOri());
			if (info.getCompletePrjAmt() != null)
				if(isExist){//�Ѵ��ڸ��
					txtcompletePrjAmt.setValue(FDCHelper.ZERO);
				}else{
					txtcompletePrjAmt.setValue(info.getCompletePrjAmt());
				}
			//Ԥ����ȫ������һ��,����ʱ���������
			if(info.get("prjPayEntry")!=null){
				PayReqPrjPayEntryInfo payReqEntry = (PayReqPrjPayEntryInfo)info.get("prjPayEntry");
				PaymentPrjPayEntryInfo payEntry = editData.getPrjPayEntry();
				if(payEntry==null){
					payEntry = new PaymentPrjPayEntryInfo();
					editData.setPrjPayEntry(payEntry);
					payEntry.setLstAdvanceAllPaid(payReqEntry.getLstAdvanceAllPaid());
					payEntry.setLstAdvanceAllReq(payReqEntry.getAdvanceAllReq());
					payEntry.setAdvance(payReqEntry.getAdvance());
					payEntry.setLocAdvance(payReqEntry.getLocAdvance());
				}
				
			}
			if (info.getPaymentProportion() != null)
					txtpaymentProportion.setValue(info.getPaymentProportion());				
			
			if(info!=null){
				pkbookedDate.setValue(info.getBookedDate());
				cbPeriod.setValue(info.getPeriod());
			}
		}
		
		//��һ�Ŵ���Ʊ
		if(!isExist){
			PaymentPrjPayEntryInfo payEntry = editData.getPrjPayEntry();
			if(payEntry==null){
				payEntry = new PaymentPrjPayEntryInfo();
				editData.setPrjPayEntry(payEntry);
			}
		//////////////////////////////////////////////////////////////////////////////////////////
			editData.getPrjPayEntry().setInvoiceNumber(info.getInvoiceNumber());
			editData.getPrjPayEntry().setInvoiceAmt(info.getInvoiceAmt());
//			txtInvoiceNumber.setText(info.getInvoiceNumber());
//			txtInvoiceAmt.setValue(info.getInvoiceAmt());
//			pkInvoiceDate.setValue(info.getInvoiceDate());
			
		}else{
//			pkInvoiceDate.setValue(null);
		}
		
//		txtAllInvoiceAmt.setValue(info.getAllInvoiceAmt());
	
		//����Ʊ by zhiyuan_tang
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("paymentbill.id", editData.getId()));
		view.setFilter(filter);
		FDCPaymentBillCollection fdcPaymentCol = null;
		try {
			fdcPaymentCol = FDCPaymentBillFactory.getRemoteInstance().getFDCPaymentBillCollection(view);
		} catch (BOSException e1) {
			logger.error(e1.getStackTrace(), e1);
		}
		
		if (fdcPaymentCol != null) {
			for (Iterator it = fdcPaymentCol.iterator(); it.hasNext();) {
				FDCPaymentBillInfo fdcPaymentInfo = (FDCPaymentBillInfo)it.next();
				txtInvoiceNumber.setText(fdcPaymentInfo.getInvoiceNumber());
				txtInvoiceAmt.setValue(fdcPaymentInfo.getInvoiceAmt());
				pkInvoiceDate.setValue(fdcPaymentInfo.getInvoiceDate());

				PaymentPrjPayEntryInfo payEntry = editData.getPrjPayEntry();
				if (payEntry == null) {
					payEntry = new PaymentPrjPayEntryInfo();
					editData.setPrjPayEntry(payEntry);
				}
				editData.getPrjPayEntry().setInvoiceNumber(info.getInvoiceNumber());
				editData.getPrjPayEntry().setInvoiceAmt(info.getInvoiceAmt());
			}
		}
		
		BigDecimal allInvoiceAmt = (BigDecimal) initData.get("FDCPaymentAllInvoicAmt");
		if (allInvoiceAmt != null) {
			txtAllInvoiceAmt.setValue(allInvoiceAmt);
		}
		
		
		// ������(��ʱȥ����
		getConceit();
		// ���üƻ���Ŀ�Ĺ�������
//		EntityViewInfo evi = CasRecPayHandler
//				.getFpItemEvi(FPItemDirectionEnum.OUTWARD_MOVEMENT);
//		prmtFpItem.setEntityViewInfo(evi);
		
		
		if (this.editData.getBillStatus() == null)
			this.editData.setBillStatus(BillStatusEnum.SAVE);
		this.editData.setSourceType(SourceTypeEnum.FDC);
		if (this.editData.getSettleBizType() == null)
			this.editData.setSettleBizType(SettBizTypeEnum.PAYOUTSIDE);
		
		if (this.editData.getCompany() == null)
			this.editData.setCompany(SysContext.getSysContext().getCurrentFIUnit());
		//����һ���ˣ�һ���ǳ�����Ա�����뵥���ɸ��ʱ����ͬһ�ˣ��޸�ʱ��ֵ
//		if (this.editData.getCreator() == null)
//			this.editData.setCreator((SysContext.getSysContext().getCurrentUserInfo()));
//		if (this.editData.getCreateTime() == null)
//			this.editData.setCreateTime(new Timestamp(System.currentTimeMillis()));
		if (this.editData.getUrgentDegree() == null)
			this.editData.setUrgentDegree(UrgentDegreeEnum.NORMAL);
//		if (this.editData.getPayDate() == null)
//			this.editData.setPayDate(DateTimeUtils.truncateDate(new Date()));
		if (this.editData.getBizDate() == null)
			this.editData.setBizDate(DateTimeUtils.truncateDate(new Date()));
		if (this.editData.getDayaccount() == null)
			this.editData.setDayaccount(FDCHelper.ZERO);
		if (this.editData.getCU() == null)
			this.editData.setCU(SysContext.getSysContext().getCurrentCtrlUnit());
		
		if ((this.editData.getFdcPayReqID() == null)
				|| (STATUS_FINDVIEW.equals(this.oprtState))) {
			this.actionAntiAudit.setEnabled(false);
			this.actionClosePayReq.setEnabled(false);
			this.actionCancelClosePayReq.setEnabled(false);
			this.btnClosePayReq.setEnabled(false);
			this.btnCancelClosePayReq.setEnabled(false);
			actionTraceUp.setEnabled(false);
		} else {
			this.actionClosePayReq.setEnabled(true);
			this.actionCancelClosePayReq.setEnabled(true);
			this.btnClosePayReq.setEnabled(true);
			this.btnCancelClosePayReq.setEnabled(true);
			this.actionTraceUp.setEnabled(true);
		}
		// if (this.getBOTPViewStatus() == 0) {
		String contractBillId = this.editData.getContractBillId();
		if (contractBillId != null) {
			if ((BOSUuid.read(contractBillId).getType())
					.equals(new ContractBillInfo().getBOSType())) {
				ContractBillInfo contractBill = (ContractBillInfo)initData.get(FDCConstants.FDC_INIT_CONTRACT);
				String str = contractBill.getNumber();
				this.txtContractNo.setText(str + "");
				if (str != null)
					editData.setContractNo(str);
				BOSUuid conno = contractBill.getId();
				this.editData.setContractBillId(conno + "");
				this.editData.setCurProject(curProject);
				
			} else if ((BOSUuid.read(contractBillId).getType())
					.equals(new ContractWithoutTextInfo().getBOSType())) {
				ContractWithoutTextInfo text = (ContractWithoutTextInfo)initData.get(FDCConstants.FDC_INIT_CONTRACT);
				String str = text.getNumber();
				this.txtContractNo.setText(str + "");
				if (str != null)
					editData.setContractNo(str);
				BOSUuid conno = text.getId();

				this.editData.setContractBillId(conno + "");
					
				// �������ı���ͬ������Ŀ
				this.editData.setCurProject(curProject);
					
			}
		}

		// �ұ�Ϊ��ʱ���ñұ�Ϊ��λ��
		if (this.editData.getCurrency() == null) {
			if (baseCurrency != null) {
				this.editData.setCurrency(baseCurrency);				
				this.editData.setExchangeRate(FDCConstants.ONE);
			}
		}
		if (this.editData.getCurrency() != null) {
			if (editData.getCurrency().getId().toString().equals(baseCurrency.getId().toString())) {
				// �Ǳ�λ��ʱ������ѡ����û�
				txtExchangeRate.setValue(FDCConstants.ONE);
				txtExchangeRate.setRequired(false);
				txtExchangeRate.setEditable(false);
				txtExchangeRate.setEnabled(false);
				isBaseCurrency = true;
			}
			else{
				isBaseCurrency = false;
//				canModifyAmount = false;
//				btnInputCollect.setEnabled(canModifyAmount);
//				this.prmtCurrency.setEnabled(false);
//				this.txtExchangeRate.setEnabled(true);
				this.txtLocalAmt.setValue(editData.getLocalAmt());
			}
		}
		
		//�ƻ���Ŀ
//		this.prmtFpItem.setQueryInfo("com.kingdee.eas.fm.fpl.FpItemQuery");
		
		// ��ʼ����¼
		if (editData.getUrgentDegree() == null)
			editData.setUrgentDegree(UrgentDegreeEnum.NORMAL);
		
		

		super.loadFields();
		
		// ���±�λ����ʾ
		if(editData.getLocalAmt()==null){
			setAmount();
		}

		// ���ø������Ϊ��ͬ�ĸ������ �Ӹ���й���
//		PaymentBillCollection payCol = (PaymentBillCollection)initData.get("PaymentBillCollection");		
//		if(payCol!=null){
//			editData.setPayTimes(payCol.size());
//		}
//		
//		((ICell)bindCellMap.get(PaymentBillContants.PAYTIMES)).setValue(payCol.size()+"");
//		
		/*
		 * // ������ǰ��kdtEntries���Լ���kdtable��� Rectangle kdtRectangle =
		 * kdtEntries.getBounds(); kdtEntries.setEnabled(false);
		 * kdtEntries.setVisible(false); kdtEntries = createPaymentBillTable();
		 * kdtEntries.setBounds(kdtRectangle); //
		 * pnlPayBillEntry.add(kdtEntries, null);
		 * pnlPayBillEntry.add(kdtEntries, new KDLayout.Constraints(10, 10, 850,
		 * 450, KDLayout.Constraints.ANCHOR_TOP |
		 * KDLayout.Constraints.ANCHOR_BOTTOM_SCALE |
		 * KDLayout.Constraints.ANCHOR_LEFT_SCALE |
		 * KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
		 *  // ��ҳǩ��ʾ sortPanel(); kdtEntries.getScriptManager().runAll(); //
		 * �������������ݣ�δ���޸ģ�ֱ�ӹر�ʱ�����ֱ�����ʾ
		 * 
		 * 
		 * this.storeFields(); this.initOldData(this.editData);
		 */
		prmtPayerAccountBank.setValue(editData.getPayerAccountBank());
		prmtPayerAccount.setValue(editData.getPayerAccount());
		txtPayeeAccountBank.setText(editData.getPayeeAccountBank());
		
		this.txtDayaccount.setEnabled(false);
		// addBalanceListener();
		setNumber();
		
		if (txtNumber.isEnabled()) {
			txtNumber.requestFocusInWindow();
		} else {
			pkBizDate.requestFocusInWindow();
		}
		
		refreshUITitle();

//		//
		prmtActFdcPayeeName.setRequired(false);
		if(!editData.isIsNeedPay()){
			prmtPayee.setRequired(true);
			
//			prmtFdcPayeeName.setRequired(false);
//			prmtActFdcPayeeName.setRequired(false);
//			txtPayeeBank.setRequired(false);
			//txtPayeeAccountBank.setRequired(false);			
			prmtSettlementType.setRequired(false);
			prmtPayerAccount.setRequired(false);
		}else{
//			txtPayeeAccountBank.setRequired(true);
			prmtPayerAccount.setRequired(true);
		}
		
//		if(editData.getIsEmergency().getValue()==1){
//			mergencyState.setSelectedItem(editData.getIsEmergency());
//		}
		
		FMClientHelper.setSelectObject(comboPayeeType, editData.getPayeeType());
		if (editData.getPayeeType() == null) {
			comboPayeeType.setSelectedItem(FMSysDefinedEnum.OTHER);
		}
		
		// �����տ���
		String payeeId = editData.getPayeeID();
		String payeeNumber = editData.getPayeeNumber();
		String payeeName = editData.getPayeeName();
		handler.fillPayeeOrPayer(prmtPayee, payeeId, payeeNumber, payeeName);
		
		Object  obj = comboPayeeType.getSelectedItem();
		if(obj instanceof AsstActTypeInfo ){
			AsstActTypeInfo asstActTypeInfo = (AsstActTypeInfo)obj;
			if ("provider".equalsIgnoreCase(asstActTypeInfo.getAsstHGAttribute())||"person".equalsIgnoreCase(asstActTypeInfo.getAsstHGAttribute())){
//				prmtPayee.setEnabled(false);
			}
		}
		
		try {
			province_dataChanged( editData.getRecProvince());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		//����Ԥ����Ŀ:���EASϵͳ���ʽ�ƻ��ǵ�����һ��ģ�飬�ʽ�ƻ����Ƹ���ʱ��ͨ���ڸ���ϼӡ��ƻ���Ŀ�����ʽ�ƻ�ģ��ġ��ƻ���Ŀ�����������Ӷ�ʵ�ֶԸ���Ŀ��ƣ�
		//�����ʽ�ƻ�ͨ��Ԥ��ϵͳ�����ƣ���Ԥ��ϵͳ��ͨ��������Ԥ����Ŀ����ʵ�ֶԸ���Ŀ��Ƶģ�������Ҫ�������ԭ���ġ��ƻ���Ŀ����Ϊ����Ԥ��ϵͳ�еġ�Ԥ����Ŀ��
		//by cassiel 2010-09-29
		if(isFpItem){
			this.prmtFpItem.setValue(this.editData.getFpItem());
		}else{
			FDCDealFPOrBudgetItemUtil.loadFieldsBgItem(prmtFpItem, editData, FDCDealFPOrBudgetItemUtil.getBgItemProps());
		}
		
		//���������ݺ����
		attachListeners(); 
	}
	
	
    protected void refreshUITitle() {
    	if(titleMain==null){
    		return;
    	}
    	
        String state = getOprtState();
        if (OprtState.ADDNEW.equals(state)) {
            billIndex = "";
            this.setUITitle(titleMain + " - " + titleNew);
        } else {
            if (OprtState.EDIT.equals(state)) {
                this.setUITitle(titleMain + " - " + titleModify + billIndex);
            } else {
                boolean isFromMsgCenter = false;
                Map uiContext = getUIContext();
                if (uiContext != null) {
                    isFromMsgCenter = Boolean.TRUE.equals(uiContext.get("isFromWorkflow"));
                }
                if (!isFromMsgCenter) {
                    this.setUITitle(titleMain + " - " + titleView + billIndex);
                }
            }
        }
    }
    
	/**
	 * ��ȡ����������������������ڱ༭����Ĵ�����������ʾ
	 * 
	 * @author:zhonghui_luo 2006-10-13 14:29:32
	 * 
	 */
	protected void getConceit() {
		EntityViewInfo vi = new EntityViewInfo();
		SorterItemInfo sortItemInfo = new SorterItemInfo("createTime");
		sortItemInfo.setSortType(SortType.DESCEND);
		vi.getSorter().add(sortItemInfo);
		FilterInfo fi = new FilterInfo();
		FilterItemCollection it = fi.getFilterItems();
		it.add(new FilterItemInfo("billId", this.editData.getId()));
		vi.setFilter(fi);
		// SelectorItemCollection selector = new SelectorItemCollection();
		// selector.add("opinion");
		// vi.getSelector().add(new SelectorItemInfo("opinion"));
		try {
			MultiApproveCollection c = MultiApproveFactory.getRemoteInstance()
					.getMultiApproveCollection(vi);
			Iterator iter = c.iterator();
			if (iter.hasNext()) {
				MultiApproveInfo info = (MultiApproveInfo) iter.next();
				String str = info.getOpinion();
				editData.setConceit(str);
			}
		} catch (BOSException e) {
			super.handUIException(e);
		}
	}	

	//���þ���
	protected void setPrecision(){
		CurrencyInfo currency = editData.getCurrency();	    	
    	Date bookedDate = (Date)editData.getBizDate();
    	
    	ExchangeRateInfo exchangeRate = null;
		try {
			exchangeRate = FDCClientHelper.getLocalExRateBySrcCurcy(this, currency.getId(),this.currentCompany,bookedDate);
		} catch (Exception e) {			
			e.printStackTrace();
			return ;
		} 
    	
    	int curPrecision = FDCClientHelper.getPrecOfCurrency(currency.getId());
    	int exPrecision = curPrecision;
    	
    	if(exchangeRate!=null){
    		exPrecision = exchangeRate.getPrecision();
    	}
    		
    	txtExchangeRate.setPrecision(exPrecision);
    	txtAmount.setPrecision(curPrecision);
    	BigDecimal exRate =  editData.getExchangeRate();    	
    	txtExchangeRate.setValue(exRate);
    	txtAmount.setValue(editData.getAmount());
	}
	
	private String getSettleNumber() {
		StringBuffer sb = new StringBuffer();
		Object obj = f7SettleNumber.getData();
		if (obj == null) {
			return null;
		}

		if(obj instanceof Object[]) {
			Object[] billColl = (Object[]) obj;

			for (int i = 0; i < billColl.length; i++) {
				if(billColl[i] instanceof ChequeInfo){
					ChequeInfo receivableBill = (ChequeInfo) billColl[i];
					sb.append(receivableBill.getNumber());
				}
				if (i != billColl.length - 1) {
					sb.append(";");
				}
			}
		} else {
			sb.append(obj.toString());
		}
		return sb.toString();

	}

	/**
	 * output storeFields method
	 */
	public void storeFields() {
		// ����¼�ڵ����ݴ洢��info
		// if(this.getBOTPViewStatus()==0){
		if (editData.getContractBillId() != null) {
			if ((BOSUuid.read(editData.getContractBillId()).getType())
					.equals(new ContractBillInfo().getBOSType())) {
				updateDynamicValue();
				kdtEntries.getScriptManager().runAll();
				PaymentBillClientUtils.getValueFromCell(editData, bindCellMap);
			}
		}
		// }
		
		editData.setSettlementNumber(getSettleNumber());
		
		if (prmtPayerAccountBank.getValue() != null) {
			editData.setPayerAccountBank((AccountBankInfo) prmtPayerAccountBank.getValue());
		}
		
		// ����ʵ���տ��ʺ�
//		String actbankAccountNumber = getActRecAccountNumber();
//		editData.setActRecAccountBank(actbankAccountNumber);
		
		//����Ԥ����Ŀ:���EASϵͳ���ʽ�ƻ��ǵ�����һ��ģ�飬�ʽ�ƻ����Ƹ���ʱ��ͨ���ڸ���ϼӡ��ƻ���Ŀ�����ʽ�ƻ�ģ��ġ��ƻ���Ŀ�����������Ӷ�ʵ�ֶԸ���Ŀ��ƣ�
		//�����ʽ�ƻ�ͨ��Ԥ��ϵͳ�����ƣ���Ԥ��ϵͳ��ͨ��������Ԥ����Ŀ����ʵ�ֶԸ���Ŀ��Ƶģ�������Ҫ�������ԭ���ġ��ƻ���Ŀ����Ϊ��Ԥ��ϵͳ�Ĳ���BG056������
		//by cassiel 2010-09-29
		if(isFpItem){
			this.editData.setFpItem((FpItemInfo)this.prmtFpItem.getValue());
		}else{
			FDCDealFPOrBudgetItemUtil.storeFieldsBgItem(prmtFpItem, editData, FDCDealFPOrBudgetItemUtil.getBgItemProps());
		}
		
		//�����տ��ʺ�(����PT032486�޸�ActRecAccountBank��������Ϊ��������)
		String bankAccountNumber = getAccountNumber();
		AccountBankInfo accountBank = getAccountBankByAccountNumber(bankAccountNumber);
		editData.setPayeeAccountBank(bankAccountNumber);
		editData.setActRecAccountBank(accountBank);
		//�����˾
		editData.setAgentPayCompany((CompanyOrgUnitInfo)prmtAgentCompany.getValue());

		editData.setPayeeAccountBankO(getAccountBankByAccountNumber(bankAccountNumber));
		if (editData.getPayeeAccountBankO() != null) {
			editData.setOppInnerAcct(editData.getPayeeAccountBankO().getInnerAcct());
		} else {
			editData.setOppInnerAcct(null);
		}
		
//		editData.setPayDate(DateTimeUtils.truncateDate(editData.getPayDate()));
		if (editData.getPayerAccount() != null) {
			if (editData.getPayerAccount().isIsBank()) {
				editData.setFundType(BizTypeEnum.BANK);
			} else if (editData.getPayerAccount().isIsCash()) {
				editData.setFundType(BizTypeEnum.CASH);
			} else {
				editData.setFundType(BizTypeEnum.OTHER);
			}
		}
		
		if (comboPayeeType.getSelectedItem() == null
				|| comboPayeeType.getSelectedItem() instanceof FMSysDefinedEnum) {

			editData.setPayeeType(null);
		} else {
			editData.setPayeeType((AsstActTypeInfo) comboPayeeType
					.getSelectedItem());
		}
//		editData.setAddProjectAmt(amtInContractOri);
		// editData.setFeeType((FeeTypeEnum)comboFeeType.getSelectedItem());
		
		super.storeFields();
		
		// �����տ���
		// �����տ���
		if (prmtPayee.getData() ==null){
			editData.setPayeeNumber(null);
			editData.setPayeeName(null);
			editData.setPayeeID(null);
		}
		else if (prmtPayee.getData() instanceof String) {

			editData.setPayeeNumber((String) prmtPayee.getData());
			editData.setPayeeName((String) prmtPayee.getData());

		} else if (prmtPayee.getData() instanceof DataBaseInfo) {

			DataBaseInfo info = (DataBaseInfo) prmtPayee.getData();
			if (info != null) {
				if (info.getId() != null) {
					editData.setPayeeID(info.getId().toString());
				}
				editData.setPayeeNumber(info.getNumber());
				editData.setPayeeName(info.getName());
			}
		}
		
//		editData.setBizDate(editData.getPayDate());
		editData.setBizDate(editData.getBizDate());
		
//		editData.setIsEmergency((IsMergencyEnum)mergencyState.getSelectedItem());

		
		//ͬ����׼
		SettlementTypeInfo settType = (SettlementTypeInfo) prmtSettlementType
		.getData();
		if (settType != null&&settType.getNtType() != null) {
			if (NTTypeGroupEnum.PaymentCheque.equals(settType.getNtType()
					.getSuperGroup())
				) {//R100130-010
				Object o=f7SettleNumber.getData();
				if(o instanceof Object[]){
					Object[] chequeCol=(Object[])o;
					if(chequeCol!=null&&chequeCol.length>0&&chequeCol[0] instanceof ChequeInfo){
						ChequeInfo chequeInfo=(ChequeInfo)chequeCol[0];
						editData.setCheque(chequeInfo);
						editData.setSettlementNumber(chequeInfo.getNumber());
					}
				}else if(o instanceof ChequeInfo){
					editData.setCheque((ChequeInfo)o);
					editData.setSettlementNumber(((ChequeInfo)o).getNumber());
				}
				
			}
		}
		//����һ���ˣ�һ���ǳ�����Ա�����뵥���ɸ��ʱ����ͬһ�ˣ��޸�ʱ��ֵ
		if (this.editData.getCreator() == null)
			this.editData.setCreator((SysContext.getSysContext().getCurrentUserInfo()));
		if (this.editData.getCreateTime() == null)
			this.editData.setCreateTime(new Timestamp(System.currentTimeMillis()));
		
		
		if(this.txtLocalAmt.getBigDecimalValue()!=null&&this.txtLocalAmt.getBigDecimalValue().compareTo(FDCConstants.ZERO)!=0){
			BigDecimal localamount = this.txtLocalAmt.getBigDecimalValue().setScale(2,BigDecimal.ROUND_HALF_UP);
			//��д���Ϊ��λ�ҽ��
			String cap = FDCClientHelper.getChineseFormat(localamount,false);
			txtcapitalAmount.setText(cap);
			this.editData.setCapitalAmount(cap);
		}else{
			txtcapitalAmount.setText(null);
		}
	}
	
    protected void comboPayeeType_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
    }
	
	/**
	 * �������տ������ͱ仯ʱ,�����տ������Ƶ����� ���տ�������Ϊ����ʱ,���տ������ƿ��ֹ�¼����F7ѡ��Ϊ��
	 */
	protected void comboPayeeType_itemStateChanged(ItemEvent e)
			throws Exception {

		if (e.getStateChange() == 2) {
			return;
		}

		if (comboPayeeType.getSelectedItem() == null) {
			return;
		}

		prmtPayee.setValue(null);
		
		txtPayeeBank.setText(null);
		
		txtPayeeAccountBank.setValue(null);
		
//		txtPayeeAcctName.setText(null);

		if (comboPayeeType.getSelectedItem() instanceof AsstActTypeInfo) {

			AsstActTypeInfo info = (AsstActTypeInfo) comboPayeeType
					.getSelectedItem();
			prmtPayee.setSelector(null);
			prmtPayee.setFilterInfoProducer(null);
			GLf7Utils.assignSelector(prmtPayee, info, this);
			if (info.getAsstHGAttribute().equalsIgnoreCase("innerAccount")) {// �ڲ��ʻ���
																				// ������˾����

				info.setIsForCompany(false);
				FilterInfo filter = new FilterInfo();
				filter.getFilterItems().add(new FilterItemInfo("closed", "0"));
				filter.getFilterItems().add(new FilterItemInfo("isUsed", "1"));
				EntityViewInfo evi = new EntityViewInfo();
				evi.setFilter(filter);
				prmtPayee.setEntityViewInfo(evi);
				prmtPayee.setCommitParser(new KDBizPromptBox.DefaultParser(
						"$simpleCode$;$number$;$name$", prmtPayee));
				txtPayeeAccountBank.setEnabled(false);
			}
			else if(info.getAsstHGAttribute().equalsIgnoreCase("bankAccount"))
			{
				prmtPayee.setCommitParser(new KDBizPromptBox.DefaultParser(
						"$simpleCode$;$number$;$name$", prmtPayee));
				txtPayeeAccountBank.setEnabled(false);
			}
			else
			{
				prmtPayee.setCommitParser(new KDBizPromptBox.DefaultParser(
						"$number$;$name$", prmtPayee));
				txtPayeeAccountBank.setEnabled(true);
			}
			prmtPayee.setDisplayFormat("$number$ $name$");

			//����ǹ�Ӧ�������޸�
			Object  obj = comboPayeeType.getSelectedItem();
			if(obj instanceof AsstActTypeInfo ){
				AsstActTypeInfo asstActTypeInfo = (AsstActTypeInfo)obj;
				if ("provider".equalsIgnoreCase(asstActTypeInfo.getAsstHGAttribute())){				
					
					// �����տ���
					if(editData.getActFdcPayeeName()!=null){
//						prmtPayee.setEnabled(false);
						String payeeId = editData.getActFdcPayeeName().getId().toString();
						String payeeNumber = editData.getActFdcPayeeName().getNumber();
						String payeeName = editData.getActFdcPayeeName().getName();
						handler.fillPayeeOrPayer(prmtPayee, payeeId, payeeNumber, payeeName);
					}else if("person".equalsIgnoreCase(asstActTypeInfo.getAsstHGAttribute())){
//						prmtPayee.setEnabled(false);
					}else{
						prmtPayee.setEnabled(true);
					}
				}else{
					prmtPayee.setEnabled(true);
				}
			}
		}

		else if (comboPayeeType.getSelectedItem() instanceof FMSysDefinedEnum) {
			ExtendParser parser = new ExtendParser(prmtPayee);
			prmtPayee.setEditFormat("$name$");
			prmtPayee.setCommitParser(parser);
			prmtPayee.setDisplayFormat("$name$");
			txtPayeeAccountBank.setEnabled(true);
		}
//		//��Ϊinitdatastatus()����¼����ᱻ����2��,��ԭ����false״̬��Ϊtrue,�������ڴ�����һ��״̬
//		if(!canModifyPayeeAccountBankTotal)
//		{
//		txtPayeeAccountBank.setEnabled(canModifyPayeeAccountBankTotal);
//		}
	}
	
	private String getAccountNumber() {
		String bankAccountNumber = null;
		
		if(txtPayeeAccountBank.isVisible()){
			if (txtPayeeAccountBank.getData() instanceof String
					|| txtPayeeAccountBank.getText() instanceof String) {
	
				bankAccountNumber = txtPayeeAccountBank.getText();
	
			} else if (txtPayeeAccountBank.getData() instanceof AccountBankInfo) {
	
				AccountBankInfo info = (AccountBankInfo) txtPayeeAccountBank
						.getData();
				if (info != null) {
					bankAccountNumber = info.getBankAccountNumber();
	
				}
			}
			else if((txtPayeeAccountBank.getData()  instanceof SupplierCompanyBankInfo || 
					txtPayeeAccountBank.getData()  instanceof CustomerCompanyBankInfo)) {
				ObjectBaseInfo obj = (ObjectBaseInfo) txtPayeeAccountBank.getData();
				bankAccountNumber = (String) obj.get("bankAccount");
			}
		}else{
			bankAccountNumber = txtPayeeAccountBank1.getText();
		}
		
		return bankAccountNumber;
	}


	private String getActRecAccountNumber() {
		String bankAccountNumber = null;
		if (prmtPayerAccountBank.getData() instanceof String
				|| prmtPayerAccountBank.getText() instanceof String) {

			bankAccountNumber = prmtPayerAccountBank.getText();

		} else if (prmtPayerAccountBank.getData() instanceof AccountBankInfo) {

			AccountBankInfo info = (AccountBankInfo) prmtPayerAccountBank.getData();
			if (info != null) {
				bankAccountNumber = info.getBankAccountNumber();

			}
		}
		return bankAccountNumber;
	}
	
	private AccountBankInfo getAccountBankByAccountNumber(String acctNumber) {
		if (FMHelper.isEmpty(acctNumber)) {
			return null;
		}
		EntityViewInfo ev = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		ev.setFilter(filter);
		filter.getFilterItems().add(
				new FilterItemInfo("bankAccountNumber", acctNumber.trim()));
		try {
			AccountBankCollection banks = AccountBankFactory
					.getRemoteInstance().getAccountBankCollection(ev);
			if (banks != null && banks.size() > 0) {
				return banks.get(0);
			}
		} catch (BOSException e) {
			this.handUIException(e);
		}
		return null;
	}
	
	

	public void onShow() throws Exception {
		
		tHelper.getDisabledTables().add(this.getDetailTable());
		enableExportExcel(getDetailTable());
		
		// ��ʾinfo�����ݵ����
		if (editData.getContractBillId() != null) {
			if ((BOSUuid.read(editData.getContractBillId()).getType())
					.equals(new ContractWithoutTextInfo().getBOSType())) {
				// final StyleAttributes sa = kdtEntries.getStyleAttributes();
				// sa.setLocked(true);
				// sa.setBackground(noEditColor);
				// btnInputCollect.setEnabled(false);
				// kdtEntries.setEnabled(false);
			}
		}
		super.onShow();
		
		detailHelper = new PaymentBillDetailHelper(this);
		detailHelper.createPaymentBillDetailTable();
		
		if (editData.getBillStatus() == null)
			return;
		if (txtNumber.isEnabled()) {
			txtNumber.requestFocusInWindow();
		} else {
			pkBizDate.requestFocusInWindow();
		}
		Boolean disableSplit = (Boolean) getUIContext().get("disableSplit");
		if (disableSplit != null && disableSplit.booleanValue()) {
			actionSplit.setVisible(false);
			actionSplit.setEnabled(false);
			
			actionSave.setVisible(false);
			actionSave.setEnabled(false);
		}
		if ((!getOprtState().equals(OprtState.ADDNEW) && !getOprtState()
				.equals(OprtState.EDIT))) {
//			final StyleAttributes sa = getDetailTable().getStyleAttributes();
//			sa.setLocked(true);
//			sa.setBackground(noEditColor);
//			getDetailTable().setEnabled(false);
//			getDetailTable().setEditable(false);
		}
		//actionEdit.setEnabled(actionEdit.isVisible());
		
		
		if(getOprtState().equals(OprtState.ADDNEW)){
			ContractWithoutTextInfo context = new ContractWithoutTextInfo();
			String contractBillId = this.editData.getContractBillId();
			if (contractBillId != null && contractBillId.trim().length() > 1) {
				if ((BOSUuid.read(contractBillId).getType()).equals(context.getBOSType())) {
					SelectorItemCollection sel=new SelectorItemCollection();
					sel.add("currency.id");
					sel.add("originalAmount");
					sel.add("amount");
					sel.add("actPaiedLocAmount");
					sel.add("actPaiedAmount");
					sel.add("supplier.*");
					sel.add("person.*");
					
					PayRequestBillInfo pay = PayRequestBillFactory.getRemoteInstance().getPayRequestBillInfo(new ObjectUuidPK(this.editData.getFdcPayReqID()),sel);
					KDTable table = getDetailTable();
					BigDecimal localamount =FDCHelper.ZERO;
					BigDecimal amount=FDCHelper.ZERO;
					BigDecimal exchangeRate = toBigDecimal(txtExchangeRate.getNumberValue());
					
					BigDecimal actPayAmount=FDCHelper.ZERO;
					BigDecimal actPayLocAmount=FDCHelper.ZERO;
					FDCSQLBuilder _builder = new FDCSQLBuilder();
					_builder.appendSql(" select sum(pb.famount) actPayAmount,sum(pb.flocalAmount) actPayLocAmount from t_cas_paymentbill pb");
					_builder.appendSql(" where pb.fFdcPayReqID='"+pay.getId().toString()+"'");
					if(editData.getId()!=null){
						_builder.appendSql(" and pb.fid!='"+editData.getId().toString()+"'");
					}
					final IRowSet rowSet = _builder.executeQuery();
					while (rowSet.next()) {
						if(rowSet.getBigDecimal("actPayAmount")!=null){
							actPayAmount=rowSet.getBigDecimal("actPayAmount");
						}
						if(rowSet.getBigDecimal("actPayLocAmount")!=null){
							actPayLocAmount=rowSet.getBigDecimal("actPayLocAmount");
						}
					}
					
					table.getCell(4, 4).setValue(pay.getOriginalAmount().subtract(actPayAmount));
					localamount=pay.getAmount().subtract(actPayLocAmount);
					this.txtLocalAmt.setValue(localamount);
					this.editData.setLocalAmt(localamount);
					
					amount=localamount.divide(FDCHelper.toBigDecimal(exchangeRate),2,BigDecimal.ROUND_HALF_UP);	
					txtAmount.setValue(amount);
					this.editData.setAmount(amount);
					
					setProjectPriceInContract();

					if(this.prmtPayee.getValue()==null){
						AsstActTypeCollection asstActTypeColl = CasRecPayHandler.getAsstActTypeColl(null, false, false);
					     if(pay.getPerson()!=null){
					    	 for (int i = 0; i < asstActTypeColl.size(); i++) {
								if ("person".equalsIgnoreCase(asstActTypeColl.get(i).getAsstHGAttribute())) {
									this.comboPayeeType.setSelectedItem(asstActTypeColl.get(i));
								}
							 }
							 this.prmtPayee.setValue(pay.getPerson());
//							 this.prmtPayee.setEnabled(false);
					     }
					}
				}
			}
		}
		if(this.editData.getFdcPayReqID()!=null&&prmtCurrency.getValue()!=null){
			FDCSQLBuilder _builder = new FDCSQLBuilder();
			_builder.appendSql(" select pb.fid from t_cas_paymentbill pb");
			_builder.appendSql(" where pb.fFdcPayReqID='"+this.editData.getFdcPayReqID()+"'");
			if(editData.getId()!=null){
				_builder.appendSql(" and pb.fid!='"+editData.getId().toString()+"'");
			}
			final IRowSet rowSet = _builder.executeQuery();
			if (rowSet.size()>0) {
				this.prmtCurrency.setEnabled(false);
			}
		}
		this.actionViewBudget.setVisible(false);
	}

	/*
	 * ���ظ���������Լ���kdtable��¼
	 * 
	 * 
	 */
	public void initUIContentLayout() {
		super.initUIContentLayout();
		pnlPayBillHeader.putClientProperty("OriginalBounds", new Rectangle(10,
				10, 870, 500));
		pnlPayBillEntry.putClientProperty("OriginalBounds", new Rectangle(10,
				10, 870, 500));
		tbpPaymentBill.add(pnlPayBillHeader, new KDLayout.Constraints(10, 10,
				850, 500, KDLayout.Constraints.ANCHOR_TOP
						| KDLayout.Constraints.ANCHOR_BOTTOM_SCALE
						| KDLayout.Constraints.ANCHOR_LEFT_SCALE
						| KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
		txtAmount.setPrecision(2);
		txtAmount.setHorizontalAlignment(JTextField.RIGHT);
		txtAmount.setMinimumValue(FDCHelper.MIN_TOTAL_VALUE);
		txtAmount.setMaximumValue(FDCHelper.MAX_TOTAL_VALUE);
		
		txtLocalAmt.setPrecision(2);
		txtLocalAmt.setHorizontalAlignment(JTextField.RIGHT);
		txtLocalAmt.setMinimumValue(FDCHelper.MIN_TOTAL_VALUE);
		txtLocalAmt.setMaximumValue(FDCHelper.MAX_TOTAL_VALUE);
		
		txtpaymentProportion.setPrecision(2);
		txtpaymentProportion.setRemoveingZeroInDispaly(false);
		txtpaymentProportion.setHorizontalAlignment(JTextField.RIGHT);
		
		txtcompletePrjAmt.setPrecision(2);
		txtcompletePrjAmt.setHorizontalAlignment(JTextField.RIGHT);
		txtcompletePrjAmt.setRemoveingZeroInDispaly(false);
		
		txtAllCompletePrjAmt.setPrecision(2);
		txtAllCompletePrjAmt.setRemoveingZeroInDispaly(false);
		
		txtAllInvoiceAmt.setPrecision(2);
		txtAllInvoiceAmt.setRemoveingZeroInDispaly(false);
		
		txtInvoiceAmt.setPrecision(2);
		txtInvoiceAmt.setRemoveingZeroInDispaly(false);
		
		txtExchangeRate.setRequired(true);
		ExtendParser parset = new ExtendParser(f7SettleNumber);
		f7SettleNumber.setCommitParser(parset);
		
	}

	/*
	 * �õ�������ʾ��¼��table
	 */
	
	private boolean isFormContractQuery = false;
	
	private KDTable createPaymentBillTable() {
		isFormContractQuery = false;
		if(getUIContext().get("fromContractQuery") != null){
			isFormContractQuery = Boolean.valueOf(getUIContext().get("fromContractQuery").toString()).booleanValue();
		}
		
		logger.debug("��ʼ����¼table:");
		// 6��1ͷ��0�еı��
		KDTable table = new KDTable(8, 1, 0);
       
		// EXCEL������������,������
//		 table.setHeadDisplayMode(KDTStyleConstants.HEAD_DISPLAY_EXCEL);
		// ����������
		table.getIndexColumn().getStyleAttributes().setHided(true);
		table.setRefresh(false);
		table.getScriptManager().setAutoRun(false);
		StyleAttributes sa = table.getStyleAttributes();
		// �Ƿ�ɱ༭
		sa.setLocked(true);
		sa.setNumberFormat("###,##0.00");
		// �ں�ͷ��
		IRow headRow = table.getHeadRow(0);
//		headRow.getCell(0).setValue(PaymentBillClientUtils.getRes("payTable"));
		headRow.getCell(0).setValue("���̸��������");
		KDTMergeManager mm = table.getHeadMergeManager();
		mm.mergeBlock(0, 0, 0, 7, KDTMergeManager.SPECIFY_MERGE);
		
		BigDecimal temp = FDCHelper.ZERO;
		BigDecimal tempOri = FDCHelper.ZERO;
		if (this.editData.getPayPartAMatlAmt() != null)
			temp = this.editData.getPayPartAMatlAmt();
		//ԭ�������ֶ�
		// ����̶���
		initFixTable(table);
		if (editData.getContractBillId() != null) {
			if ((BOSUuid.read(editData.getContractBillId()).getType()).equals(new ContractBillInfo().getBOSType())) {
				updateDynamicValue();
				kdtEntries.getScriptManager().runAll();
				PaymentBillClientUtils.getValueFromCell(editData, bindCellMap);
			}
		}
		// �ڵ�9�в���Ӧ�ۿ����¼
		int lstDeductTypeRowIdx = initDynamicTable(table);
		int lastRowIdx = table.getRowCount() - 1; // ���һ��
		
		// ����Ƿ��������ĸ��
//		try {
		PayRequestBillInfo info = (PayRequestBillInfo)initData.get("PayRequestBillInfo");
		if(info.getPayPartAMatlOriAmt() != null)
			tempOri = info.getPayPartAMatlOriAmt();
		if(FDCHelper.toBigDecimal(temp).compareTo(FDCHelper.ZERO)==0){
			tempOri = FDCHelper.ZERO;
		}
		PaymentBillCollection paymentColl =  (PaymentBillCollection)initData.get("PaymentBillCollection_paymentColl");
		Iterator iter = paymentColl.iterator();
		if (!iter.hasNext()) {
			ContractBillInfo con = new ContractBillInfo();
			String contractBillId = this.editData.getContractBillId();
			if (contractBillId != null && contractBillId.trim().length() > 1) {
				if ((BOSUuid.read(contractBillId).getType()).equals(con.getBOSType())) {
					ContractBillInfo contractBill = (ContractBillInfo)initData.get(FDCConstants.FDC_INIT_CONTRACT);
							
					if (contractBill.getAddPrjAmtPaid() != null) {
								// ���ӹ��̿�
						if ((contractBill.getAddPrjAmtPaid().compareTo(FDCHelper.ZERO)) == 0) {
							editData.setLstAddPrjAllPaidAmt(null);
									((ICell) bindCellMap.get(PaymentBillContants.LSTADDPRJALLPAIDAMT)).setValue(null);
									table.getCell(6, 2).setValue(null);
						} else {
							editData.setLstAddPrjAllPaidAmt(contractBill.getAddPrjAmtPaid());
							((ICell) bindCellMap.get(PaymentBillContants.LSTADDPRJALLPAIDAMT)).setValue(contractBill.getAddPrjAmtPaid());
							table.getCell(6, 2).setValue(contractBill.getAddPrjAmtPaid());
						}
					}
					
					if (contractBill.getPrjPriceInConPaid() != null) {
								// ��ͬ�ڹ��̿�
						if ((contractBill.getPrjPriceInConPaid().compareTo(FDCHelper.ZERO)) == 0) {
							editData.setLstPrjAllPaidAmt(null);
							((ICell) bindCellMap.get(PaymentBillContants.LSTPRJALLPAIDAMT)).setValue(null);
							table.getCell(4, 2).setValue(null);
						} else {
							editData.setLstPrjAllPaidAmt(contractBill.getPrjPriceInConPaid());
							((ICell) bindCellMap.get(PaymentBillContants.LSTPRJALLPAIDAMT)).setValue(contractBill.getPrjPriceInConPaid());
							table.getCell(4, 2).setValue(contractBill.getPrjPriceInConPaid());
						}
					}
					
					if (contractBill.getPaidPartAMatlAmt() != null) {
								// �׹���
						if ((contractBill.getPaidPartAMatlAmt().compareTo(FDCHelper.ZERO)) == 0) {
							editData.setLstAMatlAllPaidAmt(null);
							((ICell) bindCellMap.get(PaymentBillContants.LSTAMATLALLPAIDAMT)).setValue(null);
							table.getCell(lstDeductTypeRowIdx + 1, 2).setValue(null);
						} else {
							editData.setLstAMatlAllPaidAmt(contractBill.getPaidPartAMatlAmt());
							((ICell) bindCellMap.get(PaymentBillContants.LSTAMATLALLPAIDAMT)).setValue(contractBill.getPaidPartAMatlAmt());
							table.getCell(lstDeductTypeRowIdx + 1, 2).setValue(contractBill.getPaidPartAMatlAmt());
						}
					}
					
					//���ǩ֤
					BigDecimal amount = FDCHelper.ZERO;
					ContractChangeBillCollection collection =  (ContractChangeBillCollection)initData.get("ContractChangeBillCollection");
					ContractChangeBillInfo billInfo;
					for (Iterator itor = collection.iterator(); itor
									.hasNext();) {
						billInfo = (ContractChangeBillInfo) itor.next();
						if (billInfo.getAmount() != null)
							amount = amount.add(billInfo.getAmount());
					}
							
					// ���ñ��ǩ֤���
					if ((amount.compareTo(FDCHelper.ZERO)) == 0) {
						((ICell) bindCellMap
										.get(PaymentBillContants.CHANGEAMT))
										.setValue(null);
								editData.setChangeAmt(null);
					} else {
						((ICell) bindCellMap
									.get(PaymentBillContants.CHANGEAMT))
										.setValue(amount);
								editData.setChangeAmt(amount);
					}
							// ������
					if(!isFormContractQuery){
						if (contractBill.getSettleAmt() != null) {
							if ((contractBill.getSettleAmt().compareTo(FDCHelper.ZERO)) == 0)
										((ICell) bindCellMap.get(PaymentBillContants.SETTLEAMT)).setValue(null);
							else
										((ICell) bindCellMap.get(PaymentBillContants.SETTLEAMT)).setValue(contractBill.getSettleAmt());
						}
					}else{
						if (contractBill.getSettleAmt() != null) {
							if ((contractBill.getSettleAmt().compareTo(FDCHelper.ZERO)) == 0)
										table.getCell(2, 7).setValue(null);
							else
								table.getCell(2, 7).setValue(contractBill.getSettleAmt());
						}
					}
							// editData.setSettleAmt(contractBill.getSettleAmt());
					//2009-1-20 ��Ӧ��ʹ��new BigDecimal(0),��Ӧ����new BigDecimal("0")
					amount = FDCHelper.ZERO;
//					amount = new BigDecimal(0);

							// ���ݽ��㵥��״̬������������۵�ֵ,�ѽ����Ϊ�����
					if (!contractBill.isHasSettled()) {
								// δ���ս���,������ۣ���ͬ�ۣ������ͬ�ۣ���Ҫ��һ����ȷ���󣩣��ú�ͬ�����������б��ǩ֤���Ľ��֮��
						if (contractBill.getAmount() != null) {
							if (editData.getChangeAmt() != null)
								amount = contractBill.getAmount().add(
												editData.getChangeAmt());
							else
										amount = contractBill.getAmount();
						}else if (editData.getChangeAmt() != null)
									amount = editData.getChangeAmt();
						else
									amount = FDCHelper.ZERO;
					} else {
						if (contractBill.getSettleAmt() != null)
									amount = contractBill.getSettleAmt();
					}
					
					if ((amount.compareTo(FDCHelper.ZERO)) == 0) {
						editData.setLatestPrice(null);
						((ICell) bindCellMap
										.get(PaymentBillContants.LATESTPRICE))
										.setValue(null);
					} else {
						editData.setLatestPrice(amount);
						((ICell) bindCellMap
										.get(PaymentBillContants.LATESTPRICE))
										.setValue(amount);
					}
						
				}
			}
		}

		ContractBillInfo con = new ContractBillInfo();
		ContractWithoutTextInfo context = new ContractWithoutTextInfo();
		String contractBillId = this.editData.getContractBillId();
		if (contractBillId != null && contractBillId.trim().length() > 1) {
			try {
				if ((BOSUuid.read(contractBillId).getType()).equals(con
						.getBOSType())) {
					ContractBillInfo contractBill = (ContractBillInfo)initData.get(FDCConstants.FDC_INIT_CONTRACT);
					// ��ͬ���
					this.txtContractNo.setText(contractBill.getNumber());
					if (contractBill.getNumber() != null)
						editData.setContractNo(contractBill.getNumber());
					// ��ͬ����
					table.getCell(1, 1).setValue(contractBill.getName());
					// ��ͬ���
					/*if (contractBill.getAmount() != null) {
						if (((contractBill.getAmount())
								.compareTo(FDCHelper.ZERO)) == 0) {
							table.getCell(0, 6).setValue(null);
						} else
							table.getCell(0, 6).setValue(
									contractBill.getAmount());
					}*/
					// �����������޶���ͬ��ȡ��ǰ�����
					
					if(!isFormContractQuery){
						if(FDCHelper.toBigDecimal(info.getContractPrice(),2).compareTo(FDCHelper.ZERO)==0){
							table.getCell(0, 6).setValue(null);
						}else{
							table.getCell(0, 6).setValue(info.getContractPrice());
						}
					}else{
						if(FDCHelper.toBigDecimal(info.getContractPrice(),2).compareTo(FDCHelper.ZERO)==0){
							table.getCell(0, 7).setValue(null);
						}else{
							table.getCell(0, 7).setValue(info.getContractPrice());
						}
					}
					
				} else if ((BOSUuid.read(contractBillId).getType())
						.equals(context.getBOSType())) {
					ContractWithoutTextInfo text = (ContractWithoutTextInfo)initData.get(FDCConstants.FDC_INIT_CONTRACT);
					this.txtContractNo.setText(text.getNumber());
					if (text.getNumber() != null)
						editData.setContractNo(text.getNumber());
					// ��ͬ����
					table.getCell(1, 1).setValue(text.getName());
					// ��ͬ���
					if(!isFormContractQuery){
						if(FDCHelper.toBigDecimal(info.getContractPrice(),2).compareTo(FDCHelper.ZERO)==0){
							table.getCell(0, 6).setValue(null);
						}else{
							table.getCell(0, 6).setValue(info.getContractPrice());
						}
					}else{
						if(FDCHelper.toBigDecimal(info.getContractPrice(),2).compareTo(FDCHelper.ZERO)==0){
							table.getCell(0, 7).setValue(null);
						}else{
							table.getCell(0, 7).setValue(info.getContractPrice());
						}
					}
				}
			} catch (Exception e) {
				super.handUIException(e);
			} 
		}

		if (this.editData.getLstAMatlAllPaidAmt() != null) {
			if (this.editData.getLstAMatlAllPaidAmt().compareTo(FDCHelper.ZERO) == 0) {
				table.getCell(lstDeductTypeRowIdx + 1, 2).setValue(null);
			} else
				table.getCell(lstDeductTypeRowIdx + 1, 2).setValue(
						this.editData.getLstAMatlAllPaidAmt());
		}

		/**
		 * 
		 * ����ͳ�ƹ�ʽ--���ȿ���,��sum���ú��������м���,�÷�������Excel ��sum(D3:D7);
		 * 
		 * ���㹫ʽ:
		 * ���������ۼ����� = ���������ۼ�����
		 * ���������ۼ�ʵ�� = ���������ۼ�ʵ�� + ���ڷ���
		 * 
		 */
		IRow row = null;
		// ��ͬ�ڹ��̿�
		row = table.getRow(4);
		row.getCell(6).setExpressions("=sum(D5)");
		// row.getCell(5).setExpressions("=sum(D5,E5)");
		row.getCell(7).setExpressions("=sum(C5,F5)");
		
		// Ԥ����
		row = table.getRow(5);
		row.getCell(6).setExpressions("=sum(D6)");
		row.getCell(7).setExpressions("=sum(C6,F6)");
		// ���̿�(������)
		row = table.getRow(6);
		row.getCell(6).setExpressions("=sum(D7)");
		// row.getCell(5).setExpressions("=sum(D6,E6)");
		row.getCell(7).setExpressions("=sum(C7,F7)");
		// ����
		row = table.getRow(7);
		row.getCell(6).setExpressions("=sum(D8)");
		// row.getCell(6).setExpressions("=sum(C7)");
		row.getCell(7).setExpressions("=sum(C8,F8)");
		// ΥԼ
		row = table.getRow(8);
		row.getCell(6).setExpressions("=sum(D9)");
		row.getCell(7).setExpressions("=sum(C9,F9)");
		/*
		 * // С�� row = table.getRow(7);
		 * row.getCell(2).setExpressions("=sum(c5,c7)");
		 * row.getCell(3).setExpressions("=sum(D5,D7)");
		 * row.getCell(4).setExpressions("=sum(E5,E7)");
		 * row.getCell(5).setExpressions("=sum(D8)"); //
		 * row.getCell(5).setExpressions("=sum(D7,E7)");
		 * row.getCell(6).setExpressions("=sum(C8,E8)");
		 */
		/*
		 * �׹��Ŀ��ۼ�
		 */
		row = table.getRow(lstDeductTypeRowIdx + 1);
		//�׹��� �������� ԭ��
		if ((tempOri.compareTo(FDCHelper.ZERO)) == 0)
			row.getCell(4).setValue(null);
		else
			row.getCell(4).setValue(tempOri);
		//�׹��� �������� ����
		if ((temp.compareTo(FDCHelper.ZERO)) == 0)
			row.getCell(5).setValue(null);
		else
			row.getCell(5).setValue(temp);
//		row.getCell(4).getStyleAttributes().setLocked(!isBaseCurrency);
		row.getCell(6).setExpressions("=D" + (lstDeductTypeRowIdx + 2));
		// row.getCell(5).setExpressions("=D"+(lstDeductTypeRowIdx+2)+"+E"+(lstDeductTypeRowIdx+2));
		row.getCell(7).setExpressions(
				"=(C" + (lstDeductTypeRowIdx + 2) + "+F"
						+ (lstDeductTypeRowIdx + 2) + ")");
		/**
		 * ʵ���� ��ΪҪ���������ܶ�λӦ�ۿ���С�Ƶ�λ��,�ʷŵ�����������. ʵ���� ʵ������ȿ�С�ƣ�Ӧ�ۿ���С��-Ӧ�ۼ׹���
		 * ����Ϊ:ʵ�����ͬ�ڹ��̿�+����-ΥԼ��-Ӧ�ۿ���С��-Ӧ�ۼ׹��� by sxhong 2007/09/28
		 */
		int paidRowIdx = lastRowIdx - 4;
		row = table.getRow(paidRowIdx);
		ICell cell = null;
		StringBuffer exp;
		for (char c = 'C'; c <= 'H'; c++) {
			cell = row.getCell(c - 'A');
			exp = new StringBuffer("=");
			exp.append(c).append(5).append("+"); // ��ͬ�ڹ��̿�
			exp.append(c).append(6).append("+"); //Ԥ����
			exp.append(c).append(8).append("-"); // ����
			exp.append(c).append(9).append("-"); // ΥԼ��
			exp.append(c).append(paidRowIdx - 1).append("-");// �׹���
			exp.append(c).append(paidRowIdx); // Ӧ�ۿ�С��
			cell.setExpressions(exp.toString());
		}
		
		/**
		 * 
		 * ��� ��������ۣ����ȿ�С�� ����Ϊ ��������ۣ���ͬ�ڹ��̿� by sxhong 2007-9-28
		 * 
		 * ����ұ��п�������� ��ʽ����Ϊ
		 * ��� = ������� - ����ͬ�ڹ��̿�*���ʣ�  kelvin_yang 2008-09-23
		 * ��� = ������� - ����ͬ�ڹ��̿� + Ԥ��� ���Ҳ��ܳ˻��� by hpw 2009-07-22
		 * ����Ϊ ��������ۣ�ʵ����С��  by hpw 2009-7-24
		 * ���=�������-���ȿ����ֹ�����ۼ�ʵ�� by cassiel_peng 2010-05-28
		 */
//		table.getCell(paidRowIdx + 1, 6).setExpressions("=F2-G8");
		
//		BigDecimal exRate = editData.getExchangeRate();
//		if(exRate==null || exRate == FDCHelper.ZERO){
//			exRate = FDCHelper.ONE;
//		}
//		BigDecimal exRate = FDCHelper.toBigDecimal(editData.getExchangeRate());
		
//		table.getCell(paidRowIdx + 1, 7).setExpressions("=F2-(G5*"+exRate+")");
//		table.getCell(paidRowIdx + 1, 7).setExpressions("=G2-(H5*"+exRate+")");
//		table.getCell(paidRowIdx + 1, 7).setExpressions("=G2 - (H5 + H6)");
		if(!isFormContractQuery){
			table.getCell(paidRowIdx + 1, 7).setExpressions("=G2 - H"+(lastRowIdx-3));
			table.getCell(paidRowIdx + 1, 7).setExpressions("=G2-H5");
		}else{
			table.getCell(paidRowIdx + 1, 7).setExpressions("=H2 - H"+(lastRowIdx-3));
			table.getCell(paidRowIdx + 1, 7).setExpressions("=H2-H5");
		}
		
		/**
		 * 
		 * ������ۿ���Ϊ��,����ֱ��ʹ�ü��㹫ʽ�ķ���
		 * 
		 * ��������%=���ڷ���ʵ����/�������
		 * �ۼ�����%=�����ۼ�����/�������
		 * 
		 */
		row = table.getRow(lastRowIdx-1);
		if(!isFormContractQuery){
			if (editData.getLatestPrice() != null&& editData.getLatestPrice().compareTo(FDCHelper.ZERO) > 0) {
				//��������%
				exp = new StringBuffer("=(");
				exp.append("F").append(5).append("/");
				exp.append("G2)*100");
				row.getCell(1).setExpressions(exp.toString());
				//�ۼ�����%
				exp = new StringBuffer("=(");
				exp.append("G").append(5).append("/");
				exp.append("G2)*100");
				row.getCell(3).setExpressions(exp.toString());
				
				// Ӧ�����룬Ӧ���ۼ�����
				row = table.getRow(lastRowIdx);
				exp = new StringBuffer("=(");
				exp.append("F").append(lastRowIdx-3).append("/");
				exp.append("G2)*100");
				row.getCell(1).setExpressions(exp.toString());
				exp = new StringBuffer("=(");
				exp.append("G").append(lastRowIdx-3 ).append("/");
				exp.append("G2)*100");
				row.getCell(3).setExpressions(exp.toString());
			}
		}
		
		
		
		// ���ö��뷽ʽ
		sa = table.getColumn(1).getStyleAttributes();
		sa.setHorizontalAlign(HorizontalAlignment.RIGHT);
		//��Ŀ����,��ͬ�����������
		table.getCell(0, 1).getStyleAttributes().setHorizontalAlign(HorizontalAlignment.LEFT);
		table.getCell(0, 7).getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
		table.getCell(1, 7).getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
		table.getCell(2, 7).getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
		table.getCell(1, 1).getStyleAttributes().setHorizontalAlign(HorizontalAlignment.LEFT);
		if(!isFormContractQuery){
			table.getCell(0, 6).getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
			table.getCell(1, 6).getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
			table.getCell(2, 6).getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
		}
		table.getCell(2, 1).getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
		table.getCell(2, 3).getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);

		for (int i = 0; i < 2; i++) {
			row = table.getRow(lastRowIdx - i);
			for (int j = 1; j < 7; j += 2) {
				sa = row.getCell(j).getStyleAttributes();
				sa.setHorizontalAlign(HorizontalAlignment.RIGHT);
			}
		}
		kdtEntries = table;
		String paramValue="false";
		try {
			paramValue = ParamControlFactory.getRemoteInstance().getParamValue(new ObjectUuidPK(SysContext.getSysContext().getCurrentOrgUnit().getId()), "ISALLOWADD");
		} catch (EASBizException e) {
			e.printStackTrace();
		} catch (BOSException e) {
			e.printStackTrace();
		}
		if("true".equals(paramValue)){
			setTableCellColorAndEdit(false);
		}else{
			setTableCellColorAndEdit(true);
		}
		
		table.setRefresh(true);
		// �Զ�������С
		table.setAutoResize(true);
		table.getScriptManager().setAutoRun(true);
		// �̶����
		table.getIndexColumn().setWidthAdjustMode(KDTIndexColumn.WIDTH_FIXED);
		kdtEntries.getScriptManager().runAll();

		table.getRow(5).getStyleAttributes().setHided(!isAdvance());
		// �������ӹ��̿���
		table.getRow(6).getStyleAttributes().setHided(true);

		// ȥ������ƻ��ĸ�:
		for (int i = 4; i < table.getColumnCount(); i++) {
			StyleAttributes styleAttributes = table.getCell(lastRowIdx - 1, i)
					.getStyleAttributes();
			styleAttributes.setLocked(true);
			styleAttributes.setBackground(noEditColor);
		}
		table.getCell(lastRowIdx - 1, 1).getStyleAttributes().setLocked(true);
		table.getCell(lastRowIdx - 1, 1).getStyleAttributes().setBackground(noEditColor);
		if(isFormContractQuery){
			table.getCell(2,6).setValue("������");
			table.getCell(4, 7).setValue(getPaiedAmt());
		}
		return table;
	}
	
	public BigDecimal getPaiedAmt(){
		BigDecimal paiedAmt = FDCHelper.ZERO;
		String cId = editData.getContractBillId();
		FDCSQLBuilder builder = new FDCSQLBuilder();
		builder.appendSql("select sum(famount) from t_cas_paymentbill where fbillstatus = 15 and fcontractbillid = ?");
		builder.addParam(cId);
		try {
			RowSet rs = builder.executeQuery();
			while(rs.next()){
				paiedAmt = rs.getBigDecimal(1);
			}
		} catch (BOSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return paiedAmt;
	}
	
	/**
	 * ���ñ��Ԫ��Ŀɱ༭״̬����ɫ
	 * 
	 * @author sxhong Date 2006-9-28
	 */
	private void setTableCellColorAndEdit(boolean canModifyAmount) {

		// ���üƻ������п���¼��
		KDTable table = getDetailTable();
		IRow row;
		ICell cell;
		int lastRowIdx = table.getRowCount() - 1;
		table.setRefresh(false);
		table.setEditable(canModifyAmount && true);
		table.setEnabled(canModifyAmount && true);
		StyleAttributes sa;
		sa = table.getStyleAttributes();
		// sa.setLocked(false);
		sa.setBackground(Color.WHITE);
		/*
		 * �ɱ༭��Ԫ��
		 */
		// ���ڷ���
		boolean isTemp = editData.getFdcPayType().getPayType().getId().toString().equals(PaymentTypeInfo.tempID);
		table.getCell(4, 4).getStyleAttributes().setLocked(isTemp?true:false);
		table.getCell(4, 5).getStyleAttributes().setLocked(canModifyAmount);
		//�ͻ�����Ҫ����ס
//		table.getCell(5, 4).getStyleAttributes().setLocked(false);
		table.getCell(5, 5).getStyleAttributes().setLocked(true);
		if(isBaseCurrency){
			table.getCell(6, 5).getStyleAttributes().setLocked(!canModifyAmount || false);
			// �׹���
//			table.getCell(lastRowIdx - 4, 4).getStyleAttributes().setLocked(!canModifyAmount ||false);
			table.getCell(lastRowIdx - 4, 5).getStyleAttributes().setLocked(true);
			row = table.getRow(lastRowIdx - 1);
			row.getCell(1).getStyleAttributes().setLocked(!canModifyAmount ||false);
			row.getCell(3).getStyleAttributes().setLocked(!canModifyAmount ||false);
//			row.getCell(5).getStyleAttributes().setLocked(!canModifyAmount ||false);
			table.getCell(lastRowIdx, 6).getStyleAttributes().setLocked(!canModifyAmount ||false);
		}
		else{
//			table.getCell(5, 5).getStyleAttributes().setLocked(true);
			// �׹���
			table.getCell(lastRowIdx - 4, 5).getStyleAttributes().setLocked(true);
			row = table.getRow(lastRowIdx - 1);
			row.getCell(1).getStyleAttributes().setLocked(true);
			row.getCell(3).getStyleAttributes().setLocked(true);
			row.getCell(5).getStyleAttributes().setLocked(true);
			table.getCell(lastRowIdx, 6).getStyleAttributes().setLocked(true);
		}
		table.getCell(lastRowIdx - 4, 6).getStyleAttributes().setLocked(true);
		/*
		 * ��ɫ
		 */
		for (int i = 4; i < table.getRowCount() - 2; i++) {
			row = table.getRow(i);
			for (int j = 2; j < table.getColumnCount(); j++) {
				cell = row.getCell(j);
				sa = cell.getStyleAttributes();
				sa.setHorizontalAlign(HorizontalAlignment.RIGHT);
				if (sa.isLocked()) {
					sa.setBackground(noEditColor);
				}
			}
		}
		if(!isFormContractQuery){
			table.getCell(0, 6).getStyleAttributes().setBackground(noEditColor);
			table.getCell(1, 6).getStyleAttributes().setBackground(noEditColor);
			table.getCell(2, 6).getStyleAttributes().setBackground(noEditColor);
		}else{
			table.getCell(0, 7).getStyleAttributes().setBackground(noEditColor);
			table.getCell(1, 7).getStyleAttributes().setBackground(noEditColor);
			table.getCell(2, 7).getStyleAttributes().setBackground(noEditColor);
		}
		
		table.getCell(2, 1).getStyleAttributes().setBackground(noEditColor);
		table.getCell(2, 3).getStyleAttributes().setBackground(noEditColor);
		table.getCell(lastRowIdx, 1).getStyleAttributes().setBackground(noEditColor);
		table.getCell(lastRowIdx, 3).getStyleAttributes().setBackground(noEditColor);

		// �������
		table.getCell(2, 1).getStyleAttributes().setBackground(noEditColor);
		table.setRefresh(true);
		btnInputCollect.setEnabled(canModifyAmount && !STATUS_VIEW.equals(this.oprtState));
		table.getCell(lastRowIdx - 1, 3).getStyleAttributes().setLocked(true);
		table.getCell(lastRowIdx - 1, 3).getStyleAttributes().setBackground(noEditColor);
		// table.repaint();
		// table.refresh();
		
		if(this.editData.getFdcPayReqID()!=null&&prmtCurrency.getValue()!=null){
			SelectorItemCollection sel=new SelectorItemCollection();
			sel.add("currency.id");
			try {
				PayRequestBillInfo pay = PayRequestBillFactory.getRemoteInstance().getPayRequestBillInfo(new ObjectUuidPK(this.editData.getFdcPayReqID()),sel);
				CurrencyInfo currency=(CurrencyInfo)prmtCurrency.getValue();
				if(!pay.getCurrency().getId().toString().equals(currency.getId().toString())){
					table.getCell(4, 4).getStyleAttributes().setLocked(true);
				}else{
					table.getCell(4, 4).getStyleAttributes().setLocked(false);
				}
			} catch (EASBizException e) {
				e.printStackTrace();
			} catch (BOSException e) {
				e.printStackTrace();
			}
		}
	}

	/*
	 * ��ʼ�����Ĺ̶�����,��ʼ����ʱ��������Ϣ��bindCellmap
	 * 
	 */
	private void initFixTable(KDTable table) {
		// ���15��,�ڵ�9�в���Ӧ�ۿ����¼
		table.addRows(15);
		IRow row;
		ICell cell;
		// ��һ��
		// BindCellUtil.bindCell(table, rowIndex, colIndex, name, valueName,
		// valueProperty, bindCellMap)
		// ��Ŀ����
		String str = PaymentBillClientUtils.getRes("projectName");
		if (editData.getCurProject() != null) {
			String name = null;
			if (curProject.getFullOrgUnit()!= null) {
				name = curProject.getFullOrgUnit().getName() + "\\"	+ editData.getCurProject().getDisplayName();				
			} else
				name = editData.getCurProject().getDisplayName();
			name = name.toString().replace('_', '\\');
			table.getCell(0, 1).setValue(name);
			table.getCell(0, 1).getStyleAttributes().setNumberFormat("@");
		}
		table.getCell(0, 0).setValue(str);

		// ��ͬ����
		str = PaymentBillClientUtils.getRes("contractName");
		table.getCell(1, 0).setValue(str);
		table.getCell(1, 1).getStyleAttributes().setNumberFormat("@");
		// table.getCell(1,1).setValue(contactName);
		// PaymentBillClientUtils.bindCell(table, 1, 0, "��ͬ����",
		// PaymentBillContants.CONTRACTNAME, bindCellMap);
		// ���ǩ֤���
		str = PaymentBillClientUtils.getRes("changeAmount");
		if (editData.getChangeAmt() != null) {
			if ((editData.getChangeAmt().compareTo(FDCHelper.ZERO)) == 0)
				table.getCell(2, 1).setValue(null);
			else
				table.getCell(2, 1).setValue(this.editData.getChangeAmt());
		}
		PaymentBillClientUtils.bindCell(table, 2, 0, str,PaymentBillContants.CHANGEAMT, bindCellMap, true);
		// �������
		//����ǴӺ�ͬ��ѯ���棬����ʾ�������2010-06-18
		if(!isFormContractQuery){
			str = PaymentBillClientUtils.getRes("payTimes");
			PaymentBillClientUtils.bindCell(table, 3, 0, str,PaymentBillContants.PAYTIMES, bindCellMap);
		}
		
		// ���ȿ���
		str = PaymentBillClientUtils.getRes("scheduleAmt");
		table.getCell(4, 0).setValue(str);

		// Ӧ�ۿ��������5��
		// Ӧ�ۼ׹��Ŀ�
		str = PaymentBillClientUtils.getRes("shouldSub");
		table.getCell(9, 0).setValue(str);
		// ʵ����
		str = PaymentBillClientUtils.getRes("payAmt");
		table.getCell(10, 0).setValue(str);
		// ���
		str = PaymentBillClientUtils.getRes("balance");
		table.getCell(11, 0).setValue(str);
		// ���ڼƻ�����
		str = PaymentBillClientUtils.getRes("payThis");
		if (editData.getCurPlannedPayment() != null) {
			if ((editData.getCurPlannedPayment().compareTo(FDCHelper.ZERO)) == 0)
				table.getCell(12, 1).setValue(null);
			else
				table.getCell(12, 1).setValue(
						this.editData.getCurPlannedPayment());
		}
		PaymentBillClientUtils.bindCell(table, 12, 0, str,
				PaymentBillContants.CURPLANNEDPAYMENT, bindCellMap, true);
		// ��������%
		str = PaymentBillClientUtils.getRes("reqThis");
		PaymentBillClientUtils.bindCell(table, 13, 0, str,
				PaymentBillContants.CURREQPERCENT, bindCellMap);
		//Ӧ������
		table.getCell(14, 0).setValue("Ӧ������%");
		//�ۼ�Ӧ������
		table.getCell(14, 2).setValue("�ۼ�Ӧ������%");
		
		// �ڶ���
		// ���ȿ���
		// ��ͬ�ڹ��̿�
		str = PaymentBillClientUtils.getRes("amtInContract");
		table.getCell(4, 1).setValue(str);
		//Ԥ����
		table.getCell(5, 1).setValue("Ԥ����");
		// ���ӹ��̿�
		str = PaymentBillClientUtils.getRes("addAmt");
		table.getCell(6, 1).setValue(str);
		str = PaymentBillClientUtils.getRes("guerdon");
		table.getCell(7, 1).setValue(str);// ���ӽ�������6�У������ļ�һ
		table.getCell(8, 1).setValue("ΥԼ��");// ��ΥԼ�����С��
		/*
		 * // С�� str = PaymentBillClientUtils.getRes("add"); table.getCell(7,
		 * 1).setValue(str);
		 */

		// ������
		// ������
		str = PaymentBillClientUtils.getRes("subAmt");
		if(!isFormContractQuery){
			if (editData.getSettleAmt() != null) {
				if ((editData.getSettleAmt().compareTo(FDCHelper.ZERO)) == 0)
					table.getCell(2, 3).setValue(null);
				else
					table.getCell(2, 3).setValue(this.editData.getSettleAmt());
				PaymentBillClientUtils.bindCell(table, 2, 2, str,PaymentBillContants.SETTLEAMT, bindCellMap, true);
			}
		}else{
			if (editData.getSettleAmt() != null) {
				if ((editData.getSettleAmt().compareTo(FDCHelper.ZERO)) == 0)
					table.getCell(2, 7).setValue(null);
				else
					table.getCell(2, 7).setValue(this.editData.getSettleAmt());
			}
		}
	
		
		// �����ۼ�ʵ��
		str = PaymentBillClientUtils.getRes("payLast");
		table.getCell(3, 2).setValue(str);
		// ����Ƿ����
		str = PaymentBillClientUtils.getRes("loanThis");
		if (editData.getCurBackPay() != null) {
			if ((editData.getCurBackPay().compareTo(FDCHelper.ZERO)) == 0)
				table.getCell(12, 3).setValue(null);
			else
				table.getCell(12, 3).setValue(this.editData.getCurBackPay());
		}
		PaymentBillClientUtils.bindCell(table, 12, 2, str,PaymentBillContants.CURBACKPAY, bindCellMap, true);
		// BindCellUtil.bindCell(table, 11, 2, "�ۼ����룥",
		// PaymentBillContants.ALLREQPERCENT, bindCellMap);
		// �ۼ����룥
		str = PaymentBillClientUtils.getRes("reqAll");
		table.getCell(13, 2).setValue(str);

		// ������
		// �����ۼ�����
		str = PaymentBillClientUtils.getRes("reqLastAll");
		table.getCell(3, 3).setValue(str);

		// ������
		// ��ͬ���
		str = PaymentBillClientUtils.getRes("contraPrice");
		table.getCell(0, 5).setValue(str);
		// �������
		str = PaymentBillClientUtils.getRes("latestPrice");
		if(!isFormContractQuery){
			if (editData.getLatestPrice() != null) {
				if ((editData.getLatestPrice().compareTo(FDCHelper.ZERO)) == 0)
					table.getCell(1, 6).setValue(null);
				else
					table.getCell(1, 6).setValue(this.editData.getLatestPrice());
			}
		}else{
			if (editData.getLatestPrice() != null) {
				if ((editData.getLatestPrice().compareTo(FDCHelper.ZERO)) == 0)
					table.getCell(1, 7).setValue(null);
				else
					table.getCell(1, 7).setValue(this.editData.getLatestPrice());
			}
		}
		PaymentBillClientUtils.bindCell(table, 1, 5, str,PaymentBillContants.LATESTPRICE, bindCellMap, true);
		// �����뵥�Ѹ����
		str = PaymentBillClientUtils.getRes("payReq");
		BigDecimal temp = FDCHelper.ZERO;
		
		PaymentBillInfo billInfo = null;
		PaymentBillCollection payColHasPaid =  (PaymentBillCollection)initData.get("PaymentBillCollection_hasPaid");		
		for (Iterator itor = payColHasPaid.iterator(); itor.hasNext();) {
			billInfo = (PaymentBillInfo) itor.next();
			temp = temp.add(billInfo.getAmount());
		}
		
		
		if (temp != null) {
			if(!isFormContractQuery){
				if ((temp.compareTo(FDCHelper.ZERO)) == 0)
					table.getCell(2, 6).setValue(null);
				else
					table.getCell(2, 6).setValue(temp);
				
				PaymentBillClientUtils.bindCell(table, 2, 5, str,PaymentBillContants.PAYEDAMT, bindCellMap, true);
			}
//			else{
//				if ((temp.compareTo(FDCHelper.ZERO)) == 0)
//					table.getCell(2, 7).setValue(null);
//				else
//					table.getCell(2, 7).setValue(temp);
//			}
			
			
		}
	
		
		
		//���ڷ���(ԭ��)
		
		str = PaymentBillClientUtils.getRes("accThis")+"(ԭ��)";
		table.getCell(3, 4).setValue(str);
		
		// ���ڷ���
		str = PaymentBillClientUtils.getRes("accThis")+"(����)";
		table.getCell(3, 5).setValue(str);
		
		if(isFormContractQuery){
			
			table.getColumn(2).getStyleAttributes().setHided(true);
			table.getColumn(3).getStyleAttributes().setHided(true);
			table.getColumn(4).getStyleAttributes().setHided(true);
			table.getColumn(5).getStyleAttributes().setHided(true);
			
		}
		
		/*
		 * // ����ƻ� str = PaymentBillClientUtils.getRes("payPlan"); if
		 * (editData.getPaymentPlan() != null) { if
		 * ((editData.getPaymentPlan().compareTo(FDCHelper.ZERO)) == 0)
		 * table.getCell(11, 5).setValue(null); else table.getCell(11,
		 * 5).setValue(this.editData.getPaymentPlan()); }
		 * PaymentBillClientUtils.bindCell(table, 11, 4, str,
		 * PaymentBillContants.PAYMENTPLAN, bindCellMap, true);
		 */
		// ������ȣ�
		str = PaymentBillClientUtils.getRes("imageShedule");
		if (editData.getImageSchedule() != null) {
			if ((editData.getImageSchedule().compareTo(FDCHelper.ZERO)) == 0)
				table.getCell(13, 6).setValue(null);
			else
				table.getCell(13, 6).setValue(this.editData.getImageSchedule());
		}
		PaymentBillClientUtils.bindCell(table, 13, 4, str,PaymentBillContants.IMAGESCHEDULE, bindCellMap, true);
		// ������
		// �����ۼ�����
		str = PaymentBillClientUtils.getRes("reqAllThis");
		table.getCell(3, 6).setValue(str);
		// ������
		// �����ۼ�ʵ��
		str = PaymentBillClientUtils.getRes("payAllThis");
		table.getCell(3, 7).setValue(str);

		// �������ݰ󶨽��ȿ�׹���,ʵ����
		// ���ȿ���,
		row = table.getRow(4);
		cell = row.getCell(2);// �����ۼ�ʵ��
		if (editData.getLstPrjAllPaidAmt() != null) {
			if ((editData.getLstPrjAllPaidAmt().compareTo(FDCHelper.ZERO)) == 0)
				table.getCell(4, 2).setValue(null);
			else
				table.getCell(4, 2).setValue(
						this.editData.getLstPrjAllPaidAmt());
		}
		PaymentBillClientUtils.bindCell(cell,PaymentBillContants.LSTPRJALLPAIDAMT, bindCellMap, true);
		// PaymentBillClientUtils.bindCell(cell,"contractBill.prjPriceInConPaid",
		// bindCellMap);
		cell = row.getCell(3);// �����ۼ�����
		PaymentBillClientUtils.bindCell(cell,PaymentBillContants.LSTPRJALLREQAMT, bindCellMap, true);
		cell = row.getCell(4);// ������(ԭ��)
		cell.getStyleAttributes().setLocked(!isBaseCurrency);
		if (editData.getAddProjectAmt() != null) {
			if ((editData.getAddProjectAmt().compareTo(FDCHelper.ZERO)) == 0)
				table.getCell(4, 4).setValue(null);
			else
				table.getCell(4, 4).setValue(editData.getAddProjectAmt());
		}
		PaymentBillClientUtils.bindCell(cell,PaymentBillContants.PROJECTPRICEINCONTRACTORI, bindCellMap, true);
		cell = row.getCell(5);// ������
//		cell.getStyleAttributes().setLocked(!isBaseCurrency);
		if (editData.getProjectPriceInContract() != null) {
			if ((editData.getProjectPriceInContract().compareTo(FDCHelper.ZERO)) == 0)
				table.getCell(4, 5).setValue(null);
			else
				table.getCell(4, 5).setValue(
						this.editData.getProjectPriceInContract());
		}
		PaymentBillClientUtils.bindCell(cell,PaymentBillContants.PROJECTPRICEINCONTRACT, bindCellMap, true);
		cell = row.getCell(6);// �ۼ�����
		PaymentBillClientUtils.bindCell(cell, PaymentBillContants.PRJALLREQAMT,
				bindCellMap, true);
		
		//Ԥ����
		row = table.getRow(5);
		cell = row.getCell(2);// Ԥ�������������ۼ�ʵ��
		if (editData.getPrjPayEntry() != null && editData.getPrjPayEntry().getLstAdvanceAllPaid()!=null) {
			if (FDCHelper.ZERO.compareTo(editData.getPrjPayEntry().getLstAdvanceAllPaid()) == 0)
				table.getCell(5, 2).setValue(null);
			else
				table.getCell(5, 2).setValue(
						editData.getPrjPayEntry().getLstAdvanceAllPaid());
		}
		PaymentBillClientUtils.bindCell(cell,
				PaymentBillContants.LSTADVANCEALLPAID, bindCellMap, true);
		cell = row.getCell(3);// Ԥ������������ۼ�����
		if (editData.getPrjPayEntry() != null && editData.getPrjPayEntry().getLstAdvanceAllReq()!=null) {
			if (FDCHelper.ZERO.compareTo(editData.getPrjPayEntry().getLstAdvanceAllReq()) == 0)
				table.getCell(5, 3).setValue(null);
			else
				table.getCell(5, 3).setValue(
						editData.getPrjPayEntry().getLstAdvanceAllReq());
		}
		PaymentBillClientUtils.bindCell(cell,
				PaymentBillContants.LSTADDPRJALLREQAMT, bindCellMap, true);
		if (editData.getPrjPayEntry() != null && editData.getPrjPayEntry().getAdvance()!=null) {
			if (FDCHelper.ZERO.compareTo(editData.getPrjPayEntry().getAdvance()) == 0)
				table.getCell(5, 4).setValue(null);
			else
				table.getCell(5, 4).setValue(
						editData.getPrjPayEntry().getAdvance());
		}
		cell = row.getCell(4);// �����ԭ�ң�
		PaymentBillClientUtils.bindCell(cell,
				PaymentBillContants.ADVANCE, bindCellMap, true);
		cell = row.getCell(5);// ������(����)
		if (editData.getPrjPayEntry()!= null &&  editData.getPrjPayEntry().getLocAdvance()!=null) {
			if (FDCHelper.ZERO.compareTo(editData.getPrjPayEntry().getLocAdvance()) == 0)
				table.getCell(5, 5).setValue(null);
			else
				table.getCell(5, 5).setValue(editData.getPrjPayEntry().getLocAdvance());
		}
		cell = row.getCell(6);// Ԥ��������������ۼ�����
		PaymentBillClientUtils.bindCell(cell,
				PaymentBillContants.ADVANCEALLREQ, bindCellMap, true);
		
		row = table.getRow(6);
		cell = row.getCell(2);// ��ͬ�����ӹ��̿��ۼ�ʵ��
		if (editData.getLstAddPrjAllPaidAmt() != null) {
			if ((editData.getLstAddPrjAllPaidAmt().compareTo(FDCHelper.ZERO)) == 0)
				table.getCell(6, 2).setValue(null);
			else
				table.getCell(6, 2).setValue(
						this.editData.getLstAddPrjAllPaidAmt());
		}
		PaymentBillClientUtils.bindCell(cell,
				PaymentBillContants.LSTADDPRJALLPAIDAMT, bindCellMap, true);
		// PaymentBillClientUtils.bindCell(cell,"contractBill.addPrjAmtPaid",
		// bindCellMap);
		cell = row.getCell(3);// �����ۼ�����
		PaymentBillClientUtils.bindCell(cell,
				PaymentBillContants.LSTADDPRJALLREQAMT, bindCellMap, true);
		cell = row.getCell(4);// �����ԭ�ң�
		PaymentBillClientUtils.bindCell(cell,
				PaymentBillContants.ADDPROJECTAMTORI, bindCellMap, true);
		
		cell = row.getCell(6);// ������(����)
		cell.getStyleAttributes().setLocked(!isBaseCurrency);
		if (editData.getAddProjectAmt() != null) {
			if ((editData.getAddProjectAmt().compareTo(FDCHelper.ZERO)) == 0)
				table.getCell(6, 5).setValue(null);
			else
				table.getCell(6, 5).setValue(this.editData.getAddProjectAmt());
		}
//		PaymentBillClientUtils.bindCell(cell,
//				PaymentBillContants.ADDPROJECTAMT, bindCellMap, true);
		
		cell = row.getCell(6);// �ۼ�����
		PaymentBillClientUtils.bindCell(cell,
				PaymentBillContants.ADDPRJALLREQAMT, bindCellMap, true);
		
		// �׹���
		row = table.getRow(9);
		cell = row.getCell(2);// �ۼ�ʵ��
		PaymentBillClientUtils.bindCell(cell,
				PaymentBillContants.LSTAMATLALLPAIDAMT, bindCellMap, true);
		cell = row.getCell(3);// �����ۼ�����
		PaymentBillClientUtils.bindCell(cell,
				PaymentBillContants.LSTAMATLALLREQAMT, bindCellMap, true);
		cell = row.getCell(4);// Ӧ�ۼ׹����ڷ�����ԭ�ң�
		PaymentBillClientUtils.bindCell(cell,
				PaymentBillContants.PAYPARTAMATLAMTORI, bindCellMap, true);
		cell = row.getCell(5);//Ӧ�ۼ׹����ڷ��������ң�
//		cell.getStyleAttributes().setLocked(!isBaseCurrency);
		// table.getCell(7,4).setValue(this.editData.getPayPartAMatlAmt());
		PaymentBillClientUtils.bindCell(cell,
				PaymentBillContants.PAYPARTAMATLAMT, bindCellMap, true);
		cell = row.getCell(6);
		PaymentBillClientUtils.bindCell(cell,
				PaymentBillContants.PAYPARTAMATLALLREQAMT, bindCellMap, true);
		
		// ʵ����(ԭ��)
		row = table.getRow(10);
		cell = row.getCell(4);
		PaymentBillClientUtils.bindCell(cell, PaymentBillContants.CURPAID,bindCellMap, true);
		cell = row.getCell(5);               //ʵ����(����)
		PaymentBillClientUtils.bindCell(cell, PaymentBillContants.CURPAIDLOCAL,
				bindCellMap, true);
		// paytimes
		if(bindCellMap.get(PaymentBillContants.PAYTIMES) != null){
			StyleAttributes sa = ((ICell) bindCellMap.get(PaymentBillContants.PAYTIMES)).getStyleAttributes();
			sa.setNumberFormat("###,##0");
			sa.setLocked(!isBaseCurrency);
			// �������
			sa = ((ICell) bindCellMap.get(PaymentBillContants.IMAGESCHEDULE))
					.getStyleAttributes();
			sa.setLocked(!isBaseCurrency);

		}
		
		// ��ʽ���������
		
		// �ںϱ��
		KDTMergeManager mm = table.getMergeManager();
		// �ں�ǰ����
		mm.mergeBlock(0, 1, 0, 3, KDTMergeManager.SPECIFY_MERGE);
		mm.mergeBlock(1, 1, 1, 3, KDTMergeManager.SPECIFY_MERGE);
		//add by warship at 2010\05\09 ����ͬ�����������
		table.getCell(1, 1).getStyleAttributes().setHorizontalAlign(HorizontalAlignment.LEFT);
		mm.mergeBlock(0, 4, 0, 5, KDTMergeManager.SPECIFY_MERGE);
		mm.mergeBlock(1, 4, 1, 5, KDTMergeManager.SPECIFY_MERGE);
		mm.mergeBlock(2, 4, 2, 5, KDTMergeManager.SPECIFY_MERGE);
		if(!isFormContractQuery){
			mm.mergeBlock(0, 6, 0, 7, KDTMergeManager.SPECIFY_MERGE);
			mm.mergeBlock(1, 6, 1, 7, KDTMergeManager.SPECIFY_MERGE);
			mm.mergeBlock(2, 6, 2, 7, KDTMergeManager.SPECIFY_MERGE);	
		}else{
			table.getCell(0,6).setValue("��ͬ���");
			table.getCell(1,6).setValue("�������");
			table.getCell(2,6).setValue("������");
		}
		
//		
		
		// �������
		// mm.mergeBlock(3, 1, 3, 2, KDTMergeManager.SPECIFY_MERGE);
		// ���ȿ���
		mm.mergeBlock(4, 0, 8, 0, KDTMergeManager.SPECIFY_MERGE);
		// mm.mergeBlock(4, 1, 4, 2, KDTMergeManager.SPECIFY_MERGE);
		// mm.mergeBlock(5, 1, 5, 2, KDTMergeManager.SPECIFY_MERGE);
		// mm.mergeBlock(6, 1, 6, 2, KDTMergeManager.SPECIFY_MERGE);
		if(isFormContractQuery){
//			mm.mergeBlock(3, 0, 3, 5);
		}
		// Ӧ�ۼ׹��Ŀ�
		mm.mergeBlock(9, 0, 9, 1, KDTMergeManager.SPECIFY_MERGE);
		// ʵ����
		mm.mergeBlock(10, 0, 10, 1, KDTMergeManager.SPECIFY_MERGE);
		// ���
		mm.mergeBlock(11, 0, 11, 6, KDTMergeManager.SPECIFY_MERGE);
		// �������
		mm.mergeBlock(12, 5, 12, 7, KDTMergeManager.SPECIFY_MERGE);
		mm.mergeBlock(13, 5, 13, 7, KDTMergeManager.SPECIFY_MERGE);
		mm.mergeBlock(14, 5, 14, 7, KDTMergeManager.SPECIFY_MERGE);
		mm = null;
		
		
		if(isFormContractQuery){
			table.getRow(12).getStyleAttributes().setHided(true);
			table.getRow(13).getStyleAttributes().setHided(true);
			table.getRow(14).getStyleAttributes().setHided(true);
		}
	}

	/*
	 * �趨���ĸ�ʽ
	 * 
	 */
	public static KDTDefaultCellEditor getCellNumberEdit() {
		KDFormattedTextField kdc = new KDFormattedTextField();
		kdc.setDataType(KDFormattedTextField.BIGDECIMAL_TYPE);
		kdc.setPrecision(2);
		kdc.setMinimumValue(FDCHelper.MIN_VALUE);
		kdc.setMaximumValue(FDCHelper.MAX_VALUE);
		kdc.setHorizontalAlignment(JTextField.RIGHT);
		kdc.setSupportedEmpty(true);
		kdc.setVisible(true);
		kdc.setEnabled(true);
		// kdc.setRequired(false);
		KDTDefaultCellEditor editor = new KDTDefaultCellEditor(kdc);
		return editor;
	}

	/**
	 * ��ʼ����¼�Ķ�̬����,��Ӧ�ۿ���, �ۿ�����������Կۿ�����뵥������(DeductBillOfPayReq)
	 * ���ݸ������뵥������й��ˡ���һ�ε��ݹ���ʱ�ۿ�ȫ��Я����֮�������������ۿ���
	 */
	private int initDynamicTable(KDTable table) {
		// HashMap map=new HashMap();
		// �ڵ�9�в���Ӧ�ۿ����¼,��������ʱ��ѭ��ʵ��
		int base = 9;// ����Ļ���
		int rows = 0;
		KDTMergeManager mm = table.getMergeManager();
		IRow row;
		String contractId = editData.getContractBillId();
		if (contractId == null || PayReqUtils.isConWithoutTxt(contractId)) {
			return base;
		}
		if (editData.getFdcPayReqID() != null) {
			// String payid = null;
			int times = 0;
			int timestemp = 0;
			// �Ƿ��Ѿ��й��������
			boolean had = false; 
			PayRequestBillEntryCollection payReqEntry = (PayRequestBillEntryCollection)initData.get("PayRequestBillCollection_hasPaid");
			if (payReqEntry.iterator().hasNext()) {
				PayRequestBillEntryInfo info = (PayRequestBillEntryInfo) (payReqEntry
						.iterator().next());
				if (info.getPayTimes() > 1)
					had = true;
				else {
					times = payReqEntry.size();
					if (times == 1) {
						had = false;
					} else {
						PayRequestBillEntryCollection payReqEntrytemp =  (PayRequestBillEntryCollection)initData.get("PayRequestBillCollection_c5");
						timestemp = payReqEntrytemp.size();
						if (timestemp == 0)
							had = false;
						else
							had = true;
					}
				}
			} else {
				PayRequestBillEntryCollection payReqEntrytemp =  (PayRequestBillEntryCollection)initData.get("PayRequestBillCollection_c5");
				timestemp = payReqEntrytemp.size();
				if (timestemp == 0)
					had = false;
				else
					had = true;
			}

			// ����
			GuerdonOfPayReqBillInfo geInfo = null;
			int count = 0;
			BigDecimal total = FDCHelper.ZERO;
			BigDecimal totalOri = FDCHelper.ZERO;
			
			GuerdonOfPayReqBillCollection c =  (GuerdonOfPayReqBillCollection)initData.get("GuerdonOfPayReqBillCollection");
			count = c.size();
			for (int i = 0; i < count; i++) {
				geInfo = c.get(i);
				if (geInfo.getAmount() != null)
					total = total.add(geInfo.getAmount());
				if(geInfo.getOriginalAmount() != null)
					totalOri = totalOri.add(geInfo.getOriginalAmount());
			}
			if (had == false) {
				if ((total.compareTo(FDCHelper.ZERO)) == 0)
					table.getCell(7, 5).setValue(null);
				else
					table.getCell(7, 5).setValue(total);
				if((totalOri.compareTo(FDCHelper.ZERO))==0)
					table.getCell(7, 4).setValue(null);
				else
					table.getCell(7, 4).setValue(totalOri);
			}
			
			// ��ͬ������Ľ���
			BigDecimal totalReq = FDCHelper.ZERO;
			GuerdonOfPayReqBillCollection deGePay = (GuerdonOfPayReqBillCollection)initData.get("GuerdonOfPayReqBillCollection_deGePay");
			count = deGePay.size();
			for (int temp = 0; temp < count; temp++) {
				geInfo = deGePay.get(temp);
				if (geInfo.getAmount() != null)
					totalReq = totalReq.add(geInfo.getAmount());
			}			
			if ((totalReq.compareTo(FDCHelper.ZERO)) == 0)
				table.getCell(7, 3).setValue(null);
			else
				table.getCell(7, 3).setValue(totalReq);

			// ��ͬ���Ѹ��Ľ���
			BigDecimal totalPay = FDCHelper.ZERO;
			GuerdonOfPayReqBillCollection dePay =(GuerdonOfPayReqBillCollection)initData.get("GuerdonOfPayReqBillCollection_dePay");
			count = dePay.size();
			for (int temp = 0; temp < count; temp++) {
				geInfo = dePay.get(temp);
				if (geInfo.getAmount() != null)
					totalPay = totalPay.add(geInfo.getAmount());
			}
			
			//��ǰ�������뵥�Ѹ��Ľ���
			GuerdonOfPayReqBillCollection getemp = (GuerdonOfPayReqBillCollection)initData.get("GuerdonOfPayReqBillCollection_getemp");
			count = getemp.size();
			for (int temp = 0; temp < count; temp++) {
				geInfo = getemp.get(temp);
				if (geInfo.getAmount() != null)
					totalPay = totalPay.subtract(geInfo.getAmount());
			}
			
			if ((totalPay.compareTo(FDCHelper.ZERO)) == 0)
				table.getCell(7, 2).setValue(null);
			else
				table.getCell(7, 2).setValue(totalPay);

			/*
			 * ��ʾΥԼ�� add by sxhong 2007/09/28
			 */
			BigDecimal amount = FDCHelper.ZERO;
			BigDecimal amountOri = FDCHelper.ZERO;
			BigDecimal lstPaidAmt = FDCHelper.ZERO;
			BigDecimal lstReqAmt = FDCHelper.ZERO;
			
			CompensationOfPayReqBillCollection comCol =  (CompensationOfPayReqBillCollection)initData.get("CompensationOfPayReqBillCollection");
			for (int i = 0; i < comCol.size(); i++) {
				CompensationOfPayReqBillInfo info = comCol.get(i);
				if (info.getAmount() != null) {
					if (info.getPayRequestBill().getId().toString().equals(
							editData.getFdcPayReqID())) {
						if (info.isHasPaid()
								&& info.getPaymentBill() != null
								&& info.getPaymentBill().getId() != null
								&& !(info.getPaymentBill().getId()
										.equals(editData.getId()))) {
							lstPaidAmt = info.getAmount().add(lstPaidAmt);
						}
						amount = amount.add(FDCHelper.toBigDecimal(info
								.getAmount()));
						amountOri = amountOri.add(FDCHelper.toBigDecimal(info
								.getOriginalAmount()));
					} else {
						if (info.isHasPaid()) {
							lstPaidAmt = info.getAmount().add(lstPaidAmt);
						}
					}
					// ��������ۼ�����Ҫ������������� by hpw 2009-07-18
					lstReqAmt = info.getAmount().add(lstReqAmt);
				}
			}				
				
			if (amount.compareTo(FDCHelper.ZERO) == 0) {
				amount = null;
			}
			if (amountOri.compareTo(FDCHelper.ZERO) == 0) {
				amountOri = null;
			}
			if (lstPaidAmt.compareTo(FDCHelper.ZERO) == 0) {
				lstPaidAmt = null;
			}
			if (lstReqAmt.compareTo(FDCHelper.ZERO) == 0) {
				lstReqAmt = null;
			}
			table.getCell(8, 2).setValue(lstPaidAmt);
			table.getCell(8, 3).setValue(lstReqAmt);

			if (had == false) {
				table.getCell(8, 5).setValue(amount);
				table.getCell(8, 4).setValue(amountOri);
			}
//			CompensationOfPayReqBillCollection comPay =  (CompensationOfPayReqBillCollection)initData.get("CompensationOfPayReqBillCollection_comPay");
//			for (int i = 0; i < comCol.size(); i++) {
//				CompensationOfPayReqBillInfo info = comCol.get(i);
//				if (info.getAmount() != null) {
//					if (info.getPayRequestBill().getId().toString()
//								.equals(editData.getFdcPayReqID())) {
//						amount = FDCHelper.subtract(amount,info.getAmount());
//						amountOri = FDCHelper.subtract(amountoRri,info.getOriginalAmount());
//					} else {
//						if (info.isHasPaid()) {
//							lstPaidAmt = info.getAmount().add(
//										lstPaidAmt);
//						}
//								// ��������ۼ�����Ҫ�������������
//					}
//					lstReqAmt = info.getAmount().add(lstReqAmt);
//				}
//			}	

			// if((payid != null)&&(had == false)){
			// ��DeductOfPayReqBill��ȡ������
			DeductOfPayReqBillInfo info = null;
			// �ۿ�
			DeductOfPayReqBillCollection deduCol = (DeductOfPayReqBillCollection)initData.get("DeductOfPayReqBillCollection");
			rows = deduCol.size();
			for (int i = 0; i < rows; i++) {
				info = deduCol.get(i);
				row = table.addRow(base + i);
				row.getCell(1).setValue(info.getDeductType().getName());
				row.getCell(1).getStyleAttributes().setNumberFormat("@");
				if (info.getAllPaidAmt() != null)
					row.getCell(2).setValue(info.getAllPaidAmt());
				if (info.getAllReqAmt() != null)
					row.getCell(3).setValue(info.getAllReqAmt());
				if ((this.editData.getFdcPayReqID() != null)
							&& (had == false)) {
					if (info.getAmount() != null) {
						if ((info.getAmount().compareTo(FDCHelper.ZERO)) == 0)
							row.getCell(5).setValue(null);
						else
							row.getCell(5).setValue(info.getAmount());
					}
					if (info.getOriginalAmount() != null) {
						if ((info.getOriginalAmount().compareTo(FDCHelper.ZERO)) == 0)
							row.getCell(4).setValue(null);
						else
							row.getCell(4).setValue(info.getOriginalAmount());
					}
				}
				
				// ��ͬ������Ŀۿ���
				DeductOfPayReqBillInfo infoPay = null;
				BigDecimal deAllReq = FDCHelper.ZERO;
				int rowsReq = 0;

				DeductOfPayReqBillCollection deduPay = (DeductOfPayReqBillCollection)info.get("DeductOfPayReqBillCollection_deduPay");
				rowsReq = deduPay.size();
				for (int temp = 0; temp < rowsReq; temp++) {
					infoPay = deduPay.get(temp);
					if (infoPay.getAmount() != null)
						deAllReq = deAllReq.add(infoPay.getAmount());
				}

				if ((deAllReq.compareTo(FDCHelper.ZERO)) == 0)
					row.getCell(3).setValue(null);
				else
					row.getCell(3).setValue(deAllReq);

					// ��ͬ���Ѹ��Ŀۿ���

				DeductOfPayReqBillInfo infoPaid = null;
				BigDecimal deAllPaid = FDCHelper.ZERO;
				int rowsPaid = 0;
				DeductOfPayReqBillCollection deduPay2 = (DeductOfPayReqBillCollection)info.get("DeductOfPayReqBillCollection_deduPay2");;
				rowsPaid = deduPay2.size();
				for (int temp = 0; temp < rowsPaid; temp++) {
					infoPaid = deduPay2.get(temp);
					if (infoPaid.getAmount() != null)
						deAllPaid = deAllPaid.add(infoPaid.getAmount());
				}

				//
				DeductOfPayReqBillCollection ctemp = (DeductOfPayReqBillCollection)info.get("DeductOfPayReqBillCollection_dctemp");
				Iterator itemp = ctemp.iterator();
				if (itemp.hasNext()) {
					DeductOfPayReqBillInfo infotemp = (DeductOfPayReqBillInfo) itemp
								.next();
					if (infotemp != null && infotemp.getAmount() != null) {
							deAllPaid = deAllPaid
								.subtract(infotemp.getAmount());
					}
				}

				if ((deAllPaid.compareTo(FDCHelper.ZERO)) == 0)
					row.getCell(2).setValue(null);
				else
					row.getCell(2).setValue(deAllPaid);
				row.getCell(6).setExpressions("=D" + (base + i + 1));
				if (((toBigDecimal(row.getCell(6).getValue()))
							.compareTo(FDCHelper.ZERO)) == 0)
					row.getCell(6).setValue(null);
					// row.getCell(5).setExpressions("=D"+(base+i+1)+"+E"+(base+i+1));
				if ((row.getCell(2).getValue() == null)
							&& (row.getCell(4).getValue() == null)) {
					row.getCell(7).setValue(null);
				} else
					row.getCell(7).setExpressions(
								"=C" + (base + i + 1) + "+F" + (base + i + 1));
			}
			

			// ���һ��С��
			int lastRowIdx = base + rows;
			row = table.addRow(lastRowIdx);
			row.getCell(1).setValue(PaymentBillClientUtils.getRes("add"));
			if(rows>0){
				if ((this.editData.getFdcPayReqID() != null)) {
					/*
					 * С�Ƽ���
					 */
					StringBuffer exp;
					for (char ch = 'C'; ch <= 'H'; ch++) {
						exp = new StringBuffer("=sum(");
						exp.append(ch).append(base + 1).append(':');
						exp.append(ch).append(lastRowIdx);
						exp.append(')');
						row.getCell(ch - 'A').setExpressions(exp.toString());
						if (((toBigDecimal(row.getCell(ch - 'A').getValue()))
								.compareTo(FDCHelper.ZERO)) == 0)
							row.getCell(ch - 'A').setValue(null);
					}
				} else {
					row.getCell(2).setValue(null);
					row.getCell(3).setValue(null);
					row.getCell(4).setValue(null);
					row.getCell(5).setValue(null);
					row.getCell(6).setValue(null);
					row.getCell(7).setValue(null);
				}
				row.getCell(6).setExpressions("=D" + (lastRowIdx + 1));
				table.getCell(base, 0).setValue(
						PaymentBillClientUtils.getRes("amtDeduct"));
				mm.mergeBlock(base, 0, base + rows, 0,
						KDTMergeManager.SPECIFY_MERGE);
			}
			return lastRowIdx;
		} else {
			int lastRowIdx = base + rows;
			row = table.addRow(lastRowIdx);
			row.getCell(1).setValue(PaymentBillClientUtils.getRes("add"));
			row.getCell(2).setValue(null);
			row.getCell(3).setValue(null);
			row.getCell(4).setValue(null);
			row.getCell(5).setValue(null);
			row.getCell(6).setValue(null);
			row.getCell(7).setValue(null);
			table.getCell(base, 0).setValue(
					PaymentBillClientUtils.getRes("amtDeduct"));
			mm.mergeBlock(base, 0, base + rows, 0,
					KDTMergeManager.SPECIFY_MERGE);
			return lastRowIdx;
			// return base;
		}

	}

	// ���رո������뵥
	public void actionCancelClosePayReq_actionPerformed(ActionEvent e)
			throws Exception {
		if (this.editData.getFdcPayReqNumber() != null) {
			EntityViewInfo view = new EntityViewInfo();
			FilterInfo filter = new FilterInfo();
			FilterItemCollection item = filter.getFilterItems();
			// item.add(new FilterItemInfo("number",
			// this.editData.getFdcPayReqNumber()));
			item.add(new FilterItemInfo("id", this.editData.getFdcPayReqID()));
			filter.getFilterItems().add(
					new FilterItemInfo("hasClosed", Boolean.FALSE));
			view.setFilter(filter);
			try {
				PayRequestBillCollection c = PayRequestBillFactory
						.getRemoteInstance().getPayRequestBillCollection(view);
				Iterator iter = c.iterator();
				if (iter.hasNext()) {
					MsgBox.showWarning(this, PaymentBillClientUtils
							.getRes("hadUnClose"));
					SysUtil.abort();
				}
			} catch (BOSException exe) {
				super.handUIException(exe);// OprtState.EDIT);
			}
			IUIWindow testUI = null;
			int isYes = MsgBox.showConfirm2(this, PaymentBillClientUtils
					.getRes("cancleClose"));
			if (MsgBox.isYes(isYes)) {
				EntityViewInfo vi = new EntityViewInfo();
				FilterInfo fi = new FilterInfo();
				FilterItemCollection it = fi.getFilterItems();
				it
						.add(new FilterItemInfo("id", this.editData
								.getFdcPayReqID()));
				vi.setFilter(fi);
				try {
					PayRequestBillCollection c = PayRequestBillFactory
							.getRemoteInstance()
							.getPayRequestBillCollection(vi);
					Iterator iter = c.iterator();
					if (iter.hasNext()) {
						PayRequestBillInfo info = (PayRequestBillInfo) iter
								.next();
						UIContext uiContext = new UIContext(this);
						uiContext.put(UIContext.ID, info.getId());
						testUI = UIFactory
								.createUIFactory(UIFactoryName.NEWTAB)
								.create(
										"com.kingdee.eas.fdc.contract.client.PayRequestBillEditUI",
										uiContext, null, "CLOSE");
						testUI.show();
					}
				} catch (BOSException ebos) {
					super.handUIException(ebos);
				}
			}
		}
		super.actionCancelClosePayReq_actionPerformed(e);
	}

	// �رո������뵥
	public void actionClosePayReq_actionPerformed(ActionEvent e)
			throws Exception {
		if (this.editData.getFdcPayReqID() != null) {
			EntityViewInfo view = new EntityViewInfo();
			FilterInfo filter = new FilterInfo();
			FilterItemCollection item = filter.getFilterItems();
			item.add(new FilterItemInfo("id", this.editData.getFdcPayReqID()));
			filter.getFilterItems().add(
					new FilterItemInfo("hasClosed", Boolean.TRUE));
			view.setFilter(filter);
			try {
				PayRequestBillCollection c = PayRequestBillFactory
						.getRemoteInstance().getPayRequestBillCollection(view);
				Iterator iter = c.iterator();
				if (iter.hasNext()) {
					MsgBox.showWarning(this, PaymentBillClientUtils
							.getRes("hadClose"));
					SysUtil.abort();
				}
			} catch (BOSException exe) {
				super.handUIException(exe);// OprtState.EDIT);
			}
			IUIWindow testUI = null;
			int isYes = MsgBox.showConfirm2(this, PaymentBillClientUtils
					.getRes("close"));
			if (MsgBox.isYes(isYes)) {
				EntityViewInfo vi = new EntityViewInfo();
				FilterInfo fi = new FilterInfo();
				FilterItemCollection it = fi.getFilterItems();
				it
						.add(new FilterItemInfo("id", this.editData
								.getFdcPayReqID()));
				vi.setFilter(fi);
				try {
					PayRequestBillCollection c = PayRequestBillFactory
							.getRemoteInstance()
							.getPayRequestBillCollection(vi);
					Iterator iter = c.iterator();
					if (iter.hasNext()) {
						PayRequestBillInfo info = (PayRequestBillInfo) iter
								.next();
						UIContext uiContext = new UIContext(this);
						uiContext.put(UIContext.ID, info.getId());
						try {
							testUI = UIFactory
									.createUIFactory(UIFactoryName.NEWTAB)
									.create(
											"com.kingdee.eas.fdc.contract.client.PayRequestBillEditUI",
											uiContext, null, "CLOSE");
							testUI.show();
						} catch (BOSException exe) {
							super.handUIException(exe);// OprtState.EDIT);
						}
					}
				} catch (BOSException ebos) {
					super.handUIException(ebos);
				}
			}
		}
		super.actionClosePayReq_actionPerformed(e);
	}

	public void actionPay_actionPerformed(ActionEvent e) throws Exception {
		int isYes = MsgBox.showConfirm2(this, PaymentBillClientUtils
				.getRes("pay"));
		if (MsgBox.isYes(isYes)) {
			Set idSet = new HashSet();
			idSet.add(editData.getId());
			// 522�տ�ʱ���㷽ʽ����Ϊ��
			if (editData.getSettlementType() == null)
				throw new RecPayException(
						RecPayException.SETTLEMENTTYPE_CANNOT_BE_NULL);
			// 522����ʱ�����Ŀ����Ϊ��
			if (editData.getPayerAccount() == null)
				throw new RecPayException(
						RecPayException.PAYERACCOUNT_CANNOT_BE_NULL);
			PaymentBillFactory.getRemoteInstance().pay(idSet);
			syncDataFromDB();
			// actionPay.setEnabled(false);
		}
		super.actionPay_actionPerformed(e);
	}

	public void actionRefresh_actionPerformed(ActionEvent e) throws Exception {
		syncDataFromDB();
		setAmount();
		super.actionRefresh_actionPerformed(e);
	}

	protected void syncDataFromDB() throws Exception {
		// �ɴ��ݹ�����ID��ȡֵ����
		if (editData.getId() == null) {
			String s = EASResource.getString(FrameWorkClientUtils.strResource
					+ "Msg_IDIsNull");
			MsgBox.showError(s);
			SysUtil.abort();
		}
		IObjectPK pk = new ObjectUuidPK(BOSUuid.read(editData.getId()
				.toString()));
		setDataObject(getValue(pk));
		loadFields();
	}

	/**
	 * output actionSave_actionPerformed
	 */
	public void actionSave_actionPerformed(ActionEvent e) throws Exception {
		
		isMoving = true;
		if (txtNumber.isRequired() == true) {
			String txt = txtNumber.getText();
			if (txt == null || txt.trim().equals("")) {
				MsgBox.showError(this, PaymentBillClientUtils
						.getRes("noNumber"));
				SysUtil.abort();
			}
		}
		verifyBeforeSave();
		if (editData.getBillStatus() == null)
			editData.setBillStatus(BillStatusEnum.SAVE);
		PaymentBillClientUtils.getValueFromCell(editData, bindCellMap);
		if (getBOTPViewStatus() == 1) {
			getUIContext().put(BOTPViewS, new Integer(0));
		}
		
		if(PayReqUtils.isContractBill(editData.getContractBillId()) && isPartA && isPartAMaterialCon){
			paymentBillTableHelper.storePartAConList(editData);
		}
		
		FDCPaymentBillInfo fdcPayment = new FDCPaymentBillInfo();
		fdcPayment.setAllInvoiceAmt(txtAllInvoiceAmt.getBigDecimalValue());
		fdcPayment.setInvoiceAmt(txtInvoiceAmt.getBigDecimalValue());
		fdcPayment.setInvoiceNumber(txtInvoiceNumber.getStringValue());
		fdcPayment.setInvoiceDate((Date)pkInvoiceDate.getValue());
		
		super.actionSave_actionPerformed(e);
		
		// �����д��Ʊ����Ҫ�� by cassiel 2010-10-7
		handInvoiceAmt(fdcPayment);
		detachListeners();
		txtAllInvoiceAmt.setValue(fdcPayment.getAllInvoiceAmt());
		txtInvoiceAmt.setValue(fdcPayment.getInvoiceAmt());
		txtInvoiceNumber.setStringValue(fdcPayment.getInvoiceNumber());
		pkInvoiceDate.setValue(fdcPayment.getInvoiceDate());
		attachListeners();
		
		setOprtStateByBillStatus();
		this.storeFields();
		this.initOldData(this.editData);
	}

	protected void verifyBeforeSave() throws Exception {
		boolean isMoreThanReqAmt = false;
		if (this.editData.getFdcPayReqNumber() == null) {
			MsgBox.showWarning(this, PaymentBillClientUtils.getRes("must"));
			SysUtil.abort();
		}
		FDCClientVerifyHelper.verifyEmpty(this, prmtCurrency);
		FDCClientVerifyHelper.verifyEmpty(this, prmtPayBillType);
		/**
		 * ���ԭ�ҽ���뵥Ԫ���ڵķ�����ʵ������Ƿ�һ��
		 * ��Ϊ���ԭ�ҽ��(�Ǳ�λ���޸Ļ���ʱ����У�鲻׼by hpw)
		 */
		Object cell = bindCellMap.get(PaymentBillContants.CURPAID);
		if (cell instanceof ICell) {
			Object value = ((ICell) cell).getValue();
			if (value != null) {
				try {
					BigDecimal cellAmount = FDCHelper.toBigDecimal(value,4);//�ȱ�����λ�ٱ�����λ��֤��� by hpw 
/*					if (txtAmount.getBigDecimalValue() != null
							&& txtAmount.getBigDecimalValue().signum() < 0) {
						MsgBox.showError(this, "ԭ�ҽ��Ϊ�����ܱ��棡");
						SysUtil.abort();
						
					}*/
					// if(cellAmount.doubleValue()>0&&cellAmount.compareTo(txtAmount.getNumberValue())!=0){
					
					if(this.editData.getFdcPayReqID()!=null&&prmtCurrency.getValue()!=null){
						CurrencyInfo currency=(CurrencyInfo)prmtCurrency.getValue();
						SelectorItemCollection sel=new SelectorItemCollection();
						sel.add("currency.id");
						PayRequestBillInfo pay=PayRequestBillFactory.getRemoteInstance().getPayRequestBillInfo(new ObjectUuidPK(this.editData.getFdcPayReqID()),sel);
						
						if(pay.getCurrency().getId().toString().equals(currency.getId().toString())){
							if(this.isAdvance()){
								paymentBillTableHelper.checkAdvance(editData, bindCellMap);
								if ((FDCHelper.toBigDecimal(cellAmount,2)
												.compareTo(FDCHelper.toBigDecimal(txtAmount.getBigDecimalValue(),2)) != 0)){
									MsgBox.showWarning(this, PaymentBillClientUtils
											.getRes("unequle"));
									SysUtil.abort();
								}
							} else {
								/*if (cellAmount.doubleValue() < 0
										|| (cellAmount.doubleValue() > 0 && (FDCHelper.toBigDecimal(cellAmount,2)
												.compareTo(FDCHelper.toBigDecimal(txtAmount.getBigDecimalValue(),2)) != 0))) {
									MsgBox.showWarning(this, PaymentBillClientUtils
											.getRes("unequle"));
									SysUtil.abort();
								}*/
								if (txtAmount.getBigDecimalValue() == null
										|| (PayReqUtils.isContractBill(editData.getContractBillId())&&FDCHelper
												.toBigDecimal(cellAmount, 2)
												.compareTo(
														FDCHelper
																.toBigDecimal(
																		txtAmount
																				.getBigDecimalValue(),
																		2)) != 0)) {
									MsgBox.showWarning(this, PaymentBillClientUtils
											.getRes("unequle"));
									SysUtil.abort();
								}
							}
						}
					}
				} catch (NumberFormatException e1) {
					super.handUIException(new Throwable("��ʽ����!"
							+ e1.getMessage()));
				}
			}
		}
		// ���ԭ�ҽ�������
/*		if (((new BigDecimal(0)).compareTo(txtAmount.getNumberValue())) >= 0) {
			// if(((this.editData.getAmount()).compareTo(new
			// BigDecimal(0)))<=0){
			MsgBox.showWarning(this, PaymentBillClientUtils
					.getRes("moreThanZero"));
			SysUtil.abort();
		}*/
		/*
		 * �Ƚϸ�������������뵥���
		 */
		if (this.editData.getFdcPayReqNumber() != null&&prmtCurrency.getValue()!=null) {
			EntityViewInfo view = new EntityViewInfo();
			FilterInfo filter = new FilterInfo();
			FilterItemCollection items = filter.getFilterItems();
			// items.add(new FilterItemInfo("number",
			// this.editData.getFdcPayReqNumber()));
			items.add(new FilterItemInfo("id", this.editData.getFdcPayReqID()));
			view.setFilter(filter);
			try {
				PayRequestBillCollection c = PayRequestBillFactory
						.getRemoteInstance().getPayRequestBillCollection(view);
				Iterator iter = c.iterator();
				if (iter.hasNext()) {
					PayRequestBillInfo info = (PayRequestBillInfo) iter.next();
					//temp ���뵥�Ľ��
					BigDecimal temp = info.getOriginalAmount();
					//��ʱ��Ϊ����
//						temp = FDCHelper.toBigDecimal(info.getAmount(),4);
					//���Ҹ����뵥�Ѿ�������,(��������ǰ���)
					BigDecimal amou = FDCHelper.ZERO; //new BigDecimal(0);
					EntityViewInfo vi = new EntityViewInfo();
					FilterInfo fi = new FilterInfo();
					fi.getFilterItems().add(new FilterItemInfo("parent.id", info.getId().toString()));
					if(editData.getId()!=null){
						fi.getFilterItems().add(new FilterItemInfo("paymentBill.id", this.editData.getId().toString(),CompareType.NOTEQUALS));
					}					
					vi.setFilter(fi);
					try {
						PayRequestBillEntryCollection payReqEntry = 
							PayRequestBillEntryFactory.getRemoteInstance().getPayRequestBillEntryCollection(vi);
						Iterator itor=null;
						for (itor = payReqEntry.iterator(); itor.hasNext();) {
							PayRequestBillEntryInfo entry = (PayRequestBillEntryInfo) itor.next();
							FilterInfo paymentExist = new FilterInfo();
							paymentExist.getFilterItems().add(new FilterItemInfo("id",entry.getPaymentBill().getId().toString()));
							if (getBizInterface().exists(paymentExist)&&entry.getAmount() != null){
								amou = amou.add(entry.getAmount());
							}
						}
					} catch (BOSException e) {
						super.handUIException(e);
					}
					CurrencyInfo currency=(CurrencyInfo)prmtCurrency.getValue();
					if(info.getCurrency().getId().toString().equals(currency.getId().toString())){
						// �þ���ֵ���Ƚϣ���Ϊ�������ı���ͬ¼�븺����Added by Owen_wen 2011-05-12
						if ((temp.setScale(2, BigDecimal.ROUND_HALF_UP).abs().compareTo(amou.add(txtAmount.getBigDecimalValue()).abs().setScale(2,
								BigDecimal.ROUND_HALF_UP))) == 1) {
							//������С�����뵥���
							MsgBox.showInfo(this, PaymentBillClientUtils.getRes("lessthan"));
						} else if ((temp.setScale(2, BigDecimal.ROUND_HALF_UP).abs().compareTo(amou.add(txtAmount.getBigDecimalValue()).abs().setScale(2,
								BigDecimal.ROUND_HALF_UP))) == -1) {
							//������������뵥���
							isMoreThanReqAmt = true;
							MsgBox.showWarning(this, PaymentBillClientUtils.getRes("morethan"));
						}
					}
				}
			} catch (Exception ebos) {
				super.handUIException(ebos);
			} finally{
				//������������뵥���ʱ����ʾ�ɹ�,��Щ���
				if(isMoreThanReqAmt){
					SysUtil.abort();
				}
			}
		}
		/*
		 * �Ƚ�����ͬ�б��ܽ���Ƿ��븶����������
		 */
		if(PayReqUtils.isContractBill(editData.getContractBillId()) && isPartA && isPartAMaterialCon){
			paymentBillTableHelper.verifySave();
		}
	}

	/**
	 * output actionSubmit_actionPerformed
	 */
	public void actionSubmit_actionPerformed(ActionEvent e) throws Exception {
		if(this.getUIContext().get("ChequeReimBurse")!=null&&((Integer)this.getUIContext().get("ChequeReimBurse")).intValue()==1){
			storeFields();
			initOldData(editData);
			if(getEditUIMode(this).equals(UIFactoryName.MODEL)){
				getUIWindow().close();
			}
			UIContext uiContext = new UIContext(this.getUIContext().get(UIContext.OWNER));
			uiContext.put("PaymentBillForReimburse", editData);
			String chequeNumber=f7SettleNumber.getText();
			if(chequeNumber==null||chequeNumber.trim().equals("")){
				MsgBox.showInfo(this, "����Ų���Ϊ�գ�");
				SysUtil.abort();
			}else{
				String chequeID=null;
				Object oCheque=f7SettleNumber.getData();
				if(oCheque instanceof ChequeInfo){
					chequeID=((ChequeInfo)oCheque).getId().toString();
				}else{
					if(editData.getCheque()!=null)
						chequeID=editData.getCheque().getId().toString();
					else
						return;
				}
				/*
				FilterInfo filter=new FilterInfo();
				filter.getFilterItems().add(new FilterItemInfo("number",chequeNumber));
				EntityViewInfo evi=new EntityViewInfo();
				
				FilterInfo filter2 = new FilterInfo();
				filter2.getFilterItems().add(
						new FilterItemInfo("keepCompany.id", currentCompany.getId()
								.toString()));
				filter2.getFilterItems().add(
						new FilterItemInfo("company.id", currentCompany.getId()
								.toString()));
				filter2.getFilterItems().add(new FilterItemInfo("state", new Integer(ChequeStatusEnum.NEW_VALUE)));
				filter2.setMaskString("(#0 OR #1) AND #2");
				
				filter.mergeFilter(filter2, "AND");
				evi.setFilter(filter);
				ChequeCollection chequeColl=ChequeFactory.getRemoteInstance().getChequeCollection(evi);*/
				//if(chequeColl.size()>0){
					uiContext.put(UIContext.ID, chequeID);
					uiContext.put(UIModelDialogFactory.CANRESIZE, "true");
					IUIWindow uiWindow = null;
					// ����UI������ʾ
					uiWindow = UIFactory.createUIFactory(UIFactoryName.MODEL).create(ChequeEditUI.class.getName(),
							uiContext, null, OprtState.EDIT);
					//uiWindow.getUIObject().getUIContentPane().setSize(new Dimension(800,800));
					//uiWindow.getUIObject().getUIContentPane().setPreferredSize(new Dimension(800,800));
					uiWindow.show();
				/*}else{
					//Set idSet=new HashSet(1);
					//idSet.add(editData.getId().toString());
					///((IPaymentBill) getBizInterface()).pay(idSet);
					//PaymentBillFactory.getRemoteInstance().chequeReimburse(chequeNumber, editData.getId().toString());
					MsgBox.showInfo(this, "֧Ʊ�ѱ�ʹ�ã���ѡ������֧Ʊ��");
					SysUtil.abort();
					//this.getUIContext().put("ChequeReimBurse", new Integer(0));
				}*/
				//Component component = (Component) e.getSource();
				//FMClientHelper.showSuccessInfo(this, component);
				setDataObject(getValue(new ObjectUuidPK(editData.getId())));
				loadFields();
				setSave(true);
				setSaved(true);
			}

			return;
		}
		checkAmt();
		
		isMoving = true;

		//����ύʱ���Ƿ����ͬ���
		if(checkAllSplit){
			checkContractSplitState();			
		}
		if(checkPaymentAllSplit){
			String billId = editData.getId()==null?null:editData.getId().toString();
			Map param = new HashMap();
			param.put("isSimpleFinancial", Boolean.valueOf(isSimpleFinancial));
			param.put("isSimpleFinancialExtend", Boolean.valueOf(isSimpleFinancialExtend));
			param.put("isSimpleInvoice", Boolean.valueOf(isSimpleInvoice));
			param.put("isFinancial", Boolean.valueOf(isFinancial));
			param.put("isSeparate", Boolean.valueOf(isSeparate));
			
			SelectorItemCollection selector = new SelectorItemCollection();
			selector.add("localAmt");
			PaymentBillInfo info = (PaymentBillInfo)getBizInterface().getValue(new ObjectUuidPK(billId), selector);
			if(FDCHelper.toBigDecimal(info.getLocalAmt(),2).compareTo(FDCHelper.toBigDecimal(txtLocalAmt.getBigDecimalValue(),2))!=0){
				FDCMsgBox.showWarning("�˸����Ӧ�ĸ������뵥���ڶ�θ�����ύ�����²�֣�");
			}else{
				String msg = PaymentClientUtils.checkPaymentSplitState(billId,param);
				if(msg.length()!=0){
				FDCMsgBox.showWarning(this, msg);
				this.abort();
				}
			}
		}
		if (txtNumber.isRequired() == true) {
			String txt = txtNumber.getText();
			if (txt == null || txt.trim().equals("")) {
				MsgBox.showError(this, PaymentBillClientUtils
						.getRes("noNumber"));
				SysUtil.abort();
			}
		}
		this.setAmount();
		this.setProjectPriceInContract();
		if(this.isAdvance())
			this.setAdvance();
		if (editData.getBillStatus() == null)
			editData.setBillStatus(BillStatusEnum.SUBMIT);
		verifyBeforeSubmit();
		verifyBeforeSave();
		PaymentBillClientUtils.getValueFromCell(editData, bindCellMap);
		if (getBOTPViewStatus() == 1) {
			getUIContext().put(BOTPViewS, new Integer(0));
		}
		
		if(PayReqUtils.isContractBill(editData.getContractBillId()) && isPartA && isPartAMaterialCon){
			paymentBillTableHelper.storePartAConList(editData);
		}
		checkCanSubmit();
		
		FDCPaymentBillInfo fdcPayment = new FDCPaymentBillInfo();
		fdcPayment.setAllInvoiceAmt(txtAllInvoiceAmt.getBigDecimalValue());
		fdcPayment.setInvoiceAmt(txtInvoiceAmt.getBigDecimalValue());
		fdcPayment.setInvoiceNumber(txtInvoiceNumber.getStringValue());
		fdcPayment.setInvoiceDate((Date)pkInvoiceDate.getValue());
		
		super.actionSubmit_actionPerformed(e);
		
		// �����д��Ʊ����Ҫ�� by cassiel 2010-10-7
		handInvoiceAmt(fdcPayment);
		detachListeners();
		txtAllInvoiceAmt.setValue(fdcPayment.getAllInvoiceAmt());
		txtInvoiceAmt.setValue(fdcPayment.getInvoiceAmt());
		txtInvoiceNumber.setStringValue(fdcPayment.getInvoiceNumber());
		pkInvoiceDate.setValue(fdcPayment.getInvoiceDate());
		attachListeners();
		
		editData.setBillStatus(BillStatusEnum.SUBMIT);
		setOprtStateByBillStatus();
		this.storeFields();
		this.initOldData(this.editData);
	}
	/**
	 * �����д��Ʊ���뷢Ʊ����Ҫ�� by cassiel 2010-10-7 <p>
	 * 
	 * �޸����еĿ����߼����������뵥�ġ���Ʊ�š��͡���Ʊ���Զ���������������û��޸ķ�Ʊ�źͷ�Ʊ�����������󣬷�дԭ�������뵥�ġ���Ʊ�š��͡���Ʊ����<p>
	 * 
	 * �����ϼ����߼����Ƚϸ���ϵķ�Ʊ�š���Ʊ����Ƿ��븶�����뵥��һ�£����в�ͬ�����Ƿ�Ҫ�������ʾ
	 * 
	 * @modified by Owen_wen 2010-10-26
	 * 
	 */
	private void handInvoiceAmt(FDCPaymentBillInfo fdcPayment) throws EASBizException, BOSException {
		/*
		// ��Ʊ�Ų�ͬ
		String numDiff = "�����Ʊ���븶�����뵥��һ��, ϵͳ���Զ��޸ĸ������뵥��Ʊ�ţ��Ƿ������"; 

		// ��Ʊ��ͬ
		String amtDiff = "�����Ʊ����븶�����뵥��һ��,ϵͳ���Զ��޸ĸ������뵥��Ʊ�� �Ƿ������"; 
 		
		// ��Ʊ�źͷ�Ʊ����ͬ
		String num_amt_Diff = "����ķ�Ʊ�źͷ�Ʊ����븶�����뵥��һ�£�ϵͳ���Զ��޸ĸ������뵥�ķ�Ʊ�źͷ�Ʊ���Ƿ������"; 
		
		BigDecimal txtInvoiceAmt = this.txtInvoiceAmt.getBigDecimalValue();
		String txtInvoiceNum = this.txtInvoiceNumber.getStringValue();
		
		String payRequestBillId = this.editData.getFdcPayReqID();
		SelectorItemCollection selector = new SelectorItemCollection();
		selector.add("invoiceNumber");
		selector.add("invoiceAmt");
		selector.add("allInvoiceAmt");
		PayRequestBillInfo payReq = PayRequestBillFactory.getRemoteInstance().getPayRequestBillInfo(new ObjectUuidPK(payRequestBillId), selector);
		
		boolean isNumSame = compareInvoiceNum(payReq);
		boolean isAmtSame = compareInvoiceAmt(payReq);
		
		int confirmResult = 0;
		if (!isNumSame && !isAmtSame){
			confirmResult = FDCMsgBox.showConfirm2New(this, num_amt_Diff);
			if (confirmResult == FDCMsgBox.NO)
				SysUtil.abort();
			else{
				payReq.setInvoiceNumber(txtInvoiceNum);
				payReq.setAllInvoiceAmt(this.txtAllInvoiceAmt.getBigDecimalValue());
				payReq.setInvoiceAmt(txtInvoiceAmt);
			}
		}
		
		if (!isNumSame && isAmtSame){
			confirmResult = FDCMsgBox.showConfirm2New(this, numDiff);
			if (confirmResult == FDCMsgBox.NO)
				SysUtil.abort();
			else{
				payReq.setInvoiceNumber(txtInvoiceNum);
			}
		}
		
		if (isNumSame && !isAmtSame){
			confirmResult = FDCMsgBox.showConfirm2New(this, amtDiff);
			if (confirmResult == FDCMsgBox.NO)
				SysUtil.abort();
			else{
				payReq.setAllInvoiceAmt(this.txtAllInvoiceAmt.getBigDecimalValue());
				payReq.setInvoiceAmt(txtInvoiceAmt);
			}
		}
		
		//ֻҪ��Ʊ�źͷ�Ʊ���ֻҪ��һ�������ı䣬���¸������뵥��Ʊ�����Ϣ
		if (!isAmtSame || !isNumSame) 
			PayRequestBillFactory.getRemoteInstance().updatePartial(payReq, selector);		
		*/
//		FDCPaymentBillInfo fdcPayment = new FDCPaymentBillInfo();
//		fdcPayment.setAllInvoiceAmt(txtAllInvoiceAmt.getBigDecimalValue());
//		fdcPayment.setInvoiceAmt(txtInvoiceAmt.getBigDecimalValue());
//		fdcPayment.setInvoiceNumber(txtInvoiceNumber.getStringValue());
//		fdcPayment.setInvoiceDate(this.txt.getBigDecimalValue());
		FDCPaymentBillFactory.getRemoteInstance().updateInvoiceAmt(fdcPayment, (PaymentBillInfo)editData);

	}
	
	/**
	 * �Ƚϸ���ķ�Ʊ���븶�����뵥���Ƿ�һ��
	 * @return �����ͬ����true����ͬ����false
	 * @author owen_wen 2010-10-26
	 */
	private boolean compareInvoiceNum(PayRequestBillInfo payReq){
		String txtInvoiceNum = this.txtInvoiceNumber.getStringValue();
		String invoiceNum = payReq.getInvoiceNumber();
		
		if (StringUtils.isEmpty(txtInvoiceNum)){
			if (!StringUtils.isEmpty(invoiceNum))
				return false;
			else 
				return true;
		}else { // ��������Ϊ��ʱ
			return txtInvoiceNum.equals(invoiceNum);
		}
	}
	
	/**
	 * �Ƚϸ���ķ�Ʊ����븶�����뵥���Ƿ�һ��
	 * @return ���һ�·���true����ͬ����false
	 * @author owen_wen 2010-10-26
	 */
	private boolean compareInvoiceAmt(PayRequestBillInfo payReq){
		BigDecimal txtInvoiceAmt = this.txtInvoiceAmt.getBigDecimalValue();		
		BigDecimal invoiceAmt = payReq.getInvoiceAmt();
		
		if (txtInvoiceAmt == null){
			if (invoiceAmt != null) 
				return false;
			else
				return true;
		}else{
			if (invoiceAmt == null)
				return false;
			else 
				return (txtInvoiceAmt.compareTo(invoiceAmt) == 0);
		}
	}
	
	/**
	 * �ο�FDCBillEditUI.checkCanSubmit
	 * @return
	 * @throws Exception
	 */
	protected void checkCanSubmit() throws Exception {

		if (editData.getId() == null) {
			return;
		}
		SelectorItemCollection selector = new SelectorItemCollection();
		selector.add("billStatus");
		if(getBizInterface().exists(new ObjectUuidPK(editData.getId()))){
			PaymentBillInfo info = (PaymentBillInfo) getBizInterface().getValue(new ObjectUuidPK(editData.getId()), selector);
			if(BillStatusEnum.AUDITED.equals(info.getBillStatus())
					|| BillStatusEnum.AUDITING.equals(info.getBillStatus())){
				if(this.canModifyAccountAfterAuit&&BillStatusEnum.AUDITED.equals(info.getBillStatus())){
					return;
				}
				MsgBox.showWarning(this,"����״̬�Ѿ�������л�������ˣ��������ύ!");
				SysUtil.abort();
			}
		}
		// ����Ϊ������ʱ����ʾ�������������ʾ��ǿ��
		// "����֧��+�����ۼ�ʵ��"�Ƿ����"�ƻ���Ŀ"�еı���(���ҵ���������ڵ��·�)"�����������"��
		// ������ڣ�����ʾ�û���"��������ڱ��������������"����������
		if (!"������".equals(paramConPayPlanCtrl)) {
			Date bookDate = (Date) pkbookedDate.getValue();
			Date firstDay = BudgetViewUtils.getFirstDay(bookDate);
			Date lastDay = BudgetViewUtils.getLastDay(bookDate);
			String projectId = editData.getCurProject().getId().toString();
			BigDecimal localBudget = getLocalBudget(firstDay, lastDay,
					projectId);
			BigDecimal payedAmt = editData.getPayedAmt();
			BigDecimal curPaid = editData.getCurPaid();
			localBudget = localBudget == null ? FDCHelper.ZERO : localBudget;
			payedAmt = payedAmt == null ? FDCHelper.ZERO : payedAmt;
			curPaid = curPaid == null ? FDCHelper.ZERO : curPaid;
			if ((curPaid.add(payedAmt).compareTo(localBudget)) > 0) {
				MsgBox.showInfo("��������ڱ��������������");
				// SysUtil.abort();
			}
		}
	}
	
	boolean isMoving = false;
	/**
	 * output actionFirst_actionPerformed
	 */
	public void actionFirst_actionPerformed(ActionEvent e) throws Exception {
		isMoving = true;
		super.actionFirst_actionPerformed(e);
		setOprtStateByBillStatus();
//		fetchInitData();
	}

	/**
	 * output actionPre_actionPerformed
	 */
	public void actionPre_actionPerformed(ActionEvent e) throws Exception {
		isMoving = true;
		super.actionPre_actionPerformed(e);
		setOprtStateByBillStatus();
//		fetchInitData();
	}

	/**
	 * output actionNext_actionPerformed
	 */
	public void actionNext_actionPerformed(ActionEvent e) throws Exception {
		isMoving = true;
		super.actionNext_actionPerformed(e);
		setOprtStateByBillStatus();
//		fetchInitData();
	}

	/**
	 * output actionLast_actionPerformed
	 */
	public void actionLast_actionPerformed(ActionEvent e) throws Exception {
		isMoving = true;
		super.actionLast_actionPerformed(e);
		setOprtStateByBillStatus();
//		fetchInitData();
	}

	/**
	 * output actionPrint_actionPerformed
	 */
	public void actionPrint_actionPerformed(ActionEvent e) throws Exception {
		ArrayList idList = new ArrayList();
		if (editData != null && !StringUtils.isEmpty(editData.getString("id"))) {
			idList.add(editData.getString("id"));
		}
		if (idList == null || idList.size() == 0 || getTDQueryPK() == null
				|| getTDFileName() == null) {
			MsgBox.showWarning(this, FDCClientUtils.getRes("cantPrint"));
			return;
		}
		PaymentBillPrintProvider data = new PaymentBillPrintProvider(editData.getString("id"), getTDQueryPK());
		com.kingdee.bos.ctrl.report.forapp.kdnote.client.KDNoteHelper appHlp = new com.kingdee.bos.ctrl.report.forapp.kdnote.client.KDNoteHelper();
		appHlp.print(getTDFileName(), data, javax.swing.SwingUtilities
				.getWindowAncestor(this));
	}

	protected String getTDFileName() {
		return "/bim/fdc/finance/paymentBill";
	}

	protected IMetaDataPK getTDQueryPK() {
		return new MetaDataPK(
				"com.kingdee.eas.fdc.finance.app.PaymentBillForPrintQuery");
	}

	/**
	 * output actionPrintPreview_actionPerformed
	 */
	public void actionPrintPreview_actionPerformed(ActionEvent e)
			throws Exception {
		ArrayList idList = new ArrayList();
		if (editData != null && !StringUtils.isEmpty(editData.getString("id"))) {
			idList.add(editData.getString("id"));
		}
		if (idList == null || idList.size() == 0 || getTDQueryPK() == null
				|| getTDFileName() == null) {
			MsgBox.showWarning(this, FDCClientUtils.getRes("cantPrint"));
			return;
		}
		PaymentBillPrintProvider data = new PaymentBillPrintProvider(
				editData.getString("id"), getTDQueryPK());
		com.kingdee.bos.ctrl.report.forapp.kdnote.client.KDNoteHelper appHlp = new com.kingdee.bos.ctrl.report.forapp.kdnote.client.KDNoteHelper();
		appHlp.printPreview(getTDFileName(), data, javax.swing.SwingUtilities
				.getWindowAncestor(this));
	}

	/**
	 * output actionCopy_actionPerformed
	 */
	public void actionCopy_actionPerformed(ActionEvent e) throws Exception {
		//�������ø�������
//		super.actionCopy_actionPerformed(e);
//		actionSave.setEnabled(true);
//		setOprtStateByBillStatus();
//		
//		refreshUITitle() ;
	}
	
   

	/**
	 * output actionAddNew_actionPerformed
	 */
	public void actionAddNew_actionPerformed(ActionEvent e) throws Exception {
		checkContractSplitState();
		super.actionAddNew_actionPerformed(e);
		actionSave.setEnabled(true);
		
		refreshUITitle() ;
	}

	/**
	 * output actionEdit_actionPerformed
	 */
	public void actionEdit_actionPerformed(ActionEvent e) throws Exception {
		
		int billState = editData.getBillStatus()!=null?editData.getBillStatus().getValue():10;
		int[] states =  new int[] { BillStatusEnum.SAVE_VALUE ,BillStatusEnum.SUBMIT_VALUE  };
		boolean pass = false;
		for (int i = 0; i < states.length; i++) {
			if (billState==(states[i])) {
				pass = true;
			}
		}
		if (!pass) {
			MsgBox.showWarning(this, ContractClientUtils.getRes("cantEdit"));
			SysUtil.abort();
		}
		
//		kdtEntries.getStyleAttributes().setLocked(false);
//		if(canModifyAmount){
		// getDetailTable().setEnabled(canModifyAmount);
		// getDetailTable().setEditable(canModifyAmount);
		//kdtEntries.getCell(4,5).getStyleAttributes().setLocked(!canModifyAmount
		// );
		// setTableCellColorAndEdit(canModifyAmount) ;
			// ȥ������ƻ��ĸ�:
			int lastRowIdx = kdtEntries.getRowCount() - 1;
			for (int i = 4; i < kdtEntries.getColumnCount(); i++) {
				StyleAttributes styleAttributes = kdtEntries.getCell(lastRowIdx - 1, i)
						.getStyleAttributes();
				styleAttributes.setLocked(true);
				styleAttributes.setBackground(noEditColor);
			}
			kdtEntries.getCell(lastRowIdx - 1, 1).getStyleAttributes().setLocked(true);
			kdtEntries.getCell(lastRowIdx - 1, 1).getStyleAttributes().setBackground(noEditColor);
			
			kdtEntries.getCell(10,5).getStyleAttributes().setLocked(true);
		// kdtEntries.getCell(lastRowIdx -
		// 1,7).getStyleAttributes().setLocked(!canModifyAmount);
//		}
				
		super.actionEdit_actionPerformed(e);
		if (editData.getBillStatus() == null
				|| editData.getBillStatus().equals(BillStatusEnum.SAVE) || editData.getBillStatus().equals(BillStatusEnum.SUBMIT)) {
			actionSave.setEnabled(true);
		} else {
			actionSave.setEnabled(false);
		}
		setOprtStateByBillStatus();
		prmtCompany.setEditable(false);
		prmtFdcPayType.setEditable(false);
		prmtuseDepartment.setEditable(true);
		//prmtPayee.setEditable(false);
//		prmtActFdcPayeeName.setEditable(false);
		prmtCreator.setEditable(false);
		prmtAuditor.setEditable(false);
		prmtCashier.setEditable(false);
		prmtAccountant.setEditable(false);
		txtExchangeRate.setEditable(false);		
		
		Object newValue = prmtCurrency.getValue();
		if (newValue!=null && newValue instanceof CurrencyInfo) {
			BOSUuid srcid = ((CurrencyInfo) newValue).getId();
			if(!srcid.toString().equals(baseCurrency.getId().toString())){
				// ����Ҳ���ɸ�
				txtExchangeRate.setEditable(true);				
			}			
		}
		
		Object  obj = comboPayeeType.getSelectedItem();
		if(obj instanceof AsstActTypeInfo ){
			AsstActTypeInfo asstActTypeInfo = (AsstActTypeInfo)obj;
			if ("provider".equalsIgnoreCase(asstActTypeInfo.getAsstHGAttribute())||"person".equalsIgnoreCase(asstActTypeInfo.getAsstHGAttribute())){
//				prmtPayee.setEnabled(false);
			}
		}
		
		province_dataChanged( editData.getRecProvince());
				
		refreshUITitle() ;
		
		if(isPartA && isPartAMaterialCon){
			paymentBillTableHelper.setPartAButtonStatus(getOprtState());
		}
		
		//R110225-039�������ϸ�������޸Ļص����տ��˻���Ϣ��ʧ��ԭ����ÿ�ε��޸�ʱ���������editData���������¼���һ�Ρ�
		if (editData.getPayeeAccountBank() != null) {
			txtPayeeAccountBank.setText(editData.getPayeeAccountBank());
		}
		if(this.editData.getFdcPayReqID()!=null&&prmtCurrency.getValue()!=null){
			SelectorItemCollection sel=new SelectorItemCollection();
			sel.add("currency.id");
			PayRequestBillInfo pay = PayRequestBillFactory.getRemoteInstance().getPayRequestBillInfo(new ObjectUuidPK(this.editData.getFdcPayReqID()),sel);
			CurrencyInfo currency=(CurrencyInfo)prmtCurrency.getValue();
			if(!pay.getCurrency().getId().toString().equals(currency.getId().toString())){
				this.kdtEntries.getCell(4, 4).getStyleAttributes().setLocked(true);
			}else{
				this.kdtEntries.getCell(4, 4).getStyleAttributes().setLocked(false);
			}
			FDCSQLBuilder _builder = new FDCSQLBuilder();
			_builder.appendSql(" select pb.fid from t_cas_paymentbill pb");
			_builder.appendSql(" where pb.fFdcPayReqID='"+this.editData.getFdcPayReqID()+"'");
			if(editData.getId()!=null){
				_builder.appendSql(" and pb.fid!='"+editData.getId().toString()+"'");
			}
			final IRowSet rowSet = _builder.executeQuery();
			if (rowSet.size()>0) {
				this.prmtCurrency.setEnabled(false);
			}
		}
	}

	/**
	 * output actionRemove_actionPerformed
	 */
	public void actionRemove_actionPerformed(ActionEvent e) throws Exception {
		checkBeforeRemove();
		super.actionRemove_actionPerformed(e);
	}

	protected void checkBeforeRemove() throws Exception {
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(
				new FilterItemInfo("paymentBill", editData.getId()));
		view.setFilter(filter);
		view.getSelector().add("id");
		CoreBillBaseCollection coll = PaymentSplitFactory.getRemoteInstance()
				.getCoreBillBaseCollection(view);
		Iterator iter = coll.iterator();
		if (iter.hasNext()) {
			MsgBox.showWarning(this, ContractClientUtils.getRes("noRemove"));
			SysUtil.abort();
		}
	}

	/**
	 * output actionCreateFrom_actionPerformed
	 */
	public void actionCreateFrom_actionPerformed(ActionEvent e)
			throws Exception {
		this.getUIContext().put("contractbillid",
				this.editData.getContractBillId());
		IBTPBillEdit billEdit = this.getBillEdit();
		HashMap ctx = new HashMap();
		ctx.put("SourceBillType", "PayRequestBill");
		ctx.put("TargetBillType", "PaymentBill");
		// ���ݺ�ͬid�Խ��и������뵥�Ĺ���
		ctx.put("contractbillid", this.editData.getContractBillId());
		// billEdit.createFrom(ctx);
		billEdit.createFrom(null, ctx);
		this.getUIContext().put("BOTPViewStatus", new Integer(0));
		// super.actionCreateFrom_actionPerformed(e);
	}

	public int getBtpCreateFromType() {
		// return pullTypeSingleToSingle;
		return pullTypeSingleToMutil;
	}

	/**
	 * output actionTraceUp_actionPerformed
	 */
	public void actionTraceUp_actionPerformed(ActionEvent e) throws Exception {
		// super.actionTraceUp_actionPerformed(e);
//		check();
//		showPayreq();
		if(this.editData.getContractBillId()!=null&&this.editData.getFdcPayReqID()!=null){
			String id=this.editData.getFdcPayReqID();
			String className=PayRequestBillEditUI.class.getName();
			if(PayReqUtils.isConWithoutTxt(this.editData.getContractBillId())){
				id=this.editData.getContractBillId();
				className=ContractWithoutTextEditUI.class.getName();
			}
			UIContext uiContext = new UIContext(this);
			uiContext.put("ID", id);
	        IUIFactory uiFactory = UIFactory.createUIFactory(UIFactoryName.MODEL);
	        IUIWindow uiWindow = uiFactory.create(className, uiContext,null,OprtState.VIEW);
	        uiWindow.show();
		}else{
			FDCMsgBox.showWarning(this,"��Դ���ݣ�");
		}
	}
	/**
	 * ��ͬ���ս�����ύ������ʱ���Ƚ��������ĸ���ϼƽ����ϱ��θ�������ں�ͬ����۵Ĵ�С
	 * by Cassiel_peng  2009-08-12		<p>
	 * �߼���������,�����ͬʵ����(��������) �� ���θ����ͬ�ڹ��̿�ڷ������ں�ͬ����ۣ��������Ӧ��ʾ
	 * by Cassiel_peng  2009-12-03 		<p>
	 */
	private void checkAmt() throws Exception{
		storeFields();
		Set paymentBillSet=new HashSet();
		paymentBillSet.add(this.editData);
		boolean isCanPass=PaymentBillClientUtils.checkForSaveSubmitAudit(paymentBillSet);
		if(!isCanPass){
			MsgBox.showError("��ͬʵ����ܴ��ں�ͬ����ۡ���ͬʵ���� =�ѹرյĸ������뵥��Ӧ�ĸ����ͬ�ڹ��̿�ϼ� + δ�رյĸ������뵥��ͬ�ڹ��̿�ϼơ�");
			SysUtil.abort();
		}
	}
	private void check() throws Exception {
		if (this.editData.getFdcPayReqID() == null) {
			MsgBox.showWarning(this, PaymentBillClientUtils
					.getRes("noSourceBill"));
			SysUtil.abort();
		} else {
			EntityViewInfo vi = new EntityViewInfo();
			FilterInfo fi = new FilterInfo();
			FilterItemCollection it = fi.getFilterItems();
			// it.add(new FilterItemInfo("number",
			// this.editData.getFdcPayReqNumber()));
			it.add(new FilterItemInfo("id", this.editData.getFdcPayReqID()));
			vi.setFilter(fi);
			PayRequestBillCollection c = PayRequestBillFactory
					.getRemoteInstance().getPayRequestBillCollection(vi);
			Iterator iter = c.iterator();
			if (!iter.hasNext()) {
				MsgBox.showWarning(this, PaymentBillClientUtils
						.getRes("noSourceBill"));
				SysUtil.abort();
			}
		}
	}

	private void showPayreq() throws Exception {
		Map uiContext = new HashMap();
		uiContext.put(UIContext.OWNER, this);
		String billID = editData.getFdcPayReqID().toString(); // ��ȡID
		FilterInfo myFilter = new FilterInfo();
		myFilter.getFilterItems().add(new FilterItemInfo("id", billID));
		uiContext.put("MyFilter", myFilter); // ע�⣬�����billID��44λ����BOSUuid��������String
		// IUIFactory uiFactory=UIFactory.createUIFactory(UIFactoryName.MODEL) ;
		IUIFactory uiFactory = UIFactory.createUIFactory(UIFactoryName.NEWWIN);
		IUIWindow window = uiFactory.create(
				"com.kingdee.eas.fdc.contract.client.PayRequestFullListUI",
				uiContext, null);
		if (window instanceof UIModelDialog) {
			Dimension screenDim = Toolkit.getDefaultToolkit().getScreenSize();
			// ((JPanel)((UIModelDialog)window).getContentPane()).setSize(screenDim);
			int width = 1013;
			int height = 629;
			Dimension maxSize = new Dimension(1013, 629);
			if (screenDim.width > maxSize.getWidth()
					|| screenDim.height > maxSize.getHeight()) {
				screenDim = maxSize;
			}
			JPanel panel = ((JPanel) ((UIModelDialog) window).getContentPane());
			ListUI fullUI = (ListUI) window.getUIObject();
			// fullUI.setSize(new Dimension(690,300));
			fullUI.getMainTable().setBounds(
					new Rectangle(10, 10, width - 25, height - 50));
			fullUI.getMainTable().getColumn("id").getStyleAttributes()
					.setHided(true);
			KDTabbedPane tabPane = new KDTabbedPane();
			tabPane.addTab(PaymentBillClientUtils.getRes("payReqBill"), fullUI);
			panel.add(tabPane);
			panel.setPreferredSize(screenDim);
		} else {
			Dimension screenDim = Toolkit.getDefaultToolkit().getScreenSize();
			Dimension maxSize = new Dimension(1013, 629);
			screenDim.setSize(maxSize);
			JPanel panel = ((JPanel) ((KDFrame) window).getContentPane());
			ListUI fullUI = (ListUI) window.getUIObject();
			fullUI.getMainTable().getColumn("id").getStyleAttributes()
					.setHided(true);
			KDTabbedPane tabPane = new KDTabbedPane();
			JPanel panel2 = new KDPanel();
			panel2.setLayout(new BorderLayout());
			panel2.add(fullUI, BorderLayout.CENTER);
			tabPane.addTab(PaymentBillClientUtils.getRes("payReqBill"), panel2);
			panel.setLayout(new BorderLayout());
			panel.add(tabPane, BorderLayout.CENTER);
			panel.setPreferredSize(screenDim);
		}
		window.show();
	}

	/*
	 * ��ӵ���˰�ť
	 */
	public void actionAudit_actionPerformed(ActionEvent e) throws Exception {
		
		checkAmt();
		
		isMoving = true;
		//����ύʱ���Ƿ����ͬ���
		if(checkAllSplit){
			checkContractSplitState();			
		}
		//����ύʱ���Ƿ����ͬ���
		if(checkAllSplit){//��ʷ�����ύδ��ֵ�
			checkContractSplitState();			
		}
		super.actionAudit_actionPerformed(e);
		if (editData == null || editData.getId() == null) {
			return;
		}
		FDCClientUtils.checkBillInWorkflow(this, editData.getId().toString());
		List list = new ArrayList();
		list.add(editData.getId().toString());
		boolean isSuccess = PaymentBillFactory.getRemoteInstance().audit4FDC(list);
		if (isSuccess) {
			// actionAudit.setEnabled(false);
			prmtAuditor.setValue(SysContext.getSysContext().getCurrentUserInfo());
			FDCClientUtils.showOprtOK(this);
		}
		syncDataFromDB();
		setOprtStateByBillStatus();
		lockUIForViewStatus();
		if(this.editData.getId()!=null){
			AbstractSplitInvokeStrategy strategy = SplitInvokeStrategyFactory.createSplitStrategyByParam(this.editData.getId().toString(), this);
			strategy.invokeSplit();
		}
	}

	// �״�ť
	public void actionTaoPrint_actionPerformed(ActionEvent e) throws Exception {
		ArrayList idList = new ArrayList();
		List id = null;
		if (this.editData != null) {
			idList.add(editData.getId().toString());
		}
		if (idList == null || idList.size() == 0)
			return;
		com.kingdee.bos.ctrl.kdf.data.impl.BOSQueryDelegate data = new com.kingdee.eas.framework.util.CommonDataProvider(
				idList, getTDQueryPK());
		com.kingdee.bos.ctrl.report.forapp.kdnote.client.KDNoteHelper appHlp = new com.kingdee.bos.ctrl.report.forapp.kdnote.client.KDNoteHelper();
		appHlp.print(getTDFileName(), data, javax.swing.SwingUtilities
				.getWindowAncestor(this));
		super.actionTaoPrint_actionPerformed(e);
	}

	protected KDTable getDetailTable() {
		return kdtEntries;
		// return null;
	}

	public JPanel getPayBillEntryPanel() {
		return pnlPayBillEntry;
	}

	protected IObjectValue createNewData() {
		
		return null;
//		PaymentBillInfo req = new PaymentBillInfo();
//		req.setBillStatus(BillStatusEnum.SAVE);
//		req.setSourceType(SourceTypeEnum.FDC);
//		req.setSettleBizType(SettBizTypeEnum.PAYOUTSIDE);
//		req.setCompany(SysContext.getSysContext().getCurrentFIUnit());
//		req.setFdcPayeeType(FdcPayeeTypeEnum.OTHER);
//		req.setCreator(SysContext.getSysContext().getCurrentUserInfo());
//		req.setCreateTime(new Timestamp(System.currentTimeMillis()));
//		req.setUrgentDegree(UrgentDegreeEnum.NORMAL);
//		req.setDayaccount(FDCConstants.ZERO);
//		req.setPayDate(DateTimeUtils.truncateDate(new Date()));
//
//		if (baseCurrency != null) {
//			try {
//				req.setCurrency(CurrencyFactory.getRemoteInstance()
//								.getCurrencyInfo(new ObjectUuidPK(baseCurrency.getId())));
//			} catch (Exception e) {
//				handUIException(e);
//			}			
//			req.setExchangeRate(FDCConstants.ONE);
//		}
//		
//		String contractBillId = (String) getUIContext().get("contractBillId");
//		if (contractBillId != null && contractBillId.trim().length() > 1) {
//			try {
//				SelectorItemCollection selector = new SelectorItemCollection();
//				if ((BOSUuid.read(contractBillId).getType())
//						.equals(new ContractBillInfo().getBOSType())) {
//					// if(new
//					// BOSObjectTpye(BOSUuid.read(contractBillId).getType()) )
////					ContractBillInfo contractBill = ContractBillFactory
////							.getRemoteInstance().getContractBillInfo(
////									new ObjectUuidPK(BOSUuid
////											.read(contractBillId)));
//					// req.setContractBill(contractBill);
//					String str = contractBill.getNumber();
//					this.txtContractNo.setText(str + "");
//					if (str != null)
//						req.setContractNo(str);
//					BOSUuid conno = contractBill.getId();
//					req.setContractBillId(conno + "");
//					// ������ͬ�ҷ�
//					req.setActFdcPayeeName(SupplierFactory.getRemoteInstance()
//							.getSupplierInfo(
//									new ObjectUuidPK((contractBill.getPartB())
//											.getId())));
//					req.setFdcPayeeName(SupplierFactory.getRemoteInstance()
//							.getSupplierInfo(
//									new ObjectUuidPK((contractBill.getPartB())
//											.getId())));
//					// ����������Ŀ
//					CurProjectInfo curProject = contractBill.getCurProject();
//					selector = new SelectorItemCollection();
//					selector.add("name");
//					curProject = CurProjectFactory.getRemoteInstance()
//							.getCurProjectInfo(
//									new ObjectUuidPK(curProject.getId()));// ,
//					// selector);
//					req.setCurProject(curProject);
//					BigDecimal amount = new BigDecimal("0");
//					FilterInfo filter = new FilterInfo();
//					filter.getFilterItems().add(
//							new FilterItemInfo("contractBill.id", contractBill
//									.getId()));
//					filter.getFilterItems().add(
//							new FilterItemInfo("state",
//									FDCBillStateEnum.AUDITTED_VALUE));
//					filter.getFilterItems().add(
//							new FilterItemInfo("state",
//									FDCBillStateEnum.VISA_VALUE));
//					filter.getFilterItems().add(
//							new FilterItemInfo("state",
//									FDCBillStateEnum.ANNOUNCE_VALUE));
//					filter.setMaskString("#0 and (#1 or #2 or #3)");
//					// ���ǩ֤���
//					EntityViewInfo view = new EntityViewInfo();
//					view.setFilter(filter);
//					view.getSelector().add("amount");
//					IObjectCollection collection = ContractChangeBillFactory
//							.getRemoteInstance()
//							.getContractChangeBillCollection(view);
//					ContractChangeBillInfo billInfo;
//
//					for (Iterator iter = collection.iterator(); iter.hasNext();) {
//						billInfo = (ContractChangeBillInfo) iter.next();
//						if (billInfo.getAmount() != null)
//							amount = amount.add(billInfo.getAmount());
//					}
//					req.setChangeAmt(amount);
//					// ������
//					if (contractBill.getSettleAmt() != null) {
//						req.setSettleAmt(contractBill.getSettleAmt());
//					}
//					// ���ݽ��㵥��״̬������������۵�ֵ
//					if (!contractBill.isHasSettled()) {
//						// δ���ս���,������ۣ���ͬ�ۣ������ͬ�ۣ���Ҫ��һ����ȷ���󣩣��ú�ͬ�����������б��ǩ֤���Ľ��֮��
//						if (contractBill.getAmount() != null) {
//							if (req.getChangeAmt() != null)
//								amount = contractBill.getAmount().add(
//										req.getChangeAmt());
//							else
//								amount = contractBill.getAmount();
//						} else if (req.getChangeAmt() != null)
//							amount = req.getChangeAmt();
//						else
//							amount = FDCHelper.ZERO;
//					} else {
//						if (contractBill.getSettleAmt() != null)
//							amount = contractBill.getSettleAmt();
//					}
//					req.setLatestPrice(amount);
//					req.setPayedAmt(new BigDecimal("0"));
//					req.setAmount(new BigDecimal("0"));
//
//				} else if ((BOSUuid.read(contractBillId).getType())
//						.equals(new ContractWithoutTextInfo().getBOSType())) {
//					ContractWithoutTextInfo text = ContractWithoutTextFactory
//							.getRemoteInstance().getContractWithoutTextInfo(
//									new ObjectUuidPK(BOSUuid
//											.read(contractBillId)));
//					String str = text.getNumber();
//					this.txtContractNo.setText(str + "");
//					if (str != null)
//						req.setContractNo(str);
//					BOSUuid conno = text.getId();
//					// req.setContractBill(conno);
//					req.setContractBillId(conno + "");
//					// �������ı���ͬ�տλ
//					req.setFdcPayeeName(SupplierFactory.getRemoteInstance()
//							.getSupplierInfo(
//									new ObjectUuidPK((text.getReceiveUnit()
//											.getId()))));
//					req.setActFdcPayeeName(SupplierFactory.getRemoteInstance()
//							.getSupplierInfo(
//									new ObjectUuidPK((text.getReceiveUnit())
//											.getId())));
//					// �������ı���ͬ������Ŀ
//					CurProjectInfo curProject = text.getCurProject();
//					selector = new SelectorItemCollection();
//					selector.add("name");
//					curProject = CurProjectFactory.getRemoteInstance()
//							.getCurProjectInfo(
//									new ObjectUuidPK(curProject.getId()));// ,
//					// selector);
//					req.setCurProject(curProject);
//					// String str=curProject.getName();
//					// String str=curProject.toString(
//				}
//			} catch (EASBizException e) {
//				super.handUIException(e);
//			} catch (BOSException e) {
//				super.handUIException(e);
//			} catch (UuidException e) {
//				super.handUIException(e);
//			}
//		}
//		// setAutoNumber();
//		return req;
	}

	protected IObjectValue createNewDetailData(KDTable table) {
		PaymentBillEntryInfo reqentry = new PaymentBillEntryInfo();
		return reqentry;
	}

    protected IObjectValue getValue(IObjectPK pk) throws Exception {
    	if(editData==null || isMoving){
    		getUIContext().put("contractBillId",null);
    		getUIContext().put("ID",pk.toString());
    		
    		fetchInitData();
    		return getBizInterface().getValue(pk, getSelectors());
    	}else{
    		return editData;
    	}
    }
    
	protected ICoreBase getBizInterface() throws Exception {
		return PaymentBillFactory.getRemoteInstance();
	}
	
    protected void txtrecCity_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
    {
//		Object newValue = e.getNewValue();
////		txtrecProvince.setEnabled(true);
//		if (newValue instanceof CityInfo) {
//			CityInfo city = (CityInfo)newValue;
//			if(city.isIsDirCity()){				
//				txtrecCity.setEnabled(false);
//			}else{
//				txtrecCity.setEnabled(false);
//			}
//		}
    }
    
    protected void txtrecProvince_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
    {
		Object newValue = e.getNewValue();
		
		if (newValue instanceof ProvinceInfo) {
			if(!GlUtils.isEqual(newValue,e.getOldValue())){
//				txtrecCity.setValue(null);
			}
			
			ProvinceInfo province = (ProvinceInfo) newValue;
			province_dataChanged( province.getId().toString());
			
		}else{
			txtrecCity.setEnabled(true);
//			txtrecCity.setValue(null);
		}
    }
    
    private void province_dataChanged(String provinceId)throws Exception{		
    	if(provinceId==null){
    		return ;
    	}
    	
		EntityViewInfo ev = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		ev.setFilter(filter);
		SelectorItemCollection selector = ev.getSelector();
		selector.add("id");
		selector.add("isDirCity");
		selector.add("name");
		selector.add("number");
		
		filter.getFilterItems().add(
				new FilterItemInfo("isDirCity", Boolean.TRUE));
		if(BOSUuid.isValidLength(provinceId)){
			filter.getFilterItems().add(
				new FilterItemInfo("province.id",provinceId ));
		}else{
			filter.getFilterItems().add(
					new FilterItemInfo("province.name",provinceId ));
		}
		
		
		CityCollection col = CityFactory.getRemoteInstance().getCityCollection(ev);
		
		CityInfo city = null;
		if(col.size()>0){
			city = col.get(0);
		}
		if(city!=null && city.isIsDirCity()){				
//			txtrecCity.setEnabled(false);
//			txtrecCity.setValue(city);
//			txtrecCity.setValue(null);
		}else{
			txtrecCity.setEnabled(true);				
		}
    }
    
    /**
     * output txtrecCity_willCommit method
     */
    protected void txtrecCity_willCommit(com.kingdee.bos.ctrl.swing.event.CommitEvent e) throws Exception
    {
    }

    /**
     * output txtrecCity_willShow method
     */
    protected void txtrecCity_willShow(com.kingdee.bos.ctrl.swing.event.SelectorEvent e) throws Exception
    {
    	Object obj = txtrecProvince.getData();
    	
		EntityViewInfo ev = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		ev.setFilter(filter);
		SelectorItemCollection selector = new SelectorItemCollection();;
		selector.add("id");
		selector.add("isDirCity");
		selector.add("name");
		selector.add("province.name");
		
//		if(txtrecProvince.isEnabled()){
			if(obj instanceof String ){
				String province = (String) obj;
				if(province!=null){
					filter.getFilterItems().add(
							new FilterItemInfo("province.name", province));
				}
			}else if(obj instanceof ProvinceInfo ){
				ProvinceInfo province = (ProvinceInfo) obj;
				if(province!=null){
					filter.getFilterItems().add(
							new FilterItemInfo("province.id", province.getId().toString()));
				}
			}		
//		}

		txtrecCity.setSelectorCollection(selector);
    	txtrecCity.setEntityViewInfo(ev);		
    	txtrecCity.getQueryAgent().resetRuntimeEntityView();
    }

	// ���ݱұ��Զ���ȡ����
	protected void prmtCurrency_dataChanged(DataChangeEvent e) throws Exception {
		// prmtPayerAccount.setValue(null);
		Object newValue = e.getNewValue();
		
		if (newValue instanceof CurrencyInfo) {
			BOSUuid srcid = ((CurrencyInfo) newValue).getId();			
			Date bookedDate = (Date)pkBizDate.getValue();
	    	
			ExchangeTableInfo baseExchangeTable = this.currentCompany.getBaseExchangeTable();
			if (baseExchangeTable != null) {
				if (baseCurrency != null) {
					if (srcid.equals(baseCurrency.getId())) {
						// �Ǳ�λ��ʱ������ѡ����û�
						// txtExchangeRate.setValue(null);
						txtExchangeRate.setValue(FDCHelper.ONE);
						txtExchangeRate.setRequired(false);
						txtExchangeRate.setEditable(false);
						txtExchangeRate.setEnabled(false);
						setAmount();
						return;
					}
					txtExchangeRate.setRequired(true);
					txtExchangeRate.setEditable(true);
					txtExchangeRate.setEnabled(true);
//					IExchangeRate iExchangeRate = ExchangeRateFactory
//							.getRemoteInstance();
//					ExchangeRateInfo exchangeRate = iExchangeRate
//							.getExchangeRate(new ObjectUuidPK(baseExchangeTable
//									.getId()), new ObjectUuidPK(srcid),
//									new ObjectUuidPK(baseCurrency.getId()),
//									new Date());
					
					ExchangeRateInfo exchangeRate = FDCClientHelper.getLocalExRateBySrcCurcy(this,srcid,currentCompany,bookedDate);
					
			    	int curPrecision = FDCClientHelper.getPrecOfCurrency(srcid);
			    	BigDecimal exRate = FDCHelper.ONE;
			    	int exPrecision = curPrecision;
			    	
			    	if(exchangeRate!=null){
			    		exRate = exchangeRate.getConvertRate();
			    		exPrecision = exchangeRate.getPrecision();
			    	}
			    	txtExchangeRate.setValue(exRate);
			    	txtExchangeRate.setPrecision(exPrecision);
			    	txtAmount.setPrecision(curPrecision);
			    	
//					if (exchangeRate != null) {
//						txtExchangeRate.setValue(exchangeRate.getConvertRate());
//					} else {
//						txtExchangeRate.setValue(new BigDecimal(0));
//					}
					setAmount();

				} else {
					logger.info("��ǰ��֯û�����ñ�λ��");
					MsgBox.showInfo(this, PaymentBillClientUtils
							.getRes("noBaseCurrency"));
				}
//
			} else {
				logger.info("��ǰ��֯û�л��ʱ�");
				MsgBox.showInfo(this, PaymentBillClientUtils
						.getRes("noExchangeRateTable"));
			}

		}
		super.prmtCurrency_dataChanged(e);
	}

	protected void txtExchangeRate_dataChanged(DataChangeEvent e)
			throws Exception {
		super.txtExchangeRate_dataChanged(e);
		setAmount();
		setProjectPriceInContract();
		if(this.isAdvance())
			this.setAdvance();
	}
	/**
	 * �ύ״̬�ĸ���ڲ�ֺ��ٴ��޸ı�����ʱ����ͻ���ʾ   by Cassiel
	 */
	boolean isWarn = false;
	protected void btnInputCollect_mouseClicked(MouseEvent e) throws Exception {
		super.btnInputCollect_mouseClicked(e);
		if(STATUS_EDIT.equals(getOprtState())&&!isWarn){
			//������
			boolean isCostSplit = FDCSplitClientHelper.isBillSplited(
					getSelectBOID(), "T_FNC_PaymentSplit", "FPaymentBillID");
			//����ǳɱ���Ŀ���
			boolean isNoCostSplit = FDCSplitClientHelper.isBillSplited(
					getSelectBOID(), "T_FNC_PaymentNoCostSplit",
					"FPaymentBillID");
			if (isCostSplit || isNoCostSplit) {
				isWarn = true;
				MsgBox.showWarning("�˸���Ѿ���֣��޸Ľ��ᵼ�¸��������ֲ�һ�£��޸Ľ��󣬽�������");
			}
		}
	}
	/**
	 * ��������Ӧ����������"��ť�¼�<p>
	 * ��Ϊԭ�ҿ��ܻ��ʲ�һ��������setAmount���ݻ��ʼ��㱾λ��,ֱ��ȡ����������еı���
	 */
	
	protected void btnInputCollect_actionPerformed(ActionEvent e)
			throws Exception {
		
		// �������������
		super.btnInputCollect_actionPerformed(e);
//		if(isBaseCurrency){
			Object cell = bindCellMap.get(PaymentBillContants.CURPAID);
			if (cell instanceof ICell) {
				Object value = ((ICell) cell).getValue();
				if (value != null) {
					try {
						// txtAmount.setValue(toBigDecimal(value));
						txtAmount.setValue(FDCHelper.toBigDecimal(value));
						editData.setAmount(FDCHelper.toBigDecimal(value));
					} catch (NumberFormatException e1) {
						super.handUIException(new Throwable("��ʽ����!"
								+ e1.getMessage()));
					}
//					setAmount();
				}
			}
			cell = bindCellMap.get(PaymentBillContants.CURPAIDLOCAL);
			if (cell instanceof ICell) {
				Object value = ((ICell) cell).getValue();
				if (value != null) {
					try {
						txtLocalAmt.setValue(FDCHelper.toBigDecimal(value));
						editData.setLocalAmt(FDCHelper.toBigDecimal(value));
					} catch (NumberFormatException e1) {
						super.handUIException(new Throwable("��ʽ����!"
								+ e1.getMessage()));
					}
					setAmount();
				}
			}
//		}
		
	}
	Map paramValue = new HashMap();
	private void preProcess() throws BOSException, EASBizException {

		paramValue.put("ui_name", "CasPaymentBillUI");
		paramValue.put("isFirtTime", Boolean.TRUE);
		paramValue.put(CashSysParamConstants.CS031, Boolean.FALSE);
		paramValue.put("ID", getUIContext().get(UIContext.ID));
		HashMap param = new HashMap();
		paramValue.put("param", param);
		Map resultMap = new HashMap();
		resultMap.put("isFirtTime", Boolean.TRUE);
		resultMap.put("ui_name", "CasPaymentBillUI");
		resultMap.put("ID", getUIContext().get(UIContext.ID));
		
		resultMap = BatchFetchParamFacadeFactory.getRemoteInstance()
				.fetchParam(resultMap);
		paramValue.putAll(resultMap);
	}
	
	/**
	 * ��ͬ���������ò�ͬ��WorkButton״̬ ֻ����action����
	 */
	protected void initDataStatus() {
		super.initDataStatus();
		
		CompanyF7Factory.initCompanyBizUnitF7(this, prmtAgentCompany);
		
		String oprtS = getOprtState();
		boolean val = false;
        if(paramValue.get("CS024") == null)
            val = ContextHelperFactory.getRemoteInstance().getBooleanParam("CS024", new ObjectUuidPK(currentCompany.getId()));
        else
            val = ((Boolean)paramValue.get("CS024")).booleanValue();
        
		PaymentBillInfo info = (PaymentBillInfo)getDataObject();
		BillStatusEnum billStatus = BillStatusEnum.SAVE;
        if(info.getBillStatus() != null)
        	billStatus = info.getBillStatus();
		boolean canApprove = billStatus.equals(BillStatusEnum.SUBMIT) && val && !"FINDVIEW".equals(oprtS);
        actionApprove.setEnabled(canApprove);
        actionApprove.setVisible(canApprove);
        boolean canAntiApprove = billStatus.equals(BillStatusEnum.APPROVED) && val && !"FINDVIEW".equals(oprtS);
        actionUntiApprove.setEnabled(canAntiApprove);
        actionUntiApprove.setVisible(canAntiApprove);
        btnUntiApprove.setVisible(false);
        if(canAntiApprove){
        	this.txtPayeeBank.setAccessAuthority(CtrlCommonConstant.AUTHORITY_COMMON);
        	this.txtPayeeAccountBank.setAccessAuthority(CtrlCommonConstant.AUTHORITY_COMMON);
        	this.prmtPayee.setAccessAuthority(CtrlCommonConstant.AUTHORITY_COMMON);
        	this.prmtCurrency.setAccessAuthority(CtrlCommonConstant.AUTHORITY_COMMON);
        	this.prmtCurrency.setAccessAuthority(CtrlCommonConstant.AUTHORITY_COMMON);
        	this.txtPayeeAccountBank.setAccessAuthority(CtrlCommonConstant.AUTHORITY_COMMON);
        	this.prmtPayBillType.setAccessAuthority(CtrlCommonConstant.AUTHORITY_COMMON);
        	this.txtPayeeBank.setEnabled(false);
        	this.txtPayeeAccountBank.setEnabled(false);
        	this.prmtPayee.setEnabled(false);
        	this.prmtCurrency.setEnabled(false);
        	this.prmtPayerAccount.setEnabled(false);
        	this.txtPayeeAccountBank.setEnabled(false);
        	this.prmtPayBillType.setEnabled(false);
        }
	}
	
	/**
	 * ��������ʼ��UI���ֿؼ� 1���ұ� 2���տ������ͣ��ͻ�����Ӧ�̡�ְԱ����˾����������
	 */
	private void initUIProp() throws Exception {
		// ��ʼ������������
		comboPayeeType.removeAllItems();
		
		AsstActTypeCollection asstActTypeColl = CasRecPayHandler.getAsstActTypeColl(null, false, false);
		comboPayeeType.addItem(FMSysDefinedEnum.OTHER);
		
		for (int i = 0; i < asstActTypeColl.size(); i++) {
			if ("innerAccount".equalsIgnoreCase(asstActTypeColl.get(i)
					.getAsstHGAttribute())) {
				innerAccountActType = asstActTypeColl.get(i);

			}
			if ("provider".equalsIgnoreCase(asstActTypeColl.get(i)
					.getAsstHGAttribute())) {
				supplierAssitType = asstActTypeColl.get(i);
			}
		}
		
		comboPayeeType.addItems(asstActTypeColl.toArray());
		

	}
	boolean isFpItem = false;
	public void onLoad() throws Exception {
		isFpItem =FDCDealFPOrBudgetItemUtil.getIsFpOrBg();
		
		// �ʽ�ƻ���Ǩ��ԭ�ƻ���Ŀ����ʹ�ã����ú�ͬ�¶ȹ����ƻ����Ƹ���
		// edit by emanon ����ע�͵�
//		if (isFpItem) {// ������ʾ�ƻ���Ŀ
//			prmtFpItem.setQueryInfo("com.kingdee.eas.fm.fpl.FpItemQuery");
//			
//			Set set = new HashSet();
//			set.add(FPItemDirectionEnum.OUTWARD_MOVEMENT);
//			set.add(FPItemDirectionEnum.OTHER_VALUE);
//
//			FilterInfo filter = new FilterInfo();
//			filter.getFilterItems().add(
//					new FilterItemInfo("direction", set, CompareType.INCLUDE));
//
//			EntityViewInfo evi = new EntityViewInfo();
//			evi.setFilter(filter);
//			
//			prmtFpItem.setEntityViewInfo(evi);
//		}else{//������ʾԤ����Ŀ
//			NewBgItemDialog bgItemSelect = new NewBgItemDialog(this);
//			prmtFpItem.setSelector(bgItemSelect);
//			prmtFpItem.setSelector(bgItemSelect);
//		}
		
		//ע��һ��������   by Cassiel_peng
		btnInputCollect.addMouseListener(new MouseAdapter(){
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				super.mouseClicked(e);
				try {
					btnInputCollect_mouseClicked(e);
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		});
		
		// �������ʽ���ɣ�����Ҫ����ȡ��
		boolean notFirstTime = paramValue != null
				&& paramValue.get("isFirtTime") != null
				&& paramValue.get("isFirtTime").equals(Boolean.FALSE);
		boolean isCreateFrom = this.getBOTPViewStatus() == 1 && notFirstTime;
		if (!isCreateFrom) {
			preProcess();
		}
		
		fetchInitData();
		
		initUIProp();
		
		super.onLoad();
		
//		if(this.editData.getFdcPayReqID()!=null){
//			SelectorItemCollection sel=new SelectorItemCollection();
//			sel.add("hasClosed");
//			PayRequestBillInfo pay = PayRequestBillFactory.getRemoteInstance().getPayRequestBillInfo(new ObjectUuidPK(this.editData.getFdcPayReqID()),sel);
//			if(pay.isHasClosed()){
//				FDCMsgBox.showWarning(this,"�������뵥�ѹرգ���ֹ������");
//				SysUtil.abort();
//			}
//		}
		contractId = editData.getContractBillId();
		
		// this.txtNumber.setEnabled(false);
		addBalanceListener();
		//����ύ������ʱ���Ƿ�У���ͬδ��ȫ���
		checkAllSplit = FDCUtils.getDefaultFDCParamByKey(null,null,FDCConstants.FDC_PARAM_CHECKALLSPLIT);
		// ������ǰ��kdtEntries���Լ���kdtable���
		Rectangle kdtRectangle = kdtEntries.getBounds();
		kdtEntries.setEnabled(false);
		kdtEntries.setVisible(false);
		kdtEntries = createPaymentBillTable();
		kdtEntries.addKDTEditListener(new KDTEditAdapter(){
			public void editStopped(KDTEditEvent e) {
				try {
					kdtEntries_editStopped(e);
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		});
		
//		if (this.getBOTPViewStatus() == 1) {//����initFixTable(table)���ѱ�֤�κ������ȡ���뵥��ͬ�ڹ��̿�
//			((ICell)bindCellMap.get(PaymentBillContants.PROJECTPRICEINCONTRACT)).setValue(editData.getAmount());
//		}
		
		kdtEntries.setBounds(kdtRectangle);
		// pnlPayBillEntry.add(kdtEntries, null);
		pnlPayBillEntry.add(kdtEntries, new KDLayout.Constraints(10, 10, 850,
				450, KDLayout.Constraints.ANCHOR_TOP
						| KDLayout.Constraints.ANCHOR_BOTTOM_SCALE
						| KDLayout.Constraints.ANCHOR_LEFT_SCALE
						| KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
		// ��ҳǩ��ʾ
		sortPanel();
		kdtEntries.getScriptManager().runAll();
		afterPressDeleteButton();
		if (getOprtState() != null && !getOprtState().equals(OprtState.ADDNEW)
				&& !getOprtState().equals(OprtState.EDIT)) {
			// ������¼��Ԫ��
			Set set = bindCellMap.keySet();
			for (Iterator iter = set.iterator(); iter.hasNext();) {
				Object obj = bindCellMap.get(iter.next());
				if (obj instanceof ICell) {
					ICell cell = (ICell) obj;
					if (!cell.getStyleAttributes().isLocked()) {
						cell.getStyleAttributes().setLocked(true);
					}
				}
			}
			//			
			int last = getDetailTable().getRowCount() - 1;
			// �������
			getDetailTable().getCell(last, 6).getStyleAttributes().setLocked(
					true);
			//�׹�
			getDetailTable().getCell(last - 4, 5).getStyleAttributes()
					.setLocked(true);

		}else{			
//			//�����޸ĵ������
//			boolean canModify = true;
//			BillStatusEnum billState = editData.getBillStatus();
//			if (canModifyAccountAfterAuit && getOprtState().equals(OprtState.EDIT) &&
//					billState!=null && billState.equals(BillStatusEnum.AUDITED)){
//				canModify = !editData.isFiVouchered();
//				
//				//			
//
//			}
		}
		
		String contractid = (String) getUIContext().get("contractBillId");
		if (contractid == null && editData != null && editData.getId() != null) {
			getUIContext().put("contractBillId", editData.getContractBillId());
		}
			
		for (int i = 0; i < getDetailTable().getColumnCount(); i++) {
			getDetailTable().getColumn(i).setSortable(false);
		}

		txtSummary.setMaxLength(500);    
		prmtDesc.setMaxLength(150); 
		f7SettleNumber.setMaxLength(80);  
		txtUsage.setMaxLength(usageLegth);
		
		pkBizDate.setSupportedEmpty(false);
		if(initData.get("PARAM_CS034")!=null&&initData.get("PARAM_CS034") instanceof String){
			String cs034 = (String)initData.get("PARAM_CS034");
			this.comboFeeType.setRequired(Boolean.valueOf(cs034).booleanValue());
		}
		if(initData.get("PARAM_CS036")!=null&&initData.get("PARAM_CS036") instanceof String){
			String cs036 = (String)initData.get("PARAM_CS036");
			this.txtUsage.setRequired(Boolean.valueOf(cs036).booleanValue());
		}
		
		// �������������ݣ�δ���޸ģ�ֱ�ӹر�ʱ�����ֱ�����ʾ
		this.storeFields();
		this.initOldData(this.editData);
		
		if (getUIContext().get("isMultiApprove") != null) {
			this.actionEdit.setVisible(false);
			this.actionSplit.setVisible(false);
		}
		
		String cu = null;
		if (editData != null && editData.getCU() != null) {
			cu = editData.getCU().getId().toString();
		} else {
			cu = SysContext.getSysContext().getCurrentCtrlUnit().getId()
					.toString();
		}
		FDCClientUtils.setRespDeptF7(prmtuseDepartment, this, cu);
		
		//txtPayeeAccountBank ����¼���ı�
        ExtendParser parserAccountFrom = new ExtendParser(txtPayeeAccountBank);
        txtPayeeAccountBank.setCommitParser(parserAccountFrom);
        
		ExtendParser parserCity = new ExtendParser(txtrecCity);
		txtrecCity.setCommitParser(parserCity);
		
		ExtendParser parserProvince = new ExtendParser(prmtDesc);
		prmtDesc.setCommitParser(parserProvince);
		
		//��ʼ����Ӧ��F7
		String cuId = editData.getCU().getId().toString();
		FDCClientUtils.initSupplierF7(this, prmtPayee, cuId);
		
		if(STATUS_EDIT.equals(getOprtState())){
			if(prmtPayerAccount.getValue() instanceof AccountViewInfo){
				AccountViewInfo acc = (AccountViewInfo)prmtPayerAccount.getValue();
				if(acc.isIsBank()){
//					txtPayeeBank.setRequired(true);	
				}else{
					txtPayeeBank.setRequired(false);					
				}
			}
		}
		
		//���þ���
		setPrecision();
		
		setButtonStatus(this.getOprtState());
		
		amtInContractOri = FDCHelper.toBigDecimal(editData.getAddProjectAmt());
		if(prmtPayerAccountBank.getValue() != null && prmtPayerAccountBank.isEnabled()){
			prmtPayerAccount.setEnabled(false);
		}

		//�׹�����ͬ�б�
		initPartA();
		
		//���ȿ��ۼ����깤
		loadAllCompletePrjAmt();
		
		//���޽�
		loadQualityGuaranteAmt();
		//53������:�ڸ�������ı���ͬ���棬���տ��ʺš��տ����б�Ϊ�Ǳ�¼��Ŀ(��Ʊ�¼�������ͻ�Ҫ��Ǳ�¼) by hpw 2009-08-26
		txtPayeeBank.setRequired(false);
		resetConfirmBillEntrysPaidAmt(FDCHelper.toBigDecimal(editData.getAmount()));
		initMaterial();
		
		SelectorItemCollection selector = new SelectorItemCollection();
		selector.add("*");
		selector.add("ntType.*");
		prmtSettlementType.setSelectorCollection(selector);
		SettlementTypeInfo settType = (SettlementTypeInfo) prmtSettlementType
		.getData();
		if((STATUS_VIEW==this.getOprtState()||STATUS_FINDVIEW==this.getOprtState())
				&&this.editData.getBillStatus()==BillStatusEnum.AUDITED&&
				(settType!=null&&settType.getNtType()!=null&&NTTypeGroupEnum.PaymentCheque.equals(settType.getNtType()
						.getSuperGroup()))){
			this.actionChequeReimburse.setVisible(true);
			this.actionChequeReimburse.setEnabled(true);
			actionChequeReimburse.putValue(Action.SMALL_ICON, EASResource
					.getIcon("imgTbtn_cancelverification"));
		}
		if(this.getUIContext().get("ChequeReimBurse")!=null&&((Integer)this.getUIContext().get("ChequeReimBurse")).intValue()==1){
			initChequeReimburseStatus();
		}
		
		initFDCDepPlan();
		
		this.btnInputCollect.setVisible(false);
		this.actionClosePayReq.setVisible(false);
		this.actionCancelClosePayReq.setVisible(false);
		
		this.actionPre.setVisible(false);
		this.actionNext.setVisible(false);
		this.actionFirst.setVisible(false);
		this.actionLast.setVisible(false);
		
		this.actionNextPerson.setVisible(false);
	}
	
	String paramConPayPlanCtrl;

	/**
	 * ��ʼ���ƻ��ı��򣬽�ԭ����prmtFpItem����
	 * 
	 * @throws BOSException
	 * @throws EASBizException
	 */
	private void initFDCDepPlan() throws EASBizException, BOSException{
		// �鿴Ԥ��ͼ��
		menuItemViewBalance.setIcon(EASResource.getIcon("imgTbtn_fundcount"));
		btnViewBalance.setIcon(EASResource.getIcon("imgTbtn_fundcount"));
		
		//���ڸ������"�ƻ���Ŀ"��˵����������"ͨ����ͬ�¶ȸ���ƻ����ƺ�ͬ�������뼰����Ʋ���"
		//����ֵΪ"�ϸ����"��"��ʾ����"ʱ�����ֶ�ֵ�Զ��ɸ������뵥�����Ҳ����޸ģ�������Ϊ"������"ʱ��
		//ά����״��F7ȡԤ��ϵͳ�е�Ԥ����Ŀ
		String org = SysContext.getSysContext().getCurrentOrgUnit().getId().toString();
		paramConPayPlanCtrl = ParamManager.getParamValue(null, new ObjectUuidPK(
				org), "FDC325_CONTROLPAYREQUEST");
		if ("0".equals(paramConPayPlanCtrl)) {
			paramConPayPlanCtrl = "�ϸ����";
		} else if ("1".equals(paramConPayPlanCtrl)) {
			paramConPayPlanCtrl = "��ʾ����";
		} else {
			paramConPayPlanCtrl = "������";
		}
		if("�ϸ����".equals(paramConPayPlanCtrl) || "��ʾ����".equals(paramConPayPlanCtrl) ){
			contFpItem.setVisible(false);
			contPlanItem.setVisible(true);
			txtPlanItem.setEnabled(false);
			
			String conID = editData.getContractBillId();
			SelectorItemCollection sic = new SelectorItemCollection();
			Calendar cal = Calendar.getInstance();
			if (((new ContractWithoutTextInfo()).getBOSType()).equals(BOSUuid
					.read(conID).getType())) {
				sic.add("id");
				sic.add("bookedDate");
				sic.add("fdcDepConPlan.id");
				sic.add("fdcDepConPlan.id");
				sic.add("fdcDepConPlan.payMattersName");
				sic.add("fdcDepConPlan.head.deptment.name");
				ContractWithoutTextInfo con = ContractWithoutTextFactory
						.getRemoteInstance().getContractWithoutTextInfo(
								new ObjectUuidPK(conID), sic);
				if(con!=null&&con.getFdcDepConPlan()!=null){
					cal.setTime(con.getBookedDate());
					FDCDepConPayPlanNoContractInfo plan = con
							.getFdcDepConPlan();
					StringBuffer format = new StringBuffer();
					format.append("�޺�ͬ ");
					format.append(plan.getPayMattersName()).append(" ");
					format.append(cal.get(Calendar.YEAR));
					format.append("��");
					format.append(cal.get(Calendar.MONTH) + 1);
					format.append("�¸���ƻ�");
					txtPlanItem.setText(format.toString());
					txtPlanItem.setUserObject(plan);
				}
			} else {
				sic.add("id");
				sic.add("bookedDate");
				sic.add("planHasCon.id");
				sic.add("planHasCon.contractName");
				sic.add("planHasCon.head.deptment.name");
				sic.add("planUnCon.id");
				sic.add("planUnCon.unConName");
				sic.add("planUnCon.parent.deptment.name");
				String payRequestBillId = this.editData.getFdcPayReqID();
				PayRequestBillInfo payReq = PayRequestBillFactory
						.getRemoteInstance().getPayRequestBillInfo(
								new ObjectUuidPK(payRequestBillId), sic);
				cal.setTime(payReq.getBookedDate());
				if (payReq.getPlanHasCon() != null) {
					String contractName = payReq.getPlanHasCon()
							.getContractName();
					StringBuffer format = new StringBuffer();
					format.append(contractName).append(" ");
					format.append(cal.get(Calendar.YEAR));
					format.append("��");
					format.append(cal.get(Calendar.MONTH) + 1);
					format.append("�¸���ƻ�");
					txtPlanItem.setText(format.toString());
					txtPlanItem.setUserObject(payReq.getPlanHasCon());
				} else if (payReq.getPlanUnCon() != null) {
					String contractName = payReq.getPlanUnCon().getUnConName();
					StringBuffer format = new StringBuffer();
					format.append("��ǩ��");
					format.append(contractName).append(" ");
					format.append(cal.get(Calendar.YEAR));
					format.append("��");
					format.append(cal.get(Calendar.MONTH) + 1);
					format.append("�¸���ƻ�");
					txtPlanItem.setText(format.toString());
					txtPlanItem.setUserObject(payReq.getPlanUnCon());
				}
			}
		} else {
			contPlanItem.setVisible(false);
			contFpItem.setVisible(true);
			isFpItem =FDCDealFPOrBudgetItemUtil.getIsFpOrBg();
			if (isFpItem) {// ������ʾ�ƻ���Ŀ
				prmtFpItem.setQueryInfo("com.kingdee.eas.fm.fpl.FpItemQuery");
				
				Set set = new HashSet();
				set.add(FPItemDirectionEnum.OUTWARD_MOVEMENT);
				set.add(FPItemDirectionEnum.OTHER_VALUE);
	
				FilterInfo filter = new FilterInfo();
				filter.getFilterItems().add(
						new FilterItemInfo("direction", set, CompareType.INCLUDE));
	
				EntityViewInfo evi = new EntityViewInfo();
				evi.setFilter(filter);
				
				prmtFpItem.setEntityViewInfo(evi);
			}else{//������ʾԤ����Ŀ
				NewBgItemDialog bgItemSelect = new NewBgItemDialog(this);
				prmtFpItem.setSelector(bgItemSelect);
				prmtFpItem.setSelector(bgItemSelect);
			}
		}
	}
	
	public void actionChequeReimburse_actionPerformed(ActionEvent e)
			throws Exception {
		// TODO Auto-generated method stub
		super.actionChequeReimburse_actionPerformed(e);
		String tempOprState = getOprtState();
		setSave(false);
		// Ӧ�����绥��
		this.setOprtState(STATUS_EDIT);
		this.getUIContext().put("ChequeReimBurse", new Integer(1));
		IObjectValue val = (IObjectValue) getUIContext().get("CURRENT.VO");
		getUIContext().put("CURRENT.VO", null);
		try {
			setDataObject(val);
		} catch (Exception ex) {
			setOprtState(tempOprState);
			getUIContext().put("CURRENT.VO", val);
			throw ex;
		}

		unLockUI();

		// showMessageForStatus();
		setMessageText(getClassAlise() + " " + "Ʊ���");
		setIsShowTextOnly(true);
		showMessage();
		this.setUITitle(getClassAlise() + "-" + "�޸�");

		// lockUIComponent(false);
		// getActionManager().enableAllAction();
		initDataStatus();
		setDefaultFocused();

		initChequeReimburseStatus();
		
	}
	
	private void initChequeReimburseStatus(){
		actionSubmit.setEnabled(true);
		Component[] cmp=this.pnlPayBillDetail.getComponents();
		for(int i=0;i<cmp.length;i++){
			cmp[i].setEnabled(false);
		}
		cmp=this.pnlPayBillMaterial.getComponents();
		for(int i=0;i<cmp.length;i++){
			cmp[i].setEnabled(false);
		}
		cmp=this.pnlPayBillPartA.getComponents();
		for(int i=0;i<cmp.length;i++){
			cmp[i].setEnabled(false);
		}
		cmp=this.pnlPayBillEntry.getComponents();
		for(int i=0;i<cmp.length;i++){
			cmp[i].setEnabled(false);
		}
		cmp = this.pnlPayBillHeader.getComponents();
		for(int i=0;i<cmp.length;i++){
			cmp[i].setEnabled(false);
		}
		
		f7SettleNumber.setEnabled(true);
//		btnPayGroup.setVisible(false);
		ItemAction[] actions = new ItemAction[] { 
				this.actionSave,
				actionAddNew,
				actionRemove,
				actionEdit,
				actionAttachment,
				actionPre,
				actionNext,
				actionFirst,
				actionLast,
//				actionViewBdgBalance,
//				actionUntiApprove,
				actionAddLine,
				actionAntiAudit,
				actionViewBgBalance,
				actionTraceUp,
				actionTraceDown,
				actionWorkFlowG,
				actionCreateFrom,
				actionCreateTo,
//				actionDlFromSt,
				actionMultiapprove,
				actionViewDoProccess,
				actionAuditResult,
				actionNextPerson,
				actionVoucher,
				this.actionClosePayReq,
				this.actionCancelClosePayReq,
				this.actionSplit,
				this.actionChequeReimburse,
				this.actionRefresh,
				actionInsertLine, actionCopyLine, actionRemoveLine };
		FDCClientHelper.setActionEnable(actions, false);
	
}
	
	private EntityViewInfo getBizTypeEV(AccountBankInfo accBank) {
		EntityViewInfo ev = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		ev.setFilter(filter);
		Set set = new HashSet();
		set.add(new Integer(SettBizTypeEnum.PAYINSIDE_VALUE));
		set.add(new Integer(SettBizTypeEnum.PAYOUTSIDE_VALUE));
//		set.add(new Integer(SettBizTypeEnum.PAYINSIDEDIF_VALUE));
		filter.getFilterItems().add(
				new FilterItemInfo("type", set, CompareType.INCLUDE));
		if (accBank == null || accBank.getInnerAcct() == null) {
			filter.getFilterItems().add(new FilterItemInfo("id", null));

		}

		return ev;
	}

	/**
	 * �����¼���
	 */
	private void addBalanceListener() {
		FocusAdapter listener = new java.awt.event.FocusAdapter() {
			public void focusGained(java.awt.event.FocusEvent e) {
				try {
					AccountBankInfo acctBank = (AccountBankInfo) prmtPayerAccountBank
							.getValue();
					AccountViewInfo acctView = (AccountViewInfo) prmtPayerAccount
							.getValue();
					CurrencyInfo currency = (CurrencyInfo) prmtCurrency
							.getValue();

					setBalanceValue(txtDayaccount, acctView, acctBank, currency);
				} catch (EASBizException e1) {
					e1.printStackTrace();
					SysUtil.abort();
				} catch (BOSException e1) {
					e1.printStackTrace();
					SysUtil.abort();
				}
			}

			public void focusLost(FocusEvent arg0) {
				try {
					AccountBankInfo acctBank = (AccountBankInfo) prmtPayerAccountBank
							.getValue();
					AccountViewInfo acctView = (AccountViewInfo) prmtPayerAccount
							.getValue();
					CurrencyInfo currency = (CurrencyInfo) prmtCurrency
							.getValue();

					setBalanceValue(txtDayaccount, acctView, acctBank, currency);
				} catch (EASBizException e1) {
					e1.printStackTrace();
					SysUtil.abort();
				} catch (BOSException e1) {
					e1.printStackTrace();
					SysUtil.abort();
				}
			}
		};
		prmtCurrency.addFocusListener(listener);
		prmtPayerAccount.getEditor().addFocusListener(listener);
		prmtPayerAccountBank.getEditor().addFocusListener(listener);

	}

	// �ռ������Ļ���map
	private Map balanceMap = new HashMap();

	private void setBalanceValue(KDFormattedTextField txtDayaccount,
			AccountViewInfo acctView, AccountBankInfo acctBank,
			CurrencyInfo currency) throws EASBizException, BOSException {
		if (currency == null)
			return;
		BigDecimal balance = FMConstants.ZERO;
		ICashManagement iCash = CashManagementFactory.getRemoteInstance();

		// ȡ�����п�Ŀ���ռ������
		if (acctBank != null) {

			balance = (BigDecimal) balanceMap.get(acctBank.getId().toString()
					+ currency.getId().toString());

			if (balance == null) {
				balance = iCash.getBankData(currentCompany.getId().toString(),
						acctBank.getId().toString(), currency.getId()
								.toString(), new Date(), new Date(),
						CashDataTypeEnum.Y, true);

				if (balance != null) {
					balanceMap.put(acctBank.getId().toString()
							+ currency.getId().toString(), balance);
				}
			}
		} else if (acctView != null) {

			balance = (BigDecimal) balanceMap.get(acctView.getId().toString()
					+ currency.getId().toString());

			if (balance == null) {
				balance = iCash.getCashData(currentCompany.getId().toString(),
						acctView.getId().toString(), currency.getId()
								.toString(), new Date(), new Date(),
						CashDataTypeEnum.Y, true);

				if (balance != null) {
					balanceMap.put(acctView.getId().toString()
							+ currency.getId().toString(), balance);
				}
			}
		}

		/*balance = balance.setScale(currency.getPrecision(),
				BigDecimal.ROUND_HALF_UP);*/
		//update by renliang
		if(balance!=null){
			balance = balance.setScale(currency.getPrecision(),
					BigDecimal.ROUND_HALF_UP);
		}
		
		NumberFormat fm = NumberFormat.getInstance();
		fm.setGroupingUsed(true);
		if (!FMHelper.isEmpty(balance)) {
			txtDayaccount.setText(fm.format(balance));
		}
	}

	protected void txtAmount_dataChanged(DataChangeEvent e) throws Exception {
		super.txtAmount_dataChanged(e);
//		setAmount();
	}

	// ��ȡ��Ҫ��ʾ������
	public SelectorItemCollection getSelectors() {
		SelectorItemCollection sic = new SelectorItemCollection();//super.getSelectors();
		
		//��Դϵͳ		
		sic.add("sourceType"); 
		
		//		
		sic.add("fiVouchered"); 
		//�������
		sic.add("number");
		//��ͬ��
		sic.add("contractBillId");
		sic.add("contractNo");
		//����״̬
		sic.add("billStatus");
		//��������
		sic.add("payDate");
		//ҵ������
		sic.add("bizDate");	
		//�տ�����
		sic.add(new SelectorItemInfo("payeeBank"));
		//�տ��ʻ�
		sic.add(new SelectorItemInfo("payeeAccountBank"));
		
		//�ռ������
		sic.add(new SelectorItemInfo("dayaccount"));
		//����
		sic.add(new SelectorItemInfo("exchangeRate"));
		//ԭ�ҽ��
		sic.add(new SelectorItemInfo("amount"));
		//��λ�ҽ��
		sic.add(new SelectorItemInfo("localAmt"));
		//��ע
		sic.add(new SelectorItemInfo("summary"));
		//������
		sic.add(new SelectorItemInfo("accessoryAmt"));
		//����˵��
		sic.add(new SelectorItemInfo("description"));		
		//������
		sic.add(new SelectorItemInfo("conceit"));		
//		payeeID	������ID
		sic.add(new SelectorItemInfo("payeeID"));
//		payeeNumber	����������
		sic.add(new SelectorItemInfo("payeeNumber"));
//		payeeName	����������
		sic.add(new SelectorItemInfo("payeeName"));
//		payeeBank	�տ�����
		sic.add(new SelectorItemInfo("payeeBank"));
//		payeeAccountBank	�տ��˺�
		sic.add(new SelectorItemInfo("payeeAccountBank"));		
		//fdcPayReqNumber
		sic.add(new SelectorItemInfo("fdcPayReqNumber"));
//		urgentDegree	�����̶�
		sic.add(new SelectorItemInfo("urgentDegree"));
//		latestPrice	�������
		sic.add(new SelectorItemInfo("latestPrice"));
//		addProjectAmt	���ӹ��̿�(���ڷ���)
		sic.add(new SelectorItemInfo("addProjectAmt"));
//		changeAmt	���ǩ֤���
		sic.add(new SelectorItemInfo("changeAmt"));
//		payedAmt	�����뵥�Ѹ����
		sic.add(new SelectorItemInfo("payedAmt"));
		sic.add(new SelectorItemInfo("actPayAmt"));
//		payPartAMatlAmt	Ӧ�ۼ׹����Ͽ���ڷ�����
		sic.add(new SelectorItemInfo("payPartAMatlAmt"));
//		payTimes	�������
		sic.add(new SelectorItemInfo("payTimes"));
//		projectPriceInContract	��ͬ�ڹ��̿���ڷ�����
		sic.add(new SelectorItemInfo("projectPriceInContract"));
		// Ԥ����(�����ۺ����ٸ�)
		sic.add("prjPayEntry.lstAdvanceAllPaid");
		sic.add("prjPayEntry.lstAdvanceAllReq");
		sic.add("prjPayEntry.advance");
		sic.add("prjPayEntry.locAdvance");
		sic.add("prjPayEntry.advanceAllReq");
		sic.add("prjPayEntry.advanceAllPaid");
		sic.add("prjPayEntry.invoiceNumber");
		sic.add("prjPayEntry.invoiceAmt");
//		scheduleAmt	���ȿ�
		sic.add(new SelectorItemInfo("scheduleAmt"));
//		settleAmt	������
		sic.add(new SelectorItemInfo("settleAmt"));
//		curPlannedPayment	���ڼƻ�����
		sic.add(new SelectorItemInfo("curPlannedPayment"));
//		curBackPay	����Ƿ����
		sic.add(new SelectorItemInfo("curBackPay"));	
//		curReqPercent	��������%
		sic.add(new SelectorItemInfo("curReqPercent"));
//		allReqPercent	�ۼ�����%
		sic.add(new SelectorItemInfo("allReqPercent"));
//		imageSchedule	�������
		sic.add(new SelectorItemInfo("imageSchedule"));
//		curPaid	ʵ����ڷ�����
		sic.add(new SelectorItemInfo("curPaid"));
//		prjAllReqAmt	��ͬ�ڹ����ۼ�����
		sic.add(new SelectorItemInfo("prjAllReqAmt"));
//		addPrjAllReqAmt	���ӹ��̿��ۼ�����
		sic.add(new SelectorItemInfo("addPrjAllReqAmt"));
//		payPartAMatlAllReqAmt	�׹����ۼ������
		sic.add(new SelectorItemInfo("payPartAMatlAllReqAmt"));
//		lstPrjAllReqAmt	��ͬ�ڹ��̿������ۼ�����
		sic.add(new SelectorItemInfo("lstAddPrjAllReqAmt"));
//		lstAddPrjAllReqAmt	���ӹ��̿������ۼ�
		sic.add(new SelectorItemInfo("lstAddPrjAllReqAmt"));
//		lstAMatlAllReqAmt	�׹��������ۼ�����
		sic.add(new SelectorItemInfo("lstAMatlAllReqAmt"));
//		actFdcPayeeName	���ز�ʵ���տλȫ��
		sic.add(new SelectorItemInfo("actFdcPayeeName"));
//		fdcPayReqNumber	���ز��������뵥���ݱ��
		sic.add(new SelectorItemInfo("fdcPayReqNumber"));
//		lstPrjAllPaidAmt	���ں�ͬ���ۼ�ʵ��
		sic.add(new SelectorItemInfo("lstPrjAllPaidAmt"));
		//		lstAddPrjAllPaidAmt	���ӹ��̿������ۼ�ʵ��
		sic.add(new SelectorItemInfo("lstAddPrjAllPaidAmt"));
//		lstAMatlAllPaidAmt	�׹��������ۼ�ʵ��
		sic.add(new SelectorItemInfo("lstAMatlAllPaidAmt"));
//		fdcPayReqID	�������뵥ID
		sic.add(new SelectorItemInfo("fdcPayReqID"));
//		isEmergency	�Ƿ�Ӽ�
		sic.add(new SelectorItemInfo("isEmergency"));
		//capitalAmount ��д���
		sic.add(new SelectorItemInfo("capitalAmount"));
		//�����
		sic.add(new SelectorItemInfo("settlementNumber"));

		//ʡ/��
		sic.add(new SelectorItemInfo("recCity"));
		sic.add(new SelectorItemInfo("recProvince"));
		//ͬ�����
		sic.add(new SelectorItemInfo("isDifferPlace"));
		//��Ҫ֧��
		sic.add(new SelectorItemInfo("isNeedPay"));
		//��;
		sic.add(new SelectorItemInfo("usage"));
		
		sic.add(new SelectorItemInfo("payeeType.id"));
		sic.add(new SelectorItemInfo("payeeID"));
		sic.add(new SelectorItemInfo("payeeNumber"));
		sic.add(new SelectorItemInfo("payeeName"));
		
		sic.add(new SelectorItemInfo("CU.id"));
		sic.add(new SelectorItemInfo("CU.name"));
		sic.add(new SelectorItemInfo("CU.number"));
		//��˾
		sic.add(new SelectorItemInfo("company.id"));
		sic.add(new SelectorItemInfo("company.name"));
		sic.add(new SelectorItemInfo("company.number"));

		//�����˾
		sic.add(new SelectorItemInfo("agentPayCompany.id"));
		sic.add(new SelectorItemInfo("agentPayCompany.name"));
		sic.add(new SelectorItemInfo("agentPayCompany.number"));
		
		//��������
		sic.add("fdcPayType.number");
		sic.add("fdcPayType.name");
		sic.add("fdcPayType.payType.id");
		
		//�ÿ��
		sic.add(new SelectorItemInfo("useDepartment.number"));
		sic.add(new SelectorItemInfo("useDepartment.name"));

		//������Ŀ
		sic.add(new SelectorItemInfo("curProject.id"));
		sic.add(new SelectorItemInfo("curProject.name"));
		sic.add(new SelectorItemInfo("curProject.number"));
		sic.add(new SelectorItemInfo("curProject.longNumber"));
		sic.add(new SelectorItemInfo("curProject.displayName"));
		sic.add(new SelectorItemInfo("curProject.fullOrgUnit.id"));
		sic.add(new SelectorItemInfo("curProject.fullOrgUnit.name"));
		sic.add(new SelectorItemInfo("curProject.fullOrgUnit.number"));


		//�տ���� payeeArea
		sic.add("payeeArea.number");
		sic.add("payeeArea.name");
		//�տλȫ��
		sic.add("fdcPayeeName.number");
		sic.add("fdcPayeeName.name");		
		//�������
		sic.add(new SelectorItemInfo("feeType.number"));
		sic.add(new SelectorItemInfo("feeType.name"));		
		//�����Ŀ
		sic.add(new SelectorItemInfo("payerAccount.id"));
		sic.add(new SelectorItemInfo("payerAccount.name"));
		sic.add(new SelectorItemInfo("payerAccount.number"));
		sic.add(new SelectorItemInfo("payerAccount.longNumber"));
		sic.add(new SelectorItemInfo("payerAccount.longName"));		
		//��������
		sic.add(new SelectorItemInfo("payerBank.id"));
		sic.add(new SelectorItemInfo("payerBank.name"));
		sic.add(new SelectorItemInfo("payerBank.number"));
		sic.add(new SelectorItemInfo("payerBank.longNumber"));
		//�����˺�
		sic.add(new SelectorItemInfo("payerAccountBank.name"));
		sic.add(new SelectorItemInfo("payerAccountBank.number"));	
		sic.add(new SelectorItemInfo("payerAccountBank.bankaccountnumber"));
		sic.add(new SelectorItemInfo("payerAccountBank.isByCurrency"));
		sic.add(new SelectorItemInfo("payerAccountBank.currency.id"));
		sic.add(new SelectorItemInfo("payerAccountBank.bank.id"));
		sic.add(new SelectorItemInfo("payerAccountBank.bank.name"));
		sic.add(new SelectorItemInfo("payerAccountBank.bank.number"));
		sic.add(new SelectorItemInfo("payerAccountBank.bank.longNumber"));
		sic.add(new SelectorItemInfo("payerAccountBank.company.id"));
		
		//���ز�ʵ���տλȫ��
		sic.add(new SelectorItemInfo("actFdcPayeeName.id"));
		sic.add(new SelectorItemInfo("actFdcPayeeName.name"));
		sic.add(new SelectorItemInfo("actFdcPayeeName.number"));
		//���㷽ʽ
		sic.add(new SelectorItemInfo("settlementType.id"));
		sic.add(new SelectorItemInfo("settlementType.name"));
		sic.add(new SelectorItemInfo("settlementType.number"));
		
		// sic.add(new SelectorItemInfo("settlementType.ntType.*"));
		sic.add(new SelectorItemInfo("settlementType.ntType.id"));
		sic.add(new SelectorItemInfo("settlementType.ntType.group"));
		sic.add(new SelectorItemInfo("settlementType.ntType.isUsed"));
		sic.add(new SelectorItemInfo("settlementType.ntType.isRecNote"));
		sic.add(new SelectorItemInfo("settlementType.ntType.name"));
		sic.add(new SelectorItemInfo("settlementType.ntType.number"));
		sic.add(new SelectorItemInfo("settlementType.ntType.superGroup"));
		
		//֧Ʊ
		sic.add(new SelectorItemInfo("cheque.*"));
		//�ұ�
		sic.add(new SelectorItemInfo("currency.id"));
		sic.add(new SelectorItemInfo("currency.name"));
		sic.add(new SelectorItemInfo("currency.number"));

		//�ƻ���Ŀ
		sic.add(new SelectorItemInfo("fpItem.number"));
		sic.add(new SelectorItemInfo("fpItem.name"));
		
		//ҵ������
		sic.add(new SelectorItemInfo("bizType.type"));
		sic.add(new SelectorItemInfo("bizType.name"));
		sic.add(new SelectorItemInfo("bizType.number"));

		//�Ƶ���
		sic.add(new SelectorItemInfo("creator.id"));
		sic.add(new SelectorItemInfo("creator.number"));
		sic.add(new SelectorItemInfo("creator.name"));
		
		sic.add(new SelectorItemInfo("createTime"));
		//�����
		sic.add(new SelectorItemInfo("auditor.id"));
		sic.add(new SelectorItemInfo("auditor.number"));
		sic.add(new SelectorItemInfo("auditor.name"));
		sic.add(new SelectorItemInfo("auditDate"));

		//����
		sic.add(new SelectorItemInfo("cashier.id"));
		sic.add(new SelectorItemInfo("cashier.number"));
		sic.add(new SelectorItemInfo("cashier.name"));
		//���
		sic.add(new SelectorItemInfo("accountant.id"));
		sic.add(new SelectorItemInfo("accountant.number"));
		sic.add(new SelectorItemInfo("accountant.name"));
		
		//�ƻ���Ŀ
		sic.add(new SelectorItemInfo("entries.fpItem.number"));
		sic.add(new SelectorItemInfo("entries.fpItem.name"));
		sic.add(new SelectorItemInfo("entries.amount"));
		sic.add(new SelectorItemInfo("entries.localAmt"));
		sic.add(new SelectorItemInfo("entries.actualAmt"));
		sic.add(new SelectorItemInfo("entries.actualLocAmt"));
		
		//Ԥ����Ŀ
		sic.add("outBgItemId");
		sic.add("outBgItemNumber");
		sic.add("outBgItemName");
		sic.add("lastUpdateTime");
		
//		sic.add(new SelectorItemInfo("contractBill.id"));
//		sic.add(new SelectorItemInfo("contractBill.number"));
//		sic.add(new SelectorItemInfo("contractBill.name"));

		sic.add(new SelectorItemInfo("oppAccount.id"));
		sic.add(new SelectorItemInfo("oppAccount.name"));
		sic.add(new SelectorItemInfo("oppAccount.number"));
		sic.add(new SelectorItemInfo("oppAccount.simpleName"));
		sic.add(new SelectorItemInfo("oppAccount.longNumber"));
		sic.add(new SelectorItemInfo("oppAccount.displayName"));
		sic.add(new SelectorItemInfo("oppAccount.longName"));
		sic.add("CU.name");
		sic.add("CU.number");
		
		sic.add("payBillType.*");
		
		sic.add("entries.*");
		
		
		sic.add("approver.*");
		
		sic.add(new SelectorItemInfo("isDifBank"));
	    sic.add(new SelectorItemInfo("FRecCountry.*"));
		return sic;
	}

	public IUIObject getInstance(Map uiContext) {		
		// �鿴ʱ��ȡinfo
		PaymentBillInfo billInfo = (PaymentBillInfo) uiContext
				.get(UIContext.INIT_DATAOBJECT);
		// ���������ʱ��ȡinfo
		if (billInfo == null && uiContext.get("DATAOBJECTS") != null) {
			billInfo = (PaymentBillInfo) ((Map) uiContext.get("DATAOBJECTS"))
					.get("billInfo");
		}
		// �������տ�ʱ��ȡinfo
		if (billInfo == null && uiContext.get(UIContext.ID) != null) {
			String id = null;
			if (uiContext.get(UIContext.ID) instanceof BOSUuid) {
				id = ((BOSUuid) uiContext.get(UIContext.ID)).toString();
			} else if (uiContext.get(UIContext.ID) instanceof String) {
				id = (String) uiContext.get(UIContext.ID);
			} else {
				id = uiContext.get(UIContext.ID).toString();
			}
			IObjectPK pk = new ObjectUuidPK(id);
			try {
				billInfo = PaymentBillFactory.getRemoteInstance()
						.getPaymentBillInfo(pk,this.getSelectors());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		if (billInfo != null
				&& (SourceTypeEnum.FDC.equals(billInfo.getSourceType()))) {
			if (Boolean.TRUE.equals(uiContext.get("GetInstanceOver"))) {
				this.editData = billInfo;
				return this;
			}
			// || SourceTypeEnum.FDC.equals(billInfo.getSourceSysType()))) {
			try {
				uiContext.put("GetInstanceOver", Boolean.TRUE);
				PaymentBillEditUI pay = (PaymentBillEditUI) UIFactoryHelper.initUIObject(PaymentBillEditUI.class.getName(), uiContext,
						null, this.oprtState);
//				PaymentBillEditUI pay = new PaymentBillEditUI();
				pay.editData = billInfo;
				return pay;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return super.getInstance(uiContext);
	}

	private void sortPanel() {
		tbpPaymentBill.removeAll();
		tbpPaymentBill.add(pnlPayBillHeader, resHelper
				.getString("pnlPayBillHeader.constraints"));
		tbpPaymentBill.add(pnlPayBillEntry, resHelper
				.getString("pnlPayBillEntry.constraints"));
		//�׹�����ͬ�б�ҳǩ
		if(PayReqUtils.isContractBill(editData.getContractBillId()) && isPartA){
			tbpPaymentBill.add(pnlPayBillPartA, resHelper.getString("pnlPayBillPartA.constraints"));
		}
		tbpPaymentBill.add(pnlPayBillDetail, resHelper.getString("pnlPayBillDetail.constraints"));
		tbpPaymentBill.add(pnlPayBillMaterial,resHelper.getString("pnlPayBillMaterial.constraints"));
	}

	protected void updateButtonStatus() {
		// super.updateButtonStatus();
		// ��������������֯����������ɾ����
		if (!SysContext.getSysContext().getCurrentFIUnit().isIsBizUnit()) {
			// if
			// (!SysContext.getSysContext().getCurrentCostUnit().isIsBizUnit())
			// {
			actionAddNew.setEnabled(false);
			actionEdit.setEnabled(false);
			actionRemove.setEnabled(false);
			actionAddNew.setVisible(false);
			actionEdit.setVisible(false);
			actionRemove.setVisible(false);
			actionAttachment.setEnabled(false);
			actionAddLine.setEnabled(false);
			actionInsertLine.setEnabled(false);
			actionRemoveLine.setEnabled(false);
			actionCreateFrom.setEnabled(false);
		}
		if ((!SysContext.getSysContext().getCurrentOrgUnit()
				.isIsCompanyOrgUnit())
				&& (SysContext.getSysContext().getCurrentOrgUnit()
						.isIsCostOrgUnit())) {
			actionCreateFrom.setEnabled(false);
			actionRemove.setEnabled(false);
		} else {
			setOprtStateByBillStatus();
		}
		
	}

	/**
	 * Object����ת��ΪBigDecimal����
	 */
	private BigDecimal toBigDecimal(Object obj) {
		if (obj == null) {
			return BigDecimal.valueOf(0);
		} else {
			if (obj instanceof BigDecimal) {
				return (BigDecimal) obj;
			} else {
				String str = obj.toString().trim();
				if (str.matches("\\.?\\d*") || str.matches("\\d+\\.?\\d*")) {
					return new BigDecimal(str);
				}
			}
		}
		return BigDecimal.valueOf(0);
	}

	// ��̬���±������
	private void updateDynamicValue() {

		// �ϴ��ۼ�����
		// �õ����϶�Ӧԭ�������뵥���ۼ����������
		PayRequestBillCollection payReqCol =(PayRequestBillCollection) initData.get("PayRequestBillCollection"); ;
		Iterator iter = payReqCol.iterator();
		if (iter.hasNext()) {
			PayRequestBillInfo info = (PayRequestBillInfo) iter.next();
			editData.setLstPrjAllReqAmt(info.getPrjAllReqAmt());
			editData.setLstAddPrjAllReqAmt(info.getAddPrjAllReqAmt());
			editData.setLstAMatlAllReqAmt(info
							.getPayPartAMatlAllReqAmt());
			editData.setLstAMatlAllPaidAmt(info.getLstAMatlAllPaidAmt());
			((ICell) bindCellMap
							.get(PaymentBillContants.LSTPRJALLREQAMT))
							.setValue(info.getPrjAllReqAmt());
			((ICell) bindCellMap
							.get(PaymentBillContants.LSTADDPRJALLREQAMT))
							.setValue(info.getAddPrjAllReqAmt());
			((ICell) bindCellMap
							.get(PaymentBillContants.LSTAMATLALLREQAMT))
							.setValue(info.getPayPartAMatlAllReqAmt());
			((ICell) bindCellMap
					.get(PaymentBillContants.LSTAMATLALLPAIDAMT))
					.setValue(info.getLstAMatlAllPaidAmt());
		}
			
		// ���ø������Ϊ��ͬ�ĸ������ �Ӹ���й���
		PaymentBillCollection payCol = (PaymentBillCollection)initData.get("PaymentBillCollection");	
		editData.setPayTimes(payCol.size());
		
		if(bindCellMap.get(PaymentBillContants.PAYTIMES) != null){
			((ICell) bindCellMap.get(PaymentBillContants.PAYTIMES)).setValue(payCol
					.size()+ "");
		}
		
		//����������Ϊ0 �ֹ��趨�������� ʵ��Ϊ0
		if(payCol.size() == 0){
//			editData.setLstPrjAllReqAmt(FDCConstants.ZERO);
			editData.setLstPrjAllPaidAmt(FDCConstants.ZERO);
			editData.setLstAddPrjAllPaidAmt(FDCConstants.ZERO);
			editData.setLstAMatlAllPaidAmt(FDCConstants.ZERO);
//			((ICell) bindCellMap
//					.get(PaymentBillContants.LSTPRJALLREQAMT))
//					.setValue(FDCConstants.ZERO);
			((ICell)bindCellMap
					.get(PaymentBillContants.LSTPRJALLPAIDAMT))
					.setValue(FDCConstants.ZERO);
			((ICell) bindCellMap
					.get(PaymentBillContants.LSTADDPRJALLPAIDAMT))
					.setValue(FDCConstants.ZERO);
			((ICell) bindCellMap
					.get(PaymentBillContants.LSTAMATLALLPAIDAMT))
					.setValue(FDCConstants.ZERO);
		}
		// �������뵥���Ѹ���� by sxhong 2007/09/29
		BigDecimal amount = FDCHelper.ZERO;
		for (int i = 0; i < payCol.size(); i++) {
			if (payCol.get(i).getFdcPayReqID().equals(editData.getFdcPayReqID())) {
				amount = amount.add(payCol.get(i).getAmount());
			}
		}
		if(bindCellMap.get(PaymentBillContants.PAYEDAMT) != null){
			((ICell) bindCellMap.get(PaymentBillContants.PAYEDAMT)).setValue(amount);
		}
	}

	// ���ݸ����ʺ��Զ��õ���������
	protected void prmtPayerAccountBank_dataChanged(DataChangeEvent e)
			throws Exception {
		Object newValue = e.getNewValue();
		if (e.getOldValue() != null && e.getOldValue().equals(newValue)) {
			return;
		}
		boolean isNotNull = e.getNewValue() != null;
		if (!isNotNull) {
			// ���п�Ŀ��Ҫ���
			prmtPayerAccount.setValue(null);
			prmtPayerBank.setValue(null);
			prmtSettleBizType.setData(null);
//			prmtSettleBizType.setEnabled(false);
			prmtPayerAccount.setEnabled(true);
			txtPayeeBank.setRequired(false);
			return;
		}
		if (newValue instanceof AccountBankInfo) {
			AccountBankInfo acc = (AccountBankInfo) prmtPayerAccountBank
					.getValue();
			IBank iBank = BankFactory.getRemoteInstance();
			BankInfo bankInfo = iBank.getBankInfo(new ObjectUuidPK(acc
					.getBank().getId()));
			if (acc != null) {
				prmtPayerAccount.setEnabled(false);
				prmtPayerBank.setValue(bankInfo);
				prmtPayerAccount.setValue(acc.getAccount());
				if(((AccountViewInfo)acc.getAccount()).isIsBank()==true){
//					txtPayeeBank.setRequired(true);
				}else{
					txtPayeeBank.setRequired(false);
				}
			} else {
				logger.info("�ò�����Ӧ�̵Ĳ�����֯");
			}
			
//			if(acc.getInnerAcct()==null){
//				prmtSettleBizType.setValue(null);
//				
//				comboPayeeType.setSelectedItem(this.supplierAssitType);
//			}else{
//				comboPayeeType.setSelectedItem(innerAccountActType);
//			}
		}else{
			comboPayeeType.setSelectedItem(this.supplierAssitType);
		}
		
		
	}

	protected void prmtPayerAccountBank_willCommit(CommitEvent e)
			throws Exception {
//		AccountViewInfo account = (AccountViewInfo) prmtPayerAccount.getValue();
//		if (account != null) {
//			String accountId = account.getId().toString();
//			setPayerAcctBankEvi(accountId, false);
//		} else {
			setPayerAcctBankEvi(null, false);
//		}
	}

	private void setPayerAcctBankEvi(String accountId, boolean isCapitalUp) {

		Object payee = prmtPayee.getValue();

			String companyId = currentCompany.getId().toString();
	
			prmtPayerAccountBank.getQueryAgent().resetRuntimeEntityView();
	
			EntityViewInfo evi = CasRecPayHandler.getAccountBankEvi(companyId,
					accountId);
			/**
			 * �����ʽ��ϻ��������ͣ������˻�����ѡ�����뻧���ʵ��˻����ʽ��ϻ��������ͣ�����ѡ��������֧���ʵ��˻���
			 */
			if (isCapitalUp) {
				evi.getFilter().getFilterItems().add(
						new FilterItemInfo("accountType", new Integer(
								AccountType.PAYIN_VALUE)));
				evi.getFilter().getFilterItems().add(
						new FilterItemInfo("notOutPay", FMConstants.TRUE));
			} else {
				evi.getFilter().getFilterItems().add(
						new FilterItemInfo("accountType", new Integer(
								AccountType.PAYIN_VALUE), CompareType.NOTEQUALS));
				evi.getFilter().getFilterItems().add(
						new FilterItemInfo("notOutPay", FMConstants.FALSE));
			}
	
			prmtPayerAccountBank.setEntityViewInfo(evi);
			SelectorItemCollection selectors = new SelectorItemCollection();
			selectors.add("*");
			selectors.add("bank.id");
			selectors.add("bank.name");
			selectors.add("bank.number");
			selectors.add("bank.longNumber");
			selectors.add("currency.id");
			selectors.add("account.number");
			selectors.add("account.name");
			selectors.add("account.isBank");
			prmtPayerAccountBank.setSelectorCollection(selectors);
		
	}

	protected void prmtPayerAccountBank_willShow(SelectorEvent e)
			throws Exception {
//		AccountViewInfo account = (AccountViewInfo) prmtPayerAccount.getValue();
		boolean istranup = false;
//		if (account != null) {
//			String accountId = account.getId().toString();
//			setPayerAcctBankEvi(accountId, istranup);
//		} else {
			setPayerAcctBankEvi(null, istranup);
//		}
	}

	public void actionAntiAudit_actionPerformed(ActionEvent e) throws Exception {
		isMoving = true;
		String id = getSelectBOID();
		if (id != null&&!checkPaymentAllSplit) {
			
			FDCClientUtils.checkBillInWorkflow(this, id);
			
			FilterInfo filterSett = new FilterInfo();
			filterSett.getFilterItems().add(
					new FilterItemInfo("paymentBill.id", id));
			filterSett.getFilterItems().add(
					new FilterItemInfo("state", FDCBillStateEnum.INVALID_VALUE,
							CompareType.NOTEQUALS));
			boolean hasSettleSplit = false;
			if (PaymentSplitFactory.getRemoteInstance().exists(filterSett)) {
				hasSettleSplit = true;
			}
			if (hasSettleSplit) {
				MsgBox.showWarning("����Ѿ����,���ܷ�����!");
				SysUtil.abort();
				return;
			}			
			Set idSet = new HashSet();
			idSet.add(id);
			PaymentBillFactory.getRemoteInstance().antiAudit(idSet);
		}
		FDCClientUtils.showOprtOK(this);
		super.actionAntiAudit_actionPerformed(e);
		syncDataFromDB();
		setOprtStateByBillStatus();
	}

	// �ύ֮ǰ��У��
	protected void verifyBeforeSubmit() throws Exception {
		if (this.editData.getFdcPayReqNumber() == null) {
			MsgBox.showWarning(this, PaymentBillClientUtils.getRes("must"));
			SysUtil.abort();
		}
		FDCClientVerifyHelper.verifyEmpty(this, prmtPayBillType);
		//У���¼��
		//�����ո������տ��������޾�����Ϣ��ʾ ����һ��
		if(this.comboFeeType.isRequired()){
			if(comboFeeType.getValue()==null){
				MsgBox.showWarning(this,"�ո������Ϊ�գ�");
				SysUtil.abort();
			}
		}
		if(this.prmtPayee.isRequired()){
			if(prmtPayee.getValue()==null){
				MsgBox.showWarning(this,"�տ������Ʋ���Ϊ�գ�");
				SysUtil.abort();
			}
		}
		if(this.txtUsage.isRequired()){
			if(txtUsage.getText()==null){
				MsgBox.showWarning(this,"��;����Ϊ�գ�");
				SysUtil.abort();
			}
		}
		
//		FDCClientVerifyHelper.verifyEmpty(this, comboFeeType);
		//�����¼����Required true,�Ǳ�¼Ϊfalse�����ﲻ��ע��
		FDCClientVerifyHelper.verifyEmpty(this, prmtPayerAccount);
		FDCClientVerifyHelper.verifyEmpty(this, prmtCurrency);
		FDCClientVerifyHelper.verifyUIControlEmpty(this);
		
		if(PayReqUtils.isContractBill(editData.getContractBillId()) && isPartA && isPartAMaterialCon){
			if (!isReferenceToPartAConfirm()) // �й�������ҪУ��
				paymentBillTableHelper.verifySubmit();
		}
	}

	protected void prmtPayerAccount_dataChanged(DataChangeEvent e)
			throws Exception {
		//2009-1-12 �п����Ǵӡ�������״̬�µĵ����л��������桱״̬�µĵ��ݣ���ʱ��ĿΪ�գ�Ӧ���Ͽ�ֵ�ж�
		if(e.getNewValue() == null){
			prmtPayerAccountBank.setEnabled(true);
			return;
		}
		if(((AccountViewInfo)e.getNewValue()).isIsBank()==true){
//			txtPayeeBank.setRequired(true);
		}else{
			txtPayeeBank.setRequired(false);
		}
		//R110303-069����������ύ״̬���Ӧ�����޸ĸ����˺�
		if(prmtPayerAccountBank.getValue() == null && prmtPayerAccount.isEnabled()){
			if (BillStatusEnum.SAVE.equals(editData.getBillStatus()) || BillStatusEnum.SUBMIT.equals(editData.getBillStatus())) {
				prmtPayerAccountBank.setEnabled(true);	
			} else {
				prmtPayerAccountBank.setEnabled(false);		
			}
		}
		
		if (OprtState.VIEW.equals(getOprtState())) {
			return;
		}
	}

	protected void prmtPayerAccount_willCommit(CommitEvent e) throws Exception {

		CurrencyInfo currencyInfo = (CurrencyInfo) prmtCurrency.getValue();
		if (currencyInfo == null) {
			e.setCanceled(true);
			return;
		}
		setPayerAcctEvi(currencyInfo);
	}

	protected void prmtPayerAccount_willShow(SelectorEvent e) throws Exception {

		CurrencyInfo currencyInfo = (CurrencyInfo) prmtCurrency.getValue();
		if (currencyInfo == null) {
			//�ұ�Ϊ��ʱ������ʾ
			FDCMsgBox.showWarning(this, "����ѡ��ұ�!");
			prmtPayerAccount.setValue(null);
			e.setCanceled(true);
			return;
		}
		setPayerAcctEvi(currencyInfo);
	}

	private void setPayerAcctEvi(CurrencyInfo currencyInfo)
			throws EASBizException, BOSException {
		String currencyId = currencyInfo.getId().toString();
		String companyId = currentCompany.getId().toString();
		String cuId = currentCompany.getCU().getId().toString();

		prmtPayerAccount.getQueryAgent().resetRuntimeEntityView();

		// ���ݸ��ʽȡ�ù�������
		EntityViewInfo treeevi = CasRecPayHandler.getAccountViewEvi(cuId,
				companyId, currencyId, false);

		AccountPromptBox opseelect = new AccountPromptBox(this, currentCompany,
				treeevi.getFilter(), false, true);
		prmtPayerAccount.setSelector(opseelect);
		EntityViewInfo evi = CasRecPayHandler.getAccountViewEvi(cuId,
				companyId, currencyId, true);

		prmtPayerAccount.setEntityViewInfo(evi);
	}

	public void actionSplit_actionPerformed(ActionEvent e) throws Exception {
		super.actionSplit_actionPerformed(e);
		String id = getSelectBOID();
		String contractId = editData.getContractBillId();
		if (id == null || contractId==null){
			return;
		}
		SelectorItemCollection selector = new SelectorItemCollection();
		selector.add("localAmt");
		PaymentBillInfo info = (PaymentBillInfo)getBizInterface().getValue(new ObjectUuidPK(id), selector);
		if(FDCHelper.toBigDecimal(info.getLocalAmt(),2).compareTo(FDCHelper.toBigDecimal(txtLocalAmt.getBigDecimalValue(),2))!=0){
			FDCMsgBox.showWarning(this, "�˸����Ӧ�ĸ������뵥���ڶ�θ�����ύ�����²�֣�");
			return;
		}
		Map param = new HashMap();
		param.put("isSimpleFinancial", Boolean.valueOf(isSimpleFinancial));
		param.put("isAdjustVourcherModel", Boolean.valueOf(isAdjustVourcherModel));
		PaymentSplitHelper.checkPaymentVouchered(id, contractId, param);
		
		
		AbstractSplitInvokeStrategy invokeStra = new PaymentSplitInvokeStrategy(
				id, this);
		invokeStra.invokeSplit();
	}

	protected void initWorkButton() {
		super.initWorkButton();
		btnSplit.setIcon(FDCClientHelper.ICON_SPLIT);
		menuItemSplit.setAccelerator(KeyStroke.getKeyStroke("alt shift S"));
		actionViewBgBalance.putValue(Action.SMALL_ICON, EASResource
				.getIcon("imgTbtn_check"));
		actionAddNew.setEnabled(false);
		actionAddLine.setEnabled(false);
		actionRemoveLine.setEnabled(false);
		actionInsertLine.setEnabled(false);
		txtAllCompletePrjAmt.setHorizontalAlignment(JTextField.RIGHT);
		txtQualityGuarante.setHorizontalAlignment(JTextField.RIGHT);
		txtInvoiceAmt.setHorizontalAlignment(JTextField.RIGHT);
		txtAllInvoiceAmt.setHorizontalAlignment(JTextField.RIGHT);
		//if(isSimpleFinancial || isAutoComplete || (isSeparate && isCostSplit)){
		if((isSeparate && isCostSplit) || isAutoComplete){
			contcompletePrjAmt.setVisible(false);
			contpaymentProportion.setVisible(false);
			contAllCompletePrjAmt.setVisible(false);
		}
		prmtCreator.setRequired(false);
		pkCreateTime.setRequired(false);
		actionApprove.putValue("SmallIcon", EASResource.getIcon("imgTbtn_auditing"));
	    actionUntiApprove.putValue("SmallIcon", EASResource.getIcon("imgTbtn_fauditing"));
	}

	/**
	 * �絥���Ѿ����,���޸ĵ��ݽ���ʱ�������ʾ���²��
	 */
	boolean amtWarned = false;

	protected void txtAmount_focusGained(FocusEvent e) throws Exception {
		// TODO Auto-generated method stub
		super.txtAmount_focusGained(e);

		if (STATUS_EDIT.equals(getOprtState()) && !amtWarned) {
			boolean isCostSplit = FDCSplitClientHelper.isBillSplited(
					getSelectBOID(), "T_FNC_PaymentSplit", "FPaymentBillID");
			boolean isNoCostSplit = FDCSplitClientHelper.isBillSplited(
					getSelectBOID(), "T_FNC_PaymentNoCostSplit",
					"FPaymentBillID");
			if (isCostSplit || isNoCostSplit) {
				MsgBox.showConfirm2(this, ContractClientUtils
						.getRes("spltedWarningForAmtChg"));
			}
			amtWarned = true;
		}
	}

	public void actionViewBgBalance_actionPerformed(ActionEvent e)
			throws Exception {
		storeFields();
		PaymentBillInfo info = editData;
		BgCtrlParamCollection paramColl = BgCtrlPaymentBillHandler
				.getParamColl(info);

		String boName = RecPayHelper.BO_NAME_PAY;
		FMClientHelper.viewBgBalance(this, boName, paramColl, info);
	}

	public void afterActionPerformed(ActionEvent e) {
		super.afterActionPerformed(e);
		
		if(initData==null){
			return ;
		}
		
		Object source = e.getSource();
		if (source == btnCreateFrom || source == menuItemCreateFrom) {
			if (txtNumber.isEnabled()) {
				txtNumber.requestFocusInWindow();
			} else {
				pkBizDate.requestFocusInWindow();
			}
		} else if (source == btnEdit || source == menuItemEdit) {
			prmtCompany.setEditable(false);
			prmtFdcPayType.setEditable(false);
			prmtuseDepartment.setEditable(false);
			//prmtPayee.setEditable(false);
//			prmtActFdcPayeeName.setEditable(false);
			prmtCreator.setEditable(false);
			prmtAuditor.setEditable(false);
			prmtCashier.setEditable(false);
			prmtAccountant.setEditable(false);
		}
		
		if (source == btnNext || source == menuItemNext 
				|| source == btnPre || source == menuItemPre
				|| source == btnFirst || source == menuItemFirst
				|| source == btnLast || source == menuItemLast) {
			// ������ǰ��kdtEntries���Լ���kdtable���
			Rectangle kdtRectangle = kdtEntries.getBounds();
			kdtEntries.setEnabled(false);
			kdtEntries.setVisible(false);
			kdtEntries = createPaymentBillTable();
			kdtEntries.setBounds(kdtRectangle);
			// pnlPayBillEntry.add(kdtEntries, null);
			
	
			pnlPayBillEntry.add(kdtEntries, new KDLayout.Constraints(10, 10, 850,
					450, KDLayout.Constraints.ANCHOR_TOP
							| KDLayout.Constraints.ANCHOR_BOTTOM_SCALE
							| KDLayout.Constraints.ANCHOR_LEFT_SCALE
							| KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
			
			getDetailTable().setEnabled(false);
			getDetailTable().setEditable(false);
			kdtEntries.getCell(4,5).getStyleAttributes().setLocked(true);
			setTableCellColorAndEdit(false) ;
			// ȥ������ƻ��ĸ�:
			int lastRowIdx = kdtEntries.getRowCount() - 1;
			for (int i = 4; i < kdtEntries.getColumnCount(); i++) {
				StyleAttributes styleAttributes = kdtEntries.getCell(lastRowIdx - 1, i)
						.getStyleAttributes();
				styleAttributes.setLocked(true);
				styleAttributes.setBackground(noEditColor);
			}
			kdtEntries.getCell(lastRowIdx - 1, 1).getStyleAttributes().setLocked(true);
			kdtEntries.getCell(lastRowIdx - 1, 1).getStyleAttributes().setBackground(noEditColor);
			
			kdtEntries.getCell(9,5).getStyleAttributes().setLocked(true);
			kdtEntries.getCell(lastRowIdx - 1,6).getStyleAttributes().setLocked(true);
//		}
		}
	}

	protected void prmtPayee_willShow(SelectorEvent e) throws Exception {
		if (comboPayeeType.getSelectedItem() == null
				|| comboPayeeType.getSelectedItem() instanceof FMSysDefinedEnum) {
			e.setCanceled(true);
			return;
		}
	}
	
	protected void prmtPayee_willCommit(SelectorEvent e) throws Exception {
		if (comboPayeeType.getSelectedItem() == null
				|| comboPayeeType.getSelectedItem() instanceof FMSysDefinedEnum) {
			e.setCanceled(true);
			return;
		}

	}
	
	private CustomerInfo getCustomer(Object objCust) {
		if (objCust instanceof CustomerInfo)
			return (CustomerInfo) objCust;
		else
			return null;
	}
	
	private SupplierInfo getSupplier(Object objCust) {
		if (objCust instanceof SupplierInfo)
			return (SupplierInfo) objCust;
		else
			return null;
	}
	
	protected void prmtPayee_dataChanged(DataChangeEvent e) throws Exception {

		Object payee = prmtPayee.getValue();
		if (payee instanceof SupplierInfo) {
			SupplierInfo supplier = (SupplierInfo) payee;
			// �����տ��˴����տ��к��˺�
			if (supplier != null) {
				
				SelectorItemCollection sic = super.getSelectors();
				sic.add(new SelectorItemInfo("*"));
				sic.add(new SelectorItemInfo("province.*"));
				sic.add(new SelectorItemInfo("city.*"));
			 
				SupplierInfo realSupplier=SupplierFactory.getRemoteInstance().getSupplierInfo(new ObjectUuidPK(supplier.getId()),sic);
				txtrecProvince.setData(realSupplier.getProvince());
				txtrecCity.setData(realSupplier.getCity());
				
				BOSUuid supplierid = supplier.getId();
				
				//��Ӧ�̵Ļ�ȡ			
				String supperid = supplierid.toString();			
				PayReqUtils.fillBank(editData,supperid,curProject.getCU().getId().toString());	
				txtPayeeAccountBank.setText(editData.getPayeeAccountBank());
				txtPayeeBank.setText(editData.getPayeeBank());
				
//				SupplierCompanyInfoInfo supplierCompany = SupplierFactory
//						.getRemoteInstance().getCompanyInfo(
//								new ObjectUuidPK(supplier.getId()),
//								new ObjectUuidPK(currentCompany.getId()));
//				if (supplierCompany != null) {
//
//					txtPayeeAccountBank.setText(supplierCompany
//							.getBankAccount());
//					txtPayeeBank.setText(supplierCompany.getBankName());
//					
//				} else {
//					txtPayeeAccountBank.setText(null);
//					txtPayeeBank.setText(null);
//				}

			}

		} else if (payee instanceof CustomerInfo) {

			CustomerInfo cust = this.getCustomer(prmtPayee.getData());

			// �����տ��˴����տ��к��˺�
			if (cust != null) {
				
				SelectorItemCollection sic = super.getSelectors();
				sic.add(new SelectorItemInfo("*"));
				sic.add(new SelectorItemInfo("province.*"));
				sic.add(new SelectorItemInfo("city.*"));
			 
				CustomerInfo realCust=CustomerFactory.getRemoteInstance().getCustomerInfo(new ObjectUuidPK(cust.getId()),sic);
				txtrecProvince.setData(realCust.getProvince());
				txtrecCity.setData(realCust.getCity());
				
				CustomerCompanyInfoInfo custCompany = CustomerFactory
						.getRemoteInstance().getCustomerCompanyInfo(
								new ObjectUuidPK(cust.getId()),
								new ObjectUuidPK(currentCompany.getId()));
				if (custCompany != null) {
					// txtPayeeAccountBank.setText(custCompany.getBankAccount());
					txtPayeeAccountBank.setText(custCompany.getBankAccount());
					txtPayeeBank.setText(custCompany.getBank());
				} else {
					txtPayeeAccountBank.setText(null);
					txtPayeeBank.setText(null);
				}

			}

		} else if (payee instanceof InnerAccountInfo) {

			InnerAccountInfo info = (InnerAccountInfo) payee;

			// �����տ��˴����տ��к��˺�
			if (info != null) {
				EntityViewInfo ev = new EntityViewInfo();
				FilterInfo filter = new FilterInfo();
				filter.getFilterItems().add(
						new FilterItemInfo("innerAcct.id", info.getId()
								.toString()));
				ev.setFilter(filter);
				SelectorItemCollection selector = ev.getSelector();
				selector.add("*");
				selector.add("bank.*");

				AccountBankCollection accountBankColl = AccountBankFactory
						.getRemoteInstance().getAccountBankCollection(ev);
				if (accountBankColl != null && accountBankColl.size() == 1) {
					AccountBankInfo accountBankInfo = accountBankColl.get(0);
					txtPayeeBank.setText(accountBankInfo.getBank().getName());
					txtPayeeAccountBank.setText(accountBankInfo
							.getBankAccountNumber());
//					txtPayeeAcctName.setText(accountBankInfo.getName());

				}
			}

		} else if (payee instanceof AccountBankInfo) {

			AccountBankInfo info = (AccountBankInfo) payee;

			// �����տ��˴����տ��к��˺�
			if (info != null) {
				SelectorItemCollection selector = new SelectorItemCollection();
				selector.add("*");
				selector.add("bank.*");

				info = AccountBankFactory.getRemoteInstance()
						.getAccountBankInfo(new ObjectUuidPK(info.getId()),
								selector);
				txtPayeeBank.setText(info.getBank().getName());
				txtPayeeAccountBank.setText(info.getBankAccountNumber());
//				txtPayeeAcctName.setText(info.getName());

			}
		}

	}
	
	protected void prmtPayeeArea_willShow(SelectorEvent e) throws Exception {
		setPayeeAreaEvi();
	}

	protected void prmtPayeeArea_willCommit(CommitEvent e) throws Exception {
		setPayeeAreaEvi();
	}

	private void setPayeeAreaEvi() {
		AccountBankInfo bankaccount = (AccountBankInfo) prmtPayerAccountBank
				.getValue();
		BankInterfaceTypeEnum type = null;
		if (bankaccount != null) {
			type = bankaccount.getBankInterfaceType();

		}
		EntityViewInfo ev = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		if (type != null) {
			filter.getFilterItems().add(
					new FilterItemInfo("bankInterfaceType", new Integer(type
							.getValue())));
		} else {
			filter.getFilterItems().add(new FilterItemInfo("id", null));

		}
		ev.setFilter(filter);
		prmtPayeeArea.setEntityViewInfo(ev);
		prmtPayeeArea.getQueryAgent().resetRuntimeEntityView();

	}

	protected void verifyInput(ActionEvent e) throws Exception {
		checkIsAllInvoicAmtMoreThanConLatestPrice(editData, FDCHelper.toBigDecimal(this.txtInvoiceAmt.getBigDecimalValue()));
		if (!prmtSettleBizType.isEnabled()) {
			prmtSettleBizType.setRequired(false);
		}
		
		SettlementTypeInfo settType1 = (SettlementTypeInfo) prmtSettlementType
				.getData();
		if (settType1 != null) {
			if (settType1.getNtType() != null) {//
				if (NTTypeGroupEnum.PaymentCheque.equals(settType1.getNtType()
						.getSuperGroup())) {// R100130-010
					Object oCheque = f7SettleNumber.getData();
					if (oCheque instanceof ChequeInfo) {
						ChequeInfo cheque = (ChequeInfo) oCheque;
						if (cheque.getId() != null) {
							AccountBankInfo accountBankInfo = (AccountBankInfo) prmtPayerAccountBank
									.getValue();
							if (accountBankInfo != null
									&& (cheque.getBankAcct() == null || !cheque
											.getBankAcct().getId().equals(
													accountBankInfo.getId()))
									&& (cheque.getBank() == null || !cheque
											.getBank().getId().equals(
													accountBankInfo.getBank()
															.getId()))) {
								MsgBox.showWarning("�����˻��򸶿�������Ʊ�ݲ������뷵���޸ģ�");
								SysUtil.abort();
							}
						}
					}
					/*
					 * String chequeNumber=f7SettleNumber.getText();
					 * if(chequeNumber==null||chequeNumber.trim().equals("")){
					 * }else{ FilterInfo filter=new FilterInfo();
					 * filter.getFilterItems().add(new
					 * FilterItemInfo("number",chequeNumber)); EntityViewInfo
					 * evi=new EntityViewInfo();
					 * 
					 * FilterInfo filter2 = new FilterInfo();
					 * filter2.getFilterItems().add( new
					 * FilterItemInfo("keepCompany.id", currentCompany.getId()
					 * .toString())); filter2.getFilterItems().add( new
					 * FilterItemInfo("company.id", currentCompany.getId()
					 * .toString())); filter2.getFilterItems().add(new
					 * FilterItemInfo("state", new
					 * Integer(ChequeStatusEnum.NEW_VALUE)));
					 * filter2.setMaskString("(#0 OR #1) AND #2");
					 * 
					 * filter.mergeFilter(filter2, "AND");
					 * evi.setFilter(filter); ChequeCollection
					 * chequeColl=ChequeFactory
					 * .getRemoteInstance().getChequeCollection(evi);
					 * if(chequeColl==null||chequeColl.size()==0){
					 * MsgBox.showWarning("֧Ʊ�ѱ�ʹ�ã���ѡ������֧Ʊ��"); SysUtil.abort(); }
					 * }
					 */
				}
			}
		}

		// �����ʻ��ұ��븶��ұ��Ч��
		if (prmtPayerAccountBank.getValue() != null) {

			CurrencyInfo Crinfo = (CurrencyInfo) prmtCurrency.getValue();
			AccountBankInfo acctBankInfo = (AccountBankInfo) prmtPayerAccountBank
					.getValue();
			CurrencyInfo acctBankCrinfo = (CurrencyInfo) acctBankInfo
					.getCurrency();
			if (acctBankInfo.isIsByCurrency()) {
				if (!acctBankCrinfo.getId().equals(Crinfo.getId())) {

					MsgBox.showWarning(this, "�����˻��ĵ�һ�ұ𲻵��ڸ���ұ�");
					SysUtil.abort();
				}
			}

		}

		// ��������˻����������нӿڣ����տ��ˡ��տ����С��տ��˻���ʡ�ݡ���/����һΪ��ʱ���������ʱ�����û�¼�룬
		// ����û�ȷ�ϲ��ύ���󸶿���Լ������档
		AccountBankInfo acctBankInfo = (AccountBankInfo) prmtPayerAccountBank
				.getValue();
		if (acctBankInfo != null && acctBankInfo.isIsSetBankInterface()) {

			if (prmtPayee.getValue() == null
					|| FMHelper.isEmpty(txtPayeeBank.getText())
					|| FMHelper.isEmpty(txtPayeeAccountBank.getText())
					||( prmtPayeeArea.getValue() == null&&txtrecCity.getValue()==null) && prmtPayeeArea.getValue() == null || prmtFRecCountry.getValue() == null) {

				String msg = EASResource.getString(CasRecPayHandler.srcPath,
						"acctBankHasInterface");
				int result = MsgBox.showConfirm2(this, msg);
				if (result == MsgBox.CANCEL) {
					SysUtil.abort();
				}
			}
		}

		// �����㷽ʽ��Ϊ��ʱ,�����Ŀ�͸����ʻ�����¼һ��
		if (prmtSettlementType.getValue() != null) {
			if (prmtPayerAccount.getValue() == null
					&& prmtPayerAccountBank.getValue() == null) {
				String msg = EASResource.getString(CasRecPayHandler.srcPath,
						"must_have_payer_AccountView_or_Accountbank");
				/***
				 * ��������������Ŀ�����Բ��ñ�¼��
				 * ����ʾע��
				 */
//				MsgBox.showError(this, msg);
//				SysUtil.abort();
			}
		}
		 FMClientVerifyHelper.verifyAEqualsB(this, prmtCompany, prmtAgentCompany);
		 Map agentRelatedProps = new HashMap();
		 agentRelatedProps.put("ui", this);
		 agentRelatedProps.put("editData", editData);
		 // �����ո���initDataStatus
		//�����Ϊ�����տֱ�ӷ���
			if(prmtAgentCompany.getValue()==null){
				return ;
			}
			
			//�������ĸ����˻����ⲿ�����˺š��ҹ������ڲ��˻���ֻ��ѡ������տ����͵�ҵ������
			AccountBankInfo acctBank=(AccountBankInfo) prmtPayerAccountBank.getValue();
			
			if(acctBank!=null&&acctBank.getInnerAcct()!=null&&prmtSettleBizType.getValue()!=null){
				SettBizTypeInfo bizTypeInfo = (SettBizTypeInfo) prmtSettleBizType.getData();
				if(!SettBizTypeEnum.PAYOUTSIDE.equals(bizTypeInfo.getType())
						&& !SettBizTypeEnum.LINKPAY.equals(bizTypeInfo.getType())){
//					MsgBox.showError(CasRecPayHandler.srcPath, "payerAcctBankRelInnerAcct");
					MsgBox.showError("�������ĸ����˻����ⲿ�����˺š��ҹ������ڲ��˻���ֻ��ѡ������տ����͵�ҵ������");
					SysUtil.abort();
				}
			}
	}

	protected void prmtSettlementType_dataChanged(DataChangeEvent e)
			throws Exception {
		super.prmtSettlementType_dataChanged(e);
		Object objNew = e.getNewValue();
		if (objNew == null)
			return;
		if (objNew instanceof SettlementTypeInfo
				&& ((SettlementTypeInfo) objNew).getId().toString().equals(
						"e09a62cd-00fd-1000-e000-0b32c0a8100dE96B2B8E")) {
			// ���㷽ʽΪ���������м��˺�Ϊ�Ǳ�¼
//			txtPayeeBank.setRequired(false);
//			txtPayeeBank.repaint();
			//txtPayeeAccountBank.setRequired(false);
//			txtPayeeAccountBank.repaint();
		} else {
//			txtPayeeBank.setRequired(true);
//			txtPayeeBank.repaint();
			//txtPayeeAccountBank.setRequired(true);
//			txtPayeeAccountBank.repaint();
		}
		
		SettlementTypeInfo settType = (SettlementTypeInfo) prmtSettlementType
				.getData();
		if (settType != null) {
			if (settType.getNtType() != null) {//
				if (NTTypeGroupEnum.PaymentCheque.equals(settType.getNtType()
						.getSuperGroup())
					) {// R100130-010
					f7SettleNumber.setQueryInfo("com.kingdee.eas.fm.nt.app.ChequeQuery");
					if (f7SettleNumber.isEnabledMultiSelection()) {
						String tempSettle = null;
						if (f7SettleNumber.getData() instanceof String) {
							tempSettle = (String) f7SettleNumber.getData();
							ChequeInfo cheque = new ChequeInfo();
							cheque.setNumber(tempSettle);
							f7SettleNumber.setData(new Object[] { cheque });
						}
						f7SettleNumber.setEnabledMultiSelection(false);
						if (f7SettleNumber.getData() instanceof ChequeInfo) {
							f7SettleNumber.setData(tempSettle);
						}
					}
				} else {
					f7SettleNumber.setEnabledMultiSelection(true);
				}
			}
}
	}
	

    /**
     * output txtPayeeAccountBank_willCommit method
     */
    protected void txtPayeeAccountBank_willCommit(com.kingdee.bos.ctrl.swing.event.CommitEvent e) throws Exception
    {
		Object payee = prmtPayee.getValue();
		if (payee instanceof SupplierInfo) {
			SupplierInfo supply=(SupplierInfo)payee;
			initSupplierCompanyInfoBank(supply,txtPayeeAccountBank);
		}
		else if(payee instanceof CustomerInfo){
			CustomerInfo customer=(CustomerInfo)payee;
			initCustomerCompanyInfoBank(customer,txtPayeeAccountBank);
		}
		else {
			//initPayeeAcctBankASAcctBank();
			setPayerAcctBankEvi(null, false);
		}
    	
    }
    
	private void initCustomerCompanyInfoBank(CustomerInfo customer, KDBizPromptBox txtPayeeAccountBank) {
		txtPayeeAccountBank.setQueryInfo("com.kingdee.eas.basedata.master.cssp.app.F7CustomerCompanyInfoBankQuery");
		txtPayeeAccountBank.setHasCUDefaultFilter(false);
		EntityViewInfo view=new EntityViewInfo();
		FilterInfo filter=new FilterInfo();
		if(customer!=null) {
			filter.getFilterItems().add(new FilterItemInfo("customer.id",customer.getId()));
		}
		filter.getFilterItems().add(new FilterItemInfo("companyOrgUnit.id",currentCompany.getId()));
		view.setFilter(filter);
		txtPayeeAccountBank.setEntityViewInfo(view);
		txtPayeeAccountBank.setEditFormat("$bankAccount$");
		txtPayeeAccountBank.setDisplayFormat("$bankAccount$");
		txtPayeeAccountBank.getQueryAgent().resetRuntimeEntityView();
		
	}

	private void initSupplierCompanyInfoBank(SupplierInfo supply, KDBizPromptBox txtPayeeAccountBank) {
		txtPayeeAccountBank.setQueryInfo("com.kingdee.eas.basedata.master.cssp.app.F7SupplierCompanyInfoBankQuery");
		txtPayeeAccountBank.setHasCUDefaultFilter(false);
		EntityViewInfo view=new EntityViewInfo();
		FilterInfo filter=new FilterInfo();
		if(supply!=null) {
			filter.getFilterItems().add(new FilterItemInfo("supplier.id",supply.getId()));
		}
		filter.getFilterItems().add(new FilterItemInfo("companyOrgUnit.id",currentCompany.getId()));
		view.setFilter(filter);
		txtPayeeAccountBank.setEntityViewInfo(view);
		txtPayeeAccountBank.setEditFormat("$bankAccount$");
		txtPayeeAccountBank.setDisplayFormat("$bankAccount$");
		txtPayeeAccountBank.getQueryAgent().resetRuntimeEntityView();
		
	}

    /**
     * output txtPayeeAccountBank_willShow method
     */
    protected void txtPayeeAccountBank_willShow(com.kingdee.bos.ctrl.swing.event.SelectorEvent e) throws Exception
    {
    	
		
		//����ǹ�Ӧ�������޸�
		Object  obj = comboPayeeType.getSelectedItem();
		if(obj instanceof AsstActTypeInfo ){
			AsstActTypeInfo asstActTypeInfo = (AsstActTypeInfo)obj;
			if ("provider".equalsIgnoreCase(asstActTypeInfo.getAsstHGAttribute())){
				
				String payeeId = editData.getPayeeID();
				if(payeeId!=null){
					SupplierInfo supply = new SupplierInfo();
					supply.setId(BOSUuid.read(payeeId));
				
					initSupplierCompanyInfoBank(supply,txtPayeeAccountBank);
				}
			}else {
				//initPayeeAcctBankASAcctBank();
				setPayerAcctBankEvi(null, true);
			}
		}
		
//		Object payee = prmtPayee.getValue();
//		if (payee instanceof SupplierInfo) {
//			SupplierInfo supply=(SupplierInfo)payee;
//			initSupplierCompanyInfoBank(supply,txtPayeeAccountBank);
//		}
//		else if(payee instanceof CustomerInfo){
//			CustomerInfo customer=(CustomerInfo)payee;
//			initCustomerCompanyInfoBank(customer,txtPayeeAccountBank);
//		}
//		else {
//			//initPayeeAcctBankASAcctBank();
//			setPayerAcctBankEvi(null, true);
//		}
//    	
    }

    
    
    /**
     * output txtPayeeAccountBank_dataChanged method
     */
    protected void txtPayeeAccountBank_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
    {
		if (e.getNewValue() != null && !e.getNewValue().equals(e.getOldValue())
				&& e.getNewValue() instanceof AccountBankInfo) {
			AccountBankInfo acctbank = (AccountBankInfo) e.getNewValue();
			editData.setPayeeAccountBankO(acctbank);
			if (acctbank.getBank() != null) {
				SelectorItemCollection selector = new SelectorItemCollection();
				selector.add("id");
//				selector.add("company.*");
				selector.add("bank.number");
				selector.add("bank.name");
				AccountBankInfo bank = AccountBankFactory.getRemoteInstance()
						.getAccountBankInfo(new ObjectUuidPK(acctbank.getId()),
								selector);
//				if (!type.getId().toString().equals(
//						PaymentBillTypeInfo.TRANS_UP)) {
					txtPayeeBank.setText(bank.getBank().toString());

//				}
			}

		} else if (e.getNewValue() != null && !e.getNewValue().equals(e.getOldValue())
				&& e.getNewValue() instanceof SupplierCompanyBankInfo) {

			SupplierCompanyBankInfo accountbank = (SupplierCompanyBankInfo)e.getNewValue();
			//editData.setPayeeAccountBankO(accountbank.geta);
			txtPayeeBank.setText(accountbank.getBank());
			
		} else if (e.getNewValue() == null) {
			editData.setPayeeAccountBankO(null);
			txtPayeeBank.setText(null);
		}
    }

	private void checkContractSplitState() {
		String contractBillId = editData.getContractBillId();
		if (contractBillId == null) {
			contractBillId = (String) getUIContext().get("contractBillId");
		}
		if (contractBillId != null) {
			if (!ContractClientUtils.getContractSplitState(contractBillId)) {
				MsgBox.showWarning(this, FDCSplitClientHelper
						.getRes("conNotSplited"));
				SysUtil.abort();
			}
		}
	}

	protected void prmtSettleBizType_dataChanged(DataChangeEvent e) throws Exception {
		
		SettBizTypeInfo bizTypeInfo = (SettBizTypeInfo) prmtSettleBizType.getValue();

		if (bizTypeInfo != null) {			
			if (SettBizTypeEnum.PAYINSIDE.equals(bizTypeInfo.getType())) {// Ĭ���տ�������δ�ڲ��ʻ�	
				if (FMHelper.isEmpty(prmtPayee.getData())) {
					if (innerAccountActType != null) {
						FMClientHelper.setSelectObject(comboPayeeType,innerAccountActType);
					}
				}
			}
		}else{
			if (supplierAssitType != null) {
				FMClientHelper.setSelectObject(comboPayeeType,supplierAssitType);
			}
		}
		
		//FMClientHelper.setSelectObject(comboPayeeType,innerAccountActType);
	}
	
	protected void prmtSettleBizType_willCommit(CommitEvent e) throws Exception {
		super.prmtSettleBizType_willCommit(e);
		AccountBankInfo accBank = (AccountBankInfo) prmtPayerAccountBank
				.getData();
		prmtSettleBizType.setEntityViewInfo(getBizTypeEV(accBank));
		prmtSettleBizType.getQueryAgent().resetRuntimeEntityView();
	}
	
	protected void prmtSettleBizType_willShow(SelectorEvent e) throws Exception {
		super.prmtSettleBizType_willShow(e);
		AccountBankInfo accBank = (AccountBankInfo) prmtPayerAccountBank
				.getData();
		prmtSettleBizType.setEntityViewInfo(getBizTypeEV(accBank));
		prmtSettleBizType.getQueryAgent().resetRuntimeEntityView();
	}
	
    protected void initListener()
    {
    	//super.initListener();
    }
    
    public void checkModified() throws Exception {    	
    	super.checkModified() ;
    }
    
    protected boolean isUseMainMenuAsTitle(){
    	return false;
    }
    
    protected void kdtPartA_editStopped(KDTEditEvent e) throws Exception {
    	super.kdtPartA_editStopped(e);
    	paymentBillTableHelper.kdtPartA_editStopped(e);
    }
    
    /**
     * �����ڼ׹�����ͬ�б�ʱ��У����������Ƿ�ı�.<p>
     */
    public boolean isModify() {
    	
    	if(PayReqUtils.isContractBill(editData.getContractBillId()) && isPartA && isPartAMaterialCon){
    		return paymentBillTableHelper.isModify()?true:super.isModify();
    	}
    	try {
			if (editData != null && editData.getId() != null) {
				//ɾ�������ݿ����Ѳ����ڣ�ֱ�ӷ���false by hpw 2010-06-25
				FilterInfo filter = new FilterInfo();
				filter.getFilterItems().add(new FilterItemInfo("id", editData.getId().toString()));
				boolean isExist = getBizInterface().exists(filter);
				if (!isExist) {
					return false;
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated method stub
			e.printStackTrace();
		}
    	return super.isModify();
    }

    /**
     * ���������ʻ��ͬ�ڹ��̿���仯ʱ�����¼��㱾��
     */
    private void setProjectPriceInContract(){
    	Object object = bindCellMap
				.get(PaymentBillContants.PROJECTPRICEINCONTRACTORI);
		if (object instanceof ICell) {
			ICell cell = (ICell) object;
			if (cell.getValue() != null) {
				BigDecimal originalAmount = FDCHelper.toBigDecimal(cell
						.getValue());
				BigDecimal amount = originalAmount.multiply(FDCHelper
						.toBigDecimal(txtExchangeRate.getBigDecimalValue()));
				
				if(this.editData.getFdcPayReqID()!=null&&prmtCurrency.getValue()!=null){
					SelectorItemCollection sel=new SelectorItemCollection();
					sel.add("currency.id");
					sel.add("originalAmount");
					sel.add("amount");
					
					try {
						PayRequestBillInfo	pay = PayRequestBillFactory.getRemoteInstance().getPayRequestBillInfo(new ObjectUuidPK(this.editData.getFdcPayReqID()),sel);
						KDTable table = getDetailTable();
						CurrencyInfo currency=(CurrencyInfo)prmtCurrency.getValue();
						BigDecimal localamount =FDCHelper.ZERO;
						BigDecimal exchangeRate = toBigDecimal(txtExchangeRate.getNumberValue());
						if(!pay.getCurrency().getId().toString().equals(currency.getId().toString())){
							amount=pay.getAmount();
						}
						
					} catch (EASBizException e) {
						e.printStackTrace();
					} catch (BOSException e) {
						e.printStackTrace();
					}
				}
				getDetailTable().getCell(cell.getRowIndex(),
						cell.getColumnIndex() + 1).setValue(amount);
				amtInContractOri = originalAmount;
				editData.setAddProjectAmt(originalAmount);//����Ϊʲô���治��߳?
				editData.setProjectPriceInContract(amount);
			}else{
				getDetailTable().getCell(cell.getRowIndex(),
						cell.getColumnIndex() + 1).setValue(null);
				editData.setAddProjectAmt(null);//����Ϊʲô���治��߳?
				editData.setProjectPriceInContract(null);
			}
			BigDecimal paidAmt = FDCHelper.toBigDecimal(cell.getValue());
			resetConfirmBillEntrysPaidAmt(paidAmt);
		}
    }

	private void resetConfirmBillEntrysPaidAmt(BigDecimal paidAmt) {
		if(this.initData.containsKey("PayRequestBillConfirmEntryCollection")){
			PayRequestBillConfirmEntryCollection colls = (PayRequestBillConfirmEntryCollection)initData.get("PayRequestBillConfirmEntryCollection");
			for(int i=0;i<colls.size();i++){
				PayRequestBillConfirmEntryInfo info = colls.get(i);
				if(paidAmt.compareTo(FDCHelper.toBigDecimal(info.getReqAmount()))>0){
					info.setPaidAmount(info.getReqAmount());
				}else{
					info.setPaidAmount(paidAmt);
				}
				paidAmt = paidAmt.subtract(FDCHelper.toBigDecimal(info.getPaidAmount()));
				if(!info.containsKey("notIncludeThisPaidAmt")){
					BigDecimal notIncludeThisPaidAmt = FDCHelper.toBigDecimal(info.getConfirmBill().getPaidAmt()).subtract(FDCHelper.toBigDecimal(info.getPaidAmount()));
					info.put("notIncludeThisPaidAmt", notIncludeThisPaidAmt);
				}
				BigDecimal confirmPaidAmt = FDCHelper.toBigDecimal(info.get("notIncludeThisPaidAmt")).add(info.getPaidAmount());
				info.getConfirmBill().setPaidAmt(confirmPaidAmt);
				if(paidAmt.compareTo(FDCHelper.ZERO)<=0)
					break;
			}
		}
	}
    /**
     * ���������ʻ�Ԥ����ԭ�ҷ����仯ʱ�����¼��㱾��
     */
    private void setAdvance(){
    	Object object = bindCellMap
				.get(PaymentBillContants.ADVANCE);
		if (object instanceof ICell) {
			ICell cell = (ICell) object;
			if (cell.getValue() != null) {
				BigDecimal originalAmount = FDCHelper.toBigDecimal(cell
						.getValue());
				BigDecimal amount = originalAmount.multiply(FDCHelper
						.toBigDecimal(txtExchangeRate.getBigDecimalValue()));
				getDetailTable().getCell(cell.getRowIndex(),
						cell.getColumnIndex() + 1).setValue(amount);
				//������ύʱ������editdata
				if(editData.getPrjPayEntry()==null){
					editData.setPrjPayEntry(new PaymentPrjPayEntryInfo());
				}
				editData.getPrjPayEntry().setAdvance(originalAmount);
				editData.getPrjPayEntry().setLocAdvance(amount);
			}
		}
    }
    
    protected void kdtEntries_editStopped(KDTEditEvent e) throws Exception {
    	int rowIndex = 4;
		int colIndex = 4;
		if (e.getRowIndex() == rowIndex
				&& e.getColIndex() == colIndex) {
			setProjectPriceInContract();
			setAmount();
		}
		if (e.getValue() != null && e.getRowIndex() == rowIndex+1
				&& e.getColIndex() == colIndex) {
			if(this.isAdvance())
				setAdvance();
		}
		this.initMaterial();
    }
    public void initMaterial() throws EASBizException, BOSException{
    	if(this.initData.containsKey("PayRequestBillConfirmEntryCollection")){
    		PayRequestBillConfirmEntryCollection mcBills = (PayRequestBillConfirmEntryCollection)initData.get("PayRequestBillConfirmEntryCollection");
    		if (mcBills!=null&&mcBills.size()>0){
        		paymentBillTableHelper.loadMaterialData(mcBills);
        	}else{
        		this.tbpPaymentBill.remove(this.pnlPayBillMaterial);
        	}
    	}
    	
    }
    
    /**
     * �������׹�ҳǩ��ʼ��
     * @author pengwei_hou Date: 2008-12-06
     * @throws EASBizException
     * @throws BOSException
     */
    public void initPartA() throws EASBizException, BOSException{
    	
    	if (PayReqUtils.isContractBill(editData.getContractBillId())) {

			ContractBillInfo contractBillInfo = ContractBillFactory.getRemoteInstance().getContractBillInfo(
					"select isPartAMaterialCon, hasSettled where id = '" + editData.getContractBillId() + "'");
			
			isPartAMaterialCon = contractBillInfo.isIsPartAMaterialCon();
			hasSettled = contractBillInfo.isHasSettled();
			// �׹��Ĳ���Ϊtrueʱ
			if (isPartA && isPartAMaterialCon) {
				kdtPartA.checkParsed();
				paymentBillTableHelper.setPartATable();
				paymentBillTableHelper.setPartAMainContractF7(editData);
				paymentBillTableHelper.loadPartAData();
				paymentBillTableHelper.addTotalRow();
				paymentBillTableHelper.sumTable();
				paymentBillTableHelper.initCtn(ctnPartAEntrys);
				paymentBillTableHelper.setPartAButtonStatus(getOprtState());
				
				// ��Ȼ�Ǽ׹��ĺ�ͬ��������������뵥û�й���������ȷ�ϵ�ʱ��ҲҪ���ؼ׹�ҳǩ
				// �μ��������� R101203-220 Added By Owen_wen 2011-01-14
				if (this.initData.containsKey("PayRequestBillConfirmEntryCollection")) {
					if (isReferenceToPartAConfirm()) {
						this.tbpPaymentBill.remove(this.pnlPayBillPartA);
					}
				}
			} else {
				this.tbpPaymentBill.remove(this.pnlPayBillPartA);
			}
		}
    }

	/**
	 * �׹��ĺ�ͬ���жϸ������뵥��û�й���������ȷ�ϵ�ʱ
	 * 
	 * @return true û�й�����false ����������ȷ�ϵ�
	 */
	private boolean isReferenceToPartAConfirm() {
		PayRequestBillConfirmEntryCollection mcBills = (PayRequestBillConfirmEntryCollection) initData.get("PayRequestBillConfirmEntryCollection");
		return (mcBills != null && mcBills.size() == 0);
	}

	/**
	 * ��������ͬ�����и������͵����Ϊ���ȿ�ĸ������뵥������״̬���������깤���ۼ�ֵ��
	 * 
	 * @author pengwei_hou Date: 2008-12-07
	 * @throws BOSException
	 */
    private void loadAllCompletePrjAmt() throws BOSException{
//    	String paymentType = editData.getFdcPayType().getPayType().getId().toString();
//    	String progressID = TypeInfo.progressID;
//    	if(!paymentType.equals(progressID)){
//    		return;
//    	}
    	BigDecimal allCompletePrjAmt = FDCHelper.ZERO;
    	EntityViewInfo view = new EntityViewInfo();
    	view.getSelector().add("completePrjAmt");
    	view.getSelector().add("state");
    	FilterInfo filter = new FilterInfo();
    	filter.getFilterItems().add(new FilterItemInfo("contractId", contractId));
    	// ����ģʽ�ģ����ȿ��ۼ����깤������������ֻ��Ҫ���ȿ��
    	// ����ģʽ���ȿ��ۼ����깤���������� by cassiel_peng 2010-05-31
    	if(isFinancial){
    		filter.getFilterItems().add(new FilterItemInfo("paymentType.payType.id", TypeInfo.progressID));
    	}
    	if(editData.getCreateTime()!=null){
    		
    		filter.getFilterItems().add(new FilterItemInfo("createTime",editData.getCreateTime(),CompareType.LESS_EQUALS));
    	}else{
    		filter.getFilterItems().add(new FilterItemInfo("createTime",new Timestamp(System.currentTimeMillis()),CompareType.LESS_EQUALS));
    	}
    	view.setFilter(filter);
    	PayRequestBillCollection payReqColl = PayRequestBillFactory.getRemoteInstance().getPayRequestBillCollection(view);
    	
    	if(payReqColl != null && payReqColl.size() > 0){
    		for(int i=0;i<payReqColl.size();i++){
    			PayRequestBillInfo info = payReqColl.get(i);
    			allCompletePrjAmt = allCompletePrjAmt.add(FDCHelper.toBigDecimal(info.getCompletePrjAmt()));
    		}
    	}
    	if(allCompletePrjAmt.compareTo(FDCHelper.ZERO)>0){
    		txtAllCompletePrjAmt.setValue(allCompletePrjAmt);
    	}
    }
    
    /**
     * ��������ͬ���ս���ǰ��Ϊ�ա���ͬ���ս����Ϊ���㵥�ϱ��޽�
     * @author pengwei_hou Date: 2008-12-07
     * @throws BOSException 
     * @throws EASBizException 
     */
    private void loadQualityGuaranteAmt() throws EASBizException, BOSException{
    	if(contractId==null) return;
    	if(hasSettled){
    		String sql = "select qualityGuarante where contractBill = '" + contractId +"' and IsFinalSettle = 1";
    		ContractSettlementBillInfo info = ContractSettlementBillFactory
    				.getRemoteInstance().getContractSettlementBillInfo(sql);
    		if(info!=null){
    			BigDecimal qualityGuaranteAmt = FDCHelper.toBigDecimal(info.getQualityGuarante());
    			if(qualityGuaranteAmt.compareTo(FDCHelper.ZERO)>0){
    				txtQualityGuarante.setText(FDCHelper.toBigDecimal(qualityGuaranteAmt, 2).toString());
    			}
    		}
    	}else{
    		txtQualityGuarante.setValue(null);
    	}
    }
    
    public Object getDeductMap(){
    	return initData.get("DeductOfPayReqBillCollection");
    }
    
    /**
     * Ԥ�������������+��ͬ
     * @return
     */
    protected boolean isAdvance(){
    	try {
			return isSeparate&&FDCUtils.isContractBill(null, editData.getContractBillId());
		} catch (EASBizException e) {
			this.handUIException(e);
		} catch (BOSException e) {
			this.handUIException(e);
		}
		return false;
    }
       
    
	private void afterPressDeleteButton(){
			
		kdtEntries.setBeforeAction(new BeforeActionListener(){
				public void beforeAction(BeforeActionEvent e)
				{
					if(BeforeActionEvent.ACTION_DELETE==e.getType()){
						
						for (int i = 0; i <kdtEntries.getSelectManager().size(); i++)
						{
							KDTSelectBlock block = kdtEntries.getSelectManager().get(i);
							for (int rowIndex = block.getBeginRow(); rowIndex <= block.getEndRow(); rowIndex++)
							{
								for(int colIndex=block.getBeginCol();colIndex<=block.getEndCol();colIndex++){
									kdtEntries.getCell(rowIndex, colIndex).setValue(null);
										KDTEditEvent event = new KDTEditEvent(kdtEntries, null, null, rowIndex, colIndex, true, 1);
										try {
											kdtEntries_editStopped(event);
										} catch (Exception e1) {
											e1.printStackTrace();
										}
								}
							}
							
						}
					}
				}
			});
		}
	
	
	/**
	 * ��Ʊ���仯ʱ���ۼƷ�Ʊ���ҲӦ��Ҫ����Ӧ�仯
	 * @author owen_wen
	 */
	protected void txtInvoiceAmt_dataChanged(DataChangeEvent e) throws Exception {
		BigDecimal oldValue = FDCHelper.toBigDecimal(e.getOldValue());
		BigDecimal newValue = FDCHelper.toBigDecimal(e.getNewValue());
		BigDecimal valueDiff = newValue.subtract(oldValue);
		this.txtAllInvoiceAmt.setValue(FDCHelper.toBigDecimal(txtAllInvoiceAmt.getBigDecimalValue()).add(valueDiff));
	}
	
	/**
	 * �鿴Ԥ��
	 * 
	 * @author 
	 * @date 2011-3-1
	 */
	public void actionViewBudget_actionPerformed(ActionEvent e)
			throws Exception {
		Map uiContext = getUIContext();
		Date bookDate = (Date) pkbookedDate.getValue();
		Date firstDay = BudgetViewUtils.getFirstDay(bookDate);
		Date lastDay = BudgetViewUtils.getLastDay(bookDate);
		String projectId = editData.getCurProject().getId().toString();
		
		String curProject = editData.getCurProject().getDisplayName();
		uiContext.put("curProject", curProject);
		Object obj = txtPlanItem.getUserObject();
		String curDept = "";
		if (obj == null) {
			MsgBox.showWarning(this, "��Ԥ�㣬���޷��鿴��");
			abort();
		} else if (obj instanceof FDCDepConPayPlanContractInfo) {
			curDept = ((FDCDepConPayPlanContractInfo) obj).getHead()
					.getDeptment().getName();
		} else if (obj instanceof FDCDepConPayPlanUnsettledConInfo) {
			curDept = ((FDCDepConPayPlanUnsettledConInfo) obj)
					.getParent().getDeptment().getName();
		} else if (obj instanceof FDCDepConPayPlanNoContractInfo) {
			curDept = ((FDCDepConPayPlanNoContractInfo) obj).getHead()
					.getDeptment().getName();
		}
		uiContext.put("curDept", curDept);
		String planMonth = DateTimeUtils.format(editData.getBizDate(), "yyyy-MM");
		uiContext.put("planMonth", planMonth);
		
		BigDecimal localBudget = getLocalBudget(firstDay, lastDay, projectId);
		uiContext.put("localBudget", localBudget);
		BigDecimal actPaied = getActPaied(firstDay, lastDay, projectId);
		uiContext.put("actPaied", actPaied);
		BigDecimal floatFund = getFloatFund(firstDay, lastDay, projectId);
		uiContext.put("floatFund", floatFund);
		BigDecimal bgBalance = localBudget.subtract(floatFund).subtract(actPaied);
		uiContext.put("bgBalance", bgBalance);
		
		BudgetViewUtils.showModelUI(uiContext);
	}
	
	/**
	 * �����Ѹ�
	 * 
	 * @param firstDay
	 * @param lastDay
	 * @param projectId
	 * @return
	 * @throws BOSException
	 * @throws SQLException
	 */
	private BigDecimal getActPaied(Date firstDay, Date lastDay, String projectId)
			throws BOSException, SQLException {
		BigDecimal actPaied = FDCHelper.ZERO;
		FDCSQLBuilder builderActPaied = new FDCSQLBuilder();
		builderActPaied.appendSql("select sum(FLocalAmount) as actPaied "
				+ "from t_cas_paymentbill  as pay where pay.FBillStatus = 15 "
				+ "and pay.FBizDate >= ");
		builderActPaied.appendParam(firstDay);
		builderActPaied.appendSql(" and pay.FBizDate <= ");
		builderActPaied.appendParam(lastDay);
		builderActPaied.appendSql(" and pay.FCurProjectID = ");
		builderActPaied.appendParam(projectId);
		builderActPaied.appendSql(" and pay.FContractBillID = ");
		builderActPaied.appendParam(editData.getContractBillId());
		if(editData.getId()!=null){
			builderActPaied.appendSql(" and FID != ");
			builderActPaied.appendParam(editData.getId().toString());
		}
		// �鿴״̬����ǰ�ģ�����޸�ʱ���ڵ�ǰ���ݵ�����޸�ʱ��֮ǰ��
		// ������������������޸�ʱ�䣿�ǰ�����ʱ��Ҳ����Ҳ���У�����ʱ��û��ʱ����
		if (STATUS_VIEW.equals(getOprtState())) {
			builderActPaied.appendSql(" and pay.FLastUpdateTime <= ");
			builderActPaied.appendSql("{ts '");
			builderActPaied.appendSql(FMConstants.FORMAT_TIME.format(editData
					.getLastUpdateTime()));
			builderActPaied.appendSql("' }");
			// �˴���ȷ��ʱ���룬���Բ������¾�
			// builderActPaied.appendParam(editData.getLastUpdateTime());
		}
		IRowSet rowSetActPaied = builderActPaied.executeQuery();
		while (rowSetActPaied.next()) {
			if (rowSetActPaied.getString("actPaied") != null) {
				actPaied = rowSetActPaied.getBigDecimal("actPaied");
			}
		}
		return actPaied;
	}
	
	/**
	 * ������;
	 * 
	 * @param firstDay
	 * @param lastDay
	 * @param projectId
	 * @return
	 * @throws BOSException
	 * @throws SQLException
	 */
	private BigDecimal getFloatFund(Date firstDay, Date lastDay,
			String projectId) throws BOSException, SQLException {
		BigDecimal floatFund = FDCHelper.ZERO;
		FDCSQLBuilder builderFloatFund = new FDCSQLBuilder();
		builderFloatFund.appendSql(" select sum(FLocalAmount) as floatFund ");
		builderFloatFund.appendSql(" from t_cas_paymentbill ");
		builderFloatFund.appendSql(" where FbillStatus = 11 ");
		builderFloatFund.appendSql(" and FBizDate >= ");
		builderFloatFund.appendParam(firstDay);
		builderFloatFund.appendSql(" and FBizDate <= ");
		builderFloatFund.appendParam(lastDay);
		builderFloatFund.appendSql(" and FCurProjectID = ");
		builderFloatFund.appendParam(projectId);
		builderFloatFund.appendSql(" and FContractBillID = ");
		builderFloatFund.appendParam(editData.getContractBillId());
		if(editData.getId()!=null){
			builderFloatFund.appendSql(" and FID != ");
			builderFloatFund.appendParam(editData.getId().toString());
		}
		// �鿴״̬����ǰ��
		if (STATUS_VIEW.equals(getOprtState())) {
			builderFloatFund.appendSql(" and FLastUpdateTime <= ");
			builderFloatFund.appendSql("{ts '");
			builderFloatFund.appendSql(FMConstants.FORMAT_TIME.format(editData
					.getLastUpdateTime()));
			builderFloatFund.appendSql("' }");
			// �˴���ȷ��ʱ���룬���Բ������¾�
//			builderFloatFund.appendParam(editData.getLastUpdateTime());
		}
		IRowSet rowSetFloatFund = builderFloatFund.executeQuery();
		while (rowSetFloatFund.next()) {
			if (rowSetFloatFund.getString("floatFund") != null) {
				floatFund = rowSetFloatFund.getBigDecimal("floatFund");
			}
		}
		return floatFund;
	}
	
	/**
	 * ȡԤ�㣬����ǩ����ͬԤ��ʹ�ǩ����ͬԤ��2����������ݽ���F7ֵ�ж�<br>
	 * �Ӻ�ͬ��������ƻ���ȡֵ
	 * 
	 * @param firstDay
	 * @param lastDay
	 * @param projectId
	 * @return
	 * @throws BOSException
	 * @throws SQLException
	 */
	private BigDecimal getLocalBudget(Date firstDay, Date lastDay,
			String projectId) throws BOSException, SQLException {
		BigDecimal localBudget = FDCHelper.ZERO;
		Object plan = txtPlanItem.getUserObject();
		FDCSQLBuilder builder = new FDCSQLBuilder();
		if (plan != null && plan instanceof FDCDepConPayPlanContractInfo) {
			builder
					.appendSql("select admin.FName_l2 as curDept,entry.FMonth as planMonth,entry.FOfficialPay as localBudget ");
			builder.appendSql("from T_FNC_FDCDepConPayPlanContract as con ");
			builder
					.appendSql("left join T_FNC_FDCDepConPayPlanBill as head on head.FID = con.FHeadID ");
			builder
					.appendSql("left join T_FNC_FDCDepConPayPlanCE as entry on entry.FParentC = con.FID ");
			builder
					.appendSql("left join T_ORG_Admin as admin on admin.FID = head.FDeptmentID ");
			// builder
			// .appendSql(
			// "where (head.FState = '4AUDITTED' or head.FState = '10PUBLISH') "
			// );
			builder.appendSql("where 1=1 ");
			builder.appendSql(" and entry.FMonth >= ");
			builder.appendParam(firstDay);
			builder.appendSql(" and entry.FMonth <= ");
			builder.appendParam(lastDay);
			builder.appendSql(" and con.FID = '");
			builder.appendSql(
					((FDCDepConPayPlanContractInfo) plan).getId().toString())
					.appendSql("' ");
			builder.appendSql(" and con.FProjectID = ");
			builder.appendParam(projectId);
			builder.appendSql("order by head.FYear desc,head.FMonth DESC");
			IRowSet rowSet = builder.executeQuery();
			while (rowSet.next()) {
				if (rowSet.getString("localBudget") != null) {
					localBudget = rowSet.getBigDecimal("localBudget");
				}
			}
		} else if (plan != null
				&& plan instanceof FDCDepConPayPlanUnsettledConInfo) {
			builder
					.appendSql(" select admin.FName_l2 as curDept,entry.FMonth as planMonth,entry.FOfficialPay as localBudget ");
			builder.appendSql(" from T_FNC_FDCDepConPayPlanUC as con ");
			builder
					.appendSql(" left join T_FNC_FDCDepConPayPlanBill as head on head.FID = con.FParentID ");
			builder
					.appendSql(" left join T_FNC_FDCDepConPayPlanUE as entry on entry.FParentID = con.FID ");
			builder
					.appendSql(" left join T_ORG_Admin as admin on admin.FID = head.FDeptmentID ");
			builder
					.appendSql(" where ");
			builder.appendSql(" entry.FMonth >= ");
			builder.appendParam(firstDay);
			builder.appendSql(" and entry.FMonth <= ");
			builder.appendParam(lastDay);
			builder.appendSql(" and con.FID = '");
			builder.appendSql(
					((FDCDepConPayPlanUnsettledConInfo) plan).getId()
							.toString()).appendSql("' ");
			builder.appendSql(" and con.FProjectID = ");
			builder.appendParam(projectId);
			builder.appendSql("order by head.FYear desc,head.FMonth DESC");
			IRowSet rowSet = builder.executeQuery();
			while (rowSet.next()) {
				if (rowSet.getString("localBudget") != null) {
					localBudget = rowSet.getBigDecimal("localBudget");
				}
			}
		} else if (plan != null
				&& plan instanceof FDCDepConPayPlanNoContractInfo) {
			builder
					.appendSql(" select admin.FName_l2 as curDept,entry.FMonth as planMonth,entry.FOfficialPay as localBudget ");
			builder.appendSql(" from T_FNC_FDCDepConPayPlanNoCon as con ");
			builder
					.appendSql(" left join T_FNC_FDCDepConPayPlanBill as head on head.FID = con.FHeadID ");
			builder
					.appendSql(" left join T_FNC_FDCDepConPayPlanNCEntry as entry on entry.FParentNC = con.FID ");
			builder
					.appendSql(" left join T_ORG_Admin as admin on admin.FID = head.FDeptmentID ");
			builder
					.appendSql(" where  ");
			builder.appendSql(" entry.FMonth >= ");
			builder.appendParam(firstDay);
			builder.appendSql(" and entry.FMonth <= ");
			builder.appendParam(lastDay);
			builder.appendSql(" and con.FID = '");
			builder.appendSql(
					((FDCDepConPayPlanNoContractInfo) plan).getId().toString())
					.appendSql("' ");
			builder.appendSql("order by head.FYear desc,head.FMonth DESC");
			IRowSet rowSet = builder.executeQuery();
			while (rowSet.next()) {
				if (rowSet.getString("localBudget") != null) {
					localBudget = rowSet.getBigDecimal("localBudget");
				}
			}
		}
		return localBudget;
	}
	
	/**
	 * ��ȡָ�����ڵĵ�һ��
	 * @param date
	 * @return ������2010-5-1 00:00:00.000
	 * @see Calendar#roll(int, int)
	 */
	public static Date getFirstDay(Date date){
		Calendar ca = Calendar.getInstance();
		ca.setTime(date);
		ca.set(Calendar.DAY_OF_MONTH, 1);
		ca.set(Calendar.HOUR_OF_DAY, 0);
		ca.set(Calendar.MINUTE,0);
		ca.set(Calendar.SECOND, 0);
		ca.set(Calendar.MILLISECOND, 0);
		return ca.getTime();
	}
	
	
	/**
	 * ��ȡָ�����ڵ����һ��
	 * @param date
	 * @return �����磺2010-5-31 23:59:59.999
	 * @see Calendar#roll(int, int)
	 */
	public static Date getLastDay(Date date){
		Calendar ca = Calendar.getInstance();
		ca.setTime(date);
		ca.set(Calendar.DAY_OF_MONTH, 1);
		ca.roll(Calendar.DAY_OF_MONTH,-1);
		ca.set(Calendar.HOUR_OF_DAY, 23);
		ca.set(Calendar.MINUTE, 59);
		ca.set(Calendar.SECOND, 59);
		ca.set(Calendar.MILLISECOND, 999);
		
		return ca.getTime();
	}
	
	/**
	 * ������
	 */
	protected void pkbookedDate_dataChanged(DataChangeEvent e) throws Exception {
		actionViewBudget_actionPerformed(null);
	}

	/**
	 * ����Ӧ�������뵥���ۼƷ�Ʊ����Ƿ�ᳬ����ͬ�������
	 * 
	 * @author owen_wen
	 * @param ctx
	 * @param paymentBill
	 *            ���
	 * @param thisInvoiceAmt
	 *            �����޸ĺ�ķ�Ʊ���
	 * @throws EASBizException
	 */
	private void checkIsAllInvoicAmtMoreThanConLatestPrice(PaymentBillInfo paymentBill, BigDecimal thisInvoiceAmt) throws BOSException,
			EASBizException {
		boolean isAllowOverConPrice = FDCUtils.getBooleanValue4FDCParamByKey(null, SysContext.getSysContext().getCurrentOrgUnit().getId().toString(),
				FDCConstants.FDC_PARAM_OVERRUNCONPRICE);
		if (isAllowOverConPrice) // ��������ۼƷ�Ʊ�����ں�ͬ�������
			return;
		PayRequestBillInfo payReqInfo = PayRequestBillFactory.getRemoteInstance().getPayRequestBillInfo(
				new ObjectUuidPK(BOSUuid.read(paymentBill.getFdcPayReqID())));
		// �µ��ۼƷ�Ʊ���
		BigDecimal newAllInvoice = FDCNumberHelper.subtract(payReqInfo.getAllInvoiceAmt(), FDCHelper.toBigDecimal(payReqInfo.getInvoiceAmt())).add(
				thisInvoiceAmt);
		
		//R110725-0280:���ı���ͬ�������ɵĸ���ύ������ͬ�������ΪNULL
		BigDecimal latestPrice = FDCNumberHelper.ZERO;
		if (FDCUtils.isContractBill(null, paymentBill.getContractBillId())) {		
			Map map = (Map) FDCUtils.getLastAmt_Batch(null, new String[] { paymentBill.getContractBillId() });
			latestPrice = (BigDecimal) map.get(paymentBill.getContractBillId());
		} else {
			//���ı���ͬ���ڸ������뵥�Ľ��ɣ�������ȡ��ͬ�Ľ��
			ContractWithoutTextInfo info = ContractWithoutTextFactory.getRemoteInstance().getContractWithoutTextInfo(new ObjectUuidPK(BOSUuid.read(paymentBill.getContractBillId())));
			latestPrice = info.getAmount();
		}

		if (newAllInvoice.compareTo(latestPrice) > 0) {
			// ���뵥�ķ�Ʊ�ۼƽ�����ͬ��������ۣ�������ʾ��Ϣ
			throw new FinanceException(FinanceException.ALLINVOICEAMTMORETHANCONLATESTPRICE);
		}
	}
	public void actionApprove_actionPerformed(ActionEvent e) throws Exception {
		isMoving = true;
		checkIsAdjustPeriod();
        Set idSet = new HashSet();
        idSet.add(editData.getId().toString());
        ((IPaymentBill)getBizInterface()).approve(idSet);
        showMsg(e);
        actionApprove.setEnabled(false);
        syncDataFromDB();
	}

	public void actionUntiApprove_actionPerformed(ActionEvent e) throws Exception {
		isMoving = true;
		checkIsAdjustPeriod();
        Set idSet = new HashSet();
        idSet.add(editData.getId().toString());
        ((IPaymentBill)getBizInterface()).untiApprove(idSet);
        showMsg(e);
        syncDataFromDB();
	}
	public void checkIsAdjustPeriod()
    {
        PeriodInfo currentPeriod = (PeriodInfo)paramValue.get("currentPeriond");
        if(currentPeriod != null && currentPeriod.isIsAdjustPeriod())
        {
            MsgBox.showError(this, (new FMException(FMException.CURRPERIODISADJUST)).getMessage());
            SysUtil.abort();
        }
    }
	private void showMsg(ActionEvent e)
    {
        Component component = (Component)e.getSource();
        FMClientHelper.showSuccessMessage(this, component);
    }
}
