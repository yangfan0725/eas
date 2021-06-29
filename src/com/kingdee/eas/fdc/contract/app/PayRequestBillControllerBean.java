package com.kingdee.eas.fdc.contract.app;

import java.math.BigDecimal;
import java.net.URLEncoder;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.axis.client.Call;
import org.apache.axis.client.Service;
import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.Context;
import com.kingdee.bos.IBOSObject;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.dao.IObjectCollection;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.bot.BOTRelationCollection;
import com.kingdee.bos.metadata.data.SortType;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemCollection;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.bos.webservice.login.EASLoginProxy;
import com.kingdee.bos.workflow.ProcessInstInfo;
import com.kingdee.bos.workflow.service.ormrpc.EnactmentServiceFactory;
import com.kingdee.bos.workflow.service.ormrpc.IEnactmentService;
import com.kingdee.eas.base.attachment.AttachmentFactory;
import com.kingdee.eas.base.attachment.AttachmentInfo;
import com.kingdee.eas.base.attachment.AttachmentStorageTypeEnum;
import com.kingdee.eas.base.attachment.BoAttchAssoCollection;
import com.kingdee.eas.base.attachment.BoAttchAssoFactory;
import com.kingdee.eas.base.botp.BOTBillOperStateEnum;
import com.kingdee.eas.base.btp.BTPManagerFactory;
import com.kingdee.eas.base.btp.BTPTransformResult;
import com.kingdee.eas.base.btp.IBTPManager;
import com.kingdee.eas.base.codingrule.CodingRuleException;
import com.kingdee.eas.base.param.ParamControlFactory;
import com.kingdee.eas.base.permission.UserFactory;
import com.kingdee.eas.base.permission.UserInfo;
import com.kingdee.eas.basedata.assistant.CurrencyInfo;
import com.kingdee.eas.basedata.assistant.PeriodInfo;
import com.kingdee.eas.basedata.master.auxacct.AsstActTypeCollection;
import com.kingdee.eas.basedata.master.auxacct.AsstActTypeFactory;
import com.kingdee.eas.basedata.master.auxacct.IAsstActType;
import com.kingdee.eas.basedata.master.cssp.SupplierCompanyInfoInfo;
import com.kingdee.eas.basedata.master.cssp.SupplierFactory;
import com.kingdee.eas.basedata.org.CompanyOrgUnitInfo;
import com.kingdee.eas.basedata.org.CostCenterOrgUnitInfo;
import com.kingdee.eas.basedata.org.CtrlUnitInfo;
import com.kingdee.eas.basedata.org.FullOrgUnitFactory;
import com.kingdee.eas.basedata.org.FullOrgUnitInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.cp.bc.ExpenseTypeFactory;
import com.kingdee.eas.cp.bc.ExpenseTypeInfo;
import com.kingdee.eas.fdc.basedata.ContractTypeOrgTypeEnum;
import com.kingdee.eas.fdc.basedata.CurProjectFactory;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.fdc.basedata.DeductTypeCollection;
import com.kingdee.eas.fdc.basedata.DeductTypeFactory;
import com.kingdee.eas.fdc.basedata.DeductTypeInfo;
import com.kingdee.eas.fdc.basedata.FDCBillInfo;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCCommonServerHelper;
import com.kingdee.eas.fdc.basedata.FDCConstants;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.basedata.IFDCBill;
import com.kingdee.eas.fdc.basedata.PaymentTypeInfo;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.contract.ChangeAuditBillFactory;
import com.kingdee.eas.fdc.contract.ChangeAuditBillInfo;
import com.kingdee.eas.fdc.contract.CompensationBillFactory;
import com.kingdee.eas.fdc.contract.CompensationBillInfo;
import com.kingdee.eas.fdc.contract.CompensationOfPayReqBillCollection;
import com.kingdee.eas.fdc.contract.CompensationOfPayReqBillFactory;
import com.kingdee.eas.fdc.contract.CompensationOfPayReqBillInfo;
import com.kingdee.eas.fdc.contract.ContractBaseDataCollection;
import com.kingdee.eas.fdc.contract.ContractBaseDataFactory;
import com.kingdee.eas.fdc.contract.ContractBillFactory;
import com.kingdee.eas.fdc.contract.ContractBillInfo;
import com.kingdee.eas.fdc.contract.ContractChangeBillFactory;
import com.kingdee.eas.fdc.contract.ContractCostSplitEntryCollection;
import com.kingdee.eas.fdc.contract.ContractCostSplitEntryInfo;
import com.kingdee.eas.fdc.contract.ContractException;
import com.kingdee.eas.fdc.contract.ContractSettlementBillCollection;
import com.kingdee.eas.fdc.contract.ContractSettlementBillFactory;
import com.kingdee.eas.fdc.contract.ContractWithoutTextFactory;
import com.kingdee.eas.fdc.contract.ContractWithoutTextInfo;
import com.kingdee.eas.fdc.contract.DeductOfPayReqBillCollection;
import com.kingdee.eas.fdc.contract.DeductOfPayReqBillEntryCollection;
import com.kingdee.eas.fdc.contract.DeductOfPayReqBillEntryFactory;
import com.kingdee.eas.fdc.contract.DeductOfPayReqBillEntryInfo;
import com.kingdee.eas.fdc.contract.DeductOfPayReqBillFactory;
import com.kingdee.eas.fdc.contract.DeductOfPayReqBillInfo;
import com.kingdee.eas.fdc.contract.FDCUtils;
import com.kingdee.eas.fdc.contract.GuerdonBillCollection;
import com.kingdee.eas.fdc.contract.GuerdonBillFactory;
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
import com.kingdee.eas.fdc.contract.PayContentTypeFactory;
import com.kingdee.eas.fdc.contract.PayContentTypeInfo;
import com.kingdee.eas.fdc.contract.PayReqInvoiceEntryFactory;
import com.kingdee.eas.fdc.contract.PayRequestBillBgEntryInfo;
import com.kingdee.eas.fdc.contract.PayRequestBillCollection;
import com.kingdee.eas.fdc.contract.PayRequestBillConfirmEntryCollection;
import com.kingdee.eas.fdc.contract.PayRequestBillConfirmEntryFactory;
import com.kingdee.eas.fdc.contract.PayRequestBillConfirmEntryInfo;
import com.kingdee.eas.fdc.contract.PayRequestBillEntryFactory;
import com.kingdee.eas.fdc.contract.PayRequestBillException;
import com.kingdee.eas.fdc.contract.PayRequestBillFactory;
import com.kingdee.eas.fdc.contract.PayRequestBillInfo;
import com.kingdee.eas.fdc.finance.ContractPayPlanCollection;
import com.kingdee.eas.fdc.finance.ContractPayPlanFactory;
import com.kingdee.eas.fdc.finance.ContractPayPlanInfo;
import com.kingdee.eas.fdc.finance.DeductBillEntryCollection;
import com.kingdee.eas.fdc.finance.DeductBillEntryFactory;
import com.kingdee.eas.fdc.finance.DeductBillEntryInfo;
import com.kingdee.eas.fdc.finance.FDCBudgetAcctFacadeFactory;
import com.kingdee.eas.fdc.finance.FDCBudgetConstants;
import com.kingdee.eas.fdc.finance.FDCBudgetPeriodInfo;
import com.kingdee.eas.fdc.finance.FDCPaymentBillCollection;
import com.kingdee.eas.fdc.finance.FDCPaymentBillFactory;
import com.kingdee.eas.fdc.finance.FDCPaymentBillHelper;
import com.kingdee.eas.fdc.finance.FDCPaymentBillInfo;
import com.kingdee.eas.fdc.finance.PayRequestSplitFactory;
import com.kingdee.eas.fdc.finance.ShowDeductOfPartABillFactory;
import com.kingdee.eas.fdc.finance.WorkLoadConfirmBillFactory;
import com.kingdee.eas.fdc.material.MaterialConfirmBillCollection;
import com.kingdee.eas.fdc.material.MaterialConfirmBillFactory;
import com.kingdee.eas.fdc.material.MaterialConfirmBillInfo;
import com.kingdee.eas.fdc.schedule.FDCSCHUtils;
import com.kingdee.eas.fi.cas.BillStatusEnum;
import com.kingdee.eas.fi.cas.PaymentBillEntryInfo;
import com.kingdee.eas.fi.cas.PaymentBillFactory;
import com.kingdee.eas.fi.cas.PaymentBillInfo;
import com.kingdee.eas.fi.cas.UrgentDegreeEnum;
import com.kingdee.eas.fi.gl.GlUtils;
import com.kingdee.eas.framework.BillBaseCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.eas.ma.budget.BgControlFacadeFactory;
import com.kingdee.eas.ma.budget.BgCtrlParamCollection;
import com.kingdee.eas.ma.budget.BgCtrlResultCollection;
import com.kingdee.eas.ma.budget.BgCtrlTypeEnum;
import com.kingdee.eas.ma.budget.BgItemInfo;
import com.kingdee.eas.ma.budget.IBgControlFacade;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.app.ContextUtil;
import com.kingdee.jdbc.rowset.IRowSet;
import com.kingdee.util.DateTimeUtils;
import com.kingdee.util.NumericExceptionSubItem;

public class PayRequestBillControllerBean extends AbstractPayRequestBillControllerBean
{
    private static Logger logger =
        Logger.getLogger("com.kingdee.eas.fdc.contract.app.PayRequestBillControllerBean");
    
    private BOSUuid payBillId;
    private void checkInvoice(PayRequestBillInfo info,Context ctx) throws EASBizException, BOSException{
    	for(int i=0;i<info.getInvoiceEntry().size();i++){
    		FilterInfo filter=new FilterInfo();
    		filter.getFilterItems().add(new FilterItemInfo("invoice.id",info.getInvoiceEntry().get(i).getInvoice().getId().toString()));
    		if(info.getId()!=null){
    			filter.getFilterItems().add(new FilterItemInfo("parent.id",info.getId().toString(),CompareType.NOTEQUALS));
    		}
    		if(PayReqInvoiceEntryFactory.getLocalInstance(ctx).exists(filter)){
    			throw new EASBizException(new NumericExceptionSubItem("100","�Ѿ��������������뵥������������ѡ��"));
    		}
    	}
    }
	protected void _save(Context ctx, IObjectPK pk, IObjectValue model) throws BOSException, EASBizException {
		PayRequestBillInfo info=(PayRequestBillInfo)model;
		checkInvoice(info,ctx);
		info.setAmount(FDCHelper.multiply(info.getOriginalAmount(), info.getExchangeRate()));
		super._save(ctx, pk, info);
		
		//R101207-373 �������뵥�޸��ύʱ����Ҫ���¼���������뵥�ġ���ͬ�ڹ����ۼ����롱��ֵ  by zhiyuan_tang 2010/12/20
		recountReqAmt(ctx, ((PayRequestBillInfo)info).getContractId());
	}
	protected IObjectPK _save(Context ctx, IObjectValue model) throws BOSException, EASBizException
	{
		PayRequestBillInfo info=(PayRequestBillInfo)model;
		checkInvoice(info,ctx);
		info.setAmount(FDCHelper.multiply(info.getOriginalAmount(), info.getExchangeRate()));
		IObjectPK pk = super._save(ctx, model);
		
		//R101207-373 �������뵥�޸��ύʱ����Ҫ���¼���������뵥�ġ���ͬ�ڹ����ۼ����롱��ֵ  by zhiyuan_tang 2010/12/20
		recountReqAmt(ctx, ((PayRequestBillInfo)info).getContractId());
		
		if(pk!=null){
			
		}
		return pk;
		
	}

	private void setPropsForBill(Context ctx, PayRequestBillInfo info) {
		
		FullOrgUnitInfo orgUnit = ContextUtil.getCurrentCostUnit(ctx).castToFullOrgUnitInfo();;
		info.setOrgUnit(orgUnit);
		CtrlUnitInfo currentCtrlUnit = ContextUtil.getCurrentCtrlUnit(ctx);
		info.setCU(currentCtrlUnit);
	}

	protected void _addnew(Context ctx, IObjectPK pk, IObjectValue model) throws BOSException, EASBizException {
//		setPropsForBill(ctx, (PayRequestBillInfo)model);
//		checkBill(ctx, model);
		super._addnew(ctx, pk, model);
		//�ۿ��뽱��Ĭ������,����Ĭ�ϲ�����
		Object flag=ctx.get("fromwebservice");
		if(flag==null){
			addDeductBill((PayRequestBillInfo)model, ctx);
			addGuerdonBill((PayRequestBillInfo)model, ctx);
		}
	}

