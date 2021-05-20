package com.kingdee.eas.fdc.finance;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractFDCAdjustProductEntryInfo extends com.kingdee.eas.framework.CoreBaseInfo implements Serializable 
{
    public AbstractFDCAdjustProductEntryInfo()
    {
        this("id");
    }
    protected AbstractFDCAdjustProductEntryInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object:��������¼������Ʒ����'s nullproperty 
     */
    public com.kingdee.bos.util.BOSUuid getId()
    {
        return getBOSUuid("id");
    }
    public void setId(com.kingdee.bos.util.BOSUuid item)
    {
        setBOSUuid("id", item);
    }
    /**
     * Object: ��������¼������Ʒ���� 's ��������¼ property 
     */
    public com.kingdee.eas.fdc.finance.FDCAdjustBillEntryInfo getAdjustBillEntry()
    {
        return (com.kingdee.eas.fdc.finance.FDCAdjustBillEntryInfo)get("adjustBillEntry");
    }
    public void setAdjustBillEntry(com.kingdee.eas.fdc.finance.FDCAdjustBillEntryInfo item)
    {
        put("adjustBillEntry", item);
    }
    /**
     * Object: ��������¼������Ʒ���� 's ��Ʒ���� property 
     */
    public com.kingdee.eas.fdc.basedata.ProductTypeInfo getProductType()
    {
        return (com.kingdee.eas.fdc.basedata.ProductTypeInfo)get("productType");
    }
    public void setProductType(com.kingdee.eas.fdc.basedata.ProductTypeInfo item)
    {
        put("productType", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("2AEE5488");
    }
}