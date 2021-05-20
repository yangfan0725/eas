package com.kingdee.eas.fdc.sellhouse;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractPlanisphereInfo extends com.kingdee.eas.fdc.basedata.FDCBillInfo implements Serializable 
{
    public AbstractPlanisphereInfo()
    {
        this("id");
    }
    protected AbstractPlanisphereInfo(String pkField)
    {
        super(pkField);
        put("elementEntry", new com.kingdee.eas.fdc.sellhouse.PlanisphereElementEntryCollection());
    }
    /**
     * Object:ƽ��ʾ��ͼ's ����property 
     */
    public com.kingdee.eas.fdc.sellhouse.PlanisphereTypeEnum getPtype()
    {
        return com.kingdee.eas.fdc.sellhouse.PlanisphereTypeEnum.getEnum(getString("ptype"));
    }
    public void setPtype(com.kingdee.eas.fdc.sellhouse.PlanisphereTypeEnum item)
    {
		if (item != null) {
        setString("ptype", item.getValue());
		}
    }
    /**
     * Object: ƽ��ʾ��ͼ 's ������Ŀ property 
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
     * Object: ƽ��ʾ��ͼ 's ¥�� property 
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
     * Object:ƽ��ʾ��ͼ's �Ƿ����ֵ�Ԫproperty 
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
     * Object:ƽ��ʾ��ͼ's ��Ԫproperty 
     */
    public int getUnit()
    {
        return getInt("unit");
    }
    public void setUnit(int item)
    {
        setInt("unit", item);
    }
    /**
     * Object:ƽ��ʾ��ͼ's ¥��property 
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
     * Object:ƽ��ʾ��ͼ's ������property 
     */
    public int getCellWidth()
    {
        return getInt("cellWidth");
    }
    public void setCellWidth(int item)
    {
        setInt("cellWidth", item);
    }
    /**
     * Object:ƽ��ʾ��ͼ's ����߶�property 
     */
    public int getCellHeigth()
    {
        return getInt("cellHeigth");
    }
    public void setCellHeigth(int item)
    {
        setInt("cellHeigth", item);
    }
    /**
     * Object:ƽ��ʾ��ͼ's ���������Ŀproperty 
     */
    public int getCellHorizCount()
    {
        return getInt("cellHorizCount");
    }
    public void setCellHorizCount(int item)
    {
        setInt("cellHorizCount", item);
    }
    /**
     * Object:ƽ��ʾ��ͼ's ����������Ŀproperty 
     */
    public int getCellVertiCount()
    {
        return getInt("cellVertiCount");
    }
    public void setCellVertiCount(int item)
    {
        setInt("cellVertiCount", item);
    }
    /**
     * Object: ƽ��ʾ��ͼ 's Ԫ�ط�¼ property 
     */
    public com.kingdee.eas.fdc.sellhouse.PlanisphereElementEntryCollection getElementEntry()
    {
        return (com.kingdee.eas.fdc.sellhouse.PlanisphereElementEntryCollection)get("elementEntry");
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("BC5F4C72");
    }
}