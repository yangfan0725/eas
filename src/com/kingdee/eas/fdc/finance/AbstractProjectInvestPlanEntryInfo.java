package com.kingdee.eas.fdc.finance;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractProjectInvestPlanEntryInfo extends com.kingdee.eas.framework.CoreBillEntryBaseInfo implements Serializable 
{
    public AbstractProjectInvestPlanEntryInfo()
    {
        this("id");
    }
    protected AbstractProjectInvestPlanEntryInfo(String pkField)
    {
        super(pkField);
        put("monthEntry", new com.kingdee.eas.fdc.finance.MonthEntryCollection());
    }
    /**
     * Object: ��Ŀȫ���������ʽ�Ͷ��ƻ���¼ 's �ɱ���Ŀ property 
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
     * Object: ��Ŀȫ���������ʽ�Ͷ��ƻ���¼ 's  property 
     */
    public com.kingdee.eas.fdc.finance.ProjectInvestPlanInfo getParent()
    {
        return (com.kingdee.eas.fdc.finance.ProjectInvestPlanInfo)get("parent");
    }
    public void setParent(com.kingdee.eas.fdc.finance.ProjectInvestPlanInfo item)
    {
        put("parent", item);
    }
    /**
     * Object: ��Ŀȫ���������ʽ�Ͷ��ƻ���¼ 's �·�Ͷ����Ϣ property 
     */
    public com.kingdee.eas.fdc.finance.MonthEntryCollection getMonthEntry()
    {
        return (com.kingdee.eas.fdc.finance.MonthEntryCollection)get("monthEntry");
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("480EB9CA");
    }
}