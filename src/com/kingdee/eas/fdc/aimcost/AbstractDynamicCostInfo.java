package com.kingdee.eas.fdc.aimcost;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractDynamicCostInfo extends com.kingdee.eas.framework.BillBaseInfo implements Serializable 
{
    public AbstractDynamicCostInfo()
    {
        this("id");
    }
    protected AbstractDynamicCostInfo(String pkField)
    {
        super(pkField);
        put("adjustEntrys", new com.kingdee.eas.fdc.aimcost.AdjustRecordEntryCollection());
        put("intendingCostEntrys", new com.kingdee.eas.fdc.aimcost.IntendingCostEntryCollection());
    }
    /**
     * Object: ��̬�ɱ� 's ��Ŀ property 
     */
    public com.kingdee.eas.fdc.basedata.CostAccountInfo getAccount()
    {
        return (com.kingdee.eas.fdc.basedata.CostAccountInfo)get("account");
    }
    public void setAccount(com.kingdee.eas.fdc.basedata.CostAccountInfo item)
    {
        put("account", item);
    }
    /**
     * Object:��̬�ɱ�'s �����ܶ�property 
     */
    public java.math.BigDecimal getAdjustSumAmount()
    {
        return getBigDecimal("adjustSumAmount");
    }
    public void setAdjustSumAmount(java.math.BigDecimal item)
    {
        setBigDecimal("adjustSumAmount", item);
    }
    /**
     * Object:��̬�ɱ�'s �������ɱ��ܶ�property 
     */
    public java.math.BigDecimal getIntendingCostSumAmount()
    {
        return getBigDecimal("intendingCostSumAmount");
    }
    public void setIntendingCostSumAmount(java.math.BigDecimal item)
    {
        setBigDecimal("intendingCostSumAmount", item);
    }
    /**
     * Object: ��̬�ɱ� 's ������Ϣ��¼ property 
     */
    public com.kingdee.eas.fdc.aimcost.AdjustRecordEntryCollection getAdjustEntrys()
    {
        return (com.kingdee.eas.fdc.aimcost.AdjustRecordEntryCollection)get("adjustEntrys");
    }
    /**
     * Object: ��̬�ɱ� 's �������ɱ���¼ property 
     */
    public com.kingdee.eas.fdc.aimcost.IntendingCostEntryCollection getIntendingCostEntrys()
    {
        return (com.kingdee.eas.fdc.aimcost.IntendingCostEntryCollection)get("intendingCostEntrys");
    }
    /**
     * Object: ��̬�ɱ� 's �ڼ�(���ݷ����ı���ڼ�) property 
     */
    public com.kingdee.eas.basedata.assistant.PeriodInfo getPeriod()
    {
        return (com.kingdee.eas.basedata.assistant.PeriodInfo)get("period");
    }
    public void setPeriod(com.kingdee.eas.basedata.assistant.PeriodInfo item)
    {
        put("period", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("F5C696F5");
    }
}