package com.kingdee.eas.fdc.invite;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractExpertInfo extends com.kingdee.eas.framework.DataBaseInfo implements Serializable 
{
    public AbstractExpertInfo()
    {
        this("id");
    }
    protected AbstractExpertInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: ר�� 's ��½�û� property 
     */
    public com.kingdee.eas.base.permission.UserInfo getUser()
    {
        return (com.kingdee.eas.base.permission.UserInfo)get("user");
    }
    public void setUser(com.kingdee.eas.base.permission.UserInfo item)
    {
        put("user", item);
    }
    /**
     * Object:ר��'s �Ƿ񶳽�property 
     */
    public boolean isIsEnable()
    {
        return getBoolean("isEnable");
    }
    public void setIsEnable(boolean item)
    {
        setBoolean("isEnable", item);
    }
    /**
     * Object: ר�� 's ר����� property 
     */
    public com.kingdee.eas.fdc.invite.ExpertTypeInfo getExpertType()
    {
        return (com.kingdee.eas.fdc.invite.ExpertTypeInfo)get("expertType");
    }
    public void setExpertType(com.kingdee.eas.fdc.invite.ExpertTypeInfo item)
    {
        put("expertType", item);
    }
    /**
     * Object:ר��'s �绰����property 
     */
    public String getTel()
    {
        return getString("tel");
    }
    public void setTel(String item)
    {
        setString("tel", item);
    }
    /**
     * Object: ר�� 's ��֯ property 
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
        return new BOSObjectType("E1E8864E");
    }
}