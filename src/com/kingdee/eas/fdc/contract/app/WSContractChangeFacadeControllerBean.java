package com.kingdee.eas.fdc.contract.app;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import javax.ejb.*;
import java.rmi.RemoteException;
import java.sql.Timestamp;
import java.util.Date;

import com.kingdee.bos.*;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.bos.metadata.IMetaDataPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
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

import com.kingdee.eas.base.attachment.AttachmentFactory;
import com.kingdee.eas.base.attachment.AttachmentInfo;
import com.kingdee.eas.base.attachment.AttachmentStorageTypeEnum;
import com.kingdee.eas.base.attachment.BoAttchAssoFactory;
import com.kingdee.eas.base.attachment.BoAttchAssoInfo;
import com.kingdee.eas.base.attachment.FtpConfigCollection;
import com.kingdee.eas.base.attachment.FtpConfigFactory;
import com.kingdee.eas.base.message.BMCMessageFactory;
import com.kingdee.eas.base.message.BMCMessageInfo;
import com.kingdee.eas.base.message.IBMCMessage;
import com.kingdee.eas.base.message.MsgBizType;
import com.kingdee.eas.base.message.MsgPriority;
import com.kingdee.eas.base.message.MsgStatus;
import com.kingdee.eas.base.message.MsgType;
import com.kingdee.eas.base.permission.UserCollection;
import com.kingdee.eas.base.permission.UserFactory;
import com.kingdee.eas.base.permission.UserInfo;
import com.kingdee.eas.base.receiver.BasReceiverCollection;
import com.kingdee.eas.base.receiver.BasReceiverInfo;
import com.kingdee.eas.basedata.assistant.CurrencyFactory;
import com.kingdee.eas.basedata.org.FullOrgUnitInfo;
import com.kingdee.eas.basedata.org.OrgType;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.fdc.basedata.ChangeReasonCollection;
import com.kingdee.eas.fdc.basedata.ChangeReasonFactory;
import com.kingdee.eas.fdc.basedata.ChangeTypeCollection;
import com.kingdee.eas.fdc.basedata.ChangeTypeFactory;
import com.kingdee.eas.fdc.basedata.CurProjectCollection;
import com.kingdee.eas.fdc.basedata.CurProjectFactory;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCDateHelper;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.ProjectWithCostCenterOUCollection;
import com.kingdee.eas.fdc.basedata.ProjectWithCostCenterOUFactory;
import com.kingdee.eas.fdc.basedata.ProjectWithCostCenterOUInfo;
import com.kingdee.eas.fdc.basedata.SourceTypeEnum;
import com.kingdee.eas.fdc.basedata.SpecialtyTypeCollection;
import com.kingdee.eas.fdc.basedata.SpecialtyTypeFactory;
import com.kingdee.eas.fdc.basedata.client.FDCClientUtils;
import com.kingdee.eas.fdc.contract.ChangeAuditBillFactory;
import com.kingdee.eas.fdc.contract.ChangeAuditBillInfo;
import com.kingdee.eas.fdc.contract.ChangeAuditEntryFactory;
import com.kingdee.eas.fdc.contract.ChangeAuditEntryInfo;
import com.kingdee.eas.fdc.contract.ChangeBillStateEnum;
import com.kingdee.eas.fdc.contract.ChangeSupplierEntryInfo;
import com.kingdee.eas.fdc.contract.ChangeUrgentDegreeEnum;
import com.kingdee.eas.fdc.contract.ConChangeSettleEntryInfo;
import com.kingdee.eas.fdc.contract.ContractBillCollection;
import com.kingdee.eas.fdc.contract.ContractBillFactory;
import com.kingdee.eas.fdc.contract.ContractChangeBillCollection;
import com.kingdee.eas.fdc.contract.ContractChangeBillFactory;
import com.kingdee.eas.fdc.contract.ContractChangeBillInfo;
import com.kingdee.eas.fdc.contract.ContractChangeEntryCollection;
import com.kingdee.eas.fdc.contract.ContractChangeSettleBillFactory;
import com.kingdee.eas.fdc.contract.ContractChangeSettleBillInfo;
import com.kingdee.eas.fdc.contract.ContractSettlementSubmissionCollection;
import com.kingdee.eas.fdc.contract.ContractSettlementSubmissionFactory;
import com.kingdee.eas.fdc.contract.ContractSettlementSubmissionInfo;
import com.kingdee.eas.fdc.contract.FDCUtils;
import com.kingdee.eas.fdc.contract.GraphCountEnum;
import com.kingdee.eas.fdc.contract.OfferEnum;
import com.kingdee.eas.fdc.contract.ResponsibleStyleEnum;
import com.kingdee.eas.fdc.contract.SpecialtyTypeEntryInfo;
import com.kingdee.eas.fdc.contract.SupplierContentEntryInfo;
import com.kingdee.eas.fm.common.ContextHelperFactory;
import com.kingdee.eas.fm.common.IContextHelper;

