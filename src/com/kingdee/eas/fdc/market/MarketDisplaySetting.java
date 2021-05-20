package com.kingdee.eas.fdc.market;

import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.eas.common.client.SysContext;
import com.sun.xml.messaging.saaj.util.ByteInputStream;

public class MarketDisplaySetting implements Serializable {
	
	private int nameRepeat = 0;
	
	private int nameAndPhoneRepeat = 0;
	
	private int phoneRepeat = 0;
	
	private Map sortStandardMap = new HashMap();
	
	private int markInvoice = 0;
	
	private int gxcheck=0;
	
	private int phoneNumber=0;
	
	private int mobileNumber=0;
	
	private String checkedRadioButton = "kDRadioSingle";
	
	public MarketDisplaySetting()
	{
//		 �������ݿ�����
		MarketDisplaySetting disSetting = null;
		try {
			MarketDisplaySetCollection marketSetCollection = MarketDisplaySetFactory
					.getRemoteInstance().getMarketDisplaySetCollection();
			if (marketSetCollection.size() > 0) {
				byte[] b = marketSetCollection.get(0).getValue();
				ObjectInputStream objectInputStream = new ObjectInputStream(
						new ByteInputStream(b, 0, b.length));
				disSetting = (MarketDisplaySetting) objectInputStream
						.readObject();
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (disSetting != null) {
			this.nameRepeat = disSetting.nameRepeat;
			this.phoneRepeat = disSetting.phoneRepeat;
			this.mobileNumber=disSetting.mobileNumber;
			this.phoneNumber=disSetting.phoneNumber;
			this.gxcheck=disSetting.gxcheck;
			this.sortStandardMap = disSetting.sortStandardMap;
			this.markInvoice = disSetting.markInvoice;
			this.setCheckedRadioButton(disSetting.getCheckedRadioButton());
			this.setNameAndPhoneRepeat(disSetting.getNameAndPhoneRepeat());
		}
	}
	
	public void save() throws Exception {

		ByteArrayOutputStream arrayOutputStream = new ByteArrayOutputStream();
		ObjectOutputStream objectOutputStream = new ObjectOutputStream(
				arrayOutputStream);
		objectOutputStream.writeObject(this);
		byte[] b = arrayOutputStream.toByteArray();
		MarketDisplaySetCollection setCol = MarketDisplaySetFactory
				.getRemoteInstance().getMarketDisplaySetCollection();
		if (setCol.size() > 0) {
			MarketDisplaySetInfo setInfo = setCol.get(0);
			setInfo.setValue(b);
//			setInfo.setGxcheck(gxcheck==0?false:true);
			MarketDisplaySetFactory.getRemoteInstance().update(
					new ObjectUuidPK(setInfo.getId()), setInfo);
		} else {
			MarketDisplaySetInfo setInfo = new MarketDisplaySetInfo();
			setInfo.setCreator(SysContext.getSysContext().getCurrentUserInfo());
			setInfo.setValue(b);
//			setInfo.setGxcheck(gxcheck==0?false:true);
			MarketDisplaySetFactory.getRemoteInstance().addnew(setInfo);
		}
	}

	public int getMarkInvoice() {
		return markInvoice;
	}

	public void setMarkInvoice(int markInvoice) {
		this.markInvoice = markInvoice;
	}

	public int getNameRepeat() {
		return nameRepeat;
	}

	public void setNameRepeat(int nameRepeat) {
		this.nameRepeat = nameRepeat;
	}

	public int getGxcheck() {
		return gxcheck;
	}

	public void setGxcheck(int gxcheck) {
		this.gxcheck = gxcheck;
	}
	public int getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(int phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public int getMobileNumber() {
		return mobileNumber;
	}

	public void setMobileNumber(int mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	public int getPhoneRepeat() {
		return phoneRepeat;
	}

	public void setPhoneRepeat(int phoneRepeat) {
		this.phoneRepeat = phoneRepeat;
	}


	public Map getSortStandardMap() {
		return sortStandardMap;
	}

	public void setSortStandardMap(Map sortStandardMap) {
		this.sortStandardMap = sortStandardMap;
	}

	public void setNameAndPhoneRepeat(int nameAndPhoneRepeat) {
		this.nameAndPhoneRepeat = nameAndPhoneRepeat;
	}

	public int getNameAndPhoneRepeat() {
		return nameAndPhoneRepeat;
	}

	public void setCheckedRadioButton(String checkedRadioButton) {
		this.checkedRadioButton = checkedRadioButton;
	}

	public String getCheckedRadioButton() {
		return checkedRadioButton;
	}

}
