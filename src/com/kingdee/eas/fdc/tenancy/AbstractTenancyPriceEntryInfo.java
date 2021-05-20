package com.kingdee.eas.fdc.tenancy;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractTenancyPriceEntryInfo extends com.kingdee.eas.framework.CoreBillEntryBaseInfo implements Serializable 
{
    public AbstractTenancyPriceEntryInfo()
    {
        this("id");
    }
    protected AbstractTenancyPriceEntryInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: 基准租金审批分录 's 基准租金分录 property 
     */
    public com.kingdee.eas.fdc.tenancy.TenPriceEntryInfo getTenPrice()
    {
        return (com.kingdee.eas.fdc.tenancy.TenPriceEntryInfo)get("tenPrice");
    }
    public void setTenPrice(com.kingdee.eas.fdc.tenancy.TenPriceEntryInfo item)
    {
        put("tenPrice", item);
    }
    /**
     * Object: 基准租金审批分录 's 关联租赁合同 property 
     */
    public com.kingdee.eas.fdc.tenancy.TenancyBillInfo getParent()
    {
        return (com.kingdee.eas.fdc.tenancy.TenancyBillInfo)get("parent");
    }
    public void setParent(com.kingdee.eas.fdc.tenancy.TenancyBillInfo item)
    {
        put("parent", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("7C50C940");
    }
}