package com.kingdee.eas.fdc.sellhouse;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractFollowAssistantDataInfo extends com.kingdee.eas.fdc.basedata.FDCDataBaseInfo implements Serializable 
{
    public AbstractFollowAssistantDataInfo()
    {
        this("id");
    }
    protected AbstractFollowAssistantDataInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: ������������ 's ����������������Ŀ�Ĺ�ϵ property 
     */
    public com.kingdee.eas.fdc.sellhouse.SellProjectInfo getSellProject()
    {
        return (com.kingdee.eas.fdc.sellhouse.SellProjectInfo)get("sellProject");
    }
    public void setSellProject(com.kingdee.eas.fdc.sellhouse.SellProjectInfo item)
    {
        put("sellProject", item);
    }
    /**
     * Object: ������������ 's ��������������������Ĺ�ϵ property 
     */
    public com.kingdee.eas.fdc.sellhouse.FollowAssistantDataTypeInfo getType()
    {
        return (com.kingdee.eas.fdc.sellhouse.FollowAssistantDataTypeInfo)get("type");
    }
    public void setType(com.kingdee.eas.fdc.sellhouse.FollowAssistantDataTypeInfo item)
    {
        put("type", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("F45AB0BC");
    }
}