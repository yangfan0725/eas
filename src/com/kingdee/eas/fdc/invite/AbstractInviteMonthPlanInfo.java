package com.kingdee.eas.fdc.invite;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractInviteMonthPlanInfo extends com.kingdee.eas.fdc.basedata.FDCBillInfo implements Serializable 
{
    public AbstractInviteMonthPlanInfo()
    {
        this("id");
    }
    protected AbstractInviteMonthPlanInfo(String pkField)
    {
        super(pkField);
        put("entry", new com.kingdee.eas.fdc.invite.InviteMonthPlanEntrysCollection());
    }
    /**
     * Object: �¶Ȳɹ��б�ƻ� 's ������Ŀ property 
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
     * Object:�¶Ȳɹ��б�ƻ�'s �汾��property 
     */
    public java.math.BigDecimal getVersion()
    {
        return getBigDecimal("version");
    }
    public void setVersion(java.math.BigDecimal item)
    {
        setBigDecimal("version", item);
    }
    /**
     * Object: �¶Ȳɹ��б�ƻ� 's ��¼ property 
     */
    public com.kingdee.eas.fdc.invite.InviteMonthPlanEntrysCollection getEntry()
    {
        return (com.kingdee.eas.fdc.invite.InviteMonthPlanEntrysCollection)get("entry");
    }
    /**
     * Object: �¶Ȳɹ��б�ƻ� 's ��Ŀ�׶� property 
     */
    public com.kingdee.eas.fdc.basedata.MeasureStageInfo getMeasureStage()
    {
        return (com.kingdee.eas.fdc.basedata.MeasureStageInfo)get("measureStage");
    }
    public void setMeasureStage(com.kingdee.eas.fdc.basedata.MeasureStageInfo item)
    {
        put("measureStage", item);
    }
    /**
     * Object:�¶Ȳɹ��б�ƻ�'s �ƻ��·�property 
     */
    public int getPlanMonth()
    {
        return getInt("planMonth");
    }
    public void setPlanMonth(int item)
    {
        setInt("planMonth", item);
    }
    /**
     * Object:�¶Ȳɹ��б�ƻ�'s �ƻ��·�property 
     */
    public int getPlanYear()
    {
        return getInt("planYear");
    }
    public void setPlanYear(int item)
    {
        setInt("planYear", item);
    }
    /**
     * Object: �¶Ȳɹ��б�ƻ� 's ��Լ�滮 property 
     */
    public com.kingdee.eas.fdc.contract.programming.ProgrammingInfo getProgramming()
    {
        return (com.kingdee.eas.fdc.contract.programming.ProgrammingInfo)get("programming");
    }
    public void setProgramming(com.kingdee.eas.fdc.contract.programming.ProgrammingInfo item)
    {
        put("programming", item);
    }
    /**
     * Object:�¶Ȳɹ��б�ƻ�'s ��עproperty 
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
     * Object:�¶Ȳɹ��б�ƻ�'s �Ƿ�����property 
     */
    public boolean isIsLatest()
    {
        return getBoolean("isLatest");
    }
    public void setIsLatest(boolean item)
    {
        setBoolean("isLatest", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("F0BDFD1C");
    }
}