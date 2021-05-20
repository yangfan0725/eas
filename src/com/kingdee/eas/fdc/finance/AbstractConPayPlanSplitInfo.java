package com.kingdee.eas.fdc.finance;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractConPayPlanSplitInfo extends com.kingdee.eas.fdc.basedata.FDCSplitBillInfo implements Serializable 
{
    public AbstractConPayPlanSplitInfo()
    {
        this("id");
    }
    protected AbstractConPayPlanSplitInfo(String pkField)
    {
        super(pkField);
        put("entrys", new com.kingdee.eas.fdc.finance.ConPayPlanSplitEntryCollection());
    }
    /**
     * Object: ��ͬ����ƻ���� 's null property 
     */
    public com.kingdee.eas.fdc.finance.ContractPayPlanInfo getBill()
    {
        return (com.kingdee.eas.fdc.finance.ContractPayPlanInfo)get("bill");
    }
    public void setBill(com.kingdee.eas.fdc.finance.ContractPayPlanInfo item)
    {
        put("bill", item);
    }
    /**
     * Object: ��ͬ����ƻ���� 's ��¼ property 
     */
    public com.kingdee.eas.fdc.finance.ConPayPlanSplitEntryCollection getEntrys()
    {
        return (com.kingdee.eas.fdc.finance.ConPayPlanSplitEntryCollection)get("entrys");
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("7B145B5C");
    }
}