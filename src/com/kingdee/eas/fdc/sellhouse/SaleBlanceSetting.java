package com.kingdee.eas.fdc.sellhouse;

import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import com.kingdee.bos.Context;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.framework.cache.CacheServiceFactory;
import com.kingdee.eas.common.client.SysContext;
import com.sun.xml.messaging.saaj.util.ByteInputStream;

public class SaleBlanceSetting implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3405534563826802020L;	
//	//�ѽ�����ŵ�MAP��
//	private Map isCheckMap = new HashMap();
	
	//��ѡ�����˵�δ��������Ϲ���ĳ����Ϲ���
	private Object sincerObject = null;
	
	//δ�������Ϲ���
	private Object noAuditPur = null;
	
	//δ���˵�Ԥ����
	private Object noApplyPrePur = null;
	
	//������δ�����׸����Ϲ���
	private Object firstAmountPur = null;
	
	//�˷�δ�������˷���
	private Object noAuditQuitRoom = null;
	
	//�˷������˵�δ�������˷���
	private Object canRefundmentAmount = null;
	
	//���δ�����ı����
	private Object noAuditPurChange = null;
	
	//����δ�����Ļ�����
	private Object noAuditChangeRoom = null;
	
	//�Ѱ�����ɰ��ҵ�û����ȡ���ҿ���Ϲ���
	private Object loanNoRev = null;
	
	//�Ѱ�����ɹ�����û����ȡ��������Ϲ���
	private Object accFundNoRev = null;
	
	public SaleBlanceSetting(){
		this(null);
	}
	
	public SaleBlanceSetting(Context ctx) {
		//this.setDefaultValue(ctx);
		// �������ݿ�����
		SaleBlanceSetting saleSetting = null;
		try {
			ISaleBlanceSet saleFace = null;
			if(ctx == null){
				saleFace = SaleBlanceSetFactory.getRemoteInstance();
			}else{
				saleFace = SaleBlanceSetFactory.getLocalInstance(ctx);
			}
			SaleBlanceSetCollection saleSetCollection = saleFace.getSaleBlanceSetCollection();
			if (saleSetCollection.size() > 0) {
				byte[] b = saleSetCollection.get(0).getValue();
				ObjectInputStream objectInputStream = new ObjectInputStream(
						new ByteInputStream(b, 0, b.length));
				saleSetting = (SaleBlanceSetting) objectInputStream
						.readObject();
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(saleSetting!=null)
		{
			this.sincerObject = saleSetting.sincerObject;
			this.noAuditPur = saleSetting.noAuditPur;
			this.noApplyPrePur = saleSetting.noApplyPrePur;
			this.firstAmountPur = saleSetting.firstAmountPur;
			this.noAuditQuitRoom = saleSetting.noAuditQuitRoom;
			this.canRefundmentAmount = saleSetting.canRefundmentAmount;
			this.noAuditPurChange = saleSetting.noAuditPurChange;
			this.noAuditChangeRoom = saleSetting.noAuditChangeRoom;
			this.loanNoRev = saleSetting.loanNoRev;
			this.accFundNoRev = saleSetting.accFundNoRev;
		}
	}	

	private void setDefaultValue(Context ctx)
	{
	}
		
	public void save() throws Exception {

		ByteArrayOutputStream arrayOutputStream = new ByteArrayOutputStream();
		ObjectOutputStream objectOutputStream = new ObjectOutputStream(
				arrayOutputStream);
		objectOutputStream.writeObject(this);
		byte[] b = arrayOutputStream.toByteArray();
		SaleBlanceSetCollection setCol = SaleBlanceSetFactory
				.getRemoteInstance().getSaleBlanceSetCollection();
		if (setCol.size() > 0) {
			SaleBlanceSetInfo setInfo = setCol.get(0);
			setInfo.setValue(b);
			SaleBlanceSetFactory.getRemoteInstance().update(
					new ObjectUuidPK(setInfo.getId()), setInfo);
			CacheServiceFactory.getInstance().discardType(setInfo.getBOSType());
		} else {
			SaleBlanceSetInfo setInfo = new SaleBlanceSetInfo();
			setInfo.setCreator(SysContext.getSysContext().getCurrentUserInfo());
			setInfo.setValue(b);
			SaleBlanceSetFactory.getRemoteInstance().addnew(setInfo);
			CacheServiceFactory.getInstance().discardType(setInfo.getBOSType());
		}
	}
	
	public Object getSincerObject() {
		return sincerObject;
	}

	public void setSincerObject(Object sincerObject) {
		this.sincerObject = sincerObject;
	}

	public Object getNoAuditPur() {
		return noAuditPur;
	}

	public void setNoAuditPur(Object noAuditPur) {
		this.noAuditPur = noAuditPur;
	}

	public Object getNoApplyPrePur() {
		return noApplyPrePur;
	}

	public void setNoApplyPrePur(Object noApplyPrePur) {
		this.noApplyPrePur = noApplyPrePur;
	}

	public Object getFirstAmountPur() {
		return firstAmountPur;
	}

	public void setFirstAmountPur(Object firstAmountPur) {
		this.firstAmountPur = firstAmountPur;
	}

	public Object getNoAuditQuitRoom() {
		return noAuditQuitRoom;
	}

	public void setNoAuditQuitRoom(Object noAuditQuitRoom) {
		this.noAuditQuitRoom = noAuditQuitRoom;
	}

	public Object getCanRefundmentAmount() {
		return canRefundmentAmount;
	}

	public void setCanRefundmentAmount(Object canRefundmentAmount) {
		this.canRefundmentAmount = canRefundmentAmount;
	}

	public Object getNoAuditPurChange() {
		return noAuditPurChange;
	}

	public void setNoAuditPurChange(Object noAuditPurChange) {
		this.noAuditPurChange = noAuditPurChange;
	}

	public Object getNoAuditChangeRoom() {
		return noAuditChangeRoom;
	}

	public void setNoAuditChangeRoom(Object noAuditChangeRoom) {
		this.noAuditChangeRoom = noAuditChangeRoom;
	}	

	public Object getLoanNoRev()
	{
		return loanNoRev;
	}
	
	public void setLoanNoRev(Object loanNoRev)
	{
		this.loanNoRev = loanNoRev;
	}
	
	public Object getAccFundNoRev() {
		return accFundNoRev;
	}

	public void setAccFundNoRev(Object accFundNoRev) {
		this.accFundNoRev = accFundNoRev;
	}
}
