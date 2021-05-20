package com.kingdee.eas.fdc.tenancy.app;

import java.util.Date;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.base.codingrule.CodingRuleException;
import com.kingdee.eas.base.codingrule.CodingRuleManagerFactory;
import com.kingdee.eas.base.codingrule.ICodingRuleManager;
import com.kingdee.eas.base.permission.UserInfo;
import com.kingdee.eas.basedata.org.SaleOrgUnitInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.sellhouse.app.DataBaseCodeRuleHelper;
import com.kingdee.eas.fdc.tenancy.RestReceivableInfo;
import com.kingdee.eas.fdc.tenancy.TenBillBaseInfo;
import com.kingdee.eas.fdc.tenancy.TenancyBillStateEnum;
import com.kingdee.eas.framework.DataBaseInfo;
import com.kingdee.eas.util.app.ContextUtil;

import com.kingdee.util.NumericExceptionSubItem;
import com.kingdee.util.StringUtils;

public class RestReceivableControllerBean extends AbstractRestReceivableControllerBean
{
    private static Logger logger =
        Logger.getLogger("com.kingdee.eas.fdc.tenancy.app.RestReceivableControllerBean");

//	protected IObjectPK _save(Context ctx,IObjectValue model)
//			throws BOSException, EASBizException {
//		RestReceivableInfo restRevModel = (RestReceivableInfo)model;
//		RestReceivableEntryCollection restRevColls = restRevModel.getEntrys();
//		// �������޺�ͬ����Ӧ������
//		ITenBillOtherPay  opayDao = TenBillOtherPayFactory.getLocalInstance(ctx);
//		TenancyBillInfo tb = restRevModel.getTenancyBill();
//		if(tb != null && restRevColls != null && !restRevColls.isEmpty()){
//			Iterator iterator = restRevColls.iterator();
//			RestReceivableEntryInfo restRevEntry = null;
//			TenBillOtherPayInfo otherPay = null;
//			IObjectPK otherPayPK = null;
//			while(iterator.hasNext()){
//				restRevEntry = (RestReceivableEntryInfo)iterator.next();
//				otherPay = restRevEntry.readData(restRevEntry);
//				otherPay.setHead(tb);
//				otherPayPK = opayDao.save(otherPay);
//				otherPay.setId(BOSUuid.read(otherPayPK.toString()));
//				
//				restRevEntry.setTenancyBillEntry(otherPay);
//			}
//		}
//		
//		return super._save(ctx, model);
//	}


//	protected void _delete(Context ctx, IObjectPK pk) throws BOSException,
//			EASBizException {
//		// ����ɾ�����޺�ͬ����Ӧ��
//		ITenBillOtherPay  opayDao = TenBillOtherPayFactory.getLocalInstance(ctx);
//		
//		RestReceivableInfo model = this.getRestReceivableInfo(ctx,pk,getSelectors());
//		RestReceivableEntryCollection colls = model.getEntrys();
//		
//		RestReceivableEntryInfo entry = null;
//		TenBillOtherPayInfo otherPay = null;
//		IObjectPK opayPK = null; 
//		if(colls != null && !colls.isEmpty()){
//			Iterator iterator = colls.iterator();
//			while(iterator.hasNext()){
//				entry = (RestReceivableEntryInfo)iterator.next();
//				otherPay = entry.getTenancyBillEntry();
//				if(otherPay != null){
//					opayPK = new ObjectUuidPK(otherPay.getId());
//					opayDao.delete(opayPK);
//				}
//			}
//		}
//		super._delete(ctx, pk);
//	}
    
	private SelectorItemCollection getSelectors(){
		SelectorItemCollection sic = new SelectorItemCollection ();
		sic.add("id");
		sic.add("entrys.id");
		sic.add("entrys.tenancyBillEntry.id");
		return sic;
	}
	protected void _audit(Context ctx, BOSUuid billId) throws BOSException,
			EASBizException {
		UserInfo auditor = ContextUtil.getCurrentUserInfo(ctx);
		Date auditDate = new Date(System.currentTimeMillis());
		
		RestReceivableInfo restRev = this.getRestReceivableInfo(ctx, new ObjectUuidPK(billId));
		restRev.setAuditor(auditor);
		restRev.setAuditDate(auditDate);
		restRev.setBillState(TenancyBillStateEnum.Audited);
		
		//add by yangfan
		for (int i = 0; i < restRev.getOtherPayList().size(); i++) {
			if (restRev.getOtherPayList().get(i).getHead() == null) {
				restRev.getOtherPayList().get(i).setHead(
						restRev.getTenancyBill());
			}
		}
		this.updatePartial(ctx, restRev, getUpdateSIC());
	}

	protected void _setAuditting(Context ctx, BOSUuid billId)throws BOSException, EASBizException {
		RestReceivableInfo bill = new RestReceivableInfo();

		bill.setId(billId);
		bill.setBillState(TenancyBillStateEnum.Auditing);
		SelectorItemCollection selector = new SelectorItemCollection();
		selector.add("billState");

		_updatePartial(ctx, bill, selector);
	}
	protected void _setSubmit(Context ctx, BOSUuid billId) throws BOSException,
			EASBizException {
		RestReceivableInfo bill = new RestReceivableInfo();

		bill.setId(billId);
		bill.setBillState(TenancyBillStateEnum.Submitted);
		SelectorItemCollection selector = new SelectorItemCollection();
		selector.add("billState");

		_updatePartial(ctx, bill, selector);
	}
	protected void _unAudit(Context ctx, BOSUuid billId) throws BOSException,
			EASBizException {
		RestReceivableInfo restRev = (RestReceivableInfo)this.getRestReceivableInfo(ctx, new ObjectUuidPK(billId));
		for(int i=0;i<restRev.getOtherPayList().size();i++){
			if(restRev.getOtherPayList().get(i).getActRevAmount()!=null){
				throw new EASBizException(new NumericExceptionSubItem("100","����ʵ�ս���ֹ��������"));
			}
		}
		restRev.setAuditor(null);
		restRev.setAuditDate(null);
		restRev.setBillState(TenancyBillStateEnum.Submitted);
		
		if(restRev != null && restRev.getOtherPayList().size() > 0)
        {
			 for(int i = 0; i < restRev.getOtherPayList().size(); i++)
				 restRev.getOtherPayList().get(i).setHead(null);
        }
		this.updatePartial(ctx, restRev, getUpdateSIC());
	}