	protected IObjectPK _addnew(Context ctx, IObjectValue model) throws BOSException, EASBizException {
//		setPropsForBill(ctx, (PayRequestBillInfo)model);
//		checkBill(ctx, model);
		//��¼info����Դ
		PayRequestBillInfo info  =(PayRequestBillInfo)model;
		if(info.getSource()==null && info.getContractId()!=null){
			info.setSource(BOSUuid.read(info.getContractId()).getType().toString());
		}
		if(info.getContractId()!=null){
			EntityViewInfo view=new EntityViewInfo();
			view.setFilter(new FilterInfo());
			//EntityViewInfo���ܻᵼ��info.getContractId()�Զ�����NVarchar��ת������»�ȡ�������ݣ�����toString()ȷ��������ȷ��
			view.getFilter().appendFilterItem("contractId", info.getContractId().toString());
			view.getSelector().add("id");
			ContractBaseDataCollection c=ContractBaseDataFactory.getLocalInstance(ctx).getContractBaseDataCollection(view);
			if(c!=null&&c.size()==1){
				info.setContractBase(c.get(0));
			}
		}
		IObjectPK objectPK = super._addnew(ctx, model);
		
		if(objectPK!=null){
			/*****
			 * ���¹����ļ׹�����ȷ�ϵ����ۼ�������
			 */
			if(info!=null&&info.getConfirmEntry()!=null){
				FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
				builder.setPrepareStatementSql("update T_PAM_MaterialConfirmBill set FToDateReqAmt=(select sum(FReqAmount) from T_CON_PayReqConfirmEntry where FConfirmBillID=?) where FID=? ");
				builder.setBatchType(FDCSQLBuilder.PREPARESTATEMENT_TYPE);
				boolean hasSQL = false;
				for(int i=0;i<info.getConfirmEntry().size();i++){
					if(info.getConfirmEntry().get(i).getConfirmBill()!=null){
						builder.addParam(info.getConfirmEntry().get(i).getConfirmBill().getId().toString());
						builder.addParam(info.getConfirmEntry().get(i).getConfirmBill().getId().toString());
						builder.addBatch();
						hasSQL = true;
					}
				}
				if(hasSQL){
					builder.executeBatch();
				}
				builder = null;
			}
		}
		
		Object flag=ctx.get("fromwebservice");
		if(flag==null){
			addDeductBill((PayRequestBillInfo)model, ctx);
			addGuerdonBill((PayRequestBillInfo)model, ctx);
		}
		
		return objectPK;
	}
	 public void deleteOA(Context ctx,PayRequestBillInfo info) throws EASBizException{
		 if(info.getSourceFunction()!=null){
	    		throw new EASBizException(new NumericExceptionSubItem("100","�Ѵ���OA���̣���ֹɾ����"));
	    	}
//	    	try {
//				FDCSQLBuilder builder=new FDCSQLBuilder(ctx);
//				builder.appendSql("select furl from t_oa");
//				IRowSet rs=builder.executeQuery();
//				String url=null;
//				while(rs.next()){
//					url=rs.getString("furl");
//				}
//				if(url!=null){
//					Service s=new Service();
//					Call call=(Call)s.createCall();
//					
//					call.setTargetEndpointAddress(new java.net.URL(url));
//		            call.addParameter("arg0", org.apache.axis.encoding.XMLType.XSD_STRING, javax.xml.rpc.ParameterMode.IN);
//		            call.setReturnType(org.apache.axis.encoding.XMLType.XSD_STRING);
//		            call.setUseSOAPAction(true);
//					call.setTimeout(Integer.valueOf(1000*600000*60));
//			        call.setMaintainSession(true);
//					call.setOperationName("deleteEkpFlow");
//			        String result=(String)call.invoke(new Object[]{info.getSourceFunction()} );
//			        JSONObject rso = JSONObject.fromObject(result);
//			        if(!rso.getString("code").equals("1")){
//			        	throw new EASBizException(new NumericExceptionSubItem("100",rso.getString("massage")));
//			        }
//				}
//			} catch (SQLException e) {
//				e.printStackTrace();
//			} catch (Exception e) {
//				throw new EASBizException(new NumericExceptionSubItem("100",e.getMessage()));
//			}
	    }
	    public void sendtoOA(Context ctx,PayRequestBillInfo info) throws EASBizException{
	    	try {
	    		UserInfo u=UserFactory.getLocalInstance(ctx).getUserByID(ctx.getCaller());
	    		if(u.getPerson()==null)return;
	    		if(isBillInWorkflow(ctx,info.getId().toString()))return;
				FDCSQLBuilder builder=new FDCSQLBuilder(ctx);
				builder.appendSql("select furl from t_oa");
				IRowSet rs=builder.executeQuery();
				String url=null;
				while(rs.next()){
					url=rs.getString("furl");
				}
				if(url!=null){
					info=this.getPayRequestBillInfo(ctx, new ObjectUuidPK(info.getId()));
					
					CurProjectInfo curProject=CurProjectFactory.getLocalInstance(ctx).getCurProjectInfo(new ObjectUuidPK(info.getCurProject().getId()));
					if(!curProject.isIsOA()) return;
					Service s=new Service();
					Call call=(Call)s.createCall();
					
					call.setTargetEndpointAddress(new java.net.URL(url+"kmReviewWebserviceService?wsdl"));
		            call.addParameter("arg0", org.apache.axis.encoding.XMLType.XSD_STRING, javax.xml.rpc.ParameterMode.IN);
		            call.setReturnType(org.apache.axis.encoding.XMLType.XSD_STRING);
		            call.setUseSOAPAction(true);
					call.setTimeout(Integer.valueOf(1000*600000*60));
				        
					JSONObject json=new JSONObject();
					
					if(info.getSourceFunction()!=null){
						call.setOperationName("updateEkpReview");
						json.put("id", info.getSourceFunction());
						json.put("flowParam", info.getOaOpinion());
					}else{
						call.setOperationName("addEkpReview");
						json.put("id", info.getId().toString());
					}
//					builder.clear();
//    				builder.appendSql("select TID from oatemplate where TYPE='payRequest'");
//    				IRowSet tidrs = builder.executeQuery();
//    				String tmplateId=null;
//    				while(tidrs.next()){
//    					tmplateId=tidrs.getString("TID");
//    				}
//    				json.put("tmplateId", tmplateId);
					
					PayContentTypeInfo pc=PayContentTypeFactory.getLocalInstance(ctx).getPayContentTypeInfo(new ObjectUuidPK(info.getPayContentType().getId()));
					if(pc.getPayOaTId()==null){
						throw new EASBizException(new NumericExceptionSubItem("100","��������δά����ӦOAģ��ID��"));
					}else{
						json.put("tmplateId", pc.getPayOaTId());
					}
					json.put("fdType", "02");
					json.put("docSubject", info.getContractName()+"��������");
					
					json.put("loginName", u.getNumber());
					JSONObject obj = new JSONObject();
					
					obj.put("fd_38c8d015c3c744", pc.getName());
					obj.put("fd_38c8ce5e59839c", info.getAmount().doubleValue());
					if(info.isIsJT()){
						obj.put("fd_38f672cd7463f2", "��");
					}else{
						obj.put("fd_38f672cd7463f2", "��");
					}
					ContractBillInfo con=ContractBillFactory.getLocalInstance(ctx).getContractBillInfo(new ObjectUuidPK(info.getContractId()));
					if(con.getOrgType().equals(ContractTypeOrgTypeEnum.NEIBU)){
						obj.put("fd_395ad2dad08684", "��");
					}else{
						obj.put("fd_395ad2dad08684", "��");
					}
					String et="";
					for(int i=0;i<info.getBgEntry().size();i++){
						ExpenseTypeInfo etInfo=ExpenseTypeFactory.getLocalInstance(ctx).getExpenseTypeInfo(new ObjectUuidPK(info.getBgEntry().get(i).getExpenseType().getId()));
						if(i==info.getBgEntry().size()-1){
							et=et+etInfo.getName();
						}else{
							et=et+etInfo.getName()+",";
						}
					}
					obj.put("fd_395ad26fab0362", et);
					
//					webҳ��鿴·��
					builder.clear();
					builder.appendSql("select url from weburl where type='view'");
					IRowSet rsv = builder.executeQuery();
					String sendUrl=null;
					while(rsv.next()){
//						http://172.17.4.125:8082/easWeb/#/
						StringBuffer sbv = new StringBuffer();
						String appendUrl = rsv.getString("url");
//						������ approval
						String appendType="payment/view?from=oa&id=";
//						id
						String idv = info.getId().toString();
						String appendId = URLEncoder.encode(idv, "utf-8");
//						token
						String token = "&token=";
						sendUrl = String.valueOf(sbv.append(appendUrl).append(appendType).append(appendId).append(token));
						System.out.println(sendUrl);
					}
					json.put("fdPcViewLink", sendUrl);

//					app�鿴��ַ
					String sendAppUrl = null;
					StringBuffer sba = new StringBuffer();
					builder.clear();
					builder.appendSql("select url from weburl where type ='app'");
					IRowSet rsapp = builder.executeQuery();
					while(rsapp.next()){
//						http://172.17.4.125:8082/easWeb/#/
						String appendUrl = rsapp.getString("url");
//						������ approval
						String appendType="payment/?from=oa&id=";
//						id
						String idv = info.getId().toString();
						String appendId = URLEncoder.encode(idv, "utf-8");
//						token
						String token = "&token=";
						sendAppUrl = String.valueOf(sba.append(appendUrl).append(appendType).append(appendId).append(token));
						System.out.println(sendUrl);
					}
					json.put("fdMobileViewLink", sendAppUrl);
					
					//��Ӧ��ͬ��oa����id
					String contractId = info.getContractId();
					ContractBillInfo ctbInfo=ContractBillFactory.getLocalInstance(ctx).getContractBillInfo(new ObjectUuidPK(contractId));
					if(ctbInfo!=null){
						String oaLC = ctbInfo.getSourceFunction();
						if(oaLC!=null||oaLC!=""){
							json.put("fdMobileEditLink", oaLC);
						}
					}
					
					json.put("data",obj.toString());
					
					json.put("fd_application", info.getOaPosition().split(":")[0]);
					
					JSONArray attFile = new JSONArray();
					
					SelectorItemCollection attsic = new SelectorItemCollection();
					attsic.add(new SelectorItemInfo("id"));
					attsic.add(new SelectorItemInfo("attachment.name"));
					attsic.add(new SelectorItemInfo("attachment.remotePath"));
					attsic.add(new SelectorItemInfo("attachment.storageType"));
					attsic.add(new SelectorItemInfo("attachment.simpleName"));
					attsic.add(new SelectorItemInfo("boID"));
					FilterInfo filter = new FilterInfo();
					filter.getFilterItems().add(new FilterItemInfo("boID", info.getId().toString()));
					EntityViewInfo evi = new EntityViewInfo();
					evi.setFilter(filter);
					evi.setSelector(attsic);
					BoAttchAssoCollection cols= BoAttchAssoFactory.getLocalInstance(ctx).getBoAttchAssoCollection(evi);
					
		            for (int i=0;i<cols.size();i++) {
		                JSONObject attObj = new JSONObject();
		                AttachmentInfo att=cols.get(i).getAttachment();
		                if(att.getStorageType().equals(AttachmentStorageTypeEnum.FTP)){
		                	String simpleName=att.getSimpleName();
		                	if(att.getName()!=null){
		                		if(att.getName().indexOf("."+simpleName)<0){
			                		attObj.put("filename", att.getName()+"."+att.getSimpleName());
			                	}else{
			                		attObj.put("filename", att.getName());
			                	}
		                	}else{
		                		attObj.put("filename", "."+att.getSimpleName());
		                	}
			                attObj.put("filepath", att.getRemotePath());
			                attObj.put("fileType", "02");
			                attFile.add(attObj);
		                }
		            }
		            json.put("attFile", attFile);
		            
			        String result=(String)call.invoke(new Object[]{json.toString()} );
			        JSONObject rso = JSONObject.fromObject(result);
			        if(!rso.getString("code").equals("1")){
			        	throw new EASBizException(new NumericExceptionSubItem("100",rso.getString("massage")));
			        }else{
			        	info.setSourceFunction(rso.getString("oaid"));
			        	info.setState(FDCBillStateEnum.AUDITTING);
			        	SelectorItemCollection sic=new SelectorItemCollection();
						sic.add("sourceFunction");
						sic.add("state");
						PayRequestBillFactory.getLocalInstance(ctx).updatePartial(info,sic);
						
						Object atturl=rso.get("atturl");
    					if(atturl!=null){
    						SelectorItemCollection attupdatesic = new SelectorItemCollection();
    						attupdatesic.add(new SelectorItemInfo("beizhu"));
    						
    						JSONArray atturlarry = rso.getJSONArray("atturl");
    						for (int i=0;i<cols.size();i++) {
    							AttachmentInfo att=cols.get(i).getAttachment();
    							for(int j=0;j<atturlarry.size();j++){
    								String simpleName=att.getSimpleName();
    								String fileName=null;
    								if(att.getName()!=null){
    									if(att.getName().indexOf("."+simpleName)<0){
    										fileName=att.getName()+"."+att.getSimpleName();
    				                	}else{
    				                		fileName=att.getName();
    				                	}
    								}else{
    									fileName="."+att.getSimpleName();
    								}
    								if(fileName.equals(atturlarry.getJSONObject(j).getString("name"))){
    									att.setBeizhu(atturlarry.getJSONObject(j).getString("url"));
    									AttachmentFactory.getLocalInstance(ctx).updatePartial(att, attupdatesic);
    				                }
    							}
    			            }
    					}
			        }
				}
			} catch (SQLException e) {
				e.printStackTrace();
			} catch (Exception e) {
				throw new EASBizException(new NumericExceptionSubItem("100",e.getMessage()));
			}
	    }
	protected IObjectPK _submit(Context ctx, IObjectValue model) throws BOSException, EASBizException {
		//�ύǰ���
		PayRequestBillInfo info=(PayRequestBillInfo)model;
		checkInvoice(info,ctx);
		boolean hasUsed=FDCUtils.getDefaultFDCParamByKey(ctx, null, FDCConstants.FDC_PARAM_ACCTBUDGET);
		checkBillForSubmit( ctx,info);
		info.setAmount(FDCHelper.multiply(info.getOriginalAmount(), info.getExchangeRate()));
		
		final IObjectPK pk = super._submit(ctx, model);
		
		//R101207-373 �������뵥�޸��ύʱ����Ҫ���¼���������뵥�ġ���ͬ�ڹ����ۼ����롱��ֵ  by zhiyuan_tang 2010/12/20
		recountReqAmt(ctx, ((PayRequestBillInfo)model).getContractId());
		
		if(hasUsed){
			PayRequestSplitFactory.getLocalInstance(ctx).autoSplit(pk.toString());
		}
		if(model.get("fromweb")==null){
    		sendtoOA(ctx,info);
    	}
		return pk;
	}
	protected void _submit(Context ctx, IObjectPK pk, IObjectValue model) throws BOSException, EASBizException {
		PayRequestBillInfo info=(PayRequestBillInfo)model;
		checkInvoice(info,ctx);
		boolean hasUsed=FDCUtils.getDefaultFDCParamByKey(ctx, null, FDCConstants.FDC_PARAM_ACCTBUDGET);
		checkBillForSubmit( ctx,model);
		info.setAmount(FDCHelper.multiply(info.getOriginalAmount(), info.getExchangeRate()));
		
		super._submit(ctx, pk, model);
		
		//R101207-373 �������뵥�޸��ύʱ����Ҫ���¼���������뵥�ġ���ͬ�ڹ����ۼ����롱��ֵ  by zhiyuan_tang 2010/12/20
		recountReqAmt(ctx, ((PayRequestBillInfo)model).getContractId());
		
		if(hasUsed){
			PayRequestSplitFactory.getLocalInstance(ctx).autoSplit(pk.toString());
		}
		if(model.get("fromweb")==null){
    		sendtoOA(ctx,info);
    	}
	}
	public boolean isBillInWorkflow(Context ctx,String id) throws BOSException{
		ProcessInstInfo instInfo = null;
		ProcessInstInfo procInsts[] = null;

		IEnactmentService service2 = EnactmentServiceFactory.createEnactService(ctx);
		procInsts = service2.getProcessInstanceByHoldedObjectId(id);
		int i = 0;
		for(int n = procInsts.length; i < n; i++){
			if("open.running".equals(procInsts[i].getState()) || "open.not_running.suspended".equals(procInsts[i].getState())){
				instInfo = procInsts[i];
			}
		}
		if(instInfo != null){
			return true;
		}else{
			return false;
		}
    }
	protected void _audit(Context ctx, List idList) throws BOSException, EASBizException {
		if(idList==null) return;
		for (Iterator iter = idList.iterator(); iter.hasNext();) {
			String id = (String) iter.next();			
			audit(ctx, BOSUuid.read(id));
		}	
	}

	/**
	 * 
	 * ������������
	 * @author:jelon
	 * @see com.kingdee.eas.fdc.contract.app.AbstractContractBillControllerBean#_unAudit(com.kingdee.bos.Context, java.util.List)
	 */
	protected void _unAudit(Context ctx, List idList) throws BOSException, EASBizException {
		if(idList==null) return;
		for (Iterator iter = idList.iterator(); iter.hasNext();) {
			String id = (String) iter.next();
			unAudit(ctx, BOSUuid.read(id));
		}	
	}

	protected void _audit(Context ctx, BOSUuid billId) throws BOSException, EASBizException
	{
		
		checkBillForAudit( ctx,billId,null);
		
		PayRequestBillInfo billInfo = new PayRequestBillInfo();
		billInfo.setId(billId);
		billInfo.setAuditor(ContextUtil.getCurrentUserInfo(ctx));
		// �������
		billInfo.setAuditTime(new Date());
		// ״̬
		billInfo.setState(FDCBillStateEnum.AUDITTED);
		
		SelectorItemCollection selector = new SelectorItemCollection();
		selector.add("auditor");
		selector.add("auditTime");
		selector.add("state");
		
		_updatePartial(ctx, billInfo, selector);
		//����ۿ���,��Ϊ��������ѡ���ʱ��ʹ���
		selector = new SelectorItemCollection();
		selector.add("contractId");
		selector.add("*");
		selector.add("curProject.id");
		//ȡ��������Ŀ�Ĳ�����֯�����ɸ��ʱ�á� by sxhong 2009-06-04 18:28:23
		selector.add("curProject.fullOrgUnit.id");
		selector.add("orgUnit.id");
		selector.add("realSupplier.number");
		selector.add("realSupplier.name");
		selector.add("supplier.number");
		selector.add("supplier.name");
		selector.add("CU.name");
		selector.add("CU.number");
		//�ڼ�
		selector.add("period.number");
		selector.add("period.beginDate");
		selector.add("period.endDate");
		selector.add("isBgControl");
		selector.add("bgEntry.*");
		selector.add("bgEntry.accountView.*");
		selector.add("btEntry.expenseType.*");
		selector.add("paymentType.*");
		selector.add("supplier.*");
		selector.add("payBillType.*");
		selector.add("person.*");
		selector.add("costedDept.*");
		PayRequestBillInfo payRequestBillInfo = PayRequestBillFactory.getLocalInstance(ctx)
			.getPayRequestBillInfo(new ObjectUuidPK(billId),selector);
		//payRequestBillInfo.getRealSupplier().getName();
		
		//��ֹ�����ۼ�ʵ���ڸ������뵥����������ύ��ʱ��ȡ���Ǻ�ͬ�ϵ�prjPriceInConPaid������ֻ�б�������ύ��ʱ��ŻὫ��ͬ�ϵ�prjPriceInConPaid
		//ͬ�����浽�������뵥�ϵ�lstPrjAllPaidAmt�ֶ���ȥ.ϵͳ��˴��������³������������:B�������뵥��A�������뵥û�и���֮ǰ���ύ�ˣ���ΪA��û�и���
		//��ͬ�ϵ� prjPriceInConPaidΪ�գ���ôB�ڱ����ύ��ʱ��ͬ����B�������뵥��ֵ��Ϊ�գ���ʱ��A���Ȼ������B���ֽ�ֹ�����ۼ�ʵ������Ϊ�ա����
		//��ʱ��B�������뵥�������ύһ�¾ͺ���.
		//Ϊ�˷�ֹ����������������ǿ�����������ʱ�������·�ʽ��ʽ����һ��                  by cassiel_peng 2010-1-5
		
		FDCSQLBuilder _builder=new FDCSQLBuilder(ctx);
		_builder.appendSql("select fprjPriceInConPaid as amount from T_CON_ContractBill where fid=?");
		_builder.addParam(payRequestBillInfo.getContractId());
		IRowSet rowSet = _builder.executeQuery();
		if(rowSet!=null&&rowSet.size()==1){
			try {
				rowSet.next();
				billInfo.setLstPrjAllPaidAmt(rowSet.getBigDecimal("amount"));
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		SelectorItemCollection _selector = new  SelectorItemCollection();
		_selector.add("lstPrjAllPaidAmt");
		_updatePartial(ctx, billInfo, _selector);
		
/*
		String contractID=payRequestBillInfo.getContractId();
		if(contractID!=null&&BOSUuid.read(contractID).getType().equals(contractType)){
			EntityViewInfo view=new EntityViewInfo();
			FilterInfo filter=new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("parent.payRequestBill.id",billId.toString()));
			view.setFilter(filter);
			DeductOfPayReqBillEntryCollection c = DeductOfPayReqBillEntryFactory.getLocalInstance(ctx).getDeductOfPayReqBillEntryCollection(view);
			selector=new SelectorItemCollection();
			selector.add("hasApplied");
			DeductBillEntryInfo dinfo=new DeductBillEntryInfo();
			for (Iterator iterator = c.iterator(); iterator.hasNext();)
			{
				DeductOfPayReqBillEntryInfo info = (DeductOfPayReqBillEntryInfo) iterator.next();
				dinfo.setId(info.getDeductBillEntry().getId());
				dinfo.setHasApplied(true);
				DeductBillEntryFactory.getLocalInstance(ctx).updatePartial(dinfo, selector);
								
			}
		}*/

		//�жϲ����Ƿ�����
		HashMap param = FDCUtils.getDefaultFDCParam(ctx,payRequestBillInfo.getOrgUnit().getId().toString());
		if(param.get(FDCConstants.FDC_PARAM_CONPAYPLAN)!=null){
			boolean hasConPlan = Boolean.valueOf(param.get(FDCConstants.FDC_PARAM_CONPAYPLAN).toString()).booleanValue();
			// ���ı�û�и���ƻ�
			if(hasConPlan&&FDCUtils.isContractBill(ctx, payRequestBillInfo.getContractId())){
				if (payRequestBillInfo.getCurPlannedPayment()==null || 
						FDCConstants.ZERO.compareTo(payRequestBillInfo.getCurPlannedPayment())==0) {
//					throw new PayRequestBillException(PayRequestBillException.MUSTCONPAYPLAN);
				}
	//			String contractId = payRequestBillInfo.getContractId();
	//			FilterInfo filter = new FilterInfo();
	//			filter.getFilterItems().add(new FilterItemInfo("contractId", contractId));
	//			if (!ContractPayPlanFactory.getLocalInstance(ctx).exists(filter)) {
	//				throw new PayRequestBillException(PayRequestBillException.MUSTCONPAYPLAN);
	//			}
			}
		}
/*		
		//�ۼ�Ԥ��
		boolean useWorkflow = FDCUtils.isRunningWorkflow(ctx, payRequestBillInfo.getId().toString());
        boolean isMbgCtrl = Boolean.valueOf(param.get(FDCConstants.FDC_PARAM_STARTMG).toString()).booleanValue();
        if (!useWorkflow && isMbgCtrl) {
            IBgControlFacade bgControlFacade = BgControlFacadeFactory.getLocalInstance(ctx);
            IBgBudgetFacade iBgBudget = BgBudgetFacadeFactory.getLocalInstance(ctx);

            boolean isPass = false;
            isPass = iBgBudget.getAllowAccessNoWF(FDCConstants.PayRequestBill);
            if (isPass) {
                // 5.1.1��ʱ����
                bgControlFacade.bgAuditAllowAccess(payRequestBillInfo.getId(), FDCConstants.PayRequestBill, BgCtrlPayRequestBillHandler.BGHANDLER);
            } else {
                isPass = bgControlFacade.bgAudit(payRequestBillInfo.getId().toString(),  FDCConstants.PayRequestBill, BgCtrlPayRequestBillHandler.BGHANDLER);
            }
            // ����isPass�ж��Ƿ����쳣
            if (!isPass) {
                throw new PayRequestBillException(PayRequestBillException.BEFOREBGBAL);
            }
        }*/
		
		
		//��ԷҪ�� ���ݵ�ǰʱ�����ö�Ӧ�ڼ�
		String companyID = payRequestBillInfo.getCurProject().getFullOrgUnit().getId().toString();
		Map paramMap = FDCUtils.getDefaultFDCParam(ctx, companyID);
//		boolean isResetPeriod = FDCUtils.getParamValue(paramMap, FDCConstants.FDC_PARAM_RESETPERIOD);
		boolean isInCore = FDCUtils.getParamValue(paramMap, FDCConstants.FDC_PARAM_INCORPORATION);
		if(isInCore){//ȥ�������������½�ͳһ�������߼�����
			String prjId = payRequestBillInfo.getCurProject().getId().toString();
			// �����ڼ�
			PeriodInfo finPeriod = FDCUtils.getCurrentPeriod(ctx, prjId, false);
			PeriodInfo billPeriod = payRequestBillInfo.getPeriod();
			PeriodInfo shouldPeriod = null;//���������ڼ�
			Date bookedDate = DateTimeUtils.truncateDate(payRequestBillInfo.getBookedDate());
			
			if(finPeriod==null){
				throw new EASBizException(new NumericExceptionSubItem("100","��������Ӧ����֯û�е�ǰʱ����ڼ䣬�������ã�"));
			}
			/***************
			 * ��1�����������뵥�ϵġ�ҵ�����ڡ��͡�ҵ���ڼ䡱���ڡ��������ڡ���������Ŀ�ɱ����񡰵�ǰ�ڼ䡱��ʱ������ġ������ڼ䡱�͡��������ڡ�ȡ�������뵥�ġ�ҵ���ڼ䡱�͡�ҵ�����ڡ�
			 * ��2�����������뵥�ϵġ�ҵ�����ڡ��͡�ҵ���ڼ䡱С�ڵ��ڡ��������ڡ���������Ŀ�ɱ����񡰵�ǰ�ڼ䡱��ʱ������ġ������ڼ䡱�͡��������ڡ�ȡ��������Ŀ�ɱ����񡰵�ǰ�ڼ䡱�����������ڣ��ҽ��������ڷ�д�ظ������뵥�ϵġ�ҵ�����ڡ��͡�ҵ���ڼ䡱��
			 *	
			 *	ԭ�����ֱ���ʱ��ͬ���ڼ��ϳ�����
			 */
			if(billPeriod!=null&&billPeriod.getNumber()>finPeriod.getNumber()){
				if (bookedDate.before(billPeriod.getBeginDate())) {
					bookedDate = billPeriod.getBeginDate();
				} else if (bookedDate.after(billPeriod.getEndDate())) {
					bookedDate = billPeriod.getEndDate();
				}
				shouldPeriod = billPeriod;
			}else if(finPeriod!=null){
				if (bookedDate.before(finPeriod.getBeginDate())) {
					bookedDate = finPeriod.getBeginDate();
				} else if (bookedDate.after(finPeriod.getEndDate())) {
					bookedDate = finPeriod.getEndDate();
				}
				shouldPeriod = finPeriod;
			}
			FDCSQLBuilder builder=new FDCSQLBuilder(ctx);
			builder.appendSql("update T_CON_PayRequestBill set FPeriodId = ?,FBookedDate = ? where fId=? ");
			builder.addParam(shouldPeriod.getId().toString());
			builder.addParam(bookedDate);
			builder.addParam(billId.toString());
			builder.execute();
			//�����ڼ������
			payRequestBillInfo.setBookedDate(bookedDate);
		}
		

		String param1="true";
		try {
			CurProjectInfo project =payRequestBillInfo.getCurProject();
			String orgUnitId=CurProjectFactory.getLocalInstance(ctx).getCurProjectInfo(new ObjectUuidPK(project.getId())).getFullOrgUnit().getId().toString();
			param1 = ParamControlFactory.getLocalInstance(ctx).getParamValue(new ObjectUuidPK(orgUnitId), "YF_CREATEPAY");
		} catch (EASBizException e) {
			e.printStackTrace();
		} catch (BOSException e) {
			e.printStackTrace();
		}
		PaymentBillInfo payBillInfo = null;
		if("true".equals(param1)){
			try {
				payBillInfo = createPayment(ctx,payRequestBillInfo);
			} catch (Exception e) {
				throw new EASBizException(new NumericExceptionSubItem("100","���ɸ��ʱ����:"+e.getMessage()),e);//new BOSException(e.getMessage());//new PayRequestBillException(PayRequestBillException.CHECKTEXTLENGTH1);
			}
		}
		if(payBillInfo != null){
			payBillId = payBillInfo.getId();
			
			try {
				updateFDCPaymentBillinvoice(ctx, payRequestBillInfo, payBillInfo);
			} catch (Exception e) {
				throw new EASBizException(new NumericExceptionSubItem("100","���·��ز�����м��ʱ����:"+e.getMessage()),e);
			}
		}
//		//�ۼ�Ԥ��
//		if(payRequestBillInfo.isIsBgControl()){
//			BgControlFacadeFactory.getLocalInstance(ctx).bgAudit(billId.toString(), "com.kingdee.eas.fdc.contract.PayRequestBill", null);
//		}
	}
	protected void _unAudit(Context ctx, BOSUuid billId) throws BOSException, EASBizException
	{
		
		checkBillForUnAudit( ctx,billId,null);
		
		
		BOSObjectType  contractType=new ContractBillInfo().getBOSType();
		SelectorItemCollection selector = new SelectorItemCollection();
		selector.add("contractId");
		selector.add("number");
		selector.add("curProject.id");
		selector.add("orgUnit.id");
		selector.add("amount");
		//��������
		selector.add("paymentType.payType.number");
		selector.add("isBgControl");

		PayRequestBillInfo payRequestBillInfo = PayRequestBillFactory.getLocalInstance(ctx).getPayRequestBillInfo(new ObjectUuidPK(billId),selector);
		
		String contractID= payRequestBillInfo.getContractId();
		
		//���ı���ͬ�ķ����������ı���ά��
		if(contractID!=null&&!BOSUuid.read(contractID).getType().equals(contractType)){
			throw new PayRequestBillException(PayRequestBillException.WITHOUTCONTRACTEXEC);
		}else{
			
			PaymentTypeInfo type = (PaymentTypeInfo) payRequestBillInfo.getPaymentType();
			if (type.getPayType().getId().toString().equals(PaymentTypeInfo.progressID)) {
				selector = new SelectorItemCollection();
				selector.add("hasSettled");
				
				ContractBillInfo contractBillInfo =ContractBillFactory.getLocalInstance(ctx).getContractBillInfo(new ObjectUuidPK(contractID),selector);
				if(contractBillInfo.isHasSettled()){
					throw new PayRequestBillException(PayRequestBillException.PROCNTUNAUDIT,new Object[]{payRequestBillInfo.getNumber()});
				}
			}
		}
		
		FilterInfo filter=new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("parent.id",billId.toString()));
		filter.getFilterItems().add(new FilterItemInfo("paymentBill.id",null,CompareType.NOTEQUALS));
		if(PayRequestBillEntryFactory.getLocalInstance(ctx).exists(filter)){
			throw new PayRequestBillException(PayRequestBillException.CANNT_UNAUDIT);
		}
		PayRequestBillInfo billInfo = new PayRequestBillInfo();
		
		billInfo.setId(billId);
		billInfo.setAuditor(null);
		billInfo.setAuditTime(null);
		billInfo.setState(FDCBillStateEnum.SUBMITTED);
		
		selector = new SelectorItemCollection();
		selector.add("auditor");
		selector.add("auditTime");
		selector.add("state");
		
		_updatePartial(ctx, billInfo, selector);	
		
		
		//����ۿ���
		if(contractID!=null&&BOSUuid.read(contractID).getType().equals(contractType)){
			EntityViewInfo view=new EntityViewInfo();
			filter=new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("parent.payRequestBill.id",billId.toString()));
			view.setFilter(filter);
			DeductOfPayReqBillEntryCollection c = DeductOfPayReqBillEntryFactory.getLocalInstance(ctx).getDeductOfPayReqBillEntryCollection(view);
			selector=new SelectorItemCollection();
			selector.add("hasApplied");
			DeductBillEntryInfo dinfo=new DeductBillEntryInfo();
			for (Iterator iterator = c.iterator(); iterator.hasNext();)
			{
				DeductOfPayReqBillEntryInfo info = (DeductOfPayReqBillEntryInfo) iterator.next();
				dinfo.setId(info.getDeductBillEntry().getId());
				dinfo.setHasApplied(false);
				DeductBillEntryFactory.getLocalInstance(ctx).updatePartial(dinfo, selector);
								
			}
		}
	
		

        // 20050511 ��־���ṩԤ��ӿ�
		HashMap param = FDCUtils.getDefaultFDCParam(ctx,payRequestBillInfo.getOrgUnit().getId().toString());
		//boolean useWorkflow = FDCUtils.isRunningWorkflow(ctx, billInfo.getId().toString());
        boolean isMbgCtrl = Boolean.valueOf(param.get(FDCConstants.FDC_PARAM_STARTMG).toString()).booleanValue();
        if (isMbgCtrl ) {
            IBgControlFacade iBgCtrl = BgControlFacadeFactory.getLocalInstance(ctx);
            iBgCtrl.cancelRequestBudget(payRequestBillInfo.getId().toString());
        }
        
        //����Ԥ��
        if(payRequestBillInfo.isIsBgControl()){
        	BgControlFacadeFactory.getLocalInstance(ctx).returnBudget(billId, "com.kingdee.eas.fdc.contract.PayRequestBill", null);
        }
	}


