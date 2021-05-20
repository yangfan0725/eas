package com.kingdee.eas.fdc.sellhouse;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractAgioSchemeInfo extends com.kingdee.eas.fdc.basedata.FDCDataBaseInfo implements Serializable 
{
    public AbstractAgioSchemeInfo()
    {
        this("id");
    }
    protected AbstractAgioSchemeInfo(String pkField)
    {
        super(pkField);
        put("agioSchemeEntry", new com.kingdee.eas.fdc.sellhouse.AgioSchemeEntryCollection());
    }
    /**
     * Object:�ۿ۷���'s ��Чʱ��property 
     */
    public java.util.Date getValidDate()
    {
        return getDate("validDate");
    }
    public void setValidDate(java.util.Date item)
    {
        setDate("validDate", item);
    }
    /**
     * Object:�ۿ۷���'s ʧЧʱ��property 
     */
    public java.util.Date getInvalidDate()
    {
        return getDate("invalidDate");
    }
    public void setInvalidDate(java.util.Date item)
    {
        setDate("invalidDate", item);
    }
    /**
     * Object: �ۿ۷��� 's �ۿ۹����¼ property 
     */
    public com.kingdee.eas.fdc.sellhouse.AgioSchemeEntryCollection getAgioSchemeEntry()
    {
        return (com.kingdee.eas.fdc.sellhouse.AgioSchemeEntryCollection)get("agioSchemeEntry");
    }
    /**
     * Object: �ۿ۷��� 's ������Ŀ property 
     */
    public com.kingdee.eas.fdc.sellhouse.SellProjectInfo getSellProject()
    {
        return (com.kingdee.eas.fdc.sellhouse.SellProjectInfo)get("sellProject");
    }
    public void setSellProject(com.kingdee.eas.fdc.sellhouse.SellProjectInfo item)
    {
        put("sellProject", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("918C91AC");
    }
}