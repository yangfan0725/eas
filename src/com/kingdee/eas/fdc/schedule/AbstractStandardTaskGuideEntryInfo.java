package com.kingdee.eas.fdc.schedule;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractStandardTaskGuideEntryInfo extends com.kingdee.eas.framework.CoreBillEntryBaseInfo implements Serializable 
{
    public AbstractStandardTaskGuideEntryInfo()
    {
        this("id");
    }
    protected AbstractStandardTaskGuideEntryInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: ��׼����ָ����¼ 's ��׼����ָ�� property 
     */
    public com.kingdee.eas.fdc.schedule.StandardTaskGuideInfo getParent()
    {
        return (com.kingdee.eas.fdc.schedule.StandardTaskGuideInfo)get("parent");
    }
    public void setParent(com.kingdee.eas.fdc.schedule.StandardTaskGuideInfo item)
    {
        put("parent", item);
    }
    /**
     * Object:��׼����ָ����¼'s �ĵ�Ŀ¼property 
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
     * Object:��׼����ָ����¼'s �ĵ�����property 
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
     * Object:��׼����ָ����¼'s �Ƿ��ϴ���Эͬproperty 
     */
    public boolean isIsCPFile()
    {
        return getBoolean("isCPFile");
    }
    public void setIsCPFile(boolean item)
    {
        setBoolean("isCPFile", item);
    }
    /**
     * Object:��׼����ָ����¼'s �ĵ���IDproperty 
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
     * Object:��׼����ָ����¼'s ָʾ����property 
     */
    public com.kingdee.eas.fdc.schedule.GuideTypeEnum getGuideType()
    {
        return com.kingdee.eas.fdc.schedule.GuideTypeEnum.getEnum(getString("guideType"));
    }
    public void setGuideType(com.kingdee.eas.fdc.schedule.GuideTypeEnum item)
    {
		if (item != null) {
        setString("guideType", item.getValue());
		}
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("DF52EDAE");
    }
}