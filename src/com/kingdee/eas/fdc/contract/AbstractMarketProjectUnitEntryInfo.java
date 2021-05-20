package com.kingdee.eas.fdc.contract;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractMarketProjectUnitEntryInfo extends com.kingdee.eas.framework.CoreBillEntryBaseInfo implements Serializable 
{
    public AbstractMarketProjectUnitEntryInfo()
    {
        this("id");
    }
    protected AbstractMarketProjectUnitEntryInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: Ӫ�������¼ 's ͷ property 
     */
    public com.kingdee.eas.fdc.contract.MarketProjectInfo getHead()
    {
        return (com.kingdee.eas.fdc.contract.MarketProjectInfo)get("head");
    }
    public void setHead(com.kingdee.eas.fdc.contract.MarketProjectInfo item)
    {
        put("head", item);
    }
    /**
     * Object:Ӫ�������¼'s �ȼ۵�λproperty 
     */
    public String getUnit()
    {
        return getString("unit");
    }
    public void setUnit(String item)
    {
        setString("unit", item);
    }
    /**
     * Object:Ӫ�������¼'s ����property 
     */
    public java.math.BigDecimal getAmount()
    {
        return getBigDecimal("amount");
    }
    public void setAmount(java.math.BigDecimal item)
    {
        setBigDecimal("amount", item);
    }
    /**
     * Object:Ӫ�������¼'s ��עproperty 
     */
    public String getRemark()
    {
        return getString("remark");
    }
    public void setRemark(String item)
    {
        setString("remark", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("48E1068C");
    }
}