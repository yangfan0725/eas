package com.kingdee.eas.fdc.invite.supplier;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractSupplierSplAreaEntryInfo extends com.kingdee.eas.framework.CoreBaseInfo implements Serializable 
{
    public AbstractSupplierSplAreaEntryInfo()
    {
        this("id");
    }
    protected AbstractSupplierSplAreaEntryInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: ���������¼ 's �������� property 
     */
    public com.kingdee.eas.fdc.invite.supplier.FDCSplAreaInfo getFdcSplArea()
    {
        return (com.kingdee.eas.fdc.invite.supplier.FDCSplAreaInfo)get("fdcSplArea");
    }
    public void setFdcSplArea(com.kingdee.eas.fdc.invite.supplier.FDCSplAreaInfo item)
    {
        put("fdcSplArea", item);
    }
    /**
     * Object: ���������¼ 's ��Ӧ�� property 
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
        return new BOSObjectType("2FB4B91A");
    }
}