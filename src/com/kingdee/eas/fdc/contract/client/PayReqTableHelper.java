package com.kingdee.eas.fdc.contract.client;

import java.awt.Color;
import java.awt.Rectangle;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.servertable.KDTStyleConstants;
import com.kingdee.bos.ctrl.kdf.table.ICell;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTAction;
import com.kingdee.bos.ctrl.kdf.table.KDTDefaultCellEditor;
import com.kingdee.bos.ctrl.kdf.table.KDTEditHelper;
import com.kingdee.bos.ctrl.kdf.table.KDTIndexColumn;
import com.kingdee.bos.ctrl.kdf.table.KDTMergeManager;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectBlock;
import com.kingdee.bos.ctrl.kdf.table.KDTTransferAction;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.event.BeforeActionEvent;
import com.kingdee.bos.ctrl.kdf.table.event.BeforeActionListener;
import com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent;
import com.kingdee.bos.ctrl.kdf.table.event.KDTPropertyChangeEvent;
import com.kingdee.bos.ctrl.kdf.table.event.KDTPropertyChangeListener;
import com.kingdee.bos.ctrl.kdf.util.style.StyleAttributes;
import com.kingdee.bos.ctrl.kdf.util.style.Styles.HorizontalAlignment;
import com.kingdee.bos.ctrl.swing.KDFormattedTextField;
import com.kingdee.bos.ctrl.swing.KDLayout;
import com.kingdee.bos.metadata.data.SortType;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemCollection;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.fdc.basedata.DeductTypeCollection;
import com.kingdee.eas.fdc.basedata.DeductTypeInfo;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCBillWFFacadeFactory;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.basedata.client.FDCColorConstants;
import com.kingdee.eas.fdc.contract.CompensationOfPayReqBillCollection;
import com.kingdee.eas.fdc.contract.CompensationOfPayReqBillFactory;
import com.kingdee.eas.fdc.contract.CompensationOfPayReqBillInfo;
import com.kingdee.eas.fdc.contract.ContractBillInfo;
import com.kingdee.eas.fdc.contract.ContractChangeBillCollection;
import com.kingdee.eas.fdc.contract.ContractChangeBillInfo;
import com.kingdee.eas.fdc.contract.DeductOfPayReqBillCollection;
import com.kingdee.eas.fdc.contract.DeductOfPayReqBillEntryCollection;
import com.kingdee.eas.fdc.contract.DeductOfPayReqBillEntryFactory;
import com.kingdee.eas.fdc.contract.DeductOfPayReqBillEntryInfo;
import com.kingdee.eas.fdc.contract.DeductOfPayReqBillFactory;
import com.kingdee.eas.fdc.contract.DeductOfPayReqBillInfo;
import com.kingdee.eas.fdc.contract.FDCUtils;
import com.kingdee.eas.fdc.contract.GuerdonBillCollection;
import com.kingdee.eas.fdc.contract.GuerdonBillInfo;
import com.kingdee.eas.fdc.contract.GuerdonOfPayReqBillCollection;
import com.kingdee.eas.fdc.contract.GuerdonOfPayReqBillFactory;
import com.kingdee.eas.fdc.contract.GuerdonOfPayReqBillInfo;
import com.kingdee.eas.fdc.contract.PartAConfmOfPayReqBillCollection;
import com.kingdee.eas.fdc.contract.PartAConfmOfPayReqBillFactory;
import com.kingdee.eas.fdc.contract.PartAConfmOfPayReqBillInfo;
import com.kingdee.eas.fdc.contract.PartAOfPayReqBillCollection;
import com.kingdee.eas.fdc.contract.PartAOfPayReqBillFactory;
import com.kingdee.eas.fdc.contract.PartAOfPayReqBillInfo;
import com.kingdee.eas.fdc.contract.PayReqPrjPayEntryInfo;
import com.kingdee.eas.fdc.contract.PayReqUtils;
import com.kingdee.eas.fdc.contract.PayRequestBillCollection;
import com.kingdee.eas.fdc.contract.PayRequestBillEntryCollection;
import com.kingdee.eas.fdc.contract.PayRequestBillEntryInfo;
import com.kingdee.eas.fdc.contract.PayRequestBillFactory;
import com.kingdee.eas.fdc.contract.PayRequestBillInfo;
import com.kingdee.eas.fdc.finance.DeductBillEntryCollection;
import com.kingdee.eas.fdc.finance.DeductBillEntryFactory;
import com.kingdee.eas.fdc.finance.DeductBillEntryInfo;
import com.kingdee.eas.fi.cas.BillStatusEnum;
import com.kingdee.eas.fi.cas.PaymentBillInfo;
import com.kingdee.eas.framework.BillBaseCollection;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.MsgBox;
import com.kingdee.jdbc.rowset.IRowSet;

public final class PayReqTableHelper {
	private static final Logger logger = CoreUIObject.getLogger(PayReqUtils.class);

	public static final Color noEditColor = new Color(232, 232, 227);

	private PayRequestBillInfo editData = null;

	private PayRequestBillEditUI editUI = null;

	private HashMap bindCellMap = null;
	private KDTable table=null;
	public PayReqTableHelper(PayRequestBillEditUI editUI) {
		this.editUI = editUI;
		this.editData = editUI.editData;
		this.bindCellMap = editUI.bindCellMap;
	}

	/**
	 * 得到用于显示分录的table
	 * 
	 * @author sxhong 下午01:57:45
	 * @return
	 * @throws BOSException 
	 * @throws EASBizException 
	 * @throws BOSException
	 */
	KDTable createPayRequetBillTable(DeductTypeCollection deductTypeCollection) throws EASBizException, BOSException {
		/*
		 * 将表格的创建放在onLoad的后面使得editData及getUIContext()在创建表格时可用,以控制扣款项的创建
		 */
		// 隐藏以前的kdtEntrys用自己的kdtable替代
		Rectangle kdtRectangle = editUI.kdtEntrys.getBounds();
		editUI.kdtEntrys.setEnabled(false);
		editUI.kdtEntrys.setVisible(false);
		
		logger.debug("init table:");
		// 6列1头部0行的表格
		table = new KDTable(8, 1, 0);
		
		table.setBounds(kdtRectangle);
		// this.add(kdtEntrys, null);
		editUI.kDPanel1.add(table, new KDLayout.Constraints(KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT, kdtRectangle));
		
		// EXCEL风格的行列索引,测试用
//		 table.setHeadDisplayMode(KDTStyleConstants.HEAD_DISPLAY_EXCEL);
		// 隐藏行索引
		table.getIndexColumn().getStyleAttributes().setHided(true);
		table.setRefresh(false);
		table.getScriptManager().setAutoRun(false);
		StyleAttributes sa = table.getStyleAttributes();
		// 是否可编辑
		sa.setLocked(true);
		sa.setNumberFormat("###,##0.00");
		// 融合头部
		IRow headRow = table.getHeadRow(0);
		headRow.getCell(0).setValue(getRes("prjTable"));
		KDTMergeManager mm = table.getHeadMergeManager();
		mm.mergeBlock(0, 0, 0, 7, KDTMergeManager.SPECIFY_MERGE);
		//做调试用
//		FDCTableHelper.setDebugHead(table);
		initFixTable(table);
		// 在第7行插入应扣款项分录
		initDynamicTable(table,deductTypeCollection);
		int lastRowIdx = table.getRowCount() - 1; // 最后一行

		IRow row;
		calcTable();

		// 设置对齐方式
		sa = table.getColumn(1).getStyleAttributes();
		sa.setHorizontalAlign(HorizontalAlignment.RIGHT);
		//项目名称,合同名称向左对齐
		table.getCell(0, 1).getStyleAttributes().setHorizontalAlign(HorizontalAlignment.LEFT);
		table.getCell(1, 1).getStyleAttributes().setHorizontalAlign(HorizontalAlignment.LEFT);
		table.getCell(0, 6).getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
		table.getCell(1, 6).getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
		table.getCell(2, 6).getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
		table.getCell(2, 1).getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
		table.getCell(2, 3).getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);

		for (int i = 0; i < 2; i++) {
			row = table.getRow(lastRowIdx - i);
			for (int j = 1; j < 6; j += 2) {
				sa = row.getCell(j).getStyleAttributes();
				sa.setHorizontalAlign(HorizontalAlignment.RIGHT);
			}
		}
		setTableCellColorAndEdit(table);
		table.setRefresh(true);
		// 自动调整大小
		table.setAutoResize(true);
		table.getScriptManager().setAutoRun(true);
		// 固定宽度
		table.getIndexColumn().setWidthAdjustMode(KDTIndexColumn.WIDTH_FIXED);
		boolean isSeparate = editUI.isSeparate&&(FDCUtils.isContractBill(null, editData.getContractId()));
		table.getRow(5).getStyleAttributes().setHided(!isSeparate);
		// 隐藏增加工程款行
		table.getRow(6).getStyleAttributes().setHided(true);
		
		//设置表格拷贝的模式为值拷贝
		((KDTTransferAction)table.getActionMap().get(KDTAction.PASTE)).setPasteMode(KDTEditHelper.VALUE);
		
		table.getViewManager().setFreezeView(4, 0);
		
