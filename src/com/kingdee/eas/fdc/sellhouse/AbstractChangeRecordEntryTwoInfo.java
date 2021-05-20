package com.kingdee.eas.fdc.sellhouse;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractChangeRecordEntryTwoInfo extends com.kingdee.eas.framework.DataBaseInfo implements Serializable 
{
    public AbstractChangeRecordEntryTwoInfo()
    {
        this("id");
    }
    protected AbstractChangeRecordEntryTwoInfo(String pkField)
    {
        super(pkField);
        put("newCustomer", new com.kingdee.eas.fdc.sellhouse.ChangeNameNewCustomerEntryCollection());
        put("oldCustomer", new com.kingdee.eas.fdc.sellhouse.ChangeNameOldCustomerEntryCollection());
    }
    /**
     * Object: ������¼��¼ 's �����Ϲ��� property 
     */
    public com.kingdee.eas.fdc.sellhouse.SincerityPurchaseInfo getHead()
    {
        return (com.kingdee.eas.fdc.sellhouse.SincerityPurchaseInfo)get("head");
    }
    public void setHead(com.kingdee.eas.fdc.sellhouse.SincerityPurchaseInfo item)
    {
        put("head", item);
    }
    /**
     * Object:������¼��¼'s ��������property 
     */
    public java.util.Date getChangeDate()
    {
        return getDate("changeDate");
    }
    public void setChangeDate(java.util.Date item)
    {
        setDate("changeDate", item);
    }
    /**
     * Object: ������¼��¼ 's ԭ�ͻ� property 
     */
    public com.kingdee.eas.fdc.sellhouse.ChangeNameOldCustomerEntryCollection getOldCustomer()
    {
        return (com.kingdee.eas.fdc.sellhouse.ChangeNameOldCustomerEntryCollection)get("oldCustomer");
    }
    /**
     * Object: ������¼��¼ 's �¿ͻ� property 
     */
    public com.kingdee.eas.fdc.sellhouse.ChangeNameNewCustomerEntryCollection getNewCustomer()
    {
        return (com.kingdee.eas.fdc.sellhouse.ChangeNameNewCustomerEntryCollection)get("newCustomer");
    }
    /**
     * Object:������¼��¼'s ��עproperty 
     */
    public String getRemark()
    {
        return getString("remark");
    }
    public void setRemark(String item)
    {
        setString("remark", item);
    }
    /**
     * Object: ������¼��¼ 's ������ property 
     */
    public com.kingdee.eas.base.permission.UserInfo getOperator()
    {
        return (com.kingdee.eas.base.permission.UserInfo)get("operator");
    }
    public void setOperator(com.kingdee.eas.base.permission.UserInfo item)
    {
        put("operator", item);
    }
    /**
     * Object:������¼��¼'s ԤԼ�绰property 
     */
    public String getAppointmentPhone()
    {
        return getString("appointmentPhone");
    }
    public void setAppointmentPhone(String item)
    {
        setString("appointmentPhone", item);
    }
    /**
     * Object:������¼��¼'s ԤԼ����property 
     */
    public String getAppointmentPeople()
    {
        return getString("appointmentPeople");
    }
    public void setAppointmentPeople(String item)
    {
        setString("appointmentPeople", item);
    }
    /**
     * Object: ������¼��¼ 's �����ͻ� property 
     */
    public com.kingdee.eas.fdc.sellhouse.CluesManageInfo getCluesCus()
    {
        return (com.kingdee.eas.fdc.sellhouse.CluesManageInfo)get("cluesCus");
    }
    public void setCluesCus(com.kingdee.eas.fdc.sellhouse.CluesManageInfo item)
    {
        put("cluesCus", item);
    }
    /**
     * Object:������¼��¼'s �Ͽͻ�property 
     */
    public String getOldCusStr()
    {
        return getString("oldCusStr");
    }
    public void setOldCusStr(String item)
    {
        setString("oldCusStr", item);
    }
    /**
     * Object:������¼��¼'s �¿ͻ�property 
     */
    public String getNewCusStr()
    {
        return getString("newCusStr");
    }
    public void setNewCusStr(String item)
    {
        setString("newCusStr", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("75B31B16");
    }
}