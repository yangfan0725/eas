package com.kingdee.eas.fdc.finance;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractSettledMonthlyInfo extends com.kingdee.eas.framework.CoreBaseInfo implements Serializable 
{
    public AbstractSettledMonthlyInfo()
    {
        this("id");
    }
    protected AbstractSettledMonthlyInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object:�½����ݱ�'s ʵ��BOSTypeproperty 
     */
    public String getEntityID()
    {
        return getString("entityID");
    }
    public void setEntityID(String item)
    {
        setString("entityID", item);
    }
    /**
     * Object:�½����ݱ�'s ����IDproperty 
     */
    public String getObjectID()
    {
        return getString("objectID");
    }
    public void setObjectID(String item)
    {
        setString("objectID", item);
    }
    /**
     * Object:�½����ݱ�'s ������ĿIDproperty 
     */
    public String getCurProjectID()
    {
        return getString("curProjectID");
    }
    public void setCurProjectID(String item)
    {
        setString("curProjectID", item);
    }
    /**
     * Object: �½����ݱ� 's �½��ڼ� property 
     */
    public com.kingdee.eas.basedata.assistant.PeriodInfo getSettlePeriod()
    {
        return (com.kingdee.eas.basedata.assistant.PeriodInfo)get("settlePeriod");
    }
    public void setSettlePeriod(com.kingdee.eas.basedata.assistant.PeriodInfo item)
    {
        put("settlePeriod", item);
    }
    /**
     * Object:�½����ݱ�'s ��ͬID�����ں������ݴ����ѯ���ݣ�property 
     */
    public String getContractID()
    {
        return getString("contractID");
    }
    public void setContractID(String item)
    {
        setString("contractID", item);
    }
    /**
     * Object:�½����ݱ�'s �Ƿ�ɱ��½�property 
     */
    public boolean isIsCost()
    {
        return getBoolean("isCost");
    }
    public void setIsCost(boolean item)
    {
        setBoolean("isCost", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("C78A4423");
    }
}