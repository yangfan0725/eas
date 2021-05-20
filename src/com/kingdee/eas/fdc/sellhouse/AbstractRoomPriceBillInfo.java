package com.kingdee.eas.fdc.sellhouse;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractRoomPriceBillInfo extends com.kingdee.eas.fdc.basedata.FDCBillInfo implements Serializable 
{
    public AbstractRoomPriceBillInfo()
    {
        this("id");
    }
    protected AbstractRoomPriceBillInfo(String pkField)
    {
        super(pkField);
        put("entrys", new com.kingdee.eas.fdc.sellhouse.RoomPriceBillEntryCollection());
        put("buildingEntrys", new com.kingdee.eas.fdc.sellhouse.BuildingEntryCollection());
    }
    /**
     * Object: ���۵� 's ��¼ property 
     */
    public com.kingdee.eas.fdc.sellhouse.RoomPriceBillEntryCollection getEntrys()
    {
        return (com.kingdee.eas.fdc.sellhouse.RoomPriceBillEntryCollection)get("entrys");
    }
    /**
     * Object:���۵�'s ���۵�����property 
     */
    public com.kingdee.eas.fdc.sellhouse.PriceBillTypeEnum getType()
    {
        return com.kingdee.eas.fdc.sellhouse.PriceBillTypeEnum.getEnum(getString("type"));
    }
    public void setType(com.kingdee.eas.fdc.sellhouse.PriceBillTypeEnum item)
    {
		if (item != null) {
        setString("type", item.getValue());
		}
    }
    /**
     * Object:���۵�'s �Ƿ�ִ��property 
     */
    public boolean isIsExecuted()
    {
        return getBoolean("isExecuted");
    }
    public void setIsExecuted(boolean item)
    {
        setBoolean("isExecuted", item);
    }
    /**
     * Object:���۵�'s �Ƿ�ʧЧproperty 
     */
    public boolean isIsInvalid()
    {
        return getBoolean("isInvalid");
    }
    public void setIsInvalid(boolean item)
    {
        setBoolean("isInvalid", item);
    }
    /**
     * Object: ���۵� 's ¥����¼ property 
     */
    public com.kingdee.eas.fdc.sellhouse.BuildingEntryCollection getBuildingEntrys()
    {
        return (com.kingdee.eas.fdc.sellhouse.BuildingEntryCollection)get("buildingEntrys");
    }
    /**
     * Object:���۵�'s buildingsproperty 
     */
    public String getBuildings()
    {
        return getString("buildings");
    }
    public void setBuildings(String item)
    {
        setString("buildings", item);
    }
    /**
     * Object: ���۵� 's ������Ŀ property 
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
     * Object:���۵�'s ��������property 
     */
    public com.kingdee.eas.fdc.sellhouse.RoomPriceTypeEnum getPriceBillType()
    {
        return com.kingdee.eas.fdc.sellhouse.RoomPriceTypeEnum.getEnum(getString("priceBillType"));
    }
    public void setPriceBillType(com.kingdee.eas.fdc.sellhouse.RoomPriceTypeEnum item)
    {
		if (item != null) {
        setString("priceBillType", item.getValue());
		}
    }
    /**
     * Object:���۵�'s �Ƿ��Զ�ȡ��property 
     */
    public boolean isIsAutoToInteger()
    {
        return getBoolean("isAutoToInteger");
    }
    public void setIsAutoToInteger(boolean item)
    {
        setBoolean("isAutoToInteger", item);
    }
    /**
     * Object:���۵�'s ȡ������property 
     */
    public com.kingdee.eas.fdc.sellhouse.ToIntegerTypeEnum getToIntegerType()
    {
        return com.kingdee.eas.fdc.sellhouse.ToIntegerTypeEnum.getEnum(getString("toIntegerType"));
    }
    public void setToIntegerType(com.kingdee.eas.fdc.sellhouse.ToIntegerTypeEnum item)
    {
		if (item != null) {
        setString("toIntegerType", item.getValue());
		}
    }
    /**
     * Object:���۵�'s λ��property 
     */
    public com.kingdee.eas.fdc.sellhouse.DigitEnum getDigit()
    {
        return com.kingdee.eas.fdc.sellhouse.DigitEnum.getEnum(getString("digit"));
    }
    public void setDigit(com.kingdee.eas.fdc.sellhouse.DigitEnum item)
    {
		if (item != null) {
        setString("digit", item.getValue());
		}
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("E897B75A");
    }
}