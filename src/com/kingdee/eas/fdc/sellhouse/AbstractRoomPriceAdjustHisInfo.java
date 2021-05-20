package com.kingdee.eas.fdc.sellhouse;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractRoomPriceAdjustHisInfo extends com.kingdee.eas.framework.CoreBillBaseInfo implements Serializable 
{
    public AbstractRoomPriceAdjustHisInfo()
    {
        this("id");
    }
    protected AbstractRoomPriceAdjustHisInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object:�����Ŀ��ʷ's ���۵���Idproperty 
     */
    public com.kingdee.bos.util.BOSUuid getBillId()
    {
        return getBOSUuid("billId");
    }
    public void setBillId(com.kingdee.bos.util.BOSUuid item)
    {
        setBOSUuid("billId", item);
    }
    /**
     * Object: �����Ŀ��ʷ 's ���� property 
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
     * Object:�����Ŀ��ʷ's Ԥ�ⷿ��property 
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
     * Object:�����Ŀ��ʷ's ʵ�ⷿ��property 
     */
    public String getRoomNo()
    {
        return getString("roomNo");
    }
    public void setRoomNo(String item)
    {
        setString("roomNo", item);
    }
    /**
     * Object:�����Ŀ��ʷ's ���۷�ʽproperty 
     */
    public com.kingdee.eas.fdc.sellhouse.SellTypeEnum getSellType()
    {
        return com.kingdee.eas.fdc.sellhouse.SellTypeEnum.getEnum(getString("sellType"));
    }
    public void setSellType(com.kingdee.eas.fdc.sellhouse.SellTypeEnum item)
    {
		if (item != null) {
        setString("sellType", item.getValue());
		}
    }
    /**
     * Object:�����Ŀ��ʷ's ��׼�ܼ�property 
     */
    public java.math.BigDecimal getStandAmount()
    {
        return getBigDecimal("standAmount");
    }
    public void setStandAmount(java.math.BigDecimal item)
    {
        setBigDecimal("standAmount", item);
    }
    /**
     * Object:�����Ŀ��ʷ's �������property 
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
     * Object:�����Ŀ��ʷ's nullproperty 
     */
    public java.math.BigDecimal getBuildingPrice()
    {
        return getBigDecimal("buildingPrice");
    }
    public void setBuildingPrice(java.math.BigDecimal item)
    {
        setBigDecimal("buildingPrice", item);
    }
    /**
     * Object:�����Ŀ��ʷ's �������property 
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
     * Object:�����Ŀ��ʷ's ���ڵ���property 
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
     * Object:�����Ŀ��ʷ's ����״̬property 
     */
    public com.kingdee.eas.fdc.sellhouse.RoomSellStateEnum getSellState()
    {
        return com.kingdee.eas.fdc.sellhouse.RoomSellStateEnum.getEnum(getString("sellState"));
    }
    public void setSellState(com.kingdee.eas.fdc.sellhouse.RoomSellStateEnum item)
    {
		if (item != null) {
        setString("sellState", item.getValue());
		}
    }
    /**
     * Object:�����Ŀ��ʷ's ���״̬property 
     */
    public com.kingdee.eas.fdc.sellhouse.ChangeStateEnum getChangeState()
    {
        return com.kingdee.eas.fdc.sellhouse.ChangeStateEnum.getEnum(getString("changeState"));
    }
    public void setChangeState(com.kingdee.eas.fdc.sellhouse.ChangeStateEnum item)
    {
		if (item != null) {
        setString("changeState", item.getValue());
		}
    }
    /**
     * Object:�����Ŀ��ʷ's ��������property 
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
     * Object:�����Ŀ��ʷ's ���۷�ʽproperty 
     */
    public com.kingdee.eas.fdc.sellhouse.PriceBillTypeEnum getPriceMode()
    {
        return com.kingdee.eas.fdc.sellhouse.PriceBillTypeEnum.getEnum(getString("priceMode"));
    }
    public void setPriceMode(com.kingdee.eas.fdc.sellhouse.PriceBillTypeEnum item)
    {
		if (item != null) {
        setString("priceMode", item.getValue());
		}
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("CEA60E10");
    }
}