package com.kingdee.eas.fdc.invite;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractAppraiseTempletTypeInfo extends com.kingdee.eas.framework.TreeBaseInfo implements Serializable 
{
    public AbstractAppraiseTempletTypeInfo()
    {
        this("id");
    }
    protected AbstractAppraiseTempletTypeInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object:����ģ�����'s ����property 
     */
    public boolean isIsEnabled()
    {
        return getBoolean("isEnabled");
    }
    public void setIsEnabled(boolean item)
    {
        setBoolean("isEnabled", item);
    }
    /**
     * Object: ����ģ����� 's ������֯ property 
     */
    public com.kingdee.eas.basedata.org.FullOrgUnitInfo getOrgUnit()
    {
        return (com.kingdee.eas.basedata.org.FullOrgUnitInfo)get("orgUnit");
    }
    public void setOrgUnit(com.kingdee.eas.basedata.org.FullOrgUnitInfo item)
    {
        put("orgUnit", item);
    }
    /**
     * Object: ����ģ����� 's �ϼ�����ģ������ property 
     */
    public com.kingdee.eas.fdc.invite.AppraiseTempletTypeInfo getParent()
    {
        return (com.kingdee.eas.fdc.invite.AppraiseTempletTypeInfo)get("parent");
    }
    public void setParent(com.kingdee.eas.fdc.invite.AppraiseTempletTypeInfo item)
    {
        put("parent", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("CEED1492");
    }
}