public class WSContractChangeFacadeControllerBean extends AbstractWSContractChangeFacadeControllerBean
{
    private static Logger logger =
        Logger.getLogger("com.kingdee.eas.fdc.contract.app.WSContractChangeFacadeControllerBean");

	protected String _synContractChange(Context ctx, String str)
			throws BOSException, EASBizException {
		logger.info(str);
		JSONObject obj = JSONObject.fromObject(str);
		String FID=obj.getString("FID");
		String FCurProjectID=obj.getString("FCurProjectID");
		String FContractBillID=obj.getString("FContractBillID");
		String FName=obj.getString("FName");
		String FNumber=obj.getString("FNumber");
		String FChangeType=obj.getString("FChangeType");
		String FChangeReason=obj.getString("FChangeReason");
		String FSpecialtyType=obj.getString("FSpecialtyType");
		String FCostAmount=obj.getString("FCostAmount");
		String FBizDate=obj.getString("FBizDate");
		String FReaDesc=obj.getString("FReaDesc");
		String FChangeContent=obj.getString("FChangeContent");
		String FDescription=obj.getString("FDescription");
		String FCreatorNumber=obj.getString("FCreatorNumber");
		
		ChangeAuditBillInfo info=new ChangeAuditBillInfo();
		info.setSourceBillId(FID);
		info.setNumber(FNumber);
		info.setName(FName);
		info.setDescription(FDescription);
		info.setReaDesc(FReaDesc);
		info.setBookedDate(FDCDateHelper.stringToDate(FBizDate));
		info.setSourceType(SourceTypeEnum.IMP);
		info.setUrgentDegree(ChangeUrgentDegreeEnum.Normal);
		info.setChangeState(ChangeBillStateEnum.Saved);
		info.setGraphCount(GraphCountEnum.NoFile);
		info.setIsAlreadyExecuted(false);
		info.setState(FDCBillStateEnum.SAVED);
		info.setChangeState(ChangeBillStateEnum.Saved);
		info.setTotalCost(new BigDecimal(FCostAmount));
    	
		JSONObject rs = new JSONObject();
		CurProjectCollection projectCol=CurProjectFactory.getLocalInstance(ctx).getCurProjectCollection("select CU.*,* from where id='"+FCurProjectID+"'");
		if(projectCol.size()>0){
			info.setCurProject(projectCol.get(0));
			info.setCurProjectName(projectCol.get(0).getName());
			info.setCU(projectCol.get(0).getCU());
			
			 EntityViewInfo view = new EntityViewInfo();
			 FilterInfo filter = new FilterInfo();
			 filter.getFilterItems().add(new FilterItemInfo("curProject.id", FCurProjectID));
			 view.setFilter(filter);
			 view.getSelector().add("costCenterOU.*");
			 ProjectWithCostCenterOUCollection pwcc= ProjectWithCostCenterOUFactory.getLocalInstance(ctx).getProjectWithCostCenterOUCollection(view);
			 
			 if(pwcc.size() > 0){
				 ProjectWithCostCenterOUInfo ouinfo = pwcc.get(0);
				 info.setOrgUnit(ouinfo.getCostCenterOU().castToFullOrgUnitInfo());
			 }
		}else{
			rs.put("state", "0");
			rs.put("msg", "工程项目不存在！");
			return rs.toString();
		}
		ChangeTypeCollection changeTypeCol=ChangeTypeFactory.getLocalInstance(ctx).getChangeTypeCollection("select * from where name='"+FChangeType+"'");
		if(changeTypeCol.size()>0){
			info.setAuditType(changeTypeCol.get(0));
			info.setAuditTypeName(changeTypeCol.get(0).getName());
		}else{
			rs.put("state", "0");
			rs.put("msg", "变更类型不存在！");
			return rs.toString();
		}
		ChangeReasonCollection changeReansonCol=ChangeReasonFactory.getLocalInstance(ctx).getChangeReasonCollection("select * from where name='"+FChangeReason+"'");
		if(changeReansonCol.size()>0){
			info.setChangeReason(changeReansonCol.get(0));
		}else{
			rs.put("state", "0");
			rs.put("msg", "变更原因不存在！");
			return rs.toString();
		}
		info.setOffer(OfferEnum.DESIGNCOM);
		ContractBillCollection contractCol=ContractBillFactory.getLocalInstance(ctx).getContractBillCollection("select * from where id='"+FContractBillID+"'");
		if(contractCol.size()>0){
			info.setConductUnit(contractCol.get(0).getPartB());
			info.setPeriod(contractCol.get(0).getPeriod());
			
			ChangeSupplierEntryInfo changeSupplierEntryInfo=new ChangeSupplierEntryInfo();
			changeSupplierEntryInfo.setContractBill(contractCol.get(0));
			changeSupplierEntryInfo.setMainSupp(contractCol.get(0).getPartB());
			changeSupplierEntryInfo.setCostAmount(new BigDecimal(FCostAmount));
			changeSupplierEntryInfo.setOriCostAmount(new BigDecimal(FCostAmount));
			changeSupplierEntryInfo.setCurrency(CurrencyFactory.getLocalInstance(ctx).getCurrencyCollection("select * from where number='RMB'").get(0));
			changeSupplierEntryInfo.setExRate(new BigDecimal(1));
			changeSupplierEntryInfo.setConstructPrice(new BigDecimal(FCostAmount));
			
			info.setTotalConstructPrice(new BigDecimal(FCostAmount));
			
			ChangeAuditEntryInfo changeAuditEntryInfo=new ChangeAuditEntryInfo();
			changeAuditEntryInfo.setChangeContent(FChangeContent);
			changeAuditEntryInfo.setSeq(1);
			changeAuditEntryInfo.setNumber("A");
			
			SupplierContentEntryInfo supplierContentEntryInfo=new SupplierContentEntryInfo();
			supplierContentEntryInfo.setContent(changeAuditEntryInfo);
			changeSupplierEntryInfo.getEntrys().add(supplierContentEntryInfo);
			
			info.getSuppEntry().add(changeSupplierEntryInfo);
			info.getEntrys().add(changeAuditEntryInfo);
		}else{
			rs.put("state", "0");
			rs.put("msg", "合同不存在！");
			return rs.toString();
		}
		SpecialtyTypeCollection specialtyTypeCol=SpecialtyTypeFactory.getLocalInstance(ctx).getSpecialtyTypeCollection("select * from where name='"+FSpecialtyType+"'");
		if(specialtyTypeCol.size()>0){
			SpecialtyTypeEntryInfo specialtyTypeEntryInfo=new SpecialtyTypeEntryInfo();
			specialtyTypeEntryInfo.setSpecialtyType(specialtyTypeCol.get(0));
			info.getMutiSpecialtyType().add(specialtyTypeEntryInfo);
		}else{
			rs.put("state", "0");
			rs.put("msg", "专业类型不存在！");
			return rs.toString();
		}
		UserCollection userCol=UserFactory.getLocalInstance(ctx).getUserCollection("select * from where number='"+FCreatorNumber+"'");
		if(userCol.size()>0){
			info.setCreator(userCol.get(0));
			info.setLastUpdateUser(userCol.get(0));
		}else{
			rs.put("state", "0");
			rs.put("msg", "制单人不存在！");
			return rs.toString();
		}
		String id=ChangeAuditBillFactory.getLocalInstance(ctx).addnew(info).toString();
		
		JSONArray attach=obj.getJSONArray("attach");
		for(int i=0;i<attach.size();i++){
			String FAttachName=attach.getJSONObject(i).getString("FName");
			String FRemotePath=attach.getJSONObject(i).getString("FRemotePath");
			
			AttachmentInfo attachmentInfo=new AttachmentInfo();
			attachmentInfo.setName(FAttachName);
			attachmentInfo.setRemotePath(FRemotePath);
			attachmentInfo.setStorageType(AttachmentStorageTypeEnum.FTP);
			attachmentInfo.setFtp(FtpConfigFactory.getLocalInstance(ctx).getFtpConfigCollection("select * from where name='OA'").get(0));
			attachmentInfo.setSimpleName(FRemotePath.substring(FRemotePath.lastIndexOf(".")+1, FRemotePath.length()));
			
			BoAttchAssoInfo boAttchAssoInfo=new BoAttchAssoInfo();
			boAttchAssoInfo.setAssoType("天联云附件");
			boAttchAssoInfo.setBoID(id);
			boAttchAssoInfo.setAttachment(attachmentInfo);
			
			attachmentInfo.getBoAttchAsso().add(boAttchAssoInfo);
			
			AttachmentFactory.getLocalInstance(ctx).save(attachmentInfo);
		}
		
		rs.put("state", "1");
		rs.put("msg", "同步成功！");
		
		
		IBMCMessage iBMCMessage= BMCMessageFactory.getLocalInstance(ctx);

		BMCMessageInfo msgInfo = new BMCMessageInfo();
		msgInfo.setTitle("请处理天联云发送变更审批单 编号："+info.getNumber()+"变更名称："+info.getName());
		msgInfo.setBody("请处理天联云发送变更审批单 编号："+info.getNumber()+"变更名称："+info.getName());
		msgInfo.setPriority(MsgPriority.HIGH);
		msgInfo.setReceivers(info.getCreator().getName());
		msgInfo.setNreceivers(info.getCreator().getName());
		msgInfo.setType(MsgType.NOTICE);
		msgInfo.setBizType(MsgBizType.XITONGOFFICE);
		msgInfo.setStatus(MsgStatus.UNREADED);
		msgInfo.setId(BOSUuid.create(msgInfo.getBOSType()));
		msgInfo.setSourceIDs(id);

		msgInfo.setOrgType(OrgType.ControlUnit);
		msgInfo.setOrgID(info.getOrgUnit().getId().toString());
		msgInfo.setSender("系统");

		BasReceiverInfo receiverInfo = new BasReceiverInfo();
		receiverInfo.setType("1");
		receiverInfo.setValue(info.getCreator().getId().toString());
		receiverInfo.setDesc(info.getCreator().getName());

		receiverInfo.setIsShow(false);
		BasReceiverCollection receivercoll = new BasReceiverCollection();
		receivercoll.add(receiverInfo);
		iBMCMessage.addHandMsg(msgInfo, receivercoll);
		
		return rs.toString();
	}

