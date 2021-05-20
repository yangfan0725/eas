package com.kingdee.eas.fdc.invite.supplier;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractUserSupplierAssoInfo extends com.kingdee.eas.framework.CoreBillEntryBaseInfo implements Serializable 
{
    public AbstractUserSupplierAssoInfo()
    {
        this("id");
    }
    protected AbstractUserSupplierAssoInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: 用户供应商关联 's 所属外网用户 property 
     */
    public com.kingdee.eas.fdc.invite.supplier.WebUserInfo getParent()
    {
        return (com.kingdee.eas.fdc.invite.supplier.WebUserInfo)get("parent");
    }
    public void setParent(com.kingdee.eas.fdc.invite.supplier.WebUserInfo item)
    {
        put("parent", item);
    }
    /**
     * Object: 用户供应商关联 's 供应商 property 
     */
    public com.kingdee.eas.fdc.invite.supplier.SupplierStockInfo getSupplier()
    {
        return (com.kingdee.eas.fdc.invite.supplier.SupplierStockInfo)get("supplier");
    }
    public void setSupplier(com.kingdee.eas.fdc.invite.supplier.SupplierStockInfo item)
    {
        put("supplier", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("2EBA0F9D");
    }
}