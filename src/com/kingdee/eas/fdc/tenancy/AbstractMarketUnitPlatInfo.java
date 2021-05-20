package com.kingdee.eas.fdc.tenancy;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractMarketUnitPlatInfo extends com.kingdee.eas.framework.CoreBaseInfo implements Serializable 
{
    public AbstractMarketUnitPlatInfo()
    {
        this("id");
    }
    protected AbstractMarketUnitPlatInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: Ӫ����Ԫƽ�ӱ� 's ������ property 
     */
    public com.kingdee.eas.base.permission.UserInfo getDutyPerson()
    {
        return (com.kingdee.eas.base.permission.UserInfo)get("dutyPerson");
    }
    public void setDutyPerson(com.kingdee.eas.base.permission.UserInfo item)
    {
        put("dutyPerson", item);
    }
    /**
     * Object: Ӫ����Ԫƽ�ӱ� 's ��Ա property 
     */
    public com.kingdee.eas.base.permission.UserInfo getMember()
    {
        return (com.kingdee.eas.base.permission.UserInfo)get("member");
    }
    public void setMember(com.kingdee.eas.base.permission.UserInfo item)
    {
        put("member", item);
    }
    /**
     * Object: Ӫ����Ԫƽ�ӱ� 's Ӫ����Ԫ property 
     */
    public com.kingdee.eas.fdc.tenancy.MarketingUnitInfo getMarketUnit()
    {
        return (com.kingdee.eas.fdc.tenancy.MarketingUnitInfo)get("marketUnit");
    }
    public void setMarketUnit(com.kingdee.eas.fdc.tenancy.MarketingUnitInfo item)
    {
        put("marketUnit", item);
    }
    /**
     * Object: Ӫ����Ԫƽ�ӱ� 's ��֯��Ԫ property 
     */
    public com.kingdee.eas.basedata.org.FullOrgUnitInfo getOrgUnit()
    {
        return (com.kingdee.eas.basedata.org.FullOrgUnitInfo)get("orgUnit");
    }
    public void setOrgUnit(com.kingdee.eas.basedata.org.FullOrgUnitInfo item)
    {
        put("orgUnit", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("4C278918");
    }
}