	protected String _synChangeSettlement(Context ctx, String str)
			throws BOSException, EASBizException {
		logger.info(str);
		JSONObject obj = JSONObject.fromObject(str);
		String FID=obj.getString("FID");
		String FChangeID=obj.getString("FChangeID");
		String FAmount=obj.getString("FAmount");
		String FColseDescription=obj.getString("FColseDescription");
		String FCreatorNumber=obj.getString("FCreatorNumber");
		String FNumber=obj.getString("FNumber");
		String FIsFee=obj.getString("FIsFee");
		
		ContractChangeSettleBillInfo info=new ContractChangeSettleBillInfo();
		info.setBookedDate(new Date());
		info.setBizDate(new Date());
		info.setSourceBillId(FID);
		info.setColseDescription(FColseDescription);
		info.setState(FDCBillStateEnum.SAVED);
		
		JSONObject rs = new JSONObject();
		
		ContractChangeBillCollection contractChangeBillCol=null;
		if(BOSUuid.isValid(FChangeID, true)){
			contractChangeBillCol=ContractChangeBillFactory.getLocalInstance(ctx).getContractChangeBillCollection("select entry.*,* from where id='"+FChangeID+"'");
		}else{
			contractChangeBillCol=ContractChangeBillFactory.getLocalInstance(ctx).getContractChangeBillCollection("select entry.*,* from where sourceBillId='"+FChangeID+"'");
		}
		if(contractChangeBillCol.size()>0){
			ContractChangeBillInfo contractChangeInfo=contractChangeBillCol.get(0);
			info.setName(contractChangeInfo.getName()+"确认");
			info.setConChangeBill(contractChangeInfo);
			info.setContractBill(contractChangeInfo.getContractBill());
			info.setCurProject(contractChangeInfo.getCurProject());
			info.setSupplier(contractChangeInfo.getMainSupp());
			info.setResponsibleStyle(ResponsibleStyleEnum.AllContain);
			ContractChangeEntryCollection  coll = contractChangeInfo.getEntrys();
			StringBuffer sb = new StringBuffer();
			for(int i = 0 ; i < coll.size() ; i ++){
				sb.append(",");
				sb.append(coll.get(i).getChangeContent());
			}
			info.setChangeReson(sb.toString().replaceFirst(",", ""));
			info.setOriginalAmount(contractChangeInfo.getContractBill().getOriginalAmount());
			info.setAmount(contractChangeInfo.getContractBill().getAmount());
			info.setOrgUnit(contractChangeInfo.getOrgUnit());
			info.setReportAmount(new BigDecimal(FAmount));
			info.setIsFinish(true);
			String cotractId = contractChangeInfo.getContractBill().getId().toString();
			info.setLastAmount(FDCUtils.getContractLastAmt(ctx, cotractId));
			info.setNumber(FNumber);
			info.setChangeReson(contractChangeInfo.getEntrys().get(0).getChangeContent());
			if(FIsFee.equals("1")){
				info.setIsFee(true);
			}else{
				info.setIsFee(false);
			}
		}else{
			rs.put("state", "0");
			rs.put("msg", "合同变更不存在！");
			return rs.toString();
		}
		JSONArray entryjs=obj.getJSONArray("entry");
		BigDecimal allowAmount=FDCHelper.ZERO;
		for(int i=0;i<entryjs.size();i++){
			String FChangeReason=entryjs.getJSONObject(i).getString("FChangeReason");
			String FEntryAmount=entryjs.getJSONObject(i).getString("FAmount");
			String FUnit=entryjs.getJSONObject(i).getString("FUnit");
			String FProNumber=entryjs.getJSONObject(i).getString("FProNumber");
				
			String FTotalAmount=entryjs.getJSONObject(i).getString("FTotalAmount");
			
			ConChangeSettleEntryInfo conChangeSettleEntryInfo=new ConChangeSettleEntryInfo();
			conChangeSettleEntryInfo.setChangeContent(FChangeReason);
			conChangeSettleEntryInfo.setAmount(new BigDecimal(FEntryAmount));
			conChangeSettleEntryInfo.setUnit(FUnit);
			if(FProNumber!=null&&!FProNumber.equals("null")){
				conChangeSettleEntryInfo.setProNumber(new BigDecimal(FProNumber));
			}
			conChangeSettleEntryInfo.setTotalAmount(new BigDecimal(FTotalAmount));
			
			info.getEntrys().add(conChangeSettleEntryInfo);
			
			allowAmount=allowAmount.add(new BigDecimal(FTotalAmount));
		}
		info.setAllowAmount(allowAmount);
		
		UserCollection userCol=UserFactory.getLocalInstance(ctx).getUserCollection("select * from where number='"+FCreatorNumber+"'");
		if(userCol.size()>0){
			info.setCreator(userCol.get(0));
			info.setLastUpdateUser(userCol.get(0));
		}else{
			rs.put("state", "0");
			rs.put("msg", "制单人不存在！");
			return rs.toString();
		}
		String id=ContractChangeSettleBillFactory.getLocalInstance(ctx).addnew(info).toString();
		
		JSONArray attach=obj.getJSONArray("attach");
		for(int i=0;i<attach.size();i++){
			String FAttachName=attach.getJSONObject(i).getString("FName");
			String FRemotePath=attach.getJSONObject(i).getString("FRemotePath");
			
			AttachmentInfo attachmentInfo=new AttachmentInfo();
			attachmentInfo.setName(FAttachName);
			attachmentInfo.setRemotePath(FRemotePath);
			attachmentInfo.setStorageType(AttachmentStorageTypeEnum.FTP);
			attachmentInfo.setFtp(FtpConfigFactory.getLocalInstance(ctx).getFtpConfigCollection("select * from where name='OA'").get(0));
			attachmentInfo.setSimpleName(FRemotePath.substring(FRemotePath.lastIndexOf(".")+1, FRemotePath.length()));
			
			BoAttchAssoInfo boAttchAssoInfo=new BoAttchAssoInfo();
			boAttchAssoInfo.setAssoType("天联云附件");
			boAttchAssoInfo.setBoID(id);
			boAttchAssoInfo.setAttachment(attachmentInfo);
			
			attachmentInfo.getBoAttchAsso().add(boAttchAssoInfo);
			
			AttachmentFactory.getLocalInstance(ctx).save(attachmentInfo);
		}
		
		rs.put("state", "1");
		rs.put("msg", "同步成功！");
		
		IBMCMessage iBMCMessage= BMCMessageFactory.getLocalInstance(ctx);

		BMCMessageInfo msgInfo = new BMCMessageInfo();
		msgInfo.setTitle("请处理天联云发送变更确认单 编号："+info.getNumber()+"确认名称："+info.getName());
		msgInfo.setBody("请处理天联云发送变更确认单 编号："+FNumber+"确认名称："+info.getName());
		msgInfo.setPriority(MsgPriority.HIGH);
		msgInfo.setReceivers(info.getCreator().getName());
		msgInfo.setNreceivers(info.getCreator().getName());
		msgInfo.setType(MsgType.NOTICE);
		msgInfo.setBizType(MsgBizType.XITONGOFFICE);
		msgInfo.setStatus(MsgStatus.UNREADED);
		msgInfo.setId(BOSUuid.create(msgInfo.getBOSType()));
		msgInfo.setSourceIDs(id);

		msgInfo.setOrgType(OrgType.ControlUnit);
		msgInfo.setOrgID(info.getOrgUnit().getId().toString());
		msgInfo.setSender("系统");

		BasReceiverInfo receiverInfo = new BasReceiverInfo();
		receiverInfo.setType("1");
		receiverInfo.setValue(info.getCreator().getId().toString());
		receiverInfo.setDesc(info.getCreator().getName());

		receiverInfo.setIsShow(false);
		BasReceiverCollection receivercoll = new BasReceiverCollection();
		receivercoll.add(receiverInfo);
		iBMCMessage.addHandMsg(msgInfo, receivercoll);
		return rs.toString();
	}

