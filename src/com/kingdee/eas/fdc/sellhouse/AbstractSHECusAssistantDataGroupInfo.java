package com.kingdee.eas.fdc.sellhouse;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractSHECusAssistantDataGroupInfo extends com.kingdee.eas.framework.TreeBaseInfo implements Serializable 
{
    public AbstractSHECusAssistantDataGroupInfo()
    {
        this("id");
    }
    protected AbstractSHECusAssistantDataGroupInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: �ͻ������������ 's ������֯ property 
     */
    public com.kingdee.eas.basedata.org.FullOrgUnitInfo getCreateOrgUnit()
    {
        return (com.kingdee.eas.basedata.org.FullOrgUnitInfo)get("createOrgUnit");
    }
    public void setCreateOrgUnit(com.kingdee.eas.basedata.org.FullOrgUnitInfo item)
    {
        put("createOrgUnit", item);
    }
    /**
     * Object:�ͻ������������'s �Ƿ�Ԥ��property 
     */
    public boolean isIsExtendField()
    {
        return getBoolean("isExtendField");
    }
    public void setIsExtendField(boolean item)
    {
        setBoolean("isExtendField", item);
    }
    /**
     * Object: �ͻ������������ 's ���ڵ� property 
     */
    public com.kingdee.eas.fdc.sellhouse.SHECusAssistantDataGroupInfo getParent()
    {
        return (com.kingdee.eas.fdc.sellhouse.SHECusAssistantDataGroupInfo)get("parent");
    }
    public void setParent(com.kingdee.eas.fdc.sellhouse.SHECusAssistantDataGroupInfo item)
    {
        put("parent", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("808912E3");
    }
}