package com.kingdee.eas.fdc.tenancy.app;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.dao.IObjectCollection;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.tenancy.FlagAtTermEnum;
import com.kingdee.eas.fdc.tenancy.HandleStateEnum;
import com.kingdee.eas.fdc.tenancy.ITenancyEntryInfo;
import com.kingdee.eas.fdc.tenancy.QuitTenancyInfo;
import com.kingdee.eas.fdc.tenancy.TenAttachResourceEntryCollection;
import com.kingdee.eas.fdc.tenancy.TenAttachResourceEntryFactory;
import com.kingdee.eas.fdc.tenancy.TenAttachResourceEntryInfo;
import com.kingdee.eas.fdc.tenancy.TenancyBillFactory;
import com.kingdee.eas.fdc.tenancy.TenancyBillInfo;
import com.kingdee.eas.fdc.tenancy.TenancyBillStateEnum;
import com.kingdee.eas.fdc.tenancy.TenancyHelper;
import com.kingdee.eas.fdc.tenancy.TenancyRoomEntryCollection;
import com.kingdee.eas.fdc.tenancy.TenancyRoomEntryFactory;
import com.kingdee.eas.fdc.tenancy.TenancyRoomEntryInfo;

public class QuitTenancyControllerBean extends AbstractQuitTenancyControllerBean
{
    private static Logger logger =
        Logger.getLogger("com.kingdee.eas.fdc.tenancy.app.QuitTenancyControllerBean");
    
    protected boolean isUseName() {
    	return false;
    }
    
    protected IObjectPK _submit(Context ctx, IObjectValue model) throws BOSException, EASBizException {
    	//��ͬ���ⵥ�ύ�󣬺�ͬ��Ϊ������״̬
    	QuitTenancyInfo quitTen = (QuitTenancyInfo) model;
    	TenancyBillInfo tenancy = quitTen.getTenancyBill();
    	tenancy.setTenancyState(TenancyBillStateEnum.QuitTenancying);
    	SelectorItemCollection sels = new SelectorItemCollection();
    	sels.add("tenancyState");
    	TenancyBillFactory.getLocalInstance(ctx).updatePartial(tenancy, sels);
    	
    	return super._submit(ctx, model);
    }
    
    protected void _delete(Context ctx, IObjectPK pk) throws BOSException, EASBizException {
    	//ɾ���ύ״̬�����ⵥ����Ҫ��ԭ��ͬ��Ϊԭִ��״̬
    	SelectorItemCollection sels = new SelectorItemCollection();
    	sels.add("tenancyBill.id");
    	
    	QuitTenancyInfo quitTen = getQuitTenancyInfo(ctx, pk, sels);
    	TenancyBillInfo tenancy = quitTen.getTenancyBill();
    	if(tenancy == null){
    		logger.error("���ⵥ�����޺�ͬΪ��. quitTenId" + quitTen.getId());
    	}
    	String tenId = tenancy.getId().toString();
    	
    	SelectorItemCollection tenSels = new SelectorItemCollection();
    	tenSels.add("id");
    	tenSels.add("tenancyState");
    	tenSels.add("tenancyRoomList.id");
    	tenSels.add("tenancyRoomList.handleState");
    	
    	tenancy = TenancyBillFactory.getLocalInstance(ctx).getTenancyBillInfo(new ObjectUuidPK(tenId), tenSels);
    	
    	//ֻ����������ִ���У�����ִ���еĺ�ͬ��������,ɾ������󣬸��ݽ����������
    	TenancyRoomEntryCollection tenRooms = tenancy.getTenancyRoomList();
    	TenAttachResourceEntryCollection tenAttachs = tenancy.getTenAttachResourceList();
    	int deliveryRoomAndAttachCount = 0;//ԭ��ͬ�ѽ��������������������������Դ��
    	deliveryRoomAndAttachCount = addDeliveryCount(tenRooms, deliveryRoomAndAttachCount);
    	deliveryRoomAndAttachCount = addDeliveryCount(tenAttachs, deliveryRoomAndAttachCount);
    	
    	TenancyBillStateEnum bornTenState = null;
    	if(deliveryRoomAndAttachCount == 0){//���һ�����Ӷ�û����ԭ��֮ͬǰ������״̬
    		bornTenState = TenancyBillStateEnum.Audited;
    	}else if(deliveryRoomAndAttachCount == (tenRooms.size() + tenAttachs.size())){//�������ȫ���ˣ�ԭ��֮ͬǰ��ִ����״̬
    		bornTenState = TenancyBillStateEnum.Executing;
    	}else{//���ֻ����1���֣�ԭ��֮ͬǰ�ǲ���ִ��״̬
    		bornTenState = TenancyBillStateEnum.PartExecuted;
    	}
    	tenancy.setTenancyState(bornTenState);
    	SelectorItemCollection updateBillStateSels = new SelectorItemCollection();
    	updateBillStateSels.add("tenancyState");
    	TenancyBillFactory.getLocalInstance(ctx).updatePartial(tenancy, updateBillStateSels);
    	
    	super._delete(ctx, pk);
    }