	protected String _synContractSettlementSubmission(Context ctx, String str)
			throws BOSException, EASBizException {
		logger.info(str);
		JSONObject obj = JSONObject.fromObject(str);
		String FID=obj.getString("FID");
		String FCurProjectID=obj.getString("FCurProjectID");
		String FContractBillID=obj.getString("FContractBillID");
		String FNumber=obj.getString("FNumber");
		String FAmount=obj.getString("FAmount");
		String FBizDate=obj.getString("FBizDate");
		String FApplier=obj.getString("FApplier");
		String FPartAer=obj.getString("FPartAer");
		String FCCer=obj.getString("FCCer");
		String FScope=obj.getString("FScope");
		String FOAID=obj.getString("FOAID");
		String FDescription=obj.getString("FDescription");
	
		ContractSettlementSubmissionCollection col=ContractSettlementSubmissionFactory.getLocalInstance(ctx).getContractSettlementSubmissionCollection("select * from where sourceBillId='"+FID+"'");
		ContractSettlementSubmissionInfo info=null;
		if(col.size()>0){
			info=col.get(0);
		}else{
			info=new ContractSettlementSubmissionInfo();
		}
		info.setState(FDCBillStateEnum.AUDITTING);
		info.setSourceBillId(FID);
		info.setNumber(FNumber);
		info.setBizDate(FDCDateHelper.stringToDate(FBizDate));
		info.setAmount(new BigDecimal(FAmount));
		info.setApplier(FApplier);
		info.setPartAer(FPartAer);
		info.setCCer(FCCer);
		info.setScope(FScope);
		info.setSourceFunction(FOAID);
		info.setDescription(FDescription);
		
		JSONObject rs = new JSONObject();
		CurProjectCollection projectCol=CurProjectFactory.getLocalInstance(ctx).getCurProjectCollection("select CU.*,* from where id='"+FCurProjectID+"'");
		if(projectCol.size()>0){
			info.setCurProject(projectCol.get(0));
			info.setCU(projectCol.get(0).getCU());
			
			 EntityViewInfo view = new EntityViewInfo();
			 FilterInfo filter = new FilterInfo();
			 filter.getFilterItems().add(new FilterItemInfo("curProject.id", FCurProjectID));
			 view.setFilter(filter);
			 view.getSelector().add("costCenterOU.*");
			 ProjectWithCostCenterOUCollection pwcc= ProjectWithCostCenterOUFactory.getLocalInstance(ctx).getProjectWithCostCenterOUCollection(view);
			 
			 if(pwcc.size() > 0){
				 ProjectWithCostCenterOUInfo ouinfo = pwcc.get(0);
				 info.setOrgUnit(ouinfo.getCostCenterOU().castToFullOrgUnitInfo());
			 }
		}else{
			rs.put("state", "0");
			rs.put("msg", "工程项目不存在！");
			return rs.toString();
		}
		
		ContractBillCollection contractCol=ContractBillFactory.getLocalInstance(ctx).getContractBillCollection("select * from where id='"+FContractBillID+"'");
		if(contractCol.size()>0){
			info.setContractBill(contractCol.get(0));
		}else{
			rs.put("state", "0");
			rs.put("msg", "合同不存在！");
			return rs.toString();
		}
		String id=ContractSettlementSubmissionFactory.getLocalInstance(ctx).save(info).toString();
		
		FilterInfo filter=new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("boID",id));
		BoAttchAssoFactory.getLocalInstance(ctx).delete(filter);
		
