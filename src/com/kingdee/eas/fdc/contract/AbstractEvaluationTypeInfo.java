package com.kingdee.eas.fdc.contract;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractEvaluationTypeInfo extends com.kingdee.eas.framework.TreeBaseInfo implements Serializable 
{
    public AbstractEvaluationTypeInfo()
    {
        this("id");
    }
    protected AbstractEvaluationTypeInfo(String pkField)
    {
        super(pkField);
        put("entry", new com.kingdee.eas.fdc.contract.EvaluationTypeEntryCollection());
    }
    /**
     * Object:��������'s ���û����״̬property 
     */
    public boolean isIsEnabled()
    {
        return getBoolean("isEnabled");
    }
    public void setIsEnabled(boolean item)
    {
        setBoolean("isEnabled", item);
    }
    /**
     * Object: �������� 's ���ڵ� property 
     */
    public com.kingdee.eas.fdc.contract.EvaluationTypeInfo getParent()
    {
        return (com.kingdee.eas.fdc.contract.EvaluationTypeInfo)get("parent");
    }
    public void setParent(com.kingdee.eas.fdc.contract.EvaluationTypeInfo item)
    {
        put("parent", item);
    }
    /**
     * Object: �������� 's ��¼ property 
     */
    public com.kingdee.eas.fdc.contract.EvaluationTypeEntryCollection getEntry()
    {
        return (com.kingdee.eas.fdc.contract.EvaluationTypeEntryCollection)get("entry");
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("71588B91");
    }
}