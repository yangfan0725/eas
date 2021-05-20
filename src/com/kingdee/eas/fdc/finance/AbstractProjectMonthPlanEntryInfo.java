package com.kingdee.eas.fdc.finance;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractProjectMonthPlanEntryInfo extends com.kingdee.eas.framework.CoreBillEntryBaseInfo implements Serializable 
{
    public AbstractProjectMonthPlanEntryInfo()
    {
        this("id");
    }
    protected AbstractProjectMonthPlanEntryInfo(String pkField)
    {
        super(pkField);
        put("dateEntry", new com.kingdee.eas.fdc.finance.ProjectMonthPlanDateEntryCollection());
    }
    /**
     * Object: ��Ŀ�¶ȸ���ƻ���¼ 's ��Ŀ�¶ȸ���ƻ� property 
     */
    public com.kingdee.eas.fdc.finance.ProjectMonthPlanInfo getHead()
    {
        return (com.kingdee.eas.fdc.finance.ProjectMonthPlanInfo)get("head");
    }
    public void setHead(com.kingdee.eas.fdc.finance.ProjectMonthPlanInfo item)
    {
        put("head", item);
    }
    /**
     * Object: ��Ŀ�¶ȸ���ƻ���¼ 's ��ͬ property 
     */
    public com.kingdee.eas.fdc.contract.ContractBillInfo getContractBill()
    {
        return (com.kingdee.eas.fdc.contract.ContractBillInfo)get("contractBill");
    }
    public void setContractBill(com.kingdee.eas.fdc.contract.ContractBillInfo item)
    {
        put("contractBill", item);
    }
    /**
     * Object: ��Ŀ�¶ȸ���ƻ���¼ 's ��ϸ��¼ property 
     */
    public com.kingdee.eas.fdc.finance.ProjectMonthPlanDateEntryCollection getDateEntry()
    {
        return (com.kingdee.eas.fdc.finance.ProjectMonthPlanDateEntryCollection)get("dateEntry");
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("6B44ADF3");
    }
}