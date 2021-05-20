package com.kingdee.eas.fdc.schedule.report;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractScheduleReportOrgInfo extends com.kingdee.eas.fdc.basedata.FDCTreeBaseDataInfo implements Serializable 
{
    public AbstractScheduleReportOrgInfo()
    {
        this("id");
    }
    protected AbstractScheduleReportOrgInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: ���ȱ�����֯ 's ��Ӧ��֯ property 
     */
    public com.kingdee.eas.basedata.org.FullOrgUnitInfo getRelateOrg()
    {
        return (com.kingdee.eas.basedata.org.FullOrgUnitInfo)get("relateOrg");
    }
    public void setRelateOrg(com.kingdee.eas.basedata.org.FullOrgUnitInfo item)
    {
        put("relateOrg", item);
    }
    /**
     * Object: ���ȱ�����֯ 's ���ڵ� property 
     */
    public com.kingdee.eas.fdc.schedule.report.ScheduleReportOrgInfo getParent()
    {
        return (com.kingdee.eas.fdc.schedule.report.ScheduleReportOrgInfo)get("parent");
    }
    public void setParent(com.kingdee.eas.fdc.schedule.report.ScheduleReportOrgInfo item)
    {
        put("parent", item);
    }
    /**
     * Object:���ȱ�����֯'s ��עproperty 
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
     * Object:���ȱ�����֯'s �������property 
     */
    public int getSortNumber()
    {
        return getInt("sortNumber");
    }
    public void setSortNumber(int item)
    {
        setInt("sortNumber", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("714FB2DB");
    }
}