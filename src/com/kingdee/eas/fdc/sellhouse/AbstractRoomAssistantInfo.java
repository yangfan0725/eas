package com.kingdee.eas.fdc.sellhouse;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractRoomAssistantInfo extends com.kingdee.eas.fdc.basedata.FDCDataBaseInfo implements Serializable 
{
    public AbstractRoomAssistantInfo()
    {
        this("id");
    }
    protected AbstractRoomAssistantInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: ���丨������ 's ���丨������ property 
     */
    public com.kingdee.eas.fdc.sellhouse.RoomAssistantTypeInfo getType()
    {
        return (com.kingdee.eas.fdc.sellhouse.RoomAssistantTypeInfo)get("type");
    }
    public void setType(com.kingdee.eas.fdc.sellhouse.RoomAssistantTypeInfo item)
    {
        put("type", item);
    }
    /**
     * Object:���丨������'s �Ƿ�Ĭ��property 
     */
    public boolean isIsDefault()
    {
        return getBoolean("isDefault");
    }
    public void setIsDefault(boolean item)
    {
        setBoolean("isDefault", item);
    }
    /**
     * Object: ���丨������ 's ������Ŀ property 
     */
    public com.kingdee.eas.fdc.sellhouse.SellProjectInfo getSellProject()
    {
        return (com.kingdee.eas.fdc.sellhouse.SellProjectInfo)get("sellProject");
    }
    public void setSellProject(com.kingdee.eas.fdc.sellhouse.SellProjectInfo item)
    {
        put("sellProject", item);
    }
    /**
     * Object: ���丨������ 's ��֯ property 
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
        return new BOSObjectType("BC8DAD48");
    }
}