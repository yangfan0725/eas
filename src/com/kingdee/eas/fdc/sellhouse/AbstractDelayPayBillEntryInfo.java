package com.kingdee.eas.fdc.sellhouse;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractDelayPayBillEntryInfo extends com.kingdee.eas.fdc.sellhouse.TranPayListEntryInfo implements Serializable 
{
    public AbstractDelayPayBillEntryInfo()
    {
        this("id");
    }
    protected AbstractDelayPayBillEntryInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: 延期付款申请分录 's 签约单id property 
     */
    public com.kingdee.eas.fdc.sellhouse.DelayPayBillInfo getHead()
    {
        return (com.kingdee.eas.fdc.sellhouse.DelayPayBillInfo)get("head");
    }
    public void setHead(com.kingdee.eas.fdc.sellhouse.DelayPayBillInfo item)
    {
        put("head", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("AF25B88B");
    }
}