	protected void _setAuditing(Context ctx, BOSUuid billId) throws BOSException, EASBizException {
		PayRequestBillInfo billInfo = new PayRequestBillInfo();
		billInfo.setId(billId);
		billInfo.setState(FDCBillStateEnum.AUDITTING);
		SelectorItemCollection selector = new SelectorItemCollection();
		selector.add("state");
		_updatePartial(ctx, billInfo, selector);					
	}

	protected void _setAudited(Context ctx, BOSUuid billId) throws BOSException, EASBizException {
		_audit(ctx, billId);
/*		PayRequestBillInfo billInfo = new PayRequestBillInfo();
		billInfo.setId(billId);			
		// �����
		billInfo.setAuditor(ContextUtil.getCurrentUserInfo(ctx));
		// �������
		billInfo.setAuditTime(DateTimeUtils.truncateDate(new Date()));
		// ״̬
		billInfo.setState(FDCBillStateEnum.AUDITTED);
		
		SelectorItemCollection selector = new SelectorItemCollection();
		selector.add("auditor");
		selector.add("auditTime");
		selector.add("state");
		
		_updatePartial(ctx, billInfo, selector);	*/		
	}
	
	/**
	 * 
	 * ��������������ظ�
	 * 
	 * @param ctx
	 * @param billInfo
	 * @throws BOSException
	 * @throws EASBizException
	 * @author:liupd ����ʱ�䣺2006-8-24
	 *               <p>
	 */
	private void checkNumberDup(Context ctx, PayRequestBillInfo billInfo)
			throws BOSException, EASBizException {
		FilterInfo filter = new FilterInfo();

		filter.getFilterItems().add(
				new FilterItemInfo("number", billInfo.getNumber()));
		if (billInfo.getId() != null) {
			filter.getFilterItems().add(
					new FilterItemInfo("id", billInfo.getId().toString(),
							CompareType.NOTEQUALS));
		}
		filter.getFilterItems().add(
				new FilterItemInfo("state", FDCBillStateEnum.INVALID,CompareType.NOTEQUALS));
		filter.getFilterItems().add(
					new FilterItemInfo("orgUnit.id", billInfo.getOrgUnit()
							.getId()));
		

		if (_exists(ctx, filter)) {
			throw new ContractException(ContractException.NUMBER_DUP);
		}
	}

	protected void checkBill(Context ctx, IObjectValue model)
			throws BOSException, EASBizException {

		PayRequestBillInfo info = ((PayRequestBillInfo) model);
		checkNumberDup(ctx, info);
		checkPaymentType(ctx,info);

	}


	protected void _update(Context ctx, IObjectPK pk, IObjectValue model)
			throws BOSException, EASBizException {
		checkBill(ctx, model);
		super._update(ctx, pk, model);
	}

	
	private void addDeductBill(PayRequestBillInfo editData,Context ctx){
		if(editData==null) return;
		String contractId=editData.getContractId();
		if(contractId==null||editData.getId()==null) return;
		
		String payReqId=editData.getId().toString();
		
		HashMap map=new HashMap();
		EntityViewInfo view;
		FilterInfo filter;
		SelectorItemCollection selector;
		/*
		 * �õ���ۿ����Ͷ�Ӧ��deductOfPayReqBillInfo��map
		 */
		view=new EntityViewInfo();
		filter=new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("isEnabled",Boolean.TRUE));
		filter.getFilterItems().add(new FilterItemInfo("id", DeductTypeInfo.partAMaterialType, CompareType.NOTEQUALS));
		view.setFilter(filter);
		final SorterItemInfo sorterItemInfo = new SorterItemInfo("number");
		sorterItemInfo.setSortType(SortType.ASCEND);
		view.getSorter().add(sorterItemInfo);
		view.getSelector().add("id");
		
		try
		{
			DeductTypeInfo info = null;
			DeductTypeCollection c = DeductTypeFactory.getLocalInstance(ctx).getDeductTypeCollection(view);
			for (int i = 0; i < c.size(); i++)
			{
				info = c.get(i);
				DeductOfPayReqBillInfo deductOfPayReqBillInfo = new DeductOfPayReqBillInfo();
				deductOfPayReqBillInfo.setPayRequestBill(editData);
				deductOfPayReqBillInfo.setDeductType(info);
				deductOfPayReqBillInfo.setAmount(FDCHelper.ZERO);
				deductOfPayReqBillInfo.setOriginalAmount(FDCHelper.ZERO);
				IObjectPK pk=DeductOfPayReqBillFactory.getLocalInstance(ctx).addnew(deductOfPayReqBillInfo);
				deductOfPayReqBillInfo.setId(BOSUuid.read(pk.toString()));
				map.put(info.getId().toString(), deductOfPayReqBillInfo);
			}
		} catch (Exception e)
		{
			logger.info(e);
		}
		
		if(map.size()<=0) {
			return;
		}
		
/*		Collection values = map.values();
		
		for(Iterator iter=values.iterator();iter.hasNext();){
			try
			{
				DeductOfPayReqBillInfo info = (DeductOfPayReqBillInfo)(iter.next());
				IObjectPK pk=DeductOfPayReqBillFactory.getLocalInstance(ctx).addnew(info);
				info.setId(BOSUuid.read(pk.toString()));
			} catch (BOSException e)
			{
				logger.info(e);
			}
		}*/
	
		/*
		 * ���˳�DeductOfPayReqEntryBill���Ѻ��еĿۿ
		 */
		Set set=new HashSet();
		FilterItemCollection items;
		view = new EntityViewInfo();
		filter=new FilterInfo();
		items = filter.getFilterItems();
		items.add(new FilterItemInfo("parent.PayRequestBill.contractId",contractId,CompareType.EQUALS));
		items.add(new FilterItemInfo("parent.payRequestBill.id",payReqId,CompareType.NOTEQUALS));
		view.setFilter(filter);
		selector=new SelectorItemCollection();
		selector.add("deductBillEntry.id");
		
		try
		{//�õ�Ҫ����id���Ϸ���set��
			DeductOfPayReqBillEntryCollection c;
			c = DeductOfPayReqBillEntryFactory.getLocalInstance(ctx).getDeductOfPayReqBillEntryCollection(view);
			DeductOfPayReqBillEntryInfo info;
			for(int i=0;i<c.size();i++){
				info = c.get(i);
				set.add(info.getDeductBillEntry().getId()); //��¼��ID
			}
		} catch (BOSException e)
		{
			logger.info(e);
		}
		
