package com.kingdee.eas.fdc.schedule;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractTaskEvaluationInfo extends com.kingdee.eas.fdc.basedata.FDCDataBaseInfo implements Serializable 
{
    public AbstractTaskEvaluationInfo()
    {
        this("id");
    }
    protected AbstractTaskEvaluationInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object:������������'s ��������property 
     */
    public com.kingdee.eas.fdc.schedule.TaskEvaluationTypeEnum getEvaluationType()
    {
        return com.kingdee.eas.fdc.schedule.TaskEvaluationTypeEnum.getEnum(getString("evaluationType"));
    }
    public void setEvaluationType(com.kingdee.eas.fdc.schedule.TaskEvaluationTypeEnum item)
    {
		if (item != null) {
        setString("evaluationType", item.getValue());
		}
    }
    /**
     * Object:������������'s ���۽��property 
     */
    public String getEvaluationResult()
    {
        return getEvaluationResult((Locale)null);
    }
    public void setEvaluationResult(String item)
    {
		setEvaluationResult(item,(Locale)null);
    }
    public String getEvaluationResult(Locale local)
    {
        return TypeConversionUtils.objToString(get("evaluationResult", local));
    }
    public void setEvaluationResult(String item, Locale local)
    {
        put("evaluationResult", item, local);
    }
    /**
     * Object:������������'s �Ƿ�ͨ��property 
     */
    public boolean isEvaluationPass()
    {
        return getBoolean("evaluationPass");
    }
    public void setEvaluationPass(boolean item)
    {
        setBoolean("evaluationPass", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("54E273F7");
    }
}