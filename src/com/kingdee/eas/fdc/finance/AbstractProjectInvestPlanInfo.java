package com.kingdee.eas.fdc.finance;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractProjectInvestPlanInfo extends com.kingdee.eas.fdc.basedata.FDCBillInfo implements Serializable 
{
    public AbstractProjectInvestPlanInfo()
    {
        this("id");
    }
    protected AbstractProjectInvestPlanInfo(String pkField)
    {
        super(pkField);
        put("entry", new com.kingdee.eas.fdc.finance.ProjectInvestPlanEntryCollection());
    }
    /**
     * Object:��Ŀȫ���������ʽ�Ͷ��ƻ�'s �ƻ���ʼʱ��property 
     */
    public java.util.Date getStartDate()
    {
        return getDate("startDate");
    }
    public void setStartDate(java.util.Date item)
    {
        setDate("startDate", item);
    }
    /**
     * Object:��Ŀȫ���������ʽ�Ͷ��ƻ�'s �ƻ���ֹʱ��property 
     */
    public java.util.Date getEndDate()
    {
        return getDate("endDate");
    }
    public void setEndDate(java.util.Date item)
    {
        setDate("endDate", item);
    }
    /**
     * Object: ��Ŀȫ���������ʽ�Ͷ��ƻ� 's ��¼ property 
     */
    public com.kingdee.eas.fdc.finance.ProjectInvestPlanEntryCollection getEntry()
    {
        return (com.kingdee.eas.fdc.finance.ProjectInvestPlanEntryCollection)get("entry");
    }
    /**
     * Object: ��Ŀȫ���������ʽ�Ͷ��ƻ� 's ������Ŀ property 
     */
    public com.kingdee.eas.fdc.basedata.CurProjectInfo getProject()
    {
        return (com.kingdee.eas.fdc.basedata.CurProjectInfo)get("project");
    }
    public void setProject(com.kingdee.eas.fdc.basedata.CurProjectInfo item)
    {
        put("project", item);
    }
    /**
     * Object:��Ŀȫ���������ʽ�Ͷ��ƻ�'s �汾��property 
     */
    public java.math.BigDecimal getVersionNumber()
    {
        return getBigDecimal("versionNumber");
    }
    public void setVersionNumber(java.math.BigDecimal item)
    {
        setBigDecimal("versionNumber", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("2E405388");
    }
}