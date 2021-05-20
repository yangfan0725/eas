package com.kingdee.eas.fdc.sellhouse;

import java.io.Serializable;
import java.math.BigDecimal;

public class PreMoneySetting implements Serializable{
	
	private BigDecimal preLevelAmount;	 //Ԥ����ͽ��
	private Boolean isLevelModify;		//��Ϳɷ��޸�
	private BigDecimal preStandAmount;	//Ԥ����׼���
	private Boolean isStandModify;		//��׼�ɷ��޸�
	
	
	public BigDecimal getPreLevelAmount() {
		return preLevelAmount;
	}
	public void setPreLevelAmount(BigDecimal preLevelAmount) {
		this.preLevelAmount = preLevelAmount;
	}
	public Boolean getIsLevelModify() {
		return isLevelModify;
	}
	public void setIsLevelModify(Boolean isLevelModify) {
		this.isLevelModify = isLevelModify;
	}
	public BigDecimal getPreStandAmount() {
		return preStandAmount;
	}
	public void setPreStandAmount(BigDecimal preStandAmount) {
		this.preStandAmount = preStandAmount;
	}
	public Boolean getIsStandModify() {
		return isStandModify;
	}
	public void setIsStandModify(Boolean isStandModify) {
		this.isStandModify = isStandModify;
	}
}
