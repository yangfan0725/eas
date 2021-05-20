package com.kingdee.eas.fdc.sellhouse.client;

import java.math.BigDecimal;

public class PurchaseParam {

	private String agioDes;		//�ۿ�˵��
	private BigDecimal finalAgio; //�������
	private BigDecimal dealTotalAmount; 	//�ɽ��ܼ�
	private BigDecimal contractTotalAmount;		//�ɽ��ܼ�
	private BigDecimal contractBuildPrice;		//���佨�����۵׼�
	private BigDecimal contractRoomPrice;		//�����������۵׼�
	private BigDecimal dealBuildPrice;			//���佨�����۵׼�
	private BigDecimal dealRoomPrice;			//�����������۵׼�
	
	
	public PurchaseParam() {
		agioDes = "";
		finalAgio = new BigDecimal("100");
		dealTotalAmount = new BigDecimal("0");
		contractTotalAmount = new BigDecimal("0");
		contractBuildPrice = new BigDecimal("0");
		contractRoomPrice = new BigDecimal("0");
		dealBuildPrice = new BigDecimal("0");
		dealRoomPrice = new BigDecimal("0");
	}
	
	public String getAgioDes() {
		return agioDes;
	}
	public void setAgioDes(String agioDes) {
		this.agioDes = agioDes;
	}
	public BigDecimal getFinalAgio() {
		return finalAgio;
	}
	public void setFinalAgio(BigDecimal finalAgio) {
		this.finalAgio = finalAgio;
	}
	public BigDecimal getDealTotalAmount() {
		return dealTotalAmount;
	}
	public void setDealTotalAmount(BigDecimal dealTotalAmount) {
		this.dealTotalAmount = dealTotalAmount;
	}
	public BigDecimal getContractTotalAmount() {
		return contractTotalAmount;
	}
	public void setContractTotalAmount(BigDecimal contractTotalAmount) {
		this.contractTotalAmount = contractTotalAmount;
	}
	public BigDecimal getContractBuildPrice() {
		return contractBuildPrice;
	}
	public void setContractBuildPrice(BigDecimal contractBuildPrice) {
		this.contractBuildPrice = contractBuildPrice;
	}
	public BigDecimal getContractRoomPrice() {
		return contractRoomPrice;
	}
	public void setContractRoomPrice(BigDecimal contractRoomPrice) {
		this.contractRoomPrice = contractRoomPrice;
	}
	public BigDecimal getDealBuildPrice() {
		return dealBuildPrice;
	}
	public void setDealBuildPrice(BigDecimal dealBuildPrice) {
		this.dealBuildPrice = dealBuildPrice;
	}
	public BigDecimal getDealRoomPrice() {
		return dealRoomPrice;
	}
	public void setDealRoomPrice(BigDecimal dealRoomPrice) {
		this.dealRoomPrice = dealRoomPrice;
	}
	
	
	
	
	
	
}