	protected void _passAudit(Context ctx, IObjectPK pk, IObjectValue model)
			throws EASBizException, BOSException {
//		super._passAudit(ctx, pk, model);
		
		UserInfo auditor = ContextUtil.getCurrentUserInfo(ctx);
		Date auditDate = new Date(System.currentTimeMillis());
		
		RestReceivableInfo restRev = (RestReceivableInfo)model;
		restRev.setAuditor(auditor);
		restRev.setAuditDate(auditDate);
		restRev.setBillState(TenancyBillStateEnum.Audited);
		
		//add by yangfan
		for (int i = 0; i < restRev.getOtherPayList().size(); i++) {
			if (restRev.getOtherPayList().get(i).getHead() == null) {
				restRev.getOtherPayList().get(i).setHead(
						restRev.getTenancyBill());
			}
		}
		this.updatePartial(ctx, restRev, getUpdateSIC());
	}

	protected void _unpassAudit(Context ctx, IObjectPK pk, IObjectValue model)
			throws EASBizException, BOSException {
//		super._unpassAudit(ctx, pk, model);
		
		RestReceivableInfo restRev = (RestReceivableInfo)model;
		restRev.setAuditor(null);
		restRev.setAuditDate(null);
		restRev.setBillState(TenancyBillStateEnum.Submitted);
		
		 if(restRev != null && restRev.getOtherPayList().size() > 0)
         {
			 for(int i = 0; i < restRev.getOtherPayList().size(); i++)
				 restRev.getOtherPayList().get(i).setHead(null);
         }
		 
		this.updatePartial(ctx, restRev, getUpdateSIC());
	}
	
	private SelectorItemCollection getUpdateSIC(){
		SelectorItemCollection sic = new SelectorItemCollection();
		sic.add("auditor");
		sic.add("auditDate");
		sic.add("billState");
		
		//add by yangfan
		sic.add("otherPayList.head");
		return sic;
	}
	
	//by tim_gao ��Ϻű��벻��ʹ�ã���ӷ��� 2010-11-16
	protected IObjectPK _submit(Context ctx, IObjectValue model) throws BOSException, EASBizException {
		
		handleIntermitNumber(ctx,(RestReceivableInfo)model);
		return super._submit(ctx, model);
	}
	
	protected IObjectPK _save(Context ctx, IObjectValue model) throws BOSException, EASBizException {
		
		handleIntermitNumber(ctx,(RestReceivableInfo)model);
		return super._save(ctx, model);
	}
	
	//by tim_gao ���ձ������ 2010-11-16
	protected void _delete(Context ctx, IObjectPK[] arrayPK) throws BOSException, EASBizException {
	    	for (int i = 0; i < arrayPK.length; i++) {
	    		recycleNumber(ctx, (RestReceivableInfo) _getValue(ctx, arrayPK[i]));
			}
	    	super._delete(ctx, arrayPK);
	}
	    
	protected void _delete(Context ctx, IObjectPK pk) throws BOSException, EASBizException {
		recycleNumber(ctx, (RestReceivableInfo) _getValue(ctx, pk));
	    	super._delete(ctx, pk);
	}

	public static void handleIntermitNumber(Context ctx, RestReceivableInfo info)
		throws BOSException, CodingRuleException, EASBizException {
		// ����û��ڿͻ����ֹ�ѡ���˶Ϻ�,��˴�����������
		if (info.getNumber() != null && info.getNumber().length() > 0)
			return;

		ICodingRuleManager iCodingRuleManager = CodingRuleManagerFactory
			.getLocalInstance(ctx);

		String costUnitId = ContextUtil.getCurrentOrgUnit(ctx).getId()
			.toString();

		if (StringUtils.isEmpty(costUnitId)) {
			return;
		}

	if (iCodingRuleManager.isExist(info, costUnitId)) {
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
	
	/**
	 * ����Number����������˱������֧�ֶϺŵĻ�
	 * @param ctx
	 * @param info
	 * @throws BOSException
	 * @throws EASBizException
	 * @throws CodingRuleException
	 */
	public static void recycleNumber(Context ctx, RestReceivableInfo info) throws BOSException, EASBizException, CodingRuleException {
		SaleOrgUnitInfo currentSaleUnit = ContextUtil.getCurrentSaleUnit(ctx);
		String curOrgId = currentSaleUnit.getId().toString();
		if(info.getNumber()!=null&&info.getNumber().length()>0){
			ICodingRuleManager iCodingRuleManager = CodingRuleManagerFactory.getLocalInstance(ctx);
	        if (iCodingRuleManager.isExist(info, curOrgId) && iCodingRuleManager.isUseIntermitNumber(info, curOrgId)) {
	            iCodingRuleManager.recycleNumber(info, curOrgId, info.getNumber());
	        }
		}
	}
	}