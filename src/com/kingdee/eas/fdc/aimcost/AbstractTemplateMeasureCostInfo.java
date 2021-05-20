package com.kingdee.eas.fdc.aimcost;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractTemplateMeasureCostInfo extends com.kingdee.eas.fdc.basedata.FDCBillInfo implements Serializable 
{
    public AbstractTemplateMeasureCostInfo()
    {
        this("id");
    }
    protected AbstractTemplateMeasureCostInfo(String pkField)
    {
        super(pkField);
        put("constrEntrys", new com.kingdee.eas.fdc.aimcost.TempConstrPlanIndexEntryCollection());
        put("costEntry", new com.kingdee.eas.fdc.aimcost.TemplateMeasureEntryCollection());
        put("newPlanIndexEntryPT", new com.kingdee.eas.fdc.aimcost.TemplateNewPlanIndexPTCollection());
        put("newPlanIndexEntry", new com.kingdee.eas.fdc.aimcost.TemplateNewPlanIndexCollection());
    }
    /**
     * Object: �ɱ����� 's ������Ŀ property 
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
     * Object: �ɱ����� 's ��¼ property 
     */
    public com.kingdee.eas.fdc.aimcost.TemplateMeasureEntryCollection getCostEntry()
    {
        return (com.kingdee.eas.fdc.aimcost.TemplateMeasureEntryCollection)get("costEntry");
    }
    /**
     * Object:�ɱ�����'s �汾��property 
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
     * Object:�ɱ�����'s �汾����property 
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
     * Object:�ɱ�����'s �޶�����property 
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
     * Object:�ɱ�����'s �Ƿ����հ汾property 
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
     * Object: �ɱ����� 's ��Ŀϵ�� property 
     */
    public com.kingdee.eas.fdc.basedata.ProjectTypeInfo getProjectType()
    {
        return (com.kingdee.eas.fdc.basedata.ProjectTypeInfo)get("projectType");
    }
    public void setProjectType(com.kingdee.eas.fdc.basedata.ProjectTypeInfo item)
    {
        put("projectType", item);
    }
    /**
     * Object:�ɱ�����'s �Ƿ�Ŀ��ɱ�����property 
     */
    public boolean isIsAimMeasure()
    {
        return getBoolean("isAimMeasure");
    }
    public void setIsAimMeasure(boolean item)
    {
        setBoolean("isAimMeasure", item);
    }
    /**
     * Object:�ɱ�����'s ���汾��property 
     */
    public int getMainVerNo()
    {
        return getInt("mainVerNo");
    }
    public void setMainVerNo(int item)
    {
        setInt("mainVerNo", item);
    }
    /**
     * Object:�ɱ�����'s �Ӱ汾��property 
     */
    public int getSubVerNo()
    {
        return getInt("subVerNo");
    }
    public void setSubVerNo(int item)
    {
        setInt("subVerNo", item);
    }
    /**
     * Object: �ɱ����� 's ����ָ�� property 
     */
    public com.kingdee.eas.fdc.aimcost.TempConstrPlanIndexEntryCollection getConstrEntrys()
    {
        return (com.kingdee.eas.fdc.aimcost.TempConstrPlanIndexEntryCollection)get("constrEntrys");
    }
    /**
     * Object: �ɱ����� 's ����׶� property 
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
     * Object:�ɱ�����'s ���޶�����IDproperty 
     */
    public String getSrcMeasureCostId()
    {
        return getString("srcMeasureCostId");
    }
    public void setSrcMeasureCostId(String item)
    {
        setString("srcMeasureCostId", item);
    }
    /**
     * Object:�ɱ�����'s ����˵��property 
     */
    public String getMeasureDescription()
    {
        return getString("measureDescription");
    }
    public void setMeasureDescription(String item)
    {
        setString("measureDescription", item);
    }
    /**
     * Object:�ɱ�����'s �汾����property 
     */
    public com.kingdee.eas.fdc.aimcost.VersionTypeEnum getVersionType()
    {
        return com.kingdee.eas.fdc.aimcost.VersionTypeEnum.getEnum(getString("versionType"));
    }
    public void setVersionType(com.kingdee.eas.fdc.aimcost.VersionTypeEnum item)
    {
		if (item != null) {
        setString("versionType", item.getValue());
		}
    }
    /**
     * Object: �ɱ����� 's �滮ָ�� property 
     */
    public com.kingdee.eas.fdc.aimcost.TemplateNewPlanIndexCollection getNewPlanIndexEntry()
    {
        return (com.kingdee.eas.fdc.aimcost.TemplateNewPlanIndexCollection)get("newPlanIndexEntry");
    }
    /**
     * Object: �ɱ����� 's �滮ָ��ҵ̬���� property 
     */
    public com.kingdee.eas.fdc.aimcost.TemplateNewPlanIndexPTCollection getNewPlanIndexEntryPT()
    {
        return (com.kingdee.eas.fdc.aimcost.TemplateNewPlanIndexPTCollection)get("newPlanIndexEntryPT");
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("241306BA");
    }
}