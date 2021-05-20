package com.kingdee.eas.fdc.finance.client;

import java.awt.Color;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTMergeManager;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.util.style.StyleAttributes;
import com.kingdee.bos.ctrl.kdf.util.style.Styles.HorizontalAlignment;
import com.kingdee.bos.metadata.data.SortType;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.eas.fdc.basedata.DeductTypeInfo;
import com.kingdee.eas.fdc.basedata.FDCConstants;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCNumberHelper;
import com.kingdee.eas.fdc.basedata.client.FDCColorConstants;
import com.kingdee.eas.fdc.contract.DeductOfPayReqBillCollection;
import com.kingdee.eas.fdc.finance.FDCCostVoucherEntryCollection;
import com.kingdee.eas.fdc.finance.FDCCostVoucherEntryFactory;
import com.kingdee.eas.fdc.finance.FDCCostVoucherEntryInfo;
import com.kingdee.eas.fdc.finance.FDCPayVoucherEntryCollection;
import com.kingdee.eas.fdc.finance.FDCPayVoucherEntryFactory;
import com.kingdee.eas.fdc.finance.FDCPayVoucherEntryInfo;
import com.kingdee.eas.fi.cas.PaymentBillInfo;

/**
 * �����������ϸ��
 * @author pengwei_hou Date:2008-12-11
 */
public class PaymentBillDetailHelper {
	private static final Logger logger = CoreUIObject.getLogger(PaymentBillDetailHelper.class);
	
	public static final Color noEditColor = FDCColorConstants.cantEditColor;

	private PaymentBillInfo editData = null;

	private PaymentBillEditUI editUI = null;
	
	private Map deductMap = new HashMap();
	
	private KDTable table=null;
	
	private int costIndex = 0;
	
	private int deductIndex = 1;
	
	public PaymentBillDetailHelper(PaymentBillEditUI editUI) {
		this.editUI = editUI;
		this.editData = editUI.editData;
		this.table = editUI.kdtDetail;
	}
	
	/**
	 * ���������������ʾ�����ϸ���table.<p>
	 * �����Ĵ�������onLoad�ĺ���ʹ��editData��getUIContext()�ڴ������ʱ����
	 * @return table
	 */
	KDTable createPaymentBillDetailTable() throws Exception{
		
		// EXCEL������������,������
		// table.setHeadDisplayMode(KDTStyleConstants.HEAD_DISPLAY_EXCEL);
		// ����������
		table.getIndexColumn().getStyleAttributes().setHided(true);
		table.setRefresh(false);
		table.getScriptManager().setAutoRun(false);
		table.getColumn("periodCostAmt").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
		table.getColumn("periodPayAmt").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
		StyleAttributes sa = table.getStyleAttributes();
		 //�Ƿ�ɱ༭
		sa.setLocked(true);
		sa.setBackground(noEditColor);
		sa.setNumberFormat("###,##0.00");
		
		//�̶���
		initFixTable(table);
		
		table = initDynamicCostTable(table);

		initDynamicDeductTable(table);
		
		KDTMergeManager mm = table.getMergeManager();
		mm.mergeBlock(0, 1, costIndex+1, 1, KDTMergeManager.FREE_MERGE);//���ȿ����ͬ�ڹ��̿
		mm.mergeBlock(0, 2, costIndex, 2, KDTMergeManager.FREE_MERGE);//���ȿ��ΥԼ��
		mm.mergeBlock(costIndex+2, 1, deductIndex-1, 1, KDTMergeManager.FREE_MERGE);//Ӧ�ۿ���(�ۿ���)
//		mm.mergeBlock(costIndex, 1, deductIndex, 2, KDTMergeManager.FREE_MERGE);//Ӧ�ۿ���(Ӧ�ۼ׹��Ŀ�)
		mm.mergeBlock(deductIndex, 1, deductIndex, 2, KDTMergeManager.FREE_MERGE);//Ӧ�ۼ׹��Ŀ�
		
		return table;
	}

