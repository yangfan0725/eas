package com.kingdee.eas.fdc.sellhouse;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractPriceAdjustEntryInfo extends com.kingdee.eas.framework.DataBaseInfo implements Serializable 
{
    public AbstractPriceAdjustEntryInfo()
    {
        this("id");
    }
    protected AbstractPriceAdjustEntryInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: ���۷����¼ 's ����ͷ property 
     */
    public com.kingdee.eas.fdc.sellhouse.PriceAdjustInfo getHead()
    {
        return (com.kingdee.eas.fdc.sellhouse.PriceAdjustInfo)get("head");
    }
    public void setHead(com.kingdee.eas.fdc.sellhouse.PriceAdjustInfo item)
    {
        put("head", item);
    }
    /**
     * Object: ���۷����¼ 's ���� property 
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
     * Object:���۷����¼'s �ɽ������property 
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
     * Object:���۷����¼'s ԭ��������property 
     */
    public java.math.BigDecimal getOldBuildingPrice()
    {
        return getBigDecimal("oldBuildingPrice");
    }
    public void setOldBuildingPrice(java.math.BigDecimal item)
    {
        setBigDecimal("oldBuildingPrice", item);
    }
    /**
     * Object:���۷����¼'s ���������property 
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
     * Object:���۷����¼'s ԭ���ڵ���property 
     */
    public java.math.BigDecimal getOldRoomPrice()
    {
        return getBigDecimal("oldRoomPrice");
    }
    public void setOldRoomPrice(java.math.BigDecimal item)
    {
        setBigDecimal("oldRoomPrice", item);
    }
    /**
     * Object:���۷����¼'s �ɵ��Ƿ�����property 
     */
    public boolean isIsCalByRoomOld()
    {
        return getBoolean("isCalByRoomOld");
    }
    public void setIsCalByRoomOld(boolean item)
    {
        setBoolean("isCalByRoomOld", item);
    }
    /**
     * Object:���۷����¼'s �½������property 
     */
    public java.math.BigDecimal getNewBuildingArea()
    {
        return getBigDecimal("newBuildingArea");
    }
    public void setNewBuildingArea(java.math.BigDecimal item)
    {
        setBigDecimal("newBuildingArea", item);
    }
    /**
     * Object:���۷����¼'s �½�������property 
     */
    public java.math.BigDecimal getNewBuildingPrice()
    {
        return getBigDecimal("newBuildingPrice");
    }
    public void setNewBuildingPrice(java.math.BigDecimal item)
    {
        setBigDecimal("newBuildingPrice", item);
    }
    /**
     * Object:���۷����¼'s �·������property 
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
     * Object:���۷����¼'s �����ڵ���property 
     */
    public java.math.BigDecimal getNewRoomPrice()
    {
        return getBigDecimal("newRoomPrice");
    }
    public void setNewRoomPrice(java.math.BigDecimal item)
    {
        setBigDecimal("newRoomPrice", item);
    }
    /**
     * Object:���۷����¼'s �µ��Ƿ����ڼ���property 
     */
    public boolean isIsCalByRoomNew()
    {
        return getBoolean("isCalByRoomNew");
    }
    public void setIsCalByRoomNew(boolean item)
    {
        setBoolean("isCalByRoomNew", item);
    }
    /**
     * Object:���۷����¼'s �������۲��property 
     */
    public java.math.BigDecimal getBuildingPriceBalance()
    {
        return getBigDecimal("buildingPriceBalance");
    }
    public void setBuildingPriceBalance(java.math.BigDecimal item)
    {
        setBigDecimal("buildingPriceBalance", item);
    }
    /**
     * Object:���۷����¼'s �������ݲ�����property 
     */
    public java.math.BigDecimal getBuildingPriceBalanceScale()
    {
        return getBigDecimal("buildingPriceBalanceScale");
    }
    public void setBuildingPriceBalanceScale(java.math.BigDecimal item)
    {
        setBigDecimal("buildingPriceBalanceScale", item);
    }
    /**
     * Object:���۷����¼'s ���ڵ��ݲ��property 
     */
    public java.math.BigDecimal getRoomPriceBalance()
    {
        return getBigDecimal("roomPriceBalance");
    }
    public void setRoomPriceBalance(java.math.BigDecimal item)
    {
        setBigDecimal("roomPriceBalance", item);
    }
    /**
     * Object:���۷����¼'s ���ڵ��۲�����property 
     */
    public java.math.BigDecimal getRoomPriceBalanceScale()
    {
        return getBigDecimal("roomPriceBalanceScale");
    }
    public void setRoomPriceBalanceScale(java.math.BigDecimal item)
    {
        setBigDecimal("roomPriceBalanceScale", item);
    }
    /**
     * Object:���۷����¼'s �����������property 
     */
    public com.kingdee.eas.fdc.sellhouse.SellAreaTypeEnum getSalesareatype()
    {
        return com.kingdee.eas.fdc.sellhouse.SellAreaTypeEnum.getEnum(getString("Salesareatype"));
    }
    public void setSalesareatype(com.kingdee.eas.fdc.sellhouse.SellAreaTypeEnum item)
    {
		if (item != null) {
        setString("Salesareatype", item.getValue());
		}
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("9E38785A");
    }
}