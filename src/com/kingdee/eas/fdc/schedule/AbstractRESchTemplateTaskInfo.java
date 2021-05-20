package com.kingdee.eas.fdc.schedule;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractRESchTemplateTaskInfo extends com.kingdee.eas.fdc.basedata.FDCTreeBaseDataInfo implements Serializable 
{
    public AbstractRESchTemplateTaskInfo()
    {
        this("id");
    }
    protected AbstractRESchTemplateTaskInfo(String pkField)
    {
        super(pkField);
        put("businessType", new com.kingdee.eas.fdc.schedule.RESchTemplateTaskBizTypeCollection());
        put("predecessors", new com.kingdee.eas.fdc.schedule.RESchTemplateTaskPredecessorCollection());
    }
    /**
     * Object: ���ز��ƻ�ģ������ 's ģ�� property 
     */
    public com.kingdee.eas.fdc.schedule.RESchTemplateInfo getTemplate()
    {
        return (com.kingdee.eas.fdc.schedule.RESchTemplateInfo)get("template");
    }
    public void setTemplate(com.kingdee.eas.fdc.schedule.RESchTemplateInfo item)
    {
        put("template", item);
    }
    /**
     * Object: ���ز��ƻ�ģ������ 's �ϼ����� property 
     */
    public com.kingdee.eas.fdc.schedule.RESchTemplateTaskInfo getParent()
    {
        return (com.kingdee.eas.fdc.schedule.RESchTemplateTaskInfo)get("parent");
    }
    public void setParent(com.kingdee.eas.fdc.schedule.RESchTemplateTaskInfo item)
    {
        put("parent", item);
    }
    /**
     * Object: ���ز��ƻ�ģ������ 's ��׼����ָ�� property 
     */
    public com.kingdee.eas.fdc.schedule.StandardTaskGuideInfo getStandardTask()
    {
        return (com.kingdee.eas.fdc.schedule.StandardTaskGuideInfo)get("standardTask");
    }
    public void setStandardTask(com.kingdee.eas.fdc.schedule.StandardTaskGuideInfo item)
    {
        put("standardTask", item);
    }
    /**
     * Object:���ز��ƻ�ģ������'s �������property 
     */
    public com.kingdee.eas.fdc.schedule.RESchTaskTypeEnum getTaskType()
    {
        return com.kingdee.eas.fdc.schedule.RESchTaskTypeEnum.getEnum(getString("taskType"));
    }
    public void setTaskType(com.kingdee.eas.fdc.schedule.RESchTaskTypeEnum item)
    {
		if (item != null) {
        setString("taskType", item.getValue());
		}
    }
    /**
     * Object: ���ز��ƻ�ģ������ 's �ɹ���� property 
     */
    public com.kingdee.eas.fdc.schedule.AchievementTypeInfo getAchievementType()
    {
        return (com.kingdee.eas.fdc.schedule.AchievementTypeInfo)get("achievementType");
    }
    public void setAchievementType(com.kingdee.eas.fdc.schedule.AchievementTypeInfo item)
    {
        put("achievementType", item);
    }
    /**
     * Object: ���ز��ƻ�ģ������ 's ҵ������ property 
     */
    public com.kingdee.eas.fdc.schedule.RESchTemplateTaskBizTypeCollection getBusinessType()
    {
        return (com.kingdee.eas.fdc.schedule.RESchTemplateTaskBizTypeCollection)get("businessType");
    }
    /**
     * Object: ���ز��ƻ�ģ������ 's ǰ������ property 
     */
    public com.kingdee.eas.fdc.schedule.RESchTemplateTaskPredecessorCollection getPredecessors()
    {
        return (com.kingdee.eas.fdc.schedule.RESchTemplateTaskPredecessorCollection)get("predecessors");
    }
    /**
     * Object:���ز��ƻ�ģ������'s �ο�����property 
     */
    public int getReferenceDay()
    {
        return getInt("referenceDay");
    }
    public void setReferenceDay(int item)
    {
        setInt("referenceDay", item);
    }
    /**
     * Object:���ز��ƻ�ģ������'s ���property 
     */
    public int getSeq()
    {
        return getInt("seq");
    }
    public void setSeq(int item)
    {
        setInt("seq", item);
    }
    /**
     * Object:���ز��ƻ�ģ������'s ǰ����������property 
     */
    public String getPredecessorDesc()
    {
        return getString("predecessorDesc");
    }
    public void setPredecessorDesc(String item)
    {
        setString("predecessorDesc", item);
    }
    /**
     * Object:���ز��ƻ�ģ������'s ҵ����������property 
     */
    public String getBusinessTypeDesc()
    {
        return getString("businessTypeDesc");
    }
    public void setBusinessTypeDesc(String item)
    {
        setString("businessTypeDesc", item);
    }
    /**
     * Object:���ز��ƻ�ģ������'s ��Դ�ڼ��Źܿؽڵ�property 
     */
    public String getSrcGroupNode()
    {
        return getString("srcGroupNode");
    }
    public void setSrcGroupNode(String item)
    {
        setString("srcGroupNode", item);
    }
    /**
     * Object:���ز��ƻ�ģ������'s �Ƿ�¥���ڵ�property 
     */
    public boolean isIsBuildingNode()
    {
        return getBoolean("isBuildingNode");
    }
    public void setIsBuildingNode(boolean item)
    {
        setBoolean("isBuildingNode", item);
    }
    /**
     * Object:���ز��ƻ�ģ������'s �Ƿ�Ӫ�����ڵ�property 
     */
    public boolean isIsOperationNode()
    {
        return getBoolean("isOperationNode");
    }
    public void setIsOperationNode(boolean item)
    {
        setBoolean("isOperationNode", item);
    }
    /**
     * Object: ���ز��ƻ�ģ������ 's ������ property 
     */
    public com.kingdee.eas.basedata.person.PersonInfo getAdminPerson()
    {
        return (com.kingdee.eas.basedata.person.PersonInfo)get("adminPerson");
    }
    public void setAdminPerson(com.kingdee.eas.basedata.person.PersonInfo item)
    {
        put("adminPerson", item);
    }
    /**
     * Object: ���ز��ƻ�ģ������ 's ���β��� property 
     */
    public com.kingdee.eas.basedata.org.FullOrgUnitInfo getAdminDept()
    {
        return (com.kingdee.eas.basedata.org.FullOrgUnitInfo)get("adminDept");
    }
    public void setAdminDept(com.kingdee.eas.basedata.org.FullOrgUnitInfo item)
    {
        put("adminDept", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("198DF1AE");
    }
}