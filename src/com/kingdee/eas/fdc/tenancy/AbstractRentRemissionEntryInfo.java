package com.kingdee.eas.fdc.tenancy;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractRentRemissionEntryInfo extends com.kingdee.eas.framework.CoreBillEntryBaseInfo implements Serializable 
{
    public AbstractRentRemissionEntryInfo()
    {
        this("id");
    }
    protected AbstractRentRemissionEntryInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: �������¼ 's ������𷿼� property 
     */
    public com.kingdee.eas.fdc.tenancy.TenancyRoomPayListEntryInfo getRentRemissionRoom()
    {
        return (com.kingdee.eas.fdc.tenancy.TenancyRoomPayListEntryInfo)get("rentRemissionRoom");
    }
    public void setRentRemissionRoom(com.kingdee.eas.fdc.tenancy.TenancyRoomPayListEntryInfo item)
    {
        put("rentRemissionRoom", item);
    }
    /**
     * Object: �������¼ 's �������� property 
     */
    public com.kingdee.eas.fdc.sellhouse.MoneyDefineInfo getMoneyDefine()
    {
        return (com.kingdee.eas.fdc.sellhouse.MoneyDefineInfo)get("moneyDefine");
    }
    public void setMoneyDefine(com.kingdee.eas.fdc.sellhouse.MoneyDefineInfo item)
    {
        put("moneyDefine", item);
    }
    /**
     * Object:�������¼'s �������property 
     */
    public int getLeaseSeq()
    {
        return getInt("leaseSeq");
    }
    public void setLeaseSeq(int item)
    {
        setInt("leaseSeq", item);
    }
    /**
     * Object:�������¼'s ��ʼ����property 
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
     * Object:�������¼'s ��������property 
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
     * Object:�������¼'s ԭӦ�����property 
     */
    public java.math.BigDecimal getAppAmount()
    {
        return getBigDecimal("appAmount");
    }
    public void setAppAmount(java.math.BigDecimal item)
    {
        setBigDecimal("appAmount", item);
    }
    /**
     * Object:�������¼'s ������property 
     */
    public java.math.BigDecimal getRemisionAmount()
    {
        return getBigDecimal("remisionAmount");
    }
    public void setRemisionAmount(java.math.BigDecimal item)
    {
        setBigDecimal("remisionAmount", item);
    }
    /**
     * Object: �������¼ 's ������� property 
     */
    public com.kingdee.eas.fdc.tenancy.RentRemissionInfo getHead()
    {
        return (com.kingdee.eas.fdc.tenancy.RentRemissionInfo)get("head");
    }
    public void setHead(com.kingdee.eas.fdc.tenancy.RentRemissionInfo item)
    {
        put("head", item);
    }
    /**
     * Object:�������¼'s Ӧ������property 
     */
    public java.util.Date getAppDate()
    {
        return getDate("appDate");
    }
    public void setAppDate(java.util.Date item)
    {
        setDate("appDate", item);
    }
    /**
     * Object:�������¼'s ����Ӧ�ո���IDproperty 
     */
    public String getOtherPayListID()
    {
        return getString("otherPayListID");
    }
    public void setOtherPayListID(String item)
    {
        setString("otherPayListID", item);
    }
    /**
     * Object:�������¼'s �Ƿ��������� property 
     */
    public boolean isIsOther()
    {
        return getBoolean("isOther");
    }
    public void setIsOther(boolean item)
    {
        setBoolean("isOther", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("09A090FB");
    }
}