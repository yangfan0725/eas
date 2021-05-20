package com.kingdee.eas.fdc.finance;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractWorkLoadPayVoucherEntryInfo extends com.kingdee.eas.fdc.finance.FDCBaseVoucherEntryInfo implements Serializable 
{
    public AbstractWorkLoadPayVoucherEntryInfo()
    {
        this("id");
    }
    protected AbstractWorkLoadPayVoucherEntryInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: ��������ָ����¼ 's ������ȷ�ϵ� property 
     */
    public com.kingdee.eas.fdc.finance.WorkLoadConfirmBillInfo getParent()
    {
        return (com.kingdee.eas.fdc.finance.WorkLoadConfirmBillInfo)get("parent");
    }
    public void setParent(com.kingdee.eas.fdc.finance.WorkLoadConfirmBillInfo item)
    {
        put("parent", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("5381B0A6");
    }
}