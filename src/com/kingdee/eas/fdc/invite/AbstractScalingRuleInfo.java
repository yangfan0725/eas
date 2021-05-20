package com.kingdee.eas.fdc.invite;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractScalingRuleInfo extends com.kingdee.eas.fdc.basedata.FDCDataBaseInfo implements Serializable 
{
    public AbstractScalingRuleInfo()
    {
        this("id");
    }
    protected AbstractScalingRuleInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object:�������'s �Ƿ�������ϵ��property 
     */
    public boolean isNeedCorrection()
    {
        return getBoolean("needCorrection");
    }
    public void setNeedCorrection(boolean item)
    {
        setBoolean("needCorrection", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("9EB3C97F");
    }
}