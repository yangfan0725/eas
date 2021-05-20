/**
 * output package name
 */
package com.kingdee.eas.fdc.sellhouse.client;

import java.awt.Rectangle; 
import java.awt.event.ActionEvent;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.util.KDTableUtil;
import com.kingdee.bos.ctrl.swing.KDLayout;
import com.kingdee.bos.ctrl.swing.KDScrollPane;
import com.kingdee.bos.ctrl.swing.KDTextField;
import com.kingdee.bos.ctrl.swing.event.DataChangeEvent;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.framework.cache.CacheServiceFactory;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemCollection;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.UIException;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.base.codingrule.CodingRuleManagerFactory;
import com.kingdee.eas.base.codingrule.ICodingRuleManager;
import com.kingdee.eas.base.permission.UserInfo;
import com.kingdee.eas.base.uiframe.client.UIFactoryHelper;
import com.kingdee.eas.basedata.org.client.OrgInnerUtils;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basedata.FDCBillFacadeFactory;
import com.kingdee.eas.fdc.basedata.FDCBillInfo;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.basedata.FDCSQLFacadeFactory;
import com.kingdee.eas.fdc.basedata.MoneySysTypeEnum;
import com.kingdee.eas.fdc.basedata.app.FDCBillFacadeControllerBean;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.insider.IInsider;
import com.kingdee.eas.fdc.insider.InsiderCollection;
import com.kingdee.eas.fdc.insider.InsiderFactory;
import com.kingdee.eas.fdc.insider.InsiderInfo;
import com.kingdee.eas.fdc.insider.client.CertificateUtil;
import com.kingdee.eas.fdc.insider.client.IntegralNewApplication2EditUI;
import com.kingdee.eas.fdc.sellhouse.AgioEntryCollection;
import com.kingdee.eas.fdc.sellhouse.BuildingInfo;
import com.kingdee.eas.fdc.sellhouse.BuildingUnitInfo;
import com.kingdee.eas.fdc.sellhouse.CertificateInfo;
import com.kingdee.eas.fdc.sellhouse.FDCCustomerFactory;
import com.kingdee.eas.fdc.sellhouse.FDCCustomerInfo;
import com.kingdee.eas.fdc.sellhouse.FunctionSetting;
import com.kingdee.eas.fdc.sellhouse.HousePropertyEnum;
import com.kingdee.eas.fdc.sellhouse.IPurCustomer;
import com.kingdee.eas.fdc.sellhouse.IPurchase;
import com.kingdee.eas.fdc.sellhouse.IRoomSignContract;
import com.kingdee.eas.fdc.sellhouse.IntentionRoomsEntryCollection;
import com.kingdee.eas.fdc.sellhouse.IntentionRoomsEntryFactory;
import com.kingdee.eas.fdc.sellhouse.IntentionRoomsEntryInfo;
import com.kingdee.eas.fdc.sellhouse.MoneyTypeEnum;
import com.kingdee.eas.fdc.sellhouse.PurCustomerFactory;
import com.kingdee.eas.fdc.sellhouse.PurCustomerInfo;
import com.kingdee.eas.fdc.sellhouse.PurchaseCollection;
import com.kingdee.eas.fdc.sellhouse.PurchaseCustomerInfoCollection;
import com.kingdee.eas.fdc.sellhouse.PurchaseCustomerInfoFactory;
import com.kingdee.eas.fdc.sellhouse.PurchaseCustomerInfoInfo;
import com.kingdee.eas.fdc.sellhouse.PurchaseFactory;
import com.kingdee.eas.fdc.sellhouse.PurchaseInfo;
import com.kingdee.eas.fdc.sellhouse.PurchasePayListEntryCollection;
import com.kingdee.eas.fdc.sellhouse.PurchasePayListEntryFactory;
import com.kingdee.eas.fdc.sellhouse.PurchaseRoomAttachmentEntryCollection;
import com.kingdee.eas.fdc.sellhouse.PurchaseRoomAttachmentEntryFactory;
import com.kingdee.eas.fdc.sellhouse.PurchaseStateEnum;
import com.kingdee.eas.fdc.sellhouse.RoomDisplaySetting;
import com.kingdee.eas.fdc.sellhouse.RoomFactory;
import com.kingdee.eas.fdc.sellhouse.RoomInfo;
import com.kingdee.eas.fdc.sellhouse.RoomSellStateEnum;
import com.kingdee.eas.fdc.sellhouse.RoomSignContractCollection;
import com.kingdee.eas.fdc.sellhouse.RoomSignContractFactory;
import com.kingdee.eas.fdc.sellhouse.RoomSignContractInfo;
import com.kingdee.eas.fdc.sellhouse.SellProjectFactory;
import com.kingdee.eas.fdc.sellhouse.SellProjectInfo;
import com.kingdee.eas.fdc.sellhouse.SellTypeEnum;
import com.kingdee.eas.fdc.sellhouse.SincerityPurchaseInfo;
import com.kingdee.eas.fdc.sellhouse.TrackRecordEnum;
import com.kingdee.eas.fi.cas.ReceivingBillCollection;
import com.kingdee.eas.fi.cas.ReceivingBillFactory;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.mm.basedata.OprStatusEnum;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.app.ContextUtil;
import com.kingdee.eas.util.client.MsgBox;
import com.kingdee.jdbc.rowset.IRowSet;
import com.kingdee.util.DateTimeUtils;
import com.kingdee.util.StringUtils;
import com.kingdee.util.UuidException;

/**
 * output class name
 */
public class RoomSignContractEditUI extends AbstractRoomSignContractEditUI {
	private static final String FINDVIEW_OPRSTATE = "FINDVIEW";

	private static final Logger logger = CoreUIObject
			.getLogger(RoomSignContractEditUI.class);

	PurchaseEditUI plUI = null;
//	private SellProjectInfo sellProject = null;
	private RoomDisplaySetting setting = new RoomDisplaySetting();
	
	UserInfo userInfo = SysContext.getSysContext().getCurrentUserInfo();
	/**
	 * output class constructor
	 */
	public RoomSignContractEditUI() throws Exception {
		super();
	}

	public void onShow() throws Exception {
		super.onShow();
		if (this.txtContractNumber.isEnabled()) {
			this.txtContractNumber.requestFocus();
		} else {
			this.txtContractName.requestFocus();
		}
		
		this.actionAudit.setVisible(true);
		this.actionAudit.setEnabled(true);
		this.actionUnAudit.setVisible(true);
		this.actionUnAudit.setEnabled(true);
	}

	/**
	 * output storeFields method
	 */
	public void storeFields() {
		RoomSignContractInfo sign = this.editData;
		RoomInfo room = (RoomInfo) this.txtRoomNumber.getUserObject();
		sign.setRoom(room);
		plUI.storeFields();
		sign.setPurchase(this.plUI.editData);
		
		super.storeFields();
	}

	public void checkContractNum() throws BOSException{
		if(this.txtContractNum.getText()!=null && !"".equals(this.txtContractNum.getText())){
			try{
				String existSql = "where contractNumber = '"+this.txtContractNum.getText()+"' ";
				if(this.editData.getId()!=null)
					existSql += "and id!='"+this.editData.getId()+"' ";
				if(RoomSignContractFactory.getRemoteInstance().exists(existSql)){
					MsgBox.showWarning("�ú�ͬ���Ѵ��ڣ�");
					this.abort();
				}
			}catch(Exception e){
				e.printStackTrace();
			}
		}
	}
	
