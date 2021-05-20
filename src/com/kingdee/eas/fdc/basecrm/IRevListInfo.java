package com.kingdee.eas.fdc.basecrm;

import java.math.BigDecimal;

import com.kingdee.bos.dao.IObjectValue;

/**
 * �տ���ϸ��ֵ����ӿ�,���տ���տ���ϸ����Ҫʵ�ָýӿ�
 * ���Ϲ���Ӧ����ϸ,����Ӧ����ϸ,���޺�ͬӦ����ϸ��
 * */
public interface IRevListInfo extends IObjectValue{
	/**
	 * ������Ϣ,ʵ�������ɷ���.���տ�༭������ʾʹ��
	 * @deprecated ��ʱ������
	 * */
	String getDesc();
	
	/**
	 * �������Ӧ�ս��.������֮���ܵ�Ӧ���Ƕ���
	 * ��Ҫ����Ϊ���ޣ���ҵ�Լ����ʵ�ַ�ʽ��һ�£����޼����ᷴдӦ�ս�����ҵ���ᣩ
	 * @return ������¥��û�м��⣬���� Ӧ�ս��
	 *            ���ޣ�������������Ӧ���У����� Ӧ�ս��
	 *            ��ҵ������δ������Ӧ���У����� Ӧ�ս��-������
	 * */
	BigDecimal getFinalAppAmount();
	
	/**
	 * ��õ���������
	 * ʵ�ַ�ʽΪ��ʵ�ս��-�ѵ�
	 * */
	BigDecimal getActRevAmountAfterAdjust();
	
	/**
	 * �����ʣ����
	 * ʵ�ַ�ʽΪ������������-��ת-����-ʵ�ʻ������
	 * */
	BigDecimal getAllRemainAmount();
	
	/**
	 * ���ʣ����
	 * ʵ�ַ�ʽΪ������������-��ת-����-�������
	 * */
	BigDecimal getRemainAmount();
	
	/**
	 * ���ʣ�����ƽ��
	 * ʵ�ַ�ʽΪ��
	 * 	if(ʣ�������){
			return ʣ����;
		}else{
			return ���ƽ��-��ת-����-�ѵ�;
		}
	 * */
	BigDecimal getRemainLimitAmount();
	
	/**
	 * �����ϸ������
	 * */
	RevListTypeEnum getRevListTypeEnum();
	
	/**
     * �տ���ϸ's Ӧ�ս��property 
     */
    public java.math.BigDecimal getAppAmount();
    public void setAppAmount(java.math.BigDecimal item);
    /**
     * �տ���ϸ's Ӧ������property 
     */
    public java.util.Date getAppDate();
    public void setAppDate(java.util.Date item);
    /**
     * �տ���ϸ 's �������� property 
     */
    public com.kingdee.eas.fdc.sellhouse.MoneyDefineInfo getMoneyDefine();
    public void setMoneyDefine(com.kingdee.eas.fdc.sellhouse.MoneyDefineInfo item);
    /**
     * �տ���ϸ's ʵ�ս��property 
     */
    public java.math.BigDecimal getActRevAmount();
    public void setActRevAmount(java.math.BigDecimal item);
    /**
     * �տ���ϸ's ������ý��property 
     */
    public java.math.BigDecimal getToFeeAmount();
    public void setToFeeAmount(java.math.BigDecimal item);
    /**
     * �տ���ϸ's ��ת����ý��property 
     */
    public java.math.BigDecimal getHasToFeeAmount();
    public void setHasToFeeAmount(java.math.BigDecimal item);
    /**
     * �տ���ϸ's ���ƽ��property 
     */
    public java.math.BigDecimal getLimitAmount();
    public void setLimitAmount(java.math.BigDecimal item);
    /**
     * �տ���ϸ's ��ת���property 
     */
    public java.math.BigDecimal getHasTransferredAmount();
    public void setHasTransferredAmount(java.math.BigDecimal item);
    /**
     * �տ���ϸ's ���˽��property 
     */
    public java.math.BigDecimal getHasRefundmentAmount();
    public void setHasRefundmentAmount(java.math.BigDecimal item);
    /**
     * �տ���ϸ's �ѵ����property 
     */
    public java.math.BigDecimal getHasAdjustedAmount();
    public void setHasAdjustedAmount(java.math.BigDecimal item);
    /**
     * �տ���ϸ's �Ƿ�ʣ�������property 
     */
    public boolean isIsRemainCanRefundment();
    public void setIsRemainCanRefundment(boolean item);
    /**
     * �տ���ϸ's �տ�����ʶproperty 
     */
    public com.kingdee.eas.fdc.basecrm.RevMoneyTypeEnum getRevMoneyType();
    public void setRevMoneyType(com.kingdee.eas.fdc.basecrm.RevMoneyTypeEnum item);
    /**
     * �տ���ϸ's �˿�����ʶproperty 
     */
    public com.kingdee.eas.fdc.basecrm.RefundmentMoneyTypeEnum getRefundmentMoneyType();
    public void setRefundmentMoneyType(com.kingdee.eas.fdc.basecrm.RefundmentMoneyTypeEnum item);
    /**
     * �տ���ϸ's �Ƿ�ɳ���property 
     */
    public boolean isIsCanRevBeyond();
    public void setIsCanRevBeyond(boolean item);
    /**
     * �տ���ϸ's ���ÿ����ʶproperty 
     */
    public com.kingdee.eas.fdc.basecrm.FeeMoneyTypeEnum getFeeMoneyType();
    public void setFeeMoneyType(com.kingdee.eas.fdc.basecrm.FeeMoneyTypeEnum item);
    
    /**
     * EAS����'s IDproperty 
     */
    public com.kingdee.bos.util.BOSUuid getId();
    public void setId(com.kingdee.bos.util.BOSUuid item);
          
    /**
     * �տ���ϸ's ʵ������property 
     */
    public java.util.Date getActRevDate();
    public void setActRevDate(java.util.Date item);
    
    /**
     * �տ���ϸ's ���property 
     */
    public int getSeq();
    public void setSeq(int item);
    
    public BigDecimal getInvoiceAmount();
    public void setInvoiceAmount(BigDecimal amount);
}
