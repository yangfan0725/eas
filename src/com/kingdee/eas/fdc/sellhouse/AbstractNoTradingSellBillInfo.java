package com.kingdee.eas.fdc.sellhouse;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractNoTradingSellBillInfo extends com.kingdee.eas.fdc.basedata.FDCBillInfo implements Serializable 
{
    public AbstractNoTradingSellBillInfo()
    {
        this("id");
    }
    protected AbstractNoTradingSellBillInfo(String pkField)
    {
        super(pkField);
        put("entry", new com.kingdee.eas.fdc.sellhouse.NoTradingSellBillEntryCollection());
    }
    /**
     * Object: 非操盘项目销售回款确认单 's 分录 property 
     */
    public com.kingdee.eas.fdc.sellhouse.NoTradingSellBillEntryCollection getEntry()
    {
        return (com.kingdee.eas.fdc.sellhouse.NoTradingSellBillEntryCollection)get("entry");
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("AE89813E");
    }
}