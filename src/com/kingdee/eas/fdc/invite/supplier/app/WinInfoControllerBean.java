package com.kingdee.eas.fdc.invite.supplier.app;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.contract.ContractException;
import com.kingdee.eas.fdc.invite.supplier.NetworkUtils;
import com.kingdee.eas.fdc.invite.supplier.WinInfoFactory;
import com.kingdee.eas.fdc.invite.supplier.WinInfoInfo;
import com.kingdee.eas.fdc.invite.supplier.WinSupplierEntryInfo;

public class WinInfoControllerBean extends AbstractWinInfoControllerBean
{
    private static Logger logger =
        Logger.getLogger("com.kingdee.eas.fdc.invite.supplier.app.WinInfoControllerBean");
    
    @Override
    protected void _publish(Context ctx, BOSUuid billId) throws BOSException,
    		EASBizException {
    	// TODO Auto-generated method stub
    	super._publish(ctx, billId);
    	SelectorItemCollection sic = new SelectorItemCollection();
    	sic.add("*");
    	sic.add("invation.*");
    	sic.add("inviteProject.*");
    	sic.add("winSupplierEntry.*");
    	sic.add("winSupplierEntry.winSupplier.*");
    	WinInfoInfo info = WinInfoFactory.getLocalInstance(ctx).getWinInfoInfo(new ObjectUuidPK(billId),sic);
    	
    	if(!info.getState().equals(FDCBillStateEnum.AUDITTED) ||"1".equals(info.getPublishState())){
    		throw new ContractException(ContractException.WITHMSG,new String[]{"ֻ������ͨ������û�з��������б���Ϣ�ſ��Խ��з���"});
    	}
    	
    	info.setPublishState("1");
    	sic = new SelectorItemCollection();
    	sic.add("publishState");
    	WinInfoFactory.getLocalInstance(ctx).updatePartial(info, sic);
    	
    	syncWinInfo(ctx, info);
    	
    	FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
 	    builder.appendSql("update t_inv_inviteproject set FPUBLISHWININFO =1 where fid = ?");
 	    builder.addParam(info.getInviteProject().getId()+"");
 	    builder.executeUpdate();
 	    
    	
    }

	private void syncWinInfo(Context ctx, WinInfoInfo info) throws ContractException {
		String baseUrl = NetworkUtils.getBaseUrl(ctx);
    	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
    	String syncInvitationUrl =baseUrl+"T_GYS_WinInfo_Sync";
    	Map<String,String> params = new HashMap<String,String>();
    
    	/*1	ID	FID	�ַ�	50	����
    	2	����	FNAME	�ַ�	255	
    	3	����	FNUMBER	�ַ�	255	
    	4	����ʱ��	FCREATEDATE	����	8	
    	5	������	FCREATOR	�ַ�	50	
    	6	����޸���	FLASTUPDATOR	�ַ�	50	
    	7	����޸�ʱ�� 	FLASTUPDATEDATE	����	8	
    	8	������	FAUDIITOR	�ַ�	50	
    	9	����ʱ��	FAUDITTIME	����	8	
    	10	�б�����	FINVITEPROJECTID	�ַ�	50	ȡ���б�����.FID
    	11	�б���Ŀ����	FINVITEPROJECTNAME	�ַ�	255	
    	12	�б굥λ	FWINSUPPLIER	�ַ�	50	ȡ�Թ�Ӧ��.FID
    	13	�б�ʱ��	FWINDATE	����	8	
    	14	�б겿��ϵ��	FINVITOR	�ַ�	255	
    	15	�б겿��ϵ�绰	FINIVITORPHONE	�ַ�	50	
    	16	��Ʋ�������	FCHECKER	�ַ�	255	
    	17	��Ʋ��绰	FCHECKERPHONE	�ַ�	50	
    	18	�б���Ϣ��ϸ����	FDETAILINFO	�ַ�	500	*/
    	
    	params.put("FID", info.getId().toString());
    	params.put("FNAME", info.getName());
    	params.put("FNUMBER", info.getNumber());
    	params.put("FCREATEDATE", sdf.format(info.getCreateTime()));
    	params.put("FLASTUPDATEDATE", sdf.format(info.getLastUpdateTime()));
    	if(info.getAuditTime()!= null){
    		params.put("FAUDITTIME", sdf.format(info.getAuditTime()));
    	}
    	params.put("FAUDIITOR", "EAS");
    	params.put("FCREATOR", "EAS");
    	params.put("FLASTUPDATOR", "EAS");
    	//TODO FIXIT �˴��б깩Ӧ�̸ĳɷ�¼
    	
    	Iterator it =info.getWinSupplierEntry().iterator();
    	StringBuffer str = new StringBuffer();
    	WinSupplierEntryInfo entry = null;
    	for(;it.hasNext();){
    		entry = (WinSupplierEntryInfo) it.next();
    		str.append(entry.getWinSupplier().getId()+";");
    	}
    	params.put("FWINSUPPLIER", str.toString());
    	params.put("FWINDATE", sdf.format(info.getWinDate()));
    	params.put("FINVITOR", info.getInvitor());
    	params.put("FINIVITORPHONE", info.getInvitorPhone());
    	params.put("FCHECKER", info.getChecker());
    	params.put("FCHECKERPHONE", info.getCheckerPhone());
    	params.put("FDETAILINFO", info.getDetailInfo());
    	params.put("FPUBLISHSTATE", info.getPublishState());
    	params.put("FINVITEPROJECTID", info.getInviteProject().getId()+"");
    	params.put("FINVITEPROJECTNAME", info.getInviteProject().getName());
    	
    	
    	String result = NetworkUtils.post(syncInvitationUrl, params);
    	logger.info("ͬ���б���Ϣ�������"+result);
    	NetworkUtils.processResponseResult(ctx,result);
	}
    
    @Override
    protected void _unPublish(Context ctx, BOSUuid billId) throws BOSException,
    		EASBizException {
    	// TODO Auto-generated method stub
    	super._unPublish(ctx, billId);
    	SelectorItemCollection sic = new SelectorItemCollection();
    	sic.add("*");
    	sic.add("invation.*");
    	sic.add("inviteProject.*");
    	sic.add("winSupplierEntry.*");
    	sic.add("winSupplierEntry.winSupplier.*");
    	WinInfoInfo info = WinInfoFactory.getLocalInstance(ctx).getWinInfoInfo(new ObjectUuidPK(billId),sic);
    	if(!info.getState().equals(FDCBillStateEnum.AUDITTED) ||"0".equals(info.getPublishState())){
    		throw new ContractException(ContractException.WITHMSG,new String[]{"ֻ������ͨ�������Ѿ����������б���Ϣ�ſ��Խ��з���"});
    	}
    	info.setPublishState("0");
    	sic = new SelectorItemCollection();
    	sic.add("publishState");
    	WinInfoFactory.getLocalInstance(ctx).updatePartial(info, sic);
    	
    	syncWinInfo(ctx, info);
    	
    	FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
 	    builder.appendSql("update t_inv_inviteproject set FPUBLISHWININFO =0 where fid = ?");
 	    builder.addParam(info.getInviteProject().getId()+"");
 	    builder.executeUpdate();
    }
}