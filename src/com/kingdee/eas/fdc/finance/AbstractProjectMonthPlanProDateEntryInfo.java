package com.kingdee.eas.fdc.finance;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractProjectMonthPlanProDateEntryInfo extends com.kingdee.eas.framework.CoreBillEntryBaseInfo implements Serializable 
{
    public AbstractProjectMonthPlanProDateEntryInfo()
    {
        this("id");
    }
    protected AbstractProjectMonthPlanProDateEntryInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object:��Ŀ�¶ȸ���ƻ���ϸ��¼����Լ�滮��'s �·�property 
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
     * Object:��Ŀ�¶ȸ���ƻ���ϸ��¼����Լ�滮��'s ���property 
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
     * Object: ��Ŀ�¶ȸ���ƻ���ϸ��¼����Լ�滮�� 's Ԥ����Ŀ property 
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
     * Object:��Ŀ�¶ȸ���ƻ���ϸ��¼����Լ�滮��'s �ƻ�֧��property 
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
     * Object: ��Ŀ�¶ȸ���ƻ���ϸ��¼����Լ�滮�� 's ��Ŀ��ȸ���ƻ���¼����Լ�滮�� property 
     */
    public com.kingdee.eas.fdc.finance.ProjectMonthPlanProEntryInfo getHeadEntry()
    {
        return (com.kingdee.eas.fdc.finance.ProjectMonthPlanProEntryInfo)get("headEntry");
    }
    public void setHeadEntry(com.kingdee.eas.fdc.finance.ProjectMonthPlanProEntryInfo item)
    {
        put("headEntry", item);
    }
    /**
     * Object:��Ŀ�¶ȸ���ƻ���ϸ��¼����Լ�滮��'s �ÿ�˵��property 
     */
    public String getRemark()
    {
        return getString("remark");
    }
    public void setRemark(String item)
    {
        setString("remark", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("10563C96");
    }
}