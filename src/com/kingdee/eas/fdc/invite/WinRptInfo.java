package com.kingdee.eas.fdc.invite;

import java.io.Serializable;

import com.kingdee.eas.custom.purchasebaseinfomation.PurchaseCategoryInfo;

public class WinRptInfo implements Serializable {
	
	private int count;
	private PurchaseCategoryInfo pc;
	private InviteTypeInfo it;
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public PurchaseCategoryInfo getPc() {
		return pc;
	}
	public void setPc(PurchaseCategoryInfo pc) {
		this.pc = pc;
	}
	public InviteTypeInfo getIt() {
		return it;
	}
	public void setIt(InviteTypeInfo it) {
		this.it = it;
	}

}
