package com.kingdee.eas.fdc.invite.supplier;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractSupplierQuaLevelEntryInfo extends com.kingdee.eas.framework.CoreBaseInfo implements Serializable 
{
    public AbstractSupplierQuaLevelEntryInfo()
    {
        this("id");
    }
    protected AbstractSupplierQuaLevelEntryInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: 资质等级分录 's 资质等级 property 
     */
    public com.kingdee.eas.fdc.invite.supplier.QuaLevelInfo getQuaLevel()
    {
        return (com.kingdee.eas.fdc.invite.supplier.QuaLevelInfo)get("quaLevel");
    }
    public void setQuaLevel(com.kingdee.eas.fdc.invite.supplier.QuaLevelInfo item)
    {
        put("quaLevel", item);
    }
    /**
     * Object: 资质等级分录 's 供应商 property 
     */
    public com.kingdee.eas.fdc.invite.supplier.SupplierStockInfo getHead()
    {
        return (com.kingdee.eas.fdc.invite.supplier.SupplierStockInfo)get("head");
    }
    public void setHead(com.kingdee.eas.fdc.invite.supplier.SupplierStockInfo item)
    {
        put("head", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("9C049447");
    }
}