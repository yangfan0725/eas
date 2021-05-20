package com.kingdee.eas.fdc.finance;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractProjectYearPlanInfo extends com.kingdee.eas.fdc.basedata.FDCBillInfo implements Serializable 
{
    public AbstractProjectYearPlanInfo()
    {
        this("id");
    }
    protected AbstractProjectYearPlanInfo(String pkField)
    {
        super(pkField);
        put("totalBgEntry", new com.kingdee.eas.fdc.finance.ProjectYearPlanTotalBgItemEntryCollection());
        put("entry", new com.kingdee.eas.fdc.finance.ProjectYearPlanEntryCollection());
        put("totalEntry", new com.kingdee.eas.fdc.finance.ProjectYearPlanTotalEntryCollection());
    }
    /**
     * Object: ��Ŀ��ȸ���滮 's ������Ŀ property 
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
     * Object:��Ŀ��ȸ���滮's �汾��property 
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
     * Object: ��Ŀ��ȸ���滮 's ��¼ property 
     */
    public com.kingdee.eas.fdc.finance.ProjectYearPlanEntryCollection getEntry()
    {
        return (com.kingdee.eas.fdc.finance.ProjectYearPlanEntryCollection)get("entry");
    }
    /**
     * Object:��Ŀ��ȸ���滮's �Ƿ�����property 
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
     * Object: ��Ŀ��ȸ���滮 's ���ܷ�¼ property 
     */
    public com.kingdee.eas.fdc.finance.ProjectYearPlanTotalEntryCollection getTotalEntry()
    {
        return (com.kingdee.eas.fdc.finance.ProjectYearPlanTotalEntryCollection)get("totalEntry");
    }
    /**
     * Object: ��Ŀ��ȸ���滮 's Ԥ����Ŀ���ܷ�¼ property 
     */
    public com.kingdee.eas.fdc.finance.ProjectYearPlanTotalBgItemEntryCollection getTotalBgEntry()
    {
        return (com.kingdee.eas.fdc.finance.ProjectYearPlanTotalBgItemEntryCollection)get("totalBgEntry");
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("9F9C8250");
    }
}