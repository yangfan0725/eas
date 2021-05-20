package com.kingdee.eas.fdc.basecrm;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractSHERevbillTwoEntryInfo extends com.kingdee.eas.framework.CoreBillEntryBaseInfo implements Serializable 
{
    public AbstractSHERevbillTwoEntryInfo()
    {
        this("id");
    }
    protected AbstractSHERevbillTwoEntryInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object:�ո����������'s nullproperty 
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
     * Object:�ո����������'s ���property 
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
     * Object:�ո����������'s �ո��Idproperty 
     */
    public com.kingdee.bos.util.BOSUuid getSheRevBillEntryId()
    {
        return getBOSUuid("sheRevBillEntryId");
    }
    public void setSheRevBillEntryId(com.kingdee.bos.util.BOSUuid item)
    {
        setBOSUuid("sheRevBillEntryId", item);
    }
    /**
     * Object: �ո���������� 's �ո����¼ property 
     */
    public com.kingdee.eas.fdc.basecrm.SHERevBillEntryInfo getHead()
    {
        return (com.kingdee.eas.fdc.basecrm.SHERevBillEntryInfo)get("head");
    }
    public void setHead(com.kingdee.eas.fdc.basecrm.SHERevBillEntryInfo item)
    {
        put("head", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("0AE8D85C");
    }
}