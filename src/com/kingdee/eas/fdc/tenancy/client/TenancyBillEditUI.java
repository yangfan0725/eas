/**
 * output package name
 */
package com.kingdee.eas.fdc.tenancy.client;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.EventListener;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import javax.swing.AbstractButton;
import javax.swing.Action;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox;
import com.kingdee.bos.ctrl.extendcontrols.KDCommonPromptDialog;
import com.kingdee.bos.ctrl.extendcontrols.ext.IFilterInfoProducer;
import com.kingdee.bos.ctrl.kdf.table.ICell;
import com.kingdee.bos.ctrl.kdf.table.IColumn;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTDefaultCellEditor;
import com.kingdee.bos.ctrl.kdf.table.KDTMergeManager;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectBlock;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectManager;
import com.kingdee.bos.ctrl.kdf.table.KDTStyleConstants;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.event.BeforeActionEvent;
import com.kingdee.bos.ctrl.kdf.table.event.BeforeActionListener;
import com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent;
import com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent;
import com.kingdee.bos.ctrl.kdf.table.util.KDTableUtil;
import com.kingdee.bos.ctrl.kdf.util.editor.ICellEditor;
import com.kingdee.bos.ctrl.kdf.util.style.Styles.HorizontalAlignment;
import com.kingdee.bos.ctrl.swing.KDComboBox;
import com.kingdee.bos.ctrl.swing.KDComboBoxMultiColumnItem;
import com.kingdee.bos.ctrl.swing.KDContainer;
import com.kingdee.bos.ctrl.swing.KDDatePicker;
import com.kingdee.bos.ctrl.swing.KDFormattedTextField;
import com.kingdee.bos.ctrl.swing.KDTextField;
import com.kingdee.bos.ctrl.swing.KDWorkButton;
import com.kingdee.bos.ctrl.swing.event.DataChangeEvent;
import com.kingdee.bos.ctrl.swing.event.DataChangeListener;
import com.kingdee.bos.ctrl.swing.event.SelectorEvent;
import com.kingdee.bos.ctrl.swing.event.SelectorListener;
import com.kingdee.bos.dao.IObjectCollection;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.IMetaDataLoader;
import com.kingdee.bos.metadata.IMetaDataPK;
import com.kingdee.bos.metadata.MetaDataLoaderFactory;
import com.kingdee.bos.metadata.MetaDataPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemCollection;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.ItemAction;
import com.kingdee.bos.ui.face.UIException;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.base.attachment.AttachmentFactory;
import com.kingdee.eas.base.attachment.AttachmentFtpFacadeFactory;
import com.kingdee.eas.base.attachment.AttachmentInfo;
import com.kingdee.eas.base.attachment.BizobjectFacadeFactory;
import com.kingdee.eas.base.attachment.BoAttchAssoCollection;
import com.kingdee.eas.base.attachment.BoAttchAssoFactory;
import com.kingdee.eas.base.attachment.BoAttchAssoInfo;
import com.kingdee.eas.base.attachment.IAttachment;
import com.kingdee.eas.base.attachment.common.AttachmentClientManager;
import com.kingdee.eas.base.attachment.common.AttachmentManagerFactory;
import com.kingdee.eas.base.attachment.util.FileGetter;
import com.kingdee.eas.base.param.ParamControlFactory;
import com.kingdee.eas.base.permission.UserInfo;
import com.kingdee.eas.base.uiframe.client.UIFactoryHelper;
import com.kingdee.eas.basedata.assistant.CurrencyInfo;
import com.kingdee.eas.basedata.org.CtrlUnitInfo;
import com.kingdee.eas.basedata.org.FullOrgUnitInfo;
import com.kingdee.eas.basedata.org.OrgType;
import com.kingdee.eas.basedata.org.OrgUnitInfo;
import com.kingdee.eas.basedata.org.SaleOrgUnitInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.cp.bc.BizCollUtil;
import com.kingdee.eas.fdc.basecrm.CRMHelper;
import com.kingdee.eas.fdc.basecrm.FDCReceivingBillCollection;
import com.kingdee.eas.fdc.basecrm.FDCReceivingBillFactory;
import com.kingdee.eas.fdc.basecrm.client.CRMClientHelper;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCDateHelper;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.basedata.MoneySysTypeEnum;
import com.kingdee.eas.fdc.basedata.client.FDCClientHelper;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.basedata.client.FDCTableHelper;
import com.kingdee.eas.fdc.contract.FDCUtils;
import com.kingdee.eas.fdc.sellhouse.BuildingInfo;
import com.kingdee.eas.fdc.sellhouse.BuildingUnitInfo;
import com.kingdee.eas.fdc.sellhouse.CertifacateNameEnum;
import com.kingdee.eas.fdc.sellhouse.DigitEnum;
import com.kingdee.eas.fdc.sellhouse.FDCCustomerInfo;
import com.kingdee.eas.fdc.sellhouse.MoneyDefineCollection;
import com.kingdee.eas.fdc.sellhouse.MoneyDefineFactory;
import com.kingdee.eas.fdc.sellhouse.MoneyDefineInfo;
import com.kingdee.eas.fdc.sellhouse.MoneyTypeEnum;
import com.kingdee.eas.fdc.sellhouse.RoomCollection;
import com.kingdee.eas.fdc.sellhouse.RoomFactory;
import com.kingdee.eas.fdc.sellhouse.RoomInfo;
import com.kingdee.eas.fdc.sellhouse.SellProjectInfo;
import com.kingdee.eas.fdc.sellhouse.SubareaInfo;
import com.kingdee.eas.fdc.sellhouse.ToIntegerTypeEnum;
import com.kingdee.eas.fdc.sellhouse.TrackRecordEnum;
import com.kingdee.eas.fdc.sellhouse.client.CommerceHelper;
import com.kingdee.eas.fdc.sellhouse.client.CustomerEditUI;
import com.kingdee.eas.fdc.sellhouse.client.RoomEditUI;
import com.kingdee.eas.fdc.sellhouse.client.RoomSelectUI;
import com.kingdee.eas.fdc.sellhouse.client.SHEHelper;
import com.kingdee.eas.fdc.tenancy.AgencyContractInfo;
import com.kingdee.eas.fdc.tenancy.AgencyInfo;
import com.kingdee.eas.fdc.tenancy.AttachDealAmountEntryCollection;
import com.kingdee.eas.fdc.tenancy.AttachDealAmountEntryInfo;
import com.kingdee.eas.fdc.tenancy.AttachResourceCollection;
import com.kingdee.eas.fdc.tenancy.AttachResourceFactory;
import com.kingdee.eas.fdc.tenancy.AttachResourceInfo;
import com.kingdee.eas.fdc.tenancy.BizStateEnum;
import com.kingdee.eas.fdc.tenancy.BrandCollection;
import com.kingdee.eas.fdc.tenancy.BrandInfo;
import com.kingdee.eas.fdc.tenancy.ChargeDateTypeEnum;
import com.kingdee.eas.fdc.tenancy.CommisionStandardEnum;
import com.kingdee.eas.fdc.tenancy.ConRentTypeEnum;
import com.kingdee.eas.fdc.tenancy.ContractTypeEnum;
import com.kingdee.eas.fdc.tenancy.CustomerEntryBrandCollection;
import com.kingdee.eas.fdc.tenancy.CustomerEntryBrandFactory;
import com.kingdee.eas.fdc.tenancy.CustomerEntryBrandInfo;
import com.kingdee.eas.fdc.tenancy.DateEnum;
import com.kingdee.eas.fdc.tenancy.DealAmountEntryCollection;
import com.kingdee.eas.fdc.tenancy.DealAmountEntryInfo;
import com.kingdee.eas.fdc.tenancy.DepositDealBillFactory;
import com.kingdee.eas.fdc.tenancy.EquipmentEntryCollection;
import com.kingdee.eas.fdc.tenancy.EquipmentEntryInfo;
import com.kingdee.eas.fdc.tenancy.FirstLeaseTypeEnum;
import com.kingdee.eas.fdc.tenancy.FlagAtTermEnum;
import com.kingdee.eas.fdc.tenancy.FreeTenancyTypeEnum;
import com.kingdee.eas.fdc.tenancy.HandleStateEnum;
import com.kingdee.eas.fdc.tenancy.ICustomerEntryBrand;
import com.kingdee.eas.fdc.tenancy.IDealAmountInfo;
import com.kingdee.eas.fdc.tenancy.ITenPriceBaseLine;
import com.kingdee.eas.fdc.tenancy.ITenancyEntryInfo;
import com.kingdee.eas.fdc.tenancy.ITenancyPayListInfo;
import com.kingdee.eas.fdc.tenancy.ITenancyPriceEntry;
import com.kingdee.eas.fdc.tenancy.IncreaseStyleEnum;
import com.kingdee.eas.fdc.tenancy.IncreasedRentEntryCollection;
import com.kingdee.eas.fdc.tenancy.IncreasedRentEntryInfo;
import com.kingdee.eas.fdc.tenancy.IncreasedRentTypeEnum;
import com.kingdee.eas.fdc.tenancy.LiquidatedInfo;
import com.kingdee.eas.fdc.tenancy.MoneyEnum;
import com.kingdee.eas.fdc.tenancy.MoreRoomsTypeEnum;
import com.kingdee.eas.fdc.tenancy.OccurreStateEnum;
import com.kingdee.eas.fdc.tenancy.OtherBillFactory;
import com.kingdee.eas.fdc.tenancy.QuitTenancyFactory;
import com.kingdee.eas.fdc.tenancy.RentCountTypeEnum;
import com.kingdee.eas.fdc.tenancy.RentFreeBillInfo;
import com.kingdee.eas.fdc.tenancy.RentFreeEntryCollection;
import com.kingdee.eas.fdc.tenancy.RentFreeEntryFactory;
import com.kingdee.eas.fdc.tenancy.RentFreeEntryInfo;
import com.kingdee.eas.fdc.tenancy.RentRemissionFactory;
import com.kingdee.eas.fdc.tenancy.RentStartTypeEnum;
import com.kingdee.eas.fdc.tenancy.RentTypeEnum;
import com.kingdee.eas.fdc.tenancy.SinCustomerEntrysCollection;
import com.kingdee.eas.fdc.tenancy.SinCustomerEntrysInfo;
import com.kingdee.eas.fdc.tenancy.SinObligateAttachEntryCollection;
import com.kingdee.eas.fdc.tenancy.SinObligateAttachEntryInfo;
import com.kingdee.eas.fdc.tenancy.SinObligateRoomsEntryCollection;
import com.kingdee.eas.fdc.tenancy.SinObligateRoomsEntryInfo;
import com.kingdee.eas.fdc.tenancy.SincerObligateInfo;
import com.kingdee.eas.fdc.tenancy.TenAttachResourceEntryCollection;
import com.kingdee.eas.fdc.tenancy.TenAttachResourceEntryInfo;
import com.kingdee.eas.fdc.tenancy.TenAttachResourcePayListEntryCollection;
import com.kingdee.eas.fdc.tenancy.TenAttachResourcePayListEntryInfo;
import com.kingdee.eas.fdc.tenancy.TenBillOtherPayCollection;
import com.kingdee.eas.fdc.tenancy.TenBillOtherPayInfo;
import com.kingdee.eas.fdc.tenancy.TenBillRoomStateEnum;
import com.kingdee.eas.fdc.tenancy.TenLiquidatedCollection;
import com.kingdee.eas.fdc.tenancy.TenLiquidatedInfo;
import com.kingdee.eas.fdc.tenancy.TenPriceBaseLineFactory;
import com.kingdee.eas.fdc.tenancy.TenPriceEntryInfo;
import com.kingdee.eas.fdc.tenancy.TenancyBillFactory;
import com.kingdee.eas.fdc.tenancy.TenancyBillInfo;
import com.kingdee.eas.fdc.tenancy.TenancyBillStateEnum;
import com.kingdee.eas.fdc.tenancy.TenancyContractTypeEnum;
import com.kingdee.eas.fdc.tenancy.TenancyCustomerEntryCollection;
import com.kingdee.eas.fdc.tenancy.TenancyCustomerEntryInfo;
import com.kingdee.eas.fdc.tenancy.TenancyHelper;
import com.kingdee.eas.fdc.tenancy.TenancyModeEnum;
import com.kingdee.eas.fdc.tenancy.TenancyModificationFactory;
import com.kingdee.eas.fdc.tenancy.TenancyPriceEntryCollection;
import com.kingdee.eas.fdc.tenancy.TenancyPriceEntryFactory;
import com.kingdee.eas.fdc.tenancy.TenancyPriceEntryInfo;
import com.kingdee.eas.fdc.tenancy.TenancyRoomChargeEntryCollection;
import com.kingdee.eas.fdc.tenancy.TenancyRoomChargeEntryInfo;
import com.kingdee.eas.fdc.tenancy.TenancyRoomEntryCollection;
import com.kingdee.eas.fdc.tenancy.TenancyRoomEntryFactory;
import com.kingdee.eas.fdc.tenancy.TenancyRoomEntryInfo;
import com.kingdee.eas.fdc.tenancy.TenancyRoomPayListEntryCollection;
import com.kingdee.eas.fdc.tenancy.TenancyRoomPayListEntryFactory;
import com.kingdee.eas.fdc.tenancy.TenancyRoomPayListEntryInfo;
import com.kingdee.eas.fdc.tenancy.TenancyStateEnum;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.MsgBox;
import com.kingdee.jdbc.rowset.IRowSet;
import com.kingdee.util.StringUtils;

/**
 * ���޺�ͬ�༭����
 */
public class TenancyBillEditUI extends AbstractTenancyBillEditUI implements
		TenancyBillConstant {
	private static final long serialVersionUID = 3940285291180126710L;
	private static final Logger log = CoreUIObject
			.getLogger(TenancyBillEditUI.class);
	private SellProjectInfo sellProjectInfo = null;
	private UserInfo userInfo = SysContext.getSysContext().getCurrentUserInfo();
	private SaleOrgUnitInfo saleOrg = SHEHelper.getCurrentSaleOrg();
	BigDecimal rentArea = FDCHelper.ZERO;// TODO �ܶ��ĵı���
	private TenancyBillInfo oldTenancyBill = null;// ������ת��ʱ��ԭ��ͬ
	private MoneyDefineCollection monDefineColl = new MoneyDefineCollection();
	Map roomDisMap = new HashMap();
	boolean isFirstEdit = false;
	String oldState = "";
	boolean isNeedSelectBaseLine = false;
	boolean isNeedSelectBrand = false;

	/** ��Ҫ�Է�¼���л�ȡ.��getSelector���Ѿ����� */
	protected IObjectValue getValue(IObjectPK pk) throws Exception {
		TenancyBillInfo tenancy = (TenancyBillInfo) super.getValue(pk);

		if (tenancy.getOldTenancyBill() != null) {
			String oldTenancyId = tenancy.getOldTenancyBill().getId()
					.toString();
			TenancyBillInfo oldTenancy = TenancyHelper
					.getTenancyBillInfo(oldTenancyId);
			// TenancyBillFactory.getRemoteInstance().getTenancyBillInfo(new
			// ObjectUuidPK(oldTenancyId), this.getSelectors());
			tenancy.setOldTenancyBill(oldTenancy);

			oldTenancyBill = oldTenancy;
		}
		TenancyRoomEntryCollection trc = tenancy.getTenancyRoomList();
		// ���ӶԷ����¼��roomLongNUm �������� eirc_wang 2010.7.06
		CRMHelper.sortCollection(trc, "roomLongNum", true);
		TenancyHelper.sortPayListOfTenancy(tenancy);
		return tenancy;
	}

	public TenancyBillEditUI() throws Exception {
		super();
	}

	private boolean isLoad = false;

	public void loadFields() {
		isLoad = true;
		this.tblTenPrice.checkParsed();
		detachListeners();
		// --��loadFields֮ǰ���ø�ֵΪnull,����ؼ���ȡ��ǰʱ�����
		this.pkStartDate.setValue(null);
		this.pkEndDate.setValue(null);
		super.loadFields();
		TenancyBillInfo tenBill = this.editData;
		oldTenancyBill = tenBill.getOldTenancyBill();
		this.comboRentCountType.setSelectedItem(tenBill.getRentCountType());
		this.txtDaysPerYear.setValue(new Integer(tenBill.getDaysPerYear()));

		this.chkIsAutoToInteger.setSelected(tenBill.isIsAutoToInteger());
		this.chkIsAutoToIntegerForFee.setSelected(tenBill
				.isIsAutoToIntegerFee());

		this.comboToIntegerType.setSelectedItem(tenBill.getToIntegerType());
		this.comboToIntegerTypeFee.setSelectedItem(tenBill
				.getToIntegetTypeFee());

		this.comboDigit.setSelectedItem(tenBill.getDigit());
		this.comboDigitFee.setSelectedItem(tenBill.getDigitFee());

		this.comboRentStartType.setSelectedItem(tenBill.getRentStartType());
		this.pkStartDateLimit.setValue(tenBill.getStartDateLimit());
		this.chkIsFreeContract.setSelected(tenBill.isIsFreeContract());
		this.txtLeaseCount.setValue(tenBill.getLeaseCount());
		this.pkFristLeaseDate.setValue(tenBill.getFristRevDate());
		this.f7BussinessDepartMent.setValue(tenBill.getOrgUnit());
		this.DPickFromMonth.setValue(tenBill.getSecondRevDate());

		if (MoreRoomsTypeEnum.StandardRentSetting.equals(tenBill
				.getMoreRoomsType())) {
			this.contMoreRoomsType.setVisible(true);
			this.comboMoreRoomsType.setSelectedItem(tenBill.getMoreRoomsType());
		}
		this.tblRoom.removeRows();
		this.tblAttachRes.removeRows();
		this.tabMiddle.removeAll();

		addRoomRows(tenBill.getTenancyRoomList());
		addAttachResRows(tenBill.getTenAttachResourceList());

		loadIncreasedRents(tenBill.getIncreasedRents());
		loadFreeRents(tenBill.getRentFrees());

		// add by yangfan
		loadLiquidated(tenBill.getTenLiquidated());

		loadOtherPayList(tenBill);
		// by tim_gao 2010-9-10
		// ������ ״̬ʱ ��������ôӾɵĺ�ͬ����������
		if (TenancyContractTypeEnum.ContinueTenancy
				.equals(this.comboTenancyType.getSelectedItem())
				&& OprtState.ADDNEW.equals(this.getOprtState())) {

			for(int i=0;i<tenBill.getOldTenancyBill()
			.getTenancyRoomList().size();i++){
				tenBill.getOldTenancyBill()
				.getTenancyRoomList().get(i).getDealAmounts().clear();
			}
			loadTblRentSetbyTrans(tenBill.getOldTenancyBill()
					.getTenancyRoomList());
		}

		if (isDynamicStartDate() && this.pkStartDate.getValue() == null) {
			loadDynamicTblRentSet(tenBill.getTenancyRoomList(), tenBill
					.getTenAttachResourceList());
		}
		// ������ôд����Ϊ���ֱ��дelse�����޿����������޺�ͬʱ�����loadTblRentSet��
		else if (this.pkStartDate.getValue() != null
				&& this.pkEndDate.getValue() != null) {

			loadTblRentSet(tenBill.getTenancyRoomList(), tenBill
					.getTenAttachResourceList());
			try {
				reSetRentSetInfo(tenBill.getTenancyRoomList());
			} catch (BOSException e) {
				e.printStackTrace();
			}
		}

		try {
			loadCustomers(tenBill.getTenCustomerList());
		} catch (EASBizException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (BOSException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		setFirstLeaseEndDateVisible();
		setVisibleAboutRentCountType(tenBill.getRentCountType());
		setAgencySourceVisible();
		setToIntegerVisable(tenBill.isIsAutoToInteger());
		setToIntegerFeeVisable(tenBill.isIsAutoToIntegerFee());
		setIsbtnPayList(tenBill.isIsFreeContract());

		this.btnInsert.setEnabled(false);
		this.btnAddPayList.setEnabled(false);
		this.btnDelPayList.setEnabled(false);

		// ת����������ֻͬ��ѡ��̶���ʼ�������ͺ�ͬ
		if ("ADDNEW".equals(this.getOprtState())
				&& (TenancyContractTypeEnum.RejiggerTenancy
						.equals(this.comboTenancyType.getSelectedItem())
						|| TenancyContractTypeEnum.ContinueTenancy
								.equals(this.comboTenancyType.getSelectedItem()) || TenancyContractTypeEnum.PriceChange
						.equals(this.comboTenancyType.getSelectedItem()))) {
			this.comboRentStartType.setEnabled(false);
		}
		if ("ADDNEW".equals(this.getOprtState())
				&& TenancyContractTypeEnum.ChangeName
						.equals(this.comboTenancyType.getSelectedItem())) {
			try {
				this.comboRentStartType.setEnabled(false);
				updatePayListInfo();
			} catch (BOSException e) {
				this.handleException(e);
			}
		} else {
			List leaseList = getLeaseListFromView();
			updatePayList(tenBill.getTenancyRoomList(), tenBill
					.getTenAttachResourceList(), leaseList);// ���ɸ�����ϸ�б�
			updateTotalRent();
		}
		try {
			isFreeConSet(this.chkIsFreeContract.isSelected());
		} catch (BOSException e) {
			this.handleException(e);
		}

		if (this.tblRoom.getRowCount() > 0) {
			this.prmtRentFreeBill.setEnabled(true);
			TenancyRoomEntryInfo entry = (TenancyRoomEntryInfo) this.tblRoom
					.getRow(0).getUserObject();
			EntityViewInfo view = new EntityViewInfo();
			FilterInfo filter = new FilterInfo();
			filter.getFilterItems()
					.add(
							new FilterItemInfo("state",
									FDCBillStateEnum.AUDITTED_VALUE));
			if (this.editData.getId() != null) {
				filter.getFilterItems().add(
						new FilterItemInfo("id",
								"select frentFreeBillId from T_TEN_TenancyBill where fid!='"
										+ this.editData.getId().toString()
										+ "' and frentFreeBillId is not null",
								CompareType.NOTINNER));
			} else {
				filter
						.getFilterItems()
						.add(
								new FilterItemInfo(
										"id",
										"select frentFreeBillId from T_TEN_TenancyBill where frentFreeBillId is not null",
										CompareType.NOTINNER));
			}
			filter.getFilterItems().add(
					new FilterItemInfo("room.id", entry.getRoom().getId()
							.toString()));
			view.setFilter(filter);
			prmtRentFreeBill.setEntityViewInfo(view);
		} else {
			this.prmtRentFreeBill.setEnabled(false);
		}
		loadTotalPayList();
		attachListeners();
		isLoad = false;

		// ���ظ���
		try {
			fillAttachmnetTable();
		} catch (EASBizException e) {
			handleException(e);
		} catch (BOSException e) {
			handleException(e);
		}
		
		updateTotalInfo();
		
		if(ConRentTypeEnum.ZJ.equals(this.cbConRentType.getSelectedItem())){
			this.txtTenPrice.setEnabled(true);
			this.txtTenPrice.setRequired(true);
		}else{
			this.txtTenPrice.setEnabled(false);
			this.txtTenPrice.setRequired(false);
		}
	}

	
//	private void fillDataToRow(TenPriceEntryInfo entry, IRow r) {
//		
//		r.getCell("sellProject").setValue(entry.getSellProject());
//		r.getCell("roomName").setValue(entry.getRoomName());
//		r.getCell("area").setValue(entry.getArea());
//		r.getCell("dayPrice").setValue(entry.getDayPrice());
//		r.getCell("monthPrice").setValue(entry.getMonthPrice());
//		r.getCell("yearPrice").setValue(entry.getYearPrice());
//		r.getCell("maxFreeDay").setValue(entry.getMaxFreeDay());
//		r.getCell("maxLease").setValue(entry.getMaxLease());
//		if(r.getCell("depoist") != null)
//		  r.getCell("depoist").setValue(entry.getDeposit());//deposit
//		if(r.getCell("deposit") != null)
//		  r.getCell("deposit").setValue(entry.getDeposit());
//		r.getCell("id").setValue(entry.getId());
//		r.getCell("id").setUserObject(entry);
//		if(r.getCell("description") != null)
//			  r.getCell("description").setValue(entry.getDescription());
//	}


	private void initTotalPayList() {
		this.tblTotal.checkParsed();
		this.tblTotal.getSelectManager().setSelectMode(
				KDTSelectManager.CELL_SELECT);
		this.tblTotal.setActiveCellStatus(KDTStyleConstants.ACTIVE_CELL_EDIT);
		this.tblTotal.getStyleAttributes().setLocked(true);

		KDFormattedTextField formattedTextField = new KDFormattedTextField(
				KDFormattedTextField.BIGDECIMAL_TYPE);
		formattedTextField.setSupportedEmpty(true);
		formattedTextField.setPrecision(2);
		formattedTextField.setNegatived(false);
		ICellEditor numberEditor = new KDTDefaultCellEditor(formattedTextField);
		this.tblTotal.getColumn("appAmount").setEditor(numberEditor);
		this.tblTotal.getColumn("appAmount").getStyleAttributes()
				.setHorizontalAlign(HorizontalAlignment.RIGHT);
		this.tblTotal.getColumn("appAmount").getStyleAttributes()
				.setNumberFormat(FDCHelper.getNumberFtm(2));

		this.tblTotal.getColumn("actRevAmount").setEditor(numberEditor);
		this.tblTotal.getColumn("actRevAmount").getStyleAttributes()
				.setHorizontalAlign(HorizontalAlignment.RIGHT);
		this.tblTotal.getColumn("actRevAmount").getStyleAttributes()
				.setNumberFormat(FDCHelper.getNumberFtm(2));

		this.tblTotal.getColumn("rate").setEditor(numberEditor);
		this.tblTotal.getColumn("rate").getStyleAttributes()
				.setHorizontalAlign(HorizontalAlignment.RIGHT);
		this.tblTotal.getColumn("rate").getStyleAttributes().setNumberFormat(
				FDCHelper.getNumberFtm(2));

		this.tblTotal.getColumn("appAmountNoTax").setEditor(numberEditor);
		this.tblTotal.getColumn("appAmountNoTax").getStyleAttributes()
				.setHorizontalAlign(HorizontalAlignment.RIGHT);
		this.tblTotal.getColumn("appAmountNoTax").getStyleAttributes()
				.setNumberFormat(FDCHelper.getNumberFtm(2));

		this.tblTotal.getColumn("actRevAmountNoTax").setEditor(numberEditor);
		this.tblTotal.getColumn("actRevAmountNoTax").getStyleAttributes()
				.setHorizontalAlign(HorizontalAlignment.RIGHT);
		this.tblTotal.getColumn("actRevAmountNoTax").getStyleAttributes()
				.setNumberFormat(FDCHelper.getNumberFtm(2));
	}

	// TODO FIXME �ڴ˴����ر����˰��
	private void loadTotalPayList() {
		TenBillOtherPayCollection otherPayList = new TenBillOtherPayCollection();
		for (int i = 0; i < this.editData.getOtherPayList().size(); i++) {
			TenBillOtherPayInfo entry = this.editData.getOtherPayList().get(i);
			otherPayList.add(entry);
		}
		if (this.editData.getTenancyRoomList().size() > 0) {
			for (int i = 0; i < this.editData.getTenancyRoomList().get(0)
					.getPayList().size(); i++) {
				TenancyRoomPayListEntryInfo tenOtherInfo = this.editData
						.getTenancyRoomList().get(0).getRoomPayList().get(i);
				TenBillOtherPayInfo entry = new TenBillOtherPayInfo();
				entry.setMoneyDefine(tenOtherInfo.getMoneyDefine());
				entry.setAppAmount(tenOtherInfo.getAppAmount());
				entry.setActRevAmount(tenOtherInfo.getActRevAmount());
				entry.setHasRefundmentAmount(tenOtherInfo.getHasRefundmentAmount());
				entry.setHasToFeeAmount(tenOtherInfo.getHasToFeeAmount());
				entry.setHasTransferredAmount(tenOtherInfo.getHasTransferredAmount());
				entry.setHasAdjustedAmount(tenOtherInfo.getHasAdjustedAmount());
				
				otherPayList.add(entry);
			}
		}
		CRMHelper.sortCollection(otherPayList, "moneyDefine.number", true);
		
		TenancyPriceEntryCollection priceEntry = editData.getTenPriceEntry();
		TenancyPriceEntryInfo priceEntryInfo = null;
		this.tblTenPrice.removeRows();
		int size = priceEntry.size();
		IRow r = null;
		for(int i=0;i<size;i++){
			priceEntryInfo = priceEntry.get(i);
			r = this.tblTenPrice.addRow();
			r.setUserObject(priceEntryInfo);
//			this.tblTenPrice.setUserObject(priceEntryInfo);
			fillDataToRow(priceEntryInfo.getTenPrice(), r);
			
		}
		

		this.tblTotal.removeRows();
		Map addRow = new HashMap();
		for (int i = 0; i < otherPayList.size(); i++) {
			TenBillOtherPayInfo entry = otherPayList.get(i);
			if (entry.getMoneyDefine() != null) {
				BigDecimal rate = null;
				try {
					rate = MoneyDefineFactory.getRemoteInstance()
							.getMoneyDefineInfo(
									new ObjectUuidPK(entry.getMoneyDefine()
											.getId())).getRate();
				} catch (EASBizException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (BOSException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if (addRow.containsKey(entry.getMoneyDefine().getNumber())) {
					IRow row = (IRow) addRow.get(entry.getMoneyDefine()
							.getNumber());
					row.getCell("appAmount").setValue(
							FDCHelper.add(row.getCell("appAmount").getValue(),
									entry.getAppAmount()));
					row.getCell("actRevAmount").setValue(
							FDCHelper.add(row.getCell("actRevAmount")
									.getValue(), entry.getAllRemainAmount()));

					BigDecimal div = FDCHelper.add(new BigDecimal(1), FDCHelper
							.divide(rate, new BigDecimal(100), 4,
									BigDecimal.ROUND_HALF_UP));
					row.getCell("appAmountNoTax").setValue(
							FDCHelper.divide(row.getCell("appAmount")
									.getValue(), div, 2,
									BigDecimal.ROUND_HALF_UP));
					row.getCell("actRevAmountNoTax").setValue(
							FDCHelper.divide(row.getCell("actRevAmount")
									.getValue(), div, 2,
									BigDecimal.ROUND_HALF_UP));
				} else {
					IRow row = this.tblTotal.addRow();
					row.getCell("moneyDefine").setValue(
							entry.getMoneyDefine().getName());
					row.getCell("rate").setValue(rate);
					row.getCell("appAmount").setValue(entry.getAppAmount());
					row.getCell("actRevAmount").setValue(
							entry.getAllRemainAmount());

					BigDecimal div = FDCHelper.add(new BigDecimal(1), FDCHelper
							.divide(rate, new BigDecimal(100), 4,
									BigDecimal.ROUND_HALF_UP));
					row.getCell("appAmountNoTax").setValue(
							FDCHelper.divide(entry.getAppAmount(), div, 2,
									BigDecimal.ROUND_HALF_UP));
					row.getCell("actRevAmountNoTax").setValue(
							FDCHelper.divide(entry.getAllRemainAmount(), div, 2,
									BigDecimal.ROUND_HALF_UP));
					addRow.put(entry.getMoneyDefine().getNumber(), row);
				}
			}
		}
		CRMClientHelper.getFootRow(this.tblTotal, new String[] { "appAmount",
				"actRevAmount", "appAmountNoTax", "actRevAmountNoTax" });
	}

	/**
	 * @author tim_gao
	 * @see �����ⷿ�����У�����Ѻ��� ��Ϣ
	 * @param TenancyRoomEntryCollection
	 * 
	 */
	public void loadTblRentSetbyTrans(TenancyRoomEntryCollection tenRooms) {
		Iterator it = tenRooms.iterator();
		this.tblRentSet.removeRows();
		while (it.hasNext()) {

			TenancyRoomEntryInfo tenRoomEntryInfo = (TenancyRoomEntryInfo) it
					.next();
			IRow row = this.tblRentSet.addRow();
			row.setUserObject(tenRoomEntryInfo);
			row.getCell(C_ROOM_ROOM)
					.setValue(tenRoomEntryInfo.getRoomLongNum());

			int sum = tenRoomEntryInfo.getDealAmounts().size();
			if (sum > 0) {
				for (int i = 0; i < sum; i++) {

					DealAmountEntryInfo dealMoneyInfo = tenRoomEntryInfo
							.getDealAmounts().get(i);
					// �ж�Ŀ¼��Ѻ������ʾ
					if (row.getCell(dealMoneyInfo.getMoneyDefine().getNumber()) != null) {
						row.getCell(dealMoneyInfo.getMoneyDefine().getNumber())
								.setValue(dealMoneyInfo.getAmount());
					}

				}
			}

			row.getCell(C_RENT_FIRST_RENT).setValue(
					tenRoomEntryInfo.getFirstPayAmount());
			row.getCell(C_RENT_TENANCY_MODEL).setValue(
					tenRoomEntryInfo.getTenancyModel());
			row.getCell(C_RENT_RENT_TYPE).setValue(
					tenRoomEntryInfo.getDealRentType());

		}

	}

	private void loadOtherPayList(TenancyBillInfo info) {
		this.tblOtherPayList.removeRows();
		TenBillOtherPayCollection otherPayList = info.getOtherPayList();
		CRMHelper.sortCollection(otherPayList, "leaseSeq", true);
		// Map payMap = new TreeMap();
		for (int i = 0; i < otherPayList.size(); i++) {
			TenBillOtherPayInfo tenOtherInfo = otherPayList.get(i);
			addOtherPayListEntryRow(tenOtherInfo);
			// payMap.put(new Integer(tenOtherInfo.getSeq()), tenOtherInfo);
		}

		// Set set = payMap.keySet();
		// for (Iterator iter = set.iterator(); iter.hasNext();) {
		// Integer key = (Integer) iter.next();
		// TenBillOtherPayInfo tenOtherInfo = (TenBillOtherPayInfo)
		// payMap.get(key);
		// addOtherPayListEntryRow(tenOtherInfo);
		// }
	}

	private void loadFreeRents(RentFreeEntryCollection rentFrees) {
		this.tblFree.removeRows();
		this.tblFreeFeeInfo.checkParsed();
		this.tblFreeFeeInfo.removeRows();
		this.txtFreeFeeInfo.setText("");
		StringBuffer strByFreeInfo = new StringBuffer();
		BigDecimal freeDays = FDCHelper.ZERO;
		for (int i = 0; i < rentFrees.size(); i++) {
			RentFreeEntryInfo rentFree = rentFrees.get(i);
			strByFreeInfo.append(rentFree.getFreeStartDate());
			strByFreeInfo.append("��");
			strByFreeInfo.append(rentFree.getFreeEndDate());
			strByFreeInfo.append("(��");
			long diffDay = FDCDateHelper.dateDiff(rentFree.getFreeStartDate(),
					rentFree.getFreeEndDate()) / 86400000;
			freeDays = FDCHelper.add(freeDays, diffDay);
			strByFreeInfo.append(diffDay + "��)");
			if (i > 0 && i < rentFree.size()) {
				strByFreeInfo.append(";");
			}
			IRow row = this.tblFree.addRow();
			row.setUserObject(rentFree);

			row.getCell(C_FREE_START_DATE)
					.setValue(rentFree.getFreeStartDate());
			row.getCell(C_FREE_END_DATE).setValue(rentFree.getFreeEndDate());
			row.getCell(C_FREE_TENANCY_TYPE).setValue(
					rentFree.getFreeTenancyType());
			row.getCell(C_FREE_DES).setValue(rentFree.getDescription());

			row = tblFreeFeeInfo.addRow();
			row.setUserObject(rentFree);

			row.getCell("freeStartDate").setValue(rentFree.getFreeStartDate());
			row.getCell("freeEndDate").setValue(rentFree.getFreeEndDate());
			row.getCell("freeType").setValue(rentFree.getFreeTenancyType());
			row.getCell(C_FREE_DES).setValue(rentFree.getDescription());
		}

		this.txtFreeFeeInfo.setText(strByFreeInfo.toString());
		this.txtMaxFreeDay.setUserObject(freeDays);
	}

	private void loadIncreasedRents(IncreasedRentEntryCollection increaseds) {
		this.tblIncrease.removeRows();
		this.txtIncreament.setText("");
		this.tblIncreament.checkParsed();
		this.tblIncreament.removeRows();
		StringBuffer strByIncrementInfo = new StringBuffer();
		BigDecimal freeDays = FDCHelper.ZERO;
		for (int i = 0; i < increaseds.size(); i++) {
			IncreasedRentEntryInfo increased = increaseds.get(i);
			strByIncrementInfo.append(increased.getIncreaseDate());
			strByIncrementInfo.append("����");
			strByIncrementInfo.append(increased.getValue());
			if (i > 0 && i < increaseds.size()) {
				strByIncrementInfo.append(";");
			}
			IRow row = this.tblIncrease.addRow();
			row.setUserObject(increased);

			row.getCell(C_INC_INCREASE_DATE).setValue(
					increased.getIncreaseDate());
			row.getCell(C_INC_INCREASE_TYPE).setValue(
					increased.getIncreaseType());
			row.getCell(C_INC_VALUE).setValue(increased.getValue());
			row.getCell(C_INC_INCREASESTYLE).setValue(
					increased.getIncreaseStyle());

			row = this.tblIncreament.addRow();
			row.setUserObject(increased);

			row.getCell("increamentDate").setValue(increased.getIncreaseDate());
			row.getCell("increamentType").setValue(increased.getIncreaseType());
			row.getCell("value").setValue(increased.getValue());
			row.getCell("method").setValue(increased.getIncreaseStyle());
		}

		this.txtIncreament.setText(strByIncrementInfo.toString());
	}

	/**
	 * �������޿ͻ���Ϣ
	 * 
	 * @param tenCustomerList
	 * @throws BOSException 
	 * @throws EASBizException 
	 */
	private void loadCustomers(TenancyCustomerEntryCollection tenCustomerList) throws BOSException, EASBizException {
		// modified by weiqiang_chen PBG053457
		ICustomerEntryBrand ieb = CustomerEntryBrandFactory.getRemoteInstance();
		SelectorItemCollection sic = new SelectorItemCollection();
		sic.add("*");
		sic.add("brand.*");
		
		this.tblCustomer.removeRows();
		for (int i = 0; i < tenCustomerList.size(); i++) {
			TenancyCustomerEntryInfo tenCustomer = tenCustomerList.get(i);
			IRow row = this.tblCustomer.addRow();
			row.setHeight(20);
			row.setUserObject(tenCustomer);
			row.getCell(C_CUS_PROPERTY_PERCENT).setValue(
					tenCustomer.getPropertyPercent());
			row.getCell(C_CUS_DES).setValue(tenCustomer.getDescription());
			FDCCustomerInfo customer = tenCustomer.getFdcCustomer();
			if (customer != null) {
				row.getCell(C_CUS_CUSTOMER).setValue(customer);
				row.getCell(C_CUS_POSTALCODE)
						.setValue(customer.getPostalcode());
				row.getCell(C_CUS_PHONE).setValue(customer.getPhone());
				row.getCell(C_CUS_CERTIFICATE_NAME).setValue(
						customer.getCertificateName());
				row.getCell(C_CUS_CERTIFICATE_NUMBER).setValue(
						customer.getCertificateNumber());
				row.getCell(C_CUS_MAIL_ADDRESS).setValue(
						customer.getMailAddress());
				row.getCell(C_CUS_BOOK_DATE).setValue(customer.getCreateTime());
				
			}
			CustomerEntryBrandCollection cbc = tenCustomer.getBrandEntry();
			BrandCollection bc = new BrandCollection();
			CustomerEntryBrandInfo cebi = null;
			StringBuffer str = new StringBuffer();
			
			for(Iterator it = cbc.iterator();it.hasNext();){
				cebi = (CustomerEntryBrandInfo) it.next();
				if(cebi.getBrand() == null){
					cebi = (CustomerEntryBrandInfo) ieb.getValue(new ObjectUuidPK(cebi.getId()), sic);
				}
				bc.add(cebi.getBrand());
				str.append(cebi.getBrand().getName()+";");
			}
			if(bc.size()>0){
				row.getCell("brand").setUserObject(bc);
				row.getCell("brand").setValue(str.toString());
			}
				
		}
	}

	/**
	 * ����ΥԼ����㷽������ add by yangfan
	 */
	private void loadLiquidated(TenLiquidatedCollection collection) {
		this.tblLiquidated.removeRows();
		TreeMap map = new TreeMap();
		for (int i = 0; i < collection.size(); i++) {
			TenLiquidatedInfo liinfo = collection.get(i);
			map.put(liinfo.getMoneyDefine().getNumber(), liinfo);
		}
		Iterator it = map.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry entry = (Map.Entry) it.next();
			IRow row = this.tblLiquidated.addRow();
			TenLiquidatedInfo liq = (TenLiquidatedInfo) entry.getValue();
			row.setUserObject(liq);
			row.getCell("moneyDefine").setValue(liq.getMoneyDefine());
			row.getCell("liquidated").setValue(liq.getLiquidated());
		}

	}

	/** ���ݵ��ݵ�״̬������һЩ�ؼ���״̬ */
	private void initControlByBillState() {
		TenancyBillInfo ten = this.editData;
		TenancyBillStateEnum state = ten.getTenancyState();
		if (state == null || TenancyBillStateEnum.Saved.equals(state)) {
			this.actionSave.setEnabled(true);
		} else {
			this.actionSave.setEnabled(false);
		}

		if (state != null && !TenancyBillStateEnum.Saved.equals(state)
				&& !TenancyBillStateEnum.Submitted.equals(state)) {
			this.actionEdit.setEnabled(false);
		} else {
			if (this.getOprtState().equals("VIEW")) {
				this.actionEdit.setEnabled(true);
			}
		}

		TenancyContractTypeEnum tenType = this.getCurrentTenancyType();

		// ����
		if (TenancyContractTypeEnum.NewTenancy.equals(tenType)) {
			this.txtChangeDes.setEnabled(false);
		}

		// ����
		if (TenancyContractTypeEnum.ContinueTenancy.equals(tenType)) {
			this.pkStartDate.setEnabled(false);// ��ʼ����ֻ����ԭ��ͬ��������
			this.tblCustomer.getStyleAttributes().setLocked(true);
			this.actionAddCustomer.setEnabled(false);
			this.btnNewCustomer.setEnabled(false);
			this.actionRemoveCustomer.setEnabled(false);

		}

		// ����
		if (TenancyContractTypeEnum.RejiggerTenancy.equals(tenType)) {
			this.tblCustomer.getStyleAttributes().setLocked(true);
			this.actionAddCustomer.setEnabled(false);
			this.btnNewCustomer.setEnabled(false);
			this.actionRemoveCustomer.setEnabled(false);
		}

		// ת��
		if (TenancyContractTypeEnum.ChangeName.equals(tenType)) {
			this.tblRoom.getStyleAttributes().setLocked(true);
			this.tblAttachRes.getStyleAttributes().setLocked(true);
			this.actionAddRoom.setEnabled(false);
			this.actionRemoveRoom.setEnabled(false);
			this.btnAddAttachRes.setEnabled(false);
			this.btnRemoveAttachRes.setEnabled(false);
			this.comboChargeDateType.setEnabled(false);
			this.spinLeaseTime.setEnabled(false);
			this.spinChargeOffsetDays.setEnabled(false);
		}

		// �۸���
		if (TenancyContractTypeEnum.PriceChange.equals(tenType)) {
			this.tblRoom.getStyleAttributes().setLocked(true);
			this.tblAttachRes.getStyleAttributes().setLocked(true);
			this.tblCustomer.getStyleAttributes().setLocked(true);
			this.actionAddCustomer.setEnabled(false);
			this.btnNewCustomer.setEnabled(false);
			this.actionRemoveCustomer.setEnabled(false);
			this.actionAddRoom.setEnabled(false);
			this.actionRemoveRoom.setEnabled(false);
			this.btnAddAttachRes.setEnabled(false);
			this.btnRemoveAttachRes.setEnabled(false);
			this.comboChargeDateType.setEnabled(false);
			this.spinLeaseTime.setEnabled(false);
			this.spinChargeOffsetDays.setEnabled(false);
			this.comboFirstLeaseType.setEnabled(false);

		}

		// �����ת��ʱ������¼��ۿ�
		if (TenancyContractTypeEnum.RejiggerTenancy.equals(tenType)
				|| TenancyContractTypeEnum.ChangeName.equals(tenType)) {
			this.txtDeductionAmount.setEnabled(true);
		} else {
			this.txtDeductionAmount.setEnabled(false);
		}
		// TODO �ۿ�����޸�Ϊ��Է���ۿĿǰ�ݲ�ʵ�ֿۿ��
		this.contDeductionAmount.setVisible(false);
	}

	/** ���ݲ���״̬��������ؿؼ���״̬ */
	private void initControlByOprtState() {
		if (this.getOprtState().equals("VIEW")
				|| this.getOprtState().equals("FINDVIEW")) {
			this.actionAddCustomer.setEnabled(false);
			this.btnNewCustomer.setEnabled(false);
			this.actionAddRoom.setEnabled(false);
			this.actionRemoveCustomer.setEnabled(false);
			this.actionRemoveRoom.setEnabled(false);
			this.btnAddAttachRes.setEnabled(false);
			this.btnRemoveAttachRes.setEnabled(false);

			this.btnAddOtherPaylist.setEnabled(false);
			this.btnDelOtherPaylist.setEnabled(false);

			this.btnAddIncrease.setEnabled(false);
			this.btnRmIncrease.setEnabled(false);
			this.btnAddFree.setEnabled(false);
			this.btnRmFree.setEnabled(false);

			// ֻ�����ڲ쿴״̬�²��ܽ���Ѻ���ת
			this.actionCarryForward.setEnabled(true);

			for (int i = 0; i < tabMiddle.getTabCount(); i++) {
				TenancyPropertyUI ui = (TenancyPropertyUI) this.tabMiddle
						.getComponentAt(i);
				ui.setEditable(false);
			}
		} else {
			this.actionAddCustomer.setEnabled(true);
			this.btnNewCustomer.setEnabled(true);
			this.actionRemoveCustomer.setEnabled(true);
			this.actionAddRoom.setEnabled(true);
			this.actionRemoveRoom.setEnabled(true);
			this.btnAddAttachRes.setEnabled(true);
			this.btnRemoveAttachRes.setEnabled(true);

			this.btnAddOtherPaylist.setEnabled(isEdit);
			this.btnDelOtherPaylist.setEnabled(isEdit);
			this.tblOtherPayList.setEnabled(isEdit);

			// this.btnAddIncrease.setEnabled(true);
			// this.btnRmIncrease.setEnabled(true);
			this.btnAddFree.setEnabled(true);
			this.btnRmFree.setEnabled(true);

			this.actionCarryForward.setEnabled(false);

			for (int i = 0; i < tabMiddle.getTabCount(); i++) {
				TenancyPropertyUI ui = (TenancyPropertyUI) this.tabMiddle
						.getComponentAt(i);
				ui.setEditable(true);
			}
		}
		if (this.getOprtState().equals("EDIT")) {
			this.f7SincerObligate.setEnabled(false);
		}
	}

	public void onLoad() throws Exception {
		isNeedSelectBaseLine = FDCUtils.getBooleanValue4FDCParamByKey(null, SysContext.getSysContext().getCurrentOrgUnit().getId().toString(), "ISMUSTSELECTBASELINE");
		isNeedSelectBrand = FDCUtils.getBooleanValue4FDCParamByKey(null, SysContext.getSysContext().getCurrentOrgUnit().getId().toString(), "ISMUSTINPUTBRAND");
		
		oldState = this.oprtState;
		if (!saleOrg.isIsBizUnit()) {
			this.actionEdit.setEnabled(false);
		}

		initControl();
		initRoomTable();
		initAttachResTable();
		initF7Bussinss();
		// //TODO ��ʱ����������Դ
		// if(tabbedPaneRoom.getTabCount() == 2){
		// this.tabbedPaneRoom.remove(1);
		// }
		tabbedPaneRoom.setVisible(false);
		initCustomeTable();
		initRentSetTable();
		initPayListTable();
		initOtherPayList();
		// add by yangfan
		initLiquidatedTable();
		initTotalPayList();
		this.tabbedPaneContract.setSize(new Dimension(1013, 720));
		this.tabbedPaneContract.setPreferredSize(new Dimension(1013, 720));
		// this.tabbedPaneContract.setMinimumSize(new Dimension(1013,720));
		// this.kDPanel4.setSize(new Dimension(1013,720));
		// this.kDPanel4.setPreferredSize(new Dimension(1013,600720));
		// this.kDPanel4.setMinimumSize(new Dimension(1013,720));
		// this.kDPanel2.setSize(new Dimension(1013,720));
		// this.kDPanel2.setPreferredSize(new Dimension(1013,720));
		// this.kDPanel2.setMinimumSize(new Dimension(1013,720));
		tabbedPaneContract.remove(panelContractInfo);
		tabbedPaneContract.remove(kDPaneLongContract);
		tabbedPaneContract.remove(panelCommissionSetting);
		btnViewRoomInfo.setVisible(false);
		btnViewCustInfo.setVisible(false);
		btnNewCustomer.setVisible(false);

		this.contfirstTermTenAmo.setVisible(false);
		this.contDepositAmount.setVisible(false);
		this.contRemainDepositAmount.setVisible(false);

		this.contStartDateLimit.setVisible(false);
		this.contMoreRoomsType.setVisible(false);

		super.onLoad();
		this.actionAuditResult.setVisible(false);
		this.actionAttachment.setVisible(false);
		this.txtLeaseCount.setDataType(0);
		initControlByOprtState();
		initControlByBillState();

		setSincerFilter();
		this.storeFields();
		initOldData(this.editData);
		this.actionCarryForward.setVisible(false);

		this.actionPrint.setVisible(true);
		this.actionPrintPreview.setVisible(true);
		this.actionPrint.setEnabled(true);
		this.actionPrintPreview.setEnabled(true);

		this.actionAudit.setVisible(true);
		actionAudit.setEnabled(true);
		if (!saleOrg.isIsBizUnit()) {
			this.actionAddNew.setEnabled(false);
			this.actionAudit.setEnabled(false);
			this.actionUnAudit.setEnabled(false);
		}
		this.txtName.setMaxLength(80);
		this.txtNumber.setMaxLength(80);
		this.actionAttachment.setVisible(true);
		this.actionAuditResult.setVisible(true);
		this.actionPre.setVisible(false);
		this.actionNext.setVisible(false);
		this.actionFirst.setVisible(false);
		this.actionLast.setVisible(false);
		// ���ɽ���� ,�ܳɽ������� ���� 2010.12.14 xin_wang
		this.contLateFeeAmount.setVisible(false);
		// ��������Ŀ��������ȷ���Ƿ���ʾ��ҵҳǩ 2010.12.14 xin_wang
		SellProjectInfo sellProject = (SellProjectInfo) this.f7SellProject
				.getValue();
		if (sellProject != null) {
			if (!sellProject.isIsForPPM()) {
				this.tabbedPaneContract.remove(panelProperty);
			}
		}
		// ���ݲ�ͬ�������������� ��ʾ��ͬ�Ŀؼ� 2010.12.15 xin_wang
		if (ChargeDateTypeEnum.FixedDate
				.equals((ChargeDateTypeEnum) comboChargeDateType
						.getSelectedItem())) {
			this.contFromMonth.setVisible(true);
			this.contChargeOffsetDays.setVisible(false);
		} else {
			this.contFromMonth.setVisible(false);
			this.contChargeOffsetDays.setVisible(true);
		}
		// add by yangfan
		initLiquidatedTable();
		this.cbRateDate.setEnabled(false);
		this.cbReliefDate.setEnabled(false);
		this.cbStandardDate.setEnabled(false);
		this.cbRateDate.setSelectedItem(DateEnum.DAY);
		this.cbReliefDate.setSelectedItem(DateEnum.DAY);
		this.cbStandardDate.setSelectedItem(DateEnum.DAY);

		this.txtStandard.setPrecision(0);
		this.txtStandard.setRemoveingZeroInDispaly(false);
		this.txtStandard.setRemoveingZeroInEdit(false);
		this.txtStandard.setNegatived(false);
		this.txtStandard.setHorizontalAlignment(JTextField.RIGHT);
		this.txtStandard.setSupportedEmpty(true);

		this.txtRelief.setPrecision(0);
		this.txtRelief.setRemoveingZeroInDispaly(false);
		this.txtRelief.setRemoveingZeroInEdit(false);
		this.txtRelief.setNegatived(false);
		this.txtRelief.setHorizontalAlignment(JTextField.RIGHT);
		this.txtRelief.setSupportedEmpty(true);

		this.txtRate.setRemoveingZeroInDispaly(false);
		this.txtRate.setRemoveingZeroInEdit(false);
		this.txtRate.setNegatived(false);
		this.txtRate.setHorizontalAlignment(2);
		this.txtRate.setHorizontalAlignment(JTextField.RIGHT);
		this.txtRate.setSupportedEmpty(true);

		txtRate.setRequired(true);
		txtRelief.setRequired(true);
		txtStandard.setRequired(true);

		if (this.chkIsMDLiquidated.getSelected() == 32) {
			this.panLiquidated.setVisible(false);
			this.panMDLiquidated.setVisible(true);
		} else {
			this.panLiquidated.setVisible(true);
			this.panMDLiquidated.setVisible(false);
		}
		this.panMDLiquidated.setBounds(26, 49, 490, 355);
		setContMoreRoomsTypeVisiable();

		KDBizPromptBox f7Customer = new KDBizPromptBox();
		f7Customer
				.setQueryInfo("com.kingdee.eas.fdc.sellhouse.app.CustomerQuery");
		f7Customer.setEntityViewInfo(CommerceHelper.getPermitCustomerView(
				this.editData.getSellProject(), userInfo));
		f7Customer.setEditable(true);
		f7Customer.setDisplayFormat("$name$");
		f7Customer.setEditFormat("$number$");
		f7Customer.setCommitFormat("$number$");
		ICellEditor f7Editor = new KDTDefaultCellEditor(f7Customer);
		this.tblCustomer.getColumn(C_CUS_CUSTOMER).setEditor(f7Editor);

		this.actionAddCollectProtocol.setVisible(false);

		this.txtName.setEnabled(false);

		this.tabbedPaneContract.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				if (tabbedPaneContract.getSelectedIndex() == 0) {
					// storePayList();
//					storeOtherPayList();
//					loadTotalPayList();
				}
			}
		});

		if ((TenancyBillStateEnum.Audited.equals(this.editData
				.getTenancyState()) || TenancyBillStateEnum.Executing
				.equals(this.editData.getTenancyState()))
				&& this.oprtState.equals(OprtState.VIEW)) {
			this.actionAdjust.setEnabled(true);
			this.tblPayList.setEditable(true);
			for (int i = 0; i < this.tblPayList.getRowCount(); i++) {
				IRow row = this.tblPayList.getRow(i);
				IRow nextRow = this.tblPayList.getRow(i + 1);
				for (int k = 0; k < this.tblPayList.getColumnCount(); k++) {
					// if(this.tblPayList.getColumnKey(k).equals(
					// C_PAYS_APP_PAY_DATE)
					//||this.tblPayList.getColumnKey(k).equals(C_PAYS_START_DATE
					// )
					//||this.tblPayList.getColumnKey(k).equals(C_PAYS_END_DATE))
					// {
					//if(nextRow==null||nextRow.getTreeLevel()>=row.getTreeLevel
					// ()){
					// row.getCell(k).getStyleAttributes().setLocked(false);
					// }else{
					// row.getCell(k).getStyleAttributes().setLocked(true);
					// }
					// }else
					if (this.tblPayList.getColumnKey(k).indexOf(
							POSTFIX_C_PAYS_APP_AMOUNT) >= 0) {
						if (nextRow == null
								|| nextRow.getTreeLevel() <= row.getTreeLevel()) {
							row.getCell(k).getStyleAttributes()
									.setLocked(false);
						} else {
							row.getCell(k).getStyleAttributes().setLocked(true);
						}
					} else {
						row.getCell(k).getStyleAttributes().setLocked(true);
					}
				}
			}
			if (isEdit) {
				this.tblOtherPayList.setEditable(true);
				for (int k = 0; k < this.tblOtherPayList.getColumnCount(); k++) {
					if (
					// this.tblOtherPayList.getColumnKey(k).equals("appDate")
					//||this.tblOtherPayList.getColumnKey(k).equals("startDate")
					// ||this.tblOtherPayList.getColumnKey(k).equals("endDate")
					// ||
					this.tblOtherPayList.getColumnKey(k).equals("appAmount")) {
						this.tblOtherPayList.getColumn(k).getStyleAttributes()
								.setLocked(false);
					} else {
						this.tblOtherPayList.getColumn(k).getStyleAttributes()
								.setLocked(true);
					}
				}
			}
		} else {
			this.actionAdjust.setEnabled(false);
		}

		this.tblAttachement.checkParsed();
		KDWorkButton btnAttachment = new KDWorkButton();

		this.actionAttachment.putValue("SmallIcon", EASResource
				.getIcon("imgTbtn_affixmanage"));
		btnAttachment = (KDWorkButton) this.contAttachment
				.add(this.actionAttachment);
		btnAttachment.setText("��������");
		btnAttachment.setSize(new Dimension(140, 19));

		this.tblTotal.getColumn("rate").getStyleAttributes().setHided(true);
		this.tblTotal.getColumn("appAmountNoTax").getStyleAttributes()
				.setHided(true);
		this.tblTotal.getColumn("actRevAmountNoTax").getStyleAttributes()
				.setHided(true);
		
		 this.cbConRentType.addItemListener(new java.awt.event.ItemListener() {
	            public void itemStateChanged(java.awt.event.ItemEvent e) {
	                try {
	                	cbConRentType_itemStateChanged(e);
	                } catch (Exception exc) {
	                    handUIException(exc);
	                } finally {
	                }
	            }
	        });
		 
		 this.tblTenPrice.setEnabled(false);
	}
	protected void cbConRentType_itemStateChanged(ItemEvent e) throws Exception {
		if(ConRentTypeEnum.ZJ.equals(this.cbConRentType.getSelectedItem())){
			this.txtTenPrice.setEnabled(true);
			this.txtTenPrice.setRequired(true);
		}else{
			this.txtTenPrice.setEnabled(false);
			this.txtTenPrice.setRequired(false);
			this.txtTenPrice.setValue(null);
		}
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
			filter.getFilterItems().add(
					new FilterItemInfo("attachment.beizhu", "1",
							CompareType.NOTEQUALS));
			filter.getFilterItems().add(
					new FilterItemInfo("attachment.beizhu", null,
							CompareType.EQUALS));
			// //��Ӳ���Э��
			// filter.getFilterItems().add(new FilterItemInfo("boID",
			// this.editData.getAgreementID()));
			//			
			// filter.getFilterItems().add(new
			// FilterItemInfo("assoType","��׼��ͬ",CompareType.NOTEQUALS));
			filter.setMaskString("#0 and (#1 or #2)");
			EntityViewInfo evi = new EntityViewInfo();
			evi.getSorter().add(new SorterItemInfo("boID"));
			evi.getSorter().add(new SorterItemInfo("attachment.name"));
			evi.setFilter(filter);
			evi.setSelector(sic);
			BoAttchAssoCollection cols = null;
			try {
				cols = BoAttchAssoFactory.getRemoteInstance()
						.getBoAttchAssoCollection(evi);
			} catch (BOSException e) {
				e.printStackTrace();
			}
			boolean flag = false;
			if (cols != null && cols.size() > 0) {
				for (Iterator it = cols.iterator(); it.hasNext();) {
					BoAttchAssoInfo boaInfo = (BoAttchAssoInfo) it.next();
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

	protected void tblAttachement_tableClicked(KDTMouseEvent e)
			throws Exception {
		if (e.getType() == 1 && e.getButton() == 1 && e.getClickCount() == 2) {
			IRow row = tblAttachement.getRow(e.getRowIndex());
			getFileGetter();
			Object selectObj = row.getCell("id").getValue();
			if (selectObj != null) {
				String attachId = selectObj.toString();
				fileGetter.viewAttachment(attachId);
			}

		}
	}

	private FileGetter fileGetter;

	private FileGetter getFileGetter() throws Exception {
		if (fileGetter == null)
			fileGetter = new FileGetter((IAttachment) AttachmentFactory
					.getRemoteInstance(), AttachmentFtpFacadeFactory
					.getRemoteInstance());
		return fileGetter;
	}

	private void initF7Bussinss() {
		this.f7BussinessDepartMent
				.setQueryInfo("com.kingdee.eas.basedata.org.app.OUQuery");
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("isSaleOrgUnit", "1"));
		filter.getFilterItems().add(
				new FilterItemInfo("partSale.isBizUnit", "1"));
		view.setFilter(filter);
		this.f7BussinessDepartMent.setEntityViewInfo(view);

	}

	protected void pkTenancyDate_dataChanged(DataChangeEvent e)
			throws Exception {
		// updatePayListInfo();
	}

	protected void pkFristLeaseDate_dataChanged(DataChangeEvent e)
			throws Exception {
		updatePayListInfo();
	}

	private void initPayListTable() {
		FDCTableHelper.disableDelete(this.tblPayList);
		afterPressDeleteButton();
		this.tblPayList.getSelectManager().setSelectMode(
				KDTSelectManager.ROW_SELECT);
		this.tblPayList.setEditable(false);
	}

	private KDWorkButton btnAddIncrease = null;
	private KDWorkButton btnRmIncrease = null;
	private KDWorkButton btnAddFree = null;
	private KDWorkButton btnRmFree = null;

	private void initRentSetTable() throws BOSException {
		this.tblIncrease.checkParsed();
		this.tblFree.checkParsed();
		// this.tblRentSet.checkParsed();
		// this.tblRentSet.getColumn("actdayprice").getStyleAttributes().
		// setHorizontalAlign(HorizontalAlignment.RIGHT);
		// this.tblRentSet.getColumn("actdayprice").getStyleAttributes().
		// setNumberFormat(FDCHelper.getNumberFtm(2));
		//this.tblRentSet.getColumn("actdayprice").getStyleAttributes().setHided
		// (true);
		//this.tblIncrease.setActiveCellStatus(KDTStyleConstants.ACTIVE_CELL_EDIT
		// );
		// this.tblFree.setActiveCellStatus(KDTStyleConstants.ACTIVE_CELL_EDIT);
		//this.tblRentSet.setActiveCellStatus(KDTStyleConstants.ACTIVE_CELL_EDIT
		// );
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(
				new FilterItemInfo("moneyType", "DepositAmount"));
		filter.getFilterItems()
				.add(new FilterItemInfo("sysType", "TenancySys"));
		view.setFilter(filter);
		monDefineColl = MoneyDefineFactory.getRemoteInstance()
				.getMoneyDefineCollection(view);
		// ��ʼ����������ΪѺ��
		if (monDefineColl.size() > 1) {
			for (int i = 0; i < monDefineColl.size(); i++) {
				MoneyDefineInfo moneyInfo = monDefineColl.get(i);
				if (i == 0) {
					IColumn columnMoney = this.tblRentSet.getColumn("deposit");
					columnMoney.setKey(moneyInfo.getNumber());
					IRow headRow0 = this.tblRentSet.getHeadRow(0);
					IRow headRow1 = this.tblRentSet.getHeadRow(1);
					headRow0.getCell(moneyInfo.getNumber()).setValue("Ѻ��");
					headRow1.getCell(moneyInfo.getNumber()).setValue(moneyInfo);
				} else {
					IColumn columnMoney = this.tblRentSet.addColumn(2);
					columnMoney.setKey(moneyInfo.getNumber());
					IRow headRow0 = this.tblRentSet.getHeadRow(0);
					IRow headRow1 = this.tblRentSet.getHeadRow(1);
					headRow0.getCell(moneyInfo.getNumber()).setValue("Ѻ��");
					headRow1.getCell(moneyInfo.getNumber()).setValue(moneyInfo);
				}
			}
		} else if (monDefineColl.size() == 1) {
			IColumn columnMoney = this.tblRentSet.getColumn("deposit");
			columnMoney.setKey(((MoneyDefineInfo) monDefineColl.getObject(0))
					.getNumber());// setKey
		}

		this.tblRentSet.getHeadMergeManager().mergeBlock(0, 0, 1,
				this.tblRentSet.getColumnCount() - 1,
				KDTMergeManager.FREE_MERGE);

		this.tblRentSet.getColumn(C_RENT_ROOM).getStyleAttributes().setLocked(
				true);

		for (int i = 0; i < monDefineColl.size(); i++) {
			MoneyDefineInfo moneyInfo = monDefineColl.get(i);
			this.tblRentSet.getColumn(moneyInfo.getNumber()).setEditor(
					createFormattedCellEditor());
			this.tblRentSet.getColumn(moneyInfo.getNumber())
					.getStyleAttributes().setHorizontalAlign(
							HorizontalAlignment.RIGHT);
			this.tblRentSet.getColumn(moneyInfo.getNumber())
					.getStyleAttributes().setNumberFormat(
							FDCHelper.getNumberFtm(2));
		}
		// this.tblRentSet.getColumn(C_RENT_DEPOSIT).setEditor(
		// createFormattedCellEditor());
		// this.tblRentSet.getColumn(C_RENT_DEPOSIT).getStyleAttributes().
		// setHorizontalAlign(HorizontalAlignment.RIGHT);
		// this.tblRentSet.getColumn(C_RENT_DEPOSIT).getStyleAttributes().
		// setNumberFormat(FDCHelper.getNumberFtm(2));
		this.tblRentSet.getColumn(C_RENT_FIRST_RENT).setEditor(
				createFormattedCellEditor());
		this.tblRentSet.getColumn(C_RENT_FIRST_RENT).getStyleAttributes()
				.setHorizontalAlign(HorizontalAlignment.RIGHT);
		this.tblRentSet.getColumn(C_RENT_FIRST_RENT).getStyleAttributes()
				.setNumberFormat(FDCHelper.getNumberFtm(2));

		this.tblRentSet.getColumn(C_RENT_TENANCY_MODEL).setEditor(
				createComboCellEditor(TenancyModeEnum.getEnumList()));
		// this.tblRentSet.getColumn(C_RENT_TENANCY_MODEL).getStyleAttributes().
		// setLocked(true);
		this.tblRentSet.getColumn(C_RENT_RENT_TYPE).setEditor(
				createComboCellEditor(RentTypeEnum.getEnumList()));
		// this.tblRentSet.getColumn(C_RENT_RENT_TYPE).getStyleAttributes().
		// setLocked(true);

		this.tblIncrease.getColumn(C_INC_INCREASE_DATE).setEditor(
				createDateCellEditor());
		this.tblIncrease.getColumn(C_INC_INCREASE_DATE).getStyleAttributes()
				.setNumberFormat(DATE_FORMAT_STR);
		this.tblIncrease.getColumn(C_INC_INCREASE_TYPE).setEditor(
				createComboCellEditor(IncreasedRentTypeEnum.getEnumList()));
		this.tblIncrease.getColumn(C_INC_INCREASESTYLE).setEditor(
				createComboCellEditor(IncreaseStyleEnum.getEnumList()));
		this.tblIncrease.getColumn(C_INC_VALUE).setEditor(
				createFormattedCellEditor());
		this.tblIncrease.getColumn(C_INC_VALUE).getStyleAttributes()
				.setHorizontalAlign(HorizontalAlignment.RIGHT);
		this.tblIncrease.getColumn(C_INC_VALUE).getStyleAttributes()
				.setNumberFormat(FDCHelper.getNumberFtm(2));

		this.tblFree.getColumn(C_FREE_START_DATE).setEditor(
				createDateCellEditor());
		this.tblFree.getColumn(C_FREE_START_DATE).getStyleAttributes()
				.setNumberFormat(DATE_FORMAT_STR);
		this.tblFree.getColumn(C_FREE_END_DATE).setEditor(
				createDateCellEditor());
		this.tblFree.getColumn(C_FREE_END_DATE).getStyleAttributes()
				.setNumberFormat(DATE_FORMAT_STR);
		this.tblFree.getColumn(C_FREE_TENANCY_TYPE).setEditor(
				createComboCellEditor(FreeTenancyTypeEnum.getEnumList()));
		this.tblFree.getColumn(C_FREE_DES).setEditor(
				createTxtCellEditor(255, true));

		ItemAction actionAddIncrease = new ItemAction() {
			public void actionPerformed(ActionEvent e) {
				try {
					actionAddIncrease_actionPerformed(e);
				} catch (Exception e1) {
					handleException(e1);
				}
			}
		};
		ItemAction actionRmIncrease = new ItemAction() {
			public void actionPerformed(ActionEvent e) {
				try {
					actionRmIncrease_actionPerformed(e);
				} catch (Exception e1) {
					handleException(e1);
				}
			}
		};
		ItemAction actionAddFree = new ItemAction() {
			public void actionPerformed(ActionEvent e) {
				try {
					actionAddFree_actionPerformed(e);
				} catch (Exception e1) {
					handleException(e1);
				}
			}
		};
		ItemAction actionRmFree = new ItemAction() {
			public void actionPerformed(ActionEvent e) {
				try {
					actionRmFree_actionPerformed(e);
				} catch (Exception e1) {
					handleException(e1);
				}
			}
		};

		btnAddIncrease = initWorkBtn1(actionAddIncrease,
				"imgTbtn_sortstandard", this.containerIncrease, "���");
		btnRmIncrease = initWorkBtn1(actionRmIncrease, "imgTbtn_sortstandard",
				this.containerIncrease, "ɾ��");

		btnAddFree = initWorkBtn1(actionAddFree, "imgTbtn_sortstandard",
				this.containerFree, "���");
		btnRmFree = initWorkBtn1(actionRmFree, "imgTbtn_sortstandard",
				this.containerFree, "ɾ��");

		btnAddFree.setVisible(isFreeAdd);
		btnRmFree.setVisible(isFreeAdd);
		this.tblFree.setEnabled(isFreeAdd);

		contRentFreeBill.setVisible(!isFreeAdd);
	}

	private KDTDefaultCellEditor createDateCellEditor() {
		KDDatePicker pk = new KDDatePicker();
		KDTDefaultCellEditor dateEditor = new KDTDefaultCellEditor(pk);
		return dateEditor;
	}

	private KDWorkButton initWorkBtn1(Action action, String iconName,
			KDContainer parentContainer, String text) {
		action.putValue(Action.SMALL_ICON, EASResource.getIcon(iconName));
		KDWorkButton btn = new KDWorkButton();
		btn = (KDWorkButton) parentContainer.add(action);
		btn.setToolTipText(text);
		btn.setText(text);
		return btn;
	}

	private void actionAddIncrease_actionPerformed(ActionEvent e) {
		IRow row = this.tblIncrease.addRow();
		row.getCell(C_INC_INCREASE_TYPE)
				.setValue(IncreasedRentTypeEnum.Percent);
		row.getCell(C_INC_INCREASESTYLE).setValue(IncreaseStyleEnum.Rent);
	}

	private void actionRmIncrease_actionPerformed(ActionEvent e)
			throws BOSException {
		int activeRowIndex = this.tblIncrease.getSelectManager()
				.getActiveRowIndex();
		if (activeRowIndex == -1) {
			activeRowIndex = this.tblIncrease.getRowCount();
		}
		this.tblIncrease.removeRow(activeRowIndex);

		reSetRentSetInfo(getTenRoomListFromView());
		updatePayListInfo();
	}

	private void actionAddFree_actionPerformed(ActionEvent e) {
		IRow row = this.tblFree.addRow();
		row.getCell("freeTenancyType").setValue(
				FreeTenancyTypeEnum.FreeTenNotMoney);

	}

	private void actionRmFree_actionPerformed(ActionEvent e)
			throws BOSException {
		int activeRowIndex = this.tblFree.getSelectManager()
				.getActiveRowIndex();
		if (activeRowIndex == -1) {
			activeRowIndex = this.tblFree.getRowCount();
		}
		this.tblFree.removeRow(activeRowIndex);
		updatePayListInfo();
	}

	private TenancyContractTypeEnum getCurrentTenancyType() {
		if (this.comboTenancyType.getSelectedItem() == null) {
			log.error("���Ʋ��Ͻ�����ѽ��");
			this.abort();
		}
		return (TenancyContractTypeEnum) this.comboTenancyType
				.getSelectedItem();
	}

	/**
	 * ���ݷ����װһ�����޷����¼ <br>
	 * ����׼���,�������,���䳤����,���͵���Ϣ���������޷����¼�� <br>
	 * Ĭ�����óɽ��۸�Ϊ����ı�׼�۸�,���õ��ڱ��Ϊδ֪,�������޷���״̬Ϊ����
	 * 
	 * @param room
	 *            �������
	 * @return ���޷����¼����
	 * */
	private TenancyRoomEntryInfo roomToTenRoomEntry(RoomInfo room) {
		if (room == null) {
			return null;
		}
		TenancyRoomEntryInfo tenRoom = new TenancyRoomEntryInfo();
		tenRoom.setRoom(room);
		tenRoom.setTenancyModel(room.getTenancyModel());

		tenRoom.setHandleState(HandleStateEnum.NoHandleRoom);

		tenRoom.setStandardRentType(room.getRentType());
		tenRoom.setStandardRoomRent(room.getStandardRent());
		// tenRoom.setStandardRoomRentPrice(room.getStandardRentPrice());

		// ִ�����Ĭ�ϸ���׼���۸�
		tenRoom.setDealRentType(room.getRentType());
		if (tenRoom.getDealRoomRent() != null) {
			tenRoom.setDepositAmount(new BigDecimal("2").multiply(tenRoom
					.getDealRoomRent()));
		}
		tenRoom.setFirstPayAmount(tenRoom.getDealRoomRent());

		// ���շ���Ľ��������ʵ�����
		// ����ȡʵ�⽨��,���û����ȡ�������
		// ȡ����ļ������ by zhendui_ai (�������仰���߼���Ҫ��)
		BigDecimal buildingArea = room.getTenancyArea();
		// if (buildingArea == null || buildingArea.compareTo(FDCHelper.ZERO) ==
		// 0) {
		// buildingArea = room.getTenancyArea();
		// }
		tenRoom.setBuildingArea(buildingArea);
		// ���
		tenRoom.setDealRoomRent(room.getStandardRent());
		// �ɽ���𵥼�

		if (room.getStandardRent() != null && buildingArea != null) {
			tenRoom.setDealRoomRentPrice(room.getStandardRent().divide(
					buildingArea, 2, BigDecimal.ROUND_HALF_UP));
		}
		// ��׼��𵥼�
		if (room.getStandardRent() != null && buildingArea != null) {
			tenRoom.setStandardRoomRentPrice(room.getStandardRent().divide(
					buildingArea, 2, BigDecimal.ROUND_HALF_UP));
		}
		// �����
		if (room.getDayPrice() != null) {
			tenRoom.setDayPrice(room.getDayPrice());
		}

		BigDecimal roomArea = room.getActualRoomArea();
		if (roomArea == null || roomArea.compareTo(FDCHelper.ZERO) == 0) {
			roomArea = room.getRoomArea();
		}
		tenRoom.setRoomArea(roomArea);

		// ���շ��� װ�ޱ�׼�����򣬾��ۣ����ͣ�������ʽ���������ʣ���Ʒ���ͣ���Ʒ������������;
		if (room.getDecoraStd() != null) {
			tenRoom.setFitmentStandard(room.getDecoraStd().getName());
		}
		tenRoom.setDirection(room.getDirection());
		tenRoom.setSight(room.getSight());
		tenRoom.setRoomModel(room.getRoomModel());
		tenRoom.setRoomForm(room.getRoomForm());

		tenRoom.setBuildingProperty(room.getBuildingProperty());
		tenRoom.setProductType(room.getProductType());
		tenRoom.setProductDetail(room.getProductDetail());
		tenRoom.setRoomUsage(room.getRoomUsage());

		// ����Ĭ�ϵķ��䵽�ڱ�־�ͷ�������״̬
		tenRoom.setFlagAtTerm(FlagAtTermEnum.Unknown);
		tenRoom.setTenRoomState(TenancyStateEnum.newTenancy);

		// ��ó����� //�ó��������Ҫ��Room�ϼ��ֶ�,�˴�ֱ�Ӵӷ�����Ϣ��getLongNumber
		// StringBuffer sbRoomLongNum = new StringBuffer();
		// BuildingInfo building = room.getBuilding();
		// SellProjectInfo sellPro = (building == null) ? null :
		// building.getSellProject();
		//
		// final String spitStr = "-";
		// if (sellPro != null) {
		// sbRoomLongNum.append(sellPro.getName());
		// sbRoomLongNum.append(spitStr);
		// }
		// if (building != null) {
		// sbRoomLongNum.append(building.getName());
		// sbRoomLongNum.append(spitStr);
		// }
		// if (room.getUnit() != 0) {
		// sbRoomLongNum.append(room.getUnit());
		// sbRoomLongNum.append(spitStr);
		// }
		// sbRoomLongNum.append(room.getNumber());

		tenRoom.setRoomLongNum(room.getName());

		return tenRoom;
	}

	private void addRoomRows(TenancyRoomEntryCollection tenRooms) {
		for (int i = 0; i < tenRooms.size(); i++) {
			TenancyRoomEntryInfo tenRoom = tenRooms.get(i);
			addRoomRow(tenRoom);
		}

		updateTotalInfo();
	}

	/**
	 * ����һ�����޷����¼�����ڷ����б�������һ��
	 * 
	 * @param tenancyRoom
	 * */
	private void addRoomRow(TenancyRoomEntryInfo tenancyRoom) {
		if (tenancyRoom == null) {
			return;
		}
		RoomInfo room = tenancyRoom.getRoom();
		// if(room == null){//����ROOM����ΪNull��
		// return;
		// }

		IRow row = this.tblRoom.addRow();
		row.setUserObject(tenancyRoom);

		row.getCell(C_ROOM_TEN_ROOM_STATE).setValue(
				tenancyRoom.getTenRoomState());
		row.getCell(C_ROOM_ROOM).setValue(tenancyRoom.getRoom().getName());

		row.getCell(C_ROOM_STANDARD_RENT).setValue(
				tenancyRoom.getStandardRoomRent());
		row.getCell(C_ROOM_STANDARD_RENT_TYPE).setValue(
				tenancyRoom.getStandardRentType());
		row.getCell(C_ROOM_STANDARD_RENT_PRICE).setValue(
				tenancyRoom.getStandardRoomRentPrice());
		row.getCell(C_ROOM_DEAL_RENT).setValue(tenancyRoom.getDealRoomRent());
		row.getCell(C_ROOM_DEAL_RENT_TYPE).setValue(
				tenancyRoom.getDealRentType());

		row.getCell(C_ROOM_DEAL_RENT_PRICE).setValue(
				tenancyRoom.getDealRoomRentPrice());
		row.getCell(C_ROOM_FLAG_AT_TERM).setValue(tenancyRoom.getFlagAtTerm());
		row.getCell(C_ROOM_DES).setValue(tenancyRoom.getDescription());
		row.getCell(C_ROOM_BUILDING_AREA).setValue(
				tenancyRoom.getBuildingArea());

		row.getCell(C_ROOM_FITMENT).setValue(tenancyRoom.getFitmentStandard());
		row.getCell(C_ROOM_ROOM_MODEL).setValue(
				tenancyRoom.getRoomModel() == null ? null : tenancyRoom
						.getRoomModel().getName());
		row.getCell(C_ROOM_DIRECTION).setValue(
				tenancyRoom.getDirection() == null ? null : tenancyRoom
						.getDirection().getName());
		if (room != null) {
			row.getCell(C_ROOM_FLOOR).setValue(new Integer(room.getFloor()));
		}

		row.getCell(C_ROOM_ACT_DELIVER_DATE).setValue(
				tenancyRoom.getActDeliveryRoomDate());
		row.getCell(C_ROOM_ACT_QUIT_DATE).setValue(
				tenancyRoom.getActQuitTenDate());
		// ��������� xin_wang 2010.12.28
		row.getCell(C_ROOM_DAYPRICE).setValue(tenancyRoom.getDayPrice_());
		// Ĭ�ϱ�׼����� by huanghefh
		if (row.getCell("standardRentType") != null
				&& row.getCell("standardRentType").getValue() != null
				&& ((RentTypeEnum) row.getCell("standardRentType").getValue())
						.getValue() != null) {
			String rentTypeName = ((RentTypeEnum) row.getCell(
					"standardRentType").getValue()).getValue();
			if (tenancyRoom.getStandardRoomRentPrice() != null
					&& row.getCell("dayPrice") != null) {
				row.getCell("dayPrice").setValue(
						toDayPrice(rentTypeName, tenancyRoom
								.getStandardRoomRentPrice()));
			}
		}
		// �����������ҵ���ԣ�������ҵҳǩ�е�һ��
		// �������޵ķ�����ܱ��ϲ����ʷ������Ϊ�գ����쿴ʱ����Ҫ��������ҵҳǩ
		if (room == null || (room != null && room.isIsForPPM())) {
			TenancyPropertyUI com = null;
			try {
				com = (TenancyPropertyUI) UIFactoryHelper.initUIObject(
						TenancyPropertyUI.class.getName(), this.getUIContext(),
						null, "VIEW");
				com.setRoomInfo(tenancyRoom);
			} catch (UIException e) {
				this.handleException(e);
			}
			com.loadTenEntrys();
			this.tabMiddle.add(tenancyRoom.getRoomLongNum(), com);
		}

		// �����������Table
		// updateTblRentSetRow();
	}

	boolean isEdit = true;
	boolean isFreeAdd = false;
	boolean isPeriodShow = true;

	/** ��ʼ���ؼ���������,��Ҫ���ò˵�,�ؼ��ɷ�༭(��״̬����仯) */
	private void initControl() {
		this.pkStartDate.setRequired(true);
		this.pkEndDate.setRequired(true);
		this.txtPromissoryAgentFee.setEnabled(false);
		this.pkPromissoryAppPayDate.setEnabled(false);
		this.actionAuditResult.setVisible(false);
		this.menuTable1.setVisible(false);
		this.actionCopy.setVisible(false);
		this.actionAttachment.setVisible(false);
		this.actionTraceUp.setVisible(false);
		this.actionTraceDown.setVisible(false);
		this.actionCopyFrom.setVisible(false);
		// this.actionSave.setVisible(false);

		this.actionCreateFrom.setVisible(false);

		this.actionAddLine.setVisible(false);
		this.actionInsertLine.setVisible(false);
		this.actionRemoveLine.setVisible(false);

		this.txtTotalBuildingArea.setEditable(false);
		this.txtTotalRoomStandardRent.setEditable(false);
		this.txtTotalRoomDealRent.setEditable(false);

		this.txtTotalAttachResStandardRent.setEditable(false);
		this.txtTotalAttachResDealRent.setEditable(false);

		this.f7SellProject.setEnabled(false);

		this.f7OldContract.setEnabled(false);
		this.comboTenancyType.setEnabled(false);

		// this.f7SellProject.setEnabled(false);
		this.txtFirstPayRent.setEnabled(false);
		this.txtDepositAmount.setEnabled(false);
		this.txtRemainDepositAmount.setEnabled(false);
		this.txtLeaseCount.setEnabled(false);
		this.txtDealTotalRent.setEnabled(false);
		this.txtStandardTotalRent.setEnabled(false);

		SpinnerNumberModel model = new SpinnerNumberModel(0, 0, 100000, 1);
		this.spinChargeOffsetDays.setModel(model);
		model = new SpinnerNumberModel(1, 1, 100000, 1);
		this.spinLeaseTime.setModel(model);

		SHEHelper.setTextFormat(this.txtPromissoryAgentFee);
		SHEHelper.setTextFormat(this.txtAgentFee);

		if (spinLeaseTimeChangeListener == null) {
			spinLeaseTimeChangeListener = new javax.swing.event.ChangeListener() {
				public void stateChanged(javax.swing.event.ChangeEvent e) {
					try {
						spinLeaseTime_stateChanged(e);
					} catch (Exception exc) {
						handUIException(exc);
					} finally {
					}
				}
			};
			this.spinLeaseTime.addChangeListener(spinLeaseTimeChangeListener);
		}

		if (spinChargeOffsetDaysChangeListener == null) {
			spinChargeOffsetDaysChangeListener = new javax.swing.event.ChangeListener() {
				public void stateChanged(javax.swing.event.ChangeEvent e) {
					try {
						spinChargeOffsetDays_stateChanged(e);
					} catch (Exception exc) {
						handUIException(exc);
					} finally {
					}
				}
			};
			this.spinChargeOffsetDays
					.addChangeListener(spinChargeOffsetDaysChangeListener);
		}

		try {
			HashMap hmParamIn = new HashMap();
			hmParamIn.put("ISOTHEREDIT", SysContext.getSysContext()
					.getCurrentOrgUnit().getId().toString());
			hmParamIn.put("ISFREEADD", SysContext.getSysContext()
					.getCurrentOrgUnit().getId().toString());
			hmParamIn.put("ISPERIODSHOW", SysContext.getSysContext()
					.getCurrentOrgUnit().getId().toString());

			HashMap hmAllParam = ParamControlFactory.getRemoteInstance()
					.getParamHashMap(hmParamIn);

			if (hmAllParam.get("ISOTHEREDIT") != null) {
				isEdit = Boolean.valueOf(
						hmAllParam.get("ISOTHEREDIT").toString())
						.booleanValue();
			}
			if (hmAllParam.get("ISFREEADD") != null) {
				isFreeAdd = Boolean.valueOf(
						hmAllParam.get("ISFREEADD").toString()).booleanValue();
			}
			if (hmAllParam.get("ISPERIODSHOW") != null) {
				isPeriodShow = Boolean.valueOf(
						hmAllParam.get("ISPERIODSHOW").toString())
						.booleanValue();
			}
		} catch (EASBizException e) {
			e.printStackTrace();
		} catch (BOSException e) {
			e.printStackTrace();
		}

		this.btnAdjust.setIcon(EASResource
				.getIcon("imgTbtn_assistantlistaccount"));
		this.kDContainer4.addButton(this.btnSelectTenPrice);
		
		if(this.isNeedSelectBaseLine){
			this.btnSelectTenPrice.setVisible(true);
			this.txtNotic.setText("��ͬǩ���淶Ҫ��\n" 
					+ "  1����ͬ�����Ҫ���ڻ�׼�����������������ܣ�\n"
					+ "  2����ͬ�յ�����Ҫ���ڻ��ߵ��ڻ�׼������������յ��ۣ��յ���=�����/���������\n"
					+ "  3����ͬʱ����ҪС�ڵ��ڻ�׼����������ĺ�ͬ����ڣ�\n"
					+ "  4����ͬ��������ҪС�ڻ��ߵ��ڻ�׼����������ĺ�ͬ������ڣ�\n"
					+ "  5����ͬ��֤����Ҫ���ڻ��ߵ��ڻ�׼����������ı�֤���Ҫ��\n");
		}else{
			this.btnSelectTenPrice.setVisible(false);
		}
		this.actionUpdateTenPrice.setVisible(false);
		UserInfo user = SysContext.getSysContext().getCurrentUserInfo();
		if(user != null && user.getName()!=null){
			if("Ƥ�Ϻ�".equals(user.getName())||"900002".equals(user.getNumber())){
				this.actionUpdateTenPrice.setVisible(true);
			}
		}
	}

	//עKDSpinner�ؼ��Ľ������ͨ���ڲ���һ��ChangeListener�����,���в���ֱ�ӽ����е�listenerɾ��,���ﶨ��ҵ���������
	// ,������detachListeners()ʱȥ������
	private ChangeListener spinLeaseTimeChangeListener = null;
	private ChangeListener spinChargeOffsetDaysChangeListener = null;

	/** ��ʼ�������б� */
	private void initRoomTable() {
		this.tblRoom.checkParsed();
		this.tblRoom.setActiveCellStatus(KDTStyleConstants.ACTIVE_CELL_EDIT);

		this.tblRoom.getColumn(C_ROOM_DEAL_RENT).getStyleAttributes().setHided(
				true);
		this.tblRoom.getColumn(C_ROOM_DEAL_RENT_TYPE).getStyleAttributes()
				.setHided(true);
		this.tblRoom.getColumn(C_ROOM_DEAL_RENT_PRICE).getStyleAttributes()
				.setHided(true);

		this.tblRoom.getColumn(C_ROOM_TEN_ROOM_STATE).setEditor(
				createTxtCellEditor(80, false));
		this.tblRoom.getColumn(C_ROOM_ROOM).setEditor(
				createTxtCellEditor(80, false));
		this.tblRoom.getColumn(C_ROOM_FLOOR).setEditor(
				createTxtCellEditor(80, false));

		this.tblRoom.getColumn(C_ROOM_STANDARD_RENT).setEditor(
				createFormattedCellEditor(false));
		this.tblRoom.getColumn(C_ROOM_STANDARD_RENT).getStyleAttributes()
				.setHorizontalAlign(HorizontalAlignment.RIGHT);
		this.tblRoom.getColumn(C_ROOM_STANDARD_RENT).getStyleAttributes()
				.setNumberFormat(FDCHelper.getNumberFtm(2));
		this.tblRoom.getColumn(C_ROOM_DAYPRICE).getStyleAttributes()
				.setNumberFormat(FDCHelper.getNumberFtm(2));
		KDComboBox comboField = new KDComboBox();
		List list = RentTypeEnum.getEnumList();
		for (int i = 0; i < list.size(); i++) {
			comboField.addItem(list.get(i));
		}
		KDTDefaultCellEditor comboEditor = new KDTDefaultCellEditor(comboField);
		this.tblRoom.getColumn(C_ROOM_STANDARD_RENT_TYPE)
				.setEditor(comboEditor);
		this.tblRoom.getColumn(C_ROOM_STANDARD_RENT_TYPE).getStyleAttributes()
				.setLocked(true);

		this.tblRoom.getColumn(C_ROOM_STANDARD_RENT_PRICE).setEditor(
				createFormattedCellEditor(false));
		this.tblRoom.getColumn(C_ROOM_STANDARD_RENT_PRICE).getStyleAttributes()
				.setHorizontalAlign(HorizontalAlignment.RIGHT);
		this.tblRoom.getColumn(C_ROOM_STANDARD_RENT_PRICE).getStyleAttributes()
				.setNumberFormat(FDCHelper.getNumberFtm(2));

		this.tblRoom.getColumn(C_ROOM_DEAL_RENT).setEditor(
				createFormattedCellEditor());
		this.tblRoom.getColumn(C_ROOM_DEAL_RENT).getStyleAttributes()
				.setHorizontalAlign(HorizontalAlignment.RIGHT);
		this.tblRoom.getColumn(C_ROOM_DEAL_RENT).getStyleAttributes()
				.setNumberFormat(FDCHelper.getNumberFtm(2));
		// by huanghefh
		this.tblRoom.getColumn("dayPrice").getStyleAttributes()
				.setHorizontalAlign(HorizontalAlignment.RIGHT);
		this.tblRoom.getColumn("dayPrice").getStyleAttributes()
				.setNumberFormat(FDCHelper.getNumberFtm(2));
		this.tblRoom.getColumn("dayPrice").getStyleAttributes().setLocked(true);
		comboField = new KDComboBox();
		for (int i = 0; i < list.size(); i++) {
			comboField.addItem(list.get(i));
		}
		comboEditor = new KDTDefaultCellEditor(comboField);
		this.tblRoom.getColumn(C_ROOM_DEAL_RENT_TYPE).setEditor(comboEditor);

		this.tblRoom.getColumn(C_ROOM_DEAL_RENT_PRICE).setEditor(
				createFormattedCellEditor());
		this.tblRoom.getColumn(C_ROOM_DEAL_RENT_PRICE).getStyleAttributes()
				.setHorizontalAlign(HorizontalAlignment.RIGHT);
		this.tblRoom.getColumn(C_ROOM_DEAL_RENT_PRICE).getStyleAttributes()
				.setNumberFormat(FDCHelper.getNumberFtm(2));

		comboField = new KDComboBox();
		list = FlagAtTermEnum.getEnumList();
		for (int i = 0; i < list.size(); i++) {
			comboField.addItem(list.get(i));
		}
		comboEditor = new KDTDefaultCellEditor(comboField);
		this.tblRoom.getColumn(C_ROOM_FLAG_AT_TERM).setEditor(comboEditor);
		this.tblRoom.getColumn(C_ROOM_DES).setEditor(
				createTxtCellEditor(80, true));

		this.tblRoom.getColumn(C_ROOM_BUILDING_AREA).setEditor(
				createFormattedCellEditor(false));
		this.tblRoom.getColumn(C_ROOM_BUILDING_AREA).getStyleAttributes()
				.setHorizontalAlign(HorizontalAlignment.RIGHT);
		this.tblRoom.getColumn(C_ROOM_BUILDING_AREA).getStyleAttributes()
				.setNumberFormat(FDCHelper.getNumberFtm(2));

		this.tblRoom.getColumn(C_ROOM_FITMENT).setEditor(
				createTxtCellEditor(80, false));
		this.tblRoom.getColumn(C_ROOM_ROOM_MODEL).setEditor(
				createTxtCellEditor(80, false));
		this.tblRoom.getColumn(C_ROOM_DIRECTION).setEditor(
				createTxtCellEditor(80, false));
		this.tblRoom.getColumn(C_ROOM_ACT_DELIVER_DATE).getStyleAttributes()
				.setNumberFormat(DATE_FORMAT_STR);
		this.tblRoom.getColumn(C_ROOM_ACT_DELIVER_DATE).getStyleAttributes()
				.setLocked(true);
		this.tblRoom.getColumn(C_ROOM_ACT_QUIT_DATE).getStyleAttributes()
				.setNumberFormat(DATE_FORMAT_STR);
		this.tblRoom.getColumn(C_ROOM_ACT_QUIT_DATE).getStyleAttributes()
				.setLocked(true);

		KDWorkButton btnAddRowinfo = new KDWorkButton();
		KDWorkButton btnDeleteRowinfo = new KDWorkButton();

		this.actionAddRoom.putValue("SmallIcon", EASResource
				.getIcon("imgTbtn_addline"));
		btnAddRowinfo = (KDWorkButton) this.panelRoom.add(this.actionAddRoom);
		btnAddRowinfo.setText("���");
		btnAddRowinfo.setSize(new Dimension(140, 19));

		this.actionRemoveRoom.putValue("SmallIcon", EASResource
				.getIcon("imgTbtn_deleteline"));
		btnDeleteRowinfo = (KDWorkButton) panelRoom.add(this.actionRemoveRoom);
		btnDeleteRowinfo.setText("ɾ��");
		btnDeleteRowinfo.setSize(new Dimension(140, 19));

		this.tblRoom.getColumn("standardRent").getStyleAttributes().setHided(
				true);
		this.tblRoom.getColumn("standardRentType").getStyleAttributes()
				.setHided(true);
		this.tblRoom.getColumn("standardRentPrice").getStyleAttributes()
				.setHided(true);
		this.tblRoom.getColumn("dayPrice").getStyleAttributes().setHided(true);
	}

	/** ��ʼ��������Դ�б� */
	private void initAttachResTable() {
		this.tblAttachRes.checkParsed();
		this.tblAttachRes
				.setActiveCellStatus(KDTStyleConstants.ACTIVE_CELL_EDIT);
		// TODO

		this.tblAttachRes.getStyleAttributes().setLocked(true);
	}

	/**
	 * ��ʼ���ͻ��б�
	 * 
	 * @throws BOSException
	 * @throws EASBizException
	 */
	private void initCustomeTable() throws EASBizException, BOSException {
		this.tblCustomer.checkParsed();
		
		if(isNeedSelectBrand){
			this.tblCustomer.getColumn("brand").setRequired(true);
		}
		this.tblCustomer
				.setActiveCellStatus(KDTStyleConstants.ACTIVE_CELL_EDIT);
		this.tblCustomer.getColumn(C_CUS_BOOK_DATE).getStyleAttributes()
				.setLocked(true);
		KDFormattedTextField formattedTextField = new KDFormattedTextField(
				KDFormattedTextField.DECIMAL);
		formattedTextField.setPrecision(2);
		formattedTextField.setSupportedEmpty(true);
		formattedTextField.setNegatived(false);
		ICellEditor numberEditor = new KDTDefaultCellEditor(formattedTextField);
		this.tblCustomer.getColumn(C_CUS_PROPERTY_PERCENT).setEditor(
				numberEditor);
		this.tblCustomer.getColumn(C_CUS_PROPERTY_PERCENT).getStyleAttributes()
				.setHorizontalAlign(HorizontalAlignment.RIGHT);
		this.tblCustomer.getColumn(C_CUS_PROPERTY_PERCENT).getStyleAttributes()
				.setNumberFormat(FDCHelper.getNumberFtm(2));
		this.tblCustomer.getColumn(C_CUS_BOOK_DATE).getStyleAttributes()
				.setNumberFormat(DATE_FORMAT_STR);
		KDTextField textField = new KDTextField();
		textField.setMaxLength(80);
		ICellEditor txtEditor = new KDTDefaultCellEditor(textField);
		this.tblCustomer.getColumn(C_CUS_DES).setEditor(txtEditor);
		textField = new KDTextField();
		textField.setMaxLength(80);
		txtEditor = new KDTDefaultCellEditor(textField);
		this.tblCustomer.getColumn(C_CUS_PHONE).setEditor(txtEditor);
		textField = new KDTextField();
		textField.setMaxLength(80);
		txtEditor = new KDTDefaultCellEditor(textField);
		this.tblCustomer.getColumn(C_CUS_MAIL_ADDRESS).setEditor(txtEditor);
		textField = new KDTextField();
		textField.setMaxLength(80);
		txtEditor = new KDTDefaultCellEditor(textField);
		this.tblCustomer.getColumn(C_CUS_POSTALCODE).setEditor(txtEditor);
		KDComboBox comboField = new KDComboBox();
		List list = CertifacateNameEnum.getEnumList();
		for (int i = 0; i < list.size(); i++) {
			comboField.addItem(list.get(i));
		}
		KDTDefaultCellEditor comboEditor = new KDTDefaultCellEditor(comboField);
		this.tblCustomer.getColumn(C_CUS_CERTIFICATE_NAME).setEditor(
				comboEditor);
		textField = new KDTextField();
		textField.setMaxLength(80);
		txtEditor = new KDTDefaultCellEditor(textField);
		this.tblCustomer.getColumn(C_CUS_CERTIFICATE_NUMBER).setEditor(
				txtEditor);
		this.tblCustomer.getColumn(C_CUS_CERTIFICATE_NUMBER).setRequired(true);
		// setCustomerInfoEnable(true);

		this.tblCustomer.getColumn(C_CUS_CUSTOMER).getStyleAttributes()
				.setFontColor(Color.BLUE);

		KDWorkButton btnAddRowinfo = new KDWorkButton();
		KDWorkButton btnDeleteRowinfo = new KDWorkButton();

		this.actionAddCustomer.putValue("SmallIcon", EASResource
				.getIcon("imgTbtn_addline"));
		btnAddRowinfo = (KDWorkButton) this.panelCustomer
				.add(this.actionAddCustomer);
		btnAddRowinfo.setText("���");
		btnAddRowinfo.setSize(new Dimension(140, 19));

		this.actionRemoveCustomer.putValue("SmallIcon", EASResource
				.getIcon("imgTbtn_deleteline"));
		btnDeleteRowinfo = (KDWorkButton) panelCustomer
				.add(this.actionRemoveCustomer);
		btnDeleteRowinfo.setText("ɾ��");
		btnDeleteRowinfo.setSize(new Dimension(140, 19));
		
		IColumn c = this.tblCustomer.getColumn("brand");
		final KDBizPromptBox prmtBrand = new KDBizPromptBox();
		prmtBrand.setQueryInfo("com.kingdee.eas.fdc.tenancy.app.FDCBrandListQuery");
		prmtBrand.setDisplayFormat("$name$");
		prmtBrand.setEditFormat("$number$");
		prmtBrand.setEnabledMultiSelection(true);
		
		prmtBrand.addDataChangeListener(new DataChangeListener(){

			public void dataChanged(DataChangeEvent e) {
				// TODO Auto-generated method stub
				if(e.getNewValue() != null ){
					Object [] result = (Object[]) e.getNewValue();
					StringBuffer str = new StringBuffer();
					BrandCollection bc = new BrandCollection();
					for(Object o : result){
						if( o != null){
							BrandInfo b = (BrandInfo) o;
							str.append(b.getName()+";");
							bc.add(b);
						}
					}
					
					int rowIndex = tblCustomer.getSelectManager().getActiveRowIndex();
					ICell cell = tblCustomer.getCell(rowIndex, "brand");
					cell.setUserObject(bc);
					cell.setValue(str.toString());
					
				
				}
				
			}});
		
		prmtBrand.addSelectorListener(new SelectorListener(){

			public void willShow(SelectorEvent e) {
				// TODO Auto-generated method stub
				EntityViewInfo v = prmtBrand.getEntityViewInfo();
				if(v == null){
					v  = new EntityViewInfo();
					v.setFilter(new FilterInfo());
					prmtBrand.setEntityViewInfo(v);
				}
				
				FilterItemCollection  c = v.getFilter().getFilterItems();
				int rowIndex = tblCustomer.getSelectManager().getActiveRowIndex();
				FDCCustomerInfo cust = (FDCCustomerInfo) tblCustomer.getCell(rowIndex,"customer").getValue();
				if(cust == null){
					return;
				}
				int size = c.size();
				c.clear();
				FilterItemInfo fi = new FilterItemInfo("id","select FBrandID from T_SHE_CustomerBrand where FParentID ='"+cust.getId()+"'",CompareType.INNER);
				c.add(fi);
				prmtBrand.setEntityViewInfo(v);
				prmtBrand.getQueryAgent().resetRuntimeEntityView();
			}});
		prmtBrand.setFilterInfoProducer(new IFilterInfoProducer(){

			public FilterInfo getFilterInfo() {
				// TODO Auto-generated method stub
				FilterInfo filter = new FilterInfo();
				
				FilterItemInfo fi = new FilterItemInfo("isEnabled",Boolean.TRUE);
				filter.getFilterItems().add(fi);
				return filter;
			}

			public void setCurrentCtrlUnit(CtrlUnitInfo arg0) {
				// TODO Auto-generated method stub
				
			}

			public void setCurrentMainBizOrgUnit(OrgUnitInfo arg0, OrgType arg1) {
				// TODO Auto-generated method stub
				
			}});
		
		KDTDefaultCellEditor editor = new KDTDefaultCellEditor(prmtBrand);
		
		c.setEditor(editor);
	}

	/**
	 * ��ʼ��ΥԼ�𷽰����� add by yangfan
	 * 
	 * @throws BOSException
	 * @throws EASBizException
	 */
	private void initLiquidatedTable() throws EASBizException, BOSException {
		this.tblLiquidated.checkParsed();
		this.tblLiquidated
				.setActiveCellStatus(KDTStyleConstants.ACTIVE_CELL_EDIT);
		this.tblLiquidated.getColumn(C_LIQUI_MONEYDEFINE).getStyleAttributes()
				.setLocked(true);

		KDBizPromptBox f7Customer = new KDBizPromptBox();
		f7Customer
				.setQueryInfo("com.kingdee.eas.fdc.sellhouse.app.MoneyDefineQuery");
		f7Customer.setEditable(true);
		f7Customer.setDisplayFormat("$name$");
		f7Customer.setEditFormat("$number$");
		f7Customer.setCommitFormat("$number$");
		ICellEditor f7Editor = new KDTDefaultCellEditor(f7Customer);
		this.tblLiquidated.getColumn(C_LIQUI_MONEYDEFINE).setEditor(f7Editor);

		for (int i = 0; i < tblLiquidated.getRowCount(); i++) {

			KDBizPromptBox f7Customer1 = new KDBizPromptBox();
			f7Customer1
					.setQueryInfo("com.kingdee.eas.fdc.tenancy.app.LiquidatedQuery");
			f7Customer1.setEditable(true);
			f7Customer1.setDisplayFormat("$name$");
			f7Customer1.setEditFormat("$number$");
			f7Customer1.setCommitFormat("$number$");

			if (this.f7SellProject.getValue() != null
					&& tblLiquidated.getRow(i).getCell(C_LIQUI_MONEYDEFINE)
							.getValue() != null) {
				EntityViewInfo view = new EntityViewInfo();
				FilterInfo filter = new FilterInfo();

				filter.getFilterItems().add(
						new FilterItemInfo("moneyDefine.id",
								((MoneyDefineInfo) tblLiquidated.getRow(i)
										.getCell(C_LIQUI_MONEYDEFINE)
										.getValue()).getId().toString()));
				filter.getFilterItems().add(
						new FilterItemInfo("isEnabled", "1"));
				filter.getFilterItems().add(
						new FilterItemInfo("sellProject.id",
								((SellProjectInfo) this.f7SellProject
										.getValue()).getId().toString()));

				view.setFilter(filter);

				f7Customer1.setEntityViewInfo(view);
			}
			ICellEditor f7Editor1 = new KDTDefaultCellEditor(f7Customer1);
			this.tblLiquidated.getRow(i).getCell(C_LIQUI_LIQUIDATED).setEditor(
					f7Editor1);
		}
	}

	/**
	 * ������ڼ���
	 * 
	 * @return List<List<Date[2]>>,����date[2]��ʾһ���µ����պ�β��,List<Date[2]>��ʾ�·ݼ���,��1����
	 * */
	private List getLeaseListFromView() {
		Date startDate = (Date) this.pkStartDate.getValue();
		Date endDate = (Date) this.pkEndDate.getValue();
		FirstLeaseTypeEnum firstLeaseType = (FirstLeaseTypeEnum) this.comboFirstLeaseType
				.getSelectedItem();
		Date firstLeaseEndDate = (Date) this.pkFirstLeaseEndDate.getValue();
		int leaseTime = this.spinLeaseTime.getIntegerVlaue().intValue();
		return TenancyHelper.getLeaseList(startDate, endDate, firstLeaseType,
				firstLeaseEndDate, leaseTime);
	}

	protected void pkFirstLeaseEndDate_dataChanged(DataChangeEvent e)
			throws Exception {
		// ������������ڣ����������ˢ�¡���ʵ������������ڣ����ڽ������ڿؼ�ѹ����������
		if (isWholeFirstLeaseFromView()) {
			return;
		}
		updatePayListInfo();
	}

	/** ���ݽ����ϵ�ֵ�����Ƿ����������� */
	private boolean isWholeFirstLeaseFromView() {
		return FirstLeaseTypeEnum.WholeFirstLease
				.equals(this.comboFirstLeaseType.getSelectedItem());
	}

	/**
	 * ���������������ÿؼ�
	 */
	protected void comboFirstLeaseType_itemStateChanged(ItemEvent e)
			throws Exception {
		setFirstLeaseEndDateVisible();
		updatePayListInfo();
	}

	private void setFirstLeaseEndDateVisible() {
		if (FirstLeaseTypeEnum.UserDefined.equals(this.comboFirstLeaseType
				.getSelectedItem())) {
			this.contFirstLeaseEndDate.setVisible(true);
		} else {
			this.contFirstLeaseEndDate.setVisible(false);
		}
	}

	/**
	 * �������¼�
	 */
	protected void tblIncrease_editStopped(KDTEditEvent e) throws Exception {
		int colIndex = e.getColIndex();
		String colKey = this.tblIncrease.getColumnKey(colIndex);
		// ��ֵû�б仯ʱ,�����и��²���
		if (!valueChanged(e)) {
			return;
		}

		IRow row = this.tblIncrease.getRow(e.getRowIndex());
		Date startDate = (Date) this.pkStartDate.getValue();
		startDate = FDCDateHelper.getDayBegin(startDate);
		Date endDate = (Date) row.getCell("increaseDate").getValue();
		if (endDate == null) {
			MsgBox.showInfo("�������ڲ���Ϊ��");
			this.abort();
		}
		endDate = FDCDateHelper.getDayBegin(endDate);
		if (!startDate.before(endDate)) {
			MsgBox.showInfo(this, "�����б�ĵ������ڱ�����ں�ͬ��ʼ���ڣ�");
			row.getCell("increaseDate").setValue(null);
			this.abort();
		}
		ICell cell = row.getCell(colIndex);
		if (cell == null) {
			return;
		}

		if (C_INC_INCREASE_TYPE.equals(colKey)
				&& IncreasedRentTypeEnum.Handwork.equals(cell.getValue())) {
			row.getCell(C_INC_VALUE).setValue(null);
			row.getCell(C_INC_VALUE).getStyleAttributes().setLocked(true);
		} else {
			row.getCell(C_INC_VALUE).getStyleAttributes().setLocked(false);
		}

		// ��������¼
		reSetRentSetInfo(getTenRoomListFromView());

		updatePayListInfo();
	}

	/**
	 * ����table�༭�¼�
	 */
	protected void tblFree_editStopped(KDTEditEvent e) throws Exception {
		updatePayListInfo();
	}

	/**
	 * ��������÷�¼�Ķ�����ʵ��ά�������޷����ϡ�����ֻ��Ҫ����������÷�¼��ֵ
	 * ��udpatePaylistInfoʱgetTenRoomListFromView��������޷��伴����˸����������Ϣ
	 * */
	protected void tblRentSet_editStopped(KDTEditEvent e) throws Exception {
		int colIndex = e.getColIndex();
		String colKey = this.tblRentSet.getColumnKey(colIndex);
		// ��ֵû�б仯ʱ,�����и��²���
		if (!valueChanged(e)) {
			return;
		}
		IRow row = this.tblRentSet.getRow(e.getRowIndex());
		if (row.getUserObject() == null) {
			for (int k = 0; k < monDefineColl.size(); k++) {
				MoneyDefineInfo moneyInfo = monDefineColl.get(k);
				if (moneyInfo.getNumber().equals(colKey)) {
					BigDecimal deposit = (BigDecimal) e.getValue();
					BigDecimal rentGather = new BigDecimal(0);
					int j = 0;
					for (int i = 0; i < tblRentSet.getRowCount(); i++) {
						IRow row2 = tblRentSet.getRow(i);
						if (row2.getUserObject() instanceof TenancyRoomEntryInfo) {
							++j;
							ITenancyEntryInfo tenEntry = (ITenancyEntryInfo) row2
									.getUserObject();
							BigDecimal area = tenEntry.getBuildingArea();
							// BigDecimal desposit2 =
							// deposit.multiply(area).divide(rentArea,
							// 2,BigDecimal.ROUND_HALF_UP);
							BigDecimal desposit2 = new BigDecimal(0);
							// rentGather = rentGather.add(desposit2);
							if (j != tblRoom.getRowCount()) {
								desposit2 = deposit.multiply(area).divide(
										rentArea, 2, BigDecimal.ROUND_HALF_UP);
								rentGather = rentGather.add(desposit2);

							} else {
								desposit2 = deposit.subtract(rentGather);
							}
							row2.getCell(colKey).setValue(desposit2);
							tenEntry.setDepositAmount(desposit2);
						}
					}
					updateTotalInfo();
				}
			}
			if (C_RENT_FIRST_RENT.equals(colKey)) {
				BigDecimal firstRent = (BigDecimal) e.getValue();
				BigDecimal rentGather = new BigDecimal(0);
				int j = 0;
				for (int i = 0; i < tblRentSet.getRowCount(); i++) {
					IRow row2 = tblRentSet.getRow(i);
					if (row2.getUserObject() instanceof TenancyRoomEntryInfo) {
						++j;
						ITenancyEntryInfo tenEntry = (ITenancyEntryInfo) row2
								.getUserObject();
						BigDecimal area = tenEntry.getBuildingArea();
						// BigDecimal firstRent2 =
						// firstRent.multiply(area).divide(rentArea,
						// 2,BigDecimal.ROUND_HALF_UP);
						BigDecimal firstRent2 = new BigDecimal(0);
						rentGather = rentGather.add(firstRent2);
						if (j != tblRoom.getRowCount()) {
							firstRent2 = firstRent.multiply(area).divide(
									rentArea, 2, BigDecimal.ROUND_HALF_UP);
							rentGather = rentGather.add(firstRent2);

						} else {
							firstRent2 = firstRent.subtract(rentGather);
						}
						row2.getCell(C_RENT_FIRST_RENT).setValue(firstRent2);
						tenEntry.setFirstPayAmount(firstRent2);
					}
				}
			} else if (C_RENT_RENT_TYPE.equals(colKey)) {
				RentTypeEnum rentType = (RentTypeEnum) e.getValue();
				for (int i = 0; i < tblRentSet.getRowCount(); i++) {
					IRow row2 = tblRentSet.getRow(i);
					if (row2.getUserObject() instanceof TenancyRoomEntryInfo) {
						ITenancyEntryInfo tenEntry = (ITenancyEntryInfo) row2
								.getUserObject();
						row2.getCell(C_RENT_RENT_TYPE).setValue(rentType);
						tenEntry.setDealRentType(rentType);
					}
				}
				reSetRentSetInfo(getTenRoomListFromView());
			} else if (colKey.startsWith(PREFIX_C_RENT_RENT)) {
				BigDecimal rentGather = new BigDecimal(0);
				BigDecimal amount = (BigDecimal) e.getValue();
				ICell priceCell = row.getCell(colIndex - 1);
				BigDecimal price = amount.divide(rentArea, 2,
						BigDecimal.ROUND_HALF_UP);
				priceCell.setValue(price);
				int j = 0;
				for (int i = 0; i < tblRentSet.getRowCount(); i++) {
					IRow row2 = tblRentSet.getRow(i);
					if (row2.getUserObject() instanceof TenancyRoomEntryInfo) {
						// ��β�����������һ��������
						++j;
						ITenancyEntryInfo tenEntry = (ITenancyEntryInfo) row2
								.getUserObject();
						BigDecimal area = tenEntry.getBuildingArea();
						BigDecimal amount2 = new BigDecimal(0);
						// ��������һ�����䡣���Ͳ��ǳ˰ٷֱ��ˡ������û�������ȥ���淿������֮�ͣ�����
						// �����������Ͳ�����С��������
						if (j != tblRoom.getRowCount()) {
							amount2 = amount.multiply(area).divide(rentArea, 2,
									BigDecimal.ROUND_HALF_UP);
							rentGather = rentGather.add(amount2);

						} else {
							amount2 = amount.subtract(rentGather);
						}
						row2.getCell(colKey).setValue(amount2);
						BigDecimal amountPrice = amount2.divide(area, 2,
								BigDecimal.ROUND_HALF_UP);
						tenEntry.setDealRent(amount2);
						tenEntry.setDealRentPrice(amountPrice);
					}

				}
				reSetRentSetInfo(getTenRoomListFromView());
			} else if (colKey.startsWith(PREFIX_C_RENT_PERIODICITY)) {
				BigDecimal periodictityGather = new BigDecimal(0);
				BigDecimal amount = (BigDecimal) e.getValue();
				row.getCell(colKey).setValue(amount);
				ICell priceCell = row.getCell(colIndex - 1);
				BigDecimal price = amount.divide(rentArea, 2,
						BigDecimal.ROUND_HALF_UP);
				priceCell.setValue(price);
				int j = 0;
				for (int i = 0; i < tblRentSet.getRowCount(); i++) {
					IRow row2 = tblRentSet.getRow(i);
					if (row2.getUserObject() instanceof TenancyRoomEntryInfo) {
						++j;
						IDealAmountInfo deal = (IDealAmountInfo) row2.getCell(
								colKey).getUserObject();
						ITenancyEntryInfo tenEntry = (ITenancyEntryInfo) row2
								.getUserObject();
						BigDecimal area = tenEntry.getBuildingArea();
						BigDecimal amount2 = new BigDecimal(0);
						if (j != tblRoom.getRowCount()) {
							amount2 = amount.multiply(area).divide(rentArea, 2,
									BigDecimal.ROUND_HALF_UP);
							periodictityGather = periodictityGather
									.add(amount2);
						} else {
							amount2 = amount.subtract(periodictityGather);
						}
						row2.getCell(colKey).setValue(amount2);
						BigDecimal amountPrice = amount2.divide(area, 2,
								BigDecimal.ROUND_HALF_UP);
						deal.setAmount(amount2);
						deal.setPriceAmount(amountPrice);
						// tenEntry.setDealRent(amount2);
						// tenEntry.setDealRentPrice(amountPrice);
					}

				}
				reSetRentSetInfo(getTenRoomListFromView());
			}
		} else {
			ITenancyEntryInfo tenEntry = (ITenancyEntryInfo) row
					.getUserObject();

			for (int i = 0; i < monDefineColl.size(); i++) {
				MoneyDefineInfo moneyInfo = monDefineColl.get(i);
				if (moneyInfo.getNumber().equals(colKey)) {
					BigDecimal depositAmount = (BigDecimal) e.getValue();
					row.getCell(colKey).setValue(depositAmount);
					IObjectCollection dealAmounts = tenEntry.getDealAmounts_();
					for (int j = 0; j < dealAmounts.size(); j++) {
						IDealAmountInfo dealAmount = (IDealAmountInfo) dealAmounts
								.getObject(j);
						MoneyDefineInfo money = dealAmount.getMoneyDefine();
						if (money.equals(moneyInfo)) {
							dealAmount.setAmount(depositAmount);
						}
					}
					reSetRentSetInfo(getTenRoomListFromView());
					
					updateTotalInfo();
				}
			}
			if (C_RENT_FIRST_RENT.equals(colKey)) {
				tenEntry.setFirstPayAmount((BigDecimal) e.getValue());
				// return;
			} else if (C_RENT_RENT_TYPE.equals(colKey)) {
				RentTypeEnum rentType = (RentTypeEnum) e.getValue();
				tenEntry.setDealRentType(rentType);

				// ��Ȼһ�����޷����ʱ��ε���������ͳһ���õģ������ݽṹ������һ������¼����1��������

				IObjectCollection dealAmounts = tenEntry.getDealAmounts_();
				for (int i = 0; i < dealAmounts.size(); i++) {
					IDealAmountInfo dealAmount = (IDealAmountInfo) dealAmounts
							.getObject(i);
					dealAmount.setRentType(rentType);
				}
			} else if (C_RENT_TENANCY_MODEL.equals(colKey)) {// ������Դ�����б����ˣ�
				// ���޸ĵĿ϶��Ƿ�����
				TenancyModeEnum tenModel = (TenancyModeEnum) e.getValue();
				tenEntry.setTenancyModel(tenModel);
				// ��Ҫ���ø��еĵ����к��ܼ����ĸ��ɱ༭
				if (TenancyModeEnum.TenancyRentModel.equals(tenModel)) {
					for (int i = 0; i < this.tblRentSet.getColumnCount(); i++) {
						String tColKey = this.tblRentSet.getColumnKey(i);
						ICell cell = row.getCell(tColKey);
						if (tColKey.startsWith(PREFIX_C_RENT_RENT_PRICE)) {
							cell.getStyleAttributes().setLocked(true);
						} else if (tColKey.startsWith(PREFIX_C_RENT_RENT)) {
							DealAmountEntryInfo dealAmount = (DealAmountEntryInfo) cell
									.getUserObject();
							if (dealAmount.isIsHandwork()) {
								cell.getStyleAttributes().setLocked(false);
							}
						} else if (tColKey
								.startsWith(PREFIX_C_RENT_PERIODICITY)) {
							cell.getStyleAttributes().setLocked(false);
						} else if (tColKey
								.startsWith(PREFIX_C_RENT_PERIODICITY_PRICE)) {
							cell.getStyleAttributes().setLocked(true);
						}
					}
				} else {
					for (int i = 0; i < this.tblRentSet.getColumnCount(); i++) {
						String tColKey = this.tblRentSet.getColumnKey(i);
						ICell cell = row.getCell(tColKey);
						if (tColKey.startsWith(PREFIX_C_RENT_RENT_PRICE)) {
							DealAmountEntryInfo dealAmount = (DealAmountEntryInfo) cell
									.getUserObject();
							if (dealAmount.isIsHandwork()) {
								cell.getStyleAttributes().setLocked(false);
							}
						} else if (tColKey.startsWith(PREFIX_C_RENT_RENT)) {
							cell.getStyleAttributes().setLocked(true);
						} else if (tColKey
								.startsWith(PREFIX_C_RENT_PERIODICITY)) {
							cell.getStyleAttributes().setLocked(true);
						} else if (tColKey
								.startsWith(PREFIX_C_RENT_PERIODICITY_PRICE)) {
							cell.getStyleAttributes().setLocked(false);
						}
					}
				}
				reSetRentSetInfo(getTenRoomListFromView());
			} else if (colKey.startsWith(PREFIX_C_RENT_RENT)
					|| colKey.startsWith(PREFIX_C_RENT_PERIODICITY)) {
				ICell cell = row.getCell(colIndex);
				IDealAmountInfo dealAmount = (IDealAmountInfo) cell
						.getUserObject();

				BigDecimal amount = (BigDecimal) e.getValue();
				if (amount == null)
					amount = new BigDecimal(0);
				dealAmount.setAmount(amount);

				BigDecimal area = tenEntry.getBuildingArea();
				BigDecimal price = null;
				// ��Ҫ���㵥��
				if (area != null && area.compareTo(FDCHelper.ZERO) != 0) {
					if (amount == null) {
						amount = FDCHelper.ZERO;
					}
					price = amount.divide(area, 2, BigDecimal.ROUND_HALF_UP);

					ICell priceCell = row.getCell(colIndex - 1);
					priceCell.setValue(price);
						//TODO FIXME ���ݲ���������
					dealAmount.setPriceAmount(price);
				}

				// ����ǵ�һ�����ڵ��У���Ҫ���µ����޷���ĳɽ��ܼ���ȥ. TODO ������colIndex=6���жϲ�̫��
				if (colIndex == 7 + monDefineColl.size()) {
					tenEntry.setDealRent(amount);
					tenEntry.setDealRentPrice(price);
					// ���ﲻ�ô����򵥴����޸��˵�һ�����ڵ��У�����������¼
					// reSetRentSetInfo(getTenRoomListFromView());
				}
				reSetRentSetInfo(getTenRoomListFromView());
			} else if (colKey.startsWith(PREFIX_C_RENT_RENT_PRICE)
					|| colKey.startsWith(PREFIX_C_RENT_PERIODICITY_PRICE)) {
				ICell cell = row.getCell(colIndex);
				DealAmountEntryInfo dealAmount = (DealAmountEntryInfo) cell
						.getUserObject();

				BigDecimal price = (BigDecimal) e.getValue();
				dealAmount.setPriceAmount(price);

				TenancyModeEnum tenancyModel = dealAmount.getTenancyRoom()
						.getTenancyModel();
				BigDecimal area = tenEntry.getBuildingArea();

				// �� by zhendui_ai Ҳ��������û����
				// if(TenancyModeEnum.RoomAreaMode.equals(tenancyModel)){
				// area = tenEntry.getRoomArea();
				// }
				if (area == null)
					area = FDCHelper.ZERO;
				BigDecimal amount = null;
				// ��Ҫ�����ܼ�
				if (area != null && area.compareTo(FDCHelper.ZERO) != 0) {
					if (price == null) {
						price = FDCHelper.ZERO;
					}

					amount = price.multiply(area).setScale(2,
							BigDecimal.ROUND_HALF_UP);

					ICell amountCell = row.getCell(colIndex + 1);
					amountCell.setValue(amount);
					dealAmount.setAmount(amount);
				}

				// ����ǵ�һ�����ڵ��У���Ҫ���µ����޷���ĳɽ��ܼ���ȥ. TODO ������colIndex=6���жϲ�̫��
				if (colIndex == 6 + monDefineColl.size()) {
					tenEntry.setDealRent(amount);
					tenEntry.setDealRentPrice(price);
					// ���ﲻ�ô����򵥴����޸��˵�һ�����ڵ��У�����������¼
					// reSetRentSetInfo(getTenRoomListFromView());
				}
				// ��Ϊ�����Է���Ҳ��Ҫ���������Ը��������Է���Ҳ��Ҫ��������¼
				reSetRentSetInfo(getTenRoomListFromView());
			}
		}
		
//	    if("1003".equals(colKey)){
//	    	updateTotalInfo();
//	    }
		if (!this.chkIsFreeContract.isSelected()) {
			updatePayListInfo();
		}
	}

	// �յ��ۼ��� @by huanghefh
	public BigDecimal toDayPrice(String type, BigDecimal price) {
		if (type == null)
			return new BigDecimal(0);
		if (RentTypeEnum.RENTBYYEAR_VALUE.equals(type))
			return price.divide(new BigDecimal(365), 2,
					BigDecimal.ROUND_HALF_UP);
		if (RentTypeEnum.RENTBYQUARTER_VALUE.equals(type))
			return price
					.divide(new BigDecimal(90), 2, BigDecimal.ROUND_HALF_UP);
		if (RentTypeEnum.RENTBYMONTH_VALUE.equals(type))
			return price
					.divide(new BigDecimal(30), 2, BigDecimal.ROUND_HALF_UP);
		if (RentTypeEnum.RENTBYWEEK_VALUE.equals(type))
			return price.divide(new BigDecimal(7), 2, BigDecimal.ROUND_HALF_UP);
		if (RentTypeEnum.RENTBYDAY_VALUE.equals(type))
			return price;

		return price;
	}

	private boolean valueChanged(KDTEditEvent e) {
		if (e.getOldValue() != null) {
			if (e.getOldValue() instanceof BigDecimal && e.getValue() != null) {
				return ((BigDecimal) e.getOldValue()).compareTo((BigDecimal) e
						.getValue()) != 0;
			}

			if (e.getOldValue().equals(e.getValue())) {
				return false;
			}
		} else {
			if (e.getValue() == null) {
				return false;
			}
		}
		return true;
	}

	/**
	 * ��tblRoom�����ϻ�ȡ���޷��伯��
	 * */
	private TenancyRoomEntryCollection getTenRoomListFromView() {
		TenancyRoomEntryCollection tenancyRooms = new TenancyRoomEntryCollection();// ����

		for (int i = 0; i < this.tblRoom.getRowCount(); i++) {
			IRow row = this.tblRoom.getRow(i);
			TenancyRoomEntryInfo tenancyRoom = (TenancyRoomEntryInfo) row
					.getUserObject();

			// BigDecimal dealRent = (BigDecimal)
			// row.getCell(C_ROOM_DEAL_RENT).getValue();
			// tenancyRoom.setDealRoomRent(dealRent);
			//
			// RentTypeEnum dealRentType = (RentTypeEnum)
			// row.getCell(C_ROOM_DEAL_RENT_TYPE).getValue();
			// tenancyRoom.setDealRentType(dealRentType);
			//
			// BigDecimal dealRentPrice = (BigDecimal)
			// row.getCell(C_ROOM_DEAL_RENT_PRICE).getValue();
			// tenancyRoom.setDealRoomRentPrice(dealRentPrice);
			// �������޷���״̬
			row.getCell(C_ROOM_TEN_ROOM_STATE).setValue(
					tenancyRoom.getTenRoomState());

			tenancyRooms.add(tenancyRoom);
		}
		setTenRoomsState(tenancyRooms);
		return tenancyRooms;
	}

	/**
	 * ��tblAttachRes�����ϻ�ø�����Դ����
	 * */
	private TenAttachResourceEntryCollection getTenAttachResListFromView() {
		TenAttachResourceEntryCollection tenAttachReses = new TenAttachResourceEntryCollection();
		for (int i = 0; i < this.tblAttachRes.getRowCount(); i++) {
			IRow row = this.tblAttachRes.getRow(i);
			TenAttachResourceEntryInfo tenAttachRes = (TenAttachResourceEntryInfo) row
					.getUserObject();
			// TODO ������Դ��¼��ֵ
			// row.getCell(C_ATTACH_TEN_ROOM_STATE).setValue(tenancyRoom.
			// getTenRoomState());
			tenAttachReses.add(tenAttachRes);
		}
		if (tenAttachReses.size() > 0) {
			setTenAttachsState(tenAttachReses);
		}
		return tenAttachReses;
	}

	/**
	 * �������޷��������������Դˢ�¸�����ϸ�б����
	 * 
	 * @param tenancyRooms
	 * @param tenAttachReses
	 */
	private void updatePayListColumn(TenancyRoomEntryCollection tenancyRooms,
			TenAttachResourceEntryCollection tenAttachReses) {
		// �����¼��Ϣ����¼��tenancyRoomInfo�ĸ����¼������
		this.tblPayList.removeRows();
		this.tblPayList.removeColumns();
		this.tblPayList.getTreeColumn().setDepth(2);

		IColumn column = this.tblPayList.addColumn();
		column.setKey(C_PAYS_LEASE_SEQ);
		column.setEditor(createTxtCellEditor(80, false));

		column = this.tblPayList.addColumn();
		column.setKey(C_PAYS_MONEY_DEFINE);
		column.setEditor(createTxtCellEditor(80, false));

		column = this.tblPayList.addColumn();
		column.setKey(C_PAYS_START_DATE);
		column.getStyleAttributes().setNumberFormat(DATE_FORMAT_STR);
		column.getStyleAttributes().setLocked(true);

		column = this.tblPayList.addColumn();
		column.setKey(C_PAYS_END_DATE);
		column.getStyleAttributes().setNumberFormat(DATE_FORMAT_STR);
		column.getStyleAttributes().setLocked(true);

		column = this.tblPayList.addColumn();
		column.setKey(C_PAYS_APP_PAY_DATE);
		column.getStyleAttributes().setNumberFormat(DATE_FORMAT_STR);

		// column = this.tblPayList.addColumn();
		// column.setKey(C_PAYS_FREE_DAYS);
		// column.getStyleAttributes().setNumberFormat();

		for (int i = 0; i < tenancyRooms.size(); i++) {
			TenancyRoomEntryInfo tenancyRoom = tenancyRooms.get(i);
			column = this.tblPayList.addColumn();
			// column.setUserObject(tenancyRoom);// �����޷���������,���޸Ľ��ʱ����TODO

			StringBuffer key = new StringBuffer();
			key.append(PREFIX_C_PAYS_ROOM).append(i).append(
					POSTFIX_C_PAYS_APP_AMOUNT);
			column.setKey(key.toString());
			column.setEditor(this.createFormattedCellEditor());
			column.getStyleAttributes().setHorizontalAlign(
					HorizontalAlignment.RIGHT);
			column.getStyleAttributes().setNumberFormat(
					FDCHelper.getNumberFtm(2));

			column = this.tblPayList.addColumn();
			key = new StringBuffer();
			key.append(PREFIX_C_PAYS_ROOM).append(i).append(
					POSTFIX_C_PAYS_ACT_AMOUNT);
			column.setKey(key.toString());
			column.getStyleAttributes().setHorizontalAlign(
					HorizontalAlignment.RIGHT);
			column.getStyleAttributes().setNumberFormat(
					FDCHelper.getNumberFtm(2));
			column.getStyleAttributes().setLocked(true);

			column = this.tblPayList.addColumn();
			key = new StringBuffer();
			key.append(PREFIX_C_PAYS_ROOM).append(i).append(
					POSTFIX_C_PAYS_ACT_PAY_DATE);
			column.setKey(key.toString());
			column.getStyleAttributes().setNumberFormat(DATE_FORMAT_STR);
			column.getStyleAttributes().setLocked(true);
		}

		for (int i = 0; i < tenAttachReses.size(); i++) {
			TenAttachResourceEntryInfo tenAttachRes = tenAttachReses.get(i);
			column = this.tblPayList.addColumn();
			// column.setUserObject(tenAttachRes);//��������Դ�������,���޸Ľ��ʱ����TODO
			StringBuffer key = new StringBuffer();
			key.append(PREFIX_C_PAYS_ATTACH_RES).append(i).append(
					POSTFIX_C_PAYS_APP_AMOUNT);
			column.setKey(key.toString());
			column.getStyleAttributes().setHorizontalAlign(
					HorizontalAlignment.RIGHT);
			column.getStyleAttributes().setNumberFormat(
					FDCHelper.getNumberFtm(2));

			column = this.tblPayList.addColumn();
			key = new StringBuffer();
			key.append(PREFIX_C_PAYS_ATTACH_RES).append(i).append(
					POSTFIX_C_PAYS_ACT_AMOUNT);
			column.setKey(key.toString());
			column.getStyleAttributes().setHorizontalAlign(
					HorizontalAlignment.RIGHT);
			column.getStyleAttributes().setNumberFormat(
					FDCHelper.getNumberFtm(2));

			column = this.tblPayList.addColumn();
			key = new StringBuffer();
			key.append(PREFIX_C_PAYS_ATTACH_RES).append(i).append(
					POSTFIX_C_PAYS_ACT_PAY_DATE);
			column.setKey(key.toString());
			column.getStyleAttributes().setNumberFormat(DATE_FORMAT_STR);
		}

		column = this.tblPayList.addColumn();
		column.setKey(C_PAYS_TOTAL_APP_PAY);
		column.getStyleAttributes().setHorizontalAlign(
				HorizontalAlignment.RIGHT);
		column.getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(2));
		column.getStyleAttributes().setLocked(true);
		column = this.tblPayList.addColumn();
		column.setKey(C_PAYS_TOTAL_ACT_PAY);
		column.getStyleAttributes().setHorizontalAlign(
				HorizontalAlignment.RIGHT);
		column.getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(2));
		column.getStyleAttributes().setLocked(true);
	}

	/**
	 * �������ں�����������Դ�ļ۸�,����������Դ�ĸ�����ϸע�뵽���޷�����
	 * 
	 * @param digit
	 * @param toIntegerType
	 * @param isToInteger
	 * @param daysPerYear
	 * @param rentCountType
	 * */
	private void fillTenAttachResPayList(
			TenAttachResourceEntryCollection tenAttachReses, List leaseList,
			RentFreeEntryCollection rentFrees, RentCountTypeEnum rentCountType,
			int daysPerYear, boolean isToInteger,
			ToIntegerTypeEnum toIntegerType, DigitEnum digit,
			boolean isToIntegerFee, ToIntegerTypeEnum toIntegerTypeFee,
			DigitEnum digitFee) throws BOSException {
		try {
			TenancyHelper.fillTenEntryPayList(tenAttachReses,
					TenAttachResourcePayListEntryCollection.class,
					TenAttachResourcePayListEntryInfo.class, this
							.getDepositMoneyDefine(),
					this.getRentMoneyDefine(), leaseList, rentFrees,
					rentCountType, daysPerYear, isToInteger, toIntegerType,
					digit, isToIntegerFee, toIntegerTypeFee, digitFee,
					(ChargeDateTypeEnum) this.comboChargeDateType
							.getSelectedItem(), this.spinChargeOffsetDays
							.getIntegerVlaue().intValue(),
					(Date) this.pkTenancyDate.getValue(),
					(Date) this.DPickFromMonth.getValue(), this.spinLeaseTime
							.getIntegerVlaue().intValue());
		} catch (InstantiationException e) {
			throw new BOSException(e);
		} catch (IllegalAccessException e) {
			throw new BOSException(e);
		}
	}

	/**
	 * ����������Դ������ϸ
	 * 
	 * @param tenAttachReses
	 * @throws BOSException
	 */
	private void fillTenAttachResPayList(
			TenAttachResourceEntryCollection tenAttachReses)
			throws BOSException {
		for (int i = 0; i < tenAttachReses.size(); i++) {
			TenAttachResourceEntryInfo tenAttach = tenAttachReses.get(i);

			TenAttachResourcePayListEntryCollection payList = tenAttach
					.getAttachResPayList();

			TenAttachResourcePayListEntryCollection newPayList = new TenAttachResourcePayListEntryCollection();
			TenAttachResourcePayListEntryInfo attachDepositPay = null;
			TenAttachResourcePayListEntryInfo attachFirstPay = null;
			if (payList.size() > 0) {
				attachDepositPay = payList.get(0);
				attachFirstPay = payList.get(1);
			} else {
				attachDepositPay = new TenAttachResourcePayListEntryInfo();
				attachFirstPay = new TenAttachResourcePayListEntryInfo();
				attachDepositPay.setTenAttachRes(tenAttach);
				attachFirstPay.setTenAttachRes(tenAttach);
			}
			attachDepositPay.setAppAmount(tenAttach.getDepositAmount());
			attachDepositPay.setLeaseSeq(1);
			attachDepositPay.setSeq(0);
			attachDepositPay.setAppPayDate(null);
			attachDepositPay.setStartDate(null);
			attachDepositPay.setEndDate(null);
			attachDepositPay.setMoneyDefine(getDepositMoneyDefine());
			newPayList.add(attachDepositPay);
			payList.clear();
			payList.addCollection(newPayList);
		}
	}

	/**
	 * �������޷��丶����ϸ
	 * 
	 * @param tenancyRooms
	 * @throws BOSException
	 */
	private void fillTenRoomPayList(TenancyRoomEntryCollection tenancyRooms)
			throws BOSException {
		for (int i = 0; i < tenancyRooms.size(); i++) {
			TenancyRoomEntryInfo tenRoomEntry = tenancyRooms.get(i);
			TenancyRoomPayListEntryCollection roomPayList = tenRoomEntry
					.getRoomPayList();
			TenancyRoomPayListEntryCollection newRoomPayList = new TenancyRoomPayListEntryCollection();
			TenancyRoomPayListEntryInfo roomDepositPay = null;
			if (roomPayList.size() > 0) {// ���ԭ�����и�����ϸ,��Ѻ����������϶�ͬʱ����
				// ���ִ���ʽ��Ҫ��Ϊ�˱���ԭ�и�����ϸ��¼��IDֵ,ʹ�������ύ���ⵥʱ,
				// �Ը���������ϸ��¼ִ�е���update����
				roomDepositPay = roomPayList.get(0);// Ѻ��
			} else {
				roomDepositPay = new TenancyRoomPayListEntryInfo();// Ѻ��
				roomDepositPay.setTenRoom(tenRoomEntry);
			}
			roomDepositPay.setAppAmount(tenRoomEntry.getDepositAmount());
			roomDepositPay.setLeaseSeq(1);
			roomDepositPay.setSeq(0);
			roomDepositPay.setAppPayDate(null);
			roomDepositPay.setStartDate(null);
			roomDepositPay.setEndDate(null);
			roomDepositPay.setMoneyDefine(getDepositMoneyDefine());
			newRoomPayList.add(roomDepositPay);
			roomPayList.clear();
			roomPayList.addCollection(newRoomPayList);
		}

	}

	/**
	 * �������ں����޷���۸�,�������丶����ϸע�뵽���޷�����
	 * 
	 * @param tenancyRooms
	 * @param leaseList
	 *            ���ڼ���
	 * @param rentFrees
	 *            �����¼����
	 * @param digit
	 * @param toIntegerType
	 * @param isToInteger
	 * @param daysPerYear
	 * @param rentCountType
	 * @throws BOSException
	 * */
	private void fillTenRoomPayList(TenancyRoomEntryCollection tenancyRooms,
			List leaseList, RentFreeEntryCollection rentFrees,
			RentCountTypeEnum rentCountType, int daysPerYear,
			boolean isToInteger, ToIntegerTypeEnum toIntegerType,
			DigitEnum digit, boolean isToIntegerFee,
			ToIntegerTypeEnum toIntegerTypeFee, DigitEnum digitFee)
			throws BOSException {
		try {// ����������� �������ڡ��ڶ���Ӧ������
			TenancyHelper.fillTenEntryPayList(tenancyRooms,
					TenancyRoomPayListEntryCollection.class,
					TenancyRoomPayListEntryInfo.class, this
							.getDepositMoneyDefine(),
					this.getRentMoneyDefine(), leaseList, rentFrees,
					rentCountType, daysPerYear, isToInteger, toIntegerType,
					digit, isToIntegerFee, toIntegerTypeFee, digitFee,
					(ChargeDateTypeEnum) this.comboChargeDateType
							.getSelectedItem(), this.spinChargeOffsetDays
							.getIntegerVlaue().intValue(),
					(Date) this.pkFristLeaseDate.getValue(),
					(Date) this.DPickFromMonth.getValue(), this.spinLeaseTime
							.getIntegerVlaue().intValue());
		} catch (InstantiationException e) {
			throw new BOSException(e);
		} catch (IllegalAccessException e) {
			throw new BOSException(e);
		}
	}

	private MoneyDefineInfo depositMoneyName = null;// Ѻ���������
	private MoneyDefineInfo rentMoneyName = null;// ����������

	private MoneyDefineInfo getDepositMoneyDefine() throws BOSException {
		if (depositMoneyName == null) {
			EntityViewInfo view = new EntityViewInfo();
			FilterInfo filter = new FilterInfo();
			filter.getFilterItems()
					.add(
							new FilterItemInfo("moneyType",
									MoneyTypeEnum.DepositAmount));
			filter.getFilterItems().add(
					new FilterItemInfo("sysType", MoneySysTypeEnum.TenancySys));
			view.setFilter(filter);
			MoneyDefineCollection moneyNames = MoneyDefineFactory
					.getRemoteInstance().getMoneyDefineCollection(view);
			if (!moneyNames.isEmpty()) {
				depositMoneyName = moneyNames.get(0);
			}
		}
		if (depositMoneyName == null) {
			MsgBox.showInfo(this, "���ȶ���Ѻ�����͵Ŀ������ͣ�");
			abort();
		}
		return depositMoneyName;
	}

	protected void pkStartDate_dataChanged(DataChangeEvent e) throws Exception {
		// �������޷�������¼
		reSetRentSetInfo(getTenRoomListFromView());
		updatePayListInfo();
		if (this.pkStartDate.getValue() != null
				&& this.pkEndDate.getValue() != null) {
			isFreeConSet(this.chkIsFreeContract.isSelected());
		}
		updateTotalInfo();
	}

	protected void pkEndDate_dataChanged(DataChangeEvent e) throws Exception {
		// �������޷�������¼
		reSetRentSetInfo(getTenRoomListFromView());
		updatePayListInfo();
		if (this.pkStartDate.getValue() != null
				&& this.pkEndDate.getValue() != null) {
			isFreeConSet(this.chkIsFreeContract.isSelected());
		}
		updateTotalInfo();
	}

	private void updateTotalInfo() {
		// ��Ҫ��ȡ������Ϣ���ͻ����ơ���ʼ���ڡ��������ڡ�������Ϣ
		Date startDate = this.pkStartDate.getValue() == null ? null
				: (Date) this.pkStartDate.getValue();
		Date endDate = this.pkEndDate.getValue() == null ? null
				: (Date) this.pkEndDate.getValue();
		
       if(startDate !=null && endDate != null){
    	   int  dayDiff = FDCDateHelper.getDiffDays(startDate, endDate)+1;
    	   this.txtMaxLease.setText(dayDiff+"");
    	   this.txtMaxLease.setHorizontalAlignment(11);
       }
		
		StringBuffer custName = new StringBuffer();
		int rc = this.tblCustomer.getRowCount();
		// ��ȡ�ͻ���Ϣ
		for (int i = 0; i < rc; i++) {
			custName.append(this.tblCustomer.getCell(i, "customer").getValue());
			if (i > 0) {
				custName.append(",");
			}
			
		}
		
		this.txtTenancyCustNew.setText(custName.toString());

		 if(this.tblRoom.getRowCount() > 0){
			 TenancyRoomEntryInfo entry = (TenancyRoomEntryInfo) this.tblRoom.getRow(0).getUserObject();
			 if(entry.getRoom().getTenancyArea() != null ){
				 this.txtRoomArea.setText(entry.getRoom().getTenancyArea()+"");
			 }else if(this.tblRoom.getRow(0).getCell(C_ROOM_BUILDING_AREA).getValue() != null){
				 this.txtRoomArea.setText(this.tblRoom.getRow(0).getCell(C_ROOM_BUILDING_AREA).getValue()+"");
			 }
			this.txtRoomArea.setHorizontalAlignment(11);
			Object room = this.tblRoom.getRowCount() > 0 ? this.tblRoom.getCell(0,"room").getValue() : null;
			this.txtFreeFeeInfo.setText(room+"");		
		 }
		 
		 
		 //��ʾ��������Ϣ
		 if(tblFree.getRowCount()>0){
			 int diffDay = 0;
			 this.tblFreeFeeInfo.removeRows();
			 for(int i=0;i<tblFree.getRowCount();i++){
				 Date sDate = (Date)tblFree.getCell(i, C_FREE_START_DATE).getValue();
				 Date eDate =  (Date)tblFree.getCell(i, C_FREE_END_DATE).getValue();
				 IRow row = this.tblFreeFeeInfo.addRow();
				 RentFreeEntryInfo rentFree = (RentFreeEntryInfo) tblFree.getRow(i).getUserObject();
				 row.getCell("freeStartDate").setValue(rentFree != null?rentFree.getFreeStartDate():FDCDateHelper.formatDate(sDate));
				 row.getCell("freeEndDate").setValue(rentFree != null?rentFree.getFreeEndDate():FDCDateHelper.formatDate(sDate));
				 row.getCell("freeType").setValue(rentFree != null?rentFree.getFreeTenancyType():tblFree.getCell(i, C_FREE_TENANCY_TYPE).getValue());
				 row.getCell(C_FREE_DES).setValue(rentFree != null?rentFree.getDescription():tblFree.getCell(i, C_FREE_DES).getValue());
				 diffDay+=FDCDateHelper.getDiffDays(sDate, eDate);
			 }
			 
			 this.txtMaxFreeDay.setText(diffDay+"");
			 this.txtMaxFreeDay.setHorizontalAlignment(11);
		 }
		 //��ʾ��֤����Ϣ
		 if(this.tblRentSet.getRow(0) != null){
			 BigDecimal depositAmount=FDCHelper.ZERO;
			 for (int k = 0; k < monDefineColl.size(); k++) {
					MoneyDefineInfo moneyInfo = monDefineColl.get(k);
					if (this.tblRentSet.getRow(0).getCell(moneyInfo.getNumber())!=null) {
						depositAmount=FDCHelper.add(depositAmount, this.tblRentSet.getRow(0).getCell(moneyInfo.getNumber()).getValue());
					}
			 }
			 this.txtDepoist.setText(depositAmount+"");
			 this.txtDepoist.setHorizontalAlignment(11);
		 }
		 

	}
	
	// 100*4*365
	// 100*5*365
	

	protected void tblRoom_editStopped(KDTEditEvent e) throws Exception {
		super.tblRoom_editStopped(e);

		// /* �����б��ٽ����������ã��ʲ���Ҫ��ˢ�º�ͬ������ϸ��Ϣ
		// ����޸ķ������,����Ҫ��д��𵥼�,���¸�����ϸ
		int colIndex = e.getColIndex();
		String colKey = this.tblRoom.getColumnKey(colIndex);

		if (!C_ROOM_FLAG_AT_TERM.equals(colKey)) {
			return;
		}
		// ��ֵû�б仯ʱ,�����и��²���
		if (e.getOldValue() != null) {
			if (e.getOldValue().equals(e.getValue())) {
				return;
			}
		}

		IRow row = this.tblRoom.getRow(e.getRowIndex());
		TenancyRoomEntryInfo tenRoom = (TenancyRoomEntryInfo) row
				.getUserObject();

		tenRoom.setFlagAtTerm((FlagAtTermEnum) row.getCell(C_ROOM_FLAG_AT_TERM)
				.getValue());

		// RentTypeEnum rentType = (RentTypeEnum)
		// row.getCell(C_ROOM_DEAL_RENT_TYPE).getValue();
		// BigDecimal rent = (BigDecimal)
		// row.getCell(C_ROOM_DEAL_RENT).getValue();
		// BigDecimal area = (BigDecimal)
		// row.getCell(C_ROOM_BUILDING_AREA).getValue();
		//
		// tenRoom.setDealRentType(rentType);
		// tenRoom.setDealRoomRent(rent);
		// if (rent == null) {
		// rent = FDCHelper.ZERO;
		// }
		//
		// if (C_ROOM_DEAL_RENT.equals(colKey)) {
		// BigDecimal rentPrice = null;
		// if (area != null && FDCHelper.ZERO.compareTo(area) != 0) {
		// rentPrice = rent.divide(area, 2, BigDecimal.ROUND_HALF_UP);
		// }
		// row.getCell(C_ROOM_DEAL_RENT_PRICE).setValue(rentPrice);
		// tenRoom.setDealRoomRentPrice(rentPrice);
		// }
		// updatePayListInfo();
		// */
	}

	// protected void tblAttachRes_editStopped(KDTEditEvent e) throws Exception
	// {
	// super.tblAttachRes_editStopped(e);
	// }

	protected void comboFlagAtTerm_itemStateChanged(ItemEvent e)
			throws Exception {
		super.comboFlagAtTerm_itemStateChanged(e);
		// TODO ���Է����¼�ĵ��ڱ�־����д
		// TODO �������ݲ�����
	}

	protected void comboChargeDateType_itemStateChanged(ItemEvent e)
			throws Exception {
		if (ChargeDateTypeEnum.FixedDate
				.equals((ChargeDateTypeEnum) comboChargeDateType
						.getSelectedItem())) {
			this.contFromMonth.setVisible(true);
			this.contChargeOffsetDays.setVisible(false);
		} else {
			this.contFromMonth.setVisible(false);
			this.contChargeOffsetDays.setVisible(true);
		}
		updatePayListInfo();
	}

	private void spinLeaseTime_stateChanged(ChangeEvent e) throws Exception {
		updatePayListInfo();
	}

	private void spinChargeOffsetDays_stateChanged(ChangeEvent e)
			throws Exception {
		updatePayListInfo();
	}

	boolean isEditPayList = false;

	protected void tblPayList_editStopped(KDTEditEvent e) throws Exception {
		int colIndex = e.getColIndex();
		int rowIndex = e.getRowIndex();
		IColumn col = tblPayList.getColumn(colIndex);
		IRow row = tblPayList.getRow(rowIndex);

		String key = col.getKey();
		// ����ʱ���޸ĸı���ϸ��ʱ��
		if (C_PAYS_START_DATE.equals(key) || C_PAYS_END_DATE.equals(key)
				|| C_PAYS_APP_PAY_DATE.equals(key)) {
			Date date = new Date();
			if (e.getValue() != null) {
				date = (Date) e.getValue();
			}
			int leaseSeq = ((Integer) row.getCell(C_PAYS_LEASE_SEQ).getValue())
					.intValue();
			for (int i = 0; i < tblPayList.getRowCount(); i++) {
				IRow row2 = tblPayList.getRow(i);
				int seq = ((Integer) row2.getCell(C_PAYS_LEASE_SEQ).getValue())
						.intValue();
				if (leaseSeq == seq) {
					if (row2.getUserObject() != null) {
						ITenancyPayListInfo iTeninfo = (ITenancyPayListInfo) row2
								.getUserObject();
						if (C_PAYS_START_DATE.equals(key)) {
							iTeninfo.setStartDate(date);
						} else if (C_PAYS_END_DATE.equals(key)) {
							iTeninfo.setEndDate(date);

						} else {
							iTeninfo.setAppPayDate(date);
						}
						row2.setUserObject(iTeninfo);
						row2.getCell(key).setValue(date);
					}
				}
			}
		}
		// ��ϸ�е�Ӧ�������ĸı���ܺϼƺ͸�����ϼ�
		if (key.startsWith(PREFIX_C_PAYS_ROOM)
				&& key.endsWith(POSTFIX_C_PAYS_APP_AMOUNT)) {
			BigDecimal oldAppAmount = FDCHelper.ZERO;
			BigDecimal newAppAmount = FDCHelper.ZERO;
			if (e.getOldValue() != null) {
				oldAppAmount = (BigDecimal) e.getOldValue();
			}
			if (e.getValue() != null) {
				newAppAmount = (BigDecimal) e.getValue();
			}
			BigDecimal actRevAmount = FDCHelper.ZERO;
			for (int k = 0; k < this.tblPayList.getColumnCount(); k++) {
				if (this.tblPayList.getColumnKey(k).indexOf(
						POSTFIX_C_PAYS_ACT_AMOUNT) >= 0
						&& this.tblPayList.getColumnKey(k).indexOf(
								POSTFIX_C_PAYS_ACT_PAY_DATE) < 0) {
					actRevAmount = (BigDecimal) row.getCell(k).getValue();
				}
			}
			if (newAppAmount.compareTo(actRevAmount) < 0) {
				FDCMsgBox.showWarning(this, "Ӧ�ս���С��ʵ�ս�");
				row.getCell(key).setValue(oldAppAmount);
				SysUtil.abort();
			}
			BigDecimal difAmount = newAppAmount.subtract(oldAppAmount);
			int leaseSeq = ((Integer) row.getCell(C_PAYS_LEASE_SEQ).getValue())
					.intValue();
			for (int i = 0; i < tblPayList.getRowCount(); i++) {
				IRow row2 = tblPayList.getRow(i);
				int seq = ((Integer) row2.getCell(C_PAYS_LEASE_SEQ).getValue())
						.intValue();
				if (leaseSeq == seq) {
					if (row2.getUserObject() == null) {
						BigDecimal huizong = (BigDecimal) row2.getCell(key)
								.getValue();
						if (huizong == null) {
							huizong = FDCHelper.ZERO;
						}
						row2.getCell(key).setValue(huizong.add(difAmount));
					}
				}
			}

			for (int i = 0; i < tblPayList.getRowCount(); i++) {
				IRow row2 = tblPayList.getRow(i);
				int seq = ((Integer) row2.getCell(C_PAYS_LEASE_SEQ).getValue())
						.intValue();
				if (leaseSeq == seq) {
					if (row2.getUserObject() == null) {
						BigDecimal oldTotalAppPayAmount = (BigDecimal) row2
								.getCell(C_PAYS_TOTAL_APP_PAY).getValue();
						if (oldTotalAppPayAmount == null) {
							oldTotalAppPayAmount = FDCHelper.ZERO;
						}

						row2.getCell(C_PAYS_TOTAL_APP_PAY).setValue(
								oldTotalAppPayAmount.add(difAmount));
					}
				}
			}

			BigDecimal oldTotalAppPayAmount = (BigDecimal) row.getCell(
					C_PAYS_TOTAL_APP_PAY).getValue();
			if (oldTotalAppPayAmount == null) {
				oldTotalAppPayAmount = FDCHelper.ZERO;
			}

			row.getCell(C_PAYS_TOTAL_APP_PAY).setValue(
					oldTotalAppPayAmount.add(difAmount));

			// ������޸ĵ�Ѻ���������,��Ҫ�޸ĺ�ͬ��Ϣ�еĵĽ��
			// updateDepositAndFirstPayRent();
		} else if (key.startsWith(PREFIX_C_PAYS_ATTACH_RES)) {
			// TenAttachResourcePayListEntryInfo pay =
			// (TenAttachResourcePayListEntryInfo) row.getUserObject();
			if (key.endsWith(POSTFIX_C_PAYS_APP_AMOUNT)) {
				// pay.setAppAmount((BigDecimal) row.getCell(key));
			}
		}
		isEditPayList = true;
		updatePayListInfo();
		isEditPayList = false;
	}

	protected void tblOtherPayList_editStopped(KDTEditEvent e) throws Exception {
		int colIndex = e.getColIndex();
		int rowIndex = e.getRowIndex();
		IColumn col = tblPayList.getColumn(colIndex);
		IRow row = tblPayList.getRow(rowIndex);
		if (col.getKey().equals("appAmount")) {
			BigDecimal oldAppAmount = FDCHelper.ZERO;
			BigDecimal newAppAmount = FDCHelper.ZERO;
			if (e.getOldValue() != null) {
				oldAppAmount = (BigDecimal) e.getOldValue();
			}
			if (e.getValue() != null) {
				newAppAmount = (BigDecimal) e.getValue();
			}

			BigDecimal actRevAmount = FDCHelper.ZERO;
			if (row.getCell("actRevAmount").getValue() != null)
				actRevAmount = (BigDecimal) row.getCell("actRevAmount")
						.getValue();
			if (newAppAmount.compareTo(actRevAmount) < 0) {
				FDCMsgBox.showWarning(this, "Ӧ�ս���С��ʵ�ս�");
				row.getCell("appAmount").setValue(oldAppAmount);
				SysUtil.abort();
			}
		}
	}

	/**
	 * @�޸���: ����
	 * @�޸�ʱ��: 2010-9-7
	 * @����: ��������ͬ�ͻ�ʱ��ʾ�������
	 */
	protected void tblCustomerInfo_editStopped(KDTEditEvent e) throws Exception {
		super.tblCustomerInfo_editStopped(e);

		if (e.getColIndex() == 1) {
			IRow row = this.tblCustomer.getRow(e.getRowIndex());
			int i = row.getRowIndex() - 1;
			FDCCustomerInfo customer = (FDCCustomerInfo) row.getCell(
					C_CUS_CUSTOMER).getValue();
			if (customer == null) {
				return;
			}
			boolean flag = true;
			// �Ա���ͬ�ͻ� tim_gao 2010-9-7
			while (i >= 0 && flag) {
				FDCCustomerInfo compra = (FDCCustomerInfo) this.tblCustomer
						.getRow(i).getCell(C_CUS_CUSTOMER).getValue();
				if (customer.getId().toString().equals(
						compra.getId().toString())) {
					MsgBox.showWarning(this, "���" + (i + 1) + "�пͻ���ͬ,��ֹ���!");
					flag = false;
				}
				i--;
			}
			if (customer != null && flag) {
				row.getCell(C_CUS_PHONE).setValue(customer.getPhone());
				row.getCell(C_CUS_POSTALCODE)
						.setValue(customer.getPostalcode());
				row.getCell(C_CUS_CERTIFICATE_NAME).setValue(
						customer.getCertificateName());
				row.getCell(C_CUS_CERTIFICATE_NUMBER).setValue(
						customer.getCertificateNumber());
				row.getCell(C_CUS_MAIL_ADDRESS).setValue(
						customer.getMailAddress());
				row.getCell(C_CUS_BOOK_DATE).setValue(customer.getCreateTime());
			} else {
				row.getCell(C_CUS_PHONE).setValue(null);
				row.getCell(C_CUS_POSTALCODE).setValue(null);
				row.getCell(C_CUS_CERTIFICATE_NAME).setValue(null);
				row.getCell(C_CUS_CERTIFICATE_NUMBER).setValue(null);
				row.getCell(C_CUS_MAIL_ADDRESS).setValue(null);
				row.getCell(C_CUS_BOOK_DATE).setValue(null);
			}
		}

		updateTotalInfo();
	}

	/**
	 * �����������,������Դ���������ڵ����.���µ��ɽ����,��׼���ͷ��������,������Դ�����
	 * */
	private void updateTotalRent() {
		Date startDate = (Date) this.pkStartDate.getValue();
		Date endDate = (Date) this.pkEndDate.getValue();

		boolean hasRentDate = true;
		// �����ʼ�ͽ�������Ϊ�գ�
		if (startDate == null || endDate == null) {
			hasRentDate = false;
		} else {
			startDate = FDCDateHelper.getDayBegin(startDate);
			endDate = FDCDateHelper.getDayBegin(endDate);
		}

		BigDecimal totalRoomStandardRent = FDCHelper.ZERO;
		BigDecimal totalRoomDealRent = FDCHelper.ZERO;
		BigDecimal totalBuildingArea = FDCHelper.ZERO;

		BigDecimal totalAttachStandardRent = FDCHelper.ZERO;
		BigDecimal totalAttachDealRent = FDCHelper.ZERO;

		for (int i = 0; i < this.tblRoom.getRowCount(); i++) {
			IRow row = this.tblRoom.getRow(i);
			TenancyRoomEntryInfo tenancyRoom = (TenancyRoomEntryInfo) row
					.getUserObject();
			RentTypeEnum standardRentType = tenancyRoom.getStandardRentType();
			standardRentType = standardRentType == null ? RentTypeEnum.RentByMonth
					: standardRentType;
			BigDecimal standardRent = tenancyRoom.getStandardRoomRent();
			standardRent = standardRent == null ? FDCHelper.ZERO : standardRent;
			if (hasRentDate) {
				BigDecimal rent2 = new BigDecimal(0);
				if (TenancyHelper.getRentBetweenDate(startDate, endDate,
						standardRentType, standardRent) != null) {
					rent2 = TenancyHelper.getRentBetweenDate(startDate,
							endDate, standardRentType, standardRent);
				}
				totalRoomStandardRent = totalRoomStandardRent.add(rent2);
				BigDecimal totalRent = FDCHelper.ZERO;
				if (tenancyRoom.getRoomTotalRent() != null) {
					totalRent = tenancyRoom.getRoomTotalRent();
				}
				totalRoomDealRent = totalRoomDealRent.add(totalRent);
			}

			BigDecimal buildingArea = tenancyRoom.getBuildingArea();
			totalBuildingArea = totalBuildingArea
					.add(buildingArea == null ? FDCHelper.ZERO : buildingArea);
		}

		for (int i = 0; i < this.tblAttachRes.getRowCount(); i++) {
			IRow row = this.tblAttachRes.getRow(i);
			TenAttachResourceEntryInfo tenAttach = (TenAttachResourceEntryInfo) row
					.getUserObject();

			RentTypeEnum standardRentType = tenAttach.getStandardRentType();
			BigDecimal standardRent = tenAttach.getStandardAttachResRent();
			if (hasRentDate) {
				totalAttachStandardRent = totalRoomStandardRent
						.add(TenancyHelper.getRentBetweenDate(startDate,
								endDate, standardRentType, standardRent));
				BigDecimal toalRent = new BigDecimal(0);
				if (tenAttach.getAttachTotalRent() == null) {
					toalRent = new BigDecimal(0);
				} else {
					toalRent = tenAttach.getAttachTotalRent();
				}
				totalAttachDealRent = totalRoomDealRent.add(toalRent);
			}
		}

		this.txtTotalRoomStandardRent.setValue(totalRoomStandardRent);
		this.txtTotalRoomDealRent.setValue(totalRoomDealRent);
		this.txtTotalBuildingArea.setValue(totalBuildingArea);

		this.txtTotalAttachResStandardRent.setValue(totalAttachStandardRent);
		this.txtTotalAttachResDealRent.setValue(totalAttachDealRent);

		BigDecimal totalStandardRent = totalRoomStandardRent
				.add(totalAttachStandardRent);
		BigDecimal totalDealRent = totalRoomDealRent.add(totalAttachDealRent);

		this.txtStandardTotalRent.setValue(totalStandardRent);
		this.txtDealTotalRent.setValue(getDealTotalRent());
	}

	// ��ͬ�ܳɽ����Ӧ�ð�������ɽ����+�����Է���+������Դ�ɽ����+�����Է���
	// ��ͬ��׼���=�����׼���+������Դ��׼���(��׼���û�б�׼�����Է���)
	// �����ܳɽ����=����ɽ����+�����Է���
	// �����ܱ�׼���=�����׼���
	private BigDecimal getDealTotalRent() {
		BigDecimal totalAmount = new BigDecimal(0);
		List appPayColKeys = getAppPayColKeys();
		for (int i = 0; i < tblPayList.getRowCount(); i++) {
			IRow row = this.tblPayList.getRow(i);
			if (row.getUserObject() != null) {
				for (int j = 0; j < appPayColKeys.size(); j++) {
					String key = (String) appPayColKeys.get(j);
					ICell cell = row.getCell(key);
					BigDecimal appAmount = (BigDecimal) cell.getValue();
					if (appAmount == null) {
						appAmount = new BigDecimal(0);
					}
					TenancyRoomPayListEntryInfo tenPayInfo = (TenancyRoomPayListEntryInfo) row
							.getCell(key).getUserObject();
					if (tenPayInfo != null) {
						if (MoneyTypeEnum.RentAmount.equals(tenPayInfo
								.getMoneyDefine().getMoneyType())) {
							totalAmount = totalAmount.add(appAmount);
						}
					}
				}
			}
		}
		return totalAmount;
	}

	/**
	 * ����Ѻ����׸����
	 * */
	private void updateDepositAndFirstPayRent() {
		BigDecimal depositeAmount = FDCHelper.ZERO;
		BigDecimal firstPayAmount = FDCHelper.ZERO;

		if (this.tblPayList.getRowCount() != 0) {
			IRow depositeRow = this.tblPayList.getRow(0);
			BigDecimal tmp = (BigDecimal) depositeRow.getCell(
					C_PAYS_TOTAL_APP_PAY).getValue();
			if (tmp != null) {
				depositeAmount = tmp;
			}
			if (this.tblPayList.getRowCount() > 1) {
				IRow firstPayRow = this.tblPayList.getRow(1);
				tmp = (BigDecimal) firstPayRow.getCell(C_PAYS_TOTAL_APP_PAY)
						.getValue();
				if (tmp != null) {
					firstPayAmount = tmp;
				}
			}
		}

		this.txtDepositAmount.setValue(depositeAmount);
		this.txtFirstPayRent.setValue(firstPayAmount);
	}

	/**
	 * ����Ѻ��
	 * */
	private void updateDeposit() {
		BigDecimal depositeAmount = FDCHelper.ZERO;

		if (this.tblPayList.getRowCount() != 0) {
			IRow depositeRow = this.tblPayList.getRow(0);
			BigDecimal tmp = (BigDecimal) depositeRow.getCell(
					C_PAYS_TOTAL_APP_PAY).getValue();
			if (tmp != null) {
				depositeAmount = tmp;
			}
		}

		this.txtDepositAmount.setValue(depositeAmount);
		this.txtDepoist.setText(depositeAmount+"");
	}

	/**
	 * �����Ƿ�������ʽ��ͬ��ȷ���Ƿ���Ҫ���¸�����ϸ ���¸�����ϸ��ص���Ϣ �漰
	 * {���ӷ���,���ٷ���,���Ӹ�����Դ,���ٸ�����Դ,�޸ķ���ʵ�����,�޸ĸ�����Դʵ�����
	 * �޸�������ʼʱ��,����ʱ��,�����ڼ䳤��,��������,�������Ƿ�������� �޸�����������,������ƫ������} ��Щ��������Ҫִ�иø��²���
	 * 
	 * @throws BOSException
	 * */
	private void updatePayListInfo(boolean isFreeContract) throws BOSException {
		RentCountTypeEnum rentCountType = (RentCountTypeEnum) this.comboRentCountType
				.getSelectedItem();
		int daysPerYear = this.txtDaysPerYear.getIntegerValue() == null ? 360
				: this.txtDaysPerYear.getIntegerValue().intValue();
		boolean isToInteger = this.chkIsAutoToInteger.isSelected();
		ToIntegerTypeEnum toIntegerType = (ToIntegerTypeEnum) this.comboToIntegerType
				.getSelectedItem();
		DigitEnum digit = (DigitEnum) this.comboDigit.getSelectedItem();
		// ������� �����Է�������
		boolean isToIntegerFee = this.chkIsAutoToIntegerForFee.isSelected();
		ToIntegerTypeEnum toIntegerTypeFee = (ToIntegerTypeEnum) this.comboToIntegerTypeFee
				.getSelectedItem();
		DigitEnum digitFee = (DigitEnum) this.comboDigitFee.getSelectedItem();

		RentFreeEntryCollection rentFrees = getRentFreesFromView();

		TenAttachResourceEntryCollection tenAttachReses = getTenAttachResListFromView();
		TenancyRoomEntryCollection tenancyRooms = getTenRoomListFromView();
		TenancyBillInfo tenRoomInfo = this.editData;

		List leaseList = getLeaseListFromView();
		// �����������
		if (leaseList == null) {
			leaseList = new ArrayList();
		}

		if (isDynamicStartDate() && this.pkStartDate.getValue() == null) {
			this.txtLeaseCount.setValue(tenRoomInfo.getLeaseCount());
			fillTenRoomPayList(tenancyRooms);
			fillTenAttachResPayList(tenAttachReses);
			updatePayList(tenancyRooms, tenAttachReses, leaseList);
//			 updateDeposit();
		} else {
			// ����������
			updateLeaseCount(leaseList);
			if (!isFreeContract
					&& (this.getOprtState().equals("ADDNEW") || this
							.getOprtState().equals("EDIT"))) {
				fillTenRoomPayList(tenancyRooms, leaseList, rentFrees,
						rentCountType, daysPerYear, isToInteger, toIntegerType,
						digit, isToIntegerFee, toIntegerTypeFee, digitFee);
				fillTenAttachResPayList(tenAttachReses, leaseList, rentFrees,
						rentCountType, daysPerYear, isToInteger, toIntegerType,
						digit, isToIntegerFee, toIntegerTypeFee, digitFee);
			} else {
				fillFreeConTenRoomPayList(tenancyRooms);
			}
			// ���¸�����ϸ�б�
			if (this.getOprtState().equals("VIEW")) {
				tenancyRooms = this.editData.getTenancyRoomList();
			}
			if (!isEditPayList) {
				updatePayList(tenancyRooms, tenAttachReses, leaseList);
			}
			// ���º�ͬѺ����׸����
			// updateDepositAndFirstPayRent();

			// �����ܱ�׼���,�ɽ������Ϣ
			updateTotalRent();

			// ����Լ�������
			updatePromissoryAgentFee();
		}
	}

	// ����ʽ��ͬ������ϸ
	protected void fillFreeConTenRoomPayList(
			TenancyRoomEntryCollection tenancyRooms) {
		if (tenancyRooms == null || tenancyRooms.isEmpty()) {
			return;
		}
		// �Ȼ������Ӧ��������
		List appPayColKeys = getAppPayColKeys();
		BigDecimal oneEntryTotalRent = null;
		for (int i = 0; i < tenancyRooms.size(); i++) {
			oneEntryTotalRent = FDCHelper.ZERO;

			TenancyRoomEntryInfo tenEntry = (TenancyRoomEntryInfo) tenancyRooms
					.getObject(i);
			TenancyRoomPayListEntryCollection newPayList = new TenancyRoomPayListEntryCollection();
			for (int j = 0; j < tblPayList.getRowCount(); j++) {
				IRow row = this.tblPayList.getRow(j);
				if (row.getUserObject() != null) {
					ITenancyPayListInfo payListInfo = (ITenancyPayListInfo) row
							.getUserObject();
					Date appDate = (Date) row.getCell(C_PAYS_APP_PAY_DATE)
							.getValue();
					if (appDate == null)
						return;
					appDate = FDCDateHelper.getDayBegin(appDate);
					Date startDate = (Date) row.getCell(C_PAYS_START_DATE)
							.getValue();
					startDate = FDCDateHelper.getDayBegin(startDate);
					Date endDate = (Date) row.getCell(C_PAYS_END_DATE)
							.getValue();
					endDate = FDCDateHelper.getDayBegin(endDate);
					int leaseSeq = payListInfo.getLeaseSeq();
					int seq = payListInfo.getSeq();
					MoneyDefineInfo money = payListInfo.getMoneyDefine();
					for (int m = 0; m < appPayColKeys.size(); m++) {
						String key = (String) appPayColKeys.get(m);
						ICell cell = row.getCell(key);
						BigDecimal appAmount = (BigDecimal) cell.getValue();
						TenancyRoomPayListEntryInfo tenPayInfo = (TenancyRoomPayListEntryInfo) row
								.getCell(key).getUserObject();
						TenancyRoomPayListEntryInfo tay = new TenancyRoomPayListEntryInfo();
						if (tenEntry.getRoom() != null && tenPayInfo != null) {
							if (tenEntry.getRoom().getId().toString().equals(
									tenPayInfo.getTenRoom().getRoom().getId()
											.toString())) {
								// ����������Ҫ���� xin_wang 2010.11.24
								if (money != null) {
									if (MoneyTypeEnum.RentAmount.equals(money
											.getMoneyType())) {
										oneEntryTotalRent = FDCHelper.add(
												oneEntryTotalRent, appAmount);
									}
								}
								tay.setTenRoom(tenPayInfo.getTenRoom());
								tay.setAppAmount(appAmount);
								tay.setAppPayDate(appDate);
								tay.setStartDate(startDate);
								tay.setEndDate(endDate);
								tay.setSeq(seq);
								tay.setMoneyDefine(money);
								tay.setLeaseSeq(leaseSeq);
								newPayList.add(tay);
							}
						}
					}
				}
			}
			tenEntry.getPayList().clear();
			tenEntry.getPayList().addObjectCollection(newPayList);
			tenEntry.setTotalRent(oneEntryTotalRent);
		}
	}

	/**
	 * ���¸�����ϸ��ص���Ϣ �漰 {���ӷ���,���ٷ���,���Ӹ�����Դ,���ٸ�����Դ,�޸ķ���ʵ�����,�޸ĸ�����Դʵ�����
	 * �޸�������ʼʱ��,����ʱ��,�����ڼ䳤��,��������,�������Ƿ�������� �޸�����������,������ƫ������} ��Щ��������Ҫִ�иø��²���
	 * 
	 * @throws BOSException
	 * */
	public void updatePayListInfo() throws BOSException {
		updatePayListInfo(this.chkIsFreeContract.isSelected());
	}

	private void updatePayList(TenancyRoomEntryCollection tenancyRooms,
			TenAttachResourceEntryCollection tenAttachReses, List leaseList) {
		updatePayListColumn(tenancyRooms, tenAttachReses);

		IRow headRow = this.tblPayList.addHeadRow();
		headRow.getCell(C_PAYS_MONEY_DEFINE).setValue("��������");
		headRow.getCell(C_PAYS_LEASE_SEQ).setValue("�������");
		headRow.getCell(C_PAYS_START_DATE).setValue("��ʼ����");
		headRow.getCell(C_PAYS_END_DATE).setValue("��������");
		headRow.getCell(C_PAYS_APP_PAY_DATE).setValue("Ӧ������");

		for (int i = 0; i < tenancyRooms.size(); i++) {
			String roomNum = tenancyRooms.get(i).getRoom().getName();
			headRow.getCell(PREFIX_C_PAYS_ROOM + i + POSTFIX_C_PAYS_APP_AMOUNT)
					.setValue(roomNum);
			headRow.getCell(PREFIX_C_PAYS_ROOM + i + POSTFIX_C_PAYS_ACT_AMOUNT)
					.setValue(roomNum);
			headRow.getCell(
					PREFIX_C_PAYS_ROOM + i + POSTFIX_C_PAYS_ACT_PAY_DATE)
					.setValue(roomNum);
		}

		for (int i = 0; i < tenAttachReses.size(); i++) {
			String attNum = tenAttachReses.get(i).getAttachResource().getName();
			headRow.getCell(
					PREFIX_C_PAYS_ATTACH_RES + i + POSTFIX_C_PAYS_APP_AMOUNT)
					.setValue(attNum);
			headRow.getCell(
					PREFIX_C_PAYS_ATTACH_RES + i + POSTFIX_C_PAYS_ACT_AMOUNT)
					.setValue(attNum);
			headRow.getCell(
					PREFIX_C_PAYS_ATTACH_RES + i + POSTFIX_C_PAYS_ACT_PAY_DATE)
					.setValue(attNum);
		}
		headRow.getCell(C_PAYS_TOTAL_APP_PAY).setValue("Ӧ�պϼ�");
		headRow.getCell(C_PAYS_TOTAL_ACT_PAY).setValue("ʵ�պϼ�");

		headRow = this.tblPayList.addHeadRow();
		headRow.getCell(C_PAYS_MONEY_DEFINE).setValue("��������");
		headRow.getCell(C_PAYS_LEASE_SEQ).setValue("�������");
		headRow.getCell(C_PAYS_START_DATE).setValue("��ʼ����");
		headRow.getCell(C_PAYS_END_DATE).setValue("��������");
		headRow.getCell(C_PAYS_APP_PAY_DATE).setValue("Ӧ������");

		for (int i = 0; i < tenancyRooms.size(); i++) {
			headRow.getCell(PREFIX_C_PAYS_ROOM + i + POSTFIX_C_PAYS_APP_AMOUNT)
					.setValue("Ӧ�ս��");
			headRow.getCell(PREFIX_C_PAYS_ROOM + i + POSTFIX_C_PAYS_ACT_AMOUNT)
					.setValue("ʵ�ս��");
			headRow.getCell(
					PREFIX_C_PAYS_ROOM + i + POSTFIX_C_PAYS_ACT_PAY_DATE)
					.setValue("ʵ������");
		}

		for (int i = 0; i < tenAttachReses.size(); i++) {
			headRow.getCell(
					PREFIX_C_PAYS_ATTACH_RES + i + POSTFIX_C_PAYS_APP_AMOUNT)
					.setValue("Ӧ�ս��");
			headRow.getCell(
					PREFIX_C_PAYS_ATTACH_RES + i + POSTFIX_C_PAYS_ACT_AMOUNT)
					.setValue("ʵ�ս��");
			headRow.getCell(
					PREFIX_C_PAYS_ATTACH_RES + i + POSTFIX_C_PAYS_ACT_PAY_DATE)
					.setValue("ʵ������");
		}
		headRow.getCell(C_PAYS_TOTAL_APP_PAY).setValue("Ӧ�պϼ�");
		headRow.getCell(C_PAYS_TOTAL_ACT_PAY).setValue("ʵ�պϼ�");

		tblPayList.getHeadMergeManager().mergeBlock(0, 0, 1,
				7 + (tenancyRooms.size() + tenAttachReses.size()) * 3 - 1,
				KDTMergeManager.FREE_MERGE);

		// if (leaseList == null || leaseList.isEmpty()) {
		// return;
		// }
		if (tenancyRooms.size() == 0 && tenAttachReses.size() == 0) {
			return;
		}

		// Map leaseMap = parsePayListByLease();

		// ע��:����ͳһ���´���,��������͸�����Դ�ĸ�����ϸӦ�ö��ǰ����������˳�������.

		// ȡһ������������������
		ITenancyEntryInfo tenObj = tenancyRooms.get(0);
		if (tenObj == null) {
			tenObj = tenAttachReses.get(0);
		}

		if (tenObj == null) {
			return;
		}

		IObjectCollection oneObjPays = tenObj.getPayList();

		Map tmpMap = new TreeMap();
		for (int i = 0; i < oneObjPays.size(); i++) {
			ITenancyPayListInfo pay = (ITenancyPayListInfo) oneObjPays
					.getObject(i);
			int leaseSeq = pay.getLeaseSeq();

			if (tmpMap.get(new Integer(leaseSeq)) == null) {
				List paysGroupByLease = new ArrayList();
				paysGroupByLease.add(pay);
				tmpMap.put(new Integer(leaseSeq), paysGroupByLease);
			} else {
				List tmpPays = (List) tmpMap.get(new Integer(leaseSeq));
				tmpPays.add(pay);
				Collections.sort(tmpPays, new Comparator() {
					public int compare(Object arg0, Object arg1) {
						ITenancyPayListInfo pay0 = (ITenancyPayListInfo) arg0;
						ITenancyPayListInfo pay1 = (ITenancyPayListInfo) arg1;
						if (pay0 == null || pay1 == null) {
							return 0;
						}
						return pay0.getSeq() - pay1.getSeq();
					}
				});
			}
		}
		if (!isEditPayList) {
			cbMD.removeAllItems();
			cbMD.addItem(new KDComboBoxMultiColumnItem(new String[] { "" }));
		}
		Set md = new HashSet();
		this.getUIContext().put("tmpMap", tmpMap);
		for (Iterator itor = tmpMap.keySet().iterator(); itor.hasNext();) {
			Integer key = (Integer) itor.next();
			List pays = (List) tmpMap.get(key);
			if (pays.size() == 1) {
				ITenancyPayListInfo tPay = (ITenancyPayListInfo) pays.get(0);
				IRow row = this.tblPayList.addRow();
				row.getStyleAttributes().setBackground(
						new java.awt.Color(246, 246, 191));
				
				if(tPay instanceof TenancyRoomPayListEntryInfo&&((TenancyRoomPayListEntryInfo)tPay).isIsUnPay()){
					for(int i=0;i<this.tblPayList.getColumnCount();i++){
						if(!this.tblPayList.getColumnKey(i).equals(C_PAYS_LEASE_SEQ)){
							row.getCell(i).getStyleAttributes().setBackground(Color.CYAN);
						}
					}
				}
				row.setTreeLevel(0);
				row.setUserObject(tPay);

				row.getCell(C_PAYS_MONEY_DEFINE)
						.setValue(tPay.getMoneyDefine());
				row.getCell(C_PAYS_LEASE_SEQ).setValue(
						new Integer(tPay.getLeaseSeq()));
				row.getCell(C_PAYS_START_DATE).setValue(tPay.getStartDate());
				row.getCell(C_PAYS_END_DATE).setValue(tPay.getEndDate());
				row.getCell(C_PAYS_APP_PAY_DATE).setValue(tPay.getAppPayDate());
				if (!isEditPayList
						&& !md.contains(tPay.getMoneyDefine().getName())) {
					cbMD.addItem(new KDComboBoxMultiColumnItem(
							new String[] { tPay.getMoneyDefine().getName() }));
					md.add(tPay.getMoneyDefine().getName());
				}
			} else {
				IRow unionRow = this.tblPayList.addRow();
				unionRow.setTreeLevel(0);
				unionRow.getStyleAttributes().setBackground(
						new java.awt.Color(246, 246, 191));

				StringBuffer moneyDes = new StringBuffer();
				for (int i = 0; i < pays.size(); i++) {
					ITenancyPayListInfo tPay = (ITenancyPayListInfo) pays
							.get(i);
					IRow entryRow = this.tblPayList.addRow();
					if(tPay instanceof TenancyRoomPayListEntryInfo&&((TenancyRoomPayListEntryInfo)tPay).isIsUnPay()){
						for(int j=0;j<this.tblPayList.getColumnCount();j++){
							if(!this.tblPayList.getColumnKey(j).equals(C_PAYS_LEASE_SEQ)){
								entryRow.getCell(j).getStyleAttributes().setBackground(Color.CYAN);
							}
						}
					}
					entryRow.setTreeLevel(1);
					entryRow.setUserObject(tPay);

					entryRow.getCell(C_PAYS_MONEY_DEFINE).setValue(
							"  " + tPay.getMoneyDefine());
					entryRow.getCell(C_PAYS_LEASE_SEQ).setValue(key);
					entryRow.getCell(C_PAYS_START_DATE).setValue(
							tPay.getStartDate());
					entryRow.getCell(C_PAYS_END_DATE).setValue(
							tPay.getEndDate());
					entryRow.getCell(C_PAYS_APP_PAY_DATE).setValue(
							tPay.getAppPayDate());

					if (i != 0) {
						moneyDes.append("��");
					}
					moneyDes.append(tPay.getMoneyDefine());

					if (i == 0) {
						unionRow.getCell(C_PAYS_START_DATE).setValue(
								tPay.getStartDate());
						unionRow.getCell(C_PAYS_END_DATE).setValue(
								tPay.getEndDate());
						unionRow.getCell(C_PAYS_APP_PAY_DATE).setValue(
								tPay.getAppPayDate());
					}
					if (!isEditPayList
							&& !md.contains(tPay.getMoneyDefine().getName())) {
						cbMD
								.addItem(new KDComboBoxMultiColumnItem(
										new String[] { tPay.getMoneyDefine()
												.getName() }));
						md.add(tPay.getMoneyDefine().getName());
					}
				}

				unionRow.getCell(C_PAYS_MONEY_DEFINE).setValue(
						moneyDes.toString());
				unionRow.getCell(C_PAYS_LEASE_SEQ).setValue(key);
			}
		}

		Map unionRows = new HashMap();
		IRow currentUnionRow = null;
		for (int i = 0; i < this.tblPayList.getRowCount(); i++) {
			IRow row = this.tblPayList.getRow(i);

			// ���ںϼ��У�����ͳһ����
			if (row.getUserObject() == null) {
				unionRows.put(row, new ArrayList());
				currentUnionRow = row;
				continue;
			} else {
				if (row.getTreeLevel() == 1) {
					List entryRows = (List) unionRows.get(currentUnionRow);
					entryRows.add(row);
				} else {
					currentUnionRow = null;
				}

				ITenancyPayListInfo onePay = (ITenancyPayListInfo) row
						.getUserObject();
				int seq = onePay.getSeq();

				BigDecimal totalAppPayThisLeas = FDCHelper.ZERO;
				BigDecimal totalActPayThisLeas = FDCHelper.ZERO;
				for (int j = 0; j < tenancyRooms.size(); j++) {
					TenancyRoomEntryInfo tenancyRoom = tenancyRooms.get(j);
					TenancyRoomPayListEntryCollection tmpPayList = tenancyRoom
							.getRoomPayList();
					TenancyRoomPayListEntryInfo tmpPayEntry = tmpPayList
							.get(seq);
					if (tmpPayEntry == null) {
						log.error("�߼������������,tenancyRoom.id:"
								+ tenancyRoom.getId());
						continue;
					}

					row.getCell(
							PREFIX_C_PAYS_ROOM + j + POSTFIX_C_PAYS_APP_AMOUNT)
							.setValue(tmpPayEntry.getAppAmount());
					// tmpPayEntryֻ�洢��tenancyRoom��IDֵ,��������tenancyRoom����,
					// ������verifyPayList()ʱ������޷���ļ����ֶ�
					tmpPayEntry.setTenRoom(tenancyRoom);
					row.getCell(
							PREFIX_C_PAYS_ROOM + j + POSTFIX_C_PAYS_APP_AMOUNT)
							.setUserObject(tmpPayEntry);// Ӧ���������޸�
					// .
					// �������ñ�����storePayList
					// (
					// )
					// �б���Ӧ�����
					row.getCell(
							PREFIX_C_PAYS_ROOM + j + POSTFIX_C_PAYS_ACT_AMOUNT)
							.setValue(tmpPayEntry.getAllRemainAmount());
					row.getCell(
							PREFIX_C_PAYS_ROOM + j
									+ POSTFIX_C_PAYS_ACT_PAY_DATE).setValue(
							tmpPayEntry.getActPayDate());

					totalAppPayThisLeas = totalAppPayThisLeas.add(tmpPayEntry
							.getAppAmount() == null ? FDCHelper.ZERO
							: tmpPayEntry.getAppAmount());
					// ʵ�պϼ�Ҳȡʣ���������ȡʵ�ս��
					totalActPayThisLeas = totalActPayThisLeas.add(tmpPayEntry
							.getAllRemainAmount() == null ? FDCHelper.ZERO
							: tmpPayEntry.getAllRemainAmount());
				}

				for (int j = 0; j < tenAttachReses.size(); j++) {
					TenAttachResourceEntryInfo tenAttachRes = tenAttachReses
							.get(j);

					TenAttachResourcePayListEntryCollection tmpPayList = tenAttachRes
							.getAttachResPayList();
					TenAttachResourcePayListEntryInfo tmpPayEntry = tmpPayList
							.get(seq);
					if (tmpPayEntry == null) {
						log.error("�߼������������,tenancyRoom.id:"
								+ tenAttachRes.getId());
						continue;
					}

					row.getCell(
							PREFIX_C_PAYS_ATTACH_RES + j
									+ POSTFIX_C_PAYS_APP_AMOUNT).setValue(
							tmpPayEntry.getAppAmount());
					// tmpPayEntryֻ�洢��tenancyRoom��IDֵ,��������tenancyRoom����,
					// ������verifyPayList()ʱ������޷���ļ����ֶ�
					tmpPayEntry.setTenAttachRes(tenAttachRes);
					row.getCell(
							PREFIX_C_PAYS_ATTACH_RES + j
									+ POSTFIX_C_PAYS_APP_AMOUNT).setUserObject(
							tmpPayEntry);
					row.getCell(
							PREFIX_C_PAYS_ATTACH_RES + j
									+ POSTFIX_C_PAYS_ACT_AMOUNT).setValue(
							tmpPayEntry.getActAmount());
					row.getCell(
							PREFIX_C_PAYS_ATTACH_RES + j
									+ POSTFIX_C_PAYS_ACT_PAY_DATE).setValue(
							tmpPayEntry.getActPayDate());

					totalAppPayThisLeas = totalAppPayThisLeas.add(tmpPayEntry
							.getAppAmount() == null ? FDCHelper.ZERO
							: tmpPayEntry.getAppAmount());
					totalActPayThisLeas = totalActPayThisLeas.add(tmpPayEntry
							.getActAmount() == null ? FDCHelper.ZERO
							: tmpPayEntry.getActAmount());
				}

				row.getCell(C_PAYS_TOTAL_APP_PAY).setValue(totalAppPayThisLeas);
				row.getCell(C_PAYS_TOTAL_ACT_PAY).setValue(totalActPayThisLeas);
			}
		}

		// �ϼ���
		for (Iterator itor = unionRows.keySet().iterator(); itor.hasNext();) {
			IRow unionRow = (IRow) itor.next();
			List entryRows = (List) unionRows.get(unionRow);

			for (int i = 0; i < this.tblPayList.getColumnCount(); i++) {
				String colKey = this.tblPayList.getColumn(i).getKey();
				if (colKey.equals(C_PAYS_TOTAL_APP_PAY)
						|| colKey.equals(C_PAYS_TOTAL_ACT_PAY)
						|| colKey.endsWith(POSTFIX_C_PAYS_APP_AMOUNT)
						|| colKey.endsWith(POSTFIX_C_PAYS_ACT_AMOUNT)) {
					BigDecimal unionAmount = FDCHelper.ZERO;
					for (int j = 0; j < entryRows.size(); j++) {
						IRow entryRow = (IRow) entryRows.get(j);
						BigDecimal entryAmount = (BigDecimal) entryRow.getCell(
								colKey).getValue();
						if (entryAmount != null) {
							unionAmount = unionAmount.add(entryAmount);
						}
					}
					unionRow.getCell(colKey).setValue(unionAmount);
				}
			}
		}

		this.tblPayList.getMergeManager().mergeBlock(0, 0,
				this.tblPayList.getRowCount() - 1, 0,
				KDTMergeManager.FREE_ROW_MERGE);
	}

	/**
	 * �ӽ�����ȡֵ��װ�������¼����
	 * */
	private RentFreeEntryCollection getRentFreesFromView() {
		RentFreeEntryCollection rentFrees = new RentFreeEntryCollection();
		for (int i = 0; i < this.tblFree.getRowCount(); i++) {
			IRow row = this.tblFree.getRow(i);
			Date startDate = (Date) row.getCell(C_FREE_START_DATE).getValue();
			Date endDate = (Date) row.getCell(C_FREE_END_DATE).getValue();
			FreeTenancyTypeEnum fttEnum = (FreeTenancyTypeEnum) row.getCell(
					C_FREE_TENANCY_TYPE).getValue();
			String des = (String) row.getCell(C_FREE_DES).getValue();

			if (startDate == null || endDate == null) {
				continue;
			}

			startDate = FDCDateHelper.getDayBegin(startDate);
			endDate = FDCDateHelper.getDayBegin(endDate);

			if (startDate.after(endDate)) {
				// TODO �Ƿ���Ҫ������ʾ
				continue;
			}

			RentFreeEntryInfo rentFree = new RentFreeEntryInfo();
			rentFree.setFreeStartDate(startDate);
			rentFree.setFreeEndDate(endDate);
			rentFree.setFreeTenancyType(fttEnum);
			rentFree.setDescription(des);

			rentFrees.add(rentFree);
		}
		return rentFrees;
	}

	private boolean isDynamicStartDate() {
		return RentStartTypeEnum.DynamicStartDate
				.equals(this.comboRentStartType.getSelectedItem());
	}

	/** @deprecated ����ô�2�������ĸ÷��� */
	private void reSetRentSetInfo(TenancyRoomEntryCollection tenRooms)
			throws BOSException {
		reSetRentSetInfo(tenRooms, getTenAttachResListFromView());
	}

	/**
	 * ���¸��ݶ���ʱ������������¼,�������޷����¡���ˢ�µ����档 <br>
	 * �����������Ҫ���ã��޸�������ʼ���������ڣ��޸��������б�����ڣ����Ӻ�ɾ������
	 * {@link #pkStartDate_dataChanged(DataChangeEvent)}
	 * {@link #pkEndDate_dataChanged(DataChangeEvent)}
	 * {@link #tblIncrease_editStopped(KDTEditEvent)}
	 * {@link #btnAddRoom_actionPerformed(ActionEvent)}
	 * {@link #btnRemoveRoom_actionPerformed(ActionEvent)} <br>
	 * һ���������󣬾���Ҫˢ�¸�����ϸ������Ҫ����{@link #updatePayListInfo()}
	 * */
	private void reSetRentSetInfo(TenancyRoomEntryCollection tenRooms,
			TenAttachResourceEntryCollection tenAttaches) throws BOSException {
		boolean isDynamicStartDate = isDynamicStartDate();
		IncreasedRentEntryCollection increasedRents = getIncreasedRentsFromView();
		Date startDate = (Date) this.pkStartDate.getValue();
		Date endDate = (Date) this.pkEndDate.getValue();

		// ���������õ������Է��õ�ֵ
		Map mapPrice = new HashMap();
		Map mapRent = new HashMap();
		int m = 0;
		int n = 0;
		for (int i = 0; i < tblRentSet.getRowCount(); i++) {
			IRow row = tblRentSet.getRow(i);
			String moneyName = "";
			String moneyPriceName = "";
			for (int j = 0; j < tblRentSet.getColumnCount(); j++) {
				IColumn column = this.tblRentSet.getColumn(j);
				String colKey = column.getKey();
				// if(colKey.startsWith(PREFIX_C_RENT_PERIODICITY_PRICE))
				// {
				// String str =
				// colKey.substring(PREFIX_C_RENT_PERIODICITY_PRICE.
				// length(),colKey.length());
				// StringBuffer strBuff = new StringBuffer(str);
				// strBuff.append(i);
				// BigDecimal price =
				// (BigDecimal)row.getCell(colKey).getValue();
				// if(price==null)
				// {
				// price = new BigDecimal(0);
				// }
				// mapPrice.put(strBuff.toString(), price);
				// }else
				if (colKey.startsWith(PREFIX_C_RENT_PERIODICITY)) {
					// ��Ϊ�������ʱ��ֶΣ����ж�������Է��õ���ʱ��ʱ���޷��ͱ���ʱ��ʱ��һ��
					// �����ÿ������ͼ��ϵڼ��еڼ������жϲ���Ψһ��
					int index1 = colKey.indexOf("-");
					int index = colKey.indexOf("(");
					String str = colKey.substring(index1 + 1, index);
					if (!str.equals(moneyName)) {
						m = 0;
					} else {
						m++;
					}
					moneyName = str;
					StringBuffer strBuff = new StringBuffer(str);
					strBuff.append(i);
					strBuff.append(m);
					BigDecimal rent = (BigDecimal) row.getCell(colKey)
							.getValue();
					if (rent == null) {
						rent = new BigDecimal(0);
					}
					mapRent.put(strBuff.toString(), rent);
				} else if (colKey.startsWith(PREFIX_C_RENT_PERIODICITY_PRICE)) {
					// ��Ϊ�������ʱ��ֶΣ����ж�������Է��õ���ʱ��ʱ���޷��ͱ���ʱ��ʱ��һ��
					// �����ÿ������ͼ��ϵڼ��еڼ������жϲ���Ψһ��
					int index1 = colKey.indexOf("-");
					int index = colKey.indexOf("(");
					String str = colKey.substring(index1 + 1, index);
					str = str + "price";
					if (!str.equals(moneyPriceName)) {
						n = 0;
					} else {
						n++;
					}
					moneyPriceName = str;
					StringBuffer strBuff = new StringBuffer(str);
					strBuff.append(i);
					strBuff.append(n);
					BigDecimal rent = (BigDecimal) row.getCell(colKey)
							.getValue();
					if (rent == null) {
						rent = new BigDecimal(0);
					}
					mapRent.put(strBuff.toString(), rent);
				}
			}
		}
		MoneyDefineCollection periodicityMoneys = new MoneyDefineCollection();
		if (isPeriodShow) {
			// ���������ͷ���
			EntityViewInfo view = new EntityViewInfo();
			FilterInfo filter = new FilterInfo();
			filter.getFilterItems().add(
					new FilterItemInfo("moneyType",
							MoneyTypeEnum.PeriodicityAmount));
			filter.getFilterItems().add(
					new FilterItemInfo("sysType", MoneySysTypeEnum.TenancySys));
			// ԭ��û���Ƿ�����״̬�������Է�����Ҫ������
			// filter.getFilterItems().add(new FilterItemInfo("isEnabled",
			// Boolean.TRUE));
			view.setFilter(filter);
			periodicityMoneys = MoneyDefineFactory.getRemoteInstance()
					.getMoneyDefineCollection(view);
		}

		/*
		 * �߼�������Ҫ�������������һ����������һ����������ʱ�򣬷���һ��һ������ӣ�
		 */
		TenancyRoomEntryCollection tenColl = getTenRoomListFromView();
		for (Iterator itor = tenColl.iterator(); itor.hasNext();) {
			Map map = new HashMap();
			TenancyRoomEntryInfo tenRoominfo = (TenancyRoomEntryInfo) itor
					.next();
			boolean flag = false;
			for (int i = 0; i < tblRentSet.getRowCount(); i++) {
				IRow row = tblRentSet.getRow(i);
				TenancyRoomEntryInfo tenRoom = null;
				if (row.getUserObject() != null) {
					tenRoom = (TenancyRoomEntryInfo) row.getUserObject();
				}
				if (tenRoominfo.getRoom().getId().equals(
						tenRoom.getRoom().getId())) {
					for (int j = 0; j < monDefineColl.size(); j++) {
						MoneyDefineInfo moneyInfo = monDefineColl.get(j);
						BigDecimal disAmount = new BigDecimal(0);
						if (row.getCell(moneyInfo.getNumber()).getValue() == null) {
							map.put(moneyInfo, disAmount);
						} else {
							disAmount = (BigDecimal) row.getCell(
									moneyInfo.getNumber()).getValue();
							map.put(moneyInfo, disAmount);
						}
					}
					roomDisMap.put(tenRoom.getRoom().getId().toString(), map);
					flag = true;
					break;
				}
			}
			if (flag) {
				continue;
			} else {
				BigDecimal disAmount = new BigDecimal(0);
				for (int j = 0; j < monDefineColl.size(); j++) {
					MoneyDefineInfo moneyInfo = monDefineColl.get(j);

					map.put(moneyInfo, disAmount);
				}
				roomDisMap.put(tenRoominfo.getRoom().getId().toString(), map);
			}
		}

		try {
			isFirstEdit = TenancyHelper.reSetRentSetInfo(tenRooms, roomDisMap,
					increasedRents, startDate, endDate, periodicityMoneys,
					DealAmountEntryCollection.class, DealAmountEntryInfo.class,
					getRentMoneyDefine(), isDynamicStartDate, mapPrice,
					mapRent, this.getOprtState(), isFirstEdit, oldState);
			isFirstEdit = TenancyHelper.reSetRentSetInfo(tenAttaches,
					roomDisMap, increasedRents, startDate, endDate,
					periodicityMoneys, AttachDealAmountEntryCollection.class,
					AttachDealAmountEntryInfo.class, getRentMoneyDefine(),
					isDynamicStartDate, mapPrice, mapRent, this.getOprtState(),
					isFirstEdit, oldState);
		} catch (InstantiationException e) {
			throw new BOSException(e);
		} catch (IllegalAccessException e) {
			throw new BOSException(e);
		}

		if (isDynamicStartDate && startDate == null) {
			loadDynamicTblRentSet(tenRooms, tenAttaches);
		} else {
			loadTblRentSet(tenRooms, tenAttaches);
		}
	}

	/**
	 * �ӽ�����ȡ������װ����������¼���� <br>
	 * δ���õ������ں���ֵ�ľ���Ϊ��Ч��¼����
	 * */
	private IncreasedRentEntryCollection getIncreasedRentsFromView() {
		IncreasedRentEntryCollection increasedRents = new IncreasedRentEntryCollection();
		for (int i = 0; i < this.tblIncrease.getRowCount(); i++) {
			IRow row = this.tblIncrease.getRow(i);
			Date increaseDate = (Date) row.getCell(C_INC_INCREASE_DATE)
					.getValue();
			if (increaseDate == null) {
				continue;
			}

			IncreasedRentTypeEnum increasedRentType = (IncreasedRentTypeEnum) row
					.getCell(C_INC_INCREASE_TYPE).getValue();
			BigDecimal value = (BigDecimal) row.getCell(C_INC_VALUE).getValue();

			if (!IncreasedRentTypeEnum.Handwork.equals(increasedRentType)
					&& value == null) {
				continue;
			}

			IncreaseStyleEnum increaseStyle = (IncreaseStyleEnum) row.getCell(
					C_INC_INCREASESTYLE).getValue();

			IncreasedRentEntryInfo increasedRent = new IncreasedRentEntryInfo();
			increasedRent.setIncreaseDate(increaseDate);
			increasedRent.setIncreaseType(increasedRentType);
			increasedRent.setValue(value);
			increasedRent.setIncreaseStyle(increaseStyle);

			increasedRents.add(increasedRent);
		}
		return increasedRents;
	}

	/**
	 * ������ʼ����Ϊ��̬��ʼ����ʱ���������Ϣ
	 * 
	 * @param tenRooms
	 * @param tenAttaches
	 */
	private void loadDynamicTblRentSet(TenancyRoomEntryCollection tenRooms,
			TenAttachResourceEntryCollection tenAttaches) {
		this.tblRentSet.removeRows();
		/* ����Ѻ�� */
		BigDecimal depositGather = FDCHelper.ZERO;
		/* �������� */
		BigDecimal firstRentGather = FDCHelper.ZERO;
		RentTypeEnum rentTypeGather = RentTypeEnum.RentByMonth;
		rentArea = FDCHelper.ZERO;
		Map rentMap = new HashMap();
		Map rentPriceMap = new HashMap();
		BigDecimal standardRentGateher = FDCHelper.ZERO;
		// �Ƚ������ɾ�����ٸ������õĵ����б�̬����tblRentSet��
		List toDelColIndexes = new ArrayList();
		for (int i = 0; i < this.tblRentSet.getColumnCount(); i++) {
			IColumn column = this.tblRentSet.getColumn(i);
			String colKey = column.getKey();
			if (colKey.startsWith(PREFIX_C_RENT_RENT_PRICE)
					|| colKey.startsWith(PREFIX_C_RENT_RENT)
					|| colKey.startsWith(PREFIX_C_RENT_PERIODICITY_PRICE)
					|| colKey.startsWith(PREFIX_C_RENT_PERIODICITY)) {
				toDelColIndexes.add(new Integer(i));
			}
		}

		for (int i = toDelColIndexes.size() - 1; i >= 0; i--) {
			int colIndex = ((Integer) toDelColIndexes.get(i)).intValue();
			this.tblRentSet.removeColumn(colIndex);
		}
		Map despositMap = new HashMap();
		// ���ӵ�һ��ʱ���ж�̬������
		boolean isFirstRow = true;
		for (int i = 0; i < tenRooms.size(); i++) {
			TenancyRoomEntryInfo tenRoom = tenRooms.get(i);
			IRow row = this.tblRentSet.addRow();
			row.setUserObject(tenRoom);
			row.getCell(C_RENT_ROOM).setValue(tenRoom.getRoomLongNum());
			TenancyModeEnum tenancyMode = TenancyModeEnum.TenancyRentModel;
			if (tenRoom.getTenancyModel() != null) {
				tenancyMode = tenRoom.getTenancyModel();
			}
			row.getCell(C_RENT_TENANCY_MODEL).setValue(tenancyMode);
			RentTypeEnum rentType = RentTypeEnum.RentByMonth;
			if (tenRoom.getDealRentType() != null) {
				rentType = tenRoom.getDealRentType();
			} else {
				tenRoom.setDealRentType(rentType);
			}
			row.getCell(C_RENT_RENT_TYPE).setValue(rentType);
			// row.getCell(C_RENT_DEPOSIT).setValue(tenRoom.getDepositAmount());
			row.getCell(C_RENT_FIRST_RENT)
					.setValue(tenRoom.getFirstPayAmount());
			if (tenRoom.getDepositAmount() != null) {
				depositGather = depositGather.add(tenRoom.getDepositAmount());
			}
			if (tenRoom.getFirstPayAmount() != null) {
				firstRentGather = firstRentGather.add(tenRoom
						.getFirstPayAmount());
			}

			if (tenRoom.getBuildingArea() != null) {
				rentArea = rentArea.add(tenRoom.getBuildingArea());
			}
			// ��Ϊ�����е���������ܻ�ͷ����е�һ��
			if (tenRoom.getDealRentType() != null) {
				rentTypeGather = tenRoom.getDealRentType();
			}
			DealAmountEntryCollection dealAmounts = tenRoom.getDealAmounts();
			for (int k = 0; k < dealAmounts.size(); k++) {
				DealAmountEntryInfo dealAmount = dealAmounts.get(k);
				String moneyNumber = dealAmount.getMoneyDefine().getNumber();
				// �ѻ�����Ѻ�������
				if (despositMap.get(moneyNumber) == null) {
					depositGather = dealAmount.getAmount();
					despositMap.put(moneyNumber, depositGather);
				} else {
					BigDecimal amount = (BigDecimal) despositMap
							.get(moneyNumber);
					amount = amount == null ? new BigDecimal(0) : amount;
					if (dealAmount.getAmount() == null) {
						depositGather = amount.add(new BigDecimal(0));
						despositMap.put(moneyNumber, depositGather);
					} else {
						depositGather = amount.add(dealAmount.getAmount());
						despositMap.put(moneyNumber, depositGather);
					}

				}
				{
					for (int j = 0; j < monDefineColl.size(); j++) {
						MoneyDefineInfo moneyInfo = monDefineColl.get(j);
						if (moneyNumber.equals(moneyInfo.getNumber())) {
							row.getCell(moneyNumber).setValue(
									dealAmount.getAmount());
						}
					}
				}
			}
			if (isFirstRow) {
				for (int j = 0; j < dealAmounts.size(); j++) {
					DealAmountEntryInfo dealAmount = dealAmounts.get(j);
					if (!MoneyTypeEnum.DepositAmount.equals(dealAmount
							.getMoneyDefine().getMoneyType())) {
						String keyRentPrice = PREFIX_C_RENT_RENT_PRICE;
						String keyRent = PREFIX_C_RENT_RENT;
						addTblRentSetColumn(keyRentPrice, keyRent, "�������");
					}
				}
				isFirstRow = false;
			}

			for (int j = 0; j < dealAmounts.size(); j++) {
				DealAmountEntryInfo dealAmount = dealAmounts.get(j);
				if (!MoneyTypeEnum.DepositAmount.equals(dealAmount
						.getMoneyDefine().getMoneyType())) {
					String moneyName = dealAmount.getMoneyDefine().getName();

					String keyRentPrice = PREFIX_C_RENT_RENT_PRICE;
					String keyRent = PREFIX_C_RENT_RENT;

					ICell cellRentPrice = row.getCell(keyRentPrice);
					ICell cellRent = row.getCell(keyRent);

					if (cellRentPrice != null && cellRent != null) {
						cellRentPrice.setValue(dealAmount.getPriceAmount());
						cellRent.setValue(dealAmount.getAmount());

						if (rentMap.get(keyRent) != null) {
							BigDecimal rent = (BigDecimal) rentMap.get(keyRent);
							rent = rent == null ? new BigDecimal(0) : rent;
							BigDecimal amount = new BigDecimal(0);
							if (dealAmount.getAmount() != null) {
								amount = dealAmount.getAmount();
							}
							standardRentGateher = rent.add(amount);
							rentMap.put(keyRent, standardRentGateher);
							rentPriceMap.put(keyRent, keyRentPrice);
						} else {
							standardRentGateher = dealAmount.getAmount();
							standardRentGateher = standardRentGateher == null ? new BigDecimal(
									0)
									: standardRentGateher;
							rentMap.put(keyRent, standardRentGateher);
							rentPriceMap.put(keyRent, keyRentPrice);
						}

						// �� dealAmount�������cell�У��Ա�֧��tblRentSet���ֶ�����
						cellRentPrice.setUserObject(dealAmount);
						cellRent.setUserObject(dealAmount);

						// ������ǰ��ܼۼ��㣬�������ܼ۲��ɱ༭
						if (!TenancyModeEnum.TenancyRentModel
								.equals(tenancyMode)) {
							cellRent.getStyleAttributes().setLocked(true);
						} else {
							cellRentPrice.getStyleAttributes().setLocked(true);
						}
					} else {
						keyRentPrice = PREFIX_C_RENT_PERIODICITY_PRICE
								+ moneyName;
						keyRent = PREFIX_C_RENT_PERIODICITY + moneyName;

						cellRentPrice = row.getCell(keyRentPrice);
						cellRent = row.getCell(keyRent);

						if (cellRentPrice != null && cellRent != null) {
							cellRentPrice.setValue(dealAmount.getPriceAmount());
							cellRent.setValue(dealAmount.getAmount());

							// �� dealAmount�������cell�У��Ա�֧��tblRentSet���ֶ�����
							cellRentPrice.setUserObject(dealAmount);
							cellRent.setUserObject(dealAmount);

							// ������ǰ��ܼۼ��㣬�������ܼ۲��ɱ༭
							if (!TenancyModeEnum.TenancyRentModel
									.equals(tenRoom.getTenancyModel())) {
								cellRent.getStyleAttributes().setLocked(true);
							} else {
								cellRentPrice.getStyleAttributes().setLocked(
										true);
							}
						}
					}
				}
			}
		}

		// �տ�ʼ�ӻ����е�ʱ��Ĭ���ǻ��ܷ�������������ǻ��ܵ���
		if (isStandardRentSetting()) {
			IRow rowGather = tblRentSet.addRow();
			rowGather.getStyleAttributes().setBackground(Color.YELLOW);
			rowGather.getCell(C_RENT_ROOM).setValue("������");
			rowGather.getCell(C_RENT_TENANCY_MODEL).setValue(
					TenancyModeEnum.TenancyRentModel);
			rowGather.getCell(C_RENT_RENT_TYPE).setValue(rentTypeGather);
			for (int i = 0; i < monDefineColl.size(); i++) {
				MoneyDefineInfo moneyInfo = monDefineColl.get(i);
				if (despositMap.get(moneyInfo.getNumber()) != null) {
					BigDecimal amount = (BigDecimal) despositMap.get(moneyInfo
							.getNumber());
					rowGather.getCell(moneyInfo.getNumber()).setValue(amount);
				}
			}
			// rowGather.getCell(C_RENT_DEPOSIT).setValue(depositGather);
			rowGather.getCell(C_RENT_FIRST_RENT).setValue(firstRentGather);
			rowGather.getCell(C_RENT_TENANCY_MODEL).getStyleAttributes()
					.setLocked(true);

			setRentAndPriceGather(rentMap, rentPriceMap, rowGather);
			lockTblRent();
		}

		for (int i = 0; i < tenAttaches.size(); i++) {
			TenAttachResourceEntryInfo tenAttach = tenAttaches.get(i);
			IRow row = this.tblRentSet.addRow();

			row.setUserObject(tenAttach);
			row.getCell(C_RENT_ROOM).setValue(tenAttach.getAttachLongNum());
			row.getCell(C_RENT_TENANCY_MODEL).setValue(
					TenancyModeEnum.TenancyRentModel);
			row.getCell(C_RENT_TENANCY_MODEL).getStyleAttributes().setLocked(
					true);
			row.getCell(C_RENT_RENT_TYPE).setValue(tenAttach.getDealRentType());
			//row.getCell(C_RENT_DEPOSIT).setValue(tenAttach.getDepositAmount())
			// ;
			row.getCell(C_RENT_FIRST_RENT).setValue(
					tenAttach.getFirstPayAmount());

			AttachDealAmountEntryCollection dealAmounts = tenAttach
					.getDealAmounts();

			if (isFirstRow) {
				for (int j = 0; j < dealAmounts.size(); j++) {
					String keyRentPrice = PREFIX_C_RENT_RENT_PRICE;
					String keyRent = PREFIX_C_RENT_RENT;
					addTblRentSetColumn(keyRentPrice, keyRent, "�������");
				}
				isFirstRow = false;
			}
			for (int j = 0; j < dealAmounts.size(); j++) {
				AttachDealAmountEntryInfo dealAmount = dealAmounts.get(j);
				String moneyName = dealAmount.getMoneyDefine().getName();
				// String dateDes = getDateDes(moneyName,
				// dealAmount.getStartDate(), dealAmount.getEndDate());

				String keyRentPrice = PREFIX_C_RENT_RENT_PRICE;
				String keyRent = PREFIX_C_RENT_RENT;

				ICell cellRentPrice = row.getCell(keyRentPrice);
				ICell cellRent = row.getCell(keyRent);

				if (cellRentPrice != null && cellRent != null) {
					// cellRentPrice.setValue(dealAmount.getPriceAmount());
					cellRent.setValue(dealAmount.getAmount());

					// �� dealAmount�������cell�У��Ա�֧��tblRentSet���ֶ�����
					// cellRentPrice.setUserObject(dealAmount);
					cellRent.setUserObject(dealAmount);

					if (dealAmount.isIsHandwork()) {
						// ������ǰ��ܼۼ��㣬�������ܼ۲��ɱ༭
						cellRentPrice.getStyleAttributes().setLocked(true);
					} else {
						cellRentPrice.getStyleAttributes().setLocked(true);
						cellRent.getStyleAttributes().setLocked(true);
					}
				} else {
					keyRentPrice = PREFIX_C_RENT_PERIODICITY_PRICE + moneyName;
					keyRent = PREFIX_C_RENT_PERIODICITY + moneyName;

					cellRentPrice = row.getCell(keyRentPrice);
					cellRent = row.getCell(keyRent);

					if (cellRentPrice != null && cellRent != null) {
						// cellRentPrice.setValue(dealAmount.getPriceAmount());
						cellRent.setValue(dealAmount.getAmount());

						// �� dealAmount�������cell�У��Ա�֧��tblRentSet���ֶ�����
						cellRentPrice.setUserObject(dealAmount);
						cellRent.setUserObject(dealAmount);

						// ������ǰ��ܼۼ��㣬�������ܼ۲��ɱ༭
						cellRentPrice.getStyleAttributes().setLocked(true);
					}
				}
			}
		}

		this.tblRentSet.getHeadMergeManager().mergeBlock(0, 0, 1,
				tblRentSet.getColumnCount() - 1, KDTMergeManager.FREE_MERGE);
		this.tblRentSet.setAutoscrolls(true);
	}

	// ���û�������������𵥼۵�ֵ
	private void setRentAndPriceGather(Map rentMap, Map rentPriceMap,
			IRow rowGather) {
		Set rentSet = rentMap.keySet();
		Iterator rentIter = rentSet.iterator();
		while (rentIter.hasNext()) {
			String rentKey = (String) rentIter.next();
			rowGather.getCell(rentKey).setValue(rentMap.get(rentKey));
			String rentPriceKey = (String) rentPriceMap.get(rentKey);
			BigDecimal value = new BigDecimal(0);
			if (rentMap.get(rentKey) != null) {
				value = (BigDecimal) rentMap.get(rentKey);
			}
			rowGather.getCell(rentPriceKey).setValue(
					value.divide(rentArea, 2, BigDecimal.ROUND_HALF_UP));
			rowGather.getCell(rentKey).setUserObject(rentMap.get(rentKey));
			rowGather.getCell(rentPriceKey).getStyleAttributes()
					.setLocked(true);
		}
	}

	// �����ǻ��ܷ�����
	private void lockTblRent() {
		for (int k = 0; k < tblRentSet.getRowCount(); k++) {
			IRow row2 = tblRentSet.getRow(k);
			if (row2.getUserObject() instanceof TenancyRoomEntryInfo) {
				for (int i = 0; i < this.tblRentSet.getColumnCount(); i++) {
					IColumn column = this.tblRentSet.getColumn(i);
					String colKey = column.getKey();
					row2.getCell(colKey).getStyleAttributes().setLocked(true);
				}
			}
		}
	}

	// �Ƿ���Ҫ���ӻ�����
	private boolean isStandardRentSetting() {
		// ��������������1���ҷ������㷽ʽ�����ͳһ���õ�ʱ�����Ҫ���ӻ�����,������Դ����������������
		if ((MoreRoomsTypeEnum.StandardRentSetting
				.equals((MoreRoomsTypeEnum) this.comboMoreRoomsType
						.getSelectedItem()))
				&& this.tblRoom.getRowCount() > 1) {
			return true;
		}
		return false;
	}

	/**
	 * �������޷�������޷���ĳɽ��۸��¼.�����������Table
	 * 
	 * @param tenAttaches
	 * */
	private void loadTblRentSet(TenancyRoomEntryCollection tenRooms,
			TenAttachResourceEntryCollection tenAttaches) {

		this.tblRentSet.removeRows();
		/* ����Ѻ�� */
		BigDecimal depositGather = FDCHelper.ZERO;
		/* �������� */
		BigDecimal firstRentGather = FDCHelper.ZERO;
		RentTypeEnum rentTypeGather = RentTypeEnum.RentByMonth;
		rentArea = FDCHelper.ZERO;
		Map rentMap = new HashMap();
		Map rentPriceMap = new HashMap();
		Map otherRentMap = new HashMap();
		Map otherRentPriceMap = new HashMap();
		BigDecimal standardRentGateher = FDCHelper.ZERO;
		// �Ƚ������ɾ�����ٸ������õĵ����б�̬����tblRentSet��
		List toDelColIndexes = new ArrayList();
		for (int i = 0; i < this.tblRentSet.getColumnCount(); i++) {
			IColumn column = this.tblRentSet.getColumn(i);
			String colKey = column.getKey();
			if (colKey.startsWith(PREFIX_C_RENT_RENT_PRICE)
					|| colKey.startsWith(PREFIX_C_RENT_RENT)
					|| colKey.startsWith(PREFIX_C_RENT_PERIODICITY_PRICE)
					|| colKey.startsWith(PREFIX_C_RENT_PERIODICITY)) {
				toDelColIndexes.add(new Integer(i));
			}
		}

		for (int i = toDelColIndexes.size() - 1; i >= 0; i--) {
			int colIndex = ((Integer) toDelColIndexes.get(i)).intValue();
			this.tblRentSet.removeColumn(colIndex);
		}

		Map despositMap = new HashMap();
		// ���ӵ�һ��ʱ���ж�̬������
		boolean isFirstRow = true;
		for (int i = 0; i < tenRooms.size(); i++) {
			TenancyRoomEntryInfo tenRoom = tenRooms.get(i);
			IRow row = this.tblRentSet.addRow();
			row.setUserObject(tenRoom);
			row.getCell(C_RENT_ROOM).setValue(tenRoom.getRoomLongNum());
			TenancyModeEnum tenancyMode = TenancyModeEnum.TenancyRentModel;
			if (tenRoom.getTenancyModel() != null) {
				tenancyMode = tenRoom.getTenancyModel();
			} else {
				tenRoom.setTenancyModel(tenancyMode);
			}
			row.getCell(C_RENT_TENANCY_MODEL).setValue(tenancyMode);
			RentTypeEnum rentType = RentTypeEnum.RentByMonth;
			if (tenRoom.getDealRentType() != null) {
				rentType = tenRoom.getDealRentType();
			} else {
				tenRoom.setDealRentType(rentType);
			}
			row.getCell(C_RENT_RENT_TYPE).setValue(rentType);
			// row.getCell(C_RENT_DEPOSIT).setValue(tenRoom.getDepositAmount());
			row.getCell(C_RENT_FIRST_RENT)
					.setValue(tenRoom.getFirstPayAmount());
			// if(tenRoom.getDepositAmount()!=null)
			// {
			// depositGather = depositGather.add(tenRoom.getDepositAmount());
			// }
			if (tenRoom.getFirstPayAmount() != null) {
				firstRentGather = firstRentGather.add(tenRoom
						.getFirstPayAmount());
			}

			if (tenRoom.getBuildingArea() != null) {
				rentArea = rentArea.add(tenRoom.getBuildingArea());
			}
			// ��Ϊ�����е���������ܻ�ͷ����е�һ��
			// if(tenRoom.getDealRentType()!=null)
			// {
			rentTypeGather = tenRoom.getDealRentType();
			// }
			DealAmountEntryCollection dealAmounts = tenRoom.getDealAmounts();
			for (int k = 0; k < dealAmounts.size(); k++) {
				DealAmountEntryInfo dealAmount = dealAmounts.get(k);
				String moneyNumber = dealAmount.getMoneyDefine().getNumber();
				// �ѻ�����Ѻ�������
				if (despositMap.get(moneyNumber) == null) {
					depositGather = dealAmount.getAmount();
					despositMap.put(moneyNumber, depositGather);
				} else {
					BigDecimal amount = (BigDecimal) despositMap
							.get(moneyNumber);
					amount = amount == null ? new BigDecimal(0) : amount;
					if (dealAmount.getAmount() == null) {
						depositGather = amount.add(new BigDecimal(0));
						despositMap.put(moneyNumber, depositGather);
					} else {
						depositGather = amount.add(dealAmount.getAmount());
						despositMap.put(moneyNumber, depositGather);
					}

				}
				// ���ֻ��һ��Ѻ�����ͣ���ֵ
				// if(monDefineColl.size()==1)
				// {
				// MoneyDefineInfo moneyInfo = monDefineColl.get(0);
				// if(moneyNumber.equals(moneyInfo.getNumber()))
				// {
				// if(dealAmount.getAmount()==null ||
				// dealAmount.getAmount().compareTo(new BigDecimal(0))==0)
				// {
				//row.getCell(moneyNumber).setValue(tenRoom.getDepositAmount());
				// }else
				// {
				// row.getCell(moneyNumber).setValue(dealAmount.getAmount());
				// }
				//						
				// }
				// }else
				{
					for (int j = 0; j < monDefineColl.size(); j++) {
						MoneyDefineInfo moneyInfo = monDefineColl.get(j);
						if (moneyNumber.equals(moneyInfo.getNumber())) {
							row.getCell(moneyNumber).setValue(
									dealAmount.getAmount());
						}
					}
				}
			}
			if (isFirstRow) {
				// �����Է���
				DealAmountEntryCollection periodicityMoneys = new DealAmountEntryCollection();
				for (int j = 0; j < dealAmounts.size(); j++) {
					DealAmountEntryInfo dealAmount = dealAmounts.get(j);
					if (MoneyTypeEnum.DepositAmount.equals(dealAmount
							.getMoneyDefine().getMoneyType())) {
						continue;
					}
					String moneyName = dealAmount.getMoneyDefine().getName();
					if (MoneyTypeEnum.PeriodicityAmount.equals(dealAmount
							.getMoneyDefine().getMoneyType())) {
						periodicityMoneys.add(dealAmount);
						continue;
					}
					String dateDes = getDateDes(moneyName, dealAmount
							.getStartDate(), dealAmount.getEndDate());

					String keyRentPrice = PREFIX_C_RENT_RENT_PRICE + dateDes;
					String keyRent = PREFIX_C_RENT_RENT + dateDes;

					addTblRentSetColumn(keyRentPrice, keyRent, dateDes);
				}

				for (int j = 0; j < periodicityMoneys.size(); j++) {
					DealAmountEntryInfo dealAmount = periodicityMoneys.get(j);
					String moneyName = dealAmount.getMoneyDefine().getName();
					String dateDes = getDateDes(moneyName, dealAmount
							.getStartDate(), dealAmount.getEndDate());
					String key1 = PREFIX_C_RENT_PERIODICITY_PRICE + dateDes;
					String key2 = PREFIX_C_RENT_PERIODICITY + dateDes;

					addTblRentPeriodicityColumn(key1, key2, dateDes);
				}
				isFirstRow = false;
			}

			for (int j = 0; j < dealAmounts.size(); j++) {
				DealAmountEntryInfo dealAmount = dealAmounts.get(j);
				if (MoneyTypeEnum.DepositAmount.equals(dealAmount
						.getMoneyDefine().getMoneyType())) {
					continue;
				}
				String moneyName = dealAmount.getMoneyDefine().getName();
				String dateDes = getDateDes(moneyName, dealAmount
						.getStartDate(), dealAmount.getEndDate());

				String keyRentPrice = PREFIX_C_RENT_RENT_PRICE + dateDes;
				String keyRent = PREFIX_C_RENT_RENT + dateDes;

				ICell cellRentPrice = row.getCell(keyRentPrice);
				ICell cellRent = row.getCell(keyRent);

				if (cellRentPrice != null && cellRent != null) {
					cellRentPrice.setValue(dealAmount.getPriceAmount());
					cellRent.setValue(dealAmount.getAmount());
					// ����ʵ������յ��ۣ� @by huanghefh
					if ((RentTypeEnum) row.getCell("rentType").getValue() != null) {
						String rentTypeName = ((RentTypeEnum) row.getCell(
								"rentType").getValue()).getValue();
						// row.getCell("actdayprice").setValue(toDayPrice(
						// rentTypeName,dealAmount.getPriceAmount()));
					}
					// �������ܵļ�ֵ�͵��۴�����������������ʱ����
					if (rentMap.get(keyRent) != null) {
						BigDecimal rent = (BigDecimal) rentMap.get(keyRent);
						rent = rent == null ? new BigDecimal(0) : rent;
						BigDecimal amount = new BigDecimal(0);
						if (dealAmount.getAmount() != null) {
							amount = dealAmount.getAmount();
						}
						standardRentGateher = rent.add(amount);
						rentMap.put(keyRent, standardRentGateher);
						rentPriceMap.put(keyRent, keyRentPrice);
					} else {
						standardRentGateher = dealAmount.getAmount();
						standardRentGateher = standardRentGateher == null ? new BigDecimal(
								0)
								: standardRentGateher;
						rentMap.put(keyRent, standardRentGateher);
						rentPriceMap.put(keyRent, keyRentPrice);
					}
					// �� dealAmount�������cell�У��Ա�֧��tblRentSet���ֶ�����
					cellRentPrice.setUserObject(dealAmount);
					cellRent.setUserObject(dealAmount);

					if (dealAmount.isIsHandwork()) {
						// ������ǰ��ܼۼ��㣬�������ܼ۲��ɱ༭
						if (!TenancyModeEnum.TenancyRentModel
								.equals(tenancyMode)) {
							cellRent.getStyleAttributes().setLocked(true);
						} else {
							cellRentPrice.getStyleAttributes().setLocked(true);
						}
					} else {
						cellRentPrice.getStyleAttributes().setLocked(true);
						cellRent.getStyleAttributes().setLocked(true);
					}
				} else {
					keyRentPrice = PREFIX_C_RENT_PERIODICITY_PRICE + dateDes;
					keyRent = PREFIX_C_RENT_PERIODICITY + dateDes;

					cellRentPrice = row.getCell(keyRentPrice);
					cellRent = row.getCell(keyRent);

					if (cellRentPrice != null && cellRent != null) {
						cellRentPrice.setValue(dealAmount.getPriceAmount());
						cellRent.setValue(dealAmount.getAmount());

						if (otherRentMap.get(keyRent) != null) {
							BigDecimal rent = (BigDecimal) otherRentMap
									.get(keyRent);
							rent = rent == null ? new BigDecimal(0) : rent;
							BigDecimal amount = new BigDecimal(0);
							if (dealAmount.getAmount() != null) {
								amount = dealAmount.getAmount();
							}
							standardRentGateher = rent.add(amount);
							otherRentMap.put(keyRent, standardRentGateher);
							otherRentPriceMap.put(keyRent, keyRentPrice);
						} else {
							standardRentGateher = dealAmount.getAmount();
							standardRentGateher = standardRentGateher == null ? new BigDecimal(
									0)
									: standardRentGateher;
							otherRentMap.put(keyRent, standardRentGateher);
							otherRentPriceMap.put(keyRent, keyRentPrice);
						}

						// �� dealAmount�������cell�У��Ա�֧��tblRentSet���ֶ�����
						cellRentPrice.setUserObject(dealAmount);
						cellRent.setUserObject(dealAmount);

						// ������ǰ��ܼۼ��㣬�������ܼ۲��ɱ༭
						if (!TenancyModeEnum.TenancyRentModel
								.equals(tenancyMode)) {
							cellRent.getStyleAttributes().setLocked(true);
						} else {
							cellRentPrice.getStyleAttributes().setLocked(true);
						}
					}
				}
			}
		}

		// �տ�ʼ�ӻ����е�ʱ��Ĭ���ǻ��ܷ�������������ǻ��ܵ���
		if (isStandardRentSetting()) {
			IRow rowGather = tblRentSet.addRow();
			rowGather.getStyleAttributes().setBackground(Color.YELLOW);
			rowGather.getCell(C_RENT_ROOM).setValue("������");
			rowGather.getCell(C_RENT_TENANCY_MODEL).setValue(
					TenancyModeEnum.TenancyRentModel);
			rowGather.getCell(C_RENT_RENT_TYPE).setValue(rentTypeGather);
			for (int i = 0; i < monDefineColl.size(); i++) {
				MoneyDefineInfo moneyInfo = monDefineColl.get(i);
				if (despositMap.get(moneyInfo.getNumber()) != null) {
					BigDecimal amount = (BigDecimal) despositMap.get(moneyInfo
							.getNumber());
					rowGather.getCell(moneyInfo.getNumber()).setValue(amount);
				}
			}
			// rowGather.getCell(C_RENT_DEPOSIT).setValue(depositGather);
			rowGather.getCell(C_RENT_FIRST_RENT).setValue(firstRentGather);
			rowGather.getCell(C_RENT_TENANCY_MODEL).getStyleAttributes()
					.setLocked(true);

			setRentAndPriceGather(rentMap, rentPriceMap, rowGather);
			setRentAndPriceGather(otherRentMap, otherRentPriceMap, rowGather);

			// Set otherRentSet = otherRentMap.keySet();
			// Iterator otherRent = otherRentSet.iterator();
			// while(otherRent.hasNext())
			// {
			// String otherKey = (String)otherRent.next();
			// rowGather.getCell(otherKey).setValue(otherRentMap.get(otherKey));
			// String otherRentPriceKey =
			// (String)otherRentPriceMap.get(otherKey);
			// BigDecimal value = new BigDecimal(0);
			// if(rentMap.get(otherKey)!=null)
			// {
			// value = (BigDecimal)rentMap.get(otherKey);
			// }
			//rowGather.getCell(otherRentPriceKey).setValue(value.divide(rentArea
			// , 2,BigDecimal.ROUND_HALF_UP));
			// rowGather.getCell(otherKey).setUserObject(value);
			// //
			// rowGather.getCell(otherKey).getStyleAttributes().setLocked(true);
			//rowGather.getCell(otherRentPriceKey).getStyleAttributes().setLocked
			// (true);
			// }
			lockTblRent();
		}

		for (int i = 0; i < tenAttaches.size(); i++) {
			TenAttachResourceEntryInfo tenAttach = tenAttaches.get(i);
			IRow row = this.tblRentSet.addRow();

			row.setUserObject(tenAttach);
			row.getCell(C_RENT_ROOM).setValue(tenAttach.getAttachLongNum());
			row.getCell(C_RENT_TENANCY_MODEL).setValue(
					TenancyModeEnum.TenancyRentModel);
			row.getCell(C_RENT_TENANCY_MODEL).getStyleAttributes().setLocked(
					true);
			row.getCell(C_RENT_RENT_TYPE).setValue(tenAttach.getDealRentType());
			//row.getCell(C_RENT_DEPOSIT).setValue(tenAttach.getDepositAmount())
			// ;
			row.getCell(C_RENT_FIRST_RENT).setValue(
					tenAttach.getFirstPayAmount());

			AttachDealAmountEntryCollection dealAmounts = tenAttach
					.getDealAmounts();
			if (isFirstRow) {
				// �����Է���
				AttachDealAmountEntryCollection periodicityMoneys = new AttachDealAmountEntryCollection();
				for (int j = 0; j < dealAmounts.size(); j++) {
					AttachDealAmountEntryInfo dealAmount = dealAmounts.get(j);
					String moneyName = dealAmount.getMoneyDefine().getName();
					if (MoneyTypeEnum.PeriodicityAmount.equals(dealAmount
							.getMoneyDefine().getMoneyType())) {
						periodicityMoneys.add(dealAmount);
						continue;
					}
					String dateDes = getDateDes(moneyName, dealAmount
							.getStartDate(), dealAmount.getEndDate());

					String keyRentPrice = PREFIX_C_RENT_RENT_PRICE + dateDes;
					String keyRent = PREFIX_C_RENT_RENT + dateDes;

					addTblRentSetColumn(keyRentPrice, keyRent, dateDes);
				}

				for (int j = 0; j < periodicityMoneys.size(); j++) {
					AttachDealAmountEntryInfo dealAmount = periodicityMoneys
							.get(j);
					String moneyName = dealAmount.getMoneyDefine().getName();
					String key1 = PREFIX_C_RENT_PERIODICITY_PRICE + moneyName;
					String key2 = PREFIX_C_RENT_PERIODICITY + moneyName;
					String dateDes = getDateDes(moneyName, dealAmount
							.getStartDate(), dealAmount.getEndDate());

					addTblRentPeriodicityColumn(key1, key2, dateDes);
				}
				isFirstRow = false;
			}

			for (int j = 0; j < dealAmounts.size(); j++) {
				AttachDealAmountEntryInfo dealAmount = dealAmounts.get(j);
				String moneyName = dealAmount.getMoneyDefine().getName();
				String dateDes = getDateDes(moneyName, dealAmount
						.getStartDate(), dealAmount.getEndDate());

				String keyRentPrice = PREFIX_C_RENT_RENT_PRICE + dateDes;
				String keyRent = PREFIX_C_RENT_RENT + dateDes;

				ICell cellRentPrice = row.getCell(keyRentPrice);
				ICell cellRent = row.getCell(keyRent);

				if (cellRentPrice != null && cellRent != null) {
					// cellRentPrice.setValue(dealAmount.getPriceAmount());
					cellRent.setValue(dealAmount.getAmount());
					// �� dealAmount�������cell�У��Ա�֧��tblRentSet���ֶ�����
					// cellRentPrice.setUserObject(dealAmount);
					cellRent.setUserObject(dealAmount);

					if (dealAmount.isIsHandwork()) {
						// ������ǰ��ܼۼ��㣬�������ܼ۲��ɱ༭
						cellRentPrice.getStyleAttributes().setLocked(true);
					} else {
						cellRentPrice.getStyleAttributes().setLocked(true);
						cellRent.getStyleAttributes().setLocked(true);
					}
				} else {
					keyRentPrice = PREFIX_C_RENT_PERIODICITY_PRICE + moneyName;
					keyRent = PREFIX_C_RENT_PERIODICITY + moneyName;

					cellRentPrice = row.getCell(keyRentPrice);
					cellRent = row.getCell(keyRent);

					if (cellRentPrice != null && cellRent != null) {
						// cellRentPrice.setValue(dealAmount.getPriceAmount());
						cellRent.setValue(dealAmount.getAmount());
						// �� dealAmount�������cell�У��Ա�֧��tblRentSet���ֶ�����
						cellRentPrice.setUserObject(dealAmount);
						cellRent.setUserObject(dealAmount);

						// ������ǰ��ܼۼ��㣬�������ܼ۲��ɱ༭
						cellRentPrice.getStyleAttributes().setLocked(true);
					}
				}
			}
		}

		this.tblRentSet.getHeadMergeManager().mergeBlock(0, 0, 1,
				tblRentSet.getColumnCount() - 1, KDTMergeManager.FREE_MERGE);
		this.tblRentSet.setAutoscrolls(true);

	}

	private void addTblRentPeriodicityColumn(String key1, String key2,
			String des) {
		IColumn col = this.tblRentSet.addColumn();
		col.setKey(key1);
		col.setEditor(createFormattedCellEditor());
		col.getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
		col.getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(2));

		col = this.tblRentSet.addColumn();
		col.setKey(key2);
		col.setEditor(createFormattedCellEditor());
		col.getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
		col.getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(2));

		IRow headRow0 = this.tblRentSet.getHeadRow(0);
		headRow0.getCell(key1).setValue(des);
		headRow0.getCell(key2).setValue(des);

		IRow headRow1 = this.tblRentSet.getHeadRow(1);
		headRow1.getCell(key1).setValue("����");
		headRow1.getCell(key2).setValue("���");
	}

	private String getDateDes(String moneyName, Date startDate, Date endDate) {
		String dateDes = null;
		if (startDate == null || endDate == null) {
			return null;
		}
		if (startDate.equals(endDate)) {
			dateDes = FDCDateHelper.DateToString(startDate);
		} else {
			dateDes = FDCDateHelper.DateToString(startDate) + "��"
					+ FDCDateHelper.DateToString(endDate);
		}
		return moneyName + "(" + dateDes + ")";
	}

	private void addTblRentSetColumn(String keyRentType, String keyRent,
			String headRowDes) {
		IColumn col = this.tblRentSet.addColumn();
		col.setKey(keyRentType);
		col.setEditor(createFormattedCellEditor());
		col.getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
		col.getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(2));

		col = this.tblRentSet.addColumn();
		col.setKey(keyRent);
		col.setEditor(createFormattedCellEditor());
		col.getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
		col.getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(2));

		IRow headRow0 = this.tblRentSet.getHeadRow(0);
		headRow0.getCell(keyRentType).setValue(headRowDes);
		headRow0.getCell(keyRent).setValue(headRowDes);

		IRow headRow1 = this.tblRentSet.getHeadRow(1);
		headRow1.getCell(keyRentType).setValue("��𵥼�");
		headRow1.getCell(keyRent).setValue("���");
	}

	/**
	 * ���ݳɽ�����ܼۺ��н��ͬӶ���׼����Լ�������
	 * */
	private void updatePromissoryAgentFee() {
		BigDecimal dealTotalRent = this.txtDealTotalRent.getBigDecimalValue();
		if (dealTotalRent == null) {
			dealTotalRent = FDCHelper.ZERO;
		}

		AgencyContractInfo agencyContract = (AgencyContractInfo) this.f7AgencyContract
				.getValue();
		if (agencyContract != null) {
			CommisionStandardEnum comStand = agencyContract
					.getCommisionStandard();
			BigDecimal value = agencyContract.getValue();
			if (value == null)
				value = FDCHelper.ZERO;
			BigDecimal agentFee = FDCHelper.ZERO;
			if (CommisionStandardEnum.Percentum.equals(comStand)) {
				agentFee = dealTotalRent.multiply(value).divide(
						FDCHelper.ONE_HUNDRED, 2, BigDecimal.ROUND_HALF_UP);
			} else if (CommisionStandardEnum.AptoticAmount.equals(comStand)) {
				agentFee = agencyContract.getValue();
			}
			this.txtPromissoryAgentFee.setValue(agentFee);

			if (this.txtAgentFee.getBigDecimalValue() == null) {
				this.txtAgentFee.setValue(agentFee);
			}
		}
	}

	/**
	 * �������޷��������״̬ <br>
	 * �����ת������������״̬Ϊ"����" <br>
	 * ���⣺�����º�ͬ�ϴ���,�ɺ�ͬ�ϴ���.�º�ͬ�ϵķ�������״̬Ϊ"����"; <br>
	 * �����º�ͬ�ϴ���,�ɺ�ͬ�ϲ�����.�Ҿɺ�ͬ�ϵķ������º�ͬ�϶�����,�º�ͬ�ϸ÷�������״̬Ϊ"����"; <br>
	 * �����º�ͬ�ϴ���,�ɺ�ͬ�ϲ�����.���ɺ�ͬ�ϵķ������º�ͬ�ϲ�������,�º�ͬ�ϵķ�������״̬Ϊ"����"; <br>
	 * ���⣺�����º�ͬ�ϴ���,�ɺ�ͬ�ϴ���.�º�ͬ�ϵķ�������״̬��ԭ��ͬ�ϸ÷��������״̬��ͬ; <br>
	 * �����º�ͬ�ϴ���,�ɺ�ͬ�ϲ�����.�Ҿɺ�ͬ�ϵķ������º�ͬ�϶�����,�º�ͬ�ϸ÷�������״̬Ϊ"����"; <br>
	 * �����º�ͬ�ϴ���,�ɺ�ͬ�ϲ�����.���ɺ�ͬ�ϵķ������º�ͬ�ϲ�������,�º�ͬ�ϵķ�������״̬Ϊ"����";
	 * 
	 * @param tenancyRooms
	 * */
	private void setTenRoomsState(TenancyRoomEntryCollection tenancyRooms) {
		TenancyContractTypeEnum currentTenancyType = getCurrentTenancyType();
		// �����������ʱ,��Ҫ����ԭ��ͬ�ķ����ж��������޷���״̬
		if (TenancyContractTypeEnum.ContinueTenancy.equals(currentTenancyType)
				|| TenancyContractTypeEnum.RejiggerTenancy
						.equals(currentTenancyType)) {
			Map oldTenRoomMap = new HashMap();
			TenancyRoomEntryCollection oldTenRooms = oldTenancyBill
					.getTenancyRoomList();
			for (int j = 0; j < oldTenRooms.size(); j++) {
				TenancyRoomEntryInfo oldTenRoom = oldTenRooms.get(j);
				if (oldTenRoom.getRoom() != null) {
					oldTenRoomMap.put(oldTenRoom.getRoom().getId().toString(),
							oldTenRoom);
				}
			}

			Set oldTenRoomIds = oldTenRoomMap.keySet();
			Set newTenRoomIds = new HashSet();

			for (int i = 0; i < tenancyRooms.size(); i++) {
				TenancyRoomEntryInfo tenRoom = tenancyRooms.get(i);
				if (tenRoom.getRoom() != null) {
					newTenRoomIds.add(tenRoom.getRoom().getId().toString());
				}
			}

			// ���������ʱ,ԭ��ͬ�ϵķ����Ƿ����º�ͬ��
			boolean isAllOldTenRoomsInNewTen = newTenRoomIds
					.containsAll(oldTenRoomIds);

			for (int i = 0; i < tenancyRooms.size(); i++) {
				TenancyRoomEntryInfo tenRoom = tenancyRooms.get(i);
				TenancyStateEnum tenRoomState = TenancyStateEnum.newTenancy;
				if (tenRoom.getRoom() != null) {
					String roomId = tenRoom.getRoom().getId().toString();
					if (oldTenRoomIds.contains(roomId)) {// �����ԭ��ͬ�д���,����ʱΪ"����";
						// ����ʱΪԭ��ͬ�����¼�ϵ�����״̬
						if (TenancyContractTypeEnum.ContinueTenancy
								.equals(currentTenancyType)) {
							tenRoomState = TenancyStateEnum.continueTenancy;
						} else if (TenancyContractTypeEnum.RejiggerTenancy
								.equals(currentTenancyType)) {
							TenancyRoomEntryInfo oldTenRoom = (TenancyRoomEntryInfo) oldTenRoomMap
									.get(roomId);
							tenRoomState = oldTenRoom.getTenRoomState();
						}
					} else {// ���䲻��ԭ��ͬ��,���ԭ��ͬ�����з��䶼���º�ͬ��,��Ϊ����;����Ϊ����
						if (isAllOldTenRoomsInNewTen) {
							tenRoomState = TenancyStateEnum.enlargeTenancy;
						} else {
							tenRoomState = TenancyStateEnum.newTenancy;
						}
					}
				}
				tenRoom.setTenRoomState(tenRoomState);
			}
		} else {// ������߸���ʱ,��������״̬��Ϊ"����"
			for (int i = 0; i < tenancyRooms.size(); i++) {
				TenancyRoomEntryInfo tenRoom = tenancyRooms.get(i);
				tenRoom.setTenRoomState(TenancyStateEnum.newTenancy);
			}
		}
	}

	/**
	 * �������޷��������״̬ <br>
	 * �����ת������������״̬Ϊ"����" <br>
	 * ���⣺�����º�ͬ�ϴ���,�ɺ�ͬ�ϴ���.�º�ͬ�ϵķ�������״̬Ϊ"����"; <br>
	 * �����º�ͬ�ϴ���,�ɺ�ͬ�ϲ�����.�Ҿɺ�ͬ�ϵķ������º�ͬ�϶�����,�º�ͬ�ϸ÷�������״̬Ϊ"����"; <br>
	 * �����º�ͬ�ϴ���,�ɺ�ͬ�ϲ�����.���ɺ�ͬ�ϵķ������º�ͬ�ϲ�������,�º�ͬ�ϵķ�������״̬Ϊ"����"; <br>
	 * ���⣺�����º�ͬ�ϴ���,�ɺ�ͬ�ϴ���.�º�ͬ�ϵķ�������״̬��ԭ��ͬ�ϸ÷��������״̬��ͬ; <br>
	 * �����º�ͬ�ϴ���,�ɺ�ͬ�ϲ�����.�Ҿɺ�ͬ�ϵķ������º�ͬ�϶�����,�º�ͬ�ϸ÷�������״̬Ϊ"����"; <br>
	 * �����º�ͬ�ϴ���,�ɺ�ͬ�ϲ�����.���ɺ�ͬ�ϵķ������º�ͬ�ϲ�������,�º�ͬ�ϵķ�������״̬Ϊ"����";
	 * */
	private void setTenAttachsState(TenAttachResourceEntryCollection tenAttachs) {
		TenancyContractTypeEnum currentTenancyType = getCurrentTenancyType();
		// �����������ʱ,��Ҫ����ԭ��ͬ�ķ����ж��������޷���״̬
		if (TenancyContractTypeEnum.ContinueTenancy.equals(currentTenancyType)
				|| TenancyContractTypeEnum.RejiggerTenancy
						.equals(currentTenancyType)) {
			Map oldTenAttachMap = new HashMap();
			TenAttachResourceEntryCollection oldTenAttachs = oldTenancyBill
					.getTenAttachResourceList();

			for (int j = 0; j < oldTenAttachs.size(); j++) {
				TenAttachResourceEntryInfo oldTenAttach = oldTenAttachs.get(j);
				if (oldTenAttach.getAttachResource() != null) {
					oldTenAttachMap.put(oldTenAttach.getAttachResource()
							.getId().toString(), oldTenAttach);
				}
			}

			Set oldTenAttachIds = oldTenAttachMap.keySet();
			Set newTenAttachIds = new HashSet();

			for (int i = 0; i < tenAttachs.size(); i++) {
				TenAttachResourceEntryInfo tenAttach = oldTenAttachs.get(i);
				if (tenAttach.getAttachResource() != null) {
					newTenAttachIds.add(tenAttach.getAttachResource().getId()
							.toString());
				}
			}

			// ���������ʱ,ԭ��ͬ�ϵķ����Ƿ����º�ͬ��
			boolean isAllOldTenRoomsInNewTen = newTenAttachIds
					.containsAll(oldTenAttachIds);

			for (int i = 0; i < tenAttachs.size(); i++) {
				TenAttachResourceEntryInfo tenAttach = tenAttachs.get(i);
				TenancyStateEnum tenAttachState = TenancyStateEnum.newTenancy;
				if (tenAttach.getAttachResource() != null) {
					String attachId = tenAttach.getAttachResource().getId()
							.toString();
					if (oldTenAttachIds.contains(attachId)) {//�����ԭ��ͬ�д���,����ʱΪ"����"
						// ;
						// ����ʱΪԭ��ͬ�����¼�ϵ�����״̬
						if (TenancyContractTypeEnum.ContinueTenancy
								.equals(currentTenancyType)) {
							tenAttachState = TenancyStateEnum.continueTenancy;
						} else if (TenancyContractTypeEnum.RejiggerTenancy
								.equals(currentTenancyType)) {
							TenAttachResourceEntryInfo oldTenAttach = (TenAttachResourceEntryInfo) oldTenAttachMap
									.get(attachId);
							tenAttachState = oldTenAttach.getTenAttachState();
						}
					} else {// ���䲻��ԭ��ͬ��,���ԭ��ͬ�����з��䶼���º�ͬ��,��Ϊ����;����Ϊ����
						if (isAllOldTenRoomsInNewTen) {
							tenAttachState = TenancyStateEnum.enlargeTenancy;
						} else {
							tenAttachState = TenancyStateEnum.newTenancy;
						}
					}
				}
				tenAttach.setTenAttachState(tenAttachState);
			}
		} else {// ������߸���ʱ,��������״̬��Ϊ"����"
			for (int i = 0; i < tenAttachs.size(); i++) {
				TenAttachResourceEntryInfo tenAttach = tenAttachs.get(i);
				tenAttach.setTenAttachState(TenancyStateEnum.newTenancy);
			}
		}
	}

	/**
	 * ����������,��������ȷ��2λС��
	 * 
	 * @param leaseList
	 *            ���ڼ��� ע��ֻ�����1�����ڿ��ܲ�������������,���1�ڵ��·���/���ڳ���=���1�ڵ�����.
	 * */
	private void updateLeaseCount(List leaseList) {
		// ���ڲ�����С�� 090701
		this.txtLeaseCount.setValue(new Integer(leaseList.size()));
	}

	/** TODO ��������ܻ����ɻ�������,��ȡ�������������չ */
	private MoneyDefineInfo[] getPayType() {
		MoneyDefineInfo[] payType = new MoneyDefineInfo[4];

		return null;
	}

	protected void verifyInput(ActionEvent e) throws Exception {
		super.verifyInput(e);
		boolean isOk =true;
		//����У����Ϣ
		if(isNeedSelectBaseLine){
			updateTotalInfo();
			//��ȡѡ������޻�׼����Ϣ����
			BigDecimal totalDepoist = FDCHelper.ZERO;
			BigDecimal avgDayPrice = FDCHelper.ZERO;
			BigDecimal totalRent = FDCHelper.ZERO;
			BigDecimal totalMaxFreeDay = FDCHelper.ZERO;
			BigDecimal totalMaxLease = FDCHelper.ZERO;
			BigDecimal totalArea = FDCHelper.ZERO;
			BigDecimal totalRentBaseLine = FDCHelper.ZERO;
			
			for(int i=0;i<tblTenPrice.getRowCount();i++){
				IRow r = this.tblTenPrice.getRow(i);
				TenPriceEntryInfo entry = (TenPriceEntryInfo) r.getCell("id").getUserObject();
				totalDepoist = totalDepoist.add(entry.getDeposit());
				totalRentBaseLine =totalRentBaseLine.add(entry.getArea().multiply(entry.getDayPrice()));
			    if(entry.getMaxLease().compareTo(totalMaxLease)>0){
			    	totalMaxLease = entry.getMaxLease();
			    }
			    if(entry.getMaxFreeDay().compareTo(totalMaxFreeDay)>0){
			    	totalMaxFreeDay = entry.getMaxFreeDay();
			    }
				totalArea = totalArea.add(entry.getArea());
			}
			//��ȡ���ϼ� row.getCell("moneyDefine")
			int c = this.tblTotal.getRowCount();
			IRow cr = null;
			for(int i=0;i<c;i++){
				cr = this.tblTotal.getRow(i);
				if("���".equals(cr.getCell("moneyDefine").getValue().toString())){
					totalRent = totalRent.add(FDCHelper.toBigDecimal(cr.getCell("appAmount").getValue()+""));
				}
			}
			
			
			BigDecimal lease = FDCHelper.toBigDecimal(txtMaxLease.getText());
			BigDecimal area = FDCHelper.toBigDecimal(txtRoomArea.getText());
			avgDayPrice =FDCHelper.divide(totalRent, lease);
			avgDayPrice = FDCHelper.divide(avgDayPrice,area);
			this.txtDayPrice.setText(avgDayPrice.toString());
			this.txtDayPrice.setHorizontalAlignment(11);
			
			BigDecimal tDayPrice = FDCHelper.divide(totalRentBaseLine.multiply(lease),lease);
			tDayPrice=FDCHelper.divide(tDayPrice,area);
			
			BigDecimal tMaxFreeDay = FDCHelper.ZERO;
			if(!StringUtils.isEmpty(txtMaxFreeDay.getText())){
				tMaxFreeDay = FDCHelper.toBigDecimal(txtMaxFreeDay.getText());
				if(tMaxFreeDay.compareTo(totalMaxFreeDay)>0){
					this.lblFreeDay.setText("��ͬ������ڳ���");
					this.lblFreeDay.setForeground(Color.RED);
					isOk =false;
				}else{
					this.lblFreeDay.setText("����");
					this.lblFreeDay.setForeground(Color.GREEN);
				}
			}
			BigDecimal tRoomArea = FDCHelper.ZERO;
			if(!StringUtils.isEmpty(txtRoomArea.getText())){
				tRoomArea = FDCHelper.toBigDecimal(txtRoomArea.getText());
				if(tRoomArea.compareTo(totalArea)!= 0){
					this.lblArea.setText("������������ڻ�׼���");
					this.lblArea.setForeground(Color.RED);
					isOk =false;
				}else{
					this.lblArea.setText("����");
					this.lblArea.setForeground(Color.GREEN);
				}
			}
			BigDecimal tLeaseDay = FDCHelper.ZERO;
			if(!StringUtils.isEmpty(txtMaxLease.getText())){
				tLeaseDay = FDCHelper.toBigDecimal(txtMaxLease.getText());
				if(tLeaseDay.compareTo(totalMaxLease)>0){
					this.lblMaxLease.setText("��ͬ��ڳ���");
					this.lblMaxLease.setForeground(Color.RED);
					isOk =false;
				}else{
					this.lblMaxLease.setText("����");
					this.lblMaxLease.setForeground(Color.GREEN);
				}
			}
			if(!StringUtils.isEmpty(txtDayPrice.getText())){
				tLeaseDay = FDCHelper.toBigDecimal(txtDayPrice.getText());
				if(tLeaseDay.compareTo(tDayPrice)<0){
					this.lblDayPrice.setText("�յ��۵��ڻ�׼����");
					this.lblDayPrice.setForeground(Color.RED);
					isOk =false;
				}else{
					this.lblDayPrice.setText("����");
					this.lblDayPrice.setForeground(Color.GREEN);
				}
			}
			BigDecimal tDepoist = FDCHelper.ZERO;
			if(!StringUtils.isEmpty(txtDepoist.getText())){
				tLeaseDay = FDCHelper.toBigDecimal(txtDepoist.getText());
				if(tLeaseDay.compareTo(totalDepoist)<0){
					this.lblDepoist.setText("��֤����ڻ�׼��֤��");
					this.lblDepoist.setForeground(Color.RED);
					isOk =false;
				}else{
					this.lblDepoist.setText("����");
					this.lblDepoist.setForeground(Color.GREEN);
				}
			}
			
			if(!isOk){
				FDCMsgBox.showError("��ͬ��׼����׼��Ϣʧ�ܣ���ϸ��Ϣ��鿴����ҳǩ�ĺ�׼ ��Ϣ��");
				SysUtil.abort();
			}
		}
		
		
		verifyBaseInfo();
		if (!isDynamicStartDate()) {
			// verifyBaseInfo���Ѿ�У����pkStartDate��pkEndDate����Ϊ��
			Date startDate = FDCDateHelper.getDayBegin((Date) this.pkStartDate
					.getValue());
			Date endDate = FDCDateHelper.getDayBegin((Date) this.pkEndDate
					.getValue());
			verifySpecial(startDate);
			verifyRoom(startDate, endDate);
			verifyCustomer();
			verifyRentSet();
			checkPayList();
			viftyOtherPayList();
		}

		// verifyPayList();������ϸ�в����������ˣ��������ﲻ���ڲ���ȵ������������У��

		// add by yangfan
		if (this.chkIsMDLiquidated.getSelected() == 16
				&& this.chkIsAccLiquidated.getSelected() == 32) {
			if (StringUtils.isEmpty(this.txtRate.getText())) {
				MsgBox.showInfo("ΥԼ���������¼�룡");
				abort();
			}
			if (this.txtRate.getBigDecimalValue().compareTo(FDCHelper.ZERO) < 0
					|| this.txtRate.getBigDecimalValue().compareTo(
							FDCHelper.ZERO) == 0) {
				MsgBox.showInfo("ΥԼ������������0��");
				abort();
			}
			// if(StringUtils.isEmpty(this.txtRelief.getText())){
			// MsgBox.showInfo("ΥԼ��������¼�룡");
			// abort();
			// }
			// if(this.txtRelief.getIntegerValue()<=0){
			// MsgBox.showInfo("ΥԼ�����������0��");
			// abort();
			// }
			// if(StringUtils.isEmpty(this.txtStandard.getText())){
			// MsgBox.showInfo("ΥԼ������׼����¼�룡");
			// abort();
			// }
			// if(this.txtStandard.getIntegerValue()<=0){
			// MsgBox.showInfo("ΥԼ������׼����������0��");
			// abort();
			// }
		}

		String room = "";
		BigDecimal roomArea = FDCHelper.ZERO;
		if (this.tblRoom.getRowCount() > 0) {
			TenancyRoomEntryInfo entry = (TenancyRoomEntryInfo) this.tblRoom
					.getRow(0).getUserObject();
			room = room + entry.getRoom().getName();
		}
		if (this.pkTenancyDate.getValue() != null) {
			room = room
					+ "("
					+ FDCDateHelper.DateToString((Date) this.pkTenancyDate
							.getValue()) + ")";
			
		}
		this.txtName.setText(room);
		

		FilterInfo filter = new FilterInfo();

		filter.getFilterItems().add(
				new FilterItemInfo("boID", this.editData.getId().toString()));
		if (!BoAttchAssoFactory.getRemoteInstance().exists(filter)) {
			FDCMsgBox.showWarning(this, "�����ϴ�������");
			SysUtil.abort();
		}
	}

	/**
	 * ��� �Կͻ���Ϣ����֤
	 */
	private void verifyCustomer() throws BOSException {
		BigDecimal percent = FDCHelper.ZERO;
		Map customerMap = new HashMap();
		if (this.tblCustomer.getRowCount() == 0) {
			MsgBox.showInfo("û�����޿ͻ�,�����!");
			this.abort();
		}

		for (int i = 0; i < this.tblCustomer.getRowCount(); i++) {
			IRow row = this.tblCustomer.getRow(i);
			if (row.getCell("propertyPercent").getValue() == null) {
				MsgBox.showInfo("��" + (row.getRowIndex() + 1) + "�пͻ�û�����ó������!");
				this.abort();
			}

			FDCCustomerInfo customer = (FDCCustomerInfo) row
					.getCell("customer").getValue();
			if (customer == null) {
				MsgBox.showInfo("��" + (row.getRowIndex() + 1) + "�пͻ�û��¼��!");
				this.abort();
			}
			// if (customer.getCertificateName() == null ||
			// customer.getCertificateNumber() == null) {
			// MsgBox.showInfo("�ͻ�" + customer.getName() + "֤������Ϊ��!!");
			// CustomerCardUI.addTheCustomerAuthority(customer, this);
			// setCustomerRowData(customer, row);
			// this.abort();
			// }
			if(isNeedSelectBrand){
				BrandCollection cbc = (BrandCollection) row.getCell("brand").getUserObject();
				if(cbc == null || cbc.size() == 0){
					MsgBox.showInfo("��" + (row.getRowIndex() + 1) + "��Ʒ��û��¼��!");
					this.abort();
				}
			}
			
			
			if (customerMap.containsKey(customer)) {
				MsgBox.showInfo("��" + (row.getRowIndex() + 1) + "�пͻ��ظ�!");
				this.abort();
			} else {
				customerMap.put(customer, customer);
			}
			percent = percent.add((BigDecimal) row.getCell("propertyPercent")
					.getValue());
		}
		if (percent.compareTo(new BigDecimal("100")) != 0) {
			MsgBox.showInfo("�����������100%!");
			this.abort();
		}
	}

	private void setCustomerRowData(FDCCustomerInfo customer, IRow row) {
		if (customer != null) {
			row.getCell("customer").setValue(customer);

			row.getCell("phone").setValue(customer.getPhone());
			row.getCell("postalcode").setValue(customer.getPostalcode());
			row.getCell("certificateName").setValue(
					customer.getCertificateName());
			row.getCell("certificateNumber").setValue(
					customer.getCertificateNumber());
			row.getCell("mailAddress").setValue(customer.getMailAddress());
			row.getCell("bookDate").setValue(customer.getCreateTime());
		} else {
			row.getCell("phone").setValue(null);
			row.getCell("postalcode").setValue(null);
			row.getCell("certificateName").setValue(null);
			row.getCell("certificateNumber").setValue(null);
			row.getCell("mailAddress").setValue(null);
			row.getCell("bookDate").setValue(null);
		}
	}

	private void verifyRentSet() {
		for (int i = 0; i < tblRentSet.getRowCount(); i++) {
			IRow row = tblRentSet.getRow(i);
			if (row.getCell(C_RENT_RENT_TYPE).getValue() == null) {
				MsgBox.showInfo("���������������ڲ���Ϊ��");
				abort();
			}
		}
	}

	/**
	 * У�鷿��.��Ҫ��У�鷿���Ƿ���������ͬ�������ϵ�ʱ���ͻ TODO ���У��Ŀǰ�ͻ��������Ǻܸ�
	 * */
	private void verifyRoom(Date startDate, Date endDate) throws BOSException {
		for (int i = 0; i < this.tblRoom.getRowCount(); i++) {
			IRow row = tblRoom.getRow(i);
			TenancyRoomEntryInfo tenRoom = (TenancyRoomEntryInfo) row
					.getUserObject();

			// BigDecimal tenRoomDealRent = tenRoom.getDealRoomRent();
			// if (tenRoomDealRent == null ||
			// tenRoomDealRent.compareTo(FDCHelper.ZERO) == 0) {
			// MsgBox.showInfo(this, "��" + (i + 1) + "�з���û���óɽ����.");
			// this.abort();
			// }
			BigDecimal roomTotalRent = tenRoom.getRoomTotalRent();
			RoomInfo room = tenRoom.getRoom();

			BigDecimal freeDays = FDCHelper.ZERO;
			if (this.txtFreeFeeInfo.getUserObject() != null)
				freeDays = FDCHelper.toBigDecimal(this.txtFreeFeeInfo
						.getUserObject());
			int lease = FDCDateHelper.getDiffDays(startDate, endDate);

			// �����ͬ���ܳ��������������Լ��������

			if (roomTotalRent == null)
				roomTotalRent = FDCHelper.ZERO;
			BigDecimal firstPayAmount = tenRoom.getFirstPayAmount();
			if (firstPayAmount == null)
				firstPayAmount = FDCHelper.ZERO;

			// if(firstPayAmount.compareTo(roomTotalRent) > 0){
			// MsgBox.showInfo(tenRoom.getRoomLongNum() + " ���õ���������˸÷����������.");
			// abort();
			// }

			// �ҵ�����������зǱ���,����ֹ,������״̬�����޷����¼.�ڱ�����֤�Ƿ��������ĺ�ͬ����ʱ���ͻ
			EntityViewInfo view = new EntityViewInfo();
			FilterInfo filter = new FilterInfo();
			view.setFilter(filter);

			filter.getFilterItems().add(
					new FilterItemInfo("tenancy.tenancyState",
							TenancyBillStateEnum.Saved, CompareType.NOTEQUALS));
			filter.getFilterItems().add(
					new FilterItemInfo("tenancy.tenancyState",
							TenancyBillStateEnum.Expiration,
							CompareType.NOTEQUALS));
			filter.getFilterItems().add(
					new FilterItemInfo("tenancy.tenancyState",
							TenancyBillStateEnum.BlankOut,
							CompareType.NOTEQUALS));

			if (this.editData.getId() != null) {
				filter.getFilterItems().add(
						new FilterItemInfo("tenancy.id", this.editData.getId()
								.toString(), CompareType.NOTEQUALS));
			}
			filter.getFilterItems().add(
					new FilterItemInfo("room.id", room.getId().toString()));

			TenancyRoomEntryCollection tenRooms = TenancyRoomEntryFactory
					.getRemoteInstance().getTenancyRoomEntryCollection(view);
			if (tenRooms != null && !tenRooms.isEmpty()) {

				TenancyRoomEntryInfo lastTenBeforeThisTenBill = null;// ����-
				// �����ĺ�ͬ��������֮ǰ
				// -
				// ����ĺ�ͬ
				// -
				// �ķ����¼
				// ͬһ������δ��ֹ����Ч��ͬ��������,��������ж��Ƿ����ʱ���ͻ
				for (int j = 0; j < tenRooms.size(); j++) {
					TenancyRoomEntryInfo tmpTenRoom = tenRooms.get(j);

					TenancyBillInfo tenBill = tmpTenRoom.getTenancy();

					// ����鵽�����޷������������ͬ��ԭ��ͬ,����������У��
					if (oldTenancyBill != null
							&& tenBill.getId().toString().equals(
									oldTenancyBill.getId().toString())) {
						continue;
					}

					Date tmpStartDate = tmpTenRoom.getStartDate();
					Date tmpEndDate = tmpTenRoom.getEndDate();
					// ������ͬ����ʼʱ�������к�ͬ������ʱ��֮�� ����
					// ������ͬ�Ľ���ʱ�������к�ͬ������ʱ��֮�� ����
					// ������ͬ������ʱ����������к�ͬ������ʱ��;��˵������ʱ���ͻ
					// ��Ϊ�����ǰ�Ƕ�̬��ʼ���ڵ����޺�ͬ�����ڸĳɹ̶���ʼ���ڣ���ô��ʼʱ����п���Ϊ��
					if (tmpStartDate != null && tmpEndDate != null) {
						if ((!startDate.before(tmpStartDate) && !startDate
								.after(tmpEndDate))
								|| (!endDate.before(tmpStartDate) && !endDate
										.after(tmpEndDate))
								|| (startDate.before(tmpStartDate) && endDate
										.after(tmpEndDate))) {
							MsgBox.showInfo(this, "��" + (i + 1) + "�з�������ʱ���ͻ.");
							this.abort();
						}

						if (!startDate.before(tmpEndDate)) {
							if (lastTenBeforeThisTenBill == null) {
								lastTenBeforeThisTenBill = tmpTenRoom;
							} else {
								if (lastTenBeforeThisTenBill.getTenancy() != null
										&& lastTenBeforeThisTenBill
												.getTenancy().getEndDate() != null) {
									if (!tmpEndDate
											.before(lastTenBeforeThisTenBill
													.getTenancy().getEndDate())) {
										lastTenBeforeThisTenBill = tmpTenRoom;
									}
								}
							}
						}
					}
				}

				// ��������ĺ�ͬ��������֮ǰ��������޷����¼���ǵ�������״̬,��������г���
				if (lastTenBeforeThisTenBill != null) {
					// ��������һ����ͬ����������ͬ��ԭ��ͬ,����������У��
					if (oldTenancyBill != null
							&& lastTenBeforeThisTenBill.getId().toString()
									.equals(oldTenancyBill.getId().toString())) {
						continue;
					}

					FlagAtTermEnum flagAtTerm = lastTenBeforeThisTenBill
							.getFlagAtTerm();
					if (!FlagAtTermEnum.QuitAtTerm.equals(flagAtTerm)) {
						MsgBox.showInfo(this, "��" + (i + 1) + "�еķ��䲻�ʺϳ���.");
						this.abort();
					}
				}
			}
		}
	}

	/**
	 * �Ը������ת���������������֤
	 * */
	private void verifySpecial(Date startDate) throws EASBizException,
			BOSException {
		TenancyContractTypeEnum tenType = getCurrentTenancyType();
		if (!TenancyContractTypeEnum.NewTenancy.equals(tenType)) {
			if (oldTenancyBill == null) {
				MsgBox.showInfo(this, "error impossiable!");
				this.abort();
			}

			// ���ԭ��ͬ�����ύ״̬��������״̬�ļ��ⵥ����������������и������ת��
			if (TenancyHelper.existRentRemissionByTenBill(RentRemissionFactory
					.getRemoteInstance(), oldTenancyBill.getId().toString())) {
				MsgBox.showInfo(this, "ԭ��ͬ���ڽ��������⣬����ִ�иò�����");
				this.abort();
			}

			if (TenancyHelper.existTenancyModificationByTenBill(
					TenancyModificationFactory.getRemoteInstance(),
					oldTenancyBill.getId().toString())) {
				MsgBox.showInfo(this, "ԭ��ͬ���ڽ��б��������ִ�иò�����");
				this.abort();
			}

			// ԭ��ͬ�ĵ�һ�첻�ܸ�������
			// if (!startDate.after(oldTenancyBill.getStartDate())) {
			// MsgBox.showInfo(this, "��ͬ��ʼ����Ӧ����ԭ��ͬ��ʼ���ڣ�");
			// this.abort();
			// }

			// ���ύ���ⵥ�ĺ�ͬ�����ٽ��и�������������
			if (TenancyHelper.existQuitTenBillByTenBill(QuitTenancyFactory
					.getRemoteInstance(), oldTenancyBill.getId().toString(),
					null)) {
				MsgBox.showInfo(this, "ԭ��ͬ�Ѿ������ⵥ��");
				this.abort();
			}

			// �Ѿ������˸����������ĺ�ͬ������ִ�иò���
			String targetTenId = TenancyHelper
					.getTargetTenIdBySrcTenancyId(oldTenancyBill.getId()
							.toString());
			if (targetTenId != null) {
				if (this.editData.getId() == null) {
					MsgBox.showInfo(this, "ԭ��ͬ�Ѿ����������⣡");
					this.abort();
				} else {// ��Ҫ�ѵ�ǰ�ύ�ĺ�ͬ����
					if (!this.editData.getId().toString().equals(targetTenId)) {
						MsgBox.showInfo(this, "ԭ��ͬ�Ѿ����������⣡");
						this.abort();
					}
				}
			}

			// ������������ʼ����һ��Ҫ��ԭ��ͬ����֮��
			if (TenancyContractTypeEnum.RejiggerTenancy.equals(tenType)
					|| TenancyContractTypeEnum.ChangeName.equals(tenType)) {
				Date oldStartDate = oldTenancyBill.getStartDate();
				oldStartDate = FDCDateHelper.getDayBegin(oldStartDate);
				Date oldEndDate = oldTenancyBill.getEndDate();
				oldEndDate = FDCDateHelper.getDayBegin(oldEndDate);
				if ((startDate.before(oldStartDate) && !startDate
						.equals(oldStartDate))
						|| (startDate.after(oldEndDate) && !startDate
								.equals(oldEndDate))) {
					MsgBox.showInfo(this, "�����ת����ͬ����ʼ���ڱ�����ԭ��ͬ��������֮��!");
					this.abort();
				}
			}

			TenancyBillStateEnum oldTenBillState = oldTenancyBill
					.getTenancyState();
			if (this.getOprtState().equalsIgnoreCase("ADDNEW")
					&& !TenancyBillStateEnum.Executing.equals(oldTenBillState)) {
				MsgBox.showInfo(this, "ֻ��ִ���еĺ�ͬ����" + tenType.getAlias() + "��");
				this.abort();
			}
		}
	}

	private void verifyBaseInfo() {
		if (this.txtNumber.isEditable() && this.txtNumber.isEnabled()
				&& StringUtils.isEmpty(this.txtNumber.getText())) {
			MsgBox.showInfo(this, "��ͬ���벻��Ϊ�գ�");
			this.abort();
		}

		// if (StringUtils.isEmpty(this.txtName.getText())) {
		// MsgBox.showInfo(this, "��ͬ���Ʋ���Ϊ�գ�");
		// this.abort();
		// }

		if (this.f7SellProject.getValue() == null) {
			MsgBox.showInfo(this, "������Ŀ����Ϊ�գ�");
			this.abort();
		}
		if (this.cbConRentType.getSelectedItem() == null) {
			MsgBox.showInfo(this, "��ͬ���ⷽʽ����Ϊ�գ�");
			this.abort();
		}
		if (this.cbContractType.getSelectedItem() == null) {
			MsgBox.showInfo(this, "��ͬ���Ͳ���Ϊ�գ�");
			this.abort();
		}
		if (this.cbTenancyBillType.getSelectedItem() == null) {
			MsgBox.showInfo(this, "���������Ϊ�գ�");
			this.abort();
		}
		if(this.txtTenPrice.isRequired()&&this.txtTenPrice.getBigDecimalValue()==null){
			MsgBox.showInfo(this, "Ӷ�𵥼۲���Ϊ�գ�");
			this.abort();
		}
		if (this.tblRoom.getRowCount() == 0
				&& this.tblAttachRes.getRowCount() == 0) {
			MsgBox.showInfo(this, "û�����޵���Դ��");
			this.abort();
		}

		// if (this.tblCustomer.getRowCount() == 0) {
		// MsgBox.showInfo(this, "����Ҫ��һ�����޿ͻ���");
		// this.abort();
		// }
		// for(int i=0;i<tblCustomer.getRowCount();i++)
		// {
		// IRow row = tblCustomer.getRow(i);
		// if(row.getCell(C_CUS_CERTIFICATE_NUMBER).getValue()==null)
		// {
		// MsgBox.showInfo("��"+(i+1)+"�пͻ���֤�����벻��Ϊ��");
		// this.abort();
		// }
		// }

		if (FirstLeaseTypeEnum.UserDefined.equals(comboFirstLeaseType
				.getSelectedItem())
				&& pkFirstLeaseEndDate.getValue() == null) {
			MsgBox.showInfo(this, "�Զ����������ͣ����ڽ�ֹ���ڲ���Ϊ�գ�");
			this.abort();
		}
		if (RentStartTypeEnum.DynamicStartDate
				.equals((RentStartTypeEnum) this.comboRentStartType
						.getSelectedItem())) {
			if (this.txtLeaseCount.getIntegerValue() == null
					|| this.txtLeaseCount.getIntegerValue().intValue() <= 0) {
				MsgBox.showInfo("��̬������ʼ���ڵĺ�ͬ���ڱ������0");
				this.abort();
			}
		} else {
			Date startDate = (Date) this.pkStartDate.getValue();
			Date endDate = (Date) this.pkEndDate.getValue();
			if (startDate == null || endDate == null) {
				MsgBox.showInfo(this, "������ʼʱ��ͽ���ʱ�䲻��Ϊ�գ�");
				this.abort();
			}

			startDate = FDCDateHelper.getDayBegin(startDate);
			endDate = FDCDateHelper.getDayBegin(endDate);
			if (startDate.after(endDate)) {
				MsgBox.showInfo(this, "������ʼʱ�䲻�ܳ��ڽ���ʱ�䣡");
				this.abort();
			}
		}

		if (this.cbTenBillRoomState.getSelectedItem() == null) {
			MsgBox.showInfo(this, "��Դ״̬����Ϊ�գ�");
			this.abort();
		}
	}

	public void actionAddRoom_actionPerformed(ActionEvent e) throws Exception {
		if (this.tblRoom.getRowCount() > 0) {
			MsgBox.showInfo(this, "ֻ��ѡ��һ�׷��䣡");
			this.abort();
		}
		// ���ѡ��ķ���
		BuildingInfo buildingInfo = (BuildingInfo) this.getUIContext().get(
				"building");
		BuildingUnitInfo buildUnit = (BuildingUnitInfo) this.getUIContext()
				.get("buildUnit");

		RoomInfo room = RoomSelectUI.showOneRoomSelectUI(this, buildingInfo,
				buildUnit, MoneySysTypeEnum.TenancySys, null, sellProjectInfo);
		if (room == null || room.isEmpty()) {
			return;
		}

		// �ж��Ƿ�ѡ�����ظ��ķ���
		Set existRoomIds = new HashSet();
		for (int i = 0; i < this.tblRoom.getRowCount(); i++) {
			IRow row = this.tblRoom.getRow(i);
			TenancyRoomEntryInfo tenRoom = (TenancyRoomEntryInfo) row
					.getUserObject();
			if (tenRoom == null || tenRoom.getRoom() == null) {
				continue;
			}
			String id = tenRoom.getRoom().getId().toString();
			existRoomIds.add(id);
		}

		TenancyRoomEntryCollection tenRooms = new TenancyRoomEntryCollection();
		// for (int i = 0; i < rooms.size(); i++) {
		// RoomInfo room = rooms.get(i);
		room = SHEHelper.queryRoomInfo(room.getId().toString());
		if (existRoomIds.contains(room.getId().toString())) {
			MsgBox.showInfo(this, "����" + room.getNumber() + "�Ѵ��������޷����б��У�");
			this.abort();
		}
		if (room.getTenancyState() == null
				|| TenancyStateEnum.unTenancy.equals(room.getTenancyState())) {
			MsgBox.showInfo(this, "����" + room.getNumber() + "δ���⣬���ܳ��⣡");
			this.abort();
		}
		if (TenancyStateEnum.sincerObligate.equals(room.getTenancyState())) {
			MsgBox.showInfo(this, "�����Ѿ���Ԥ�������Ƚ���ȡ��Ԥ��������");
			this.abort();
		}
		if (TenancyStateEnum.keepTenancy.equals(room.getTenancyState())) {
			MsgBox.showInfo(this, "����" + room.getNumber() + "�ѷ�棬���ܳ��⣡");
			this.abort();
		}

		if (room.getTenancyArea() == null
				|| room.getTenancyArea().compareTo(new BigDecimal(0)) == 0) {
			MsgBox.showInfo(this, "����" + room.getNumber() + "�������Ϊ�գ����ܳ���");
			this.abort();
		}

		TenancyRoomEntryInfo tenancyRoom = roomToTenRoomEntry(room);

		tenRooms.add(tenancyRoom);
		// }

		// ����������
		addRoomRows(tenRooms);
		setContMoreRoomsTypeVisiable();

		// ��������¼
		// TODO FIXME �ڴ˴�����˰����Ϣ
		reSetRentSetInfo(getTenRoomListFromView());

		// ���¸�����ϸ����Ϣ
		updatePayListInfo();

		this.prmtRentFreeBill.setEnabled(true);
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(
				new FilterItemInfo("state", FDCBillStateEnum.AUDITTED_VALUE));
		if (this.editData.getId() != null) {
			filter.getFilterItems().add(
					new FilterItemInfo("id",
							"select frentFreeBillId from T_TEN_TenancyBill where fid!='"
									+ this.editData.getId().toString()
									+ "' and frentFreeBillId is not null",
							CompareType.NOTINNER));
		} else {
			filter
					.getFilterItems()
					.add(
							new FilterItemInfo(
									"id",
									"select frentFreeBillId from T_TEN_TenancyBill where frentFreeBillId is not null",
									CompareType.NOTINNER));
		}
		filter.getFilterItems().add(
				new FilterItemInfo("room.id", room.getId().toString()));
		view.setFilter(filter);
		prmtRentFreeBill.setEntityViewInfo(view);
	}

	private void setContMoreRoomsTypeVisiable() {
		this.contMoreRoomsType.setVisible(this.tblRoom.getRowCount() > 1);
	}

	protected void btnAddAttachRes_actionPerformed(ActionEvent e)
			throws Exception {
		// ���ѡ���������Դ
		KDCommonPromptDialog dlg = new KDCommonPromptDialog();
		IMetaDataLoader loader = MetaDataLoaderFactory
				.getRemoteMetaDataLoader();
		dlg.setQueryInfo(loader.getQuery(new MetaDataPK(
				"com.kingdee.eas.fdc.tenancy.app.AttachResourceQuery")));
		dlg.show();
		Object[] object = (Object[]) dlg.getData();

		if (object == null || object.length == 0) {
			return;
		}

		AttachResourceCollection attachReses = new AttachResourceCollection();
		for (int i = 0; i < object.length; i++) {
			AttachResourceInfo attachRes = (AttachResourceInfo) object[0];
			attachReses.add(attachRes);
		}

		// �ж��Ƿ�ѡ�����ظ���������Դ
		Set existAttachIds = new HashSet();
		for (int i = 0; i < this.tblAttachRes.getRowCount(); i++) {
			IRow row = this.tblAttachRes.getRow(i);
			TenAttachResourceEntryInfo tenAttach = (TenAttachResourceEntryInfo) row
					.getUserObject();
			if (tenAttach == null || tenAttach.getAttachResource() == null) {
				continue;
			}
			String id = tenAttach.getAttachResource().getId().toString();
			existAttachIds.add(id);
		}

		TenAttachResourceEntryCollection tenAttachReses = new TenAttachResourceEntryCollection();

		SelectorItemCollection sels = new SelectorItemCollection();
		sels.add("*");
		sels.add("sellProject.name");
		sels.add("subarea.name");
		sels.add("building.name");

		for (int i = 0; i < attachReses.size(); i++) {
			AttachResourceInfo attach = attachReses.get(i);
			if (existAttachIds.contains(attach.getId().toString())) {
				MsgBox.showInfo(this, "������Դ" + attach.getNumber()
						+ "�Ѵ���������������Դ�б��У�");
				this.abort();
			}

			if (attach.getRentType() == null
					|| attach.getStandardRent() == null) {
				MsgBox
						.showInfo(this, "������Դ" + attach.getNumber()
								+ "δ���⣬���ܳ��⣡");
				this.abort();
			}
			attach = AttachResourceFactory.getRemoteInstance()
					.getAttachResourceInfo(
							new ObjectUuidPK(attach.getId().toString()), sels);
			TenAttachResourceEntryInfo tenAttach = attachToTenAttach(attach);
			tenAttachReses.add(tenAttach);
		}

		// ����������Դ��
		addAttachResRows(tenAttachReses);

		// ��������¼
		reSetRentSetInfo(getTenRoomListFromView(),
				getTenAttachResListFromView());

		// ���¸�����ϸ����Ϣ
		updatePayListInfo();
	}

	private void addAttachResRows(
			TenAttachResourceEntryCollection tenAttachReses) {
		for (int i = 0; i < tenAttachReses.size(); i++) {
			TenAttachResourceEntryInfo tenAttach = tenAttachReses.get(i);
			addAttachResRow(tenAttach);
		}
	}

	/**
	 * ����һ�����޸�����Դ��¼�ڸ�����Դ�б�������һ��
	 * 
	 * @param tenAttach
	 * */
	private void addAttachResRow(TenAttachResourceEntryInfo tenAttach) {
		if (tenAttach == null) {
			return;
		}
		AttachResourceInfo attach = tenAttach.getAttachResource();
		if (attach == null) {
			return;
		}

		IRow row = this.tblAttachRes.addRow();
		row.setUserObject(tenAttach);

		//row.getCell(C_ATTACH_TEN_ROOM_STATE).setValue(tenAttach.getTenRoomState
		// ());
		row.getCell(C_ATTACH_ATTACH).setValue(tenAttach.getAttachLongNum());
		// TODO ����Ӧ��ҲҪ���յ�
		row.getCell(C_ATTACH_ATTACH_RES_TYPE).setValue(attach.getAttachType());
		row.getCell(C_ATTACH_ATTACH_RES_NAME).setValue(attach.getName());
		row.getCell(C_ATTACH_ATTACH_RES_DES).setValue(attach.getDescription());

		row.getCell(C_ATTACH_STANDARD_RENT).setValue(
				tenAttach.getStandardAttachResRent());
		row.getCell(C_ATTACH_STANDARD_RENT_TYPE).setValue(
				tenAttach.getStandardRentType());
		// row.getCell(C_ATTACH_STANDARD_RENT_PRICE).setValue(tenAttach.
		// getStandardAttachResRentPrice());
		//row.getCell(C_ATTACH_DEAL_RENT).setValue(tenAttach.getDealAttachResRent
		// ());
		//row.getCell(C_ATTACH_DEAL_RENT_TYPE).setValue(tenAttach.getDealRentType
		// ());

		// if (attach != null) {
		// row.getCell(C_ROOM_FLOOR).setValue(new Integer(attach.getFloor()));
		// row.getCell(C_ROOM_FITMENT).setValue(attach.getFitmentStandard());
		// row.getCell(C_ROOM_ROOM_MODEL).setValue(attach.getRoomModel() == null
		// ? null : attach.getRoomModel().getName());
		// row.getCell(C_ROOM_DIRECTION).setValue(attach.getDirection() == null
		// ? null : attach.getDirection().getName());
		// }

		row.getCell(C_ROOM_ACT_DELIVER_DATE).setValue(
				tenAttach.getActDeliveryAttachResDate());
		row.getCell(C_ROOM_ACT_QUIT_DATE).setValue(
				tenAttach.getActQuitTenDate());

		// �����������Table
		// updateTblRentSetRow();

	}

	private TenAttachResourceEntryInfo attachToTenAttach(
			AttachResourceInfo attach) {
		if (attach == null) {
			return null;
		}
		TenAttachResourceEntryInfo tenAttach = new TenAttachResourceEntryInfo();
		tenAttach.setAttachResource(attach);

		// tenAttach.setTenancyModel(attach.getTenancyModel());
		tenAttach.setHandleState(HandleStateEnum.NoHandleRoom);

		tenAttach.setStandardRentType(attach.getRentType());
		tenAttach.setStandardAttachResRent(attach.getStandardRent());
		// tenAttach.setStandardAttachResRentPrice(attach.getsta());

		// ִ�����Ĭ�ϸ���׼���۸�
		tenAttach.setDealRentType(attach.getRentType());
		tenAttach.setDealAttachResRent(attach.getStandardRent());
		// tenAttach.setDealRoomRentPrice(attach.getStandardRentPrice());

		if (tenAttach.getDealAttachResRent() != null) {
			tenAttach.setDepositAmount(new BigDecimal("2").multiply(tenAttach
					.getDealAttachResRent()));
		}
		tenAttach.setFirstPayAmount(tenAttach.getDealAttachResRent());

		// ���շ���Ľ��������ʵ�����
		// ����ȡʵ�⽨��,���û����ȡ�������

		// ���շ��� װ�ޱ�׼�����򣬾��ۣ����ͣ�������ʽ���������ʣ���Ʒ���ͣ���Ʒ������������;

		// ����Ĭ�ϵķ��䵽�ڱ�־�ͷ�������״̬
		tenAttach.setFlagAtTerm(FlagAtTermEnum.Unknown);
		tenAttach.setTenAttachState(TenancyStateEnum.newTenancy);

		// ��ó�����
		StringBuffer sbRoomLongNum = new StringBuffer();
		BuildingInfo building = attach.getBuilding();
		SellProjectInfo sellPro = attach.getSellProject();
		SubareaInfo subArea = attach.getSubarea();

		final String spitStr = "-";
		if (sellPro != null) {
			sbRoomLongNum.append(sellPro.getName());
			sbRoomLongNum.append(spitStr);
		}
		if (subArea != null) {
			sbRoomLongNum.append(subArea.getName());
			sbRoomLongNum.append(spitStr);
		}
		if (building != null) {
			sbRoomLongNum.append(building.getName());
			sbRoomLongNum.append(spitStr);
		}
		sbRoomLongNum.append(attach.getNumber());
		tenAttach.setAttachLongNum(sbRoomLongNum.toString());
		return tenAttach;

	}

	public void actionAddCustomer_actionPerformed(ActionEvent e)
			throws Exception {
		super.actionAddCustomer_actionPerformed(e);
		int activeRowIndex = this.tblCustomer.getSelectManager()
				.getActiveRowIndex();
		IRow row = null;
		if (activeRowIndex == -1) {
			row = this.tblCustomer.addRow();
		} else {
			row = this.tblCustomer.addRow(activeRowIndex + 1);
		}
		row.setHeight(20);
		row.setUserObject(new TenancyCustomerEntryInfo());
		if (this.tblCustomer.getRowCount() == 1) {
			row.getCell(C_CUS_PROPERTY_PERCENT).setValue(new BigDecimal("100"));
		}

	}

	protected void btnAddNewCustomer_actionPerformed(ActionEvent e)
			throws Exception {
		UIContext uiContext = new UIContext(this);
		// ����UI������ʾ
		String cte = "EC";
		uiContext.put("ECT", cte);
		IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.MODEL)
				.create(CustomerEditUI.class.getName(), uiContext, null,
						"ADDNEW");
		uiWindow.show();

		CustomerEditUI cusEditUI = (CustomerEditUI) uiWindow.getUIObject();
		FDCCustomerInfo cus = (FDCCustomerInfo) cusEditUI.getUIContext().get(
				CustomerEditUI.KEY_SUBMITTED_DATA);
		if (cus != null) {
			addCustomerRow(cus);
		}

	}

	private void addCustomerRow(FDCCustomerInfo customer) {
		int activeRowIndex = this.tblCustomer.getSelectManager()
				.getActiveRowIndex();
		IRow row = null;
		if (activeRowIndex == -1) {
			row = this.tblCustomer.addRow();
		} else {
			row = this.tblCustomer.addRow(activeRowIndex + 1);
		}
		row.setHeight(20);
		row.setUserObject(new TenancyCustomerEntryInfo());
		if (this.tblCustomer.getRowCount() == 1) {
			row.getCell(C_CUS_PROPERTY_PERCENT).setValue(new BigDecimal("100"));
		}
		row.getCell("customer").setValue(customer);
		row.getCell("phone").setValue(customer.getPhone());
		if (customer.getPostalcode() != null) {
			row.getCell("postalcode").setValue(customer.getPostalcode());
		}
		if (customer.getCertificateName() != null) {
			row.getCell("certificateName").setValue(
					customer.getCertificateName());
		}
		if (customer.getCertificateNumber() != null) {
			row.getCell("certificateNumber").setValue(
					customer.getCertificateNumber());
		}
		if (customer.getMailAddress() != null) {
			row.getCell("mailAddress").setValue(customer.getMailAddress());
		}
		row.getCell("bookDate").setValue(customer.getCreateTime());
		if (customer.getDescription() != null) {
			row.getCell("des").setValue(customer.getDescription());
		}
		updateTotalInfo();
	}

	protected void chkIsByAgency_actionPerformed(ActionEvent e)
			throws Exception {
		super.chkIsByAgency_actionPerformed(e);
		setAgencySourceVisible();
	}

	protected void f7Agency_dataChanged(DataChangeEvent e) throws Exception {
		super.f7Agency_dataChanged(e);
		setAgencySourceVisible();
	}

	protected void f7AgencyContract_dataChanged(DataChangeEvent e)
			throws Exception {
		super.f7AgencyContract_dataChanged(e);
		if (f7AgencyContract.getData() == null) {
			this.txtPromissoryAgentFee.setValue(null);
			this.pkPromissoryAppPayDate.setValue(null);
		} else {
			updatePromissoryAgentFee();
		}
	}

	/**
	 * ���ô�������ҳǩ�Ƿ���ʾ
	 * */
	private void setAgencySourceVisible() {
		boolean isByAgency = this.chkIsByAgency.isSelected();
		SellProjectInfo sellProject = (SellProjectInfo) this.f7SellProject
				.getValue();
		int i = 6;
		if (sellProject != null) {
			if (!sellProject.isIsForPPM()) {
				i = 5;
			}
		}
		if (isByAgency) {
			if (this.tabbedPaneContract.getTabCount() == i) {
				this.tabbedPaneContract.add(panelAgency, "��������", 4);
			}
			if (this.f7Agency.getData() == null) {
				this.f7AgencyContract.setData(null);
				this.f7AgencyContract.setEnabled(false);
			} else {
				this.f7AgencyContract.setEnabled(true);

				f7AgencyContract.getQueryAgent().setDefaultFilterInfo(null);
				f7AgencyContract.getQueryAgent().setHasCUDefaultFilter(false);
				f7AgencyContract.getQueryAgent().resetRuntimeEntityView();

				EntityViewInfo view = new EntityViewInfo();
				FilterInfo filter = new FilterInfo();
				view.setFilter(filter);

				String sellProjectId = ((SellProjectInfo) this.f7SellProject
						.getData()).getId().toString();
				String agencyId = ((AgencyInfo) this.f7Agency.getData())
						.getId().toString();

				filter.getFilterItems().add(
						new FilterItemInfo("sellProject.id", sellProjectId));
				filter.getFilterItems().add(
						new FilterItemInfo("agency.id", agencyId));
				this.f7AgencyContract.setEntityViewInfo(view);
			}
			if (f7AgencyContract.getData() == null) {
				this.txtPromissoryAgentFee.setValue(null);
				this.pkPromissoryAppPayDate.setValue(null);
			}
		} else {
			this.tabbedPaneContract.remove(panelAgency);
		}
	}

	public void actionRemoveRoom_actionPerformed(ActionEvent e)
			throws Exception {
		// ɾ�������б��ϵļ�¼
		int activeRowIndex = this.tblRoom.getSelectManager()
				.getActiveRowIndex();
		if (activeRowIndex == -1) {
			activeRowIndex = this.tblRoom.getRowCount();
		}
		IRow row = this.tblRoom.getRow(activeRowIndex);
		if (row == null)
			return;
		TenancyRoomEntryInfo tenRoom = (TenancyRoomEntryInfo) row
				.getUserObject();

		for (int i = 0; i < this.tabMiddle.getTabCount(); i++) {
			String title = this.tabMiddle.getTitleAt(i);
			if (tenRoom.getRoomLongNum().equals(title)) {
				this.tabMiddle.remove(i);
				break;
			}
		}
		this.tblRoom.removeRow(activeRowIndex);
		if (this.tblRoom.getRowCount() > 1) {
			this.contMoreRoomsType.setVisible(true);
		} else {
			this.contMoreRoomsType.setVisible(false);
		}
		// ��������¼
		reSetRentSetInfo(getTenRoomListFromView());

		// ɾ�����䣬���ܲ���Ҫ��������¼��TODO ����֤
		updatePayListInfo();

		this.prmtRentFreeBill.setValue(null);
		this.prmtRentFreeBill.setEnabled(false);

	}

	protected void btnRemoveAttachRes_actionPerformed(ActionEvent e)
			throws Exception {
		// super.btnRemoveAttachRes_actionPerformed(e);
		// removeRow(this.tblAttachRes);
		// ��������¼
		reSetRentSetInfo(getTenRoomListFromView());
		updatePayListInfo();
	}

	public void actionRemoveCustomer_actionPerformed(ActionEvent e)
			throws Exception {
		super.actionRemoveCustomer_actionPerformed(e);
		removeRow(this.tblCustomer);
	}

	/** ɾ��table�е�һ��,��ѡ��ʱɾ��ѡ����,����ɾ�����һ�� */
	private void removeRow(KDTable table) {
		int activeRowIndex = table.getSelectManager().getActiveRowIndex();
		if (activeRowIndex == -1) {
			activeRowIndex = table.getRowCount();
		}
		table.removeRow(activeRowIndex);
	}

	public void storeFields() {
		super.storeFields();
		this.editData.setName(this.editData.getTenancyName());

		this.editData
				.setRentCountType((RentCountTypeEnum) this.comboRentCountType
						.getSelectedItem());
		if (this.txtDaysPerYear.getIntegerValue() != null) {
			this.editData.setDaysPerYear(this.txtDaysPerYear.getIntegerValue()
					.intValue());
		}
		this.editData.setIsAutoToInteger(this.chkIsAutoToInteger.isSelected());
		this.editData.setIsAutoToIntegerFee(this.chkIsAutoToIntegerForFee
				.isSelected());

		this.editData
				.setToIntegerType((ToIntegerTypeEnum) this.comboToIntegerType
						.getSelectedItem());
		this.editData
				.setToIntegetTypeFee((ToIntegerTypeEnum) this.comboToIntegerTypeFee
						.getSelectedItem());

		this.editData.setDigit((DigitEnum) this.comboDigit.getSelectedItem());
		this.editData.setDigitFee((DigitEnum) this.comboDigitFee
				.getSelectedItem());

		this.editData
				.setRentStartType((RentStartTypeEnum) this.comboRentStartType
						.getSelectedItem());
		this.editData
				.setStartDateLimit((Date) this.pkStartDateLimit.getValue());
		this.editData
				.setMoreRoomsType((MoreRoomsTypeEnum) this.comboMoreRoomsType
						.getSelectedItem());
		this.editData.setIsFreeContract(this.chkIsFreeContract.isSelected());
		this.editData.setFristRevDate((Date) this.pkFristLeaseDate.getValue());
		this.editData.setSecondRevDate((Date) this.DPickFromMonth.getValue());
		this.editData.setOrgUnit((FullOrgUnitInfo) this.f7BussinessDepartMent
				.getValue());
		// ����storePayList�������ֹ��޸��ˣ����Կ��Բ��õ���������
		if (this.chkIsFreeContract.isSelected()) {
			fillFreeConTenRoomPayList(getTenRoomListFromView());
		}
		storeTenancyRooms();
		storeTenancyAttachResources();
		storeTenancyCustomer();

		// add by yangfan
		storeLiquidated();

		// ���������¼�������¼
		this.editData.getIncreasedRents().clear();
		this.editData.getIncreasedRents().addCollection(
				getIncreasedRentsFromView());
		this.editData.getRentFrees().clear();
		this.editData.getRentFrees().addCollection(getRentFreesFromView());
		//

		storeTenRoomPropertyEntrys();
		// storePayList();
		storeOtherPayList();
		
		int cc = this.tblTenPrice.getRowCount();
		Set<String> tenPriceIdS = new HashSet<String>();
		TenancyPriceEntryCollection priceEntry= this.editData.getTenPriceEntry();
		priceEntry.clear();
		for(int i=0;i<cc;i++){
			TenancyPriceEntryInfo tenPriceEntry = (TenancyPriceEntryInfo) this.tblTenPrice.getRow(i).getUserObject();
			if(tenPriceEntry != null){
				TenPriceEntryInfo info = tenPriceEntry.getTenPrice();
				tenPriceEntry.setTenPrice(info);
				tenPriceEntry.setParent(this.editData);
				priceEntry.add(tenPriceEntry);
				tenPriceIdS.add(info.getId().toString());
			}
		}
		editData.put("tenPriceIdS", tenPriceIdS);
		
		BigDecimal depositAmount=FDCHelper.ZERO;
		Iterator it = this.editData.getTenancyRoomList().iterator();
		while (it.hasNext()) {

			TenancyRoomEntryInfo tenRoomEntryInfo = (TenancyRoomEntryInfo) it
					.next();

			int sum = tenRoomEntryInfo.getDealAmounts().size();
			if (sum > 0) {
				for (int i = 0; i < sum; i++) {

					DealAmountEntryInfo dealMoneyInfo = tenRoomEntryInfo
							.getDealAmounts().get(i);
					// �ж�Ŀ¼��Ѻ������ʾ
					if (dealMoneyInfo.getMoneyDefine().getMoneyType().equals(MoneyTypeEnum.DepositAmount)) {
						depositAmount=FDCHelper.add(depositAmount,dealMoneyInfo.getAmount());
					}

				}
			}

			this.editData.setDepositAmount(depositAmount);

		}
	}

	private void storeOtherPayList() {
		TenancyBillInfo ten = this.editData;
		ten.getOtherPayList().clear();
		for (int i = 0; i < this.tblOtherPayList.getRowCount(); i++) {
			IRow row = this.tblOtherPayList.getRow(i);
			TenBillOtherPayInfo tenOtherInfo = (TenBillOtherPayInfo) row
					.getUserObject();
			tenOtherInfo.setSeq(i);
			tenOtherInfo.setMoneyDefine((MoneyDefineInfo) row.getCell(
					"moneyTypeName").getValue());
			tenOtherInfo.setAppDate((Date) row.getCell("appDate").getValue());
			tenOtherInfo.setStartDate((Date) row.getCell("startDate")
					.getValue());
			tenOtherInfo.setEndDate((Date) row.getCell("endDate").getValue());
			tenOtherInfo.setCurrency((CurrencyInfo) row.getCell("currency")
					.getValue());
			tenOtherInfo.setAppAmount((BigDecimal) row.getCell("appAmount")
					.getValue());
			tenOtherInfo.setActRevAmount((BigDecimal) row.getCell(
					"actRevAmount").getValue());
			tenOtherInfo.setActRevDate((Date) row.getCell("actRevDate")
					.getValue());
			tenOtherInfo.setDescription((String) row.getCell("description")
					.getValue());
			ten.getOtherPayList().add(tenOtherInfo);
		}
	}

	// ������ҵҳǩ�����Ϣ
	private void storeTenRoomPropertyEntrys() {
		for (int i = 0; i < tabMiddle.getTabCount(); i++) {
			Component com = this.tabMiddle.getComponentAt(i);
			TenancyPropertyUI ui = (TenancyPropertyUI) com;
			ui.storeTenEntrys();
		}
	}

	// Ӧ����ϸ��֧���ֹ�¼��ʱ���ú���Ҳû��
	private void storePayList() {
		// �Ȼ������Ӧ��������
		List appPayColKeys = getAppPayColKeys();

		TenancyRoomPayListEntryCollection newPayList = new TenancyRoomPayListEntryCollection();
		// ��������Ӧ������Ӧ������
		for (int i = 0; i < tblPayList.getRowCount(); i++) {
			IRow row = this.tblPayList.getRow(i);
			if (row.getUserObject() != null) {
				ITenancyPayListInfo payListInfo = (ITenancyPayListInfo) row
						.getUserObject();
				Date appDate = (Date) row.getCell(C_PAYS_APP_PAY_DATE)
						.getValue();
				appDate = FDCDateHelper.getDayBegin(appDate);
				Date startDate = (Date) row.getCell(C_PAYS_START_DATE)
						.getValue();
				startDate = FDCDateHelper.getDayBegin(startDate);
				Date endDate = (Date) row.getCell(C_PAYS_END_DATE).getValue();
				endDate = FDCDateHelper.getDayBegin(endDate);
				int leaseSeq = payListInfo.getLeaseSeq();
				int seq = payListInfo.getSeq();
				MoneyDefineInfo money = payListInfo.getMoneyDefine();
				for (int j = 0; j < appPayColKeys.size(); j++) {
					String key = (String) appPayColKeys.get(j);
					ICell cell = row.getCell(key);
					BigDecimal appAmount = (BigDecimal) cell.getValue();
					Object ob = row.getCell(key).getUserObject();
					TenancyRoomPayListEntryInfo pay = new TenancyRoomPayListEntryInfo();// TODO
					// pay.setTenRoom(item); // δ����������Դ
					pay.setAppAmount(appAmount);
					pay.setAppPayDate(appDate);
					pay.setStartDate(startDate);
					pay.setEndDate(endDate);
					pay.setSeq(seq);
					pay.setMoneyDefine(money);
					pay.setLeaseSeq(leaseSeq);
					newPayList.add(pay);
				}
			}
		}
	}

	/**
	 * �������Ӧ��������
	 * 
	 * @deprecated �ú���Ŀǰû��
	 * */
	private List getAppPayColKeys() {
		List appPayColKeys = new ArrayList();
		for (int i = 0; i < this.tblPayList.getColumnCount(); i++) {
			IColumn col = this.tblPayList.getColumn(i);
			if (col.getKey().endsWith(POSTFIX_C_PAYS_APP_AMOUNT)) {// TODO
				// ���ｫ������Դ��Ӧ��Ҳ������
				appPayColKeys.add(col.getKey());
			}
		}
		return appPayColKeys;
	}

	private void storeTenancyCustomer() {
		TenancyBillInfo tenBill = this.editData;
		TenancyCustomerEntryCollection customerInfos = tenBill
				.getTenCustomerList();
		customerInfos.clear();
		StringBuffer tenCustomerDes = new StringBuffer();
		StringBuffer str = new StringBuffer();
		for (int i = 0; i < this.tblCustomer.getRowCount(); i++) {
			IRow row = this.tblCustomer.getRow(i);
			TenancyCustomerEntryInfo customerInfo = (TenancyCustomerEntryInfo) row
					.getUserObject();
			FDCCustomerInfo customer = (FDCCustomerInfo) row.getCell(
					C_CUS_CUSTOMER).getValue();
			if (customer == null) {
				MsgBox.showInfo("���޿ͻ�����Ϊ��!");
				this.abort();
			}
			if (customer != null) {
				customer.setMailAddress((String) row
						.getCell(C_CUS_MAIL_ADDRESS).getValue());
				customer.setPhone((String) row.getCell(C_CUS_PHONE).getValue());
				customer.setPostalcode((String) row.getCell(C_CUS_POSTALCODE)
						.getValue());
				if (row.getCell(C_CUS_CERTIFICATE_NAME).getValue() != null) {
					// customer.setCertificateName((CertifacateNameEnum)
					// row.getCell(C_CUS_CERTIFICATE_NAME).getValue());
				}
				customer.setCertificateNumber((String) row.getCell(
						C_CUS_CERTIFICATE_NUMBER).getValue());
			}
			
			customerInfo.setFdcCustomer(customer);
			customerInfo.setPropertyPercent((BigDecimal) row.getCell(
					C_CUS_PROPERTY_PERCENT).getValue());
			customerInfo.setDescription((String) row.getCell(C_CUS_DES)
					.getValue());
			customerInfos.add(customerInfo);
			CustomerEntryBrandCollection cbc = customerInfo.getBrandEntry();
			CustomerEntryBrandInfo cebi = null;
			cbc.clear();
			
			BrandCollection bc = (BrandCollection) row.getCell("brand").getUserObject();
			if(bc != null){
				for(Iterator it = bc.iterator();it.hasNext();){
					BrandInfo  info = (BrandInfo) it.next();
					cebi = new CustomerEntryBrandInfo();
					cebi.setBrand(info);
					cebi.setParent(customerInfo);
					cbc.add(cebi);
					str.append(info.getName()+";");
				}
			}
			if (i != 0) {
				tenCustomerDes.append(",");
			}
			tenCustomerDes.append(customer.getName());
		    
			
			
		}
		tenBill.setTenCustomerDes(tenCustomerDes.toString());
		tenBill.setBrandDesc(str.toString());
	}

	private void storeTenancyAttachResources() {
		TenancyBillInfo tenBill = this.editData;
		TenAttachResourceEntryCollection tenAttachs = tenBill
				.getTenAttachResourceList();
		tenAttachs.clear();
		StringBuffer attachDes = new StringBuffer();
		// ��Ϊ�ڷ����б��edittstopped�¼���,�Ѿ����䶯��ֵ���µ�row.getUserObject��,
		// ��������ֻ��Ҫ��userObjectȡ������
		for (int i = 0; i < this.tblAttachRes.getRowCount(); i++) {
			IRow row = this.tblAttachRes.getRow(i);
			TenAttachResourceEntryInfo tenAttach = (TenAttachResourceEntryInfo) row
					.getUserObject();

			// tenAttach.setDescription((String)
			// row.getCell(C_ROOM_DES).getValue());
			tenAttach.setStartDate(tenBill.getStartDate());
			tenAttach.setEndDate(tenBill.getEndDate());
			tenAttachs.add(tenAttach);
			if (i != 0) {
				attachDes.append(",");
			}
			attachDes.append(tenAttach.getAttachLongNum());
		}
		tenBill.setTenAttachesDes(attachDes.toString());

	}

	private void storeTenancyRooms() {
		TenancyBillInfo tenBill = this.editData;
		TenancyRoomEntryCollection tenancyRooms = tenBill.getTenancyRoomList();
		tenancyRooms.clear();
		StringBuffer roomsDes = new StringBuffer();
		String roomDesString = null;
		// ��Ϊ�ڷ����б��edittstopped�¼���,�Ѿ����䶯��ֵ���µ�row.getUserObject��,
		// ��������ֻ��Ҫ��userObjectȡ������
		boolean flag = true;
		for (int i = 0; i < this.tblRoom.getRowCount(); i++) {
			IRow row = this.tblRoom.getRow(i);
			TenancyRoomEntryInfo tenancyRoom = (TenancyRoomEntryInfo) row
					.getUserObject();

			tenancyRoom.setDescription((String) row.getCell(C_ROOM_DES)
					.getValue());
			tenancyRoom.setStartDate(tenBill.getStartDate());
			tenancyRoom.setEndDate(tenBill.getEndDate());
			tenancyRooms.add(tenancyRoom);
			if (i != 0) {
				roomsDes.append(",");
			}

			roomsDes.append(tenancyRoom.getRoomLongNum());
			// ���޷�����������255ʱ��ֻ�ᱻ�ض� eirc_wang 2010.07.01
			if (flag) {
				if (roomsDes.toString().length() > 250) {
					roomDesString = roomsDes.substring(0, 250);
					roomDesString = roomDesString + "...";
					flag = false;
				} else {
					// ����������������255ʱȴ��������ֵ��������ʱ�����ⷿ����ʾȫ��Ϊ��
					roomDesString = roomsDes.toString();
				}
			}
		}
		tenBill.setTenRoomsDes(roomDesString);
	}

	/**
	 * add by yangfan
	 */
	private void storeLiquidated() {
		TenancyBillInfo tenBill = this.editData;
		TenLiquidatedCollection collection = tenBill.getTenLiquidated();
		collection.clear();

		for (int i = 0; i < this.tblLiquidated.getRowCount(); i++) {
			IRow row = this.tblLiquidated.getRow(i);
			TenLiquidatedInfo info = (TenLiquidatedInfo) row.getUserObject();
			info.setMoneyDefine((MoneyDefineInfo) row.getCell(
					C_LIQUI_MONEYDEFINE).getValue());
			info.setLiquidated((LiquidatedInfo) row.getCell(C_LIQUI_LIQUIDATED)
					.getValue());
			collection.add(info);
		}
	}

	/**
	 * �����¶���,������Ҫ��2������� <br>
	 * 1.���ݴ����KEY_ROOMS,�������޷����¼ <br>
	 * 2.�����ͬ�Ǹ���,������߸���,��ԭ��ͬ�������Ϣ�����º�ͬ.
	 * */
	protected IObjectValue createNewData() {
		TenancyBillInfo tenancyBill = new TenancyBillInfo();
		tenancyBill.setId(BOSUuid.create(tenancyBill.getBOSType()));
		tenancyBill.setTenBillRoomState(TenBillRoomStateEnum.TEN);
		// �����޿��ƽ��洫���ķ���
		RoomCollection rooms = (RoomCollection) this.getUIContext().get(
				KEY_ROOMS);
		if (rooms != null) {
			for (int i = 0; i < rooms.size(); i++) {
				RoomInfo room = rooms.get(i);
				// TODO ���֧�ֶ����������ޣ��������仹������ͬһ����Ŀ��.
				if (TenancyStateEnum.sincerObligate.equals(room
						.getTenancyState())) {
					MsgBox.showInfo(this, "�����Ѿ���Ԥ�������Ƚ���ȡ��Ԥ��������");
					this.abort();
				}
				if (i == 0) {
					SellProjectInfo sellProject = room.getBuilding()
							.getSellProject();
					tenancyBill.setSellProject(sellProject);
				}
				TenancyRoomEntryInfo tenRoom = roomToTenRoomEntry(room);
				tenancyBill.getTenancyRoomList().add(tenRoom);
			}
		} else {
			tenancyBill.setSellProject((SellProjectInfo) this.getUIContext()
					.get(KEY_SELL_PROJECT));
		}

		// ����Ԥ��ת����
		SincerObligateInfo sinInfo = (SincerObligateInfo) this.getUIContext()
				.get("sincerObligate");
		if (sinInfo != null) {
			try {
				sinInfo = TenancyHelper.getSincerObligateInfo(sinInfo.getId()
						.toString());
			} catch (Exception e) {
				e.printStackTrace();
			}

			sinInfo.setBizState(BizStateEnum.TENANCY);
			tenancyBill.setSincerObligate(sinInfo);
			tenancyBill.setSellProject(sinInfo.getSellProject());
			TenancyRoomEntryCollection tenancyColl = tenancyBill
					.getTenancyRoomList();
			TenAttachResourceEntryCollection tenAttColl = tenancyBill
					.getTenAttachResourceList();
			TenancyCustomerEntryCollection tenCusColl = tenancyBill
					.getTenCustomerList();
			SinObligateRoomsEntryCollection sincerColl = sinInfo
					.getSinRoomList();
			SinObligateAttachEntryCollection sinAttColl = sinInfo
					.getSinAttachList();
			SinCustomerEntrysCollection sinCusColl = sinInfo
					.getSinCustomerList();
			for (int i = 0; i < sincerColl.size(); i++) {
				TenancyRoomEntryInfo tenInfo = new TenancyRoomEntryInfo();
				SinObligateRoomsEntryInfo sinRoomEntryInfo = sincerColl.get(i);
				tenInfo.setRoom(sinRoomEntryInfo.getRoom());
				tenInfo = roomToTenRoomEntry(sinRoomEntryInfo.getRoom());
				tenancyColl.add(tenInfo);
			}
			for (int i = 0; i < sinAttColl.size(); i++) {
				TenAttachResourceEntryInfo tenAttachInfo = new TenAttachResourceEntryInfo();
				SinObligateAttachEntryInfo sinAttInfo = sinAttColl.get(i);
				tenAttachInfo.setAttachResource(sinAttInfo.getAttachResource());
				tenAttachInfo = attachToTenAttach(sinAttInfo
						.getAttachResource());
				tenAttColl.add(tenAttachInfo);
			}
			for (int i = 0; i < sinCusColl.size(); i++) {
				TenancyCustomerEntryInfo tenCusInfo = new TenancyCustomerEntryInfo();
				SinCustomerEntrysInfo sinCusInfo = sinCusColl.get(i);
				tenCusInfo.setFdcCustomer(sinCusInfo.getFdcCustomer());
				tenCusInfo.setPropertyPercent(sinCusInfo.getPropertyPercent());
				tenCusColl.add(tenCusInfo);
			}
		}

		// ȡ��������,���Ϊ��,Ĭ��������
		TenancyContractTypeEnum contractType = (TenancyContractTypeEnum) this
				.getUIContext().get(KEY_TENANCY_CONTRACT_TYPE);
		if (contractType == null) {
			contractType = TenancyContractTypeEnum.NewTenancy;
		}
		tenancyBill.setTenancyType(contractType);

		tenancyBill.setFlagAtTerm(FlagAtTermEnum.Unknown);
		UserInfo user = SysContext.getSysContext().getCurrentUserInfo();
		tenancyBill.setTenancyAdviser(user);
		// Ĭ��������Ϊ����ǰ5��
		tenancyBill.setChargeDateType(ChargeDateTypeEnum.BeforeStartDate);
		tenancyBill.setChargeOffsetDays(0);

		tenancyBill.setStartDate(new Date());
		tenancyBill.setLeaseTime(1);
		tenancyBill.setTenancyDate(new Date());
		tenancyBill.setIsFreeDaysInLease(true);
		tenancyBill.setFirstLeaseType(FirstLeaseTypeEnum.WholeFirstLease);

		tenancyBill.setRentCountType(RentCountTypeEnum.ActDateCount);
		tenancyBill.setRentStartType(RentStartTypeEnum.SettledStartDate);

		tenancyBill.setCreator(user);
		tenancyBill.setCU(SysContext.getSysContext().getCurrentCtrlUnit());
		tenancyBill.setOrgUnit(SysContext.getSysContext().getCurrentOrgUnit()
				.castToFullOrgUnitInfo());
		tenancyBill.setBookedDate(new Date());
		tenancyBill.setCreateTime(new Timestamp(new Date().getTime()));
		// ����Ӧ������
		tenancyBill.setFristRevDate(new Date());
		tenancyBill.setMoreRoomsType(MoreRoomsTypeEnum.RoomRentSetting);
		tenancyBill.setIsFreeContract(false);
		
		tenancyBill.setContractType(ContractTypeEnum.FFZ);
		// tenancyBill.setRentCountType(item);
		// tenancyBill.setRentStartType(item);

		// ���ԭ��ͬID
		String oldTenancyBillId = (String) this.getUIContext().get(
				KEY_OLD_TENANCY_BILL_ID);
		if (TenancyContractTypeEnum.RejiggerTenancy.equals(contractType)
				|| TenancyContractTypeEnum.ContinueTenancy.equals(contractType)
				|| TenancyContractTypeEnum.ChangeName.equals(contractType)
				|| TenancyContractTypeEnum.PriceChange.equals(contractType)) {
			setTenancyOfContinueOrChange(tenancyBill, contractType,
					oldTenancyBillId);
		}

		// ȡ��������Ŀ by renliang at 2010-6-29
		sellProjectInfo = getSellProjectFormUIContext();
		if (sellProjectInfo != null) {
			tenancyBill.setSellProject(sellProjectInfo);
		}
		// add by yangfan
		if (TenancyContractTypeEnum.NewTenancy.equals(contractType)) {
			try {
				MoneyDefineCollection mdcollection = MoneyDefineFactory
						.getRemoteInstance().getMoneyDefineCollection(
								"select * where sysType='"
										+ MoneySysTypeEnum.TENANCYSYS_VALUE
										+ "' order by number");
				for (int i = 0; i < mdcollection.size(); i++) {
					TenLiquidatedInfo info = new TenLiquidatedInfo();
					info.setMoneyDefine(mdcollection.get(i));
					tenancyBill.getTenLiquidated().add(info);
				}
			} catch (BOSException e) {
				e.printStackTrace();
			}

			tenancyBill.setRateDate(DateEnum.DAY);
			tenancyBill.setReliefDate(DateEnum.DAY);
			tenancyBill.setStandardDate(DateEnum.DAY);
			tenancyBill.setOccurred(OccurreStateEnum.PRIOR);
			tenancyBill.setBit(MoneyEnum.YUAN);
		}
		return tenancyBill;
	}

	/**
	 * ȡ��������Ŀ
	 * 
	 * @author liang_ren969
	 * @date 2010-6-29
	 * @return SellProjectInfo
	 */
	public SellProjectInfo getSellProjectFormUIContext() {
		if (getUIContext().get("sellProject") != null) {
			sellProjectInfo = (SellProjectInfo) getUIContext().get(
					"sellProject");
		}
		return sellProjectInfo;
	}

	protected void f7SincerObligate_dataChanged(DataChangeEvent e)
			throws Exception {
		SincerObligateInfo sincerInfo = (SincerObligateInfo) this.f7SincerObligate
				.getValue();
		if (sincerInfo == null) {
			this.tblRoom.removeRows();
			this.tblCustomer.removeRows();
		} else {
			sincerInfo = TenancyHelper.getSincerObligateInfo(sincerInfo.getId()
					.toString());
			SinObligateRoomsEntryCollection sincerColl = sincerInfo
					.getSinRoomList();
			this.tblRoom.removeRows();
			tabMiddle.removeAll();
			// modified by weiqiang_chen PBG053457
			// Map roomMap= new HashMap();
			// for(int j = 0,n = tblRoom.getRowCount(); j < n;j ++){
			// IRow row = tblRoom.getRow(j);
			// TenancyRoomEntryInfo info = (TenancyRoomEntryInfo)
			// row.getUserObject();
			// if(info != null && info.getRoom() != null){
			// roomMap.put(info.getRoom().getId().toString(), new Integer(j));
			// }
			// }
			for (int i = 0; i < sincerColl.size(); i++) {
				TenancyRoomEntryInfo tenInfo = new TenancyRoomEntryInfo();
				SinObligateRoomsEntryInfo sinRoomEntryInfo = sincerColl.get(i);
				tenInfo.setRoom(sinRoomEntryInfo.getRoom());
				// if(sinRoomEntryInfo.getRoom() != null &&
				//roomMap.containsKey(sinRoomEntryInfo.getRoom().getId().toString
				// ())){
				// continue;
				// }
				tenInfo = roomToTenRoomEntry(sinRoomEntryInfo.getRoom());
				addRoomRow(tenInfo);
			}
			// modified by weiqiang_chen PBG053457
			// Map custmap = new HashMap();
			// for(int j = 0,n = tblCustomer.getRowCount();j < n;j++){
			// IRow row = tblCustomer.getRow(j);
			// TenancyCustomerEntryInfo info = (TenancyCustomerEntryInfo)
			// row.getUserObject();
			// if(info != null && info.getFdcCustomer() != null){
			// custmap.put(info.getFdcCustomer().getId().toString(), new
			// Integer(j));
			// }
			// }
			SinCustomerEntrysCollection sinCusColl = sincerInfo
					.getSinCustomerList();
			TenancyCustomerEntryCollection tenCusColl = new TenancyCustomerEntryCollection();
			for (int i = 0; i < sinCusColl.size(); i++) {
				TenancyCustomerEntryInfo tenCusInfo = new TenancyCustomerEntryInfo();
				SinCustomerEntrysInfo sinCusInfo = sinCusColl.get(i);
				tenCusInfo.setFdcCustomer(sinCusInfo.getFdcCustomer());
				// if(sinCusInfo.getFdcCustomer() != null &&
				// custmap.containsKey(sinCusInfo.getFdcCustomer().getId().
				// toString())){
				// continue;
				// }
				tenCusInfo.setPropertyPercent(sinCusInfo.getPropertyPercent());
				tenCusColl.add(tenCusInfo);
			}
			loadCustomers(tenCusColl);
		}
	}

	/*
	 * ��������Ҫ�󣬸������ֶν��й��ˣ�ֻ��ʾ��Ԥ��״̬�ļ�¼ �޸��ˣ��Թ��� �޸����ڣ�2010-04-20
	 */
	private void setSincerFilter() {
		SellProjectInfo projectInfo = (SellProjectInfo) this.getUIContext()
				.get("sellProject");
		EntityViewInfo viewInfo = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		if (projectInfo != null) {
			filter.getFilterItems().add(
					new FilterItemInfo("sellProject.id", projectInfo.getId()
							.toString()));
			filter.getFilterItems()
					.add(
							new FilterItemInfo("bizState",
									BizStateEnum.AUDITTED_VALUE));
			filter.getFilterItems().add(
					new FilterItemInfo("bizState",
							BizStateEnum.SINCEROBLIGATED_VALUE));
			// filter.setMaskString("#0 and (#1 or #2)");
			filter.setMaskString("#0 and #2)");
		} else {
			filter.getFilterItems().add(
					new FilterItemInfo("sellProject.id", null));
		}
		viewInfo.setFilter(filter);
		f7SincerObligate.setEntityViewInfo(viewInfo);
	}

	/**
	 * ����Ǹ������������߸���,��ԭ��ͬ�������Ϣ�����º�ͬ
	 * */
	private void setTenancyOfContinueOrChange(TenancyBillInfo tenancyBill,
			TenancyContractTypeEnum contractType, String oldTenancyBillId) {
		if (oldTenancyBillId != null) {
			SelectorItemCollection sels = this.getSelectors();
			try {
				// oldTenancyBill =
				// TenancyBillFactory.getRemoteInstance().getTenancyBillInfo(new
				// ObjectUuidPK(oldTenancyBillId), sels);
				oldTenancyBill = TenancyHelper
						.getTenancyBillInfo(oldTenancyBillId);
			} catch (EASBizException e) {
				handleException(e);
			} catch (BOSException e) {
				handleException(e);
			}
		}

		if (oldTenancyBill == null) {
			MsgBox.showInfo("����������û��ԭ��ͬ.");
			this.abort();
		}

		tenancyBill.setOldTenancyBill(oldTenancyBill);
		tenancyBill.setSellProject(oldTenancyBill.getSellProject());

		// ��ԭ��ͬ�Ŀͻ���Ϣ�����º�ͬ
		TenancyCustomerEntryCollection tenCustomers = oldTenancyBill
				.getTenCustomerList();
		for (int i = 0; i < tenCustomers.size(); i++) {
			TenancyCustomerEntryInfo tenCustomer = (TenancyCustomerEntryInfo) tenCustomers
					.get(i).clone();
			tenCustomer.setId(null);// ���������ͬ��Ҫ�������޿ͻ���¼,����IDΪnull
			tenancyBill.getTenCustomerList().add(tenCustomer);
		}
		tenancyBill.setTenCustomerDes(oldTenancyBill.getTenCustomerDes());

		// ��ԭ��ͬ�ĵ�����Ϣ�����º�ͬ
		IncreasedRentEntryCollection increaseds = oldTenancyBill
				.getIncreasedRents();
		for (int i = 0; i < increaseds.size(); i++) {
			IncreasedRentEntryInfo increased = increaseds.get(i);
			increased.setId(null);
			tenancyBill.getIncreasedRents().add(increased);
		}

		// ��ԭ��ͬ��������Ϣ�����º�ͬ
		RentFreeEntryCollection frees = oldTenancyBill.getRentFrees();
		for (int i = 0; i < frees.size(); i++) {
			RentFreeEntryInfo free = frees.get(i);
			free.setId(null);
			tenancyBill.getRentFrees().add(free);
		}

		// ��ԭ��ͬ�����޷�������º�ͬ
		TenancyRoomEntryCollection tenRooms = oldTenancyBill
				.getTenancyRoomList();
		for (int i = 0; i < tenRooms.size(); i++) {
			TenancyRoomEntryInfo tenRoom = (TenancyRoomEntryInfo) tenRooms.get(
					i).clone();
			// tenRoom.getRoomPayList().clear();// �������ͬ,�������޷����¼��¼
			// tenRoom.getDealAmounts().clear();
			tenRoom.setId(null);
			tenRoom.setFlagAtTerm(FlagAtTermEnum.Unknown);
			tenRoom.setHandleState(HandleStateEnum.NoHandleRoom);
			tenRoom.setStartDate(null);
			tenRoom.setEndDate(null);
			tenRoom.setActDeliveryRoomDate(null);
			tenRoom.setActQuitTenDate(null);
			tenRoom.setRemainDepositAmount(null);
			tenRoom.setRoomTotalRent(null);
			if (TenancyContractTypeEnum.PriceChange.equals(contractType)) {
				DealAmountEntryCollection DealAmounts = tenRoom
						.getDealAmounts();
				for (int k = 0; k < DealAmounts.size(); k++) {
					DealAmountEntryInfo dealAmountEntryInfo = DealAmounts
							.get(k);
					dealAmountEntryInfo.setId(null);
					if (MoneyTypeEnum.RentAmount.equals(dealAmountEntryInfo
							.getMoneyDefine().getMoneyType())) {
						// �����������Ϊ�� �ڼ���������������ɽ���¼�����ʱ ȡ��ʱ�����¼�ϵĳɽ����
						tenRoom.setDealRent(dealAmountEntryInfo.getAmount());
					}
				}
				TenancyRoomPayListEntryCollection tenancyRoomPayLists = tenRoom
						.getRoomPayList();
				for (int p = 0; p < tenancyRoomPayLists.size(); p++) {
					TenancyRoomPayListEntryInfo roomPayInfo = tenancyRoomPayLists
							.get(p);
					roomPayInfo.setId(null);
				}
			} else {
				tenRoom.getDealAmounts().clear();
				tenRoom.getRoomPayList().clear();
			}

			// ����ҵ������Ե�ID���
			TenancyRoomChargeEntryCollection tenRoomCharges = tenRoom
					.getTenRoomCharges();
			for (int j = 0; j < tenRoomCharges.size(); j++) {
				TenancyRoomChargeEntryInfo tenRoomCharge = tenRoomCharges
						.get(j);
				tenRoomCharge.setId(null);
			}
			EquipmentEntryCollection eqs = tenRoom.getEquipments();
			for (int j = 0; j < eqs.size(); j++) {
				EquipmentEntryInfo eq = eqs.get(j);
				eq.setId(null);
			}

			tenancyBill.getTenancyRoomList().add(tenRoom);
		}
		tenancyBill.setTenRoomsDes(oldTenancyBill.getTenRoomsDes());

		// ��ԭ��ͬ������������Դ�����º�ͬ
		TenAttachResourceEntryCollection tenAttaches = oldTenancyBill
				.getTenAttachResourceList();
		for (int i = 0; i < tenAttaches.size(); i++) {
			TenAttachResourceEntryInfo tenAttach = (TenAttachResourceEntryInfo) tenAttaches
					.get(i).clone();
			tenAttach.getAttachResPayList().clear();
			tenAttach.getDealAmounts().clear();
			tenAttach.setId(null);
			tenAttach.setFlagAtTerm(FlagAtTermEnum.Unknown);
			tenAttach.setHandleState(HandleStateEnum.NoHandleRoom);
			tenAttach.setStartDate(null);
			tenAttach.setEndDate(null);
			tenAttach.setActDeliveryAttachResDate(null);
			tenAttach.setActQuitTenDate(null);
			tenAttach.setAttachTotalRent(null);
			tenancyBill.getTenAttachResourceList().add(tenAttach);
		}

		// add by yangfan
		TenLiquidatedCollection tenLiquidated = oldTenancyBill
				.getTenLiquidated();
		for (int i = 0; i < tenLiquidated.size(); i++) {
			TenLiquidatedInfo tenLiqInfo = (TenLiquidatedInfo) tenLiquidated
					.get(i).clone();
			tenLiqInfo.setId(null);
			tenancyBill.getTenLiquidated().add(tenLiqInfo);
		}
		tenancyBill.setRate(oldTenancyBill.getRate());
		tenancyBill.setIsAccountLiquidated(oldTenancyBill
				.isIsAccountLiquidated());
		tenancyBill.setIsMDLiquidated(oldTenancyBill.isIsMDLiquidated());
		tenancyBill.setRateDate(oldTenancyBill.getRateDate());
		tenancyBill.setRelief(oldTenancyBill.getRelief());
		tenancyBill.setReliefDate(oldTenancyBill.getReliefDate());
		tenancyBill.setStandard(oldTenancyBill.getStandard());
		tenancyBill.setStandardDate(oldTenancyBill.getStandardDate());
		tenancyBill.setBit(oldTenancyBill.getBit());
		tenancyBill.setOccurred(oldTenancyBill.getOccurred());
		tenancyBill.setFirstLeaseEndDate(oldTenancyBill.getFirstLeaseEndDate());

		tenancyBill.setTenAttachesDes(oldTenancyBill.getTenAttachesDes());

		tenancyBill.setFirstLeaseType(oldTenancyBill.getFirstLeaseType());

		tenancyBill.setAgency(oldTenancyBill.getAgency());
		tenancyBill.setLeaseTime(oldTenancyBill.getLeaseTime());
		tenancyBill.setChargeDateType(oldTenancyBill.getChargeDateType());
		tenancyBill.setChargeOffsetDays(oldTenancyBill.getChargeOffsetDays());
		tenancyBill.setPayeeBank(oldTenancyBill.getPayeeBank());
		tenancyBill.setIsFreeContract(oldTenancyBill.isIsFreeContract());
		tenancyBill.setFristRevDate(oldTenancyBill.getFristRevDate());
		tenancyBill.setOrgUnit(oldTenancyBill.getOrgUnit());
		tenancyBill.setSecondRevDate(oldTenancyBill.getSecondRevDate());

		// �������ʼ����һ����ԭ��ͬ�Ľ������ڵĺ�1��
		if (TenancyContractTypeEnum.ContinueTenancy.equals(contractType)) {
			tenancyBill.setStartDate(TenancyHelper.addCalendar(oldTenancyBill
					.getEndDate(), Calendar.DATE, 1));
		} else if (TenancyContractTypeEnum.ChangeName.equals(contractType)
				|| TenancyContractTypeEnum.PriceChange.equals(contractType)) {
			tenancyBill.setStartDate(oldTenancyBill.getStartDate());
			tenancyBill.setEndDate(oldTenancyBill.getEndDate());
		}

	}

	private void viftyOtherPayList() {
		if (tblOtherPayList.getRowCount() > 0) {
			for (int i = 0; i < tblOtherPayList.getRowCount(); i++) {
				IRow row = this.tblOtherPayList.getRow(i);
				if (row.getCell("moneyTypeName").getValue() == null) {
					MsgBox.showInfo("����Ӧ�յ�" + (i + 1) + "�п������Ʋ���Ϊ��!");
					this.abort();
				}
				if (row.getCell("appDate").getValue() == null) {
					MsgBox.showInfo("����Ӧ�յ�" + (i + 1) + "��Ӧ�����ڲ���Ϊ��!");
					this.abort();
				}
				if (row.getCell("appAmount").getValue() == null
						|| ((BigDecimal) row.getCell("appAmount").getValue())
								.compareTo(new BigDecimal(0)) == 0) {
					MsgBox.showInfo("����Ӧ�յ�" + (i + 1) + "��Ӧ�ս���Ϊ�ջ�����!");
					this.abort();
				}
			}
		}

	}

	private void checkPayList() {
		if(tblPayList.getRowCount()==0){
			MsgBox.showInfo("�տ���ϸ����Ϊ��");
			this.abort();
		}
		IRow row = tblPayList.getRow(0);
		Date startDate = (Date) row.getCell(C_PAYS_START_DATE).getValue();
		startDate = FDCDateHelper.getDayBegin(startDate);
		Date conStratDate = (Date) this.pkStartDate.getValue();
		conStratDate = FDCDateHelper.getDayBegin(conStratDate);
		if (startDate.compareTo(conStratDate) != 0) {
			MsgBox.showInfo("�տ���ϸ��һ����ʼ���ںͺ�ͬ��ʼ���ڱ���һ��");
			this.abort();
		}

		IRow row2 = tblPayList.getRow(tblPayList.getRowCount() - 1);
		Date endDate = (Date) row2.getCell(C_PAYS_END_DATE).getValue();
		endDate = FDCDateHelper.getDayBegin(endDate);
		Date conEndDate = (Date) this.pkEndDate.getValue();
		conEndDate = FDCDateHelper.getDayBegin(conEndDate);
		if (endDate.compareTo(conEndDate) != 0) {
			MsgBox.showInfo("�տ���ϸ����н������ںͺ�ͬ�������ڱ���һ��");
			this.abort();
		}

		//
		List payList = new ArrayList();
		if (tblPayList.getRow(1) == null)
			return;
		ITenancyPayListInfo tenPayInfo = (ITenancyPayListInfo) tblPayList
				.getRow(1).getUserObject();
		int seqq = tenPayInfo.getLeaseSeq();
		payList.add(tenPayInfo);
		for (int i = 0; i < tblPayList.getRowCount(); i++) {
			IRow row4 = tblPayList.getRow(i);
			if (row4.getUserObject() != null) {
				ITenancyPayListInfo tenPayInfo2 = (ITenancyPayListInfo) row4
						.getUserObject();
				if (tenPayInfo2.getLeaseSeq() > seqq) {
					payList.add(tenPayInfo2);
					seqq++;
				}
			}
		}
		ITenancyPayListInfo payInfo = (ITenancyPayListInfo) payList.get(0);
		int seq = payInfo.getLeaseSeq();
		for (int i = 0; i < payList.size(); i++) {
			ITenancyPayListInfo payInfo2 = (ITenancyPayListInfo) payList.get(i);
			int seq2 = payInfo2.getLeaseSeq();
			if (seq2 > seq) {
				Date stratDate = payInfo2.getStartDate();
				stratDate = FDCDateHelper.getDayBegin(stratDate);
				Date endDate2 = payInfo.getEndDate();
				Date nextDate = SHEHelper.getNextDay(endDate2);
				if (nextDate.compareTo(stratDate) != 0) {
					MsgBox.showInfo("��" + seq + "��" + seq2 + "����ʼ����ʱ����β��������");
					this.abort();
				} else {
					payInfo = payInfo2;
					seq = seq2;
				}
			}
		}
		// vifyDisAmount();
		vifyPayListAmount();
	}

	private void vifyPayListAmount() {
		boolean boo = false;
		for (int i = 0; i < tblPayList.getRowCount(); i++) {
			IRow row = tblPayList.getRow(i);
			if (row.getUserObject() != null) {
				for (int j = 0; j < getAppPayColKeys().size(); j++) {
					String key = (String) getAppPayColKeys().get(j);
					ICell cell = row.getCell(key);
					BigDecimal appAmount = (BigDecimal) cell.getValue();
					if (appAmount == null
							|| appAmount.compareTo(new BigDecimal(0)) == 0) {
						Object obj = row.getCell(C_PAYS_MONEY_DEFINE)
								.getValue();
						if (obj instanceof String) {
							String des = ((String) obj).trim();
							if ("���".equals(des)) {
								boo = true;
								// MsgBox.showInfo("������ϸ�����Ӧ������Ϊ��!");
								// this.abort();
							}
						} else if (obj instanceof MoneyDefineInfo) {
							MoneyDefineInfo money = (MoneyDefineInfo) row
									.getCell(C_PAYS_MONEY_DEFINE).getValue();
							if (MoneyTypeEnum.RentAmount.equals(money
									.getMoneyType())) {
								boo = true;
								// MsgBox.showInfo("������ϸ�����Ӧ������Ϊ��!");
								// this.abort();
							}
						}
					}
				}
			}
		}
		if (boo) {
			int a = MsgBox.showConfirm2New(null, "�տ���ϸ�����Ϊ����У�ȷ���ύ��?");
			if (JOptionPane.NO_OPTION == a) {
				this.abort();
			}
		}
	}

	// У�鸶����ϸ��Ѻ���Ƿ��������õ�Ѻ��һ��
	private void vifyDisAmount() {
		BigDecimal sumdisAmount = new BigDecimal(0);
		for (int i = 0; i < tblPayList.getRowCount(); i++) {
			IRow payListRow = tblPayList.getRow(i);
			if (payListRow.getUserObject() != null) {
				// TenancyRoomEntryInfo tenEntry = (TenancyRoomEntryInfo)
				// row.getUserObject();
				// ����������Ѻ����������Ͷ�����MAP�
				for (int j = 0; j < getAppPayColKeys().size(); j++) {
					String key = (String) getAppPayColKeys().get(j);
					ICell cell = payListRow.getCell(key);
					BigDecimal appAmount = (BigDecimal) cell.getValue();
					appAmount = appAmount == null ? new BigDecimal(0)
							: appAmount;
					Object obj = payListRow.getCell(C_PAYS_MONEY_DEFINE)
							.getValue();
					String moneyName = "";
					if (obj instanceof String) {
						moneyName = ((String) obj).trim();
					} else if (obj instanceof MoneyDefineInfo) {
						MoneyDefineInfo money = (MoneyDefineInfo) obj;
						moneyName = money.getName();
					}
					TenancyRoomPayListEntryInfo tenPayInfo = (TenancyRoomPayListEntryInfo) payListRow
							.getCell(key).getUserObject();
					if (tenPayInfo != null && tenPayInfo.getTenRoom() != null
							&& tenPayInfo.getTenRoom().getRoom() != null) {
						if (roomDisMap.get(tenPayInfo.getTenRoom().getRoom()
								.getId().toString()) != null) {
							Map map = (Map) roomDisMap.get(tenPayInfo
									.getTenRoom().getRoom().getId().toString());
							for (Iterator itor = map.keySet().iterator(); itor
									.hasNext();) {
								MoneyDefineInfo moneyInfo = (MoneyDefineInfo) itor
										.next();
								BigDecimal disAmount = (BigDecimal) map
										.get(moneyInfo);
								disAmount = disAmount == null ? new BigDecimal(
										0) : disAmount;
								if (moneyInfo.getName().equals(moneyName)) {
									if (disAmount.compareTo(appAmount) != 0) {
										try {
											RoomInfo roomInfo = RoomFactory
													.getRemoteInstance()
													.getRoomInfo(
															new ObjectUuidPK(
																	tenPayInfo
																			.getTenRoom()
																			.getRoom()
																			.getId()
																			.toString()));
											MsgBox.showInfo("����"
													+ roomInfo.getName() + "��"
													+ moneyInfo.getName()
													+ "���ú��տ���ϸ��Ѻ��һ��");
											this.abort();
										} catch (EASBizException e) {
											e.printStackTrace();
										} catch (BOSException e) {
											e.printStackTrace();
										}
									} else {
										sumdisAmount = sumdisAmount
												.add(disAmount);
									}
								}
							}
						}
					}
				}
			}
		}
		this.txtDepositAmount.setValue(sumdisAmount);
	}

	public void actionUnAudit_actionPerformed(ActionEvent e) throws Exception {
		String id = this.editData.getId().toString();
		TenancyBillInfo tenBill = TenancyBillFactory.getRemoteInstance()
				.getTenancyBillInfo(new ObjectUuidPK(id));
		TenancyContractTypeEnum tenType = tenBill.getTenancyType();

		if (OtherBillFactory.getRemoteInstance().exists(
				"select id from where tenancyBill.id='" + id + "'")) {
			MsgBox.showInfo(this, "����������ͬ����ֹ������������");
			this.abort();
		}
		if (DepositDealBillFactory.getRemoteInstance().exists(
				"select id from where tenancyBill.id='" + id + "'")) {
			MsgBox.showInfo(this, "����Ѻ�������뵥����ֹ������������");
			this.abort();
		}
		if (!tenType.equals(TenancyContractTypeEnum.NewTenancy)) {
			MsgBox.showInfo(this, "ֻ�������ͬ������������");
			this.abort();
		}

		if (!TenancyBillStateEnum.Audited.equals(tenBill.getTenancyState())) {
			MsgBox.showInfo(this, "ֻ������״̬�ĺ�ͬ������������");
			this.abort();
		}

		boolean isjiaojie = false;
		TenancyRoomEntryCollection list = tenBill.getTenancyRoomList();
		for (int i = 0; i < list.size(); i++) {
			TenancyRoomEntryInfo entry = list.get(i);
			if (!entry.getHandleState().equals(HandleStateEnum.NoHandleRoom)) {
				isjiaojie = true;
				break;
			}
		}
		FilterInfo info = new FilterInfo();
		info.appendFilterItem("tenancyObj.id", tenBill.getId());
		EntityViewInfo view = new EntityViewInfo();
		view.setFilter(info);
		FDCReceivingBillCollection c = FDCReceivingBillFactory
				.getRemoteInstance().getFDCReceivingBillCollection(view);
		if (isjiaojie = false || c.size() == 0) {
			TenancyBillFactory.getRemoteInstance().antiAudit(tenBill.getId());
			MsgBox.showInfo(this, "�������ɹ���");
		} else {
			MsgBox.showInfo(this, "ֻ��δ���ӷ��䡢δ�տ�������ͬ������������");
		}
	}

	public void actionAudit_actionPerformed(ActionEvent e) throws Exception {
		// super.actionAudit_actionPerformed(e);
		if (this.editData.getId() == null) {
			MsgBox.showInfo(this, "�����ύ����");
			this.abort();
		}
		String id = this.editData.getId().toString();
		TenancyBillInfo tenBill = TenancyBillFactory.getRemoteInstance()
				.getTenancyBillInfo(new ObjectUuidPK(id));
		TenancyBillStateEnum tenState = tenBill.getTenancyState();
		if (!TenancyBillStateEnum.Submitted.equals(tenState)) {
			if (TenancyBillStateEnum.Audited.equals(tenState)) {
				MsgBox.showInfo(this, "��ͬ�Ѿ�������");
				this.abort();
			} else {
				MsgBox.showInfo(this, "ֻ�����ύ�ĺ�ͬ����������");
				this.abort();
			}

		}
		TenancyBillFactory.getRemoteInstance().audit(BOSUuid.read(id));
		MsgBox.showInfo(this, "�����ɹ���");
	}

	private String validateExistByName(String name) throws BOSException,
			SQLException {
		StringBuffer str = new StringBuffer();
		// StringBuffer resultStr = new StringBuffer(
		// "<html><table border='1' cellspacing='0' cellpadding='0'><tr>�ͻ�<th></th>��˾<th></th><th>��Ŀ</th><th>��ͬ���</th><th>��ʼ����</th><th>��������</th></tr>"
		// );
		//
		String sql = "select c.fname_l2 name ,org.fname_l2 companyName,sp.fname_l2 project,b.fnumber contractBill,to_char(b.fstartdate,'yyyy-mm-dd') startDate,to_char(b.fenddate "
				+ " ,'yyyy-mm-dd') endDate "
				+ " from t_ten_tenancycustomerentry e"
				+ " inner join t_ten_tenancybill b on e.ftenancybillid = b.fid and b.ftenancystate = 'Executing'  "
				+ " left outer join t_she_fdccustomer c on e.ffdccustomerid = c.fid "
				+ " left outer join t_she_sellproject sp on sp.fid = b.fsellprojectid "
				+ " left outer join t_org_baseunit org on org.fid = sp.forgunitid "
				+ " where c.fname_l2 in (" + name + ")";
		if (editData.getId() != null) {
			sql += " and b.fid <>'" + editData.getId().toString() + "'";
		}
		FDCSQLBuilder builder = new FDCSQLBuilder();
		builder.appendSql(sql);
		IRowSet rs = builder.executeQuery();
		Map<String, String> errorString = new HashMap<String, String>();
		String key = null;
		String errorMsg = null;
		while (rs.next()) {
			key = rs.getString("name");
			errorMsg = " ��" + rs.getString("companyName") + "��"
					+ rs.getString("project") + "��" + key + "�����޺�ͬ����ͬ���Ϊ��"
					+ rs.getString("contractBill") + ",��ʼʱ��Ϊ��"
					+ rs.getString("startDate") + ",����ʱ��Ϊ��"
					+ rs.getString("endDate");
			//resultStr.append("<tr><td>").append(key).append("</td><td>").append
			// (rs.getString("companyName")).append("</td><td>").append(rs.
			// getString("project")).append("</td><td>")
			//.append(rs.getString("contractBill")).append("</td><td>").append(rs
			// .getString("startDate")).append("</td><td>").append(rs.getString(
			// "endDate")).append("</td></tr>");
			if (errorString.containsKey(key)) {
				errorString.put(key, errorString.get(key) + "\n" + errorMsg);
			} else {
				errorString.put(key, errorMsg);
			}
		}

		for (Iterator<String> it = errorString.keySet().iterator(); it
				.hasNext();) {
			key = it.next();
			str.append(errorString.get(key));
		}
		// resultStr.append("</table></html>");
		return str.toString();
	}

	public void actionSubmit_actionPerformed(ActionEvent e) throws Exception {

		String oldOprt = this.oprtState;
		super.actionSubmit_actionPerformed(e);
		isFreeConSet(this.chkIsFreeContract.isSelected());
		List list = new ArrayList();
		StringBuffer cnames = new StringBuffer();
		int c = this.tblCustomer.getRowCount();
		for (int i = 0; i < c; i++) {
			IRow row = this.tblCustomer.getRow(i);
			FDCCustomerInfo fdcCust = (FDCCustomerInfo) row.getCell("customer")
					.getValue();
			if (fdcCust != null)
				list.add(fdcCust);
			cnames.append("'").append(fdcCust.getName()).append("'");
			if (i != (c - 1)) {
				cnames.append(",");
			}
		}
		// �̻�����ҵ�񵥾ݰ���,���ⵥ �����ύ�������̻�����
		if (oldOprt.equals(OprtState.ADDNEW)) {
			this.getUIContext().remove(KEY_ROOMS);
			this.getUIContext().remove(KEY_OLD_TENANCY_BILL_ID);
			this.getUIContext().remove(KEY_TENANCY_CONTRACT_TYPE);

			CommerceHelper.addCommerceTrackRecord(this, list,
					(UserInfo) this.f7TenancyAdviser.getValue(),
					TrackRecordEnum.TenancyApp, this.editData.getNumber(),
					this.editData.getId() == null ? null : this.editData
							.getId().toString());
		}

		// У�鵱ǰ�ͻ���û���ڴ���������ִ���еĺ�ͬ����
		if (cnames != null && cnames.length() > 0) {
			String result = validateExistByName(cnames.toString());
			if (!StringUtils.isEmpty(result)) {
				FDCMsgBox.showDetailAndOK(this, "��ܰ��ʾ����ͬ�ͻ����ƺ�ͬ��ʾ������ʾ����ȷ������.",
						result.toString(), 3);
			}
		}
		// ��������
		// this.setOprtState(OprtState.VIEW);
		actionSubmit.setEnabled(true);
		actionEdit.setEnabled(false);
		this.actionAudit.setVisible(true);
		actionAudit.setEnabled(true);

		// add by yangfan
		initLiquidatedTable();
		
	}

	public void actionEdit_actionPerformed(ActionEvent e) throws Exception {
		super.actionEdit_actionPerformed(e);
		// by huanghefh
		reSetRentSetInfo(getTenRoomListFromView());
		updatePayListInfo();
		if (this.pkStartDate.getValue() != null
				&& this.pkEndDate.getValue() != null) {
			isFreeConSet(this.chkIsFreeContract.isSelected());
		}

		initControlByOprtState();
		initControlByBillState();
		this.btnAddIncrease.setEnabled(false);
		this.btnRmIncrease.setEnabled(false);
		this.btnAddFree.setEnabled(false);
		this.btnRmFree.setEnabled(false);
		/**
		 * ����鿴״̬�£�����޸ģ������޸Ŀ�ʼ�ͽ���ʱ��
		 * 
		 * @author liang_ren969
		 */
		if (FDCBillStateEnum.SUBMITTED.equals(this.editData.getState())
				&& this.oprtState.equals("EDIT")) {
			this.pkStartDate.setEditable(true);
			this.pkStartDate.setEnabled(true);
			this.pkEndDate.setEnabled(true);
			this.pkEndDate.setEditable(true);
		}
		isFreeConSet(this.chkIsFreeContract.isSelected());
	}

	public void actionAddNew_actionPerformed(ActionEvent e) throws Exception {

		/**
		 * ����������Ŀ
		 * 
		 * @author liang_ren969
		 * @date 2010-6-29
		 */
		// this.oprtState =OprtState.ADDNEW;
		if (editData.getSellProject() != null) {

			sellProjectInfo = this.editData.getSellProject();
		}

		this.editData.setSellProject(sellProjectInfo);

		this.getUIContext().clear();

		super.actionAddNew_actionPerformed(e);
		// ����״̬����action״̬
		initControlByOprtState();

		// add by yangfan
		initLiquidatedTable();

	}

	public SelectorItemCollection getSelectors() {
		SelectorItemCollection sels = super.getSelectors();
		sels.add("TenancyType");
		sels.add("tenancyState");
		sels.add("rentCountType");

		sels.add("isAutoToInteger");
		sels.add("toIntegerType");
		sels.add("digit");

		sels.add("isAutoToIntegerFee");
		sels.add("toIntegetTypeFee");
		sels.add("digitFee");
		sels.add("firstLeaseEndDate");

		sels.add("rentStartType");
		sels.add("startDateLimit");
		sels.add("daysPerYear");
		sels.add("moreRoomsType");
		sels.add("isFreeContract");
		sels.add("state");

		sels.add("fristRevDate");
		sels.add("orgUnit");
		sels.add("orgUnit.*");
		sels.add("secondRevDate");

		sels.add("increasedRents.*");
		sels.add("rentFrees.*");

		sels.add("tenancyRoomList.*");
		sels.add("tenancyRoomList.room.floor");
		sels.add("tenancyRoomList.room.isForPPM");
		sels.add("tenancyRoomList.room.number");
		sels.add("tenancyRoomList.room.name");

		sels.add("tenancyRoomList.room.building.name");
		sels.add("tenancyRoomList.room.building.number");
		sels.add("tenancyRoomList.room.roomModel.name");
		sels.add("tenancyRoomList.room.roomModel.number");
		sels.add("tenancyRoomList.room.buildingProperty.name");
		sels.add("tenancyRoomList.room.direction.number");
		sels.add("tenancyRoomList.room.direction.name");
		sels.add("tenancyRoomList.room.buildingProperty.number");
		sels.add("tenancyRoomList.room.building.sellProject.name");
		sels.add("tenancyRoomList.room.building.sellProject.number");
		sels.add("tenancyRoomList.roomPayList.*");
		sels.add("tenancyRoomList.roomPayList.currency.name");
		sels.add("tenancyRoomList.roomPayList.currency.number");

		sels.add("tenancyRoomList.roomPayList.moneyDefine.name");
		sels.add("tenancyRoomList.roomPayList.moneyDefine.number");
		sels.add("tenancyRoomList.roomPayList.moneyDefine.moneyType");
		sels.add("tenancyRoomList.roomPayList.moneyDefine.sysType");
		sels.add("tenancyRoomList.roomPayList.moneyDefine.isEnabled");

		sels.add("tenancyRoomList.dealAmounts.*");
		sels.add("tenancyRoomList.dealAmounts.moneyDefine.name");
		sels.add("tenancyRoomList.dealAmounts.moneyDefine.number");
		sels.add("tenancyRoomList.dealAmounts.moneyDefine.moneyType");
		sels.add("tenancyRoomList.dealAmounts.moneyDefine.sysType");
		sels.add("tenancyRoomList.dealAmounts.moneyDefine.isEnabled");

		sels.add("tenAttachResourceList.*");
		sels.add("tenAttachResourceList.attachResource.attachType");
		sels.add("tenAttachResourceList.attachResource.name");
		sels.add("tenAttachResourceList.attachResource.number");
		sels.add("tenAttachResourceList.attachResource.description");

		sels.add("tenAttachResourceList.attachResource.sellProject.name");
		sels.add("tenAttachResourceList.attachResource.subarea.name");
		sels.add("tenAttachResourceList.attachResource.building.name");
		sels.add("tenAttachResourceList.attachResPayList.*");
		sels.add("tenAttachResourceList.attachResPayList.currency.number");
		sels.add("tenAttachResourceList.attachResPayList.currency.name");

		sels.add("tenAttachResourceList.attachResPayList.moneyDefine.name");
		sels.add("tenAttachResourceList.attachResPayList.moneyDefine.number");
		sels
				.add("tenAttachResourceList.attachResPayList.moneyDefine.moneyType");
		sels.add("tenAttachResourceList.attachResPayList.moneyDefine.sysType");
		sels
				.add("tenAttachResourceList.attachResPayList.moneyDefine.isEnabled");

		sels.add("tenAttachResourceList.dealAmounts.*");
		sels.add("tenAttachResourceList.dealAmounts.moneyDefine.name");
		sels.add("tenAttachResourceList.dealAmounts.moneyDefine.number");
		sels.add("tenAttachResourceList.dealAmounts.moneyDefine.moneyType");
		sels.add("tenAttachResourceList.dealAmounts.moneyDefine.sysType");
		sels.add("tenAttachResourceList.dealAmounts.moneyDefine.isEnabled");

		sels.add("tenancyRoomList.equipments.*");
		sels.add("tenancyRoomList.equipments.dev.*");
		sels.add("tenancyRoomList.tenRoomCharges.*");
		sels.add("tenancyRoomList.tenRoomCharges.chargeStandard.*");
		sels.add("tenancyRoomList.tenRoomCharges.chargeItem.name");

		sels.add("tenCustomerList.*");
		sels.add("tenCustomerList.brandEntry.brand.id");
		sels.add("tenCustomerList.brandEntry.brand.name");
		sels.add("tenCustomerList.brandEntry.brand.number");
		sels.add("tenCustomerList.fdcCustomer.name");
		sels.add("tenCustomerList.fdcCustomer.number");
		sels.add("tenCustomerList.fdcCustomer.postalcode");
		sels.add("tenCustomerList.fdcCustomer.phone");
		sels.add("tenCustomerList.fdcCustomer.certificateName");
		sels.add("tenCustomerList.fdcCustomer.certificateNumber");
		sels.add("tenCustomerList.fdcCustomer.mailAddress");
		sels.add("tenCustomerList.fdcCustomer.description");

		sels.add("otherPayList.*");
		sels.add("otherPayList.moneyDefine.*");
		sels.add("otherPayList.currency.*");
		sels.add("otherPayList.otherBill.*");
		//��ȡ��׼�����������Ϣ
		sels.add("tenPriceEntry.*");
		sels.add("tenPriceEntry.tenPrice.id");
		sels.add("tenPriceEntry.tenPrice.sellProject");
		sels.add("tenPriceEntry.tenPrice.roomName");
		sels.add("tenPriceEntry.tenPrice.area");
		sels.add("tenPriceEntry.tenPrice.dayPrice");
		sels.add("tenPriceEntry.tenPrice.yearPrice");
		sels.add("tenPriceEntry.tenPrice.monthPrice");
		sels.add("tenPriceEntry.tenPrice.maxFreeDay");
		sels.add("tenPriceEntry.tenPrice.maxLease");
		sels.add("tenPriceEntry.tenPrice.deposit");
		sels.add("tenPriceEntry.tenPrice.description");
		

		// add by yangfan
		sels.add("tenLiquidated.*");
		sels.add("tenLiquidated.moneyDefine.*");
		sels.add("tenLiquidated.liquidated.*");
		sels.add("tenLiquidated.liquidated.name");
		sels.add("tenLiquidated.liquidated.number");
		sels.add("rate");
		sels.add("isAccountLiquidated");
		sels.add("isMDLiquidated");
		sels.add("rateDate");
		sels.add("relief");
		sels.add("reliefDate");
		sels.add("standard");
		sels.add("standardDate");
		sels.add("bit");
		sels.add("occurred");
		sels.add("depositAmount");
		
		return sels;
	}

	private KDTDefaultCellEditor createTxtCellEditor(int length,
			boolean editable) {
		return TenancyClientHelper.createTxtCellEditor(length, editable);
	}

	private KDTDefaultCellEditor createComboCellEditor(List enumList) {
		return TenancyClientHelper.createComboCellEditor(enumList);
	}

	private KDTDefaultCellEditor createComboCellEditorAndSelectOne(List enumList) {
		return TenancyClientHelper.createComboCellEditorAndSelectOne(enumList);
	}

	private ICellEditor createFormattedCellEditor() {
		return TenancyClientHelper.createFormattedCellEditor(true);
	}

	private ICellEditor createFormattedCellEditor(boolean editable) {
		return TenancyClientHelper.createFormattedCellEditor(editable);
	}

	protected KDTable getDetailTable() {
		return null;
	}

	protected void attachListeners() {
		this.addItemListener(this.comboChargeDateType);
		this.spinChargeOffsetDays
				.addChangeListener(spinChargeOffsetDaysChangeListener);
		this.spinLeaseTime.addChangeListener(spinLeaseTimeChangeListener);

		this.addDataChangeListener(this.pkStartDate);
		this.addDataChangeListener(this.pkEndDate);
		this.addDataChangeListener(this.prmtRentFreeBill);
	}

	protected void detachListeners() {
		this.removeItemListener(this.comboChargeDateType);
		this.spinChargeOffsetDays
				.removeChangeListener(spinChargeOffsetDaysChangeListener);
		this.spinLeaseTime.removeChangeListener(spinLeaseTimeChangeListener);

		// ע�⣺���ﲻ�ܽ�������ȫ��ȥ��,KDSpin�Ľ���ˢ��ͨ�����ڲ���һ��������ʵ�ֵ�
		// this.removeChangeListener(this.spinFreeDays);
		// this.removeChangeListener(this.spinChargeOffsetDays);
		// this.removeChangeListener(this.spinLeaseTime);

		// TODO ʵ��֤��,��2��������û�б�ȥ��,������
		this.removeDataChangeListener(this.pkStartDate);
		this.removeDataChangeListener(this.pkEndDate);
		this.removeDataChangeListener(this.prmtRentFreeBill);
	}

	protected void addActionListener(AbstractButton com) {
		EventListener[] listeners = (EventListener[]) listenersMap.get(com);
		if (listeners != null && listeners.length > 0) {
			for (int i = 0; i < listeners.length; i++) {
				com.addActionListener((ActionListener) listeners[i]);
			}
		}
	}

	protected void addItemListener(JComboBox com) {
		EventListener[] listeners = (EventListener[]) listenersMap.get(com);
		if (listeners != null && listeners.length > 0) {
			for (int i = 0; i < listeners.length; i++) {
				com.addItemListener((ItemListener) listeners[i]);
			}
		}
	}

	protected void addChangeListener(JSpinner com) {
		EventListener[] listeners = (EventListener[]) listenersMap.get(com);
		if (listeners != null && listeners.length > 0) {
			for (int i = 0; i < listeners.length; i++) {
				com.addChangeListener((ChangeListener) listeners[i]);
			}
		}
	}

	protected void removeActionListener(AbstractButton com) {
		EventListener[] listeners = com.getListeners(ActionListener.class);
		if (listeners == null) {
			return;
		}
		for (int i = 0; i < listeners.length; i++) {
			com.removeActionListener((ActionListener) listeners[i]);
		}
		if (listeners.length > 0) {
			listenersMap.put(com, listeners);
		}
	}

	protected void removeItemListener(JComboBox com) {
		EventListener[] listeners = com.getListeners(ItemListener.class);
		if (listeners == null) {
			return;
		}
		for (int i = 0; i < listeners.length; i++) {
			com.removeItemListener((ItemListener) listeners[i]);
		}
		if (listeners.length > 0) {
			listenersMap.put(com, listeners);
		}
	}

	protected void removeChangeListener(JSpinner com) {
		EventListener[] listeners = com.getListeners(ChangeListener.class);
		if (listeners == null) {
			return;
		}
		for (int i = 0; i < listeners.length; i++) {
			com.removeChangeListener((ChangeListener) listeners[i]);
		}
		if (listeners.length > 0) {
			listenersMap.put(com, listeners);
		}
	}

	protected KDTextField getNumberCtrl() {
		return this.txtNumber;
	}

	private MoneyDefineInfo getRentMoneyDefine() throws BOSException {
		if (rentMoneyName == null) {
			EntityViewInfo view = new EntityViewInfo();
			FilterInfo filter = new FilterInfo();
			filter.getFilterItems().add(
					new FilterItemInfo("moneyType", MoneyTypeEnum.RentAmount));
			filter.getFilterItems().add(
					new FilterItemInfo("sysType", MoneySysTypeEnum.TenancySys));
			view.setFilter(filter);
			MoneyDefineCollection moneyNames = MoneyDefineFactory
					.getRemoteInstance().getMoneyDefineCollection(view);
			if (!moneyNames.isEmpty()) {
				rentMoneyName = moneyNames.get(0);
			}
		}
		if (rentMoneyName == null) {
			MsgBox.showInfo(this, "���ȶ���������͵Ŀ������ͣ�");
			abort();
		}
		return rentMoneyName;
	}

	private void setIsbtnPayList(boolean isFreeContract) {
		this.btnInsert.setEnabled(isFreeContract);
		this.btnAddPayList.setEnabled(isFreeContract);
		this.btnDelPayList.setEnabled(isFreeContract);
		TenancyContractTypeEnum currentTenancyType = getCurrentTenancyType();
		if (!TenancyContractTypeEnum.ChangeName.equals(currentTenancyType)) {
			this.actionAddRoom.setEnabled(!isFreeContract);
			this.actionRemoveRoom.setEnabled(!isFreeContract);
		}

		for (int i = 0; i < tblIncrease.getRowCount(); i++) {
			IRow row = tblIncrease.getRow(i);
			row.getStyleAttributes().setLocked(isFreeContract);
		}

		for (int i = 0; i < tblFree.getRowCount(); i++) {
			IRow row = tblFree.getRow(i);
			row.getStyleAttributes().setLocked(isFreeContract);
		}
	}

	private void setToIntegerVisable(boolean isAutoToInteger) {
		this.contToIntegerType.setVisible(isAutoToInteger);
		this.contDigit.setVisible(isAutoToInteger);
	}

	private void setToIntegerFeeVisable(boolean isAutoToInteger) {
		this.contToIntegerType2.setVisible(isAutoToInteger);
		this.contDigit2.setVisible(isAutoToInteger);
	}

	protected void comboRentCountType_itemStateChanged(ItemEvent e)
			throws Exception {
		setVisibleAboutRentCountType((RentCountTypeEnum) this.comboRentCountType
				.getSelectedItem());
		updatePayListInfo();
	}

	protected void txtDaysPerYear_dataChanged(DataChangeEvent e)
			throws Exception {
		updatePayListInfo();
	}

	private void setVisibleAboutRentCountType(RentCountTypeEnum rentCountType) {
		if (RentCountTypeEnum.ActDateCount.equals(rentCountType)) {
			this.contDaysPerYear.setVisible(false);
		} else {
			this.contDaysPerYear.setVisible(true);
		}
	}

	protected void comboRentStartType_itemStateChanged(ItemEvent e)
			throws Exception {
		setVisibleAboutRentStartType((RentStartTypeEnum) this.comboRentStartType
				.getSelectedItem());
		updatePayListInfo();
	}

	private void setVisibleAboutRentStartType(RentStartTypeEnum rentStartType) {
		if (RentStartTypeEnum.SettledStartDate.equals(rentStartType)) {
			this.contStartDateLimit.setVisible(false);
			this.pkStartDate.setEnabled(true);
			this.pkStartDate.setValue(new Date());
			this.pkEndDate.setEnabled(true);
			this.pkStartDate.setRequired(true);
			this.pkEndDate.setRequired(true);
			this.pkEndDate.setValue(new Date());
			this.txtLeaseCount.setEnabled(false);
			// this.btnAddIncrease.setEnabled(true);
			// this.btnRmIncrease.setEnabled(true);
			this.btnAddFree.setEnabled(true);
			this.btnRmFree.setEnabled(true);
			this.comboFirstLeaseType.setEnabled(true);
		} else {
			this.btnAddIncrease.setEnabled(false);
			this.btnRmIncrease.setEnabled(false);
			this.btnAddFree.setEnabled(false);
			this.btnRmFree.setEnabled(false);
			this.comboFirstLeaseType
					.setSelectedItem(FirstLeaseTypeEnum.WholeFirstLease);
			this.comboFirstLeaseType.setEnabled(false);
			this.contStartDateLimit.setVisible(true);
			this.txtLeaseCount.setEnabled(true);
			if ("ADDNEW".equals(this.getOprtState())
					|| "EDIT".equals(this.getOprtState())) {
				this.btnAddIncrease.setEnabled(false);
				this.btnRmIncrease.setEnabled(false);
				this.btnAddFree.setEnabled(false);
				this.btnRmFree.setEnabled(false);
				this.comboFirstLeaseType
						.setSelectedItem(FirstLeaseTypeEnum.WholeFirstLease);
				this.comboFirstLeaseType.setEnabled(false);
				this.pkStartDate.setEnabled(false);
				this.pkStartDate.setValue(null);
				this.pkEndDate.setEnabled(false);
				this.pkEndDate.setValue(null);
				this.pkStartDate.setRequired(false);
				this.pkEndDate.setRequired(false);
			}
		}
	}

	protected void comboMoreRoomsType_itemStateChanged(ItemEvent e)
			throws Exception {
		reSetRentSetInfo(getTenRoomListFromView(),
				getTenAttachResListFromView());
	}

	protected void comboToIntegerType_itemStateChanged(ItemEvent e)
			throws Exception {
		updatePayListInfo();
	}

	protected void comboToIntegerTypeFee_itemStateChanged(ItemEvent e)
			throws Exception {
		updatePayListInfo();
	}

	protected void comboDigit_itemStateChanged(ItemEvent e) throws Exception {
		updatePayListInfo();
	}

	protected void comboDigitFee_itemStateChanged(ItemEvent e) throws Exception {
		updatePayListInfo();
	}

	protected void chkIsAutoToInteger_actionPerformed(ActionEvent e)
			throws Exception {
		setToIntegerVisable(this.chkIsAutoToInteger.isSelected());
		updatePayListInfo();
	}

	protected void chkIsAutoToIntegerForFee_actionPerformed(ActionEvent e)
			throws Exception {
		setToIntegerFeeVisable(this.chkIsAutoToIntegerForFee.isSelected());
		updatePayListInfo();
	}

	private void setOtherFreeContract(boolean isFreeContract) {
		this.contLeaseTime.setEnabled(isFreeContract);
		this.contFirstLeaseType.setEnabled(isFreeContract);
		this.contFirstLeaseEndDate.setEnabled(isFreeContract);
		this.contChargeDateType.setEnabled(isFreeContract);
		this.contChargeOffsetDays.setEnabled(isFreeContract);
		this.contRentStartType.setEnabled(isFreeContract);
		this.contStartDateLimit.setEnabled(isFreeContract);
		// this.btnAddIncrease.setEnabled(isFreeContract);
		// this.btnRmIncrease.setEnabled(isFreeContract);
		this.btnAddFree.setEnabled(isFreeContract);
		this.btnRmFree.setEnabled(isFreeContract);
		this.contRentCountType.setEnabled(isFreeContract);
		this.contDaysPerYear.setEnabled(isFreeContract);
		this.contMoreRoomsType.setEnabled(isFreeContract);
		this.chkIsAutoToInteger.setEnabled(isFreeContract);
		this.contToIntegerType.setEnabled(isFreeContract);
		this.contDigit.setEnabled(isFreeContract);
		// ���������� �����ڷ���
		this.chkIsAutoToIntegerForFee.setEnabled(isFreeContract);
		this.contToIntegerType2.setEnabled(isFreeContract);
		this.contDigit2.setEnabled(isFreeContract);

	}

	private boolean isMoney(String colKey, MoneyDefineCollection monDefineColl) {
		boolean boo = false;
		for (int i = 0; i < monDefineColl.size(); i++) {
			MoneyDefineInfo money = monDefineColl.get(i);
			if (money.getNumber().equals(colKey)) {
				boo = true;
				break;
			}
		}
		return boo;
	}

	// ����ʽ��ͬ���Ƹ�����ϸ���Ƿ��ܱ༭
	protected void isFreeConSet(boolean isFreeContract) throws BOSException {
		if (isLoad)
			return;
		if (isFreeContract) {
			if (this.tblRoom.getRowCount() == 0) {
				MsgBox.showInfo("����ѡ�����޷���!");
				this.chkIsFreeContract.setSelected(false);
				this.abort();
			}
			if (this.pkStartDate.getValue() == null
					|| this.pkEndDate.getValue() == null) {
				MsgBox.showInfo("����ȷ����ʼ����������");
				this.chkIsFreeContract.setSelected(false);
				this.abort();
			}
			for (int i = 0; i < this.tblRentSet.getRowCount(); i++) {
				IRow row = tblRentSet.getRow(i);
				for (int j = 0; j < this.tblRentSet.getColumnCount(); j++) {
					IColumn column = this.tblRentSet.getColumn(j);
					String colKey = column.getKey();
					if (!isMoney(colKey, monDefineColl)
							&& !C_RENT_FIRST_RENT.equals(colKey)) {
						row.getCell(colKey).getStyleAttributes().setLocked(
								isFreeContract);
					}
				}
			}
			this.tblPayList.setEditable(true);
			for (int i = 0; i < this.tblPayList.getRowCount(); i++) {
				IRow row = tblPayList.getRow(i);
				for (int j = 0; j < this.tblPayList.getColumnCount(); j++) {
					IColumn column = this.tblPayList.getColumn(j);
					String colKey = column.getKey();
					if (C_PAYS_MONEY_DEFINE.equals(colKey)) {
						String str = "";
						Object ob = row.getCell(colKey).getValue();
						if (ob instanceof String) {
							str = (String) ob;
						} else if (ob instanceof StringBuffer) {
							str = ((StringBuffer) ob).toString();
						} else if (ob instanceof MoneyDefineInfo) {
							str = ((MoneyDefineInfo) ob).getName();
						}
						int a = ((Integer) row.getCell(C_PAYS_LEASE_SEQ)
								.getValue()).intValue();
						// ����ǻ���������Ӧ�����ñ༭������ϸ�е�Ӧ��������
						if (str.split("��").length > 1) {
							row.getCell(C_PAYS_START_DATE).getStyleAttributes()
									.setLocked(false);
							row.getCell(C_PAYS_END_DATE).getStyleAttributes()
									.setLocked(false);
							row.getCell(C_PAYS_APP_PAY_DATE)
									.getStyleAttributes().setLocked(false);
							for (int k = 0; k < tblRoom.getRowCount(); k++) {
								row.getCell(
										PREFIX_C_PAYS_ROOM + k
												+ POSTFIX_C_PAYS_APP_AMOUNT)
										.getStyleAttributes().setLocked(true);
							}

						} else if (str.equals("���") && a != 1)// ���û�������Է��õ�ʱ������ǻ�����������ϸ�����Զ��ñ༭
						{
							row.getCell(C_PAYS_START_DATE).getStyleAttributes()
									.setLocked(false);
							row.getCell(C_PAYS_END_DATE).getStyleAttributes()
									.setLocked(false);
							row.getCell(C_PAYS_APP_PAY_DATE)
									.getStyleAttributes().setLocked(false);
							for (int k = 0; k < tblRoom.getRowCount(); k++) {
								row.getCell(
										PREFIX_C_PAYS_ROOM + k
												+ POSTFIX_C_PAYS_APP_AMOUNT)
										.getStyleAttributes().setLocked(false);
							}
						} else {
							// ��ϸ��ֻ�ܱ༭Ӧ������ʼ���ڡ��������ں�Ӧ������������б䶯
							row.getCell(C_PAYS_START_DATE).getStyleAttributes()
									.setLocked(true);
							row.getCell(C_PAYS_END_DATE).getStyleAttributes()
									.setLocked(true);
							row.getCell(C_PAYS_APP_PAY_DATE)
									.getStyleAttributes().setLocked(true);
							for (int k = 0; k < tblRoom.getRowCount(); k++) {
								row.getCell(
										PREFIX_C_PAYS_ROOM + k
												+ POSTFIX_C_PAYS_APP_AMOUNT)
										.getStyleAttributes().setLocked(false);
							}
						}
					}
				}
			}
		} else {
			rentSetStyle();
			initPayListTable();
		}
		setIsbtnPayList(isFreeContract);
		setOtherFreeContract(!this.chkIsFreeContract.isSelected());
	}

	private void rentSetStyle() {
		for (int i = 0; i < tblRentSet.getRowCount(); i++) {
			IRow row = tblRentSet.getRow(i);
			for (int j = 0; j < this.tblRentSet.getColumnCount(); j++) {
				IColumn column = this.tblRentSet.getColumn(j);
				String colKey = column.getKey();
				if (C_RENT_RENT_TYPE.equals(colKey)) {
					row.getCell(C_RENT_RENT_TYPE).getStyleAttributes()
							.setLocked(false);
				} else if (C_RENT_TENANCY_MODEL.equals(colKey)) {
					row.getCell(C_RENT_TENANCY_MODEL).getStyleAttributes()
							.setLocked(false);
					TenancyModeEnum tenModel = (TenancyModeEnum) row.getCell(
							C_RENT_TENANCY_MODEL).getValue();
					if (TenancyModeEnum.TenancyRentModel.equals(tenModel)) {
						for (int m = 0; m < tblRentSet.getColumnCount(); m++) {
							String tColKey = this.tblRentSet.getColumnKey(m);
							ICell cell = row.getCell(tColKey);
							if (tColKey.startsWith(PREFIX_C_RENT_RENT_PRICE)) {
								cell.getStyleAttributes().setLocked(true);
							} else if (tColKey.startsWith(PREFIX_C_RENT_RENT)) {
								cell.getStyleAttributes().setLocked(false);
							} else if (tColKey
									.startsWith(PREFIX_C_RENT_PERIODICITY)) {
								cell.getStyleAttributes().setLocked(false);
							} else if (tColKey
									.startsWith(PREFIX_C_RENT_PERIODICITY_PRICE)) {
								cell.getStyleAttributes().setLocked(true);
							}
						}
					} else {
						for (int m = 0; m < this.tblRentSet.getColumnCount(); m++) {
							String tColKey = this.tblRentSet.getColumnKey(m);
							ICell cell = row.getCell(tColKey);
							if (tColKey.startsWith(PREFIX_C_RENT_RENT_PRICE)) {
								cell.getStyleAttributes().setLocked(false);
							} else if (tColKey.startsWith(PREFIX_C_RENT_RENT)) {
								cell.getStyleAttributes().setLocked(true);
							} else if (tColKey
									.startsWith(PREFIX_C_RENT_PERIODICITY)) {
								cell.getStyleAttributes().setLocked(true);
							} else if (tColKey
									.startsWith(PREFIX_C_RENT_PERIODICITY_PRICE)) {
								cell.getStyleAttributes().setLocked(false);
							}
						}
					}
				}
			}

		}
	}

	protected void btnAddOtherPaylist_actionPerformed(ActionEvent e)
			throws Exception {
		super.btnAddOtherPaylist_actionPerformed(e);
		TenBillOtherPayInfo tenOtherInfo = new TenBillOtherPayInfo();
		addOtherPayListEntryRow(tenOtherInfo);
	}

	private void addOtherPayListEntryRow(TenBillOtherPayInfo tenOtherInfo) {
		if (tenOtherInfo == null)
			return;
		IRow row = this.tblOtherPayList.addRow();
		row.setUserObject(tenOtherInfo);
		row.getCell("leaseSeq").setValue(
				tenOtherInfo.getLeaseSeq() == 0 ? null : tenOtherInfo
						.getLeaseSeq());
		row.getCell("moneyTypeName").setValue(tenOtherInfo.getMoneyDefine());
		row.getCell("appDate").setValue(tenOtherInfo.getAppDate());
		row.getCell("startDate").setValue(tenOtherInfo.getStartDate());
		row.getCell("endDate").setValue(tenOtherInfo.getEndDate());
		row.getCell("currency").setValue(tenOtherInfo.getCurrency());
		row.getCell("appAmount").setValue(tenOtherInfo.getAppAmount());
		row.getCell("actRevAmount").setValue(tenOtherInfo.getAllRemainAmount());
		row.getCell("actRevDate").setValue(tenOtherInfo.getActRevDate());
		row.getCell("description").setValue(tenOtherInfo.getDescription());

		if(tenOtherInfo.isIsUnPay()){
			for(int i=0;i<this.tblOtherPayList.getColumnCount();i++){
				if(!this.tblOtherPayList.getColumnKey(i).equals("leaseSeq")){
					row.getCell(i).getStyleAttributes().setBackground(Color.CYAN);
				}
			}
		}
		// if (tenOtherInfo.getActRevAmount() != null &&
		// tenOtherInfo.getActRevAmount().compareTo(FDCHelper.ZERO) > 0) {
		// row.getStyleAttributes().setLocked(true);
		// row.getStyleAttributes().setBackground(FDCClientHelper.
		// KDTABLE_SUBTOTAL_BG_COLOR);
		// }
		// if(tenOtherInfo.getOtherBill()!=null){
		// row.getStyleAttributes().setLocked(true);
		// }
	}

	protected void btnDelOtherPaylist_actionPerformed(ActionEvent e)
			throws Exception {
		super.btnDelOtherPaylist_actionPerformed(e);
		int activeRowIndex = this.tblOtherPayList.getSelectManager()
				.getActiveRowIndex();
		if (activeRowIndex == -1) {
			activeRowIndex = this.tblOtherPayList.getRowCount();
		}
		IRow row = this.tblOtherPayList.getRow(activeRowIndex);
		if (row == null) {
			return;
		}
		TenBillOtherPayInfo entry = (TenBillOtherPayInfo) row.getUserObject();
		BigDecimal actAmount = entry.getActRevAmount();
		if (actAmount != null && actAmount.compareTo(FDCHelper.ZERO) != 0) {
			MsgBox.showInfo("�÷�¼�Ѿ��տ�,����ɾ��!");
			return;
		}
		if (entry.getOtherBill() != null) {
			MsgBox.showInfo("�÷�¼��������ͬ����,����ɾ��!");
			return;
		}
		this.tblOtherPayList.removeRow(activeRowIndex);
	}

	protected void chkIsFreeContract_actionPerformed(ActionEvent e)
			throws Exception {
		super.chkIsFreeContract_actionPerformed(e);
		updatePayListInfo(this.chkIsFreeContract.isSelected());
		isFreeConSet(this.chkIsFreeContract.isSelected());
	}

	// ���븶����ϸ��
	protected void btnInsert_actionPerformed(ActionEvent e) throws Exception {
		super.btnInsert_actionPerformed(e);
		IRow selectRow = KDTableUtil.getSelectedRow(tblPayList);
		if (selectRow == null)
			return;
		ITenancyPayListInfo onePay = (ITenancyPayListInfo) selectRow
				.getUserObject();
		if (onePay == null) {
			selectRow = tblPayList.getRow(tblPayList.getSelectManager()
					.getActiveRowIndex() + 1);
			onePay = (ITenancyPayListInfo) selectRow.getUserObject();
		}
		if (onePay.getLeaseSeq() == 1) {
			MsgBox.showInfo("��һ���ڲ��ܲ���");
			this.abort();
		}
		// ȡ��ѡ�����ڹ��м���������ϸ
		int payCount = 0;
		Map tmpMap = new HashMap();
		List list = new ArrayList();
		for (int i = 0; i < tblPayList.getRowCount(); i++) {
			IRow row4 = tblPayList.getRow(i);
			if (row4.getUserObject() != null) {
				ITenancyPayListInfo pay = (ITenancyPayListInfo) row4
						.getUserObject();
				if (pay.getLeaseSeq() == onePay.getLeaseSeq()) {
					list.add(pay);
					tmpMap.put(new Integer(onePay.getLeaseSeq()), list);
				}
			}
		}

		int rowCount = 0;
		List pays = (List) tmpMap.get(new Integer(onePay.getLeaseSeq()));
		payCount = pays.size();
		if (payCount > 1) {
			// һ�����ڸ�����ϸ�ж��е�ʱ��
			// �����жϴ���һ�п�ʼ�����ʱ�򲻻��������У������������
			rowCount = onePay.getLeaseSeq() - 1;
		} else {
			// һ������ֻ��һ��������ϸ
			rowCount = tblPayList.getSelectManager().getActiveRowIndex();
		}
		// ���ֻ��һ�ֿ���Ļ���ֱ������һ����¼
		if (pays.size() == 1) {
			ITenancyPayListInfo tPay = (ITenancyPayListInfo) pays.get(0);
			if (tPay.getLeaseSeq() == onePay.getLeaseSeq()) {
				IRow row = this.tblPayList.addRow(selectRow.getRowIndex());
				row.setTreeLevel(0);
				ITenancyPayListInfo roomPay = new TenancyRoomPayListEntryInfo();
				toSale(tPay, roomPay);
				roomPay.setLeaseSeq(onePay.getLeaseSeq());
				roomPay.setSeq(tPay.getSeq());
				row.setUserObject(tPay);

				row.getCell(C_PAYS_MONEY_DEFINE)
						.setValue(tPay.getMoneyDefine());
				row.getCell(C_PAYS_LEASE_SEQ).setValue(
						new Integer(tPay.getLeaseSeq()));
				row.getCell(C_PAYS_START_DATE).setValue(tPay.getStartDate());
				row.getCell(C_PAYS_START_DATE).getStyleAttributes().setLocked(
						false);
				row.getCell(C_PAYS_END_DATE).setValue(tPay.getEndDate());
				row.getCell(C_PAYS_END_DATE).getStyleAttributes().setLocked(
						false);
				row.getCell(C_PAYS_APP_PAY_DATE).setValue(tPay.getAppPayDate());
				for (int m = 0; m < tblRoom.getRowCount(); m++) {
					// ��Ӧ������д��ϸ���ϸ��Ӧ�ķ����Ա��ڱ����ʱ��ȡֵ
					TenancyRoomPayListEntryInfo ten = (TenancyRoomPayListEntryInfo) selectRow
							.getCell(
									PREFIX_C_PAYS_ROOM + m
											+ POSTFIX_C_PAYS_APP_AMOUNT)
							.getUserObject();
					row.getCell(
							PREFIX_C_PAYS_ROOM + m + POSTFIX_C_PAYS_APP_AMOUNT)
							.setUserObject(ten);
				}
			}
		} else {
			// ����ж��ֻ���Ҫ���ݸ�����ϸ���ɻ�����,������������жϴ���һ�п�ʼ�����¼
			for (int m = 0; m < tblPayList.getRowCount(); m++) {
				IRow row5 = tblPayList.getRow(m);
				if (row5.getUserObject() != null) {
					ITenancyPayListInfo tPay2 = (ITenancyPayListInfo) row5
							.getUserObject();
					if (tPay2.getLeaseSeq() < onePay.getLeaseSeq()) {
						rowCount++;
					}
				}

			}
			StringBuffer moneyDes = new StringBuffer();
			// ������ֻ������һ��������λ����Ҫ�ڵ�һ�����ԾͰѵ�һ����λ�ü�¼���������������ֵ
			int aa = rowCount;
			boolean boo = false;
			for (int i = 0; i < pays.size(); i++) {
				ITenancyPayListInfo tPay = (ITenancyPayListInfo) pays.get(i);
				if (tPay.getLeaseSeq() == onePay.getLeaseSeq()) {
					IRow unionRow = null;
					if (!boo) {
						unionRow = this.tblPayList.addRow(aa);
						unionRow.setTreeLevel(0);
						boo = true;
					}
					IRow entryRow = this.tblPayList.addRow(rowCount + 1);
					entryRow.setTreeLevel(1);
					ITenancyPayListInfo roomPay = new TenancyRoomPayListEntryInfo();
					toSale(tPay, roomPay);
					roomPay.setLeaseSeq(onePay.getLeaseSeq());
					roomPay.setSeq(tPay.getSeq());
					entryRow.setUserObject(tPay);
					rowCount++;

					entryRow.getCell(C_PAYS_MONEY_DEFINE).setValue(
							"  " + tPay.getMoneyDefine());
					entryRow.getCell(C_PAYS_LEASE_SEQ).setValue(
							new Integer(onePay.getLeaseSeq()));
					entryRow.getCell(C_PAYS_START_DATE).setValue(
							tPay.getStartDate());
					entryRow.getCell(C_PAYS_END_DATE).setValue(
							tPay.getEndDate());
					entryRow.getCell(C_PAYS_APP_PAY_DATE).setValue(
							tPay.getAppPayDate());
					entryRow.getCell(C_PAYS_APP_PAY_DATE).getStyleAttributes()
							.setLocked(true);
					for (int m = 0; m < tblRoom.getRowCount(); m++) {
						// ��Ӧ������д��ϸ���ϸ��Ӧ�ķ����Ա��ڱ����ʱ��ȡֵ
						TenancyRoomPayListEntryInfo ten = (TenancyRoomPayListEntryInfo) selectRow
								.getCell(
										PREFIX_C_PAYS_ROOM + m
												+ POSTFIX_C_PAYS_APP_AMOUNT)
								.getUserObject();
						entryRow.getCell(
								PREFIX_C_PAYS_ROOM + m
										+ POSTFIX_C_PAYS_APP_AMOUNT)
								.setUserObject(ten);
					}
					if (i != 0) {
						moneyDes.append("��");
					}
					moneyDes.append(tPay.getMoneyDefine());
					// ����ж����Ļ��͸������и�ֵ
					if (i != 0) {
						unionRow = tblPayList.getRow(aa);
						unionRow.getCell(C_PAYS_MONEY_DEFINE).setValue(
								moneyDes.toString());
						unionRow.getCell(C_PAYS_LEASE_SEQ).setValue(
								new Integer(onePay.getLeaseSeq()));
						unionRow.getCell(C_PAYS_START_DATE).setValue(
								tPay.getStartDate());
						unionRow.getCell(C_PAYS_START_DATE)
								.getStyleAttributes().setLocked(false);
						unionRow.getCell(C_PAYS_END_DATE).setValue(
								tPay.getEndDate());
						unionRow.getCell(C_PAYS_END_DATE).getStyleAttributes()
								.setLocked(false);
						unionRow.getCell(C_PAYS_APP_PAY_DATE).setValue(
								tPay.getAppPayDate());
						for (int k = 0; k < tblRoom.getRowCount(); k++) {
							unionRow.getCell(
									PREFIX_C_PAYS_ROOM + k
											+ POSTFIX_C_PAYS_APP_AMOUNT)
									.getStyleAttributes().setLocked(true);
						}
					}
				}
			}
		}
		// }
		// �����˼�¼�󣬸�������ļ�¼��������Ŷ���1
		for (int i = rowCount + 1; i < tblPayList.getRowCount(); i++) {
			IRow row = tblPayList.getRow(i);
			int inte = ((Integer) row.getCell(C_PAYS_LEASE_SEQ).getValue())
					.intValue();
			if (row.getUserObject() != null) {
				ITenancyPayListInfo object = (ITenancyPayListInfo) row
						.getUserObject();
				ITenancyPayListInfo roomPay = new TenancyRoomPayListEntryInfo();
				toSale(object, roomPay);
				roomPay.setLeaseSeq(object.getLeaseSeq() + 1);
				roomPay.setSeq(object.getSeq() + payCount);
				row.setUserObject(roomPay);
			}
			row.getCell(C_PAYS_LEASE_SEQ).setValue(new Integer(inte + 1));
		}
		this.tblPayList.getMergeManager().mergeBlock(0, 0,
				this.tblPayList.getRowCount() - 1, 0,
				KDTMergeManager.FREE_ROW_MERGE);
	}

	// ���Ӹ�����ϸ��
	protected void btnAddPayList_actionPerformed(ActionEvent e)
			throws Exception {
		super.btnAddPayList_actionPerformed(e);
		// ȡ��������ڵĸ�����ϸ
		List payList = new ArrayList();
		IRow row = this.tblPayList.getRow(tblPayList.getRowCount() - 1);
		int leaseSeq = ((Integer) row.getCell(C_PAYS_LEASE_SEQ).getValue())
				.intValue();
		ITenancyPayListInfo payInfo = (ITenancyPayListInfo) row.getUserObject();
		if (leaseSeq == 1) {
			for (int i = 0; i < tblPayList.getRowCount(); i++) {
				IRow row2 = tblPayList.getRow(i);
				if (row2.getUserObject() != null) {
					ITenancyPayListInfo payInfo2 = (ITenancyPayListInfo) row2
							.getUserObject();
					if (!payInfo2.getMoneyDefine().getMoneyType().equals(
							MoneyTypeEnum.DepositAmount)) {
						payList.add(payInfo2);
					}
				}
			}
		} else {
			for (int i = 0; i < tblPayList.getRowCount(); i++) {
				IRow row2 = tblPayList.getRow(i);
				int seq = ((Integer) row2.getCell(C_PAYS_LEASE_SEQ).getValue())
						.intValue();
				if (leaseSeq == seq) {
					if (row2.getUserObject() != null) {
						ITenancyPayListInfo payInfo2 = (ITenancyPayListInfo) row2
								.getUserObject();
						payList.add(payInfo2);
					}
				}
			}
		}

		if (payList.size() == 1) {
			IRow entryRow = this.tblPayList.addRow(tblPayList.getRowCount());
			entryRow.setTreeLevel(1);
			ITenancyPayListInfo roomPay = new TenancyRoomPayListEntryInfo();
			toSale(payInfo, roomPay);
			roomPay.setLeaseSeq(leaseSeq + 1);
			roomPay.setSeq(payInfo.getSeq() + 1);
			entryRow.setUserObject(roomPay);

			entryRow.getCell(C_PAYS_MONEY_DEFINE).setValue(
					payInfo.getMoneyDefine());
			entryRow.getCell(C_PAYS_LEASE_SEQ).setValue(
					new Integer(leaseSeq + 1));
			entryRow.getCell(C_PAYS_START_DATE)
					.setValue(payInfo.getStartDate());
			entryRow.getCell(C_PAYS_START_DATE).getStyleAttributes().setLocked(
					false);
			entryRow.getCell(C_PAYS_END_DATE).setValue(payInfo.getEndDate());
			entryRow.getCell(C_PAYS_END_DATE).getStyleAttributes().setLocked(
					false);
			entryRow.getCell(C_PAYS_APP_PAY_DATE).setValue(
					payInfo.getAppPayDate());
			for (int m = 0; m < tblRoom.getRowCount(); m++) {
				// ��Ӧ������д��ϸ���ϸ��Ӧ�ķ����Ա��ڱ����ʱ��ȡֵ
				TenancyRoomPayListEntryInfo ten = (TenancyRoomPayListEntryInfo) row
						.getCell(
								PREFIX_C_PAYS_ROOM + m
										+ POSTFIX_C_PAYS_APP_AMOUNT)
						.getUserObject();
				entryRow.getCell(
						PREFIX_C_PAYS_ROOM + m + POSTFIX_C_PAYS_APP_AMOUNT)
						.setUserObject(ten);
			}
		} else {
			int rowCount = tblPayList.getRowCount();
			int aa = rowCount;
			boolean boo = false;
			StringBuffer moneyDes = new StringBuffer();
			for (int i = 0; i < payList.size(); i++) {
				ITenancyPayListInfo payListInfo = (ITenancyPayListInfo) payList
						.get(i);
				IRow unionRow = null;
				if (!boo) {
					unionRow = this.tblPayList.addRow(aa);
					unionRow.setTreeLevel(0);
					boo = true;
				}
				IRow entryRow = this.tblPayList.addRow(rowCount + 1);
				entryRow.setTreeLevel(1);
				ITenancyPayListInfo roomPay = new TenancyRoomPayListEntryInfo();
				toSale(payListInfo, roomPay);
				roomPay.setLeaseSeq(leaseSeq + 1);
				roomPay.setSeq(payListInfo.getSeq() + payList.size());
				entryRow.setUserObject(roomPay);
				rowCount++;

				entryRow.getCell(C_PAYS_MONEY_DEFINE).setValue(
						"  " + payListInfo.getMoneyDefine());
				entryRow.getCell(C_PAYS_LEASE_SEQ).setValue(
						new Integer(leaseSeq + 1));
				entryRow.getCell(C_PAYS_START_DATE).setValue(
						payListInfo.getStartDate());
				entryRow.getCell(C_PAYS_END_DATE).setValue(
						payListInfo.getEndDate());
				entryRow.getCell(C_PAYS_APP_PAY_DATE).setValue(
						payListInfo.getAppPayDate());
				entryRow.getCell(C_PAYS_APP_PAY_DATE).getStyleAttributes()
						.setLocked(true);
				for (int m = 0; m < tblRoom.getRowCount(); m++) {
					// ��Ӧ������д��ϸ���ϸ��Ӧ�ķ����Ա��ڱ����ʱ��ȡֵ
					TenancyRoomPayListEntryInfo ten = (TenancyRoomPayListEntryInfo) row
							.getCell(
									PREFIX_C_PAYS_ROOM + m
											+ POSTFIX_C_PAYS_APP_AMOUNT)
							.getUserObject();
					entryRow.getCell(
							PREFIX_C_PAYS_ROOM + m + POSTFIX_C_PAYS_APP_AMOUNT)
							.setUserObject(ten);
				}
				if (i != 0) {
					moneyDes.append("��");
				}
				moneyDes.append(payListInfo.getMoneyDefine());
				// ����ж����Ļ��͸������и�ֵ
				if (i != 0) {
					unionRow = tblPayList.getRow(aa);
					unionRow.getCell(C_PAYS_MONEY_DEFINE).setValue(
							moneyDes.toString());
					unionRow.getCell(C_PAYS_LEASE_SEQ).setValue(
							new Integer(leaseSeq + 1));
					unionRow.getCell(C_PAYS_START_DATE).setValue(
							payListInfo.getStartDate());
					unionRow.getCell(C_PAYS_START_DATE).getStyleAttributes()
							.setLocked(false);
					unionRow.getCell(C_PAYS_END_DATE).setValue(
							payListInfo.getEndDate());
					unionRow.getCell(C_PAYS_END_DATE).getStyleAttributes()
							.setLocked(false);
					unionRow.getCell(C_PAYS_APP_PAY_DATE).setValue(
							payListInfo.getAppPayDate());
					for (int k = 0; k < tblRoom.getRowCount(); k++) {
						unionRow.getCell(
								PREFIX_C_PAYS_ROOM + k
										+ POSTFIX_C_PAYS_APP_AMOUNT)
								.getStyleAttributes().setLocked(true);
					}
				}
			}
			this.tblPayList.getMergeManager().mergeBlock(0, 0,
					this.tblPayList.getRowCount() - 1, 0,
					KDTMergeManager.FREE_ROW_MERGE);
		}

	}

	// ɾ��������ϸ��
	protected void btnDelPayList_actionPerformed(ActionEvent e)
			throws Exception {
		super.btnDelPayList_actionPerformed(e);

		IRow selectRow = KDTableUtil.getSelectedRow(tblPayList);
		if (selectRow == null)
			return;
		ITenancyPayListInfo onePay = (ITenancyPayListInfo) selectRow
				.getUserObject();
		if (onePay == null) {
			selectRow = tblPayList.getRow(tblPayList.getSelectManager()
					.getActiveRowIndex() + 1);
			onePay = (ITenancyPayListInfo) selectRow.getUserObject();
			BigDecimal actAmount = onePay.getActAmount();
			if (actAmount != null && actAmount.compareTo(FDCHelper.ZERO) != 0) {
				MsgBox.showInfo("�÷�¼�Ѿ��տ�,����ɾ��!");
				return;
			}
		}
		if (onePay.getLeaseSeq() == 1) {
			MsgBox.showInfo("��һ���ڲ���ɾ��");
			this.abort();
		}

		// if(((Integer)tblPayList.getRow(tblPayList.getRowCount()-1).getCell(
		// C_PAYS_LEASE_SEQ).getValue()).intValue()==2)
		// {
		// MsgBox.showInfo("������ϸ��ֻ��2�У�������ɾ��");
		// this.abort();
		// }

		int leaseSeq = onePay.getLeaseSeq();
		int rowCount = 0;
		for (int i = 0; i < tblPayList.getRowCount(); i++) {
			IRow row = tblPayList.getRow(i);
			int seq = ((Integer) row.getCell(C_PAYS_LEASE_SEQ).getValue())
					.intValue();
			if (seq < leaseSeq) {
				rowCount++;
			} else if (leaseSeq == seq) {
				this.tblPayList.removeRow(i);
				--i;
			}
		}

		// ȷ��1���������м���������ϸ
		int seqCount = 0;
		for (int i = tblPayList.getRowCount() - 1; i > 0; i--) {
			IRow row = tblPayList.getRow(i);
			if (row.getUserObject() == null) {
				seqCount = tblPayList.getRowCount() - 1 - i;
				break;
			} else if (row.getUserObject() != null && i == 1) {
				seqCount = 1;
			}
		}

		for (int i = rowCount; i < tblPayList.getRowCount(); i++) {
			IRow row = tblPayList.getRow(i);
			int inte = ((Integer) row.getCell(C_PAYS_LEASE_SEQ).getValue())
					.intValue();
			if (row.getUserObject() != null) {
				ITenancyPayListInfo object = (ITenancyPayListInfo) row
						.getUserObject();
				ITenancyPayListInfo roomPay = new TenancyRoomPayListEntryInfo();
				toSale(object, roomPay);
				roomPay.setLeaseSeq(inte - 1);
				roomPay.setSeq(object.getSeq() - seqCount);
				row.setUserObject(roomPay);
			}
			row.getCell(C_PAYS_LEASE_SEQ).setValue(new Integer(inte - 1));
		}
		this.tblPayList.getMergeManager().mergeBlock(0, 0,
				this.tblPayList.getRowCount() - 1, 0,
				KDTMergeManager.FREE_ROW_MERGE);
	}

	private ITenancyPayListInfo toSale(ITenancyPayListInfo object,
			ITenancyPayListInfo roomPay) {
		roomPay.setMoneyDefine(object.getMoneyDefine());
		roomPay.setStartDate(object.getStartDate());
		roomPay.setEndDate(object.getEndDate());
		roomPay.setAppPayDate(object.getAppPayDate());
		return roomPay;
	}

	protected void chkIsFreeContract_stateChanged(ChangeEvent e)
			throws Exception {
	}

	private void initOtherPayList() {
		this.tblOtherPayList.checkParsed();
		this.tblOtherPayList.getStyleAttributes().setLocked(false);
		this.tblOtherPayList.getSelectManager().setSelectMode(
				KDTSelectManager.CELL_SELECT);
		this.tblOtherPayList
				.setActiveCellStatus(KDTStyleConstants.ACTIVE_CELL_EDIT);

		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems()
				.add(
						new FilterItemInfo("sysType",
								MoneySysTypeEnum.TENANCYSYS_VALUE));
		filter.getFilterItems().add(
				new FilterItemInfo("moneyType", MoneyTypeEnum.LATEFEE_VALUE));
		filter.getFilterItems().add(
				new FilterItemInfo("moneyType",
						MoneyTypeEnum.COMMISSIONCHARGE_VALUE));
		filter.getFilterItems().add(
				new FilterItemInfo("moneyType",
						MoneyTypeEnum.FITMENTAMOUNT_VALUE));
		filter.getFilterItems()
				.add(
						new FilterItemInfo("moneyType",
								MoneyTypeEnum.ELSEAMOUNT_VALUE));
		filter.getFilterItems()
				.add(
						new FilterItemInfo("moneyType",
								MoneyTypeEnum.REPLACEFEE_VALUE));
		filter.getFilterItems().add(
				new FilterItemInfo("moneyType", MoneyTypeEnum.BREACHFEE_VALUE));
		filter.setMaskString("#0 and (#1 or #2 or #3 or #4 or #5 or #6)");
		view.setFilter(filter);
		this.tblOtherPayList.getColumn("moneyTypeName").setEditor(
				CommerceHelper.getKDBizPromptBoxEditor(
						"com.kingdee.eas.fdc.sellhouse.app.MoneyDefineQuery",
						view));
		this.tblOtherPayList.getColumn("appDate").setEditor(
				CommerceHelper.getKDDatePickerEditor());
		this.tblOtherPayList.getColumn("appDate").getStyleAttributes()
				.setNumberFormat("yyyy-MM-dd");
		this.tblOtherPayList.getColumn("appDate").setRequired(true);
		this.tblOtherPayList.getColumn("startDate").setEditor(
				CommerceHelper.getKDDatePickerEditor());
		this.tblOtherPayList.getColumn("startDate").getStyleAttributes()
				.setNumberFormat("yyyy-MM-dd");
		this.tblOtherPayList.getColumn("endDate").setEditor(
				CommerceHelper.getKDDatePickerEditor());
		this.tblOtherPayList.getColumn("endDate").getStyleAttributes()
				.setNumberFormat("yyyy-MM-dd");
		this.tblOtherPayList.getColumn("actRevDate").setEditor(
				CommerceHelper.getKDDatePickerEditor());
		this.tblOtherPayList.getColumn("actRevDate").getStyleAttributes()
				.setNumberFormat("yyyy-MM-dd");
		this.tblOtherPayList.getColumn("actRevDate").getStyleAttributes()
				.setLocked(true);
		this.tblOtherPayList
				.getColumn("currency")
				.setEditor(
						CommerceHelper
								.getKDBizPromptBoxEditor(
										"com.kingdee.eas.basedata.assistant.app.F7CurrencyQuery",
										null));
		this.tblOtherPayList.getColumn("currency").getStyleAttributes()
				.setLocked(true);

		KDFormattedTextField formattedTextField = new KDFormattedTextField(
				KDFormattedTextField.BIGDECIMAL_TYPE);
		formattedTextField.setSupportedEmpty(true);
		formattedTextField.setPrecision(2);
		formattedTextField.setNegatived(false);
		ICellEditor numberEditor = new KDTDefaultCellEditor(formattedTextField);
		this.tblOtherPayList.getColumn("appAmount").setEditor(numberEditor);
		this.tblOtherPayList.getColumn("appAmount").getStyleAttributes()
				.setHorizontalAlign(HorizontalAlignment.RIGHT);
		this.tblOtherPayList.getColumn("appAmount").getStyleAttributes()
				.setNumberFormat(FDCHelper.getNumberFtm(2));

		this.tblOtherPayList.getColumn("actRevAmount").setEditor(numberEditor);
		this.tblOtherPayList.getColumn("actRevAmount").getStyleAttributes()
				.setHorizontalAlign(HorizontalAlignment.RIGHT);
		this.tblOtherPayList.getColumn("actRevAmount").getStyleAttributes()
				.setNumberFormat(FDCHelper.getNumberFtm(2));
		this.tblOtherPayList.getColumn("actRevAmount").getStyleAttributes()
				.setLocked(true);

		this.tblOtherPayList.getColumn("appAmount").getStyleAttributes()
				.setBackground(FDCClientHelper.KDTABLE_COMMON_BG_COLOR);
		this.tblOtherPayList.getColumn("moneyTypeName").getStyleAttributes()
				.setBackground(FDCClientHelper.KDTABLE_COMMON_BG_COLOR);

		KDTextField textField = new KDTextField();
		textField.setMaxLength(80);
		KDTDefaultCellEditor txtEditor = new KDTDefaultCellEditor(textField);
		this.tblOtherPayList.getColumn("description").setEditor(txtEditor);

		this.tblOtherPayList.getGroupManager().setGroup(true);

		this.tblOtherPayList.getColumn("leaseSeq").setGroup(true);
		this.tblOtherPayList.getColumn("leaseSeq").setMergeable(true);
		this.tblOtherPayList.getColumn("leaseSeq").getStyleAttributes()
				.setLocked(true);
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
		TenancyBillDataProvider data = new TenancyBillDataProvider(editData
				.getString("id"), getTDQueryPK());
		com.kingdee.bos.ctrl.report.forapp.kdnote.client.KDNoteHelper appHlp = new com.kingdee.bos.ctrl.report.forapp.kdnote.client.KDNoteHelper();
		appHlp.print(getTDFileName(), data, javax.swing.SwingUtilities
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
		TenancyBillDataProvider data = new TenancyBillDataProvider(editData
				.getString("id"), getTDQueryPK());
		com.kingdee.bos.ctrl.report.forapp.kdnote.client.KDNoteHelper appHlp = new com.kingdee.bos.ctrl.report.forapp.kdnote.client.KDNoteHelper();
		appHlp.printPreview(getTDFileName(), data, javax.swing.SwingUtilities
				.getWindowAncestor(this));
	}

	private void afterPressDeleteButton() {
		tblPayList.setBeforeAction(new BeforeActionListener() {
			public void beforeAction(BeforeActionEvent e) {
				if (BeforeActionEvent.ACTION_DELETE == e.getType()) {
					// ///�����������������ʱ��ֱ�ӷ���
					for (int i = 0; i < tblPayList.getSelectManager().size(); i++) {
						KDTSelectBlock block = tblPayList.getSelectManager()
								.get(i);
						for (int rowIndex = block.getBeginRow(); rowIndex <= block
								.getEndRow(); rowIndex++) {
							for (int colIndex = block.getBeginCol(); colIndex <= block
									.getEndCol(); colIndex++) {
								KDTEditEvent event = new KDTEditEvent(
										tblPayList, null, null, rowIndex,
										colIndex, true, 1);
								try {
									tblPayList_editStopped(event);
								} catch (Exception e1) {
									handleException(e1);
								}
							}
						}
					}
				}
			}
		});
	}

	protected String getTDFileName() {
		return "/bim/fdc/tenancy/TenancyBill";
	}

	protected IMetaDataPK getTDQueryPK() {
		return new MetaDataPK(
				"com.kingdee.eas.fdc.tenancy.app.TenancyBillPrintQuery");
	}

	/**
	 * ��ʵ���������˱����������õ�ʱ��Ԫ�������ɵ�abstract��loadFields()�ｫ������setAutoNumberByOrg(
	 * String orgType)����������������
	 * ��������TenBillBase(Ҳ����FDCBILL)��onLoad�������handleCodingRule()
	 * ������ͻ��Ҳ������������ʱ���������α�������ICodingRuleManager.getNumber(),
	 * �����ÿ��������ʱ�򶼳��ֲ���Ϊ����������岽��*2 (�����ǲ��������) ���������ε��Զ����ɵı������������ xin_wang
	 * 2010.11.15
	 */
	protected void setAutoNumberByOrg(String orgType) {
		// super.setAutoNumberByOrg(orgType);
	}

	protected boolean isShowAttachmentAction() {
		return true;
	}

	protected void btnViewRoomInfo_actionPerformed(ActionEvent e)
			throws Exception {
		int activeRowIndex = this.tblRoom.getSelectManager()
				.getActiveRowIndex();
		if (activeRowIndex < 0) {
			MsgBox.showInfo("��ѡ��һ�з��䣡");
		}
		IRow row = tblRoom.getRow(activeRowIndex);
		if (row != null) {
			TenancyRoomEntryInfo tenancyRoom = (TenancyRoomEntryInfo) row
					.getUserObject();
			if (tenancyRoom.getRoom() != null) {
				UIContext uiContext = new UIContext(this);
				uiContext.put("ID", tenancyRoom.getRoom().getId().toString());
				IUIWindow uiWindow = UIFactory.createUIFactory(
						UIFactoryName.NEWTAB).create(
						RoomEditUI.class.getName(), uiContext, null, "VIEW");
				uiWindow.show();
			}
		}
	}

	protected void tblCustomer_tableClicked(KDTMouseEvent e) throws Exception {
		if (e.getType() == KDTStyleConstants.BODY_ROW
				&& e.getButton() == MouseEvent.BUTTON1
				&& e.getClickCount() == 2
				&& tblCustomer.getColumn(e.getColIndex()).getKey().equals(
						"customer")) {
			IRow row = tblCustomer.getRow(e.getRowIndex());
			if (row != null) {
				TenancyCustomerEntryInfo TenancyCustomer = (TenancyCustomerEntryInfo) row
						.getUserObject();
				if (TenancyCustomer.getFdcCustomer() != null) {
					UIContext uiContext = new UIContext(this);
					uiContext.put("ID", TenancyCustomer.getFdcCustomer()
							.getId().toString());
					IUIWindow uiWindow = UIFactory.createUIFactory(
							UIFactoryName.NEWTAB).create(
							CustomerEditUI.class.getName(), uiContext, null,
							"VIEW");
					uiWindow.show();
				}
			}
		}
	}

	protected void btnViewCustInfo_actionPerformed(ActionEvent e)
			throws Exception {
		int activeRowIndex = this.tblCustomer.getSelectManager()
				.getActiveRowIndex();
		if (activeRowIndex < 0) {
			MsgBox.showInfo("��ѡ��һ�пͻ���");
		}
		IRow row = tblCustomer.getRow(activeRowIndex);
		if (row != null) {
			TenancyCustomerEntryInfo TenancyCustomer = (TenancyCustomerEntryInfo) row
					.getUserObject();
			if (TenancyCustomer.getFdcCustomer() != null) {
				UIContext uiContext = new UIContext(this);
				uiContext.put("ID", TenancyCustomer.getFdcCustomer().getId()
						.toString());
				IUIWindow uiWindow = UIFactory.createUIFactory(
						UIFactoryName.NEWTAB)
						.create(CustomerEditUI.class.getName(), uiContext,
								null, "VIEW");
				uiWindow.show();
			}
		}
	}

	protected void DPickFromMonth_dataChanged(DataChangeEvent e)
			throws Exception {
		updatePayListInfo();
	}

	// add by yangfan
	protected void chkIsMDLiquidated_actionPerformed(ActionEvent e)
			throws Exception {
		if (this.chkIsMDLiquidated.getSelected() == 32) {
			this.panLiquidated.setVisible(false);
			this.panMDLiquidated.setVisible(true);
		} else {
			this.panLiquidated.setVisible(true);
			this.panMDLiquidated.setVisible(false);
		}
		super.chkIsMDLiquidated_actionPerformed(e);
	}

	protected void txtRate_dataChanged(DataChangeEvent e) throws Exception {
		if (e.getNewValue() != null
				&& ((BigDecimal) e.getNewValue())
						.compareTo(new BigDecimal(100)) == 1) {
			txtRate.setValue(e.getOldValue());
		}
		super.txtRate_dataChanged(e);
	}

	protected void prmtRentFreeBill_dataChanged(DataChangeEvent e)
			throws Exception {
		boolean isChanged = true;
		isChanged = BizCollUtil.isF7ValueChanged(e);
		if (!isChanged) {
			return;
		}
		RentFreeBillInfo info = (RentFreeBillInfo) this.prmtRentFreeBill
				.getValue();
		this.editData.getRentFrees().clear();
		if (info != null) {
			RentFreeEntryCollection col = RentFreeEntryFactory
					.getRemoteInstance().getRentFreeEntryCollection(
							"select * from where rentFreeBill.id='"
									+ info.getId() + "' order by seq");
			for (int i = 0; i < col.size(); i++) {
				this.editData.getRentFrees().add(col.get(i));
			}
		}
		this.loadFreeRents(this.editData.getRentFrees());
		updatePayListInfo();
	}

	protected void cbMD_itemStateChanged(ItemEvent arg0) throws Exception {
		for (int i = 0; i < this.tblPayList.getRowCount(); i++) {
			IRow row = this.tblPayList.getRow(i);
			ITenancyPayListInfo tPay = (ITenancyPayListInfo) row
					.getUserObject();
			if (this.cbMD.getSelectedItem().toString().equals("")) {
				row.getStyleAttributes().setHided(false);
			} else {
				if (tPay != null
						&& tPay.getMoneyDefine().getName().equals(
								this.cbMD.getSelectedItem().toString())) {
					row.getStyleAttributes().setHided(false);
				} else {
					row.getStyleAttributes().setHided(true);
				}
			}
		}
	}

	public void actionAttachment_actionPerformed(ActionEvent e)
			throws Exception {
		AttachmentClientManager acm = AttachmentManagerFactory
				.getClientManager();
		String boID = getSelectBOID();
		if (boID == null)
			return;
		boolean isEdit = false;
		if ("ADDNEW".equals(getOprtState()) || "EDIT".equals(getOprtState())) {
			isEdit = true;
		}
		acm.showAttachmentListUIByBoID(boID, this, isEdit);
		fillAttachmnetTable();
	}

	public boolean destroyWindow() {
		boolean b = super.destroyWindow();
		if (b) {
			try {
				if (!TenancyBillFactory.getRemoteInstance().exists(
						new ObjectUuidPK(this.editData.getId().toString()))) {
					deleteAttachment(this.editData.getId().toString());
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return b;
	}

	protected void deleteAttachment(String id) throws BOSException,
			EASBizException {
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();

		filter.getFilterItems().add(new FilterItemInfo("boID", id));
		view.setFilter(filter);
		BoAttchAssoCollection col = BoAttchAssoFactory.getRemoteInstance()
				.getBoAttchAssoCollection(view);
		for (int i = 0; i < col.size(); i++) {
			EntityViewInfo attview = new EntityViewInfo();
			FilterInfo attfilter = new FilterInfo();

			attfilter.getFilterItems().add(
					new FilterItemInfo("attachment.id", col.get(i)
							.getAttachment().getId().toString()));
			attview.setFilter(attfilter);
			BoAttchAssoCollection attcol = BoAttchAssoFactory
					.getRemoteInstance().getBoAttchAssoCollection(attview);
			if (attcol.size() == 1) {
				BizobjectFacadeFactory.getRemoteInstance()
						.delTempAttachment(id);
			} else if (attcol.size() > 1) {
				BoAttchAssoFactory.getRemoteInstance().delete(filter);
			}
		}
	}
	
	
	
	private void fillDataToRow(TenPriceEntryInfo entry, IRow r) {
		r.getCell("sellProject").setValue(entry.getSellProject());
		r.getCell("roomName").setValue(entry.getRoomName());
		r.getCell("area").setValue(entry.getArea());
		r.getCell("dayPrice").setValue(entry.getDayPrice());
		r.getCell("monthPrice").setValue(entry.getMonthPrice());
		r.getCell("yearPrice").setValue(entry.getYearPrice());
		r.getCell("maxFreeDay").setValue(entry.getMaxFreeDay());
		r.getCell("maxLease").setValue(entry.getMaxLease());
		if(r.getCell("depoist") != null)
		  r.getCell("depoist").setValue(entry.getDeposit());//deposit
		if(r.getCell("deposit") != null)
		  r.getCell("deposit").setValue(entry.getDeposit());
		r.getCell("id").setValue(entry.getId());
		r.getCell("id").setUserObject(entry);
		if(r.getCell("description") != null)
			  r.getCell("description").setValue(entry.getDescription());
	}

	

	public void actionAdust_actionPerformed(ActionEvent e) throws Exception {
		if (MsgBox.showConfirm2("�Ƿ�ȷ���޸ģ�") == MsgBox.CANCEL) {
			return;
		}
		verifyInput(null);
		chkIsFreeContract.setSelected(true);
		this.storeFields();
		TenancyBillFactory.getRemoteInstance().updatePartial(editData,
				this.getSelectors());
		FDCSQLBuilder fdcSB = new FDCSQLBuilder();
		fdcSB.setBatchType(FDCSQLBuilder.STATEMENT_TYPE);
		for (int i = 0; i < tblPayList.getRowCount(); i++) {
			IRow row = this.tblPayList.getRow(i);
			if (row.getUserObject() != null) {
				TenancyRoomPayListEntryInfo payListInfo = (TenancyRoomPayListEntryInfo) row
						.getUserObject();
				String id = payListInfo.getId().toString();
				BigDecimal appAmount = null;
				for (int k = 0; k < this.tblPayList.getColumnCount(); k++) {
					if (this.tblPayList.getColumnKey(k).indexOf(
							POSTFIX_C_PAYS_APP_AMOUNT) >= 0) {
						appAmount = (BigDecimal) row.getCell(k).getValue();
					}
				}
				StringBuffer sql = new StringBuffer();
				sql
						.append("update T_TEN_TenancyRoomPayListEntry set fappAmount = "
								+ appAmount + " where fid = '" + id + "'");
				fdcSB.addBatch(sql.toString());
			}
		}
		fdcSB.executeBatch();
	}

	@Override
	public boolean useScrollPane() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public int getHorizontalScrollPolicy() {
		// TODO Auto-generated method stub
		return 30;
	}

	public int getVerticalScrollPolicy() {
		return 20;
	}

	@Override
	public void actionSelectTenPrice_actionPerformed(ActionEvent e)
			throws Exception {
		// TODO Auto-generated method stub
		super.actionSelectTenPrice_actionPerformed(e);
		int s = this.tblTenPrice.getRowCount();
		Set<String> selectedIds = new HashSet<String>();
		IRow r = null;
		for (int i = 0; i < s; i++) {
			selectedIds.add(this.tblTenPrice.getCell(i, "id").getValue() + "");
		}

		UIContext uiContext = new UIContext();
		uiContext.put("selected", selectedIds);
		uiContext.put("parentUI", this);
		UIFactory.createUIFactory().create(SelectedTenPriceUI.class.getName(),
				uiContext).show();

	}
	
	@Override
	public void actionUpdateTenPrice_actionPerformed(ActionEvent e)
			throws Exception {
		// TODO Auto-generated method stub
		super.actionUpdateTenPrice_actionPerformed(e);
		if(!FDCBillStateEnum.AUDITTED.equals(editData.getState())){
			FDCMsgBox.showError("�˹��� �������޸� ����״̬�ĵ��ݡ�");
			return;
		}
		
	    //���ɻ�׼����¼
         CoreBaseCollection cols = new CoreBaseCollection();
         Set<String> tenPriceIdS = new HashSet<String>();
		 int size = this.tblTenPrice.getRowCount();
		 ITenancyPriceEntry f = TenancyPriceEntryFactory.getRemoteInstance();
		 FilterInfo fi = new FilterInfo();
		 fi.getFilterItems().add(new FilterItemInfo("parent.id",editData.getId().toString()));
		 f.delete(fi);
		 for(int i=0;i<size;i++){
			 TenancyPriceEntryInfo tenPriceEntry = (TenancyPriceEntryInfo) this.tblTenPrice.getRow(i).getUserObject();
				if(tenPriceEntry != null){
					TenPriceEntryInfo info = tenPriceEntry.getTenPrice();
					tenPriceEntry.setTenPrice(info);
					tenPriceEntry.setSeq(i+1);
					tenPriceEntry.setParent(this.editData);
					cols.add(tenPriceEntry);
					tenPriceIdS.add(info.getId().toString());
				}
		 }
		 f.save(cols);
		
	    //���»�׼���������������״̬
		 ITenPriceBaseLine tenPriceBaseLine = TenPriceBaseLineFactory.getRemoteInstance();
 		for(Iterator<String> it=tenPriceIdS.iterator();it.hasNext();){
 			String id = it.next();
 			tenPriceBaseLine.useTenPrice(BOSUuid.read(id));
 		}
 		
 		FDCMsgBox.showInfo("���³ɹ�.");
		
	}
}
