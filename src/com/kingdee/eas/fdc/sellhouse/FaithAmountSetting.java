package com.kingdee.eas.fdc.sellhouse;

import java.io.Serializable;
import java.math.BigDecimal;

public class FaithAmountSetting implements Serializable {
	private BigDecimal faithAmount;	 //Ĭ�ϳ���������

	public void setFaithAmount(BigDecimal faithAmount) {
		this.faithAmount = faithAmount;
	}

	public BigDecimal getFaithAmount() {
		return faithAmount; 
	}
}