		/*
		 * ��DeductBillEntryȡ���������������Ŀۿ
		 */
		view = new EntityViewInfo();
		filter=new FilterInfo();
		items = filter.getFilterItems();
		items.add(new FilterItemInfo("contractId",contractId,CompareType.EQUALS));
		items.add(new FilterItemInfo("hasApplied",Boolean.FALSE,CompareType.EQUALS));
		items.add(new FilterItemInfo("Parent.state",FDCBillStateEnum.AUDITTED_VALUE,CompareType.EQUALS));
		if(set.size()>0){
			items.add(new FilterItemInfo("id",set,CompareType.NOTINCLUDE));
		}
		view.setFilter(filter);
		try{
			DeductBillEntryInfo info;
			DeductBillEntryCollection c = DeductBillEntryFactory.getLocalInstance(ctx).getDeductBillEntryCollection(view);
			DeductOfPayReqBillInfo deductOfPayReqBillInfo;
			DeductOfPayReqBillEntryInfo entryInfo;
			for(int i=0;i<c.size();i++){
				info=c.get(i);
				Object o= map.get(info.getDeductType().getId().toString());
				if(o!=null){
					deductOfPayReqBillInfo=(DeductOfPayReqBillInfo)o;
					deductOfPayReqBillInfo.setAmount(deductOfPayReqBillInfo.getAmount().add(info.getDeductAmt()));
					deductOfPayReqBillInfo.setOriginalAmount(deductOfPayReqBillInfo.getAmount().add(info.getDeductOriginalAmt()));
					entryInfo=new DeductOfPayReqBillEntryInfo();
					entryInfo.setParent(deductOfPayReqBillInfo);
					entryInfo.setDeductBillEntry(info);
					DeductOfPayReqBillEntryFactory.getLocalInstance(ctx).addnew(entryInfo);
				}
			}
			DeductOfPayReqBillFactory.getLocalInstance(ctx).reCalcAmount(editData.getId().toString());
		}catch(Exception e){
			e.printStackTrace();
		}
		

	}
	protected void _addDeductBill(Context ctx, IObjectValue model) throws BOSException
	{
		addDeductBill((PayRequestBillInfo)model,ctx);
	}
	
	protected void _delete(Context ctx, IObjectPK pk) throws BOSException, EASBizException
	{
		if(pk==null){
			return ;
		}
		// ͬʱɾ�������Ŀۿ�б�
		_delete(ctx, new IObjectPK[]{pk});
	}
	protected IObjectPK[] _delete(Context ctx, FilterInfo filter) throws BOSException, EASBizException
	{
		//�ݲ�֧�ֵĲ���
//		throw new UnsupportedOperationException();
		throw new EASBizException(PayRequestBillException.NOTSUPPORTOPRT);
//		return super._delete(ctx, filter);
	}
	
	protected void _cancel(Context ctx, IObjectPK pk) throws BOSException, EASBizException {
		
		//ɾ�������Ŀۿ���
		deleteOfPayRequest(ctx, new IObjectPK[]{pk});
		super._cancel(ctx,pk);
	}
	
	protected void deleteOfPayRequest(Context ctx, IObjectPK[] arrayPK) throws BOSException, EASBizException
	{

		//�������뵥��ͬID
		String contractBillId = getPayRequestBillInfo(ctx, arrayPK[0]).getContractId();
		
		Set arrayPKSet=new HashSet();
		for (int i = 0; i < arrayPK.length; i++)
		{
			arrayPKSet.add(arrayPK[i].getKeyValue("id").toString());
		}
		FilterInfo filter=new FilterInfo();
		FilterItemCollection items = filter.getFilterItems();
		if(arrayPKSet.size()>0){
			items.add(new FilterItemInfo("payRequestBill.id",arrayPKSet,CompareType.INCLUDE));
		}

		//ɾ�������Ŀۿ���		
		{
			EntityViewInfo view=new EntityViewInfo();
			view.setFilter(filter);
			view.getSelector().add("entrys.deductBillEntry.id");
			
			SelectorItemCollection updateSelector=new SelectorItemCollection();
			updateSelector.add("id");
			updateSelector.add("hasApplied");
			
			DeductOfPayReqBillCollection deductOfPayReqBillCollection = DeductOfPayReqBillFactory.getLocalInstance(ctx).getDeductOfPayReqBillCollection(view);
			for (int i = 0; i < deductOfPayReqBillCollection.size(); i++) {
				DeductOfPayReqBillInfo billInfo = deductOfPayReqBillCollection.get(i);
				
				for (int j = 0; j < billInfo.getEntrys().size(); j++) {
					DeductOfPayReqBillEntryInfo billEntryInfo = billInfo.getEntrys().get(j);
					
					DeductBillEntryInfo info=new DeductBillEntryInfo();
					info.setId(billEntryInfo.getDeductBillEntry().getId());
					info.setHasApplied(false);
					DeductBillEntryFactory.getLocalInstance(ctx).updatePartial(info,updateSelector);
				}
			}
			
			DeductOfPayReqBillFactory.getLocalInstance(ctx).delete(filter);
		}
		//ɾ�������Ľ�����,�����������ۿ���ͬ
		{
			EntityViewInfo view=new EntityViewInfo();
			view.setFilter(filter);
			view.getSelector().add("guerdon.id");
			
			SelectorItemCollection updateSelector=new SelectorItemCollection();
			updateSelector.add("id");
			updateSelector.add("isGuerdoned");
			GuerdonOfPayReqBillCollection guerdonOfPayReqBillCollection = GuerdonOfPayReqBillFactory.getLocalInstance(ctx).getGuerdonOfPayReqBillCollection(view);
			for (int i = 0; i < guerdonOfPayReqBillCollection.size(); i++) {
				GuerdonOfPayReqBillInfo item = guerdonOfPayReqBillCollection.get(i);
				GuerdonBillInfo info=new GuerdonBillInfo();
				info.setId(item.getGuerdon().getId());
				info.setIsGuerdoned(false);
				GuerdonBillFactory.getLocalInstance(ctx).updatePartial(info, updateSelector);
				
			}
			GuerdonOfPayReqBillFactory.getLocalInstance(ctx).delete(filter);
		}
		{
			//ɾ��������ΥԼ��,����������뽱����ͬ
			EntityViewInfo view=new EntityViewInfo();
			view.setFilter(filter);
			view.getSelector().add("compensation.id");
			
			SelectorItemCollection updateSelector=new SelectorItemCollection();
			updateSelector.add("id");
			updateSelector.add("isCompensated");
			CompensationOfPayReqBillCollection compensationOfPayReqBillCollection = CompensationOfPayReqBillFactory.getLocalInstance(ctx).getCompensationOfPayReqBillCollection(view);
			for (int i = 0; i < compensationOfPayReqBillCollection.size(); i++) {
				CompensationOfPayReqBillInfo item = compensationOfPayReqBillCollection.get(i);
				CompensationBillInfo info=new CompensationBillInfo();
				info.setId(item.getCompensation().getId());
				info.setIsCompensated(false);
				CompensationBillFactory.getLocalInstance(ctx).updatePartial(info, updateSelector);
				
			}
			CompensationOfPayReqBillFactory.getLocalInstance(ctx).delete(filter);
		}
//		boolean param = FDCUtils.getDefaultFDCParamByKey(ctx, ContextUtil
//				.getCurrentFIUnit(ctx).getId().toString(),
//				FDCConstants.FDC_PARAM_CREATEPARTADEDUCT);
		//
//		if(param){
			//����Ϊ��ʱ��ɾ�������ļ׹��ۿ
			EntityViewInfo view=new EntityViewInfo();
			view.setFilter(filter);
			view.getSelector().add("deductBill.id");
			view.getSelector().add("deductBill.entrys.id");
			view.getSelector().add("deductBill.entrys.contractId");
			
			SelectorItemCollection updateSelector = new SelectorItemCollection();
			updateSelector.add("entrys.hasApplied");
			
			PartAOfPayReqBillCollection partAOfPayReqBillCollection = PartAOfPayReqBillFactory.getLocalInstance(ctx).getPartAOfPayReqBillCollection(view);
			for (int i = 0; i < partAOfPayReqBillCollection.size(); i++) {
				PartAOfPayReqBillInfo item = partAOfPayReqBillCollection.get(i);
				int deductEntrySize = item.getDeductBill().getEntrys().size();
				if(deductEntrySize == 0){
					continue;
				}
				for(int j=0;j<deductEntrySize;j++){
					DeductBillEntryInfo entry = item.getDeductBill().getEntrys().get(j);
					if(entry.getContractId().equals(contractBillId)){
						entry.setHasApplied(false);
						DeductBillEntryFactory.getLocalInstance(ctx).updatePartial(entry, updateSelector);
					}
				}
			}
			PartAOfPayReqBillFactory.getLocalInstance(ctx).delete(filter);
			
			//by cassiel 
			//ɾ���׹��Ŀۿ��οۿ������ϸ
			FilterInfo _filter = new FilterInfo();
			_filter.getFilterItems().add(new FilterItemInfo("payReqID",arrayPKSet,CompareType.INCLUDE));
			ShowDeductOfPartABillFactory.getLocalInstance(ctx).delete(_filter);
			
//		}else{
			//����Ϊ��ʱ��ɾ�������ļ׹�ȷ�ϵ�
			view=new EntityViewInfo();
			view.setFilter(filter);
			view.getSelector().add("materialConfirmBill.id");
			
			updateSelector = new SelectorItemCollection();
			updateSelector.add("id");
			updateSelector.add("hasApplied");
			PartAConfmOfPayReqBillCollection partAConfmOfPayReqBillCollection = PartAConfmOfPayReqBillFactory.getLocalInstance(ctx).getPartAConfmOfPayReqBillCollection(view);
			for (int i = 0; i < partAConfmOfPayReqBillCollection.size(); i++) {
				PartAConfmOfPayReqBillInfo item = partAConfmOfPayReqBillCollection.get(i);
				MaterialConfirmBillInfo info = new MaterialConfirmBillInfo();
				info.setId(item.getMaterialConfirmBill().getId());
				info.setHasApplied(false);
				MaterialConfirmBillFactory.getLocalInstance(ctx).updatePartial(info, updateSelector);
			}
			PartAConfmOfPayReqBillFactory.getLocalInstance(ctx).delete(filter);
//		}
	}
	protected void _delete(Context ctx, IObjectPK[] arrayPK) throws BOSException, EASBizException
	{
		if(arrayPK.length<1) return;
		PayRequestBillInfo model = getPayRequestBillInfo(ctx, new ObjectUuidPK(
				arrayPK[0].toString()), getSic());
		String contractId=model.getContractId();//getPayRequestBillInfo(ctx, arrayPK[0]).getContractId();
		if(contractId==null) return;
		if(BOSUuid.read(contractId).getType().equals((new ContractWithoutTextInfo()).getBOSType())){
			throw new PayRequestBillException(PayRequestBillException.WITHOUTCONTRACTEXEC);
		}

		//ɾ�������Ŀۿ���	
		deleteOfPayRequest(ctx,arrayPK);
		
		PayRequestBillInfo payReq=new PayRequestBillInfo();
		payReq.put("arrayPK",arrayPK);
		payReq.put("contractId", contractId);//ɾ��ʱֻ��ɾ��һ����ͬ�Ķ�����뵥
		/**
		 * ɾ��ʱ�����ɵĴ�ǩ����ͬ��¼ɾ����������ԭ��ǩ����ͬ״̬��ʹ������ͬ���Թ��� by hpw 20010-01-04
		 * TODO ��ֻ֧�ֵ���ɾ��,���ŵ���ͬ�����ڼ�Ŀǰ��û������Ӧ�ó���
		 */
//		FDCBudgetPeriodInfo period=FDCBudgetPeriodInfo.getPeriod(model.getPayDate(),false);
//		payReq.put("period", period);
//		FDCBudgetAcctFacadeFactory.getLocalInstance(ctx).invokeBudgetCtrl(payReq, FDCBudgetConstants.STATE_DELETE);
		
		/*****
		 * ���¹����ļ׹�����ȷ�ϵ����ۼ�������
		 */
		Set idSet = new HashSet();
		for(int i=0;i<arrayPK.length;i++){
			idSet.add(arrayPK[i].toString());
			
			deleteOA(ctx,PayRequestBillFactory.getLocalInstance(ctx).getPayRequestBillInfo(arrayPK[i]));
		}
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("parent",idSet,CompareType.INCLUDE));
		EntityViewInfo view = new EntityViewInfo();
		view.setFilter(filter);
		PayRequestBillConfirmEntryCollection confirmEns = PayRequestBillConfirmEntryFactory.getLocalInstance(ctx).getPayRequestBillConfirmEntryCollection(view);
		if(confirmEns!=null&&confirmEns.size()>0){
			FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
			builder.setPrepareStatementSql("update T_PAM_MaterialConfirmBill set FToDateReqAmt=(select sum(FReqAmount) from T_CON_PayReqConfirmEntry where FConfirmBillID=? and Fid!=?) where FID=? ");
			builder.setBatchType(FDCSQLBuilder.PREPARESTATEMENT_TYPE);
			boolean hasSQL = false;
			for(int i=0;i<confirmEns.size();i++){
				if(confirmEns.get(i).getConfirmBill()!=null){
					builder.addParam(confirmEns.get(i).getConfirmBill().getId().toString());
					builder.addParam(confirmEns.get(i).getId().toString());
					builder.addParam(confirmEns.get(i).getConfirmBill().getId().toString());
					builder.addBatch();
					hasSQL = true;
				}
			}
			if(hasSQL){
				builder.executeBatch();
			}
			builder = null;
		}
		
		super._delete(ctx, arrayPK);
		
		
		
		//R101207-373 �ദ��Ҫ���ּ���������뵥�ġ���ͬ�ڹ����ۼ����롱��ֵ , ��ȡ��һ������ by zhiyuan_tang 2010/12/20
		recountReqAmt(ctx, contractId);

