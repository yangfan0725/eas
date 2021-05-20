package com.kingdee.eas.fdc.aimcost;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractAimCostInfo extends com.kingdee.eas.framework.CoreBillBaseInfo implements Serializable 
{
    public AbstractAimCostInfo()
    {
        this("id");
    }
    protected AbstractAimCostInfo(String pkField)
    {
        super(pkField);
        put("costEntry", new com.kingdee.eas.fdc.aimcost.CostEntryCollection());
    }
    /**
     * Object:Ŀ��ɱ�'s ��֯���߹�����ĿIdproperty 
     */
    public String getOrgOrProId()
    {
        return getString("orgOrProId");
    }
    public void setOrgOrProId(String item)
    {
        setString("orgOrProId", item);
    }
    /**
     * Object: Ŀ��ɱ� 's ��¼ property 
     */
    public com.kingdee.eas.fdc.aimcost.CostEntryCollection getCostEntry()
    {
        return (com.kingdee.eas.fdc.aimcost.CostEntryCollection)get("costEntry");
    }
    /**
     * Object:Ŀ��ɱ�'s �汾��property 
     */
    public String getVersionNumber()
    {
        return getString("versionNumber");
    }
    public void setVersionNumber(String item)
    {
        setString("versionNumber", item);
    }
    /**
     * Object:Ŀ��ɱ�'s �汾����property 
     */
    public String getVersionName()
    {
        return getString("versionName");
    }
    public void setVersionName(String item)
    {
        setString("versionName", item);
    }
    /**
     * Object:Ŀ��ɱ�'s �޶�����property 
     */
    public java.util.Date getRecenseDate()
    {
        return getDate("recenseDate");
    }
    public void setRecenseDate(java.util.Date item)
    {
        setDate("recenseDate", item);
    }
    /**
     * Object:Ŀ��ɱ�'s ��������property 
     */
    public java.util.Date getAuditDate()
    {
        return getDate("auditDate");
    }
    public void setAuditDate(java.util.Date item)
    {
        setDate("auditDate", item);
    }
    /**
     * Object:Ŀ��ɱ�'s �Ƿ����°汾property 
     */
    public boolean isIsLastVersion()
    {
        return getBoolean("isLastVersion");
    }
    public void setIsLastVersion(boolean item)
    {
        setBoolean("isLastVersion", item);
    }
    /**
     * Object: Ŀ��ɱ� 's �ڼ� property 
     */
    public com.kingdee.eas.basedata.assistant.PeriodInfo getPeriod()
    {
        return (com.kingdee.eas.basedata.assistant.PeriodInfo)get("period");
    }
    public void setPeriod(com.kingdee.eas.basedata.assistant.PeriodInfo item)
    {
        put("period", item);
    }
    /**
     * Object:Ŀ��ɱ�'s ����״̬property 
     */
    public com.kingdee.eas.fdc.basedata.FDCBillStateEnum getState()
    {
        return com.kingdee.eas.fdc.basedata.FDCBillStateEnum.getEnum(getString("state"));
    }
    public void setState(com.kingdee.eas.fdc.basedata.FDCBillStateEnum item)
    {
		if (item != null) {
        setString("state", item.getValue());
		}
    }
    /**
     * Object: Ŀ��ɱ� 's ����׶� property 
     */
    public com.kingdee.eas.fdc.basedata.MeasureStageInfo getMeasureStage()
    {
        return (com.kingdee.eas.fdc.basedata.MeasureStageInfo)get("measureStage");
    }
    public void setMeasureStage(com.kingdee.eas.fdc.basedata.MeasureStageInfo item)
    {
        put("measureStage", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("B08D497B");
    }
}