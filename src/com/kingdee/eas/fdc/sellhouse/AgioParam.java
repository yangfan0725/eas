package com.kingdee.eas.fdc.sellhouse;

import java.util.Collection;

import com.kingdee.bos.dao.IObjectCollection;
import com.kingdee.eas.fdc.sellhouse.AgioEntryCollection;
import com.kingdee.eas.fdc.sellhouse.DigitEnum;
import com.kingdee.eas.fdc.sellhouse.PriceAccountTypeEnum;
import com.kingdee.eas.fdc.sellhouse.PurchaseAgioEntryCollection;
import com.kingdee.eas.fdc.sellhouse.PurchaseChangeAgioEntryCollection;
import com.kingdee.eas.fdc.sellhouse.ToIntegerTypeEnum;
import com.kingdee.eas.framework.DataBaseCollection;


/**
 *�ۿ۲��� 
 * @author jeegan_wang
 */
public class AgioParam {
	private AgioEntryCollection agios;			//�Ϲ��� �ۿ���Ϣ �� �Ϲ������ �ۿ���Ϣ 
														//PurchaseAgioEntryCollection  PurchaseChangeAgioEntryCollection
	private boolean isToInteger;		//�Ƿ��Զ�ȡ��
	private boolean isBasePriceSell;	//�Ƿ�ͼ�����
	private PriceAccountTypeEnum priceAccountType;	//�۸���㷽ʽ���ܼ۷��㵥�ۣ����۷����ܼ� 
	private ToIntegerTypeEnum toIntegerType;	//ȡ����ʽ���������룻���㣻��λ
	private DigitEnum digit;					//λ������λ��ʮλ����λ��ǧλ����λ��ʮ��λ
	private SpecialDiscountInfo specialAgio;
	public AgioParam() {
		agios = new AgioEntryCollection();
		isToInteger = false;
		
		//Ĭ��ȡ���������е�
		isBasePriceSell = false;
		priceAccountType = PriceAccountTypeEnum.StandSetPrice ;  
		toIntegerType = ToIntegerTypeEnum.Round;;
		digit = DigitEnum.EntryDigit;
		specialAgio=null;
	}
	
	/**
	 * �Ϲ��� �ۿ���Ϣ  �Ϲ������ �ۿ���Ϣ
	 * @return
	 */
	public AgioEntryCollection getAgios() {
		return agios;
	}
	
	/**
	 * �Ϲ��� �ۿ���Ϣ  �Ϲ������ �ۿ���Ϣ
	 * @param agios
	 */
	public void setAgios(AgioEntryCollection agios) {
		this.agios = agios;
	}
	
	
	/**
	 * λ������λ��ʮλ����λ��ǧλ����λ��ʮ��λ
	 * @return
	 */
	public DigitEnum getDigit() {
		return digit;
	}
	/**
	 * λ������λ��ʮλ����λ��ǧλ����λ��ʮ��λ
	 * @param digit
	 */
	public void setDigit(DigitEnum digit) {
		this.digit = digit;
	}
	
	/**
	 * ȡ����ʽ���������룻���㣻��λ
	 * @return
	 */
	public boolean isToInteger() {
		return isToInteger;
	}
	/**
	 * ȡ����ʽ���������룻���㣻��λ
	 * @param isToInteger
	 */
	public void setToInteger(boolean isToInteger) {
		this.isToInteger = isToInteger;
	}
	
	/**
	 * �Ƿ�ͼ�����
	 * @return
	 */
	public boolean isBasePriceSell(){
		return isBasePriceSell;
	}
	/**
	 * �Ƿ�ͼ�����
	 * @param isBasePriceSell
	 */
	public void setBasePriceSell(boolean isBasePriceSell){
		this.isBasePriceSell = isBasePriceSell;
	}
	
	
	/**
	 * �۸���㷽ʽ���ܼ۷��㵥�ۣ����۷����ܼ� 
	 * @return
	 */
	public PriceAccountTypeEnum getPriceAccountType() {
		return priceAccountType;
	}
	/**
	 * �۸���㷽ʽ���ܼ۷��㵥�ۣ����۷����ܼ� 
	 * @param priceAccountType
	 */
	public void setPriceAccountType(PriceAccountTypeEnum priceAccountType) {
		this.priceAccountType = priceAccountType;
	}
	
	/**
	 * ȡ����ʽ���������룻���㣻��λ
	 * @return
	 */
	public ToIntegerTypeEnum getToIntegerType() {
		return toIntegerType;
	}
	/**
	 * ȡ����ʽ���������룻���㣻��λ
	 * @param toIntegerType
	 */
	public void setToIntegerType(ToIntegerTypeEnum toIntegerType) {
		this.toIntegerType = toIntegerType;
	}
	
	
	
	public SpecialDiscountInfo getSpecialAgio() {
		return specialAgio;
	}
	public void setSpecialAgio(SpecialDiscountInfo specialAgio) {
		this.specialAgio = specialAgio;
	}
	
}