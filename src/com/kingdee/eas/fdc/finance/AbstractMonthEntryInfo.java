package com.kingdee.eas.fdc.finance;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractMonthEntryInfo extends com.kingdee.eas.framework.CoreBillEntryBaseInfo implements Serializable 
{
    public AbstractMonthEntryInfo()
    {
        this("id");
    }
    protected AbstractMonthEntryInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object:Ͷ�ʼƻ��·ݱ�'s �·�property 
     */
    public java.util.Date getMonth()
    {
        return getDate("month");
    }
    public void setMonth(java.util.Date item)
    {
        setDate("month", item);
    }
    /**
     * Object:Ͷ�ʼƻ��·ݱ�'s Ͷ�ʶ�property 
     */
    public java.math.BigDecimal getInvestValue()
    {
        return getBigDecimal("investValue");
    }
    public void setInvestValue(java.math.BigDecimal item)
    {
        setBigDecimal("investValue", item);
    }
    /**
     * Object: Ͷ�ʼƻ��·ݱ� 's ������¼ property 
     */
    public com.kingdee.eas.fdc.finance.ProjectInvestPlanEntryInfo getParent()
    {
        return (com.kingdee.eas.fdc.finance.ProjectInvestPlanEntryInfo)get("parent");
    }
    public void setParent(com.kingdee.eas.fdc.finance.ProjectInvestPlanEntryInfo item)
    {
        put("parent", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("D66B4421");
    }
}