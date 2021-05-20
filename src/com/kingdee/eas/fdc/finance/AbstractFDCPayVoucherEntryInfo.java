package com.kingdee.eas.fdc.finance;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractFDCPayVoucherEntryInfo extends com.kingdee.eas.fdc.finance.FDCBaseVoucherEntryInfo implements Serializable 
{
    public AbstractFDCPayVoucherEntryInfo()
    {
        this("id");
    }
    protected AbstractFDCPayVoucherEntryInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: ���ز������¼ 's ��� property 
     */
    public com.kingdee.eas.fi.cas.PaymentBillInfo getParent()
    {
        return (com.kingdee.eas.fi.cas.PaymentBillInfo)get("parent");
    }
    public void setParent(com.kingdee.eas.fi.cas.PaymentBillInfo item)
    {
        put("parent", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("AF19A856");
    }
}