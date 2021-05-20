package com.kingdee.eas.fdc.aimcost.app;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.basedata.assistant.PeriodInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.fdc.aimcost.AdjustRecordEntryCollection;
import com.kingdee.eas.fdc.aimcost.AdjustRecordEntryInfo;
import com.kingdee.eas.fdc.aimcost.AimCostAdjustEntryInfo;
import com.kingdee.eas.fdc.aimcost.AimCostAdjustInfo;
import com.kingdee.eas.fdc.aimcost.DyCostSplitDataGetter;
import com.kingdee.eas.fdc.aimcost.DynamicCostFactory;
import com.kingdee.eas.fdc.aimcost.DynamicCostInfo;
import com.kingdee.eas.fdc.aimcost.DynamicCostMap;
import com.kingdee.eas.fdc.aimcost.FDCCostRptFacadeFactory;
import com.kingdee.eas.fdc.basedata.FDCBillInfo;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.TimeTools;
import com.kingdee.eas.fdc.contract.FDCUtils;

/**
 * Ŀ��ɱ�������
 * 
 * @author pengwei_hou
 * @date 2009-07-13
 * 
 * @version EAS FDC6.1
 */
public class AimCostAdjustControllerBean extends
		AbstractAimCostAdjustControllerBean {
	private static Logger logger = Logger
			.getLogger("com.kingdee.eas.fdc.aimcost.app.AimCostAdjustControllerBean");

	/**
	 * ����ʱ��¼ͬ�����������ɱ�Ԥ�������¼
	 * 
	 * @param ctx
	 * @param billId
	 * @return
	 * @throws BOSException
	 * @throws EASBizException
	 */
	protected void _audit(Context ctx, BOSUuid billId) throws BOSException,
			EASBizException {
		super._audit(ctx, billId);
		
		synToDynamicCost(ctx, billId);
	}

	private void synToDynamicCost(Context ctx, BOSUuid billId)
			throws BOSException, EASBizException {
		AimCostAdjustInfo info = this.getAimCostAdjustInfo(ctx,
				new ObjectUuidPK(billId), getSic());
		String prjId = info.getCurProject().getId().toString();
		
		DyCostSplitDataGetter dyGetter;

		TimeTools.getInstance().msValuePrintln("start fetchData");
		//��ȡ��ǰ�ڼ�
		PeriodInfo curPeriod = FDCUtils.getCurrentPeriod(ctx,prjId,true);
		//ȡ��ǰ�ڼ�����
		final DynamicCostMap dynamicCostMap = FDCCostRptFacadeFactory.getLocalInstance(ctx).getDynamicCost(prjId, curPeriod,true);
		if(dynamicCostMap==null){
			return;
		}
		dyGetter=dynamicCostMap.getDyCostSplitDataGetter();
		TimeTools.getInstance().msValuePrintln("end fetchData");
		
		BOSUuid id = null;
		DynamicCostInfo parent = null;
		AdjustRecordEntryInfo recordInfo = null;
		
		Map dynCostMap = new HashMap();
		int seq = 1;
		for (int i = 0; i < info.getEntrys().size(); i++) {
			AimCostAdjustEntryInfo entry = info.getEntrys().get(i);
			
			String acctId = entry.getCostAccount().getId().toString();
			if(dynCostMap.containsKey(acctId)){
				parent = (DynamicCostInfo)dynCostMap.get(acctId);
			}else{
				dynCostMap.put(acctId, parent);
			}
			parent = dyGetter.getDynamicInfo(acctId);
			if(parent==null){
				parent=new DynamicCostInfo();
				parent.setId(BOSUuid.create(parent.getBOSType()));
				parent.setAccount(entry.getCostAccount());
				parent.setCU(SysContext.getSysContext().getCurrentCtrlUnit());
				parent.setPeriod(curPeriod);
				parent.setCreator(info.getCreator());
				parent.setCreateTime(info.getCreateTime());
				parent.setAuditor(info.getAuditor());
				//Add by zhiyuan_tang 2010/07/12 R100622-111 �������ɱ�Ԥ�����ʷ��¼���ܱ���
				parent.setAdjustSumAmount(FDCHelper.ZERO);	//����ʼ��Ϊ0
				dyGetter.updateDynamic(acctId, parent);
			}
			if(info.getId()==null){
				info.setId(BOSUuid.create(info.getBOSType()));
			}
			seq = parent.getAdjustEntrys().size();
			recordInfo = new AdjustRecordEntryInfo();
			id = BOSUuid.create(recordInfo.getBOSType());
			recordInfo.setId(id);
			recordInfo.setParent(parent);
			recordInfo.setAdjustAcctName(entry.getCostAccount().getName());
			recordInfo.setParent(parent);
			recordInfo.setAdjustDate(entry.getAdjustDate());
			if (entry.getAdjuster() != null) {
				recordInfo.setAdjuster(entry.getAdjuster());
				recordInfo.setAdjusterName(entry.getAdjuster().getName());
			}
			recordInfo.setWorkload(entry.getWorkload());
			recordInfo.setUnit(entry.getUnit());
			recordInfo.setPrice(entry.getPrice());
			recordInfo.setCostAmount(entry.getCostAmount());
			recordInfo.setProduct(entry.getProductType());
			recordInfo.setDescription(entry.getDescription());
			recordInfo.setAdjustType(entry.getAdjustType());
			recordInfo.setAdjustReason(entry.getAdjustReason());
			recordInfo.setAdjustEntryId(entry.getId().toString());
			recordInfo.setPeriod(curPeriod);
			recordInfo.setSeq(seq +i);
			
			//Delete by zhiyuan_tang 2010/07/09  	R100622-111 �������ɱ�Ԥ�����ʷ��¼���ܱ���
			//����ʱ��ɾ����ʷ��¼
			//parent.getAdjustEntrys().clear();
			AdjustRecordEntryCollection adjustCol = new AdjustRecordEntryCollection();
			adjustCol.add(recordInfo);
			parent.getAdjustEntrys().addCollection(adjustCol);
			//Update by zhiyuan_tang 2010/07/09   R100622-111 �������ɱ�Ԥ�����ʷ��¼���ܱ���
			//�����ԭ���Ļ������ۼ�
			//BigDecimal adjustAmount = FDCHelper.ZERO;
			BigDecimal adjustAmount = parent.getAdjustSumAmount();

			for (int j = 0; j < adjustCol.size(); j++) {
				AdjustRecordEntryInfo adjustInfo = adjustCol.get(j);
				adjustAmount = adjustAmount.add(adjustInfo.getCostAmount());
				if (adjustInfo.getId() == null) {
					adjustInfo.setId(BOSUuid.create(adjustInfo.getBOSType()));
				}
				
			}
			parent.setAdjustSumAmount(adjustAmount);
			DynamicCostFactory.getLocalInstance(ctx).submit(parent);
		}
		
	}
	private void unSynToDynamicCost(Context ctx, BOSUuid billId)
			throws BOSException, EASBizException {
		AimCostAdjustInfo info = this.getAimCostAdjustInfo(ctx,
				new ObjectUuidPK(billId), getSic());
		String prjId = info.getCurProject().getId().toString();

		DyCostSplitDataGetter dyGetter;

		TimeTools.getInstance().msValuePrintln("start fetchData");
		// ��ȡ��ǰ�ڼ�
		PeriodInfo curPeriod = FDCUtils.getCurrentPeriod(ctx, prjId, true);
		// ȡ��ǰ�ڼ�����
		final DynamicCostMap dynamicCostMap = FDCCostRptFacadeFactory
				.getLocalInstance(ctx).getDynamicCost(prjId, curPeriod, true);
		if (dynamicCostMap == null) {
			return;
		}
		dyGetter = dynamicCostMap.getDyCostSplitDataGetter();
		TimeTools.getInstance().msValuePrintln("end fetchData");

		DynamicCostInfo parent = null;
		for (int i = 0; i < info.getEntrys().size(); i++) {
			AimCostAdjustEntryInfo entry = info.getEntrys().get(i);

			String acctId = entry.getCostAccount().getId().toString();
			parent = dyGetter.getDynamicInfo(acctId);

			AdjustRecordEntryCollection adjustCol = parent.getAdjustEntrys();
			BigDecimal adjustAmount = FDCHelper.toBigDecimal(parent.getAdjustSumAmount());
			for (int j = 0; j < adjustCol.size(); j++) {
				AdjustRecordEntryInfo adjustInfo = adjustCol.get(j);
				//Add by zhiyuan_tang 2010/07/12 	R100622-111 �������ɱ�Ԥ�����ʷ��¼���ܱ���
				//���Nullֵ�ж�
				if (adjustInfo.getAdjustEntryId() != null) {
					if(entry.getId().toString().equals(adjustInfo.getAdjustEntryId().toString())){
						adjustAmount = adjustAmount.subtract(adjustInfo.getCostAmount());
						adjustCol.remove(adjustInfo);
					}
				}

			}
			//Delete by zhiyuan_tang 2010/07/12 	R100622-111 �������ɱ�Ԥ�����ʷ��¼���ܱ���
			//������ʱ�������ϸ��¼
			//parent.getAdjustEntrys().clear();
			parent.getAdjustEntrys().addCollection(adjustCol);
			parent.setAdjustSumAmount(adjustAmount);
			DynamicCostFactory.getLocalInstance(ctx).submit(parent);
		}

	}
	
	/**
	 * ������ʱ���������ɱ�Ԥ�����ɵķ�¼ɾ��
	 * 
	 * @param ctx
	 * @param billId
	 * @return
	 * @throws BOSException
	 * @throws EASBizException
	 */
	protected void _unAudit(Context ctx, BOSUuid billId) throws BOSException,
			EASBizException {
		super._unAudit(ctx, billId);
		unSynToDynamicCost(ctx,billId);
	}

	protected void checkNameDup(Context ctx, FDCBillInfo billInfo)
			throws BOSException, EASBizException {
		// û��name������ҪУ��
	}

	private SelectorItemCollection getSic() {
		SelectorItemCollection sic = new SelectorItemCollection();
		sic.add("*");
		sic.add("entrys.*");
		sic.add("entrys.costAccount.id");
		sic.add("entrys.costAccount.name");
		sic.add("entrys.adjuster.name");
		sic.add("entrys.adjuster.id");
		return sic;
	}
}