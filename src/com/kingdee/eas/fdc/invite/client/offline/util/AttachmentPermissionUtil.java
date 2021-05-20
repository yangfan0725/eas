package com.kingdee.eas.fdc.invite.client.offline.util;

import java.util.Set;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.workflow.ActivityInstInfo;
import com.kingdee.bos.workflow.AssignmentInfo;
import com.kingdee.bos.workflow.ProcessInstInfo;
import com.kingdee.bos.workflow.metas.WfAssignmentState;
import com.kingdee.bos.workflow.metas.WfState;
import com.kingdee.bos.workflow.service.ormrpc.EnactmentServiceFactory;
import com.kingdee.bos.workflow.service.ormrpc.IEnactmentService;
import com.kingdee.bos.workflow.service.ormrpc.IWfFacade;
import com.kingdee.bos.workflow.service.ormrpc.WfFacadeFactory;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fi.cas.BillStatusEnum;

/**
 * �б긽������������״̬�ж�
 * @author guangyue_liu
 * 2010-04-29
 */
public class AttachmentPermissionUtil {

	
	/**
	 * �жϸ����������Ƿ���ã���������״̬��ֻ�е�ǰ���̵�ִ���˲ſ��Խ��б༭��������ֻ���Ǳ༭�Լ��ϴ���
	 * @param state
	 * @param billID
	 * @return
	 * @throws BOSException
	 */
	public static boolean checkAuditingAttachmentEdit(Object state,String billID,boolean isEdit) throws BOSException{
		//�����������״̬�����жϵ�ǰ�������̶�Ӧ��ִ���ǲ��ǵ�ǰ��¼�����������Խ��б༭����������
		if (state != null
				&& (state.toString().equals(FDCBillStateEnum.AUDITTING.toString())
					||state.toString().equals(BillStatusEnum.AUDITING.toString()))) {
			IEnactmentService service2 = EnactmentServiceFactory.createRemoteEnactService();
			ProcessInstInfo[] processInsts = service2.getProcessInstanceByHoldedObjectId(billID);
			if(processInsts != null && processInsts.length > 0 && processInsts[0]!=null){
				//��ȡ��һ������ʵ��
				String procInstId = processInsts[0].getProcInstId();
				
				ActivityInstInfo[] actInsts = service2.getActInstMetaArrayByProcInstId(procInstId);
				ActivityInstInfo currentactInst = null;
				for(int i = 0;i < actInsts.length;i ++){
					if(actInsts[i] != null 
							&& WfState.NOT_STARTED_VALUE.equals(actInsts[i].getState())){
						currentactInst = actInsts[i];
					}
				}
		
				if(currentactInst!=null){
					//�õ���ǰ�δ��ʼ������Ľڵ��ִ����
//					IWfFacade facade=WfFacadeFactory.getRemoteInstance();
//			        Set userIds=facade.getPerformerUserIds(currentactInst.getActInstId());
			        
			        IEnactmentService esc = EnactmentServiceFactory.createRemoteEnactService();
	
			        AssignmentInfo[] assignments = esc.getAllAssignmentByActInstId(currentactInst.getActInstId());
			        
			        //�õ���ǰ��¼�û�
			        String currUserId = SysContext.getSysContext().getCurrentUserInfo().getId().toString();
//			        if(userIds.contains(currUserId)){
//			        	isEdit = true;
//			        }else{
//			        	isEdit = false;
//			        }
			        
			        for(int i=0;i<assignments.length;i++){
			        	AssignmentInfo assignment = assignments[i];
			        	if((WfAssignmentState.ASSIGNED.equals(assignment.getState())
			        			||  WfAssignmentState.PROCESSING.equals(assignment.getState()) )
			        			&& currUserId.equals(assignment.getUserId())){
			        		isEdit = true;
			        		break;
			        	}else{
			        		isEdit = false;
			        	}
			        }
			        
		        }
			}         
		} 
		
		return isEdit;
	}
	
}
