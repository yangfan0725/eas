/**
 * output package name
 */
package com.kingdee.eas.fdc.sellhouse.client;

import java.awt.event.ActionEvent;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;
import java.util.EventListener;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.swing.event.DataChangeEvent;
import com.kingdee.bos.ctrl.swing.event.DataChangeListener;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.basedata.assistant.PeriodInfo;
import com.kingdee.eas.basedata.assistant.PeriodUtils;
import com.kingdee.eas.basedata.org.SaleOrgUnitInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.fdc.basecrm.RevBillStatusEnum;
import com.kingdee.eas.fdc.basecrm.RevBillTypeEnum;
import com.kingdee.eas.fdc.basecrm.RevBizTypeEnum;
import com.kingdee.eas.fdc.basedata.FDCDateHelper;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.sellhouse.MoneyTypeEnum;
import com.kingdee.eas.fdc.sellhouse.PayListEntryCollection;
import com.kingdee.eas.fdc.sellhouse.PayListEntryInfo;
import com.kingdee.eas.fdc.sellhouse.PurchaseCollection;
import com.kingdee.eas.fdc.sellhouse.PurchaseFactory;
import com.kingdee.eas.fdc.sellhouse.PurchaseInfo;
import com.kingdee.eas.fdc.sellhouse.QuestionTypeEnum;
import com.kingdee.eas.fdc.sellhouse.SHEPayTypeFactory;
import com.kingdee.eas.fdc.sellhouse.SHEPayTypeInfo;
import com.kingdee.eas.fdc.sellhouse.SaleBalanceFactory;
import com.kingdee.eas.fdc.sellhouse.SaleBalanceInfo;
import com.kingdee.eas.fdc.sellhouse.SaleBalanceTypeEnum;
import com.kingdee.eas.fdc.sellhouse.SaleBlanceSetting;
import com.kingdee.eas.fdc.sellhouse.SellProjectFactory;
import com.kingdee.eas.fdc.sellhouse.SellProjectInfo;
import com.kingdee.eas.fdc.sellhouse.SettleMentFacadeFactory;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.util.client.MsgBox;
import com.kingdee.jdbc.rowset.IRowSet;

/**
 * output class name
 */
public class SaleBalanceEditUI extends AbstractSaleBalanceEditUI {

	//��ѡ�����˵�δ��������Ϲ���ĳ����Ϲ���
	public static String KEY_SINCER = "��δ��������ĵĳ����Ϲ���";
	
	//δ�������Ϲ���
	public static String KEY_NOAUDITPUR = "��δ�������Ϲ���";
	
	//δ���˵�Ԥ����
	public static String KEY_NOAPPLYPREPUR = "��δ���˵�Ԥ����";
	
	//������δ�����׸����Ϲ���
	public static String KEY_FIRSTAMOUNTPUR = "���Ϲ���δ�����׸�";
	
	//�˷�δ�������˷���
	public static String KEY_NOAUDITQUITROOM = "��δ�������˷���";
	
	//�˷������˵�δ�������˷���
	public static String KEY_CANREFUNDMENTAMOUNT = "���˷������굫û�������˷���";
	
	//���δ�����ı����
	public static String KEY_NOAUDITPURCHANGE = "��δ�����ı����";
	
	//����δ�����Ļ�����
	public static String KEY_NOAUDITCHANGEROOM = "��δ�����Ļ�����";
	
	//������ɰ��ҵ�δ���밴�ҵ��Ϲ���
	public static String KEY_LOANNOREV = "���Ѱ�����ɰ��ҵ�δ���밴�ҵ��Ϲ���";
	
	//������ɹ�����δ���빫������Ϲ���
	public static String KEY_ACCFUNDNOREV = "������ɹ�����δ���빫������Ϲ���";
	
	private static final Logger logger = CoreUIObject.getLogger(SaleBalanceEditUI.class);
	SaleOrgUnitInfo saleOrg = SHEHelper.getCurrentSaleOrg();
	SaleBlanceSetting saleBlanSetting = new SaleBlanceSetting();

	/**
	 * output class constructor
	 */
	public SaleBalanceEditUI() throws Exception {
		super();
	}

	public void onLoad() throws Exception {
		super.onLoad();
		initControl();
		prmtSellProjectSetViewInfo();
		//prmtPeriodSetEntityView();
	}

	public boolean checkBeforeWindowClosing() {
		// return super.checkBeforeWindowClosing();
		return true;
	}

	private void prmtSellProjectSetViewInfo() {
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		// filter.getFilterItems().add(new FilterItemInfo("orgUnit.id",
		// saleOrg.getId().toString()));
		// filter.getFilterItems().add(new FilterItemInfo("isForSHE",
		// Boolean.TRUE));
		Set idSet = (Set) this.getUIContext().get("sellProjectIdSet");
		view.setFilter(filter);
		if (idSet.size() > 1) {
			filter.getFilterItems().add(new FilterItemInfo("id", idSet, CompareType.INCLUDE));
		}
		this.prmtSellProject.setEntityViewInfo(view);
	}

	public void loadFields() {
		EventListener[] listeners = this.prmtSellProject.getListeners(DataChangeListener.class);
		for (int i = 0; i < listeners.length; i++) {
			this.prmtSellProject.removeDataChangeListener((DataChangeListener) listeners[i]);
		}
		EventListener[] periodListeners = this.prmtPeriod.getListeners(DataChangeListener.class);
		for (int i = 0; i < periodListeners.length; i++) {
			this.prmtPeriod.removeDataChangeListener((DataChangeListener) periodListeners[i]);
		}
		super.loadFields();
		for (int i = 0; i < listeners.length; i++) {
			this.prmtSellProject.addDataChangeListener((DataChangeListener) listeners[i]);
		}

		for (int i = 0; i < periodListeners.length; i++) {
			this.prmtPeriod.addDataChangeListener((DataChangeListener) periodListeners[i]);
		}

	}

	protected void prmtPeriod_dataChanged(DataChangeEvent e) throws Exception {
		super.prmtPeriod_dataChanged(e);
		PeriodInfo period = (PeriodInfo) this.prmtPeriod.getValue();
		if (period != null) {
			this.pkStartDate.setValue(period.getBeginDate());
			this.pkEndDate.setValue(period.getEndDate());
			this.txtRemark.setText(period.getNumber() + "�½�");
			this.btnBalanceCheck.setEnabled(true);
			this.btnBalanceConfirm.setEnabled(false);
		}
	}

