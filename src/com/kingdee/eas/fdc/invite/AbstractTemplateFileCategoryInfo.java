package com.kingdee.eas.fdc.invite;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractTemplateFileCategoryInfo extends com.kingdee.eas.framework.TreeBaseInfo implements Serializable 
{
    public AbstractTemplateFileCategoryInfo()
    {
        this("id");
    }
    protected AbstractTemplateFileCategoryInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object:ģ���ļ���¼'s ����property 
     */
    public String getContext()
    {
        return getString("context");
    }
    public void setContext(String item)
    {
        setString("context", item);
    }
    /**
     * Object: ģ���ļ���¼ 's �ϼ�Ŀ¼ property 
     */
    public com.kingdee.eas.fdc.invite.TemplateFileCategoryInfo getParent()
    {
        return (com.kingdee.eas.fdc.invite.TemplateFileCategoryInfo)get("parent");
    }
    public void setParent(com.kingdee.eas.fdc.invite.TemplateFileCategoryInfo item)
    {
        put("parent", item);
    }
    /**
     * Object: ģ���ļ���¼ 's ģ���ļ� property 
     */
    public com.kingdee.eas.fdc.invite.TemplateFileInfo getTempFile()
    {
        return (com.kingdee.eas.fdc.invite.TemplateFileInfo)get("tempFile");
    }
    public void setTempFile(com.kingdee.eas.fdc.invite.TemplateFileInfo item)
    {
        put("tempFile", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("5D839D58");
    }
}