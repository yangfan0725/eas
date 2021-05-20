package com.kingdee.eas.fdc.sellhouse.report;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class OtherInfo implements Serializable {
	
	private String roomId;
	private String bankName;
	private String acBankName;
	private BigDecimal contactTotalAmt;
	private Date signDate;
	private String salesMan;
	private String bizHandler;
	private String loanType;
	
	
	public String getAcBankName() {
		return acBankName;
	}
	public void setAcBankName(String acBankName) {
		this.acBankName = acBankName;
	}
	public String getLoanType() {
		return loanType;
	}
	public void setLoanType(String loanType) {
		this.loanType = loanType;
	}
	public String getRoomId() {
		return roomId;
	}
	public void setRoomId(String roomId) {
		this.roomId = roomId;
	}
	public String getBankName() {
		return bankName;
	}
	public void setBankName(String bankName) {
		this.bankName = bankName;
	}
	public BigDecimal getContactTotalAmt() {
		return contactTotalAmt;
	}
	public void setContactTotalAmt(BigDecimal contactTotalAmt) {
		this.contactTotalAmt = contactTotalAmt;
	}
	public Date getSignDate() {
		return signDate;
	}
	public void setSignDate(Date signDate) {
		this.signDate = signDate;
	}
	public String getSalesMan() {
		return salesMan;
	}
	public void setSalesMan(String salesMan) {
		this.salesMan = salesMan;
	}
	public String getBizHandler() {
		return bizHandler;
	}
	public void setBizHandler(String bizHandler) {
		this.bizHandler = bizHandler;
	}
	
	
	
	

}
