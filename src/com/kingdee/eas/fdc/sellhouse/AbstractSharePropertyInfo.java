package com.kingdee.eas.fdc.sellhouse;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractSharePropertyInfo extends com.kingdee.eas.framework.DataBaseInfo implements Serializable 
{
    public AbstractSharePropertyInfo()
    {
        this("id");
    }
    protected AbstractSharePropertyInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: ������ҵ���� 's ��ҵ���� property 
     */
    public com.kingdee.eas.base.permission.UserInfo getUser()
    {
        return (com.kingdee.eas.base.permission.UserInfo)get("user");
    }
    public void setUser(com.kingdee.eas.base.permission.UserInfo item)
    {
        put("user", item);
    }
    /**
     * Object: ������ҵ���� 's ͷ property 
     */
    public com.kingdee.eas.fdc.sellhouse.CluesManageInfo getProperty()
    {
        return (com.kingdee.eas.fdc.sellhouse.CluesManageInfo)get("property");
    }
    public void setProperty(com.kingdee.eas.fdc.sellhouse.CluesManageInfo item)
    {
        put("property", item);
    }
    /**
     * Object:������ҵ����'s ��������property 
     */
    public java.util.Date getShareDate()
    {
        return getDate("shareDate");
    }
    public void setShareDate(java.util.Date item)
    {
        setDate("shareDate", item);
    }
    /**
     * Object: ������ҵ���� 's ��������� property 
     */
    public com.kingdee.eas.base.permission.UserInfo getOperationPerson()
    {
        return (com.kingdee.eas.base.permission.UserInfo)get("operationPerson");
    }
    public void setOperationPerson(com.kingdee.eas.base.permission.UserInfo item)
    {
        put("operationPerson", item);
    }
    /**
     * Object: ������ҵ���� 's �ͻ� property 
     */
    public com.kingdee.eas.fdc.sellhouse.SHECustomerInfo getCustomer()
    {
        return (com.kingdee.eas.fdc.sellhouse.SHECustomerInfo)get("customer");
    }
    public void setCustomer(com.kingdee.eas.fdc.sellhouse.SHECustomerInfo item)
    {
        put("customer", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("911F17D9");
    }
}