	protected void prmtPeriodSetEntityView() throws Exception {
		if (this.pkStartDate.getValue() != null) {
			EntityViewInfo view = new EntityViewInfo();
			FilterInfo filter = new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("beginDate", this.pkStartDate.getValue()));
			view.setFilter(filter);
			this.prmtPeriod.setEntityViewInfo(view);
		}

	}

	protected void prmtSellProject_dataChanged(DataChangeEvent e) throws Exception {
		super.prmtSellProject_dataChanged(e);
		SellProjectInfo sellProject = (SellProjectInfo) this.prmtSellProject.getValue();
		Date startDate = null;
		Date endDate = null;
		if (sellProject != null) {
			this.tblReport.removeRows();
			this.tblReport.checkParsed();
			this.actionBalanceCheck.setEnabled(true);
			this.actionBalanceConfirm.setEnabled(false);
			this.pkEndDate.setEnabled(false);
			// ��ȡ�ϴν���������ڡ�
			startDate = this.getLastEndDate(sellProject.getId().toString());
			endDate = this.getLastEndDate(sellProject.getId().toString());
			// startDate = sellProject.getBalanceEndDate();
			// endDate = sellProject.getBalanceEndDate();

			/**
			 * ��������ϴν���������ڣ� �����ϴν���������ڼ�1����Ϊ���ν��㿪ʼ���ڣ� ��1���¼�1��Ϊ���ν���������ڡ�
			 * **/
			if (startDate != null) {
				startDate = SHEHelper.getNextDay(startDate);
				endDate = SHEHelper.getNextDay(endDate);
				this.pkStartDate.setValue(SHEHelper.getDayStart(startDate));
				this.pkStartDate.setEnabled(false);
				endDate = SHEHelper.getNextMonthPreDate(endDate);
				this.pkEndDate.setValue(endDate);
			} else {
				/**
				 * �����δ������� ��ȡ��������Ŀ���̴ο�������Ϊ�������۽��㿪ʼ���ڣ� ��1���¼�1��Ϊ�������۽���������ڡ�
				 * */
				try {
					startDate = this.getStartDate(sellProject.getId().toString());
				} catch (Exception e1) {
					handleException(e1);
					e1.printStackTrace();
				}
				if (startDate == null) {
					/**
					 * �����������Ŀ��δ���̣���û�н��������ȡ����ʱ�䣬ͨ�������������ܳ��֡�
					 * */
					MsgBox.showInfo("��������Ŀ��û���̣����顣");
					return;
				}
			}
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

	/*****
	 * ��������Ŀȡ�̴εĿ������ڡ�
	 * 
	 * **/
	private Date getStartDate(String sellProjectId) throws Exception {
		Date startDate = null;
		FDCSQLBuilder detailBuilder = new FDCSQLBuilder();
		detailBuilder.appendSql("select FOrderDate from T_SHE_SellOrder where FProjectID = ?");
		detailBuilder.addParam(sellProjectId);
		try {
			IRowSet detailSet = detailBuilder.executeQuery();
			if (detailSet.next()) {
				startDate = detailSet.getDate("FOrderDate");
			}
		} catch (Exception e) {
			handleException(e);
			e.printStackTrace();
		}
		return startDate;
	}

	private void initControl() {
		this.actionBalanceCheck.setEnabled(true);
		this.actionBalanceConfirm.setEnabled(false);
		this.btnBalanceCheck.setEnabled(true);
		this.btnBalanceConfirm.setEnabled(false);
		this.menuItemBalanceCheck.setEnabled(true);
		this.menuItemBalanceConfirm.setEnabled(false);
		this.actionCopy.setVisible(false);
		this.actionPrint.setVisible(false);
		this.actionPrintPreview.setVisible(false);

		this.actionFirst.setVisible(false);
		this.actionNext.setVisible(false);
		this.actionLast.setVisible(false);
		this.actionPre.setVisible(false);
		
		this.actionReset.setVisible(true);
		this.actionReset.setEnabled(true);
		this.btnReset.setEnabled(true);
	
		this.prmtPeriod.setEnabled(false);
		this.pkStartDate.setEnabled(false);
		this.pkEndDate.setEnabled(false);
		this.actionSave.setVisible(false);
		this.actionSubmit.setVisible(false);
		this.actionSubmit.setEnabled(false);
		
		this.prmtSellProject.setEnabled(false);
	}

	public void actionSubmit_actionPerformed(ActionEvent e) throws Exception {
		if (this.prmtSellProject.getValue() == null || this.prmtSellProject.getValue() == "") {
			MsgBox.showInfo("����δ����������Ŀ�����ܱ���!");
			this.abort();
			return;
		}

//		if (this.prmtPeriod.getValue() == null || this.prmtPeriod.getValue() == "") {
//			MsgBox.showInfo("����δ�����½��ڼ䣬���ܱ���!");
//			this.abort();
//			return;
//		}

		if (this.pkStartDate.getValue() == null || this.pkStartDate.getValue() == "") {
			MsgBox.showInfo("����δ���������ʼ���ڣ����ܱ���!");
			this.abort();
			return;
		}
		if (this.pkEndDate.getValue() == null || this.pkEndDate.getValue() == "") {
			MsgBox.showInfo("����δ�������������ڣ����ܱ���!");
			this.abort();
			return;
		}
		Date startDate = SHEHelper.getDayStart((Date) this.pkStartDate.getValue());
		Date endDate = SHEHelper.getDayEnd((Date) this.pkEndDate.getValue());
		if (startDate.after(endDate)) {
			MsgBox.showInfo("������ʼ���ڲ��ܴ��ڽ����������!");
			this.abort();
			return;
		}
		Date balanceDate = SHEHelper.getDayEnd((Date) this.pkBalanceDate.getValue());
		if (startDate.after(balanceDate)) {
			MsgBox.showInfo("������ʼ���ڲ��ܴ��ڽ����������!");
			this.abort();
			return;
		}

		if (endDate.after(balanceDate)) {
			MsgBox.showInfo("����������ڲ��ܴ��ڽ����������!");
			this.abort();
			return;
		}
		super.actionSubmit_actionPerformed(e);
	}

	/**
	 * output storeFields method
	 */
	public void storeFields() {
		super.storeFields();
	}

	// ��ֹ��:δ�������տ
	private int getNoAudit(SellProjectInfo project) {
		FDCSQLBuilder builder = new FDCSQLBuilder();
		builder.appendSql("select count(*) count from t_bdc_fdcreceivingbill ");
		builder.appendSql("where FBillStatus in ('"+RevBillStatusEnum.SAVE_VALUE+"','"+RevBillStatusEnum.SUBMIT_VALUE+"','"+RevBillStatusEnum.AUDITING_VALUE+"') ");
		builder.appendSql("and FRevBizType in ('"+RevBizTypeEnum.CUSTOMER_VALUE+"','"+RevBizTypeEnum.PURCHASE_VALUE+"','"+RevBizTypeEnum.SINCERITY_VALUE+"','"+RevBizTypeEnum.AREACOMPENSATE_VALUE+"') ");
		builder.appendSql("and FSellProjectID ='"+project.getId()+"' ");
		this.appendFilterSql(builder, "FBizDate");

		IRowSet countSet = null;
		// ��ֹ����
		int estopCount = 0;
		try {
			countSet = builder.executeQuery();
			if(countSet.next()) {
				estopCount = countSet.getInt("count");
			}
		} catch (Exception e1) {
			this.handleException(e1);
		}
		return estopCount;
	}

	// ��ֹ��������������տ���տδ����ƾ֤
	private int getNoFiVouchered(SellProjectInfo project) {
		FDCSQLBuilder builder = new FDCSQLBuilder();
		builder.appendSql("select count(*) count from t_bdc_fdcreceivingbill ");
		builder.appendSql("where FBillStatus in ('"+RevBillStatusEnum.AUDITED_VALUE+"','"+RevBillStatusEnum.RECED_VALUE+"') ");
		builder.appendSql("and FRevBizType in ('"+RevBizTypeEnum.CUSTOMER_VALUE+"','"+RevBizTypeEnum.PURCHASE_VALUE+"','"+RevBizTypeEnum.SINCERITY_VALUE+"','"+RevBizTypeEnum.AREACOMPENSATE_VALUE+"') ");
		builder.appendSql("and (FFiVouchered=0 or FFiVouchered is null)  and FSellProjectID ='"+project.getId()+"' ");
		//���տ��ת��Ҳ��Ҫ����ƾ֤��û�к����տ��
//		builder.appendSql("and FRevBillType !='"+RevBillTypeEnum.TRANSFER_VALUE+"' ");
		this.appendFilterSql(builder, "FBizDate");

		IRowSet countSet = null;
		// ��ֹ����
		int estopCount = 0;
		try {
			countSet = builder.executeQuery();
			if (countSet.next()) {
				estopCount = countSet.getInt("count");
			}
		} catch (Exception e1) {
			this.handleException(e1);
		}
		return estopCount;
	}
	
	//�����ࣺ�����Ϲ��������˵������δ����ĳ����Ϲ���
	private int getSincerity(SellProjectInfo project)
	{
		FDCSQLBuilder builder3 = new FDCSQLBuilder();
		builder3.appendSql(" select count(*) count from T_SHE_SincerReceiveEntry sinEntry");
		builder3.appendSql(" left join t_she_sinceritypurchase sinPur on sinEntry.fheadid = sinPur.fid");
		builder3.appendSql(" where sinPur.FSellProjectID='" + project.getId().toString() + "'");
		builder3.appendSql(" and sinPur.FIsReceiveEnterAccount=1 ");
		builder3.appendSql("and FappAmount > (isnull(FactRevAmount,0) - isnull(FHasToFeeAmount,0) - isnull(FHasTransferredAmount,0) - isnull(FHasRefundmentAmount,0) - isnull(FHasAdjustedAmount,0)) ");
		this.appendFilterSql(builder3, "sinEntry.FCreateTime");
		IRowSet countSet3 = null;
		//�����δ��������
		int warningCount2 = 0;
		try {
			countSet3 = builder3.executeQuery();
			if(countSet3.next()) {
				warningCount2 = countSet3.getInt("count");
			}
		} catch (Exception e1) {
			this.handleException(e1);
		}
		return warningCount2;
	}

	// �����ࣺδ�������Ϲ��������Ϲ�������Ϲ������е��Ϲ���
	private int getNoAuditPurchase(SellProjectInfo project) {		
		FDCSQLBuilder builder3 = new FDCSQLBuilder();
		builder3.appendSql(" select count(*) count from t_she_purchase purchase ");
		builder3.appendSql(" where purchase.FSellProjectID='" + project.getId().toString() + "'");
		builder3.appendSql(" and (purchase.FPurchaseState='PurchaseApply' or purchase.FPurchaseState='PurchaseAuditing')");
		this.appendFilterSql(builder3, "purchase.FPurchaseDate");
		IRowSet countSet3 = null;
		// �Ϲ�δ������������
		int warningCount2 = 0;
		try {
			countSet3 = builder3.executeQuery();
			if(countSet3.next()) {
				warningCount2 = countSet3.getInt("count");
			}
		} catch (Exception e1) {
			this.handleException(e1);
		}
		return warningCount2;
	}
	
	// �����ࣺδ���˵�Ԥ����
	public int getNoAuditPrePur(SellProjectInfo project)
	{
		FDCSQLBuilder builder = new FDCSQLBuilder();
		builder.appendSql(" select count(*) count from t_she_purchase purchase ");
		builder.appendSql(" where purchase.FSellProjectID='" + project.getId().toString() + "'");
		builder.appendSql(" and (purchase.FPurchaseState='PrePurchaseApply')");
		this.appendFilterSql(builder, "purchase.FPrePurchaseDate");
		IRowSet countSet = null;
		// Ԥ��δ������������
		int warningCount = 0;
		try {
			countSet = builder.executeQuery();
			if(countSet.next()) {
				warningCount = countSet.getInt("count");
			}
		} catch (Exception e1) {
			this.handleException(e1);
		}
		return warningCount;
	}

	// �����ࣺ�Ϲ��������˵��Ϲ�������û�н����׸�
	private int getFisrtAmount(SellProjectInfo project) {
		FDCSQLBuilder builder = new FDCSQLBuilder();
		builder.appendSql(" select count(*) count from T_SHE_PurchasePayListEntry payList ");
		builder.appendSql(" left join t_she_purchase pur on payList.fheadid = pur.fid");
		builder.appendSql(" left join T_SHE_MoneyDefine define on payList.fmoneydefineid = define.fid");
		builder.appendSql(" where pur.FSellProjectID='" + project.getId().toString() + "' ");
		builder.appendSql("and FappAmount > (isnull(FactRevAmount,0) - isnull(FHasToFeeAmount,0) - isnull(FHasTransferredAmount,0) - isnull(FHasRefundmentAmount,0) - isnull(FHasAdjustedAmount,0)) ");
		builder.appendSql(" and pur.FPurchaseState='PurchaseAudit' and define.FMoneyType='FisrtAmount'");
		this.appendFilterSql(builder, "pur.FAuditTime");
		IRowSet countSet = null;
		int count = 0;
		// �Ϲ�����δ�����׸���������
		try {
			countSet = builder.executeQuery();
			if(countSet.next()) {
				count = countSet.getInt("count");
			}
		} catch (Exception e1) {
			this.handleException(e1);
		}
		return count;
	}

	// �����ࣺ�˷�δ������������
	private int getQuitRoom(SellProjectInfo project) {
		FDCSQLBuilder builder = new FDCSQLBuilder();
		builder.appendSql(" select count(*) count from t_she_quitRoom quitRoom ");
		builder.appendSql(" left join t_she_purchase purchase on quitRoom.fpurchaseid = purchase.fid ");
		builder.appendSql(" where purchase.FSellProjectID='" + project.getId().toString() + "'");
		builder.appendSql(" and (quitRoom.FState='1SAVED' or quitRoom.FState='2SUBMITTED' or quitRoom.FState='3AUDITTING')");
		this.appendFilterSql(builder, "quitRoom.FQuitDate");
		IRowSet countSet = null;
		int count = 0;
		// �˷�δ������������
		try {
			countSet = builder.executeQuery();
			if(countSet.next()) {
				count = countSet.getInt("count");
			}
		} catch (Exception e1) {
			this.handleException(e1);
		}
		return count;
	}

	// �����ࣺ�˷�������δ��������������
	private int getCanRefundmentAmount(SellProjectInfo project) {
		FDCSQLBuilder builder = new FDCSQLBuilder();
		builder.appendSql(" select quitRoom.fid quitRoomID,sum(payList.FLimitAmount) amount,sum(elsePayList.FLimitAmount) elseAmount from t_she_quitroom quitRoom ");
		builder.appendSql(" left join t_she_purchase pur on quitRoom.fpurchaseid = pur.fid ");
		builder.appendSql(" left join T_SHE_PurchasePayListEntry payList on pur.fid = payList.FHeadID ");
		builder.appendSql(" left join T_SHE_PurchaseElsePayListEntry elsePayList on pur.fid = elsePayList.FHeadID ");
		builder.appendSql(" where pur.FSellProjectID='" + project.getId().toString() + "'");
		builder.appendSql(" and quitRoom.FState='4AUDITTED' ");
		this.appendFilterSql(builder, "quitRoom.FQuitDate");
		builder.appendSql(" group by quitRoom.fid ");
		IRowSet countSet = null;
		int count = 0;
		// �˷�������δ��������������,����Ҫ�Ҹ�����ϸ��¼�Ŀ��˽�Ҫ������Ӧ����¼�Ŀ��˽��
		//ע�����ֻ��Ҫ�����������Ļ�����дû���⣬���Ҫ�Ҿ���Ľ��Ļ����SQL����������
		//��Ϊ������ϸ������Ӧ����ϸ���п����Ƕ�����һ��left join�Ļ����ý���ظ����ӣ���ֻ�Ҵ���0�ľ�û������
		try {
			countSet = builder.executeQuery();
			while (countSet.next()) {
				BigDecimal amount = countSet.getBigDecimal("amount");
				BigDecimal elseAmount = countSet.getBigDecimal("elseAmount");
				elseAmount = elseAmount==null?new BigDecimal(0):elseAmount;
				amount = amount==null?new BigDecimal(0):amount;
				if (amount.compareTo(new BigDecimal(0)) != 0 || elseAmount.compareTo(new BigDecimal(0))!=0) {
					count++;
				}
			}
		} catch (Exception e1) {
			this.handleException(e1);
		}
		
		return count;
	}

	// �����ࣺ���δ����������
	private int getPurchaseChange(SellProjectInfo project) {
		FDCSQLBuilder builder = new FDCSQLBuilder();
		builder.appendSql(" select count(*) count from t_she_purchaseChange change ");
		builder.appendSql(" left join t_she_purchase purchase on change.fpurchaseid = purchase.fid ");
		builder.appendSql(" where purchase.FSellProjectID='" + project.getId().toString() + "'");
		builder.appendSql(" and (change.FState='1SAVED' or change.FState='2SUBMITTED' or change.FState='3AUDITTING')");
		this.appendFilterSql(builder, "change.FChangeDate");
		IRowSet countSet = null;
		int count = 0;
		// ���δ������������
		try {
			countSet = builder.executeQuery();
			while (countSet.next()) {
				count = countSet.getInt("count");
			}
		} catch (Exception e1) {
			this.handleException(e1);
		}
		return count;
	}

	// �����ࣺ����δ������������
	private int getChangeRoom(SellProjectInfo project) {
		FDCSQLBuilder builder = new FDCSQLBuilder();
		builder.appendSql(" select count(*) count from t_she_changeRoom change ");
		builder.appendSql(" left join t_she_purchase purchase on change.FOldPurchaseID = purchase.fid ");
		builder.appendSql(" where purchase.FSellProjectID='" + project.getId().toString() + "'");
		builder.appendSql(" and (change.FState='1SAVED' or change.FState='2SUBMITTED' or change.FState='3AUDITTING')");
		this.appendFilterSql(builder, "change.FChangeDate");
		IRowSet countSet = null;
		int count = 0;
		// ����δ������������
		try {
			countSet = builder.executeQuery();
			while (countSet.next()) {
				count = countSet.getInt("count");
			}
		} catch (Exception e1) {
			this.handleException(e1);
		}
		return count;
	}
	
	
	//�����ࣺ����ɹ����𰴽ҵ�δ���빫������Ϲ�������
	private int getAccFundRevCount(SellProjectInfo project) throws BOSException, EASBizException
	{
		int count =0;
		Set purchaseSet = new HashSet();
		FDCSQLBuilder builder = new FDCSQLBuilder();
		//�����ҳ��Ѿ�������ɹ����𰴽ҵ��Ϲ���
		builder.appendSql(" select purchase.fid purchaseID from T_SHE_RoomLoan loan ");
		builder.appendSql(" left join t_she_purchase purchase on loan.FPurchaseID = purchase.fid ");
		builder.appendSql(" left join T_SHE_moneyDefine moneyDefine on loan.FMmType = moneyDefine.fid ");
		builder.appendSql(" where purchase.FSellProjectID='" + project.getId().toString() + "'");				
		builder.appendSql(" and loan.FAFMortgagedState=3 and moneyDefine.FMoneyType='AccFundAmount'");
		IRowSet countSet = null;
		try {
			countSet = builder.executeQuery();
			while (countSet.next()) {
				String purchaseid = countSet.getString("purchaseID");
				purchaseSet.add(purchaseid);			
			}
			} catch (Exception e) {
				this.handleException(e);
			}
			
		//Ȼ��鿴��Щ�Ϲ����Ƿ����빫����
			if(purchaseSet.size()>0)
			{
				builder = new FDCSQLBuilder();
				builder.appendSql(" select count(*) count from T_SHE_Purchase purchase ");
				builder.appendSql(" left join T_SHE_PurchasePayListEntry payList on payList.fHeadID = purchase.fid ");
				builder.appendSql(" left join T_SHE_moneyDefine moneyDefine on payList.fMoneyDefineID = moneyDefine.fid where ");
				builder.appendParam("purchase.fid", purchaseSet.toArray());
				builder.appendSql(" and purchase.FSellProjectID='" + project.getId().toString() + "'");
				builder.appendSql(" and payList.FappAmount > (isnull(payList.FactRevAmount,0) - isnull(payList.FHasToFeeAmount,0) - isnull(payList.FHasTransferredAmount,0) - isnull(payList.FHasRefundmentAmount,0) - isnull(payList.FHasAdjustedAmount,0)) ");
				builder.appendSql(" and moneyDefine.FMoneyType='AccFundAmount' and purchase.FPurchaseState='PurchaseAudit' ");
				this.appendFilterSql(builder, "purchase.FAuditTime ");
				try {
					countSet = builder.executeQuery();
					while (countSet.next()) {
						count = countSet.getInt("count");
					}
				} catch (Exception e) {
					this.handleException(e);
				}						
			}	
		return count;
	}
	
	//�����ࣺ����ɰ��ҵ�δ���밴�ҿ���Ϲ�������
	private int getLoanNoRevCount2(SellProjectInfo project) throws BOSException, EASBizException
	{
		int count =0;
		Set purchaseSet = new HashSet();
		FDCSQLBuilder builder = new FDCSQLBuilder();
		//�����ҳ��Ѿ�������ɰ��ҵ��Ϲ���
		builder.appendSql(" select purchase.fid purchaseID from T_SHE_RoomLoan loan ");
		builder.appendSql(" left join t_she_purchase purchase on loan.FPurchaseID = purchase.fid ");
		builder.appendSql(" left join T_SHE_moneyDefine moneyDefine on loan.FMmType = moneyDefine.fid ");
		builder.appendSql(" where purchase.FSellProjectID='" + project.getId().toString() + "'");				
		builder.appendSql(" and loan.FAFMortgagedState=3 and moneyDefine.FMoneyType='LoanAmount'");
		IRowSet countSet = null;
		try {
			countSet = builder.executeQuery();
			while (countSet.next()) {
				String purchaseid = countSet.getString("purchaseID");
				purchaseSet.add(purchaseid);			
			}
			} catch (Exception e) {
				this.handleException(e);
			}
			
		//Ȼ��鿴��Щ�Ϲ����Ƿ����밴�ҿ�
			if(purchaseSet.size()>0)
			{
				builder = new FDCSQLBuilder();
				builder.appendSql(" select count(*) count from T_SHE_Purchase purchase ");
				builder.appendSql(" left join T_SHE_PurchasePayListEntry payList on payList.fHeadID = purchase.fid ");
				builder.appendSql(" left join T_SHE_moneyDefine moneyDefine on payList.fMoneyDefineID = moneyDefine.fid where ");
				builder.appendParam("purchase.fid", purchaseSet.toArray());
				builder.appendSql(" and purchase.FSellProjectID='" + project.getId().toString() + "'");
				builder.appendSql(" and payList.FappAmount > (isnull(payList.FactRevAmount,0) - isnull(payList.FHasToFeeAmount,0) - isnull(payList.FHasTransferredAmount,0) - isnull(payList.FHasRefundmentAmount,0) - isnull(payList.FHasAdjustedAmount,0)) ");
				builder.appendSql(" and moneyDefine.FMoneyType='LoanAmount' and purchase.FPurchaseState='PurchaseAudit' ");
				this.appendFilterSql(builder, "purchase.FAuditTime ");
				try {
					countSet = builder.executeQuery();
					while (countSet.next()) {
						count = countSet.getInt("count");
					}
				} catch (Exception e) {
					this.handleException(e);
				}						
			}	
		return count;
	}
	
	//  ������������ѹ�����Ͱ��ҷ���һ��У�顣�������ҵķ������ʱ���ܻ������������⣬�Ȱ����ǲ�
//	//�����ࣺ����ɰ��ҵ�δ���밴�ҿ���Ϲ�������
//	private int getLoanNoRevCount(SellProjectInfo project) throws BOSException, EASBizException
//	{
//		int count = 0;
//		//�ҳ��Ѱ�����ɻ��߻�δ����İ��һ��߹�����
//		Set purchaseSet = new HashSet();
//		Map purchaseMap = new HashMap();
//		FDCSQLBuilder builder = new FDCSQLBuilder();
//		builder.appendSql(" select purchase.fid purchaseID,moneyDefine.FMoneyType moneyType from T_SHE_RoomLoan loan ");
//		builder.appendSql(" left join t_she_purchase purchase on loan.FPurchaseID = purchase.fid ");
//		builder.appendSql(" left join T_SHE_moneyDefine moneyDefine on loan.FMmType = moneyDefine.fid ");
//		builder.appendSql(" where purchase.FSellProjectID='" + project.getId().toString() + "'");				
//		builder.appendSql(" and loan.FAFMortgagedState='3' and (moneyDefine.FMoneyType='LoanAmount' or moneyDefine.FMoneyType='AccFundAmount')");
//		builder.appendSql(" order by moneyDefine.FMoneyType ");
//		IRowSet countSet = null;
//		try {
//			countSet = builder.executeQuery();
//			while (countSet.next()) {
//				String purchaseid = countSet.getString("purchaseID");
//				String moneyType = countSet.getString("moneyType");
//				if(purchaseMap.get(purchaseid)!=null)
//				{
//					String str = (String)purchaseMap.get(purchaseid);
//					purchaseMap.put(purchaseid, str+","+moneyType);
//				}else
//				{
//					purchaseSet.add(purchaseid);
//					purchaseMap.put(purchaseid, moneyType);
//				}				
//			}
//			} catch (Exception e) {
//				this.handleException(e);
//			}
//		
//		EntityViewInfo view = new EntityViewInfo();
//		FilterInfo filter = new FilterInfo();
//		filter.getFilterItems().add(new FilterItemInfo("id",purchaseSet,CompareType.INCLUDE));
//		SelectorItemCollection sel=new SelectorItemCollection();
//		sel.add("payType.id");
//		sel.add("payType.name");
//		sel.add("payType.payLists.id");
//		sel.add("payType.payLists.moneyDefine");
//		sel.add("payType.payLists.moneyDefine.moneyType");
//		view.setFilter(filter);
//		view.setSelector(sel);
//		PurchaseCollection purColl = PurchaseFactory.getRemoteInstance().getPurchaseCollection(view);
//		for(int i=0;i<purColl.size();i++)
//		{
//			PurchaseInfo pur = purColl.get(i);
//			SHEPayTypeInfo payInfo = pur.getPayType();
//			SelectorItemCollection sic=new SelectorItemCollection();			
//			sic.add("payLists.id");
//			sic.add("payLists.name");
//			sic.add("payLists.moneyDefine.*");
//			sic.add("payLists.moneyDefine.name");
//			sic.add("payLists.moneyDefine.moneyType");
//			payInfo = SHEPayTypeFactory.getRemoteInstance().getSHEPayTypeInfo(new ObjectUuidPK(payInfo.getId()), sic);
//			PayListEntryCollection payColl =  payInfo.getPayLists();
//			StringBuffer string = new StringBuffer();
//			for(int j=0;j<payColl.size();j++)
//			{
//				PayListEntryInfo payEntryInfo = payColl.get(j);
//				MoneyTypeEnum mon = payEntryInfo.getMoneyDefine().getMoneyType();
//				if(MoneyTypeEnum.LoanAmount.equals(mon))
//				{
//					if(string.length()>0)
//					{
//						string.append(",");
//						string = string.append(MoneyTypeEnum.LOANAMOUNT_VALUE);
//					}else
//					{
//						string = string.append(MoneyTypeEnum.LOANAMOUNT_VALUE);
//					}
//					
//				}else if(MoneyTypeEnum.AccFundAmount.equals(mon))
//				{
//					//��Ϊ�������������accfundAmount�϶�����ǰ���������⴦����
//					if(string.length()>0)
//					{
//						StringBuffer str = new StringBuffer();
//						str.append(MoneyTypeEnum.ACCFUNDAMOUNT_VALUE);
//						str.append(",");
//						string = str.append(string);
//					}else
//					{
//						string = string.append(MoneyTypeEnum.ACCFUNDAMOUNT_VALUE);
//					}
//				}
//			}
//			String moneyType = (String)purchaseMap.get(pur.getId().toString());
//			if(!moneyType.equals(string.toString()))
//			{
//				purchaseSet.remove(pur.getId().toString());
//			}
//		}	
//		if(purchaseSet.size()>0)
//		{
//			builder = new FDCSQLBuilder();
//			builder.appendSql(" select purchaseID,sum(appAmount) appAmount,sum(actRevAmount) revAmount from (");
//			builder.appendSql(" select purchase.fid purchaseID,payList.FappAmount appAmount,payList.FActRevAmount actRevAmount from T_SHE_Purchase purchase ");
//			builder.appendSql(" left join T_SHE_PurchasePayListEntry payList on payList.fHeadID = purchase.fid ");
//			builder.appendSql(" left join T_SHE_moneyDefine moneyDefine on payList.fMoneyDefineID = moneyDefine.fid where ");
//			builder.appendParam("purchase.fid", purchaseSet.toArray());
//			builder.appendSql(" and purchase.FSellProjectID='" + project.getId().toString() + "'");
//			builder.appendSql(" and payList.FappAmount > isnull(payList.FActRevAmount,0) and moneyDefine.FMoneyType='LoanAmount' ");
//			builder.appendSql(" and purchase.FPurchaseState='PurchaseAudit' ");
//			this.appendFilterSql(builder, "purchase.FAuditTime ");
//			builder.appendSql(" union ");
//			builder.appendSql(" select purchase.fid purchaseID,payList.FappAmount appAmount,payList.FActRevAmount actRevAmount from T_SHE_Purchase purchase ");
//			builder.appendSql(" left join T_SHE_PurchasePayListEntry payList on payList.fHeadID = purchase.fid ");
//			builder.appendSql(" left join T_SHE_moneyDefine moneyDefine on payList.fMoneyDefineID = moneyDefine.fid where ");
//			builder.appendParam("purchase.fid", purchaseSet.toArray());
//			builder.appendSql(" and purchase.FSellProjectID='" + project.getId().toString() + "'");
//			builder.appendSql(" and payList.FappAmount > isnull(payList.FActRevAmount,0) and moneyDefine.FMoneyType='AccFundAmount' ");
//			builder.appendSql(" and purchase.FPurchaseState='PurchaseAudit' ");
//			this.appendFilterSql(builder, "purchase.FAuditTime ");
//			builder.appendSql(" ) aa group by purchaseID");
//			try {
//				countSet = builder.executeQuery();
//				while (countSet.next()) {
//					String purchaseID = countSet.getString("purchaseID");
//					BigDecimal appAmount = countSet.getBigDecimal("appAmount");
//					BigDecimal revAmount = countSet.getBigDecimal("revAmount");
//					appAmount = appAmount==null?new BigDecimal(0):appAmount;
//					revAmount = revAmount==null?new BigDecimal(0):revAmount;
//					if(appAmount.compareTo(revAmount)==1)
//					{
//						count++;
//					}
//				}
//			} catch (Exception e) {
//				this.handleException(e);
//			}						
//		}		
//		return count;
//	}

	private void show(int type, int count, SellProjectInfo project, String str) {
		this.tblReport.checkParsed();
		IRow row = tblReport.addRow();
		this.showTblReport(type, row, count, project, str);
	}
	
	private int checkSaleBlance(Object obj,int count,String str,SellProjectInfo project)
	{
		int estopCount =0;
		if(count!=0)
		{
			if(obj instanceof QuestionTypeEnum)
			{
				if(QuestionTypeEnum.prompt.equals((QuestionTypeEnum)obj))
				{
					show(1, count, project, str);
				}else if(QuestionTypeEnum.estop.equals((QuestionTypeEnum)obj))
				{
					show(0, count, project, str);
					estopCount = count;
				}else
				{
					//��������ǲ���ʾ�Ǿ�ʲô������
				}
			}
		}
		return estopCount;
	}
	
	/**
	 *������
	 */
	protected void btnBalanceCheck_actionPerformed(ActionEvent e) throws Exception {
		this.tblReport.removeRows();
		SellProjectInfo project = (SellProjectInfo) this.prmtSellProject.getValue();
		// ��ֹ��:δ�������տ
		int estopCount = this.getNoAudit(project);
		if (estopCount != 0) {
			String str = "��δ�������տ";
			show(0, estopCount, project, str);
		}
		// ��ֹ��������������տ���տδ����ƾ֤
		int estopCount0 = this.getNoFiVouchered(project);
		if (estopCount0 != 0) {
			String str = "��δ����ƾ֤���տ";
			show(0, estopCount0, project, str);
		}
		
		//�����ࣺ��ѡ�����˵ĳ����Ϲ���δ�������� ��
		int estopCount1 = checkSaleBlance(saleBlanSetting.getSincerObject(),this.getSincerity(project),
				SaleBalanceEditUI.KEY_SINCER,project);
		
		//�����ࣺδ�������Ϲ��������Ϲ�������Ϲ������е��Ϲ���
		int estopCount2 = checkSaleBlance(saleBlanSetting.getNoAuditPur(),getNoAuditPurchase(project),
				SaleBalanceEditUI.KEY_NOAUDITPUR,project);
		
		// �����ࣺδ���˵�Ԥ��������Ԥ���������Ϲ���
		int estopCount3 = checkSaleBlance(saleBlanSetting.getNoApplyPrePur(),getNoAuditPrePur(project),
				SaleBalanceEditUI.KEY_NOAPPLYPREPUR,project);

		// �����ࣺ�Ϲ��������˵��Ϲ�������û�н����׸�
		int estopCount4 = checkSaleBlance(saleBlanSetting.getFirstAmountPur(),getFisrtAmount(project),
				SaleBalanceEditUI.KEY_FIRSTAMOUNTPUR,project);

		// �����ࣺ�˷�δ������������
		int estopCount5 = checkSaleBlance(saleBlanSetting.getNoAuditQuitRoom(),getQuitRoom(project),
				SaleBalanceEditUI.KEY_NOAUDITQUITROOM,project);
		

		// �����ࣺ�˷�������δ��������������
		int estopCount6 = checkSaleBlance(saleBlanSetting.getCanRefundmentAmount(),getCanRefundmentAmount(project),
				SaleBalanceEditUI.KEY_CANREFUNDMENTAMOUNT,project);
		
		// �����ࣺ���δ����������
		int estopCount7 = checkSaleBlance(saleBlanSetting.getNoAuditPurChange(),getPurchaseChange(project),
				SaleBalanceEditUI.KEY_NOAUDITPURCHANGE,project);

		// �����ࣺ����δ��������������
		int estopCount8 = checkSaleBlance(saleBlanSetting.getNoAuditChangeRoom(),getChangeRoom(project),
				SaleBalanceEditUI.KEY_NOAUDITCHANGEROOM,project);
		
//		//�����ࣺ����ɰ��ҵ�δ��ȡ���ҿ���Ϲ�������
//		int estopCount9 = checkSaleBlance(saleBlanSetting.getLoanNoRev(),getLoanNoRevCount(project),
//				SaleBalanceEditUI.KEY_LOANNOREV,project);
		//�����ࣺ����ɰ��ҵ�δ��ȡ���ҿ���Ϲ�������
		int estopCount9 = checkSaleBlance(saleBlanSetting.getLoanNoRev(),getLoanNoRevCount2(project),
				SaleBalanceEditUI.KEY_LOANNOREV,project);
		
		int estopCount10 = checkSaleBlance(saleBlanSetting.getAccFundNoRev(),getAccFundRevCount(project),
				SaleBalanceEditUI.KEY_ACCFUNDNOREV,project);
		
		if (estopCount == 0 && estopCount0 == 0 && estopCount1 == 0 && estopCount2 == 0 && estopCount3 == 0 && 
				estopCount4 == 0 && estopCount5 == 0 && estopCount6 == 0 && estopCount7 == 0 && estopCount8 == 0 && estopCount9 ==0 && estopCount10 ==0) {
			Date beginDate = (Date) this.pkStartDate.getValue();
			Date endDate = (Date) this.pkEndDate.getValue();
			Date bizDate = SHEHelper.getDayEnd((Date) this.pkBalanceDate.getValue());
			if (endDate == null) {
				MsgBox.showInfo("�½�������ڲ���Ϊ��!");
				this.abort();
			}
			if (endDate.after(beginDate) == false && endDate.equals(beginDate) == false) {
				MsgBox.showInfo("�½�������ڲ������½���ʼ����֮ǰ,û�н�ֹ��Ҳ������ʽ�½�!");
				this.abort();
			}
			if (endDate.after(bizDate)) {
				MsgBox.showInfo("�½�������ڲ������½��������֮��,û�н�ֹ��Ҳ������ʽ�½�!");
				this.abort();
			}
			this.btnBalanceConfirm.setEnabled(true);
		} else {
			this.btnBalanceConfirm.setEnabled(false);
		}
		super.btnBalanceCheck_actionPerformed(e);
	}

	protected void showTblReport(int type, IRow row, int count, SellProjectInfo project, String str) {
		if (type == 0) {
			row.getCell("problemType").setValue("��ֹ");
		} else {
			row.getCell("problemType").setValue("��ʾ");
		}
		row.getCell("problemType").getStyleAttributes().setLocked(true);
		row.getCell("problemDesc").setValue(project.getName() + "��" + count + str + "!");
		row.getCell("problemDesc").getStyleAttributes().setLocked(true);
	}

	public void appendFilterSql(FDCSQLBuilder builder, String proName) {		
		if (this.prmtSellProject.getValue() == null || this.prmtSellProject.getValue() == "") {
			MsgBox.showInfo("������Ŀ����Ϊ�ա�");
			this.abort();
			return;
		}

		if (this.prmtPeriod.getValue() == null || this.prmtPeriod.getValue() == "") {
			MsgBox.showInfo("�½��ڼ䲻��Ϊ�ա�");
			this.abort();
			return;
		}
		
		Date beginDate = (Date) this.pkStartDate.getValue();
		if (beginDate == null) {
			MsgBox.showInfo("�½���ʼ���ڲ���Ϊ��!");
			this.abort();
		}
		if (beginDate != null) {
			builder.appendSql(" and " + proName + " >= ? ");
			builder.addParam(FDCDateHelper.getSqlDate(beginDate));
		}
		
		Date endDate = (Date) this.pkEndDate.getValue();
//		Date bizDate = SHEHelper.getDayEnd((Date) this.pkBalanceDate.getValue());
//		if (endDate == null) {
//			MsgBox.showInfo("����������ڲ���Ϊ��!");
//			this.abort();
//		}
//		if (endDate.after(beginDate) == false && endDate.equals(beginDate) == false) {
//			MsgBox.showInfo("����������ڲ����ڽ�����ʼ����֮ǰ");
//			this.abort();
//		}
//		if (endDate.after(bizDate)) {
//			MsgBox.showInfo("����������ڲ����ڽ����������֮��!");
//			this.abort();
//		}
		if (endDate != null) {
			builder.appendSql(" and " + proName + " < ? ");
			builder.addParam(FDCDateHelper.getNextDay(endDate));
		}
	}

	/**
	 * output actionBalanceCheck_actionPerformed
	 */
	public void actionBalanceCheck_actionPerformed(ActionEvent e) throws Exception {
		super.actionBalanceCheck_actionPerformed(e);
	}

	/**
	 * output actionBalanceConfirm_actionPerformed
	 */
	public void actionBalanceConfirm_actionPerformed(ActionEvent e) throws Exception {
		SellProjectInfo project = (SellProjectInfo) this.prmtSellProject.getValue();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("sellProject.id",project.getId().toString()));
		filter.getFilterItems().add(new FilterItemInfo("startDate",this.pkStartDate.getValue()));
		filter.getFilterItems().add(new FilterItemInfo("operateType",SaleBalanceTypeEnum.Balance));
		if(SaleBalanceFactory.getRemoteInstance().exists(filter)){
			this.btnBalanceConfirm.setEnabled(false);
			this.btnBalanceCheck.setEnabled(false);
			this.btnReset.setEnabled(true);
			MsgBox.showInfo("���½��ڼ��Ѿ��½ᡣ");
			return;
		}
		
		if (this.getNoAudit(project) != 0 || this.getNoFiVouchered(project) != 0) {
			MsgBox.showInfo("���ڽ�ֹ�½��ҵ���������ݼ���в鿴��ϸ��������ɽ��������½�!");
			this.abort();
		} 
//		else if (this.getNoAuditPurchase(project) != 0 || this.getFisrtAmount(project) != 0 || this.getQuitRoom(project) != 0 || this.getCanRefundmentAmount(project) != 0
//				|| this.getPurchaseChange(project) != 0 || this.getChangeRoom(project) != 0 || this.getSincerity(project)!=0 || this.getNoAuditPrePur(project)!=0) {
//			if (MsgBox.showConfirm2New(this, "����������ҵ���������ݼ���в鿴��ϸ���,�Ƿ�����½�?") == MsgBox.YES) {
//				this.storeFields();
//				SaleBalanceInfo saleBalanceInfo = this.editData;
//				saleBalanceInfo.setId(BOSUuid.create(saleBalanceInfo.getBOSType()));
//
//				try {
//					SaleBalanceFactory.getRemoteInstance().addnew(saleBalanceInfo);
//					SettleMentFacadeFactory.getRemoteInstance().dealSaleBalance(new ObjectUuidPK(saleBalanceInfo.getId()));
//					this.btnBalanceConfirm.setEnabled(false);
//					this.btnReset.setEnabled(false);
//					MsgBox.showInfo("�½�ɹ���");
//					// ��һ���ڼ��Ƿ����
//					PeriodInfo nextPeriod = PeriodUtils.getNextPeriodInfo(saleBalanceInfo.getPeriod());
//					if (nextPeriod == null || nextPeriod.getId() == null) {
//						MsgBox.showInfo("��һ���ڼ䲻���ڣ�����������");
//						this.abort();
//					}else
//					{
//						SellProjectInfo projectInfo = saleBalanceInfo.getSellProject();
//						projectInfo.setSaleNowTerm(new Integer(nextPeriod.getNumber()).toString());
//						projectInfo.setSaleNowPeriod(nextPeriod);
//						SelectorItemCollection sels = new SelectorItemCollection();
//						sels.add("saleNowTerm");
//						sels.add("saleNowPeriod.id");
//						SellProjectFactory.getRemoteInstance().updatePartial(projectInfo, sels);
//					}
//					this.uiWindow.close();
//				} catch (Exception ee) {
//					SaleBalanceFactory.getRemoteInstance().delete(new ObjectUuidPK(saleBalanceInfo.getId()));
//					this.handleException(ee);
//					this.abort();
//				}
//			}
//		} 
		else {
			this.storeFields();
			SaleBalanceInfo saleBalanceInfo = this.editData;
			saleBalanceInfo.setId(BOSUuid.create(saleBalanceInfo.getBOSType()));

			try {
				SaleBalanceFactory.getRemoteInstance().addnew(saleBalanceInfo);
				SettleMentFacadeFactory.getRemoteInstance().dealSaleBalance(new ObjectUuidPK(saleBalanceInfo.getId()));
				this.btnBalanceConfirm.setEnabled(false);
				this.btnReset.setEnabled(false);
				MsgBox.showInfo("�½�ɹ���");
				// ��һ���ڼ��Ƿ����
				PeriodInfo nextPeriod = PeriodUtils.getNextPeriodInfo(saleBalanceInfo.getPeriod());
				if (nextPeriod == null || nextPeriod.getId() == null) {
					MsgBox.showInfo("��һ���ڼ䲻���ڣ�����������");
					this.abort();
				}else
				{
					SellProjectInfo projectInfo = saleBalanceInfo.getSellProject();
					projectInfo.setSaleNowTerm(new Integer(nextPeriod.getNumber()).toString());
					projectInfo.setSaleNowPeriod(nextPeriod);
					SelectorItemCollection sels = new SelectorItemCollection();
					sels.add("saleNowTerm");
					sels.add("saleNowPeriod.id");
					SellProjectFactory.getRemoteInstance().updatePartial(projectInfo, sels);
				}
				this.uiWindow.close();
			} catch (Exception ee) {
				SaleBalanceFactory.getRemoteInstance().delete(new ObjectUuidPK(saleBalanceInfo.getId()));
				this.handleException(ee);
				this.abort();
			}
		}
	}
	
	public SelectorItemCollection getSelectors() {
		SelectorItemCollection sels = new SelectorItemCollection();
		sels.add("*");
		sels.add("salePeriod.*");
		sels.add("saleNowPeriod.*");
		return super.getSelectors();
	}

	protected IObjectValue createNewData() {
		SaleBalanceInfo info = new SaleBalanceInfo();
		SellProjectInfo sellProject = (SellProjectInfo) this.getUIContext().get("sellProject");

		Date startDate = null;
		Date endDate = null;
		if (sellProject != null) {
			SelectorItemCollection sels = new SelectorItemCollection();
			sels.add("*");
			sels.add("salePeriod.*");
			sels.add("saleNowPeriod.*");
			try {
				sellProject = SellProjectFactory.getRemoteInstance().getSellProjectInfo(new ObjectUuidPK(sellProject.getId()),sels);
			} catch (EASBizException e2) {
				this.handleException(e2);
			} catch (BOSException e2) {
				this.handleException(e2);
			}
			info.setSellProject(sellProject);
			if(sellProject.getSaleNowPeriod()==null)
			{
				MsgBox.showInfo("��������Ŀ��û�н����½��ʼ�����뵽������Ŀ�½�״̬����г�ʼ��");
				this.abort();
			}else
			{
				info.setPeriod(sellProject.getSaleNowPeriod());
			}
			PeriodInfo periodInfo = sellProject.getSaleNowPeriod();
			startDate = periodInfo.getBeginDate();
			endDate = periodInfo.getEndDate();
			info.setStartDate(startDate);
			info.setEndDate(endDate);
		}
		info.setCreator(SysContext.getSysContext().getCurrentUserInfo());
		info.setBalanceDate(new Timestamp(System.currentTimeMillis()));
		info.setOperateType(SaleBalanceTypeEnum.Balance);
		info.setCreator(SysContext.getSysContext().getCurrentUserInfo());
		info.setCU(SysContext.getSysContext().getCurrentCtrlUnit());
		info.setCreateTime(new Timestamp(System.currentTimeMillis()));
		info.setLastBalancDate(new Timestamp(System.currentTimeMillis()));
		return info;
	}

	protected ICoreBase getBizInterface() throws Exception {
		return SaleBalanceFactory.getRemoteInstance();
	}

}