package com.kingdee.eas.fdc.contract;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractMarketYearProjectInfo extends com.kingdee.eas.fdc.basedata.FDCBillInfo implements Serializable 
{
    public AbstractMarketYearProjectInfo()
    {
        this("id");
    }
    protected AbstractMarketYearProjectInfo(String pkField)
    {
        super(pkField);
        put("entry", new com.kingdee.eas.fdc.contract.MarketYearProjectEntryCollection());
    }
    /**
     * Object: Ӫ�����Ԥ�� 's ��¼ property 
     */
    public com.kingdee.eas.fdc.contract.MarketYearProjectEntryCollection getEntry()
    {
        return (com.kingdee.eas.fdc.contract.MarketYearProjectEntryCollection)get("entry");
    }
    /**
     * Object:Ӫ�����Ԥ��'s ���property 
     */
    public int getYear()
    {
        return getInt("year");
    }
    public void setYear(int item)
    {
        setInt("year", item);
    }
    /**
     * Object:Ӫ�����Ԥ��'s �汾��property 
     */
    public int getVersion()
    {
        return getInt("version");
    }
    public void setVersion(int item)
    {
        setInt("version", item);
    }
    /**
     * Object:Ӫ�����Ԥ��'s �Ƿ�����property 
     */
    public boolean isIsLatest()
    {
        return getBoolean("isLatest");
    }
    public void setIsLatest(boolean item)
    {
        setBoolean("isLatest", item);
    }
    /**
     * Object:Ӫ�����Ԥ��'s ����ܶ�property 
     */
    public java.math.BigDecimal getTotalAmount()
    {
        return getBigDecimal("totalAmount");
    }
    public void setTotalAmount(java.math.BigDecimal item)
    {
        setBigDecimal("totalAmount", item);
    }
    /**
     * Object:Ӫ�����Ԥ��'s ��һ�汾����ܶ�property 
     */
    public java.math.BigDecimal getLastTotalAmount()
    {
        return getBigDecimal("lastTotalAmount");
    }
    public void setLastTotalAmount(java.math.BigDecimal item)
    {
        setBigDecimal("lastTotalAmount", item);
    }
    /**
     * Object:Ӫ�����Ԥ��'s ���ǩԼָ�꣨��property 
     */
    public java.math.BigDecimal getSign()
    {
        return getBigDecimal("sign");
    }
    public void setSign(java.math.BigDecimal item)
    {
        setBigDecimal("sign", item);
    }
    /**
     * Object:Ӫ�����Ԥ��'s ���Ӫ�����ʣ�ȫ��Ŀ��%property 
     */
    public java.math.BigDecimal getRate()
    {
        return getBigDecimal("rate");
    }
    public void setRate(java.math.BigDecimal item)
    {
        setBigDecimal("rate", item);
    }
    /**
     * Object:Ӫ�����Ԥ��'s ���Ӫ������Ԥ�㣨���˿�Ŀ����property 
     */
    public java.math.BigDecimal getYearAmount()
    {
        return getBigDecimal("yearAmount");
    }
    public void setYearAmount(java.math.BigDecimal item)
    {
        setBigDecimal("yearAmount", item);
    }
    /**
     * Object:Ӫ�����Ԥ��'s ���Ӫ�����ʣ����˿�Ŀ��%property 
     */
    public java.math.BigDecimal getYearRate()
    {
        return getBigDecimal("yearRate");
    }
    public void setYearRate(java.math.BigDecimal item)
    {
        setBigDecimal("yearRate", item);
    }
    /**
     * Object: Ӫ�����Ԥ�� 's Ӫ����Ŀ property 
     */
    public com.kingdee.eas.fdc.sellhouse.SellProjectInfo getSellProject()
    {
        return (com.kingdee.eas.fdc.sellhouse.SellProjectInfo)get("sellProject");
    }
    public void setSellProject(com.kingdee.eas.fdc.sellhouse.SellProjectInfo item)
    {
        put("sellProject", item);
    }
    /**
     * Object:Ӫ�����Ԥ��'s ��Ŀ����property 
     */
    public String getSellProjecttxt()
    {
        return getString("sellProjecttxt");
    }
    public void setSellProjecttxt(String item)
    {
        setString("sellProjecttxt", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("12113D65");
    }
}