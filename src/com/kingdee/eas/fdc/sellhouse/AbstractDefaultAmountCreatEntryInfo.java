package com.kingdee.eas.fdc.sellhouse;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractDefaultAmountCreatEntryInfo extends com.kingdee.eas.framework.CoreBillEntryBaseInfo implements Serializable 
{
    public AbstractDefaultAmountCreatEntryInfo()
    {
        this("id");
    }
    protected AbstractDefaultAmountCreatEntryInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: ����ΥԼ���¼ 's ���� property 
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
     * Object: ����ΥԼ���¼ 's ����ͷID property 
     */
    public com.kingdee.eas.fdc.sellhouse.DefaultAmountCreatInfo getHead()
    {
        return (com.kingdee.eas.fdc.sellhouse.DefaultAmountCreatInfo)get("head");
    }
    public void setHead(com.kingdee.eas.fdc.sellhouse.DefaultAmountCreatInfo item)
    {
        put("head", item);
    }
    /**
     * Object:����ΥԼ���¼'s �ͻ�property 
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
     * Object:����ΥԼ���¼'s ��ϵ�绰property 
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
     * Object:����ΥԼ���¼'s ҵ������property 
     */
    public java.util.Date getBizDate()
    {
        return getDate("bizDate");
    }
    public void setBizDate(java.util.Date item)
    {
        setDate("bizDate", item);
    }
    /**
     * Object:����ΥԼ���¼'s ҵ����property 
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
     * Object:����ΥԼ���¼'s ��ͬ�ܼ�property 
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
     * Object:����ΥԼ���¼'s Ƿ����property 
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
     * Object:����ΥԼ���¼'s ҵ������property 
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
     * Object:����ΥԼ���¼'s ��ҵ����property 
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
     * Object:����ΥԼ���¼'s ��תΥԼ��property 
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
     * Object:����ΥԼ���¼'s �ο�ΥԼ��property 
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
     * Object:����ΥԼ���¼'s ����ΥԼ��property 
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
     * Object:����ΥԼ���¼'s ��עproperty 
     */
    public String getRemak()
    {
        return getString("remak");
    }
    public void setRemak(String item)
    {
        setString("remak", item);
    }
    /**
     * Object: ����ΥԼ���¼ 's ΥԼ������¼ property 
     */
    public com.kingdee.eas.fdc.sellhouse.DefaultAmountMangerEntryInfo getDefaultAmountMangerEntry()
    {
        return (com.kingdee.eas.fdc.sellhouse.DefaultAmountMangerEntryInfo)get("defaultAmountMangerEntry");
    }
    public void setDefaultAmountMangerEntry(com.kingdee.eas.fdc.sellhouse.DefaultAmountMangerEntryInfo item)
    {
        put("defaultAmountMangerEntry", item);
    }
    /**
     * Object: ����ΥԼ���¼ 's ���׸����¼ property 
     */
    public com.kingdee.eas.fdc.sellhouse.TranBusinessOverViewInfo getTranBusinessOverView()
    {
        return (com.kingdee.eas.fdc.sellhouse.TranBusinessOverViewInfo)get("tranBusinessOverView");
    }
    public void setTranBusinessOverView(com.kingdee.eas.fdc.sellhouse.TranBusinessOverViewInfo item)
    {
        put("tranBusinessOverView", item);
    }
    /**
     * Object: ����ΥԼ���¼ 's ǩԼ�����¼ property 
     */
    public com.kingdee.eas.fdc.sellhouse.SignPayListEntryInfo getSignPayListEntry()
    {
        return (com.kingdee.eas.fdc.sellhouse.SignPayListEntryInfo)get("signPayListEntry");
    }
    public void setSignPayListEntry(com.kingdee.eas.fdc.sellhouse.SignPayListEntryInfo item)
    {
        put("signPayListEntry", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("5D7DBF07");
    }
}