/*		//���ı���ɾ��ͳһ�����ı��ĵط�ά��
//		ɾ�����������ı���ͬ		
		if(contractId!=null){
			String sql = "delete from T_CON_ContractWithoutText where fid = ? ";
			Object []params = new Object[] {contractId};
			 DbUtil.execute(ctx, sql, params);
		}*/
		// ���½��ȹ���id
		String companyId = null;
		if (model.getCurProject().getFullOrgUnit() == null) {
			SelectorItemCollection sic = new SelectorItemCollection();
			sic.add("fullOrgUnit.id");
			CurProjectInfo curProject = CurProjectFactory.getLocalInstance(ctx)
					.getCurProjectInfo(
							new ObjectUuidPK(model.getCurProject().getId()
									.toString()), sic);

			companyId = curProject.getFullOrgUnit().getId().toString();
		} else {
			companyId = model.getCurProject().getFullOrgUnit().getId()
					.toString();
		}
		Map paramMap = FDCUtils.getDefaultFDCParam(ctx, companyId);
		boolean isFromFillBill = FDCUtils.getParamValue(paramMap,
				FDCConstants.FDC_PARAM_PROJECTFILLBILL);
		boolean isLoadStrict = FDCUtils.getParamValue(paramMap,
				FDCConstants.FDCSCH_PARAM_ISFILLBILLCONTROLSTRICT);
		FDCSCHUtils.updateTaskRef(ctx,isFromFillBill,isLoadStrict,true,contractId,arrayPK,null);
		
	}
	
	/**
	 * �����������ͬ��Ӧ�����и������뵥���ۼ������ۼ����������ۼ����룫���ڷ����
	 *      �ദ��Ҫ���ּ���������뵥�ġ���ͬ�ڹ����ۼ����롱��ֵ , ������ȡ��һ������
	 * @param ctx
	 * @param contractId
	 * @throws BOSException
	 * @throws EASBizException
	 * @author zhiyuan_tang 2010/12/20
	 */
	private void recountReqAmt(Context ctx, String contractId) throws BOSException, EASBizException {
		FilterInfo filter;
		EntityViewInfo view;
		if(contractId==null) return;
		view=new EntityViewInfo();
		filter=new FilterInfo();
		view.setFilter(filter);
		filter.getFilterItems().add(new FilterItemInfo("contractId",contractId));
		SorterItemInfo sorterItemInfo = new SorterItemInfo("createTime");
		sorterItemInfo.setSortType(SortType.ASCEND);
		view.getSorter().add(sorterItemInfo);
		view.getSelector().add("number");
		view.getSelector().add("createTime");
		view.getSelector().add("prjAllReqAmt");
		view.getSelector().add("addPrjAllReqAmt");
		view.getSelector().add("payPartAMatlAllReqAmt");
		
		view.getSelector().add("projectPriceInContract");
		view.getSelector().add("addProjectAmt");
		view.getSelector().add("payPartAMatlAmt");
		PayRequestBillCollection c = getPayRequestBillCollection(ctx, view);
		PayRequestBillInfo info=null;
		
		for(int i=0;i<c.size();i++){
			info=c.get(i);
			if(i==0){
				//����0
				info.setPrjAllReqAmt(info.getProjectPriceInContract());
				info.setAddPrjAllReqAmt(info.getAddProjectAmt());
				info.setPayPartAMatlAllReqAmt(info.getPayPartAMatlAmt());
			}else{
				//���������ۼƣ����ڷ���
				if(c.get(i-1).getPrjAllReqAmt() instanceof BigDecimal &&info.getProjectPriceInContract() instanceof BigDecimal){
					info.setPrjAllReqAmt(c.get(i-1).getPrjAllReqAmt().add(info.getProjectPriceInContract()));
				}
				if (c.get(i-1).getAddPrjAllReqAmt() instanceof BigDecimal && info.getAddProjectAmt() instanceof BigDecimal)
				{
					info.setAddPrjAllReqAmt(c.get(i-1).getAddPrjAllReqAmt().add(info.getAddProjectAmt()));
				}
				if(c.get(i-1).getPayPartAMatlAllReqAmt() instanceof BigDecimal &&info.getPayPartAMatlAmt() instanceof BigDecimal){
					info.setPayPartAMatlAllReqAmt(c.get(i-1).getPayPartAMatlAllReqAmt().add(info.getPayPartAMatlAmt()));
				}
			}
			_updatePartial(ctx, info, view.getSelector());
		}
	}
	
	protected IObjectPK[] _delete(Context ctx, String oql) throws BOSException, EASBizException
	{
		//�ݲ�֧�ֵĲ���
		throw new EASBizException(PayRequestBillException.NOTSUPPORTOPRT);
//		return super._delete(ctx, oql);
	}

	private void addGuerdonBill(PayRequestBillInfo billInfo,Context ctx) throws BOSException,EASBizException{
		SelectorItemCollection selector=new SelectorItemCollection();
		selector.add("id");
		selector.add("isGuerdoned");
		EntityViewInfo view=new EntityViewInfo();
		FilterInfo filter=new FilterInfo();
		filter.appendFilterItem("contract.id", billInfo.getContractId());
		filter.getFilterItems().add(new FilterItemInfo("state",FDCBillStateEnum.AUDITTED_VALUE,CompareType.EQUALS));
		filter.getFilterItems().add(new FilterItemInfo("isGuerdoned",String.valueOf(1),CompareType.NOTEQUALS));
/*		//�����뵥��ͬ���·�֮ǰ��
		Timestamp createTime = billInfo.getCreateTime();
		Calendar cal=Calendar.getInstance();
		cal.setTime(createTime);
		createTime.setDate(cal.getActualMaximum(Calendar.DATE));
		createTime.setHours(cal.getActualMaximum(Calendar.HOUR_OF_DAY)); //24Сʱ��
		createTime.setMinutes(cal.getActualMaximum(Calendar.MINUTE));
		createTime.setSeconds(cal.getActualMaximum(Calendar.SECOND));
		filter.getFilterItems().add(new FilterItemInfo("createTime",createTime,CompareType.LESS_EQUALS));
*/		
		view.setFilter(filter);
		view.getSelector().add("id");
		view.getSelector().add("amount");
		view.getSelector().add("originalAmount");
		view.getSelector().add("isGuerdoned");
		GuerdonBillCollection guerdonBillCollection = GuerdonBillFactory.getLocalInstance(ctx).getGuerdonBillCollection(view);
		GuerdonBillInfo info=null;
		GuerdonOfPayReqBillInfo gpInfo=null;
		CoreBaseCollection cbcollection=new CoreBaseCollection();
		for(int i=0;i<guerdonBillCollection.size();i++){
			info=guerdonBillCollection.get(i);
			gpInfo=new GuerdonOfPayReqBillInfo();
			gpInfo.setAmount(info.getAmount());
			gpInfo.setOriginalAmount(info.getOriginalAmount());
			gpInfo.setPayRequestBill(billInfo);
			gpInfo.setGuerdon(info);
			cbcollection.add(gpInfo);
			
			info.setIsGuerdoned(true);
			GuerdonBillFactory.getLocalInstance(ctx).updatePartial(info, selector);
		}
		GuerdonOfPayReqBillFactory.getLocalInstance(ctx).addnew(cbcollection);
	}
	
	//���ɸ��
	private PaymentBillInfo createPayment(Context ctx,PayRequestBillInfo payReqBill)throws Exception{
		//���������뵥���ɸ����Ĭ�ϻ�ر�
		//��������ɵĸ��ɾ���� ��Ҫ�ٴ����ɸ�� ����Ҫ���رմ˸������뵥
		if(payReqBill.isHasClosed()){
			return null;
		}
		BOSObjectType bosType = new PaymentBillInfo().getBOSType();

		IBTPManager iBTPManager = BTPManagerFactory.getLocalInstance(ctx);
		BTPTransformResult result = iBTPManager.transform(payReqBill, bosType.toString());

		IObjectCollection destBillColl = result.getBills();
		BOTRelationCollection botRelateColl = result.getBOTRelationCollection();

		PaymentBillInfo destBillInfo = null;

		//2009-1-12 ȡ������֯  
		//��ʵʵ�������֯�����ù�����Ŀ�ϵ�fullorgunit��modify  by sxhong 2009-06-04 18:31:24
		FullOrgUnitInfo org = payReqBill.getOrgUnit();
		CompanyOrgUnitInfo company=null;	
		if(payReqBill.getCurProject().getFullOrgUnit()!=null){
			String companyId=payReqBill.getCurProject().getFullOrgUnit().getId().toString();
			company=new CompanyOrgUnitInfo();
			company.setId(BOSUuid.read(companyId));
		}else{
			company=FDCHelper.getFIOrgUnit(ctx, org);
		}
		
		BOSObjectType  contractType=new ContractBillInfo().getBOSType();
		SelectorItemCollection selectors = new SelectorItemCollection();
		selectors.add("isNeedPaid");
		selectors.add("account.id");
		
		//�������֮�󣬲������Դ���
		//�˴�����BOTPδ���õ����� 
		for (int i = 0, size = destBillColl.size(); i < size; i++) {

			destBillInfo = (PaymentBillInfo) destBillColl.getObject(i);
			if(destBillInfo.getCU()==null){
				destBillInfo.setCU(payReqBill.getCU());
			}
			if(destBillInfo.getCompany()==null){
				destBillInfo.setCompany(company);
			}
//			if(destBillInfo.getBizDate()==null){
//				destBillInfo.setBizDate(payReqBill.getBizDate());
				destBillInfo.setBizDate(SysUtil.getAppServerTime(ctx));//���ز�ά��bookeddate��Ĭ�������뵥��ͬ
//			}
		  //����Ҫ��Ѹ������ڸĳ�����ʱ������
//			if(destBillInfo.getPayDate()==null){//botpδ���ø���ʱ��ʱȡ���뵥��������,����ʱȡ����ʱ������
//				destBillInfo.setPayDate(payReqBill.getPayDate());
//			}
			if(destBillInfo.getCurProject()==null){
				destBillInfo.setCurProject(payReqBill.getCurProject());
			}
			if(destBillInfo.getBillStatus()==null){
				destBillInfo.setBillStatus(BillStatusEnum.SAVE);
			}
			if(destBillInfo.getCurrency()==null){
				destBillInfo.setCurrency(payReqBill.getCurrency());
			}
			if(destBillInfo.getExchangeRate()==null){
				destBillInfo.setExchangeRate(payReqBill.getExchangeRate());
			}
			if(destBillInfo.getAmount()==null){
				destBillInfo.setAmount(payReqBill.getOriginalAmount());
			}
			if(destBillInfo.getLocalAmt()==null){
				if (payReqBill.getAmount()==null && payReqBill.getOriginalAmount() != null && payReqBill.getExchangeRate() != null){
					destBillInfo.setLocalAmt(payReqBill.getOriginalAmount().multiply(payReqBill.getExchangeRate()));
				}else{
					destBillInfo.setLocalAmt(payReqBill.getAmount());
				}
			}
			if(destBillInfo.getFdcPayReqID()==null){
				destBillInfo.setFdcPayReqID(payReqBill.getId().toString());
			}
			if(destBillInfo.getFdcPayReqNumber()==null){
				destBillInfo.setFdcPayReqNumber(payReqBill.getNumber());
			}
			if(destBillInfo.getContractNo()==null){
				destBillInfo.setContractNo(payReqBill.getContractNo());
			}
			if(destBillInfo.getContractBillId()==null){
				destBillInfo.setContractBillId(payReqBill.getContractId());
			}
			if(destBillInfo.getActFdcPayeeName()==null){
				destBillInfo.setActFdcPayeeName(payReqBill.getRealSupplier());
			}
			if(destBillInfo.getFdcPayeeName()==null){
				destBillInfo.setFdcPayeeName(payReqBill.getSupplier());
			}
			if(destBillInfo.getCapitalAmount()==null){
				destBillInfo.setCapitalAmount(payReqBill.getCapitalAmount());
			}
			if(destBillInfo.getAddProjectAmt()==null){
				destBillInfo.setAddProjectAmt(payReqBill.getProjectPriceInContractOri());
			}
			if(destBillInfo.getLstPrjAllPaidAmt()==null){
				destBillInfo.setLstPrjAllPaidAmt(payReqBill.getLstPrjAllPaidAmt());
			}
			//�Ƿ��ύ����
			destBillInfo.setIsNeedPay(payReqBill.isIsPay());
			//��ע
			if(destBillInfo.getDescription()==null){
				destBillInfo.setDescription(payReqBill.getDescription());
			}
			//����˵��
			if(destBillInfo.getSummary()==null){
				destBillInfo.setSummary(payReqBill.getMoneyDesc());
			}
			//�����̶�
			if(destBillInfo.getUrgentDegree()==null){
				destBillInfo.setUrgentDegree(UrgentDegreeEnum.getEnum(String.valueOf(payReqBill.getUrgentDegree().getValue())));
			}
			//destBillInfo.setIsEmergency(IsMergencyEnum.getEnum(String.valueOf(payReqBill.getUrgentDegree().getValue())));
			//�����̶�
			if(destBillInfo.getIsEmergency()==null){
				if(destBillInfo.getUrgentDegree()!=null&&destBillInfo.getUrgentDegree().equals(UrgentDegreeEnum.URGENT)){
					destBillInfo.put("isEmergency", new Integer(1));
				}else{
					destBillInfo.put("isEmergency", new Integer(0));
				}
			}
			//�տ������˻�
			if(destBillInfo.getPayeeBank()==null){
				destBillInfo.setPayeeBank(payReqBill.getRecBank());
			}
			if(destBillInfo.getPayeeAccountBank()==null){
				destBillInfo.setPayeeAccountBank(payReqBill.getRecAccount());
			}
			
			//ͬ������Լ���;
			destBillInfo.setIsDifferPlace(payReqBill.getIsDifferPlace());
			if(destBillInfo.getUsage()==null){
				destBillInfo.setUsage(payReqBill.getUsage());
			}
			destBillInfo.setAccessoryAmt(payReqBill.getAttachment());
			
			
			EntityViewInfo evi = new EntityViewInfo();
			FilterInfo filter = new FilterInfo();
	        StringBuffer sb = new StringBuffer();
	        sb.append("select FAsstActTypeID from T_BD_AsstActTypeDefault where FIsAccountCussent=1 ");
	        sb.append(" and FCompanyID='" + company.getId().toString() + "'");
	        filter.getFilterItems().add(new FilterItemInfo("id", sb.toString(), CompareType.INNER));
	      
	        evi.setFilter(filter);
	        evi.getSorter().add(new SorterItemInfo("realtionDataObject"));
	        IAsstActType asstActType = AsstActTypeFactory.getLocalInstance(ctx);
	        AsstActTypeCollection asstActTypeCollection = asstActType.getAsstActTypeCollection(evi);
			//�տ���
			if(payReqBill.getSupplier()!=null){
				destBillInfo.setPayeeID(payReqBill.getSupplier().getId().toString());
				destBillInfo.setPayeeNumber(payReqBill.getSupplier().getNumber());
				destBillInfo.setPayeeName(payReqBill.getSupplier().getName());
				for(int k=0;k<asstActTypeCollection.size();k++){
					if ("provider".equalsIgnoreCase(asstActTypeCollection.get(k).getAsstHGAttribute())){
						destBillInfo.setPayeeType(asstActTypeCollection.get(k));
					}
				}
			}else if(payReqBill.getPerson()!=null){
				destBillInfo.setPayeeID(payReqBill.getPerson().getId().toString());
				destBillInfo.setPayeeNumber(payReqBill.getPerson().getNumber());
				destBillInfo.setPayeeName(payReqBill.getPerson().getName());
				for(int k=0;k<asstActTypeCollection.size();k++){
					if ("person".equalsIgnoreCase(asstActTypeCollection.get(k).getAsstHGAttribute())){
						destBillInfo.setPayeeType(asstActTypeCollection.get(k));
					}
				}
			}
			//��������
			if(destBillInfo.getFdcPayType()==null){
				destBillInfo.setFdcPayType(payReqBill.getPaymentType());
			}
			
			/**���Ӳ��������ݲ������Ƹ������뵥���������Ժ����ɵĸ�����Ƶ��˵�ȡֵ��R090520-176������by neo **/
//			boolean isCreator = FDCUtils.getDefaultFDCParamByKey(ctx, 
//					ContextUtil.getCurrentFIUnit(ctx).getId().toString(),FDCConstants.FDC_PARAM_PAYMENTCREATOR);
			boolean isCreator=true;
			if(isCreator){
				destBillInfo.setCreator(payReqBill.getCreator());
			}
			
			/**��������ı���ͬ��
			 * �����ò���ɱ�һ�廯����ʱ������ѡ�����踶��������s��������Ŀ���ֶΣ�
			 * �����󣬸��ֶν���Զ����롰�����Ŀ������Ӧ�ĸ�����Զ����Ѹ��״̬��
			 * ��������һ�廯����������ѡ�����踶������ı���ͬ�����󣬶�Ӧ�ĸ���Զ���Ϊ���Ѹ����*/
			
			ContractWithoutTextInfo model = null;
			if(!BOSUuid.read(payReqBill.getContractId()).getType().equals(contractType)){
				model = (ContractWithoutTextInfo)ContractWithoutTextFactory.getLocalInstance(ctx).
						getContractWithoutTextInfo(new ObjectUuidPK(payReqBill.getContractId()),selectors);
				if(destBillInfo.getPayerAccount()==null){
					destBillInfo.setPayerAccount(model.getAccount());
				}
			}
			if(payReqBill.getSupplier()!=null){
				if(payReqBill.getPaymentType()!=null){
					destBillInfo.setDescription("֧��  "+payReqBill.getSupplier().getName()+" "+payReqBill.getPaymentType().getName());
				}else{
					destBillInfo.setDescription("֧��  "+payReqBill.getSupplier().getName());
				}
				
			}else if(payReqBill.getPerson()!=null){
				if(payReqBill.getPaymentType()!=null){
					destBillInfo.setDescription("֧��  "+payReqBill.getPerson().getName()+" "+payReqBill.getPaymentType().getName());
				}else{
					destBillInfo.setDescription("֧��  "+payReqBill.getPerson().getName());
				}
			}
			if(payReqBill.getBgEntry().size()==1){
				destBillInfo.setOppAccount(payReqBill.getBgEntry().get(0).getAccountView());
			}
//			for(int j=0;j<payReqBill.getBgEntry().size();j++){
//				PaymentBillEntryInfo entry=new PaymentBillEntryInfo();
//				entry.setAmount(payReqBill.getBgEntry().get(j).getRequestAmount());
//				entry.setLocalAmt(payReqBill.getBgEntry().get(j).getRequestAmount());
//	            entry.setActualAmt(payReqBill.getBgEntry().get(j).getRequestAmount());
//	            entry.setActualLocAmt(payReqBill.getBgEntry().get(j).getRequestAmount());
//	            entry.setCurrency(payReqBill.getCurrency());
//	            entry.setExpenseType(payReqBill.getBgEntry().get(j).getExpenseType());
//	            entry.setSourceBillEntryId(payReqBill.getBgEntry().get(j).getId().toString());
//	            entry.setCostCenter(payReqBill.getCostedDept());
//				destBillInfo.getEntries().add(entry);
//			}
//			destBillInfo.setCostCenter(payReqBill.getCostedDept());
			destBillInfo.setPayBillType(payReqBill.getPayBillType());
			
			//�ύ״̬
			IObjectPK pk =null;
			int ss=0;
			while(ss<8){
				try {
					pk= iBTPManager.saveRelations(destBillInfo, botRelateColl);
					break;
				} catch (Exception e) {
					ss++;
				}
			}
			
			destBillInfo.setId(BOSUuid.read(pk.toString()));
			
			boolean is = FDCUtils.IsFinacial(ctx,company.getId().toString());
			if(model!=null && model.isIsNeedPaid() ){
				List list = new ArrayList();
				list.add(pk.toString());
				if((is && model.getAccount()!=null ) || !is ){
					PaymentBillFactory.getLocalInstance(ctx).audit4FDC(list);
				}
			}else{
				if(!isCreator){
					SelectorItemCollection selector = new SelectorItemCollection();
					selector.add("createTime");
					selector.add("creator");
					destBillInfo.setCreateTime(null);
					destBillInfo.setCreator(null);
					PaymentBillFactory.getLocalInstance(ctx).updatePartial(destBillInfo, selector);
				}
			}
		}
		
		return destBillInfo;
	}
	
	private void updateFDCPaymentBillinvoice(Context ctx, PayRequestBillInfo payReqBill, PaymentBillInfo paymentBill) throws EASBizException, BOSException{
		//�������ɸ���м��ʱ�������������뵥�ķ�Ʊ���ֶ�
		FDCPaymentBillInfo fdcPayment = new FDCPaymentBillInfo();
		fdcPayment.setInvoiceNumber(payReqBill.getInvoiceNumber());
		fdcPayment.setInvoiceAmt(payReqBill.getInvoiceAmt());
		fdcPayment.setAllInvoiceAmt(payReqBill.getInvoiceAmt());
		fdcPayment.setInvoiceDate(payReqBill.getInvoiceDate());
		FDCPaymentBillHelper.handleFDCPaymentBillInvoice(ctx, fdcPayment, paymentBill);
	}
	
	/**
	 * �������뵥�ۼƶ���ں�ͬ����������ʱ���ѣ��ύ��������
	 * @param ctx
	 * @param billInfo
	 * @throws BOSException 
	 */
	private void checkAmt(Context ctx,PayRequestBillInfo billInfo) throws BOSException,EASBizException {
		if(billInfo.getLatestPrice()==null){
			return;
		}
		EntityViewInfo view=new EntityViewInfo();
		FilterInfo filter=new FilterInfo();
		view.setFilter(filter);
		filter.appendFilterItem("contractId", billInfo.getContractId());
		view.getSelector().add("amount");
		PayRequestBillCollection c = PayRequestBillFactory.getLocalInstance(ctx).getPayRequestBillCollection(view);
		BigDecimal total=FDCHelper.ZERO;
		for(int i=0;i<c.size();i++){
			total=total.add(FDCHelper.toBigDecimal(c.get(0).getAmount()));
		}
		if(total.compareTo(billInfo.getLatestPrice())>0){
			throw new PayRequestBillException(PayRequestBillException.CHECKLSTAMT);
		}
		
	}
	
	private void checkPaymentType(Context ctx,PayRequestBillInfo payReq) throws EASBizException,BOSException{

/*		String settlementID="Ga7RLQETEADgAAC/wKgOlOwp3Sw=";//�����
		String progressID="Ga7RLQETEADgAAC6wKgOlOwp3Sw=";//���ȿ�
		String keepID="Ga7RLQETEADgAADDwKgOlOwp3Sw=";//���޿�
*/		
		if(payReq.getContractId()==null||payReq.getPaymentType()==null||payReq.getPaymentType().getPayType()==null){
			return;
		}
		PaymentTypeInfo type=payReq.getPaymentType();
		FilterInfo filter=new FilterInfo();
		filter.appendFilterItem("paymentType.payType.id", PaymentTypeInfo.settlementID);
		filter.appendFilterItem("contractId", payReq.getContractId());
		if(payReq.getId()!=null){
			filter.getFilterItems().add(new FilterItemInfo("id",payReq.getId(),CompareType.NOTEQUALS));
		}
		if(type.getPayType().getId().toString().equals(PaymentTypeInfo.keepID)){
			String orgUnitId = FDCUtils.findCostCenterOrgId(ctx, payReq.getCurProject().getId().toString());
			boolean isKeepBefore = FDCUtils.getDefaultFDCParamByKey(ctx, orgUnitId, FDCConstants.FDC_PARAM_KEEPBEFORESETTLEMENT);
			if(!_exists(ctx,filter)){
				if(!isKeepBefore){
					throw new PayRequestBillException(PayRequestBillException.CANTSELECTKEEPAMT);
				}
//				MsgBox.showError(prmtPayment,"�����������ܸ����޿�,�����ڸ������͵�����Ϊ�������ĸ������뵥ʱ����ѡ�񡰱��޿���͸�������");
			}
			
			//���޿��ܽ��ܴ��ڽ��㵥�ϵ��ʱ���
			FilterInfo myfilter=new FilterInfo();
			myfilter.appendFilterItem("contractBill.id", payReq.getContractId());
			EntityViewInfo view=new EntityViewInfo();
			view.setFilter(myfilter);
			view.getSelector().add("qualityGuarante");
			// by Cassiel_peng
			SorterItemInfo sorterItem = new SorterItemInfo();
			sorterItem.setPropertyName("qualityGuarante");
			sorterItem.setSortType(SortType.DESCEND);
			view.getSorter().add(sorterItem);
			ContractSettlementBillCollection c = ContractSettlementBillFactory.getLocalInstance(ctx).getContractSettlementBillCollection(view);
			BigDecimal amount=FDCHelper.ZERO;
			// by Cassiel_peng
			if(c!=null&&c.size()!=0){
				amount=FDCHelper.toBigDecimal(FDCHelper.toBigDecimal(c.get(0).getQualityGuarante()));
			}
			/**
			 * ����ϵͳԭ��ֻ֧�ֺ�ͬ�ĵ��ʽ��㣬����ȡ�ۼƱ��޿����ʱ�������´������󣬵���֧�ֺ�ͬ�Ķ�ʽ���֮����Ȼ��������
			 * �ͻᵼ�������ظ��ۼӣ�����Ҫ����ֻ��ȡ�ú�ͬ���ۼƱ��޿������ÿ�ʽ��㵥���ۼƱ��޿�֮��   by Cassiel_peng
			 */
//			for(int i=0;i<c.size();i++){
//				 amount= amount.add(FDCHelper.toBigDecimal(c.get(i).getQualityGuarante()));
//			}
			//�������뵥���ۼƱ��޽��
			view=new EntityViewInfo();
			filter=new FilterInfo();
			filter.appendFilterItem("contractId",payReq.getContractId());
			filter.appendFilterItem("paymentType.payType.id",PaymentTypeInfo.keepID);
			view.setFilter(filter);
			view.getSelector().add("amount");
			PayRequestBillCollection payRequestBillC = getPayRequestBillCollection(ctx, view);
			BigDecimal payReqAmount=FDCHelper.ZERO;
			for(int i=0;i<payRequestBillC.size();i++){
				PayRequestBillInfo info=payRequestBillC.get(i);
				if(info.getId().equals(payReq.getId())){
					continue;//�Թ���ǰ����
				}
				payReqAmount=payReqAmount.add(FDCHelper.toBigDecimal(info.getAmount()));
			}
			//
			payReqAmount=payReqAmount.add(FDCHelper.toBigDecimal(payReq.getAmount()));
//			if(FDCHelper.toBigDecimal(payReq.getAmount()).compareTo(amount)>0){
			if(FDCHelper.toBigDecimal(payReqAmount).compareTo(amount)>0){
				throw new PayRequestBillException(PayRequestBillException.MORETHANQUALITYGUARANTE);
			}
			
		}
		
		if(type.getPayType().getId().toString().equals(PaymentTypeInfo.progressID)){
			if(_exists(ctx,filter)){
				//֮ǰ����ʾ��Ϣ���� by zhiyuan_tang 2010/12/21
				throw new PayRequestBillException(PayRequestBillException.CANTSELECTPROGRESSAMT);
//				MsgBox.showError(prmtPayment,"����������ܸ����ȿ�,�����ڸ������͵�����Ϊ�������ĸ������뵥ʱ����ѡ�񡰽��ȿ���͸�������");
			}
		}
		
		if(type.getPayType().getId().toString().equals(PaymentTypeInfo.settlementID)){
			FilterInfo myfilter=new FilterInfo();
			myfilter.appendFilterItem("id", payReq.getContractId());
			myfilter.appendFilterItem("hasSettled", Boolean.TRUE);
			if(!ContractBillFactory.getLocalInstance(ctx).exists(myfilter)){
				throw new PayRequestBillException(PayRequestBillException.MUSTSETTLE);
//				MsgBox.showError(prmtPayment,"��ͬ�������֮���������������ĸ������뵥");
			}
		}
	
	}
	
	//������ʱ���༭�����ʼ���ݴ����ȷ���
	protected Map _fetchInitData(Context ctx, Map paramMap) throws BOSException, EASBizException {
		
		paramMap.put("isCost",Boolean.FALSE);
		Map initMap = super._fetchInitData(ctx,paramMap);		
		CompanyOrgUnitInfo currentFIUnit = ContextUtil.getCurrentFIUnit(ctx);

		String comId = null;
//		String reqPayId = null;
//		if(paramMap.get("reqPayId")!=null){
//			reqPayId = (String) paramMap.get("reqPayId");
//		}
	
		//��ͬ����
		String contractBillId = (String)paramMap.get("contractBillId");
		
		if(contractBillId==null){
			
			if(paramMap.get("ID")==null){
				return initMap;
			}
			
			String id = paramMap.get("ID").toString();
			
			SelectorItemCollection selector = new SelectorItemCollection();
			selector.add("contractId");	
			PayRequestBillInfo bill = (PayRequestBillInfo) this.getValue(ctx,new ObjectUuidPK(id),selector);
			contractBillId = bill.getContractId();
		}
		
		//initMap.put("payScale",FDCConstants.ZERO);
		
		if(contractBillId!=null){
			
			SelectorItemCollection selector = new SelectorItemCollection();
			selector.add("*");
			selector.add("currency.*");
			selector.add("curProject.name");
			selector.add("curProject.number");
			selector.add("curProject.longNumber");
			selector.add("curProject.codingNumber");
			selector.add("curProject.displayName");
			selector.add("curProject.parent.id");
			selector.add("curProject.fullOrgUnit.name");
			selector.add("curProject.fullOrgUnit.code");
			selector.add("curProject.CU.name");
			selector.add("curProject.CU.number");
			selector.add("curProject.CU.code");
			selector.add("respDept.*");
			selector.add("partB.*");
			selector.add("orgUnit.*");
			selector.add("contractType.entry.payContentType.*");
			selector.add("lxNum.*");
			CurProjectInfo project = null;
			
			BOSObjectType  contractType=new ContractBillInfo().getBOSType();

			if(BOSUuid.read(contractBillId).getType().equals(contractType)){
				
				ContractBillInfo contractBill = ContractBillFactory.getLocalInstance(ctx).
						getContractBillInfo(new ObjectUuidPK(BOSUuid.read(contractBillId)),selector);
				initMap.put(FDCConstants.FDC_INIT_CONTRACT,contractBill);
				
				//�������
				if (contractBill.isHasSettled()) {				
				
					FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
					builder.clear();
					builder.appendSql("select fqualityGuarante as amount from t_con_contractsettlementbill where fcontractbillid=");
					builder.appendParam(contractBillId);
					IRowSet rowSet = builder.executeQuery();
					BigDecimal amount = FDCHelper.ZERO;
					if (rowSet.size() == 1) {
						try {
							rowSet.next();
							amount = FDCHelper.toBigDecimal(rowSet.getBigDecimal("amount"));
						} catch (SQLException e) {
							e.printStackTrace();
							throw new BOSException(e);
						}					
					}
					
					initMap.put("payScale",amount);
				}
				
				//��Ӧ��
				if (contractBill.getPartB()!=null){
					String supplierid= contractBill.getPartB().getId().toString();
					BOSUuid FIid = contractBill.getCurProject().getFullOrgUnit().getId();
					SupplierCompanyInfoInfo companyInfo = SupplierFactory.getLocalInstance(ctx).
							getCompanyInfo(new ObjectUuidPK(supplierid),new ObjectUuidPK(FIid));	
					initMap.put("supplierCompanyInfoInfo",companyInfo);
				}				
				
				project = contractBill.getCurProject();
				comId = contractBill.getCurProject().getFullOrgUnit().getId().toString();
				
				/*****
				 * 
				 * ��Ҫ����Ƿ�����ü׹��ĺ�ͬ��ص�������״̬���ֳ�ȷ�ϵ���
				 * ����У���
				 * 			���ֳ�ȷ�ϵ��ϵĽ��д����������ԭ���ֶΣ��н�������޸ģ�û����������������
				 * 			���ɸ������뵥���ֳ�ȷ�ϵ���¼
				 
				 */
				if(contractBill.isIsPartAMaterialCon()){
					BigDecimal confirmAmts = FDCHelper.ZERO;
					if(paramMap.get("Fw_ID")!=null){
						selector = new SelectorItemCollection();
						selector.add("confirmEntry.*");	
						PayRequestBillInfo bill = (PayRequestBillInfo) this.getValue(ctx,new ObjectUuidPK((String)paramMap.get("Fw_ID")),selector);
						for(int i=0;i<bill.getConfirmEntry().size();i++){
							confirmAmts = confirmAmts.add(FDCHelper.toBigDecimal(bill.getConfirmEntry().get(i).getReqAmount()));
						}
						initMap.put("confirmAmts", confirmAmts);
						initMap.put("confirmBillEntry", bill.getConfirmEntry());
					}
					else{
						EntityViewInfo view = new EntityViewInfo();
						selector = view.getSelector();
						selector.add("id");
						selector.add("confirmAmt");
						selector.add("toDateReqAmt");
						FilterInfo filter = new FilterInfo();
						filter.getFilterItems().add(new FilterItemInfo("state",FDCBillStateEnum.AUDITTED_VALUE));
						filter.getFilterItems().add(new FilterItemInfo("materialContractBill.id",contractBillId));
						view.setFilter(filter);
						MaterialConfirmBillCollection mcBills = MaterialConfirmBillFactory.getLocalInstance(ctx).getMaterialConfirmBillCollection(view);
						confirmAmts = FDCHelper.ZERO;
						PayRequestBillConfirmEntryCollection confirmEntrys = new PayRequestBillConfirmEntryCollection();
						for(Iterator it=mcBills.iterator();it.hasNext();){
							MaterialConfirmBillInfo mcBill = (MaterialConfirmBillInfo)it.next();
							BigDecimal reqAmount = FDCHelper.toBigDecimal(mcBill.getConfirmAmt()).subtract(FDCHelper.toBigDecimal(mcBill.getToDateReqAmt()));
							if(reqAmount.compareTo(FDCHelper.ZERO)>0){
								PayRequestBillConfirmEntryInfo item = new PayRequestBillConfirmEntryInfo();
								item.setConfirmBill(mcBill);
								item.put("notIncludeThisReqAmount", mcBill.getToDateReqAmt());
								item.setReqAmount(reqAmount);
								confirmEntrys.add(item);
								confirmAmts = FDCHelper.add(reqAmount, confirmAmts);
							}
							
						}
						initMap.put("confirmAmts", confirmAmts);
						initMap.put("confirmBillEntry", confirmEntrys);
					}
				}
			}else{
				
				ContractWithoutTextInfo contractBill = ContractWithoutTextFactory.getLocalInstance(ctx).
				getContractWithoutTextInfo(new ObjectUuidPK(BOSUuid.read(contractBillId)),selector);
				//initMap.put(FDCConstants.FDC_INIT_CONTRACT,contractBill);
				
				project = contractBill.getCurProject();
				
				comId = contractBill.getCurProject().getFullOrgUnit().getId().toString();
			}

			
			//������Ŀ
			String projectId = (String) project.getId().toString();
			CurProjectInfo curProjectInfo = CurProjectFactory.getLocalInstance(ctx).getCurProjectInfo(new ObjectUuidPK(projectId));
			
			initMap.put(FDCConstants.FDC_INIT_PROJECT,curProjectInfo);
			
			if(projectId!=null){
				initPeriod( ctx, projectId,curProjectInfo, comId, initMap ,false);
			}		
			
			//������Ŀ��Ӧ����֯
			String orgUnitId = null;
			if(curProjectInfo!=null &&  curProjectInfo.getCostCenter()!=null){
				orgUnitId = curProjectInfo.getCostCenter().getId().toString();
			}			
			//��õ�ǰ��֯		
			FullOrgUnitInfo orgUnitInfo = FullOrgUnitFactory.getLocalInstance(ctx)
				.getFullOrgUnitInfo(new ObjectUuidPK(orgUnitId));			
			initMap.put(FDCConstants.FDC_INIT_ORGUNIT,orgUnitInfo);
			
			//���»�ȡ������Ŀ��Ӧ�Ĳ�����֯   R101202-122 by zhiyuan_tang
			if (comId != null) {
				CompanyOrgUnitInfo company =GlUtils.getCurrentCompany(ctx, comId,null,false);	
				initMap.put(FDCConstants.FDC_INIT_COMPANY,company);
			}
			
			
			//���ø������Ϊ��ͬ�ĸ������ �Ӹ���й���
			EntityViewInfo view = new EntityViewInfo();
			selector = view.getSelector();
			selector.add("id");
			FilterInfo filter = new FilterInfo();
			view.setFilter(filter);
			filter.getFilterItems().add(
					new FilterItemInfo("contractBillId", contractBillId));
			filter.getFilterItems().add(new FilterItemInfo("billStatus", BillStatusEnum.PAYED_VALUE+ ""));
			BillBaseCollection 	c = PaymentBillFactory.getLocalInstance(ctx).getBillBaseCollection(view);
			initMap.put("payTimes",new Integer(c.size()));
			
			//ContractChangeBillCollection
			filter = new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("contractBill.id",contractBillId));
			filter.getFilterItems().add(new FilterItemInfo("state", FDCBillStateEnum.AUDITTED_VALUE));
			filter.getFilterItems().add(new FilterItemInfo("state", FDCBillStateEnum.VISA_VALUE));
			filter.getFilterItems().add(new FilterItemInfo("state", FDCBillStateEnum.ANNOUNCE_VALUE));
			filter.setMaskString("#0 and (#1 or #2 or #3)");
			view = new EntityViewInfo();
			view.setFilter(filter);
			view.getSelector().add("amount");
			view.getSelector().add("balanceAmount");
			view.getSelector().add("hasSettled");
			IObjectCollection collection = ContractChangeBillFactory.getLocalInstance(ctx).getContractChangeBillCollection(view);
			initMap.put("ContractChangeBillCollection",collection);
			
			//PaymentBillCollection
		    view=new EntityViewInfo();
		    view.getSelector().add("contractBillId");
		    view.getSelector().add("billStatus");
		    view.getSelector().add("amount");
		    view.getSelector().add("fdcPayReqID");
		    filter=new FilterInfo();
		    view.setFilter(filter);
		    filter.getFilterItems().add(new FilterItemInfo("contractBillId",contractBillId));
		    filter.getFilterItems().add(new FilterItemInfo("billStatus",BillStatusEnum.PAYED_VALUE+""));
		    BillBaseCollection billBaseCollection = PaymentBillFactory.getLocalInstance(ctx).getBillBaseCollection(view);
		    initMap.put("PaymentBillCollection",billBaseCollection);
		        
		    //GuerdonOfPayReqBillCollection
			view=new EntityViewInfo();
			filter=new FilterInfo();
			filter.appendFilterItem("payRequestBill.contractId", contractBillId);
			view.setFilter(filter);
			//TODO �ټ�һ��ʱ�����
			GuerdonOfPayReqBillCollection guerdonOfPayReqBillCollection = GuerdonOfPayReqBillFactory.getLocalInstance(ctx)
						.getGuerdonOfPayReqBillCollection(view);
			initMap.put("GuerdonOfPayReqBillCollection",guerdonOfPayReqBillCollection);
			
			//GuerdonBillCollection
			view=new EntityViewInfo();
			filter=new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("contract.id", contractBillId));
			filter.getFilterItems().add(new FilterItemInfo("state",FDCBillStateEnum.AUDITTED_VALUE,CompareType.EQUALS));
			filter.getFilterItems().add(new FilterItemInfo("isGuerdoned",String.valueOf(1),CompareType.NOTEQUALS));
		
			view.setFilter(filter);
			view.getSelector().add("id");
			view.getSelector().add("amount");
			view.getSelector().add("originalAmount");
			view.getSelector().add("isGuerdoned");
			GuerdonBillCollection guerdonBillCollection = GuerdonBillFactory.getLocalInstance(ctx).getGuerdonBillCollection(view);
			initMap.put("GuerdonBillCollection",guerdonBillCollection);
			
			//PartAOfPayReqBillCollection
			view=new EntityViewInfo();
			filter=new FilterInfo();
			filter.appendFilterItem("payRequestBill.contractId", contractBillId);
			view.setFilter(filter);
			PartAOfPayReqBillCollection partAOfPayReqBillCollection = PartAOfPayReqBillFactory.getLocalInstance(ctx)
						.getPartAOfPayReqBillCollection(view);
			initMap.put("PartAOfPayReqBillCollection",partAOfPayReqBillCollection);
			
			//PartAConfmOfPayReqBillCollection
			view=new EntityViewInfo();
			filter=new FilterInfo();
			filter.appendFilterItem("payRequestBill.contractId", contractBillId);
			view.setFilter(filter);
			PartAConfmOfPayReqBillCollection partAConfmOfPayReqBillCollection = PartAConfmOfPayReqBillFactory.getLocalInstance(ctx)
						.getPartAConfmOfPayReqBillCollection(view);
			initMap.put("PartAConfmOfPayReqBillCollection",partAConfmOfPayReqBillCollection);
			
			//MaterialConfirmBillCollection
			/*view=new EntityViewInfo();
			filter=new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("mainContractBill.id", contractBillId));
			filter.getFilterItems().add(new FilterItemInfo("state",FDCBillStateEnum.AUDITTED_VALUE,CompareType.EQUALS));
			filter.getFilterItems().add(new FilterItemInfo("hasApplied",String.valueOf(1),CompareType.NOTEQUALS));
		
			view.setFilter(filter);
			view.getSelector().add("id");
			view.getSelector().add("amount");
			view.getSelector().add("hasApplied");
			MaterialConfirmBillCollection materialConfirmBillCollection = MaterialConfirmBillFactory.getLocalInstance(ctx).getMaterialConfirmBillCollection(view);
			initMap.put("MaterialConfirmBillCollection",materialConfirmBillCollection);*/
			
			//CompensationOfPayReqBillCollection
			view=new EntityViewInfo();
			filter=new FilterInfo();
			filter.appendFilterItem("payRequestBill.contractId", contractBillId);
			view.setFilter(filter);
			//TODO �ټ�һ��ʱ�����
			CompensationOfPayReqBillCollection compensationOfPayReqBillCollection = CompensationOfPayReqBillFactory.getLocalInstance(ctx).getCompensationOfPayReqBillCollection(view);
			initMap.put("CompensationOfPayReqBillCollection",compensationOfPayReqBillCollection);
			
			//DeductTypeCollection
			view = new EntityViewInfo();
			filter = new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("isEnabled", Boolean.TRUE));
			//���˼׹���
			filter.getFilterItems().add(new FilterItemInfo("id", DeductTypeInfo.partAMaterialType, CompareType.NOTEQUALS));
			view.setFilter(filter);
			final SorterItemInfo sorterItemInfo = new SorterItemInfo("number");
			sorterItemInfo.setSortType(SortType.ASCEND);
			view.getSorter().add(sorterItemInfo);
			view.getSelector().add("number");
			view.getSelector().add("name");
			DeductTypeCollection deductTypeCollection = DeductTypeFactory.getLocalInstance(ctx).getDeductTypeCollection(view);
			initMap.put("DeductTypeCollection",deductTypeCollection);
			
