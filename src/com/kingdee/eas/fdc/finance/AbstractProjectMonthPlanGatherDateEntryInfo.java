package com.kingdee.eas.fdc.finance;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractProjectMonthPlanGatherDateEntryInfo extends com.kingdee.eas.framework.CoreBillEntryBaseInfo implements Serializable 
{
    public AbstractProjectMonthPlanGatherDateEntryInfo()
    {
        this("id");
    }
    protected AbstractProjectMonthPlanGatherDateEntryInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object:��Ŀ�¶ȸ���ƻ�������ϸ��¼'s �·�property 
     */
    public int getMonth()
    {
        return getInt("month");
    }
    public void setMonth(int item)
    {
        setInt("month", item);
    }
    /**
     * Object:��Ŀ�¶ȸ���ƻ�������ϸ��¼'s ���property 
     */
    public int getYear()
    {
        return getInt("year");
    }
    public void setYear(int item)
    {
        setInt("year", item);
    }
    /**
     * Object: ��Ŀ�¶ȸ���ƻ�������ϸ��¼ 's Ԥ����Ŀ property 
     */
    public com.kingdee.eas.ma.budget.BgItemInfo getBgItem()
    {
        return (com.kingdee.eas.ma.budget.BgItemInfo)get("bgItem");
    }
    public void setBgItem(com.kingdee.eas.ma.budget.BgItemInfo item)
    {
        put("bgItem", item);
    }
    /**
     * Object:��Ŀ�¶ȸ���ƻ�������ϸ��¼'s �ƻ�֧��property 
     */
    public java.math.BigDecimal getAmount()
    {
        return getBigDecimal("amount");
    }
    public void setAmount(java.math.BigDecimal item)
    {
        setBigDecimal("amount", item);
    }
    /**
     * Object: ��Ŀ�¶ȸ���ƻ�������ϸ��¼ 's ��Ŀ��ȸ���ƻ���¼ property 
     */
    public com.kingdee.eas.fdc.finance.ProjectMonthPlanGatherEntryInfo getHeadEntry()
    {
        return (com.kingdee.eas.fdc.finance.ProjectMonthPlanGatherEntryInfo)get("headEntry");
    }
    public void setHeadEntry(com.kingdee.eas.fdc.finance.ProjectMonthPlanGatherEntryInfo item)
    {
        put("headEntry", item);
    }
    /**
     * Object:��Ŀ�¶ȸ���ƻ�������ϸ��¼'s �ÿ�˵��property 
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
     * Object:��Ŀ�¶ȸ���ƻ�������ϸ��¼'s �ϱ����property 
     */
    public java.math.BigDecimal getReportAmount()
    {
        return getBigDecimal("reportAmount");
    }
    public void setReportAmount(java.math.BigDecimal item)
    {
        setBigDecimal("reportAmount", item);
    }
    /**
     * Object:��Ŀ�¶ȸ���ƻ�������ϸ��¼'s �˶����property 
     */
    public java.math.BigDecimal getExecuteAmount()
    {
        return getBigDecimal("executeAmount");
    }
    public void setExecuteAmount(java.math.BigDecimal item)
    {
        setBigDecimal("executeAmount", item);
    }
    /**
     * Object:��Ŀ�¶ȸ���ƻ�������ϸ��¼'s ��ɹ�����property 
     */
    public java.math.BigDecimal getAmt()
    {
        return getBigDecimal("amt");
    }
    public void setAmt(java.math.BigDecimal item)
    {
        setBigDecimal("amt", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("84AB89EA");
    }
}