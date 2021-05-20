package com.kingdee.eas.fdc.tenancy;

import java.io.Serializable;
import java.math.BigDecimal;

public class TenanycAppAmtInfo implements Serializable {
	
	private String key;
	private String moneyTypeId;
	private String moneyDefineType;
	private String moneyDefineName;
	private BigDecimal appAmt;
	private BigDecimal actAmt;
	private BigDecimal delayAmt;
	
	public String getMoneyDefineName() {
		return moneyDefineName;
	}
	public void setMoneyDefineName(String moneyDefineName) {
		this.moneyDefineName = moneyDefineName;
	}
	public String getMoneyTypeId() {
		return moneyTypeId;
	}
	public void setMoneyTypeId(String moneyTypeId) {
		this.moneyTypeId = moneyTypeId;
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public String getMoneyDefineType() {
		return moneyDefineType;
	}
	public void setMoneyDefineType(String moneyDefineType) {
		this.moneyDefineType = moneyDefineType;
	}
	public BigDecimal getAppAmt() {
		return appAmt;
	}
	public void setAppAmt(BigDecimal appAmt) {
		this.appAmt = appAmt;
	}
	public BigDecimal getActAmt() {
		return actAmt;
	}
	public void setActAmt(BigDecimal actAmt) {
		this.actAmt = actAmt;
	}
	public BigDecimal getDelayAmt() {
		return delayAmt;
	}
	public void setDelayAmt(BigDecimal delayAmt) {
		this.delayAmt = delayAmt;
	}
	
	

}
