package com.kingdee.eas.fdc.invite.supplier;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractSupplierStorageNumberInfo extends com.kingdee.eas.fdc.basedata.FDCBillInfo implements Serializable 
{
    public AbstractSupplierStorageNumberInfo()
    {
        this("id");
    }
    protected AbstractSupplierStorageNumberInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: ��Ӧ�������� 's ������֯ property 
     */
    public com.kingdee.eas.basedata.org.PurchaseOrgUnitInfo getPurchaseOrgUnit()
    {
        return (com.kingdee.eas.basedata.org.PurchaseOrgUnitInfo)get("purchaseOrgUnit");
    }
    public void setPurchaseOrgUnit(com.kingdee.eas.basedata.org.PurchaseOrgUnitInfo item)
    {
        put("purchaseOrgUnit", item);
    }
    /**
     * Object: ��Ӧ�������� 's ��Ӧ�� property 
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
     * Object: ��Ӧ�������� 's �ɹ���� property 
     */
    public com.kingdee.eas.fdc.invite.InviteTypeInfo getInviteType()
    {
        return (com.kingdee.eas.fdc.invite.InviteTypeInfo)get("inviteType");
    }
    public void setInviteType(com.kingdee.eas.fdc.invite.InviteTypeInfo item)
    {
        put("inviteType", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("40F07C00");
    }
}