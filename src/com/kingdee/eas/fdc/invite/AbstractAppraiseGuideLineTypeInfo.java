package com.kingdee.eas.fdc.invite;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractAppraiseGuideLineTypeInfo extends com.kingdee.eas.framework.TreeBaseInfo implements Serializable 
{
    public AbstractAppraiseGuideLineTypeInfo()
    {
        this("id");
    }
    protected AbstractAppraiseGuideLineTypeInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object:����ָ�����'s �Ƿ�����property 
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
     * Object: ����ָ����� 's ����� property 
     */
    public com.kingdee.eas.fdc.invite.AppraiseGuideLineTypeInfo getParent()
    {
        return (com.kingdee.eas.fdc.invite.AppraiseGuideLineTypeInfo)get("parent");
    }
    public void setParent(com.kingdee.eas.fdc.invite.AppraiseGuideLineTypeInfo item)
    {
        put("parent", item);
    }
    /**
     * Object: ����ָ����� 's ������֯ property 
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
        return new BOSObjectType("11DFF31B");
    }
}