package com.kingdee.eas.fdc.tenancy;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractRestReceivableInfo extends com.kingdee.eas.framework.CoreBillBaseInfo implements Serializable 
{
    public AbstractRestReceivableInfo()
    {
        this("id");
    }
    protected AbstractRestReceivableInfo(String pkField)
    {
        super(pkField);
        put("entrys", new com.kingdee.eas.fdc.tenancy.RestReceivableEntryCollection());
        put("otherPayList", new com.kingdee.eas.fdc.tenancy.TenBillOtherPayCollection());
    }
    /**
     * Object: K����Ӧ�� 's ��¼ property 
     */
    public com.kingdee.eas.fdc.tenancy.RestReceivableEntryCollection getEntrys()
    {
        return (com.kingdee.eas.fdc.tenancy.RestReceivableEntryCollection)get("entrys");
    }
    /**
     * Object:K����Ӧ��'s �Ƿ�����ƾ֤property 
     */
    public boolean isFivouchered()
    {
        return getBoolean("Fivouchered");
    }
    public void setFivouchered(boolean item)
    {
        setBoolean("Fivouchered", item);
    }
    /**
     * Object: K����Ӧ�� 's ���޺�ͬ property 
     */
    public com.kingdee.eas.fdc.tenancy.TenancyBillInfo getTenancyBill()
    {
        return (com.kingdee.eas.fdc.tenancy.TenancyBillInfo)get("tenancyBill");
    }
    public void setTenancyBill(com.kingdee.eas.fdc.tenancy.TenancyBillInfo item)
    {
        put("tenancyBill", item);
    }
    /**
     * Object:K����Ӧ��'s ����״̬property 
     */
    public com.kingdee.eas.fdc.tenancy.TenancyBillStateEnum getBillState()
    {
        return com.kingdee.eas.fdc.tenancy.TenancyBillStateEnum.getEnum(getString("billState"));
    }
    public void setBillState(com.kingdee.eas.fdc.tenancy.TenancyBillStateEnum item)
    {
		if (item != null) {
        setString("billState", item.getValue());
		}
    }
    /**
     * Object:K����Ӧ��'s �������property 
     */
    public java.util.Date getAuditDate()
    {
        return getDate("auditDate");
    }
    public void setAuditDate(java.util.Date item)
    {
        setDate("auditDate", item);
    }
    /**
     * Object: K����Ӧ�� 's ����Ӧ����Ŀ property 
     */
    public com.kingdee.eas.fdc.tenancy.TenBillOtherPayCollection getOtherPayList()
    {
        return (com.kingdee.eas.fdc.tenancy.TenBillOtherPayCollection)get("otherPayList");
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("B42ABEF9");
    }
}