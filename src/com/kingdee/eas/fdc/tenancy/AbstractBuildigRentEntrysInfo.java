package com.kingdee.eas.fdc.tenancy;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractBuildigRentEntrysInfo extends com.kingdee.eas.framework.DataBaseInfo implements Serializable 
{
    public AbstractBuildigRentEntrysInfo()
    {
        this("id");
    }
    protected AbstractBuildigRentEntrysInfo(String pkField)
    {
        super(pkField);
        put("roomEntrys", new com.kingdee.eas.fdc.tenancy.BuildingRoomEntrysCollection());
    }
    /**
     * Object: ¥�������¼ 's ͷ property 
     */
    public com.kingdee.eas.fdc.tenancy.TenancyRentBillInfo getHead()
    {
        return (com.kingdee.eas.fdc.tenancy.TenancyRentBillInfo)get("head");
    }
    public void setHead(com.kingdee.eas.fdc.tenancy.TenancyRentBillInfo item)
    {
        put("head", item);
    }
    /**
     * Object: ¥�������¼ 's ¥�� property 
     */
    public com.kingdee.eas.fdc.sellhouse.BuildingInfo getBuildings()
    {
        return (com.kingdee.eas.fdc.sellhouse.BuildingInfo)get("buildings");
    }
    public void setBuildings(com.kingdee.eas.fdc.sellhouse.BuildingInfo item)
    {
        put("buildings", item);
    }
    /**
     * Object: ¥�������¼ 's ������Ϣ property 
     */
    public com.kingdee.eas.fdc.tenancy.BuildingRoomEntrysCollection getRoomEntrys()
    {
        return (com.kingdee.eas.fdc.tenancy.BuildingRoomEntrysCollection)get("roomEntrys");
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("F925217D");
    }
}