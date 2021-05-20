package com.kingdee.eas.fdc.sellhouse;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractBasePriceControlInfo extends com.kingdee.eas.fdc.basedata.FDCBillInfo implements Serializable 
{
    public AbstractBasePriceControlInfo()
    {
        this("id");
    }
    protected AbstractBasePriceControlInfo(String pkField)
    {
        super(pkField);
        put("buildingEntry", new com.kingdee.eas.fdc.sellhouse.BasePriceControlBuildingEntryCollection());
        put("roomEntry", new com.kingdee.eas.fdc.sellhouse.BasePriceRoomEntryCollection());
    }
    /**
     * Object: �׼ۿ��� 's �����¼ property 
     */
    public com.kingdee.eas.fdc.sellhouse.BasePriceRoomEntryCollection getRoomEntry()
    {
        return (com.kingdee.eas.fdc.sellhouse.BasePriceRoomEntryCollection)get("roomEntry");
    }
    /**
     * Object: �׼ۿ��� 's ������Ŀ property 
     */
    public com.kingdee.eas.fdc.sellhouse.SellProjectInfo getProject()
    {
        return (com.kingdee.eas.fdc.sellhouse.SellProjectInfo)get("project");
    }
    public void setProject(com.kingdee.eas.fdc.sellhouse.SellProjectInfo item)
    {
        put("project", item);
    }
    /**
     * Object:�׼ۿ���'s �׼�����property 
     */
    public com.kingdee.eas.fdc.sellhouse.BaseTypeEnum getBaseType()
    {
        return com.kingdee.eas.fdc.sellhouse.BaseTypeEnum.getEnum(getString("baseType"));
    }
    public void setBaseType(com.kingdee.eas.fdc.sellhouse.BaseTypeEnum item)
    {
		if (item != null) {
        setString("baseType", item.getValue());
		}
    }
    /**
     * Object: �׼ۿ��� 's ¥�� property 
     */
    public com.kingdee.eas.fdc.sellhouse.BuildingInfo getBuilding()
    {
        return (com.kingdee.eas.fdc.sellhouse.BuildingInfo)get("building");
    }
    public void setBuilding(com.kingdee.eas.fdc.sellhouse.BuildingInfo item)
    {
        put("building", item);
    }
    /**
     * Object:�׼ۿ���'s �����׼۾���property 
     */
    public java.math.BigDecimal getAvgBuildingBasePrice()
    {
        return getBigDecimal("avgBuildingBasePrice");
    }
    public void setAvgBuildingBasePrice(java.math.BigDecimal item)
    {
        setBigDecimal("avgBuildingBasePrice", item);
    }
    /**
     * Object:�׼ۿ���'s ��߽����׼۾���property 
     */
    public java.math.BigDecimal getMaxBuildingBasePrice()
    {
        return getBigDecimal("maxBuildingBasePrice");
    }
    public void setMaxBuildingBasePrice(java.math.BigDecimal item)
    {
        setBigDecimal("maxBuildingBasePrice", item);
    }
    /**
     * Object:�׼ۿ���'s ��ͽ����׼۾���property 
     */
    public java.math.BigDecimal getMinBuildingBasePrice()
    {
        return getBigDecimal("minBuildingBasePrice");
    }
    public void setMinBuildingBasePrice(java.math.BigDecimal item)
    {
        setBigDecimal("minBuildingBasePrice", item);
    }
    /**
     * Object:�׼ۿ���'s ���ڵ׼۾���property 
     */
    public java.math.BigDecimal getAvgRoomBasePrice()
    {
        return getBigDecimal("avgRoomBasePrice");
    }
    public void setAvgRoomBasePrice(java.math.BigDecimal item)
    {
        setBigDecimal("avgRoomBasePrice", item);
    }
    /**
     * Object:�׼ۿ���'s ������ڵ׼۾���property 
     */
    public java.math.BigDecimal getMaxRoomBasePrice()
    {
        return getBigDecimal("maxRoomBasePrice");
    }
    public void setMaxRoomBasePrice(java.math.BigDecimal item)
    {
        setBigDecimal("maxRoomBasePrice", item);
    }
    /**
     * Object:�׼ۿ���'s ������ڵ׼۾���property 
     */
    public java.math.BigDecimal getMinRoomBasePrice()
    {
        return getBigDecimal("minRoomBasePrice");
    }
    public void setMinRoomBasePrice(java.math.BigDecimal item)
    {
        setBigDecimal("minRoomBasePrice", item);
    }
    /**
     * Object:�׼ۿ���'s �Ƿ�ִ��property 
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
     * Object: �׼ۿ��� 's ��Ԫ property 
     */
    public com.kingdee.eas.fdc.sellhouse.BuildingUnitInfo getUnit()
    {
        return (com.kingdee.eas.fdc.sellhouse.BuildingUnitInfo)get("unit");
    }
    public void setUnit(com.kingdee.eas.fdc.sellhouse.BuildingUnitInfo item)
    {
        put("unit", item);
    }
    /**
     * Object: �׼ۿ��� 's ¥����¼ property 
     */
    public com.kingdee.eas.fdc.sellhouse.BasePriceControlBuildingEntryCollection getBuildingEntry()
    {
        return (com.kingdee.eas.fdc.sellhouse.BasePriceControlBuildingEntryCollection)get("buildingEntry");
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("5EE7F220");
    }
}