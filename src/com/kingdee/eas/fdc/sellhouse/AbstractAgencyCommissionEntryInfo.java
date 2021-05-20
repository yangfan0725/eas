package com.kingdee.eas.fdc.sellhouse;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractAgencyCommissionEntryInfo extends com.kingdee.eas.framework.CoreBillEntryBaseInfo implements Serializable 
{
    public AbstractAgencyCommissionEntryInfo()
    {
        this("id");
    }
    protected AbstractAgencyCommissionEntryInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object:����Ӷ���¼'s ��ͬ���property 
     */
    public java.math.BigDecimal getContractAmt()
    {
        return getBigDecimal("contractAmt");
    }
    public void setContractAmt(java.math.BigDecimal item)
    {
        setBigDecimal("contractAmt", item);
    }
    /**
     * Object:����Ӷ���¼'s ��ͬĿ����property 
     */
    public java.math.BigDecimal getContractAmtTarget()
    {
        return getBigDecimal("contractAmtTarget");
    }
    public void setContractAmtTarget(java.math.BigDecimal item)
    {
        setBigDecimal("contractAmtTarget", item);
    }
    /**
     * Object:����Ӷ���¼'s �ؿ���property 
     */
    public java.math.BigDecimal getBackAmt()
    {
        return getBigDecimal("backAmt");
    }
    public void setBackAmt(java.math.BigDecimal item)
    {
        setBigDecimal("backAmt", item);
    }
    /**
     * Object:����Ӷ���¼'s �ؿ�Ŀ����property 
     */
    public java.math.BigDecimal getBackAmtTarget()
    {
        return getBigDecimal("backAmtTarget");
    }
    public void setBackAmtTarget(java.math.BigDecimal item)
    {
        setBigDecimal("backAmtTarget", item);
    }
    /**
     * Object:����Ӷ���¼'s ��ͬ��ɱ���property 
     */
    public java.math.BigDecimal getContractCompleteRate()
    {
        return getBigDecimal("contractCompleteRate");
    }
    public void setContractCompleteRate(java.math.BigDecimal item)
    {
        setBigDecimal("contractCompleteRate", item);
    }
    /**
     * Object:����Ӷ���¼'s �ؿ���ɱ���property 
     */
    public java.math.BigDecimal getBackCompleteRate()
    {
        return getBigDecimal("backCompleteRate");
    }
    public void setBackCompleteRate(java.math.BigDecimal item)
    {
        setBigDecimal("backCompleteRate", item);
    }
    /**
     * Object:����Ӷ���¼'s ��Ʒ����property 
     */
    public String getProductType()
    {
        return getString("productType");
    }
    public void setProductType(String item)
    {
        setString("productType", item);
    }
    /**
     * Object:����Ӷ���¼'s ְλproperty 
     */
    public String getPosition()
    {
        return getString("position");
    }
    public void setPosition(String item)
    {
        setString("position", item);
    }
    /**
     * Object:����Ӷ���¼'s ����������property 
     */
    public java.math.BigDecimal getCalcBonusAmt()
    {
        return getBigDecimal("calcBonusAmt");
    }
    public void setCalcBonusAmt(java.math.BigDecimal item)
    {
        setBigDecimal("calcBonusAmt", item);
    }
    /**
     * Object:����Ӷ���¼'s ����������property 
     */
    public java.math.BigDecimal getCalcBonusRate()
    {
        return getBigDecimal("calcBonusRate");
    }
    public void setCalcBonusRate(java.math.BigDecimal item)
    {
        setBigDecimal("calcBonusRate", item);
    }
    /**
     * Object:����Ӷ���¼'s Ӧ������property 
     */
    public java.math.BigDecimal getBonus()
    {
        return getBigDecimal("bonus");
    }
    public void setBonus(java.math.BigDecimal item)
    {
        setBigDecimal("bonus", item);
    }
    /**
     * Object:����Ӷ���¼'s Ԥ�����property 
     */
    public java.math.BigDecimal getKeepAmt()
    {
        return getBigDecimal("keepAmt");
    }
    public void setKeepAmt(java.math.BigDecimal item)
    {
        setBigDecimal("keepAmt", item);
    }
    /**
     * Object:����Ӷ���¼'s �������property 
     */
    public java.math.BigDecimal getAdjustAmt()
    {
        return getBigDecimal("adjustAmt");
    }
    public void setAdjustAmt(java.math.BigDecimal item)
    {
        setBigDecimal("adjustAmt", item);
    }
    /**
     * Object:����Ӷ���¼'s ʵ�����property 
     */
    public java.math.BigDecimal getRealBonusAmt()
    {
        return getBigDecimal("realBonusAmt");
    }
    public void setRealBonusAmt(java.math.BigDecimal item)
    {
        setBigDecimal("realBonusAmt", item);
    }
    /**
     * Object:����Ӷ���¼'s ��עproperty 
     */
    public String getDescription()
    {
        return getString("description");
    }
    public void setDescription(String item)
    {
        setString("description", item);
    }
    /**
     * Object: ����Ӷ���¼ 's Ӷ����㵥 property 
     */
    public com.kingdee.eas.fdc.sellhouse.CommissionSettlementBillInfo getParent()
    {
        return (com.kingdee.eas.fdc.sellhouse.CommissionSettlementBillInfo)get("parent");
    }
    public void setParent(com.kingdee.eas.fdc.sellhouse.CommissionSettlementBillInfo item)
    {
        put("parent", item);
    }
    /**
     * Object:����Ӷ���¼'s ��������property 
     */
    public String getPerson()
    {
        return getString("person");
    }
    public void setPerson(String item)
    {
        setString("person", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("42D36787");
    }
}