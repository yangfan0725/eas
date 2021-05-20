package com.kingdee.eas.fdc.tenancy;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractIntentionCustomerInfo extends com.kingdee.eas.fdc.basedata.FDCBillInfo implements Serializable 
{
    public AbstractIntentionCustomerInfo()
    {
        this("id");
    }
    protected AbstractIntentionCustomerInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object:����ͻ�'s �ֻ�/��ϵ�绰property 
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
     * Object:����ͻ�'s �Ա�property 
     */
    public com.kingdee.eas.fdc.sellhouse.SexEnum getSex()
    {
        return com.kingdee.eas.fdc.sellhouse.SexEnum.getEnum(getString("sex"));
    }
    public void setSex(com.kingdee.eas.fdc.sellhouse.SexEnum item)
    {
		if (item != null) {
        setString("sex", item.getValue());
		}
    }
    /**
     * Object:����ͻ�'s �Ѹ�Ӷ��property 
     */
    public java.math.BigDecimal getPayedAmount()
    {
        return getBigDecimal("payedAmount");
    }
    public void setPayedAmount(java.math.BigDecimal item)
    {
        setBigDecimal("payedAmount", item);
    }
    /**
     * Object:����ͻ�'s ��Ϣ��property 
     */
    public java.math.BigDecimal getInfoAmount()
    {
        return getBigDecimal("infoAmount");
    }
    public void setInfoAmount(java.math.BigDecimal item)
    {
        setBigDecimal("infoAmount", item);
    }
    /**
     * Object:����ͻ�'s �Ѹ���Ϣ��property 
     */
    public java.math.BigDecimal getPayedInfoAmount()
    {
        return getBigDecimal("payedInfoAmount");
    }
    public void setPayedInfoAmount(java.math.BigDecimal item)
    {
        setBigDecimal("payedInfoAmount", item);
    }
    /**
     * Object: ����ͻ� 's ����¥��/��ҵ԰�� property 
     */
    public com.kingdee.eas.fdc.sellhouse.SellProjectInfo getProject()
    {
        return (com.kingdee.eas.fdc.sellhouse.SellProjectInfo)get("project");
    }
    public void setProject(com.kingdee.eas.fdc.sellhouse.SellProjectInfo item)
    {
        put("project", item);
    }
    /**
     * Object:����ͻ�'s ����property 
     */
    public String getCity()
    {
        return getString("city");
    }
    public void setCity(String item)
    {
        setString("city", item);
    }
    /**
     * Object: ����ͻ� 's ������ property 
     */
    public com.kingdee.eas.fdc.tenancy.BrokerInfo getBroker()
    {
        return (com.kingdee.eas.fdc.tenancy.BrokerInfo)get("broker");
    }
    public void setBroker(com.kingdee.eas.fdc.tenancy.BrokerInfo item)
    {
        put("broker", item);
    }
    /**
     * Object:����ͻ�'s ��֧��property 
     */
    public boolean isIsPayed()
    {
        return getBoolean("isPayed");
    }
    public void setIsPayed(boolean item)
    {
        setBoolean("isPayed", item);
    }
    /**
     * Object:����ͻ�'s ��ϵ��property 
     */
    public String getContactName()
    {
        return getString("contactName");
    }
    public void setContactName(String item)
    {
        setString("contactName", item);
    }
    /**
     * Object: ����ͻ� 's ��ҵ���� property 
     */
    public com.kingdee.eas.base.permission.UserInfo getSaleMan()
    {
        return (com.kingdee.eas.base.permission.UserInfo)get("saleMan");
    }
    public void setSaleMan(com.kingdee.eas.base.permission.UserInfo item)
    {
        put("saleMan", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("75527961");
    }
}