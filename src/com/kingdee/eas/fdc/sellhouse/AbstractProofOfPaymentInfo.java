package com.kingdee.eas.fdc.sellhouse;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractProofOfPaymentInfo extends com.kingdee.eas.framework.ObjectBaseInfo implements Serializable 
{
    public AbstractProofOfPaymentInfo()
    {
        this("id");
    }
    protected AbstractProofOfPaymentInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object:����֤��'s ֤�����property 
     */
    public String getNumber()
    {
        return getString("number");
    }
    public void setNumber(String item)
    {
        setString("number", item);
    }
    /**
     * Object: ����֤�� 's �Ϲ��� property 
     */
    public com.kingdee.eas.fdc.sellhouse.PurchaseInfo getPurchaseBill()
    {
        return (com.kingdee.eas.fdc.sellhouse.PurchaseInfo)get("purchaseBill");
    }
    public void setPurchaseBill(com.kingdee.eas.fdc.sellhouse.PurchaseInfo item)
    {
        put("purchaseBill", item);
    }
    /**
     * Object:����֤��'s �ͻ�����property 
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
     * Object:����֤��'s �Ա�property 
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
     * Object:����֤��'s ���֤����property 
     */
    public String getIdentificationCard()
    {
        return getString("identificationCard");
    }
    public void setIdentificationCard(String item)
    {
        setString("identificationCard", item);
    }
    /**
     * Object:����֤��'s �ƶ��绰property 
     */
    public String getMobilPhone()
    {
        return getString("mobilPhone");
    }
    public void setMobilPhone(String item)
    {
        setString("mobilPhone", item);
    }
    /**
     * Object:����֤��'s �̶��绰property 
     */
    public String getFixedPhone()
    {
        return getString("fixedPhone");
    }
    public void setFixedPhone(String item)
    {
        setString("fixedPhone", item);
    }
    /**
     * Object:����֤��'s ��ͬ����property 
     */
    public String getContractNumber()
    {
        return getString("contractNumber");
    }
    public void setContractNumber(String item)
    {
        setString("contractNumber", item);
    }
    /**
     * Object:����֤��'s ��ͬ�ܼ�property 
     */
    public java.math.BigDecimal getContractTotalPrice()
    {
        return getBigDecimal("contractTotalPrice");
    }
    public void setContractTotalPrice(java.math.BigDecimal item)
    {
        setBigDecimal("contractTotalPrice", item);
    }
    /**
     * Object:����֤��'s ����¥��property 
     */
    public String getRoomDesc()
    {
        return getString("roomDesc");
    }
    public void setRoomDesc(String item)
    {
        setString("roomDesc", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("EC661AE6");
    }
}