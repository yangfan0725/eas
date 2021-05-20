package com.kingdee.eas.fdc.tenancy;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractSinObligateRoomsEntryInfo extends com.kingdee.eas.framework.CoreBillBaseInfo implements Serializable 
{
    public AbstractSinObligateRoomsEntryInfo()
    {
        this("id");
    }
    protected AbstractSinObligateRoomsEntryInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: ����Ԥ�������¼ 's ����Ԥ����ͷ property 
     */
    public com.kingdee.eas.fdc.tenancy.SincerObligateInfo getHead()
    {
        return (com.kingdee.eas.fdc.tenancy.SincerObligateInfo)get("head");
    }
    public void setHead(com.kingdee.eas.fdc.tenancy.SincerObligateInfo item)
    {
        put("head", item);
    }
    /**
     * Object: ����Ԥ�������¼ 's ���� property 
     */
    public com.kingdee.eas.fdc.sellhouse.RoomInfo getRoom()
    {
        return (com.kingdee.eas.fdc.sellhouse.RoomInfo)get("room");
    }
    public void setRoom(com.kingdee.eas.fdc.sellhouse.RoomInfo item)
    {
        put("room", item);
    }
    /**
     * Object:����Ԥ�������¼'s ���䳤��property 
     */
    public String getRoomLongNum()
    {
        return getString("roomLongNum");
    }
    public void setRoomLongNum(String item)
    {
        setString("roomLongNum", item);
    }
    /**
     * Object:����Ԥ�������¼'s Լ���������property 
     */
    public com.kingdee.eas.fdc.tenancy.RentTypeEnum getPlightRentType()
    {
        return com.kingdee.eas.fdc.tenancy.RentTypeEnum.getEnum(getString("plightRentType"));
    }
    public void setPlightRentType(com.kingdee.eas.fdc.tenancy.RentTypeEnum item)
    {
		if (item != null) {
        setString("plightRentType", item.getValue());
		}
    }
    /**
     * Object:����Ԥ�������¼'s Լ�����property 
     */
    public java.math.BigDecimal getPlightRoomRent()
    {
        return getBigDecimal("plightRoomRent");
    }
    public void setPlightRoomRent(java.math.BigDecimal item)
    {
        setBigDecimal("plightRoomRent", item);
    }
    /**
     * Object:����Ԥ�������¼'s Լ����𵥼�property 
     */
    public java.math.BigDecimal getPlightRoomRentPrice()
    {
        return getBigDecimal("plightRoomRentPrice");
    }
    public void setPlightRoomRentPrice(java.math.BigDecimal item)
    {
        setBigDecimal("plightRoomRentPrice", item);
    }
    /**
     * Object:����Ԥ�������¼'s ԭ�������property 
     */
    public com.kingdee.eas.fdc.tenancy.RentTypeEnum getStandardRentType()
    {
        return com.kingdee.eas.fdc.tenancy.RentTypeEnum.getEnum(getString("standardRentType"));
    }
    public void setStandardRentType(com.kingdee.eas.fdc.tenancy.RentTypeEnum item)
    {
		if (item != null) {
        setString("standardRentType", item.getValue());
		}
    }
    /**
     * Object:����Ԥ�������¼'s ��׼�������property 
     */
    public java.math.BigDecimal getStandardRoomRent()
    {
        return getBigDecimal("standardRoomRent");
    }
    public void setStandardRoomRent(java.math.BigDecimal item)
    {
        setBigDecimal("standardRoomRent", item);
    }
    /**
     * Object:����Ԥ�������¼'s ��׼���ⵥ��property 
     */
    public java.math.BigDecimal getStandardRoomRentPrice()
    {
        return getBigDecimal("standardRoomRentPrice");
    }
    public void setStandardRoomRentPrice(java.math.BigDecimal item)
    {
        setBigDecimal("standardRoomRentPrice", item);
    }
    /**
     * Object:����Ԥ�������¼'s ��ʼ����property 
     */
    public java.util.Date getStartDate()
    {
        return getDate("startDate");
    }
    public void setStartDate(java.util.Date item)
    {
        setDate("startDate", item);
    }
    /**
     * Object:����Ԥ�������¼'s ��������property 
     */
    public java.util.Date getEndDate()
    {
        return getDate("endDate");
    }
    public void setEndDate(java.util.Date item)
    {
        setDate("endDate", item);
    }
    /**
     * Object:����Ԥ�������¼'s ʵ�ʽ�������property 
     */
    public java.util.Date getActDeliveryRoomDate()
    {
        return getDate("actDeliveryRoomDate");
    }
    public void setActDeliveryRoomDate(java.util.Date item)
    {
        setDate("actDeliveryRoomDate", item);
    }
    /**
     * Object:����Ԥ�������¼'s �������property 
     */
    public java.math.BigDecimal getTenancyArea()
    {
        return getBigDecimal("tenancyArea");
    }
    public void setTenancyArea(java.math.BigDecimal item)
    {
        setBigDecimal("tenancyArea", item);
    }
    /**
     * Object:����Ԥ�������¼'s �������property 
     */
    public java.math.BigDecimal getBuildingArea()
    {
        return getBigDecimal("buildingArea");
    }
    public void setBuildingArea(java.math.BigDecimal item)
    {
        setBigDecimal("buildingArea", item);
    }
    /**
     * Object:����Ԥ�������¼'s �����¼״̬property 
     */
    public com.kingdee.eas.fdc.tenancy.TenancyStateEnum getTenRoomState()
    {
        return com.kingdee.eas.fdc.tenancy.TenancyStateEnum.getEnum(getString("tenRoomState"));
    }
    public void setTenRoomState(com.kingdee.eas.fdc.tenancy.TenancyStateEnum item)
    {
		if (item != null) {
        setString("tenRoomState", item.getValue());
		}
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("4977CB04");
    }
}