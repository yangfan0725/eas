/**
 * output package name
 */
package com.kingdee.eas.fdc.tenancy.client;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.*;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.metadata.IMetaDataPK;
import com.kingdee.bos.metadata.MetaDataPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SorterItemCollection;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox;
import com.kingdee.bos.ctrl.kdf.table.ICell;
import com.kingdee.bos.ctrl.kdf.table.IColumn;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTDefaultCellEditor;
import com.kingdee.bos.ctrl.kdf.table.KDTMergeManager;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectManager;
import com.kingdee.bos.ctrl.kdf.table.KDTStyleConstants;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent;
import com.kingdee.bos.ctrl.kdf.util.editor.ICellEditor;
import com.kingdee.bos.ctrl.kdf.util.style.Styles.HorizontalAlignment;
import com.kingdee.bos.ctrl.swing.KDContainer;
import com.kingdee.bos.ctrl.swing.KDDatePicker;
import com.kingdee.bos.ctrl.swing.KDFormattedTextField;
import com.kingdee.bos.ctrl.swing.KDSpinner;
import com.kingdee.bos.ctrl.swing.KDTextField;
import com.kingdee.bos.ctrl.swing.KDWorkButton;
import com.kingdee.bos.ctrl.swing.event.DataChangeEvent;
import com.kingdee.bos.dao.IObjectCollection;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.eas.base.attachment.BizobjectFacadeFactory;
import com.kingdee.eas.base.attachment.BoAttchAssoCollection;
import com.kingdee.eas.base.attachment.BoAttchAssoFactory;
import com.kingdee.eas.base.attachment.common.AttachmentClientManager;
import com.kingdee.eas.base.attachment.common.AttachmentManagerFactory;
import com.kingdee.eas.base.core.fi.gl.KDSpinnerCellEditor;
import com.kingdee.eas.base.message.MsgType;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.fdc.aimcost.ContractPhaseEnum;
import com.kingdee.eas.fdc.aimcost.CostIndexConfigCollection;
import com.kingdee.eas.fdc.aimcost.CostIndexConfigEntryInfo;
import com.kingdee.eas.fdc.aimcost.CostIndexConfigFactory;
import com.kingdee.eas.fdc.aimcost.CostIndexConfigInfo;
import com.kingdee.eas.fdc.aimcost.CostIndexEntryCollection;
import com.kingdee.eas.fdc.aimcost.CostIndexEntryInfo;
import com.kingdee.eas.fdc.aimcost.FieldTypeEnum;
import com.kingdee.eas.fdc.basecrm.CRMHelper;
import com.kingdee.eas.fdc.basecrm.client.CRMClientHelper;
import com.kingdee.eas.fdc.basedata.FDCBillInfo;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCDateHelper;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.basedata.MoneySysTypeEnum;
import com.kingdee.eas.fdc.basedata.client.FDCClientHelper;
import com.kingdee.eas.fdc.basedata.client.FDCClientUtils;
import com.kingdee.eas.fdc.basedata.client.FDCClientVerifyHelper;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.contract.FDCUtils;
import com.kingdee.eas.fdc.invite.InviteTypeInfo;
import com.kingdee.eas.fdc.sellhouse.DigitEnum;
import com.kingdee.eas.fdc.sellhouse.MoneyDefineFactory;
import com.kingdee.eas.fdc.sellhouse.MoneyDefineInfo;
import com.kingdee.eas.fdc.sellhouse.MoneyTypeEnum;
import com.kingdee.eas.fdc.sellhouse.RoomInfo;
import com.kingdee.eas.fdc.sellhouse.SHEComHelper;
import com.kingdee.eas.fdc.sellhouse.ToIntegerTypeEnum;
import com.kingdee.eas.fdc.sellhouse.client.CommerceHelper;
import com.kingdee.eas.fdc.sellhouse.client.SHEHelper;
import com.kingdee.eas.fdc.tenancy.ChargeDateTypeEnum;
import com.kingdee.eas.fdc.tenancy.DealAmountEntryInfo;
import com.kingdee.eas.fdc.tenancy.DepositDealBillFactory;
import com.kingdee.eas.fdc.tenancy.FirstLeaseTypeEnum;
import com.kingdee.eas.fdc.tenancy.IDealAmountInfo;
import com.kingdee.eas.fdc.tenancy.ITenancyEntryInfo;
import com.kingdee.eas.fdc.tenancy.ITenancyPayListInfo;
import com.kingdee.eas.fdc.tenancy.OtherBillEntryInfo;
import com.kingdee.eas.fdc.tenancy.OtherBillFactory;
import com.kingdee.eas.fdc.tenancy.OtherBillInfo;
import com.kingdee.eas.fdc.tenancy.RentCountTypeEnum;
import com.kingdee.eas.fdc.tenancy.RentFreeEntryCollection;
import com.kingdee.eas.fdc.tenancy.RentTypeEnum;
import com.kingdee.eas.fdc.tenancy.TenAttachResourceEntryCollection;
import com.kingdee.eas.fdc.tenancy.TenAttachResourceEntryInfo;
import com.kingdee.eas.fdc.tenancy.TenAttachResourcePayListEntryCollection;
import com.kingdee.eas.fdc.tenancy.TenAttachResourcePayListEntryInfo;
import com.kingdee.eas.fdc.tenancy.TenBillOtherPayCollection;
import com.kingdee.eas.fdc.tenancy.TenBillOtherPayInfo;
import com.kingdee.eas.fdc.tenancy.TenancyBillFactory;
import com.kingdee.eas.fdc.tenancy.TenancyBillInfo;
import com.kingdee.eas.fdc.tenancy.TenancyHelper;
import com.kingdee.eas.fdc.tenancy.TenancyRoomEntryCollection;
import com.kingdee.eas.fdc.tenancy.TenancyRoomEntryInfo;
import com.kingdee.eas.fdc.tenancy.TenancyRoomPayListEntryCollection;
import com.kingdee.eas.fdc.tenancy.TenancyRoomPayListEntryInfo;
import com.kingdee.eas.framework.*;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.MsgBox;
import com.kingdee.jdbc.rowset.IRowSet;
import com.kingdee.util.StringUtils;
import com.kingdee.util.UuidException;

/**
 * output class name
 */
