package com.kingdee.eas.fdc.sellhouse;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractChequeSellProjectEntryInfo extends com.kingdee.eas.framework.CoreBaseInfo implements Serializable 
{
    public AbstractChequeSellProjectEntryInfo()
    {
        this("id");
    }
    protected AbstractChequeSellProjectEntryInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: Ʊ����������Ŀ 's ����ͷ property 
     */
    public com.kingdee.eas.fdc.sellhouse.CRMChequeInfo getCRMCheque()
    {
        return (com.kingdee.eas.fdc.sellhouse.CRMChequeInfo)get("CRMCheque");
    }
    public void setCRMCheque(com.kingdee.eas.fdc.sellhouse.CRMChequeInfo item)
    {
        put("CRMCheque", item);
    }
    /**
     * Object: Ʊ����������Ŀ 's ������Ŀ property 
     */
    public com.kingdee.eas.fdc.sellhouse.SellProjectInfo getSellProject()
    {
        return (com.kingdee.eas.fdc.sellhouse.SellProjectInfo)get("sellProject");
    }
    public void setSellProject(com.kingdee.eas.fdc.sellhouse.SellProjectInfo item)
    {
        put("sellProject", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("4C846227");
    }
}