		JSONArray attach=obj.getJSONArray("attach");
		for(int i=0;i<attach.size();i++){
			String FAttachName=attach.getJSONObject(i).getString("FName");
			String FRemotePath=attach.getJSONObject(i).getString("FRemotePath");
			
			AttachmentInfo attachmentInfo=new AttachmentInfo();
			attachmentInfo.setName(FAttachName);
			attachmentInfo.setRemotePath(FRemotePath);
			attachmentInfo.setStorageType(AttachmentStorageTypeEnum.FTP);
			attachmentInfo.setFtp(FtpConfigFactory.getLocalInstance(ctx).getFtpConfigCollection("select * from where name='天联云'").get(0));
			attachmentInfo.setSimpleName(FRemotePath.substring(FRemotePath.lastIndexOf(".")+1, FRemotePath.length()));
			
			BoAttchAssoInfo boAttchAssoInfo=new BoAttchAssoInfo();
			boAttchAssoInfo.setAssoType("天联云附件");
			boAttchAssoInfo.setBoID(id);
			boAttchAssoInfo.setAttachment(attachmentInfo);
			
			attachmentInfo.getBoAttchAsso().add(boAttchAssoInfo);
			
			AttachmentFactory.getLocalInstance(ctx).save(attachmentInfo);
		}
		
