package com.kingdee.eas.fdc.invite.supplier;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractWinSupplierEntryInfo extends com.kingdee.eas.framework.CoreBillEntryBaseInfo implements Serializable 
{
    public AbstractWinSupplierEntryInfo()
    {
        this("id");
    }
    protected AbstractWinSupplierEntryInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: 中标供应商分录 's 中标供应商 property 
     */
    public com.kingdee.eas.fdc.invite.supplier.SupplierStockInfo getWinSupplier()
    {
        return (com.kingdee.eas.fdc.invite.supplier.SupplierStockInfo)get("winSupplier");
    }
    public void setWinSupplier(com.kingdee.eas.fdc.invite.supplier.SupplierStockInfo item)
    {
        put("winSupplier", item);
    }
    /**
     * Object: 中标供应商分录 's 所属中标信息 property 
     */
    public com.kingdee.eas.fdc.invite.supplier.WinInfoInfo getParent()
    {
        return (com.kingdee.eas.fdc.invite.supplier.WinInfoInfo)get("parent");
    }
    public void setParent(com.kingdee.eas.fdc.invite.supplier.WinInfoInfo item)
    {
        put("parent", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("52D591C2");
    }
}