//			CostCenterOrgUnitInfo costCenterOrg = FDCHelper.getCostCenter(project,ctx);
//			
//			FullOrgUnitInfo orgInfo = null;
//			if(costCenterOrg!=null){
//				orgInfo = costCenterOrg.castToFullOrgUnitInfo();
//			}
			
			initMap.put("FullOrgUnitInfo",orgUnitInfo);
			
			try {
				Map resultMap = FDCUtils.getLastOriginalAmt_Batch(ctx, new String[]{contractBillId});
				if(resultMap!=null&&resultMap.containsKey(contractBillId)){
					initMap.put("lastestPriceOriginal",resultMap.get(contractBillId));
				}
				else{
					initMap.put("lastestPriceOriginal",FDCHelper.ZERO);
				}
			} catch (Exception e) {
				// TODO �Զ����� catch ��
				e.printStackTrace();
			}
			
			
			
			
		}
		
		return initMap;
	}
	
    private SelectorItemCollection getSic(){
		// �˹���Ϊ��ϸ��Ϣ����
        SelectorItemCollection sic = new SelectorItemCollection();
        sic.add(new SelectorItemInfo("id"));
        sic.add(new SelectorItemInfo("number"));
        sic.add(new SelectorItemInfo("period.id"));
        sic.add(new SelectorItemInfo("period.beginDate"));	
        sic.add(new SelectorItemInfo("curProject.fullOrgUnit.id"));
        sic.add(new SelectorItemInfo("curProject.id"));
        sic.add(new SelectorItemInfo("curProject.displayName"));	
        sic.add(new SelectorItemInfo("contractId"));
        sic.add(new SelectorItemInfo("amount"));
        sic.add(new SelectorItemInfo("projectPriceInContract"));
        sic.add(new SelectorItemInfo("confirmEntry.*"));
        sic.add(new SelectorItemInfo("payDate"));
        sic.add(new SelectorItemInfo("paymentType.id"));
        sic.add(new SelectorItemInfo("paymentType.name"));
        sic.add(new SelectorItemInfo("hasClosed"));
        return sic;
    }
    
	//�ύʱУ�鵥���ڼ䲻���ڹ�����Ŀ�ĵ�ǰ�ڼ�֮ǰ
    private void checkBillForSubmit(Context ctx, IObjectValue model)throws BOSException, EASBizException {
		
		//�������ڵ�ǰ�ɱ��ڼ�֮ǰ
    	PayRequestBillInfo payReqBill = (PayRequestBillInfo)model;
		
		//�Ƿ����ò���һ�廯
		String comId = null;
		if( payReqBill.getCurProject().getFullOrgUnit()==null){
			SelectorItemCollection sic = new SelectorItemCollection();
			sic.add("fullOrgUnit.id");
			CurProjectInfo curProject = CurProjectFactory.getLocalInstance(ctx)
					.getCurProjectInfo(new ObjectUuidPK(payReqBill.getCurProject().getId().toString()),sic);
			
			comId = curProject.getFullOrgUnit().getId().toString();
		}else{
			comId = payReqBill.getCurProject().getFullOrgUnit().getId().toString();
		}
		//R110615-0438���������뵥�ύʱ����
		checkConWorkLoad(ctx, payReqBill.getId(), model);
/*		û���жϵı�Ҫ
		boolean isInCore = FDCUtils.IsInCorporation( ctx, comId);
		if(isInCore){
			PeriodInfo bookedPeriod = FDCUtils.getCurrentPeriod(ctx,contractBill.getCurProject().getId().toString(),false);
			if((bookedPeriod!=null && contractBill.getPeriod() !=null) && contractBill.getPeriod().getBeginDate().before(bookedPeriod.getBeginDate())){
				//�����ڼ䲻���ڹ�����Ŀ�ĵ�ǰ�ڼ�֮ǰ CNTPERIODBEFORE
				throw new ContractException(ContractException.CNTPERIODBEFORE);
			}
		}
*/
		/***
		 * ����ȷ�ϵ����ύ�ж�
		 */
		/***
		 * �������Ĳ���ȷ�ϵ����Ƿ�ᳬ��ȷ�Ͻ��
		 */
		if(payReqBill.getConfirmEntry()!=null&&payReqBill.getConfirmEntry().size()>0){
			Set idSet = new HashSet();
			Map confirmBillEntryMap = new HashMap();
			for(int i=0;i<payReqBill.getConfirmEntry().size();i++){
				if(payReqBill.getConfirmEntry().get(i)!=null&&payReqBill.getConfirmEntry().get(i).getConfirmBill()!=null){
					idSet.add(payReqBill.getConfirmEntry().get(i).getConfirmBill().getId().toString());
					confirmBillEntryMap.put(payReqBill.getConfirmEntry().get(i).getConfirmBill().getId().toString(), payReqBill.getConfirmEntry().get(i));
				}
			}
			EntityViewInfo view = new EntityViewInfo();
			FilterInfo filter = new FilterInfo();
			view.getSelector().add("*");
			filter.getFilterItems().add(new FilterItemInfo("id", idSet,CompareType.INCLUDE));
			view.setFilter(filter);
			MaterialConfirmBillCollection mcBills = MaterialConfirmBillFactory.getLocalInstance(ctx).getMaterialConfirmBillCollection(view);
			for(int i=0;i<mcBills.size();i++){
				String key = mcBills.get(i).getId().toString();
				PayRequestBillConfirmEntryInfo entryInfo = (PayRequestBillConfirmEntryInfo)confirmBillEntryMap.get(key);
				entryInfo.setConfirmBill(mcBills.get(i));
			}
			for(int i=0;i<payReqBill.getConfirmEntry().size();i++){
				PayRequestBillConfirmEntryInfo entryInfo = payReqBill.getConfirmEntry().get(i);
				BigDecimal reqAmt = FDCHelper.toBigDecimal(entryInfo.getReqAmount());
				BigDecimal paidAmt = FDCHelper.toBigDecimal(entryInfo.getPaidAmount());
				if(reqAmt.compareTo(paidAmt)>0){
					BigDecimal thisReqNotPaid = reqAmt.subtract(paidAmt);
					BigDecimal confirmAmt     = FDCHelper.toBigDecimal(entryInfo.getConfirmBill().getConfirmAmt());
					BigDecimal confirmPaidAmt = FDCHelper.toBigDecimal(entryInfo.getConfirmBill().getPaidAmt());
					if(confirmPaidAmt.add(thisReqNotPaid).compareTo(confirmAmt)>0){
						throw new EASBizException(new NumericExceptionSubItem("000","�����Ĳ���ȷ�ϵ�:"+entryInfo.getConfirmBill().getNumber()+"  ��ȷ�ϵ�ʵ����� + ȷ�ϵ�����������   ����  ����ȷ�ϵ�ȷ�Ͻ�������ִ�д˲�����"));
					}
				}
			}
		}
	}
	//���У��
	private void checkBillForAudit(Context ctx, BOSUuid billId,FDCBillInfo billInfo )throws BOSException, EASBizException {
    
		PayRequestBillInfo model = (PayRequestBillInfo)billInfo;

        if(model==null || model.getCurProject()==null ||model.getCurProject().getFullOrgUnit()==null||model.getPaymentType()==null){
        	model= this.getPayRequestBillInfo(ctx,new ObjectUuidPK(billId.toString()),getSic());
        }
        if(model.isHasClosed()){
        	throw new EASBizException(new NumericExceptionSubItem("100","�����Ѿ��رգ����ܽ�������������"));
        }
        checkConWorkLoad(ctx, billId,model);
        //��鹦���Ƿ��Ѿ�������ʼ��
		String comId = model.getCurProject().getFullOrgUnit().getId().toString();
/*			û���жϵı�Ҫ
		//�Ƿ����ò���һ�廯
		boolean isInCore = FDCUtils.IsInCorporation( ctx, comId);
		if(isInCore){
			String curProject = model.getCurProject().getId().toString();	
//			//�ɱ��Ѿ��½�
//			PeriodInfo bookedPeriod = FDCUtils.getCurrentPeriod(ctx,curProject,true);
//			if(model.getPeriod().getBeginDate().after(bookedPeriod.getEndDate())){
//				throw new  ContractException(ContractException.AUD_AFTERPERIOD,new Object[]{model.getNumber()});
//			}
			
			//����û���½ᣬ������˵���{0}
			PeriodInfo finPeriod = FDCUtils.getCurrentPeriod(ctx,curProject,false);
			if(finPeriod!=null && model.getPeriod()!=null && model.getPeriod().getBeginDate().after(finPeriod.getEndDate())){
				throw new  ContractException(ContractException.AUD_FINNOTCLOSE,new Object[]{model.getNumber()});
			}	
		}
		*/
		
		//budget contrl
//		FDCBudgetAcctFacadeFactory.getLocalInstance(ctx).invokeBudgetCtrl(model, FDCBudgetConstants.STATE_AUDIT);
		
		
		/***
		 * ����ȷ�ϵ����ύ�ж�
		 */
		/***
		 * �������Ĳ���ȷ�ϵ����Ƿ�ᳬ��ȷ�Ͻ��
		 */
		if(model.getConfirmEntry()!=null&&model.getConfirmEntry().size()>0){
			Set idSet = new HashSet();
			Map confirmBillEntryMap = new HashMap();
			for(int i=0;i<model.getConfirmEntry().size();i++){
				if(model.getConfirmEntry().get(i)!=null&&model.getConfirmEntry().get(i).getConfirmBill()!=null){
					idSet.add(model.getConfirmEntry().get(i).getConfirmBill().getId().toString());
					confirmBillEntryMap.put(model.getConfirmEntry().get(i).getConfirmBill().getId().toString(), model.getConfirmEntry().get(i));
				}
			}
			EntityViewInfo view = new EntityViewInfo();
			FilterInfo filter = new FilterInfo();
			view.getSelector().add("*");
			filter.getFilterItems().add(new FilterItemInfo("id", idSet,CompareType.INCLUDE));
			view.setFilter(filter);
			MaterialConfirmBillCollection mcBills = MaterialConfirmBillFactory.getLocalInstance(ctx).getMaterialConfirmBillCollection(view);
			for(int i=0;i<mcBills.size();i++){
				String key = mcBills.get(i).getId().toString();
				PayRequestBillConfirmEntryInfo entryInfo = (PayRequestBillConfirmEntryInfo)confirmBillEntryMap.get(key);
				entryInfo.setConfirmBill(mcBills.get(i));
			}
			for(int i=0;i<model.getConfirmEntry().size();i++){
				PayRequestBillConfirmEntryInfo entryInfo = model.getConfirmEntry().get(i);
				BigDecimal reqAmt = FDCHelper.toBigDecimal(entryInfo.getReqAmount());
				BigDecimal paidAmt = FDCHelper.toBigDecimal(entryInfo.getPaidAmount());
				if(reqAmt.compareTo(paidAmt)>0){
					BigDecimal thisReqNotPaid = reqAmt.subtract(paidAmt);
					BigDecimal confirmAmt     = FDCHelper.toBigDecimal(entryInfo.getConfirmBill().getConfirmAmt());
					BigDecimal confirmPaidAmt = FDCHelper.toBigDecimal(entryInfo.getConfirmBill().getPaidAmt());
					if(confirmPaidAmt.add(thisReqNotPaid).compareTo(confirmAmt)>0){
						throw new EASBizException(new NumericExceptionSubItem("000","�����Ĳ���ȷ�ϵ�:"+entryInfo.getConfirmBill().getNumber()+"  ��ȷ�ϵ�ʵ����� + ȷ�ϵ�����������   ����  ����ȷ�ϵ�ȷ�Ͻ�������ִ�д˲�����"));
					}
				}
			}
		}
		
	}
	//���У��
	private void checkBillForUnAudit(Context ctx, BOSUuid billId,FDCBillInfo billInfo )throws BOSException, EASBizException {
		PayRequestBillInfo model = (PayRequestBillInfo)billInfo;

        if(model==null  || model.getCurProject()==null ||model.getCurProject().getFullOrgUnit()==null ){
        	model= this.getPayRequestBillInfo(ctx,new ObjectUuidPK(billId.toString()),getSic());
        }
		//��鹦���Ƿ��Ѿ�������ʼ��
		String comId = model.getCurProject().getFullOrgUnit().getId().toString();
/*			û���жϵı�Ҫ
 *  by sxhong 2009-07-30 22:20:49
		//�Ƿ����ò���һ�廯
		boolean isInCore = FDCUtils.IsInCorporation( ctx, comId);
		if(isInCore){
			String curProject = model.getCurProject().getId().toString();

			//�����ڼ��ڹ�����Ŀ��ǰ�ڼ�֮ǰ�����ܷ����
			PeriodInfo bookedPeriod = FDCUtils.getCurrentPeriod(ctx,curProject,false);
			if(bookedPeriod!=null && model.getPeriod()!=null && model.getPeriod().getBeginDate().before(bookedPeriod.getBeginDate())){
				throw new  ContractException(ContractException.CNTPERIODBEFORE);
			}	
			
//			if(ProjectPeriodStatusUtil._isEnd(ctx,curProject)){
//				throw new ProjectPeriodStatusException(ProjectPeriodStatusException.CLOPRO_HASEND,new Object[]{model.getCurProject().getDisplayName()});
//			}	
		}*/
		
		FDCBudgetAcctFacadeFactory.getLocalInstance(ctx).invokeBudgetCtrl(model, FDCBudgetConstants.STATE_UNAUDIT);
	}
	
	protected boolean isCost() {
		return false;
	}
	
	protected String _getContractTypeNumber(Context ctx, IObjectPK pk) throws BOSException, EASBizException {
		BOSObjectType  contractType=new ContractBillInfo().getBOSType();
		
        SelectorItemCollection sic = new SelectorItemCollection();
        sic.add(new SelectorItemInfo("contractId"));
        PayRequestBillInfo model = this.getPayRequestBillInfo(ctx,new ObjectUuidPK(pk.toString()),sic);
        	
		String contractBillId = model.getContractId();
		if(BOSUuid.read(contractBillId).getType().equals(contractType)){
			
	        sic = new SelectorItemCollection();
	        sic.add(new SelectorItemInfo("contractType.number"));
	        sic.add(new SelectorItemInfo("contractType.longNumber"));
			ContractBillInfo contractBillInfo = 
				ContractBillFactory.getLocalInstance(ctx).getContractBillInfo(new ObjectUuidPK(contractBillId),sic);
			
			return contractBillInfo.getContractType().getLongNumber().replace('!','.');
		}else{
			return "none";	
		}
		
	}
	
	//�ر�
	protected void _close(Context ctx, IObjectPK pk) throws BOSException, EASBizException {

		SelectorItemCollection selector = new SelectorItemCollection();
		selector.add("hasClosed");
		PayRequestBillInfo bill = (PayRequestBillInfo) this.getValue(ctx,pk,selector);
		
		bill.setHasClosed(true);

		
		_updatePartial(ctx,bill, selector);
		
	}

	protected void _unClose(Context ctx, IObjectPK pk) throws BOSException, EASBizException {
		
		/***
		 * �������Ĳ���ȷ�ϵ����Ƿ�ᳬ��ȷ�Ͻ��
		 */
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		view.getSelector().add("*");
		view.getSelector().add("confirmBill.*");
		filter.getFilterItems().add(new FilterItemInfo("parent", pk.toString()));
		view.setFilter(filter);
		PayRequestBillConfirmEntryCollection mcBills = PayRequestBillConfirmEntryFactory.getLocalInstance(ctx).getPayRequestBillConfirmEntryCollection(view);
		for(int i=0;i<mcBills.size();i++){
			PayRequestBillConfirmEntryInfo entryInfo = mcBills.get(i);
			BigDecimal reqAmt = FDCHelper.toBigDecimal(entryInfo.getReqAmount());
			BigDecimal paidAmt = FDCHelper.toBigDecimal(entryInfo.getPaidAmount());
			if(reqAmt.compareTo(paidAmt)>0){
				BigDecimal thisReqNotPaid = reqAmt.subtract(paidAmt);
				BigDecimal confirmAmt     = FDCHelper.toBigDecimal(entryInfo.getConfirmBill().getConfirmAmt());
				BigDecimal confirmPaidAmt = FDCHelper.toBigDecimal(entryInfo.getConfirmBill().getPaidAmt());
				if(confirmPaidAmt.add(thisReqNotPaid).compareTo(confirmAmt)>0){
					throw new EASBizException(new NumericExceptionSubItem("000","�����Ĳ���ȷ�ϵ�:"+entryInfo.getConfirmBill().getNumber()+"  ��ȷ�ϵ�ʵ����� + ȷ�ϵ�����������δ�����   ����  ����ȷ�ϵ�ȷ�Ͻ�������ִ�д˲�����"));
				}
			}
		}
		
		SelectorItemCollection selector = new SelectorItemCollection();
		selector.add("hasClosed");
		PayRequestBillInfo bill = (PayRequestBillInfo) this.getValue(ctx,pk,selector);
		
		bill.setHasClosed(false);

		
		_updatePartial(ctx,bill, selector);
	}
	
	protected void _adjustPayment(Context ctx, IObjectPK payRequestBillId, Map dataMap) throws BOSException, EASBizException {
		// TODO �Զ����ɷ������
		
	}
	
	//�Ƿ񳬹����¸���ƻ�
	protected boolean _outPayPlan(Context ctx, IObjectPK pk) throws BOSException, EASBizException {
		BOSObjectType  contractType=new ContractBillInfo().getBOSType();
		
        SelectorItemCollection sic = new SelectorItemCollection();
        sic.add(new SelectorItemInfo("contractId"));
        sic.add(new SelectorItemInfo("amount"));
        sic.add(new SelectorItemInfo("payDate"));
        PayRequestBillInfo model = this.getPayRequestBillInfo(ctx,new ObjectUuidPK(pk.toString()),sic);
        	
		String contractBillId = model.getContractId();
		if(BOSUuid.read(contractBillId).getType().equals(contractType)){
			Date payDate = model.getPayDate();
			int year = payDate.getYear()+1900;
			int month = payDate.getMonth()+1;
			int number = year*100+month;
			
			BigDecimal planAmount = null;
			BigDecimal payAmount = null;
			
			EntityViewInfo view = new EntityViewInfo();
			view.getSelector().add("payDate");
			view.getSelector().add("contractId");
			view.getSelector().add("payAmount");
			
			FilterInfo filter = new FilterInfo();
			view.setFilter(filter);
			filter.getFilterItems().add(new FilterItemInfo("contractId", contractBillId));
					
			ContractPayPlanCollection contractPayPlanCollection = ContractPayPlanFactory
					.getLocalInstance(ctx).getContractPayPlanCollection(view);
			
			for (int i = 0; i < contractPayPlanCollection.size(); i++) {
				ContractPayPlanInfo info = contractPayPlanCollection.get(i);
				Date planDate = info.getPayDate();
				Calendar cal = Calendar.getInstance();
				cal.setTime(planDate);
						
				int key = cal.get(Calendar.YEAR)*100+ cal.get(Calendar.MONTH)+1;
				if(number==key){
					if(planAmount == null){
						planAmount = info.getPayAmount();
					}else if(info.getPayAmount() == null){
						planAmount = planAmount.add(info.getPayAmount());
					}					
				}
			}
			
			view = new EntityViewInfo();
			view.getSelector().add("payDate");
			view.getSelector().add("contractId");
			view.getSelector().add("amount");
			
			filter = new FilterInfo();
			view.setFilter(filter);
			filter.getFilterItems().add(new FilterItemInfo("contractId", contractBillId));
			PayRequestBillCollection col = this.getPayRequestBillCollection(ctx,view);
			for (int i = 0; i < col.size(); i++) {
				PayRequestBillInfo info = col.get(i);
				Date planDate = info.getPayDate();
				Calendar cal = Calendar.getInstance();
				cal.setTime(planDate);
						
				int key = cal.get(Calendar.YEAR)*100+ cal.get(Calendar.MONTH)+1;
				if(number==key){
					if(payAmount == null){
						payAmount = info.getAmount();
					}else if(info.getAmount() != null){
						payAmount = payAmount.add(info.getAmount());
					}					
				}
			}
			
			//������������0
			if(payAmount!=null ){
				if(planAmount==null){
					return true;
				}else{
					return planAmount.compareTo(payAmount)<0;
				}
			}
		}
		
		return false;
	}
		//��������ظ�����ȡ����
	protected void handleIntermitNumber(Context ctx, FDCBillInfo info) throws BOSException, CodingRuleException, EASBizException
	{
		handleIntermitNumberForReset(ctx, info);
	}
	protected void _deleteForContWithoutText(Context ctx, IObjectPK[] arrayPK) throws BOSException, EASBizException {
		// TODO �Զ����ɷ������
		super._delete(ctx,arrayPK);
	}
	protected void recycleNumber(Context ctx, IObjectPK pk) throws BOSException, EASBizException, CodingRuleException {
		
	}
	protected void _reverseSave(Context ctx, IObjectPK pk, IObjectValue model)
			throws BOSException, EASBizException {
		super._reverseSave(ctx, pk, model);
		//����Ҫ��д
	}
	protected void _reverseSave(Context ctx, IObjectPK srcBillPK,
			IObjectValue srcBillVO, BOTBillOperStateEnum billOperStateEnum,
			IObjectValue relationInfo) throws BOSException, EASBizException {
		super._reverseSave(ctx, srcBillPK, srcBillVO, billOperStateEnum, relationInfo);
		//����Ҫ��д
	}
	
	private void checkConWorkLoad(Context ctx, BOSUuid billId, IObjectValue model) throws BOSException, EASBizException{
		
		PayRequestBillInfo bill = (PayRequestBillInfo)model;
		if(bill==null || bill.getCurProject()==null ||bill.getCurProject().getFullOrgUnit()==null){
			bill= this.getPayRequestBillInfo(ctx,new ObjectUuidPK(billId.toString()),getSic());
        }
		//String companyID = ContextUtil.getCurrentFIUnit(ctx).getId().toString();
		String companyID = bill.getCurProject().getFullOrgUnit().getId().toString();//ȡ������Ŀ������֯
		boolean isSeperate = FDCUtils.getDefaultFDCParamByKey(ctx, companyID, FDCConstants.FDC_PARAM_SEPARATEFROMPAYMENT);
		if(!isSeperate){
			return;
		}
		if (bill != null && bill.getContractId() != null) {
			boolean isConWithout = (new ContractBillInfo().getBOSType())
					.equals(BOSUuid.read(bill.getContractId()).getType());
			if (!isConWithout) {
				return;
			}
		}
		if(bill.getContractId()!=null){
			FilterInfo filter = new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("isCoseSplit", Boolean.valueOf(true), CompareType.NOTEQUALS));
			filter.getFilterItems().add(new FilterItemInfo("id", bill.getContractId()));
			if(ContractBillFactory.getLocalInstance(ctx).exists(filter))
				return;
		}
