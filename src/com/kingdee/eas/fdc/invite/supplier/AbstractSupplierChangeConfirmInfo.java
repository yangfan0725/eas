package com.kingdee.eas.fdc.invite.supplier;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractSupplierChangeConfirmInfo extends com.kingdee.eas.fdc.basedata.FDCBillInfo implements Serializable 
{
    public AbstractSupplierChangeConfirmInfo()
    {
        this("id");
    }
    protected AbstractSupplierChangeConfirmInfo(String pkField)
    {
        super(pkField);
        put("entry", new com.kingdee.eas.fdc.invite.supplier.SupplierChangeEntryCollection());
    }
    /**
     * Object: 供应商变更审核 's 分录  property 
     */
    public com.kingdee.eas.fdc.invite.supplier.SupplierChangeEntryCollection getEntry()
    {
        return (com.kingdee.eas.fdc.invite.supplier.SupplierChangeEntryCollection)get("entry");
    }
    /**
     * Object: 供应商变更审核 's 关联供应商 property 
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
        return new BOSObjectType("E1F35F4C");
    }
}