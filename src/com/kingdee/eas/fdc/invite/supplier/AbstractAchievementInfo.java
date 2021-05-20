package com.kingdee.eas.fdc.invite.supplier;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractAchievementInfo extends com.kingdee.eas.framework.CoreBillEntryBaseInfo implements Serializable 
{
    public AbstractAchievementInfo()
    {
        this("id");
    }
    protected AbstractAchievementInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object:ҵ��'s ��������property 
     */
    public String getProjectName()
    {
        return getString("projectName");
    }
    public void setProjectName(String item)
    {
        setString("projectName", item);
    }
    /**
     * Object:ҵ��'s �ͻ�����property 
     */
    public String getClientName()
    {
        return getString("clientName");
    }
    public void setClientName(String item)
    {
        setString("clientName", item);
    }
    /**
     * Object:ҵ��'s �ͻ���ַproperty 
     */
    public String getClientAddress()
    {
        return getString("clientAddress");
    }
    public void setClientAddress(String item)
    {
        setString("clientAddress", item);
    }
    /**
     * Object:ҵ��'s �ͻ���ϵ��property 
     */
    public String getClientLinkPerson()
    {
        return getString("clientLinkPerson");
    }
    public void setClientLinkPerson(String item)
    {
        setString("clientLinkPerson", item);
    }
    /**
     * Object:ҵ��'s �绰property 
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
     * Object:ҵ��'s ��ͬ���property 
     */
    public java.math.BigDecimal getContractPay()
    {
        return getBigDecimal("contractPay");
    }
    public void setContractPay(java.math.BigDecimal item)
    {
        setBigDecimal("contractPay", item);
    }
    /**
     * Object:ҵ��'s ��ʼ����property 
     */
    public java.util.Date getStartDate()
    {
        return getDate("startDate");
    }
    public void setStartDate(java.util.Date item)
    {
        setDate("startDate", item);
    }
    /**
     * Object:ҵ��'s ��ֹ����property 
     */
    public java.util.Date getEndDate()
    {
        return getDate("endDate");
    }
    public void setEndDate(java.util.Date item)
    {
        setDate("endDate", item);
    }
    /**
     * Object:ҵ��'s Ͷ������property 
     */
    public java.math.BigDecimal getPeopleCount()
    {
        return getBigDecimal("peopleCount");
    }
    public void setPeopleCount(java.math.BigDecimal item)
    {
        setBigDecimal("peopleCount", item);
    }
    /**
     * Object:ҵ��'s ��עproperty 
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
     * Object:ҵ��'s �Ƿ��и���property 
     */
    public boolean isIsHaveAttach()
    {
        return getBoolean("isHaveAttach");
    }
    public void setIsHaveAttach(boolean item)
    {
        setBoolean("isHaveAttach", item);
    }
    /**
     * Object: ҵ�� 's ���������� property 
     */
    public com.kingdee.eas.fdc.invite.supplier.SupplierStockInfo getParent()
    {
        return (com.kingdee.eas.fdc.invite.supplier.SupplierStockInfo)get("parent");
    }
    public void setParent(com.kingdee.eas.fdc.invite.supplier.SupplierStockInfo item)
    {
        put("parent", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("2AD45B71");
    }
}