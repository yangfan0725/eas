package com.kingdee.eas.fdc.tenancy;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractRoomPropertyMainInfo extends com.kingdee.eas.framework.DataBaseInfo implements Serializable 
{
    public AbstractRoomPropertyMainInfo()
    {
        this("id");
    }
    protected AbstractRoomPropertyMainInfo(String pkField)
    {
        super(pkField);
        put("roomProepertyEntry", new com.kingdee.eas.fdc.tenancy.RoomPropertyReportCollection());
    }
    /**
     * Object: ���ݲ�Ȩ���� 's ���ݲ�Ȩ��¼ property 
     */
    public com.kingdee.eas.fdc.tenancy.RoomPropertyReportCollection getRoomProepertyEntry()
    {
        return (com.kingdee.eas.fdc.tenancy.RoomPropertyReportCollection)get("roomProepertyEntry");
    }
    /**
     * Object:���ݲ�Ȩ����'s ��������property 
     */
    public com.kingdee.eas.fdc.tenancy.RoomPropertyTypeEnum getPropertyOjbectType()
    {
        return com.kingdee.eas.fdc.tenancy.RoomPropertyTypeEnum.getEnum(getString("propertyOjbectType"));
    }
    public void setPropertyOjbectType(com.kingdee.eas.fdc.tenancy.RoomPropertyTypeEnum item)
    {
		if (item != null) {
        setString("propertyOjbectType", item.getValue());
		}
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("CB1D0952");
    }
}