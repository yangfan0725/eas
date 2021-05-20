package com.kingdee.eas.fdc.sellhouse;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractBuildingFloorEntryInfo extends com.kingdee.eas.framework.DataBaseInfo implements Serializable 
{
    public AbstractBuildingFloorEntryInfo()
    {
        this("id");
    }
    protected AbstractBuildingFloorEntryInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object:¥��'s ��property 
     */
    public int getFloor()
    {
        return getInt("floor");
    }
    public void setFloor(int item)
    {
        setInt("floor", item);
    }
    /**
     * Object:¥��'s ¥�����property 
     */
    public String getFloorAlias()
    {
        return getString("floorAlias");
    }
    public void setFloorAlias(String item)
    {
        setString("floorAlias", item);
    }
    /**
     * Object: ¥�� 's ¥�� property 
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
     * Object:¥��'s ����property 
     */
    public boolean isIsEnable()
    {
        return getBoolean("isEnable");
    }
    public void setIsEnable(boolean item)
    {
        setBoolean("isEnable", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("4D1D3FB5");
    }
}