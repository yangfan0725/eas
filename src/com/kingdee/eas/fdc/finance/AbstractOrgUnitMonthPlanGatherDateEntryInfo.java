package com.kingdee.eas.fdc.finance;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractOrgUnitMonthPlanGatherDateEntryInfo extends com.kingdee.eas.framework.CoreBillEntryBaseInfo implements Serializable 
{
    public AbstractOrgUnitMonthPlanGatherDateEntryInfo()
    {
        this("id");
    }
    protected AbstractOrgUnitMonthPlanGatherDateEntryInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object:�¶��ʽ�ƻ�������ϸ��¼'s �·�property 
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
     * Object:�¶��ʽ�ƻ�������ϸ��¼'s ���property 
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
     * Object: �¶��ʽ�ƻ�������ϸ��¼ 's Ԥ����Ŀ property 
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
     * Object:�¶��ʽ�ƻ�������ϸ��¼'s �ƻ�֧��property 
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
     * Object: �¶��ʽ�ƻ�������ϸ��¼ 's �¶��ʽ�ƻ����ܷ�¼ property 
     */
    public com.kingdee.eas.fdc.finance.OrgUnitMonthPlanGatherEntryInfo getHeadEntry()
    {
        return (com.kingdee.eas.fdc.finance.OrgUnitMonthPlanGatherEntryInfo)get("headEntry");
    }
    public void setHeadEntry(com.kingdee.eas.fdc.finance.OrgUnitMonthPlanGatherEntryInfo item)
    {
        put("headEntry", item);
    }
    /**
     * Object:�¶��ʽ�ƻ�������ϸ��¼'s �ÿ�˵��property 
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
     * Object:�¶��ʽ�ƻ�������ϸ��¼'s �ϱ����property 
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
     * Object:�¶��ʽ�ƻ�������ϸ��¼'s �˶����property 
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
     * Object:�¶��ʽ�ƻ�������ϸ��¼'s ��ɹ�����property 
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
        return new BOSObjectType("730F8C59");
    }
}