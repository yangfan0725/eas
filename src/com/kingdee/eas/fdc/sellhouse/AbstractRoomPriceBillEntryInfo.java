package com.kingdee.eas.fdc.sellhouse;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractRoomPriceBillEntryInfo extends com.kingdee.eas.framework.DataBaseInfo implements Serializable 
{
    public AbstractRoomPriceBillEntryInfo()
    {
        this("id");
    }
    protected AbstractRoomPriceBillEntryInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: ���۵���¼ 's ͷ property 
     */
    public com.kingdee.eas.fdc.sellhouse.RoomPriceBillInfo getHead()
    {
        return (com.kingdee.eas.fdc.sellhouse.RoomPriceBillInfo)get("head");
    }
    public void setHead(com.kingdee.eas.fdc.sellhouse.RoomPriceBillInfo item)
    {
        put("head", item);
    }
    /**
     * Object: ���۵���¼ 's ���� property 
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
     * Object:���۵���¼'s ��������property 
     */
    public java.math.BigDecimal getBuildPrice()
    {
        return getBigDecimal("buildPrice");
    }
    public void setBuildPrice(java.math.BigDecimal item)
    {
        setBigDecimal("buildPrice", item);
    }
    /**
     * Object:���۵���¼'s ���ڵ���property 
     */
    public java.math.BigDecimal getRoomPrice()
    {
        return getBigDecimal("roomPrice");
    }
    public void setRoomPrice(java.math.BigDecimal item)
    {
        setBigDecimal("roomPrice", item);
    }
    /**
     * Object:���۵���¼'s �Ƿ����property 
     */
    public boolean isIsAdjust()
    {
        return getBoolean("isAdjust");
    }
    public void setIsAdjust(boolean item)
    {
        setBoolean("isAdjust", item);
    }
    /**
     * Object:���۵���¼'s �������property 
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
     * Object:���۵���¼'s �������property 
     */
    public java.math.BigDecimal getRoomArea()
    {
        return getBigDecimal("roomArea");
    }
    public void setRoomArea(java.math.BigDecimal item)
    {
        setBigDecimal("roomArea", item);
    }
    /**
     * Object:���۵���¼'s �Ƿ������������property 
     */
    public boolean isIsCalByRoomArea()
    {
        return getBoolean("isCalByRoomArea");
    }
    public void setIsCalByRoomArea(boolean item)
    {
        setBoolean("isCalByRoomArea", item);
    }
    /**
     * Object:���۵���¼'s ��������property 
     */
    public com.kingdee.eas.fdc.sellhouse.PriceTypeForPriceBillEnum getPriceType()
    {
        return com.kingdee.eas.fdc.sellhouse.PriceTypeForPriceBillEnum.getEnum(getInt("priceType"));
    }
    public void setPriceType(com.kingdee.eas.fdc.sellhouse.PriceTypeForPriceBillEnum item)
    {
		if (item != null) {
        setInt("priceType", item.getValue());
		}
    }
    /**
     * Object:���۵���¼'s ��׼�ܼ�property 
     */
    public java.math.BigDecimal getStandAmount()
    {
        return getBigDecimal("standAmount");
    }
    public void setStandAmount(java.math.BigDecimal item)
    {
        setBigDecimal("standAmount", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("C236E938");
    }
}