	private int addDeliveryCount(IObjectCollection tenObjs, int deliveryRoomAndAttachCount) {
		for(int i=0; i<tenObjs.size(); i++){
			ITenancyEntryInfo tenObj = (ITenancyEntryInfo) tenObjs.getObject(i);
			HandleStateEnum handleState = tenObj.getHandleState();
    		if(HandleStateEnum.HandlingRoom.equals(handleState) || HandleStateEnum.livingHouse.equals(handleState)){
    			deliveryRoomAndAttachCount++;
    		}
    	}
		return deliveryRoomAndAttachCount;
	}
    
    /** �������� */
    protected void _audit(Context ctx, BOSUuid billId) throws BOSException, EASBizException {
    	QuitTenancyInfo quitTen = getQuitTenancyInfo(ctx, new ObjectUuidPK(billId.toString()));
    	
    	TenancyBillInfo ten = quitTen.getTenancyBill();
    	
    	if(ten == null){
    		throw new BOSException("���ⵥû�ҵ�ԭ��ͬ��");
    	}
    	
    	ten = TenancyHelper.getTenancyBillInfo(ctx, ten.getId().toString());
    	
    	//����ԭ��ͬ��ʣ��Ѻ���Ӧ�����
    	TenancyHelper.adjustDepositeAndPayList(ctx, quitTen.getQuitDate(), ten, true);
    	
    	//ԭ��ͬʣ��Ѻ���ȥ�۳��Ľ��//TODO �ۿ���δʵ�֣��������Է���ۿ�,��������䲻�õ��ˡ�
//    	TenancyHelper.deductAmountOfRemainDepositAmount(ctx, quitTen.getDeductAmount(), ten);
    	
    	//����ԭ��ͬ��ԭ��ͬ�����¼�ĵ��ڱ��
       	FlagAtTermEnum quitTenType = quitTen.getQuitTenancyType();
    	ten.setFlagAtTerm(quitTenType);
    	
    	SelectorItemCollection sels = new SelectorItemCollection();
    	sels.add("flagAtTerm");
    	
    	TenancyRoomEntryCollection tenRooms = ten.getTenancyRoomList();
    	TenAttachResourceEntryCollection tenAttachs = ten.getTenAttachResourceList();
    	boolean allNotDelieve = true;//���з����δ����,������Ϊδ����״̬
    	for(int i=0; i<tenRooms.size(); i++){
    		TenancyRoomEntryInfo tenRoom = tenRooms.get(i);
    		if(tenRoom.getHandleState() != null  &&  !HandleStateEnum.NoHandleRoom.equals(tenRoom.getHandleState())){
    			allNotDelieve = false;
    		}
    		
    		tenRoom.setFlagAtTerm(quitTenType);
    		TenancyRoomEntryFactory.getLocalInstance(ctx).updatePartial(tenRoom, sels);
    	}
    	
    	for(int i=0; i<tenAttachs.size(); i++){
    		TenAttachResourceEntryInfo tenAttach = tenAttachs.get(i);
    		if(tenAttach.getHandleState() != null  &&  !HandleStateEnum.NoHandleRoom.equals(tenAttach.getHandleState())){
    			allNotDelieve = false;
    		}
    		
    		tenAttach.setFlagAtTerm(quitTenType);
    		TenAttachResourceEntryFactory.getLocalInstance(ctx).updatePartial(tenAttach, sels);
    	}
    	
    	//���ԭ��ͬ�����з����δ��������ֱ����ֹԭ��ͬ
    	if(allNotDelieve){
    		ten.setTenancyState(TenancyBillStateEnum.Expiration);
    		sels.add("tenancyState");
    	}
    	TenancyBillFactory.getLocalInstance(ctx).updatePartial(ten, sels);
    	
    	super._audit(ctx, billId);
    }
    
}