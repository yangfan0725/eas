package com.kingdee.eas.fdc.contract.app;

import org.apache.log4j.Logger;
import javax.ejb.*;
import java.rmi.RemoteException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import com.kingdee.bos.*;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.bos.metadata.IMetaDataPK;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.metadata.rule.RuleExecutor;
import com.kingdee.bos.metadata.MetaDataPK;
//import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.framework.ejb.AbstractEntityControllerBean;
import com.kingdee.bos.framework.ejb.AbstractBizControllerBean;
//import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.IObjectCollection;
import com.kingdee.bos.service.ServiceContext;
import com.kingdee.bos.service.IServiceContext;

import java.lang.String;
import java.math.BigDecimal;

import com.kingdee.eas.base.codingrule.CodingRuleManagerFactory;
import com.kingdee.eas.base.codingrule.ICodingRuleManager;
import com.kingdee.eas.base.param.ParamControlFactory;
import com.kingdee.eas.base.permission.UserInfo;
import com.kingdee.eas.basedata.master.auxacct.AsstAccountFactory;
import com.kingdee.eas.basedata.master.auxacct.AsstAccountInfo;
import com.kingdee.eas.basedata.master.auxacct.AsstActGroupDetailCollection;
import com.kingdee.eas.basedata.master.auxacct.AsstActTypeInfo;
import com.kingdee.eas.basedata.master.cssp.CustomerInfo;
import com.kingdee.eas.basedata.org.CompanyOrgUnitFactory;
import com.kingdee.eas.basedata.org.CostCenterOrgUnitCollection;
import com.kingdee.eas.basedata.org.CostCenterOrgUnitFactory;
import com.kingdee.eas.basedata.org.CtrlUnitInfo;
import com.kingdee.eas.basedata.org.OrgUnitInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.cp.bc.ExpenseTypeCollection;
import com.kingdee.eas.cp.bc.ExpenseTypeFactory;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.ormapping.IORMappingDAO;
import com.kingdee.bos.dao.ormapping.ORMappingDAO;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.eas.fdc.contract.ContractRecBillFactory;
import com.kingdee.eas.fdc.contract.ContractRecBillInfo;
import com.kingdee.eas.fdc.contract.IContractRecBill;
import com.kingdee.eas.fdc.basecrm.CRMHelper;
import com.kingdee.eas.fdc.basecrm.FDCReceivingBillCollection;
import com.kingdee.eas.fdc.basecrm.FDCReceivingBillEntryCollection;
import com.kingdee.eas.fdc.basecrm.FDCReceivingBillFactory;
import com.kingdee.eas.fdc.basecrm.FDCReceivingBillInfo;
import com.kingdee.eas.fdc.basecrm.IFDCReceivingBill;
import com.kingdee.eas.fdc.basecrm.RevBillTypeEnum;
import com.kingdee.eas.fdc.basedata.app.FDCBillControllerBean;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.metadata.entity.SorterItemCollection;
import com.kingdee.eas.fdc.contract.ContractRecBillCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.eas.framework.CoreBillBaseCollection;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.eas.fdc.basedata.FDCBillCollection;
import com.kingdee.eas.fdc.basedata.FDCBillInfo;
import com.kingdee.eas.fdc.basedata.FDCDateHelper;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fi.cas.AssItemsForCashPayInfo;
import com.kingdee.eas.fi.cas.AssItemsForCashRecInfo;
import com.kingdee.eas.fi.cas.BillStatusEnum;
import com.kingdee.eas.fi.cas.CasRecPayBillTypeEnum;
import com.kingdee.eas.fi.cas.IReceivingBillType;
import com.kingdee.eas.fi.cas.PaymentBillEntryCollection;
import com.kingdee.eas.fi.cas.PaymentBillEntryInfo;
import com.kingdee.eas.fi.cas.PaymentBillFactory;
import com.kingdee.eas.fi.cas.PaymentBillInfo;
import com.kingdee.eas.fi.cas.PaymentBillTypeInfo;
import com.kingdee.eas.fi.cas.ReceivingBillEntryCollection;
import com.kingdee.eas.fi.cas.ReceivingBillEntryInfo;
import com.kingdee.eas.fi.cas.ReceivingBillFactory;
import com.kingdee.eas.fi.cas.ReceivingBillInfo;
import com.kingdee.eas.fi.cas.ReceivingBillTypeCollection;
import com.kingdee.eas.fi.cas.ReceivingBillTypeFactory;
import com.kingdee.eas.fi.cas.ReceivingBillTypeInfo;
import com.kingdee.eas.fi.cas.SettlementStatusEnum;
import com.kingdee.eas.fi.cas.SourceTypeEnum;
import com.kingdee.eas.fi.cas.app.ArApRecPayServerHelper;
import com.kingdee.eas.framework.ObjectBaseCollection;
import com.kingdee.eas.util.app.ContextUtil;
import com.kingdee.util.NumericExceptionSubItem;