		return table;
	}

	private void initLayout(){
		
	}
	/**
	 * 奥园要求在付款申请单序时簿和付款申请单查询界面添加一列 "是否存在附件" 
	 * 由于可能多处需使用该功能,故抽取为方法  by Cassiel_peng
	 */
	public static Map handleAttachment(Set boIDS) throws BOSException{
		Map attachMap=new HashMap();
		FDCSQLBuilder builder=new FDCSQLBuilder();
		builder.appendSql("select fboid from t_bas_boattchasso where ");
		builder.appendParam("fboid", boIDS.toArray());
		IRowSet rowSet;
		try {
			rowSet = builder.executeQuery();
			while (rowSet.next()) {
				//如果该合同有业务相关附件就进行标记，否则不标记
				attachMap.put(rowSet.getString("fboid"),Boolean.TRUE);
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		return attachMap;
	}
	//处理最后审批人和最后审批时间 by cassiel_peng 2010-05-26
	public  static Map  handleAuditPersonTime(Set payReqIds) {
		Map auditMap = new HashMap();
		try {
			auditMap= FDCBillWFFacadeFactory.getRemoteInstance().getWFBillLastAuditorAndTime(payReqIds);
		} catch (EASBizException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (BOSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return auditMap;
	}
	
	/**
	 * 初始化表格的固定部分,初始化的时候加入绑定信息到bindCellmap
	 * 
	 * @author sxhong 下午03:19:15
	 */
	private void initFixTable(KDTable table) {
		// 添加15行,在第9行插入应扣款项分录
		table.addRows(15);
		IRow row;
		ICell cell;
		// 第一列
		//工程项目名称0, 0
		PayReqUtils.bindCell(table, 0, 0, getRes("curProject.name"), "curProject.displayName", bindCellMap);
		table.getCell(0, 1).getStyleAttributes().setNumberFormat("@");
		//合同名称1, 0
		PayReqUtils.bindCell(table, 1, 0, getRes("contractBill.contractName"), PayRequestBillContants.CONTRACTNAME, bindCellMap);
		table.getCell(1, 1).getStyleAttributes().setNumberFormat("@");
		//变更指令金额
		PayReqUtils.bindCell(table, 2, 0, getRes("changeAmt"), PayRequestBillContants.CHANGEAMT, bindCellMap);
		//合同付款次数
		PayReqUtils.bindCell(table, 3, 0, getRes("payTimes"), PayRequestBillContants.PAYTIMES, bindCellMap);
		//进度款项
		table.getCell(4, 0).setValue(getRes("scheduleAmt"));

		// 应扣款项下面的5行
		//应扣甲才扣款
		table.getCell(9, 0).setValue(getRes("payPartAMatlAmt"));
		//实付款
		table.getCell(10, 0).setValue(getRes("paid"));
		//余款
		table.getCell(11, 0).setValue(getRes("residue"));
		//本期计划付款
		PayReqUtils.bindCell(table, 12, 0, getRes("curPlannedPayment"), PayRequestBillContants.CURPLANNEDPAYMENT, bindCellMap);
		
		//本次申请
		table.getCell(13, 0).setValue(getRes("curReqPercent"));
		//PayReqUtils.bindCell(table, 12, 0, getRes("curReqPercent"), PayRequestBillContants.CURREQPERCENT, bindCellMap);
		//应付申请
		table.getCell(14, 0).setValue("应付申请%");
		
		// 第二列
		// 进度款项
		//合同内工程款
		table.getCell(4, 1).setValue(getRes("projectPriceInContract"));
		//预付款
		table.getCell(5, 1).setValue("预付款");
		//合同增加款项
		table.getCell(6, 1).setValue(getRes("addProjectAmt"));
		//奖励
		table.getCell(7, 1).setValue("奖励");// 增加奖励单在6行，其后面的加一
		//违约金
		table.getCell(8, 1).setValue("违约金");// 用违约金替代小计
//		table.getCell(7, 1).setValue(getRes("subtotal"));//小计

		// 第三列
		//结算金额
		PayReqUtils.bindCell(table, 2, 2, getRes("settleAmt"), PayRequestBillContants.SETTLEAMT, bindCellMap);
		//截至上期实付
		table.getCell(3, 2).setValue(getRes("lstAllPaid"));

		//本期欠付款
		PayReqUtils.bindCell(table, 12, 2, getRes("curBackPay"), PayRequestBillContants.CURBACKPAY, bindCellMap);

		//累计申请％
		table.getCell(13, 2).setValue(getRes("allReqPercent"));
		//累计累计申请％
		table.getCell(14, 2).setValue("累计应付申请%");

		
		// 第四列
		//截至上期累计申请
		table.getCell(3, 3).setValue(getRes("lstAllReq"));

		// 第五列
		//合同造价
		PayReqUtils.bindCell(table, 0, 5, getRes("contractPrice"), PayRequestBillContants.CONTRACTPRICE, bindCellMap);
		//最新造价
		PayReqUtils.bindCell(table, 1, 5, getRes("latestPrice"), PayRequestBillContants.LATESTPRICE, bindCellMap);
		//本申请单已付金额
		PayReqUtils.bindCell(table, 2, 5, getRes("payedAmt"), PayRequestBillContants.PAYEDAMT, bindCellMap);	
		//本期发生(原币)
		table.getCell(3, 4).setValue(getRes("curOccur")+"原币");
		//本期发生(本币)
		table.getCell(3, 5).setValue(getRes("curOccur")+"本币");
		//形象进度
		PayReqUtils.bindCell(table, 13, 4, getRes("imageSchedule"), PayRequestBillContants.IMAGESCHEDULE, bindCellMap);
		// 第六列
		//截至本期累计申请
		table.getCell(3, 6).setValue(getRes("curAllReq"));
		// 第七列
		//截至本期累计实付
		table.getCell(3, 7).setValue(getRes("curAllPaid"));

		/*
		 * 特殊数据绑定进度款，甲供材,实付款
		 */
		// 进度款项,
		//合同内工程款
		row = table.getRow(4);
		cell = row.getCell(2);// 上期累计实付
		PayReqUtils.bindCell(cell, PayRequestBillContants.LSTPRJALLPAIDAMT, bindCellMap);
		cell = row.getCell(3);// 上期累计申请
		PayReqUtils.bindCell(cell, PayRequestBillContants.LSTPRJALLREQAMT, bindCellMap);
		cell = row.getCell(4);// 发生额
		cell.getStyleAttributes().setLocked(false);
		PayReqUtils.bindCell(cell, PayRequestBillContants.PROJECTPRICEINCONTRACTORI, bindCellMap, true);
		cell = row.getCell(5);// 发生额(本币)
		cell.getStyleAttributes().setLocked(true);
		PayReqUtils.bindCell(cell, PayRequestBillContants.PROJECTPRICEINCONTRACT, bindCellMap, true);
		cell = row.getCell(6);// 累计申请
		PayReqUtils.bindCell(cell, PayRequestBillContants.PRJALLREQAMT, bindCellMap);
		
		//预付款款
		row = table.getRow(5);
		cell = row.getCell(2);// 上期累计实付
		PayReqUtils.bindCell(cell, PayRequestBillContants.LSTADVANCEALLPAID, bindCellMap);
		cell = row.getCell(3);// 上期累计申请
		PayReqUtils.bindCell(cell, PayRequestBillContants.LSTADVANCEALLREQ, bindCellMap);
		cell = row.getCell(4);// 发生额
		cell.getStyleAttributes().setLocked(false);
		PayReqUtils.bindCell(cell, PayRequestBillContants.ADVANCE, bindCellMap, true);
		cell = row.getCell(5);// 发生额(本币)
		cell.getStyleAttributes().setLocked(true);
		PayReqUtils.bindCell(cell, PayRequestBillContants.LOCALADVANCE, bindCellMap, true);
		cell = row.getCell(6);// 累计申请
		PayReqUtils.bindCell(cell, PayRequestBillContants.ADVANCEALLREQ, bindCellMap);
		cell = row.getCell(7);// 累计实付
		PayReqUtils.bindCell(cell, PayRequestBillContants.ADVANCEALLPAID, bindCellMap);
		
		//增加工程款（隐藏）
		row = table.getRow(6);
		cell = row.getCell(2);// 合同内增加工程款累计实付
		PayReqUtils.bindCell(cell, PayRequestBillContants.LSTADDPRJALLPAIDAMT, bindCellMap);
		cell = row.getCell(3);// 上期累计申请
		PayReqUtils.bindCell(cell, PayRequestBillContants.LSTADDPRJALLREQAMT, bindCellMap);
		cell = row.getCell(4);// 发生额
		cell.getStyleAttributes().setLocked(false);
		PayReqUtils.bindCell(cell, PayRequestBillContants.ADDPROJECTAMTORI, bindCellMap, true);
		cell = row.getCell(5);// 发生额(本币)
		cell.getStyleAttributes().setLocked(true);
		PayReqUtils.bindCell(cell, PayRequestBillContants.ADDPROJECTAMT, bindCellMap, true);
		cell = row.getCell(6);// 累计申请
		PayReqUtils.bindCell(cell, PayRequestBillContants.ADDPRJALLREQAMT, bindCellMap);

		// 奖励单
		row = table.getRow(7);
		cell = row.getCell(2);// 奖励单累计实付
		PayReqUtils.bindCell(cell, "lstGuerdonPaidAmt", bindCellMap);
		cell = row.getCell(3);// 奖励单上期累计申请
		PayReqUtils.bindCell(cell, "lstGuerdonReqAmt", bindCellMap);
		cell = row.getCell(4);// 奖励单发生额
		cell.getStyleAttributes().setLocked(true);
		PayReqUtils.bindCell(cell, "guerdonOriginalAmt", bindCellMap, true);
		cell = row.getCell(5);// 奖励单发生额（本币）
		cell.getStyleAttributes().setLocked(true);
		PayReqUtils.bindCell(cell, "guerdonAmt", bindCellMap, true);
		cell = row.getCell(6);// 奖励单累计申请
		PayReqUtils.bindCell(cell, "allGuerdonAmt", bindCellMap);
		
		//违约金
		row = table.getRow(8);
		cell = row.getCell(2);// 违约金累计实付
		PayReqUtils.bindCell(cell, "lstCompensationPaidAmt", bindCellMap);
		cell = row.getCell(3);// 违约金上期累计申请
		PayReqUtils.bindCell(cell, "lstCompensationReqAmt", bindCellMap);
		cell = row.getCell(4);// 违约金发生额
		cell.getStyleAttributes().setLocked(true);
		PayReqUtils.bindCell(cell, "compensationOriginalAmt", bindCellMap, true);
		cell = row.getCell(5);// 违约金发生额本币
		cell.getStyleAttributes().setLocked(true);
		PayReqUtils.bindCell(cell, "compensationAmt", bindCellMap, true);
		cell = row.getCell(6);// 违约金累计申请
		PayReqUtils.bindCell(cell, "allCompensationAmt", bindCellMap);
				
		// 甲供材
		row = table.getRow(9);
		cell = row.getCell(2);// 截至上期累计实付
		PayReqUtils.bindCell(cell, PayRequestBillContants.LSTAMATLALLPAIDAMT, bindCellMap);
		cell = row.getCell(3);// 截至上期累计申请
		PayReqUtils.bindCell(cell, PayRequestBillContants.LSTAMATLALLREQAMT, bindCellMap);
		cell = row.getCell(4);// 发生额
		//TODO 原币
		PayReqUtils.bindCell(cell, PayRequestBillContants.PAYPARTAMATLAMTORI, bindCellMap, true);
		cell = row.getCell(5);// 发生额本币
		PayReqUtils.bindCell(cell, PayRequestBillContants.PAYPARTAMATLAMT, bindCellMap, true);
		cell = row.getCell(6);//本期累计实付
		PayReqUtils.bindCell(cell, PayRequestBillContants.PAYPARTAMATLALLREQAMT, bindCellMap);
		cell = row.getCell(7);//本期累计实付
		PayReqUtils.bindCell(cell, PayRequestBillContants.PAYPARTAMATLALLPAIDAMT, bindCellMap);
		// 实付款
		row = table.getRow(10);
		cell = row.getCell(4); //原币
		PayReqUtils.bindCell(cell, PayRequestBillContants.CURPAID, bindCellMap, true);
		cell = row.getCell(5); //本币
		PayReqUtils.bindCell(cell, PayRequestBillContants.CURPAIDLOCAL, bindCellMap, true);

		// paytimes
		StyleAttributes sa = ((ICell) bindCellMap.get(PayRequestBillContants.PAYTIMES)).getStyleAttributes();
		// 格式化付款次数
		sa.setNumberFormat("###,##0");
		// 形象进度
		sa = ((ICell) bindCellMap.get(PayRequestBillContants.IMAGESCHEDULE)).getStyleAttributes();
		KDFormattedTextField txt = new KDFormattedTextField(KDFormattedTextField.BIGDECIMAL_TYPE);
		txt.setPrecision(2);
		txt.setMaximumValue(FDCHelper.ONE_HUNDRED);
		txt.setMinimumValue(FDCHelper._ONE_HUNDRED);
		KDTDefaultCellEditor editor = new KDTDefaultCellEditor(txt);
		((ICell) bindCellMap.get(PayRequestBillContants.IMAGESCHEDULE)).setEditor(editor);
		sa.setLocked(false);

		/*
		 * 融合表格
		 */
		KDTMergeManager mm = table.getMergeManager();

		// 融合前三行
		mm.mergeBlock(0, 1, 0, 3, KDTMergeManager.SPECIFY_MERGE);
		mm.mergeBlock(1, 1, 1, 3, KDTMergeManager.SPECIFY_MERGE);

		mm.mergeBlock(0, 4, 0, 5, KDTMergeManager.SPECIFY_MERGE);
		mm.mergeBlock(1, 4, 1, 5, KDTMergeManager.SPECIFY_MERGE);
		mm.mergeBlock(2, 4, 2, 5, KDTMergeManager.SPECIFY_MERGE);
		
		mm.mergeBlock(0, 6, 0, 7, KDTMergeManager.SPECIFY_MERGE);
		mm.mergeBlock(1, 6, 1, 7, KDTMergeManager.SPECIFY_MERGE);
		mm.mergeBlock(2, 6, 2, 7, KDTMergeManager.SPECIFY_MERGE);
		// 付款次数
		// mm.mergeBlock(3, 1, 3, 2, KDTMergeManager.SPECIFY_MERGE);
		// 进度款项
		mm.mergeBlock(4, 0, 8, 0, KDTMergeManager.SPECIFY_MERGE);
/*		table.getCell(4, 0).setValue(null);
		mm.mergeBlock(4, 0, 4, 1, KDTMergeManager.SPECIFY_MERGE);
		//奖励
		mm.mergeBlock(6, 0, 6, 1, KDTMergeManager.SPECIFY_MERGE);
		//违约金
		mm.mergeBlock(7, 0, 7, 1, KDTMergeManager.SPECIFY_MERGE);*/
		// mm.mergeBlock(4, 1, 4, 2, KDTMergeManager.SPECIFY_MERGE);
		// mm.mergeBlock(5, 1, 5, 2, KDTMergeManager.SPECIFY_MERGE);
		// mm.mergeBlock(6, 1, 6, 2, KDTMergeManager.SPECIFY_MERGE);
		// 应扣甲供材款
		mm.mergeBlock(9, 0, 9, 1, KDTMergeManager.SPECIFY_MERGE);
		// 实付款
		mm.mergeBlock(10, 0, 10, 1, KDTMergeManager.SPECIFY_MERGE);
		// 余款
		mm.mergeBlock(11, 0, 11, 6, KDTMergeManager.SPECIFY_MERGE);
		// 最後二行
		mm.mergeBlock(12, 5, 12, 7, KDTMergeManager.SPECIFY_MERGE);
		mm.mergeBlock(13, 5, 13, 7, KDTMergeManager.SPECIFY_MERGE);
		
		mm.mergeBlock(14, 5, 14, 7, KDTMergeManager.SPECIFY_MERGE);
		
		mm = null;

	}

	/**
	 * 初始化分录的动态部分,即应扣款项, 1.审核前扣款类型来源于基础资料,扣款类型,
	 * 其来自扣款单与申请单关联表(DeductBillOfPayReq)的过虑值
	 * 2.审核后扣款类型与金额都来自扣款单与申请单关联表(DeductBillOfPayReq)
	 * 
	 * @author sxhong Date 2006-9-29
	 * @param table
	 * @return
	 */
	private int initDynamicTable(KDTable table,DeductTypeCollection deductTypeCollection) {
		// 在第9行插入应扣款项分录,基础资料时用循环实现
		int base = 9;// 插入的基点
		int rows = 0;
		KDTMergeManager mm = table.getMergeManager();
		IRow row;
		String contractId = editData.getContractId();
		if (contractId == null || PayReqUtils.isConWithoutTxt(contractId)) {
			return base;
		}
		if (FDCBillStateEnum.AUDITTED.equals(editData.getState()) || FDCBillStateEnum.AUDITTING.equals(editData.getState())) {
			// 从DeductOfPayReqBill内取出数据
			DeductOfPayReqBillInfo info = null;
			EntityViewInfo view = new EntityViewInfo();
			FilterInfo filter = new FilterInfo();
			FilterItemCollection items = filter.getFilterItems();
			items.add(new FilterItemInfo("payRequestBill.id", editData.getId().toString()));
//			filter.getFilterItems().add(new FilterItemInfo("deductType.id", DeductTypeInfo.partAMaterialType, CompareType.NOTEQUALS));
			view.setFilter(filter);
			final SorterItemInfo sorterItemInfo = new SorterItemInfo("deductType.number");
			sorterItemInfo.setSortType(SortType.ASCEND);
			view.getSorter().add(sorterItemInfo);
			view.getSelector().add("deductType.number");
			view.getSelector().add("deductType.name");
			view.getSelector().add("*");
			try {

				DeductOfPayReqBillCollection c = DeductOfPayReqBillFactory.getRemoteInstance().getDeductOfPayReqBillCollection(view);
				rows = c.size();
				for (int i = 0; i < rows; i++) {
					info = c.get(i);
					// 把显示零的地方变成空
					if (info.getAllPaidAmt() != null && info.getAllPaidAmt().compareTo(FDCHelper.ZERO) == 0) {
						info.setAllPaidAmt(null);
					}
					if (info.getAllReqAmt() != null && info.getAllReqAmt().compareTo(FDCHelper.ZERO) == 0) {
						info.setAllReqAmt(null);
					}
					if (info.getAmount() != null && info.getAmount().compareTo(FDCHelper.ZERO) == 0) {
						info.setAmount(null);
					}
					if (info.getOriginalAmount() != null && info.getOriginalAmount().compareTo(FDCHelper.ZERO) == 0) {
						info.setOriginalAmount(null);
					}
					row = table.addRow(base + i);
					row.getCell(1).setValue(info.getDeductType().getName());
					row.getCell(1).getStyleAttributes().setNumberFormat("@");
					row.getCell(2).setValue(info.getAllPaidAmt());
					row.getCell(3).setValue(info.getAllReqAmt());
					row.getCell(4).setValue(info.getOriginalAmount());
					row.getCell(5).setValue(info.getAmount());
					row.getCell(6).setExpressions("=D" + (base + i + 1) + "+F" + (base + i + 1));
					row.getCell(7).setExpressions("=C" + (base + i + 1) + "+F" + (base + i + 1));

				}
			} catch (BOSException e) {
				handUIException(e);
			}

		} else {
			HashMap map = getDeductData(editData,deductTypeCollection);
			if (map.size() > 0) {
				DeductTypeInfo info = null;
				EntityViewInfo view = new EntityViewInfo();
				FilterInfo filter = new FilterInfo();
				filter.getFilterItems().add(new FilterItemInfo("isEnabled", Boolean.TRUE));
				view.setFilter(filter);
				final SorterItemInfo sorterItemInfo = new SorterItemInfo("number");
				sorterItemInfo.setSortType(SortType.ASCEND);
				view.getSorter().add(sorterItemInfo);
				view.getSelector().add("number");
				view.getSelector().add("name");
				try {

					DeductTypeCollection c = deductTypeCollection;//DeductTypeFactory.getRemoteInstance().getDeductTypeCollection(view);
					rows = c.size();
					Object[] arrays;
					for (int i = 0; i < c.size(); i++) {
						info = c.get(i);
						arrays = (Object[]) map.get(info.getId().toString());
						// System.out.println("number:"+inf.getNumber());
						// System.out.println("name:"+inf.getName());
						for (int j = 0; j < 3; j++) {
							if (arrays[j] instanceof BigDecimal && ((BigDecimal) arrays[j]).compareTo(FDCHelper.ZERO) == 0) {
								arrays[j] = null;
							}
						}
						row = table.addRow(base + i);
						row.getCell(1).setValue(info.getName());
						row.getCell(1).getStyleAttributes().setNumberFormat("@");
						row.getCell(2).setValue(arrays[0]);
						row.getCell(3).setValue(arrays[1]);
						row.getCell(4).setValue(arrays[3]);
						row.getCell(5).setValue(arrays[2]);
						row.getCell(6).setExpressions("=D" + (base + i + 1) + "+F" + (base + i + 1));
						row.getCell(7).setExpressions("=C" + (base + i + 1) + "+F" + (base + i + 1));
					}
				} catch (Exception e) {
					handUIException(e);
				}
			}
		}

		// 最后一行小计
		int lastRowIdx = base + rows;
		row = table.addRow(lastRowIdx);
		row.getCell(1).setValue(getRes("subtotal"));
		if(rows!=0){
			/*
			 * 小计计算
			 */
			StringBuffer exp;
			for (char c = 'C'; c <= 'H'; c++) {
				exp = new StringBuffer("=sum(");
				exp.append(c).append(base + 1).append(':');
				exp.append(c).append(lastRowIdx);
				exp.append(')');
				row.getCell(c - 'A').setExpressions(exp.toString());
			}
			table.getCell(base, 0).setValue(getRes("deductAmtItem"));
			mm.mergeBlock(base, 0, base + rows, 0, KDTMergeManager.SPECIFY_MERGE);
		}

		return lastRowIdx;
	}

	/**
	 * 分录的计算逻辑
	 * 
	 * @author sxhong Date 2007-4-5
	 * @param table
	 * @param lastRowIdx
	 */
	void calcTable() {
		int lastRowIdx = table.getRowCount() - 1; 
		/*
		 * 设置统计公式--进度款项,用sum内置函数来进行计算,用法类似于Excel 如sum(D3，D7);
		 */
		IRow row = null;
		// 合同内工程数
		row = table.getRow(4);
		row.getCell(6).setExpressions("=sum(D5,F5)");
		// row.getCell(6).setExpressions("=sum(C5)");
		row.getCell(7).setExpressions("=sum(C5,F5)");
		// 预付款
		row = table.getRow(5);
		row.getCell(6).setExpressions("=sum(D6,F6)");
		row.getCell(7).setExpressions("=sum(C6,F6)");
		// 增加工程款
		row = table.getRow(6);
		row.getCell(6).setExpressions("=sum(D7,F7)");
		// row.getCell(6).setExpressions("=sum(C6)");
		row.getCell(7).setExpressions("=sum(C7,F7)");
		// 奖励
		row = table.getRow(7);
		row.getCell(6).setExpressions("=sum(D8,F8)");
		// row.getCell(6).setExpressions("=sum(C7)");
		row.getCell(7).setExpressions("=sum(C8,F8)");
		
		// 违约金
		row = table.getRow(8);
		row.getCell(6).setExpressions("=sum(D9,F9)");
		// row.getCell(6).setExpressions("=sum(C7)");
		row.getCell(7).setExpressions("=sum(C9,F9)");
/*		// 小计
		row = table.getRow(7);
		row.getCell(2).setExpressions("=sum(c5,c7)");
		row.getCell(3).setExpressions("=sum(D5,D7)");
		row.getCell(4).setExpressions("=sum(E5,E7)");

		row.getCell(5).setExpressions("=sum(D8,E8)");
		// row.getCell(6).setExpressions("=sum(C8)");
		row.getCell(6).setExpressions("=sum(C8,E8)");
*/
		/*
		 * 甲供材款累计
		 */
		row = table.getRow(lastRowIdx - 5);
		// 应扣甲供材款”行、“本次申请原币”列应自动从款项调整中甲供扣款的“本次扣款金额”合计而来，而且不可编辑 by cassiel
		row.getCell(4).getStyleAttributes().setLocked(true);
//		row.getCell(4).getStyleAttributes().setLocked(false);
		row.getCell(6).setExpressions("=D" + (lastRowIdx - 4) + "+F" + (lastRowIdx - 4));
		row.getCell(7).setExpressions("=(C" + (lastRowIdx - 4) + "+F" + (lastRowIdx - 4) + ")");

		/*实付款
		 * 因为要初化完后才能定位应扣款项小计的位置,故放到这里来计算. 实付款 实付款＝进度款小计－应扣款项小计-应扣甲供材
		 * 调整为:实付款＝合同内工程款+预付款+奖励-违约金-应扣款项小计-应扣甲供材 by sxhong 2007/09/28
		 */
		int paidRowIdx = lastRowIdx - 4;
		row = table.getRow(paidRowIdx);
		ICell cell = null;
		StringBuffer exp;
		for (char c = 'C'; c <= 'H'; c++) {
			cell = row.getCell(c - 'A');
			exp = new StringBuffer("=");
			exp.append(c).append(5).append("+");			//合同内工程款
			exp.append(c).append(6).append("+");            //预付款
			exp.append(c).append(8).append("-");			//奖励
			exp.append(c).append(9).append("-");			//违约金
			exp.append(c).append(paidRowIdx - 1).append("-");//甲供材
			exp.append(c).append(paidRowIdx);				//应扣款小计
			cell.setExpressions(exp.toString());
		}
		/*
		 * 余款 余款＝最新造价－进度款小计
		 * 调整为 余款＝最新造价－合同内工程款 by sxhong 2007-9-28
		 * 调整为 余款＝最新造价－合同内工程款-预付款 by hpw 2009-7-22
		 * 调整为 余款＝最新造价－实付款小计  by hpw 2009-7-24
		 *  余额=最新造价-进度款项截止本期累计实付 by cassiel_peng 2010-05-28
		 */
//		table.getCell(paidRowIdx + 1, 6).setExpressions("=F2-G8");
//		table.getCell(paidRowIdx + 1, 7).setExpressions("=G2-(H5+H6)");
//		table.getCell(paidRowIdx + 1, 6).setExpressions("=G2-(H5+H6)");
//		table.getCell(paidRowIdx + 1, 7).setExpressions("=G2-H"+(lastRowIdx-3));
		table.getCell(paidRowIdx + 1, 7).setExpressions("=G2-H5");

		row = table.getRow(lastRowIdx-1);
		// 最新造价可能为零,不能直接使用计算公式的方法
		if (editData.getLatestPrice() != null && editData.getLatestPrice().compareTo(FDCHelper.ZERO) > 0) {
			//本次申请%=本期发生实付款/最新造价
			exp = new StringBuffer("=(");
			exp.append("F").append(5).append("/");
			exp.append("G2)*100");
			row.getCell(1).setExpressions(exp.toString());
			/*
			 * 累计申请%=本期累计申请/最新造价
			 */
			exp = new StringBuffer("=(");
			exp.append("G").append(5).append("/");
			exp.append("G2)*100");
			row.getCell(3).setExpressions(exp.toString());

			// 应付申请，应付累计申请
			row = table.getRow(lastRowIdx);
			exp = new StringBuffer("=(");
			exp.append("F").append(lastRowIdx-3).append("/");
			exp.append("G2)*100");
			row.getCell(1).setExpressions(exp.toString());
			exp = new StringBuffer("=(");
			exp.append("G").append(lastRowIdx-3).append("/");
			exp.append("G2)*100");
			row.getCell(3).setExpressions(exp.toString());
		}

		
		/**
		 * 所有的本期累计实付等于上期累计实付 ？？？
		 */
		for (int i = 4; i < lastRowIdx - 4; i++) {
			table.getCell(i, 7).setExpressions("=C" + (i + 1));
		}
	}

	/**
	 * 设置表格单元格的可编辑状态及颜色
	 * 
	 * @author sxhong Date 2006-9-28
	 */
	void setTableCellColorAndEdit(KDTable table) {

		// 设置计划付款行可以录入

		IRow row;
		ICell cell;
		int lastRowIdx = table.getRowCount() - 1;
		table.setRefresh(false);
		table.setEditable(true);
		table.setEnabled(true);
		StyleAttributes sa;
		sa = table.getStyleAttributes();
		// sa.setLocked(false);
		sa.setBackground(Color.WHITE);
		/*
		 * 可编辑单元格
		 */

		// 本期发生
		table.getCell(4, 4).getStyleAttributes().setLocked(false);
		table.getCell(4, 4).getStyleAttributes().setBackground(FDCColorConstants.requiredColor);
		table.getCell(5, 4).getStyleAttributes().setLocked(false);
		// 甲供材
		table.getCell(lastRowIdx - 4, 4).getStyleAttributes().setLocked(true);

		row = table.getRow(lastRowIdx - 1);
		row.getCell(1).getStyleAttributes().setLocked(false);
		row.getCell(3).getStyleAttributes().setLocked(false);
		row.getCell(6).getStyleAttributes().setLocked(false);
		table.getCell(lastRowIdx, 6).getStyleAttributes().setLocked(false);

		/*
		 * 颜色
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
		table.getCell(0, 6).getStyleAttributes().setBackground(noEditColor);
		table.getCell(1, 6).getStyleAttributes().setBackground(noEditColor);
		table.getCell(2, 6).getStyleAttributes().setBackground(noEditColor);
		table.getCell(2, 1).getStyleAttributes().setBackground(noEditColor);
		table.getCell(2, 3).getStyleAttributes().setBackground(noEditColor);
		table.getCell(lastRowIdx, 1).getStyleAttributes().setBackground(noEditColor);
		table.getCell(lastRowIdx, 3).getStyleAttributes().setBackground(noEditColor);

		// 付款次数
		table.getCell(3, 1).getStyleAttributes().setBackground(noEditColor);
		table.setRefresh(true);
		editUI.btnInputCollect.setEnabled(true);
		// table.repaint();
		// table.refresh();
		
		//去掉付款计划的格:
		for (int i = 4; i < table.getColumnCount(); i++) {
			StyleAttributes styleAttributes = table.getCell(lastRowIdx, i).getStyleAttributes();
			styleAttributes.setLocked(true);
			styleAttributes.setBackground(noEditColor);
			if(i==5){
				continue;
			}
			styleAttributes = table.getCell(lastRowIdx - 1, i).getStyleAttributes();
			styleAttributes.setLocked(true);
			styleAttributes.setBackground(noEditColor);
		}
		table.getCell(lastRowIdx-1, 1).getStyleAttributes().setLocked(true);
		table.getCell(lastRowIdx-1, 1).getStyleAttributes().setBackground(noEditColor);
		table.getCell(lastRowIdx-1, 3).getStyleAttributes().setLocked(true);
		table.getCell(lastRowIdx-1, 3).getStyleAttributes().setBackground(noEditColor);
	}

	/**
	 * 重载扣款单
	 * @param editData
	 * @param table
	 * @throws Exception
	 */
	void reloadDeductTable(PayRequestBillInfo editData,KDTable table,DeductTypeCollection c) throws Exception {
		int base = 9;
		IRow row;
		HashMap map = getDeductData(editData,c);
		if (map.size() > 0) {
			DeductTypeInfo info = null;

			try{
				Object[] arrays;
				for (int i = 0; i < c.size(); i++) {
					info = c.get(i);
					arrays = (Object[]) map.get(info.getId().toString());
					// System.out.println("number:"+inf.getNumber());
					// System.out.println("name:"+inf.getName());
					for (int j = 0; j < 4; j++) {
						if (arrays[j] instanceof BigDecimal && ((BigDecimal) arrays[j]).compareTo(FDCHelper.ZERO) == 0) {
							arrays[j] = null;
						}
					}
					row = table.getRow(base + i);
					if(row==null) return;
					row.getCell(1).setValue(info.getName());
					row.getCell(1).getStyleAttributes().setNumberFormat("@");
					row.getCell(2).setValue(arrays[0]);
					row.getCell(3).setValue(arrays[1]);
					row.getCell(4).setValue(arrays[3]);//发生额原币
					row.getCell(5).setValue(arrays[2]);
				}
			} catch (Exception e) {
				handUIException(e);
			}
		}
	}

	/**
	 * 为没有审核过的单据得到应扣款项的一个Map，其中： Key为DeductTypeInfo的ID Value 为Object[3]保存
	 * 0实付额,1累计额,2发生额,3发生额原币
	 * 
	 * @author sxhong Date 2006-10-26
	 * @return
	 */
	private HashMap getDeductData(PayRequestBillInfo editData,DeductTypeCollection deductTypeCollection) {
		String contractId = editData.getContractId();
		if (contractId == null) return null;

		HashMap map = new HashMap();
		EntityViewInfo view;
		FilterInfo filter;
		FilterItemCollection items;

		/*
		 * 
		 */
		view = new EntityViewInfo();
		filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("isEnabled", Boolean.TRUE));
		view.setFilter(filter);
		final SorterItemInfo sorterItemInfo = new SorterItemInfo("number");
		sorterItemInfo.setSortType(SortType.ASCEND);
		view.getSorter().add(sorterItemInfo);
		view.getSelector().add("id");

		try {
			DeductTypeInfo info = null;
			DeductTypeCollection c = deductTypeCollection;
			for (int i = 0; i < c.size(); i++) {
				// new Object[3]保存 0实付额,1累计额,2发生额,3发生额原币
				info = c.get(i);
				Object[] o = new Object[4];
				map.put(info.getId().toString(), o);
			}
		} catch (Exception e) {
			handUIException(e);
		}

		if (map.size() <= 0) {
			return map;
		}

		if (editUI.getOprtState().equals(OprtState.ADDNEW)) {
			/*
			 * 发生额,从DeductBillEntry过滤,过滤出已用扣款单放入notIncludeSet内
			 */
			Set notIncludeSet = new HashSet();
			view = new EntityViewInfo();
			FilterInfo filter2 = new FilterInfo();
			FilterItemCollection items2 = filter2.getFilterItems();
			items2.add(new FilterItemInfo("parent.PayRequestBill.contractId", contractId, CompareType.EQUALS));
			SelectorItemCollection selector = new SelectorItemCollection();
			selector.add("deductBillEntry.id");
			view.setFilter(filter2);

			try {// 要过滤的集合
				DeductOfPayReqBillEntryCollection c;
				c = DeductOfPayReqBillEntryFactory.getRemoteInstance().getDeductOfPayReqBillEntryCollection(view);
				DeductOfPayReqBillEntryInfo info;
				for (int i = 0; i < c.size(); i++) {
					info = c.get(i);
					notIncludeSet.add(info.getDeductBillEntry().getId().toString());
				}
			} catch (BOSException e1) {
				handUIException(e1);
			}

			view = new EntityViewInfo();
			filter = new FilterInfo();
			items = filter.getFilterItems();
			items = filter.getFilterItems();
			items.add(new FilterItemInfo("contractId", contractId, CompareType.EQUALS));
			items.add(new FilterItemInfo("hasApplied", Boolean.FALSE, CompareType.EQUALS));
			items.add(new FilterItemInfo("Parent.state", FDCBillStateEnum.AUDITTED_VALUE, CompareType.EQUALS));
			if (notIncludeSet.size() > 0) {
				items.add(new FilterItemInfo("id", notIncludeSet, CompareType.NOTINCLUDE));
			}
			view.setFilter(filter);
			try {
				DeductBillEntryInfo info;
				DeductBillEntryCollection c = DeductBillEntryFactory.getRemoteInstance().getDeductBillEntryCollection(view);
				for (int i = 0; i < c.size(); i++) {
					info = c.get(i);
					Object o = map.get(info.getDeductType().getId().toString());
					if (o != null) {
						Object[] arrays = (Object[]) o;
						BigDecimal v;
						// 发生额
						if (arrays[2] == null) {
							arrays[2] = info.getDeductAmt();
						} else {
							v = (BigDecimal) arrays[2];
							arrays[2] = v.add(info.getDeductAmt());
						}
						// 发生额原币
						if (arrays[3] == null) {
							arrays[3] = info.getDeductOriginalAmt();
						} else {
							v = (BigDecimal) arrays[3];
							arrays[3] = v.add(info.getDeductOriginalAmt());
						}
					}
				}
			} catch (BOSException e) {
				handUIException(e);
			}
			/*
			 * 累计额:实付,申请
			 */
			view = new EntityViewInfo();
			filter = new FilterInfo();
			items = filter.getFilterItems();
			items = filter.getFilterItems();
			items.add(new FilterItemInfo("payRequestBill.contractId", contractId, CompareType.EQUALS));
			view.setFilter(filter);
			try {
				DeductOfPayReqBillCollection c = DeductOfPayReqBillFactory.getRemoteInstance().getDeductOfPayReqBillCollection(view);
				DeductOfPayReqBillInfo info;
				for (int i = 0; i < c.size(); i++) {
					info = c.get(i);
					Object o = map.get(info.getDeductType().getId().toString());
					if (o != null) {
						Object[] arrays = (Object[]) o;
						BigDecimal v;
						BigDecimal amount = info.getAmount();
						/*
						 * 申请,是以前的所有发生额之和
						 */
						if (arrays[1] == null) {
							arrays[1] = amount;
						} else {
							v = (BigDecimal) arrays[1];
							arrays[1] = v.add(amount);
						}

						/*
						 * 实付,是以前的所有已实付的发生额之和
						 */
						if (!info.isHasPaid()) {
							continue;
						}

						if (arrays[0] == null) {
							arrays[0] = amount;
						} else {
							v = (BigDecimal) arrays[0];
							arrays[0] = v.add(amount);
						}

					}
				}

			} catch (BOSException e) {
				handUIException(e);
			}

		} else {// 新增结束
			// 取数据之前重新算一次
			try {
				DeductOfPayReqBillFactory.getRemoteInstance().reCalcAmount(editData.getId().toString());
			} catch (Exception e1) {
				handUIException(e1);
			}
			/*
			 * 保存后数据直接从DeductOfPayReqBill内取 累计额:实付,申请,发生额,发生额原币
			 */
			view = new EntityViewInfo();
			filter = new FilterInfo();
			items = filter.getFilterItems();
			items = filter.getFilterItems();
			// items.add(new
			// FilterItemInfo("payRequestBill.contractId",contractId,CompareType.EQUALS));
			items.add(new FilterItemInfo("payRequestBill.id", editData.getId().toString(), CompareType.EQUALS));
			view.setFilter(filter);
			try {
				DeductOfPayReqBillInfo info;
				DeductOfPayReqBillCollection c = DeductOfPayReqBillFactory.getRemoteInstance().getDeductOfPayReqBillCollection(view);
				for (int i = 0; i < c.size(); i++) {
					info = c.get(i);
					Object o = map.get(info.getDeductType().getId().toString());
					if (o != null) {
						Object[] arrays = (Object[]) o;
						/*
						 * 发生额原币
						 */
						arrays[3] = info.getOriginalAmount();
						/*
						 * 发生额
						 */
						arrays[2] = info.getAmount();

						/*
						 * 申请
						 */
						arrays[1] = info.getAllReqAmt();

						/*
						 * 实付
						 */
						arrays[0] = info.getAllPaidAmt();

					}
				}
			} catch (BOSException e) {
				handUIException(e);
			}

		}
		return map;
	}

	
	
	/**
	 * 在新增,保存时动态更新值 最新造价,结算金额,变更金额等
	 * @author sxhong  		Date 2006-9-22
	 */
	void updateDynamicValue (PayRequestBillInfo editData,ContractBillInfo contractBill,
			ContractChangeBillCollection collection, BillBaseCollection billBaseCollection) throws Exception{
		boolean isUpdateCell=bindCellMap.get(PayRequestBillContants.CHANGEAMT)!=null;
		//ContractBillInfo contractBill = ContractBillFactory.getRemoteInstance().getContractBillInfo(new ObjectUuidPK(BOSUuid.read(editData.getContractId())));
		BigDecimal amount = FDCHelper.ZERO;

		ContractChangeBillInfo billInfo;
		//变更金额累计=未结算变更+已结算变更
		for (Iterator iter = collection.iterator(); iter.hasNext();)
		{
			billInfo = (ContractChangeBillInfo) iter.next();
			if(billInfo.isHasSettled()){
				amount = amount.add(FDCHelper.toBigDecimal(billInfo.getBalanceAmount()));
			}else{
				amount = amount.add(FDCHelper.toBigDecimal(billInfo.getAmount()));
			}
		}
		
		editData.setChangeAmt(amount);
		if (isUpdateCell) {
			//设置变更签证金额
			if(amount!= null
					&& amount.compareTo(FDCHelper.ZERO) != 0){
				((ICell)bindCellMap.get(PayRequestBillContants.CHANGEAMT)).setValue(amount);
			}else{
				((ICell)bindCellMap.get(PayRequestBillContants.CHANGEAMT)).setValue(null);
			}			
		}
		
		editData.setSettleAmt(contractBill.getSettleAmt());
		if (isUpdateCell) {
			// 结算金额
			if(contractBill.getSettleAmt()!= null
//					&& contractBill.getSettleAmt().compareTo(FDCHelper.ZERO) != 0
					){
				((ICell)bindCellMap.get(PayRequestBillContants.SETTLEAMT)).setValue(contractBill.getSettleAmt());
			}else{
				((ICell)bindCellMap.get(PayRequestBillContants.SETTLEAMT)).setValue(null);
			}
			
		}
		amount =FDCHelper.ZERO;
		
		// 根据结算单的状态来设置最新造价的值,已结算就为结算额
		if (!contractBill.isHasSettled())
		{
			/*
			 * 万科最新要求,未结算的合同最新造价=合同金额+变更金额+奖励-索赔,扣款(已被申请单关联的) 
			 * by sxhong 2007/09/28
			 */
			//奖励
/*			直接用SQL
 			BigDecimal guerdonAmt = FDCHelper.ZERO;
			filter = new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("contract.id", contractBill.getId()));
			filter.getFilterItems().add(new FilterItemInfo("state", FDCBillStateEnum.AUDITTED_VALUE));
			filter.getFilterItems().add(new FilterItemInfo("isGuerdoned", Boolean.TRUE));
			view = new EntityViewInfo();
			view.setFilter(filter);
			view.getSelector().add("amount");
			GuerdonBillCollection coll = GuerdonBillFactory.getRemoteInstance().getGuerdonBillCollection(view);
			GuerdonBillInfo info;
			for (Iterator iter = coll.iterator(); iter.hasNext();)
			{
				info = (GuerdonBillInfo) iter.next();
				guerdonAmt = guerdonAmt.add(FDCHelper.toBigDecimal(info.getAmount()));
			}
			
			//扣款,该合同下被付款申请单关联的扣款之和
			BigDecimal deductAmt = FDCHelper.ZERO;
			view=new EntityViewInfo();
			view.setFilter(new FilterInfo());
			view.getSelector().add("amount");
			view.getFilter().appendFilterItem("payRequestBill.contractId", contractBill.getId());
			DeductOfPayReqBillCollection c = DeductOfPayReqBillFactory.getRemoteInstance().getDeductOfPayReqBillCollection(view);
			for(int i=0;i<c.size();i++){
				deductAmt=deductAmt.add(FDCHelper.toBigDecimal(c.get(i).getAmount()));
			}
			//索赔
			BigDecimal compenseAmt = FDCHelper.ZERO;
			filter = new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("contract.id", contractBill.getId()));
			filter.getFilterItems().add(new FilterItemInfo("state", FDCBillStateEnum.AUDITTED_VALUE));
			filter.getFilterItems().add(new FilterItemInfo("isCompensated", Boolean.TRUE));
			view = new EntityViewInfo();
			view.setFilter(filter);
			view.getSelector().add("amount");
			CompensationBillCollection compenseColl = CompensationBillFactory.getRemoteInstance().getCompensationBillCollection(view);
			CompensationBillInfo compenseInfo;
			for (Iterator iter = compenseColl.iterator(); iter.hasNext();)
			{
				compenseInfo = (CompensationBillInfo) iter.next();
				compenseAmt = compenseAmt.add(FDCHelper.toBigDecimal(compenseInfo.getAmount()));
			}*/
			//奖励
			BigDecimal guerdonAmt=FDCHelper.ZERO;
			BigDecimal guerdonOriginalAmt=FDCHelper.ZERO;
			BigDecimal compenseAmt=FDCHelper.ZERO;
			BigDecimal compenseOriginalAmt=FDCHelper.ZERO;
			BigDecimal deductAmt=FDCHelper.ZERO;
			BigDecimal deductOriginalAmt=FDCHelper.ZERO;
			FDCSQLBuilder builder=new FDCSQLBuilder();
			builder.appendSql("select sum(famount) as amount,sum(foriginalamount) as originalamount from T_CON_GuerdonBill where  fcontractid =? AND fstate = ? AND fisGuerdoned = 1");
			builder.addParam(contractBill.getId().toString());
			builder.addParam(FDCBillStateEnum.AUDITTED_VALUE);
			IRowSet rowSet = builder.executeQuery();
			if(rowSet.size()==1){
				rowSet.next();
				guerdonAmt=FDCHelper.toBigDecimal(rowSet.getBigDecimal("amount"));
				guerdonOriginalAmt=FDCHelper.toBigDecimal(rowSet.getBigDecimal("originalamount"));
			}
			
			//违约
			builder.clear();
			builder.appendSql("select sum(famount) as amount,sum(foriginalamount) as originalamount from T_CON_CompensationBill where  fcontractid =? AND fstate = ? AND fisCompensated = 1");
			builder.addParam(contractBill.getId().toString());
			builder.addParam(FDCBillStateEnum.AUDITTED_VALUE);
			rowSet = builder.executeQuery();
			if(rowSet.size()==1){
				rowSet.next();
				compenseAmt=FDCHelper.toBigDecimal(rowSet.getBigDecimal("amount"));
				compenseOriginalAmt=FDCHelper.toBigDecimal(rowSet.getBigDecimal("originalamount"));
			}
			
			//扣款
			builder.clear();
			builder.appendSql("select sum(famount) as amount,sum(foriginalamount) as originalamount from T_CON_DeductOfPayReqBill " +
					"where fpayRequestBillId in (select fid from T_CON_PayRequestBill where fcontractid=?)");
			builder.addParam(contractBill.getId().toString());
			rowSet = builder.executeQuery();
			if(rowSet.size()==1){
				rowSet.next();
				deductAmt=FDCHelper.toBigDecimal(rowSet.getBigDecimal("amount"));
				deductOriginalAmt=FDCHelper.toBigDecimal(rowSet.getBigDecimal("originalamount"));
			}
			
			/*
			 * 万科最新要求,未结算的合同最新造价=合同金额+变更金额+奖励-索赔,扣款(已被申请单关联的) 
			 * by sxhong 2007/09/28
			 * 
			 * 经罗忠慧确认,现系统最新造价=合同金额+变更金额
			 * by hpw 2009/05/11
			 * 
			 * 更改成用统一接口取合同最新造价
			 * 
			 */
			amount = (BigDecimal) FDCUtils.getLastAmt_Batch(null, new String[]{contractBill.getId().toString()}).get(contractBill.getId().toString());
//			amount = contractBill.getAmount().add(FDCHelper.toBigDecimal(editData.getChangeAmt()));
//			amount = amount.add(guerdonAmt).subtract(compenseAmt).subtract(deductAmt);
			
		}else{
			amount=contractBill.getSettleAmt();
		}
		
		editData.setLatestPrice(amount);
		if (isUpdateCell) {
			if(amount!= null
//					&& amount.compareTo(FDCHelper.ZERO) != 0
					){
				((ICell)bindCellMap.get(PayRequestBillContants.LATESTPRICE)).setValue(amount);
			}else{
				//editData.setLatestPrice(null);
				((ICell)bindCellMap.get(PayRequestBillContants.LATESTPRICE)).setValue(null);
			}			
		}
		
		if(isUpdateCell){
			StringBuffer exp;
			int lastRowIdx=editUI.getDetailTable().getRowCount()-1;
			IRow row=editUI.getDetailTable().getRow(lastRowIdx);
			//最新造价可能为零,不能直接使用计算公式的方法
			
	/*		if(editData.getLatestPrice()!=null&&editData.getLatestPrice().compareTo(FDCHelper.ZERO)>0){
				exp=new StringBuffer("=(");
				exp.append("E").append(lastRowIdx-2).append("/");
				exp.append("F2)*100");
				row.getCell(1).setExpressions(exp.toString());
			}else{
				row.getCell(1).setValue(null);
			}*/
			/*
			 * 累计申请%=本期累计申请/最新造价
			 */
			if(FDCHelper.isPositiveBigDecimal(editData.getLatestPrice())){
/*				exp=new StringBuffer("=(");
				exp.append("G").append(lastRowIdx-2).append("/");
				exp.append("G2)*100");
				row.getCell(1).setExpressions(exp.toString());
			
				
				exp=new StringBuffer("=(");
				exp.append("H").append(lastRowIdx-2).append("/");
				exp.append("G2)*100");
				row.getCell(3).setExpressions(exp.toString());*/
				
//				exp = new StringBuffer("=(");
//				exp.append("F").append(lastRowIdx - 2).append("/");
//				exp.append("G2)*100");
//				row.getCell(1).setExpressions(exp.toString());
//				/*
//				 * 累计申请%=本期累计申请/最新造价
//				 */
//				exp = new StringBuffer("=(");
//				exp.append("G").append(lastRowIdx - 2).append("/");
//				exp.append("G2)*100");
//				row.getCell(3).setExpressions(exp.toString());
				
				
				//本次申请%=本期发生实付款/最新造价
				exp = new StringBuffer("=(");
				exp.append("F").append(5).append("/");
				exp.append("G2)*100");
				row.getCell(1).setExpressions(exp.toString());
				/*
				 * 累计申请%=本期累计申请/最新造价
				 */
				exp = new StringBuffer("=(");
				exp.append("G").append(5).append("/");
				exp.append("G2)*100");
				row.getCell(3).setExpressions(exp.toString());

				// 应付申请，应付累计申请
				row = table.getRow(lastRowIdx);
				exp = new StringBuffer("=(");
				exp.append("F").append(lastRowIdx-3).append("/");
				exp.append("G2)*100");
				row.getCell(1).setExpressions(exp.toString());
				exp = new StringBuffer("=(");
				exp.append("G").append(lastRowIdx-3).append("/");
				exp.append("G2)*100");
				row.getCell(3).setExpressions(exp.toString());
				
				
			}else{
				row.getCell(1).setValue(null);
				row.getCell(3).setValue(null);
			}
		}
		updateLstReqAmt(editData, isUpdateCell);
		
	
		
        //设置付款次数为合同的付款次数 从付款单中过滤        
        final int size = billBaseCollection.size();
		//        editData.setPayTimes(c.size());
        amount=FDCHelper.ZERO;
        if(editData!=null&&editData.getId()!=null){
        	for(Iterator iter=billBaseCollection.iterator();iter.hasNext();){
        		PaymentBillInfo tmp = (PaymentBillInfo)iter.next();
        		if(tmp.getFdcPayReqID().equals(editData.getId().toString())){
        			amount.add(FDCHelper.toBigDecimal(tmp.getAmount()));
        		}
        	}     	
        }
    	editData.setPayedAmt(amount);   
        
		editData.setPayTimes(size);
        if(isUpdateCell){
        	((ICell)bindCellMap.get(PayRequestBillContants.PAYTIMES)).setValue(String.valueOf(size));
        	((ICell)bindCellMap.get(PayRequestBillContants.PAYEDAMT)).setValue(amount);
        }
	}

	/**
	 * 
	 * @author sxhong  		Date 2007-4-5
	 * @param editData
	 * @param isUpdateCell
	 * @param contractBill
	 * @throws BOSException
	 */
	void updateLstReqAmt(PayRequestBillInfo editData, boolean isUpdateCell) throws BOSException {
		BigDecimal lstReqAmt = FDCHelper.ZERO;
		BigDecimal lstaddProjectAmt = FDCHelper.ZERO;
		BigDecimal lstpayPartAMatAmt = FDCHelper.ZERO;
		FilterInfo filter;
		EntityViewInfo view;
		
		view = new EntityViewInfo();
		view.getSelector().add("projectPriceInContract");
		view.getSelector().add("addProjectAmt");
		view.getSelector().add("payPartAMatlAmt");
		view.getSelector().add("hasClosed");
		view.getSelector().add("entrys.projectPriceInContract");
		view.getSelector().add("entrys.addProjectAmt");
		view.getSelector().add("entrys.payPartAMatlAmt");
		view.getSelector().add("entrys.amount");
		view.getSelector().add("completePrjAmt");
		view.getSelector().add("originalAmount");
		
		filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("createTime",editData.getCreateTime(),CompareType.LESS));
		filter.getFilterItems().add(new FilterItemInfo("contractId",editData.getContractId()));
		
		view.setFilter(filter);
		PayRequestBillCollection cols = PayRequestBillFactory.getRemoteInstance().getPayRequestBillCollection(view);
		//截至上期间累计申请关闭则取分录累计，未关闭则取实际申请取申请请单金额 by hpw 2010.9.22
		for(Iterator it = cols.iterator();it.hasNext();){
			PayRequestBillInfo info = (PayRequestBillInfo) it.next();
			if(info.isHasClosed()){
				for(Iterator iter=info.getEntrys().iterator();iter.hasNext();){
					PayRequestBillEntryInfo entry = (PayRequestBillEntryInfo)iter.next();
					lstReqAmt = FDCHelper.add(lstReqAmt,entry.getAmount());
					lstaddProjectAmt = FDCHelper.add(lstaddProjectAmt,entry.getAmount());
					lstpayPartAMatAmt = FDCHelper.add(lstpayPartAMatAmt,entry.getAmount());
				}
				if(info.getCompletePrjAmt()!=null&&info.getOriginalAmount()!=null&&info.getCompletePrjAmt().compareTo(info.getOriginalAmount())!=0){
					lstReqAmt = FDCHelper.add(lstReqAmt,FDCHelper.subtract(info.getCompletePrjAmt(), info.getOriginalAmount()));
					lstaddProjectAmt = FDCHelper.add(lstaddProjectAmt,FDCHelper.subtract(info.getCompletePrjAmt(), info.getOriginalAmount()));
					lstpayPartAMatAmt = FDCHelper.add(lstpayPartAMatAmt,FDCHelper.subtract(info.getCompletePrjAmt(), info.getOriginalAmount()));
				}
			}else{
				lstReqAmt = FDCHelper.add(lstReqAmt,info.getProjectPriceInContract());
				lstaddProjectAmt = FDCHelper.add(lstaddProjectAmt,info.getAddProjectAmt());
				lstpayPartAMatAmt = FDCHelper.add(lstpayPartAMatAmt,info.getPayPartAMatlAmt());
			}
		}
		FDCSQLBuilder builder = new FDCSQLBuilder();
//		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
//		String sqlDate = sdf.format(editData.getCreateTime());
//		builder.appendSql("select isnull(sum(fprojectPriceInContract),0) fprojectPriceInContract,isnull(sum(faddProjectAmt),0) faddProjectAmt,isnull(sum(fpayPartAMatlAmt),0) fpayPartAMatlAmt from t_con_payrequestbillentry where fparentid in(select fid from t_con_payrequestbill where fcontractid = ?  and fcreateTime < ? ) ");
//		builder.addParam(editData.getContractId());
//		builder.addParam("{ ts \'"+sqlDate+" }");
//		// {ts'2010-06-26 22:27:24'}
//		logger.info(builder.getTestSql());
//		RowSet rs = builder.executeQuery();
//		try {
//			while(rs.next()){
//				lstReqAmt.add(rs.getBigDecimal(1));
//				lstaddProjectAmt.add(rs.getBigDecimal(2));
//				lstpayPartAMatAmt.add(rs.getBigDecimal(3));
//			}
//		} catch (SQLException e1) {
//			// TODO Auto-generated catch block
//			handUIException(e1);
//		}
		/*
		 * 上次累计申请
		 * 得到本合同对应原付款申请单中累计申请额最大的也就是最近的一次
		 */
//		view=new EntityViewInfo();
//		filter = new FilterInfo();
//		FilterItemCollection items = filter.getFilterItems();
//		items.add(new FilterItemInfo("createTime",editData.getCreateTime(),CompareType.LESS));
//		items.add(new FilterItemInfo("contractId", editData.getContractId()));
//		view.setFilter(filter);
//		SorterItemInfo sorterItemInfo = new SorterItemInfo("createTime");
//		sorterItemInfo.setSortType(SortType.DESCEND);
//		view.getSorter().add(sorterItemInfo);
//		view.getSelector().add("createTime");
//		view.getSelector().add("prjAllReqAmt");
//		view.getSelector().add("addPrjAllReqAmt");
//		view.getSelector().add("payPartAMatlAllReqAmt");
//		view.getSelector().add("entrys.projectPriceInContract");
//		view.getSelector().add("entrys.addProjectAmt");
//		view.getSelector().add("entrys.payPartAMatlAmt");

//		PayRequestBillCollection c = PayRequestBillFactory.getRemoteInstance().getPayRequestBillCollection(view);
//		PayRequestBillEntryInfo entryInfo = null;
//		Iterator iter=c.iterator();
//		if(iter.hasNext()){
//			PayRequestBillInfo info=(PayRequestBillInfo)iter.next();
/*					editData.setLstPrjAllReqAmt(info.getPrjAllReqAmt());
//			editData.setLstAddPrjAllReqAmt(info.getAddPrjAllReqAmt());
//			editData.setLstAMatlAllReqAmt(info.getPayPartAMatlAllReqAmt());
//*/			
//			if(isUpdateCell){
//				((ICell)bindCellMap.get(PayRequestBillContants.LSTPRJALLREQAMT)).setValue(info.getPrjAllReqAmt());
//				((ICell)bindCellMap.get(PayRequestBillContants.LSTADDPRJALLREQAMT)).setValue(info.getAddPrjAllReqAmt());
//				((ICell)bindCellMap.get(PayRequestBillContants.LSTAMATLALLREQAMT)).setValue(info.getPayPartAMatlAllReqAmt());
//			}
//			editData.setLstPrjAllReqAmt(info.getPrjAllReqAmt());
//			editData.setLstAddPrjAllReqAmt(info.getAddPrjAllReqAmt());
//			editData.setLstAMatlAllReqAmt(info.getPayPartAMatlAllReqAmt());
//			
//		}else{
//			if(isUpdateCell){
//				((ICell)bindCellMap.get(PayRequestBillContants.LSTPRJALLREQAMT)).setValue(null);
//				((ICell)bindCellMap.get(PayRequestBillContants.LSTADDPRJALLREQAMT)).setValue(null);
//				((ICell)bindCellMap.get(PayRequestBillContants.LSTAMATLALLREQAMT)).setValue(null);
//			}
//			editData.setLstPrjAllReqAmt(null);
//			editData.setLstAddPrjAllReqAmt(null);
//			editData.setLstAMatlAllReqAmt(null);
//		}
//			for(Iterator it = info.getEntrys().iterator();it.hasNext();){
//				entryInfo = (PayRequestBillEntryInfo) it.next();
//				lstReqAmt = lstReqAmt.add(entryInfo.getProjectPriceInContract());
//				lstaddProjectAmt = lstaddProjectAmt.add(entryInfo.getAddProjectAmt());
//				lstpayPartAMatAmt = lstpayPartAMatAmt.add(entryInfo.getPayPartAMatlAmt());
//			}
//			editData.setLstPrjAllReqAmt(lstReqAmt);
//			editData.setLstAddPrjAllReqAmt(lstaddProjectAmt);
//			editData.setLstAMatlAllReqAmt(lstpayPartAMatAmt);
			
//		}
		
		editData.setLstPrjAllReqAmt(lstReqAmt);
		editData.setLstAddPrjAllReqAmt(lstaddProjectAmt);
	    editData.setLstAMatlAllReqAmt(lstpayPartAMatAmt);
		
		if(isUpdateCell){
			((ICell)bindCellMap.get(PayRequestBillContants.LSTPRJALLREQAMT)).setValue(lstReqAmt);
			((ICell)bindCellMap.get(PayRequestBillContants.LSTADDPRJALLREQAMT)).setValue(lstaddProjectAmt);
			((ICell)bindCellMap.get(PayRequestBillContants.LSTAMATLALLREQAMT)).setValue(null);
		}
		
		if (editData.getState() == FDCBillStateEnum.SAVED || editData.getState() == FDCBillStateEnum.SUBMITTED) {
			if(PayReqUtils.isContractBill(editData.getContractId())){
				builder.clear();
				builder=new FDCSQLBuilder();
				builder.appendSql("select fprjPriceInConPaid as amount from T_CON_ContractBill where fid=?");
				builder.addParam(editData.getContractId());
				IRowSet rowSet = builder.executeQuery();
				if(rowSet!=null&&rowSet.size()==1){
					try {
						rowSet.next();
						setCellValue(PayRequestBillContants.LSTPRJALLPAIDAMT, rowSet.getBigDecimal("amount"));
						editData.setLstPrjAllPaidAmt(rowSet.getBigDecimal("amount"));
					} catch (SQLException e) {
						e.printStackTrace();
					}
					
				}
			}
		}
	}
	
	void updateLstAdvanceAmt(PayRequestBillInfo editData, boolean isUpdate) throws BOSException, EASBizException {
		BigDecimal lstAdvanceAllPaid = FDCHelper.ZERO;
		BigDecimal lstAdvanceAllReq = FDCHelper.ZERO;
		BigDecimal advance = FDCHelper.ZERO;
		BigDecimal locAdvance = FDCHelper.ZERO;
		BigDecimal advanceAllReq = FDCHelper.ZERO;
		if(!isUpdate){
			EntityViewInfo view = new EntityViewInfo();
			FilterInfo filter = new FilterInfo();
			view.setFilter(filter);
			filter.getFilterItems().add(new FilterItemInfo("contractId", editData.getContractId()));
			filter.getFilterItems().add(new FilterItemInfo("createTime", editData.getCreateTime(),CompareType.LESS));
//			view.getSelector().add("prjPayEntry.*");
			view.getSelector().add("prjPayEntry.id");
			view.getSelector().add("prjPayEntry.locAdvance");
			view.getSelector().add("entrys.paymentBill.billStatus");
			view.getSelector().add("entrys.paymentBill.prjPayEntry.advance");
			view.getSelector().add("entrys.paymentBill.prjPayEntry.locAdvance");
			PayRequestBillCollection c = PayRequestBillFactory.getRemoteInstance()
				.getPayRequestBillCollection(view);
			for(int i=0;i<c.size();i++){
				PayRequestBillInfo info = c.get(i);
				if(info.getId().equals(editData.getId())){
					continue;
				}
				PayRequestBillEntryCollection entry = info.getEntrys();
				if(entry!=null&&entry.size()>0){
					for(int j=0;j<entry.size();j++){
						PaymentBillInfo payment = info.getEntrys().get(j).getPaymentBill();
						if(BillStatusEnum.PAYED.equals(payment.getBillStatus())){
							if(payment.getPrjPayEntry()!=null){
								lstAdvanceAllPaid = FDCHelper.add(lstAdvanceAllPaid,payment.getPrjPayEntry().getLocAdvance());
							}
						}
					}
				}
				if(info.getPrjPayEntry()!=null){
					lstAdvanceAllReq = FDCHelper.add(lstAdvanceAllReq, info.getPrjPayEntry().getLocAdvance());
				}
			}
			if(FDCHelper.ZERO.compareTo(lstAdvanceAllPaid)==0){
				lstAdvanceAllPaid = null;
			}
			if(FDCHelper.ZERO.compareTo(lstAdvanceAllReq)==0){
				lstAdvanceAllReq = null;
			}
			((ICell)bindCellMap.get(PayRequestBillContants.LSTADVANCEALLPAID)).setValue(lstAdvanceAllPaid);
			((ICell)bindCellMap.get(PayRequestBillContants.LSTADVANCEALLREQ)).setValue(lstAdvanceAllReq);
			if(editData.getPrjPayEntry()!=null){
				//本期的申请及累计申请取本期的数据 by hpw 2009-07-28
				((ICell)bindCellMap.get(PayRequestBillContants.ADVANCE)).setValue(editData.getPrjPayEntry().getAdvance());
				((ICell)bindCellMap.get(PayRequestBillContants.LOCALADVANCE)).setValue(editData.getPrjPayEntry().getLocAdvance());
				((ICell)bindCellMap.get(PayRequestBillContants.ADVANCEALLREQ)).setValue(editData.getPrjPayEntry().getAdvanceAllReq());
			}
//			((ICell)bindCellMap.get(PayRequestBillContants.ADVANCEALLREQ)).setValue(lstAdvanceAllReq);
			((ICell)bindCellMap.get(PayRequestBillContants.ADVANCEALLPAID)).setValue(lstAdvanceAllPaid);
		} else{
			lstAdvanceAllPaid = FDCHelper.toBigDecimal(((ICell)bindCellMap.get(PayRequestBillContants.LSTADVANCEALLPAID)).getValue());
			lstAdvanceAllReq = FDCHelper.toBigDecimal(((ICell)bindCellMap.get(PayRequestBillContants.LSTADVANCEALLREQ)).getValue());
			advance = FDCHelper.toBigDecimal(((ICell)bindCellMap.get(PayRequestBillContants.ADVANCE)).getValue());
			locAdvance = FDCHelper.toBigDecimal(((ICell)bindCellMap.get(PayRequestBillContants.LOCALADVANCE)).getValue());
			advanceAllReq =FDCHelper.toBigDecimal(((ICell)bindCellMap.get(PayRequestBillContants.ADVANCEALLREQ)).getValue());
			PayReqPrjPayEntryInfo prjPayEntry = editData.getPrjPayEntry();
			if (prjPayEntry == null) {
				prjPayEntry = new PayReqPrjPayEntryInfo();
			}
			prjPayEntry.setLstAdvanceAllPaid(lstAdvanceAllPaid);
			prjPayEntry.setLstAdvanceAllReq(lstAdvanceAllReq);
			prjPayEntry.setAdvance(advance);
			prjPayEntry.setLocAdvance(locAdvance);
			prjPayEntry.setAdvanceAllReq(advanceAllReq);
			prjPayEntry.setAdvanceAllPaid(lstAdvanceAllPaid);
			editData.setPrjPayEntry(prjPayEntry);
		}
		
		
		
	}
	
	/**
	 * 合同下已审批状态的付款申请单的预付款-本次申请原币+本张单据的预付款-本次申请原币必须大于0
	 * @param editData
	 * @throws BOSException
	 */
	void checkAdvance(PayRequestBillInfo editData,Map bindCellMap) throws BOSException{
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		view.setFilter(filter);
		filter.getFilterItems().add(new FilterItemInfo("contractId", editData.getContractId()));
		filter.getFilterItems().add(new FilterItemInfo("state", FDCBillStateEnum.AUDITTED_VALUE));
		if(editData.getId()!=null){
			filter.getFilterItems().add(new FilterItemInfo("id", editData.getId().toString(),CompareType.NOTEQUALS));
		}
		view.getSelector().add("prjPayEntry.advance");
		PayRequestBillCollection c = PayRequestBillFactory.getRemoteInstance()
			.getPayRequestBillCollection(view);
		BigDecimal advance = FDCHelper.ZERO;
		if(c!=null){
			for(int i=0;i<c.size();i++){
				PayRequestBillInfo info = c.get(i);
				if(info.getPrjPayEntry()!=null){
					advance = FDCHelper.add(advance, info.getPrjPayEntry().getAdvance());
				}
			}
		}
		Object cellValue = ((ICell)bindCellMap.get(PayRequestBillContants.ADVANCE)).getValue();
		advance = FDCHelper.add(advance, cellValue);
		if (FDCHelper.ZERO.compareTo(advance) == 1) {
			MsgBox.showError("合同下已审批状态的付款申请单的 预付款本次申请原币 + 本张单据的预付款本次申请原币 必须大于0");
			SysUtil.abort();
		}
		
	}

	public void getAdvanceValueFromCell(PayRequestBillInfo editData,
			HashMap bindCellMap) {
//		if
//		PayReqPrjPayEntryInfo info = 
	}
	
	void reloadGuerdonValue(PayRequestBillInfo editData,BigDecimal amount) throws Exception{

    	if(!PayReqUtils.isContractBill(editData.getContractId())){
    		return;
    	}
//    	if(amount!=null){
//    		((ICell)bindCellMap.get("guerdonAmt")).setValue(amount);
//    		((ICell) bindCellMap.get("guerdonOriginalAmt")).setValue(FDCHelper
//					.toBigDecimal(amount).divide(editData.getExchangeRate(),
//							BigDecimal.ROUND_HALF_UP));
//		} else {
    		//进行刷新操作从数据库内取
    		BigDecimal originalamount = FDCHelper.ZERO;
    		amount=FDCHelper.ZERO;
    		BigDecimal lstPaidAmt=FDCHelper.ZERO;
    		BigDecimal lstReqAmt=FDCHelper.ZERO;
    		EntityViewInfo view=new EntityViewInfo();
    		view.getSelector().add("amount");
    		view.getSelector().add("originalAmount");
    		view.getSelector().add("hasPaid");
//    		view.getSelector().add("guerdon.exRate");
    		FilterInfo filter=new FilterInfo();
    		filter.appendFilterItem("payRequestBill.contractId", editData.getContractId());
    		view.setFilter(filter);
    		//TODO 再加一个时间过滤
    		Timestamp createTime = editData.getCreateTime();
//    		Calendar cal=Calendar.getInstance();
//    		cal.setTime(createTime);
//    		createTime.setDate(cal.getActualMaximum(Calendar.DATE));
//    		createTime.setHours(cal.getActualMaximum(Calendar.HOUR_OF_DAY)); //24小时制
//    		createTime.setMinutes(cal.getActualMaximum(Calendar.MINUTE));
//    		createTime.setSeconds(cal.getActualMaximum(Calendar.SECOND));
    		filter.getFilterItems().add(new FilterItemInfo("payRequestBill.createTime",createTime,CompareType.LESS_EQUALS));
    		GuerdonOfPayReqBillCollection c = GuerdonOfPayReqBillFactory.getRemoteInstance().getGuerdonOfPayReqBillCollection(view);
    		for(int i=0;i<c.size();i++){
    			GuerdonOfPayReqBillInfo info = c.get(i);
    			if(info.getAmount()!=null){
    				if(info.getPayRequestBill().getId().equals(editData.getId())){
    					amount=amount.add(FDCHelper.toBigDecimal(info.getAmount()));
    					originalamount = originalamount.add(FDCHelper.toBigDecimal(info.getOriginalAmount()));
    				}else{
	    				if(info.isHasPaid()){
	    					lstPaidAmt=info.getAmount().add(lstPaidAmt);
	    				}
	    				
	    				lstReqAmt=info.getAmount().add(lstReqAmt);
    				}
    			}
    		}
    		if(amount.compareTo(FDCHelper.ZERO)==0){
    			amount=null;
    		}
    		if(originalamount.compareTo(FDCHelper.ZERO)==0){
    			originalamount=null;
    		}
    		if(lstPaidAmt.compareTo(FDCHelper.ZERO)==0){
    			lstPaidAmt=null;
    		}
    		if(lstReqAmt.compareTo(FDCHelper.ZERO)==0){
    			lstReqAmt=null;
    		} 
    		((ICell)bindCellMap.get("guerdonAmt")).setValue(amount);
    		((ICell)bindCellMap.get("guerdonOriginalAmt")).setValue(originalamount);
    		((ICell)bindCellMap.get("lstGuerdonPaidAmt")).setValue(lstPaidAmt);
    		((ICell)bindCellMap.get("lstGuerdonReqAmt")).setValue(lstReqAmt);
    		
//    	}
    }
	
	/**
	 * 
	 * @author sxhong  		Date 2007-3-29
	 * @param objectValue
	 * @param contractBillId
	 * @throws BOSException
	 */
	void updateGuerdonValue(PayRequestBillInfo objectValue, String contractBillId,
			GuerdonOfPayReqBillCollection guerdonOfPayReqBillCollection,GuerdonBillCollection guerdonBillCollection) throws BOSException {
		FilterInfo filter;
		EntityViewInfo view;
		//奖励单
		BigDecimal lstPaidAmt=FDCHelper.ZERO;
		BigDecimal lstReqAmt=FDCHelper.ZERO;
		
		for(int i=0;i<guerdonOfPayReqBillCollection.size();i++){
			GuerdonOfPayReqBillInfo info = guerdonOfPayReqBillCollection.get(i);
			if(info.getAmount()!=null){
				if(info.isHasPaid()){
					lstPaidAmt=info.getAmount().add(lstPaidAmt);
				}
				
				lstReqAmt=info.getAmount().add(lstReqAmt);
			}
		}
		if(lstPaidAmt.compareTo(FDCHelper.ZERO)==0){
			lstPaidAmt=null;
		}
		if(lstReqAmt.compareTo(FDCHelper.ZERO)==0){
			lstReqAmt=null;
		}
		objectValue.put("lstGuerdonPaidAmt", lstPaidAmt);
		objectValue.put("lstGuerdonReqAmt", lstReqAmt);
		
		//带出默认的本次发生额:所有未申请的奖励单
		BigDecimal sum=FDCHelper.ZERO;
		BigDecimal sumOriginal=FDCHelper.ZERO;
		for (int i = 0; i < guerdonBillCollection.size(); i++) {
			GuerdonBillInfo item = guerdonBillCollection.get(i);
			if(item.getAmount()!=null){
				sum=sum.add(item.getAmount());
			}
			if(item.getOriginalAmount()!=null){
				sumOriginal=sumOriginal.add(item.getOriginalAmount());
			}
		}
		if(sum.compareTo(FDCHelper.ZERO)==0){
			sum=null;
		}
		if(sumOriginal.compareTo(FDCHelper.ZERO)==0){
			sumOriginal=null;
		}
		objectValue.put("guerdonAmt", sum);
		objectValue.put("guerdonOriginalAmt", sumOriginal);
		
		if(bindCellMap.get("guerdonAmt")!=null&&bindCellMap.get("guerdonOriginalAmt")!=null){
			((ICell)bindCellMap.get("guerdonAmt")).setValue(sum);
			((ICell)bindCellMap.get("guerdonOriginalAmt")).setValue(sumOriginal);
			((ICell)bindCellMap.get("lstGuerdonPaidAmt")).setValue(lstPaidAmt);
			((ICell)bindCellMap.get("lstGuerdonReqAmt")).setValue(lstReqAmt);
		}
	}
	
	void reloadCompensationValue(PayRequestBillInfo editData,BigDecimal amount) throws Exception{

    	if(!PayReqUtils.isContractBill(editData.getContractId())){
    		return;
    	}
//    	if(amount!=null){
//    		((ICell)bindCellMap.get("compensationAmt")).setValue(amount);
//    		((ICell)bindCellMap.get("compensationOriginalAmt")).setValue(FDCHelper
//					.toBigDecimal(amount).divide(editData.getExchangeRate(), BigDecimal.ROUND_HALF_UP));
//    	}else{
    		//进行刷新操作从数据库内取
    		BigDecimal originalamount = FDCHelper.ZERO;
    		amount=FDCHelper.ZERO;
    		BigDecimal lstPaidAmt=FDCHelper.ZERO;
    		BigDecimal lstReqAmt=FDCHelper.ZERO;
    		EntityViewInfo view=new EntityViewInfo();
    		FilterInfo filter=new FilterInfo();
    		filter.appendFilterItem("payRequestBill.contractId", editData.getContractId());
    		view.setFilter(filter);
    		//TODO 再加一个时间过滤
    		Timestamp createTime = editData.getCreateTime();
//    		Calendar cal=Calendar.getInstance();
//    		cal.setTime(createTime);
//    		createTime.setDate(cal.getActualMaximum(Calendar.DATE));
//    		createTime.setHours(cal.getActualMaximum(Calendar.HOUR_OF_DAY)); //24小时制
//    		createTime.setMinutes(cal.getActualMaximum(Calendar.MINUTE));
//    		createTime.setSeconds(cal.getActualMaximum(Calendar.SECOND));
    		filter.getFilterItems().add(new FilterItemInfo("payRequestBill.createTime",createTime,CompareType.LESS_EQUALS));
    		
    		CompensationOfPayReqBillCollection c = CompensationOfPayReqBillFactory.getRemoteInstance().getCompensationOfPayReqBillCollection(view);
    		for(int i=0;i<c.size();i++){
    			CompensationOfPayReqBillInfo info = c.get(i);
    			if(info.getAmount()!=null){
    				if(info.getPayRequestBill().getId().equals(editData.getId())){
    					amount=amount.add(FDCHelper.toBigDecimal(info.getAmount()));
    					originalamount = originalamount.add(FDCHelper.toBigDecimal(info.getOriginalAmount()));
    				}else{
	    				if(info.isHasPaid()){
	    					lstPaidAmt=info.getAmount().add(lstPaidAmt);
	    				}
	    				
	    				lstReqAmt=info.getAmount().add(lstReqAmt);
    				}
    			}
    		}
    		if(amount.compareTo(FDCHelper.ZERO)==0){
    			amount=null;
    		}
    		if(originalamount.compareTo(FDCHelper.ZERO)==0){
    			originalamount=null;
    		}
    		if(lstPaidAmt.compareTo(FDCHelper.ZERO)==0){
    			lstPaidAmt=null;
    		}
    		if(lstReqAmt.compareTo(FDCHelper.ZERO)==0){
    			lstReqAmt=null;
    		} 
    		((ICell)bindCellMap.get("compensationAmt")).setValue(amount);
    		((ICell)bindCellMap.get("compensationOriginalAmt")).setValue(originalamount);
    		((ICell)bindCellMap.get("lstCompensationPaidAmt")).setValue(lstPaidAmt);
    		((ICell)bindCellMap.get("lstCompensationReqAmt")).setValue(lstReqAmt);
    		
//    	}

    }
	
	/**
	 * 
	 * @author sxhong  		Date 2007-3-29
	 * @param objectValue
	 * @param contractBillId
	 * @throws BOSException
	 */
	void updateCompensationValue(PayRequestBillInfo objectValue, String contractBillId,
			CompensationOfPayReqBillCollection compensationOfPayReqBillCollection) throws BOSException {
		FilterInfo filter;
		EntityViewInfo view;
		//奖励单
		BigDecimal lstPaidAmt=FDCHelper.ZERO;
		BigDecimal lstReqAmt=FDCHelper.ZERO;
		for(int i=0;i<compensationOfPayReqBillCollection.size();i++){
			CompensationOfPayReqBillInfo info = compensationOfPayReqBillCollection.get(i);
			if(info.getAmount()!=null){
				if(info.isHasPaid()){
					lstPaidAmt=info.getAmount().add(lstPaidAmt);
				}
				
				lstReqAmt=info.getAmount().add(lstReqAmt);
			}
		}
		if(lstPaidAmt.compareTo(FDCHelper.ZERO)==0){
			lstPaidAmt=null;
		}
		if(lstReqAmt.compareTo(FDCHelper.ZERO)==0){
			lstReqAmt=null;
		}
		objectValue.put("lstCompensationPaidAmt", lstPaidAmt);
		objectValue.put("lstCompensationReqAmt", lstReqAmt);
		
		//违约不需要带出
		//带出默认的本次发生额:所有未申请的奖励单
		BigDecimal sum=FDCHelper.ZERO;
		BigDecimal sumOriginal=FDCHelper.ZERO;
/*		view=new EntityViewInfo();
		filter=new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("contract.id", contractBillId));
		filter.getFilterItems().add(new FilterItemInfo("state",FDCBillStateEnum.AUDITTED_VALUE,CompareType.EQUALS));
		filter.getFilterItems().add(new FilterItemInfo("isCompensated",String.valueOf(1),CompareType.NOTEQUALS));
		view.setFilter(filter);
		view.getSelector().add("id");
		view.getSelector().add("amount");
		view.getSelector().add("originalamount");
		view.getSelector().add("isCompensated");
		CompensationBillCollection compensationBillCollection = CompensationBillFactory.getRemoteInstance().getCompensationBillCollection(view);
		for (int i = 0; i < compensationBillCollection.size(); i++) {
			CompensationBillInfo item = compensationBillCollection.get(i);
			if(item.getAmount()!=null){
				sum=sum.add(item.getAmount());
			}
			if(item.getOriginalAmount()!=null){
				sumOriginal=sumOriginal(item.getOriginalAmount());
			}
		}
*/		
		if(sum.compareTo(FDCHelper.ZERO)==0){
			sum=null;
		}
		if(sumOriginal.compareTo(FDCHelper.ZERO)==0){
			sumOriginal=null;
		}
		objectValue.put("compensationAmt", sum);
		objectValue.put("compensationOriginalAmt", sumOriginal);
		
		if(bindCellMap.get("compensationAmt")!=null&&bindCellMap.get("compensationOriginalAmt")!=null){
			((ICell)bindCellMap.get("compensationAmt")).setValue(sum);
			((ICell)bindCellMap.get("compensationOriginalAmt")).setValue(sumOriginal);
			((ICell)bindCellMap.get("lstCompensationPaidAmt")).setValue(lstPaidAmt);
			((ICell)bindCellMap.get("lstCompensationReqAmt")).setValue(lstReqAmt);
		}
	}
	
	/*
	 * 得到资源文件
	 */
	private String getRes(String resName) {
		return PayReqUtils.getRes(resName);
	}

	private void handUIException(Exception e) {
		editUI.handUIException(e);
	}
	
	public ICell getCell(String key){
		Object object = bindCellMap.get(key);
		if(object instanceof ICell){
			return (ICell)object;
		}
		
		return null;
	}
	
	public Object getCellValue(String key){
		ICell cell=getCell(key);
		if(cell!=null){
			return cell.getValue();
		}
		return null;
	}
	
	public void setCellValue(String key,Object value){
		ICell cell=getCell(key);
		if(cell!=null){
			cell.setValue(value);
		}
	}
	
	public void debugCellExp(){
//		for(int i=4;i<9;i++){
//			for(int j=4;i<7;j++){
//				table.getCell(i, j).getStyleAttributes().setLocked(false);
//				
//			}
//		}
/*		table.setEditable(true);
		table.setEnabled(true);
		table.getStyleAttributes().setLocked(false);*/

	}
	
	void setLstPrict(){
		if(editUI.getOprtState()==OprtState.ADDNEW){
			//新增的时候将未被关联的奖励，扣款，违约考虑进来
			BigDecimal latestPrice=FDCHelper.toBigDecimal(getCellValue("latestPrice"));
			BigDecimal gueronAmt=FDCHelper.toBigDecimal(getCellValue("guerdonAmt"));//奖励单
			BigDecimal compensationAmt=FDCHelper.toBigDecimal(getCellValue("compensationAmt"));//违约金
			ICell cell=table.getCell(table.getRowCount()-6, 5);
			BigDecimal psubTotal=FDCHelper.toBigDecimal(cell.getValue());//扣款单小计
			latestPrice=latestPrice.add(gueronAmt).subtract(compensationAmt).subtract(psubTotal);
			setCellValue("latestPrice", latestPrice);
		}
	}
	
	/**
	 * 重载甲供材扣款单
	 * @param editData
	 * @param amount
	 * @throws Exception
	 */
	void reloadPartAValue(PayRequestBillInfo editData,BigDecimal amount) throws Exception{
		
//		if(!PayReqUtils.isContractBill(editData.getContractId())){
//    		return;
//    	}
		BigDecimal originalAmount = FDCHelper.ZERO;
		if(amount == null){
			amount = FDCHelper.ZERO;
		}
		if(amount.compareTo(FDCHelper.ZERO)==1){
			amount = FDCHelper.ZERO;
			((ICell)bindCellMap.get("payPartAMatlAmt")).setValue(amount);
		}
    	//进行刷新操作从数据库内取
    	BigDecimal lstPaidAmt = FDCHelper.ZERO;
    	BigDecimal lstReqAmt = FDCHelper.ZERO;
    	BigDecimal allReqAmt = FDCHelper.ZERO;
    	EntityViewInfo view=new EntityViewInfo();
    	FilterInfo filter=new FilterInfo();
    	filter.appendFilterItem("payRequestBill.contractId", editData.getContractId());
    	view.setFilter(filter);
    	//TODO 再加一个时间过滤
    	Timestamp createTime = editData.getCreateTime();
    	filter.getFilterItems().add(new FilterItemInfo("payRequestBill.createTime",createTime,CompareType.LESS_EQUALS));
    	
    	PartAOfPayReqBillCollection c = PartAOfPayReqBillFactory.getRemoteInstance().getPartAOfPayReqBillCollection(view);
    	for(int i=0;i<c.size();i++){
   			PartAOfPayReqBillInfo info = c.get(i);
   			if(info.getAmount()!=null){
   				
   				if(info.getPayRequestBill().getId().equals(editData.getId())){
   					amount=amount.add(FDCHelper.toBigDecimal(info.getAmount()));
   					BigDecimal tem = FDCHelper.toBigDecimal(info.getOriginalAmount()).compareTo(FDCHelper.ZERO)>0?
   							FDCHelper.toBigDecimal(info.getOriginalAmount()):FDCHelper.toBigDecimal(info.getAmount());
   					originalAmount = originalAmount.add(tem);
   				}else{
	   				if(info.isHasPaid()){
	   					lstPaidAmt=info.getAmount().add(lstPaidAmt);
	   				}
	   				lstReqAmt=info.getAmount().add(lstReqAmt);
    			}
    		}
    	}
    	allReqAmt = lstReqAmt.add(amount);
    	if(lstPaidAmt.compareTo(FDCHelper.ZERO)==0){
    		lstPaidAmt = null;
    	}
    	if(lstReqAmt.compareTo(FDCHelper.ZERO)==0){
    		lstReqAmt = null;
    	}
    	if(originalAmount.compareTo(FDCHelper.ZERO)==0){
    		originalAmount = null;
    	}
    	if(allReqAmt.compareTo(FDCHelper.ZERO)==0){
    		allReqAmt = null;
    	}
    	((ICell)bindCellMap.get("LstAMatlAllPaidAmt")).setValue(lstPaidAmt);
    	((ICell)bindCellMap.get("lstAMatlAllReqAmt")).setValue(lstReqAmt);
    	((ICell)bindCellMap.get("payPartAMatlAmt")).setValue(amount);
    	((ICell)bindCellMap.get("payPartAMatlOriAmt")).setValue(originalAmount);
    	((ICell)bindCellMap.get("payPartAMatlAllReqAmt")).setValue(allReqAmt);
    	((ICell)bindCellMap.get("payPartAMatlAllPaidAmt")).setValue(lstPaidAmt);
    	
	}
	
	/**
	 * 甲供材应扣款数据,新增时调用更新
	 * @param objectValue
	 * @param contractBillId
	 * @param compensationOfPayReqBillCollection
	 * @throws BOSException
	 */
	void updatePartAValue(PayRequestBillInfo objectValue, String contractBillId,
			PartAOfPayReqBillCollection partAOfPayReqBillCollection) throws BOSException {
		
		//甲供扣款单
		if(partAOfPayReqBillCollection==null){
			return;
		}
		BigDecimal amount=FDCHelper.ZERO;
		BigDecimal originalamount = FDCHelper.ZERO;
		BigDecimal lstPaidAmt = FDCHelper.ZERO;
		BigDecimal lstReqAmt = FDCHelper.ZERO;
		BigDecimal allReqAmt = FDCHelper.ZERO;
		for(int i=0;i<partAOfPayReqBillCollection.size();i++){
			PartAOfPayReqBillInfo info = partAOfPayReqBillCollection.get(i);
			if(info.getAmount()!=null){
				if(info.isHasPaid()){
					lstPaidAmt=info.getAmount().add(lstPaidAmt);
				}
				lstReqAmt=info.getAmount().add(lstReqAmt);
				allReqAmt = lstReqAmt.add(amount);
			}
		}
		if(bindCellMap.get("payPartAMatlAmt")!=null){
			((ICell)bindCellMap.get("LstAMatlAllPaidAmt")).setValue(lstPaidAmt);
    		((ICell)bindCellMap.get("lstAMatlAllReqAmt")).setValue(lstReqAmt);
    		((ICell)bindCellMap.get("payPartAMatlAmt")).setValue(amount);
    		((ICell)bindCellMap.get("payPartAMatlOriAmt")).setValue(originalamount);
    		((ICell)bindCellMap.get("payPartAMatlAllReqAmt")).setValue(allReqAmt);
    		((ICell)bindCellMap.get("payPartAMatlAllPaidAmt")).setValue(lstPaidAmt);
		}
	}
	/**
	 * 重载甲供材确认单
	 * @param editData
	 * @param amount
	 * @throws Exception
	 */
	void reloadPartAConfmValue(PayRequestBillInfo editData,BigDecimal amount) throws Exception{
//		if(!PayReqUtils.isContractBill(editData.getContractId())){
//    		return;
//    	}
		if(amount == null){
			amount = FDCHelper.ZERO;
		}
		if(amount.compareTo(FDCHelper.ZERO)==1){
			amount = FDCHelper.ZERO;
			((ICell)bindCellMap.get("payPartAMatlAmt")).setValue(amount);
		}
//    	if(amount!=null){
//    		((ICell) bindCellMap.get("payPartAMatlAmt")).setValue(amount);
//    	}else{
    		//进行刷新操作从数据库内取
    		amount=FDCHelper.ZERO;
    		BigDecimal originalamount = FDCHelper.ZERO;
    		BigDecimal lstPaidAmt = FDCHelper.ZERO;
    		BigDecimal lstReqAmt = FDCHelper.ZERO;
    		BigDecimal allReqAmt = FDCHelper.ZERO;
    		EntityViewInfo view=new EntityViewInfo();
    		FilterInfo filter=new FilterInfo();
    		filter.appendFilterItem("payRequestBill.contractId", editData.getContractId());
    		view.setFilter(filter);
    		//TODO 再加一个时间过滤
    		Timestamp createTime = editData.getCreateTime();
    		filter.getFilterItems().add(new FilterItemInfo("payRequestBill.createTime",createTime,CompareType.LESS_EQUALS));
    		
    		PartAConfmOfPayReqBillCollection c = PartAConfmOfPayReqBillFactory.getRemoteInstance().getPartAConfmOfPayReqBillCollection(view);
    		for(int i=0;i<c.size();i++){
    			PartAConfmOfPayReqBillInfo info = c.get(i);
    			if(info.getAmount()!=null){
    				if(info.getPayRequestBill().getId().equals(editData.getId())){
    					amount=amount.add(FDCHelper.toBigDecimal(info.getAmount()));
    					//2009-2-8 甲供扣款原币 应累加
    					originalamount = originalamount.add(FDCHelper.toBigDecimal(info.getOriginalAmount()));
//    					originalamount = FDCHelper.toBigDecimal(info.getOriginalAmount());
    				}else{
	    				if(info.isHasPaid()){
	    					lstPaidAmt=info.getAmount().add(lstPaidAmt);
	    				}
	    				lstReqAmt=info.getAmount().add(lstReqAmt);
    				}
    			}
    		} 
    		allReqAmt = amount.add(lstReqAmt);
    		if(lstPaidAmt.compareTo(FDCHelper.ZERO)==0){
    			lstPaidAmt = null;
    		}
    		if(originalamount.compareTo(FDCHelper.ZERO)==0){
    			originalamount = null;
    		}
    		if(lstReqAmt.compareTo(FDCHelper.ZERO)==0){
    			lstReqAmt = null;
    		}
    		((ICell)bindCellMap.get("LstAMatlAllPaidAmt")).setValue(lstPaidAmt);
    		((ICell)bindCellMap.get("lstAMatlAllReqAmt")).setValue(lstReqAmt);
    		((ICell)bindCellMap.get("payPartAMatlAmt")).setValue(amount);
    		((ICell)bindCellMap.get("payPartAMatlOriAmt")).setValue(originalamount);
    		((ICell)bindCellMap.get("payPartAMatlAllReqAmt")).setValue(allReqAmt);
    		((ICell)bindCellMap.get("payPartAMatlAllPaidAmt")).setValue(lstPaidAmt);
//    	}
	}
	
	/**
	 * 更新 甲供材确认单对应的数据
	 * @param objectValue
	 * @param contractBillId
	 * @param compensationOfPayReqBillCollection
	 * @throws BOSException
	 */
	void updatePartAConfmValue(PayRequestBillInfo objectValue, String contractBillId,
			PartAConfmOfPayReqBillCollection partAConfmOfPayReqBillCollection) throws BOSException {

		if(partAConfmOfPayReqBillCollection==null){
			return;
		}
		BigDecimal amount=FDCHelper.ZERO;
		BigDecimal originalamount=FDCHelper.ZERO;
		BigDecimal lstPaidAmt=FDCHelper.ZERO;
		BigDecimal lstReqAmt=FDCHelper.ZERO;
		for(int i=0;i<partAConfmOfPayReqBillCollection.size();i++){
			PartAConfmOfPayReqBillInfo info = partAConfmOfPayReqBillCollection.get(i);
			if(info.getAmount()!=null){
				if(info.isHasPaid()){
					lstPaidAmt=info.getAmount().add(lstPaidAmt);
				}
				lstReqAmt=info.getAmount().add(lstReqAmt);
			}
		}
		
		if(bindCellMap.get("payPartAMatlAmt")!=null){
			((ICell)bindCellMap.get("LstAMatlAllPaidAmt")).setValue(lstPaidAmt);
    		((ICell)bindCellMap.get("lstAMatlAllReqAmt")).setValue(lstReqAmt);
    		((ICell)bindCellMap.get("payPartAMatlAmt")).setValue(amount);
    		((ICell)bindCellMap.get("payPartAMatlOriAmt")).setValue(originalamount);
    		((ICell)bindCellMap.get("payPartAMatlAllReqAmt")).setValue(lstReqAmt);
    		((ICell)bindCellMap.get("payPartAMatlAllPaidAmt")).setValue(lstPaidAmt);
		}
	}
	protected void setBeforeAction() {

		table.setBeforeAction(new BeforeActionListener() {
			public void beforeAction(BeforeActionEvent e) {
				if (BeforeActionEvent.ACTION_DELETE == e.getType()) {
					for (int i = 0; i < table.getSelectManager().size(); i++) {
						KDTSelectBlock block = table.getSelectManager()
								.get(i);
						for (int rowIndex = block.getBeginRow(); rowIndex <= block
								.getEndRow(); rowIndex++) {
							for (int colIndex = block.getBeginCol(); colIndex <= block
									.getEndCol(); colIndex++) {
								//非合同内工程款Cell,或非编辑状态时撤销事件
								if((colIndex!=editUI.columnIndex||rowIndex!=editUI.rowIndex) ||table.getCell(rowIndex, colIndex).getStyleAttributes().isLocked()){
									e.setCancel(true);
									continue;
								}
								KDTEditEvent event = new KDTEditEvent(
										table, null, null, rowIndex,
										colIndex, true, 1);
								try {
									editUI.kdtEntrys_editStopped(event);
								} catch (Exception e1) {
									handUIException(e1);
								}
							}
						}
					}
				}else if(BeforeActionEvent.ACTION_PASTE==e.getType()){
					table.putClientProperty("ACTION_PASTE", "ACTION_PASTE");
				}
			}
		});
		
		table.setAfterAction(new BeforeActionListener() {
			public void beforeAction(BeforeActionEvent e) {
				if (BeforeActionEvent.ACTION_PASTE == e.getType()) {
					table.putClientProperty("ACTION_PASTE", null);
				}

			}
		});
		/*
		 * KDTable的KDTEditListener仅在编辑的时候触发，
		 * KDTPropertyChangeListener则是在删除，粘贴等导致单元格value发生变化的时候都会触发。
		 */
		table.addKDTPropertyChangeListener(new KDTPropertyChangeListener(){
			public void propertyChange(KDTPropertyChangeEvent evt) {
			    // 表体单元格值发生变化
			    if ((evt.getType() == KDTStyleConstants.BODY_ROW) && (evt.getPropertyName().equals(KDTStyleConstants.CELL_VALUE)))
			    {
			    	if(table.getClientProperty("ACTION_PASTE")!=null){
			    		// 触发editStop事件
			    		int rowIndex = evt.getRowIndex();
			    		int colIndex = evt.getColIndex();
			    		if(rowIndex!=4||colIndex!=4){
			    			// 这里对整个表体cell做了监听，根据需要过滤
			    			return;
			    		}
			    		KDTEditEvent event=new KDTEditEvent(table);
			    		event.setColIndex(colIndex);
			    		event.setRowIndex(rowIndex);
			    		event.setOldValue(null);
			    		ICell cell = table.getCell(rowIndex,colIndex);
			    		if(cell==null){
			    			return;
			    		}
			    		event.setValue(cell.getValue());
			    		try {
			    			editUI.kdtEntrys_editStopped(event);
			    			
			    		} catch (Exception e1) {
			    			handUIException(e1);
			    		}
			    	}
			    }
			}
		});
	}
}
