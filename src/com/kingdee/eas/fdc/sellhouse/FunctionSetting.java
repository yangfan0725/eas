package com.kingdee.eas.fdc.sellhouse;

import java.io.Serializable;

public class FunctionSetting implements Serializable {

	private Boolean isBasePrice;				//����ǿ�Ƶ׼ۿ���
	private Boolean isSignGathering;			//����ǩԼ�տ����
	private Boolean isMortagage;				//���õ�Ѻ��������
	private Boolean isActGathering;				//�Ϲ�ҵ��ʵ���տ�Ϊ׼
	private Boolean isSincerSellOrder;			//������������Ϲ�δ���̷���	
	private Boolean isSincerPrice;				//������������������� 
	private Boolean isHouseMoney;				//�����Ƿ���������
	private Boolean isEditPurAndSignDate;	    //�Ϲ�/ǩԼ�����Ƿ�����༭
	private Boolean	isAdjustPrices;				//�˷��Ƿ�������
	private Boolean isLoanReceiving;            //���ҿ�����밴�Ұ�����ɺ�����տ�
//	private Boolean isChoose;					//�Ƿ�����ѡ��
//	private Integer chooseTime;						//��������ʱ�䣨���ӣ�
	
	public Boolean getIsLoanReceiving() {
		return isLoanReceiving;
	}
	public void setIsLoanReceiving(Boolean isLoanReceiving) {
		this.isLoanReceiving = isLoanReceiving;
	}
	public Boolean getIsEditPurAndSignDate() {
		return isEditPurAndSignDate;
	}
	public void setIsEditPurAndSignDate(Boolean isEditPurAndSignDate) {
		this.isEditPurAndSignDate = isEditPurAndSignDate;
	}
	public Boolean getIsAdjustPrices() {
		return isAdjustPrices;
	}
	public void setIsAdjustPrices(Boolean isAdjustPrices) {
		this.isAdjustPrices = isAdjustPrices;
	}
	public Boolean getIsBasePrice() {
		return isBasePrice;
	}
	public void setIsBasePrice(Boolean isBasePrice) {
		this.isBasePrice = isBasePrice;
	}
	public Boolean getIsSignGathering() {
		return isSignGathering;
	}
	public void setIsSignGathering(Boolean isSignGathering) {
		this.isSignGathering = isSignGathering;
	}
	public Boolean getIsMortagage() {
		return isMortagage;
	}
	public void setIsMortagage(Boolean isMortagage) {
		this.isMortagage = isMortagage;
	}
	public Boolean getIsActGathering() {
		return isActGathering;
	}
	public void setIsActGathering(Boolean isActGathering) {
		this.isActGathering = isActGathering;
	}
	public Boolean getIsSincerSellOrder() {
		return isSincerSellOrder;
	}
	public void setIsSincerSellOrder(Boolean isSincerSellOrder) {
		this.isSincerSellOrder = isSincerSellOrder;
	}
	public Boolean getIsSincerPrice() {
		return isSincerPrice;
	}
	public void setIsSincerPrice(Boolean isSincerPrice) {
		this.isSincerPrice = isSincerPrice;
	}
	public Boolean getIsHouseMoney() {
		return isHouseMoney;
	}
	public void setIsHouseMoney(Boolean isHouseMoney) {
		this.isHouseMoney = isHouseMoney;
	}
//	public Boolean getIsChoose() {
//		return isChoose;
//	}
//	public void setIsChoose(Boolean isChoose) {
//		this.isChoose = isChoose;
//	}
//	public Integer getChooseTime() {
//		return chooseTime;
//	}
//	public void setChooseTime(Integer chooseTime) {
//		this.chooseTime = chooseTime;
//	}
//	
	
	
	
	
}
