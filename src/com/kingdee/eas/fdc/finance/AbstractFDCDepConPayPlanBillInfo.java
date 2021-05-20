package com.kingdee.eas.fdc.finance;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractFDCDepConPayPlanBillInfo extends com.kingdee.eas.fdc.basedata.FDCBillInfo implements Serializable 
{
    public AbstractFDCDepConPayPlanBillInfo()
    {
        this("id");
    }
    protected AbstractFDCDepConPayPlanBillInfo(String pkField)
    {
        super(pkField);
        put("noContract", new com.kingdee.eas.fdc.finance.FDCDepConPayPlanNoContractCollection());
        put("unsettledCon", new com.kingdee.eas.fdc.finance.FDCDepConPayPlanUnsettledConCollection());
        put("hasContract", new com.kingdee.eas.fdc.finance.FDCDepConPayPlanContractCollection());
    }
    /**
     * Object: ��ͬ�¶ȹ�������ƻ� 's ������Ŀ property 
     */
    public com.kingdee.eas.fdc.basedata.CurProjectInfo getCurProject()
    {
        return (com.kingdee.eas.fdc.basedata.CurProjectInfo)get("curProject");
    }
    public void setCurProject(com.kingdee.eas.fdc.basedata.CurProjectInfo item)
    {
        put("curProject", item);
    }
    /**
     * Object: ��ͬ�¶ȹ�������ƻ� 's ���ز�Ԥ���ڼ� property 
     */
    public com.kingdee.eas.fdc.finance.FDCBudgetPeriodInfo getFdcPeriod()
    {
        return (com.kingdee.eas.fdc.finance.FDCBudgetPeriodInfo)get("fdcPeriod");
    }
    public void setFdcPeriod(com.kingdee.eas.fdc.finance.FDCBudgetPeriodInfo item)
    {
        put("fdcPeriod", item);
    }
    /**
     * Object: ��ͬ�¶ȹ�������ƻ� 's ����ƻ����� property 
     */
    public com.kingdee.eas.fdc.basedata.PayPlanCycleInfo getPayPlanCycle()
    {
        return (com.kingdee.eas.fdc.basedata.PayPlanCycleInfo)get("payPlanCycle");
    }
    public void setPayPlanCycle(com.kingdee.eas.fdc.basedata.PayPlanCycleInfo item)
    {
        put("payPlanCycle", item);
    }
    /**
     * Object: ��ͬ�¶ȹ�������ƻ� 's ���� property 
     */
    public com.kingdee.eas.basedata.org.AdminOrgUnitInfo getDeptment()
    {
        return (com.kingdee.eas.basedata.org.AdminOrgUnitInfo)get("deptment");
    }
    public void setDeptment(com.kingdee.eas.basedata.org.AdminOrgUnitInfo item)
    {
        put("deptment", item);
    }
    /**
     * Object:��ͬ�¶ȹ�������ƻ�'s ���property 
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
     * Object:��ͬ�¶ȹ�������ƻ�'s �·�property 
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
     * Object:��ͬ�¶ȹ�������ƻ�'s �����·�property 
     */
    public int getEditMonth()
    {
        return getInt("editMonth");
    }
    public void setEditMonth(int item)
    {
        setInt("editMonth", item);
    }
    /**
     * Object:��ͬ�¶ȹ�������ƻ�'s �汾property 
     */
    public String getVersion()
    {
        return getString("version");
    }
    public void setVersion(String item)
    {
        setString("version", item);
    }
    /**
     * Object: ��ͬ�¶ȹ�������ƻ� 's ��ǩ����ͬ����ƻ� property 
     */
    public com.kingdee.eas.fdc.finance.FDCDepConPayPlanContractCollection getHasContract()
    {
        return (com.kingdee.eas.fdc.finance.FDCDepConPayPlanContractCollection)get("hasContract");
    }
    /**
     * Object: ��ͬ�¶ȹ�������ƻ� 's ��ǩ����ͬ����ƻ� property 
     */
    public com.kingdee.eas.fdc.finance.FDCDepConPayPlanUnsettledConCollection getUnsettledCon()
    {
        return (com.kingdee.eas.fdc.finance.FDCDepConPayPlanUnsettledConCollection)get("unsettledCon");
    }
    /**
     * Object: ��ͬ�¶ȹ�������ƻ� 's �޺�ͬ����ƻ� property 
     */
    public com.kingdee.eas.fdc.finance.FDCDepConPayPlanNoContractCollection getNoContract()
    {
        return (com.kingdee.eas.fdc.finance.FDCDepConPayPlanNoContractCollection)get("noContract");
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("F288954F");
    }
}