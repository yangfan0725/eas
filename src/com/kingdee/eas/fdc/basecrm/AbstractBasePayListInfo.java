package com.kingdee.eas.fdc.basecrm;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractBasePayListInfo extends com.kingdee.eas.framework.CoreBaseInfo implements Serializable 
{
    public AbstractBasePayListInfo()
    {
        this("id");
    }
    protected AbstractBasePayListInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object:����Ӧ����ϸ����'s Ӧ�տ�property 
     */
    public java.math.BigDecimal getReceivableAmount()
    {
        return getBigDecimal("receivableAmount");
    }
    public void setReceivableAmount(java.math.BigDecimal item)
    {
        setBigDecimal("receivableAmount", item);
    }
    /**
     * Object:����Ӧ����ϸ����'s Ӧ������property 
     */
    public java.sql.Timestamp getReceDate()
    {
        return getTimestamp("receDate");
    }
    public void setReceDate(java.sql.Timestamp item)
    {
        setTimestamp("receDate", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("95DA079A");
    }
}