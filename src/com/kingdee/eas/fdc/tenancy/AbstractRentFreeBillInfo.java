package com.kingdee.eas.fdc.tenancy;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractRentFreeBillInfo extends com.kingdee.eas.fdc.tenancy.TenBillBaseInfo implements Serializable 
{
    public AbstractRentFreeBillInfo()
    {
        this("id");
    }
    protected AbstractRentFreeBillInfo(String pkField)
    {
        super(pkField);
        put("entry", new com.kingdee.eas.fdc.tenancy.RentFreeEntryCollection());
    }
    /**
     * Object: ������������ 's ���� property 
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
     * Object: ������������ 's �ͻ� property 
     */
    public com.kingdee.eas.fdc.sellhouse.FDCCustomerInfo getCustomer()
    {
        return (com.kingdee.eas.fdc.sellhouse.FDCCustomerInfo)get("customer");
    }
    public void setCustomer(com.kingdee.eas.fdc.sellhouse.FDCCustomerInfo item)
    {
        put("customer", item);
    }
    /**
     * Object: ������������ 's ��¼ property 
     */
    public com.kingdee.eas.fdc.tenancy.RentFreeEntryCollection getEntry()
    {
        return (com.kingdee.eas.fdc.tenancy.RentFreeEntryCollection)get("entry");
    }
    /**
     * Object: ������������ 's ��Ŀ property 
     */
    public com.kingdee.eas.fdc.sellhouse.SellProjectInfo getSellProject()
    {
        return (com.kingdee.eas.fdc.sellhouse.SellProjectInfo)get("sellProject");
    }
    public void setSellProject(com.kingdee.eas.fdc.sellhouse.SellProjectInfo item)
    {
        put("sellProject", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("A9348B15");
    }
}