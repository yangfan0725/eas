package com.kingdee.eas.fdc.sellhouse;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractRoomAssistantTypeInfo extends com.kingdee.eas.framework.TreeBaseInfo implements Serializable 
{
    public AbstractRoomAssistantTypeInfo()
    {
        this("id");
    }
    protected AbstractRoomAssistantTypeInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: ���丨���������� 's �ϼ� property 
     */
    public com.kingdee.eas.fdc.sellhouse.RoomAssistantTypeInfo getParent()
    {
        return (com.kingdee.eas.fdc.sellhouse.RoomAssistantTypeInfo)get("parent");
    }
    public void setParent(com.kingdee.eas.fdc.sellhouse.RoomAssistantTypeInfo item)
    {
        put("parent", item);
    }
    /**
     * Object:���丨����������'s �Ƿ�Ԥ��property 
     */
    public boolean isIsPopValue()
    {
        return getBoolean("isPopValue");
    }
    public void setIsPopValue(boolean item)
    {
        setBoolean("isPopValue", item);
    }
    /**
     * Object: ���丨���������� 's ��֯ property 
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
     * Object:���丨����������'s ѡ������property 
     */
    public com.kingdee.eas.fdc.sellhouse.RoomAssistantTypeEnum getSelType()
    {
        return com.kingdee.eas.fdc.sellhouse.RoomAssistantTypeEnum.getEnum(getString("selType"));
    }
    public void setSelType(com.kingdee.eas.fdc.sellhouse.RoomAssistantTypeEnum item)
    {
		if (item != null) {
        setString("selType", item.getValue());
		}
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("380DCCA2");
    }
}