public class OtherBillEditUI extends AbstractOtherBillEditUI implements TenancyBillConstant 
{
    private static final Logger logger = CoreUIObject.getLogger(OtherBillEditUI.class);
    private boolean isFreeContract=false;
    public OtherBillEditUI() throws Exception
    {
        super();
    }
	public void loadFields() {
		detachListeners();
		super.loadFields();
		setSaveActionStatus();
		
		SelectorItemCollection sels = new SelectorItemCollection();
		sels.add("*");
		sels.add("sellProject.*");
		sels.add("orgUnit.*");
		sels.add("tenancyAdviser.*");
		
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
		TenancyBillInfo tenBill;
		try {
			tenBill = TenancyBillFactory.getRemoteInstance().getTenancyBillInfo(new ObjectUuidPK(this.editData.getTenancyBill().getId()),sels);
			this.editData.setTenancyBill(tenBill);
		} catch (EASBizException e) {
			e.printStackTrace();
		} catch (BOSException e) {
			e.printStackTrace();
		}
		
		TenancyRoomEntryCollection tenancyRooms = this.editData.getTenancyBill().getTenancyRoomList();
		if(tenancyRooms.size()>0){
			TenancyRoomEntryInfo roomEntry=tenancyRooms.get(0);
			roomEntry.getPayList().clear();
			TenBillOtherPayCollection col=this.editData.getPayEntry();
			CRMHelper.sortCollection(col, "seq", true);
			BigDecimal returnAmt = FDCHelper.ZERO;
			int seq=0;
			for(int i=0;i<col.size();i++){
				TenancyRoomPayListEntryInfo entry=new TenancyRoomPayListEntryInfo();
				TenBillOtherPayInfo otherEntry=col.get(i);
				entry.setSeq(seq);
				entry.setAppDate(otherEntry.getAppDate());
				entry.setStartDate(otherEntry.getStartDate());
				entry.setEndDate(otherEntry.getEndDate());
				entry.setAppAmount(otherEntry.getAppAmount());
				entry.setLeaseSeq(otherEntry.getLeaseSeq());
				entry.setMoneyDefine(otherEntry.getMoneyDefine());
				returnAmt = otherEntry.getHasRefundmentAmount();
				entry.setActRevAmount(otherEntry.getActRevAmount().subtract(returnAmt));
				entry.setActRevDate(otherEntry.getActRevDate());
				entry.setIsUnPay(otherEntry.isIsUnPay());
				roomEntry.getRoomPayList().add(entry);
				
				seq++;
			}
		}
			
		List leaseList = getLeaseListFromView();
		// �����������		
		if (leaseList == null) {
			leaseList = new ArrayList();
		}
		updatePayList(tenancyRooms, new TenAttachResourceEntryCollection(), leaseList);// ���ɸ�����ϸ�б�
		
		
		for(int i=0;i<this.kdtEntry.getRowCount();i++){
			MoneyDefineInfo md=(MoneyDefineInfo) this.kdtEntry.getRow(i).getCell("moneyDefine").getValue();
			if(md!=null){
				this.kdtEntry.getRow(i).getCell("rate").setValue(md.getRate());
				BigDecimal div=FDCHelper.add(new BigDecimal(1), FDCHelper.divide(md.getRate(), new BigDecimal(100), 4, BigDecimal.ROUND_HALF_UP));
				this.kdtEntry.getRow(i).getCell("amountNoTax").setValue(FDCHelper.divide(this.kdtEntry.getRow(i).getCell("amount").getValue(), div, 2, BigDecimal.ROUND_HALF_UP));
			}else{
				this.kdtEntry.getRow(i).getCell("rate").setValue(null);
				this.kdtEntry.getRow(i).getCell("amountNoTax").setValue(null);
			}
		}
		
		CRMClientHelper.getFootRow(this.kdtEntry, new String[]{"price","workload","amount"});
		
		setOprtState(this.oprtState);
		attachListeners();
		setAuditButtonStatus(this.getOprtState());
	}
	public void storeFields()
    {
		TenancyRoomEntryCollection tenancyRooms = this.editData.getTenancyBill().getTenancyRoomList();
		this.editData.getPayEntry().clear();
		if(tenancyRooms.size()>0){
			CRMHelper.sortCollection(tenancyRooms, "seq", true);
			for(int i=0;i<tenancyRooms.get(0).getRoomPayList().size();i++){
				TenancyRoomPayListEntryInfo entry=tenancyRooms.get(0).getRoomPayList().get(i);
				TenBillOtherPayInfo otherEntry=new TenBillOtherPayInfo();
				otherEntry.setSeq(entry.getSeq());                      
				otherEntry.setAppDate(entry.getAppDate());
				otherEntry.setStartDate(entry.getStartDate());
				otherEntry.setEndDate(entry.getEndDate());
				otherEntry.setAppAmount(entry.getAppAmount());
				otherEntry.setLeaseSeq(entry.getLeaseSeq());
				otherEntry.setMoneyDefine(entry.getMoneyDefine());
				
				this.editData.getPayEntry().add(otherEntry);
			}
		}
        super.storeFields();
    }
	private ChangeListener spinLeaseTimeChangeListener = null;
	protected void attachListeners() {
		this.spinLeaseTime.addChangeListener(spinLeaseTimeChangeListener);

		this.addDataChangeListener(this.pkStartDate);
		this.addDataChangeListener(this.pkEndDate);
	}
	protected void detachListeners() {
		this.spinLeaseTime.removeChangeListener(spinLeaseTimeChangeListener);
		this.removeDataChangeListener(this.pkStartDate);
		this.removeDataChangeListener(this.pkEndDate);
	}
	protected ICoreBase getBizInterface() throws Exception {
		return OtherBillFactory.getRemoteInstance();
	}
	protected KDTable getDetailTable() {
		return this.kdtEntry;
	}
	protected KDTextField getNumberCtrl() {
		return this.txtNumber;
	}
	protected IObjectValue createNewData() {
		OtherBillInfo info=new OtherBillInfo();
		TenancyBillInfo ten = (TenancyBillInfo) this.getUIContext().get("tenancy");
		info.setId(BOSUuid.create(info.getBOSType()));
		info.setTenancyBill(ten);
		info.setOrgUnit(ten.getOrgUnit());
		info.setStartDate(ten.getStartDate());
		info.setEndDate(ten.getEndDate());
		info.setLeaseTime(ten.getLeaseTime());
		return info;
	}
	public SelectorItemCollection getSelectors() {
		SelectorItemCollection sels = super.getSelectors();
		sels.add("payEntry.*");
		sels.add("payEntry.moneyDefine.*");
		sels.add("state");
		sels.add("orgUnit.*");
		return sels;
	}
	public void onLoad() throws Exception {
		
		SpinnerNumberModel model = new SpinnerNumberModel(1, 1, 100000, 1);
		this.spinLeaseTime.setModel(model);

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
		
		this.menuTable1.setVisible(false);
		this.actionAddNew.setVisible(false);
		super.onLoad();
		
		KDWorkButton btnAddRowinfo = new KDWorkButton();
		KDWorkButton btnInsertRowinfo = new KDWorkButton();
		KDWorkButton btnDeleteRowinfo = new KDWorkButton();
		
		this.actionAddLine.setVisible(true);
		this.actionInsertLine.setVisible(true);
		this.actionRemoveLine.setVisible(true);
		this.actionAttachment.setVisible(true);
		
		this.btnAddLine.setVisible(false);
		this.btnInsertLine.setVisible(false);
		this.btnRemoveLine.setVisible(false);
		this.actionAddLine.putValue("SmallIcon", EASResource.getIcon("imgTbtn_addline"));
		btnAddRowinfo = (KDWorkButton)contEntry.add(this.actionAddLine);
		btnAddRowinfo.setText("������");
		btnAddRowinfo.setSize(new Dimension(140, 19));

		this.actionInsertLine.putValue("SmallIcon", EASResource.getIcon("imgTbtn_insert"));
		btnInsertRowinfo = (KDWorkButton) contEntry.add(this.actionInsertLine);
		btnInsertRowinfo.setText("������");
		btnInsertRowinfo.setSize(new Dimension(140, 19));

		this.actionRemoveLine.putValue("SmallIcon", EASResource.getIcon("imgTbtn_deleteline"));
		btnDeleteRowinfo = (KDWorkButton) contEntry.add(this.actionRemoveLine);
		btnDeleteRowinfo.setText("ɾ����");
		btnDeleteRowinfo.setSize(new Dimension(140, 19));
		
		this.kdtEntry.checkParsed();
		this.kdtEntry.setActiveCellStatus(KDTStyleConstants.ACTIVE_CELL_EDIT);

		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("sysType", MoneySysTypeEnum.TENANCYSYS_VALUE));
		filter.getFilterItems().add(new FilterItemInfo("moneyType", MoneyTypeEnum.LATEFEE_VALUE));
		filter.getFilterItems().add(new FilterItemInfo("moneyType", MoneyTypeEnum.COMMISSIONCHARGE_VALUE));
		filter.getFilterItems().add(new FilterItemInfo("moneyType", MoneyTypeEnum.FITMENTAMOUNT_VALUE));
		filter.getFilterItems().add(new FilterItemInfo("moneyType", MoneyTypeEnum.ELSEAMOUNT_VALUE));
		filter.getFilterItems().add(new FilterItemInfo("moneyType", MoneyTypeEnum.REPLACEFEE_VALUE));
		filter.getFilterItems().add(new FilterItemInfo("moneyType", MoneyTypeEnum.BREACHFEE_VALUE));
		filter.getFilterItems().add(new FilterItemInfo("moneyType", MoneyTypeEnum.DEPOSITAMOUNT_VALUE));
		filter.getFilterItems().add(new FilterItemInfo("name", "%����%",CompareType.NOTLIKE));
		filter.setMaskString("#0 and (#1 or #2 or #3 or #4 or #5 or #6 or #7) and #8");
		view.setFilter(filter);
		SorterItemCollection sort=new SorterItemCollection();
		sort.add(new SorterItemInfo("number"));
		view.setSorter(sort);
		this.kdtEntry.getColumn("moneyDefine").setEditor(CommerceHelper.getKDBizPromptBoxEditor("com.kingdee.eas.fdc.sellhouse.app.MoneyDefineQuery", view));

