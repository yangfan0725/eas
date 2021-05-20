package com.kingdee.eas.fdc.invite.markesupplier;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractMarketSupplierStockLinkPersonInfo extends com.kingdee.eas.framework.CoreBillEntryBaseInfo implements Serializable 
{
    public AbstractMarketSupplierStockLinkPersonInfo()
    {
        this("id");
    }
    protected AbstractMarketSupplierStockLinkPersonInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object:��ϵ��'s ����property 
     */
    public String getPersonName()
    {
        return getString("personName");
    }
    public void setPersonName(String item)
    {
        setString("personName", item);
    }
    /**
     * Object:��ϵ��'s ְ��property 
     */
    public String getPosition()
    {
        return getString("position");
    }
    public void setPosition(String item)
    {
        setString("position", item);
    }
    /**
     * Object:��ϵ��'s �ֻ�property 
     */
    public String getPhone()
    {
        return getString("phone");
    }
    public void setPhone(String item)
    {
        setString("phone", item);
    }
    /**
     * Object:��ϵ��'s �칫�绰property 
     */
    public String getWorkPhone()
    {
        return getString("workPhone");
    }
    public void setWorkPhone(String item)
    {
        setString("workPhone", item);
    }
    /**
     * Object:��ϵ��'s ����property 
     */
    public String getPersonFax()
    {
        return getString("personFax");
    }
    public void setPersonFax(String item)
    {
        setString("personFax", item);
    }
    /**
     * Object:��ϵ��'s �Ƿ�Ϊ��Ȩ��ϵ��property 
     */
    public boolean isIsDefault()
    {
        return getBoolean("isDefault");
    }
    public void setIsDefault(boolean item)
    {
        setBoolean("isDefault", item);
    }
    /**
     * Object:��ϵ��'s ����property 
     */
    public String getEmail()
    {
        return getString("email");
    }
    public void setEmail(String item)
    {
        setString("email", item);
    }
    /**
     * Object: ��ϵ�� 's ��������ͷ property 
     */
    public com.kingdee.eas.fdc.invite.markesupplier.MarketSupplierStockInfo getParent()
    {
        return (com.kingdee.eas.fdc.invite.markesupplier.MarketSupplierStockInfo)get("parent");
    }
    public void setParent(com.kingdee.eas.fdc.invite.markesupplier.MarketSupplierStockInfo item)
    {
        put("parent", item);
    }
    /**
     * Object:��ϵ��'s ��ϵ��ʽproperty 
     */
    public String getContact()
    {
        return getString("contact");
    }
    public void setContact(String item)
    {
        setString("contact", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("9BB9DD8F");
    }
}