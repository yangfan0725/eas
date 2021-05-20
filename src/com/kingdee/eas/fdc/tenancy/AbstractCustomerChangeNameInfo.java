package com.kingdee.eas.fdc.tenancy;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractCustomerChangeNameInfo extends com.kingdee.eas.fdc.tenancy.TenBillBaseInfo implements Serializable 
{
    public AbstractCustomerChangeNameInfo()
    {
        this("id");
    }
    protected AbstractCustomerChangeNameInfo(String pkField)
    {
        super(pkField);
        put("newCustomer", new com.kingdee.eas.fdc.tenancy.CustomerChangeEntryCollection());
        put("oldCustomer", new com.kingdee.eas.fdc.tenancy.CustomerChangeOldEntryCollection());
    }
    /**
     * Object:�ͻ�����'s ���ԭ��property 
     */
    public String getChangeReson()
    {
        return getString("changeReson");
    }
    public void setChangeReson(String item)
    {
        setString("changeReson", item);
    }
    /**
     * Object: �ͻ����� 's ���޺�ͬ property 
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
     * Object: �ͻ����� 's �¿ͻ� property 
     */
    public com.kingdee.eas.fdc.tenancy.CustomerChangeEntryCollection getNewCustomer()
    {
        return (com.kingdee.eas.fdc.tenancy.CustomerChangeEntryCollection)get("newCustomer");
    }
    /**
     * Object: �ͻ����� 's �ɿͻ� property 
     */
    public com.kingdee.eas.fdc.tenancy.CustomerChangeOldEntryCollection getOldCustomer()
    {
        return (com.kingdee.eas.fdc.tenancy.CustomerChangeOldEntryCollection)get("oldCustomer");
    }
    /**
     * Object: �ͻ����� 's ������Ŀ property 
     */
    public com.kingdee.eas.fdc.sellhouse.SellProjectInfo getSellProject()
    {
        return (com.kingdee.eas.fdc.sellhouse.SellProjectInfo)get("sellProject");
    }
    public void setSellProject(com.kingdee.eas.fdc.sellhouse.SellProjectInfo item)
    {
        put("sellProject", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("555D4242");
    }
}