	/**
	 * ��ʼ��
	 * @param table
	 */
	private void initFixTable(KDTable table) {
//		String scheduleAmt = getRes("scheduleAmt");
//		String amtInContract = getRes("amtInContract");
//		String amtDeduct = getRes("amtDeduct");
//		String shouldSub = getRes("shouldSub");
//		String add = getRes("add");
//		
	}
	
	/**
	 * ���������ȿ���
	 * @param table
	 * @return int
	 * @throws Exception 
	 */
	private KDTable initDynamicCostTable(KDTable table) throws Exception{
		String scheduleAmt = getRes("scheduleAmt");
		String amtInContract = getRes("amtInContract");
		IRow row;
		//���ȿ�����
		row = table.addRow();
		row.getCell(1).setValue(scheduleAmt);
		row.getCell(2).setValue(amtInContract);
		FDCPayVoucherEntryCollection payColl = getPayCollection();
		FDCCostVoucherEntryInfo advanceVouhcer = null;
		if (payColl != null) {
			for(int i=0;i<payColl.size();i++){ 
				FDCPayVoucherEntryInfo payVoucherInfo = payColl.get(i);
				String type = payVoucherInfo.getType();
				if(FDCConstants.FDC_INIT_ADVANCE.equals(type)){
					advanceVouhcer = new FDCCostVoucherEntryInfo();
					advanceVouhcer.setLocateAmount(FDCHelper.subtract(new BigDecimal("0"), payVoucherInfo.getLocateAmount()));
					advanceVouhcer.setAccountView(payVoucherInfo.getAccountView());
					advanceVouhcer.setCurProject(payVoucherInfo.getCurProject());
					advanceVouhcer.setType(FDCConstants.FDC_INIT_ADVANCE);
				}
			}
		}
		FDCCostVoucherEntryCollection costColl = getCostCollection();
		if(advanceVouhcer!=null){
			//Ԥ����������ͬʱ���Ӳ�ͬ�ķ�¼ȡֵ
			costColl.add(advanceVouhcer);
		}
		boolean isGuerdon = false;
		if (costColl != null) {
			for(int i=0;i<costColl.size();i++){ 
				FDCCostVoucherEntryInfo costVoucherInfo = costColl.get(i);
				String type = costVoucherInfo.getType();
				//���˵�����,�ں�ߵ�������
				if(FDCConstants.FDC_INIT_GUERDON.equals(type)){
					isGuerdon = true;
					continue;
				}
				//���˵�Ԥ����
				if(FDCConstants.FDC_INIT_ADVANCE.equals(type)){
					continue;
				}
				if(isGuerdon?(i >1):(i>0)){
					row = table.addRow(costIndex+1);
					row.getCell(1).setValue(scheduleAmt);
					row.getCell(2).setValue(amtInContract);
					costIndex++;
					deductIndex++;
				}
				row.getCell(3).setValue(costVoucherInfo.getAmount());
				row.getCell(4).setValue(costVoucherInfo.getPayAmt());
				if(costVoucherInfo.getAccountView()!=null){
					row.getCell(5).setValue(costVoucherInfo.getAccountView().getLongName());
				}
				if(costVoucherInfo.getCurProject()!=null){
					row.getCell(6).setValue(costVoucherInfo.getCurProject().getName());
				}
			}
		}
		//Ԥ����
		IRow advanceRow = table.addRow();
		advanceRow.getCell(1).setValue(scheduleAmt);
		advanceRow.getCell(2).setValue("Ԥ����");
		if(!editUI.isAdvance()){
			advanceRow.getStyleAttributes().setHided(true);
		}
		costIndex++;
		deductIndex++;
		
		//����
		IRow guerdonRow = table.addRow();
		guerdonRow.getCell(1).setValue(scheduleAmt);
		guerdonRow.getCell(2).setValue(getRes("guerdon"));
		costIndex++;
		deductIndex++;
		
		//������������Ԥ����
		if (costColl != null) {
			for(int i=0;i<costColl.size();i++){ 
				FDCCostVoucherEntryInfo costVoucherInfo = costColl.get(i);
				String type = costVoucherInfo.getType();
				if(FDCConstants.FDC_INIT_GUERDON.equals(type)){
					guerdonRow.getCell(3).setValue(costVoucherInfo.getAmount());
					guerdonRow.getCell(4).setValue(costVoucherInfo.getPayAmt());
					if(costVoucherInfo.getAccountView()!=null){
						guerdonRow.getCell(5).setValue(costVoucherInfo.getAccountView().getLongName());
					}
				}else if(FDCConstants.FDC_INIT_ADVANCE.equals(type)){
					advanceRow.getCell(3).setValue(costVoucherInfo.getAmount());
					advanceRow.getCell(4).setValue(costVoucherInfo.getLocateAmount());
					if(costVoucherInfo.getAccountView()!=null){
						advanceRow.getCell(5).setValue(costVoucherInfo.getAccountView().getLongName());
					}
					if(costVoucherInfo.getCurProject()!=null){
						advanceRow.getCell(6).setValue(costVoucherInfo.getCurProject().getName());
					}
				}
			}
		}
		return table;
	}
	
