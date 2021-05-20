package com.kingdee.eas.fdc.contract.programming.client;

import java.math.BigDecimal;
import java.util.Date;

public class ProgDynaTableObject {
	private String id;// ��ܺ�ԼID
	private int level;// ���Ͳ��
	private String longNumber;// ��ܺ�Լ������
	private String name;// ��ܺ�Լ����
	private BigDecimal amount;// ��ܺ�Լ�滮���
	private BigDecimal controlAmount;// ��ܺ�Լ���ƽ��
	private BigDecimal balance;// ��ܺ�Լ�滮���
	private BigDecimal controlBalance;// ��ܺ�Լ�������
	private String invite;// �б�����
	private String contractname;// ��ͬ����
	private String signSec;// ��ͬ�ҷ�
	private BigDecimal signUpAmount;// ǩԼ���
	private BigDecimal changeAmount;// ������
	private BigDecimal newCost;// �������
	private BigDecimal settleAmount;// ������
	private String remark;// ��ע
	private BigDecimal estimateAmount;// Ԥ����ͬ�䶯
	private BigDecimal withOutTextAmount;// ���ı���ͬ
	private BigDecimal budgetAmount;// δǩ��ͬ
	private String contractNumber;// δǩ��ͬ
	private Date bookedDate;// δǩ��ͬ
	
	
	public String getContractNumber() {
		return contractNumber;
	}

	public void setContractNumber(String contractNumber) {
		this.contractNumber = contractNumber;
	}

	public Date getBookedDate() {
		return bookedDate;
	}

	public void setBookedDate(Date bookedDate) {
		this.bookedDate = bookedDate;
	}

	public BigDecimal getEstimateAmount() {
		return estimateAmount;
	}

	public void setEstimateAmount(BigDecimal estimateAmount) {
		this.estimateAmount = estimateAmount;
	}

	public BigDecimal getWithOutTextAmount() {
		return withOutTextAmount;
	}

	public void setWithOutTextAmount(BigDecimal withOutTextAmount) {
		this.withOutTextAmount = withOutTextAmount;
	}

	public BigDecimal getBudgetAmount() {
		return budgetAmount;
	}

	public void setBudgetAmount(BigDecimal budgetAmount) {
		this.budgetAmount = budgetAmount;
	}

	private String ccbFid;

	public String getCcbFid() {
		return ccbFid;
	}

	public void setCcbFid(String ccbFid) {
		this.ccbFid = ccbFid;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public int getLevel() {
		return level;
	}
	public void setLevel(int level) {
		this.level = level;
	}
	public String getLongNumber() {
		return longNumber;
	}
	public void setLongNumber(String longNumber) {
		this.longNumber = longNumber;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public BigDecimal getControlAmount() {
		return controlAmount;
	}

	public void setControlAmount(BigDecimal controlAmount) {
		this.controlAmount = controlAmount;
	}

	public BigDecimal getBalance() {
		return balance;
	}

	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}

	public BigDecimal getControlBalance() {
		return controlBalance;
	}

	public void setControlBalance(BigDecimal controlBalance) {
		this.controlBalance = controlBalance;
	}
	public String getInvite() {
		return invite;
	}
	public void setInvite(String invite) {
		this.invite = invite;
	}
	public String getContractname() {
		return contractname;
	}
	public void setContractname(String contractname) {
		this.contractname = contractname;
	}
	public String getSignSec() {
		return signSec;
	}
	public void setSignSec(String signSec) {
		this.signSec = signSec;
	}

	public BigDecimal getSignUpAmount() {
		return signUpAmount;
	}

	public void setSignUpAmount(BigDecimal signUpAmount) {
		this.signUpAmount = signUpAmount;
	}

	public BigDecimal getChangeAmount() {
		return changeAmount;
	}

	public void setChangeAmount(BigDecimal changeAmount) {
		this.changeAmount = changeAmount;
	}

	public BigDecimal getNewCost() {
		return newCost;
	}

	public void setNewCost(BigDecimal newCost) {
		this.newCost = newCost;
	}

	public BigDecimal getSettleAmount() {
		return settleAmount;
	}

	public void setSettleAmount(BigDecimal settleAmount) {
		this.settleAmount = settleAmount;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}


}