	public void actionSave_actionPerformed(ActionEvent e) throws Exception {
		if(this.txtContractNumber.isEditable() && this.txtContractNumber.isEditable() && StringUtils.isEmpty(this.txtContractNumber.getText())){
			MsgBox.showWarning("��ͬ���벻��Ϊ��!");
			this.abort();
		}
		if(OprtState.EDIT.equals(this.getOprtState()) &&  !this.txtContractNum.getText().equals(this.editData.getContractNumber())){
			checkContractNum();
		}
		RoomInfo room = (RoomInfo) this.txtRoomNumber.getUserObject();
		if (room == null) {
			MsgBox.showInfo("��ѡ�񷿼�!");
			this.abort();
		}
		if(plUI == null){//����Ҳֻ�����ݴ��������߼��²����ܳ���
			MsgBox.showInfo("û����Ӧ���Ϲ���!");
			this.abort();
		}
		
		boolean debug2 =getIsImmediacySign(room);
		Date purchaseDate = DateTimeUtils.truncateDate((Date)this.plUI.pkPurchaseDate.getValue());
		Date roomSignDate = DateTimeUtils.truncateDate((Date)this.pkSignDate.getValue());
		if(purchaseDate!=null && roomSignDate!=null){
			if(!debug2 && this.editData.getPurchase() != null){
				if(roomSignDate.compareTo(purchaseDate) == -1 ){
					MsgBox.showWarning(this,"ǩԼ�������Ϲ���������֮ǰ���������ύ��������¼�룡");
					SysUtil.abort();
				}
			}else{
				this.plUI.pkPurchaseDate.setValue(roomSignDate);
				this.plUI.pkSignDate.setValue(roomSignDate);
			}
		}
		
		Date signDate = (Date) this.pkSignDate.getValue();
		Date joinDate = (Date) this.pkSignJoinDate.getValue();
		if (signDate != null && joinDate != null) {
			if (signDate.after(joinDate)) {
				MsgBox.showInfo("Լ��������ڲ���С�ڻ����ǩԼ���ڣ�");
				this.abort();
			}
		}
		
		plUI.verifySubmit();
		
		
		addTheCustomerAuthority(room);
		
		verifyBalance();
		boolean debug = false;
		try
		{
			String id = room.getBuilding().getSellProject().getId().toString();
			RoomDisplaySetting setting= new RoomDisplaySetting();
			HashMap functionSetMap = (HashMap) setting.getFunctionSetMap();
			FunctionSetting funcSet = (FunctionSetting)functionSetMap.get(id);
			if(funcSet == null)	{
				logger.warn("û�и�������Ŀ��Ӧ��ǩԼ���ò�����");
			}else	{
				debug = funcSet.getIsSignGathering().booleanValue();
			}
		}
		catch(Exception e1)
		{
			logger.warn("��ȡ�÷����Ӧ��������Ŀ��ǩԼ���ò��������˴���Ĭ�Ͻ���ǩԼ�տ���ƣ�");
		}
		if(debug)
		{
			this.checkReceiveMoney(room);
		}
		String oldOprt = this.getOprtState();
		
		addPurchaseWhenAddNew(e,plUI);
		
		if(getIsImmediacySign(room) && this.editData.getPurchase()==null){
			MsgBox.showWarning("�Ϲ���δ�ύ��");
			SysUtil.abort();
		}
		
		this.setOprtState("EDIT");
		saveCustomerInfo();
		super.actionSave_actionPerformed(e);
		
		
		
		addCommerceTrackRecordWhenAddNew(oldOprt,plUI.editData);
	}
	
	
	//�����Щ�����ݣ��Ѿ��Ϲ������ͻ����֤δ¼���ڴ�ǩԼ�����޷�¼��
	private void addTheCustomerAuthority(RoomInfo room) throws Exception {
		//�ͻ������֤�������¼��
		if(room.getLastPurchase()!=null){
			PurchaseCustomerInfoCollection purCustColl = PurchaseCustomerInfoFactory.getRemoteInstance().getPurchaseCustomerInfoCollection(
					"select customer.name,customer.certificateName,customer.certificateNumber " +
					"where head.id='"+room.getLastPurchase().getId()+"' and (customer.certificateNumber is null or customer.certificateNumber ='')");
			if(purCustColl.size()>0) {
				MsgBox.showInfo("�ͻ�'"+purCustColl.get(0).getCustomer().getName()+"'��֤�����벻��Ϊ��,�����룡");
				CustomerCardUI.addTheCustomerAuthority(purCustColl.get(0).getCustomer(), this);
				this.abort();
			}
		}
	}
	
	
	
	
	/**
	 * �ж��Ƿ���ǩԼ��
	 * @param room
	 */
	private void checkIsHaveSignContract(RoomInfo room)
	{
		PurchaseInfo purchase = room.getLastPurchase();
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		view.setFilter(filter);
		if(purchase!=null){
			filter.getFilterItems().add(
					new FilterItemInfo("purchase.id", purchase.getId().toString()));
		}
		filter.getFilterItems().add(
				new FilterItemInfo("room.id", room.getId().toString()));
		filter.getFilterItems().add(
				new FilterItemInfo("isBlankOut", Boolean.FALSE));
		RoomSignContractCollection signs = null;
		try
		{
			signs = RoomSignContractFactory.getRemoteInstance()
					.getRoomSignContractCollection(view);
		} catch (BOSException e1)
		{
			super.handUIException(e1);
		}
		if (signs.size() > 0)
		{
			MsgBox.showInfo("�÷����Ѿ�������Ч״̬�µ�ǩԼ��!");
			this.abort();
		}
	}
	
	
	/**
	 * ���������ж��Ƿ��������룬û���������жϣ��Ƿ�����ȫ��
	 * @throws EASBizException
	 * @throws BOSException
	 */
	private void checkReceive() throws EASBizException, BOSException
	{
		if (this.isHaveFirstAmount())
		{
			if (!this.isCompleteReceiveFirstAmount())
			{
				MsgBox.showInfo("�÷���û���������ڿ����ǩԼ��");
				this.abort();
			}
		} else
		{
			if (!this.isHaveCompleteReceive())
			{
				MsgBox.showInfo("û�н���ȫ�����ǩԼ��");
				this.abort();
			}
		}
	}
	/**
	 * �а��ҿ����ж��Ƿ����밴�ҿ�֮ǰ�Ŀ��û�а��ҿ����ж��Ƿ�����ƻ��Կ�������п�
	 * @throws BOSException 
	 * @throws EASBizException 
	 *
	 */
	private void checkLoanAmount() throws BOSException, EASBizException
	{
		if(this.isHaveLoan())
		{
			if(!this.isHaveReceiveAllBeforeLoanAmount())
			{
				MsgBox.showInfo("�÷��䰴�ҿ�֮ǰ�Ŀ���δ���룬����ǩԼ��");
				this.abort();
			}
		}
		else
		{
			/*if(!this.isHaveCompleteReceiveByPlan())
			{
				MsgBox.showInfo("�ƻ��Կ���δ���룬����ǩԼ��");
				this.abort();
			}*/
		}
	}
	/**
	 * �жϳ��˰��ҿ�͹�����Ŀ����Ƿ�����
	 */
	private void checkReceiveMoney(RoomInfo room)
	{
		verifyBalance();
		if(!this.isHaveCompleteReceiveByPlan(room))
		{
			MsgBox.showWarning("�÷���δ���� ���ҿ� �� ������ ֮��ļƻ��Կ������ǩԼ��");
			this.abort();
		}
	}
	
	
	protected void verifyInput(ActionEvent e) throws Exception {
		verifyBalance();
		return;
	}
	
	/**
	 * �����ѽ�����ڼ䣬���������ǩԼ���޸�
	 * */
	private void verifyBalance() {
		Date bizDate = (Date) this.pkSignDate.getValue();
		if(bizDate==null){
			MsgBox.showInfo("ǩԼ���ڲ���Ϊ�ա�");
			this.abort();
		}
		Date balanceEndDate = null;
        RoomInfo room = (RoomInfo) this.txtRoomNumber.getUserObject();
		SelectorItemCollection selColl = new SelectorItemCollection();
		selColl.add("building.sellProject.*");		
		try {
			room = RoomFactory.getRemoteInstance().getRoomInfo(new ObjectUuidPK(BOSUuid.read(room.getId().toString())),selColl);
		} catch (EASBizException e) {
			handleException(e);
			e.printStackTrace();
		} catch (BOSException e) {
			handleException(e);
			e.printStackTrace();
		} catch (UuidException e) {
			handleException(e);
			e.printStackTrace();
		}	
		SellProjectInfo sellProject= room.getBuilding().getSellProject();
		if(sellProject != null){
			try {
				balanceEndDate = getLastEndDate(sellProject.getId().toString());
			} catch (Exception e) {
				handleException(e);
				e.printStackTrace();
			}
			SHEHelper.verifyBalance(bizDate, balanceEndDate);
		}
	}
	/***
	 * ��������Ŀȡ�ϴν���Ľ�ֹ���ڡ�
	 * **/

	private Date getLastEndDate(String sellProjectId) throws Exception {
		Date lastEndDate = null;
		FDCSQLBuilder detailBuilder = new FDCSQLBuilder();
		detailBuilder.appendSql("select FBalanceEndDate from T_SHE_SellProject where FID = ?");
		detailBuilder.addParam(sellProjectId);
		try {
			IRowSet detailSet = detailBuilder.executeQuery();
			if (detailSet.next()) {
				lastEndDate = detailSet.getDate("FBalanceEndDate");
			}
		} catch (Exception e) {
			handleException(e);
			e.printStackTrace();
		}
		return lastEndDate;
	}
	
	public void actionSubmit_actionPerformed(ActionEvent e) throws Exception {
		if(this.txtContractNumber.isVisible() && this.txtContractNumber.isEditable() && StringUtils.isEmpty(this.txtContractNumber.getText())){
			MsgBox.showWarning("��ͬ���벻��Ϊ��!");
			this.abort();
		}
		if(OprtState.EDIT.equals(this.getOprtState()) && !this.txtContractNum.getText().equals(this.editData.getContractNumber())){
			checkContractNum();
		}
		
		RoomInfo room = (RoomInfo) this.txtRoomNumber.getUserObject();
		
		if (room == null) {
			MsgBox.showInfo("��ѡ�񷿼�!");
			this.abort();
		}
		if(plUI == null){
			MsgBox.showInfo("û����Ӧ���Ϲ���!");
			this.abort();
		}
		
		plUI.verifySubmit();
		
		SelectorItemCollection selColl = new SelectorItemCollection();
		selColl.add("building.sellProject.id");
		selColl.add("lastPurchase.payListEntry.moneyDefine.*");
		selColl.add("lastPurchase.payListEntry.*");
		
		room = RoomFactory.getRemoteInstance().getRoomInfo(new ObjectUuidPK(BOSUuid.read(room.getId().toString())),selColl);
		
		boolean debug2 =getIsImmediacySign(room);
		Date purchaseDate = DateTimeUtils.truncateDate((Date)this.plUI.pkPurchaseDate.getValue());
		Date roomSignDate = DateTimeUtils.truncateDate((Date)this.pkSignDate.getValue());
		if(purchaseDate!=null && roomSignDate!=null){
			if(this.editData.getPurchase() != null){
				if(roomSignDate.compareTo(purchaseDate) == -1 ){
					MsgBox.showWarning(this,"ǩԼ�������Ϲ���������֮ǰ���������ύ��������¼�룡");
					SysUtil.abort();
				}
			}else{
				this.plUI.pkPurchaseDate.setValue(roomSignDate);
				this.plUI.pkSignDate.setValue(roomSignDate);
			}
		}
		
		Date signDate = (Date) this.pkSignDate.getValue();
		Date joinDate = (Date) this.pkSignJoinDate.getValue();
		if (signDate != null && joinDate != null) {
			if (signDate.after(joinDate)) {
				MsgBox.showInfo("������ڲ�������ǩԼ����!");
				this.abort();
			}
		}
		//PurchaseInfo purchase = room.getLastPurchase();
		
//		plUI.verifySubmit();
		
		addTheCustomerAuthority(room);
		
		verifyBalance();
		boolean debug = false;
		try
		{
			String id = room.getBuilding().getSellProject().getId().toString();
			RoomDisplaySetting setting= new RoomDisplaySetting();
			HashMap functionSetMap = (HashMap) setting.getFunctionSetMap();
			FunctionSetting funcSet = (FunctionSetting)functionSetMap.get(id);
			if(funcSet == null)	{
				logger.warn("û�и�������Ŀ��Ӧ��ǩԼ���ò�����");
			}else	{
				debug = funcSet.getIsSignGathering().booleanValue();
			}
		}
		catch(Exception e1)
		{
			logger.warn("��ȡ�÷����Ӧ��������Ŀ��ǩԼ���ò��������˴���Ĭ�Ͻ���ǩԼ�տ���ƣ�");
		}
		if(debug)
		{
			this.checkReceiveMoney(room);
		}
		
		String oldOprt = this.oprtState;
	
		addPurchaseWhenAddNew(e,plUI);
		
		
		if(getIsImmediacySign(room) && this.editData.getPurchase()==null){
			MsgBox.showWarning("�Ϲ���δ�ύ��");
			SysUtil.abort();
		}
		
		this.setOprtState(OprtState.VIEW);		//����Ȼ����ȥ����ȥ�����ύʱ����ʾ��"�÷����Ѿ�������Ч״̬�µ�ǩԼ��!"��
       
		saveCustomerInfo();
		super.actionSubmit_actionPerformed(e);
		
		this.setOprtState(OprtState.VIEW);
		toggleModifyCustBtn();
		addCommerceTrackRecordWhenAddNew(oldOprt,plUI.editData);
		//by tim_gao ����ģ�������µ��ý������� 2011-2-28
//		this.setPurchaseTab();
	}

