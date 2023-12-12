/**
 * output package name
 */
package com.kingdee.eas.fdc.contract.client;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import javax.swing.Action;
import javax.swing.event.ChangeEvent;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.ctrl.extendcontrols.IDataFormat;
import com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox;
import com.kingdee.bos.ctrl.kdf.table.ICell;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTDefaultCellEditor;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent;
import com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent;
import com.kingdee.bos.ctrl.kdf.table.event.KDTSelectEvent;
import com.kingdee.bos.ctrl.kdf.util.render.ObjectValueRender;
import com.kingdee.bos.ctrl.kdf.util.style.Styles.HorizontalAlignment;
import com.kingdee.bos.ctrl.swing.KDComboBoxMultiColumnItem;
import com.kingdee.bos.ctrl.swing.KDDatePicker;
import com.kingdee.bos.ctrl.swing.KDFormattedTextField;
import com.kingdee.bos.ctrl.swing.KDTextField;
import com.kingdee.bos.ctrl.swing.KDWorkButton;
import com.kingdee.bos.ctrl.swing.StringUtils;
import com.kingdee.bos.ctrl.swing.event.DataChangeEvent;
import com.kingdee.bos.ctrl.swing.event.DataChangeListener;
import com.kingdee.bos.ctrl.swing.event.SelectorEvent;
import com.kingdee.bos.ctrl.swing.util.CtrlCommonConstant;
import com.kingdee.bos.dao.AbstractObjectValue;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectStringPK;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.framework.cache.ActionCache;
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
import com.kingdee.bos.ui.face.IItemAction;
import com.kingdee.bos.ui.face.IUIFactory;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.bos.workflow.ProcessInstInfo;
import com.kingdee.bos.workflow.service.ormrpc.EnactmentServiceFactory;
import com.kingdee.bos.workflow.service.ormrpc.IEnactmentService;
import com.kingdee.eas.base.attachment.AttachmentFactory;
import com.kingdee.eas.base.attachment.AttachmentFtpFacadeFactory;
import com.kingdee.eas.base.attachment.AttachmentInfo;
import com.kingdee.eas.base.attachment.BoAttchAssoCollection;
import com.kingdee.eas.base.attachment.BoAttchAssoFactory;
import com.kingdee.eas.base.attachment.BoAttchAssoInfo;
import com.kingdee.eas.base.attachment.IAttachment;
import com.kingdee.eas.base.attachment.common.AttachmentClientManager;
import com.kingdee.eas.base.attachment.common.AttachmentManagerFactory;
import com.kingdee.eas.base.attachment.util.FileGetter;
import com.kingdee.eas.base.commonquery.BooleanEnum;
import com.kingdee.eas.base.param.ParamControlFactory;
import com.kingdee.eas.base.param.util.ParamManager;
import com.kingdee.eas.base.permission.OrgRangeIncludeSubOrgFactory;
import com.kingdee.eas.base.permission.OrgRangeType;
import com.kingdee.eas.base.permission.UserInfo;
import com.kingdee.eas.basedata.assistant.CurrencyInfo;
import com.kingdee.eas.basedata.assistant.ExchangeRateInfo;
import com.kingdee.eas.basedata.assistant.SettlementTypeInfo;
import com.kingdee.eas.basedata.master.account.AccountViewInfo;
import com.kingdee.eas.basedata.master.account.client.AccountPromptBox;
import com.kingdee.eas.basedata.master.cssp.SupplierCompanyInfoCollection;
import com.kingdee.eas.basedata.master.cssp.SupplierCompanyInfoFactory;
import com.kingdee.eas.basedata.master.cssp.SupplierCompanyInfoInfo;
import com.kingdee.eas.basedata.master.cssp.SupplierFactory;
import com.kingdee.eas.basedata.master.cssp.SupplierInfo;
import com.kingdee.eas.basedata.org.AdminOrgUnitFactory;
import com.kingdee.eas.basedata.org.AdminOrgUnitInfo;
import com.kingdee.eas.basedata.org.CompanyOrgUnitInfo;
import com.kingdee.eas.basedata.org.CostCenterOrgUnitInfo;
import com.kingdee.eas.basedata.org.FullOrgUnitCollection;
import com.kingdee.eas.basedata.org.FullOrgUnitFactory;
import com.kingdee.eas.basedata.org.OrgUnitInfo;
import com.kingdee.eas.basedata.org.PositionCollection;
import com.kingdee.eas.basedata.org.PositionInfo;
import com.kingdee.eas.basedata.org.client.f7.CompanyBizUnitF7;
import com.kingdee.eas.basedata.org.client.f7.CompanyF7;
import com.kingdee.eas.basedata.person.PersonFactory;
import com.kingdee.eas.basedata.person.PersonInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.cp.bc.BizCollBillBaseInfo;
import com.kingdee.eas.cp.bc.BizCollUtil;
import com.kingdee.eas.cp.bc.CommonUtilFacadeFactory;
import com.kingdee.eas.cp.bc.ExpenseTypeFactory;
import com.kingdee.eas.cp.bc.ExpenseTypeInfo;
import com.kingdee.eas.cp.bc.client.ExpenseTypePromptBox;
import com.kingdee.eas.fdc.aimcost.FDCCostRptFacadeFactory;
import com.kingdee.eas.fdc.basecrm.CRMHelper;
import com.kingdee.eas.fdc.basedata.ContractTypeCollection;
import com.kingdee.eas.fdc.basedata.ContractTypeFactory;
import com.kingdee.eas.fdc.basedata.ContractTypeInfo;
import com.kingdee.eas.fdc.basedata.ContractTypeOrgTypeEnum;
import com.kingdee.eas.fdc.basedata.CostAccountFactory;
import com.kingdee.eas.fdc.basedata.CostAccountInfo;
import com.kingdee.eas.fdc.basedata.CostAccountWithBgItemFactory;
import com.kingdee.eas.fdc.basedata.CostAccountYJTypeEnum;
import com.kingdee.eas.fdc.basedata.CostSplitStateEnum;
import com.kingdee.eas.fdc.basedata.CurProjectFactory;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.fdc.basedata.FDCBasedataException;
import com.kingdee.eas.fdc.basedata.FDCBillInfo;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCCommonServerHelper;
import com.kingdee.eas.fdc.basedata.FDCConstants;
import com.kingdee.eas.fdc.basedata.FDCDateHelper;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.basedata.PaymentTypeCollection;
import com.kingdee.eas.fdc.basedata.PaymentTypeFactory;
import com.kingdee.eas.fdc.basedata.PaymentTypeInfo;
import com.kingdee.eas.fdc.basedata.ProjectStatusInfo;
import com.kingdee.eas.fdc.basedata.SourceTypeEnum;
import com.kingdee.eas.fdc.basedata.TaxInfoEnum;
import com.kingdee.eas.fdc.basedata.client.ContractTypePromptSelector;
import com.kingdee.eas.fdc.basedata.client.FDCClientHelper;
import com.kingdee.eas.fdc.basedata.client.FDCClientUtils;
import com.kingdee.eas.fdc.basedata.client.FDCClientVerifyHelper;
import com.kingdee.eas.fdc.basedata.client.FDCContractParamUI;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.basedata.client.FDCTableHelper;
import com.kingdee.eas.fdc.basedata.util.TableUtils;
import com.kingdee.eas.fdc.contract.BankNumInfo;
import com.kingdee.eas.fdc.contract.ConNoCostSplitFactory;
import com.kingdee.eas.fdc.contract.ConSplitExecStateEnum;
import com.kingdee.eas.fdc.contract.ContractBillCollection;
import com.kingdee.eas.fdc.contract.ContractBillFactory;
import com.kingdee.eas.fdc.contract.ContractBillInfo;
import com.kingdee.eas.fdc.contract.ContractCostSplitEntryCollection;
import com.kingdee.eas.fdc.contract.ContractCostSplitEntryFactory;
import com.kingdee.eas.fdc.contract.ContractCostSplitFactory;
import com.kingdee.eas.fdc.contract.ContractFacadeFactory;
import com.kingdee.eas.fdc.contract.ContractInvoiceInfo;
import com.kingdee.eas.fdc.contract.ContractMarketEntryInfo;
import com.kingdee.eas.fdc.contract.ContractPropertyEnum;
import com.kingdee.eas.fdc.contract.ContractWTInvoiceEntryInfo;
import com.kingdee.eas.fdc.contract.ContractWTMarketEntryInfo;
import com.kingdee.eas.fdc.contract.ContractWithoutTextBgEntryCollection;
import com.kingdee.eas.fdc.contract.ContractWithoutTextBgEntryInfo;
import com.kingdee.eas.fdc.contract.ContractWithoutTextCollection;
import com.kingdee.eas.fdc.contract.ContractWithoutTextFactory;
import com.kingdee.eas.fdc.contract.ContractWithoutTextInfo;
import com.kingdee.eas.fdc.contract.FDCUtils;
import com.kingdee.eas.fdc.contract.FeeEntryInfo;
import com.kingdee.eas.fdc.contract.FeeTypeEnum;
import com.kingdee.eas.fdc.contract.JZTypeEnum;
import com.kingdee.eas.fdc.contract.MarketProjectCollection;
import com.kingdee.eas.fdc.contract.MarketProjectFactory;
import com.kingdee.eas.fdc.contract.MarketProjectInfo;
import com.kingdee.eas.fdc.contract.PayContentTypeInfo;
import com.kingdee.eas.fdc.contract.ContractWTInvoiceEntryInfo;
import com.kingdee.eas.fdc.contract.PayReqUtils;
import com.kingdee.eas.fdc.contract.PayRequestBillBgEntryCollection;
import com.kingdee.eas.fdc.contract.PayRequestBillBgEntryInfo;
import com.kingdee.eas.fdc.contract.PayRequestBillCollection;
import com.kingdee.eas.fdc.contract.PayRequestBillFactory;
import com.kingdee.eas.fdc.contract.PayRequestBillInfo;
import com.kingdee.eas.fdc.contract.TraEntryInfo;
import com.kingdee.eas.fdc.contract.UrgentDegreeEnum;
import com.kingdee.eas.fdc.contract.app.MarketCostTypeEnum;
import com.kingdee.eas.fdc.contract.app.OaUtil;
import com.kingdee.eas.fdc.contract.app.WTInvoiceTypeEnum;
import com.kingdee.eas.fdc.contract.programming.ProgrammingContractFactory;
import com.kingdee.eas.fdc.contract.programming.ProgrammingContractInfo;
import com.kingdee.eas.fdc.contract.programming.client.ContractBillLinkProgContEditUI;
import com.kingdee.eas.fdc.contract.programming.client.ProgrammingContractEditUI;
import com.kingdee.eas.fdc.finance.FDCDepConPayPlanNoContractInfo;
import com.kingdee.eas.fdc.finance.PaymentSplitFactory;
import com.kingdee.eas.fdc.finance.ProjectMonthPlanGatherEntryCollection;
import com.kingdee.eas.fdc.finance.ProjectMonthPlanGatherEntryFactory;
import com.kingdee.eas.fdc.finance.ProjectMonthPlanGatherEntryInfo;
import com.kingdee.eas.fdc.finance.ProjectMonthPlanGatherFactory;
import com.kingdee.eas.fdc.sellhouse.RoomInfo;
import com.kingdee.eas.fi.cas.PaymentBillEntryInfo;
import com.kingdee.eas.fi.cas.PaymentBillInfo;
import com.kingdee.eas.fi.cas.PaymentBillTypeInfo;
import com.kingdee.eas.fi.cas.client.CasRecPayHandler;
import com.kingdee.eas.fi.gl.GlUtils;
import com.kingdee.eas.fm.common.ContextHelperFactory;
import com.kingdee.eas.fm.common.FMConstants;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.eas.framework.CoreBillBaseCollection;
import com.kingdee.eas.framework.batchHandler.RequestContext;
import com.kingdee.eas.ma.budget.BgCtrlTypeEnum;
import com.kingdee.eas.ma.budget.BgControlFacadeFactory;
import com.kingdee.eas.ma.budget.BgControlSchemeCollection;
import com.kingdee.eas.ma.budget.BgControlSchemeFactory;
import com.kingdee.eas.ma.budget.BgCtrlParamCollection;
import com.kingdee.eas.ma.budget.BgCtrlResultCollection;
import com.kingdee.eas.ma.budget.BgCtrlResultInfo;
import com.kingdee.eas.ma.budget.BgHelper;
import com.kingdee.eas.ma.budget.BgItemCollection;
import com.kingdee.eas.ma.budget.BgItemFactory;
import com.kingdee.eas.ma.budget.BgItemInfo;
import com.kingdee.eas.ma.budget.BgItemObject;
import com.kingdee.eas.ma.budget.IBgControlFacade;
import com.kingdee.eas.ma.budget.client.BgBalanceViewUI;
import com.kingdee.eas.ma.budget.client.NewBgItemDialog;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.app.DbUtil;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.MsgBox;
import com.kingdee.jdbc.rowset.IRowSet;
import com.kingdee.util.NumericExceptionSubItem;

/**
 * 
 * ����:���ı���ͬ�༭����
 * @author liupd  date:2006-10-10 <p>
 * @version EAS5.1.3
 */