	/**
	 * ������Ӧ�ۿ���
	 * @param table
	 * @return
	 * @throws BOSException
	 */
	private void initDynamicDeductTable(KDTable table) throws BOSException {
		IRow row;
		int base = deductIndex;
		BigDecimal deductAdd = FDCHelper.ZERO;
		String scheduleAmt = getRes("scheduleAmt");
		String amtDeduct = getRes("amtDeduct");
		String shouldSub = getRes("shouldSub");
		String add = getRes("add");
		
		deductMap = getDeductMap();
		
		DeductOfPayReqBillCollection deductColl = (DeductOfPayReqBillCollection) editUI.getDeductMap();
		for(int i=0;i<deductColl.size();i++){
			
			row = table.addRow(deductIndex);
			deductIndex++;
			
			row.getCell(1).setValue(amtDeduct);
			row.getCell(2).setValue(deductColl.get(i).getDeductType().getName());
			String idStr = deductColl.get(i).getDeductType().getId().toString();
			if(deductMap == null){
				continue;
			}
			Object[] o = (Object[]) deductMap.get(idStr);
			
			if(o == null){
				continue;
			}
			
			row.getCell(4).setValue(o[0]);
			row.getCell(5).setValue(o[1]);
			
			deductAdd = deductAdd.add(FDCHelper.toBigDecimal(o[0]));
		}
		
		row = table.addRow();
		row.getCell(1).setValue(amtDeduct);
		row.getCell(2).setValue(add);
		row.getCell(4).setValue(deductAdd);
		deductIndex++;
		
//		row = table.addRow(base);
//		row.getCell(1).setValue(scheduleAmt);
//		row.getCell(2).setValue(getRes("guerdon"));
//		if(deductMap != null && deductMap.get(FDCConstants.FDC_INIT_GUERDON) != null){
//			Object[] guerdon = (Object[]) deductMap.get(FDCConstants.FDC_INIT_GUERDON);
//			row.getCell(4).setValue(guerdon[0]);
//			row.getCell(5).setValue(guerdon[1]);
//		}
//		deductIndex++;
		
		row = table.addRow(base);
		row.getCell(1).setValue(scheduleAmt);
		row.getCell(2).setValue(getRes("compensation"));
		if(deductMap != null && deductMap.get(FDCConstants.FDC_INIT_COMPENSATION) != null){
			Object[] compensation = (Object[]) deductMap.get(FDCConstants.FDC_INIT_COMPENSATION);
			row.getCell(4).setValue(compensation[0]);
			row.getCell(5).setValue(compensation[1]);
		}
		deductIndex++;
		
		//Ӧ�ۼ׹��Ŀ���
		row = table.addRow();
		row.getCell(1).setValue(shouldSub);
		row.getCell(2).setValue(shouldSub);
		if(deductMap != null && deductMap.get(DeductTypeInfo.partAMaterialType) != null){
			Object[] partA = (Object[]) deductMap.get(DeductTypeInfo.partAMaterialType);
			row.getCell(4).setValue(partA[0]);
			row.getCell(5).setValue(partA[1]);
		}
		
		//С�ƴ�����
//		row = table.getRow();
//		row.getCell(1).setValue(amtDeduct);
//		row.getCell(2).setValue(getRes("add"));
//		row.getCell(4).setValue("3444444444444");//����
		
	}
	
