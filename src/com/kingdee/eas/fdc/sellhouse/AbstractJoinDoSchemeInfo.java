package com.kingdee.eas.fdc.sellhouse;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractJoinDoSchemeInfo extends com.kingdee.eas.fdc.basedata.FDCDataBaseInfo implements Serializable 
{
    public AbstractJoinDoSchemeInfo()
    {
        this("id");
    }
    protected AbstractJoinDoSchemeInfo(String pkField)
    {
        super(pkField);
        put("dataEntries", new com.kingdee.eas.fdc.sellhouse.JoinDataEntryCollection());
        put("apprEntries", new com.kingdee.eas.fdc.sellhouse.JoinApproachEntryCollection());
    }
    /**
     * Object: ��﷽�� 's ������Ŀ property 
     */
    public com.kingdee.eas.fdc.sellhouse.SellProjectInfo getProject()
    {
        return (com.kingdee.eas.fdc.sellhouse.SellProjectInfo)get("project");
    }
    public void setProject(com.kingdee.eas.fdc.sellhouse.SellProjectInfo item)
    {
        put("project", item);
    }
    /**
     * Object: ��﷽�� 's ���������� property 
     */
    public com.kingdee.eas.fdc.sellhouse.JoinApproachEntryCollection getApprEntries()
    {
        return (com.kingdee.eas.fdc.sellhouse.JoinApproachEntryCollection)get("apprEntries");
    }
    /**
     * Object: ��﷽�� 's ���������� property 
     */
    public com.kingdee.eas.fdc.sellhouse.JoinDataEntryCollection getDataEntries()
    {
        return (com.kingdee.eas.fdc.sellhouse.JoinDataEntryCollection)get("dataEntries");
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("F973C7B5");
    }
}