public class ContractWithoutTextEditUI extends
		AbstractContractWithoutTextEditUI {
	private static final Logger logger = CoreUIObject
			.getLogger(ContractWithoutTextEditUI.class);
	
	//	�Ƿ��һ�μ���,�������Ʊ��ļ�����ʾ
	private boolean isFirstLoad=true;
	//��ǰ�����Ƿ��и����б�
	private boolean isHasAttachment = false;
	//�������뵥
	private PayRequestBillInfo prbi  = new PayRequestBillInfo();
	
	//��������
	private PaymentTypeCollection paymentTypes = null;	
	
	//��ͬ����
	private ContractTypeCollection contractType = null ;

	//���ò���һ�廯
	protected boolean isFinacial = false;
	
	//��Ŀ��ͬǩԼ�ܽ�����Ŀ
	private String controlCost ;
	//���Ʒ�ʽ
	private String controlType ;
	
	//�Ƿ���ʾ����ͬ������Ŀ��
	private boolean isShowCharge = false;
	
	// �ۼƷ�Ʊ���
	private BigDecimal allInvoiceAmt = FDCHelper.ZERO;
	
	// ���ز����ǿ�Ʋ��������ϵͳ  at 2010-5-10
	protected boolean isNotEnterCAS = false;

	// �������뵥�����ı���ͬ��Ʊ�š���Ʊ����¼
	private boolean isInvoiceRequired = false;
	
	// ͨ����ͬ�¶ȹ�������ƻ��������ı���ͬ
	private String CONTROLNOCONTRACT = "������";

	// �������Ƿ����ѡ���������ŵ���Ա
	private boolean canSelecOtherOrgPerson() {
		boolean canSelectOtherOrgPerson = false;
		try {
			canSelectOtherOrgPerson = FDCUtils.getDefaultFDCParamByKey(null, null, FDCConstants.FDC_PARAM_SELECTPERSON);
		} catch (EASBizException e) {
			e.printStackTrace();
		} catch (BOSException e) {
			e.printStackTrace();
		}
		return canSelectOtherOrgPerson;
	}
	boolean isFeeTraEntry=false;
	/**
	 * output class constructor
	 */
	public ContractWithoutTextEditUI() throws Exception {
		super();
		jbInit() ;
	}

	private void jbInit() {	    
		titleMain = getUITitle();
	}
	
	public void onShow() throws Exception {
		super.onShow();
		
		setFocus();
		txtPaymentRequestBillNumber.setRequired(false);
		txtPaymentRequestBillNumber.setEditable(false);
		this.btnViewProgramContract.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_showlist"));
		this.btnProgram.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_associatecreate"));
		this.pkbookedDate.setEnabled(false);
	}
	protected void afterSubmitAddNew() {
		clearPayRequestFieldCtrl();
		
		super.afterSubmitAddNew();
		setFocus();
	}
	
	private void setFocus() {
		if(txtNumber.isEnabled() && txtNumber.isEditable()) {
			txtNumber.requestFocus();
		}
		else {
			txtbillName.requestFocus();
		}
	}
	
	/**
	 * output getEditUIName method
	 */
	protected String getEditUIName() {
		return com.kingdee.eas.fdc.contract.client.ContractWithoutTextEditUI.class
				.getName();
	}

	/**
	 * output getBizInterface method
	 */
	protected com.kingdee.eas.framework.ICoreBase getBizInterface()
			throws Exception {
		return ContractWithoutTextFactory.getRemoteInstance();
	}

	/**
	 * output getDetailTable method
	 */
	protected KDTable getDetailTable() {
		return null;
	}

	/**
	 * output createNewDetailData method
	 */
	protected IObjectValue createNewDetailData(KDTable table) {

		return null;
	}

	/**
	 * output createNewData method
	 */
	protected com.kingdee.bos.dao.IObjectValue createNewData() {
		ContractWithoutTextInfo objectValue = new ContractWithoutTextInfo();
		objectValue.setId(BOSUuid.create(objectValue.getBOSType()));
		objectValue.setCreator((SysContext.getSysContext().getCurrentUserInfo()));
		objectValue.setCreateTime(new Timestamp(serverDate.getTime()));
//		objectValue.setSignDate(this.serverDate);

		objectValue.setSourceType(SourceTypeEnum.ADDNEW);
		objectValue.setFeeType(FeeTypeEnum.FEE);
		
		objectValue.setIsCostSplit(true);
		objectValue.setConSplitExecState(ConSplitExecStateEnum.COMMON);
		objectValue.setCurrency(this.baseCurrency);
		//��ĿId
		BOSUuid projId = (BOSUuid) getUIContext().get("projectId");
		if(projId != null) { 
			CurProjectInfo projInfo = curProject;			
			objectValue.setCurProject(projInfo);
			objectValue.setCU(projInfo.getCU());		
		}
		
		objectValue.setBookedDate(bookedDate);
		objectValue.setPeriod(curPeriod);
		
		//�������ı���ͬ��ֳ�ʼ��״̬Ϊδ���
		objectValue.setSplitState(CostSplitStateEnum.NOSPLIT);
		objectValue.setInvoiceDate(serverDate);
		
//		PersonInfo person = SysContext.getSysContext().getCurrentUserInfo().getPerson();
//		PositionCollection positionCol;
//		positionCol = null;
//		AdminOrgUnitInfo adminInfo = null;
//		try {
//			positionCol = PersonFactory.getRemoteInstance().getPositions(person.getId());
//			PositionInfo position = null;
//			if(positionCol.size() > 0){
//				position = positionCol.get(0);
//			}
//			if(position!=null && position.getAdminOrgUnit()!=null){
//				adminInfo = AdminOrgUnitFactory.getRemoteInstance().getAdminOrgUnitInfo(new ObjectUuidPK(position.getAdminOrgUnit().getId().toString()));
//			}
//		} catch (Exception e1) {
//			e1.printStackTrace();
//		} 
//		objectValue.setUseDepartment(adminInfo);
		
		objectValue.setIsInvoice(true);
		objectValue.setIsBgControl(true);
		objectValue.setIsNeedPaid(true);
		objectValue.getBgEntry().add(new ContractWithoutTextBgEntryInfo());
//		try {
//			BizCollBillBaseInfo baseInfo = CommonUtilFacadeFactory.getRemoteInstance().forLoanBillCreateNewData();
//			objectValue.setApplierOrgUnit(baseInfo.getOrgUnit());
//			objectValue.setApplier(baseInfo.getApplier());
//			objectValue.setApplierCompany(baseInfo.getApplierCompany());
//			objectValue.setCostedDept(SysContext.getSysContext().getCurrentCostUnit());
//			objectValue.setCostedCompany(SysContext.getSysContext().getCurrentFIUnit());
//		} catch (EASBizException e) {
//			e.printStackTrace();
//		} catch (BOSException e) {
//			e.printStackTrace();
//		}
		objectValue.setApplier(SysContext.getSysContext().getCurrentUserInfo().getPerson());
		
		return objectValue;
	}

	private void clearPayRequestFieldCtrl() {
		prmtPayment.setValue(null);
		prmtuseDepartment.setValue(null);
		txtPaymentRequestBillNumber.setText(null);
		pksignDate.setValue(null);
		prmtreceiveUnit.setValue(null);
		prmtrealSupplier.setValue(null);
		txtBank.setText(null);
		prmtsettlementType.setValue(null);
		txtBankAcct.setText(null);
		chkUrgency.setSelected(false);
		chkNeedPaid.setSelected(true);
		txtNoPaidReason.setText(null);
		prmtAccount.setValue(null);
		prmtcurrency.setValue(null);
		txtexchangeRate.setValue(null);
		txtamount.setValue(null);
		txtcapitalAmount.setText(null);
		txtBcAmount.setValue(null);
		txtPaymentProportion.setValue(null);
		txtcompletePrjAmt.setValue(null);
		txtMoneyDesc.setText(null);
		txtattachment.setText(null);
	}
	
	//�����������
	protected  void fetchInitParam() throws Exception{
		super.fetchInitParam();
		//��ĿId
		BOSUuid projId = (BOSUuid) getUIContext().get("projectId");
		if(projId != null) { 
			CurProjectInfo projInfo = curProject;	
			//����Ĳ���Ϊ�����ڹ�����Ŀ��Ӧ�Ĳ�����֯id�������ǻ�õ�ǰ��½������֯��id
			//���óɱ�����һ�廯
			HashMap paramItem = FDCUtils.getDefaultFDCParam(null,projInfo.getFullOrgUnit().getId().toString());
//			HashMap paramItem = FDCUtils.getDefaultFDCParam(null,SysContext.getSysContext().getCurrentFIUnit().getId().toString());
			if(paramItem.get(FDCConstants.FDC_PARAM_FINACIAL)!=null){
				isFinacial = Boolean.valueOf(paramItem.get(FDCConstants.FDC_PARAM_FINACIAL).toString()).booleanValue();
			}
			if(paramItem.get(FDCConstants.FDC_PARAM_INVOICEREQUIRED)!=null){
				isInvoiceRequired = Boolean.valueOf(paramItem.get(FDCConstants.FDC_PARAM_INVOICEREQUIRED).toString()).booleanValue();
			}
		}
		//�Ƿ���ʾ����ͬ������Ŀ��
		isShowCharge = FDCUtils.getDefaultFDCParamByKey(null,null,FDCConstants.FDC_PARAM_CHARGETYPE);			
		
		String org = SysContext.getSysContext().getCurrentOrgUnit().getId()
				.toString();
		CONTROLNOCONTRACT = ParamManager.getParamValue(null, new ObjectUuidPK(
				org), "FDC326_CONTROLNOCONTRACT");
		if ("0".equals(CONTROLNOCONTRACT)) {
			CONTROLNOCONTRACT = "�ϸ����";
		} else if ("1".equals(CONTROLNOCONTRACT)) {
			CONTROLNOCONTRACT = "��ʾ����";
		} else if ("2".equals(CONTROLNOCONTRACT)) {
			CONTROLNOCONTRACT = "������";
		}
	}
	
	/**
	 * new update by renliang at 2010-5-12
	 */
	public void isNotEnterCAS(){
		
		CurProjectInfo info = editData.getCurProject();
		
		if(info!=null){
			Map paramItem = null;
			try {
				paramItem = FDCUtils.getDefaultFDCParam(null, info.getFullOrgUnit().getId().toString());
			} catch (EASBizException e) {
				
				logger.info(e.getMessage());
			} catch (BOSException e) {
			
				logger.info(e.getMessage());
			}
			if(paramItem.get(FDCConstants.FDC_PARAM_NOTENTERCAS)!=null)	{
				this.isNotEnterCAS = Boolean.valueOf(paramItem.get(FDCConstants.FDC_PARAM_NOTENTERCAS).toString()).booleanValue();
			}
		}
		
	}
	
	
	//�����������
	protected  void fetchInitData() throws Exception{

		super.fetchInitData();
		
		//���Ҹ�������
		paymentTypes = (PaymentTypeCollection)ActionCache.get("ContractWithoutTextEditUIHandler.paymentTypes");
		if(paymentTypes==null){
			String sSql = "select * where IsEnabled=1 and payType.id ='Ga7RLQETEADgAAC6wKgOlOwp3Sw='";			
			paymentTypes = PaymentTypeFactory.getRemoteInstance().getPaymentTypeCollection(sSql);
		}

		//���Һ�ͬ����
		contractType = (ContractTypeCollection)ActionCache.get("ContractWithoutTextEditUIHandler.contractType");
		if(contractType==null){
			String contractSql = "select * where IsEnabled = 1" ;
			contractType = ContractTypeFactory.getRemoteInstance().getContractTypeCollection(contractSql) ;
		}
		
		Map param = (Map)ActionCache.get("FDCBillEditUIHandler.orgParamItem");
		if(param==null){
			param = FDCUtils.getDefaultFDCParam(null,orgUnitInfo.getId().toString());	
		}

		if(param.get(FDCConstants.FDC_PARAM_OUTCOST)!=null){
			controlCost = param.get(FDCConstants.FDC_PARAM_OUTCOST).toString();
		}
		if(param.get(FDCConstants.FDC_PARAM_CONTROLTYPE)!=null){
			controlType = param.get(FDCConstants.FDC_PARAM_CONTROLTYPE).toString();
		}
	}
	
	public void onLoad() throws Exception {
		this.comboPayeeType.addItem(new KDComboBoxMultiColumnItem(new String[] {"��Ӧ��" }));
		this.comboPayeeType.addItem(new KDComboBoxMultiColumnItem(new String[] {"ְԱ" }));
		
		super.onLoad();
		//new update by renliang at 2010-5-14
        isNotEnterCAS();
	    if (isNotEnterCAS) {
	    	chkNeedPaid.setEnabled(false);
			// ����жϣ���֤��ʾ��ȷ
			if (OprtState.ADDNEW.equals(getOprtState())) {
				chkNeedPaid.setSelected(true);
			}			
		}
	    
		//onLoad�����߼�����Ϊһ������
		fillAttachmentList();
		afterOnload();
		this.prmtPayment.setEnabled(false);
		
		EntityViewInfo view=new EntityViewInfo();
		FilterInfo filter=new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("isBizUnit",Boolean.TRUE));
		filter.getFilterItems().add(new FilterItemInfo("isSealUp",Boolean.FALSE));
		CompanyOrgUnitInfo com=(CompanyOrgUnitInfo)this.prmtCostedCompany.getValue();
		if(com!=null){
			filter.getFilterItems().add(new FilterItemInfo("id",getCostedDeptIdSet(com),CompareType.INCLUDE));
		}
		view.setFilter(filter);
        this.prmtCostedDept.setQueryInfo("com.kingdee.eas.basedata.org.app.CostCenterOrgUnitQuery");
        this.prmtCostedDept.setEntityViewInfo(view);
        this.prmtCostedDept.setEditFormat("$number$");
        this.prmtCostedDept.setDisplayFormat("$name$");
        this.prmtCostedDept.setCommitFormat("$number$");
        
        view=new EntityViewInfo();
		filter=new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("isSealUp",Boolean.FALSE));
		view.setFilter(filter);
        this.prmtCostedCompany.setQueryInfo("com.kingdee.eas.basedata.org.app.CompanyOrgUnitQuery");
        CompanyF7 comp = new CompanyF7(this);
        prmtCostedCompany.setEntityViewInfo(view);
        prmtCostedCompany.setSelector(comp);
        prmtCostedCompany.setDisplayFormat("$name$");
        prmtCostedCompany.setEditFormat("$number$");
        prmtCostedCompany.setCommitFormat("$number$");
        
		FDCClientUtils.setPersonF7(this.prmtApplier, this,null);
		
        setBgEditState();
        //��ʼ����Լ���  ΪʲôgetSelector���ܴ���ID������
        initProgrammingContract();
        if(this.prmtContractType.getValue()!=null){
        	this.prmtPayContentType.setEnabled(true);
        }else{
        	this.prmtPayContentType.setEnabled(false);
        }
        if("��Ӧ��".equals(this.comboPayeeType.getSelectedItem().toString())){
			prmtrealSupplier.setRequired(true);
			prmtrealSupplier.setEnabled(true);
		}else{
			prmtrealSupplier.setRequired(false);
			prmtrealSupplier.setEnabled(false);
		}
        initBgEntryTable();
        initInvoiceEntryTable();
        initMarketEntryTable();
        
        this.chkMenuItemSubmitAndAddNew.setVisible(false);
		this.chkMenuItemSubmitAndAddNew.setSelected(false);
		
		this.tblAttachement.checkParsed();
		KDWorkButton btnAttachment = new KDWorkButton();

		this.actionAttachment.putValue("SmallIcon", EASResource.getIcon("imgTbtn_affixmanage"));
		btnAttachment = (KDWorkButton) this.contAttachment.add(this.actionAttachment);
		btnAttachment.setText("��������");
		btnAttachment.setSize(new Dimension(140, 19));
		
		
		HashMap hmParamIn = new HashMap();
		hmParamIn.put("FDC_ISFEETRAENTRY", null);
		try {
			HashMap hmAllParam = ParamControlFactory.getRemoteInstance().getParamHashMap(hmParamIn);
			if(hmAllParam.get("FDC_ISFEETRAENTRY")!=null){
				isFeeTraEntry=Boolean.parseBoolean(hmAllParam.get("FDC_ISFEETRAENTRY").toString());
			}else{
				isFeeTraEntry=false;
			}
		} catch (EASBizException e) {
			e.printStackTrace();
		} catch (BOSException e) {
			e.printStackTrace();
		}
		cbFeeType_itemStateChanged(null);
		
		KDFormattedTextField txtWeight = new KDFormattedTextField();
		txtWeight.setDataType(KDFormattedTextField.BIGDECIMAL_TYPE);
		txtWeight.setDataVerifierType(KDFormattedTextField.NO_VERIFIER);
		txtWeight.setPrecision(2);
		txtWeight.setMinimumValue(FDCHelper.ZERO);
		KDTDefaultCellEditor weight = new KDTDefaultCellEditor(txtWeight);
		
		this.kdtFeeEntry.getColumn("amount").setEditor(weight);
		this.kdtTraEntry.getColumn("airFee").setEditor(weight);
		this.kdtTraEntry.getColumn("carFee").setEditor(weight);
		this.kdtTraEntry.getColumn("cityFee").setEditor(weight);
		this.kdtTraEntry.getColumn("otherFee").setEditor(weight);
		this.kdtTraEntry.getColumn("liveFee").setEditor(weight);
		this.kdtTraEntry.getColumn("allowance").setEditor(weight);
		this.kdtTraEntry.getColumn("other").setEditor(weight);
		this.kdtTraEntry.getColumn("total").setEditor(weight);
		
		hmParamIn = new HashMap();
		hmParamIn.put("FDC_ISOTHERCOSTEDDEPT", null);
		HashMap hmAllParam = ParamControlFactory.getRemoteInstance().getParamHashMap(hmParamIn);
		if(hmAllParam.get("FDC_ISOTHERCOSTEDDEPT")!=null){
			isOtherCostedDept=Boolean.parseBoolean(hmAllParam.get("FDC_ISOTHERCOSTEDDEPT").toString());
		}else{
			isOtherCostedDept=true;
		}
		if(!isOtherCostedDept){
			this.prmtCostedCompany.setEnabled(false);
			this.prmtCostedCompany.setRequired(false);
			this.prmtCostedDept.setRequired(false);
		}
		
		this.btnAccountView.setIcon(EASResource.getIcon("imgTbtn_associatecreate"));
		
		this.actionCopy.setVisible(false);
		this.actionPre.setVisible(false);
		this.actionNext.setVisible(false);
		this.actionFirst.setVisible(false);
		this.actionLast.setVisible(false);
		
		
		view=new EntityViewInfo();
		filter = new FilterInfo();
		FilterItemCollection filterItems = filter.getFilterItems();
		filterItems.add(new FilterItemInfo("state", FDCBillStateEnum.AUDITTED_VALUE));
		filterItems.add(new FilterItemInfo("isSub", Boolean.FALSE));
		filterItems.add(new FilterItemInfo("orgUnit.id", editData.getCurProject().getFullOrgUnit().getId().toString()));
		
		view=new EntityViewInfo();
		view.setFilter(filter);
		this.prmtMarketProject.setEntityViewInfo(view);
		
		filter = new FilterInfo();
		filterItems = filter.getFilterItems();
		filterItems.add(new FilterItemInfo("fullOrgUnit.id", editData.getCurProject().getFullOrgUnit().getId().toString()));
		filterItems.add(new FilterItemInfo("isMarket", Boolean.TRUE));
		filterItems.add(new FilterItemInfo("isLeaf", Boolean.TRUE));
		
		view=new EntityViewInfo();
		view.setFilter(filter);
		this.prmtMpCostAccount.setEntityViewInfo(view);
		
		filter = new FilterInfo();
		filterItems = filter.getFilterItems();
		filterItems.add(new FilterItemInfo("isEnabled", Boolean.TRUE));
		
		view=new EntityViewInfo();
		view.setFilter(filter);
		this.prmtLxNum.setEntityViewInfo(view);
		
		txtBankAcct.setRequired(true);
		
		this.cbOrgType.removeItem(ContractTypeOrgTypeEnum.ALLRANGE);
		this.cbOrgType.removeItem(ContractTypeOrgTypeEnum.BIGRANGE);
		this.cbOrgType.removeItem(ContractTypeOrgTypeEnum.SMALLRANGE);
		
		this.actionMALine.setVisible(false);
		this.actionMRLine.setVisible(false);
	}
	public void actionAccountView_actionPerformed(ActionEvent e)throws Exception {
		if(!ContractWithoutTextFactory.getRemoteInstance().exists(new ObjectUuidPK(this.editData.getId()))){
			FDCMsgBox.showWarning(this,"���ȱ��浥�ݣ�");
			SysUtil.abort();
		}
		
		String splitBillID = null;

		FDCBillInfo billInfo = null;
		CoreBaseCollection coll = null;

		String editName = null;

		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("conWithoutText.id", this.editData.getId().toString()));
		view.setFilter(filter);
		view.getSelector().add("id");
		view.getSelector().add("state");

		SelectorItemCollection selectors = new SelectorItemCollection();
		selectors.add("id");
			editName = com.kingdee.eas.fdc.finance.client.PaymentSplitEditUI.class.getName();
			coll = PaymentSplitFactory.getRemoteInstance().getCollection(view);

		boolean isSplited = false;
		boolean isAudited = false;

		if (coll.size() > 0) {
			billInfo = (FDCBillInfo) coll.get(0);
			splitBillID = billInfo.getId().toString();
			isSplited = true;

			if (billInfo.getState().equals(FDCBillStateEnum.AUDITTED)) {
				isAudited = true;
			}
		}

		UIContext uiContext = new UIContext(this);
		String oprtState;

		if (isSplited) {
			uiContext.put(UIContext.ID, splitBillID);

			if (isAudited) {
				oprtState = OprtState.VIEW;
			} else {
				oprtState = OprtState.EDIT;
			}
		} else {
			uiContext.put("costBillID", this.editData.getId().toString());
			oprtState = OprtState.ADDNEW;
		}
		uiContext.put("amount", this.txtamount.getBigDecimalValue());
		IUIWindow uiWin = UIFactory.createUIFactory(UIFactoryName.MODEL)
				.create(editName, uiContext, null, oprtState);
		uiWin.show();
	}
	boolean isOtherCostedDept=true;
	protected Set getCostedDeptIdSet(CompanyOrgUnitInfo com) throws EASBizException, BOSException{
		if(com==null) return null;
		Set id=new HashSet();
		String longNumber=FullOrgUnitFactory.getRemoteInstance().getFullOrgUnitInfo(new ObjectUuidPK(com.getId())).getLongNumber();
		EntityViewInfo view=new EntityViewInfo();
		FilterInfo filter=new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("longNumber",longNumber+"!%",CompareType.LIKE));
		view.setFilter(filter);
		FullOrgUnitCollection col=FullOrgUnitFactory.getRemoteInstance().getFullOrgUnitCollection(view);
		for(int i=0;i<col.size();i++){
			if(col.get(i).getPartCostCenter()!=null){
				id.add(col.get(i).getId().toString());
			}
		}
		return id;
	}
	private void initProgrammingContract() throws EASBizException, BOSException {
		if(this.editData.getProgrammingContract()!=null && this.editData.getProgrammingContract().getId() != null){
			ProgrammingContractInfo proContInfo = getProContInfo(this.editData.getProgrammingContract().getId().toString());
			this.editData.setProgrammingContract(proContInfo);
			this.txtPCName.setText(this.editData.getProgrammingContract().getName());
		}
	}

	protected void loadBgEntryTable(){
		ContractWithoutTextBgEntryCollection col=editData.getBgEntry();
		CRMHelper.sortCollection(col, "seq", true);
		this.kdtBgEntry.removeRows();
		for(int i=0;i<col.size();i++){
			ContractWithoutTextBgEntryInfo entry=col.get(i);
			IRow row=this.kdtBgEntry.addRow();
			row.setUserObject(entry);
			if(entry.getExpenseType()!=null){
				try {
					row.getCell("expenseType").setValue(ExpenseTypeFactory.getRemoteInstance().getExpenseTypeInfo(new ObjectUuidPK(entry.getExpenseType().getId())));
				} catch (EASBizException e) {
					e.printStackTrace();
				} catch (BOSException e) {
					e.printStackTrace();
				}
			}
			row.getCell("accountView").setValue(entry.getAccountView());
			row.getCell("requestAmount").setValue(entry.getRequestAmount());
			row.getCell("amount").setValue(entry.getAmount());
			if(entry.getBgItem()!=null){
				try {
					row.getCell("bgItem").setValue(BgItemFactory.getRemoteInstance().getBgItemInfo(new ObjectUuidPK(entry.getBgItem().getId())));
				} catch (EASBizException e) {
					e.printStackTrace();
				} catch (BOSException e) {
					e.printStackTrace();
				}
			}
		}
	}
	protected void storeBgEntryTable(){
		BigDecimal amount=FDCHelper.ZERO;
		editData.getBgEntry().clear();
    	for(int i=0;i<this.kdtBgEntry.getRowCount();i++){
    		IRow row = this.kdtBgEntry.getRow(i);
    		ContractWithoutTextBgEntryInfo entry=(ContractWithoutTextBgEntryInfo) row.getUserObject();
    		entry.setSeq(i);
    		entry.setExpenseType((ExpenseTypeInfo)row.getCell("expenseType").getValue());
    		entry.setAccountView((AccountViewInfo)row.getCell("accountView").getValue());
    		entry.setRequestAmount((BigDecimal)row.getCell("requestAmount").getValue());
    		entry.setAmount((BigDecimal)row.getCell("amount").getValue());
    		entry.setBgItem((BgItemInfo)row.getCell("bgItem").getValue());
    		editData.getBgEntry().add(entry);
    		
    		amount=FDCHelper.add(amount,row.getCell("requestAmount").getValue());
    	}
    	this.txtamount.setValue(amount);
	}
	protected void initBgEntryTable(){
		KDWorkButton btnAddRowinfo=new KDWorkButton();
    	KDWorkButton btnInsertRowinfo=new KDWorkButton();
    	KDWorkButton btnDeleteRowinfo=new KDWorkButton();
    	
    	this.actionAddLine.putValue("SmallIcon", EASResource.getIcon("imgTbtn_addline"));
		btnAddRowinfo = (KDWorkButton)this.contBgEntry.add(this.actionAddLine);
		btnAddRowinfo.setText("������");
		btnAddRowinfo.setSize(new Dimension(140, 19));
		
		this.actionInsertLine.putValue("SmallIcon", EASResource.getIcon("imgTbtn_insert"));
		btnInsertRowinfo = (KDWorkButton)this.contBgEntry.add(this.actionInsertLine);
		btnInsertRowinfo.setText("������");
		btnInsertRowinfo.setSize(new Dimension(140, 19));
		
		this.actionRemoveLine.putValue("SmallIcon", EASResource.getIcon("imgTbtn_deleteline"));
		btnDeleteRowinfo = (KDWorkButton)this.contBgEntry.add(this.actionRemoveLine);
		btnDeleteRowinfo.setText("ɾ����");
		btnDeleteRowinfo.setSize(new Dimension(140, 19));
		
		this.kdtBgEntry.checkParsed();
		
		CompanyOrgUnitInfo curCompany = company;
		EntityViewInfo view = this.getAccountEvi(curCompany);
		AccountPromptBox dialog = new AccountPromptBox(this, curCompany,view.getFilter(), false, true);
		KDBizPromptBox f7Box = new KDBizPromptBox();
		KDTDefaultCellEditor f7Editor = new KDTDefaultCellEditor(f7Box);
		f7Box.setDialog(dialog);
		f7Box.setEditFormat("$number$");
        f7Box.setDisplayFormat("$name$");
        f7Box.setCommitFormat("$number$");
        
		f7Editor = new KDTDefaultCellEditor(f7Box);
		this.kdtBgEntry.getColumn("accountView").setEditor(f7Editor);
		
		f7Box = new KDBizPromptBox();
		f7Box.setQueryInfo("com.kingdee.eas.cp.bc.app.F7ExpenseTypeQuery");
        ExpenseTypePromptBox selector = new ExpenseTypePromptBox(this);
        selector.setMultiSelect(false);
        f7Box.setSelector(selector);
        f7Box.setEnabledMultiSelection(false);
        f7Box.setEditFormat("$number$");
        f7Box.setDisplayFormat("$typeName$-$number$");
        f7Box.setCommitFormat("$number$");
        f7Editor = new KDTDefaultCellEditor(f7Box);
		this.kdtBgEntry.getColumn("expenseType").setEditor(f7Editor);
		
		KDFormattedTextField txtWeight = new KDFormattedTextField();
		txtWeight.setDataType(KDFormattedTextField.BIGDECIMAL_TYPE);
		txtWeight.setDataVerifierType(KDFormattedTextField.NO_VERIFIER);
		txtWeight.setPrecision(2);
		txtWeight.setMinimumValue(FDCHelper.ZERO);
		KDTDefaultCellEditor weight = new KDTDefaultCellEditor(txtWeight);
		this.kdtBgEntry.getColumn("requestAmount").setEditor(weight);
		this.kdtBgEntry.getColumn("requestAmount").getStyleAttributes().setNumberFormat("#0.00");
		this.kdtBgEntry.getColumn("requestAmount").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
		
		this.kdtBgEntry.getColumn("amount").setEditor(weight);
		this.kdtBgEntry.getColumn("amount").getStyleAttributes().setNumberFormat("#0.00");
		this.kdtBgEntry.getColumn("amount").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
		this.kdtBgEntry.getColumn("amount").getStyleAttributes().setHided(true);
		
		f7Box = new KDBizPromptBox();
		f7Editor = new KDTDefaultCellEditor(f7Box);
		f7Box.setDisplayFormat("$name$");
		f7Box.setEditFormat("$name$");
		f7Box.setCommitFormat("$name$");
		NewBgItemDialog bgItemDialog=new NewBgItemDialog(this);
		bgItemDialog.setMulSelect(false);
		bgItemDialog.setSelectCombinItem(false);
		f7Box.setSelector(bgItemDialog);
		f7Editor = new KDTDefaultCellEditor(f7Box);
		this.kdtBgEntry.getColumn("bgItem").setEditor(f7Editor);
		this.kdtBgEntry.getColumn("bgItem").getStyleAttributes().setLocked(true);
		this.kdtBgEntry.getColumn("bgItem").getStyleAttributes().setHided(true);
		
		
		this.actionAddLine.putValue("SmallIcon", EASResource.getIcon("imgTbtn_addline"));
		btnAddRowinfo = (KDWorkButton)this.contFeeEntry.add(this.actionAddLine);
		btnAddRowinfo.setText("������");
		btnAddRowinfo.setSize(new Dimension(140, 19));
		
		this.actionInsertLine.putValue("SmallIcon", EASResource.getIcon("imgTbtn_insert"));
		btnInsertRowinfo = (KDWorkButton)this.contFeeEntry.add(this.actionInsertLine);
		btnInsertRowinfo.setText("������");
		btnInsertRowinfo.setSize(new Dimension(140, 19));
		
		this.actionRemoveLine.putValue("SmallIcon", EASResource.getIcon("imgTbtn_deleteline"));
		btnDeleteRowinfo = (KDWorkButton)this.contFeeEntry.add(this.actionRemoveLine);
		btnDeleteRowinfo.setText("ɾ����");
		btnDeleteRowinfo.setSize(new Dimension(140, 19));
		
		this.actionAddLine.putValue("SmallIcon", EASResource.getIcon("imgTbtn_addline"));
		btnAddRowinfo = (KDWorkButton)this.contTraEntry.add(this.actionAddLine);
		btnAddRowinfo.setText("������");
		btnAddRowinfo.setSize(new Dimension(140, 19));
		
		this.actionInsertLine.putValue("SmallIcon", EASResource.getIcon("imgTbtn_insert"));
		btnInsertRowinfo = (KDWorkButton)this.contTraEntry.add(this.actionInsertLine);
		btnInsertRowinfo.setText("������");
		btnInsertRowinfo.setSize(new Dimension(140, 19));
		
		this.actionRemoveLine.putValue("SmallIcon", EASResource.getIcon("imgTbtn_deleteline"));
		btnDeleteRowinfo = (KDWorkButton)this.contTraEntry.add(this.actionRemoveLine);
		btnDeleteRowinfo.setText("ɾ����");
		btnDeleteRowinfo.setSize(new Dimension(140, 19));
	}

	protected void kdtBgEntry_editStopped(KDTEditEvent e) throws Exception {
		if(this.kdtBgEntry.getColumnKey(e.getColIndex()).equals("requestAmount")){
			if(this.kdtBgEntry.getRow(e.getRowIndex()).getCell("requestAmount").getValue()!=null){
				this.kdtBgEntry.getRow(e.getRowIndex()).getCell("amount").setValue(this.kdtBgEntry.getRow(e.getRowIndex()).getCell("requestAmount").getValue());
			}
		}
//		else if(this.kdtBgEntry.getColumnKey(e.getColIndex()).equals("expenseType")){
//			getBgAmount();
//		}
		else if(this.kdtBgEntry.getColumnKey(e.getColIndex()).equals("bgItem")){
			if(this.kdtBgEntry.getRow(e.getRowIndex()).getCell("bgItem").getValue() instanceof BgItemObject){
				BgItemObject bgItem=(BgItemObject) this.kdtBgEntry.getRow(e.getRowIndex()).getCell("bgItem").getValue();
				if(bgItem!=null){
					if(!bgItem.getResult().get(0).isIsLeaf()){
						FDCMsgBox.showWarning(this,"��ѡ����ϸԤ����Ŀ��");
						this.kdtBgEntry.getRow(e.getRowIndex()).getCell("bgItem").setValue(null);
					}else{
						this.kdtBgEntry.getRow(e.getRowIndex()).getCell("bgItem").setValue(bgItem.getResult().get(0));
					}
				}
			}
			getBgAmount();
		}else if(this.kdtBgEntry.getColumnKey(e.getColIndex()).equals("expenseType")){
			ExpenseTypeInfo info=(ExpenseTypeInfo) this.kdtBgEntry.getRow(e.getRowIndex()).getCell("expenseType").getValue();
			if(info!=null){
				BgItemInfo bgItem=null;
				FDCSQLBuilder _builder = new FDCSQLBuilder();
				_builder.appendSql(" select bgItem.fid id from T_BG_BgItem bgItem where bgItem.fnumber in(");
				_builder.appendSql(" select map.fbgItemCombinKey from T_BG_BgControlItemMap map left join T_BG_BgControlScheme scheme on scheme.fid=map.FBgCtrlSchemeID");
				_builder.appendSql(" where scheme.fboName='com.kingdee.eas.fi.cas.app.PaymentBill' and scheme.fisValid=1 and scheme.fisSysDefault=0");
				_builder.appendSql(" and scheme.fcostCenterId='"+this.editData.getOrgUnit().getId().toString()+"'");
				_builder.appendSql(" and SUBSTRING(map.fbillItemCombinValue,charindex(':',map.fbillItemCombinValue)+1,length(map.fbillItemCombinValue)-charindex(':',map.fbillItemCombinValue)) ='"+info.getNumber()+"'");
				_builder.appendSql(" group by map.fbgItemCombinKey)");
				final IRowSet rowSet = _builder.executeQuery();
				while (rowSet.next()) {
					bgItem=BgItemFactory.getRemoteInstance().getBgItemInfo(new ObjectUuidPK(rowSet.getString("id")));
					break;
				}
				this.kdtBgEntry.getRow(e.getRowIndex()).getCell("bgItem").setValue(bgItem);
			}
			getBgAmount();
		}
		if(this.kdtBgEntry.getColumnKey(e.getColIndex()).equals("requestAmount")||this.kdtBgEntry.getColumnKey(e.getColIndex()).equals("amount")){
			BigDecimal amount =TableUtils.getColumnValueSum(this.kdtBgEntry, "requestAmount");
			this.txtamount.setValue(amount);
			TableUtils.getFootRow(this.kdtBgEntry,new String[]{"requestAmount","amount"});
		}
	}
	private KDTextField txtBgAmount=null;
	protected void kdtBgEntry_tableSelectChanged(KDTSelectEvent e) throws Exception {
		getBgAmount();
	}

	public void actionAddLine_actionPerformed(ActionEvent e) throws Exception {
		if(this.kDTabbedPane1.getSelectedComponent().equals(this.contBgEntry)){
			IRow row=this.kdtBgEntry.addRow();
			ContractWithoutTextBgEntryInfo entry=new ContractWithoutTextBgEntryInfo();
			row.setUserObject(entry);
		}else if(this.kDTabbedPane1.getSelectedComponent().equals(this.contFeeEntry)){
			IRow row=this.kdtFeeEntry.addRow();
			FeeEntryInfo entry=new FeeEntryInfo();
			row.setUserObject(entry);
		}else if(this.kDTabbedPane1.getSelectedComponent().equals(this.contTraEntry)){
			IRow row=this.kdtTraEntry.addRow();
			TraEntryInfo entry=new TraEntryInfo();
			row.setUserObject(entry);
		}
	}
	public void actionInsertLine_actionPerformed(ActionEvent e) throws Exception {
		if(this.kDTabbedPane1.getSelectedComponent().equals(this.contBgEntry)){
			 IRow row = null;
			 if(this.kdtBgEntry.getSelectManager().size() > 0){
				 int top = this.kdtBgEntry.getSelectManager().get().getTop();
				 if(isTableColumnSelected(this.kdtBgEntry))
					 row = this.kdtBgEntry.addRow();
				 else
					 row = this.kdtBgEntry.addRow(top);
		     }else{
		    	 row = this.kdtBgEntry.addRow();
		     }
			 ContractWithoutTextBgEntryInfo entry=new ContractWithoutTextBgEntryInfo();
			 row.setUserObject(entry);
		}else if(this.kDTabbedPane1.getSelectedComponent().equals(this.contFeeEntry)){
			 IRow row = null;
			 if(this.kdtFeeEntry.getSelectManager().size() > 0){
				 int top = this.kdtFeeEntry.getSelectManager().get().getTop();
				 if(isTableColumnSelected(this.kdtFeeEntry))
					 row = this.kdtFeeEntry.addRow();
				 else
					 row = this.kdtFeeEntry.addRow(top);
		     }else{
		    	 row = this.kdtFeeEntry.addRow();
		     }
			 FeeEntryInfo entry=new FeeEntryInfo();
			 row.setUserObject(entry);
		}else if(this.kDTabbedPane1.getSelectedComponent().equals(this.contTraEntry)){
			 IRow row = null;
			 if(this.kdtTraEntry.getSelectManager().size() > 0){
				 int top = this.kdtTraEntry.getSelectManager().get().getTop();
				 if(isTableColumnSelected(this.kdtTraEntry))
					 row = this.kdtTraEntry.addRow();
				 else
					 row = this.kdtTraEntry.addRow(top);
		     }else{
		    	 row = this.kdtTraEntry.addRow();
		     }
			 TraEntryInfo entry=new TraEntryInfo();
			 row.setUserObject(entry);
		}
	}
	public void actionRemoveLine_actionPerformed(ActionEvent e) throws Exception {
		if(this.kDTabbedPane1.getSelectedComponent().equals(this.contBgEntry)){
			if(this.kdtBgEntry.getSelectManager().size() == 0 || isTableColumnSelected(this.kdtBgEntry)){
	            FDCMsgBox.showInfo(this, EASResource.getString("com.kingdee.eas.framework.FrameWorkResource.Msg_NoneEntry"));
	            return;
	        }
	        if(FDCMsgBox.isYes(FDCMsgBox.showConfirm2(this, EASResource.getString("com.kingdee.eas.framework.FrameWorkResource.Confirm_Delete")))){
	            int top = this.kdtBgEntry.getSelectManager().get().getBeginRow();
	            int bottom = this.kdtBgEntry.getSelectManager().get().getEndRow();
	            for(int i = top; i <= bottom; i++){
	                if(this.kdtBgEntry.getRow(top) == null)
	                {
	                    FDCMsgBox.showInfo(this, EASResource.getString("com.kingdee.eas.framework.FrameWorkResource.Msg_NoneEntry"));
	                    return;
	                }
	                this.kdtBgEntry.removeRow(top);
	                BigDecimal amount =TableUtils.getColumnValueSum(this.kdtBgEntry, "requestAmount");
	    			this.txtamount.setValue(amount);
	                TableUtils.getFootRow(this.kdtBgEntry,new String[]{"requestAmount","amount"});
	            }
	        }
		}else if(this.kDTabbedPane1.getSelectedComponent().equals(this.contFeeEntry)){
			if(this.kdtFeeEntry.getSelectManager().size() == 0 || isTableColumnSelected(this.kdtFeeEntry)){
	            FDCMsgBox.showInfo(this, EASResource.getString("com.kingdee.eas.framework.FrameWorkResource.Msg_NoneEntry"));
	            return;
	        }
	        if(FDCMsgBox.isYes(FDCMsgBox.showConfirm2(this, EASResource.getString("com.kingdee.eas.framework.FrameWorkResource.Confirm_Delete")))){
	            int top = this.kdtFeeEntry.getSelectManager().get().getBeginRow();
	            int bottom = this.kdtFeeEntry.getSelectManager().get().getEndRow();
	            for(int i = top; i <= bottom; i++){
	                if(this.kdtFeeEntry.getRow(top) == null)
	                {
	                    FDCMsgBox.showInfo(this, EASResource.getString("com.kingdee.eas.framework.FrameWorkResource.Msg_NoneEntry"));
	                    return;
	                }
	                this.kdtFeeEntry.removeRow(top);
	                TableUtils.getFootRow(this.kdtBgEntry,new String[]{"amount"});
	            }
	        }
		}else if(this.kDTabbedPane1.getSelectedComponent().equals(this.contTraEntry)){
			if(this.kdtTraEntry.getSelectManager().size() == 0 || isTableColumnSelected(this.kdtTraEntry)){
	            FDCMsgBox.showInfo(this, EASResource.getString("com.kingdee.eas.framework.FrameWorkResource.Msg_NoneEntry"));
	            return;
	        }
	        if(FDCMsgBox.isYes(FDCMsgBox.showConfirm2(this, EASResource.getString("com.kingdee.eas.framework.FrameWorkResource.Confirm_Delete")))){
	            int top = this.kdtTraEntry.getSelectManager().get().getBeginRow();
	            int bottom = this.kdtTraEntry.getSelectManager().get().getEndRow();
	            for(int i = top; i <= bottom; i++){
	                if(this.kdtTraEntry.getRow(top) == null)
	                {
	                    FDCMsgBox.showInfo(this, EASResource.getString("com.kingdee.eas.framework.FrameWorkResource.Msg_NoneEntry"));
	                    return;
	                }
	                this.kdtTraEntry.removeRow(top);
	                TableUtils.getFootRow(this.kdtTraEntry,new String[]{"days","airFee","carFee","cityFee","otherFee","persons","liveDays","liveFee","allowance","other","total"});
	            }
	        }
		}
	}
	protected void prmtCostedCompany_dataChanged(DataChangeEvent e) throws Exception {
		EntityViewInfo view=new EntityViewInfo();
		FilterInfo filter=new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("isBizUnit",Boolean.TRUE));
		filter.getFilterItems().add(new FilterItemInfo("isSealUp",Boolean.FALSE));
		CompanyOrgUnitInfo com=(CompanyOrgUnitInfo)this.prmtCostedCompany.getValue();
		if(com!=null){
			if(!com.isIsLeaf()){
				FDCMsgBox.showWarning(this,"��ѡ����ϸԤ��е���˾��");
				this.prmtCostedCompany.setValue(null);
				return;
			}
			Set id=getCostedDeptIdSet(com);
			if(this.prmtCostedDept.getValue()!=null){
				if(id.contains(((CostCenterOrgUnitInfo)prmtCostedDept.getValue()).getId().toString())){
					return;
				}else{
					this.prmtCostedDept.setValue(null);
				}
			}
			filter.getFilterItems().add(new FilterItemInfo("id",getCostedDeptIdSet(com),CompareType.INCLUDE));
			this.prmtCostedDept.setEnabled(true);
		}else{
			this.prmtCostedDept.setValue(null);
			this.prmtCostedDept.setEnabled(false);
		}
		view.setFilter(filter);
        this.prmtCostedDept.setQueryInfo("com.kingdee.eas.basedata.org.app.CostCenterOrgUnitQuery");
        this.prmtCostedDept.setEntityViewInfo(view);
        this.prmtCostedDept.setEditFormat("$number$");
        this.prmtCostedDept.setDisplayFormat("$name$");
        this.prmtCostedDept.setCommitFormat("$number$");
	}
	protected void prmtCostedDept_dataChanged(DataChangeEvent e) throws Exception {
         getBgAmount();
	}
	private boolean isShowBgAmount=false;
	
	protected BigDecimal getAccActOnLoadBgAmount(String bgItemNumber,boolean isFromWorkFlow) throws BOSException, SQLException{
		if(this.prmtCostedDept.getValue()==null) return FDCHelper.ZERO;
		
		String bgComItem="";
		Set bgComItemSet=new HashSet();
		bgComItemSet.add(bgItemNumber);
		FDCSQLBuilder _builder = new FDCSQLBuilder();
		_builder.appendSql(" select distinct t2.fformula fformula from T_BG_BgTemplateCtrlSetting t1 left join T_BG_BgTemplateCtrlSetting t2 on t1.fgroupno=t2.fgroupno where t1.FOrgUnitId ='"+((CostCenterOrgUnitInfo)this.prmtCostedDept.getValue()).getId().toString()+"'");
		_builder.appendSql(" and t1.fgroupno!='-1' and t2.fgroupno!='-1' and t1.fformula like '%"+bgItemNumber+"%' and t2.fformula not like '%"+bgItemNumber+"%'");
		IRowSet rowSet = _builder.executeQuery();
		
		while(rowSet.next()){
			if(rowSet.getString("fformula")!=null){
				String formula=rowSet.getString("fformula");
				bgComItemSet.add(formula.substring(9, formula.indexOf(",")-1));
			}
		}
		Object[] bgObject = bgComItemSet.toArray();
    	for(int i=0;i<bgObject.length;i++){
        	if(i==0){
        		bgComItem=bgComItem+"'"+bgObject[i].toString()+"'";
        	}else{
        		bgComItem=bgComItem+",'"+bgObject[i].toString()+"'";
        	}
        }
		_builder = new FDCSQLBuilder();
		_builder.appendSql(" select sum(entry.frequestAmount-isnull(entry.factPayAmount,0))payAmount from T_CON_PayRequestBill bill left join T_CON_PayRequestBillBgEntry entry on entry.fheadid=bill.fid ");
		_builder.appendSql(" left join T_BG_BgItem bgItem on bgItem.fid=entry.fbgitemid ");
		_builder.appendSql(" where bill.fisBgControl=1 and bill.FCostedDeptId='"+((CostCenterOrgUnitInfo)this.prmtCostedDept.getValue()).getId().toString()+"' and bgItem.fnumber in("+bgComItem+")");
		if(isFromWorkFlow){
			_builder.appendSql(" and bill.fstate in('3AUDITTING','4AUDITTED') ");
		}else{
			_builder.appendSql(" and bill.fstate in('2SUBMITTED','3AUDITTING','4AUDITTED') ");
		}
		_builder.appendSql(" and bill.fhasClosed=0 and bill.famount is not null");
		
		if(editData.getId()!=null){
			_builder.appendSql(" and bill.fcontractid!='"+editData.getId().toString()+"'");
		}
		rowSet = _builder.executeQuery();
		while(rowSet.next()){
			if(rowSet.getBigDecimal("payAmount")!=null)
				return rowSet.getBigDecimal("payAmount");
		}
		return FDCHelper.ZERO;
	}
	protected void getPayPlanValue() throws BOSException, SQLException, EASBizException{
		this.txtPayPlanValue.setText(null);
		if (!STATUS_ADDNEW.equals(getOprtState()) && !STATUS_EDIT.equals(getOprtState())) {
			return;
		}
		if(this.editData.getProgrammingContract()==null||this.pkbookedDate.getValue()==null){
			return;
		}
		HashMap hmParamIn = new HashMap();
		hmParamIn.put("CIFI_PAYPLAN", editData.getCurProject().getFullOrgUnit().getId().toString());
		HashMap hmAllParam = ParamControlFactory.getRemoteInstance().getParamHashMap(hmParamIn);
		if(hmAllParam.get("CIFI_PAYPLAN")==null||!Boolean.valueOf(hmAllParam.get("CIFI_PAYPLAN").toString()).booleanValue()){
			return;
		}
		BigDecimal amount=FDCHelper.ZERO;
	
		Calendar cal = Calendar.getInstance();
		cal.setTime((Date)this.pkbookedDate.getValue());
		int year=cal.get(Calendar.YEAR);
		int month=cal.get(Calendar.MONTH)+1;
		
		EntityViewInfo view=new EntityViewInfo();
    	FilterInfo filter=new FilterInfo();
    	
    	filter.getFilterItems().add(new FilterItemInfo("programmingContract.id",this.editData.getProgrammingContract().getId().toString()));
    	filter.getFilterItems().add(new FilterItemInfo("head.state",FDCBillStateEnum.CONFIRMED_VALUE));
    	filter.getFilterItems().add(new FilterItemInfo("head.isLatest",Boolean.TRUE));
    	
    	SorterItemInfo bizDateSort=new SorterItemInfo("head.bizDate");
    	bizDateSort.setSortType(SortType.DESCEND);
    	view.getSorter().add(bizDateSort);
    	view.getSelector().add("dateEntry.*");
    	view.setFilter(filter);
    	
    	ProjectMonthPlanGatherEntryCollection col=ProjectMonthPlanGatherEntryFactory.getRemoteInstance().getProjectMonthPlanGatherEntryCollection(view);
    	if(col.size()>0){
    		ProjectMonthPlanGatherEntryInfo ppEntry=col.get(0);
    		for(int i=0;i<ppEntry.getDateEntry().size();i++){
    			int comYear=ppEntry.getDateEntry().get(i).getYear();
    			int comMonth=ppEntry.getDateEntry().get(i).getMonth();
    			if(comYear==year&&comMonth==month){
    				amount=ppEntry.getDateEntry().get(i).getAmount();
    			}
    		}
    	}
    	if(amount.toString().equals("0E-10")){
    		amount=FDCHelper.ZERO;
    	}
		this.txtPayPlanValue.setText("���¸���ƻ���"+amount.setScale(2,BigDecimal.ROUND_HALF_UP).toString());
	}
	protected PaymentBillInfo createTempPaymentBill() throws BOSException{
		PaymentBillInfo pay=new PaymentBillInfo();
		for(int i=0;i<this.kdtBgEntry.getRowCount();i++){
			PaymentBillEntryInfo entry=new PaymentBillEntryInfo();
			BigDecimal requestAmount=(BigDecimal) this.kdtBgEntry.getRow(i).getCell("requestAmount").getValue();
			
			entry.setAmount(requestAmount);
			entry.setLocalAmt(requestAmount);
            entry.setActualAmt(requestAmount);
            entry.setActualLocAmt(requestAmount);
            entry.setCurrency((CurrencyInfo) this.prmtcurrency.getValue());
            entry.setExpenseType((ExpenseTypeInfo) this.kdtBgEntry.getRow(i).getCell("expenseType").getValue());
            entry.setCostCenter((CostCenterOrgUnitInfo) this.prmtCostedDept.getValue());
            pay.getEntries().add(entry);
		}
		pay.setCompany((CompanyOrgUnitInfo)this.prmtCostedCompany.getValue());
		pay.setCostCenter((CostCenterOrgUnitInfo) this.prmtCostedDept.getValue());
		pay.setPayDate(FDCCommonServerHelper.getServerTimeStamp());
//		pay.setBizDate(FDCCommonServerHelper.getServerTimeStamp());
		pay.setCurrency((CurrencyInfo) this.prmtcurrency.getValue());
		
		return pay;
	}
	protected void getBgAmount() throws EASBizException, BOSException, SQLException{
		if(isShowBgAmount){
			
			String amount=null;
	        if(this.kdtBgEntry.getSelectManager().size() > 0)
	        {
	            int top = this.kdtBgEntry.getSelectManager().get().getTop();
	            if(isTableColumnSelected(this.kdtBgEntry)){
	            	if(txtBgAmount!=null){
	            		txtBgAmount.setVisible(false);
	            	}
	                return;
	            }
	            if(this.kdtBgEntry.getRow(top) == null){
	            	if(txtBgAmount!=null){
	            		txtBgAmount.setVisible(false);
	            	}
	                return;
	            }
	            if(this.kdtBgEntry.getRow(top).getCell("expenseType").getValue() != null && this.kdtBgEntry.getRow(top).getCell("bgItem").getValue() != null && this.prmtCostedDept.getValue() != null
						&& this.prmtCostedCompany.getValue() != null){
	            	if(txtBgAmount==null){
	            		txtBgAmount=new KDTextField();
	            		txtBgAmount.setEnabled(false);
	            		this.contBgEntry.add(txtBgAmount);
	            		txtBgAmount.setBounds(400, 2, 400, 18);
	            	}
	            	try {
						beforeStoreFields(null);
					} catch (Exception e) {
						e.printStackTrace();
					}
					this.storeFields();
					BgItemInfo bgItem=(BgItemInfo)this.kdtBgEntry.getRow(top).getCell("bgItem").getValue();
					
	    			IBgControlFacade iBgControlFacade = BgControlFacadeFactory.getRemoteInstance();
	    	        BgCtrlResultCollection coll = iBgControlFacade.getBudget("com.kingdee.eas.fi.cas.app.PaymentBill", new BgCtrlParamCollection(),createTempPaymentBill());
	    	        if(coll.size()>0){
	    	        	for(int i=0;i<coll.size();i++){
	    	        		if(bgItem.getNumber().equals(coll.get(i).getItemCombinNumber())){
	    	        			BigDecimal balanceAmount=FDCHelper.ZERO;
	    						if(coll.get(i).getBalance() != null){
	    							balanceAmount=coll.get(i).getBalance();
	    						}
	    	        			BigDecimal balance=balanceAmount.subtract(getAccActOnLoadBgAmount(bgItem.getNumber(),true));
	    	        			amount="����:"+editData.getCostedDept().getName()+" Ԥ����Ŀ:"+bgItem.getName()+" Ԥ�����:"+balance.toString();
	    	        			break;
	    	        		}
	    	        	}
	    	        }
	            	if(amount!=null){
	            		txtBgAmount.setVisible(true);
	                	txtBgAmount.setText(amount);
	            	}else{
	            		txtBgAmount.setVisible(false);
	            	}
	            }else{
	            	if(txtBgAmount!=null){
	            		txtBgAmount.setVisible(false);
	            	}
	            }
	        }
		}
	}
	protected void cbIsInvoice_itemStateChanged(ItemEvent e) throws Exception {
//		if(this.cbIsInvoice.isSelected()){
//			this.txtInvoiceNumber.setEnabled(true);
//			this.txtInvoiceAmt.setEnabled(true);
//			this.pkInvoiceDate.setEnabled(true);
//		}else{
//			this.txtInvoiceNumber.setEnabled(false);
//			this.txtInvoiceAmt.setEnabled(false);
//			this.pkInvoiceDate.setEnabled(false);
//		}
	}
	protected void cbIsBgControl_itemStateChanged(ItemEvent e) throws Exception {
		if(this.cbIsBgControl.isSelected()){
			if(!this.getOprtState().equals(OprtState.VIEW)){
				if(this.kdtBgEntry.getRowCount()==0){
					IRow row=this.kdtBgEntry.addRow();
					ContractWithoutTextBgEntryInfo entry=new ContractWithoutTextBgEntryInfo();
					row.setUserObject(entry);
				}
//				if(this.editData.getProgrammingContract()!=null){
//					SelectorItemCollection sel=new SelectorItemCollection();
//					sel.add("costEntries.costAccount.longNumber");
//					ProgrammingContractInfo pc=ProgrammingContractFactory.getRemoteInstance().getProgrammingContractInfo(new ObjectUuidPK(this.editData.getProgrammingContract().getId()),sel);
//					Set costAccount=new HashSet();
//					for(int i=0;i<pc.getCostEntries().size();i++){
//						costAccount.add(pc.getCostEntries().get(i).getCostAccount().getLongNumber());
//					}
//					row.getCell("bgItem").setValue(CostAccountWithBgItemFactory.getRemoteInstance().getBgItem(costAccount));
//				}
			}
			this.actionAddLine.setEnabled(true);
			this.actionInsertLine.setEnabled(true);
			this.actionRemoveLine.setEnabled(true);
			
			this.kdtBgEntry.getColumn("expenseType").getStyleAttributes().setLocked(false);
			this.kdtBgEntry.getColumn("accountView").getStyleAttributes().setLocked(false);
			this.kdtBgEntry.getColumn("requestAmount").getStyleAttributes().setLocked(false);
			
//			this.prmtApplier.setEnabled(true);
			this.prmtApplierOrgUnit.setEnabled(true);
			this.prmtCostedCompany.setEnabled(true);
			this.prmtCostedDept.setEnabled(true);
			
			BigDecimal amount =TableUtils.getColumnValueSum(this.kdtBgEntry, "requestAmount");
			this.txtamount.setValue(amount);
			this.txtamount.setEnabled(false);
			
			if(this.prmtCostedCompany.getValue()!=null){
				this.prmtCostedDept.setEnabled(true);
			}else{
				this.prmtCostedDept.setEnabled(false);
			}
		}else{
			if(!this.getOprtState().equals(OprtState.VIEW)){
				this.kdtBgEntry.removeRows();
			}
			
			this.actionAddLine.setEnabled(false);
			this.actionInsertLine.setEnabled(false);
			this.actionRemoveLine.setEnabled(false);
			
			this.kdtBgEntry.getColumn("expenseType").getStyleAttributes().setLocked(true);
			this.kdtBgEntry.getColumn("accountView").getStyleAttributes().setLocked(true);
			this.kdtBgEntry.getColumn("requestAmount").getStyleAttributes().setLocked(true);
//			this.prmtApplier.setEnabled(false);
			this.prmtApplierOrgUnit.setEnabled(false);
			this.prmtCostedCompany.setEnabled(false);
			this.prmtCostedDept.setEnabled(false);
			
			this.txtamount.setEnabled(true);
		}
	}
	protected EntityViewInfo getAccountEvi(CompanyOrgUnitInfo companyInfo){
		EntityViewInfo evi = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("companyID.id", companyInfo.getId().toString()));
		if (companyInfo.getAccountTable() != null){
			filter.getFilterItems().add(new FilterItemInfo("accountTableID.id", companyInfo.getAccountTable().getId().toString()));
		}
		filter.getFilterItems().add(new FilterItemInfo("isGFreeze", Boolean.FALSE));
		evi.setFilter(filter);
		return evi;
	}
	private void afterOnload() throws BOSException, EASBizException {		
		
		txtAllInvoiceAmt.setMaximumValue(FDCHelper.MAX_DECIMAL);
		
		//���ú�ͬ����F7
		prmtContractType.setSelector(new ContractTypePromptSelector(this));
		
		//���ݲ����Ƿ���ʾ��ͬ������Ŀ
		if(!isShowCharge){
			this.conConCharge.setVisible(false);
			this.prmtConCharge.setRequired(false);
			this.actionViewBgBalance.setVisible(false);
			this.actionViewBgBalance.setEnabled(false);
			this.menuItemViewBgBalance.setVisible(false);
			this.menuItemViewBgBalance.setEnabled(false);
		}else{
			this.conConCharge.setVisible(true);
			this.prmtConCharge.setRequired(true);
			this.actionViewBgBalance.setVisible(true);
			this.actionViewBgBalance.setEnabled(true);
			this.menuItemViewBgBalance.setVisible(true);
			this.menuItemViewBgBalance.setEnabled(true);
		}
		//ֻ��ʾ�����õĺ�ͬ������Ŀ
		EntityViewInfo view = this.prmtConCharge.getEntityViewInfo();
		if(view == null){
			view = new EntityViewInfo();
		}
		FilterInfo filterCharge = new FilterInfo();
		filterCharge.getFilterItems().add(new FilterItemInfo("isEnabled",new Integer(1)));
		view.setFilter(filterCharge);
		this.prmtConCharge.setEntityViewInfo(view);
		
		OrgUnitInfo orgUnitInfo = SysContext.getSysContext().getCurrentOrgUnit();
		EntityViewInfo v = new EntityViewInfo();
		FilterInfo filter  = new FilterInfo();
		//��ǰ���õĹ�����Ŀ
		Set projLeafNodesIdSet = (Set)getUIContext().get("projLeafNodesIdSet");
		if(projLeafNodesIdSet!=null && projLeafNodesIdSet.size()>0){
			filter.getFilterItems().add(new FilterItemInfo("id", projLeafNodesIdSet, CompareType.INCLUDE));			
		}else{
			filter.getFilterItems().add(new FilterItemInfo("isLeaf",new Integer(1)));
			filter.getFilterItems().add(new FilterItemInfo("fullOrgUnit.longNumber", orgUnitInfo.getLongNumber() + "%",CompareType.LIKE));
			filter.getFilterItems().add(new FilterItemInfo("fullOrgUnit.isCostOrgUnit", Boolean.TRUE));
			filter.getFilterItems().add(new FilterItemInfo("id", " select b.fid from T_FDC_ProjectWithCostCenterOU a "
					+" inner join t_fdc_curproject p on a.fcurprojectid=p.fid "
					+" inner join t_fdc_curproject b on charindex(p.FLongNumber || '!',b.FLongNumber || '!')=1 "
								+" where p.flevel=1 ", CompareType.INNER));
		}

		v.setFilter(filter);
		prmtcurProject.setEntityViewInfo(v);
		
		txtNumber.setMaxLength(80);
    	txtbillName.setMaxLength(80);
    	txtNoPaidReason.setMaxLength(40);
    	txtInvoiceNumber.setMaxLength(80);

    	txtMoneyDesc.setLineWrap(true);
    	this.cbPeriod.setQueryInfo("com.kingdee.eas.basedata.assistant.app.F7PeriodQuery");
    	
    	//����ԭ�ҽ��Ŀ�¼�뷶Χ
//        txtamount.setPrecision(2);
        txtamount.setMinimumValue(FDCHelper.MIN_VALUE);
        txtamount.setMaximumValue(FDCHelper.MAX_VALUE.multiply(FDCHelper.TEN));
        if(txtamount.getText()==null||"".equals(txtamount.getText())){
        	txtamount.setValue(FDCHelper.ZERO);
        }
        txtBcAmount.setPrecision(2);
        txtBcAmount.setMinimumValue(FDCHelper.MIN_VALUE);
        txtBcAmount.setMaximumValue(FDCHelper.MAX_VALUE.multiply(FDCHelper.TEN));
        
//        txtexchangeRate.setPrecision(2);
        txtexchangeRate.setMinimumValue(FDCHelper.MIN_VALUE);
        txtexchangeRate.setMaximumValue(FDCHelper.MAX_VALUE.multiply(FDCHelper.TEN));
    	
        txtcompletePrjAmt.setPrecision(2);
        //txtcompletePrjAmt.setMinimumValue(FDCHelper.MIN_TOTAL_VALUE);
        txtcompletePrjAmt.setMinimumValue(FDCHelper.ZERO);
        txtcompletePrjAmt.setMaximumValue(FDCHelper.MAX_TOTAL_VALUE.multiply(FDCHelper.TEN));
        
    	//if(isFirstLoad) isFirstLoad=false;
    	if(isFirstLoad) isFirstLoad=false;
    	PayRequestBillCollection prbc ;
    	if(!STATUS_ADDNEW.equals(getOprtState())) {
	    	//���ظ������뵥����
    		EntityViewInfo evi = new EntityViewInfo();
    		FilterInfo filterInfo = new FilterInfo();
    		filterInfo.getFilterItems().add(new FilterItemInfo("contractId",editData.getId().toString()));    		
    		//SelectorItemCollection selector = new SelectorItemCollection();
    		evi.getSelector().add(new SelectorItemInfo("*"));
    		evi.getSelector().add(new SelectorItemInfo("useDepartment.*"));
    		evi.getSelector().add(new SelectorItemInfo("currency.*"));
    		evi.getSelector().add(new SelectorItemInfo("supplier.*"));
    		evi.getSelector().add(new SelectorItemInfo("settlementType.*"));
    		evi.getSelector().add(new SelectorItemInfo("curProject.*"));
    		evi.getSelector().add(new SelectorItemInfo("paymentType.*"));
    		evi.getSelector().add(new SelectorItemInfo("realSupplier.*"));
    		evi.setFilter(filterInfo);
    		prbc = PayRequestBillFactory.getRemoteInstance().getPayRequestBillCollection(evi);
    		if(prbc.size()!=0){
    			this.prbi = prbc.get(0);
    		}
    		this.prmtPayment.setData(prbi.getPaymentType());
    		this.prmtuseDepartment.setData(prbi.getUseDepartment());
    		this.txtPaymentRequestBillNumber.setText(prbi.getNumber());
    		this.pksignDate.setValue(prbi.getPayDate());
    		this.prmtrealSupplier.setData(prbi.getRealSupplier());
    		this.prmtcurrency.setData(prbi.getCurrency());
    		if(prbi.getExchangeRate()!=null){
    			this.txtexchangeRate.setText(prbi.getExchangeRate().toString());
    		}
    		if(prbi.getCapitalAmount()!=null){
    			this.txtcapitalAmount.setText(prbi.getCapitalAmount());
    		}else{
    			BigDecimal localamount = editData.getAmount();
    			if(localamount.compareTo(FDCConstants.ZERO)!=0){
    				localamount = localamount.setScale(2,BigDecimal.ROUND_HALF_UP);
    			}
    			this.txtcapitalAmount.setText(FDCClientHelper.getChineseFormat(localamount,false));
    		}
    		if(prbi.getPaymentProportion()!=null){
//    			this.txtPaymentProportion.setText(prbi.getPaymentProportion().toString());
    			this.txtPaymentProportion.setValue(prbi.getPaymentProportion());
    		}
    		if(prbi.getCompletePrjAmt()!=null){
//    			this.txtcompletePrjAmt.setText(prbi.getCompletePrjAmt().toString());
    			txtcompletePrjAmt.setValue(prbi.getCompletePrjAmt());
    		}
    		this.txtMoneyDesc.setText(prbi.getMoneyDesc()); 
    		if(prbi.getOriginalAmount()!=null){
    			txtamount.setValue(prbi.getOriginalAmount());
    			//this.txtamount.setText(prbi.getAmount().toString());//ԭ�ҽ��
//    			this.setAmount();
    		}
    		
    		if(chkUrgency!=null){
    			if(prbi.getUrgentDegree()==UrgentDegreeEnum.URGENT){
    	    		chkUrgency.setSelected(true);
    	    	}else{
    	    		chkUrgency.setSelected(false);
    	    	}
    		}
    		
   			this.txtattachment.setText(String.valueOf(prbi.getAttachment()));   			
			txtBankAcct.setText(prbi.getRecAccount());
			txtBank.setText(prbi.getRecBank());
    			
    		//�ύ��,�������޸���Ŀ
    		if(!this.editData.getState().getValue().equals(FDCBillStateEnum.SAVED_VALUE)){
    			this.prmtcurProject.setEnabled(false);
    		}
    		//add by jianxing_zhou
    		addListener();
    	}
        
    	this.txtPaymentProportion.setEditable(false);
		this.txtcompletePrjAmt.setEditable(false);		
		this.cbPeriod.setEnabled(false);
		
		if(getOprtState()!=OprtState.ADDNEW&&getOprtState()!=OprtState.EDIT){
			chkUrgency.setEnabled(false);
			if(getOprtState()==OprtState.VIEW){
				chkCostsplit.setEnabled(false);
			}
			
			chkNeedPaid.setEnabled(false);
			txtNoPaidReason.setEnabled(false);
			prmtAccount.setEnabled(false);
		
		}
		
		String cuId = editData.getCU().getId().toString();	
		FDCClientUtils.setRespDeptF7(prmtuseDepartment, this, canSelecOtherOrgPerson() ? null :cuId);
		//��ʼ����Ӧ��F7
		FDCClientUtils.initSupplierF7(this, prmtrealSupplier, cuId);

		//�����ò���ɱ�һ�廯����ʱ������ѡ�����踶����������������Ŀ���ֶΣ������󣬸��ֶν���Զ����롰�����Ŀ������Ӧ�ĸ�����Զ����Ѹ��״̬��
		//��������һ�廯����������ѡ�����踶������ı���ͬ�����󣬶�Ӧ�ĸ���Զ���Ϊ���Ѹ����
		if(isFinacial){
			kDLabelContainer16.setVisible(true);
		}else{
			kDLabelContainer16.setVisible(false);
		}
		
	    setPrecision();
		
		txtBank.setRequired(false);
		txtBankAcct.setRequired(false); 
		
		//���ı���ͬ¼��ʱ���ú�ͬ����Ϊ��¼���������ͷǱ�¼
		prmtContractType.setRequired(true);
		prmtPayment.setRequired(true);
		prmtsettlementType.setRequired(false);
		handleOldData();
		this.actionPrint.setEnabled(true);
		this.actionPrint.setVisible(true);
		this.actionPrintPreview.setEnabled(true);
		this.actionPrintPreview.setVisible(true);
        
        //ҵ�������ж�Ϊ��ʱȡ�ڼ��ж�
        if(pkbookedDate!=null&&pkbookedDate.isSupportedEmpty()){
            pkbookedDate.setSupportedEmpty(false);
        }
		//�ۼƷ�Ʊ���
        txtInvoiceAmt.setPrecision(2);
		txtInvoiceAmt.setSupportedEmpty(true);
		txtAllInvoiceAmt.setSupportedEmpty(true);
		txtInvoiceAmt.setMinimumValue(FDCHelper.MIN_VALUE);
		txtInvoiceAmt.setEnabled(true);
		txtInvoiceAmt.setRequired(true);
		txtAllInvoiceAmt.setEnabled(false);
        txtInvoiceAmt.setMaximumValue(FDCHelper.MAX_VALUE.multiply(FDCHelper.TEN));
        
		txtInvoiceAmt.addDataChangeListener(new DataChangeListener(){
			public void dataChanged(DataChangeEvent eventObj) {
				BigDecimal invoiceAmt = txtInvoiceAmt.getBigDecimalValue();
				txtAllInvoiceAmt.setValue(allInvoiceAmt.add(FDCHelper.toBigDecimal(invoiceAmt)));
			}
		});
		/**
		 * ʹ��Ʊ���Ƿ��¼�������ֵ������������ֵΪfalseʱ����Ʊ��Ϊ�Ǳ�¼�
		 * -- by jiadong 2010-06-29
		 */
//		if(isInvoiceRequired){
//			txtInvoiceNumber.setRequired(isInvoiceRequired);
//		}
			
		this.btnViewBudget.setEnabled(true);
		// �ƻ���Ŀ
		try {
//			initFDCDepConPayPlanF7();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
    protected void prmtcurProject_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
    {
    	if(!this.isIncorporation){
    		return ;
    	}
    	
    	if(e.getNewValue()!=null
    			&& !GlUtils.isEqual(e.getOldValue() ,e.getNewValue())		
    	){
    		CurProjectInfo projectInfo = (CurProjectInfo)e.getNewValue();
    		curProject = projectInfo;
    		editData.setCurProject(projectInfo);
    		
    		//bookedDate_dataChanged(e);
    		getUIContext().put("projectId",BOSUuid.read(curProject.getId().toString()));
    		this.fetchInitData();
    		
			editData.setBookedDate(bookedDate);
			editData.setPeriod(curPeriod);
			
			pkbookedDate.setValue(bookedDate);
			cbPeriod.setValue(curPeriod);
    	}
    }

    protected void bookedDate_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
    {
    	if(editData.getCurProject()==null){
    		return ;
    	}
    	//e.getOldValue();
    	String projectId = this.editData.getCurProject().getId().toString();    	
    	fetchPeriod(e,pkbookedDate,cbPeriod, projectId,  false);
    	
    	prmtcurrency_dataChanged(null);
    }
    
	public void storeFields() {
		if("��Ӧ��".equals(this.comboPayeeType.getSelectedItem().toString())){
    		this.editData.setReceiveUnit((SupplierInfo)prmtreceiveUnit.getData());//�տλ
    		this.editData.setPerson(null);
    		this.cbFeeType.setSelectedItem(null);
		}else{
			this.editData.setPerson((PersonInfo)prmtreceiveUnit.getData());
			this.editData.setReceiveUnit(null);
		}
		this.storeBgEntryTable();
		
		if(FeeTypeEnum.FEE.equals(cbFeeType.getSelectedItem())){
			this.kdtTraEntry.removeRows();
		}else if(FeeTypeEnum.TRA.equals(cbFeeType.getSelectedItem())){
			this.kdtFeeEntry.removeRows();
		}else{
			this.kdtTraEntry.removeRows();
			this.kdtFeeEntry.removeRows();
		}
		super.storeFields();
	}
    protected void beforeStoreFields(ActionEvent e) throws Exception {
    	setAmount();
    	super.beforeStoreFields(e);
    	this.prbi.setPaymentType((PaymentTypeInfo)prmtPayment.getData());//��������
    	this.prbi.setUseDepartment((AdminOrgUnitInfo)prmtuseDepartment.getData());//�ÿ��
    	this.prbi.setNumber(txtPaymentRequestBillNumber.getText());//�������뵥����
    	if(FDCHelper.isEmpty(prbi.getNumber())){
    		this.prbi.setNumber(txtNumber.getText());
    	}
    	this.prbi.setPayDate(pksignDate.getSqlDate());//��������
    	if("��Ӧ��".equals(this.comboPayeeType.getSelectedItem().toString())){
    		if(prmtreceiveUnit.getData()!=null &&prmtreceiveUnit.getData() instanceof PersonInfo){
    			prmtreceiveUnit.setValue(null);
				FDCMsgBox.showWarning(this,"ʵ���տλ��ѡ��Ӧ�̻������ϣ�");
				SysUtil.abort();
			}
    		this.prbi.setSupplier((SupplierInfo)prmtreceiveUnit.getData());//�տλ
    		this.prbi.setPerson(null);
		}else{
			if(prmtreceiveUnit.getData()!=null &&prmtreceiveUnit.getData() instanceof SupplierInfo){
				prmtreceiveUnit.setValue(null);
				FDCMsgBox.showWarning(this,"ʵ���տλ��ѡ����Ա�������ϣ�");
				SysUtil.abort();
			}
			this.prbi.setPerson((PersonInfo)prmtreceiveUnit.getData());
			this.prbi.setSupplier(null);
		}
    	this.prbi.setRealSupplier((SupplierInfo)prmtrealSupplier.getData());//ʵ���տλ
    	this.prbi.setSettlementType((SettlementTypeInfo)prmtsettlementType.getData());//���㷽ʽ    	
    	this.prbi.setRecBank(txtBank.getText());//�տ�����
    	this.prbi.setRecAccount(txtBankAcct.getText());//�տ��ʺ�
    	//�Ƿ�Ӽ�����ĳ�chkbox
    	if(this.chkUrgency.isSelected()){
    		this.prbi.setUrgentDegree(UrgentDegreeEnum.URGENT);
    	}else{
    		this.prbi.setUrgentDegree(UrgentDegreeEnum.NORMAL);
    	}
//    	this.prbi.setUrgentDegree((UrgentDegreeEnum)combUrgentDegree.getSelectedItem());//�����̶�
    	this.prbi.setCurrency((CurrencyInfo)prmtcurrency.getData());//�ұ�
    	this.prbi.setLatestPrice(txtBcAmount.getBigDecimalValue());//�������
    	this.prbi.setExchangeRate(txtexchangeRate.getBigDecimalValue());//����
    	this.prbi.setCapitalAmount(txtcapitalAmount.getText());//��д���
    	this.prbi.setPaymentProportion(txtPaymentProportion.getBigDecimalValue());//�������
    	this.prbi.setCompletePrjAmt(txtcompletePrjAmt.getBigDecimalValue());//���깤���������
    	this.prbi.setAmount(txtBcAmount.getBigDecimalValue());//ԭ�ҽ��
    	this.prbi.setOriginalAmount(txtamount.getBigDecimalValue());//ԭ�ҽ��
//    	this.prbi.setMoneyDesc(txtDescription.getText());//��ע
//    	prbi.setUsage(txtMoneyDesc.getText());//����˵��
    	//�ᵥ�����ı���ͬ�ύ���ٲ鿴�õ���ʱ����д�ġ�����˵���ֶ���Ϣ���鿴����   ����ע���е��� by Cassiel_peng 2009-10-10 
    	this.prbi.setMoneyDesc(txtMoneyDesc.getText());//����˵��
    	this.prbi.setUsage(txtMoneyDesc.getText());//��;  Ҳ������Ҫ����ɶֵ����֮ǰע�͵������д���Ҳ����ѿ���˵����ֵ���������Ǿ������ɣ�
    	
    	if(txtattachment.getIntegerValue()!=null){
    		this.prbi.setAttachment(txtattachment.getIntegerValue().intValue());//����
    	}
    	this.prbi.setCurProject((CurProjectInfo) this.prmtcurProject.getValue());//������Ŀ
    	prbi.setSourceType(SourceTypeEnum.ADDNEW);
    	prbi.setInvoiceNumber(txtInvoiceNumber.getText());//��Ʊ��
    	prbi.setInvoiceDate(pkInvoiceDate.getSqlDate());//��Ʊ����
    	prbi.setInvoiceAmt(txtInvoiceAmt.getBigDecimalValue());//��Ʊ���
    	prbi.setAllInvoiceAmt(txtAllInvoiceAmt.getBigDecimalValue());//�ۼƷ�Ʊ���
    	
    	prbi.setIsBgControl(this.cbIsBgControl.isSelected());
    	prbi.setApplier((PersonInfo) this.prmtApplier.getData());
    	prbi.setApplierCompany((CompanyOrgUnitInfo) this.prmtApplierCompany.getData());
    	prbi.setApplierOrgUnit((AdminOrgUnitInfo) this.prmtApplierOrgUnit.getData());
    	prbi.setCostedCompany((CompanyOrgUnitInfo) this.prmtCostedCompany.getData());
    	prbi.setCostedDept((CostCenterOrgUnitInfo) this.prmtCostedDept.getData());
    	prbi.setPayBillType((PaymentBillTypeInfo) this.prmtPayBillType.getData());
    	prbi.setPayContentType((PayContentTypeInfo) this.prmtPayContentType.getData());
    	prbi.setLocalCurrency(this.baseCurrency);
    	prbi.setLxNum((BankNumInfo) this.prmtLxNum.getData());
    	prbi.getBgEntry().clear();
    	for(int i=0;i<this.kdtBgEntry.getRowCount();i++){
    		IRow row = this.kdtBgEntry.getRow(i);
    		PayRequestBillBgEntryInfo entry=new PayRequestBillBgEntryInfo();
    		entry.setSeq(i);
    		entry.setExpenseType((ExpenseTypeInfo)row.getCell("expenseType").getValue());
    		entry.setAccountView((AccountViewInfo)row.getCell("accountView").getValue());
    		entry.setRequestAmount((BigDecimal)row.getCell("requestAmount").getValue());
    		entry.setAmount((BigDecimal)row.getCell("amount").getValue());
    		entry.setBgItem((BgItemInfo)row.getCell("bgItem").getValue());
    		prbi.getBgEntry().add(entry);
    	}
    	this.editData.put("PayRequestBillInfo",prbi);//��ֵ	
    	
    	// ����:���ı���ͬ��ʱ�������ı���ͬ��ѯ��ʱ�����������ÿ����,��ǰ�ǵ��������ڸ������뵥�ϵ�һ���ÿ�ţ����㲻������   by cassiel_peng 2011-03-15
    	this.editData.setUseDepartment((AdminOrgUnitInfo)prmtuseDepartment.getData());//�ÿ��

    	//ҵ������Ĭ��δ���ã����Ԥ�����ʱ��ҵ�����ڿ��ƣ��ᵼ�´�ӡԤ������ʾ��ҵ�����������ݣ��ֶ�����һ��
    	editData.setBizDate((Date)this.pkbookedDate.getValue());// added by Owen_wen
    }
    public void actionSave_actionPerformed(ActionEvent e) throws Exception {
		// ����ǰ��д�������Ŀ�ܺ�Լ���Ƿ����á��ֶ�
    	updateProgrammingContract(this.editData.getProgrammingContract(),0);
		//��ͬ���Ͳ���Ϊ��
		if(prmtContractType.getData() == null)
    	{
    		MsgBox.showWarning("��ͬ���Ͳ���Ϊ�գ�");
    		SysUtil.abort() ;
    	}
		//�������Ͳ���Ϊ��
		if(prmtPayment.getData() == null)
		{
			MsgBox.showWarning("���������Ϊ�գ�");
    		SysUtil.abort() ;
		}
    	super.actionSave_actionPerformed(e);
		// �����дд�������Ŀ�ܺ�Լ״̬
    	updateProgrammingContract(this.editData.getProgrammingContract(),1);
//    	ContractWithoutTextFactory.getRemoteInstance().getContractWithoutTextInfo()
//    	PayRequestBillFactory.getRemoteInstance().addnew(this.prbi);
		modify=false;
		setBgEditState();
    }
	/**
	 * output actionEdit_actionPerformed
	 */
	public void actionEdit_actionPerformed(ActionEvent e) throws Exception {
	
		
		//�������ݻ��ڵ���޸ĵ�ʱ��ʧ������Ϊû�а�
		String obj = txtattachment.getText();
		super.actionEdit_actionPerformed(e);
		
		
		chkUrgency.setEnabled(true);
		chkCostsplit.setEnabled(true);
		
//		chkNeedPaid.setEnabled(true);
		if(chkNeedPaid.isSelected()){
			txtNoPaidReason.setEnabled(true);
			prmtAccount.setEnabled(true);
		}else{
			txtNoPaidReason.setEnabled(false);	
			prmtAccount.setEnabled(false);
			txtNoPaidReason.setText(null);
			prmtAccount.setValue(null);
		}
		
		if(!this.editData.getState().getValue().equals(FDCBillStateEnum.SAVED_VALUE)){
			this.prmtcurProject.setEnabled(false);
		};
		this.txtattachment.setText(obj);
    	txtPaymentProportion.setEditable(false);
		txtcompletePrjAmt.setEditable(false);
		setAmount();
		this.menuBiz.setVisible(false);

		//new update by renliang at 2010-5-12
		if(this.isNotEnterCAS){
			chkNeedPaid.setEnabled(false);
			chkNeedPaid.setSelected(true);
		}
		setBgEditState();
	}
	public boolean isBillInWorkflow(String id) throws BOSException{
		ProcessInstInfo instInfo = null;
		ProcessInstInfo procInsts[] = null;

		IEnactmentService service2 = EnactmentServiceFactory.createRemoteEnactService();
		procInsts = service2.getProcessInstanceByHoldedObjectId(id);
		int i = 0;
		for(int n = procInsts.length; i < n; i++){
			if("open.running".equals(procInsts[i].getState()) || "open.not_running.suspended".equals(procInsts[i].getState())){
				instInfo = procInsts[i];
			}
		}
		if(instInfo != null){
			return true;
		}else{
			return false;
		}
    }
	public void actionSubmit_actionPerformed(ActionEvent e) throws Exception {
		//��ͬ���Ͳ���Ϊ��
		if(prmtContractType.getData() == null)
    	{
    		MsgBox.showWarning("��ͬ���Ͳ���Ϊ�գ�");
    		SysUtil.abort() ;
    	}
		//�������Ͳ���Ϊ��
		if(prmtPayment.getData() == null)
		{
			MsgBox.showWarning("���������Ϊ�գ�");
    		SysUtil.abort() ;
		}
		this.storeFields();
		this.verifyInputForSubmint();
		
		UserInfo u=SysContext.getSysContext().getCurrentUserInfo();
		CurProjectInfo project=CurProjectFactory.getRemoteInstance().getCurProjectInfo(new ObjectUuidPK(this.editData.getCurProject().getId()));
		if(project.isIsOA()&&u.getPerson()!=null&&!isBillInWorkflow(this.editData.getId().toString())){
			if(this.editData.getSourceFunction()==null){
				this.editData.setOaPosition(null);
				Map map=ContractBillFactory.getRemoteInstance().getOAPosition(u.getNumber());
				if(map.size()>1){
					UIContext uiContext = new UIContext(this);
					uiContext.put("map", map);
					uiContext.put("editData", this.editData);
			        IUIFactory uiFactory = UIFactory.createUIFactory(UIFactoryName.MODEL);
			        IUIWindow uiWindow = uiFactory.create(OaPositionUI.class.getName(), uiContext,null,OprtState.VIEW);
			        uiWindow.show();
			        if(this.editData.getOaPosition()==null){
			        	return;
			        }
				}else if(map.size()==1){
					Iterator<Entry<String, String>> entries = map.entrySet().iterator();
					while(entries.hasNext()){
						Entry<String, String> entry = entries.next();
					    String key = entry.getKey();
					    String value = entry.getValue();
					    this.editData.setOaPosition(key+":"+value);
					}
				}else{
					FDCMsgBox.showWarning(this,"��ȡOA���ʧ�ܣ�");
		    		return;
				}
			}else{
				this.editData.setOaOpinion(null);
				UIContext uiContext = new UIContext(this);
				uiContext.put("editData", this.editData);
		        IUIFactory uiFactory = UIFactory.createUIFactory(UIFactoryName.MODEL);
		        IUIWindow uiWindow = uiFactory.create(OaOpinionUI.class.getName(), uiContext,null,OprtState.VIEW);
		        uiWindow.show();
		        if(this.editData.getOaOpinion()==null){
		        	return;
		        }
			}
		}
		// �ύǰ��д�������Ŀ�ܺ�Լ���Ƿ����á��ֶ�
		updateProgrammingContract(this.editData.getProgrammingContract(),0);
		
		if(this.prmtMpCostAccount.getValue()!=null&&this.prmtMarketProject.getValue()!=null){
			CostAccountInfo info=CostAccountFactory.getRemoteInstance().getCostAccountInfo(new ObjectUuidPK(((CostAccountInfo)this.prmtMpCostAccount.getValue()).getId()));
			MarketProjectInfo market=MarketProjectFactory.getRemoteInstance().getMarketProjectInfo(new ObjectUuidPK(((MarketProjectInfo)this.prmtMarketProject.getValue()).getId()));
			boolean isSet=false;
			ContractWithoutTextCollection col=ContractWithoutTextFactory.getRemoteInstance().getContractWithoutTextCollection("select marketProject.id from where id='"+this.editData.getId()+"'");
			if(col.size()>0&&col.get(0).getMarketProject()!=null&&!col.get(0).getMarketProject().getId().toString().equals(market.getId().toString())){
				isSet=true;
			}
			if(FDCHelper.isEmpty(this.editData.getIsTimeOut())||isSet){
				if(info.getYjType()!=null&&info.getYjType().equals(CostAccountYJTypeEnum.FYJ)&&market.getAuditTime()!=null){
					Calendar cal = new GregorianCalendar();
					cal.setTime(FDCDateHelper.getNextMonth(market.getAuditTime()));
					cal.set(5, 15);
					cal.set(11, 0);
					cal.set(12, 0);
					cal.set(13, 0);
					cal.set(14, 0);
					int day=FDCDateHelper.getDiffDays(cal.getTime(), new Date());
					if(day>1){
						FDCMsgBox.showInfo(this,"��ͬ���̱�������������ͨ����2���ڷ��𣻳�������Ӷ��������ı���������ڴ���15��֮ǰ�������ı���ͬ���̣���ʱ�������̽����ա��ζ�����Ӫ�����ù����ƶȡ��涨���������˽��п۷���");
						this.editData.setIsTimeOut("��");
					}else{
						this.editData.setIsTimeOut("��");
					}
				}else{
					this.editData.setIsTimeOut("��");
				}
			}
		}
		for(int i=0;i<this.tblMarket.getRowCount();i++){
			IRow row=this.tblMarket.getRow(i);
			row.getCell("date").setValue(new Date());
		}
		super.actionSubmit_actionPerformed(e);
		modify=false;
		// �ύ��дд�������Ŀ�ܺ�Լ״̬
		updateProgrammingContract(this.editData.getProgrammingContract(),1);
		//new update by renliang at 2010-5-12
		if(isNotEnterCAS){
			this.chkNeedPaid.setEnabled(false);
			this.chkNeedPaid.setSelected(true);
		}
		setBgEditState();
		
		if (editData.getState() == FDCBillStateEnum.AUDITTING) {
			btnSave.setEnabled(false);
			btnSubmit.setEnabled(false);
			btnEdit.setEnabled(false);
			btnRemove.setEnabled(false);
		}
		this.setOprtState("VIEW");
	}
	/**
	 * ���¹滮��Լ"�Ƿ�����"�ֶ�
	 * 
	 * @param proContId
	 * @param isCiting
	 */
	private void updateProgrammingContract(ProgrammingContractInfo pc, int isCiting) {
		try {
			String oldPCId=null;
			FDCSQLBuilder buildSQL = new FDCSQLBuilder();
			IRowSet iRowSet=null;
			if(this.editData.getId()!=null){
				buildSQL.appendSql("select fprogrammingContract from T_CON_ContractWithoutText where fid='" + this.editData.getId().toString() + "'");
				iRowSet = buildSQL.executeQuery();
				while (iRowSet.next()) {
					oldPCId=iRowSet.getString("fprogrammingContract");
				}
			}
			if(isCiting==0){
				if(oldPCId==null||(pc!=null&&oldPCId.equals(pc.getId().toString()))){
					return;
				}
				buildSQL = new FDCSQLBuilder();
				buildSQL.appendSql("select count(*) count from T_CON_ContractWithoutText where fprogrammingContract='" + oldPCId + "' and fid!='" + this.editData.getId().toString() + "'");
				int count = 0;
				iRowSet = buildSQL.executeQuery();
				if (iRowSet.next()) {
					count = iRowSet.getInt("count");
				}
				if(count>0){
					return;
				}
				buildSQL = new FDCSQLBuilder();
				buildSQL.appendSql("update T_CON_ProgrammingContract set FIsWTCiting = " + isCiting + " where FID = '" + oldPCId + "'");
				buildSQL.executeUpdate();
			}else if(pc!=null){
				buildSQL = new FDCSQLBuilder();
				buildSQL.appendSql("update T_CON_ProgrammingContract set FIsWTCiting = " + isCiting + " where FID = '" + pc.getId().toString() + "'");
				buildSQL.executeUpdate();
			}
		} catch (BOSException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
    protected void chkNeedPaid_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
    	if(chkNeedPaid.isSelected()){
    		txtNoPaidReason.setEnabled(true);
    		prmtAccount.setEnabled(true);
//    		prmtreceiveUnit.setRequired(false);
//    		prmtrealSupplier.setRequired(false);
    		txtBank.setRequired(false);
    		txtBankAcct.setRequired(false); 
    		prmtsettlementType.setRequired(false);
    	}else{
    		txtNoPaidReason.setText(null);
    		txtNoPaidReason.setEnabled(false);
    		prmtAccount.setEnabled(false);
    		prmtAccount.setValue(null);
//    		prmtreceiveUnit.setRequired(true);
//    		prmtrealSupplier.setRequired(true);
//    		txtBank.setRequired(true);
//    		txtBankAcct.setRequired(true);
//    		prmtsettlementType.setRequired(true);
    	}
    	txtBank.repaint();
    	txtBankAcct.repaint();
    }
	/**
	 * 
	 * ���������ر���ؼ����������ʵ�֣�
	 * @return
	 * @author:liupd
	 * ����ʱ�䣺2006-10-13 <p>
	 */
	protected KDTextField getNumberCtrl() {
		return txtNumber;
	}
	
	protected void chkgenPaymentBill_itemStateChanged(ItemEvent e) throws Exception {
//		if(e.getStateChange() == ItemEvent.SELECTED) {
//			chkgenPaymentReque.setSelected(true);
//		}
		
	}
	protected void chkgenPaymentReque_itemStateChanged(ItemEvent e) throws Exception {
//		if(e.getStateChange() == ItemEvent.DESELECTED) {
//			chkgenPaymentBill.setSelected(false);
//		}
	}
	public void setOprtState(String oprtType) {
		super.setOprtState(oprtType);
//		if(STATUS_ADDNEW.equals(oprtType)) {
//			prmtcurProject.setEnabled(true);
//		}
//		else {
			this.prmtcurProject.setAccessAuthority(CtrlCommonConstant.AUTHORITY_COMMON);
			prmtcurProject.setEnabled(false);
//		}
			setProgAndAccountState((ContractTypeInfo) prmtContractType.getValue());
		
		txtPaymentRequestBillNumber.setEditable(false);
		setBgEditState();
	}
	private void setProgAndAccountState(ContractTypeInfo contractType){
		if(STATUS_ADDNEW.equals(this.getOprtState()) ||STATUS_EDIT.equals(this.getOprtState())){
			if(contractType!=null&&contractType.isIsAccountView()){
				this.btnAccountView.setEnabled(true);
				this.btnProgram.setEnabled(false);
			}else{
				if(this.editData!=null&&this.editData.getCurrency()!=null&&this.editData.getCurProject().isIsWholeAgeStage()){
					this.btnProgram.setEnabled(false);
				}else{
					this.btnProgram.setEnabled(true);
				}
				this.btnAccountView.setEnabled(false);
			}
		}else{
			btnProgram.setEnabled(false);
			btnAccountView.setEnabled(false);
		}
		btnAccountView.setVisible(false);
	}
	public void setBgEditState(){
		if(this.cbIsBgControl.isSelected()){
			if (getOprtState().equals(OprtState.VIEW)){
				this.actionAddLine.setEnabled(false);
				this.actionInsertLine.setEnabled(false);
				this.actionRemoveLine.setEnabled(false);
			}else{
				this.actionAddLine.setEnabled(true);
				this.actionInsertLine.setEnabled(true);
				this.actionRemoveLine.setEnabled(true);
				
				this.txtamount.setEnabled(false);
			}
			this.kdtBgEntry.getColumn("expenseType").getStyleAttributes().setLocked(false);
			this.kdtBgEntry.getColumn("accountView").getStyleAttributes().setLocked(false);
			this.kdtBgEntry.getColumn("requestAmount").getStyleAttributes().setLocked(false);
//			this.prmtApplier.setEnabled(true);
			this.prmtApplierOrgUnit.setEnabled(true);
			this.prmtCostedCompany.setEnabled(true);
			this.prmtCostedDept.setEnabled(true);
		}else{
			this.actionAddLine.setEnabled(false);
			this.actionInsertLine.setEnabled(false);
			this.actionRemoveLine.setEnabled(false);
			
			this.kdtBgEntry.getColumn("expenseType").getStyleAttributes().setLocked(true);
			this.kdtBgEntry.getColumn("accountView").getStyleAttributes().setLocked(true);
			this.kdtBgEntry.getColumn("requestAmount").getStyleAttributes().setLocked(true);
//			this.prmtApplier.setEnabled(false);
			this.prmtApplierOrgUnit.setEnabled(false);
			this.prmtCostedCompany.setEnabled(false);
			this.prmtCostedDept.setEnabled(false);
			
			if (!getOprtState().equals(OprtState.VIEW)){
				this.txtamount.setEnabled(true);
			}
		}
		if(this.cbIsBgControl.isSelected()&&this.prmtCostedCompany.getValue()!=null){
			this.prmtCostedDept.setEnabled(true);
		}else{
			this.prmtCostedDept.setEnabled(false);
		}
//		if(this.cbIsInvoice.isSelected()){
//			this.txtInvoiceNumber.setEnabled(true);
//			this.txtInvoiceAmt.setEnabled(true);
//			this.pkInvoiceDate.setEnabled(true);
//		}else{
//			this.txtInvoiceNumber.setEnabled(false);
//			this.txtInvoiceAmt.setEnabled(false);
//			this.pkInvoiceDate.setEnabled(false);
//		}
		if(!isOtherCostedDept){
			this.prmtCostedCompany.setEnabled(false);
		}
		if (getOprtState().equals(OprtState.VIEW)) {
			this.actionInvoiceALine.setEnabled(false);
			this.actionInvoiceILine.setEnabled(false);
			this.actionInvoiceRLine.setEnabled(false);
			this.actionMKFP.setEnabled(true);
		} else {
			this.actionInvoiceALine.setEnabled(true);
			this.actionInvoiceILine.setEnabled(true);
			this.actionInvoiceRLine.setEnabled(true);
			this.actionMKFP.setEnabled(true);
		}
		if(this.curProject!=null){
			if(TaxInfoEnum.SIMPLE.equals(curProject.getTaxInfo())){
				this.cbTaxerQua.setAccessAuthority(CtrlCommonConstant.AUTHORITY_COMMON);
				this.txtTaxerNum.setAccessAuthority(CtrlCommonConstant.AUTHORITY_COMMON);
				this.cbTaxerQua.setEnabled(false);
				this.txtTaxerNum.setEnabled(false);
				
				this.actionInvoiceALine.setEnabled(false);
				this.actionInvoiceILine.setEnabled(false);
				this.actionInvoiceRLine.setEnabled(false);
				this.actionMKFP.setEnabled(false);
			}
		}
	}
			
	/**
     * output actionFirst_actionPerformed
     */
    public void actionFirst_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionFirst_actionPerformed(e);
        //֮ǰ����onLoad �ᵼ�¸���CoreBillEditUI�ж������Ŀ¼�˵�����������鿴��
        //�ʰ�onLoad��������������� ����ִ��
        afterOnload();
        prmtrealSupplier.setEnabled(false);
    }

    /**
     * output actionPre_actionPerformed
     */
    public void actionPre_actionPerformed(ActionEvent e) throws Exception
    {
    	super.actionPre_actionPerformed(e);
        //֮ǰ����onLoad �ᵼ�¸���CoreBillEditUI�ж������Ŀ¼�˵�����������鿴��
        //�ʰ�onLoad��������������� ����ִ��
    	afterOnload();
    	prmtrealSupplier.setEnabled(false);
    }

    /**
     * output actionNext_actionPerformed
     */
    public void actionNext_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionNext_actionPerformed(e);
        //֮ǰ����onLoad �ᵼ�¸���CoreBillEditUI�ж������Ŀ¼�˵�����������鿴��
        //�ʰ�onLoad��������������� ����ִ��
        afterOnload();
        prmtrealSupplier.setEnabled(false);
    }

    /**
     * output actionLast_actionPerformed
     */
    public void actionLast_actionPerformed(ActionEvent e) throws Exception
    {
    	super.actionLast_actionPerformed(e);
        //֮ǰ����onLoad �ᵼ�¸���CoreBillEditUI�ж������Ŀ¼�˵�����������鿴��
        //�ʰ�onLoad��������������� ����ִ��
    	afterOnload();
    	prmtrealSupplier.setEnabled(false);
    }

	protected void prmtreceiveUnit_dataChanged(DataChangeEvent e) throws Exception {
		super.prmtreceiveUnit_dataChanged(e);
		
		if(e.getNewValue() != null) {
			if("��Ӧ��".equals(this.comboPayeeType.getSelectedItem().toString())){
//				prmtrealSupplier.setData(prmtreceiveUnit.getData());
				prmtrealSupplier.setEnabled(true);
			}else{
				prmtrealSupplier.setData(null);
				prmtrealSupplier.setEnabled(false);
			}
		}else{
			prmtrealSupplier.setData(null);
			prmtrealSupplier.setEnabled(false);
		}
		if("��Ӧ��".equals(this.comboPayeeType.getSelectedItem().toString())){
			prmtrealSupplier.setRequired(true);
			prmtrealSupplier.setEnabled(true);
		}else{
			prmtrealSupplier.setRequired(false);
			prmtrealSupplier.setEnabled(false);
		}
	}
    /**
     * output prmtrealSupplier_dataChanged method
     */
    protected void prmtrealSupplier_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
    {

		super.prmtrealSupplier_dataChanged(e);
		Object newValue = e.getNewValue();
		if (newValue instanceof SupplierInfo)
		{
			//�״μ���
			if(isFirstLoad&&!getOprtState().equals(OprtState.ADDNEW)) return;
			//��newValue.equalse(e.getOldValue()) �����,��Ϊ�Ƚϵ��Ƕ�ջ��ֵ
			if((e.getOldValue() instanceof SupplierInfo)&&
				((SupplierInfo)e.getOldValue()).getId().equals(((SupplierInfo)newValue).getId())) {
				return;
			}
//			e.setOldValue(newValue);
			SupplierInfo supplier = (SupplierInfo) newValue;
//			BOSUuid supplierid = supplier.getId();
//			BOSUuid FIid = SysContext.getSysContext().getCurrentFIUnit().getId();
//			SupplierCompanyInfoInfo companyInfo = SupplierFactory.getRemoteInstance().getCompanyInfo(new ObjectUuidPK(supplierid), new ObjectUuidPK(FIid));
//			if (companyInfo != null)
//			{
////				txtBankAcct.setText(companyInfo.getBankAccount());
//				txtBank.setText(companyInfo.getBankName());
//			} else
//			{
////				logger.info(getRes("canntGetFiOrg"));
//			}
			if(supplier!=null){
//				EntityViewInfo view=new EntityViewInfo();
//				FilterInfo filter=new FilterInfo();
//				filter.getFilterItems().add(new FilterItemInfo("supplier.id",supplier.getId().toString()));
//				view.setFilter(filter);
//				SelectorItemCollection sic = super.getSelectors();
//				sic.add(new SelectorItemInfo("supplierBank.*"));
//				view.setSelector(sic);
//				SupplierCompanyInfoCollection col=SupplierCompanyInfoFactory.getRemoteInstance().getSupplierCompanyInfoCollection(view);
//				if(col.size()>0&&col.get(0).getSupplierBank().size()>0){
//					this.txtBank.setText(col.get(0).getSupplierBank().get(0).getBank());
////					this.txtBankAccount.setText(col.get(0).getSupplierBank().get(0).getBankAccount());
//				}
				this.txtTaxerNum.setText(supplier.getTaxRegisterNo());
			}else{
//				this.txtBank.setText(null);
//				this.txtBankAccount.setText(null);
				this.txtTaxerNum.setText(null);
			}

		}
	
    }
	protected void setFieldsNull(AbstractObjectValue newData) {
		// TODO Auto-generated method stub
		super.setFieldsNull(newData);
		ContractWithoutTextInfo info = (ContractWithoutTextInfo)newData;
		info.setCurProject(editData.getCurProject());
		
		info.setProgrammingContract(null);
		this.txtPCName.setText(null);
	}

	
	public SelectorItemCollection getSelectors() {
		SelectorItemCollection selectorItemCollection = super.getSelectors();
		selectorItemCollection.add("curProject.id");
		selectorItemCollection.add("curProject.displayName");
		selectorItemCollection.add("curProject.CU.number");
		selectorItemCollection.add("curProject.fullOrgUnit.name");
		
		
		selectorItemCollection.add("orgUnit.id");
		selectorItemCollection.add("state");
		selectorItemCollection.add("currency.number");
		selectorItemCollection.add("currency.name");
		selectorItemCollection.add("amount");
		selectorItemCollection.add("originalAmount");
		
		selectorItemCollection.add("period.number");
		selectorItemCollection.add("period.periodNumber");
		selectorItemCollection.add("period.periodYear");
		selectorItemCollection.add("period.beginDate");
		
		selectorItemCollection.add("fdcDepConPlan.id");
		selectorItemCollection.add("fdcDepConPlan.head.number");
		selectorItemCollection.add("fdcDepConPlan.head.name");
		selectorItemCollection.add("lastUpdateTime");
		
		selectorItemCollection.add("company.*");
		
		selectorItemCollection.add("bgEntry.*");
		selectorItemCollection.add("bgEntry.accountView.*");
		selectorItemCollection.add("btEntry.expenseType.*");
		selectorItemCollection.add("btEntry.bgItem.*");
		
		selectorItemCollection.add("programmingContract.id");
		selectorItemCollection.add("programmingContract.amount");
		selectorItemCollection.add("programmingContract.name");
		selectorItemCollection.add("programmingContract.number");
		selectorItemCollection.add("programmingContract.balance");
//		selectorItemCollection.add(new SelectorItemInfo("programmingContract.programming.*"));
		selectorItemCollection.add("programmingContract.programming.project.id");
		selectorItemCollection.add("programmingContract.contractType.id");
		selectorItemCollection.add("person.*");
		selectorItemCollection.add("receiveUnit.*");
		
		selectorItemCollection.add("contractType.*");
		
		selectorItemCollection.add("sourceFunction");
		selectorItemCollection.add("oaPosition");
		selectorItemCollection.add("oaOpinion");
		selectorItemCollection.add("isTimeOut");
		return selectorItemCollection;
	}
	   
    
    protected void controlDate_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e,ControlDateChangeListener listener) throws Exception
    {
    	if("bookedDate".equals(listener.getShortCut())){
    		bookedDate_dataChanged(e);
    	}
    }
    
    //ҵ�����ڱ仯�¼�
    ControlDateChangeListener bookedDateChangeListener = new ControlDateChangeListener("bookedDate");
    
    
    protected void attachListeners() {   	
    	pkbookedDate.addDataChangeListener(bookedDateChangeListener);
    	
    	addDataChangeListener(txtamount);
    	//Add by zhiyuan_tang 2010/07/20 
    	//������ʹ���뿪����Ʊ����λ�ҽ������깤���������������ӻ��ʵ�dataChange�¼�
    	addDataChangeListener(txtexchangeRate);
    	
    	addDataChangeListener(txtPaymentProportion);
    	addDataChangeListener(prmtcurProject);
    	addDataChangeListener(prmtcurrency);
    	addDataChangeListener(prmtrealSupplier);
    	addDataChangeListener(prmtreceiveUnit);
    	addDataChangeListener(prmtsettlementType);
    	
//    	addDataChangeListener(this.cbIsBgControl);
		addDataChangeListener(this.prmtApplier);
		addDataChangeListener(this.prmtApplierOrgUnit);
		addDataChangeListener(this.prmtCostedCompany);
		addDataChangeListener(this.prmtCostedDept);
		addDataChangeListener(this.prmtContractType);
		addDataChangeListener(this.comboPayeeType);
    }
    
    protected void detachListeners() {
    	pkbookedDate.removeDataChangeListener(bookedDateChangeListener);
    	
    	removeDataChangeListener(txtamount);
    	removeDataChangeListener(txtPaymentProportion);
    	removeDataChangeListener(prmtcurProject);
    	removeDataChangeListener(prmtcurrency);
    	removeDataChangeListener(prmtrealSupplier);
    	removeDataChangeListener(prmtreceiveUnit);
    	removeDataChangeListener(prmtsettlementType);
    	
//    	removeDataChangeListener(this.cbIsBgControl);
		removeDataChangeListener(this.prmtApplier);
		removeDataChangeListener(this.prmtApplierOrgUnit);
		removeDataChangeListener(this.prmtCostedCompany);
		removeDataChangeListener(this.prmtCostedDept);
		removeDataChangeListener(this.prmtContractType);
		removeDataChangeListener(this.comboPayeeType);
    }
		
	public void loadFields() {
		
		//loadFields ���ע��������,����loadFields�����¼�
		detachListeners();
		
		super.loadFields();
		
		this.loadBgEntryTable();
		TableUtils.getFootRow(this.kdtBgEntry,new String[]{"requestAmount","amount"});
		TableUtils.getFootRow(this.kdtFeeEntry,new String[]{"amount"});
		TableUtils.getFootRow(this.kdtTraEntry,new String[]{"days","airFee","carFee","cityFee","otherFee","persons","liveDays","liveFee","allowance","other","total"});
        
		setSaveActionStatus();
		if(getOprtState()==OprtState.ADDNEW){		
			this.menuBiz.setVisible(false);
			chkNeedPaid.setSelected(false);
			chkUrgency.setSelected(false);
			//TODO		
			if(paymentTypes!=null && paymentTypes.size()>0){
				prmtPayment.setData(paymentTypes.get(0));
			}
			//���������ı���ͬ�������Ǻ�ͬĬ��ֵΪ���ı���ͬ����
//			if(contractType!=null && contractType.size()> 0){
//				for(int i = 0; i < contractType.size(); ++i)
//				{
//					if(contractType.get(i).getId().toString().equalsIgnoreCase("eJ6R8AEcEADgAAAAwKgRlawXT3w="))
//					{
//						prmtContractType.setData(contractType.get(i));
//						break ;
//					}
//				}
//			}
			
			prmtrealSupplier.setEnabled(false);			
		}
		
		txtPaymentProportion.setValue(new BigDecimal(100));
		txtcompletePrjAmt.setValue(txtBcAmount.getBigDecimalValue());
		
		if(editData != null && editData.getCurProject() != null) {			
			//String projId = editData.getCurProject().getId().toString();			
			//FullOrgUnitInfo costOrg = FDCClientUtils.getCostOrgByProj(projId);
			
			txtOrg.setText(this.orgUnitInfo.getDisplayName());
			editData.setOrgUnit(orgUnitInfo);
			editData.setCompany(orgUnitInfo);
			editData.setCU(editData.getCurProject().getCU());
		}
		
		//�ұ�ѡ��
		if (this.editData.getCurrency() != null) {
			if (editData.getCurrency().getId().toString().equals(baseCurrency.getId().toString())) {
				// �Ǳ�λ��ʱ������ѡ����û�
				txtexchangeRate.setValue(FDCConstants.ONE);
				txtexchangeRate.setRequired(false);
				txtexchangeRate.setEditable(false);
				txtexchangeRate.setEnabled(false);
			}
		}
		
		//���踶��ѡ��仯
		try {
			chkNeedPaid_actionPerformed(null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(this.editData.getReceiveUnit()!=null){
			this.prmtreceiveUnit.setValue(this.editData.getReceiveUnit());
			this.comboPayeeType.setSelectedIndex(0);
		}else if(this.editData.getPerson()!=null){
			this.prmtreceiveUnit.setValue(this.editData.getPerson());
			this.comboPayeeType.setSelectedIndex(1);
		}else{
			this.comboPayeeType.setSelectedIndex(0);
		}
		if(editData!=null&&editData.getCU()!=null){
			String cuId = editData.getCU().getId().toString();	
			if("��Ӧ��".equals(this.comboPayeeType.getSelectedItem().toString())){
				FDCClientUtils.initSupplierF7(this, prmtreceiveUnit, cuId);
			}else{
				FDCClientUtils.setPersonF7(prmtreceiveUnit, this,null);
			}
		}
	    attachListeners() ;
	    if(STATUS_EDIT.equals(getOprtState()) && !this.actionAudit.isVisible()
	    		&& !this.actionUnAudit.isVisible()){
	    	this.menuBiz.setVisible(false);
	    }
	    isShowBgAmount=true;
	    
	    this.cbIsBgControl.setEnabled(false);
	    this.chkNeedPaid.setEnabled(false);
	    
	    try {
			fillAttachmnetTable();
		} catch (EASBizException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (BOSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		setProgAndAccountState((ContractTypeInfo) prmtContractType.getValue());
		
		if(this.curProject!=null){
			try {
				this.curProject=CurProjectFactory.getRemoteInstance().getCurProjectInfo(new ObjectUuidPK(this.curProject.getId()));
			} catch (EASBizException e) {
				e.printStackTrace();
			} catch (BOSException e) {
				e.printStackTrace();
			}
			if(TaxInfoEnum.SIMPLE.equals(curProject.getTaxInfo())){
				this.cbTaxerQua.setEnabled(false);
				this.txtTaxerNum.setEnabled(false);
				
				this.actionInvoiceALine.setEnabled(false);
				this.actionInvoiceILine.setEnabled(false);
				this.actionInvoiceRLine.setEnabled(false);
				this.actionMKFP.setEnabled(false);
			}
		}
		String paramValue="true";
		try {
			paramValue = ParamControlFactory.getRemoteInstance().getParamValue(new ObjectUuidPK(SysContext.getSysContext().getCurrentOrgUnit().getId()), "YF_MARKETPROJECTCONTRACT");
		} catch (EASBizException e) {
			e.printStackTrace();
		} catch (BOSException e) {
			e.printStackTrace();
		}
		if(this.editData.getContractType()!=null){
			if(this.editData.getContractType().isIsMarket()&&"true".equals(paramValue)){
				this.prmtMarketProject.setEnabled(true);
				this.prmtMarketProject.setRequired(true);
				this.prmtMpCostAccount.setEnabled(true);
				this.prmtMpCostAccount.setRequired(true);
				this.actionMALine.setEnabled(true);
				this.actionMRLine.setEnabled(true);
			}else{
				this.prmtMarketProject.setEnabled(false);
				this.prmtMarketProject.setRequired(false);
				this.prmtMpCostAccount.setEnabled(false);
				this.prmtMpCostAccount.setEnabled(false);
				this.actionMALine.setEnabled(false);
				this.actionMRLine.setEnabled(false);
			}
		}else{
			this.prmtMarketProject.setEnabled(false);
			this.prmtMarketProject.setRequired(false);
			this.prmtMpCostAccount.setEnabled(false);
			this.prmtMpCostAccount.setRequired(false);
			this.actionMALine.setEnabled(false);
			this.actionMRLine.setEnabled(false);
		}
	}
	
	protected void comboPayeeType_itemStateChanged(ItemEvent e) throws Exception {
		if (e.getStateChange() == 2) {
			return;
		}
		if (this.comboPayeeType.getSelectedItem() == null) {
			return;
		}
		if(editData!=null&&editData.getCU()!=null){
			prmtreceiveUnit.setData(null);
			prmtrealSupplier.setData(null);
			String cuId = editData.getCU().getId().toString();	
			if("��Ӧ��".equals(this.comboPayeeType.getSelectedItem().toString())){
				prmtreceiveUnit.setEntityViewInfo(null);
				FDCClientUtils.initSupplierF7(this, prmtreceiveUnit, cuId);
			}else{
				prmtreceiveUnit.setEntityViewInfo(null);
				FDCClientUtils.setPersonF7(prmtreceiveUnit, this,null);
			}
		}
		if("��Ӧ��".equals(this.comboPayeeType.getSelectedItem().toString())){
			prmtrealSupplier.setRequired(true);
			prmtrealSupplier.setEnabled(true);
		}else{
			prmtrealSupplier.setRequired(false);
			prmtrealSupplier.setEnabled(false);
		}
		
		cbFeeType_itemStateChanged(null);
	}

	//���þ���
	protected void setPrecision(){
		CurrencyInfo currency = editData.getCurrency();	    	
    	Date bookedDate = (Date)editData.getBookedDate();
    	
    	ExchangeRateInfo exchangeRate = null;
		try {
			exchangeRate = FDCClientHelper.getLocalExRateBySrcCurcy(this, currency.getId(),company,bookedDate);
		} catch (Exception e) {			
			e.printStackTrace();
			return ;
		} 
    	
    	int curPrecision = FDCClientHelper.getPrecOfCurrency(currency.getId());
    	int exPrecision = curPrecision;
    	
    	if(exchangeRate!=null){
    		exPrecision = exchangeRate.getPrecision();
    	}
    		
    	txtexchangeRate.setPrecision(exPrecision);
    	BigDecimal exRate =  prbi.getExchangeRate();    
    	if(exRate!=null){
    		txtexchangeRate.setValue(exRate);
    	}
    	txtamount.setPrecision(curPrecision);
      	txtamount.setValue(editData.getOriginalAmount());
	}
	
	protected void verifyInputForSave()throws Exception  {
		super.verifyInputForSave();
		FDCClientVerifyHelper.verifyEmpty(this, txtbillName);
		FDCClientVerifyHelper.verifyEmpty(this, prmtcurProject);
		checkAmountForSave();
		
		if(prmtMpCostAccount.isRequired()){
			FDCClientVerifyHelper.verifyEmpty(this, prmtMpCostAccount);
		}
		if(prmtMarketProject.isRequired()){
			FDCClientVerifyHelper.verifyEmpty(this, prmtMarketProject);
			if(this.tblMarket.getRowCount()==0){
				FDCMsgBox.showWarning(this,"Ӫ����ͬ��̯��ϸ����Ϊ�գ�");
				SysUtil.abort();
			}
			BigDecimal rate=FDCHelper.ZERO;
			for(int i=0;i<this.tblMarket.getRowCount();i++){
				rate=FDCHelper.add(rate, this.tblMarket.getRow(i).getCell("rate").getValue());
				if(this.tblMarket.getRow(i).getCell("date")==null){
					FDCMsgBox.showWarning(this,"Ԥ�Ʒ������²���Ϊ�գ�");
					SysUtil.abort();
				}
				if(this.tblMarket.getRow(i).getCell("rate")==null){
					FDCMsgBox.showWarning(this,"������������Ϊ�գ�");
					SysUtil.abort();
				}
				if(this.tblMarket.getRow(i).getCell("amount")==null){
					FDCMsgBox.showWarning(this,"��������Ϊ�գ�");
					SysUtil.abort();
				}
			}
			if(rate.compareTo(new BigDecimal(100))!=0){
				FDCMsgBox.showWarning(this,"��������֮�Ͳ�����100%��");
				SysUtil.abort();
			}
			CostAccountInfo cinfo=(CostAccountInfo)prmtMpCostAccount.getValue();
			MarketProjectInfo info=MarketProjectFactory.getRemoteInstance().getMarketProjectInfo(new ObjectUuidPK(((MarketProjectInfo)prmtMarketProject.getValue()).getId()));
			Set costSet=new HashSet();
			for(int i=0;i<info.getCostEntry().size();i++){
				costSet.add(info.getCostEntry().get(i).getCostAccount().getId());
			}
			if(!costSet.contains(cinfo.getId())){
				FDCMsgBox.showWarning(this,"���ù����������в����ڣ�");
				SysUtil.abort();
			}
			BigDecimal comAmount=FDCHelper.ZERO;
//			ContractBillCollection col=ContractBillFactory.getRemoteInstance().getContractBillCollection("select amount from where mpCostAccount.id='"+cinfo.getId()+"' and marketProject.id='"+info.getId()+"'");
//			for(int i=0;i<col.size();i++){
//				comAmount=FDCHelper.add(comAmount,col.get(i).getAmount());
//			}
//			ContractWithoutTextCollection wtCol=null;
//			if(editData.getId()!=null){
//				wtCol=ContractWithoutTextFactory.getRemoteInstance().getContractWithoutTextCollection("select amount from where mpCostAccount.id='"+cinfo.getId()+"' and marketProject.id='"+info.getId()+"' and id!='"+this.editData.getId()+"'");
//			}else{
//				wtCol=ContractWithoutTextFactory.getRemoteInstance().getContractWithoutTextCollection("select amount from where mpCostAccount.id='"+cinfo.getId()+"' and marketProject.id='"+info.getId()+"'");
//			}
//			for(int i=0;i<wtCol.size();i++){
//				comAmount=FDCHelper.add(comAmount,wtCol.get(i).getAmount());
//			}
			StringBuilder sql = new StringBuilder();
			sql.append(" select sum(famount) amount from t_con_contractwithouttext ");
			sql.append(" where fmpCostAccountid='"+cinfo.getId()+"' and fmarketProjectid='"+info.getId()+"'");
	    	if(editData.getId()!=null){
	    		sql.append(" and fid!='"+this.editData.getId()+"'");
	    	}
			FDCSQLBuilder _builder = new FDCSQLBuilder();
			_builder.appendSql(sql.toString());
			IRowSet rowSet = _builder.executeQuery();
			while(rowSet.next()){
				comAmount=rowSet.getBigDecimal("amount");
			}
			
			BigDecimal subAmount=FDCHelper.ZERO;
			MarketProjectCollection mpcol=MarketProjectFactory.getRemoteInstance().getMarketProjectCollection("select amount,costEntry.*,costEntry.costAccount.* from where state!='1SAVED' and isSub=1 and mp.id='"+info.getId()+"'");
			for(int i=0;i<mpcol.size();i++){
				for(int j=0;j<mpcol.get(i).getCostEntry().size();j++){
					if(mpcol.get(i).getCostEntry().get(j).getCostAccount().getId().equals(cinfo.getId())){
						subAmount=FDCHelper.add(subAmount,mpcol.get(i).getCostEntry().get(j).getAmount());
					}
				}
			}
			mpcol=MarketProjectFactory.getRemoteInstance().getMarketProjectCollection("select amount,costEntry.*,costEntry.costAccount.* from where isSub=0 and id='"+info.getId().toString()+"'");
			for(int i=0;i<mpcol.size();i++){
				for(int j=0;j<mpcol.get(i).getCostEntry().size();j++){
					if(mpcol.get(i).getCostEntry().get(j).getCostAccount().getId().equals(cinfo.getId())){
						subAmount=FDCHelper.add(subAmount,mpcol.get(i).getCostEntry().get(j).getAmount());
					}
				}
			}
			if(FDCHelper.add(comAmount, this.txtamount.getBigDecimalValue()).compareTo(subAmount)>0){
				FDCMsgBox.showWarning(this,"��ͬ��������ʣ���");
				SysUtil.abort();
			}
		}
		
	}

	protected boolean checkCanSubmit() throws Exception {		
		if(isIncorporation && ((FDCBillInfo)editData).getPeriod()==null){
			MsgBox.showWarning(this,"���óɱ��½��ڼ䲻��Ϊ�գ����ڻ�������ά���ڼ������ѡ��ҵ������");
			SysUtil.abort();
		}
		return super.checkCanSubmit();
	}
	
	private void checkAmountForSave(){
		BigDecimal bd = txtamount.getBigDecimalValue();
		if(FDCHelper.toBigDecimal(bd).compareTo(FDCHelper.ZERO) == 0){
			MsgBox.showWarning(this, "ԭ�ҽ���Ϊ��!");
			txtamount.requestFocus(true);
			SysUtil.abort();
		}
		
	}
	
	private void checkAmountForSubmit() throws BOSException, SQLException{
		// 11.6.24 �����붯̬�ɱ����Ͳ���������� add by emanon
		if (chkCostsplit.isSelected()) {
			if ("�ϸ����".equals(CONTROLNOCONTRACT)
					|| "��ʾ����".equals(CONTROLNOCONTRACT)) {
				if (prmtPlanProject.getValue() == null) {
					MsgBox.showWarning(this, "�ƻ���Ŀ����Ϊ��");
					abort();
				}
			}
			if ("�ϸ����".equals(CONTROLNOCONTRACT)) {
				BigDecimal getBgBalance = getBgBalance();
				BigDecimal bcAmount = txtBcAmount.getBigDecimalValue();
				if (bcAmount.compareTo(getBgBalance) > 0) {
					MsgBox.showWarning(this, "��������ڱ��ڿ���Ԥ�㣬�����ύ");
					abort();
				}
			}
			if ("��ʾ����".equals(CONTROLNOCONTRACT)) {
				BigDecimal getBgBalance = getBgBalance();
				BigDecimal bcAmount = txtBcAmount.getBigDecimalValue();
				if (bcAmount.compareTo(getBgBalance) > 0) {
					int result = MsgBox
							.showConfirm2(this, "��������ڱ��ڿ���Ԥ�㣬�Ƿ��ύ��");
					if (result != MsgBox.OK) {
						SysUtil.abort();
					}
				}
			}
		}
	}
	protected BigDecimal getAccActOnLoadPcAmount(ProgrammingContractInfo pc) throws BOSException, SQLException{
		if(pc==null) return FDCHelper.ZERO;
		FDCSQLBuilder _builder = new FDCSQLBuilder();
		_builder.appendSql(" select sum(t.amount) amount from (select sum(bill.famount) amount from T_CON_ContractBill bill ");
		_builder.appendSql(" where bill.fProgrammingContract='"+pc.getId().toString()+"' and bill.fstate in('2SUBMITTED','3AUDITTING')");
		
		_builder.appendSql(" union all select sum(bill.famount) amount from T_CON_ContractWithoutText bill where 1=1");
		if(this.editData.getId()!=null){
			_builder.appendSql(" and bill.fid!='"+this.editData.getId()+"'");
		}
		_builder.appendSql(" and bill.fProgrammingContract='"+pc.getId().toString()+"' and bill.fstate in('2SUBMITTED','3AUDITTING'))t");
		
		IRowSet rowSet = _builder.executeQuery();
		while(rowSet.next()){
			if(rowSet.getBigDecimal("amount")!=null)
				return rowSet.getBigDecimal("amount");
		}
		return FDCHelper.ZERO;
	}
	private void verifyContractProgrammingPara() throws BOSException, SQLException, EASBizException {
		ProgrammingContractInfo pc = (ProgrammingContractInfo) this.editData.getProgrammingContract();
		if(pc != null && this.editData.getId() !=null){
			SelectorItemCollection sick = new SelectorItemCollection();
			sick.add("*");
			pc = ProgrammingContractFactory.getRemoteInstance().getProgrammingContractInfo(new ObjectUuidPK(pc.getId()), sick);
		}
		String param = null;
		BOSUuid idcu = editData.getCurProject().getId();
		ObjectUuidPK pk = new ObjectUuidPK(editData.getOrgUnit().getId());
		param = ParamControlFactory.getRemoteInstance().getParamValue(pk, "FDC228_ISSTRICTCONTROL");
		if (!com.kingdee.util.StringUtils.isEmpty(param)) {
			int i = Integer.parseInt(param);
			boolean isAbort=false;
			if(pc==null){
				FilterInfo filter = new FilterInfo();
				filter.getFilterItems().add(new FilterItemInfo("programming.project.id", idcu));
				filter.getFilterItems().add(new FilterItemInfo("programming.isLatest", new Integer(1), CompareType.EQUALS));
				filter.getFilterItems().add(new FilterItemInfo("programming.state", FDCBillStateEnum.AUDITTED_VALUE, CompareType.EQUALS));
				if(editData.getContractType()!=null){
					filter.getFilterItems().add(new FilterItemInfo("contractType.id", editData.getContractType().getId().toString(), CompareType.EQUALS));
				}
				isAbort=ProgrammingContractFactory.getRemoteInstance().exists(filter);
			}
			switch (i) {
			case 0:
				// �ϸ����ʱ
				if (pc != null) {
					BigDecimal amount = this.editData.getAmount().setScale(2, BigDecimal.ROUND_HALF_UP);
					BigDecimal balance=(pc.getBalance()== null?FDCHelper.ZERO:pc.getBalance()).setScale(2, BigDecimal.ROUND_HALF_UP);
					BigDecimal onLoadAmount=getAccActOnLoadPcAmount(pc).setScale(2, BigDecimal.ROUND_HALF_UP);
					String detail="�滮��"+balance+"\nռ�ý�"+onLoadAmount;
					
					if (amount.compareTo(FDCHelper.subtract(balance, onLoadAmount)) > 0) {
						FDCMsgBox.showDetailAndOK(this, "��ͬǩԼ���������ĺ�Լ�ģ��滮���-ռ�ý����������ύ��", detail, 2);
						SysUtil.abort();
					}
				} else if(isAbort){
					FDCMsgBox.showWarning(this, "δ������ܺ�Լ���������ύ��");
					SysUtil.abort();
				}
				break;
			case 1:
				// ��ʾ����ʱ
				if (pc != null ) {
					BigDecimal amount = this.editData.getAmount().setScale(2, BigDecimal.ROUND_HALF_UP);
					BigDecimal balance=(pc.getBalance()== null?FDCHelper.ZERO:pc.getBalance()).setScale(2, BigDecimal.ROUND_HALF_UP);
					BigDecimal onLoadAmount=getAccActOnLoadPcAmount(pc).setScale(2, BigDecimal.ROUND_HALF_UP);
					String detail="�滮��"+balance+"\nռ�ý�"+onLoadAmount;
					
					if (amount.compareTo(FDCHelper.subtract(balance, onLoadAmount)) > 0) {
						if(FDCMsgBox.showConfirm3a(this, "��ͬǩԼ���������ĺ�Լ�ģ��滮���-ռ�ý�����ȷ���Ƿ��ύ��",detail)== FDCMsgBox.CANCEL){
							SysUtil.abort();
						}
					}
				} else if(isAbort) {
					FDCMsgBox.showWarning(this, "δ������ܺ�Լ���������ύ��");
					SysUtil.abort();
				}
				break;
			case 2:
				// ������ʱ
				if (pc != null) {
					BigDecimal amount = this.editData.getAmount().setScale(2, BigDecimal.ROUND_HALF_UP);
					BigDecimal balance=(pc.getBalance()== null?FDCHelper.ZERO:pc.getBalance()).setScale(2, BigDecimal.ROUND_HALF_UP);
					BigDecimal onLoadAmount=getAccActOnLoadPcAmount(pc).setScale(2, BigDecimal.ROUND_HALF_UP);
					String detail="�滮��"+balance+"\nռ�ý�"+onLoadAmount;
					
					if (amount.compareTo(FDCHelper.subtract(balance, onLoadAmount)) > 0) {
						if(FDCMsgBox.showConfirm3a(this, "��ͬǩԼ���������ĺ�Լ�ģ��滮���-ռ�ý�����ȷ���Ƿ��ύ��",detail)== FDCMsgBox.CANCEL){
							SysUtil.abort();
						}
					}
				}
				break;
			}
		}
	}
	protected void verifyInputForSubmint()throws Exception {
//		checkAmountForSubmit();
		checkAmount();
		//Ԥ�����
		checkMbgCtrlBalance();

		FDCClientVerifyHelper.verifyEmpty(this, this.prmtContractType);
		FDCClientVerifyHelper.verifyEmpty(this, cbOrgType);
//		ContractTypeInfo ct=(ContractTypeInfo)this.prmtContractType.getValue();
//		if(ct.isIsAccountView()){
//			if(!PaymentSplitFactory.getRemoteInstance().exists("select * from where conWithoutText.id='"+this.editData.getId().toString()+"' and splitState='3ALLSPLIT'")){
//				FDCMsgBox.showWarning(this,"���ȹ����ɱ���Ŀ��������ȫ��֣�");
//				SysUtil.abort();
//			}
//		}else{
			verifyContractProgrammingPara();
//		}
		FDCClientVerifyHelper.verifyEmpty(this, this.prmtPayContentType);
			
		FDCClientVerifyHelper.verifyEmpty(this, this.prmtuseDepartment);
		FDCClientVerifyHelper.verifyEmpty(this, this.txtInvoiceAmt);
		FDCClientVerifyHelper.verifyEmpty(this, this.prmtLxNum);
		FDCClientVerifyHelper.verifyEmpty(this, this.txtBankAcct);
		
		if(this.cbIsBgControl.isSelected()){
			FDCClientVerifyHelper.verifyEmpty(this, this.prmtApplier);
//			FDCClientVerifyHelper.verifyEmpty(this, this.prmtApplierCompany);
//			FDCClientVerifyHelper.verifyEmpty(this, this.prmtApplierOrgUnit);
			FDCClientVerifyHelper.verifyEmpty(this, this.prmtCostedCompany);
			FDCClientVerifyHelper.verifyEmpty(this, this.prmtCostedDept);
			if(this.kdtBgEntry.getRowCount()==0){
				FDCMsgBox.showWarning(this,"�����嵥����Ϊ�գ�");
				SysUtil.abort();
			}
//			if (getUIContext().get("isFromWorkflow") != null &&getUIContext().get("approveIsPass")!=null&& getOprtState().equals(OprtState.EDIT)) {
//				this.editData.put("isFromWorkflow",getUIContext().get("isFromWorkflow").toString());
//				this.editData.put("approveIsPass",getUIContext().get("approveIsPass").toString());
//			}
			IBgControlFacade iBgControlFacade = BgControlFacadeFactory.getRemoteInstance();
			BgCtrlResultCollection coll = iBgControlFacade.getBudget("com.kingdee.eas.fi.cas.app.PaymentBill", new BgCtrlParamCollection(), createTempPaymentBill());
			Map bgItemMap=new HashMap();
			boolean isWarning=true;
			for (int i = 0; i < this.kdtBgEntry.getRowCount(); i++) {
				IRow row = this.kdtBgEntry.getRow(i);
					
				if (row.getCell("expenseType").getValue() == null) {
					FDCMsgBox.showWarning(this,"�����嵥Ԥ����Ŀ����Ϊ�գ�");
					this.kdtBgEntry.getEditManager().editCellAt(row.getRowIndex(), this.kdtBgEntry.getColumnIndex("expenseType"));
					SysUtil.abort();
				}
//				if (row.getCell("bgItem").getValue() == null) {
//					FDCMsgBox.showWarning(this,"�����嵥Ԥ����Ŀ����Ϊ�գ�");
//					this.kdtBgEntry.getEditManager().editCellAt(row.getRowIndex(), this.kdtBgEntry.getColumnIndex("bgItem"));
//					SysUtil.abort();
//				}
				if (row.getCell("requestAmount").getValue() == null) {
					FDCMsgBox.showWarning(this,"�����嵥�������Ϊ�գ�");
					this.kdtBgEntry.getEditManager().editCellAt(row.getRowIndex(), this.kdtBgEntry.getColumnIndex("requestAmount"));
					SysUtil.abort();
				}
				if (((BigDecimal)row.getCell("requestAmount").getValue()).compareTo(FDCHelper.ZERO)<=0) {
					FDCMsgBox.showWarning(this,"�����嵥������������0��");
					this.kdtBgEntry.getEditManager().editCellAt(row.getRowIndex(), this.kdtBgEntry.getColumnIndex("requestAmount"));
					SysUtil.abort();
				}
				BgItemInfo bgItem=(BgItemInfo) row.getCell("bgItem").getValue();
				for (int j = 0; j < coll.size(); j++) {
					if (bgItem.getNumber().equals(coll.get(j).getItemCombinNumber())) {
						if(BgCtrlTypeEnum.NoCtrl.equals(coll.get(j).getCtrlType())){
							break;
						}
						if (getUIContext().get("isFromWorkflow") != null && getUIContext().get("isFromWorkflow").toString().equals("true") &&getUIContext().get("approveIsPass")!=null&&getUIContext().get("approveIsPass").toString().equals("false")&& getOprtState().equals(OprtState.EDIT)) {
							break;
						}
						BigDecimal balanceAmount=FDCHelper.ZERO;
						BigDecimal requestAmount=(BigDecimal)row.getCell("requestAmount").getValue();
						if(coll.get(j).getBalance() != null){
							balanceAmount=coll.get(j).getBalance();
						}
						if(bgItemMap.containsKey(bgItem.getNumber())){
							BigDecimal sumAmount=(BigDecimal) bgItemMap.get(bgItem.getNumber());
							balanceAmount=balanceAmount.subtract(sumAmount);
							bgItemMap.put(bgItem.getNumber(), sumAmount.add(requestAmount));
						}else{
							bgItemMap.put(bgItem.getNumber(), requestAmount);
						}
						BigDecimal balance=balanceAmount.subtract(getAccActOnLoadBgAmount(bgItem.getNumber().toString(),true));
						if(requestAmount.compareTo(balance)>0){
							FDCMsgBox.showWarning(this, bgItem.getName()+"����Ԥ����");
							SysUtil.abort();
						}
						if (isWarning&&(getUIContext().get("isFromWorkflow") == null || getUIContext().get("isFromWorkflow").toString().equals("false"))) {
							BigDecimal endBalance=balanceAmount.subtract(getAccActOnLoadBgAmount(bgItem.getNumber(),false));
							if(requestAmount.compareTo(endBalance)>0){
								if(FDCMsgBox.showConfirm2(this, "�㷢��ĵ��������루��ȷ��+��;�����ۼƽ���ѳ���Ԥ�㣻\n���������п��ܲ���ͨ������ȷ���Ƿ��ύ��")!= FDCMsgBox.YES){
									SysUtil.abort();
								}else{
									isWarning=false;
								}
							}
						}
					}
				}
			}
		}
		if(isFeeTraEntry){
			FDCClientVerifyHelper.verifyEmpty(this, this.cbFeeType);
			if(FeeTypeEnum.FEE.equals(this.cbFeeType.getSelectedItem())){
				if(this.kdtFeeEntry.getRowCount()==0){
					FDCMsgBox.showWarning(this,"������ϸ����Ϊ�գ�");
					SysUtil.abort();
				}
				BigDecimal total =TableUtils.getColumnValueSum(this.kdtFeeEntry, "amount");
				if(this.txtamount.getBigDecimalValue().compareTo(total)!=0){
					FDCMsgBox.showWarning(this,"������ϸ�ĺϼƽ�����ԭ�ҽ�");
					SysUtil.abort();
				}
			}else if(FeeTypeEnum.TRA.equals(this.cbFeeType.getSelectedItem())){
				if(this.kdtTraEntry.getRowCount()==0){
					FDCMsgBox.showWarning(this,"������ϸ����Ϊ�գ�");
					SysUtil.abort();
				}
				BigDecimal total =TableUtils.getColumnValueSum(this.kdtTraEntry, "total");
				if(this.txtamount.getBigDecimalValue().compareTo(total)!=0){
					FDCMsgBox.showWarning(this,"������ϸ�ĺϼƽ�����ԭ�ҽ�");
					SysUtil.abort();
				}
			}
		}
		if(this.editData.getCurProject()!=null){
			ContractTypeInfo ct=(ContractTypeInfo) this.prmtContractType.getValue();
			String[] contractType=null;
        	HashMap hmParamIn = new HashMap();
        	hmParamIn.put("PAYPLAN_NOTEXT", null);
			HashMap hmAllParam = ParamControlFactory.getRemoteInstance().getParamHashMap(hmParamIn);
			if(hmAllParam.get("PAYPLAN_NOTEXT")!=null&&!"".equals(hmAllParam.get("PAYPLAN_NOTEXT"))){
				contractType=hmAllParam.get("PAYPLAN_NOTEXT").toString().split(";");
			}
			boolean isCheck=false;
			if(contractType!=null&&contractType.length>0){
				for(int i=0;i<contractType.length;i++){
        			if(ct.getLongNumber().indexOf(contractType[i])>=0){
        				isCheck=true;
        			}
        		}
			}
			if(isCheck){
				Map param=new HashMap();
				param.put("isContract", Boolean.FALSE);
				param.put("contractId", this.editData.getCurProject().getId().toString());
				if (editData.getId() != null) {
					param.put("billId", editData.getId().toString());
				}
				if(editData.getCurProject().getFullOrgUnit()!=null){
					param.put("orgId", editData.getCurProject().getFullOrgUnit().getId().toString());
				}
				if(editData.getCurProject()!=null){
					param.put("curProjectId", editData.getCurProject().getId().toString());
				}
				param.put("bizDate", this.pkbookedDate.getValue());
				param.put("amount", this.txtamount.getBigDecimalValue());
				Map result=ProjectMonthPlanGatherFactory.getRemoteInstance().checkPass(param);
				if(result!=null&&result.get("isPass")!=null){
					if(!((Boolean)result.get("isPass")).booleanValue()&&result.get("warning")!=null){
						FDCMsgBox.showWarning(this,result.get("warning").toString());
						SysUtil.abort();
					}
				}
			}
		}
//		if(!TaxInfoEnum.SIMPLE.equals(this.curProject.getTaxInfo())){
//			FDCClientVerifyHelper.verifyEmpty(this, this.cbTaxerQua);
//			FDCClientVerifyHelper.verifyEmpty(this, this.txtTaxerNum);
//			if(this.kdtInvoiceEntry.getRowCount()==0){
//				FDCMsgBox.showWarning(this,"��Ʊ��¼����Ϊ�գ�");
//				SysUtil.abort();
//			}
//			for (int i = 0; i < this.kdtInvoiceEntry.getRowCount(); i++) {
//				IRow row = this.kdtInvoiceEntry.getRow(i);
//					
//				if (row.getCell("invoiceType").getValue() == null) {
//					FDCMsgBox.showWarning(this,"��Ʊ���Ͳ���Ϊ�գ�");
//					this.kdtBgEntry.getEditManager().editCellAt(row.getRowIndex(), this.kdtBgEntry.getColumnIndex("invoiceType"));
//					SysUtil.abort();
//				}
//				WTInvoiceTypeEnum type=(WTInvoiceTypeEnum) row.getCell("invoiceType").getValue();
//				if(type.equals(WTInvoiceTypeEnum.NOMAL)||type.equals(WTInvoiceTypeEnum.RECEIPT)){
//					if (row.getCell("totalAmount").getValue() == null) {
//						FDCMsgBox.showWarning(this,"��˰����Ϊ�գ�");
//						this.kdtBgEntry.getEditManager().editCellAt(row.getRowIndex(), this.kdtBgEntry.getColumnIndex("totalAmount"));
//						SysUtil.abort();
//					}
//				}else{
//					if (row.getCell("invoiceNumber").getValue() == null||"".equals(row.getCell("invoiceNumber").getValue().toString().trim())) {
//						FDCMsgBox.showWarning(this,"��Ʊ���벻��Ϊ�գ�");
//						this.kdtBgEntry.getEditManager().editCellAt(row.getRowIndex(), this.kdtBgEntry.getColumnIndex("invoiceNumber"));
//						SysUtil.abort();
//					}
//					if (row.getCell("bizDate").getValue() == null) {
//						FDCMsgBox.showWarning(this,"��Ʊ���ڲ���Ϊ�գ�");
//						this.kdtBgEntry.getEditManager().editCellAt(row.getRowIndex(), this.kdtBgEntry.getColumnIndex("bizDate"));
//						SysUtil.abort();
//					}
//					if (row.getCell("totalAmount").getValue() == null) {
//						FDCMsgBox.showWarning(this,"��˰����Ϊ�գ�");
//						this.kdtBgEntry.getEditManager().editCellAt(row.getRowIndex(), this.kdtBgEntry.getColumnIndex("totalAmount"));
//						SysUtil.abort();
//					}
//					if (row.getCell("rate").getValue() == null) {
//						FDCMsgBox.showWarning(this,"˰�ʲ���Ϊ�գ�");
//						this.kdtBgEntry.getEditManager().editCellAt(row.getRowIndex(), this.kdtBgEntry.getColumnIndex("rate"));
//						SysUtil.abort();
//					}
//					if (row.getCell("amount").getValue() == null) {
//						FDCMsgBox.showWarning(this,"˰���Ϊ�գ�");
//						this.kdtBgEntry.getEditManager().editCellAt(row.getRowIndex(), this.kdtBgEntry.getColumnIndex("amount"));
//						SysUtil.abort();
//					}
//				}
//			}
//		}
		
		if(prmtMpCostAccount.isRequired()){
			FDCClientVerifyHelper.verifyEmpty(this, prmtMpCostAccount);
		}
		if(prmtMarketProject.isRequired()){
			FDCClientVerifyHelper.verifyEmpty(this, prmtMarketProject);
			if(this.tblMarket.getRowCount()==0){
				FDCMsgBox.showWarning(this,"Ӫ����ͬ��̯��ϸ����Ϊ�գ�");
				SysUtil.abort();
			}
			BigDecimal rate=FDCHelper.ZERO;
			for(int i=0;i<this.tblMarket.getRowCount();i++){
				rate=FDCHelper.add(rate, this.tblMarket.getRow(i).getCell("rate").getValue());
				if(this.tblMarket.getRow(i).getCell("date")==null){
					FDCMsgBox.showWarning(this,"Ԥ�Ʒ������²���Ϊ�գ�");
					SysUtil.abort();
				}
				if(this.tblMarket.getRow(i).getCell("rate")==null){
					FDCMsgBox.showWarning(this,"������������Ϊ�գ�");
					SysUtil.abort();
				}
				if(this.tblMarket.getRow(i).getCell("amount")==null){
					FDCMsgBox.showWarning(this,"��������Ϊ�գ�");
					SysUtil.abort();
				}
			}
			if(rate.compareTo(new BigDecimal(100))!=0){
				FDCMsgBox.showWarning(this,"��������֮�Ͳ�����100%��");
				SysUtil.abort();
			}
			CostAccountInfo cinfo=(CostAccountInfo)prmtMpCostAccount.getValue();
			MarketProjectInfo info=MarketProjectFactory.getRemoteInstance().getMarketProjectInfo(new ObjectUuidPK(((MarketProjectInfo)prmtMarketProject.getValue()).getId()));
			Set costSet=new HashSet();
			for(int i=0;i<info.getCostEntry().size();i++){
				costSet.add(info.getCostEntry().get(i).getCostAccount().getId());
			}
			if(!costSet.contains(cinfo.getId())){
				FDCMsgBox.showWarning(this,"���ù����������в����ڣ�");
				SysUtil.abort();
			}
			BigDecimal comAmount=FDCHelper.ZERO;
			
			StringBuilder sql = new StringBuilder();
			sql.append(" select sum(famount) amount from t_con_contractwithouttext ");
			sql.append(" where fmpCostAccountid='"+cinfo.getId()+"' and fmarketProjectid='"+info.getId()+"'");
	    	if(editData.getId()!=null){
	    		sql.append(" and fid!='"+this.editData.getId()+"'");
	    	}
			FDCSQLBuilder _builder = new FDCSQLBuilder();
			_builder.appendSql(sql.toString());
			IRowSet rowSet = _builder.executeQuery();
			while(rowSet.next()){
				comAmount=rowSet.getBigDecimal("amount");
			}
		
//			ContractWithoutTextCollection wtCol=null;
//			if(editData.getId()!=null){
//				wtCol=ContractWithoutTextFactory.getRemoteInstance().getContractWithoutTextCollection("select amount from where mpCostAccount.id='"+cinfo.getId()+"' and marketProject.id='"+info.getId()+"' and id!='"+this.editData.getId()+"'");
//			}else{
//				wtCol=ContractWithoutTextFactory.getRemoteInstance().getContractWithoutTextCollection("select amount from where mpCostAccount.id='"+cinfo.getId()+"' and marketProject.id='"+info.getId()+"'");
//			}
//			for(int i=0;i<wtCol.size();i++){
//				comAmount=FDCHelper.add(comAmount,wtCol.get(i).getAmount());
//			}
			
			BigDecimal subAmount=FDCHelper.ZERO;
			MarketProjectCollection mpcol=MarketProjectFactory.getRemoteInstance().getMarketProjectCollection("select amount,costEntry.*,costEntry.costAccount.* from where state!='1SAVED' and isSub=1 and mp.id='"+info.getId()+"'");
			for(int i=0;i<mpcol.size();i++){
				for(int j=0;j<mpcol.get(i).getCostEntry().size();j++){
					if(mpcol.get(i).getCostEntry().get(j).getCostAccount().getId().equals(cinfo.getId())){
						subAmount=FDCHelper.add(subAmount,mpcol.get(i).getCostEntry().get(j).getAmount());
					}
				}
			}
			mpcol=MarketProjectFactory.getRemoteInstance().getMarketProjectCollection("select amount,costEntry.*,costEntry.costAccount.* from where isSub=0 and id='"+info.getId().toString()+"'");
			for(int i=0;i<mpcol.size();i++){
				for(int j=0;j<mpcol.get(i).getCostEntry().size();j++){
					if(mpcol.get(i).getCostEntry().get(j).getCostAccount().getId().equals(cinfo.getId())){
						subAmount=FDCHelper.add(subAmount,mpcol.get(i).getCostEntry().get(j).getAmount());
					}
				}
			}
			if(FDCHelper.add(comAmount, this.txtamount.getBigDecimalValue()).compareTo(subAmount)>0){
				FDCMsgBox.showWarning(this,"��ͬ��������ʣ���");
				SysUtil.abort();
			}
		}
		super.verifyInputForSubmint();
	}
	private void checkAmount() throws Exception {
    	String projectId = editData.getCurProject().getId().toString();
    	
    	if(editData.isIsCostSplit() && ( FDCContractParamUI.RD_MSG.equalsIgnoreCase(controlType)
    			|| FDCContractParamUI.RD_CONTROL.equalsIgnoreCase(controlType))){
    		BigDecimal amiCost  = null;
    		//��ȡ�ɱ����ƽ��
    		String msg = null;
    		if(!FDCContractParamUI.RD_DYMIC.equalsIgnoreCase(controlCost)){
    			amiCost  = FDCCostRptFacadeFactory.getRemoteInstance().getAimCost(projectId);
    			msg = "Ŀ��ɱ�";
    		}else{
    			amiCost  = FDCCostRptFacadeFactory.getRemoteInstance().getDynCost(projectId);
    			msg = "��̬�ɱ�";
    		}
    		//��ȡ��Ŀ��ͬǩԼ�ܽ����״̬������
    		String id = editData.getId()!=null? editData.getId().toString():null;
    		BigDecimal signAmount  = ContractFacadeFactory.getRemoteInstance().getProjectAmount(projectId,id); 
    		
    		if(amiCost.compareTo(signAmount.add(editData.getAmount()))<0){
    			//��ʾ  �ϸ����
    			FDCMsgBox.showWarning(this,"��Ŀ��ͬǩԼ�ܽ���Ѿ�������"+msg);
	    		if(!FDCContractParamUI.RD_MSG.equalsIgnoreCase(controlType)){
	    			SysUtil.abort();
	    		}
    		}
    		
    	}
	}
	   // �ύʱ������Ԥ�����
    private void checkMbgCtrlBalance() throws BOSException, EASBizException{
		//���ݲ����Ƿ���ʾ��ͬ������Ŀ
        if (!isShowCharge || editData.getConChargeType() == null) {
			return;
		}

		StringBuffer buffer = new StringBuffer("");
		IBgControlFacade iCtrl = BgControlFacadeFactory.getRemoteInstance();
		editData.setBizDate((Date) this.pkbookedDate.getValue());
		BgCtrlResultCollection bgCtrlResultCollection = iCtrl.getBudget(
				FDCConstants.ContractWithoutText, null, editData);

		if (bgCtrlResultCollection != null) {
			for (int j = 0, count = bgCtrlResultCollection.size(); j < count; j++) {
				BgCtrlResultInfo bgCtrlResultInfo = bgCtrlResultCollection
						.get(j);

				BigDecimal balance = bgCtrlResultInfo.getBalance();
				if (balance != null
						&& balance.compareTo(bgCtrlResultInfo.getReqAmount()) < 0) {
					buffer.append(
							bgCtrlResultInfo.getItemName() + "("
									+ bgCtrlResultInfo.getOrgUnitName() + ")")
							.append(
									EASResource.getString(
											FDCConstants.VoucherResource,
											"BalanceNotEnagh")
											+ "\r\n");
				}
			}
		}

		if (buffer.length() > 0) {
			int result = MsgBox.showConfirm2(this, buffer.toString()
					+ "\r\n"
					+ EASResource.getString(FDCConstants.VoucherResource,
							"isGoOn"));
			if (result == MsgBox.CANCEL) {
				SysUtil.abort();
			}
		}
    }
	protected void verifyInput(ActionEvent e) throws Exception {
//		if(!chkCostsplit.isSelected()){
//			MsgBox.showInfo(this,ContractClientUtils.getRes("NotEntryCost"));
//		}
		
		// ������ò���һ�廯,����¼���ڼ�
		if(this.isIncorporation && cbPeriod.getValue()==null){
			MsgBox.showConfirm2(this,"���óɱ��½�,����¼���ڼ�");
			SysUtil.abort();
		}		

		checkAmountForSave();
		
		super.verifyInput(e);
		
		checkProjStatus();
	}
	
	private void checkProjStatus() {
//		�����Ŀ״̬�ѹرգ�����ѡ���Ƿ�ɱ���� 
		if(editData != null && editData.getCurProject() != null) {
			BOSUuid id = editData.getCurProject().getId();
			
			SelectorItemCollection selectors = new SelectorItemCollection();
			selectors.add("projectStatus");
			CurProjectInfo curProjectInfo = null;
			try {
				curProjectInfo = CurProjectFactory.getRemoteInstance().getCurProjectInfo(new ObjectUuidPK(id), selectors);
				
			
			}catch (Exception ex) {
				handUIException(ex);
			}
			
			if(curProjectInfo.getProjectStatus() != null) {
				String closedState = ProjectStatusInfo.closeID;
				String transferState = ProjectStatusInfo.transferID;
				String projStateId = curProjectInfo.getProjectStatus().getId().toString();
				if(projStateId != null && (projStateId.equals(closedState)||projStateId.equals(transferState))) {
					if(chkCostsplit.isSelected()) {
						MsgBox.showWarning(this, "�ú�ͬ���ڵĹ�����Ŀ�Ѿ����ڹرջ���ת״̬�����ܽ��붯̬�ɱ�����ȡ��\"���붯̬�ɱ�\"��ѡ���ٱ���/�ύ");
						SysUtil.abort();
					}
				}
			}
		}
	}
	
	protected void checkRef(String id) {
		ContractWithoutTextClientUtils.checkRef(this, id);
	}
	/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
    /**
     * output txtamount_dataChanged method
     */
    protected void txtamount_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
    {
    	super.txtamount_dataChanged(e);
		setAmount();
		setPropPrjAmount("amount",e);
		txtcompletePrjAmt.setValue(txtBcAmount.getBigDecimalValue());
		
		for(int i=0;i<this.tblMarket.getRowCount();i++){
			 IRow r = this.tblMarket.getRow(i);
			 r.getCell("amount").setValue(FDCHelper.divide(FDCHelper.multiply(this.txtamount.getBigDecimalValue(), r.getCell("rate").getValue()), new BigDecimal(100), 2, BigDecimal.ROUND_HALF_UP));
		}
    }

    /**
     * output txtamount_focusLost method
     */
    protected void txtamount_focusLost(java.awt.event.FocusEvent e) throws Exception
    {
    }

    /**
     * output txtNumber_focusLost method
     */
    protected void txtNumber_focusLost(java.awt.event.FocusEvent e) throws Exception
    {
    	txtPaymentRequestBillNumber.setText(txtNumber.getText());
    }
    
    /**
     * output prmtcurrency_dataChanged method
     */
    protected void prmtcurrency_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
    {
		super.prmtcurrency_dataChanged(e);
		//Object newValue = e.getNewValue();
		if (e==null || e.getNewValue() instanceof CurrencyInfo)
		{
			if(isFirstLoad || (STATUS_VIEW.equals(getOprtState()) || STATUS_FINDVIEW.equals(getOprtState()))) return;
			CurrencyInfo currency = (CurrencyInfo)prmtcurrency.getValue();	 
			if(currency==null){
				//���û��ʵ�ֵ����¼����������ʱ���ܵ����
				return;
			}
			BOSUuid srcid = currency.getId();
			Date bookedDate = (Date)pkbookedDate.getValue();
			
	    	ExchangeRateInfo exchangeRate = FDCClientHelper.getLocalExRateBySrcCurcy(this,srcid,company,bookedDate);
	    	
	    	int curPrecision = FDCClientHelper.getPrecOfCurrency(srcid);
	    	BigDecimal exRate = FDCHelper.ONE;
	    	int exPrecision = curPrecision;
	    	
	    	if(exchangeRate!=null){
	    		exRate = exchangeRate.getConvertRate();
	    		exPrecision = exchangeRate.getPrecision();
	    	}
	    	txtexchangeRate.setValue(exRate);
	    	txtexchangeRate.setPrecision(exPrecision);
	    	txtamount.setPrecision(curPrecision);
//			txtexchangeRate.setValue(FDCClientHelper.getLocalExRateBySrcCurcy(this, srcid,company,bookedDate));
	    	
	    	
			setAmount();
			
			if (srcid.toString().equals(baseCurrency.getId().toString())) {
				// �Ǳ�λ��ʱ������ѡ����û�
				txtexchangeRate.setValue(FDCConstants.ONE);
				txtexchangeRate.setRequired(false);
				txtexchangeRate.setEditable(false);
				txtexchangeRate.setEnabled(false);
			}else{
				txtexchangeRate.setRequired(true);
				txtexchangeRate.setEditable(true);
				txtexchangeRate.setEnabled(true);
			}

		}
		getBgAmount();
    }

    /**
     * output txtpaymentProportion_dataChanged method
     */
    protected void txtpaymentProportion_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
    {
    	super.txtpaymentProportion_dataChanged(e);
    	setPropPrjAmount("paymentProp",e);
    }

    /**
     * output txtcompletePrjAmt_dataChanged method
     */
    protected void txtcompletePrjAmt_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
    {
    	super.txtcompletePrjAmt_dataChanged(e);
    	setPropPrjAmount("completePrjAmt",e);
    }

    /**
     * output txtexchangeRate_focusLost method
     */
    protected void txtexchangeRate_focusLost(java.awt.event.FocusEvent e) throws Exception
    {
    	/*Delete by zhiyuan_tang 2010/07/20
    	 * Ϊ������ʹ���뿪����Ʊ����λ�ҽ������깤���������������ӻ��ʵ�dataChange�¼���
    	 * ԭ��focusLost�¼��Ĵ���Ҳһ���Ƶ�dataChange�¼���
    	*/
//    	super.txtexchangeRate_focusLost(e);
//		setAmount();
    }
    
    //Add by zhiyuan_tang 2010/07/20 
	/**
	 * output txtexchangeRate_focusLost method
	 * @author zhiyuan_tang 2010/07/20 <p>
	 * ������ʹ���뿪����Ʊ����λ�ҽ������깤���������������ӻ��ʵ�dataChange�¼�<p>
	 */
    protected void txtexchangeRate_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
    {
    	super.txtexchangeRate_dataChanged(e);
		setAmount();
		setPropPrjAmount("amount",e);
		txtcompletePrjAmt.setValue(txtBcAmount.getBigDecimalValue());
    }
    
	/**
	 * Description: ���ñ�λ�ҽ��,��Сд��
	 * 
	 * @author sxhong Date 2006-9-6
	 */
	private void setAmount()
	{
		
		BigDecimal amount = (BigDecimal) txtamount.getNumberValue();
		Object exchangeRate = txtexchangeRate.getNumberValue();
		if(amount==null){
			amount = FDCConstants.ZERO;
		}
		if (amount != null)
		{
			Object value = prmtcurrency.getValue();
			boolean isBaseCurrency = false;
			
			if (value instanceof CurrencyInfo)
			{				
				//��λ�Ҵ���
				CompanyOrgUnitInfo currentFIUnit = SysContext.getSysContext().getCurrentFIUnit();
				CurrencyInfo baseCurrency = currentFIUnit.getBaseCurrency();
				BOSUuid srcid = ((CurrencyInfo) value).getId();

				if(baseCurrency!=null){
					if(srcid.equals(baseCurrency.getId())){
						isBaseCurrency = true;
						/*if (exchangeRate instanceof BigDecimal&&((BigDecimal)exchangeRate).intValue()!=1)
						{
							MsgBox.showWarning(this,"��ѡ����Ǳ�λ�ң����ǻ��ʲ�����1");
						}*/
						txtBcAmount.setValue(amount);
						txtexchangeRate.setValue(new BigDecimal("1"));
						txtexchangeRate.setEditable(false);
						//return;
					}else{
						this.txtexchangeRate.setEditable(true);
						txtexchangeRate.setEnabled(true);
					}
				}

			}
			
			BigDecimal localamount = FDCConstants.ZERO;
			if (!isBaseCurrency && exchangeRate instanceof BigDecimal)
			{
				localamount = amount.multiply((BigDecimal)exchangeRate);				
				txtBcAmount.setValue(localamount);
			}else{
				localamount = amount;
			}
			//��Ʊ���
//			txtInvoiceAmt.setValue(txtBcAmount.getBigDecimalValue());
//			txtAllInvoiceAmt.setValue(txtBcAmount.getBigDecimalValue());
			//�������깤������
			txtcompletePrjAmt.setValue(txtBcAmount.getBigDecimalValue());
			//��д���Ϊ��λ�ҽ��
			if(localamount.compareTo(FDCConstants.ZERO)!=0){
				localamount = localamount.setScale(2,BigDecimal.ROUND_HALF_UP);
				String cap = FDCClientHelper.getChineseFormat(localamount,false);
				//FDCHelper.transCap((CurrencyInfo) value, amount);
				txtcapitalAmount.setText(cap);
			}else{
				txtcapitalAmount.setText(null);
			}


		}
	}	
    /**
     * ���ø�����������깤��������ԭ�ҽ��֮��Ĺ�ϵ��
     * 				���깤��������ԭ�ҽ��¸������
     * @author sxhong  		Date 2007-3-12
     */
    private void setPropPrjAmount(String cause,DataChangeEvent e){
    	if(isFirstLoad||(!txtPaymentProportion.isRequired())) return;
//    	if(PayReqUtils.isContractWithoutText(editData.getcon))
    	BigDecimal amount=txtBcAmount.getBigDecimalValue();
    	BigDecimal paymentProp=txtPaymentProportion.getBigDecimalValue();
    	BigDecimal completePrj=txtcompletePrjAmt.getBigDecimalValue();
    	
    	if(amount==null) amount=FDCHelper.ZERO;
    	if(paymentProp==null) paymentProp=FDCHelper.ZERO;
    	if(completePrj==null) completePrj=FDCHelper.ZERO;
    	
    	if(cause.equals("amount")){
    		if(paymentProp.compareTo(FDCHelper.ZERO)==0){
    			if(completePrj.compareTo(FDCHelper.ZERO)==0){
    				return;
    			}else{
    				txtPaymentProportion.setValue(amount.setScale(4).divide(completePrj, BigDecimal.ROUND_HALF_UP).multiply(FDCHelper.ONE_HUNDRED));
    			}
    		}else{
    			txtcompletePrjAmt.setValue(amount.divide(paymentProp, BigDecimal.ROUND_HALF_UP).multiply(FDCHelper.ONE_HUNDRED));
    		}
    	}else if(cause.equals("completePrjAmt")){
    		/*
    		if(completePrj.subtract(amount).compareTo(new BigDecimal("0.01"))<0){
				MsgBox.showWarning(this, "��������������0,��������������깤����������֤���������ԭ�ҽ��/���깤������>0 ��0.01%");
				if(e.getOldValue() instanceof BigDecimal){
					if(((BigDecimal)e.getOldValue()).subtract(amount).compareTo(new BigDecimal("0.01"))>=0){
						txtcompletePrjAmt.setValue(e.getOldValue());
					}
						
				}
//				SysUtil.abort();
				return;
			}else{
				txtpaymentProportion.setValue(amount.divide(completePrj, BigDecimal.ROUND_HALF_UP).multiply(FDCHelper.ONE_HUNDRED));
			}*/
//    		if(completePrj.compareTo(FDCHelper.ZERO)==0){
//    			MsgBox.showWarning(this, "���깤����������Ϊ0");
//    			SysUtil.abort();
//    		}
    		if(completePrj.compareTo(FDCHelper.ZERO)==0){
    			return;
    		}
    		txtPaymentProportion.setValue(amount.setScale(4).divide(completePrj, BigDecimal.ROUND_HALF_UP).multiply(FDCHelper.ONE_HUNDRED));
    	}else{
			if(paymentProp.compareTo(FDCHelper.ZERO)==0){
				return;
			}else{
    			txtcompletePrjAmt.setValue(amount.setScale(4).divide(paymentProp, BigDecimal.ROUND_HALF_UP).multiply(FDCHelper.ONE_HUNDRED));
			}
    	}
    }
    
	
    /**
     * ���ǳ����ദ�����������Ϊ,ͳһ��FDCBillEditUI.handCodingRule�����д���
     */
    protected void setAutoNumberByOrg(String orgType) {
    	
    }
    protected void prmtsettlementType_dataChanged(DataChangeEvent e)
    		throws Exception {
//    	// TODO Auto-generated method stub
//    	super.prmtsettlementType_dataChanged(e);
		Object objNew=e.getNewValue();
//		if(objNew==null) return;
//		if(objNew instanceof SettlementTypeInfo
//				&&((SettlementTypeInfo)objNew).getId().toString().equals("e09a62cd-00fd-1000-e000-0b32c0a8100dE96B2B8E")){
//			//���㷽ʽΪ���������м��˺�Ϊ�Ǳ�¼
//			txtBank.setRequired(false);
//			txtBank.repaint();
//			txtBankAcct.setRequired(false);
//			txtBankAcct.repaint();
//		}else{
////			txtBank.setRequired(true);
////			txtBank.repaint();
////			txtBankAcct.setRequired(true);
////			txtBankAcct.repaint();
//		}
    	
    	if(objNew instanceof SettlementTypeInfo && ((SettlementTypeInfo) objNew).getName().equals("�ֽ�")){
    		txtBank.setRequired(false);
    		txtBankAcct.setRequired(false);
    		txtBank.setEnabled(false);
			txtBankAcct.setEnabled(false);
			txtBank.setText(null);
			txtBankAcct.setText(null);
		}else{
			txtBank.setRequired(true);
			txtBankAcct.setRequired(true);
			txtBank.setEnabled(true);
			txtBankAcct.setEnabled(true);
		}
    	txtBankAcct.repaint();
		txtBank.repaint();
    }
    
    public void actionAddNew_actionPerformed(ActionEvent e) throws Exception {
		// TODO Auto-generated method stub
		super.actionAddNew_actionPerformed(e);
		
		//handleCodingRule();
		txtPaymentRequestBillNumber.setText("");
		
    	if(prmtcurrency.getValue()!=null){
    		CurrencyInfo cur = (CurrencyInfo)prmtcurrency.getValue();
    		if(!this.baseCurrency.getId().toString().equals(cur.getId().toString())){
    			//txtexchangeRate.setEnabled(false);
    			prmtcurrency.setValue(baseCurrency);
    		}
    	}
    	this.txtMoneyDesc.setText(null);
    	this.chkUrgency.setEnabled(true);
//		chkNeedPaid.setEnabled(true);
		if(chkNeedPaid.isSelected()){
			txtNoPaidReason.setEnabled(true);
		}else{
			txtNoPaidReason.setEnabled(false);	
			txtNoPaidReason.setText(null);
		}
				
    	this.chkCostsplit.setEnabled(true);
//    	prmtuseDepartment.setValue(null);
    	txtamount.setValue(FDCHelper.ZERO);
    	txtBcAmount.setValue(FDCHelper.ZERO);
    	
    	this.txtPaymentProportion.setEditable(false);
		this.txtcompletePrjAmt.setEditable(false);
		if(isNotEnterCAS){
			this.chkNeedPaid.setEnabled(false);
			this.chkNeedPaid.setSelected(true);
		}
	}
    
    public void actionCopy_actionPerformed(ActionEvent e) throws Exception {

    	boolean isNeedPaid = chkNeedPaid.isSelected();
    	boolean chkUrgencyCopy = chkUrgency.isSelected();
    	String txtNoPaidReasonCopy = txtNoPaidReason.getText();
    	Object prmtAccountCopy = prmtAccount.getValue();
		String obj = txtattachment.getText();
		
    	super.actionCopy_actionPerformed(e);
    	//handleCodingRule();
    	txtPaymentRequestBillNumber.setText("");
    	prmtrealSupplier.setEnabled(true);
    	
    	if(prmtcurrency.getValue()!=null){
    		CurrencyInfo cur = (CurrencyInfo)prmtcurrency.getValue();
    		if(this.baseCurrency.getId().toString().equals(cur.getId().toString())){
    			txtexchangeRate.setEnabled(false);
    		}
    	}
    	
    	this.txtattachment.setText(obj);
    	this.chkUrgency.setEnabled(true);
    	this.chkCostsplit.setEnabled(true);
//		chkNeedPaid.setEnabled(true);
//		chkNeedPaid.setSelected(isNeedPaid);
		if(chkNeedPaid.isSelected()){
			txtNoPaidReason.setEnabled(true);
			txtNoPaidReason.setText(txtNoPaidReasonCopy);
			prmtAccount.setEnabled(true);
			prmtAccount.setValue(prmtAccountCopy);
		}else{
			txtNoPaidReason.setEnabled(false);	
			prmtAccount.setEnabled(false);
			txtNoPaidReason.setText(null);
			prmtAccount.setValue(null);
		}
		chkUrgency.setSelected(chkUrgencyCopy);
		//���ҵ�������ڵ�ǰ�ڼ�֮ǰ�ģ�����ҵ������Ϊ��ǰ�ڼ��һ��
		Date bookedDate  = (Date)pkbookedDate.getValue();
		if(bookedDate!=null && canBookedPeriod!=null && bookedDate.before(canBookedPeriod.getBeginDate()) ){
			this.editData.setBookedDate(canBookedPeriod.getBeginDate());
			pkbookedDate.setValue(canBookedPeriod.getBeginDate());
		}
		editData.setSourceType(SourceTypeEnum.ADDNEW);
		//new update by renliang
		if(isNotEnterCAS){
//			this.chkNeedPaid.setEnabled(false);
//			this.chkNeedPaid.setSelected(true);
		}
		setBgEditState();
    }
    
    //�޸� ����˵���͸��� û����ʾ����
    boolean modify;
    public boolean isModify(){
    	if (STATUS_VIEW.equals(getOprtState())) {
			return false;
		}
    	if(modify)return true;
    	return super.isModify();
    }
    
    private void addListener(){
    	txtMoneyDesc.addKeyListener(new KeyListener(){

			public void keyPressed(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}

			public void keyReleased(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}

			public void keyTyped(KeyEvent e) {
				modify=true;
			}
    		
    	});
    	
    	txtattachment.addKeyListener(new KeyListener(){

			public void keyPressed(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}

			public void keyReleased(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}

			public void keyTyped(KeyEvent e) {
				modify=true;
			}
    		
    	});
    }
    
	private void setPayerAcctEvi(CurrencyInfo currencyInfo) throws EASBizException, BOSException {
		String currencyId = currencyInfo.getId().toString();
		String companyId = company.getId().toString();
		String cuId = company.getCU().getId().toString();
		
		prmtAccount.getQueryAgent().resetRuntimeEntityView();
		
		// ���ݸ��ʽȡ�ù�������
		EntityViewInfo treeevi = CasRecPayHandler.getAccountViewEvi(cuId,
				companyId, currencyId, false);
		
		AccountPromptBox opseelect = new AccountPromptBox(this, company,
				treeevi.getFilter(), false, true);
		prmtAccount.setSelector(opseelect);
		EntityViewInfo evi = CasRecPayHandler.getAccountViewEvi(cuId,
				companyId, currencyId, true);
		
		prmtAccount.setEntityViewInfo(evi);
	}


    /**
     * output prmtAccount_willCommit method
     */
    protected void prmtAccount_willCommit(com.kingdee.bos.ctrl.swing.event.CommitEvent e) throws Exception
    {
		CurrencyInfo currencyInfo = (CurrencyInfo) prmtcurrency.getValue();
		if (currencyInfo == null) {
			e.setCanceled(true);
			return;
		}
		setPayerAcctEvi(currencyInfo);
    }

    /**
     * output prmtAccount_willShow method
     */
    protected void prmtAccount_willShow(com.kingdee.bos.ctrl.swing.event.SelectorEvent e) throws Exception
    {
		CurrencyInfo currencyInfo = (CurrencyInfo) prmtcurrency.getValue();
		if (currencyInfo == null) {
			e.setCanceled(true);
			return;
		}
		setPayerAcctEvi(currencyInfo);
    }

    /**
     * output prmtAccount_dataChanged method
     */
    protected void prmtAccount_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
    {
    	
    }
    
    protected boolean isUseMainMenuAsTitle(){
    	return false;
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
        ContractWithoutTextPrintDataProvider data = new ContractWithoutTextPrintDataProvider(editData.getString("id"),getTDQueryPK());
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
        ContractWithoutTextPrintDataProvider data = new ContractWithoutTextPrintDataProvider(editData.getString("id"),getTDQueryPK());
        com.kingdee.bos.ctrl.report.forapp.kdnote.client.KDNoteHelper appHlp = new com.kingdee.bos.ctrl.report.forapp.kdnote.client.KDNoteHelper();
        appHlp.printPreview(getTDFileName(), data, javax.swing.SwingUtilities.getWindowAncestor(this));
	}
	//������ı���ͬ�״��Ӧ��Ŀ¼
	protected String getTDFileName() {
    	return "/bim/fdc/contract/ContractWithoutText";
	}
	//��Ӧ���״�Query
	protected IMetaDataPK getTDQueryPK() {
    	return new MetaDataPK("com.kingdee.eas.fdc.contract.app.ContractWithoutTextPrintQuery");
	}
    
	/**
	 * RPC���죬�κ�һ���¼�ֻ��һ��RPC
	 */
	
	public boolean isPrepareInit() {
    	return true;
    }
	
	public boolean isPrepareActionSubmit() {
    	return false;
    }
	
	public boolean isPrepareActionSave() {
    	return false;
    }
	
	public RequestContext prepareActionSubmit(IItemAction itemAction)
	throws Exception {
		RequestContext request = super.prepareActionSubmit(itemAction);
		
		return request;
	}
	
    /**
     * EditUI�ṩ�ĳ�ʼ��handler��������
     */
    protected void PrepareHandlerParam(RequestContext request)
    {
        super.PrepareHandlerParam(request);
        

    }
	protected void initWorkButton() {
		super.initWorkButton();
		actionViewBgBalance.putValue(Action.SMALL_ICON, EASResource.getIcon("imgTbtn_sequencecheck"));
	
	}
	//�鿴Ԥ�����
	public void actionViewBgBalance_actionPerformed(ActionEvent e) throws Exception {
		storeFields();
		//ҵ������Ĭ��δ���ã����Ԥ�����ʱ��ҵ�����ڿ��ƣ��ᵼ�²鿴Ԥ��ʱ�鲻�����ֶ�����һ��
		editData.setBizDate((Date) this.pkbookedDate.getValue());
		ContractWithoutTextInfo info = editData;
		IBgControlFacade iCtrl = BgControlFacadeFactory.getRemoteInstance();
		BgCtrlResultCollection coll = iCtrl.getBudget(
				FDCConstants.ContractWithoutText, null, info);

		UIContext uiContext = new UIContext(this);
		uiContext.put(BgHelper.BGBALANCE, coll);

		IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.MODEL)
				.create(BgBalanceViewUI.class.getName(), uiContext, null,
						STATUS_VIEW);
		uiWindow.show();
		info=null;
	}

	 public void fillAttachmentList(){
	    	this.cmbAttachment.removeAllItems();
			String boId = null;
			if(this.editData.getId() == null){
				return;
			}else{
				boId = this.editData.getId().toString();
			}
			
			if(boId != null){
				SelectorItemCollection sic = new SelectorItemCollection();
				sic.add(new SelectorItemInfo("id"));
				sic.add(new SelectorItemInfo("attachment.id"));
				sic.add(new SelectorItemInfo("attachment.name"));
				
				FilterInfo filter = new FilterInfo();
				filter.getFilterItems().add(new FilterItemInfo("boID",boId));
				
				EntityViewInfo evi = new EntityViewInfo();
				evi.setFilter(filter);
				evi.setSelector(sic);
				BoAttchAssoCollection cols = null;
				 try {
					cols =BoAttchAssoFactory.getRemoteInstance().getBoAttchAssoCollection(evi);
				} catch (BOSException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if(cols != null && cols.size()>0){
					for(Iterator it = cols.iterator();it.hasNext();){
						AttachmentInfo attachment = ((BoAttchAssoInfo)it.next()).getAttachment();
						this.cmbAttachment.addItem(attachment);
					}
					this.isHasAttachment = true;
				}else{
					this.isHasAttachment =false;
				}
			}
			this.btnViewAttachment.setEnabled(this.isHasAttachment);
//			this.viewAttachment.setEnabled(isHasAttachment);
	    }
	 
	 public void actionAttachment_actionPerformed(ActionEvent e)
			throws Exception {
		// TODO Auto-generated method stub
		super.actionAttachment_actionPerformed(e);
		fillAttachmnetTable();
	}
	 
	 public void actionViewAttachment_actionPerformed(ActionEvent e)
			throws Exception {
		// TODO Auto-generated method stub
		super.actionViewAttachment_actionPerformed(e);
		String attachId = null;
    	if(this.cmbAttachment.getSelectedItem() != null && this.cmbAttachment.getSelectedItem() instanceof AttachmentInfo){
    		attachId = ((AttachmentInfo)this.cmbAttachment.getSelectedItem()).getId().toString();
    		AttachmentClientManager acm = AttachmentManagerFactory.getClientManager();
    		acm.viewAttachment(attachId);
    	}
	}
	 
	protected void lockContainer(Container container) {
		 if(lblAttachmentContainer.getName().equals(container.getName())){
	        	return;
	        }
	        else{
	        	super.lockContainer(container);
	        }
	}
	
	/**
	 * ������Դ��R100806-236��ͬ¼�롢��������㵥�ݽ�������������ť
	 * <P>
	 * Ϊʵ�ִ�BT��������д���·���
	 * 
	 * @author owen_wen 2011-03-08
	 */
	protected void setAuditButtonStatus(String oprtType) {
		if (STATUS_VIEW.equals(oprtType) || STATUS_EDIT.equals(oprtType)) {
			actionAudit.setVisible(true);
			actionUnAudit.setVisible(true);
			actionAudit.setEnabled(true);
			actionUnAudit.setEnabled(true);

			FDCBillInfo bill = (FDCBillInfo) editData;
			if (editData != null) {
				if (FDCBillStateEnum.AUDITTED.equals(bill.getState())) {
					actionUnAudit.setVisible(true);
					actionUnAudit.setEnabled(true);
					actionAudit.setVisible(false);
					actionAudit.setEnabled(false);
				} else {
					actionUnAudit.setVisible(false);
					actionUnAudit.setEnabled(false);
					actionAudit.setVisible(true);
					actionAudit.setEnabled(true);
				}
			}
		} else {
			actionAudit.setVisible(false);
			actionUnAudit.setVisible(false);
			actionAudit.setEnabled(false);
			actionUnAudit.setEnabled(false);
		}
	}

	/**
	 * ������Դ��R100806-236��ͬ¼�롢��������㵥�ݽ�������������ť
	 * <P>
	 * Ϊʵ�ִ�BT��������д���·���
	 * 
	 * @author owen_wen 2011-03-08
	 */
	public void checkBeforeAuditOrUnAudit(FDCBillStateEnum state, String warning) throws Exception {

		isSameUserForUnAudit = FDCUtils.getDefaultFDCParamByKey(null, null, FDCConstants.FDC_PARAM_AUDITORMUSTBETHESAMEUSER);

		if (isSameUserForUnAudit && editData.getAuditor() != null) {

			if (!SysContext.getSysContext().getCurrentUserInfo().getId().equals(editData.getAuditor().getId())) {
				try {
					throw new FDCBasedataException(FDCBasedataException.AUDITORMUSTBETHESAMEUSER);
				} catch (FDCBasedataException e) {
					handUIExceptionAndAbort(e);
				}
			}
		}
		// ��鵥���Ƿ��ڹ�������
		FDCClientUtils.checkBillInWorkflow(this, getSelectBOID());

		if (editData == null || editData.getState() == null || !editData.getState().equals(state)) {
			MsgBox.showWarning(this, FDCClientUtils.getRes(warning));
			SysUtil.abort();
		}
	}
	
	/**
	 * ������ҵ�����ڷ����仯����Ҫ���ݵ�UIContext��ͨ��UIContext�ڴ��ݵ��ƻ���ĿF7
	 */
	protected void pkbookedDate_dataChanged(DataChangeEvent e) throws Exception {
		this.getUIContext().put("bookedDate", this.pkbookedDate.getSqlDate());
		Calendar cal = Calendar.getInstance();
		cal.setTime((Date) pkbookedDate.getValue());
		int year = cal.get(Calendar.YEAR);
		int month = cal.get(Calendar.MONTH) + 1;
		prmtPlanProject.setDisplayFormat("$payMattersName$ " + year + "��" + month + "�� �޺�ͬ�ƻ�");
		this.prmtPlanProject.setData(null);
		
//		getBgAmount();
		getPayPlanValue();
	}

	/**
	 * ��������ʼ���ƻ���ĿF7
	 * <p>
	 * added by cp 2011/03-04
	 */
	public void initFDCDepConPayPlanF7() throws Exception {
		if (chkCostsplit.isSelected() && !"������".equals(CONTROLNOCONTRACT)) {
//			prmtPlanProject.setRequired(true);
		}
		prmtPlanProject
				.setQueryInfo("com.kingdee.eas.fdc.finance.app.FDCDepConPayPlanNoContractF7Query");
		prmtPlanProject.setEditFormat("$payMattersName$");
		FDCDepConPayPlanSelector f7Selector = new FDCDepConPayPlanSelector(this);
		this.prmtPlanProject.setSelector(f7Selector);
	}

	/**
	 * �������鿴Ԥ��
	 * <p>
	 * added by cp 2011/03-04
	 */
	public void actionViewBudget_actionPerformed(ActionEvent e)
			throws Exception {
		FDCDepConPayPlanNoContractInfo plan = (FDCDepConPayPlanNoContractInfo) prmtPlanProject
		.getValue();
		if (plan == null) {
			MsgBox.showWarning("��Ԥ�㣬�ݲ��ܲ鿴");
			SysUtil.abort();
		}
		String payMatters = plan.getPayMatters();
		String planNum = plan.getHead().getNumber();

		Map uiContext = getUIContext();
		Date bookDate = (Date) pkbookedDate.getValue();
		Date firstDay = BudgetViewUtils.getFirstDay(bookDate);
		Date lastDay = BudgetViewUtils.getLastDay(bookDate);
		String projectId = editData.getCurProject().getId().toString();
		Map rt = getLocalBudget(firstDay, lastDay, projectId, planNum,
				payMatters);
		String curPrj = (String) rt.get("curProject");
		uiContext.put("curProject", curPrj);
		String curDept = (String) rt.get("curDept");
		uiContext.put("curDept", curDept);
		String planMonth = (String) rt.get("planMonth");
		uiContext.put("planMonth", planMonth);
		BigDecimal localBudget = (BigDecimal) rt.get("localBudget");
		uiContext.put("localBudget", localBudget);
		BigDecimal actPaied = getActPaied(firstDay, lastDay, projectId, planNum, payMatters);
		uiContext.put("actPaied", actPaied);
		BigDecimal floatFund = getFloatFund(firstDay, lastDay, projectId, planNum, payMatters);
		uiContext.put("floatFund", floatFund);
		BigDecimal bgBalance = localBudget.subtract(floatFund).subtract(actPaied);
		uiContext.put("bgBalance", bgBalance);
		BudgetViewUtils.showModelUI(uiContext);
	}

	/*
	 * ��ȡ���¿������
	 */
	protected BigDecimal getBgBalance() throws BOSException, SQLException {
		FDCDepConPayPlanNoContractInfo plan = (FDCDepConPayPlanNoContractInfo) prmtPlanProject
				.getValue();
		if (plan != null) {
		String payMatters = plan.getPayMatters();
		String planNum = plan.getHead().getNumber();

		Map uiContext = getUIContext();
		Date bookDate = (Date) pkbookedDate.getValue();
		Date firstDay = BudgetViewUtils.getFirstDay(bookDate);
		Date lastDay = BudgetViewUtils.getLastDay(bookDate);
		String projectId = editData.getCurProject().getId().toString();
		Map rt = getLocalBudget(firstDay, lastDay, projectId, planNum,
				payMatters);
		String curDept = (String) rt.get("curDept");
		uiContext.put("curDept", curDept);
		String planMonth = (String) rt.get("planMonth");
		uiContext.put("planMonth", planMonth);
		BigDecimal localBudget = (BigDecimal) rt.get("localBudget");
		uiContext.put("localBudget", localBudget);
		BigDecimal actPaied = getActPaied(firstDay, lastDay, projectId, planNum, payMatters);
		uiContext.put("actPaied", actPaied);
		BigDecimal floatFund = getFloatFund(firstDay, lastDay, projectId, planNum, payMatters);
		uiContext.put("floatFund", floatFund);
		
		return localBudget.subtract(floatFund).subtract(actPaied);
		}else{
			return FDCHelper.ZERO;
		}
	}

	/**
	 * �����Ѹ�
	 * 
	 * @param firstDay
	 * @param lastDay
	 * @param projectId
	 * @param planNum
	 * @param payMatters
	 * @return
	 * @throws BOSException
	 * @throws SQLException
	 */
	protected BigDecimal getActPaied(Date firstDay, Date lastDay,
			String projectId, String planNum, String payMatters)
			throws BOSException, SQLException {
		BigDecimal actPaied = FDCHelper.ZERO;
		FDCSQLBuilder builderActPaied = new FDCSQLBuilder();
		builderActPaied
				.appendSql(" select sum(pay.FLocalAmount) as actPaied from t_cas_paymentbill as pay ");
		builderActPaied
				.appendSql(" left join T_CON_ContractWithoutText as con on con.FID = pay.FContractBillID ");
		builderActPaied
				.appendSql(" left join T_FNC_FDCDepConPayPlanNoCon as np on np.FID = con.FFdcDepConPlanID ");
		builderActPaied
				.appendSql(" left join T_FNC_FDCDepConPayPlanBill as pb on pb.FID = np.FHeadID ");
		builderActPaied.appendSql(" where pay.FBillStatus = 15 ");
		builderActPaied.appendSql(" and pay.FBizDate >= ");
		builderActPaied.appendParam(firstDay);
		builderActPaied.appendSql(" and pay.FBizDate <= ");
		builderActPaied.appendParam(lastDay);
		builderActPaied.appendSql(" and pay.FCurProjectID = ");
		builderActPaied.appendParam(projectId);
		builderActPaied.appendSql(" and pb.FNumber = ");
		builderActPaied.appendParam(planNum);
		builderActPaied.appendSql(" and np.FPayMatters = ");
		builderActPaied.appendParam(payMatters);
		// �鿴״̬����ǰ��
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
	protected BigDecimal getFloatFund(Date firstDay, Date lastDay,
			String projectId, String planNum, String payMatters) throws BOSException, SQLException {
		
		BigDecimal floatFund = FDCHelper.ZERO;
		FDCSQLBuilder builderFloatFund = new FDCSQLBuilder();
		builderFloatFund
				.appendSql(" select sum(con.FAmount) as floatFund from T_CON_ContractWithoutText as con ");
		builderFloatFund
				.appendSql(" left join T_FNC_FDCDepConPayPlanNoCon as np on np.FID = con.FFdcDepConPlanID ");
		builderFloatFund
				.appendSql(" left join T_FNC_FDCDepConPayPlanBill as pb on pb.FID = np.FHeadID ");
		builderFloatFund
				.appendSql(" where (con.FState = '2SUBMITTED' or con.FState = '3AUDITTING') ");
		builderFloatFund.appendSql(" and con.FBookedDate >= ");
		builderFloatFund.appendParam(firstDay);
		builderFloatFund.appendSql(" and con.FBookedDate <= ");
		builderFloatFund.appendParam(lastDay);
		builderFloatFund.appendSql(" and con.FCurProjectID = ");
		builderFloatFund.appendParam(projectId);
		builderFloatFund.appendSql(" and pb.FNumber =  ");
		builderFloatFund.appendParam(planNum);
		builderFloatFund.appendSql(" and np.FPayMatters = ");
		builderFloatFund.appendParam(payMatters);
		// �鿴״̬����ǰ��
		if (STATUS_VIEW.equals(getOprtState())) {
			builderFloatFund.appendSql(" and con.FLastUpdateTime <= ");
			builderFloatFund.appendSql("{ts '");
			builderFloatFund.appendSql(FMConstants.FORMAT_TIME.format(editData
					.getLastUpdateTime()));
			builderFloatFund.appendSql("' }");
			// �˴���ȷ��ʱ���룬���Բ������¾�
			// builderFloatFund.appendParam(editData.getLastUpdateTime());
		}
		if (editData.getId() != null) {
			builderFloatFund.appendSql(" and con.FID != ");
			builderFloatFund.appendParam(editData.getId().toString());
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
	protected Map getLocalBudget(Date firstDay, Date lastDay, String projectId,
			String planNum, String payMatters) throws BOSException,
			SQLException {
		Map rt = new HashMap();
		FDCDepConPayPlanNoContractInfo plan = (FDCDepConPayPlanNoContractInfo) prmtPlanProject
				.getValue();
		if (plan == null) {
			CurProjectInfo prj = (CurProjectInfo) prmtcurProject.getValue();
			String prjName = "";
			if (prj != null) {
				prjName = prj.getName();
			}
			rt.put("curProject", prjName);
			AdminOrgUnitInfo dpt = (AdminOrgUnitInfo) prmtuseDepartment
					.getValue();
			String curDept = "";
			if (dpt != null) {
				curDept = dpt.getName();
			}
			rt.put("curDept", curDept);
			SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM");
			String planMonth = ft.format(editData.getBookedDate());
			rt.put("planMonth", planMonth);
			rt.put("localBudget", FDCHelper.ZERO);
			return rt;
		}
		
		BigDecimal localBudget = FDCHelper.ZERO;
		FDCSQLBuilder builder = new FDCSQLBuilder();
		builder
				.appendSql("select prj.FName_l2 as prjName,admin.FName_l2 as curDept,entry.FMonth as planMonth,entry.FOfficialPay as localBudget ");
		builder.appendSql("from T_FNC_FDCDepConPayPlanNoCon as con ");
		builder
				.appendSql("left join T_FNC_FDCDepConPayPlanBill as head on head.FID = con.FHeadID ");
		builder
				.appendSql("left join T_FNC_FDCDepConPayPlanNCEntry as entry on entry.FParentNC = con.FID ");
		builder
				.appendSql("left join T_ORG_Admin as admin on admin.FID = head.FDeptmentID ");
		builder.appendSql(" left join T_FDC_CurProject as prj on prj.FID = con.FProjectID ");
		builder
				.appendSql("where ");
		builder.appendSql(" entry.FMonth >= ");
		builder.appendParam(firstDay);
		builder.appendSql(" and entry.FMonth <= ");
		builder.appendParam(lastDay);
		// builder.appendSql(" and con.FProjectID = ");
		// builder.appendParam(projectId);
		builder.appendSql(" and con.FID = ");
		builder.appendParam(plan.getId().toString());
//		builder.appendSql(" and head.FNumber = ");
//		builder.appendParam(planNum);
		builder.appendSql(" order by head.FYear desc,head.FMonth DESC");
		IRowSet rowSet = builder.executeQuery();
		while (rowSet.next()) {
			String curPrj = rowSet.getString("prjName");
			rt.put("curProject", curPrj);
			String curDept = rowSet.getString("curDept");
			rt.put("curDept", curDept);
			String planMonth = rowSet.getString("planMonth");
			rt.put("planMonth", planMonth);
			if (rowSet.getString("localBudget") != null) {
				localBudget = rowSet.getBigDecimal("localBudget");
				rt.put("localBudget", localBudget);
			}
		}
		return rt;
	}
	
	protected void chkCostsplit_actionPerformed(ActionEvent e) throws Exception {
		if (chkCostsplit.isSelected()) {
//			prmtPlanProject.setRequired(true);
		} else {
			prmtPlanProject.setRequired(false);
		}
	}
	
	public void actionViewProgramContract_actionPerformed(ActionEvent e)
			throws Exception {
		if (this.editData.getProgrammingContract() == null) {
			MsgBox.showInfo(this, "�ú�ͬû�й�����ܺ�Լ!");
			this.abort();
		}

		UIContext uiContext = new UIContext(this);
		IUIWindow uiWindow = null;
		ContractWithoutTextInfo  contractWithoutTextInfo  = (ContractWithoutTextInfo ) editData;
		ProgrammingContractInfo proContInfo = getProContInfo(contractWithoutTextInfo.getProgrammingContract().getId().toString());
		contractWithoutTextInfo.setProgrammingContract(proContInfo);
		uiContext.put("programmingContract", proContInfo);
		uiContext.put("programmingContractTemp", "programmingContractTemp");
		uiWindow = UIFactory.createUIFactory(UIFactoryName.MODEL).create(ProgrammingContractEditUI.class.getName(), uiContext, null, OprtState.VIEW);
		uiWindow.show();
	}
	
	private ProgrammingContractInfo getProContInfo(String id) throws EASBizException, BOSException {
		SelectorItemCollection selItemCol = new SelectorItemCollection();
		selItemCol.add("*");
		selItemCol.add("costEntries.*");
		selItemCol.add("costEntries.costAccount.*");
		selItemCol.add("costEntries.costAccount.curProject.*");
		selItemCol.add("economyEntries.*");
		selItemCol.add("economyEntries.paymentType.*");
		ProgrammingContractInfo pcInfo = ProgrammingContractFactory.getRemoteInstance().getProgrammingContractInfo(new ObjectUuidPK(id), selItemCol);
		return pcInfo;
	}
	
	public void actionProgram_actionPerformed(ActionEvent e) throws Exception {
		showProgramContract();
		getPayPlanValue();
	}
	
	private void showProgramContract() throws Exception {
		
		IUIWindow uiWindow = null;
		UIContext uiContext = new UIContext(this);
		ContractWithoutTextInfo contractWithoutTextInfo = editData;
		if(this.prmtContractType.getValue()==null){
			FDCMsgBox.showWarning(this,"����ѡ���ͬ���ͣ�");
			return;
		}
		contractWithoutTextInfo.setContractType((ContractTypeInfo) this.prmtContractType.getValue());
		uiContext.put("contractWithoutTextInfo", contractWithoutTextInfo);
		//�Ƿ�ȫ�ں�ͬ
		uiContext.put("isWholeAgeStage", new Boolean(curProject.isIsWholeAgeStage()));
		uiWindow = UIFactory.createUIFactory(UIFactoryName.MODEL).create(ContractBillLinkProgContEditUI.class.getName(), uiContext, null,OprtState.EDIT);
		uiWindow.show();
		if (contractWithoutTextInfo.getProgrammingContract() != null) {
			this.txtPCName.setText(contractWithoutTextInfo.getProgrammingContract().getName());
		} else {
			this.txtPCName.setText(null);
		}
	}
	protected void prmtContractType_dataChanged(DataChangeEvent e) throws Exception {
		boolean isChanged = true;
		isChanged = BizCollUtil.isF7ValueChanged(e);
        if(!isChanged){
        	return;
        }
        ContractTypeInfo ct=(ContractTypeInfo) this.prmtContractType.getValue();
        setProgAndAccountState(ct);
        
        Set id=new HashSet();
		if(ct!=null){
			if(this.editData.getProgrammingContract()!=null&&this.editData.getProgrammingContract().getContractType()!=null
					&&!this.editData.getProgrammingContract().getContractType().getId().equals(ct.getId())){
				this.editData.setProgrammingContract(null);
				this.txtPCName.setText(null);
				getPayPlanValue();
			}
			SelectorItemCollection sel=new SelectorItemCollection();
			sel.add("entry.payContentType.*");
			sel.add("*");
			ct=ContractTypeFactory.getRemoteInstance().getContractTypeInfo(new ObjectUuidPK(ct.getId()),sel);
			this.prmtPayContentType.setEnabled(true);
			
			for(int i=0;i<ct.getEntry().size();i++){
				if(ct.getEntry().get(i).getPayContentType()!=null){
					id.add(ct.getEntry().get(i).getPayContentType().getId().toString());
				}
			}
			if(ct.getEntry().size()==1&&ct.getEntry().get(0).getPayContentType()!=null){
				this.prmtPayContentType.setValue(ct.getEntry().get(0).getPayContentType());
			}
			
			chkCostsplit.setSelected(ct.isIsCost());
			
			ContractTypeInfo info=ContractTypeFactory.getRemoteInstance().getContractTypeInfo("select *,contractWFTypeEntry.contractWFType.*,inviteTypeEntry.inviteType.* from where id='"+ct.getId().toString()+"'");
			String paramValue="true";
			try {
				paramValue = ParamControlFactory.getRemoteInstance().getParamValue(new ObjectUuidPK(SysContext.getSysContext().getCurrentOrgUnit().getId()), "YF_MARKETPROJECTCONTRACT");
			} catch (EASBizException e1) {
				e1.printStackTrace();
			} catch (BOSException e1) {
				e1.printStackTrace();
			}
			if(info.isIsMarket()&&"true".equals(paramValue)){
				this.prmtMarketProject.setEnabled(true);
				this.prmtMarketProject.setRequired(true);
				this.prmtMpCostAccount.setEnabled(true);
				this.prmtMpCostAccount.setRequired(true);
				this.actionMALine.setEnabled(true);
				this.actionMRLine.setEnabled(true);
			}else{
				this.prmtMarketProject.setEnabled(false);
				this.prmtMarketProject.setRequired(false);
				this.prmtMpCostAccount.setEnabled(false);
				this.prmtMpCostAccount.setRequired(false);
				this.actionMALine.setEnabled(false);
				this.actionMRLine.setEnabled(false);
				this.prmtMarketProject.setValue(null);
				this.prmtMpCostAccount.setValue(null);
				this.tblMarket.removeRows();
			}
		}else{
			this.prmtPayContentType.setEnabled(false);
			
			this.prmtMarketProject.setEnabled(false);
			this.prmtMarketProject.setRequired(false);
			this.prmtMpCostAccount.setEnabled(false);
			this.prmtMpCostAccount.setRequired(false);
			this.actionMALine.setEnabled(false);
			this.actionMRLine.setEnabled(false);
			this.prmtMarketProject.setValue(null);
			this.prmtMpCostAccount.setValue(null);
			this.tblMarket.removeRows();
		}
		
		if(this.prmtPayContentType.getValue()!=null&&!id.contains(((PayContentTypeInfo)this.prmtPayContentType.getValue()).getId().toString())){
			this.prmtPayContentType.setValue(null);
		}
		EntityViewInfo view=new EntityViewInfo();
		FilterInfo filter=new FilterInfo();
		if(id.size()>0){
			filter.getFilterItems().add(new FilterItemInfo("id",id,CompareType.INCLUDE));
		}else{
			filter.getFilterItems().add(new FilterItemInfo("id",null));
		}
		CurProjectInfo project=CurProjectFactory.getRemoteInstance().getCurProjectInfo(new ObjectUuidPK(this.editData.getCurProject().getId()));
		if(project.isIsOA()){
			filter.getFilterItems().add(new FilterItemInfo("description", "2021��������������"));
			filter.getFilterItems().add(new FilterItemInfo("wtOaTId",null,CompareType.ISNOT));
		}else{
			filter.getFilterItems().add(new FilterItemInfo("description", null,CompareType.IS));
			filter.getFilterItems().add(new FilterItemInfo("description", "2021��������������",CompareType.NOTEQUALS));
			filter.setMaskString("#0 and (#1 or #2)");
		}
		view.setFilter(filter);
		this.prmtPayContentType.setEntityViewInfo(view);
	}

	public void actionRemove_actionPerformed(ActionEvent e) throws Exception {
		FDCClientUtils.checkBillInWorkflow(this, getSelectBOID());
//		EntityViewInfo view = new EntityViewInfo();
//		FilterInfo filter = new FilterInfo();
//		filter.getFilterItems().add(new FilterItemInfo("conWithoutText.id", editData.getId()));
//		view.setFilter(filter);
//		view.getSelector().add("id");
//		CoreBillBaseCollection coll = PaymentSplitFactory.getRemoteInstance()
//				.getCoreBillBaseCollection(view);
//		Iterator iter = coll.iterator();
//		if (iter.hasNext()) {
//			MsgBox.showWarning(this, "����ɾ����Ӧ�޺�ͬ��֣�");
//			SysUtil.abort();
//		}
		super.actionRemove_actionPerformed(e);
	}
	protected void tblAttachement_tableClicked(KDTMouseEvent e)throws Exception {
		if(e.getType() == 1 && e.getButton() == 1 && e.getClickCount() == 2){
			IRow row  =  tblAttachement.getRow(e.getRowIndex());
			getFileGetter();
			Object selectObj= row.getCell("id").getValue();
			if(selectObj!=null){
				String attachId=selectObj.toString();
				fileGetter.viewAttachment(attachId);
			}
		}
	}
	private  FileGetter fileGetter;
	private  FileGetter getFileGetter() throws Exception {
        if (fileGetter == null)
            fileGetter = new FileGetter((IAttachment) AttachmentFactory.getRemoteInstance(), AttachmentFtpFacadeFactory.getRemoteInstance());
        return fileGetter;
    }
	public void fillAttachmnetTable() throws EASBizException, BOSException {
		this.tblAttachement.removeRows();
		String boId = null;
		if (this.editData.getId() == null) {
			return;
		} else {
			boId = this.editData.getId().toString();
		}
		if (boId != null) {
			SelectorItemCollection sic = new SelectorItemCollection();
			sic.add(new SelectorItemInfo("id"));
			sic.add(new SelectorItemInfo("attachment.id"));
			sic.add(new SelectorItemInfo("attachment.name"));
			sic.add(new SelectorItemInfo("attachment.createTime"));
			sic.add(new SelectorItemInfo("attachment.attachID"));
			sic.add(new SelectorItemInfo("attachment.beizhu"));
			sic.add(new SelectorItemInfo("assoType"));
			sic.add(new SelectorItemInfo("boID"));

			FilterInfo filter = new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("boID", boId));
			EntityViewInfo evi = new EntityViewInfo();
			evi.getSorter().add(new SorterItemInfo("boID"));
			evi.getSorter().add(new SorterItemInfo("attachment.name"));
			evi.setFilter(filter);
			evi.setSelector(sic);
			BoAttchAssoCollection cols = null;
			try {
				cols = BoAttchAssoFactory.getRemoteInstance().getBoAttchAssoCollection(evi);
			} catch (BOSException e) {
				e.printStackTrace();
			}
			boolean flag = false;
			if (cols != null && cols.size() > 0) {
				for (Iterator it = cols.iterator(); it.hasNext();) {
					BoAttchAssoInfo boaInfo = (BoAttchAssoInfo)it.next();
					AttachmentInfo attachment = boaInfo.getAttachment();
					IRow row = tblAttachement.addRow();
					row.getCell("id").setValue(attachment.getId().toString());
					row.getCell("seq").setValue(attachment.getAttachID());
					row.getCell("name").setValue(attachment.getName());
					row.getCell("date").setValue(attachment.getCreateTime());
					row.getCell("type").setValue(boaInfo.getAssoType());
				}
			}
		}
	}
	protected void cbFeeType_itemStateChanged(ItemEvent e) throws Exception {
		this.kDTabbedPane1.removeAll();
		this.kDTabbedPane1.add(this.contBgEntry,"�����嵥");
		if(!"��Ӧ��".equals(this.comboPayeeType.getSelectedItem().toString())){
			if(!isFeeTraEntry){
				this.cbFeeType.setRequired(false);	
				this.contFeeType.setVisible(false);
			}else{
				this.cbFeeType.setRequired(true);
				this.contFeeType.setVisible(true);
				if(FeeTypeEnum.FEE.equals(cbFeeType.getSelectedItem())){
					this.kDTabbedPane1.add(this.contFeeEntry,"������ϸ");
					if(this.kdtFeeEntry.getRowCount()==0)this.kdtFeeEntry.addRow();
				}else if(FeeTypeEnum.TRA.equals(cbFeeType.getSelectedItem())){
					this.kDTabbedPane1.add(this.contTraEntry,"������ϸ");
					if(this.kdtTraEntry.getRowCount()==0)this.kdtTraEntry.addRow();
				}
			}
		}else{
			this.cbFeeType.setRequired(false);	
			this.contFeeType.setVisible(false);
		}
		this.kDTabbedPane1.add(this.kDContainer4,"Ӫ����ͬ��̯��ϸ");
	}
	protected void kdtFeeEntry_editStopped(KDTEditEvent e) throws Exception {
		if(this.kdtFeeEntry.getColumnKey(e.getColIndex()).equals("amount")){
			TableUtils.getFootRow(this.kdtFeeEntry,new String[]{"amount"});
		}
	}
	protected void kdtTraEntry_editStopped(KDTEditEvent e) throws Exception {
		if(this.kdtTraEntry.getColumnKey(e.getColIndex()).equals("startDate")||this.kdtTraEntry.getColumnKey(e.getColIndex()).equals("endDate")){
			Date startDate=(Date) this.kdtTraEntry.getRow(e.getRowIndex()).getCell("startDate").getValue();
			Date endDate=(Date) this.kdtTraEntry.getRow(e.getRowIndex()).getCell("endDate").getValue();
			if(startDate!=null&&endDate!=null){
				if(startDate.after(endDate)){
					FDCMsgBox.showWarning(this,"��ʼ���ڲ������ڽ�������");
					this.kdtTraEntry.getRow(e.getRowIndex()).getCell(e.getColIndex()).setValue(e.getOldValue());
				}else{
					this.kdtTraEntry.getRow(e.getRowIndex()).getCell("days").setValue(FDCDateHelper.getDiffDays(startDate, endDate));
				}
			}
		}else{
			BigDecimal airFee=FDCHelper.toBigDecimal(this.kdtTraEntry.getRow(e.getRowIndex()).getCell("airFee").getValue());
			BigDecimal carFee=FDCHelper.toBigDecimal(this.kdtTraEntry.getRow(e.getRowIndex()).getCell("carFee").getValue());
			BigDecimal cityFee=FDCHelper.toBigDecimal(this.kdtTraEntry.getRow(e.getRowIndex()).getCell("cityFee").getValue());
			BigDecimal otherFee=FDCHelper.toBigDecimal(this.kdtTraEntry.getRow(e.getRowIndex()).getCell("otherFee").getValue());
			BigDecimal liveFee=FDCHelper.toBigDecimal(this.kdtTraEntry.getRow(e.getRowIndex()).getCell("liveFee").getValue());
			BigDecimal allowance=FDCHelper.toBigDecimal(this.kdtTraEntry.getRow(e.getRowIndex()).getCell("allowance").getValue());
			BigDecimal other=FDCHelper.toBigDecimal(this.kdtTraEntry.getRow(e.getRowIndex()).getCell("other").getValue());
			BigDecimal total=airFee.add(carFee).add(cityFee).add(otherFee).add(liveFee).add(allowance).add(other);
			this.kdtTraEntry.getRow(e.getRowIndex()).getCell("total").setValue(total);
		}
		TableUtils.getFootRow(this.kdtTraEntry,new String[]{"days","airFee","carFee","cityFee","otherFee","persons","liveDays","liveFee","allowance","other","total"});
	}
	private void setInvoiceAmt(){
		BigDecimal totalAmount=FDCHelper.ZERO;
		BigDecimal amount=FDCHelper.ZERO;
		for(int i=0;i<this.kdtInvoiceEntry.getRowCount();i++){
			totalAmount=FDCHelper.add(totalAmount, this.kdtInvoiceEntry.getRow(i).getCell("totalAmount").getValue());
			amount=FDCHelper.add(amount,this.kdtInvoiceEntry.getRow(i).getCell("amount").getValue());
		}
		this.txtInvoiceAmt.setValue(totalAmount);
		this.txtRateAmount.setValue(amount);
	}
	protected void kdtInvoiceEntry_editStopped(KDTEditEvent e) throws Exception {
		setInvoiceAmt();
	}
	protected void initMarketEntryTable() throws BOSException {
		this.tblMarket.checkParsed();
		this.tblMarket.setEditable(true);
		KDWorkButton btnAddRowinfo = new KDWorkButton();
		KDWorkButton btnDeleteRowinfo = new KDWorkButton();

		this.actionMALine.putValue("SmallIcon", EASResource.getIcon("imgTbtn_addline"));
		btnAddRowinfo = (KDWorkButton) this.kDContainer4.add(this.actionMALine);
		btnAddRowinfo.setText("������");
		btnAddRowinfo.setSize(new Dimension(140, 19));

		this.actionMRLine.putValue("SmallIcon", EASResource.getIcon("imgTbtn_deleteline"));
		btnDeleteRowinfo = (KDWorkButton) this.kDContainer4.add(this.actionMRLine);
		btnDeleteRowinfo.setText("ɾ����");
		btnDeleteRowinfo.setSize(new Dimension(140, 19));
		
		KDFormattedTextField amount = new KDFormattedTextField();
		amount.setDataType(KDFormattedTextField.BIGDECIMAL_TYPE);
		amount.setDataVerifierType(KDFormattedTextField.NO_VERIFIER);
		amount.setNegatived(false);
		amount.setPrecision(2);
		KDTDefaultCellEditor amountEditor = new KDTDefaultCellEditor(amount);
		
		this.tblMarket.getColumn("amount").setEditor(amountEditor);
		
		KDFormattedTextField rateamount = new KDFormattedTextField();
		rateamount.setDataType(KDFormattedTextField.BIGDECIMAL_TYPE);
		rateamount.setDataVerifierType(KDFormattedTextField.NO_VERIFIER);
		rateamount.setNegatived(false);
		rateamount.setPrecision(2);
		rateamount.setMaximumValue(new BigDecimal(100));
		rateamount.setMinimumValue(FDCHelper.ZERO);
		KDTDefaultCellEditor rateamountEditor = new KDTDefaultCellEditor(rateamount);
		
		this.tblMarket.getColumn("rate").setEditor(rateamountEditor);
		this.tblMarket.getColumn("rate").getStyleAttributes().setNumberFormat("#,##0.00;-#,##0.00");
		this.tblMarket.getColumn("rate").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.getAlignment("right"));
		
		
		ObjectValueRender render_scale = new ObjectValueRender();
		render_scale.setFormat(new IDataFormat() {
			public String format(Object o) {
				String str = o.toString();
				if (!FDCHelper.isEmpty(str)) {
					return str + "%";
				}
				return str;
			}
		});
		tblMarket.getColumn("rate").setRenderer(render_scale);
		
		String formatString = "yyyy-MM-dd";
		this.tblMarket.getColumn("date").getStyleAttributes().setNumberFormat(formatString);
		
		this.tblMarket.getColumn("date").setRequired(true);
		this.tblMarket.getColumn("rate").setRequired(true);
		this.tblMarket.getColumn("amount").setRequired(true);
	
		this.tblMarket.getColumn("amount").getStyleAttributes().setLocked(true);
		this.tblMarket.getColumn("date").getStyleAttributes().setLocked(true);
		this.tblMarket.getColumn("rate").getStyleAttributes().setLocked(true);
	}
	protected void prmtMpCostAccount_willShow(SelectorEvent e) throws Exception {
		Set id=new HashSet();
		if(prmtMarketProject.getValue()!=null){
			MarketProjectCollection mpcol=MarketProjectFactory.getRemoteInstance().getMarketProjectCollection("select amount,costEntry.*,costEntry.costAccount.* from where id='"+((MarketProjectInfo)prmtMarketProject.getValue()).getId()+"'");
			for(int i=0;i<mpcol.size();i++){
				for(int j=0;j<mpcol.get(i).getCostEntry().size();j++){
					if(MarketCostTypeEnum.NOTEXTCONTRACT.equals(mpcol.get(i).getCostEntry().get(j).getType()))
						id.add(mpcol.get(i).getCostEntry().get(j).getCostAccount().getId().toString());
				}
			}
		}
		
		EntityViewInfo view = new EntityViewInfo();		
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("id",id,CompareType.INCLUDE));
		view.setFilter(filter);
		prmtMpCostAccount.setEntityViewInfo(view);
		prmtMpCostAccount.getQueryAgent().resetRuntimeEntityView();
	}
	
	protected void prmtMarketProject_dataChanged(DataChangeEvent e)
			throws Exception {
		if(prmtMarketProject.getValue()!=null){
			MarketProjectInfo info=MarketProjectFactory.getRemoteInstance().getMarketProjectInfo(new ObjectUuidPK(((MarketProjectInfo)prmtMarketProject.getValue()).getId()));
			this.cbIsJT.setSelected(info.isIsJT());
		}else{
			this.cbIsJT.setSelected(false);
		}
	}
	protected void prmtMpCostAccount_dataChanged(DataChangeEvent e)throws Exception {
		this.tblMarket.removeRows();
		IRow row=this.tblMarket.addRow();
		row.getCell("date").setValue(new Date());
		row.getCell("rate").setValue(new BigDecimal(100));
		row.getCell("amount").setValue(this.txtamount.getBigDecimalValue());
	}

	protected void tblMarket_editStopped(KDTEditEvent e) throws Exception {
		 IRow r = this.tblMarket.getRow(e.getRowIndex());
		 int colIndex = e.getColIndex();
		 if(colIndex == this.tblMarket.getColumnIndex("rate")){
			 r.getCell("amount").setValue(FDCHelper.divide(FDCHelper.multiply(this.txtBcAmount.getBigDecimalValue(), r.getCell("rate").getValue()), new BigDecimal(100), 2, BigDecimal.ROUND_HALF_UP));
		 }
		 if(colIndex == this.tblMarket.getColumnIndex("date")){
			 if(r.getCell("date").getValue()!=null){
				 Calendar c = Calendar.getInstance();
				 int year = c.get(Calendar.YEAR);
				 
				 c = Calendar.getInstance();
				 c.setTime((Date) r.getCell("date").getValue());
				 int comyear = c.get(Calendar.YEAR);
				 if(year!=comyear){
					 FDCMsgBox.showError("�������²�������꣡");
					 r.getCell("date").setValue(null);
					 abort();
				 }
			 }
		 }
	}

	public void actionMALine_actionPerformed(ActionEvent e) throws Exception {
		IRow row = this.tblMarket.addRow();
		ContractWTMarketEntryInfo info = new ContractWTMarketEntryInfo();
		info.setId(BOSUuid.create(info.getBOSType()));
		
		row.setUserObject(info);
	}
	public void actionMRLine_actionPerformed(ActionEvent e) throws Exception {
		int activeRowIndex = tblMarket.getSelectManager().getActiveRowIndex();
		if(activeRowIndex<0){
			FDCMsgBox.showError("����ѡ��һ������");
			abort();
		}
		tblMarket.removeRow(activeRowIndex);
	}
	protected void initInvoiceEntryTable() throws BOSException {
		KDWorkButton btnAddRowinfo = new KDWorkButton();
		KDWorkButton btnInsertRowinfo = new KDWorkButton();
		KDWorkButton btnDeleteRowinfo = new KDWorkButton();
		KDWorkButton mkFpViewInfo = new KDWorkButton();
//
//		this.actionInvoiceALine.putValue("SmallIcon", EASResource.getIcon("imgTbtn_addline"));
//		btnAddRowinfo = (KDWorkButton) this.contInvoiceEntry.add(this.actionInvoiceALine);
//		btnAddRowinfo.setText("������");
//		btnAddRowinfo.setSize(new Dimension(140, 19));
//
//		this.actionInvoiceILine.putValue("SmallIcon", EASResource.getIcon("imgTbtn_insert"));
//		btnInsertRowinfo = (KDWorkButton) this.contInvoiceEntry.add(this.actionInvoiceILine);
//		btnInsertRowinfo.setText("������");
//		btnInsertRowinfo.setSize(new Dimension(140, 19));
//
//		this.actionInvoiceRLine.putValue("SmallIcon", EASResource.getIcon("imgTbtn_deleteline"));
//		btnDeleteRowinfo = (KDWorkButton) this.contInvoiceEntry.add(this.actionInvoiceRLine);
//		btnDeleteRowinfo.setText("ɾ����");
//		btnDeleteRowinfo.setSize(new Dimension(140, 19));
//
//		this.kdtInvoiceEntry.checkParsed();
//		
//		KDTextField textField = new KDTextField();
//		textField.setMaxLength(80);
//		KDTDefaultCellEditor txtEditor = new KDTDefaultCellEditor(textField);
//		this.kdtInvoiceEntry.getColumn("invoiceNumber").setEditor(txtEditor);
//		
//		KDDatePicker pk = new KDDatePicker();
//		KDTDefaultCellEditor dateEditor = new KDTDefaultCellEditor(pk);
//		this.kdtInvoiceEntry.getColumn("bizDate").setEditor(dateEditor);
		
		ObjectValueRender date_scale = new ObjectValueRender();
		date_scale.setFormat(new IDataFormat() {
			public String format(Object o) {
				Date str = (Date)o;
				return FDCDateHelper.DateToString(str);
			}
		});
		this.kdtInvoiceEntry.getColumn("bizDate").setRenderer(date_scale);
		this.kdtInvoiceEntry.getColumn("specialVATTaxRate").getStyleAttributes().setHided(true);
//		KDFormattedTextField amount = new KDFormattedTextField();
//		amount.setDataType(KDFormattedTextField.BIGDECIMAL_TYPE);
//		amount.setDataVerifierType(KDFormattedTextField.NO_VERIFIER);
//		amount.setNegatived(false);
//		amount.setPrecision(2);
//		KDTDefaultCellEditor amountEditor = new KDTDefaultCellEditor(amount);
//		this.kdtInvoiceEntry.getColumn("amount").setEditor(amountEditor);
//		this.kdtInvoiceEntry.getColumn("amount").getStyleAttributes().setNumberFormat("#,##0.00;-#,##0.00");
//		this.kdtInvoiceEntry.getColumn("amount").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.getAlignment("right"));
//		
//		KDFormattedTextField rateamount = new KDFormattedTextField();
//		rateamount.setDataType(KDFormattedTextField.BIGDECIMAL_TYPE);
//		rateamount.setDataVerifierType(KDFormattedTextField.NO_VERIFIER);
//		rateamount.setNegatived(false);
//		rateamount.setPrecision(2);
//		rateamount.setMaximumValue(new BigDecimal(100));
//		rateamount.setMinimumValue(FDCHelper.ZERO);
//		KDTDefaultCellEditor rateamountEditor = new KDTDefaultCellEditor(rateamount);
//		this.kdtInvoiceEntry.getColumn("rate").setEditor(rateamountEditor);
//		this.kdtInvoiceEntry.getColumn("rate").getStyleAttributes().setNumberFormat("#,##0.00;-#,##0.00");
//		this.kdtInvoiceEntry.getColumn("rate").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.getAlignment("right"));
//		
//		this.kdtInvoiceEntry.getColumn("totalAmount").setEditor(amountEditor);
//		this.kdtInvoiceEntry.getColumn("totalAmount").getStyleAttributes().setNumberFormat("#,##0.00;-#,##0.00");
//		this.kdtInvoiceEntry.getColumn("totalAmount").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.getAlignment("right"));
//		
//		ObjectValueRender render_scale = new ObjectValueRender();
//		render_scale.setFormat(new IDataFormat() {
//			public String format(Object o) {
//				String str = o.toString();
//				if (!FDCHelper.isEmpty(str)) {
//					return str + "%";
//				}
//				return str;
//			}
//		});
//		this.kdtInvoiceEntry.getColumn("rate").setRenderer(render_scale);
		
		
		this.kdtInvoiceEntry.checkParsed();
		this.kdtInvoiceEntry.setEditable(true);
		btnAddRowinfo = new KDWorkButton();
		btnDeleteRowinfo = new KDWorkButton();

		
		this.actionInvoiceALine.putValue("SmallIcon", EASResource.getIcon("imgTbtn_addline"));
		btnAddRowinfo = (KDWorkButton) this.contInvoiceEntry.add(this.actionInvoiceALine);
		btnAddRowinfo.setText("������");
		btnAddRowinfo.setSize(new Dimension(140, 19));

		this.actionInvoiceRLine.putValue("SmallIcon", EASResource.getIcon("imgTbtn_deleteline"));
		btnDeleteRowinfo = (KDWorkButton) this.contInvoiceEntry.add(this.actionInvoiceRLine);
		btnDeleteRowinfo.setText("ɾ����");
		btnDeleteRowinfo.setSize(new Dimension(140, 19));
		
		this.actionMKFP.putValue("SmallIcon", EASResource.getIcon("imgTbtn_addline"));
		mkFpViewInfo = (KDWorkButton) this.contInvoiceEntry.add(this.actionMKFP);
		mkFpViewInfo.setText("����鿴��Ʊ");
		mkFpViewInfo.setSize(new Dimension(140, 19));
		
		this.kdtInvoiceEntry.getColumn("invoiceNumber").getStyleAttributes().setLocked(true);
		this.kdtInvoiceEntry.getColumn("invoiceTypeDesc").getStyleAttributes().setLocked(true);
		this.kdtInvoiceEntry.getColumn("bizDate").getStyleAttributes().setLocked(true);
		this.kdtInvoiceEntry.getColumn("totalPriceAndTax").getStyleAttributes().setLocked(true);
		this.kdtInvoiceEntry.getColumn("specialVATTaxRate").getStyleAttributes().setLocked(true);
		this.kdtInvoiceEntry.getColumn("totalTaxAmount").getStyleAttributes().setLocked(true);
	}
	public void actionInvoiceALine_actionPerformed(ActionEvent e) throws Exception {
//		IRow row = this.kdtInvoiceEntry.addRow();
//		ContractWTInvoiceEntryInfo entry = new ContractWTInvoiceEntryInfo();
//		row.setUserObject(entry);
		
		UIContext uiContext = new UIContext(this);
		uiContext.put("table",this.kdtInvoiceEntry);
        IUIFactory uiFactory = UIFactory.createUIFactory(UIFactoryName.MODEL);
        IUIWindow uiWindow = uiFactory.create(MK_FPSelectUI.class.getName(), uiContext,null,OprtState.VIEW);
        uiWindow.show();
	}

//	public void actionInvoiceILine_actionPerformed(ActionEvent e) throws Exception {
//		IRow row = null;
//		if (this.kdtInvoiceEntry.getSelectManager().size() > 0) {
//			int top = this.kdtInvoiceEntry.getSelectManager().get().getTop();
//			if (isTableColumnSelected(this.kdtInvoiceEntry))
//				row = this.kdtInvoiceEntry.addRow();
//			else
//				row = this.kdtInvoiceEntry.addRow(top);
//		} else {
//			row = this.kdtInvoiceEntry.addRow();
//		}
//		ContractWTInvoiceEntryInfo entry = new ContractWTInvoiceEntryInfo();
//		row.setUserObject(entry);
//	}

	public void actionMKFP_actionPerformed(ActionEvent e) throws Exception {
		int activeRowIndex = kdtInvoiceEntry.getSelectManager().getActiveRowIndex();
		if(activeRowIndex<0){
			FDCMsgBox.showError("����ѡ��һ������");
			abort();
		}
		String invoiceNumber = (String)this.kdtInvoiceEntry.getRow(activeRowIndex).getCell("invoiceNumber").getValue();
//		String invoiceNumber = (String)this.editData.getInvoiceEntry().get(activeRowIndex).get("invoiceNumber");
		String link = ContractWithoutTextFactory.getRemoteInstance().getMKLink(invoiceNumber);
		if(link==null||link.equals("")){
			FDCMsgBox.showError("δ�ҵ���Ӧ��ƱͼƬ����ȷ�Ϸ�ƱͼƬ���ϴ�");
		}
		Runtime.getRuntime().exec("rundll32 url.dll,FileProtocolHandler "+link);
	}
	

	public void actionInvoiceRLine_actionPerformed(ActionEvent e) throws Exception {
//		if (this.kdtInvoiceEntry.getSelectManager().size() == 0 || isTableColumnSelected(this.kdtInvoiceEntry)) {
//			FDCMsgBox.showInfo(this, EASResource.getString("com.kingdee.eas.framework.FrameWorkResource.Msg_NoneEntry"));
//			return;
//		}
//		if (FDCMsgBox.isYes(FDCMsgBox.showConfirm2(this, EASResource.getString("com.kingdee.eas.framework.FrameWorkResource.Confirm_Delete")))) {
//			int top = this.kdtInvoiceEntry.getSelectManager().get().getBeginRow();
//			int bottom = this.kdtInvoiceEntry.getSelectManager().get().getEndRow();
//			for (int i = top; i <= bottom; i++) {
//				if (this.kdtInvoiceEntry.getRow(top) == null) {
//					FDCMsgBox.showInfo(this, EASResource.getString("com.kingdee.eas.framework.FrameWorkResource.Msg_NoneEntry"));
//					return;
//				}
//				this.kdtInvoiceEntry.removeRow(top);
//				setInvoiceAmt();
//			}
//		}
		int activeRowIndex = kdtInvoiceEntry.getSelectManager().getActiveRowIndex();
		if(activeRowIndex<0){
			FDCMsgBox.showError("����ѡ��һ������");
			abort();
		}
		kdtInvoiceEntry.removeRow(activeRowIndex);
	}
	protected void prmtLxNum_dataChanged(DataChangeEvent e) throws Exception {
		BankNumInfo info=(BankNumInfo) this.prmtLxNum.getValue();
		if(info!=null){
			this.txtBank.setText(info.getName());
		}else{
			this.txtBank.setText(null);
		}
	}
	public void actionWorkFlowG_actionPerformed(ActionEvent e) throws Exception {
    	String id = this.editData.getId().toString();
    	ContractWithoutTextCollection col=ContractWithoutTextFactory.getRemoteInstance().getContractWithoutTextCollection("select * from where id='"+id+"'");
    	if(col.size()>0&&col.get(0).getSourceFunction()!=null){
    		FDCSQLBuilder builder=new FDCSQLBuilder();
			builder.appendSql("select fviewurl from t_oa");
			IRowSet rs=builder.executeQuery();
			String url=null;
			while(rs.next()){
				url=rs.getString("fviewurl");
			}
			if(url!=null){
				String mtLoginNum = OaUtil.encrypt(SysContext.getSysContext().getCurrentUserInfo().getNumber());
				String s2 = "&MtFdLoinName=";
				StringBuffer stringBuffer = new StringBuffer();
	            String oaid = URLEncoder.encode(col.get(0).getSourceFunction());
	            String link = String.valueOf(stringBuffer.append(url).append(oaid).append(s2).append(mtLoginNum));
				Runtime.getRuntime().exec("rundll32 url.dll,FileProtocolHandler "+link);  
			}
    	}else{
    		super.actionWorkFlowG_actionPerformed(e);
    	}
	}
	public void actionAuditResult_actionPerformed(ActionEvent e)
	throws Exception {
		String id = this.editData.getId().toString();
		ContractWithoutTextCollection col=ContractWithoutTextFactory.getRemoteInstance().getContractWithoutTextCollection("select * from where id='"+id+"'");
    	if(col.size()>0&&col.get(0).getSourceFunction()!=null){
    		FDCSQLBuilder builder=new FDCSQLBuilder();
			builder.appendSql("select fviewurl from t_oa");
			IRowSet rs=builder.executeQuery();
			String url=null;
			while(rs.next()){
				url=rs.getString("fviewurl");
			}
			if(url!=null){
				String mtLoginNum = OaUtil.encrypt(SysContext.getSysContext().getCurrentUserInfo().getNumber());
				String s2 = "&MtFdLoinName=";
				StringBuffer stringBuffer = new StringBuffer();
	            String oaid = URLEncoder.encode(col.get(0).getSourceFunction());
	            String link = String.valueOf(stringBuffer.append(url).append(oaid).append(s2).append(mtLoginNum));
				Runtime.getRuntime().exec("rundll32 url.dll,FileProtocolHandler "+link);  
			}
    	}else{
    		super.actionAuditResult_actionPerformed(e);
    	}
	}
}