	/**
	 * ��������ȡ��Դ�ļ�
	 * @param resName
	 * @return
	 */
	String getRes(String resName){
		return PaymentBillClientUtils.getRes(resName);
	}
	
	/**
	 * ��������ͬ�ڹ��̿���ڳɱ�����뱾�ڸ����ȡ�ɱ�ƾ֤��¼
	 * @return
	 * @throws Exception
	 */
	private FDCCostVoucherEntryCollection getCostCollection() throws Exception{
		String contractId = editData.getContractBillId();
		EntityViewInfo view = new EntityViewInfo();
		SorterItemInfo sorterItemInfo = new SorterItemInfo("curProject.name");
		sorterItemInfo.setSortType(SortType.ASCEND);
		view.getSorter().add(sorterItemInfo);
		sorterItemInfo = new SorterItemInfo("type");
		sorterItemInfo.setSortType(SortType.DESCEND);
		view.getSorter().add(sorterItemInfo);
		view.getSelector().add("amount");
		view.getSelector().add("locateAmount");
		view.getSelector().add("payAmt");
		view.getSelector().add("accountView.longName");
		view.getSelector().add("curProject.name");
		view.getSelector().add("type");
		view.setFilter(new FilterInfo());
		view.getFilter().getFilterItems().add(new FilterItemInfo("parent.contractBillId", contractId));
		view.getFilter().getFilterItems().add(new FilterItemInfo("parent.id", editData.getId().toString()));
		FDCCostVoucherEntryCollection costVoucherEntryCollection = FDCCostVoucherEntryFactory
				.getRemoteInstance().getFDCCostVoucherEntryCollection(view);
		if(costVoucherEntryCollection == null || costVoucherEntryCollection.size() == 0){
			return null;
		}
		
		return costVoucherEntryCollection;
	}
	
	/**
	 * �������ۿ���ΥԼȡ����ƾ֤��¼
	 * @return
	 * @throws BOSException
	 */
	private FDCPayVoucherEntryCollection getPayCollection() throws BOSException{
		
		String contractId = editData.getContractBillId();
		EntityViewInfo view = new EntityViewInfo();
		view.getSelector().add("locateAmount");
		view.getSelector().add("accountView.longName");
		view.getSelector().add("curProject.name");
		view.getSelector().add("type");
		view.setFilter(new FilterInfo());
		view.getFilter().getFilterItems().add(new FilterItemInfo("parent.contractBillId", contractId));
		view.getFilter().getFilterItems().add(new FilterItemInfo("parent.id", editData.getId().toString()));
		FDCPayVoucherEntryCollection payVoucherEntryCollection = FDCPayVoucherEntryFactory
				.getRemoteInstance().getFDCPayVoucherEntryCollection(view);
		
		return payVoucherEntryCollection;
	}
	
	private Map getDeductMap() throws BOSException{
		FDCPayVoucherEntryCollection payVoucherEntryCollection = getPayCollection();
		if(payVoucherEntryCollection == null || payVoucherEntryCollection.size() ==0){
			return null;
		}
		Set idSet = new HashSet(); 
		for(int i=0;i<payVoucherEntryCollection.size();i++){
			
			FDCPayVoucherEntryInfo entry = payVoucherEntryCollection.get(i);
			
			if(entry.getType() == null || entry.getType().toString().equals(FDCConstants.FDC_INIT_PROJECTPRICEINCONTRACT)){
				continue;
			}
			String typeId = entry.getType().toString();
			
			Object[] obj = new Object[2];
			obj[0] = FDCHelper.toBigDecimal(entry.getLocateAmount());
			if(entry.getAccountView()!=null)
				obj[1] = entry.getAccountView().getLongName();
			
			if(idSet.contains(typeId)){
				Object[] o = (Object[]) deductMap.get(typeId);
				o[0] = FDCNumberHelper.add(o[0], obj[0]);
//				o[1] = obj[1];
//				deductMap.remove(typeId);
//				deductMap.put(typeId, o);
			}else{
				deductMap.put(entry.getType().toString(), obj);
				idSet.add(typeId);
			}
		}
		
		return deductMap;
	}
}
