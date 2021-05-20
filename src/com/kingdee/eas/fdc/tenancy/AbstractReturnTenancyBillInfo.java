package com.kingdee.eas.fdc.tenancy;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractReturnTenancyBillInfo extends com.kingdee.eas.fdc.basedata.FDCBillInfo implements Serializable 
{
    public AbstractReturnTenancyBillInfo()
    {
        this("id");
    }
    protected AbstractReturnTenancyBillInfo(String pkField)
    {
        super(pkField);
        put("tenancyContractEntry", new com.kingdee.eas.fdc.tenancy.ReturnTenancyContractCollection());
        put("entry", new com.kingdee.eas.fdc.tenancy.ReturnTenancyEntryCollection());
    }
    /**
     * Object: ���ⵥ 's ���� property 
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
     * Object:���ⵥ's ��������property 
     */
    public java.math.BigDecimal getReturnYear()
    {
        return getBigDecimal("returnYear");
    }
    public void setReturnYear(java.math.BigDecimal item)
    {
        setBigDecimal("returnYear", item);
    }
    /**
     * Object:���ⵥ's ��ʼ����property 
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
     * Object:���ⵥ's ��������property 
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
     * Object: ���ⵥ 's ���޺�ͬ property 
     */
    public com.kingdee.eas.fdc.tenancy.ReturnTenancyContractCollection getTenancyContractEntry()
    {
        return (com.kingdee.eas.fdc.tenancy.ReturnTenancyContractCollection)get("tenancyContractEntry");
    }
    /**
     * Object: ���ⵥ 's ���ⵥ��¼ property 
     */
    public com.kingdee.eas.fdc.tenancy.ReturnTenancyEntryCollection getEntry()
    {
        return (com.kingdee.eas.fdc.tenancy.ReturnTenancyEntryCollection)get("entry");
    }
    /**
     * Object: ���ⵥ 's ��Ŀ property 
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
     * Object:���ⵥ's �ر�������property 
     */
    public String getStrOfReturnRate()
    {
        return getString("strOfReturnRate");
    }
    public void setStrOfReturnRate(String item)
    {
        setString("strOfReturnRate", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("E444454E");
    }
}