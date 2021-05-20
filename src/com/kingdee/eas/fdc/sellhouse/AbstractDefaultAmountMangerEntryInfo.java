package com.kingdee.eas.fdc.sellhouse;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractDefaultAmountMangerEntryInfo extends com.kingdee.eas.framework.CoreBillEntryBaseInfo implements Serializable 
{
    public AbstractDefaultAmountMangerEntryInfo()
    {
        this("id");
    }
    protected AbstractDefaultAmountMangerEntryInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: ΥԼ������¼ 's ΥԼ��ID property 
     */
    public com.kingdee.eas.fdc.sellhouse.DefaultAmountMangerInfo getHead()
    {
        return (com.kingdee.eas.fdc.sellhouse.DefaultAmountMangerInfo)get("head");
    }
    public void setHead(com.kingdee.eas.fdc.sellhouse.DefaultAmountMangerInfo item)
    {
        put("head", item);
    }
    /**
     * Object: ΥԼ������¼ 's ��������ҵ������ property 
     */
    public com.kingdee.eas.fdc.sellhouse.TranBusinessOverViewInfo getTranOverView()
    {
        return (com.kingdee.eas.fdc.sellhouse.TranBusinessOverViewInfo)get("tranOverView");
    }
    public void setTranOverView(com.kingdee.eas.fdc.sellhouse.TranBusinessOverViewInfo item)
    {
        put("tranOverView", item);
    }
    /**
     * Object:ΥԼ������¼'s Ӧ������property 
     */
    public java.util.Date getAppDate()
    {
        return getDate("appDate");
    }
    public void setAppDate(java.util.Date item)
    {
        setDate("appDate", item);
    }
    /**
     * Object:ΥԼ������¼'s Ӧ�ս��property 
     */
    public java.math.BigDecimal getAppAmount()
    {
        return getBigDecimal("appAmount");
    }
    public void setAppAmount(java.math.BigDecimal item)
    {
        setBigDecimal("appAmount", item);
    }
    /**
     * Object:ΥԼ������¼'s ʵ������property 
     */
    public java.util.Date getActDate()
    {
        return getDate("actDate");
    }
    public void setActDate(java.util.Date item)
    {
        setDate("actDate", item);
    }
    /**
     * Object:ΥԼ������¼'s ʵ�ս��property 
     */
    public java.math.BigDecimal getActAmount()
    {
        return getBigDecimal("actAmount");
    }
    public void setActAmount(java.math.BigDecimal item)
    {
        setBigDecimal("actAmount", item);
    }
    /**
     * Object:ΥԼ������¼'s Ƿ������property 
     */
    public int getArgDays()
    {
        return getInt("argDays");
    }
    public void setArgDays(int item)
    {
        setInt("argDays", item);
    }
    /**
     * Object:ΥԼ������¼'s Ƿ����property 
     */
    public java.math.BigDecimal getArgAmount()
    {
        return getBigDecimal("argAmount");
    }
    public void setArgAmount(java.math.BigDecimal item)
    {
        setBigDecimal("argAmount", item);
    }
    /**
     * Object:ΥԼ������¼'s �ο�ΥԼ��property 
     */
    public java.math.BigDecimal getReferAmount()
    {
        return getBigDecimal("referAmount");
    }
    public void setReferAmount(java.math.BigDecimal item)
    {
        setBigDecimal("referAmount", item);
    }
    /**
     * Object:ΥԼ������¼'s ����ΥԼ��property 
     */
    public java.math.BigDecimal getSubDeAmount()
    {
        return getBigDecimal("subDeAmount");
    }
    public void setSubDeAmount(java.math.BigDecimal item)
    {
        setBigDecimal("subDeAmount", item);
    }
    /**
     * Object:ΥԼ������¼'s ��תΥԼ��property 
     */
    public java.math.BigDecimal getCarryAmount()
    {
        return getBigDecimal("carryAmount");
    }
    public void setCarryAmount(java.math.BigDecimal item)
    {
        setBigDecimal("carryAmount", item);
    }
    /**
     * Object:ΥԼ������¼'s ��עproperty 
     */
    public String getRemark()
    {
        return getString("remark");
    }
    public void setRemark(String item)
    {
        setString("remark", item);
    }
    /**
     * Object: ΥԼ������¼ 's �������� property 
     */
    public com.kingdee.eas.fdc.sellhouse.MoneyDefineInfo getMoneyDefine()
    {
        return (com.kingdee.eas.fdc.sellhouse.MoneyDefineInfo)get("moneyDefine");
    }
    public void setMoneyDefine(com.kingdee.eas.fdc.sellhouse.MoneyDefineInfo item)
    {
        put("moneyDefine", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("C0FFB2FA");
    }
}