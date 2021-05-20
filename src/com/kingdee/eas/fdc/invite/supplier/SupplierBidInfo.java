package com.kingdee.eas.fdc.invite.supplier;

import java.io.Serializable;
import java.math.BigDecimal;

public class SupplierBidInfo implements Serializable {
	
	private String supplierName;
	private String grade;
	private String inviteTypeId;
	private String inviteTypeName;
	private BigDecimal score;
	private BigDecimal qualificationCount;
	private BigDecimal winCount;
	private BigDecimal highCount;
	private BigDecimal noneCount;
	private BigDecimal illegalCount;
	private BigDecimal unQualifed;
	private BigDecimal giveUpCount;
	private BigDecimal lowestWinCount;
	private BigDecimal yearScore;
	private BigDecimal checkScore;
	private BigDecimal executeScore;
	private BigDecimal winAmt;
	private BigDecimal directAmt;
	private BigDecimal directCount;
	
	
	
	
	public BigDecimal getDirectCount() {
		return directCount;
	}
	public void setDirectCount(BigDecimal directCount) {
		this.directCount = directCount;
	}
	public BigDecimal getDirectAmt() {
		return directAmt;
	}
	public void setDirectAmt(BigDecimal directAmt) {
		this.directAmt = directAmt;
	}
	public BigDecimal getWinAmt() {
		return winAmt;
	}
	public void setWinAmt(BigDecimal winAmt) {
		this.winAmt = winAmt;
	}
	public BigDecimal getYearScore() {
		return yearScore;
	}
	public void setYearScore(BigDecimal yearScore) {
		this.yearScore = yearScore;
	}
	public BigDecimal getCheckScore() {
		return checkScore;
	}
	public void setCheckScore(BigDecimal checkScore) {
		this.checkScore = checkScore;
	}
	public BigDecimal getExecuteScore() {
		return executeScore;
	}
	public void setExecuteScore(BigDecimal executeScore) {
		this.executeScore = executeScore;
	}
	public BigDecimal getLowestWinCount() {
		return lowestWinCount;
	}
	public void setLowestWinCount(BigDecimal lowestWinCount) {
		this.lowestWinCount = lowestWinCount;
	}
	public String getSupplierName() {
		return supplierName;
	}
	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}
	public String getGrade() {
		return grade;
	}
	public void setGrade(String grade) {
		this.grade = grade;
	}
	public String getInviteTypeId() {
		return inviteTypeId;
	}
	public void setInviteTypeId(String inviteTypeId) {
		this.inviteTypeId = inviteTypeId;
	}
	public String getInviteTypeName() {
		return inviteTypeName;
	}
	public void setInviteTypeName(String inviteTypeName) {
		this.inviteTypeName = inviteTypeName;
	}
	public BigDecimal getScore() {
		return score;
	}
	public void setScore(BigDecimal score) {
		this.score = score;
	}
	public BigDecimal getQualificationCount() {
		return qualificationCount;
	}
	public void setQualificationCount(BigDecimal qualificationCount) {
		this.qualificationCount = qualificationCount;
	}
	public BigDecimal getWinCount() {
		return winCount;
	}
	public void setWinCount(BigDecimal winCount) {
		this.winCount = winCount;
	}
	public BigDecimal getHighCount() {
		return highCount;
	}
	public void setHighCount(BigDecimal highCount) {
		this.highCount = highCount;
	}
	public BigDecimal getNoneCount() {
		return noneCount;
	}
	public void setNoneCount(BigDecimal noneCount) {
		this.noneCount = noneCount;
	}
	public BigDecimal getIllegalCount() {
		return illegalCount;
	}
	public void setIllegalCount(BigDecimal illegalCount) {
		this.illegalCount = illegalCount;
	}
	public BigDecimal getUnQualifed() {
		return unQualifed;
	}
	public void setUnQualifed(BigDecimal unQualifed) {
		this.unQualifed = unQualifed;
	}
	public BigDecimal getGiveUpCount() {
		return giveUpCount;
	}
	public void setGiveUpCount(BigDecimal giveUpCount) {
		this.giveUpCount = giveUpCount;
	}
	
	
	
	
	
	
	

}
