package com.kingdee.eas.fdc.invite.supplier;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractSupplierLinkPeronF7Info extends com.kingdee.eas.fdc.basedata.FDCDataBaseInfo implements Serializable 
{
    public AbstractSupplierLinkPeronF7Info()
    {
        this("id");
    }
    protected AbstractSupplierLinkPeronF7Info(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: 联系人 's 档案分类 property 
     */
    public com.kingdee.eas.fdc.invite.supplier.SupplierFileTypeInfo getSupplierFileType()
    {
        return (com.kingdee.eas.fdc.invite.supplier.SupplierFileTypeInfo)get("supplierFileType");
    }
    public void setSupplierFileType(com.kingdee.eas.fdc.invite.supplier.SupplierFileTypeInfo item)
    {
        put("supplierFileType", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("0C17316F");
    }
}