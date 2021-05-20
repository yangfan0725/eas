package com.kingdee.eas.fdc.invite;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractTemplateFileInfo extends com.kingdee.eas.fdc.basedata.FDCBillInfo implements Serializable 
{
    public AbstractTemplateFileInfo()
    {
        this("id");
    }
    protected AbstractTemplateFileInfo(String pkField)
    {
        super(pkField);
        put("fileCategory", new com.kingdee.eas.fdc.invite.TemplateFileCategoryCollection());
    }
    /**
     * Object: ����ģ���ļ� 's ģ������ property 
     */
    public com.kingdee.eas.fdc.invite.TemplateTypeInfo getTemplateType()
    {
        return (com.kingdee.eas.fdc.invite.TemplateTypeInfo)get("templateType");
    }
    public void setTemplateType(com.kingdee.eas.fdc.invite.TemplateTypeInfo item)
    {
        put("templateType", item);
    }
    /**
     * Object: ����ģ���ļ� 's �ļ�Ŀ¼ property 
     */
    public com.kingdee.eas.fdc.invite.TemplateFileCategoryCollection getFileCategory()
    {
        return (com.kingdee.eas.fdc.invite.TemplateFileCategoryCollection)get("fileCategory");
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("CCDB563A");
    }
}