	/**
	 * �ͻ���Ϣ��д��fdccustomer��
	 * @throws BOSException
	 * @throws EASBizException
	 */
	private void saveCustomerInfo() throws BOSException, EASBizException {
		plUI.storeCustomerInfo();
		CoreBaseCollection col = new CoreBaseCollection();
		if(plUI.editData.getCustomerInfo() != null){
			for(int i=0; i<plUI.editData.getCustomerInfo().size(); i++){
				FDCCustomerInfo customer = plUI.editData.getCustomerInfo().get(i).getCustomer();
				if(customer == null || customer.getId() == null){
					logger.error("�����ͻ������ǿյ�");
					this.abort();
				}
				col.add(customer);
			}
		}
		FDCCustomerFactory.getRemoteInstance().updateBatchData(col);
		//��Ҫsave purCustomer
		CoreBaseCollection col2 = new CoreBaseCollection();
		String id = null;
		if(plUI.editData.getPurCustomer() != null){
			for(int i=0; i<plUI.editData.getPurCustomer().size(); i++){
				PurCustomerInfo customer = (PurCustomerInfo)(plUI.editData.getPurCustomer().get(i));
				if(i<1){
					id = customer.getParent()!=null?customer.getParent().getId().toString():null;
				}
				if(customer == null){
					logger.error("�����ͻ������ǿյ�");
					this.abort();
				}
				col2.add(customer);
			}
		}
		IPurCustomer purCustomerEx= PurCustomerFactory.getRemoteInstance();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("parent.id",id));
		purCustomerEx.delete(filter);
		purCustomerEx.addnew(col2);
	}

	
	public void addPurchaseWhenAddNew(ActionEvent e,PurchaseEditUI plUI) throws Exception{
		if(getOprtState().equals(this.STATUS_ADDNEW)
				&& (this.editData.getPurchase()==null 
				|| (this.editData.getPurchase().getPurchaseState().equals(PurchaseStateEnum.PrePurchaseApply)
						|| this.editData.getPurchase().getPurchaseState().equals(PurchaseStateEnum.PrePurchaseCheck)))){
			BuildingInfo buildingInfo = (BuildingInfo) this.getUIContext().get("building");
			BuildingUnitInfo buldUnit = (BuildingUnitInfo) this.getUIContext().get("buildUnit");
			// ���ȴӽ����ȡ���䣬��Ϊ�ղ�ѡ�񷿼�
			// add by jiyu_guan
			RoomInfo room = (RoomInfo) plUI.txtRoomNumber.getUserObject();
			if (room == null) {
				room = RoomSelectUI.showOneRoomSelectUI(this, buildingInfo,
						buldUnit, MoneySysTypeEnum.SalehouseSys);
			}
			if (room == null) {
				// ��ѡ�����ж��������񣬷�ֹ��̨����
				SysUtil.abort();
			}
			
			if(getIsImmediacySign(room)){
				if(plUI.editData.getId()==null){
					BOSUuid uuid=BOSUuid.create(plUI.editData.getBOSType());
					plUI.editData.setId(uuid);
				}
				Date signDate = DateTimeUtils.truncateDate((Date)this.pkSignDate.getValue());
				plUI.pkPurchaseDate.setValue(signDate);
				plUI.pkSignDate.setValue(signDate);
				
				//�ύ�Ϲ���
				plUI.verifySubmit();
				plUI.setOprtState("EDIT");
				plUI.storeFields();
				SincerityPurchaseInfo sin = (SincerityPurchaseInfo) this.getUIContext().get("sincerity");
				if (sin != null) {// �����Ϲ�ת�Ϲ�
					plUI.editData.setSincerityPurchase(sin);
					plUI.editData.setPrePurchaseAmount(sin.getSincerityAmount());
					plUI.editData.setEarnestBase(sin.getSincerityAmount());
				}
				PurchaseFactory.getRemoteInstance().immediacySave(plUI.editData);
				plUI.addCommerceTrackRecord(OprtState.ADDNEW, TrackRecordEnum.PurchaseApp);
				CacheServiceFactory.getInstance().discardType(new PurchaseCustomerInfoInfo().getBOSType());
				plUI.loadCustomerState();
				//�����Ϲ���
				PurchaseFactory.getRemoteInstance().audit(plUI.editData.getId());
				plUI.editData = PurchaseFactory.getRemoteInstance().getPurchaseInfo(new ObjectUuidPK(plUI.editData.getId()),plUI.getSelectors());
				this.editData.setIsImmediacySign(true);
				this.editData.setPurchase(plUI.editData);
			}
		}
	}
	
	/**
	 * �ж��Ƿ�����ֱ��ǩԼ 
	 * @param room
	 * @return
	 */
	public boolean getIsImmediacySign(RoomInfo room){
		boolean debug = false;
		RoomInfo room2 = null;
		try {
			room2 = SHEHelper.queryRoomInfo(room.getId().toString());
		} catch (EASBizException e) {
			e.printStackTrace();
		} catch (BOSException e) {
			e.printStackTrace();
		} catch (UuidException e) {
			e.printStackTrace();
		}
		if(room2!=null){
			String id = room2.getBuilding().getSellProject().getId().toString();
			RoomDisplaySetting setting= new RoomDisplaySetting();
			HashMap functionSetMap = (HashMap) setting.getFunctionSetMap();
			FunctionSetting funcSet = (FunctionSetting)functionSetMap.get(id);
			if(funcSet == null)	{
				debug = true ;
			}else{
				if(funcSet.getIsActGathering().booleanValue()
					|| funcSet.getIsSignGathering().booleanValue()){
					debug = false ;
				}else{
					debug = true ;
				}
			}
		}
		return debug;
	}

	
	//�̻�����ҵ�񵥾ݰ���,ǩԼ��  �����ύ�������̻�����
	private void addCommerceTrackRecordWhenAddNew(String oprtState,PurchaseInfo purchase) {
		if(oprtState.equals(OprtState.ADDNEW))  {
			  if(purchase!=null) {
				List list = new ArrayList();				
				for(int i=0;i<purchase.getCustomerInfo().size();i++) {
					PurchaseCustomerInfoInfo purCust = (PurchaseCustomerInfoInfo)purchase.getCustomerInfo().get(i);
					if(purCust.getCustomer()!=null) list.add(purCust.getCustomer());
				}
				CommerceHelper.addCommerceTrackRecord(this,list,purchase.getSalesman(),TrackRecordEnum.SignApp,this.editData.getNumber(),this.editData.getId()==null?null:this.editData.getId().toString());
			  }
		}		
	}
	
	
	
	public void loadFields() {
		super.loadFields();
		RoomSignContractInfo sign = this.editData;
		if (sign.getRoom() != null) {
			this.txtRoomNumber.setUserObject(sign.getRoom());
			this.txtRoomNumber.setText(sign.getRoom().getDisplayName());
		}
		try {
			setPurchaseTab();
		} catch (EASBizException e) {
			e.printStackTrace();
		} catch (BOSException e) {
			e.printStackTrace();
		} catch (UuidException e) {
			e.printStackTrace();
		}
		if (!this.getOprtState().equals("ADDNEW")) {
			this.btnSelectRoom.setEnabled(false);
		}
		if(!sign.isIsOnRecord()){
			this.pkOnRecordDate.setEnabled(false);
		}
		if(OprtState.EDIT.equals(this.getOprtState()) && this.plUI!=null){
//			setPurchaseEditUI();
		}
	}

	public SelectorItemCollection getSelectors() {
		SelectorItemCollection sels = super.getSelectors();
		sels.add("room.*");
		sels.add("*");
		return sels;
	}
	KDScrollPane scrollPane;
	private void setPurchaseTab() throws EASBizException, BOSException, UuidException {
		RoomInfo room = (RoomInfo) this.txtRoomNumber.getUserObject();
		if (room == null) {
			return;
		}
		// ȥ��ԭ�ȵ�ҳǩ add by jiyu_guan
		this.tabPurchase.removeAll();
		this.remove(tabPurchase);
		//ÿ���ύ�����������·��� �̶����棬����֮ǰû��ɾ���Ľ��������»�
		//����ÿ����Ҫ��֮ǰ�Ŀؼ�ɾ�����ٷ�
		if (scrollPane != null) {
			scrollPane.removeAll();
			this.remove(scrollPane);
		}
		scrollPane = new KDScrollPane();
		//by tim_gao �޸�Ϊͼ�����¿ؼ��Ĵ�С���������ê���Ĵ�С 2011-03-28
		scrollPane.setBounds(new Rectangle(10, 189, 991, 506));
		//ԭ�ȵ�ͼ���С
        this.add(scrollPane, new KDLayout.Constraints(10, 189, 991, 506, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));    
       //by tim_gao ������������췽������ΪscrollPane.getBounds()�����ê���仯��ͼ���Խ��Խ��,��Ҫ��ê���ı�׼�����
 //      this.add(scrollPane, new KDLayout.Constraints( KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT, scrollPane.getBounds()));    

////		KDScrollPane scrollPane = new KDScrollPane();
////		this.tabPurchase.add(scrollPane, "");
//		UIContext uiContext = new UIContext(ui);

		UIContext uiContext = new UIContext(ui);
		boolean debug =getIsImmediacySign(room);
		if(this.getOprtState().equals(this.STATUS_ADDNEW) 
				&& debug && this.editData.getPurchase()==null){
			try {
				String state = this.STATUS_ADDNEW;
				plUI = (PurchaseEditUI) UIFactoryHelper.initUIObject(
						PurchaseEditUI.class.getName(), uiContext, null, state);
			} catch (UIException e) {
				this.handleException(e);
			}
			if (plUI != null) {
				scrollPane.setViewportView(plUI);
				scrollPane.setKeyBoardControl(true);
			}
			
			initPurchaseRoom(plUI,room);
			if(!isCodeRuleEnable(plUI.editData)){
				SimpleDateFormat datefor =new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
				String number ="";
				String dateNumber =datefor.format(new Date())+"-";
				FDCSQLBuilder sql=new FDCSQLBuilder();
				sql.appendSql("select top 1 fdisplayName from T_SHE_Purchase " +
						"where fnumber like '%"+dateNumber+"' order by fnumber desc");
				IRowSet rowSet =sql.executeQuery();
				try {
					while (rowSet.next()) {
						String fnumber =rowSet.getString("fnumber");
						fnumber=fnumber.substring(11, fnumber.length());
						Integer numberInt=new Integer(fnumber);
						int i =numberInt.intValue()+1;
						if(i>999){
							number = number +i;
						}else if(i>99){
							number = number+"0"+i;
						}else if(i>9){
							number = number+"00"+i;
						}else{
							number = number+"000"+i;
						}
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
				if(number.equals("")){
					plUI.txtNumber.setText(dateNumber+"0001");
				}else{
					plUI.txtNumber.setText(number);
				}
			}else{
//				String number =getAutoCode(plUI.editData);
//				if(number.equals("")){
//					MsgBox.showWarning("����������ɵı���Ϊ�գ�");
//					SysUtil.abort();
//				}else{
//					plUI.txtNumber.setText(number);
//				}
			}
			actionEdit.setEnabled(false);
			// �����Ϲ�תǩԼ
			SincerityPurchaseInfo sin = (SincerityPurchaseInfo) this.getUIContext().get("sincerity");
			if (sin != null) {
				plUI.editData.setSincerityPurchase(sin);
				plUI.f7SincerityPurchase.setValue(sin);
				plUI.editData.setPrePurchaseAmount(sin.getSincerityAmount());
				plUI.editData.setEarnestBase(sin.getSincerityAmount());
				plUI.tblCustomerInfo.removeRows();
				IRow row = plUI.tblCustomerInfo.addRow();
				row.setHeight(20);
				row.setUserObject(new PurchaseCustomerInfoInfo());
				row.getCell("propertyPercent").setValue(new BigDecimal("100"));
				if (sin.getCustomer() != null) {
					// by tim_gao ԤԼ�źſͻ��ṹ�仯 2010-06-15
//					FDCCustomerInfo customer = FDCCustomerFactory.getRemoteInstance().getFDCCustomerInfo(new ObjectUuidPK(sin.getCustomer().getId()));
//					plUI.setCustomerRowData(customer, row);
				}
			}
		}else{
			String purchaseId = this.editData.getPurchase().getId().toString();
			uiContext.put(UIContext.ID, purchaseId);
			uiContext.put("src", "signContract");
			try {
				String state = "EDIT";
				if (this.getOprtState().equals("VIEW")) {
					state = "VIEW";
				//�����״̬û�п�����ȫ  update by renliang 2011-1-18
				}else if(this.getOprtState().equals(FINDVIEW_OPRSTATE)){
					state = "VIEW";
				}
				plUI = (PurchaseEditUI) UIFactoryHelper.initUIObject(
						PurchaseEditUI.class.getName(), uiContext, null, state);
			} catch (UIException e) {
				this.handleException(e);
			}
			if (plUI != null) {
				scrollPane.setViewportView(plUI);
				scrollPane.setKeyBoardControl(true);
				plUI.btnUp.setEnabled(false);
				plUI.btnDown.setEnabled(false);
				plUI.btnAddNewCustomer.setEnabled(false);
				plUI.btnAddCustomer.setEnabled(false);
				plUI.btnDeleteCustomer.setEnabled(false);
				plUI.modifyInfo.setEnabled(false);
				plUI.modifyName.setEnabled(false);
//				plUI.btnCreat.setEnabled(false);
			}
		}
		initPurchaseBtn(plUI);
		toggleModifyCustBtn();
		plUI.tblCustomerInfo.setEditable(true);
	}
	
	// �Ƿ���ڱ������
	protected boolean isCodeRuleEnable(IObjectValue objValue)
			throws EASBizException, BOSException {
		String currentOrgId = SysContext.getSysContext().getCurrentOrgUnit()
				.getId().toString();
		ICodingRuleManager codeRuleMgr = null;
		codeRuleMgr = CodingRuleManagerFactory.getRemoteInstance();
		return codeRuleMgr.isExist(objValue, currentOrgId);
	}
	
	// �õ��Զ�����
	protected String getAutoCode(IObjectValue objValue) 
			throws EASBizException, BOSException { 
		String currentOrgId = SysContext.getSysContext().getCurrentOrgUnit()
				.getId().toString();
		ICodingRuleManager codeRuleMgr = null;
		
		codeRuleMgr = CodingRuleManagerFactory.getRemoteInstance();
		if (codeRuleMgr.isUseIntermitNumber(objValue, currentOrgId)) {
			return codeRuleMgr.readNumber(objValue, currentOrgId);
		} else {
			return codeRuleMgr.getNumber(objValue, currentOrgId);
		}
	}

/**
 *  �����޸Ŀͻ������Ϳͻ����ϰ�ť
 */
	private void toggleModifyCustBtn(){
		boolean isEditOprstate = OprtState.EDIT.equals(this.getOprtState()) ;
		boolean isSaved = FDCBillStateEnum.SAVED.equals(this.editData.getState());
		boolean isSubmited = FDCBillStateEnum.SUBMITTED.equals(this.editData.getState());
		boolean flag = isEditOprstate && (isSaved ||isSubmited);
		if(plUI == null) return;
		plUI.modifyInfo.setEnabled(flag);
		plUI.modifyName.setEnabled(flag);
	}
	
	/**
	 * ֱ��ǩԼ����ʱ���������
	 */
	public void initPurchaseBtn(PurchaseEditUI plUI){
//        if(plUI == null){
//        	return;
//        }   
		plUI.contNumber.setVisible(false);
		plUI.contCreateDate.setVisible(false);
		plUI.contCreator.setVisible(false);
		plUI.contDes.setVisible(false);
		plUI.contSeller.setBounds(new Rectangle(10, 361, 270, 19));
		plUI.add(plUI.contSeller, new KDLayout.Constraints(10, 361, 270, 19, 0));
		plUI.contSecondSeller.setBounds(new Rectangle(320, 361, 270, 19));
		plUI.add(plUI.contSecondSeller, new KDLayout.Constraints(320, 361, 270, 19, 0));
		plUI.contSincerityPurchase.setBoundLabelText("�������ⵥ");
		plUI.contSincerityPurchase.setBounds(new Rectangle(615, 361, 270, 19));
		plUI.add(plUI.contSincerityPurchase, new KDLayout.Constraints(615, 361, 270, 19, 0));
		
		plUI.tabPurchase.add(plUI.panelPurchaseBill, "��ͬ��Ϣ",0);
		plUI.contPurchaseDate.setVisible(false);
		plUI.contSignDate.setVisible(false);
		plUI.contPlanSignTimeLimit.setVisible(false);
		plUI.contAgio.setBounds(new Rectangle(612, 58, 270, 19));
		plUI.panelPurchaseBill.add(plUI.contAgio, null);
		plUI.btnChooseAgio.setBounds(new Rectangle(791, 100, 82, 23));
		plUI.panelPurchaseBill.add(plUI.btnChooseAgio, null);
		plUI.contAgioDes.setBounds(new Rectangle(20, 100, 760, 19));
		plUI.panelPurchaseBill.add(plUI.contAgioDes, null);
		
		plUI.contPriceAccount.setBounds(new Rectangle(20, 127, 270, 19));
		plUI.panelPurchaseBill.add(plUI.contPriceAccount, null);
		plUI.contSpecialAgioType.setBounds(new Rectangle(316, 127, 270, 19));
		plUI.panelPurchaseBill.add(plUI.contSpecialAgioType, null);
		plUI.contSpecialAgio.setBounds(new Rectangle(612, 127, 270, 19));
		plUI.panelPurchaseBill.add(plUI.contSpecialAgio, null);
	}
	
	/**
	 * ֱ��ǩԼ����ʱ��������ѡ�����ʼ���Ϲ���Ϣ
	 * @throws UuidException 
	 * @throws BOSException 
	 * @throws EASBizException 
	 */
	public void initPurchaseRoom(PurchaseEditUI plUI,RoomInfo room) throws EASBizException, BOSException, UuidException{
		if (room == null) {
			return;
		}
		BuildingInfo buildingInfo = room.getBuilding();

		SellProjectInfo sellProject = buildingInfo.getSellProject();
		if (sellProject != null && sellProject.getId() != null) {
			sellProject = SellProjectFactory.getRemoteInstance().getSellProjectInfo(new ObjectUuidPK(BOSUuid.read(sellProject.getId().toString())));
		}
		
		verifyAddNewRoom(room);
		if (room != null) {
			plUI.setIsEarnestInHouseAmount(plUI.editData, sellProject);
			
			plUI.txtRoomNumber.setUserObject(room);
			plUI.txtRoomNumber.setText(room.getNumber());
			if(room.getSellOrder()!=null){
				plUI.txtSellOrder.setText(room.getSellOrder().getName());
			}
			plUI.txtProject.setText(room.getBuilding().getSellProject().getName());
			plUI.txtProject.setUserObject(sellProject);
			if (room.getBuilding().getSubarea() != null) {
				plUI.txtSubarea.setText(room.getBuilding().getSubarea().getName());
			}
			plUI.txtBuilding.setText(room.getBuilding().getName());
			plUI.spiUnit.setValue(new Integer(room.getBuildUnit() == null ? 0 : room.getBuildUnit().getSeq()));
			plUI.spiFloor.setValue(new Integer(room.getFloor()));
			plUI.f7BuildingFloor.setValue(room.getBuildingFloor());

			plUI.txtBuildingArea.setValue(room.getBuildingArea());
			plUI.txtRoomArea.setValue(room.getRoomArea());
			plUI.f7RoomModel.setValue(room.getRoomModel());
			plUI.txtFitmentStandard.setText(room.getFitmentStandard());
			plUI.txtAgio.setUserObject(new AgioEntryCollection());
			plUI.comboSellType.setSelectedItem(SellTypeEnum.PreSell);
			// setSincerityFilter(room);
			// this.f7SincerityPurchase.setValue(null);
			plUI.updateAttachmentAmount(null);
			
			plUI.updateAreaCompensateAmount();

			plUI.addF7PayTypeFilter();
			plUI.f7PayType.setValue(null);
			plUI.showStandardPrice();
		}
	}
	
	public void actionEdit_actionPerformed(ActionEvent e) throws Exception {
		super.actionEdit_actionPerformed(e);
		this.pkCreateTime.setEnabled(false);
		this.pkCreator.setEnabled(false);
		isEditSignDate();
		setPurchaseTab();
		if(this.plUI != null){
//			setPurchaseEditUI();
		}
	}

	protected void btnSelectRoom_actionPerformed(ActionEvent e)	throws Exception {
		BuildingInfo buildingInfo = (BuildingInfo) this.getUIContext().get("building");
		BuildingUnitInfo buldUnit = (BuildingUnitInfo) this.getUIContext().get("buildUnit");
		RoomInfo room = RoomSelectUI.showOneRoomSelectUI(this, buildingInfo,
				buldUnit,MoneySysTypeEnum.SalehouseSys);
		if (room == null) {
			return;
		}
		verifyAddNewRoom(room);
		this.txtContractName.setText(room.getBuilding().getSellProject()
				.getName()
				+ "���ۺ�ͬ");
		this.txtRoomNumber.setUserObject(room);
		this.txtRoomNumber.setText(room.getDisplayName());
		PurchaseInfo purchase = room.getLastPurchase();
		this.editData.setPurchase(purchase);
		this.setPurchaseTab();
		if(this.plUI != null){
//			setPurchaseEditUI();
		}
	}

	private void verifyAddNewRoom(RoomInfo room)
	{
		if (room == null)
		{
			return;
		}
		boolean debug = getIsImmediacySign(room);
		if(HousePropertyEnum.Attachment.equals(room.getHouseProperty()))
		{
			MsgBox.showInfo("�����������ܵ���ǩԼ!");
			this.abort();
		}

		else if (room.getSellState().equals(RoomSellStateEnum.KeepDown))
		{
			MsgBox.showInfo("�÷����Ǳ���״̬�����ܽ���ǩԼ������");
			this.abort();
		}
		else if (room.getSellState().equals(RoomSellStateEnum.Init))
		{
			MsgBox.showInfo("�÷����ǳ�ʼ״̬�����ܽ���ǩԼ������");
			this.abort();
		}else if(!debug && room.getSellState().equals(RoomSellStateEnum.OnShow)){
			MsgBox.showInfo("�÷�����δ�Ϲ�״̬�����ܽ���ǩԼ������");
			this.abort();
		}
		else if (!debug && room.getSellState().equals(RoomSellStateEnum.PrePurchase))
		{
			MsgBox.showInfo("�÷�����δ�Ϲ�״̬�����ܽ���ǩԼ������");
			this.abort();
		}else if (!debug && room.getSellState().equals(RoomSellStateEnum.SincerPurchase))
		{
			MsgBox.showInfo("�÷�����δ�Ϲ�״̬�����ܽ���ǩԼ������");
			this.abort();
		}
		else if(room.getStandardTotalAmount()==null){
			MsgBox.showInfo("δ���۷��䲻��ǩԼ��");
			this.abort();
		}
		else
		{
			this.checkIsHaveSignContract(room);
		}
	
		boolean isAttached = false;
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(
				new FilterItemInfo("room.id", room.getId().toString()));
		/*try
		{
			isAttached = RoomAttachmentEntryFactory.getRemoteInstance().exists(
					filter);
		} catch (EASBizException e)
		{
			e.printStackTrace();
		} catch (BOSException e)
		{
			e.printStackTrace();
		}
		if (isAttached)
		{
			MsgBox.showInfo("�÷����Ѿ������������,���ܵ�������!");
			this.abort();
		}*/
		
		EntityViewInfo view1 = new EntityViewInfo();
		view1.getSelector().add("*");
		// view.getSelector().add("room.*");
		FilterInfo filter1 = new FilterInfo();
		view1.setFilter(filter1);
		filter1.getFilterItems().add(
				new FilterItemInfo("attachmentEntry.room.id", room.getId()
						.toString()));
		PurchaseRoomAttachmentEntryCollection attaches = null;
		try
		{
			attaches = PurchaseRoomAttachmentEntryFactory.getRemoteInstance()
					.getPurchaseRoomAttachmentEntryCollection(view1);
		} catch (BOSException e)
		{
			e.printStackTrace();
		}
		if (attaches.size() > 0)
		{
			MsgBox.showInfo("�÷����Ѿ�����������󶨲��Ҳ����ͬ,���ܵ�������!");
			this.abort();
		}
		
		
		
		
		PurchaseInfo purchase = room.getLastPurchase();
		if (purchase != null)
		{
			if (PurchaseStateEnum.PrePurchaseApply == purchase
					.getPurchaseState()
					|| PurchaseStateEnum.PrePurchaseCheck == purchase
							.getPurchaseState())
			{
				if (!debug && purchase.getAuditor() == null)
				{
					MsgBox.showInfo("Ԥ��������ǩԼ!");
					this.abort();
				}
			}
			else
			{
				if(debug && purchase.getAuditor() == null 
						&& !(purchase.getPurchaseState().equals(PurchaseStateEnum.PrePurchaseApply)
						||  purchase.getPurchaseState().equals(PurchaseStateEnum.PrePurchaseCheck))){
					MsgBox.showInfo("�����Ϲ���û�����,����ǩԼ!");
					this.abort();
				}else if(!debug && purchase.getAuditor() == null){
					MsgBox.showInfo("�����Ϲ���û�����,����ǩԼ!");
					this.abort();
				}
			}
		}
		
	}

	public void onLoad() throws Exception {
		/*
		SwingUtilities.getWindowAncestor(this).addWindowListener(new WindowAdapter(){
			public void windowClosed(WindowEvent e) {
				//
			}
		});
		*/
		
		super.onLoad();
		this.actionTraceDown.setVisible(false);
		this.actionTraceUp.setVisible(false);
		//this.actionWorkFlowG.setVisible(false);
		this.actionCreateFrom.setVisible(false);
		this.actionAddLine.setVisible(false);
		this.actionRemoveLine.setVisible(false);
		this.actionInsertLine.setVisible(false);
		this.actionViewDoProccess.setVisible(false);
		this.actionRemove.setVisible(false);
		this.actionCancel.setVisible(false);
		this.actionCancelCancel.setVisible(false);
		this.txtRoomNumber.setEnabled(false);
		this.actionCopy.setVisible(false);
		this.actionCancel.setVisible(false);
		this.actionCancelCancel.setVisible(false);
		this.actionRemove.setVisible(false);
		this.actionAudit.setVisible(false);
		this.actionUnAudit.setVisible(false);
		this.menuBiz.setVisible(false);
		this.menuTable1.setVisible(false);
		this.pkCreateTime.setEnabled(false);
		this.pkCreator.setEnabled(false);
		if(this.editData.isIsBlankOut()){
			this.actionEdit.setEnabled(false);
		}else if(!getOprtState().equals(this.STATUS_ADDNEW)){
			this.actionEdit.setEnabled(true);
		}
		//���½��������� + ������
		if(editData.getState() != null && !editData.isIsBlankOut() && !editData.getState().equals(FDCBillStateEnum.AUDITTED))	{  
			this.actionSave.setEnabled(false);
		}	
		
		this.chkIsOnRecord.setEnabled(false);
		this.pkOnRecordDate.setEnabled(false);
		if(OprtState.ADDNEW.equals(this.getOprtState()) && this.plUI!=null){
//			this.setPurchaseEditUI();
		}
		this.initUIByBillState();
		
		actionIntegral.setVisible(true);
		actionIntegral.setEnabled(true);
	}

	private void setPurchaseEditUI(){
		this.plUI.btnUp.setEnabled(true);
		this.plUI.btnDown.setEnabled(true);
		this.plUI.btnAddNewCustomer.setEnabled(true);
		this.plUI.btnAddCustomer.setEnabled(true);
		this.plUI.btnDeleteCustomer.setEnabled(true);
		this.plUI.tblCustomerInfo.setEditable(true);
		this.plUI.f7secondSeller.setEnabled(true);
		this.plUI.txtPlanSignTimeLimit.setEnabled(true);
		this.plUI.pkPurchaseDate.setEnabled(true);
		this.plUI.comboSpecialAgioType.setEnabled(true);
		this.plUI.comboDigit.setEnabled(true);
		this.plUI.btnLittleAdjust.setEnabled(true);
		this.plUI.btnAddPayEntry.setEnabled(true);
		this.plUI.btnInsertPayEntry.setEnabled(true);
		this.plUI.btnRemovePayEntry.setEnabled(true);
		this.plUI.tblPayList.setEditable(true);
		setPurchasePayList();
//		this.plUI.tblPayList.getStyleAttributes().setLocked(false);
	}
	
	private void setPurchasePayList(){
		for(int i=0; i<this.plUI.tblPayList.getRowCount(); i++){
			IRow row = this.plUI.tblPayList.getRow(i);
			if(row.getCell("actAmount").getValue()!=null && ((BigDecimal)row.getCell("actAmount").getValue()).compareTo(FDCHelper.ZERO) == 1){
				row.getCell("amount").getStyleAttributes().setLocked(true);
			}
		}
	}
	
	protected IObjectValue createNewData() {
		isEditSignDate();
		RoomSignContractInfo sign = new RoomSignContractInfo();
		sign.setOrgUnit(SysContext.getSysContext().getCurrentSaleUnit()
				.castToFullOrgUnitInfo());
		sign.setIsBlankOut(false);
		sign.setSignDate(new Date());
		RoomInfo room = (RoomInfo) this.getUIContext().get("room");
		verifyAddNewRoom(room);
		sign.setRoom(room);
		if (room != null) {
			sign
					.setName(room.getBuilding().getSellProject().getName()
							+ "���ۺ�ͬ-"+room.getDisplayName());
			PurchaseInfo purchase = room.getLastPurchase();
			//----ֻ���Ϲ����ǿ���Ϊ�Ϲ�����״̬���Ϲ�������ǩԼ.��Ҫ��Ϊ�˷�ֹ����е��Ϲ���ǩԼ   081104 by zhicheng_jin------
			boolean debug = getIsImmediacySign(room);
			if((purchase == null  ||  !PurchaseStateEnum.PurchaseAudit.equals(purchase.getPurchaseState()))
					&& !debug){
				logger.info("lastPurchase is null:" + (purchase==null));
				if(purchase != null){
					logger.info("purchaseState is :" + (purchase.getPurchaseState()));
				}
				MsgBox.showInfo("ֻ���Ϲ�����״̬���Ϲ�������ǩԼ!");
				this.abort();
			}
			//---------------------------
			sign.setPurchase(purchase);
		}
		sign.setCU(SysContext.getSysContext().getCurrentCtrlUnit());
//		sign.setOrgUnit(SysContext.getSysContext().getCurrentOrgUnit().castToFullOrgUnitInfo());
		sign.setBookedDate(new Date());
		try {
			sign.setCreateTime(FDCSQLFacadeFactory.getRemoteInstance()
					.getServerTime());
		} catch (BOSException e) {
			e.printStackTrace();
		}
		sign.setCreator(userInfo);
		return sign;
	}

	protected ICoreBase getBizInterface() throws Exception {
		return RoomSignContractFactory.getRemoteInstance();
	}

	protected void attachListeners() {

	}

	protected void detachListeners() {

	}

	protected KDTextField getNumberCtrl() {
		return this.txtContractNumber;
	}

	protected void chkIsOnRecord_actionPerformed(ActionEvent e) throws Exception {
		super.chkIsOnRecord_actionPerformed(e);
		/*
		if(this.chkIsOnRecord.isSelected()){
			this.pkOnRecordDate.setEnabled(true);
		}else{
			this.pkOnRecordDate.setEnabled(false);
		}
		*/
	}
	
	private void initUIByBillState()
	{
		if(FDCBillStateEnum.AUDITTED == this.editData.getState()&&this.oprtState.equalsIgnoreCase(OprtState.EDIT))
		{
			this.txtContractNumber.setEnabled(false);
			this.txtContractName.setEnabled(false);
			this.btnSelectRoom.setEnabled(false);
			this.chkIsOnRecord.setEnabled(false);
			this.pkSignDate.setEnabled(false);
			this.pkOnRecordDate.setEnabled(false);
			this.pkSignJoinDate.setEnabled(false);
			this.txtDescription.setEnabled(false);
			
			
		
		}
	}

	protected KDTable getDetailTable() {
		// TODO �Զ����ɷ������
		return null;
	}
	public void actionPre_actionPerformed(ActionEvent e) throws Exception
	{
		super.actionPre_actionPerformed(e);
		this.actionAudit.setVisible(false);
	}
	public void actionNext_actionPerformed(ActionEvent e) throws Exception
	{
		super.actionNext_actionPerformed(e);
		this.actionAudit.setVisible(false);
	}
	public void actionFirst_actionPerformed(ActionEvent e) throws Exception
	{
		super.actionFirst_actionPerformed(e);
		this.actionAudit.setVisible(false);
	}
	public void actionLast_actionPerformed(ActionEvent e) throws Exception
	{
		super.actionLast_actionPerformed(e);
		this.actionAudit.setVisible(false);
		
	}
	
	public void actionAddNew_actionPerformed(ActionEvent e) throws Exception
	{
		super.actionAddNew_actionPerformed(e);
		this.txtContractName.setEnabled(true);
		this.txtContractNum.setEnabled(true);
		this.txtContractNumber.setEnabled(true);
		this.txtDescription.setEnabled(true);
		this.txtRoomNumber.setEnabled(true);
		this.btnSelectRoom.setEnabled(true);
		this.txtRoomNumber.setText(null);
	}
	
	/**
	 * �ж��Ƿ��������ڿ�����Ժ� ͬһ������ �ֶ�ʼƻ�����ȡ
	 * @return
	 * @throws BOSException
	 * @author laiquan_luo
	 */
	private boolean isCompleteReceiveFirstAmount() throws BOSException
	{
		boolean debug = false;
		RoomInfo room = (RoomInfo) this.txtRoomNumber.getUserObject();
		PurchaseInfo purchase = room.getLastPurchase();
		
		//���ڿ��Ǽƻ��Կ���
		FilterInfo filter = new FilterInfo();
		EntityViewInfo view = new EntityViewInfo();
		view.getSelector().add("*");
		view.setFilter(filter);
		
		filter.getFilterItems().add(new FilterItemInfo("head",purchase.getId().toString()));
		filter.getFilterItems().add(new FilterItemInfo("moneyDefine.moneyType",MoneyTypeEnum.FISRTAMOUNT_VALUE));
		
		PurchasePayListEntryCollection purchasePayListEntryColl = PurchasePayListEntryFactory.getRemoteInstance().getPurchasePayListEntryCollection(view);
		if(purchasePayListEntryColl.size() < 1)
		{
			logger.error("�Ϲ��� "+ purchase.getName()+"  "+purchase.getNumber()+"  û���׸���ļƻ��Կ���...");
			MsgBox.showInfo("�����߼������Ѽ�¼��־��");
			this.abort();
		}//����Ӧ���Ǿ�һ�����ݵģ���������Ϊ�˼����Ժ���ͬһ������ֶ�����ƻ�����ȡ
		else
		{
			BigDecimal actAmount = FDCHelper.ZERO;
			BigDecimal appAmount = FDCHelper.ZERO;
			for(int i = 0; i < purchasePayListEntryColl.size(); i ++)
			{
				if(purchasePayListEntryColl.get(i).getActPayAmount() != null)
				{
					 actAmount = actAmount.add(purchasePayListEntryColl.get(i).getActPayAmount());
				}
				if(purchasePayListEntryColl.get(i).getApAmount() != null)
				{
					appAmount = appAmount.add(purchasePayListEntryColl.get(i).getApAmount());
				}
			}
			if(actAmount.compareTo(appAmount) >= 0)
			{
				debug = true;
			}
		}
		return debug;
	}
	/**
	 * �ж��Ƿ������ڿ�
	 * @return
	 * @throws EASBizException
	 * @throws BOSException
	 */
	private boolean isHaveFirstAmount() throws EASBizException, BOSException
	{
		boolean debug = false;
		
		RoomInfo room = (RoomInfo) this.txtRoomNumber.getUserObject();
		PurchaseInfo purchase = room.getLastPurchase();
	
//		���ڿ��Ǽƻ��Կ���
		FilterInfo filter = new FilterInfo();
		
		filter.getFilterItems().add(new FilterItemInfo("head",purchase.getId().toString()));
		filter.getFilterItems().add(new FilterItemInfo("moneyDefine.moneyType",MoneyTypeEnum.FISRTAMOUNT_VALUE));
		
		debug = PurchasePayListEntryFactory.getRemoteInstance().exists(filter);
		
		return debug;
	}
	/**
	 * �ж��Ƿ��а��ҿ�
	 * @return
	 * @throws EASBizException
	 * @throws BOSException
	 */
	private boolean isHaveLoan() throws EASBizException, BOSException
	{
		boolean debug = false;

		RoomInfo room = (RoomInfo) this.txtRoomNumber.getUserObject();
		PurchaseInfo purchase = room.getLastPurchase();

		// ���ڿ��Ǽƻ��Կ���
		FilterInfo filter = new FilterInfo();

		filter.getFilterItems().add(
				new FilterItemInfo("head", purchase.getId().toString()));
		filter.getFilterItems().add(new FilterItemInfo("moneyDefine.moneyType",	MoneyTypeEnum.LOANAMOUNT_VALUE));

		debug = PurchasePayListEntryFactory.getRemoteInstance().exists(filter);
		return debug;
	}
	/**
	 * �Ƿ����밴�ҿ�֮ǰ�����мƻ��Կ���
	 * @return
	 * @throws BOSException 
	 */
	private boolean isHaveReceiveAllBeforeLoanAmount() throws BOSException
	{

		boolean debug = true;
		RoomInfo room = (RoomInfo) this.txtRoomNumber.getUserObject();
		PurchaseInfo purchase = room.getLastPurchase();
		
//		 ���ڿ��Ǽƻ��Կ���
		FilterInfo filter = new FilterInfo();
        EntityViewInfo view = new EntityViewInfo();
        view.getSelector().add("*");
        view.getSelector().add("moneyDefine.*");
        view.setFilter(filter);
        view.getSorter().add(new SorterItemInfo("seq"));
		filter.getFilterItems().add(new FilterItemInfo("head", purchase.getId().toString()));
		
		PurchasePayListEntryCollection payListColl = PurchasePayListEntryFactory.getRemoteInstance().getPurchasePayListEntryCollection(view);
		List tempList = new ArrayList();
		for(int i = 0; i < payListColl.size(); i ++)
		{
			if(MoneyTypeEnum.LoanAmount != payListColl.get(i).getMoneyDefine().getMoneyType())
			{
				tempList.add(payListColl.get(i).getMoneyDefine().getMoneyType().getValue());
			}
			else
			{
				break;
			}
		}
		
		for(int i = 0; i < tempList.size(); i ++)
		{
			String temp = tempList.get(i).toString();
			if(!this.isReceiveAllByMoneyName(temp))
			{
				debug = false;
			}
		}
		return debug;
	}
	/**
	 * �ж��Ƿ�����ĳ������
	 * @param money
	 * @return
	 * @throws BOSException 
	 */
	private boolean isReceiveAllByMoneyName(String money) throws BOSException
	{

		boolean debug = false;
		RoomInfo room = (RoomInfo) this.txtRoomNumber.getUserObject();
		PurchaseInfo purchase = room.getLastPurchase();
		
		//���ڿ��Ǽƻ��Կ���
		FilterInfo filter = new FilterInfo();
		EntityViewInfo view = new EntityViewInfo();
		view.getSelector().add("*");
		view.setFilter(filter);
		
		filter.getFilterItems().add(new FilterItemInfo("head",purchase.getId().toString()));
		filter.getFilterItems().add(new FilterItemInfo("moneyDefine.moneyType",money));
		
		PurchasePayListEntryCollection purchasePayListEntryColl = PurchasePayListEntryFactory.getRemoteInstance().getPurchasePayListEntryCollection(view);
		if(purchasePayListEntryColl.size() < 1)
		{
			logger.error("�Ϲ��� "+ purchase.getName()+"  "+purchase.getNumber()+"  û��"+ money+ "�ƻ��Կ���...");
			MsgBox.showInfo("�����߼������Ѽ�¼��־��");
			this.abort();
		}//����Ӧ���Ǿ�һ�����ݵģ���������Ϊ�˼����Ժ���ͬһ������ֶ�����ƻ�����ȡ
		else
		{
			BigDecimal actAmount = FDCHelper.ZERO;
			BigDecimal appAmount = FDCHelper.ZERO;
			for(int i = 0; i < purchasePayListEntryColl.size(); i ++)
			{
				if(purchasePayListEntryColl.get(i).getActPayAmount() != null)
				{
					 actAmount = actAmount.add(purchasePayListEntryColl.get(i).getActPayAmount());
				}
				if(purchasePayListEntryColl.get(i).getApAmount() != null)
				{
					appAmount = appAmount.add(purchasePayListEntryColl.get(i).getApAmount());
				}
			}
			if(actAmount.compareTo(appAmount) >= 0)
			{
				debug = true;
			}
		}
		return debug;
	
	}
	
	/**
	 * �ж��Ƿ��Ѿ������ȫ��
	 * @return
	 * @throws BOSException 
	 */
	private boolean isHaveCompleteReceive() throws BOSException
	{
		boolean debug = false;
		RoomInfo room = (RoomInfo) this.txtRoomNumber.getUserObject();
		PurchaseInfo purchase = room.getLastPurchase();
		
		//���ڿ��Ǽƻ��Կ���
		FilterInfo filter = new FilterInfo();
		EntityViewInfo view = new EntityViewInfo();
		view.getSelector().add("*");
		view.setFilter(filter);
		
		filter.getFilterItems().add(new FilterItemInfo("fdcReceiveBill.purchase",purchase.getId().toString()));
		filter.getFilterItems().add(new FilterItemInfo("fdcReceiveBill.room",room.getId().toString()));
		

		ReceivingBillCollection rbColl = ReceivingBillFactory.getRemoteInstance().getReceivingBillCollection(view);
		BigDecimal temp = FDCHelper.ZERO;
		for(int i = 0; i < rbColl.size(); i ++)
		{
			BigDecimal t = rbColl.get(i).getAmount();
			if(t != null)
			{
				temp = temp.add(t);
			}
		}
		BigDecimal roomPrice = FDCHelper.ZERO;
		if(purchase.getContractTotalAmount() != null)
		{
			roomPrice = purchase.getContractTotalAmount();
		}
		if(temp.compareTo(roomPrice) >= 0)
		{
			debug = true;
		}
		else
		{
			debug = false;
		}
		return debug;
	}
	/**
	 * �Ƿ������ ���ҿ� �͹������ �ƻ�����
	 * @return
	 */
	private boolean isHaveCompleteReceiveByPlan(RoomInfo room)
	{
		boolean debug = true;
		
		PurchaseInfo purchase = room.getLastPurchase();
		
		PurchasePayListEntryCollection purchasePayListEntryColl = purchase.getPayListEntry();
		
		BigDecimal actAmount = FDCHelper.ZERO;
		BigDecimal appAmount = FDCHelper.ZERO;
		for(int i = 0; i < purchasePayListEntryColl.size(); i ++)
		{
			if((MoneyTypeEnum.AccFundAmount.equals(purchasePayListEntryColl.get(i).getMoneyDefine().getMoneyType()))
					|| (MoneyTypeEnum.LoanAmount.equals(purchasePayListEntryColl.get(i).getMoneyDefine().getMoneyType())))
					continue;
			
			if(purchasePayListEntryColl.get(i).getActPayAmount() != null)
			{
				actAmount = purchasePayListEntryColl.get(i).getActPayAmount();
			}
			else
			{
				actAmount = FDCHelper.ZERO;
			}
			if(purchasePayListEntryColl.get(i).getApAmount() != null)
			{
				appAmount = purchasePayListEntryColl.get(i).getApAmount();
			}
			else
			{
				appAmount = FDCHelper.ZERO;
			}
			if(actAmount.compareTo(appAmount) < 0)
			{
				debug = false;
				break;
			}
		}
		return debug;
	}

	/**
	 * ������Ƕ������Դδ�ͷŵ�����.
	 * */
	public boolean destroyWindow() {
		if(plUI != null){
			plUI.destroyWindow();
		}
		return super.destroyWindow();
	}
	
	/**
	 * ��ǩԼ���ڽ��в��������Լ��ı�����ʾ
	 */
	private void isEditSignDate(){
		BuildingInfo building = (BuildingInfo)this.getUIContext().get("building");
		if(building == null){
			return;
		}
		SellProjectInfo sellProject = building.getSellProject();
		Map funcSetMap = setting.getFunctionSetMap();
		FunctionSetting funcSet = (FunctionSetting)funcSetMap.get(sellProject.getId()==null?null:sellProject.getId().toString());
		if(funcSet !=null ){
			if(!funcSet.getIsEditPurAndSignDate().booleanValue()){
				this.pkSignDate.setEnabled(false);
			}else{
				this.pkSignDate.setEnabled(true);
			}
		}
	}
	
	protected void pkSignDate_dataChanged(DataChangeEvent e) throws Exception {
		if(this.plUI == null){
			return;
		}
		super.pkSignDate_dataChanged(e);
		Date signDate = DateTimeUtils.truncateDate((Date)this.pkSignDate.getValue());
		//ֱ��ǩԼʱ��ǩԼ���ڸı䣬ͬʱ�ı��Ϲ���������
		RoomInfo room = (RoomInfo) this.txtRoomNumber.getUserObject();
		boolean debug =getIsImmediacySign(room);
		if(this.getOprtState().equals(this.STATUS_ADDNEW) 
				&& debug && this.editData.getPurchase() == null){
			this.plUI.pkPurchaseDate.setValue(signDate);
			this.plUI.pkSignDate.setValue(signDate);
		}
		//
		Date purchaseDate = DateTimeUtils.truncateDate((Date)this.plUI.pkPurchaseDate.getValue());
		if(purchaseDate!=null && signDate!=null){
			if(signDate.compareTo(purchaseDate) == -1){
				MsgBox.showWarning(this,"ǩԼ�������Ϲ���������֮ǰ��������¼�룡");
				SysUtil.abort();
			}
		}
	}

	/**
	 * ���͹�������
	 */
	public void actionIntegral_actionPerformed(ActionEvent e) throws Exception {
		if(getOprtState().equals(this.STATUS_ADDNEW)){
			MsgBox.showWarning("���ȱ��棡");
			return;
		}
		if(this.plUI == null){
			return;
		}
		RoomSignContractInfo roomSignContractInfo =checkIsIntegral();
		checkKdtEntrys();
		Set set=new HashSet();
		int rowIndex[] =KDTableUtil.getSelectedRows(plUI.tblCustomerInfo);
		for(int i=0;i<rowIndex.length;i++){
			IRow row=plUI.tblCustomerInfo.getRow(rowIndex[i]);
			if(row.getCell("customer").getValue()!=null){
				FDCCustomerInfo fdcCustomer = null;
				//�����Ϲ��������˿��ո��죬���ȡ�ͻ���Ҫ����idȡ����Ϊrow.getCell("customer").getValue()ʵ��Ϊ�ַ��� by zgy 
				if(row.getCell("id").getValue()!=null){
					String id  = row.getCell("id").getValue().toString();
					try {
						fdcCustomer = FDCCustomerFactory.getRemoteInstance().getFDCCustomerInfo(new ObjectUuidPK(id));
					} catch (EASBizException e1) {
						e1.printStackTrace();
					} catch (BOSException e1) {
						e1.printStackTrace();
					}
				}else{
					fdcCustomer= (FDCCustomerInfo) row.getCell("customer").getValue();
				}
				InsiderInfo insider=checkInsider(fdcCustomer);
				if(insider!=null){
					set.add(insider.getId().toString());
				}
			}
		}
			
		if(set.size()>0){
			Map ctxMap = new HashMap();
			if(plUI.prmtRecommendInsider.getValue()!=null){
				InsiderInfo recommendInsider=(InsiderInfo) plUI.prmtRecommendInsider.getValue();
				ctxMap.put("recommendInsider", recommendInsider);
			}
			ctxMap.put("set", set);
			ctxMap.put("roomSignContractInfo", roomSignContractInfo);
			ctxMap.put(UIContext.OWNER, this);
			IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.NEWWIN)
				.create(IntegralNewApplication2EditUI.class.getName(), ctxMap,null,OprtState.ADDNEW);
			uiWindow.show();
		}else{
			MsgBox.showWarning("�ǻ�Ա�ͻ������ܽ������͹������ֲ�����");
		}
	}
	
	public void checkKdtEntrys(){
	    int i[] =KDTableUtil.getSelectedRows(plUI.tblCustomerInfo);
	    if(i.length==0){
	    	MsgBox.showWarning("��ѡ��ͻ���");
	    	SysUtil.abort();
	    }
	    if(i.length>1){
	    	MsgBox.showWarning("ֻ��Ϊһλ�ͻ����͹������֣�");
	    	SysUtil.abort();
	    }
	}
	
	/**
	 * �Ƿ� �����͹�������
	 * @return
	 * @throws BOSException 
	 * @throws EASBizException 
	 */
	public RoomSignContractInfo checkIsIntegral() throws BOSException, EASBizException{
		SelectorItemCollection sic=new SelectorItemCollection();
    	sic.add("*");
    	sic.add("room.id");
    	sic.add("room.name");
    	sic.add("room.number");
    	sic.add("room.displayName");
    	sic.add("purchase.id");
    	sic.add("purchase.name");
    	sic.add("purchase.number");
    	sic.add("purchase.customerNames");
    	sic.add("purchase.sellProject.id");
    	sic.add("purchase.sellProject.name");
    	sic.add("purchase.sellProject.number");
		IRoomSignContract ir=RoomSignContractFactory.getRemoteInstance();
		RoomSignContractInfo info= ir.getRoomSignContractInfo
				(new ObjectUuidPK(this.editData.getId().toString()),sic);
		if(!info.getState().equals(FDCBillStateEnum.AUDITTED)){
			MsgBox.showWarning("��������ǩԼ���������͹������֣�");
			SysUtil.abort();
		}
		if(info.isIsIntegral()){
			MsgBox.showWarning("ͬһ��ǩԼ��ֻ������һ�ι������֣�");
			SysUtil.abort();
		}
		return info;
	}
	
	/**
	 * ���ݿͻ���Ϣ ��ѯ��Ӧ��Ա����
	 * @param fdcCustomer
	 * @return
	 * @throws BOSException
	 */
	public InsiderInfo checkInsider(FDCCustomerInfo fdcCustomer) throws BOSException{
		EntityViewInfo view=new EntityViewInfo();
		FilterInfo filter=new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("fdcCustomer.id",fdcCustomer.getId().toString()));
		view.setFilter(filter);
		IInsider ii=InsiderFactory.getRemoteInstance();
		InsiderCollection col=ii.getInsiderCollection(view);
		if(col!=null && col.size()>0){
			return col.get(0);
		}
		return null;
	}
	
	/**
	 * ��д �Ƿ������͹�������
	 * @throws BOSException 
	 * @throws EASBizException 
	 */
	public void setIsIntegral() throws BOSException, EASBizException{
		IRoomSignContract ir=RoomSignContractFactory.getRemoteInstance();
		RoomSignContractInfo info= ir.getRoomSignContractInfo
				(new ObjectUuidPK(this.editData.getId().toString()));
		info.setIsIntegral(true);
		SelectorItemCollection sic=new SelectorItemCollection();
		sic.add("isIntegral");
		ir.updatePartial(info, sic);
	}
	
	private void verifyCustomer() throws BOSException, EASBizException {
		BigDecimal percent = FDCHelper.ZERO;
		Map customerMap = new HashMap();
		if (this.plUI.tblCustomerInfo.getRowCount() == 0) {
			MsgBox.showInfo("û���Ϲ��ͻ�,�����!");
			this.abort();
		}
		for (int i = 0; i < plUI.tblCustomerInfo.getRowCount(); i++) {
			IRow row = plUI.tblCustomerInfo.getRow(i);
			if (row.getCell("propertyPercent").getValue() == null) {
				MsgBox.showInfo("��" + (row.getRowIndex() + 1) + "�пͻ�û�����ò�Ȩ����!");
				this.abort();
			}else{
				//��ӵ�����Ȩ�������ܵ���0���ж�  by renliang at 2010-12-22
				BigDecimal propertyPercent = (BigDecimal) row.getCell("propertyPercent").getValue();
				if (propertyPercent.compareTo(new BigDecimal("0")) == 0) {
					MsgBox.showInfo("��" + (row.getRowIndex() + 1) + "�пͻ��Ĳ�Ȩ�������ܵ���0!");
					this.abort();
				}
		
			}
			
			// ���֤��������֤
			if (row.getCell("certificateNumber").getValue() == null) {
				MsgBox.showWarning("����д֤������!");
				SysUtil.abort();
			}
			//by zgy �滻ԭ���߼������ݷ�¼id����ȡ�ͻ������ӽ��ױ���ȡ�������ӿͻ�����ȡ������ֹ�ͻ������뽻�ױ�ͬ������
			FDCCustomerInfo customer = null;
			if(row.getCell("id").getValue()!=null){
				String id  = row.getCell("id").getValue().toString();
				if(id!=null){
					try {
						customer  = FDCCustomerFactory.getRemoteInstance().getFDCCustomerInfo(new ObjectUuidPK(id));
					} catch (EASBizException e) {
						e.printStackTrace();
					} catch (BOSException e) {
						e.printStackTrace();
					}
				}

			}else{
				customer = (FDCCustomerInfo) row.getCell("customer").getValue();
			}
			if (customer == null) {
				MsgBox.showInfo("��" + (row.getRowIndex() + 1) + "�пͻ�û��¼��!");
				this.abort();
			}
			if (customerMap.containsKey(customer)) {
				MsgBox.showInfo("��" + (row.getRowIndex() + 1) + "�пͻ��ظ�!");
				this.abort();
			} else {
				customerMap.put(customer, customer);
			}

			// �ڿͻ�֤���͵绰δ�����޸�ʱ,��������֤
			/*CertifacateNameEnum nameEnum = (CertifacateNameEnum) row.getCell("certificateName").getValue();
			String certificateNum = (String) row.getCell("certificateNumber").getValue();
			if (!FDCHelper.isEmpty(certificateNum) && nameEnum != null) {
				if (!nameEnum.equals(customer.getCertificateName()) || !certificateNum.equals(customer.getCertificateNumber())) {
					if (!FDCCustomerHelper.verifyCertificateNumber(nameEnum, certificateNum, customer.getId().toString())) {
						this.abort();
					}
				}
			}*/
			String certificateNum = (String) row.getCell("certificateNumber").getValue();
			
			if(row.getCell("certificateName").getValue()!=null){
				CertificateInfo info  = (CertificateInfo)row.getCell("certificateName").getValue();
				if("001".equals(info.getNumber())){
					if(certificateNum.trim().length()==15 || certificateNum.trim().length()==18){
						try {
							if(!CertificateUtil.CardValidate(certificateNum)){
								FDCMsgBox.showWarning(this,"���֤���벻���Ϲ���������¼��");
								SysUtil.abort();
							}
						} catch (ParseException e) {
							logger.error(e.getMessage());
						}
					}
				}
			}
			//new update by renliang at 2011-3-2
			
			if(row.getCell("certificateName").getValue()!=null){
				CertificateInfo info =(CertificateInfo)row.getCell("certificateName").getValue();
				CertificateInfo infoNew = customer.getCertificateName();
				if(!FDCHelper.isEmpty(certificateNum) && info != null && infoNew !=null ){
					if(info.getId()!=null && infoNew.getId()!=null && info.getId().toString().equals(infoNew.getId().toString())){
						if (!FDCCustomerHelper.verifyCertificateNumber(info.getId().toString(), certificateNum, customer.getId().toString())) {
							this.abort();
						}
					}
				}
			}
			
			String phone = (String) row.getCell("phone").getValue();
			if (phone == null || phone.trim().equals("")) {
				MsgBox.showInfo("��" + (row.getRowIndex() + 1) + "�пͻ���ϵ�绰����Ϊ��!");
				this.abort();
			}
//			if (!phone.equals(customer.getPhone())) {
//				FDCCustomerInfo otherCust = customer;
				FDCCustomerInfo otherCust = (FDCCustomerInfo) customer.clone();
				otherCust.setPhone(phone);
				Map rMap = FDCCustomerFactory.getRemoteInstance().verifySave(otherCust, false);
				otherCust = null;
				boolean isAbort = FDCCustomerHelper.verifyPhoneAndName(rMap, this);
				if (isAbort) {
//					row.getCell("phone").setValue(customer.getPhone());
					this.abort();
				}
//			}

			percent = percent.add((BigDecimal) row.getCell("propertyPercent").getValue());
		}
		if (percent.compareTo(new BigDecimal("100")) != 0) {
			MsgBox.showInfo("��Ȩ��������100%!");
			this.abort();
		}

		RoomInfo room = (RoomInfo) txtRoomNumber.getUserObject();
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		view.getSelector().add("customerInfo.*");
		view.setFilter(filter);
		filter.getFilterItems().add(new FilterItemInfo("room.id", room.getId().toString()));
		filter.getFilterItems().add(new FilterItemInfo("purchaseState", PurchaseStateEnum.ChangeRoomBlankOut, CompareType.NOTEQUALS));
		filter.getFilterItems().add(new FilterItemInfo("purchaseState", PurchaseStateEnum.ManualBlankOut, CompareType.NOTEQUALS));
		filter.getFilterItems().add(new FilterItemInfo("purchaseState", PurchaseStateEnum.NoPayBlankOut, CompareType.NOTEQUALS));
		filter.getFilterItems().add(new FilterItemInfo("purchaseState", PurchaseStateEnum.QuitRoomBlankOut, CompareType.NOTEQUALS));
		filter.getFilterItems().add(new FilterItemInfo("purchaseState", PurchaseStateEnum.AdjustBlankOut, CompareType.NOTEQUALS));
		if (this.editData.getId() != null) {
			filter.getFilterItems().add(new FilterItemInfo("id", this.editData.getId().toString(), CompareType.NOTEQUALS));
		}
		PurchaseCollection purchases = PurchaseFactory.getRemoteInstance().getPurchaseCollection(view);
		for (int i = 0; i < purchases.size(); i++) {
			PurchaseInfo pur = purchases.get(i);
			PurchaseCustomerInfoCollection customerInfos = pur.getCustomerInfo();
			for (int j = 0; j < customerInfos.size(); j++) {
				PurchaseCustomerInfoInfo customerInfoInfo = customerInfos.get(j);
				if (customerInfoInfo.getCustomer() != null) {
					String aCusId = customerInfoInfo.getCustomer().getId().toString();
					for (int k = 0; k < plUI.tblCustomerInfo.getRowCount(); k++) {
						IRow row = plUI.tblCustomerInfo.getRow(k);
						 //by zgy �滻ԭ���߼������ݷ�¼id����ȡ�ͻ������ӽ��ױ���ȡ�������ӿͻ�����ȡ������ֹ�ͻ������뽻�ױ�ͬ������
						if(row.getCell("id").getValue()!=null){
							String id  = row.getCell("id").getValue().toString();
							if(id!=null){
								try {
									FDCCustomerInfo	customer  = FDCCustomerFactory.getRemoteInstance().getFDCCustomerInfo(new ObjectUuidPK(id));
									if (customer.getId().toString().equals(aCusId)) {
										MsgBox.showInfo("��" + (row.getRowIndex() + 1) + "�пͻ��Ѿ��Ϲ����˸÷���!");
										this.abort();
									}
								} catch (EASBizException e) {
									e.printStackTrace();
								} catch (BOSException e) {
									e.printStackTrace();
								}
							}
						}else{
							FDCCustomerInfo customer = (FDCCustomerInfo) row.getCell("customer").getValue();
							if (customer.getId().toString().equals(aCusId)) {
								MsgBox.showInfo("��" + (row.getRowIndex() + 1) + "�пͻ��Ѿ��Ϲ����˸÷���!");
								this.abort();
							}
						}
					}
				}
			}
		}

		// ͨ����������
		if (this.editData.getId() == null && purchases.size() > 0) {
			boolean debug = false;
			boolean debug2 =true;
			try {
				String id = room.getBuilding().getSellProject().getId().toString();
				HashMap functionSetMap = (HashMap)setting.getFunctionSetMap();
				FunctionSetting funcSet = (FunctionSetting)functionSetMap.get(id);
				if (funcSet == null) {
					logger.warn("û�и�������Ŀ��Ӧ���Ϲ����ò�����");
				} else {
					if(funcSet.getIsActGathering()!=null){
						debug = funcSet.getIsActGathering().booleanValue();
					}
					if(funcSet.getIsActGathering().booleanValue()
							|| funcSet.getIsSignGathering().booleanValue()){
						debug2 = false ;
					}else{
						debug2 = true ;
					}	
				}
			} catch (Exception e1) {
				debug = true;
				logger.warn("��ȡ�÷����Ӧ��������Ŀ��ǩԼ���ò��������˴���Ĭ����ʵ���տ�Ϊ׼��");
			}
			if (debug) {
				if (MsgBox.showConfirm2New(this, "�Ѿ�����Ԥ��/�Ϲ��÷���,�Ƿ����?") != MsgBox.YES) {
					this.abort();
				}
			} else if(!debug &&!debug2){
				MsgBox.showWarning("�Ѿ�����Ԥ��/�Ϲ��÷��䣡");
				this.abort();
			}
		}
	}
}