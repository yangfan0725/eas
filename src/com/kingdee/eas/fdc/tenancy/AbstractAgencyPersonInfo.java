package com.kingdee.eas.fdc.tenancy;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractAgencyPersonInfo extends com.kingdee.eas.framework.BillEntryBaseInfo implements Serializable 
{
    public AbstractAgencyPersonInfo()
    {
        this("id");
    }
    protected AbstractAgencyPersonInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: ��Ա 's ������Ա property 
     */
    public com.kingdee.eas.fdc.tenancy.AgencyInfo getParent()
    {
        return (com.kingdee.eas.fdc.tenancy.AgencyInfo)get("parent");
    }
    public void setParent(com.kingdee.eas.fdc.tenancy.AgencyInfo item)
    {
        put("parent", item);
    }
    /**
     * Object:��Ա's ְλproperty 
     */
    public String getJob()
    {
        return getString("job");
    }
    public void setJob(String item)
    {
        setString("job", item);
    }
    /**
     * Object:��Ա's ����property 
     */
    public String getName()
    {
        return getString("name");
    }
    public void setName(String item)
    {
        setString("name", item);
    }
    /**
     * Object:��Ա's ��ϵ�绰property 
     */
    public String getPhone()
    {
        return getString("phone");
    }
    public void setPhone(String item)
    {
        setString("phone", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("531047E3");
    }
}