//		BigDecimal amount = FDCHelper.toBigDecimal(bill.getProjectPriceInContract(), 2);
//    	EntityViewInfo view = new EntityViewInfo();
//    	FilterInfo filter = new FilterInfo();
//    	view.getSelector().add("projectPriceInContract");
//    	filter.getFilterItems().add(new FilterItemInfo("contractId",bill.getContractId()));
//    	if(bill!=null&&bill.getId()!=null){
//    		filter.getFilterItems().add(new FilterItemInfo("id", bill.getId().toString(),CompareType.NOTEQUALS));
//    	}
//    	view.setFilter(filter);
//    	PayRequestBillCollection colls = PayRequestBillFactory.getLocalInstance(ctx).getPayRequestBillCollection(view);
//    	if(colls!=null){
//    		for(int i=0;i<colls.size();i++){
//    			PayRequestBillInfo info = colls.get(i);
//    			amount = amount.add(FDCHelper.toBigDecimal(info.getProjectPriceInContract()));
//    		}
//    	}
		
		//��ͬ�ڹ��̿�Ƿ�����ۼ�ȷ���깤������
		//�˴�֮ǰ�Ƚ��ң�����ǰ�Ķ�ע�ͣ�������һ��ͳһ�ж�
		//���У������Ԥ�������͵ĸ������뵥������У��
		if (bill.getPaymentType() != null
				&& bill.getPaymentType().getName().indexOf("Ԥ����") < 0) {
			BigDecimal payAmt=FDCHelper.ZERO;
			FDCSQLBuilder builder=new FDCSQLBuilder();
			builder.appendSql("select sum(t.famount) as famount from \n ");
			builder.appendSql("( \n ");
			builder.appendSql("(select FProjectPriceInContract as famount from T_Con_PayRequestBill where FHasClosed=0 and FContractId=?) \n ");
			builder.appendSql("union all\n ");
			builder.appendSql("(select pay.FProjectPriceInContract as famount from T_CAS_PaymentBill pay \n  ");
			builder.appendSql("inner join T_Con_PayRequestbill req on pay.FFdcPayReqID=req.FID \n ");
			builder.appendSql("and pay.FContractBillId=req.FContractId \n ");
			builder.appendSql("and req.FHasClosed=1 and req.FContractId=? ) \n ");
			builder.appendSql(") t \n ");//sql server �ӱ���
			builder.addParam(bill.getContractId());
			builder.addParam(bill.getContractId());
			IRowSet rs=builder.executeQuery(ctx);
			try {
				if (rs.next()) {
					// ��ǰ���еĺ�ͬ�ڹ��̿�
					payAmt = FDCHelper
							.toBigDecimal(rs.getBigDecimal("famount"));
				}
			} catch (SQLException e) {
				logger.error(e);
			}
			if (billId == null) {
				// ��ǰ������ʱ���ѵ�ǰ���ݽ��Ҳ����һ���ж�(���ڲ���Ԥ���ȡ��λ�Һ�ȡ��ͬ�ڹ��̿�һ��)
				payAmt = FDCHelper.add(payAmt, bill.getAmount());
			}
			//��ǰ��ϼƵĽ�����ۼ�ȷ�Ϲ������Ƚϣ������������ύ������
			BigDecimal workLoad = WorkLoadConfirmBillFactory.getLocalInstance(
					ctx).getWorkLoad(bill.getContractId());
			if (FDCHelper.toBigDecimal(payAmt, 2).compareTo(
					FDCHelper.toBigDecimal(workLoad, 2)) > 0) {
				throw new ContractException(
						ContractException.MORETHANCOMPLETEPRJAMT);
			}
			
			
			builder = new FDCSQLBuilder(ctx);
			builder.appendSql(" select sum(fappAmount) as appAmount from T_FNC_WorkLoadConfirmBill where ");
			builder.appendParam("FContractBillId", bill.getContractId());
			builder.appendSql(" and ");
			builder.appendParam("fstate", FDCBillStateEnum.AUDITTED_VALUE);
			rs = builder.executeQuery();
			BigDecimal appAmount = FDCHelper.ZERO;
			try {
				while(rs.next()){
					appAmount = rs.getBigDecimal("appAmount");
				}
			} catch (SQLException e) {
				e.printStackTrace();
				logger.error(e.getMessage());
			}
			if (FDCHelper.toBigDecimal(payAmt, 2).compareTo(
					FDCHelper.toBigDecimal(appAmount, 2)) > 0) {
				throw new EASBizException(new NumericExceptionSubItem("100","��ͬ�ۼ������ۼ�Ӧ���������ύ��"));
			}
		}
