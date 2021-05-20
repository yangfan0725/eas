package com.kingdee.eas.fdc.tenancy;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractRentAdjustEntrysInfo extends com.kingdee.eas.framework.DataBaseInfo implements Serializable 
{
    public AbstractRentAdjustEntrysInfo()
    {
        this("id");
    }
    protected AbstractRentAdjustEntrysInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: ���ⵥ��¼ 's ���ⵥͷ property 
     */
    public com.kingdee.eas.fdc.tenancy.TenancyRentAdjustInfo getHead()
    {
        return (com.kingdee.eas.fdc.tenancy.TenancyRentAdjustInfo)get("head");
    }
    public void setHead(com.kingdee.eas.fdc.tenancy.TenancyRentAdjustInfo item)
    {
        put("head", item);
    }
    /**
     * Object: ���ⵥ��¼ 's ������Ϣ property 
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
     * Object:���ⵥ��¼'s ��ǰ�������property 
     */
    public com.kingdee.eas.fdc.tenancy.RentTypeEnum getOldRentType()
    {
        return com.kingdee.eas.fdc.tenancy.RentTypeEnum.getEnum(getString("oldRentType"));
    }
    public void setOldRentType(com.kingdee.eas.fdc.tenancy.RentTypeEnum item)
    {
		if (item != null) {
        setString("oldRentType", item.getValue());
		}
    }
    /**
     * Object:���ⵥ��¼'s ��ǰ�����㷽ʽproperty 
     */
    public com.kingdee.eas.fdc.tenancy.TenancyModeEnum getOldTenancyModel()
    {
        return com.kingdee.eas.fdc.tenancy.TenancyModeEnum.getEnum(getString("oldTenancyModel"));
    }
    public void setOldTenancyModel(com.kingdee.eas.fdc.tenancy.TenancyModeEnum item)
    {
		if (item != null) {
        setString("oldTenancyModel", item.getValue());
		}
    }
    /**
     * Object:���ⵥ��¼'s ��ǰ����״̬property 
     */
    public com.kingdee.eas.fdc.tenancy.TenancyStateEnum getOldTenancyState()
    {
        return com.kingdee.eas.fdc.tenancy.TenancyStateEnum.getEnum(getString("oldTenancyState"));
    }
    public void setOldTenancyState(com.kingdee.eas.fdc.tenancy.TenancyStateEnum item)
    {
		if (item != null) {
        setString("oldTenancyState", item.getValue());
		}
    }
    /**
     * Object:���ⵥ��¼'s ��ǰ��׼���property 
     */
    public java.math.BigDecimal getOldStandardRent()
    {
        return getBigDecimal("oldStandardRent");
    }
    public void setOldStandardRent(java.math.BigDecimal item)
    {
        setBigDecimal("oldStandardRent", item);
    }
    /**
     * Object:���ⵥ��¼'s ��ǰ�������property 
     */
    public java.math.BigDecimal getOldTenancyArea()
    {
        return getBigDecimal("oldTenancyArea");
    }
    public void setOldTenancyArea(java.math.BigDecimal item)
    {
        setBigDecimal("oldTenancyArea", item);
    }
    /**
     * Object:���ⵥ��¼'s ��ǰ��𵥼�property 
     */
    public java.math.BigDecimal getOldTenancyRentPrice()
    {
        return getBigDecimal("oldTenancyRentPrice");
    }
    public void setOldTenancyRentPrice(java.math.BigDecimal item)
    {
        setBigDecimal("oldTenancyRentPrice", item);
    }
    /**
     * Object:���ⵥ��¼'s ��ǰ�������property 
     */
    public java.math.BigDecimal getOldBuildingArea()
    {
        return getBigDecimal("oldBuildingArea");
    }
    public void setOldBuildingArea(java.math.BigDecimal item)
    {
        setBigDecimal("oldBuildingArea", item);
    }
    /**
     * Object:���ⵥ��¼'s ��ǰ������𵥼ۣ�ԭ��𵥼ۣ�property 
     */
    public java.math.BigDecimal getOldRentPrice()
    {
        return getBigDecimal("oldRentPrice");
    }
    public void setOldRentPrice(java.math.BigDecimal item)
    {
        setBigDecimal("oldRentPrice", item);
    }
    /**
     * Object:���ⵥ��¼'s ��ǰ���������ԭ���������property 
     */
    public java.math.BigDecimal getOldRoomArea()
    {
        return getBigDecimal("oldRoomArea");
    }
    public void setOldRoomArea(java.math.BigDecimal item)
    {
        setBigDecimal("oldRoomArea", item);
    }
    /**
     * Object:���ⵥ��¼'s ��ǰ������𵥼�property 
     */
    public java.math.BigDecimal getOldRoomRentPrice()
    {
        return getBigDecimal("oldRoomRentPrice");
    }
    public void setOldRoomRentPrice(java.math.BigDecimal item)
    {
        setBigDecimal("oldRoomRentPrice", item);
    }
    /**
     * Object:���ⵥ��¼'s �����׼���property 
     */
    public java.math.BigDecimal getNewStandardRent()
    {
        return getBigDecimal("newStandardRent");
    }
    public void setNewStandardRent(java.math.BigDecimal item)
    {
        setBigDecimal("newStandardRent", item);
    }
    /**
     * Object:���ⵥ��¼'s �����������property 
     */
    public com.kingdee.eas.fdc.tenancy.RentTypeEnum getNewRentType()
    {
        return com.kingdee.eas.fdc.tenancy.RentTypeEnum.getEnum(getString("newRentType"));
    }
    public void setNewRentType(com.kingdee.eas.fdc.tenancy.RentTypeEnum item)
    {
		if (item != null) {
        setString("newRentType", item.getValue());
		}
    }
    /**
     * Object:���ⵥ��¼'s ���������㷽ʽproperty 
     */
    public com.kingdee.eas.fdc.tenancy.TenancyModeEnum getNewTenancyModel()
    {
        return com.kingdee.eas.fdc.tenancy.TenancyModeEnum.getEnum(getString("newTenancyModel"));
    }
    public void setNewTenancyModel(com.kingdee.eas.fdc.tenancy.TenancyModeEnum item)
    {
		if (item != null) {
        setString("newTenancyModel", item.getValue());
		}
    }
    /**
     * Object:���ⵥ��¼'s ��������״̬property 
     */
    public com.kingdee.eas.fdc.tenancy.TenancyStateEnum getNewTenancyState()
    {
        return com.kingdee.eas.fdc.tenancy.TenancyStateEnum.getEnum(getString("newTenancyState"));
    }
    public void setNewTenancyState(com.kingdee.eas.fdc.tenancy.TenancyStateEnum item)
    {
		if (item != null) {
        setString("newTenancyState", item.getValue());
		}
    }
    /**
     * Object:���ⵥ��¼'s ������𵥼�property 
     */
    public java.math.BigDecimal getNewTenancyRentPrice()
    {
        return getBigDecimal("newTenancyRentPrice");
    }
    public void setNewTenancyRentPrice(java.math.BigDecimal item)
    {
        setBigDecimal("newTenancyRentPrice", item);
    }
    /**
     * Object:���ⵥ��¼'s ��������𵥼�property 
     */
    public java.math.BigDecimal getNewRentPrice()
    {
        return getBigDecimal("newRentPrice");
    }
    public void setNewRentPrice(java.math.BigDecimal item)
    {
        setBigDecimal("newRentPrice", item);
    }
    /**
     * Object:���ⵥ��¼'s ����������𵥼�property 
     */
    public java.math.BigDecimal getNewRoomRentPrice()
    {
        return getBigDecimal("newRoomRentPrice");
    }
    public void setNewRoomRentPrice(java.math.BigDecimal item)
    {
        setBigDecimal("newRoomRentPrice", item);
    }
    /**
     * Object:���ⵥ��¼'s ���󷿼�property 
     */
    public java.math.BigDecimal getNewRoomArea()
    {
        return getBigDecimal("newRoomArea");
    }
    public void setNewRoomArea(java.math.BigDecimal item)
    {
        setBigDecimal("newRoomArea", item);
    }
    /**
     * Object:���ⵥ��¼'s ���䳤����property 
     */
    public String getLongNumber()
    {
        return getString("longNumber");
    }
    public void setLongNumber(String item)
    {
        setString("longNumber", item);
    }
    /**
     * Object:���ⵥ��¼'s �������property 
     */
    public String getRoomNumber()
    {
        return getString("roomNumber");
    }
    public void setRoomNumber(String item)
    {
        setString("roomNumber", item);
    }
    /**
     * Object:���ⵥ��¼'s ԭ�������property 
     */
    public java.math.BigDecimal getOldMaxFreeDay()
    {
        return getBigDecimal("oldMaxFreeDay");
    }
    public void setOldMaxFreeDay(java.math.BigDecimal item)
    {
        setBigDecimal("oldMaxFreeDay", item);
    }
    /**
     * Object:���ⵥ��¼'s �����������property 
     */
    public java.math.BigDecimal getNewMaxFreeDay()
    {
        return getBigDecimal("newMaxFreeDay");
    }
    public void setNewMaxFreeDay(java.math.BigDecimal item)
    {
        setBigDecimal("newMaxFreeDay", item);
    }
    /**
     * Object:���ⵥ��¼'s ԭ�����property 
     */
    public java.math.BigDecimal getOldMaxLease()
    {
        return getBigDecimal("oldMaxLease");
    }
    public void setOldMaxLease(java.math.BigDecimal item)
    {
        setBigDecimal("oldMaxLease", item);
    }
    /**
     * Object:���ⵥ��¼'s �������property 
     */
    public java.math.BigDecimal getNewMaxLease()
    {
        return getBigDecimal("newMaxLease");
    }
    public void setNewMaxLease(java.math.BigDecimal item)
    {
        setBigDecimal("newMaxLease", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("3B9EE432");
    }
}