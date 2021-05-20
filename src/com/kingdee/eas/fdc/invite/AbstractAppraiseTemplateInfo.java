package com.kingdee.eas.fdc.invite;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractAppraiseTemplateInfo extends com.kingdee.eas.fdc.basedata.FDCBillInfo implements Serializable 
{
    public AbstractAppraiseTemplateInfo()
    {
        this("id");
    }
    protected AbstractAppraiseTemplateInfo(String pkField)
    {
        super(pkField);
        put("templateEntry", new com.kingdee.eas.fdc.invite.AppraiseTemplateEntryCollection());
    }
    /**
     * Object: ����ģ�� 's ģ������ property 
     */
    public com.kingdee.eas.fdc.invite.AppraiseTempletTypeInfo getTemplateType()
    {
        return (com.kingdee.eas.fdc.invite.AppraiseTempletTypeInfo)get("templateType");
    }
    public void setTemplateType(com.kingdee.eas.fdc.invite.AppraiseTempletTypeInfo item)
    {
        put("templateType", item);
    }
    /**
     * Object: ����ģ�� 's ģ���¼ property 
     */
    public com.kingdee.eas.fdc.invite.AppraiseTemplateEntryCollection getTemplateEntry()
    {
        return (com.kingdee.eas.fdc.invite.AppraiseTemplateEntryCollection)get("templateEntry");
    }
    /**
     * Object:����ģ��'s �Ƿ�Ȩ�ؼ��������ֵproperty 
     */
    public boolean isIsUserWidth()
    {
        return getBoolean("isUserWidth");
    }
    public void setIsUserWidth(boolean item)
    {
        setBoolean("isUserWidth", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("831F2B29");
    }
}