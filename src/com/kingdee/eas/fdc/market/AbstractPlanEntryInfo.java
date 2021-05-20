package com.kingdee.eas.fdc.market;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractPlanEntryInfo extends com.kingdee.eas.framework.BillEntryBaseInfo implements Serializable 
{
    public AbstractPlanEntryInfo()
    {
        this("id");
    }
    protected AbstractPlanEntryInfo(String pkField)
    {
        super(pkField);
        put("E3", new com.kingdee.eas.fdc.market.PlanEntryE3Collection());
    }
    /**
     * Object: ��¼ 's ����ͷ property 
     */
    public com.kingdee.eas.fdc.market.PlanInfo getParent()
    {
        return (com.kingdee.eas.fdc.market.PlanInfo)get("Parent");
    }
    public void setParent(com.kingdee.eas.fdc.market.PlanInfo item)
    {
        put("Parent", item);
    }
    /**
     * Object: ��¼ 's ����� property 
     */
    public com.kingdee.eas.fdc.market.MovementPlanInfo getMovementPlan()
    {
        return (com.kingdee.eas.fdc.market.MovementPlanInfo)get("MovementPlan");
    }
    public void setMovementPlan(com.kingdee.eas.fdc.market.MovementPlanInfo item)
    {
        put("MovementPlan", item);
    }
    /**
     * Object: ��¼ 's �ƻ�Ч�� property 
     */
    public com.kingdee.eas.fdc.market.PlanEntryE3Collection getE3()
    {
        return (com.kingdee.eas.fdc.market.PlanEntryE3Collection)get("E3");
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("6E26B418");
    }
}