		KDFormattedTextField formattedTextField = new KDFormattedTextField(KDFormattedTextField.BIGDECIMAL_TYPE);
		formattedTextField.setSupportedEmpty(true);
		formattedTextField.setPrecision(2);
		formattedTextField.setNegatived(false);
		ICellEditor numberEditor = new KDTDefaultCellEditor(formattedTextField);
		this.kdtEntry.getColumn("amount").setEditor(numberEditor);
		this.kdtEntry.getColumn("amount").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
		this.kdtEntry.getColumn("amount").getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(2));
		
		this.kdtEntry.getColumn("price").setEditor(numberEditor);
		this.kdtEntry.getColumn("price").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
		this.kdtEntry.getColumn("price").getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(2));
		
		this.kdtEntry.getColumn("workload").setEditor(numberEditor);
		this.kdtEntry.getColumn("workload").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
		this.kdtEntry.getColumn("workload").getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(2));
		
		this.kdtEntry.getColumn("rate").getStyleAttributes().setHided(true);
		this.kdtEntry.getColumn("rate").setEditor(numberEditor);
		this.kdtEntry.getColumn("rate").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
		this.kdtEntry.getColumn("rate").getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(2));
		
		this.kdtEntry.getColumn("amountNoTax").getStyleAttributes().setHided(true);
		this.kdtEntry.getColumn("amountNoTax").setEditor(numberEditor);
		this.kdtEntry.getColumn("amountNoTax").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
		this.kdtEntry.getColumn("amountNoTax").getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(2));
		
		this.kdtEntry.getColumn("amount").setRequired(true);
        this.kdtEntry.getColumn("moneyDefine").setRequired(true);

		KDTextField textField = new KDTextField();
		textField.setMaxLength(80);
		KDTDefaultCellEditor txtEditor = new KDTDefaultCellEditor(textField);
		this.kdtEntry.getColumn("description").setEditor(txtEditor);
		
		this.actionAuditResult.setVisible(true);
		this.actionPre.setVisible(false);
		this.actionNext.setVisible(false);
		this.actionFirst.setVisible(false);
		this.actionLast.setVisible(false);
		
		FDCClientUtils.setRespDeptF7(this.prmtDept,null);
		
		this.actionPrint.setVisible(true);
		this.actionPrintPreview.setVisible(true);
		this.actionPrint.setEnabled(true);
		this.actionPrintPreview.setEnabled(true);
		
		this.contDes.setVisible(false);
	}
	public void setOprtState(String oprtType) {
		super.setOprtState(oprtType);
		if (oprtType.equals(OprtState.VIEW)) {
			this.lockUIForViewStatus();
			this.actionAddLine.setEnabled(false);
			this.actionInsertLine.setEnabled(false);
			this.actionRemoveLine.setEnabled(false);
		} else {
			this.unLockUI();
			this.actionAddLine.setEnabled(true);
			this.actionInsertLine.setEnabled(true);
			this.actionRemoveLine.setEnabled(true);
		}
	}
	protected IObjectValue createNewDetailData(KDTable table) {
		OtherBillEntryInfo entry=new OtherBillEntryInfo();
		return entry;
	}
	private String validateExistByName(String tenancyId) throws BOSException, SQLException{
		StringBuffer str = new StringBuffer();
		String sql = "select c.fname_l2 name ,org.fname_l2 companyName,sp.fname_l2 project,b.fnumber contractBill,to_char(b.fstartdate,'yyyy-mm-dd') startDate,to_char(b.fenddate "+
						" ,'yyyy-mm-dd') endDate "+
						" from t_ten_tenancycustomerentry e"+
						" inner join t_ten_tenancybill b on e.ftenancybillid = b.fid and b.ftenancystate = 'Executing' "+
						" left outer join t_she_fdccustomer c on e.ffdccustomerid = c.fid "+
						" left outer join t_she_sellproject sp on sp.fid = b.fsellprojectid "+
						" left outer join t_org_baseunit org on org.fid = sp.forgunitid "+
						" where c.fname_l2 in (select fname_l2 from t_she_fdccustomer where fid in (select ffdccustomerid from t_ten_tenancycustomerentry where ftenancybillid ='"+tenancyId+"')) and b.fid <> '"+tenancyId+"'";

		FDCSQLBuilder builder = new FDCSQLBuilder();
		builder.appendSql(sql);
		IRowSet rs = builder.executeQuery();
		Map<String,String> errorString = new HashMap<String,String>();
		String key = null;
		String errorMsg = null;
		while(rs.next()){
			key = rs.getString("name"); 
			errorMsg = " ��"+rs.getString("companyName")+"��"+rs.getString("project")+"��"+key+"�����޺�ͬ����ͬ���Ϊ��"+rs.getString("contractBill")+",��ʼʱ��Ϊ��"+rs.getString("startDate")+",����ʱ��Ϊ��"+rs.getString("endDate");
			if(errorString.containsKey(key)){
				errorString.put(key, errorString.get(key)+"\n"+errorMsg);
			}else{
				errorString.put(key, errorMsg);
			}
		}
		
		for(Iterator<String> it =errorString.keySet().iterator();it.hasNext();){
			key = it.next();
			str.append(errorString.get(key));
		}
//		resultStr.append("</table></html>");
		return str.toString();
	}
	
	
	public void actionSubmit_actionPerformed(ActionEvent e) throws Exception {
		BOSUuid tenancyId = editData.getTenancyBill().getId();
		String result = validateExistByName(tenancyId+"");
		if(!StringUtils.isEmpty(result)){
			FDCMsgBox.showDetailAndOK(this, "��ܰ��ʾ����ͬ�ͻ����ƺ�ͬ��ʾ������ʾ����ȷ������.", result.toString(),3);
		}
		super.actionSubmit_actionPerformed(e);
		this.setOprtState("VIEW");
		this.actionAudit.setVisible(true);
		this.actionAudit.setEnabled(true);
	}
	public void actionAudit_actionPerformed(ActionEvent e) throws Exception {
		super.actionAudit_actionPerformed(e);
		setAuditButtonStatus(STATUS_VIEW);
		this.actionSave.setEnabled(false);
	}
	public void actionRemove_actionPerformed(ActionEvent e) throws Exception {
		if(editData.getId()!=null){
			FDCClientUtils.checkBillInWorkflow(this, editData.getId().toString());
		}
		super.actionRemove_actionPerformed(e);
		handleCodingRule();
	}
	public void actionUnAudit_actionPerformed(ActionEvent e) throws Exception {
		super.actionUnAudit_actionPerformed(e);
		setAuditButtonStatus(this.getOprtState());
	}
	protected void kdtEntry_editStopped(KDTEditEvent e) throws Exception {
		isFreeContract=false;
		if(this.kdtEntry.getColumnKey(e.getColIndex()).equals("moneyDefine")
				||this.kdtEntry.getColumnKey(e.getColIndex()).equals("amount")){
			updatePayListInfo();
			MoneyDefineInfo md=(MoneyDefineInfo) this.kdtEntry.getRow(e.getRowIndex()).getCell("moneyDefine").getValue();
			if(md!=null){
				this.kdtEntry.getRow(e.getRowIndex()).getCell("rate").setValue(md.getRate());
				BigDecimal div=FDCHelper.add(new BigDecimal(1), FDCHelper.divide(md.getRate(), new BigDecimal(100), 4, BigDecimal.ROUND_HALF_UP));
				this.kdtEntry.getRow(e.getRowIndex()).getCell("amountNoTax").setValue(FDCHelper.divide(this.kdtEntry.getRow(e.getRowIndex()).getCell("amount").getValue(), div, 2, BigDecimal.ROUND_HALF_UP));
			}else{
				this.kdtEntry.getRow(e.getRowIndex()).getCell("rate").setValue(null);
				this.kdtEntry.getRow(e.getRowIndex()).getCell("amountNoTax").setValue(null);
			}
		}
		CRMClientHelper.getFootRow(this.kdtEntry, new String[]{"price","workload","amount"});
	}
	protected void pkStartDate_dataChanged(DataChangeEvent e) throws Exception {
		Date startDate = (Date) this.pkStartDate.getValue();
		Date tenStartDate = (Date) this.editData.getTenancyBill().getStartDate();
		
		if(startDate!=null&&tenStartDate!=null){
			startDate = FDCDateHelper.getDayBegin(startDate);
			tenStartDate = FDCDateHelper.getDayBegin(tenStartDate);
			if (tenStartDate.after(startDate)) {
				FDCMsgBox.showWarning(this, "��ʼ���ڲ����������޺�ͬ��ʼ���ڣ�");
				this.removeDataChangeListener(this.pkStartDate);
				this.pkStartDate.setValue(e.getOldValue());
				this.addDataChangeListener(this.pkStartDate);
				return;
			}
		}
		updatePayListInfo();
	}
	protected void pkEndDate_dataChanged(DataChangeEvent e) throws Exception {
		Date endDate = (Date) this.pkEndDate.getValue();
		Date tenEndDate = (Date) this.editData.getTenancyBill().getEndDate();
		
		if(endDate!=null&&tenEndDate!=null){
			endDate = FDCDateHelper.getDayBegin(endDate);
			tenEndDate = FDCDateHelper.getDayBegin(tenEndDate);
			if (endDate.after(tenEndDate)) {
				FDCMsgBox.showWarning(this, "�������ڲ��ܳ������޺�ͬ�������ڣ�");
				this.removeDataChangeListener(this.pkEndDate);
				this.pkEndDate.setValue(e.getOldValue());
				this.addDataChangeListener(this.pkEndDate);
				return;
			}
		}
		updatePayListInfo();
	}
	private void spinLeaseTime_stateChanged(ChangeEvent e) throws Exception {
		updatePayListInfo();
	}
	public void updatePayListInfo() throws BOSException
	{		
		RentCountTypeEnum rentCountType = this.editData.getTenancyBill().getRentCountType();
		int daysPerYear = this.editData.getTenancyBill().getDaysPerYear();
		boolean isToInteger = this.editData.getTenancyBill().isIsAutoToInteger();
		ToIntegerTypeEnum toIntegerType = this.editData.getTenancyBill().getToIntegerType();
		DigitEnum digit = this.editData.getTenancyBill().getDigit();
		//������� �����Է�������
		boolean isToIntegerFee = this.editData.getTenancyBill().isIsAutoToIntegerFee();
		ToIntegerTypeEnum toIntegerTypeFee = this.editData.getTenancyBill().getToIntegetTypeFee();
		DigitEnum digitFee = (DigitEnum) this.editData.getTenancyBill().getDigitFee();
		
		TenancyRoomEntryCollection tenancyRooms = this.editData.getTenancyBill().getTenancyRoomList();
		for(int i=0;i<tenancyRooms.size();i++){
			TenancyRoomEntryInfo entry=tenancyRooms.get(i);
			entry.getDealAmounts().clear();
			entry.getPayList().clear();
			for(int j=0;j<this.kdtEntry.getRowCount();j++){
				IRow row=this.kdtEntry.getRow(j);
				if(row.getCell("moneyDefine").getValue()==null) continue;
				DealAmountEntryInfo dealEntry=new DealAmountEntryInfo();
				dealEntry.setMoneyDefine((MoneyDefineInfo) row.getCell("moneyDefine").getValue());
				dealEntry.setStartDate((Date) this.pkStartDate.getValue());
				dealEntry.setEndDate((Date) this.pkEndDate.getValue());
				dealEntry.setAmount((BigDecimal) row.getCell("amount").getValue());
				dealEntry.setRentType(RentTypeEnum.RentByMonth);
				
				entry.getDealAmounts().add(dealEntry);
			}
		}
		List leaseList = getLeaseListFromView();
		// �����������		
		if (leaseList == null) {
			leaseList = new ArrayList();
		}
		if(!isFreeContract && (this.getOprtState().equals("ADDNEW") || this.getOprtState().equals("EDIT")))
		{
			fillTenRoomPayList(tenancyRooms, leaseList, new RentFreeEntryCollection(), rentCountType, daysPerYear, isToInteger, toIntegerType, digit,isToIntegerFee,toIntegerTypeFee,digitFee  );
		}else
		{				
			fillFreeConTenRoomPayList(tenancyRooms);
		}
		updatePayList(tenancyRooms, new TenAttachResourceEntryCollection(), leaseList);
	}
	protected void fillFreeConTenRoomPayList(TenancyRoomEntryCollection tenancyRooms)
	{
		if (tenancyRooms == null  ||  tenancyRooms.isEmpty()) {
			return;
		}
		// �Ȼ������Ӧ��������
		List appPayColKeys = getAppPayColKeys();
		BigDecimal oneEntryTotalRent = null;
		for(int i=0;i<tenancyRooms.size();i++)
		{
			 oneEntryTotalRent = FDCHelper.ZERO;
			
			TenancyRoomEntryInfo tenEntry = (TenancyRoomEntryInfo) tenancyRooms.getObject(i);
			TenancyRoomPayListEntryCollection newPayList = new TenancyRoomPayListEntryCollection();
			for(int j=0;j<tblPayList.getRowCount();j++)
			{
				IRow row = this.tblPayList.getRow(j);
				if(row.getUserObject()!=null)
				{
					ITenancyPayListInfo payListInfo =(ITenancyPayListInfo)row.getUserObject();
					Date appDate = (Date) row.getCell(C_PAYS_APP_PAY_DATE).getValue();
					if(appDate==null)return;
					appDate = FDCDateHelper.getDayBegin(appDate);
					Date startDate = (Date) row.getCell(C_PAYS_START_DATE).getValue();
					startDate = FDCDateHelper.getDayBegin(startDate);
					Date endDate = (Date) row.getCell(C_PAYS_END_DATE).getValue();
					endDate = FDCDateHelper.getDayBegin(endDate);
					int leaseSeq = payListInfo.getLeaseSeq();
					int seq = payListInfo.getSeq();
					MoneyDefineInfo money = payListInfo.getMoneyDefine();
					for (int m = 0; m < appPayColKeys.size(); m++) {
						String key = (String) appPayColKeys.get(m);
						ICell cell = row.getCell(key);
						BigDecimal appAmount = (BigDecimal) cell.getValue();
						TenancyRoomPayListEntryInfo tenPayInfo = (TenancyRoomPayListEntryInfo)row.getCell(key).getUserObject();
						TenancyRoomPayListEntryInfo tay  = new TenancyRoomPayListEntryInfo();
						if(tenEntry.getRoom()!=null && tenPayInfo!=null)
						{
							if(tenEntry.getRoom().getId().toString().equals(tenPayInfo.getTenRoom().getRoom().getId().toString()))
							{
								//����������Ҫ���� xin_wang 2010.11.24
								if(money!=null){
									if(MoneyTypeEnum.RentAmount.equals(money.getMoneyType())){
										oneEntryTotalRent =oneEntryTotalRent.add(appAmount);
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
	private void fillTenRoomPayList(TenancyRoomEntryCollection tenancyRooms, List leaseList, RentFreeEntryCollection rentFrees, RentCountTypeEnum rentCountType, int daysPerYear,
			boolean isToInteger, ToIntegerTypeEnum toIntegerType, DigitEnum digit,boolean isToIntegerFee, ToIntegerTypeEnum toIntegerTypeFee, DigitEnum digitFee) throws BOSException {
		try {//�����������  �������ڡ��ڶ���Ӧ������
			fillTenEntryPayList(tenancyRooms, TenancyRoomPayListEntryCollection.class, TenancyRoomPayListEntryInfo.class, null, null, leaseList, rentFrees, rentCountType, daysPerYear, 
					isToInteger, toIntegerType, digit,isToIntegerFee, toIntegerTypeFee, digitFee, this.editData.getTenancyBill().getChargeDateType(), this.editData.getTenancyBill().getChargeOffsetDays(),this.editData.getTenancyBill().getFristRevDate(),this.editData.getTenancyBill().getFixedDateFromMonth(),this.spinLeaseTime.getIntegerVlaue().intValue());
		} catch (InstantiationException e) {
			throw new BOSException(e);
		} catch (IllegalAccessException e) {
			throw new BOSException(e);
		}
	}
	public void fillTenEntryPayList(IObjectCollection tenEntrys, Class payColClazz, Class tenPayClazz, 
			MoneyDefineInfo depositMoney, MoneyDefineInfo rentMoney, 
			List leaseList, RentFreeEntryCollection rentFrees, RentCountTypeEnum rentCountType, int daysPerYear, 
			boolean isToInteger, ToIntegerTypeEnum toIntegerType, DigitEnum digit, boolean isToIntegerFee, ToIntegerTypeEnum toIntegerTypeFee, DigitEnum digitFee,ChargeDateTypeEnum chargeDateType, int chargeOffsetDays
			,Date pkTenancyDate,Date dPickFromMonth,int spinLeaseTime) throws BOSException, InstantiationException, IllegalAccessException {
		if (tenEntrys == null  ||  tenEntrys.isEmpty()) {
			return;
		}
		for (int i = 0; i < tenEntrys.size(); i++) {
			ITenancyEntryInfo tenEntry = (ITenancyEntryInfo) tenEntrys.getObject(i);
			
			IObjectCollection dealAmounts = tenEntry.getDealAmounts_();
			IObjectCollection payList = tenEntry.getPayList();
			
			IObjectCollection newPayList = (IObjectCollection) payColClazz.newInstance();
			// ͳ��һ�����䲻����Ѻ�����������ܺ�,�ύʱ��������ϸ���ܺ�Ӧ�͸�ֵ���. 
			BigDecimal oneEntryTotalRent = FDCHelper.ZERO;
			
			int seq = 0;
			for (int j = 0; j < leaseList.size(); j++) {
				List monthes = (List) leaseList.get(j);// �·ݼ���

				Date[] firstMonth = (Date[]) monthes.get(0);// �����ڵ�����
				Date[] lastMonth = (Date[]) monthes.get(monthes.size() - 1);// �����ڵ����1��
				
				// -------����÷�������ڵ����---
				BigDecimal leaseRent = getRentOfThisLease(monthes, rentMoney, dealAmounts, rentFrees, rentCountType, daysPerYear);
				//ȡ��
				leaseRent = SHEComHelper.getAmountAfterToInteger(leaseRent, isToInteger, toIntegerType, digit);
				// ---------------------
				
				if (leaseRent == null)
					leaseRent = FDCHelper.ZERO;
				
				int freeDaysOfThisLease = 0;// ���������������
				oneEntryTotalRent = oneEntryTotalRent.add(leaseRent);
				
				if (j == 0) {// ����ǵ�һ����,���ܻ��ж��� Ѻ�� �� ����������ǰ�Ѻ���¼���ڳɽ�����¼���Դ�����ȡ
					ITenancyPayListInfo depositPay = null;
					ITenancyPayListInfo firstPay = null;
					for(int m=0;m<dealAmounts.size();m++)
					{
						IDealAmountInfo dealAmount = (IDealAmountInfo) dealAmounts.getObject(m);
						if(MoneyTypeEnum.DepositAmount.equals(dealAmount.getMoneyDefine().getMoneyType()))
						{				
							depositPay = (ITenancyPayListInfo) tenPayClazz.newInstance();// Ѻ��
							depositPay.setTenEntry(tenEntry);
							setRoomPayParam(depositPay, dealAmount.getMoneyDefine(), dealAmount.getAmount(), j + 1, seq++,
									firstMonth[0], lastMonth[1], freeDaysOfThisLease, chargeDateType, chargeOffsetDays,pkTenancyDate,dPickFromMonth,spinLeaseTime,j);
							newPayList.addObject(depositPay);
						}
					}
//					firstPay = (ITenancyPayListInfo) tenPayClazz.newInstance();
//					firstPay.setTenEntry(tenEntry);
////					if (payList.size() > 1) {// ���ԭ�����и�����ϸ,��Ѻ����������϶�ͬʱ����
////						// ���ִ���ʽ��Ҫ��Ϊ�˱���ԭ�и�����ϸ��¼��IDֵ,ʹ�������ύ���ⵥʱ,
////						// �Ը���������ϸ��¼ִ�е���update����
////						depositPay = (ITenancyPayListInfo) payList.getObject(0);// Ѻ��
////						firstPay = (ITenancyPayListInfo) payList.getObject(1);// �������
////					} else if(payList.size() == 1){
////						depositPay = (ITenancyPayListInfo) payList.getObject(0);// Ѻ��
////						firstPay = (ITenancyPayListInfo) tenPayClazz.newInstance();
////						firstPay.setTenEntry(tenEntry);
////					} else{
////						depositPay = (ITenancyPayListInfo) tenPayClazz.newInstance();// Ѻ��
////						firstPay = (ITenancyPayListInfo) tenPayClazz.newInstance();
////						depositPay.setTenEntry(tenEntry);
////						firstPay.setTenEntry(tenEntry);
////					}
//
//					// if (!hasPayoff(roomDepositPay)) {//Ĭ��Ѻ��Ϊ�÷���ĳɽ����
////					setRoomPayParam(roomDepositPay, getDepositMoneyDefine(), getRentPerLease(tenRoomEntry.getDealRentType(), tenRoomEntry.getDealRoomRent(), this.spinLeaseTime.getIntegerVlaue().intValue()), j + 1, seq++,
////							firstMonth[0], lastMonth[1], freeDaysOfThisLease);
////					setRoomPayParam(depositPay, depositMoney, tenEntry.getDepositAmount(), j + 1, seq++,
////							firstMonth[0], lastMonth[1], freeDaysOfThisLease, chargeDateType, chargeOffsetDays);
////					 }
//					// if (!hasPayoff(roomFirstPay)) {
//					setRoomPayParam(firstPay, rentMoney, leaseRent, j + 1, seq++, firstMonth[0], lastMonth[1], freeDaysOfThisLease, chargeDateType, chargeOffsetDays,pkTenancyDate,dPickFromMonth,spinLeaseTime,j);
//					// }
//					
//					newPayList.addObject(firstPay);
				} else {
//					ITenancyPayListInfo payEntry = null;
//					if (j < payList.size() - 1) {
//						payEntry = (ITenancyPayListInfo) payList.getObject(j + 1);
//					} else {
//						payEntry = (ITenancyPayListInfo) tenPayClazz.newInstance();
//						payEntry.setTenEntry(tenEntry);
//					}
//					// ����Ѿ��������ϸ,���޸ĸ���������ϸ
//					// if (!hasPayoff(roomPayEntry)) {
//					setRoomPayParam(payEntry, rentMoney, leaseRent, j + 1, seq++, firstMonth[0], lastMonth[1], freeDaysOfThisLease, chargeDateType, chargeOffsetDays,null,dPickFromMonth,spinLeaseTime,j);
//					// }
//					newPayList.addObject(payEntry);
				}
				
				//������������Է��ã�����Ҫ���������Է��õ�Ӧ����ϸ
				MoneyDefineInfo money = new MoneyDefineInfo();
				for(int k=0; k<dealAmounts.size(); k++){
					IDealAmountInfo dealAmount = (IDealAmountInfo) dealAmounts.getObject(k);
					if(!MoneyTypeEnum.DepositAmount.equals(dealAmount.getMoneyDefine().getMoneyType())  
							&&  dealAmount.getAmount() != null 
							//&&  dealAmount.getAmount().compareTo(FDCHelper.ZERO) > 0 ��������жϣ���Ȼ��������ɵ�Ӧ����ϸ��λ xin_wang 2010.11.22
							){
						if(dealAmount.getMoneyDefine().equals(money))
						{
							continue;
						}else
						{
							money = dealAmount.getMoneyDefine();
						}
						ITenancyPayListInfo pPay = (ITenancyPayListInfo) tenPayClazz.newInstance();
						BigDecimal amount = getRentOfThisLease(monthes, dealAmount.getMoneyDefine(), dealAmounts, rentFrees, rentCountType, daysPerYear);
						//ȡ�� eric_wang 2010.08.25
						amount = SHEComHelper.getAmountAfterToInteger(amount, isToIntegerFee, toIntegerTypeFee, digitFee);
//						BigDecimal amount = TenancyHelper.getRentBetweenDate(firstMonth[0], lastMonth[1], dealAmount.getRentType(), dealAmount.getAmount());
						//add by pu_zhang 2010-10-20
						if(j==0){// ����ǵ�һ���ڣ���д���޺�ͬ����
							setRoomPayParam(pPay, dealAmount.getMoneyDefine(), amount, j + 1, seq++, firstMonth[0], lastMonth[1], 0, chargeDateType, chargeOffsetDays,pkTenancyDate,dPickFromMonth,spinLeaseTime,j);
						}else{// ����Ƿǵ�һ���ڣ����޺�ͬ����Ϊ��
							setRoomPayParam(pPay, dealAmount.getMoneyDefine(), amount, j + 1, seq++, firstMonth[0], lastMonth[1], 0, chargeDateType, chargeOffsetDays,null,dPickFromMonth,spinLeaseTime,j);
						}
						newPayList.addObject(pPay);	
					}
				}
			}
			payList.clear();
			payList.addObjectCollection(newPayList);
			tenEntry.setTotalRent(oneEntryTotalRent);
		}
	}
	private void setRoomPayParam(ITenancyPayListInfo payEntry, MoneyDefineInfo moneyDefine, BigDecimal leaseRent, 
			int leaseSeq, int seq, Date startDate, Date endDate, int freeDaysOfThisLease, ChargeDateTypeEnum chargeDateType, int chargeOffsetDays,Date pkTenancyDate,Date dPickFromMonth,int spinLeaseTime,int j) throws BOSException {
		// roomPayEntry.setCurrency(currency);
		// roomPayEntry.setMoneyDefine(moneyDefine);
		payEntry.setAppAmount(leaseRent);
		payEntry.setLeaseSeq(leaseSeq);
		payEntry.setSeq(seq);
		payEntry.setStartDate(startDate);
		payEntry.setEndDate(endDate);
		//���ֶ����������ˡ�
//		roomPayEntry.setFreeDays(freeDaysOfThisLease);

		payEntry.setMoneyDefine(moneyDefine);

		// ����Ӧ������
		Date appPayDate = null;
		if(pkTenancyDate!=null){//��һ�����õ�ʱ�����޺�ͬ��ҵ�����ڣ�Ӧ������Ϊ���޺�ͬ��ҵ�����ڣ�
			appPayDate=pkTenancyDate;
		}else{//�ǵ�һ��,����ƫ��ȥ����Ӧ������
			if (ChargeDateTypeEnum.BeforeStartDate.equals(chargeDateType)) {
				appPayDate = TenancyHelper.addCalendar(startDate, Calendar.DATE, -chargeOffsetDays);
			} else if (ChargeDateTypeEnum.AfterStartDate.equals(chargeDateType)) {
				appPayDate = TenancyHelper.addCalendar(startDate, Calendar.DATE, chargeOffsetDays);
			} else if (ChargeDateTypeEnum.BeforeEndDate.equals(chargeDateType)) {
				appPayDate = TenancyHelper.addCalendar(endDate, Calendar.DATE, -chargeOffsetDays);
			} else if (ChargeDateTypeEnum.AfterEndDate.equals(chargeDateType)) {
				appPayDate = TenancyHelper.addCalendar(endDate, Calendar.DATE, chargeOffsetDays);
			} else if (ChargeDateTypeEnum.FixedDate.equals(chargeDateType)&&dPickFromMonth!=null) {
				appPayDate = TenancyHelper.addCalendar(dPickFromMonth, Calendar.MONTH, spinLeaseTime*(j-1));
			} else {
				appPayDate = startDate;
			}
		}
		payEntry.setAppPayDate(appPayDate);
	}
	private BigDecimal getRentOfThisLease(List monthes, MoneyDefineInfo rentMoney, IObjectCollection dealAmounts, RentFreeEntryCollection rentFrees, RentCountTypeEnum rentCountType, int daysPerYear) throws BOSException {
		Date[] firstMonth = (Date[]) monthes.get(0);
		Date[] lastMonth = (Date[]) monthes.get(monthes.size() - 1);
		return TenancyHelper.getRentBetweenDate(rentMoney, firstMonth[0], lastMonth[1], dealAmounts, rentFrees, rentCountType, daysPerYear);
	}
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
	private List getLeaseListFromView() {
		Date startDate = (Date) this.pkStartDate.getValue();
		Date endDate = (Date) this.pkEndDate.getValue();
		FirstLeaseTypeEnum firstLeaseType = FirstLeaseTypeEnum.WholeFirstLease;
		Date firstLeaseEndDate = (Date) this.pkStartDate.getValue();
		int leaseTime = this.spinLeaseTime.getIntegerVlaue().intValue();
		return TenancyHelper.getLeaseList(startDate, endDate, firstLeaseType, firstLeaseEndDate, leaseTime);
	}
	private void updatePayList(TenancyRoomEntryCollection tenancyRooms, TenAttachResourceEntryCollection tenAttachReses, List leaseList) {
		updatePayListColumn(tenancyRooms, tenAttachReses);

		IRow headRow = this.tblPayList.addHeadRow();
		headRow.getCell(C_PAYS_MONEY_DEFINE).setValue("��������");
		headRow.getCell(C_PAYS_LEASE_SEQ).setValue("�������");
		headRow.getCell(C_PAYS_START_DATE).setValue("��ʼ����");
		headRow.getCell(C_PAYS_END_DATE).setValue("��������");
		headRow.getCell(C_PAYS_APP_PAY_DATE).setValue("Ӧ������");

		for (int i = 0; i < tenancyRooms.size(); i++) {
			String roomNum = tenancyRooms.get(i).getRoom().getName();
			headRow.getCell(PREFIX_C_PAYS_ROOM + i + POSTFIX_C_PAYS_APP_AMOUNT).setValue(roomNum);
			headRow.getCell(PREFIX_C_PAYS_ROOM + i + POSTFIX_C_PAYS_ACT_AMOUNT).setValue(roomNum);
			headRow.getCell(PREFIX_C_PAYS_ROOM + i + POSTFIX_C_PAYS_ACT_PAY_DATE).setValue(roomNum);
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
			headRow.getCell(PREFIX_C_PAYS_ROOM + i + POSTFIX_C_PAYS_APP_AMOUNT).setValue("Ӧ�ս��");
			headRow.getCell(PREFIX_C_PAYS_ROOM + i + POSTFIX_C_PAYS_ACT_AMOUNT).setValue("ʵ�ս��");
			headRow.getCell(PREFIX_C_PAYS_ROOM + i + POSTFIX_C_PAYS_ACT_PAY_DATE).setValue("ʵ������");
		}
		headRow.getCell(C_PAYS_TOTAL_APP_PAY).setValue("Ӧ�պϼ�");
		headRow.getCell(C_PAYS_TOTAL_ACT_PAY).setValue("ʵ�պϼ�");

		tblPayList.getHeadMergeManager().mergeBlock(0, 0, 1, 7 + (tenancyRooms.size() + tenAttachReses.size()) * 3 - 1, KDTMergeManager.FREE_MERGE);

		//		if (leaseList == null || leaseList.isEmpty()) {
		//			return;
		//		}
		if (tenancyRooms.size() == 0 && tenAttachReses.size() == 0) {
			return;
		}

		//		Map leaseMap = parsePayListByLease();

		// ע��:����ͳһ���´���,��������͸�����Դ�ĸ�����ϸӦ�ö��ǰ����������˳�������.

		//ȡһ������������������
		ITenancyEntryInfo tenObj = tenancyRooms.get(0);
		if(tenObj == null){
			tenObj = tenAttachReses.get(0);
		}

		if(tenObj == null){
			return;
		}

		IObjectCollection oneObjPays = tenObj.getPayList();

		Map tmpMap = new TreeMap();
		for(int i=0; i<oneObjPays.size(); i++){
			ITenancyPayListInfo pay = (ITenancyPayListInfo) oneObjPays.getObject(i);
			int leaseSeq = pay.getLeaseSeq();

			if(tmpMap.get(new Integer(leaseSeq)) == null){
				List paysGroupByLease = new ArrayList();
				paysGroupByLease.add(pay);
				tmpMap.put(new Integer(leaseSeq), paysGroupByLease);
			}else{
				List tmpPays = (List)tmpMap.get(new Integer(leaseSeq));
				tmpPays.add(pay);
				Collections.sort(tmpPays, new Comparator(){
					public int compare(Object arg0, Object arg1) {
						ITenancyPayListInfo pay0 = (ITenancyPayListInfo)arg0;
						ITenancyPayListInfo pay1 = (ITenancyPayListInfo)arg1;
						if(pay0 == null  ||  pay1 == null){
							return 0;
						}
						return pay0.getSeq() - pay1.getSeq();
					}
				});
			}						
		}

		this.getUIContext().put("tmpMap", tmpMap);
		for(Iterator itor = tmpMap.keySet().iterator(); itor.hasNext(); ){
			Integer key = (Integer)itor.next();
			List pays = (List) tmpMap.get(key);
			if(pays.size() == 1){
				ITenancyPayListInfo tPay = (ITenancyPayListInfo) pays.get(0);
				IRow row = this.tblPayList.addRow();
				row.getStyleAttributes().setBackground(new java.awt.Color(246, 246, 191));
				
				if(tPay instanceof TenancyRoomPayListEntryInfo&&((TenancyRoomPayListEntryInfo)tPay).isIsUnPay()){
					for(int i=0;i<this.tblPayList.getColumnCount();i++){
						if(!this.tblPayList.getColumnKey(i).equals(C_PAYS_LEASE_SEQ)){
							row.getCell(i).getStyleAttributes().setBackground(Color.CYAN);
						}
					}
				}
				row.setTreeLevel(0);
				row.setUserObject(tPay);

				row.getCell(C_PAYS_MONEY_DEFINE).setValue(tPay.getMoneyDefine());
				row.getCell(C_PAYS_LEASE_SEQ).setValue(new Integer(tPay.getLeaseSeq()));
				row.getCell(C_PAYS_START_DATE).setValue(tPay.getStartDate());
				row.getCell(C_PAYS_END_DATE).setValue(tPay.getEndDate());
				row.getCell(C_PAYS_APP_PAY_DATE).setValue(tPay.getAppPayDate());
			}else{
				IRow unionRow = this.tblPayList.addRow();
				unionRow.setTreeLevel(0);
				unionRow.getStyleAttributes().setBackground(new java.awt.Color(246, 246, 191));

				StringBuffer moneyDes = new StringBuffer();
				for(int i=0; i<pays.size(); i++){
					ITenancyPayListInfo tPay = (ITenancyPayListInfo) pays.get(i);
					IRow entryRow = this.tblPayList.addRow();
					if(tPay instanceof TenancyRoomPayListEntryInfo&&((TenancyRoomPayListEntryInfo)tPay).isIsUnPay()){
						for(int j=0;j<this.tblPayList.getColumnCount();j++){
							if(!this.tblPayList.getColumnKey(i).equals(C_PAYS_LEASE_SEQ)){
								entryRow.getCell(j).getStyleAttributes().setBackground(Color.CYAN);
							}
						}
					}
					entryRow.setTreeLevel(1);
					entryRow.setUserObject(tPay);

					entryRow.getCell(C_PAYS_MONEY_DEFINE).setValue("  " + tPay.getMoneyDefine());
					entryRow.getCell(C_PAYS_LEASE_SEQ).setValue(key);
					entryRow.getCell(C_PAYS_START_DATE).setValue(tPay.getStartDate());
					entryRow.getCell(C_PAYS_END_DATE).setValue(tPay.getEndDate());
					entryRow.getCell(C_PAYS_APP_PAY_DATE).setValue(tPay.getAppPayDate());

					if(i != 0){
						moneyDes.append("��");
					}
					moneyDes.append(tPay.getMoneyDefine());

					if(i == 0){
						unionRow.getCell(C_PAYS_START_DATE).setValue(tPay.getStartDate());
						unionRow.getCell(C_PAYS_END_DATE).setValue(tPay.getEndDate());
						unionRow.getCell(C_PAYS_APP_PAY_DATE).setValue(tPay.getAppPayDate());
					}
				}

				unionRow.getCell(C_PAYS_MONEY_DEFINE).setValue(moneyDes.toString());
				unionRow.getCell(C_PAYS_LEASE_SEQ).setValue(key);
			}
		}

		Map unionRows = new HashMap();
		IRow currentUnionRow = null;
		for(int i=0; i<this.tblPayList.getRowCount(); i++){
			IRow row = this.tblPayList.getRow(i);

			//���ںϼ��У�����ͳһ����
			if(row.getUserObject() == null){
				unionRows.put(row, new ArrayList());
				currentUnionRow = row;
				continue;
			}else{
				if(row.getTreeLevel() == 1){
					List entryRows = (List) unionRows.get(currentUnionRow);
					entryRows.add(row);
				}else{
					currentUnionRow = null;
				}

				ITenancyPayListInfo onePay = (ITenancyPayListInfo) row.getUserObject();
				int seq = onePay.getSeq();

				BigDecimal totalAppPayThisLeas = FDCHelper.ZERO;
				BigDecimal totalActPayThisLeas = FDCHelper.ZERO;
				for (int j = 0; j < tenancyRooms.size(); j++) {
					TenancyRoomEntryInfo tenancyRoom = tenancyRooms.get(j);
					TenancyRoomPayListEntryCollection tmpPayList = tenancyRoom.getRoomPayList();
					TenancyRoomPayListEntryInfo tmpPayEntry = tmpPayList.get(seq);
					if (tmpPayEntry == null) {
						continue;
					}

					row.getCell(PREFIX_C_PAYS_ROOM + j + POSTFIX_C_PAYS_APP_AMOUNT).setValue(tmpPayEntry.getAppAmount());
					// tmpPayEntryֻ�洢��tenancyRoom��IDֵ,��������tenancyRoom����,
					// ������verifyPayList()ʱ������޷���ļ����ֶ�
					tmpPayEntry.setTenRoom(tenancyRoom);
					row.getCell(PREFIX_C_PAYS_ROOM + j + POSTFIX_C_PAYS_APP_AMOUNT).setUserObject(tmpPayEntry);// Ӧ���������޸�
					// .
					// �������ñ�����storePayList
					// (
					// )
					// �б���Ӧ�����
					row.getCell(PREFIX_C_PAYS_ROOM + j + POSTFIX_C_PAYS_ACT_AMOUNT).setValue(tmpPayEntry.getAllRemainAmount());
					row.getCell(PREFIX_C_PAYS_ROOM + j + POSTFIX_C_PAYS_ACT_PAY_DATE).setValue(tmpPayEntry.getActPayDate());

					totalAppPayThisLeas = totalAppPayThisLeas.add(tmpPayEntry.getAppAmount() == null ? FDCHelper.ZERO : tmpPayEntry.getAppAmount());
					//ʵ�պϼ�Ҳȡʣ���������ȡʵ�ս��
					totalActPayThisLeas = totalActPayThisLeas.add(tmpPayEntry.getAllRemainAmount() == null ? FDCHelper.ZERO : tmpPayEntry.getAllRemainAmount());
				}

				row.getCell(C_PAYS_TOTAL_APP_PAY).setValue(totalAppPayThisLeas);
				row.getCell(C_PAYS_TOTAL_ACT_PAY).setValue(totalActPayThisLeas);
			}
		}

		//�ϼ���
		for(Iterator itor = unionRows.keySet().iterator(); itor.hasNext(); ){
			IRow unionRow = (IRow) itor.next();
			List entryRows = (List) unionRows.get(unionRow);

			for(int i=0; i<this.tblPayList.getColumnCount(); i++){
				String colKey = this.tblPayList.getColumn(i).getKey();
				if(colKey.equals(C_PAYS_TOTAL_APP_PAY) || colKey.equals(C_PAYS_TOTAL_ACT_PAY)
						|| colKey.endsWith(POSTFIX_C_PAYS_APP_AMOUNT) || colKey.endsWith(POSTFIX_C_PAYS_ACT_AMOUNT)){
					BigDecimal unionAmount = FDCHelper.ZERO;
					for(int j=0; j<entryRows.size(); j++){
						IRow entryRow = (IRow) entryRows.get(j);
						BigDecimal entryAmount = (BigDecimal) entryRow.getCell(colKey).getValue();
						if(entryAmount != null){
							unionAmount = unionAmount.add(entryAmount);
						}
					}
					unionRow.getCell(colKey).setValue(unionAmount);
				}
			}
		}

		this.tblPayList.getMergeManager().mergeBlock(0, 0, this.tblPayList.getRowCount() - 1, 0, KDTMergeManager.FREE_ROW_MERGE);
	}
	private void updatePayListColumn(TenancyRoomEntryCollection tenancyRooms, TenAttachResourceEntryCollection tenAttachReses) {
		// �����¼��Ϣ����¼��tenancyRoomInfo�ĸ����¼������
		this.tblPayList.removeRows();
		this.tblPayList.removeColumns();
		this.tblPayList.getTreeColumn().setDepth(2);

		IColumn column = this.tblPayList.addColumn();
		column.setKey(C_PAYS_LEASE_SEQ);
		column.setEditor(TenancyClientHelper.createTxtCellEditor(80, false));

		column = this.tblPayList.addColumn();
		column.setKey(C_PAYS_MONEY_DEFINE);
		column.setEditor(TenancyClientHelper.createTxtCellEditor(80, false));

		column = this.tblPayList.addColumn();
		column.setKey(C_PAYS_START_DATE);
		column.getStyleAttributes().setNumberFormat(DATE_FORMAT_STR);

		column = this.tblPayList.addColumn();
		column.setKey(C_PAYS_END_DATE);
		column.getStyleAttributes().setNumberFormat(DATE_FORMAT_STR);

		column = this.tblPayList.addColumn();
		column.setKey(C_PAYS_APP_PAY_DATE);
		column.getStyleAttributes().setNumberFormat(DATE_FORMAT_STR);

		for (int i = 0; i < tenancyRooms.size(); i++) {
			column = this.tblPayList.addColumn();

			StringBuffer key = new StringBuffer();
			key.append(PREFIX_C_PAYS_ROOM).append(i).append(POSTFIX_C_PAYS_APP_AMOUNT);
			column.setKey(key.toString());
			column.setEditor(TenancyClientHelper.createFormattedCellEditor(true));
			column.getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
			column.getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(2));

			column = this.tblPayList.addColumn();
			key = new StringBuffer();
			key.append(PREFIX_C_PAYS_ROOM).append(i).append(POSTFIX_C_PAYS_ACT_AMOUNT);
			column.setKey(key.toString());
			column.getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
			column.getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(2));
			column.getStyleAttributes().setLocked(true);

			column = this.tblPayList.addColumn();
			key = new StringBuffer();
			key.append(PREFIX_C_PAYS_ROOM).append(i).append(POSTFIX_C_PAYS_ACT_PAY_DATE);
			column.setKey(key.toString());
			column.getStyleAttributes().setNumberFormat(DATE_FORMAT_STR);
			column.getStyleAttributes().setLocked(true);
		}
		column = this.tblPayList.addColumn();
		column.setKey(C_PAYS_TOTAL_APP_PAY);
		column.getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
		column.getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(2));
		column.getStyleAttributes().setLocked(true);
		column = this.tblPayList.addColumn();
		column.setKey(C_PAYS_TOTAL_ACT_PAY);
		column.getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
		column.getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(2));
		column.getStyleAttributes().setLocked(true);
	}
	protected void tblPayList_editStopped(KDTEditEvent e) throws Exception {
		isFreeContract=true;
		int colIndex = e.getColIndex();
		int rowIndex = e.getRowIndex();
		IColumn col = tblPayList.getColumn(colIndex);
		IRow row = tblPayList.getRow(rowIndex);

		String key = col.getKey();
		//����ʱ���޸ĸı���ϸ��ʱ��
		if(C_PAYS_START_DATE.equals(key) || C_PAYS_END_DATE.equals(key) || C_PAYS_APP_PAY_DATE.equals(key))
		{
			Date date = new Date();
			if (e.getValue() != null) {
				date = (Date) e.getValue();
			}			
			int leaseSeq = ((Integer)row.getCell(C_PAYS_LEASE_SEQ).getValue()).intValue();
			for(int i=0;i<tblPayList.getRowCount();i++)
			{
				IRow row2 = tblPayList.getRow(i);
				int seq = ((Integer)row2.getCell(C_PAYS_LEASE_SEQ).getValue()).intValue();
				if(leaseSeq == seq)
				{
					if(row2.getUserObject()!=null)
					{
						ITenancyPayListInfo iTeninfo = (ITenancyPayListInfo)row2.getUserObject();
						if(C_PAYS_START_DATE.equals(key))
						{
							iTeninfo.setStartDate(date);
						}else if(C_PAYS_END_DATE.equals(key))
						{
							iTeninfo.setEndDate(date);

						}else{
							iTeninfo.setAppPayDate(date);							
						}
						row2.setUserObject(iTeninfo);
						row2.getCell(key).setValue(date);
					}
				}
			}
		}
		//��ϸ�е�Ӧ�������ĸı���ܺϼƺ͸�����ϼ�
		if (key.startsWith(PREFIX_C_PAYS_ROOM) && key.endsWith(POSTFIX_C_PAYS_APP_AMOUNT)) {
			BigDecimal oldAppAmount = FDCHelper.ZERO;
			BigDecimal newAppAmount = FDCHelper.ZERO;
			if (e.getOldValue() != null) {
				oldAppAmount = (BigDecimal) e.getOldValue();
			}
			if (e.getValue() != null) {
				newAppAmount = (BigDecimal) e.getValue();
			}
			BigDecimal difAmount = newAppAmount.subtract(oldAppAmount);
			int leaseSeq = ((Integer)row.getCell(C_PAYS_LEASE_SEQ).getValue()).intValue();
			for(int i=0;i<tblPayList.getRowCount();i++)
			{
				IRow row2 = tblPayList.getRow(i);
				int seq = ((Integer)row2.getCell(C_PAYS_LEASE_SEQ).getValue()).intValue();
				if(leaseSeq==seq)
				{
					if(row2.getUserObject()==null)
					{
						BigDecimal huizong = (BigDecimal)row2.getCell(key).getValue();
						if (huizong == null) {
							huizong = FDCHelper.ZERO;
						}
						row2.getCell(key).setValue(huizong.add(difAmount));
					}
				}
			}

			for(int i=0;i<tblPayList.getRowCount();i++)
			{
				IRow row2 = tblPayList.getRow(i);
				int seq = ((Integer)row2.getCell(C_PAYS_LEASE_SEQ).getValue()).intValue();
				if(leaseSeq==seq)
				{
					if(row2.getUserObject()==null)
					{
						BigDecimal oldTotalAppPayAmount = (BigDecimal) row2.getCell(C_PAYS_TOTAL_APP_PAY).getValue();
						if (oldTotalAppPayAmount == null) {
							oldTotalAppPayAmount = FDCHelper.ZERO;
						}

						row2.getCell(C_PAYS_TOTAL_APP_PAY).setValue(oldTotalAppPayAmount.add(difAmount));
					}
				}
			}

			BigDecimal oldTotalAppPayAmount = (BigDecimal) row.getCell(C_PAYS_TOTAL_APP_PAY).getValue();
			if (oldTotalAppPayAmount == null) {
				oldTotalAppPayAmount = FDCHelper.ZERO;
			}

			row.getCell(C_PAYS_TOTAL_APP_PAY).setValue(oldTotalAppPayAmount.add(difAmount));

			// ������޸ĵ�Ѻ���������,��Ҫ�޸ĺ�ͬ��Ϣ�еĵĽ��
			//updateDepositAndFirstPayRent();
		} else if (key.startsWith(PREFIX_C_PAYS_ATTACH_RES)) {
			// TenAttachResourcePayListEntryInfo pay =
			// (TenAttachResourcePayListEntryInfo) row.getUserObject();
			if (key.endsWith(POSTFIX_C_PAYS_APP_AMOUNT)) {
				// pay.setAppAmount((BigDecimal) row.getCell(key));
			}
		}
		 updatePayListInfo();
	}
	protected void verifyInputForSubmint() throws Exception {
		if(getNumberCtrl().isEnabled()) {
			FDCClientVerifyHelper.verifyEmpty(this, getNumberCtrl());
		}
		FDCClientVerifyHelper.verifyEmpty(this, this.txtName);
		FDCClientVerifyHelper.verifyEmpty(this, this.prmtDept);
		FDCClientVerifyHelper.verifyEmpty(this, this.pkStartDate);
		FDCClientVerifyHelper.verifyEmpty(this, this.pkEndDate);
		
		Date startDate = (Date) this.pkStartDate.getValue();
		Date endDate = (Date) this.pkEndDate.getValue();
		
		startDate = FDCDateHelper.getDayBegin(startDate);
		endDate = FDCDateHelper.getDayBegin(endDate);
		if (startDate.after(endDate)) {
			FDCMsgBox.showWarning(this, "��ʼ���ڲ��ܳ��ڽ������ڣ�");
			SysUtil.abort();
		}
		
		for(int i=0;i<this.kdtEntry.getRowCount();i++){
			IRow row = this.kdtEntry.getRow(i);
			if(row.getCell("moneyDefine").getValue()==null){
				FDCMsgBox.showWarning(this,"�������Ʋ���Ϊ�գ�");
				this.kdtEntry.getEditManager().editCellAt(row.getRowIndex(), this.kdtEntry.getColumnIndex("moneyDefine"));
				SysUtil.abort();
			}
			if(row.getCell("amount").getValue()==null){
				FDCMsgBox.showWarning(this,"��ȡ����Ϊ�գ�");
				this.kdtEntry.getEditManager().editCellAt(row.getRowIndex(), this.kdtEntry.getColumnIndex("amount"));
				SysUtil.abort();
			}
		}
		FilterInfo filter = new FilterInfo();
		
		filter.getFilterItems().add(new FilterItemInfo("boID" , this.editData.getId().toString()));
		if(!BoAttchAssoFactory.getRemoteInstance().exists(filter)){
			FDCMsgBox.showWarning(this,"�����ϴ�������");
			SysUtil.abort();
		}
	}
	public void actionAttachment_actionPerformed(ActionEvent e)throws Exception{
		 AttachmentClientManager acm = AttachmentManagerFactory.getClientManager();
		 String boID = getSelectBOID();
		 if(boID == null)
			 return;
		 boolean isEdit = false;
		 if("ADDNEW".equals(getOprtState()) || "EDIT".equals(getOprtState())){
			 isEdit = true;
       }
		 acm.showAttachmentListUIByBoID(boID, this, isEdit);
   }
	 public boolean destroyWindow() {
		 boolean b = super.destroyWindow();
		 if(b){
			 try {
				 if(!OtherBillFactory.getRemoteInstance().exists(new ObjectUuidPK(this.editData.getId().toString()))){
					 deleteAttachment(this.editData.getId().toString());
				 }
  		} catch (Exception e) {
  			e.printStackTrace();
  		}
      }
      return b;
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
		OtherBillDataProvider data = new OtherBillDataProvider(
				editData.getString("id"),editData.getTenancyBill().getId().toString(),getTDQueryPK());
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
		OtherBillDataProvider data = new OtherBillDataProvider(
				editData.getString("id"),editData.getTenancyBill().getId().toString(),getTDQueryPK());
		com.kingdee.bos.ctrl.report.forapp.kdnote.client.KDNoteHelper appHlp = new com.kingdee.bos.ctrl.report.forapp.kdnote.client.KDNoteHelper();
		appHlp.printPreview(getTDFileName(), data, javax.swing.SwingUtilities
				.getWindowAncestor(this));
	}
	protected String getTDFileName() {
		return "/bim/fdc/tenancy/OtherBill";
	}

	protected IMetaDataPK getTDQueryPK() {
		return new MetaDataPK(
		"com.kingdee.eas.fdc.tenancy.app.OtherBillPrintQuery");
	}
}