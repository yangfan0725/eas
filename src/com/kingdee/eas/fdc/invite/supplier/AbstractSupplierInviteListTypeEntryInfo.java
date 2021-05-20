package com.kingdee.eas.fdc.invite.supplier;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractSupplierInviteListTypeEntryInfo extends com.kingdee.eas.framework.CoreBaseInfo implements Serializable 
{
    public AbstractSupplierInviteListTypeEntryInfo()
    {
        this("id");
    }
    protected AbstractSupplierInviteListTypeEntryInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: �ɹ������ϸ��¼ 's �ɹ������ϸ property 
     */
    public com.kingdee.eas.fdc.invite.supplier.InviteListTypeInfo getInviteListType()
    {
        return (com.kingdee.eas.fdc.invite.supplier.InviteListTypeInfo)get("inviteListType");
    }
    public void setInviteListType(com.kingdee.eas.fdc.invite.supplier.InviteListTypeInfo item)
    {
        put("inviteListType", item);
    }
    /**
     * Object: �ɹ������ϸ��¼ 's ��Ӧ�� property 
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
        return new BOSObjectType("2D31FB2D");
    }
}