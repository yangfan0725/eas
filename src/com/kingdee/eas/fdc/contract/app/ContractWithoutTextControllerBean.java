package com.kingdee.eas.fdc.contract.app;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.sql.RowSet;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.axis.client.Call;
import org.apache.axis.client.Service;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.ICommonBOSType;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.dao.ormapping.impl.ImplUtils;
import com.kingdee.bos.metadata.IMetaDataLoader;
import com.kingdee.bos.metadata.MetaDataLoaderFactory;
import com.kingdee.bos.metadata.entity.EntityObjectInfo;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.UIRuleUtil;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.bos.workflow.ProcessInstInfo;
import com.kingdee.bos.workflow.service.ormrpc.EnactmentServiceFactory;
import com.kingdee.bos.workflow.service.ormrpc.IEnactmentService;
import com.kingdee.eas.base.attachment.AttachmentFactory;
import com.kingdee.eas.base.attachment.AttachmentInfo;
import com.kingdee.eas.base.attachment.AttachmentStorageTypeEnum;
import com.kingdee.eas.base.attachment.BoAttchAssoCollection;
import com.kingdee.eas.base.attachment.BoAttchAssoFactory;
import com.kingdee.eas.base.codingrule.CodingRuleException;
import com.kingdee.eas.base.log.LogUtil;
import com.kingdee.eas.base.permission.UserFactory;
import com.kingdee.eas.base.permission.UserInfo;
import com.kingdee.eas.basedata.assistant.PeriodInfo;
import com.kingdee.eas.basedata.master.cssp.SupplierFactory;
import com.kingdee.eas.basedata.master.cssp.SupplierInfo;
import com.kingdee.eas.basedata.org.CostCenterOrgUnitInfo;
import com.kingdee.eas.basedata.person.PersonFactory;
import com.kingdee.eas.basedata.person.PersonInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.cp.bc.ExpenseTypeFactory;
import com.kingdee.eas.cp.bc.ExpenseTypeInfo;
import com.kingdee.eas.fdc.basedata.ChangeTypeFactory;
import com.kingdee.eas.fdc.basedata.ChangeTypeInfo;
import com.kingdee.eas.fdc.basedata.ContractTypeFactory;
import com.kingdee.eas.fdc.basedata.ContractTypeInfo;
import com.kingdee.eas.fdc.basedata.ContractTypeOrgTypeEnum;
import com.kingdee.eas.fdc.basedata.CostAccountFactory;
import com.kingdee.eas.fdc.basedata.CostAccountInfo;
import com.kingdee.eas.fdc.basedata.CostAccountYJTypeEnum;
import com.kingdee.eas.fdc.basedata.CurProjectFactory;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.fdc.basedata.FDCBillInfo;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCCommonServerHelper;
import com.kingdee.eas.fdc.basedata.FDCConstants;
import com.kingdee.eas.fdc.basedata.FDCDateHelper;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.contract.ChangeAuditBillFactory;
import com.kingdee.eas.fdc.contract.ChangeAuditBillInfo;
import com.kingdee.eas.fdc.contract.ChangeBillStateEnum;
import com.kingdee.eas.fdc.contract.ContractBaseDataFactory;
import com.kingdee.eas.fdc.contract.ContractBillCollection;
import com.kingdee.eas.fdc.contract.ContractBillEntryInfo;
import com.kingdee.eas.fdc.contract.ContractBillFactory;
import com.kingdee.eas.fdc.contract.ContractBillInfo;
import com.kingdee.eas.fdc.contract.ContractEstimateChangeBillCollection;
import com.kingdee.eas.fdc.contract.ContractEstimateChangeBillFactory;
import com.kingdee.eas.fdc.contract.ContractEstimateChangeBillInfo;
import com.kingdee.eas.fdc.contract.ContractException;
import com.kingdee.eas.fdc.contract.ContractExecInfosFactory;
import com.kingdee.eas.fdc.contract.ContractExecInfosInfo;
import com.kingdee.eas.fdc.contract.ContractPCSplitBillEntryCollection;
import com.kingdee.eas.fdc.contract.ContractPCSplitBillEntryFactory;
import com.kingdee.eas.fdc.contract.ContractWTInvoiceEntryCollection;
import com.kingdee.eas.fdc.contract.ContractWTInvoiceEntryFactory;
import com.kingdee.eas.fdc.contract.ContractWTInvoiceEntryInfo;
import com.kingdee.eas.fdc.contract.ContractWithProgramInfo;
import com.kingdee.eas.fdc.contract.ContractWithoutTextCollection;
import com.kingdee.eas.fdc.contract.ContractWithoutTextFactory;
import com.kingdee.eas.fdc.contract.ContractWithoutTextInfo;
import com.kingdee.eas.fdc.contract.ContractYZEntryCollection;
import com.kingdee.eas.fdc.contract.ContractYZEntryFactory;
import com.kingdee.eas.fdc.contract.ContractYZEntryInfo;
import com.kingdee.eas.fdc.contract.FDCUtils;
import com.kingdee.eas.fdc.contract.IPayRequestBill;
import com.kingdee.eas.fdc.contract.MarketProjectFactory;
import com.kingdee.eas.fdc.contract.MarketProjectInfo;
import com.kingdee.eas.fdc.contract.PayContentTypeFactory;
import com.kingdee.eas.fdc.contract.PayContentTypeInfo;
import com.kingdee.eas.fdc.contract.PayReqInvoiceEntryCollection;
import com.kingdee.eas.fdc.contract.PayReqInvoiceEntryFactory;
import com.kingdee.eas.fdc.contract.PayReqInvoiceEntryInfo;
import com.kingdee.eas.fdc.contract.PayRequestBillCollection;
import com.kingdee.eas.fdc.contract.PayRequestBillEntryFactory;
import com.kingdee.eas.fdc.contract.PayRequestBillFactory;
import com.kingdee.eas.fdc.contract.PayRequestBillInfo;
import com.kingdee.eas.fdc.contract.programming.IProgrammingContract;
import com.kingdee.eas.fdc.contract.programming.ProgrammingContractFactory;
import com.kingdee.eas.fdc.contract.programming.ProgrammingContractInfo;
import com.kingdee.eas.fdc.finance.CostClosePeriodFacadeFactory;
import com.kingdee.eas.fdc.finance.ProjectPeriodStatusException;
import com.kingdee.eas.fdc.finance.app.ProjectPeriodStatusUtil;
import com.kingdee.eas.fi.cas.PaymentBillEntryInfo;
import com.kingdee.eas.fi.cas.PaymentBillFactory;
import com.kingdee.eas.fi.cas.PaymentBillInfo;
import com.kingdee.eas.ma.budget.BgBudgetFacadeFactory;
import com.kingdee.eas.ma.budget.BgControlFacadeFactory;
import com.kingdee.eas.ma.budget.BgCtrlParamCollection;
import com.kingdee.eas.ma.budget.BgCtrlResultCollection;
import com.kingdee.eas.ma.budget.BgCtrlTypeEnum;
import com.kingdee.eas.ma.budget.BgItemInfo;
import com.kingdee.eas.ma.budget.IBgBudgetFacade;
import com.kingdee.eas.ma.budget.IBgControlFacade;
import com.kingdee.eas.util.app.ContextUtil;
import com.kingdee.eas.util.app.DbUtil;
import com.kingdee.jdbc.rowset.IRowSet;
import com.kingdee.util.NumericExceptionSubItem;
import com.kingdee.util.db.SQLUtils;

/**
 * 
 * ����:���ı���ͬ
 * 
 * @author liupd date:2006-10-13
 *         <p>
 * @version EAS5.1.3
 */
