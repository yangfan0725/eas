package com.kingdee.eas.fdc.tenancy;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractTenancyRentAdjustInfo extends com.kingdee.eas.fdc.tenancy.TenBillBaseInfo implements Serializable 
{
    public AbstractTenancyRentAdjustInfo()
    {
        this("id");
    }
    protected AbstractTenancyRentAdjustInfo(String pkField)
    {
        super(pkField);
        put("entrys", new com.kingdee.eas.fdc.tenancy.RentAdjustEntrysCollection());
    }
    /**
     * Object: ���ⵥ 's �����¼ property 
     */
    public com.kingdee.eas.fdc.tenancy.RentAdjustEntrysCollection getEntrys()
    {
        return (com.kingdee.eas.fdc.tenancy.RentAdjustEntrysCollection)get("entrys");
    }
    /**
     * Object:���ⵥ's �Ƿ�ִ��property 
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
     * Object: ���ⵥ 's ������Ŀ property 
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
     * Object:���ⵥ's ִ��ʱ��property 
     */
    public java.sql.Timestamp getExecutedTime()
    {
        return getTimestamp("executedTime");
    }
    public void setExecutedTime(java.sql.Timestamp item)
    {
        setTimestamp("executedTime", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("2C5E6CBF");
    }
}