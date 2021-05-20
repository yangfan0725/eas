package com.kingdee.eas.fdc.sellhouse;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractDefaultAmountMangerInfo extends com.kingdee.eas.fdc.basedata.FDCBillInfo implements Serializable 
{
    public AbstractDefaultAmountMangerInfo()
    {
        this("id");
    }
    protected AbstractDefaultAmountMangerInfo(String pkField)
    {
        super(pkField);
        put("entry", new com.kingdee.eas.fdc.sellhouse.DefaultAmountMangerEntryCollection());
    }
    /**
     * Object:ΥԼ�����'s �ͻ�property 
     */
    public String getCustomerNames()
    {
        return getString("customerNames");
    }
    public void setCustomerNames(String item)
    {
        setString("customerNames", item);
    }
    /**
     * Object:ΥԼ�����'s ��ϵ�绰property 
     */
    public String getTelephone()
    {
        return getString("telephone");
    }
    public void setTelephone(String item)
    {
        setString("telephone", item);
    }
    /**
     * Object:ΥԼ�����'s ��ҵ����property 
     */
    public String getSaleManNames()
    {
        return getString("saleManNames");
    }
    public void setSaleManNames(String item)
    {
        setString("saleManNames", item);
    }
    /**
     * Object:ΥԼ�����'s ��ͬ�ܼ�property 
     */
    public java.math.BigDecimal getContractAmount()
    {
        return getBigDecimal("contractAmount");
    }
    public void setContractAmount(java.math.BigDecimal item)
    {
        setBigDecimal("contractAmount", item);
    }
    /**
     * Object:ΥԼ�����'s Ƿ����property 
     */
    public java.math.BigDecimal getArgAmount()
    {
        return getBigDecimal("argAmount");
    }
    public void setArgAmount(java.math.BigDecimal item)
    {
        setBigDecimal("argAmount", item);
    }
    /**
     * Object:ΥԼ�����'s �ο�ΥԼ��property 
     */
    public java.math.BigDecimal getRefDeAmount()
    {
        return getBigDecimal("refDeAmount");
    }
    public void setRefDeAmount(java.math.BigDecimal item)
    {
        setBigDecimal("refDeAmount", item);
    }
    /**
     * Object:ΥԼ�����'s ����ΥԼ��property 
     */
    public java.math.BigDecimal getSubDeAmount()
    {
        return getBigDecimal("subDeAmount");
    }
    public void setSubDeAmount(java.math.BigDecimal item)
    {
        setBigDecimal("subDeAmount", item);
    }
    /**
     * Object:ΥԼ�����'s ��תΥԼ��property 
     */
    public java.math.BigDecimal getCarryAmount()
    {
        return getBigDecimal("carryAmount");
    }
    public void setCarryAmount(java.math.BigDecimal item)
    {
        setBigDecimal("carryAmount", item);
    }
    /**
     * Object:ΥԼ�����'s ҵ������property 
     */
    public String getBusType()
    {
        return getString("busType");
    }
    public void setBusType(String item)
    {
        setString("busType", item);
    }
    /**
     * Object:ΥԼ�����'s ΥԼ���������property 
     */
    public java.util.Date getDefCalDate()
    {
        return getDate("defCalDate");
    }
    public void setDefCalDate(java.util.Date item)
    {
        setDate("defCalDate", item);
    }
    /**
     * Object:ΥԼ�����'s ��עproperty 
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
     * Object: ΥԼ����� 's ���� property 
     */
    public com.kingdee.eas.fdc.sellhouse.RoomInfo getRoom()
    {
        return (com.kingdee.eas.fdc.sellhouse.RoomInfo)get("room");
    }
    public void setRoom(com.kingdee.eas.fdc.sellhouse.RoomInfo item)
    {
        put("room", item);
    }
    /**
     * Object: ΥԼ����� 's ����� property 
     */
    public com.kingdee.eas.fdc.sellhouse.SHEPayTypeInfo getPayment()
    {
        return (com.kingdee.eas.fdc.sellhouse.SHEPayTypeInfo)get("payment");
    }
    public void setPayment(com.kingdee.eas.fdc.sellhouse.SHEPayTypeInfo item)
    {
        put("payment", item);
    }
    /**
     * Object: ΥԼ����� 's ����ͷ property 
     */
    public com.kingdee.eas.fdc.sellhouse.DefaultAmountMangerEntryCollection getEntry()
    {
        return (com.kingdee.eas.fdc.sellhouse.DefaultAmountMangerEntryCollection)get("entry");
    }
    /**
     * Object: ΥԼ����� 's ��������Id property 
     */
    public com.kingdee.eas.fdc.sellhouse.TransactionInfo getTransaction()
    {
        return (com.kingdee.eas.fdc.sellhouse.TransactionInfo)get("transaction");
    }
    public void setTransaction(com.kingdee.eas.fdc.sellhouse.TransactionInfo item)
    {
        put("transaction", item);
    }
    /**
     * Object: ΥԼ����� 's ��Ŀ���� property 
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
     * Object:ΥԼ�����'s ���µ���IDproperty 
     */
    public String getBillId()
    {
        return getString("billId");
    }
    public void setBillId(String item)
    {
        setString("billId", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("29845C58");
    }
}