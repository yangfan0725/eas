package com.kingdee.eas.fdc.schedule;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractTaskCostInfoInfo extends com.kingdee.eas.fdc.schedule.SchedulePropBaseExtInfo implements Serializable 
{
    public AbstractTaskCostInfoInfo()
    {
        this("id");
    }
    protected AbstractTaskCostInfoInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object:����ɱ���Ϣ's Ŀ��ɱ�property 
     */
    public java.math.BigDecimal getAimCost()
    {
        return getBigDecimal("aimCost");
    }
    public void setAimCost(java.math.BigDecimal item)
    {
        setBigDecimal("aimCost", item);
    }
    /**
     * Object:����ɱ���Ϣ's ʵ�ʳɱ�property 
     */
    public java.math.BigDecimal getActCost()
    {
        return getBigDecimal("actCost");
    }
    public void setActCost(java.math.BigDecimal item)
    {
        setBigDecimal("actCost", item);
    }
    /**
     * Object:����ɱ���Ϣ's �ɱ�ƫ��property 
     */
    public java.math.BigDecimal getCostDeviation()
    {
        return getBigDecimal("costDeviation");
    }
    public void setCostDeviation(java.math.BigDecimal item)
    {
        setBigDecimal("costDeviation", item);
    }
    /**
     * Object:����ɱ���Ϣ's �ɱ���עproperty 
     */
    public String getCostMemo()
    {
        return getString("costMemo");
    }
    public void setCostMemo(String item)
    {
        setString("costMemo", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("DAFB3996");
    }
}