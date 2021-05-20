package com.kingdee.eas.fdc.invite.markesupplier.uitl;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.base.codingrule.CodingRuleException;
import com.kingdee.eas.base.codingrule.CodingRuleManagerFactory;
import com.kingdee.eas.base.codingrule.ICodingRuleManager;
import com.kingdee.eas.base.message.BMCMessageFactory;
import com.kingdee.eas.base.message.BMCMessageInfo;
import com.kingdee.eas.base.message.IBMCMessage;
import com.kingdee.eas.base.message.MsgPriority;
import com.kingdee.eas.base.message.MsgStatus;
import com.kingdee.eas.base.message.MsgType;
import com.kingdee.eas.base.permission.PermissionUtils;
import com.kingdee.eas.base.permission.UserFactory;
import com.kingdee.eas.base.permission.UserInfo;
import com.kingdee.eas.base.receiver.BasReceiverCollection;
import com.kingdee.eas.base.receiver.BasReceiverInfo;
import com.kingdee.eas.basedata.org.AdminOrgUnitInfo;
import com.kingdee.eas.basedata.org.SaleOrgUnitInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fm.common.ContextHelperFactory;
import com.kingdee.eas.fm.common.IContextHelper;
import com.kingdee.eas.framework.CoreBillBaseInfo;
import com.kingdee.eas.framework.DataBaseInfo;
import com.kingdee.eas.util.app.ContextUtil;
import com.kingdee.util.StringUtils;

public class SysPlatformHelper {
	
	/**
	 * ��ȡԭ����ID
	 * @param destEntryID  Ŀ�굥��ID
	 * */
	public static String getSrcEntryId(String destEntryID)throws Exception
	{
			StringBuffer buffer = new StringBuffer();
			buffer.append("SELECT  FSRCENTRYID FROM T_BOT_RELATIONENTRY WHERE FDESTENTRYID ='"+destEntryID+"'");
			FDCSQLBuilder fdc = new FDCSQLBuilder();
			fdc.appendSql(buffer.toString());
			ResultSet rs = fdc.executeQuery();
			String srcEntryid = null;
			if(rs.next())
				srcEntryid = rs.getString("FSRCENTRYID");
			return srcEntryid;
	}
	/**
	 * ����������ϲ�����Ϻŵı�������ڷ������˱���ǰ����
	 * @param ctx
	 * @param info �������ֵ����
	 * */
	public static void handleIntermitNumber(Context ctx, DataBaseInfo info)
			throws BOSException, CodingRuleException, EASBizException {
		// ����û��ڿͻ����ֹ�ѡ���˶Ϻ�,��˴�����������
		if (info.getNumber() != null && info.getNumber().length() > 0)
			return;
		ICodingRuleManager iCodingRuleManager = CodingRuleManagerFactory.getLocalInstance(ctx);
		String costUnitId = ContextUtil.getCurrentOrgUnit(ctx).getId().toString();
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
	public static void recycleNumber(Context ctx, DataBaseInfo info) throws BOSException, EASBizException, CodingRuleException {
		SaleOrgUnitInfo currentSaleUnit = ContextUtil.getCurrentSaleUnit(ctx);
		String curOrgId = currentSaleUnit.getId().toString();
		if(info.getNumber()!=null&&info.getNumber().length()>0){
			ICodingRuleManager iCodingRuleManager = CodingRuleManagerFactory.getLocalInstance(ctx);
	        if (iCodingRuleManager.isExist(info, curOrgId) && iCodingRuleManager.isUseIntermitNumber(info, curOrgId)) {
	            iCodingRuleManager.recycleNumber(info, curOrgId, info.getNumber());
	        }
		}
	}
	/**
	 * ��ȡ���ݱ�����������
	 * 
	 * @return
	 * @throws BOSException
	 * @throws EASBizException
	 */
	public static String getBillNumber(Context ctx, CoreBillBaseInfo editData,String companyID)throws BOSException, EASBizException {
		
		ICodingRuleManager iCodingRuleManager=null;
		if(ctx==null){
			companyID = ((AdminOrgUnitInfo)SysContext.getSysContext().getCurrentAdminUnit()).getId().toString();
			iCodingRuleManager=CodingRuleManagerFactory.getRemoteInstance();
			if (iCodingRuleManager.isExist(editData,companyID)) {
				return iCodingRuleManager.getNumber(editData,companyID);
			}else{
				return null;
			}
		}else{
			companyID = ((AdminOrgUnitInfo)ContextUtil.getCurrentAdminUnit(ctx)).getId().toString();
			iCodingRuleManager= CodingRuleManagerFactory.getLocalInstance(ctx);
			if (iCodingRuleManager.isExist(editData,companyID)) {
				return iCodingRuleManager.getNumber(editData,companyID,"orderSort");
			}else{
				return null;
			}
		}
	}
	/**
	 * �Ƚ��û���������
	 * userNumber:�û���
	 * userPwd:����
	 * */
	public static boolean comparePassword(String userNumber,String userPwd){
		boolean isMatch = false;
		UserInfo userInfo;
		try {
			userInfo = UserFactory.getRemoteInstance().getUserInfo("where number = '"+userNumber+"'");
			String passwordINDB = userInfo.getPassword();
			if(StringUtils.isEmpty(userPwd)){
				isMatch = StringUtils.isEmpty(passwordINDB);
			} else{
				isMatch = PermissionUtils.encrypt(userInfo.getId().toString(), userPwd).equals(passwordINDB);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} 
		return isMatch;
	}
	/**
	 * ��Ϣ���ͣ�����֪ͨ��Ϣ��������Ϣ��״̬������Ϣ
	 * title:��Ϣ����
	 * body:��Ϣ��
	 * Receivers:��Ϣ������
	 * */
	public static void sendBMCMsgInfo(Context ctx, String title, String body, UserInfo Receivers)
    throws BOSException
	{
		if(Receivers == null)
			return;
	    IBMCMessage iBMCMessage;
	    IContextHelper localInstance;
	    if(ctx == null)
	    {
	    	iBMCMessage = BMCMessageFactory.getRemoteInstance();
	    	localInstance = ContextHelperFactory.getRemoteInstance();
	    } else
	    {
	    	iBMCMessage = BMCMessageFactory.getLocalInstance(ctx);
	    	localInstance = ContextHelperFactory.getLocalInstance(ctx);
	    }
	    BMCMessageInfo msgInfo = new BMCMessageInfo();
	    msgInfo.setTitle(title);
	    msgInfo.setBody(body);
	    msgInfo.setPriority(MsgPriority.MIDDLE);
	    msgInfo.setReceivers(Receivers.getName());
	    msgInfo.setType(MsgType.ONLINE);
	    msgInfo.setStatus(MsgStatus.UNREADED);
	    msgInfo.setId(BOSUuid.create(msgInfo.getBOSType()));
	    msgInfo.setSender(localInstance.getCurrentUser().getName());
	    BasReceiverInfo receiverInfo = new BasReceiverInfo();
	    receiverInfo.setType("1");
	    receiverInfo.setValue(Receivers.getId().toString());
	    receiverInfo.setDesc(Receivers.getName());
	    receiverInfo.setIsShow(false);
	    BasReceiverCollection receivercoll = new BasReceiverCollection();
	    receivercoll.add(receiverInfo);
	    iBMCMessage.addHandMsg(msgInfo, receivercoll);
	}
}
