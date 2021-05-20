package com.kingdee.eas.fdc.contract;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractMarketMonthProjectInfo extends com.kingdee.eas.fdc.basedata.FDCBillInfo implements Serializable 
{
    public AbstractMarketMonthProjectInfo()
    {
        this("id");
    }
    protected AbstractMarketMonthProjectInfo(String pkField)
    {
        super(pkField);
        put("entry", new com.kingdee.eas.fdc.contract.MarketMonthProjectEntryCollection());
    }
    /**
     * Object: Ӫ���¶�Ԥ�� 's ��¼ property 
     */
    public com.kingdee.eas.fdc.contract.MarketMonthProjectEntryCollection getEntry()
    {
        return (com.kingdee.eas.fdc.contract.MarketMonthProjectEntryCollection)get("entry");
    }
    /**
     * Object:Ӫ���¶�Ԥ��'s ���property 
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
     * Object:Ӫ���¶�Ԥ��'s �¶�property 
     */
    public int getMonth()
    {
        return getInt("month");
    }
    public void setMonth(int item)
    {
        setInt("month", item);
    }
    /**
     * Object:Ӫ���¶�Ԥ��'s ��Ŀ����property 
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
        return new BOSObjectType("F0E4E850");
    }
}