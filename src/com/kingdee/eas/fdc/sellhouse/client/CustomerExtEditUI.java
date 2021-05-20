/**
 * output package name
 */
package com.kingdee.eas.fdc.sellhouse.client;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dialog;
import java.awt.FocusTraversalPolicy;
import java.awt.Window;
import java.awt.event.*;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.swing.Action;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.tree.TreeNode;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.metadata.data.SortType;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.metadata.entity.SorterItemCollection;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.UIFocusTraversalPolicy;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.UIException;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.bos.ui.face.UIRuleUtil;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox;
import com.kingdee.bos.ctrl.kdf.table.IColumn;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTDefaultCellEditor;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectBlock;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectManager;
import com.kingdee.bos.ctrl.kdf.table.KDTStyleConstants;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.event.KDTActiveCellEvent;
import com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent;
import com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent;
import com.kingdee.bos.ctrl.kdf.table.event.KDTSelectEvent;
import com.kingdee.bos.ctrl.kdf.table.util.KDTableUtil;
import com.kingdee.bos.ctrl.kdf.util.editor.ICellEditor;
import com.kingdee.bos.ctrl.kdf.util.style.Styles.HorizontalAlignment;
import com.kingdee.bos.ctrl.swing.IKDTextComponent;
import com.kingdee.bos.ctrl.swing.KDCheckBox;
import com.kingdee.bos.ctrl.swing.KDComboBox;
import com.kingdee.bos.ctrl.swing.KDDatePicker;
import com.kingdee.bos.ctrl.swing.KDTabbedPane;
import com.kingdee.bos.ctrl.swing.KDTextArea;
import com.kingdee.bos.ctrl.swing.KDTextField;
import com.kingdee.bos.ctrl.swing.KDWorkButton;
import com.kingdee.bos.ctrl.swing.StringUtils;
import com.kingdee.bos.ctrl.swing.event.DataChangeEvent;
import com.kingdee.bos.ctrl.swing.event.DataChangeListener;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.dao.DataAccessException;
import com.kingdee.bos.dao.IObjectCollection;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.framework.cache.CacheServiceFactory;
import com.kingdee.eas.base.permission.UserInfo;
import com.kingdee.eas.basedata.master.cssp.CustomerInfo;
import com.kingdee.eas.basedata.org.FullOrgUnitInfo;
import com.kingdee.eas.basedata.org.OrgUnitInfo;
import com.kingdee.eas.basedata.org.SaleOrgUnitInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basecrm.RevBillTypeEnum;
import com.kingdee.eas.fdc.basecrm.RevBizTypeEnum;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCSQLFacadeFactory;
import com.kingdee.eas.fdc.basedata.MoneySysTypeEnum;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.customerservice.CmsTimeConferBillCollection;
import com.kingdee.eas.fdc.customerservice.CmsTimeConferBillFactory;
import com.kingdee.eas.fdc.customerservice.CmsTimeConferBillInfo;
import com.kingdee.eas.fdc.customerservice.FDCStdQueationProcessType;
import com.kingdee.eas.fdc.customerservice.ServiceProcessBillCollection;
import com.kingdee.eas.fdc.customerservice.ServiceProcessBillFactory;
import com.kingdee.eas.fdc.customerservice.ServiceProcessBillInfo;
import com.kingdee.eas.fdc.insider.InsiderCollection;
import com.kingdee.eas.fdc.insider.InsiderFactory;
import com.kingdee.eas.fdc.insider.InsiderInfo;
import com.kingdee.eas.fdc.insider.client.InsiderApplicationNewEditUI;
import com.kingdee.eas.fdc.market.MarketDisplaySetting;
import com.kingdee.eas.fdc.propertymgmt.CustomerMoveVoucherCollection;
import com.kingdee.eas.fdc.propertymgmt.CustomerMoveVoucherFactory;
import com.kingdee.eas.fdc.propertymgmt.IJoinVoucher;
import com.kingdee.eas.fdc.propertymgmt.JoinStatus;
import com.kingdee.eas.fdc.propertymgmt.JoinType;
import com.kingdee.eas.fdc.propertymgmt.JoinVoucherCollection;
import com.kingdee.eas.fdc.propertymgmt.JoinVoucherFactory;
import com.kingdee.eas.fdc.propertymgmt.JoinVoucherInfo;
import com.kingdee.eas.fdc.propertymgmt.PPMARBaseInfo;
import com.kingdee.eas.fdc.propertymgmt.PPMGeneralARCollection;
import com.kingdee.eas.fdc.propertymgmt.PPMGeneralARFactory;
import com.kingdee.eas.fdc.propertymgmt.PPMGeneralARInfo;
import com.kingdee.eas.fdc.propertymgmt.PPMMeasureARCollection;
import com.kingdee.eas.fdc.propertymgmt.PPMMeasureARFactory;
import com.kingdee.eas.fdc.propertymgmt.PPMMeasureARInfo;
import com.kingdee.eas.fdc.propertymgmt.PPMTemporaryCollection;
import com.kingdee.eas.fdc.propertymgmt.PPMTemporaryFactory;
import com.kingdee.eas.fdc.propertymgmt.PPMTemporaryInfo;
import com.kingdee.eas.fdc.sellhouse.AdapterLogCollection;
import com.kingdee.eas.fdc.sellhouse.AdapterLogInfo;
import com.kingdee.eas.fdc.sellhouse.BuildingInfo;
import com.kingdee.eas.fdc.sellhouse.CertifacateNameEnum;
import com.kingdee.eas.fdc.sellhouse.CertificateInfo;
import com.kingdee.eas.fdc.sellhouse.CommerceChanceCollection;
import com.kingdee.eas.fdc.sellhouse.CommerceChanceFactory;
import com.kingdee.eas.fdc.sellhouse.CommerceChanceInfo;
import com.kingdee.eas.fdc.sellhouse.CustomerBusinessScopeEntryInfo;
import com.kingdee.eas.fdc.sellhouse.CustomerEntryCollection;
import com.kingdee.eas.fdc.sellhouse.CustomerEntryFactory;
import com.kingdee.eas.fdc.sellhouse.CustomerEntryInfo;
import com.kingdee.eas.fdc.sellhouse.CustomerTypeEnum;
import com.kingdee.eas.fdc.sellhouse.CustomerWorkAreaEntryInfo;
import com.kingdee.eas.fdc.sellhouse.EnterprisePropertyEnum;
import com.kingdee.eas.fdc.sellhouse.FDCCustomerCollection;
import com.kingdee.eas.fdc.sellhouse.FDCCustomerFactory;
import com.kingdee.eas.fdc.sellhouse.FDCCustomerInfo;
import com.kingdee.eas.fdc.sellhouse.FDCReceiveBillCollection;
import com.kingdee.eas.fdc.sellhouse.FDCReceiveBillEntryCollection;
import com.kingdee.eas.fdc.sellhouse.FDCReceiveBillEntryFactory;
import com.kingdee.eas.fdc.sellhouse.FDCReceiveBillEntryInfo;
import com.kingdee.eas.fdc.sellhouse.FDCReceiveBillFactory;
import com.kingdee.eas.fdc.sellhouse.IFDCCustomer;
import com.kingdee.eas.fdc.sellhouse.LinkmanEntryCollection;
import com.kingdee.eas.fdc.sellhouse.LinkmanEntryFactory;
import com.kingdee.eas.fdc.sellhouse.LinkmanEntryInfo;
import com.kingdee.eas.fdc.sellhouse.MoneyDefineCollection;
import com.kingdee.eas.fdc.sellhouse.MoneyDefineFactory;
import com.kingdee.eas.fdc.sellhouse.MoneyTypeEnum;
import com.kingdee.eas.fdc.sellhouse.PurchaseCustomerInfoCollection;
import com.kingdee.eas.fdc.sellhouse.PurchaseCustomerInfoFactory;
import com.kingdee.eas.fdc.sellhouse.PurchaseCustomerInfoInfo;
import com.kingdee.eas.fdc.sellhouse.PurchaseInfo;
import com.kingdee.eas.fdc.sellhouse.RoomFactory;
import com.kingdee.eas.fdc.sellhouse.RoomInfo;
import com.kingdee.eas.fdc.sellhouse.SellProjectInfo;
import com.kingdee.eas.fdc.sellhouse.ShareModelEnum;
import com.kingdee.eas.fdc.sellhouse.ShareSellerCollection;
import com.kingdee.eas.fdc.sellhouse.ShareSellerFactory;
import com.kingdee.eas.fdc.sellhouse.ShareSellerInfo;
import com.kingdee.eas.fdc.sellhouse.SubareaInfo;
import com.kingdee.eas.fdc.sellhouse.TrackPhaseEnum;
import com.kingdee.eas.fdc.sellhouse.TrackRecordCollection;
import com.kingdee.eas.fdc.sellhouse.TrackRecordFactory;
import com.kingdee.eas.fdc.sellhouse.TrackRecordInfo;
import com.kingdee.eas.fdc.sellhouse.WorkAreaInfo;
import com.kingdee.eas.fdc.tenancy.BusinessScopeInfo;
import com.kingdee.eas.fdc.tenancy.MarketUnitControlCollection;
import com.kingdee.eas.fdc.tenancy.MarketUnitControlFactory;
import com.kingdee.eas.fdc.tenancy.MarketUnitPlatCollection;
import com.kingdee.eas.fdc.tenancy.MarketUnitPlatFactory;
import com.kingdee.eas.fdc.tenancy.MarketUnitPlatInfo;
import com.kingdee.eas.fdc.tenancy.MarketingUnitCollection;
import com.kingdee.eas.fdc.tenancy.MarketingUnitFactory;
import com.kingdee.eas.fdc.tenancy.MarketingUnitInfo;
import com.kingdee.eas.fdc.tenancy.MarketingUnitMemberCollection;
import com.kingdee.eas.fdc.tenancy.MarketingUnitMemberFactory;
import com.kingdee.eas.fdc.tenancy.MarketingUnitMemberInfo;
import com.kingdee.eas.fdc.tenancy.TenancyBillInfo;
import com.kingdee.eas.fdc.tenancy.TenancyCustomerEntryCollection;
import com.kingdee.eas.fdc.tenancy.TenancyCustomerEntryFactory;
import com.kingdee.eas.fdc.tenancy.TenancyCustomerEntryInfo;
import com.kingdee.eas.fdc.tenancy.client.TenancyBillEditUI;
import com.kingdee.eas.fdc.tenancy.client.TenancyClientHelper;
import com.kingdee.eas.fi.cas.ReceivingBillCollection;
import com.kingdee.eas.fi.cas.ReceivingBillFactory;
import com.kingdee.eas.framework.*;
import com.kingdee.eas.framework.client.FrameWorkClientUtils;
import com.kingdee.eas.scm.common.client.GeneralKDPromptSelectorAdaptor;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.MsgBox;
import com.kingdee.util.UuidException;
import com.kingdee.util.enums.EnumUtils;

/**
 * output class name
 */
public class CustomerExtEditUI extends AbstractCustomerExtEditUI
{
    private static final Logger logger = CoreUIObject.getLogger(CustomerExtEditUI.class);
	UserInfo userInfo = SysContext.getSysContext().getCurrentUserInfo();
	SaleOrgUnitInfo saleOrg = SysContext.getSysContext().getCurrentSaleUnit();
	MarketDisplaySetting setting = new MarketDisplaySetting();
	private KDWorkButton btnAddLinkman = null;
	private KDWorkButton btnRemoveLinkman = null;
	private final static BigDecimal ZERO = new BigDecimal("0.00"); 
    /**
     * output class constructor
     */
    public CustomerExtEditUI() throws Exception
    {
        super();
    }

	
	//����ϵ�˷�¼���л�ȡ
	protected IObjectValue getValue(IObjectPK pk) throws Exception {
		CacheServiceFactory.getInstance().discardType(new LinkmanEntryInfo().getBOSType());
		CacheServiceFactory.getInstance().discardType(new TrackRecordInfo().getBOSType());
		CacheServiceFactory.getInstance().discardType(new FDCCustomerInfo().getBOSType());
		CacheServiceFactory.getInstance().discardType(new ShareSellerInfo().getBOSType());
		
		FDCCustomerInfo cus = (FDCCustomerInfo) super.getValue(pk);
		
		FilterInfo acctFilter = new FilterInfo();
		acctFilter.getFilterItems().add(new FilterItemInfo("head", pk.toString()));
		
		SelectorItemCollection sel = new SelectorItemCollection();
		sel.add(new SelectorItemInfo("*"));
		sel.add(new SelectorItemInfo("fdcCustomer.*"));
		EntityViewInfo view = new EntityViewInfo();
		view.getSelector().addObjectCollection(sel);
		view.setFilter(acctFilter);
		
		SelectorItemCollection sel2 = new SelectorItemCollection();
		sel2.add(new SelectorItemInfo("*"));
		sel2.add(new SelectorItemInfo("seller.*"));
		sel2.add(new SelectorItemInfo("marketingUnit.*"));
		sel2.add(new SelectorItemInfo("orgUnit.*"));
		EntityViewInfo view2 = new EntityViewInfo();
		view2.getSelector().addObjectCollection(sel2);
		view2.setFilter(acctFilter);
		
		LinkmanEntryCollection linkmanList = LinkmanEntryFactory.getRemoteInstance().getLinkmanEntryCollection(view2);
		cus.getLinkmanList().addCollection(linkmanList);
		
		ShareSellerCollection shareSellerColl = ShareSellerFactory.getRemoteInstance().getShareSellerCollection(view2);
		cus.getShareSellerList().addCollection(shareSellerColl);
		return cus;
	}
	
	public void onLoad() throws Exception {
		this.actionPre.setVisible(false);
		this.actionNext.setVisible(false);
		this.actionFirst.setVisible(false);
		this.actionLast.setVisible(false);
		this.actionAddCommerceChance.setEnabled(true);
		actionRemoveLinkman.setEnabled(true);
		
			
		SpinnerNumberModel model = new SpinnerNumberModel(0, 0, 999, 1);
		this.spinTradeTime.setModel(model);
		
		SpinnerNumberModel model1 = new SpinnerNumberModel(0, 0, 999, 1);
		this.spinTenTradeTime.setModel(model1);
		
		
		//specilAgio
	
//		this.numtxtTradeTime.getInputMap().put(KeyStroke.getKeyStroke('.', true), KDNumberTextField.undoAction);
		
		this.f7salesman.setRequired(true);
		
		//this.f7salesman.setEnabled(false);
		EntityViewInfo saleManView = CommerceHelper.getPermitSalemanView(this.editData.getProject());
		this.f7salesman.setEntityViewInfo(saleManView);	
		this.f7CustomerManager.setEntityViewInfo(saleManView);
		this.btnAddLine.setToolTipText("������");
		
		initLinkmanTable();		
		initShareSeller();
		initAdapterLog();
		initSaleRecordTable();
		
		//this.f7Country.setEnabledMultiSelection(true);
		this.f7WorkArea.setEnabledMultiSelection(true);
		this.f7BusinessScope.setEnabledMultiSelection(true);
		
		super.onLoad();
		///tblLinkman.getColumn("phone").setRequired(true);
		initNatureEnterpriseF7(this,enterpriceProperty,null);
		initBusinessScopeF7(this, f7BusinessScope, null);
		initCooperateModeF7(this, f7CooperateMode, null);
		lcCountry.setBoundLabelText("����/����");
		//this.f7HabitationArea.setEnabledMultiSelection(true);
		if(STATUS_ADDNEW.equals(this.getOprtState()))
		this.f7CustomerManager.setValue(this.f7salesman.getValue());
		if(!STATUS_ADDNEW.equals(this.getOprtState())){
			initPropertyHouse();
			initCustomerServiceSpecial();
			//this.actionRev.setEnabled(true);
			//this.actionRefund.setEnabled(true);
		}
		
		//this.boxSex.addItem("");
		
		this.menuBar.remove(menuBiz);
		this.toolBar.remove(btnCancelCancel);
		this.toolBar.remove(btnCancel);
		
		this.actionRemove.setVisible(false);
		this.actionSave.setVisible(false);
		this.actionPrint.setVisible(false);
		this.actionPrintPreview.setVisible(false);
		this.actionCancel.setVisible(false);
		this.actionCancelCancel.setVisible(false);
		this.actionCopy.setVisible(false);
		this.actionCustomerAdapter.setEnabled(false);
		this.actionCustomerShare.setEnabled(false);
		this.actionCustomerCancelShare.setEnabled(false);
		if(STATUS_EDIT.equals(this.getOprtState()))
		{
			if(this.tblShareSeller.getRowCount()>0)
			{
				for(int i=0;i<tblShareSeller.getRowCount();i++)
				{
					IRow row = tblShareSeller.getRow(i);
					ShareModelEnum shareModel = (ShareModelEnum)row.getCell("shareModel").getValue();
					if(ShareModelEnum.shareMarket.equals(shareModel) || ShareModelEnum.shareOrgUnit.equals(shareModel))
					{
						row.getCell("isAgainShare").getStyleAttributes().setLocked(true);
						row.getCell("isUpdate").getStyleAttributes().setLocked(false);
					}else
					{
						row.getCell("isAgainShare").getStyleAttributes().setLocked(false);
						row.getCell("isUpdate").getStyleAttributes().setLocked(false);
					}
				}
			}		
			String fdcCustomerID = this.editData.getId().toString();
			if(!hasUpdatePermission(fdcCustomerID))
			{
				MsgBox.showInfo("�Ըÿͻ�û���޸�Ȩ!");
				this.abort();
			}
			if(CustomerShareUI.hasSharePermission(fdcCustomerID))
			{
				this.actionCustomerShare.setEnabled(true);
				this.actionCustomerCancelShare.setEnabled(true);
			}
			
			if(CustomerAdapterUI.hasAdpaterPermission(fdcCustomerID)) {
				this.actionCustomerAdapter.setEnabled(true);
			}
		}
		
		this.txtNumber.setMaxLength(70);
		this.f7ReceptionType.setRequired(true);
		
		SaleOrgUnitInfo saleOrg = SHEHelper.getCurrentSaleOrg();
		if (!saleOrg.isIsBizUnit()) {
			this.actionAddNew.setEnabled(false);
			this.actionEdit.setEnabled(false);
		}
		
		setVisableAboutCustomerType();
		setTabVisable();
		SimpleKDTSortManager.setTableSortable(tblShareSeller);
		//�������ҳǩ��������,���޸�ʱ����ȡ����¥����
		if(this.tblSaleRecord.getRowCount() != 0  ||  this.tblSaleCommerce.getRowCount() != 0){
			this.chkIsForSHE.setEnabled(false);
		}
		if(this.tblTenancyRecord.getRowCount() != 0  ||  this.tblTenancyCommerce.getRowCount() != 0){
			this.chkIsForTen.setEnabled(false);
		}	
		SellProjectInfo sp = (SellProjectInfo) this.f7Project.getValue();
		
		setGroupFilter(sp);
		
		addGroupDataListener(f7FamillyEarning);
		addGroupDataListener(f7ReceptionType);
		addGroupDataListener(f7HabitationArea);
		addGroupDataListener(f7CustomerGrade);
		addGroupDataListener(f7CustomerOrigin);
		
/*		��һ����˭�ӵģ�  �ᵼ�¿ͻ���ʱ���򿪵Ľ����޷����̻�����
  		MoneySysTypeEnum latSysType = (MoneySysTypeEnum)getUIContext().get("latSysType");
		if(latSysType == null){
			actionAddCommerceChance.setEnabled(false);
		}*/
		
		if(getOprtState().equals(this.STATUS_VIEW)){
			actionInsider.setEnabled(true);
		}else{
			actionInsider.setEnabled(false);
		}
		FilterInfo filter =  new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("fdcCustomer", this.editData.getId()));
		if(InsiderFactory.getRemoteInstance().exists(filter)){
			actionInsider.setEnabled(false);
		}
		
		
		
		 if(this.getUIContext().get("CustomerView")!=null){
			 this.actionAddNew.setEnabled(false);
			 this.actionEdit.setEnabled(false);
			 this.actionSave.setEnabled(false);
			 this.getUIContext().put("CustomerView",null);
		 }
		 
