package com.kingdee.eas.fdc.finance;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractFDCProDepConPayPlanInfo extends com.kingdee.eas.fdc.basedata.FDCBillInfo implements Serializable 
{
    public AbstractFDCProDepConPayPlanInfo()
    {
        this("id");
    }
    protected AbstractFDCProDepConPayPlanInfo(String pkField)
    {
        super(pkField);
        put("noContract", new com.kingdee.eas.fdc.finance.FDCProDepConPayPlanNoContractCollection());
        put("unsettledCon", new com.kingdee.eas.fdc.finance.FDCProDepConPayPlanUnsettledCollection());
        put("hasContract", new com.kingdee.eas.fdc.finance.FDCProDepConPayPlanContractCollection());
    }
    /**
     * Object: ��Ŀ�¶��ʽ��������ƻ� 's ������Ŀ property 
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
     * Object: ��Ŀ�¶��ʽ��������ƻ� 's ����ƻ����� property 
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
     * Object: ��Ŀ�¶��ʽ��������ƻ� 's ���� property 
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
     * Object:��Ŀ�¶��ʽ��������ƻ�'s ���property 
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
     * Object:��Ŀ�¶��ʽ��������ƻ�'s �·�property 
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
     * Object:��Ŀ�¶��ʽ��������ƻ�'s �����·�property 
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
     * Object:��Ŀ�¶��ʽ��������ƻ�'s �汾property 
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
     * Object: ��Ŀ�¶��ʽ��������ƻ� 's ��ǩ����ͬ��¼ property 
     */
    public com.kingdee.eas.fdc.finance.FDCProDepConPayPlanContractCollection getHasContract()
    {
        return (com.kingdee.eas.fdc.finance.FDCProDepConPayPlanContractCollection)get("hasContract");
    }
    /**
     * Object: ��Ŀ�¶��ʽ��������ƻ� 's ��ǩ����ͬ��¼ property 
     */
    public com.kingdee.eas.fdc.finance.FDCProDepConPayPlanUnsettledCollection getUnsettledCon()
    {
        return (com.kingdee.eas.fdc.finance.FDCProDepConPayPlanUnsettledCollection)get("unsettledCon");
    }
    /**
     * Object: ��Ŀ�¶��ʽ��������ƻ� 's �޺�ͬ��¼ property 
     */
    public com.kingdee.eas.fdc.finance.FDCProDepConPayPlanNoContractCollection getNoContract()
    {
        return (com.kingdee.eas.fdc.finance.FDCProDepConPayPlanNoContractCollection)get("noContract");
    }
    /**
     * Object:��Ŀ�¶��ʽ��������ƻ�'s �Ƿ����»���property 
     */
    public boolean isIsReSum()
    {
        return getBoolean("isReSum");
    }
    public void setIsReSum(boolean item)
    {
        setBoolean("isReSum", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("B3BEFFE7");
    }
}