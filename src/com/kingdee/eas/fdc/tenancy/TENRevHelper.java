package com.kingdee.eas.fdc.tenancy;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.basecrm.IRevListInfo;
import com.kingdee.eas.fdc.basecrm.RevListTypeEnum;
import com.kingdee.eas.framework.ICoreBase;

public class TENRevHelper {
	
	/**
	 * �����տ���ϸ���ͺ�id����տ���ϸ��ֵ���� 
	 * */
	public static IRevListInfo getRevListInfo(RevListTypeEnum revListType, String revListId) throws BOSException, EASBizException {
		return getRevListInfo(null, revListType, revListId, getSelectorItemCollection(revListType));
	}
	
	public static IRevListInfo getRevListInfo(Context ctx, RevListTypeEnum revListType, String revListId, SelectorItemCollection sels) throws BOSException, EASBizException{
		ICoreBase bizI = getRevListBizInterface(ctx, revListType);
		if(bizI != null  &&  revListId != null){
			return (IRevListInfo) bizI.getValue(new ObjectUuidPK(revListId), sels);
		}
		return null;
	}
	
	public static ICoreBase getRevListBizInterface(Context ctx, RevListTypeEnum revListType) throws BOSException {
		//Ŀǰ�������,��Ҫ���⼸������:���޷����տ��¼; TODO ���������տ��¼��; TODO ����Ԥ���տ��¼; TODO �ͻ��տ�
		if(RevListTypeEnum.tenRoomRev.equals(revListType)){
			return ctx == null ? TenancyRoomPayListEntryFactory.getRemoteInstance() : TenancyRoomPayListEntryFactory.getLocalInstance(ctx);
		}else if(RevListTypeEnum.tenOtherRev.equals(revListType)){
			//TODO ���������տ���ϸ
			return ctx == null ? TenBillOtherPayFactory.getRemoteInstance() : TenBillOtherPayFactory.getLocalInstance(ctx);
		}else if(RevListTypeEnum.sincerobligate.equals(revListType))
		{
			return ctx == null ? SincerPaylistEntrysFactory.getRemoteInstance() : SincerPaylistEntrysFactory.getLocalInstance(ctx);
		}
		return null;
	}
	
	public static SelectorItemCollection getSelectorItemCollection(RevListTypeEnum revListType){
		SelectorItemCollection sels = new SelectorItemCollection();
		sels.add("*");
		sels.add("moneyDefine.*");
		if (RevListTypeEnum.tenRoomRev.equals(revListType)) {
			sels.add("tenRoom.roomLongNum");
			//by tim_gao �����ͬ��ʾ
			sels.add("tenBill.tenancyName");
			sels.add("tenBill.number");
		} else if (RevListTypeEnum.tenOtherRev.equals(revListType)) {
			
		} else if (RevListTypeEnum.sincerobligate.equals(revListType)) {
		
		}
		
		return sels;
	}
}
