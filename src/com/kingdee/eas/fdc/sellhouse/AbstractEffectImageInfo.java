package com.kingdee.eas.fdc.sellhouse;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractEffectImageInfo extends com.kingdee.eas.fdc.basedata.FDCBillInfo implements Serializable 
{
    public AbstractEffectImageInfo()
    {
        this("id");
    }
    protected AbstractEffectImageInfo(String pkField)
    {
        super(pkField);
        put("elementEntry", new com.kingdee.eas.fdc.sellhouse.EffectImageElementEntryCollection());
    }
    /**
     * Object:Ч��ͼ's ����property 
     */
    public com.kingdee.eas.fdc.sellhouse.EffectImageEnum getType()
    {
        return com.kingdee.eas.fdc.sellhouse.EffectImageEnum.getEnum(getString("type"));
    }
    public void setType(com.kingdee.eas.fdc.sellhouse.EffectImageEnum item)
    {
		if (item != null) {
        setString("type", item.getValue());
		}
    }
    /**
     * Object:Ч��ͼ's �Ƿ����ֵ�Ԫproperty 
     */
    public boolean isIsShowUnit()
    {
        return getBoolean("isShowUnit");
    }
    public void setIsShowUnit(boolean item)
    {
        setBoolean("isShowUnit", item);
    }
    /**
     * Object: Ч��ͼ 's ������Ŀ property 
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
     * Object: Ч��ͼ 's ¥�� property 
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
     * Object: Ч��ͼ 's ¥�� property 
     */
    public com.kingdee.eas.fdc.sellhouse.BuildingFloorEntryInfo getBuildingFloor()
    {
        return (com.kingdee.eas.fdc.sellhouse.BuildingFloorEntryInfo)get("buildingFloor");
    }
    public void setBuildingFloor(com.kingdee.eas.fdc.sellhouse.BuildingFloorEntryInfo item)
    {
        put("buildingFloor", item);
    }
    /**
     * Object: Ч��ͼ 's ��Ԫ property 
     */
    public com.kingdee.eas.fdc.sellhouse.BuildingUnitInfo getBuildingUnit()
    {
        return (com.kingdee.eas.fdc.sellhouse.BuildingUnitInfo)get("buildingUnit");
    }
    public void setBuildingUnit(com.kingdee.eas.fdc.sellhouse.BuildingUnitInfo item)
    {
        put("buildingUnit", item);
    }
    /**
     * Object: Ч��ͼ 's Ԫ�ط�¼ property 
     */
    public com.kingdee.eas.fdc.sellhouse.EffectImageElementEntryCollection getElementEntry()
    {
        return (com.kingdee.eas.fdc.sellhouse.EffectImageElementEntryCollection)get("elementEntry");
    }
    /**
     * Object:Ч��ͼ's Ч��ͼproperty 
     */
    public byte[] getEffectImage()
    {
        return (byte[])get("effectImage");
    }
    public void setEffectImage(byte[] item)
    {
        put("effectImage", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("9D6AA48F");
    }
}