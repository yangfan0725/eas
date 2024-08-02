/**
 * output package name
 */
package com.kingdee.eas.fdc.contract.client;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.EventListener;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import javax.swing.Action;
import javax.swing.ActionMap;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.apache.log4j.Logger;
import org.apache.poi1.hwpf.extractor.WordExtractor;

import javax.swing.filechooser.FileFilter;

import bsh.This;

import com.jacob.activeX.ActiveXComponent;
import com.jacob.activeX.ActiveXDIY;
import com.jacob.com.Dispatch;
import com.jacob.com.Variant;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.ctrl.extendcontrols.BizDataFormat;
import com.kingdee.bos.ctrl.extendcontrols.IDataFormat;
import com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox;
import com.kingdee.bos.ctrl.kdf.servertable.KDTStyleConstants;
import com.kingdee.bos.ctrl.kdf.table.ICell;
import com.kingdee.bos.ctrl.kdf.table.IColumn;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTAction;
import com.kingdee.bos.ctrl.kdf.table.KDTDefaultCellEditor;
import com.kingdee.bos.ctrl.kdf.table.KDTEditHelper;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectManager;
import com.kingdee.bos.ctrl.kdf.table.KDTTransferAction;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.KDTableHelper;
import com.kingdee.bos.ctrl.kdf.table.event.BeforeActionEvent;
import com.kingdee.bos.ctrl.kdf.table.event.BeforeActionListener;
import com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent;
import com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent;
import com.kingdee.bos.ctrl.kdf.util.editor.ICellEditor;
import com.kingdee.bos.ctrl.kdf.util.render.ObjectValueRender;
import com.kingdee.bos.ctrl.kdf.util.style.Styles.HorizontalAlignment;
import com.kingdee.bos.ctrl.swing.KDComboBox;
import com.kingdee.bos.ctrl.swing.KDContainer;
import com.kingdee.bos.ctrl.swing.KDDatePicker;
import com.kingdee.bos.ctrl.swing.KDFileChooser;
import com.kingdee.bos.ctrl.swing.KDFormattedTextField;
import com.kingdee.bos.ctrl.swing.KDOptionPane;
import com.kingdee.bos.ctrl.swing.KDTextArea;
import com.kingdee.bos.ctrl.swing.KDTextField;
import com.kingdee.bos.ctrl.swing.KDWorkButton;
import com.kingdee.bos.ctrl.swing.StringUtils;
import com.kingdee.bos.ctrl.swing.event.CommitEvent;
import com.kingdee.bos.ctrl.swing.event.CommitListener;
import com.kingdee.bos.ctrl.swing.event.DataChangeEvent;
import com.kingdee.bos.ctrl.swing.event.DataChangeListener;
import com.kingdee.bos.ctrl.swing.event.SelectorEvent;
import com.kingdee.bos.ctrl.swing.event.SelectorListener;
import com.kingdee.bos.ctrl.swing.util.CtrlCommonConstant;
import com.kingdee.bos.dao.AbstractObjectValue;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.IObjectValue;
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
import com.kingdee.bos.metadata.entity.SorterItemCollection;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.IItemAction;
import com.kingdee.bos.ui.face.IUIFactory;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.UIException;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.bos.workflow.ProcessInstInfo;
import com.kingdee.bos.workflow.service.ormrpc.EnactmentServiceFactory;
import com.kingdee.bos.workflow.service.ormrpc.IEnactmentService;
import com.kingdee.eas.base.attachment.Attachment;
import com.kingdee.eas.base.attachment.AttachmentFactory;
import com.kingdee.eas.base.attachment.AttachmentFtpFacadeFactory;
import com.kingdee.eas.base.attachment.AttachmentInfo;
import com.kingdee.eas.base.attachment.BizobjectFacadeFactory;
import com.kingdee.eas.base.attachment.BoAttchAssoCollection;
import com.kingdee.eas.base.attachment.BoAttchAssoFactory;
import com.kingdee.eas.base.attachment.BoAttchAssoInfo;
import com.kingdee.eas.base.attachment.IAttachment;
import com.kingdee.eas.base.attachment.IBoAttchAsso;
import com.kingdee.eas.base.attachment.common.AttachmentClientManager;
import com.kingdee.eas.base.attachment.common.AttachmentManagerFactory;
import com.kingdee.eas.base.attachment.util.FileGetter;
import com.kingdee.eas.base.attachment.util.Resrcs;
import com.kingdee.eas.base.codingrule.CodingRuleException;
import com.kingdee.eas.base.codingrule.CodingRuleManagerFactory;
import com.kingdee.eas.base.codingrule.ICodingRuleManager;
import com.kingdee.eas.base.commonquery.BooleanEnum;
import com.kingdee.eas.base.param.IParamControl;
import com.kingdee.eas.base.param.ParamControlFactory;
import com.kingdee.eas.base.permission.PermissionFactory;
import com.kingdee.eas.base.permission.UserInfo;
import com.kingdee.eas.basedata.assistant.CurrencyInfo;
import com.kingdee.eas.basedata.assistant.ExchangeRateInfo;
import com.kingdee.eas.basedata.master.cssp.SupplierCompanyInfoCollection;
import com.kingdee.eas.basedata.master.cssp.SupplierCompanyInfoFactory;
import com.kingdee.eas.basedata.master.cssp.SupplierFactory;
import com.kingdee.eas.basedata.master.cssp.SupplierInfo;
import com.kingdee.eas.basedata.org.AdminOrgUnitFactory;
import com.kingdee.eas.basedata.org.AdminOrgUnitInfo;
import com.kingdee.eas.basedata.org.FullOrgUnitInfo;
import com.kingdee.eas.basedata.org.OrgConstants;
import com.kingdee.eas.basedata.org.PositionMemberCollection;
import com.kingdee.eas.basedata.org.PositionMemberFactory;
import com.kingdee.eas.basedata.person.PersonInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.cp.bc.BizCollUtil;
import com.kingdee.eas.cp.eip.fts.documents.ExtractorMsWord;
import com.kingdee.eas.cp.eip.fts.documents.I_ExtractionResult;
import com.kingdee.eas.fdc.aimcost.AimCostCollection;
import com.kingdee.eas.fdc.aimcost.AimCostFactory;
import com.kingdee.eas.fdc.aimcost.AimCostInfo;
import com.kingdee.eas.fdc.aimcost.DynamicCostMap;
import com.kingdee.eas.fdc.aimcost.FDCCostRptFacadeFactory;
import com.kingdee.eas.fdc.basedata.ContractCodingTypeCollection;
import com.kingdee.eas.fdc.basedata.ContractCodingTypeFactory;
import com.kingdee.eas.fdc.basedata.ContractCodingTypeInfo;
import com.kingdee.eas.fdc.basedata.ContractDetailDefCollection;
import com.kingdee.eas.fdc.basedata.ContractDetailDefFactory;
import com.kingdee.eas.fdc.basedata.ContractDetailDefInfo;
import com.kingdee.eas.fdc.basedata.ContractSecondTypeEnum;
import com.kingdee.eas.fdc.basedata.ContractSourceFactory;
import com.kingdee.eas.fdc.basedata.ContractSourceInfo;
import com.kingdee.eas.fdc.basedata.ContractThirdTypeEnum;
import com.kingdee.eas.fdc.basedata.ContractTypeCollection;
import com.kingdee.eas.fdc.basedata.ContractTypeFactory;
import com.kingdee.eas.fdc.basedata.ContractTypeInfo;
import com.kingdee.eas.fdc.basedata.ContractTypeOrgTypeEnum;
import com.kingdee.eas.fdc.basedata.CostAccountFactory;
import com.kingdee.eas.fdc.basedata.CostAccountInfo;
import com.kingdee.eas.fdc.basedata.CostAccountYJTypeEnum;
import com.kingdee.eas.fdc.basedata.CostSplitStateEnum;
import com.kingdee.eas.fdc.basedata.CurProjectFactory;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.fdc.basedata.DataTypeEnum;
import com.kingdee.eas.fdc.basedata.FDCBasedataException;
import com.kingdee.eas.fdc.basedata.FDCBillInfo;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCCommonServerHelper;
import com.kingdee.eas.fdc.basedata.FDCConstants;
import com.kingdee.eas.fdc.basedata.FDCDateHelper;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.basedata.IFDCBill;
import com.kingdee.eas.fdc.invite.InviteTypeFactory;
import com.kingdee.eas.fdc.basedata.LandDeveloperFactory;
import com.kingdee.eas.fdc.basedata.ProjectStatusInfo;
import com.kingdee.eas.fdc.basedata.SourceTypeEnum;
import com.kingdee.eas.fdc.basedata.TaxInfoEnum;
import com.kingdee.eas.fdc.basedata.client.ContractTypePromptSelector;
import com.kingdee.eas.fdc.basedata.client.FDCClientHelper;
import com.kingdee.eas.fdc.basedata.client.FDCClientUtils;
import com.kingdee.eas.fdc.basedata.client.FDCClientVerifyHelper;
import com.kingdee.eas.fdc.basedata.client.FDCColorConstants;
import com.kingdee.eas.fdc.basedata.client.FDCContractParamUI;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.basedata.client.FDCUIWeightWorker;
import com.kingdee.eas.fdc.basedata.client.IFDCWork;
import com.kingdee.eas.fdc.contract.BankNumFactory;
import com.kingdee.eas.fdc.contract.BankNumInfo;
import com.kingdee.eas.fdc.contract.ConNoCostSplitCollection;
import com.kingdee.eas.fdc.contract.ConNoCostSplitFactory;
import com.kingdee.eas.fdc.contract.ConSplitExecStateEnum;
import com.kingdee.eas.fdc.contract.ContractBailEntryCollection;
import com.kingdee.eas.fdc.contract.ContractBailEntryInfo;
import com.kingdee.eas.fdc.contract.ContractBailInfo;
import com.kingdee.eas.fdc.contract.ContractBillCollection;
import com.kingdee.eas.fdc.contract.ContractBillEntryCollection;
import com.kingdee.eas.fdc.contract.ContractBillEntryInfo;
import com.kingdee.eas.fdc.contract.ContractBillFactory;
import com.kingdee.eas.fdc.contract.ContractBillInfo;
import com.kingdee.eas.fdc.contract.ContractBillRateEntryInfo;
import com.kingdee.eas.fdc.contract.ContractContentCollection;
import com.kingdee.eas.fdc.contract.ContractContentFactory;
import com.kingdee.eas.fdc.contract.ContractContentInfo;
import com.kingdee.eas.fdc.contract.ContractCostSplitCollection;
import com.kingdee.eas.fdc.contract.ContractCostSplitEntryCollection;
import com.kingdee.eas.fdc.contract.ContractCostSplitEntryFactory;
import com.kingdee.eas.fdc.contract.ContractCostSplitEntryInfo;
import com.kingdee.eas.fdc.contract.ContractCostSplitFactory;
import com.kingdee.eas.fdc.contract.ContractEstimateChangeBillFactory;
import com.kingdee.eas.fdc.contract.ContractEstimateChangeTypeEnum;
import com.kingdee.eas.fdc.contract.ContractFacadeFactory;
import com.kingdee.eas.fdc.contract.ContractInvoiceEntryInfo;
import com.kingdee.eas.fdc.contract.ContractMarketEntryInfo;
import com.kingdee.eas.fdc.contract.ContractModelFactory;
import com.kingdee.eas.fdc.contract.ContractModelInfo;
import com.kingdee.eas.fdc.contract.ContractPCSplitBillEntryFactory;
import com.kingdee.eas.fdc.contract.ContractPayItemInfo;
import com.kingdee.eas.fdc.contract.ContractPropertyEnum;
import com.kingdee.eas.fdc.contract.ContractSettleTypeEnum;
import com.kingdee.eas.fdc.contract.ContractUtil;
import com.kingdee.eas.fdc.contract.ContractWFTypeInfo;
import com.kingdee.eas.fdc.contract.ContractWithoutTextCollection;
import com.kingdee.eas.fdc.contract.ContractWithoutTextFactory;
import com.kingdee.eas.fdc.contract.CostPropertyEnum;
import com.kingdee.eas.fdc.contract.FDCUtils;
import com.kingdee.eas.fdc.contract.ForWriteMarkHelper;
import com.kingdee.eas.fdc.contract.IContractBill;
import com.kingdee.eas.fdc.contract.JZTypeEnum;
import com.kingdee.eas.fdc.contract.MarketProjectCollection;
import com.kingdee.eas.fdc.contract.MarketProjectCostEntryCollection;
import com.kingdee.eas.fdc.contract.MarketProjectCostEntryFactory;
import com.kingdee.eas.fdc.contract.MarketProjectCostEntryInfo;
import com.kingdee.eas.fdc.contract.MarketProjectFactory;
import com.kingdee.eas.fdc.contract.MarketProjectInfo;
import com.kingdee.eas.fdc.contract.app.MarketCostTypeEnum;
import com.kingdee.eas.fdc.contract.app.OaUtil;
import com.kingdee.eas.fdc.contract.programming.ProgrammingContractCollection;
import com.kingdee.eas.fdc.contract.programming.ProgrammingContractFactory;
import com.kingdee.eas.fdc.contract.programming.ProgrammingContractInfo;
import com.kingdee.eas.fdc.contract.programming.client.ContractBillLinkProgContEditUI;
import com.kingdee.eas.fdc.contract.programming.client.ProgrammingContractEditUI;
import com.kingdee.eas.fdc.finance.PaymentSplitFactory;
import com.kingdee.eas.fdc.finance.client.ContractPayPlanEditUI;
import com.kingdee.eas.fdc.invite.DirectAccepterResultCollection;
import com.kingdee.eas.fdc.invite.DirectAccepterResultFactory;
import com.kingdee.eas.fdc.invite.DirectAccepterResultInfo;
import com.kingdee.eas.fdc.invite.InviteDocumentsCollection;
import com.kingdee.eas.fdc.invite.InviteDocumentsFactory;
import com.kingdee.eas.fdc.invite.InviteProjectCollection;
import com.kingdee.eas.fdc.invite.InviteProjectEntriesCollection;
import com.kingdee.eas.fdc.invite.InviteProjectEntriesFactory;
import com.kingdee.eas.fdc.invite.InviteProjectFactory;
import com.kingdee.eas.fdc.invite.InviteProjectInfo;
import com.kingdee.eas.fdc.invite.InviteTypeInfo;
import com.kingdee.eas.fdc.invite.TenderAccepterResultCollection;
import com.kingdee.eas.fdc.invite.TenderAccepterResultEntryFactory;
import com.kingdee.eas.fdc.invite.TenderAccepterResultEntryInfo;
import com.kingdee.eas.fdc.invite.TenderAccepterResultFactory;
import com.kingdee.eas.fdc.invite.TenderAccepterResultInfo;
import com.kingdee.eas.fdc.invite.client.DirectAccepterResultEditUI;
import com.kingdee.eas.fdc.invite.client.TenderAccepterResultEditUI;
import com.kingdee.eas.fdc.invite.supplier.SupplierAttachListEntryFactory;
import com.kingdee.eas.fdc.invite.supplier.SupplierStateEnum;
import com.kingdee.eas.fdc.invite.supplier.SupplierStockFactory;
import com.kingdee.eas.fdc.invite.supplier.SupplierStockInfo;
import com.kingdee.eas.fi.gl.GlUtils;
import com.kingdee.eas.fm.common.ContextHelperFactory;
import com.kingdee.eas.fm.common.IProgressMonitor;
import com.kingdee.eas.fm.common.IRunnableWithProgress;
import com.kingdee.eas.fm.common.client.ProgressDialog;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.eas.framework.CoreBillBaseCollection;
import com.kingdee.eas.framework.CoreBillBaseInfo;
import com.kingdee.eas.framework.batchHandler.RequestContext;
import com.kingdee.eas.framework.client.FrameWorkClientUtils;
import com.kingdee.eas.framework.client.workflow.DefaultWorkflowUIEnhancement;
import com.kingdee.eas.framework.client.workflow.IWorkflowUIEnhancement;
import com.kingdee.eas.framework.client.workflow.IWorkflowUISupport;
import com.kingdee.eas.ma.bg.client.BgBalanceViewUI;
import com.kingdee.eas.ma.budget.BgControlFacadeFactory;
import com.kingdee.eas.ma.budget.BgCtrlResultCollection;
import com.kingdee.eas.ma.budget.BgCtrlResultInfo;
import com.kingdee.eas.ma.budget.BgHelper;
import com.kingdee.eas.ma.budget.IBgControlFacade;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.app.ContextUtil;
import com.kingdee.eas.util.app.DbUtil;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.MsgBox;
import com.kingdee.jdbc.rowset.IRowSet;
import com.kingdee.util.DateTimeUtils;
import com.kingdee.util.UuidException;

/**
 * 
 * ����:��ͬ���ݱ༭����
 * @author liupd  date:2006-10-10 <p>
 * @version EAS5.1.3
 */
public class ContractBillEditUI extends AbstractContractBillEditUI implements IWorkflowUISupport {
	private KDComboBox isLonelyCalCombo = null;
	
	private static final String DETAIL_DEF_ID = "detailDef.id";

	private static final Logger logger = CoreUIObject
			.getLogger(ContractBillEditUI.class);

	//�������Ƿ����ѡ���������ŵ���Ա
	private boolean canSelectOtherOrgPerson = false;

	//��Ŀ��ͬǩԼ�ܽ�����Ŀ
	private String controlCost;

	//���Ʒ�ʽ
	private String controlType;

	//��ͬ����ǰ���в��
	public boolean splitBeforeAudit;
	
	//�Ƿ���ʾ����ͬ������Ŀ��
	private boolean isShowCharge = false;
	//���ز�ҵ��ϵͳ���Ĺ����Ƿ����������ʼ����۹���
	boolean isUseWriteMark=FDCUtils.getDefaultFDCParamByKey(null,null, FDCConstants.FDC_PARAM_WRITEMARK);
	boolean isNeed=false;
	
	//�����ͬ����ѡ���ύ״̬������ͬ
	private boolean isSupply = false;
	
	
	//add by david_yang PT043562 2011.03.29 �˵����Ƿ��и���
	private boolean isHasAttchment = false;
	
	/**
	 * output class constructor
	 */
	public ContractBillEditUI() throws Exception {
		super();
		//����onLoad������
		//		SwingUtilities.invokeLater(new Runnable() {
		//			public void run() {
		//				prmtcontractType.requestFocus(true);
		//			}
		//		});
		jbInit();
	}
	
	/**
	 * ��ͬ�ύ����ʱ�Ƿ������ƻ�������й��� 2010-08-09
	 * 
	 * @return
	 */
	private boolean isRelatedTask(){
		boolean isRealtedTask = false;
		String cuID = SysContext.getSysContext().getCurrentCostUnit().getId().toString();
		try {
			isRealtedTask = FDCUtils.getDefaultFDCParamByKey(null, cuID, FDCConstants.FDC_PARAM_RELATEDTASK);
		} catch (EASBizException e) {
			e.printStackTrace();
		} catch (BOSException e) {
			e.printStackTrace();
		}
		return isRealtedTask;
	}
	private void jbInit() {
		titleMain = this.getUITitle();
	}

	//�仯�¼�
	protected void controlDate_dataChanged(
			com.kingdee.bos.ctrl.swing.event.DataChangeEvent e,
			ControlDateChangeListener listener) throws Exception {
		if ("bookedDate".equals(listener.getShortCut())) {
			bookedDate_dataChanged(e);
		} else if ("grtAmount".equals(listener.getShortCut())) {
			setGrtAmt("txtGrtAmount", e);
		} else if ("grtRate".equals(listener.getShortCut())) {
			setGrtAmt("txtGrtRate", e);
		} else if ("amount".equals(listener.getShortCut())){
			setGrtAmt("txtGrtRate", e);
		}
	}

	//ҵ�����ڱ仯�¼�
	ControlDateChangeListener bookedDateChangeListener = new ControlDateChangeListener(
			"bookedDate");

	ControlDateChangeListener amountChangeListener = new ControlDateChangeListener(
		"amount");
	
	ControlDateChangeListener grtAmountChangeListener = new ControlDateChangeListener(
			"grtAmount");

	ControlDateChangeListener grtRateChangeListener = new ControlDateChangeListener(
			"grtRate");

	protected void attachListeners() {
		pkbookedDate.addDataChangeListener(bookedDateChangeListener);
		txtamount.addDataChangeListener(amountChangeListener);
		txtGrtAmount.addDataChangeListener(grtAmountChangeListener);
		txtGrtRate.addDataChangeListener(grtRateChangeListener);
		
		addDataChangeListener(comboCurrency);
		addDataChangeListener(contractPropert);
		addDataChangeListener(contractSource);
		addDataChangeListener(prmtModel);
		//������β��ż�����
		addDataChangeListener(prmtRespDept);
		addDataChangeListener(prmtNeedDept);
		//addDataChangeListener(comboCurrency);

		addDataChangeListener(prmtcontractType);
		addDataChangeListener(txtamount);
		addDataChangeListener(txtExRate);
		addDataChangeListener(txtStampTaxRate);
		addDataChangeListener(prmtTAEntry);
		addDataChangeListener(pkStartDate);
		addDataChangeListener(pkEndDate);
	}

	protected void detachListeners() {
		pkbookedDate.removeDataChangeListener(bookedDateChangeListener);
		txtamount.removeDataChangeListener(amountChangeListener);
		txtGrtAmount.removeDataChangeListener(grtAmountChangeListener);
		txtGrtRate.removeDataChangeListener(grtRateChangeListener);
		
		removeDataChangeListener(comboCurrency);
		removeDataChangeListener(contractPropert);
		removeDataChangeListener(contractSource);
		removeDataChangeListener(prmtModel);
		removeDataChangeListener(prmtcontractType);
		removeDataChangeListener(txtamount);
		removeDataChangeListener(txtGrtAmount);
		removeDataChangeListener(txtExRate);
		removeDataChangeListener(txtGrtRate);
		removeDataChangeListener(txtStampTaxRate);
		//�������β��ż�����
		removeDataChangeListener(prmtRespDept);
		removeDataChangeListener(prmtNeedDept);
		removeDataChangeListener(prmtTAEntry);
		removeDataChangeListener(pkStartDate);
		removeDataChangeListener(pkEndDate);
	}

	/** �Ƿ�ʹ�ò��Ƴɱ��Ľ��, �Ƿ񵥾ݼ��� = ��, �˱���ֵΪ fasle, �Ƿ񵥾ݼ��� = ��, �˱���ֵ = true, 
	 * �����ϸ��Ϣû���Ƿ񵥶�����, ��˱���ֵΪ false
	 */
	protected boolean isUseAmtWithoutCost = false;

	private boolean isLoad=false;
	public void loadFields() {
		isLoad=true;
		detachListeners();

		super.loadFields();
		tblBail.removeRows();
		//������Ķ������Ѿ����󶨺��˵Ķ������ڱ����ʱ���Ѿ���������ݿ⣬�������ݵ�ʱ��Ҳȡ�������ݣ��ɾ��ǲ������
		//������������������ˣ� ֻ���Լ�ά��ʹ����ֵ�ˣ���Ȼ�������Ѿ��󶨺õĵ��ݵķ�¼�ķ�¼�������ȥ  by Cassiel_peng  2008-8-27
		this.tblEconItem.getColumn("payCondition").getStyleAttributes().setWrapText(true);
		this.tblEconItem.getColumn("desc").getStyleAttributes().setWrapText(true);
		tblBail.getColumn("bailCondition").getStyleAttributes().setWrapText(true);
		tblBail.getColumn("desc").getStyleAttributes().setWrapText(true);
		if(this.editData.getBail()!=null&&this.editData.getBail().getEntry()!=null){
			for (int i = 0; i < this.editData.getBail().getEntry().size(); i++) {
				ContractBailEntryInfo entry = this.editData.getBail().getEntry().get(i);
				IRow row=tblBail.addRow();
				row.setUserObject(entry);
				row.getCell("bailAmount").setValue(FDCHelper.toBigDecimal(entry.getAmount(),2));
				row.getCell("bailDate").setValue(entry.getBailDate());
				row.getCell("bailRate").setValue(entry.getProp());
				row.getCell("bailCondition").setValue(entry.getBailConditon());
				row.getCell("desc").setValue(entry.getDesc());
			}
		}
		
		isUseAmtWithoutCost = editData.isIsAmtWithoutCost();
		loadDetailEntries();
          
		this.txtOverAmt.setValue(new Double(editData.getOverRate()));
		
		if (editData.getState() == FDCBillStateEnum.SUBMITTED) {
			actionSave.setEnabled(false);
		}
//		if(!FDCBillStateEnum.SAVED.equals(editData.getState())){
//			this.prmtpartB.setEnabled(false);
//		}
		//�ұ�ѡ��
		GlUtils.setSelectedItem(comboCurrency, editData.getCurrency());

		txtExRate.setValue(editData.getExRate());
		txtLocalAmount.setValue(editData.getAmount());
		if (this.editData.getCurrency() != null) {
			if (editData.getCurrency().getId().toString().equals(
					baseCurrency.getId().toString())) {
				// �Ǳ�λ��ʱ������ѡ����û�
				txtExRate.setValue(FDCConstants.ONE);
				txtExRate.setRequired(false);
				txtExRate.setEditable(false);
				txtExRate.setEnabled(false);
			}
		}

		if (editData != null && editData.getCurProject() != null) {

			//			String projId = editData.getCurProject().getId().toString();
			//			CurProjectInfo curProjectInfo = FDCClientUtils.getProjectInfoForDisp(projId);

			//���bug BT315446 ע�ʹ��� txtProj.setText(curProject.getDisplayName());
			//txtProj.setText(curProject.getDisplayName());
			txtProj.setText(editData.getCurProject().getDisplayName());

			FullOrgUnitInfo costOrg = this.orgUnitInfo;
			//FDCHelper.getCostCenter(editData.getCurProject(),null).castToFullOrgUnitInfo(); 
			//FDCClientUtils.getCostOrgByProj(projId);

			txtOrg.setText(editData.getCurProject().getFullOrgUnit().getName());
			editData.setOrgUnit(costOrg);
			editData.setCU(curProject.getCU());
		}

		//2009-1-20 ��loadFields����������������������ť״̬�ķ�������ͨ����һ����һ�����л�����ʱ����ȷ��ʾ��������������ť��
		setAuditButtonStatus(this.getOprtState());
		
		this.comboCoopLevel.setSelectedItem(editData.getCoopLevel());
		
		setCapticalAmount();
		
		//���غ�ͬ��������
		loadContractSettleType();
		
		try {
			loadContractModel();
		} catch (EASBizException e) {
			this.handleException(e);
		} catch (BOSException e) {
			this.handleException(e);
		}
		//���ظ���
		try {
			fillAttachmnetTable();
		} catch (EASBizException e) {
			handleException(e);
		} catch (BOSException e) {
			handleException(e);
		}
		
		try {
			loadInvite();
			
		} catch (BOSException e) {
			e.printStackTrace();
		}
		try {
			loadSupplyEntry();
		} catch (BOSException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		setProgAndAccountState((ContractTypeInfo) prmtcontractType.getValue(),(ContractPropertyEnum) contractPropert.getSelectedItem());
		
		isLoad=false;
		
		if(this.contractPropert.getSelectedItem()!=null){
			if (this.contractPropert.getSelectedItem() == ContractPropertyEnum.DIRECT) {
				this.txtamount.setEnabled(true);
			}else if (this.contractPropert.getSelectedItem() == ContractPropertyEnum.SUPPLY) {
			}else if (this.contractPropert.getSelectedItem() == ContractPropertyEnum.THREE_PARTY) {
				this.txtamount.setEnabled(true);
			}else if (this.contractPropert.getSelectedItem() == ContractPropertyEnum.STRATEGY) {
				this.txtamount.setEnabled(false);
			}
		}
		
		if(this.curProject!=null){
			try {
				this.curProject=CurProjectFactory.getRemoteInstance().getCurProjectInfo(new ObjectUuidPK(this.curProject.getId()));
			} catch (EASBizException e) {
				e.printStackTrace();
			} catch (BOSException e) {
				e.printStackTrace();
			}
			
			//����Ǽ��׼���
//			if(TaxInfoEnum.SIMPLE.equals(curProject.getTaxInfo())){
//				this.cbTaxerQua.setEnabled(true);
//				this.txtTaxerNum.setEnabled(true);
//				this.txtBank.setEnabled(true);
//				this.txtBankAccount.setEnabled(true);
//				
//				this.actionDetailALine.setEnabled(true);
//				this.actionDetailILine.setEnabled(true);
//				this.actionDetailRLine.setEnabled(true);
//			}
		}
		try {
			if(this.editData.getTaEntry()!=null){
				loadTenderBill(this.editData.getTaEntry().getParent().getId().toString());
			}
		} catch (EASBizException e) {
			e.printStackTrace();
		} catch (BOSException e) {
			e.printStackTrace();
		}
		fillDetailByPropert(this.editData.getContractPropert(),this.editData.getContractType());
//		if(this.editData.getContractType()!=null){
//			if(this.editData.getContractType().isIsTA()){
//				this.prmtTAEntry.setEnabled(true);
//				this.prmtTAEntry.setRequired(true);
//				this.prmtpartB.setEnabled(false);
//				if(ContractPropertyEnum.SUPPLY.equals(this.editData.getContractPropert())){
//					this.prmtTAEntry.setEnabled(false);
//					this.prmtTAEntry.setRequired(false);
//					this.prmtpartB.setEnabled(false);
//				}else{
////					this.prmtpartB.setEnabled(true);
//				}
//			}else{
//				this.prmtTAEntry.setEnabled(false);
//				this.prmtTAEntry.setRequired(false);
//				this.prmtTAEntry.setValue(null);
//				if(ContractPropertyEnum.SUPPLY.equals(this.editData.getContractPropert())){
//					this.prmtpartB.setEnabled(false);
//				}else{
//					if(FDCBillStateEnum.SAVED.equals(editData.getState())){
//						this.prmtpartB.setEnabled(true);
//					}else{
//						this.prmtpartB.setEnabled(false);
//					}
//				}
//			}
//			String paramValue="true";
//			try {
//				paramValue = ParamControlFactory.getRemoteInstance().getParamValue(new ObjectUuidPK(SysContext.getSysContext().getCurrentOrgUnit().getId()), "YF_MARKETPROJECTCONTRACT");
//			} catch (EASBizException e) {
//				e.printStackTrace();
//			} catch (BOSException e) {
//				e.printStackTrace();
//			}
//			
//			if(this.editData.getContractType().isIsMarket()&&"true".equals(paramValue)){
//				this.prmtMarketProject.setEnabled(true);
//				this.prmtMarketProject.setRequired(true);
//				this.prmtMpCostAccount.setEnabled(true);
//				this.prmtMpCostAccount.setRequired(true);
//				this.cbJzType.setRequired(true);
//				this.pkJzStartDate.setRequired(true);
//				this.pkJzEndDate.setRequired(true);
//				this.actionMALine.setEnabled(true);
//				this.actionMRLine.setEnabled(true);
//			}else{
//				this.prmtMarketProject.setEnabled(false);
//				this.prmtMarketProject.setRequired(false);
//				this.prmtMpCostAccount.setEnabled(false);
//				this.prmtMpCostAccount.setRequired(false);
//				this.cbJzType.setRequired(false);
//				this.pkJzStartDate.setRequired(false);
//				this.pkJzEndDate.setRequired(false);
//				this.actionMALine.setEnabled(false);
//				this.actionMRLine.setEnabled(false);
//			}
//		}else{
//			this.prmtTAEntry.setEnabled(false);
//			this.prmtTAEntry.setRequired(false);
//			this.prmtTAEntry.setValue(null);
//			if(ContractPropertyEnum.SUPPLY.equals(this.editData.getContractPropert())){
//				this.prmtTAEntry.setEnabled(false);
//				this.prmtTAEntry.setRequired(false);
//				this.prmtpartB.setEnabled(false);
//			}else{
//				if(FDCBillStateEnum.SAVED.equals(editData.getState())){
//					this.prmtpartB.setEnabled(true);
//				}else{
//					this.prmtpartB.setEnabled(false);
//				}
//			}
//			this.prmtMarketProject.setEnabled(false);
//			this.prmtMarketProject.setRequired(false);
//			this.prmtMpCostAccount.setEnabled(false);
//			this.prmtMpCostAccount.setEnabled(false);
//			this.actionMALine.setEnabled(false);
//			this.actionMRLine.setEnabled(false);
//		}
		if(this.editData.getTaEntry()!=null){
			try {
				String	name = TenderAccepterResultFactory.getRemoteInstance().getTenderAccepterResultInfo(new ObjectUuidPK(this.editData.getTaEntry().getParent().getId().toString())).getName();
				this.prmtTAEntry.setCommitFormat(name);		
		        this.prmtTAEntry.setDisplayFormat(name);		
		        this.prmtTAEntry.setEditFormat(name);	
		        this.prmtTAEntry.setText(name);
			} catch (EASBizException e) {
				e.printStackTrace();
			} catch (BOSException e) {
				e.printStackTrace();
			}
		}
		try {
			boolean isUseYz=false;
			CurProjectInfo project = CurProjectFactory.getRemoteInstance().getCurProjectInfo(new ObjectUuidPK(this.editData.getCurProject().getId()));
			if(project.isIsOA()){
				for(int i=0;i<tblDetail.getRowCount();i++){
					if(tblDetail.getRow(i).getCell(DETAIL_COL).getValue().toString().equals("�Ƿ�ʹ�õ�����")&&
							tblDetail.getRow(i).getCell(CONTENT_COL).getValue()!=null&&tblDetail.getRow(i).getCell(CONTENT_COL).getValue().toString().equals("��")){
						isUseYz=true;
					}
				}
			}
			if(this.getOprtState().equals(OprtState.VIEW)){
				this.actionYZALine.setEnabled(false);
				this.actionYZRLine.setEnabled(false);
			}else{
				if(isUseYz){
					this.actionYZALine.setEnabled(true);
					this.actionYZRLine.setEnabled(true);
				}else{
					this.actionYZALine.setEnabled(false);
					this.actionYZRLine.setEnabled(false);
				}
			}
		} catch (EASBizException e) {
			e.printStackTrace();
		} catch (BOSException e) {
			e.printStackTrace();
		}
		if(this.editData.getContractType()!=null&&this.editData.getContractType().isIsRelateReceive()){
			this.prmtContractBillReceive.setRequired(true);
			this.prmtContractBillReceive.setEnabled(true);
		}else{
			this.prmtContractBillReceive.setRequired(false);
			this.prmtContractBillReceive.setEnabled(false);
		}
		attachListeners();
	}

	protected void loadContractModel() throws EASBizException, BOSException {
		removeDataChangeListener(prmtModel);
		if(editData.getContractModel()!=null){
			prmtModel.setEnabled(false);
			ContractModelInfo info = ContractModelFactory.getRemoteInstance().getContractModelInfo(new ObjectUuidPK(editData.getContractModel()));
			this.prmtModel.setValue(info);
		}
		addDataChangeListener(prmtModel);
	}

	protected void loadContractSettleType() {
		ContractSettleTypeEnum  cst = this.editData.getContractSettleType();
		if(ContractSettleTypeEnum.AType.equals(cst)){
			this.btnAType.setSelected(true);
		} else if(ContractSettleTypeEnum.BType.equals(cst)){
			this.btnBType.setSelected(true);
		} else if(ContractSettleTypeEnum.CType.equals(cst)){
			this.BtnCType.setSelected(true);
		}
		removeDataChangeListener(prmtCreateOrg);
		this.prmtCreateOrg.setValue(this.editData.getCreateDept());
		addDataChangeListener(prmtCreateOrg);
	}

	/**
	 * ����ԭ�ҽ���д�ͱ��ҽ���д
	 * 
	 * @author owen_wen 2011-06-09
	 */
	public void setCapticalAmount() {
		BigDecimal amount = FDCHelper.toBigDecimal(txtamount.getBigDecimalValue());
		this.txtOrgAmtBig.setText(FDCClientHelper.getChineseFormat(amount, false));
		this.txtOrgAmtBig.setHorizontalAlignment(JTextField.RIGHT);

		BigDecimal localAmt = FDCHelper.toBigDecimal(txtLocalAmount.getBigDecimalValue());
		this.txtAmtBig.setText(FDCClientHelper.getChineseFormat(localAmt, false));
		this.txtAmtBig.setHorizontalAlignment(JTextField.RIGHT);
	}
	
	/*
	 * �鿴��ͬ�Ƿ������ܺ�Լ
	 * 
	 * @seecom.kingdee.eas.fdc.contract.client.AbstractContractBillEditUI#
	 * actionViewProgramCon_actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionViewProgramCon_actionPerformed(ActionEvent e) throws Exception {
		// TODO ��ͬ����
		if (this.editData.getProgrammingContract() == null) {
			MsgBox.showInfo(this, "�ú�ͬû�й�����ܺ�Լ!");
			this.abort();
		}

		UIContext uiContext = new UIContext(this);
		IUIWindow uiWindow = null;
		ContractBillInfo contractBillInfo = (ContractBillInfo) editData;
		ProgrammingContractInfo proContInfo = getProContInfo(contractBillInfo.getProgrammingContract().getId().toString());
		contractBillInfo.setProgrammingContract(proContInfo);
		uiContext.put("programmingContract", proContInfo);
		uiContext.put("programmingContractTemp", "programmingContractTemp");
		uiWindow = UIFactory.createUIFactory(UIFactoryName.MODEL).create(ProgrammingContractEditUI.class.getName(), uiContext, null, OprtState.VIEW);
		uiWindow.show();
	}

	/**
	 * ��ȡ�������Ĺ滮��Լ
	 * 
	 * @param id
	 * @return
	 * @throws EASBizException
	 * @throws BOSException
	 */
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

	/**
	 * output storeFields method
	 */
	public void storeFields() {
		//������Լ��֤�𼰷�������
		ContractBailInfo contractBailInfo=this.editData.getBail();
		if(contractBailInfo==null){
			contractBailInfo=new ContractBailInfo();
			contractBailInfo.setId(BOSUuid.create(contractBailInfo.getBOSType()));
			this.editData.setBail(contractBailInfo);
		}
		contractBailInfo.setAmount(FDCHelper.toBigDecimal(txtBailOriAmount.getBigDecimalValue(),2));
		contractBailInfo.setProp(FDCHelper.toBigDecimal(txtBailRate.getBigDecimalValue(),2));
		ContractBailEntryCollection coll=contractBailInfo.getEntry();
		coll.clear();
		for (int i = 0; i < this.tblBail.getRowCount(); i++) {
			IRow row=tblBail.getRow(i);
			ContractBailEntryInfo bailEntryInfo=(ContractBailEntryInfo)row.getUserObject();
			bailEntryInfo.setParent(contractBailInfo);
			//�˴���û��Ǵ���һ���з�¼�е���ʵ�û���û����������ֵ�����
			if(row.getCell("bailAmount").getValue()!=null){
				bailEntryInfo.setAmount(FDCHelper.toBigDecimal(row.getCell("bailAmount").getValue(),2));
			}
			if(row.getCell("bailRate").getValue()!=null){
				bailEntryInfo.setProp(FDCHelper.toBigDecimal(row.getCell("bailRate").getValue(),2));
			}
			if(row.getCell("bailDate").getValue()!=null){
				bailEntryInfo.setBailDate(row.getCell("bailDate").getValue()==null?DateTimeUtils.truncateDate(new Date()):(Date)row.getCell("bailDate").getValue());
			}
			bailEntryInfo.setBailConditon((String)row.getCell("bailCondition").getValue());
			bailEntryInfo.setDesc((String)row.getCell("desc").getValue());
			coll.add(bailEntryInfo);
		}

		
		editData.setSignDate(DateTimeUtils.truncateDate(editData.getSignDate()));
		editData.setIsAmtWithoutCost(isUseAmtWithoutCost);
		editData.setOverRate(Double.valueOf(this.txtOverAmt.getText()).doubleValue());
		super.storeFields();
		storeDetailEntries();
		storeContractSettleType();
	
	}
	private void storeContractSettleType() {
		if(this.btnAType.isSelected()){
			this.editData.setContractSettleType(ContractSettleTypeEnum.AType);
		} else if(this.btnBType.isSelected()){
			this.editData.setContractSettleType(ContractSettleTypeEnum.BType);
		} else if(this.BtnCType.isSelected()){
			this.editData.setContractSettleType(ContractSettleTypeEnum.CType);
		}
		this.editData.setCreateDept((AdminOrgUnitInfo)this.prmtCreateOrg.getValue());
	}

	protected void prmtMainContract_willShow(SelectorEvent e) throws Exception {
		EntityViewInfo view = null;		
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("isSubContract",Boolean.FALSE));
		filter.getFilterItems().add(new FilterItemInfo("state",FDCBillStateEnum.AUDITTED.getValue()));
		filter.getFilterItems().add(new FilterItemInfo("contractSourceId.id",ContractSourceInfo.COOP_VALUE));
		if (prmtpartB.getData() != null) 
			//���Ϲ���������ս������ͬ���Ӻ�ͬ���ҷ���ͬ��added by Owen_wen 2010-8-18
			filter.getFilterItems().add(new FilterItemInfo("partB.id", ((SupplierInfo)prmtpartB.getData()).getId().toString())); 		
		if(prmtMainContract.getQueryAgent().getEntityViewInfo() != null){
			view = prmtMainContract.getQueryAgent().getEntityViewInfo();
			view.setFilter(filter);
		}else{
			view = new EntityViewInfo();
			view.setFilter(filter);
		}
		prmtMainContract.setEntityViewInfo(view);
		prmtMainContract.getQueryAgent().resetRuntimeEntityView();
		
	}
	
	public void actionPrint_actionPerformed(ActionEvent e) throws Exception {
		ArrayList idList = new ArrayList();
		if (editData != null && !StringUtils.isEmpty(editData.getString("id"))) {
			idList.add(editData.getString("id"));
		}
		if (idList == null || idList.size() == 0 || getTDQueryPK() == null
				|| getTDFileName() == null) {
			MsgBox.showWarning(this, EASResource.getString(
					"com.kingdee.eas.fdc.basedata.client.FdcResource",
					"cantPrint"));
			return;
		}
		ContractBillEditDataProvider dataPvd = new ContractBillEditDataProvider(
				editData.getString("id"), getTDQueryPK());
		dataPvd.setBailId(editData.getBail().getId().toString());
		com.kingdee.bos.ctrl.report.forapp.kdnote.client.KDNoteHelper appHlp = new com.kingdee.bos.ctrl.report.forapp.kdnote.client.KDNoteHelper();
		appHlp.print(getTDFileName(), dataPvd, javax.swing.SwingUtilities
				.getWindowAncestor(this));
	}

	public void actionPrintPreview_actionPerformed(ActionEvent e)
			throws Exception {
		ArrayList idList = new ArrayList();
		if (editData != null && !StringUtils.isEmpty(editData.getString("id"))) {
			idList.add(editData.getString("id"));
		}
		if (idList == null || idList.size() == 0 || getTDQueryPK() == null
				|| getTDFileName() == null) {
			MsgBox.showWarning(this, EASResource.getString(
					"com.kingdee.eas.fdc.basedata.client.FdcResource",
					"cantPrint"));
			return;

		}
		ContractBillEditDataProvider dataPvd = new ContractBillEditDataProvider(
				editData.getString("id"), getTDQueryPK());
		dataPvd.setBailId(editData.getBail().getId().toString());
		com.kingdee.bos.ctrl.report.forapp.kdnote.client.KDNoteHelper appHlp = new com.kingdee.bos.ctrl.report.forapp.kdnote.client.KDNoteHelper();
		appHlp.printPreview(getTDFileName(), dataPvd, javax.swing.SwingUtilities
				.getWindowAncestor(this));
	}

	protected String getTDFileName() {
		return "/bim/fdc/contract/ContractBill";
	}

	protected IMetaDataPK getTDQueryPK() {
		return new MetaDataPK(
				"com.kingdee.eas.fdc.contract.app.ContractBillQueryForPrint");
	}

	/**
	 * output getEditUIName method
	 */
	protected String getEditUIName() {
		return com.kingdee.eas.fdc.contract.client.ContractBillEditUI.class
				.getName();
	}

	/**
	 * output getBizInterface method
	 */
	protected com.kingdee.eas.framework.ICoreBase getBizInterface()
			throws Exception {
		return com.kingdee.eas.fdc.contract.ContractBillFactory
				.getRemoteInstance();
	}

	/**
	 * 
	 * ��������ͬ��ϸ��ϢTable
	 * 
	 * @return
	 * @author:liupd ����ʱ�䣺2006-8-26
	 *               <p>
	 */
	private KDTable getDetailInfoTable() {
		return tblDetail;
	}

	/**
	 * output createNewDetailData method
	 */
	protected IObjectValue createNewDetailData(KDTable table) {
		if(table==this.tblEconItem){
			return new ContractPayItemInfo();
		}else if(table==this.tblBail){
			ContractBailEntryInfo contractBailEntryInfo = new ContractBailEntryInfo();
			contractBailEntryInfo.setId(BOSUuid.create(contractBailEntryInfo.getBOSType()));
			return contractBailEntryInfo;
		}else{
			return new ContractBillEntryInfo();
		}
	}

	/**
	 * output createNewData method
	 */
	protected com.kingdee.bos.dao.IObjectValue createNewData() {
		ContractBillInfo objectValue = new com.kingdee.eas.fdc.contract.ContractBillInfo();
		objectValue.setId(BOSUuid.create(objectValue.getBOSType()));
		objectValue.setCreator(SysContext.getSysContext().getCurrentUserInfo());
		objectValue.setState(FDCBillStateEnum.SAVED);
//		objectValue.setRespPerson(SysContext.getSysContext().getCurrentUserInfo().getPerson());
		objectValue.setNeedPerson(SysContext.getSysContext().getCurrentUserInfo().getPerson());
		PersonInfo person = SysContext.getSysContext().getCurrentUserInfo().getPerson();
//		objectValue.setRespPerson(person);
		AdminOrgUnitInfo adminInfo = null;
		if(person!=null){
			try {
				SelectorItemCollection personsel=new SelectorItemCollection();
				personsel.add("position.adminOrgUnit.*");
				if(person!=null){
					EntityViewInfo view = new EntityViewInfo();
		    		FilterInfo filter = new FilterInfo();
		    		filter.getFilterItems().add(new FilterItemInfo("person.id",person.getId()));
		    		filter.getFilterItems().add(new FilterItemInfo("isPrimary",Boolean.TRUE));
		    		view.setFilter(filter);
		    		view.setSelector(personsel);
					
					PositionMemberCollection pmcol=PositionMemberFactory.getRemoteInstance().getPositionMemberCollection(view);
					if(pmcol.size()>0){
						adminInfo=pmcol.get(0).getPosition().getAdminOrgUnit();
					}
				}
			} catch (Exception e1) {
				e1.printStackTrace();
			} 
		}
		
//		objectValue.setRespDept(adminInfo);
		objectValue.setNeedDept(adminInfo);
		objectValue.setCreateDept(adminInfo);
		objectValue.setCreateTime(new Timestamp(serverDate.getTime()));
		objectValue.setSignDate(serverDate);
//		objectValue.setProgrammingContract(item)

		objectValue.setSourceType(SourceTypeEnum.ADDNEW);

		// Ĭ��ֱ�Ӻ�ͬ
		objectValue.setContractPropert(ContractPropertyEnum.DIRECT);
		objectValue.setSplitState(CostSplitStateEnum.NOSPLIT);
		BOSUuid projId = (BOSUuid) getUIContext().get("projectId");
		BOSUuid typeId = (BOSUuid) getUIContext().get("contractTypeId");
		if (projId == null) {
			projId = editData.getCurProject().getId();
		}

		CurProjectInfo curProjectInfo = this.curProject;
		objectValue.setCurProject(curProjectInfo);
		if (curProjectInfo.getLandDeveloper() != null)
			objectValue.setLandDeveloper(curProjectInfo.getLandDeveloper());

		try {
//			Ĭ��ί��
//			objectValue.setContractSourceId(ContractSourceFactory
//					.getRemoteInstance().getContractSourceInfo(
//							new ObjectUuidPK(ContractSourceInfo.TRUST_VALUE)));
			//��ǰ��Ĭ��ί�У����ڸĳɳ��Զ�ѡ��ȡ�б��еĵ�һ�����Ϊ�հ�
			EntityViewInfo evi = new EntityViewInfo();
			FilterInfo filter = new FilterInfo();
			evi.setFilter(filter);
			SorterItemCollection sic = new SorterItemCollection();
			SorterItemInfo sorts = new SorterItemInfo();
			sorts.setPropertyName("number");
			sorts.setSortType(SortType.ASCEND);
			sic.add(sorts);	
			evi.setSorter(sic);
			filter.getFilterItems().add(new FilterItemInfo("isEnabled",Boolean.TRUE));
			
			objectValue.setContractSourceId((ContractSourceInfo)(ContractSourceFactory.getRemoteInstance().getCollection(evi).get(0)));
			
			if (typeId != null) {
				ContractTypeInfo contractTypeInfo = null;

				SelectorItemCollection selector = new SelectorItemCollection();
				selector.add("*");
				selector.add("dutyOrgUnit.id");
				selector.add("dutyOrgUnit.number");
				selector.add("dutyOrgUnit.name");
				selector.add("contractWFTypeEntry.contractWFType.*");
				contractTypeInfo = ContractTypeFactory
						.getRemoteInstance()
						.getContractTypeInfo(new ObjectUuidPK(typeId), selector);

				objectValue.setContractType(contractTypeInfo);
//				objectValue.setRespDept(contractTypeInfo.getDutyOrgUnit());
				objectValue.setIsCoseSplit(contractTypeInfo.isIsCost());
				objectValue.setPayScale(contractTypeInfo.getPayScale());

				objectValue.setStampTaxRate(contractTypeInfo.getStampTaxRate());
				
//				if(ContractTypeOrgTypeEnum.BIGRANGE.equals(contractTypeInfo.getOrgType())||ContractTypeOrgTypeEnum.SMALLRANGE.equals(contractTypeInfo.getOrgType())){
//					objectValue.setOrgType(contractTypeInfo.getOrgType());
//				}
				if(contractTypeInfo.getContractWFTypeEntry().size()==1){
					objectValue.setContractWFType(contractTypeInfo.getContractWFTypeEntry().get(0).getContractWFType());
				}
			}
		} catch (Exception e) {
			handUIException(e);
		}

		// ǩԼ����
		// objectValue.setSignDate(new Date());

		//�������
		objectValue.setCostProperty(CostPropertyEnum.COMP_COMFIRM);

		/*
		 * �����ʾ��������ֵ����ȱʡֵ5.00���ֹ��޸ģ������ڡ�Ԥ�������ó�ʼֵ����������ʾ��������ֵ����ȱʡֵ100.00���ֹ��޸ģ������ڡ�Ԥ�������ó�ʼֵ����������ʾ��������ֵ����ȱʡֵ85.00���ֹ��޸ģ������ڡ�Ԥ�������ó�ʼֵ����
		 */
		objectValue.setPayPercForWarn(new BigDecimal("85"));
		objectValue.setChgPercForWarn(new BigDecimal("0"));

		//δ����
		objectValue.setHasSettled(false);

		objectValue.setGrtRate(FDCHelper.ZERO);

		objectValue.setCurrency(baseCurrency);
		objectValue.setExRate(FDCHelper.ONE);
		objectValue.setAmount(FDCHelper.ZERO);
		objectValue.setOriginalAmount(FDCHelper.ZERO);
		objectValue.setPayScale(FDCHelper.ZERO);
		objectValue.setGrtAmount(FDCHelper.ZERO);

		objectValue.setCU(curProjectInfo.getCU());
		objectValue.setConSplitExecState(ConSplitExecStateEnum.COMMON);

		//�ڼ�
		objectValue.setBookedDate(bookedDate);
		objectValue.setPeriod(curPeriod);

		mainContractId = null;
		detailAmount = FDCHelper.ZERO;
		//����Ҫ���� ��Լ��֤�𼰷�������
		ContractBailInfo contractBail=new ContractBailInfo();
		contractBail.setId(BOSUuid.create(contractBail.getBOSType()));
		objectValue.setBail(contractBail);
		objectValue.setIsOpenContract(false);
		objectValue.setIsStardContract(false);
		return objectValue;
	}

	private void setInviteCtrlVisibleByContractSource(
			ContractSourceInfo contractSource) {
		if (contractSource == null || contractSource.getId() == null)
			return;
		String sourceId = contractSource.getId().toString();
		if (ContractSourceInfo.TRUST_VALUE.equals(sourceId)) {
			setInviteCtrlVisible(false);
		} else if (ContractSourceInfo.INVITE_VALUE.equals(sourceId)) {
			setInviteCtrlVisible(true);
			// �����
			contsecondPrice.setVisible(false);
			// �׼�
			contbasePrice.setVisible(false);

			//��ע
			contRemark.setVisible(false);

			//			ս�Ժ�������
			contCoopLevel.setVisible(false);
			// �Ƽ۷�ʽ
			contPriceType.setVisible(false);
			
			chkIsSubMainContract.setVisible(false);
			conMainContract.setVisible(false);
			conEffectiveEndDate.setVisible(false);
			conEffectiveStartDate.setVisible(false);
			conInformation.setVisible(false);
			kDScrollPane1.setVisible(false);

		} else if (ContractSourceInfo.DISCUSS_VALUE.equals(sourceId)) {
			setInviteCtrlVisible(false);
			// �б��
			contwinPrice.setVisible(true);
			// �б굥λ
			contwinUnit.setVisible(true);
			// �����
			contsecondPrice.setVisible(true);
			// �׼�
			contbasePrice.setVisible(true);

			//��ע
			contRemark.setVisible(true);

		} else if (ContractSourceInfo.COOP_VALUE.equals(sourceId)) {
			setInviteCtrlVisible(false);
			//ս�Ժ�������
			contCoopLevel.setVisible(true);
//			// �Ƽ۷�ʽ
//			contPriceType.setVisible(true);
			chkIsSubMainContract.setVisible(true);
			conMainContract.setVisible(true);
			conEffectiveEndDate.setVisible(true);
			conEffectiveStartDate.setVisible(true);
			conInformation.setVisible(true);
			kDScrollPane1.setVisible(true);
			this.prmtMainContract.setEnabled(false);
		} else {
			setInviteCtrlVisible(false);
		}

	}
	
	

	private void setInviteCtrlVisible(boolean visible) {
		Component[] components = pnlInviteInfo.getComponents();
		for (int i = 0; i < components.length; i++) {
			components[i].setVisible(visible);
		}
	}

	/**
	 * 
	 * ��������ͬ���ʱ仯����ͬ��ϸ��Ϣ���ֶα仯
	 * 
	 * @author:liupd
	 * @see com.kingdee.eas.fdc.contract.client.AbstractContractBillEditUI#contractPropert_itemStateChanged(java.awt.event.ItemEvent)
	 */
	ContractPropertyEnum contractPro = null;

	protected void contractPropert_itemStateChanged(ItemEvent e)
			throws Exception {

		if (e.getStateChange() == ItemEvent.SELECTED) {
			ContractPropertyEnum contractProp = (ContractPropertyEnum) e
					.getItem();
			contractPro = contractProp;
			
			contractPropertChanged(contractProp,(ContractTypeInfo) prmtcontractType.getValue());

		}
		setProgAndAccountState((ContractTypeInfo) prmtcontractType.getValue(),(ContractPropertyEnum) contractPropert.getSelectedItem());
		super.contractPropert_itemStateChanged(e);

	}

	private void contractPropertChanged(ContractPropertyEnum contractProp,ContractTypeInfo ct) {
		if (contractProp != null) {

			fillDetailByPropert(contractProp,ct);

			if (contractProp == ContractPropertyEnum.THREE_PARTY) {
				prmtpartC.setRequired(true);
			} else {
				prmtpartC.setRequired(false);
			}
		}
	}

	/*
	 * ������
	 */
	private static final String DETAIL_COL = ContractClientUtils.CON_DETAIL_DETAIL_COL;

	private static final String CONTENT_COL = ContractClientUtils.CON_DETIAL_CONTENT_COL;

	private static final String ROWKEY_COL = ContractClientUtils.CON_DETIAL_ROWKEY_COL;

	private static final String DESC_COL = ContractClientUtils.CON_DETIAL_DESC_COL;

	private static final String DATATYPE_COL = ContractClientUtils.CON_DETIAL_DATATYPE_COL;

	/*
	 * �б�ʶ
	 */
	private static final String NA_ROW = ContractClientUtils.MAIN_CONTRACT_NAME_ROW;

	private static final String NU_ROW = ContractClientUtils.MAIN_CONTRACT_NUMBER_ROW;

	private static final String AM_ROW = ContractClientUtils.AMOUNT_WITHOUT_COST_ROW;

	private static final String LO_ROW = ContractClientUtils.IS_LONELY_CAL_ROW;
	
	private static final String ES_ROW = ContractClientUtils.MAIN_CONTRACT_ESTIMATEAMOUNT_ROW;
	//Ԥ����ID
	private static final String ESID_ROW = ContractClientUtils.MAIN_CONTRACT_ESTIMATEID_ROW;

	/*
	 * ����ͬid 
	 */
	private String mainContractId = null;

	private BigDecimal detailAmount = FDCHelper.ZERO;

	class MyDataChangeListener implements DataChangeListener {

		public void dataChanged(DataChangeEvent eventObj) {
			String name = null;
			ContractBillInfo info = null;
			if (eventObj.getNewValue() != null) {
				info = (ContractBillInfo) eventObj.getNewValue();
				name = info.getName();
				mainContractId = info.getId().toString();
				if (isUseAmtWithoutCost&& detailAmount.compareTo(FDCHelper.ZERO) > 0) {
					try {
						MsgContractHasSplit();
					} catch (EASBizException e) {
						e.printStackTrace();
					} catch (BOSException e) {
						e.printStackTrace();
					}
				}
				comboCurrency.setEnabled(false);
				for (int i = 0; i < comboCurrency.getItemCount(); i++) {
					Object o = comboCurrency.getItemAt(i);
					if (o instanceof CurrencyInfo) {
						CurrencyInfo c = (CurrencyInfo) o;
						if (c.getId().equals(info.getCurrency().getId())) {
							comboCurrency.setSelectedIndex(i);
						}
					}
				}
				comboCurrency.setEditable(false);
				txtExRate.setValue(info.getExRate());
				txtExRate.setEditable(false);
				try {
					SelectorItemCollection sic = new SelectorItemCollection();
					sic.add("*");
					sic.add("programmingContract.*");
					info = ContractBillFactory.getRemoteInstance().getContractBillInfo(new ObjectUuidPK(info.getId().toString()), sic);
				} catch (Exception e) {
					logger.error(e);
					e.printStackTrace();
				}
				
				if (info.getProgrammingContract() != null) {//����ͬ�ǹ����˺�Լ�滮��
					//�����Լ��ܱ����ã��˺�Լ�������µ�Ŀ��ɱ����޶��汾�����ܱ��ٴ�ʹ�� 20110327 wangxin
//					if(isNotLastestVersion(info.getProgrammingContract())){
//						FDCMsgBox.showWarning("����ͬ��Ӧ��Լ�滮δ�������µ�Ŀ��ɱ����������޶���Լ�滮��");
//						name=null;
//						prmtFwContractTemp.setValue(null);
//						textFwContract.setText(null);
//						editData.setProgrammingContract(null);
//						getDetailInfoTable().getCell(getRowIndexByRowKey(NU_ROW),CONTENT_COL).setValue(null);
//						getDetailInfoTable().getCell(getRowIndexByRowKey(NA_ROW),CONTENT_COL).setValue(null);
//						return;
//					}
					if(isLonelyCalCombo.getSelectedItem().equals(BooleanEnum.FALSE)){// ����ǵ������㲹���ͬ
						prmtFwContractTemp.setValue(info.getProgrammingContract());
						textFwContract.setText(info.getProgrammingContract().getName());
						editData.setProgrammingContract(info.getProgrammingContract());
					}else{
						prmtFwContractTemp.setValue(info.getProgrammingContract());
						textFwContract.setText(info.getProgrammingContract().getName());
						editData.setProgrammingContract(info.getProgrammingContract());
						//��ʾԤ��������ͬ���µģ�
//						if(info!=null){
//							ContractEstimateChangeBillCollection estimateColl;
//							try {
//								EntityViewInfo view = new EntityViewInfo();
//								FilterInfo filter = new FilterInfo();
//								view.setFilter(filter);
//								filter.getFilterItems().add(new FilterItemInfo("contractbill.id",info.getId().toString()));
//								filter.getFilterItems().add(new FilterItemInfo("isLastest",Boolean.TRUE));
//								filter.getFilterItems().add(new FilterItemInfo("isUsed",Boolean.FALSE));
//								estimateColl = ContractEstimateChangeBillFactory.getRemoteInstance().getContractEstimateChangeBillCollection(view);
//								if(estimateColl.size()>0){
//									getDetailInfoTable().getCell(getRowIndexByRowKey(ES_ROW),CONTENT_COL).setValue(estimateColl.get(0).getEstimateAmount());
									//����Ԥ����ͬ�䶯��ID
//									getDetailInfoTable().getCell(getRowIndexByRowKey(ESID_ROW),CONTENT_COL).setValue(estimateColl.get(0).getId().toString());
//								}else {
//									getDetailInfoTable().getCell(getRowIndexByRowKey(NU_ROW),CONTENT_COL).setValue(null);
//									getDetailInfoTable().getCell(getRowIndexByRowKey(NA_ROW),CONTENT_COL).setValue(null);
//									getDetailInfoTable().getCell(getRowIndexByRowKey(ES_ROW),CONTENT_COL).setValue(null);
//									FDCMsgBox.showWarning("û����Ч��Ԥ����ͬ�䶯����ǩ�����ͬ��");
//								}
//							} catch (BOSException e) {
//								logger.error(e);
//								e.printStackTrace();
//							}
//							
//						}
					}
				}else{
					prmtFwContractTemp.setValue(null);
					textFwContract.setText(null);
					editData.setProgrammingContract(null);
				}
			} else {
				name = null;
				// �������ͬҲ��������Ŀ�ܺ�Լ
				prmtFwContractTemp.setValue(null);
				textFwContract.setText(null);
				editData.setProgrammingContract(null);
			}
			//			comboCurrency.setSelectedItem(info.getCurrency());
			//���ò����ͬ�ұ�ͻ��ʺ�����ͬһ��
			getDetailInfoTable().getCell(getRowIndexByRowKey(NA_ROW),CONTENT_COL).setValue(name);
		}

	}
	private boolean isNotLastestVersion(ProgrammingContractInfo pcInfo) {
		try{
			String progId = pcInfo.getId().toString();
			SelectorItemCollection sel= new SelectorItemCollection();
			sel.add("programming.aimCost.id");
			sel.add("programming.project.id");
			while(true){
				FilterInfo filter=new FilterInfo();
				EntityViewInfo view=new EntityViewInfo();
				filter.getFilterItems().add(new FilterItemInfo("srcId",progId));
				view.setFilter(filter);
				ProgrammingContractCollection pcol=ProgrammingContractFactory.getRemoteInstance().getProgrammingContractCollection(view);
				if(pcol.size()>0){
					progId=pcol.get(0).getId().toString();
				}else{
					break;
				}
			}
			ProgrammingContractInfo progammingInfo = ProgrammingContractFactory.getRemoteInstance().getProgrammingContractInfo(new ObjectUuidPK(progId),sel);
			AimCostInfo aimcost = progammingInfo.getProgramming().getAimCost();
			CurProjectInfo project = progammingInfo.getProgramming().getProject();
			EntityViewInfo entityView = new EntityViewInfo();
			FilterInfo filter = new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("state",FDCBillStateEnum.AUDITTED_VALUE));
			filter.getFilterItems().add(new FilterItemInfo("orgOrProId",project.getId().toString()));
			filter.getFilterItems().add(new FilterItemInfo("isLastVersion",Boolean.TRUE));
			entityView.setFilter(filter);
			AimCostCollection aimCostColl= AimCostFactory.getRemoteInstance().getAimCostCollection(entityView);
			if(aimCostColl.size()>0){
				if(!aimcost.getId().toString().equals(aimCostColl.get(0).getId().toString())){
					return true;
				}else{
					return false;
				}
			}
			return true;
		}catch (EASBizException e) {
			e.printStackTrace();
		} catch (BOSException e) {
			e.printStackTrace();
		}
		return true;
	}
	/**
	 * 
	 * �����������б�ʶ��ȡ��Index
	 * 
	 * @param rowKey
	 * @return
	 * @author:liupd ����ʱ�䣺2006-7-27
	 *               <p>
	 */
	private int getRowIndexByRowKey(String rowKey) {
		int rowIndex = 0;

		int rowCount = getDetailInfoTable().getRowCount();
		for (int i = 0; i < rowCount; i++) {
			String key = (String) getDetailInfoTable().getCell(i, ROWKEY_COL)
					.getValue();
			if (key != null && key.equals(rowKey)) {
				rowIndex = i;
				break;
			}
		}

		return rowIndex;
	}

	// �Ƿ�ո���ʾ�������Ӽ�¼
	boolean lastDispAddRows = false;

	/**
	 * 
	 * �����������ͬ����Ϊ������ͬ�������ͬ�����ͬ��ϸ��ϢҪ����ʾ4���ֶ�
	 * 
	 * @param contractProp
	 * @author:liupd ����ʱ�䣺2006-7-26
	 *               <p>
	 */
	private void fillDetailByPropert(ContractPropertyEnum contractProp,ContractTypeInfo ct) {
		if(ct!=null){
			try {
				ct=ContractTypeFactory.getRemoteInstance().getContractTypeInfo("select * from where id='"+ct.getId()+"'");
			} catch (EASBizException e) {
				e.printStackTrace();
			} catch (BOSException e) {
				e.printStackTrace();
			}
			if(ct.isIsTA()){
				this.prmtTAEntry.setEnabled(true);
				this.prmtTAEntry.setRequired(true);
				this.prmtpartB.setEnabled(false);
				if(ContractPropertyEnum.SUPPLY.equals(contractProp)){
					this.prmtTAEntry.setEnabled(false);
					this.prmtTAEntry.setRequired(false);
					this.prmtpartB.setEnabled(false);
				}else{
//					this.prmtpartB.setEnabled(true);
				}
			}else{
				this.prmtTAEntry.setEnabled(false);
				this.prmtTAEntry.setRequired(false);
				this.prmtTAEntry.setValue(null);
				if(ContractPropertyEnum.SUPPLY.equals(contractProp)){
					this.prmtpartB.setEnabled(false);
				}else{
					this.prmtpartB.setEnabled(true);
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
			if(ct.isIsMarket()&&"true".equals(paramValue)){
				this.prmtMarketProject.setEnabled(true);
				this.prmtMarketProject.setRequired(true);
				this.prmtMpCostAccount.setEnabled(true);
				this.prmtMpCostAccount.setRequired(true);
				this.cbJzType.setRequired(true);
				this.pkJzStartDate.setRequired(true);
				this.pkJzEndDate.setRequired(true);
				this.actionMALine.setEnabled(true);
				this.actionMRLine.setEnabled(true);
			}else{
				this.prmtMarketProject.setEnabled(false);
				this.prmtMarketProject.setRequired(false);
				this.prmtMpCostAccount.setEnabled(false);
				this.prmtMpCostAccount.setRequired(false);
				this.cbJzType.setRequired(false);
				this.pkJzStartDate.setRequired(false);
				this.pkJzEndDate.setRequired(false);
				this.actionMALine.setEnabled(false);
				this.actionMRLine.setEnabled(false);
				this.prmtMarketProject.setValue(null);
				this.prmtMpCostAccount.setValue(null);
				this.tblMarket.removeRows();
			}
		}else{
			this.prmtTAEntry.setEnabled(false);
			this.prmtTAEntry.setRequired(false);
			this.prmtTAEntry.setValue(null);
			if(ContractPropertyEnum.SUPPLY.equals(contractProp)){
				this.prmtTAEntry.setEnabled(false);
				this.prmtTAEntry.setRequired(false);
				this.prmtpartB.setEnabled(false);
			}else{
				this.prmtpartB.setEnabled(true);
			}
			this.prmtMarketProject.setEnabled(false);
			this.prmtMarketProject.setRequired(false);
			this.prmtMpCostAccount.setEnabled(false);
			this.prmtMpCostAccount.setEnabled(false);
			this.actionMALine.setEnabled(false);
			this.actionMRLine.setEnabled(false);
			this.prmtMarketProject.setValue(null);
			this.prmtMpCostAccount.setValue(null);
			this.tblMarket.removeRows();
		}
		if(contractProp == ContractPropertyEnum.SUPPLY){
			this.prmtlandDeveloper.setEnabled(false);
			
			this.prmtLxNum.setEnabled(false);
			this.txtBank.setEnabled(false);
			this.txtBankAccount.setEnabled(false);
			this.cbTaxerQua.setEnabled(false);
			this.txtTaxerNum.setEnabled(false);
		}else{
			this.prmtlandDeveloper.setEnabled(true);
			
			this.prmtLxNum.setEnabled(true);
			this.txtBank.setEnabled(true);
			this.txtBankAccount.setEnabled(true);
			this.cbTaxerQua.setEnabled(true);
			this.txtTaxerNum.setEnabled(true);
		}

		if (contractProp == ContractPropertyEnum.THREE_PARTY
				|| contractProp == ContractPropertyEnum.SUPPLY) {

			if (lastDispAddRows) {
				ICell cell = getDetailInfoTable().getRow(
						getRowIndexByRowKey(NU_ROW)).getCell(CONTENT_COL);
				
				ICell loCell = getDetailInfoTable().getRow(getRowIndexByRowKey(LO_ROW)).getCell(CONTENT_COL);
				if (contractProp == ContractPropertyEnum.SUPPLY) {
					cell.getStyleAttributes().setBackground(
							FDCHelper.KDTABLE_TOTAL_BG_COLOR);
					if (cell.getEditor() != null
							&& cell.getEditor().getComponent() instanceof KDBizPromptBox) {
						KDBizPromptBox box = (KDBizPromptBox) cell.getEditor()
								.getComponent();
						box.setRequired(true);
					}
					if(loCell.getEditor()!=null&&loCell.getEditor().getComponent() instanceof KDComboBox){
						loCell.setValue(BooleanEnum.FALSE);
						loCell.getStyleAttributes().setLocked(true);
						isLonelyChanged(BooleanEnum.FALSE);
					}
				} else {
					cell.getStyleAttributes().setBackground(Color.WHITE);
					if (cell.getEditor() != null
							&& cell.getEditor().getComponent() instanceof KDBizPromptBox) {
						KDBizPromptBox box = (KDBizPromptBox) cell.getEditor()
								.getComponent();
						box.setRequired(false);
					}
					if(loCell.getEditor()!=null&&loCell.getEditor().getComponent() instanceof KDComboBox){
						loCell.getStyleAttributes().setLocked(false);
					}
				}
				return;
			}

			/*
			 * ����
			 */
			String isLonelyCal = ContractClientUtils.getRes("isLonelyCal");
			String amountWithOutCost = ContractClientUtils
					.getRes("amountWithOutCost");
			String mainContractNumber = ContractClientUtils
					.getRes("mainContractNumber");
			String mainContractName = ContractClientUtils
					.getRes("mainContractName");

			/*
			 * �Ƿ񵥶�����
			 */
			IRow isLonelyCalRow = getDetailInfoTable().addRow();
			isLonelyCalRow.getCell(ROWKEY_COL).setValue(LO_ROW);
			isLonelyCalRow.getCell(DETAIL_COL).setValue(isLonelyCal);
			KDComboBox isLonelyCalCombo = getBooleanCombo();
			KDTDefaultCellEditor isLonelyCalComboEditor = new KDTDefaultCellEditor(isLonelyCalCombo);
			isLonelyCalRow.getCell(CONTENT_COL).setEditor(isLonelyCalComboEditor);
			isLonelyCalCombo.addItemListener(new IsLonelyChangeListener());
			isLonelyCalRow.getCell(DATATYPE_COL).setValue(DataTypeEnum.BOOL);
			if (contractProp == ContractPropertyEnum.SUPPLY) {
				isLonelyCalRow.getCell(CONTENT_COL).setValue(BooleanEnum.FALSE);
				isLonelyCalRow.getCell(CONTENT_COL).getStyleAttributes().setLocked(true);
			}else{
				isLonelyCalRow.getCell(CONTENT_COL).setValue(BooleanEnum.TRUE);
				isLonelyCalRow.getCell(CONTENT_COL).getStyleAttributes().setLocked(false);
			}
			/*
			 * ���Ƴɱ��Ľ��
			 */
			IRow amountWithOutCostRow = getDetailInfoTable().addRow();
			amountWithOutCostRow.getCell(ROWKEY_COL).setValue(AM_ROW);
			amountWithOutCostRow.getCell(DETAIL_COL)
					.setValue(amountWithOutCost);

			amountWithOutCostRow.getCell(CONTENT_COL).setEditor(
					FDCClientHelper.getNumberCellEditor());
			amountWithOutCostRow.getCell(CONTENT_COL).getStyleAttributes()
					.setHorizontalAlign(HorizontalAlignment.RIGHT);
			// ���Ϊ�ǣ��򡰲��Ƴɱ��Ľ�������¼��
			amountWithOutCostRow.getCell(CONTENT_COL).getStyleAttributes()
					.setLocked(true);

			amountWithOutCostRow.getCell(DATATYPE_COL).setValue(
					DataTypeEnum.NUMBER);

			/*
			 * ��Ӧ����ͬ����
			 */
			IRow mainContractNumberRow = getDetailInfoTable().addRow();
			mainContractNumberRow.getCell(ROWKEY_COL).setValue(NU_ROW);
			mainContractNumberRow.getCell(DETAIL_COL).setValue(mainContractNumber);
			KDBizPromptBox kDBizPromptBoxContract = getContractF7Editor();
			KDTDefaultCellEditor prmtContractEditor = new KDTDefaultCellEditor(kDBizPromptBoxContract);
			ICell cell = mainContractNumberRow.getCell(CONTENT_COL);
			cell.setEditor(prmtContractEditor);
			ObjectValueRender objectValueRender = getF7Render();
			cell.setRenderer(objectValueRender);

			MyDataChangeListener lis = new MyDataChangeListener();
			kDBizPromptBoxContract.addDataChangeListener(lis);

			mainContractNumberRow.getCell(DATATYPE_COL).setValue(
					DataTypeEnum.STRING);
			if (contractProp == ContractPropertyEnum.SUPPLY) {
				cell.getStyleAttributes().setBackground(
						FDCHelper.KDTABLE_TOTAL_BG_COLOR);
				kDBizPromptBoxContract.setRequired(true);
			} else {
				cell.getStyleAttributes().setBackground(Color.WHITE);
				kDBizPromptBoxContract.setRequired(false);
			}

			/*
			 * ��Ӧ����ͬ����
			 */
			IRow mainContractNameRow = getDetailInfoTable().addRow();
			mainContractNameRow.getCell(ROWKEY_COL).setValue(NA_ROW);
			mainContractNameRow.getCell(DETAIL_COL).setValue(mainContractName);
			mainContractNameRow.getCell(CONTENT_COL).getStyleAttributes()
					.setLocked(true);

			mainContractNameRow.getCell(DATATYPE_COL).setValue(
					DataTypeEnum.STRING);
			
			//Ԥ����ͬ�Ľ�� 20120330
			IRow estimateAmountRow = getDetailInfoTable().addRow();
			estimateAmountRow.getCell(ROWKEY_COL).setValue(ES_ROW);
			estimateAmountRow.getCell(DETAIL_COL).setValue("Ԥ�����");
			estimateAmountRow.getCell(DATATYPE_COL).setValue(DataTypeEnum.NUMBER);
			estimateAmountRow.getCell(CONTENT_COL).setEditor(FDCClientHelper.getNumberCellEditor());
			estimateAmountRow.getCell(CONTENT_COL).getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
			estimateAmountRow.getCell(CONTENT_COL).getStyleAttributes().setLocked(true);
			
			//Ԥ���䶯��ID 20120330
			IRow estimateID = getDetailInfoTable().addRow();
			estimateID.getCell(ROWKEY_COL).setValue(ESID_ROW);
			estimateID.getCell(DETAIL_COL).setValue("Ԥ�����ID");
			estimateID.getCell(DATATYPE_COL).setValue(DataTypeEnum.STRING);
			estimateID.getStyleAttributes().setHided(true);
			lastDispAddRows = true;
			
			if (contractProp == ContractPropertyEnum.SUPPLY) {
				isLonelyChanged(BooleanEnum.FALSE);
			}else{
				isLonelyChanged(BooleanEnum.TRUE);
			}
		} else {
			if (lastDispAddRows) {
				isLonelyChanged(BooleanEnum.TRUE);
				getDetailInfoTable().removeRow(getRowIndexByRowKey(LO_ROW));
				getDetailInfoTable().removeRow(getRowIndexByRowKey(AM_ROW));
				getDetailInfoTable().removeRow(getRowIndexByRowKey(NU_ROW));
				getDetailInfoTable().removeRow(getRowIndexByRowKey(NA_ROW));
				getDetailInfoTable().removeRow(getRowIndexByRowKey(ES_ROW));
				getDetailInfoTable().removeRow(getRowIndexByRowKey(ESID_ROW));
			}
			lastDispAddRows = false;
		}
		
		if(!isLoad){
			this.costProperty.removeAllItems();
			if (contractProp == ContractPropertyEnum.DIRECT) {
				this.txtamount.setEnabled(true);
				this.costProperty.addItem(CostPropertyEnum.COMP_COMFIRM);
				this.costProperty.addItem(CostPropertyEnum.TEMP_EVAL);
			}else if (contractProp == ContractPropertyEnum.SUPPLY) {
				this.costProperty.addItem(CostPropertyEnum.COMP_COMFIRM);
				this.costProperty.addItem(CostPropertyEnum.TEMP_EVAL);
				this.costProperty.addItem(CostPropertyEnum.BASE_CONFIRM);
			}else if (contractProp == ContractPropertyEnum.THREE_PARTY) {
				this.txtamount.setEnabled(true);
				this.costProperty.addItem(CostPropertyEnum.COMP_COMFIRM);
			}else if (contractProp == ContractPropertyEnum.STRATEGY) {
				this.costProperty.addItem(CostPropertyEnum.COMP_COMFIRM);
				
				this.txtamount.setValue(FDCHelper.ZERO);
				this.txtamount.setEnabled(false);
			}
		}
	}

	private void addKDTableLisener() {
		tblDetail.setBeforeAction(new BeforeActionListener() {
			public void beforeAction(BeforeActionEvent e) {
				comboCurrency.setEnabled(true);
				txtExRate.setEditable(true);
				KDTSelectManager sm = tblDetail.getSelectManager();
				if (sm.getActiveRowIndex() == getRowIndexByRowKey(LO_ROW)
						&& sm.getActiveColumnIndex() == 2) {
					if (BeforeActionEvent.ACTION_PASTE == e.getType()) {
						e.setCancel(true);
					}
				}
			}
		});
	}

	private ObjectValueRender getF7Render() {
		ObjectValueRender objectValueRender = new ObjectValueRender();
		BizDataFormat format = new BizDataFormat("$number$");
		objectValueRender.setFormat(format);
		return objectValueRender;
	}

	private KDBizPromptBox getContractF7Editor() {
		KDBizPromptBox kDBizPromptBoxContract = new KDBizPromptBox();
		kDBizPromptBoxContract.setQueryInfo("com.kingdee.eas.fdc.contract.app.ContractBillF7SimpleQuery");
		kDBizPromptBoxContract.setDisplayFormat("$number$");
		kDBizPromptBoxContract.setEditFormat("$number$");
		kDBizPromptBoxContract.setCommitFormat("$number$");
		kDBizPromptBoxContract.setEditable(true);
		FilterInfo filter = new FilterInfo();
		
		FilterItemCollection filterItems = filter.getFilterItems();
		//		filterItems.add(new FilterItemInfo("contractPropert", ContractPropertyEnum.DIRECT_VALUE));
		//		2009-01-13 yx ֧�ֲ����ͬ������ͬΪ������ͬ(�Ƹ����� R081201-122)
		HashSet set = new HashSet();
		set.add(ContractPropertyEnum.DIRECT_VALUE);
		set.add(ContractPropertyEnum.THREE_PARTY_VALUE);
		filterItems.add(new FilterItemInfo("contractPropert", set,
				CompareType.INCLUDE));
		filterItems.add(new FilterItemInfo("isAmtWithoutCost", Boolean.FALSE));
		filterItems.add(new FilterItemInfo("contractType.isEnabled",
				Boolean.TRUE));
		//		filterItems.add(new FilterItemInfo("state", FDCBillStateEnum.CANCEL_VALUE, CompareType.NOTEQUALS));
		//		filterItems.add(new FilterItemInfo("state", FDCBillStateEnum.SAVED_VALUE, CompareType.NOTEQUALS));
		//		filterItems.add(new FilterItemInfo("state", FDCBillStateEnum.SUBMITTED, CompareType.NOTEQUALS));
		//		filterItems.add(new FilterItemInfo("state", FDCBillStateEnum.INVALID, CompareType.NOTEQUALS));
		//1.4.3.2.2 ֻ�����Ѿ������ĺ�ͬ
//		filterItems.add(new FilterItemInfo("state",
//				FDCBillStateEnum.AUDITTED_VALUE));
		Set stateSet = new HashSet();
		stateSet.add(FDCBillStateEnum.AUDITTED_VALUE);
		if(isSupply){
			stateSet.add(FDCBillStateEnum.AUDITTING_VALUE);
			stateSet.add(FDCBillStateEnum.SUBMITTED_VALUE);
		}
		filterItems.add(new FilterItemInfo("state", stateSet, CompareType.INCLUDE));
		//2008-09-17 ���� �ѽ���ĺ�ͬ����Ҫ����
		filterItems.add(new FilterItemInfo("hasSettled", Boolean.FALSE));

		String projId = null;
		if (editData.getCurProject() != null
				&& editData.getCurProject().getId() != null) {
			projId = editData.getCurProject().getId().toString();
		} else if (getUIContext().get("projectId") != null) {
			projId = ((BOSUuid) getUIContext().get("projectId")).toString();
		}
		if (projId != null) {
			filterItems.add(new FilterItemInfo("curProject.id", projId
					.toString()));
		}
		if (editData.getId() != null) {
			filterItems.add(new FilterItemInfo("id", editData.getId()
					.toString(), CompareType.NOTEQUALS));
		}

		EntityViewInfo view = new EntityViewInfo();
		view.setFilter(filter);
		kDBizPromptBoxContract.setEntityViewInfo(view);
		return kDBizPromptBoxContract;
	}

	private KDComboBox getBooleanCombo() {
		isLonelyCalCombo = new KDComboBox();
		isLonelyCalCombo.addItems(BooleanEnum.getEnumList().toArray());
		return isLonelyCalCombo;
	}

	class IsLonelyChangeListener implements ItemListener {

		/*
		 * ? �Ƿ񵥶����㣺�����ͣ������б�ö��ֵ���ǡ��񡣱�¼�ȱʡΪ�ǡ����ɱ༭��
		 * ���Ϊ�ǣ��򡰲��Ƴɱ��Ľ�������¼�룬�ڡ���ͬ��¼�롣 ���Ϊ���򡰲��Ƴɱ��Ľ���Ҫ¼�룬����ͬ��������¼�롣
		 * ����ڡ���ͬ����¼�������ݣ����Ƿ񵥶����㡱�ָ�Ϊ����ϵͳ�Զ�������ͬ���������д�������Ƴɱ��Ľ����֮Ҳһ����
		 */
		public void itemStateChanged(ItemEvent e) {
			if (e.getStateChange() == ItemEvent.SELECTED) {
				BooleanEnum b = (BooleanEnum) e.getItem();
				isLonelyChanged(b);
			}

		}

	}

	/**
	 * �Ƿ񵥶����㷢���ı�
	 * @param b
	 */
	private void isLonelyChanged(BooleanEnum b) {
		ICell cell = getDetailInfoTable().getRow(getRowIndexByRowKey(AM_ROW)).getCell(CONTENT_COL);

		if (b == BooleanEnum.TRUE) {
			// ��������ܰ�ť�ָ�
			cell.getStyleAttributes().setLocked(true);
			txtamount.setEditable(true);
			txtGrtRate.setValue(FDCHelper.ZERO);
			txtGrtRate.setRequired(true);
			txtGrtRate.setEditable(true);
			if (cell.getValue() != null && cell.getValue() instanceof Number) {
				txtamount.setNumberValue((Number) cell.getValue());
				calLocalAmt();
				cell.setValue(null);
			}
			setAmountRequired(true);

			isUseAmtWithoutCost = false;
			// �������ͬ��ܺ�Լ
			prmtFwContractTemp.setValue(null);
			textFwContract.setText(null);
			editData.setProgrammingContract(null);
		} else {
			// ��������ܰ�ť�ָ�
			cell.getStyleAttributes().setLocked(false);
			txtamount.setEditable(false);
			//��Ϊ����������Ĳ����ͬ ���ޱ���������¼��
			if (contractPro == ContractPropertyEnum.SUPPLY) {
				txtGrtRate.setRequired(false);
				txtGrtRate.setValue(FDCHelper.ZERO);
				txtGrtRate.setEditable(false);
				txtGrtAmount.setRequired(false);
				txtGrtAmount.setEditable(false);
			}
			if (txtamount.getNumberValue() != null) {
				cell.setValue(txtamount.getNumberValue());
				try {
					MsgContractHasSplit();
				} catch (EASBizException e) {
					e.printStackTrace();
				} catch (BOSException e) {
					e.printStackTrace();
				}
			}
			txtamount.setValue(null);
			txtLocalAmount.setValue(null);
			txtStampTaxAmt.setValue(null);

			setAmountRequired(false);

			isUseAmtWithoutCost = true;
			calStampTaxAmt();
			// ��������ͬ��ܺ�Լ
			if (mainContractId != null && getDetailInfoTable().getCell(getRowIndexByRowKey(NA_ROW), CONTENT_COL).getValue() != null) {
				ContractBillInfo info = null;
				try {
					SelectorItemCollection sic = new SelectorItemCollection();
					sic.add("*");
					sic.add("programmingContract.*");
					info = ContractBillFactory.getRemoteInstance().getContractBillInfo(new ObjectUuidPK(mainContractId.toString()), sic);
				} catch (Exception e) {
					logger.error(e);
				}
				// ����ǵ������㲹���ͬ
				if (info.getProgrammingContract() != null) {
					prmtFwContractTemp.setValue(info.getProgrammingContract());
					textFwContract.setText(info.getProgrammingContract().getName());
					editData.setProgrammingContract(info.getProgrammingContract());
				}
			}
		}
	}

	private void setAmountRequired(boolean required) {
		txtamount.setRequired(required);
		txtLocalAmount.setRequired(required);
	}

	/**
	 * ��������ͬ�γɷ�ʽ�仯���б�/�����Ϣ�ؼ��仯
	 */
	protected void contractSource_dataChanged(
			com.kingdee.bos.ctrl.swing.event.DataChangeEvent e)
			throws Exception {
		Object newValue = e.getNewValue();
		setInviteCtrlVisibleByContractSource((ContractSourceInfo) newValue);
		super.contractSource_dataChanged(e);
	}

	/**
	 * output contractSource_willShow method
	 */
	protected void contractSource_willShow(
			com.kingdee.bos.ctrl.swing.event.SelectorEvent e) throws Exception {
	}

	// �����������
	protected void fetchInitParam() throws Exception {

		super.fetchInitParam();

		Map param = (Map) ActionCache.get("FDCBillEditUIHandler.orgParamItem");
		if (param == null) {
			param = FDCUtils.getDefaultFDCParam(null, orgUnitInfo.getId()
					.toString());
		}
		
		if (param.get(FDCConstants.FDC_PARAM_SELECTPERSON) != null) {
			canSelectOtherOrgPerson = Boolean.valueOf(
					param.get(FDCConstants.FDC_PARAM_SELECTPERSON).toString())
					.booleanValue();
		}
		if (param.get(FDCConstants.FDC_PARAM_OUTCOST) != null) {
			controlCost = param.get(FDCConstants.FDC_PARAM_OUTCOST).toString();
		}
		if (param.get(FDCConstants.FDC_PARAM_CONTROLTYPE) != null) {
			controlType = param.get(FDCConstants.FDC_PARAM_CONTROLTYPE)
					.toString();
		}

		//splitBeforeAudit
		if (param.get(FDCConstants.FDC_PARAM_SPLITBFAUDIT) != null) {
			splitBeforeAudit = Boolean.valueOf(
					param.get(FDCConstants.FDC_PARAM_SPLITBFAUDIT).toString())
					.booleanValue();
		}
		//�Ƿ���ʾ����ͬ������Ŀ��
		if(param.get(FDCConstants.FDC_PARAM_CHARGETYPE) != null){
			isShowCharge = Boolean.valueOf(param.get(FDCConstants.FDC_PARAM_CHARGETYPE).toString()).booleanValue();			
		}
		if(param.get(FDCConstants.FDC_PARAM_SELECTSUPPLY) != null){
			isSupply = Boolean.valueOf(param.get(FDCConstants.FDC_PARAM_SELECTSUPPLY).toString()).booleanValue();			
		}
	}
	protected void txtBailOriAmount_dataChanged(DataChangeEvent e)
			throws Exception {
		EventListener[] listeners = txtBailRate.getListeners(DataChangeListener.class);
		for(int i=0;i<listeners.length;i++){
			txtBailRate.removeDataChangeListener((DataChangeListener)listeners[i]);
		}
		try{
			BigDecimal bailRate= FDCHelper.ZERO;
			BigDecimal bailOriAmount= FDCHelper.toBigDecimal(txtBailOriAmount.getNumberValue(),2);
			BigDecimal contractAmount=FDCHelper.toBigDecimal(txtamount.getNumberValue(),2);
			if(contractAmount.compareTo(FDCHelper.ZERO)!=0){
				bailRate=bailOriAmount.multiply(FDCHelper.ONE_HUNDRED).divide(contractAmount, 2, BigDecimal.ROUND_HALF_UP);
				if(bailRate.compareTo(FDCHelper.ONE_HUNDRED)==1){
					MsgBox.showWarning("��Լ��֤���������100%");
					txtBailOriAmount.setValue(null);
					SysUtil.abort();
				}
				this.txtBailRate.setValue(bailRate);
			}
		}finally{
			for(int i=0;i<listeners.length;i++){
				txtBailRate.addDataChangeListener((DataChangeListener)listeners[i]);
			}
		}
		//�����Լ��֤�����ı�Ļ�����Ӧ�ķ�¼ҲӦ�÷����ı�
		for (int i = 0; i < this.tblBail.getRowCount(); i++) {
			if(this.tblBail.getRow(i).getCell("bailRate").getValue()!=null&&txtamount.getNumberValue()!=null){
				BigDecimal bailAmount=FDCHelper.divide(FDCHelper.multiply(this.tblBail.getRow(i).getCell("bailRate").getValue(),FDCHelper.toBigDecimal(txtBailOriAmount.getBigDecimalValue(),2)),FDCHelper.ONE_HUNDRED);
				this.tblBail.getRow(i).getCell("bailAmount").setValue(bailAmount);
			}
			if(this.tblBail.getRow(i).getCell("bailRate").getValue()==null&&txtamount.getNumberValue()!=null&&this.tblBail.getRow(i).getCell("bailAmount").getValue()!=null){
				//�������
				BigDecimal bailRate=FDCHelper.divide(FDCHelper.multiply(this.tblBail.getRow(i).getCell("bailAmount").getValue(), FDCHelper.ONE), FDCHelper.toBigDecimal(txtBailOriAmount.getNumberValue(),2));
				if(bailRate.compareTo(FDCHelper.ONE_HUNDRED)==1){
					MsgBox.showWarning("���ر�������100%");
					this.tblBail.getRow(i).getCell("bailRate").setValue(null);
					SysUtil.abort();
				}
				this.tblBail.getRow(i).getCell("bailRate").setValue(bailRate);
			}
		}
		
	}
	
	protected void txtBailRate_dataChanged(DataChangeEvent e) throws Exception {
		EventListener[] listeners = txtBailOriAmount.getListeners(DataChangeListener.class);
		for(int i=0;i<listeners.length;i++){
			txtBailOriAmount.removeDataChangeListener((DataChangeListener)listeners[i]);
		}
		try{
			BigDecimal bailOriAmount= FDCHelper.ZERO;
			BigDecimal bailRate= FDCHelper.toBigDecimal(txtBailRate.getNumberValue(),2);
			BigDecimal contractAmount=FDCHelper.toBigDecimal(txtamount.getNumberValue(),2);
			bailOriAmount=FDCHelper.toBigDecimal(FDCHelper.divide(FDCHelper.multiply(bailRate,contractAmount),FDCHelper.ONE_HUNDRED));
			this.txtBailOriAmount.setValue(bailOriAmount);
		
		}finally{
			for(int i=0;i<listeners.length;i++){
				txtBailOriAmount.addDataChangeListener((DataChangeListener)listeners[i]);
			}
		}
		for (int i = 0; i < this.tblBail.getRowCount(); i++) {
			if(this.tblBail.getRow(i).getCell("bailRate").getValue()!=null&&txtamount.getNumberValue()!=null){
				BigDecimal bailAmount=FDCHelper.divide(FDCHelper.multiply(this.tblBail.getRow(i).getCell("bailRate").getValue(),FDCHelper.toBigDecimal(txtBailOriAmount.getBigDecimalValue(),2)),FDCHelper.ONE_HUNDRED);
				this.tblBail.getRow(i).getCell("bailAmount").setValue(bailAmount);
			}
			if(this.tblBail.getRow(i).getCell("bailRate").getValue()==null&&txtamount.getNumberValue()!=null&&this.tblBail.getRow(i).getCell("bailAmount").getValue()!=null){
				//�������
				BigDecimal bailRate=FDCHelper.divide(FDCHelper.multiply(this.tblBail.getRow(i).getCell("bailAmount").getValue(), FDCHelper.ONE), FDCHelper.toBigDecimal(txtBailOriAmount.getNumberValue(),2));
				if(bailRate.compareTo(FDCHelper.ONE_HUNDRED)==1){
					MsgBox.showWarning("���ر�������100%");
					this.tblBail.getRow(i).getCell("bailRate").setValue(null);
					SysUtil.abort();
				}
				this.tblBail.getRow(i).getCell("bailRate").setValue(bailRate);
			}
		}
	}
	
	public void onShow() throws Exception {
		// TODO Auto-generated method stub
		super.onShow();
		this.contAttachmentNameList.setEnabled(true);
		//ΪcomboAttachmentNameList�ٴ������������Ȩ�ޣ�����ʹ���ڿ��ӿɱ༭�������Ҳ����ѡ�������б���е���  by Cassiel_peng
		this.comboAttachmentNameList.setAccessAuthority(CtrlCommonConstant.AUTHORITY_COMMON);
		this.comboAttachmentNameList.setEnabled(true);
		this.comboAttachmentNameList.setEditable(true);
		
		//����������������
		this.kDContainer1.setContainerType(KDContainer.DISEXTENDSIBLE_STYLE);
		this.contPayItem.setContainerType(KDContainer.DISEXTENDSIBLE_STYLE);
		this.contBailItem.setContainerType(KDContainer.DISEXTENDSIBLE_STYLE);
		
//		JButton btnAdd = this.kDContainer1.add(this.actionAddLine);
//		JButton btnRemove = this.kDContainer1.add(this.actionRemoveLine);
//		this.kDContainer1.setVisibleOfExpandButton(true);
//		this.actionAddLine.setVisible(true);
//		this.actionAddLine.setEnabled(true);
//		this.actionRemoveLine.setVisible(true);
//		this.actionRemoveLine.setEnabled(true);
//		btnAdd.setRequestFocusEnabled(false);
//		btnRemove.setRequestFocusEnabled(false);
//		btnAdd.setIcon(EASResource.getIcon("imgTbtn_addline"));
//		btnRemove.setIcon(EASResource.getIcon("imgTbtn_deleteline"));
		// ����������ʱ�޸��ֶ�
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				editAuditProgColumn(); // �ڸ�����ʵ�����·���
			}
		});
		contOrgAmtBig.setLocation(5, 5);
		
		tabPanel.remove(ecoItemPanel);
	}
	
	/*
	 * ����������ʱ�޸��ֶ�
	 */
	private void editAuditProgColumn() {
		// �ж��Ƿ��ڹ�����������
		Object isFromWorkflow = getUIContext().get("isFromWorkflow");
		// ����
		logger.error(isFromWorkflow + "************************");
		logger.error(getOprtState().equals(STATUS_FINDVIEW) + "");
		logger.error(actionSave.isVisible() + "");
		logger.error((editData.getState() == FDCBillStateEnum.SUBMITTED || editData.getState() == FDCBillStateEnum.AUDITTING) + "");
		if (isFromWorkflow != null && isFromWorkflow.toString().equals("true") && getOprtState().equals(STATUS_FINDVIEW) && actionSave.isVisible()
				&& (editData.getState() == FDCBillStateEnum.SUBMITTED || editData.getState() == FDCBillStateEnum.AUDITTING)) {

			// �����������������еĿռ�
			this.lockUIForViewStatus();

			// ���ȸ��ؼ������ʵ�Ȩ�ޣ�Ȼ�������Ƿ�ɱ༭�Ϳ����ˣ�����ʹ��unloack������������ʹ���õĿؼ�����edit��״̬
			this.prmtFwContractTemp.setAccessAuthority(CtrlCommonConstant.AUTHORITY_COMMON);
			this.prmtFwContractTemp.setEditable(true);
			this.actionSave.setEnabled(true);

		}
		if (isFromWorkflow != null && isFromWorkflow.toString().equals("true") && getOprtState().equals(STATUS_EDIT)) {
			actionRemove.setEnabled(false);
		}
	}
	
	protected void tblEconItem_editStopped(KDTEditEvent e) throws Exception {
		
		BigDecimal contractAmount=FDCHelper.toBigDecimal(this.txtamount.getNumberValue(),2);
		
		int colIndex=e.getColIndex();
		int rowIndex=e.getRowIndex();
		IColumn payAmountCol=this.tblEconItem.getColumn("payAmount");
		IColumn payRateCol=this.tblEconItem.getColumn("payRate");
		if(colIndex==payAmountCol.getColumnIndex()){
			BigDecimal cellPayRateValue=FDCHelper.ZERO;
			BigDecimal cellPayAmountValue=FDCHelper.toBigDecimal(e.getValue(),2);
			if(contractAmount.compareTo(FDCHelper.ZERO)!=0){//��������Ϊ0
				cellPayRateValue=cellPayAmountValue.multiply(FDCHelper.ONE_HUNDRED).divide(contractAmount, 2, BigDecimal.ROUND_HALF_UP);
				if(cellPayRateValue.compareTo(FDCHelper.ONE_HUNDRED)==1){
					MsgBox.showWarning("�����������100%");
					this.tblEconItem.getCell(e.getRowIndex(), e.getColIndex()).setValue(null);
					SysUtil.abort();
				}
				this.tblEconItem.getCell(rowIndex, "payRate").setValue(cellPayRateValue);
			}
		}
		if(colIndex==payRateCol.getColumnIndex()){
			BigDecimal cellPayAmountValue=FDCHelper.ZERO;
			BigDecimal cellPayRateValue=FDCHelper.toBigDecimal(e.getValue(),2);
			cellPayAmountValue=FDCHelper.toBigDecimal(FDCHelper.divide(FDCHelper.multiply(contractAmount,cellPayRateValue),FDCHelper.ONE_HUNDRED, 2,BigDecimal.ROUND_HALF_UP),2);
			this.tblEconItem.getCell(rowIndex, "payAmount").setValue(cellPayAmountValue);
		}
		if(colIndex==this.tblEconItem.getColumn("payType").getColumnIndex()){
			//��Ӹ�������F7
			KDBizPromptBox bizPayTypeBox = new KDBizPromptBox();
			bizPayTypeBox.setQueryInfo("com.kingdee.eas.fdc.basedata.app.F7PaymentTypeQuery");
			KDTDefaultCellEditor payTypeEditor=new KDTDefaultCellEditor(bizPayTypeBox);
			this.tblEconItem.getColumn("payType").setEditor(payTypeEditor);
		}
		if(colIndex==this.tblEconItem.getColumn("date").getColumnIndex()){
			KDDatePicker payDataPicker = new KDDatePicker();
			payDataPicker.setDatePattern(FDCHelper.KDTABLE_DATE_FMT);
			ICellEditor payDateEditor = new KDTDefaultCellEditor(payDataPicker);
			this.tblEconItem.getColumn("date").setEditor(payDateEditor);
		}
	}
	/**
	 * 2009-11-23���޸ģ����㷵�����ʱӦ������Լ��֤���*�������� ��Զ��
	 */
	protected void tblBail_editStopped(KDTEditEvent e) throws Exception {
		
//		BigDecimal contractAmount=FDCHelper.toBigDecimal(this.txtamount.getNumberValue(),2);
		BigDecimal bailOrgAmount=FDCHelper.toBigDecimal(this.txtBailOriAmount.getNumberValue(),2);
		
		int colIndex=e.getColIndex();
		int rowIndex=e.getRowIndex();
		IColumn bailAmountCol=this.tblBail.getColumn("bailAmount");
		IColumn bailRateCol=this.tblBail.getColumn("bailRate");
		if(colIndex==bailAmountCol.getColumnIndex()){
			BigDecimal cellBailRateValue=FDCHelper.ZERO;
			BigDecimal cellBailAmountValue=FDCHelper.toBigDecimal(e.getValue(),2);
			if(bailOrgAmount.compareTo(FDCHelper.ZERO)!=0){
				cellBailRateValue=cellBailAmountValue.multiply(FDCHelper.ONE_HUNDRED).divide(bailOrgAmount, 2, BigDecimal.ROUND_HALF_UP);
				if(cellBailRateValue.compareTo(FDCHelper.ONE_HUNDRED)==1){
					MsgBox.showWarning("������������100%");
					this.tblBail.getCell(e.getRowIndex(), e.getColIndex()).setValue(null);
					SysUtil.abort();
				}
				this.tblBail.getCell(rowIndex, "bailRate").setValue(cellBailRateValue);
			}
		}
		if(colIndex==bailRateCol.getColumnIndex()){
			BigDecimal cellBailAmountValue=FDCHelper.ZERO;
			BigDecimal cellBailRateValue=FDCHelper.toBigDecimal(e.getValue(),2);
			cellBailAmountValue=FDCHelper.divide(FDCHelper.multiply(cellBailRateValue, bailOrgAmount), FDCHelper.ONE_HUNDRED, 2, BigDecimal.ROUND_HALF_UP);
			this.tblBail.getCell(rowIndex, "bailAmount").setValue(cellBailAmountValue);
		}
		if(colIndex==this.tblBail.getColumn("bailDate").getColumnIndex()){
			KDDatePicker bailDatePicker1 = new KDDatePicker();
			bailDatePicker1.setDatePattern(FDCHelper.KDTABLE_DATE_FMT);
			ICellEditor bailDateEditor = new KDTDefaultCellEditor(bailDatePicker1);
			this.tblBail.getColumn("bailDate").setEditor(bailDateEditor);
		}
	}
	
	
	/**
	 * ����"��������"��"��Լ��֤�𼰷�������"���������ʽ����   by Cassiel_peng
	 */
	private void initEcoEntryTableStyle() {
		((KDTTransferAction) tblEconItem.getActionMap().get(KDTAction.PASTE)).setPasteMode(KDTEditHelper.VALUE);
		((KDTTransferAction) tblBail.getActionMap().get(KDTAction.PASTE)).setPasteMode(KDTEditHelper.VALUE);
		tblEconItem.getColumn("payAmount").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
		tblEconItem.getColumn("payRate").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
		tblEconItem.getColumn("date").getStyleAttributes().setNumberFormat(FDCHelper.KDTABLE_DATE_FMT);
		
		tblBail.getColumn("bailAmount").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
		tblBail.getColumn("bailRate").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
		tblBail.getColumn("bailDate").getStyleAttributes().setNumberFormat(FDCHelper.KDTABLE_DATE_FMT);
		txtBailOriAmount.setRemoveingZeroInDispaly(false);
		this.txtBailOriAmount.setRequestFocusEnabled(true);
		this.txtBailOriAmount.setDataType(KDFormattedTextField.BIGDECIMAL_TYPE);
		this.txtBailOriAmount.setPrecision(2);
		this.txtBailOriAmount.setNegatived(false);
		this.txtBailRate.setMaximumValue(FDCHelper.MAX_DECIMAL);
		this.txtBailRate.setMinimumValue(FDCHelper.ZERO);
		this.txtBailOriAmount.setHorizontalAlignment(JTextField.RIGHT);
//		this.txtBailRate.setPercentDisplay(true);
		this.txtBailRate.setRequestFocusEnabled(true);
		this.txtBailRate.setDataType(KDFormattedTextField.BIGDECIMAL_TYPE);
		this.txtBailRate.setPrecision(2);
		this.txtBailRate.setMaximumValue(FDCHelper.ONE_HUNDRED);
		this.txtBailRate.setMinimumValue(FDCHelper.ZERO);
		this.txtBailRate.setNegatived(false);
		this.txtBailRate.setHorizontalAlignment(JTextField.RIGHT);
		this.txtBailRate.setRemoveingZeroInDispaly(false);
	}
	public void onLoad() throws Exception {
		
		this.tblInvite.checkParsed();
		FDCHelper.formatTableDate(this.tblInvite, "createDate");
		FDCHelper.formatTableDate(this.tblInvite, "auditDate");
		this.tblInvite.getColumn("respDept").getStyleAttributes().setHided(true);
		
//		BOSUuid.create((new ParamInfo ()).getBOSType());
		//2008-12-30 ��ʱ�����¹��� ���Ʒ�¼
		this.actionCopyLine.setVisible(false);
		this.actionCopyLine.setEnabled(false);
	    this.txtOverAmt.addDataChangeListener(new DataChangeListener(){
			public void dataChanged(DataChangeEvent eventObj) {
				Object old  = eventObj.getOldValue();
				if(old == null){
					old = Double.valueOf("0.0");
				}
				Object newValue = eventObj.getNewValue();
				if(newValue != null ){
				if( Double.valueOf(newValue.toString()).doubleValue() < 0 || Double.valueOf(newValue.toString()).doubleValue() > 999 ){
				   txtOverAmt.setValue(old,false);
				}}
			}
		});			
	    
	    // ��ѡ�Ƿ�ս���Ӻ�ͬʱ�������ͬû���ҷ�������ʾ
	    this.chkIsSubMainContract.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				if(chkIsSubMainContract.isSelected() && prmtpartB.getData() == null){
					FDCMsgBox.showWarning(EASResource.getString("com.kingdee.eas.fdc.contract.client.ContractResource", "plsSelectPartBFirst"));
					chkIsSubMainContract.setSelected(false);
					prmtpartB.requestFocus(true);
				}
			}
	    });
	    
	    this.chkIsSubMainContract.addChangeListener(new ChangeListener(){
			public void stateChanged(ChangeEvent e) {
				if(chkIsSubMainContract.isSelected()){
					if (prmtpartB.getData() != null) {
						prmtMainContract.setEnabled(true);
						prmtMainContract.setRequired(true);
					}
				}else{
					prmtMainContract.setEnabled(false);
					prmtMainContract.setRequired(false);
					prmtMainContract.setDataNoNotify(null);
				}
			}});
	    
	    this.prmtMainContract.addDataChangeListener(new DataChangeListener(){

			public void dataChanged(DataChangeEvent eventObj) {
				if(eventObj.getNewValue() != null){
					ContractBillInfo info = (ContractBillInfo) eventObj.getNewValue();
					if(info.getEffectiveEndDate() != null)
					kdpEffectiveEndDate.setValue(info.getEffectiveEndDate());
					if(info.getEffectiveStartDate() != null )
					kdpEffectStartDate.setValue(info.getEffectiveStartDate());
					if(info.getCoopLevel() != null){
						comboCoopLevel.setSelectedItem(info.getCoopLevel());
					}
					Date now = null;
					try {
						 now = FDCCommonServerHelper.getServerTime();
					} catch (BOSException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} 
					if(now != null && info.getEffectiveEndDate() != null && info.getEffectiveStartDate() != null){
						if(now.after(info.getEffectiveEndDate()) || now.before(info.getEffectiveStartDate())){
							int n = MsgBox.showConfirm2New(null, "��ǰս�Ժ�ͬ�ѹ��ڣ�����ʹ�øú�ͬ��");
							if(JOptionPane.NO_OPTION == n){
								prmtMainContract.setDataNoNotify(null);
								abort();
							}
						}
					}
					
				}
			}});
		
		//ֻ��ʾ�����õĺ�ͬ������Ŀ
		EntityViewInfo viewInfo = this.prmtCharge.getEntityViewInfo();
		if (viewInfo == null) {
			viewInfo = new EntityViewInfo();
		}
		FilterInfo filterInfo = new FilterInfo();
		filterInfo.getFilterItems().add(
				new FilterItemInfo("isEnabled", new Integer(1)));
		viewInfo.setFilter(filterInfo);
		this.prmtCharge.setEntityViewInfo(viewInfo);
		tblCost.getStyleAttributes().setLocked(true);
		tblCost.checkParsed();
		//����
		txtamount.setHorizontalAlignment(JTextField.RIGHT);
		//������Ϊ��
		txtamount.setNegatived(false);
		txtLocalAmount.setHorizontalAlignment(JTextField.RIGHT);
		txtGrtAmount.setHorizontalAlignment(JTextField.RIGHT);
		txtGrtAmount.setPrecision(6);
		txtGrtRate.setEditable(true);
		txtExRate.setDataType(KDFormattedTextField.BIGDECIMAL_TYPE);
		txtExRate.setPrecision(6);
		EntityViewInfo invView = new EntityViewInfo();
		FilterInfo invFilter = new FilterInfo();
		invView.setFilter(invFilter);
		invFilter.getFilterItems().add(
				new FilterItemInfo("isEnabled", Boolean.TRUE));
		prmtinviteType.setEntityViewInfo(invView);

		//init currency
		removeDataChangeListener(comboCurrency);
		FDCClientHelper.initComboCurrency(comboCurrency, true);
		
		txtcontractName.setMaxLength(200);//�˾���������super֮ǰ,�����ڼ��ر���õ����ݵ�ʱ��ֻ���ȡǰ80���ַ���ʾ by Cassiel_peng
		super.onLoad();
		setMaxMinValueForCtrl();
		
		initEcoEntryTableStyle();
		sortTabbedPanel();

		//�ڴ���Ҫ�������ע��
		//		fillCostPanel();

		setInviteCtrlVisible(false);
		EntityViewInfo viewContractSource = new EntityViewInfo();
		FilterInfo filterSource = new FilterInfo();
		filterSource.getFilterItems().add(new FilterItemInfo("isEnabled", Boolean.TRUE));
		viewContractSource.setFilter(filterSource);
		this.contractSource.setEntityViewInfo(viewContractSource);
		
		updateButtonStatus();

		// ��ʼ����ͬ����F7
		prmtcontractType.setSelector(new ContractTypePromptSelector(this));

		//���ڼ׷��������ϼ���ΪS4������filterʱ���Զ�����CU���� Ϊȥ��Ĭ�ϼ���filter���޸ĵ�
		//		EntityViewInfo view = new EntityViewInfo();
		//		FilterInfo filter = new FilterInfo();
		//		filter.getFilterItems().add(new FilterItemInfo("isEnabled", Boolean.TRUE));
		//		HashSet set=new HashSet();
		//		set.add(OrgConstants.SYS_CU_ID);
		String cuId = editData.getCU().getId().toString();
		//		//
		//		set.add(cuId);
		//		filter.getFilterItems().add(new FilterItemInfo("CU.id",set,CompareType.INCLUDE));
		//
		//		view.setFilter(filter);
		//		prmtlandDeveloper.setEntityViewInfo(view);
		//ȥ��Ĭ�ϼ���filter
		prmtlandDeveloper.addSelectorListener(new SelectorListener() {
			public void willShow(SelectorEvent e) {
				KDBizPromptBox f7 = (KDBizPromptBox) e.getSource();
				f7.getQueryAgent().setDefaultFilterInfo(null);
				f7.getQueryAgent().setHasCUDefaultFilter(false);
				f7.getQueryAgent().resetRuntimeEntityView();
				EntityViewInfo view = new EntityViewInfo();
				FilterInfo filter = new FilterInfo();
				filter.getFilterItems().add(
						new FilterItemInfo("isEnabled", Boolean.TRUE));
				HashSet set = new HashSet();
				set.add(OrgConstants.SYS_CU_ID);
				String cuId = editData.getCU().getId().toString();
				set.add(cuId);
				filter.getFilterItems().add(
						new FilterItemInfo("CU.id", set, CompareType.INCLUDE));
				view.setFilter(filter);
				f7.setEntityViewInfo(view);
			}
		});
		prmtlandDeveloper.addCommitListener(new CommitListener() {
			public void willCommit(CommitEvent e) {
				KDBizPromptBox f7 = (KDBizPromptBox) e.getSource();
				f7.getQueryAgent().setDefaultFilterInfo(null);
				f7.getQueryAgent().setHasCUDefaultFilter(false);
				f7.getQueryAgent().resetRuntimeEntityView();
				EntityViewInfo view = new EntityViewInfo();
				FilterInfo filter = new FilterInfo();
				filter.getFilterItems().add(
						new FilterItemInfo("isEnabled", Boolean.TRUE));
				HashSet set = new HashSet();
				set.add(OrgConstants.SYS_CU_ID);
				String cuId = editData.getCU().getId().toString();
				set.add(cuId);
				filter.getFilterItems().add(
						new FilterItemInfo("CU.id", set, CompareType.INCLUDE));
				view.setFilter(filter);
				f7.setEntityViewInfo(view);
			}
		});

		FDCClientUtils.setRespDeptF7(prmtRespDept, this,
				canSelectOtherOrgPerson ? null : cuId);
		FDCClientUtils.setRespDeptF7(prmtCreateOrg, this,
				canSelectOtherOrgPerson ? null : cuId);
		FDCClientUtils.setPersonF7(prmtRespPerson, this,
				canSelectOtherOrgPerson ? null : cuId);
		
		FDCClientUtils.setRespDeptF7(prmtNeedDept, this,
				canSelectOtherOrgPerson ? null : cuId);
		FDCClientUtils.setPersonF7(prmtNeedPerson, this,
				canSelectOtherOrgPerson ? null : cuId);

		actionAddLine.setEnabled(false);
		actionAddLine.setVisible(false);

		actionInsertLine.setEnabled(false);
		actionInsertLine.setVisible(false);

		actionRemoveLine.setEnabled(false);
		actionRemoveLine.setVisible(false);

		//		txtGrtAmount.setEditable(false);
		txtCreator.setEnabled(false);
		kDDateCreateTime.setEnabled(false);

		if (editData.getContractSource() != null) {
			setInviteCtrlVisibleByContractSource(editData.getContractSourceId());
		}

		if (STATUS_ADDNEW.equals(getOprtState())
				&& prmtcontractType.getData() != null) {
			ContractTypeInfo cType = (ContractTypeInfo) prmtcontractType
					.getData();
			removeDetailTableRowsOfContractType();

			try {
				fillDetailByContractType(cType.getId().toString());
			} catch (Exception e) {
				handUIException(e);
			}
		}
		setContractType();

		if (editData != null
				&& (editData.getState() == FDCBillStateEnum.CANCEL || editData
						.isIsArchived())) {
			actionEdit.setEnabled(false);
			btnEdit.setEnabled(false);
			actionSplit.setEnabled(false);
			btnSplit.setEnabled(false);
		}

		//��ͬ����ǰ���в��
		if (splitBeforeAudit && editData.getState() != null
				&& !FDCBillStateEnum.SAVED.equals(editData.getState())) {
			actionSplit.setEnabled(true);
			actionSplit.setVisible(true);
			actionDelSplit.setEnabled(true);
			actionDelSplit.setVisible(true);
		}
		if (!FDCBillStateEnum.SUBMITTED.equals(editData.getState())) {
			actionDelSplit.setEnabled(false);
		}

		if (editData.getSplitState() == null
				|| CostSplitStateEnum.NOSPLIT.equals(editData.getSplitState())) {
			this.actionViewCost.setVisible(false);
		} else {
			this.actionViewCost.setVisible(true);
		}

		setPrecision();

		//��ʼ����Ӧ��F7
		FDCClientUtils.initSupplierF7(this, prmtpartB, cuId);
		FDCClientUtils.initSupplierF7(this, prmtpartC, cuId);
		FDCClientUtils.initSupplierF7(this, prmtwinUnit, cuId);
		FDCClientUtils.initSupplierF7(this, prmtlowestPriceUnit, cuId);
		FDCClientUtils.initSupplierF7(this, prmtlowerPriceUnit, cuId);
		FDCClientUtils.initSupplierF7(this, prmtmiddlePriceUnit, cuId);
		FDCClientUtils.initSupplierF7(this, prmthigherPriceUnit, cuId);
		FDCClientUtils.initSupplierF7(this, prmthighestPriceUni, cuId);

		addKDTableLisener();
		handleOldData();

		this.actionPrintPreview.setVisible(true);
		this.actionPrintPreview.setEnabled(true);
		this.actionPrint.setVisible(true);
		this.actionPrint.setEnabled(true);
		//��ͨ���������뵥�鿴��ͬʱ�����Բ鿴����ͬ�ĸ�����Ϣ��
		if (STATUS_FINDVIEW.equals(this.oprtState)) {

			this.actionAttachment.setVisible(true);
			this.actionAttachment.setEnabled(true);
			//û������ ����ǰ��������ȫ ����ʱ���鿴����ʾ��ְ�ť
			String prjId = editData.getOrgUnit().getId().toString();
			boolean isSplitBeforeAudit = FDCUtils.getDefaultFDCParamByKey(null,
					prjId, FDCConstants.FDC_PARAM_SPLITBFAUDIT);
			if (!isSplitBeforeAudit) {
				this.actionSplit.setEnabled(false);
				this.actionSplit.setVisible(false);
			}
		}

		getDetailInfoTable().getStyleAttributes().setWrapText(true);
		//getDetailInfoTable().getStyleAttributes().set
		this.disableTableMenus(this.getDetailInfoTable());
		//�޸ĺ�ͬʱ�ж��Ƿ�Ϊ�Ƕ�������Ĳ����ͬ  
		if (editData.getContractPropert() == ContractPropertyEnum.SUPPLY) {
			ICell loCell = getDetailInfoTable().getRow(getRowIndexByRowKey(LO_ROW)).getCell(CONTENT_COL);
			txtamount.setEditable(false);
			txtGrtRate.setRequired(false);
			txtGrtRate.setEditable(false);
			txtGrtAmount.setRequired(false);
			txtGrtAmount.setEditable(false);

			setAmountRequired(false);

			isUseAmtWithoutCost = true;
			loCell.getStyleAttributes().setLocked(true);
			this.txtGrtRate.setEditable(false);
		}

		//ҵ����־�ж�Ϊ��ʱȡ�ڼ��ж�
		if (pkbookedDate != null && pkbookedDate.isSupportedEmpty()) {
			pkbookedDate.setSupportedEmpty(false);
		}
		setInviteCtrlVisibleByContractSource(editData.getContractSourceId());
		//���ǲ鿴״̬ʱ�Ż�ý���
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				if (!getOprtState().equals(STATUS_VIEW)) {
					prmtcontractType.requestFocus(true);
				}
				//��ͬ����ӡ��˰�ʾ���Ŀǰ���Ե�0.01%�����ǽ���ͬ��ӡ��˰��Ϊ0.05�룬ϵͳ�޷�����
				txtStampTaxRate.setPrecision(6);
			}
		});
		//���ݲ����Ƿ���ʾ��ͬ������Ŀ
		if(!isShowCharge){
			this.conChargeType.setVisible(false);
			this.prmtCharge.setRequired(false);
			this.actionViewBgBalance.setVisible(false);
			this.actionViewBgBalance.setEnabled(false);
			this.menuItemViewBgBalance.setVisible(false);
			this.menuItemViewBgBalance.setEnabled(false);
		}else{
			this.conChargeType.setVisible(true);
			this.prmtCharge.setRequired(true);
			this.actionViewBgBalance.setVisible(true);
			this.actionViewBgBalance.setEnabled(true);
			this.menuItemViewBgBalance.setVisible(true);
			this.menuItemViewBgBalance.setEnabled(true);
		}
			//����������б���û�����ݣ���ô�鿴������ť�û�
			boolean hasAttachment=getAttachmentNamesToShow();
			if(!hasAttachment){
				this.btnViewAttachment.setEnabled(false);
				this.btnViewAttachment.setVisible(true);
			}else{
				this.btnViewAttachment.setEnabled(true);	
				this.btnViewAttachment.setVisible(true);
			}
//		}
	
		this.comboAttachmentNameList.setEditable(true);
		//������������ݣ�"�鿴����"��ťҲ�û�(��Ϊ�����"��������"�����б�һ��Ϊ�գ����Բ���Ҫ���ж�"�鿴����"��ť)
//		if(STATUS_ADDNEW.equals(getOprtState())){
//			this.btnViewContrnt.setEnabled(false);
//			this.btnViewContrnt.setVisible(true);
//		}
		
		if(getOprtState()==STATUS_EDIT){
			EcoItemHelper.setPayItemRowBackColorWhenInit(this.tblEconItem);
			EcoItemHelper.setBailRowBackColorWhenInit(this.tblBail, txtBailOriAmount, txtBailRate);			
		}

		if(this.editData.isIsSubContract()){
			this.prmtMainContract.setEnabled(true);
		}
	
		detailTableAutoFitRowHeight();
		
//		this.cbOrgType.removeItem(ContractTypeOrgTypeEnum.ALLRANGE);
		ContractTypeInfo info = (ContractTypeInfo) this.prmtcontractType.getValue();
		if(info!=null){
//			if(ContractTypeOrgTypeEnum.BIGRANGE.equals(info.getOrgType())||ContractTypeOrgTypeEnum.SMALLRANGE.equals(info.getOrgType())){
//				this.cbOrgType.setEnabled(false);
//			}
			Set id = new HashSet();
			for (int i = 0; i < info.getContractWFTypeEntry().size(); i++) {
				if (info.getContractWFTypeEntry().get(i).getContractWFType() != null) {
					id.add(info.getContractWFTypeEntry().get(i).getContractWFType().getId().toString());
				}
			}
			
			Set inviteid = new HashSet();
			for (int i = 0; i < info.getInviteTypeEntry().size(); i++) {
				if (info.getInviteTypeEntry().get(i).getInviteType() != null) {
					inviteid.add(info.getInviteTypeEntry().get(i).getInviteType().getId().toString());
				}
			}
			
			EntityViewInfo view = new EntityViewInfo();
			FilterInfo filter = new FilterInfo();
			if (id.size() > 0) {
				filter.getFilterItems().add(new FilterItemInfo("id", id, CompareType.INCLUDE));
			} else {
				filter.getFilterItems().add(new FilterItemInfo("id", null));
			}
			
			CurProjectInfo project=CurProjectFactory.getRemoteInstance().getCurProjectInfo(new ObjectUuidPK(this.editData.getCurProject().getId()));
			if(project.isIsOA()){
				filter.getFilterItems().add(new FilterItemInfo("description", "2021��������������"));
			}
			view.setFilter(filter);
			this.prmtContractWFType.setEntityViewInfo(view);
			
			view = new EntityViewInfo();
			filter = new FilterInfo();
			if (inviteid.size() > 0) {
				filter.getFilterItems().add(new FilterItemInfo("id", inviteid, CompareType.INCLUDE));
			} else {
				filter.getFilterItems().add(new FilterItemInfo("id", null));
			}
			view.setFilter(filter);
			this.prmtInviteType.setEntityViewInfo(view);
			
			if(inviteid.size()==0){
//				this.prmtInviteType.setAccessAuthority(CtrlCommonConstant.AUTHORITY_COMMON);
//				this.prmtInviteType.setEnabled(false);
			}
		}else{
//			this.prmtInviteType.setEnabled(false);
			this.prmtContractWFType.setEnabled(false);
//			this.cbOrgType.setEnabled(false);
		}
		this.prmtpartB.setAccessAuthority(CtrlCommonConstant.AUTHORITY_COMMON);
		this.prmtlandDeveloper.setAccessAuthority(CtrlCommonConstant.AUTHORITY_COMMON);
		
		labSettleType.setVisible(false);
		btnAType.setVisible(false);
		btnBType.setVisible(false);
		BtnCType.setVisible(false);
		
		this.actionViewContent.setVisible(false);
		this.btnAgreement.setVisible(false);
		
		this.tblAttachement.checkParsed();
		KDWorkButton btnUpLoad = new KDWorkButton();
//		KDWorkButton btnAgreementText = new KDWorkButton();
		KDWorkButton btnAttachment = new KDWorkButton();

		this.actionUpLoad.putValue("SmallIcon", EASResource.getIcon("imgTbtn_affixmanage"));
		btnUpLoad = (KDWorkButton)this.contAttachment.add(this.actionUpLoad);
		btnUpLoad.setText("�ϴ���׼��ͬ");
		btnUpLoad.setSize(new Dimension(140, 19));
		
//		this.actionAgreementText.putValue("SmallIcon", EASResource.getIcon("imgTbtn_affixmanage"));
//		btnAgreementText = (KDWorkButton)this.contAttachment.add(this.actionAgreementText);
//		btnAgreementText.setText("�ϴ���������");
//		btnAgreementText.setSize(new Dimension(140, 19));

		this.actionAttachment.putValue("SmallIcon", EASResource.getIcon("imgTbtn_affixmanage"));
		btnAttachment = (KDWorkButton) this.contAttachment.add(this.actionAttachment);
		btnAttachment.setText("��������");
		btnAttachment.setSize(new Dimension(140, 19));
		
		KDWorkButton btnDownLoad = new KDWorkButton();
		this.actionDownLoad.putValue("SmallIcon", EASResource.getIcon("imgTbtn_affixmanage"));
		btnDownLoad = (KDWorkButton) this.contMode.add(this.actionDownLoad);
		btnDownLoad.setText("���ر�׼��ͬ����");
		btnDownLoad.setSize(new Dimension(140, 19));
		
		this.contSrcAmount.getBoundLabel().setForeground(Color.RED);
		
		this.chkMenuItemSubmitAndAddNew.setVisible(false);
		this.chkMenuItemSubmitAndAddNew.setSelected(false);
		
		txtDes.setMaxLength(1000);
		
		HashMap hmParamIn = new HashMap();
		hmParamIn.put("FDC_ISNEED", null);
		try {
			HashMap hmAllParam = ParamControlFactory.getRemoteInstance().getParamHashMap(hmParamIn);
			if(hmAllParam.get("FDC_ISNEED")!=null){
				isNeed=Boolean.parseBoolean(hmAllParam.get("FDC_ISNEED").toString());
			}else{
				isNeed=false;
			}
		} catch (EASBizException e) {
			e.printStackTrace();
		} catch (BOSException e) {
			e.printStackTrace();
		}
		this.contNeedDept.setVisible(isNeed);
		this.contNeedPerson.setVisible(isNeed);
		
		this.btnAccountView.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_associatecreate"));
		this.actionPre.setVisible(false);
		this.actionNext.setVisible(false);
		this.actionFirst.setVisible(false);
		this.actionLast.setVisible(false);
		this.actionContractPlan.setVisible(false);
		
		this.txtRemark.setMaxLength(500);
		
		initTable();
		
		FilterInfo filter = new FilterInfo();
		FilterItemCollection filterItems = filter.getFilterItems();
		filterItems.add(new FilterItemInfo("parent.startDate", serverDate, CompareType.LESS_EQUALS));
		filterItems.add(new FilterItemInfo("parent.endDate", serverDate,CompareType.GREATER_EQUALS));
		filterItems.add(new FilterItemInfo("parent.state",FDCBillStateEnum.AUDITTED_VALUE));
		filterItems.add(new FilterItemInfo("hit",Boolean.TRUE));
		
//		InviteProjectEntriesCollection col=InviteProjectEntriesFactory.getRemoteInstance().getInviteProjectEntriesCollection("select parent.id from where project.id='"+this.editData.getCurProject().getId()+"'");
//		Set curProjectSet=new HashSet();
//		for(int i=0;i<col.size();i++){
//			curProjectSet.add(col.get(i).getParent().getId().toString());
//		}
//		InviteProjectCollection col1=InviteProjectFactory.getRemoteInstance().getInviteProjectCollection("select id from where invitePurchaseMode.type='3STRATEGY'");
//		for(int i=0;i<col1.size();i++){
//			curProjectSet.add(col1.get(i).getId().toString());
//		}
//		filterItems.add(new FilterItemInfo("inviteProject.id",curProjectSet,CompareType.INCLUDE));
		EntityViewInfo view=new EntityViewInfo();
		view.setFilter(filter);
		this.prmtTAEntry.setEntityViewInfo(view);
		
		filter = new FilterInfo();
		filterItems = filter.getFilterItems();
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
		
		prmtInviteType.setRequired(false);
		prmtInviteType.setEnabled(false);
		
		if(this.editData.getOaState()!=null&&this.editData.getOaState().equals("1")){
			this.prmtcontractType.setEnabled(false);
			this.txtcontractName.setEnabled(false);
			this.prmtlandDeveloper.setEnabled(false);
			this.prmtpartB.setEnabled(false);
			this.prmtpartC.setEnabled(false);
			this.contractPropert.setEnabled(false);
			this.comboCurrency.setEnabled(false);
			this.txtamount.setEnabled(false);
			this.txtGrtRate.setEnabled(false);
			this.pkbookedDate.setEnabled(false);
			this.txtGrtAmount.setEnabled(false);
			this.txtchgPercForWarn.setEnabled(false);
			this.contractSource.setEnabled(false);
			this.pkStartDate.setEnabled(false);
			this.pkEndDate.setEnabled(false);
			this.prmtRespPerson.setEnabled(false);
			this.prmtContractWFType.setEnabled(false);
			this.prmtRespDept.setEnabled(false);
			this.cbOrgType.setEnabled(false);
			this.prmtCreateOrg.setEnabled(false);
			this.txtDes.setEnabled(false);
			this.prmtLxNum.setEnabled(false);
			this.txtBank.setEnabled(false);
			this.txtBankAccount.setEnabled(false);
			this.cbTaxerQua.setEnabled(false);
			this.txtTaxerNum.setEnabled(false);
			this.txtNumber.setEnabled(false);
			this.prmtTAEntry.setEnabled(false);
			this.prmtMarketProject.setEnabled(false);
			this.prmtMpCostAccount.setEnabled(false);
			this.prmtinviteType.setEnabled(false);
			
			this.actionDetailALine.setEnabled(false);
			this.actionDetailILine.setEnabled(false);
			this.actionDetailRLine.setEnabled(false);
			this.kdtDetailEntry.setEnabled(false);
			this.actionYZALine.setEnabled(false);
			this.actionYZRLine.setEnabled(false);
			this.kdtYZEntry.setEnabled(false);
			this.cbJzType.setEnabled(false);
			this.pkJzStartDate.setEnabled(false);
			this.pkJzEndDate.setEnabled(false);
			this.tblMarket.setEnabled(false);
			this.tblDetail.setEnabled(false);
		}
		
		this.actionMALine.setVisible(false);
		this.actionMRLine.setVisible(false);
		
		this.actionAddNew.setVisible(false);
		this.actionCopy.setVisible(false);
		
		view=new EntityViewInfo();
		filter=new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("state",FDCBillStateEnum.AUDITTED_VALUE));
		filter.getFilterItems().add(new FilterItemInfo("curProject.id",this.editData.getCurProject().getId()));
		HashSet set = new HashSet();
		set.add(ContractPropertyEnum.DIRECT_VALUE);
		set.add(ContractPropertyEnum.THREE_PARTY_VALUE);
		set.add(ContractPropertyEnum.STRATEGY_VALUE);
		filterItems.add(new FilterItemInfo("contractPropert", set,
				CompareType.INCLUDE));
		view.setFilter(filter);
		this.prmtContractBillReceive.setEntityViewInfo(view);
		
		String param="false";
		param = ParamControlFactory.getRemoteInstance().getParamValue(new ObjectUuidPK(SysContext.getSysContext().getCurrentOrgUnit().getId()), "YF_BANKNUM");
		if("true".equals(param)){
			prmtLxNum.setRequired(true);
			txtBank.setRequired(true);
			txtBankAccount.setRequired(true);
			cbTaxerQua.setRequired(true);
			txtTaxerNum.setRequired(true);
			
			this.cbOrgType.removeItem(ContractTypeOrgTypeEnum.ALLRANGE);
			this.cbOrgType.removeItem(ContractTypeOrgTypeEnum.BIGRANGE);
			this.cbOrgType.removeItem(ContractTypeOrgTypeEnum.SMALLRANGE);
			
			txtchgPercForWarn.setRequired(true);
			pkStartDate.setRequired(true);
			pkEndDate.setRequired(true);
			
			this.cbConnectedTransaction.setRequired(true);
		}else{
			prmtLxNum.setRequired(false);
			txtBank.setRequired(false);
			txtBankAccount.setRequired(false);
			cbTaxerQua.setRequired(false);
			txtTaxerNum.setRequired(false);
			
			this.cbOrgType.removeItem(ContractTypeOrgTypeEnum.NEIBU);
			this.cbOrgType.removeItem(ContractTypeOrgTypeEnum.WAIBU);
			
			txtchgPercForWarn.setRequired(false);
			pkStartDate.setRequired(false);
			pkEndDate.setRequired(false);
			
			this.cbConnectedTransaction.setRequired(false);
		}
	}
	protected void prmtTAEntry_dataChanged(DataChangeEvent e) throws Exception {
		this.tblInvite.removeRows();
		this.prmtpartB.setValue(null);
		if(prmtTAEntry.getValue()!=null){
			TenderAccepterResultInfo tainfo=TenderAccepterResultFactory.getRemoteInstance().getTenderAccepterResultInfo(new ObjectUuidPK(((TenderAccepterResultEntryInfo)prmtTAEntry.getValue()).getParent().getId().toString()));
			String name=tainfo.getName();
			this.prmtTAEntry.setCommitFormat(name);		
	        this.prmtTAEntry.setDisplayFormat(name);		
	        this.prmtTAEntry.setEditFormat(name);	
	        
			loadTenderBill(((TenderAccepterResultEntryInfo)prmtTAEntry.getValue()).getParent().getId().toString());
			SupplierStockInfo info=SupplierStockFactory.getRemoteInstance().getSupplierStockInfo("select sysSupplier.*,* from where id='"+((TenderAccepterResultEntryInfo)prmtTAEntry.getValue()).getSupplier().getId()+"'");
			if(info.getSysSupplier()!=null){
				this.prmtpartB.setValue(info.getSysSupplier());
			}
			InviteProjectInfo p=InviteProjectFactory.getRemoteInstance().getInviteProjectInfo(new ObjectUuidPK(tainfo.getInviteProject().getId()));
			InviteTypeInfo t=InviteTypeFactory.getRemoteInstance().getInviteTypeInfo(new ObjectUuidPK(p.getInviteType().getId()));
			this.prmtInviteType.setValue(t);
		}
	}
	public void loadTenderBill(String id) throws EASBizException, BOSException{
		this.tblInvite.removeRows();
		if(id!=null){
			IRow row = this.tblInvite.addRow();
			TenderAccepterResultInfo info=TenderAccepterResultFactory.getRemoteInstance().getTenderAccepterResultInfo("select creator.name,auditor.name,* from where id='"+id+"'");
			if (info != null) {
				row.getCell("id").setValue(info.getId());
				row.getCell("number").setValue(info.getNumber());

				if (info.getRespDept() != null) {row.getCell("respDept").setValue(info.getRespDept().getName());
				}
				if (info.getCreator() != null) {
					row.getCell("creator").setValue(info.getCreator().getName());
				}
				row.getCell("createDate").setValue(info.getCreateTime());
				if (info.getAuditor() != null) {
					row.getCell("auditor").setValue(info.getAuditor().getName());
				}
				row.getCell("auditDate").setValue(info.getAuditTime());
				row.getCell("name").setValue(info.getName());
				row.setUserObject(info);
			}
		}
	}
	public void actionAccountView_actionPerformed(ActionEvent e)throws Exception {
		if(!ContractBillFactory.getRemoteInstance().exists(new ObjectUuidPK(this.editData.getId()))){
			FDCMsgBox.showWarning(this,"���ȱ��浥�ݣ�");
			SysUtil.abort();
		}
		showSplitUI(this,this.editData.getId().toString(),"contractBill.id",this.editData.getBOSType().toString(),true,this.txtLocalAmount.getBigDecimalValue());
	}

	//ҵ�����ڱ仯����,�ڼ�ı仯
	protected void bookedDate_dataChanged(
			com.kingdee.bos.ctrl.swing.event.DataChangeEvent e)
			throws Exception {
		//super.pkbookedDate_dataChanged(e);

		String projectId = this.editData.getCurProject().getId().toString();
		fetchPeriod(e, pkbookedDate, cbPeriod, projectId, true);

		this.comboCurrency_itemStateChanged(null);
	}

	private void setMaxMinValueForCtrl() {
		FDCClientHelper.setValueScopeForPercentCtrl(txtchgPercForWarn);
		FDCClientHelper.setValueScopeForPercentCtrl(txtpayPercForWarn);

		FDCClientHelper.setValueScopeForPercentCtrl(txtPayScale);
		FDCClientHelper.setValueScopeForPercentCtrl(txtStampTaxRate);
		FDCClientHelper.setValueScopeForPercentCtrl(txtGrtRate);

		this.txtamount.setMinimumValue(FDCHelper.MIN_VALUE);
		this.txtamount.setMaximumValue(FDCHelper.MAX_VALUE);
		this.txtLocalAmount.setMaximumValue(FDCHelper.MAX_TOTAL_VALUE2);
		this.txtLocalAmount.setMinimumValue(FDCHelper.MIN_TOTAL_VALUE2);
		txtExRate.setMaximumValue(FDCHelper.ONE_THOUSAND);
		txtExRate.setMinimumValue(FDCHelper.ZERO);

		txtNumber.setMaxLength(80);
//		txtcontractName.setMaxLength(80);

		KDTextField tblDetail_desc_TextField = new KDTextField();
		tblDetail_desc_TextField.setName("tblDetail_desc_TextField");
		tblDetail_desc_TextField.setMaxLength(80);
		KDTDefaultCellEditor tblDetail_desc_CellEditor = new KDTDefaultCellEditor(
				tblDetail_desc_TextField);
		tblDetail.getColumn("desc").setEditor(tblDetail_desc_CellEditor);
		
		this.txtcontractName.setMaxLength(200);
		EcoItemHelper.setEntryTableCtrl(this.tblEconItem, this.tblBail);
	}

	/**
	 * 
	 * ��������֤Tab��˳��
	 * 
	 * @author:liupd ����ʱ�䣺2006-7-19
	 *               <p>
	 */
	private void sortTabbedPanel() {

		kDTabbedPane1.removeAll();
		kDTabbedPane1.add(this.kDContainer3,resHelper.getString("kDContainer3.constraints"));
		kDTabbedPane1.add(pnlDetail, resHelper.getString("pnlDetail.constraints"));
		if(ContractPropertyEnum.SUPPLY.equals(contractPropert.getSelectedItem())){
			kDTabbedPane1.add(this.kdtSupplyEntry, "����ͬ��Ϣ");
		}else{
			kDTabbedPane1.add(this.kdtSupplyEntry, resHelper.getString("kdtSupplyEntry.constraints"));
		}
		kDTabbedPane1.add(this.panelInvite, resHelper.getString("panelInvite.constraints"));
		kDTabbedPane1.add(this.kDContainer4, resHelper.getString("kDContainer4.constraints"));
		kDTabbedPane1.add(this.kDContainer5, resHelper.getString("kDContainer5.constraints"));
		
//		kDTabbedPane1.add(pnlInviteInfo, resHelper.getString("pnlInviteInfo.constraints"));
		//�ڴ���Ҫ�������ע��
		//		kDTabbedPane1.add(pnlCost, "�ɱ���Ϣ");		
	}

	/**
	 * output prmtcontractType_willShow method
	 */
	protected void prmtcontractType_willShow(com.kingdee.bos.ctrl.swing.event.SelectorEvent e) throws Exception {
		if (STATUS_EDIT.equals(getOprtState())) {
			MsgBox.showWarning(this, FDCClientUtils.getRes("changeContractType"));
		}
		initPrmtcontractType();
	}
	
	private void initPrmtcontractType(){
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		view.setFilter(filter);
		ContractTypeInfo conTypeInfo = (ContractTypeInfo)prmtcontractType.getValue();
		if(conTypeInfo !=null){
			view.getFilter().getFilterItems().add(new FilterItemInfo("contractType.id",conTypeInfo.getId().toString()));
		}
		view.getFilter().getFilterItems().add(new FilterItemInfo("isLastVer",Boolean.TRUE));
		prmtModel.setEntityViewInfo(view);
	}
	protected void prmtModel_willShow(SelectorEvent e) throws Exception {
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		view.setFilter(filter);
		ContractTypeInfo conTypeInfo = (ContractTypeInfo)prmtcontractType.getValue();
		if(conTypeInfo !=null){
			view.getFilter().getFilterItems().add(new FilterItemInfo("contractType.id",conTypeInfo.getId().toString()));
		}
		view.getFilter().getFilterItems().add(new FilterItemInfo("isLastVer",Boolean.TRUE));
		prmtModel.setEntityViewInfo(view);
		prmtModel.getQueryAgent().resetRuntimeEntityView();
	}
	
	protected void prmtModel_dataChanged(DataChangeEvent e) throws Exception {
		boolean isChanged = true;
		isChanged = BizCollUtil.isF7ValueChanged(e);
		if (!isChanged) {
			return;
		}
		deleteTypeAttachment(this.editData.getId().toString(),"��׼��ͬ");
		
		deleteContractContent();
		
		ContractModelInfo contractModel = (ContractModelInfo)this.prmtModel.getValue();
		if(contractModel!=null){
			//���ú�ͬ����ID ȥ�Һ�ͬ����
			EntityViewInfo viewInfo = new EntityViewInfo();
			viewInfo.getSelector().add("id");
			viewInfo.getSelector().add("fileName");
			viewInfo.getSelector().add("version");
			viewInfo.getSelector().add("fileType");
			viewInfo.getSelector().add("createTime");
			viewInfo.getSelector().add("creator.*");
			viewInfo.getSelector().add("contentFile");
			SorterItemInfo sorterItemInfo = new SorterItemInfo("fileType");
			sorterItemInfo.setSortType(SortType.ASCEND);
			viewInfo.getSorter().add(sorterItemInfo);
			sorterItemInfo = new SorterItemInfo("version");
			sorterItemInfo.setSortType(SortType.DESCEND);
			viewInfo.getSorter().add(sorterItemInfo);
			FilterInfo filterInfo = new FilterInfo();
			filterInfo.appendFilterItem("parent", contractModel.getId().toString());
			viewInfo.setFilter(filterInfo);
			ContractContentCollection contentCollection = ContractContentFactory.getRemoteInstance().getContractContentCollection(viewInfo);
			ContractContentInfo model = (ContractContentInfo)contentCollection.get(0);
			if(model!=null){
				String fullname = model.getFileType();
				editData.setContractModel(contractModel.getId().toString());
				SelectorItemCollection sel = new SelectorItemCollection();
				sel.add("contractModel");
				BigDecimal version = new BigDecimal("1.0");
				ContractContentInfo contentInfo = new ContractContentInfo();
				contentInfo.setVersion(version);
				contentInfo.setContract(editData);
				contentInfo.setFileType(fullname);
				contentInfo.setContentFile(model.getContentFile());
				ContractContentFactory.getRemoteInstance().save(contentInfo);
				
				addModeAttachmentInfo(this.editData.getId().toString(),"��׼��ͬ",model.getFileType(),FormetFileSize(model.getContentFile().length),model.getContentFile(),"1");
			}
			this.prmtModel.setEnabled(false);
		}else{
			this.prmtModel.setEnabled(true);
		}
		fillAttachmnetTable();
	}
	
	//����˵֧��ά�����������һ��
	private void updateModel(boolean oprt) throws Exception {
		if(prmtModel.getValue()==null){
			editData.setModel(null);
			return;
		}
		AttachmentInfo info = (AttachmentInfo)prmtModel.getValue();
		FilterInfo filter = new FilterInfo();
		if(oprt){
			filter.getFilterItems().add(new FilterItemInfo("id", info.getId().toString()));
			AttachmentFactory.getRemoteInstance().delete(filter);
			editData.setModel(null);
			prmtModel.setValue(null);
			return;
		}
		if(editData.getModel()!=null&&editData.getModel().getId().toString().equals(info.getId().toString())){
			return;
		}
		if(editData.getModel()!=null){
			filter.getFilterItems().add(new FilterItemInfo("id", editData.getModel().getId().toString()));
			AttachmentFactory.getRemoteInstance().delete(filter);
			if(oprt){
				return;
			}
		}

		SelectorItemCollection sic = new SelectorItemCollection();
		sic.add(new SelectorItemInfo("*"));
		sic.add(new SelectorItemInfo("boAttchAsso.*"));
		
		filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("id", info.getId().toString()));
		EntityViewInfo evi = new EntityViewInfo();
		evi.setFilter(filter);
		evi.setSelector(sic);
		AttachmentInfo attachment = AttachmentFactory.getRemoteInstance().getAttachmentCollection(evi).get(0);
		attachment.setId(BOSUuid.create(attachment.getBOSType()));
		attachment.getBoAttchAsso().get(0).setId(BOSUuid.create("172F3A47"));
		attachment.getBoAttchAsso().get(0).setBoID(editData.getId().toString());
		AttachmentFactory.getRemoteInstance().addnew(attachment);
		editData.setModel(attachment);
		prmtModel.setValue(attachment);
	}
	
	protected void prmtcontractType_dataChanged(DataChangeEvent e)
			throws Exception {
		Object newValue = e.getNewValue();
		ContractTypeInfo info = (ContractTypeInfo) newValue;
		boolean isChanged = true;
		isChanged = BizCollUtil.isF7ValueChanged(e);
		if (!isChanged) {
			return;
		}
		setProgAndAccountState(info,(ContractPropertyEnum) contractPropert.getSelectedItem());
		
		contractTypeChanged(newValue);
		super.prmtcontractType_dataChanged(e);
		
		detailTableAutoFitRowHeight();
		this.prmtModel.setValue(null);
		
		if(info!=null){
			if(!ContractPropertyEnum.SUPPLY.equals(contractPropert.getSelectedItem())){
				if(this.editData.getProgrammingContract()!=null&&this.editData.getProgrammingContract().getContractType()!=null
						&&!this.editData.getProgrammingContract().getContractType().getId().equals(info.getId())){
					this.editData.setProgrammingContract(null);
					this.textFwContract.setText(null);
				}
			}
			if(info.isIsRelateReceive()){
				this.prmtContractBillReceive.setRequired(true);
				this.prmtContractBillReceive.setEnabled(true);
			}else{
				this.prmtContractBillReceive.setRequired(false);
				this.prmtContractBillReceive.setEnabled(false);
			}
//			if(ContractTypeOrgTypeEnum.BIGRANGE.equals(info.getOrgType())||ContractTypeOrgTypeEnum.SMALLRANGE.equals(info.getOrgType())){
//				this.cbOrgType.setSelectedItem(info.getOrgType());
//				this.cbOrgType.setEnabled(false);
//			}else{
//				this.cbOrgType.setSelectedItem(null);
//				this.cbOrgType.setEnabled(true);
//			}
			info=ContractTypeFactory.getRemoteInstance().getContractTypeInfo("select *,contractWFTypeEntry.contractWFType.*,inviteTypeEntry.inviteType.* from where id='"+info.getId().toString()+"'");
			Set id = new HashSet();
			for (int i = 0; i < info.getContractWFTypeEntry().size(); i++) {
				if (info.getContractWFTypeEntry().get(i).getContractWFType() != null) {
					id.add(info.getContractWFTypeEntry().get(i).getContractWFType().getId().toString());
				}
			}
			
			Set inviteid = new HashSet();
			for (int i = 0; i < info.getInviteTypeEntry().size(); i++) {
				if (info.getInviteTypeEntry().get(i).getInviteType() != null) {
					inviteid.add(info.getInviteTypeEntry().get(i).getInviteType().getId().toString());
				}
			}
			EntityViewInfo view = new EntityViewInfo();
			FilterInfo filter = new FilterInfo();
			if (id.size() > 0) {
				filter.getFilterItems().add(new FilterItemInfo("id", id, CompareType.INCLUDE));
			} else {
				filter.getFilterItems().add(new FilterItemInfo("id", null));
			}
			
			CurProjectInfo project=CurProjectFactory.getRemoteInstance().getCurProjectInfo(new ObjectUuidPK(this.editData.getCurProject().getId()));
			if(project.isIsOA()){
				filter.getFilterItems().add(new FilterItemInfo("description", "2021��������������"));
			}
			view.setFilter(filter);
			this.prmtContractWFType.setEntityViewInfo(view);
			
			view = new EntityViewInfo();
			filter = new FilterInfo();
			if (inviteid.size() > 0) {
				filter.getFilterItems().add(new FilterItemInfo("id", inviteid, CompareType.INCLUDE));
			} else {
				filter.getFilterItems().add(new FilterItemInfo("id", null));
			}
			view.setFilter(filter);
			this.prmtInviteType.setEntityViewInfo(view);
			if(inviteid.size()==0){
//				this.prmtInviteType.setValue(null);
//				this.prmtInviteType.setEnabled(false);
			}else{
//				this.prmtInviteType.setEnabled(true);
			}
			
			if(this.prmtContractWFType.getValue()!=null&&!id.contains(((ContractWFTypeInfo)this.prmtContractWFType.getValue()).getId().toString())){
				this.prmtContractWFType.setValue(null);
			}else if(info.getContractWFTypeEntry().size()==1){
				this.prmtContractWFType.setValue(info.getContractWFTypeEntry().get(0).getContractWFType());
			}
			this.prmtContractWFType.setEnabled(true);
			
			if(info.isIsTA()){
				this.prmtTAEntry.setEnabled(true);
				this.prmtTAEntry.setRequired(true);
				this.prmtpartB.setEnabled(false);
				if(ContractPropertyEnum.SUPPLY.equals(this.contractPropert.getSelectedItem())){
					this.prmtTAEntry.setEnabled(false);
					this.prmtTAEntry.setRequired(false);
					this.prmtpartB.setEnabled(false);
				}else{
//					this.prmtpartB.setEnabled(true);
				}
			}else{
				this.prmtTAEntry.setEnabled(false);
				this.prmtTAEntry.setRequired(false);
				this.prmtTAEntry.setValue(null);
				if(ContractPropertyEnum.SUPPLY.equals(this.contractPropert.getSelectedItem())){
					this.prmtpartB.setEnabled(false);
				}else{
					this.prmtpartB.setEnabled(true);
				}
			}
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
				this.cbJzType.setRequired(true);
				this.pkJzStartDate.setRequired(true);
				this.pkJzEndDate.setRequired(true);
				this.actionMALine.setEnabled(true);
				this.actionMRLine.setEnabled(true);
			}else{
				this.prmtMarketProject.setEnabled(false);
				this.prmtMarketProject.setRequired(false);
				this.prmtMpCostAccount.setEnabled(false);
				this.prmtMpCostAccount.setRequired(false);
				this.cbJzType.setRequired(false);
				this.pkJzStartDate.setRequired(false);
				this.pkJzEndDate.setRequired(false);
				this.actionMALine.setEnabled(false);
				this.actionMRLine.setEnabled(false);
				this.prmtMarketProject.setValue(null);
				this.prmtMpCostAccount.setValue(null);
				this.tblMarket.removeRows();
			}
		}else{
//			this.cbOrgType.setSelectedItem(null);
//			this.cbOrgType.setEnabled(false);
			
			this.prmtContractWFType.setValue(null);
			this.prmtContractWFType.setEnabled(false);
//			this.prmtInviteType.setValue(null);
//			this.prmtInviteType.setEnabled(false);
			
			this.prmtTAEntry.setEnabled(false);
			this.prmtTAEntry.setRequired(false);
			this.prmtTAEntry.setValue(null);
			
			if(ContractPropertyEnum.SUPPLY.equals(this.contractPropert.getSelectedItem())){
				this.prmtTAEntry.setEnabled(false);
				this.prmtTAEntry.setRequired(false);
				this.prmtpartB.setEnabled(false);
			}else{
				this.prmtpartB.setEnabled(true);
			}
			
			this.prmtMarketProject.setEnabled(false);
			this.prmtMarketProject.setRequired(false);
			this.prmtMpCostAccount.setEnabled(false);
			this.prmtMpCostAccount.setEnabled(false);
			this.actionMALine.setEnabled(false);
			this.actionMRLine.setEnabled(false);
			this.prmtMarketProject.setValue(null);
			this.prmtMpCostAccount.setValue(null);
			this.tblMarket.removeRows();
		}
		getModeByInvite();
	}
	private boolean isExistCodingRule() {
		String currentOrgId = this.orgUnitInfo.getId().toString();
		boolean isExist = false;
		try {
			ICodingRuleManager iCodingRuleManager = CodingRuleManagerFactory.getRemoteInstance();
			if (currentOrgId == null || currentOrgId.trim().length() == 0) {
				// ��ǰ�û�������֯������ʱ��ȱʡʵ�����ü��ŵ�
				currentOrgId = OrgConstants.DEF_CU_ID;
			}
			if (currentOrgId.length() == 0 || !iCodingRuleManager.isExist(editData, currentOrgId)) {
				currentOrgId = FDCClientHelper.getCurrentOrgId();
				if (iCodingRuleManager.isExist(editData, currentOrgId, "codeType.number")) {
					isExist = true;
				} else if (iCodingRuleManager.isExist(editData, currentOrgId)) {
					isExist = true;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return isExist;
	}
	
	/**
	 * ��ͬ���ͷ����ı�ʱ��Ҫ���Ĺ���
	 * @param newValue
	 */
	private void contractTypeChanged(Object newValue) throws Exception,
			BOSException, EASBizException, CodingRuleException {
		if (newValue != null && STATUS_ADDNEW.equals(getOprtState())) {
			ContractTypeInfo info = (ContractTypeInfo) newValue;
			//if (info != null&& 
			//update by renliang 2010-6-4
			 if((this.editData.getContractType() == null || !this.editData
							.getContractType().getId().equals(info.getId()))) {
				this.editData.setContractType(info);
//				if(isExistCodingRule()){
					this.handleCodingRule1();
//				}
			}
			removeDetailTableRowsOfContractType();
			fillDetailByContractType(info.getId().toString());
			chkCostSplit.setSelected(info.isIsCost());
			fillInfoByContractType(info);
		} else {
			if (newValue != null) {
				// û���������(Ҳ�����鵵����Ϊ�鵵ǰ�����������)�ĺ�ͬ�ĺ�ͬ���Ϳ����޸ģ���ͬ����Ҳ�����޸�
				if (STATUS_EDIT.equals(getOprtState())) {
					if ((this.editData.getState()
							.equals(com.kingdee.eas.fdc.basedata.FDCBillStateEnum.AUDITTED_VALUE))) {
						// prmtcontractType.setEnabled(false);
						actionSplit.setEnabled(true);
					} else {
						ContractTypeInfo info = (ContractTypeInfo) newValue;
						if (!info.isIsLeaf()) {
							MsgBox.showWarning(this, FDCClientUtils
									.getRes("selectLeaf"));
							prmtcontractType.setData(null);
							prmtcontractType.requestFocus();
							SysUtil.abort();
						}
						removeDetailTableRowsOfContractType();
						fillDetailByContractType(info.getId().toString());
						chkCostSplit.setSelected(info.isIsCost());
						fillInfoByContractType(info);
						if (this.editData.getContractType() != null
								///&& info != null update by renliang
								&& !this.editData.getContractType().getId()
										.equals(info.getId())) {
							this.editData.setContractType(info);
//							if(isExistCodingRule()){//���� 
								this.handleCodingRule1();
//							}
						}
					}
				}
			}
		}
		setCheckBoxValue();
	}

	/**
	 * ���ݺ�ͬ������������������
	 * @param info
	 * @throws BOSException
	 * @throws EASBizException
	 */
	protected void fillInfoByContractType(ContractTypeInfo info)
			throws BOSException, EASBizException {
		//R100429-042��ͬ���͸ı�ʱ�����β��Ų����ݺ�ͬ���͵ĸı���ı䣬����ͬ���͸Ĳ�������β��� by cassiel_peng 2010-05-21
		/*
		 * R100429-042֮ǰ�ĸĶ������⣬�����������棬ѡ���ͬ���ͣ��޷��������β���.
		 * Ӧ���ǣ����β�����ֵ֮�����β��Ų����ݺ�ͬ���͵ĸı���ı� by zhiyuan_tang
		 */
		if (info.getDutyOrgUnit() != null) {
			BOSUuid id = info.getDutyOrgUnit().getId();
			SelectorItemCollection selectors = new SelectorItemCollection();
			selectors.add("id");
			selectors.add("number");
			selectors.add("name");
			selectors.add("isLeaf");
			CoreBaseInfo value = AdminOrgUnitFactory.getRemoteInstance()
			.getValue(new ObjectUuidPK(id), selectors);
			prmtRespDept.setValue(value);
		}

		txtPayScale.setNumberValue(info.getPayScale());

		txtStampTaxRate.setNumberValue(info.getStampTaxRate());
	}

	private void removeDetailTableRowsOfContractType() {

		for (int i = 0; i < getDetailInfoTable().getRowCount(); i++) {
			String rowKey = (String) getDetailInfoTable()
					.getCell(i, ROWKEY_COL).getValue();
			if (rowKey == null || rowKey.length() == 0) {
				getDetailInfoTable().removeRow(i);
				i--;
			}
		}
	}

	/**
	 * ���ݺ�ͬ��������ͬ��ϸ��Ϣҳǩ������
	 * @param id
	 * @throws Exception
	 */
	private void fillDetailByContractType(String id) throws Exception {

		/**
		 * �����Զ���ĺ�ͬ���͵�ʱ��ϵͳû���Զ���ʾ��ע
		 */
		if (!ContractClientUtils.isContractTypePre(id)) {
			IRow row = getDetailInfoTable().addRow(0);
			row.getCell(DETAIL_COL).setValue(
					ContractClientUtils.getRes("remark"));
			row.getCell(DATATYPE_COL).setValue(DataTypeEnum.STRING);
		}

		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("contractType.id", id));
		filter.getFilterItems().add(
				new FilterItemInfo("isEnabled", Boolean.TRUE));
		view.setFilter(filter);
		SorterItemInfo sorterItemInfo = new SorterItemInfo("number");
		sorterItemInfo.setSortType(SortType.DESCEND);
		view.getSorter().add(sorterItemInfo);
		ContractDetailDefCollection contractDetailDefCollection = ContractDetailDefFactory
				.getRemoteInstance().getContractDetailDefCollection(view);

		KDTDefaultCellEditor editorString = getEditorByDataType(DataTypeEnum.STRING);

		for (Iterator iter = contractDetailDefCollection.iterator(); iter.hasNext();) {
			ContractDetailDefInfo element = (ContractDetailDefInfo) iter.next();
			IRow row = getDetailInfoTable().addRow(0);
			row.getCell(DETAIL_COL).setValue(element.getName());
			KDTDefaultCellEditor editor = getEditorByDataType(element.getDataTypeEnum());
			if (editor != null) {
				row.getCell(CONTENT_COL).setEditor(editor);
			}
			
			
			if (element.getDataTypeEnum() == DataTypeEnum.DATE) {
				row.getCell(CONTENT_COL).setValue(FDCHelper.getCurrentDate());
				row.getCell(CONTENT_COL).getStyleAttributes().setNumberFormat(
						"%r{yyyy-M-d}t");
			} else if (element.getDataTypeEnum() == DataTypeEnum.NUMBER) {
				row.getCell(CONTENT_COL).getStyleAttributes()
						.setHorizontalAlign(HorizontalAlignment.RIGHT);
			}
			row.getCell(DATATYPE_COL).setValue(element.getDataTypeEnum());
			row.getCell(DETAIL_DEF_ID).setValue(element.getId().toString());

			row.getCell(DESC_COL).setEditor(editorString);
			
			// �����ͬ��ϸ����Ϊ��¼��򱳾�ɫҪ��ʾΪ��¼ɫ  added by owen_wen 2010-09-10
			if (element.isIsMustInput()){
				this.setRequiredBGColor(row);
			}
		}

	}

	public KDTDefaultCellEditor getEditorByDataType(DataTypeEnum dataType) {
		if (dataType == DataTypeEnum.DATE) {
			//KDDatePicker datePicker = new KDDatePicker();
			return FDCClientHelper.getDateCellEditor();
		} else if (dataType == DataTypeEnum.BOOL) {
			KDComboBox booleanCombo = getBooleanCombo();
			return new KDTDefaultCellEditor(booleanCombo);
		} else if (dataType == DataTypeEnum.NUMBER) {
			return FDCClientHelper.getNumberCellEditor();
		} else if (dataType == DataTypeEnum.STRING) {

			KDTextArea indexValue_TextField = new KDTextArea();
			indexValue_TextField.setName("indexValue_TextField");
			indexValue_TextField.setVisible(true);
			indexValue_TextField.setEditable(true);
			indexValue_TextField.setMaxLength(1000);
			indexValue_TextField.setColumns(10);
			indexValue_TextField.setWrapStyleWord(true);
			indexValue_TextField.setLineWrap(true);
			indexValue_TextField.setAutoscrolls(true);

			KDTDefaultCellEditor indexValue_CellEditor = new KDTDefaultCellEditor(
					indexValue_TextField);
			indexValue_CellEditor.setClickCountToStart(1);
			return indexValue_CellEditor;
		}
		return null;
	}

	public void setOprtState(String oprtType) {
		super.setOprtState(oprtType);
		if (STATUS_ADDNEW.equals(oprtType)) {
			prmtcontractType.setEnabled(true);
			actionSplit.setEnabled(false);
		} else {
			if (this.editData != null) {
				setContractType();
			} else {
				//				prmtcontractType.setEnabled(false);
				actionSplit.setEnabled(true);
			}
		}
		if (STATUS_VIEW.equals(oprtType) || "FINDVIEW".equals(oprtType)) {
			actionAddLine.setEnabled(false);
			actionInsertLine.setEnabled(false);
			actionRemoveLine.setEnabled(false);
			btnAddLine.setEnabled(false);
			btnInsertLine.setEnabled(false);
			btnRemoveLine.setEnabled(false);
			tblDetail.getStyleAttributes().setLocked(true);
			//			menuBiz.setVisible(false);
		}

		actionViewContent.setEnabled(true);

		if (STATUS_FINDVIEW.equals(oprtType)
				&& getUIContext().get("source") == null) {
			actionSplit.setEnabled(true);
			actionSplit.setVisible(true);
			actionDelSplit.setEnabled(false);
			actionDelSplit.setVisible(false);
		} else {
			actionSplit.setEnabled(false);
			actionSplit.setVisible(false);
			actionDelSplit.setEnabled(false);
			actionDelSplit.setVisible(false);
		}

		//��ͬ����ǰ���в��
		if (splitBeforeAudit && editData != null && editData.getState() != null
				&& !FDCBillStateEnum.SAVED.equals(editData.getState())) {
			actionSplit.setEnabled(true);
			actionSplit.setVisible(true);
			actionDelSplit.setEnabled(true);
			actionDelSplit.setVisible(true);
		}
		if (editData != null
				&& !FDCBillStateEnum.SUBMITTED.equals(editData.getState())) {
			actionDelSplit.setEnabled(false);
		}

		if (oprtType.equals(STATUS_ADDNEW)) {
			actionContractPlan.setEnabled(false);
		} else {
			actionContractPlan.setEnabled(true);
		}

		if (oprtType.equals(OprtState.VIEW)) {
			this.actionUpLoad.setEnabled(false);
			this.actionAgreementText.setEnabled(false);
		}else{
			this.actionUpLoad.setEnabled(true);
			this.actionAgreementText.setEnabled(true);
		}
		setProgAndAccountState((ContractTypeInfo) prmtcontractType.getValue(),(ContractPropertyEnum) contractPropert.getSelectedItem());
		
		if (oprtType.equals(OprtState.VIEW)) {
			this.actionDetailALine.setEnabled(false);
			this.actionDetailILine.setEnabled(false);
			this.actionDetailRLine.setEnabled(false);
			
			this.actionMALine.setEnabled(false);
			this.actionMRLine.setEnabled(false);
			
			this.tblMarket.setEditable(false);
		} else {
			this.actionDetailALine.setEnabled(true);
			this.actionDetailILine.setEnabled(true);
			this.actionDetailRLine.setEnabled(true);
			
			this.actionMALine.setEnabled(true);
			this.actionMRLine.setEnabled(true);
		}
		
		if(this.curProject!=null){
//			if(TaxInfoEnum.SIMPLE.equals(curProject.getTaxInfo())){
//				this.cbTaxerQua.setAccessAuthority(CtrlCommonConstant.AUTHORITY_COMMON);
//				this.txtTaxerNum.setAccessAuthority(CtrlCommonConstant.AUTHORITY_COMMON);
//				this.txtBank.setAccessAuthority(CtrlCommonConstant.AUTHORITY_COMMON);
//				this.txtBankAccount.setAccessAuthority(CtrlCommonConstant.AUTHORITY_COMMON);
//				this.cbTaxerQua.setEnabled(false);
//				this.txtTaxerNum.setEnabled(false);
//				this.txtBank.setEnabled(false);
//				this.txtBankAccount.setEnabled(false);
//				
//				this.actionDetailALine.setEnabled(false);
//				this.actionDetailILine.setEnabled(false);
//				this.actionDetailRLine.setEnabled(false);
//			}
			
			boolean isUseYz=false;
			if(curProject.isIsOA()){
				for(int i=0;i<tblDetail.getRowCount();i++){
					if(tblDetail.getRow(i).getCell(DETAIL_COL).getValue().toString().equals("�Ƿ�ʹ�õ�����")
							&&tblDetail.getRow(i).getCell(CONTENT_COL).getValue()!=null&&tblDetail.getRow(i).getCell(CONTENT_COL).getValue().toString().equals("��")){
						isUseYz=true;
					}
				}
			}
			if(oprtType.equals(OprtState.VIEW)){
				this.actionYZALine.setEnabled(false);
				this.actionYZRLine.setEnabled(false);
			}else{
				if(isUseYz){
					this.actionYZALine.setEnabled(true);
					this.actionYZRLine.setEnabled(true);
				}else{
					this.actionYZALine.setEnabled(false);
					this.actionYZRLine.setEnabled(false);
				}
			}
		}
	}
	public void setProgAndAccountState(ContractTypeInfo contractType,ContractPropertyEnum contractProperty){
		if(STATUS_ADDNEW.equals(this.getOprtState()) ||STATUS_EDIT.equals(this.getOprtState())){
			if (ContractPropertyEnum.SUPPLY.equals(contractProperty)) {
				this.btnProgram.setEnabled(false);
				this.btnAccountView.setEnabled(false);
			} else {
				if(contractType!=null&&contractType.isIsAccountView()){
					this.btnAccountView.setEnabled(true);
					this.btnProgram.setEnabled(false);
				}else{
					if(this.editData!=null&&this.editData.getCurProject()!=null&&this.editData.getCurProject().isIsWholeAgeStage()){
						this.btnProgram.setEnabled(false);
					}else{
						this.btnProgram.setEnabled(true);
					}
					this.btnAccountView.setEnabled(false);
				}
			}
		}else{
			btnProgram.setEnabled(false);
			btnAccountView.setEnabled(false);
		}
	}
	/*
	 * 20070315 jack û���������(Ҳ�����鵵����Ϊ�鵵ǰ�����������)�ĺ�ͬ�ĺ�ͬ���Ϳ����޸ģ���ͬ����Ҳ�����޸�
	 */
	public void setContractType() {
		if (STATUS_EDIT.equals(getOprtState())) {
			if (this.editData.getState() != null
					&& (this.editData.getState()
							.equals(com.kingdee.eas.fdc.basedata.FDCBillStateEnum.AUDITTED_VALUE))) {
				//				prmtcontractType.setEnabled(false);
				actionSplit.setEnabled(true);
			} else {
				prmtcontractType.setEnabled(true);
				actionSplit.setEnabled(false);
			}
		}
	}

	public SelectorItemCollection getSelectors() {
		SelectorItemCollection sic = super.getSelectors();
		sic.add(new SelectorItemInfo("curProject.id"));
		sic.add(new SelectorItemInfo("curProject.name"));
		sic.add(new SelectorItemInfo("curProject.number"));
		sic.add(new SelectorItemInfo("curProject.codingNumber"));
		sic.add(new SelectorItemInfo("curProject.displayName"));
		sic.add(new SelectorItemInfo("curProject.fullOrgUnit.name"));
		sic.add(new SelectorItemInfo("curProject.isWholeAgeStage"));
		
		sic.add(new SelectorItemInfo("currency.number"));
		sic.add(new SelectorItemInfo("currency.name"));
		sic.add(new SelectorItemInfo("currency.precision"));

		sic.add(new SelectorItemInfo("CU.id"));
		sic.add(new SelectorItemInfo("orgUnit.id"));
		sic.add(new SelectorItemInfo("contractType.*"));
		
		sic.add(new SelectorItemInfo("codeType.id"));
		sic.add(new SelectorItemInfo("codeType.name"));
		sic.add(new SelectorItemInfo("codeType.number"));
		sic.add(new SelectorItemInfo("codeType.thirdType"));
		sic.add(new SelectorItemInfo("codeType.secondType"));
		
		sic.add(new SelectorItemInfo("entrys.*"));
		sic.add(new SelectorItemInfo("contractPlan.*"));

		sic.add(new SelectorItemInfo("amount"));
		sic.add(new SelectorItemInfo("originalAmount"));
		sic.add(new SelectorItemInfo("state"));
		sic.add(new SelectorItemInfo("isAmtWithoutCost"));

		sic.add(new SelectorItemInfo("isArchived"));
		sic.add(new SelectorItemInfo("splitState"));

		sic.add(new SelectorItemInfo("period.number"));
		sic.add(new SelectorItemInfo("period.periodNumber"));
		sic.add(new SelectorItemInfo("period.beginDate"));
		sic.add(new SelectorItemInfo("period.periodYear"));
		
		sic.add(new SelectorItemInfo("payItems.*"));
		sic.add(new SelectorItemInfo("bail.*"));
//		sic.add(new SelectorItemInfo("bail.entry.bailConditon"));
//		sic.add(new SelectorItemInfo("bail.entry.bailAmount"));
//		sic.add(new SelectorItemInfo("bail.entry.prop"));
//		sic.add(new SelectorItemInfo("bail.entry.bailDate"));
//		sic.add(new SelectorItemInfo("bail.entry.desc"));
		
		sic.add(new SelectorItemInfo("auditor.id"));
		
		sic.add(new SelectorItemInfo("sourceBillId"));
		
		sic.add(new SelectorItemInfo("mainContract.*"));
		sic.add(new SelectorItemInfo("effectiveStartDate"));
		sic.add(new SelectorItemInfo("effectiveEndDate"));
		sic.add(new SelectorItemInfo("isSubContract"));
		sic.add(new SelectorItemInfo("information"));
		
		sic.add(new SelectorItemInfo("overRate"));
		sic.add(new SelectorItemInfo("bizDate"));
		sic.add(new SelectorItemInfo("bookDate"));
		
		sic.add(new SelectorItemInfo("createDept.id"));
		sic.add(new SelectorItemInfo("createDept.name"));
		sic.add(new SelectorItemInfo("createDept.number"));
		sic.add(new SelectorItemInfo("contractSettleType"));
		sic.add(new SelectorItemInfo("srcProID"));
		
		sic.add(new SelectorItemInfo("agreementID"));
		sic.add(new SelectorItemInfo("contractModel"));
		
		sic.add(new SelectorItemInfo("programmingContract.id"));
		sic.add(new SelectorItemInfo("programmingContract.amount"));
		sic.add(new SelectorItemInfo("programmingContract.name"));
		sic.add(new SelectorItemInfo("programmingContract.number"));
		sic.add(new SelectorItemInfo("programmingContract.balance"));
		sic.add(new SelectorItemInfo("programmingContract.estimateAmount"));
		
//		sic.add(new SelectorItemInfo("programmingContract.programming.*"));
		sic.add(new SelectorItemInfo("programmingContract.programming.project.id"));
		sic.add(new SelectorItemInfo("programmingContract.contractType.id"));
		sic.add(new SelectorItemInfo("contractType.contractWFTypeEntry.contractWFType.*"));
		sic.add(new SelectorItemInfo("contractType.inviteTypeEntry.inviteType.*"));
		
		sic.add(new SelectorItemInfo("taEntry.parent.*"));
		sic.add(new SelectorItemInfo("sourceFunction"));
		sic.add(new SelectorItemInfo("oaPosition"));
		sic.add(new SelectorItemInfo("oaOpinion"));
		sic.add(new SelectorItemInfo("oaState"));
		sic.add(new SelectorItemInfo("isTimeOut"));
		return sic;
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

	/**
	 * ��ʾ������
	 */
	protected void loadLineFields(KDTable table, IRow row, IObjectValue obj) {
		if(table==this.tblEconItem){
			if(obj!=null){
				dataBinder.loadLineFields(tblEconItem, row, obj);
			}
		}
		if(table==this.tblBail){
			if(obj!=null){
				dataBinder.loadLineFields(tblBail, row, obj);
			}
		}
	}

	private void storeDetailEntries() {
		ContractBillEntryInfo entryInfo = null;
		int count = getDetailInfoTable().getRowCount();
		ContractBillInfo billInfo = new ContractBillInfo();
		billInfo.setId(editData.getId());

		for (int i = 0; i < editData.getEntrys().size(); i++) {
			editData.getEntrys().removeObject(i);
			i--;
		}
		/*
		 * ���������ͬ����,�Ҳ���ֱ�Ӻ�ͬ,���õ���ͷ�ϵ�����ͬ����
		 */
		editData.setMainContractNumber(null);
		for (int i = 0; i < count; i++) {
			entryInfo = new ContractBillEntryInfo();

			entryInfo.setParent(billInfo);
			String detail = (String) getDetailInfoTable()
					.getCell(i, DETAIL_COL).getValue();
			DataTypeEnum dataType = (DataTypeEnum) getDetailInfoTable()
					.getCell(i, DATATYPE_COL).getValue();
			Object contentValue = getDetailInfoTable().getCell(i, CONTENT_COL)
					.getValue();
			String content = null;

			if (contentValue != null) {
				if (contentValue instanceof CoreBaseInfo) {
					content = ((CoreBaseInfo) contentValue).getId().toString();

				} else if (contentValue instanceof Date) {
					Date date = (Date) contentValue;
					content = DateFormat.getDateInstance().format(date);

				} else {
					content = contentValue.toString();
				}
			}

			String rowKey = (String) getDetailInfoTable().getCell(i, ROWKEY_COL).getValue();
			String desc = (String) getDetailInfoTable().getCell(i, DESC_COL).getValue();
			String detailDefId = (String) getDetailInfoTable().getCell(i, DETAIL_DEF_ID).getValue();

			entryInfo.setDetail(detail);

			entryInfo.setContent(content);
			entryInfo.setRowKey(rowKey);
			entryInfo.setDesc(desc);
			if (dataType != null) {
				entryInfo.setDataType(dataType);
			}
			entryInfo.setDetailDefID(detailDefId);
			editData.getEntrys().add(entryInfo);

			/*
			 * ���������ͬ����,�Ҳ���ֱ�Ӻ�ͬ,���õ���ͷ�ϵ�����ͬ����
			 */
			if (rowKey != null && rowKey.equals(NU_ROW)
					&& editData.getContractPropert() != ContractPropertyEnum.DIRECT) {
				if (contentValue != null && contentValue instanceof CoreBillBaseInfo) {
					String number = ((CoreBillBaseInfo) contentValue).getNumber();
					editData.setMainContractNumber(number);
				}
			}

			if (isUseAmtWithoutCost && rowKey != null && rowKey.equals(AM_ROW)) {
				//R110506-0418���ǵ�������Ĳ����ͬ���Ƴɱ����ȡ���ھ���һ��  By zhiyuan_tang
//				editData.setAmount(FDCHelper.toBigDecimal(content));
//				editData.setOriginalAmount(FDCHelper.divide(FDCHelper.toBigDecimal(content), txtExRate.getBigDecimalValue()));
				editData.setOriginalAmount(FDCHelper.toBigDecimal(content));
				editData.setAmount(FDCHelper.multiply(FDCHelper.toBigDecimal(content), txtExRate.getBigDecimalValue()));
			}

		}
	}
	private void detailTableAutoFitRowHeight() {
		//ADD by zhiyuan_tang 2010-08-06  ��ͬ��ϸ����Ӧ�и�
		for (int i = 0; i< getDetailInfoTable().getRowCount(); i++) {
			KDTableHelper.autoFitRowHeight(getDetailInfoTable(), i);
		}
	}
	
	/**
	 * ���ñ��ԪΪ����Ϊ��¼ɫ
	 * @author owen_wen 2010-09-10
	 */
	private void setRequiredBGColor(IRow row){
		row.getCell(CONTENT_COL).getStyleAttributes().setBackground(FDCColorConstants.requiredColor);
		row.getCell(DESC_COL).getStyleAttributes().setBackground(FDCColorConstants.requiredColor);
	}

	/**
	 * �����ͬ��ϸ�ķ�¼
	 */
	protected void loadDetailEntries() {
		if (STATUS_ADDNEW.equals(getOprtState()))
			return;
		getDetailInfoTable().removeRows(false);

		ContractBillEntryCollection coll = editData.getEntrys();
		if (coll == null || coll.size() == 0) {
			return;
		}

		KDTDefaultCellEditor editorString = getEditorByDataType(DataTypeEnum.STRING);
		getDetailInfoTable().getColumn(DESC_COL).getStyleAttributes().setWrapText(true);
		getDetailInfoTable().getColumn(CONTENT_COL).getStyleAttributes().setWrapText(true);
		int size = coll.size();

		ContractDetailDefCollection detailColls = getConDetailsDef();
		
		ContractBillEntryInfo entryInfo = null;
		for (int i = 0; i < size; i++) {
			entryInfo = (ContractBillEntryInfo) coll.get(i);
			IRow row = getDetailInfoTable().addRow();
			row.getCell(DETAIL_COL).setValue(entryInfo.getDetail());

			DataTypeEnum dataType = entryInfo.getDataType();
			if (dataType == DataTypeEnum.DATE) {
				row.getCell(CONTENT_COL).getStyleAttributes().setNumberFormat("%r{yyyy-M-d}t");
			} else if (dataType == DataTypeEnum.NUMBER) {
				row.getCell(CONTENT_COL).getStyleAttributes().setNumberFormat("%r-[ ]0.2n");
				row.getCell(CONTENT_COL).getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
			}
			//��ϸ��Ϣ����ID����Ϊ�գ� �˴�������жϡ� modify by yzq at 2010-09-20
			if (!StringUtils.isEmpty(entryInfo.getDetailDefID())) {
				ContractDetailDefInfo cddi = null;
				try {
					cddi = ContractDetailDefFactory.getRemoteInstance().getContractDetailDefInfo(
							new ObjectUuidPK(BOSUuid.read(entryInfo.getDetailDefID())));
				} catch (Exception e1) {
					logger.warn(e1.getCause(), e1);
				} 
				if (cddi != null && cddi.isIsMustInput()) {
					setRequiredBGColor(row);
				}
			}

			KDTDefaultCellEditor editor = getEditorByDataType(dataType);

			if (editor != null) {
				row.getCell(CONTENT_COL).setEditor(editor);
			}
			if (entryInfo.getRowKey() != null&& entryInfo.getRowKey().equals(NU_ROW)) {
				KDBizPromptBox kDBizPromptBoxContract = getContractF7Editor();
				KDTDefaultCellEditor prmtContractEditor = new KDTDefaultCellEditor(kDBizPromptBoxContract);
				row.getCell(CONTENT_COL).setEditor(prmtContractEditor);
				ObjectValueRender objectValueRender = getF7Render();
				row.getCell(CONTENT_COL).setRenderer(objectValueRender);
			}

			if (entryInfo.getRowKey() != null
					&& entryInfo.getRowKey().equals(LO_ROW)) {
				KDComboBox box = (KDComboBox) row.getCell(CONTENT_COL)
						.getEditor().getComponent();
				IsLonelyChangeListener isLonelyChangeListener = new IsLonelyChangeListener();
				box.addItemListener(isLonelyChangeListener);

				lastDispAddRows = true;
			}

			if (entryInfo.getRowKey() != null&& entryInfo.getRowKey().equals(NU_ROW)) {
				Component component = row.getCell(CONTENT_COL).getEditor()
						.getComponent();
				if (component instanceof KDBizPromptBox) {
					KDBizPromptBox box = (KDBizPromptBox) row.getCell(
							CONTENT_COL).getEditor().getComponent();
					box.addDataChangeListener(new MyDataChangeListener());
				}
			}

			if (entryInfo.getRowKey() != null
					&& entryInfo.getRowKey().equals(NA_ROW)) {
				row.getCell(CONTENT_COL).getStyleAttributes().setLocked(true);

			}
			//Ԥ����ͬ�䶯���  2010330 wangxin
			if (entryInfo.getRowKey() != null
					&& entryInfo.getRowKey().equals(ES_ROW)) {
				row.getCell(CONTENT_COL).getStyleAttributes().setLocked(true);

			}
			if (entryInfo.getRowKey() != null
					&& entryInfo.getRowKey().equals(ESID_ROW)) {
				row.getStyleAttributes().setHided(true);

			}

			if (entryInfo.getRowKey() != null
					&& entryInfo.getRowKey().equals(NU_ROW)
					&& entryInfo.getContent() != null
					&& entryInfo.getContent().trim().length() > 0) {
				String id = entryInfo.getContent();
				try {
					ContractBillInfo contractBillInfo = ContractBillFactory
							.getRemoteInstance().getContractBillInfo(
									new ObjectUuidPK(id));
					row.getCell(CONTENT_COL).setValue(contractBillInfo);
					mainContractId = id;
				} catch (Exception e) {
					handUIException(e);
				}
				
			} else if (entryInfo.getRowKey() != null
					&& entryInfo.getRowKey().equals(AM_ROW)) {
				if (entryInfo.getContent() != null
						&& entryInfo.getContent().trim().length() > 0) {
					BigDecimal decm = new BigDecimal(entryInfo.getContent());
					row.getCell(CONTENT_COL).setValue(decm);
				}

				if (isUseAmtWithoutCost) {
					if (STATUS_FINDVIEW.equals(this.getOprtState())
							|| STATUS_VIEW.equals(this.getOprtState())) {
						row.getCell(CONTENT_COL).getStyleAttributes()
								.setLocked(true);
					} else
						row.getCell(CONTENT_COL).getStyleAttributes()
								.setLocked(false);
					txtamount.setEditable(false);
					setAmountRequired(false);
				} else {
					row.getCell(CONTENT_COL).getStyleAttributes().setLocked(
							true);
					txtamount.setEditable(true);
					setAmountRequired(true);
				}
			} else if (dataType == DataTypeEnum.BOOL) {
				BooleanEnum be = BooleanEnum.FALSE;
				if (entryInfo.getContent() != null
						&& entryInfo.getContent().equals(
								BooleanEnum.TRUE.getAlias())) {
					be = BooleanEnum.TRUE;

				}
				row.getCell(CONTENT_COL).setValue(be);
			} else if (dataType == DataTypeEnum.DATE
					&& !FDCHelper.isEmpty(entryInfo.getContent())) {
				DateFormat df = DateFormat.getDateInstance();

				Date date = null;
				try {
					date = df.parse(entryInfo.getContent());
				} catch (ParseException e) {
					e.printStackTrace();
					//throw new RuntimeException(e);
				}
				row.getCell(CONTENT_COL).setValue(date);
			} else {
				row.getCell(CONTENT_COL).setValue(entryInfo.getContent());
			}

			row.getCell(ROWKEY_COL).setValue(entryInfo.getRowKey());
			row.getCell(DESC_COL).setValue(entryInfo.getDesc());
			row.getCell(DATATYPE_COL).setValue(dataType);
			row.getCell(DETAIL_DEF_ID).setValue(entryInfo.getDetailDefID());
			//			lastDispAddRows=false;
			row.getCell(DESC_COL).setEditor(editorString);

			if (dataType == DataTypeEnum.STRING) {
				int height = row.getHeight();
				int lentgh1 = entryInfo.getContent() != null ? entryInfo
						.getContent().length() : 0;
				int lentgh2 = entryInfo.getDesc() != null ? entryInfo.getDesc()
						.length() : 0;
				int heightsize = lentgh1 / 75 > lentgh2 / 125 ? lentgh1 / 75
						: lentgh2 / 125;

				row.setHeight(height * (1 + heightsize));
			}
			if(STATUS_EDIT.equals(getOprtState())){
				//�ڱ���ʱ���Ѿ��еĺ�ͬ��ϸ��Ϣ�� detailColls ���Ƴ���detailColls ��ʣ��ľ�����Ҫ����׷�ӵ����β���ļ�¼�ˡ�
				if(detailColls!=null&&detailColls.size()>0){
					for(int j = 0;j<detailColls.size();j++){
						ContractDetailDefInfo detail = (ContractDetailDefInfo)detailColls.get(j);
						String detailId = detail.getId().toString();
						if (!StringUtils.isEmpty(entryInfo.getDetailDefID())) {
							if(entryInfo.getDetailDefID().equals(detailId)){
								detailColls.remove(detail);
							}
						}
					}
				}
			}
		}
		//׷�ӵ����β��
		if (STATUS_EDIT.equals(getOprtState())) {
			if (detailColls != null && detailColls.size() > 0) {
				for (Iterator iter = detailColls.iterator(); iter.hasNext();) {
					ContractDetailDefInfo detail = (ContractDetailDefInfo) iter.next();
					fillConDetails(editorString, detail);
				}
			}

			if (!lastDispAddRows) {
				fillDetailByPropert(editData.getContractPropert(),editData.getContractType());
			}
			if (isUseAmtWithoutCost && mainContractId != null) {
				this.comboCurrency.setEnabled(false);
			}

			detailTableAutoFitRowHeight();
		}
	}
	private void fillConDetails(KDTDefaultCellEditor editorString, ContractDetailDefInfo detail) {
		IRow appendRow = this.tblDetail.addRow();
		appendRow.getCell(DETAIL_COL).setValue(detail.getName());
		KDTDefaultCellEditor _editor = getEditorByDataType(detail
				.getDataTypeEnum());
		if (_editor != null) {
			appendRow.getCell(CONTENT_COL).setEditor(_editor);
		}
		if (detail.getDataTypeEnum() == DataTypeEnum.DATE) {
			appendRow.getCell(CONTENT_COL).setValue(
					FDCHelper.getCurrentDate());
			appendRow.getCell(CONTENT_COL).getStyleAttributes()
					.setNumberFormat("%r{yyyy-M-d}t");
		} else if (detail.getDataTypeEnum() == DataTypeEnum.NUMBER) {
			appendRow.getCell(CONTENT_COL).getStyleAttributes()
					.setHorizontalAlign(HorizontalAlignment.RIGHT);
		}
		appendRow.getCell(DATATYPE_COL).setValue(
				detail.getDataTypeEnum());
		appendRow.getCell(DETAIL_DEF_ID).setValue(
				detail.getId().toString());

		appendRow.getCell(DESC_COL).setEditor(editorString);

		// �����ͬ��ϸ����Ϊ��¼��򱳾�ɫҪ��ʾΪ��¼ɫ added by owen_wen 2010-09-10
		if (detail.isIsMustInput()) {
			this.setRequiredBGColor(appendRow);
		}
	}
	private ContractDetailDefCollection getConDetailsDef() {
		String conTypeId = this.editData.getContractType().getId().toString();
		SelectorItemCollection selector = new SelectorItemCollection();
		selector.add(new SelectorItemInfo("id"));
		selector.add(new SelectorItemInfo("dataTypeEnum"));
		selector.add(new SelectorItemInfo("isMustInput"));
		selector.add(new SelectorItemInfo("name"));
		selector.add(new SelectorItemInfo("number"));
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("contractType.id",conTypeId));
		filter.getFilterItems().add(new FilterItemInfo("isEnabled",Boolean.TRUE));
		EntityViewInfo view = new EntityViewInfo();
		view.setFilter(filter);
		view.setSelector(selector);
		ContractDetailDefCollection detailColls = null;
		try {
			detailColls = ContractDetailDefFactory.getRemoteInstance().getContractDetailDefCollection(view);
		} catch (BOSException e2) {
			e2.printStackTrace();
		}
		return detailColls;
	}

	protected void setFieldsNull(AbstractObjectValue newData) {
		super.setFieldsNull(newData);
		ContractBillInfo info = (ContractBillInfo) newData;
//		info.setId(BOSUuid.create(info.getBOSType()));
		info.setCurProject(editData.getCurProject());
		info.setContractType(editData.getContractType());
		info.setIsArchived(false);
		
		info.setProgrammingContract(null);
		this.textFwContract.setText(null);
	}

	/*
	 * ���ӱ༭�ؼ��Ľ�����
	 */
	protected void unLockUI() {
		super.unLockUI();
		getDetailInfoTable().getStyleAttributes().setLocked(false);
		chkCostSplit.setEnabled(true);
		chkIsPartAMaterialCon.setEnabled(true);
		/*
		 * ԭ�ҽ���Ƿ��д���Ƿ�ʹ�ò��Ƴɱ��Ľ��������
		 */
		int rowIndex = getRowIndexByRowKey(AM_ROW);
		IRow row = getDetailInfoTable().getRow(rowIndex);
		if (row != null) {
			ICell cell = row.getCell(CONTENT_COL);
			if (isUseAmtWithoutCost) {
				if (rowIndex > 0)
					cell.getStyleAttributes().setLocked(false);
				txtamount.setEditable(false);
			} else {
				if (rowIndex > 0)
					cell.getStyleAttributes().setLocked(true);
				txtamount.setEditable(true);
			}
		}

		int conNameRowIdx = getRowIndexByRowKey(NA_ROW);

		if (conNameRowIdx > 0) {
			getDetailInfoTable().getRow(conNameRowIdx).getCell(CONTENT_COL)
					.getStyleAttributes().setLocked(true);
		}
	}

	protected void lockUIForViewStatus() {
		super.lockUIForViewStatus();
		chkCostSplit.setEnabled(false);
		chkIsPartAMaterialCon.setEnabled(false);
	}

	public void actionAddNew_actionPerformed(ActionEvent e) throws Exception {
		super.actionAddNew_actionPerformed(e);
		prmtcontractType.setEnabled(true);
		kDDateCreateTime.setEnabled(false);
		comboCurrency.setEnabled(true);
		prmtModel.setEnabled(true);
		getDetailInfoTable().removeRows();
		ContractTypeInfo cType = (ContractTypeInfo) prmtcontractType.getData();
		if (cType != null) {				
			fillDetailByContractType(cType.getId().toString());
		}

		lastDispAddRows = false;
	}

	public void actionEdit_actionPerformed(ActionEvent e) throws Exception {
		Object obj = txtExRate.getValue();

		//�ú�ͬ�Ѿ������˲�֣����ܽ����޸�
//		if (editData.getSplitState() != null
//				&& !CostSplitStateEnum.NOSPLIT.equals(editData.getSplitState())) {
//			MsgBox.showWarning(this, "�ú�ͬ�Ѿ������˲�֣����ܽ����޸�");
//			SysUtil.abort();
//		}

		super.actionEdit_actionPerformed(e);
		txtCreator.setEnabled(false);
		kDDateCreateTime.setEnabled(false);
		//		û���������(Ҳ�����鵵����Ϊ�鵵ǰ�����������)�ĺ�ͬ�ĺ�ͬ���Ϳ����޸ģ���ͬ����Ҳ�����޸�
		if (STATUS_EDIT.equals(getOprtState())) {
			if ((this.editData.getState()
					.equals(com.kingdee.eas.fdc.basedata.FDCBillStateEnum.AUDITTED_VALUE))) {
				//				prmtcontractType.setEnabled(false);
				actionSplit.setEnabled(true);
			} else {
				prmtcontractType.setEnabled(true);
				//				actionSplit.setEnabled(true);
			}
		}
		comboCurrency_itemStateChanged(null);

		if (obj != null) {
			txtExRate.setValue(obj);
		}
		if (isUseAmtWithoutCost && mainContractId != null) {
			this.comboCurrency.setEnabled(false);
		}
		
		if(editData.isIsSubContract()){
			this.prmtMainContract.setEnabled(true);
		}
		ContractBillEntryCollection coll = editData.getEntrys();
		if (coll == null || coll.size() == 0) {
			return;
		}

		KDTDefaultCellEditor editorString = getEditorByDataType(DataTypeEnum.STRING);
		getDetailInfoTable().getColumn(DESC_COL).getStyleAttributes().setWrapText(true);
		getDetailInfoTable().getColumn(CONTENT_COL).getStyleAttributes().setWrapText(true);
		int size = coll.size();
		
		ContractDetailDefCollection detailColls = getConDetailsDef();
		ContractBillEntryInfo entryInfo = null;
		for (int i = 0; i < size; i++) {
			entryInfo = (ContractBillEntryInfo) coll.get(i);
			// �ڱ���ʱ���Ѿ��еĺ�ͬ��ϸ��Ϣ�� detailColls ���Ƴ���detailColls
			// ��ʣ��ľ�����Ҫ����׷�ӵ����β���ļ�¼�ˡ�
			if (detailColls != null && detailColls.size() > 0) {
				for (int j = 0; j < detailColls.size(); j++) {
					ContractDetailDefInfo detail = (ContractDetailDefInfo) detailColls
							.get(j);
					String detailId = detail.getId().toString();
					if (!StringUtils.isEmpty(entryInfo.getDetailDefID())) {
						if (entryInfo.getDetailDefID().equals(detailId)) {
							detailColls.remove(detail);
						}
					}
				}
			}
		}
	//׷�ӵ����β��
		if (STATUS_EDIT.equals(getOprtState())) {
			if (detailColls != null && detailColls.size() > 0) {
				for (Iterator iter = detailColls.iterator(); iter.hasNext();) {
					ContractDetailDefInfo detail = (ContractDetailDefInfo) iter
							.next();
					fillConDetails(editorString, detail);
				}
			}
		}
		if(prmtModel.getValue()!= null){
			this.prmtModel.setEnabled(false);
		}
		if (editData.getContractPropert() == ContractPropertyEnum.SUPPLY) {
			ICell loCell = getDetailInfoTable().getRow(getRowIndexByRowKey(LO_ROW)).getCell(CONTENT_COL);
			txtamount.setEditable(false);
			txtGrtRate.setRequired(false);
			txtGrtRate.setEditable(false);
			txtGrtAmount.setRequired(false);
			txtGrtAmount.setEditable(false);

			setAmountRequired(false);

			isUseAmtWithoutCost = true;
			loCell.getStyleAttributes().setLocked(true);
			this.txtGrtRate.setEditable(false);
		}
		
		if (this.contractPropert.getSelectedItem() == ContractPropertyEnum.DIRECT) {
			this.txtamount.setEnabled(true);
		}else if (this.contractPropert.getSelectedItem() == ContractPropertyEnum.SUPPLY) {
		}else if (this.contractPropert.getSelectedItem() == ContractPropertyEnum.THREE_PARTY) {
			this.txtamount.setEnabled(true);
		}else if (this.contractPropert.getSelectedItem() == ContractPropertyEnum.STRATEGY) {
			this.txtamount.setEnabled(false);
		}
		fillDetailByPropert((ContractPropertyEnum) this.contractPropert.getSelectedItem(),(ContractTypeInfo) this.prmtcontractType.getValue());
		this.txtNumber.setEnabled(false);
		if(this.editData.getOaState()!=null&&this.editData.getOaState().equals("1")){
			this.prmtcontractType.setEnabled(false);
			this.txtcontractName.setEnabled(false);
			this.prmtlandDeveloper.setEnabled(false);
			this.prmtpartB.setEnabled(false);
			this.prmtpartC.setEnabled(false);
			this.contractPropert.setEnabled(false);
			this.comboCurrency.setEnabled(false);
			this.txtamount.setEnabled(false);
			this.txtGrtRate.setEnabled(false);
			this.pkbookedDate.setEnabled(false);
			this.txtGrtAmount.setEnabled(false);
			this.txtchgPercForWarn.setEnabled(false);
			this.contractSource.setEnabled(false);
			this.pkStartDate.setEnabled(false);
			this.pkEndDate.setEnabled(false);
			this.prmtRespPerson.setEnabled(false);
			this.prmtContractWFType.setEnabled(false);
			this.prmtRespDept.setEnabled(false);
			this.cbOrgType.setEnabled(false);
			this.prmtCreateOrg.setEnabled(false);
			this.txtDes.setEnabled(false);
			this.prmtLxNum.setEnabled(false);
			this.txtBank.setEnabled(false);
			this.txtBankAccount.setEnabled(false);
			this.cbTaxerQua.setEnabled(false);
			this.txtTaxerNum.setEnabled(false);
			this.txtNumber.setEnabled(false);
			this.prmtTAEntry.setEnabled(false);
			this.prmtMarketProject.setEnabled(false);
			this.prmtMpCostAccount.setEnabled(false);
			this.prmtinviteType.setEnabled(false);
			
			this.actionDetailALine.setEnabled(false);
			this.actionDetailILine.setEnabled(false);
			this.actionDetailRLine.setEnabled(false);
			this.kdtDetailEntry.setEnabled(false);
			this.actionYZALine.setEnabled(false);
			this.actionYZRLine.setEnabled(false);
			this.kdtYZEntry.setEnabled(false);
			this.cbJzType.setEnabled(false);
			this.pkJzStartDate.setEnabled(false);
			this.pkJzEndDate.setEnabled(false);
			this.tblMarket.setEnabled(false);
			this.tblDetail.setEnabled(false);
		}
	}

	public void actionSplit_actionPerformed(ActionEvent e) throws Exception {
		//super.actionSplit_actionPerformed(e);
		
		if (getSelectBOID() == null)
			return;
		//boolean isCostSplit=chkCostSplit.isSelected();
		//��ͬ���		jelon 12/30/2006
		String contrBillID = getSelectBOID();

		AbstractSplitInvokeStrategy invokeStra = new ContractSplitInvokeStrategy(
				contrBillID, this);
		invokeStra.invokeSplit();
	}

	public void actionDelSplit_actionPerformed(ActionEvent e) throws Exception {
		checkConInWF();
		//super.com
		if (getSelectBOID() == null)
			return;

		ContractBillInfo costBillInfo = null;
		//��ͬ���		jelon 12/30/2006
		String contrBillID = getSelectBOID();

		SelectorItemCollection selectors = new SelectorItemCollection();

		selectors.add("id");
		selectors.add("splitState");

		costBillInfo = ContractBillFactory.getRemoteInstance()
				.getContractBillInfo(
						new ObjectUuidPK(BOSUuid.read(contrBillID)), selectors);

		//�ú�ͬ�Ѿ������˲�֣����ܽ����޸�
		if (costBillInfo.getSplitState() == null
				|| CostSplitStateEnum.NOSPLIT.equals(costBillInfo
						.getSplitState())) {
			MsgBox.showWarning(this, "�ú�ͬ��û�в��");
			SysUtil.abort();
		}

		if (confirmRemove()) {
			FilterInfo filter = new FilterInfo();
			filter.getFilterItems().add(
					new FilterItemInfo("contractBill.id", contrBillID));
			EntityViewInfo view = new EntityViewInfo();
			view.setFilter(filter);
			view.getSelector().add("id");

			ContractCostSplitCollection col = ContractCostSplitFactory
					.getRemoteInstance().getContractCostSplitCollection(view);
			IObjectPK[] pks = new IObjectPK[col.size()];
			for (int i = 0; i < col.size(); i++) {
				pks[i] = new ObjectUuidPK(col.get(i).getId().toString());
			}

			ContractCostSplitFactory.getRemoteInstance().delete(pks);

			ConNoCostSplitCollection nocol = ConNoCostSplitFactory
					.getRemoteInstance().getConNoCostSplitCollection(view);
			IObjectPK[] nopks = new IObjectPK[nocol.size()];
			for (int i = 0; i < nocol.size(); i++) {
				nopks[i] = new ObjectUuidPK(nocol.get(i).getId().toString());
			}

			ConNoCostSplitFactory.getRemoteInstance().delete(nopks);
			//ContractCostSplitFactory.getRemoteInstance().delete(filter);
			//ConNoCostSplitFactory.getRemoteInstance().delete(filter);	
		}
	}

	private void checkConInWF() {
		if (editData == null || editData.getSplitState() == null)
			return;
		//�Ƿ�������ǰ���
		try {
			if (splitBeforeAudit
					&& FDCUtils.isRunningWorkflow(editData.getId().toString())
					&& CostSplitStateEnum.ALLSPLIT.equals(editData
							.getSplitState())) {
				MsgBox.showWarning(this, "��ͬ�ڹ�����������ɾ����ͬ��֣�");
				SysUtil.abort();
			}
		} catch (BOSException e) {

			e.printStackTrace();
		}
	}

	public void showSplitUI(CoreUIObject parentUI, String billId,
			String billPropName, String bosType, boolean isCostSplit,BigDecimal amount)
			throws BOSException, UIException {
		String splitBillID = null;

		FDCBillInfo billInfo = null;
		CoreBaseCollection coll = null;

		String editName = null;

		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo(billPropName, billId));
		view.setFilter(filter);
		view.getSelector().add("id");
		view.getSelector().add("state");

		SelectorItemCollection selectors = new SelectorItemCollection();
		selectors.add("id");
		if (isCostSplit) {
			// coll = ContractCostSplitFactory.getRemoteInstance().
			// getContractCostSplitCollection(view);
			editName = com.kingdee.eas.fdc.contract.client.ContractCostSplitEditUI.class.getName();
			coll = ContractCostSplitFactory.getRemoteInstance().getCollection(view);
		} else {
			editName = com.kingdee.eas.fdc.contract.client.ConNoCostSplitEditUI.class.getName();
			coll = ConNoCostSplitFactory.getRemoteInstance().getCollection(view);
		}

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

		UIContext uiContext = new UIContext(parentUI);
		String oprtState;

		if (isSplited) {
			uiContext.put(UIContext.ID, splitBillID);

			if (isAudited) {
				oprtState = OprtState.VIEW;
			} else {
				oprtState = OprtState.EDIT;
			}
		} else {
			uiContext.put("costBillID", billId);
			oprtState = OprtState.ADDNEW;
		}
		uiContext.put("amount", amount);
		IUIWindow uiWin = UIFactory.createUIFactory(UIFactoryName.MODEL)
				.create(editName, uiContext, null, oprtState);

		uiWin.show();
	}

	//���þ���
	protected void setPrecision() {
		CurrencyInfo currency = editData.getCurrency();
		Date bookedDate = (Date) editData.getBookedDate();

		ExchangeRateInfo exchangeRate = null;
		try {
			exchangeRate = FDCClientHelper.getLocalExRateBySrcCurcy(this,
					currency.getId(), company, bookedDate);
		} catch (Exception e) {
			e.printStackTrace();
			txtExRate.setPrecision(2);
			return;
		}

		int curPrecision = FDCClientHelper.getPrecOfCurrency(currency.getId());
		int exPrecision = curPrecision;

		if (exchangeRate != null) {
			exPrecision = exchangeRate.getPrecision();
		}

		txtExRate.setPrecision(exPrecision);
		BigDecimal exRate = editData.getExRate();
		txtExRate.setValue(exRate);
		txtamount.setPrecision(curPrecision);
		txtamount.setValue(editData.getOriginalAmount());
	}

	protected void comboCurrency_itemStateChanged(ItemEvent e) throws Exception {

		super.comboCurrency_itemStateChanged(e);
		if (STATUS_VIEW.equals(getOprtState())
				|| STATUS_FINDVIEW.equals(getOprtState()))
			return;
		if (e == null || e.getStateChange() == ItemEvent.SELECTED) {
			CurrencyInfo currency = (CurrencyInfo) this.comboCurrency
					.getSelectedItem();
			//CurrencyInfo baseCurrency = FDCClientHelper.getBaseCurrency(SysContext.getSysContext().getCurrentFIUnit());
			Date bookedDate = (Date) pkbookedDate.getValue();

			ExchangeRateInfo exchangeRate = FDCClientHelper
					.getLocalExRateBySrcCurcy(this, currency.getId(), company,
							bookedDate);

			int curPrecision = FDCClientHelper.getPrecOfCurrency(currency
					.getId());
			BigDecimal exRate = FDCHelper.ONE;
			int exPrecision = curPrecision;

			if (exchangeRate != null) {
				exRate = exchangeRate.getConvertRate();
				exPrecision = exchangeRate.getPrecision();
			}

			txtExRate.setPrecision(exPrecision);
			txtExRate.setValue(exRate);
			txtamount.setPrecision(curPrecision);

			if (baseCurrency != null
					&& baseCurrency.getId().equals(currency.getId())) {
				txtExRate.setValue(FDCConstants.ONE);
				txtExRate.setRequired(false);
				txtExRate.setEditable(false);
				txtExRate.setEnabled(false);
			} else {
				txtExRate.setEditable(true);
				txtExRate.setRequired(true);
				txtExRate.setEnabled(true);
			}
			calLocalAmt();
		}
	}

	protected void txtExRate_dataChanged(DataChangeEvent e) throws Exception {
		super.txtExRate_dataChanged(e);
		calLocalAmt();
	}
	/**
	 * ���㸶�������ԭ�ҽ����߱���   by Cassiel_peng 2009-8-26
	 */
	private void calPayItemAmt(){
		EcoItemHelper.calPayItemAmt(this.tblEconItem, this.tblBail, txtamount);
	}
	/**
	 * ������Լ��֤�𼰷������ֵ�ԭ�ҽ����߱���   by Cassiel_peng 2009-8-26
	 * ����ͬ�����ı�ʱ��Ӧ������Լ��֤���*�������� ��Զ��
	 */
	private void calBailAmt(){
	   EcoItemHelper.calBailAmt(tblBail, txtamount, txtBailOriAmount, txtBailRate);
	}
	/**
	 * ���㱾�ҽ��
	 *
	 */
	private void calLocalAmt() {
		if (txtamount.getBigDecimalValue() != null
				&& txtExRate.getBigDecimalValue() != null) {
//			BigDecimal lAmount = FDCHelper.toBigDecimal(txtamount.getBigDecimalValue(),2).multiply(
//					FDCHelper.toBigDecimal(txtExRate.getBigDecimalValue(),2));
			/*
			 * ע��1.����ʱ����������λС�����Ա��������׼ȷ
			 *     2.С�������ȡ�����������Ƚ�ʱ�������ȡС��λ
			 *     3.С��λ�����໥����ʱ�ȼ����ٽ�ȡ
			 */    
			
			BigDecimal lAmount = txtamount.getBigDecimalValue().multiply(
					txtExRate.getBigDecimalValue());
			txtLocalAmount.setNumberValue(lAmount);
		} else {
			txtLocalAmount.setNumberValue(null);
		}
		
		//��Ҫ���㱾λ�ҵĵط�������Ҫ���¼���ӡ��˰���
		calStampTaxAmt();
		
		calGrtAmt();
	}

	/**
	 * ����ӡ��˰���
	 *
	 */
	private void calStampTaxAmt() {
		
		//ʹ�ò��Ƴɱ��Ľ��
		if (isUseAmtWithoutCost) {
			int rowcount = tblDetail.getRowCount();
			Object newValue = null;
			for (int i = 0; i < rowcount; i++) {
				IRow entryRow = tblDetail.getRow(i);
				//
				if (AM_ROW.equals(entryRow.getCell(ROWKEY_COL).getValue())) {
					newValue = entryRow.getCell(CONTENT_COL).getValue();
					break;
				}
			}
			BigDecimal originalAmount = (BigDecimal) newValue;
			//����ӡ��˰
			if(originalAmount!=null&&txtExRate.getBigDecimalValue() != null&&txtStampTaxRate.getBigDecimalValue()!=null){
				BigDecimal stampTaxAmount = FDCHelper.multiply(originalAmount,txtExRate.getBigDecimalValue()).multiply(txtStampTaxRate.getBigDecimalValue());
				stampTaxAmount = stampTaxAmount.divide(FDCConstants.B100,
						BigDecimal.ROUND_HALF_UP);
				txtStampTaxAmt.setNumberValue(stampTaxAmount);
			}else{
				txtStampTaxAmt.setNumberValue(null);
			}
			return;
		}

		//��ʹ�ò��Ƴɱ��Ľ��
		if (txtamount.getBigDecimalValue() != null
				&& txtExRate.getBigDecimalValue() != null&&txtStampTaxRate.getBigDecimalValue()!=null) {
			BigDecimal stampTaxAmount = FDCHelper.multiply(txtamount.getBigDecimalValue(),txtExRate.getBigDecimalValue()).multiply(txtStampTaxRate.getBigDecimalValue());
			stampTaxAmount = stampTaxAmount.divide(FDCConstants.B100,
					BigDecimal.ROUND_HALF_UP);
			txtStampTaxAmt.setNumberValue(stampTaxAmount);
		} else {
			txtStampTaxAmt.setNumberValue(null);
		}
	}

	/**
	 * ���㱣�޽��
	 * 
	 */
	private void calGrtAmt() {

		//ʹ�ò��Ƴɱ��Ľ��,���㱣�޽��
		if (isUseAmtWithoutCost) {
			int rowcount = tblDetail.getRowCount();
			Object newValue = null;
			for (int i = 0; i < rowcount; i++) {
				IRow entryRow = tblDetail.getRow(i);
				//
				if (AM_ROW.equals(entryRow.getCell(ROWKEY_COL).getValue())) {
					newValue = entryRow.getCell(CONTENT_COL).getValue();
					break;
				}
			}

			//���㱣�޽��
			BigDecimal originalAmount = (BigDecimal) newValue;
			if (originalAmount != null && txtExRate.getBigDecimalValue() != null) {
				originalAmount = originalAmount.multiply(txtExRate.getBigDecimalValue());
			}
			BigDecimal grtAmount = originalAmount.multiply(txtGrtRate
					.getBigDecimalValue());
			grtAmount = grtAmount.divide(FDCConstants.B100,
					BigDecimal.ROUND_HALF_UP);
			txtGrtAmount.setNumberValue(grtAmount);

			return;
		}

		//��ʹ�ò��Ƴɱ��Ľ��
		if (txtamount.getBigDecimalValue() != null
				&& txtGrtRate.getBigDecimalValue() != null && txtExRate.getBigDecimalValue() != null) {
			BigDecimal grtAmount = txtamount.getBigDecimalValue().multiply(txtExRate.getBigDecimalValue()).multiply(
					txtGrtRate.getBigDecimalValue());
			grtAmount = grtAmount.divide(FDCConstants.B100,
					BigDecimal.ROUND_HALF_UP);
			txtGrtAmount.setNumberValue(grtAmount);
		} else {
			txtGrtAmount.setNumberValue(null);
		}
	}

	/**
	 * ������ ��ͬ���<br>
	 * 
	 * <pre>
	 *       	 isUseAmtWithoutCost true 
	 *       				ȡ���Ƴɱ��Ľ�� 
	 *            isUseAmtWithoutCost false
	 *                    ��ʹ�ò��Ƴɱ��Ľ��
	 *   @return BigDecimal
	 * 
	 */
	private BigDecimal getAmount() {

		if (isUseAmtWithoutCost) {
			int rowcount = tblDetail.getRowCount();
			Object newValue = null;
			for (int i = 0; i < rowcount; i++) {
				IRow entryRow = tblDetail.getRow(i);
				if (AM_ROW.equals(entryRow.getCell(ROWKEY_COL).getValue())) {
					newValue = entryRow.getCell(CONTENT_COL).getValue();
					break;
				}
			}
			return FDCHelper.toBigDecimal(newValue);
		} else {
			return FDCHelper.toBigDecimal(txtamount.getBigDecimalValue());
		}
	}

	/**
	 * ������R090112-050���޽���Ǹ��ݱ��޽�����Զ�����ģ�����ֱ��¼�롣
	 * <p>
	 * ҵ���еı��޽���е�ʱ�򲻸��ݱ����ƶ�������ֱ��¼�뱣�޽��ٵ��㱣�޽����
	 * 
	 * @author pengwei_hou Date:2009-04-10
	 * @param flag
	 * @param e
	 */
	private void setGrtAmt(String flag, DataChangeEvent e) {
		BigDecimal amount = getAmount();
		if (amount != null && txtExRate.getBigDecimalValue() != null) {
			amount = amount.multiply(txtExRate.getBigDecimalValue());
		}
		if(FDCHelper.toBigDecimal(txtGrtAmount.getBigDecimalValue()).compareTo(amount) > 0){
			String msg = "���޽���� ";
			if (isUseAmtWithoutCost) {
				msg = msg + "��ͬ����";
			} else {
				msg  = msg + "�����ͬ����";
			}
			MsgBox.showWarning(this, msg);
			txtGrtAmount.setValue(FDCHelper.ZERO);
			SysUtil.abort();
		}
		if (flag.equals("txtGrtAmount")) {

			DataChangeListener[] listeners = (DataChangeListener[]) txtGrtRate
					.getListeners(DataChangeListener.class);
			for (int i = 0; i < listeners.length; i++) {
				txtGrtRate.removeDataChangeListener(listeners[i]);
			}
			BigDecimal grtAmount = FDCHelper.toBigDecimal(txtGrtAmount.getBigDecimalValue());
			BigDecimal grtRate = FDCHelper.ZERO;
			if (FDCHelper.ZERO.compareTo(amount) != 0) {
				grtRate = grtAmount.multiply(FDCHelper.ONE_HUNDRED).divide(amount, 6, BigDecimal.ROUND_HALF_UP);
				txtGrtRate.setValue(grtRate);
			}
			for (int i = 0; i < listeners.length; i++) {
				txtGrtRate.addDataChangeListener(listeners[i]);
			}
		} else {
			DataChangeListener[] listeners = (DataChangeListener[]) txtGrtAmount
					.getListeners(DataChangeListener.class);
			for (int i = 0; i < listeners.length; i++) {
				txtGrtAmount.removeDataChangeListener(listeners[i]);
			}

			BigDecimal grtRate = FDCHelper.toBigDecimal(txtGrtRate.getBigDecimalValue());
			BigDecimal grtAmount = grtRate.multiply(amount).divide(FDCHelper.ONE_HUNDRED, 2,
					BigDecimal.ROUND_HALF_UP);
			txtGrtAmount.setValue(grtAmount);

			for (int i = 0; i < listeners.length; i++) {
				txtGrtAmount.addDataChangeListener(listeners[i]);
			}
		}
	}

	protected void txtStampTaxRate_dataChanged(DataChangeEvent e) throws Exception {
		calStampTaxAmt();
	}

	protected void txtamount_dataChanged(DataChangeEvent e) throws Exception {
		calLocalAmt();
		calStampTaxAmt();
		calPayItemAmt();//by Cassiel_peng
		calBailAmt();

		setCapticalAmount();
		
//		for(int i=0;i<this.tblMarket.getRowCount();i++){
//			 IRow r = this.tblMarket.getRow(i);
//			 r.getCell("amount").setValue(FDCHelper.divide(FDCHelper.multiply(this.txtLocalAmount.getBigDecimalValue(), r.getCell("rate").getValue()), new BigDecimal(100), 2, BigDecimal.ROUND_HALF_UP));
//		}
		setMarketEntry();
	}

	protected void txtGrtRate_dataChanged(DataChangeEvent e) throws Exception {
		calLocalAmt();
		//    	calGrtAmt();
	}

	protected void verifyInputForSave() throws Exception {

		FDCClientVerifyHelper.verifyEmpty(this, prmtcontractType);
		
		this.verifyInputForContractDetail();
		
		super.verifyInputForSave();

		FDCClientVerifyHelper.verifyEmpty(this, txtcontractName);
	
		checkProjStatus();
		
		if(editData.getInformation() != null && editData.getInformation().length() > 255){
			FDCMsgBox.showError("��ͬժҪ��Ϣ����ϵͳԼ������(255)��");
			abort();
		}
		
		if(this.prmtMainContract.isRequired()){
			if(prmtMainContract.getData() == null){
				FDCMsgBox.showError("¼��ս���Ӻ�ͬʱ��ս������ͬ���벻��Ϊ�գ�");
				abort();
			}
		}
		
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
//			BigDecimal comAmount=FDCHelper.ZERO;
//			ContractBillCollection col=null;
//			if(editData.getId()!=null){
//				col=ContractBillFactory.getRemoteInstance().getContractBillCollection("select amount from where mpCostAccount.id='"+cinfo.getId()+"' and marketProject.id='"+info.getId()+"' and id!='"+this.editData.getId()+"'");
//			}else{
//				col=ContractBillFactory.getRemoteInstance().getContractBillCollection("select amount from where mpCostAccount.id='"+cinfo.getId()+"' and marketProject.id='"+info.getId()+"'");
//			}
//			for(int i=0;i<col.size();i++){
//				comAmount=FDCHelper.add(comAmount,col.get(i).getAmount());
//			}
//			
//			ContractWithoutTextCollection wtCol=ContractWithoutTextFactory.getRemoteInstance().getContractWithoutTextCollection("select amount from where mpCostAccount.id='"+cinfo.getId()+"' and marketProject.id='"+info.getId()+"'");
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
			String	paramValue = ParamControlFactory.getRemoteInstance().getParamValue(new ObjectUuidPK(SysContext.getSysContext().getCurrentOrgUnit().getId()), "YF_MARKETPROJECT");
			if(this.txtLocalAmount.getBigDecimalValue().compareTo(subAmount)>0){
				if("false".equals(paramValue)){
					if(FDCMsgBox.showConfirm2(this,"��ͬ��������ʣ�����ȷ���Ƿ񱣴棿") == FDCMsgBox.CANCEL){
						SysUtil.abort();
					}
				}else{
					FDCMsgBox.showWarning(this,"��ͬ��������ʣ���");
					SysUtil.abort();
				}
			}
		}
	}

	public void actionViewCost_actionPerformed(ActionEvent e) throws Exception {
		String selectBOID = getSelectBOID();
		if (selectBOID == null) {
			MsgBox.showWarning(this, "��ͬ��δ����");
			SysUtil.abort();
		}

		UIContext uiContext = new UIContext(this);
		uiContext.put(UIContext.ID, selectBOID);
		uiContext.put(FDCConstants.FDC_INIT_PROJECT, curProject);

		ContractCostInfoUI.showEditUI(this, uiContext, getOprtState());
	}

	protected void tblDetail_editStopping(
			com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent evt)
			throws Exception {
		KDTable table = (KDTable) evt.getSource();
		IRow entryRow = table.getRow(evt.getRowIndex());
		//ICell cell = entryRow.getCell(evt.getColIndex());

		Object newValue = evt.getValue();
		IColumn col = table.getColumn(evt.getColIndex());
		String colKey = col.getKey();

		//���ݷ����޸�
		if (colKey.equals("content") && (newValue instanceof BigDecimal)
				&& txtStampTaxRate.getBigDecimalValue() != null && txtExRate.getBigDecimalValue() != null) {

			if (isUseAmtWithoutCost && AM_ROW.equals(entryRow.getCell(ROWKEY_COL).getValue())) {
				//
				if (mainContractId != null) {
					MsgContractHasSplit();
				}
				detailAmount = (BigDecimal) newValue;
				// ����ӡ��˰
				BigDecimal originalAmount = (BigDecimal) newValue;
				if (originalAmount != null && txtExRate.getBigDecimalValue() != null) {
					originalAmount = originalAmount.multiply(txtExRate.getBigDecimalValue());
				}
				BigDecimal stampTaxAmount = originalAmount.multiply(txtStampTaxRate.getBigDecimalValue());
				stampTaxAmount = stampTaxAmount.divide(FDCConstants.B100, BigDecimal.ROUND_HALF_UP);
				txtStampTaxAmt.setNumberValue(stampTaxAmount);

				// ���㱣�޽��
				BigDecimal grtAmount = originalAmount.multiply(txtGrtRate.getBigDecimalValue());
				grtAmount = grtAmount.divide(FDCConstants.B100, BigDecimal.ROUND_HALF_UP);
				txtGrtAmount.setNumberValue(grtAmount);
			}
		}
		if (colKey.equals("content")&&NU_ROW.equals(entryRow.getCell(ROWKEY_COL).getValue())&&ContractPropertyEnum.SUPPLY.equals(this.contractPropert.getSelectedItem())){
			if(newValue==null){
				this.prmtlandDeveloper.setValue(null);
				this.prmtpartB.setValue(null);
				
				this.prmtLxNum.setValue(null);
				this.txtBank.setText(null);
				this.txtBankAccount.setText(null);
				this.cbTaxerQua.setSelectedItem(null);
				this.txtTaxerNum.setText(null);
				this.prmtTAEntry.setValue(null);
			}else{
				ContractBillInfo contractBillInfo=(ContractBillInfo)newValue;
				if(contractBillInfo.getLandDeveloper()!=null){
					this.prmtlandDeveloper.setValue(LandDeveloperFactory.getRemoteInstance().getLandDeveloperInfo(new ObjectUuidPK(contractBillInfo.getLandDeveloper().getId())));
				}
				if(contractBillInfo.getPartB()!=null){
					this.prmtpartB.setValue(SupplierFactory.getRemoteInstance().getSupplierInfo(new ObjectUuidPK(contractBillInfo.getPartB().getId())));
				}
				contractBillInfo =ContractBillFactory.getRemoteInstance().getContractBillInfo(new ObjectUuidPK(contractBillInfo.getId()));
				if(contractBillInfo.getLxNum()!=null){
					this.prmtLxNum.setValue(BankNumFactory.getRemoteInstance().getBankNumInfo(new ObjectUuidPK(contractBillInfo.getLxNum().getId())));
					this.prmtLxNum.setEnabled(false);
				}else{
					this.prmtLxNum.setValue(null);
					this.prmtLxNum.setEnabled(true);
				}
				this.txtBank.setText(contractBillInfo.getBank());
				if(contractBillInfo.getBankAccount()!=null){
					this.txtBankAccount.setText(contractBillInfo.getBankAccount());
					this.txtBankAccount.setEnabled(false);
				}else{
					this.txtBankAccount.setText(null);
					this.txtBankAccount.setEnabled(true);
				}
				if(contractBillInfo.getTaxerQua()!=null){
					this.cbTaxerQua.setSelectedItem(contractBillInfo.getTaxerQua());
					this.cbTaxerQua.setEnabled(false);
				}else{
					this.cbTaxerQua.setSelectedItem(null);
					this.cbTaxerQua.setEnabled(true);
				}
				if(contractBillInfo.getTaxerNum()!=null){
					this.txtTaxerNum.setText(contractBillInfo.getTaxerNum());
					this.txtTaxerNum.setEnabled(false);
				}else{
					this.txtTaxerNum.setText(null);
					this.txtTaxerNum.setEnabled(true);
				}
				if(contractBillInfo.getTaEntry()!=null){
					this.prmtTAEntry.setValue(TenderAccepterResultEntryFactory.getRemoteInstance().getTenderAccepterResultEntryInfo(new ObjectUuidPK(contractBillInfo.getTaEntry().getId())));
				}
			}
		}
		CurProjectInfo project=CurProjectFactory.getRemoteInstance().getCurProjectInfo(new ObjectUuidPK(this.editData.getCurProject().getId()));
		if (colKey.equals("content")&&"�Ƿ�ʹ�õ�����".equals(entryRow.getCell(DETAIL_COL).getValue())){
			if(project.isIsOA()){
				if(newValue==null||(newValue!=null&&(((BooleanEnum)newValue).equals(BooleanEnum.TRUE)))){
					this.kdtYZEntry.removeRows();
					this.actionYZALine.setEnabled(false);
					this.actionYZRLine.setEnabled(false);
				}else{
					this.actionYZALine.setEnabled(true);
					this.actionYZRLine.setEnabled(true);
				}
			}else{
				this.kdtYZEntry.removeRows();
				this.actionYZALine.setEnabled(false);
				this.actionYZRLine.setEnabled(false);
			}
		}
	}

	public void MsgContractHasSplit() throws EASBizException, BOSException {
		if (this.checkContractHasSplit(mainContractId))
			MsgBox.showInfo(this,
					"�÷ǵ�������Ĳ����ͬ����������׷�ӵ���Ӧ����ͬǩԼ����У�������ͬ����ѱ���������ɾ���������ϸú�ͬ��֡�");
	}

	/**
	 * output actionRemove_actionPerformed
	 */
	public void actionRemove_actionPerformed(ActionEvent e) throws Exception {
		checkBeforeRemove();
		if(isRelatedTask()){
			checkConRelatedTaskDelUnAudit();
		}
		super.actionRemove_actionPerformed(e);
	}

	protected void checkBeforeRemove() throws Exception {
		//��鵥���Ƿ��ڹ�������
		FDCClientUtils.checkBillInWorkflow(this, getSelectBOID());
//		EntityViewInfo view = new EntityViewInfo();
//		FilterInfo filter = new FilterInfo();
//		filter.getFilterItems().add(new FilterItemInfo("contractBill", editData.getId()));
//		view.setFilter(filter);
//		view.getSelector().add("id");
//		CoreBillBaseCollection coll = ContractCostSplitFactory.getRemoteInstance()
//				.getCoreBillBaseCollection(view);
//		Iterator iter = coll.iterator();
//		if (iter.hasNext()) {
//			MsgBox.showWarning(this, "����ɾ����Ӧ��ͬ��֣�");
//			SysUtil.abort();
//		}
	}

	public void actionCopy_actionPerformed(ActionEvent e) throws Exception {
		//��ȡ��ǰ���ݵ����β���
		Object obj = editData.get("respDept");
		AdminOrgUnitInfo adminOrgInfo = null; 
		if(obj !=null){
			adminOrgInfo = (AdminOrgUnitInfo)obj;
		}
		
		super.actionCopy_actionPerformed(e);
		chkCostSplit.setEnabled(true);
		editData.setSplitState(null);
		this.pkbookedDate.setValue(this.bookedDate);
		editData.setSourceType(SourceTypeEnum.ADDNEW);
		//this.cbPeriod.setValue(this.curPeriod);
		
		//��ȡ��ǰ�û���Ȩ����֯
		Set orgsSet = FDCUtils.getAuthorizedOrgs(null);
		//�����ǰ�û�û�����β�����֯��Ȩ�ޣ�Ӧ����ʾΪ�ա�
		if(adminOrgInfo!=null){
			if(orgsSet==null || (orgsSet !=null && !orgsSet.contains(adminOrgInfo.getId().toString()))){
				this.prmtRespDept.setValue(null);
				editData.setRespDept(null);
			}
		}
	}

	protected void updateButtonStatus() {
		super.updateButtonStatus();

		//		 ���������ɱ����ģ����ܲ��
		if (!SysContext.getSysContext().getCurrentCostUnit().isIsBizUnit()) {
			actionSplit.setEnabled(false);
			actionSplit.setVisible(false);
		}
//		if(STATUS_ADDNEW.equals(getOprtState())){
//			prmtModel.setEnabled(false);
//		}else{
//			prmtModel.setEnabled(true);
//		}
	}
	private KDTable table=tblEconItem;
	public void actionAddLine_actionPerformed(ActionEvent e) throws Exception {
		if(STATUS_VIEW.equals(getOprtState())){
			//.....do nothing.....
		}else if((getOprtState()==STATUS_VIEW||getOprtState()==STATUS_FINDVIEW)&&this.editData.getId()!=null&&FDCUtils.isRunningWorkflow(this.editData.getId().toString())){
			//.....do nothing.....�ڹ��������ǲ�������������ɾ����¼�ģ��ʡ�����
		}
		else{
			if (this.tblEconItem.isFocusOwner()) {
				table=this.tblEconItem;
			} 
			if (this.tblBail.isFocusOwner()) {
				table=this.tblBail;
			}
			addLine(getDetailTable());
			appendFootRow(getDetailTable());
			EcoItemHelper.setPayItemRowBackColor(tblEconItem);
			EcoItemHelper.setBailRowBackColor(tblBail, txtBailOriAmount, txtBailRate);
		}
	}
	
	public void actionRemoveLine_actionPerformed(ActionEvent e)
			throws Exception {
		if(STATUS_VIEW.equals(getOprtState())){
			//.....do nothing.....
		}else if((getOprtState()==STATUS_VIEW||getOprtState()==STATUS_FINDVIEW)&&this.editData.getId()!=null&&FDCUtils.isRunningWorkflow(this.editData.getId().toString())){
			//.....do nothing.....��Ϊ�ڹ���������ʱ��Ҫ��"�鿴����"�����а�ť���ܶ��ſ�(����Ȩ�޵�ǰ����)�ʽ��������״̬����Ϊ��STATUS.EDIT..���ڹ���·���ǲ�������������ɾ����¼�ģ��ʡ�����
		}else{
			if (this.tblEconItem.isFocusOwner()) {
				this.tblEconItem.checkParsed();
				int index = -1;
				index = this.tblEconItem.getSelectManager().getActiveRowIndex();
				if (index != -1) {
					tblEconItem.removeRow(index);
				} else {
			
				}
			} else if (this.tblBail.isFocusOwner()) {
				this.tblBail.checkParsed();
				int index = -1;
				index = this.tblBail.getSelectManager().getActiveRowIndex();

				if (index != -1) {
					tblBail.removeRow(index);
					if(this.tblBail.getRowCount()<=0){
						this.txtBailOriAmount.setRequired(false);
						this.txtBailRate.setRequired(false);
					}
				} else {
					
				}
			}
		}
	}
	protected void initWorkButton() {
		super.initWorkButton();
		this.menuItemViewContent.setIcon(EASResource
				.getIcon("imgTbtn_seeperformance"));
		this.btnViewContent.setIcon(EASResource
				.getIcon("imgTbtn_seeperformance"));
		this.menuItemSplit.setIcon(FDCClientHelper.ICON_SPLIT);
		this.btnContractPlan.setIcon(EASResource
				.getIcon("imgTbtn_subjectsetting"));
		btnSplit.setIcon(FDCClientHelper.ICON_SPLIT);
		btnDelSplit.setIcon(EASResource.getIcon("imgTbtn_deleteline"));
		menuItemViewContent.setText(menuItemViewContent.getText() + "(V)");
		menuItemViewContent.setMnemonic('V');
		menuItemSplit.setText(menuItemSplit.getText() + "(S)");
		menuItemSplit.setMnemonic('S');
		actionViewBgBalance.putValue(Action.SMALL_ICON, EASResource
				.getIcon("imgTbtn_sequencecheck"));	
		btnViewCost.setIcon(EASResource.getIcon("imgTbtn_sequencecheck"));
		initEcoEntryTableStyle();

	}

	protected void checkRef(String id) throws Exception {
		super.checkRef(id);

		ContractClientUtils.checkContractBillRef(this, id);
	}

	private boolean checkContractHasSplit(String id) throws EASBizException,
			BOSException {
		FilterInfo filterSett = new FilterInfo();
		filterSett.getFilterItems().add(
				new FilterItemInfo("contractBill.id", id));
		filterSett.getFilterItems().add(
				new FilterItemInfo("state", FDCBillStateEnum.INVALID_VALUE,
						CompareType.NOTEQUALS));
		boolean hasSettleSplit = false;
		if (ContractCostSplitFactory.getRemoteInstance().exists(filterSett)
				|| ConNoCostSplitFactory.getRemoteInstance().exists(filterSett)) {
			hasSettleSplit = true;
		}
		return hasSettleSplit;
	}

	/**
	 * ���ǳ����ദ�����������Ϊ,ͳһ��FDCBillEditUI.handCodingRule�����д���
	 */
	protected void setAutoNumberByOrg(String orgType) {

	}

	protected void handleCodingRule() throws BOSException, CodingRuleException,
			EASBizException {

		if (STATUS_ADDNEW.equals(this.oprtState)||STATUS_EDIT.equals(this.oprtState)) {
			handleCodingRule1();
		}
//		if(STATUS_EDIT.equals(getOprtState())){
//			String orgId = this.orgUnitInfo.getId().toString();
//			if (orgId == null || orgId.trim().length() == 0) {
//				// ��ǰ�û�������֯������ʱ��ȱʡʵ�����ü��ŵ�
//				orgId = OrgConstants.DEF_CU_ID;
//			}
//			if(editData.getCodeType() != null){
//				ICodingRuleManager iCodingRuleManager = CodingRuleManagerFactory.getRemoteInstance();
//				if (iCodingRuleManager.isExist(editData, orgId,"codeType.number")) {
//					if(!iCodingRuleManager.isModifiable(editData, orgId,"codeType.number")){
//						this.txtNumber.setEnabled(false);
//					} 
//				}
//			}
//		}
	}

	private void handleCodingRule1() {
		String orgId = this.orgUnitInfo.getId().toString();
		String bindingProperty = "codeType.number";
		try {
			ICodingRuleManager iCodingRuleManager = CodingRuleManagerFactory.getRemoteInstance();
			if (orgId == null || orgId.trim().length() == 0) {
				// ��ǰ�û�������֯������ʱ��ȱʡʵ�����ü��ŵ�
				orgId = OrgConstants.DEF_CU_ID;
			}
			//���ñ�������
			EntityViewInfo evi = new EntityViewInfo();
			FilterInfo filter = new FilterInfo();
			ContractTypeInfo cti = editData.getContractType();
			if (editData.getContractType() != null) {
				if (cti.getLevel() != 1) {
					//ȡһ����ͬ���
					String number = editData.getContractType().getLongNumber();
					if (number.indexOf("!") != -1) {
						number = number.substring(0, number.indexOf("!"));
					}
					ContractTypeCollection ctCol=ContractTypeFactory.getRemoteInstance().getContractTypeCollection("select id where longNumber = '" + number+ "'");
					if(ctCol.size()>0){
						cti = ctCol.get(0);
					}
				}
			}
			ContractCodingTypeCollection cctc = null;
			if (cti != null) {
				filter.getFilterItems().add(new FilterItemInfo("contractType.id", cti.getId().toString()));
				filter.getFilterItems().add(new FilterItemInfo("secondType", ContractSecondTypeEnum.OLD_VALUE));//���к�ͬ,��Ϊ�����в�֪��ͬ����
				filter.getFilterItems().add(new FilterItemInfo("thirdType", ContractThirdTypeEnum.NEW_VALUE));//��ͬ��������״̬
				evi.setFilter(filter);
				cctc = ContractCodingTypeFactory.getRemoteInstance().getContractCodingTypeCollection(evi);
			}

			ContractCodingTypeInfo codingType = null;
			ContractCodingTypeInfo codingTypeAll = null;
			if (cctc != null && cctc.size() > 0)
				codingType = cctc.get(0);

			filter = new FilterInfo();
			filter.getFilterItems().add(
					new FilterItemInfo("contractType.id", null));
			//�½��Ļ������к�ͬ
			filter.getFilterItems().add(
					new FilterItemInfo("secondType",
							ContractSecondTypeEnum.OLD_VALUE));
			//����״̬
			filter.getFilterItems().add(
					new FilterItemInfo("thirdType",
							ContractThirdTypeEnum.NEW_VALUE));
			evi.setFilter(filter);
			cctc = ContractCodingTypeFactory.getRemoteInstance()
					.getContractCodingTypeCollection(evi);
			if (cctc.size() > 0)
				codingTypeAll = cctc.get(0);

			/************
			 * ���ں�ͬ�ı�����򣬿��԰��պ�ͬ�������ñ�����򣬻�����һ��"ȫ��"�ı�������
			 * ���ͬʱ������ȫ�����͵������͵ı�����򣬻��г�ͻ
			 * ���������������������δ���
			 * ����ͬ�ı�������Ϊĳһ�����������ʱ������Ĵ�����ȡ�����������Ͷ�Ӧ�ı���������û��ȡ��[˵����û�����ñ�����򣬻�û������]
			 * ����ȡһ��"ȫ��"���Ͷ�Ӧ�ı������
			 * ��: ����ϸ�ĺ�ͬ���ͣ�û�����ñ�������ʱ��ȡȫ�����Ͷ�Ӧ�ı������[���ں�ͬ�޸Ľ��棬�޸ĺ�ͬ����ʱ,���ߴ˴�]
			 */

			/****
			 * �ɱ����ģ�һ�������ж�Ӧ�ı������
			 */
			if (codingType != null) {
				editData.setCodeType(codingType);
				if (setNumber(iCodingRuleManager, orgId, bindingProperty))
					return;
			}
			/***
			 * �ɱ����ģ�ȫ�����Ͷ�Ӧ�б������
			 */
			if (codingTypeAll != null) {
				editData.setCodeType(codingTypeAll);
				if (setNumber(iCodingRuleManager, orgId, bindingProperty))
					return;
			}
			orgId = this.company.getId().toString();
			/****
			 * ������֯��һ�������ж�Ӧ�ı������
			 */
			if (codingType != null) {
				editData.setCodeType(codingType);
				if (setNumber(iCodingRuleManager, orgId, bindingProperty))
					return;
			}
			/***
			 * ������֯��ȫ�����Ͷ�Ӧ�б������
			 */
			if (codingTypeAll != null) {
				editData.setCodeType(codingTypeAll);
				if (setNumber(iCodingRuleManager, orgId, bindingProperty))
					return;
			}
			this.txtNumber.setEnabled(true);
			//��û�����ñ�����������£������number������   By zhiyuan_tang 2010/06/29
//			this.txtNumber.setText(null);

		} catch (Exception err) {
			err.printStackTrace();
			// �ѷű�������TEXT�ؼ�������Ϊ�ɱ༭״̬�����û���������
			setNumberTextEnabled();
			this.handleException(err);
		}
	}

	/**
	 * ת������number����"!"��Ϊ"."
	 * @param orgNumber
	 * @author owen_wen 2010-11-23
	 */
	private String convertNumber(String orgNumber){
		return orgNumber.replaceAll("!", ".");
	}
	
	protected boolean setNumber(ICodingRuleManager iCodingRuleManager,
			String orgId, String bindingProperty) throws CodingRuleException,
			EASBizException, BOSException {

		if (iCodingRuleManager.isExist(this.editData, orgId, bindingProperty)) {
			String number = "";
			//ɾ��NumberΪnull���ж�  ԭ��ѡ��һ���б������ĺ�ͬ���ͣ��������
			//number�ᱻ����Ϊ��ȡ���ĺ�ͬ���룬�������ٴθı��ͬ����ʱ��������н���ȡ�Ŵ���  By zhiyuan_tang 2010/06/30
//			if (editData.getNumber() == null) {
			if (iCodingRuleManager.isUseIntermitNumber(editData, orgId,
					bindingProperty)) { // ��������в�����Ϻ�					
				// ��������������ˡ��Ϻ�֧�֡����ܣ���ʱֻ�Ƕ�ȡ��ǰ���±��룬�����������ڱ���ʱ

				if (STATUS_ADDNEW.equals(this.oprtState)){ //ֻ������ʱ����Ҫȥȡnumber������Ǳ༭����Ҫ����֮ǰ��number Added by Owe_wen 2010-09-16
					number = iCodingRuleManager.readNumber(editData, orgId, bindingProperty, "");
					//��number��ֵ����caller����Ӧ�����ԣ�����TEXT�ؼ��ı༭״̬���úá�
					prepareNumber(editData, convertNumber(number));
				}
			} else if (iCodingRuleManager.isAddView(editData, orgId, bindingProperty)) { // ��������������ˡ�������ʾ��			
			//						number = iCodingRuleManager.getNumber(editData, orgId, bindingProperty, "");
				// û�����öϺ�֧�ֹ��ܣ���ʱ��ȡ�˱����������ı���
				String costCenterId = null;
				if (editData instanceof FDCBillInfo
						&& ((FDCBillInfo) editData).getOrgUnit() != null) {
					costCenterId = ((FDCBillInfo) editData).getOrgUnit()
							.getId().toString();
				} else {
					costCenterId = SysContext.getSysContext()
							.getCurrentCostUnit().getId().toString();
				}
				try {
					if (STATUS_ADDNEW.equals(this.oprtState)){ //ֻ������ʱ����Ҫȥȡnumber������Ǳ༭����Ҫ����֮ǰ��number Added by Owe_wen 2010-09-16
						number = prepareNumberForAddnew(iCodingRuleManager, (FDCBillInfo) editData, orgId, costCenterId, bindingProperty);
						// ��number��ֵ����caller����Ӧ�����ԣ�����TEXT�ؼ��ı༭״̬���úá�
						prepareNumber(editData, convertNumber(number));
					}
				} catch (Exception e) {
					e.printStackTrace();
					handleCodingRuleError(e);
				}
			} else {
				this.txtNumber.setEnabled(false);
			}
//			}
			if (iCodingRuleManager.isModifiable(editData, orgId,bindingProperty)) {
				// ����������û����޸�
				setNumberTextEnabled();
			} else {
				this.txtNumber.setEnabled(false);
			}
			return true;
		}
		return false;
	}

	// ��������������ˡ�������ʾ��,��������Ƿ��Ѿ��ظ�
	protected String prepareNumberForAddnew(ICodingRuleManager iCodingRuleManager, FDCBillInfo info,
			String orgId, String costerId, String bindingProperty) throws Exception {

		String number = null;
		FilterInfo filter = null;
		int i = 0;
		do {
			// ��������ظ�����ȡ����
			if (bindingProperty != null) {
				number = iCodingRuleManager.getNumber(editData, orgId, bindingProperty, "");
			} else {
				number = iCodingRuleManager.getNumber(editData, orgId);
			}

			filter = new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("number", number));
			filter.getFilterItems().add(
					new FilterItemInfo("state", FDCBillStateEnum.INVALID.getValue(), CompareType.NOTEQUALS));
			filter.getFilterItems().add(new FilterItemInfo("orgUnit.id", costerId));
			if (info.getId() != null) {
				filter.getFilterItems().add(
						new FilterItemInfo("id", info.getId().toString(), CompareType.NOTEQUALS));
			}
			i++;
		} while (((IFDCBill) getBizInterface()).exists(filter) && i < 1000);

		return number;
	}

	protected void setNumberTextEnabled() {

		//		if(getNumberCtrl() != null) {
		//			CostCenterOrgUnitInfo currentCostUnit = SysContext.getSysContext().getCurrentCostUnit();
		//			
		//			if(currentCostUnit != null) {
		//				boolean isAllowModify = FDCClientUtils.isAllowModifyNumber(editData, currentCostUnit.getId().toString());
		//				
		//				getNumberCtrl().setEnabled(isAllowModify);
		//				getNumberCtrl().setEditable(isAllowModify);
		//			}
		//		}
	}

	boolean amtWarned = false;

	protected void txtamount_focusGained(FocusEvent e) throws Exception {
		// TODO Auto-generated method stub
		super.txtamount_focusGained(e);

		if (STATUS_EDIT.equals(getOprtState()) && !amtWarned) {
			ContractClientUtils.checkSplitedForAmtChange(this, getSelectBOID());
			amtWarned = true;
		}
	}
	
	protected void afterSubmitAddNew() {
		//�Ƿ���Ҫ����Ԥ����ͬ�䶯  20120222 wangxin
		//�����˱��������Լ�滮
//			try {
//				if((ContractPropertyEnum.DIRECT.equals(this.editData.getContractPropert())&&this.editData.getProgrammingContract()!=null)||
//						ContractPropertyEnum.SUPPLY.equals(this.editData.getContractPropert())){
//					openContractEstimateChange();
//				}
//				
//			} catch (UIException e1) {
//				e1.printStackTrace();
//			}
			//EAS3G����
//			try {
//				dealEAS3GAttachment();
//			} catch (BOSException e1) {
//				e1.printStackTrace();
//			} catch (EASBizException e2) {
//				e2.printStackTrace();
//			}
		
		super.afterSubmitAddNew();
		if(prmtcontractType.getValue()!=null){
			try {
				contractTypeChanged(prmtcontractType.getValue());
			} catch (CodingRuleException e) {
				handUIException(e);
			} catch (EASBizException e) {
				handUIException(e);
			} catch (BOSException e) {
				handUIException(e);
			} catch (Exception e) {
				handUIException(e);
			}
			detailTableAutoFitRowHeight();
		}
	}
	
	/**
	 * ��ֺ󣬲������޸ġ��Ƿ�ɱ���֡��ֶ�
	 */
	protected void chkCostSplit_mouseClicked(MouseEvent e) throws Exception {
		super.chkCostSplit_mouseClicked(e);

		if (STATUS_EDIT.equals(getOprtState())) {

			if (editData.getSplitState() != null && editData.getSplitState() != CostSplitStateEnum.NOSPLIT) {

				boolean b = chkCostSplit.isSelected();

				chkCostSplit.getModel().setSelected(!b);

				MsgBox.showWarning(this, ContractClientUtils.getRes("splitedNotChangeIsCSplit"));

				SysUtil.abort();
			}
		}
		setCheckBoxValue();
	}

	/**
	 * ���������Ǽ׹��ĺ�ͬ�����Ƿ���붯̬�ɱ���Ϊ���Ҳ����޸�. <br>
	 * 
	 * 2009-9-3 ���붯̬�ɱ������Ƿ�׹��ģ�û�й�ϵ�ˣ�by yong_zhou
	 * @param MouseEvent
	 *            e
	 */
	protected void chkIsPartAMaterialCon_mouseClicked(MouseEvent e)
			throws Exception {
		setCheckBoxValue();
	}
	private void setCheckBoxValue(){
		/*
		 * 2009-9-3 ���붯̬�ɱ������Ƿ�׹��ģ�û�й�ϵ�ˣ�by yong_zhou
		 */
//		if (chkIsPartAMaterialCon.isSelected()) {
//			chkCostSplit.setSelected(false);
//			chkCostSplit.setEnabled(false);
//			chkIsPartAMaterialCon.setEnabled(true);
//		}else{
//			chkCostSplit.setEnabled(true);
//		}
//		if (chkCostSplit.isSelected()) {
//			chkIsPartAMaterialCon.setSelected(false);
//			chkIsPartAMaterialCon.setEnabled(false);
//			chkCostSplit.setEnabled(true);
//		} else{
//			chkIsPartAMaterialCon.setEnabled(true);
//		}
	}
	public void afterActionPerformed(ActionEvent e) {
		super.afterActionPerformed(e);
	}
	protected void verifyInput(ActionEvent e) throws Exception {

		//������ò���һ�廯,����¼���ڼ�
		if (this.isIncorporation && cbPeriod.getValue() == null) {
			MsgBox.showConfirm2(this, "���óɱ��½�,����¼���ڼ�");
			SysUtil.abort();
		}

		FDCClientVerifyHelper.verifyEmpty(this, prmtcontractType);
		super.verifyInputForSave();
		FDCClientVerifyHelper.verifyEmpty(this, txtcontractName);

		super.verifyInput(e);
		//		FDCClientVerifyHelper.verifyEmpty(this, prmtlandDeveloper);
		//		FDCClientVerifyHelper.verifyEmpty(this, prmtpartB);

		/*
		 * �����ͬ����¼������ͬ����
		 */
		if (editData.getContractPropert() == ContractPropertyEnum.SUPPLY) {
			ContractBillEntryCollection entrys = editData.getEntrys();
			boolean hasMainContNum = false;
			for (Iterator iter = entrys.iterator(); iter.hasNext();) {
				ContractBillEntryInfo element = (ContractBillEntryInfo) iter
						.next();
				String rowKey = element.getRowKey();
				if (rowKey != null && rowKey.equals(NU_ROW)
						&& element.getContent() != null
						&& element.getContent().length() > 0) {
					hasMainContNum = true;
					break;
				}
			}

			if (!hasMainContNum) {
				MsgBox.showWarning(this, "�����ͬ����¼������ͬ����(�ں�ͬ��ϸ��Ϣ��¼��)");
				SysUtil.abort();
			}
		}

//		if (!chkCostSplit.isSelected()) {
//			MsgBox.showInfo(this, ContractClientUtils.getRes("NotEntryCost"));
//		}

		checkStampMatch();

		checkProjStatus();

	}

	protected boolean checkCanSubmit() throws Exception {
		if (isIncorporation && ((FDCBillInfo) editData).getPeriod() == null) {
			MsgBox.showWarning(this, "���óɱ��½��ڼ䲻��Ϊ�գ����ڻ�������ά���ڼ������ѡ��ҵ������");
			SysUtil.abort();
		}
		return super.checkCanSubmit();
	}
	/**
	 * �ύʱУ��:��Լ��֤������������Լ��֤�𼰷����б��¼�з��ؽ��֮��  by Cassiel_peng  2008-8-26
	 */
	private void checkBailOriAmount() {
		BigDecimal itemAmtSum=FDCHelper.ZERO;
		for(int i=0;i<tblEconItem.getRowCount();i++){
			itemAmtSum=FDCHelper.add(itemAmtSum, tblEconItem.getRow(i).getCell("payAmount").getValue());
		}
		if(itemAmtSum.compareTo(FDCHelper.toBigDecimal(txtamount.getBigDecimalValue(),2))==1){
			MsgBox.showWarning("��ͬ�����������֮�Ͳ��ܴ��ں�ͬԭ�ҽ�");
			SysUtil.abort();
		}
		BigDecimal bailAmountSum=FDCHelper.ZERO;
		for (int i = 0; i < this.tblBail.getRowCount(); i++) {
			if(this.tblBail.getRow(i).getCell("bailAmount")!=null){
				bailAmountSum=FDCHelper.add(bailAmountSum, this.tblBail.getRow(i).getCell("bailAmount").getValue());
			}
		}
		if(bailAmountSum.compareTo(FDCHelper.toBigDecimal(txtBailOriAmount.getBigDecimalValue(),2))!=0){
			MsgBox.showWarning("��Լ��֤������������Լ��֤�𼰷����б��¼�з��ؽ��֮��");
			SysUtil.abort();
		}
	}
	
	/**
	 * У�� ����ͬ��ϸ��Ϣ�� �еı�¼������¼��Ϊ�գ�������ʾ
	 * @author owen_wen 2010-09-10
	 */
	private void verifyInputForContractDetail(){
		ContractBillEntryCollection coll = editData.getEntrys();
		
		for (int i = 0, size = coll.size(); i < size ; i++){
			ContractBillEntryInfo entryInfo = coll.get(i);
			
			ContractDetailDefInfo cddi = null;
			
			// �п����ǲ����ͬ���ϵķ�¼�����ﲻ���ж�
			if (coll.get(i).getDetailDefID() == null)
				continue;
			
			try {
				cddi = ContractDetailDefFactory.getRemoteInstance().getContractDetailDefInfo(new ObjectUuidPK(BOSUuid.read(entryInfo.getDetailDefID())));
			} catch (EASBizException e1) {
				this.handleException(e1);
			} catch (BOSException e1) {
				this.handleException(e1);
			} catch (UuidException e1) {
				this.handleException(e1);
			}
			
			if (cddi!=null && cddi.isIsMustInput()){
				if (entryInfo.getContent() == null){
					String info = ContractClientUtils.getRes("conDtlCantEmpty");
					String[] args = new String[] {entryInfo.getDetail(), "����"};
					FDCMsgBox.showInfo(this,FDCClientHelper.formatMessage(info, args));
					SysUtil.abort();
					return;
				}
			}
		}
	}
	protected BigDecimal getAccActOnLoadPcAmount(ProgrammingContractInfo pc) throws BOSException, SQLException{
		if(pc==null) return FDCHelper.ZERO;
		FDCSQLBuilder _builder = new FDCSQLBuilder();
		_builder.appendSql(" select sum(bill.famount) amount from T_CON_ContractWithoutText bill ");
		_builder.appendSql(" where bill.fProgrammingContract='"+pc.getId().toString()+"' and bill.fstate in('2SUBMITTED','3AUDITTING')");
		IRowSet rowSet = _builder.executeQuery();
		while(rowSet.next()){
			if(rowSet.getBigDecimal("amount")!=null)
				return rowSet.getBigDecimal("amount");
		}
		return FDCHelper.ZERO;
	}
	protected void verifyInputForSubmint() throws Exception {
		this.verifyInputForContractDetail();
		checkAmout();
		//Ԥ�����
		checkMbgCtrlBalance();
		if(this.prmtContractBillReceive.isRequired()){
			FDCClientVerifyHelper.verifyEmpty(this, prmtContractBillReceive);
		}
		if(this.cbConnectedTransaction.isRequired()){
			FDCClientVerifyHelper.verifyEmpty(this, cbConnectedTransaction);
		}
		FDCClientVerifyHelper.verifyEmpty(this, prmtcontractType);
		//����У��
		FDCClientVerifyHelper.verifyEmpty(this, prmtpartB);
		FDCClientVerifyHelper.verifyEmpty(this, prmtlandDeveloper);
		FDCClientVerifyHelper.verifyEmpty(this, prmtRespDept);
		FDCClientVerifyHelper.verifyEmpty(this, prmtRespPerson);
		if(isNeed){
			FDCClientVerifyHelper.verifyEmpty(this, prmtNeedDept);
			FDCClientVerifyHelper.verifyEmpty(this, prmtNeedPerson);
		}
		FDCClientVerifyHelper.verifyEmpty(this, prmtContractWFType);
		FDCClientVerifyHelper.verifyEmpty(this, cbOrgType);
		if(this.prmtInviteType.isEnabled()){
			FDCClientVerifyHelper.verifyEmpty(this, prmtInviteType);
		}
		super.verifyInputForSubmint();
		if(tblBail.getRowCount()>0){
			FDCClientVerifyHelper.verifyEmpty(this, txtBailOriAmount);
			FDCClientVerifyHelper.verifyEmpty(this, txtBailRate);
		}
		if(prmtTAEntry.isRequired()){
			FDCClientVerifyHelper.verifyEmpty(this, prmtTAEntry);
		}
		if(prmtMpCostAccount.isRequired()){
			FDCClientVerifyHelper.verifyEmpty(this, prmtMpCostAccount);
		}
		if(prmtMarketProject.isRequired()){
			FDCClientVerifyHelper.verifyEmpty(this, prmtMarketProject);
			FDCClientVerifyHelper.verifyEmpty(this, cbJzType);
			FDCClientVerifyHelper.verifyEmpty(this, pkJzStartDate);
			FDCClientVerifyHelper.verifyEmpty(this, pkJzEndDate);
			Date thisDate=new Date();
			if(this.editData.getCreateTime()!=null){
				thisDate=this.editData.getCreateTime();
			}
			Date bizDate=(Date) this.pkJzStartDate.getValue();
			if(FDCDateHelper.dateDiff(FDCDateHelper.getDayBegin(thisDate), bizDate)<0){
				FDCMsgBox.showWarning(this,"��ʼ���ڲ�����С�ڵ��ݴ������ڣ�");
				SysUtil.abort();
			}
			if(this.tblMarket.getRowCount()==0){
				FDCMsgBox.showWarning(this,"Ӫ����ͬ��̯��ϸ����Ϊ�գ�");
				SysUtil.abort();
			}
			BigDecimal rate=FDCHelper.ZERO;
			for(int i=0;i<this.tblMarket.getRowCount();i++){
				if(this.tblMarket.getRow(i).getCell("date").getValue()==null){
					FDCMsgBox.showWarning(this,"Ԥ�Ʒ������²���Ϊ�գ�");
					SysUtil.abort();
				}
				if(this.tblMarket.getRow(i).getCell("rate").getValue()==null){
					FDCMsgBox.showWarning(this,"������������Ϊ�գ�");
					SysUtil.abort();
				}
				if(this.tblMarket.getRow(i).getCell("amount").getValue()==null){
					FDCMsgBox.showWarning(this,"��������Ϊ�գ�");
					SysUtil.abort();
				}
				rate=FDCHelper.add(rate, this.tblMarket.getRow(i).getCell("rate").getValue());
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
			ContractBillCollection con=ContractBillFactory.getRemoteInstance().getContractBillCollection("select * from where marketProject.id='"+info.getId()+"' and mpCostAccount.id='"+cinfo.getId()+"' and id!='"+this.editData.getId()+"'");
			if(con.size()>0){
				FDCMsgBox.showWarning(this,"���ù����ѱ���ͬ��"+con.get(0).getNumber()+" ѡ��");
				SysUtil.abort();
			}
//			BigDecimal comAmount=FDCHelper.ZERO;
//			ContractBillCollection col=null;
//			if(editData.getId()!=null){
//				col=ContractBillFactory.getRemoteInstance().getContractBillCollection("select amount from where mpCostAccount.id='"+cinfo.getId()+"' and marketProject.id='"+info.getId()+"' and id!='"+this.editData.getId()+"'");
//			}else{
//				col=ContractBillFactory.getRemoteInstance().getContractBillCollection("select amount from where mpCostAccount.id='"+cinfo.getId()+"' and marketProject.id='"+info.getId()+"'");
//			}
//			for(int i=0;i<col.size();i++){
//				comAmount=FDCHelper.add(comAmount,col.get(i).getAmount());
//			}
//			
//			ContractWithoutTextCollection wtCol=ContractWithoutTextFactory.getRemoteInstance().getContractWithoutTextCollection("select amount from where mpCostAccount.id='"+cinfo.getId()+"' and marketProject.id='"+info.getId()+"'");
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
			String	paramValue = ParamControlFactory.getRemoteInstance().getParamValue(new ObjectUuidPK(SysContext.getSysContext().getCurrentOrgUnit().getId()), "YF_MARKETPROJECT");
			if(this.txtLocalAmount.getBigDecimalValue().compareTo(subAmount)>0){
				if("false".equals(paramValue)){
					if(FDCMsgBox.showConfirm2(this,"��ͬ��������ʣ�����ȷ���Ƿ��ύ��") == FDCMsgBox.CANCEL){
						SysUtil.abort();
					}
				}else{
					FDCMsgBox.showWarning(this,"��ͬ��������ʣ���");
					SysUtil.abort();
				}
			}
		}
		EcoItemHelper.checkVeforeSubmit(tblEconItem, tblBail);
		checkBailOriAmount();
		
		checkBeforeSubmit();
		//��ͬ�ύ����ǰ��У���Ƿ������������
		if(isRelatedTask()){
			ContractClientUtils.checkConRelatedTaskSubmit(this.editData);
		}
		FDCClientVerifyHelper.verifyEmpty(this, this.prmtcontractType);
		ContractTypeInfo ct=(ContractTypeInfo)this.prmtcontractType.getValue();
//		if(!ContractPropertyEnum.SUPPLY.equals(this.contractPropert.getSelectedItem())){
			if(ct.isIsAccountView()){
				if(!ContractPropertyEnum.SUPPLY.equals(this.contractPropert.getSelectedItem())){
					ContractCostSplitCollection col=ContractCostSplitFactory.getRemoteInstance().getContractCostSplitCollection("select amount from where contractBill.id='"+this.editData.getId().toString()+"'");
					if(col.size()==0){
						FDCMsgBox.showWarning(this,"���ȹ����ɱ���Ŀ��������ȫ��֣�");
						SysUtil.abort();
					}
					if(col.get(0).getAmount()==null||col.get(0).getAmount().compareTo(this.txtamount.getBigDecimalValue())!=0){
						FDCMsgBox.showWarning(this,"���ȹ����ɱ���Ŀ��������ȫ��֣�");
						SysUtil.abort();
					}
					this.editData.setSplitState(CostSplitStateEnum.ALLSPLIT);
				}
			}else{
				verifyContractProgrammingPara();
			}
//		}
		
		//��̬������ ���ŵĲ���Ҫ����ͬ��������
//		String orgName = this.editData.getOrgUnit().getDisplayName();
//		String targetName = "��Լ��Źɷ����޹�˾";
//		if(!this.btnAType.isSelected()&&!this.btnBType.isSelected()&&!this.BtnCType.isSelected()&& orgName.indexOf(targetName)==-1&&isNeedCheckContractType()){
//			FDCMsgBox.showWarning(this, "����д��ͬ�������ͣ�");
//			this.btnAType.requestFocus(true);
//			SysUtil.abort();
//		}
			String param="false";
			param = ParamControlFactory.getRemoteInstance().getParamValue(new ObjectUuidPK(SysContext.getSysContext().getCurrentOrgUnit().getId()), "YF_BANKNUM");
			if("true".equals(param)){
				FDCClientVerifyHelper.verifyEmpty(this, this.prmtLxNum);
				if (!this.editData.getContractPropert().equals(ContractPropertyEnum.DIRECT) && !this.editData.getContractPropert().equals(ContractPropertyEnum.SUPPLY_VALUE))
			    {
			      FDCClientVerifyHelper.verifyEmpty(this, this.txtBank);
			      FDCClientVerifyHelper.verifyEmpty(this, this.txtBankAccount);
			      FDCClientVerifyHelper.verifyEmpty(this, this.cbTaxerQua);
			      FDCClientVerifyHelper.verifyEmpty(this, this.txtTaxerNum);
			    }
				
				FDCClientVerifyHelper.verifyEmpty(this, pkStartDate);
				FDCClientVerifyHelper.verifyEmpty(this, pkEndDate);
			}
		
		if((!this.editData.getContractPropert().equals(ContractPropertyEnum.DIRECT)) 
				&& (!TaxInfoEnum.SIMPLE.equals(this.curProject.getTaxInfo()))
				&& !this.editData.getContractPropert().equals(ContractPropertyEnum.SUPPLY_VALUE)){
			if(this.kdtDetailEntry.getRowCount()==0){
				FDCMsgBox.showWarning(this,"��ͬǩ����ϸ����Ϊ�գ�");
				SysUtil.abort();
			}
			for(int i = 0; i < this.kdtDetailEntry.getRowCount(); i++){
				IRow row=this.kdtDetailEntry.getRow(i);
				if(row.getCell("detail").getValue()==null||"".equals(row.getCell("detail").getValue().toString().trim())){
					FDCMsgBox.showWarning(this,"��ͬ��ϸ����Ϊ�գ�");
					this.kdtDetailEntry.getEditManager().editCellAt(row.getRowIndex(), this.kdtDetailEntry.getColumnIndex("detail"));
					SysUtil.abort();
				}
				if(row.getCell("totalAmount").getValue()==null){
					FDCMsgBox.showWarning(this,"��˰����Ϊ�գ�");
					this.kdtDetailEntry.getEditManager().editCellAt(row.getRowIndex(), this.kdtDetailEntry.getColumnIndex("totalAmount"));
					SysUtil.abort();
				}
				if(row.getCell("rate").getValue()==null){
					FDCMsgBox.showWarning(this,"˰�ʲ���Ϊ�գ�");
					this.kdtDetailEntry.getEditManager().editCellAt(row.getRowIndex(), this.kdtDetailEntry.getColumnIndex("rate"));
					SysUtil.abort();
				}
			}
		}
		
		boolean isUseYz=false;
		CurProjectInfo project=CurProjectFactory.getRemoteInstance().getCurProjectInfo(new ObjectUuidPK(this.editData.getCurProject().getId()));
		if(project.isIsOA()){
			for(int i=0;i<tblDetail.getRowCount();i++){
				if(tblDetail.getRow(i).getCell(DETAIL_COL).getValue().toString().equals("�Ƿ�ʹ�õ�����")
						&&tblDetail.getRow(i).getCell(CONTENT_COL).getValue().toString().equals("��")){
					isUseYz=true;
				}
			}
		}
		if(isUseYz){
			if(this.kdtYZEntry.getRowCount()==0){
				FDCMsgBox.showWarning(this,"ӡ����Ϣ����Ϊ�գ�");
				SysUtil.abort();
			}
			if(this.kdtYZEntry.getRowCount()>10){
				FDCMsgBox.showWarning(this,"ӡ����Ϣ��������ʮ����");
				SysUtil.abort();
			}
			for(int i=0;i<this.kdtYZEntry.getRowCount();i++){
				IRow row=this.kdtYZEntry.getRow(i);
				if(row.getCell("count").getValue()==null){
					FDCMsgBox.showWarning(this,"ӡ��ʹ�ô�������Ϊ�գ�");
					this.kdtYZEntry.getEditManager().editCellAt(row.getRowIndex(), this.kdtYZEntry.getColumnIndex("count"));
					SysUtil.abort();
				}
			}
		}
	}
	private void verifyContractProgrammingPara() throws BOSException, SQLException, EASBizException {
		ProgrammingContractInfo pc = (ProgrammingContractInfo) this.editData.getProgrammingContract();
		String param = null;
		ObjectUuidPK pk = new ObjectUuidPK(editData.getOrgUnit().getId());
		
		param = ParamControlFactory.getRemoteInstance().getParamValue(pk, "FDC228_ISSTRICTCONTROL");
		
		String programControlMode = param;//0:�ϸ���ƣ� 1:��ʾ���ƣ�2:����ʾ
		if(programControlMode != null && !"2".equals(programControlMode)){
			if(editData==null||editData.getId()==null){
				FDCMsgBox.showWarning(this,"���ȱ��浥�ݣ���������Լ�滮��");
				SysUtil.abort();
			}
		}
		FilterInfo filter = new FilterInfo();
		if(pc!=null&&!ContractPropertyEnum.SUPPLY.equals(this.editData.getContractPropert())){
			filter.getFilterItems().add(new FilterItemInfo("programmingContract.id",pc.getId().toString()));
			filter.getFilterItems().add(new FilterItemInfo("id",editData.getId().toString(),CompareType.NOTEQUALS));
			if(ContractBillFactory.getRemoteInstance().exists(filter)){
				FDCMsgBox.showWarning("��Լ�滮�Ѿ���������ͬ������������ѡ��");
				SysUtil.abort();
			}
			filter = new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("programmingContract.id",pc.getId().toString()));
			if(ContractPCSplitBillEntryFactory.getRemoteInstance().exists(filter)){
				FDCMsgBox.showWarning("��Լ�滮�Ѿ����������ں�ͬ������������ѡ��");
				SysUtil.abort();
			}
		}
		//======================��Լ��ܿ��ƺ�ͬǩ���߼�����=====================================================================
		//1������Ҫ���ù�����Լ��ܵĲ��������ǽ��붯̬�ɱ��ĺ�ͬ����,�Ҳ���ȫ�ں�ͬ��
		if(!curProject.isIsWholeAgeStage()){
			param = ParamControlFactory.getRemoteInstance().getParamValue(pk, "FDC228_ISSTRICTCONTROL");
			if (!com.kingdee.util.StringUtils.isEmpty(param)) {
				int i = Integer.parseInt(param);
				boolean isAbort=false;
				if(pc==null){
					filter = new FilterInfo();
					filter.getFilterItems().add(new FilterItemInfo("programming.project.id", this.editData.getCurProject().getId().toString()));
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
						if(ContractPropertyEnum.SUPPLY.equals(this.editData.getContractPropert())){
							ICell cell = getDetailInfoTable().getRow(getRowIndexByRowKey(AM_ROW)).getCell(CONTENT_COL);
//							if(pc.getEstimateAmount()==null||(cell.getValue()!=null&&cell.getValue() instanceof BigDecimal&&((BigDecimal)cell.getValue()).compareTo(pc.getEstimateAmount())>0)){
//								FDCMsgBox.showWarning(this,"�����ͬԭ�ҽ�������ͬ��Լ�滮��Ԥ�����������ύ��");
//								SysUtil.abort();
//							}
							if(cell.getValue()!=null&&cell.getValue() instanceof BigDecimal){
//								BigDecimal amount= (BigDecimal) cell.getValue();
//								BigDecimal balance=(pc.getBalance()== null?FDCHelper.ZERO:pc.getBalance()).setScale(2, BigDecimal.ROUND_HALF_UP);
//								if(amount.compareTo(balance)>0){
//									FDCMsgBox.showWarning(this, "��ͬǩԼ���������ĺ�Լ�Ĺ滮���������ύ��");
//									SysUtil.abort();
//								}
								ContractEstimateChangeBillFactory.getRemoteInstance().sub(pc, ContractEstimateChangeTypeEnum.SUPPLY, (BigDecimal) cell.getValue(), false,editData.getOrgUnit().getId().toString());
							}
						}else{
							BigDecimal amount = this.editData.getAmount().setScale(2, BigDecimal.ROUND_HALF_UP);
							BigDecimal balance=(pc.getBalance()== null?FDCHelper.ZERO:pc.getBalance()).setScale(2, BigDecimal.ROUND_HALF_UP);
							BigDecimal estimate = (pc.getEstimateAmount()== null?FDCHelper.ZERO:pc.getEstimateAmount()).setScale(2, BigDecimal.ROUND_HALF_UP);
							BigDecimal onLoadAmount=getAccActOnLoadPcAmount(pc).setScale(2, BigDecimal.ROUND_HALF_UP);
							String detail="�滮��"+balance+"\nԤ����"+estimate+"\nռ�ý�"+onLoadAmount;
							
							if (amount.compareTo(FDCHelper.subtract(FDCHelper.add(balance, estimate), onLoadAmount)) > 0) {
								FDCMsgBox.showDetailAndOK(this, "��ͬǩԼ���������ĺ�Լ�ģ��滮���+Ԥ�����-ռ�ý����������ύ��", detail, 2);
								SysUtil.abort();
							}
						}
					} else if(isAbort){
						FDCMsgBox.showWarning(this, "δ������ܺ�Լ���������ύ��");
						SysUtil.abort();
					}
					break;
				case 1:
					// ��ʾ����ʱ
					if (pc != null) {
						if(ContractPropertyEnum.SUPPLY.equals(this.editData.getContractPropert())){
							ICell cell = getDetailInfoTable().getRow(getRowIndexByRowKey(AM_ROW)).getCell(CONTENT_COL);
//							if(pc.getEstimateAmount()==null||(cell.getValue()!=null&&cell.getValue() instanceof BigDecimal&&((BigDecimal)cell.getValue()).compareTo(pc.getEstimateAmount())>0)){
//								if(FDCMsgBox.showConfirm2(this,"�����ͬԭ�ҽ�������ͬ��Լ�滮��Ԥ������ȷ���Ƿ��ύ��") == FDCMsgBox.CANCEL){
//									SysUtil.abort();
//								}
//							}
							if(cell.getValue()!=null&&cell.getValue() instanceof BigDecimal){
//								BigDecimal amount= (BigDecimal) cell.getValue();
//								BigDecimal balance=(pc.getBalance()== null?FDCHelper.ZERO:pc.getBalance()).setScale(2, BigDecimal.ROUND_HALF_UP);
//								if(amount.compareTo(balance)>0){
//									if(FDCMsgBox.showConfirm2(this, "��ͬǩԼ���������ĺ�Լ�Ĺ滮����ȷ���Ƿ��ύ��")== FDCMsgBox.CANCEL){
//										SysUtil.abort();
//									}
//								}
								try {
									ContractEstimateChangeBillFactory.getRemoteInstance().sub(pc, ContractEstimateChangeTypeEnum.SUPPLY, (BigDecimal) cell.getValue(), false,editData.getOrgUnit().getId().toString());
								} catch (EASBizException e) {
									if(FDCMsgBox.showConfirm2(this,"����Ԥ����ͬ�䶯����ȷ���Ƿ��ύ��") == FDCMsgBox.CANCEL){
										SysUtil.abort();
									}
								}
							}
						}else{
							BigDecimal amount = this.editData.getAmount().setScale(2, BigDecimal.ROUND_HALF_UP);
							BigDecimal balance=(pc.getBalance()== null?FDCHelper.ZERO:pc.getBalance()).setScale(2, BigDecimal.ROUND_HALF_UP);
							BigDecimal estimate = (pc.getEstimateAmount()== null?FDCHelper.ZERO:pc.getEstimateAmount()).setScale(2, BigDecimal.ROUND_HALF_UP);
							BigDecimal onLoadAmount=getAccActOnLoadPcAmount(pc).setScale(2, BigDecimal.ROUND_HALF_UP);
							String detail="�滮��"+balance+"\nԤ�����:��"+estimate+"\nռ�ý�"+onLoadAmount;
							
							if (amount.compareTo(FDCHelper.subtract(FDCHelper.add(balance, estimate), onLoadAmount)) > 0) {
								if(FDCMsgBox.showConfirm3a(this, "��ͬǩԼ���������ĺ�Լ�ģ��滮���+Ԥ�����-ռ�ý�����ȷ���Ƿ��ύ��",detail)== FDCMsgBox.CANCEL){
									SysUtil.abort();
								}
							}
						}
					} else if(isAbort) {
						if(FDCMsgBox.showConfirm2(this, "δ������ܺ�Լ����ȷ���Ƿ��ύ��")== FDCMsgBox.CANCEL){
							SysUtil.abort();
						}
					}
					break;
				case 2:
					// ������ʱ
//					if (pc != null) {
//						if(ContractPropertyEnum.SUPPLY.equals(this.editData.getContractPropert())){
//							ICell cell = getDetailInfoTable().getRow(getRowIndexByRowKey(AM_ROW)).getCell(CONTENT_COL);
////							if(pc.getEstimateAmount()==null||(cell.getValue()!=null&&cell.getValue() instanceof BigDecimal&&((BigDecimal)cell.getValue()).compareTo(pc.getEstimateAmount())>0)){
////								if(FDCMsgBox.showConfirm2(this,"�����ͬԭ�ҽ�������ͬ��Լ�滮��Ԥ������ȷ���Ƿ��ύ��") == FDCMsgBox.CANCEL){
////									SysUtil.abort();
////								}
////							}
//							if(cell.getValue()!=null&&cell.getValue() instanceof BigDecimal){
//								try {
//									ContractEstimateChangeBillFactory.getRemoteInstance().sub(pc, ContractEstimateChangeTypeEnum.SUPPLY, (BigDecimal) cell.getValue(), false);
//								} catch (EASBizException e) {
//									if(FDCMsgBox.showConfirm2(this,"����Ԥ����ͬ�䶯����ȷ���Ƿ��ύ��") == FDCMsgBox.CANCEL){
//										SysUtil.abort();
//									}
//								}
//							}
//						}else{
//							BigDecimal amount = this.editData.getAmount().setScale(2, BigDecimal.ROUND_HALF_UP);
//							BigDecimal balance=(pc.getBalance()== null?FDCHelper.ZERO:pc.getBalance()).setScale(2, BigDecimal.ROUND_HALF_UP);
//							BigDecimal estimate = (pc.getEstimateAmount()== null?FDCHelper.ZERO:pc.getEstimateAmount()).setScale(2, BigDecimal.ROUND_HALF_UP);
//							BigDecimal onLoadAmount=getAccActOnLoadPcAmount(pc).setScale(2, BigDecimal.ROUND_HALF_UP);
//							String detail="�滮��"+balance+"\nԤ����"+estimate+"\nռ�ý�"+onLoadAmount;
//							
//							if (amount.compareTo(FDCHelper.subtract(FDCHelper.add(balance, estimate), onLoadAmount)) > 0) {
//								if(FDCMsgBox.showConfirm3a(this, "��ͬǩԼ���������ĺ�Լ�ģ��滮���+Ԥ�����-ռ�ý�����ȷ���Ƿ��ύ��",detail)== FDCMsgBox.CANCEL){
//									SysUtil.abort();
//								}
//							}
//						}
//					}
					break;
				}
			}
		}
	}
	/**
	 * �����ͬ��� <= 
	 * @return
	 * @throws EASBizException
	 * @throws BOSException
	 */
	private boolean isOverEstimateAmount() throws EASBizException, BOSException {
		
		return false;
	}

//	private boolean isNeedCheckContractType(){
//		String  contractTypeNumber = this.editData.getContractType().getLongNumber().toUpperCase();
//		String respDeptNumber = this.editData.getRespDept().getNumber().toUpperCase();
//		respDeptNumber = respDeptNumber.substring(respDeptNumber.lastIndexOf("-") + 1,respDeptNumber.length());
//		if((contractTypeNumber.startsWith("02QQ")||contractTypeNumber.startsWith("03JA")||contractTypeNumber.startsWith("04JC")||contractTypeNumber.startsWith("05GJ"))
//				&& (respDeptNumber.equals("04")||respDeptNumber.equals("05")||respDeptNumber.equals("06")||respDeptNumber.equals("10"))){
//			return true;
//		}
//		return false;
//	}
	private boolean isCheckAmount() throws EASBizException, BOSException {
		Object param = getCtrlParam();
		String paramValue = param == null ? "" : param.toString();
		return true;
	}
	private Object getCtrlParam() throws EASBizException, BOSException {
		String orgPk = this.editData.getOrgUnit().getId().toString();
		IObjectPK orgpk = new ObjectUuidPK(orgPk);
		String param = ParamControlFactory.getRemoteInstance().getParamValue(orgpk, "FDC228_ISSTRICTCONTROL");
		return param == null ? "" : param;
	}	
	
	/**
	 * ���ò���"�Ƿ������ƻ�������й���"�󣬺�ͬɾ���ͷ�����ʱ��У��ú�ͬ���Ƿ���ڡ���ͬ�������������
	 * ����У�������ɾ���ͷ����� by cassiel 2010-08-09
	 */
	private void checkConRelatedTaskDelUnAudit() throws BOSException, SQLException{
		ContractBillInfo contract = this.editData;
		if(contract.getId()==null){
			return;
		}
		String contractBillId = this.editData.getId().toString();
		FDCSQLBuilder builder = new FDCSQLBuilder();
		builder.appendSql("select fid,count(fid) jishu from T_SCH_ContractAndTaskRel where FContractID=? group by fid ");
		builder.addParam(contractBillId);
		IRowSet rowSet = builder.executeQuery();
		Set ids = new HashSet();
		while(rowSet.next()){
			int count = rowSet.getInt("jishu");
			if(count >0){
				String id = rowSet.getString("fid");
				ids.add(id);
			}
		}
		boolean flag = false;
		if(ids.size()>0){
			builder.clear();
			builder.appendParam("select count(fid) jishu from T_SCH_ContractAndTaskRelEntry where FParentID  ",ids.toArray());
			IRowSet _rowSet = builder.executeQuery();
			while(_rowSet.next()){
				int _count = _rowSet.getInt("jishu");
				if(_count > 0){
					flag = true;
					break;
				}
			}
		}
		if(flag){
			FDCMsgBox.showInfo("��ͬ�ѱ��������������ִ�д˲�����");
			SysUtil.abort();
		}
		
	}
	
	private void checkBeforeSubmit() {
		if(editData.getEffectiveEndDate()!= null && editData.getEffectiveStartDate() != null){
			if(editData.getEffectiveEndDate().before(editData.getEffectiveStartDate())){
				FDCMsgBox.showError("��ͬ��Ч��ֹ����С�ڿ�ʼ���ڣ�");
				abort();
			}
		}
		if(editData.getInformation() != null && editData.getInformation().length() > 255){
			FDCMsgBox.showError("��ͬժҪ��Ϣ����ϵͳԼ������(255)��");
			abort();
		}
		
		if(this.prmtMainContract.isRequired()){
			if(prmtMainContract.getData() == null){
				FDCMsgBox.showError("¼��ս���Ӻ�ͬʱ��ս������ͬ���벻��Ϊ�գ�");
				abort();
			}
		}
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
					if(chkCostSplit.isSelected()) {
						MsgBox.showWarning(this, "�ú�ͬ���ڵĹ�����Ŀ�Ѿ����ڹرջ���ת״̬�����ܽ��붯̬�ɱ�����ȡ��\"���붯̬�ɱ�\"��ѡ���ٱ���/�ύ");
						SysUtil.abort();
					}
				}
			}
		}
	}

	private void checkStampMatch() {
		/**
		 * ӡ��˰�����ں�ͬ���*ӡ��˰�ʣ���ʾ
		 */
		BigDecimal stampTaxAmt = editData.getStampTaxAmt() == null ? FDCHelper.ZERO
				: editData.getStampTaxAmt();
		BigDecimal amount = editData.getAmount() == null ? FDCHelper.ZERO
				: editData.getAmount();
		BigDecimal stampTaxRate = editData.getStampTaxRate() == null ? FDCHelper.ZERO
				: editData.getStampTaxRate();
		stampTaxAmt = stampTaxAmt.setScale(2,BigDecimal.ROUND_HALF_UP);
		if (editData.getStampTaxAmt() != null
				&& editData.getAmount() != null
				&& stampTaxAmt.compareTo(amount.multiply(stampTaxRate).divide(
						FDCConstants.B100, 2, BigDecimal.ROUND_HALF_UP)) != 0) {
			int result = MsgBox.showConfirm2(this, ContractClientUtils
					.getRes("stampNotMatchAmt"));
			if (result == MsgBox.NO || result == MsgBox.CANCEL) {
				SysUtil.abort();
			}
		}
	}

	/**
	 * �����
	 * @throws BOSException 
	 * @throws EASBizException 
	 *
	 */
	private void checkAmout() throws EASBizException, BOSException {
		String projectId = editData.getCurProject().getId().toString();

		if (editData.isIsCoseSplit()
				&& (FDCContractParamUI.RD_MSG.equalsIgnoreCase(controlType) || FDCContractParamUI.RD_CONTROL
						.equalsIgnoreCase(controlType))) {
			if (editData.getAmount() == null) {
				editData.setAmount(FDCConstants.ZERO);
			}
			BigDecimal amiCost = null;
			//��ȡ�ɱ����ƽ��
			String msg = null;
			if (!FDCContractParamUI.RD_DYMIC.equalsIgnoreCase(controlCost)) {
				amiCost = FDCCostRptFacadeFactory.getRemoteInstance()
						.getAimCost(projectId);
				msg = "Ŀ��ɱ�";
			} else {
				amiCost = FDCCostRptFacadeFactory.getRemoteInstance()
						.getDynCost(projectId);
				msg = "��̬�ɱ�";
			}
			if (amiCost == null)
				amiCost = FDCConstants.ZERO;

			//��ȡ��Ŀ��ͬǩԼ�ܽ����״̬������
			String id = editData.getId() != null ? editData.getId().toString()
					: null;
			BigDecimal signAmount = ContractFacadeFactory.getRemoteInstance()
					.getProjectAmount(projectId, id);
			if (signAmount == null)
				signAmount = FDCConstants.ZERO;

			if (amiCost.compareTo(signAmount.add(editData.getAmount())) < 0) {
				//��ʾ  �ϸ����
				FDCMsgBox.showWarning(this, "��Ŀ��ͬǩԼ�ܽ���Ѿ�������" + msg);
				if (!FDCContractParamUI.RD_MSG.equalsIgnoreCase(controlType)) {
					SysUtil.abort();
				}
			}

		}

	}

	public void actionContractPlan_actionPerformed(ActionEvent e) throws Exception {
		String selectBOID = getSelectBOID();
		if (selectBOID == null) {
			MsgBox.showWarning(this, "��ͬ��δ���棬���ȱ����ͬ����ά����ͬ����ƻ�");
			SysUtil.abort();
		}
		ContractPayPlanEditUI.showEditUI(this, selectBOID, getOprtState());
	}
	
	/**
	 * ����ͬ�Ƿ������ܺ�Լ
	 * 
	 * @return
	 * @throws Exception
	 */
	private String checkReaPre() throws Exception {
		String billId = null;
		if(editData.getId() != null && ContractBillFactory.getRemoteInstance().exists(new ObjectUuidPK(editData.getId()))){
			SelectorItemCollection sic = new SelectorItemCollection();
			sic.add("programmingContract");
			ContractBillInfo conInfo = ContractBillFactory.getRemoteInstance().getContractBillInfo(new ObjectUuidPK(editData.getId()),sic);
			if(conInfo.getProgrammingContract() != null){
				billId = conInfo.getProgrammingContract().getId().toString();
			}
		}
		return billId;
		
	}


	public void actionSave_actionPerformed(ActionEvent e) throws Exception {
		// ����ǰ��д�������Ŀ�ܺ�Լ���Ƿ����á��ֶ�
		updateProgrammingContract(this.editData.getProgrammingContract(),0);
		super.actionSave_actionPerformed(e);
		EcoItemHelper.setPayItemRowBackColor(this.tblEconItem);
		EcoItemHelper.setBailRowBackColor(tblBail, txtBailOriAmount, txtBailRate);
		// �����дд�������Ŀ�ܺ�Լ״̬
		updateProgrammingContract(this.editData.getProgrammingContract(),1);
		Object isFromWorkflow = getUIContext().get("isFromWorkflow");
		if (isFromWorkflow != null && isFromWorkflow.toString().equals("true") && getOprtState().equals(STATUS_EDIT)) {
			btnSubmit.setEnabled(false);
			btnCopy.setEnabled(false);
			btnRemove.setEnabled(false);
			btnAddLine.setEnabled(false);
			btnRemoveLine.setEnabled(false);
		}
//		prmtModel.setEnabled(true);
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
		// ����ǰ��д�������Ŀ�ܺ�Լ���Ƿ����á��ֶ�
		updateProgrammingContract(this.editData.getProgrammingContract(),0);
		
		if(this.prmtMpCostAccount.getValue()!=null&&this.prmtMarketProject.getValue()!=null){
			CostAccountInfo info=CostAccountFactory.getRemoteInstance().getCostAccountInfo(new ObjectUuidPK(((CostAccountInfo)this.prmtMpCostAccount.getValue()).getId()));
			MarketProjectInfo market=MarketProjectFactory.getRemoteInstance().getMarketProjectInfo(new ObjectUuidPK(((MarketProjectInfo)this.prmtMarketProject.getValue()).getId()));
			boolean isSet=false;
			ContractBillCollection col=ContractBillFactory.getRemoteInstance().getContractBillCollection("select marketProject.id from where id='"+this.editData.getId()+"'");
			if(col.size()>0&&col.get(0).getMarketProject()!=null&&!col.get(0).getMarketProject().getId().toString().equals(market.getId().toString())){
				isSet=true;
			}
			if(FDCHelper.isEmpty(this.editData.getIsTimeOut())||isSet){
				if(info.getYjType()!=null&&info.getYjType().equals(CostAccountYJTypeEnum.FYJ)&&market.getAuditTime()!=null){
					Calendar cal = new GregorianCalendar();
					cal.setTime(market.getAuditTime());
					cal.set(11, 0);
					cal.set(12, 0);
					cal.set(13, 0);
					cal.set(14, 0);
					int day=FDCDateHelper.getDiffDays(cal.getTime(), new Date());
					if(day>3){
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
		super.actionSubmit_actionPerformed(e);
		// �����дд�������Ŀ�ܺ�Լ״̬
		updateProgrammingContract(this.editData.getProgrammingContract(),1);
		if (this.getOprtState().equals("ADDNEW")) {
			this.actionSave.setEnabled(true);
			//R101231-259  ��ͬ��������¼�룬�����ύ��ѡ���ͬ���Ͳ��ܴ�����ϸ��Ϣ�� by zhiyuan_tang 2010-01-18
			removeDetailTableRowsOfContractType();
			ContractTypeInfo cType = (ContractTypeInfo) prmtcontractType.getData();
			if (cType != null) {				
				fillDetailByContractType(cType.getId().toString());
			}
			fillDetailByPropert(ContractPropertyEnum.DIRECT,cType);
		}
		handleOldData();
		EcoItemHelper.setPayItemRowBackColorWhenInit(this.tblEconItem);
		EcoItemHelper.setBailRowBackColorWhenInit(tblBail, txtBailOriAmount, txtBailRate);
		
//		if(STATUS_FINDVIEW.equals(this.oprtState) ||STATUS_EDIT.equals(this.oprtState)){
//			try {
//				dealEAS3GAttachment();
//			} catch (BOSException e1) {
//				e1.printStackTrace();
//			} catch (EASBizException e2) {
//				e2.printStackTrace();
//			}
//		}
		
		if (editData.getState() == FDCBillStateEnum.AUDITTING) {
			btnSave.setEnabled(false);
			btnSubmit.setEnabled(false);
			btnEdit.setEnabled(false);
			btnRemove.setEnabled(false);
		}
		this.setOprtState("VIEW");
	}

	/**
	 * 1���ܼ۱տں�ͬ������Ҫ������Ԥ����ͬ�䶯�����棻
	 * 2���Ǳտں�ͬ:����ͬ����Ϊ��׮������ʯ���������ܰ������¡�Ϳ�ϡ���ש����ʯ�ᡢ����Ļǽ������װ�ܰ������۹���
	 * 		�Һ�ͬ���С�ں�Լ�滮�����������Ԥ����ͬ�䶯����
	 * 3�������ͬ ����Ԥ����ͬʱ��ͬ������ͬ��
	 * @throws UIException 
	 */
	private void openContractEstimateChange() throws UIException {
//		this.prmtcontractType.getValue();
		if(!this.chkIsOpen.isSelected()&&islitterAmount()&&ContractPropertyEnum.DIRECT.equals(this.editData.getContractPropert())&&this.editData.getProgrammingContract() != null){//ֱ�Ӻ�ͬ
			UIContext uiContext = new UIContext(this);
			uiContext.put("contract",this.editData);
			uiContext.put("isFromContract",Boolean.TRUE);
			IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.MODEL).create(ContractEstimateChangeEditUI.class.getName(), uiContext, null, "ADDNEW");
			uiWindow.show();
		} else if( ContractPropertyEnum.SUPPLY.equals(this.editData.getContractPropert())&&islitterAmount()){//�����ͬ�Ľ��С��Ԥ�����
			UIContext uiContext = new UIContext(this);
			ContractBillInfo contractBillInfo = null;
			ContractBillEntryCollection  entryColl = editData.getEntrys();
			for(int i = 0 ; i < entryColl.size();i++){
				ContractBillEntryInfo entryInfo = entryColl.get(i);
				if(entryInfo.getRowKey() != null&& entryInfo.getRowKey().equals(NU_ROW)&& entryInfo.getContent() != null
						&& entryInfo.getContent().trim().length() > 0){
					String id = entryInfo.getContent();
					try {
						contractBillInfo = ContractBillFactory.getRemoteInstance().getContractBillInfo(new ObjectUuidPK(id));
					} catch (Exception e) {
						handUIException(e);
					}
				}
			}
			uiContext.put("contract",contractBillInfo);
			uiContext.put("isFromContract",Boolean.TRUE);
			uiContext.put("suppyContractId",this.editData.getId().toString());
			IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.MODEL).create(ContractEstimateChangeEditUI.class.getName(), uiContext, null, "ADDNEW");
			uiWindow.show();
		}
	}

	/**
	 *
	 *1����Լ�滮�߼� ǩԼ��� = ����ͬ��� + �����ͬ���
	 *2���Ƿ񵯳�Ԥ������������� ���ƽ�� - ǩԼ���  >��κ�ͬ��� 
	 * @return
	 */
	private boolean islitterAmount() {
		BigDecimal amount = this.txtamount.getBigDecimalValue();
		BigDecimal planAmount = FDCHelper.ZERO;
		if(ContractPropertyEnum.DIRECT.equals(this.editData.getContractPropert())){
			planAmount = this.editData.getProgrammingContract().getAmount()!=null?this.editData.getProgrammingContract().getAmount():FDCHelper.ZERO;
			planAmount = FDCHelper.subtract(planAmount, this.editData.getProgrammingContract().getSignUpAmount());
			if(planAmount.compareTo(amount) > 0){
				return true;
			}
		}else if(ContractPropertyEnum.SUPPLY.equals(this.editData.getContractPropert())){
			String number = this.editData.getMainContractNumber();
			ContractBillInfo info =null;
			try {
				info = ContractBillFactory.getRemoteInstance().getContractBillInfo("select programmingContract.* where number = '"+number+"' ");
			} catch (EASBizException e) {
				e.printStackTrace();
			} catch (BOSException e) {
				e.printStackTrace();
			}
			planAmount = info!=null?info.getProgrammingContract().getAmount():FDCHelper.ZERO;
			planAmount = FDCHelper.subtract(planAmount, info.getProgrammingContract().getSignUpAmount());
			if(planAmount.compareTo(amount) > 0){
				return true;
			}
		}
		
		return false;
	}

	// �ύʱ������Ԥ�����
	private void checkMbgCtrlBalance() throws BOSException, EASBizException {
		//���ݲ����Ƿ���ʾ��ͬ������Ŀ
		if (!isShowCharge || editData.getConChargeType() == null) {
			return;
		}
		StringBuffer buffer = new StringBuffer("");
		editData.setBizDate((Date) this.pkbookedDate.getValue());
		IBgControlFacade iCtrl = BgControlFacadeFactory.getRemoteInstance();
		BgCtrlResultCollection bgCtrlResultCollection = iCtrl.getBudget(FDCConstants.ContractBill, null, editData);
		if (bgCtrlResultCollection != null) {
			for (int j = 0, count = bgCtrlResultCollection.size(); j < count; j++) {
				BgCtrlResultInfo bgCtrlResultInfo = bgCtrlResultCollection.get(j);

				BigDecimal balance = bgCtrlResultInfo.getBalance();
				if (balance != null && balance.compareTo(bgCtrlResultInfo.getReqAmount()) < 0) {
					buffer.append(bgCtrlResultInfo.getItemName() + "(" + bgCtrlResultInfo.getOrgUnitName() + ")")
							.append(
									EASResource.getString(FDCConstants.VoucherResource, "BalanceNotEnagh")
											+ "\r\n");
				}
			}
		}

		if (buffer.length() > 0) {
			int result = MsgBox.showConfirm2(this, buffer.toString() + "\r\n"
					+ EASResource.getString(FDCConstants.VoucherResource, "isGoOn"));
			if (result == MsgBox.CANCEL) {
				SysUtil.abort();
			}
		}
	}

	protected boolean isUseMainMenuAsTitle() {
		return false;
	}

	protected void fillCostPanel() throws Exception {
		FDCUIWeightWorker.getInstance().addWork(new IFDCWork() {
			public void run() {
				try {
					fillCostPanelByAcct();
				} catch (Exception e) {
					handUIException(e);
				}
			}
		});

	}

	ContractCostHelper helper = null;

	//���Լ���costPanel
	protected void fillCostPanelByAcct() throws Exception {

		if (editData.getSplitState() == null
				|| CostSplitStateEnum.NOSPLIT.equals(editData.getSplitState())) {
			return;
		}
		//aimCost hasHappen intendingHappen dynamicCost
		FDCHelper.formatTableNumber(tblCost, "aimCost");
		FDCHelper.formatTableNumber(tblCost, "hasHappen");
		FDCHelper.formatTableNumber(tblCost, "intendingHappen");
		FDCHelper.formatTableNumber(tblCost, "dynamicCost");
		FDCHelper.formatTableNumber(tblCost, "chayi");

		String selectObjId = this.curProject.getId().toString();
		DynamicCostMap dynamicCostMap = FDCCostRptFacadeFactory
				.getRemoteInstance().getDynamicCost(selectObjId, null, true);

		if (dynamicCostMap == null) {
			return;
		}

		Map acctMap = new HashMap();
		//��ȡ��ǰ��ͬ�Ĳ�ֿ�Ŀ			
		EntityViewInfo view = new EntityViewInfo();
		view.getSelector().add("costAccount.id");
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("parent.contractBill.id", editData.getId().toString()));
		filter.getFilterItems().add(new FilterItemInfo("parent.isInvalid", new Integer(0)));
		view.setFilter(filter);
		ContractCostSplitEntryCollection col = ContractCostSplitEntryFactory.getRemoteInstance()
				.getContractCostSplitEntryCollection(view);
		for (Iterator iter = col.iterator(); iter.hasNext();) {
			ContractCostSplitEntryInfo element = (ContractCostSplitEntryInfo) iter.next();

			String costAcctId = element.getCostAccount().getId().toString();
			if (acctMap.containsKey(costAcctId)) {
				continue;
			}
			acctMap.put(costAcctId, element);
		}

		helper = new ContractCostHelper(tblCost, editData.getId().toString());
		helper.fillCostTable();
	}

	protected void tblCost_tableClicked(
			com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e)
			throws Exception {
		if (helper == null)
			return;
		helper.tblCost_tableClicked(this, e);
	}

	/*
	 *  ��FDCBillEdit�ڽ���ĳɳ��󷽷�,��ǿ��Ҫ������ȥʵ��
	 * @see com.kingdee.eas.fdc.basedata.client.FDCBillEditUI#getDetailTable()
	 */
	protected KDTable getDetailTable() {
		return table;
	}

	/**
	 * RPC���죬�κ�һ���¼�ֻ��һ��RPC
	 */

	public boolean isPrepareInit() {

		return true;
	}

	public boolean isPrepareActionSubmit() {
		//FDCClientVerifyHelper.verifyRequire(this);
		return false;
	}

	public boolean isPrepareActionSave() {
		//FDCClientVerifyHelper.verifyRequire(this);
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
	protected void PrepareHandlerParam(RequestContext request) {
		super.PrepareHandlerParam(request);

	}
	//�鿴Ԥ�����
	public void actionViewBgBalance_actionPerformed(ActionEvent e) throws Exception {
		storeFields();
		//ҵ������Ĭ��δ���ã����Ԥ�����ʱ��ҵ�����ڿ��ƣ��ᵼ�²鿴Ԥ��ʱ�鲻�����ֶ�����һ��
		editData.setBizDate((Date) this.pkbookedDate.getValue());
		ContractBillInfo info = editData;
		IBgControlFacade iCtrl = BgControlFacadeFactory.getRemoteInstance();
		BgCtrlResultCollection coll = iCtrl.getBudget(
				FDCConstants.ContractBill, null, info);

		UIContext uiContext = new UIContext(this);
		uiContext.put(BgHelper.BGBALANCE, coll);

		IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.MODEL)
				.create(BgBalanceViewUI.class.getName(), uiContext, null,
						STATUS_VIEW);
		uiWindow.show();
		info=null;
	}
	
	/**
	 * �õ�����FilterGetter��"���ظ���"��"�򿪸���"��ʵ��  by Cassiel_peng
	 */
	private  FileGetter fileGetter;
	private  FileGetter getFileGetter() throws Exception {
        if (fileGetter == null)
            fileGetter = new FileGetter((IAttachment) AttachmentFactory.getRemoteInstance(), AttachmentFtpFacadeFactory.getRemoteInstance());
        return fileGetter;
    }
	/**
	 * ����ҵ�񵥾ݵ�ID�õ��õ������еĹ����������ҽ�����������ʾ�������б����  
	 * ��ΪUI�༭����"�鿴����"��"�鿴����"��ֻ���ڹ������������鿴,��˽�ֹ"ά��"���ʲ���Ҫ����Ȩ�ޡ�  by Cassiel_peng
	 */
	private boolean getAttachmentNamesToShow() throws Exception{
		        String boID = getSelectBOID();
		        boolean hasAttachment=false;
		        if(boID == null)
		        {
		            return hasAttachment;
		        } 
	        	SelectorItemCollection itemColl=new SelectorItemCollection();
	        	itemColl.add(new SelectorItemInfo("id"));
	        	itemColl.add(new SelectorItemInfo("attachment.name"));
	        	itemColl.add(new SelectorItemInfo("attachment.id"));
	        	EntityViewInfo view=new EntityViewInfo();
	        	view.getSelector().addObjectCollection(itemColl);
	        	view.getSorter().add(new SorterItemInfo("attachment.name"));
	        	FilterInfo filter=new FilterInfo();
	        	filter.getFilterItems().add(new FilterItemInfo("boID",getSelectBOID()));
	        	
	        	view.setFilter(filter);
	        	
	        	BoAttchAssoCollection boAttchColl=BoAttchAssoFactory.getRemoteInstance().getBoAttchAssoCollection(view);
	        	if(boAttchColl!=null&&boAttchColl.size()>0){
	        		comboAttachmentNameList.removeAllItems();
	        		hasAttachment=true;
	        		for(int i=0;i<boAttchColl.size();i++){
	        			AttachmentInfo attachment=(AttachmentInfo)boAttchColl.get(i).getAttachment();
	        			this.comboAttachmentNameList.addItem(attachment);
	        			this.comboAttachmentNameList.setUserObject(attachment);
	        		}
	        	}
	        	return hasAttachment;
	        }
	
	/**
	 * ���"�鿴����"��ť   by Cassiel_peng
	 */
	protected void btnViewAttachment_actionPerformed(ActionEvent e) throws Exception {
		//�õ������б����ѡ�еĸ�����ID
		getFileGetter();
		Object selectObj=this.comboAttachmentNameList.getSelectedItem();
		if(selectObj!=null){
			String attachId=((AttachmentInfo)selectObj).getId().toString();
//			fileGetter.downloadAttachment(attachId);����Ҫ���ظ���
			fileGetter.viewAttachment(attachId);
		}
	};

	public void actionViewContent_actionPerformed(ActionEvent e) throws Exception {
		if(isUseWriteMark&&this.editData != null && this.editData.getId() != null&&this.editData instanceof FDCBillInfo){
			//�����ڹ������е�����Ѿ���ContractContentUI���ص�ʱ����й����������ڴ˴��ٽ��д��� 
//			if(FDCUtils.isRunningWorkflow(this.editData.getId().toString())){
//				ForWriteMarkHelper.isUseWriteMarkForEditUI(this.editData,STATUS_EDIT,this);
//			}else{
				ForWriteMarkHelper.isUseWriteMarkForEditUI(this.editData,getOprtState(),this);
//			}
		}else{
			super.actionViewContent_actionPerformed(e);
			ContractClientUtils.viewContent(this, getSelectBOID());
		}
	}
	protected void getModeByInvite() throws BOSException, EASBizException, IOException{
		if(editData.getProgrammingContract()!=null&&this.prmtcontractType.getValue()!=null){
			EntityViewInfo view=new EntityViewInfo();
			FilterInfo filter=new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("programmingContract.id",editData.getProgrammingContract().getId().toString()));
			view.setFilter(filter);
			view.getSelector().add(new SelectorItemInfo("parent.id"));
			view.getSelector().add(new SelectorItemInfo("parent.inviteType.*"));
			InviteProjectEntriesCollection col=InviteProjectEntriesFactory.getRemoteInstance().getInviteProjectEntriesCollection(view);
			if(col.size()>0){
				this.prmtInviteType.setValue(col.get(0).getParent().getInviteType());
				
				view=new EntityViewInfo();
				filter=new FilterInfo();
				filter.getFilterItems().add(new FilterItemInfo("inviteProject.id",col.get(0).getParent().getId().toString()));
				filter.getFilterItems().add(new FilterItemInfo("contractModel.contractType.id",((ContractTypeInfo)this.prmtcontractType.getValue()).getId().toString()));
				view.setFilter(filter);
				view.getSelector().add(new SelectorItemInfo("contractModel.*"));
				view.getSelector().add(new SelectorItemInfo("contractModel.contractType.*"));
				InviteDocumentsCollection docCol=InviteDocumentsFactory.getRemoteInstance().getInviteDocumentsCollection(view);
				if(docCol.size()>0){
					this.prmtModel.setValue(null);
					removeDataChangeListener(prmtModel);
					this.prmtModel.setValue(docCol.get(0).getContractModel());
					addDataChangeListener(prmtModel);
					editData.setContractModel(docCol.get(0).getContractModel().getId().toString());
					
					SelectorItemCollection sic=new SelectorItemCollection();
					sic.add(new SelectorItemInfo("id"));
					sic.add(new SelectorItemInfo("attachment.*"));
					sic.add(new SelectorItemInfo("assoType"));
					sic.add(new SelectorItemInfo("boID"));
					view=new EntityViewInfo();
					filter=new FilterInfo();
					view.setFilter(filter);
					view.setSelector(sic);
					
					filter.getFilterItems().add(new FilterItemInfo("attachment.attachID",docCol.get(0).getId().toString()));
					filter.getFilterItems().add(new FilterItemInfo("boID",docCol.get(0).getId().toString()));
					filter.getFilterItems().add(new FilterItemInfo("assoType","��׼��ͬ"));
					
					BoAttchAssoCollection cols = BoAttchAssoFactory.getRemoteInstance().getBoAttchAssoCollection(view);
					if(cols.size()>0){
						addModeAttachmentInfo(this.editData.getId().toString(),"��׼��ͬ",cols.get(0).getAttachment().getName()+".",FormetFileSize(cols.get(0).getAttachment().getFile().length),cols.get(0).getAttachment().getFile(),null);
					}else{
						EntityViewInfo viewInfo = new EntityViewInfo();
						viewInfo.getSelector().add("id");
						viewInfo.getSelector().add("fileName");
						viewInfo.getSelector().add("version");
						viewInfo.getSelector().add("fileType");
						viewInfo.getSelector().add("createTime");
						viewInfo.getSelector().add("creator.*");
						viewInfo.getSelector().add("contentFile");
						SorterItemInfo sorterItemInfo = new SorterItemInfo("fileType");
						sorterItemInfo.setSortType(SortType.ASCEND);
						viewInfo.getSorter().add(sorterItemInfo);
						sorterItemInfo = new SorterItemInfo("version");
						sorterItemInfo.setSortType(SortType.DESCEND);
						viewInfo.getSorter().add(sorterItemInfo);
						FilterInfo filterInfo = new FilterInfo();
						filterInfo.appendFilterItem("parent", docCol.get(0).getContractModel().getId().toString());
						viewInfo.setFilter(filterInfo);
						ContractContentCollection contentCollection = ContractContentFactory.getRemoteInstance().getContractContentCollection(viewInfo);
						ContractContentInfo model = (ContractContentInfo)contentCollection.get(0);
						if(model!=null){
							String fullname = model.getFileType();
							BigDecimal version = new BigDecimal("1.0");
							ContractContentInfo contentInfo = new ContractContentInfo();
							contentInfo.setVersion(version);
							contentInfo.setContract(editData);
							contentInfo.setFileType(fullname);
							contentInfo.setContentFile(model.getContentFile());
							ContractContentFactory.getRemoteInstance().save(contentInfo);
							
							addModeAttachmentInfo(this.editData.getId().toString(),"��׼��ͬ",model.getFileType(),FormetFileSize(model.getContentFile().length),model.getContentFile(),"1");
						}
					}
					this.prmtModel.setEnabled(false);
				}
			}
			fillAttachmnetTable();
		}
	}
	public void actionProgram_actionPerformed(ActionEvent e) throws Exception {
		showProgramContract();
		getModeByInvite();
		loadInvite();
	}
	protected void loadInvite() throws BOSException{
		this.tblInvite.removeRows();
		if(editData.getProgrammingContract()!=null){
			InviteProjectEntriesCollection col=InviteProjectEntriesFactory.getRemoteInstance().getInviteProjectEntriesCollection("select parent.id from where programmingContract.id='"+editData.getProgrammingContract().getId().toString()+"'");
			if(col.size()>0){
				EntityViewInfo view = new EntityViewInfo();

				view.getSelector().add(new SelectorItemInfo("*"));
				view.getSelector().add(new SelectorItemInfo("respDept.name"));
				view.getSelector().add(new SelectorItemInfo("creator.name"));
				view.getSelector().add(new SelectorItemInfo("auditor.name"));

				FilterInfo filter = new FilterInfo();
				filter.getFilterItems().add(new FilterItemInfo("inviteProject.id", col.get(0).getParent().getId().toString()));

				view.setFilter(filter);

				TenderAccepterResultCollection tenderCol = TenderAccepterResultFactory.getRemoteInstance().getTenderAccepterResultCollection(view);
				for(int i=0;i<tenderCol.size();i++){
					TenderAccepterResultInfo info = tenderCol.get(i);

					IRow row = this.tblInvite.addRow();

					if (info != null) {
						row.getCell("id").setValue(info.getId());
						row.getCell("number").setValue(info.getNumber());

						if (info.getRespDept() != null) {row.getCell("respDept").setValue(info.getRespDept().getName());
						}
						if (info.getCreator() != null) {
							row.getCell("creator").setValue(info.getCreator().getName());
						}
						row.getCell("createDate").setValue(info.getCreateTime());
						if (info.getAuditor() != null) {
							row.getCell("auditor").setValue(info.getAuditor().getName());
						}
						row.getCell("auditDate").setValue(info.getAuditTime());
						row.getCell("name").setValue(info.getName());
						row.setUserObject(info);
					}
				}
				DirectAccepterResultCollection directCol = DirectAccepterResultFactory.getRemoteInstance().getDirectAccepterResultCollection(view);
				for(int i=0;i<directCol.size();i++){
					DirectAccepterResultInfo info = directCol.get(i);

					IRow row = this.tblInvite.addRow();

					if (info != null) {
						row.getCell("id").setValue(info.getId());
						row.getCell("number").setValue(info.getNumber());

						if (info.getRespDept() != null) {row.getCell("respDept").setValue(info.getRespDept().getName());
						}
						if (info.getCreator() != null) {
							row.getCell("creator").setValue(info.getCreator().getName());
						}
						row.getCell("createDate").setValue(info.getCreateTime());
						if (info.getAuditor() != null) {
							row.getCell("auditor").setValue(info.getAuditor().getName());
						}
						row.getCell("auditDate").setValue(info.getAuditTime());
						row.getCell("name").setValue(info.getName());
						row.setUserObject(info);
					}
				}
			}
		}
	}
	protected void loadSupplyEntry() throws BOSException, SQLException{
		this.kdtSupplyEntry.getStyleAttributes().setLocked(true);
		this.kdtSupplyEntry.getColumn("number").setWidth(200);
		this.kdtSupplyEntry.getColumn("name").setWidth(200);
		this.kdtSupplyEntry.getColumn("amount").setWidth(200);
		this.kdtSupplyEntry.getColumn("localAmount").setWidth(200);
		    
	    this.kdtSupplyEntry.getColumn("amount").getStyleAttributes().setNumberFormat("#,##0.00;-#,##0.00");
	    this.kdtSupplyEntry.getColumn("amount").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
	    this.kdtSupplyEntry.getColumn("localAmount").getStyleAttributes().setNumberFormat("#,##0.00;-#,##0.00");
	    this.kdtSupplyEntry.getColumn("localAmount").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
		this.kdtSupplyEntry.removeRows();
		if(editData.getId()==null||!FDCBillStateEnum.AUDITTED.equals(editData.getState())){
			this.contSrcAmount.setVisible(false);
			return;
		}
		FDCSQLBuilder builder = new FDCSQLBuilder();
		if(ContractPropertyEnum.SUPPLY.equals(contractPropert.getSelectedItem())){
			builder.appendSql(" select parent.fid id,parent.fcontractPropert contractPropert,contractType.fname_l2 contractType,parent.fnumber number,parent.fname name,parent.fbookedDate bookedDate,supplier.fname_l2 partB,parent.foriginalAmount amount,parent.famount localAmount");
			builder.appendSql(" from t_con_contractbillentry entry inner join t_con_contractbill con on con.fid = entry.fparentid and con.fisAmtWithoutCost=1 and");
			builder.appendSql(" con.fcontractPropert='SUPPLY'  inner join T_Con_contractBill parent on parent.fnumber = con.fmainContractNumber and parent.fcurprojectid=con.fcurprojectid");
			builder.appendSql(" left join t_bd_supplier supplier on supplier.fid=parent.fpartBId left join T_FDC_ContractType contractType on contractType.fid=parent.fcontractTypeId where  entry.FRowkey='am' and con.fstate='4AUDITTED' and ");
			builder.appendParam(" con.fid", editData.getId().toString());
			IRowSet rowSet = builder.executeQuery();
			while(rowSet.next()){
				IRow row=this.kdtSupplyEntry.addRow();
				row.setUserObject(rowSet.getString("id"));
				ContractPropertyEnum contractProperty=ContractPropertyEnum.getEnum(rowSet.getString("contractPropert"));
				if(contractProperty!=null){
					row.getCell("contractPropert").setValue(contractProperty.getAlias());
				}
				row.getCell("contractType").setValue(rowSet.getString("contractType"));
				row.getCell("number").setValue(rowSet.getString("number"));
				row.getCell("name").setValue(rowSet.getString("name"));
				row.getCell("partB").setValue(rowSet.getString("partB"));
				row.getCell("bookedDate").setValue(rowSet.getDate("bookedDate"));
				row.getCell("amount").setValue(rowSet.getBigDecimal("amount"));
				row.getCell("localAmount").setValue(rowSet.getBigDecimal("localAmount"));
			}
			this.contSrcAmount.setVisible(false);
		}else{
			builder.appendSql(" select con.fid id,con.fcontractPropert contractPropert,contractType.fname_l2 contractType,con.fnumber number,con.fname name,con.fbookedDate bookedDate,supplier.fname_l2 partB,con.foriginalAmount amount,con.famount localAmount");
			builder.appendSql(" from t_con_contractbillentry entry inner join t_con_contractbill con on con.fid = entry.fparentid and con.fisAmtWithoutCost=1 and");
			builder.appendSql(" con.fcontractPropert='SUPPLY'  inner join T_Con_contractBill parent on parent.fnumber = con.fmainContractNumber and parent.fcurprojectid=con.fcurprojectid");
			builder.appendSql(" left join t_bd_supplier supplier on supplier.fid=con.fpartBId left join T_FDC_ContractType contractType on contractType.fid=con.fcontractTypeId where  entry.FRowkey='am' and con.fstate='4AUDITTED' and ");
			builder.appendParam(" parent.fid", editData.getId().toString());
			IRowSet rowSet = builder.executeQuery();
			while(rowSet.next()){
				IRow row=this.kdtSupplyEntry.addRow();
				row.setUserObject(rowSet.getString("id"));
				ContractPropertyEnum contractProperty=ContractPropertyEnum.getEnum(rowSet.getString("contractPropert"));
				if(contractProperty!=null){
					row.getCell("contractPropert").setValue(contractProperty.getAlias());
				}
				row.getCell("contractType").setValue(rowSet.getString("contractType"));
				row.getCell("number").setValue(rowSet.getString("number"));
				row.getCell("name").setValue(rowSet.getString("name"));
				row.getCell("partB").setValue(rowSet.getString("partB"));
				row.getCell("bookedDate").setValue(rowSet.getDate("bookedDate"));
				row.getCell("amount").setValue(rowSet.getBigDecimal("amount"));
				row.getCell("localAmount").setValue(rowSet.getBigDecimal("localAmount"));
			}
			if(rowSet.size()==0){
				this.contSrcAmount.setVisible(false);
			}
		}
	}
	protected void kdtSupplyEntry_tableClicked(KDTMouseEvent e) throws Exception {
		if (e.getType() == KDTStyleConstants.BODY_ROW && e.getButton() == MouseEvent.BUTTON1 && e.getClickCount() == 2) {
			String id=(String)this.kdtSupplyEntry.getRow(e.getRowIndex()).getUserObject();
			if(id!=null){
				UIContext uiContext = new UIContext(this);
				uiContext.put(UIContext.OWNER, this);
				uiContext.put("ID", id);
				IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.MODEL).create(ContractBillEditUI.class.getName(), uiContext, null, OprtState.VIEW);
				uiWindow.show();
			}
		}
	}
	private void showProgramContract() throws Exception {
		IUIWindow uiWindow = null;
		UIContext uiContext = new UIContext(this);
		ContractBillInfo contractBillInfo = editData;
		if(this.prmtcontractType.getValue()==null){
			FDCMsgBox.showWarning(this,"����ѡ���ͬ���ͣ�");
			return;
		}
		contractBillInfo.setContractType((ContractTypeInfo) this.prmtcontractType.getValue());
		uiContext.put("contractBillInfo", contractBillInfo);
		//�Ƿ�ȫ�ں�ͬ
		uiContext.put("isWholeAgeStage", new Boolean(curProject.isIsWholeAgeStage()));
		uiWindow = UIFactory.createUIFactory(UIFactoryName.MODEL).create(ContractBillLinkProgContEditUI.class.getName(), uiContext, null,OprtState.EDIT);
		uiWindow.show();
		if (contractBillInfo.getProgrammingContract() != null) {
			this.textFwContract.setText(contractBillInfo.getProgrammingContract().getName());
		} else {
			this.textFwContract.setText(null);
		}
	}
	
	/**
	 * ʵ�� IWorkflowUIEnhancement �ӿڱ���ʵ�ֵķ���getWorkflowUIEnhancement by Cassiel_peng 2009-9-21
	 */
	public IWorkflowUIEnhancement getWorkflowUIEnhancement() {
		return new DefaultWorkflowUIEnhancement(){
			public List getApporveToolButtons(CoreUIObject uiObject) {
				
				List toolButtionsList=new ArrayList();
				btnViewContent.setVisible(true);
				toolButtionsList.add(btnViewContent);
				btnViewCost.setVisible(true);
				toolButtionsList.add(btnViewCost);
				btnProgram.setVisible(true);
				toolButtionsList.add(btnProgram);
				btnAccountView.setVisible(true);
				toolButtionsList.add(btnAccountView);
				return toolButtionsList;
			}
		};
	}
	public void actionAudit_actionPerformed(ActionEvent e) throws Exception {
		if(editData==null || editData.getId()==null){
			return;
		}
		checkMainSupplyCon();
	    if(isRelatedTask()){
			checkConRelatedTaskDelUnAudit();
		}
		super.actionAudit_actionPerformed(e);
	}
	private void checkMainSupplyCon() throws BOSException, SQLException {
		FDCSQLBuilder builder = new FDCSQLBuilder();
	    builder.appendSql("select bill.fcontractpropert from t_con_contractbillentry entry ");
	    builder.appendSql("inner join t_con_contractbill bill on  bill.fid=entry.fparentid ");
	    builder.appendSql("inner join t_con_contractbill main on main.fid=entry.fcontent and main.fstate <> '4AUDITTED' ");
	    builder.appendSql("where ");
	    builder.appendParam("bill.fid", editData.getId().toString());
	    builder.appendSql(" and bill.fcontractpropert='SUPPLY' ");
	    builder.appendSql(" and entry.fdetail='��Ӧ����ͬ����' ");
	    IRowSet rs = builder.executeQuery();
	    if(rs!=null&&rs.size()==1){
	    	rs.next();
	    	String prop = rs.getString("fcontractpropert");
	    	if("SUPPLY".equals(prop)){
	    		FDCMsgBox.showWarning(this,"����ѡ��������д�������ͬδ�����Ĳ����ͬ�����ܽ��д˲���!");
		    	this.abort();
	    	}
	    }
	}
	public void actionUnAudit_actionPerformed(ActionEvent e) throws Exception {
		if(editData==null || editData.getId()==null){
			return;
		}
		FDCSQLBuilder builder = new FDCSQLBuilder();
	    builder.appendSql("select * from t_con_contractbillentry entry ");
	    builder.appendSql("inner join t_con_contractbill bill on bill.fid=entry.fparentid ");
	    builder.appendSql("inner join t_con_contractbill main on  main.fid=entry.fcontent ");
	    builder.appendSql("where");
	    builder.appendParam("main.fid", editData.getId().toString());
	    if(isSupply){
	    	builder.appendSql(" and bill.fstate='4AUDITTED'");
	    }
	    IRowSet rs = builder.executeQuery();
	    if(rs!=null&&rs.size()>0){
			FDCMsgBox.showWarning(this, "����ѡ������ݴ��ڱ������ͬ���õĺ�ͬ�����ܽ��д˲���!");
			this.abort();
		}
	    
	    if (!splitBeforeAudit) {
			boolean hasSettleSplit = checkContractHasSplit(this.editData.getId().toString());
			if (hasSettleSplit) {
				MsgBox.showWarning("��ͬ�Ѿ����,���ܷ�����!");
				SysUtil.abort();
				return;
			}
		}
	    
	  //R110603-0148:������ڱ��������������������
	    if (ContractUtil.hasChangeAuditBill(null, editData.getId())) {
    		FDCMsgBox.showWarning(this, EASResource.getString("com.kingdee.eas.fdc.contract.client.ContractResource", "hasChangeAuditBill"));
			this.abort();
    	}
    	
    	//R110603-0148:������ڱ��ָ�������������
    	if (ContractUtil.hasContractChangeBill(null, editData.getId())) {
    		FDCMsgBox.showWarning(this, EASResource.getString("com.kingdee.eas.fdc.contract.client.ContractResource", "hasContractChangeBill"));
			this.abort();
    	}
    	
	    if(isRelatedTask()){
			checkConRelatedTaskDelUnAudit();
		}
		super.actionUnAudit_actionPerformed(e);
	}
	

	
	/**
	 * ����R110125-045����ͬ����_��ͬ����_��ͬ¼��_������ͬʱӡ��˰���޷���������
	 * ��д�˳�����Ĵ˷�����ֻ��ӡ��˰�ʵ�û�б���ֵ��ʱ�򣬲Ÿ�Ĭ��ֵ by zhiyuan_tang
	 */
	protected void applyDefaultValue(IObjectValue vo) {
		if (vo.get("originalAmount") == null) {
			vo.put("originalAmount",new java.math.BigDecimal(0));
		}
		if (vo.get("stampTaxRate") == null) {
			vo.put("stampTaxRate",new java.math.BigDecimal(0));
		}
		if (vo.get("stampTaxAmt") == null) {
			vo.put("stampTaxAmt",new java.math.BigDecimal(0));
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
	private void updateProgrammingContract(ProgrammingContractInfo pc, int isCiting) {
		try {
			String oldPCId=null;
			FDCSQLBuilder buildSQL = new FDCSQLBuilder();
			IRowSet iRowSet = null;
			if(this.editData.getId()!=null){
				buildSQL.appendSql("select fprogrammingContract from t_con_contractBill where fid='" + this.editData.getId().toString() + "'");
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
				buildSQL.appendSql("select count(*) count from t_con_contractBill where fprogrammingContract='" + oldPCId + "' and fid!='" + this.editData.getId().toString() + "'");
				int count = 0;
				iRowSet = buildSQL.executeQuery();
				if (iRowSet.next()) {
					count = iRowSet.getInt("count");
				}
				if(count>0){
					return;
				}
				buildSQL = new FDCSQLBuilder();
				buildSQL.appendSql("update T_CON_ProgrammingContract set FIsCiting = " + isCiting + " where FID = '" + oldPCId + "'");
				buildSQL.executeUpdate();
			}else if(pc!=null){
				buildSQL = new FDCSQLBuilder();
				buildSQL.appendSql("update T_CON_ProgrammingContract set FIsCiting = " + isCiting + " where FID = '" + pc.getId().toString() + "'");
				buildSQL.executeUpdate();
			}
		} catch (BOSException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	//add by david_yang PT043562 2011.03.29
	public void actionAttachment_actionPerformed(ActionEvent e) throws Exception {
		super.actionAttachment_actionPerformed(e);
		fillAttachmnetTable();
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
			filter.getFilterItems().add(new FilterItemInfo("attachment.beizhu", "1",CompareType.NOTEQUALS));
			filter.getFilterItems().add(new FilterItemInfo("attachment.beizhu", null,CompareType.EQUALS));
//			//��Ӳ���Э��
//			filter.getFilterItems().add(new FilterItemInfo("boID", this.editData.getAgreementID()));
//			
//			filter.getFilterItems().add(new FilterItemInfo("assoType","��׼��ͬ",CompareType.NOTEQUALS));
			filter.setMaskString("#0 and (#1 or #2)");
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
//					if(boId.equals(boaInfo.getBoID())){
//						AttachmentInfo attachment = boaInfo.getAttachment();
//							IRow row = tblAttachement.addRow();
//							row.getCell("id").setValue(attachment.getId().toString());
//							row.getCell("seq").setValue(attachment.getAttachID());
//							row.getCell("name").setValue(attachment.getName());
//							row.getCell("date").setValue(attachment.getCreateTime());
//							row.getCell("isAgreement").setValue(Boolean.FALSE);
//					}else{
						AttachmentInfo attachment = boaInfo.getAttachment();
						IRow row = tblAttachement.addRow();
						row.getCell("id").setValue(attachment.getId().toString());
						row.getCell("seq").setValue(attachment.getAttachID());
						row.getCell("name").setValue(attachment.getName());
						row.getCell("date").setValue(attachment.getCreateTime());
						row.getCell("type").setValue(boaInfo.getAssoType());
						if((attachment.getBeizhu()==null||!attachment.getBeizhu().equals("1"))&&(boaInfo.getAssoType()!=null&&boaInfo.getAssoType().equals("��׼��ͬ"))){
							flag=true;
						}else if(boaInfo.getAssoType()!=null&&boaInfo.getAssoType().equals("����Э��")){
							flag =false;
						}
//					}
				}
				this.isHasAttchment = true;
			}else {
				this.isHasAttchment = false;
			}
			if(flag){
				if(this.prmtModel.getValue()!=null){
					this.chkIsStandardContract.setSelected(true);
				}
			} else {
				this.chkIsStandardContract.setSelected(false);
			}
		}
	}
	
	protected void tblAttachement_tableClicked(KDTMouseEvent e)
			throws Exception {
		if(e.getType() == 1 && e.getButton() == 1 && e.getClickCount() == 2)
        {
			IRow row  =  tblAttachement.getRow(e.getRowIndex());
			getFileGetter();
			Object selectObj= row.getCell("id").getValue();
			if(selectObj!=null){
				String attachId=selectObj.toString();
				fileGetter.viewAttachment(attachId);
			}
			
        }
	}
	private boolean ctrlAgreementBtnStatus() throws EASBizException, BOSException{
		boolean isEdit = false;
		
	    if(STATUS_ADDNEW.equals(getOprtState())||STATUS_EDIT.equals(getOprtState()))
	    {
	        isEdit = true;
	    }
	    
	    if(isEdit){
	        String orgId = SysContext.getSysContext().getCurrentOrgUnit().getId().toString();
	        String userId = SysContext.getSysContext().getCurrentUserInfo().getId().toString();
	        String creatorId=this.editData.getCreator()!=null?this.editData.getCreator().getId().toString():null;
			String uiName = ContractBillEditUI.class.getName();
			boolean hasFunctionPermission = PermissionFactory.getRemoteInstance().hasFunctionPermission(
    				new ObjectUuidPK(userId),
    				new ObjectUuidPK(orgId),
    				new MetaDataPK(uiName),
    				new MetaDataPK("ActionAttamentCtrl") );
			//���δ���ò�������Ȩ�޵��û����ܽ��и���ά��,����Ѿ������˲������Ƶ��˵��ڵ�ǰ�û�����Ȩ�޲��ܽ��� ά��
        	if(hasFunctionPermission){
        		//creatorId ==null; Ϊ����,��������
        		if(creatorId!=null){
        			boolean creatorCtrl=FDCUtils.getDefaultFDCParamByKey(null, null, FDCConstants.FDC_PARAM_CREATORATTACHMENT);
        			if(creatorCtrl&&!creatorId.equals(userId)){
        				//�Ƶ���Ҫ���ڵ�ǰ�û�����
        				isEdit=false;
        			}
        		}
        	}else{
        		isEdit=false;
        	}
        }
	    return isEdit;
	 }
	/**
	 *  //���EAS-3Gƽ̨�Ͽ�������׼��ͬ���벹��Э��������
	 * @throws BOSException
	 * @throws EASBizException 
	 */
//	private void dealEAS3GAttachment() throws BOSException, EASBizException{String billId = this.editData.getId().toString();
//	IBoAttchAsso boFacad = BoAttchAssoFactory.getRemoteInstance();
//	IAttachment attachmentFacad = AttachmentFactory.getRemoteInstance();
//	FilterInfo filter1 = new FilterInfo();
//	filter1 = new FilterInfo();
//	//�ٴ��ύ��ʱ����������ϴ������ĺ�ͬ���ģ�����Э����Ҫ��ɾ����Ȼ���ٴ�����
//	filter1.getFilterItems().add(new FilterItemInfo("attachID",billId));
//	attachmentFacad.delete(filter1);
//	//��ͬ����(��׼��ͬ)����ȡ���汾������һ������
//	EntityViewInfo view1 = new EntityViewInfo();
//	filter1 = new FilterInfo();
//	filter1.getFilterItems().add(new FilterItemInfo("contract.id",this.editData.getId()));
//	SorterItemCollection sort = new SorterItemCollection();
//	SorterItemInfo sortItem = new SorterItemInfo("version");
//	sortItem.setSortType(SortType.DESCEND);
//	sort.add(sortItem);
//	view1.setFilter(filter1);
//	view1.setSorter(sort);
//	ContractContentCollection conColl = ContractContentFactory.getRemoteInstance().getContractContentCollection(view1);
//	ContractContentInfo conInfo = null;
//	if(conColl.size() > 0 ) {
//		 conInfo = conColl.get(0);
//		 AttachmentInfo info = new AttachmentInfo();
//		 info.setType("Microsoft Word �ĵ�");
//		 info.setIsShared(false);
//		 info.setName(conInfo.getFileType());
//		 info.setFile(conInfo.getContentFile());
//		 info.setSimpleName("doc");
//		 info.setAttachID(billId);
//		 BoAttchAssoInfo boInfo = new BoAttchAssoInfo();
//		 boInfo.setBoID(billId);
//		 boInfo.setAttachment(info);
//		 info.getBoAttchAsso().clear();
//		 info.getBoAttchAsso().add(boInfo);
//		 attachmentFacad.addnew(info);
//	}
//	
//	//����Э�飺ֱ������һ�������ķ�¼��boid = editData.id
//		EntityViewInfo view = new EntityViewInfo();
//		SelectorItemCollection sel = new SelectorItemCollection();
//		sel.add("*");
//		sel.add("attachment.*");
//		view.setSelector(sel);
//		FilterInfo filter = new FilterInfo();
//		filter.getFilterItems().add(new FilterItemInfo("boID",this.editData.getAgreementID()));
//		view.setFilter(filter);
//		BoAttchAssoCollection attColl = boFacad.getBoAttchAssoCollection(view);
//		for(int i = 0 ; i < attColl.size(); i ++){
//			BoAttchAssoInfo attInfo = attColl.get(i);
//			attInfo = (BoAttchAssoInfo)attInfo.clone();
//			attInfo.setId(BOSUuid.create(attInfo.getBOSType()));
//			attInfo.setBoID(billId);
//			AttachmentInfo info = (AttachmentInfo)attInfo.getAttachment().clone();
//			info.getBoAttchAsso().clear();
//			info.getBoAttchAsso().add(attInfo);
//			info.setAttachID(billId);
//			info.setId(null);
//			attInfo.setAttachment(info);
//			attachmentFacad.addnew(info);
//		}
//	}
	protected void tblInvite_tableClicked(KDTMouseEvent e) throws Exception {
		if (e.getType() == KDTStyleConstants.BODY_ROW && e.getButton() == MouseEvent.BUTTON1 && e.getClickCount() == 2) {
			IRow row = this.tblInvite.getRow(e.getRowIndex());
			if(row.getUserObject()==null) return;
			String editUI=null;
			if(row.getUserObject() instanceof TenderAccepterResultInfo){
				editUI=TenderAccepterResultEditUI.class.getName();
			}else {
				editUI=DirectAccepterResultEditUI.class.getName();
			}
			UIContext uiContext = new UIContext(this);
			uiContext.put("ID", row.getCell("id").getValue().toString());
			IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.MODEL).create(editUI, uiContext, null, OprtState.VIEW);
			uiWindow.show();
		}
	}
	public void actionDownLoad_actionPerformed(ActionEvent e) throws Exception {
		if(this.prmtModel.getValue()==null){
			FDCMsgBox.showInfo(this,"����ѡ���׼��ͬ��");
			return;
		}
		String id=null;
		SelectorItemCollection sic=new SelectorItemCollection();
		sic.add(new SelectorItemInfo("attachment.id"));
		FilterInfo filter = new FilterInfo();
		EntityViewInfo view=new EntityViewInfo();
		filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("attachment.attachID",this.editData.getId().toString()));
		filter.getFilterItems().add(new FilterItemInfo("boID",this.editData.getId().toString()));
		filter.getFilterItems().add(new FilterItemInfo("assoType","��׼��ͬ"));
		view.setFilter(filter);
		view.setSelector(sic);
		
		BoAttchAssoCollection cols = BoAttchAssoFactory.getRemoteInstance().getBoAttchAssoCollection(view);
		if(cols.size()==0){
			sic=new SelectorItemCollection();
			sic.add(new SelectorItemInfo("fileType"));
			sic.add(new SelectorItemInfo("contentFile"));
			FDCSQLBuilder _builder = new FDCSQLBuilder();
			_builder.appendSql(" select top 1 fid from T_CON_ContractContent where fcontractid='"+this.editData.getId().toString()+"' order by fversion desc");
			final IRowSet rowSet = _builder.executeQuery();
			if(rowSet.size()>0){
				while (rowSet.next()) {
					ContractContentInfo info=ContractContentFactory.getRemoteInstance().getContractContentInfo(new ObjectUuidPK(rowSet.getString("fid")),sic);
					id=addModeAttachmentInfo(this.editData.getId().toString(),"��׼��ͬ",info.getFileType(),FormetFileSize(info.getContentFile().length),info.getContentFile(),"1");
				}
			}else{
				ContractModelInfo contractModel=(ContractModelInfo) this.prmtModel.getValue();
				EntityViewInfo viewInfo = new EntityViewInfo();
				viewInfo.getSelector().add("id");
				viewInfo.getSelector().add("fileName");
				viewInfo.getSelector().add("version");
				viewInfo.getSelector().add("fileType");
				viewInfo.getSelector().add("createTime");
				viewInfo.getSelector().add("creator.*");
				viewInfo.getSelector().add("contentFile");
				SorterItemInfo sorterItemInfo = new SorterItemInfo("fileType");
				sorterItemInfo.setSortType(SortType.ASCEND);
				viewInfo.getSorter().add(sorterItemInfo);
				sorterItemInfo = new SorterItemInfo("version");
				sorterItemInfo.setSortType(SortType.DESCEND);
				viewInfo.getSorter().add(sorterItemInfo);
				FilterInfo filterInfo = new FilterInfo();
				filterInfo.appendFilterItem("parent", contractModel.getId().toString());
				viewInfo.setFilter(filterInfo);
				ContractContentCollection contentCollection = ContractContentFactory.getRemoteInstance().getContractContentCollection(viewInfo);
				ContractContentInfo model = (ContractContentInfo)contentCollection.get(0);
				if(model!=null){
					String fullname = model.getFileType();
					editData.setContractModel(contractModel.getId().toString());
					SelectorItemCollection sel = new SelectorItemCollection();
					sel.add("contractModel");
					BigDecimal version = new BigDecimal("1.0");
					ContractContentInfo contentInfo = new ContractContentInfo();
					contentInfo.setVersion(version);
					contentInfo.setContract(editData);
					contentInfo.setFileType(fullname);
					contentInfo.setContentFile(model.getContentFile());
					ContractContentFactory.getRemoteInstance().save(contentInfo);
					
					addModeAttachmentInfo(this.editData.getId().toString(),"��׼��ͬ",model.getFileType(),FormetFileSize(model.getContentFile().length),model.getContentFile(),"1");
				}
			}
		}else if(cols.size()>1){
			FDCMsgBox.showInfo(this,"��׼��ͬ���ڶ����");
			return;
		}else{
			id=cols.get(0).getAttachment().getId().toString(); 
		}
		String file=getFileGetter().downloadAttachment(id, this);
		if(file!=null&&!"".equals(file)){
			FDCMsgBox.showInfo(this,"��׼��ͬ���سɹ���");
		}
	}

	public void actionUpLoad_actionPerformed(ActionEvent e) throws Exception {
		if(this.prmtModel.getValue()==null){
			FDCMsgBox.showInfo(this,"����ѡ���׼��ͬ��");
			return;
		}
		SelectorItemCollection sic=new SelectorItemCollection();
		sic.add(new SelectorItemInfo("attachment.file"));
		sic.add(new SelectorItemInfo("attachment.name"));
		FilterInfo filter = new FilterInfo();
		EntityViewInfo view=new EntityViewInfo();
		filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("attachment.attachID",this.editData.getId().toString()));
		filter.getFilterItems().add(new FilterItemInfo("boID",this.editData.getId().toString()));
		filter.getFilterItems().add(new FilterItemInfo("assoType","��׼��ͬ"));
		
		view.setFilter(filter);
		view.setSelector(sic);
		
		BoAttchAssoCollection cols = BoAttchAssoFactory.getRemoteInstance().getBoAttchAssoCollection(view);
		if(cols.size()==0){
			FDCMsgBox.showInfo(this,"�ޱ�׼��ͬ��");
			return;
		}else if(cols.size()>1){
			FDCMsgBox.showInfo(this,"��׼��ͬ���ڶ����");
			return;
		}
		
		KDFileChooser fc = new KDFileChooser(System.getProperty("user.home"));
		fc.setFileSelectionMode(0);
		fc.setMultiSelectionEnabled(false);
		FileFilter ff = new FileFilter(){
            public boolean accept(File pathname) {
                if (pathname.isFile()&&
                		pathname.getName().toUpperCase().endsWith(".DOC")) {
                    return true;
                }
                return false;
            }
			public String getDescription() {
				return ".DOC";
			}};
		fc.setFileFilter(ff);
		int retVal = fc.showOpenDialog(this);
		if(retVal == 0){
			File retFile=fc.getSelectedFile();
			if(retFile!=null){
				if(!retFile.exists()){
					FDCMsgBox.showInfo(this,Resrcs.getString("FileNotExisted"));
		        }else if(retFile!=null&&retFile.length() > 52428800L){
		        	FDCMsgBox.showInfo(this,Resrcs.getString("FileSizeNotAllowed"));
		        }else{
		        	File file = File.createTempFile("temp"+cols.get(0).getAttachment().getName(), ".DOC");
		    		FileOutputStream fos = new FileOutputStream(file);
		    		fos.write(cols.get(0).getAttachment().getFile());
		    		fos.close();
		    		
		    		FileInputStream in = new FileInputStream(file);
		    		FileInputStream newIn = new FileInputStream(retFile);
		    		try {
						WordExtractor extractor = new WordExtractor(in);
						String instr = extractor.getParagraphText()[0];
						if(instr.indexOf("\r\n")>0){
							instr=instr.split("\r\n")[0];
						}
						if(instr.indexOf("\r")>0){
							instr=instr.split("\r")[0];
						}
						in.close();
						
						WordExtractor newExtractor = new WordExtractor(newIn);
						String newInstr = newExtractor.getParagraphText()[0];
						if(newInstr.indexOf("\r\n")>0){
							newInstr=newInstr.split("\r\n")[0];
						}
						if(newInstr.indexOf("\r")>0){
							newInstr=newInstr.split("\r")[0];
						}
						newIn.close();
						
						if(!newInstr.equals(instr)){
			        		FDCMsgBox.showInfo(this,"��׼��ͬ�汾��һ�£�");
			        		return;
			        	}
					} catch (Exception e2) {
						FDCMsgBox.showInfo(this,"��ʹ�ñ�׼��ͬ��Microsoft Word�汾��");
		        		return;
					}finally{
						in.close();
						newIn.close();
					}
		        	
		        	long len = retFile.length();
		    		byte[] bytes = new byte[(int)len];
		    		
		    		FileInputStream fi=new FileInputStream(retFile);
		    		BufferedInputStream bi=new BufferedInputStream(fi);
		    		int size=fi.available();
		    		bi.read(bytes);
		    		deleteTypeAttachment(this.editData.getId().toString(),"��׼��ͬ");
		    		addModeAttachmentInfo(this.editData.getId().toString(),"��׼��ͬ",retFile.getName(),FormetFileSize(size),bytes,null);
		    		bi.close();
		    		fi.close();
		    		
		    		FDCMsgBox.showInfo(this,"��׼��ͬ�ϴ��ɹ���");
		    		fillAttachmnetTable();
		        }
			}
		}
	}
	public void actionAgreementText_actionPerformed(ActionEvent e)throws Exception {
		AttachmentClientManager acm = AttachmentManagerFactory.getClientManager();
		String boID = getSelectBOID();
        if(boID == null)
        {
            return;
        }
        if(this.editData.getAgreementID()!=null){
			acm.showAttachmentListUIByBoID(this.editData.getAgreementID(), this,ctrlAgreementBtnStatus());
        }else{
        	this.editData.setAgreementID(BOSUuid.create(this.editData.getBOSType()).toString());
        	acm.showAttachmentListUIByBoID(this.editData.getAgreementID(), this,ctrlAgreementBtnStatus());
        }
        EntityViewInfo view = new EntityViewInfo();
        FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("boID",this.editData.getAgreementID()));
		SelectorItemCollection sel = new SelectorItemCollection();
		sel.add("attachment.size");
		sel.add("attachment.file");
		sel.add("attachment.name");
		view.setSelector(sel);
		view.setFilter(filter);
		BoAttchAssoCollection attColl = BoAttchAssoFactory.getRemoteInstance().getBoAttchAssoCollection(view);
		deleteTypeAttachment(this.editData.getId().toString(),"����Э��");
		for(int i = 0 ; i < attColl.size(); i ++){
	    	addModeAttachmentInfo(this.editData.getId().toString(),"����Э��",attColl.get(i).getAttachment().getName(),attColl.get(i).getAttachment().getSize(),attColl.get(i).getAttachment().getFile(),null);
		}
        fillAttachmnetTable();
	}
	private String addModeAttachmentInfo(String boId,String type,String fullname,String size,byte[] bytes,String beizhu) throws EASBizException, BOSException, IOException{
		AttachmentInfo info = new AttachmentInfo();
		info.setType("Microsoft Word �ĵ�");
		info.setIsShared(false);
		if(fullname.indexOf(".")>0){
			info.setName(fullname.substring(0,fullname.lastIndexOf(".")));
		}else{
			info.setName(fullname);
		}
		info.setFile(bytes);
		info.setSize(size);
		info.setSimpleName("doc");
		info.setAttachID(boId);
		info.setBeizhu(beizhu);
		info.setDescription(type+"����ɾ����");
		BoAttchAssoInfo boInfo = new BoAttchAssoInfo();
		boInfo.setBoID(boId);
		boInfo.setAssoType(type);
		boInfo.setAttachment(info);
		info.getBoAttchAsso().clear();
		info.getBoAttchAsso().add(boInfo);
		IObjectPK pk=AttachmentFactory.getRemoteInstance().addnew(info);
		return pk.toString();
	}
	public String FormetFileSize(long fileS) {
		DecimalFormat df = new DecimalFormat("#.00");
		String fileSizeString = "";
		if (fileS < 1024) {
			fileSizeString = df.format((double) fileS) + "B";
		} else if (fileS < 1048576) {
			fileSizeString = df.format((double) fileS / 1024) + "KB";
		} else if (fileS < 1073741824) {
			fileSizeString = df.format((double) fileS / 1048576) + "M";
		} else {
			fileSizeString = df.format((double) fileS / 1073741824) + "G";
		}
		return fileSizeString;
	}
	private void deleteContractContent() throws BOSException{
		FDCSQLBuilder _builder = new FDCSQLBuilder();
		_builder.appendSql(" delete from T_CON_ContractContent where fcontractid='"+this.editData.getId().toString()+"'");
		_builder.executeUpdate();
	}
	public boolean destroyWindow() {
		//���û�б��浥�ݣ���Ҫ��������ȥ�ĺ�ͬ����ɾ��
		boolean b = super.destroyWindow();
        if(b){
        	try {
    			IContractBill contractBillFacade = null;
    			contractBillFacade = ContractBillFactory.getRemoteInstance();
    			if(!contractBillFacade.exists(new ObjectUuidPK(this.editData.getId().toString()))){
    				deleteContractContent();
    				
    				if(this.editData.getAgreementID()!=null){
    					deleteAttachment(this.editData.getAgreementID());
    				}
    				deleteAttachment(this.editData.getId().toString());
    			}
    		} catch (Exception e) {
    			e.printStackTrace();
    			logger.error(e);
    		}
        }
        return b;
	}
	protected void deleteTypeAttachment(String id,String type) throws BOSException, EASBizException{
		EntityViewInfo view=new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		
		filter.getFilterItems().add(new FilterItemInfo("boID" , id));
		filter.getFilterItems().add(new FilterItemInfo("assoType",type));
		
		view.setFilter(filter);
		BoAttchAssoCollection col=BoAttchAssoFactory.getRemoteInstance().getBoAttchAssoCollection(view);
		for(int i=0;i<col.size();i++){
			FilterInfo deleteFilter = new FilterInfo();
			deleteFilter.getFilterItems().add(new FilterItemInfo("id" , col.get(i).getAttachment().getId().toString()));
			AttachmentFactory.getRemoteInstance().delete(deleteFilter);
		}
	}
	protected void deleteAttachment(String id) throws BOSException, EASBizException{
		EntityViewInfo view=new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		
		filter.getFilterItems().add(new FilterItemInfo("boID" , id));
		view.setFilter(filter);
		BoAttchAssoCollection col=BoAttchAssoFactory.getRemoteInstance().getBoAttchAssoCollection(view);
		for(int i=0;i<col.size();i++){
			EntityViewInfo attview=new EntityViewInfo();
			FilterInfo attfilter = new FilterInfo();
			
			attfilter.getFilterItems().add(new FilterItemInfo("attachment.id" , col.get(i).getAttachment().getId().toString()));
			attview.setFilter(attfilter);
			BoAttchAssoCollection attcol=BoAttchAssoFactory.getRemoteInstance().getBoAttchAssoCollection(attview);
			if(attcol.size()==1){
				BizobjectFacadeFactory.getRemoteInstance().delTempAttachment(id);
			}else if(attcol.size()>1){
				BoAttchAssoFactory.getRemoteInstance().delete(filter);
			}
		}
	}
	
	protected void initTable() {
		this.kdtDetailEntry.checkParsed();
		
		KDWorkButton btnAddRowinfo = new KDWorkButton();
		KDWorkButton btnInsertRowinfo = new KDWorkButton();
		KDWorkButton btnDeleteRowinfo = new KDWorkButton();

		this.actionDetailALine.putValue("SmallIcon", EASResource.getIcon("imgTbtn_addline"));
		btnAddRowinfo = (KDWorkButton) this.kDContainer3.add(this.actionDetailALine);
		btnAddRowinfo.setText("������");
		btnAddRowinfo.setSize(new Dimension(140, 19));

		this.actionDetailILine.putValue("SmallIcon", EASResource.getIcon("imgTbtn_insert"));
		btnInsertRowinfo = (KDWorkButton) this.kDContainer3.add(this.actionDetailILine);
		btnInsertRowinfo.setText("������");
		btnInsertRowinfo.setSize(new Dimension(140, 19));

		this.actionDetailRLine.putValue("SmallIcon", EASResource.getIcon("imgTbtn_deleteline"));
		btnDeleteRowinfo = (KDWorkButton) this.kDContainer3.add(this.actionDetailRLine);
		btnDeleteRowinfo.setText("ɾ����");
		btnDeleteRowinfo.setSize(new Dimension(140, 19));
		
		
		this.kdtDetailEntry.getColumn("detail").setRequired(true);
		this.kdtDetailEntry.getColumn("totalAmount").setRequired(true);
		this.kdtDetailEntry.getColumn("rate").setRequired(true);
		this.kdtDetailEntry.getColumn("amount").setRequired(true);
		
		KDFormattedTextField amount = new KDFormattedTextField();
		amount.setDataType(KDFormattedTextField.BIGDECIMAL_TYPE);
		amount.setDataVerifierType(KDFormattedTextField.NO_VERIFIER);
		amount.setNegatived(false);
		amount.setPrecision(2);
		KDTDefaultCellEditor amountEditor = new KDTDefaultCellEditor(amount);
		this.kdtDetailEntry.getColumn("totalAmount").setEditor(amountEditor);
		this.kdtDetailEntry.getColumn("totalAmount").getStyleAttributes().setNumberFormat("#,##0.00;-#,##0.00");
		this.kdtDetailEntry.getColumn("totalAmount").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.getAlignment("right"));
		
		this.kdtDetailEntry.getColumn("amount").setEditor(amountEditor);
		this.kdtDetailEntry.getColumn("amount").getStyleAttributes().setNumberFormat("#,##0.00;-#,##0.00");
		this.kdtDetailEntry.getColumn("amount").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.getAlignment("right"));
		this.kdtDetailEntry.getColumn("amount").getStyleAttributes().setLocked(true);
		
		KDFormattedTextField rateamount = new KDFormattedTextField();
		rateamount.setDataType(KDFormattedTextField.BIGDECIMAL_TYPE);
		rateamount.setDataVerifierType(KDFormattedTextField.NO_VERIFIER);
		rateamount.setNegatived(false);
		rateamount.setPrecision(2);
		rateamount.setMaximumValue(new BigDecimal(100));
		rateamount.setMinimumValue(FDCHelper.ZERO);
		KDTDefaultCellEditor rateamountEditor = new KDTDefaultCellEditor(rateamount);
		
		this.kdtDetailEntry.getColumn("rate").setEditor(rateamountEditor);
		this.kdtDetailEntry.getColumn("rate").getStyleAttributes().setNumberFormat("#,##0.00;-#,##0.00");
		this.kdtDetailEntry.getColumn("rate").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.getAlignment("right"));
		
		ActionMap actionMap = this.kdtDetailEntry.getActionMap();
		actionMap.remove(KDTAction.CUT);
		actionMap.remove(KDTAction.DELETE);
		actionMap.remove(KDTAction.PASTE);
		
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
		kdtDetailEntry.getColumn("rate").setRenderer(render_scale);
		
		
		this.tblMarket.checkParsed();
		this.tblMarket.setEditable(true);
		btnAddRowinfo = new KDWorkButton();
		btnDeleteRowinfo = new KDWorkButton();

		this.actionMALine.putValue("SmallIcon", EASResource.getIcon("imgTbtn_addline"));
		btnAddRowinfo = (KDWorkButton) this.kDContainer4.add(this.actionMALine);
		btnAddRowinfo.setText("������");
		btnAddRowinfo.setSize(new Dimension(140, 19));

		this.actionMRLine.putValue("SmallIcon", EASResource.getIcon("imgTbtn_deleteline"));
		btnDeleteRowinfo = (KDWorkButton) this.kDContainer4.add(this.actionMRLine);
		btnDeleteRowinfo.setText("ɾ����");
		btnDeleteRowinfo.setSize(new Dimension(140, 19));
		
		this.tblMarket.getColumn("amount").setEditor(amountEditor);
		
		this.tblMarket.getColumn("rate").setEditor(rateamountEditor);
		this.tblMarket.getColumn("rate").getStyleAttributes().setNumberFormat("#,##0.00;-#,##0.00");
		this.tblMarket.getColumn("rate").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.getAlignment("right"));
		
		tblMarket.getColumn("rate").setRenderer(render_scale);
		
		String formatString = "yyyy-MM-dd";
		this.tblMarket.getColumn("date").getStyleAttributes().setNumberFormat(formatString);
		
		this.tblMarket.getColumn("date").setRequired(true);
		this.tblMarket.getColumn("rate").setRequired(true);
		this.tblMarket.getColumn("amount").setRequired(true);
		
		this.tblMarket.getColumn("amount").getStyleAttributes().setLocked(true);
		this.tblMarket.getColumn("date").getStyleAttributes().setLocked(true);
		this.tblMarket.getColumn("rate").getStyleAttributes().setLocked(true);
		
		this.kdtYZEntry.checkParsed();
		this.kdtYZEntry.setEditable(true);
		btnAddRowinfo = new KDWorkButton();
		btnDeleteRowinfo = new KDWorkButton();

		this.actionYZALine.putValue("SmallIcon", EASResource.getIcon("imgTbtn_addline"));
		btnAddRowinfo = (KDWorkButton) this.kDContainer5.add(this.actionYZALine);
		btnAddRowinfo.setText("������");
		btnAddRowinfo.setSize(new Dimension(140, 19));

		this.actionYZRLine.putValue("SmallIcon", EASResource.getIcon("imgTbtn_deleteline"));
		btnDeleteRowinfo = (KDWorkButton) this.kDContainer5.add(this.actionYZRLine);
		btnDeleteRowinfo.setText("ɾ����");
		btnDeleteRowinfo.setSize(new Dimension(140, 19));
		
		this.kdtYZEntry.getColumn("name").getStyleAttributes().setLocked(true);
		this.kdtYZEntry.getColumn("admin").getStyleAttributes().setLocked(true);
		this.kdtYZEntry.getColumn("type").getStyleAttributes().setLocked(true);
		this.kdtYZEntry.getColumn("count").setRequired(true);
	}
	public void actionYZALine_actionPerformed(ActionEvent e) throws Exception {
		UIContext uiContext = new UIContext(this);
		uiContext.put("table",this.kdtYZEntry);
        IUIFactory uiFactory = UIFactory.createUIFactory(UIFactoryName.MODEL);
        IUIWindow uiWindow = uiFactory.create(QJ_YZSelectUI.class.getName(), uiContext,null,OprtState.VIEW);
        uiWindow.show();
	}
	public void actionYZRLine_actionPerformed(ActionEvent e) throws Exception {
		int activeRowIndex = kdtYZEntry.getSelectManager().getActiveRowIndex();
		if(activeRowIndex<0){
			FDCMsgBox.showError("����ѡ��һ������");
			abort();
		}
		kdtYZEntry.removeRow(activeRowIndex);
	}
	protected void kdtYZEntry_editStopped(KDTEditEvent e) throws Exception {
		
	}
	protected void cbJzType_itemStateChanged(ItemEvent e) throws Exception {
		if(isLoad){
			return;
		}
		setMarketEntry();
	}
	protected void pkJzEndDate_dataChanged(DataChangeEvent e) throws Exception {
		setMarketEntry();
	}
	protected void pkJzStartDate_dataChanged(DataChangeEvent e) throws Exception {
		setMarketEntry();
	}
	private void setMarketEntry() throws ParseException{
		if(this.pkJzStartDate.getValue()!=null&&this.pkJzEndDate.getValue()!=null&&this.cbJzType.getSelectedItem()!=null){
			Date startDate=(Date) this.pkJzStartDate.getValue();
			Date endDate=(Date) this.pkJzEndDate.getValue();
			JZTypeEnum type=(JZTypeEnum) this.cbJzType.getSelectedItem();
			this.tblMarket.removeRows();
			if(type.equals(JZTypeEnum.YI)){
				IRow row=this.tblMarket.addRow();
				row.getCell("date").setValue(startDate);
				row.getCell("rate").setValue(new BigDecimal(100));
				row.getCell("amount").setValue(this.txtLocalAmount.getBigDecimalValue());
			}else{
				List monthList=getMonth(startDate,endDate);
				int days=FDCDateHelper.getDiffDays(startDate, endDate);
				BigDecimal sub=FDCHelper.divide(this.txtLocalAmount.getBigDecimalValue(), days, 2, BigDecimal.ROUND_HALF_UP);
				BigDecimal allRate=FDCHelper.ZERO;
				BigDecimal allAmount=FDCHelper.ZERO;
				for(int i=0;i<monthList.size();i++){
					IRow row=this.tblMarket.addRow();
					row.getCell("date").setValue(FDCDateHelper.getFirstDayOfMonth(((Date) monthList.get(i))));
					BigDecimal amount=FDCHelper.ZERO;
					BigDecimal rate=FDCHelper.ZERO;
					if(i==0){
						amount=FDCHelper.multiply(sub, FDCDateHelper.getDiffDays(startDate, FDCDateHelper.getLastDayOfMonth(((Date) monthList.get(i)))));
						rate=FDCHelper.divide(FDCHelper.multiply(amount, new BigDecimal(100)), this.txtLocalAmount.getBigDecimalValue(), 2, BigDecimal.ROUND_HALF_UP);
						allRate=FDCHelper.add(allRate, rate);
						allAmount=FDCHelper.add(allAmount, amount);
					}else if(i==monthList.size()-1){
						amount=FDCHelper.subtract(this.txtLocalAmount.getBigDecimalValue(), allAmount);
//						amount=FDCHelper.divide(FDCHelper.multiply(this.txtLocalAmount.getBigDecimalValue(),rate), new BigDecimal(100), 2, BigDecimal.ROUND_HALF_UP);
						rate=FDCHelper.subtract(100, allRate);
					}else{
						amount=FDCHelper.multiply(sub, FDCDateHelper.getDiffDays(FDCDateHelper.getFirstDayOfMonth(((Date) monthList.get(i))), FDCDateHelper.getLastDayOfMonth(((Date) monthList.get(i)))));
						rate=FDCHelper.divide(FDCHelper.multiply(amount, new BigDecimal(100)), this.txtLocalAmount.getBigDecimalValue(), 2, BigDecimal.ROUND_HALF_UP);
						allRate=FDCHelper.add(allRate, rate);
						allAmount=FDCHelper.add(allAmount, amount);
					}
					
					row.getCell("rate").setValue(rate);
					row.getCell("amount").setValue(amount);
					
//					if(i==monthList.size()-1){
//						row.getCell("rate").setValue(FDCHelper.subtract(100, allRate));
//					}else{
//						row.getCell("rate").setValue(rate);
//						allRate=FDCHelper.add(allRate, rate);
//					}
//					row.getCell("amount").setValue(FDCHelper.divide(FDCHelper.multiply(this.txtLocalAmount.getBigDecimalValue(),row.getCell("rate").getValue()), new BigDecimal(100), 2, BigDecimal.ROUND_HALF_UP));
				}
			}
		}
	}
	protected void prmtMpCostAccount_dataChanged(DataChangeEvent e)throws Exception {
		
	}
	 /**
     * ��ȡ��������֮�������·�
     * @param startDate
     * @param endDate
     * @return
     * @throws ParseException
     */
    private static List getMonth(Date startDate, Date endDate) throws ParseException {
        List list = new ArrayList();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
        Calendar c1 = Calendar.getInstance();
        Calendar c2 = Calendar.getInstance();
        c1.setTime(startDate);
        c2.setTime(endDate);
        int  year = c2.get(Calendar.YEAR) - c1.get(Calendar.YEAR);
        int month = c2.get(Calendar.MONTH)+year*12 - c1.get(Calendar.MONTH);
        for(int i = 0;i<=month;i++){
            c1.setTime(startDate);
            c1.add(c1.MONTH, i);
            list.add(c1.getTime());
        }
        return list;
    }

	protected void prmtMpCostAccount_willShow(SelectorEvent e) throws Exception {
		Set id=new HashSet();
		if(prmtMarketProject.getValue()!=null){
			MarketProjectCollection mpcol=MarketProjectFactory.getRemoteInstance().getMarketProjectCollection("select amount,costEntry.*,costEntry.costAccount.* from where id='"+((MarketProjectInfo)prmtMarketProject.getValue()).getId()+"'");
			for(int i=0;i<mpcol.size();i++){
				for(int j=0;j<mpcol.get(i).getCostEntry().size();j++){
					if(MarketCostTypeEnum.CONTRACT.equals(mpcol.get(i).getCostEntry().get(j).getType())){
						MarketProjectInfo mp=(MarketProjectInfo) prmtMarketProject.getValue();
						ContractBillCollection con=ContractBillFactory.getRemoteInstance().getContractBillCollection("select * from where marketProject.id='"+mp.getId()+"' and mpCostAccount.id='"+mpcol.get(i).getCostEntry().get(j).getCostAccount().getId()+"' and id!='"+this.editData.getId()+"'");
						if(con.size()==0)
							id.add(mpcol.get(i).getCostEntry().get(j).getCostAccount().getId().toString());
					}
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
	protected void tblMarket_editStopped(KDTEditEvent e) throws Exception {
		 IRow r = this.tblMarket.getRow(e.getRowIndex());
		 int colIndex = e.getColIndex();
		 if(colIndex == this.tblMarket.getColumnIndex("rate")){
			 r.getCell("amount").setValue(FDCHelper.divide(FDCHelper.multiply(this.txtLocalAmount.getBigDecimalValue(), r.getCell("rate").getValue()), new BigDecimal(100), 2, BigDecimal.ROUND_HALF_UP));
		 }
		 if(colIndex == this.tblMarket.getColumnIndex("date")){
			 if(r.getCell("date").getValue()!=null){
				 Calendar c = Calendar.getInstance();
				 int year = c.get(Calendar.YEAR);
				 
				 c = Calendar.getInstance();
				 c.setTime((Date) r.getCell("date").getValue());
				 int comyear = c.get(Calendar.YEAR);
				 
				 String	paramValue = ParamControlFactory.getRemoteInstance().getParamValue(new ObjectUuidPK(SysContext.getSysContext().getCurrentOrgUnit().getId()), "YF_MARKETPROJECT");
				 if("true".equals(paramValue)){
					 if(year>comyear){
						 FDCMsgBox.showError("��ݲ���С�ڵ�ǰ��ݣ�");
						 r.getCell("date").setValue(null);
						 abort();
					 }
				 }
			 }
		 }
	}

	public void actionMALine_actionPerformed(ActionEvent e) throws Exception {
		IRow row = this.tblMarket.addRow();
		ContractMarketEntryInfo info = new ContractMarketEntryInfo();
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

	public void actionDetailALine_actionPerformed(ActionEvent e) throws Exception {
		int year=0;
		int month=0;
		IRow row = this.kdtDetailEntry.addRow();
		ContractBillRateEntryInfo info = new ContractBillRateEntryInfo();
		info.setId(BOSUuid.create(info.getBOSType()));
		
		row.setUserObject(info);
	}
	public void actionDetailILine_actionPerformed(ActionEvent e) throws Exception {
		IRow row = null;
		if (this.kdtDetailEntry.getSelectManager().size() > 0) {
			int top = this.kdtDetailEntry.getSelectManager().get().getTop();
			if (isTableColumnSelected(this.kdtDetailEntry)){
				actionDetailALine_actionPerformed(e);
			}else{
				ContractBillRateEntryInfo info = new ContractBillRateEntryInfo();
				info.setId(BOSUuid.create(info.getBOSType()));
				
				row = this.kdtDetailEntry.addRow(top);
				row.setUserObject(info);
			}
		} else {
			actionDetailALine_actionPerformed(e);
		}
	}
	public boolean confirmRemove(Component comp) {
		return FDCMsgBox.isYes(FDCMsgBox.showConfirm2(comp, EASResource.getString("com.kingdee.eas.framework.FrameWorkResource.Confirm_Delete")));
	}
	public void actionDetailRLine_actionPerformed(ActionEvent e) throws Exception {
		if (this.kdtDetailEntry.getSelectManager().size() == 0 || isTableColumnSelected(this.kdtDetailEntry)) {
			FDCMsgBox.showInfo(this, EASResource.getString("com.kingdee.eas.framework.FrameWorkResource.Msg_NoneEntry"));
			return;
		}
		if (confirmRemove(this)) {
			int top = this.kdtDetailEntry.getSelectManager().get().getBeginRow();
			int bottom = this.kdtDetailEntry.getSelectManager().get().getEndRow();
			for (int i = top; i <= bottom; i++) {
				if (this.kdtDetailEntry.getRow(top) == null) {
					FDCMsgBox.showInfo(this, EASResource.getString("com.kingdee.eas.framework.FrameWorkResource.Msg_NoneEntry"));
					return;
				}
				this.kdtDetailEntry.removeRow(top);
			}
		}
	}
	protected void kdtDetailEntry_editStopped(KDTEditEvent e) throws Exception {
		if("totalAmount".equals(this.kdtDetailEntry.getColumnKey(e.getColIndex()))
				||"rate".equals(this.kdtDetailEntry.getColumnKey(e.getColIndex()))){
			BigDecimal rate=FDCHelper.add(new BigDecimal(1), FDCHelper.divide(this.kdtDetailEntry.getRow(e.getRowIndex()).getCell("rate").getValue(), new BigDecimal(100), 4, BigDecimal.ROUND_HALF_UP));
			this.kdtDetailEntry.getRow(e.getRowIndex()).getCell("amount").setValue(FDCHelper.divide(this.kdtDetailEntry.getRow(e.getRowIndex()).getCell("totalAmount").getValue(), rate, 2, BigDecimal.ROUND_HALF_UP));
		}
	}
	protected void prmtpartB_dataChanged(DataChangeEvent e) throws Exception {
		SupplierInfo supplier=(SupplierInfo) this.prmtpartB.getValue();
		if(supplier!=null){
			EntityViewInfo view=new EntityViewInfo();
			FilterInfo filter=new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("supplier.id",supplier.getId().toString()));
			view.setFilter(filter);
			SelectorItemCollection sic = super.getSelectors();
			sic.add(new SelectorItemInfo("supplierBank.*"));
			view.setSelector(sic);
			SupplierCompanyInfoCollection col=SupplierCompanyInfoFactory.getRemoteInstance().getSupplierCompanyInfoCollection(view);
			if(col.size()>0&&col.get(0).getSupplierBank().size()>0){
//				this.txtBank.setText(col.get(0).getSupplierBank().get(0).getBank());
				this.txtBankAccount.setText(col.get(0).getSupplierBank().get(0).getBankAccount());
			}
			if(supplier.getTaxRegisterNo()!=null)
				this.txtTaxerNum.setText(supplier.getTaxRegisterNo());
		}else{
//			this.txtBank.setText(null);
//			this.txtBankAccount.setText(null);
//			this.txtTaxerNum.setText(null);
		}
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
    	ContractBillCollection col=ContractBillFactory.getRemoteInstance().getContractBillCollection("select * from where id='"+id+"'");
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

	@Override
	public void actionAuditResult_actionPerformed(ActionEvent e)
			throws Exception {
		String id = this.editData.getId().toString();
		ContractBillCollection col=ContractBillFactory.getRemoteInstance().getContractBillCollection("select * from where id='"+id+"'");
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
