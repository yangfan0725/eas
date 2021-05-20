package com.kingdee.eas.fdc.finance;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractSubjectLevelInfo extends com.kingdee.eas.framework.DataBaseInfo implements Serializable 
{
    public AbstractSubjectLevelInfo()
    {
        this("id");
    }
    protected AbstractSubjectLevelInfo(String pkField)
    {
        super(pkField);
        put("Subject", new com.kingdee.eas.fdc.finance.SubjectLevelSubjectCollection());
    }
    /**
     * Object: �ƻ���Ŀ���� 's ��Ŀ��¼ property 
     */
    public com.kingdee.eas.fdc.finance.SubjectLevelSubjectCollection getSubject()
    {
        return (com.kingdee.eas.fdc.finance.SubjectLevelSubjectCollection)get("Subject");
    }
    /**
     * Object:�ƻ���Ŀ����'s ����property 
     */
    public boolean isIsEnabled()
    {
        return getBoolean("isEnabled");
    }
    public void setIsEnabled(boolean item)
    {
        setBoolean("isEnabled", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("596E1847");
    }
}