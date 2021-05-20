package com.kingdee.eas.fdc.invite.supplier;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractFDCSplChangeHistroyInfo extends com.kingdee.eas.fdc.basedata.FDCBillInfo implements Serializable 
{
    public AbstractFDCSplChangeHistroyInfo()
    {
        this("id");
    }
    protected AbstractFDCSplChangeHistroyInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: ��Ӧ����Ϣ��� 's ��Ӧ�� property 
     */
    public com.kingdee.eas.fdc.invite.supplier.SupplierStockInfo getSupplier()
    {
        return (com.kingdee.eas.fdc.invite.supplier.SupplierStockInfo)get("supplier");
    }
    public void setSupplier(com.kingdee.eas.fdc.invite.supplier.SupplierStockInfo item)
    {
        put("supplier", item);
    }
    /**
     * Object:��Ӧ����Ϣ���'s ��Ӧ��property 
     */
    public String getChangeText()
    {
        return getString("changeText");
    }
    public void setChangeText(String item)
    {
        setString("changeText", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("308F4088");
    }
}