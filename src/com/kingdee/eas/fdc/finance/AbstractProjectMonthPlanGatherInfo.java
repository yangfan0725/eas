package com.kingdee.eas.fdc.finance;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractProjectMonthPlanGatherInfo extends com.kingdee.eas.fdc.basedata.FDCBillInfo implements Serializable 
{
    public AbstractProjectMonthPlanGatherInfo()
    {
        this("id");
    }
    protected AbstractProjectMonthPlanGatherInfo(String pkField)
    {
        super(pkField);
        put("payEntry", new com.kingdee.eas.fdc.finance.ProjectMonthPlanGatherPayEntryCollection());
        put("entry", new com.kingdee.eas.fdc.finance.ProjectMonthPlanGatherEntryCollection());
    }
    /**
     * Object: ��Ŀ�¶��ʽ�ƻ� 's ������Ŀ property 
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
     * Object:��Ŀ�¶��ʽ�ƻ�'s �汾��property 
     */
    public int getVersion()
    {
        return getInt("version");
    }
    public void setVersion(int item)
    {
        setInt("version", item);
    }
    /**
     * Object: ��Ŀ�¶��ʽ�ƻ� 's ��¼ property 
     */
    public com.kingdee.eas.fdc.finance.ProjectMonthPlanGatherEntryCollection getEntry()
    {
        return (com.kingdee.eas.fdc.finance.ProjectMonthPlanGatherEntryCollection)get("entry");
    }
    /**
     * Object:��Ŀ�¶��ʽ�ƻ�'s �Ƿ�����property 
     */
    public boolean isIsLatest()
    {
        return getBoolean("isLatest");
    }
    public void setIsLatest(boolean item)
    {
        setBoolean("isLatest", item);
    }
    /**
     * Object: ��Ŀ�¶��ʽ�ƻ� 's �ƻ����� property 
     */
    public com.kingdee.eas.fdc.basedata.PayPlanCycleInfo getCycle()
    {
        return (com.kingdee.eas.fdc.basedata.PayPlanCycleInfo)get("cycle");
    }
    public void setCycle(com.kingdee.eas.fdc.basedata.PayPlanCycleInfo item)
    {
        put("cycle", item);
    }
    /**
     * Object:��Ŀ�¶��ʽ�ƻ�'s �汾����property 
     */
    public com.kingdee.eas.fdc.finance.VersionTypeEnum getVersionType()
    {
        return com.kingdee.eas.fdc.finance.VersionTypeEnum.getEnum(getString("versionType"));
    }
    public void setVersionType(com.kingdee.eas.fdc.finance.VersionTypeEnum item)
    {
		if (item != null) {
        setString("versionType", item.getValue());
		}
    }
    /**
     * Object: ��Ŀ�¶��ʽ�ƻ� 's δ���֧�����ݷ�¼ property 
     */
    public com.kingdee.eas.fdc.finance.ProjectMonthPlanGatherPayEntryCollection getPayEntry()
    {
        return (com.kingdee.eas.fdc.finance.ProjectMonthPlanGatherPayEntryCollection)get("payEntry");
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("0003689A");
    }
}