		 initStatus();
		//new add by renliang at 2011-3-2
		initNewCertificateName(true);
	}

	private void initStatus() {
    	this.f7HabitationArea.setRequired(true);
		this.f7CustomerOrigin.setRequired(true);
		this.f7CustomerGrade.setRequired(true);
		this.txtEmployment.setRequired(true);
		this.txtareaDescription.setRequired(true);
		
		this.f7RoomModelType.setRequired(true);
		this.txtIntentionArea.setRequired(true);
		this.txtAge.setRequired(true);
		///////////////
		this.chkIsForTen.setVisible(false);
		this.chkIsForPPM.setVisible(false);
		this.lcFax.setVisible(false);
		this.lcCountry.setVisible(false);
		this.lcOfficeTel.setVisible(false);
		this.lcBookedPlace.setVisible(false);
		this.lcPhone2.setVisible(false);
		this.lcProvince.setVisible(false);
		this.lcQQ.setVisible(false);
		this.lcEmail.setVisible(false);
		
		SHEHelper.setTextFormat(this.txtIntentionArea);
		
//		SHEHelper.setTextFormat(this.txtAge);
		txtAge.setRemoveingZeroInDispaly(false);
		txtAge.setRemoveingZeroInEdit(true);
//		txtAge.setPrecision(0);
		txtAge.setHorizontalAlignment(JTextField.RIGHT);
		txtAge.setSupportedEmpty(true);
	}
	private void addGroupDataListener(final KDBizPromptBox f7){
		f7.addDataChangeListener(new DataChangeListener(){
			public void dataChanged(DataChangeEvent eventObj) {
				if(f7.getValue() == null){
					return;
				}
				if(CustomerExtEditUI.this.f7Project.getValue() == null){
					MsgBox.showInfo("����ѡ���ʼ�Ǽ���Ŀ!");
					f7.setValue(null);
				}
			}
		});
	}
	
	protected void f7Project_dataChanged(DataChangeEvent e) throws Exception {
		super.f7Project_dataChanged(e);		
		this.initF7Filter();
		SellProjectInfo sellProject = (SellProjectInfo) this.f7Project.getValue();
		this.setGroupFilter(sellProject);
	}
	private void setGroupFilter(SellProjectInfo sp) {
		if (sp == null){
			return;
		}
		String spId = sp.getId().toString();
		//���ü�ͥ����ļ��Źܿع���
		SHEHelper.SetGroupFilters(this.f7FamillyEarning, spId, "��ͥ����", "CustomerArch");		
		//���ýӴ���ʽ�ļ��Źܿع���
		SHEHelper.SetGroupFilters(this.f7ReceptionType, spId, "�Ӵ���ʽ", "CustomerArch");		
		//���þ�ס����ļ��Źܿع���
		SHEHelper.SetGroupFilters(this.f7HabitationArea, spId, "��ס����", "CustomerArch");		
		//���ÿͻ��ּ��ļ��Źܿع���
		SHEHelper.SetGroupFilters(this.f7CustomerGrade, spId, "�ͻ��ּ�", "CustomerArch");		
		//���ÿͻ���Դ�ļ��Źܿع���
		SHEHelper.SetGroupFilters(this.f7CustomerOrigin, spId, "�ͻ���Դ", "CustomerArch");						
	}
	private void initF7Filter() {
		if(this.f7FamillyEarning.getEntityViewInfo()!=null){
			this.f7FamillyEarning.setEntityViewInfo(null);
		}
		if(this.f7ReceptionType.getEntityViewInfo()!=null){			
			this.f7ReceptionType.setEntityViewInfo(null);
		}			
		if(this.f7HabitationArea.getEntityViewInfo()!=null){			
			this.f7HabitationArea.setEntityViewInfo(null);
		}
		if(this.f7CustomerGrade.getEntityViewInfo()!=null){			
			this.f7CustomerGrade.setEntityViewInfo(null);
		}
		if(this.f7CustomerOrigin.getEntityViewInfo()!=null){			
			this.f7CustomerOrigin.setEntityViewInfo(null);
		}
	}
	


	private void initSaleRecordTable() {
		this.tblSaleRecord.checkParsed();
		this.tblSaleRecord.getColumn("prePurchaseAmount").getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(2));
		this.tblSaleRecord.getColumn("dealAmount").getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(2));
		this.tblSaleRecord.getColumn("dealPrice").getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(2));
		if(this.tblSaleRecord.getColumn("specilAgio")!=null){
			this.tblSaleRecord.getColumn("specilAgio").getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(2));
		}
	}


	protected void boxCustomerType_itemStateChanged(ItemEvent e) throws Exception {
		super.boxCustomerType_itemStateChanged(e);
		setVisableAboutCustomerType();
	}
	
	private void setVisableAboutCustomerType() {
		CustomerTypeEnum customerType = (CustomerTypeEnum) boxCustomerType.getSelectedItem();
		boolean isPersonal = false;
		boolean isEnterprice = false;
		String desLabelText = "";
		if(CustomerTypeEnum.PersonalCustomer.equals(customerType)){
			isPersonal = true;
			desLabelText = "���˼��";
			lcHabitationArea.setBoundLabelText("��ס����");
			kDLabelContainer2.setBoundLabelText("��������");
			lcBusinessScope.setVisible(false);
			lcCooperateMode.setVisible(false);
			lcCustomerManager.setVisible(false);
			//new add by renliang at 2011-3-2
			initNewCertificateName(true);
		}else if(CustomerTypeEnum.EnterpriceCustomer.equals(customerType)){
			isEnterprice = true;
			desLabelText = "��ҵ���";
			lcHabitationArea.setBoundLabelText("��������");
			kDLabelContainer2.setBoundLabelText("��������");
			lcBusinessScope.setVisible(true);
			lcCooperateMode.setVisible(true);
			lcCustomerManager.setVisible(true);
			//new add by renliang at 2011-3-2
			initNewCertificateName(false);
		}else{
			//new add by renliang at 2011-3-2
			initNewCertificateName(true);
			logger.error("error.! customerType: " + customerType);
		}
		this.contEnterpriceProperty.setVisible(isEnterprice);
		this.contEnterpriceSize.setVisible(isEnterprice);
		this.contIndustry.setVisible(isEnterprice);
		this.contEnterpriceHomepage.setVisible(isEnterprice);
		
		this.lcSex.setVisible(isPersonal);
		this.lcFamillyEarning.setVisible(isPersonal);
		this.lcEmployment.setVisible(isPersonal);
		this.lcWorkCompany.setVisible(isPersonal);

		this.lcDescription.setBoundLabelText(desLabelText);
	}
	
	private void setTabVisable(){
		boolean isForSHE = this.chkIsForSHE.isSelected();
		boolean isForTen = this.chkIsForTen.isSelected();
		boolean isForPPM = this.chkIsForPPM.isSelected();
		
		this.tabNew.remove(panelSale);
		this.tabNew.remove(panelTenancy);
		this.tabNew.remove(panelManager);
		
		if(isForSHE){
			this.tabNew.add(panelSale, "��¥");
		}
		if(isForTen){
			this.tabNew.add(panelTenancy, "����");
		}
		if(isForPPM){
			this.tabNew.add(panelManager, "��ҵ");
		}
	}
	
	protected void chkIsForSHE_actionPerformed(ActionEvent e) throws Exception {
		super.chkIsForSHE_actionPerformed(e);
		setTabVisable();
	}
	
	protected void chkIsForTen_actionPerformed(ActionEvent e) throws Exception {
		super.chkIsForTen_actionPerformed(e);
		setTabVisable();
	}
	
	protected void chkIsForPPM_actionPerformed(ActionEvent e) throws Exception {
		super.chkIsForPPM_actionPerformed(e);
		setTabVisable();
	}
	
	
	/**
	 * ��ʼ��������¼�б�
	 * */
	public void initTrackRecordTable() {		
		tblTrackRecord.checkParsed();
		tblTrackRecord.getStyleAttributes().setLocked(true);
		tblTrackRecord.getSelectManager().setSelectMode(KDTSelectManager.ROW_SELECT);	
		tblTrackRecord.getColumn("name").getStyleAttributes().setHided(true);
		tblTrackRecord.getColumn("head.name").getStyleAttributes().setHided(true);
		tblTrackRecord.getColumn("createTime").getStyleAttributes().setNumberFormat("YYYY-MM-DD");
		
		if(this.editData!=null) {
			EntityViewInfo view = new EntityViewInfo();
			FilterInfo filter = new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("head.id",this.editData.getId().toString()));
			//filter.getFilterItems().add(new FilterItemInfo("sellProject.id",this.editData.getProject().getId().toString()));
			view.setFilter(filter);
			SelectorItemCollection selector = new SelectorItemCollection();
			selector.add(new SelectorItemInfo("*"));
			selector.add(new SelectorItemInfo("head.name"));
			selector.add(new SelectorItemInfo("eventType.name"));
			selector.add(new SelectorItemInfo("receptionType.name"));
			selector.add(new SelectorItemInfo("commerceChance.name"));
			
			view.setSelector(selector);
			TrackRecordCollection trackColl = null;			
			try {
				trackColl = TrackRecordFactory.getRemoteInstance().getTrackRecordCollection(view);
			} catch (BOSException e) {
				logger.error(e.getMessage());
				this.handleException(e);
			}				
			
			this.tblTrackRecord.removeRows();
			if(trackColl != null){
				for(int i=0;i<trackColl.size();i++) {
					TrackRecordInfo trackInfo = trackColl.get(i);

					//�����һ�������󣬸��ĵ�ǰ���״νӴ���ʽ.����ֻ�����ݿ�����ģ�����ǰ���汣����ֳɾɵ���
					if(trackColl.size()==1 && this.getOprtState().equals(OprtState.EDIT)) {
						this.f7ReceptionType.setValue(trackInfo.getReceptionType());
					}
					
					IRow row = this.tblTrackRecord.addRow();
					row.setUserObject(trackInfo);
					row.getCell("id").setValue(trackInfo.getId().toString());
					row.getCell("number").setValue(trackInfo.getNumber());
					row.getCell("name").setValue(trackInfo.getName());
					row.getCell("eventDate").setValue(trackInfo.getEventDate());
					row.getCell("head.name").setValue(trackInfo.getHead());
					row.getCell("trackType").setValue(trackInfo.getTrackType());
					row.getCell("trackResult").setValue(trackInfo.getTrackResult()==null?"":trackInfo.getTrackResult().getAlias());
					row.getCell("eventType.name").setValue(trackInfo.getEventType()==null?"":trackInfo.getEventType().getName());
					row.getCell("receptionType.name").setValue(trackInfo.getReceptionType()==null?"":trackInfo.getReceptionType().getName());
					row.getCell("commerceChance.name").setValue(trackInfo.getCommerceChance()==null?"":trackInfo.getCommerceChance().getName());
					row.getCell("description").setValue(trackInfo.getDescription());
					row.getCell("createTime").setValue(trackInfo.getCreateTime());
				}	
				
			}
		}
		
	}
	
	
	/**
	 *��¥�Ϲ���¼    �ͻ���¼PurchaseCustomerInfo
	 */	
	private void initSalePurchaseRecord() {
		tblSaleRecord.checkParsed();
		//�����Ժ���Ҫ��ձ�񣬲�Ȼ�������ݳ��ָ������ eric_wang 2010.07.27
		tblSaleRecord.removeRows();
		tblSaleRecord.getStyleAttributes().setLocked(false);
		tblSaleRecord.getSelectManager().setSelectMode(KDTSelectManager.ROW_SELECT);
		tblSaleRecord.getColumn("id").getStyleAttributes().setHided(true);
		
		if(this.editData!=null) {
			EntityViewInfo view = new EntityViewInfo();
			FilterInfo filter = new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("customer.id",this.editData.getId().toString()));
			//filter.getFilterItems().add(new FilterItemInfo("customer.project.id",this.editData.getProject().getId().toString()));
			view.setFilter(filter);
			SelectorItemCollection selector = new SelectorItemCollection();
			selector.add(new SelectorItemInfo("head.*"));
			selector.add(new SelectorItemInfo("head.sincerityPurchase.number"));
			selector.add(new SelectorItemInfo("customer.id"));
			selector.add(new SelectorItemInfo("head.room.number"));
			//selector.add(new SelectorItemInfo("customer.project.id"));
			view.setSelector(selector);
			PurchaseCustomerInfoCollection trackColl = null;			
			try {
				trackColl = PurchaseCustomerInfoFactory.getRemoteInstance().getPurchaseCustomerInfoCollection(view);
			} catch (BOSException e) {
				logger.error(e.getMessage());
				e.printStackTrace();
				this.handleException(e);
			}				
			if(trackColl==null) return;
			for(int i=0;i<trackColl.size();i++) {
				PurchaseCustomerInfoInfo custInfo = trackColl.get(i);
				PurchaseInfo trackInfo = custInfo.getHead();
				if(trackInfo!=null) {
					IRow row = this.tblSaleRecord.addRow();
					row.setUserObject(trackInfo);
					row.getCell("id").setValue(trackInfo.getId().toString());
					row.getCell("number").setValue(trackInfo.getNumber());
					row.getCell("purchaseState").setValue(trackInfo.getPurchaseState());
					row.getCell("room.number").setValue(trackInfo.getRoom()==null?"":trackInfo.getRoom().getNumber());
					row.getCell("customer").setValue(trackInfo.getCustomerNames());
					row.getCell("sincerityPurchase.number").setValue(trackInfo.getSincerityPurchase() == null ? "" : trackInfo.getSincerityPurchase().getNumber());
					row.getCell("prePurchaseAmount").setValue(trackInfo.getPrePurchaseAmount());
					row.getCell("prePurchaseCurrency.name").setValue(trackInfo.getPrePurchaseCurrency());
					row.getCell("prePurchaseDate").setValue(trackInfo.getPrePurchaseDate());
					row.getCell("dealAmount").setValue(trackInfo.getAmount());
					row.getCell("dealPrice").setValue(trackInfo.getDealPrice());
					row.getCell("payType.name").setValue(trackInfo.getPayType());
					row.getCell("salesman.name").setValue(trackInfo.getSalesman());
					row.getCell("prePurchaseAuditor.name").setValue(trackInfo.getPrePurchaseAuditor());
					row.getCell("prePurchaseAuditDate").setValue(trackInfo.getPrePurchaseAuditDate());
					row.getCell("sellType").setValue(trackInfo.getSellType());
					row.getCell("specilAgio").setValue(trackInfo.getSpecialAgio());
				}
			}	
		}
	}
	
	/**
	 * �ͻ���Ա��¼ insider
	 */
	public void initInside(){
		this.tblInsider.checkParsed();
		this.tblInsider.removeRows(false);
		tblInsider.getStyleAttributes().setLocked(false);
		tblInsider.getColumn("id").getStyleAttributes().setHided(true);
		if(this.editData!=null && this.editData.getId()!=null){
			EntityViewInfo view = new EntityViewInfo();
			FilterInfo filter = new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("fdcCustomer.id",this.editData.getId().toString()));
			view.setFilter(filter);
			SelectorItemCollection selector = new SelectorItemCollection();
			selector.add(new SelectorItemInfo("*"));
			selector.add(new SelectorItemInfo("vipLevel.id"));
			selector.add(new SelectorItemInfo("vipLevel.name"));
			selector.add(new SelectorItemInfo("vipLevel.number"));
			view.setSelector(selector);
			InsiderCollection insiderColl = null;
			try {
				insiderColl = InsiderFactory.getRemoteInstance().getInsiderCollection(view);
			} catch (BOSException e) {
				e.printStackTrace();
			}
			if(insiderColl!=null && insiderColl.size()>0){
				for(int i=0;i<insiderColl.size();i++){
					InsiderInfo insiderInfo = insiderColl.get(i);
					IRow row = this.tblInsider.addRow();
					row.setUserObject(insiderInfo);
					row.getCell("id").setValue(insiderInfo.getId());
					row.getCell("insider").setValue(insiderInfo.getCustomerName());
					row.getCell("insiderCode").setValue(insiderInfo.getInsiderCode());
					row.getCell("vipLevel").setValue(insiderInfo.getVipLevel());
					row.getCell("requestDate").setValue(insiderInfo.getRequestDate());
					row.getCell("bizDate").setValue(insiderInfo.getBizDate());
					row.getCell("insiderRemove").setValue(insiderInfo.getInsiderRemove());
					row.getCell("insiderHortation").setValue(insiderInfo.getInsiderHortation());
				}
			}
		}
	}
	
	/**
	 * ���������¼   �ͻ���¼ TenancyCustomerEntry
	 */
	private void initTenancyBillRecord() {
		this.tblTenancyRecord.checkParsed();
		//�����Ժ���Ҫ��ձ�񣬲�Ȼ�������ݳ��ָ������ eric_wang 2010.07.29
		tblTenancyRecord.removeRows();
		tblTenancyRecord.getStyleAttributes().setLocked(false);
		tblTenancyRecord.getSelectManager().setSelectMode(KDTSelectManager.ROW_SELECT);
		tblTenancyRecord.getColumn("id").getStyleAttributes().setHided(true);
		
		if(this.editData!=null) {
			EntityViewInfo view = new EntityViewInfo();
			FilterInfo filter = new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("fdcCustomer.id",this.editData.getId().toString()));
			//filter.getFilterItems().add(new FilterItemInfo("fdcCustomer.project.id",this.editData.getProject().getId().toString()));
			view.setFilter(filter);
			SelectorItemCollection selector = new SelectorItemCollection();
			selector.add(new SelectorItemInfo("tenancyBill.*"));
			selector.add(new SelectorItemInfo("fdcCustomer.id"));
			//selector.add(new SelectorItemInfo("fdcCustomer.project.id"));
			view.setSelector(selector);
			TenancyCustomerEntryCollection trackColl = null;			
			try {
				trackColl = TenancyCustomerEntryFactory.getRemoteInstance().getTenancyCustomerEntryCollection(view);
			} catch (BOSException e) {
				logger.error(e.getMessage());
				e.printStackTrace();
				this.handleException(e);
			}				
			
			if(trackColl==null) return;
			for(int i=0;i<trackColl.size();i++) {
				TenancyCustomerEntryInfo custInfo = trackColl.get(i);
				TenancyBillInfo trackInfo = custInfo.getTenancyBill();
				if(trackInfo!=null) {
					IRow row = this.tblTenancyRecord.addRow();
					row.setUserObject(trackInfo);
					row.getCell("id").setValue(trackInfo.getId().toString());
					row.getCell("number").setValue(trackInfo.getNumber());
					row.getCell("tenancyState").setValue(trackInfo.getTenancyState());
					row.getCell("tenancyName").setValue(trackInfo.getTenancyName());
					row.getCell("tenRoomsDes").setValue(trackInfo.getTenRoomsDes());
					row.getCell("tenAttachesDes").setValue(trackInfo.getTenAttachesDes());
					row.getCell("tenCustomerDes").setValue(trackInfo.getTenCustomerDes());
					row.getCell("tenancyType").setValue(trackInfo.getTenancyType());
					row.getCell("oldTenancyBill.tenancyName").setValue(trackInfo.getOldTenancyBill()==null?"":trackInfo.getOldTenancyBill().getTenancyName());
					row.getCell("startDate").setValue(trackInfo.getStartDate());
					row.getCell("leaseCount").setValue(trackInfo.getLeaseCount());
					row.getCell("endDate").setValue(trackInfo.getEndDate());
					row.getCell("leaseTime").setValue(String.valueOf(trackInfo.getLeaseTime()));
					row.getCell("flagAtTerm").setValue(trackInfo.getFlagAtTerm());
					row.getCell("tenancyAdviser.name").setValue(trackInfo.getTenancyAdviser());
					row.getCell("agency.name").setValue(trackInfo.getAgency());
					row.getCell("dealTotalRent").setValue(trackInfo.getDealTotalRent());
					row.getCell("standardTotalRent").setValue(trackInfo.getStandardTotalRent());
					row.getCell("depositAmount").setValue(trackInfo.getDepositAmount());
					row.getCell("firstPayRent").setValue(trackInfo.getFirstPayRent());
					row.getCell("deliveryRoomDate").setValue(trackInfo.getDeliveryRoomDate());
					row.getCell("description").setValue(trackInfo.getDescription());
					row.getCell("specialClause").setValue(trackInfo.getSpecialClause());
					row.getCell("createTime").setValue(trackInfo.getCreateTime());
				}
			}	
		}
	}
	
	/**
	 * �̻� :������¥������  
	 * @param type  :  Sale  , Tenancy
	 */
	public void initCommerceChanceRecord(String type) {
		if(type==null) return;
		//�����Ժ���Ҫ��ձ�񣬲�Ȼ�������ݳ��ָ������ eric_wang 2010.07.27
		
		this.tblSaleCommerce.checkParsed();
		//this.tblSaleCommerce.removeRows();
		this.tblSaleCommerce.getStyleAttributes().setLocked(false);
		this.tblSaleCommerce.getSelectManager().setSelectMode(KDTSelectManager.ROW_SELECT);
		tblSaleCommerce.getColumn("id").getStyleAttributes().setHided(true);
		
		this.tblTenancyCommerce.checkParsed();
		//tblTenancyCommerce.removeRows();
		this.tblTenancyCommerce.getStyleAttributes().setLocked(false);
		this.tblTenancyCommerce.getSelectManager().setSelectMode(KDTSelectManager.ROW_SELECT);
		tblTenancyCommerce.getColumn("id").getStyleAttributes().setHided(true);
		
		if(this.editData!=null) {
			EntityViewInfo view = new EntityViewInfo();
			FilterInfo filter = new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("fdcCustomer.id",this.editData.getId().toString()));
			//filter.getFilterItems().add(new FilterItemInfo("sellProject.id",this.editData.getProject().getId().toString()));
			
			if(type.equals("Sale"))
				filter.getFilterItems().add(new FilterItemInfo("sysType",MoneySysTypeEnum.SalehouseSys.getValue()));
			else if(type.equals("Tenancy"))
				filter.getFilterItems().add(new FilterItemInfo("sysType",MoneySysTypeEnum.TenancySys.getValue()));
			
			view.setFilter(filter);
			SelectorItemCollection selector = new SelectorItemCollection();
			selector.add(new SelectorItemInfo("*"));
			selector.add(new SelectorItemInfo("sellProject.name"));
			selector.add(new SelectorItemInfo("saleMan.name"));
			selector.add(new SelectorItemInfo("commerceLevel.name"));
			selector.add(new SelectorItemInfo("fdcCustomer.name"));
			selector.add(new SelectorItemInfo("creator.name"));
			view.setSelector(selector);
			CommerceChanceCollection trackColl = null;			
			try {
				trackColl = CommerceChanceFactory.getRemoteInstance().getCommerceChanceCollection(view);
			} catch (BOSException e) {
				logger.error(e.getMessage());
				e.printStackTrace();
				this.handleException(e);
			}				
			
			if(trackColl==null) return;
			for(int i=0;i<trackColl.size();i++) {
				CommerceChanceInfo trackInfo = trackColl.get(i);
				IRow row = null;
				if(type.equals("Sale"))
					row = this.tblSaleCommerce.addRow();
				else if(type.equals("Tenancy"))
					row = this.tblTenancyCommerce.addRow();
				row.setUserObject(trackInfo);
				row.getCell("id").setValue(trackInfo.getId().toString());
				row.getCell("number").setValue(trackInfo.getNumber());
				row.getCell("name").setValue(trackInfo.getName());
				row.getCell("sysType").setValue(trackInfo.getSysType());
				row.getCell("sellProject.name").setValue(trackInfo.getSellProject());
				row.getCell("saleMan.name").setValue(trackInfo.getSaleMan().getName());
				row.getCell("commerceLevel.name").setValue(trackInfo.getCommerceLevel());
				row.getCell("commerceStatus").setValue(trackInfo.getCommerceStatus());
				row.getCell("fdcCustomer.name").setValue(trackInfo.getFdcCustomer());
				row.getCell("creator.name").setValue(trackInfo.getCreator().getName());
				row.getCell("createTime").setValue(trackInfo.getCreateTime());
			}	
		}
		
	}
	
	
	
	/**
	 * Ϊ�����б༭���л�������
	 * */
	private void tblBaseSet(KDTable table) {
		table.checkParsed();
		table.getStyleAttributes().setLocked(false);
		table.getSelectManager().setSelectMode(KDTSelectManager.CELL_SELECT);
		table.setActiveCellStatus(KDTStyleConstants.ACTIVE_CELL_EDIT);
	}
	
	/**
	 * ��ʼ��ת����ʷ��¼�б�
	 * */
	private void initAdapterLog()
	{
		tblBaseSet(this.tblAdapterLog);
		
		IColumn columnAdapterDate = this.tblAdapterLog.getColumn("adapterDate");
		columnAdapterDate.getStyleAttributes().setHorizontalAlign(HorizontalAlignment.LEFT);
		columnAdapterDate.setEditor(createTxtCellEditor(80, false));
		
		KDCheckBox chkBox = new KDCheckBox();
		ICellEditor checkBox = new KDTDefaultCellEditor(chkBox);
		IColumn columnIsAdapterInter = this.tblAdapterLog.getColumn("isAdapterInter");
		columnIsAdapterInter.setEditor(checkBox);
		
		chkBox = new KDCheckBox();
		ICellEditor isAdaptercheckBox = new KDTDefaultCellEditor(chkBox);
		IColumn columnIsAdapterFunction= this.tblAdapterLog.getColumn("isAdapterFunction");
		columnIsAdapterFunction.setEditor(isAdaptercheckBox);
		
		chkBox = new KDCheckBox();
		ICellEditor isEndAdapterCheckBox = new KDTDefaultCellEditor(chkBox);
		IColumn columnIsEndAdapter= this.tblAdapterLog.getColumn("isEndAdapter");
		columnIsEndAdapter.setEditor(isEndAdapterCheckBox);
		
		KDBizPromptBox f7Box = new KDBizPromptBox();
		f7Box.setEditFormat("$number$");
		f7Box.setDisplayFormat("$name$");
		f7Box.setCommitFormat("$number$");
		f7Box.setQueryInfo("com.kingdee.eas.base.permission.app.F7UserQuery");
		//f7Box.setQueryInfo("com.kingdee.eas.fdc.sellhouse.app.CustomerQuery");
		KDTDefaultCellEditor f7Editor = new KDTDefaultCellEditor(f7Box);
		
		IColumn beforeSeller = this.tblAdapterLog.getColumn("beforeSeller");
		beforeSeller.getStyleAttributes().setHorizontalAlign(HorizontalAlignment.LEFT);
		beforeSeller.setEditor(f7Editor);
		
		IColumn afterSeller = this.tblAdapterLog.getColumn("afterSeller");
		afterSeller.getStyleAttributes().setHorizontalAlign(HorizontalAlignment.LEFT);
		afterSeller.setEditor(f7Editor);
		
		IColumn operationPerson = this.tblAdapterLog.getColumn("operationPerson");
		operationPerson.getStyleAttributes().setHorizontalAlign(HorizontalAlignment.LEFT);
		operationPerson.setEditor(f7Editor);
	}
	/**
	 * ��ʼ���������۹����б�
	 * */
	private void initShareSeller() {
		tblBaseSet(this.tblShareSeller);
		
//		IColumn columnShareSellerNumber = this.tblShareSeller.getColumn("shareSellerNumber");
//		columnShareSellerNumber.getStyleAttributes().setHorizontalAlign(HorizontalAlignment.LEFT);
//		columnShareSellerNumber.setEditor(createTxtCellEditor(80, false));
		
		
		IColumn columnDescription = this.tblShareSeller.getColumn("description");
		columnDescription.getStyleAttributes().setHorizontalAlign(HorizontalAlignment.LEFT);
		columnDescription.setEditor(createTxtCellEditor(80, true));
		
		IColumn columnShareDate = this.tblShareSeller.getColumn("shareDate");
		columnShareDate.getStyleAttributes().setHorizontalAlign(HorizontalAlignment.LEFT);
		columnShareDate.setEditor(createTxtCellEditor(80, false));
		
		KDCheckBox chkBox = new KDCheckBox();
		ICellEditor checkBox = new KDTDefaultCellEditor(chkBox);
		IColumn columnIsAgainShare = this.tblShareSeller.getColumn("isAgainShare");
		columnIsAgainShare.setEditor(checkBox);
		
		chkBox = new KDCheckBox();
		ICellEditor checkBoxUpdate = new KDTDefaultCellEditor(chkBox);
		IColumn columnIsUpdate = this.tblShareSeller.getColumn("isUpdate");
		columnIsUpdate.setEditor(checkBoxUpdate);
		
//		KDBizPromptBox f7Box = new KDBizPromptBox();
//		f7Box.setEditFormat("$number$");
//		f7Box.setDisplayFormat("$name$");
//		f7Box.setCommitFormat("$number$");
//		f7Box.setQueryInfo("com.kingdee.eas.base.permission.app.F7UserQuery");
//		//f7Box.setQueryInfo("com.kingdee.eas.fdc.sellhouse.app.CustomerQuery");
//		KDTDefaultCellEditor f7Editor = new KDTDefaultCellEditor(f7Box);
//		
//		IColumn columnName = this.tblShareSeller.getColumn("shareSellerName");
//		columnName.getStyleAttributes().setHorizontalAlign(HorizontalAlignment.LEFT);
//		columnName.setEditor(f7Editor);
	}
	
	private void findShareSeller()
	{
		if(this.editData.getId()!=null)
		{
			EntityViewInfo view = new EntityViewInfo();
			FilterInfo filter = new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("head.id",this.editData.getId().toString()));
			view.setFilter(filter);
			SelectorItemCollection selector = new SelectorItemCollection();
			selector.add(new SelectorItemInfo("*"));
			selector.add(new SelectorItemInfo("head.name"));
			selector.add(new SelectorItemInfo("seller.*"));
			selector.add(new SelectorItemInfo("marketingUnit.*"));
			selector.add(new SelectorItemInfo("orgUnit.*"));
			selector.add(new SelectorItemInfo("operationPerson.*"));
			view.setSelector(selector);
			ShareSellerCollection shareColl = null;
			try {
				shareColl = ShareSellerFactory.getRemoteInstance().getShareSellerCollection(view);
			} catch (BOSException e) {
				logger.error(e.getMessage());
				this.handleException(e);
			}
			this.tblShareSeller.removeRows();
			if(shareColl != null){
				for(int i=0;i<shareColl.size();i++) {
					ShareSellerInfo shareSellerInfo = shareColl.get(i);
					addShareSellerRow(shareSellerInfo);
				}
			}
		}
	}
	
	private void findSaleman()
	{
		SelectorItemCollection sels = new SelectorItemCollection();
		sels.add("*");
		sels.add("salesman.*");

		sels.add("adapterLogList.*");
		sels.add("adapterLogList.beforeSeller.*");
		sels.add("adapterLogList.afterSeller.*");
		sels.add("adapterLogList.operationPerson.*");
		sels.add("shareSellerList.*");
		sels.add("shareSellerList.seller.*");
		sels.add("shareSellerList.marketingUnit.*");
		sels.add("shareSellerList.orgUnit.*");
		sels.add("shareSellerList.operationPerson.*");
		AdapterLogCollection adaColl = null;
		ShareSellerCollection shareSellColl = null;
		try {
			FDCCustomerInfo customerInfo = FDCCustomerFactory.getRemoteInstance().getFDCCustomerInfo(
					new ObjectUuidPK(this.editData.getId()), sels);
			UserInfo user = customerInfo.getSalesman();
			this.f7salesman.setValue(user);
			if(STATUS_ADDNEW.equals(this.getOprtState()))
			this.f7CustomerManager.setValue(user);
			adaColl = customerInfo.getAdapterLogList();
			shareSellColl = customerInfo.getShareSellerList();
		} catch (EASBizException e) {
			logger.error(e.getMessage());
			this.handleException(e);
		} catch (BOSException e) {
			logger.error(e.getMessage());
			this.handleException(e);
		}
		this.tblAdapterLog.removeRows();
		if(adaColl.size()>0)
		{
			Timestamp lastUpdateTime = adaColl.get(0).getLastUpdateTime();
			String afterSellerID  = null;
			for(int i=0;i<adaColl.size();i++)
			{
				AdapterLogInfo adaInfo = (AdapterLogInfo)adaColl.get(i);
				addAdapterLogRow(adaInfo);
				if(adaInfo.getLastUpdateTime().after(lastUpdateTime) ||adaInfo.getLastUpdateTime().equals(lastUpdateTime))
				{
					lastUpdateTime = adaInfo.getLastUpdateTime();
					afterSellerID = adaInfo.getAfterSeller().getId().toString();
				}
			}
			for(int i=0;i<tblShareSeller.getRowCount();i++)
			{
				IRow row = tblShareSeller.getRow(i);
				ShareSellerInfo shareSellerInfo = (ShareSellerInfo)row.getUserObject();
				ShareModelEnum shareModel = shareSellerInfo.getShareModel();
				if(ShareModelEnum.sharePerson.equals(shareModel))
				{
					if(afterSellerID.equals(shareSellerInfo.getSeller().getId().toString()))
					{
						try {
							this.tblShareSeller.removeRow(i);
							ShareSellerFactory.getRemoteInstance().delete(new ObjectUuidPK(shareSellerInfo.getId()));
						} catch (EASBizException e) {
							e.printStackTrace();
						} catch (BOSException e) {
							e.printStackTrace();
						}
					}
				}
				
			}
		}
	}
	
	/**
	 * ��ʼ����ϵ���б�
	 * */
	private void initLinkmanTable() {
		btnAddLinkman = new KDWorkButton();
		this.actionAddLinkman.putValue(Action.SMALL_ICON, EASResource.getIcon("imgTbtn_sortstandard"));
		btnAddLinkman = (KDWorkButton) this.containLinkman.add(this.actionAddLinkman);
		btnAddLinkman.setToolTipText("������ϵ��");
		btnAddLinkman.setText("������ϵ��");
		
		btnRemoveLinkman = new KDWorkButton();
		this.actionRemoveLinkman.putValue(Action.SMALL_ICON, EASResource.getIcon("imgTbtn_deleteline"));
		btnRemoveLinkman = (KDWorkButton) this.containLinkman.add(this.actionRemoveLinkman);
		btnRemoveLinkman.setToolTipText("ɾ����ϵ��");
		btnRemoveLinkman.setText("ɾ����ϵ��");
		
		
		tblBaseSet(tblLinkman);
		
		IColumn columnRelation = this.tblLinkman.getColumn("relation");
		columnRelation.getStyleAttributes().setHorizontalAlign(HorizontalAlignment.LEFT);
		columnRelation.setEditor(createTxtCellEditor(80, true));
		
		IColumn columnName = this.tblLinkman.getColumn("customerNumber");
		columnName.getStyleAttributes().setHorizontalAlign(HorizontalAlignment.LEFT);
		columnName.setEditor(createTxtCellEditor(80, false));
		
		KDComboBox box=new KDComboBox();
		box.addItems(EnumUtils.getEnumList("com.kingdee.eas.fdc.sellhouse.SexEnum").toArray());		
		box.setSelectedIndex(0);
		KDTDefaultCellEditor sexEditor =  new KDTDefaultCellEditor(box);
		
		
		IColumn columnSex = this.tblLinkman.getColumn("customerSex");
		columnSex.getStyleAttributes().setHorizontalAlign(HorizontalAlignment.LEFT);
		//columnSex.setEditor(createTxtCellEditor(80, true));
		columnSex.setEditor(sexEditor);
		
		IColumn columnPhone = this.tblLinkman.getColumn("phone");
		columnPhone.getStyleAttributes().setHorizontalAlign(HorizontalAlignment.LEFT);
		columnPhone.setEditor(createTxtCellEditor(80, true));
		
		IColumn columnDes = this.tblLinkman.getColumn("description");
		columnDes.getStyleAttributes().setHorizontalAlign(HorizontalAlignment.LEFT);
		columnDes.setEditor(createTxtCellEditor(80, true));
		
		KDBizPromptBox f7Box = new KDBizPromptBox();
		f7Box.setEditFormat("$number$");
		f7Box.setDisplayFormat("$name$");
		f7Box.setCommitFormat("$number$");
		f7Box.setQueryInfo("com.kingdee.eas.fdc.sellhouse.app.CustomerQuery");
		
		//ֻ��ѡδ�����õ�
		FilterInfo mainFilter = new FilterInfo();
		mainFilter.getFilterItems().add(new FilterItemInfo("isEnabled", new Integer(1)));
		/*
		 * ע�͵�ԭ����ְλ���룬Ӧ�����Ե�ǰ��¼�û�������
		 * by xiaoao_liu
		 */
				
				//�Կͻ�F7����ְλ����
		//		Set personIds = FDCCustomerHelper.getChildPersonIdsOfCurrentUser();
		EntityViewInfo viewInfo = new EntityViewInfo();
		
		//�жϵ�ǰ�û��Ƿ��йܿص�ǰ��֯��Ȩ��
		OrgUnitInfo orgUnit = SysContext.getSysContext().getCurrentOrgUnit();
		String parentNumberStr = "'"+orgUnit.getNumber()+"'"; //�ϼ���֯���룬�����Լ�
		String[] parentNumbers = orgUnit.getLongNumber().split("!");
		if(parentNumbers.length>1) {
			for(int i=0;i<(parentNumbers.length-1);i++) {
				parentNumberStr += ",'"+ parentNumbers[i] +"'";
			}
		}
		MarketUnitControlCollection muControl=null;
		try {
			MarketUnitControlCollection muControlColl = MarketUnitControlFactory.getRemoteInstance().getMarketUnitControlCollection(
					"select orgUnit.id,orgUnit.number,orgUnit.longNumber where controler.id ='"+userInfo.getId().toString()+"' and orgUnit.number in ("+parentNumberStr+") ");
			muControl=muControlColl;
		} catch (BOSException e2) {
			e2.printStackTrace();
		}
		Set saleManIds = new HashSet();
		Set marketingUnitIDs = new HashSet();
		if(muControl!=null && muControl.size()>0){
			if(this.getUIContext().get("MarketingNode")!=null){
				TreeNode treeNode = (TreeNode)this.getUIContext().get("MarketingNode");
				getAllSaleManIds(treeNode,saleManIds);
				getAllMarketingunitIds(treeNode,marketingUnitIDs);
			}
		}
		
//		MarketUnitControlCollection muControlColl = MarketUnitControlFactory.getLocalInstance().getMarketUnitControlCollection(
//				"select orgUnit.id,orgUnit.number,orgUnit.longNumber where controler.id ='"+userInfo.getId().toString()+"' and orgUnit.number in ("+parentNumberStr+") ");
//		
		Set retSet=null;
		MarketingUnitCollection muColl=null;
	
		Set userID=new HashSet();
		userID.add(userInfo.getId().toString());
		try {
			retSet=MarketingUnitFactory.getRemoteInstance().getPermitSaleManIdSet(userInfo);
			muColl=MarketingUnitFactory.getRemoteInstance().getMarketUnitCollByDutySaleMan(userInfo);
		} catch (EASBizException e1) {
			e1.printStackTrace();
		} catch (BOSException e1) {
			e1.printStackTrace();
		}
		
		Set marketingUnitID =new HashSet();
		Set saleOrgID=new HashSet();
		Set saleOrgIDs=new HashSet();
		saleOrgIDs.add(saleOrg.getId().toString());
	
		if(muColl!=null && muColl.size()<0){
			for(int i=0;i<muColl.size();i++){
				marketingUnitID.add(muColl.get(i).getId().toString());
			}
		}
		if(marketingUnitID!=null && marketingUnitID.size()>0){
			saleOrgID.add(saleOrg.getId().toString());
		}
		FilterInfo filter = new FilterInfo();
		FilterItemInfo item = new FilterItemInfo();
		
		if(muControl!=null && muControl.size()>0){//��ǰ�û�Ϊ�ܿ���Ա��ʱ����Կ������е�
			filter.getFilterItems().add(new FilterItemInfo("salesman.id", saleManIds, CompareType.INCLUDE));
			filter.getFilterItems().add(new FilterItemInfo("shareSellerList.seller.id", saleManIds, CompareType.INCLUDE));
			filter.getFilterItems().add(new FilterItemInfo("shareSellerList.marketingUnit.id", marketingUnitIDs, CompareType.INCLUDE));
			filter.getFilterItems().add(new FilterItemInfo("shareSellerList.orgUnit.id", saleOrg.getId().toString(), CompareType.INCLUDE));
			filter.setMaskString("(#0 or #1 or #2 or #3)");
		}else{
			if(retSet == null){
			item = new FilterItemInfo("salesman.id", userInfo.getId().toString());
		    }else{
//			item = new FilterItemInfo("salesman.person.id", personIds, CompareType.INCLUDE);
			item = new FilterItemInfo("salesman.id", retSet, CompareType.INCLUDE);
		    }
		   filter.getFilterItems().add(item);
        
		   filter.getFilterItems().add(new FilterItemInfo("shareSellerList.seller.id", retSet, CompareType.INCLUDE));
		   if(marketingUnitID!=null && marketingUnitID.size()>0){
			  filter.getFilterItems().add(new FilterItemInfo("shareSellerList.marketingUnit.id", marketingUnitID, CompareType.INCLUDE));
			  filter.getFilterItems().add(new FilterItemInfo("shareSellerList.orgUnit.id", saleOrgID, CompareType.INCLUDE));
			  filter.setMaskString("(#0 or #1 or #2 or #3)");
		   }else{
			  filter.setMaskString("(#0 or #1)");
		   }
		}


		try {
			mainFilter.mergeFilter(filter, "and");
		} catch (BOSException e) {
			logger.error(e.getMessage());
			this.handleException(e);
		}
		
		viewInfo.setFilter(mainFilter);
		f7Box.setEntityViewInfo(viewInfo);
		
		KDTDefaultCellEditor f7Editor = new KDTDefaultCellEditor(f7Box);
		
		IColumn columnNumber = this.tblLinkman.getColumn("customerName");
		columnNumber.getStyleAttributes().setHorizontalAlign(HorizontalAlignment.LEFT);
		columnNumber.setEditor(f7Editor);

//		this.tblLinkman.getColumn("jiange").getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(0));
	}
	
	
	/**
	 * �Խ����ϵĿɷ������ɷ�༭�Ƚ��п���
	 * */
	private void initControl() {
		this.dpBookedDate.setEnabled(false);
		this.txtBooker.setEditable(false);
		
		//�������²����޸������͵绰
		boolean isStatusAddNew = this.oprtState.equals(STATUS_ADDNEW);
		this.txtName.setEditable(isStatusAddNew);
		this.txtPhone.setEditable(isStatusAddNew);
		
		boolean isStatusView = this.oprtState.equals(STATUS_VIEW);
		this.tblLinkman.setEditable(!isStatusView);
		this.tblTrackRecord.setEditable(!isStatusView);
		
		//�޸Ļ�鿴״̬���Խ������Ӹ�����¼�Ĳ���
		boolean isViewOrEdit = isStatusView || STATUS_EDIT.equals(this.oprtState);
		
		this.actionAddLine.setEnabled(isViewOrEdit);
//		this.actionCustomerAdapter.setEnabled(isViewOrEdit);
//		this.actionCustomerShare.setEnabled(isViewOrEdit);
//		this.actionCustomerCancelShare.setEnabled(isViewOrEdit);
		
		boolean isStatusEdit = this.oprtState.equals(STATUS_EDIT);
		this.txtNumber.setEditable(!isStatusEdit);
		
		this.chkMenuItemSubmitAndAddNew.setSelected(false);
	}
	//���෽����������д����
	protected void checkIsOUSealUp() throws Exception{
	}
	
	public void actionAddNew_actionPerformed(ActionEvent e) throws Exception {
		super.actionAddNew_actionPerformed(e);
		actionInsider.setEnabled(false);
		
	}
	
	
	public void actionEdit_actionPerformed(ActionEvent e) throws Exception {
		String fdcCustomerID = this.editData.getId().toString();
		if(!hasUpdatePermission(fdcCustomerID))
		{
			MsgBox.showInfo("�Ըù���ͻ�û���޸�Ȩ!");
			this.abort();
		}
		if(this.tblShareSeller.getRowCount()>0)
		{
			for(int i=0;i<tblShareSeller.getRowCount();i++)
			{
				IRow row = tblShareSeller.getRow(i);
				ShareModelEnum shareModel = (ShareModelEnum)row.getCell("shareModel").getValue();
				if(ShareModelEnum.shareMarket.equals(shareModel) || ShareModelEnum.shareOrgUnit.equals(shareModel))
				{
					row.getCell("isAgainShare").getStyleAttributes().setLocked(true);
					row.getCell("isUpdate").getStyleAttributes().setLocked(false);
				}else
				{
					row.getCell("isAgainShare").getStyleAttributes().setLocked(false);
					row.getCell("isUpdate").getStyleAttributes().setLocked(false);
				}
			}
		}
		//�鿴״̬���ٵ��޸ģ��Խ���������п���
		
		boolean isStatusViewBefore = this.oprtState.equals(STATUS_VIEW);
		if(CustomerShareUI.hasSharePermission(fdcCustomerID))
		{
			this.actionCustomerShare.setEnabled(true);
			this.actionCustomerCancelShare.setEnabled(true);
		}
		
		if(CustomerAdapterUI.hasAdpaterPermission(fdcCustomerID))	{
			this.actionCustomerAdapter.setEnabled(true);
		}
		super.actionEdit_actionPerformed(e);
		if(isStatusViewBefore)
			initControl();
	}
	
	public void loadFields() {
		initControl();//�����ӣ��쿴���޸Ĳ���ʱ�����������Ӧ����,���鿴״̬���ٵ��޸Ĳ�����øú�������Ҫ���⴦��
		
logger.info("CustomerExtEditUI:loadFields--" + this.getOprtState() + new Timestamp(new Date().getTime()));		
		
		FDCCustomerInfo info = this.editData;
		loadTblLinkmanFields(info);
		if(STATUS_ADDNEW.equals(this.getOprtState()))
		{
			loadTblShareSeller(info);
			loadTblAdapterLog(info);
		}else
		{
			findShareSeller();
			findSaleman();
		}	
logger.info("CustomerExtEditUI:loadFields--" + "ShareSeller&&findSaleman" + new Timestamp(new Date().getTime()));		
		SHEHelper.setNumberEnabled(this.editData,this.getOprtState(),this.txtNumber);
		
		super.loadFields();
		
		if(!this.oprtState.equals(OprtState.ADDNEW)) {
			//���ؿͻ����ټ�¼
			initTrackRecordTable();
logger.info("CustomerExtEditUI:loadFields--" + "initTrackRecordTable()" + new Timestamp(new Date().getTime()));				
			//������¥�Ϲ���¼
			initSalePurchaseRecord();
logger.info("CustomerExtEditUI:loadFields--" + "initSalePurchaseRecord()" + new Timestamp(new Date().getTime()));			
			//�������������¼
			initTenancyBillRecord();
logger.info("CustomerExtEditUI:loadFields--" + "initTenancyBillRecord()" + new Timestamp(new Date().getTime()));			
			//���ػ�Ա��Ϣ
			initInside();
			//������¥�̻��������̻�
			//initInside();
			//eric_wang 2010.08.05
			this.tblSaleCommerce.removeRows();
			this.tblTenancyCommerce.removeRows();
			initCommerceChanceRecord("Sale");
			initCommerceChanceRecord("Tenancy");
logger.info("CustomerExtEditUI:loadFields--" + "initCommerceChanceRecord()" + new Timestamp(new Date().getTime()));			
		}
		
		if(editData.getId()!=null){
			int size = editData.getWorkArea().size();
			CoreBaseInfo[] workareas = new CoreBaseInfo[size];
			for(int i=0;i<size;i++){
				CustomerWorkAreaEntryInfo CWAEinfo = editData.getWorkArea().get(i);
				workareas[i] = CWAEinfo.getWorkArea();
			}
			f7WorkArea.setValue(workareas);
			
			int size1 = editData.getBusinessScope().size();
			CoreBaseInfo[] businessScope = new CoreBaseInfo[size1];
			for(int i=0;i<size1;i++){
				CustomerBusinessScopeEntryInfo CBSEinfo = editData.getBusinessScope().get(i);
				businessScope[i] = CBSEinfo.getBusinessScope();
			}
			f7BusinessScope.setValue(businessScope);
		}	
		this.storeFields();
		
		initOldData(this.editData);
	}
	
	
	
	/**
	 * �������ݵ���ϵ���б����
	 * */
	private void loadTblLinkmanFields(FDCCustomerInfo info) {
		//��ʱ��¼��Ŀͻ�Ϊ��ֻ��id,������Ҫ���²�ѯһ��
		//LinkmanEntryCollection linkmanList = info.getLinkmanList();
		
		if(info==null || info.getId()==null) return;
		EntityViewInfo view = new EntityViewInfo();
		view.setFilter(new FilterInfo());
		view.getFilter().appendFilterItem("head.id",info.getId().toString());
		view.getSelector().add("*");
		view.getSelector().add("fdcCustomer.*");
		LinkmanEntryCollection linkmanList = null;
		try {
			linkmanList = LinkmanEntryFactory.getRemoteInstance().getLinkmanEntryCollection(view);
		} catch (BOSException e) {
			e.printStackTrace();
		}				
		tblLinkman.removeRows();		
		
		for (int i = 0; i < linkmanList.size(); i++) {
			LinkmanEntryInfo entry = linkmanList.get(i);
			addLinkmanEntryRow(entry);
		}
	}
	
	private void addLinkmanEntryRow(LinkmanEntryInfo entry) {
		IRow row = tblLinkman.addRow();
		FDCCustomerInfo linkman = entry.getFdcCustomer();
		
		row.setUserObject(entry);

		row.getCell("customerName").setValue(linkman);
		row.getCell("name").setValue(entry.getPersonName());
		row.getCell("relation").setValue(entry.getRelation());
		row.getCell("customerNumber").setValue(
				linkman != null ? linkman.getNumber() : null);
		row.getCell("customerSex").setValue(entry.getSex());
		row.getCell("phone").setValue(entry.getPhone());
		row.getCell("description").setValue(entry.getDescription());

	}
	
	/**
	 * �������ݵ���ϵ���б����
	 * */
	private void loadTblShareSeller(FDCCustomerInfo info) {
		ShareSellerCollection shareSellerList = info.getShareSellerList();
		tblShareSeller.removeRows();
		for (int i = 0; i < shareSellerList.size(); i++) {
			ShareSellerInfo shareSellerInfo = shareSellerList.get(i);
			addShareSellerRow(shareSellerInfo);
		}
	}
	
	private void loadTblAdapterLog(FDCCustomerInfo info) {
		AdapterLogCollection adapterCpll = info.getAdapterLogList();
		tblAdapterLog.removeRows();
		for (int i = 0; i < adapterCpll.size(); i++) {
			AdapterLogInfo adaLogInfo = adapterCpll.get(i);
			addAdapterLogRow(adaLogInfo);
		}
	}
	
	
	/**
	 * �������ݵ�ת����ʷ��¼�б����
	 * */
	private void addAdapterLogRow(AdapterLogInfo info) {
		IRow row = this.tblAdapterLog.addRow();
		row.setUserObject(info);
		UserInfo beforeSeller = info.getBeforeSeller();
		UserInfo afterSeller = info.getAfterSeller();
		UserInfo operationPerson = info.getOperationPerson();
		
		row.getCell("beforeSeller").setValue(beforeSeller.getName());
		row.getCell("beforeSeller").setUserObject(beforeSeller);
		row.getCell("afterSeller").setValue(afterSeller.getName());
		row.getCell("afterSeller").setUserObject(afterSeller);
		row.getCell("operationPerson").setValue(operationPerson.getName());
		row.getCell("operationPerson").setUserObject(operationPerson);
		row.getCell("operationPerson").getStyleAttributes().setLocked(true);
		row.getCell("afterSeller").getStyleAttributes().setLocked(true);
		row.getCell("beforeSeller").getStyleAttributes().setLocked(true);
		row.getCell("adapterDate").setValue(info.getAdapterDate());
		row.getCell("adapterDate").getStyleAttributes().setLocked(true);
		row.getCell("isAdapterInter").setValue(new Boolean(info.isIsAdapterInter()));
		row.getCell("isAdapterFunction").setValue(new Boolean(info.isIsAdapterFunction()));
		row.getCell("isEndAdapter").setValue(new Boolean(info.isIsEndAdapter()));
//		if(STATUS_EDIT.equals(this.getOprtState()) || STATUS_ADDNEW.equals(this.getOprtState()))
//		{
//			row.getCell("isAdapterInter").getStyleAttributes().setLocked(false);
//			row.getCell("isAdapterFunction").getStyleAttributes().setLocked(false);
//			row.getCell("isEndAdapter").getStyleAttributes().setLocked(false);
//		}else
//		{
			row.getCell("isAdapterInter").getStyleAttributes().setLocked(true);
			row.getCell("isAdapterFunction").getStyleAttributes().setLocked(true);
			row.getCell("isEndAdapter").getStyleAttributes().setLocked(true);
//		}
	}
	/**
	 * �������ݵ��������۹����б����
	 * */
	private void addShareSellerRow(ShareSellerInfo info) {
		IRow row = tblShareSeller.addRow();
		row.setUserObject(info);
		ShareModelEnum shareModel = (ShareModelEnum)info.getShareModel();
		row.getCell("shareModel").setValue(shareModel);
		row.getCell("shareModel").getStyleAttributes().setLocked(true);
		if(ShareModelEnum.sharePerson.equals(shareModel))
		{		
			UserInfo userInfo = info.getSeller();	
			if(userInfo != null){
				row.getCell("shareObject").setValue(userInfo.getName());
				row.getCell("shareObject").setUserObject(userInfo);
				row.getCell("shareObject").getStyleAttributes().setLocked(true);
				if(info!=null && info.getOperationPerson()!=null){
					row.getCell("operationPerson").setValue(info.getOperationPerson().getName());
				}
				row.getCell("operationPerson").setUserObject(info.getOperationPerson());
				row.getCell("operationPerson").getStyleAttributes().setLocked(true);
				row.getCell("isAgainShare").setValue(new Boolean(info.isIsAgainShare()));
				
				row.getCell("shareDate").setValue(info.getShareDate());
				row.getCell("isUpdate").setValue(new Boolean(info.isIsUpdate()));
				
				if(STATUS_EDIT.equals(this.getOprtState()) || STATUS_ADDNEW.equals(this.getOprtState()))
				{
					row.getCell("isAgainShare").getStyleAttributes().setLocked(false);
					row.getCell("isUpdate").getStyleAttributes().setLocked(false);
				}else
				{
					row.getCell("isAgainShare").getStyleAttributes().setLocked(true);
					row.getCell("isUpdate").getStyleAttributes().setLocked(true);
				}
				row.getCell("description").setValue(info.getDescription());
			}else
			{
				row.getCell("isAgainShare").setValue(new Boolean(false));
				row.getCell("isUpdate").setValue(new Boolean(false));
				if(STATUS_EDIT.equals(this.getOprtState()) || STATUS_ADDNEW.equals(this.getOprtState()))
				{
					row.getCell("isAgainShare").getStyleAttributes().setLocked(false);
					row.getCell("isUpdate").getStyleAttributes().setLocked(false);
				}else
				{
					row.getCell("isAgainShare").getStyleAttributes().setLocked(true);
					row.getCell("isUpdate").getStyleAttributes().setLocked(true);
				}
			}
		}else if(ShareModelEnum.shareMarket.equals(shareModel))
		{
			MarketingUnitInfo marketingInfo = (MarketingUnitInfo)info.getMarketingUnit();
			if(marketingInfo!=null)
			{
				row.getCell("shareObject").setValue(marketingInfo.getName());
				row.getCell("shareObject").setUserObject(marketingInfo);
				row.getCell("shareObject").getStyleAttributes().setLocked(true);
				
				row.getCell("operationPerson").setValue(info.getOperationPerson().getName());
				row.getCell("operationPerson").setUserObject(info.getOperationPerson());
				row.getCell("operationPerson").getStyleAttributes().setLocked(true);
				
                row.getCell("isAgainShare").setValue(new Boolean(info.isIsAgainShare()));
				
				row.getCell("shareDate").setValue(info.getShareDate());
				row.getCell("isUpdate").setValue(new Boolean(info.isIsUpdate()));
				row.getCell("isAgainShare").getStyleAttributes().setLocked(true);
				row.getCell("shareDate").getStyleAttributes().setLocked(true);
				//row.getCell("isUpdate").getStyleAttributes().setLocked(true);
				if(STATUS_EDIT.equals(this.getOprtState()) || STATUS_ADDNEW.equals(this.getOprtState()))
				{
					row.getCell("isUpdate").getStyleAttributes().setLocked(false);
				}else
				{
					row.getCell("isUpdate").getStyleAttributes().setLocked(true);
				}
				row.getCell("description").setValue(info.getDescription());
			}
		}else if(ShareModelEnum.shareOrgUnit.equals(shareModel))
		{
			FullOrgUnitInfo orgUnitInfo = (FullOrgUnitInfo)info.getOrgUnit();
			if(orgUnitInfo!=null)
			{
				row.getCell("shareObject").setValue(orgUnitInfo.getName());
				row.getCell("shareObject").setUserObject(orgUnitInfo);
				row.getCell("shareObject").getStyleAttributes().setLocked(true);
				
				row.getCell("operationPerson").setValue(info.getOperationPerson().getName());
				row.getCell("operationPerson").setUserObject(info.getOperationPerson());
				row.getCell("operationPerson").getStyleAttributes().setLocked(true);
				
                row.getCell("isAgainShare").setValue(new Boolean(info.isIsAgainShare()));
				
				row.getCell("shareDate").setValue(info.getShareDate());
				row.getCell("isUpdate").setValue(new Boolean(info.isIsUpdate()));
				row.getCell("isAgainShare").getStyleAttributes().setLocked(true);
				row.getCell("shareDate").getStyleAttributes().setLocked(true);
				//row.getCell("isUpdate").getStyleAttributes().setLocked(true);
				if(STATUS_EDIT.equals(this.getOprtState()) || STATUS_ADDNEW.equals(this.getOprtState()))
				{
					row.getCell("isUpdate").getStyleAttributes().setLocked(false);
				}else
				{
					row.getCell("isUpdate").getStyleAttributes().setLocked(true);
				}
				row.getCell("description").setValue(info.getDescription());
			}
		}
		
	}
	
	protected IObjectValue createNewData() {
		FDCCustomerInfo value = new FDCCustomerInfo();
		value.setIsEnabled(true);
		value.setIsImportantTrack(false);
		value.setCustomerType(CustomerTypeEnum.PersonalCustomer);
		value.setTrackPhase(TrackPhaseEnum.PotentialCustomer);
//		value.setSex(SexEnum.Mankind);
		//value.setCertificateName(CertifacateNameEnum.IDCard);
		value.setSalesman(userInfo);
		try{
			value.setCreateTime(SHEHelper.getServerDate());
		}catch(Exception e){
			e.printStackTrace();
		}
		value.setCreator(userInfo);
		
		value.setEnterpriceProperty(EnterprisePropertyEnum.PersonalAsset);
		
		FDCCustomerInfo lsCus = getLastAddedCustomer();
		if(lsCus != null){
			value.setProject(lsCus.getProject());
			value.setBookedPlace(lsCus.getBookedPlace());
			value.setReceptionType(lsCus.getReceptionType());
		}
		value.setCU(SysContext.getSysContext().getCurrentCtrlUnit());
		/*
		 * ֧��Ĭ��ֵ����,�������û��������¥ҵ�����޺���ҵְ��,��Ĭ��ѡ����¥ҵ�����޺���ҵְ�ܸ�ѡ��
		 * add by wenyaowei 20090616
		 * ------------------start
		 */
		boolean isSellFunction = false;
		boolean isTenancyFunction = false;
		boolean isWuYeFunction = false;
		MarketingUnitMemberCollection memCol = null;
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		view.setFilter(filter);
		filter.getFilterItems().add(new FilterItemInfo("member.id", SysContext.getSysContext().getCurrentUserInfo().getId().toString()));

		try {
			memCol = MarketingUnitMemberFactory.getRemoteInstance().getMarketingUnitMemberCollection(view);
		} catch (BOSException e) {
			handleException(e);
		}
		if (!memCol.isEmpty()) {
			for (int i = 0; i < memCol.size(); i++) {
				MarketingUnitMemberInfo info = memCol.get(i);
				if (info.isIsSellFunction()) {
					isSellFunction = true;
				}
				if (info.isIsTenancyFunction()) {
					isTenancyFunction = true;
				}
				if (info.isIsWuYeFunction()) {
					isWuYeFunction = true;
				}
			}
		}		
		
		value.setIsForSHE(isSellFunction); // ������¥ְ��
		value.setIsForTen(isTenancyFunction);// ��������ְ��
		value.setIsForPPM(isWuYeFunction); // ������ҵְ��
		/*
		 * ------------------end
		 */
		
		
	//��������ťʱ������������������֮ǰ�û���ĸ�����¼��Ϣ�������������������
		this.tblAdapterLog.removeRows(false);
		this.tblInsider.removeRows(false);
		this.tblLinkman.removeRows(false);
		this.tblSaleCommerce.removeRows(false);
		this.tblSaleRecord.removeRows(false);
		this.tblShareSeller.removeRows(false);
		this.tblTenancyCommerce.removeRows(false);
		this.tblTenancyCommerce.removeRows(false);
		this.tblTrackRecord.removeRows(false);
		
		return value;
	}
	
	private static FDCCustomerInfo lastAddedCustomerCache;//���ڻ���ͻ��ϴ���ӵĿͻ�
	private FDCCustomerInfo getLastAddedCustomer(){
		try {
			initLastAddedCustomerCache(false);
		} catch (BOSException e) {
			logger.warn("��ʼ�����¼��ͻ�����"+e.getMessage());
			logger.debug("", e);
		}
		return lastAddedCustomerCache;
	}
	
	//
	private void initLastAddedCustomerCache(boolean reload) throws BOSException {
		if(lastAddedCustomerCache != null  &&  !reload){
			return;
		}
		FilterInfo cusFilter = new FilterInfo();
		cusFilter.getFilterItems().add(new FilterItemInfo("booker", userInfo.getName()));
		SelectorItemCollection sel = new SelectorItemCollection();
		sel.add(new SelectorItemInfo("*"));
		sel.add(new SelectorItemInfo("receptionType.*"));
		sel.add(new SelectorItemInfo("project.*"));
		EntityViewInfo view = new EntityViewInfo();
		view.getSelector().addObjectCollection(sel);
		view.setFilter(cusFilter);
		
		FDCCustomerCollection cs = FDCCustomerFactory.getRemoteInstance().getFDCCustomerCollection(view);
		lastAddedCustomerCache = getLastRecord(cs);//ȡ���µ�һ��	
	}
	
	private FDCCustomerInfo getLastRecord(FDCCustomerCollection cs) {
		FDCCustomerInfo cus = null;
		for(int i=0; i<cs.size(); i++){
			FDCCustomerInfo tmp = cs.get(i);
			if(cus == null){
				cus = tmp;
				continue;
			}
			if(cus.getCreateTime() != null  &&  cus.getCreateTime().before(tmp.getCreateTime())){
				cus = tmp;
			}
		}
		return cus;
	}
	
	protected ICoreBase getBizInterface() throws Exception {
		return FDCCustomerFactory.getRemoteInstance();
	}	
	
	public void actionAddLine_actionPerformed(ActionEvent e) throws Exception {
		super.actionAddLine_actionPerformed(e);
		if(this.editData!=null && this.editData.getId()!=null) {
			UIContext uiContext = new UIContext(this); 		
			uiContext.put("FdcCustomer", this.editData);
		
			SellProjectInfo proInfo = (SellProjectInfo)this.f7Project.getValue();
			if(proInfo!=null)
			uiContext.put("SellProject", proInfo);
			
			if(this.chkIsForSHE.isSelected())
				uiContext.put("MoneySysTypeEnum", MoneySysTypeEnum.SalehouseSys);
			else if(this.chkIsForTen.isSelected())
				uiContext.put("MoneySysTypeEnum", MoneySysTypeEnum.TenancySys);
			
			try {
				IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.MODEL).create(TrackRecordEditUI.class.getName(), uiContext, null, OprtState.ADDNEW);
				uiWindow.show();
			} catch (UIException ee) {
				logger.error(ee.getMessage());
				this.handleException(ee);
			}
			initTrackRecordTable();   //ˢ��
		}
	}
	
	public void actionAddLinkman_actionPerformed(ActionEvent e) throws Exception {
		super.actionAddLinkman_actionPerformed(e);
		LinkmanEntryInfo entry = new LinkmanEntryInfo();
		this.addLinkmanEntryRow(entry);
	}
	
	
	public void actionRemoveLinkman_actionPerformed(ActionEvent e)
			throws Exception {
		
		if (tblLinkman != null) {
			removeLine(tblLinkman);
			appendFootRow(tblLinkman);
			// ʵ��ɾ����Ľ������ 2007-10-09 haiti_yang
			if (tblLinkman.getRowCount() == 0) {
				FocusTraversalPolicy policy = null;
				Container container = null;
				Component initComponent = null;
				if (this.getFocusTraversalPolicy() != null
						&& this.getFocusTraversalPolicy() instanceof UIFocusTraversalPolicy) {
					policy = this.getFocusTraversalPolicy();
					container = this;
					Component[] traverComponent = ((UIFocusTraversalPolicy) policy)
							.getComponents();
					for (int i = 0; i < traverComponent.length; i++) {
						if (traverComponent[i] == this.tblLinkman) {
							initComponent = traverComponent[i];
							break;
						}
					}
					if (initComponent == null) {
						initComponent = policy.getLastComponent(container);
						initComponent.requestFocusInWindow();
					} else if (initComponent != null) {
						Component component = policy.getComponentBefore(
								container, initComponent);
						while ((component instanceof IKDTextComponent) == false
								|| component.isEnabled() == false) {
							component = policy.getComponentBefore(container,
									component);
						}
						component.requestFocusInWindow();
					}
				} else if (policy == null) {
					if (this.getUIWindow() instanceof Dialog) {
						policy = ((Dialog) uiWindow).getFocusTraversalPolicy();
						container = (Dialog) uiWindow;
					} else if (this.getUIWindow() instanceof Window) {
						policy = ((Window) uiWindow).getFocusTraversalPolicy();
						container = (Window) uiWindow;
					}

					if (policy != null) {
						try {
							Component component = policy.getComponentBefore(
									container, tblLinkman);
							while ((component instanceof IKDTextComponent) == false
									|| component.isEnabled() == false) {
								component = policy.getComponentBefore(
										container, component);
							}
							component.requestFocusInWindow();
						} catch (Exception ex) {
						}
					}
				}
			}
		}
		//super.actionRemoveLinkman_actionPerformed(e);
	}

	/**
	 * ��ָ�������ɾ����ǰѡ���� ���Ӹ���ɾ������ 2007-03-12
	 * 
	 * @param table
	 */
	protected void removeLine(KDTable table) {
		if (table == null) {
			return;
		}

		if ((table.getSelectManager().size() == 0))
		// || isTableColumnSelected(table))
		{
			MsgBox.showInfo(this, EASResource
					.getString(FrameWorkClientUtils.strResource
							+ "Msg_NoneEntry"));

			return;
		}

		// [begin]����ɾ����¼����ʾ����
		if (confirmRemove()) {
			// ��ȡѡ�����ܸ���
			KDTSelectManager selectManager = table.getSelectManager();
			int size = selectManager.size();
			KDTSelectBlock selectBlock = null;
			// ��Ϊ������˳����ܲ����Ǳ����е�˳��������Ҫ����ʹѡ����˳����������С����
			Set indexSet = new HashSet();

			for (int blockIndex = 0; blockIndex < size; blockIndex++) {
				selectBlock = selectManager.get(blockIndex);
				int top = selectBlock.getBeginRow();
				int bottom = selectBlock.getEndRow();
				if (table.getRow(top) == null) {
					MsgBox.showInfo(this, EASResource
							.getString(FrameWorkClientUtils.strResource
									+ "Msg_NoneEntry"));
					return;
				}
				for (int i = top; i <= bottom; i++) {
					indexSet.add(new Integer(i));
				}
			}
			Integer[] indexArr = new Integer[indexSet.size()];
			Object[] indexObj = indexSet.toArray();
			System.arraycopy(indexObj, 0, indexArr, 0, indexArr.length);
			Arrays.sort(indexArr);
			if (indexArr == null)
				return;
			for (int i = indexArr.length - 1; i >= 0; i--) {
				int rowIndex = Integer.parseInt(String.valueOf(indexArr[i]));
				IObjectValue detailData = (IObjectValue) table.getRow(rowIndex)
						.getUserObject();
				table.removeRow(rowIndex);
				IObjectCollection collection = (IObjectCollection) table
						.getUserObject();

				if (collection == null) {
					logger.error("collection not be binded to table");
				} else {
					// Modify By Jacky Zhang
					if (detailData != null) {
						int index = getCollectionIndex(collection, detailData);
						// �����кϼ��еķ�¼����
						if (index >= 0 && collection.size() > index) {
							collection.removeObject(index);
						}
					}
				}
			///	afterRemoveLine(table, detailData);
			}

			// ��������м�¼��λ����һ��
			if (table.getRow(0) != null)
				table.getSelectManager().select(0, 0);
		}
	}
	
	// ��ΪĿǰObjectValue�Ƚ��ǰ�ֵ�Ƚϣ���������ʹ�ã������¼ֵ��ͬ��
	// ����ɾ���ҵ��ĵ�һ�������������ʵ�ְ�ָ��Ƚϡ�2007-2-5
	private int getCollectionIndex(IObjectCollection collection,
			IObjectValue obj) {
		int index = -1;
		if (collection == null) {
			return index;
		}
		for (int i = collection.size() - 1; i >= 0; i--) {
			if (obj == collection.getObject(i)) {
				index = i;
				return index;
			}
		}
		return index;
	}
	//������ϵ�˺͸�����¼�Լ����򷿼��Store
	public void storeFields() {
		FDCCustomerInfo type = this.editData;
		storeLinkmanList(type);		
		storeShareSellerList(type);
		storeAdapterLog(type);
		super.storeFields();
		storeWorkArea(type);
		storeBusinessScope(type);
		//�����ϴα���Ŀͻ�������Ŀ
		if(this.oprtState.equals(STATUS_ADDNEW)){
			lastAddedCustomerCache = this.editData;
		}
	}
	/*
	 * ��������f7��ѡ���ܱ���
	 */
	private void storeWorkArea(FDCCustomerInfo type) {
		type.getWorkArea().clear();
		if(f7WorkArea.getText()!=null&&(!f7WorkArea.getText().equals(""))){
			Object v = f7WorkArea.getValue();
	 		if(v instanceof Object[]){
				Object object[] = (Object[])v;
				for(int i=0;i<object.length;i++){
					WorkAreaInfo workarea = (WorkAreaInfo)object[i];
					CustomerWorkAreaEntryInfo customerWorkAreaEntryInfo  = new CustomerWorkAreaEntryInfo();				
					customerWorkAreaEntryInfo.setId(BOSUuid.create(customerWorkAreaEntryInfo.getBOSType()));
					customerWorkAreaEntryInfo.setWorkArea(workarea); 
					customerWorkAreaEntryInfo.setParent(editData);
					type.getWorkArea().add(customerWorkAreaEntryInfo);
				}
			}else if(v instanceof WorkAreaInfo){
				WorkAreaInfo workarea = (WorkAreaInfo)v;
				CustomerWorkAreaEntryInfo customerWorkAreaEntryInfo  = new CustomerWorkAreaEntryInfo();	
				customerWorkAreaEntryInfo.setId(BOSUuid.create(customerWorkAreaEntryInfo.getBOSType()));
				customerWorkAreaEntryInfo.setWorkArea(workarea);
				customerWorkAreaEntryInfo.setParent(editData);
				type.getWorkArea().add(customerWorkAreaEntryInfo);
			}
		}
	}
	
	
	/*
	 * ҵ��Χf7��ѡ���ܱ���
	 */
	private void storeBusinessScope(FDCCustomerInfo type) {
		type.getBusinessScope().clear();
		if(f7BusinessScope.getText()!=null&&(!f7BusinessScope.getText().equals(""))){
			Object v = f7BusinessScope.getValue();
	 		if(v instanceof Object[]){
				Object object[] = (Object[])v;
				for(int i=0;i<object.length;i++){
					BusinessScopeInfo businessScopeInfo = (BusinessScopeInfo)object[i];
					CustomerBusinessScopeEntryInfo customerBusinessScopeEntryInfo  = new CustomerBusinessScopeEntryInfo();				
					customerBusinessScopeEntryInfo.setId(BOSUuid.create(customerBusinessScopeEntryInfo.getBOSType()));
					customerBusinessScopeEntryInfo.setBusinessScope(businessScopeInfo); 
					customerBusinessScopeEntryInfo.setParent(editData);
					type.getBusinessScope().add(customerBusinessScopeEntryInfo);
				}
			}else if(v instanceof WorkAreaInfo){
				BusinessScopeInfo businessScopeInfo = (BusinessScopeInfo)v;
				CustomerBusinessScopeEntryInfo customerBusinessScopeEntryInfo  = new CustomerBusinessScopeEntryInfo();				
				customerBusinessScopeEntryInfo.setId(BOSUuid.create(customerBusinessScopeEntryInfo.getBOSType()));
				customerBusinessScopeEntryInfo.setBusinessScope(businessScopeInfo); 
				customerBusinessScopeEntryInfo.setParent(editData);
				type.getBusinessScope().add(customerBusinessScopeEntryInfo);
			}
		}
	}
	/**
	 * ���ݸ��������ж�����һ��������¼����������ֻ��ȷ���죬����ȡ�ü�¼��׼ȷ
	 * */
	/*  ���ټ�¼Ϊ�ͻ���¼��ʱ ���õ�  ,���ڸ��ټ�¼�Ѿ�������� 
	 private TrackRecordInfo getLastTrackDate(TrackRecordCollection collection) {
	 TrackRecordInfo lastTrack = null;
	 for(int i=0; i<collection.size(); i++){
	 TrackRecordInfo tmp = collection.get(i);
	 if(lastTrack == null){
	 lastTrack = tmp;
	 continue;
	 }
	 if(tmp.getEventDate().after(lastTrack.getEventDate())){
	 lastTrack = tmp;
	 }
	 }
	 return lastTrack;
	 }
	 
	 
	 private void storeTrackRecordList(FDCCustomerInfo type) {
	 TrackRecordCollection trackRecordList = type.getTrackRecordList();
	 trackRecordList.clear();
	 for(int i=0; i<tblTrackRecord.getRowCount(); i++){
	 IRow row = tblTrackRecord.getRow(i);
	 TrackRecordInfo entry = (TrackRecordInfo) row.getUserObject();
	 Object obj = row.getCell("eventType").getValue();
	 if(obj == null){
	 logger.info("û��ѡ���¼����ͣ������б��棡");
	 continue;
	 }
	 entry.setEventDate((Date) row.getCell("eventDate").getValue());
	 entry.setEventType((EventTypeInfo) obj);
	 entry.setDescription((String) row.getCell("description").getValue());			
	 trackRecordList.add(entry);
	 }
	 }
	 */
	
	
	private void storeLinkmanList(FDCCustomerInfo type) {
		LinkmanEntryCollection linkmanList = type.getLinkmanList();
		linkmanList.clear();
		for (int i = 0; i < tblLinkman.getRowCount(); i++) {
			IRow row = tblLinkman.getRow(i);
			LinkmanEntryInfo entry = (LinkmanEntryInfo) row.getUserObject();
			if(entry == null){
				entry  = new LinkmanEntryInfo();
				entry.setId(BOSUuid.create(entry.getBOSType()));
			}
			Object obj = row.getCell("customerName").getValue();
			/*if(row.getCell("phone").getValue()==null||row.getCell("phone").getValue().toString().equals("")){
				MsgBox.showError("��ϵ�˷�¼����ϵ�绰����Ϊ�գ�");
				abort();
			}*/
//			if(obj == null){
//				logger.info("û��ѡ����ϵ�ˣ������б��棡");
//				continue;
//			}
			entry.setFdcCustomer((FDCCustomerInfo) obj);
			entry.setRelation((String) row.getCell("relation").getValue());
			if(row.getCell("customerSex").getValue()!=null){
				entry.setSex(row.getCell("customerSex").getValue().toString());
			}else{
				entry.setSex("");
			}
			entry.setPhone((String) row.getCell("phone").getValue());
			entry.setDescription((String) row.getCell("description").getValue());
			entry.setPersonName((String)row.getCell("name").getValue());
			linkmanList.add(entry);
		}
	}
	
	private void storeAdapterLog(FDCCustomerInfo type)
	{
		AdapterLogCollection adapterLogList = type.getAdapterLogList();
		adapterLogList.clear();
		if(tblAdapterLog.getRowCount()>0)
		{
			for(int i=0;i<tblAdapterLog.getRowCount();i++)
			{
				IRow row = tblAdapterLog.getRow(i);
				AdapterLogInfo adapterLogInfo = (AdapterLogInfo) row.getUserObject();
				adapterLogInfo.setAfterSeller((UserInfo)row.getCell("afterSeller").getUserObject());
				adapterLogInfo.setBeforeSeller((UserInfo)row.getCell("beforeSeller").getUserObject());
				adapterLogInfo.setOperationPerson((UserInfo)row.getCell("operationPerson").getUserObject());
				adapterLogInfo.setAdapterDate((Date)row.getCell("adapterDate").getValue());
				if(row.getCell("isAdapterInter").getValue()==null)
				{
					adapterLogInfo.setIsAdapterInter(false);
				}else
				{
					adapterLogInfo.setIsAdapterInter(((Boolean)row.getCell("isAdapterInter").getValue()).booleanValue());
				}
				if(row.getCell("isAdapterFunction").getValue()==null)
				{
					adapterLogInfo.setIsAdapterFunction(false);
				}else
				{
					adapterLogInfo.setIsAdapterFunction(((Boolean)row.getCell("isAdapterFunction").getValue()).booleanValue());
				}
				if(row.getCell("isEndAdapter").getValue()==null)
				{
					adapterLogInfo.setIsEndAdapter(false);
				}else
				{
					adapterLogInfo.setIsEndAdapter(((Boolean)row.getCell("isEndAdapter").getValue()).booleanValue());
				}
				adapterLogList.add(adapterLogInfo);
			}
			
		}
		
	}
	
	private void storeShareSellerList(FDCCustomerInfo type) {
		ShareSellerCollection shareSellerList = type.getShareSellerList();
		shareSellerList.clear();
		if(tblShareSeller.getRowCount()>0){
			for (int i = 0; i < tblShareSeller.getRowCount(); i++) {
				IRow row = tblShareSeller.getRow(i);
				ShareSellerInfo shareSellerInfo = (ShareSellerInfo) row.getUserObject();
				ShareModelEnum shareModel = shareSellerInfo.getShareModel();
				if(ShareModelEnum.sharePerson.equals(shareModel))
				{
					Object obj = row.getCell("shareObject").getUserObject();
					if(obj == null){
						logger.info("û��ѡ�����۹��ʣ������б��棡");
						continue;
					}
					shareSellerInfo.setSeller((UserInfo)obj);
				}else if(ShareModelEnum.shareMarket.equals(shareModel))
				{
					Object obj = row.getCell("shareObject").getUserObject();
					shareSellerInfo.setMarketingUnit((MarketingUnitInfo)obj);
				}else if(ShareModelEnum.shareOrgUnit.equals(shareModel))
				{
					Object obj = row.getCell("shareObject").getUserObject();
					shareSellerInfo.setOrgUnit((FullOrgUnitInfo)obj);
				}
				
				if(row.getCell("isAgainShare").getValue()==null)
				{
					shareSellerInfo.setIsAgainShare(false);
				}else
				{
					shareSellerInfo.setIsAgainShare(((Boolean)row.getCell("isAgainShare").getValue()).booleanValue());
				}
				try {
					shareSellerInfo.setShareDate(FDCSQLFacadeFactory.getRemoteInstance()
							.getServerTime());
				} catch (BOSException e) {
					logger.error(e.getMessage());
					this.handleException(e);
				}
				shareSellerInfo.setOperationPerson((UserInfo)row.getCell("operationPerson").getUserObject());
				if(row.getCell("isUpdate").getValue()==null)
				{
					shareSellerInfo.setIsUpdate(false);
				}else
				{
					shareSellerInfo.setIsUpdate(((Boolean)row.getCell("isUpdate").getValue()).booleanValue());
				}
				shareSellerInfo.setDescription((String) row.getCell("description").getValue());
				shareSellerList.add(shareSellerInfo);
			}
		}
	}
	
	public SelectorItemCollection getSelectors() {
		SelectorItemCollection selectors = super.getSelectors();
		selectors.add("*");
		selectors.add("salesman.*");
		selectors.add("adapterLogList.*");
		selectors.add("adapterLogList.beforeSeller.*");
		selectors.add("adapterLogList.afterSeller.*");
		selectors.add("adapterLogList.operationPerson.*");
		selectors.add("workArea.*");
		selectors.add("workArea.parent");
		selectors.add("workArea.workArea.*");
		selectors.add("businessScope.*");
		selectors.add("businessScope.parent");
		selectors.add("businessScope.businessScope.*");
		selectors.add("linkmanList.*");
		selectors.add("intentionArea");
		selectors.add("age");
		
		return selectors;
	}
	
	public static final String KEY_DESTORY_WINDOW = "destoryWindowAfterSubmit";
	public static final String KEY_SUBMITTED_DATA = "submittedData";
	public void actionSubmit_actionPerformed(ActionEvent e) throws Exception {
		
		super.actionSubmit_actionPerformed(e);
		if(Boolean.TRUE.equals(this.getUIContext().get(KEY_DESTORY_WINDOW))){
			destroyWindow();
		}
		
		this.getUIContext().put(KEY_SUBMITTED_DATA, this.editData);
		CacheServiceFactory.getInstance().discardType(new LinkmanEntryInfo().getBOSType());
		CacheServiceFactory.getInstance().discardType(new TrackRecordInfo().getBOSType());
		CacheServiceFactory.getInstance().discardType(new FDCCustomerInfo().getBOSType());
		CacheServiceFactory.getInstance().discardType(new ShareSellerInfo().getBOSType());
		actionInsider.setEnabled(true);
		
	}
	
	
	
	/**
	 * ��¼�������֤
	 * */
	protected void beforeStoreFields(ActionEvent e) throws Exception {
		super.beforeStoreFields(e);
		vertifyInput();
	}
	
	public void vertifyInput() throws Exception {
//		FDCCustomerInfo info = this.editData;
		if(this.txtNumber.isEnabled() && this.txtNumber.isEditable() && StringUtils.isEmpty(this.txtNumber.getText())){
			MsgBox.showWarning("�ͻ����벻��Ϊ��");
			this.abort();
		}
		
		if(!this.chkIsForSHE.isSelected()  &&  !this.chkIsForTen.isSelected()  &&  !this.chkIsForPPM.isSelected()){
			MsgBox.showInfo(this, "��¥,����,��ҵ��������ѡһ��!");
			this.abort();
		}
		
		verifyF7InputNotNull(f7Project, "��ʼ�Ǽ���Ŀ����Ϊ��");
		
		if(this.f7ReceptionType.getValue()==null) {
			MsgBox.showInfo(this, "�״νӴ���ʽ����Ϊ��!");
			this.abort();			
		}
		
		if(this.txtPhone.getText().length()>0&&this.txtPhone.getText().length()<5){
			MsgBox.showWarning("�ƶ��绰¼��ʱ����λ����������5λ��");
			this.abort();
		}
		verifyInputNotNull(txtEmployment, "ְҵ����Ϊ��");
		verifyF7RoomModelType(f7CustomerGrade, "�ͻ��ּ�����Ϊ��");
		verifyF7RoomModelType(f7RoomModelType, "�����Ͳ���Ϊ��");
		verifyF7RoomModelType(f7HabitationArea, "��ס������Ϊ��");
		verifyF7RoomModelType(f7CustomerOrigin, "�ͻ���Դ����Ϊ��");
		if(this.txtIntentionArea.getNumberValue()==null){
			MsgBox.showWarning("�����������Ϊ�գ�");
			this.abort();
		}
		if(this.txtAge.getNumberValue()==null){
			MsgBox.showWarning("�ͻ����䲻��Ϊ�գ�");
			this.abort();
		}
		if(this.txtareaDescription.getText()==null ){
			MsgBox.showWarning("���˼�鲻��Ϊ�գ�");
			this.abort();
		}else if (this.txtareaDescription.getText().trim().length()<=0 ){
			MsgBox.showWarning("���˼�鲻��Ϊ�գ�");
			this.abort();
		}
		
		
		verifyInputNotNull(txtName, "��������Ϊ��");
		verifyInputNotNull(txtPhone, "�绰���벻��Ϊ��");
		verifyF7InputNotNull(f7salesman, "���۹��ʲ���Ϊ��");
		verifyLength(txtareaDescription, 255, "��ע");
		
		/***
		 * �˴���������ж���䣬��5����ÿ�����涼��RPC����
		 * ��Ҫ�Ż�����
		 * 
		 * �˴��Ż�����ʱ��CustomerName,Phone�ϲ������������ж�
		 * TODO ������������
		 */
		//�˴���editData����Ӧ����ֵ�ģ�verifyInputӦ������storeField֮��,��������֮ǰ
		boolean isSingle = setting.getCheckedRadioButton().equals("kDRadioSingle");
		editData.setName(txtName.getText());
		editData.setPhone(txtPhone.getText());
		Map rMap = ((IFDCCustomer)this.getBizInterface()).verifySave(editData,isSingle);
		
//		if((rMap.containsKey("dupNameMsg")&&setting.getNameRepeat()==0)&&
//				(rMap.containsKey("dupPhoneMsg")&&setting.getPhoneRepeat()==0)){
//			//������ǿ�Ʋ����棬�ϲ���ʾ
//			FDCMsgBox.showConfirm3a(this, "���ܱ��棬��鿴��ϸ��Ϣ", (String)rMap.get("dupNameMsg")+"\n"+(String)rMap.get("dupPhoneMsg"));
//			abort();//ǿ�Ʋ�����
//		}
//		//����û�ѡ����ظ�����
//		
//		
//		if(rMap.containsKey("dupNameMsg")){
//				if(setting.getNameRepeat()==2)
//				{
//					//����ʾ��ֱ�ӱ���
//				}else if(setting.getNameRepeat()==0){
//					FDCMsgBox.showConfirm3a(this, "���ܱ��棬��鿴��ϸ��Ϣ", (String)rMap.get("dupNameMsg"));
//					abort();//ǿ�Ʋ�����
//				}else if(setting.getNameRepeat()==1){
//					//������ʾ���ͻ�ѡ�񱣴�;
//					if(FDCMsgBox.YES!=FDCMsgBox.showConfirm3a(this, "�����ظ�����Ϣ����鿴��ϸ��Ϣ��ȷ���Ƿ񱣴棿", (String)rMap.get("dupNameMsg"))){
//						abort();
//					}
//				}else{
//					abort();
//				}
//			}
//			
//			if(rMap.containsKey("dupPhoneMsg")){
//				if(setting.getPhoneRepeat()==2)
//				{
//					//����ʾ��ֱ�ӱ���
//				}else if(setting.getPhoneRepeat()==0){
//					FDCMsgBox.showConfirm3a(this, "���ܱ��棬��鿴��ϸ��Ϣ", (String)rMap.get("dupPhoneMsg"));
//					abort();//ǿ�Ʋ�����
//				}else if(setting.getPhoneRepeat()==1){
//					//������ʾ���ͻ�ѡ�񱣴�;
//					if(FDCMsgBox.YES!=FDCMsgBox.showConfirm3a(this, "�����ظ�����Ϣ����鿴��ϸ��Ϣ��ȷ���Ƿ񱣴棿", (String)rMap.get("dupPhoneMsg"))){
//						abort();
//					}
//				}else{
//					abort();
//				}
//			}
//			if(rMap.containsKey("dupNameAndPhoneMsg")){
//				if(setting.getNameAndPhoneRepeat() == 2){
//					
//				}else if(setting.getNameAndPhoneRepeat()==0){
//					FDCMsgBox.showConfirm3a(this, "���ܱ��棬��鿴��ϸ��Ϣ", (String)rMap.get("dupNameAndPhoneMsg"));
//					abort();//ǿ�Ʋ�����
//				}else if(setting.getNameAndPhoneRepeat()==1){
//					//������ʾ���ͻ�ѡ�񱣴�;
//					if(FDCMsgBox.YES!=FDCMsgBox.showConfirm3a(this, "�����ظ�����Ϣ����鿴��ϸ��Ϣ��ȷ���Ƿ񱣴棿", (String)rMap.get("dupNameAndPhoneMsg"))){
//						abort();
//					}
//				}else{
//					abort();
//				}
//			}
//			
////		}
		
		
	   if(FDCCustomerHelper.verifyPhoneAndName(rMap, this)){
		   abort();
	   }	
		
		
//		if(this.getOprtState().equals(OprtState.EDIT) && this.txtName.getText().equals(info.getName()))
//		{
//			
//		}else
//		{
//			verifyCustomerName();
//		}
		verifyCertificateNumber();
//		verifyPhone();
		verifyLinkman();
		verifyShareSeller();
	}
	
	
	
	private void verifyF7RoomModelType(KDBizPromptBox f7box, String msg) {
		Object obj = f7box.getValue();
		if(obj == null){
			MsgBox.showWarning(this,msg);
			SysUtil.abort();
		}
	}

	/**
	 * ��ʼ��֤�����ƵĹ�������
	 * @param isPerson
	 */
	private void initNewCertificateName(boolean isPerson) {
		
		this.boxCertificateName.setEditable(false);
		
		EntityViewInfo evi = new EntityViewInfo();
		FilterInfo filterInfo = new FilterInfo();
		if(isPerson){
			filterInfo.getFilterItems().add(new FilterItemInfo("customerType", CustomerTypeEnum.PERSONALCUSTOMER_VALUE, CompareType.EQUALS));
		}else{
			filterInfo.getFilterItems().add(new FilterItemInfo("customerType", CustomerTypeEnum.ENTERPRICECUSTOMER_VALUE, CompareType.EQUALS));
		}
		evi.setFilter(filterInfo);
		this.boxCertificateName.setEntityViewInfo(evi);
		
	}

	private void verifyCertificateNumber() throws BOSException {
		//CertifacateNameEnum nameEnum = (CertifacateNameEnum) this.boxCertificateName.getSelectedItem();
		//update by renliang
		CertificateInfo  cetifiInfo = null;
		if(this.boxCertificateName.getValue()!=null){
			cetifiInfo = (CertificateInfo) this.boxCertificateName.getValue();
		}
		
		/*if(cetifiInfo==null){
			FDCMsgBox.showWarning(this,"����ѡ��֤�����ƣ�");
			SysUtil.abort();
		}*/
		
		String certificateNum = this.txtCertificateNumber.getText().trim();
		if(FDCHelper.isEmpty(certificateNum)){
			return;
		}
		
		//��������֤������λ����Ϊ15��18ʱ��������ʾ
		/*if(CertifacateNameEnum.IDCard.equals(nameEnum)){
			if(certificateNum.length() != 18  &&  certificateNum.length() != 15){
				int result = MsgBox.showConfirm2New(null, "�ͻ����֤����λ������ȷ,�Ƿ������");
				if(result != MsgBox.YES){
					this.abort();
				}
			}
		}*/
		if(cetifiInfo.getId()!=null && cetifiInfo.getNumber().equals("001")){
			if(certificateNum.length() != 18  &&  certificateNum.length() != 15){
				int result = MsgBox.showConfirm2New(null, "�ͻ����֤����λ������ȷ,�Ƿ������");
				if(result != MsgBox.YES){
					this.abort();
				}
			}
		
	
		
		//������޸Ĳ���,֤��δ�����˱仯ʱ���ý�����֤
		/*if(STATUS_EDIT.equals(this.getOprtState())){
			if(this.editData.getCertificateName()!=null && this.editData.getCertificateNumber()!=null)	{
				if(nameEnum.equals(this.editData.getCertificateName())  &&  certificateNum.equals(this.editData.getCertificateNumber())){
					return;
				}	
			}
		}*/

		//������޸Ĳ���,֤��δ�����˱仯ʱ���ý�����֤
		if(STATUS_EDIT.equals(this.getOprtState())){
			if(this.editData.getCertificateName()!=null && this.editData.getCertificateNumber()!=null)	{
				/*if(nameEnum.equals(this.editData.getCertificateName())  &&  certificateNum.equals(this.editData.getCertificateNumber())){
					return;
				}*/
				if(cetifiInfo.getName()!=null && cetifiInfo.getName().toString().equals(this.editData.getCertificateName())  &&  certificateNum.equals(this.editData.getCertificateNumber())){
					return;
				}
			}
		}
	
		String id = null;
		if(this.editData.getId() != null){
			id = this.editData.getId().toString();
		}
		
		/*if(!FDCCustomerHelper.verifyCertificateNumber(nameEnum, certificateNum, id)){
			this.abort();;
		}*/
		if(!FDCCustomerHelper.verifyCertificateNumber(cetifiInfo.getId().toString(), certificateNum, id)){
			this.abort();
		}
	 }
  }
	
	private void verifyCustomerName() throws BOSException {
		String customerName = this.txtName.getText();
//		if(!FDCCustomerHelper.verifyCustomerName(customerName,setting)){
			this.abort();;
//		}
	}
	
	private void verifyF7InputNotNull(KDBizPromptBox f7box, String msg) {
		Object obj = f7box.getValue();
		if(obj == null){
			MsgBox.showWarning(this,msg);
			SysUtil.abort();
		}
	}
	
	
	
	
	/**
	 * ��֤��ϵ�˵�¼����������ϵ�ˣ���ϵΪ��¼
	 * */
	private void verifyLinkman() {
		for (int i = 0; i < tblLinkman.getRowCount(); i++) {
			IRow row = tblLinkman.getRow(i);
			//LinkmanEntryInfo entry = (LinkmanEntryInfo) row.getUserObject();						
			
			Object obj = row.getCell("customerName").getValue();
			Object obj1 = row.getCell("relation").getValue();			
			
			if(obj != null  &&  this.editData.getId() != null){
				if(this.editData.getId().equals(((FDCCustomerInfo)obj).getId())){
					MsgBox.showWarning("��ϵ�˲���ѡ���Լ�.");
					this.abort();
				}
			}
			
//			if(obj == null  &&  obj1 != null){
//				this.tabNew.setSelectedIndex(0);
//				this.kDTabbedPane1.setSelectedIndex(0);
//				MsgBox.showWarning("��ϵ���еĿͻ�����Ϊ��¼��,��¼��ͻ�����.");
//				this.abort();
//			}
			
//			if(obj == null  &&  !linkmanFlag){
//				this.tabNew.setSelectedIndex(0);
//				this.kDTabbedPane1.setSelectedIndex(0);
//				int result = MsgBox.showConfirm2("δ¼����ϵ��,ȷ������Ը���ϵ�˽��б���,ȡ���жϱ������");
//				if(result != MsgBox.YES){
//					SysUtil.abort();
//				}
//				linkmanFlag = true;
//				continue;
//			}
			
			
			//��ϵ�˲�Ϊ�գ�����ϵΪ�գ���ʾ��ϵΪ��¼��
			if(obj != null  &&  obj1 == null){
				this.tabNew.setSelectedIndex(0);
				this.kDTabbedPane1.setSelectedIndex(0);
				MsgBox.showWarning("��ϵ���еĹ�ϵΪ��¼��,��¼���ϵ.");
				SysUtil.abort();
			}
		}
	}
	
	/**
	 * ��֤�������۹��ʵ�¼�����������۹���Ϊ��¼
	 * */
	private void verifyShareSeller() {
		boolean shareSellerFlag = false;//��ϵ��ΪNULLʱ���Ƿ������ϵ�˽��пͻ�����
		for (int i = 0; i < tblShareSeller.getRowCount(); i++) {
			IRow row = tblShareSeller.getRow(i);
			
			Object obj = row.getCell("shareObject").getValue();
			if(obj == null  &&  !shareSellerFlag){
				this.tabNew.setSelectedIndex(0);
				this.kDTabbedPane1.setSelectedIndex(0);
				MsgBox.showInfo("δ¼�����۹��ʲ��ܱ���!");
				SysUtil.abort();
				shareSellerFlag = true;
				continue;
			}
		}
	}
	
	public Date simpleDate(Date date) throws ParseException{
		Date d = (isNotNull(date)) ? date : new Date();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");;
		return format .parse(format.format(d));
	}
	
	public boolean isNotNull(Object o){
		return (!isNull(o));
	}
	
	public static boolean isNull(Object o){
		return (o == null);
	}
	
	private void verifyLength(KDTextArea txtareaDescription, int length, String msg) {
		String str = txtareaDescription.getText().trim();
		if(!str.equals("") && str.length()>length){
			MsgBox.showWarning(msg + "��������ַ�����" + length);
			SysUtil.abort();
		}
		
	}
	
	private void verifyInputNotNull(KDTextField txt, String msg) {
		if(!FDCCustomerHelper.verifyInputNotNull(txt, msg)){
			this.abort();
		}
	}
	
	public void actionSubmitOption_actionPerformed(ActionEvent e) throws Exception {
		super.actionSubmitOption_actionPerformed(e);
	}
	
	public void actionSave_actionPerformed(ActionEvent e) throws Exception {
		if("".equals(this.f7WorkArea.getValue())&& null==this.f7WorkArea.getValue())
		{
			return;
		}
		else
//			this.f7WorkArea.setValue(this.editData.getWorkArea().getName());
		//RoomPriceBillInfo roomInfo = RoomPriceBillFactory.getRemoteInstance().getRoomPriceBillInfo(pk,sic);
	//	FDCCustomerFactory.getRemoteInstance().updatePartial(model, selector);
		super.actionSave_actionPerformed(e);
		actionInsider.setEnabled(true);
	}
	
	/**
	 * ����������ѡ���cellEditor
	 * */
	private KDTDefaultCellEditor createDPCellEditor(boolean editable) {
		KDDatePicker datePicker = new KDDatePicker();
		datePicker.setEditable(editable);
		KDTDefaultCellEditor dpEditor = new KDTDefaultCellEditor(datePicker);
		return dpEditor;
	}
	
	/**
	 * �������ı��༭���cellEditor
	 * @param length
	 * 			�ı���������볤��
	 * @param editable
	 * 			�Ƿ�ɱ༭
	 * */
	private KDTDefaultCellEditor createTxtCellEditor(int length, boolean editable) {
		KDTextField textField = new KDTextField();
		textField.setMaxLength(length);
		textField.setEditable(editable);
		KDTDefaultCellEditor txtEditor = new KDTDefaultCellEditor(textField);
		return txtEditor;
	}
	
	
	protected void tblShareSeller_editStopped(KDTEditEvent e) throws Exception {
		super.tblShareSeller_editStopped(e);
		int colIndex = e.getColIndex();
		
		if(colIndex == 0){
			int rowIndex = e.getRowIndex();
			IRow row = tblShareSeller.getRow(rowIndex);
			Object obj = row.getCell("shareSellerName").getUserObject();
			if(obj != null){
				UserInfo info = (UserInfo) obj;
				row.getCell("shareSellerNumber").setValue(info.getNumber());
				//row.getCell("shareDate").setValue(info.getCreateTime());
				//row.getCell("description").setValue(info.getDescription());
			}
			else{
				row.getCell("shareSellerNumber").setValue(null);
				//row.getCell("shareDate").setValue(null);
				//row.getCell("description").setValue(null);
			}
		}
	}
	
	/**
	 * ��Ԫ���¼��������ѡ����굥������ѡ������
	 */
	protected void tblShareSeller_activeCellChanged(KDTActiveCellEvent e) throws Exception {
		super.tblShareSeller_activeCellChanged(e);
		TenancyClientHelper.tableName_activeCellChanged(e,this.tblShareSeller);
	}
	
	private void verifyPhone() throws BOSException, Exception {
		if(!oprtState.equals(STATUS_ADDNEW)){
			return;
		}
		String phone = txtPhone.getText();
		if(!FDCCustomerHelper.verifyPhone2(phone,setting)){
			this.abort();
		}
	}
	
	
	
	protected void tblLinkman_editStopped(KDTEditEvent e) throws Exception {
		super.tblLinkman_editStopped(e);
		int colIndex = e.getColIndex();
		
		if(colIndex == 1){
			int rowIndex = e.getRowIndex();
			IRow row = tblLinkman.getRow(rowIndex);
			Object obj = row.getCell("customerName").getValue();
			if(obj != null){
				tblLinkman.getColumn("relation").setRequired(true);
				FDCCustomerInfo info = (FDCCustomerInfo) obj;
				row.getCell("customerNumber").setValue(info.getNumber());
				row.getCell("name").setValue(info.getName());
				row.getCell("customerSex").setValue(info.getSex());
				row.getCell("phone").setValue(info.getPhone());
			}
			else{
				row.getCell("customerNumber").setValue(null);
				row.getCell("customerSex").setValue(null);
				row.getCell("phone").setValue(null);
				row.getCell("name").setValue(null);
				tblLinkman.getColumn("relation").setRequired(false);
			}
		}
	}
	
	protected void tblSaleCommerce_tableClicked(KDTMouseEvent e) throws Exception {
		super.tblSaleCommerce_tableClicked(e);
		
		if (e.getButton() == 1 && e.getClickCount() == 2) {
			if(e.getType()!=1) return;
			CommerceChanceInfo trackInfo = (CommerceChanceInfo)this.tblSaleCommerce.getRow(e.getRowIndex()).getUserObject();
			if (trackInfo == null) {
				return;
			}
			
			CommerceHelper.openTheUIWindow(this,CommerceChanceEditUI.class.getName(),trackInfo.getId().toString());
		}		
	}
	
	protected void tblSaleRecord_tableClicked(KDTMouseEvent e) throws Exception {
		super.tblSaleRecord_tableClicked(e);
		
		if (e.getButton() == 1 && e.getClickCount() == 2) {
			if(e.getType()!=1) return;
			
			PurchaseInfo trackInfo = (PurchaseInfo)this.tblSaleRecord.getRow(e.getRowIndex()).getUserObject();
			if (trackInfo == null) {
				return;
			}
			
			CommerceHelper.openTheUIWindow(this,PurchaseEditUI.class.getName(),trackInfo.getId().toString());
		}	
	}
	
	protected void tblTenancyCommerce_tableClicked(KDTMouseEvent e) throws Exception {
		super.tblTenancyCommerce_tableClicked(e);
		
		if (e.getButton() == 1 && e.getClickCount() == 2) {
			if(e.getType()!=1) return;
			
			CommerceChanceInfo trackInfo = (CommerceChanceInfo)this.tblTenancyCommerce.getRow(e.getRowIndex()).getUserObject();
			if (trackInfo == null) {
				return;
			}
			
			CommerceHelper.openTheUIWindow(this,CommerceChanceEditUI.class.getName(),trackInfo.getId().toString());
		}	
	}
	
	protected void tblTenancyRecord_tableClicked(KDTMouseEvent e) throws Exception {
		super.tblTenancyRecord_tableClicked(e);
		
		if (e.getButton() == 1 && e.getClickCount() == 2) {
			if(e.getType()!=1) return;
			
			TenancyBillInfo trackInfo = (TenancyBillInfo)this.tblTenancyRecord.getRow(e.getRowIndex()).getUserObject();
			if (trackInfo == null) {
				return;
			}
			
			CommerceHelper.openTheUIWindow(this,TenancyBillEditUI.class.getName(),trackInfo.getId().toString());
		}	
	}
	
	protected void tblTrackRecord_tableClicked(KDTMouseEvent e) throws Exception {
		super.tblTrackRecord_tableClicked(e);
		if (e.getButton() == 1 && e.getClickCount() == 2) {
			if(e.getType()!=1) return;
			
			TrackRecordInfo trackInfo = (TrackRecordInfo)this.tblTrackRecord.getRow(e.getRowIndex()).getUserObject();
			if (trackInfo == null) {
				return;
			}
			
			CommerceHelper.openTheUIWindow(this,TrackRecordEditUI.class.getName(),trackInfo.getId().toString());
		}		
	}
	
	public void actionCustomerAdapter_actionPerformed(ActionEvent e) throws Exception {
		String  fdcCustomerID = this.editData.getId().toString();
		CustomerAdapterUI.showEditUI(this,fdcCustomerID);
		findSaleman();
	}
	
	public void actionCustomerShare_actionPerformed(ActionEvent e) throws Exception {
		String  fdcCustomerID = this.editData.getId().toString();
		CustomerShareUI.showEditUI(this,fdcCustomerID);
		findShareSeller();
	}
	
	public void actionCustomerCancelShare_actionPerformed(ActionEvent e) throws Exception {
		String  fdcCustomerID = this.editData.getId().toString();
		CustomerCancelShareUI.showEditUI(this,fdcCustomerID);
		findShareSeller();
	}
	
	//�����۹��ʱ仯ʱ,�ı�����������Ŀ�Ĺ�������
	public void f7salesman_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e){
		UserInfo saleMan = (UserInfo)this.f7salesman.getValue();
		//if(saleMan!=)
		Set proIds = null;
		try {
			proIds = CommerceHelper.getPermitProjectIds(saleMan);
			proIds.add("null");	
		} catch (BOSException e1) {
			
			e1.printStackTrace();
		}
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("id",proIds,CompareType.INCLUDE));
		view.setFilter(filter);
		this.f7Project.setEntityViewInfo(view);
	}
	
	
	
	
	
	
	
	//�̻��ܿ���  �ͻ��������� 
	public KDTabbedPane getKDTabbedPane() {
		return this.kDTabbedPane1;
	}	
	public KDTable getTblTrackRecord() {
		return this.tblTrackRecord;
	}	
	public KDTable getTblShareSeller() {
		return this.tblShareSeller;
	} 	
	public KDTable getTblAdapterLog() {
		return this.tblAdapterLog;
	}	
