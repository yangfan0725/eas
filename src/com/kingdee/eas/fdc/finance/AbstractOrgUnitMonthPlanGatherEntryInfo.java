package com.kingdee.eas.fdc.finance;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractOrgUnitMonthPlanGatherEntryInfo extends com.kingdee.eas.framework.CoreBillEntryBaseInfo implements Serializable 
{
    public AbstractOrgUnitMonthPlanGatherEntryInfo()
    {
        this("id");
    }
    protected AbstractOrgUnitMonthPlanGatherEntryInfo(String pkField)
    {
        super(pkField);
        put("dateEntry", new com.kingdee.eas.fdc.finance.OrgUnitMonthPlanGatherDateEntryCollection());
    }
    /**
     * Object: �¶��ʽ�ƻ����ܷ�¼ 's �¶��ʽ�ƻ����� property 
     */
    public com.kingdee.eas.fdc.finance.OrgUnitMonthPlanGatherInfo getHead()
    {
        return (com.kingdee.eas.fdc.finance.OrgUnitMonthPlanGatherInfo)get("head");
    }
    public void setHead(com.kingdee.eas.fdc.finance.OrgUnitMonthPlanGatherInfo item)
    {
        put("head", item);
    }
    /**
     * Object: �¶��ʽ�ƻ����ܷ�¼ 's ��ϸ��¼ property 
     */
    public com.kingdee.eas.fdc.finance.OrgUnitMonthPlanGatherDateEntryCollection getDateEntry()
    {
        return (com.kingdee.eas.fdc.finance.OrgUnitMonthPlanGatherDateEntryCollection)get("dateEntry");
    }
    /**
     * Object: �¶��ʽ�ƻ����ܷ�¼ 's ������Ŀ property 
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
     * Object: �¶��ʽ�ƻ����ܷ�¼ 's ��֯ property 
     */
    public com.kingdee.eas.basedata.org.FullOrgUnitInfo getOrgUnit()
    {
        return (com.kingdee.eas.basedata.org.FullOrgUnitInfo)get("orgUnit");
    }
    public void setOrgUnit(com.kingdee.eas.basedata.org.FullOrgUnitInfo item)
    {
        put("orgUnit", item);
    }
    /**
     * Object:�¶��ʽ�ƻ����ܷ�¼'s Դ����Idproperty 
     */
    public com.kingdee.bos.util.BOSUuid getSrcId()
    {
        return getBOSUuid("srcId");
    }
    public void setSrcId(com.kingdee.bos.util.BOSUuid item)
    {
        setBOSUuid("srcId", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("C89491E7");
    }
}