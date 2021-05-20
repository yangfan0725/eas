package com.kingdee.eas.fdc.sellhouse;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractMarketingCommissionEntryInfo extends com.kingdee.eas.framework.CoreBillEntryBaseInfo implements Serializable 
{
    public AbstractMarketingCommissionEntryInfo()
    {
        this("id");
    }
    protected AbstractMarketingCommissionEntryInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: ��¼ 's Ա�� property 
     */
    public com.kingdee.eas.basedata.person.PersonInfo getPerson()
    {
        return (com.kingdee.eas.basedata.person.PersonInfo)get("person");
    }
    public void setPerson(com.kingdee.eas.basedata.person.PersonInfo item)
    {
        put("person", item);
    }
    /**
     * Object: ��¼ 's Ӷ����㵥 property 
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
     * Object:��¼'s ��ͬ���property 
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
     * Object:��¼'s ��ͬĿ����property 
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
     * Object:��¼'s �ؿ���property 
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
     * Object:��¼'s �ؿ�Ŀ����property 
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
     * Object:��¼'s ��ͬ��ɱ���property 
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
     * Object:��¼'s �ؿ���ɱ���property 
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
     * Object:��¼'s ��Ʒ����property 
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
     * Object:��¼'s �ά����property 
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
     * Object:��¼'s ��ɱ���property 
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
     * Object:��¼'s ����property 
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
     * Object:��¼'s Ԥ�����property 
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
     * Object:��¼'s �������property 
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
     * Object:��¼'s ʵ�ʷ��Ž�����property 
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
     * Object:��¼'s ����property 
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
     * Object:��¼'s ��λproperty 
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
     * Object:��¼'s bonusTypeproperty 
     */
    public int getBonusType()
    {
        return getInt("bonusType");
    }
    public void setBonusType(int item)
    {
        setInt("bonusType", item);
    }
    /**
     * Object:��¼'s �¶��Ϲ��б꣨Ԫ��property 
     */
    public java.math.BigDecimal getMpur()
    {
        return getBigDecimal("mpur");
    }
    public void setMpur(java.math.BigDecimal item)
    {
        setBigDecimal("mpur", item);
    }
    /**
     * Object:��¼'s �¶Ⱥ�ͬ�б꣨Ԫ��property 
     */
    public java.math.BigDecimal getMcontract()
    {
        return getBigDecimal("mcontract");
    }
    public void setMcontract(java.math.BigDecimal item)
    {
        setBigDecimal("mcontract", item);
    }
    /**
     * Object:��¼'s ����Ϲ��Ԫ��property 
     */
    public java.math.BigDecimal getPur()
    {
        return getBigDecimal("pur");
    }
    public void setPur(java.math.BigDecimal item)
    {
        setBigDecimal("pur", item);
    }
    /**
     * Object:��¼'s ��ɺ�ͬ�Ԫ��property 
     */
    public java.math.BigDecimal getContract()
    {
        return getBigDecimal("contract");
    }
    public void setContract(java.math.BigDecimal item)
    {
        setBigDecimal("contract", item);
    }
    /**
     * Object:��¼'s �Ϲ��б�����ʣ�%��property 
     */
    public java.math.BigDecimal getPurRate()
    {
        return getBigDecimal("purRate");
    }
    public void setPurRate(java.math.BigDecimal item)
    {
        setBigDecimal("purRate", item);
    }
    /**
     * Object:��¼'s ��ͬ�б�����ʣ�%��property 
     */
    public java.math.BigDecimal getContractRate()
    {
        return getBigDecimal("contractRate");
    }
    public void setContractRate(java.math.BigDecimal item)
    {
        setBigDecimal("contractRate", item);
    }
    /**
     * Object:��¼'s �ά����property 
     */
    public java.math.BigDecimal getCalcTBonus()
    {
        return getBigDecimal("calcTBonus");
    }
    public void setCalcTBonus(java.math.BigDecimal item)
    {
        setBigDecimal("calcTBonus", item);
    }
    /**
     * Object:��¼'s ��ɱ���property 
     */
    public java.math.BigDecimal getCalcTBonusRate()
    {
        return getBigDecimal("calcTBonusRate");
    }
    public void setCalcTBonusRate(java.math.BigDecimal item)
    {
        setBigDecimal("calcTBonusRate", item);
    }
    /**
     * Object:��¼'s �����������property 
     */
    public java.math.BigDecimal getXz()
    {
        return getBigDecimal("xz");
    }
    public void setXz(java.math.BigDecimal item)
    {
        setBigDecimal("xz", item);
    }
    /**
     * Object:��¼'s Ԥ������property 
     */
    public java.math.BigDecimal getKeepRate()
    {
        return getBigDecimal("keepRate");
    }
    public void setKeepRate(java.math.BigDecimal item)
    {
        setBigDecimal("keepRate", item);
    }
    /**
     * Object:��¼'s ָ���ۺ������property 
     */
    public java.math.BigDecimal getZbRate()
    {
        return getBigDecimal("zbRate");
    }
    public void setZbRate(java.math.BigDecimal item)
    {
        setBigDecimal("zbRate", item);
    }
    /**
     * Object:��¼'s ������Աproperty 
     */
    public String getQdPerson()
    {
        return getString("qdPerson");
    }
    public void setQdPerson(String item)
    {
        setString("qdPerson", item);
    }
    /**
     * Object:��¼'s �Ƽ���property 
     */
    public String getRecommended()
    {
        return getString("recommended");
    }
    public void setRecommended(String item)
    {
        setString("recommended", item);
    }
    /**
     * Object:��¼'s ����Ϲ���property 
     */
    public java.math.BigDecimal getPurAmt()
    {
        return getBigDecimal("purAmt");
    }
    public void setPurAmt(java.math.BigDecimal item)
    {
        setBigDecimal("purAmt", item);
    }
    /**
     * Object:��¼'s �¶��Ϲ�ָ��property 
     */
    public java.math.BigDecimal getPurTarget()
    {
        return getBigDecimal("purTarget");
    }
    public void setPurTarget(java.math.BigDecimal item)
    {
        setBigDecimal("purTarget", item);
    }
    /**
     * Object:��¼'s �Ϲ�ָ�������property 
     */
    public java.math.BigDecimal getPurComplateRate()
    {
        return getBigDecimal("purComplateRate");
    }
    public void setPurComplateRate(java.math.BigDecimal item)
    {
        setBigDecimal("purComplateRate", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("FA10D91C");
    }
}