package com.kingdee.eas.fdc.finance;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractWorkLoadConfirmBillRelTaskInfo extends com.kingdee.eas.framework.CoreBillEntryBaseInfo implements Serializable 
{
    public AbstractWorkLoadConfirmBillRelTaskInfo()
    {
        this("id");
    }
    protected AbstractWorkLoadConfirmBillRelTaskInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: ��ȷ�ϵ����õ����� 's �����ĺ�ͬ property 
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
     * Object: ��ȷ�ϵ����õ����� 's ��Ӧ������ property 
     */
    public com.kingdee.eas.fdc.schedule.WorkAmountEntryInfo getWorkamountEntry()
    {
        return (com.kingdee.eas.fdc.schedule.WorkAmountEntryInfo)get("workamountEntry");
    }
    public void setWorkamountEntry(com.kingdee.eas.fdc.schedule.WorkAmountEntryInfo item)
    {
        put("workamountEntry", item);
    }
    /**
     * Object: ��ȷ�ϵ����õ����� 's ���õķ�¼ property 
     */
    public com.kingdee.eas.fdc.finance.WorkLoadConfirmBillInfo getParent()
    {
        return (com.kingdee.eas.fdc.finance.WorkLoadConfirmBillInfo)get("parent");
    }
    public void setParent(com.kingdee.eas.fdc.finance.WorkLoadConfirmBillInfo item)
    {
        put("parent", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("7F6F991D");
    }
}