package com.kingdee.eas.fdc.sellhouse;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractSHEFunctionChooseRoomSetInfo extends com.kingdee.eas.framework.CoreBaseInfo implements Serializable 
{
    public AbstractSHEFunctionChooseRoomSetInfo()
    {
        this("id");
    }
    protected AbstractSHEFunctionChooseRoomSetInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: ��¥����-ѡ������ 's ͷ property 
     */
    public com.kingdee.eas.fdc.sellhouse.SHEFunctionSetInfo getHead()
    {
        return (com.kingdee.eas.fdc.sellhouse.SHEFunctionSetInfo)get("head");
    }
    public void setHead(com.kingdee.eas.fdc.sellhouse.SHEFunctionSetInfo item)
    {
        put("head", item);
    }
    /**
     * Object: ��¥����-ѡ������ 's ��Ŀ property 
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
     * Object: ��¥����-ѡ������ 's ¥�� property 
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
     * Object:��¥����-ѡ������'s ��ʼ����property 
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
     * Object:��¥����-ѡ������'s ��������property 
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
     * Object:��¥����-ѡ������'s �Ƿ�����property 
     */
    public boolean isIsEnabled()
    {
        return getBoolean("isEnabled");
    }
    public void setIsEnabled(boolean item)
    {
        setBoolean("isEnabled", item);
    }
    /**
     * Object:��¥����-ѡ������'s Ĭ��ʱЧproperty 
     */
    public int getDefaultLimitDate()
    {
        return getInt("defaultLimitDate");
    }
    public void setDefaultLimitDate(int item)
    {
        setInt("defaultLimitDate", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("39F9A183");
    }
}