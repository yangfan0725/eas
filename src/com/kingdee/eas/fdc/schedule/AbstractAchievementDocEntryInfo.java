package com.kingdee.eas.fdc.schedule;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractAchievementDocEntryInfo extends com.kingdee.eas.framework.CoreBillEntryBaseInfo implements Serializable 
{
    public AbstractAchievementDocEntryInfo()
    {
        this("id");
    }
    protected AbstractAchievementDocEntryInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object:�ɹ��ĵ���¼'s �ĵ�·��property 
     */
    public String getDocPath()
    {
        return getString("docPath");
    }
    public void setDocPath(String item)
    {
        setString("docPath", item);
    }
    /**
     * Object:�ɹ��ĵ���¼'s �ĵ�����property 
     */
    public String getDocName()
    {
        return getString("docName");
    }
    public void setDocName(String item)
    {
        setString("docName", item);
    }
    /**
     * Object:�ɹ��ĵ���¼'s �ĵ�IDproperty 
     */
    public String getDocID()
    {
        return getString("docID");
    }
    public void setDocID(String item)
    {
        setString("docID", item);
    }
    /**
     * Object:�ɹ��ĵ���¼'s �ĵ�����property 
     */
    public String getDocType()
    {
        return getString("docType");
    }
    public void setDocType(String item)
    {
        setString("docType", item);
    }
    /**
     * Object: �ɹ��ĵ���¼ 's �ɹ����� property 
     */
    public com.kingdee.eas.fdc.schedule.AchievementManagerInfo getParent()
    {
        return (com.kingdee.eas.fdc.schedule.AchievementManagerInfo)get("parent");
    }
    public void setParent(com.kingdee.eas.fdc.schedule.AchievementManagerInfo item)
    {
        put("parent", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("5A8292D3");
    }
}