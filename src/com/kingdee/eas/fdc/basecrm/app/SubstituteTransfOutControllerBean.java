package com.kingdee.eas.fdc.basecrm.app;

import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.base.codingrule.CodingRuleException;
import com.kingdee.eas.base.codingrule.CodingRuleManagerFactory;
import com.kingdee.eas.base.codingrule.ICodingRuleManager;
import com.kingdee.eas.basedata.org.CompanyOrgUnitInfo;
import com.kingdee.eas.basedata.org.OrgUnitInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.basecrm.RevBillTypeEnum;
import com.kingdee.eas.fdc.basecrm.SHERevBillInfo;
import com.kingdee.eas.fdc.basecrm.SubstituteTransfOutEntryCollection;
import com.kingdee.eas.fdc.basecrm.SubstituteTransfOutEntryInfo;
import com.kingdee.eas.fdc.basecrm.SubstituteTransfOutFactory;
import com.kingdee.eas.fdc.basecrm.SubstituteTransfOutInfo;
import com.kingdee.eas.fdc.basedata.FDCBillInfo;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.sellhouse.GatherTypeEnum;
import com.kingdee.eas.fdc.sellhouse.ReceiveGatherEntryInfo;
import com.kingdee.eas.fdc.sellhouse.ReceiveGatherFactory;
import com.kingdee.eas.fdc.sellhouse.ReceiveGatherInfo;
import com.kingdee.eas.fdc.sellhouse.TranBusinessOverViewCollection;
import com.kingdee.eas.fdc.sellhouse.TranBusinessOverViewFactory;
import com.kingdee.eas.fdc.sellhouse.TranBusinessOverViewInfo;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.eas.framework.CoreBillBaseInfo;
import com.kingdee.eas.util.app.ContextUtil;
import com.kingdee.util.NumericExceptionSubItem;
import com.kingdee.util.StringUtils;

public class SubstituteTransfOutControllerBean extends AbstractSubstituteTransfOutControllerBean
{
    private static Logger logger =
        Logger.getLogger("com.kingdee.eas.fdc.basecrm.app.SubstituteTransfOutControllerBean");
    
    
    protected void _audit(Context ctx, BOSUuid billId) throws BOSException,	EASBizException {
		if(billId==null) return;
		SubstituteTransfOutInfo model = new SubstituteTransfOutInfo();
		model.setId(billId);
		SelectorItemCollection selector = new SelectorItemCollection();
		selector.add(new SelectorItemInfo("state"));
		selector.add(new SelectorItemInfo("auditor"));
		selector.add(new SelectorItemInfo("auditDate"));
		model.setState(FDCBillStateEnum.AUDITTED);    	
		model.setAuditor(ContextUtil.getCurrentUserInfo(ctx));
		model.setAuditDate(new Date());
		this._updatePartial(ctx, model, selector);
		//throw new EASBizException(new NumericExceptionSubItem("100","�����ɹ���"));
		
		updateTransactionEntryIsSubstitute(ctx,billId,true);
		
		
 		//ͬʱ���ɲ���ĸ��
		//������Է��֧��ʵ����		
	}
		
		
	protected void _unAudit(Context ctx, BOSUuid billId) throws BOSException,	EASBizException {
		if(billId==null) return;
		SubstituteTransfOutInfo model = new SubstituteTransfOutInfo();
		model.setId(billId);
		SelectorItemCollection selector = new SelectorItemCollection();
		selector.add(new SelectorItemInfo("state"));
		selector.add(new SelectorItemInfo("auditor"));
		selector.add(new SelectorItemInfo("auditDate"));
		model.setState(FDCBillStateEnum.SUBMITTED);    	
		model.setAuditor(null);
		model.setAuditDate(null);
		this._updatePartial(ctx, model, selector);
		//throw new EASBizException(new NumericExceptionSubItem("100","�����������ɹ���"));
		
		updateTransactionEntryIsSubstitute(ctx,billId,false);
	}
		
	
	private void updateTransactionEntryIsSubstitute(Context ctx, BOSUuid billId,boolean isSubstitute) throws BOSException,	EASBizException {
		//���¶�Ӧ�Ľ�����ϸ���Ѵ���״̬
		SubstituteTransfOutInfo model = (SubstituteTransfOutInfo)this._getValue(ctx, new ObjectUuidPK(billId));
		SubstituteTransfOutEntryCollection entryColl = model.getEntrys();
		String idsStr = "";
		for (int i = 0; i < entryColl.size(); i++) {
			idsStr += ",'"+ entryColl.get(i).getRelateBillEntryId() +"'";
		}
		if(!idsStr.equals("")) {
			idsStr = idsStr.substring(1);
			CoreBaseCollection coreColl = new CoreBaseCollection();
			TranBusinessOverViewCollection tranViewColl = TranBusinessOverViewFactory.getLocalInstance(ctx)
						.getTranBusinessOverViewCollection("select * where id in ("+ idsStr +") ");
			for (int i = 0; i < tranViewColl.size(); i++) {
				TranBusinessOverViewInfo tranViewInfo = tranViewColl.get(i);
				tranViewInfo.setIsSubstitute(isSubstitute);
				coreColl.add(tranViewInfo);
			}
			if(coreColl.size()>0)
				TranBusinessOverViewFactory.getLocalInstance(ctx).update(coreColl);
		}
	}
	
		
	protected void _audit(Context ctx, List idList) throws BOSException,		EASBizException {
		for (int i = 0; i < idList.size(); i++) {
			String idStr = (String)idList.get(i);
			this._audit(ctx, BOSUuid.read(idStr));
		}
	}
		
		
	protected void _unAudit(Context ctx, List idList) throws BOSException,			EASBizException {
		for (int i = 0; i < idList.size(); i++) {
			String idStr = (String)idList.get(i);
			this._unAudit(ctx, BOSUuid.read(idStr));
		}	
	}
    
    

