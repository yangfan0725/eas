package com.kingdee.eas.fdc.tenancy;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractTenancyRentBillInfo extends com.kingdee.eas.fdc.tenancy.TenBillBaseInfo implements Serializable 
{
    public AbstractTenancyRentBillInfo()
    {
        this("id");
    }
    protected AbstractTenancyRentBillInfo(String pkField)
    {
        super(pkField);
        put("buildingEntrys", new com.kingdee.eas.fdc.tenancy.BuildigRentEntrysCollection());
        put("roomEntrys", new com.kingdee.eas.fdc.tenancy.BuildingRoomEntrysCollection());
    }
    /**
     * Object: K���ⵥ 's ����¥����¼ property 
     */
    public com.kingdee.eas.fdc.tenancy.BuildigRentEntrysCollection getBuildingEntrys()
    {
        return (com.kingdee.eas.fdc.tenancy.BuildigRentEntrysCollection)get("buildingEntrys");
    }
    /**
     * Object:K���ⵥ's �Ƿ�ִ��property 
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
     * Object:K���ⵥ's �Ƿ�ʧЧproperty 
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
     * Object:K���ⵥ's ¥������property 
     */
    public String getBuildingNames()
    {
        return getString("buildingNames");
    }
    public void setBuildingNames(String item)
    {
        setString("buildingNames", item);
    }
    /**
     * Object: K���ⵥ 's ������Ŀ property 
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
     * Object:K���ⵥ's ִ��ʱ��property 
     */
    public java.sql.Timestamp getExecutedTime()
    {
        return getTimestamp("executedTime");
    }
    public void setExecutedTime(java.sql.Timestamp item)
    {
        setTimestamp("executedTime", item);
    }
    /**
     * Object: K���ⵥ 's �����¼ property 
     */
    public com.kingdee.eas.fdc.tenancy.BuildingRoomEntrysCollection getRoomEntrys()
    {
        return (com.kingdee.eas.fdc.tenancy.BuildingRoomEntrysCollection)get("roomEntrys");
    }
    /**
     * Object:K���ⵥ's ��������property 
     */
    public com.kingdee.eas.fdc.sellhouse.TenancyPriceTypeEnum getPriceBillType()
    {
        return com.kingdee.eas.fdc.sellhouse.TenancyPriceTypeEnum.getEnum(getString("priceBillType"));
    }
    public void setPriceBillType(com.kingdee.eas.fdc.sellhouse.TenancyPriceTypeEnum item)
    {
		if (item != null) {
        setString("priceBillType", item.getValue());
		}
    }
    /**
     * Object: K���ⵥ 's ������ property 
     */
    public com.kingdee.eas.fdc.tenancy.TenRentBillDaysOfMonthInfo getDaysOfMonth()
    {
        return (com.kingdee.eas.fdc.tenancy.TenRentBillDaysOfMonthInfo)get("daysOfMonth");
    }
    public void setDaysOfMonth(com.kingdee.eas.fdc.tenancy.TenRentBillDaysOfMonthInfo item)
    {
        put("daysOfMonth", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("D27EE537");
    }
}