		rs.put("state", "1");
		rs.put("msg", "同步成功！");
		return rs.toString();
	}
	protected String _synCSSubmissionState(Context ctx, String str)
			throws BOSException, EASBizException {
		logger.info(str);
		JSONObject obj = JSONObject.fromObject(str);
		String FID=obj.getString("FID");
		String FState=obj.getString("FState");
		
		JSONObject rs = new JSONObject();
		ContractSettlementSubmissionCollection col=ContractSettlementSubmissionFactory.getLocalInstance(ctx).getContractSettlementSubmissionCollection("select * from where sourceBillId='"+FID+"'");
		if(col.size()>0){
			ContractSettlementSubmissionInfo info=col.get(0);
			info.setState(FDCBillStateEnum.getEnum(FState));
			
			SelectorItemCollection sic=new SelectorItemCollection();
			sic.add("state");
			ContractSettlementSubmissionFactory.getLocalInstance(ctx).updatePartial(info, sic);
		}else{
			rs.put("state", "0");
			rs.put("msg", "合同结算送审单不存在！");
			return rs.toString();
		}
		rs.put("state", "1");
		rs.put("msg", "同步成功！");
		return rs.toString();
	}

	protected String _synCSSubmissionAttach(Context ctx, String str)
			throws BOSException, EASBizException {
		logger.info(str);
		JSONObject obj = JSONObject.fromObject(str);
		String FID=obj.getString("FID");
		
		JSONObject rs = new JSONObject();
		ContractSettlementSubmissionCollection col=ContractSettlementSubmissionFactory.getLocalInstance(ctx).getContractSettlementSubmissionCollection("select * from where sourceBillId='"+FID+"'");
		if(col.size()>0){
			String id=col.get(0).getId().toString();
			
			FilterInfo filter=new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("boID",id));
			BoAttchAssoFactory.getLocalInstance(ctx).delete(filter);
			
			JSONArray attach=obj.getJSONArray("attach");
			if(attach!=null){
				for(int i=0;i<attach.size();i++){
					String FAttachName=attach.getJSONObject(i).getString("FName");
					String FRemotePath=attach.getJSONObject(i).getString("FRemotePath");
					
					AttachmentInfo attachmentInfo=new AttachmentInfo();
					attachmentInfo.setName(FAttachName);
					attachmentInfo.setRemotePath(FRemotePath);
					attachmentInfo.setStorageType(AttachmentStorageTypeEnum.FTP);
					attachmentInfo.setFtp(FtpConfigFactory.getLocalInstance(ctx).getFtpConfigCollection("select * from where name='OA'").get(0));
					attachmentInfo.setSimpleName(FRemotePath.substring(FRemotePath.lastIndexOf(".")+1, FRemotePath.length()));
					
					BoAttchAssoInfo boAttchAssoInfo=new BoAttchAssoInfo();
					boAttchAssoInfo.setAssoType("天联云附件");
					boAttchAssoInfo.setBoID(id);
					boAttchAssoInfo.setAttachment(attachmentInfo);
					
					attachmentInfo.getBoAttchAsso().add(boAttchAssoInfo);
					
					AttachmentFactory.getLocalInstance(ctx).save(attachmentInfo);
				}
			}
		}else{
			rs.put("state", "0");
			rs.put("msg", "合同结算送审单不存在！");
			return rs.toString();
		}
		rs.put("state", "1");
		rs.put("msg", "同步成功！");
		return rs.toString();
	}
}