//	public KDTextField getCustomerName() {
//		return this.txtName;	
//	}	
	public KDBizPromptBox getSaleManPrmt() {
		return this.f7salesman;	
	}
	public KDBizPromptBox getSellProject() {
		return this.f7Project;	
	}
//	public KDTextField getCustomerPhone() {
//		return this.txtPhone;	
//	}	
	public FDCCustomerInfo getCustomerData() {
		return this.editData;	
	}	
	
	public void initOldData() {
		this.initOldData(this.editData);
	}
	
	
	
	//��ǰ��¼�û���ָ���ͻ��Ƿ����޸ĵ�Ȩ��
	public static boolean hasUpdatePermission(FDCCustomerInfo customerInfo) throws EASBizException, BOSException, UuidException {
		if(customerInfo==null) return false;		
		return hasUpdatePermission(customerInfo.getId().toString());
	}
	
	public static boolean hasUpdatePermission(String customerId) throws EASBizException, BOSException, UuidException {
		if(customerId==null) return false;
		
		UserInfo currUserInfo = SysContext.getSysContext().getCurrentUserInfo();
		if(FDCCustomerFactory.getRemoteInstance().exists("where id='"+customerId+"' and salesman.id='"+currUserInfo.getId().toString()+"' "))
			return true;  //��ǰ��¼��Ա���Լ��Ŀͻ�
		
		boolean hasPermission = false;
		//��ǰ��֯�£���Ϊָ���ͻ������Ĺ��ʵĸ����ˣ���ӵ��ҵ�����Ȩ��
		hasPermission = SHEHelper.hasOperatorPermission(customerId);
		if(hasPermission) return true;
			
		//��  �Ըÿͻ������޸�Ȩ�Ĺ������ 
		hasPermission = ShareSellerFactory.getRemoteInstance().exists("where head.id='"+ customerId +"'  and seller.id='"+ currUserInfo.getId().toString() +"'" +
				"and isUpdate=1 and shareModel = '"+ShareModelEnum.SHAREPERSON_VALUE+"' ");
		if(hasPermission) return true;

		//���޸�Ȩ�޵Ĺ���Ԫ
		String filterMarketSql = "select FMarketingUnitID from T_SHE_ShareSeller where FHeadID='"+customerId+"' and FisUpdate=1 and FShareModel='shareMarket' ";
		if(MarketingUnitMemberFactory.getRemoteInstance().exists("where head.id in ("+filterMarketSql+") and member.id='"+currUserInfo.getId().toString()+"'"))
			return true;
		
		//���޸�Ȩ�޵Ĺ�����֯		
		String filterOrgSql = "select FOrgUnitID from T_SHE_ShareSeller where FHeadID='"+customerId+"' and FisUpdate=1 and FShareModel='shareOrgUnit' ";		
		if(MarketingUnitMemberFactory.getRemoteInstance().exists("where head.orgUnit.id in ("+filterOrgSql+") and member.id='"+currUserInfo.getId().toString()+"'"))
			return true;
		
		//��   �Ըÿͻ������޸�Ȩ�Ĺ������ �� ���������в���Ȩ��
		SaleOrgUnitInfo saleOrg = SysContext.getSysContext().getCurrentSaleUnit();
		String filterSaleManSql = "select FSellerID from T_SHE_ShareSeller where FHeadID='"+customerId+"' and FisUpdate=1 and FShareModel='sharePerson' ";
		MarketUnitPlatCollection muPlatColl = MarketUnitPlatFactory.getRemoteInstance().getMarketUnitPlatCollection("select dutyPerson.id,marketUnit.id " +
				"where orgUnit.id='"+ saleOrg.getId().toString() +"' and dutyPerson.id='"+currUserInfo.getId().toString()+"' and member.id in ("+ filterSaleManSql +") ");
		for(int i=0;i<muPlatColl.size();i++) {
			MarketUnitPlatInfo muPlatInfo = muPlatColl.get(i);
			boolean existFlag = MarketingUnitMemberFactory.getRemoteInstance().exists("where head.id='"+muPlatInfo.getMarketUnit().getId().toString()+"' " +
					"and isDuty=1 and isOperation=1 and member.id='"+muPlatInfo.getDutyPerson().getId().toString()+"'");
			if(existFlag) return true;
		}
		
		return false;
	}
	
	
	////////////////////
	/**
	 * ��ʼ��  ��ҵҳǩ  ������Ϣ
	 */
	private void initPropertyHouse(){
		
		FDCCustomerInfo info = this.editData;//  new FDCCustomerInfo();
		if(info==null) 
			return;
		JoinVoucherCollection moveInColl = getJoinInList(info);
		CustomerMoveVoucherCollection moveOutColl = new CustomerMoveVoucherCollection();//getCustomerMoveOutList(info);
		
		fetchHouseTable(moveInColl,moveOutColl);
		
		
		registerListener();
		
//		if(moveInColl!=null && moveInColl.get(0)!=null){
//			initPropertyArrearage(info,moveInColl.get(0).getRoom());
//		}else if(moveOutColl!=null && moveOutColl.get(0)!=null){
//			initPropertyArrearage(info,moveOutColl.get(0).getRoom());
//		}
//		if(moveInColl!=null && moveInColl.get(0)!=null){
//			initPropertyDeposit(info,moveInColl.get(0).getRoom());
//		}else if(moveOutColl!=null && moveOutColl.get(0)!=null){
//			initPropertyDeposit(info,moveOutColl.get(0).getRoom());
//		}
		
	}
	
	/**
	 * ��ʼ��  ��ҵҳǩ   Ƿ���¼
	 * @param info
	 * @param room
	 */
	private void  initPropertyArrearage(FDCCustomerInfo info,RoomInfo room){  //��ҵ����
		PPMGeneralARCollection generalcoll = getPPMGeneralAR(info,room.getId().toString());
		PPMMeasureARCollection measurecoll = getMeasureAR(info,room.getId().toString());
		PPMTemporaryCollection temporarycoll =  getTemporaryAR(info,room.getId().toString());
		
		filterPPMARData(generalcoll,measurecoll,temporarycoll);
		
		fetchArrearageTable(generalcoll,measurecoll,temporarycoll);
		
		
	}
	
	/**
	 * ��ʼ�� ��ҵҳǩ   Ԥ�տ��¼
	 * @param info
	 * @param room
	 */
	private void  initPropertyDeposit(FDCCustomerInfo info,RoomInfo room) {
		
		FDCReceiveBillEntryCollection receivingentryColl = getPPMDeposit(info,room.getId().toString());
		
		RoomInfo roomInfo =room;
		SellProjectInfo sellProjectInfo = new SellProjectInfo();
		
		try
		{
			if(roomInfo.getBuilding()==null){
				roomInfo = 	RoomFactory.getRemoteInstance().getRoomInfo(new ObjectUuidPK(room.getId()));
				if(roomInfo==null)
				{
					return ;
				}
			}
			
			BuildingInfo  building = (BuildingInfo) com.kingdee.bos.ui.face.UIRuleUtil.getObject(com.kingdee.bos.ui.face.UIRuleUtil.getProperty(roomInfo,"building"));
			if(building.getSellProject()!=null){
				sellProjectInfo =(SellProjectInfo) com.kingdee.bos.ui.face.UIRuleUtil.getObject(com.kingdee.bos.ui.face.UIRuleUtil.getProperty(building,"sellproject"));
			}
		} catch (BOSException e)
		{
			e.printStackTrace();
		} catch (EASBizException e)
		{
			e.printStackTrace();
		}
		
		
		fetchDepositTable(receivingentryColl,roomInfo,sellProjectInfo);
	} 
	
	/**
	 * ��ʼ���ͷ� ҳǩ  ��Լ����
	 */
	private void initCustomerServiceSpecial(){
		FDCCustomerInfo info = this.editData;//  new FDCCustomerInfo();
		if(info==null) 
			return;
		CmsTimeConferBillCollection cmsTimeConferColl = getCMSTimeConferList(info);
		
		fetchCustomerServiceSpecialTable(cmsTimeConferColl);
		
		
		//registerListener();
		
		//if(cmsTimeConferColl!=null && cmsTimeConferColl.get(0)!=null){
			//CmsTimeConferBillInfo  tempInfo = cmsTimeConferColl.get(0);
			initCustomerServiceRecord(info,new RoomInfo());
			initCustomerServiceComplaint(info,new RoomInfo());
		//}
		
		
	}
	
	/**
	 * ��ʼ���ͷ� ҳǩ  �����¼
	 * @param info
	 * @param room
	 */
	private void  initCustomerServiceRecord(FDCCustomerInfo info,RoomInfo room){
		ServiceProcessBillCollection serviceProcessColl = getCustomerServiceRecordList(info,"");
		fetchCustomerServiceRecordTable(serviceProcessColl);
	}
	/**
	 * ��ʼ���ͷ� ҳǩ  Ͷ�߼�¼
	 * @param info
	 * @param room
	 */
	private void  initCustomerServiceComplaint(FDCCustomerInfo info,RoomInfo room) {
		ServiceProcessBillCollection serviceProcessColl = getCustomerServiceComplaintList(info,"");
		fetchCustomerServiceComplaintTable(serviceProcessColl);
	}
	
	
	
	/**
	 * ���˳���Ӧ�� ����Ӧ�ռ���ʱӦ���У�����Ӧ�տ���շ���������Ӧ�ս��-������-���ս��>0  ����
	 * @param generalcoll
	 * @param measurecoll
	 * @param temporarycoll
	 */
	private void filterPPMARData(PPMGeneralARCollection generalcoll,
			PPMMeasureARCollection measurecoll,
			PPMTemporaryCollection temporarycoll)
	{
		PPMGeneralARCollection temp_generalcoll = new PPMGeneralARCollection();
		PPMMeasureARCollection temp_measurecoll =  new PPMMeasureARCollection();
		PPMTemporaryCollection  temp_temporarycoll =  new PPMTemporaryCollection();
		if(generalcoll!=null){
			int len = generalcoll.size();
			for(int i=0;i<len;i++){
				PPMGeneralARInfo info =generalcoll.get(i);
				int desvalue =checkarrearage(info).compareTo(ZERO);
				if(desvalue>0){
					temp_generalcoll.add(info);
				}
			}
			generalcoll.clear();
			generalcoll.addCollection(temp_generalcoll);
		}
		
		if(measurecoll!=null){
			int len = measurecoll.size();
			for(int i=0;i<len;i++){
				PPMMeasureARInfo info =measurecoll.get(i);
				int desvalue = checkarrearage(info).compareTo(ZERO);
				if(desvalue>0){
					temp_measurecoll.add(info);
				}
			}
			measurecoll.clear();
			measurecoll.addCollection(temp_measurecoll);
		}
		
		if(temporarycoll!=null){
			int len = temporarycoll.size();
			for(int i=0;i<len;i++){
				PPMTemporaryInfo info =temporarycoll.get(i);
				int desvalue = checkarrearage(info).compareTo(ZERO);
				if(desvalue>0){
					temp_temporarycoll.add(info);
				}
			}
			temporarycoll.clear();
			temporarycoll.addCollection(temp_temporarycoll);
		}
		
		
	}
	
	/**
	 * ��ȡ �ͻ���ס�Ǽ�
	 * @param info
	 * @return
	 */
	
	private JoinVoucherCollection getJoinInList(FDCCustomerInfo info){
		
		EntityViewInfo entityViewInfo = new EntityViewInfo();
		
    	FilterInfo filterInfo = new FilterInfo();
		filterInfo.getFilterItems().add(new  FilterItemInfo("customer.id", info.getId().toString()));	
		filterInfo.getFilterItems().add(new  FilterItemInfo("state", FDCBillStateEnum.AUDITTED_VALUE));	
		entityViewInfo.setFilter(filterInfo);
		SorterItemCollection sorterColl = new SorterItemCollection();
		SorterItemInfo roomsort = new SorterItemInfo("room");
		roomsort.setSortType(SortType.DESCEND);
		sorterColl.add(roomsort);
		sorterColl.add(new SorterItemInfo("joinStatus"));
		sorterColl.add(new SorterItemInfo("joinType"));
		entityViewInfo.setSorter(sorterColl);
		JoinVoucherCollection incoll =null;
		try
		{
			 incoll = 	JoinVoucherFactory.getRemoteInstance().getJoinVoucherCollection(entityViewInfo);
		} catch (BOSException e)
		{
			e.printStackTrace();
		}
		
		
		return incoll;
	}
	
	
	/**
	 * ��ȡ �ͻ�Ǩ���Ǽ�
	 * �ѷ�������
	 * @param info
	 * @return
	 */
	private CustomerMoveVoucherCollection getCustomerMoveOutList(FDCCustomerInfo info){
		
		EntityViewInfo entityViewInfo = new EntityViewInfo();
		
    	FilterInfo filterInfo = new FilterInfo();
		filterInfo.getFilterItems().add(new  FilterItemInfo("customer.id", info.getId().toString()));	
		filterInfo.getFilterItems().add(new  FilterItemInfo("state", FDCBillStateEnum.AUDITTED_VALUE));	
		entityViewInfo.setFilter(filterInfo);
		CustomerMoveVoucherCollection outcoll =null;
		try
		{
			outcoll = 	CustomerMoveVoucherFactory.getRemoteInstance().getCustomerMoveVoucherCollection(entityViewInfo);
		} catch (BOSException e)
		{
			e.printStackTrace();
		}
		return outcoll;
	}
	
	
	/**
	 * ��ȡ����Ӧ��
	 * @param info
	 * @param roomId
	 * @return
	 */
	private PPMGeneralARCollection  getPPMGeneralAR(FDCCustomerInfo info,String roomId){
		EntityViewInfo entityViewInfo = new EntityViewInfo();
		
    	FilterInfo filterInfo = new FilterInfo();
		filterInfo.getFilterItems().add(new  FilterItemInfo("customer.id", info.getId().toString()));	
		filterInfo.getFilterItems().add(new  FilterItemInfo("room.id", roomId));	
		entityViewInfo.setFilter(filterInfo);
		PPMGeneralARCollection incoll =null;
		try
		{
			 incoll = 	PPMGeneralARFactory.getRemoteInstance().getPPMGeneralARCollection(entityViewInfo);
		} catch (BOSException e)
		{
			e.printStackTrace();
		}
		return incoll;
	}
	
	
	/**
	 * ����Ӧ��
	 * @param info
	 * @param roomId
	 * @return
	 */
	private PPMMeasureARCollection  getMeasureAR(FDCCustomerInfo info,String roomId){
		EntityViewInfo entityViewInfo = new EntityViewInfo();
		
		FilterInfo filterInfo = new FilterInfo();
		filterInfo.getFilterItems().add(new  FilterItemInfo("customer.id", info.getId().toString()));	
		filterInfo.getFilterItems().add(new  FilterItemInfo("room.id", roomId));	
		entityViewInfo.setFilter(filterInfo);
		PPMMeasureARCollection incoll =null;
		try
		{
			incoll = 	PPMMeasureARFactory.getRemoteInstance().getPPMMeasureARCollection(entityViewInfo);
		} catch (BOSException e)
		{
			e.printStackTrace();
		}
		return incoll;
	}
	
	
	/**
	 * ��ʱӦ��
	 * @param info
	 * @param roomId
	 * @return
	 */
	private PPMTemporaryCollection  getTemporaryAR(FDCCustomerInfo info,String roomId){
		EntityViewInfo entityViewInfo = new EntityViewInfo();
		FilterInfo filterInfo = new FilterInfo();
		filterInfo.getFilterItems().add(new  FilterItemInfo("customer.id", info.getId().toString()));	
		filterInfo.getFilterItems().add(new  FilterItemInfo("room.id", roomId));	
		entityViewInfo.setFilter(filterInfo);
		PPMTemporaryCollection incoll =null;
		try
		{
			incoll = 	PPMTemporaryFactory.getRemoteInstance().getPPMTemporaryCollection(entityViewInfo);
		} catch (BOSException e)
		{
			e.printStackTrace();
		}
		return incoll;
	}
	
	
	/** 
	 *��ȡ �տ��¼  �տ���ϸ
	 * @param info
	 * @param roomId
	 * @return
	 */
	private  FDCReceiveBillEntryCollection  getPPMDeposit(FDCCustomerInfo info,String roomId){
		///���  ���ز��ͻ������Ŀͻ�����
		
		String sysCustomerId =null;
		try
		{
			CustomerInfo tempObj =  (CustomerInfo)UIRuleUtil.getObject(UIRuleUtil.getProperty(info, "sysCustomer"));
			if(tempObj!=null ){
				sysCustomerId = tempObj.getId().toString();
			}
		} catch (DataAccessException e2)
		{
			// TODO Auto-generated catch block
			e2.printStackTrace();
		} catch (BOSException e2)
		{
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		if(sysCustomerId==null || sysCustomerId.length()<1){
			return new FDCReceiveBillEntryCollection();
		}
		/////
		/// �ͻ���¼������ ��� �����ÿͻ����տ����  ///
		EntityViewInfo ceView = new EntityViewInfo();
		FilterInfo cefilterInfo = new FilterInfo();
		
		cefilterInfo.getFilterItems().add(new  FilterItemInfo("customer.id", sysCustomerId));	
		ceView.setFilter(cefilterInfo);
		
		CustomerEntryCollection cecoll = null;
		try
		{
			cecoll = CustomerEntryFactory.getRemoteInstance().getCustomerEntryCollection(ceView);
		} catch (BOSException e)
		{
			e.printStackTrace();
		}
		if(cecoll==null ||  cecoll.size()<1){
			return  new FDCReceiveBillEntryCollection();
		}
		
		////////////////////////////////////////////////////////////////////////////////
		///���� ����ָ�㷿����տ
		Set receiveBillSet = new HashSet();
		
		CustomerEntryInfo tempInfo=null;
		for(int i=0;i< cecoll.size();i++){
			tempInfo =  (CustomerEntryInfo)cecoll.get(i);
			receiveBillSet.add(tempInfo.getHead().getId().toString());
		}
		tempInfo= null;
		
		EntityViewInfo entityViewInfo = new EntityViewInfo();
		FilterInfo filterInfo = new FilterInfo();
		filterInfo.getFilterItems().add(new  FilterItemInfo("room.id", roomId));	
		filterInfo.getFilterItems().add(new  FilterItemInfo("id", receiveBillSet,CompareType.INCLUDE));
		entityViewInfo.setFilter(filterInfo);
		FDCReceiveBillCollection fdcreceiveColl =null;
		try
		{
			 fdcreceiveColl = 	FDCReceiveBillFactory.getRemoteInstance().getFDCReceiveBillCollection(entityViewInfo);
		} catch (BOSException e)
		{
			e.printStackTrace();
		}
		
		if(fdcreceiveColl==null || fdcreceiveColl.size()<1){
			return new FDCReceiveBillEntryCollection();
		}
		
		///////////////////////////////////////////////////////////////////////////////////////////////
		///�����տ��� �����տ -�տ���ϸ����
		FDCReceiveBillEntryCollection  returnColl= new FDCReceiveBillEntryCollection();
		Set  set = new HashSet();
		for(int i=0;i<fdcreceiveColl.size();i++){
			set.add(fdcreceiveColl.get(i).getId().toString());
		}
	
		EntityViewInfo receiveViewInfo = new EntityViewInfo();
		FilterInfo receivefilterInfo = new FilterInfo();
		
		receivefilterInfo.getFilterItems().add(new  FilterItemInfo("fdcReceiveBill.id", set,CompareType.INCLUDE));
		ReceivingBillCollection receivecoll = null;
		Set receiveSet = new HashSet();
		receiveViewInfo.setFilter(receivefilterInfo);
		try
		{
			receivecoll  = ReceivingBillFactory.getRemoteInstance().getReceivingBillCollection(receiveViewInfo);
		} catch (BOSException e)
		{
			e.printStackTrace();
		}
		
		if(receivecoll==null ||receivecoll.size()<1){
			return returnColl;
			
		}
		for(int i=0;i<receivecoll.size();i++){
			receiveSet.add(receivecoll.get(i).getId().toString());
		}
		
		
		//////////////////////////////////////////////////////////////////////////////////////////////////////
		///��� Ԥ�տ� ��  ����� ����
		EntityViewInfo moneyDefineViewInfo = new EntityViewInfo();
		FilterInfo moneyDefinefilterInfo = new FilterInfo();
		moneyDefinefilterInfo.getFilterItems().add(new  FilterItemInfo("moneyType", MoneyTypeEnum.PREMONEY_VALUE));
		moneyDefineViewInfo.setFilter(moneyDefinefilterInfo);
		MoneyDefineCollection mdcoll = null;
		Set mdSet = new HashSet();
		try
		{
			mdcoll = MoneyDefineFactory.getRemoteInstance().getMoneyDefineCollection(moneyDefineViewInfo);
		} catch (BOSException e1)
		{
			e1.printStackTrace();
		}
		if(mdcoll==null  || mdcoll.size()<1){
			return returnColl;
			
		}
		for(int i=0;i<mdcoll.size();i++){
			mdSet.add(mdcoll.get(i).getId().toString());
		}
		
		//////////////////////////////////////////////////////////////////
		////�տ���ϸ�� ����  ָ��������� + �տ�����Ϊ Ԥ�յ� ����
		EntityViewInfo entryViewInfo = new EntityViewInfo();
		FilterInfo entryfilterInfo = new FilterInfo();
		entryfilterInfo.getFilterItems().add(new  FilterItemInfo("moneyDefine.id", mdSet,CompareType.INCLUDE));
		entryfilterInfo.getFilterItems().add(new  FilterItemInfo("receivingBill.id", receiveSet,CompareType.INCLUDE));
		entryViewInfo.setFilter(entryfilterInfo);
		
		try
		{
			returnColl  = FDCReceiveBillEntryFactory.getRemoteInstance().getFDCReceiveBillEntryCollection(entryViewInfo);
		} catch (BOSException e)
		{
			e.printStackTrace();
		}
		
		return returnColl;
		
		
	}
	
	
	/**
	 * ��ȡ��Լ��Լ��������
	 * @param info
	 * @return
	 */
	private  CmsTimeConferBillCollection getCMSTimeConferList(FDCCustomerInfo info){
		
		EntityViewInfo entityViewInfo = new EntityViewInfo();
		
    	FilterInfo filterInfo = new FilterInfo();
		filterInfo.getFilterItems().add(new  FilterItemInfo("customer.id", info.getId().toString()));	
		filterInfo.getFilterItems().add(new  FilterItemInfo("state", FDCBillStateEnum.AUDITTED_VALUE));	
		filterInfo.getFilterItems().add(new  FilterItemInfo("isEnable","1"));	
		entityViewInfo.setFilter(filterInfo);
		CmsTimeConferBillCollection cmsConfercoll =null;
		try
		{
			 cmsConfercoll = CmsTimeConferBillFactory.getRemoteInstance().getCmsTimeConferBillCollection(entityViewInfo);
		} catch (BOSException e)
		{
			e.printStackTrace();
		}
		
		
		return cmsConfercoll;
	}
	
	
	/**
	 * ��ȡ�����¼
	 * @param info
	 * @param roomId
	 * @return
	 */
	private  ServiceProcessBillCollection getCustomerServiceRecordList(FDCCustomerInfo info,String roomId){
		EntityViewInfo entityViewInfo = new EntityViewInfo();
		
    	FilterInfo filterInfo = new FilterInfo();
    	filterInfo.getFilterItems().add(new  FilterItemInfo("customer.id", info.getId().toString()));	
    	//filterInfo.getFilterItems().add(new  FilterItemInfo("room", roomId));	
    	filterInfo.getFilterItems().add(new  FilterItemInfo("processType", FDCStdQueationProcessType.CONSULATION_VALUE));	
    	filterInfo.getFilterItems().add(new  FilterItemInfo("processType", FDCStdQueationProcessType.REPAIR_VALUE));	
    	filterInfo.getFilterItems().add(new  FilterItemInfo("processType", FDCStdQueationProcessType.SERVICE_VALUE));
    	
    	filterInfo.setMaskString(" #0 and (#1 or  #2 or #3) ");
    	 entityViewInfo.setFilter(filterInfo);
    	ServiceProcessBillCollection csrColl =null;
		try
		{
			 csrColl = ServiceProcessBillFactory.getRemoteInstance().getServiceProcessBillCollection(entityViewInfo);
		} catch (BOSException e)
		{
			e.printStackTrace();
		}
		return csrColl;
	}
	
	/**
	 * ��ȡͶ�߼�¼
	 * @param info
	 * @param roomId
	 * @return
	 */
	private  ServiceProcessBillCollection getCustomerServiceComplaintList(FDCCustomerInfo info,String roomId){
		EntityViewInfo entityViewInfo = new EntityViewInfo();
		
		
		
    	FilterInfo filterInfo = new FilterInfo();
		filterInfo.getFilterItems().add(new  FilterItemInfo("customer.id", info.getId().toString()));	
		//filterInfo.getFilterItems().add(new  FilterItemInfo("room", roomId));	
		filterInfo.getFilterItems().add(new  FilterItemInfo("processType", FDCStdQueationProcessType.COMPLAIN_VALUE));	
		entityViewInfo.setFilter(filterInfo);
		ServiceProcessBillCollection spbColl =null;
		try
		{
			 spbColl = ServiceProcessBillFactory.getRemoteInstance().getServiceProcessBillCollection(entityViewInfo);
		} catch (BOSException e)
		{
			e.printStackTrace();
		}
		return spbColl;
	}
	
	
	/**
	 * ��� ������Ϣ
	 * @param incoll
	 * @param outcoll
	 */
	private void fetchHouseTable(JoinVoucherCollection incoll, CustomerMoveVoucherCollection outcoll){
		this.houseProperty.checkParsed();
		houseProperty.getStyleAttributes().setLocked(false);
		houseProperty.getSelectManager().setSelectMode(KDTSelectManager.ROW_SELECT);
		houseProperty.getColumn("id").getStyleAttributes().setHided(true);
		
		
		//�ͻ���ס�Ǽ�  Ǩ���Ǽ�
		if(incoll!=null){
			for(int i=0;i<incoll.size();i++) {
				JoinVoucherInfo inInfo = incoll.get(i);
				try
				{
					//TenancyBillInfo trackInfo = custInfo.getTenancyBill();
					if(inInfo!=null) {
						RoomInfo room = RoomFactory.getRemoteInstance().getRoomInfo(new ObjectUuidPK(inInfo.getRoom().getId()));
						SellProjectInfo sellProjectInfo = (SellProjectInfo)com.kingdee.bos.ui.face.UIRuleUtil
								.getObject(com.kingdee.bos.ui.face.UIRuleUtil.getProperty(inInfo,"sellproject"));
						IRow row = this.houseProperty.addRow();
						row.setUserObject(inInfo);
						row.getCell("id").setValue(inInfo.getId().toString());
						row.getCell("projectName").setValue(sellProjectInfo);
						if(sellProjectInfo.getSubarea()!=null && sellProjectInfo.getSubarea().size()>0){
							SubareaInfo subInfo = (SubareaInfo) sellProjectInfo.getSubarea().get(0);
							row.getCell("partition").setValue(subInfo.getNumber());
						}
						row.getCell("building").setValue(com.kingdee.bos.ui.face.UIRuleUtil
								.getObject(com.kingdee.bos.ui.face.UIRuleUtil.getProperty(room,"building")));
						row.getCell("unit").setValue(com.kingdee.bos.ui.face.UIRuleUtil
								.getObject(com.kingdee.bos.ui.face.UIRuleUtil.getProperty(room,"buildunit")));
						row.getCell("room").setValue(room);
						row.getCell("buildingarea").setValue(room.getBuildingArea());
						row.getCell("roomArea").setValue(room.getRoomArea());
						row.getCell("actualBuildingArea").setValue(room.getActualBuildingArea());
						row.getCell("actualRoomArea").setValue(room.getActualRoomArea());
						row.getCell("bizDate").setValue(inInfo.getBizDate());
						
						row.getCell("customerType").setValue(getCustomerType(inInfo.getJoinType(),inInfo.getJoinStatus()));
						row.getCell("phone").setValue(inInfo.getPhone());
						
					}
				} catch (EASBizException e)
				{
					e.printStackTrace();
				} catch (BOSException e)
				{
					e.printStackTrace();
				}
			}	
		}
		
		/*  Unused
		//MoveOut
		if(outcoll!=null){
			for(int i=0;i<outcoll.size();i++) {
				CustomerMoveVoucherInfo inInfo = outcoll.get(i);
				try
				{
				//TenancyBillInfo trackInfo = custInfo.getTenancyBill();
					if(inInfo!=null) {
						RoomInfo room;
					
							room = RoomFactory.getRemoteInstance().getRoomInfo(new ObjectUuidPK(inInfo.getRoom().getId()));
						//RoomInfo room = inInfo.getRoom();
						IRow row = this.houseProperty.addRow();
						row.setUserObject(inInfo);
						row.getCell("id").setValue(inInfo.getId().toString());
						row.getCell("projectName").setValue(
								com.kingdee.bos.ui.face.UIRuleUtil
										.getObject(com.kingdee.bos.ui.face.UIRuleUtil.getProperty(inInfo,"sellproject")));
						row.getCell("building").setValue(com.kingdee.bos.ui.face.UIRuleUtil
								.getObject(com.kingdee.bos.ui.face.UIRuleUtil.getProperty(room,"building")));
						row.getCell("unit").setValue(com.kingdee.bos.ui.face.UIRuleUtil
								.getObject(com.kingdee.bos.ui.face.UIRuleUtil.getProperty(room,"buildunit")));
						row.getCell("room").setValue(room);
						row.getCell("buildingarea").setValue(room.getBuildingArea());
						row.getCell("roomArea").setValue(room.getRoomArea());
						row.getCell("actualBuildingArea").setValue(room.getActualBuildingArea());
						row.getCell("actualRoomArea").setValue(room.getActualRoomArea());
						row.getCell("bizDate").setValue(inInfo.getBizDate());
						
						//row.getCell("customerType").setValue(getCustomerType(inInfo.getJoinType(),inInfo.getJoinStatus()));
						//row.getCell("phone").setValue(inInfo.get);//TODO
						
					}
				} catch (EASBizException e)
				{
					e.printStackTrace();
				} catch (BOSException e)
				{
					e.printStackTrace();
				}
			}	
			
		}*/
		
		
	}
	
	/**
	 * ���Ƿ���¼
	 * 
	 * @param generalcoll
	 * @param measurecoll
	 * @param temporarycoll
	 */
	private void fetchArrearageTable(PPMGeneralARCollection generalcoll,
	PPMMeasureARCollection measurecoll,
	PPMTemporaryCollection temporarycoll){
		this.advanceReceipt.removeRows();
		this.advanceReceipt.checkParsed();
		advanceReceipt.getStyleAttributes().setLocked(false);
		advanceReceipt.getSelectManager().setSelectMode(KDTSelectManager.ROW_SELECT);
		advanceReceipt.getColumn("id").getStyleAttributes().setHided(true);
		
		int seq =0; //line number  
		
		if(generalcoll!=null){
			for(int i=0;i<generalcoll.size();i++) {
				fetchArrearageTableRow(this.advanceReceipt,generalcoll.get(i),seq);
			}	
		}
		
		if(measurecoll!=null){
			for(int i=0;i<measurecoll.size();i++) {
				fetchArrearageTableRow(this.advanceReceipt,measurecoll.get(i),seq);
			}	
		}
		if(temporarycoll!=null){
			for(int i=0;i<temporarycoll.size();i++) {
					fetchArrearageTableRow(this.advanceReceipt,temporarycoll.get(i),seq);
			}
		}
			
		}
	
	
	/**
	 *���  ����Ӧ�ա�����Ӧ�ռ���ʱӦ��  Ƿ���¼ 
	 * @param table
	 * @param inInfo
	 * @param seq   δ��
	 */
	private void  fetchArrearageTableRow(KDTable table,PPMARBaseInfo inInfo,int seq){
		
		IRow row = table.addRow();
		if(inInfo!=null){
			try
			{
			//row.setUserObject(inInfo);
			row.getCell("id").setValue(inInfo.getId().toString());
				row.getCell("projectName").setValue(com.kingdee.bos.ui.face.UIRuleUtil
						.getObject(com.kingdee.bos.ui.face.UIRuleUtil.getProperty(inInfo,"sellproject")));
			row.getCell("roomNumber").setValue(com.kingdee.bos.ui.face.UIRuleUtil
					.getObject(com.kingdee.bos.ui.face.UIRuleUtil.getProperty(inInfo,"room")));
			row.getCell("fundName").setValue(
					com.kingdee.bos.ui.face.UIRuleUtil
					.getObject(com.kingdee.bos.ui.face.UIRuleUtil.getProperty(inInfo,"chargeitem")));
			row.getCell("payFeeDate").setValue(inInfo.getArDate());
			row.getCell("receivableFee").setValue(inInfo.getArAmout());
			row.getCell("arrearageFee").setValue(checkarrearage(inInfo));
			
			} catch (DataAccessException e)
			{
				e.printStackTrace();
			} catch (BOSException e)
			{
				e.printStackTrace();
			}
		}
	}
	
	
	/**
	 * ���Ԥ�տ��¼
	 * @param coll
	 * @param room
	 * @param sellProject
	 */
	private void fetchDepositTable(FDCReceiveBillEntryCollection coll,RoomInfo room,SellProjectInfo  sellProject){
		this.arrearsRecord.removeRows();
		this.arrearsRecord.checkParsed();
		arrearsRecord.getStyleAttributes().setLocked(false);
		arrearsRecord.getSelectManager().setSelectMode(KDTSelectManager.ROW_SELECT);
		arrearsRecord.getColumn("id").getStyleAttributes().setHided(true);
		
		if(coll!=null){
			for(int i=0;i<coll.size();i++) {
				FDCReceiveBillEntryInfo inInfo = coll.get(i);
				try
				{
						/////////ȥ���տ���-�����<0
						if(inInfo!=null) {
						if(inInfo.getAmount()!=null && inInfo.getCounteractAmount()!=null){
							 if(inInfo.getAmount().compareTo(inInfo.getCounteractAmount())<0){
								 continue;
							 }
						}
					
					IRow row = this.arrearsRecord.addRow();
					row.getCell("id").setValue(inInfo.getId().toString());
					row.getCell("projectName").setValue(sellProject);
					row.getCell("room").setValue(room);
					row.getCell("fundName").setValue(com.kingdee.bos.ui.face.UIRuleUtil
								.getObject(com.kingdee.bos.ui.face.UIRuleUtil.getProperty(inInfo,"moneyDefine")));
					
					row.getCell("receiveFee").setValue(inInfo.getAmount());
					
					
					BigDecimal usableFee =inInfo.getAmount();
					if(inInfo.getAmount()!=null &&inInfo.getCounteractAmount()!=null){
						 usableFee =  inInfo.getAmount().subtract(inInfo.getCounteractAmount());
					}
					row.getCell("usableFee").setValue(usableFee);
					
				
				}
				} catch (DataAccessException e)
				{
					e.printStackTrace();
				} catch (BOSException e)
				{
					e.printStackTrace();
				}
			}	
		}
	}
	
	/**
	 * �����Լ����
	 * @param coll
	 */
	private void fetchCustomerServiceSpecialTable(CmsTimeConferBillCollection coll){
		this.specialServer.checkParsed();
		specialServer.getStyleAttributes().setLocked(false);
		specialServer.getSelectManager().setSelectMode(KDTSelectManager.ROW_SELECT);
		specialServer.getColumn("id").getStyleAttributes().setHided(true);
		
		int seq =0; //line number  
		
		if(coll!=null){
			for(int i=0;i<coll.size();i++) {
				CmsTimeConferBillInfo inInfo = coll.get(i);
				seq++;
				try
				{
				if(inInfo!=null) {
					IRow row = this.specialServer.addRow();
					//row.setUserObject(inInfo);
					row.getCell("id").setValue(inInfo.getId().toString());
					row.getCell("proeprtyItem").setValue(com.kingdee.bos.ui.face.UIRuleUtil
							.getObject(com.kingdee.bos.ui.face.UIRuleUtil.getProperty(inInfo,"sellproject")));
					row.getCell("roomNumber").setValue(com.kingdee.bos.ui.face.UIRuleUtil
							.getObject(com.kingdee.bos.ui.face.UIRuleUtil.getProperty(inInfo,"room")));
					row.getCell("servicesProject").setValue(com.kingdee.bos.ui.face.UIRuleUtil
								.getObject(com.kingdee.bos.ui.face.UIRuleUtil.getProperty(inInfo,"serviceItem")));
					row.getCell("appointments").setValue(inInfo.getAdvanceTime());
				}
				} catch (DataAccessException e)
				{
					e.printStackTrace();
				} catch (BOSException e)
				{
					e.printStackTrace();
				}
			}	
		}
		
		
		
	}
	
	/**
	 * ������,	Ͷ��  ��¼
	 * @param coll
	 */
	private void fetchCustomerServiceRecordTable(ServiceProcessBillCollection coll){
		this.serverRecord.removeRows();
		this.serverRecord.checkParsed();
		serverRecord.getStyleAttributes().setLocked(false);
		serverRecord.getSelectManager().setSelectMode(KDTSelectManager.ROW_SELECT);
		serverRecord.getColumn("id").getStyleAttributes().setHided(true);
		
		int seq =0; //line number  
		if(coll!=null){
			for(int i=0;i<coll.size();i++) {
				ServiceProcessBillInfo inInfo = coll.get(i);
				seq++;
				try
				{
					if(inInfo!=null) {
						IRow row = this.serverRecord.addRow();
						//row.setUserObject(inInfo);
						row.getCell("id").setValue(inInfo.getId().toString());
						row.getCell("propertyItem").setValue(com.kingdee.bos.ui.face.UIRuleUtil
								.getObject(com.kingdee.bos.ui.face.UIRuleUtil.getProperty(inInfo,"sellproject")));
						row.getCell("roomNumber").setValue(com.kingdee.bos.ui.face.UIRuleUtil
								.getObject(com.kingdee.bos.ui.face.UIRuleUtil.getProperty(inInfo,"room")));
						
						row.getCell("phone").setValue(inInfo.getLinkTel());
						row.getCell("callTime").setValue(inInfo.getRegisterTime());
						row.getCell("callcontent").setValue(inInfo.getRegisterNote());
				
						row.getCell("questionSort").setValue(com.kingdee.bos.ui.face.UIRuleUtil
								.getObject(com.kingdee.bos.ui.face.UIRuleUtil.getProperty(inInfo,"stdQuestion")));
						
						row.getCell("dealSchedule").setValue(inInfo.getBillStatus());
						row.getCell("dealDescription").setValue(inInfo.getProcessNote());
						//row.getCell("receiveFee").setValue(inInfo.getProcessNote());  dealTime
						row.getCell("dealPerson").setValue(com.kingdee.bos.ui.face.UIRuleUtil
								.getObject(com.kingdee.bos.ui.face.UIRuleUtil.getProperty(inInfo,"accepter")));
						
					}
				} catch (DataAccessException e)
				{
					e.printStackTrace();
				} catch (BOSException e)
				{
					e.printStackTrace();
				}
			}	
		}
	}
	
	private void fetchCustomerServiceComplaintTable(ServiceProcessBillCollection coll){
		this.complaintRecord.removeRows();
		this.complaintRecord.checkParsed();
		complaintRecord.getStyleAttributes().setLocked(false);
		complaintRecord.getSelectManager().setSelectMode(KDTSelectManager.ROW_SELECT);
		complaintRecord.getColumn("id").getStyleAttributes().setHided(true);
		
		int seq =0; //line number  
		if(coll!=null){
			for(int i=0;i<coll.size();i++) {
				ServiceProcessBillInfo inInfo = coll.get(i);
				seq++;
				try
				{
					if(inInfo!=null) {
						IRow row = this.complaintRecord.addRow();
						//row.setUserObject(inInfo);
						row.getCell("id").setValue(inInfo.getId().toString());
						row.getCell("propertyItem").setValue(com.kingdee.bos.ui.face.UIRuleUtil
								.getObject(com.kingdee.bos.ui.face.UIRuleUtil.getProperty(inInfo,"sellproject")));
						row.getCell("roomNumber").setValue(com.kingdee.bos.ui.face.UIRuleUtil
								.getObject(com.kingdee.bos.ui.face.UIRuleUtil.getProperty(inInfo,"room")));
						
						row.getCell("phone").setValue(inInfo.getLinkTel());
						row.getCell("callTime").setValue(inInfo.getRegisterTime());
						row.getCell("callContent").setValue(inInfo.getRegisterNote());
						//row.getCell("dealType").setValue(inInfo.getProcessType());
						row.getCell("questionSort").setValue(com.kingdee.bos.ui.face.UIRuleUtil
								.getObject(com.kingdee.bos.ui.face.UIRuleUtil.getProperty(inInfo,"stdQuestion")));
						
						row.getCell("dealSchedule").setValue(inInfo.getBillStatus());
						row.getCell("dealDescription").setValue(inInfo.getProcessNote());
						//row.getCell("receiveFee").setValue(inInfo.getProcessNote());  dealTime
						row.getCell("dealPerson").setValue(com.kingdee.bos.ui.face.UIRuleUtil
								.getObject(com.kingdee.bos.ui.face.UIRuleUtil.getProperty(inInfo,"accepter")));
						
					}
				} catch (DataAccessException e)
				{
					e.printStackTrace();
				} catch (BOSException e)
				{
					e.printStackTrace();
				}
			}	
		}
	}
	
	/**
	 * ���� ������� ���״̬ ���� �ͻ�����
	 * @param type  �������
	 * @param status���״̬
	 * @return  �ͻ�����  
	 */
	private Object  getCustomerType(JoinType type,JoinStatus status){
		if(JoinType.HOUSEMASTER.equals(type) && JoinStatus.JOIN.equals(status) ){
			return "��ҵ��";
		}
		else if(JoinType.HOUSEMASTER.equals(type) && JoinStatus.WITHDRAW.equals(status) ){
			return "��ҵ��";
		}else if(JoinType.LESSEE.equals(type) && JoinStatus.JOIN.equals(status) ){
			return "���⻧";
		}else if(JoinType.LESSEE.equals(type) && JoinStatus.WITHDRAW.equals(status) ){
			return "���⻧";
		}
		return null;
	}
	
	
	private BigDecimal checkarrearage(PPMARBaseInfo info){
			BigDecimal arAmount = info.getArAmout();
				if(arAmount==null) arAmount = new BigDecimal(0);
			BigDecimal derateAmount = info.getDerateAmount();
				if(derateAmount==null) derateAmount = new BigDecimal(0);
			BigDecimal payedAmount = info.getPayedAmount();
			    if(payedAmount==null) payedAmount = new BigDecimal(0);
			return arAmount.subtract(derateAmount).subtract(payedAmount);
	}	
	
	private void registerListener(){
		
		
	}
	
	/**
	 * ѡ�� ������Ϣ���� Ƿ���¼ ��Ԥ�տ�
	 * @param info
	 * @return
	 */
	protected void houseProperty_tableSelectChanged(KDTSelectEvent e)
			throws Exception
	{
		super.houseProperty_tableSelectChanged(e);

		IRow row = KDTableUtil.getSelectedRow(this.houseProperty);
		RoomInfo room = (RoomInfo) row.getCell("room").getValue();
		initPropertyArrearage(this.editData, room);
		initPropertyDeposit(this.editData, room);
	}
	
	/**
	 * ѡ�� ��Լ���� ���� �����¼ ��Ͷ�߼�¼
	 */
	protected void specialServer_tableSelectChanged(KDTSelectEvent e)
			throws Exception
	{
		super.specialServer_tableSelectChanged(e);
		IRow row = KDTableUtil.getSelectedRow(this.specialServer);
		RoomInfo room = (RoomInfo) row.getCell("roomNumber").getValue();
		initCustomerServiceRecord(this.editData, room);
		initCustomerServiceComplaint(this.editData, room);
	}
	
	protected void houseProperty_editStopped(KDTEditEvent e) throws Exception
	{
		if ((e.getValue() != null && e.getValue().equals(e.getOldValue()))
				|| (e.getValue() == null && e.getOldValue() == null)) {
			return;
		}
		int selectRow = e.getRowIndex();
		int selectCol = e.getColIndex();
		KDTable kDTable = (KDTable) e.getSource();

		IRow curRow = houseProperty.getRow(selectRow);
		
		int phoneCol = kDTable.getColumnIndex("phone");
		if (selectCol == phoneCol) {
			
			if(curRow.getCell("id")!=null){
				String phone= houseProperty.getRow(selectRow).getCell("phone").getValue().toString();
				updatePhoneToJoinVoucher(curRow.getCell("id").getValue().toString(),phone);
			}
		}
		
	}
	
	private void updatePhoneToJoinVoucher(String id,String phone){
		IJoinVoucher ijoin;
		try
		{
			ijoin = JoinVoucherFactory.getRemoteInstance();
			JoinVoucherInfo info= ijoin.getJoinVoucherInfo(new ObjectUuidPK(BOSUuid.read(id)));
			if(info!=null){
				info.setPhone(phone);
				ijoin.update(new ObjectUuidPK(info.getId()), info);
			}
		} catch (BOSException e)
		{
			e.printStackTrace();
		} catch (EASBizException e)
		{
			e.printStackTrace();
		} catch (UuidException e)
		{
			e.printStackTrace();
		}
	}
	/////////////////
	
	/**
	 * �������ܣ�����̻�
	 */
	public void actionAddCommerceChance_actionPerformed(ActionEvent e)
			throws Exception {
		super.actionAddCommerceChance_actionPerformed(e);

		
		if (editData.getId() == null) {
			MsgBox.showInfo("���ȱ���ͻ���Ϣ!");
			return;
		}
		String idStr = editData.getId().toString();

		FDCCustomerInfo fdcCust = FDCCustomerFactory.getRemoteInstance()
				.getFDCCustomerInfo(new ObjectUuidPK(BOSUuid.read(idStr)));
		MoneySysTypeEnum latSysType = (MoneySysTypeEnum)getUIContext().get("latSysType");
		if(latSysType == null){
			latSysType = MoneySysTypeEnum.SalehouseSys;
		}
		UIContext uiContext = new UIContext(this);
		// ��������ϵͳ�Ϳͻ�
		if (fdcCust != null)
			uiContext.put("FdcCustomer", fdcCust);
		uiContext.put("SysType", latSysType);
		try {
			IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.MODEL)
					.create(CommerceChanceEditUI.class.getName(), uiContext,
							null, OprtState.ADDNEW);
			uiWindow.show();
		} catch (UIException ee) {
			ee.printStackTrace();
			this.handleException(ee);
			this.abort();
		}
	}
	
	
	//��ʼ��f7��ҵ�������ṹ
	public static void initNatureEnterpriseF7(CoreUIObject owner, KDBizPromptBox box, String cuId) {
		GeneralKDPromptSelectorAdaptor selectorLisenterSupplier = new GeneralKDPromptSelectorAdaptor(
				box,F7NatureEnterpriseListUI.class.getName(),owner,"com.kingdee.eas.fdc.tenancy.app.F7NatureEnterpriseQuery");
		box.setSelector(selectorLisenterSupplier);
		EntityViewInfo evi = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("isLeaf",new Integer(1)));
		box.setEntityViewInfo(evi);
		box.setQueryInfo("com.kingdee.eas.fdc.tenancy.app.F7NatureEnterpriseQuery");
	}
	//��ʼ��f7ҵ��Χ���ṹ
	public static void initBusinessScopeF7(CoreUIObject owner, KDBizPromptBox box, String cuId) {
		GeneralKDPromptSelectorAdaptor selectorLisenterSupplier = new GeneralKDPromptSelectorAdaptor(
				box,F7BusinessScopeListUI.class.getName(),owner,"com.kingdee.eas.fdc.tenancy.app.F7BusinessScopeQuery");
		box.setSelector(selectorLisenterSupplier);
		box.setQueryInfo("com.kingdee.eas.fdc.tenancy.app.F7BusinessScopeQuery");
	}
	//��ʼ��f7����ģʽ���ṹ
	public static void initCooperateModeF7(CoreUIObject owner, KDBizPromptBox box, String cuId) {
		GeneralKDPromptSelectorAdaptor selectorLisenterSupplier = new GeneralKDPromptSelectorAdaptor(
				box,F7CooperateModeListUI.class.getName(),owner,"com.kingdee.eas.fdc.tenancy.app.F7CooperateModeQuery");
		box.setSelector(selectorLisenterSupplier);
		box.setQueryInfo("com.kingdee.eas.fdc.tenancy.app.F7CooperateModeQuery");
	}
	public void actionInsider_actionPerformed(ActionEvent e) throws Exception {
		FDCCustomerInfo info = this.editData;
		Map ctxMap = new HashMap();
		ctxMap.put("fdcCustomerInfo", info);
		ctxMap.put(UIContext.OWNER, this);
		IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.NEWWIN)
			.create(InsiderApplicationNewEditUI.class.getName(), ctxMap,null,OprtState.ADDNEW);
		uiWindow.show();
	}
	
		/////////////////
	
	public void actionRev_actionPerformed(ActionEvent e) throws Exception {
		UIContext uiContext = new UIContext(this);
		uiContext.put(SHEReceivingBillEditUI.KEY_SELL_PROJECT, this.editData.getProject());
		uiContext.put(SHEReceivingBillEditUI.KEY_REV_BILL_TYPE, RevBillTypeEnum.gathering);
		uiContext.put(SHEReceivingBillEditUI.KEY_IS_LOCK_BILL_TYPE, new Boolean(true));	
		uiContext.put(SHEReceivingBillEditUI.KEY_REV_BIZ_TYPE, RevBizTypeEnum.customer);
		uiContext.put(SHEReceivingBillEditUI.KEY_SELL_FdcCustUser, this.editData);
		IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.NEWTAB)
				.create(SHEReceivingBillEditUI.class.getName(), uiContext, null,"ADDNEW");
		uiWindow.show();			
	}
	
	public void actionRefund_actionPerformed(ActionEvent e) throws Exception {
		UIContext uiContext = new UIContext(this);
		uiContext.put(SHEReceivingBillEditUI.KEY_SELL_PROJECT, this.editData.getProject());
		uiContext.put(SHEReceivingBillEditUI.KEY_REV_BILL_TYPE, RevBillTypeEnum.refundment);
		uiContext.put(SHEReceivingBillEditUI.KEY_IS_LOCK_BILL_TYPE, new Boolean(true));	
		uiContext.put(SHEReceivingBillEditUI.KEY_REV_BIZ_TYPE, RevBizTypeEnum.customer);
		uiContext.put(SHEReceivingBillEditUI.KEY_SELL_FdcCustUser, this.editData);
		IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.NEWTAB)
				.create(SHEReceivingBillEditUI.class.getName(), uiContext, null,"ADDNEW");
		uiWindow.show();		
	}
	
	
	/**
	 * ��ѯ��ǰ��֯�����е�Ӫ����Ա��ids
	 * xiaoa_liu
	 * @param treeNode
	 */
	private void getAllSaleManIds(TreeNode treeNode, Set saleManIds) {
		if (treeNode != null) {
			if (treeNode.isLeaf()) {
				DefaultKingdeeTreeNode thisNode = (DefaultKingdeeTreeNode) treeNode;
				if (thisNode.getUserObject() instanceof UserInfo) {
					UserInfo user = (UserInfo) thisNode.getUserObject();
					saleManIds.add(user.getId().toString());
				}
			} else {
				int childCount = treeNode.getChildCount();
				while (childCount > 0) {
					getAllSaleManIds(treeNode.getChildAt(childCount - 1),
							saleManIds);
					childCount--;
				}
			}
		}
	}
	/**
	 * ��ѯ��ǰ��֯�����е�Ӫ����Ԫ��ids
	 * xiaoa_liu
	 * @param treeNode
	 */
	private void getAllMarketingunitIds(TreeNode treeNode, Set marketingUnitIDs){
		if (treeNode != null) {
				DefaultKingdeeTreeNode thisNode = (DefaultKingdeeTreeNode) treeNode;
				if (thisNode.getUserObject() instanceof MarketingUnitInfo) {
					MarketingUnitInfo user = (MarketingUnitInfo) thisNode.getUserObject();
					marketingUnitIDs.add(user.getId().toString());
			    } else {
				int childCount = treeNode.getChildCount();
				while (childCount > 0) {
					getAllMarketingunitIds(treeNode.getChildAt(childCount - 1),marketingUnitIDs);
							
					childCount--;
				}
			}
		}
	}
	
}