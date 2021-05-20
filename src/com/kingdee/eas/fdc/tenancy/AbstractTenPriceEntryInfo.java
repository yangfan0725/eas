package com.kingdee.eas.fdc.tenancy;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractTenPriceEntryInfo extends com.kingdee.eas.framework.CoreBillEntryBaseInfo implements Serializable 
{
    public AbstractTenPriceEntryInfo()
    {
        this("id");
    }
    protected AbstractTenPriceEntryInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object:基准租金单分录's 项目名称 property 
     */
    public String getSellProject()
    {
        return getString("sellProject");
    }
    public void setSellProject(String item)
    {
        setString("sellProject", item);
    }
    /**
     * Object:基准租金单分录's 房间名称property 
     */
    public String getRoomName()
    {
        return getString("roomName");
    }
    public void setRoomName(String item)
    {
        setString("roomName", item);
    }
    /**
     * Object:基准租金单分录's 租赁面积property 
     */
    public java.math.BigDecimal getArea()
    {
        return getBigDecimal("area");
    }
    public void setArea(java.math.BigDecimal item)
    {
        setBigDecimal("area", item);
    }
    /**
     * Object:基准租金单分录's 日单价property 
     */
    public java.math.BigDecimal getDayPrice()
    {
        return getBigDecimal("dayPrice");
    }
    public void setDayPrice(java.math.BigDecimal item)
    {
        setBigDecimal("dayPrice", item);
    }
    /**
     * Object:基准租金单分录's 月租金property 
     */
    public java.math.BigDecimal getMonthPrice()
    {
        return getBigDecimal("monthPrice");
    }
    public void setMonthPrice(java.math.BigDecimal item)
    {
        setBigDecimal("monthPrice", item);
    }
    /**
     * Object:基准租金单分录's 年租金property 
     */
    public java.math.BigDecimal getYearPrice()
    {
        return getBigDecimal("yearPrice");
    }
    public void setYearPrice(java.math.BigDecimal item)
    {
        setBigDecimal("yearPrice", item);
    }
    /**
     * Object:基准租金单分录's 合同最长免租期property 
     */
    public java.math.BigDecimal getMaxFreeDay()
    {
        return getBigDecimal("maxFreeDay");
    }
    public void setMaxFreeDay(java.math.BigDecimal item)
    {
        setBigDecimal("maxFreeDay", item);
    }
    /**
     * Object:基准租金单分录's 合同最长租期property 
     */
    public java.math.BigDecimal getMaxLease()
    {
        return getBigDecimal("maxLease");
    }
    public void setMaxLease(java.math.BigDecimal item)
    {
        setBigDecimal("maxLease", item);
    }
    /**
     * Object:基准租金单分录's 合同最低保证金property 
     */
    public java.math.BigDecimal getDeposit()
    {
        return getBigDecimal("deposit");
    }
    public void setDeposit(java.math.BigDecimal item)
    {
        setBigDecimal("deposit", item);
    }
    /**
     * Object: 基准租金单分录 's 租金审批单 property 
     */
    public com.kingdee.eas.fdc.tenancy.TenPriceBaseLineInfo getParent()
    {
        return (com.kingdee.eas.fdc.tenancy.TenPriceBaseLineInfo)get("parent");
    }
    public void setParent(com.kingdee.eas.fdc.tenancy.TenPriceBaseLineInfo item)
    {
        put("parent", item);
    }
    /**
     * Object:基准租金单分录's 是否使用property 
     */
    public int getState()
    {
        return getInt("state");
    }
    public void setState(int item)
    {
        setInt("state", item);
    }
    /**
     * Object:基准租金单分录's 备注property 
     */
    public String getDescription()
    {
        return getString("description");
    }
    public void setDescription(String item)
    {
        setString("description", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("6B47B19D");
    }
}