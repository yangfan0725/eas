package com.kingdee.eas.fdc.invite;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractTempletTypeInfo extends com.kingdee.eas.framework.TreeBaseInfo implements Serializable 
{
    public AbstractTempletTypeInfo()
    {
        this("id");
    }
    protected AbstractTempletTypeInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object:�б�����'s �Ƿ�����property 
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
     * Object: �б����� 's ���ڵ� property 
     */
    public com.kingdee.eas.fdc.invite.TempletTypeInfo getParent()
    {
        return (com.kingdee.eas.fdc.invite.TempletTypeInfo)get("parent");
    }
    public void setParent(com.kingdee.eas.fdc.invite.TempletTypeInfo item)
    {
        put("parent", item);
    }
    /**
     * Object: �б����� 's ������֯ property 
     */
    public com.kingdee.eas.basedata.org.FullOrgUnitInfo getOrg()
    {
        return (com.kingdee.eas.basedata.org.FullOrgUnitInfo)get("org");
    }
    public void setOrg(com.kingdee.eas.basedata.org.FullOrgUnitInfo item)
    {
        put("org", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("3EF483FD");
    }
}