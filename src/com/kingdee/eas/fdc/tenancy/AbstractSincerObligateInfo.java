package com.kingdee.eas.fdc.tenancy;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractSincerObligateInfo extends com.kingdee.eas.fdc.tenancy.TenBillBaseInfo implements Serializable 
{
    public AbstractSincerObligateInfo()
    {
        this("id");
    }
    protected AbstractSincerObligateInfo(String pkField)
    {
        super(pkField);
        put("sinAttachList", new com.kingdee.eas.fdc.tenancy.SinObligateAttachEntryCollection());
        put("sinCustomerList", new com.kingdee.eas.fdc.tenancy.SinCustomerEntrysCollection());
        put("sinRoomList", new com.kingdee.eas.fdc.tenancy.SinObligateRoomsEntryCollection());
        put("payListEntrys", new com.kingdee.eas.fdc.tenancy.SincerPaylistEntrysCollection());
    }
    /**
     * Object: ����Ԥ�� 's �ͻ���¼ property 
     */
    public com.kingdee.eas.fdc.tenancy.SinCustomerEntrysCollection getSinCustomerList()
    {
        return (com.kingdee.eas.fdc.tenancy.SinCustomerEntrysCollection)get("sinCustomerList");
    }
    /**
     * Object: ����Ԥ�� 's �����¼ property 
     */
    public com.kingdee.eas.fdc.tenancy.SinObligateRoomsEntryCollection getSinRoomList()
    {
        return (com.kingdee.eas.fdc.tenancy.SinObligateRoomsEntryCollection)get("sinRoomList");
    }
    /**
     * Object: ����Ԥ�� 's ������Դ��¼ property 
     */
    public com.kingdee.eas.fdc.tenancy.SinObligateAttachEntryCollection getSinAttachList()
    {
        return (com.kingdee.eas.fdc.tenancy.SinObligateAttachEntryCollection)get("sinAttachList");
    }
    /**
     * Object:����Ԥ��'s Ԥ������property 
     */
    public int getObligateDateCount()
    {
        return getInt("obligateDateCount");
    }
    public void setObligateDateCount(int item)
    {
        setInt("obligateDateCount", item);
    }
    /**
     * Object:����Ԥ��'s Լ�������ڼ䳤��property 
     */
    public int getTenancyTermLength()
    {
        return getInt("tenancyTermLength");
    }
    public void setTenancyTermLength(int item)
    {
        setInt("tenancyTermLength", item);
    }
    /**
     * Object:����Ԥ��'s Լ����������property 
     */
    public int getPlightFreeDays()
    {
        return getInt("plightFreeDays");
    }
    public void setPlightFreeDays(int item)
    {
        setInt("plightFreeDays", item);
    }
    /**
     * Object:����Ԥ��'s Լ����ʼ����property 
     */
    public java.util.Date getPlightStrartDate()
    {
        return getDate("plightStrartDate");
    }
    public void setPlightStrartDate(java.util.Date item)
    {
        setDate("plightStrartDate", item);
    }
    /**
     * Object:����Ԥ��'s Լ������property 
     */
    public java.math.BigDecimal getLeaseCount()
    {
        return getBigDecimal("leaseCount");
    }
    public void setLeaseCount(java.math.BigDecimal item)
    {
        setBigDecimal("leaseCount", item);
    }
    /**
     * Object:����Ԥ��'s Լ����������property 
     */
    public java.util.Date getPlightEndDate()
    {
        return getDate("plightEndDate");
    }
    public void setPlightEndDate(java.util.Date item)
    {
        setDate("plightEndDate", item);
    }
    /**
     * Object:����Ԥ��'s �Ƿ�ִ��property 
     */
    public boolean isIsExecuted()
    {
        return getBoolean("isExecuted");
    }
    public void setIsExecuted(boolean item)
    {
        setBoolean("isExecuted", item);
    }
    /**
     * Object:����Ԥ��'s ִ��ʱ��property 
     */
    public java.sql.Timestamp getExecutedTime()
    {
        return getTimestamp("executedTime");
    }
    public void setExecutedTime(java.sql.Timestamp item)
    {
        setTimestamp("executedTime", item);
    }
    /**
     * Object: ����Ԥ�� 's ������Ŀ property 
     */
    public com.kingdee.eas.fdc.sellhouse.SellProjectInfo getSellProject()
    {
        return (com.kingdee.eas.fdc.sellhouse.SellProjectInfo)get("sellProject");
    }
    public void setSellProject(com.kingdee.eas.fdc.sellhouse.SellProjectInfo item)
    {
        put("sellProject", item);
    }
    /**
     * Object:����Ԥ��'s ҵ�񵥾�״̬property 
     */
    public com.kingdee.eas.fdc.tenancy.BizStateEnum getBizState()
    {
        return com.kingdee.eas.fdc.tenancy.BizStateEnum.getEnum(getString("bizState"));
    }
    public void setBizState(com.kingdee.eas.fdc.tenancy.BizStateEnum item)
    {
		if (item != null) {
        setString("bizState", item.getValue());
		}
    }
    /**
     * Object:����Ԥ��'s �Ƿ�Ӧ�ճ����property 
     */
    public boolean isIsSincerReceive()
    {
        return getBoolean("isSincerReceive");
    }
    public void setIsSincerReceive(boolean item)
    {
        setBoolean("isSincerReceive", item);
    }
    /**
     * Object: ����Ԥ�� 's ������ϸ��¼ property 
     */
    public com.kingdee.eas.fdc.tenancy.SincerPaylistEntrysCollection getPayListEntrys()
    {
        return (com.kingdee.eas.fdc.tenancy.SincerPaylistEntrysCollection)get("payListEntrys");
    }
    /**
     * Object:����Ԥ��'s �����property 
     */
    public int getSatisfaction()
    {
        return getInt("satisfaction");
    }
    public void setSatisfaction(int item)
    {
        setInt("satisfaction", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("37796DDC");
    }
}