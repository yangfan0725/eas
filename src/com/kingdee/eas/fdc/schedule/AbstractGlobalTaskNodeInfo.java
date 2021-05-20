package com.kingdee.eas.fdc.schedule;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractGlobalTaskNodeInfo extends com.kingdee.eas.fdc.basedata.FDCDataBaseInfo implements Serializable 
{
    public AbstractGlobalTaskNodeInfo()
    {
        this("id");
    }
    protected AbstractGlobalTaskNodeInfo(String pkField)
    {
        super(pkField);
        put("bizType", new com.kingdee.eas.fdc.schedule.GTNBizTypeCollection());
    }
    /**
     * Object: ���Źܿؽڵ� 's �׶��Գɹ���� property 
     */
    public com.kingdee.eas.fdc.schedule.AchievementTypeInfo getAchType()
    {
        return (com.kingdee.eas.fdc.schedule.AchievementTypeInfo)get("achType");
    }
    public void setAchType(com.kingdee.eas.fdc.schedule.AchievementTypeInfo item)
    {
        put("achType", item);
    }
    /**
     * Object:���Źܿؽڵ�'s ҵ����������property 
     */
    public String getBizTypeDesc()
    {
        return getString("bizTypeDesc");
    }
    public void setBizTypeDesc(String item)
    {
        setString("bizTypeDesc", item);
    }
    /**
     * Object: ���Źܿؽڵ� 's ���з�¼ property 
     */
    public com.kingdee.eas.fdc.schedule.GTNBizTypeCollection getBizType()
    {
        return (com.kingdee.eas.fdc.schedule.GTNBizTypeCollection)get("bizType");
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("1F89DF40");
    }
}