public class ContractRecBillControllerBean extends AbstractContractRecBillControllerBean
{
    private static Logger logger =
        Logger.getLogger("com.kingdee.eas.fdc.contract.app.ContractRecBillControllerBean");
    protected void checkNameDup(Context ctx, FDCBillInfo billInfo)throws BOSException, EASBizException{
    	
    	
    }
	@Override
	protected void _audit(Context ctx, BOSUuid billId) throws BOSException,
			EASBizException {
		super._audit(ctx, billId);
		ArrayList list=new ArrayList();
		list.add(billId.toString());
		this.createCashBill(ctx, list);
	}
	@Override
	protected void _unAudit(Context ctx, BOSUuid billId) throws BOSException,
			EASBizException {
		super._unAudit(ctx, billId);
		if(ReceivingBillFactory.getLocalInstance(ctx).exists("select id from where sourceBillId='"+billId+"'")){
			throw new EASBizException(new NumericExceptionSubItem("101","已生成出纳收付款单，请先删除出纳收付款单后再进行反审批操作！"));
		}
	}
	private ReceivingBillTypeInfo getReceivingBillType(Context ctx){
		ReceivingBillTypeInfo typeInfo = null;
		ReceivingBillTypeCollection coll =null;
		EntityViewInfo ev = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		ev.setFilter(filter);
		filter.getFilterItems().add(
				new FilterItemInfo("number", "999",
						CompareType.EQUALS));
		IReceivingBillType iReceivingBillType;
		try {
			iReceivingBillType = ReceivingBillTypeFactory
					.getLocalInstance(ctx);
			
			coll = iReceivingBillType.getReceivingBillTypeCollection(ev);
		} catch (BOSException e) {
			logger.error(e.getMessage()+"得到出纳系统的默认的收款类型!");
		}
		

		if (coll != null && !coll.isEmpty()) {
			typeInfo = coll.get(0);
		}
		
		return typeInfo;
	}
	private void createCashBill(Context ctx, ArrayList idList) throws BOSException, EASBizException{
		
		IContractRecBill receivingBill= ContractRecBillFactory.getLocalInstance(ctx);
		Set  idSet = new HashSet(); 
		for (int i = 0; i < idList.size(); i++){
			String id = idList.get(i).toString();
			idSet.add(id);
		}
		
		EntityViewInfo evi = new EntityViewInfo();
		FilterInfo filterInfo = new FilterInfo();
		filterInfo.getFilterItems().add(new FilterItemInfo("id", idSet, CompareType.INCLUDE));
		evi.setFilter(filterInfo);

		SelectorItemCollection coll = new SelectorItemCollection();
		coll.add(new SelectorItemInfo("*"));
		coll.add(new SelectorItemInfo("customer.*"));
		coll.add(new SelectorItemInfo("contractBillReceive.currency.*"));
		coll.add(new SelectorItemInfo("revAccount.*"));
		coll.add(new SelectorItemInfo("accountBank.*"));
		coll.add(new SelectorItemInfo("settlementType.*"));
		coll.add(new SelectorItemInfo("bank.*"));
		coll.add(new SelectorItemInfo("currency.*"));
		coll.add(new SelectorItemInfo("entry.*"));
		coll.add(new SelectorItemInfo("entry.oppAccount.*"));
		coll.add(new SelectorItemInfo("entry.moneyDefine.*"));
		evi.setSelector(coll);

		
		ContractRecBillCollection collection = receivingBill.getContractRecBillCollection(evi);
		
		
		Set updateIdSet = new HashSet();
		
		
		UserInfo userInfo = ContextUtil.getCurrentUserInfo(ctx);
		CtrlUnitInfo cuInfo = ContextUtil.getCurrentCtrlUnit(ctx);

		IORMappingDAO iReceiving = ORMappingDAO.getInstance(new ReceivingBillInfo().getBOSType(), ctx, getConnection(ctx));
		IORMappingDAO iPay = ORMappingDAO.getInstance(new PaymentBillInfo().getBOSType(), ctx, getConnection(ctx));
		
		ReceivingBillInfo rev=null;
		PaymentBillInfo pay=null;
		RevBillTypeEnum type=null;
		
		SelectorItemCollection sels = new SelectorItemCollection();
    	sels.add("asstActGpDt.asstActType.*");
    	sels.add("asstActGpDt.*");
    	
		for (int i = 0; i < collection.size(); i++) {
			ContractRecBillCollection billColl = null;
			String id = collection.get(i).getId().toString();
			billColl=collection;
			if(billColl!=null && billColl.size()>0){
				for (int j = 0; j < billColl.size(); j++) {
					ContractRecBillInfo fdcReceivingBillInfo = billColl.get(j);

					ReceivingBillTypeInfo receivingBillTypeInfo = getReceivingBillType(ctx);
					
					ReceivingBillInfo  receivingBillInfo = new ReceivingBillInfo();
					//公司
					receivingBillInfo.setCompany(CompanyOrgUnitFactory.getLocalInstance(ctx).getCompanyOrgUnitInfo(new ObjectUuidPK(fdcReceivingBillInfo.getOrgUnit().getId())));
					//业务日期
					receivingBillInfo.setBizDate(FDCDateHelper.getDayBegin(fdcReceivingBillInfo.getBizDate()));
					//汇率
					receivingBillInfo.setExchangeRate(fdcReceivingBillInfo.getRate());
					//币别
					receivingBillInfo.setCurrency(fdcReceivingBillInfo.getCurrency());
					
					//收款金额
					receivingBillInfo.setActRecAmt(fdcReceivingBillInfo.getAmount());
					
					receivingBillInfo.setCreator(userInfo);
					receivingBillInfo.setCreateTime(new Timestamp(System.currentTimeMillis()));
					receivingBillInfo.setLastUpdateTime(new Timestamp(System.currentTimeMillis()));
					receivingBillInfo.setLastUpdateUser(userInfo);
					receivingBillInfo.setCU(cuInfo);
					
				
					/**
					 * 设置编码		
					 */
					ICodingRuleManager iCodingRuleManager = null;
					if(ctx!=null)	iCodingRuleManager = CodingRuleManagerFactory.getLocalInstance(ctx);
					else 	iCodingRuleManager = CodingRuleManagerFactory.getRemoteInstance();
					
					OrgUnitInfo orgUnit = null;
					if(ctx!=null) {
						orgUnit = ContextUtil.getCurrentSaleUnit(ctx);
						if(orgUnit==null) orgUnit = ContextUtil.getCurrentOrgUnit(ctx);
					}else {
						orgUnit = SysContext.getSysContext().getCurrentSaleUnit();
						if(orgUnit==null) orgUnit = SysContext.getSysContext().getCurrentOrgUnit();
					}
					
					String retNumber = iCodingRuleManager.getNumber(receivingBillInfo, orgUnit.getId().toString());
					
					
					///if(retNumber==null || retNumber.trim().length()==0 )
						//throw new EASBizException(new NumericExceptionSubItem("100","未启用编码规则 ，不能自动生成转款单！"));
					
					if(retNumber!=null && !"".equals(retNumber)){
						receivingBillInfo.setNumber(retNumber);
					}
					
					/**
					 * 添加收款人
					 */
					CustomerInfo custInfo  = fdcReceivingBillInfo.getCustomer();
					if(custInfo!=null){
						receivingBillInfo.setPayerID(custInfo.getId().toString());
						receivingBillInfo.setPayerNumber(custInfo.getNumber());
						receivingBillInfo.setPayerName(custInfo.getName());
					}
//					//折本位币
//					BigDecimal actrecLocAmt = FDCHelper.ZERO;
//					BigDecimal ecchangeRate = FDCHelper.ONE;
					
//					if(fdcReceivingBillInfo.getExchangeRate()==null){
//						ecchangeRate = fdcReceivingBillInfo.getExchangeRate();
//					}
//					
//					actrecLocAmt  = FDCHelper.multiply(billEntry.get(k).getRevAmount(), ecchangeRate);
					
					receivingBillInfo.setActRecLocAmt(fdcReceivingBillInfo.getAmount());
					
					//收款科目
						receivingBillInfo.setPayeeAccount(fdcReceivingBillInfo.getRevAccount());	
					
					// 检查科目合法性
					try {
						arapHelper.verifyAccountView(ctx, fdcReceivingBillInfo.getRevAccount(), receivingBillInfo
								.getCurrency(), receivingBillInfo.getCompany());
					} catch (EASBizException e) {
						logger.error(e.getMessage()+"科目不按指定币别核算，请选择其它币别！");
						throw new BOSException("科目不按指定币别核算，请选择其它币别！");
					}
					
					//收款账户
//					if(billEntry.get(i).getRevAccountBank()!=null){
						receivingBillInfo.setPayeeAccountBank(fdcReceivingBillInfo.getAccountBank());	
//					}
					
					//结算方式
					receivingBillInfo.setSettlementType(fdcReceivingBillInfo.getSettlementType());
					//结算号
					receivingBillInfo.setSettlementNumber(fdcReceivingBillInfo.getSettlementNumber());
					receivingBillInfo.setPayeeBank(fdcReceivingBillInfo.getBank());
					
//					MoneyDefineInfo moneyDefineInfo  = billEntry.get(k).getMoneyDefine();
					
//					/**
//					 * 新增加收款类型的转换
//					 */
//					if(moneyDefineInfo!=null){
//						//收款类型
//						receivingBillInfo.setRecBillType(moneyDefineInfo.getRevBillType());
//					}else{
						//收款类型
						receivingBillInfo.setRecBillType(receivingBillTypeInfo);
//					}
					/**
					 * 设置非空字段
					 */
					receivingBillInfo.setReceivingBillType(CasRecPayBillTypeEnum.RealType);
					receivingBillInfo.setIsExchanged(false);
					receivingBillInfo.setIsInitializeBill(false);
					receivingBillInfo.setIsImport(false);
					receivingBillInfo.setFiVouchered(false);
					receivingBillInfo.setSettlementStatus(SettlementStatusEnum.UNSUBMIT);
					receivingBillInfo.setIsAppointVoucher(false);
					receivingBillInfo.setIsCoopBuild(false);
					receivingBillInfo.setSourceType(SourceTypeEnum.CASH);
					//单据状态
					receivingBillInfo.setBillStatus(BillStatusEnum.SAVE);
					//原始单据id
					receivingBillInfo.setSourceBillId(id);
					
//					Set mdName=new HashSet();
					for (int k = 0; k <fdcReceivingBillInfo.getEntry().size(); k++) {
						/**
						 * 以下是分录内容
						 */
						ReceivingBillEntryCollection receBillEntry = receivingBillInfo.getEntries();
						ReceivingBillEntryInfo receBillEntryInfo = new ReceivingBillEntryInfo();
						//收款金额
						receBillEntryInfo.setActualAmt(fdcReceivingBillInfo.getEntry().get(i).getAmount());
						//对方科目
						if(fdcReceivingBillInfo.getEntry().get(i).getOppAccount()!=null){
							receBillEntryInfo.setOppAccount(fdcReceivingBillInfo.getEntry().get(i).getOppAccount());	
							
							if(receBillEntryInfo.getOppAccount().getCAA()!=null){
								AsstAccountInfo account=AsstAccountFactory.getLocalInstance(ctx).getAsstAccountInfo(new ObjectUuidPK(receBillEntryInfo.getOppAccount().getCAA().getId()),sels);
								AsstActGroupDetailCollection aacol=account.getAsstActGpDt();
								CRMHelper.sortCollection(aacol, "seq", true);
								for(int l=0;l<aacol.size();l++){
									AsstActTypeInfo asstActType=aacol.get(l).getAsstActType();
									AssItemsForCashRecInfo ass=new AssItemsForCashRecInfo();
									ass.setTableName(asstActType.getRealtionDataObject());
									ass.setMappingFileds(asstActType.getMappingFieldName());
									ass.setAsstActType(asstActType);
									ass.setIsSelected(false);
									ass.setSeq(aacol.get(l).getSeq());
									ass.setEntrySeq(i+1);
									
									if(asstActType.getRealtionDataObject().equals("T_BD_Customer")){
										if(custInfo!=null){
											ass.setFromID(custInfo.getId().toString());
											ass.setFromNumber(custInfo.getNumber());
											ass.setIsSelected(true);
										}
									}
									receBillEntryInfo.getAssItemsEntries().add(ass);
								}
							}
						}
//						Map date=getDate(billEntry.get(k).getRevListId(),ctx);
						
						receBillEntry.add(receBillEntryInfo);
//						if(fdcReceivingBillInfo.getEntry().get(i).getMoneyDefine()!=null)
//							mdName.add(billEntry.get(k).getMoneyDefine().getName()+date.get("startDate")+date.get("endDate"));
					}
//					if(fdcReceivingBillInfo.getRoom()!=null){
//						receivingBillInfo.setDescription(fdcReceivingBillInfo.getRoom().getName());
//						Iterator<String> it = mdName.iterator();  
//						while (it.hasNext()) {
//							String str = it.next();
//							receivingBillInfo.setDescription(receivingBillInfo.getDescription()+";"+str);
//						}  
//					}
					rev=receivingBillInfo;
					iReceiving.addNewBatch(receivingBillInfo);
				}
			}
			updateIdSet.add(id);
		}
		
		
		ReceivingBillFactory.getLocalInstance(ctx).submit(rev);
		Set revId=new HashSet();
		revId.add(rev.getId().toString());
		ReceivingBillFactory.getLocalInstance(ctx).audit(revId);
		ReceivingBillFactory.getLocalInstance(ctx).rec(revId);
	}
	ArApRecPayServerHelper arapHelper = new ArApRecPayServerHelper();
}