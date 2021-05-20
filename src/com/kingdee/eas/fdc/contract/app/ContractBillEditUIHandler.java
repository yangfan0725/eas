/**
 * output package name
 */
package com.kingdee.eas.fdc.contract.app;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.fdc.basedata.CurProjectFactory;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.fdc.basedata.ProjectStatusInfo;
import com.kingdee.eas.fdc.contract.ContractBillInfo;
import com.kingdee.eas.fdc.contract.ContractException;
import com.kingdee.eas.framework.batchHandler.RequestConstant;
import com.kingdee.eas.framework.batchHandler.RequestContext;
import com.kingdee.eas.framework.batchHandler.ResponseContext;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.MsgBox;
import com.kingdee.util.StringUtils;


/**
 * output class name
 */
public class ContractBillEditUIHandler extends AbstractContractBillEditUIHandler
{
	protected void _handleInit(RequestContext request,ResponseContext response, Context context) throws Exception {
		super._handleInit(request,response,context);
	}
	
	protected void _handleActionSplit(RequestContext request,ResponseContext response, Context context) throws Exception {
	
	}
	
	protected void _handleActionViewContent(RequestContext request,ResponseContext response, Context context) throws Exception {
	
	}
	
	protected void _handleActionContractPlan(RequestContext request,ResponseContext response, Context context) throws Exception {
	
	}
	
	protected void _handleActionDelSplit(RequestContext request,ResponseContext response, Context context) throws Exception {
	
	}
	
	protected void _handleActionViewCost(RequestContext request,ResponseContext response, Context context) throws Exception {
	
	}
	
	protected void _handleActionSubmit(RequestContext request,ResponseContext response, Context context) throws Exception {
		super._handleActionSubmit(request,response,context);
	}
	
	/**
	 * ��ȡ���ݺ�ת��request��ҵ����֯,coreBillEditUIHandler�н�����֯ת��
	 * @param request
	 * @param response
	 * @param context
	 * @throws Exception
	 */
	protected void switchRequestOrg(RequestContext request,ResponseContext response, Context context) throws Exception
	{
	   	
	}
	/**
	 * ��ҵ��ʵ���������ݷ�������Ӧҵ���߼�
	 * @param request
	 * @param response
	 * @param context
	 */
	protected IObjectValue createNewData(RequestContext request,ResponseContext response, Context context) throws Exception
	{
		return null;
	}
	/**
	 * ����Ĭ��ֵ
	 * @param vo
	 * @throws Exception
	 */
	protected void applyDefaultValue(IObjectValue vo) throws Exception
	{
		
	}
	/**
	 * �ڷ����ʵ�ֿͻ��˵�rpc
	 * @param request
	 * @param response
	 * @param context
	 * @throws Exception
	 */
	protected void loadFields(RequestContext request,ResponseContext response, Context context) throws Exception
	{
		
	}
	/**
	 * ����У��
	 * @param request
	 * @param response
	 * @param context
	 * @throws Exception
	 */
	protected void verifyInput(RequestContext request,ResponseContext response, Context context) throws Exception
	{
		ContractBillInfo editData =  (ContractBillInfo)request.get(RequestConstant.FRAMEWORK_VALUE_KEY);
		super.verifyInput(request,response,context);
		
		//У���¼��
		String msg =null;
		
		if(msg==null && editData.getContractType()==null){
			msg = "��ͬ���Ͳ���Ϊ��";
			
		}	
		
		if(StringUtils.isEmpty(editData.getNumber())){
			msg = "���벻��Ϊ��";			
		}
		
		if(msg==null && StringUtils.isEmpty(editData.getName())){
			msg = "���Ʋ���Ϊ��";
			
		}
		if(msg==null && editData.getLandDeveloper()==null){
			msg = "�׷�����Ϊ��";
			
		}	
		if(msg==null && editData.getPartB()==null){
			
			msg = "�ҷ�����Ϊ��";
		}
		
		if(msg==null && editData.getRespDept()==null){
			msg = "���β��Ų���Ϊ��";
			
		}
		
		if(msg==null && editData.getOriginalAmount()==null){
			msg = "����Ϊ��";
			
		}
		
		if(msg==null && editData.getRespPerson()==null){
			msg = "�����˲���Ϊ��";
			
		}
		
//		�����Ŀ״̬�ѹرգ�����ѡ���Ƿ�ɱ���� 
		if(editData != null && editData.getCurProject() != null) {
			BOSUuid id = editData.getCurProject().getId();
			
			SelectorItemCollection selectors = new SelectorItemCollection();
			selectors.add("projectStatus");
			CurProjectInfo curProjectInfo = CurProjectFactory.getLocalInstance(context).getCurProjectInfo(new ObjectUuidPK(id), selectors);
		
			if(curProjectInfo.getProjectStatus() != null) {
				String closedState = ProjectStatusInfo.closeID;
				String projStateId = curProjectInfo.getProjectStatus().getId().toString();
				if(projStateId != null && projStateId.equals(closedState)) {
					if(editData.isIsCoseSplit()) {
						msg = "�ú�ͬ���ڵĹ�����Ŀ�Ѿ����ڹر�״̬�����ܽ��붯̬�ɱ�����ȡ��\"���붯̬�ɱ�\"��ѡ���ٱ���/�ύ";
//						MsgBox.showWarning(this, "�ú�ͬ���ڵĹ�����Ŀ�Ѿ����ڹر�״̬�����ܽ��붯̬�ɱ�����ȡ��\"���붯̬�ɱ�\"��ѡ���ٱ���/�ύ");
//						SysUtil.abort();
						
					}
				}
			}
		}
		
		if(msg!=null){
			throw new ContractException(ContractException.WITHMSG,new Object[]{msg});
		}
	}
	/**
	 * ��������ʱ�������ݶ���
	 * @param request
	 * @param response
	 * @param context
	 * @throws Exception
	 */
	protected void storeFields(RequestContext request,ResponseContext response, Context context) throws Exception
	{
		
	}

	protected void _handleActionViewBgBalance(RequestContext request,
			ResponseContext response, Context context) throws Exception {
		
	}

	protected void _handleActionViewAttachmentSelf(RequestContext request,
			ResponseContext response, Context context) throws Exception {
		// TODO Auto-generated method stub
		
	}

	protected void _handleActionViewContentSelf(RequestContext request,
			ResponseContext response, Context context) throws Exception {
		// TODO Auto-generated method stub
		
	}
	
}