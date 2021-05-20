package com.kingdee.eas.fdc.sellhouse;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractPossessionStandardInfo extends com.kingdee.eas.framework.DataBaseInfo implements Serializable 
{
    public AbstractPossessionStandardInfo()
    {
        this("id");
    }
    protected AbstractPossessionStandardInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object:������׼'s IDproperty 
     */
    public com.kingdee.bos.util.BOSUuid getId()
    {
        return getBOSUuid("id");
    }
    public void setId(com.kingdee.bos.util.BOSUuid item)
    {
        setBOSUuid("id", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("CA7170BA");
    }
}