//		String reqId = "";
//		if(bill.getId()!=null){
//			reqId=bill.getId().toString();
//		}
//		BigDecimal payAmt=FDCHelper.ZERO;
//		FDCSQLBuilder builder=new FDCSQLBuilder();
//		builder.appendSql("select sum(t.famount) as famount  from  \n ");
//		builder.appendSql("( \n ");
//		builder.appendSql("(select FProjectPriceInContract as famount from T_Con_PayRequestBill where FHasClosed=0 and fid=? and FContractId=?) \n ");
//		builder.appendSql("union all\n ");
//		builder.appendSql("(select pay.FProjectPriceInContract as famount from T_CAS_PaymentBill pay \n  ");
//		builder.appendSql("inner join T_Con_PayRequestbill req on pay.FFdcPayReqID=req.FID \n ");
//		builder.appendSql("and pay.FContractBillId=req.FContractId \n ");
//		builder.appendSql("and req.FHasClosed=1 and req.FContractId=? ) \n ");
//		builder.appendSql(") t \n ");//sql server �ӱ���
//		builder.addParam(reqId);
//		builder.addParam(bill.getContractId());
//		builder.addParam(bill.getContractId());
//		try {
//			IRowSet rowSet=builder.executeQuery(ctx);
//			try {
//				if(rowSet.next()){
//					//NP:toBigDecimal
//					payAmt=FDCHelper.toBigDecimal(rowSet.getBigDecimal("famount"));
//				}
//			} catch (SQLException e) {
//				e.printStackTrace();
//			}
//		} catch (BOSException e) {
//			e.printStackTrace();
//		}
//		
//		if(bill.getId()!=null){
//			EntityViewInfo _view = new EntityViewInfo();
//			SelectorItemCollection selector = new SelectorItemCollection();
//			selector.add("projectPriceInContract");
//			FilterInfo _filter = new FilterInfo();
//			_filter.getFilterItems().add(new FilterItemInfo("fdcPayReqID",bill.getId().toString()));
//			_filter.getFilterItems().add(new FilterItemInfo("fdcPayType.name","%Ԥ����%",CompareType.NOTLIKE));
//			_view.setFilter(_filter);
//			_view.setSelector(selector);
//			PayRequestBillInfo _tempReqInfo = null;
//			PaymentBillInfo _tempPayInfo = null;
//			if(bill.isHasClosed()){
//				if(PaymentBillFactory.getRemoteInstance().exists(_filter)){
//					_tempPayInfo = PaymentBillFactory.getRemoteInstance().getPaymentBillCollection(_view).get(0);
//					payAmt = FDCHelper.add(payAmt, _tempPayInfo.getProjectPriceInContract());
//				}
//			}else{
//				_filter=new FilterInfo();
//				_filter.getFilterItems().add(new FilterItemInfo("id",bill.getId().toString()));
//				_filter.getFilterItems().add(new FilterItemInfo("paymentType.name","%Ԥ����%",CompareType.NOTLIKE));
//				_view.setFilter(_filter);
//				if(PayRequestBillFactory.getLocalInstance(ctx).exists(_filter)){
//					_tempReqInfo = PayRequestBillFactory.getLocalInstance(ctx).getPayRequestBillCollection(_view).get(0);
//					payAmt = FDCHelper.add(payAmt, _tempReqInfo.getProjectPriceInContract());
//				}
//			}
//		}
//		
//    	BigDecimal workLoad = WorkLoadConfirmBillFactory.getLocalInstance(ctx).getWorkLoad(bill.getContractId());
//    	if(FDCHelper.toBigDecimal(payAmt,2).compareTo(FDCHelper.toBigDecimal(workLoad,2)) > 0){
//    		throw new ContractException(ContractException.MORETHANCOMPLETEPRJAMT);
//    	}
    }
	
	/**
	 * �жϸ������뵥�Ƿ�����Ԥ����ҵ�������������ĸ��������κ�һ�����򷵻��ǣ�����Ԥ����ҵ�񣻷����򷵻ط񣬲�����Ԥ����ҵ��
	 * һ��	�����¶ȸ���ƻ��걨���У���ǩ����ͬ����û�б��ƻ����ȼƻ���Ϊ0ʱ ����ͬԤ����ҵ��
	 * ����	�����¶ȸ���ƻ��걨���У���ͬδǩ��������ǩ�������ֲ��Ǳ��е�δǩ����ͬ����Ҫ���ʱ����ͬԤ����ҵ��
	 * ����	�����¶ȸ���ƻ��걨���У���ǩ����ͬ�걨�˵������ƻ�������ʵ���������ƻ���ʱ����ͬԤ����ҵ��
	 * �ġ�	�����¶ȸ���ƻ��걨���У�δǩ����ͬ�걨�˵������ƻ�������ʵ�����ʱ�����˸�δǩ����ͬ�������ƻ�������ͬԤ����ҵ��
	 * 
	 * ֻУ�鱾�������뱾�ڼƻ� by hpw 2009-08-24
	 * 
	 * @throws BOSException,EASBizException
	 */
	protected boolean _isOutBudget(Context ctx, IObjectPK pk) throws BOSException, EASBizException {
        SelectorItemCollection sic = new SelectorItemCollection();
		sic.add(new SelectorItemInfo("contractId"));
		sic.add(new SelectorItemInfo("amount"));
		sic.add(new SelectorItemInfo("payDate"));
		sic.add(new SelectorItemInfo("id"));
		PayRequestBillInfo model = this.getPayRequestBillInfo(ctx,
				new ObjectUuidPK(pk.toString()), sic);
		// �����������ۼ�
		BigDecimal allAmount = FDCHelper.ZERO;
		// ����Ԥ���Ŀ�����ۼ�
		BigDecimal allPlanAmt = null;
		Date payDate = model.getPayDate();//ʱС�� ȡ��������
    	
    	EntityViewInfo view = new EntityViewInfo();
    	view.getSelector().add("amount");
    	view.getSelector().add("state");
    	view.getSelector().add("payDate");
    	FilterInfo filter = new FilterInfo();
    	filter.getFilterItems().add(new FilterItemInfo("contractId", model.getContractId()));
//    	filter.getFilterItems().add(new FilterItemInfo("createTime", model.getCreateTime(), CompareType.LESS_EQUALS));
    	filter.getFilterItems().add(new FilterItemInfo("state", FDCBillStateEnum.SAVED_VALUE, CompareType.NOTEQUALS));
    	// ����������,���������浥����ȡ��ǰ������������,�Ա����������޸�ʱ���ݴ���
    	if(model.getId() != null){
    		filter.getFilterItems().add(new FilterItemInfo("id", model.getId().toString(), CompareType.NOTEQUALS));
    	}
    	view.setFilter(filter);
    	PayRequestBillCollection payReqColl = null;
		try {
			payReqColl = PayRequestBillFactory.getLocalInstance(ctx).getPayRequestBillCollection(view);
		} catch (BOSException e) {
			// TODO �Զ����� catch ��
			e.printStackTrace();
		}
    	
    	if(payReqColl != null){
    		for(int i=0;i<payReqColl.size();i++){
    			PayRequestBillInfo info = payReqColl.get(i);
    			if(info.getPayDate().getYear()==payDate.getYear()&&info.getPayDate().getMonth()==payDate.getMonth()){
    				allAmount = allAmount.add(FDCHelper.toBigDecimal(info.getAmount()));
    			}
    		}
    	}
    	allAmount = FDCHelper.add(allAmount, model.getAmount()).setScale(2, BigDecimal.ROUND_HALF_UP);
    	
		FDCBudgetPeriodInfo period = FDCBudgetPeriodInfo.getPeriod(payDate,
				false);
		Map dataMap = FDCBudgetAcctFacadeFactory.getLocalInstance(ctx)
				.getAssociateAcctPay(pk.toString(), period);
		if (dataMap != null && dataMap.get("splitEntrys") != null) {
			ContractCostSplitEntryCollection splitEntrys = (ContractCostSplitEntryCollection) dataMap
					.get("splitEntrys");
			for (Iterator iter = splitEntrys.iterator(); iter.hasNext();) {
				ContractCostSplitEntryInfo entry = (ContractCostSplitEntryInfo) iter
						.next();
				BigDecimal planAmt = entry.getBigDecimal("planAmt");
				if (allPlanAmt == null) {
					allPlanAmt = planAmt;
				} else if (planAmt != null) {
					allPlanAmt = allPlanAmt.add(planAmt);
				}
			}
			if (allAmount.compareTo(FDCHelper.toBigDecimal(allPlanAmt, 2)) >0) {
				return true;
			}
		}
		return false;
	}
	protected BOSUuid _getPaymentBillId(Context ctx)
			throws BOSException {
		// TODO Auto-generated method stub
		return payBillId;
	}
	protected BOSUuid _auditAndOpenPayment(Context ctx, BOSUuid billId)
			throws BOSException, EASBizException {
//		try {
			_audit(ctx, billId);
//		} catch (EASBizException e) {
			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		return payBillId;
	}
	protected void _setUnAudited2Auditing(Context ctx, BOSUuid billId)
			throws BOSException, EASBizException {
		IBOSObject object = BOSObjectFactory.createBOSObject(ctx,billId.getType());
		IFDCBill iBillBase = (IFDCBill) object;
		iBillBase.setAudittingStatus(billId);
		
		FDCBillInfo billInfo = new FDCBillInfo();
		billInfo.setId(billId);
		billInfo.setState(FDCBillStateEnum.AUDITTING);
		
		SelectorItemCollection selector = new SelectorItemCollection();
		selector.add("state");
		iBillBase.updatePartial(billInfo,selector);
	}
	protected BigDecimal getAccActOnLoadBgAmount(Context ctx,String bgItemNumber,PayRequestBillInfo info) throws BOSException, SQLException {
		String bgComItem = "";
		Set bgComItemSet = new HashSet();
		bgComItemSet.add(bgItemNumber);
		FDCSQLBuilder _builder = new FDCSQLBuilder(ctx);
		_builder.appendSql(" select distinct t2.fformula fformula from T_BG_BgTemplateCtrlSetting t1 left join T_BG_BgTemplateCtrlSetting t2 on t1.fgroupno=t2.fgroupno where t1.FOrgUnitId ='"
				+ info.getCostedDept().getId().toString() + "'");
		_builder.appendSql(" and t1.fgroupno!='-1' and t2.fgroupno!='-1' and t1.fformula like '%" + bgItemNumber + "%' and t2.fformula not like '%" + bgItemNumber + "%'");
		IRowSet rowSet = _builder.executeQuery();

		while (rowSet.next()) {
			if (rowSet.getString("fformula") != null) {
				String formula = rowSet.getString("fformula");
				bgComItemSet.add(formula.substring(9, formula.indexOf(",") - 1));
			}
		}
		Object[] bgObject = bgComItemSet.toArray();
		for (int i = 0; i < bgObject.length; i++) {
			if (i == 0) {
				bgComItem = bgComItem + "'" + bgObject[i].toString() + "'";
			} else {
				bgComItem = bgComItem + ",'" + bgObject[i].toString() + "'";
			}
		}

		_builder = new FDCSQLBuilder(ctx);
		_builder.appendSql(" select sum(entry.frequestAmount-isnull(entry.factPayAmount,0))payAmount from T_CON_PayRequestBill bill left join T_CON_PayRequestBillBgEntry entry on entry.fheadid=bill.fid ");
		_builder.appendSql(" left join T_BG_BgItem bgItem on bgItem.fid=entry.fbgitemid ");
		_builder.appendSql(" where bill.fisBgControl=1 and bill.FCostedDeptId='" + info.getCostedDept().getId().toString() + "' and bgItem.fnumber in(" + bgComItem+")");
		_builder.appendSql(" and bill.fstate in('3AUDITTING','4AUDITTED') ");
		_builder.appendSql(" and bill.fhasClosed=0 and bill.famount is not null");

		if (info.getId() != null) {
			_builder.appendSql(" and bill.fid!='" + info.getId().toString() + "'");
		}
		rowSet = _builder.executeQuery();
		while (rowSet.next()) {
			if (rowSet.getBigDecimal("payAmount") != null)
				return rowSet.getBigDecimal("payAmount");
		}
		return FDCHelper.ZERO;
	}
	protected PaymentBillInfo createTempPaymentBill(PayRequestBillInfo info) throws BOSException {
		PaymentBillInfo pay = new PaymentBillInfo();
		for (int i = 0; i < info.getBgEntry().size(); i++) {
			PaymentBillEntryInfo entry = new PaymentBillEntryInfo();
			BigDecimal requestAmount = info.getBgEntry().get(i).getRequestAmount();

			entry.setAmount(requestAmount);
			entry.setLocalAmt(requestAmount);
			entry.setActualAmt(requestAmount);
			entry.setActualLocAmt(requestAmount);
			entry.setCurrency(info.getCurrency());
			entry.setExpenseType(info.getBgEntry().get(i).getExpenseType());
			entry.setCostCenter(info.getCostedDept());
			pay.getEntries().add(entry);
		}
		pay.setCompany(info.getCostedCompany());
		pay.setCostCenter(info.getCostedDept());
		pay.setPayDate(new Date());
		pay.setCurrency(info.getCurrency());

		return pay;
	}
	protected boolean _bgPass(Context ctx, BOSUuid id) throws BOSException, EASBizException {
		BOSObjectType  payrequestType=new PayRequestBillInfo().getBOSType();
		BOSObjectType  contractWTType=new ContractWithoutTextInfo().getBOSType();
		
        SelectorItemCollection sic = new SelectorItemCollection();
        sic.add(new SelectorItemInfo("bgEntry.*"));
        sic.add(new SelectorItemInfo("bgEntry.bgItem.*"));
        sic.add(new SelectorItemInfo("bgEntry.expenseType.*"));
        sic.add(new SelectorItemInfo("costedDept.*"));
        sic.add(new SelectorItemInfo("costedCompany.*"));
        sic.add(new SelectorItemInfo("currency.*"));
        PayRequestBillInfo payRequestInfo = null;
        	
		if(id.getType().equals(payrequestType)){
			payRequestInfo=this.getPayRequestBillInfo(ctx, new ObjectUuidPK(id),sic);
		}else if(id.getType().equals(contractWTType)){
			EntityViewInfo view=new EntityViewInfo();
			FilterInfo filter=new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("contractId",id.toString()));
			view.setFilter(filter);
			view.setSelector(sic);
			PayRequestBillCollection col=this.getPayRequestBillCollection(ctx,view);
			if(col.size()==1){
				payRequestInfo=col.get(0);
			}else{
				return false;
			}
		}
		IBgControlFacade iBgControlFacade = BgControlFacadeFactory.getLocalInstance(ctx);
		BgCtrlResultCollection coll = iBgControlFacade.getBudget("com.kingdee.eas.fi.cas.app.PaymentBill", new BgCtrlParamCollection(), createTempPaymentBill(payRequestInfo));
		Map bgItemMap = new HashMap();
		boolean isWarning = true;
		for (int i = 0; i < payRequestInfo.getBgEntry().size(); i++) {
			PayRequestBillBgEntryInfo entry=payRequestInfo.getBgEntry().get(i);

			BgItemInfo bgItem = entry.getBgItem();
			for (int j = 0; j < coll.size(); j++) {
				if (bgItem.getNumber().equals(coll.get(j).getItemCombinNumber())) {
					if (BgCtrlTypeEnum.NoCtrl.equals(coll.get(j).getCtrlType())) {
						break;
					}
					BigDecimal balanceAmount = FDCHelper.ZERO;
					BigDecimal requestAmount = entry.getRequestAmount();
					if (coll.get(j).getBalance() != null) {
						balanceAmount = coll.get(j).getBalance();
					}
					if (bgItemMap.containsKey(bgItem.getNumber())) {
						BigDecimal sumAmount = (BigDecimal) bgItemMap.get(bgItem.getNumber());
						balanceAmount = balanceAmount.subtract(sumAmount);
						bgItemMap.put(bgItem.getNumber(), sumAmount.add(requestAmount));
					} else {
						bgItemMap.put(bgItem.getNumber(), requestAmount);
					}
					BigDecimal balance=FDCHelper.ZERO;
					try {
						balance = balanceAmount.subtract(getAccActOnLoadBgAmount(ctx,bgItem.getNumber(),payRequestInfo));
					} catch (SQLException e) {
						e.printStackTrace();
					}
					if (requestAmount.compareTo(balance) > 0) {
						return false;
					}
				}
			}
		}
		return true;
	}
	
	
}