	/**
	 * ����������״̬
	 */
	protected void _setAudittingStatus(Context ctx, BOSUuid billId)
			throws BOSException, EASBizException {
	    SubstituteTransfOutInfo rev = new SubstituteTransfOutInfo();
		rev.setId(billId);
		rev.setState(FDCBillStateEnum.AUDITTING);
		SelectorItemCollection sels = new SelectorItemCollection();
		sels.add("state");
		this.updatePartial(ctx, rev, sels);
	}

    /**
	 * �����ύ״̬
	 */
	protected void _setSubmitStatus(Context ctx, BOSUuid billId)
			throws BOSException, EASBizException {
		SubstituteTransfOutInfo rev = new SubstituteTransfOutInfo();
		rev.setId(billId);
		rev.setState(FDCBillStateEnum.SUBMITTED);
		SelectorItemCollection sels = new SelectorItemCollection();
		sels.add("state");
		this.updatePartial(ctx, rev, sels);		
	}
	
	
	protected void _delete(Context ctx, IObjectPK pk) throws BOSException,
			EASBizException {
		SubstituteTransfOutInfo transInfo = (SubstituteTransfOutInfo)this._getValue(ctx, pk);
		if(transInfo.getState()!=null){
			if(!transInfo.getState().equals(FDCBillStateEnum.SAVED) && !transInfo.getState().equals(FDCBillStateEnum.SUBMITTED))
				throw new EASBizException(new NumericExceptionSubItem("100","�Ǳ�����ύ״̬����ֹɾ����"));
		}
		
		
		super._delete(ctx, pk);
	}
	
	
	
/*    protected void createReceiveGather(Context ctx,SubstituteTransfOutInfo transOutInfo) throws EASBizException, BOSException
    {
    	if(transOutInfo==null || transOutInfo.getEntrys().size()==0) return;
    	
    	ReceiveGatherInfo revGatherInfo = new ReceiveGatherInfo();
    	SHERevBillInfo quitRevBill = new SHERevBillInfo();
	
    	CompanyOrgUnitInfo company = ContextUtil.getCurrentFIUnit(ctx);
    	revGatherInfo.setCompany(company);
    	revGatherInfo.setCurrency(company.getBaseCurrency());
    	revGatherInfo.setExchangeRate(FDCHelper.ONE);
    	
    	ICodingRuleManager iCodingRuleManager = CodingRuleManagerFactory.getLocalInstance(ctx);
		
		OrgUnitInfo orgUnit = ContextUtil.getCurrentSaleUnit(ctx);
		if(orgUnit==null) orgUnit = ContextUtil.getCurrentOrgUnit(ctx);
		
		String retNumber ="";
		if(iCodingRuleManager.isExist(revGatherInfo, orgUnit.getId().toString()))
		    retNumber = iCodingRuleManager.getNumber(revGatherInfo, orgUnit.getId().toString());
		if(retNumber!=null && !"".equals(retNumber)){
			revGatherInfo.setNumber(retNumber);
		}else{
			retNumber = transOutInfo.getNumber()+"_toRevGather";
		}
    	
    	revGatherInfo.setProject(transOutInfo.getSellProject());
    	revGatherInfo.setRevBillType(RevBillTypeEnum.gathering);
    	revGatherInfo.setGatherType(GatherTypeEnum.ReceiveGather);
    	revGatherInfo.setBank(transOutInfo.getRevBank());
    	//revGatherInfo.setAccountBank();
    	revGatherInfo.setSumAmount(transOutInfo.getPayAmount());
    	revGatherInfo.setSettlementType(transOutInfo.getSettlementType());
    	revGatherInfo.setSettlementNumber(transOutInfo.getSettlementType()==null?"":transOutInfo.getSettlementType().getNumber());
    	revGatherInfo.setBizDate(transOutInfo.getBizDate());
    	revGatherInfo.setRevAccount(transOutInfo.getRevAccount());
    	revGatherInfo.setOppAccount(transOutInfo.getOppAccount());
    	revGatherInfo.setCreateTime(transOutInfo.getCreateTime());
    	revGatherInfo.setCreator(transOutInfo.getCreator());
    	revGatherInfo.setState(FDCBillStateEnum.SUBMITTED);
    	revGatherInfo.setDescription("���շ���ת����"+transOutInfo.getNumber()+"���ɵĳ��ɻ��ܵ�");
    	SubstituteTransfOutEntryCollection subEntryColl = transOutInfo.getEntrys();
    	for(int i=0;i<subEntryColl.size();i++)	{
    		SubstituteTransfOutEntryInfo subEntryInfo = subEntryColl.get(i);
    		ReceiveGatherEntryInfo revEntryInfo = new ReceiveGatherEntryInfo();
    		revEntryInfo.setRoom(subEntryInfo.getRoom());
    		revEntryInfo.setCustomerDisName(subEntryInfo.getCustomer());
    		revEntryInfo.setMoneyDefine(transOutInfo.getMoneyDefine());
    		revEntryInfo.setSettlementType(transOutInfo.getSettlementType());
    		revEntryInfo.setSettlementNumber(transOutInfo.getSettlementType()==null?"":transOutInfo.getSettlementType().getNumber());
    		revEntryInfo.setRevAmount(subEntryInfo.getPayAmount());
    		revEntryInfo.setOppAccount(transOutInfo.getOppAccount());
    		//revEntryInfo.setCusAccountBankNumber();
    		revEntryInfo.setSheRevBill(null);
    		revGatherInfo.getEntry().add(revEntryInfo);
    		
    	}
    	//���ɴ��շ���ת����
    	IObjectPK revGatherId = ReceiveGatherFactory.getLocalInstance(ctx).addnew(revGatherInfo);
    	
    	//�����˿
    	
    	SelectorItemCollection updateSels = new SelectorItemCollection();
		updateSels.add("sourceBillId");
    	//��¼���зſ���ɵĳ��ɻ��ܵ�������  �� SourceBillId �ֶμ�¼
    	transOutInfo.setSourceBillId(revGatherId.toString());		
		this._updatePartial(ctx, transOutInfo, updateSels);
    }	
	*/
	
	
	protected IObjectPK _submit(Context ctx, IObjectValue model)
			throws BOSException, EASBizException {
		SubstituteTransfOutInfo transInfo = (SubstituteTransfOutInfo)model;
		// ����Ϻ�
		if (transInfo.getId() == null
				|| !this._exists(ctx, new ObjectUuidPK(transInfo.getId()))) {
			handleIntermitNumber(ctx, transInfo);
		}
		return super._submit(ctx, model);
	}
	
	
	protected void handleIntermitNumber(Context ctx, CoreBillBaseInfo info)
		throws BOSException, CodingRuleException, EASBizException {
		// ����û��ڿͻ����ֹ�ѡ���˶Ϻ�,��˴�����������
		if (info.getNumber() != null && info.getNumber().length() > 0)
			return;
		
		ICodingRuleManager iCodingRuleManager = CodingRuleManagerFactory
				.getLocalInstance(ctx);
		
		boolean isExist = true;
		String costUnitId = ContextUtil.getCurrentOrgUnit(ctx).getId()
				.toString();
		if (StringUtils.isEmpty(costUnitId)) {
			return;
		}
		
		if (!iCodingRuleManager.isExist(info, costUnitId)) {
			isExist = false;
		}
		
		if (isExist) {
			// ѡ���˶Ϻ�֧�ֻ���û��ѡ��������ʾ,��ȡ�����ñ��
			if (iCodingRuleManager.isUseIntermitNumber(info, costUnitId)
					|| !iCodingRuleManager.isAddView(info, costUnitId))
			// �˴���orgId�벽��1����orgIdʱһ�µģ��ж��û��Ƿ����öϺ�֧�ֹ���
			{
				// �����˶Ϻ�֧�ֹ��ܣ���ʱֻ�Ƕ�ȡ��ǰ���±��룬�����������ڱ���ʱ
				String number = iCodingRuleManager.getNumber(info, costUnitId);
				info.setNumber(number);
			}
		}
	}	
	

	
	
	
}