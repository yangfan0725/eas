package com.kingdee.eas.fdc.sellhouse;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractPurCustomerInfo extends com.kingdee.eas.framework.CoreBillBaseInfo implements Serializable 
{
    public AbstractPurCustomerInfo()
    {
        this("id");
    }
    protected AbstractPurCustomerInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object:�Ϲ����׿ͻ���Ϣ's �Ϲ���property 
     */
    public com.kingdee.bos.util.BOSUuid getPurchase()
    {
        return getBOSUuid("purchase");
    }
    public void setPurchase(com.kingdee.bos.util.BOSUuid item)
    {
        setBOSUuid("purchase", item);
    }
    /**
     * Object:�Ϲ����׿ͻ���Ϣ's �ɿͻ���Ϣproperty 
     */
    public com.kingdee.bos.util.BOSUuid getOldPurCustomer()
    {
        return getBOSUuid("oldPurCustomer");
    }
    public void setOldPurCustomer(com.kingdee.bos.util.BOSUuid item)
    {
        setBOSUuid("oldPurCustomer", item);
    }
    /**
     * Object:�Ϲ����׿ͻ���Ϣ's ��Ȩ����property 
     */
    public java.math.BigDecimal getPropertyPercent()
    {
        return getBigDecimal("propertyPercent");
    }
    public void setPropertyPercent(java.math.BigDecimal item)
    {
        setBigDecimal("propertyPercent", item);
    }
    /**
     * Object:�Ϲ����׿ͻ���Ϣ's nullproperty 
     */
    public String getCustomerName()
    {
        return getString("customerName");
    }
    public void setCustomerName(String item)
    {
        setString("customerName", item);
    }
    /**
     * Object:�Ϲ����׿ͻ���Ϣ's property 
     */
    public com.kingdee.bos.util.BOSUuid getCustomerID()
    {
        return getBOSUuid("customerID");
    }
    public void setCustomerID(com.kingdee.bos.util.BOSUuid item)
    {
        setBOSUuid("customerID", item);
    }
    /**
     * Object:�Ϲ����׿ͻ���Ϣ's ��ϵ�绰property 
     */
    public String getTel()
    {
        return getString("tel");
    }
    public void setTel(String item)
    {
        setString("tel", item);
    }
    /**
     * Object:�Ϲ����׿ͻ���Ϣ's ֤������property 
     */
    public String getCertificateNumber()
    {
        return getString("certificateNumber");
    }
    public void setCertificateNumber(String item)
    {
        setString("certificateNumber", item);
    }
    /**
     * Object:�Ϲ����׿ͻ���Ϣ's ͨ�ŵ�ַproperty 
     */
    public String getMailAddress()
    {
        return getString("mailAddress");
    }
    public void setMailAddress(String item)
    {
        setString("mailAddress", item);
    }
    /**
     * Object:�Ϲ����׿ͻ���Ϣ's nullproperty 
     */
    public String getPostalcode()
    {
        return getString("postalcode");
    }
    public void setPostalcode(String item)
    {
        setString("postalcode", item);
    }
    /**
     * Object:�Ϲ����׿ͻ���Ϣ's �Ա�property 
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
     * Object:�Ϲ����׿ͻ���Ϣ's �Ƿ����ͻ�property 
     */
    public int getSeq()
    {
        return getInt("seq");
    }
    public void setSeq(int item)
    {
        setInt("seq", item);
    }
    /**
     * Object: �Ϲ����׿ͻ���Ϣ 's �Ϲ��� property 
     */
    public com.kingdee.eas.fdc.sellhouse.PurchaseInfo getParent()
    {
        return (com.kingdee.eas.fdc.sellhouse.PurchaseInfo)get("parent");
    }
    public void setParent(com.kingdee.eas.fdc.sellhouse.PurchaseInfo item)
    {
        put("parent", item);
    }
    /**
     * Object: �Ϲ����׿ͻ���Ϣ 's ֤������ property 
     */
    public com.kingdee.eas.fdc.sellhouse.CertificateInfo getCertificateName()
    {
        return (com.kingdee.eas.fdc.sellhouse.CertificateInfo)get("certificateName");
    }
    public void setCertificateName(com.kingdee.eas.fdc.sellhouse.CertificateInfo item)
    {
        put("certificateName", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("33B38C50");
    }
}