public class ContractWithoutTextControllerBean extends
		AbstractContractWithoutTextControllerBean {
	private static Logger logger = Logger
			.getLogger("com.kingdee.eas.fdc.contract.app.ContractWithoutTextControllerBean");
	// �������뵥����
	private String payReqNumber = new String("");

	// ����
	protected IObjectPK _save(Context ctx, IObjectValue model)
			throws BOSException, EASBizException {
		FDCBillInfo con = ((FDCBillInfo) model);
		PayRequestBillInfo prbi = (PayRequestBillInfo) con.get("PayRequestBillInfo");
		con.setAmount(FDCHelper.multiply(con.getOriginalAmount(), prbi.getExchangeRate()));
		prbi.setAmount(con.getAmount());
		IObjectPK pk = super._save(ctx, con);
		con.setId(BOSUuid.read(pk.toString()));
		createPayRequestBill(ctx, con, prbi, FDCBillStateEnum.SAVED);
		return pk;

	}
	protected void _save(Context ctx, IObjectPK pk, IObjectValue model) throws BOSException, EASBizException {
		FDCBillInfo con = ((FDCBillInfo) model);
		PayRequestBillInfo prbi = (PayRequestBillInfo) con.get("PayRequestBillInfo");
		con.setAmount(FDCHelper.multiply(con.getOriginalAmount(), prbi.getExchangeRate()));
		prbi.setAmount(con.getAmount());
		super._save(ctx, pk, model);
		con.setId(BOSUuid.read(pk.toString()));
		createPayRequestBill(ctx, con, prbi, FDCBillStateEnum.SAVED);
	}

	protected void _submit(Context ctx, IObjectPK pk, IObjectValue model) throws BOSException, EASBizException {
		FDCBillInfo con = ((FDCBillInfo) model);
		checkBillForSubmit(ctx, con);
		PayRequestBillInfo prbi = (PayRequestBillInfo) con.get("PayRequestBillInfo");
		con.setAmount(FDCHelper.multiply(con.getOriginalAmount(), prbi.getExchangeRate()));
		prbi.setAmount(con.getAmount());
		super._submit(ctx, pk, model);
		con.setId(BOSUuid.read(pk.toString()));
		createPayRequestBill(ctx, con, prbi, FDCBillStateEnum.SUBMITTED);
		
		if(model.get("fromweb")==null){
			sendtoOA(ctx, (ContractWithoutTextInfo) model);
		}
		updateMKFP(ctx, (ContractWithoutTextInfo) model);
	}

	protected IObjectPK _submit(Context ctx, IObjectValue model)throws BOSException, EASBizException {
		FDCBillInfo con = ((FDCBillInfo) model);
		checkBillForSubmit(ctx, con);
		PayRequestBillInfo prbi = (PayRequestBillInfo) con.get("PayRequestBillInfo");
		con.setAmount(FDCHelper.multiply(con.getOriginalAmount(), prbi.getExchangeRate()));
		prbi.setAmount(con.getAmount());
		IObjectPK pk = super._submit(ctx, con);
		con.setId(BOSUuid.read(pk.toString()));
		createPayRequestBill(ctx, con, prbi, FDCBillStateEnum.SUBMITTED);
		
		if(model.get("fromweb")==null){
			sendtoOA(ctx, (ContractWithoutTextInfo) model);
		}
		updateMKFP(ctx, (ContractWithoutTextInfo) model);
		return pk;
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
	
	public void updateMKFP(Context ctx,ContractWithoutTextInfo info) throws EASBizException, BOSException{
		ContractWTInvoiceEntryCollection fpEntrys = info.getInvoiceEntry();
		if(fpEntrys.size()>0){
			for (int x=0;x<fpEntrys.size();x++) {
				ContractWTInvoiceEntryInfo fp =ContractWTInvoiceEntryFactory.getLocalInstance(ctx).getContractWTInvoiceEntryInfo(new ObjectUuidPK(info.getInvoiceEntry().get(x).getId()));
				Integer use = fp.getIsMKUsed();
			
				String invoiceNumber = fp.getInvoiceNumber();
				if("".equals(use)||use==1){
					throw new EASBizException(new NumericExceptionSubItem("100","��Ʊ��Ϊ"+invoiceNumber+"�ķ�Ʊ�ѱ�ʹ��,������ѡ��Ʊ��"));
				}
//				��ȡÿ�̷�Ʊ���� �޸Ľ����Ʊ״̬Ϊ����
				String number = fp.getInvoiceNumber();
				String viewLink = getMKLink(ctx,number);
				if(viewLink!=null&&viewLink!=""){
					fp.setViewLink(viewLink);
					fp.setIsMKUsed(1);
					SelectorItemCollection sic=new SelectorItemCollection();
					sic.add("isMkUsed");
					sic.add("viewLink");
					ContractWTInvoiceEntryFactory.getLocalInstance(ctx).updatePartial(fp,sic);
				}
			}
		}
	}
	public String getMKLink(Context ctx,String number) throws EASBizException, BOSException{
		String viewLink=null;
		com.alibaba.fastjson.JSONObject login=new com.alibaba.fastjson.JSONObject();
		String tokenId=null;
		String entCode=null;
		String appCode=null;
		String secret=null;
		String mturl=null;
		Timestamp ts = new Timestamp(System.currentTimeMillis());
		long lt = ts.getTime();
		try {
			FDCSQLBuilder builder=new FDCSQLBuilder(ctx);
			builder.appendSql("select * from dl_mk where type='da'");
			IRowSet rs=builder.executeQuery();
			while(rs.next()){
				mturl=rs.getString("url");
				entCode=rs.getString("entCode");
				appCode=rs.getString("appCode");
				secret=rs.getString("secret");
			}
			login.put("userCode", appCode);
			login.put("secretToken", SHA(secret+":"+appCode+":"+lt));
			login.put("timestamp", lt);
			
			HttpClient httpClient =new HttpClient();
    		PostMethod post = new PostMethod(mturl+"/open/auth/login");
    		post.setRequestHeader("Content-Type", "application/json;charse=UTF-8") ;
    		post.setRequestBody(login.toJSONString());
    		httpClient.executeMethod(post);
    		
    		String respStr = post.getResponseBodyAsString();
            post.releaseConnection();
			
            com.alibaba.fastjson.JSONObject crso = com.alibaba.fastjson.JSONObject.parseObject(respStr);
            com.alibaba.fastjson.JSONObject d=crso.getJSONObject("data");
			if(200!=(crso.getInteger("code"))){
				throw new EASBizException(new NumericExceptionSubItem("100",crso.getString("message")));
			}else{
				tokenId=d.getString("token");
			}
		} catch (Exception e) {
			try {
				throw new EASBizException(new NumericExceptionSubItem("100",e.getMessage()));
			} catch (EASBizException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		
		com.alibaba.fastjson.JSONObject dataJson=new com.alibaba.fastjson.JSONObject();
//		��ȡ��Ʊ�鿴����
		try {
			dataJson.put("invoiceNumber", number);
//			dataJson.put("invoiceCode", "3200211130");
			HttpClient httpClient =new HttpClient();
    		PostMethod post = new PostMethod(mturl+"/open/ecm-invoice/search");
			post.setRequestHeader("Content-Type", "application/json;charse=UTF-8") ;
			post.setRequestHeader("Authorization", tokenId) ;
    		post.setRequestBody(dataJson.toJSONString());
    		httpClient.executeMethod(post);
    		
    		String respStr = post.getResponseBodyAsString();
            post.releaseConnection();
            com.alibaba.fastjson.JSONObject crso = com.alibaba.fastjson.JSONObject.parseObject(respStr);
            com.alibaba.fastjson.JSONObject data = crso.getJSONObject("data");
			com.alibaba.fastjson.JSONArray pageDataArray=data.getJSONArray("pageData");
			
			if(pageDataArray.size()>0){
				com.alibaba.fastjson.JSONObject view = pageDataArray.getJSONObject(0);
				if(view!=null){
					com.alibaba.fastjson.JSONArray fileUrls = view.getJSONArray("fileUrls");
					if(fileUrls!=null&&fileUrls.size()>0){
						String fileUrl = fileUrls.getString(0);
						if(fileUrl!=null&&fileUrl!=""){
//							��VIEWURL��ֵ
							viewLink = new StringBuffer().append("http://172.17.4.52:8080").append(fileUrl).toString();
						}
					}
				}
			}
		} catch (Exception e) {
			throw new EASBizException(new NumericExceptionSubItem("100",e.getMessage()));
			// TODO Auto-generated catch block
			
		}
		return viewLink;
	}
	
	public void sendtoOA(Context ctx,ContractWithoutTextInfo info) throws EASBizException{
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
				info=this.getContractWithoutTextInfo(ctx, new ObjectUuidPK(info.getId()));
				
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
//				builder.clear();
//				builder.appendSql("select TID from oatemplate where TYPE='notext'");
//				IRowSet tidrs = builder.executeQuery();
//				String tmplateId=null;
//				while(tidrs.next()){
//					tmplateId=tidrs.getString("TID");
//				}
//				json.put("tmplateId", tmplateId);
				
				PayContentTypeInfo pc=PayContentTypeFactory.getLocalInstance(ctx).getPayContentTypeInfo(new ObjectUuidPK(info.getPayContentType().getId()));
				if(pc.getWtOaTId()==null){
					throw new EASBizException(new NumericExceptionSubItem("100","��������δά����ӦOAģ��ID��"));
				}else{
					json.put("tmplateId", pc.getWtOaTId());
				}
				json.put("fdType", "03");
				json.put("docSubject", info.getName());

				json.put("loginName", u.getNumber());
				
				if(info.getReceiveUnit()!=null){
					SupplierInfo Sup=SupplierFactory.getLocalInstance(ctx).getSupplierInfo(new ObjectUuidPK(info.getReceiveUnit().getId()));
					json.put("fdCompanyName", Sup.getName());
				}else if(info.getPerson()!=null){
					PersonInfo Sup=PersonFactory.getLocalInstance(ctx).getPersonInfo(new ObjectUuidPK(info.getPerson().getId()));
					json.put("fdCompanyName", Sup.getName());
				}
				
				JSONObject obj = new JSONObject();
				if(info.getMpCostAccount()!=null&&info.getMarketProject()!=null){
					CostAccountInfo ca=CostAccountFactory.getLocalInstance(ctx).getCostAccountInfo(new ObjectUuidPK(info.getMpCostAccount().getId()));
					MarketProjectInfo market=MarketProjectFactory.getLocalInstance(ctx).getMarketProjectInfo(new ObjectUuidPK(info.getMarketProject().getId()));
					if(ca.getYjType()!=null&&ca.getYjType().equals(CostAccountYJTypeEnum.FYJ)&&market.getAuditTime()!=null){
						Calendar cal = new GregorianCalendar();
						cal.setTime(FDCDateHelper.getNextMonth(market.getAuditTime()));
						cal.set(5, 15);
						
						int day=FDCDateHelper.getDiffDays(cal.getTime(), new Date());
						if(day<1){
							obj.put("fd_timeout", "��");
						}
					}
				}
				if(info.getPayContentType()!=null){
					PayContentTypeInfo pct=PayContentTypeFactory.getLocalInstance(ctx).getPayContentTypeInfo(new ObjectUuidPK(info.getPayContentType().getId()));
					obj.put("fd_38cf18383073ec", pct.getName());
				}
				if(info.getContractType()!=null){
					ContractTypeInfo ct=ContractTypeFactory.getLocalInstance(ctx).getContractTypeInfo(new ObjectUuidPK(info.getContractType().getId()));
					obj.put("fd_38cf183640fe40", ct.getName());
				}
				obj.put("fd_38cf1871775004", info.getAmount().doubleValue());
				if(info.isIsJT()){
					obj.put("fd_38f672e9da3dda", "��");
				}else{
					obj.put("fd_38f672e9da3dda", "��");
				}
				
				if(info.getOrgType().equals(ContractTypeOrgTypeEnum.NEIBU)){
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
				
//				webҳ��鿴·�� �����߹���ɽ��ˮ�� ����һ�����
				builder.clear();
				builder.appendSql("select url from weburl where type='view'");
				IRowSet rsv = builder.executeQuery();
				String sendUrl=null;
				while(rsv.next()){
//					http://172.17.4.125:8082/easWeb/#/
					StringBuffer sbv = new StringBuffer();
					String appendUrl = rsv.getString("url");
//					������ approval
					String appendType="notext/view?from=oa&id=";
//					id
					String idv = info.getId().toString();
					String appendId = URLEncoder.encode(idv, "utf-8");
//					token
					String token = "&token=";
					sendUrl = String.valueOf(sbv.append(appendUrl).append(appendType).append(appendId).append(token));
					System.out.println(sendUrl);
				}
				json.put("fdPcViewLink", sendUrl);

//				app�鿴��ַ
				String sendAppUrl = null;
				StringBuffer sba = new StringBuffer();
				builder.clear();
				builder.appendSql("select url from weburl where type ='app'");
				IRowSet rsapp = builder.executeQuery();
				while(rsapp.next()){
//					http://172.17.4.125:8082/easWeb/#/
					String appendUrl = rsapp.getString("url");
//					������ approval
					String appendType="notext/?from=oa&id=";
//					id
					String idv = info.getId().toString();
					String appendId = URLEncoder.encode(idv, "utf-8");
//					token
					String token = "&token=";
					sendAppUrl = String.valueOf(sba.append(appendUrl).append(appendType).append(appendId).append(token));
					System.out.println(sendUrl);
				}
				json.put("fdMobileViewLink", sendAppUrl);
				
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
		                attObj.put("fileType", "03");
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
					ContractWithoutTextFactory.getLocalInstance(ctx).updatePartial(info,sic);
					
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
	public void deleteOA(Context ctx,ContractWithoutTextInfo info) throws EASBizException{
		if(info.getSourceFunction()!=null){
    		throw new EASBizException(new NumericExceptionSubItem("100","�Ѵ���OA���̣���ֹɾ����"));
    	}
//    	try {
//			FDCSQLBuilder builder=new FDCSQLBuilder(ctx);
//			builder.appendSql("select furl from t_oa");
//			IRowSet rs=builder.executeQuery();
//			String url=null;
//			while(rs.next()){
//				url=rs.getString("furl");
//			}
//			if(url!=null){
//				Service s=new Service();
//				Call call=(Call)s.createCall();
//				
//				call.setTargetEndpointAddress(new java.net.URL(url));
//	            call.addParameter("arg0", org.apache.axis.encoding.XMLType.XSD_STRING, javax.xml.rpc.ParameterMode.IN);
//	            call.setReturnType(org.apache.axis.encoding.XMLType.XSD_STRING);
//	            call.setUseSOAPAction(true);
//				call.setTimeout(Integer.valueOf(1000*600000*60));
//		        call.setMaintainSession(true);
//				call.setOperationName("deleteEkpFlow");
//		        String result=(String)call.invoke(new Object[]{info.getSourceFunction()} );
//		        JSONObject rso = JSONObject.fromObject(result);
//		        if(!rso.getString("code").equals("1")){
//		        	throw new EASBizException(new NumericExceptionSubItem("100",rso.getString("massage")));
//		        }
//			}
//		} catch (SQLException e) {
//			e.printStackTrace();
//		} catch (Exception e) {
//			throw new EASBizException(new NumericExceptionSubItem("100",e.getMessage()));
//		}
    }
	protected void trimName(FDCBillInfo fDCBillInfo) {
		super.trimName(fDCBillInfo);
	}

	// ���
	protected void _audit(Context ctx, BOSUuid billId) throws BOSException,
			EASBizException {

		checkBillForAudit(ctx, billId, null);

		super._audit(ctx, billId);
		try {
			synUpdateBillByRelation(ctx,billId,true,null);
		} catch (SQLException e) {
			e.printStackTrace();
			logger.warn(e);
		}
		// ͬ����Ǹ������뵥Ϊ����״̬
		EntityViewInfo evi = new EntityViewInfo();
		FilterInfo filterInfo = new FilterInfo();
		filterInfo.getFilterItems().add(
				new FilterItemInfo("contractId", billId.toString()));
		evi.getSelector().add(new SelectorItemInfo("id"));
		evi.setFilter(filterInfo);

		IPayRequestBill iPayReq = PayRequestBillFactory.getLocalInstance(ctx);
		PayRequestBillCollection prbc = iPayReq
				.getPayRequestBillCollection(evi);

		if (prbc.size() > 0) {
			iPayReq.audit(prbc.get(0).getId());
		}
		ContractExecInfosFactory.getLocalInstance(ctx).updateContract(
				ContractExecInfosInfo.EXECINFO_AUDIT, billId.toString());
	}

	
	/**
	 * 1 .����ͬδ����ʱ(�����ս�������ս���δ����)���滮���=�滮���-�����ı�ǩԼ���+��������������=���ƽ��-���ı�ǩԼ��� 2
	 * .����ͬ�ѽ���ʱ(���ս���������)���滮���=�滮���-������������=���ƽ��-������ 3
	 * .��дʱ���ں�ͬ��������ͨ��ʱ���������������ͨ��ʱ�����ָ�����ʱ����ͬ��������ͨ��ʱ�� 4.
	 * ��ͬ�޶������󣬹滮���=�滮���-���޶����ǩԼ���+��������������=���ƽ��-�޶����ǩԼ��
	 * 
	 * @param ctx
	 * @param billId
	 * @throws EASBizException
	 * @throws BOSException
	 * @throws SQLException
	 * @throws SQLException
	 */
	private void synUpdateBillByRelation(Context ctx, BOSUuid billId, boolean flag, String relateAmt) throws EASBizException, BOSException,
			SQLException {
		String billIdStr = billId.toString();
//		FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
//		IRowSet rowSet = null;
//		builder.appendSql(" select parent.fid conId from t_con_contractbillentry entry inner join t_con_contractbill con on con.fid = entry.fparentid   and con.fisAmtWithoutCost=1 and");
//		builder.appendSql(" con.fcontractPropert='SUPPLY'  inner join T_Con_contractBill parent on parent.fnumber = con.fmainContractNumber  and  parent.fcurprojectid=con.fcurprojectid   ");
//		builder.appendSql(" where  entry.FRowkey='am' and ");
//		builder.appendParam(" con.fid", billIdStr);
//		rowSet = builder.executeQuery();
//		if (rowSet.next()) {
//			billIdStr = rowSet.getString("conId").toString();
//		}
		ContractWithoutTextInfo contractWithoutTextInfo = this.getContractWithoutTextInfo(ctx, new ObjectUuidPK(billIdStr), getSic());
		if (contractWithoutTextInfo.getProgrammingContract() == null)
			return;
		IProgrammingContract service = ProgrammingContractFactory.getLocalInstance(ctx);
		ProgrammingContractInfo pcInfo = contractWithoutTextInfo.getProgrammingContract();
		if (pcInfo == null)
			return;
		// �滮���
		BigDecimal balanceAmt = pcInfo.getBalance();
		// �������
		BigDecimal controlBalanceAmt = pcInfo.getControlBalance();
		// ���ı���ͬǩԼ���
		BigDecimal signAmount = contractWithoutTextInfo.getAmount();
		// if(mainSignAmount != null){
		// if(flag){
		// signAmount = FDCHelper.subtract(signAmount, mainSignAmount);
		// }else{
		// signAmount = FDCHelper.add(signAmount, mainSignAmount);
		// }
		// }
		// ��ܺ�Լ���ı���ͬ���
		BigDecimal signUpAmount = pcInfo.getWithOutTextAmount();
		// ���
		BigDecimal otherSignUpAmount = FDCHelper.ZERO;
		if (flag) {
			if (relateAmt == null) {
				pcInfo.setBalance(FDCHelper.subtract(balanceAmt, signAmount));
				pcInfo.setControlBalance(FDCHelper.subtract(controlBalanceAmt, signAmount));
				pcInfo.setWithOutTextAmount(FDCHelper.add(signUpAmount, signAmount));
				// ά�����
				// otherBalance = FDCHelper.subtract(balanceAmt,
				// FDCHelper.subtract(balanceAmt, signAmount));
				// otherControlBalance = FDCHelper.subtract(controlBalanceAmt,
				// FDCHelper.subtract(controlBalanceAmt, signAmount));
				otherSignUpAmount = FDCHelper.subtract(FDCHelper.add(signUpAmount, signAmount), signUpAmount);
			} else {
				pcInfo.setBalance(FDCHelper.subtract(balanceAmt, relateAmt));
				pcInfo.setControlBalance(FDCHelper.subtract(controlBalanceAmt, relateAmt));
				pcInfo.setWithOutTextAmount(FDCHelper.add(signUpAmount, relateAmt));
				// ά�����
				// otherBalance = FDCHelper.subtract(balanceAmt,
				// FDCHelper.subtract(balanceAmt, relateAmt));
				// otherControlBalance = FDCHelper.subtract(controlBalanceAmt,
				// FDCHelper.subtract(controlBalanceAmt, relateAmt));
				otherSignUpAmount = FDCHelper.subtract(FDCHelper.add(signUpAmount, relateAmt), signUpAmount);
			}
			pcInfo.setBudgetAmount(FDCHelper.ZERO);
			pcInfo.setWithOutTextName(contractWithoutTextInfo.getName());
			pcInfo.setWithOutTextNumber(contractWithoutTextInfo.getNumber());
			pcInfo.setBillId(billIdStr);
		} else {
			if (relateAmt == null) {
				pcInfo.setBalance(FDCHelper.add(balanceAmt, signAmount));
				pcInfo.setControlBalance(FDCHelper.add(controlBalanceAmt, signAmount));
				pcInfo.setWithOutTextAmount(FDCHelper.subtract(signUpAmount, signAmount));
				// ά�����
				// otherBalance = FDCHelper.subtract(balanceAmt,
				// FDCHelper.add(balanceAmt, signAmount));
				// otherControlBalance = FDCHelper.subtract(controlBalanceAmt,
				// FDCHelper.add(controlBalanceAmt, signAmount));
				otherSignUpAmount = FDCHelper.subtract(FDCHelper.subtract(signUpAmount, signAmount), signUpAmount);
			} else {
				pcInfo.setBalance(FDCHelper.add(balanceAmt, relateAmt));
				pcInfo.setControlBalance(FDCHelper.add(controlBalanceAmt, relateAmt));
				pcInfo.setWithOutTextAmount(FDCHelper.subtract(signUpAmount, relateAmt));
				// ά�����
				// otherBalance = FDCHelper.subtract(balanceAmt,
				// FDCHelper.add(balanceAmt, relateAmt));
				// otherControlBalance = FDCHelper.subtract(controlBalanceAmt,
				// FDCHelper.add(controlBalanceAmt, relateAmt));
				otherSignUpAmount = FDCHelper.subtract(FDCHelper.subtract(signUpAmount, relateAmt), signUpAmount);
			}
			pcInfo.setBudgetAmount(pcInfo.getAmount());
			pcInfo.setWithOutTextName(contractWithoutTextInfo.getName());
			pcInfo.setWithOutTextNumber(contractWithoutTextInfo.getNumber());
			pcInfo.setBillId(null);
		}
		
		pcInfo.setWithOutTextName(contractWithoutTextInfo.getName());
		pcInfo.setWithOutTextNumber(contractWithoutTextInfo.getNumber());
		SelectorItemCollection sict = new SelectorItemCollection();
		sict.add("balance");
		sict.add("controlBalance");
		sict.add("withOutTextAmount");
		sict.add("withOutTextNumber");
		sict.add("withOutTextName");
		sict.add("changeAmount");
		sict.add("settleAmount");
		sict.add("srcId");
		sict.add("budgetAmount");
		sict.add("billId");
//		sict.add("estimateAmount");
//		
//		//��Ҫ�ǲ����ͬ���Ѻ�Լ�滮��Ԥ�������Ϊ�㣬
//		BigDecimal  estimateAmount = pcInfo.getEstimateAmount();
//		pcInfo.setEstimateAmount(FDCHelper.ZERO);
//		pcInfo.setBalance(pcInfo.getBalance().add(estimateAmount));
		service.updatePartial(pcInfo, sict);
		//��Ӧ��Ԥ���䶯����Ϊ�Ƿ�����Ϊfalse
//		FilterInfo filter = new FilterInfo();
//		filter.getFilterItems().add(new FilterItemInfo("programmingContract.id",pcInfo.getId().toString()));
//		filter.getFilterItems().add(new FilterItemInfo("isLastest",Boolean.TRUE));
//		EntityViewInfo view = new EntityViewInfo();
//		view.setFilter(filter);
//		ContractEstimateChangeBillCollection  coll = ContractEstimateChangeBillFactory.getLocalInstance(ctx).getContractEstimateChangeBillCollection(view);
//		if(coll.size()>0){
//			ContractEstimateChangeBillInfo contInfo = coll.get(0);
//			contInfo.setIsLastest(false);
//			SelectorItemCollection sel = new SelectorItemCollection();
//			sel.add("isLastest");
//			ContractEstimateChangeBillFactory.getLocalInstance(ctx).updatePartial(contInfo, sel);
//		}
		
		//==========================
		// ���������ĺ�Լ�滮�汾���
//		String progId = pcInfo.getId().toString();
//		while (progId != null) {
//			FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
//			IRowSet rowSet = null;
//			String nextVersionProgId = getNextVersionProg(ctx, progId, builder, rowSet);
//			if (nextVersionProgId != null) {
//				pcInfo = ProgrammingContractFactory.getLocalInstance(ctx).getProgrammingContractInfo(new ObjectUuidPK(nextVersionProgId), sict);
//				pcInfo.setBalance(FDCHelper.subtract(pcInfo.getBalance(), otherSignUpAmount));
//				pcInfo.setControlBalance(FDCHelper.subtract(pcInfo.getControlBalance(), otherSignUpAmount));
//				pcInfo.setSignUpAmount(FDCHelper.add(pcInfo.getSignUpAmount(), otherSignUpAmount));
//				service.updatePartial(pcInfo, sict);
//				progId = pcInfo.getId().toString();
//			} else {
//				progId = null;
//			}
//		}
	}
	
	private String getNextVersionProg(Context ctx, String nextProgId, FDCSQLBuilder builder, IRowSet rowSet) throws BOSException, SQLException {
		String tempId = null;
		builder.clear();
		builder.appendSql(" select fid from t_con_programmingContract where  ");
		builder.appendParam("FSrcId", nextProgId);
		rowSet = builder.executeQuery();
		while (rowSet.next()) {
			tempId = rowSet.getString("fid");
		}
		return tempId;
	}
	// �����
	protected void _unAudit(Context ctx, BOSUuid billId) throws BOSException,
			EASBizException {

		if (billId == null)
			return;
		// �ڷ�������ʱ���ж���û�и��,����в���������
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(
				new FilterItemInfo("contractBillId", billId.toString()));
		if (PaymentBillFactory.getLocalInstance(ctx).exists(filter)) {
			throw new ContractException(ContractException.HASPAYMENTBILL);
		}

		checkBillForUnAudit(ctx, billId, null);
		
		String sql = "update T_CON_PayRequestBill set fstate=?,fhasClosed=0 where fcontractid=?";
		String[] params = new String[] { FDCBillStateEnum.SUBMITTED_VALUE,
				billId.toString() };
		DbUtil.execute(ctx, sql, params);

		EntityViewInfo view = new EntityViewInfo();
		filter = new FilterInfo();
		filter.getFilterItems().add(
				new FilterItemInfo("contractId", billId.toString()));
		view.setFilter(filter);

		SelectorItemCollection selector = new SelectorItemCollection();
		selector.add("contractId");
		selector.add("curProject.id");
		selector.add("orgUnit.id");

		PayRequestBillCollection col = PayRequestBillFactory.getLocalInstance(
				ctx).getPayRequestBillCollection(view);
		for (int i = 0; i < col.size(); i++) {

			PayRequestBillInfo payRequestBillInfo = col.get(i);
			String id = col.get(i).getId().toString();
			// 20050511 ��־���ṩԤ��ӿ�
			HashMap param = FDCUtils.getDefaultFDCParam(ctx, payRequestBillInfo
					.getOrgUnit().getId().toString());
			// boolean useWorkflow = FDCUtils.isRunningWorkflow(ctx,
			// billInfo.getId().toString());
			boolean isMbgCtrl = Boolean.valueOf(
					param.get(FDCConstants.FDC_PARAM_STARTMG).toString())
					.booleanValue();
			if (isMbgCtrl) {
				IBgControlFacade iBgCtrl = BgControlFacadeFactory
						.getLocalInstance(ctx);
				iBgCtrl.cancelRequestBudget(id);
			}
		}

		super._unAudit(ctx, billId);

		try {
			synUpdateBillByRelation(ctx,billId,false,null);
		} catch (SQLException e) {
			e.printStackTrace();
			logger.warn(e);
		}
		
		// �Զ�ɾ�������½�����
		SelectorItemCollection selectors = new SelectorItemCollection();
		selectors.add("period.id");

		ContractWithoutTextInfo info = (ContractWithoutTextInfo) this.getValue(
				ctx, new ObjectUuidPK(billId), selectors);
		if (info.getPeriod() != null) {
			CostClosePeriodFacadeFactory.getLocalInstance(ctx).delete(
					info.getId().toString(),
					info.getPeriod().getId().toString());
		}
		ContractExecInfosFactory.getLocalInstance(ctx).updateContract(
				ContractExecInfosInfo.EXECINFO_UNAUDIT, billId.toString());
		
		//MK
		ContractWithoutTextInfo info1=ContractWithoutTextFactory.getLocalInstance(ctx).getContractWithoutTextInfo("select invoiceEntry.* from where id='"+billId+"'");
		if(info1!=null){
			ContractWTInvoiceEntryCollection entrys = info1.getInvoiceEntry();
			if(entrys.size()>0){
				for(int i=0;i<entrys.size();i++){
					ContractWTInvoiceEntryInfo invoiceInfo = entrys.get(i);
					invoiceInfo.setIsMKUsed(0);
					SelectorItemCollection sic=new SelectorItemCollection();
					sic.add("isMkUsed");
					ContractWTInvoiceEntryFactory.getLocalInstance(ctx).updatePartial(invoiceInfo,sic);
				}
			}
		}
	}

	// ����
	protected IObjectPK _addnew(Context ctx, IObjectValue model)
			throws BOSException, EASBizException {
		IObjectPK objectPK = super._addnew(ctx, model);

		// ͬ������ͬ�������ϣ����ں�ͬ��������Ŀ
		ContractBaseDataHelper.synToContractBaseData(ctx, true, objectPK
				.toString());

		/** ��ӷ�дContractBaseDataID�Ĵ��� -by neo */
		FDCSQLBuilder builder = new FDCSQLBuilder();
		builder
				.appendSql("update t_con_contractwithouttext set fcontractbasedataid ="
						+ "(select fid from t_CON_contractbasedata where fcontractid = t_con_contractwithouttext.fid) "
						+ "where");
		builder.appendParam("fid", objectPK.toString());
		builder.executeUpdate(ctx);
		/** ��ӷ�дContractBaseDataID�Ĵ��� -by neo */
		return objectPK;
	}

	// �޸�
	protected void _update(Context ctx, IObjectPK pk, IObjectValue model)
			throws BOSException, EASBizException {

		super._update(ctx, pk, model);

		// ͬ������ͬ�������ϣ����ں�ͬ��������Ŀ
		ContractBaseDataHelper.synToContractBaseData(ctx, true, pk.toString());
	}

	protected void _updatePartial(Context ctx, IObjectValue model,
			SelectorItemCollection selector) throws BOSException,
			EASBizException {
		super._updatePartial(ctx, model, selector);

		// ͬ������ͬ�������ϣ����ں�ͬ��������Ŀ
		String id = ((FDCBillInfo) model).getId().toString();
		ContractBaseDataHelper.synToContractBaseData(ctx, true, id);

	}

	protected void _delete(Context ctx, IObjectPK pk) throws BOSException,
			EASBizException {
		try {//������Լ�滮�Ƿ�����˺�ͬ
			updateOldProg(ctx,pk);
		} catch (Exception e) {
			logger.error(e);
		}
		_delete(ctx, new IObjectPK[] { pk });
	}
	
	/**
	 * �ҳ��������Ŀ�ܺ�Լ�ļ�¼��(���ı��Ѿ��ϳ������ٲ���)
	 * 
	 * @param proContId
	 * @return
	 */
	private int isCitingByProg(Context ctx, String proContId) {
		FDCSQLBuilder buildSQL = new FDCSQLBuilder(ctx);
		buildSQL.appendSql(" select count(1) count from T_INV_InviteProject ");
		buildSQL.appendSql(" where FProgrammingContractId = '" + proContId + "' ");
		buildSQL.appendSql(" union ");
		buildSQL.appendSql(" select count(1) count from T_CON_ContractWithoutText ");
		buildSQL.appendSql(" where FProgrammingContract = '" + proContId + "' ");
		int count = 0;
		try {
			IRowSet iRowSet = buildSQL.executeQuery();
			while (iRowSet.next()) {
				count += iRowSet.getInt("count");
			}
		} catch (BOSException e) {
			logger.error(e);
		} catch (SQLException e) {
			logger.error(e);
		}
		return count;
	}

	/**
	 * ������ı���ͬ�Ƿ������ܺ�Լ
	 * 
	 * @return
	 * @throws Exception
	 */
	private String checkReaPre(Context ctx, IObjectPK pk) throws Exception {
		FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
		SelectorItemCollection sic = new SelectorItemCollection();
		sic.add("*");
		sic.add("programmingContract.*");
		builder.clear();
		builder.appendSql("select fprogrammingContract from T_CON_ContractWithoutText where 1=1 and ");
		builder.appendParam("fid", pk.toString());
		IRowSet rowSet = builder.executeQuery();
		while (rowSet.next()) {
			if (rowSet.getString("fprogrammingContract") != null) {
				return rowSet.getString("fprogrammingContract").toString();
			}
		}
		return null;
	}
	
	private int isCitingByContractWithoutText(Context ctx,String proContId) {
		FDCSQLBuilder buildSQL = new FDCSQLBuilder(ctx);
		buildSQL.appendSql("select count(*) count from T_CON_ContractWithoutText ");
		buildSQL.appendSql("where FProgrammingContract = '" + proContId + "' ");
		int count = 0;
		try {
			IRowSet iRowSet = buildSQL.executeQuery();
			if (iRowSet.next()) {
				count = iRowSet.getInt("count");
			}
		} catch (BOSException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return count;
	}
	/**
	 * �����Ͽ�ܺ�Լ�Ƿ�����
	 * 
	 * @throws Exception
	 * @throws Exception
	 */
	private void updateOldProg(Context ctx, IObjectPK pk) throws Exception {
		String checkReaPre = checkReaPre(ctx, pk);
		if(checkReaPre!=null){
			int linkContractWithoutText = isCitingByContractWithoutText(ctx,checkReaPre);// ����Լ�������ı���ͬ��������
			if (linkContractWithoutText <= 1 ) {
				updateProgrammingContract(ctx, checkReaPre, 0);
			}
		}
	}
	
	private String isUpdateNextProgState(Context ctx, String progId) throws Exception {
//		String flag = null;
//		FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
//		builder.appendSql(" select fid from t_con_programmingContract where ");
//		builder.appendParam("fSrcId", progId);
//		IRowSet rowSet = builder.executeQuery();
//		while (rowSet.next()) {
//			flag = rowSet.getString("fid").toString();
//		}
		return null;
	}
	
	private boolean preVersionProg(Context ctx, String progId) throws BOSException, SQLException {
		boolean isCityingProg = false;
		int tempIsCiting = 0;
		FDCSQLBuilder buildSQL = new FDCSQLBuilder(ctx);
		buildSQL.appendSql(" select t1.FIsCiting isCiting from t_con_programmingContract t1 where t1.fid = (");
		buildSQL.appendSql(" select t2.FSrcId from t_con_programmingContract t2 where t2.fid = '" + progId + "')");
		IRowSet rowSet = buildSQL.executeQuery();
		while (rowSet.next()) {
			tempIsCiting = rowSet.getInt("isCiting");
		}
		if (tempIsCiting > 0) {
			isCityingProg = true;
		}
		return isCityingProg;
	}
	
	/**
	 * ���¹滮��Լ"�Ƿ�����"�ֶ�
	 * 
	 * @param proContId
	 * @param isCiting
	 */
	private void updateProgrammingContract(Context ctx, String proContId, int isCiting) {
		FDCSQLBuilder buildSQL = new FDCSQLBuilder(ctx);
		buildSQL.appendSql("update T_CON_ProgrammingContract set FIsWTCiting = " + isCiting + " ");
		buildSQL.appendSql("where FID = '" + proContId + "' ");
		try {
			buildSQL.executeUpdate();
		} catch (BOSException e) {
			e.printStackTrace();
		}
	}
	protected void _cancel(Context ctx, IObjectPK pk) throws BOSException,
			EASBizException {
		super._cancel(ctx, pk);

		// ͬ��ɾ����ͬ��������
		String sql = "delete t_con_contractbasedata where fcontractid = ?";
		DbUtil.execute(ctx, sql, new Object[] { pk.toString() });

		sql = "delete from T_CON_PayRequestBillEntry where fparentid in (select fid from T_CON_PayRequestBill where fcontractid =?) "; // TODO
		DbUtil.execute(ctx, sql, new Object[] { pk.toString() });
		sql = "delete from T_CON_PayRequestBill where fcontractid =?";
		DbUtil.execute(ctx, sql, new Object[] { pk.toString() });
		sql = "delete from T_CON_ContractBaseData where fcontractid =?";
		DbUtil.execute(ctx, sql, new Object[] { pk.toString() });

	}

	protected void _delete(Context ctx, IObjectPK[] arrayPK)
			throws BOSException, EASBizException {
		Set set = new HashSet();
		// String str="'";
		for (int i = 0; i < arrayPK.length; i++) {
			set.add(arrayPK[i]);
			
			deleteOA(ctx,ContractWithoutTextFactory.getLocalInstance(ctx).getContractWithoutTextInfo(arrayPK[i]));
			// str+=arrayPK[i].toString()+"','";
		}
		// str=str.substring(0,str.length()-2);
		FilterInfo filter = new FilterInfo();
		filter = new FilterInfo();
		filter.getFilterItems().add(
				new FilterItemInfo("id", set, CompareType.INCLUDE));
		filter.getFilterItems().add(
				new FilterItemInfo("state", FDCBillStateEnum.AUDITTED_VALUE));
		if (_exists(ctx, filter)) {
			throw new ContractException(ContractException.HASAUDIT);
		}

		// �ڷ�������ʱ���ж���û�и��,����в���������
		// filter.getFilterItems().add(new
		// FilterItemInfo("contractBillId",set,CompareType.INCLUDE));
		// if(PaymentBillFactory.getLocalInstance(ctx).exists(filter)){
		// throw new ContractException(ContractException.HASPAYMENTBILL);
		// }
		// SQL����������¼��־
		// String sql=
		// "delete from T_CON_PayRequestBillEntry where fparentid in (select fid from T_CON_PayRequestBill where fcontractid in ("
		// +str+"));"; //TODO
		// DbUtil.execute(ctx, sql);
		//sql="delete from T_CON_PayRequestBill where fcontractid in ("+str+");"
		// ;
		// DbUtil.execute(ctx, sql);
		// IObjectPK payrequestPK =null;
		// PayRequestBillFactory.getLocalInstance(ctx).delete(payrequestPK);

		//
		filter = new FilterInfo();
		filter.getFilterItems().add(
				new FilterItemInfo("parent.contractid", set,
						CompareType.INCLUDE));
		PayRequestBillEntryFactory.getLocalInstance(ctx).delete(filter);
		// ���ݣ���Ҫ��¼��־
		EntityViewInfo view = new EntityViewInfo();
		view.getSelector().add(new SelectorItemInfo("id"));
		view.getSelector().add(new SelectorItemInfo("number"));
		filter = new FilterInfo();
		filter.getFilterItems().add(
				new FilterItemInfo("contractid", set, CompareType.INCLUDE));
		view.setFilter(filter);
		PayRequestBillCollection payRequestBills = PayRequestBillFactory
				.getLocalInstance(ctx).getPayRequestBillCollection(view);
		IObjectPK[] payPks = new IObjectPK[payRequestBills.size()];
		int i = 0;
		String paysLogInfo = "";
		for (Iterator it = payRequestBills.iterator(); it.hasNext();) {
			PayRequestBillInfo pay = (PayRequestBillInfo) it.next();
			payPks[i++] = new ObjectUuidPK(pay.getId());
			paysLogInfo += paysLogInfo.equals("") ? pay.getNumber() : ","
					+ pay.getNumber();
		}
		// begin log
		IObjectPK logPk = LogUtil.beginLog(ctx, "fin_payRequst_delete",
				(new PayRequestBillInfo()).getBOSType(), null, paysLogInfo,
				"fin_payRequst_delete");
		// do delete
		PayRequestBillFactory.getLocalInstance(ctx).deleteForContWithoutText(
				payPks);
		// after log
		LogUtil.afterLog(ctx, logPk);
		// delete payrequestbill end

		/*
		 * //����ֱ��ɾ�����������Ϊ������Ŀ��������õĻ��ǲ�����ɾ���ģ���ORMap�ķ�����������ļ�� String
		 * sql="delete from T_CON_ContractBaseData where fcontractid in ("
		 * +str+");"; DbUtil.execute(ctx, sql);
		 */
		filter = new FilterInfo();
		filter.getFilterItems().add(
				new FilterItemInfo("contractId", set, CompareType.INCLUDE));
		ContractBaseDataFactory.getLocalInstance(ctx).delete(filter);
		
		super._delete(ctx, arrayPK);
		
	}
	protected void recycleNumber(Context ctx, IObjectPK pk) throws BOSException, EASBizException, CodingRuleException {
		
	}
	private void createPayRequestBill(Context ctx, FDCBillInfo con,
			PayRequestBillInfo prbi, FDCBillStateEnum state)
			throws BOSException, EASBizException {

		// ɾ��֮ǰ�Ķ��ึ�����뵥
		String sql = "delete from T_CON_PayRequestBill where FContractId = ? ";
		Object[] params = new Object[] { con.getId().toString() };
		DbUtil.execute(ctx, sql, params);
		checkNumber(ctx, con.getNumber());
		// �������뵥�����ظ���У��
		String number = payReqNumber;

		// UI����
		// �������뵥
		PayRequestBillInfo prbiNew = new PayRequestBillInfo();

		// ��������
		prbiNew.setPaymentType(prbi.getPaymentType());
		// �ÿ��
		prbiNew.setUseDepartment(prbi.getUseDepartment());
		// �������뵥����
		// prbiNew.setNumber(prbi.getNumber());
		// if(prbi.getId() == null){
		// 2009-2-5 ʼ��ʹ��checkNumber����������ı������ɸ������뵥
		prbiNew.setNumber(number);
		// }else{
		// prbiNew.setNumber(prbi.getNumber());
		// }
		// ��������
		prbiNew.setPayDate(prbi.getPayDate());
		// �տλ
		prbiNew.setSupplier(prbi.getSupplier());
		// ʵ���տλ
		prbiNew.setRealSupplier(prbi.getRealSupplier());
		prbiNew.setPerson(prbi.getPerson());
		// ���㷽ʽ
		prbiNew.setSettlementType(prbi.getSettlementType());
		// �տ�����
		prbiNew.setRecBank(prbi.getRecBank());
		// �տ��ʺ�
		prbiNew.setRecAccount(prbi.getRecAccount());
		// �����̶�
		prbiNew.setUrgentDegree(prbi.getUrgentDegree());
		// �ұ�
		prbiNew.setCurrency(prbi.getCurrency());
		
		prbiNew.setLocalCurrency(prbi.getLocalCurrency());
		// �������
		prbiNew.setLatestPrice(prbi.getLatestPrice());
		// ����
		prbiNew.setExchangeRate(prbi.getExchangeRate());
		// ��д���
		prbiNew.setCapitalAmount(prbi.getCapitalAmount());
		// �������
		prbiNew.setPaymentProportion(prbi.getPaymentProportion());
		// ���깤���������
		prbiNew.setCompletePrjAmt(prbi.getCompletePrjAmt());
		// ��λ�ҽ��
		prbiNew.setAmount(prbi.getAmount());
		prbiNew.setProjectPriceInContract(prbi.getAmount());
		// ԭ�ҽ��
		prbiNew.setOriginalAmount(prbi.getOriginalAmount());
		prbiNew.setProjectPriceInContractOri(prbi.getOriginalAmount());
		// ����˵��
		prbiNew.setUsage(prbi.getUsage());
		// ����˵��
		prbiNew.setMoneyDesc(prbi.getMoneyDesc());
		// ��ע
		prbiNew.setDescription(prbi.getDescription());

		// ����
		prbiNew.setAttachment(prbi.getAttachment());
		// ������Ŀ
		prbiNew.setCurProject(prbi.getCurProject());
		// �ر�״̬
		prbiNew.setHasClosed(false);
		// ��������
		prbiNew.setBookedDate(con.getBookedDate());
		// �ڼ�
		prbiNew.setPeriod(con.getPeriod());

		// �˴���ȡ��
		// ��ͬ��
		prbiNew.setContractNo(con.getNumber());
		// ��ͬID
		prbiNew.setContractId(con.getId().toString());
		prbiNew.setSource(con.getBOSType().toString());
		// ��ͬ����
		prbiNew.setContractName(con.getName());
		// ��ͬ���
		prbiNew.setContractPrice(con.getAmount());
		// ״̬
		prbiNew.setState(state);
		// �������
		// prbiNew.setPaymentProportion(new BigDecimal(100));
		prbiNew.setPaymentProportion(FDCHelper.ONE_HUNDRED);
		// ���깤���������
		prbiNew.setCompletePrjAmt(con.getAmount());// ���깤���������
		// �ɱ�����
		prbiNew.setOrgUnit(con.getOrgUnit());
		// ����Ԫ
		prbiNew.setCU(con.getCU());
		
	
		// ���踶������뵥 update by liang_ren at 2010-5-10
	
		prbiNew.setIsPay(!((ContractWithoutTextInfo) con).isIsNeedPaid());
			
		// ��Ʊ��
		prbiNew.setInvoiceNumber(prbi.getInvoiceNumber());
		// ��Ʊ����
		prbiNew.setInvoiceDate(prbi.getInvoiceDate());
		// ��Ʊ���
		prbiNew.setInvoiceAmt(prbi.getInvoiceAmt());
		// �ۼƷ�Ʊ���
		prbiNew.setAllInvoiceAmt(prbi.getInvoiceAmt());
		
		prbiNew.setIsBgControl(prbi.isIsBgControl());
		prbiNew.setApplier(prbi.getApplier());
		prbiNew.setApplierCompany(prbi.getApplierCompany());
		prbiNew.setApplierOrgUnit(prbi.getApplierOrgUnit());
		prbiNew.setCostedCompany(prbi.getCostedCompany());
		prbiNew.setCostedDept(prbi.getCostedDept());
		prbiNew.setPayBillType(prbi.getPayBillType());
		prbiNew.setPayContentType(prbi.getPayContentType());
		prbiNew.setCreator(prbi.getCreator());
		prbiNew.setLastUpdateUser(prbi.getLastUpdateUser());
		
		prbiNew.setLxNum(prbi.getLxNum());
		prbiNew.setIsJT(prbi.isIsJT());
		for(int i=0;i<prbi.getBgEntry().size();i++){
			prbi.getBgEntry().get(i).setId(null);
			prbiNew.getBgEntry().add(prbi.getBgEntry().get(i));
		}
		PayRequestBillFactory.getLocalInstance(ctx).addnew(prbiNew);

	}

	// ������ʱ���༭�����ʼ���ݴ����ȷ���
	protected Map _fetchInitData(Context ctx, Map paramMap)
			throws BOSException, EASBizException {

		paramMap.put("isCost", Boolean.FALSE);
		Map initMap = super._fetchInitData(ctx, paramMap);

		return initMap;
	}

	private SelectorItemCollection getSic() {
		// �˹���Ϊ��ϸ��Ϣ����
		SelectorItemCollection sic = new SelectorItemCollection();
		sic.add(new SelectorItemInfo("id"));
		sic.add(new SelectorItemInfo("number"));
		sic.add(new SelectorItemInfo("period.id"));
		sic.add(new SelectorItemInfo("period.beginDate"));
		sic.add(new SelectorItemInfo("curProject.fullOrgUnit.id"));
		sic.add(new SelectorItemInfo("curProject.id"));
		sic.add(new SelectorItemInfo("curProject.displayName"));
		sic.add(new SelectorItemInfo("conChargeType.id"));
	     // wangxin 
		sic.add(new SelectorItemInfo("hasSettled"));
		sic.add(new SelectorItemInfo("programmingContract.*"));
		sic.add(new SelectorItemInfo("*"));
		return sic;
	}

	// �ύʱУ�鵥���ڼ䲻���ڹ�����Ŀ�ĵ�ǰ�ڼ�֮ǰ
	private void checkBillForSubmit(Context ctx, IObjectValue model)
			throws BOSException, EASBizException {

		// �������ڵ�ǰ�ɱ��ڼ�֮ǰ
		ContractWithoutTextInfo contractBill = (ContractWithoutTextInfo) model;

		String comId = null;
		if (contractBill.getCurProject().getFullOrgUnit() == null) {
			SelectorItemCollection sic = new SelectorItemCollection();
			sic.add("fullOrgUnit.id");
			CurProjectInfo curProject = CurProjectFactory.getLocalInstance(ctx)
					.getCurProjectInfo(
							new ObjectUuidPK(contractBill.getCurProject()
									.getId().toString()), sic);

			comId = curProject.getFullOrgUnit().getId().toString();
		} else {
			comId = contractBill.getCurProject().getFullOrgUnit().getId()
					.toString();
		}

		boolean isInCore = FDCUtils.IsInCorporation(ctx, comId);
		if (isInCore) {
			PeriodInfo bookedPeriod = FDCUtils.getCurrentPeriod(ctx,
					contractBill.getCurProject().getId().toString(), false);
			if (contractBill.getPeriod().getBeginDate().before(
					bookedPeriod.getBeginDate())) {
				// �����ڼ䲻���ڹ�����Ŀ�ĵ�ǰ�ڼ�֮ǰ CNTPERIODBEFORE
				throw new ContractException(ContractException.CNTPERIODBEFORE);
			}
		}
//		IBgControlFacade iBgControlFacade = BgControlFacadeFactory.getLocalInstance(ctx);
//		BgCtrlResultCollection coll = iBgControlFacade.getBudget("com.kingdee.eas.fi.cas.app.PaymentBill", new BgCtrlParamCollection(), createTempPaymentBill(contractBill));
//		Map bgItemMap=new HashMap();
//		for (int i = 0; i < contractBill.getBgEntry().size(); i++) {
//			BgItemInfo bgItem=contractBill.getBgEntry().get(i).getBgItem();
//			for (int j = 0; j < coll.size(); j++) {
//				if (bgItem.getNumber().equals(coll.get(j).getItemCombinNumber())) {
//					if(BgCtrlTypeEnum.NoCtrl.equals(coll.get(j).getCtrlType())){
//						break;
//					}
//					String isFromWorkflow=(String) contractBill.get("isFromWorkflow");
//					String approveIsPass=(String) contractBill.get("approveIsPass");
//					if (isFromWorkflow != null && isFromWorkflow.equals("true")&&approveIsPass!=null&&approveIsPass.equals("false")) {
//						break;
//					}
//					BigDecimal balanceAmount=FDCHelper.ZERO;
//					BigDecimal requestAmount=contractBill.getBgEntry().get(i).getRequestAmount();
//					if(coll.get(j).getBalance() != null){
//						balanceAmount=coll.get(j).getBalance();
//					}
//					if(bgItemMap.containsKey(bgItem.getNumber())){
//						BigDecimal sumAmount=(BigDecimal) bgItemMap.get(bgItem.getNumber());
//						balanceAmount=balanceAmount.subtract(sumAmount);
//						bgItemMap.put(bgItem.getNumber(), sumAmount.add(requestAmount));
//					}else{
//						bgItemMap.put(bgItem.getNumber(), requestAmount);
//					}
//					BigDecimal balance=FDCHelper.ZERO;
//					try {
//						balance = balanceAmount.subtract(getAccActOnLoadBgAmount(ctx,bgItem.getNumber(),contractBill));
//					} catch (SQLException e) {
//						e.printStackTrace();
//					}
//					if(requestAmount.compareTo(balance)>0){
//						throw new EASBizException(new NumericExceptionSubItem("000",bgItem.getName()+"����Ԥ����"));
//					}
//				}
//			}
//		}
	}
	protected BigDecimal getAccActOnLoadBgAmount(Context ctx,String bgItemNumber,ContractWithoutTextInfo contractBill) throws BOSException, SQLException{
		if(contractBill.getCostedDept()==null) return FDCHelper.ZERO;
		
		String bgComItem="";
		Set bgComItemSet=new HashSet();
		bgComItemSet.add(bgItemNumber);
		FDCSQLBuilder _builder = new FDCSQLBuilder(ctx);
		_builder.appendSql(" select distinct t2.fformula fformula from T_BG_BgTemplateCtrlSetting t1 left join T_BG_BgTemplateCtrlSetting t2 on t1.fgroupno=t2.fgroupno where t1.FOrgUnitId ='"+contractBill.getCostedDept().getId().toString()+"'");
		_builder.appendSql(" and t1.fgroupno!='-1' and t2.fgroupno!='-1' and t1.fformula like '%"+bgItemNumber+"%' and t2.fformula not like '%"+bgItemNumber+"%'");
		IRowSet rowSet = _builder.executeQuery();
		
		while(rowSet.next()){
			if(rowSet.getString("fformula")!=null){
				String formula=rowSet.getString("fformula");
				bgComItemSet.add(formula.substring(9, formula.indexOf(",")-1));
			}
		}
		Object[] bgObject = bgComItemSet.toArray();
    	for(int i=0;i<bgObject.length;i++){
        	if(i==0){
        		bgComItem=bgComItem+"'"+bgObject[i].toString()+"'";
        	}else{
        		bgComItem=bgComItem+",'"+bgObject[i].toString()+"'";
        	}
        }
    	
		_builder = new FDCSQLBuilder(ctx);
		_builder.appendSql(" select sum(entry.frequestAmount-isnull(entry.factPayAmount,0))payAmount from T_CON_PayRequestBill bill left join T_CON_PayRequestBillBgEntry entry on entry.fheadid=bill.fid ");
		_builder.appendSql(" left join T_BG_BgItem bgItem on bgItem.fid=entry.fbgitemid ");
		_builder.appendSql(" where bill.fisBgControl=1 and bill.FCostedDeptId='"+contractBill.getCostedDept().getId().toString()+"'  and bgItem.fnumber in("+bgComItem+")");
		_builder.appendSql(" and bill.fstate in('3AUDITTING','4AUDITTED') ");
		_builder.appendSql(" and bill.fhasClosed=0 and bill.famount is not null");
		
		if(contractBill.getId()!=null){
			_builder.appendSql(" and bill.fcontractid!='"+contractBill.getId().toString()+"'");
		}
		rowSet = _builder.executeQuery();
		while(rowSet.next()){
			if(rowSet.getBigDecimal("payAmount")!=null)
				return rowSet.getBigDecimal("payAmount");
		}
		return FDCHelper.ZERO;
	}
    protected PaymentBillInfo createTempPaymentBill(ContractWithoutTextInfo contractBill) throws BOSException{
		PaymentBillInfo pay=new PaymentBillInfo();
		for(int i=0;i<contractBill.getBgEntry().size();i++){
			PaymentBillEntryInfo entry=new PaymentBillEntryInfo();
			BigDecimal requestAmount=contractBill.getBgEntry().get(i).getRequestAmount();
			
			entry.setAmount(requestAmount);
			entry.setLocalAmt(requestAmount);
            entry.setActualAmt(requestAmount);
            entry.setActualLocAmt(requestAmount);
            entry.setCurrency(contractBill.getCurrency());
            entry.setExpenseType(contractBill.getBgEntry().get(i).getExpenseType());
            entry.setCostCenter(contractBill.getCostedDept());
            pay.getEntries().add(entry);
		}
		pay.setCompany(contractBill.getCostedCompany());
		pay.setCostCenter(contractBill.getCostedDept());
		pay.setPayDate(FDCCommonServerHelper.getServerTimeStamp());
		pay.setCurrency(contractBill.getCurrency());
		
		return pay;
	}
	// ���У��
	private void checkBillForAudit(Context ctx, BOSUuid billId,
			FDCBillInfo billInfo) throws BOSException, EASBizException {

		ContractWithoutTextInfo model = (ContractWithoutTextInfo) billInfo;

		if (model == null) {
			model = this.getContractWithoutTextInfo(ctx, new ObjectUuidPK(
					billId.toString()), getSic());
		}
		// ContractBillInfo contractBillInfo = this.getContractBillInfo(ctx,new
		// ObjectUuidPK(billId.toString()),sic);
		// ��鹦���Ƿ��Ѿ�������ʼ��
		String comId = model.getCurProject().getFullOrgUnit().getId()
				.toString();

		// �Ƿ����ò���һ�廯
		boolean isInCore = FDCUtils.IsInCorporation(ctx, comId);
		if (isInCore) {
			String curProject = model.getCurProject().getId().toString();

			if (!ProjectPeriodStatusUtil._isClosed(ctx, curProject)) {
				throw new ProjectPeriodStatusException(
						ProjectPeriodStatusException.ISNOT_INIT,
						new Object[] { model.getCurProject().getDisplayName() });
			}
			// //�ɱ��Ѿ��½�
			// PeriodInfo bookedPeriod =
			// FDCUtils.getCurrentPeriod(ctx,curProject,true);
			//if(model.getPeriod().getBeginDate().after(bookedPeriod.getEndDate(
			// ))){
			// throw new ContractException(ContractException.AUD_AFTERPERIOD,new
			// Object[]{model.getNumber()});
			// }

			// ����û���½ᣬ������˵���{0}
			// PeriodInfo finPeriod =
			// FDCUtils.getCurrentPeriod(ctx,curProject,false);
			//if(model.getPeriod().getBeginDate().after(finPeriod.getEndDate()))
			// {
			// throw new ContractException(ContractException.AUD_FINNOTCLOSE,new
			// Object[]{model.getNumber()});
			// }
		}
		// ���ݲ����Ƿ���ʾ��ͬ������Ŀ
		// ��������ʱԤ�����
		boolean isShowCharge = FDCUtils.getDefaultFDCParamByKey(ctx, null,
				FDCConstants.FDC_PARAM_CHARGETYPE);
		if (isShowCharge && model.getConChargeType() != null) {
			invokeAuditBudgetCtrl(ctx, model);
		}
	}

	// ��������ʱ Ԥ�����
	private void invokeAuditBudgetCtrl(Context ctx,
			ContractWithoutTextInfo conBill) throws BOSException,
			EASBizException {
		boolean useWorkflow = FDCUtils.isRunningWorkflow(ctx, conBill.getId()
				.toString());
		if (!useWorkflow) {
			IBgControlFacade bgControlFacade = BgControlFacadeFactory
					.getLocalInstance(ctx);
			IBgBudgetFacade iBgBudget = BgBudgetFacadeFactory
					.getLocalInstance(ctx);

			boolean isPass = false;
			isPass = iBgBudget
					.getAllowAccessNoWF(FDCConstants.ContractWithoutText);
			if (isPass) {
				// 5.1.1��ʱ����
				bgControlFacade.bgAuditAllowAccess(conBill.getId(),
						FDCConstants.ContractWithoutText, null);
			} else {
				isPass = bgControlFacade.bgAudit(conBill.getId().toString(),
						FDCConstants.ContractWithoutText, null);
			}
			// ����isPass�ж��Ƿ����쳣
			if (!isPass) {
				throw new ContractException(ContractException.BEFOREBGBAL);
			}
		}
	}

	// ���ݷ�����ʱ Ԥ�����
	private void invokeUnAuditBudgetCtrl(Context ctx,
			ContractWithoutTextInfo conBill) throws BOSException,
			EASBizException {

		IBgControlFacade iBgCtrl = BgControlFacadeFactory.getLocalInstance(ctx);
		iBgCtrl.cancelRequestBudget(conBill.getId().toString());
	}

	// ���У��
	private void checkBillForUnAudit(Context ctx, BOSUuid billId,
			FDCBillInfo billInfo) throws BOSException, EASBizException {
		ContractWithoutTextInfo model = (ContractWithoutTextInfo) billInfo;
		// �˹���Ϊ��ϸ��Ϣ����
		SelectorItemCollection sic = new SelectorItemCollection();
		sic.add(new SelectorItemInfo("id"));
		sic.add(new SelectorItemInfo("number"));
		sic.add(new SelectorItemInfo("curProject.fullOrgUnit.id"));
		sic.add(new SelectorItemInfo("curProject.id"));
		sic.add(new SelectorItemInfo("curProject.displayName"));
		sic.add(new SelectorItemInfo("period.beginDate"));
		sic.add(new SelectorItemInfo("conChargeType.id"));

		if (model == null) {
			model = this.getContractWithoutTextInfo(ctx, new ObjectUuidPK(
					billId.toString()), getSic());
		}
		// ��鹦���Ƿ��Ѿ�������ʼ��
		String comId = model.getCurProject().getFullOrgUnit().getId()
				.toString();

		// �Ƿ����ò���һ�廯
		boolean isInCore = FDCUtils.IsInCorporation(ctx, comId);
		if (isInCore) {
			String curProject = model.getCurProject().getId().toString();

			// �����ڼ��ڹ�����Ŀ��ǰ�ڼ�֮ǰ�����ܷ����
			PeriodInfo bookedPeriod = FDCUtils.getCurrentPeriod(ctx,
					curProject, false);
			if (model.getPeriod().getBeginDate().before(
					bookedPeriod.getBeginDate())) {
				throw new ContractException(ContractException.CNTPERIODBEFORE);
			}

			// if(ProjectPeriodStatusUtil._isEnd(ctx,curProject)){
			// throw new
			// ProjectPeriodStatusException(ProjectPeriodStatusException
			// .CLOPRO_HASEND,new
			// Object[]{model.getCurProject().getDisplayName()});
			// }
		}
		// ���ݲ����Ƿ���ʾ��ͬ������Ŀ
		// ��������ʱԤ�����
		boolean isShowCharge = FDCUtils.getDefaultFDCParamByKey(ctx, null,
				FDCConstants.FDC_PARAM_CHARGETYPE);
		if (isShowCharge && model.getConChargeType() != null) {
			invokeUnAuditBudgetCtrl(ctx, model);
		}
	}

	// �Ƿ�ɱ�
	protected boolean isCost() {
		return false;
	}

	// ��ȡ��ͬ���ͱ���
	protected String _getContractTypeNumber(Context ctx, IObjectPK id)
			throws BOSException, EASBizException {
		//
		SelectorItemCollection sic = new SelectorItemCollection();
		sic.add(new SelectorItemInfo("contractType.number"));
		sic.add(new SelectorItemInfo("contractType.longNumber"));
		ContractWithoutTextInfo contractBill = (ContractWithoutTextInfo) this
				.getValue(ctx, id, sic);

		if (contractBill.getContractType() != null) {
			return contractBill.getContractType().getLongNumber().replace('!',
					'.');
		} else {
			return "*";
		}
	}

	// ��������ظ�����ȡ����
	protected void handleIntermitNumber(Context ctx, FDCBillInfo info)
			throws BOSException, CodingRuleException, EASBizException {
		handleIntermitNumberForReset(ctx, info);
	}

	// �������Ƿ���ͬ
	private void checkNumber(Context ctx, String number) {
		if (number==null || number.equals("")) {
			return;
		}
		payReqNumber = number;
		// �������뵥�����ظ���У��
		String sql = "select fid from T_CON_PayRequestBill where FNumber = ? ";
		Object[] params = new Object[] { number };

		RowSet rowset;
		try {
			rowset = DbUtil.executeQuery(ctx, sql, params);
			try {
				if (rowset.next()) {
					payReqNumber = payReqNumber + "(���ı�)";
					checkNumber(ctx, payReqNumber);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} catch (BOSException e1) {
			e1.printStackTrace();
		}
	}

	protected String _getUseDepartment(Context ctx, String id)
			throws BOSException, EASBizException {
		EntityViewInfo view = new EntityViewInfo();
		view.setFilter(new FilterInfo());
		view.getFilter().getFilterItems().add(new FilterItemInfo("contractId",id.toString()));
		view.setSelector(new SelectorItemCollection());
		view.getSelector().add("useDepartment.id");
		view.getSelector().add("useDepartment.name");
		view.getSelector().add("useDepartment.number");
		view.getSelector().add("useDepartment.longNumber");
		PayRequestBillCollection payReqBillCol = PayRequestBillFactory.getLocalInstance(ctx).getPayRequestBillCollection(view);
		if(payReqBillCol.size() > 0){
			PayRequestBillInfo billInfo = payReqBillCol.get(0);
			return billInfo.getUseDepartment().getLongNumber().replace('!', '.');
		}
		return "*";
	}

	protected String _getPaymentType(Context ctx, BOSUuid contractId)
			throws BOSException, EASBizException {
		EntityViewInfo view = new EntityViewInfo();
		view.setFilter(new FilterInfo());
		view.getFilter().getFilterItems().add(new FilterItemInfo("contractId",contractId.toString()));
		view.setSelector(new SelectorItemCollection());
		view.getSelector().add("paymentType.id");
		view.getSelector().add("paymentType.name");
		view.getSelector().add("paymentType.number");
		PayRequestBillCollection payReqBillCol = PayRequestBillFactory.getLocalInstance(ctx).getPayRequestBillCollection(view);
		if(payReqBillCol.size() > 0){
			PayRequestBillInfo billInfo = payReqBillCol.get(0);
			return billInfo.getPaymentType().getNumber();
		}
		return "*";
	}

	protected void _synUpdateProgramming(Context ctx, String billId,IObjectValue programming) throws BOSException, EASBizException {
		if(programming==null) return;
		SelectorItemCollection sel=new SelectorItemCollection();
		sel.add("amount");
		ContractWithoutTextInfo contractBillInfo = this.getContractWithoutTextInfo(ctx, new ObjectUuidPK(billId), sel);
		IProgrammingContract service = ProgrammingContractFactory.getLocalInstance(ctx);
		ProgrammingContractInfo pcInfo = (ProgrammingContractInfo)programming;
		
		BigDecimal pcBalance=pcInfo.getBalance();
		BigDecimal pcSignUpAmount=pcInfo.getSignUpAmount();
		BigDecimal amount = contractBillInfo.getAmount();
		
		pcInfo.setBalance(FDCHelper.subtract(pcBalance, amount));
		pcInfo.setSignUpAmount(FDCHelper.add(pcSignUpAmount, amount));
		pcInfo.setIsWTCiting(true);
		
		SelectorItemCollection sict = new SelectorItemCollection();
		sict.add("balance");
		sict.add("signUpAmount");
		sict.add("isWTCiting");
		service.updatePartial(pcInfo, sict);
	}
	protected void _synReUpdateProgramming(Context ctx, String billId, IObjectValue programming) throws BOSException, EASBizException {
		if(programming==null) return;
		IProgrammingContract service = ProgrammingContractFactory.getLocalInstance(ctx);
		ProgrammingContractInfo pcInfo = (ProgrammingContractInfo)programming;
		
		BigDecimal pcAmount=pcInfo.getAmount();
		
		SelectorItemCollection sel = new SelectorItemCollection();
		sel.add("amount");
		sel.add("head.contractChangeBill.id");
		sel.add("head.contractChangeSettleBill.conChangeBill.id");
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("programmingContract.id",pcInfo.getId().toString()));
		filter.getFilterItems().add(new FilterItemInfo("state",FDCBillStateEnum.AUDITTED_VALUE));
		filter.getFilterItems().add(new FilterItemInfo("id",billId,CompareType.NOTEQUALS));
		view.setFilter(filter);
		view.setSelector(sel);
		ContractWithoutTextCollection wtcol=ContractWithoutTextFactory.getLocalInstance(ctx).getContractWithoutTextCollection(view);
		BigDecimal amount=FDCHelper.ZERO;
		for(int i=0;i<wtcol.size();i++){
			amount =FDCHelper.add(amount,wtcol.get(i).getAmount()) ;
		}
		if(wtcol.size()==0){
			pcInfo.setIsWTCiting(false);
		}
		view = new EntityViewInfo();
		filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("programmingContract.id",pcInfo.getId().toString()));
		filter.getFilterItems().add(new FilterItemInfo("state",FDCBillStateEnum.AUDITTED_VALUE));
		view.setFilter(filter);
		view.setSelector(sel);
		ContractBillCollection col=ContractBillFactory.getLocalInstance(ctx).getContractBillCollection(view);
		for(int i=0;i<col.size();i++){
			amount =FDCHelper.add(amount,col.get(i).getAmount()) ;
			pcInfo.setIsCiting(true);
		}
		if(col.size()==0){
			BigDecimal signUpAmount=FDCHelper.ZERO;
    		BigDecimal changeAmount=FDCHelper.ZERO;
    		BigDecimal settleAmount=FDCHelper.ZERO;
    		
			view = new EntityViewInfo();
			filter = new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("head.contractSettleBill.id",null,CompareType.NOTEQUALS));
			filter.getFilterItems().add(new FilterItemInfo("programmingContract.id",pcInfo.getId().toString()));
			view.setFilter(filter);
			view.setSelector(sel);
			ContractPCSplitBillEntryCollection spcol=ContractPCSplitBillEntryFactory.getLocalInstance(ctx).getContractPCSplitBillEntryCollection(view);
			if(spcol.size()==0){
				view = new EntityViewInfo();
				filter = new FilterInfo();
				filter.getFilterItems().add(new FilterItemInfo("head.contractBill.id",null,CompareType.NOTEQUALS));
				filter.getFilterItems().add(new FilterItemInfo("programmingContract.id",pcInfo.getId().toString()));
				view.setFilter(filter);
				view.setSelector(sel);
				ContractPCSplitBillEntryCollection signUpCol=ContractPCSplitBillEntryFactory.getLocalInstance(ctx).getContractPCSplitBillEntryCollection(view);
				for(int j=0;j<signUpCol.size();j++){
					signUpAmount =FDCHelper.add(signUpAmount,signUpCol.get(j).getAmount()) ;
					pcInfo.setIsCiting(true);
				}
				
				view = new EntityViewInfo();
				filter = new FilterInfo();
				filter.getFilterItems().add(new FilterItemInfo("head.contractChangeBill.id",null,CompareType.NOTEQUALS));
				filter.getFilterItems().add(new FilterItemInfo("programmingContract.id",pcInfo.getId().toString()));
				view.setFilter(filter);
				view.setSelector(sel);
				ContractPCSplitBillEntryCollection changeCol=ContractPCSplitBillEntryFactory.getLocalInstance(ctx).getContractPCSplitBillEntryCollection(view);
				for(int j=0;j<changeCol.size();j++){
					BigDecimal csAmount=FDCHelper.ZERO;
					EntityViewInfo csview = new EntityViewInfo();
					FilterInfo csfilter = new FilterInfo();
					csfilter.getFilterItems().add(new FilterItemInfo("head.contractChangeSettleBill.conChangeBill.id",changeCol.get(j).getHead().getContractChangeBill().getId().toString()));
					csfilter.getFilterItems().add(new FilterItemInfo("programmingContract.id",pcInfo.getId().toString()));
					csview.setFilter(csfilter);
					csview.setSelector(sel);
					ContractPCSplitBillEntryCollection csCol=ContractPCSplitBillEntryFactory.getLocalInstance(ctx).getContractPCSplitBillEntryCollection(csview);
					for(int k=0;k<csCol.size();k++){
						csAmount =FDCHelper.add(csAmount,csCol.get(k).getAmount()) ;
					}
					if(csAmount.compareTo(FDCHelper.ZERO)>0){
						changeAmount =FDCHelper.add(changeAmount,csAmount) ;
					}else{
						changeAmount =FDCHelper.add(changeAmount,changeCol.get(j).getAmount()) ;
					}
				}
				
				view = new EntityViewInfo();
				filter = new FilterInfo();
				filter.getFilterItems().add(new FilterItemInfo("head.contractChangeSettleBill.id",null,CompareType.NOTEQUALS));
				filter.getFilterItems().add(new FilterItemInfo("programmingContract.id",pcInfo.getId().toString()));
				view.setFilter(filter);
				view.setSelector(sel);
				ContractPCSplitBillEntryCollection csCol=ContractPCSplitBillEntryFactory.getLocalInstance(ctx).getContractPCSplitBillEntryCollection(view);
				for(int j=0;j<csCol.size();j++){
					FilterInfo cbfilter = new FilterInfo();
					cbfilter.getFilterItems().add(new FilterItemInfo("head.contractChangeBill.id",changeCol.get(j).getHead().getContractChangeSettleBill().getConChangeBill().getId().toString()));
					cbfilter.getFilterItems().add(new FilterItemInfo("programmingContract.id",pcInfo.getId().toString()));
					if(!ContractPCSplitBillEntryFactory.getLocalInstance(ctx).exists(cbfilter)){
						changeAmount =FDCHelper.add(changeAmount,changeCol.get(j).getAmount()) ;
					}
				}
			}else{
				for(int j=0;j<spcol.size();j++){
					settleAmount =FDCHelper.add(settleAmount,spcol.get(j).getAmount()) ;
				}
			}
			if(settleAmount.compareTo(FDCHelper.ZERO)>0){
				pcInfo.setBalance(FDCHelper.subtract(pcAmount, FDCHelper.add(amount, settleAmount)));
			}else{
				pcInfo.setBalance(FDCHelper.subtract(pcAmount, FDCHelper.add(amount, FDCHelper.add(signUpAmount, changeAmount))));
			}
			pcInfo.setSignUpAmount(FDCHelper.add(amount, signUpAmount));
		}else{
			pcInfo.setBalance(FDCHelper.subtract(pcAmount, amount));
			pcInfo.setSignUpAmount(amount);
		}
		SelectorItemCollection sict = new SelectorItemCollection();
		sict.add("balance");
		sict.add("signUpAmount");
		sict.add("isWTCiting");
		sict.add("isCiting");
		service.updatePartial(pcInfo, sict);
	}
	protected void _setSubmitStatus(Context ctx, BOSUuid billId) throws BOSException, EASBizException {
		super._setSubmitStatus(ctx, billId);
		PayRequestBillCollection pay=PayRequestBillFactory.getLocalInstance(ctx).getPayRequestBillCollection("select id from where contractId='"+billId+"'");
		if(pay.size()>0){
			PayRequestBillFactory.getLocalInstance(ctx).setSubmitStatus(pay.get(0).getId());
		}
	
		//MK
		ContractWithoutTextInfo info=ContractWithoutTextFactory.getLocalInstance(ctx).getContractWithoutTextInfo("select invoiceEntry.* from where id='"+billId+"'");
		if(info!=null){
			ContractWTInvoiceEntryCollection entrys = info.getInvoiceEntry();
			if(entrys.size()>0){
				for(int i=0;i<entrys.size();i++){
					ContractWTInvoiceEntryInfo invoiceInfo = entrys.get(i);
					invoiceInfo.setIsMKUsed(0);
					SelectorItemCollection sic=new SelectorItemCollection();
					sic.add("isMkUsed");
					ContractWTInvoiceEntryFactory.getLocalInstance(ctx).updatePartial(invoiceInfo,sic);
				}
			}
		}
	}
	protected void _setAudittingStatus(Context ctx, BOSUuid billId)throws BOSException, EASBizException {
		super._setAudittingStatus(ctx,billId);
		PayRequestBillCollection pay=PayRequestBillFactory.getLocalInstance(ctx).getPayRequestBillCollection("select id from where contractId='"+billId+"'");
		if(pay.size()>0){
			PayRequestBillFactory.getLocalInstance(ctx).setAudittingStatus(pay.get(0).getId());
		}
	}
	protected IObjectValue _getNoPValue(Context ctx, IObjectPK pk,SelectorItemCollection sel) throws BOSException {
		Connection cn = null;
        IObjectValue iobjectvalue;
        cn = getConnection(ctx);
        EntityViewInfo view = new EntityViewInfo();
        IMetaDataLoader loader = MetaDataLoaderFactory.getLocalMetaDataLoader(ctx);
        EntityObjectInfo bo = null;
        if((this instanceof ICommonBOSType) && ((ICommonBOSType)this).getPK() != null)
            bo = loader.getEntity(((ICommonBOSType)this).getPK());
        else
            bo = loader.getEntity(getBOSType());
        FilterInfo filterData = ImplUtils.createFilter(bo, pk);
        view.setFilter(filterData);
        if(sel!=null){
        	view.setSelector(sel);
        }else if(bo.getDefaultView() != null)
        {
            SelectorItemCollection selector = bo.getDefaultView().getSelector();
            view.setSelector(selector);
        }
        iobjectvalue = getDAO(ctx, cn).getValue(view);
        SQLUtils.cleanup(cn);
        return iobjectvalue;
	}
	protected void checkNumberDup(Context ctx, FDCBillInfo billInfo)throws BOSException, EASBizException{
		if(!isUseNumber())
			return;
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("number", billInfo.getNumber().trim()));
		if(billInfo.getId() != null)
			filter.getFilterItems().add(new FilterItemInfo("id", billInfo.getId().toString(), CompareType.NOTEQUALS));

		if(_exists(ctx, filter))
			throw new ContractException(ContractException.NUMBER_DUP);
		else return;
	}
	
	public Map getMKFP(Context ctx) throws BOSException, EASBizException {

		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		
		Map map=new HashMap();
		com.alibaba.fastjson.JSONObject login=new com.alibaba.fastjson.JSONObject();
		String tokenId=null;
		String entCode=null;
		String appCode=null;
		String secret=null;
		String mturl=null;
		Timestamp ts = new Timestamp(System.currentTimeMillis());
		long lt = ts.getTime();
		try {
			FDCSQLBuilder builder=new FDCSQLBuilder(ctx);
			builder.appendSql("select * from dl_mk where type = 'bx' ");
			IRowSet rs=builder.executeQuery();
			while(rs.next()){
				mturl=rs.getString("url");
				entCode=rs.getString("entCode");
				appCode=rs.getString("appCode");
				secret=rs.getString("secret");
			}
			login.put("appCode", appCode);
			login.put("secret", SHA(secret+lt));
			login.put("timestamp", lt);
			
			String respStr = HttpClientUtil.sendRequest(mturl+"/auth/login", "POST", "application/json;charse=UTF-8", "UTF-8", null, login.toJSONString());
			
			com.alibaba.fastjson.JSONObject crso = com.alibaba.fastjson.JSONObject.parseObject(respStr);
			com.alibaba.fastjson.JSONObject d=crso.getJSONObject("data");
			if(!"true".equals(crso.getString("success"))){
				throw new EASBizException(new NumericExceptionSubItem("100",crso.getString("message")));
			}else{
				tokenId=d.getString("tokenId");
				entCode=d.getString("entCode");
			}
		} catch (Exception e) {
			try {
				throw new EASBizException(new NumericExceptionSubItem("100",e.getMessage()));
			} catch (EASBizException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		
		//get invoices
		
		Date now = new Date();
		Calendar c = Calendar.getInstance();
		c.setTime(now);
		c.add(Calendar.MONTH, -1);
		Date ago = c.getTime();
		String exportAtEnd = simpleDateFormat.format(now);
		String exportAtStart=simpleDateFormat.format(ago);
		String employeeId=null;
		UserInfo u=UserFactory.getLocalInstance(ctx).getUserByID(ctx.getCaller());
	    employeeId = u.getNumber();
	    
		HashMap header=new HashMap();
		header.put("tokenId", tokenId);
		header.put("entCode", entCode);
		com.alibaba.fastjson.JSONObject dataJson=new com.alibaba.fastjson.JSONObject();
		dataJson.put("offset",0);
		dataJson.put("limit", 50);
		dataJson.put("exportAtStart", exportAtStart);
		dataJson.put("exportAtEnd", exportAtEnd);
		dataJson.put("employeeId", employeeId);
		try {
			String respStr = HttpClientUtil.sendRequest(mturl+"/invoice/search", "POST", "application/json;charse=UTF-8", "UTF-8", header, dataJson.toJSONString());
			com.alibaba.fastjson.JSONObject crso = com.alibaba.fastjson.JSONObject.parseObject(respStr);
			com.alibaba.fastjson.JSONArray fpArray=crso.getJSONArray("data");
			for (int i=0;i<fpArray.size();i++) {
				map.put(fpArray.getJSONObject(i).getString("invoiceNumber"), fpArray.getJSONObject(i));
				}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return map;
	}
	public static String SHA(final String strText) {
		MessageDigest messageDigest;
        String encodeStr = "";
		try {
			messageDigest = MessageDigest.getInstance("SHA-256");
            messageDigest.update(strText.getBytes("UTF-8"));
            encodeStr = byte2Hex(messageDigest.digest());
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return encodeStr;
	}
	private static String byte2Hex(byte[] bytes) {
        StringBuffer stringBuffer = new StringBuffer();
        String temp = null;
        for (int i = 0; i < bytes.length; i++) {
            temp = Integer.toHexString(bytes[i] & 0xFF);
            if (temp.length() == 1) {
                stringBuffer.append("0");
            }
            stringBuffer.append(temp);
        }
        return stringBuffer.toString();
    }
	
	public String _getMKLink(Context ctx, String number)
			throws BOSException, EASBizException {
		String viewLink=null;
	     com.alibaba.fastjson.JSONObject login=new com.alibaba.fastjson.JSONObject();
	     String tokenId=null;
	     String entCode=null;
	     String appCode=null;
	     String secret=null;
	     String mturl=null;
	     Timestamp ts = new Timestamp(System.currentTimeMillis());
	     long lt = ts.getTime();
	     try {
	         FDCSQLBuilder builder=new FDCSQLBuilder(ctx);
	         builder.appendSql("select * from dl_mk where type='da'");
	         IRowSet rs=builder.executeQuery();
	         while(rs.next()){
	             mturl=rs.getString("url");
	             entCode=rs.getString("entCode");
	             appCode=rs.getString("appCode");
	             secret=rs.getString("secret");
	         }
	         login.put("userCode", appCode);
	         login.put("secretToken", SHA(secret+":"+appCode+":"+lt));
	         login.put("timestamp", lt);

	         HttpClient httpClient =new HttpClient();
	         PostMethod post = new PostMethod(mturl+"/open/auth/login");
	         post.setRequestHeader("Content-Type", "application/json;charse=UTF-8") ;
	         post.setRequestBody(login.toJSONString());
	         httpClient.executeMethod(post);

	         String respStr = post.getResponseBodyAsString();
	         post.releaseConnection();

	         com.alibaba.fastjson.JSONObject crso = com.alibaba.fastjson.JSONObject.parseObject(respStr);
	         com.alibaba.fastjson.JSONObject d=crso.getJSONObject("data");
	         if(200!=(crso.getInteger("code"))){
	             throw new EASBizException(new NumericExceptionSubItem("100",crso.getString("message")));
	         }else{
	             tokenId=d.getString("token");
	         }
	     } catch (Exception e1) {
	         try {
	             throw new EASBizException(new NumericExceptionSubItem("100",e1.getMessage()));
	         } catch (EASBizException e11) {
	             // TODO Auto-generated catch block
	             e11.printStackTrace();
	         }
	     }

	     com.alibaba.fastjson.JSONObject dataJson=new com.alibaba.fastjson.JSONObject();
//			��ȡ��Ʊ�鿴����
	     try {
	         dataJson.put("invoiceNumber", number);
//				dataJson.put("invoiceCode", "3200211130");
	         HttpClient httpClient =new HttpClient();
	         PostMethod post = new PostMethod(mturl+"/open/ecm-invoice/search");
	         post.setRequestHeader("Content-Type", "application/json;charse=UTF-8") ;
	         post.setRequestHeader("Authorization", tokenId) ;
	         post.setRequestBody(dataJson.toJSONString());
	         httpClient.executeMethod(post);

	         String respStr = post.getResponseBodyAsString();
	         post.releaseConnection();
	         com.alibaba.fastjson.JSONObject crso = com.alibaba.fastjson.JSONObject.parseObject(respStr);
	         com.alibaba.fastjson.JSONObject data = crso.getJSONObject("data");
	         com.alibaba.fastjson.JSONArray pageDataArray=data.getJSONArray("pageData");

	         if(pageDataArray.size()>0){
	             com.alibaba.fastjson.JSONObject view = pageDataArray.getJSONObject(0);
	             if(view!=null){
	                 com.alibaba.fastjson.JSONArray fileUrls = view.getJSONArray("fileUrls");
	                 if(fileUrls!=null&&fileUrls.size()>0){
	                     String fileUrl = fileUrls.getString(0);
	                     if(fileUrl!=null&&fileUrl!=""){
//								��VIEWURL��ֵ
	                         viewLink = new StringBuffer().append("http://172.17.4.52:8080").append(fileUrl).toString();
	                     }
	                 }
	             }
	         }
	     } catch (Exception e1) {
	         throw new EASBizException(new NumericExceptionSubItem("100",e1.getMessage()));
	         // TODO Auto-generated catch block
	     }
	     return viewLink;	
	}
	 
}